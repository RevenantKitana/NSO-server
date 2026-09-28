# SỔ TAY GIẢI MÃ TOÀN BỘ CHỈ SỐ CHIẾN ĐẤU & CÔNG THỨC TÍNH SÁT THƯƠNG NSO
### (DÀNH CHO ADMIN & NGƯỜI CHƠI - 100% TIẾNG VIỆT - CÓ CHÚ GIẢI & VÍ DỤ TỪNG DÒNG)

> **Phiên bản máy chủ**: NSO Ninja School Private 2026  
> **Nguồn trích xuất từ Codebase**:  
> - `AbilityFromEquip.java`: Bộ máy tính toán và tổng hợp chỉ số nhân vật từ trang bị, tiềm năng, kỹ năng.  
> - `Char.java`: Bộ máy tính toán sát thương thời gian thực khi đánh người (PvP) hoặc đánh quái (PvE).  
> - `Mob.java`: Quy tắc né đòn và giới hạn level khi đánh Boss.

---

## 🧭 HƯỚNG DẪN ĐỌC TÀI LIỆU DÀNH CHO NGƯỜI MỚI

Khi bạn nhìn vào mã nguồn của game, game dùng các biến mảng tiếng Anh. Tài liệu này dịch toàn bộ sang tiếng Việt như sau:

| Ký Hiệu trong Code | Tên Gọi Tiếng Việt | Giải Thích Bản Chất Rất Dễ Hiểu |
| :--- | :--- | :--- |
| **`A`** (hoặc `this`) | **Người Tấn Công** | Là nhân vật đang bấm phím tung ra chiêu thức tấn công. |
| **`B`** (hoặc `pl`) | **Người Phòng Thủ** | Là mục tiêu (Người chơi khác hoặc Quái) đang chịu đòn. |
| **`potential[0]`** | **Điểm Sức Khỏe** | Điểm tiềm năng bạn tự cộng vào cột Sức khỏe (Tăng Ngoại công cho Kiếm, Kunai, Đao). |
| **`potential[1]`** | **Điểm Thân Pháp** | Điểm tiềm năng bạn tự cộng vào cột Thân pháp (Tăng Chính xác và Né đòn). |
| **`potential[2]`** | **Điểm Thể Lực** | Điểm tiềm năng bạn tự cộng vào cột Thể lực (Tăng Máu HP: 1 điểm = 10 HP gốc). |
| **`potential[3]`** | **Điểm Chakra** | Điểm tiềm năng bạn tự cộng vào cột Chakra (Tăng Mana MP: 1 điểm = 10 MP và tăng Nội công cho Tiêu, Cung, Quạt). |
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
   - [3.2. Cặp 2: Chí Mạng vs Kháng Sát Thương Chí Mạng](#32-cặp-2-chí-mạng-vs-kháng-sát-thương-chí-mạng)
   - [3.3. Cặp 3: Khắc Hệ (Hỏa $\rightarrow$ Phong $\rightarrow$ Băng $\rightarrow$ Hỏa) & Xuyên Kháng](#33-cặp-3-khắc-hệ--xuyên-kháng)
   - [3.4. Cặp 4: Bỏng Lửa Hệ Hỏa (Nhân Đôi Dame Gốc)](#34-cặp-4-bỏng-lửa-hệ-hỏa-nhân-đôi-dame-gốc)
   - [3.5. Cặp 5: Miễn Giảm Sát Thương % & Giảm Trừ Cố Định](#35-cặp-5-miễn-giảm-sát-thương--giảm-trừ-cố-định)
   - [3.6. Cặp 6: Phản Đòn Cận Chiến & Phản Đòn Băng Giá (20% HP)](#36-cặp-6-phản-đòn-cận-chiến--phản-đòn-băng-giá-20-hp)
   - [3.7. Cặp 7: Sát Thương Chuẩn & Sát Thương Lên Người](#37-cặp-7-sát-thương-chuẩn--sát-thương-lên-người)
   - [3.8. Cặp 8: Chênh Lệch Cấp Độ (Hệ Số Level trong PvP & Quy Tắc Boss PvE)](#38-cặp-8-chênh-lệch-cấp-độ-hệ-số-level-pvp--boss-pve)
   - [3.9. Cặp 9: Trạng Thái Bất Lợi (Suy Yếu Giảm 80% Dame, Đóng Băng Giảm 50% Dame)](#39-cặp-9-trạng-thái-bất-lợi)
4. [Phần 3: Ví Dụ Tính Sát Thương Thực Chiến Từng Bước (Có Số Liệu Minh Họa Từ A Đến Z)](#4-phần-3-ví-dụ-tính-sát-thương-thực-chiến-từng-bước)
5. [Phần 4: Bảng Tra Cứu Toàn Bộ Mã Option Liên Quan Đến Chiến Đấu](#5-phần-4-bảng-tra-cứu-toàn-bộ-mã-option-liên-quan-đến-chiến-đấu)

---

## 1. SƠ ĐỒ VÒNG ĐỜI CỦA MỘT ĐÒN ĐÁNH

```mermaid
graph TD
    subgraph BƯỚC 1: XÁC ĐỊNH LỰC ĐÁNH BAN ĐẦU
        A1["1. Quay ngẫu nhiên Sát Thương Gốc giữa (Min Dame -> Max Dame)"] --> A2{"2. Mục tiêu B có đang bị BỎNG không?"}
        A2 -- "Có Bị Bỏng" --> A3["Cộng thêm 100% Dame Gốc: dameHit = dameHit + dameBasic"]
        A2 -- "Không Bị Bỏng" --> A4["Giữ nguyên dameHit"]
    end

    subgraph BƯỚC 2: XỬ LÝ NỔ BẠO KÍCH (CHÍ MẠNG)
        A3 --> B1{"3. Đòn đánh có nổ CHÍ MẠNG không?"}
        A4 --> B1
        B1 -- "Có Nổ Chí Mạng" --> B2["Tính lượng dame bạo kích thêm (dameFatal)"]
        B2 --> B3["Triệt tiêu dameFatal bởi % Kháng ST Chí Mạng của B (Option 121)"]
        B1 -- "Không Chí Mạng" --> B4["dameFatal = 0"]
    end

    subgraph BƯỚC 3: ĐỐI KHÁNG THUỘC TÍNH HỆ (HỎA / BĂNG / PHONG)
        B3 --> C1["4. Kiểm tra Hệ của Người Đánh A"]
        B4 --> C1
        C1 --> C2["Trừ điểm Kháng Hệ cố định của B (Nếu không bị Xuyên Kháng Option 101)"]
        C2 --> C3["Trừ tiếp % Kháng Hệ của B (Option 127/130/131)"]
        C3 --> C4["Cộng thêm Thưởng Sát Thương Khắc Hệ của A (Option 51..56)"]
    end

    subgraph BƯỚC 4: QUA CÁC TẦNG PHÒNG THỦ & CHÊNH CẤP
        C4 --> D1["5. Khấu trừ Miễn Giảm Sát Thương % của B (Option 63 + Option 98)"]
        D1 --> D2["6. Nhân Hệ Số Chênh Lệch Level: (6 - (Level B - Level A) / 40) / 100"]
        D2 --> D3["7. Cộng Sát Thương Chuẩn (Option 113) & Dame Người (Option 103)"]
        D3 --> D4["8. Trừ Giảm Trừ Sát Thương Cố Định Sau Cùng (dameDown)"]
    end

    subgraph BƯỚC 5: XÁC SUẤT TRÚNG / TRƯỢT & TRỪ MÁU
        D4 --> E1{"9. Xúc Xắc: Chính Xác A vs Né Đòn B?"}
        E1 -- "Trượt (Miss)" --> E2["Sát thương = 0 (Hiện chữ Hụt trên đầu B)"]
        E1 -- "Trúng (Hit)" --> E3["Xử lý Phản Đòn: Trừ ngược máu của A nếu B có Phản Dame"]
        E3 --> E4["10. TRỪ MÁU MỤC TIÊU B: B.hp = B.hp - dameHit"]
    end
```

---

## 2. PHẦN 1: CÔNG THỨC TẠO CHỈ SỐ NHÂN VẬT

*(Được tính tự động trong file `AbilityFromEquip.java` mỗi khi đổi trang bị, cộng điểm hoặc buff chiêu)*

---

### 2.1. Điểm Chính Xác & Điểm Né Đòn

#### 🎯 1. Công thức tính ĐIỂM CHÍNH XÁC (`exactly`):

* **Công thức lời văn dễ hiểu:**
  $$\text{Chính Xác} = \text{Thân Pháp Tự Cộng} + \text{Thân Pháp Tăng Thêm Từ \% Tiềm Năng} + \text{Cộng Tiềm Năng Cố Định} + \text{Tổng Điểm Chính Xác Từ Đồ} + \text{Chính Xác Từ Kỹ Năng}$$

* **Công thức Code trong máy chủ:**
  ```java
  owner.exactly = owner.potential[1] 
                + (owner.potential[1] * owner.options[58] / 100) 
                + owner.options[57] 
                + owner.options[12] 
                + owner.options[10] + owner.options[18] + owner.options[75] 
                + owner.options[86] + owner.options[116] 
                + owner.optionsSupportSkill[12] 
                + owner.incrExactly;
  ```

* **BẢNG CHÚ GIẢI TỪNG THÀNH PHẦN TRONG CÔNG THỨC:**

| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích Rõ Ràng |
| :--- | :--- | :--- |
| `potential[1]` | **Điểm Thân Pháp Gốc** | Điểm người chơi tự tay cộng vào bảng tiềm năng (1 điểm Thân pháp = +1 Chính xác). |
| `potential[1] * options[58] / 100` | **Thân pháp tăng thêm do %** | Được tính từ dòng **Option 58** (*"Tăng X% điểm tiềm năng"* trên Thú cưỡi, đồ kích hoạt). |
| `options[57]` | **Cộng tiềm năng cố định** | Dòng **Option 57** (*"Tăng X điểm tiềm năng"* trên Bùa, Mặt nạ, Thú cưỡi). |
| `options[10]` | **Chính xác từ Găng tay / Vũ khí** | Dòng thuộc tính `Chính xác +X` ghi trên Găng tay và Vũ khí. |
| `options[18]` | **Chính xác từ Giày** | Dòng thuộc tính `Chính xác +X` ghi trên Giày. |
| `options[75]` | **Chính xác từ Thú Cưỡi** | Dòng thuộc tính `Chính xác +X` trên Trang bị Thú Cưỡi. |
| `options[86]` | **Chính xác từ Ngọc Khảm** | Dòng thuộc tính `Chính xác +X` khi khảm ngọc vào đồ. |
| `options[116]` | **Chính xác từ Mặt Nạ / Biến Thân** | Dòng thuộc tính `Chính xác +X` trên Mặt nạ hoặc Ngoại trang đặc biệt. |
| `optionsSupportSkill[12]` | **Chính xác từ Kỹ Năng** | Kỹ năng bị động tăng chính xác của các phái (đặc biệt là Cung, Tiêu). |
| `incrExactly` | **Chính xác phụ trợ tạm thời** | Điểm chính xác nhận thêm từ bùa chú hoặc hiệu ứng buff. |

---

#### 💨 2. Công thức tính ĐIỂM NÉ ĐÒN (`miss`):

* **Công thức lời văn dễ hiểu:**
  $$\text{Né Đòn} = \left(\text{Tổng Điểm Thân Pháp Sau Khi Nhân \%}\right) \times 1.5 + \text{Tổng Điểm Né Đòn Từ Đồ Đạc} + \text{Né Đòn Từ Kỹ Năng}$$

* **Công thức Code trong máy chủ:**
  ```java
  owner.miss = (owner.potential[1] + owner.options[57] + (owner.potential[1] * owner.options[58] / 100)) * 150 / 100
             + owner.options[5] + owner.options[17] + owner.options[62] 
             + owner.options[68] + owner.options[78] + owner.options[84] 
             + owner.options[115] + owner.optionsSupportSkill[13] 
             + owner.incrMiss;
  ```

* **BẢNG CHÚ GIẢI TỪNG THÀNH PHẦN TRONG CÔNG THỨC:**

| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích Rõ Ràng |
| :--- | :--- | :--- |
| `* 150 / 100` (tức **$\times 1.5$**) | **Hệ số quy đổi Thân Pháp ra Né Đòn** | **Quy tắc gốc của NSO**: Cứ **1 điểm Thân Pháp** sẽ tạo ra **1.5 điểm Né Đòn** (Ví dụ 100 Thân pháp cho 150 Né đòn). |
| `options[5]` | **Né đòn từ Giày / Áo** | Dòng thuộc tính `Né đòn +X` ghi trên Giày và Áo. |
| `options[17]` | **Né đòn từ Quần** | Dòng thuộc tính `Né đòn +X` ghi trên Quần. |
| `options[62]` | **Né đòn ẩn theo Set Đồ** | Dòng thuộc tính ẩn tự kích hoạt khi người chơi mặc đủ bộ đồ cùng hệ. |
| `options[68]` | **Né đòn từ Áo Choàng** | Dòng thuộc tính `Né đòn +X` trên Áo choàng / Đồ thời trang. |
| `options[78]` | **Né đòn từ Thú Cưỡi** | Dòng thuộc tính `Né đòn +X` trên Trang bị Thú Cưỡi. |
| `options[84]` | **Né đòn từ Ngọc Khảm** | Dòng thuộc tính `Né đòn +X` từ các viên ngọc khảm vào trang bị. |
| `options[115]` | **Né đòn từ Mặt Nạ** | Dòng thuộc tính `Né đòn +X` trên Mặt nạ / Biến thân. |
| `optionsSupportSkill[13]` | **Né đòn từ Kỹ Năng** | Kỹ năng bị động gia tăng thân pháp / né tránh (như phái Kiếm, Kunai). |

---

#### 🧮 VÍ DỤ MINH HỌA TÍNH CHÍNH XÁC & NÉ ĐÒN BẰNG SỐ:
Giả sử một Ninja cấp 60 có các chỉ số sau:
* Điểm tự cộng Thân pháp: `potential[1] = 400` điểm.
* Thú cưỡi có Option 58 (`Tăng 10% tiềm năng`): `options[58] = 10`.
* Bùa có Option 57 (`Tăng 20 điểm tiềm năng`): `options[57] = 20`.
* Găng tay có Option 10: `+150` Chính xác.
* Giày có Option 18: `+100` Chính xác.
* Giày có Option 5: `+200` Né đòn.
* Áo có Option 17: `+120` Né đòn.

**Các bước tính cụ thể:**
1. **Tổng Thân Pháp Hiệu Dụng**:
   $$\text{Thân Pháp} = 400 + (400 \times 10\%) + 20 = 400 + 40 + 20 = 460 \text{ điểm}$$
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
  $$\text{HP Sau Trang Bị} = \text{HP Gốc} + \left(\text{HP Gốc} \times \frac{\text{\% HP Trang Bị (Opt 31) + \% HP Ẩn (Opt 61) + \% HP Kỹ Năng}}{100}\right) + \text{Tổng HP Cố Định}$$

* **Bước 3 (Nhân thêm % HP Cao Cấp từ Option 128):**  
  $$\text{Max HP Cuối Cùng} = \text{HP Sau Trang Bị} + \left(\text{HP Sau Trang Bị} \times \frac{\text{Option 128 (\% HP sau khi mặc đồ)}}{100}\right)$$

#### 2. Công thức Code trong máy chủ:
```java
int basicHp = (owner.potential[2] + owner.options[57] + (owner.potential[2] * owner.options[58] / 100)) * 10;
owner.maxHP = basicHp + (basicHp * (owner.options[31] + owner.options[61] + owner.optionsSupportSkill[17]) / 100);
owner.maxHP += owner.options[6] + owner.options[32] + owner.options[77] + owner.options[82] + owner.options[125];
owner.maxHP += (owner.maxHP * owner.options[128] / 100); // Dòng VIP nhân tiếp theo % trên tổng HP
```

#### 3. BẢNG CHÚ GIẢI THAM SỐ MÁU (HP):
| Biến / Tham Số | Tên Tiếng Việt | Nguồn Gốc & Giải Thích |
| :--- | :--- | :--- |
| `potential[2]` | **Điểm Thể Lực** | Điểm tự cộng vào cột Thể lực (1 điểm = 10 HP gốc). |
| `options[31]` | **% Tăng HP tối đa** | Dòng thuộc tính `Tăng X% HP` trên Áo, Nón, Quần... |
| `options[61]` | **% HP tối đa ẩn** | Dòng ẩn tự mở khi mặc đủ set trang bị cùng hệ. |
| `optionsSupportSkill[17]` | **% HP từ Kỹ năng** | Kỹ năng bị động tăng HP của môn phái (như skill hỗ trợ Kiếm/Đao). |
| `options[6, 32, 77, 82, 125]`| **Điểm HP cố định** | Các dòng `+X HP` trên Áo, Dây chuyền, Ngọc khảm, Thú cưỡi. |
| `options[128]` | **% Tăng HP sau khi mặc đồ** | Dòng VIP trên trang bị cao cấp, nhân tiếp theo % trên tổng HP đã mặc đồ. |

---

### 2.3. Điểm Mana Tối Đa (Max MP)

* **Công thức lời văn:**
  $$\text{MP Gốc} = \left(\text{Điểm Chakra} + \text{Cộng Tiềm Năng} + \text{Chakra} \times \text{\% Tăng Tiềm Năng}\right) \times 10$$
  $$\text{Max MP} = \text{MP Gốc} + \left(\text{MP Gốc} \times \frac{\text{\% MP Trang Bị (Opt 28) + \% MP Ẩn (Opt 60) + \% MP Kỹ Năng}}{100}\right) + \text{Tổng MP Cố Định Từ Đồ}$$
  *(Ghi nhớ: 1 điểm Chakra luôn tạo ra **10 MP Gốc**)*.

---

### 2.4. Sát Thương Tấn Công (Ngoại Công & Nội Công)

Server phân tách thành 2 nhánh môn phái (`getSideClass()`):
* **Nhánh Ngoại Công (`Case 0`)**: Gồm **Kiếm, Kunai, Đao** $\rightarrow$ Dùng điểm **Sức Khỏe (`potential[0]`)** và các dòng Ngoại công.
* **Nhánh Nội Công (`Case 1`)**: Gồm **Tiêu, Cung, Quạt** $\rightarrow$ Dùng điểm **Chakra (`potential[3]`)** và các dòng Nội công.

#### 1. Các bước tính toán lực đánh:
* **Bước A - Sát Thương Tiềm Năng (`potentialDame`):**
  * Với Ngoại công: $\text{Dame Tiềm Năng} = \text{Sức Khỏe} + \text{Cộng Tiềm Năng} + (\text{Sức Khỏe} \times \text{\% Tăng Tiềm Năng})$
  * Với Nội công: $\text{Dame Tiềm Năng} = \text{Chakra} + \text{Cộng Tiềm Năng} + (\text{Chakra} \times \text{\% Tăng Tiềm Năng})$
* **Bước B - Tấn Công Cơ Bản (`basicAttack`):**
  $$\text{Tấn Công Cơ Bản} = \text{Dame Tiềm Năng} + \text{Tấn Công Cộng Thêm (Opt 38)} + \left(\text{Dame Tiềm Năng} \times \frac{\text{\% Sát Thương Chiêu Thức}}{100}\right)$$
* **Bước C - Tổng Sát Thương Tối Đa (`damage` hay Max Dame):**
  $$\text{Max Dame} = \text{Tấn Công Cơ Bản} + \text{Vật Công Vũ Khí} + \text{Vật Công Trang Bị} + \left(\text{Dame Tiềm Năng} \times \frac{\text{\% Vật Công (Opt 8/9)}}{100}\right) + \text{Dame Tiềm Năng}$$
* **Bước D - Sát Thương Tối Thiểu (`damage2` hay Min Dame):**
  $$\text{Min Dame} = \text{Max Dame} - 10\% = 90\% \times \text{Max Dame}$$

#### 2. BẢNG CHÚ GIẢI CÁC OPTION SÁT THƯƠNG:
| Mã Option | Tên Tiếng Việt | Áp Dụng Cho Phái Nào | Ý Nghĩa Thực Tế |
| :---: | :--- | :--- | :--- |
| **`0`** | **Ngoại công vũ khí** | Kiếm, Kunai, Đao | Điểm sát thương vật lý gốc ghi trên Vũ khí. |
| **`1`** | **Nội công vũ khí** | Tiêu, Cung, Quạt | Điểm sát thương phép thuật gốc ghi trên Vũ khí. |
| **`8`** | **% Tăng Vật công Ngoại** | Kiếm, Kunai, Đao | Tăng thêm % sát thương dựa trên điểm Sức khỏe. |
| **`9`** | **% Tăng Vật công Nội** | Tiêu, Cung, Quạt | Tăng thêm % sát thương dựa trên điểm Chakra. |
| **`21, 23, 25`**| **Vật công Ngoại trang bị** | Kiếm, Kunai, Đao | Điểm sát thương ngoại công trên Dây chuyền, Nhẫn, Bùa. |
| **`22, 24, 26`**| **Vật công Nội trang bị** | Tiêu, Cung, Quạt | Điểm sát thương nội công trên Dây chuyền, Nhẫn, Bùa. |
| **`38`** | **Tấn công cộng thêm** | Mọi phái | Dòng `Tấn công +X` cố định trên trang bị. |
| **`88 / 89 / 90`**| **Tấn công theo hệ** | Hỏa (88), Băng (89), Phong (90) | Tăng lực đánh khi nhân vật mang đúng hệ nguyên tố. |
| **`94`** | **% Tăng tấn công tiềm năng** | Mọi phái | Dòng siêu cấp: nhân 4 lần tiềm năng rồi lấy theo %. |

---

### 2.5. Điểm Kháng 3 Hệ (Hỏa, Băng, Phong)

* **Công thức lời văn:**
  $$\text{Điểm Kháng Hệ} = \text{Tổng Các Dòng Kháng Riêng Của Hệ Đó} + \text{Kháng Tất Cả Các Hệ (Opt 36 + Opt 118)} + \text{Kháng Kỹ Năng Bị Động}$$

* **Chú giải các Option Kháng:**
  * **Kháng Hỏa riêng**: `Option 2, 11, 33, 70, 96` (Ghi trên Áo, Quần, Nón, Giáp thú cưỡi).
  * **Kháng Băng riêng**: `Option 3, 12, 34, 71, 95`.
  * **Kháng Phong riêng**: `Option 4, 13, 35, 72, 97`.
  * **Option 36 & Option 118** (`Kháng tất cả các hệ`): Tự động cộng đồng thời cho cả 3 kháng Hỏa, Băng và Phong.

---

## 3. PHẦN 2: CÁC CẶP CHỈ SỐ ĐỐI KHÁNG TRỰC TIẾP TRONG GIAO TRANH

*(Được tính toán trong phương thức tấn công của `Char.java` khi Người Tấn Công A đánh vào Mục Tiêu B)*

---

### 3.1. Cặp 1: Chính Xác vs Né Đòn (Trúng hay Hụt)

* **Bản chất**: Quyết định đòn đánh có chạm được vào người mục tiêu hay không. Nếu hụt, sát thương lập tức bằng 0.

#### 1. Mã Code Java:
```java
int randMiss = NinjaUtils.nextInt(B.miss + 100);
int randExactly = NinjaUtils.nextInt(A.exactly + 100);
boolean isMiss = (randMiss > randExactly) || B.isMiss;
```

#### 2. Diễn giải bằng Tiếng Việt:
* Máy chủ đổ 2 con xúc xắc ngẫu nhiên:
  * **Xúc xắc 1 (Né của B)**: Quay ngẫu nhiên từ `0` đến `(Né Đòn của B + 100)`.
  * **Xúc xắc 2 (Chính xác của A)**: Quay ngẫu nhiên từ `0` đến `(Chính Xác của A + 100)`.
* **Kết quả**:
  * Nếu **Xúc xắc Né > Xúc xắc Chính Xác** $\rightarrow$ Đòn đánh bị **HỤT (Miss, 0 Damage)**.
  * Nếu **Xúc xắc Né $\le$ Xúc xắc Chính Xác** $\rightarrow$ Đòn đánh **TRÚNG ĐÍCH (Hit)**.

> **Giải thích con số `+ 100`**: Đây là hệ số may mắn sàn. Kể cả người có 0 né đòn thì vẫn có 1 khoảng ngẫu nhiên từ 0-99 để tránh việc 100% luôn trúng hoặc 100% luôn trượt.

---

### 3.2. Cặp 2: Chí Mạng vs Kháng Sát Thương Chí Mạng

* **Bản chất**: Khi nổ đòn bạo kích (Chí Mạng), sát thương được cộng thêm một lượng lớn. Đối thủ có thể build đồ Kháng ST Chí Mạng để triệt tiêu lượng sát thương nổ thêm này.

#### 1. Mã Code Java:
```java
int dameFatal = 0;
// Bước 1: Tính lượng sát thương bạo kích thêm
if (isFatal) {
    dameFatal += dameBasic;
    dameFatal += (dameBasic * (A.percentFatalDame + A.optionsSupportSkill[65])) / 100;
    dameFatal += A.fatalDame;
    dameFatal -= dameHit * (B.options[46] + B.options[79]) / 100;
}

// Bước 2: Triệt tiêu bởi % Kháng ST Chí Mạng của B (Option 121)
int kstcm = Math.min(100, B.options[121]);
dameFatal -= dameFatal * kstcm / 100;
```

#### 2. Diễn giải bằng Tiếng Việt:
* **Sát Thương Chí Mạng Thô**:
  $$\text{Dame Chí Mạng} = \text{Dame Gốc} + \left(\text{Dame Gốc} \times \frac{\text{\% ST Chí Mạng của A (Opt 39, 67)}}{100}\right) + \text{ST Chí Mạng Cố Định (Opt 105)} - \text{Giảm ST Chí Mạng của B}$$
* **Triệt tiêu bởi Kháng ST Chí Mạng % (`Option 121`)**:
  $$\text{Dame Chí Mạng Sau Cùng} = \text{Dame Chí Mạng Thô} \times \left(1 - \frac{\text{Kháng ST Chí Mạng \% của B (Tối đa 100\%)}}{100}\right)$$

> **Quy tắc cốt tử**: Nếu B có `Option 121 >= 100%`, toàn bộ lượng sát thương bạo kích nổ thêm `dameFatal` sẽ bị **giảm sạch về 0**. Lúc này đòn chí mạng chỉ gây sát thương như 1 đòn đánh bình thường.

---

### 3.3. Cặp 3: Khắc Hệ & Xuyên Kháng

* **Vòng tròn tương khắc**:
  * **Hỏa khắc Phong**: Kiếm, Tiêu đánh Đao, Quạt được tăng sát thương.
  * **Băng khắc Hỏa**: Kunai, Cung đánh Kiếm, Tiêu được tăng sát thương.
  * **Phong khắc Băng**: Đao, Quạt đánh Kunai, Cung được tăng sát thương.

#### 1. Mã Code Java (Ví dụ A thuộc Hệ Hỏa - `getSys() == 1`):
```java
// 1. Trừ điểm Kháng Hỏa cố định (Nếu A không kích hoạt Xuyên Kháng Option 101)
if (!isSkipResistance) {
    dameHit -= B.resFire;
}

// 2. Trừ tiếp % Kháng Hỏa của B (Option 127)
dameHit -= dameHit * B.options[127] / 100;

// 3. Trừ Giảm ST Đồ +16 của B (Option 48)
dameHit -= B.options[48];

// 4. Cộng Thưởng Khắc Hệ Phong của A (Nếu B là Hệ Phong)
dameHit += A.options[51] + (dameBasic * A.options[55] / 100);
```

#### 2. BẢNG TRA CỨU ĐỐI KHÁNG 3 HỆ:
| Hệ Của Người Đánh A | Điểm Kháng Cố Định Của B | % Kháng Hệ Của B | Giảm ST Đồ +16 | Thưởng ST Khắc Hệ Cố Định | Thưởng % ST Khắc Hệ |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Hệ Hỏa** (Kiếm, Tiêu) | `B.resFire` | `B.options[127]` | `B.options[48]` | `A.options[51]` (đánh Phong) | `A.options[55]` (đánh Phong) |
| **Hệ Băng** (Kunai, Cung)| `B.resIce` | `B.options[130]` | `B.options[49]` | `A.options[52]` (đánh Hỏa) | `A.options[56]` (đánh Hỏa) |
| **Hệ Phong** (Đao, Quạt) | `B.resWind` | `B.options[131]` | `B.options[50]` | `A.options[53]` (đánh Băng) | `A.options[54]` (đánh Băng) |

* **Cơ chế Xuyên Kháng (`Option 101`)**: Khi kích hoạt, bỏ qua toàn bộ điểm kháng cố định của B (`resFire / resIce / resWind = 0`).

---

### 3.4. Cặp 4: Bỏng Lửa Hệ Hỏa (Nhân Đôi Dame Gốc)

```java
if (B.isFire) {
    dameHit += dameBasic; // Cộng thêm đúng bằng 100% Sát thương cơ bản gốc!
}
```
* **Bản chất**: Khi mục tiêu B đang bị ngọn lửa thiêu đốt (`B.isFire == true`), bất kỳ đòn đánh tiếp theo nào bồi vào người B sẽ được **cộng thêm 1 lần Sát Thương Cơ Bản Gốc (tương đương $\times 2$ sát thương đầu vào)**.

---

### 3.5. Cặp 5: Miễn Giảm Sát Thương % & Giảm Trừ Cố Định

```java
// Tầng 1: Khấu trừ theo % Miễn Giảm
dameHit -= dameHit * (B.options[63] + B.options[98]) / 100;

// Tầng 2: Trừ thẳng điểm Giảm Trừ Sát Thương cố định sau cùng
dameHit -= B.dameDown;
```
* **Tầng 1 (% Miễn Giảm)**: Khấu trừ đồng thời **Option 63** (`% Giảm ST từ người khác`) và **Option 98** (`% Miễn giảm sát thương toàn diện`).
* **Tầng 2 (`dameDown` Cố Định)**: Trừ thẳng tổng các dòng **Option 47, 74, 80, 124** (`Giảm trừ sát thương +X`) trước khi trừ vào thanh máu.

---

### 3.6. Cặp 6: Phản Đòn Cận Chiến & Phản Đòn Băng Giá (20% HP)

```java
int reactDame = B.reactDame; // Lấy điểm phản đòn thông thường từ Option 15, 91, 126

// Cơ chế Phản Đòn Băng Giá từ Option 135
if (B.options[135] > 0) {
    if (NinjaUtils.nextInt(100) < B.options[135]) {
        reactDame = A.hp * 20 / 100; // Phản lại ngay 20% LƯỢNG MÁU HIỆN TẠI của người tấn công A!
    }
}

if (reactDame > 0) {
    A.addHp(-reactDame); // Trừ máu ngược lại cho người ra đòn
}
```

---

### 3.7. Cặp 7: Sát Thương Chuẩn & Sát Thương Lên Người

```java
dameHit += A.options[113]; // Option 113: Sát thương chuẩn (Đánh xuyên mọi loại kháng)
dameHit += A.options[103]; // Option 103: Sát thương lên người chơi (PvP)
```

---

### 3.8. Cặp 8: Chênh Lệch Cấp Độ (Hệ Số Level PvP & Boss PvE)

#### 1. Trong PvP (Người đánh Người):
$$\text{Hệ Số Level} = \frac{6 - \left(\frac{\text{Level của B} - \text{Level của A}}{40}\right)}{100}$$

$$\text{Sát Thương PvP} = \left(\text{dameHit} + \frac{\text{dameFatal}}{3}\right) \times \text{Hệ Số Level}$$

* **Ý nghĩa thực tế:**
  * **Hai người bằng cấp nhau** (Ví dụ đều cấp 100): Hệ số = $\frac{6 - 0}{100} = 6.0\%$ (Sát thương co về tỷ lệ chuẩn 6% của game).
  * **Người đánh hơn cấp người bị đánh** (A cấp 100 đánh B cấp 60): $\frac{60 - 100}{40} = -1.0 \rightarrow$ Hệ số = $\frac{6 - (-1)}{100} = 7.0\%$ ($\rightarrow$ **Sát thương tăng vọt**).
  * **Người đánh kém cấp người bị đánh** (A cấp 60 đánh B cấp 100): $\frac{100 - 60}{40} = +1.0 \rightarrow$ Hệ số = $\frac{6 - 1}{100} = 5.0\%$ ($\rightarrow$ **Sát thương bị giảm mạnh**).

#### 2. Trong PvE (Đánh Boss Thế Giới):
```java
if (Math.abs(A.level - mob.level) > 20) {
    isMiss = true; // Chênh lệch quá 20 cấp độ thì 100% ĐÁNH HỤT (0 Damage)!
}
```
* **Quy tắc săn Boss**: Nhân vật chênh lệch **trên 20 Level** so với Boss (dù cao hơn hay thấp hơn) sẽ **hoàn toàn không thể đánh trúng Boss (100% Miss)**.

---

### 3.9. Cặp 9: Trạng Thái Bất Lợi

* **Suy Yếu / Trúng Độc (`A.isInfected()`):** Người đánh A bị **giảm 80% Sát thương đầu ra** (`dameHit -= dameHit * 80 / 100`).
* **Lạnh Giá / Đóng Băng (`A.isCool()`):** Người đánh A bị **giảm 50% Sát thương đầu ra** (`dameHit -= dameHit * 50 / 100`).

---

## 4. PHẦN 3: VÍ DỤ TÍNH SÁT THƯƠNG THỰC CHIẾN TỪNG BƯỚC

Hãy cùng theo dõi một kịch bản giao tranh hoàn chỉnh từ đầu đến cuối:

### 🥋 Hồ Sơ Hai Nhân Vật:
* **Người Tấn Công A**: Phái Kiếm (Hệ Hỏa), Cấp 100.
  * Sát thương tối thiểu `damage2` = `18,000`, Sát thương tối đa `damage` = `20,000`.
  * % Sát thương chí mạng = `50%`, ST chí mạng cố định = `500`.
  * Dòng thưởng đánh hệ Phong: Option 51 = `200`, Option 55 = `20%`.
  * Sát thương chuẩn (Option 113) = `300`.
* **Người Phòng Thủ B**: Phái Quạt (Hệ Phong), Cấp 100.
  * Trạng thái: Đang bị **Bỏng** (`isFire = true`).
  * Kháng Hỏa cố định (`resFire`) = `1,000`.
  * % Kháng Hỏa (`Option 127`) = `10%`.
  * % Kháng ST Chí Mạng (`Option 121`) = `40%`.
  * % Miễn giảm sát thương (`Option 63 + 98`) = `15%`.
  * Giảm trừ sát thương cố định (`dameDown`) = `200`.

---

### 🧮 Quá Trình Tính Toán Qua 7 Bước:

* **Bước 1: Rút ngẫu nhiên sát thương & Hiệu ứng Bỏng**
  * Máy chủ random giữa 18,000 và 20,000 $\rightarrow$ Ra `dameHit = 19,000` (Lưu `dameBasic = 19,000`).
  * Do B đang bị **Bỏng**: $\text{dameHit} = 19,000 + 19,000 = 38,000$.

* **Bước 2: Xử lý nổ Chí Mạng**
  * Đòn đánh nổ chí mạng thành công:
    $$\text{dameFatal Thô} = 19,000 + (19,000 \times 50\%) + 500 = 19,000 + 9,500 + 500 = 29,000$$
  * Trừ Kháng ST Chí Mạng của B (40%):
    $$\text{dameFatal Sau Kháng} = 29,000 \times (1 - 0.40) = 17,400$$

* **Bước 3: Khắc Hệ Hỏa đánh Hệ Phong**
  * Trừ Kháng Hỏa cố định: $38,000 - 1,000 = 37,000$.
  * Trừ Kháng Hỏa 10%: $37,000 \times (1 - 0.10) = 33,300$.
  * Cộng Thưởng Khắc Hệ (Opt 51 + Opt 55): $33,300 + 200 + (19,000 \times 20\%) = 33,300 + 200 + 3,800 = 37,300$.

* **Bước 4: Trừ Miễn Giảm Sát Thương %**
  * Trừ Miễn giảm 15%: $37,300 \times (1 - 0.15) = 31,705$.

* **Bước 5: Nhân Hệ Số Level PvP (Đồng cấp 100 $\rightarrow$ Hệ số = 6.0%)**
  $$\text{dameHit} = \left(31,705 + \frac{17,400}{3}\right) \times 6.0\% = (31,705 + 5,800) \times 0.06 = 37,505 \times 0.06 = 2,250$$

* **Bước 6: Cộng Sát Thương Chuẩn & Trừ Giảm Trừ Cố Định**
  * Cộng Sát thương chuẩn (Opt 113): $2,250 + 300 = 2,550$.
  * Trừ Giảm trừ cố định (`dameDown`): $2,550 - 200 = 2,350$.

* **Bước 7: Trừ Máu Mục Tiêu B**
  * Đòn đánh trúng đích (`isMiss == false`).
  * **Thực thi trừ máu:** `B.addHp(-2350)`.
  * Màn hình Client hiện số sát thương nổ bạo kích: **`-2350 HP`**.

---

## 5. PHẦN 4: BẢNG TRA CỨU TOÀN BỘ MÃ OPTION CHIẾN ĐẤU

| Mã Option ID | Tên Thuộc Tính Trong Game | Nơi Xuất Hiện & Cách Thức Hoạt Động |
| :---: | :--- | :--- |
| **`0 / 1`** | Vũ khí Ngoại công / Nội công | Cộng trực tiếp vào lực tấn công tối đa (`damage`). |
| **`2 / 3 / 4`** | Kháng Hỏa / Kháng Băng / Kháng Phong | Điểm kháng cố định, trừ trực tiếp vào sát thương hệ nhận vào. |
| **`5 / 17 / 62`**| Né đòn cố định | Cộng vào điểm Né đòn (`miss`). |
| **`8 / 9`** | % Tăng Vật công Ngoại / Nội | Nhân % theo điểm tiềm năng Sức khỏe (Ngoại) hoặc Chakra (Nội). |
| **`10 / 18 / 75`**| Chính xác cố định | Cộng vào điểm Chính xác (`exactly`). |
| **`14 / 37 / 69`**| Chí mạng cố định | Tăng điểm tỷ lệ nổ đòn đánh bạo kích (`fatal`). |
| **`15 / 91 / 126`**| Phản đòn cận chiến | Trừ máu trực tiếp của kẻ tấn công (`reactDame`). |
| **`31 / 61`** | % HP tối đa | Tăng % máu dựa trên lượng HP gốc. |
| **`36 / 118`** | Kháng tất cả các hệ | Cộng đồng thời vào cả 3 kháng Hỏa, Băng, Phong. |
| **`38`** | Tấn công cộng thêm | Cộng điểm sát thương cố định vào tấn công cơ bản. |
| **`39 / 67`** | % Tăng sát thương chí mạng | Tăng thêm lượng damage khi nổ chí mạng (`percentFatalDame`). |
| **`40 / 41 / 42`**| Giảm thời gian Bỏng / Băng / Choáng| Rút ngắn thời gian chịu trạng thái khống chế bất lợi. |
| **`46 / 79`** | % Giảm sát thương chí mạng | Giảm bớt lượng sát thương bạo kích của đối phương. |
| **`47 / 74 / 80`**| Giảm trừ sát thương cố định | Trừ thẳng vào sát thương sau cùng (`dameDown`). |
| **`51 / 52 / 53`**| Tăng sát thương lên hệ bị khắc | Tăng điểm sát thương khi đánh đối thủ thuộc hệ bị khắc chế. |
| **`54 / 55 / 56`**| % Tăng sát thương lên hệ bị khắc | Tăng % sát thương khi đánh đối thủ thuộc hệ bị khắc chế. |
| **`57 / 58`** | Cộng tiềm năng / % Tăng tiềm năng | Tăng trực tiếp cả 4 chỉ số Sức khỏe, Thân pháp, Thể lực, Chakra. |
| **`63`** | % Giảm sát thương từ người khác | Miễn giảm % sát thương nhận vào trong PvP. |
| **`98`** | % Miễn giảm sát thương toàn diện | Miễn giảm % mọi loại sát thương (cả PvP lẫn PvE). |
| **`101`** | Bỏ qua kháng tính (Xuyên kháng) | Vô hiệu hóa điểm kháng nguyên tố của mục tiêu về 0. |
| **`103`** | Sát thương lên người chơi | Sát thương cố định cộng thêm trong PvP. |
| **`105`** | Sát thương chí mạng cố định | Cộng điểm sát thương cố định vào đòn chí mạng (`fatalDame`). |
| **`113`** | Sát thương chuẩn | Đánh xuyên qua toàn bộ kháng giáp phòng ngự. |
| **`121`** | % Kháng sát thương chí mạng | Triệt tiêu % sát thương bạo kích (Tối đa 100%). |
| **`127 / 130 / 131`**| % Kháng Hỏa / Băng / Phong | Khấu trừ theo % sát thương nguyên tố tương ứng. |
| **`128`** | % Tăng HP sau khi mặc đồ | Dòng VIP nhân tiếp theo % trên tổng lượng HP sau trang bị. |
| **`135`** | % Tỷ lệ Phản đòn Băng giá | Có tỷ lệ phản lại ngay 20% lượng HP hiện tại của kẻ tấn công. |
