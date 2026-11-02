package com.nsoz.v2.domain.combat.engine;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Single-Roll Multi-Variable Probability Resolution Engine.
 * Resolves Hit, Miss, Critical, and Status Effect in a single flat non-branching pass.
 */
public final class SingleRollCombatResolver {

    private static final double MIN_HIT_RATE = 0.05;
    private static final double MAX_HIT_RATE = 0.95;
    private static final double BASE_CRIT_RATE = 0.05;
    private static final double MAX_CRIT_RATE = 0.70;
    private static final double CRIT_HALF_POINT = 1200.0;

    private SingleRollCombatResolver() {}

    public record CombatOutcome(
            boolean isMiss,
            boolean isCrit,
            boolean hasStatusEffect
    ) {
        public static CombatOutcome miss() {
            return new CombatOutcome(true, false, false);
        }

        public static CombatOutcome hit(boolean isCrit, boolean hasStatusEffect) {
            return new CombatOutcome(false, isCrit, hasStatusEffect);
        }
    }

    /**
     * Resolves combat roll using the asymptotic hyperbolic curve for hit/miss and soft-cap crit curve.
     *
     * @param attackerAccuracy Attacker's accuracy (ID 3)
     * @param defenderEvasion  Defender's evasion (ID 4)
     * @param attackerFatal    Attacker's critical rating (ID 5)
     * @param defenderCritRes  Defender's critical resistance (ID 31 [MỚI])
     * @param effectProcChance Base chance for elemental status effect to proc (0.0 to 1.0)
     * @return Immutable CombatOutcome
     */
    public static CombatOutcome resolve(
            int attackerAccuracy,
            int defenderEvasion,
            int attackerFatal,
            int defenderCritRes,
            double effectProcChance) {

        int safeAcc = Math.max(1, attackerAccuracy);
        int safeEva = Math.max(0, defenderEvasion);

        // 1. Calculate Hit / Miss Threshold (Hyperbolic Curve)
        double hitChance = (double) safeAcc / (safeAcc + safeEva);
        hitChance = Math.min(MAX_HIT_RATE, Math.max(MIN_HIT_RATE, hitChance));
        double missThreshold = 1.0 - hitChance;

        // 2. Calculate Crit Threshold (Soft-Cap Diminishing Curve)
        int netCrit = Math.max(0, attackerFatal - defenderCritRes);
        double bonusCrit = (MAX_CRIT_RATE - BASE_CRIT_RATE) * (netCrit / (netCrit + CRIT_HALF_POINT));
        double critThreshold = missThreshold + (BASE_CRIT_RATE + bonusCrit);

        // 3. Roll single uniform random number (0.0 -> 1.0)
        double roll = ThreadLocalRandom.current().nextDouble();

        // 4. Flat Resolution
        if (roll < missThreshold) {
            return CombatOutcome.miss();
        }

        boolean isCrit = (roll < critThreshold);
        boolean hasEffect = (effectProcChance > 0.0) && (ThreadLocalRandom.current().nextDouble() < effectProcChance);

        return CombatOutcome.hit(isCrit, hasEffect);
    }
}

