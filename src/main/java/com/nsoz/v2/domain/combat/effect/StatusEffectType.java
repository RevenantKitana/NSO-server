package com.nsoz.v2.domain.combat.effect;

/**
 * Elemental Tri-State Status Effects for NSO V2.
 */
public enum StatusEffectType {
    /**
     * HỎA — BỎNG (Trường Hirosaki: Kiếm, Tiêu).
     * Trừ 100% Miễn Thương (Damage Multiplier - 1.0 -> Nhận x2 Sát thương).
     */
    BURN,

    /**
     * BĂNG — ĐÓNG BĂNG / TÊ CÓNG (Trường Haruna: Kunai, Quạt).
     * Giảm 50% Bộ tứ Thân pháp (Accuracy, Evasion, Fatal, CritRes) + Giảm tốc chạy.
     */
    FREEZE,

    /**
     * PHONG — CHOÁNG (Trường Ookaza: Đao, Cung).
     * Khóa cứng 100% thao tác nhưng bảo toàn toàn bộ chỉ số phòng ngự.
     */
    STUN
}

