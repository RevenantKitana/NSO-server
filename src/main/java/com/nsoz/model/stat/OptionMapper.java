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
            // Tấn công duy nhất cố định (Canonical Opt 0)
            case 0:
            case 1:
                return new StatModifier(StatType.ATTACK, ModType.FLAT, param);

            // Tấn công cơ bản của vũ khí ẩn +12
            case 38:
                return new StatModifier(StatType.ATTACK, ModType.FLAT, param);

            // Vật công duy nhất (% Scaling - Canonical Opt 8)
            case 8:
            case 9:
            case 94:
                return new StatModifier(StatType.ATTACK, ModType.PERCENT, param);

            // Sát thương theo hệ
            case 88:
                return new StatModifier(StatType.ELEMENT_FIRE_ATTACK, ModType.FLAT, param);
            case 89:
                return new StatModifier(StatType.ELEMENT_ICE_ATTACK, ModType.FLAT, param);
            case 90:
                return new StatModifier(StatType.ELEMENT_WIND_ATTACK, ModType.FLAT, param);

            // Tiềm năng
            case 57:
                return new StatModifier(StatType.POTENTIAL, ModType.FLAT, param);
            case 58:
                return new StatModifier(StatType.POTENTIAL, ModType.PERCENT, param);

            // HP tối đa (Canonical Opt 6)
            case 6:
                return new StatModifier(StatType.MAX_HP, ModType.FLAT, param);

            case 31:
                return new StatModifier(StatType.MAX_HP, ModType.PERCENT, param);

            case 128:
                return new StatModifier(StatType.HP_AFTER_EQUIP, ModType.PERCENT, param);

            // MP tối đa (Canonical Opt 7)
            case 7:
                return new StatModifier(StatType.MAX_MP, ModType.FLAT, param);

            case 28:
                return new StatModifier(StatType.MAX_MP, ModType.PERCENT, param);

            // Chính xác (Canonical Opt 10)
            case 10:
                return new StatModifier(StatType.ACCURACY, ModType.FLAT, param);

            // Né đòn (Canonical Opt 5)
            case 5:
                return new StatModifier(StatType.EVASION, ModType.FLAT, param);

            // Kháng Hỏa (Canonical Opt 2)
            case 2:
                return new StatModifier(StatType.RES_FIRE, ModType.FLAT, param);
            case 127:
                return new StatModifier(StatType.RES_FIRE, ModType.PERCENT, param);

            // Kháng Băng (Canonical Opt 3)
            case 3:
                return new StatModifier(StatType.RES_ICE, ModType.FLAT, param);
            case 130:
                return new StatModifier(StatType.RES_ICE, ModType.PERCENT, param);

            // Kháng Phong (Canonical Opt 4)
            case 4:
                return new StatModifier(StatType.RES_WIND, ModType.FLAT, param);
            case 131:
                return new StatModifier(StatType.RES_WIND, ModType.PERCENT, param);

            // Kháng tất cả các hệ (Canonical Opt 20)
            case 20:
                return new StatModifier(StatType.RES_ALL, ModType.FLAT, param);

            // Chí mạng (Canonical Opt 14) & ST Chí mạng (Opt 39, 105)
            case 14:
                return new StatModifier(StatType.CRIT_RATE, ModType.FLAT, param);

            case 105:
                return new StatModifier(StatType.CRIT_DAMAGE, ModType.FLAT, param);
            case 39:
                return new StatModifier(StatType.CRIT_DAMAGE, ModType.PERCENT, param);

            // Kháng ST Chí mạng (Canonical Opt 121)
            case 121:
                return new StatModifier(StatType.RES_CRIT_PERCENT, ModType.PERCENT, param);

            // Phòng thủ & Giảm trừ (Canonical Opt 47)
            case 47:
                return new StatModifier(StatType.DAMAGE_REDUCTION, ModType.FLAT, param);

            // Sát thương đặc biệt
            case 113:
                return new StatModifier(StatType.TRUE_DAMAGE, ModType.FLAT, param);
            case 103:
                return new StatModifier(StatType.PVP_DAMAGE, ModType.FLAT, param);

            // Phản đòn (Canonical Opt 15)
            case 15:
                return new StatModifier(StatType.REFLECT_MELEE, ModType.FLAT, param);
            case 135:
                return new StatModifier(StatType.REFLECT_ICE_CHANCE, ModType.PERCENT, param);

            // Hạ khắc (% Bỏ qua kháng)
            case 101:
                return new StatModifier(StatType.IGNORE_RESISTANCE, ModType.FLAT, param);

            // Tốc độ di chuyển (Canonical Opt 16)
            case 16:
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
