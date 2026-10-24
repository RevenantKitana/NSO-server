# TÀI LIỆU THIẾT KẾ & THÔNG SỐ MOD KỸ NĂNG: KIẾM & TIÊU
### (LƯU TRỮ Ý TƯỞNG REWORK GAMEPLAY - NSO PRIVATE 2026)

> **Mục tiêu**: Đưa chiều sâu chiến thuật và phong cách hành động hiện đại (High Risk - High Reward) vào NSO, xóa bỏ lối mòn "Kiếm chỉ biết ôm thủ trâu bò cù nhây" và "Tiêu chỉ biết tàng hình câu giờ hồi máu".

---

## MỤC LỤC
1. [Kỹ Năng 1: Ninja Kiếm - Chiêu 6x Pawaraikou ("Khô Máu" Kiểu K'Sante)](#1-ninja-kiếm--chiêu-6x-pawaraikou-chế-độ-khô-máu)
2. [Kỹ Năng 2: Ninja Tiêu - Chiêu 35 Hoshitama ("Đổi Mana Lấy Sức Tấn Công")](#2-ninja-tiêu--chiêu-35-hoshitama-đổi-mana-lấy-sát-thương)
3. [Bảng So Sánh Chỉ Số: Trước vs Sau Khi Mod](#3-bảng-so-sánh-chỉ-số-trước-vs-sau-khi-mod)
4. [Kế Hoạch Triển Khai Vào Codebase (Server & Database)](#4-kế-hoạch-triển-khai-vào-codebase)
5. [Đánh Giá Cân Bằng Game (Balance & Counterplay)](#5-đánh-giá-cân-bằng-game)

---

## 1. NINJA KIẾM – CHIÊU 6x PAWARAIKOU (CHẾ ĐỘ "KHÔ MÁU")

### 1.1. Thực trạng kỹ năng gốc
- **ID Skill Template**: `55` (Bí thuật 6x Kiếm - Hệ Hỏa).
- **Loại chiêu gốc**: `type = 0` (Kỹ năng Bị động / Passive).
- **Cơ chế cũ**: Chỉ cộng thêm một lượng hỏa sát thụ động (`options[61]`), không có tương tác phím bấm, lối chơi thụ động.

### 1.2. Concept Rework: "Chế Độ Khô Máu / All-Out" (K'Sante Form)
> *"Khi dồn hết chakra và khí tức vào lưỡi kiếm, Ninja Kiếm cởi bỏ toàn bộ giáp trụ phòng hộ để đổi lấy sức công phá hủy diệt trong thời gian ngắn."*

- **Chuyển thành chiêu Chủ Động (`type = 2` - Buff chủ động)**:
  - Cho phép người chơi kéo vào thanh phím tắt (Shortcut Bar) để chủ động bấm phím kích hoạt.
  - **Thời gian hồi chiêu (Cooldown)**: `45 giây` (hoặc `60 giây`).
  - **Thời gian hiệu lực**: `12 - 15 giây`.
  - **Tiêu hao khi bật**: `150 - 200 MP` + **Tự trừ 15% Máu hiện tại** ngay khi xuất chiêu.

### 1.3. Cơ chế đánh đổi trong 15 giây hiệu lực

| Chỉ Số | Biến Đổi | Giải Thích Chi Tiết |
| :--- | :---: | :--- |
| **Kháng 3 Hệ (`resFire, resIce, resWind`)** | **GIẢM 50%** | Mất đi lớp kháng phòng hộ tự nhiên, cực kỳ sợ dính sốc dame hệ khác. |
| **Điểm Né Đòn (`miss`) & Giảm Sát Thương** | **GIẢM 40%** | Dễ bị trúng đòn hơn bình thường, chấp nhận chịu đau để đổi mạng. |
| **Lực Tấn Công (`damage`, `damage2`)** | **TĂNG 80% - 100%** | Sát thương cơ bản nhân gần gấp đôi, chém nhát nào thấm nhát đó. |
| **Sát Thương Chí Mạng (`dameFatal`)** | **TĂNG 40%** | Bạo kích nổ sát thương cực lớn. |
| **Sát Thương Chuẩn (True Damage)** | **KÍCH HOẠT** | Trong `CombatService.java`: 25% tổng sát thương đòn đánh sẽ chuyển thành **Sát Thương Chuẩn** (Xuyên thẳng mọi tầng giáp, kháng hệ và giảm trừ dame của đối thủ). |

---

## 2. NINJA TIÊU – CHIÊU 35 HOSHITAMA (ĐỔI MANA LẤY SÁT THƯƠNG)

### 2.1. Thực trạng kỹ năng gốc
- **ID Skill Template**: `15` (Ẩn thân / Hồi phục - Cấp 35 Tiêu).
- **Loại chiêu gốc**: `type = 2` (Buff chủ động).
- **Cơ chế cũ**: Khi bấm tàng hình 5 giây (`Effect ID = 16`), liên tục hồi lượng lớn HP và MP. Tiêu thường dùng chiêu này chỉ để chạy trốn, câu giờ hồi máu làm giảm nhịp độ trận đấu.

### 2.2. Concept Rework: "Ninja Xạ Thủ Ám Sát" (Mana-Burn Sniper)
> *"Không còn là chiêu bài chạy trốn thụ động, Hoshitama trở thành trạng thái tập trung cao độ: Tiêu hao lượng lớn Chakra (Mana) theo từng phát bắn để cường hóa phi tiêu nổ tung mục tiêu."*

### 2.3. Cơ chế tinh chỉnh cụ thể:
1. **Khấu trừ khả năng hồi phục**:
   - Giảm lượng hồi HP xuống mức **trung bình** (chỉ hồi nhẹ ~30% so với bản gốc).
   - **XÓA HOÀN TOÀN khả năng tự hồi MP** trong thời gian tàng hình.
2. **Cơ chế Tiêu Hao Mana Đổi Lấy Sát Thương (Chọn 1 trong 2 phương án)**:

#### 👉 Phương Án A: Tiêu hao Mana theo từng đòn bắn (Khuyên dùng)
- Trong thời gian hiệu lực buff (5 - 8 giây):
- Mỗi lần Ninja Tiêu phóng phi tiêu vào kẻ địch:
  - Tự động **đốt thêm 8% Mana tối đa (`maxMP`)** của bản thân.
  - **Bù lại**: Đòn đánh được cộng thêm sát thương bộc phá tỷ lệ thuận với lượng Mana vừa tiêu hao:
    $$\text{Sát Thương Thêm} = \text{Lượng Mana Tiêu Hao} \times 2.5$$
  - Nếu người chơi có 3,000 MP, mỗi đòn đốt 240 MP và cộng thêm **+600 Sát thương bộc phá** trên mỗi cú ném!

#### 👉 Phương Án B: Đòn đánh đầu tiên phá tàng hình sốc sát thương
- Tăng Mana kích hoạt chiêu 35 lên **350 MP**.
- Trong lúc tàng hình, nhận thêm 30% tốc độ di chuyển.
- **Đòn đánh đầu tiên phá tàng hình**: Tiêu hao toàn bộ 25% Mana hiện có, đòn đánh **100% nổ Chí Mạng** và tăng **150% Sát thương**.

---

## 3. BẢNG SO SÁNH CHỈ SỐ: TRƯỚC VS SAU KHI MOD

```mermaid
graph LR
    subgraph PHÁI KIẾM (CHIÊU 6x)
        K1["Bản Gốc: Bị động<br/>+Chỉ số hỏa sát ngầm<br/>Lối chơi ôm thủ trâu bò"] --> K2["Bản Mod: Buff Chủ Động (15s)<br/>-50% Kháng hệ, -40% Né<br/>+100% Sức tấn công<br/>Kích hoạt Sát Thương Chuẩn"]
    end

    subgraph PHÁI TIÊU (CHIÊU 35)
        T1["Bản Gốc: Ẩn thân 5s<br/>Hồi nhiều Máu & Mana<br/>Lối chơi chạy trốn câu giờ"] --> T2["Bản Mod: Xạ Thủ Ám Sát<br/>Hồi máu nhẹ, Không hồi Mana<br/>Đốt Mana theo từng đòn bắn<br/>Sát thương nổ theo lượng MP đốt"]
    end
```

---

## 4. KẾ HOẠCH TRIỂN KHAI VÀO CODEBASE

### Bước 1: Cập nhật Cơ sở dữ liệu MariaDB (`skill_template` & `skill`)
```sql
-- 1. Đổi skill 6x Kiếm (ID 55) từ Bị động (type=0) sang Buff Chủ động (type=2)
UPDATE `skill_template` 
SET `type` = 2, 
    `description` = 'Chế độ Khô Máu: Giảm 50% phòng thủ, tăng cực đại lực tấn công và sát thương chuẩn trong 15s.' 
WHERE `id` = 55;

-- 2. Thêm Cooldown và Mana tiêu hao cho các cấp độ của skill 55
UPDATE `skill` 
SET `cooldown` = 45000, `mana_use` = 150 
WHERE `template_id` = 55;

-- 3. Cập nhật mô tả cho skill 35 Tiêu (ID 15)
UPDATE `skill_template` 
SET `description` = 'Ẩn thân xạ thủ: Hồi phục nhẹ HP, tiêu hao lượng lớn Mana trong mỗi đòn đánh để tăng vọt sát thương.' 
WHERE `id` = 15;
```

---

### Bước 2: Bổ sung Effect trong `Char.java` (`useSkillBuff`)
Tại hàm `useSkillBuff(byte dir, Skill skill)`:
```java
// Kích hoạt chiêu 6x Kiếm (Khô Máu)
} else if (skill.template.id == 55) {
    // Tạo Effect 65 (Thời gian 15,000ms = 15s, param = % tăng sát thương)
    Effect eff = new Effect(65, 15000, 80);
    em.setEffect(eff);
    // Trừ 15% máu hiện tại khi kích hoạt chế độ khô máu
    int sacrificeHp = this.hp * 15 / 100;
    if (this.hp - sacrificeHp > 1) {
        addHp(-sacrificeHp);
    }
    getService().updateHp();
    serverMessage("Đã kích hoạt chế độ KHÔ MÁU (Sát thương tăng vọt, Phòng thủ suy giảm trong 15s)!");
}
```

---

### Bước 3: Áp dụng công thức sát thương trong `CombatService.java`
Tại hàm `calculatePvPDamage(Char attacker, Char target, boolean isFatal)`:

```java
// 1. XỬ LÝ BUFF CHIÊU 6x KIẾM (Chế độ Khô Máu - Effect 65)
Effect effKhoMau = attacker.getEm().findByID(65);
if (effKhoMau != null) {
    // Tăng thêm 80% sát thương gốc
    dameHit += (dameHit * effKhoMau.param / 100);
    // Cộng thêm Sát thương chuẩn (bỏ qua mọi kháng hệ)
    dameHit += (attacker.damage * 25 / 100);
}

// Giảm trừ phòng ngự nếu MỤC TIÊU đang bật chế độ Khô Máu
Effect targetKhoMau = target.getEm().findByID(65);
if (targetKhoMau != null) {
    // Mục tiêu đang khô máu sẽ nhận thêm 30% sát thương từ đối phương
    dameHit += (dameHit * 30 / 100);
}

// 2. XỬ LÝ BUFF CHIÊU 35 TIÊU (Đốt Mana tăng Sát Thương - Effect 16)
Effect effTieu35 = attacker.getEm().findByID(16);
if (effTieu35 != null) {
    int manaBurn = attacker.maxMP * 8 / 100;
    if (attacker.mp >= manaBurn) {
        attacker.addMp(-manaBurn);
        attacker.getService().updateMp();
        // Sát thương cộng thêm gấp 2.5 lần mana đã đốt
        dameHit += (manaBurn * 5 / 2);
    }
}
```

---

## 5. ĐÁNH GIÁ CÂN BẰNG GAME

1. **Khắc phục sự mất cân bằng giữa các phái**:
   - Phái Kiếm từ trước đến nay rất khó dứt điểm đối thủ vì thiếu khả năng burst sát thương chớp nhoáng dù sống rất dai. Chiêu "Khô Máu" giúp Kiếm có nút "All-in" quyết định số phận trận đấu.
   - Phái Tiêu có phong cách linh hoạt, cấu rỉa tầm xa nhưng thiếu đòn trừng phạt dồn ép. Khi có cơ chế đốt Mana, Tiêu có thể chủ động kết liễu nhanh nếu người chơi biết tính toán tài nguyên Mana hợp lý.

2. **Cách Khắc Chế (Counterplay)**:
   - Khi Kiếm bật "Khô Máu": Đối thủ có thể dùng các kỹ năng khống chế (Băng, Choáng, Đẩy lùi, Tàng hình) để câu hết 15s hiệu lực; khi đó Kiếm vừa mất máu, vừa yếu thủ sẽ dễ dàng bị tiêu diệt.
   - Khi Tiêu xả đòn đốt Mana: Nếu đối thủ trang bị đồ có phản đòn hoặc giảm sát thương tốt, Tiêu sẽ nhanh chóng cạn kiệt toàn bộ thanh MP và không còn khả năng thi triển kỹ năng nào khác.

---

*Tài liệu được lưu trữ trực tiếp trong thư mục dự án để làm chuẩn mực kỹ thuật khi tiến hành cập nhật phiên bản máy chủ.*
