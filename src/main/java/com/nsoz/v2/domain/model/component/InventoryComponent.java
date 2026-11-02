package com.nsoz.v2.domain.model.component;

import com.nsoz.v2.domain.item.ItemInstance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * 3. Inventory Component: Manages Currency (Yen, Xu) and Item Storage Capacity.
 * Encapsulates Bag, Equipment, and Box inventory slots cleanly.
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

    @Builder.Default
    private ItemInstance[] bag = new ItemInstance[30];

    @Builder.Default
    private ItemInstance[] equipment = new ItemInstance[16];

    @Builder.Default
    private ItemInstance[] box = new ItemInstance[30];

    public void initStorage() {
        if (bagCapacity <= 0) bagCapacity = 30;
        if (boxCapacity <= 0) boxCapacity = 30;
        if (bag == null || bag.length != bagCapacity) {
            bag = new ItemInstance[bagCapacity];
        }
        if (equipment == null || equipment.length != 16) {
            equipment = new ItemInstance[16];
        }
        if (box == null || box.length != boxCapacity) {
            box = new ItemInstance[boxCapacity];
        }
    }

    public void loadItems(List<ItemInstance> items) {
        initStorage();
        Arrays.fill(bag, null);
        Arrays.fill(equipment, null);
        Arrays.fill(box, null);

        for (ItemInstance item : items) {
            if (item == null) continue;
            int slot = item.getSlotIndex();
            switch (item.getLocation()) {
                case "BAG":
                    if (slot >= 0 && slot < bag.length) {
                        bag[slot] = item;
                    }
                    break;
                case "EQUIP":
                    if (slot >= 0 && slot < equipment.length) {
                        equipment[slot] = item;
                    }
                    break;
                case "BOX":
                    if (slot >= 0 && slot < box.length) {
                        box[slot] = item;
                    }
                    break;
            }
        }
    }

    public ItemInstance getEquippedWeapon() {
        if (equipment == null || equipment.length <= 1) return null;
        return equipment[1];
    }

    public ItemInstance getBagItem(int slotIndex) {
        if (bag == null || slotIndex < 0 || slotIndex >= bag.length) return null;
        return bag[slotIndex];
    }

    public ItemInstance getEquipment(int slotIndex) {
        if (equipment == null || slotIndex < 0 || slotIndex >= equipment.length) return null;
        return equipment[slotIndex];
    }

    public int findFirstFreeBagSlot() {
        if (bag == null) return -1;
        for (int i = 0; i < bag.length; i++) {
            if (bag[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Equips an item from the bag into equipment slots.
     * @param bagSlotIndex index in bag
     * @return target equipment slot index, or -1 if failed
     */
    public int equip(int bagSlotIndex) {
        ItemInstance item = getBagItem(bagSlotIndex);
        if (item == null) return -1;

        int targetEquipSlot = -1;
        if (item.isWeapon()) {
            targetEquipSlot = 1; // Slot 1 = Vũ khí
        }

        if (targetEquipSlot == -1) {
            return -1; // Currently unsupported equip slot
        }

        ItemInstance currentEquipped = equipment[targetEquipSlot];

        // Swap
        equipment[targetEquipSlot] = item;
        item.setLocation("EQUIP");
        item.setSlotIndex((short) targetEquipSlot);

        if (currentEquipped != null) {
            bag[bagSlotIndex] = currentEquipped;
            currentEquipped.setLocation("BAG");
            currentEquipped.setSlotIndex((short) bagSlotIndex);
        } else {
            bag[bagSlotIndex] = null;
        }

        return targetEquipSlot;
    }

    /**
     * Unequips an item from equipment slots into bag.
     * @param equipSlotIndex index in equipment
     * @return bag slot index where item was placed, or -1 if failed (e.g. bag full)
     */
    public int unequip(int equipSlotIndex) {
        ItemInstance item = getEquipment(equipSlotIndex);
        if (item == null) return -1;

        int freeBagSlot = findFirstFreeBagSlot();
        if (freeBagSlot == -1) {
            return -1; // Bag is full
        }

        equipment[equipSlotIndex] = null;
        bag[freeBagSlot] = item;
        item.setLocation("BAG");
        item.setSlotIndex((short) freeBagSlot);

        return freeBagSlot;
    }

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
