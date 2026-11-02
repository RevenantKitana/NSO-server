package com.nsoz.v2.domain.item;

import com.nsoz.v2.domain.item.calculator.ItemStatCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Extensible Item Instance Tuple {a; x; t; ...}
 * Core Primitives (templateId, upgrade, tier, flags) + Sparse Extensible Payload.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInstance {
    private long id;
    private int characterId;
    private int templateId;         // a: template_id
    private byte upgradeLevel;      // x: upgrade_level (0 to 16)
    private byte tier;              // t: quality tier (1 to 5: A1 to A5)
    private String location;        // 'BAG', 'BOX', 'EQUIP', 'MOUNT', 'FASHION'
    private short slotIndex;        // 0 to 29
    private int quantity;
    private byte sys;
    private int flags;              // Bitmask (0x01: Bound, etc.)
    private int yenPrice;
    private long expiresAt;         // -1 = Permanent
    private String extraData;       // JSON NULL for 95% standard items

    public boolean isBound() {
        return (flags & 0x01) != 0;
    }

    public boolean isExpired(long now) {
        return expiresAt > 0 && now >= expiresAt;
    }

    public boolean isWeapon() {
        // Weapons in NSO typically have templateId 94 (Bạch Hạc Kiếm) or type = 1
        return templateId == 94 || templateId == 95 || templateId == 96 || templateId == 97 || templateId == 98 || templateId == 99;
    }

    /**
     * Calculates attack power based on Tier (A1 -> A5) and Upgrade (+0 -> +16).
     */
    public int getAttackBonus() {
        if (!isWeapon()) return 0;
        // Base range for starter sword: 20 -> 40
        int tierAttack = ItemStatCalculator.calculate(20, 40, tier);
        return ItemStatCalculator.applyUpgradeBonus(tierAttack, upgradeLevel);
    }
}
