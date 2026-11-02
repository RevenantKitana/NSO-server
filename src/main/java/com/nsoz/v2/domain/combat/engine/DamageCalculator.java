package com.nsoz.v2.domain.combat.engine;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Pure Mathematical Damage Calculator for NSO V2.
 * Calculates hit damage with Flat/Percent ordering, Critical mitigation (ID 32),
 * Multiplicative Damage Reduction (ID 33), and Elemental Burn Amplification.
 */
public final class DamageCalculator {

    private DamageCalculator() {}

    /**
     * Pure function calculating final hit damage.
     */
    public static int calculateHitDamage(
            CharacterStatsSnapshot attacker,
            int targetDefense,
            int targetDamageDown,
            double targetDamageTakenMult,
            int targetCritDamageReductionPercent,
            boolean isTargetBurned,
            SingleRollCombatResolver.CombatOutcome outcome) {

        if (outcome.isMiss()) {
            return 0; // Miss dealt 0 damage
        }

        // 1. Roll base damage uniformly in [minDamage, maxDamage]
        int minD = Math.max(1, attacker.minDamage());
        int maxD = Math.max(minD, attacker.maxDamage());
        int rawDamage = ThreadLocalRandom.current().nextInt(minD, maxD + 1);

        // 2. Damage Amplify % (ID 2: Vận công)
        if (attacker.damageAmplifyPercent() > 0) {
            rawDamage = (int) Math.round(rawDamage * (1.0 + (attacker.damageAmplifyPercent() / 100.0)));
        }

        // 3. Defense with Multiplicative Penetration (ID 19 % before ID 18 Flat)
        int netDefense = DamageReductionCalculator.calculateNetResistance(
                targetDefense, attacker.penetrationMultiplier(), attacker.flatPenetration());
        int damageAfterDefense = Math.max(1, rawDamage - netDefense);

        // 4. Subtract Flat Damage Reduction (ID 7: Giảm trừ sát thương)
        int damageAfterFlat = Math.max(1, damageAfterDefense - targetDamageDown);

        // 5. Critical Strike with Defender Reduction (ID 32: Giảm ST chí mạng %)
        if (outcome.isCrit()) {
            double extraCritPercent = Math.max(0.0,
                    (attacker.criticalDamagePercent() - 200.0) - targetCritDamageReductionPercent);
            double critMultiplier = 2.0 + (extraCritPercent / 100.0);
            damageAfterFlat = (int) Math.round(damageAfterFlat * critMultiplier);
        }

        // 6. Multiplicative Damage Reduction (ID 33) & Elemental Burn (-100% DR -> +100% Damage Taken)
        double finalMultiplier = targetDamageTakenMult;
        if (isTargetBurned) {
            // Target is burned: DR is reduced by 1.0 (equivalent to receiving +100% damage)
            finalMultiplier += 1.0;
        }

        int finalDamage = (int) Math.round(damageAfterFlat * Math.max(0.05, finalMultiplier));
        return Math.max(1, finalDamage);
    }
}

