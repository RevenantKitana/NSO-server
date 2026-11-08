package com.nsoz2.model;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nsoz2.ability.AbilityCustom;
import com.nsoz2.ability.AbilityFromEquip;
import com.nsoz2.ability.AbilityStrategy;
import com.nsoz2.admin.AdminService;
import com.nsoz2.bot.Bot;
import com.nsoz2.bot.attack.AttackTarget;
import com.nsoz2.bot.move.JaianMove;
import com.nsoz2.bot.move.MoveToTarget;
import com.nsoz2.clan.Clan;
import com.nsoz2.clan.ClanDAO;
import com.nsoz2.clan.Member;
import com.nsoz2.combat.CombatResult;
import com.nsoz2.combat.CombatService;
import com.nsoz2.constants.*;
import com.nsoz2.convert.Converter;
import com.nsoz2.db.jdbc.DbManager;
import com.nsoz2.db.mongodb.MongoDbConnection;
import com.nsoz2.effect.Effect;
import com.nsoz2.effect.EffectManager;
import com.nsoz2.event.*;
import com.nsoz2.event.bus.EventBus;
import com.nsoz2.event.bus.event.ItemPickedEvent;
import com.nsoz2.event.eventpoint.EventPoint;
import com.nsoz2.event.eventpoint.Point;
import com.nsoz2.fashion.FashionCustom;
import com.nsoz2.fashion.FashionFromEquip;
import com.nsoz2.fashion.FashionStrategy;
import com.nsoz2.item.*;
import com.nsoz2.lib.ImageMap;
import com.nsoz2.lib.ParseData;
import com.nsoz2.lib.RandomCollection;
import com.nsoz2.map.Map;
import com.nsoz2.map.*;
import com.nsoz2.map.item.Envelope;
import com.nsoz2.map.item.GiftBox;
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
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CharInventoryHandler: Quản lý toàn bộ nghiệp vụ túi đồ (bag), rương đồ (box),
 * trang bị (equipment/mount/bijuu), nhặt/ném đồ, sắp xếp đồ và mua/bán NPC.
 */
public abstract class CharInventoryHandler extends CharCombatHandler {

    public CharInventoryHandler(int id) {
        super(id);
    }

    public void removeItem(int index, int quantity, boolean isUpdate) {
        try {
            if (bag[index] != null) {
                bag[index].reduce(quantity);
                if (!bag[index].has()) {
                    bag[index] = null;
                    if (isUpdate) {
//                        getService().removeItem(index);
                        getService().useItemUpToUp(index, quantity);
                    }
                } else if (isUpdate) {
                    getService().useItemUpToUp(index, quantity);
                }
            }
        } catch (Exception ex) {
            Log.logException("remove item: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public void removeItemBags(final int id, final int quantity) {
        int num = 0;
        for (int i = 0; i < bag.length; ++i) {
            final Item item = bag[i];
            if (item != null && item.id == id) {
                num = i;

            }
        }
        removeItem(num, quantity, true);
    }

    public boolean checkItemExist(Item item) {
        for (Item item1 : this.bag) {
            if (item1 != null && item1.id == item.id) {
                return true;
            }
        }
        return false;
    }

    public int getIndexItemByIdInBag(int id, boolean isLock) {
        for (Item item : this.bag) {
            if (item != null && item.id == id && item.isLock == isLock) {
                return item.index;
            }
        }
        return -1;
    }

    public int getIndexItemByIdInBag(int id) {
        if (this.bag == null) {
            return -1;
        }
        for (Item item : this.bag) {
            if (item != null && item.id == id) {
                return item.index;
            }
        }
        return -1;
    }

    public Item getItemByIdInBag(int id) {
        for (Item item : this.bag) {
            if (item != null && item.id == id) {
                return item;
            }
        }
        return null;
    }

    public Item getItemByIdInBox(int id) {
        for (Item item : this.box) {
            if (item != null && item.id == id) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getListItem5sao(int... ids) {
        List<Item> list = new ArrayList<>();
        for (Item item : this.bag) {
            for (int id : ids) {
                if (item != null && item.id == id && item.sys == 4 && item.upgrade == 99) {
                    boolean isAdd = true;
                    for (ItemOption op : item.options) {
                        if (op.optionTemplate.id == 161) {
                            isAdd = false;
                        }
                    }
                    if (isAdd) {
                        list.add(item);
                    }
                    break;
                }
            }
        }
        return list;
    }

    public List<Item> getListItemByID(int... ids) {
        List<Item> list = new ArrayList<>();
        for (Item item : this.bag) {
            for (int id : ids) {
                if (item != null && item.id == id) {
                    list.add(item);
                    break;
                }
            }
        }
        return list;
    }

    public int getIndexItemByIdInBox(int id, boolean isLock) {
        for (Item item : this.box) {
            if (item != null && item.id == id && item.isLock == isLock) {
                return item.index;
            }
        }
        return -1;
    }

    public int getQuantityItemById(int id) {
        int number = 0;
        try {
            for (byte i = 0; i < this.bag.length; i++) {
                Item item = this.bag[i];
                if (item != null && item.id == id) {
                    number += item.getQuantity();
                }
            }
        } catch (Exception e) {
        }
        return number;
    }

    public void warningClone() {
        serverMessage("Bạn đang trong chế độ thứ thân không thể dùng được chức năng này.");
    }

    public void warningTrade() {
        serverDialog("Không thể thực hiện khi đang giao dịch.");
    }

    public void warningBagFull() {
        serverDialog(language.getString("BAG_FULL"));
    }

    public void returnToPreviousPostion(Runnable runnable) {
        setXY(this.preX, this.preY);
        getService().resetPoint();
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setFashion() {
        short horse = this.ID_HORSE;
        short body = this.ID_BODY;
        short bienHinh = this.ID_BIEN_HINH;
        short leg = this.ID_LEG;
        short hair = this.ID_HAIR;
        short matNa = this.ID_MAT_NA;
        short name = this.ID_NAME;
        short rank = this.ID_RANK;
        short weapon2 = this.ID_WEA_PONE;
        short pp = this.ID_PP;
        short shirt = this.body;
        short pant = this.leg;
        short mask = this.head;
        short weapon = this.weapon;
        short coat = this.coat;
        short glove = this.glove;
        int honor = this.honor;
        Mob mob = this.mobMe;
        if (fashionStrategy != null) {
            fashionStrategy.set((Char) this);
        }
        setMobMe();
        if (bijuu != null && bijuu[4] != null) {
            setBijuu(bijuu[4].id);
        }
        if (zone != null) {

            if (shirt != this.body) {
                zone.getService().loadShirt((Char) this);
            }
            if (pant != leg) {
                zone.getService().loadPant((Char) this);
            }
            if (mask != this.head) {
                zone.getService().loadMask((Char) this);
            }
            if (weapon != this.weapon) {
                zone.getService().loadWeapon((Char) this);
            }
            if (coat != this.coat) {
                zone.getService().loadCoat((Char) this);
            }
            if (glove != this.glove) {
                zone.getService().loadGlove((Char) this);
            }
            zone.getService().updateInfoChar((Char) this);
            getService().updateInfoMe();
            if (mob != this.mobMe) {
                zone.getService().loadPet((Char) this);
            }
            if (honor != this.honor) {
                zone.getService().loadHonor((Char) this);
            }
        }
    }


    public Item getItemInCollectionBox(int id) {
        for (Item item : collectionBox) {
            if (item != null && item.id == id) {
                return item;
            }
        }
        return null;
    }

    public void itemMountToBag(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            byte indexUI = ms.reader().readByte();
            if (indexUI < 0 || indexUI > 4) {
                return;
            }
            if (this.mount[indexUI] == null) {
                return;
            }
            if (indexUI == 4 && (this.mount[0] != null || this.mount[1] != null || this.mount[2] != null || this.mount[3] != null)) {
                serverDialog("Hãy tháo thú trang ra trước!");
                return;
            }
            for (int i = 0; i < numberCellBag; i++) {
                if (bag[i] == null) {
                    Item item = Converter.getInstance().toItem(mount[indexUI]);
                    item.isLock = true;
                    item.index = i;
                    this.bag[i] = item;
                    this.mount[indexUI] = null;
                    setAbility();
                    getService().itemMountToBag(indexUI, i);
                    zone.getService().loadMount((Char) this);
                    setFashion();
                    if (item.isExpired()) {
                        removeItem(i, 1, true);
                    }
                    return;
                }
            }
            warningBagFull();
        } catch (Exception ex) {
            Log.logException("item mount to bag err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public boolean isExpireMount() {
        return this.mount[4] != null && this.mount[4].expire != -1 && this.mount[4].expire <= System.currentTimeMillis();
    }

    public void checkExpireMount() {
        try {
            int countMount = getCountMount();

            if (!isExpireMount() || getSlotNull() < countMount) {
                return;
            }

            for (int indexUI = 0; indexUI < 5; indexUI++) {
                if (this.mount[indexUI] == null) {
                    continue;
                }
                for (int a = 0; a < numberCellBag; a++) {
                    if (bag[a] == null) {
                        Item it = Converter.getInstance().toItem(mount[indexUI]);
                        it.isLock = true;
                        it.index = a;
                        this.mount[indexUI] = null;
                        this.bag[a] = it;
                        setAbility();
                        getService().itemMountToBag(indexUI, a);
                        zone.getService().loadMount((Char) this);
                        setFashion();
                        if (it.isExpired()) {
                            removeItem(a, 1, true);
                        }
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            Log.logException("checkExpireMount err: ", Char.class, ex);
        }
    }

    public synchronized void itemBagToBox(Message ms) {
        try {
            int commandBox = getCommandBox();
            if (commandBox != RUONG_DO) {
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }

//            if (getCommandBox() == CAITAO_CAITRANG || getCommandBox() == HOANHINH_CAITRANG || getCommandBox() == TACH_CAITRANG) {  // hóa hình
//                return;
//            }

            byte indexUI = ms.reader().readByte();
            if (indexUI < 0 || indexUI > this.numberCellBag) {
                return;
            }
            Item item = this.bag[indexUI];
            if (item == null) {
                return;
            }
            if (item.isExpired()) {
                removeItem(item.index, 1, true);
                return;
            }
            itemBagToBox(item);
        } catch (Exception ex) {
            Log.logException("item bag to box err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public synchronized void itemBoxToBag(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
//            if (!isHuman) {
//                warningClone();
//                return;
//            }
            byte indexUI = ms.reader().readByte();
            int commandBox = getCommandBox();


            if (commandBox == CAI_TAO) { // hóa hình

                if (indexUI < 0 || indexUI > ItemCaiTao_HoaHinh.length) {
                    return;
                }
                Item itemct = ItemCaiTao_HoaHinh[indexUI];
                if (itemct == null || itemct.options == null) {
                    return;
                }
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "1000 Lượng", () -> {
                    if (user.gold < 1000) {
                        serverMessage("Không đủ 1000 lượng để cải tạo");
                        return;
                    }
                    if (itemct.upgrade > 0) {
                        serverMessage("Không thể cải tạo trang bị đã nâng cấp !");
                        return;
                    }
                    int vaiThuongHang = getIndexItemByIdInBag(ItemName.VAI_THUONG_HANG);
                    if (vaiThuongHang == -1 || bag[vaiThuongHang] == null || bag[vaiThuongHang].getQuantity() < 100) {
                        serverMessage("Bạn không đủ vải thượng hạng");
                        return;
                    }
                    int percent = NinjaUtils.nextInt(10, 30);
                    boolean len = 5 <= NinjaUtils.nextInt(10);
                    if (len) {
                        if (itemct.options != null && !itemct.options.isEmpty()) {
                            for (int i = 0; i < itemct.options.size(); i++) {
                                ItemOption itemOption = itemct.options.get(i);
                                int id = itemOption.optionTemplate.id;
                                int value;
                                if (id == 6 || id == 7 || id == 117 || id == 125) {
                                    value = 3000 + ((3000 * percent) / 100);
                                } else if (id == 127 || id == 130 || id == 131 || id == 94) {
                                    value = 10 + ((10 * percent) / 100);
                                } else {
                                    value = itemOption.param;
                                }
                                itemOption.param = value;
                            }
                        } else {
                            if (itemct.options == null) {
                                itemct.options = new ArrayList<>();
                            }
                            itemct.options.add(new ItemOption(7, 3000 + ((3000 * percent) / 100)));
                            itemct.options.add(new ItemOption(6, 3000 + ((3000 * percent) / 100)));
                            itemct.options.add(new ItemOption(8, 10 + ((10 * percent) / 100)));
                            itemct.options.add(new ItemOption(127, 10 + ((10 * percent) / 100)));
                            itemct.options.add(new ItemOption(130, 10 + ((10 * percent) / 100)));
                            itemct.options.add(new ItemOption(131, 10 + ((10 * percent) / 100)));
                        }
                        serverMessage("Cải tạo thành công, chỉ số được tăng thêm " + percent + "%");
                    } else {
                        serverMessage("Cải tạo thất bại, chúc bạn may mắn lần sau");
                    }
                    user.addGold(-1000);
                    removeItem(vaiThuongHang, 20, true);
                    getService().updateInfoMe();
                    getService().openUICaiTao();
                }));


                menus.add(new Menu(CMDMenu.EXECUTE, "5.000.000 Xu", () -> {
                    if (coin < 5000000) {
                        serverMessage("Không đủ 5.000.000 xu để cải tạo");
                        return;
                    }
                    int vaiThuongHang = getIndexItemByIdInBag(ItemName.VAI_THUONG_HANG);
                    if (vaiThuongHang == -1 || bag[vaiThuongHang] == null || bag[vaiThuongHang].getQuantity() < 100) {
                        serverMessage("Bạn không đủ vải thượng hạng");
                        return;
                    }
                    int percent = NinjaUtils.nextInt(10, 30);
                    if (itemct.options != null && !itemct.options.isEmpty()) {
                        for (int i = 0; i < itemct.options.size(); i++) {
                            ItemOption itemOption = itemct.options.get(i);
                            int id = itemOption.optionTemplate.id;
                            int value;
                            if (id == 6 || id == 7 || id == 117 || id == 125) {
                                value = 3000 + ((3000 * percent) / 100);
                            } else if (id == 127 || id == 130 || id == 131 || id == 94) {
                                value = 10 + ((10 * percent) / 100);
                            } else {
                                value = itemOption.param;
                            }
                            itemOption.param = value;
                        }
                    } else {
                        if (itemct.options == null) {
                            itemct.options = new ArrayList<>();
                        }
                        itemct.options.add(new ItemOption(7, 3000 + ((3000 * percent) / 100)));
                        itemct.options.add(new ItemOption(6, 3000 + ((3000 * percent) / 100)));
                        itemct.options.add(new ItemOption(8, 10 + ((10 * percent) / 100)));
                        itemct.options.add(new ItemOption(127, 10 + ((10 * percent) / 100)));
                        itemct.options.add(new ItemOption(130, 10 + ((10 * percent) / 100)));
                        itemct.options.add(new ItemOption(131, 10 + ((10 * percent) / 100)));
                    }
                    serverMessage("Cải tạo cải trang thành công chỉ số được tăng thêm " + percent + "%");
                    addCoin(-5000000);
                    removeItem(vaiThuongHang, 20, true);
                    getService().loadAll();
                    getService().updateInfoMe();
                    getService().openUICaiTao();
                }));
                getService().openUIMenu();
            } else if (commandBox == HOA_HINH) {
                if (ItemCaiTao_HoaHinh == null || indexUI < 0 || indexUI >= ItemCaiTao_HoaHinh.length) {
                    return;
                }

                Item targetItem = ItemCaiTao_HoaHinh[indexUI];
                if (targetItem == null || targetItem.options == null) {
                    return;
                }
                this.indexMenuBox = indexUI;
                setConfirmPopup(new ConfirmPopup(
                        CMDConfirmPopup.HOA_HINH,
                        "Xác nhận hóa hình cải trang, cần 500 lượng để hóa hình?"
                ));
                getService().openUIConfirmID();

            } else if (commandBox == PHAN_TACH) {
                if (ItemPhanTach == null || indexUI < 0 || indexUI >= ItemPhanTach.length) {
                    return;
                }

                Item targetItem = ItemPhanTach[indexUI];
                if (targetItem == null || targetItem.options == null) {
                    return;
                }
                this.indexMenuBox = indexUI;

                setConfirmPopup(new ConfirmPopup(
                        CMDConfirmPopup.PHAN_TACH,
                        "Xác nhận tách cải trang, nhận ngẫu nhiên 1~5 vải thượng hạng"
                ));
                getService().openUIConfirmID();


            } else if (commandBox == HOA_HINH_THU) {
                hoaHinhMount(indexUI);
            } else if (commandBox == RUONG_DO) {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                if (indexUI < 0 || indexUI > this.numberCellBox) {
                    return;
                }
                Item item = this.box[indexUI];
                if (item == null) {
                    return;
                }
                if (item.isExpired()) {
                    box[indexUI] = null;
                    return;
                }
                itemBoxToBag(item);
            } else if (commandBox == BO_SUU_TAP) {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                if (indexUI < 0 || indexUI > collectionBox.size()) {
                    return;
                }
                Item item = collectionBox.get(indexUI);
                if (item != null) {
                    if (collectionBox.size() >= 9) {
                        boolean isJirai = item.isJirai();
                        boolean isJumito = item.isJumito();
                        int upgradeMin = -1;
                        int count = 0;
                        for (Item itm : collectionBox) {
                            if (itm == null) {
                                continue;
                            }
                            boolean flag = isJirai && itm.isJirai();
                            if (isJumito && itm.isJumito()) {
                                flag = true;
                            }
                            if (flag) {
                                count++;
                                if (upgradeMin == -1 || itm.upgrade < upgradeMin) {
                                    upgradeMin = itm.upgrade;
                                }
                            }
                        }
                        if (count == 9) {
                            int newItemID = -1;
                            if (isJirai) {
                                newItemID = ItemName.MAT_NA_JIRAI_;
                            }
                            if (isJumito) {
                                newItemID = ItemName.MAT_NA_JUMITO;
                            }
                            Item itm = getItemInMaskBox(newItemID);
                            if (itm != null) {
                                itm.upgrade = (byte) upgradeMin;
                                itm.initOption();
                            } else {
                                itm = ItemFactory.getInstance().newItem(newItemID);
                                itm.yen = 0;
                                itm.upgrade = (byte) upgradeMin;
                                itm.initOption();
                                maskBox.add(itm);
                            }
                        }
                    }
                }
            } else if (commandBox == CAI_TRANG) {

                if (indexUI < 0 || indexUI > maskBox.size()) {
                    return;
                }
                Item item = maskBox.get(indexUI);
                if (item != null) {
                    setMask(item);
                    setMaskId(item.id);
                    setAbility();
                    setFashion();

                    serverMessage("Đã cải trang thành " + item.template.name);
                }
            } else if (commandBox == THANG_NGUONG) {
                if (indexUI < 0 || indexUI > equipment.length) {
                    return;
                }
                Item item = equipment[indexUI];
                checkIndexUI = indexUI;
                IndexUI = indexUI;
                if (item != null) {
                    int check = checkThangNguong(item);
                    int thangNguongStart = check + 1;
                    if (check == 0) {
                        //thangnguongcap0
                        setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.THANG_NGUONG, "Thăng ngưỡng khai hóa lên cấp " + thangNguongStart + " \n Tỷ lệ thành công: " + NinjaUtils.percentThangNguong[check] + "% \n Cần " + String.format("%,d", NinjaUtils.xuThangNguong[check]) + " xu và " + String.format("%,d", NinjaUtils.luongThangNguong[check]) + " lượng"));
                        getService().openUIConfirmID();
                    } else if (check >= 9) {
                        serverMessage("Đã đạt ngưỡng tối đa");
                    } else {
                        setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.THANG_NGUONG_C1, "Thăng ngưỡng khai hóa lên cấp " + thangNguongStart + " \n Tỷ lệ thành công: " + String.format("%,d", NinjaUtils.percentThangNguong[check]) + "% \n Cần " + String.format("%,d", NinjaUtils.xuThangNguong[check]) + " xu và " + String.format("%,d", NinjaUtils.luongThangNguong[check]) + " lượng"));
                        getService().openUIConfirmID();
                    }
                }
            } else if (commandBox == THANG_CAP) {
                List<Item> itemList = new ArrayList<>();
                int nguong = 0;
                int cap = 0;
                for (int i = 0; i < equipment.length; i++) {
                    Item item = equipment[i];
                    if (item != null && (item.template.isTypeClothe() || item.template.isTypeWeapon() || item.template.isTypeAdorn())) {
                        for (int j = 0; j < item.options.size(); j++) {
                            if (item.options.get(j).optionTemplate.id == 156 && item.options.get(j + 1).optionTemplate.id == 157) {
                                nguong = item.options.get(j).param;
                                cap = item.options.get(j + 1).param;
                                if (nguong > cap) {
                                    itemList.add(item);
                                }
                            }
                        }
                    }

                }
                for (int i = 0; i < this.numberCellBag; i++) {
                    Item item = this.bag[i];
                    if (item != null) {
                        if (item.template.id == ItemName.DA_CHINH_PHUC || item.template.id == ItemName.DA_VO_THUONG || item.template.id == ItemName.DA_PHUC_SINH) {
                            itemList.add(item);
                        }
                    }
                }
                if (itemList.isEmpty() || indexUI < 0 || indexUI >= itemList.size()) {
                    return;
                }
                Item item = itemList.get(indexUI);
                checkIndexUI = indexUI;
                IndexUI = indexUI;
                if (item != null) {


                    if (checkThangNguong(item) <= checkThangCap(item)) {
                        if (checkThangNguong(item) < 9) {
                            serverMessage("Bạn cần nâng ngưỡng khai hóa để tiếp tục nâng cấp.");
                            setCommandBox(THANG_CAP);
                            getService().openUIThangCap();
                            return;
                        }

                        if (checkThangCap(item) >= 9) {
                            serverMessage("Bạn đã đạt cấp tối đa nâng cấp.");
                            setCommandBox(THANG_CAP);
                            getService().openUIThangCap();
                            return;
                        }
                    }
                    int check = checkThangCap(item);
                    int tangCap = check + 1;
                    if (check == 0) {
                        menus.clear();
                        menus.add(new Menu(CMDMenu.EXECUTE, "Sức mạnh", () -> {
                            thangCapChinhPhuc(item);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Phòng thủ", () -> {
                            thangCapVoThuong(item);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Hồi phục", () -> {
                            thangCapPhucSinh(item);
                        }));
                        getService().openUIMenu();
                    } else {

                        setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.THANG_CAP, "Thăng cấp khai hóa lên cấp " + tangCap + " " +
                                "\n Tỷ lệ thành công: " + NinjaUtils.percentThangCap[check] + "% " +
                                "\n Cần  " + String.format("%,d", NinjaUtils.xuThangCap[check]) + " xu" +
                                " và " + String.format("%,d", NinjaUtils.luongThangCap[check]) +
                                " lượng " + String.format("%,d", NinjaUtils.quantity[check]) + " đá"));
                        getService().openUIConfirmID();
                    }

                    setCommandBox(THANG_CAP);
                }

            } else if (commandBox == STORE_UPGRADE) { // shop 16
                IndexUI = indexUI;
                StoreUpgradeManager.getInstance().buyComfirm((Char) this, indexUI, 1);

            } else if (commandBox == GIA_HAN) {
                Item itemGiahan = this.ItemExtend[indexUI];
                if (itemGiahan != null) {
                    int indexbag = getIndexItemByIdInBag(itemGiahan.id);
                    if (indexbag < 0) {
                        return;
                    }
                    Item itemInBag = this.bag[indexbag];
                    if (indexbag != -1 && itemInBag != null) {
                        if (itemInBag.id == itemGiahan.id) {

                            if (getQuantityItemById(itemGiahan.id) < 1) {
                                serverMessage("Không đủ vật phẩm");
                                return;
                            }
                            if (getQuantityItemById(itemGiahan.id) > 1) {
                                serverMessage("Chỉ có thể gia hạn vật phẩm số lượng = 1");
                                return;
                            }
                            if (itemGiahan.isGiahan()) {
                                serverMessage("Mỗi vật phẩm chỉ có thể gia hạn 1 lần");
                                return;
                            }
                            if (itemGiahan.template.type > ItemTemplate.TYPE_MATNA && itemGiahan.template.type != ItemTemplate.TYPE_MON4) {
                                if (getQuantityItemById(ItemName.BUA_AITEMU) < 1) {
                                    serverMessage("Cần có Bùa Aitemu");
                                    return;
                                }
                            }
                            if (itemGiahan.template.type == ItemTemplate.TYPE_MON4) {
                                if (getQuantityItemById(ItemName.BUA_NORU) < 1) {
                                    serverMessage("Cần có Bùa Noru");
                                    return;
                                }
                            }
                            if (itemGiahan.template.type <= ItemTemplate.TYPE_MATNA) {
                                if (getQuantityItemById(ItemName.BUA_SOCHI) < 1) {
                                    serverMessage("Cần có Bùa Sochi");
                                    return;
                                }
                            }
                            Item it = ItemFactory.getInstance().newItem(itemGiahan.id);

                            int idDa;
                            if (itemGiahan.template.type > ItemTemplate.TYPE_MATNA && itemGiahan.template.type != ItemTemplate.TYPE_MON4) {
                                idDa = ItemName.BUA_AITEMU;
                            } else if (itemGiahan.template.type == ItemTemplate.TYPE_MON4) {
                                idDa = ItemName.BUA_NORU;
                            } else {
                                idDa = ItemName.BUA_SOCHI;
                            }
                            removeItemBags(idDa, 1);
                            removeItem(indexbag, 1, true);
                            it.expire = itemGiahan.expire + (30L * 86400000L); // Sử dụng long constants
                            it.options.clear();
                            it.options = itemGiahan.options;
                            it.setLock(true);
                            it.setGiahan(true);
                            addItemToBag(it);
                            serverDialog(it.template.name + " đã được gia hạn thêm 30 ngày.");
                            setCommandBox(GIA_HAN);
                            getService().openUIExtendBox();
                        }
                    }
                }
            } else if (commandBox == DOI_LONG_DEN_XU) {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                Event event = Event.getEvent();
                event.doiLongDen((Char) this, Event.DOI_BANG_XU, indexUI);
            } else if (commandBox == DOI_LONG_DEN_LUONG) {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                Event event = Event.getEvent();
                event.doiLongDen((Char) this, Event.DOI_BANG_LUONG, indexUI);
            }


        } catch (Exception ex) {
            Log.logException("item box to bag err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }


    public Item getItemInMaskBox(int id) {
        if (maskBox != null) {
            for (Item item : maskBox) {
                if (item != null && item.id == id) {
                    return item;
                }
            }
        }

        return null;
    }

    public Item getItemInMaskBoxWithSys(int id, byte sys) {
        if (this.maskBox == null) {
            return null;
        }
        for (Item item : maskBox) {
            if (item != null && item.id == id && item.sys == sys) {
                return item;
            }
        }
        return null;
    }

    public void itemBodyToBag(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            byte indexUI = ms.reader().readByte();
            if (indexUI >= 0) {
                if (indexUI < 16) {
                    Equip equip = this.equipment[indexUI];
                    if (equip != null) {
                        for (int i = 0; i < numberCellBag; i++) {
                            if (bag[i] == null) {
                                Item item = Converter.getInstance().toItem(equip);
                                item.isLock = true;
                                item.index = i;
                                this.bag[i] = item;
                                this.equipment[indexUI] = null;

                                // xoa hieu ung long den
                                if (item.template.type == ItemTemplate.TYPE_THUNUOI) {
                                    if (item.id == 568 || item.id == 569 || item.id == 570 || item.id == 571) {
                                        Effect eff = null;
                                        switch (item.id) {
                                            case 568:
                                                eff = em.findByID((byte) 38);
                                                break;

                                            case 569:
                                                eff = em.findByID((byte) 36);
                                                break;
                                            case 1074:
                                                eff = em.findByID((byte) 47);
                                                break;

                                            case 570:
                                                eff = em.findByID((byte) 37);
                                                break;

                                            case 571:
                                                eff = em.findByID((byte) 39);
                                                break;
                                        }
                                        if (eff != null) {
                                            getService().removeEffect(eff);
                                            zone.getService().playerRemoveEffect((Char) this, eff);
                                            em.removeEffect(eff);
                                        }
                                    }
                                }
                                setFashion();
                                setAbility();
                                getService().itemBodyToBag(indexUI, i);
                                if (item.isExpired()) {
                                    removeItem(i, 1, true);
                                }
                                return;
                            }
                        }
                        warningBagFull();
                    }
                } else {
                    int index = indexUI - 16;
                    if (index < 16) {
                        Equip equip = this.fashion[index];
                        if (equip != null) {
                            for (int i = 0; i < numberCellBag; i++) {
                                if (bag[i] == null) {
                                    Item item = Converter.getInstance().toItem(equip);
                                    item.isLock = true;
                                    item.index = i;
                                    this.bag[i] = item;
                                    this.fashion[index] = null;
                                    setFashion();
                                    setAbility();
                                    getService().itemBodyToBag(indexUI, i);
                                    if (item.isExpired()) {
                                        removeItem(i, 1, true);
                                    }
                                    return;
                                }
                            }
                            warningBagFull();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.logException("Item body to bag err: ", Char.class, ex);
        }
    }

    public boolean addItemToBag(Item item) {
        if (item == null) {
            return false;
        }
        int index = getIndexItemByIdInBag(item.id, item.isLock);
        if (index != -1 && bag[index] == null) {
            return false;
        }
        if (index == -1 || !bag[index].template.isUpToUp || bag[index].hasExpire()) {
            for (int i = 0; i < this.numberCellBag; i++) {
                if (bag[i] == null) {
                    item.index = i;
                    bag[i] = item;
                    getService().addItem(item);
                    return true;
                }
            }
        } else {
            this.bag[index].add(item.getQuantity());
            if (this.bag[index].has()) {

                getService().addItem(this.bag[index]);

            } else {

                removeItem(index, bag[index].getQuantity(), true);
            }
            return true;
        }
        return false;
    }

    public boolean upgradeMount() { // nâng pet 5 sao
        Mount mount = this.mount[4];
        if (mount == null) {
            return false;
        }
        if (mount.upgrade < 99) {
            serverMessage("Thú cưới chưa đạt cấp độ tối đa");
            return false;
        } else if (mount.sys < 4) {
            if (30 / (mount.sys + 1) > NinjaUtils.nextInt(150)) { // tỉ lệ nâng sói 5 sao
                mount.sys++;
                mount.upgrade = 0;
                for (byte i = 0; i < mount.options.size(); i++) {
                    ItemOption op = mount.options.get(i);
                    if (op.optionTemplate.id == 65) {
                        op.param = 0;
                    } else if (op.optionTemplate.id == 128) {
                        op.param += 1;
                    } else if (op.optionTemplate.id == 6 || op.optionTemplate.id == 7) {
                        op.param -= 900;
                    } else if (op.optionTemplate.id == 67 || op.optionTemplate.id == 70 || op.optionTemplate.id == 71 || op.optionTemplate.id == 72) {
                        op.param -= 15;
                    } else if (op.optionTemplate.id == 69 || op.optionTemplate.id == 68 || op.optionTemplate.id == 10) {
                        op.param -= 30;
                    } else if (op.optionTemplate.id == 73) {
                        op.param -= 800;
                    } else if (op.optionTemplate.id == 74) {
                        op.param -= 100;
                    } else if (op.optionTemplate.id == 118) {
                        op.param -= 15;
                    }
                }

                if (mount.sys == 4 && this.mount[4].id == ItemName.HAC_NGUU) {
                    this.mount[4].id = ItemName.KIM_NGUU;
                }

                zone.getService().loadMount((Char) this);
                serverMessage("Nâng cấp thành công, thú cưới được tặng 1 sao");
            } else {
                if (this.mount[4].id == 776 || this.mount[4].id == 777 || this.mount[4].id == 798
                        || this.mount[4].id == ItemName.XICH_TU_MA || this.mount[4].id == ItemName.TA_LINH_MA  || this.mount[4].id == ItemName.PHONG_THUONG_MA
                        || this.mount[4].id == ItemName.BACH_NGAN_LANG || this.mount[4].id == ItemName.BACH_SU_VUONG   // thêm pet mới
                        || this.mount[4].id == ItemName.SIEU_XE_HALLOWEEN
                        || this.mount[4].id == ItemName.PHUONG_HOANG_BANG || this.mount[4].id == ItemName.BACH_HO || this.mount[4].id == ItemName.HOA_KY_LAN) {
                    serverMessage("Nâng cấp thất bại, hao phí 1 Tiến hoá thảo");
                } else {
                    serverMessage("Nâng cấp thất bại, hao phí 1 Chuyển tinh thạch");
                }
            }
        } else {
            serverMessage("Không thể nâng thêm sao");
            return false;
        }
        return true;
    }

    public boolean openMoreOptionForMount() {
        Mount mount = this.mount[4];
        if (mount == null) {
            serverMessage("Vui lòng đeo thú cưỡi trước khi thực hiện");
        } else if (mount.upgrade < 99) {
            serverMessage("Thú cưới chưa đạt cấp độ tối đa");
        } else if (mount.sys < 4) {
            serverMessage("Thú cưỡi cần đạt 5 sao mới có thể thực hiện");
        } else {
            if (mount.id == ItemName.HOA_KY_LAN && mount.options.size() < 11) {
                if (NinjaUtils.nextInt(20) == 0) {
                    boolean isHasOption58 = false;
                    boolean isHasOption128 = false;
                    boolean isHasOption127 = false;
                    boolean isHasOption130 = false;
                    boolean isHasOption131 = false;

                    for (ItemOption op : mount.options) {
                        if (op.optionTemplate.id == 58) {
                            isHasOption58 = true;
                        }
                        if (op.optionTemplate.id == 128) {
                            isHasOption128 = true;
                        }
                        if (op.optionTemplate.id == 127) {
                            isHasOption127 = true;
                        }
                        if (op.optionTemplate.id == 130) {
                            isHasOption130 = true;
                        }
                        if (op.optionTemplate.id == 131) {
                            isHasOption131 = true;
                        }
                    }

                    if (!isHasOption58) {
                        mount.options.add(new ItemOption(58, 20));
                    }
                    if (!isHasOption128) {
                        mount.options.add(new ItemOption(128, 5));
                    }
                    if (!isHasOption127) {
                        mount.options.add(new ItemOption(127, 10));
                    }
                    if (!isHasOption130) {
                        mount.options.add(new ItemOption(130, 10));
                    }
                    if (!isHasOption131) {
                        mount.options.add(new ItemOption(131, 10));
                    }

                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }
            } else if (mount.id == ItemName.BACH_HO && mount.options.size() < 8) {
                if (NinjaUtils.nextInt(20) == 0) {
                    boolean isHasOption58 = false;
                    boolean isHasOption94 = false;
                    for (ItemOption op : mount.options) {
                        if (op.optionTemplate.id == 58) {
                            isHasOption58 = true;
                        }
                        if (op.optionTemplate.id == 94) {
                            isHasOption94 = true;
                        }
                    }

                    if (!isHasOption58) {
                        mount.options.add(new ItemOption(58, 20));
                    }

                    if (!isHasOption94) {
                        mount.options.add(new ItemOption(8, 20));
                    }
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }

            } else if (mount.id == ItemName.SIEU_XE_HALLOWEEN && mount.options.size() < 8) {
                if (NinjaUtils.nextInt(20) == 0) {
                    boolean isHasOption58 = false;
                    boolean isHasOption94 = false;
                    for (ItemOption op : mount.options) {
                        if (op.optionTemplate.id == 58) {
                            isHasOption58 = true;
                        }
                        if (op.optionTemplate.id == 94) {
                            isHasOption94 = true;
                        }
                    }

                    if (!isHasOption58) {
                        mount.options.add(new ItemOption(58, 20));
                    }

                    if (!isHasOption94) {
                        mount.options.add(new ItemOption(8, 20));
                    }
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }

            } else if (mount.id == ItemName.BACH_NGAN_LANG || mount.id == ItemName.BACH_SU_VUONG && mount.options.size() < 8) {  // thêm pet mới
                if (NinjaUtils.nextInt(20) == 0) {
                    boolean isHasOption58 = false;
                    boolean isHasOption94 = false;
                    for (ItemOption op : mount.options) {
                        if (op.optionTemplate.id == 58) {
                            isHasOption58 = true;
                        }
                        if (op.optionTemplate.id == 94) {
                            isHasOption94 = true;
                        }
                    }

                    if (!isHasOption58) {
                        mount.options.add(new ItemOption(58, 20));
                    }

                    if (!isHasOption94) {
                        mount.options.add(new ItemOption(8, 20));
                    }
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }



            } else if (mount.id == ItemName.KIM_NGUU && mount.options.size() == 6) {
                if (NinjaUtils.nextInt(20) == 0) {
                    mount.options.add(new ItemOption(128, NinjaUtils.nextInt(8, 10)));
                    zone.getService().loadMount((Char) this);
                    setAbility();
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm một chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }
            } else if (mount.id == ItemName.HARLEY_DAVIDSON && mount.options.size() == 6) {
                if (NinjaUtils.nextInt(20) == 0) {
                    mount.options.add(new ItemOption(58, NinjaUtils.nextInt(5, 8)));
                    zone.getService().loadMount((Char) this);
                    setAbility();
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm một chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }
            } else if (mount.id == ItemName.PHUONG_HOANG_BANG && mount.options.size() == 6) {
                if (NinjaUtils.nextInt(20) == 0) {
                    mount.options.add(new ItemOption(134, NinjaUtils.nextInt(5)));
                    zone.getService().loadMount((Char) this);
                    setAbility();
                    serverMessage("Khai mở thành công, thú cưỡi được tăng thêm một chỉ số");
                    return true;
                } else {
                    serverMessage("Khai mở thất bại, hao phí 1 Khai Thú Lệnh");
                    return true;
                }
            } else {
                serverMessage("Thú cưỡi không có chỉ số ẩn, không thể khai mở");
            }
        }
        return false;
    }

    public synchronized void itemBagToBox(Item item) {
        int index = getIndexItemByIdInBox(item.id, item.isLock);
        if (index == -1 || !box[index].template.isUpToUp || box[index].hasExpire() || item.isLock != box[index].isLock) {
            for (int i = 0; i < this.numberCellBox; i++) {
                if (box[i] == null) {
                    int indexM = item.index;
                    item.index = i;
                    box[i] = item;
                    bag[indexM] = null;
                    getService().itemBagToBox(indexM, i);
                    return;
                }
            }
            serverDialog("Rương đồ không đủ chỗ trống");
        } else {
            box[index].add(item.getQuantity());
            getService().itemBagToBox(item.index, index);
            bag[item.index] = null;
        }
    }

    public synchronized void itemBoxToBag(Item item) {
        int index = getIndexItemByIdInBag(item.id, item.isLock);
        if (index == -1 || !bag[index].template.isUpToUp || bag[index].hasExpire() || item.isLock != bag[index].isLock) {
            for (int i = 0; i < this.numberCellBag; i++) {
                if (bag[i] == null) {
                    int indexM = item.index;
                    item.index = i;
                    bag[i] = item;
                    box[indexM] = null;
                    getService().itemBoxToBag(indexM, i);
                    return;
                }
            }
            warningBagFull();
        } else {
            bag[index].add(item.getQuantity());
            box[item.index] = null;
            getService().itemBoxToBag(item.index, index);
        }
    }

    public void updateItem(int index, int quantity) {
        if (bag[index] != null) {
            bag[index].add(quantity);
            if (!bag[index].has()) {
                getService().removeItem(index);
                bag[index] = null;
            } else {
                getService().updateItem(bag[index]);
            }
        }
    }

    public synchronized void boxSort() {
        try {
            Vector items = new Vector();
            for (int i = 0; i < this.box.length; i++) {
                Item item = this.box[i];
                if (item != null && item.template.isUpToUp && !item.hasExpire()) {
                    items.addElement(item);
                }
            }
            for (int i = 0; i < items.size(); i++) {
                Item itemi = (Item) items.elementAt(i);
                if (itemi != null) {
                    for (int j = i + 1; j < items.size(); j++) {
                        Item itemj = (Item) items.elementAt(j);
                        if (itemj != null && itemi.template.equals(itemj.template) && itemi.isLock == itemj.isLock) {
                            itemi.add(itemj.getQuantity());
                            this.box[itemj.index] = null;
                            items.setElementAt(null, j);
                        }
                    }
                }
            }
            for (int i = 0; i < this.box.length; i++) {
                if (this.box[i] != null) {
                    for (int j = 0; j <= i; j++) {
                        if (this.box[j] == null) {
                            this.box[j] = this.box[i];
                            this.box[j].index = j;
                            this.box[i] = null;
                            break;
                        }
                    }
                }
            }
            getService().boxSort();
        } catch (Exception e) {
            Log.logException("box sort err: ", Char.class, e);
        }
    }

    public synchronized void bagSort() {
        if (trade != null) {
            warningTrade();
            return;
        }
        try {
            Vector items = new Vector();
            for (int i = 0; i < this.bag.length; i++) {
                Item item = this.bag[i];
                if (item != null && item.template.isUpToUp && !item.hasExpire()) {
                    items.addElement(item);
                }
            }
            for (int i = 0; i < items.size(); i++) {
                Item itemi = (Item) items.elementAt(i);
                if (itemi != null) {
                    for (int j = i + 1; j < items.size(); j++) {
                        Item itemj = (Item) items.elementAt(j);
                        if (itemj != null && itemi.template.equals(itemj.template) && itemi.isLock == itemj.isLock) {
                            itemi.add(itemj.getQuantity());
                            this.bag[itemj.index] = null;
                            items.setElementAt(null, j);
                        }
                    }
                }
            }
            for (int i = 0; i < this.bag.length; i++) {
                if (this.bag[i] != null) {
                    for (int j = 0; j <= i; j++) {
                        if (this.bag[j] == null) {
                            this.bag[j] = this.bag[i];
                            this.bag[j].index = j;
                            this.bag[i] = null;
                            break;
                        }
                    }
                }
            }
            getService().bagSort();
        } catch (Exception e) {
            Log.logException("Bag sort err: ", Char.class, e);
        }
    }
    public void pickItem(Message ms) {
        lockItem.lock();
        try {
            if (isDead) {
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }

            short itemMapId = ms.reader().readShort();
            ItemMap itemMap = zone.findItemMapById(itemMapId);
            if (itemMap != null) {
                itemMap.lock.lock();
                try {
                    if (itemMap.isPickedUp()) {
                        return;
                    }
                    int distance = NinjaUtils.getDistance(this.x, this.y, itemMap.getX(), itemMap.getY());
                    if (distance < 150) {
                        Item item = itemMap.getItem();

                        if (item == null) {
                            return;
                        }
                        if (getSlotNull() == 0 && !(getIndexItemByIdInBag(item.id, item.isLock) != -1 && item.template.isUpToUp)) {

                            serverDialog("Hành trang không đủ chỗ trống");
                            return;

                        }

                        if (item.id == ItemName.CAY_NAM) {
                            if (taskMain == null || taskMain.taskId != TaskName.NV_HAI_NAM || taskMain.index != 1) {
                                serverMessage("Không thể hái nấm");
                                return;
                            }
                        }
                        if (item.id == ItemName.KHOANG_THACH) {
                            if (taskMain == null || taskMain.taskId != TaskName.NV_THU_THAP_NGUYEN_LIEU || taskMain.index != 1) {
                                serverMessage("Không thể nhặt khoáng thạch");
                                return;
                            }
                        }
                        if (item.id == ItemName.TINH_THE_BANG) {
                            if (taskMain == null || taskMain.taskId != TaskName.NV_THU_THAP_TINH_THE_BANG || taskMain.index != 1) {
                                serverMessage("Không thể nhặt tinh thể ");
                                return;
                            }
                        }
                        int ownerID = itemMap.getOwnerID();
                        if (item.id == ItemName.TRUNG_VI_THU) {
                            Char o = (Char) this;
                            threadPool.submit(new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        if (getQuantityItemById(ItemName.DAY_NENSHI) > 0) {
                                            getService().serverMessage("Bắt đầu nhặt trứng");
                                            getService().showWait(zone.tilemap.name);
                                            Thread.sleep(10000L);
                                            if (hp != maxHP) {
                                                getService().serverMessage("Nhặt trứng thất bại");
                                            } else {
                                                removeItemBags(ItemName.DAY_NENSHI, 1);
                                                GlobalService.getInstance().chat("Admin", "Chúc mừng " + name + " đã nhận được Trứng Vĩ Thú");
                                                Item itm = ItemFactory.getInstance().newItem(ItemName.TRUNG_VI_THU);
                                                itm.setQuantity(1);
                                                itm.isLock = false;
                                                addItemToBag(itm);
//                                                EventBus.publish(new ItemPickedEvent((Char) this, itm));
                                                zone.getService().pickItem(o, itemMap);
                                                zone.removeItem(itemMap);
                                            }
                                            getService().endWait();
                                        } else {
                                            getService().serverMessage("Bạn cần có Dây Nenshi");
                                        }
                                    } catch (Exception ex) {
                                        serverDialog(ex.getMessage());
                                    }
                                }
                            }));

                        } else if (ownerID == -1 || this.id == ownerID || itemMap.isCanPickup()) {
                            if (ownerID != -1 && item.template.type == 25 && this.id != ownerID) {
                                serverMessage("Vật phẩm của người khác");
                                return;
                            }
                            int index2 = -1;
                            int requireItemID = itemMap.getRequireItemID();
                            if (requireItemID != -1 && !NinjaUtils.isM1Tet()) {
                                index2 = getIndexItemByIdInBag(requireItemID);
                                if (index2 == -1) {
                                    String name = ItemManager.getInstance().getItemName(requireItemID);
                                    serverMessage("Bạn cần có " + name + " để nhặt");
                                    return;
                                }
                            }
                            boolean isTask = (item.template.type == ItemTemplate.TYPE_TASK || item.template.type == ItemTemplate.TYPE_TASK_WAIT || item.template.type == ItemTemplate.TYPE_TASK_SAVE);
                            if (item.id == ItemName.SUSHI) {
                                itemMap.setPickedUp(true);
                                this.hp += item.getQuantity();
                                if (this.hp > this.maxHP) {
                                    this.hp = this.maxHP;
                                }
                                zone.getService().loadHP((Char) this);
                                getService().updateHp();
                            } else if (item.template.type != ItemTemplate.TYPE_MONEY) {
                                int num = getSlotNull();
                                if (item.template.isUpToUp) {
                                    int index = getIndexItemByIdInBag(item.id, item.isLock);
                                    if (index == -1 && num == 0) {
                                        warningBagFull();
                                        return;
                                    }
                                } else {
                                    if (num == 0) {
                                        warningBagFull();
                                        return;
                                    }
                                }
                                if (requireItemID != -1 && index2 != -1) {
                                    removeItem(index2, 1, true);
                                }
                                if (itemMap instanceof GiftBox) {
                                    Point p = getEventPoint().find(Noel.RECEIVED_GIFT);
                                    if (p.getPoint() > 0) {
                                        serverMessage("Mỗi người chỉ được nhận 1 hộp quà.");
                                        return;
                                    }
                                    p.addPoint(1);
                                }
                                if (itemMap instanceof Envelope) {
                                    Point p = getEventPoint().find(LunarNewYear.ENVELOPE);
                                    if (p.getPoint() >= 10) {
                                        serverMessage("Mỗi người chỉ được nhận tối đa 10 bao lì xì.");
                                        return;
                                    }
                                    p.addPoint(1);
                                }
                                itemMap.setPickedUp(true);

                                if (isTask) {
                                    item.isLock = true;
                                }
                                if (item.id == ItemName.CAY_NAM) {
                                    if (!this.isCatchItem) {
                                        isCatchItem = true;
                                        Char o = (Char) this;
                                        threadPool.submit(new Thread(new Runnable() {

                                            public void run() {
                                                try {
                                                    getService().showWait(zone.tilemap.name);
                                                    Thread.sleep(1000);
                                                    if (!isCleaned) {
                                                        if (!isFailure) {
                                                            Item itm = ItemFactory.getInstance().newItem(ItemName.CAY_NAM);
                                                            itm.setQuantity(1);
                                                            itm.isLock = true;
                                                            addItemToBag(itm);
                                                            updateTaskPickItem(itm);
                                                            zone.getService().pickItem(o, itemMap);
                                                            zone.removeItem(itemMap);
                                                        } else {
                                                            itemMap.setPickedUp(false);
                                                        }
                                                        isFailure = false;
                                                        isCatchItem = false;
                                                        getService().endWait();
                                                    }
                                                } catch (Exception ex) {
                                                    serverDialog(ex.getMessage());
                                                }
                                            }
                                        }));
                                    }
                                } else if (item.id == ItemName.XAC_DOI_LUA) {
                                    int i = getIndexItemByIdInBag(ItemName.TINH_THE_BANG2);
                                    if (i != -1) {
                                        removeItem(i, 1, true);
                                        Item itm = ItemFactory.getInstance().newItem(ItemName.XAC_DOI_LUA2);
                                        itm.setQuantity(1);
                                        itm.isLock = true;
                                        addItemToBag(itm);
                                        if (taskId == TaskName.NV_THU_THAP_XAC_DOI_LUA) {
                                            updateTaskPickItem(itm);
                                        }
                                    } else {
                                        addHp(-1000);
                                        zone.getService().refreshHP((Char) this);
                                        serverMessage("Bạn bị tiêu hao 1000HP vì bị dơi lửa đốt");
                                        if (this.hp <= 0) {
                                            startDie();
                                        }
                                    }
                                } else if (item.id == ItemName.KHOANG_THACH) {
                                    Item itm = ItemFactory.getInstance().newItem(ItemName.KHOANG_THACH);
                                    itm.setQuantity(1);
                                    itm.isLock = true;
                                    addItemToBag(itm);

                                } else if (item.id == ItemName.TINH_THE_BANG) {
                                    Item itm = ItemFactory.getInstance().newItem(ItemName.TINH_THE_BANG);
                                    itm.setQuantity(1);
                                    itm.isLock = true;
                                    addItemToBag(itm);
                                } else {
                                    addItemToBag(item);
                                    if (item.template != null && item.template.isTypeBody() && item.tier == ItemStatCalculator.TIER_5_PERFECT) {
                                        serverMessage("Bạn vừa nhặt được " + item.template.name + " phẩm chất HOÀN MỸ (A5)!");
                                    }
                                    if (item.has(500)) {
                                        History history = new History(this.id, History.NHAT_VAT_PHAM);
                                        history.setBefore(this.coin, user.gold, this.yen);
                                        history.setAfter(this.coin, user.gold, this.yen);
                                        history.addItem(item);
                                        history.setCurrentMap(this.mapId, this.zone.id, itemMap.getId());
                                        history.setTime(System.currentTimeMillis());
                                        History.insert(history);
                                    }
                                }
                            } else {
                                itemMap.setPickedUp(true);
                                long sum = this.yen + (long) item.getQuantity();
                                if (sum > 2000000000) {
                                    this.yen = 2000000000;
                                } else {
                                    this.yen += item.getQuantity();
                                }
                                if (this.yen < 0) {
                                    this.yen = 0;
                                }
                            }
                            if (item.id != ItemName.CAY_NAM && item.id != ItemName.TRUNG_VI_THU) {
                                zone.getService().pickItem((Char) this, itemMap);
                                zone.removeItem(itemMap);
                                EventBus.publish(new ItemPickedEvent((Char) this, item));
                                if (item.template.id == ItemName.YEN) {
                                    if (this.gloryTask != null && this.gloryTask.type == GloryTask.NHAT_YEN) {
                                        this.gloryTask.updateProgress(item.getQuantity());
                                    }
                                }
                                if (item.template.type == ItemTemplate.TYPE_TASK) {
                                    if (this.taskMain != null && this.group != null) {
                                        List<Char> chars = this.group.getCharsInZone(this.mapId, zone.id);
                                        for (Char _char : chars) {
                                            if (_char != null && _char != this && _char.isDead && _char.getSlotNull() > 0) {
                                                if (_char.taskMain != null) {
                                                    if (_char.taskMain.taskId == this.taskMain.taskId && _char.taskMain.index == this.taskMain.index) {
                                                        _char.addItemToBag(Converter.getInstance().newItem(item));
                                                        EventBus.publish(new ItemPickedEvent(_char, item));

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            serverMessage("Vật phẩm của người khác");
                        }
                    }
                } finally {
                    itemMap.lock.unlock();
                }
            }
        } catch (Exception ex) {
            Log.logException("Pick Item err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

    public void throwItem(Message ms) {
        lockItem.lock();

        try {
            if (isDead) {
                return;
            }
            // fix phân thân bỏ ra đất
//            if (!isHuman) {
//                warningClone();
//                return;
//            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            if (this.user.activated == 0) {
                getService().endWait("Tài khoản phải kích hoạt để sử dụng tính năng.");
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (zone.getNumberItem() > 100) {
                serverDialog("Vật phẩm ở đất đã quá nhiều.");
                return;
            }

            byte indexUI = ms.reader().readByte();
            if (indexUI < 0 || indexUI >= this.numberCellBag || this.bag[indexUI] == null || this.bag[indexUI].isLock) {
                return;
            }
            Item item = bag[indexUI];
            if (item != null && this.user.activated == 1) {
                if (item.template.id >= 292 && item.template.id <= 305) {
                    serverDialog("Không thể vất vật phẩm này, chỉ có thể bán.");
                    return;
                }
                if (item.id == ItemName.TRUNG_VI_THU) {
                    this.bag[indexUI] = null;
                } else {
                    short x = (short) (this.x + NinjaUtils.nextInt(-30, 30));
                    short y = zone.tilemap.collisionY(x, this.y);
                    this.bag[indexUI] = null;
                    ItemMap itemMap = ItemMapFactory.getInstance().builder().id(zone.numberDropItem++).x(x).y(y).build();
                    itemMap.setItem(item);
                    itemMap.setOwnerID(-1);
                    zone.addItemMap(itemMap);
                    zone.getService().throwItem((Char) this, indexUI, itemMap);
                    getService().endDlg(false);
                    Item itm = itemMap.getItem();
                    if (itm.isSaveHistory()) {
                        History history = new History(this.id, History.BO_VAT_PHAM);
                        history.setBefore(this.coin, user.gold, this.yen);
                        history.setAfter(this.coin, user.gold, this.yen);
                        history.addItem(item);
                        history.setCurrentMap(this.mapId, this.zone.id, itemMap.getId());
                        history.setTime(System.currentTimeMillis());
                        History.insert(history);
                    }
                }

            }

            removeItem(indexUI, item.getQuantity(), true);
//            getService().saleItem(indexUI, item.getQuantity());
        } catch (Exception ex) {
            Log.logException("ThrowItem err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }

    }
    public void boxCoinIn(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }

            int xu = ms.reader().readInt();
            long sum = (long) xu + coinInBox;
            if (sum > 2000000000) {
                xu = 2000000000 - (int) this.coinInBox;
            }
            if (xu < 0) {
                return;
            }
            if (xu > this.coin) {
                serverDialog("Số xu ở hành trang không đủ.");
                return;
            }
            this.coin -= xu;
            this.coinInBox += xu;
            getService().boxCoinIn(xu);
            updateWithBalanceMessage();
        } catch (Exception ex) {
            Log.logException("Box Coin In err: ", Char.class, ex);
        }
    }

    public void boxCoinOut(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            long xu = ms.reader().readInt();
            long sum = xu + this.coin;
            if (sum > this.coinMax) {
                xu = this.coinMax - this.coin;
            }
            if (xu < 0) {
                return;
            }
            if (xu > this.coinInBox) {
                serverDialog("Số xu ở rương không đủ.");
                return;
            }
            this.coinInBox -= xu;
            this.coin += xu;
            getService().boxCoinOut((int) xu);
            updateWithBalanceMessage();
        } catch (Exception ex) {
            Log.logException("Box Coin Out err: ", Char.class, ex);
        }
    }
    public int getSlotNull() {
        int number = 0;
        for (int i = 0; i < this.numberCellBag; i++) {
            if (bag[i] == null) {
                number++;
            }
        }
        return number;
    }

    public int getCountMount() {
        int number = 0;
        for (int i = 0; i < this.mount.length; i++) {
            if (this.mount[i] != null) {
                number++;
            }
        }
        return number;
    }

    public void saleItem(Message mss) {
        try {
            // fix phân thân bỏ ra đất
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
            byte indexUI = mss.reader().readByte();
            int quantity = 1;
            if (mss.reader().available() == 4) {
                quantity = mss.reader().readInt();
            } else if (mss.reader().available() == 2) {
                quantity = mss.reader().readShort();
            }
            if (bag[indexUI] != null) {
                if (bag[indexUI].upgrade > 0 && !bag[indexUI].template.isTypeMount() && !bag[indexUI].template.isTypeBiKip() && !bag[indexUI].template.isTypeMatThan()) {
                    serverDialog("Vật phẩm này đã nâng cấp không thể bán.");
                    return;
                }
                if (quantity < 1 || !bag[indexUI].has(quantity)) {
                    serverDialog("Số lượng bán không hợp lệ.");
                    return;
                }
                if (bag[indexUI].template.type == 24 || bag[indexUI].template.type == 25) {
                    serverDialog("Không thể bán vật phẩm nhiệm vụ.");
                    return;
                }
                if (bag[indexUI].template.isTypeYoroi()) {
                    serverDialog("Vật phẩm quý giá không thể bán.");
                    return;
                }
                /// chặn bán vật phẩm
                if (bag[indexUI].template.id == ItemName.THOI_TRANG_SAKURA
                        || bag[indexUI].template.id == ItemName.THOI_TRANG_OBITO || bag[indexUI].template.id == ItemName.GAY_MAT_TRANG
                        || bag[indexUI].template.id == ItemName.GAY_TRAI_TIM || bag[indexUI].template.id == ItemName.HAKAIRO_YOROI) {
                    serverDialog("Trang bị hiếm không thể bán");
                    return;
                }


                int[] blackListItems = {568, 569, 570, 571, 397, 398, 399, 400, 401, 402};
                Item itm = bag[indexUI];
                if (itm.template.id > 11 && !IntStream.of(blackListItems).anyMatch(x -> x == itm.template.id)) {
                    History history = new History(this.id, History.BAN_VAT_PHAM);
                    history.setPrice(0, bag[indexUI].yen, 0);
                    history.setBefore(this.coin, user.gold, this.yen);
                    addYen((long) quantity * bag[indexUI].yen);
                    history.setAfter(this.coin, user.gold, this.yen);
                    history.addItem(bag[indexUI]);
                    history.setTime(System.currentTimeMillis());
                    History.insert(history);
                } else {
                    addYen((long) quantity * bag[indexUI].yen);
                }
                removeItem(indexUI, quantity, false);
                getService().saleItem(indexUI, quantity);
            }
        } catch (IOException ex) {
            Log.logException("Sale Item err: ", Char.class, ex);
        }
    }

    public void buyItem(Message mss) { // fix mua item ngoài map
        try {
//            if (!isVillage() && !isSchool() && this.mapId != 98 && this.mapId != 104) {
//                serverDialog("không thể mua đồ tại map" + mapId);
//                return;
//            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
//            if (this.mapId == 98 && this.mapId == 104) {
//                serverDialog("không thể mua đồ tại map" + mapId);
//                return;
//            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (this.taskId < TaskName.NV_DIET_SEN_TRU_COC) {
                getService().endWait("Hãy hoàn nhiệm vụ diệt trừ cóc để sử dụng chức năng này.");
                return;
            }
            byte typeUI = mss.reader().readByte();
            byte indexUI = mss.reader().readByte();
            int quantity = 1;
            if (mss.reader().available() > 0) {
                quantity = mss.reader().readUnsignedShort();
            }
            quantity = Math.min(quantity, Config.getInstance().getMaxQuantity());
            if (quantity < 1) {
                serverDialog("Số lượng không hợp lệ!");
                return;
            }
            Store store = StoreManager.getInstance().find(typeUI);
            if (store != null) {
                store.buy((Char) this, indexUI, quantity);
            }
        } catch (Exception ex) {
            Log.logException("Buy Item err: ", Char.class, ex);
        }
    }
    public void hoaHinhMount(int index) {
        List<Item> list = getListItem5sao(ItemName.XICH_NHAN_NGAN_LANG,ItemName.XE_MAY, ItemName.HARLEY_DAVIDSON);
        if (index < 0 || index >= list.size()) {
            return;
        }
        String name = ItemManager.getInstance().getItemName(ItemName.DUNG_DICH_CAI_TAO);
        int indexItem2 = this.getIndexItemByIdInBag(ItemName.DUNG_DICH_CAI_TAO);
        int quantity = 10;
        if (indexItem2 == -1 || this.bag[indexItem2] == null
                || quantity > this.bag[indexItem2].getQuantity()) {
            serverDialog(String.format("Bạn không có đủ %d %s", quantity, name));
            return;
        }
        if (this.coin < 1000000) {
            serverDialog("Không đủ xu!");
            return;
        }
        addCoin(-1000000);
        removeItem(indexItem2, quantity, true);
        if (NinjaUtils.nextInt(100) < 10) {
            int random = NinjaUtils.nextInt(10, 40);
            ArrayList<ItemOption> newOptions = new ArrayList<>();
            Item item = list.get(index);
            for (ItemOption op : item.options) {
                newOptions.add(new ItemOption(op.optionTemplate.id, op.param));
            }
            item.options.clear();
            item.options.add(new ItemOption(161, random));
            item.options.addAll(newOptions);
            for (byte k = 0; k < item.options.size(); ++k) {
                final ItemOption option = item.options.get(k);
                if (option.optionTemplate.id != 65 && option.optionTemplate.id != 66 && option.optionTemplate.id != 161) {
                    option.param += option.param * random / 100;
                }
            }
            getService().updateInfoMe();
            setAbility();
            setFashion();
            serverDialog("Đã hóa hình " + item.template.name);
        } else {
            serverDialog("Hóa hình thất bại");
        }
    }
    public void doUseBijuu(Item item) {
        doUseEquipmentBijuu(item);
        setBijuu(item.id);
    }

    public void doUseEquipmentBijuu(Item item) {
        int index = 4;
        if (item.template.isTypeEquipmentBijuu()) {
            index = item.template.type - 35;
        }
        if (index != 4 && this.bijuu[4] == null) {
            serverDialog("Hãy sử dụng Vĩ thú!");
            return;
        }
        Item itm = this.bijuu[index];
        if (itm != null) {
            itm.index = item.index;
            itm.isLock = true;
            this.bag[item.index] = itm;
            getService().updateInfoMe();
        } else {
            removeItem(item.index, 1, true);
        }
        item.index = index;
        item.isLock = true;
        this.bijuu[index] = item;
        setAbility();
        getService().onBijuuInfo(this.id, bijuu);
    }

    public void setBijuu(int itemID) {
        switch (itemID) {

            // Nhat vi bao bao
            case 924:
            case 994:
            case 1012:
            case 1030:
                mobBijuu = new Mob((short) 247, true);
                break;

            // Nhat vi
            case 933:
            case 1003:
            case 1021:
            case 1039:
                mobBijuu = new Mob((short) 240, true);
                break;

            // Nhi vi bao bao
            case 925:
            case 995:
            case 1013:
            case 1031:
                mobBijuu = new Mob((short) 248, true);
                break;

            // Nhi vi
            case 934:
            case 1004:
            case 1022:
            case 1040:
                mobBijuu = new Mob((short) 239, true);
                break;

            // Tam vi bao bao
            case 926:
            case 996:
            case 1014:
            case 1032:
                mobBijuu = new Mob((short) 249, true);
                break;

            // Tam vi
            case 935:
            case 1005:
            case 1023:
            case 1041:
                mobBijuu = new Mob((short) 241, true);
                break;

            // Tu vi bao bao
            case 927:
            case 997:
            case 1015:
            case 1033:
                mobBijuu = new Mob((short) 250, true);
                break;

            // Tu vi
            case 936:
            case 1006:
            case 1024:
            case 1042:
                mobBijuu = new Mob((short) 242, true);
                break;

            // Ngu vi bao bao
            case 928:
            case 998:
            case 1016:
            case 1034:
                mobBijuu = new Mob((short) 251, true);
                break;

            // Ngu vi
            case 937:
            case 1007:
            case 1025:
            case 1043:
                mobBijuu = new Mob((short) 256, true);
                break;

            // Luc vi bao bao
            case 929:
            case 999:
            case 1017:
            case 1035:
                mobBijuu = new Mob((short) 252, true);
                break;

            // Luc vi
            case 938:
            case 1008:
            case 1026:
            case 1044:
                mobBijuu = new Mob((short) 243, true);
                break;

            // That vi bao bao
            case 930:
            case 1000:
            case 1018:
            case 1036:
                mobBijuu = new Mob((short) 253, true);
                break;

            // That vi
            case 939:
            case 1009:
            case 1027:
            case 1045:
                mobBijuu = new Mob((short) 244, true);
                break;

            // Bat vi bao bao
            case 931:
            case 1001:
            case 1019:
            case 1037:
                mobBijuu = new Mob((short) 254, true);
                break;

            // Bat vi
            case 940:
            case 1010:
            case 1028:
            case 1046:
                mobBijuu = new Mob((short) 245, true);
                break;

            // Cuu vi bao bao
            case 932:
            case 1002:
            case 1020:
            case 1038:
                mobBijuu = new Mob((short) 255, true);
                break;

            // Cuu vi
            case 941:
            case 1011:
            case 1029:
            case 1047:
                mobBijuu = new Mob((short) 246, true);
                break;

            default:
                mobBijuu = null;
                break;
        }
        if (zone != null) {
            zone.getService().onChangeBijuu(this.id, mobBijuu);
        }
    }

    public void actionBijuu(Message mss) {
        try {
            byte type = mss.reader().readByte();
            if (type == 0) {
                doUnequipBijuu(mss);
            }
        } catch (IOException ex) {
            Log.logException("do action bijuu err", Char.class, ex);
        }
    }

    public void doUnequipBijuu(Message ms) {
        try {
            byte index = ms.reader().readByte();
            unequipBijuu(index);
        } catch (IOException ex) {
            Log.logException("remove bijuu err", Char.class, ex);
        }
    }

    private void unequipBijuu(int index) {
        if (index >= 0 && index < this.bijuu.length) {
            Item item = this.bijuu[index];
            if (item != null) {
                if (index == 4 && (this.bijuu[0] != null || this.bijuu[1] != null || this.bijuu[2] != null || this.bijuu[3] != null)) {
                    serverDialog("Hãy tháo trang bị của Vĩ thú ra trước!");
                    return;
                }
                for (int i = 0; i < numberCellBag; i++) {
                    if (bag[i] == null) {
                        item.isLock = true;
                        item.index = i;
                        if (!item.isExpired()) {
                            this.bag[i] = item;
                            getService().updateInfoMe();
                        }
                        this.bijuu[index] = null;
                        setAbility();
                        getService().onBijuuInfo(this.id, bijuu);
                        if (index == 4) {
                            setBijuu(-1);
                        }
                        return;
                    }
                }
                warningBagFull();
            }
        }
    }
    public void hoaHinh() { // hóa hình
        try {
            if (bag == null || indexMenuBox < 0 || indexMenuBox >= bag.length) {
                serverMessage("Lựa chọn không hợp lệ.");
                return;
            }
            if (user.gold < 500) {
                serverDialog("Bạn phải có ít nhất 500 lượng mới có thể hóa hình.");
                return;
            }
            Item itHoaHinh = ItemCaiTao_HoaHinh[indexMenuBox];
            if (itHoaHinh == null || itHoaHinh.template == null) {
                serverMessage("Không tìm thấy vật phẩm để hóa hình.");
                return;
            }
            int templateId = (itHoaHinh.template.gender == 1) ? ItemName.AO_KURAMA : ItemName.AO_NAKORURU; // ID CẢI TRANG MỚI
            Item newItem = ItemFactory.getInstance().newItem(templateId);
            newItem.isLock = true;
            newItem.expire = itHoaHinh.expire;
            newItem.options = new ArrayList<>();

            if (itHoaHinh.options != null) {
                for (ItemOption opt : itHoaHinh.options) {
                    if (opt.optionTemplate != null) {
                        newItem.options.add(new ItemOption(opt.optionTemplate.id, opt.param));
                    } else {
                        newItem.options.add(new ItemOption(opt.optionTemplate.id, opt.param));
                    }
                }
            }

            addGold(-500);
            removeItem(itHoaHinh.index, 1, true);
            addItemToBag(newItem);
            getService().updateInfoMe();
            getService().openUIHoaHinh();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lỗi khi hóa hình", e);
            serverMessage("Có lỗi xảy ra khi thực hiện hóa hình.");
        }
    }

    public void phanTach() { // hóa hình
        try {
            if (bag == null || indexMenuBox < 0 || indexMenuBox >= bag.length) {
                serverMessage("Lựa chọn không hợp lệ.");
                return;
            }

            Item itTach = ItemPhanTach[indexMenuBox];
            if (itTach == null || itTach.template == null) {
                serverMessage("Không tìm thấy vật phẩm để phân tách.");
                return;
            }
            if (itTach.upgrade > 0) {
                serverMessage("Không thể phân tách với vật phẩm đã chọn.");
                return;
            }
            Item newItem = ItemFactory.getInstance().newItem(ItemName.VAI_THUONG_HANG);
            newItem.isLock = true;
            int rd = NinjaUtils.nextInt(1, 5);
            newItem.setQuantity(rd);
            removeItem(itTach.index, 1, true);
            serverMessage("Tách thành công nhận được " + rd + " vải thượng hạng");
            addItemToBag(newItem);
            getService().updateInfoMe();
            getService().openUITach();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Lỗi khi phân tách", e);
            serverMessage("Có lỗi xảy ra khi thực hiện phân tách.");
        }
    }

}
