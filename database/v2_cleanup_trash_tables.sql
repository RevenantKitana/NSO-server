-- ============================================================
-- V2 DATABASE CLEANUP: REMOVE UNUSED TRASH TABLES (SPEC 02)
-- Target Database: nso_v2 ONLY
-- Safety Rule: NEVER run on nso_test / production DB
-- ============================================================

-- 1. Bảng tàn tích của Web nạp thẻ / giao dịch thứ 3
DROP TABLE IF EXISTS `transactions`;

-- 2. Bảng logs và lịch sử đăng nhập web thừa
DROP TABLE IF EXISTS `user_logs`;
DROP TABLE IF EXISTS `login_histories`;
DROP TABLE IF EXISTS `admin_buff_history`;
DROP TABLE IF EXISTS `reward_point_history`;

-- 3. Bảng backup tạm thời trong quá khứ
DROP TABLE IF EXISTS `item_bk`;
DROP TABLE IF EXISTS `item_check`;

-- 4. Bảng cấu hình Web CMS / Laravel không thuộc Game Core
DROP TABLE IF EXISTS `settings`;
DROP TABLE IF EXISTS `servers`;

-- 5. Bảng groups cũ (Party hiện tại quản lý hoàn toàn in-memory)
DROP TABLE IF EXISTS `groups`;

-- Xác nhận danh sách bảng còn lại
SHOW TABLES;
