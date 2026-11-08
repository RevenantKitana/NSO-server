package com.nsoz2.model;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nsoz2.ability.AbilityCustom;
import com.nsoz2.ability.AbilityFromEquip;
import com.nsoz2.ability.AbilityStrategy;
import com.nsoz2.admin.AdminService;
import com.nsoz2.bot.Bot;
import com.nsoz2.clan.Clan;
import com.nsoz2.clan.ClanDAO;
import com.nsoz2.clan.Member;
import com.nsoz2.combat.CombatResult;
import com.nsoz2.combat.CombatService;
import com.nsoz2.combat.engine.CharacterStatsSnapshot;
import com.nsoz2.constants.*;
import com.nsoz2.convert.Converter;
import com.nsoz2.db.jdbc.DbManager;
import com.nsoz2.db.mongodb.MongoDbConnection;
import com.nsoz2.effect.Effect;
import com.nsoz2.effect.EffectManager;
import com.nsoz2.event.*;
import com.nsoz2.fashion.FashionCustom;
import com.nsoz2.fashion.FashionFromEquip;
import com.nsoz2.fashion.FashionStrategy;
import com.nsoz2.item.*;
import com.nsoz2.lib.ImageMap;
import com.nsoz2.lib.ParseData;
import com.nsoz2.lib.RandomCollection;
import com.nsoz2.map.Map;
import com.nsoz2.map.*;
import com.nsoz2.map.item.ItemMap;
import com.nsoz2.map.item.ItemMapFactory;
import com.nsoz2.map.world.*;
import com.nsoz2.map.zones.*;
import com.nsoz2.mob.Mob;
import com.nsoz2.mob.MobManager;
import com.nsoz2.mob.MobTemplate;
import com.nsoz2.model.Invite.PlayerInvite;
import com.nsoz2.model.component.*;
import com.nsoz2.network.Controller;
import com.nsoz2.network.Message;
import com.nsoz2.network.Service;
import com.nsoz2.npc.Npc;
import com.nsoz2.option.ItemOption;
import com.nsoz2.option.SkillOption;
import com.nsoz2.party.Group;
import com.nsoz2.party.MemberGroup;
import com.nsoz2.server.*;
import com.nsoz2.skill.Skill;
import com.nsoz2.stall.Stall;
import com.nsoz2.stall.StallManager;
import com.nsoz2.store.ItemStore;
import com.nsoz2.store.Store;
import com.nsoz2.store.StoreManager;
import com.nsoz2.store.StoreUpgradeManager;
import com.nsoz2.task.*;
import com.nsoz2.thiendia.Ranking;
import com.nsoz2.thiendia.ThienDiaData;
import com.nsoz2.thiendia.ThienDiaManager;
import com.nsoz2.util.Log;
import com.nsoz2.util.NinjaUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CharBlacksmithHandler handles item refinement, gem socketing (ngoc kham),
 * item enhancement (upgrade/tinh luyen), splitting, and item transformation.
 */
public abstract class CharBlacksmithHandler extends CharItemHandler {

    public CharBlacksmithHandler(int id) {
        super(id);
    }

    public void ngocKham(Message ms) {
        lockItem.lock();
        try {
            /// cho pt luyện ngọc
//            if (!isHuman) {
//                warningClone();
//                return;
//            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            byte type = ms.reader().readByte();
            if (type == 0) {
                khamNgoc(ms);
            } else if (type == 1) {
                luyenNgoc(ms);
            } else if (type == 2) {
                gotNgoc(ms);
            } else {
                thaoNgoc(ms);
            }
        } catch (Exception e) {
            Log.logException("err ngoc kham: ", Char.class, e);
        } finally {
            lockItem.unlock();
        }

    }

    public void khamNgoc(Message ms) {
        try {
            int indexEquip = ms.reader().readUnsignedByte();
            int indexNgoc = ms.reader().readUnsignedByte();
            if (indexEquip < 0 || indexEquip >= numberCellBag) {
                return;
            }
            if (indexNgoc < 0 || indexNgoc >= numberCellBag) {
                return;
            }
            Item equip = this.bag[indexEquip];
            if (equip == null) {
                serverDialog("Không tìm thấy trang bị này. Vui lòng đăng nhập lại để kiểm tra!");
                return;
            }
            // fix vk 10x
            if ((!equip.template.isTypeClothe() && !equip.template.isTypeAdorn() && !equip.template.isTypeWeapon()) || equip.template.fashion > -1 && !equip.template.isVk100x()) {
                serverDialog("Vật phẩm này không thể khảm ngọc.");
                return;
            }
            Item ngoc = this.bag[indexNgoc];
            if (ngoc == null) {
                serverDialog("Không tìm thấy ngọc này. Vui lòng đăng nhập lại để kiểm tra!");
                return;
            }
            if (!ngoc.template.isTypeNgocKham()) {
                serverDialog("Vật phẩm này không phải ngọc.");
                return;
            }
            ArrayList<Item> crystals = new ArrayList<>();
            boolean[] list = new boolean[numberCellBag];
            while (ms.reader().available() > 0) {
                int indexCrystal = ms.reader().readUnsignedByte();
                if (indexCrystal < 0 || indexCrystal >= numberCellBag) {
                    continue;
                }
                if (!list[indexCrystal]) {
                    list[indexCrystal] = true;
                    Item crystal = this.bag[indexCrystal];
                    if (crystal != null && crystal.template.isTypeCrystal()) {
                        crystals.add(crystal);

                    }
                }
            }
            if (crystals.isEmpty()) {
                serverDialog("Hãy cho đá vào để khảm ngọc.");
                return;
            }
            int yenKham = 0;
            for (ItemOption option : ngoc.options) {
                if (option.optionTemplate.id == 123) {
                    yenKham = option.param;
                    break;
                }
            }
            long sum = NinjaUtils.sum(this.coin, this.yen);
            if (yenKham > sum) {
                serverDialog("Không đủ yên hoặc xu để khảm ngọc.");
                return;
            }
            for (Item itm : equip.gems) {
                if (itm.id == ngoc.id) {
                    serverDialog("Không được khảm ngọc cùng loại.");
                    return;
                }
            }
            History history = new History(this.id, History.KHAM_NGOC);
            history.setBefore(this.coin, user.gold, this.yen);
            history.addItem(equip);
            if (yenKham > this.yen) {
                yenKham -= this.yen;
                this.yen = 0;
                this.coin -= yenKham;
            } else {
                this.yen -= yenKham;
            }
            history.setAfter(this.coin, user.gold, this.yen);

            long total = 0;
            for (Item crystal : crystals) {
                total += GameData.UP_CRYSTAL[crystal.template.id];
            }
            int percent = (int) (total * 100 / GameData.UP_WEAPON[ngoc.upgrade]);
            if (percent > GameData.MAX_PERCENT[ngoc.upgrade]) {
                percent = GameData.MAX_PERCENT[ngoc.upgrade];
            }
            int rand = NinjaUtils.nextInt(100);
            int type = 6;
            if (rand < percent) {
                equip.addGem(ngoc);
                removeItem(ngoc.index, 1, false);
                getService().itemInfo(equip, (byte) 3, (byte) equip.index);
                type = 5;
            }
            equip.isLock = true;
            history.addItem(equip);
            history.addItem(ngoc);
            for (Item item : crystals) {
                history.addItem(item);
                removeItem(item.index, 1, false);
            }
            history.setTime(System.currentTimeMillis());
            History.insert(history);
            getService().upgrade((byte) type, equip);
        } catch (Exception e) {
            Log.logException("err kham ngoc: ", Char.class, e);
        }
    }

    public void luyenNgoc(Message ms) {
        try {
            if (!isVillage()) { /// chặn luyện ngọc
                serverMessage("Bạn chỉ có thể luyện ngọc tại làng");
                getService().endDlg(true);
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            int index = ms.reader().readUnsignedByte();
            if (index < 0 || index >= numberCellBag) {
                return;
            }
            Item item = this.bag[index];
            if (item == null) {
                serverDialog("Có lỗi xảy ra. Vui lòng đăng nhập lại để kiểm tra!");
                return;
            }
            if (item.template.type != ItemTemplate.TYPE_NGOC_KHAM) {
                serverDialog("Không thể luyện ngọc.");
                return;
            }
            if (item.upgrade >= 10) {
                serverDialog(language.getString("CAN_NOT_LUYENNGOC"));
                return;
            }
            int expUp = 0;
            ArrayList<Item> items = new ArrayList<>();
            boolean[] list = new boolean[numberCellBag];
            while (ms.reader().available() > 0) {
                int ngocIndex = ms.reader().readUnsignedByte();
                if (ngocIndex < 0 || ngocIndex >= numberCellBag || ngocIndex == index) {
                    continue;
                }
                if (!list[ngocIndex]) {
                    list[ngocIndex] = true;
                    Item ngoc = this.bag[ngocIndex];
                    if (ngoc != null && ngoc.template.type == ItemTemplate.TYPE_NGOC_KHAM) {
                        expUp += GameData.NGOC_KHAM_EXP[ngoc.upgrade][1];
                        items.add(ngoc);
                    }
                }
            }

            if (expUp == 0) {
                serverDialog("Hãy cho ngọc vào để luyện.");
                return;
            }
            History history = new History(this.id, History.LUYEN_NGOC);
            history.setBefore(this.coin, user.gold, this.yen);
            history.setAfter(this.coin, user.gold, this.yen);
            history.addItem(item);
            int expNew = 0;
            for (ItemOption option : item.options) {
                if (option.optionTemplate.id == 104) {
                    expNew = option.param;
                    break;
                }
            }
            expNew += expUp;
            int upgradeOld = item.upgrade;
            int expMax = GameData.NGOC_KHAM_EXP[upgradeOld][0];
            if (expNew > expMax) {
                expNew -= expMax;
                item.upgrade++;
                for (ItemOption option : item.options) {
                    switch (option.optionTemplate.id) {
                        case 73:
                            // tấn công
                            if (option.param > 0) {
                                int[] paramUp = {0, 50, 100, 150, 200, 250, 300, 350, 400, 450};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 50;
                            }
                            break;
                        case 115:
                            // né đòn
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 130};
                                option.param += paramUp[upgradeOld];
                            } else {
                                int[] paramUp = {0, 60, 40, 20, 20, 15, 15, 10, 10, 5};
                                option.param -= paramUp[upgradeOld];
                            }
                            break;
                        case 124:
                            // giảm trừ sát thương
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 15, 20, 25, 30, 35, 40, 45, 50};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 20;
                            }
                            break;
                        case 114:
                            // chí mạng
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 4;
                            }
                            break;
                        case 126:
                            // phản đòn
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 4;
                            }
                            break;
                        case 118:
                            // kháng tất cả
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[upgradeOld];
                            } else {
                                int[] paramUp = {0, 20, 20, 20, 15, 15, 15, 10, 10, 5};
                                option.param -= paramUp[upgradeOld];
                            }
                            break;
                        case 102:
                            // sát thương lên quái
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 400, 600, 800, 1000, 1400, 1400, 1600};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 200;
                            }
                            break;
                        case 105:
                            // sát thương chí mạng
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 800, 1100};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 200;
                            }
                            break;
                        case 103:
                            // sát thương lên người
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 800, 1200};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 150;
                            }
                            break;
                        case 121:
                            // kháng sát thương chí mạng
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 4;
                            }
                            break;
                        case 117:
                        case 125:
                            // hp, mp tối đa
                            if (option.param > 0) {
                                int[] paramUp = {0, 20, 30, 40, 60, 80, 120, 150, 200, 250};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 150;
                            }
                            break;
                        case 116:
                            // chính xác
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 130};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 100;
                            }
                            break;
                        case 119:
                        case 229:
                            // hồi phục hp,
                            // mp
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 130};
                                option.param += paramUp[upgradeOld];
                            } else {
                                option.param -= 10;
                            }
                            break;
                        case 104:
                            option.param = expNew;
                            break;
                        case 123:
                            int[] giaKham = {800000, 1600000, 2400000, 3200000, 4800000, 7200000, 10800000, 15600000,
                                    20100000, 28100000};
                            option.param = giaKham[item.upgrade - 1];
                            break;
                        default:
                            break;
                    }
                }
            }

            for (ItemOption option : item.options) {
                if (option.optionTemplate.id == 104) {
                    option.param = expNew;
                    break;
                }
            }
            item.isLock = true;
            history.addItem(item);
            for (Item itm : items) {
                history.addItem(itm);
                removeItem(itm.index, 1, false);
            }
            history.setTime(System.currentTimeMillis());
            History.insert(history);

            getService().ngocKham((byte) 1, item);
            getService().itemInfo(item, (byte) 3, (byte) index);
            if (item.upgrade > upgradeOld) {
                serverDialog("Đã luyện ngọc lên cấp " + item.upgrade);
            }
        } catch (Exception e) {
            Log.logException("err luyen ngoc: ", Char.class, e);
        }
    }

    public void gotNgoc(Message ms) {
        try {
            int index = ms.reader().readUnsignedByte();
            if (index < 0 || index >= numberCellBag) {
                return;
            }
            Item item = this.bag[index];
            if (item != null) {
                int coin = GameData.COIN_GOT_NGOC[item.upgrade];
                if (this.coin < coin) {
                    serverDialog(language.getString("NOT_ENOUGH_COIN_GOTNGOC"));
                    return;
                }
                History history = new History(this.id, History.GOT_NGOC);
                history.setBefore(this.coin, user.gold, this.yen);
                addCoin(-coin);
                history.setAfter(this.coin, user.gold, this.yen);
                history.addItem(item);
                for (ItemOption itemOption : item.options) {
                    int param = itemOption.param;
                    if (param < -1) {
                        int down = NinjaUtils.nextInt(1, Math.abs(param) - 1);
                        itemOption.param += down;
                    }
                }
                history.addItem(item);
                history.setTime(System.currentTimeMillis());
                History.insert(history);
                serverDialog(String.format("%s đã được gọt.", item.template.name));
                getService().itemInfo(item, (byte) 3, (byte) index);
            }
        } catch (Exception e) {
            Log.logException("err got ngoc: ", Char.class, e);
        }
    }

    public void thaoNgoc(Message ms) {
        try {
            int index = ms.reader().readUnsignedByte();
            Item item = null;
            try {
                item = this.bag[index];
            } catch (Exception e) {
                serverDialog("Có lỗi xảy ra. Vui lòng đăng nhập lại để kiểm tra!");
                return;
            }

            if (item.gems == null || item.gems.isEmpty()) {
                serverDialog("Trang bị này không có ngọc.");
                return;
            }
            if (item.gems.size() > getSlotNull()) {
                warningBagFull();
                return;
            }
            int yenThaoNgoc = 0;
            for (Item gem : item.gems) {
                yenThaoNgoc += Item.GIA_KHAM[gem.upgrade - 1] / 2;
            }
            if (yenThaoNgoc > NinjaUtils.sum(this.coin, this.yen)) {
                serverDialog("Không đủ yên hoặc xu để tháo ngọc.");
                return;
            }
            History history = new History(this.id, History.THAO_NGOC);
            history.setBefore(this.coin, user.gold, this.yen);
            if (yenThaoNgoc > this.yen) {
                yenThaoNgoc -= this.yen;
                this.yen = 0;
                this.coin -= yenThaoNgoc;
            } else {
                this.yen -= yenThaoNgoc;
            }
            history.setAfter(this.coin, user.gold, this.yen);
            history.addItem(item);
            ArrayList<Item> items = new ArrayList<>();
            for (Item itm : item.gems) {
                items.add(itm);
                addItemToBag(itm);
            }
            item.gems.clear();
            history.addItem(item);
            for (Item itm : items) {
                history.addItem(itm);
            }
            history.setTime(System.currentTimeMillis());
            History.insert(history);
            getService().ngocKham((byte) 3, null);
            getService().itemInfo(item, (byte) 3, (byte) index);
        } catch (Exception e) {
            Log.logException("err thao ngoc: ", Char.class, e);
        }
    }

    public void pieceTogether(Item item) {
        int newItemID = -1;
        switch (item.id) {
            case ItemName.MANH_NON_JIRAI_:
                newItemID = ItemName.NON_JIRAI;
                break;

            case ItemName.MANH_NON_JUMITO:
                newItemID = ItemName.NON_JUMITO;
                break;

            case ItemName.MANH_AO_JIRAI_:
                newItemID = ItemName.AO_JIRAI;
                break;

            case ItemName.MANH_AO_JUMITO:
                newItemID = ItemName.AO_JUMITO;
                break;

            case ItemName.MANH_GANG_TAY_JIRAI_:
                newItemID = ItemName.GANG_TAY_JIRAI;
                break;

            case ItemName.MANH_GANG_TAY_JUMITO:
                newItemID = ItemName.GANG_TAY_JUMITO;
                break;

            case ItemName.MANH_QUAN_JIRAI_:
                newItemID = ItemName.QUAN_JIRAI_;
                break;

            case ItemName.MANH_QUAN_JUMITO:
                newItemID = ItemName.QUAN_JUMITO;
                break;

            case ItemName.MANH_GIAY_JIRAI_:
                newItemID = ItemName.GIAY_JIRAI;
                break;

            case ItemName.MANH_GIAY_JUMITO:
                newItemID = ItemName.GIAY_JUMITO;
                break;

            case ItemName.MANH_NGOC_BOI_JIRAI_:
                newItemID = ItemName.NGOC_BOI_JIRAI;
                break;

            case ItemName.MANH_NGOC_BOI_JUMITO:
                newItemID = ItemName.NGOC_BOI_JUMITO;
                break;

            case ItemName.MANH_DAY_CHUYEN_JIRAI_:
                newItemID = ItemName.DAY_CHUYEN_JIRAI;
                break;

            case ItemName.MANH_DAY_CHUYEN_JUMITO:
                newItemID = ItemName.DAY_CHUYEN_JUMITO;
                break;

            case ItemName.MANH_NHAN_JIRAI_:
                newItemID = ItemName.NHAN_JIRAI;
                break;

            case ItemName.MANH_NHAN_JUMITO:
                newItemID = ItemName.NHAN_JUMITO;
                break;

            case ItemName.MANH_PHU_JIRAI_:
                newItemID = ItemName.PHU_JIRAI;
                break;

            case ItemName.MANH_PHU_JUMITO:
                newItemID = ItemName.PHU_JUMITO;
                break;

        }
        int quantityNeed = 0;
        Item itm = getItemInCollectionBox(newItemID);
        if (itm == null) {
            quantityNeed = 1000;
        } else {
            if (itm.upgrade >= 10) {
                getService().serverMessage(String.format("%s đã đạt cấp tối đa, không thể nâng cấp", itm.template.name));
                return;
            }
            quantityNeed = 1000 * (itm.upgrade + 1);
        }
        if (!item.has(quantityNeed)) {
            if (itm == null) {
                getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể chế tạo", quantityNeed));
            } else {
                getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể nâng cấp", quantityNeed));
            }
        } else {
            removeItem(item.index, quantityNeed, true);
            if (itm != null) {
                itm.upgrade++;
                getService().serverMessage(String.format("Chúc mừng bạn đã nâng cấp thành công %s lên cấp %d", itm.template.name, itm.upgrade));
            } else {
                itm = ItemFactory.getInstance().newItem(newItemID);
                itm.upgrade = 1;
                itm.yen = 0;
                collectionBox.add(itm);
                getService().serverMessage(String.format("Chúc mừng bạn đã tạo thành công %s", itm.template.name));
            }
        }
    }

    public int checkTypeItem(Item item) {
        int index = -1;
        for (int i = 0; i < item.options.size(); i++) {
            int id = item.options.get(i).optionTemplate.id;
            int type = item.options.get(i).optionTemplate.type;
            if (type == 0 && id == 157) {
                index = i;
                break;
            }

        }

        if (item.options.get(index + 1).optionTemplate.id == 73 && item.options.get(index + 2).optionTemplate.id == 69 && item.options.get(index + 3).optionTemplate.id == 116) {
            return 1;
        } else if (item.options.get(index + 1).optionTemplate.id == 115 && item.options.get(index + 2).optionTemplate.id == 118 && item.options.get(index + 3).optionTemplate.id == 121) {
            return 2;
        } else if (item.options.get(index + 1).optionTemplate.id == 6 && item.options.get(index + 2).optionTemplate.id == 7 && item.options.get(index + 3).optionTemplate.id == 128) {
            return 3;
        } else {
            return 0;
        }


    }

    public void updateItemKhaiHoa(Item item) {
        int cap = 0;
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 157) {
                cap = option.param;
                option.param += 1;
                break;
            }
        }

        int index = -1;
        for (int i = 0; i < item.options.size(); i++) {
            int id = item.options.get(i).optionTemplate.id;
            int type = item.options.get(i).optionTemplate.type;
            if (type == 0 && id == 157) {
                index = i;
                break;
            }

        }
        if (index != -1) {

            if (getOption(item.options, index + 1).optionTemplate.id == 73 && getOption(item.options, index + 2).optionTemplate.id == 69 && getOption(item.options, index + 3).optionTemplate.id == 116) {
                item.options.get(index + 1).param += NinjaUtils.sucmanhHPmP[cap - 1];
                item.options.get(index + 2).param += NinjaUtils.chimang[cap - 1];
                item.options.get(index + 3).param += NinjaUtils.chinhxac_nedon[cap - 1];
            }
            if (getOption(item.options, index + 1).optionTemplate.id == 115 && getOption(item.options, index + 2).optionTemplate.id == 118 && getOption(item.options, index + 3).optionTemplate.id == 121) {
                item.options.get(index + 1).param += NinjaUtils.chinhxac_nedon[cap - 1];
                item.options.get(index + 2).param += NinjaUtils.khangAll[cap - 1];
                item.options.get(index + 3).param += NinjaUtils.khangChimang[cap - 1];
            }
            if (getOption(item.options, index + 1).optionTemplate.id == 6 && getOption(item.options, index + 2).optionTemplate.id == 7 && getOption(item.options, index + 3).optionTemplate.id == 128) {
                item.options.get(index + 1).param += NinjaUtils.sucmanhHPmP[cap - 1];
                item.options.get(index + 2).param += NinjaUtils.sucmanhHPmP[cap - 1];
                item.options.get(index + 3).param += NinjaUtils.hpToida[cap - 1];
                item.options.get(index + 4).param += NinjaUtils.hoiHPMP[cap - 1];

            }
        }


    }

    public ItemOption getOption(ArrayList<ItemOption> options, int index) {

        return options.get(index);

    }

    public void thangCapChinhPhuc(Item item) {
        int cap = 0;
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 157) {
                cap = option.param;
            }
        }
        if (this.coin < NinjaUtils.xuThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangCap[cap]) + " xu.");
            return;
        }
        if (this.coin < NinjaUtils.luongThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangCap[cap]) + " lượng.");
            return;
        }
        if (this.getQuantityItemById(ItemName.DA_CHINH_PHUC) < NinjaUtils.quantity[cap]) {
            serverDialog("Bạn không đủ " + NinjaUtils.quantity[cap] + " viên đá chinh phục.");
            return;
        }
        addCoin(-NinjaUtils.xuThangCap[cap]);
        addGold(-NinjaUtils.luongThangCap[cap]);
        removeItemBags(ItemName.DA_CHINH_PHUC, NinjaUtils.quantity[cap]);
        if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangCap[0]) {
            item.options.add(new ItemOption(0, NinjaUtils.nextInt(90, 100)));
            item.options.add(new ItemOption(14, 5));
            item.options.add(new ItemOption(10, NinjaUtils.nextInt(8, 10)));
            for (ItemOption option : item.options) {
                if (option.optionTemplate.id == 157) {
                    option.param += 1;
                }
            }
            serverMessage("Thăng cấp thành công");
            setAbility();
            setFashion();

            getService().itemInfo(item, (byte) 4, IndexUI);
        } else {
            serverMessage("Thăng cấp thất bại.");
        }


    }

    public void thangCapVoThuong(Item item) {
        int cap = 0;
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 157) {
                cap = option.param;
            }
        }
        if (this.coin < NinjaUtils.xuThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangCap[cap]) + " xu.");
            return;
        }
        if (this.coin < NinjaUtils.luongThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangCap[cap]) + " lượng.");
            return;
        }
        if (this.getQuantityItemById(ItemName.DA_VO_THUONG) < NinjaUtils.quantity[cap]) {
            serverDialog("Bạn không đủ " + NinjaUtils.quantity[cap] + " viên đá vô thương.");
            return;
        }
        addCoin(-NinjaUtils.xuThangCap[cap]);
        addGold(-NinjaUtils.luongThangCap[cap]);
        removeItemBags(ItemName.DA_VO_THUONG, NinjaUtils.quantity[cap]);
        if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangCap[0]) {
            item.options.add(new ItemOption(5, NinjaUtils.nextInt(8, 10)));
            item.options.add(new ItemOption(20, NinjaUtils.nextInt(8, 10)));
            item.options.add(new ItemOption(121, 1));
            for (ItemOption option : item.options) {
                if (option.optionTemplate.id == 157) {
                    option.param += 1;
                }
            }
            serverMessage("Thăng cấp thành công");
            setAbility();
            setFashion();
        } else {
            serverMessage("Thăng cấp thất bại.");
        }

        getService().itemInfo(item, (byte) 4, IndexUI);
    }

    public void thangCapPhucSinh(Item item) {
        int cap = 0;
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 157) {
                cap = option.param;
            }
        }
        if (this.coin < NinjaUtils.xuThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangCap[cap]) + " xu.");
            return;
        }
        if (this.coin < NinjaUtils.luongThangCap[cap]) {
            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangCap[cap]) + " lượng.");
            return;
        }
        if (this.getQuantityItemById(ItemName.DA_PHUC_SINH) < NinjaUtils.quantity[cap]) {
            serverDialog("Bạn không đủ " + NinjaUtils.quantity[cap] + " viên đá phục sinh.");
            return;
        }
        addCoin(-NinjaUtils.xuThangCap[cap]);
        addGold(-NinjaUtils.luongThangCap[cap]);
        removeItemBags(ItemName.DA_PHUC_SINH, NinjaUtils.quantity[cap]);
        if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangCap[0]) {
            item.options.add(new ItemOption(6, NinjaUtils.nextInt(45, 50)));
            item.options.add(new ItemOption(7, NinjaUtils.nextInt(45, 50)));
            item.options.add(new ItemOption(128, 1));
            item.options.add(new ItemOption(99, NinjaUtils.nextInt(8, 10)));
            for (ItemOption option : item.options) {
                if (option.optionTemplate.id == 157) {
                    option.param += 1;
                }
            }
            serverMessage("Thăng cấp thành công");
            setAbility();
            setFashion();
        } else {
            serverMessage("Thăng cấp thất bại.");
        }


        getService().itemInfo(item, (byte) 4, IndexUI);
    }

    public int checkThangNguong(Item item) {
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 156) {
                return option.param;
            }
        }
        return 0;
    }

    public int checkThangCap(Item item) {
        for (ItemOption option : item.options) {
            if (option.optionTemplate.id == 157) {
                return option.param;
            }
        }
        return 0;
    }

//            } else if (commandBox == DOI_LONG_DEN_XU) {
//                Event event = Event.getEvent();
//                event.doiLongDen(this, Event.DOI_BANG_XU, indexUI);
//            } else if (commandBox == DOI_LONG_DEN_LUONG) {
//                Event event = Event.getEvent();
//                event.doiLongDen(this, Event.DOI_BANG_LUONG, indexUI);
//            }
//        } catch (Exception ex) {
//             Log.logException("item box to bag err: " + ex.getMessage(), ex);
//            serverDialog(ex.getMessage());
//        }
//    }

    private boolean itemNotSplit(int itemId) {
        int[] listId = new int[]{1117, 2012, 2013};
        for (int i : listId) {
            if (itemId == i) {
                return true;
            }
        }
        return false;
    }

    public void splitItem(Message ms) {
        lockItem.lock();
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            int index = ms.reader().readUnsignedByte();
            if (index < 0 || index >= this.numberCellBag) {
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            Item item = this.bag[index];
            if (item != null && (item.template.isTypeWeapon() || item.template.isTypeClothe() || item.template.isTypeAdorn())) {
                if (item.upgrade > 0 && !itemNotSplit(item.id)) {
                    int num = 0;
                    if (item.template.isTypeWeapon()) {
                        for (byte i = 0; i < item.upgrade; i++) {
                            num += GameData.UP_WEAPON[i];
                        }
                    } else if (item.template.type % 2 == 0) {
                        for (byte i = 0; i < item.upgrade; i++) {
                            num += GameData.UP_CLOTHE[i];
                        }
                    } else {
                        for (byte i = 0; i < item.upgrade; i++) {
                            num += GameData.UP_ADORN[i];
                        }
                    }
                    num /= 2;
                    int num2 = 0;
                    List<Item> list = new ArrayList<>();
                    for (int n = GameData.UP_CRYSTAL.length - 1; n >= 0; n--) {
                        if (num >= GameData.UP_CRYSTAL[n]) {
                            Item item2 = ItemFactory.getInstance().newItem(n);
                            item2.isLock = true;
                            list.add(item2);
                            num -= GameData.UP_CRYSTAL[n];
                            n++;
                            num2++;
                        }
                    }
                    if (num2 > getSlotNull()) {
                        warningBagFull();
                        return;
                    }
                    int i2 = 0;
                    int size = list.size();
                    for (int i = 0; i < this.numberCellBag; i++) {
                        if (this.bag[i] == null && i2 < size) {
                            this.bag[i] = list.get(i2);
                            this.bag[i].index = i;
                            i2++;
                        }
                    }
                    int upgradeOld = item.upgrade;
                    item.next(-upgradeOld);
                    getService().splitItem(list);
                } else {
                    serverDialog("Không thể tách trang bị chưa nâng cấp");
                }
            }
        } catch (Exception ex) {
            Log.logException("tach vật phẩm err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void upPearl(Message ms, boolean isCoin) {
        lockItem.lock();
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            Vector<Item> crystals = new Vector<>();
            boolean[] list = new boolean[numberCellBag];
            while (ms.reader().available() > 0) {
                int indexItem = ms.reader().readUnsignedByte();
                if (indexItem < 0 || indexItem >= numberCellBag) {
                    continue;
                }
                if (!list[indexItem]) {
                    list[indexItem] = true;
                    if (bag[indexItem] != null && bag[indexItem].id <= 11) {
                        if (bag[indexItem].id == 11) {
                            serverDialog(language.getString("UP_MAX"));
                            return;
                        }
                        crystals.add(bag[indexItem]);
                    }
                }
            }
            int size = crystals.size();
            if (size > 24 || size < 2) {
                serverDialog(language.getString("NOT_ENOUGH_UPPEARL"));
                return;
            }
            int percent = 0;
            int i = 0;
            for (Item item : crystals) {
                percent += GameData.UP_CRYSTAL[item.id];
            }
            if (percent > 0) {
                for (i = GameData.UP_CRYSTAL.length - 1; i >= 0; i--) {
                    if (percent > GameData.UP_CRYSTAL[i]) {
                        break;
                    }
                }
            }
            if (i >= GameData.UP_CRYSTAL.length - 1) {
                i = GameData.UP_CRYSTAL.length - 2;
            }
            percent = percent * 100 / GameData.UP_CRYSTAL[i + 1];
            if (percent <= 40) {
                serverDialog("Tỉ lệ trên 40% mới có thể luyện");
                return;
            }
            int id = i + 1;
            int indexNull = getIndexByItem(null);
            if (indexNull == -1) {
                warningBagFull();
                return;
            }
            int coin = GameData.COIN_UP_CRYSTAL[i + 1];
            if (isCoin) {
                if (this.coin < coin) {
                    serverDialog(language.getString("NOT_ENOUGH_COIN_UPPEARL"));
                    return;
                }
                this.coin -= coin;
            } else {
                if ((this.coin + this.yen) < coin) {
                    serverDialog(language.getString("NOT_ENOUGH_COIN_UPPEARL"));
                    return;
                }
                if (this.yen < coin) {
                    coin -= this.yen;
                    this.yen = 0;
                } else {
                    this.yen -= coin;
                    coin = 0;
                }
                this.coin -= coin;
            }
            for (Item item : crystals) {
                removeItem(item.index, item.getQuantity(), false);
            }
            byte type = 0;
            int rd = NinjaUtils.nextInt(100);
            if (rd < percent) {
                if (id == 11) {
                    id = 10;
                }
                type = 1;
                Item item = ItemFactory.getInstance().newItem(id);
                item.index = indexNull;
                item.isLock = true;
                bag[indexNull] = item;
                if (Event.isEvent() && rd < 10) {
                    int itemID = Event.getEvent().randomItemID();
                    if (itemID != -1) {
                        Item itemE = ItemFactory.getInstance().newItem(itemID);
                        itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                        addItemToBag(itemE);
                    }
                }
            } else {
                Item item = ItemFactory.getInstance().newItem(id - 1);
                item.index = indexNull;
                item.isLock = true;
                bag[indexNull] = item;
            }
            getService().upPearl(isCoin, type, bag[indexNull]);
        } catch (Exception ex) {
            Log.logException("luyen da err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public int getIndexByItem(Item item) {
        for (int i = 0; i < this.numberCellBag; i++) {
            if (this.bag[i] == item) {
                return i;
            }
        }
        return -1;
    }

    public void convertUpgrade(Message ms) {
        lockItem.lock();
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            int index1 = ms.reader().readUnsignedByte();
            int index2 = ms.reader().readUnsignedByte();
            int index3 = ms.reader().readUnsignedByte();
            int indexMax = Math.max(Math.max(index1, index2), index3);
            int indexMin = Math.min(Math.min(index1, index2), index3);
            if (indexMax >= this.numberCellBag || indexMin < 0) {
                return;
            }
            Item item1 = this.bag[index1];
            Item item2 = this.bag[index2];
            Item item3 = this.bag[index3];
            if (item1 == null || item2 == null || item3 == null) {
                return;
            }
            // fix vk 10x
            if (item1.template.isTypeWeapon() != item2.template.isTypeWeapon() || item1.template.isTypeAdorn() != item2.template.isTypeAdorn() || item1.template.isTypeClothe() != item2.template.isTypeClothe() || (item1.template.fashion != item2.template.fashion && !item1.template.isVk100x() && !item2.template.isVk100x())) {
                serverDialog("Trang bị không cùng loại.");
                return;
            }
            // fix vk 10x
            if (item2.template.fashion > 0 && !item2.template.isVk100x()) {
                serverDialog("Không thể chuyển hóa sang trang bị này.");
                return;
            }
            // fix vk 10x
            if ((!item1.template.isTypeClothe() && !item1.template.isTypeAdorn() && !item1.template.isTypeWeapon()) || item1.template.fashion > -1 && !item2.template.isVk100x()) {
                serverDialog("Không thể chuyển hóa sang trang bị này.");
                return;
            }
            if ((!item2.template.isTypeClothe() && !item2.template.isTypeAdorn() && !item2.template.isTypeWeapon()) || item2.template.fashion > -1 && !item2.template.isVk100x()) {
                serverDialog("Không thể chuyển hóa sang trang bị này.");
                return;
            }
            if (item1.expire != -1) {
                serverDialog("Trang bị này không thể chuyển hoá.");
                return;
            }
            if (item1.upgrade == 0) {
                serverDialog("Trang bị chưa nâng câp.");
                return;
            }
            if (item2.upgrade > 0) {
                serverDialog("Trang bị cần chuyển hoá sang không được nâng cấp.");
                return;
            }
            if (item1.template.level > item2.template.level) {
                serverDialog("Trang bị chuyển hoá sang phải có cấp độ ngang bằng hoặc lớn hơn.");
                return;
            }
            if (item3.template.type == 27) {
                if ((item3.id == 270 && item1.upgrade > 13) || (item3.id == 269 && item1.upgrade > 10)) {
                    serverDialog(item3.template.name + " không phù hợp để chuyển hoá trang bị này.");
                    return;
                }
                byte upgrade = item1.upgrade;
                item2.upgrade = 0;
                item2.next(upgrade);
                item2.isLock = true;
                item1.next(-upgrade);
                item1.isLock = true;
                getService().convertUpgrade(item1, item2);
                removeItem(item3.index, item3.getQuantity(), true);
            } else {
                serverDialog(item3.template.name + " không phải vật phẩm chuyển hoá");
            }
        } catch (Exception ex) {
            Log.logException("chuyen hoa err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void dichChuyen(Message ms) {
        lockItem.lock();
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            int index = ms.reader().readUnsignedByte();
            Item item = this.bag[index];
            if (item != null) {
                // fix dịch chuyển ruby
                if (item.template.isTypeClothe() || item.template.isTypeAdorn() && item.template.id != 1117 || item.template.isTypeWeapon() || item.template.isTypeWeapon()) {
                    if (item.upgrade >= 12) {
                        for (ItemOption option : item.options) {
                            if (option.optionTemplate.id == 85) {
                                serverDialog("Vật phẩm này đã dịch chuyển");
                                return;
                            }
                        }
                        Vector<Item> items = new Vector<>();
                        boolean[] list = new boolean[numberCellBag];
                        while (ms.reader().available() > 0) {
                            int ctt = ms.reader().readUnsignedByte();
                            if (ctt < 0 || ctt >= numberCellBag) {
                                continue;
                            }
                            if (!list[ctt]) {
                                list[ctt] = true;
                                if (this.bag[ctt] != null && this.bag[ctt].id == 454) {
                                    items.add(this.bag[ctt]);
                                }
                            }

                        }
                        if (items.size() >= 20) {
                            int indexOption = -1;
                            for (int i = 0; i < item.options.size(); i++) {
                                int type = item.options.get(i).optionTemplate.type;
                                int id = item.options.get(i).optionTemplate.id;
                                if (type >= 0 && type <= 7 && id != 156 && id != 157) {
                                    continue;
                                }
                                indexOption = i;
                                break;
                            }
                            if (indexOption == -1) {
                                indexOption = item.options.size();
                            }
                            getService().endDlg(true);
                            item.options.add(indexOption, new ItemOption(85, 0));
                            if (item.template.type == 0) {
                                int[] optionId = {3, 2, 4};
                                item.options.add(indexOption + 1, new ItemOption(optionId[item.sys - 1], 5));
                                item.options.add(indexOption + 2, new ItemOption(121, 5));
                            } else if (item.template.type == 1) {
                                item.options.add(indexOption + 1, new ItemOption(0, NinjaUtils.nextInt(250, 400)));
                                int[] optionId = {88, 89, 90};
                                item.options.add(indexOption + 2, new ItemOption(optionId[item.sys - 1], NinjaUtils.nextInt(350, 600)));
                            } else if (item.template.type == 2) {
                                item.options.add(indexOption + 1, new ItemOption(47, NinjaUtils.nextInt(24, 28)));
                                item.options.add(indexOption + 2, new ItemOption(15, NinjaUtils.nextInt(10, 14)));
                            } else if (item.template.type == 3) {
                                item.options.add(indexOption + 1, new ItemOption(20, 5));
                                item.options.add(indexOption + 2, new ItemOption(121, 5));
                            } else if (item.template.type == 4) {
                                item.options.add(indexOption + 1, new ItemOption(10, NinjaUtils.nextInt(76, 124)));
                                item.options.add(indexOption + 2, new ItemOption(8, NinjaUtils.nextInt(76, 124)));
                            } else if (item.template.type == 5) {
                                int[] optionId = {3, 2, 4};
                                item.options.add(indexOption + 1, new ItemOption(optionId[item.sys - 1], 5));
                                item.options.add(indexOption + 2, new ItemOption(14, NinjaUtils.nextInt(9, 11)));
                            } else if (item.template.type == 6) {
                                item.options.add(indexOption + 1, new ItemOption(7, NinjaUtils.nextInt(250, 450)));
                                item.options.add(indexOption + 2, new ItemOption(6, NinjaUtils.nextInt(250, 450)));
                            } else if (item.template.type == 7) {
                                int[] optionId = {3, 2, 4};
                                item.options.add(indexOption + 1, new ItemOption(optionId[item.sys - 1], 5));
                                optionId = new int[]{88, 89, 90};
                                item.options.add(indexOption + 2, new ItemOption(optionId[item.sys - 1], NinjaUtils.nextInt(350, 600)));
                            } else if (item.template.type == 8) {
                                item.options.add(indexOption + 1, new ItemOption(7, NinjaUtils.nextInt(250, 450)));
                                item.options.add(indexOption + 2, new ItemOption(5, NinjaUtils.nextInt(76, 124)));
                            } else if (item.template.type == 9) {
                                item.options.add(indexOption + 1, new ItemOption(5, NinjaUtils.nextInt(76, 124)));
                                item.options.add(indexOption + 2, new ItemOption(6, NinjaUtils.nextInt(250, 450)));
                            }
                            if (item.isLock) {
                                getService().itemInfo(item, (byte) 3, (byte) index);
                            } else {
                                bag[index] = Converter.getInstance().newItem(item);
                                bag[index].index = index;
                                bag[index].isLock = true;
                                bag[index].setQuantity(bag[index].getQuantity());
                                getService().addItem(bag[index]);
                            }

                            serverMessage("Trang bị đã được dịch chuyển");
                            for (int i = 0; i < 20; i++) {
                                removeItem(items.get(i).index, 1, true);
                            }

                        } else {
                            serverDialog("Cần phải có đủ 20 chuyển tinh thạch để dịch chuyển trang bi");
                        }
                    } else {
                        serverDialog("Vui lòng nâng cấp trang bị lên cấp 12");
                    }
                } else {
                    serverDialog("Vật phẩm này không dùng trong dịch chuyển");
                }
            } else {
                serverDialog("Vui lòng đăng nhập lại");
            }
        } catch (IOException ex) {
            Log.logException("dich chuyen err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void tinhLuyen(Message ms) {
        lockItem.lock();
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            int indexUI = ms.reader().readUnsignedByte();
            Item item = this.bag[indexUI];
            if (item != null) {
                // fix tinh luyện ruby
                if (item.template.isTypeClothe()
                        || item.template.isTypeAdorn() && item.template.id != 1117
                        || item.template.isTypeWeapon()
                        || item.template.type == 12 || (item.template.isTypeMount() && item.template.type != 33)) {
                    for (int i = 0; i < item.options.size(); i++) {
                        ItemOption option = item.options.get(i);
                        int optionId = option.optionTemplate.id;
                        int optionParam = option.param;
                        if (optionId == 85) {
                            int[] percents = new int[]{60, 45, 34, 26, 20, 15, 11, 8, 6};
                            int[] yens = new int[]{150000, 247500, 408375, 673819, 1111801, 2056832, 4010822, 7420021, 12243035};
                            byte[] quantitys = new byte[]{3, 5, 9, 4, 7, 10, 5, 7, 9};
                            int id = (optionParam >= 3) ? ((optionParam >= 6) ? 457 : 456) : 455;
                            Vector<Item> items = new Vector<>();
                            boolean[] list = new boolean[numberCellBag];
                            while (ms.reader().available() > 0) {
                                int index = ms.reader().readUnsignedByte();
                                if (index < 0 || index >= numberCellBag) {
                                    continue;
                                }
                                if (!list[index]) {
                                    list[index] = true;
                                    Item itm = this.bag[index];
                                    if (itm != null) {
                                        if (itm.id == id) {
                                            items.add(itm);
                                        }
                                    }
                                }
                            }
                            int quantity = quantitys[optionParam];
                            if (items.size() < quantity) {
                                ItemTemplate template = ItemManager.getInstance().getItemTemplate(id);
                                serverDialog("Cần phải có đủ " + quantity + " " + template.name + " để tinh luyện trang bị");
                                return;
                            }
                            int coin = yens[optionParam];
                            if ((long) coin > (this.coin + this.yen)) {
                                serverDialog(language.getString("NOT_ENOUGH_MONEY"));
                                return;
                            }
                            getService().endDlg(true);
                            int rand = NinjaUtils.nextInt(100);
                            if (rand < percents[optionParam]) {
                                for (ItemOption option1 : item.options) {
                                    if (option1.optionTemplate.type != 8 || option1.optionTemplate.id == 85) {
                                        continue;
                                    }
                                    if (option1.optionTemplate.id == 94) {
                                        int[] percentIncreases = new int[]{10, 10, 10, 20, 20, 30, 40, 50, 60};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 86) {
                                        int[] percentIncreases = new int[]{25, 30, 35, 40, 50, 60, 80, 115, 165};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 87) {
                                        int[] percentIncreases = new int[]{50, 60, 70, 90, 130, 180, 250, 330, 500};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 88 || option1.optionTemplate.id == 89 || option1.optionTemplate.id == 90) {
                                        int[] percentIncreases = new int[]{50, 70, 100, 140, 190, 250, 320, 400, 500};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 92) {
                                        int[] percentIncreases = new int[]{5, 5, 5, 5, 5, 5, 10, 10, 20};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 95 || option1.optionTemplate.id == 96 || option1.optionTemplate.id == 97) {
                                        int[] percentIncreases = new int[]{5, 5, 5, 5, 5, 5, 10, 10, 15};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 82 || option1.optionTemplate.id == 83) {
                                        int[] percentIncreases = new int[]{40, 60, 80, 100, 140, 220, 300, 420, 590};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 84) {
                                        int[] percentIncreases = new int[]{25, 30, 35, 40, 50, 60, 80, 115, 165};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 79) {
                                        int[] percentIncreases = new int[]{1, 2, 2, 2, 2, 2, 3, 3, 4};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 81) {
                                        int[] percentIncreases = new int[]{1, 2, 2, 2, 2, 2, 3, 3, 4};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 80) {
                                        int[] percentIncreases = new int[]{5, 5, 5, 5, 10, 10, 15, 15, 20};
                                        option1.param += percentIncreases[optionParam];
                                    } else if (option1.optionTemplate.id == 91) {
                                        int[] percentIncreases = new int[]{5, 5, 5, 5, 5, 5, 10, 10, 15};
                                        option1.param += percentIncreases[optionParam];
                                    }

                                }
                                option.param++;
                                if (item.isLock) {
                                    getService().itemInfo(item, (byte) 3, (byte) indexUI);
                                } else {
                                    bag[indexUI] = Converter.getInstance().newItem(item);
                                    bag[indexUI].index = indexUI;
                                    bag[indexUI].isLock = true;
                                    bag[indexUI].setQuantity(bag[indexUI].getQuantity());
                                    getService().addItem(bag[indexUI]);
                                }

                                serverMessage("Vật phẩm đã được tinh luyện thành công");
                            } else {
                                if (item.isLock) {
                                    getService().itemInfo(item, (byte) 3, (byte) indexUI);
                                } else {
                                    bag[indexUI] = Converter.getInstance().newItem(item);
                                    bag[indexUI].index = indexUI;
                                    bag[indexUI].isLock = true;
                                    bag[indexUI].setQuantity(bag[indexUI].getQuantity());
                                    getService().addItem(bag[indexUI]);
                                }
                                serverMessage("Tinh luyện thất bại");
                            }

                            if (coin > this.yen) {
                                addCoin(-(coin - this.yen));
                                addYen(-this.yen);
                            } else {
                                addYen(-coin);
                            }
                            for (int j = 0; j < quantity; j++) {
                                removeItem(items.get(j).index, 1, true);
                            }

                            return;
                        }

                    }
                    serverDialog("Vui lòng dịch chuyển trang bị trước");
                } else {
                    serverDialog("Vật phẩm này không thể tinh luyện");
                }
            }
        } catch (IOException ex) {
            Log.logException("tinh luyen err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void luyenThach(Message ms) { // fix luyện thạch khóa
        lockItem.lock();
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            Vector<Item> items = new Vector<>();
            boolean[] list = new boolean[numberCellBag];
            while (ms.reader().available() > 0) {
                int index = ms.reader().readUnsignedByte();
                if (index < 0 || index >= numberCellBag) {
                    continue;
                }
                if (!list[index]) {
                    list[index] = true;
                    Item item = this.bag[index];
                    if (item != null && (item.id == 10 || item.id == 455 || item.id == 456)) {
                        if (item.id == 10) {
                            items.add(0, item);
                        } else {
                            items.add(item);
                        }
                    }
                }
            }
            if (items.isEmpty()) {
                return;
            }
            Item item = items.get(0);
            if (item.id == 11) {
                serverDialog("Không thể dùng đá cấp 12 Cần kết hợp 9 tử tinh thạch cùng loại hoặc 3 tử tinh thạch (sơ) đá 11");
                return;
            }
            if (item.id == 10 || item.id == 455) {
                for (int i = 1; i < items.size(); i++) {
                    Item item2 = items.get(i);
                    if (item2.id != 455) {
                        items.remove(item2);
                    }
                }
            }
            if (items.size() == 4 || items.size() == 9) {

                if (item.id == 10) {
                    if (getSlotNull() < 1) {
                        warningBagFull();
                        return;
                    }
//                    for (int i = 0; i < 3; i++) {
                    Item itm = ItemFactory.getInstance().newItem(456);
                    itm.isLock = true;
                    addItemToBag(itm);
//                    }
                }
                if (item.id == 455) {
                    if (getSlotNull() == 0) {
                        warningBagFull();
                        return;
                    }
                    Item itm = ItemFactory.getInstance().newItem(456);
                    itm.isLock = false;
                    addItemToBag(itm);
                }
//                if (item.id == 11) {
//                    if (getSlotNull() < 1) {
//                        warningBagFull();
//                        return;
//                    }
////                    for (int i = 0; i < 3; i++) {
//                    Item itm = ItemFactory.getInstance().newItem(457);
//                    itm.isLock = true;
//                    addItemToBag(itm);
////                    }
//                }
                if (item.id == 456) {
                    if (getSlotNull() == 0) {
                        warningBagFull();
                        return;
                    }
                    Item itm = ItemFactory.getInstance().newItem(457);
                    itm.isLock = false;
                    addItemToBag(itm);
                }
                getService().endDlg(true);
                for (Item item2 : items) {
                    removeItem(item2.index, 1, true);
                }
            } else {
                serverDialog("Cần kết hợp 9 tử tinh thạch cùng loại  hoặc 3 tử tinh thạch (sơ) đá 11");
            }
        } catch (IOException ex) {
            Log.logException("luyen thach err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void upgradeItem(Message ms) {
        lockItem.lock();
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }

            if (!isVillage()) { /// chặn nâng cấp
                serverMessage("Bạn phải về làng để nâng cấp");
                getService().endDlg(true);
                return;
            }
            boolean isGold = ms.reader().readBoolean();
            int equipIndex = ms.reader().readUnsignedByte();
            Item item = bag[equipIndex];
            if (item == null) {
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            // fix vk 10x
            if ((!item.template.isTypeClothe() && !item.template.isTypeAdorn() && !item.template.isTypeWeapon()) || item.template.fashion > -1 && !item.template.isVk100x()) {
                serverDialog("Trang bị không thể nâng cấp");
                return;
            }
            if (item.upgrade >= item.template.getUpMax()) {
                serverDialog(language.getString("BODY_MAXUPGRADE"));
                return;
            }
            int numberBaoHiem = 0;
            int numberCrystal = 0;
            Vector<Item> crystals = new Vector<>();
            boolean[] list = new boolean[numberCellBag];
            while (ms.reader().available() > 0) {
                byte index = ms.reader().readByte();
                if (index < 0 || index >= numberCellBag) {
                    continue;
                }
                if (!list[index]) {
                    list[index] = true;
                    if (bag[index] != null && (bag[index].id <= 11 || bag[index].template.type == 28) && bag[index].getQuantity() == 1) {
                        if (bag[index].template.isTypeCrystal()) {
                            numberCrystal++;
                        } else if (bag[index].template.type == 28) {
                            numberBaoHiem++;
                        }
                        crystals.add(bag[index]);
                    }
                }
            }
            if (crystals.size() > 18) {
                serverDialog(language.getString("CRYSTAL_FULL"));
                return;
            }
            if (numberBaoHiem > 1) {
                serverDialog("Chỉ được sử dụng một bảo hiểm!");
                return;
            }
            if (numberCrystal == 0) {
                serverDialog("Hãy chọn đá nâng cấp!");
                return;
            }
            long temp = 0;
            int percent = 0;
            int coin = 0;
            int gold = 0;
            for (int i = 0; i < crystals.size(); i++) {
                Item itm = crystals.get(i);
                if (itm != null && (int) itm.template.type == 26) {
                    temp += GameData.UP_CRYSTAL[itm.id];
                }
            }
            if (item.template.isTypeClothe()) {
                percent = (int) (temp * 100 / GameData.UP_CLOTHE[item.upgrade]);
                coin = GameData.COIN_UP_CLOTHE[item.upgrade];
            }
            if (item.template.isTypeAdorn()) {
                percent = (int) (temp * 100 / GameData.UP_ADORN[item.upgrade]);
                coin = GameData.COIN_UP_ADORN[item.upgrade];
            }
            if (item.template.isTypeWeapon()) {
                percent = (int) (temp * 100 / GameData.UP_WEAPON[item.upgrade]);
                coin = GameData.COIN_UP_WEAPON[item.upgrade];
            }
            int maxPercent = GameData.MAX_PERCENT[item.upgrade];
            maxPercent += maxPercent * Config.getInstance().getMaxPercentAdd();

            if (percent > maxPercent) {
                percent = maxPercent;
            }
            if (isGold) {
                percent = (int) ((double) percent * 1.5);
                gold = GameData.GOLD_UP[item.upgrade];
            }
            if (coin > NinjaUtils.sum(this.coin, this.yen)) {
                serverDialog(language.getString("NOT_ENOUGH_COIN_UPGRADE"));
                return;
            }
            if (isGold && user.gold < gold) {
                serverDialog(language.getString("NOT_ENOUGH_GOLD_UPGRADE"));
                return;
            }
            if (isGold) {
                addGold(-gold);
            }
            if (coin > this.yen) {
                addCoin(-(coin - this.yen));
                addYen(-this.yen);
            } else {
                addYen(-coin);
            }
            boolean isBaoHiem = false;
            for (int i = 0; i < crystals.size(); i++) {
                Item itm = crystals.get(i);
                if (itm != null && (itm.template.type == 26 || itm.template.type == 28)) {
                    if (!isBaoHiem) {
                        if (itm.id == 242 && item.upgrade < 8) {
                            isBaoHiem = true;
                        } else if (itm.id == 284 && item.upgrade < 12) {
                            isBaoHiem = true;
                        } else if (itm.id == 285 && item.upgrade < 14) {
                            isBaoHiem = true;
                        } else if (itm.id == 475 && item.upgrade < 16) {
                            isBaoHiem = true;
                        }
                    }
                    removeItem(itm.index, itm.getQuantity(), false);
                }
            }
            byte type = 1;
            int rand = NinjaUtils.nextInt(100);
            int up1 = item.upgrade;
            int up2 = item.upgrade;
            if (rand < percent) {
                type = 1;
                up2 += 1;
                if (up2 == 8 && this.gloryTask != null) {
                    this.gloryTask.updateProgress(1);
                }
            } else {
                type = 0;
                if (!isBaoHiem && item.upgrade > 4) {
                    if (item.upgrade >= 14) {
                        up2 = 14;
                    } else if (item.upgrade >= 12) {
                        up2 = 12;
                    } else {
                        up2 = (byte) (item.upgrade / 4 * 4);
                    }
                }
            }
            item.isLock = true;
            item.next(up2 - up1);
            getService().upgrade(type, item);
        } catch (Exception ex) {
            Log.logException("upgrade item err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void inputNumberSplit(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (getSlotNull() == 0) {
                serverDialog("Hành trang không đủ chỗ trống");
                return;
            }
            byte indexItem = ms.reader().readByte();
            int numSplit = ms.reader().readInt();
            numSplit = numSplit > Config.getInstance().getMaxQuantity() ? Config.getInstance().getMaxQuantity() : numSplit;
            if (numSplit < 1) {
                return;
            }
            if (bag[indexItem] != null && bag[indexItem].template.isUpToUp) {
                int quantity = bag[indexItem].getQuantity();
                if (numSplit >= quantity) {
                    return;
                }
                for (int i = 0; i < this.numberCellBag; i++) {
                    if (bag[i] == null) {
                        bag[i] = Converter.getInstance().newItem(bag[indexItem]);
                        bag[i].index = i;
                        bag[i].setQuantity(numSplit);
                        getService().addItem(bag[i]);
                        bag[indexItem].reduce(numSplit);
                        getService().useItemUpToUp(indexItem, numSplit);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            Log.logException("Input Number split: ", Char.class, ex);
        }
    }
}
