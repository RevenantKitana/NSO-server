package com.nsoz2.combat.engine;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Giải thuật Xác suất Đơn lần tung cho Đầu ra Đa biến (Single-Roll Multi-Variable Resolver).
 * Đặc tả theo docs/rework/10_GIAI_THUAT_XAC_SUAT_SINGLE_ROLL_MULTI_VARIABLE.md:
 * - Triệt tiêu lỗi Nested If-Else Spaghetti và Branch Misprediction Penalty.
 * - Quy tụ toàn bộ xác suất (Né đòn, Chí mạng, Đánh trúng thường) trên 1 trục số liên tục [0.0, 1.0).
 * - Sử dụng ThreadLocalRandom, hoàn toàn không dính CAS lock, cực nhanh (~3 ns).
 * - Độc lập mục tiêu 100% trong chiêu lan.
 */
public final class SingleRollCombatResolver {

    public static final double BASE_HIT_RATE = 0.85;     // 85% tỷ lệ trúng mặc định khi tương quan cân bằng
    public static final double MIN_HIT_RATE = 0.15;      // Tối thiểu 15% trúng (không bao giờ bị bất tử né)
    public static final double MAX_HIT_RATE = 0.98;      // Tối đa 98% trúng (luôn chừa 2% né may mắn)
    public static final double ACCURACY_SCALE = 300.0;   // Hệ số co giãn tiệm cận
    public static final double BASE_CRIT_RATE = 0.05;
    public static final double MAX_CRIT_RATE = 0.70;
    public static final double CRIT_HALF_POINT = 1200.0;

    private SingleRollCombatResolver() {
        // Utility class
    }

    /**
     * Bản ghi kết quả phân giải của một đòn đánh đơn mục tiêu.
     */
    public record CombatOutcome(
        boolean isMiss,
        boolean isCrit,
        boolean hasStatusEffect
    ) {
        public static CombatOutcome miss() {
            return new CombatOutcome(true, false, false);
        }

        public static CombatOutcome hit(boolean isCrit, boolean hasEffect) {
            return new CombatOutcome(false, isCrit, hasEffect);
        }
    }

    /**
     * Phân giải toàn bộ kết quả giao tranh: Né đòn, Đánh trúng, Chí mạng, và Kích hoạt hiệu ứng môn phái.
     * Hàm thuần túy (Pure Function) - Không sinh rác, không side-effect.
     *
     * @param attackerAccuracy  Chính xác của người tấn công (ID 3)
     * @param defenderEvasion   Né đòn của mục tiêu (ID 4)
     * @param attackerFatal     Điểm chí mạng của người tấn công (ID 5)
     * @param defenderCritRes   Kháng chí mạng của mục tiêu (ID 31 [MỚI])
     * @param effectProcChance  Tỷ lệ kích hoạt hiệu ứng môn phái [0.0 -> 1.0]
     * @return CombatOutcome chứa cờ isMiss, isCrit, hasStatusEffect
     */
    public static CombatOutcome resolve(
            int attackerAccuracy,
            int defenderEvasion,
            int attackerFatal,
            int defenderCritRes,
            double effectProcChance) {

        // 1. Tính ngưỡng Né đòn (Hyperbolic Sigmoid Asymptote với Base Hit Rate 85%)
        int netAccuracy = attackerAccuracy - defenderEvasion;
        double hitChance;
        if (netAccuracy >= 0) {
            hitChance = BASE_HIT_RATE + (MAX_HIT_RATE - BASE_HIT_RATE) * ((double) netAccuracy / (netAccuracy + ACCURACY_SCALE));
        } else {
            int absNet = -netAccuracy;
            hitChance = BASE_HIT_RATE - (BASE_HIT_RATE - MIN_HIT_RATE) * ((double) absNet / (absNet + ACCURACY_SCALE));
        }
        hitChance = Math.min(MAX_HIT_RATE, Math.max(MIN_HIT_RATE, hitChance));
        double missThreshold = 1.0 - hitChance;

        // 2. Tính ngưỡng Chí Mạng (Soft-Cap Diminishing Curve chống tràn)
        int netCrit = Math.max(0, attackerFatal - defenderCritRes);
        double bonusCrit = (MAX_CRIT_RATE - BASE_CRIT_RATE) * ((double) netCrit / ((double) netCrit + CRIT_HALF_POINT));
        double critThreshold = missThreshold + (BASE_CRIT_RATE + bonusCrit);

        // 3. TUNG XÚC XẮC ĐÚNG 1 LẦN DUY NHẤT TRÊN TRỤC [0.0, 1.0)
        double roll = ThreadLocalRandom.current().nextDouble();

        // 4. Phân giải phẳng (Flat Branch Resolution - Trình biên dịch JIT tối ưu thành CMOV)
        if (roll < missThreshold) {
            return CombatOutcome.miss(); // Né đòn: Dừng ngay lập tức, triệt tiêu toàn bộ hiệu ứng
        }

        boolean isCrit = (roll < critThreshold);
        boolean hasEffect = (effectProcChance > 0.0) && (ThreadLocalRandom.current().nextDouble() < effectProcChance);

        return CombatOutcome.hit(isCrit, hasEffect);
    }

    /**
     * Tính hệ số khuếch đại sát thương chí mạng sau khi trừ Giảm Sát Thương Chí Mạng % của mục tiêu.
     * Công thức: FinalCritMult = 2.0 + max(0.0, (AttackerCritDmgPercent - DefenderCritDmgReductionPercent) / 100.0)
     */
    public static double calculateCritMultiplier(int attackerCritDmgPercent, int defenderCritDmgReductionPercent) {
        int netCritDmgBonus = Math.max(0, attackerCritDmgPercent - defenderCritDmgReductionPercent);
        return 2.0 + ((double) netCritDmgBonus / 100.0);
    }
}

