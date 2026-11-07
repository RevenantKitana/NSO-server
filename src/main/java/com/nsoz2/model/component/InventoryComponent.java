package com.nsoz2.model.component;

import com.nsoz2.item.Equip;
import com.nsoz2.item.Item;
import com.nsoz2.model.Char;

/**
 * InventoryComponent: Quản lý toàn bộ danh mục tài sản, hành trang (bag), rương (box), trang bị và tiền tệ.
 */
public class InventoryComponent extends AbstractComponent {

    public InventoryComponent(Char owner) {
        super(owner);
    }

    public Item[] getBag() {
        return owner.bag;
    }

    public Item[] getBox() {
        return owner.box;
    }

    public Equip[] getEquipments() {
        return owner.equipment;
    }

    public Equip[] getFashion() {
        return owner.fashion;
    }

    public Item[] getMounts() {
        return owner.mount;
    }

    public long getCoin() {
        return owner.coin;
    }

    public long getCoinInBox() {
        return owner.coinInBox;
    }

    public long getYen() {
        return owner.yen;
    }

    public int getGold() {
        return (owner.user != null) ? owner.user.gold : 0;
    }

    public void addCoin(long amount) {
        owner.addCoin(amount);
    }

    public void addYen(long amount) {
        owner.addYen(amount);
    }

    public void addGold(int amount) {
        if (owner.user != null) {
            owner.user.addGold(amount);
        }
    }

    public int getBagEmptyCount() {
        return owner.getSlotNull();
    }

    public boolean hasBagSpace(int slots) {
        return getBagEmptyCount() >= slots;
    }
}