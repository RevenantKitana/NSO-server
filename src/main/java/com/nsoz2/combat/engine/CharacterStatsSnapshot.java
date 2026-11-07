package com.nsoz2.combat.engine;

import com.nsoz2.model.Char;

/**
 * Bản chụp chỉ số bất biến (CharacterStatsSnapshot - Tầng 3).
 * Đặc tả theo docs/rework/09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md:
 * - Thread-safe 100%, không cần bất kỳ khóa luồng nào khi đọc trong Combat loop.
 * - Quy tụ 34 chỉ số cốt lõi đối xứng giữa Công và Thủ.
 * - Hỗ trợ Lazy Recomputation khi nhân vật đổi trang bị, tăng điểm hoặc nhận Buff.
 */
public record CharacterStatsSnapshot(
    // 1. Sinh tồn & Phòng ngự (EHP & Defense)
    int maxHp,
    int maxMp,
    int physicalDefense,
    int damageDown,                    // ID 7: Giảm trừ sát thương cố định (Flat)
    double damageTakenMultiplier,      // ID 33: Hệ số nhận sát thương sau Phép Nhân Miễn Thương (%)
    int critResistance,                // ID 31: Kháng chí mạng [MỚI]
    int critDamageReductionPercent,    // ID 32: Giảm sát thương chí mạng % [MỚI]
    int resFire,                       // ID 12: Kháng Hỏa
    int resIce,                        // ID 13: Kháng Băng
    int resWind,                       // ID 14: Kháng Phong

    // 2. Tấn công (Offense)
    int minDamage,                     // Sát thương tối thiểu
    int maxDamage,                     // Sát thương tối đa
    int criticalRate,                  // ID 5: Điểm bạo kích / Fatal
    int criticalDamageBonusPercent,    // ID 6: Thưởng thêm sát thương chí mạng (%)
    int damageAmplifyPercent,          // ID 2: Vận công / Khuếch đại sát thương (%)
    int trueDamage,                    // ID 1: Sát thương chuẩn (bỏ qua giáp/kháng/miễn thương)

    // 3. Cơ động & Độ chuẩn (Agility & Precision)
    int accuracy,                      // ID 3: Chính xác
    int evasion,                       // ID 4: Né đòn
    int moveSpeed,                     // ID 26: Tốc độ di chuyển
    int attackSpeedBonus,              // % Giảm trễ ra đòn

    // 4. Bổ trợ & Hồi phục (Utility)
    int hpLeechPercent,                // ID 21: Hút HP khi đánh (%)
    int mpLeechPercent,                // ID 23: Hút MP khi đánh (%)
    int hpOnHit,                       // ID 20: Hồi HP on-hit
    int mpOnHit,                       // ID 22: Hồi MP on-hit
    int reactDamagePercent             // ID 17: Phản đòn cận chiến
) {

    /**
     * Tạo bản chụp Snapshot từ trạng thái hiện tại của nhân vật.
     */
    public static CharacterStatsSnapshot from(Char c) {
        if (c == null) {
            return empty();
        }

        // Tính toán Multiplicative Damage Multiplier từ Miễn thương % (ID 33) và các nguồn khác
        double opt33Rate = (c.options != null && c.options.length > 33) ? ((double) Math.max(0, c.options[33]) / 100.0) : 0.0;
        double dmgMultiplier = DamageReductionCalculator.calculateDamageMultiplier(opt33Rate);

        int optCritRes = (c.options != null && c.options.length > 31) ? c.options[31] : 0;
        int optCritDmgRed = (c.options != null && c.options.length > 32) ? c.options[32] : 0;
        // Option 121 cũ được quy chuẩn thành Giảm ST Chí Mạng
        if (c.options != null && c.options.length > 121 && c.options[121] > 0) {
            optCritDmgRed = Math.max(optCritDmgRed, c.options[121]);
        }

        int trueDmg = (c.options != null && c.options.length > 113) ? c.options[113] : 0;

        return new CharacterStatsSnapshot(
            c.maxHP,
            c.maxMP,
            c.dameDown,
            c.dameDown,
            dmgMultiplier,
            optCritRes,
            optCritDmgRed,
            c.resFire,
            c.resIce,
            c.resWind,

            c.damage2,
            c.damage,
            c.fatal,
            c.percentFatalDame,
            0,
            trueDmg,

            c.exactly,
            c.miss,
            c.speed,
            0,

            (c.options != null && c.options.length > 105) ? c.options[105] : 0,
            (c.options != null && c.options.length > 106) ? c.options[106] : 0,
            0,
            0,
            c.reactDame
        );
    }

    public static CharacterStatsSnapshot empty() {
        return new CharacterStatsSnapshot(
            1, 1, 0, 0, 1.0, 0, 0, 0, 0, 0,
            1, 1, 0, 0, 0, 0,
            0, 0, 100, 0,
            0, 0, 0, 0, 0
        );
    }
}

