package com.nsoz2.combat.engine;

/**
 * Bộ tính toán Miễn Thương & Xuyên Kháng Nhân Tích (Multiplicative Stacking Engine).
 * Đặc tả theo docs/rework/09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md:
 * - Triệt tiêu 100% bug bất tử: Không cộng dồn tuyến tính (sum r_i).
 * - Multiplier = Product(1 - r_i).
 * - Áp dụng quy tắc "Xuyên % trước, trừ điểm Flat sau" và "Trừ điểm Flat trước, nhân % sau".
 */
public final class DamageReductionCalculator {

    private DamageReductionCalculator() {
        // Utility class
    }

    /**
     * Tính toán hệ số nhân sát thương từ các nguồn miễn thương (%)
     * @param reductionRates Mảng các tỷ lệ giảm thương thập phân (ví dụ: 0.10 cho 10%, 0.12 cho 12%)
     * @return Hệ số sát thương thực tế phải nhận (ví dụ: 0.792)
     */
    public static double calculateDamageMultiplier(double... reductionRates) {
        double multiplier = 1.0;
        if (reductionRates == null || reductionRates.length == 0) {
            return multiplier;
        }
        for (double rate : reductionRates) {
            if (rate > 0.0) {
                multiplier *= Math.max(0.0, 1.0 - rate);
            }
        }
        return Math.max(0.0, multiplier);
    }

    /**
     * Tính toán hệ số kháng còn lại sau khi trừ các nguồn xuyên kháng (%)
     * @param penetrationRates Mảng tỷ lệ xuyên kháng thập phân (ví dụ: 0.25 cho 25%)
     * @return Hệ số kháng còn lại (ví dụ: 0.75)
     */
    public static double calculateRemainingResistanceMultiplier(double... penetrationRates) {
        double multiplier = 1.0;
        if (penetrationRates == null || penetrationRates.length == 0) {
            return multiplier;
        }
        for (double rate : penetrationRates) {
            if (rate > 0.0) {
                multiplier *= Math.max(0.0, 1.0 - rate);
            }
        }
        return Math.max(0.0, multiplier);
    }

    /**
     * Tính kháng thực tế sau khi chịu xuyên kháng % và xuyên kháng điểm.
     * Quy tắc: Xuyên % trước (trị Tanker), trừ điểm Flat sau (trị Máu giấy).
     * Kháng Cuối = max(0, [Kháng Gốc * Product(1 - p_i)] - Xuyên Kháng Điểm)
     */
    public static int applyResistanceAndPenetration(int rawResistance, int flatPenetration, double... percentPenetrations) {
        if (rawResistance <= 0) {
            return 0;
        }
        double remainingMult = calculateRemainingResistanceMultiplier(percentPenetrations);
        double afterPercent = (double) rawResistance * remainingMult;
        int finalRes = (int) Math.round(afterPercent - flatPenetration);
        return Math.max(0, finalRes);
    }

    /**
     * Áp dụng giảm trừ sát thương Flat và Miễn thương % Nhân tích vào sát thương cuối cùng.
     * Sát thương = max(0, (Sát thương gốc - FlatDameDown) * Product(1 - r_i))
     */
    public static int applyDamageReduction(int incomingDamage, int flatReduction, double... percentReductions) {
        if (incomingDamage <= 0) {
            return 0;
        }
        int afterFlat = Math.max(0, incomingDamage - flatReduction);
        double multiplier = calculateDamageMultiplier(percentReductions);
        int finalDamage = (int) Math.round(afterFlat * multiplier);
        return Math.max(0, finalDamage);
    }
}

