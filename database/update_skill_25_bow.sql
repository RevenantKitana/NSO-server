-- Cập nhật Chiêu 25 Cung (Sogekihei - ID 31)
-- Hiệu suất chống đỡ / Giảm sát thương mana: 10% - 40% | Hồi chiêu: 60s - 40s | Thời gian hiệu lực: 45s - 60s

-- 1. Thêm Option 85 (Thời gian hiệu lực) và Option 84 (Tỷ lệ tiêu hao MP)
INSERT INTO `skill_option` (`id`, `name`) 
VALUES (85, 'Thời gian hiệu lực: # giây') 
ON DUPLICATE KEY UPDATE `name` = 'Thời gian hiệu lực: # giây';

INSERT INTO `skill_option` (`id`, `name`) 
VALUES (84, 'Tỷ lệ tiêu hao: #% MP theo sát thương') 
ON DUPLICATE KEY UPDATE `name` = 'Tỷ lệ tiêu hao: #% MP theo sát thương';

-- 2. Chuẩn hóa Option 30 theo hiệu suất chống đỡ
UPDATE `skill_option` 
SET `name` = 'Khi MP trên 10%, chống đỡ: +#% sát thương' 
WHERE `id` = 30;

-- 3. Rút gọn mô tả đầu của kỹ năng
UPDATE `skill_template` 
SET `description` = 'Dùng MP tạo khiên hấp thụ sát thương nhận vào.' 
WHERE `id` = 31;

-- 4. Cập nhật bảng skill: ẩn 'Ảnh hưởng' và 'Phạm vi' (max_fight = 0, dx = 0, dy = 0)
-- Point 1: Chống đỡ 10%, Tỷ lệ 200%, Hiệu lực 45s, Hồi chiêu 60s
-- Point 2: Chống đỡ 16%, Tỷ lệ 180%, Hiệu lực 48s, Hồi chiêu 56s
-- Point 3: Chống đỡ 22%, Tỷ lệ 160%, Hiệu lực 51s, Hồi chiêu 52s
-- Point 4: Chống đỡ 28%, Tỷ lệ 140%, Hiệu lực 54s, Hồi chiêu 48s
-- Point 5: Chống đỡ 34%, Tỷ lệ 120%, Hiệu lực 57s, Hồi chiêu 44s
-- Point 6: Chống đỡ 40%, Tỷ lệ 100%, Hiệu lực 60s, Hồi chiêu 40s

UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 0, `mana_use` = 0, `options` = '[{"param":0,"id":30},{"param":0,"id":84},{"param":0,"id":85}]' WHERE `template_id` = 31 AND `point` = 0;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 60000, `mana_use` = 100, `options` = '[{"param":10,"id":30},{"param":200,"id":84},{"param":45,"id":85}]' WHERE `template_id` = 31 AND `point` = 1;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 56000, `mana_use` = 110, `options` = '[{"param":16,"id":30},{"param":180,"id":84},{"param":48,"id":85}]' WHERE `template_id` = 31 AND `point` = 2;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 52000, `mana_use` = 120, `options` = '[{"param":22,"id":30},{"param":160,"id":84},{"param":51,"id":85}]' WHERE `template_id` = 31 AND `point` = 3;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 48000, `mana_use` = 130, `options` = '[{"param":28,"id":30},{"param":140,"id":84},{"param":54,"id":85}]' WHERE `template_id` = 31 AND `point` = 4;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 44000, `mana_use` = 140, `options` = '[{"param":34,"id":30},{"param":120,"id":84},{"param":57,"id":85}]' WHERE `template_id` = 31 AND `point` = 5;
UPDATE `skill` SET `max_fight` = 0, `dx` = 0, `dy` = 0, `cooldown` = 40000, `mana_use` = 150, `options` = '[{"param":40,"id":30},{"param":100,"id":84},{"param":60,"id":85}]' WHERE `template_id` = 31 AND `point` = 6;

-- 5. Đồng bộ JSON skillTemplates trong skill_template id = 31
UPDATE `skill_template` 
SET `skillTemplates` = '[{"skillId":313,"maxFight":0,"dx":0,"level":25,"dy":0,"manaUse":0,"options":[{"param":0,"id":30},{"param":0,"id":84},{"param":0,"id":85}],"point":0,"coolDown":0},{"skillId":314,"maxFight":0,"dx":0,"level":25,"dy":0,"manaUse":100,"options":[{"param":10,"id":30},{"param":200,"id":84},{"param":45,"id":85}],"point":1,"coolDown":60000},{"skillId":315,"maxFight":0,"dx":0,"level":26,"dy":0,"manaUse":110,"options":[{"param":16,"id":30},{"param":180,"id":84},{"param":48,"id":85}],"point":2,"coolDown":56000},{"skillId":316,"maxFight":0,"dx":0,"level":27,"dy":0,"manaUse":120,"options":[{"param":22,"id":30},{"param":160,"id":84},{"param":51,"id":85}],"point":3,"coolDown":52000},{"skillId":317,"maxFight":0,"dx":0,"level":28,"dy":0,"manaUse":130,"options":[{"param":28,"id":30},{"param":140,"id":84},{"param":54,"id":85}],"point":4,"coolDown":48000},{"skillId":318,"maxFight":0,"dx":0,"level":29,"dy":0,"manaUse":140,"options":[{"param":34,"id":30},{"param":120,"id":84},{"param":57,"id":85}],"point":5,"coolDown":44000},{"skillId":319,"maxFight":0,"dx":0,"level":30,"dy":0,"manaUse":150,"options":[{"param":40,"id":30},{"param":100,"id":84},{"param":60,"id":85}],"point":6,"coolDown":40000}]' 
WHERE `id` = 31;
