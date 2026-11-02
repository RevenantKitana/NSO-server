const fs = require('fs');
const path = require('path');

const projectRoot = path.resolve(__dirname, '..');
const srcCleanSql = path.join(projectRoot, 'database', 'init_nso_clean.sql');
const dstV2Sql = path.join(projectRoot, 'database', 'init_nso_v2.sql');

console.log('Reading source SQL:', srcCleanSql);
const sqlContent = fs.readFileSync(srcCleanSql, 'utf8');

// List of static template tables to retain verbatim from legacy
const KEEPER_TABLES = new Set([
  'clazz',
  'effect',
  'effect_data',
  'effect_data_auto',
  'item',
  'map',
  'monster',
  'nj_arrow',
  'nj_effect',
  'nj_image',
  'nj_part',
  'nj_skill',
  'npc',
  'skill',
  'skill_option',
  'skill_template',
  'store_data',
  'store_upgrade',
  'stores',
  'task',
  'task_template'
]);

// 31 Standardized Options for NSO V2
const STANDARDIZED_OPTIONS = [
  { id: 0, type: 0, name: "Tấn công: +#" },
  { id: 1, type: 0, name: "Sát thương chuẩn: +#" },
  { id: 2, type: 0, name: "Vận công: +#%" },
  { id: 3, type: 0, name: "Chính xác: +#" },
  { id: 4, type: 0, name: "Né đòn: +#" },
  { id: 5, type: 0, name: "Chí mạng: +#" },
  { id: 6, type: 1, name: "Sát thương chí mạng: +#%" },
  { id: 7, type: 0, name: "Giảm trừ sát thương: +#" },
  { id: 8, type: 1, name: "HP tối đa: +#" },
  { id: 9, type: 1, name: "HP tối đa: +#%" },
  { id: 10, type: 1, name: "MP tối đa: +#" },
  { id: 11, type: 1, name: "MP tối đa: +#%" },
  { id: 12, type: 2, name: "Kháng hỏa: +#" },
  { id: 13, type: 2, name: "Kháng băng: +#" },
  { id: 14, type: 2, name: "Kháng phong: +#" },
  { id: 15, type: 2, name: "Kháng tất cả: +#" },
  { id: 16, type: 2, name: "Kháng tất cả: +#%" },
  { id: 17, type: 2, name: "Phản đòn cận chiến: +#" },
  { id: 18, type: 0, name: "Xuyên kháng: +#" },
  { id: 19, type: 0, name: "Xuyên kháng: +#%" },
  { id: 20, type: 0, name: "Hồi HP khi đánh: +#" },
  { id: 21, type: 0, name: "Hút HP khi đánh: +#%" },
  { id: 22, type: 0, name: "Hồi MP khi đánh: +#" },
  { id: 23, type: 0, name: "Hút MP khi đánh: +#%" },
  { id: 24, type: 3, name: "Mỗi 5 giây phục hồi HP: +#" },
  { id: 25, type: 3, name: "Mỗi 5 giây phục hồi MP: +#" },
  { id: 26, type: 2, name: "Tốc độ di chuyển: +#" },
  { id: 27, type: 7, name: "Giảm thời gian Bỏng: -#%" },
  { id: 28, type: 7, name: "Giảm thời gian Đóng Băng: -#%" },
  { id: 29, type: 7, name: "Giảm thời gian Choáng: -#%" },
  { id: 30, type: 8, name: "Tăng kinh nghiệm đánh quái: +#%" },
  { id: 31, type: 2, name: "Kháng chí mạng: +#" },
  { id: 32, type: 2, name: "Giảm sát thương chí mạng: +#%" },
  { id: 33, type: 2, name: "Miễn thương: +#%" }
];

console.log('Extracting keeper tables from legacy SQL...');

// Split SQL content into table sections
// Tables are delimited by "-- Table structure for table `xyz`"
const tableRegex = /--\s+Table structure for table `([^`]+)`([\s\S]*?)(?=(?:--\s+Table structure for table `)|$)/g;

let match;
const extractedKeeperBlocks = [];

while ((match = tableRegex.exec(sqlContent)) !== null) {
  const tableName = match[1];
  const tableBlock = match[2];

  if (KEEPER_TABLES.has(tableName)) {
    console.log(`  + Extracted template table: ${tableName}`);
    extractedKeeperBlocks.push(`--\n-- Table structure and data for template: \`${tableName}\`\n--\n` + tableBlock.trim());
  }
}

console.log(`Extracted ${extractedKeeperBlocks.length} keeper tables.`);

// Build the V2 SQL file
const v2Header = `-- =============================================================================
-- NSO SERVER V2 - CLEAN STREAMLINED DATABASE SCHEMA (nso_v2)
-- Architecture: Clean Architecture + Normalized Relational Schema
-- Standard: UTF8MB4 Unicode CI, Strict Foreign Keys, Zero JSON Garbage
-- Generated for: Milestone 1 (v0.1.0-alpha) & Beyond
-- =============================================================================

CREATE DATABASE IF NOT EXISTS \`nso_v2\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE \`nso_v2\`;

SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET NAMES utf8mb4;

-- =============================================================================
-- 1. VÙNG NGƯỜI CHƠI & DỮ LIỆU ĐỘNG (DYNAMIC PLAYER STATE)
-- =============================================================================

DROP TABLE IF EXISTS \`social_relationships\`;
DROP TABLE IF EXISTS \`clan_members\`;
DROP TABLE IF EXISTS \`clans\`;
DROP TABLE IF EXISTS \`character_skills\`;
DROP TABLE IF EXISTS \`character_items\`;
DROP TABLE IF EXISTS \`characters\`;
DROP TABLE IF EXISTS \`users\`;

-- 1.1. Bảng Users (Tài khoản)
CREATE TABLE \`users\` (
    \`id\` INT AUTO_INCREMENT PRIMARY KEY,
    \`username\` VARCHAR(32) NOT NULL UNIQUE,
    \`password\` VARCHAR(255) NOT NULL,
    \`role\` TINYINT NOT NULL DEFAULT 0, -- 0: User, 1: Mod, 99: Admin
    \`status\` TINYINT NOT NULL DEFAULT 1, -- 1: Hoạt động, 0: Bị khóa
    \`luong\` INT NOT NULL DEFAULT 0,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    \`updated_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX \`idx_username\` (\`username\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 1.2. Bảng Characters (Nhân vật)
CREATE TABLE \`characters\` (
    \`id\` INT AUTO_INCREMENT PRIMARY KEY,
    \`user_id\` INT NOT NULL,
    \`name\` VARCHAR(20) NOT NULL UNIQUE,
    \`gender\` TINYINT NOT NULL DEFAULT 1, -- 0: Nữ, 1: Nam
    \`class_id\` TINYINT NOT NULL DEFAULT 0, -- 0: Chưa vào phái, 1: Kiếm, 2: Tiêu, 3: Kunai, 4: Cung, 5: Đao, 6: Quạt
    \`level\` INT NOT NULL DEFAULT 1,
    \`exp\` BIGINT NOT NULL DEFAULT 0,
    \`potential_point\` INT NOT NULL DEFAULT 0,
    \`skill_point\` INT NOT NULL DEFAULT 0,
    \`potential_str\` INT NOT NULL DEFAULT 15,
    \`potential_agi\` INT NOT NULL DEFAULT 5,
    \`potential_vit\` INT NOT NULL DEFAULT 5,
    \`potential_spi\` INT NOT NULL DEFAULT 5,
    \`xu\` BIGINT NOT NULL DEFAULT 0,
    \`yen\` BIGINT NOT NULL DEFAULT 0,
    \`xu_in_box\` BIGINT NOT NULL DEFAULT 0,
    \`bag_slots\` TINYINT NOT NULL DEFAULT 30,
    \`box_slots\` TINYINT NOT NULL DEFAULT 30,
    \`map_id\` INT NOT NULL DEFAULT 22, -- Mặc định: Làng Tone
    \`map_x\` INT NOT NULL DEFAULT 1741,
    \`map_y\` INT NOT NULL DEFAULT 264,
    \`head_id\` SMALLINT NOT NULL DEFAULT 14,
    \`pk_point\` INT NOT NULL DEFAULT 0,
    \`online\` TINYINT NOT NULL DEFAULT 0,
    \`last_login\` TIMESTAMP NULL,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT \`fk_char_user\` FOREIGN KEY (\`user_id\`) REFERENCES \`users\` (\`id\`) ON DELETE CASCADE,
    INDEX \`idx_char_name\` (\`name\`),
    INDEX \`idx_user_chars\` (\`user_id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 1.3. Bảng Character Items (Trang bị & Túi đồ chuẩn {a; x; t; ...})
CREATE TABLE \`character_items\` (
    \`id\` BIGINT AUTO_INCREMENT PRIMARY KEY,
    \`character_id\` INT NOT NULL,
    \`template_id\` INT NOT NULL,                 -- [a] Template ID
    \`upgrade_level\` TINYINT NOT NULL DEFAULT 0,  -- [x] Cường hóa +0 đến +16
    \`tier\` TINYINT NOT NULL DEFAULT 3,           -- [t] Phẩm chất A1 -> A5 (1 -> 5, mặc định A3 Thượng phẩm)
    \`location\` ENUM('BAG', 'BOX', 'EQUIP', 'MOUNT', 'FASHION') NOT NULL,
    \`slot_index\` SMALLINT NOT NULL,
    \`quantity\` INT NOT NULL DEFAULT 1,
    \`sys\` TINYINT NOT NULL DEFAULT 0,            -- Hệ nguyên tố (1: Hỏa, 2: Băng, 3: Phong)
    \`flags\` INT UNSIGNED NOT NULL DEFAULT 0,     -- Bitmask: 0x01: Khóa, 0x02: Giám định, 0x04: Sự kiện...
    \`yen_price\` INT NOT NULL DEFAULT 0,
    \`expires_at\` BIGINT NOT NULL DEFAULT -1,     -- -1: Vĩnh viễn, hoặc timestamp ms
    \`extra_data\` JSON DEFAULT NULL,              -- Sparse Payload: Khảm ngọc, tên thợ rèn... (NULL = 0 byte)
    CONSTRAINT \`fk_item_char\` FOREIGN KEY (\`character_id\`) REFERENCES \`characters\` (\`id\`) ON DELETE CASCADE,
    INDEX \`idx_char_loc_slot\` (\`character_id\`, \`location\`, \`slot_index\`),
    INDEX \`idx_template\` (\`template_id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 1.4. Bảng Character Skills (Kỹ năng nhân vật)
CREATE TABLE \`character_skills\` (
    \`character_id\` INT NOT NULL,
    \`skill_id\` INT NOT NULL,
    \`point\` TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (\`character_id\`, \`skill_id\`),
    CONSTRAINT \`fk_skill_char\` FOREIGN KEY (\`character_id\`) REFERENCES \`characters\` (\`id\`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 1.5. Bảng Clans & Clan Members (Gia tộc)
CREATE TABLE \`clans\` (
    \`id\` INT AUTO_INCREMENT PRIMARY KEY,
    \`name\` VARCHAR(30) NOT NULL UNIQUE,
    \`level\` INT NOT NULL DEFAULT 1,
    \`exp\` BIGINT NOT NULL DEFAULT 0,
    \`leader_id\` INT NOT NULL,
    \`coin\` BIGINT NOT NULL DEFAULT 0,
    \`notice\` TEXT NULL,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE \`clan_members\` (
    \`clan_id\` INT NOT NULL,
    \`character_id\` INT NOT NULL,
    \`role\` TINYINT NOT NULL DEFAULT 0, -- 0: Dự bị, 1: Thành viên, 2: Trưởng lão, 3: Tộc phó, 4: Tộc trưởng
    \`contribute_coin\` BIGINT NOT NULL DEFAULT 0,
    \`joined_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (\`clan_id\`, \`character_id\`),
    CONSTRAINT \`fk_clan_mem_clan\` FOREIGN KEY (\`clan_id\`) REFERENCES \`clans\` (\`id\`) ON DELETE CASCADE,
    CONSTRAINT \`fk_clan_mem_char\` FOREIGN KEY (\`character_id\`) REFERENCES \`characters\` (\`id\`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 1.6. Bảng Social Relationships (Bạn bè & Thù oán)
CREATE TABLE \`social_relationships\` (
    \`character_id\` INT NOT NULL,
    \`target_id\` INT NOT NULL,
    \`type\` ENUM('FRIEND', 'ENEMY') NOT NULL,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (\`character_id\`, \`target_id\`, \`type\`),
    CONSTRAINT \`fk_rel_char\` FOREIGN KEY (\`character_id\`) REFERENCES \`characters\` (\`id\`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================================================
-- 2. VÙNG KINH TẾ, VẬN HÀNH & HỆ THỐNG (SYSTEM & ECONOMY)
-- =============================================================================

DROP TABLE IF EXISTS \`game_logs\`;
DROP TABLE IF EXISTS \`giftcode_histories\`;
DROP TABLE IF EXISTS \`giftcodes\`;
DROP TABLE IF EXISTS \`stalls_auction\`;

-- 2.1. Bảng Chợ Đấu Giá (Stalls Auction)
CREATE TABLE \`stalls_auction\` (
    \`id\` BIGINT AUTO_INCREMENT PRIMARY KEY,
    \`seller_id\` INT NOT NULL,
    \`item_data\` JSON NOT NULL,
    \`price\` BIGINT NOT NULL,
    \`currency\` ENUM('XU', 'LUONG') NOT NULL DEFAULT 'XU',
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    \`expires_at\` TIMESTAMP NULL,
    INDEX \`idx_seller\` (\`seller_id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2.2. Bảng Giftcodes & Lịch sử
CREATE TABLE \`giftcodes\` (
    \`id\` INT AUTO_INCREMENT PRIMARY KEY,
    \`code\` VARCHAR(50) NOT NULL UNIQUE,
    \`rewards_json\` JSON NOT NULL,
    \`usage_limit\` INT NOT NULL DEFAULT 1,
    \`used_count\` INT NOT NULL DEFAULT 0,
    \`expired_at\` TIMESTAMP NULL,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE \`giftcode_histories\` (
    \`giftcode_id\` INT NOT NULL,
    \`user_id\` INT NOT NULL,
    \`character_id\` INT NOT NULL,
    \`claimed_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (\`giftcode_id\`, \`user_id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2.3. Bảng Game Logs (Kiểm soát giao dịch & biến động lớn)
CREATE TABLE \`game_logs\` (
    \`id\` BIGINT AUTO_INCREMENT PRIMARY KEY,
    \`character_id\` INT NULL,
    \`action_type\` VARCHAR(50) NOT NULL,
    \`details\` TEXT NOT NULL,
    \`created_at\` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX \`idx_char_action\` (\`character_id\`, \`action_type\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================================================
-- 3. BẢNG TỪ ĐIỂN ITEM OPTIONS CHUẨN HÓA (31 CHỈ SỐ GỐC)
-- =============================================================================

DROP TABLE IF EXISTS \`item_option\`;
CREATE TABLE \`item_option\` (
    \`id\` INT NOT NULL,
    \`type\` INT NOT NULL,
    \`name\` VARCHAR(500) NOT NULL,
    PRIMARY KEY (\`id\`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO \`item_option\` (\`id\`, \`type\`, \`name\`) VALUES
${STANDARDIZED_OPTIONS.map(opt => `(${opt.id}, ${opt.type}, '${opt.name}')`).join(',\n')};

-- =============================================================================
-- 4. DỮ LIỆU SEED MẪU CHO MILESTONE 1 (ADMIN & STARTER TEST)
-- =============================================================================

-- Tài khoản Admin & Tester
INSERT INTO \`users\` (\`id\`, \`username\`, \`password\`, \`role\`, \`status\`, \`luong\`) VALUES
(1, 'admin', 'admin123', 99, 1, 100000),
(2, 'player1', '123456', 0, 1, 1000),
(3, 'player2', '123456', 0, 1, 1000);

-- Nhân vật Test tại Làng Tone (map_id = 22, x = 1741, y = 264)
INSERT INTO \`characters\` (\`id\`, \`user_id\`, \`name\`, \`gender\`, \`class_id\`, \`level\`, \`exp\`, \`xu\`, \`yen\`, \`map_id\`, \`map_x\`, \`map_y\`) VALUES
(1, 1, 'admin_v2', 1, 0, 10, 5000, 1000000, 500000, 22, 1741, 264),
(2, 2, 'ninja_red', 1, 1, 1, 0, 10000, 5000, 22, 1750, 264),
(3, 3, 'ninja_blue', 0, 2, 1, 0, 10000, 5000, 22, 1760, 264);

-- Starter Items cho nhân vật admin_v2 (Kiếm gỗ tập sự, bình máu, bình mana)
-- Áp dụng chuẩn {a; x; t; location; slot}
INSERT INTO \`character_items\` (\`character_id\`, \`template_id\`, \`upgrade_level\`, \`tier\`, \`location\`, \`slot_index\`, \`quantity\`) VALUES
(1, 94, 0, 3, 'BAG', 0, 1),  -- Kiếm gỗ cấp 1 (A3 Thượng phẩm)
(1, 13, 0, 3, 'BAG', 1, 50), -- Bình HP sơ cấp (Số lượng 50)
(1, 14, 0, 3, 'BAG', 2, 50); -- Bình MP sơ cấp (Số lượng 50)

-- =============================================================================
-- 5. KẾ THỪA CÁC BẢNG TEMPLATE GAME TĨNH (METADATA GỐC)
-- =============================================================================
`;

const fullSqlOutput = v2Header + '\n\n' + extractedKeeperBlocks.join('\n\n') + '\n\nSET FOREIGN_KEY_CHECKS = 1;\n';

fs.writeFileSync(dstV2Sql, fullSqlOutput, 'utf8');
console.log(`\nSuccessfully created: ${dstV2Sql}`);
console.log(`File size: ${(fs.statSync(dstV2Sql).size / 1024 / 1024).toFixed(2)} MB`);

