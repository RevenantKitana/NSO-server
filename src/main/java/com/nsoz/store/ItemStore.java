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

    public void setMinOptions() {
        this.minOptions = new ArrayList<>();
        for (ItemOption o : maxOptions) {
            int optionID = o.optionTemplate.id;
            int optionParam = o.param;
            int newParam;
            // Nhóm 1: Sát thương tấn công cố định (Ngoại 0, Nội 1)
            if (optionID == 0 || optionID == 1 || optionID == 21 || optionID == 22 || optionID == 23
                    || optionID == 24 || optionID == 25 || optionID == 26 || optionID == 73 || optionID == 76 || optionID == 87) {
                newParam = optionParam - 49;
            // Nhóm 2: HP, MP (Flat 6, 7) và % Vật công (Ngoại 8, Nội 9)
            } else if (optionID == 6 || optionID == 7 || optionID == 8 || optionID == 9
                    || optionID == 19 || optionID == 77 || optionID == 82 || optionID == 83 || optionID == 117 || optionID == 125) {
                newParam = optionParam - 9;
            // Nhóm 3: Kháng 3 hệ (2, 3, 4), Né (5), Chính xác (10), Chí mạng (14), Phản đòn (15), Kháng tất cả (20)
            } else if (optionID == 2 || optionID == 3 || optionID == 4 || optionID == 5
                    || optionID == 10 || optionID == 11 || optionID == 12 || optionID == 13
                    || optionID == 14 || optionID == 15 || optionID == 17 || optionID == 18
                    || optionID == 20 || optionID == 68 || optionID == 69 || optionID == 70
                    || optionID == 71 || optionID == 72 || optionID == 75 || optionID == 78
                    || optionID == 81 || optionID == 84 || optionID == 86 || optionID == 91
                    || optionID == 92 || optionID == 95 || optionID == 96 || optionID == 97
                    || optionID == 114 || optionID == 115 || optionID == 116 || optionID == 118 || optionID == 126) {
                newParam = optionParam - 4;
            // Nhóm 4: Tốc độ di chuyển (16)
            } else if (optionID == 16 || optionID == 93) {
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
