# TÀI LIỆU THIẾT KẾ & THÔNG SỐ MOD KỸ NĂNG: KIẾM, TIÊU & ĐAO
### (LƯU TRỮ Ý TƯỞNG REWORK GAMEPLAY - NSO PRIVATE 2026)

> **Mục tiêu**: Đưa chiều sâu chiến thuật và phong cách hành động hiện đại (High Risk - High Reward / Strategic Utility) vào NSO, khắc phục hạn chế animation của Client J2ME, xóa bỏ các kỹ năng "rác/phèn" (Dead Skills) và tạo bản sắc riêng biệt cho từng môn phái.

---

## MỤC LỤC
1. [Phái Ninja Kiếm:](#1-phái-ninja-kiếm)
   - [1.1. Chiêu 25 Kakyuu: Nội Tại Hồi Phục Thức Ăn (Cố Định & %)](#11-chiêu-25-kakyuu-nội-tại-hồi-phục-thức-ăn-cố-định--)
   - [1.2. Chiêu 6x Pawaraikou: Chế Độ "Khô Máu" All-Out (Kiểu K'Sante)](#12-chiêu-6x-pawaraikou-chế-độ-khô-máu-all-out-kiểu-ksante)
2. [Phái Ninja Tiêu: Chiêu 35 Hoshitama ("Đổi Mana Lấy Sức Tấn Công")](#2-phái-ninja-tiêu-chiêu-35-hoshitama-đổi-mana-lấy-sức-tấn-công)
3. [Phái Ninja Đao: Chiêu 25 Magumandari ("Bùa Hại Suy Yếu & Phá Giáp")](#3-phái-ninja-đao-chiêu-25-magumandari-bùa-hại-suy-yếu--phá-giáp)
4. [Bảng So Sánh Chỉ Số: Trước vs Sau Khi Mod](#4-bảng-so-sánh-chỉ-số-trước-vs-sau-khi-mod)
5. [Kế Hoạch Triển Khai Vào Codebase (Server & Database)](#5-kế-hoạch-triển-khai-vào-codebase)
6. [Đánh Giá Cân Bằng Game (Balance & Counterplay)](#6-đánh-giá-cân-bằng-game)

---

## 1. PHÁI NINJA KIẾM

### 1.1. Chiêu 25 Kakyuu: Nội Tại Hồi Phục Thức Ăn (Cố Định & %)

#### A. Lý do thiết kế & Giới hạn Client
- **Thực trạng gốc**: Chiêu Kakyuu (ID 4) gốc là `type = 3` (dùng lên quái để nướng quái thành thức ăn). Rất phèn, vô dụng trong PvP và săn Boss.
- **Giới hạn Client**: Client NSO (J2ME / Android / PC) **không có Sprite / Animation hành động vung kiếm** cho ID chiêu này. Nếu đổi thành chiêu tấn công chủ động, nhân vật sẽ không có động tác chém hoặc animation bị khựng giật.
- **Giải pháp tối ưu**: Chuyển thành **KỸ NĂNG BỊ ĐỘNG (PASSIVE - `type = 0`)**:
  - Không cần sửa hay can thiệp Client.
  - Người chơi chỉ cần cộng điểm trong Bảng Kỹ Năng, Server sẽ tự động kích hoạt nội tại.

#### B. Concept: "Hỏa Vị Tiêu Hóa / Hấp Thụ Sinh Khí Thức Ăn"
> *"Ninja Kiếm rèn luyện thể chất hỏa khí, tối ưu hóa hệ tiêu hóa giúp hấp thu triệt để dưỡng chất từ thức ăn: Tăng vượt bậc lượng HP hồi phục mỗi nhịp (kết hợp cả giá trị cố định lẫn % Máu tối đa)."*

- **Cơ chế hoạt động**:
  - Khi Ninja Kiếm có buff thức ăn (`ItemTemplate.TYPE_EAT`), ở mỗi nhịp hồi phục định kỳ (mỗi 5 giây trong `updateEveryFiveSecond`):
  - Lượng HP hồi phục được cộng thêm:
    $$\text{HP Hồi Thêm} = \text{Giá Trị Cố Định (Flat HP)} + (\text{Max HP} \times \text{Tỉ Lệ \%})$$
  - Giúp Kiếm tận dụng thanh Máu khổng lồ của mình để trụ đường cày cuốc, ôm quái và tank Boss không lo tụt máu!

#### C. Bảng thông số chi tiết 6 cấp độ kỹ năng:

| Cấp Chiêu (Point) | Yêu Cầu Level | HP Hồi Cố Định (Mỗi 5s) | % Max HP Hồi Thêm (Mỗi 5s) | Ví dụ với Kiếm có 10,000 HP (Mỗi 5s hồi thêm) |
| :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | **+100 HP** | **+0.5% Max HP** | +150 HP / nhịp |
| **Point 2** | Cấp 26 | **+150 HP** | **+0.8% Max HP** | +230 HP / nhịp |
| **Point 3** | Cấp 27 | **+200 HP** | **+1.1% Max HP** | +310 HP / nhịp |
| **Point 4** | Cấp 28 | **+250 HP** | **+1.4% Max HP** | +390 HP / nhịp |
| **Point 5** | Cấp 29 | **+300 HP** | **+1.7% Max HP** | +470 HP / nhịp |
| **Point 6 (Max)** | Cấp 30 | **+400 HP** | **+2.0% Max HP** | **+600 HP / nhịp** |

*(Kết hợp với thức ăn cấp cao, Kiếm có thể hồi hơn 1,000 HP mỗi 5 giây mà không cần cắn bình máu tức thời).*

---

### 1.2. Chiêu 6x Pawaraikou: Chế Độ "Khô Máu" All-Out (Kiểu K'Sante)

#### A. Thực trạng kỹ năng gốc
- **ID Skill Template**: `55` (Bí thuật 6x Kiếm - Hệ Hỏa).
- **Loại chiêu gốc**: `type = 0` (Kỹ năng Bị động). Chỉ cộng hỏa sát ngầm thụ động, lối chơi cù nhây thiếu đột biến.

#### B. Concept: Chuyển thành Buff Chủ Động (`type = 2`)
- **Thời gian hồi**: `45 giây`, hiệu lực `15 giây`.
- **Tiêu hao khi bật**: `150 MP` + **Tự trừ 15% Máu hiện tại**.
- **Cơ chế đánh đổi trong 15 giây**:
  - **Giảm phòng thủ**: Giảm 50% Kháng 3 hệ (`resFire, resIce, resWind`), Giảm 40% Né đòn (`miss`), chịu thêm 30% sát thương từ đối phương.
  - **Tăng công cực đại**: Tăng 80% - 100% Lực tấn công, Tăng 40% Sát thương chí mạng.
  - **Sát thương chuẩn (True Damage)**: 25% sát thương đòn đánh chuyển thành Sát Thương Chuẩn xuyên qua mọi lớp kháng và giáp của đối thủ.

---

## 2. PHÁI NINJA TIÊU: CHIÊU 35 HOSHITAMA (ĐỔI MANA LẤY SỨC TẤN CÔNG)

- **ID Skill Template**: `15` (Ẩn thân / Hồi phục - Cấp 35 Tiêu).
- **Rework mới**:
  - **Giảm hồi HP**: Về mức nhẹ (~30% so với gốc), **Xóa hoàn toàn khả năng tự hồi MP** trong thời gian tàng hình.
  - **Cơ chế Đốt Mana Đổi Sát Thương**:
    - Mỗi lần phóng phi tiêu tự động **đốt 8% Mana tối đa (`maxMP`)**.
    - **Bù lại**: Đòn đánh được cộng thêm sát thương bùng nổ:
      $$\text{Sát Thương Thêm} = \text{Lượng Mana Đốt} \times 2.5$$
    - *Ví dụ*: Người chơi có 3,000 MP, mỗi đòn đốt 240 MP và cộng thêm **+600 Sát thương bộc phá** trên mỗi cú ném!

---

## 3. PHÁI NINJA ĐAO: CHIÊU 25 MAGUMANDARI (BÙA HẠI SUY YẾU & PHÁ GIÁP)

- **ID Skill Template**: `40` (Bùa chú cấp 25 Đao - Hệ Phong).
- **Cách thức thi triển**: Giữ nguyên cơ chế chọn mục tiêu từ xa (`dx = 100, dy = 50`, `type = 3`) và animation bay bùa của Client.
- **Phạm vi tác dụng**: Tác dụng lên **cả Quái thường, Quái Tinh Anh, Thủ Lĩnh, BOSS Thế Giới và Người chơi (PvP)**.
- **Cơ chế Bùa Hại 2 chiều (Cân bằng chuẩn kỹ năng cấp 25)**:
  - **Giảm sát thương mục tiêu gây ra**: `10% - 15%`.
  - **Tăng sát thương mục tiêu nhận vào**: `10% - 15%`.
  - Thời gian bùa: `6.0s -> 8.0s`. Hồi chiêu: `25s -> 20s`.

| Cấp Chiêu (Point) | Yêu Cầu Level | Giảm Sát Thương Mục Tiêu Gây Ra | Tăng Sát Thương Mục Tiêu Nhận Vào | Thời Gian Bùa | Cooldown | Mana Use |
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
    subgraph KIẾM 25 (KAKYUU)
        K25_1["Bản Gốc: Nướng quái thành đồ ăn<br/>Vô dụng khi PvP & Boss"] --> K25_2["Bản Mod: Kỹ năng Bị Động (Passive)<br/>Tăng hồi HP từ thức ăn mỗi 5s<br/>+400 HP cố định & +2.0% Max HP"]
    end

    subgraph KIẾM 6x (PAWARAIKOU)
        K6x_1["Bản Gốc: Bị động<br/>+Hỏa sát thụ động<br/>Ôm thủ trâu bò cù nhây"] --> K6x_2["Bản Mod: Buff Chủ Động (15s)<br/>-50% Kháng, -40% Né<br/>+100% Sức tấn công<br/>Kích hoạt 25% Sát Thương Chuẩn"]
    end

    subgraph TIÊU 35 (HOSHITAMA)
        T1["Bản Gốc: Ẩn thân 5s<br/>Hồi nhiều Máu & Mana<br/>Chạy trốn câu giờ"] --> T2["Bản Mod: Xạ Thủ Ám Sát<br/>Hồi máu nhẹ, Xóa hồi Mana<br/>Mỗi đòn đốt 8% Max MP<br/>Nổ Sát thương = 2.5 x Mana đốt"]
    end

    subgraph ĐAO 25 (MAGUMANDARI)
        D1["Bản Gốc: Cấm quái thường đánh<br/>Vô dụng với Boss & PvP"] --> D2["Bản Mod: Bùa Hại Đa Mục Tiêu (8s)<br/>Dùng lên Quái, Boss & PvP<br/>Mục tiêu Giảm 10-15% Dame gây ra<br/>Mục tiêu Nhận Thêm 10-15% Dame"]
    end
```

---

## 5. KẾ HOẠCH TRIỂN KHAI VÀO CODEBASE

### Bước 1: Cập nhật Cơ sở dữ liệu MariaDB (`skill_template` & `skill`)
```sql
-- 1. Đổi skill 25 Kiếm (ID 4) từ type=3 sang Bị động (type=0)
UPDATE `skill_template` 
SET `type` = 0, 
    `description` = 'Hỏa Vị Tiêu Hóa: Cường hóa khả năng tiêu hóa thức ăn, gia tăng mạnh lượng HP hồi phục định kỳ (cố định và theo % máu tối đa).' 
WHERE `id` = 4;

-- 2. Đổi skill 6x Kiếm (ID 55) từ Bị động (type=0) sang Buff Chủ động (type=2)
UPDATE `skill_template` 
SET `type` = 2, 
    `description` = 'Chế độ Khô Máu: Giảm 50% phòng thủ, tăng cực đại lực tấn công và sát thương chuẩn trong 15s.' 
WHERE `id` = 55;

UPDATE `skill` 
SET `cooldown` = 45000, `mana_use` = 150 
WHERE `template_id` = 55;

-- 3. Cập nhật mô tả cho skill 35 Tiêu (ID 15)
UPDATE `skill_template` 
SET `description` = 'Ẩn thân xạ thủ: Hồi phục nhẹ HP, tiêu hao lượng lớn Mana trong mỗi đòn đánh để tăng vọt sát thương.' 
WHERE `id` = 15;

-- 4. Cập nhật mô tả cho skill 25 Đao (ID 40)
UPDATE `skill_template` 
SET `description` = 'Bùa Hại Trấn Áp: Yểm bùa lên mục tiêu khiến mục tiêu giảm 10-15% sức tấn công và nhận thêm 10-15% sát thương.' 
WHERE `id` = 40;
```

---

### Bước 2: Bổ sung logic kỹ năng trong `Char.java`

#### 2.1. Hồi phục máu chiêu 25 Kiếm trong `updateEveryFiveSecond()`:
```java
// Trong hàm Char.updateEveryFiveSecond():
// Nếu nhân vật phái Kiếm (class = 1) đã học chiêu 25 (ID 4) và đang có buff thức ăn
Skill skillKakyuu = getSkillWithIdTemplate(4);
if (this.clazz == 1 && skillKakyuu != null && skillKakyuu.point > 0) {
    boolean hasFood = em.findByType((byte) 0) != null; // Có buff thức ăn
    if (hasFood) {
        int flatHp = 50 + (skillKakyuu.point * 50);          // +100 -> +400 HP
        int percentHp = this.maxHP * (skillKakyuu.point * 3 + 2) / 1000; // +0.5% -> +2.0% Max HP
        int extraRecovery = flatHp + percentHp;
        
        this.hp += extraRecovery;
        if (this.hp > this.maxHP) {
            this.hp = this.maxHP;
        }
        zone.getService().loadHP(this);
        getService().updateHp();
    }
}
```

#### 2.2. Kích hoạt chiêu 6x Kiếm (ID 55) trong `useSkillBuff()`:
```java
} else if (skill.template.id == 55) {
    Effect eff = new Effect(65, 15000, 80);
    em.setEffect(eff);
    int sacrificeHp = this.hp * 15 / 100;
    if (this.hp - sacrificeHp > 1) {
        addHp(-sacrificeHp);
    }
    getService().updateHp();
    serverMessage("Đã kích hoạt chế độ KHÔ MÁU (Sát thương tăng vọt, Phòng thủ suy giảm trong 15s)!");
}
```

#### 2.3. Bùa hại chiêu 25 Đao (ID 40) trong `attackMonster` & `attackPlayer`:
```java
if (skillTemplateId == 40) {
    int debuffPercent = 10 + (selectedSkill.point - 1); // 10% -> 15%
    long duration = 6000L + (selectedSkill.point * 400L); // 6s -> 8s
    
    // Áp bùa lên Quái / Boss (PvE)
    if (mobs != null && !mobs.isEmpty()) {
        Mob targetMob = mobs.get(0);
        Effect eff = new Effect(48, duration, debuffPercent);
        targetMob.effects.put((byte) 48, eff);
        zone.getService().addEffect(targetMob, 48, 5, 5, 0);
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

#### 3.1. Trong [CombatService.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/CombatService.java) - `calculatePvPDamage`:
```java
// Chiêu 6x Kiếm
Effect effKhoMau = attacker.getEm().findByID(65);
if (effKhoMau != null) {
    dameHit += (dameHit * effKhoMau.param / 100);
    dameHit += (attacker.damage * 25 / 100); // 25% sát thương chuẩn
}
Effect targetKhoMau = target.getEm().findByID(65);
if (targetKhoMau != null) {
    dameHit += (dameHit * 30 / 100);
}

// Chiêu 35 Tiêu
Effect effTieu35 = attacker.getEm().findByID(16);
if (effTieu35 != null) {
    int manaBurn = attacker.maxMP * 8 / 100;
    if (attacker.mp >= manaBurn) {
        attacker.addMp(-manaBurn);
        attacker.getService().updateMp();
        dameHit += (manaBurn * 5 / 2);
    }
}

// Chiêu 25 Đao (PvP)
Effect effWeaken = attacker.getEm().findByID(48);
if (effWeaken != null) {
    dameHit -= (dameHit * effWeaken.param / 100); // Giảm 10-15% công
}
Effect effFragile = target.getEm().findByID(48);
if (effFragile != null) {
    dameHit += (dameHit * effFragile.param / 100); // Nhận thêm 10-15% dame
}
```

#### 3.2. Trong [CombatService.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/CombatService.java) - `calculatePvEDamage`:
```java
// Đao đánh Boss / Quái
Effect effMobBua = mob.effects.get((byte) 48);
if (effMobBua != null) {
    dameHit += (dameHit * effMobBua.param / 100); // Cả team gây thêm 10-15% dame lên Boss
}
```

#### 3.3. Trong [Mob.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/mob/Mob.java) - `attack`:
```java
// Boss tấn công người chơi khi dính bùa của Đao
Effect effMobWeaken = this.effects.get((byte) 48);
if (effMobWeaken != null) {
    dameHp -= (dameHp * effMobWeaken.param / 100); // Giảm 10-15% dame của Boss
}
```

---

## 6. ĐÁNH GIÁ CÂN BẰNG GAME

1. **Ninja Kiếm trở thành "Pháo Đài Bất Tử" về độ hồi phục**:
   - Nhờ chiêu 25 nội tại thức ăn hồi máu theo cả lượng cố định lẫn % Max HP, Ninja Kiếm có khả năng hồi phục sinh lực tự nhiên vô song, rất phù hợp với phong cách Tanker dẫn đầu càn quét quái vật và Boss.
2. **Chiến thuật Đao & Tiêu bổ trợ lẫn nhau**:
   - Đao phóng bùa phá giáp +15% sát thương, Tiêu bật tàng hình dồn sát thương đốt Mana $\rightarrow$ Khi phối hợp tổ đội, sát thương burst lên Boss hoặc đối thủ PvP đạt hiệu quả tối đa.
3. **Hoàn toàn tương thích Client gốc**:
   - Cả 3 chiêu thức mod đều sử dụng animation, effect có sẵn hoặc chuyển thành Passive không cần can thiệp file Client `.jar`.

---

*Tài liệu được lưu trữ trực tiếp trong thư mục dự án để làm chuẩn mực kỹ thuật khi tiến hành cập nhật phiên bản máy chủ.*
