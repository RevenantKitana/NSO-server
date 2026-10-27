# SỔ TAY GIẢI MÃ TOÀN BỘ CHỈ SỐ CHIẾN ĐẤU & CÔNG THỨC TÍNH SÁT THƯƠNG NSO
### (DÀNH CHO ADMIN & NGƯỜI CHƠI - 100% TIẾNG VIỆT - CÓ CHÚ GIẢI & VÍ DỤ TỪNG DÒNG)

> **Phiên bản máy chủ**: NSO Ninja School Private 2026  
> **Nguồn trích xuất từ Codebase**:  
> - `AbilityFromEquip.java`: Bộ máy tính toán và tổng hợp chỉ số nhân vật từ trang bị, tiềm năng, kỹ năng (áp dụng `OptionMapper.java` & `StatContainer.java`).  
> - `CombatService.java`: Bộ máy tính toán sát thương & giao tranh chuẩn hóa thời gian thực khi đánh người (PvP) hoặc đánh quái (PvE).  
> - `Char.java`: Điều phối luồng đòn đánh, quản lý trạng thái, buff/debuff và thực thi kết quả `CombatResult`.  
> - `Mob.java`: Cấp độ quái, công thức kháng nguyên tố theo level, né đòn và giới hạn level săn Boss.

---

## 🧭 HƯỚNG DẪN ĐỌC TÀI LIỆU DÀNH CHO NGƯỜI MỚI

Khi bạn nhìn vào mã nguồn của game, game dùng các biến mảng tiếng Anh. Tài liệu này dịch toàn bộ sang tiếng Việt như sau:

| Ký Hiệu trong Code | Tên Gọi Tiếng Việt | Giải Thích Bản Chất Rất Dễ Hiểu |
| :--- | :--- | :--- |
| **`A`** (hoặc `this`) | **Người Tấn Công** | Là nhân vật đang bấm phím tung ra chiêu thức tấn công. |
| **`B`** (hoặc `pl`) | **Người Phòng Thủ** | Là mục tiêu (Người chơi khác hoặc Quái) đang chịu đòn. |
| **`potential[0]`** | **Điểm Sức Khỏe** | Điểm tiềm năng tự cộng vào cột Sức khỏe (Tăng Ngoại công cho Kiếm, Kunai, Đao). |
| **`potential[1]`** | **Điểm Thân Pháp** | Điểm tiềm năng tự cộng vào cột Thân pháp (Tăng Chính xác và Né đòn: 1 Thân pháp = 1.5 Né đòn, 1 Chính xác). |
| **`potential[2]`** | **Điểm Thể Lực** | Điểm tiềm năng tự cộng vào cột Thể lực (Tăng Máu HP: 1 điểm = 10 HP gốc). |
| **`potential[3]`** | **Điểm Chakra** | Điểm tiềm năng tự cộng vào cột Chakra (Tăng Mana MP: 5 MP/điểm cho phái Nội công, 8 MP/điểm cho phái Ngoại công và tăng Nội công cho Tiêu, Cung, Quạt). |
| **`options[X]`** | **Dòng Option số X** | Giá trị của dòng thuộc tính số `X` trên Trang bị, Vũ khí, Thú cưỡi, Ngọc khảm... |
| **`optionsSupportSkill[X]`**| **Kỹ Năng Hỗ Trợ** | Giá trị cộng thêm từ các kỹ năng bị động (như skill bị động 2x, 6x). |
| **`skillOptions[X]`** | **Chiêu Thức Đang Đánh** | Giá trị sức mạnh của kỹ năng chủ động mà bạn vừa bấm ra đòn. |

---

## MỤC LỤC

1. [Sơ Đồ Vòng Đời Của Một Đòn Đánh (Từ Bấm Phím Đến Trừ Máu)](#1-sơ-đồ-vòng-đời-của-một-đòn-đánh)
2. [Phần 1: Công Thức Tạo Chỉ Số Nhân Vật (Từ Đồ Đạc & Điểm Cộng)](#2-phần-1-công-thức-tạo-chỉ-số-nhân-vật)
   - [2.1. Điểm Chính Xác & Điểm Né Đòn](#21-điểm-chính-xác--điểm-né-đòn)
   - [2.2. Điểm Máu Tối Đa (Max HP)](#22-điểm-máu-tối-đa-max-hp)
   - [2.3. Điểm Mana Tối Đa (Max MP)](#23-điểm-mana-tối-đa-max-mp)
   - [2.4. Sát Thương Tấn Công (Ngoại Công & Nội Công)](#24-sát-thương-tấn-công-ngoại-công--nội-công)
   - [2.5. Điểm Kháng 3 Hệ (Hỏa, Băng, Phong)](#25-điểm-kháng-3-hệ-hỏa-băng-phong)
3. [Phần 2: Các Cặp Chỉ Số Đối Kháng Trực Tiếp Trong Giao Tranh](#3-phần-2-các-cặp-chỉ-số-đối-kháng-trực-tiếp-trong-giao-tranh)
   - [3.1. Cặp 1: Chính Xác vs Né Đòn (Trúng hay Hụt)](#31-cặp-1-chính-xác-vs-né-đòn-trúng-hay-hụt)
   - [3.2. Cặp 2: Chí Mạng vs Kháng Sát Thương Chí Mạng (Áp trần 100%)](#32-cặp-2-chí-mạng-vs-kháng-sát-thương-chí-mạng)
   - [3.3. Cặp 3: Khắc Hệ & Xuyên Kháng (2 Tầng Xử Lý Chuẩn Hóa)](#33-cặp-3-khắc-hệ--xuyên-kháng)
   - [3.4. Cặp 4: Bỏng Lửa Hệ Hỏa (Nhân Đôi Dame Gốc)](#34-cặp-4-bỏng-lửa-hệ-hỏa-nhân-đôi-dame-gốc)
   - [3.5. Cặp 5: Loại Bỏ Miễn Giảm % Thô & Cơ Chế Giảm Trừ Cố Định (`dameDown`)](#35-cặp-5-miễn-giảm-sát-thương--giảm-trừ-cố-định)
   - [3.6. Cặp 6: Phản Đòn Cận Chiến (Bán kính <= 60) & Phản Đòn Băng Giá (20% HP)](#36-cặp-6-phản-đòn-cận-chiến--phản-đòn-băng-giá-20-hp)
   - [3.7. Cặp 7: Sát Thương Lên Người & Sát Thương Chuẩn (Cộng Sau Cùng)](#37-cặp-7-sát-thương-chuẩn--sát-thương-lên-người)
   - [3.8. Cặp 8: Chênh Lệch Cấp Độ (Hệ Số Level trong PvP & Kháng Mob PvE)](#38-cặp-8-chênh-lệch-cấp-độ-hệ-số-level-pvp--boss-pve)
   - [3.9. Cặp 9: Trạng Thái Bất Lợi (Suy Yếu Giảm 80% Dame, Đóng Băng Giảm 50% Dame)](#39-cặp-9-trạng-thái-bất-lợi)
4. [Phần 3: Ví Dụ Tính Sát Thương Thực Chiến Từng Bước (Có Số Liệu Minh Họa Từ A Đến Z)](#4-phần-3-ví-dụ-tính-sát-thương-thực-chiến-từng-bước)
5. [Phần 4: Bảng Tra Cứu Toàn Bộ Mã Option Liên Quan Đến Chiến Đấu](#5-phần-4-bảng-tra-cứu-toàn-bộ-mã-option-liên-quan-đến-chiến-đấu)

---

## 1. SƠ ĐỒ VÒNG ĐỜI CỦA MỘT ĐÒN ĐÁNH

```mermaid
graph TD
    subgraph BƯỚC 1: XÁC ĐỊNH LỰC ĐÁNH BAN ĐẦU
        A1["1. Roll ngẫu nhiên Sát Thương Gốc giữa (Min Dame -> Max Dame)"] --> A2{"2. Mục tiêu B có đang bị BỎNG không?"}
        A2 -- "Có Bị Bỏng" --> A3["Cộng thêm 100% Dame Gốc: dameHit = dameHit + dameBasic"]
        A2 -- "Không Bị Bỏng" --> A4["Giữ nguyên dameHit"]
    end

    subgraph BƯỚC 2: XỬ LÝ NỔ BẠO KÍCH (CHÍ MẠNG)
        A3 --> B1{"3. Đòn đánh có nổ CHÍ MẠNG không?"}
        A4 --> B1
        B1 -- "Có Nổ Chí Mạng" --> B2["Tính lượng dame bạo kích thêm (dameFatal)"]
        B2 --> B3["Triệt tiêu dameFatal bởi Kháng ST Chí Mạng của B (Opt 121 + Opt 63, trần 100%)"]
        B1 -- "Không Chí Mạng" --> B4["dameFatal = 0"]
    end

    subgraph BƯỚC 3: ĐỐI KHÁNG THUỘC TÍNH HỆ (2 TẦNG CHUẨN HOÁ)
        B3 --> C1["4. TẦNG 1: Tính Kháng & Xuyên Kháng (Cường khắc sysUp, Hạ khắc haKhac/Opt 101)"]
        B4 --> C1
        C1 --> C2["Giảm ST theo kháng y = 77*x / (x+231) %, trừ % kháng hệ và cộng thưởng khắc hệ"]
        C2 --> C3["5. TẦNG 2: Tương khắc Ngũ Hành: Nhân 1.1 (Khắc) hoặc 0.9 (Bị Khắc)"]
    end

    subgraph BƯỚC 4: HỆ SỐ CẤP ĐỘ PVP & CHÊNH LỆCH LEVEL
        C3 --> D1["6. Nhân Hệ Số Level PvP: scale = max(1, 6 - (Level B - Level A) / 40) %"]
        D1 --> D2["dameHit = (dameHit + dameFatal / 3) * scale / 100"]
    end

    subgraph BƯỚC 5: XÁC SUẤT TRÚNG / TRƯỢT & PHẢN ĐÒN
        D2 --> E1{"7. Cự ly <= 60? Xử lý Phản đòn cận chiến (Opt 135: 20% HP hoặc Opt 15/91/126)"}
        E1 --> E2{"8. Xúc Xắc: Chính Xác A vs Né Đòn B?"}
        E2 -- "Trượt (Miss)" --> E3["isMiss = true (Sát thương về 0)"]
        E2 -- "Trúng (Hit)" --> E4["Đòn đánh trúng đích"]
    end

    subgraph BƯỚC 6: SUY YẾU, ĐÓNG BĂNG & GIẢM TRỪ CỐ ĐỊNH
        E4 --> F1["9. Trừ trạng thái: Suy yếu (-80%), Đóng băng (-50%)"]
        F1 --> F2["10. Trừ Giảm Trừ Sát Thương Cố Định của B: dameHit -= B.dameDown"]
    end

    subgraph BƯỚC 7: SÁT THƯƠNG CHUẨN & TRỪ MÁU
        F2 --> G1["11. Cộng Dame Người (Opt 103) & SÁT THƯƠNG CHUẨN (Opt 113) SAU CÙNG"]
        E3 --> G2["dameHit = 0 (Hiện chữ Hụt)"]
        G1 --> G3{"12. B có Bật Khiên Mana không?"}
        G3 -- "Có Khiên Mana" --> G4["Trừ % sát thương vào Mana (dameMp)"]
        G3 -- "Không Khiên" --> G5["TRỪ MÁU MỤC TIÊU B: B.hp = B.hp - dameHit"]
        G4 --> G5
    end

---

## 2. PHẦN 1: CÔNG THỨC TẠO CHỈ SỐ NHÂN VẬT

*(Được tính tự động trong file `AbilityFromEquip.java` mỗi khi đổi trang bị, cộng điểm hoặc buff chiêu)*

---

### 2.1. Điểm Chính Xác & Điểm Né Đòn

#### 🎯 1. Công thức tính ĐIỂM CHÍNH XÁC (`exactly`):

* **Công thức lời văn dễ hiểu:**
  $$\text{Chính Xác} = \text{Thân Pháp Hiệu Dụng} + \text{Tổng Chính Xác Cố Định Từ Đồ} + \text{Chính Xác Kỹ Năng Bị Động} + \text{Buff Tạm Thời}$$

* **Công thức Code trong máy chủ (`AbilityFromEquip.java`):**
  ```java
  int effThanPhap = (owner.potential[1] + potentialBonus) + (owner.potential[1] * potentialPercent / 100);
  owner.exactly = effThanPhap + stats.getFlat(StatType.ACCURACY)
          + owner.optionsSupportSkill[12] + owner.incrExactly;
  ```

* **BẢNG CHÚ GIẢI TỪNG THÀNH PHẦN TRONG CÔNG THỨC:**

| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích Rõ Ràng |
| :--- | :--- | :--- |
| `potential[1]` | **Điểm Thân Pháp Gốc** | Điểm người chơi tự tay cộng vào bảng tiềm năng (1 điểm Thân pháp = +1 Chính xác). |
| `potentialBonus` | **Cộng tiềm năng cố định** | Lấy từ `StatType.POTENTIAL` (Dòng **Option 57** trên Bùa, Mặt nạ, Thú cưỡi). |
| `potentialPercent` | **% Thân pháp tăng thêm** | Lấy từ `StatType.POTENTIAL` (Dòng **Option 58** trên Thú cưỡi, đồ kích hoạt). |
| `stats.getFlat(StatType.ACCURACY)` | **Tổng chính xác từ đồ đạc** | Tự động gom qua `OptionMapper`: Option 10 (Găng), 18 (Giày), 75 (Thú cưỡi), 86 (Ngọc khảm), 116 (Mặt nạ). |
| `optionsSupportSkill[12]` | **Chính xác từ Kỹ Năng** | Kỹ năng bị động tăng chính xác của các phái (đặc biệt là Cung, Tiêu). |
| `incrExactly` | **Chính xác phụ trợ tạm thời** | Điểm chính xác nhận thêm từ bùa chú hoặc hiệu ứng buff. |

> 🛡️ **Sửa lỗi kinh điển**: Trước đây mã Option 12 (Kháng Băng) bị cộng nhầm vào Chính Xác do sơ sót lịch sử. Trong engine mới, Option 12 đã được đưa hoàn toàn về đúng vị trí là Kháng Băng (`StatType.RES_ICE`), gỡ bỏ hoàn toàn khỏi Chính Xác!

---

#### 💨 2. Công thức tính ĐIỂM NÉ ĐÒN (`miss`):

* **Công thức lời văn dễ hiểu:**
  $$\text{Né Đòn} = \left(\text{Thân Pháp Hiệu Dụng}\right) \times 1.5 + \text{Tổng Điểm Né Đòn Từ Đồ Đạc} + \text{Né Đòn Kỹ Năng} + \text{Buff Tạm Thời}$$

* **Công thức Code trong máy chủ (`AbilityFromEquip.java`):**
  ```java
  owner.miss = (effThanPhap * 150 / 100) + stats.getFlat(StatType.EVASION)
          + owner.optionsSupportSkill[13] + owner.incrMiss;
  ```

* **BẢNG CHÚ GIẢI TỪNG THÀNH PHẦN TRONG CÔNG THỨC:**

| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích Rõ Ràng |
| :--- | :--- | :--- |
| `* 150 / 100` (tức **$\times 1.5$**) | **Hệ số quy đổi Thân Pháp ra Né Đòn** | **Quy tắc gốc của NSO**: Cứ **1 điểm Thân Pháp** tạo ra **1.5 điểm Né Đòn**. |
| `stats.getFlat(StatType.EVASION)` | **Tổng né đòn từ trang bị** | Tự động gom qua `OptionMapper`: Option 5 (Giày/Áo), 17 (Quần), 62 (Né ẩn set đồ), 68 (Áo choàng), 78 (Thú cưỡi), 84 (Ngọc khảm), 115 (Mặt nạ). |
| `optionsSupportSkill[13]` | **Né đòn từ Kỹ Năng** | Kỹ năng bị động gia tăng né tránh (Kiếm, Kunai). |
| `incrMiss` | **Né đòn phụ trợ tạm thời** | Buff né đòn tạm thời từ kỹ năng hoặc bùa. |

---

#### 🧮 VÍ DỤ MINH HỌA TÍNH CHÍNH XÁC & NÉ ĐÒN BẰNG SỐ:
Giả sử một Ninja cấp 60 có các chỉ số sau:
* Điểm tự cộng Thân pháp: `potential[1] = 400` điểm.
* Thú cưỡi có Option 58 (`Tăng 10% tiềm năng`): `potentialPercent = 10`.
* Bùa có Option 57 (`Tăng 20 điểm tiềm năng`): `potentialBonus = 20`.
* Găng tay có Option 10: `+150` Chính xác.
* Giày có Option 18: `+100` Chính xác.
* Giày có Option 5: `+200` Né đòn.
* Áo có Option 17: `+120` Né đòn.

**Các bước tính cụ thể:**
1. **Tổng Thân Pháp Hiệu Dụng**:
   $$\text{Thân Pháp} = (400 + 20) + (400 \times 10\%) = 420 + 40 = 460 \text{ điểm}$$
2. **Điểm Chính Xác (`exactly`)**:
   $$\text{Chính Xác} = 460 + 150 (\text{Găng}) + 100 (\text{Giày}) = 710 \text{ điểm}$$
3. **Điểm Né Đòn (`miss`)**:
   $$\text{Né Đòn} = (460 \times 1.5) + 200 (\text{Giày}) + 120 (\text{Áo}) = 690 + 320 = 1,010 \text{ điểm}$$

---

### 2.2. Điểm Máu Tối Đa (Max HP)

#### 1. Công thức lời văn dễ hiểu:
* **Bước 1 (Tính HP Gốc từ Thể lực):**  
  $$\text{HP Gốc} = \left(\text{Điểm Thể Lực} + \text{Cộng Tiềm Năng} + \text{Thể Lực} \times \text{\% Tăng Tiềm Năng}\right) \times 10$$
  *(Ghi nhớ: 1 điểm Thể lực luôn tạo ra **10 HP Gốc**)*.

* **Bước 2 (Tăng % HP từ Đồ Đạc & Kỹ Năng):**  
  $$\text{HP Sau Trang Bị} = \text{HP Gốc} + \left(\text{HP Gốc} \times \frac{\text{\% HP Trang Bị (Opt 31, 61) + \% HP Kỹ Năng (Opt 17)}}{100}\right) + \text{Tổng HP Cố Định}$$

* **Bước 3 (Nhân thêm % HP Cao Cấp từ Option 128):**  
  $$\text{Max HP Cuối Cùng} = \text{HP Sau Trang Bị} \times \left(1 + \frac{\text{Option 128 (\% HP sau khi mặc đồ)}}{100}\right) + \text{incrHP}$$

#### 2. Công thức Code trong máy chủ (`AbilityFromEquip.java`):
```java
int effTheLuc = (owner.potential[2] + potentialBonus) + (owner.potential[2] * potentialPercent / 100);
int basicHp = effTheLuc * 10;

int hpPercent = stats.getPercent(StatType.MAX_HP) + owner.optionsSupportSkill[17];
owner.maxHP = basicHp + (basicHp * hpPercent / 100) + stats.getFlat(StatType.MAX_HP);

if (owner.optionsSupportSkill.length > 72) {
    owner.maxHP += owner.optionsSupportSkill[72]; // Flat HP từ kỹ năng bị động
}

if (stats.getPercent(StatType.HP_AFTER_EQUIP) > 0) {
    owner.maxHP += (owner.maxHP * stats.getPercent(StatType.HP_AFTER_EQUIP) / 100); // Option 128
}

owner.maxHP += owner.incrHP;
if (owner.maxHP <= 0) owner.maxHP = 50;
```

#### 3. BẢNG CHÚ GIẢI THAM SỐ MÁU (HP):
| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích |
| :--- | :--- | :--- |
| `potential[2]` | **Điểm Thể Lực** | Điểm tự cộng vào cột Thể lực (1 điểm = 10 HP gốc). |
| `stats.getPercent(StatType.MAX_HP)` | **% Tăng HP tối đa** | Gom dòng Option 31 (trang bị) và Option 61 (ẩn kích hoạt +8). |
| `optionsSupportSkill[17]` | **% HP từ Kỹ năng** | Kỹ năng bị động tăng HP của môn phái (như skill hỗ trợ Kiếm/Đao). |
| `stats.getFlat(StatType.MAX_HP)` | **Điểm HP cố định** | Gom toàn bộ dòng Option 6 (Áo), 32 (+12), 77 (Thú), 82 (Áo choàng), 125 (Ngọc). |
| `stats.getPercent(StatType.HP_AFTER_EQUIP)` | **% HP sau khi mặc đồ** | Option 128 trên trang bị cao cấp, nhân trực tiếp trên tổng máu. |

---

### 2.3. Điểm Mana Tối Đa (Max MP)

* **Công thức Code trong máy chủ (`AbilityFromEquip.java`):**
```java
int effChakra = (owner.potential[3] + potentialBonus) + (owner.potential[3] * potentialPercent / 100);
int mpPerPoint = (owner.getSideClass() == 1) ? 5 : 8; // Nội công: 5 MP, Ngoại công: 8 MP
int basicMp = effChakra * mpPerPoint;

int mpPercent = stats.getPercent(StatType.MAX_MP) + owner.optionsSupportSkill[18];
owner.maxMP = basicMp + (basicMp * mpPercent / 100) + stats.getFlat(StatType.MAX_MP);

if (owner.optionsSupportSkill.length > 73) {
    owner.maxMP += owner.optionsSupportSkill[73]; // Flat MP từ kỹ năng bị động
}
if (owner.maxMP <= 0) owner.maxMP = 50;
```
*(Cân bằng lạm phát MP: Phái **Nội công** có $\text{mpPerPoint} = 5$, phái **Ngoại công** có $\text{mpPerPoint} = 8$ để đảm bảo phái Ngoại công không bị thiếu hụt mana khi tung combo chiêu)*.

---

### 2.4. Sát Thương Tấn Công (Ngoại Công & Nội Công)

Server phân tách thành 2 nhánh môn phái (`getSideClass()`):
* **Nhánh Ngoại Công (`Case 0`)**: Gồm **Kiếm, Kunai, Đao** $\rightarrow$ Dùng điểm **Sức Khỏe (`potential[0]`)** và các dòng Ngoại công.
* **Nhánh Nội Công (`Case 1`)**: Gồm **Tiêu, Cung, Quạt** $\rightarrow$ Dùng điểm **Chakra (`potential[3]`)** và các dòng Nội công.

#### 1. Các bước tính toán lực đánh (`AbilityFromEquip.java`):
* **Bước A - Sát Thương Tiềm Năng (`potentialDame`):**
  * Với Ngoại công: $\text{potentialDame} = (\text{potential}[0] + \text{potentialBonus}) + \left(\text{potential}[0] \times \frac{\text{potentialPercent}}{100}\right)$
  * Với Nội công: $\text{potentialDame} = (\text{potential}[3] + \text{potentialBonus}) + \left(\text{potential}[3] \times \frac{\text{potentialPercent}}{100}\right)$
* **Bước B - Tấn Công Cơ Bản (`basicAttack`):**
  $$\text{basicAttack} = \text{potentialDame} + \text{options}[38] + \left(\text{potentialDame} \times \frac{\text{skillOptions}[11]}{100}\right) + \text{buff tăng công}$$
* **Bước C - Tổng Sát Thương Tối Đa (`damage` hay Max Dame):**
  $$\text{Max Dame} = \text{basicAttack} + \text{Flat Atk Trang Bị} + \left(\text{potentialDame} \times \frac{\text{\% Atk Trang Bị} + \text{Skill Bị Động}}{100}\right) + \text{potentialDame} + \text{Opt 94} + \text{Damage Hệ}$$
* **Bước D - Sát Thương Tối Thiểu (`damage2` hay Min Dame):**
  $$\text{Min Dame} = \text{Max Dame} - 10\% = 90\% \times \text{Max Dame}$$

#### 2. BẢNG CHÚ GIẢI CÁC OPTION SÁT THƯƠNG:
| Mã Option | Tên Tiếng Việt | Áp Dụng Cho Phái Nào | Ý Nghĩa Thực Tế |
| :---: | :--- | :--- | :--- |
| **`0`** | **Ngoại công vũ khí** | Kiếm, Kunai, Đao | Điểm sát thương vật lý gốc ghi trên Vũ khí (`StatType.PHYSICAL_ATTACK`). |
| **`1`** | **Nội công vũ khí** | Tiêu, Cung, Quạt | Điểm sát thương phép thuật gốc ghi trên Vũ khí (`StatType.MAGIC_ATTACK`). |
| **`8`** | **% Tăng Vật công Ngoại** | Kiếm, Kunai, Đao | Tăng thêm % sát thương dựa trên điểm Sức khỏe. |
| **`9`** | **% Tăng Vật công Nội** | Tiêu, Cung, Quạt | Tăng thêm % sát thương dựa trên điểm Chakra. |
| **`21, 23, 25`**| **Vật công Ngoại trang bị** | Kiếm, Kunai, Đao | Điểm sát thương ngoại công trên Dây chuyền, Nhẫn, Bùa. |
| **`22, 24, 26`**| **Vật công Nội trang bị** | Tiêu, Cung, Quạt | Điểm sát thương nội công trên Dây chuyền, Nhẫn, Bùa. |
| **`38`** | **Tấn công cộng thêm** | Mọi phái | Dòng `Tấn công +X` cố định trên trang bị. |
| **`88 / 89 / 90`**| **Tấn công theo hệ** | Hỏa (88), Băng (89), Phong (90) | Tăng lực đánh khi nhân vật mang đúng hệ nguyên tố. |
| **`94`** | **% Tăng tấn công tiềm năng** | Mọi phái | Dòng siêu cấp: nhân 4 lần tiềm năng rồi lấy theo %. |

---

### 2.5. Điểm Kháng 3 Hệ (Hỏa, Băng, Phong)

* **Công thức chuẩn hóa trong `AbilityFromEquip.java`:**
  $$\text{Kháng Hệ} = \text{Tổng Dòng Kháng Riêng Của Hệ} + \text{Kháng Tất Cả Các Hệ (StatType.RES\_ALL)} + \text{Kháng Skill Bị Động} + \text{Buff Tạm Thời}$$

* **Chú giải các Option Kháng:**
  * **Kháng Hỏa riêng**: `Option 2, 11, 33, 70, 96` (Ghi trên Áo, Quần, Nón, Giáp thú cưỡi).
  * **Kháng Băng riêng**: `Option 3, 12, 34, 71, 95` *(Option 12 đã được map chuẩn xác về `StatType.RES_ICE`, không còn bị cộng nhầm vào Chính Xác)*.
  * **Kháng Phong riêng**: `Option 4, 13, 35, 72, 97`.
  * **Kháng tất cả các hệ (`StatType.RES_ALL`)**: `Option 20, 36, 81, 98` (Tự động cộng đồng thời cho cả 3 kháng Hỏa, Băng và Phong).

---

## 3. PHẦN 2: CÁC CẶP CHỈ SỐ ĐỐI KHÁNG TRỰC TIẾP TRONG GIAO TRANH

*(Được tính toán tập trung trong bộ máy `CombatService.java` khi Người Tấn Công A đánh vào Mục Tiêu B)*

---

### 3.1. Cặp 1: Chính Xác vs Né Đòn (Trúng hay Hụt)

* **Bản chất**: Quyết định đòn đánh có chạm được vào người mục tiêu hay không. Nếu hụt, sát thương lập tức bằng 0.

#### 1. Mã Code Java (`CombatService.java`):
```java
int randMiss = NinjaUtils.nextInt(target.miss + 100);
int randExactly = NinjaUtils.nextInt(attacker.exactly + 100);
boolean isMiss = randMiss > randExactly || target.isMiss;
```

#### 2. Diễn giải bằng Tiếng Việt:
* Máy chủ đổ 2 con xúc xắc ngẫu nhiên:
  * **Xúc xắc 1 (Né của B)**: Quay ngẫu nhiên từ `0` đến `(Né Đòn của B + 100)`.
  * **Xúc xắc 2 (Chính xác của A)**: Quay ngẫu nhiên từ `0` đến `(Chính Xác của A + 100)`.
* **Kết quả**:
  * Nếu **Xúc xắc Né > Xúc xắc Chính Xác** hoặc B đang có trạng thái né hoàn toàn (`target.isMiss == true`) $\rightarrow$ Đòn đánh bị **HỤT (Miss, 0 Damage)**.
  * Nếu **Xúc xắc Né $\le$ Xúc xắc Chính Xác** $\rightarrow$ Đòn đánh **TRÚNG ĐÍCH (Hit)**.

> **Giải thích con số `+ 100`**: Đây là hệ số may mắn sàn. Kể cả người có 0 né đòn thì vẫn có 1 khoảng ngẫu nhiên từ 0-99 để tránh việc 100% luôn trúng hoặc 100% luôn trượt.

---

### 3.2. Cặp 2: Chí Mạng vs Kháng Sát Thương Chí Mạng (Áp Trần 100%)

* **Bản chất**: Khi nổ đòn bạo kích (Chí Mạng), sát thương được cộng thêm một lượng lớn. Đối thủ có thể build đồ Kháng ST Chí Mạng để triệt tiêu lượng sát thương nổ thêm này.

#### 1. Mã Code Java (`CombatService.java`):
```java
int dameFatal = 0;
if (isFatal) {
    dameFatal += dameBasic;
    dameFatal += (dameBasic * (attacker.percentFatalDame + attacker.optionsSupportSkill[65])) / 100;
    dameFatal += attacker.fatalDame;
    dameFatal -= dameHit * (target.options[46] + target.options[79]) / 100;
}

// Trừ % Kháng Sát thương Chí mạng (Option 63 & 121) - Áp trần tối đa 100%
int kstcm = Math.min(100, Math.max(0, target.options[121] + target.options[63]));
dameFatal -= dameFatal * kstcm / 100;
if (dameFatal < 0) {
    dameFatal = 0;
}
```

#### 2. Diễn giải bằng Tiếng Việt:
* **Sát Thương Chí Mạng Thô**:
  $$\text{dameFatal Thô} = \text{dameBasic} + \left(\text{dameBasic} \times \frac{\text{\% ST Chí Mạng của A}}{100}\right) + \text{fatalDame} - \text{dameHit} \times \frac{\text{Opt 46 + Opt 79}}{100}$$
* **Triệt tiêu bởi Kháng ST Chí Mạng % (`Option 121 + Option 63`)**:
  $$\text{dameFatal Sau Kháng} = \text{dameFatal Thô} \times \left(1 - \frac{\text{kstcm (Tối đa 100\%)}}{100}\right)$$

> 🛡️ **Chuẩn hóa cân bằng**: 
> - Option 63 được gom trực tiếp vào Kháng ST Chí Mạng (thay vì trừ % thô trên toàn bộ đòn đánh gây lỗi bất tử).
> - Áp trần tối đa **100% Kháng STCM**: Nếu mục tiêu đạt 100% kháng bạo kích, phần sát thương chí mạng cộng thêm `dameFatal` sẽ bằng **0**, đòn đánh gây sát thương bình thường như không chí mạng.

---

### 3.3. Cặp 3: Khắc Hệ & Xuyên Kháng (2 Tầng Xử Lý Chuẩn Hóa)

Trong engine `CombatService.java`, hệ thống tương khắc được phân tách thành **2 tầng độc lập**:

#### TẦNG 1: Tính toán Kháng & Xuyên Kháng (`applyResistanceAndPenetration`)
```java
int sysUp = Math.max(0, attacker.sysUp);
int haKhac = Math.max(0, attacker.sysDown + attacker.options[101]);
dameHit = applyResistanceAndPenetration(dameHit, rawRes, sysUp, haKhac);
```

* **Công thức toán học giảm sát thương theo kháng:**
  $$\text{Giảm Trừ \%} = \frac{77 \times \text{effectiveRes}}{\text{effectiveRes} + 231} \quad (\%)$$
  - `delta = rawRes - sysUp` (Cường khắc trừ thẳng điểm kháng).
  - Nếu `delta >= 0`: Hạ khắc (`haKhac`) bỏ qua tiếp % lượng kháng còn lại:
    $$\text{effectiveRes} = \text{delta} \times \left(1 - \frac{\text{min}(100, \text{haKhac})}{100}\right)$$
    $$\text{dameHit} = \text{dameHit} \times \left(1 - \frac{\text{Giảm Trừ \%}}{100}\right)$$
  - Nếu `delta < 0` (Kháng âm): Mục tiêu bị thủng giáp kháng, nhận thêm sát thương:
    $$\text{Sát Thương Thêm} = (-\text{delta}) \times \left(1 + \frac{\text{haKhac}}{100}\right)$$
    $$\text{dameHit} = \text{dameHit} + \text{Sát Thương Thêm}$$

* Sau đó trừ tiếp % Kháng Hệ (`Option 127/130/131`) và cộng Thưởng Hệ (`Option 51..56`):
```java
switch (attacker.getSys()) {
    case 1: // Hỏa
        dameHit -= dameHit * target.options[127] / 100;
        dameHit += attacker.options[51] + dameBasic * attacker.options[55] / 100;
        break;
    case 2: // Băng
        dameHit -= dameHit * target.options[130] / 100;
        dameHit += attacker.options[52] + dameBasic * attacker.options[56] / 100;
        break;
    case 3: // Phong
        dameHit -= dameHit * target.options[131] / 100;
        dameHit += attacker.options[53] + dameBasic * attacker.options[54] / 100;
        break;
}
```

#### TẦNG 2: Tương Khắc Ngũ Hành (`getSysRelation`)
* **Vòng tròn tương khắc**:
  * **Hỏa (1) khắc Phong (3)**
  * **Phong (3) khắc Băng (2)**
  * **Băng (2) khắc Hỏa (1)**

```java
int relation = getSysRelation(attacker.getSys(), target.getSys());
if (relation == 1) {
    dameHit = (int) Math.round(dameHit * 1.1); // Khắc hệ: Tăng 10% sát thương
} else if (relation == -1) {
    dameHit = (int) Math.round(dameHit * 0.9); // Bị khắc hệ: Giảm 10% sát thương
}
```

---

### 3.4. Cặp 4: Bỏng Lửa Hệ Hỏa (Nhân Đôi Dame Gốc)

```java
if (target.isFire) {
    dameHit += dameBasic; // Cộng thêm đúng bằng 100% Sát thương cơ bản gốc!
}
```
* **Bản chất**: Khi mục tiêu B đang bị ngọn lửa thiêu đốt (`target.isFire == true`), bất kỳ đòn đánh tiếp theo nào bồi vào người B sẽ được **cộng thêm 1 lần Sát Thương Cơ Bản Gốc (tương đương $\times 2$ sát thương đầu vào)**.

---

### 3.5. Cặp 5: Loại Bỏ Miễn Giảm % Thô & Cơ Chế Giảm Trừ Cố Định (`dameDown`)

* **Tại sao loại bỏ Miễn giảm % thô?**
  Trước đây, việc cộng dồn `% Miễn giảm` (Option 63 + 98) trực tiếp theo công thức `dameHit -= dameHit * (63 + 98) / 100` khiến người chơi đeo đồ VIP đạt $\ge 100\%$ miễn giảm và **hoàn toàn bất tử (nhận 0 damage)**.
* **Cơ chế chuẩn hóa hiện tại:**
  1. **Option 63**: Quy về `% Kháng ST Chí Mạng` (áp trần 100%).
  2. **Option 98**: Quy về `StatType.RES_ALL` (Cộng thẳng điểm Kháng tất cả).
  3. **Option 136**: Bùa hộ thể tăng điểm kháng tất cả trong 5 giây.
  4. **Giảm trừ sát thương cố định (`dameDown`)**:
     ```java
     dameHit -= target.dameDown;
     if (dameHit < 0) {
         dameHit = 0;
     }
     ```
     `dameDown` (gom từ Option 47, 74, 80, 124) trừ thẳng điểm cố định trước khi cộng Sát thương chuẩn.

---

### 3.6. Cặp 6: Phản Đòn Cận Chiến (Bán kính <= 60) & Phản Đòn Băng Giá (20% HP)

```java
if (!attacker.isNhanBan) {
    int distance = NinjaUtils.getDistance(attacker.x, attacker.y, target.x, target.y);
    boolean isCloseRange = distance <= 60; // Chỉ kích hoạt khi cự ly cận chiến <= 60px
    if (isCloseRange) {
        if (target.options[135] > 0 && NinjaUtils.nextInt(100) < target.options[135]) {
            reactDame = attacker.hp * 20 / 100; // Phản 20% lượng HP hiện tại của A
            isIceReflect = true;
        } else if (target.reactDame > 0) {
            reactDame = calculateReflectDamageOnChar(target, attacker, target.reactDame);
        }
    }
}
```
* **Quy chuẩn kỹ thuật**:
  - Phản đòn chỉ kích hoạt khi người tấn công đứng trong phạm vi cận chiến ($\le 60\text{px}$). Đứng xa tỉa không bao giờ bị phản đòn.
  - Phản đòn thông thường (`calculateReflectDamageOnChar`) đi qua Kháng nguyên tố, % Kháng, Ngũ hành tương khắc và trừ qua `dameDown` của người tấn công.

---

### 3.7. Cặp 7: Sát Thương Lên Người & Sát Thương Chuẩn (Cộng Sau Cùng)

```java
// Thêm Sát thương lên người (Option 103)
dameHit += attacker.options[103];

// CHUẨN HÓA: CỘNG SÁT THƯƠNG CHUẨN (Option 113) SAU CÙNG
// Sát thương chuẩn không bị cản bởi dameDown, suy yếu hay đóng băng
dameHit += attacker.options[113];
```
* **Đặc tính cốt tử của Sát Thương Chuẩn (Option 113)**:
  - Được cộng vào kết quả **sau khi đã hoàn tất mọi bước trừ giáp, kháng, dameDown, suy yếu và đóng băng**.
  - Do đó, nếu người chơi sở hữu `+500 Sát thương chuẩn`, mục tiêu dù có $10,000 \text{ dameDown}$ hay người đánh bị suy yếu $80\%$, đòn đánh vẫn đảm bảo gây tối thiểu **đủ 500 máu**.

---

### 3.8. Cặp 8: Chênh Lệch Cấp Độ (Hệ Số Level PvP & Kháng Mob PvE)

#### 1. Trong PvP (Người đánh Người):
```java
int levelDiff = (target.level - attacker.level) / 40;
int scale = 6 - levelDiff;
if (scale < 1) scale = 1;
dameHit = (dameHit + (dameFatal / 3)) * scale / 100;
```
* **Tỷ lệ chuẩn 6%**: Hai người bằng cấp nhau (`levelDiff = 0`) thì sát thương co về tỷ lệ chuẩn 6% của engine NSO để trận chiến kéo dài hấp dẫn.
* Cấp cao đánh cấp thấp: `scale` tăng lên đến 7% - 8%.
* Cấp thấp đánh cấp cao: `scale` bị nén xuống còn 1% - 5%.

#### 2. Trong PvE (Đánh Quái Vật & Boss Thế Giới):
* **Công thức Kháng nguyên tố của Quái theo Level (`CombatService.getMobResistance`)**:
  $$y = \frac{12 \times \text{level}^2}{\text{level} + 144}$$
  - Kháng tương khắc (hệ bị quái khắc): $y$ điểm.
  - Kháng cùng hệ: $0.77 \times y$ điểm.
  - Kháng bị khắc: $0.33 \times y$ điểm.
  - **Kỹ năng Magumandari (Chiêu 25 Đao)**: Khi quái bị dính bùa, giảm trực tiếp 30 - 60 điểm Kháng tất cả.
* **Giới hạn Level Boss**: Nếu chênh lệch cấp độ $|\text{Level}_A - \text{Level}_{\text{Boss}}| > 20 \rightarrow$ **100% Đánh Hụt (Miss, 0 Damage)**.

---

### 3.9. Cặp 9: Trạng Thái Bất Lợi

* **Suy Yếu / Trúng Độc (`A.isInfected()`):** Người đánh A bị **giảm 80% Sát thương đầu ra** (`dameHit -= dameHit * 80 / 100`).
* **Lạnh Giá / Đóng Băng (`A.isCool()`):** Người đánh A bị **giảm 50% Sát thương đầu ra** (`dameHit -= dameHit * 50 / 100`).

---

## 4. PHẦN 3: VÍ DỤ TÍNH SÁT THƯƠNG THỰC CHIẾN TỪNG BƯỚC

Hãy cùng theo dõi kịch bản giao tranh chuẩn hóa theo `CombatService.java`:

### 🥋 Hồ Sơ Hai Nhân Vật:
* **Người Tấn Công A**: Phái Kiếm (Hệ Hỏa), Cấp 100.
  * Sát thương tối thiểu `damage2` = `18,000`, Sát thương tối đa `damage` = `20,000`.
  * % Sát thương chí mạng = `50%`, ST chí mạng cố định = `500`.
  * Cường khắc: `sysUp = 50`.
  * Hạ khắc (Skill + Option 101): `haKhac = 20%`.
  * Dòng thưởng đánh hệ Phong: Option 51 = `200`, Option 55 = `20%`.
  * Sát thương lên người (Option 103) = `150`.
  * Sát thương chuẩn (Option 113) = `300`.
* **Người Phòng Thủ B**: Phái Quạt (Hệ Phong), Cấp 100.
  * Trạng thái: Đang bị **Bỏng** (`isFire = true`).
  * Kháng Hỏa thô (`rawRes`): `1,000`.
  * % Kháng Hỏa (`Option 127`): `10%`.
  * % Kháng ST Chí Mạng (`Option 121 + Option 63`): `40%`.
  * Giảm trừ sát thương cố định (`dameDown`): `200`.

---

### 🧮 Quá Trình Tính Toán Qua 8 Bước (`CombatService.calculatePvPDamage`):

* **Bước 1: Rút ngẫu nhiên sát thương & Hiệu ứng Bỏng**
  * Roll ngẫu nhiên giữa 18,000 và 20,000 $\rightarrow$ Ra `dameHit = 19,000` (Gán `dameBasic = 19,000`).
  * B đang bị **Bỏng**: $\text{dameHit} = 19,000 + 19,000 = 38,000$.

* **Bước 2: Xử lý nổ Chí Mạng**
  * Đòn đánh nổ chí mạng thành công (`isFatal = true`):
    $$\text{dameFatal Thô} = 19,000 + (19,000 \times 50\%) + 500 = 29,000$$
  * Trừ Kháng ST Chí Mạng của B (`kstcm = 40%`):
    $$\text{dameFatal Sau Kháng} = 29,000 \times (1 - 0.40) = 17,400$$

* **Bước 3: Đối kháng thuộc tính hệ (2 Tầng)**
  * **Tầng 1 - Kháng & Xuyên kháng:**
    - $\text{delta} = \text{rawRes} - \text{sysUp} = 1,000 - 50 = 950$.
    - $\text{effectiveRes} = 950 \times (1 - 0.20) = 760$.
    - $\text{reducePercent} = \frac{77 \times 760}{760 + 231} = \frac{58,520}{991} \approx 59.05\%$.
    - $\text{dameHit} = 38,000 - (38,000 \times 59.05\%) = 15,561$.
    - Trừ % Kháng Hỏa (10%): $15,561 \times (1 - 0.10) = 14,005$.
    - Cộng Thưởng đánh hệ Phong: $14,005 + 200 + (19,000 \times 20\%) = 14,005 + 200 + 3,800 = 18,005$.
  * **Tầng 2 - Ngũ hành tương khắc (`getSysRelation`):**
    - A thuộc Hỏa, B thuộc Phong $\rightarrow$ Hỏa khắc Phong (`relation = 1`):
    $$\text{dameHit} = \text{round}(18,005 \times 1.1) = 19,806$$

* **Bước 4: Nhân Hệ Số Level PvP (Đồng cấp 100 $\rightarrow$ Scale = 6%)**
  $$\text{dameHit} = \left(19,806 + \frac{17,400}{3}\right) \times 6\% = (19,806 + 5,800) \times 0.06 = 25,606 \times 0.06 = 1,536$$

* **Bước 5: Xác suất Trúng / Trượt & Phản Đòn**
  * Xúc xắc: Đòn đánh trúng đích (`isMiss = false`).
  * Cự ly > 60px: Không kích hoạt phản đòn cận chiến.

* **Bước 6: Trừ Giảm Trừ Sát Thương Cố Định (`dameDown`)**
  $$\text{dameHit} = 1,536 - 200 = 1,336$$

* **Bước 7: Cộng Sát Thương Lên Người & SÁT THƯƠNG CHUẨN**
  * Cộng Sát thương lên người (Opt 103): $1,336 + 150 = 1,486$.
  * **Cộng Sát thương chuẩn (Opt 113) sau cùng**: $1,486 + 300 = 1,786$.

* **Bước 8: Trừ Máu Mục Tiêu B**
  * **Thực thi trừ máu:** `B.addHp(-1786)`.
  * Màn hình Client hiện số sát thương bạo kích vàng rực: **`-1786 HP`**.

---

## 5. PHẦN 4: BẢNG TRA CỨU TOÀN BỘ MÃ OPTION CHIẾN ĐẤU

| Mã Option ID | Tên Thuộc Tính Trong Game | Nơi Xuất Hiện & Cách Thức Hoạt Động |
| :---: | :--- | :--- |
| **`0 / 1`** | Vũ khí Ngoại công / Nội công | Cộng trực tiếp vào lực tấn công (`StatType.PHYSICAL/MAGIC_ATTACK`). |
| **`2 / 3 / 4`** | Kháng Hỏa / Kháng Băng / Kháng Phong | Điểm kháng cố định, trừ qua công thức $y = 77x / (x+231) \%$. |
| **`5 / 17 / 62`**| Né đòn cố định | Cộng dồn vào điểm Né đòn (`StatType.EVASION`). |
| **`8 / 9`** | % Tăng Vật công Ngoại / Nội | Nhân % theo điểm tiềm năng Sức khỏe (Ngoại) hoặc Chakra (Nội). |
| **`10 / 18 / 75`**| Chính xác cố định | Cộng dồn vào điểm Chính xác (`StatType.ACCURACY`). |
| **`12`** | Kháng Băng cố định | **Đã sửa lỗi**: Map đúng về `StatType.RES_ICE`, không còn bị nhầm vào Chính Xác. |
| **`14 / 37 / 69`**| Chí mạng cố định | Tăng điểm tỷ lệ nổ đòn đánh bạo kích (`StatType.CRIT_RATE`). |
| **`15 / 91 / 126`**| Phản đòn cận chiến | Phản sát thương khi bị đánh ở cự ly $\le 60\text{px}$. |
| **`20 / 36 / 81`**| Kháng tất cả các hệ | Cộng đồng thời vào cả 3 kháng Hỏa, Băng, Phong (`StatType.RES_ALL`). |
| **`28 / 60`** | % MP tối đa | Tăng % mana tối đa (Cân bằng lạm phát MP: 5 MP nội, 8 MP ngoại). |
| **`31 / 61`** | % HP tối đa | Tăng % máu dựa trên lượng HP gốc. |
| **`38`** | Tấn công cộng thêm | Cộng điểm sát thương cố định vào tấn công cơ bản. |
| **`39 / 67`** | % Tăng sát thương chí mạng | Tăng thêm lượng damage khi nổ chí mạng (`percentFatalDame`). |
| **`40 / 41 / 42`**| Giảm thời gian Bỏng / Băng / Choáng| Rút ngắn thời gian chịu trạng thái khống chế bất lợi. |
| **`46 / 79`** | % Giảm sát thương chí mạng | Giảm bớt lượng sát thương bạo kích của đối phương. |
| **`47 / 74 / 80`**| Giảm trừ sát thương cố định | Trừ thẳng vào sát thương sau cùng (`dameDown`). |
| **`51 / 52 / 53`**| Tăng sát thương lên hệ bị khắc | Tăng điểm sát thương khi đánh đối thủ thuộc hệ bị khắc chế. |
| **`54 / 55 / 56`**| % Tăng sát thương lên hệ bị khắc | Tăng % sát thương khi đánh đối thủ thuộc hệ bị khắc chế. |
| **`57 / 58`** | Cộng tiềm năng / % Tăng tiềm năng | Tăng trực tiếp cả 4 chỉ số Sức khỏe, Thân pháp, Thể lực, Chakra. |
| **`63`** | % Kháng ST Chí Mạng PvP | Quy chuẩn về nhóm Kháng STCM (cùng Opt 121, cap 100%) để chống bất tử. |
| **`88 / 89 / 90`**| Sát thương Hỏa / Băng / Phong | Tấn công nguyên tố cộng thẳng theo hệ nhân vật. |
| **`94`** | % Tấn công tiềm năng | Nhân 4 lần tiềm năng rồi lấy theo %. |
| **`98`** | Kháng tất cả các hệ | Quy chuẩn về `StatType.RES_ALL` thay vì trừ % thô để chống bất tử. |
| **`101`** | Bỏ qua kháng tính (% Xuyên kháng) | Kết hợp với Hạ khắc kỹ năng (`sysDown`), giảm % kháng của mục tiêu. |
| **`103`** | Sát thương lên người chơi | Sát thương cố định cộng thêm trong PvP. |
| **`105`** | Sát thương chí mạng cố định | Cộng điểm sát thương cố định vào đòn chí mạng (`fatalDame`). |
| **`113`** | Sát thương chuẩn | **True Damage**: Cộng sau cùng, không bị cản bởi giáp, kháng hay dameDown. |
| **`121`** | % Kháng sát thương chí mạng | Triệt tiêu % sát thương bạo kích (áp trần tối đa 100%). |
| **`127 / 130 / 131`**| % Kháng Hỏa / Băng / Phong | Khấu trừ theo % sát thương nguyên tố tương ứng. |
| **`128`** | % Tăng HP sau khi mặc đồ | Dòng VIP nhân tiếp theo % trên tổng lượng HP sau trang bị. |
| **`135`** | % Tỷ lệ Phản đòn Băng giá | Phản lại ngay 20% lượng HP hiện tại của kẻ tấn công ở cự ly gần. |
| **`136`** | Bùa hộ thể kích hoạt | Tăng điểm kháng tất cả trong 5 giây (Tỷ lệ 10%, cooldown 40s). |

