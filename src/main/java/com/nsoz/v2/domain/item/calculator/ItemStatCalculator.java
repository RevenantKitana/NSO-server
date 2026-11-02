package com.nsoz.v2.domain.item.calculator;

/**
 * 5-Tier Discrete Linear Interpolation Calculator for NSO V2 Items.
 * Replaces infinite random rolls with 5 standardized quality tiers:
 * A1 (Phổ thông), A2 (Tinh luyện), A3 (Thượng phẩm), A4 (Siêu cấp), A5 (Hoàn mỹ).
 */
public final class ItemStatCalculator {

    private ItemStatCalculator() {}

    /**
     * Interpolates the stat value for a given quality tier t (1 -> 5).
     * Formula: Value(t) = round(min + (max - min) * (t - 1) / 4.0)
     */
    public static int calculate(int min, int max, byte tier) {
        if (tier <= 1) return min;
        if (tier >= 5) return max;
        if (min == max) return min;

        double step = (double) (max - min) / 4.0;
        return (int) Math.round(min + (tier - 1) * step);
    }

    /**
     * Calculates upgrade bonus for upgrade levels +1 to +16.
     * Each upgrade level adds a compound percentage bonus to base stat.
     */
    public static int applyUpgradeBonus(int baseValue, byte upgradeLevel) {
        if (upgradeLevel <= 0) return baseValue;
        // Formula: Base * (1 + 0.10 * level)
        double mult = 1.0 + (upgradeLevel * 0.10);
        return (int) Math.round(baseValue * mult);
    }
}

