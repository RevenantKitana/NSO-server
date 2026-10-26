-- ==========================================================================
-- GIẢM 40% TỐC ĐỘ DI CHUYỂN (OPTION 16) CÁC SKILL BỊ ĐỘNG 15/20 CỦA CÁC PHÁI
-- Phái áp dụng: Kiếm 15 (Skill 2), Tiêu 15 (Skill 11), Kunai 15 (Skill 20), Quạt 20 (Skill 48)
-- (Cung 15 và Đao 15 không có chỉ số tốc độ di chuyển)
-- ==========================================================================

SET NAMES utf8mb4;

-- Phái Kiếm - Skill 2: Chiêu Amagedon (Lv 15)
UPDATE `skill` SET `options` = '[{"id":19,"param":35},{"id":0,"param":3},{"id":13,"param":20},{"id":16,"param":6}]' WHERE `template_id` = 2 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":19,"param":64},{"id":0,"param":5},{"id":13,"param":32},{"id":16,"param":10}]' WHERE `template_id` = 2 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":19,"param":93},{"id":0,"param":8},{"id":13,"param":44},{"id":16,"param":13}]' WHERE `template_id` = 2 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":19,"param":122},{"id":0,"param":10},{"id":13,"param":56},{"id":16,"param":17}]' WHERE `template_id` = 2 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":19,"param":151},{"id":0,"param":13},{"id":13,"param":68},{"id":16,"param":20}]' WHERE `template_id` = 2 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":19,"param":180},{"id":0,"param":15},{"id":13,"param":80},{"id":16,"param":24}]' WHERE `template_id` = 2 AND `point` = 6;

-- Phái Tiêu - Skill 11: Chiêu Itotama (Lv 15)
UPDATE `skill` SET `options` = '[{"id":8,"param":40},{"id":13,"param":30},{"id":16,"param":9},{"id":1,"param":3},{"id":14,"param":15},{"id":12,"param":15}]' WHERE `template_id` = 11 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":8,"param":92},{"id":13,"param":54},{"id":16,"param":11},{"id":1,"param":5},{"id":14,"param":24},{"id":12,"param":28}]' WHERE `template_id` = 11 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":8,"param":144},{"id":13,"param":78},{"id":16,"param":14},{"id":1,"param":8},{"id":14,"param":33},{"id":12,"param":41}]' WHERE `template_id` = 11 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":8,"param":196},{"id":13,"param":102},{"id":16,"param":16},{"id":1,"param":10},{"id":14,"param":42},{"id":12,"param":54}]' WHERE `template_id` = 11 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":8,"param":248},{"id":13,"param":126},{"id":16,"param":19},{"id":1,"param":13},{"id":14,"param":51},{"id":12,"param":67}]' WHERE `template_id` = 11 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":8,"param":300},{"id":13,"param":150},{"id":16,"param":21},{"id":1,"param":15},{"id":14,"param":60},{"id":12,"param":80}]' WHERE `template_id` = 11 AND `point` = 6;

-- Phái Kunai - Skill 20: Chiêu Akaiame (Lv 15)
UPDATE `skill` SET `options` = '[{"id":12,"param":20},{"id":6,"param":40},{"id":16,"param":12},{"id":13,"param":25},{"id":0,"param":4}]' WHERE `template_id` = 20 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":12,"param":44},{"id":6,"param":72},{"id":16,"param":15},{"id":13,"param":40},{"id":0,"param":7}]' WHERE `template_id` = 20 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":12,"param":68},{"id":6,"param":104},{"id":16,"param":18},{"id":13,"param":55},{"id":0,"param":10}]' WHERE `template_id` = 20 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":12,"param":92},{"id":6,"param":136},{"id":16,"param":21},{"id":13,"param":70},{"id":0,"param":14}]' WHERE `template_id` = 20 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":12,"param":116},{"id":6,"param":168},{"id":16,"param":24},{"id":13,"param":85},{"id":0,"param":17}]' WHERE `template_id` = 20 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":12,"param":140},{"id":6,"param":200},{"id":16,"param":27},{"id":13,"param":100},{"id":0,"param":20}]' WHERE `template_id` = 20 AND `point` = 6;

-- Phái Quạt - Skill 48: Chiêu Oouchia (Lv 20)
UPDATE `skill` SET `options` = '[{"id":29,"param":10},{"id":72,"param":100},{"id":73,"param":100},{"id":1,"param":1},{"id":16,"param":6},{"id":10,"param":50},{"id":12,"param":10},{"id":14,"param":10}]' WHERE `template_id` = 48 AND `point` = 1;
UPDATE `skill` SET `options` = '[{"id":29,"param":14},{"id":72,"param":182},{"id":73,"param":182},{"id":1,"param":2},{"id":16,"param":7},{"id":10,"param":59},{"id":12,"param":14},{"id":14,"param":13}]' WHERE `template_id` = 48 AND `point` = 2;
UPDATE `skill` SET `options` = '[{"id":29,"param":18},{"id":72,"param":264},{"id":73,"param":264},{"id":1,"param":3},{"id":16,"param":8},{"id":10,"param":68},{"id":12,"param":17},{"id":14,"param":15}]' WHERE `template_id` = 48 AND `point` = 3;
UPDATE `skill` SET `options` = '[{"id":29,"param":22},{"id":72,"param":345},{"id":73,"param":345},{"id":1,"param":4},{"id":16,"param":9},{"id":10,"param":77},{"id":12,"param":21},{"id":14,"param":18}]' WHERE `template_id` = 48 AND `point` = 4;
UPDATE `skill` SET `options` = '[{"id":29,"param":26},{"id":72,"param":427},{"id":73,"param":427},{"id":1,"param":5},{"id":16,"param":10},{"id":10,"param":86},{"id":12,"param":25},{"id":14,"param":21}]' WHERE `template_id` = 48 AND `point` = 5;
UPDATE `skill` SET `options` = '[{"id":29,"param":30},{"id":72,"param":509},{"id":73,"param":509},{"id":1,"param":6},{"id":16,"param":11},{"id":10,"param":95},{"id":12,"param":28},{"id":14,"param":24}]' WHERE `template_id` = 48 AND `point` = 6;
UPDATE `skill` SET `options` = '[{"id":29,"param":35},{"id":72,"param":591},{"id":73,"param":591},{"id":1,"param":6},{"id":16,"param":13},{"id":10,"param":105},{"id":12,"param":32},{"id":14,"param":26}]' WHERE `template_id` = 48 AND `point` = 7;
UPDATE `skill` SET `options` = '[{"id":29,"param":39},{"id":72,"param":673},{"id":73,"param":673},{"id":1,"param":7},{"id":16,"param":14},{"id":10,"param":114},{"id":12,"param":35},{"id":14,"param":29}]' WHERE `template_id` = 48 AND `point` = 8;
UPDATE `skill` SET `options` = '[{"id":29,"param":43},{"id":72,"param":755},{"id":73,"param":755},{"id":1,"param":8},{"id":16,"param":15},{"id":10,"param":123},{"id":12,"param":39},{"id":14,"param":32}]' WHERE `template_id` = 48 AND `point` = 9;
UPDATE `skill` SET `options` = '[{"id":29,"param":47},{"id":72,"param":836},{"id":73,"param":836},{"id":1,"param":9},{"id":16,"param":16},{"id":10,"param":132},{"id":12,"param":43},{"id":14,"param":35}]' WHERE `template_id` = 48 AND `point` = 10;
UPDATE `skill` SET `options` = '[{"id":29,"param":51},{"id":72,"param":918},{"id":73,"param":918},{"id":1,"param":10},{"id":16,"param":17},{"id":10,"param":141},{"id":12,"param":46},{"id":14,"param":37}]' WHERE `template_id` = 48 AND `point` = 11;
UPDATE `skill` SET `options` = '[{"id":29,"param":55},{"id":72,"param":1000},{"id":73,"param":1000},{"id":1,"param":11},{"id":16,"param":18},{"id":10,"param":150},{"id":12,"param":50},{"id":14,"param":40}]' WHERE `template_id` = 48 AND `point` = 12;

-- Cập nhật sách kỹ năng Quạt 20 trong store_data
UPDATE `store_data` SET `options` = '[{"id":29,"param":10},{"id":72,"param":100},{"id":73,"param":100},{"id":1,"param":1},{"id":16,"param":6},{"id":10,"param":50},{"id":12,"param":10},{"id":14,"param":10}]' WHERE `item_id` = 48;

