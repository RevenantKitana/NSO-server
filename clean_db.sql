-- ========================================================
-- CLEANUP SCRIPT: RESET ALL USER & DYNAMIC PLAYER DATA
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
TRUNCATE TABLE `personal_access_tokens`;
TRUNCATE TABLE `comments`;
TRUNCATE TABLE `sql_backup`;

SET FOREIGN_KEY_CHECKS = 1;

-- 6. Insert Default Admin Account (user: admin / pass: admin)
INSERT INTO `users` (
  `id`, `username`, `password`, `activated`, `balance`, `luong`, `tongnap`, 
  `point_vip`, `role`, `status`, `online`, `nap`, `tanthu`, `level`, 
  `created_at`, `updated_at`
) VALUES (
  1, 'admin', '$2a$12$5DRhFB.ltXezsfQaGSHRiO0zJaK6TSW5Eiv6Nv0m9dzGFoVA9NYVm', 
  1, 1000000, 1000000, 0, 0, 1, 0, 0, 0, 1, 'admin', NOW(), NOW()
);
