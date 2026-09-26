-- ========================================================
-- CLEANUP SCRIPT: RESET ALL USER & DYNAMIC PLAYER DATA
-- ZERO USERS / ZERO PLAYERS (100% PRISTINE CLEAN STATE)
-- PRESERVES ALL GAME METADATA, MAPS, SKILLS, ITEMS, SHOPS
-- ========================================================

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Accounts, Players, Clans & Characters
TRUNCATE TABLE `users`;
TRUNCATE TABLE `players`;
TRUNCATE TABLE `clone_char`;
TRUNCATE TABLE `clan`;
TRUNCATE TABLE `clan_member`;

-- 2. Economy, Auction Market & Transactions
TRUNCATE TABLE `shinwa`;
TRUNCATE TABLE `transactions`;
TRUNCATE TABLE `biendongsodu`;
TRUNCATE TABLE `admin_buff_history`;

-- 3. Payments & Chargings
TRUNCATE TABLE `chargings`;
TRUNCATE TABLE `chargings2`;
TRUNCATE TABLE `napcard`;
TRUNCATE TABLE `napatm`;
TRUNCATE TABLE `deposit`;
TRUNCATE TABLE `ctv_money`;
TRUNCATE TABLE `momo_accounts`;

-- 4. Events, Rewards & History
TRUNCATE TABLE `event_points`;
TRUNCATE TABLE `gift_code_histories`;
TRUNCATE TABLE `giveaways`;
TRUNCATE TABLE `reward_point_history`;
TRUNCATE TABLE `ranking_list`;
TRUNCATE TABLE `match_list`;

-- 5. Logs & Tokens
TRUNCATE TABLE `user_logs`;
TRUNCATE TABLE `login_histories`;
TRUNCATE TABLE `comments`;
TRUNCATE TABLE `sql_backup`;

-- 6. Registration OTPs (Authorization Table)
CREATE TABLE IF NOT EXISTS `registration_otps` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `code` VARCHAR(6) NOT NULL UNIQUE,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `expires_at` DATETIME NOT NULL,
  `used` TINYINT DEFAULT 0,
  `used_by` VARCHAR(50) DEFAULT NULL,
  `used_at` DATETIME DEFAULT NULL,
  INDEX `idx_code` (`code`),
  INDEX `idx_expires` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

TRUNCATE TABLE `registration_otps`;

SET FOREIGN_KEY_CHECKS = 1;
