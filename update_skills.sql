SET NAMES utf8mb4;

-- 1. Cap nhat skill_option
UPDATE `skill_option` SET `name` = 'Giảm sức tấn công của quái: -#%' WHERE `id` = 48;
INSERT INTO `skill_option` (`id`, `name`) VALUES (77, 'Giảm kháng tất cả các hệ: -#') 
  ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (78, 'Duy trì bùa hại: # giây') 
  ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

-- 2. Cap nhat mo ta khong lan muc tieu cho Dao 25
UPDATE `skill_template` 
SET `description` = 'Bùa Hại Trấn Áp: Yểm bùa lên quái vật làm suy giảm sức tấn công và kháng tất cả các hệ.' 
WHERE `id` = 40;

-- 3. Cap nhat max_fight va options chi tiet cho Dao 25
UPDATE `skill` SET 
  `max_fight` = 1, 
  `cooldown` = 25000, 
  `options` = '[{"param":10,"id":48},{"param":30,"id":77},{"param":6,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 1;

UPDATE `skill` SET 
  `max_fight` = 1, 
  `cooldown` = 24000, 
  `options` = '[{"param":11,"id":48},{"param":36,"id":77},{"param":6,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 2;

UPDATE `skill` SET 
  `max_fight` = 1, 
  `cooldown` = 23000, 
  `options` = '[{"param":12,"id":48},{"param":42,"id":77},{"param":7,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 3;

UPDATE `skill` SET 
  `max_fight` = 2, 
  `cooldown` = 22000, 
  `options` = '[{"param":13,"id":48},{"param":48,"id":77},{"param":7,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 4;

UPDATE `skill` SET 
  `max_fight` = 2, 
  `cooldown` = 21000, 
  `options` = '[{"param":14,"id":48},{"param":54,"id":77},{"param":8,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 5;

UPDATE `skill` SET 
  `max_fight` = 3, 
  `cooldown` = 20000, 
  `options` = '[{"param":15,"id":48},{"param":60,"id":77},{"param":8,"id":78}]' 
WHERE `template_id` = 40 AND `point` = 6;

-- 4. Sua triet de UTF-8 cho 4 skill con lai
UPDATE `skill_template` 
SET `description` = 'Hỏa Vị Tiêu Hóa: Tăng hiệu quả hấp thụ dinh dưỡng thức ăn, gia tăng lượng HP hồi phục mỗi 5 giây.' 
WHERE `id` = 4;

UPDATE `skill_template` 
SET `description` = 'Sử dụng hỏa thuật triệu hồi vòng lửa vô hình thiêu đốt các mục tiêu bản thân tấn công.' 
WHERE `id` = 13;

UPDATE `skill_template` 
SET `description` = 'Bù Nhìn Bộc Phá: Triệu hồi bù nhìn rơm thu hút địch. Khi biến mất, phát nổ gây sát thương Băng diện rộng dồn vào quái gần nhất.' 
WHERE `id` = 22;

UPDATE `skill_template` 
SET `description` = 'Khiên Mana Hộ Mệnh: Dùng nội lực tạo khiên hấp thụ một phần sát thương chuyển sang Mana.' 
WHERE `id` = 31;
