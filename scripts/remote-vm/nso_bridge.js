#!/usr/bin/env node
// ===============================================================================
// NSO GAME SERVER - SECURE HTTP API BRIDGE (ZERO OPEN MYSQL PORT ARCHITECTURE)
// Provides secure REST endpoints for Vercel/Web without exposing MariaDB Port 3306.
// ===============================================================================

const http = require('http');
const { spawn } = require('child_process');
const net = require('net');

const PORT = process.env.BRIDGE_PORT || 80;
const SECRET_TOKEN = process.env.BRIDGE_SECRET_KEY || 'NsoBridgeSecret2026!@#';

// Execute local MariaDB query via streaming stdin
function querySql(sql) {
  return new Promise((resolve, reject) => {
    const proc = spawn('mariadb', [
      '-u', 'nso_user',
      '-h', '127.0.0.1',
      '-pNsoGame2026!@#',
      'nso_test',
      '--batch',
      '--raw'
    ]);
    let stdout = '';
    let stderr = '';

    proc.stdout.on('data', d => { stdout += d; });
    proc.stderr.on('data', d => { stderr += d; });

    proc.on('close', code => {
      if (code !== 0) {
        return reject(new Error(stderr.trim() || `MySQL query exited with code ${code}`));
      }
      resolve(stdout);
    });

    proc.on('error', reject);

    proc.stdin.write(sql.endsWith(';') ? sql + '\n' : sql + ';\n');
    proc.stdin.end();
  });
}

function escapeSql(str) {
  if (str === null || str === undefined) return '';
  return String(str).replace(/\\/g, '\\\\').replace(/'/g, "\\'");
}

function parseTsv(tsvString) {
  if (!tsvString || !tsvString.trim()) return [];
  const lines = tsvString.trim().split('\n');
  if (lines.length < 2) return [];
  const headers = lines[0].split('\t').map(h => h.trim());
  const rows = [];
  for (let i = 1; i < lines.length; i++) {
    const line = lines[i];
    if (!line.trim()) continue;
    const values = line.split('\t');
    const row = {};
    headers.forEach((h, idx) => {
      let val = values[idx] !== undefined ? values[idx].trim() : null;
      if (val === 'NULL') val = null;
      row[h] = val;
    });
    rows.push(row);
  }
  return rows;
}

function checkGameSocket(port = 14444, timeout = 2500) {
  return new Promise((resolve) => {
    const socket = new net.Socket();
    let isConnected = false;

    socket.setTimeout(timeout);
    socket.on('connect', () => {
      isConnected = true;
      socket.destroy();
      resolve(true);
    });
    socket.on('timeout', () => {
      socket.destroy();
      resolve(false);
    });
    socket.on('error', () => {
      socket.destroy();
      resolve(false);
    });
    socket.connect(port, '127.0.0.1');
  });
}

function sendJson(res, statusCode, data) {
  res.writeHead(statusCode, {
    'Content-Type': 'application/json; charset=utf-8',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type, x-bridge-token',
  });
  res.end(JSON.stringify(data));
}

function parseJsonBody(req) {
  return new Promise((resolve, reject) => {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        resolve(body ? JSON.parse(body) : {});
      } catch (err) {
        reject(err);
      }
    });
    req.on('error', reject);
  });
}

const server = http.createServer(async (req, res) => {
  const parsedUrl = new URL(req.url, `http://${req.headers.host || 'localhost'}`);
  const pathname = parsedUrl.pathname;
  const method = req.method;

  // CORS Preflight
  if (method === 'OPTIONS') {
    res.writeHead(204, {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type, x-bridge-token',
    });
    return res.end();
  }

  // Token Verification
  const clientToken = req.headers['x-bridge-token'];
  if (clientToken !== SECRET_TOKEN) {
    return sendJson(res, 401, { success: false, error: 'Unauthorized: Invalid Bridge Secret Token' });
  }

  try {
    // 1. GET /api/status - Live server status ping & user count
    if (pathname === '/api/status' && method === 'GET') {
      const isOnline = await checkGameSocket(14444);
      let totalUsers = 0;
      let onlineUsers = 0;

      try {
        const raw = await querySql('SELECT COUNT(*) as total, SUM(CASE WHEN online = 1 THEN 1 ELSE 0 END) as online FROM users;');
        const rows = parseTsv(raw);
        if (rows.length > 0) {
          totalUsers = Number(rows[0].total) || 0;
          onlineUsers = Number(rows[0].online) || 0;
        }
      } catch (e) {
        console.warn('DB query error in /api/status:', e.message);
      }

      return sendJson(res, 200, {
        success: true,
        server: {
          serverName: 'NSO Custom (Mod by Khánh)',
          status: isOnline ? 'ONLINE' : 'OFFLINE',
          isOnline,
          totalUsers,
          onlineUsers,
          lastChecked: new Date().toISOString(),
        }
      });
    }

    // 2. GET /api/giftcodes - List active giftcodes
    if (pathname === '/api/giftcodes' && method === 'GET') {
      const raw = await querySql(
        'SELECT id, code, type, gold, coin, yen, items, expires_at, created_at FROM gift_codes WHERE status = 0 AND (expires_at IS NULL OR expires_at > NOW()) ORDER BY id DESC LIMIT 50;'
      );
      const rows = parseTsv(raw);
      const giftcodes = rows.map(r => {
        let parsedItems = [];
        try {
          if (r.items) {
            parsedItems = typeof r.items === 'string' ? JSON.parse(r.items) : r.items;
          }
        } catch (e) {
          parsedItems = [];
        }
        return {
          id: Number(r.id),
          code: r.code,
          type: Number(r.type) === 1 ? 'Mỗi nhân vật 1 lần' : 'Dùng chung',
          gold: Number(r.gold) || 0,
          coin: Number(r.coin) || 0,
          yen: Number(r.yen) || 0,
          itemsCount: parsedItems.length,
          items: parsedItems,
          expiresAt: r.expires_at,
        };
      });

      return sendJson(res, 200, { success: true, giftcodes });
    }

    // 3. POST /api/register - Register account with OTP verification
    if (pathname === '/api/register' && method === 'POST') {
      const body = await parseJsonBody(req);
      const { username, passwordHash, otp } = body;

      if (!username || !passwordHash || !otp) {
        return sendJson(res, 400, { success: false, error: 'Thiếu thông tin đăng ký bắt buộc!' });
      }

      const cleanUsername = String(username).trim().toLowerCase();
      const cleanOtp = String(otp).trim();
      const escapedHash = escapeSql(passwordHash);

      // Validate OTP
      const otpRaw = await querySql(`SELECT id, code, expires_at, used FROM registration_otps WHERE code = '${escapeSql(cleanOtp)}' LIMIT 1;`);
      const otpRows = parseTsv(otpRaw);

      if (otpRows.length === 0) {
        return sendJson(res, 400, { success: false, error: 'Mã OTP không hợp lệ! Vui lòng liên hệ [Khánh] để nhận mã cấp phép.' });
      }

      const otpRec = otpRows[0];
      if (Number(otpRec.used) === 1) {
        return sendJson(res, 400, { success: false, error: 'Mã OTP này đã được sử dụng trước đó! Mỗi mã chỉ dùng được 1 lần.' });
      }

      if (new Date(otpRec.expires_at) < new Date()) {
        return sendJson(res, 400, { success: false, error: 'Mã OTP này đã hết hạn! Vui lòng liên hệ [Khánh] để nhận mã mới.' });
      }

      // Check username exists
      const userRaw = await querySql(`SELECT id FROM users WHERE username = '${escapeSql(cleanUsername)}' LIMIT 1;`);
      const userRows = parseTsv(userRaw);
      if (userRows.length > 0) {
        return sendJson(res, 400, { success: false, error: 'Tên tài khoản này đã được sử dụng! Vui lòng chọn tên khác.' });
      }

      // Insert User
      await querySql(
        `INSERT INTO users (username, password, activated, balance, luong, tongnap, point_vip, role, status, online, nap, tanthu, level, created_at, updated_at) ` +
        `VALUES ('${escapeSql(cleanUsername)}', '${escapedHash}', 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 'member', NOW(), NOW());`
      );

      // Delete used OTP
      await querySql(`DELETE FROM registration_otps WHERE code = '${escapeSql(cleanOtp)}';`);
      querySql('DELETE FROM registration_otps WHERE expires_at < NOW();').catch(() => {});

      return sendJson(res, 200, {
        success: true,
        message: 'Đăng ký tài khoản thành công! Bạn có thể mở Client game và đăng nhập ngay bây giờ.',
        username: cleanUsername,
      });
    }

    return sendJson(res, 404, { success: false, error: 'Endpoint not found' });
  } catch (err) {
    console.error('API Bridge Server Error:', err.message);
    return sendJson(res, 500, { success: false, error: err.message || 'Internal Bridge Error' });
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`[NSO BRIDGE] API Bridge Server listening on 0.0.0.0:${PORT}`);
  console.log(`[NSO BRIDGE] Secure Token Authentication: ENABLED`);
  console.log(`[NSO BRIDGE] MariaDB Port 3306 is isolated internally.`);
});
