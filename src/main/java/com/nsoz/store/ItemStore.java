/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.store;

import com.nsoz.item.ItemManager;
import com.nsoz.item.ItemTemplate;
import com.nsoz.model.stat.OptionStandardizer;
import com.nsoz.option.ItemOption;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Getter
@Setter
public class ItemStore {

    private int id;
    private int itemID;
    private byte sys;
    private int coin;
    private int yen;
    private int gold;
    private boolean isLock;
    private long expire;
    private List<ItemOption> maxOptions, minOptions;
    private ItemTemplate template;

    @Builder
    public ItemStore(int id, int itemID, byte sys, int coin, int yen, int gold, boolean isLock, long expire,
                     List<ItemOption> options) {
        this.id = id;
        this.itemID = itemID;
        this.sys = sys;
        this.coin = coin;
        this.yen = yen;
        this.gold = gold;
        this.isLock = isLock;
        this.expire = expire;
        this.maxOptions = OptionStandardizer.mergeAndStandardize(options);
        if (this.maxOptions != null) {
            setMinOptions();
        }
        this.template = ItemManager.getInstance().getItemTemplate(itemID);
    }

    public void setMaxOptions(List<ItemOption> options) {
        this.maxOptions = OptionStandardizer.mergeAndStandardize(options);
        if (this.maxOptions != null) {
            setMinOptions();
        }
    }

    public void setMinOptions() {
        this.minOptions = new ArrayList<>();
        for (ItemOption o : maxOptions) {
            int optionID = OptionStandardizer.getCanonicalId(o.optionTemplate.id);
            int optionParam = o.param;
            int newParam;
            // Nhóm 1: Sát thương tấn công cố định (Tấn công 0)
            if (optionID == 0) {
                newParam = optionParam - 49;
            // Nhóm 2: HP, MP (Flat 6, 7) và % Vật công (8)
            } else if (optionID == 6 || optionID == 7 || optionID == 8) {
                newParam = optionParam - 9;
            // Nhóm 3: Kháng 3 hệ (2, 3, 4), Né (5), Chính xác (10), Chí mạng (14), Phản đòn (15), Kháng tất cả (20)
            } else if (optionID == 2 || optionID == 3 || optionID == 4 || optionID == 5
                    || optionID == 10 || optionID == 14 || optionID == 15 || optionID == 20) {
                newParam = optionParam - 4;
            // Nhóm 4: Tốc độ di chuyển (16)
            } else if (optionID == 16) {
                newParam = optionParam - 2;
            } else {
                newParam = optionParam;
            }
            if (newParam < 0) {
                newParam = 0;
            }
            minOptions.add(new ItemOption(optionID, newParam));
        }
    }

}
