package com.nsoz.v2.domain.model.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 4. Combat Component: Manages Class, School, PK Status, and Combat Stats.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombatComponent {
    private byte clazz; // 0: None, 1: Kiếm, 2: Phi Tiêu, 3: Côn, 4: Cung, 5: Đao, 6: Quạt
    private byte pk;    // 0: Hòa bình, 1-3: Phe phái, ...
    private int speed;

    public boolean hasClass() {
        return clazz > 0;
    }
}

