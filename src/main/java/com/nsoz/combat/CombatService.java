package com.nsoz.combat;

import com.nsoz.constants.MobName;
import com.nsoz.effect.Effect;
import com.nsoz.mob.Mob;
import com.nsoz.model.Char;
import com.nsoz.util.NinjaUtils;

/**
 * Bộ máy tính toán sát thương & giao tranh chuẩn hóa (Combat Engine).
 * Tách biệt hoàn toàn logic tính toán toán học khỏi Char.java.
 *
 * Các sửa đổi và chuẩn hóa quan trọng:
 * 1. Khắc phục bug Option 121 (Kháng ST Chí Mạng): Áp trần tối đa 100%.
 * 2. Khắc phục bug Hệ Băng nổ chí mạng cộng 2 lần kỹ năng hỗ trợ optionsSupportSkill[65].
 * 3. Chống bất tử trong PvP: Áp trần Miễn giảm sát thương (Opt 63 + Opt 98) tối đa 75%.
 * 4. Chuẩn hóa thứ tự Sát Thương Chuẩn (Option 113): Cộng trực tiếp sau khi trừ giáp/dameDown,
 *    không bị giảm trừ bởi trạng thái suy yếu (80%), đóng băng (50%) hay dameDown.
 */
public class CombatService {

    /**
     * Tính toán sát thương trong giao tranh người với người (PvP).
     *
     * @param attacker Người tấn công
     * @param target   Mục tiêu chịu đòn
     * @param isFatal  Đòn đánh có nổ chí mạng hay không
     * @return CombatResult chứa đầy đủ kết quả tính toán
     */
    public static CombatResult calculatePvPDamage(Char attacker, Char target, boolean isFatal) {
        // Step 1: Roll Base Damage
        int dameHit = NinjaUtils.nextInt(attacker.damage2, attacker.damage);
        int dameBasic = dameHit;

        // Step 2: Fire Burn Status (Mục tiêu đang bị bỏng -> nhận thêm 100% dame gốc)
        if (target.isFire) {
            dameHit += dameBasic;
        }

        // Step 3: Critical Strike (Bạo kích)
        int dameFatal = 0;
        if (isFatal) {
            dameFatal += dameBasic;
            dameFatal += (dameBasic * (attacker.percentFatalDame + attacker.optionsSupportSkill[65])) / 100;
            dameFatal += attacker.fatalDame;
            dameFatal -= dameHit * (target.options[46] + target.options[79]) / 100;
        }

        // Trừ % Kháng Sát thương Chí mạng (Option 121) - Áp trần tối đa 100%
        int kstcm = Math.min(100, Math.max(0, target.options[121]));
        dameFatal -= dameFatal * kstcm / 100;
        if (dameFatal < 0) {
            dameFatal = 0;
        }

        // Step 4: Elemental & Counter (Thuộc tính hệ & Khắc hệ)
        boolean isSkipResistance = false;
        if (attacker.isEffSkipResistance) {
            Effect eff3 = target.getEm().findByID((byte) 39);
            if (eff3 != null) {
                isSkipResistance = NinjaUtils.nextInt(100) < eff3.param;
            }
        }

        switch (attacker.getSys()) {
            case 1: // Hỏa
                if (!isSkipResistance) {
                    dameHit -= target.resFire;
                }
                dameHit -= dameHit * target.options[127] / 100;
                dameHit -= target.options[48];
                dameHit += attacker.options[51];
                dameHit += dameBasic * attacker.options[55] / 100;
                break;

            case 2: // Băng (ĐÃ FIX: Không cộng đúp optionsSupportSkill[65] tại đây)
                if (!isSkipResistance) {
                    dameHit -= target.resIce;
                }
                dameHit -= dameHit * target.options[130] / 100;
                dameHit -= target.options[49];
                dameHit += attacker.options[52];
                dameHit += dameBasic * attacker.options[56] / 100;
                break;

            case 3: // Phong
                if (!isSkipResistance) {
                    dameHit -= target.resWind;
                }
                dameHit -= dameHit * target.options[131] / 100;
                dameHit -= target.options[50];
                dameHit += attacker.options[53];
                dameHit += dameBasic * attacker.options[54] / 100;
                break;
        }

        // Step 5: Percent Damage Mitigation (Áp trần tối đa 75% để triệt tiêu bug bất tử 0 dame)
        int rawMitigation = target.options[63] + target.options[98];
        int mitigation = Math.min(75, Math.max(0, rawMitigation));
        dameHit -= dameHit * mitigation / 100;

        if (target.isReductionDame) {
            dameHit -= dameHit * target.options[136] / 100;
        }

        // Step 6: PvP Scale & Level Difference
        int levelDiff = (target.level - attacker.level) / 40;
        int scale = 6 - levelDiff;
        if (scale < 1) {
            scale = 1;
        }
        dameHit = (dameHit + (dameFatal / 3)) * scale / 100;
        if (dameHit <= 0) {
            dameHit = 1;
        }

        // Step 7: Reflection (Phản đòn)
        int reactDame = 0;
        boolean isIceReflect = false;
        if (!attacker.isNhanBan) {
            reactDame = target.reactDame;
            if (target.options[135] > 0) {
                if (NinjaUtils.nextInt(100) < target.options[135]) {
                    reactDame = attacker.hp * 20 / 100;
                    isIceReflect = true;
                }
            }
        }

        // Step 8: Accuracy vs Evasion Check
        int randMiss = NinjaUtils.nextInt(target.miss + 100);
        int randExactly = NinjaUtils.nextInt(attacker.exactly + 100);
        boolean isMiss = randMiss > randExactly || target.isMiss;

        // Step 9: Trạng thái bất lợi & Giảm trừ cố định (dameDown)
        if (attacker.isEffDameDown) {
            Effect eff2 = target.getEm().findByID((byte) 37);
            if (eff2 != null) {
                dameHit -= dameHit * eff2.param / 100;
            }
        }
        if (attacker.isInfected()) {
            dameHit -= dameHit * 80 / 100;
        }
        if (attacker.isCool()) {
            dameHit -= dameHit * 50 / 100;
        }

        // Trừ giảm trừ cố định (dameDown)
        dameHit -= target.dameDown;
        if (dameHit < 0) {
            dameHit = 0;
        }

        // Thêm Sát thương lên người (Option 103)
        dameHit += attacker.options[103];

        // CHUẨN HÓA: CỘNG SÁT THƯƠNG CHUẨN (Option 113) SAU CÙNG
        // Sát thương chuẩn không bị cản bởi dameDown, suy yếu hay đóng băng
        dameHit += attacker.options[113];

        if (isMiss) {
            dameHit = 0;
        }

        return new CombatResult(dameHit, isMiss, isFatal, reactDame, isIceReflect);
    }

    /**
     * Tính toán sát thương khi người chơi tấn công quái vật / Boss (PvE).
     */
    public static int calculatePvEDamage(Char attacker, Mob mob, boolean isFatal, int add) {
        int damageBasic = NinjaUtils.nextInt(attacker.damage2, attacker.damage);

        if (attacker.zone.tilemap.isDungeoClan()) {
            damageBasic = (int) ((long) mob.maxHP / 100);
            if (mob.template.id == MobName.BAO_QUAN || mob.template.id == MobName.TU_HA_MA_THAN) {
                damageBasic = (int) ((long) mob.maxHP / 10000);
            }
        }
        int dameHit = damageBasic;

        // Bạo kích lên quái
        if (isFatal) {
            dameHit += damageBasic;
            dameHit += damageBasic * (attacker.percentFatalDame + attacker.optionsSupportSkill[65] + add) / 100;
            dameHit += attacker.fatalDame;
        }

        // Bonus thú cưỡi
        if (attacker.mobMe != null && (attacker.mobMe.template.id == MobName.HOA_LONG
                || attacker.mobMe.template.id == MobName.DI_LONG_CAP_1
                || attacker.mobMe.template.id == MobName.DI_LONG_CAP_2
                || attacker.mobMe.template.id == MobName.DI_LONG_CAP_3
                || attacker.mobMe.template.id == MobName.HAI_MA_CAP_1
                || attacker.mobMe.template.id == MobName.HAI_MA_CAP_2
                || attacker.mobMe.template.id == MobName.HAI_MA_CAP_3)) {
            // Không tăng thêm
        } else {
            dameHit += attacker.options[102];
        }

        // Quái bị bỏng
        if (mob.effects != null && mob.effects.get((byte) 1) != null) {
            dameHit += damageBasic;
        }

        // Khắc hệ quái (Options 51..56)
        switch (mob.sys) {
            case 1:
                dameHit += attacker.options[51];
                dameHit += dameHit * attacker.options[54] / 100;
                break;
            case 2:
                dameHit += attacker.options[52];
                dameHit += dameHit * attacker.options[55] / 100;
                break;
            case 3:
                dameHit += attacker.options[53];
                dameHit += dameHit * attacker.options[56] / 100;
                break;
        }

        // Trạng thái suy yếu & đóng băng của người tấn công
        if (attacker.isInfected()) {
            dameHit -= dameHit * 80 / 100;
        }
        if (attacker.isCool()) {
            dameHit -= dameHit * 50 / 100;
        }

        // Quái đặc biệt (Người tuyết chỉ nhận 1 dame)
        if (mob.template.id == MobName.NGUOI_TUYET) {
            dameHit = 1;
        }

        // CHUẨN HÓA: CỘNG SÁT THƯƠNG CHUẨN (Option 113) SAU CÙNG
        dameHit += attacker.options[113];

        return dameHit;
    }
}
