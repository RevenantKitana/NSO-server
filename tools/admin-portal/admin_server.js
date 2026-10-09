const http = require('http');
const fs = require('fs');
const path = require('path');
const { spawn } = require('child_process');

// Load dynamic config from config/server_config.ini
const projectRoot = path.resolve(__dirname, '../..');
const configPath = path.join(projectRoot, 'config', 'server_config.ini');

if (!fs.existsSync(configPath)) {
  console.error(`==========================================================`);
  console.error(`[LOI NGHIEM TRONG] Khong tim thay file cau hinh:`);
  console.error(`  ${configPath}`);
  console.error(`Vui long tao file config/server_config.ini truoc khi chay!`);
  console.error(`==========================================================`);
  process.exit(1);
}

let VM_HOST = '';
let VM_USER = '';
let PORT = 4000;
let SSH_KEY_PATH = '';

const content = fs.readFileSync(configPath, 'utf8');
content.split(/\r?\n/).forEach(line => {
  line = line.trim();
  if (line && !line.startsWith('#')) {
    const parts = line.split('=');
    if (parts.length >= 2) {
      const key = parts[0].trim();
      const val = parts.slice(1).join('=').trim();
      if (key === 'VM_IP') VM_HOST = val;
      if (key === 'VM_USER') VM_USER = val;
      if (key === 'WEB_PORT') PORT = Number(val) || 4000;
      if (key === 'SSH_KEY') SSH_KEY_PATH = path.resolve(projectRoot, val);
    }
  }
});

if (!VM_HOST || !VM_USER || !SSH_KEY_PATH) {
  console.error(`==========================================================`);
  console.error(`[LOI CAU HINH] File config/server_config.ini thieu thong so:`);
  if (!VM_HOST) console.error(`  [-] Thieu VM_IP`);
  if (!VM_USER) console.error(`  [-] Thieu VM_USER`);
  if (!SSH_KEY_PATH) console.error(`  [-] Thieu SSH_KEY`);
  console.error(`==========================================================`);
  process.exit(1);
}

// Load items data
let itemsList = [];
try {
  const itemsFilePath = path.join(__dirname, 'items_data.json');
  if (fs.existsSync(itemsFilePath)) {
    itemsList = JSON.parse(fs.readFileSync(itemsFilePath, 'utf8'));
  }
} catch (e) {
  console.error('Error loading items_data.json:', e.message);
}

// Execute SQL on VM directly via SSH Key (Streams SQL via stdin for 100% reliable quoting and syntax safety)
function executeSshSql(sql) {
  return new Promise((resolve, reject) => {
    const args = [
      '-i', SSH_KEY_PATH,
      '-o', 'StrictHostKeyChecking=no',
      '-o', 'LogLevel=ERROR',
      `${VM_USER}@${VM_HOST}`,
      'sudo mariadb nso_test --batch --raw'
    ];

    const proc = spawn('ssh', args);
    let stdout = '';
    let stderr = '';

    proc.stdout.on('data', d => { stdout += d; });
    proc.stderr.on('data', d => { stderr += d; });

    proc.on('close', code => {
      if (code !== 0) {
        return reject(new Error(stderr.trim() || `SSH command exited with code ${code}`));
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

// Parse MariaDB batch TSV output into JSON objects
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

function sendJson(res, statusCode, data) {
  res.writeHead(statusCode, {
    'Content-Type': 'application/json; charset=utf-8',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET, POST, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Content-Type',
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
  const parsedUrl = new URL(req.url, `http://${req.headers.host}`);
  const pathname = parsedUrl.pathname;
  const method = req.method;

  // Handle CORS preflight
  if (method === 'OPTIONS') {
    res.writeHead(204, {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET, POST, DELETE, OPTIONS',
      'Access-Control-Allow-Headers': 'Content-Type',
    });
    return res.end();
  }

  try {
    // 1. Static HTML File
    if (pathname === '/' || pathname === '/index.html' || pathname === '/admin_giftcode.html') {
      const htmlPath = path.join(__dirname, 'admin_giftcode.html');
      if (fs.existsSync(htmlPath)) {
        res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
        return fs.createReadStream(htmlPath).pipe(res);
      }
    }

    // 2. GET /api/items - Return all item templates
    if (pathname === '/api/items' && method === 'GET') {
      return sendJson(res, 200, { success: true, items: itemsList });
    }

    // 3. GET /api/giftcodes - List all giftcodes via SSH
    if (pathname === '/api/giftcodes' && method === 'GET') {
      const raw = await executeSshSql(
        'SELECT id, code, type, server_id, gold, coin, yen, items, status, expires_at, created_at, updated_at FROM gift_codes ORDER BY id DESC;'
      );
      const rows = parseTsv(raw);

      const formatted = rows.map(r => {
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
          type: Number(r.type) || 0,
          server_id: Number(r.server_id) || 0,
          gold: Number(r.gold) || 0,
          coin: Number(r.coin) || 0,
          yen: Number(r.yen) || 0,
          status: Number(r.status) || 0,
          expires_at: r.expires_at,
          created_at: r.created_at,
          updated_at: r.updated_at,
          parsedItems,
        };
      });

      return sendJson(res, 200, { success: true, giftcodes: formatted });
    }

    // 4. POST /api/giftcodes - Create new giftcode via SSH
    if (pathname === '/api/giftcodes' && method === 'POST') {
      const body = await parseJsonBody(req);
      const { code, type, gold, coin, yen, items, expires_at } = body;

      if (!code || String(code).trim().length < 3) {
        return sendJson(res, 400, { success: false, error: 'Mã giftcode không hợp lệ (ít nhất 3 ký tự)!' });
      }

      const cleanCode = String(code).trim().toUpperCase();
      const codeType = Number(type) || 0;
      const rewardGold = Math.max(0, Number(gold) || 0);
      const rewardCoin = Math.max(0, Number(coin) || 0);
      const rewardYen = Math.max(0, Number(yen) || 0);
      const itemsJson = JSON.stringify(Array.isArray(items) ? items : []).replace(/"/g, '\\"');
      const expireVal = expires_at ? `'${new Date(expires_at).toISOString().slice(0, 19).replace('T', ' ')}'` : 'NULL';

      const checkRaw = await executeSshSql(`SELECT id FROM gift_codes WHERE code = '${cleanCode}' LIMIT 1;`);
      const existing = parseTsv(checkRaw);
      if (existing.length > 0) {
        return sendJson(res, 400, { success: false, error: `Mã giftcode "${cleanCode}" đã tồn tại!` });
      }

      await executeSshSql(
        `INSERT INTO gift_codes (code, type, server_id, gold, coin, yen, items, status, expires_at, created_at, updated_at) ` +
        `VALUES ('${cleanCode}', ${codeType}, 0, ${rewardGold}, ${rewardCoin}, ${rewardYen}, '${itemsJson}', 0, ${expireVal}, NOW(), NOW());`
      );

      return sendJson(res, 200, {
        success: true,
        message: `Đã tạo thành công giftcode "${cleanCode}" qua kết nối SSH bảo mật!`,
      });
    }

    // 5. DELETE /api/giftcodes - Delete giftcode and CASCADE references via SSH
    if (pathname === '/api/giftcodes' && method === 'DELETE') {
      const body = await parseJsonBody(req);
      const { id, code } = body;

      if (!id && !code) {
        return sendJson(res, 400, { success: false, error: 'Cần ID hoặc Mã code để xóa!' });
      }

      let targetCode = code;
      if (!targetCode && id) {
        const raw = await executeSshSql(`SELECT code FROM gift_codes WHERE id = ${Number(id)} LIMIT 1;`);
        const rows = parseTsv(raw);
        if (rows.length > 0) targetCode = rows[0].code;
      }

      if (targetCode) {
        await executeSshSql(`DELETE FROM gift_code_histories WHERE gift_code = '${targetCode}';`);
      }

      if (id) {
        await executeSshSql(`DELETE FROM gift_codes WHERE id = ${Number(id)};`);
      } else if (targetCode) {
        await executeSshSql(`DELETE FROM gift_codes WHERE code = '${targetCode}';`);
      }

      return sendJson(res, 200, {
        success: true,
        message: `Đã xóa giftcode "${targetCode || id}" thành công (Đã dọn dẹp các bản ghi tham chiếu trong gift_code_histories)!`,
      });
    }

    // 6. POST /api/generate-otp - Generate 6-digit OTP via SSH
    if (pathname === '/api/generate-otp' && method === 'POST') {
      const otp = Math.floor(100000 + Math.random() * 900000).toString();
      
      await executeSshSql(
        `DELETE FROM registration_otps WHERE expires_at < NOW(); ` +
        `INSERT INTO registration_otps (code, created_at, expires_at, used) ` +
        `VALUES ('${otp}', NOW(), DATE_ADD(NOW(), INTERVAL 90 MINUTE), 0);`
      );

      return sendJson(res, 200, {
        success: true,
        otp,
        expiresInMinutes: 90,
        message: `Tạo mã OTP ${otp} thành công! Có hiệu lực trong 90 phút.`,
      });
    }

    // 7. GET /api/otps - List active OTPs via SSH
    if (pathname === '/api/otps' && method === 'GET') {
      const raw = await executeSshSql(
        'SELECT id, code, created_at, expires_at, used, used_by, used_at FROM registration_otps ORDER BY id DESC LIMIT 50;'
      );
      const rows = parseTsv(raw);
      const formatted = rows.map(r => ({
        id: Number(r.id),
        code: r.code,
        created_at: r.created_at,
        expires_at: r.expires_at,
        used: Number(r.used) || 0,
        used_by: r.used_by,
        used_at: r.used_at,
      }));
      return sendJson(res, 200, { success: true, otps: formatted });
    }

    // 8. DELETE /api/otps - Delete an OTP via SSH
    if (pathname === '/api/otps' && method === 'DELETE') {
      const body = await parseJsonBody(req);
      const { code } = body;
      if (!code) {
        return sendJson(res, 400, { success: false, error: 'Thiếu mã OTP cần xóa' });
      }
      await executeSshSql(`DELETE FROM registration_otps WHERE code = '${code}';`);
      return sendJson(res, 200, { success: true, message: `Đã xóa mã OTP ${code}` });
    }

    // 9. GET /api/game-notice - Get in-game welcome popup notice via SSH
    if (pathname === '/api/game-notice' && method === 'GET') {
      const raw = await executeSshSql("SELECT value FROM `options` WHERE `key` = 'thongbaogame' LIMIT 1;");
      const rows = parseTsv(raw);
      const notice = rows.length > 0 ? (rows[0].value || '') : '';
      return sendJson(res, 200, { success: true, notice });
    }

    // 10. POST /api/game-notice - Update in-game welcome popup notice via SSH
    if (pathname === '/api/game-notice' && method === 'POST') {
      const body = await parseJsonBody(req);
      const notice = body.notice !== undefined ? escapeSql(body.notice) : '';
      
      const checkRaw = await executeSshSql("SELECT id FROM `options` WHERE `key` = 'thongbaogame' LIMIT 1;");
      const existing = parseTsv(checkRaw);
      
      if (existing.length > 0) {
        await executeSshSql(`UPDATE \`options\` SET value = '${notice}', updated_at = NOW() WHERE \`key\` = 'thongbaogame';`);
      } else {
        await executeSshSql(`INSERT INTO \`options\` (\`key\`, value, updated_at) VALUES ('thongbaogame', '${notice}', NOW());`);
      }

      return sendJson(res, 200, {
        success: true,
        message: 'Đã cập nhật thông báo đầu game thành công qua kết nối SSH bảo mật!',
        notice: body.notice,
      });
    }

    // 404
    return sendJson(res, 404, { success: false, error: 'Endpoint không tồn tại' });
  } catch (err) {
    console.error('Server error:', err.message);
    return sendJson(res, 500, { success: false, error: 'Lỗi server: ' + err.message });
  }
});

async function initDb() {
  const initSql = `
    CREATE TABLE IF NOT EXISTS \`registration_otps\` (
      \`id\` INT AUTO_INCREMENT PRIMARY KEY,
      \`code\` VARCHAR(6) NOT NULL UNIQUE,
      \`created_at\` DATETIME DEFAULT CURRENT_TIMESTAMP,
      \`expires_at\` DATETIME NOT NULL,
      \`used\` TINYINT DEFAULT 0,
      \`used_by\` VARCHAR(50) DEFAULT NULL,
      \`used_at\` DATETIME DEFAULT NULL,
      INDEX \`idx_code\` (\`code\`),
      INDEX \`idx_expires\` (\`expires_at\`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
  `;
  try {
    await executeSshSql(initSql);
    console.log(`[DB] Bảng registration_otps và options đã sẵn sàng trên MySQL.`);
  } catch (err) {
    console.error(`[DB] Cảnh báo khởi tạo database:`, err.message);
  }
}

server.on('error', (err) => {
  if (err.code === 'EADDRINUSE') {
    console.error(`\n==========================================================`);
    console.error(`[LOI] Cong ${PORT} dang bi chiem dung boi ung dung khac!`);
    console.error(`Vui long doi WEB_PORT trong config/server_config.ini hoac dong chuong trinh dang dung port ${PORT}.`);
    console.error(`==========================================================\n`);
  } else {
    console.error(`\n[LOI SERVER]:`, err.message);
  }
  process.exit(1);
});

server.listen(PORT, async () => {
  console.log(`==========================================================`);
  console.log(`  TRÌNH QUẢN LÝ GIFTCODE & OTP LOCAL CHO NSO SERVER      `);
  console.log(`  Host VM đích: ${VM_USER}@${VM_HOST}`);
  console.log(`  Truy cập giao diện tại: http://localhost:${PORT}        `);
  console.log(`  Kết nối bảo mật trực tiếp qua SSH Key: OK              `);
  console.log(`==========================================================`);
  await initDb();
});
