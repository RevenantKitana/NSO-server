# TÀI LIỆU THIẾT KẾ & THÔNG SỐ MOD KỸ NĂNG: KIẾM, TIÊU & ĐAO
### (LƯU TRỮ Ý TƯỞNG REWORK GAMEPLAY - NSO PRIVATE 2026)

> **Mục tiêu**: Đưa chiều sâu chiến thuật và phong cách hành động hiện đại (High Risk - High Reward / Strategic Counter) vào NSO, xóa bỏ các kỹ năng "rác/phèn" (Dead Skills) và đa dạng hóa lối chơi giữa các môn phái.

---

## MỤC LỤC
1. [Kỹ Năng 1: Ninja Kiếm - Chiêu 6x Pawaraikou ("Khô Máu" Kiểu K'Sante)](#1-ninja-kiếm--chiêu-6x-pawaraikou-chế-độ-khô-máu)
2. [Kỹ Năng 2: Ninja Tiêu - Chiêu 35 Hoshitama ("Đổi Mana Lấy Sức Tấn Công")](#2-ninja-tiêu--chiêu-35-hoshitama-đổi-mana-lấy-sát-thương)
3. [Kỹ Năng 3: Ninja Đao - Chiêu 25 Magumandari ("Bùa Hại Suy Yếu & Phá Giáp")](#3-ninja-đao--chiêu-25-magumandari-bùa-hại-suy-yếu--phá-giáp)
4. [Bảng So Sánh Chỉ Số: Trước vs Sau Khi Mod](#4-bảng-so-sánh-chỉ-số-trước-vs-sau-khi-mod)
5. [Kế Hoạch Triển Khai Vào Codebase (Server & Database)](#5-kế-hoạch-triển-khai-vào-codebase)
6. [Đánh Giá Cân Bằng Game (Balance & Counterplay)](#6-đánh-giá-cân-bằng-game)

---

## 1. NINJA KIẾM – CHIÊU 6x PAWARAIKOU (CHẾ ĐỘ "KHÔ MÁU")

### 1.1. Thực trạng kỹ năng gốc
- **ID Skill Template**: `55` (Bí thuật 6x Kiếm - Hệ Hỏa).
- **Loại chiêu gốc**: `type = 0` (Kỹ năng Bị động / Passive).
- **Cơ chế cũ**: Chỉ cộng thêm một lượng hỏa sát thụ động (`options[61]`), không có tương tác phím bấm, lối chơi thụ động "chỉ biết ôm thủ cù nhây".

### 1.2. Concept Rework: "Chế Độ Khô Máu / All-Out" (K'Sante Form)
> *"Khi dồn hết chakra và khí tức vào lưỡi kiếm, Ninja Kiếm cởi bỏ toàn bộ giáp trụ phòng hộ để đổi lấy sức công phá hủy diệt trong thời gian ngắn."*

- **Chuyển thành chiêu Chủ Động (`type = 2` - Buff chủ động)**:
  - Cho phép người chơi kéo vào thanh phím tắt (Shortcut Bar) để chủ động bấm phím kích hoạt.
  - **Thời gian hồi chiêu (Cooldown)**: `45 giây`.
  - **Thời gian hiệu lực**: `12 - 15 giây`.
  - **Tiêu hao khi bật**: `150 MP` + **Tự trừ 15% Máu hiện tại** ngay khi xuất chiêu.

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
2. **Cơ chế Đốt Mana Đổi Sát Thương Theo Từng Đòn Bắn**:
   - Trong thời gian hiệu lực buff (5 - 8 giây):
   - Mỗi lần Ninja Tiêu phóng phi tiêu vào kẻ địch:
     - Tự động **đốt thêm 8% Mana tối đa (`maxMP`)** của bản thân.
     - **Bù lại**: Đòn đánh được cộng thêm sát thương bộc phá tỷ lệ thuận với lượng Mana vừa tiêu hao:
       $$\text{Sát Thương Thêm} = \text{Lượng Mana Tiêu Hao} \times 2.5$$
     - *Ví dụ*: Người chơi có 3,000 MP, mỗi đòn đốt 240 MP và cộng thêm **+600 Sát thương bộc phá** trên mỗi cú ném!

---

## 3. NINJA ĐAO – CHIÊU 25 MAGUMANDARI (BÙA HẠI SUY YẾU & PHÁ GIÁP)

### 3.1. Thực trạng kỹ năng gốc
- **ID Skill Template**: `40` (Bùa chú cấp 25 Đao - Hệ Phong).
- **Loại chiêu gốc**: `type = 3` (Chiêu dùng lên quái).
- **Cơ chế cũ**: Chỉ có tác dụng lên quái vật thường khiến quái không thể đánh trong 5-15s (`zone.setDisable(mob, true)`).
- **Nhược điểm lớn**:
  - Vô dụng 100% trong PvP (không tác dụng lên người chơi).
  - Vô dụng 100% khi đi săn Boss / Phó bản (Boss miễn nhiễm hoàn toàn).
  - Quái thường đánh rất yếu nên cấm quái đánh là thừa thãi $\rightarrow$ Kỹ năng bị coi là "phế phẩm/phèn nhất game".

### 3.2. Concept Rework: "Bùa Hại Suy Yếu & Phá Giáp" (Curse of Fragility)
> *"Thay vì cấm đánh quái một cách đơn giản, Ninja Đao vận phong chú ném một lá bùa phong ấn lên mục tiêu, khiến kẻ địch bị suy giảm sức tấn công đồng thời chịu nhiều sát thương hơn từ mọi nguồn."*

- **Giữ nguyên cách thức thi triển gốc**:
  - Chọn mục tiêu từ cự ly xa (`dx = 100, dy = 50`), giữ nguyên động tác niệm bùa và animation bay bùa của Client.
- **Mở rộng phạm vi tác dụng**:
  - Tác dụng lên **cả Quái thường, Quái Tinh Anh, Thủ Lĩnh, BOSS Thế Giới và NGƯỜI CHƠI (PvP)**.
- **Cơ chế 2 chiều của Bùa Hại (Hiệu ứng vừa phải, chuẩn cấp 25)**:
  - **Giảm sát thương mục tiêu gây ra**: `10% - 15%`.
  - **Tăng sát thương mục tiêu nhận vào**: `10% - 15%`.
  - Không buff quá cao (vì đây là kỹ năng cấp trung/thấp level 25) để tránh phá vỡ cân bằng game, nhưng tổng chênh lệch trao đổi chiêu thức đạt tới **~25% - 30%**, cực kỳ hữu dụng trong giao tranh!

### 3.3. Bảng thông số chi tiết 6 cấp độ kỹ năng:

| Cấp Chiêu (Point) | Yêu Cầu Level | Giảm Sát Thương Mục Tiêu Gây Ra | Tăng Sát Thương Mục Tiêu Nhận Vào | Thời Gian Bùa Duy Trì | Thời Gian Hồi Chiêu | Mana Tiêu Hao |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | **-10%** | **+10%** | 6.0 giây | 25 giây | 30 MP |
| **Point 2** | Cấp 26 | **-11%** | **+11%** | 6.5 giây | 24 giây | 40 MP |
| **Point 3** | Cấp 27 | **-12%** | **+12%** | 7.0 giây | 23 giây | 50 MP |
| **Point 4** | Cấp 28 | **-13%** | **+13%** | 7.5 giây | 22 giây | 60 MP |
| **Point 5** | Cấp 29 | **-14%** | **+14%** | 8.0 giây | 21 giây | 70 MP |
| **Point 6 (Max)** | Cấp 30 | **-15%** | **+15%** | **8.0 giây** | **20 giây** | 80 MP |

---

## 4. BẢNG SO SÁNH CHỈ SỐ: TRƯỚC VS SAU KHI MOD

```mermaid
graph TD
    subgraph KIẾM (CHIÊU 6x - PAWARAIKOU)
        K1["Bản Gốc: Bị động<br/>+Hỏa sát thụ động<br/>Ôm thủ trâu bò cù nhây"] --> K2["Bản Mod: Buff Chủ Động (15s)<br/>-50% Kháng, -40% Né<br/>+100% Sức tấn công<br/>Kích hoạt 25% Sát Thương Chuẩn"]
    end

    subgraph TIÊU (CHIÊU 35 - HOSHITAMA)
        T1["Bản Gốc: Ẩn thân 5s<br/>Hồi nhiều Máu & Mana<br/>Chạy trốn câu giờ"] --> T2["Bản Mod: Xạ Thủ Ám Sát<br/>Hồi máu nhẹ, Xóa hồi Mana<br/>Mỗi đòn đốt 8% Max MP<br/>Nổ Sát thương = 2.5 x Mana đốt"]
    end

    subgraph ĐAO (CHIÊU 25 - MAGUMANDARI)
        D1["Bản Gốc: Cấm quái thường đánh<br/>Vô dụng với Boss & PvP<br/>Kỹ năng rác / Dead Skill"] --> D2["Bản Mod: Bùa Hại Đa Mục Tiêu (8s)<br/>Dùng lên Quái, Boss & PvP<br/>Mục tiêu Giảm 10-15% Dame gây ra<br/>Mục tiêu Chịu Thêm 10-15% Dame nhận"]
    end
```

---

## 5. KẾ HOẠCH TRIỂN KHAI VÀO CODEBASE

### Bước 1: Cập nhật Cơ sở dữ liệu MariaDB (`skill_template` & `skill`)
```sql
-- 1. Đổi skill 6x Kiếm (ID 55) từ Bị động (type=0) sang Buff Chủ động (type=2)
UPDATE `skill_template` 
SET `type` = 2, 
    `description` = 'Chế độ Khô Máu: Giảm 50% phòng thủ, tăng cực đại lực tấn công và sát thương chuẩn trong 15s.' 
WHERE `id` = 55;

-- Thêm Cooldown và Mana tiêu hao cho skill 55
UPDATE `skill` 
SET `cooldown` = 45000, `mana_use` = 150 
WHERE `template_id` = 55;

-- 2. Cập nhật mô tả cho skill 35 Tiêu (ID 15)
UPDATE `skill_template` 
SET `description` = 'Ẩn thân xạ thủ: Hồi phục nhẹ HP, tiêu hao lượng lớn Mana trong mỗi đòn đánh để tăng vọt sát thương.' 
WHERE `id` = 15;

-- 3. Cập nhật mô tả cho skill 25 Đao (ID 40)
UPDATE `skill_template` 
SET `description` = 'Bùa Hại Trấn Áp: Yểm bùa lên mục tiêu khiến mục tiêu giảm 10-15% sức tấn công và nhận thêm 10-15% sát thương.' 
WHERE `id` = 40;
```

---

### Bước 2: Bổ sung logic kích hoạt kỹ năng trong `Char.java`

#### 2.1. Kích hoạt chiêu 6x Kiếm trong `useSkillBuff`:
```java
} else if (skill.template.id == 55) {
    // Tạo Effect 65 (Thời gian 15s, param = 80% sát thương cộng thêm)
    Effect eff = new Effect(65, 15000, 80);
    em.setEffect(eff);
    // Trừ 15% máu hiện tại khi kích hoạt
    int sacrificeHp = this.hp * 15 / 100;
    if (this.hp - sacrificeHp > 1) {
        addHp(-sacrificeHp);
    }
    getService().updateHp();
    serverMessage("Đã kích hoạt chế độ KHÔ MÁU (Sát thương tăng vọt, Phòng thủ suy giảm trong 15s)!");
}
```

#### 2.2. Kích hoạt Bùa hại chiêu 25 Đao (ID 40) trong `attackMonster` & `attackPlayer`:
```java
// Khi thi triển chiêu 40 (Magumandari của Đao)
if (skillTemplateId == 40) {
    int debuffPercent = 10 + (selectedSkill.point - 1); // 10% -> 15%
    long duration = 6000L + (selectedSkill.point * 400L); // 6s -> 8s
    
    // Áp bùa lên Quái / Boss (PvE)
    if (mobs != null && !mobs.isEmpty()) {
        Mob targetMob = mobs.get(0);
        Effect eff = new Effect(48, duration, debuffPercent);
        targetMob.effects.put((byte) 48, eff);
        zone.getService().addEffect(targetMob, 48, 5, 5, 0); // Hiển thị icon bùa trên đầu quái
    }
    
    // Áp bùa lên Người chơi khác (PvP)
    if (targetPlayer != null) {
        Effect eff = new Effect(48, duration, debuffPercent);
        targetPlayer.getEm().setEffect(eff);
    }
    return;
}
```

---

### Bước 3: Áp dụng công thức tính sát thương trong `CombatService.java` & `Mob.java`

#### 3.1. Trong [CombatService.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/CombatService.java) - Hàm `calculatePvPDamage` (PvP):
```java
// --- XỬ LÝ CHIÊU 6x KIẾM (Chế độ Khô Máu - Effect 65) ---
Effect effKhoMau = attacker.getEm().findByID(65);
if (effKhoMau != null) {
    dameHit += (dameHit * effKhoMau.param / 100);       // Tăng 80% sát thương
    dameHit += (attacker.damage * 25 / 100);             // 25% Sát thương chuẩn
}

// Đối thủ đang bật Khô Máu sẽ bị nhận thêm sát thương do tụt giáp
Effect targetKhoMau = target.getEm().findByID(65);
if (targetKhoMau != null) {
    dameHit += (dameHit * 30 / 100);
}

// --- XỬ LÝ CHIÊU 35 TIÊU (Đốt Mana đổi Sát Thương - Effect 16) ---
Effect effTieu35 = attacker.getEm().findByID(16);
if (effTieu35 != null) {
    int manaBurn = attacker.maxMP * 8 / 100;
    if (attacker.mp >= manaBurn) {
        attacker.addMp(-manaBurn);
        attacker.getService().updateMp();
        dameHit += (manaBurn * 5 / 2); // Sát thương thêm = Mana đốt x 2.5
    }
}

// --- XỬ LÝ BÙA HẠI CHIÊU 25 ĐAO (Effect 48) TRONG PvP ---
// 1. Kẻ tấn công đang bị dính bùa hại -> Giảm 10-15% sát thương gây ra
Effect effWeaken = attacker.getEm().findByID(48);
if (effWeaken != null) {
    dameHit -= (dameHit * effWeaken.param / 100);
}

// 2. Mục tiêu chịu đòn đang bị dính bùa hại -> Tăng 10-15% sát thương phải nhận
Effect effFragile = target.getEm().findByID(48);
if (effFragile != null) {
    dameHit += (dameHit * effFragile.param / 100);
}
```

#### 3.2. Trong [CombatService.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/CombatService.java) - Hàm `calculatePvEDamage` (PvE):
```java
// Khi Người chơi đánh Quái/Boss dính bùa hại chiêu 25 Đao
Effect effMobBua = mob.effects.get((byte) 48);
if (effMobBua != null) {
    dameHit += (dameHit * effMobBua.param / 100); // Tăng 10-15% sát thương cả team đánh vào
}
```

#### 3.3. Trong [Mob.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/mob/Mob.java) - Hàm `attack(Char pl, Char owner)`:
```java
// Khi Quái/Boss tấn công Người chơi nhưng đang bị dính bùa hại của Đao:
Effect effMobWeaken = this.effects.get((byte) 48);
if (effMobWeaken != null) {
    dameHp -= (dameHp * effMobWeaken.param / 100); // Boss đánh yếu đi 10-15%
}
```

---

## 6. ĐÁNH GIÁ CÂN BẰNG GAME

1. **Ninja Đao có vai trò rõ nét trong Tổ Đội (Party Synergy)**:
   - Trước đây chiêu 25 Đao bị bỏ xó vì Boss miễn nhiễm cấm đánh.
   - Khi chuyển thành bùa hại giảm 15% dame Boss và tăng 15% dame nhận vào, **Đao trở thành nhân tố mở giao tranh cực kỳ quan trọng trong mọi đội săn Boss và chiến trường**.
2. **Không phá vỡ cân bằng cấp thấp**:
   - Con số 10% - 15% là mức tăng/giảm vừa phải cho một kỹ năng cấp 25, không làm lu mờ các chiêu thức 5x/6x đời sau nhưng vẫn mang lại giá trị chiến thuật rõ rệt.
3. **Đối sách (Counterplay)**:
   - Bùa hại kéo dài 8 giây và có hồi chiêu 20 giây $\rightarrow$ Đối thủ có 12 giây "cửa sổ an toàn" sau khi hết bùa để phản công dữ dội.
   - Các phái khống chế (Băng Kunai, Cung làm chậm, Quạt xóa hiệu ứng) có thể dùng kỹ năng giữ cự ly né tránh trong 8 giây Đao dán bùa.

---

*Tài liệu được lưu trữ trực tiếp trong thư mục dự án để làm chuẩn mực kỹ thuật khi tiến hành cập nhật phiên bản máy chủ.*
