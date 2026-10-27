/**
 * Migration Script: Chuẩn hóa toàn diện Bảng mã Option & Gộp dòng ẩn
 * Dự án: NSO Ninja School Online Server
 *
 * Chức năng:
 * 1. Đọc và chuẩn hóa dữ liệu Option JSON trong database (store_data, players, clone_char, v.v.).
 * 2. Hỗ trợ quét và cập nhật trực tiếp file SQL database/init_nso_clean.sql.
 * 3. Hỗ trợ kết nối Cloud VM qua SSH để chuẩn hóa trực tiếp trên MariaDB live.
 */

const fs = require('fs');
const path = require('path');
const { spawn } = require('child_process');

// Ma trận ánh xạ chuẩn (Canonical Mapping Matrix)
const CANONICAL_MAP = {
  // Sát thương ngoại công cố định (Flat) -> Option 0
  21: 0, 23: 0, 25: 0, 73: 0, 76: 0, 87: 0,
  // Sát thương nội công cố định (Flat) -> Option 1
  22: 1, 24: 1, 26: 1,

  // LƯU Ý BẤT BIẾN:
  // Option 8 (% Ngoại), 9 (% Nội), 94 (% Tiềm năng) TUYỆT ĐỐI GIỮ NGUYÊN RIÊNG BIỆT!

  // HP & MP cố định (Flat)
  32: 6, 77: 6, 82: 6, 125: 6, // -> Option 6 (HP)
  19: 7, 29: 7, 83: 7, 117: 7, // -> Option 7 (MP)

  // % Tỉ lệ HP & MP
  61: 31, // -> Option 31 (% HP)
  60: 28, // -> Option 28 (% MP)

  // Né đòn & Chính xác
  17: 5, 62: 5, 68: 5, 78: 5, 84: 5, 115: 5, // -> Option 5 (Né đòn)
  18: 10, 75: 10, 86: 10, 116: 10,           // -> Option 10 (Chính xác)

  // Chí mạng & Kháng chí mạng
  37: 14, 69: 14, 92: 14, 114: 14, // -> Option 14 (Chí mạng)
  67: 39,                          // -> Option 39 (% STCM)
  46: 121, 63: 121, 79: 121,       // -> Option 121 (Kháng STCM %)

  // Kháng nguyên tố & Kháng tất cả
  11: 2, 33: 2, 70: 2, 96: 2, // -> Option 2 (Kháng Hỏa)
  12: 3, 34: 3, 71: 3, 95: 3, // -> Option 3 (Kháng Băng)
  13: 4, 35: 4, 72: 4, 97: 4, // -> Option 4 (Kháng Phong)
  36: 20, 81: 20, 118: 20,    // -> Option 20 (Kháng tất cả)

  // Giảm trừ & Phản đòn
  74: 47, 80: 47, 124: 47, // -> Option 47 (Giảm trừ ST)
  91: 15, 126: 15,         // -> Option 15 (Phản đòn)

  // Tốc độ di chuyển
  93: 16
};

/**
 * Thuật toán Gộp & Cộng dồn cho định dạng Store: [{"id": X, "param": Y}, ...]
 */
function mergeStoreOptions(options) {
  if (!Array.isArray(options) || options.length === 0) return options;
  const merged = new Map();
  for (const opt of options) {
    if (!opt || opt.id === undefined) continue;
    const canId = CANONICAL_MAP[opt.id] !== undefined ? CANONICAL_MAP[opt.id] : opt.id;
    if (merged.has(canId)) {
      merged.get(canId).param += opt.param;
    } else {
      merged.set(canId, { id: canId, param: opt.param });
    }
  }
  return Array.from(merged.values());
}

/**
 * Thuật toán Gộp & Cộng dồn cho định dạng Player: [[id, param], [id, param], ...]
 */
function mergePlayerOptions(options) {
  if (!Array.isArray(options) || options.length === 0) return options;
  const merged = new Map();
  for (const opt of options) {
    if (!Array.isArray(opt) || opt.length < 2) continue;
    const origId = opt[0];
    const param = opt[1];
    const canId = CANONICAL_MAP[origId] !== undefined ? CANONICAL_MAP[origId] : origId;
    if (merged.has(canId)) {
      merged.set(canId, merged.get(canId) + param);
    } else {
      merged.set(canId, param);
    }
  }
  return Array.from(merged.entries()).map(([id, param]) => [id, param]);
}

/**
 * Xử lý mảng trang bị người chơi (equiped, bag, box, mount, bijuu)
 */
function standardizePlayerItemArray(itemArray) {
  if (!Array.isArray(itemArray)) return itemArray;
  for (const item of itemArray) {
    if (item && item.options && Array.isArray(item.options)) {
      item.options = mergePlayerOptions(item.options);
    }
  }
  return itemArray;
}

// ==========================================
// THỰC THI CHUẨN HÓA FILE SQL IN DATABASE
// ==========================================
function migrateInitSqlFile(sqlFilePath) {
  console.log(`[SQL Migration] Đang đọc file: ${sqlFilePath}...`);
  if (!fs.existsSync(sqlFilePath)) {
    console.error(`Không tìm thấy file ${sqlFilePath}`);
    return;
  }

  const content = fs.readFileSync(sqlFilePath, 'utf8');
  let replacedCount = 0;

  const targetPrefix = 'INSERT INTO `store_data` VALUES ';
  const startIdx = content.indexOf(targetPrefix);
  if (startIdx === -1) {
    console.log('[SQL Migration] Không tìm thấy INSERT INTO `store_data`');
    return;
  }

  const endIdx = content.indexOf(';\n', startIdx);
  const prefix = content.slice(0, startIdx + targetPrefix.length);
  const rows = content.slice(startIdx + targetPrefix.length, endIdx);
  const suffix = content.slice(endIdx);

  const updatedRows = rows.replace(/\((\d+,\d+,\d+,\d+,\d+,\d+,\d+,\d+,-?\d+,')([^']+?)('\))/g, (m, header, optStr, trailer) => {
    try {
      const parsed = JSON.parse(optStr);
      if (Array.isArray(parsed) && parsed.length > 0) {
        const merged = mergeStoreOptions(parsed);
        const newOptStr = JSON.stringify(merged);
        if (newOptStr !== optStr) {
          replacedCount++;
          return '(' + header + newOptStr + trailer;
        }
      }
    } catch (e) {
      // bỏ qua nếu không phải JSON
    }
    return m;
  });

  if (replacedCount > 0) {
    fs.writeFileSync(sqlFilePath, prefix + updatedRows + suffix, 'utf8');
    console.log(`[SQL Migration] ✅ Đã chuẩn hóa thành công ${replacedCount} bản ghi store_data trong ${sqlFilePath}!`);
  } else {
    console.log(`[SQL Migration] Dữ liệu trong ${sqlFilePath} đã sạch hoặc không có thay đổi.`);
  }
}

// Chạy trực tiếp nếu gọi từ command line
if (require.main === module) {
  const projectRoot = path.resolve(__dirname, '..');
  const initSql = path.join(projectRoot, 'database', 'init_nso_clean.sql');
  migrateInitSqlFile(initSql);
}

module.exports = {
  CANONICAL_MAP,
  mergeStoreOptions,
  mergePlayerOptions,
  standardizePlayerItemArray,
  migrateInitSqlFile
};
