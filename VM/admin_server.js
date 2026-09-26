const http = require('http');
const fs = require('fs');
const path = require('path');

let mysql;
try {
  mysql = require('mysql2/promise');
} catch (e1) {
  try {
    const webNodeModulesPath = path.resolve(__dirname, '../web/node_modules/mysql2/promise');
    mysql = require(webNodeModulesPath);
  } catch (e2) {
    console.error('Lỗi: Không tìm thấy thư viện mysql2. Hãy chạy `npm install` trong thư mục web/ trước.');
    process.exit(1);
  }
}

const PORT = 4000;
const DB_CONFIG = {
  host: process.env.MYSQL_HOST || '161.118.202.174',
  port: Number(process.env.MYSQL_PORT) || 3306,
  user: process.env.MYSQL_USER || 'nso_web',
  password: process.env.MYSQL_PASSWORD || 'NsoWebDb2026!@#',
  database: process.env.MYSQL_DATABASE || 'nso_test',
  waitForConnections: true,
  connectionLimit: 5,
  queueLimit: 0,
};

let pool;
function getPool() {
  if (!pool) {
    pool = mysql.createPool(DB_CONFIG);
  }
  return pool;
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

    // 3. GET /api/giftcodes - List all giftcodes
    if (pathname === '/api/giftcodes' && method === 'GET') {
      const db = getPool();
      const [rows] = await db.query(
        `SELECT id, code, type, server_id, gold, coin, yen, items, status, expires_at, created_at, updated_at 
         FROM gift_codes 
         ORDER BY id DESC`
      );

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
          ...r,
          parsedItems,
        };
      });

      return sendJson(res, 200, { success: true, giftcodes: formatted });
    }

    // 4. POST /api/giftcodes - Create new giftcode
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
      const itemsJson = JSON.stringify(Array.isArray(items) ? items : []);
      const expireDate = expires_at ? new Date(expires_at) : null;

      const db = getPool();
      const [existing] = await db.query('SELECT id FROM gift_codes WHERE code = ?', [cleanCode]);
      if (existing.length > 0) {
        return sendJson(res, 400, { success: false, error: `Mã giftcode "${cleanCode}" đã tồn tại!` });
      }

      const [result] = await db.query(
        `INSERT INTO gift_codes (code, type, server_id, gold, coin, yen, items, status, expires_at, created_at, updated_at)
         VALUES (?, ?, 0, ?, ?, ?, ?, 0, ?, NOW(), NOW())`,
        [cleanCode, codeType, rewardGold, rewardCoin, rewardYen, itemsJson, expireDate]
      );

      return sendJson(res, 200, {
        success: true,
        message: `Đã tạo thành công giftcode "${cleanCode}" (ID: ${result.insertId})!`,
        id: result.insertId,
      });
    }

    // 5. DELETE /api/giftcodes - Delete giftcode and CASCADE references
    if (pathname === '/api/giftcodes' && method === 'DELETE') {
      const body = await parseJsonBody(req);
      const { id, code } = body;

      if (!id && !code) {
        return sendJson(res, 400, { success: false, error: 'Cần ID hoặc Mã code để xóa!' });
      }

      const db = getPool();
      let targetCode = code;

      if (!targetCode && id) {
        const [rows] = await db.query('SELECT code FROM gift_codes WHERE id = ?', [id]);
        if (rows.length > 0) {
          targetCode = rows[0].code;
        }
      }

      // Execute cascade deletion: delete from histories first, then from gift_codes
      let historyDeleted = 0;
      if (targetCode) {
        const [histResult] = await db.query(
          'DELETE FROM gift_code_histories WHERE gift_code = ?',
          [targetCode]
        );
        historyDeleted = histResult.affectedRows || 0;
      }

      let codeDeleted = 0;
      if (id) {
        const [codeResult] = await db.query('DELETE FROM gift_codes WHERE id = ?', [id]);
        codeDeleted = codeResult.affectedRows || 0;
      } else if (targetCode) {
        const [codeResult] = await db.query('DELETE FROM gift_codes WHERE code = ?', [targetCode]);
        codeDeleted = codeResult.affectedRows || 0;
      }

      return sendJson(res, 200, {
        success: true,
        message: `Đã xóa giftcode "${targetCode || id}" thành công (Đã dọn dẹp ${historyDeleted} bản ghi lịch sử liên quan trong gift_code_histories)!`,
        historyDeleted,
        codeDeleted,
      });
    }

    // 6. POST /api/generate-otp - Generate 6-digit OTP
    if (pathname === '/api/generate-otp' && method === 'POST') {
      const otp = Math.floor(100000 + Math.random() * 900000).toString();
      const db = getPool();
      
      // Purge expired
      await db.query('DELETE FROM registration_otps WHERE expires_at < NOW()');

      // Insert new OTP with 90 minutes expiration
      await db.query(
        `INSERT INTO registration_otps (code, created_at, expires_at, used)
         VALUES (?, NOW(), DATE_ADD(NOW(), INTERVAL 90 MINUTE), 0)`,
        [otp]
      );

      return sendJson(res, 200, {
        success: true,
        otp,
        expiresInMinutes: 90,
        message: `Tạo mã OTP ${otp} thành công! Có hiệu lực trong 90 phút.`,
      });
    }

    // 7. GET /api/otps - List active OTPs
    if (pathname === '/api/otps' && method === 'GET') {
      const db = getPool();
      const [rows] = await db.query(
        `SELECT id, code, created_at, expires_at, used, used_by, used_at 
         FROM registration_otps 
         ORDER BY id DESC LIMIT 50`
      );
      return sendJson(res, 200, { success: true, otps: rows });
    }

    // 8. DELETE /api/otps - Delete an OTP
    if (pathname === '/api/otps' && method === 'DELETE') {
      const body = await parseJsonBody(req);
      const { code } = body;
      if (!code) {
        return sendJson(res, 400, { success: false, error: 'Thiếu mã OTP cần xóa' });
      }
      const db = getPool();
      await db.query('DELETE FROM registration_otps WHERE code = ?', [code]);
      return sendJson(res, 200, { success: true, message: `Đã xóa mã OTP ${code}` });
    }

    // 404
    return sendJson(res, 404, { success: false, error: 'Endpoint không tồn tại' });
  } catch (err) {
    console.error('Server error:', err);
    return sendJson(res, 500, { success: false, error: 'Lỗi server: ' + err.message });
  }
});

server.listen(PORT, () => {
  console.log(`==========================================================`);
  console.log(`  TRÌNH QUẢN LÝ GIFTCODE & OTP LOCAL CHO NSO SERVER      `);
  console.log(`  Truy cập giao diện tại: http://localhost:${PORT}        `);
  console.log(`==========================================================`);
});
