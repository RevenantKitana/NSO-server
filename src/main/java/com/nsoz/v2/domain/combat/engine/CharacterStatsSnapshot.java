package com.nsoz.v2.domain.combat.engine;

import com.nsoz.v2.domain.item.ItemInstance;
import com.nsoz.v2.domain.model.PlayerCharacter;

/**
 * Immutable Thread-safe Combat Statistics Snapshot (Java 17 Record).
 * Emitted at Tầng 3 of the 3-Layer Stat Pipeline.
 */
public record CharacterStatsSnapshot(
        // 1. Sinh tồn & Phòng ngự (EHP & Defense)
        int maxHp,
        int maxMp,
        int physicalDefense,
        int damageDown,                     // ID 7: Giảm trừ sát thương (Flat)
        double damageTakenMultiplier,       // ID 33: Hệ số nhận sát thương sau Phép Nhân Miễn Thương (%)
        int critResistance,                 // ID 31: [MỚI] Kháng chí mạng
        int critDamageReductionPercent,     // ID 32: [MỚI] Giảm sát thương chí mạng (%)
        int resFire,                        // ID 12: Kháng Hỏa
        int resIce,                         // ID 13: Kháng Băng
        int resWind,                        // ID 14: Kháng Phong

        // 2. Tấn công (Offense)
        int minDamage,                      // ID 0: Tấn công Min
        int maxDamage,                      // ID 0: Tấn công Max
        int criticalRate,                   // ID 5: Điểm Chí mạng
        int criticalDamagePercent,          // ID 6: Sát thương chí mạng (% chuẩn 200%)
        int damageAmplifyPercent,           // ID 2: Vận công / Tăng thương (%)
        double penetrationMultiplier,       // ID 19: Hệ số kháng còn lại sau Xuyên kháng % (Nhân tích lũy)
        int flatPenetration,                // ID 18: Xuyên kháng điểm (Flat)

        // 3. Cơ động & Độ chuẩn (Agility & Precision)
        int accuracy,                       // ID 3: Chính xác
        int evasion,                        // ID 4: Né đòn
        int moveSpeed,                      // ID 26: Tốc chạy
        int attackSpeedBonus,

        // 4. Bổ trợ & Hồi phục (Utility)
        int hpLeechPercent,                 // ID 21: Hút HP %
        int mpLeechPercent,                 // ID 23: Hút MP %
        int hpOnHit,                        // ID 20: Hồi HP khi đánh
        int mpOnHit,                        // ID 22: Hồi MP khi đánh
        int reactDamagePercent              // ID 17: Phản đòn cận chiến %
) {
    public static CharacterStatsSnapshot createDefault(int level, byte clazz) {
        return new CharacterStatsSnapshot(
                100 + level * 50,           // maxHp
                100 + level * 20,           // maxMp
                10 + level * 2,             // physicalDefense
                0,                          // damageDown
                1.0,                        // damageTakenMultiplier (100% damage taken, 0% reduction)
                0,                          // critResistance
                0,                          // critDamageReductionPercent
                0,                          // resFire
                0,                          // resIce
                0,                          // resWind
                20 + level * 5,             // minDamage
                30 + level * 5,             // maxDamage
                50,                         // criticalRate (ID 5)
                200,                        // criticalDamagePercent (200% default)
                0,                          // damageAmplifyPercent
                1.0,                        // penetrationMultiplier (1.0 = no penetration)
                0,                          // flatPenetration
                100 + level * 5,            // accuracy
                50 + level * 3,             // evasion
                10,                         // moveSpeed
                0,                          // attackSpeedBonus
                0,                          // hpLeechPercent
                0,                          // mpLeechPercent
                0,                          // hpOnHit
                0,                          // mpOnHit
                0                           // reactDamagePercent
        );
    }

    public static CharacterStatsSnapshot createFromCharacter(PlayerCharacter character) {
        if (character == null) {
            return createDefault(1, (byte) 0);
        }
        int level = character.getState() != null ? character.getState().getLevel() : 1;
        int minAtk = 20 + level * 5;
        int maxAtk = 30 + level * 5;

        if (character.getInventory() != null) {
            ItemInstance weapon = character.getInventory().getEquippedWeapon();
            if (weapon != null) {
                int bonus = weapon.getAttackBonus();
                minAtk += bonus;
                maxAtk += bonus;
            }
        }

        int maxHp = character.getState() != null ? character.getState().getMaxHp() : 100 + level * 50;
        int maxMp = character.getState() != null ? character.getState().getMaxMp() : 100 + level * 20;
        int speed = character.getCombat() != null ? character.getCombat().getSpeed() : 10;

        return new CharacterStatsSnapshot(
                maxHp,
                maxMp,
                10 + level * 2,
                0,
                1.0,
                0,
                0,
                0,
                0,
                0,
                minAtk,
                maxAtk,
                50,
                200,
                0,
                1.0,
                0,
                100 + level * 5,
                50 + level * 3,
                speed,
                0,
                0,
                0,
                0,
                0,
                0
        );
    }
}
