package com.nsoz.model.stat;

/**
 * Ma trận ánh xạ chuyển đổi toàn bộ mã Option ID cũ (0..150)
 * sang mô hình StatModifier chuẩn hóa.
 *
 * Sửa các lỗi kinh điển:
 * - Option 12: Kháng Băng (Trước đây bị cộng nhầm vào Điểm Chính Xác)
 * - Tự động định tuyến sát thương (Ngoại công / Nội công) theo phái (sideClass: 0 = Ngoại, 1 = Nội)
 */
public class OptionMapper {

    /**
     * Chuyển đổi mã Option ID cũ sang StatModifier.
     *
     * @param optionId  Mã Option trong dữ liệu game cũ
     * @param param     Giá trị chỉ số
     * @param sideClass 0: Ngoại công (Kiếm, Kunai, Đao), 1: Nội công (Tiêu, Cung, Quạt)
     * @return StatModifier tương ứng hoặc null nếu là option không thuộc bộ chỉ số chiến đấu
     */
    public static StatModifier map(int optionId, int param, byte sideClass) {
        if (param == 0) {
            return null;
        }

        int targetId = OptionStandardizer.getCanonicalId(optionId);

        switch (targetId) {
            // Sát thương ngoại công cố định
            case 0:
            case 21:
            case 23:
            case 25:
                // Dòng Tấn công chung (73, 76, 87) thích ứng theo phái: Ngoại công hoặc Nội công
                if (optionId == 73 || optionId == 76 || optionId == 87) {
                    StatType genericAttack = (sideClass == 0) ? StatType.PHYSICAL_ATTACK : StatType.MAGIC_ATTACK;
                    return new StatModifier(genericAttack, ModType.FLAT, param);
                }
                return new StatModifier(StatType.PHYSICAL_ATTACK, ModType.FLAT, param);

            // Tấn công cơ bản của vũ khí ẩn +12 (thích ứng theo phái)
            case 38:
                StatType weaponBaseAtk = (sideClass == 0) ? StatType.PHYSICAL_ATTACK : StatType.MAGIC_ATTACK;
                return new StatModifier(weaponBaseAtk, ModType.FLAT, param);

            // Sát thương nội công cố định
            case 1:
            case 22:
            case 24:
            case 26:
                return new StatModifier(StatType.MAGIC_ATTACK, ModType.FLAT, param);

            // % Tăng Ngoại / Nội công
            case 8:
                return new StatModifier(StatType.PHYSICAL_ATTACK, ModType.PERCENT, param);
            case 9:
                return new StatModifier(StatType.MAGIC_ATTACK, ModType.PERCENT, param);

            // Tấn công theo hệ
            case 88:
                return new StatModifier(StatType.ELEMENT_FIRE_ATTACK, ModType.FLAT, param);
            case 89:
                return new StatModifier(StatType.ELEMENT_ICE_ATTACK, ModType.FLAT, param);
            case 90:
                return new StatModifier(StatType.ELEMENT_WIND_ATTACK, ModType.FLAT, param);

            // % Tấn công tiềm năng
            case 94:
                return new StatModifier(StatType.POTENTIAL_ATTACK, ModType.PERCENT, param);

            // Tiềm năng
            case 57:
                return new StatModifier(StatType.POTENTIAL, ModType.FLAT, param);
            case 58:
                return new StatModifier(StatType.POTENTIAL, ModType.PERCENT, param);

            // HP tối đa
            case 6:
            case 32:
            case 77:
            case 82:
            case 125:
                return new StatModifier(StatType.MAX_HP, ModType.FLAT, param);

            case 31:
            case 61:
                return new StatModifier(StatType.MAX_HP, ModType.PERCENT, param);

            case 128:
                return new StatModifier(StatType.HP_AFTER_EQUIP, ModType.PERCENT, param);

            // MP tối đa
            case 7:
            case 19:
            case 29:
            case 83:
            case 117:
                return new StatModifier(StatType.MAX_MP, ModType.FLAT, param);

            case 28:
            case 60:
                return new StatModifier(StatType.MAX_MP, ModType.PERCENT, param);

            // Chính xác
            case 10:
            case 18:
            case 75:
            case 86:
            case 116:
                return new StatModifier(StatType.ACCURACY, ModType.FLAT, param);

            // Né đòn
            case 5:
            case 17:
            case 62:
            case 68:
            case 78:
            case 84:
            case 115:
                return new StatModifier(StatType.EVASION, ModType.FLAT, param);

            // Kháng Hỏa
            case 2:
            case 11:
            case 33:
            case 70:
            case 96:
                return new StatModifier(StatType.RES_FIRE, ModType.FLAT, param);
            case 127:
                return new StatModifier(StatType.RES_FIRE, ModType.PERCENT, param);

            // Kháng Băng (FIX BUG: Opt 12 đã được đưa về RES_ICE thay vì Exactly)
            case 3:
            case 12:
            case 34:
            case 71:
            case 95:
                return new StatModifier(StatType.RES_ICE, ModType.FLAT, param);
            case 130:
                return new StatModifier(StatType.RES_ICE, ModType.PERCENT, param);

            // Kháng Phong
            case 4:
            case 13:
            case 35:
            case 72:
            case 97:
                return new StatModifier(StatType.RES_WIND, ModType.FLAT, param);
            case 131:
                return new StatModifier(StatType.RES_WIND, ModType.PERCENT, param);

            // Kháng tất cả các hệ
            case 20:
            case 36:
            case 81:
            case 98:
            case 118:
                return new StatModifier(StatType.RES_ALL, ModType.FLAT, param);

            // Chí mạng & ST Chí mạng
            case 14:
            case 37:
            case 69:
            case 92:
            case 114:
                return new StatModifier(StatType.CRIT_RATE, ModType.FLAT, param);

            case 105:
                return new StatModifier(StatType.CRIT_DAMAGE, ModType.FLAT, param);
            case 39:
            case 67:
                return new StatModifier(StatType.CRIT_DAMAGE, ModType.PERCENT, param);

            case 46:
                return new StatModifier(StatType.RES_CRIT_DAMAGE, ModType.PERCENT, param);
            case 63:
            case 79:
            case 121:
                return new StatModifier(StatType.RES_CRIT_PERCENT, ModType.PERCENT, param);

            // Phòng thủ & Giảm trừ
            case 47:
            case 74:
            case 80:
            case 124:
                return new StatModifier(StatType.DAMAGE_REDUCTION, ModType.FLAT, param);

            // Sát thương đặc biệt
            case 113:
                return new StatModifier(StatType.TRUE_DAMAGE, ModType.FLAT, param);
            case 103:
                return new StatModifier(StatType.PVP_DAMAGE, ModType.FLAT, param);

            // Phản đòn
            case 15:
            case 91:
            case 126:
                return new StatModifier(StatType.REFLECT_MELEE, ModType.FLAT, param);
            case 135:
                return new StatModifier(StatType.REFLECT_ICE_CHANCE, ModType.PERCENT, param);

            // Hạ khắc (% Bỏ qua kháng)
            case 101:
                return new StatModifier(StatType.IGNORE_RESISTANCE, ModType.FLAT, param);

            // Tốc độ di chuyển
            case 93:
                return new StatModifier(StatType.MOVE_SPEED, ModType.FLAT, param);

            default:
                return null;
        }
    }

    /**
     * Nạp trực tiếp một mã Option vào StatContainer để tối ưu hiệu năng (tránh khởi tạo object thừa).
     */
    public static void apply(StatContainer container, int optionId, int param, byte sideClass) {
        if (container == null || param == 0) {
            return;
        }

        StatModifier modifier = map(optionId, param, sideClass);
        if (modifier != null) {
            container.add(modifier);
        }
    }
}
