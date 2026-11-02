package com.nsoz.v2.domain.model.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3. Inventory Component: Manages Currency (Yen, Xu) and Item Storage Capacity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryComponent {
    private long yen;
    private long xu;
    private int bagCapacity;
    private int boxCapacity;

    public boolean consumeYen(long amount) {
        if (amount <= 0) return true;
        if (yen >= amount) {
            yen -= amount;
            return true;
        }
        return false;
    }

    public void addYen(long amount) {
        if (amount > 0) {
            this.yen += amount;
        }
    }

    public boolean consumeXu(long amount) {
        if (amount <= 0) return true;
        if (xu >= amount) {
            xu -= amount;
            return true;
        }
        return false;
    }

    public void addXu(long amount) {
        if (amount > 0) {
            this.xu += amount;
        }
    }
}

