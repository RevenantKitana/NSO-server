package com.nsoz.v2.domain.item.calculator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Pure Mathematical Enchantment & Upgrading Engine (Kenshiko +1 -> +16).
 * Standardized probability curve with deterministic rules and anti-frustration fail-safes.
 */
public final class KenshikoEnchantEngine {

    // Success probability curve for +1 to +16 (basis points: 10000 = 100%)
    private static final int[] SUCCESS_CHANCES = {
            10000, // +0 -> +1: 100%
            9000,  // +1 -> +2: 90%
            8000,  // +2 -> +3: 80%
            7000,  // +3 -> +4: 70%
            6000,  // +4 -> +5: 60%
            5000,  // +5 -> +6: 50%
            4500,  // +6 -> +7: 45%
            4000,  // +7 -> +8: 40%
            3500,  // +8 -> +9: 35%
            3000,  // +9 -> +10: 30%
            2500,  // +10 -> +11: 25%
            2000,  // +11 -> +12: 20%
            1500,  // +12 -> +13: 15%
            1000,  // +13 -> +14: 10%
            700,   // +14 -> +15: 7%
            500    // +15 -> +16: 5%
    };

    private KenshikoEnchantEngine() {}

    public record UpgradeResult(
            boolean isSuccess,
            byte newLevel,
            String message
    ) {}

    /**
     * Executes single upgrade attempt on item.
     *
     * @param currentLevel Current upgrade level (+0 to +15)
     * @return UpgradeResult containing success flag and resulting level
     */
    public static UpgradeResult attemptUpgrade(byte currentLevel) {
        if (currentLevel >= 16) {
            return new UpgradeResult(false, currentLevel, "Vật phẩm đã đạt cấp cường hóa tối đa (+16)");
        }

        int targetIndex = currentLevel; // 0 for +1, 1 for +2, etc.
        int chance = SUCCESS_CHANCES[Math.min(targetIndex, SUCCESS_CHANCES.length - 1)];

        int roll = ThreadLocalRandom.current().nextInt(10000); // 0 to 9999
        boolean success = roll < chance;

        if (success) {
            byte nextLevel = (byte) (currentLevel + 1);
            return new UpgradeResult(true, nextLevel, "Cường hóa thành công lên +" + nextLevel + "!");
        } else {
            // Fail safe: If >= +8, steps down 1 level; otherwise retains current level
            byte degradedLevel = currentLevel >= 8 ? (byte) (currentLevel - 1) : currentLevel;
            return new UpgradeResult(false, degradedLevel, "Cường hóa thất bại!");
        }
    }
}

