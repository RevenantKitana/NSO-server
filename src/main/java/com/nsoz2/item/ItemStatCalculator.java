package com.nsoz2.item;

import com.nsoz2.util.NinjaUtils;

/**
 * Bộ tính toán nội suy thuộc tính trang bị theo 5 Tier rời rạc {A1 -> A5}
 * Theo tài liệu đặc tả Spec 08 (NSO Game Server Modernization V2).
 */
public final class ItemStatCalculator {

    public static final byte TIER_1_COMMON = 1;       // A1 - Phổ thông (min)
    public static final byte TIER_2_REFINED = 2;      // A2 - Tinh luyện (min + 25%)
    public static final byte TIER_3_SUPERIOR = 3;     // A3 - Thượng phẩm (trung vị 50%, chuẩn đại trà)
    public static final byte TIER_4_EPIC = 4;         // A4 - Siêu cấp (min + 75%)
    public static final byte TIER_5_PERFECT = 5;      // A5 - Hoàn mỹ (max 100%, kịch trần)

    public static final String[] TIER_NAMES = {
        "",
        "Phổ thông (A1)",
        "Tinh luyện (A2)",
        "Thượng phẩm (A3)",
        "Siêu cấp (A4)",
        "Hoàn mỹ (A5)"
    };

    private ItemStatCalculator() {}

    /**
     * Lấy tên mô tả phẩm chất của Tier
     */
    public static String getTierName(byte tier) {
        if (tier >= 1 && tier <= 5) {
            return TIER_NAMES[tier];
        }
        return TIER_NAMES[TIER_3_SUPERIOR];
    }

    /**
     * Thuật toán nội suy tuyến tính 5 mốc theo Spec 08:
     * Value(t) = round(min + (max - min) * (tier - 1) / 4)
     *
     * @param min Giá trị chỉ số tối thiểu (Tier 1)
     * @param max Giá trị chỉ số tối đa (Tier 5)
     * @param tier Phẩm chất từ 1 đến 5
     * @return Giá trị chỉ số rời rạc đã làm tròn
     */
    public static int calculate(int min, int max, byte tier) {
        if (tier <= TIER_1_COMMON) {
            return min;
        }
        if (tier >= TIER_5_PERFECT) {
            return max;
        }
        if (min == max) {
            return min;
        }

        double step = (double) (max - min) / 4.0;
        return (int) Math.round(min + (tier - 1) * step);
    }

    /**
     * Xác suất rơi đồ quái thường:
     * - A1 (Phổ thông): 40%
     * - A2 (Tinh luyện): 35%
     * - A3 (Thượng phẩm): 18%
     * - A4 (Siêu cấp): 6%
     * - A5 (Hoàn mỹ): 1%
     */
    public static byte rollTierForNormalMob() {
        int roll = NinjaUtils.nextInt(1, 100);
        if (roll <= 40) {
            return TIER_1_COMMON;
        }
        if (roll <= 75) {
            return TIER_2_REFINED;
        }
        if (roll <= 93) {
            return TIER_3_SUPERIOR;
        }
        if (roll <= 99) {
            return TIER_4_EPIC;
        }
        return TIER_5_PERFECT;
    }

    /**
     * Xác suất rơi đồ Boss / Tinh anh:
     * - A1 (Phổ thông): 10%
     * - A2 (Tinh luyện): 25%
     * - A3 (Thượng phẩm): 40%
     * - A4 (Siêu cấp): 20%
     * - A5 (Hoàn mỹ): 5%
     */
    public static byte rollTierForBoss() {
        int roll = NinjaUtils.nextInt(1, 100);
        if (roll <= 10) {
            return TIER_1_COMMON;
        }
        if (roll <= 35) {
            return TIER_2_REFINED;
        }
        if (roll <= 75) {
            return TIER_3_SUPERIOR;
        }
        if (roll <= 95) {
            return TIER_4_EPIC;
        }
        return TIER_5_PERFECT;
    }

    /**
     * Xác suất chế tạo / rèn làng Kojin:
     * - A1 (Phổ thông): 5%
     * - A2 (Tinh luyện): 20%
     * - A3 (Thượng phẩm): 45%
     * - A4 (Siêu cấp): 25%
     * - A5 (Hoàn mỹ): 5%
     */
    public static byte rollTierForCraft() {
        int roll = NinjaUtils.nextInt(1, 100);
        if (roll <= 5) {
            return TIER_1_COMMON;
        }
        if (roll <= 25) {
            return TIER_2_REFINED;
        }
        if (roll <= 70) {
            return TIER_3_SUPERIOR;
        }
        if (roll <= 95) {
            return TIER_4_EPIC;
        }
        return TIER_5_PERFECT;
    }
}

