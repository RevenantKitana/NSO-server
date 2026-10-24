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

        // Xác định kháng nguyên tố thô của target đối với hệ của attacker
        int rawRes = 0;
        switch (attacker.getSys()) {
            case 1: // Hỏa
                rawRes = target.resFire + target.options[48];
                break;
            case 2: // Băng
                rawRes = target.resIce + target.options[49];
                break;
            case 3: // Phong
                rawRes = target.resWind + target.options[50];
                break;
        }
        if (isSkipResistance) {
            rawRes = 0;
        }

        // TẦNG 1: TÍNH TOÁN KHÁNG & XUYÊN KHÁNG (Cường khắc & Hạ khắc)
        int sysUp = Math.max(0, attacker.sysUp);
        int haKhac = Math.max(0, attacker.sysDown + attacker.options[101]);
        dameHit = applyResistanceAndPenetration(dameHit, rawRes, sysUp, haKhac);

        // Kháng % hệ theo option trang bị (Opt 127: % Kháng Hỏa, Opt 130: % Kháng Băng, Opt 131: % Kháng Phong)
        // và cộng thêm sát thương hệ từ trang bị (Opt 51..56)
        switch (attacker.getSys()) {
            case 1:
                dameHit -= dameHit * target.options[127] / 100;
                dameHit += attacker.options[51];
                dameHit += dameBasic * attacker.options[55] / 100;
                break;
            case 2:
                dameHit -= dameHit * target.options[130] / 100;
                dameHit += attacker.options[52];
                dameHit += dameBasic * attacker.options[56] / 100;
                break;
            case 3:
                dameHit -= dameHit * target.options[131] / 100;
                dameHit += attacker.options[53];
                dameHit += dameBasic * attacker.options[54] / 100;
                break;
        }

        // TẦNG 2: TƯƠNG KHẮC HỆ NGŨ HÀNH (Tách biệt hoàn toàn: Nhân 1.1 nếu khắc, 0.9 nếu bị khắc)
        int relation = getSysRelation(attacker.getSys(), target.getSys());
        if (relation == 1) {
            dameHit = (int) Math.round(dameHit * 1.1);
        } else if (relation == -1) {
            dameHit = (int) Math.round(dameHit * 0.9);
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
            // Phản đòn cận chiến kích hoạt cho TẤT CẢ các phái khi khoảng cách từ nguồn sát thương đến người chơi đủ gần (bán kính <= 60)
            int distance = NinjaUtils.getDistance(attacker.x, attacker.y, target.x, target.y);
            boolean isCloseRange = distance <= 60;
            if (isCloseRange) {
                reactDame = target.reactDame;
            }
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

        // TẦNG 1: TÍNH TOÁN KHÁNG & XUYÊN KHÁNG CHO MOB (Cường khắc & Hạ khắc)
        int mobRawRes = getMobResistance(mob.level, mob.sys, attacker.getSys());
        if (mob.isMagumandari()) {
            mobRawRes -= mob.magumandariResReduction; // Chiêu 25 Đao: Trừ 30 - 60 Kháng tất cả
        }
        int sysUp = Math.max(0, attacker.sysUp);
        int haKhac = Math.max(0, attacker.sysDown + attacker.options[101]);
        dameHit = applyResistanceAndPenetration(dameHit, mobRawRes, sysUp, haKhac);

        // TẦNG 2: TƯƠNG KHẮC HỆ NGŨ HÀNH (Tách biệt hoàn toàn: Nhân 1.1 nếu khắc, 0.9 nếu bị khắc)
        int pveRelation = getSysRelation(attacker.getSys(), mob.sys);
        if (pveRelation == 1) {
            dameHit = (int) Math.round(dameHit * 1.1);
        } else if (pveRelation == -1) {
            dameHit = (int) Math.round(dameHit * 0.9);
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

    /**
     * Xác định mối quan hệ tương khắc ngũ hành giữa 2 hệ (Hỏa, Băng, Phong).
     * Quy tắc chuẩn NSO:
     * - Hỏa (1) khắc Phong (3)
     * - Phong (3) khắc Băng (2)
     * - Băng (2) khắc Hỏa (1)
     *
     * @param attSys    Hệ của người/nguồn tấn công (1: Hỏa, 2: Băng, 3: Phong, 0: Vô phái)
     * @param targetSys Hệ của mục tiêu (1: Hỏa, 2: Băng, 3: Phong, 0: Vô phái)
     * @return 1 nếu attSys khắc chế targetSys (ưu thế),
     *        -1 nếu attSys bị targetSys khắc chế (yếu thế),
     *         0 nếu cùng hệ hoặc vô phái (trung lập).
     */
    public static int getSysRelation(int attSys, int targetSys) {
        if (attSys <= 0 || targetSys <= 0 || attSys == targetSys) {
            return 0;
        }
        if ((attSys == 1 && targetSys == 3)
                || (attSys == 3 && targetSys == 2)
                || (attSys == 2 && targetSys == 1)) {
            return 1;
        }
        return -1;
    }

    /**
     * Tính toán giảm trừ / gia tăng sát thương qua hệ thống Kháng & Xuyên kháng (Cường khắc, Hạ khắc).
     * Công thức giảm ST theo kháng: y = ((77 * x) / (x + 231)) (%)
     * Cường khắc (sysUp) trừ kháng phẳng trước.
     * - Nếu delta >= 0: Hạ khắc (gộp với Opt 101) giảm trừ tiếp % lượng kháng còn lại.
     * - Nếu delta < 0 (kháng âm): Chịu thêm x_neg * (1 + haKhac / 100) sát thương.
     *
     * @param dameHit Sát thương trước khi qua lớp kháng
     * @param rawRes  Kháng ban đầu của mục tiêu đối với hệ của đòn đánh
     * @param sysUp   Cường khắc / tương khắc của người tấn công (xuyên kháng phẳng)
     * @param haKhac  Hạ khắc + Bỏ qua % kháng (Opt 101) của người tấn công
     * @return Sát thương sau khi tính kháng và xuyên kháng
     */
    public static int applyResistanceAndPenetration(int dameHit, int rawRes, int sysUp, int haKhac) {
        int delta = rawRes - sysUp;
        if (delta >= 0) {
            int ignorePercent = Math.min(100, Math.max(0, haKhac));
            double effectiveRes = delta * (1.0 - (ignorePercent / 100.0));
            double reducePercent = (77.0 * effectiveRes) / (effectiveRes + 231.0);
            dameHit -= (int) (dameHit * reducePercent / 100.0);
        } else {
            int xNeg = -delta;
            int extraDamage = (int) (xNeg * (1.0 + Math.max(0, haKhac) / 100.0));
            dameHit += extraDamage;
        }
        return Math.max(1, dameHit);
    }

    /**
     * Tính toán chỉ số kháng nguyên tố của Quái vật (Mob) theo cấp độ.
     * Công thức: y = ((12 * level^2) / (level + 144))
     * - Kháng tương khắc (hệ bị quái khắc): y
     * - Kháng đúng nguyên tố (cùng hệ với quái): 0.77 * y
     * - Kháng bị khắc (hệ khắc chế quái): 0.33 * y
     *
     * @param mobLevel Cấp độ quái vật
     * @param mobSys   Hệ của quái vật (1: Hỏa, 2: Băng, 3: Phong)
     * @param attSys   Hệ của đòn đánh (1: Hỏa, 2: Băng, 3: Phong)
     * @return Chỉ số kháng ban đầu (raw resistance)
     */
    public static int getMobResistance(int mobLevel, int mobSys, int attSys) {
        if (mobLevel <= 0 || mobSys <= 0 || attSys <= 0) {
            return 0;
        }
        double y = (12.0 * Math.pow(mobLevel, 2)) / (mobLevel + 144.0);
        int relation = getSysRelation(mobSys, attSys);
        if (relation == 1) {
            // Quái khắc hệ đòn đánh -> kháng tương khắc = y
            return (int) Math.round(y);
        } else if (mobSys == attSys) {
            // Cùng hệ -> kháng đúng nguyên tố = 0.77 * y
            return (int) Math.round(0.77 * y);
        } else {
            // Quái bị đòn đánh khắc -> kháng bị khắc = 0.33 * y
            return (int) Math.round(0.33 * y);
        }
    }
}
