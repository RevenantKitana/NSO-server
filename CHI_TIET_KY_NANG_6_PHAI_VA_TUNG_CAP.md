# CẨM NANG TOÀN DIỆN CHI TIẾT KỸ NĂNG VÀ TỪNG CẤP ĐỘ CỦA 6 PHÁI NSO

> **Phiên bản máy chủ**: NSO Ninja School Private 2026  
> **Mã nguồn tính toán**: `com.nsoz.skill`, `AbilityFromEquip`, `Char`  
> **Cơ sở dữ liệu trích xuất**: MariaDB `nso_test` (`skill_template`, `skill`, `skill_option`, `clazz`)  
> **Quy mô dữ liệu**: 6 Phái + Vô phái, 91 Skill Templates, 967 Cấp độ kỹ năng đầy đủ thông số.  

---

## MỤC LỤC
1. [Cơ chế Hoạt động & Hệ thống Thuộc tính Kỹ năng](#1-cơ-chế-hoạt-động--hệ-thống-thuộc-tính-kỹ-năng)
2. [Phái 1: Ninja Kiếm (Katana - Hệ Hỏa)](#2-phái-1-ninja-kiếm-katana---hệ-hỏa)
3. [Phái 2: Ninja Phi Tiêu (Shuriken - Hệ Hỏa)](#3-phái-2-ninja-phi-tiêu-shuriken---hệ-hỏa)
4. [Phái 3: Ninja Kunai (Đoản Đao - Hệ Băng)](#4-phái-3-ninja-kunai-đoản-đao---hệ-băng)
5. [Phái 4: Ninja Cung (Cung Tên - Hệ Băng)](#5-phái-4-ninja-cung-cung-tên---hệ-băng)
6. [Phái 5: Ninja Đao (Bản Đao - Hệ Phong)](#6-phái-5-ninja-đao-bản-đao---hệ-phong)
7. [Phái 6: Ninja Quạt (Phiến - Hệ Phong)](#7-phái-6-ninja-quạt-phiến---hệ-phong)
8. [Phái 0: Chưa Vào Trường (Kỹ Năng Cơ Bản)](#8-phái-0-chưa-vào-trường-kỹ-năng-cơ-bản)

---

## 1. CƠ CHẾ HOẠT ĐỘNG & HỆ THỐNG THUỘC TÍNH KỸ NĂNG

Trong Game Engine NSO:
- **Point (Cấp điểm kỹ năng)**: Từ cấp 1 đến `maxPoint` (6, 10, 12, hoặc 15 điểm). Cấp 0 là trạng thái khởi tạo chưa cộng điểm.
- **Level (Yêu cầu cấp nhân vật)**: Cấp độ nhân vật tối thiểu để người chơi có thể cộng điểm vào cấp kỹ năng đó.
- **Mana Use (Tiêu hao MP)**: Lượng Chakra (MP) tiêu tốn mỗi lần xuất chiêu.
- **Cooldown (Hồi chiêu)**: Tính bằng mili-giây (ms). Các chiêu tâm pháp/bị động có cooldown = 0ms.
- **Max Fight (Số mục tiêu)**: 1 (đơn mục tiêu), 3 hoặc 4 (đánh lan diện rộng).
- **Phạm vi tác động (dx, dy)**: Khoảng cách tầm đánh ngang (dx) và dọc (dy) tính bằng pixel.
- **Options (Chỉ số hiệu ứng)**: Sát thương phần trăm, điểm sát thương hệ, hỗ trợ chiêu cấp trước/sau, hiệu ứng khống chế (bỏng, đóng băng, choáng, tàng hình, hồi phục, hồi sinh...).

---

## 2. PHÁI 1: NINJA KIẾM (KATANA - HỆ HỎA)

* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Cận chiến / Ngoại công (Sức Khỏe)
* **Đặc trưng**: Sát thương bạo kích cực lớn, tỉ lệ nổ chí mạng cao, đòn đánh dồn dập thiêu đốt rút máu.

### Skill ID 1: Chiêu Hiyoko
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Kiếm pháp nhập môn - vung kiếm vào một mục tiêu gây sát thương lên mục tiêu đó

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 550ms | 1 | 51 x 18 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.10 | 1 | 500ms | 1 | 51 x 18 | Tấn công ngoại: +5%, Hỏa công: +10, Tấn công cơ bản: +100%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 2 | 500ms | 1 | 51 x 18 | Tấn công ngoại: +10%, Hỏa công: +20, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 3 | 500ms | 1 | 51 x 18 | Tấn công ngoại: +15%, Hỏa công: +30, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 4 | 450ms | 1 | 51 x 18 | Tấn công ngoại: +20%, Hỏa công: +40, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 5 | 450ms | 1 | 51 x 18 | Tấn công ngoại: +25%, Hỏa công: +50, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 6 | 450ms | 1 | 51 x 18 | Tấn công ngoại: +30%, Hỏa công: +60, Tấn công cơ bản: +100%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 7 | 400ms | 1 | 51 x 18 | Tấn công ngoại: +35%, Hỏa công: +70, Tấn công cơ bản: +100%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 8 | 400ms | 1 | 51 x 18 | Tấn công ngoại: +40%, Hỏa công: +80, Tấn công cơ bản: +100%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 9 | 400ms | 1 | 51 x 18 | Tấn công ngoại: +45%, Hỏa công: +90, Tấn công cơ bản: +100%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 10 | 350ms | 1 | 51 x 18 | Tấn công ngoại: +50%, Hỏa công: +100, Tấn công cơ bản: +100%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 12 | 350ms | 1 | 51 x 18 | Tấn công ngoại: +55%, Hỏa công: +110, Tấn công cơ bản: +100%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 15 | 300ms | 1 | 51 x 18 | Tấn công ngoại: +60%, Hỏa công: +120, Tấn công cơ bản: +100%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 2: Chiêu Amagedon
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao khả năng tấn công và phòng thủ

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +30, Né đòn: +50, Kháng tất cả: +80, Tốc độ di chuyển: +35 |
| **Cấp 2** | Lv.16 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +35, Né đòn: +60, Kháng tất cả: +100, Tốc độ di chuyển: +40 |
| **Cấp 3** | Lv.17 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +40, Né đòn: +70, Kháng tất cả: +120, Tốc độ di chuyển: +45 |
| **Cấp 4** | Lv.18 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +50, Né đòn: +80, Kháng tất cả: +130, Tốc độ di chuyển: +50 |
| **Cấp 5** | Lv.19 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +60, Né đòn: +90, Kháng tất cả: +140, Tốc độ di chuyển: +55 |
| **Cấp 6** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +70, Né đòn: +100, Kháng tất cả: +150, Tốc độ di chuyển: +60 |

### Skill ID 3: Chiêu Jizokuzan
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Chém liên tục 3 nhát kiếm gây sát thương cho một mục tiêu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 1s | 1 | 51 x 29 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.20 | 31 | 850ms | 1 | 51 x 29 | Tấn công ngoại: +40%, Hỏa công: +180, Tấn công cơ bản: +150%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 40: +12% |
| **Cấp 2** | Lv.21 | 32 | 750ms | 1 | 51 x 29 | Tấn công ngoại: +45%, Hỏa công: +190, Tấn công cơ bản: +150%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +18% |
| **Cấp 3** | Lv.22 | 33 | 650ms | 1 | 51 x 29 | Tấn công ngoại: +50%, Hỏa công: +200, Tấn công cơ bản: +150%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +24% |
| **Cấp 4** | Lv.23 | 34 | 550ms | 1 | 51 x 29 | Tấn công ngoại: +55%, Hỏa công: +210, Tấn công cơ bản: +150%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +31% |
| **Cấp 5** | Lv.24 | 35 | 450ms | 1 | 51 x 29 | Tấn công ngoại: +60%, Hỏa công: +220, Tấn công cơ bản: +150%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +38% |
| **Cấp 6** | Lv.25 | 36 | 450ms | 1 | 51 x 29 | Tấn công ngoại: +65%, Hỏa công: +230, Tấn công cơ bản: +150%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +45% |
| **Cấp 7** | Lv.26 | 37 | 400ms | 1 | 51 x 29 | Tấn công ngoại: +70%, Hỏa công: +240, Tấn công cơ bản: +150%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +52% |
| **Cấp 8** | Lv.27 | 38 | 400ms | 1 | 51 x 29 | Tấn công ngoại: +75%, Hỏa công: +260, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +59% |
| **Cấp 9** | Lv.28 | 39 | 350ms | 1 | 51 x 29 | Tấn công ngoại: +80%, Hỏa công: +280, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +66% |
| **Cấp 10** | Lv.29 | 40 | 350ms | 1 | 51 x 29 | Tấn công ngoại: +90%, Hỏa công: +300, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +73% |
| **Cấp 11** | Lv.30 | 41 | 350ms | 1 | 51 x 29 | Tấn công ngoại: +100%, Hỏa công: +320, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +81% |
| **Cấp 12** | Lv.31 | 42 | 350ms | 1 | 51 x 29 | Tấn công ngoại: +110%, Hỏa công: +340, Tấn công cơ bản: +150%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 40: +90% |

### Skill ID 4: Chiêu Kakyuu
* **Loại chiêu**: Khống chế / Bắt quái
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng hỏa thuật thiêu đốt quái vật biến chúng thành thức ăn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +500HP |
| **Cấp 2** | Lv.26 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +1000HP |
| **Cấp 3** | Lv.27 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +1500HP |
| **Cấp 4** | Lv.28 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +2000HP |
| **Cấp 5** | Lv.29 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +2500HP |
| **Cấp 6** | Lv.30 | 50 | 25s | 1 | 120 x 80 | Biến quái thành thức ăn: tăng +3000HP |

### Skill ID 5: Chiêu X Zangeki
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Xuất ra đường kiếm nhanh nhẹn đan chéo gây sát thương tối đa lên 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 63 x 25 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.30 | 46 | 725ms | 2 | 63 x 25 | Tấn công ngoại: +50%, Hỏa công: +190, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 47 | 700ms | 2 | 63 x 25 | Tấn công ngoại: +55%, Hỏa công: +210, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 48 | 650ms | 2 | 63 x 25 | Tấn công ngoại: +60%, Hỏa công: +230, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 49 | 600ms | 2 | 63 x 25 | Tấn công ngoại: +65%, Hỏa công: +250, Tấn công cơ bản: +150%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 50 | 525ms | 2 | 63 x 25 | Tấn công ngoại: +70%, Hỏa công: +270, Tấn công cơ bản: +150%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 51 | 500ms | 3 | 63 x 25 | Tấn công ngoại: +80%, Hỏa công: +290, Tấn công cơ bản: +150%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 52 | 450ms | 3 | 63 x 25 | Tấn công ngoại: +85%, Hỏa công: +310, Tấn công cơ bản: +150%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 53 | 425ms | 3 | 63 x 25 | Tấn công ngoại: +90%, Hỏa công: +330, Tấn công cơ bản: +150%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 54 | 400ms | 3 | 63 x 25 | Tấn công ngoại: +95%, Hỏa công: +350, Tấn công cơ bản: +150%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 55 | 375ms | 3 | 63 x 25 | Tấn công ngoại: +100%, Hỏa công: +370, Tấn công cơ bản: +150%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 56 | 350ms | 3 | 63 x 25 | Tấn công ngoại: +105%, Hỏa công: +390, Tấn công cơ bản: +150%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 57 | 300ms | 3 | 63 x 25 | Tấn công ngoại: +110%, Hỏa công: +410, Tấn công cơ bản: +150%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 6: Chiêu Raikou
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Làm cho bản thân tàng hình trước kẻ thù đồng thời gây chí mạng ở nhát đánh đầu tiên trong trạng thái tàng hình

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.35 | 50 | 55s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 5 giây |
| **Cấp 2** | Lv.36 | 60 | 53s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 7 giây |
| **Cấp 3** | Lv.37 | 70 | 51s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 9 giây |
| **Cấp 4** | Lv.38 | 80 | 49s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 11 giây |
| **Cấp 5** | Lv.39 | 90 | 47s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 13 giây |
| **Cấp 6** | Lv.40 | 100 | 45s | 1 | Bản thân / Ẩn | Tàng hình: duy trì 15 giây |

### Skill ID 7: Chiêu Hihebun
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tạo ra cơn mưa kiếm, gây sát thương cực lớn lên một mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 20s | 1 | 48 x 85 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.40 | 160 | 19s | 1 | 48 x 85 | Xác suất gây bỏng: +40% duy trì 4 giây, Tấn công ngoại: +80%, Hỏa công: +800, Tấn công cơ bản: +55%, Tương khắc: +70 |
| **Cấp 2** | Lv.41 | 170 | 18.50s | 1 | 48 x 85 | Xác suất gây bỏng: +43% duy trì 4 giây, Tấn công ngoại: +80%, Hỏa công: +1000, Tấn công cơ bản: +60%, Tương khắc: +75 |
| **Cấp 3** | Lv.42 | 180 | 18.50s | 1 | 48 x 85 | Xác suất gây bỏng: +46% duy trì 4 giây, Tấn công ngoại: +85%, Hỏa công: +1200, Tấn công cơ bản: +65%, Tương khắc: +75 |
| **Cấp 4** | Lv.43 | 190 | 18s | 1 | 48 x 85 | Xác suất gây bỏng: +49% duy trì 4 giây, Tấn công ngoại: +85%, Hỏa công: +1400, Tấn công cơ bản: +70%, Tương khắc: +80 |
| **Cấp 5** | Lv.44 | 200 | 18s | 1 | 48 x 85 | Xác suất gây bỏng: +52% duy trì 4 giây, Tấn công ngoại: +90%, Hỏa công: +1600, Tấn công cơ bản: +75%, Tương khắc: +80 |
| **Cấp 6** | Lv.45 | 210 | 17.50s | 1 | 48 x 85 | Xác suất gây bỏng: +55% duy trì 4 giây, Tấn công ngoại: +90%, Hỏa công: +1800, Tấn công cơ bản: +80%, Tương khắc: +85 |
| **Cấp 7** | Lv.46 | 220 | 17.50s | 1 | 48 x 85 | Xác suất gây bỏng: +58% duy trì 4 giây, Tấn công ngoại: +95%, Hỏa công: +2000, Tấn công cơ bản: +85%, Tương khắc: +85 |
| **Cấp 8** | Lv.47 | 230 | 17s | 1 | 48 x 85 | Xác suất gây bỏng: +61% duy trì 4 giây, Tấn công ngoại: +95%, Hỏa công: +2200, Tấn công cơ bản: +90%, Tương khắc: +90 |
| **Cấp 9** | Lv.48 | 240 | 17s | 1 | 48 x 85 | Xác suất gây bỏng: +65% duy trì 4 giây, Tấn công ngoại: +100%, Hỏa công: +2400, Tấn công cơ bản: +95%, Tương khắc: +90 |
| **Cấp 10** | Lv.49 | 250 | 16.50s | 1 | 48 x 85 | Xác suất gây bỏng: +70% duy trì 4 giây, Tấn công ngoại: +105%, Hỏa công: +2600, Tấn công cơ bản: +100%, Tương khắc: +95 |
| **Cấp 11** | Lv.50 | 260 | 16.50s | 1 | 48 x 85 | Xác suất gây bỏng: +75% duy trì 4 giây, Tấn công ngoại: +110%, Hỏa công: +2800, Tấn công cơ bản: +105%, Tương khắc: +95 |
| **Cấp 12** | Lv.51 | 270 | 16s | 1 | 48 x 85 | Xác suất gây bỏng: +80% duy trì 4 giây, Tấn công ngoại: +120%, Hỏa công: +3000, Tấn công cơ bản: +110%, Tương khắc: +100 |

### Skill ID 8: Chiêu Shuurai
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tăng khả năng kháng phong đồng thời giúp bản thân nhanh chóng hồi phục khi bị choáng

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +20, Giảm thời gian bị choáng: -0.2 giây, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +35, Giảm thời gian bị choáng: -0.2 giây, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +50, Giảm thời gian bị choáng: -0.3 giây, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +65, Giảm thời gian bị choáng: -0.3 giây, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +80, Giảm thời gian bị choáng: -0.4 giây, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +100, Giảm thời gian bị choáng: -0.5 giây, Tương khắc: +100 |

### Skill ID 9: Chiêu Choukouhirenzo
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Xuất ra 3 hỏa kiếm cường mạnh gây sát thương trong một phạm vi nhất định, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 0 | 1s | 1 | 65 x 25 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.50 | 88 | 900ms | 2 | 65 x 25 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công ngoại: +120%, Hỏa công: +200, Tấn công cơ bản: +180%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 90 | 850ms | 2 | 65 x 25 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +130%, Hỏa công: +250, Tấn công cơ bản: +200%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 92 | 800ms | 2 | 65 x 25 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +140%, Hỏa công: +300, Tấn công cơ bản: +220%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 94 | 750ms | 2 | 65 x 25 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +150%, Hỏa công: +350, Tấn công cơ bản: +240%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 96 | 700ms | 3 | 65 x 25 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +160%, Hỏa công: +400, Tấn công cơ bản: +260%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 98 | 650ms | 3 | 65 x 25 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +170%, Hỏa công: +450, Tấn công cơ bản: +280%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 100 | 600ms | 3 | 65 x 25 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +180%, Hỏa công: +500, Tấn công cơ bản: +300%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 102 | 550ms | 3 | 65 x 25 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +190%, Hỏa công: +550, Tấn công cơ bản: +320%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 104 | 500ms | 4 | 65 x 25 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +200%, Hỏa công: +600, Tấn công cơ bản: +340%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 106 | 450ms | 4 | 65 x 25 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +210%, Hỏa công: +650, Tấn công cơ bản: +360%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 108 | 400ms | 4 | 65 x 25 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +220%, Hỏa công: +700, Tấn công cơ bản: +380%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 110 | 350ms | 4 | 65 x 25 | Xác xuất gây bỏng: +9% duy trì 2 giây, Tấn công ngoại: +230%, Hỏa công: +750, Tấn công cơ bản: +400%, Tương khắc: +130 |

### Skill ID 55: Chiêu Pawaraikou
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Lấy hỏa làm gốc dung hợp vào nhát đánh đầu tiên gây nên sát thương cực lớn.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 40% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 2** | Lv.61 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 45% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 3** | Lv.62 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 50% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 4** | Lv.63 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 55% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 5** | Lv.64 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 60% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 6** | Lv.65 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 70% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 7** | Lv.66 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 80% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 8** | Lv.67 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 90% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 9** | Lv.68 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 100% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 10** | Lv.69 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 115% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 11** | Lv.70 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 130% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 12** | Lv.71 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 150% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 13** | Lv.72 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 200% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 14** | Lv.73 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 250% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |
| **Cấp 15** | Lv.74 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tăng 300% tấn công của nhát đánh đầu tiên của chiêu cấp 35 |

### Skill ID 61: Chiêu Maajizangeki
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Xuất ra tam hỏa kiếm hội tụ thành Cự Ảnh Kiếm bùng nổ tiêu diệt kẻ địch.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 55 x 30 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.70 | 95 | 700ms | 1 | 55 x 30 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công ngoại: +220%, Hỏa công: +200, Tấn công cơ bản: +160%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 98 | 650ms | 1 | 55 x 30 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +230%, Hỏa công: +250, Tấn công cơ bản: +200%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 101 | 650ms | 1 | 55 x 30 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +240%, Hỏa công: +600, Tấn công cơ bản: +240%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 104 | 600ms | 2 | 55 x 30 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +250%, Hỏa công: +700, Tấn công cơ bản: +280%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 107 | 600ms | 2 | 55 x 30 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +260%, Hỏa công: +800, Tấn công cơ bản: +320%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 110 | 550ms | 2 | 55 x 30 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +270%, Hỏa công: +900, Tấn công cơ bản: +360%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 113 | 550ms | 3 | 55 x 30 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +280%, Hỏa công: +1000, Tấn công cơ bản: +400%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 116 | 500ms | 3 | 55 x 30 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +290%, Hỏa công: +1100, Tấn công cơ bản: +440%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 119 | 450ms | 3 | 55 x 30 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +300%, Hỏa công: +1200, Tấn công cơ bản: +480%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 122 | 400ms | 4 | 55 x 30 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +310%, Hỏa công: +1300, Tấn công cơ bản: +520%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 125 | 350ms | 4 | 55 x 30 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +320%, Hỏa công: +1400, Tấn công cơ bản: +560%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 128 | 300ms | 4 | 55 x 30 | Xác xuất gây bỏng: +9% duy trì 2 giây, Tấn công ngoại: +330%, Hỏa công: +1500, Tấn công cơ bản: +600%, Tương khắc: +150 |

### Skill ID 67: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 73: Chiêu Ikkakujuu
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Vung kiếm tạo ra Hỏa kỳ lân đốt cháy kẻ thù.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.80 | 110 | 500ms | 2 | 120 x 46 | Xác xuất gây bỏng: +2% duy trì 2 giây, Tấn công ngoại: +270%, Hỏa công: +200, Tấn công cơ bản: +400%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 112 | 490ms | 2 | 120 x 46 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công ngoại: +280%, Hỏa công: +400, Tấn công cơ bản: +420%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 114 | 480ms | 2 | 120 x 46 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +290%, Hỏa công: +600, Tấn công cơ bản: +440%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 116 | 470ms | 3 | 120 x 46 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +300%, Hỏa công: +800, Tấn công cơ bản: +460%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 118 | 460ms | 3 | 120 x 46 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +310%, Hỏa công: +1000, Tấn công cơ bản: +480%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 120 | 440ms | 3 | 120 x 46 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +320%, Hỏa công: +1200, Tấn công cơ bản: +500%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 122 | 420ms | 4 | 120 x 46 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +330%, Hỏa công: +1400, Tấn công cơ bản: +520%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 124 | 400ms | 4 | 120 x 46 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +340%, Hỏa công: +1600, Tấn công cơ bản: +540%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 126 | 380ms | 4 | 120 x 46 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +350%, Hỏa công: +1800, Tấn công cơ bản: +560%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 128 | 360ms | 5 | 120 x 46 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +360%, Hỏa công: +2000, Tấn công cơ bản: +580%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 130 | 330ms | 5 | 120 x 46 | Xác xuất gây bỏng: +9% duy trì 2 giây, Tấn công ngoại: +370%, Hỏa công: +2200, Tấn công cơ bản: +610%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 132 | 300ms | 5 | 120 x 46 | Xác xuất gây bỏng: +10% duy trì 2 giây, Tấn công ngoại: +380%, Hỏa công: +2400, Tấn công cơ bản: +650%, Tương khắc: +180 |

### Skill ID 79: Chiêu Enko Bakusatsu
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Hỏa thuật Hỏa Long Xuất Thế

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.100 | 128 | 500ms | 3 | 120 x 46 | Xác xuất gây bỏng: +2% duy trì 2 giây, Tấn công ngoại: +305%, Hỏa công: +1000, Tấn công cơ bản: +500%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 130 | 490ms | 3 | 120 x 46 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công ngoại: +310%, Hỏa công: +1200, Tấn công cơ bản: +510%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 132 | 480ms | 3 | 120 x 46 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công ngoại: +315%, Hỏa công: +1400, Tấn công cơ bản: +520%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 134 | 470ms | 3 | 120 x 46 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +320%, Hỏa công: +1600, Tấn công cơ bản: +530%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 136 | 460ms | 4 | 120 x 46 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công ngoại: +330%, Hỏa công: +1900, Tấn công cơ bản: +540%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 138 | 440ms | 4 | 120 x 46 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +340%, Hỏa công: +2200, Tấn công cơ bản: +560%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 140 | 420ms | 4 | 120 x 46 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công ngoại: +350%, Hỏa công: +2500, Tấn công cơ bản: +580%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 142 | 400ms | 4 | 120 x 46 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +360%, Hỏa công: +2800, Tấn công cơ bản: +600%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 144 | 380ms | 5 | 120 x 46 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công ngoại: +380%, Hỏa công: +3200, Tấn công cơ bản: +620%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 146 | 360ms | 5 | 120 x 46 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công ngoại: +400%, Hỏa công: +3600, Tấn công cơ bản: +650%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 148 | 330ms | 5 | 120 x 46 | Xác xuất gây bỏng: +9% duy trì 2 giây, Tấn công ngoại: +420%, Hỏa công: +4000, Tấn công cơ bản: +680%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 150 | 300ms | 6 | 120 x 46 | Xác xuất gây bỏng: +10% duy trì 2 giây, Tấn công ngoại: +450%, Hỏa công: +4500, Tấn công cơ bản: +720%, Tương khắc: +200 |

### Skill ID 85: Hoả Hồn Long Trảm
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Bí thuật định hình Chakra - Tạo ra những thanh kiếm năng lượng nhắm thẳng đến mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất gây bỏng: +11% duy trì 2 giây, Tấn công ngoại: +550%, Hỏa công: +5500, Tấn công cơ bản: +770%, Tương khắc: +300 |

---

## 3. PHÁI 2: NINJA PHI TIÊU (SHURIKEN - HỆ HỎA)

* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Viễn chiến / Nội công (Chakra)
* **Đặc trưng**: Tốc độ ném phi tiêu nhanh nhất game, tầm xa linh hoạt, phân thân thả diều đối thủ.

### Skill ID 10: Chiêu Otama
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tiêu thuật nhập môn - phóng phi tiêu gây sát thương lên một mục tiêu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 750ms | 1 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.10 | 2 | 450ms | 1 | 150 x 150 | Tấn công nội: +5%, Hỏa công: +10, Tấn công cơ bản: +90%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 4 | 450ms | 1 | 150 x 150 | Tấn công nội: +10%, Hỏa công: +20, Tấn công cơ bản: +90%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 6 | 450ms | 1 | 150 x 150 | Tấn công nội: +15%, Hỏa công: +30, Tấn công cơ bản: +90%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 8 | 400ms | 1 | 150 x 150 | Tấn công nội: +20%, Hỏa công: +40, Tấn công cơ bản: +90%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 10 | 400ms | 1 | 150 x 150 | Tấn công nội: +25%, Hỏa công: +50, Tấn công cơ bản: +90%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 12 | 400ms | 1 | 150 x 150 | Tấn công nội: +30%, Hỏa công: +60, Tấn công cơ bản: +90%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 14 | 350ms | 1 | 150 x 150 | Tấn công nội: +35%, Hỏa công: +70, Tấn công cơ bản: +90%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 16 | 350ms | 1 | 150 x 150 | Tấn công nội: +40%, Hỏa công: +80, Tấn công cơ bản: +90%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 18 | 350ms | 1 | 150 x 150 | Tấn công nội: +45%, Hỏa công: +90, Tấn công cơ bản: +90%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 20 | 300ms | 1 | 150 x 150 | Tấn công nội: +50%, Hỏa công: +100, Tấn công cơ bản: +90%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 21 | 300ms | 1 | 150 x 150 | Tấn công nội: +55%, Hỏa công: +110, Tấn công cơ bản: +90%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 22 | 250ms | 1 | 150 x 150 | Tấn công nội: +60%, Hỏa công: +120, Tấn công cơ bản: +90%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 11: Chiêu Itotama
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao khả năng tấn công và phản đòn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +20, Hỏa công nội: +20, Chính xác: +45, Phản đòn cận chiến: +20 |
| **Cấp 2** | Lv.16 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +24, Hỏa công nội: +25, Chính xác: +50, Phản đòn cận chiến: +40 |
| **Cấp 3** | Lv.17 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +28, Hỏa công nội: +30, Chính xác: +55, Phản đòn cận chiến: +60 |
| **Cấp 4** | Lv.18 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +32, Hỏa công nội: +35, Chính xác: +60, Phản đòn cận chiến: +80 |
| **Cấp 5** | Lv.19 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +36, Hỏa công nội: +40, Chính xác: +65, Phản đòn cận chiến: +90 |
| **Cấp 6** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +40, Hỏa công nội: +45, Chính xác: +70, Phản đòn cận chiến: +100 |

### Skill ID 12: Chiêu Kasoushuriken
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phóng 2 phi tiêu liên tục gây sát thương cho một mục tiêu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 1s | 1 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.20 | 32 | 750ms | 1 | 150 x 150 | Tấn công nội: +50%, Hỏa công: +100, Tấn công cơ bản: +75%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 40: +12% |
| **Cấp 2** | Lv.21 | 34 | 650ms | 1 | 150 x 150 | Tấn công nội: +55%, Hỏa công: +105, Tấn công cơ bản: +75%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +18% |
| **Cấp 3** | Lv.22 | 36 | 650ms | 1 | 150 x 150 | Tấn công nội: +60%, Hỏa công: +110, Tấn công cơ bản: +75%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +24% |
| **Cấp 4** | Lv.23 | 38 | 550ms | 1 | 150 x 150 | Tấn công nội: +65%, Hỏa công: +120, Tấn công cơ bản: +75%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +31% |
| **Cấp 5** | Lv.24 | 40 | 550ms | 1 | 150 x 150 | Tấn công nội: +70%, Hỏa công: +130, Tấn công cơ bản: +75%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +38% |
| **Cấp 6** | Lv.25 | 42 | 500ms | 1 | 150 x 150 | Tấn công nội: +75%, Hỏa công: +140, Tấn công cơ bản: +75%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +45% |
| **Cấp 7** | Lv.26 | 44 | 500ms | 1 | 150 x 150 | Tấn công nội: +80%, Hỏa công: +150, Tấn công cơ bản: +75%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +52% |
| **Cấp 8** | Lv.27 | 46 | 450ms | 1 | 150 x 150 | Tấn công nội: +85%, Hỏa công: +160, Tấn công cơ bản: +75%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +59% |
| **Cấp 9** | Lv.28 | 48 | 450ms | 1 | 150 x 150 | Tấn công nội: +90%, Hỏa công: +170, Tấn công cơ bản: +75%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +66% |
| **Cấp 10** | Lv.29 | 50 | 400ms | 1 | 150 x 150 | Tấn công nội: +100%, Hỏa công: +180, Tấn công cơ bản: +75%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +73% |
| **Cấp 11** | Lv.30 | 52 | 350ms | 1 | 150 x 150 | Tấn công nội: +110%, Hỏa công: +190, Tấn công cơ bản: +75%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +81% |
| **Cấp 12** | Lv.31 | 54 | 300ms | 1 | 150 x 150 | Tấn công nội: +120%, Hỏa công: +200, Tấn công cơ bản: +75%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 40: +90% |

### Skill ID 13: Chiêu Taiyoutama
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng hỏa thuật triều hồi vòng lửa vô hình thiêu đốt các mục tiêu bản thân tấn công

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 120 | 45s | 1 | 100 x 100 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +25% duy trì 30 giây |
| **Cấp 2** | Lv.26 | 120 | 45s | 1 | 110 x 110 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +30% duy trì 30 giây |
| **Cấp 3** | Lv.27 | 120 | 45s | 1 | 120 x 120 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +35% duy trì 30 giây |
| **Cấp 4** | Lv.28 | 120 | 45s | 1 | 130 x 130 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +40% duy trì 30 giây |
| **Cấp 5** | Lv.29 | 120 | 45s | 1 | 140 x 140 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +50% duy trì 30 giây |
| **Cấp 6** | Lv.30 | 120 | 45s | 1 | 150 x 150 | Đốt Hp của quái vật dựa trên tấn công hiện tại: +60% duy trì 30 giây |

### Skill ID 14: Chiêu Bikoushuriken
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Vận dụng hỏa thuật đốt cháy phi tiêu phóng đi gây sát thương tối đa lên 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.30 | 58 | 750ms | 2 | 150 x 150 | Tấn công nội: +50%, Hỏa công: +150, Tấn công cơ bản: +90%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 60 | 700ms | 2 | 150 x 150 | Tấn công nội: +55%, Hỏa công: +160, Tấn công cơ bản: +90%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 62 | 700ms | 2 | 150 x 150 | Tấn công nội: +60%, Hỏa công: +170, Tấn công cơ bản: +90%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 64 | 650ms | 2 | 150 x 150 | Tấn công nội: +65%, Hỏa công: +180, Tấn công cơ bản: +90%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 66 | 650ms | 2 | 150 x 150 | Tấn công nội: +70%, Hỏa công: +190, Tấn công cơ bản: +90%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 68 | 600ms | 3 | 150 x 150 | Tấn công nội: +80%, Hỏa công: +200, Tấn công cơ bản: +90%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 70 | 600ms | 3 | 150 x 150 | Tấn công nội: +85%, Hỏa công: +210, Tấn công cơ bản: +90%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 74 | 550ms | 3 | 150 x 150 | Tấn công nội: +90%, Hỏa công: +220, Tấn công cơ bản: +90%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 78 | 500ms | 3 | 150 x 150 | Tấn công nội: +95%, Hỏa công: +230, Tấn công cơ bản: +90%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 82 | 450ms | 3 | 150 x 150 | Tấn công nội: +100%, Hỏa công: +240, Tấn công cơ bản: +90%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 86 | 400ms | 3 | 150 x 150 | Tấn công nội: +105%, Hỏa công: +250, Tấn công cơ bản: +90%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 90 | 350ms | 3 | 150 x 150 | Tấn công nội: +110%, Hỏa công: +260, Tấn công cơ bản: +90%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 15: Chiêu Hoshitama
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng ẩn thân thuật để bản thân trở nên vô hình đồng thời hồi phục hp và mp trong một thời gian xác định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.35 | 50 | 55s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +150 duy trì 5 giây |
| **Cấp 2** | Lv.36 | 70 | 53s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +200 duy trì 5 giây |
| **Cấp 3** | Lv.37 | 90 | 51s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +250 duy trì 5 giây |
| **Cấp 4** | Lv.38 | 110 | 49s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +300 duy trì 5 giây |
| **Cấp 5** | Lv.39 | 130 | 47s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +350 duy trì 5 giây |
| **Cấp 6** | Lv.40 | 150 | 45s | 1 | Bản thân / Ẩn | Ẩn thân mỗi nữa giây phục hồi Mp, Hp: +400 duy trì 5 giây |

### Skill ID 16: Chiêu Hinotama
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Trong chớp mắt phóng 2 phi tiêu bốc cháy về phía kẻ địch, gây ra lượng sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 19s | 1 | 150 x 160 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.40 | 435 | 18s | 1 | 150 x 160 | Xác suất gây bỏng: +30% duy trì 4 giây, Tấn công nội: +80%, Hỏa công: +400, Tấn công cơ bản: +55%, Tương khắc: +70 |
| **Cấp 2** | Lv.41 | 450 | 17.50s | 1 | 150 x 150 | Xác suất gây bỏng: +33% duy trì 4 giây, Tấn công nội: +80%, Hỏa công: +525, Tấn công cơ bản: +60%, Tương khắc: +75 |
| **Cấp 3** | Lv.42 | 465 | 17.50s | 1 | 150 x 150 | Xác suất gây bỏng: +36% duy trì 4 giây, Tấn công nội: +85%, Hỏa công: +650, Tấn công cơ bản: +65%, Tương khắc: +75 |
| **Cấp 4** | Lv.43 | 480 | 17s | 1 | 150 x 150 | Xác suất gây bỏng: +39% duy trì 4 giây, Tấn công nội: +85%, Hỏa công: +775, Tấn công cơ bản: +70%, Tương khắc: +80 |
| **Cấp 5** | Lv.44 | 495 | 17s | 1 | 150 x 150 | Xác suất gây bỏng: +42% duy trì 4 giây, Tấn công nội: +90%, Hỏa công: +900, Tấn công cơ bản: +75%, Tương khắc: +80 |
| **Cấp 6** | Lv.45 | 510 | 16.50s | 1 | 150 x 150 | Xác suất gây bỏng: +45% duy trì 4 giây, Tấn công nội: +90%, Hỏa công: +1025, Tấn công cơ bản: +80%, Tương khắc: +85 |
| **Cấp 7** | Lv.46 | 525 | 16.50s | 1 | 150 x 150 | Xác suất gây bỏng: +48% duy trì 4 giây, Tấn công nội: +95%, Hỏa công: +1150, Tấn công cơ bản: +85%, Tương khắc: +85 |
| **Cấp 8** | Lv.47 | 540 | 16s | 1 | 150 x 150 | Xác suất gây bỏng: +51% duy trì 4 giây, Tấn công nội: +95%, Hỏa công: +1275, Tấn công cơ bản: +90%, Tương khắc: +90 |
| **Cấp 9** | Lv.48 | 555 | 16s | 1 | 150 x 150 | Xác suất gây bỏng: +55% duy trì 4 giây, Tấn công nội: +100%, Hỏa công: +1400, Tấn công cơ bản: +95%, Tương khắc: +90 |
| **Cấp 10** | Lv.49 | 570 | 15.50s | 1 | 150 x 150 | Xác suất gây bỏng: +60% duy trì 4 giây, Tấn công nội: +105%, Hỏa công: +1600, Tấn công cơ bản: +100%, Tương khắc: +95 |
| **Cấp 11** | Lv.50 | 585 | 15.50s | 1 | 150 x 150 | Xác suất gây bỏng: +65% duy trì 4 giây, Tấn công nội: +110%, Hỏa công: +1900, Tấn công cơ bản: +105%, Tương khắc: +95 |
| **Cấp 12** | Lv.51 | 600 | 15s | 1 | 150 x 150 | Xác suất gây bỏng: +70% duy trì 4 giây, Tấn công nội: +120%, Hỏa công: +2200, Tấn công cơ bản: +110%, Tương khắc: +100 |

### Skill ID 17: Chiêu Hijoukai
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tăng khả năng kháng phong đồng thời giúp bản thân nhanh chóng hồi phục khi bị choáng

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +20, Giảm thời gian bị choáng: -0.2 giây, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +35, Giảm thời gian bị choáng: -0.2 giây, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +50, Giảm thời gian bị choáng: -0.3 giây, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +65, Giảm thời gian bị choáng: -0.3 giây, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +80, Giảm thời gian bị choáng: -0.4 giây, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng phong: +100, Giảm thời gian bị choáng: -0.5 giây, Tương khắc: +100 |

### Skill ID 18: Chiêu Choukou shuriken
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phóng ra một Phi Tiêu lớn kèm theo lửa gây sát thương trong một phạm vi nhất định, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 0 | 900ms | 1 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.50 | 98 | 850ms | 2 | 150 x 150 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công nội: +100%, Hỏa công: +200, Tấn công cơ bản: +190%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 100 | 800ms | 2 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +110%, Hỏa công: +240, Tấn công cơ bản: +200%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 102 | 750ms | 2 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +120%, Hỏa công: +280, Tấn công cơ bản: +210%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 104 | 700ms | 2 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +130%, Hỏa công: +320, Tấn công cơ bản: +220%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 106 | 650ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +140%, Hỏa công: +360, Tấn công cơ bản: +230%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 108 | 600ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +150%, Hỏa công: +400, Tấn công cơ bản: +240%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 110 | 550ms | 3 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +160%, Hỏa công: +440, Tấn công cơ bản: +250%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 112 | 500ms | 3 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +170%, Hỏa công: +480, Tấn công cơ bản: +260%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 114 | 450ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +180%, Hỏa công: +520, Tấn công cơ bản: +270%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 116 | 400ms | 4 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +190%, Hỏa công: +560, Tấn công cơ bản: +280%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 118 | 350ms | 4 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +200%, Hỏa công: +600, Tấn công cơ bản: +290%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 120 | 300ms | 4 | 150 x 150 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công nội: +210%, Hỏa công: +650, Tấn công cơ bản: +300%, Tương khắc: +130 |

### Skill ID 56: Chiêu Totogai
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Hấp thu các hỏa chủng trong tự nhiên nâng cao sát thương đánh chí mạng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +5% |
| **Cấp 2** | Lv.61 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +10% |
| **Cấp 3** | Lv.62 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +15% |
| **Cấp 4** | Lv.63 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +20% |
| **Cấp 5** | Lv.64 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +25% |
| **Cấp 6** | Lv.65 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +30% |
| **Cấp 7** | Lv.66 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +35% |
| **Cấp 8** | Lv.67 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +40% |
| **Cấp 9** | Lv.68 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +45% |
| **Cấp 10** | Lv.69 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +50% |
| **Cấp 11** | Lv.70 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +55% |
| **Cấp 12** | Lv.71 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +60% |
| **Cấp 13** | Lv.72 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +70% |
| **Cấp 14** | Lv.73 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +80% |
| **Cấp 15** | Lv.74 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng tấn công khi đánh chí mạng: +100% |

### Skill ID 62: Chiêu Baaningufukiya
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phóng một Cự Ảnh Tiêu hỏa diễm cuồn cuộn với hỏa sát đỉnh cấp về phía kẻ thù.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.70 | 105 | 750ms | 1 | 150 x 150 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công nội: +190%, Hỏa công: +600, Tấn công cơ bản: +330%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 109 | 650ms | 1 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +200%, Hỏa công: +700, Tấn công cơ bản: +350%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 113 | 630ms | 1 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +210%, Hỏa công: +800, Tấn công cơ bản: +370%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 117 | 600ms | 2 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +220%, Hỏa công: +900, Tấn công cơ bản: +390%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 121 | 550ms | 2 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +230%, Hỏa công: +1000, Tấn công cơ bản: +410%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 125 | 530ms | 2 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +240%, Hỏa công: +1100, Tấn công cơ bản: +430%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 129 | 500ms | 3 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +250%, Hỏa công: +1200, Tấn công cơ bản: +450%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 133 | 450ms | 3 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +260%, Hỏa công: +1300, Tấn công cơ bản: +470%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 137 | 450ms | 3 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +270%, Hỏa công: +1400, Tấn công cơ bản: +490%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 141 | 450ms | 4 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +280%, Hỏa công: +1500, Tấn công cơ bản: +510%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 145 | 400ms | 4 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +290%, Hỏa công: +1600, Tấn công cơ bản: +530%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 149 | 300ms | 4 | 150 x 150 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công nội: +300%, Hỏa công: +1700, Tấn công cơ bản: +550%, Tương khắc: +150 |

### Skill ID 68: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 78: Chiêu Hibashiri
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phóng một phi tiêu lửa tạo ra một vụ nổ lớn tiêu diệt kẻ địch.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.80 | 160 | 500ms | 2 | 150 x 150 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công nội: +230%, Hỏa công: +1100, Tấn công cơ bản: +460%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 163 | 490ms | 2 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +240%, Hỏa công: +1200, Tấn công cơ bản: +470%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 166 | 480ms | 2 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +250%, Hỏa công: +1300, Tấn công cơ bản: +480%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 169 | 470ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +260%, Hỏa công: +1400, Tấn công cơ bản: +490%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 172 | 460ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +270%, Hỏa công: +1500, Tấn công cơ bản: +500%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 175 | 450ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +280%, Hỏa công: +1600, Tấn công cơ bản: +510%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 178 | 440ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +290%, Hỏa công: +1700, Tấn công cơ bản: +520%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 181 | 430ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +300%, Hỏa công: +1800, Tấn công cơ bản: +530%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 184 | 420ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +310%, Hỏa công: +1900, Tấn công cơ bản: +540%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 187 | 400ms | 5 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +320%, Hỏa công: +2000, Tấn công cơ bản: +550%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 190 | 380ms | 5 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +330%, Hỏa công: +2100, Tấn công cơ bản: +570%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 193 | 350ms | 5 | 150 x 150 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công nội: +340%, Hỏa công: +2200, Tấn công cơ bản: +600%, Tương khắc: +180 |

### Skill ID 83: Chiêu Tsumabeni
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tiêu thuật Vạn Lý Phi Tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.100 | 177 | 500ms | 3 | 150 x 150 | Xác xuất gây bỏng: +3% duy trì 2 giây, Tấn công nội: +270%, Hỏa công: +1500, Tấn công cơ bản: +440%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 180 | 490ms | 3 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +275%, Hỏa công: +1600, Tấn công cơ bản: +450%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 183 | 480ms | 3 | 150 x 150 | Xác xuất gây bỏng: +4% duy trì 2 giây, Tấn công nội: +280%, Hỏa công: +1700, Tấn công cơ bản: +460%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 186 | 470ms | 3 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +285%, Hỏa công: +1800, Tấn công cơ bản: +470%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 189 | 460ms | 4 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +295%, Hỏa công: +2000, Tấn công cơ bản: +490%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 192 | 450ms | 4 | 150 x 150 | Xác xuất gây bỏng: +5% duy trì 2 giây, Tấn công nội: +305%, Hỏa công: +2200, Tấn công cơ bản: +510%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 195 | 440ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +315%, Hỏa công: +2400, Tấn công cơ bản: +530%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 198 | 430ms | 4 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +325%, Hỏa công: +2600, Tấn công cơ bản: +550%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 201 | 420ms | 5 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +340%, Hỏa công: +2900, Tấn công cơ bản: +580%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 204 | 400ms | 5 | 150 x 150 | Xác xuất gây bỏng: +6% duy trì 2 giây, Tấn công nội: +355%, Hỏa công: +3200, Tấn công cơ bản: +610%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 207 | 380ms | 5 | 150 x 150 | Xác xuất gây bỏng: +7% duy trì 2 giây, Tấn công nội: +370%, Hỏa công: +3500, Tấn công cơ bản: +640%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 210 | 350ms | 6 | 150 x 150 | Xác xuất gây bỏng: +8% duy trì 2 giây, Tấn công nội: +400%, Hỏa công: +4000, Tấn công cơ bản: +680%, Tương khắc: +200 |

### Skill ID 86: Tiêu Hoả Phi Long
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Bí thuật chuyển hóa Chakra thành chân hỏa - Phóng ra những lam hỏa tiêu cực mạnh về phía mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất gây bỏng: +11% duy trì 2 giây, Tấn công nội: +470%, Hỏa công: +5000, Tấn công cơ bản: +750%, Tương khắc: +300 |

---

## 4. PHÁI 3: NINJA KUNAI (ĐOẢN ĐAO - HỆ BĂNG)

* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Cận chiến / Ám sát / Ngoại công (Sức Khỏe)
* **Đặc trưng**: Đột kích chớp nhoáng, khống chế làm chậm và đóng băng kẻ địch, khả năng né tránh vô địch.

### Skill ID 19: Chiêu Yokobatan
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Kunai nhập môn - tấn công gây sát thương lên một mục tiêu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 600ms | 1 | 35 x 34 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.10 | 1 | 500ms | 1 | 35 x 34 | Tấn công ngoại: +5%, Băng sát: +10, Tấn công cơ bản: +100%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 2 | 500ms | 1 | 35 x 34 | Tấn công ngoại: +10%, Băng sát: +20, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 3 | 500ms | 1 | 35 x 34 | Tấn công ngoại: +15%, Băng sát: +30, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 4 | 450ms | 1 | 35 x 34 | Tấn công ngoại: +20%, Băng sát: +40, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 5 | 450ms | 1 | 35 x 34 | Tấn công ngoại: +25%, Băng sát: +50, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 6 | 400ms | 1 | 35 x 34 | Tấn công ngoại: +30%, Băng sát: +60, Tấn công cơ bản: +100%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 7 | 400ms | 1 | 35 x 34 | Tấn công ngoại: +35%, Băng sát: +70, Tấn công cơ bản: +100%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 8 | 350ms | 1 | 35 x 34 | Tấn công ngoại: +40%, Băng sát: +80, Tấn công cơ bản: +100%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 9 | 350ms | 1 | 35 x 34 | Tấn công ngoại: +45%, Băng sát: +90, Tấn công cơ bản: +100%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 10 | 300ms | 1 | 35 x 34 | Tấn công ngoại: +50%, Băng sát: +100, Tấn công cơ bản: +100%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 11 | 300ms | 1 | 35 x 34 | Tấn công ngoại: +55%, Băng sát: +110, Tấn công cơ bản: +100%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 12 | 250ms | 1 | 35 x 34 | Tấn công ngoại: +60%, Băng sát: +120, Tấn công cơ bản: +100%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 20: Chiêu Akaiame
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao khả năng tấn công độ chính xác và tốc độ di chuyển

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +20, Chính xác: +50, Băng sát ngoại: +45, Tốc độ di chuyển: +35 |
| **Cấp 2** | Lv.16 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +24, Chính xác: +60, Băng sát ngoại: +60, Tốc độ di chuyển: +40 |
| **Cấp 3** | Lv.17 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +28, Chính xác: +70, Băng sát ngoại: +75, Tốc độ di chuyển: +45 |
| **Cấp 4** | Lv.18 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +32, Chính xác: +80, Băng sát ngoại: +90, Tốc độ di chuyển: +50 |
| **Cấp 5** | Lv.19 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +36, Chính xác: +90, Băng sát ngoại: +105, Tốc độ di chuyển: +55 |
| **Cấp 6** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +40, Chính xác: +100, Băng sát ngoại: +130, Tốc độ di chuyển: +60 |

### Skill ID 21: Chiêu Mizuibatan
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Chém liên tục vào mục tiêu gây sát thương lên mục tiêu đó

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 1s | 1 | 38 x 33 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.20 | 31 | 750ms | 1 | 38 x 33 | Tấn công ngoại: +50%, Băng sát: +190, Tấn công cơ bản: +150%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 40: +12% |
| **Cấp 2** | Lv.21 | 32 | 650ms | 1 | 38 x 33 | Tấn công ngoại: +55%, Băng sát: +200, Tấn công cơ bản: +150%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +18% |
| **Cấp 3** | Lv.22 | 33 | 650ms | 1 | 38 x 33 | Tấn công ngoại: +60%, Băng sát: +210, Tấn công cơ bản: +150%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +24% |
| **Cấp 4** | Lv.23 | 34 | 550ms | 1 | 38 x 33 | Tấn công ngoại: +65%, Băng sát: +220, Tấn công cơ bản: +150%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +31% |
| **Cấp 5** | Lv.24 | 35 | 550ms | 1 | 38 x 33 | Tấn công ngoại: +70%, Băng sát: +230, Tấn công cơ bản: +150%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +38% |
| **Cấp 6** | Lv.25 | 36 | 450ms | 1 | 38 x 33 | Tấn công ngoại: +75%, Băng sát: +240, Tấn công cơ bản: +150%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +45% |
| **Cấp 7** | Lv.26 | 37 | 450ms | 1 | 38 x 33 | Tấn công ngoại: +80%, Băng sát: +250, Tấn công cơ bản: +150%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +52% |
| **Cấp 8** | Lv.27 | 38 | 400ms | 1 | 38 x 33 | Tấn công ngoại: +85%, Băng sát: +260, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +59% |
| **Cấp 9** | Lv.28 | 39 | 400ms | 1 | 38 x 33 | Tấn công ngoại: +90%, Băng sát: +270, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +66% |
| **Cấp 10** | Lv.29 | 40 | 350ms | 1 | 38 x 33 | Tấn công ngoại: +100%, Băng sát: +280, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +73% |
| **Cấp 11** | Lv.30 | 41 | 300ms | 1 | 38 x 33 | Tấn công ngoại: +105%, Băng sát: +290, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +81% |
| **Cấp 12** | Lv.31 | 42 | 250ms | 1 | 38 x 33 | Tấn công ngoại: +110%, Băng sát: +300, Tấn công cơ bản: +150%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 40: +90% |

### Skill ID 22: Chiêu Aoiame
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tạo ra khúc gỗ thế thân chịu mọi sát thương thay cho bản thân

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 35 | 12s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +5 giây |
| **Cấp 2** | Lv.26 | 35 | 18s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +7 giây |
| **Cấp 3** | Lv.27 | 35 | 21s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +9 giây |
| **Cấp 4** | Lv.28 | 35 | 23s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +11 giây |
| **Cấp 5** | Lv.29 | 35 | 25s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +13 giây |
| **Cấp 6** | Lv.30 | 35 | 27s | 1 | 150 x 90 | Tạo vật thế thân: duy trì +15 giây |

### Skill ID 23: Chiêu Uzubatan
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Kunai kết hợp băng thuật tạo ra vòng sát thương tác động lên các mục tiêu cạnh đó, tối đa 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 42 x 34 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.30 | 46 | 750ms | 2 | 42 x 34 | Tấn công ngoại: +50%, Băng sát: +210, Tấn công cơ bản: +160%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 47 | 700ms | 2 | 42 x 34 | Tấn công ngoại: +55%, Băng sát: +220, Tấn công cơ bản: +160%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 48 | 700ms | 2 | 42 x 34 | Tấn công ngoại: +60%, Băng sát: +230, Tấn công cơ bản: +160%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 49 | 650ms | 2 | 42 x 34 | Tấn công ngoại: +65%, Băng sát: +240, Tấn công cơ bản: +160%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 50 | 600ms | 2 | 42 x 34 | Tấn công ngoại: +70%, Băng sát: +250, Tấn công cơ bản: +160%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 51 | 550ms | 3 | 42 x 34 | Tấn công ngoại: +80%, Băng sát: +240, Tấn công cơ bản: +160%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 52 | 500ms | 3 | 42 x 34 | Tấn công ngoại: +85%, Băng sát: +260, Tấn công cơ bản: +160%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 53 | 450ms | 3 | 42 x 34 | Tấn công ngoại: +90%, Băng sát: +280, Tấn công cơ bản: +160%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 54 | 400ms | 3 | 42 x 34 | Tấn công ngoại: +95%, Băng sát: +300, Tấn công cơ bản: +160%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 55 | 350ms | 3 | 42 x 34 | Tấn công ngoại: +100%, Băng sát: +320, Tấn công cơ bản: +160%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 56 | 300ms | 3 | 42 x 34 | Tấn công ngoại: +105%, Băng sát: +340, Tấn công cơ bản: +160%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 57 | 250ms | 3 | 42 x 34 | Tấn công ngoại: +110%, Băng sát: +360, Tấn công cơ bản: +160%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 24: Chiêu Hibikou
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng cầm băng thuật trói chân kẻ địch trong một khoảng thời gian nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 60s | 1 | 60 x 60 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.35 | 40 | 55s | 1 | 70 x 70 | Làm cho đối phương không thể di chuyển: duy trì +3 giây |
| **Cấp 2** | Lv.36 | 50 | 50s | 1 | 80 x 80 | Làm cho đối phương không thể di chuyển: duy trì +4 giây |
| **Cấp 3** | Lv.37 | 60 | 45s | 1 | 90 x 90 | Làm cho đối phương không thể di chuyển: duy trì +5 giây |
| **Cấp 4** | Lv.38 | 70 | 40s | 1 | 100 x 100 | Làm cho đối phương không thể di chuyển: duy trì +6 giây |
| **Cấp 5** | Lv.39 | 80 | 35s | 1 | 110 x 110 | Làm cho đối phương không thể di chuyển: duy trì +7 giây |
| **Cấp 6** | Lv.40 | 90 | 30s | 1 | 120 x 120 | Làm cho đối phương không thể di chuyển: duy trì +9 giây |

### Skill ID 25: Chiêu Kogoeru
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tạo 2 vòng băng quấn quanh mục tiêu, gây ra sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 19s | 1 | 51 x 34 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.40 | 160 | 18s | 1 | 51 x 34 | Xác suất đóng băng: +40% duy trì 3 giây, Tấn công ngoại: +80%, Băng sát: +600, Tấn công cơ bản: +55%, Tương khắc: +70 |
| **Cấp 2** | Lv.41 | 170 | 17.50s | 1 | 51 x 34 | Xác suất đóng băng: +45% duy trì 3 giây, Tấn công ngoại: +80%, Băng sát: +775, Tấn công cơ bản: +60%, Tương khắc: +75 |
| **Cấp 3** | Lv.42 | 180 | 17.50s | 1 | 51 x 34 | Xác suất đóng băng: +50% duy trì 3 giây, Tấn công ngoại: +85%, Băng sát: +950, Tấn công cơ bản: +65%, Tương khắc: +75 |
| **Cấp 4** | Lv.43 | 190 | 17s | 1 | 51 x 34 | Xác suất đóng băng: +55% duy trì 3 giây, Tấn công ngoại: +85%, Băng sát: +1125, Tấn công cơ bản: +70%, Tương khắc: +80 |
| **Cấp 5** | Lv.44 | 200 | 17s | 1 | 51 x 34 | Xác suất đóng băng: +60% duy trì 3 giây, Tấn công ngoại: +90%, Băng sát: +1300, Tấn công cơ bản: +75%, Tương khắc: +80 |
| **Cấp 6** | Lv.45 | 210 | 16.50s | 1 | 51 x 34 | Xác suất đóng băng: +65% duy trì 3 giây, Tấn công ngoại: +90%, Băng sát: +1475, Tấn công cơ bản: +80%, Tương khắc: +85 |
| **Cấp 7** | Lv.46 | 220 | 16.50s | 1 | 51 x 34 | Xác suất đóng băng: +70% duy trì 3 giây, Tấn công ngoại: +95%, Băng sát: +1650, Tấn công cơ bản: +85%, Tương khắc: +85 |
| **Cấp 8** | Lv.47 | 230 | 16s | 1 | 51 x 34 | Xác suất đóng băng: +75% duy trì 3 giây, Tấn công ngoại: +95%, Băng sát: +1825, Tấn công cơ bản: +90%, Tương khắc: +90 |
| **Cấp 9** | Lv.48 | 240 | 16s | 1 | 51 x 34 | Xác suất đóng băng: +80% duy trì 3 giây, Tấn công ngoại: +100%, Băng sát: +2000, Tấn công cơ bản: +95%, Tương khắc: +90 |
| **Cấp 10** | Lv.49 | 250 | 15.50s | 1 | 51 x 34 | Xác suất đóng băng: +80% duy trì 3 giây, Tấn công ngoại: +110%, Băng sát: +2175, Tấn công cơ bản: +100%, Tương khắc: +95 |
| **Cấp 11** | Lv.50 | 260 | 15.50s | 1 | 51 x 34 | Xác suất đóng băng: +80% duy trì 3 giây, Tấn công ngoại: +120%, Băng sát: +2350, Tấn công cơ bản: +105%, Tương khắc: +95 |
| **Cấp 12** | Lv.51 | 270 | 15s | 1 | 51 x 34 | Xác suất đóng băng: +80% duy trì 3 giây, Tấn công ngoại: +130%, Băng sát: +2525, Tấn công cơ bản: +110%, Tương khắc: +100 |

### Skill ID 26: Chiêu Jotente
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tăng khả năng kháng hỏa đồng thời giúp bản thân nhanh chóng hồi phục khi bị bỏng

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +20, Giảm thời gian bị bỏng: -0.2 giây, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +35, Giảm thời gian bị bỏng: -0.3 giây, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +50, Giảm thời gian bị bỏng: -0.5 giây, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +65, Giảm thời gian bị bỏng: -0.6 giây, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +80, Giảm thời gian bị bỏng: -0.8 giây, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +100, Giảm thời gian bị bỏng: -0.10 giây, Tương khắc: +100 |

### Skill ID 27: Chiêu Choukoukogo
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tạo ra vòng băng cuộn về phía trước gây sát thương trong một phạm vi nhất định, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 63 | 900ms | 1 | 46 x 46 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.50 | 73 | 850ms | 2 | 46 x 46 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +90%, Băng sát: +380, Tấn công cơ bản: +240%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 75 | 800ms | 2 | 46 x 46 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +100%, Băng sát: +400, Tấn công cơ bản: +250%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 77 | 750ms | 2 | 46 x 46 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +110%, Băng sát: +420, Tấn công cơ bản: +260%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 79 | 700ms | 2 | 46 x 46 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +120%, Băng sát: +440, Tấn công cơ bản: +270%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 81 | 650ms | 3 | 46 x 46 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +130%, Băng sát: +460, Tấn công cơ bản: +280%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 83 | 600ms | 3 | 46 x 46 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +140%, Băng sát: +480, Tấn công cơ bản: +290%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 85 | 550ms | 3 | 46 x 46 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +150%, Băng sát: +500, Tấn công cơ bản: +300%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 87 | 500ms | 3 | 46 x 46 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +160%, Băng sát: +520, Tấn công cơ bản: +310%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 89 | 450ms | 4 | 46 x 46 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +170%, Băng sát: +540, Tấn công cơ bản: +320%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 91 | 400ms | 4 | 46 x 46 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +180%, Băng sát: +560, Tấn công cơ bản: +330%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 93 | 350ms | 4 | 46 x 46 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +190%, Băng sát: +580, Tấn công cơ bản: +340%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 95 | 300ms | 4 | 46 x 46 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +200%, Băng sát: +600, Tấn công cơ bản: +350%, Tương khắc: +130 |

### Skill ID 57: Chiêu Kitsukemaguma
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Khi bị tấn công, cơ thể sẽ tự động kêu gọi băng nguyên tố đóng băng kẻ địch.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 3% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 100 giây |
| **Cấp 2** | Lv.61 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 4% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 200 giây |
| **Cấp 3** | Lv.62 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 5% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 300 giây |
| **Cấp 4** | Lv.63 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 6% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 400 giây |
| **Cấp 5** | Lv.64 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 7% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 500 giây |
| **Cấp 6** | Lv.65 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 8% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 600 giây |
| **Cấp 7** | Lv.66 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 9% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 700 giây |
| **Cấp 8** | Lv.67 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 10% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 800 giây |
| **Cấp 9** | Lv.68 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 11% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 900 giây |
| **Cấp 10** | Lv.69 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 12% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 1000 giây |
| **Cấp 11** | Lv.70 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 13% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 1100 giây |
| **Cấp 12** | Lv.71 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 14% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 1200 giây |
| **Cấp 13** | Lv.72 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 16% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 1400 giây |
| **Cấp 14** | Lv.73 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 18% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 1700 giây |
| **Cấp 15** | Lv.74 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tỉ lệ 20% đóng băng mục tiêu khi bị tấn công, Đóng băng mục tiêu trong: 2000 giây |

### Skill ID 63: Chiêu Furiizukatto
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tạo ra hai vòng băng từ trong thân kẻ địch gây ra sát thương cực lớn đồng thời có tỉ lệ khiến kẻ địch bị đóng băng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 47 x 45 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.70 | 95 | 700ms | 1 | 47 x 45 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +190%, Băng sát: +300, Tấn công cơ bản: +240%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 98 | 700ms | 1 | 47 x 45 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +200%, Băng sát: +400, Tấn công cơ bản: +270%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 101 | 650ms | 1 | 47 x 45 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +210%, Băng sát: +500, Tấn công cơ bản: +300%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 104 | 650ms | 2 | 47 x 45 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +220%, Băng sát: +600, Tấn công cơ bản: +330%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 107 | 600ms | 2 | 47 x 45 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +230%, Băng sát: +700, Tấn công cơ bản: +360%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 110 | 600ms | 2 | 47 x 45 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +240%, Băng sát: +800, Tấn công cơ bản: +390%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 113 | 550ms | 3 | 47 x 45 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +250%, Băng sát: +900, Tấn công cơ bản: +420%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 116 | 550ms | 3 | 47 x 45 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +260%, Băng sát: +1000, Tấn công cơ bản: +450%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 119 | 500ms | 3 | 47 x 45 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +270%, Băng sát: +1100, Tấn công cơ bản: +480%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 122 | 450ms | 4 | 47 x 45 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +280%, Băng sát: +1200, Tấn công cơ bản: +510%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 125 | 400ms | 4 | 47 x 45 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +290%, Băng sát: +1300, Tấn công cơ bản: +540%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 128 | 350ms | 4 | 47 x 45 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +300%, Băng sát: +1400, Tấn công cơ bản: +570%, Tương khắc: +150 |

### Skill ID 69: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 2000 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 75: Chiêu Saihyoken
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Vòng băng sẽ triệu hồi thêm những cục băng lớn rơi trúng kẻ thù.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.80 | 100 | 450ms | 2 | 90 x 50 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +270%, Băng sát: +500, Tấn công cơ bản: +400%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 102 | 440ms | 2 | 90 x 50 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +280%, Băng sát: +600, Tấn công cơ bản: +410%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 104 | 430ms | 2 | 90 x 50 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +290%, Băng sát: +700, Tấn công cơ bản: +420%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 106 | 420ms | 3 | 90 x 50 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +300%, Băng sát: +800, Tấn công cơ bản: +430%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 108 | 410ms | 3 | 90 x 50 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +310%, Băng sát: +1000, Tấn công cơ bản: +450%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 110 | 400ms | 3 | 90 x 50 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +320%, Băng sát: +1200, Tấn công cơ bản: +470%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 112 | 390ms | 4 | 90 x 50 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +330%, Băng sát: +1400, Tấn công cơ bản: +490%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 114 | 380ms | 4 | 90 x 50 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +340%, Băng sát: +1600, Tấn công cơ bản: +510%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 116 | 370ms | 4 | 90 x 50 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +350%, Băng sát: +1800, Tấn công cơ bản: +530%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 118 | 350ms | 5 | 90 x 50 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +360%, Băng sát: +2000, Tấn công cơ bản: +560%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 120 | 330ms | 5 | 90 x 50 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +370%, Băng sát: +2200, Tấn công cơ bản: +600%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 122 | 300ms | 5 | 90 x 50 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công ngoại: +380%, Băng sát: +2500, Tấn công cơ bản: +650%, Tương khắc: +180 |

### Skill ID 81: Chiêu Shabondama
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Băng thuật Hổ Khiếu Sơn Hà

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.100 | 118 | 450ms | 3 | 90 x 46 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +280%, Băng sát: +1000, Tấn công cơ bản: +480%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 120 | 440ms | 3 | 90 x 46 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +290%, Băng sát: +1100, Tấn công cơ bản: +490%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 122 | 430ms | 3 | 90 x 46 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công ngoại: +300%, Băng sát: +1200, Tấn công cơ bản: +500%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 124 | 420ms | 3 | 90 x 46 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +310%, Băng sát: +1300, Tấn công cơ bản: +510%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 126 | 410ms | 4 | 90 x 46 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công ngoại: +325%, Băng sát: +1600, Tấn công cơ bản: +530%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 128 | 400ms | 4 | 90 x 46 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +340%, Băng sát: +1900, Tấn công cơ bản: +550%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 130 | 390ms | 4 | 90 x 46 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công ngoại: +355%, Băng sát: +2200, Tấn công cơ bản: +570%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 132 | 380ms | 4 | 90 x 46 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +370%, Băng sát: +2500, Tấn công cơ bản: +590%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 134 | 370ms | 5 | 90 x 46 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công ngoại: +390%, Băng sát: +3000, Tấn công cơ bản: +620%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 136 | 350ms | 5 | 90 x 46 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +410%, Băng sát: +3500, Tấn công cơ bản: +650%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 138 | 330ms | 5 | 90 x 46 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công ngoại: +430%, Băng sát: +4000, Tấn công cơ bản: +680%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 140 | 300ms | 6 | 90 x 46 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công ngoại: +460%, Băng sát: +5000, Tấn công cơ bản: +730%, Tương khắc: +200 |

### Skill ID 87: Băng Hổ Bạch Long
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Băng thuật tối thượng - Tạo ra hàng loạt vụ nổ năng lượng nhắm thẳng vào mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất đóng băng: +11% duy trì 1.5 giây, Tấn công ngoại: +550%, Băng sát: +5500, Tấn công cơ bản: +770%, Tương khắc: +300 |

---

## 5. PHÁI 4: NINJA CUNG (CUNG TÊN - HỆ BĂNG)

* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Viễn chiến / Tầm xa / Nội công (Chakra)
* **Đặc trưng**: Tầm bắn xa nhất trò chơi, chính xác tuyệt đối, khóa chân mục tiêu từ ngoài tầm nhìn.

### Skill ID 28: Chiêu Uzusa
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Cung thuật nhập môn - bắn một mũi tên gây sát thương lên một mục tiêu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 0s (Bị động) | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.10 | 2 | 750ms | 1 | 140 x 140 | Tấn công nội: +5%, Băng sát: +10, Tấn công cơ bản: +110%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 4 | 650ms | 1 | 140 x 140 | Tấn công nội: +10%, Băng sát: +20, Tấn công cơ bản: +110%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 6 | 630ms | 1 | 140 x 140 | Tấn công nội: +15%, Băng sát: +30, Tấn công cơ bản: +110%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 8 | 600ms | 1 | 140 x 140 | Tấn công nội: +20%, Băng sát: +40, Tấn công cơ bản: +110%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 10 | 550ms | 1 | 140 x 140 | Tấn công nội: +25%, Băng sát: +50, Tấn công cơ bản: +110%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 12 | 530ms | 1 | 140 x 140 | Tấn công nội: +30%, Băng sát: +60, Tấn công cơ bản: +110%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 14 | 500ms | 1 | 140 x 140 | Tấn công nội: +35%, Băng sát: +70, Tấn công cơ bản: +110%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 16 | 450ms | 1 | 140 x 140 | Tấn công nội: +40%, Băng sát: +80, Tấn công cơ bản: +110%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 18 | 450ms | 1 | 140 x 140 | Tấn công nội: +45%, Băng sát: +90, Tấn công cơ bản: +110%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 20 | 450ms | 1 | 140 x 140 | Tấn công nội: +50%, Băng sát: +100, Tấn công cơ bản: +110%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 22 | 400ms | 1 | 140 x 140 | Tấn công nội: +55%, Băng sát: +110, Tấn công cơ bản: +110%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 25 | 300ms | 1 | 140 x 140 | Tấn công nội: +60%, Băng sát: +120, Tấn công cơ bản: +110%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 29: Chiêu Washihitomi
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao sức tấn công và độ chính xác bản thân

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +20, Băng sát nội: +60, Chính xác: +20, Tấn công nội: +5% |
| **Cấp 2** | Lv.16 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +30, Băng sát nội: +70, Chính xác: +40, Tấn công nội: +10% |
| **Cấp 3** | Lv.17 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +40, Băng sát nội: +80, Chính xác: +60, Tấn công nội: +15% |
| **Cấp 4** | Lv.18 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +50, Băng sát nội: +90, Chính xác: +80, Tấn công nội: +20% |
| **Cấp 5** | Lv.19 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +60, Băng sát nội: +100, Chính xác: +100, Tấn công nội: +25% |
| **Cấp 6** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +70, Băng sát nội: +110, Chính xác: +120, Tấn công nội: +30% |

### Skill ID 30: Chiêu Kinkinsa
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Bắn ra một mũi tên băng vào mục tiêu gây sát thương ngay lập tức

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 1s | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.20 | 32 | 1s | 1 | 140 x 140 | Tấn công nội: +50%, Băng sát: +140, Tấn công cơ bản: +120%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 40: +12% |
| **Cấp 2** | Lv.21 | 34 | 1s | 1 | 140 x 140 | Tấn công nội: +55%, Băng sát: +150, Tấn công cơ bản: +120%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +18% |
| **Cấp 3** | Lv.22 | 36 | 1s | 1 | 140 x 140 | Tấn công nội: +60%, Băng sát: +160, Tấn công cơ bản: +120%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +24% |
| **Cấp 4** | Lv.23 | 38 | 1s | 1 | 140 x 140 | Tấn công nội: +65%, Băng sát: +170, Tấn công cơ bản: +120%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +31% |
| **Cấp 5** | Lv.24 | 40 | 1s | 1 | 140 x 140 | Tấn công nội: +70%, Băng sát: +180, Tấn công cơ bản: +120%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +38% |
| **Cấp 6** | Lv.25 | 42 | 900ms | 1 | 140 x 140 | Tấn công nội: +75%, Băng sát: +190, Tấn công cơ bản: +120%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +45% |
| **Cấp 7** | Lv.26 | 44 | 800ms | 1 | 140 x 140 | Tấn công nội: +80%, Băng sát: +200, Tấn công cơ bản: +120%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +52% |
| **Cấp 8** | Lv.27 | 46 | 700ms | 1 | 140 x 140 | Tấn công nội: +85%, Băng sát: +210, Tấn công cơ bản: +120%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +59% |
| **Cấp 9** | Lv.28 | 48 | 600ms | 1 | 140 x 140 | Tấn công nội: +90%, Băng sát: +220, Tấn công cơ bản: +120%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +66% |
| **Cấp 10** | Lv.29 | 50 | 500ms | 1 | 140 x 140 | Tấn công nội: +100%, Băng sát: +230, Tấn công cơ bản: +120%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +73% |
| **Cấp 11** | Lv.30 | 52 | 500ms | 1 | 140 x 140 | Tấn công nội: +110%, Băng sát: +240, Tấn công cơ bản: +120%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +81% |
| **Cấp 12** | Lv.31 | 55 | 500ms | 1 | 140 x 140 | Tấn công nội: +120%, Băng sát: +250, Tấn công cơ bản: +120%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 40: +90% |

### Skill ID 31: Chiêu Sogekihei
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tiêu tốn MP giảm sức sát thương của quái vật lên bản thân

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 100 | 120s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +20% duy trì 90 giây |
| **Cấp 2** | Lv.26 | 110 | 100s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +30% duy trì 90 giây |
| **Cấp 3** | Lv.27 | 120 | 80s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +40% duy trì 90 giây |
| **Cấp 4** | Lv.28 | 130 | 60s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +50% duy trì 90 giây |
| **Cấp 5** | Lv.29 | 140 | 40s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +60% duy trì 90 giây |
| **Cấp 6** | Lv.30 | 150 | 20s | 1 | Bản thân / Ẩn | Khi Mp lớn hơn 10%, dùng MP hút sát thương: +70% duy trì 90 giây |

### Skill ID 32: Chiêu Nikinkinsa
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Bắn ra 2 mũi tên băng gây sát thương trong một phạm vi nhất định, sát thương tối đa 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.30 | 58 | 750ms | 2 | 140 x 140 | Tấn công nội: +50%, Băng sát: +150, Tấn công cơ bản: +120%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 60 | 650ms | 2 | 140 x 140 | Tấn công nội: +55%, Băng sát: +160, Tấn công cơ bản: +120%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 62 | 630ms | 2 | 140 x 140 | Tấn công nội: +60%, Băng sát: +170, Tấn công cơ bản: +120%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 64 | 600ms | 2 | 140 x 140 | Tấn công nội: +65%, Băng sát: +180, Tấn công cơ bản: +120%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 66 | 550ms | 2 | 140 x 140 | Tấn công nội: +70%, Băng sát: +190, Tấn công cơ bản: +120%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 68 | 530ms | 3 | 140 x 140 | Tấn công nội: +80%, Băng sát: +200, Tấn công cơ bản: +120%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 70 | 500ms | 3 | 140 x 140 | Tấn công nội: +85%, Băng sát: +210, Tấn công cơ bản: +120%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 74 | 450ms | 3 | 140 x 140 | Tấn công nội: +90%, Băng sát: +220, Tấn công cơ bản: +120%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 78 | 450ms | 3 | 140 x 140 | Tấn công nội: +95%, Băng sát: +240, Tấn công cơ bản: +120%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 82 | 450ms | 3 | 140 x 140 | Tấn công nội: +100%, Băng sát: +260, Tấn công cơ bản: +120%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 86 | 400ms | 3 | 140 x 140 | Tấn công nội: +105%, Băng sát: +280, Tấn công cơ bản: +120%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 90 | 300ms | 3 | 140 x 140 | Tấn công nội: +110%, Băng sát: +300, Tấn công cơ bản: +120%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 33: Chiêu Joutenhitomi
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng băng tâm thuật gia tăng mạnh mẽ sức tấn công nhưng bản thân phải chịu tổn thương

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.35 | 100 | 45s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +100% duy trì 5 giây |
| **Cấp 2** | Lv.36 | 120 | 40s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +140% duy trì 5 giây |
| **Cấp 3** | Lv.37 | 140 | 35s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +180% duy trì 5 giây |
| **Cấp 4** | Lv.38 | 160 | 30s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +220% duy trì 5 giây |
| **Cấp 5** | Lv.39 | 180 | 25s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +260% duy trì 5 giây |
| **Cấp 6** | Lv.40 | 200 | 20s | 1 | Bản thân / Ẩn | Mỗi nữa giây giảm trừ 3% Hp đồng thời tăng tấn công cơ bản: +300% duy trì 5 giây |

### Skill ID 34: Chiêu Kogosa
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Băng thuật cô đọng hơi nước xung quanh tạo ra mũi tên băng bắn vào kẻ địch, gây sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 21s | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.40 | 420 | 20s | 1 | 140 x 140 | Xác suất đóng băng: +30% duy trì 3 giây, Tấn công nội: +80%, Băng sát: +550, Tấn công cơ bản: +55%, Tương khắc: +70 |
| **Cấp 2** | Lv.41 | 440 | 19.50s | 1 | 140 x 140 | Xác suất đóng băng: +33% duy trì 3 giây, Tấn công nội: +80%, Băng sát: +700, Tấn công cơ bản: +60%, Tương khắc: +75 |
| **Cấp 3** | Lv.42 | 460 | 19.50s | 1 | 140 x 140 | Xác suất đóng băng: +36% duy trì 3 giây, Tấn công nội: +85%, Băng sát: +850, Tấn công cơ bản: +65%, Tương khắc: +75 |
| **Cấp 4** | Lv.43 | 480 | 19s | 1 | 140 x 140 | Xác suất đóng băng: +39% duy trì 3 giây, Tấn công nội: +85%, Băng sát: +1000, Tấn công cơ bản: +70%, Tương khắc: +80 |
| **Cấp 5** | Lv.44 | 500 | 19s | 1 | 140 x 140 | Xác suất đóng băng: +42% duy trì 3 giây, Tấn công nội: +90%, Băng sát: +1150, Tấn công cơ bản: +75%, Tương khắc: +80 |
| **Cấp 6** | Lv.45 | 520 | 18.50s | 1 | 140 x 140 | Xác suất đóng băng: +45% duy trì 3 giây, Tấn công nội: +90%, Băng sát: +1300, Tấn công cơ bản: +80%, Tương khắc: +85 |
| **Cấp 7** | Lv.46 | 540 | 18.50s | 1 | 140 x 140 | Xác suất đóng băng: +48% duy trì 3 giây, Tấn công nội: +95%, Băng sát: +1450, Tấn công cơ bản: +85%, Tương khắc: +85 |
| **Cấp 8** | Lv.47 | 560 | 18s | 1 | 140 x 140 | Xác suất đóng băng: +51% duy trì 3 giây, Tấn công nội: +95%, Băng sát: +1600, Tấn công cơ bản: +90%, Tương khắc: +90 |
| **Cấp 9** | Lv.48 | 570 | 18s | 1 | 140 x 140 | Xác suất đóng băng: +55% duy trì 3 giây, Tấn công nội: +100%, Băng sát: +1750, Tấn công cơ bản: +95%, Tương khắc: +90 |
| **Cấp 10** | Lv.49 | 580 | 17.50s | 1 | 140 x 140 | Xác suất đóng băng: +60% duy trì 3 giây, Tấn công nội: +105%, Băng sát: +1900, Tấn công cơ bản: +100%, Tương khắc: +95 |
| **Cấp 11** | Lv.50 | 590 | 17.50s | 1 | 140 x 140 | Xác suất đóng băng: +65% duy trì 3 giây, Tấn công nội: +110%, Băng sát: +2100, Tấn công cơ bản: +105%, Tương khắc: +95 |
| **Cấp 12** | Lv.51 | 600 | 17s | 1 | 140 x 140 | Xác suất đóng băng: +70% duy trì 3 giây, Tấn công nội: +120%, Băng sát: +2300, Tấn công cơ bản: +110%, Tương khắc: +100 |

### Skill ID 35: Chiêu Kyshouma
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tăng khả năng kháng hỏa đồng thời giúp bản thân nhanh chóng hồi phục khi bị bỏng

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +20, Giảm thời gian bị bỏng: -0.2 giây, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +35, Giảm thời gian bị bỏng: -0.3 giây, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +50, Giảm thời gian bị bỏng: -0.5 giây, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +65, Giảm thời gian bị bỏng: -0.6 giây, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +80, Giảm thời gian bị bỏng: -0.8 giây, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng hỏa: +100, Giảm thời gian bị bỏng: -0.10 giây, Tương khắc: +100 |

### Skill ID 36: Chiêu Chousoukinkinsa
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Bắn ra 3 mũi tên băng về phía mục tiêu đồng thời gây ra sát thương lên các mục tiêu lân cận, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 0 | 1.10s | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.50 | 114 | 750ms | 2 | 140 x 140 | Xác xuất đóng băng: +3% duy trì 1.5 giây, Tấn công nội: +165%, Băng sát: +280, Tấn công cơ bản: +270%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 115 | 650ms | 2 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +170%, Băng sát: +300, Tấn công cơ bản: +280%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 116 | 630ms | 2 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +175%, Băng sát: +320, Tấn công cơ bản: +290%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 117 | 600ms | 2 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +180%, Băng sát: +340, Tấn công cơ bản: +300%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 118 | 550ms | 3 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +185%, Băng sát: +360, Tấn công cơ bản: +310%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 119 | 530ms | 3 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +190%, Băng sát: +390, Tấn công cơ bản: +320%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 120 | 500ms | 3 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +195%, Băng sát: +430, Tấn công cơ bản: +330%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 121 | 450ms | 3 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +200%, Băng sát: +460, Tấn công cơ bản: +340%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 122 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +205%, Băng sát: +490, Tấn công cơ bản: +350%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 123 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +210%, Băng sát: +530, Tấn công cơ bản: +360%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 124 | 400ms | 4 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +215%, Băng sát: +570, Tấn công cơ bản: +370%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 125 | 300ms | 4 | 140 x 140 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công nội: +220%, Băng sát: +620, Tấn công cơ bản: +380%, Tương khắc: +130 |

### Skill ID 58: Chiêu Totaaigo
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Triệu tập tất cả nguyên tố băng ngưng tụ thành vòng bảo vệ bản thân trong một thời gian nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 400 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 2000 giây |
| **Cấp 2** | Lv.61 | 420 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 2300 giây |
| **Cấp 3** | Lv.62 | 440 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 2600 giây |
| **Cấp 4** | Lv.63 | 460 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 2900 giây |
| **Cấp 5** | Lv.64 | 480 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 3200 giây |
| **Cấp 6** | Lv.65 | 500 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 3500 giây |
| **Cấp 7** | Lv.66 | 520 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 3800 giây |
| **Cấp 8** | Lv.67 | 540 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 4100 giây |
| **Cấp 9** | Lv.68 | 560 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 4500 giây |
| **Cấp 10** | Lv.69 | 580 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 5000 giây |
| **Cấp 11** | Lv.70 | 600 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 5500 giây |
| **Cấp 12** | Lv.71 | 620 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 6000 giây |
| **Cấp 13** | Lv.72 | 640 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 7000 giây |
| **Cấp 14** | Lv.73 | 660 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 8000 giây |
| **Cấp 15** | Lv.74 | 680 | 50s | 1 | Bản thân / Ẩn | Giảm trừ tất cả các sát thương: duy trì 10000 giây |

### Skill ID 64: Chiêu Furoozunkyuusen
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Bắn hai Cự Băng Tiễn vào kẻ thù gây ra sát thương thủy hệ đồng thời có tỉ lệ khiến kẻ địch bị đóng băng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 140 x 140 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.70 | 120 | 750ms | 1 | 140 x 140 | Xác xuất đóng băng: +3% duy trì 1.5 giây, Tấn công nội: +265%, Băng sát: +620, Tấn công cơ bản: +360%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 124 | 650ms | 1 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +270%, Băng sát: +700, Tấn công cơ bản: +380%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 128 | 630ms | 1 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +275%, Băng sát: +780, Tấn công cơ bản: +400%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 132 | 600ms | 2 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +280%, Băng sát: +860, Tấn công cơ bản: +420%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 136 | 550ms | 2 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +285%, Băng sát: +940, Tấn công cơ bản: +440%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 140 | 530ms | 2 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +290%, Băng sát: +1020, Tấn công cơ bản: +460%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 144 | 500ms | 3 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +295%, Băng sát: +1100, Tấn công cơ bản: +480%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 148 | 450ms | 3 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +300%, Băng sát: +1180, Tấn công cơ bản: +500%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 152 | 450ms | 3 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +305%, Băng sát: +1260, Tấn công cơ bản: +520%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 156 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +310%, Băng sát: +1340, Tấn công cơ bản: +540%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 160 | 400ms | 4 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +315%, Băng sát: +1420, Tấn công cơ bản: +560%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 164 | 300ms | 4 | 140 x 140 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công nội: +320%, Băng sát: +1500, Tấn công cơ bản: +580%, Tương khắc: +150 |

### Skill ID 70: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 2000 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 76: Chiêu Aisu Meiku
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Bắn liên tục những mũi tên băng về phía kẻ thù tạo ra những vòng xoáy băng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.80 | 140 | 750ms | 2 | 140 x 140 | Xác xuất đóng băng: +3% duy trì 1.5 giây, Tấn công nội: +260%, Băng sát: +1200, Tấn công cơ bản: +520%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 143 | 650ms | 2 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +270%, Băng sát: +1300, Tấn công cơ bản: +530%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 146 | 630ms | 2 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +280%, Băng sát: +1400, Tấn công cơ bản: +540%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 149 | 600ms | 3 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +290%, Băng sát: +1500, Tấn công cơ bản: +550%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 152 | 550ms | 3 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +300%, Băng sát: +1600, Tấn công cơ bản: +560%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 155 | 530ms | 3 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +310%, Băng sát: +1700, Tấn công cơ bản: +570%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 158 | 500ms | 4 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +320%, Băng sát: +1800, Tấn công cơ bản: +580%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 161 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +330%, Băng sát: +1900, Tấn công cơ bản: +590%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 164 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +340%, Băng sát: +2000, Tấn công cơ bản: +600%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 167 | 450ms | 5 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +350%, Băng sát: +2200, Tấn công cơ bản: +610%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 170 | 400ms | 5 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +360%, Băng sát: +2500, Tấn công cơ bản: +620%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 173 | 300ms | 5 | 140 x 140 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công nội: +370%, Băng sát: +2800, Tấn công cơ bản: +630%, Tương khắc: +180 |

### Skill ID 82: Chiêu Kogoraseru
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tiễn thuật Long Tiễn Vong Nhân

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.100 | 157 | 750ms | 3 | 140 x 140 | Xác xuất đóng băng: +3% duy trì 1.5 giây, Tấn công nội: +310%, Băng sát: +2000, Tấn công cơ bản: +540%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 160 | 650ms | 3 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +315%, Băng sát: +2100, Tấn công cơ bản: +545%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 163 | 630ms | 3 | 140 x 140 | Xác xuất đóng băng: +4% duy trì 1.5 giây, Tấn công nội: +320%, Băng sát: +2200, Tấn công cơ bản: +550%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 166 | 600ms | 3 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +325%, Băng sát: +2300, Tấn công cơ bản: +555%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 169 | 550ms | 4 | 140 x 140 | Xác xuất đóng băng: +5% duy trì 1.5 giây, Tấn công nội: +330%, Băng sát: +2400, Tấn công cơ bản: +560%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 172 | 530ms | 4 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +335%, Băng sát: +2500, Tấn công cơ bản: +570%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 175 | 500ms | 4 | 140 x 140 | Xác xuất đóng băng: +6% duy trì 1.5 giây, Tấn công nội: +340%, Băng sát: +2600, Tấn công cơ bản: +580%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 178 | 450ms | 4 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +350%, Băng sát: +2700, Tấn công cơ bản: +590%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 181 | 450ms | 5 | 140 x 140 | Xác xuất đóng băng: +7% duy trì 1.5 giây, Tấn công nội: +360%, Băng sát: +2800, Tấn công cơ bản: +610%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 184 | 450ms | 5 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +370%, Băng sát: +3000, Tấn công cơ bản: +630%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 187 | 400ms | 5 | 140 x 140 | Xác xuất đóng băng: +8% duy trì 1.5 giây, Tấn công nội: +380%, Băng sát: +3200, Tấn công cơ bản: +650%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 190 | 300ms | 6 | 140 x 140 | Xác xuất đóng băng: +9% duy trì 1.5 giây, Tấn công nội: +420%, Băng sát: +4200, Tấn công cơ bản: +700%, Tương khắc: +200 |

### Skill ID 88: Tiễn Ưng Vĩnh Cửu
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Đưa Băng Chakra vào mũi tên, khi chạm đến mục tiêu sẽ phát nổ tạo ra một quả cầu năng lượng nhắm thẳng đến mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất đóng băng: +11% duy trì 1.5 giây, Tấn công nội: +520%, Băng sát: +5000, Tấn công cơ bản: +750%, Tương khắc: +300 |

---

## 6. PHÁI 5: NINJA ĐAO (BẢN ĐAO - HỆ PHONG)

* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Tanker / Đấu sĩ / Ngoại công (Sức Khỏe + Thể Lực)
* **Đặc trưng**: Máu dày, giáp trâu nhất game, sát thương sấm sét gây Choáng (Stun) diện rộng.

### Skill ID 37: Chiêu Enchokuto
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Đao pháp nhập môn - dùng đao chém mạnh vào một mục tiêu gây sát thương lên mục tiêu đó

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 750ms | 1 | 44 x 40 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.10 | 1 | 650ms | 1 | 44 x 40 | Tấn công ngoại: +5%, Phong lôi: +10, Tấn công cơ bản: +100%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 2 | 630ms | 1 | 44 x 40 | Tấn công ngoại: +10%, Phong lôi: +20, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 3 | 600ms | 1 | 44 x 40 | Tấn công ngoại: +15%, Phong lôi: +30, Tấn công cơ bản: +100%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 4 | 550ms | 1 | 44 x 40 | Tấn công ngoại: +20%, Phong lôi: +40, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 5 | 530ms | 1 | 44 x 40 | Tấn công ngoại: +25%, Phong lôi: +50, Tấn công cơ bản: +100%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 6 | 500ms | 1 | 44 x 40 | Tấn công ngoại: +30%, Phong lôi: +60, Tấn công cơ bản: +100%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 7 | 450ms | 1 | 44 x 40 | Tấn công ngoại: +35%, Phong lôi: +70, Tấn công cơ bản: +110%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 8 | 450ms | 1 | 44 x 40 | Tấn công ngoại: +40%, Phong lôi: +80, Tấn công cơ bản: +120%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 9 | 450ms | 1 | 44 x 40 | Tấn công ngoại: +45%, Phong lôi: +90, Tấn công cơ bản: +130%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 10 | 400ms | 1 | 44 x 40 | Tấn công ngoại: +50%, Phong lôi: +100, Tấn công cơ bản: +140%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 12 | 300ms | 1 | 44 x 40 | Tấn công ngoại: +55%, Phong lôi: +110, Tấn công cơ bản: +145%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 15 | 300ms | 1 | 44 x 40 | Tấn công ngoại: +60%, Phong lôi: +120, Tấn công cơ bản: +150%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 38: Chiêu Konoitoame
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao sức tấn công, HP và tốc độ di chuyển

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +20, Phong lôi ngoại: +75, Tỉ lệ HP tối đa: +10%, Tốc độ di chuyển: +20 |
| **Cấp 2** | Lv.16 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +24, Phong lôi ngoại: +90, Tỉ lệ HP tối đa: +15%, Tốc độ di chuyển: +23 |
| **Cấp 3** | Lv.17 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +28, Phong lôi ngoại: +105, Tỉ lệ HP tối đa: +20%, Tốc độ di chuyển: +26 |
| **Cấp 4** | Lv.18 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +32, Phong lôi ngoại: +120, Tỉ lệ HP tối đa: +25%, Tốc độ di chuyển: +30 |
| **Cấp 5** | Lv.19 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +36, Phong lôi ngoại: +135, Tỉ lệ HP tối đa: +30%, Tốc độ di chuyển: +35 |
| **Cấp 6** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Chí mạng: +40, Phong lôi ngoại: +150, Tỉ lệ HP tối đa: +35%, Tốc độ di chuyển: +40 |

### Skill ID 39: Chiêu Maroyakato
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Chém 2 nhát Đao liên tiếp vào mục tiêu gây sát thương lên mục tiêu đó

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 1s | 1 | 58 x 29 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.20 | 31 | 900ms | 1 | 58 x 29 | Tấn công ngoại: +50%, Phong lôi: +140, Tấn công cơ bản: +160%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 40: +12% |
| **Cấp 2** | Lv.21 | 32 | 850ms | 1 | 58 x 29 | Tấn công ngoại: +55%, Phong lôi: +150, Tấn công cơ bản: +160%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +18% |
| **Cấp 3** | Lv.22 | 33 | 800ms | 1 | 58 x 29 | Tấn công ngoại: +60%, Phong lôi: +160, Tấn công cơ bản: +160%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 40: +24% |
| **Cấp 4** | Lv.23 | 34 | 800ms | 1 | 58 x 29 | Tấn công ngoại: +65%, Phong lôi: +170, Tấn công cơ bản: +160%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +31% |
| **Cấp 5** | Lv.24 | 35 | 750ms | 1 | 58 x 29 | Tấn công ngoại: +70%, Phong lôi: +180, Tấn công cơ bản: +160%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 40: +38% |
| **Cấp 6** | Lv.25 | 36 | 750ms | 1 | 58 x 29 | Tấn công ngoại: +75%, Phong lôi: +190, Tấn công cơ bản: +160%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +45% |
| **Cấp 7** | Lv.26 | 37 | 700ms | 1 | 58 x 29 | Tấn công ngoại: +80%, Phong lôi: +200, Tấn công cơ bản: +160%, Tương khắc: +45, Hỗ trợ tấn công chiêu cấp 40: +52% |
| **Cấp 8** | Lv.27 | 38 | 700ms | 1 | 58 x 29 | Tấn công ngoại: +85%, Phong lôi: +220, Tấn công cơ bản: +160%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +59% |
| **Cấp 9** | Lv.28 | 39 | 650ms | 1 | 58 x 29 | Tấn công ngoại: +90%, Phong lôi: +240, Tấn công cơ bản: +160%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 40: +66% |
| **Cấp 10** | Lv.29 | 40 | 650ms | 1 | 58 x 29 | Tấn công ngoại: +100%, Phong lôi: +260, Tấn công cơ bản: +160%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +73% |
| **Cấp 11** | Lv.30 | 42 | 600ms | 1 | 58 x 29 | Tấn công ngoại: +110%, Phong lôi: +280, Tấn công cơ bản: +160%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 40: +81% |
| **Cấp 12** | Lv.31 | 45 | 600ms | 1 | 58 x 29 | Tấn công ngoại: +120%, Phong lôi: +300, Tấn công cơ bản: +160%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 40: +90% |

### Skill ID 40: Chiêu Magumandari
* **Loại chiêu**: Khống chế / Bắt quái
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Khiến cho quái vật bị tác động không thể tấn công trong một thời gian nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 30 | 13s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +5 giây |
| **Cấp 2** | Lv.26 | 40 | 15s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +7 giây |
| **Cấp 3** | Lv.27 | 50 | 18s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +9 giây |
| **Cấp 4** | Lv.28 | 60 | 20s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +11 giây |
| **Cấp 5** | Lv.29 | 70 | 25s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +13 giây |
| **Cấp 6** | Lv.30 | 80 | 27s | 1 | 100 x 50 | Vô hiệu hóa tấn công của quái vật: duy trì +15 giây |

### Skill ID 41: Chiêu Chousouto
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Kết hợp phong thuật vung đao về phía kẻ địch gây sát thương trong phạm vi nhất định, sát thương tối đa 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 53 x 40 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.30 | 46 | 750ms | 2 | 53 x 40 | Tấn công ngoại: +50%, Phong lôi: +200, Tấn công cơ bản: +180%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 47 | 650ms | 2 | 53 x 40 | Tấn công ngoại: +55%, Phong lôi: +210, Tấn công cơ bản: +180%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 48 | 630ms | 2 | 53 x 40 | Tấn công ngoại: +60%, Phong lôi: +220, Tấn công cơ bản: +180%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 49 | 600ms | 2 | 53 x 40 | Tấn công ngoại: +65%, Phong lôi: +230, Tấn công cơ bản: +180%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 50 | 550ms | 2 | 53 x 40 | Tấn công ngoại: +70%, Phong lôi: +240, Tấn công cơ bản: +180%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 51 | 530ms | 3 | 53 x 40 | Tấn công ngoại: +80%, Phong lôi: +250, Tấn công cơ bản: +180%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 52 | 500ms | 3 | 53 x 40 | Tấn công ngoại: +85%, Phong lôi: +270, Tấn công cơ bản: +180%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 53 | 450ms | 3 | 53 x 40 | Tấn công ngoại: +90%, Phong lôi: +290, Tấn công cơ bản: +180%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 54 | 450ms | 3 | 53 x 40 | Tấn công ngoại: +95%, Phong lôi: +310, Tấn công cơ bản: +180%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 55 | 450ms | 3 | 53 x 40 | Tấn công ngoại: +100%, Phong lôi: +330, Tấn công cơ bản: +180%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 57 | 400ms | 3 | 53 x 40 | Tấn công ngoại: +105%, Phong lôi: +350, Tấn công cơ bản: +180%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 60 | 300ms | 3 | 53 x 40 | Tấn công ngoại: +110%, Phong lôi: +370, Tấn công cơ bản: +180%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 42: Chiêu Aisubaagu
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng dịch phong thuật nhanh chóng áp sát và tấn công kẻ thù ngay lập tức

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.35 | 50 | 25s | 1 | 150 x 150 | Tương khắc: +60, Tấn công ngoại: +200%, Phong lôi: +200, Tấn công cơ bản: +100% |
| **Cấp 2** | Lv.36 | 60 | 22s | 1 | 160 x 160 | Tương khắc: +65, Tấn công ngoại: +200%, Phong lôi: +220, Tấn công cơ bản: +100% |
| **Cấp 3** | Lv.37 | 70 | 19s | 1 | 170 x 170 | Tương khắc: +70, Tấn công ngoại: +200%, Phong lôi: +240, Tấn công cơ bản: +100% |
| **Cấp 4** | Lv.38 | 80 | 16s | 1 | 180 x 180 | Tương khắc: +75, Tấn công ngoại: +200%, Phong lôi: +260, Tấn công cơ bản: +100% |
| **Cấp 5** | Lv.39 | 90 | 13s | 1 | 200 x 200 | Tương khắc: +80, Tấn công ngoại: +200%, Phong lôi: +280, Tấn công cơ bản: +100% |
| **Cấp 6** | Lv.40 | 100 | 10s | 1 | 220 x 220 | Tương khắc: +90, Tấn công ngoại: +200%, Phong lôi: +300, Tấn công cơ bản: +100% |

### Skill ID 43: Chiêu Hayateto
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Nhát chém kèm thèo gió và sấm sét giáng thẳng vào mục tiêu, gây ra sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 21s | 1 | 50 x 47 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.40 | 160 | 20s | 1 | 50 x 47 | Xác suất gây choáng: +40% duy trì 2 giây, Tấn công ngoại: +80%, Phong lôi: +700, Tấn công cơ bản: +55%, Tương khắc: +70 |
| **Cấp 2** | Lv.41 | 170 | 19.50s | 1 | 50 x 47 | Xác suất gây choáng: +43% duy trì 2 giây, Tấn công ngoại: +80%, Phong lôi: +900, Tấn công cơ bản: +60%, Tương khắc: +75 |
| **Cấp 3** | Lv.42 | 180 | 19.50s | 1 | 50 x 47 | Xác suất gây choáng: +46% duy trì 2 giây, Tấn công ngoại: +85%, Phong lôi: +1100, Tấn công cơ bản: +65%, Tương khắc: +75 |
| **Cấp 4** | Lv.43 | 190 | 19s | 1 | 50 x 47 | Xác suất gây choáng: +49% duy trì 2 giây, Tấn công ngoại: +85%, Phong lôi: +1300, Tấn công cơ bản: +70%, Tương khắc: +80 |
| **Cấp 5** | Lv.44 | 200 | 19s | 1 | 50 x 47 | Xác suất gây choáng: +52% duy trì 2 giây, Tấn công ngoại: +90%, Phong lôi: +1500, Tấn công cơ bản: +75%, Tương khắc: +80 |
| **Cấp 6** | Lv.45 | 210 | 18.50s | 1 | 50 x 47 | Xác suất gây choáng: +55% duy trì 2 giây, Tấn công ngoại: +90%, Phong lôi: +1700, Tấn công cơ bản: +80%, Tương khắc: +85 |
| **Cấp 7** | Lv.46 | 220 | 18.50s | 1 | 50 x 47 | Xác suất gây choáng: +58% duy trì 2 giây, Tấn công ngoại: +95%, Phong lôi: +1900, Tấn công cơ bản: +85%, Tương khắc: +85 |
| **Cấp 8** | Lv.47 | 230 | 18s | 1 | 50 x 47 | Xác suất gây choáng: +61% duy trì 2 giây, Tấn công ngoại: +100%, Phong lôi: +2100, Tấn công cơ bản: +90%, Tương khắc: +90 |
| **Cấp 9** | Lv.48 | 240 | 18s | 1 | 50 x 47 | Xác suất gây choáng: +65% duy trì 2 giây, Tấn công ngoại: +110%, Phong lôi: +2300, Tấn công cơ bản: +95%, Tương khắc: +90 |
| **Cấp 10** | Lv.49 | 250 | 17.50s | 1 | 50 x 47 | Xác suất gây choáng: +70% duy trì 2 giây, Tấn công ngoại: +120%, Phong lôi: +2500, Tấn công cơ bản: +100%, Tương khắc: +95 |
| **Cấp 11** | Lv.50 | 260 | 17.50s | 1 | 50 x 47 | Xác suất gây choáng: +75% duy trì 2 giây, Tấn công ngoại: +130%, Phong lôi: +2700, Tấn công cơ bản: +110%, Tương khắc: +95 |
| **Cấp 12** | Lv.51 | 270 | 17s | 1 | 50 x 47 | Xác suất gây choáng: +80% duy trì 2 giây, Tấn công ngoại: +140%, Phong lôi: +2900, Tấn công cơ bản: +120%, Tương khắc: +100 |

### Skill ID 44: Chiêu Zenpanteki
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Tăng khả năng kháng băng đồng thời giúp bản thân nhanh chóng hồi phục khi bị đóng băng

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +20, Giảm thời gian bị đóng băng: -0.2 giây, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +35, Giảm thời gian bị đóng băng: -0.2 giây, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +50, Giảm thời gian bị đóng băng: -0.3 giây, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +65, Giảm thời gian bị đóng băng: -0.3 giây, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +80, Giảm thời gian bị đóng băng: -0.4 giây, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Kháng băng: +100, Giảm thời gian bị đóng băng: -0.5 giây, Tương khắc: +100 |

### Skill ID 45: Chiêu Raikouto
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Vung đao xuất ra luồng sét đánh thẳng vào mục tiêu, đồng thời gây sát thương các mục tiêu lân cận, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 0 | 750ms | 1 | 58 x 48 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.50 | 76 | 650ms | 2 | 58 x 48 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +90%, Phong lôi: +470, Tấn công cơ bản: +285%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 80 | 630ms | 2 | 58 x 48 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +100%, Phong lôi: +500, Tấn công cơ bản: +300%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 84 | 600ms | 2 | 58 x 48 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +110%, Phong lôi: +530, Tấn công cơ bản: +315%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 88 | 550ms | 2 | 58 x 48 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +120%, Phong lôi: +560, Tấn công cơ bản: +330%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 92 | 530ms | 3 | 58 x 48 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +130%, Phong lôi: +590, Tấn công cơ bản: +345%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 96 | 500ms | 3 | 58 x 48 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +140%, Phong lôi: +620, Tấn công cơ bản: +360%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 100 | 450ms | 3 | 58 x 48 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +150%, Phong lôi: +650, Tấn công cơ bản: +375%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 104 | 450ms | 3 | 58 x 48 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +160%, Phong lôi: +680, Tấn công cơ bản: +390%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 108 | 450ms | 4 | 58 x 48 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +170%, Phong lôi: +710, Tấn công cơ bản: +405%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 112 | 400ms | 4 | 58 x 48 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +180%, Phong lôi: +740, Tấn công cơ bản: +420%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 116 | 300ms | 4 | 58 x 48 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +190%, Phong lôi: +770, Tấn công cơ bản: +435%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 120 | 300ms | 4 | 58 x 48 | Xác xuất gây choáng: +10% duy trì 1 giây, Tấn công ngoại: +200%, Phong lôi: +800, Tấn công cơ bản: +450%, Tương khắc: +130 |

### Skill ID 59: Chiêu Ikennotto
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Huyễn hóa lôi điện nâng cao khả năng giữ chân mục tiêu đồng thời tăng cao HP của bản thân.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +2% |
| **Cấp 2** | Lv.61 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +3% |
| **Cấp 3** | Lv.62 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 1000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +4% |
| **Cấp 4** | Lv.63 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 1000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +5% |
| **Cấp 5** | Lv.64 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 1500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +6% |
| **Cấp 6** | Lv.65 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 1500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +7% |
| **Cấp 7** | Lv.66 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 2000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +8% |
| **Cấp 8** | Lv.67 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 2000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +9% |
| **Cấp 9** | Lv.68 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 2500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +10% |
| **Cấp 10** | Lv.69 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 2500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +11% |
| **Cấp 11** | Lv.70 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 3000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +12% |
| **Cấp 12** | Lv.71 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 3000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +13% |
| **Cấp 13** | Lv.72 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 3500 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +15% |
| **Cấp 14** | Lv.73 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 4000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +17% |
| **Cấp 15** | Lv.74 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 5000 giây giữ chặt đối phương của chiêu cấp 35, Tỉ lệ HP tối đa: +20% |

### Skill ID 65: Chiêu Baasutosutoomu
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Chém một luồng phong quyển về phía kẻ địch gây sát thương phong hệ đồng thời có tỉ lệ gây choáng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 60 x 30 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.70 | 100 | 750ms | 1 | 60 x 30 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +220%, Phong lôi: +700, Tấn công cơ bản: +235%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 103 | 650ms | 1 | 60 x 30 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +230%, Phong lôi: +790, Tấn công cơ bản: +270%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 106 | 630ms | 1 | 60 x 30 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +240%, Phong lôi: +880, Tấn công cơ bản: +305%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 109 | 600ms | 2 | 60 x 30 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +250%, Phong lôi: +970, Tấn công cơ bản: +340%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 112 | 550ms | 2 | 60 x 30 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +260%, Phong lôi: +1060, Tấn công cơ bản: +375%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 115 | 530ms | 2 | 60 x 30 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +270%, Phong lôi: +1150, Tấn công cơ bản: +410%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 118 | 500ms | 3 | 60 x 30 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +280%, Phong lôi: +1240, Tấn công cơ bản: +445%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 121 | 450ms | 3 | 60 x 30 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +290%, Phong lôi: +1330, Tấn công cơ bản: +480%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 124 | 450ms | 3 | 60 x 30 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +300%, Phong lôi: +1420, Tấn công cơ bản: +515%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 127 | 450ms | 4 | 60 x 30 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +310%, Phong lôi: +1510, Tấn công cơ bản: +550%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 130 | 400ms | 4 | 60 x 30 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +320%, Phong lôi: +1600, Tấn công cơ bản: +585%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 133 | 350ms | 4 | 60 x 30 | Xác xuất gây choáng: +10% duy trì 1 giây, Tấn công ngoại: +330%, Phong lôi: +1700, Tấn công cơ bản: +620%, Tương khắc: +150 |

### Skill ID 71: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 2000 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 74: Chiêu Kaminari
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phóng ra những tia lôi điện giáng những đòn chí mạng vào kẻ thù.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | 60 x 60 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.80 | 120 | 750ms | 2 | 90 x 50 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +220%, Phong lôi: +700, Tấn công cơ bản: +320%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 122 | 650ms | 2 | 90 x 50 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +225%, Phong lôi: +800, Tấn công cơ bản: +340%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 124 | 630ms | 2 | 90 x 50 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +230%, Phong lôi: +900, Tấn công cơ bản: +360%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 126 | 600ms | 3 | 90 x 50 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +235%, Phong lôi: +1000, Tấn công cơ bản: +380%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 128 | 550ms | 3 | 90 x 50 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +240%, Phong lôi: +1100, Tấn công cơ bản: +410%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 130 | 530ms | 3 | 90 x 50 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +250%, Phong lôi: +1200, Tấn công cơ bản: +440%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 132 | 500ms | 4 | 90 x 50 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +260%, Phong lôi: +1400, Tấn công cơ bản: +470%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 134 | 450ms | 4 | 90 x 50 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +270%, Phong lôi: +1600, Tấn công cơ bản: +500%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 136 | 450ms | 4 | 90 x 50 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +280%, Phong lôi: +1800, Tấn công cơ bản: +540%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 138 | 450ms | 5 | 90 x 50 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +300%, Phong lôi: +2100, Tấn công cơ bản: +580%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 140 | 400ms | 5 | 90 x 50 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +340%, Phong lôi: +2500, Tấn công cơ bản: +620%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 142 | 300ms | 5 | 90 x 50 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +390%, Phong lôi: +3000, Tấn công cơ bản: +670%, Tương khắc: +180 |

### Skill ID 80: Chiêu Raijin
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Lôi thuật Lôi Mã Phong Vân Khởi

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | 60 x 60 | Tấn công ngoại: +0% |
| **Cấp 1** | Lv.100 | 138 | 750ms | 3 | 90 x 46 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +220%, Phong lôi: +1000, Tấn công cơ bản: +400%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 140 | 650ms | 3 | 90 x 46 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +230%, Phong lôi: +1100, Tấn công cơ bản: +410%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 142 | 630ms | 3 | 90 x 46 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công ngoại: +240%, Phong lôi: +1300, Tấn công cơ bản: +420%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 144 | 600ms | 3 | 90 x 46 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +250%, Phong lôi: +1500, Tấn công cơ bản: +430%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 146 | 550ms | 4 | 90 x 46 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công ngoại: +265%, Phong lôi: +1700, Tấn công cơ bản: +460%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 148 | 530ms | 4 | 90 x 46 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +280%, Phong lôi: +1900, Tấn công cơ bản: +490%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 150 | 500ms | 4 | 90 x 46 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công ngoại: +295%, Phong lôi: +2100, Tấn công cơ bản: +520%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 152 | 450ms | 4 | 90 x 46 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +310%, Phong lôi: +2400, Tấn công cơ bản: +550%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 154 | 450ms | 5 | 90 x 46 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công ngoại: +340%, Phong lôi: +2800, Tấn công cơ bản: +600%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 156 | 450ms | 5 | 90 x 46 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +370%, Phong lôi: +3300, Tấn công cơ bản: +650%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 158 | 400ms | 5 | 90 x 46 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công ngoại: +400%, Phong lôi: +4000, Tấn công cơ bản: +700%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 160 | 300ms | 6 | 90 x 46 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công ngoại: +450%, Phong lôi: +5000, Tấn công cơ bản: +770%, Tương khắc: +200 |

### Skill ID 89: Vũ Đao Thiên Lôi
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Phong lôi thần thuật, Triệu hồi thiên lôi nhắm thẳng vào mục tiêu gây 1 lượng sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất gây choáng: +11% duy trì 1 giây, Tấn công nội: +580%, Phong lôi: +5000, Tấn công cơ bản: +780%, Tương khắc: +350 |

---

## 7. PHÁI 6: NINJA QUẠT (PHIẾN - HỆ PHONG)

* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Pháp sư / Hỗ trợ / Nội công (Chakra + Thể Lực)
* **Đặc trưng**: Khống chế bão lốc xoáy, duy nhất có khả năng Hồi máu (Heal), Buff Công Thủ và Hồi sinh đồng đội.

### Skill ID 46: Chiêu Ouchia
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Quạt mạnh tạo ra luồng gió tấn công thẳng vào một mục tiêu, gây sát thương lên mục tiêu đó

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.10 | 0 | 750ms | 1 | 56 x 29 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.10 | 1 | 750ms | 1 | 56 x 29 | Tấn công nội: +5%, Phong lôi: +10, Tấn công cơ bản: +150%, Tương khắc: +10, Hỗ trợ tấn công chiêu cấp 30: +5%, Hỗ trợ tấn công chiêu cấp 50: +4% |
| **Cấp 2** | Lv.11 | 2 | 650ms | 1 | 56 x 29 | Tấn công nội: +10%, Phong lôi: +15, Tấn công cơ bản: +150%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +10%, Hỗ trợ tấn công chiêu cấp 50: +8% |
| **Cấp 3** | Lv.12 | 3 | 630ms | 1 | 56 x 29 | Tấn công nội: +15%, Phong lôi: +20, Tấn công cơ bản: +150%, Tương khắc: +15, Hỗ trợ tấn công chiêu cấp 30: +15%, Hỗ trợ tấn công chiêu cấp 50: +12% |
| **Cấp 4** | Lv.13 | 4 | 600ms | 1 | 56 x 29 | Tấn công nội: +20%, Phong lôi: +25, Tấn công cơ bản: +150%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +20%, Hỗ trợ tấn công chiêu cấp 50: +16% |
| **Cấp 5** | Lv.14 | 5 | 550ms | 1 | 56 x 29 | Tấn công nội: +25%, Phong lôi: +30, Tấn công cơ bản: +150%, Tương khắc: +20, Hỗ trợ tấn công chiêu cấp 30: +25%, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 6** | Lv.15 | 6 | 530ms | 1 | 56 x 29 | Tấn công nội: +30%, Phong lôi: +35, Tấn công cơ bản: +150%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +30%, Hỗ trợ tấn công chiêu cấp 50: +24% |
| **Cấp 7** | Lv.16 | 7 | 500ms | 1 | 56 x 29 | Tấn công nội: +35%, Phong lôi: +40, Tấn công cơ bản: +200%, Tương khắc: +25, Hỗ trợ tấn công chiêu cấp 30: +35%, Hỗ trợ tấn công chiêu cấp 50: +28% |
| **Cấp 8** | Lv.17 | 8 | 450ms | 1 | 56 x 29 | Tấn công nội: +40%, Phong lôi: +45, Tấn công cơ bản: +200%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +40%, Hỗ trợ tấn công chiêu cấp 50: +32% |
| **Cấp 9** | Lv.18 | 9 | 450ms | 1 | 56 x 29 | Tấn công nội: +45%, Phong lôi: +50, Tấn công cơ bản: +200%, Tương khắc: +30, Hỗ trợ tấn công chiêu cấp 30: +45%, Hỗ trợ tấn công chiêu cấp 50: +36% |
| **Cấp 10** | Lv.19 | 10 | 450ms | 1 | 56 x 29 | Tấn công nội: +50%, Phong lôi: +60, Tấn công cơ bản: +200%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +50%, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 11** | Lv.20 | 12 | 400ms | 1 | 56 x 29 | Tấn công nội: +55%, Phong lôi: +70, Tấn công cơ bản: +200%, Tương khắc: +35, Hỗ trợ tấn công chiêu cấp 30: +55%, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 12** | Lv.21 | 15 | 300ms | 1 | 56 x 29 | Tấn công nội: +60%, Phong lôi: +80, Tấn công cơ bản: +200%, Tương khắc: +40, Hỗ trợ tấn công chiêu cấp 30: +60%, Hỗ trợ tấn công chiêu cấp 50: +50% |

### Skill ID 47: Chiêu Suishou
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Trong mỗi giây hồi phục cho bản thân và đồng đội một lượng máu nhất định

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.15 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.15 | 10 | 9s | 3 | 90 x 90 | Nữa giây phục hồi HP: +70 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +80 duy trì 5 giây |
| **Cấp 2** | Lv.25 | 20 | 10s | 3 | 100 x 100 | Nữa giây phục hồi HP: +100 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +150 duy trì 5 giây |
| **Cấp 3** | Lv.35 | 30 | 12s | 4 | 110 x 110 | Nữa giây phục hồi HP: +150 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +300 duy trì 5 giây |
| **Cấp 4** | Lv.45 | 40 | 13s | 4 | 120 x 120 | Nữa giây phục hồi HP: +300 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +450 duy trì 5 giây |
| **Cấp 5** | Lv.55 | 50 | 15s | 5 | 130 x 130 | Nữa giây phục hồi HP: +400 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +600 duy trì 5 giây |
| **Cấp 6** | Lv.65 | 60 | 17s | 6 | 140 x 140 | Nữa giây phục hồi HP: +600 duy trì 5 giây, Nữa giây phục hồi HP cho đồng đội: +800 duy trì 5 giây |

### Skill ID 48: Chiêu Oouchia
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Nhẫn thuật tâm pháp - nâng cao lượng HP, MP và kinh nghiệm khi đánh quái

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.20 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +3%, Tỉ lệ MP tối đa: +8%, Tỉ lệ HP tối đa: +8%, Tốc độ di chuyển: +20 |
| **Cấp 2** | Lv.21 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +6%, Tỉ lệ MP tối đa: +10%, Tỉ lệ HP tối đa: +10%, Tốc độ di chuyển: +25 |
| **Cấp 3** | Lv.22 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +9%, Tỉ lệ MP tối đa: +12%, Tỉ lệ HP tối đa: +12%, Tốc độ di chuyển: +25 |
| **Cấp 4** | Lv.23 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +12%, Tỉ lệ MP tối đa: +14%, Tỉ lệ HP tối đa: +14%, Tốc độ di chuyển: +30 |
| **Cấp 5** | Lv.24 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +15%, Tỉ lệ MP tối đa: +16%, Tỉ lệ HP tối đa: +16%, Tốc độ di chuyển: +30 |
| **Cấp 6** | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +18%, Tỉ lệ MP tối đa: +18%, Tỉ lệ HP tối đa: +18%, Tốc độ di chuyển: +35 |
| **Cấp 7** | Lv.26 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +21%, Tỉ lệ MP tối đa: +20%, Tỉ lệ HP tối đa: +20%, Tốc độ di chuyển: +35 |
| **Cấp 8** | Lv.27 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +24%, Tỉ lệ MP tối đa: +22%, Tỉ lệ HP tối đa: +22%, Tốc độ di chuyển: +40 |
| **Cấp 9** | Lv.28 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +27%, Tỉ lệ MP tối đa: +24%, Tỉ lệ HP tối đa: +24%, Tốc độ di chuyển: +40 |
| **Cấp 10** | Lv.29 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +30%, Tỉ lệ MP tối đa: +26%, Tỉ lệ HP tối đa: +26%, Tốc độ di chuyển: +45 |
| **Cấp 11** | Lv.30 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +32%, Tỉ lệ MP tối đa: +28%, Tỉ lệ HP tối đa: +28%, Tốc độ di chuyển: +45 |
| **Cấp 12** | Lv.31 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng % kinh nghiệm khi đánh quái: +35%, Tỉ lệ MP tối đa: +30%, Tỉ lệ HP tối đa: +30%, Tốc độ di chuyển: +50 |

### Skill ID 49: Chiêu Kusenmono
* **Loại chiêu**: Hồi sinh đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Trong tích tắc hồi phục cho một mục tiêu đang trong trạng thái kiệt sức, đồng thời gia tăng né tránh

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.25 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.25 | 200 | 800s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +400 duy trì 5 giây |
| **Cấp 2** | Lv.26 | 200 | 400s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +600 duy trì 5 giây |
| **Cấp 3** | Lv.27 | 200 | 200s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +800 duy trì 5 giây |
| **Cấp 4** | Lv.28 | 200 | 100s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +1000 duy trì 5 giây |
| **Cấp 5** | Lv.29 | 200 | 50s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +1200 duy trì 5 giây |
| **Cấp 6** | Lv.30 | 200 | 25s | 1 | 80 x 80 | Hồi phục cho đồng đội, sau khi hồi phục sẽ giúp đồng đội tăng khả năng né tránh: +1400 duy trì 5 giây |

### Skill ID 50: Chiêu Bakkuuchiha
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Ném quạt xoay vòng trước mắt gây sát thương lên các mục tiêu lân cận, tối đa 3 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.30 | 0 | 750ms | 1 | 48 x 31 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.30 | 61 | 750ms | 2 | 48 x 31 | Tấn công nội: +50%, Phong lôi: +220, Tấn công cơ bản: +150%, Tương khắc: +50, Hỗ trợ tấn công chiêu cấp 50: +10% |
| **Cấp 2** | Lv.31 | 62 | 650ms | 2 | 48 x 31 | Tấn công nội: +55%, Phong lôi: +230, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +15% |
| **Cấp 3** | Lv.32 | 63 | 630ms | 2 | 48 x 31 | Tấn công nội: +60%, Phong lôi: +240, Tấn công cơ bản: +150%, Tương khắc: +55, Hỗ trợ tấn công chiêu cấp 50: +20% |
| **Cấp 4** | Lv.33 | 64 | 600ms | 2 | 48 x 31 | Tấn công nội: +65%, Phong lôi: +250, Tấn công cơ bản: +200%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +25% |
| **Cấp 5** | Lv.34 | 65 | 550ms | 2 | 48 x 31 | Tấn công nội: +70%, Phong lôi: +260, Tấn công cơ bản: +200%, Tương khắc: +60, Hỗ trợ tấn công chiêu cấp 50: +30% |
| **Cấp 6** | Lv.35 | 66 | 530ms | 3 | 48 x 31 | Tấn công nội: +80%, Phong lôi: +270, Tấn công cơ bản: +200%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +35% |
| **Cấp 7** | Lv.36 | 67 | 500ms | 3 | 48 x 31 | Tấn công nội: +85%, Phong lôi: +280, Tấn công cơ bản: +200%, Tương khắc: +65, Hỗ trợ tấn công chiêu cấp 50: +40% |
| **Cấp 8** | Lv.37 | 68 | 450ms | 3 | 48 x 31 | Tấn công nội: +90%, Phong lôi: +300, Tấn công cơ bản: +200%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +45% |
| **Cấp 9** | Lv.38 | 69 | 450ms | 3 | 48 x 31 | Tấn công nội: +95%, Phong lôi: +320, Tấn công cơ bản: +300%, Tương khắc: +70, Hỗ trợ tấn công chiêu cấp 50: +50% |
| **Cấp 10** | Lv.39 | 70 | 450ms | 3 | 48 x 31 | Tấn công nội: +100%, Phong lôi: +340, Tấn công cơ bản: +300%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +55% |
| **Cấp 11** | Lv.40 | 73 | 400ms | 3 | 48 x 31 | Tấn công nội: +105%, Phong lôi: +360, Tấn công cơ bản: +300%, Tương khắc: +75, Hỗ trợ tấn công chiêu cấp 50: +65% |
| **Cấp 12** | Lv.41 | 76 | 300ms | 3 | 48 x 31 | Tấn công nội: +110%, Phong lôi: +380, Tấn công cơ bản: +300%, Tương khắc: +80, Hỗ trợ tấn công chiêu cấp 50: +75% |

### Skill ID 51: Chiêu Hayatemi
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Trong một thời gian nhất định tăng đồng thời khả năng tấn công và phòng thủ cho bản thân và đồng đội

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.35 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.35 | 200 | 90s | 3 | 90 x 90 | Tăng kháng tất cả cho mình và đồng đội: +10 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +100% duy trì 90 giây |
| **Cấp 2** | Lv.36 | 200 | 80s | 3 | 100 x 100 | Tăng kháng tất cả cho mình và đồng đội: +20 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +120% duy trì 90 giây |
| **Cấp 3** | Lv.37 | 200 | 70s | 4 | 110 x 110 | Tăng kháng tất cả cho mình và đồng đội: +30 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +140% duy trì 90 giây |
| **Cấp 4** | Lv.38 | 200 | 60s | 4 | 120 x 120 | Tăng kháng tất cả cho mình và đồng đội: +40 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +160% duy trì 90 giây |
| **Cấp 5** | Lv.39 | 200 | 50s | 5 | 130 x 130 | Tăng kháng tất cả cho mình và đồng đội: +50 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +180% duy trì 90 giây |
| **Cấp 6** | Lv.40 | 200 | 45s | 6 | 140 x 140 | Tăng kháng tất cả cho mình và đồng đội: +60 duy trì 90 giây, Tăng tấn công cơ bản cho mình và đồng đội: +200% duy trì 90 giây |

### Skill ID 52: Chiêu Bousouhayate
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Trong môt khoảng thời gian giảm tất cả các hiệu ứng lên bản thân và đồng đội

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.40 | 0 | 65s | 0 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.40 | 130 | 65s | 2 | 100 x 100 | Giảm 3 giây thời gian bị bỏng: duy trì 20 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 20 giây |
| **Cấp 2** | Lv.41 | 140 | 65s | 2 | 100 x 100 | Giảm 3 giây thời gian bị bỏng: duy trì 25 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 25 giây |
| **Cấp 3** | Lv.42 | 150 | 65s | 2 | 100 x 100 | Giảm 3 giây thời gian bị bỏng: duy trì 30 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 30 giây |
| **Cấp 4** | Lv.43 | 160 | 65s | 3 | 110 x 110 | Giảm 3 giây thời gian bị bỏng: duy trì 35 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 35 giây |
| **Cấp 5** | Lv.44 | 170 | 65s | 3 | 110 x 110 | Giảm 3 giây thời gian bị bỏng: duy trì 35 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 35 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 35 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 35 giây |
| **Cấp 6** | Lv.45 | 180 | 65s | 3 | 110 x 110 | Giảm 3 giây thời gian bị bỏng: duy trì 40 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 40 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 40 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 40 giây |
| **Cấp 7** | Lv.46 | 190 | 65s | 4 | 120 x 120 | Giảm 3 giây thời gian bị bỏng: duy trì 45 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 45 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 45 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 45 giây |
| **Cấp 8** | Lv.47 | 200 | 65s | 4 | 120 x 120 | Giảm 3 giây thời gian bị bỏng: duy trì 50 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 50 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 50 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 50 giây |
| **Cấp 9** | Lv.48 | 220 | 65s | 4 | 120 x 120 | Giảm 3 giây thời gian bị bỏng: duy trì 50 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 50 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 50 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 50 giây, Giảm 1 giây thời gian bị choáng: duy trì 50 giây, Giảm 0.5 giây thời gian bị choáng cho đồng đội: duy trì 50 giây |
| **Cấp 10** | Lv.49 | 240 | 65s | 5 | 130 x 130 | Giảm 3 giây thời gian bị bỏng: duy trì 55 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 55 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 55 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 55 giây, Giảm 1 giây thời gian bị choáng: duy trì 55 giây, Giảm 0.5 giây thời gian bị choáng cho đồng đội: duy trì 55 giây |
| **Cấp 11** | Lv.50 | 260 | 65s | 5 | 130 x 130 | Giảm 3 giây thời gian bị bỏng: duy trì 60 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 60 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 60 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 60 giây, Giảm 1 giây thời gian bị choáng: duy trì 60 giây, Giảm 0.5 giây thời gian bị choáng cho đồng đội: duy trì 60 giây |
| **Cấp 12** | Lv.51 | 280 | 65s | 6 | 140 x 140 | Giảm 3 giây thời gian bị bỏng: duy trì 70 giây, Giảm 2 giây thời gian bị bỏng cho đồng đội: duy trì 70 giây, Giảm 2 giây thời gian bị đóng băng: duy trì 70 giây, Giảm 1 giây thời gian bị đóng băng cho đồng đội: duy trì 70 giây, Giảm 1 giây thời gian bị choáng: duy trì 70 giây, Giảm 0.5 giây thời gian bị choáng cho đồng đội: duy trì 70 giây |

### Skill ID 53: Chiêu Toruneedo
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 6 điểm
* **Mô tả cốt truyện**: Sử dụng ảo phong thuật gia tăng tỉ lệ né tránh hoàn toàn tấn công của kẻ địch đồng thời gia tăng khả năng kháng băng của bản thân

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.45 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +5%, Kháng băng: +20, Tương khắc: +20 |
| **Cấp 2** | Lv.46 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +7%, Kháng băng: +35, Tương khắc: +35 |
| **Cấp 3** | Lv.47 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +9%, Kháng băng: +50, Tương khắc: +50 |
| **Cấp 4** | Lv.48 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +11%, Kháng băng: +65, Tương khắc: +65 |
| **Cấp 5** | Lv.49 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +13%, Kháng băng: +80, Tương khắc: +80 |
| **Cấp 6** | Lv.50 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Né tránh hoàn toàn tấn công: +15%, Kháng băng: +100, Tương khắc: +100 |

### Skill ID 54: Chiêu Tatsumaki
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Tạo ra các cơn gió lốc và sấm sét, gây sát thương lớn lên các mục tiêu nhất định, tối đa 4 mục tiêu

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.50 | 0 | 1s | 1 | 120 x 120 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.50 | 108 | 750ms | 2 | 120 x 120 | Xác xuất gây choáng: +3% duy trì 1 giây, Tấn công nội: +100%, Phong lôi: +200, Tấn công cơ bản: +280%, Tương khắc: +90 |
| **Cấp 2** | Lv.51 | 110 | 650ms | 2 | 120 x 120 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +110%, Phong lôi: +240, Tấn công cơ bản: +300%, Tương khắc: +95 |
| **Cấp 3** | Lv.52 | 112 | 630ms | 2 | 120 x 120 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +120%, Phong lôi: +280, Tấn công cơ bản: +320%, Tương khắc: +95 |
| **Cấp 4** | Lv.53 | 114 | 600ms | 2 | 120 x 120 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +130%, Phong lôi: +320, Tấn công cơ bản: +340%, Tương khắc: +100 |
| **Cấp 5** | Lv.54 | 116 | 550ms | 3 | 120 x 120 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +140%, Phong lôi: +360, Tấn công cơ bản: +360%, Tương khắc: +100 |
| **Cấp 6** | Lv.55 | 118 | 530ms | 3 | 120 x 120 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +150%, Phong lôi: +400, Tấn công cơ bản: +380%, Tương khắc: +105 |
| **Cấp 7** | Lv.56 | 120 | 500ms | 3 | 120 x 120 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +160%, Phong lôi: +440, Tấn công cơ bản: +400%, Tương khắc: +105 |
| **Cấp 8** | Lv.57 | 122 | 450ms | 3 | 120 x 120 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +170%, Phong lôi: +480, Tấn công cơ bản: +420%, Tương khắc: +110 |
| **Cấp 9** | Lv.58 | 124 | 450ms | 4 | 120 x 120 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +180%, Phong lôi: +520, Tấn công cơ bản: +440%, Tương khắc: +115 |
| **Cấp 10** | Lv.59 | 126 | 450ms | 4 | 120 x 120 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +190%, Phong lôi: +560, Tấn công cơ bản: +460%, Tương khắc: +120 |
| **Cấp 11** | Lv.60 | 128 | 400ms | 4 | 120 x 120 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +200%, Phong lôi: +600, Tấn công cơ bản: +480%, Tương khắc: +125 |
| **Cấp 12** | Lv.61 | 130 | 300ms | 4 | 120 x 120 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công nội: +210%, Phong lôi: +650, Tấn công cơ bản: +500%, Tương khắc: +130 |

### Skill ID 60: Chiêu Ooenjo
* **Loại chiêu**: Bị động (Tâm pháp / Nội tại ẩn)
* **Cấp kỹ năng tối đa**: 15 điểm
* **Mô tả cốt truyện**: Kêu gọi phong của tự nhiên dung nhập vào thân thể nâng cao hiệu quả hỗ trợ.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.60 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 15% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 2** | Lv.61 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 20% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 3** | Lv.62 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 25% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 4** | Lv.63 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 30% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 5** | Lv.64 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 35% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 6** | Lv.65 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 40% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 7** | Lv.66 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 45% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 8** | Lv.67 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 50% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 9** | Lv.68 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 55% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 10** | Lv.69 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 60% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 11** | Lv.70 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 65% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 12** | Lv.71 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 70% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 13** | Lv.72 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 80% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 14** | Lv.73 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 100% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |
| **Cấp 15** | Lv.74 | 0 | 0s (Bị động) | 1 | Bản thân / Ẩn | Tăng 150% hiệu của của chiêu cấp 15, cấp 35, cấp 40 |

### Skill ID 66: Chiêu Kougekitenrai
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Sử dụng bí thuật gia tốc phong lôi nguyên tố tạo ra vụ nổ ngay bản thân kẻ địch gây ra sát thương đồng thời có tỉ lệ gây choáng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.70 | 0 | 0s (Bị động) | 1 | 95 x 95 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.70 | 110 | 750ms | 1 | 95 x 95 | Xác xuất gây choáng: +3% duy trì 1 giây, Tấn công nội: +200%, Phong lôi: +575, Tấn công cơ bản: +340%, Tương khắc: +128 |
| **Cấp 2** | Lv.71 | 115 | 650ms | 1 | 95 x 95 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +210%, Phong lôi: +650, Tấn công cơ bản: +360%, Tương khắc: +130 |
| **Cấp 3** | Lv.72 | 120 | 630ms | 1 | 95 x 95 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +220%, Phong lôi: +725, Tấn công cơ bản: +380%, Tương khắc: +132 |
| **Cấp 4** | Lv.73 | 125 | 600ms | 2 | 95 x 95 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +230%, Phong lôi: +800, Tấn công cơ bản: +400%, Tương khắc: +134 |
| **Cấp 5** | Lv.74 | 130 | 550ms | 2 | 95 x 95 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +240%, Phong lôi: +875, Tấn công cơ bản: +420%, Tương khắc: +136 |
| **Cấp 6** | Lv.75 | 135 | 530ms | 2 | 95 x 95 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +250%, Phong lôi: +950, Tấn công cơ bản: +440%, Tương khắc: +138 |
| **Cấp 7** | Lv.76 | 140 | 500ms | 3 | 95 x 95 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +260%, Phong lôi: +1025, Tấn công cơ bản: +460%, Tương khắc: +140 |
| **Cấp 8** | Lv.77 | 145 | 450ms | 3 | 95 x 95 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +270%, Phong lôi: +1100, Tấn công cơ bản: +480%, Tương khắc: +142 |
| **Cấp 9** | Lv.78 | 150 | 450ms | 3 | 95 x 95 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +280%, Phong lôi: +1175, Tấn công cơ bản: +500%, Tương khắc: +144 |
| **Cấp 10** | Lv.79 | 155 | 450ms | 4 | 95 x 95 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +290%, Phong lôi: +1250, Tấn công cơ bản: +520%, Tương khắc: +146 |
| **Cấp 11** | Lv.80 | 160 | 400ms | 4 | 95 x 95 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +300%, Phong lôi: +1325, Tấn công cơ bản: +540%, Tương khắc: +148 |
| **Cấp 12** | Lv.81 | 165 | 300ms | 4 | 95 x 95 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công nội: +310%, Phong lôi: +1400, Tấn công cơ bản: +560%, Tương khắc: +150 |

### Skill ID 72: Nhẫn thuật Kage Bunshin
* **Loại chiêu**: Hỗ trợ / Buff bản thân & đồng đội
* **Cấp kỹ năng tối đa**: 10 điểm
* **Mô tả cốt truyện**: Nhẫn thuật cho phép tạo ra một bản sao hoàn hảo từ bản chính.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.90 | 2000 | 0s (Bị động) | 1 | Bản thân / Ẩn | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 10 phút, Sức đánh phân thân: 30% |
| **Cấp 2** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 15 phút, Sức đánh phân thân: 35% |
| **Cấp 3** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 20 phút, Sức đánh phân thân: 40% |
| **Cấp 4** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 25 phút, Sức đánh phân thân: 45% |
| **Cấp 5** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 30 phút, Sức đánh phân thân: 50% |
| **Cấp 6** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 40 phút, Sức đánh phân thân: 55% |
| **Cấp 7** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 50 phút, Sức đánh phân thân: 60% |
| **Cấp 8** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 60 phút, Sức đánh phân thân: 70% |
| **Cấp 9** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 120 phút, Sức đánh phân thân: 85% |
| **Cấp 10** | Lv.90 | 2000 | 60s | 1 | Bản thân / Ẩn | Phân thân tồn tại trong: 300 phút, Sức đánh phân thân: 100% |

### Skill ID 77: Chiêu Kokaze
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Triệu hồi lốc xoáy lôi điện ngay tại mục tiêu và hủy diệt chúng.

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.80 | 0 | 0s (Bị động) | 0 | 100 x 100 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.80 | 160 | 750ms | 2 | 100 x 100 | Xác xuất gây choáng: +3% duy trì 1 giây, Tấn công nội: +240%, Phong lôi: +1400, Tấn công cơ bản: +500%, Tương khắc: +136 |
| **Cấp 2** | Lv.81 | 163 | 650ms | 2 | 100 x 100 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +250%, Phong lôi: +1500, Tấn công cơ bản: +510%, Tương khắc: +140 |
| **Cấp 3** | Lv.82 | 166 | 630ms | 2 | 100 x 100 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +260%, Phong lôi: +1600, Tấn công cơ bản: +520%, Tương khắc: +144 |
| **Cấp 4** | Lv.83 | 169 | 600ms | 3 | 100 x 100 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +270%, Phong lôi: +1700, Tấn công cơ bản: +530%, Tương khắc: +148 |
| **Cấp 5** | Lv.84 | 172 | 550ms | 3 | 100 x 100 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +280%, Phong lôi: +1800, Tấn công cơ bản: +540%, Tương khắc: +152 |
| **Cấp 6** | Lv.85 | 175 | 530ms | 3 | 100 x 100 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +290%, Phong lôi: +1900, Tấn công cơ bản: +550%, Tương khắc: +156 |
| **Cấp 7** | Lv.86 | 178 | 500ms | 4 | 100 x 100 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +300%, Phong lôi: +2000, Tấn công cơ bản: +560%, Tương khắc: +160 |
| **Cấp 8** | Lv.87 | 181 | 450ms | 4 | 100 x 100 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +310%, Phong lôi: +2100, Tấn công cơ bản: +570%, Tương khắc: +164 |
| **Cấp 9** | Lv.88 | 184 | 450ms | 4 | 100 x 100 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +320%, Phong lôi: +2200, Tấn công cơ bản: +580%, Tương khắc: +168 |
| **Cấp 10** | Lv.89 | 187 | 450ms | 5 | 100 x 100 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +330%, Phong lôi: +2300, Tấn công cơ bản: +590%, Tương khắc: +172 |
| **Cấp 11** | Lv.90 | 190 | 400ms | 5 | 100 x 100 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +340%, Phong lôi: +2400, Tấn công cơ bản: +600%, Tương khắc: +176 |
| **Cấp 12** | Lv.91 | 193 | 300ms | 5 | 100 x 100 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công nội: +350%, Phong lôi: +2500, Tấn công cơ bản: +610%, Tương khắc: +180 |

### Skill ID 84: Chiêu Kamikaze
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 12 điểm
* **Mô tả cốt truyện**: Phiến thuật Phong Long Khiếu Thiên

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.100 | 0 | 0s (Bị động) | 0 | 120 x 100 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.100 | 177 | 750ms | 3 | 120 x 100 | Xác xuất gây choáng: +3% duy trì 1 giây, Tấn công nội: +270%, Phong lôi: +2700, Tấn công cơ bản: +520%, Tương khắc: +178 |
| **Cấp 2** | Lv.101 | 180 | 650ms | 3 | 120 x 100 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +280%, Phong lôi: +2800, Tấn công cơ bản: +530%, Tương khắc: +180 |
| **Cấp 3** | Lv.102 | 183 | 630ms | 3 | 120 x 100 | Xác xuất gây choáng: +4% duy trì 1 giây, Tấn công nội: +290%, Phong lôi: +2900, Tấn công cơ bản: +540%, Tương khắc: +182 |
| **Cấp 4** | Lv.103 | 186 | 600ms | 3 | 120 x 100 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +300%, Phong lôi: +3000, Tấn công cơ bản: +550%, Tương khắc: +184 |
| **Cấp 5** | Lv.104 | 189 | 550ms | 4 | 120 x 100 | Xác xuất gây choáng: +5% duy trì 1 giây, Tấn công nội: +310%, Phong lôi: +3100, Tấn công cơ bản: +560%, Tương khắc: +186 |
| **Cấp 6** | Lv.105 | 192 | 530ms | 4 | 120 x 100 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +320%, Phong lôi: +3200, Tấn công cơ bản: +570%, Tương khắc: +188 |
| **Cấp 7** | Lv.106 | 195 | 500ms | 4 | 120 x 100 | Xác xuất gây choáng: +6% duy trì 1 giây, Tấn công nội: +330%, Phong lôi: +3300, Tấn công cơ bản: +580%, Tương khắc: +190 |
| **Cấp 8** | Lv.107 | 198 | 450ms | 4 | 120 x 100 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +340%, Phong lôi: +3400, Tấn công cơ bản: +590%, Tương khắc: +192 |
| **Cấp 9** | Lv.108 | 201 | 450ms | 5 | 120 x 100 | Xác xuất gây choáng: +7% duy trì 1 giây, Tấn công nội: +350%, Phong lôi: +3500, Tấn công cơ bản: +600%, Tương khắc: +194 |
| **Cấp 10** | Lv.109 | 204 | 450ms | 5 | 120 x 100 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +360%, Phong lôi: +3600, Tấn công cơ bản: +620%, Tương khắc: +196 |
| **Cấp 11** | Lv.110 | 207 | 400ms | 5 | 120 x 100 | Xác xuất gây choáng: +8% duy trì 1 giây, Tấn công nội: +380%, Phong lôi: +3800, Tấn công cơ bản: +640%, Tương khắc: +198 |
| **Cấp 12** | Lv.111 | 210 | 300ms | 6 | 120 x 100 | Xác xuất gây choáng: +9% duy trì 1 giây, Tấn công nội: +400%, Phong lôi: +4000, Tấn công cơ bản: +680%, Tương khắc: +200 |

### Skill ID 90: Thuật Phiến Cuồng Phong
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 1 điểm
* **Mô tả cốt truyện**: Phiến thuật Phong Lôi - Triệu hồi thiên lôi tấn công thẳng vào mục tiêu gây ra sát thương cực lớn

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.120 | 0 | 0s (Bị động) | 0 | 150 x 150 | *(Chưa có hiệu ứng ở cấp 0)* |
| **Cấp 1** | Lv.120 | 300 | 350ms | 7 | 150 x 150 | Xác xuất gây choáng: +11% duy trì 1 giây, Tấn công nội: +580%, Tấn công nội: +5000%, Tấn công cơ bản: +780%, Tương khắc: +350 |

---

## 8. PHÁI 0: CHƯA VÀO TRƯỜNG (KỸ NĂNG CƠ BẢN)

* Kỹ năng nhập môn ban đầu dành cho tân thủ khi chưa gia nhập học viện ninja.

### Skill ID 0: Chiêu cơ bản
* **Loại chiêu**: Chủ động tấn công
* **Cấp kỹ năng tối đa**: 0 điểm
* **Mô tả cốt truyện**: Đòn đánh cơ bản sử dụng sức mạnh bản thân gây sát thương lên kẻ địch

| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |
| :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| Cấp 0 *(Gốc)* | Lv.1 | 0 | 500ms | 1 | 28 x 18 | Tấn công ngoại: +10% |

---

