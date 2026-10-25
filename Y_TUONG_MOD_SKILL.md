# TÀI LIỆU THIẾT KẾ & THÔNG SỐ MOD KỸ NĂNG: 6 HỆ PHÁI
### (LƯU TRỮ Ý TƯỞNG REWORK GAMEPLAY - NSO PRIVATE 2026)

> **Mục tiêu**: Đưa chiều sâu chiến thuật và phong cách hành động hiện đại (High Risk - High Reward / Mana Efficiency / Kinetic Burst / Strategic Utility) vào NSO, khắc phục hạn chế animation của Client J2ME, giải quyết triệt để các kỹ năng "rác/phèn" (Dead Skills) hoặc "tự bóp" (Self-sabotaging Skills) và tạo bản sắc riêng biệt cho từng môn phái.

---

## MỤC LỤC
1. [Phái Ninja Kiếm:](#1-phái-ninja-kiếm)
   - [1.1. Chiêu 25 Kakyuu: Nội Tại Hồi Phục Thức Ăn (Cố Định & %)](#11-chiêu-25-kakyuu-nội-tại-hồi-phục-thức-ăn-cố-định--)
   - [1.2. Chiêu 6x Pawaraikou: Chế Độ "Khô Máu" All-Out (Kiểu K'Sante)](#12-chiêu-6x-pawaraikou-chế-độ-khô-máu-all-out-kiểu-ksante)
2. [Phái Ninja Tiêu: Chiêu 25 Taiyoutama & Chiêu 35 Hoshitama](#2-phái-ninja-tiêu)
   - [2.1. Chiêu 25 Taiyoutama: Tối Ưu Hồi Chiêu Vòng Lửa (45s -> 40s)](#21-chiêu-25-taiyoutama-tối-ưu-hồi-chiêu-vòng-lửa-45s---40s)
   - [2.2. Chiêu 35 Hoshitama: Đổi Mana Lấy Sức Tấn Công](#22-chiêu-35-hoshitama-đổi-mana-lấy-sức-tấn-công)
3. [Phái Ninja Đao: Chiêu 25 Magumandari ("Bùa Hại Trấn Áp Quái & Lan Mục Tiêu")](#3-phái-ninja-đao-chiêu-25-magumandari-bùa-hại-trấn-áp-quái--lan-mục-tiêu)
4. [Phái Ninja Cung: Chiêu 25 Sogekihei ("Tối Ưu Hiệu Suất Khiên Mana")](#4-phái-ninja-cung-chiêu-25-sogekihei-tối-ưu-hiệu-suất-khiên-mana)
5. [Phái Ninja Kunai: Chiêu 25 Aoiame ("Bù Nhìn Nổ Động Năng Chuẩn Chỉ Số")](#5-phái-ninja-kunai-chiêu-25-aoiame-bù-nhìn-nổ-động-năng-chuẩn-chỉ-số)
6. [Bảng So Sánh Chỉ Số: Trước vs Sau Khi Mod](#6-bảng-so-sánh-chỉ-số-trước-vs-sau-khi-mod)
7. [Kế Hoạch Triển Khai Vào Codebase (Server & Database)](#7-kế-hoạch-triển-khai-vào-codebase)
8. [Đánh Giá Cân Bằng Game (Balance & Counterplay)](#8-đánh-giá-cân-bằng-game)
9. [Cân Bằng Kỹ Năng Cấp 15 (6 Phái)](#9-cân-bằng-kỹ-năng-cấp-15-6-phái)
10. [Cân Bằng Kỹ Năng Cấp 20 Quạt (Oouchia)](#10-cân-bằng-kỹ-năng-cấp-20-quạt-oouchia)

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
| **Point 1** | Cấp 25 | **+50 HP** | **+1.0% Max HP** | +150 HP / nhịp |
| **Point 2** | Cấp 26 | **+75 HP** | **+1.0% Max HP** | +175 HP / nhịp |
| **Point 3** | Cấp 27 | **+100 HP** | **+2.0% Max HP** | +300 HP / nhịp |
| **Point 4** | Cấp 28 | **+125 HP** | **+3.0% Max HP** | +425 HP / nhịp |
| **Point 5** | Cấp 29 | **+150 HP** | **+4.0% Max HP** | +550 HP / nhịp |
| **Point 6 (Max)** | Cấp 30 | **+200 HP** | **+5.0% Max HP** | **+700 HP / nhịp** |

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

## 2. PHÁI NINJA TIÊU

### 2.1. Chiêu 25 Taiyoutama: Tối Ưu Hồi Chiêu Vòng Lửa (45s -> 40s)
- **ID Skill Template**: `13` (Chiêu Taiyoutama - Cấp 25 Tiêu - Hệ Hỏa).
- **Loại chiêu**: `type = 2` (Buff chủ động tạo vòng lửa thiêu đốt quái vật bị tấn công).
- **Điều chỉnh cốt lõi**:
  - **Bản gốc**: Hồi chiêu cố định **45 giây** ở tất cả 6 cấp độ khiến nhịp thi triển vòng lửa bị gượng gạo, thiếu tính cơ động khi dọn quái.
  - **Bản Mod**: **Giảm nhẹ thời gian hồi chiêu từ 45s giảm dần về 40s** theo từng cấp kỹ năng (mỗi cấp giảm 1 giây).

| Cấp Chiêu (Point) | Yêu Cầu Level | Sát Thương Đốt (% Tấn Công / 30s) | Cooldown Mới *(Gốc 45s)* | Mana Sử Dụng |
| :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | 25% | **45 giây** *(45000ms)* | 120 MP |
| **Point 2** | Cấp 26 | 30% | **44 giây** *(44000ms)* | 120 MP |
| **Point 3** | Cấp 27 | 35% | **43 giây** *(43000ms)* | 120 MP |
| **Point 4** | Cấp 28 | 40% | **42 giây** *(42000ms)* | 120 MP |
| **Point 5** | Cấp 29 | 50% | **41 giây** *(41000ms)* | 120 MP |
| **Point 6 (Max)** | Cấp 30 | 60% | **40 giây** *(40000ms)* | 120 MP |

### 2.2. Chiêu 35 Hoshitama (Đổi Mana Lấy Sức Tấn Công)

- **ID Skill Template**: `15` (Ẩn thân / Hồi phục - Cấp 35 Tiêu).
- **Rework mới**:
  - **Giảm hồi HP**: Về mức nhẹ (~30% so với gốc), **Xóa hoàn toàn khả năng tự hồi MP** trong thời gian tàng hình.
  - **Cơ chế Đốt Mana Đổi Sát Thương**:
    - Mỗi lần phóng phi tiêu tự động **đốt 8% Mana tối đa (`maxMP`)**.
    - **Bù lại**: Đòn đánh được cộng thêm sát thương bùng nổ:
      $$\text{Sát Thương Thêm} = \text{Lượng Mana Đốt} \times 2.5$$
    - *Ví dụ*: Người chơi có 3,000 MP, mỗi đòn đốt 240 MP và cộng thêm **+600 Sát thương bộc phá** trên mỗi cú ném!

---

## 3. PHÁI NINJA ĐAO: CHIÊU 25 MAGUMANDARI (BÙA HẠI TRẤN ÁP QUÁI & LAN MỤC TIÊU)

- **ID Skill Template**: `40` (Bùa chú cấp 25 Đao - Hệ Phong).
- **Cách thức thi triển**: Chọn mục tiêu từ xa (`dx = 100, dy = 50`, `type = 3`), giữ nguyên animation bay bùa của Client.
- **Phạm vi tác dụng**: **CHỈ tác dụng lên QUÁI, TUYỆT ĐỐI KHÔNG tác dụng lên Người chơi (PvP)**.
  - Tác dụng đầy đủ và hiệu quả lên: **Quái thường, Quái Tinh Anh, Quái Thủ Lĩnh và Tà Thú (Boss Thế Giới)**.
- **Cơ chế Bùa Hại Chuyên Trị Quái (PvE Debuff Specialist)**:
  - **Giảm sức tấn công của quái**: `10% - 15%`. Giúp Đao và đồng đội giảm tải áp lực sát thương khi ôm quái đông hoặc tank Boss.
  - **Giảm Kháng tất cả của quái (`resFire, resIce, resWind`)**: `30 - 60 điểm`.
    > *Hiệu quả cực lớn*: Với công thức kháng quái theo level ($y = \frac{12 \cdot \text{level}^2}{\text{level} + 144}$), việc trừ thẳng 30-60 điểm kháng trực tiếp sẽ kéo tụt lớp phòng thủ của quái về mức âm hoặc xuyên thủng giáp, khiến sát thương của toàn đội dồn vào mục tiêu tăng vọt!
- **Cơ chế Lan theo tầm đánh (Multi-target Spread)**:
  - **Point 1 - 3**: Đơn mục tiêu (1 quái).
  - **Point 4 - 5**: Đạt Point 4 trở lên sẽ **lan ra 2 mục tiêu** trong phạm vi tầm đánh (`dx = 100, dy = 50`).
  - **Point 6 (Max)**: Đạt Point 6 sẽ **lan ra 3 mục tiêu** trong phạm vi tầm đánh, biến bùa chú thành công cụ gom quái / dọn bãi / debuff Boss kèm quái đệ cực kỳ bá đạo.
- **Thời gian bùa & Cooldown**:
  - Thời gian bùa: `6.0s -> 8.0s`.
  - Hồi chiêu: `25s -> 20s`.

| Cấp Chiêu (Point) | Yêu Cầu Level | Giảm Tấn Công Quái | Giảm Kháng Tất Cả Quái | Số Mục Tiêu (Lan) | Thời Gian Bùa | Cooldown | Mana Use |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | **-10%** | **-30 Kháng** | 1 mục tiêu | 6.0 giây | 25 giây | 30 MP |
| **Point 2** | Cấp 26 | **-11%** | **-36 Kháng** | 1 mục tiêu | 6.5 giây | 24 giây | 40 MP |
| **Point 3** | Cấp 27 | **-12%** | **-42 Kháng** | 1 mục tiêu | 7.0 giây | 23 giây | 50 MP |
| **Point 4** | Cấp 28 | **-13%** | **-48 Kháng** | **Lan 2 mục tiêu** | 7.5 giây | 22 giây | 60 MP |
| **Point 5** | Cấp 29 | **-14%** | **-54 Kháng** | **Lan 2 mục tiêu** | 8.0 giây | 21 giây | 70 MP |
| **Point 6 (Max)** | Cấp 30 | **-15%** | **-60 Kháng** | **Lan 3 mục tiêu** | **8.0 giây** | **20 giây** | 80 MP |

---

## 4. PHÁI NINJA CUNG: CHIÊU 25 SOGEKIHEI (TỐI ƯU HIỆU SUẤT KHIÊN MANA)

### 4.1. Thực trạng kỹ năng gốc & "Con dao hai lưỡi"
- **ID Skill Template**: `31` (Khiên Mana / Sogekihei - Cấp 25 Cung).
- **Vấn đề cốt lõi**:
  - Bản gốc tăng tỷ lệ hấp thụ lên quá dốc: Point 6 lên tới **70% sát thương chuyển sang Mana**.
  - Tỉ lệ quy đổi là 1:1 $\rightarrow$ Khi gặp Boss hay Tà Thú đánh 3,000 - 4,000 dame, khiên hút sạch **2,100 - 2,800 MP chỉ trong 1 đòn**!
  - **Hậu quả**: Mana cạn sạch $\rightarrow$ Kẹt toàn bộ skill không thể bắn trả (tự câm lặng) $\rightarrow$ Khi Mana < 10% khiên tự động sập $\rightarrow$ Đòn đánh tiếp theo của Boss one-hit Cung đột tử!
  - Cooldown ở Point 1 lên tới **120 giây (2 phút)** vô cùng bất hợp lý.

### 4.2. Concept Rework: "Hai Trục Cân Bằng (Dual-Progression)"
> *"Thay vì tăng tỷ lệ hút sát thương quá cao làm cháy sạch Mana, ta điều chỉnh tỷ lệ hấp thụ tăng nhẹ nhàng (đóng vai trò lớp giáp mềm 20-25%), đồng thời giảm dần lượng MP tiêu hao trên mỗi điểm sát thương (tối ưu hóa hiệu suất năng lượng theo cấp độ)."*

- **Trục 1: Tỷ lệ sát thương hấp thụ (`8% -> 23% - 25%`)**:
  - Giúp Cung giảm bớt 1/4 sát thương mà không bao giờ bị sốc tụt cạn thanh Mana.
- **Trục 2: Hiệu suất năng lượng (Mana Efficiency: `2.0 MP -> 1.0 MP / 1 ST`)**:
  - Càng lên cấp cao, Ninja Cung càng tinh thông cách điều tiết chakra: Tiêu hao ít Mana hơn để triệt tiêu cùng 1 lượng sát thương!
- **Rút ngắn Cooldown**: Giảm từ 120s xuống mức hợp lý **40s -> 20s**.

### 4.3. Bảng thông số 6 cấp độ kỹ năng Cung:

| Cấp Chiêu (Point) | Yêu Cầu Level | % Sát Thương Hấp Thụ | Tỉ Lệ Tiêu Hao Mana | Cooldown Mới *(Gốc)* | Mana Kích Hoạt |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | **8%** | **2.0 MP** đổi 1 ST | **40 giây** *(120s)* | 80 MP |
| **Point 2** | Cấp 26 | **11%** | **1.8 MP** đổi 1 ST | **35 giây** *(100s)* | 90 MP |
| **Point 3** | Cấp 27 | **14%** | **1.6 MP** đổi 1 ST | **30 giây** *(80s)* | 100 MP |
| **Point 4** | Cấp 28 | **17%** | **1.4 MP** đổi 1 ST | **25 giây** *(60s)* | 110 MP |
| **Point 5** | Cấp 29 | **20%** | **1.2 MP** đổi 1 ST | **22 giây** *(40s)* | 120 MP |
| **Point 6 (Max)** | Cấp 30 | **23% - 25%** | **1.0 MP** đổi 1 ST *(Cực rẻ)* | **20 giây** *(20s)* | 130 MP |

---

## 5. PHÁI NINJA KUNAI: CHIÊU 25 AOIAME (BÙ NHÌN NỔ ĐỘNG NĂNG CHUẨN CHỈ SỐ)

### 5.1. Thực trạng kỹ năng gốc
- **ID Skill Template**: `22` (Chiêu Aoiame - Cấp 25 Kunai - Hệ Băng).
- **Cơ chế cũ**: Cắm bù nhìn hút đòn quái trong 300px. Khi hết giờ đếm ngược, bù nhìn biến mất vô nghĩa, không để lại bất kỳ sát thương hay giá trị tấn công nào.

### 5.2. Concept Rework: "Bù Nhìn Nổ Động Năng Mang Chỉ Số Chiêu Thuần"
> *"Khác với chiêu hỗ trợ đơn thuần, chiêu Aoiame được thiết kế mang đầy đủ các chỉ số sát thương của một CHIÊU THUẦN TẤN CÔNG (như chiêu 10 Yokobatan của Kunai): Bao gồm Tấn công cơ bản, Băng sát và Tấn công nội công (%). Đồng thời tích lũy thêm +5% sát thương cho mỗi phát đòn mà bù nhìn đã gánh chịu trước khi kích nổ vào con quái gần nhất!"*

- **Bộ 3 Chỉ Số Sát Thương Chuẩn Phong Cách Chiêu Thuần**:
  1. **Tấn công cơ bản** (`Option ID = 0`): Cộng thêm sát thương cố định (+50 -> +250).
  2. **Băng sát** (`Option ID = 3`): Sát thương nguyên tố Băng đặc trưng của Kunai (+150 -> +700).
  3. **Tấn công nội công (%)** (`Option ID = 11`): Khuếch đại sát thương theo điểm tiềm năng Chakra (+100% -> +180%).
- **Cơ chế Gia Tăng Động Năng (+5% Sát thương / Hit gánh chịu)**:
  - Cứ mỗi lần quái hoặc Boss vả trúng bù nhìn $\rightarrow$ Sát thương nổ tăng thêm **+5%**!
  $$\text{Sát Thương Nổ Cuối} = \left(\text{Sát Thương Kỹ Năng Chuẩn}\right) \times \left(1 + \text{Số Hit Chịu Đựng} \times 5\%\right)$$
  - Cú nổ kích hoạt qua `CombatService.calculatePvEDamage` (có nổ Chí Mạng, có tính tương khắc hệ Băng/Thủy của Kunai).

### 5.3. Bảng thông số chi tiết 6 cấp độ chiêu thuần của Kunai 25:

| Cấp Chiêu (Point) | Yêu Cầu Level | Thời Gian Bù Nhìn (Opt 49) | HP Khúc Gỗ (Opt 82 + 83) | Tấn Công Nội (Opt 1) | Tương Khắc (Opt 33) | Sát Thương Bộc Phá (Opt 81) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Point 1** | Cấp 25 | **6.0 giây** | **600 + 10% HP** | **+130%** | **+50** | **+100% HP đã mất** |
| **Point 2** | Cấp 26 | **7.0 giây** | **800 + 15% HP** | **+150%** | **+75** | **+100% HP đã mất** |
| **Point 3** | Cấp 27 | **8.0 giây** | **1,000 + 20% HP** | **+175%** | **+100** | **+100% HP đã mất** |
| **Point 4** | Cấp 28 | **9.0 giây** | **1,200 + 25% HP** | **+200%** | **+130** | **+100% HP đã mất** |
| **Point 5** | Cấp 29 | **10.0 giây** | **1,400 + 30% HP** | **+225%** | **+165** | **+100% HP đã mất** |
| **Point 6 (Max)** | Cấp 30 | **12.0 giây** | **1,600 + 40% HP** | **+260%** | **+200** | **+100% HP đã mất** |

*Đặc tính chiến đấu*:
- **Hiện thực hóa qua thực thể Mob đồng minh (Mob Template ID 0 - Bù nhìn)**:
  - Bù nhìn xuất hiện như một đối tượng Mob đồng minh có **Thanh Máu (Health Bar)** hiển thị trực quan ở giao diện mục tiêu góc màn hình của người chơi.
  - Người chơi và đồng đội được bảo vệ (không thể tấn công nhầm vào bù nhìn).
  - Quái vật trong phạm vi bị thu hút khiêu khích và lao vào tấn công bù nhìn, đòn đánh quái làm thanh máu bù nhìn tụt trực tiếp trên màn hình.
- Khúc gỗ không có hệ và không có phòng thủ (nhận trọn sát thương từ quái).
- Khi hết giờ (6s - 12s) HOẶC khi bị quái đánh hết HP: Bù nhìn lập tức phát nổ dồn sát thương lên quái xung quanh.
- Sát thương nổ = Sát thương nội công % (có tính Bạo kích, Ngũ hành & Tương khắc Cường Khắc) + **100% Lượng máu bù nhìn đã mất** (nếu bù nhìn vỡ hết máu thì được cộng trọn vẹn 100% Max HP của bù nhìn)!

---

## 6. BẢNG SO SÁNH CHỈ SỐ: TRƯỚC VS SAU KHI MOD

```mermaid
graph TD
    subgraph KIẾM 25 (KAKYUU)
        K25_1["Bản Gốc: Nướng quái thành đồ ăn<br/>Vô dụng khi PvP & Boss"] --> K25_2["Bản Mod: Kỹ năng Bị Động (Passive)<br/>Tăng hồi HP từ thức ăn mỗi 5s<br/>+400 HP cố định & +2.0% Max HP"]
    end

    subgraph KIẾM 6x (PAWARAIKOU)
        K6x_1["Bản Gốc: Bị động<br/>+Hỏa sát thụ động<br/>Ôm thủ trâu bò cù nhây"] --> K6x_2["Bản Mod: Buff Chủ Động (15s)<br/>-50% Kháng, -40% Né<br/>+100% Sức tấn công<br/>Kích hoạt 25% Sát Thương Chuẩn"]
    end

    subgraph TIÊU 25 (TAIYOUTAMA)
        T25_1["Bản Gốc: Cooldown cố định 45s<br/>Đốt 25-60% HP quái / 30s"] --> T25_2["Bản Mod: Tối ưu Cooldown 45s -> 40s<br/>Mỗi cấp giảm 1s<br/>Mượt mà nhịp thi triển vòng lửa"]
    end

    subgraph TIÊU 35 (HOSHITAMA)
        T1["Bản Gốc: Ẩn thân 5s<br/>Hồi nhiều Máu & Mana<br/>Chạy trốn câu giờ"] --> T2["Bản Mod: Xạ Thủ Ám Sát<br/>Hồi máu nhẹ, Xóa hồi Mana<br/>Mỗi đòn đốt 8% Max MP<br/>Nổ Sát thương = 2.5 x Mana đốt"]
    end

    subgraph ĐAO 25 (MAGUMANDARI)
        D1["Bản Gốc: Cấm quái thường đánh<br/>Vô dụng với Boss & PvP"] --> D2["Bản Mod: Bùa Hại Chuyên Trị Quái (8s)<br/>Chỉ tác dụng lên Quái, Tinh Anh, Thủ Lĩnh & Tà Thú<br/>Giảm 10-15% Công & Trừ 30-60 Kháng Tất Cả<br/>Cấp 4 lan 2 mục tiêu, Cấp 6 lan 3 mục tiêu"]
    end

    subgraph CUNG 25 (SOGEKIHEI)
        C1["Bản Gốc: Hút 70% sát thương 1:1<br/>Cháy sạch Mana sau 1 hit Boss<br/>Kẹt chiêu, sập khiên đột tử"] --> C2["Bản Mod: Tối Ưu Hiệu Suất Mana<br/>Đỡ 8-25% Sát thương (êm ái)<br/>Tỉ lệ tiêu hao giảm dần: 2.0 -> 1.0 MP/ST<br/>Không lo cạn kiệt Mana khi xả combo"]
    end

    subgraph KUNAI 25 (AOIAME)
        KN1["Bản Gốc: Cọc gỗ hút đòn 5-15s<br/>Hết giờ biến mất vô nghĩa"] --> KN2["Bản Mod: Bù Nhìn Mang Chỉ Số Chiêu Thuần<br/>Tấn công cơ bản + Băng sát + Nội công %<br/>Mỗi hit quái vả tăng thêm +5% Dame nổ<br/>Kích nổ cực mạnh dồn vào quái gần nhất"]
    end
```

---

## 7. KẾ HOẠCH TRIỂN KHAI VÀO CODEBASE

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

-- 3. Cập nhật Cooldown cho skill 25 Tiêu (ID 13): 45s -> 40s
UPDATE `skill` SET `cooldown` = 45000 WHERE `template_id` = 13 AND `point` = 1;
UPDATE `skill` SET `cooldown` = 44000 WHERE `template_id` = 13 AND `point` = 2;
UPDATE `skill` SET `cooldown` = 43000 WHERE `template_id` = 13 AND `point` = 3;
UPDATE `skill` SET `cooldown` = 42000 WHERE `template_id` = 13 AND `point` = 4;
UPDATE `skill` SET `cooldown` = 41000 WHERE `template_id` = 13 AND `point` = 5;
UPDATE `skill` SET `cooldown` = 40000 WHERE `template_id` = 13 AND `point` = 6;

-- 4. Cập nhật mô tả cho skill 35 Tiêu (ID 15)
UPDATE `skill_template` 
SET `description` = 'Ẩn thân xạ thủ: Hồi phục nhẹ HP, tiêu hao lượng lớn Mana trong mỗi đòn đánh để tăng vọt sát thương.' 
WHERE `id` = 15;

-- 5. Cập nhật mô tả gọn gàng & Details chỉ số chiêu 25 Đao (ID 40)
UPDATE `skill_option` SET `name` = 'Giảm sức tấn công của quái: -#%' WHERE `id` = 48;
INSERT INTO `skill_option` (`id`, `name`) VALUES (77, 'Giảm kháng tất cả các hệ: -#') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
INSERT INTO `skill_option` (`id`, `name`) VALUES (78, 'Duy trì bùa hại: # giây') ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

UPDATE `skill_template` 
SET `description` = 'Bùa Hại Trấn Áp: Yểm bùa lên quái vật làm suy giảm sức tấn công và kháng tất cả các hệ.' 
WHERE `id` = 40;

UPDATE `skill` SET `max_fight` = 1, `cooldown` = 25000, `options` = '[{"param":10,"id":48},{"param":30,"id":77},{"param":6,"id":78}]' WHERE `template_id` = 40 AND `point` = 1;
UPDATE `skill` SET `max_fight` = 1, `cooldown` = 24000, `options` = '[{"param":11,"id":48},{"param":36,"id":77},{"param":6,"id":78}]' WHERE `template_id` = 40 AND `point` = 2;
UPDATE `skill` SET `max_fight` = 1, `cooldown` = 23000, `options` = '[{"param":12,"id":48},{"param":42,"id":77},{"param":7,"id":78}]' WHERE `template_id` = 40 AND `point` = 3;
UPDATE `skill` SET `max_fight` = 2, `cooldown` = 22000, `options` = '[{"param":13,"id":48},{"param":48,"id":77},{"param":7,"id":78}]' WHERE `template_id` = 40 AND `point` = 4;
UPDATE `skill` SET `max_fight` = 2, `cooldown` = 21000, `options` = '[{"param":14,"id":48},{"param":54,"id":77},{"param":8,"id":78}]' WHERE `template_id` = 40 AND `point` = 5;
UPDATE `skill` SET `max_fight` = 3, `cooldown` = 20000, `options` = '[{"param":15,"id":48},{"param":60,"id":77},{"param":8,"id":78}]' WHERE `template_id` = 40 AND `point` = 6;

-- 6. Cập nhật mô tả & Cooldown cho skill 25 Cung (ID 31)
UPDATE `skill_template` 
SET `description` = 'Khiên Mana Hộ Mệnh: Chuyển 8-25% sát thương nhận vào sang Mana theo tỷ lệ tiêu hao tối ưu (2.0 -> 1.0 MP/ST).' 
WHERE `id` = 31;

UPDATE `skill` SET `cooldown` = 40000 WHERE `template_id` = 31 AND `point` = 1;
UPDATE `skill` SET `cooldown` = 35000 WHERE `template_id` = 31 AND `point` = 2;
UPDATE `skill` SET `cooldown` = 30000 WHERE `template_id` = 31 AND `point` = 3;
UPDATE `skill` SET `cooldown` = 25000 WHERE `template_id` = 31 AND `point` = 4;
UPDATE `skill` SET `cooldown` = 22000 WHERE `template_id` = 31 AND `point` = 5;
UPDATE `skill` SET `cooldown` = 20000 WHERE `template_id` = 31 AND `point` = 6;

-- 7. Cập nhật mô tả và Options chiêu thuần cho skill 25 Kunai (ID 22)
UPDATE `skill_template` 
SET `description` = 'Bù Nhìn Bộc Phá: Triệu hồi bù nhìn rơm thu hút kẻ địch. Khi biến mất, phát nổ gây sát thương Băng cực lớn lên quái gần nhất, tăng thêm 5% sát thương với mỗi đòn đã gánh chịu.' 
WHERE `id` = 22;
```

---

### Bước 2: Bổ sung logic kỹ năng trong `Char.java` & `Figurehead.java`

#### 2.1. Cập nhật class [Figurehead.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/model/Figurehead.java):
```java
public class Figurehead {
    public String name;
    public short x;
    public short y;
    public int countDown;
    
    // Thuộc tính tích trữ bộc phá chiêu thuần:
    public Char owner;
    public int hitsTaken = 0;
    public int baseAttack = 50;       // Tấn công cơ bản (Opt 0)
    public int iceAttack = 150;       // Băng sát (Opt 3)
    public int percentAttack = 100;   // Tấn công nội công % (Opt 11)
    public int bonusPercentPerHit = 5;// % cộng thêm mỗi hit
    
    public Figurehead(String name, short x, short y, int countDown) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.countDown = countDown;
    }
}
```

#### 2.2. Triệu hồi Bù nhìn Kunai trong [Char.java:6980](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/model/Char.java#L6980):
```java
} else if (skill.template.id == 22) {
    short x = (short) (this.x + (20 * dir));
    short y = this.y;
    if (!this.zone.tilemap.tileTypeAt(x, y, TileMap.T_TOP)) {
        serverMessage("Cần phải có khoảng trống phía trước mới có thể thi triển chiêu này");
        return;
    }
    Figurehead buNhin = new Figurehead(this.name, x, y, options[0].param);
    buNhin.owner = this;
    
    // Nạp các chỉ số chiêu thuần theo cấp kỹ năng
    int pt = skill.point;
    buNhin.baseAttack = 50 + (pt * 40);         // +50 -> +250 Tấn công cơ bản
    buNhin.iceAttack = 150 + (pt * 110);        // +150 -> +700 Băng sát
    buNhin.percentAttack = 85 + (pt * 16);      // +100% -> +180% Tấn công nội công
    buNhin.bonusPercentPerHit = (pt >= 5) ? 5 : (3 + (pt / 2)); // 3% -> 5% / hit
    
    zone.add(buNhin);
    zone.getService().createBuNhin(buNhin);
}
```

---

### Bước 3: Logic kích nổ và giao tranh trong `Zone.java`, `Mob.java` & `CombatService.java`

#### 3.1. Quái đánh bù nhìn tăng tích lũy trong [Mob.java:830](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/mob/Mob.java#L830):
```java
zone.getService().npcAttackBuNhin(this, j);
buNhin.hitsTaken++; // Tăng số hit mỗi khi quái/boss đánh trúng bù nhìn
return;
```

#### 3.2. Bù nhìn phát nổ khi hết giờ trong [Zone.java:965](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/map/zones/Zone.java#L965):
```java
// Khi bù nhìn biến mất (b.countDown <= 0):
// 1. Tìm quái gần nhất trong bán kính 200px quanh bù nhìn
Mob targetMob = null;
int minDistance = 200;
for (Mob m : this.mobs) {
    if (m != null && !m.isDead) {
        int d = NinjaUtils.getDistance(b.x, b.y, m.x, m.y);
        if (d < minDistance) {
            minDistance = d;
            targetMob = m;
        }
    }
}

// 2. Kích nổ sát thương chuẩn Engine (mang đầy đủ Tấn công cơ bản, Băng sát, Nội công %)
if (targetMob != null && b.owner != null && !targetMob.isDead) {
    boolean isFatal = b.owner.fatal > NinjaUtils.nextInt(1000);
    
    // Sát thương nội công & cơ bản
    int dameBasic = NinjaUtils.nextInt(b.owner.damage2, b.owner.damage);
    int skillDamage = b.baseAttack + (dameBasic * b.percentAttack / 100) + b.iceAttack;
    
    // Tính bạo kích nếu nổ chí mạng
    if (isFatal) {
        skillDamage += dameBasic;
        skillDamage += (dameBasic * b.owner.percentFatalDame / 100) + b.owner.fatalDame;
    }
    
    // Tương khắc hệ Thủy/Băng của Kunai lên hệ quái
    if (targetMob.sys == 1) { // Khắc quái hệ Hỏa
        skillDamage += skillDamage * 20 / 100;
    }
    
    // 3. Nhân thêm % theo số hit bù nhìn đã gánh (+5% / hit)
    int totalDamage = skillDamage + (skillDamage * (b.hitsTaken * b.bonusPercentPerHit) / 100);
    
    targetMob.addHp(-totalDamage);
    getService().attackMonster(totalDamage, isFatal, targetMob);
    if (targetMob.hp <= 0) {
        targetMob.die();
    }
}
```

---

## 8. ĐÁNH GIÁ CÂN BẰNG GAME

1. **Ninja Kiếm**: "Pháo đài sinh tồn" với lượng hồi phục tự nhiên vô địch từ thức ăn (Flat + % Max HP).
2. **Ninja Đao**: Chiêu 25 trở thành vũ khí săn Quái, Tinh Anh, Thủ Lĩnh và Tà Thú cực mạnh: làm suy yếu 10-15% công quái giúp Đao tank vững vàng, đồng thời xé toạc 30-60 kháng tất cả và lan tới 2-3 mục tiêu ở cấp 4/6 để dọn bãi và hỗ trợ đồng đội dồn sát thương. Không áp dụng PvP để đảm bảo công bằng.
3. **Ninja Tiêu**: Chiêu 25 tối ưu hồi chiêu 45s -> 40s giúp Tiêu duy trì vòng lửa liên tục hơn khi cày quái. Chiêu 35 là biến thể ám sát dồn sát thương bùng nổ theo Mana, đưa Tiêu trở thành xạ thủ burst dame tầm xa đích thực.
4. **Ninja Cung**: Khắc phục triệt để tình trạng cháy sạch Mana / kẹt chiêu, lớp khiên hộ thể chuyển đổi sát thương sang MP với hiệu suất chống đỡ 10% - 40%, thời gian hiệu lực 45s - 60s và hồi chiêu 60s - 40s (cho phép duy trì 100% thời gian khi max cấp) kết hợp chi phí MP tối ưu dần (2.0 -> 1.0 MP/ST).
5. **Ninja Kunai**: Chiêu 25 mang đầy đủ thông số của một CHIÊU THUẦN TẤN CÔNG (Tấn công cơ bản, Băng sát, Tấn công nội công %), kết hợp cơ chế tích tụ +5% sát thương mỗi hit khiến bù nhìn trở thành chiếc bẫy phản công cực kỳ đáng sợ đối với quái vật và Boss.

---

*Tài liệu được lưu trữ trực tiếp trong thư mục dự án để làm chuẩn mực kỹ thuật khi tiến hành cập nhật phiên bản máy chủ.*

---

## 9. CÂN BẰNG KỸ NĂNG CẤP 15 (6 PHÁI)

### Phân bậc chỉ số (Point 6 Max):
- Chí Mạng: Cung > Đao > Tiêu
- Chính Xác: Kunai > Cung > Tiêu
- Né Tránh: Tiêu > Kunai > Kiếm
- Phòng Thủ: Đao > Kiếm
- Tấn Công %: Cung > Kunai > Tiêu = Kiếm
- Tấn Công Thẳng: Tiêu > Kunai = Kiếm > Đao > Cung
- Tốc Chạy: Kunai > Tiêu > Kiếm

---

### Chi tiết thông số (Point 1 -> Point 6):
(Cấp 2 đến 5 tăng đều tuyến tính giữa cấp 1 và cấp 6)

1. CUNG - Washihitomi (ID 29 - Bị động):
- Point 1 (Lv 15): Công nội +9%, Chí mạng +20, Chính xác +20, Băng sát nội +20
- Point 6 (Lv 20): Công nội +45%, Chí mạng +100, Chính xác +120, Băng sát nội +100

2. TIÊU - Itotama (ID 11 - Bị động, bỏ phản đòn):
- Point 1 (Lv 15): Hỏa công nội +40, Né tránh +30, Tốc chạy +15, Công nội +3%, Chí mạng +15, Chính xác +15
- Point 6 (Lv 20): Hỏa công nội +300, Né tránh +150, Tốc chạy +35, Công nội +15%, Chí mạng +60, Chính xác +80

3. KUNAI - Akaiame (ID 20 - Bị động):
- Point 1 (Lv 15): Chính xác +20, Băng sát ngoại +40, Tốc chạy +20, Né tránh +25, Công ngoại +4%
- Point 6 (Lv 20): Chính xác +140, Băng sát ngoại +200, Tốc chạy +45, Né tránh +100, Công ngoại +20%

4. ĐAO - Konoitoame (ID 38 - Bị động):
- Point 1 (Lv 15): HP tối đa +5%, Phản đòn +40, Phong lôi ngoại +90, Chí mạng +20
- Point 6 (Lv 20): HP tối đa +30%, Phản đòn +200, Phong lôi ngoại +140, Chí mạng +80

5. KIẾM - Amagedon (ID 2 - Bị động):
- Point 1 (Lv 15): Kháng tất cả +35, Công ngoại +3%, Né tránh +20, Tốc chạy +10
- Point 6 (Lv 20): Kháng tất cả +180, Công ngoại +15%, Né tránh +80, Tốc chạy +40

6. QUẠT - Suishou (ID 47 - Chủ động hồi phục):
- *Lưu ý cơ chế cốt lõi*: Chiêu thức **KHÔNG hồi phục Mana**. Chiêu này hồi phục máu (HP) cho bản thân và đồng đội, trong đó lượng máu hồi phục được cộng thêm theo % Max MP của người dùng, và tiêu hao Mana khi thi triển.
- Point 1 (Lv 15): Mỗi 0.5s hồi 30 HP + (1.0% Max MP) máu trong 5s | Buff đồng đội: +3% hiệu quả, hồi chiêu 10s | Mana sử dụng: 120MP + 3% tối đa
- Point 6 (Lv 65): Mỗi 0.5s hồi 150 HP + (5.0% Max MP) máu trong 5s | Buff đồng đội: +15% hiệu quả, hồi chiêu 15s | Mana sử dụng: 200MP + 8% tối đa

---

## 10. CÂN BẰNG KỸ NĂNG CẤP 20 QUẠT (OOUCHIA)

### Định hướng thiết kế:
- Chiêu tâm pháp bị động tốn 11 điểm kỹ năng để nâng tối đa (Point 1 ở Lv 20 -> Point 12 ở Lv 31).
- Tăng chỉ số dàn trải, đa năng (Hybrid), mỗi thứ một chút để xứng đáng điểm đầu tư mà không phá vỡ cân bằng.
- Không tăng máu/mana theo %, chuyển thành tăng Máu và Mana trực tiếp (Flat) để nhân vật cứng cáp và dồi dào năng lượng ngay ở giai đoạn 2x.
- Điểm sáng cốt lõi: Tăng % kinh nghiệm nhận được khi đánh quái (giữ vững bản sắc Quạt).

---

### Chi tiết thông số (Point 1 -> Point 12):
(Cấp 2 đến 11 tăng đều tuyến tính giữa Point 1 và Point 12)

QUẠT - Oouchia (ID 48 - Tâm pháp bị động):
- Point 1 (Lv 20): Tăng EXP đánh quái +10%, HP tối đa +100, MP tối đa +100, Công nội +1%, Tốc chạy +10, Phong lôi nội + 50, chính xác +10, chí mạng +10
- Point 12 (Lv 31): Tăng EXP đánh quái +55%, HP tối đa +1000, MP tối đa +1000, Công nội +11%, Tốc chạy +30, Phong lôi nội + 150, chính xác +50, chí mạng +40

---

## 11. BẢNG MÔ TẢ ĐẦU CHUẨN CỦA CÁC CHIÊU ĐÃ MOD (SKILL_TEMPLATE)

| Phái | ID | Tên chiêu | Cấp | Loại | Dòng mô tả chuẩn |
| :--- | :---: | :--- | :---: | :---: | :--- |
| **Kiếm** | **2** | Amagedon | 15 | Bị động | **Tâm pháp Kiếm thuật** |
| **Tiêu** | **11** | Itotama | 15 | Bị động | **Tâm pháp Tiêu thuật** |
| **Kunai** | **20** | Akaiame | 15 | Bị động | **Tâm pháp Ảnh Nhận thuật** |
| **Cung** | **29** | Washihitomi | 15 | Bị động | **Tâm pháp Tiễn thuật** |
| **Đao** | **38** | Konoitoame | 15 | Bị động | **Tâm pháp Đao thuật** |
| **Quạt** | **47** | Suishou | 15 | Chủ động | **Chủ động hồi phục cho bản thân và đồng đội** |
| **Quạt** | **48** | Oouchia | 20 | Bị động | **Tâm pháp Thiết Phiến thuật** |
