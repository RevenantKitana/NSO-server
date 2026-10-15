package com.nsoz.model.stat;

/**
 * Định nghĩa toàn bộ các loại chỉ số cốt lõi của Ninja School.
 * Hỗ trợ chuyển đổi từ hệ thống mã Option ID cũ sang mô hình định kiểu an toàn (Type-safe).
 */
public enum StatType {
    // Sát thương
    PHYSICAL_ATTACK,     // Ngoại công (Opt 0, 21, 23, 25, 8...)
    MAGIC_ATTACK,        // Nội công (Opt 1, 22, 24, 26, 9...)
    POTENTIAL_ATTACK,    // Tấn công theo tiềm năng (Opt 94)
    ELEMENT_FIRE_ATTACK, // Tấn công hệ Hỏa (Opt 88)
    ELEMENT_ICE_ATTACK,  // Tấn công hệ Băng (Opt 89)
    ELEMENT_WIND_ATTACK, // Tấn công hệ Phong (Opt 90)

    // Tiềm năng
    POTENTIAL,           // Điểm / % Tiềm năng (Opt 57 flat, Opt 58 percent)

    // Sinh tồn
    MAX_HP,              // Máu tối đa (Opt 6, 32, 77, 82, 125 flat; Opt 31, 61 percent)
    MAX_MP,              // Mana tối đa (Opt 7, 19, 29, 83, 117 flat; Opt 28, 60 percent)
    HP_AFTER_EQUIP,      // % HP sau khi mặc đồ (Opt 128)

    // Phòng thủ & Kháng
    DAMAGE_REDUCTION,    // Giảm trừ sát thương cố định (dameDown - Opt 47, 74, 80, 124)
    DAMAGE_MITIGATION,   // % Miễn giảm sát thương toàn diện (Opt 98)
    PVP_DAMAGE_MITIGATION,// % Giảm sát thương từ người khác (Opt 63)
    RES_FIRE,            // Kháng Hỏa (Opt 2, 11, 33, 70, 96 flat; Opt 127 percent)
    RES_ICE,             // Kháng Băng (Opt 3, 12, 34, 71, 95 flat; Opt 130 percent)
    RES_WIND,            // Kháng Phong (Opt 4, 13, 35, 72, 97 flat; Opt 131 percent)
    RES_ALL,             // Kháng tất cả các hệ (Opt 36, 118)

    // Đối kháng & Tỷ lệ
    ACCURACY,            // Chính xác (Opt 10, 18, 75, 86, 116)
    EVASION,             // Né đòn (Opt 5, 17, 62, 68, 78, 84, 115)
    CRIT_RATE,           // Tỷ lệ Chí mạng (Opt 14, 37, 69, 92, 114)
    CRIT_DAMAGE,         // Sát thương Chí mạng (Opt 105 flat; Opt 39, 67 percent)
    RES_CRIT_DAMAGE,     // % Giảm sát thương chí mạng (Opt 46, 79)
    RES_CRIT_PERCENT,    // % Kháng ST Chí mạng (Opt 121)

    // Cơ chế đặc biệt
    TRUE_DAMAGE,         // Sát thương chuẩn (Opt 113)
    PVP_DAMAGE,          // Sát thương lên người (Opt 103)
    REFLECT_MELEE,       // Phản đòn cận chiến (Opt 15, 91, 126)
    REFLECT_ICE_CHANCE,  // % Tỷ lệ phản đòn băng giá 20% HP (Opt 135)
    IGNORE_RESISTANCE,   // Xuyên kháng (Opt 101)
    MOVE_SPEED           // Tốc độ di chuyển (Opt 93)
}
