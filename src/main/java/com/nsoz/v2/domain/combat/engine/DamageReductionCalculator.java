package com.nsoz.v2.domain.combat.engine;

/**
 * Pure Mathematical Calculator for Multiplicative Damage Reduction and Penetration.
 * Enforces non-linear asymptotic diminishing returns to eliminate 100% invulnerability/true damage exploits.
 */
public final class DamageReductionCalculator {

    private DamageReductionCalculator() {}

    /**
     * Calculates the damage taken multiplier from multiple reduction sources (ID 33).
     * Formula: Multiplier = Product(1 - r_i)
     * e.g., source A reduces 10% (0.10), source B reduces 12% (0.12)
     * Result = 0.90 * 0.88 = 0.792 (Total reduction = 20.8%, NOT 22.0%)
     */
    public static double calculateDamageMultiplier(double... reductionRates) {
        double multiplier = 1.0;
        for (double rate : reductionRates) {
            multiplier *= Math.max(0.0, 1.0 - rate);
        }
        return multiplier;
    }

    /**
     * Calculates remaining resistance factor from multiple penetration sources (ID 19).
     * Formula: RemainingResFactor = Product(1 - p_i)
     * e.g., source A penetrates 30% (0.30), source B penetrates 25% (0.25)
     * Remaining = 0.70 * 0.75 = 0.525 (Actual penetration = 47.5%, NOT 55.0%)
     */
    public static double calculateRemainingResFactor(double... penetrationRates) {
        double factor = 1.0;
        for (double rate : penetrationRates) {
            factor *= Math.max(0.0, 1.0 - rate);
        }
        return factor;
    }

    /**
     * Computes final net resistance after % penetration and flat penetration.
     * Order of Operations: % Penetration FIRST (anti-tank), Flat penetration SECOND (anti-squishy).
     * NetRes = max(0, (BaseRes * RemFactor) - FlatPen)
     */
    public static int calculateNetResistance(int baseRes, double remFactor, int flatPen) {
        int scaled = (int) Math.round(baseRes * remFactor);
        return Math.max(0, scaled - flatPen);
    }
}

