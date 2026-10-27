package com.nsoz.combat.engine;

/**
 * Bộ máy tính toán sát thương chuẩn hóa mới (Simplified Damage Engine).
 * Đặc tả theo TAI_LIEU_THIET_KE_CONG_THUC_SAT_THUONG.md:
 * - Tinh giản tối đa: Quy tụ về 3 biến số trang bị (TC, VC, STC) + 1 biến số nhân vật (TN).
 * - Cụm 1 (Lực đánh cơ sở) = Tong TC + TN * T * (1 + VC / 100)
 * - Cụm 2 (Tỉ lệ thưởng %)  = (TN / (K - Lv / 100)) / 100
 * - He so nhan              = 1 + Cum 2
 * - Dame he                 = Cum 1 * He so nhan
 * - Tong Dame               = Dame he + STC (STC được CombatService cộng sau cùng)
 */
public class NewDamageEngine {

    // Hằng số cân bằng chống lạm phát (Khuyên dùng: 25.0 theo tài liệu đặc tả)
    public static final double K_BALANCE = 25.0;

    /**
     * Lấy hằng số tiềm năng T đặc trưng cho từng môn phái.
     * @param classId Mã môn phái của nhân vật (0: Chưa vào phái, 1..6: Kiếm, Tiêu, Kunai, Cung, Đao, Quạt)
     * @return Hệ số T
     */
    public static double getClassMultiplier(byte classId) {
        switch (classId) {
            case 1: // Kiếm (Hỏa - Ngoại)
                return 1.3;
            case 2: // Tiêu (Hỏa - Nội)
                return 1.2;
            case 3: // Kunai (Băng - Ngoại)
                return 1.2;
            case 4: // Cung (Băng - Nội)
                return 1.3;
            case 5: // Đao (Phong - Ngoại)
                return 1.4;
            case 6: // Quạt (Phong - Nội)
                return 1.3;
            default:
                return 1.0; // Tân thủ / Vô phái
        }
    }

    /**
     * Tính toán tổng sát thương hệ theo mô hình 2 cụm chuẩn hóa.
     *
     * @param level   Cấp độ người chơi (char.level)
     * @param totalTC Tổng điểm tấn công cố định (Flat Attack)
     * @param totalTN Điểm tiềm năng tăng tấn công hiệu dụng (Sức khỏe cho Ngoại, Chakra cho Nội)
     * @param classT  Hằng số tiềm năng đặc trưng môn phái
     * @param totalVC Tổng phần trăm vận công (% Scaling)
     * @return Sát thương hệ sau khi tính toán (tối thiểu là 1)
     */
    public static int calculateElementalDamage(int level, int totalTC, int totalTN, 
                                              double classT, int totalVC) {
        // Cụm 1: Lực đánh cơ sở = Tong TC + TN * T * (1 + VC / 100)
        double potentialBonus = (double) totalTN * classT * (1.0 + (double) totalVC / 100.0);
        double baseAttack = (double) totalTC + potentialBonus;

        // Cụm 2: Tỉ lệ thưởng % chống lạm phát = (TN / (K - Lv / 100)) / 100
        double denominator = K_BALANCE - ((double) level / 100.0);
        if (denominator <= 1.0) {
            denominator = 1.0; // Chốt an toàn chống chia cho số quá nhỏ hoặc <= 0
        }
        double bonusRatePercent = (double) totalTN / denominator; // Đơn vị: %
        double multiplier = 1.0 + (bonusRatePercent / 100.0);

        // Dame hệ = Cụm 1 * Hệ số nhân
        int finalDamage = (int) Math.round(baseAttack * multiplier);

        return Math.max(1, finalDamage);
    }
}
