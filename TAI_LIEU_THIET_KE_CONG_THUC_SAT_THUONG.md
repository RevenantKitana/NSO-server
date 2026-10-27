# TÀI LIỆU ĐẶC TẢ THIẾT KẾ HỆ THỐNG SÁT THƯƠNG & CHỈ SỐ MỚI
### (SIMPLIFIED COMBAT STATS & DAMAGE ENGINE SPECIFICATION)

> **Dự án**: NSO Ninja School Private Server 2026  
> **Trạng thái**: Đã triển khai chính thức vào Codebase (Production Implemented)  
> **Nguyên tắc cốt lõi**: Tinh giản tối đa - Triệt tiêu chỉ số râu ria - Chống lạm phát & One-shot  

---

## 1. MỤC TIÊU & TRIẾT LÝ THIẾT KẾ

1. **Thanh lọc toàn diện (Stat Pruning):** 
   - Lược bỏ hoàn toàn tình trạng một thuộc tính (như Ngoại công, Nội công, Né đòn, HP...) bị phân tán thành 5 - 8 mã Option ID khác nhau do tàn dư lịch sử cập nhật tính năng.
   - Toàn bộ trang bị, thú cưỡi, ngọc khảm quy tụ về một bảng mã chuẩn duy nhất (**Canonical Options**).
2. **Quy tụ về 3 Biến Số Trang Bị + 1 Biến Số Nhân Vật:**
   - Thay thế phép tính cộng dồn phức tạp và lộn xộn trong engine cũ bằng một mô hình toán học rõ ràng, minh bạch, dễ kiểm soát.
3. **Cân bằng bền vững (Anti-Inflation & Anti-OneShot):**
   - Đảm bảo sát thương tăng trưởng mượt mà từ Level 1 đến Level 130+.
   - Kìm hãm hiện tượng bùng nổ sát thương khi người chơi dồn đồ VIP ở giai đoạn cuối game.
   - Tạo giá trị thực sự cho **Sát Thương Thuần (STC)** và **Tấn Công Cơ Bản (TC)**.

---

## 2. KẾ HOẠCH LƯỢC BỎ CHỈ SỐ RÂU RIA & QUY CHUẨN MÃ CHUẨN

Toàn bộ các mã Option râu ria, trùng lặp về mặt bản chất toán học được quy chuẩn về các mã đại diện duy nhất:

### 2.1. Bảng quy tụ chỉ số chiến đấu:

| Bản chất thuộc tính | Các mã cũ râu ria / trùng lặp | Mã Chuẩn Giữ Lại | Ghi chú kỹ thuật |
| :--- | :--- | :---: | :--- |
| **Tấn công Ngoại công** | 21, 23, 25, 73, 76, 87 | **Option 0** | Dùng chung cho vũ khí, trang sức, thú, ngọc |
| **Tấn công Nội công** | 22, 24, 26 | **Option 1** | Dùng chung cho đồ nội công |
| **% Vận công Ngoại** | 8 | **Option 8** | Giữ nguyên vai trò đòn bẩy cho Sức Khỏe |
| **% Vận công Nội** | 9 | **Option 9** | Giữ nguyên vai trò đòn bẩy cho Chakra |
| **Sát thương chuẩn** | 113 | **Option 113** | Sát thương thuần (STC) - bỏ qua phòng ngự |
| **Kháng Hỏa / Băng / Phong**| 11, 70, 96 / 71, 95 / 13, 72, 97 | **Option 2, 3, 4** | Khai tử hoàn toàn mã 12 bị lỗi logic |
| **Kháng tất cả** | 81, 118 | **Option 20** | Quy về mã chuẩn duy nhất |
| **Máu tối đa (HP cố định)** | 77, 82, 125 | **Option 6** | Quy về mã chuẩn duy nhất |
| **Né đòn cố định** | 17, 68, 78, 84, 115 | **Option 5** | Quy về mã chuẩn duy nhất |
| **Chính xác cố định** | 18, 75, 86, 116 | **Option 10** | Quy về mã chuẩn duy nhất |
| **Chí mạng cố định** | 69, 92, 114 | **Option 14** | Quy về mã chuẩn duy nhất |
| **Giảm trừ sát thương** | 74, 80, 124 | **Option 47** | Quy về dòng dameDown chuẩn |

> *(Lưu ý: Các dòng kích hoạt cường hóa mang Type 3..7 như Option 38, 31, 28, 39... bắt buộc giữ nguyên mã để Client nhận diện đổi màu chữ xanh lá).*

---

## 3. BỘ 3+1 BIẾN SỐ CỐT LÕI (CORE PARAMETERS & ADAPTER ARCHITECTURE)

Hệ thống tách biệt hoàn toàn **Tầng Tính Toán (Logic)** và **Tầng Hiển Thị (UI/Client)**:
- **Tầng Hiển thị (Client UI)**: Tùy theo hệ phái hoặc thuộc tính trang bị mà hiển thị tên nhãn tương ứng ( Tấn công ngoại / Vật công ngoại % cho Kiếm/Đao/Kunai; Tấn công nội / Vật công nội % cho Tiêu/Cung/Quạt).
- **Tầng Tính toán (Engine)**: Gom toàn bộ vào đúng **3 biến số trang bị** + **1 biến số nhân vật**:

1. **LV (Cấp độ người chơi):** Level thực tế của nhân vật (char.level).
2. **TC (Tấn công - Flat Attack):** Tổng điểm tấn công cộng thẳng từ Vũ khí, Trang sức, Cường hóa (+12 Opt 38), Chiêu thức, Nguyên tố hệ. Tùy theo phái (Adapter):
   - Phái Ngoại (sideClass == 0): Rút từ StatType.PHYSICAL_ATTACK.
   - Phái Nội (sideClass == 1): Rút từ StatType.MAGIC_ATTACK.
3. **VC (Vận công - % Scaling):** Tổng phần trăm Vận công khuếch đại từ Trang bị, Dây chuyền, Bí kíp, Buff Long Lực Đan.
   - Phái Ngoại: Rút từ % PHYSICAL_ATTACK + Hỗ trợ Ngoại % (Option 0).
   - Phái Nội: Rút từ % MAGIC_ATTACK + Hỗ trợ Nội % (Option 1).
4. **TN (Tiềm năng tăng tấn công hiệu dụng):** 
   - Với phái Ngoại công (Kiếm, Kunai, Đao): Là điểm **Sức Khỏe** (potential[0]).
   - Với phái Nội công (Tiêu, Cung, Quạt): Là điểm **Chakra** (potential[3]).
   - *(Đã tính đủ bonus Option 57 và Option 58)*.
5. **T (Hằng số tiềm năng đặc trưng môn phái):** Hệ số định hình lối chơi riêng biệt của từng phái.
   - **Đao (Phong):** T = 1.4 (Đòn đánh đầm, sát thương dứt khoát).
   - **Kiếm (Hỏa):** T = 1.3 (Sát thương bùng nổ, thiêu đốt).
   - **Kunai (Băng):** T = 1.2 (Đòn đánh nhanh, cơ động, né đòn cao).
   - **Cung (Băng):** T = 1.3 (Bắn tỉa tầm xa, đóng băng).
   - **Tiêu (Hỏa):** T = 1.2 (Ám khí liên hoàn, rỉa máu).
   - **Quạt (Phong):** T = 1.3 (Nội công biến ảo, hỗ trợ đồng đội).
   - **Tân thủ / Vô phái:** T = 1.0.
6. **STC (Sát thương chuẩn - True Damage):** Sát thương trừ thẳng vào bình máu, không chịu ảnh hưởng của Giáp, Kháng hay trạng thái bất lợi (Lấy từ Option 113). Được CombatService cộng sau cùng sau khi trừ giáp.

---

## 4. CÔNG THỨC TÍNH SÁT THƯƠNG ĐẦU RA

### 4.1. Công thức tổng quát:

`	ext
Tong Dame = Dame he + STC
`

### 4.2. Công thức tính Sát thương Hệ (Dame hệ):

Công thức được phân tách thành 2 cụm xử lý rõ ràng:

`	ext
Cum 1 (Lực đánh cơ sở) = Tong TC + TN * T * (1 + VC / 100)

Cum 2 (Tỉ lệ thưởng %)  = (TN / (K - Lv / 100)) / 100

He so nhan              = 1 + Cum 2

Dame he                 = Cum 1 * He so nhan
`

*(Trong đó K là Hằng số cân bằng chống lạm phát).*

---

## 5. PHÂN TÍCH HẰNG SỐ CÂN BẰNG (K = 25 vs K = 41)

Hằng số K ở mẫu số là chốt phanh kiểm soát tốc độ tăng trưởng sát thương ở giai đoạn cuối game:

### Bảng đối chiếu định lượng:

| Cấp (Lv) | Tiềm năng (TN) | Mẫu số (K=25) | Mẫu số (K=41) | Hệ số nhân khi K=25 | Hệ số nhân khi K=41 |
| :---: | :---: | :---: | :---: | :---: | :---: |
| **Cấp 20** | 200 | 24.8 | 40.8 | **1.08 lần** (+8%) | **1.05 lần** (+5%) |
| **Cấp 60** | 800 | 24.4 | 40.4 | **1.33 lần** (+33%) | **1.20 lần** (+20%) |
| **Cấp 100** | 1.800 | 24.0 | 40.0 | **1.75 lần** (+75%) | **1.45 lần** (+45%) |
| **Cấp 130** | 3.000 | 23.7 | 39.7 | **2.27 lần** (+127%) | **1.76 lần** (+76%) |
| **Max VIP (Bùa TN)** | 5.000 | 23.7 | 39.7 | **3.11 lần** (+211%) | **2.26 lần** (+126%) |

### Định hướng lựa chọn:
* **Chọn K = 25 (Khuyên dùng cho server cày cuốc tiêu chuẩn - Hiện đang áp dụng):** 
  - Mang lại cảm giác tăng tiến sức mạnh rõ ràng khi đập đồ VIP.
  - Sát thương cấp 130 đạt khoảng **20.000 - 27.000 dame**, cân bằng tuyệt đẹp với các mốc máu 60.000 - 90.000 HP.
* **Chọn K = 41 (Khuyên dùng cho server đấu trường lôi đài / Hardcore PK):**
  - Nhịp độ trận đấu kéo dài, người chơi có thời gian xoay xở combo chiêu thức và né đòn.
  - Là lá chắn thép tuyệt đối an toàn nếu server dự kiến phát hành nhiều loại Bùa / Trang bị tăng % tiềm năng cao.

---

## 6. BẢNG MÔ PHỎNG DỮ LIỆU CHI TIẾT (SIMULATION)

*(Giả định chọn K = 25, Hằng số phái T = 1.2, chưa tính STC)*

| Giai đoạn | Cấp (Lv) | Tổng TC | Tiềm năng (TN) | Vận công (VC) | Lực đánh cơ sở (Cụm 1) | Thưởng % (Cụm 2) | Sát thương đầu ra (Dame hệ) | Máu (HP) trung bình | Số hit hạ gục |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Tân thủ** | 20 | 300 | 200 | 15% | 576 | +8.1% | **622** | 2.000 HP | 3 - 4 hit |
| **Trung cấp** | 60 | 1.000 | 800 | 25% | 2.200 | +32.8% | **2.921** | 12.000 HP | 4 - 5 hit |
| **Cao thủ** | 100 | 2.500 | 1.800 | 35% | 5.416 | +75.0% | **9.478** | 40.000 HP | 4 - 5 hit |
| **Max Cấp** | 130 | 4.000 | 3.000 | 50% | 9.400 | +126.6% | **21.300** | 75.000 HP | 3 - 4 hit |

👉 **Nhận xét:** Xuyên suốt từ cấp 20 đến 130, thời gian hạ gục đối thủ luôn duy trì ổn định ở mức **3 đến 5 đòn đánh chuẩn xác**, hoàn toàn không có hiện tượng 1 hit chết ngay, kích thích tư duy thi đấu kỹ năng.

---

## 7. CÀI ĐẶT JAVA THỰC TẾ (ENGINE IMPLEMENTATION)

Lớp [NewDamageEngine.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/engine/NewDamageEngine.java) đã được cài đặt và tích hợp trực tiếp vào [AbilityFromEquip.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/ability/AbilityFromEquip.java):

`java
package com.nsoz.combat.engine;

public class NewDamageEngine {

    // Hằng số cân bằng chống lạm phát (Khuyên dùng: 25.0)
    public static final double K_BALANCE = 25.0;

    public static double getClassMultiplier(byte classId) {
        switch (classId) {
            case 1: return 1.3; // Kiếm (Hỏa)
            case 2: return 1.2; // Tiêu (Hỏa)
            case 3: return 1.2; // Kunai (Băng)
            case 4: return 1.3; // Cung (Băng)
            case 5: return 1.4; // Đao (Phong)
            case 6: return 1.3; // Quạt (Phong)
            default: return 1.0;
        }
    }

    public static int calculateElementalDamage(int level, int totalTC, int totalTN, 
                                              double classT, int totalVC) {
        // Cụm 1: Lực đánh cơ sở = Tong TC + TN * T * (1 + VC / 100)
        double potentialBonus = (double) totalTN * classT * (1.0 + (double) totalVC / 100.0);
        double baseAttack = (double) totalTC + potentialBonus;

        // Cụm 2: Tỉ lệ thưởng % chống lạm phát = (TN / (K - Lv / 100)) / 100
        double denominator = K_BALANCE - ((double) level / 100.0);
        if (denominator <= 1.0) {
            denominator = 1.0;
        }
        double bonusRatePercent = (double) totalTN / denominator;
        double multiplier = 1.0 + (bonusRatePercent / 100.0);

        // Dame hệ = Cụm 1 * Hệ số nhân
        int finalDamage = (int) Math.round(baseAttack * multiplier);
        return Math.max(1, finalDamage);
    }
}
`

---

## 8. TRẠNG THÁI TRIỂN KHAI

1. **Bước 1 (Source Code - Hoàn tất):**
   - Tạo mới [NewDamageEngine.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/combat/engine/NewDamageEngine.java).
   - Tích hợp trực tiếp vào [AbilityFromEquip.java](file:///e:/TT/SETUP_LOCAL/SRC_GAME/src/main/java/com/nsoz/ability/AbilityFromEquip.java) qua phương thức calculateDamage().
2. **Bước 2 (OptionMapper - Hoàn tất):**
   - Phân định rõ ràng: Option 0 thuần túy là Ngoại công (PHYSICAL_ATTACK), Option 1 thuần túy là Nội công (MAGIC_ATTACK).
   - Các Option tấn công chung (73, 76, 87) tự động thích ứng theo phái.
   - Xóa bỏ triệt để hiện tượng phái Nội ăn kép cả 2 dòng trên vũ khí.
3. **Bước 3 (Biên dịch & Kiểm chứng):**
   - Maven build thành công (BUILD SUCCESS). Toàn bộ hệ thống sẵn sàng hoạt động.