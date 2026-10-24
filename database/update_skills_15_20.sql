-- ==========================================================================
-- CẬP NHẬT TÁI CÂN BẰNG KỸ NĂNG CẤP 15 (6 PHÁI) & KỸ NĂNG CẤP 20 QUẠT
-- Ngày thực thi: 2026-10-24T09:53:33.003Z
-- ==========================================================================

-- 1. Bổ sung các mẫu chỉ số mới vào bảng `skill_option`
INSERT INTO `skill_option` (`id`, `name`) VALUES (72, 'HP tối đa: +#') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (73, 'MP tối đa: +#') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (74, 'Mỗi 0.5 giây phục hồi thêm: +#% MP tối đa') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (75, 'Hồi phục đồng đội tăng thêm: +#% hiệu quả') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (76, 'Tiêu hao thêm MP: +#% tối đa') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

-- 2. Cập nhật chi tiết từng cấp độ của kỹ năng trong bảng `skill`

-- Skill Template ID: 2
UPDATE `skill` SET `options` = '[{"param":0,"id":19},{"param":0,"id":0},{"param":0,"id":13},{"param":0,"id":16}]' WHERE `template_id` = 2 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":19,"param":35},{"id":0,"param":3},{"id":13,"param":20},{"id":16,"param":10}]' WHERE `template_id` = 2 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":19,"param":64},{"id":0,"param":5},{"id":13,"param":32},{"id":16,"param":16}]' WHERE `template_id` = 2 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":19,"param":93},{"id":0,"param":8},{"id":13,"param":44},{"id":16,"param":22}]' WHERE `template_id` = 2 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":19,"param":122},{"id":0,"param":10},{"id":13,"param":56},{"id":16,"param":28}]' WHERE `template_id` = 2 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":19,"param":151},{"id":0,"param":13},{"id":13,"param":68},{"id":16,"param":34}]' WHERE `template_id` = 2 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":19,"param":180},{"id":0,"param":15},{"id":13,"param":80},{"id":16,"param":40}]' WHERE `template_id` = 2 AND `point` = 6;

-- Skill Template ID: 11
UPDATE `skill` SET `options` = '[{"param":0,"id":8},{"param":0,"id":13},{"param":0,"id":16},{"param":0,"id":1},{"param":0,"id":14},{"param":0,"id":12}]' WHERE `template_id` = 11 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":8,"param":40},{"id":13,"param":30},{"id":16,"param":15},{"id":1,"param":3},{"id":14,"param":15},{"id":12,"param":15}]' WHERE `template_id` = 11 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":8,"param":92},{"id":13,"param":54},{"id":16,"param":19},{"id":1,"param":5},{"id":14,"param":24},{"id":12,"param":28}]' WHERE `template_id` = 11 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":8,"param":144},{"id":13,"param":78},{"id":16,"param":23},{"id":1,"param":8},{"id":14,"param":33},{"id":12,"param":41}]' WHERE `template_id` = 11 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":8,"param":196},{"id":13,"param":102},{"id":16,"param":27},{"id":1,"param":10},{"id":14,"param":42},{"id":12,"param":54}]' WHERE `template_id` = 11 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":8,"param":248},{"id":13,"param":126},{"id":16,"param":31},{"id":1,"param":13},{"id":14,"param":51},{"id":12,"param":67}]' WHERE `template_id` = 11 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":8,"param":300},{"id":13,"param":150},{"id":16,"param":35},{"id":1,"param":15},{"id":14,"param":60},{"id":12,"param":80}]' WHERE `template_id` = 11 AND `point` = 6;

-- Skill Template ID: 20
UPDATE `skill` SET `options` = '[{"param":0,"id":12},{"param":0,"id":6},{"param":0,"id":16},{"param":0,"id":13},{"param":0,"id":0}]' WHERE `template_id` = 20 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":12,"param":20},{"id":6,"param":40},{"id":16,"param":20},{"id":13,"param":25},{"id":0,"param":4}]' WHERE `template_id` = 20 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":12,"param":44},{"id":6,"param":72},{"id":16,"param":25},{"id":13,"param":40},{"id":0,"param":7}]' WHERE `template_id` = 20 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":12,"param":68},{"id":6,"param":104},{"id":16,"param":30},{"id":13,"param":55},{"id":0,"param":10}]' WHERE `template_id` = 20 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":12,"param":92},{"id":6,"param":136},{"id":16,"param":35},{"id":13,"param":70},{"id":0,"param":14}]' WHERE `template_id` = 20 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":12,"param":116},{"id":6,"param":168},{"id":16,"param":40},{"id":13,"param":85},{"id":0,"param":17}]' WHERE `template_id` = 20 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":12,"param":140},{"id":6,"param":200},{"id":16,"param":45},{"id":13,"param":100},{"id":0,"param":20}]' WHERE `template_id` = 20 AND `point` = 6;

-- Skill Template ID: 29
UPDATE `skill` SET `options` = '[{"param":0,"id":1},{"param":0,"id":14},{"param":0,"id":12},{"param":0,"id":9}]' WHERE `template_id` = 29 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":1,"param":9},{"id":14,"param":20},{"id":12,"param":20},{"id":9,"param":20}]' WHERE `template_id` = 29 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":1,"param":16},{"id":14,"param":36},{"id":12,"param":40},{"id":9,"param":36}]' WHERE `template_id` = 29 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":1,"param":23},{"id":14,"param":52},{"id":12,"param":60},{"id":9,"param":52}]' WHERE `template_id` = 29 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":1,"param":31},{"id":14,"param":68},{"id":12,"param":80},{"id":9,"param":68}]' WHERE `template_id` = 29 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":1,"param":38},{"id":14,"param":84},{"id":12,"param":100},{"id":9,"param":84}]' WHERE `template_id` = 29 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":1,"param":45},{"id":14,"param":100},{"id":12,"param":120},{"id":9,"param":100}]' WHERE `template_id` = 29 AND `point` = 6;

-- Skill Template ID: 38
UPDATE `skill` SET `options` = '[{"param":0,"id":17},{"param":0,"id":15},{"param":0,"id":7},{"param":0,"id":14}]' WHERE `template_id` = 38 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":17,"param":5},{"id":15,"param":40},{"id":7,"param":90},{"id":14,"param":20}]' WHERE `template_id` = 38 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":17,"param":10},{"id":15,"param":72},{"id":7,"param":100},{"id":14,"param":32}]' WHERE `template_id` = 38 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":17,"param":15},{"id":15,"param":104},{"id":7,"param":110},{"id":14,"param":44}]' WHERE `template_id` = 38 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":17,"param":20},{"id":15,"param":136},{"id":7,"param":120},{"id":14,"param":56}]' WHERE `template_id` = 38 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":17,"param":25},{"id":15,"param":168},{"id":7,"param":130},{"id":14,"param":68}]' WHERE `template_id` = 38 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":17,"param":30},{"id":15,"param":200},{"id":7,"param":140},{"id":14,"param":80}]' WHERE `template_id` = 38 AND `point` = 6;

-- Skill Template ID: 47
UPDATE `skill` SET `options` = '[{"param":0,"id":27},{"param":0,"id":74},{"param":0,"id":75},{"param":0,"id":76}]', `cooldown` = 0, `mana_use` = 0 WHERE `template_id` = 47 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":27,"param":30},{"id":74,"param":1},{"id":75,"param":3},{"id":76,"param":3}]', `cooldown` = 10000, `mana_use` = 120 WHERE `template_id` = 47 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":27,"param":54},{"id":74,"param":2},{"id":75,"param":5},{"id":76,"param":4}]', `cooldown` = 11000, `mana_use` = 136 WHERE `template_id` = 47 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":27,"param":78},{"id":74,"param":3},{"id":75,"param":8},{"id":76,"param":5}]', `cooldown` = 12000, `mana_use` = 152 WHERE `template_id` = 47 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":27,"param":102},{"id":74,"param":3},{"id":75,"param":10},{"id":76,"param":6}]', `cooldown` = 13000, `mana_use` = 168 WHERE `template_id` = 47 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":27,"param":126},{"id":74,"param":4},{"id":75,"param":13},{"id":76,"param":7}]', `cooldown` = 14000, `mana_use` = 184 WHERE `template_id` = 47 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":27,"param":150},{"id":74,"param":5},{"id":75,"param":15},{"id":76,"param":8}]', `cooldown` = 15000, `mana_use` = 200 WHERE `template_id` = 47 AND `point` = 6;

-- Skill Template ID: 48
UPDATE `skill` SET `options` = '[{"param":0,"id":29},{"param":0,"id":72},{"param":0,"id":73},{"param":0,"id":1},{"param":0,"id":16},{"param":0,"id":10},{"param":0,"id":12},{"param":0,"id":14}]' WHERE `template_id` = 48 AND `point` = 0;
UPDATE `skill` SET `options` = '[{"id":29,"param":10},{"id":72,"param":100},{"id":73,"param":100},{"id":1,"param":1},{"id":16,"param":10},{"id":10,"param":50},{"id":12,"param":10},{"id":14,"param":10}]' WHERE `template_id` = 48 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":29,"param":14},{"id":72,"param":182},{"id":73,"param":182},{"id":1,"param":2},{"id":16,"param":12},{"id":10,"param":59},{"id":12,"param":14},{"id":14,"param":13}]' WHERE `template_id` = 48 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":29,"param":18},{"id":72,"param":264},{"id":73,"param":264},{"id":1,"param":3},{"id":16,"param":14},{"id":10,"param":68},{"id":12,"param":17},{"id":14,"param":15}]' WHERE `template_id` = 48 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":29,"param":22},{"id":72,"param":345},{"id":73,"param":345},{"id":1,"param":4},{"id":16,"param":15},{"id":10,"param":77},{"id":12,"param":21},{"id":14,"param":18}]' WHERE `template_id` = 48 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":29,"param":26},{"id":72,"param":427},{"id":73,"param":427},{"id":1,"param":5},{"id":16,"param":17},{"id":10,"param":86},{"id":12,"param":25},{"id":14,"param":21}]' WHERE `template_id` = 48 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":29,"param":30},{"id":72,"param":509},{"id":73,"param":509},{"id":1,"param":6},{"id":16,"param":19},{"id":10,"param":95},{"id":12,"param":28},{"id":14,"param":24}]' WHERE `template_id` = 48 AND `point` = 6;
UPDATE `skill` SET `options` = '[{"id":29,"param":35},{"id":72,"param":591},{"id":73,"param":591},{"id":1,"param":6},{"id":16,"param":21},{"id":10,"param":105},{"id":12,"param":32},{"id":14,"param":26}]' WHERE `template_id` = 48 AND `point` = 7;
UPDATE `skill` SET `options` = '[{"id":29,"param":39},{"id":72,"param":673},{"id":73,"param":673},{"id":1,"param":7},{"id":16,"param":23},{"id":10,"param":114},{"id":12,"param":35},{"id":14,"param":29}]' WHERE `template_id` = 48 AND `point` = 8;
UPDATE `skill` SET `options` = '[{"id":29,"param":43},{"id":72,"param":755},{"id":73,"param":755},{"id":1,"param":8},{"id":16,"param":25},{"id":10,"param":123},{"id":12,"param":39},{"id":14,"param":32}]' WHERE `template_id` = 48 AND `point` = 9;
UPDATE `skill` SET `options` = '[{"id":29,"param":47},{"id":72,"param":836},{"id":73,"param":836},{"id":1,"param":9},{"id":16,"param":26},{"id":10,"param":132},{"id":12,"param":43},{"id":14,"param":35}]' WHERE `template_id` = 48 AND `point` = 10;
UPDATE `skill` SET `options` = '[{"id":29,"param":51},{"id":72,"param":918},{"id":73,"param":918},{"id":1,"param":10},{"id":16,"param":28},{"id":10,"param":141},{"id":12,"param":46},{"id":14,"param":37}]' WHERE `template_id` = 48 AND `point` = 11;
UPDATE `skill` SET `options` = '[{"id":29,"param":55},{"id":72,"param":1000},{"id":73,"param":1000},{"id":1,"param":11},{"id":16,"param":30},{"id":10,"param":150},{"id":12,"param":50},{"id":14,"param":40}]' WHERE `template_id` = 48 AND `point` = 12;