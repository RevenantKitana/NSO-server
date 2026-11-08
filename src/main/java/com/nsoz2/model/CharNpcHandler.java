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
import com.nsoz2.combat.engine.CharacterStatsSnapshot;
import com.nsoz2.combat.engine.SingleRollCombatResolver;
import com.nsoz2.constants.*;
import com.nsoz2.convert.Converter;
import com.nsoz2.db.jdbc.DbManager;
import com.nsoz2.db.mongodb.MongoDbConnection;
import com.nsoz2.effect.Effect;
import com.nsoz2.effect.EffectManager;
import com.nsoz2.event.*;
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
import com.nsoz2.store.StoreUpgradeManager; // shop 16
import com.nsoz2.task.*;
import com.nsoz2.thiendia.Ranking;
import com.nsoz2.thiendia.ThienDiaData;
import com.nsoz2.thiendia.ThienDiaManager;
import com.nsoz2.util.Log;
import com.nsoz2.server.ChanLe;
import com.nsoz2.server.ChanLeManager;
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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


/**
 * CharNpcHandler: Phân hệ hội thoại, menu và các tác vụ tương tác NPC (bao gồm Admin Menu).
 * Tách biệt hoàn toàn ~9,000 dòng menu khỏi Char.java.
 */
public abstract class CharNpcHandler extends CharBase {

    public CharNpcHandler(int id) {
        super(id);
    }

    public void openMenu(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            short npcId = ms.reader().readShort();
            typeVXMM = -1;
            Npc npc = zone.getNpc(npcId);
            if (npc == null) {
                serverDialog("Không tìm thấy NPC này");
                return;
            }
            initMenu(npc.template.npcTemplateId);
            if (menus.size() > 0) {
                getService().openUIMenu();
            } else {
                getService().menu();
            }
        } catch (Exception ex) {
            Log.logException("Open Menu err(1): ", Char.class, ex);
        }
    }

    public void npcDeidara() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Hãy cứu lấy ngôi làng này!!!", "Nhiều báu vật của làng đã bị chúng cướp đi", "Chúng thật mạnh, những dị bản nhẫn giả");
            getService().npcChat(NpcName.DEIDARA, talk);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Thánh địa Fujuka", () -> {
            if (countEnterFujukaSanctuary <= 0) {
                getService().npcChat(NpcName.DEIDARA, "Bạn đã hết lượt tham gia.");
                return;
            }
            Map map = MapManager.getInstance().find(MapName.THANH_DIA_FUJUKA);
            FujukaSanctuary fs = new FujukaSanctuary(0, map, (Char) this);
            countEnterFujukaSanctuary--;
            mapBeforeEnterPB = mapId;
            outZone();
            setXY((short) 280, (short) 600);
            fs.join((Char) this);
        }));
    }

    private void npcLongDen() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Rước đèn", () -> {
            if (Event.isTrungThu()) {
                if (!isLeading()) {
                    if (level < 20) {
                        return;
                    }
                    int index = getIndexItemByIdInBag(ItemName.GIAY_THONG_HANH);
                    if (index == -1) {
                        serverDialog("Bạn không có giấy thông hành!");
                        return;
                    }
                    removeItem(index, 1, true);
                    Npc npc = zone.getNpc(NpcName.LONG_DEN_2);
                    if (npc != null) {
                        Bot bot = Bot.builder().id(-this.id).name(npc.template.name).level(0).typePk(PK_NORMAL).build();
                        bot.setDefault();
                        FashionCustom fashionCustom = FashionCustom.builder().head((short) npc.template.headId).body((short) npc.template.bodyId).leg((short) npc.template.legId).weapon((short) -1).build();
                        bot.setFashionStrategy(fashionCustom);
                        AbilityCustom abilityCustom = AbilityCustom.builder().hp(2000).build();
                        bot.setAbilityStrategy(abilityCustom);
                        bot.setAbility();
                        bot.setFashion();
                        bot.recovery();
                        bot.setXY((short) npc.cx, (short) npc.cy);
                        setEscortedEvent(bot);
                        getService().npcUpdate(npc.id, 15);
                        zone.join(bot);
                        setLeading(true);
                        eventPoint.addPoint(EventPoint.DIEM_TIEU_XAI, 1);
                    }
                }
            }
        }));
    }


    // điểm vxmm
    public void updateTopVxmm(long xu) {
        int point = (int) xu / 1000000;
        this.topvxmm += point;
    }

    public void updateTopVxmmLuong(long xu) {
        int point = (int) xu / 1000;
        this.topvxmmLuong += point;
    }

    public void updateTopBoss(int point) {

        this.topBoss += point;
    }

    public void nhanQuaPhanCung() {
        Item item = ItemFactory.getInstance().newItem(1131);
        this.addItemToBag(item);
        addGold(2000);
        addCoin(5000000);
        addYen(50000000);
    }


    public void npcAdmin() {  /// điểm danh tân thủ

        // điểm danh hằng ngày
        Date now = new Date();
        Date dateRollCall = NinjaUtils.getDate(user.lastAttendance);
        if (!DateUtils.isSameDay(now, dateRollCall)) {

            int countCheckout = user.getCountCheckout();
            if (countCheckout > 0) {
                try {
                    DbManager.executeUpdate("UPDATE `users` SET `countCheckout`= ? WHERE  `phone`=?", 0, user.phone);
                } catch (Exception e) {
                    Log.logException("Lỗi npcAdmin(1)  : ", Char.class, e);

                }
                countCheckout = 0;
            }

            int finalCountCheckout = countCheckout;
            menus.add(new Menu(CMDMenu.EXECUTE, "Điểm danh", () -> {
                if (this.user.activated == 0) {
                    serverDialog("Vui lòng kích hoạt tài khoản để sử dụng tính năng");
                    return;
                }
                int count = user.session.getCountAttendance();
                //làm 20nvhn mới được báo danh tránh clone
                if (countDoneTaskDay >= 15 || this.level >= 70) {

                    addYen(5000000);
                    int attendanceCount = Math.max(count, finalCountCheckout);
                    if (attendanceCount > 30) {
                        addGold(200);
                    } else if (attendanceCount > 20) {
                        addGold(300);
                    } else if (attendanceCount > 10) {
                        addGold(400);
                    } else {
                        addGold(500);
                    }

                    user.lastAttendance = now.getTime();
                    user.session.addAttendance();

                    try {
                        DbManager.executeUpdate("UPDATE `users` SET `countCheckout`= `countCheckout`+ ? WHERE  `phone`=?", 1, user.phone);
                    } catch (Exception e) {
                        Log.logException("Lỗi npcAdmin(2)  : ", Char.class, e);

                    }

                } else {
                    getService().npcChat(NpcName.ADMIN, "Bạn phải hoàn thành 15 nhiệm vụ hằng ngày mới điểm danh được");

                }
            }));
        }



        if (user.FirstGift == 0) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Quà nạp đầu", () -> {
                try {
                    if (!user.rankFirstGift) {
                        getService().npcChat(NpcName.ADMIN, "Bạn phải nạp 30.000đ trở lên");
                    } else if (user.FirstGift == 0) {
                        if (this.getSlotNull() < 2) {
                            getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                            return;
                        }
                        Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                        checkUPTOUP(item1, 1, true, -1);
                        user.FirstGift = 1;
                        DbManager.executeUpdate("UPDATE `users` SET `first_gift`=? WHERE `id`=?", user.FirstGift, user.id);
                        this.saveData();
                    } else {
                        service.serverDialog("Bạn đã nhận quà rồi");
                    }
                } catch (Exception e) {
                    Log.logException("Lỗi nhận quà : ", Char.class, e);

                }

            }));
        }

        menus.add(new Menu(CMDMenu.EXECUTE, "Quà tân thủ", () -> {
            nhanTanthu();
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Quà đăng nhập", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Xem ngày", () -> {
                getService().npcChat(NpcName.ADMIN, "Bạn đã đăng nhập tới ngày " + this.user.dayLogin);
            }));

            if (this.user.giftLogin[0] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 1", () -> {
                    if (this.user.dayLogin < 1) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[0] = 1;
                    this.addGold(500);
                    this.addYen(1000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 10, true, -1);
                    this.saveData();

                }));
            }
            if (this.user.giftLogin[1] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 3", () -> {
                    if (this.user.dayLogin < 3) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[1] = 1;
                    this.addGold(500);
                    this.addYen(3000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 20, true, -1);
                    this.saveData();

                }));
            }
            if (this.user.giftLogin[2] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 5", () -> {
                    if (this.user.dayLogin < 5) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[2] = 1;
                    this.addGold(500);
                    this.addYen(5000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 30, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 5, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[3] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 7", () -> {
                    if (this.user.dayLogin < 7) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[3] = 1;
                    this.addGold(500);
                    this.addYen(7000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 30, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[4] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 9", () -> {
                    if (this.user.dayLogin < 9) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[4] = 1;
                    this.addGold(500);
                    this.addYen(9000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 30, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 15, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[5] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 11", () -> {
                    if (this.user.dayLogin < 11) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[5] = 1;
                    this.addGold(600);
                    this.addYen(11000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAT_BAO);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 15, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[6] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 13", () -> {
                    if (this.user.dayLogin < 13) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    this.user.giftLogin[6] = 1;
                    this.addGold(600);
                    this.addYen(13000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_TRUNG_CAP);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[7] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 15", () -> {
                    if (this.user.dayLogin < 15) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[7] = 1;
                    this.addGold(600);
                    this.addYen(15000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.XICH_NHAN_NGAN_LANG);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[8] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 17", () -> {
                    if (this.user.dayLogin < 17) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    this.user.giftLogin[8] = 1;
                    this.addGold(600);
                    this.addYen(17000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 15, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[9] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 19", () -> {
                    if (this.user.dayLogin < 19) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    this.user.giftLogin[9] = 1;
                    this.addGold(600);
                    this.addYen(19000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[10] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 21", () -> {
                    if (this.user.dayLogin < 21) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 5) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 5 ô trống");
                        return;
                    }
                    this.user.giftLogin[10] = 1;
                    this.addGold(700);
                    this.addYen(21000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 4, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 25, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[11] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 23", () -> {
                    if (this.user.dayLogin < 23) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    this.user.giftLogin[11] = 1;
                    this.addGold(700);
                    this.addYen(23000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[12] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 25", () -> {
                    if (this.user.dayLogin < 25) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[12] = 1;
                    this.addGold(700);
                    this.addYen(25000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[13] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 27", () -> {
                    if (this.user.dayLogin < 27) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    this.user.giftLogin[13] = 1;
                    this.addGold(700);
                    this.addYen(27000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[14] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 29", () -> {
                    if (this.user.dayLogin < 29) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    this.user.giftLogin[14] = 1;
                    this.addGold(700);
                    this.addYen(29000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 40, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[15] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 31", () -> {
                    if (this.user.dayLogin < 31) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[15] = 1;
                    this.addGold(800);
                    this.addYen(31000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.XE_MAY);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HANG_DONG);
                    checkUPTOUP(item2, 5, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[16] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 33", () -> {
                    if (this.user.dayLogin < 33) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[16] = 1;
                    this.addGold(800);
                    this.addYen(33000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HOAN_CHUYEN_CAO_CAP);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[17] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 35", () -> {
                    if (this.user.dayLogin < 35) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[17] = 1;
                    this.addGold(800);
                    this.addYen(35000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HOAN_CHUYEN_CAO_CAP);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[18] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 37", () -> {
                    if (this.user.dayLogin < 37) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[18] = 1;
                    this.addGold(800);
                    this.addYen(37000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HOAN_CHUYEN_CAO_CAP);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[19] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 39", () -> {
                    if (this.user.dayLogin < 39) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[19] = 1;
                    this.addGold(800);
                    this.addYen(39000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HOAN_CHUYEN_CAO_CAP);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 40, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[20] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 41", () -> {
                    if (this.user.dayLogin < 41) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[20] = 1;
                    this.addGold(900);
                    this.addYen(41000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 40, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[21] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 43", () -> {
                    if (this.user.dayLogin < 43) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[21] = 1;
                    this.addGold(900);
                    this.addYen(43000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 10, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[22] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 45", () -> {
                    if (this.user.dayLogin < 45) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[22] = 1;
                    this.addGold(900);
                    this.addYen(45000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 20, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[23] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 47", () -> {
                    if (this.user.dayLogin < 47) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[23] = 1;
                    this.addGold(900);
                    this.addYen(47000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 30, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[24] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 49", () -> {
                    if (this.user.dayLogin < 49) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[24] = 1;
                    this.addGold(900);
                    this.addYen(49000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 40, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 40, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[25] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 51", () -> {
                    if (this.user.dayLogin < 51) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[25] = 1;
                    this.addGold(1000);
                    this.addYen(51000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HARLEY_DAVIDSON);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[26] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 53", () -> {
                    if (this.user.dayLogin < 53) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[26] = 1;
                    this.addGold(1000);
                    this.addYen(53000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HAC_NGUU);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[27] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 55", () -> {
                    if (this.user.dayLogin < 55) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[27] = 1;
                    this.addGold(1000);
                    this.addYen(55000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.XICH_NHAN_NGAN_LANG);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[28] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 57", () -> {
                    if (this.user.dayLogin < 57) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[28] = 1;
                    this.addGold(1000);
                    this.addYen(57000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.XE_MAY);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (this.user.giftLogin[29] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ngày 59", () -> {
                    if (this.user.dayLogin < 59) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa đăng nhập đủ số ngày");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    this.user.giftLogin[29] = 1;
                    this.addGold(1000);
                    this.addYen(59000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }


            getService().openUIMenu();
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Quà giờ chơi", () -> {
            menus.clear();
            int hourOnline = timeOnline / 60;
            menus.add(new Menu(CMDMenu.EXECUTE, "Xem giờ", () -> {
                getService().npcChat(NpcName.ADMIN, "Số giờ bạn online là " + hourOnline);
            }));
            if (giftOnline[0] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "2 giờ", () -> {
                    if (timeOnline < 120) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    giftOnline[0] = 1;
                    this.addGold(0);
                    this.addYen(2000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_SO_CAP);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[1] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "4 giờ", () -> {
                    if (timeOnline < 240) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 7) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 7 ô trống");
                        return;
                    }
                    giftOnline[1] = 1;
                    this.addGold(0);
                    this.addYen(2500000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_SO_CAP);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[2] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "6 giờ", () -> {
                    if (timeOnline < 360) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    giftOnline[2] = 1;
                    this.addGold(0);
                    this.addYen(3000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_TRUNG_CAP);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[3] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "8 giờ", () -> {
                    if (timeOnline < 480) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    giftOnline[3] = 1;
                    this.addGold(0);
                    this.addYen(3500000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_TRUNG_CAP);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[4] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "10 giờ", () -> {
                    if (timeOnline < 600) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[4] = 1;
                    this.addGold(0);
                    this.addYen(4000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_TRUNG_CAP);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[5] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "20 giờ", () -> {
                    if (timeOnline < 1200) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[5] = 1;
                    this.addGold(0);
                    this.addYen(5000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_TRUNG_CAP);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[6] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "30 giờ", () -> {
                    if (timeOnline < 1800) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    giftOnline[6] = 1;
                    this.addGold(0);
                    this.addYen(6000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 2, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[7] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "40 giờ", () -> {
                    if (timeOnline < 2400) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[7] = 1;
                    this.addGold(0);
                    this.addYen(7000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_CAO_CAP);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[8] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "50 giờ", () -> {
                    if (timeOnline < 3000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[8] = 1;
                    this.addGold(0);
                    this.addYen(8000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HUYET_SAC_HUNG_LANG);
                    checkUPTOUP(item1, 1, true, 10);
                    this.saveData();
                }));
            }
            if (giftOnline[9] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "60 giờ", () -> {
                    if (timeOnline < 3600) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[9] = 1;
                    this.addGold(0);
                    this.addYen(9000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item1, 20, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[10] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "70 giờ", () -> {
                    if (timeOnline < 4200) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[10] = 1;
                    this.addGold(0);
                    this.addYen(10000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 10, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[11] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "80 giờ", () -> {
                    if (timeOnline < 4800) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[11] = 1;
                    this.addGold(0);
                    this.addYen(10000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CAN_CAU_VANG);
                    checkUPTOUP(item1, 20, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 30, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[12] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "90 giờ", () -> {
                    if (timeOnline < 5400) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[12] = 1;
                    this.addGold(0);
                    this.addYen(12000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 20, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[13] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "100 giờ", () -> {
                    if (timeOnline < 6000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    giftOnline[13] = 1;
                    this.addGold(500);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CO_LENH);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.THIEN_NHAN_PHU);
                    checkUPTOUP(item2, 3, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[14] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "200 giờ", () -> {
                    if (timeOnline < 12000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[14] = 1;
                    this.addGold(500);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.CO_LENH);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.THIEN_NHAN_PHU);
                    checkUPTOUP(item2, 5, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[15] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "500 giờ", () -> {
                    if (timeOnline < 30000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    giftOnline[15] = 1;
                    this.addGold(1000);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAT_BAO);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item2, 3, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[16] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "1000 giờ", () -> {
                    if (timeOnline < 60000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[16] = 1;
                    this.addGold(3000);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item2, 5, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[17] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "1500 giờ", () -> {
                    if (timeOnline < 90000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    giftOnline[17] = 1;
                    this.addGold(5000);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.BAO_HIEM_NHU_Y);
                    checkUPTOUP(item2, 5, true, -1);
                    this.saveData();
                }));
            }
            if (giftOnline[18] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "2000 giờ", () -> {
                    if (timeOnline < 120000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[18] = 1;
                    this.addGold(7000);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HAKAIRO_YOROI);
                    checkUPTOUP(item1, 1, true, 30);
                    this.saveData();
                }));
            }
            if (giftOnline[19] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "3000 giờ", () -> {
                    if (timeOnline < 180000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa online đủ thời gian");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    giftOnline[19] = 1;
                    this.addGold(10000);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.TON_HANH_GIA);
                    checkUPTOUP(item1, 1, true, 30);
                    this.saveData();
                }));
            }

            getService().openUIMenu();
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Quà nạp tổng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Xem tổng tiền", () -> {
                getService().npcChat(NpcName.ADMIN, "Tổng số tiền bạn đã nạp là " + NinjaUtils.getCurrency(this.user.tongnap) + "vnđ");
            }));

            if (user.rewardTongnap[0] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "1 vnđ", () -> {
                    if (user.tongnap < 1) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 1 vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 2) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
                        return;
                    }
                    user.rewardTongnap[0] = 1;
                    this.addGold(0);
                    this.addYen(0);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHIEU_MAY_MAN);
                    checkUPTOUP(item2, 10, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[1] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "1 triệu", () -> {
                    if (user.tongnap < 1000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 1 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    user.rewardTongnap[1] = 1;
                    this.addGold(0);
                    this.addYen(100000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BAT_BAO);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item2, 2, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[2] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "2 triệu", () -> {
                    if (user.tongnap < 2000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 2 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 6) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 6 ô trống");
                        return;
                    }
                    user.rewardTongnap[2] = 1;
                    this.addGold(0);
                    this.addYen(200000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item2, 2, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.HAKAIRO_YOROI);
                    checkUPTOUP(item3, 1, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[3] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "5 triệu", () -> {
                    if (user.tongnap < 5000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 5 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 9) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 9 ô trống");
                        return;
                    }
                    user.rewardTongnap[3] = 1;
                    this.addGold(0);
                    this.addYen(500000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item2, 3, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.TON_HANH_GIA);
                    checkUPTOUP(item3, 1, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[4] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "10 triệu", () -> {
                    if (user.tongnap < 10000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 10 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 14) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 14 ô trống");
                        return;
                    }
                    user.rewardTongnap[4] = 1;
                    this.addGold(0);
                    this.addYen(1000000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                    checkUPTOUP(item1, 3, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item2, 5, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.BANH_TRUNG_THU_BANG_HOA);
                    checkUPTOUP(item3, 5, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[5] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "15 triệu", () -> {
                    if (user.tongnap < 15000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 15 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 11) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 11 ô trống");
                        return;
                    }
                    user.rewardTongnap[5] = 1;
                    this.addGold(0);
                    this.addYen(1500000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                    checkUPTOUP(item1, 5, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                    checkUPTOUP(item2, 5, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.TUAN_LOC);
                    checkUPTOUP(item3, 1, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[6] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "20 triệu", () -> {
                    if (user.tongnap < 20000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 20 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    user.rewardTongnap[6] = 1;
                    this.addGold(0);
                    this.addYen(2000000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.PET_YEU_TINH);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.GAY_MAT_TRANG);
                    checkUPTOUP(item2, 1, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.MAT_NA_HO);
                    checkUPTOUP(item3, 1, true, -1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[7] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "30 triệu", () -> {
                    if (user.tongnap < 30000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 30 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 3) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 3 ô trống");
                        return;
                    }
                    user.rewardTongnap[7] = 1;
                    this.addGold(500000);
                    this.addYen(2000000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.HOA_KY_LAN);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.GAY_TRAI_TIM);
                    checkUPTOUP(item2, 1, true, -1);
                    int[] idMn = {ItemName.MAT_NA_SHIN_AH, ItemName.MAT_NA_VO_DIEN, ItemName.MAT_NA_ONI, ItemName.MAT_NA_KUMA, ItemName.MAT_NA_INU};
                    int itemIDMn = idMn[NinjaUtils.nextInt(idMn.length)];
                    Item Mn = ItemFactory.getInstance().newItem(itemIDMn);
                    Mn.expire = -1;
                    Mn.setLock(true);
                    addItemToBag(Mn);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[8] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "50 triệu", () -> {
                    if (user.tongnap < 50000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 50 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 5) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 5 ô trống");
                        return;
                    }
                    user.rewardTongnap[8] = 1;
                    this.addGold(1000000);
                    this.addYen(2000000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BACH_HO);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.PHUONG_HOANG_BANG);
                    checkUPTOUP(item2, 1, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.THE_DOI_TEN);
                    checkUPTOUP(item3, 1, true, -1);
                    int[] idMn = {ItemName.MAT_NA_SHIN_AH, ItemName.MAT_NA_VO_DIEN, ItemName.MAT_NA_ONI, ItemName.MAT_NA_KUMA, ItemName.MAT_NA_INU};
                    int itemIDMn = idMn[NinjaUtils.nextInt(idMn.length)];
                    Item Mn = ItemFactory.getInstance().newItem(itemIDMn);
                    Mn.expire = -1;
                    Mn.setLock(true);
                    addItemToBag(Mn);
                    int[] idMn1 = {ItemName.MAT_NA_SHIN_AH, ItemName.MAT_NA_VO_DIEN, ItemName.MAT_NA_ONI, ItemName.MAT_NA_KUMA, ItemName.MAT_NA_INU};
                    int itemIDMn1 = idMn1[NinjaUtils.nextInt(idMn1.length)];
                    Item Mn1 = ItemFactory.getInstance().newItem(itemIDMn1);
                    Mn1.expire = -1;
                    Mn1.setLock(true);
                    addItemToBag(Mn1);
                    this.saveData();
                }));
            }
            if (user.rewardTongnap[9] != 1) {
                menus.add(new Menu(CMDMenu.EXECUTE, "80 triệu", () -> {
                    if (user.tongnap < 80000000) {
                        getService().npcChat(NpcName.ADMIN, "Bạn chưa nạp đủ 80 triệu vnđ");
                        return;
                    }
                    if (this.getSlotNull() < 4) {
                        getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 4 ô trống");
                        return;
                    }
                    user.rewardTongnap[9] = 1;
                    this.addGold(1500000);
                    this.addYen(2000000000);
                    Item item1 = ItemFactory.getInstance().newItem(ItemName.BACH_HO);
                    checkUPTOUP(item1, 1, true, -1);
                    Item item2 = ItemFactory.getInstance().newItem(ItemName.HOA_KY_LAN);
                    checkUPTOUP(item2, 1, true, -1);
                    Item item3 = ItemFactory.getInstance().newItem(ItemName.PHUONG_HOANG_BANG);
                    checkUPTOUP(item3, 1, true, -1);
                    Item item4 = ItemFactory.getInstance().newItem(ItemName.THE_DOI_TEN);
                    checkUPTOUP(item4, 1, true, -1);
                    this.saveData();
                }));
            }

            getService().openUIMenu();
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Bỏ qua nhiệm vụ", () -> {
            if (this.taskId >= 43) {
                getService().npcChat(NpcName.ADMIN, "Con đã hoàn thành hoặc bỏ qua toàn bộ nhiệm vụ rồi, không thể thực hiện thêm!");
                return;
            }
            if (this.taskId < 9) {
                getService().npcChat(NpcName.ADMIN, "Ngươi cần làm hết nhiệm vụ làng tone mới có thể thực hiện");
                return;
            }
            menus.clear();
            getService().npcChat(NpcName.ADMIN, "Con có muốn bỏ qua toàn bộ nhiệm vụ với giá 3.000 lượng không?");
            menus.add(new Menu(CMDMenu.EXECUTE, "Đồng ý", () -> {
                if (this.taskId >= 43) {
                    serverDialog("Con đã hoàn thành hoặc bỏ qua toàn bộ nhiệm vụ rồi, không thể thực hiện thêm!");
                    return;
                }
                if (this.taskId < 9) {
                    getService().npcChat(NpcName.ADMIN, "Ngươi cần làm hết nhiệm vụ làng tone mới có thể thực hiện");
                    return;
                }
                if (getSlotNull() < 7) {
                    warningBagFull();
                    return;
                }
                if (user.gold < 3000) {
                    serverDialog("Bạn không đủ 3.000 lượng");
                    return;
                }
                addGold(-3000);
                for (int i = ItemName.NGOC_1_SAO; i <= ItemName.NGOC_7_SAO; i++) {
                    Item ngoc = ItemFactory.getInstance().newItem(i);
                    ngoc.isLock = true;
                    addItemToBag(ngoc);
                }
                this.taskId = 43;
                this.taskMain = null;
                for (int i = 0; i < this.bag.length; i++) {
                    if (this.bag[i] != null && ((int) this.bag[i].template.type == 25
                            || (int) this.bag[i].template.type == 23 || (int) this.bag[i].template.type == 24)) {
                        this.bag[i] = null;
                    }
                }
                getService().clearTask();
                this.saveData();
                serverDialog("Bỏ qua nhiệm vụ thành công, máy chủ sẽ ngắt kết nối sau 3 giây");
                int TimeSeconds = 3;
                while (TimeSeconds > 0) {
                    TimeSeconds--;
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Char.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!isCleaned) {
                    this.user.session.closeMessage();
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hủy bỏ", () -> {
            }));
            getService().openUIMenu();
        }));


        String t = (notReceivedExp ? "Tắt" : "Bật");
        menus.add(new Menu(CMDMenu.EXECUTE, String.format("[%s]\nKhông nhận kinh nghiệm", t), () -> {
            if (this.level < 30 && this.notReceivedExp) {
                this.notReceivedExp = false;
                serverDialog("Tắt không nhận kinh nghiệm");
                return;
            }
            if (this.level < 30) {
                serverDialog("Phải trên level 30 mới có thể sử dụng tính năng này ");
                return;
            }
            this.notReceivedExp = !this.notReceivedExp;
            if (this.notReceivedExp) {
                serverDialog("Bật không nhận kinh nghiệm");
            } else {
                serverDialog("Tắt không nhận kinh nghiệm");
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Quà fancung", () -> {
            if (this.timeCanGiveaways > System.currentTimeMillis()) {
                serverDialog("Vui lòng thử lại sau "
                        + ((this.timeCanGiveaways - System.currentTimeMillis()) / 1_000) + " giây.");
                return;
            }
            this.timeCanGiveaways = System.currentTimeMillis() + 10_000;
            Giveaways.getInstance().give((Char) this);
        }));

//        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận ngọc 10", () -> {
//            if (this.getSlotNull() < 2) {
//                getService().npcChat(NpcName.ADMIN, "Hành trang tối thiểu 2 ô trống");
//                return;
//            }
//            int[] idngoc = {ItemName.LUC_NGOC, ItemName.LAM_TINH_NGOC, ItemName.HUYET_NGOC, ItemName.HUYEN_TINH_NGOC};
//            int itemID = idngoc[NinjaUtils.nextInt(idngoc.length)];
//            addNgoc(itemID, 10, true, false);
//            this.saveData();
//        }));


//        menus.add(new Menu(CMDMenu.EXECUTE, "Quà mốc nạp", () -> {
//            menus.clear();
//            menus.add(new Menu(CMDMenu.EXECUTE, "BXH Nạp", () -> {
//                showRankedList(4);
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Quà mốc bạc", () -> {
//                this.giftRankSilver(this);
//            }));
//
//            menus.add(new Menu(CMDMenu.EXECUTE, "Quà mốc vàng", () -> {
//                this.giftRankGold(this);
//            }));
//
//            menus.add(new Menu(CMDMenu.EXECUTE, "Quà mốc kim cương", () -> {
//                this.giftRankDiamond(this);
//            }));
//            getService().openUIMenu();
//        }));


//        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận quà", () -> {
//            if (this.timeCanGiveaways > System.currentTimeMillis()) {
//                serverDialog("Vui lòng thử lại sau "
//                        + ((this.timeCanGiveaways - System.currentTimeMillis()) / 1_000) + " giây.");
//                return;
//            }
//            this.timeCanGiveaways = System.currentTimeMillis() + 10_000;
//            Giveaways.getInstance().give((Char) this);
//        }));
//        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận gói quà", () -> {
//            if (this.timeCanNhanQua > System.currentTimeMillis()) {
//                serverDialog("Vui lòng thử lại sau "
//                        + ((this.timeCanNhanQua - System.currentTimeMillis()) / 1_000) + " giây.");
//                return;
//            }
//            this.timeCanNhanQua = System.currentTimeMillis() + 10_000;
//            int canReceivGift = checkNewBieGift();
//            try {
//                if (canReceivGift == 1) {
//                    JSONObject dataGift = getDataNewBieGift();
//                    if (dataGift != null) {
//                        boolean isOk = false;
//                        //
//                        String notice = "Chúc mừng bạn đã được tặng:\n\n";
//                        String notice2 = "";
//                        // item
//                        if (dataGift.containsKey("items")) {
//                            try {
//                                JSONArray js = (JSONArray) JSONValue.parse(dataGift.get("items").toString());
//                                if (js.size() > getSlotNull()) {
//                                    serverDialog("Chừa trống ít nhất " + js.size()
//                                            + " chỗ trong hành trang để có thể nhận");
//                                    return;
//                                }
//                                for (int k = 0; k < js.size(); k++) {
//                                    JSONObject jsob = (JSONObject) JSONValue.parse(js.get(k).toString());
//                                    if (jsob.containsKey("id") && NinjaUtils.isNumericInt(jsob.get("id").toString())) {
//                                        int value = Integer.parseInt(jsob.get("id").toString());
//                                        Item itm = ItemFactory.getInstance().newItem(value);
//                                        // hạn
//                                        String expireStr = "";
//                                        if (jsob.containsKey("expire")
//                                                && NinjaUtils.isNumericInt(jsob.get("expire").toString())) {
//                                            int day = Integer.parseInt(jsob.get("expire").toString());
//                                            long expire = System.currentTimeMillis() + (60_000L * 60 * 24 * day);
//                                            if (day != -1) {
//                                                itm.expire = expire;
//                                                expireStr = ". Hạn " + day + " ngày";
//                                            } else {
//                                                itm.expire = -1;
//                                            }
//                                        } else {
//                                            itm.expire = -1;
//                                        }
//                                        this.addItemToBag(itm);
//                                        notice2 += ("\n+ " + itm.template.name + expireStr);
//                                    }
//                                }
//                            } catch (Exception e) {
//                                Log.logException("Lỗi npcAdmin(4)  : ", Char.class, e);
//
//                                serverDialog("Có lỗi xảy ra, hãy thử lại sau (lỗi không tìm được item)");
//                                return;
//                            }
//                        }
//                        // yen
//                        if (dataGift.containsKey("yen")
//                                && NinjaUtils.isNumericLong(dataGift.get("yen").toString())) {
//                            long value = Long.parseLong(dataGift.get("yen").toString());
//                            if (value > 0) {
//                                addYen(value);
//                                isOk = true;
//                                notice += "\n+ " + NinjaUtils.numFormat(value) + " Yên";
//                            }
//                        }
//                        // luong
//                        if (dataGift.containsKey("luong")
//                                && NinjaUtils.isNumericLong(dataGift.get("luong").toString())) {
//                            long value = Long.parseLong(dataGift.get("luong").toString());
//                            if (value > 0) {
//                                addGold((int) value);
//                                isOk = true;
//                                notice += "\n+ " + NinjaUtils.numFormat(value) + " Lượng";
//                            }
//                        }
//                        if (isOk) {
//                            DbManager.executeUpdate(
//                                    "UPDATE `users` SET `gift_new_bie`= ? WHERE `id` = ? LIMIT 1;", 2,
//                                    this.user.id);
//                            DbManager.executeUpdate("UPDATE `players` SET `gift`= ? WHERE `id` = ? LIMIT 1;",
//                                    "", this.id);
//                            this.user.service.showAlert("Quà tặng", notice + notice2);
//                        } else {
//                            serverDialog("Có lỗi xảy ra, hãy thử lại sau (lỗi không tìm thấy)");
//                        }
//                    } else {
//                        serverDialog("Bạn không có phần thưởng để nhận");
//                    }
//                } else {
//                    serverDialog("Bạn không có phần thưởng để nhận");
//                }
//            } catch (Exception ee) {
//                Log.logException("Lỗi npcAdmin(5)  : ", Char.class, ee);
//
//            }
//        }));


    }

    public void nhanTanthu() {
        if (this.user == null) {
            return;
        }
        if (System.currentTimeMillis() < this.user.session.lastTimeSpam) {
            serverDialog("Thao tác quá nhanh");
            return;
        }
        this.user.session.lastTimeSpam = System.currentTimeMillis() + 10000L;
        int canReceivGift = checkNewBieGift();
        try {
            if (canReceivGift == 1) {
                JSONObject dataGift = getDataNewBieGift();
                if (dataGift != null) {
                    boolean isOk = false;
                    //
                    String notice = "Chúc mừng, bạn đã nhận được\n";
                    String notice2 = "";
                    // item
                    if (dataGift.containsKey("items")) {
                        try {
                            JSONArray js = (JSONArray) JSONValue.parse(dataGift.get("items").toString());
                            if (js.size() > getSlotNull()) {
                                serverDialog("Chừa trống ít nhất " + js.size()
                                        + " chỗ trong hành trang để có thể nhận");
                                return;
                            }
                            for (int k = 0; k < js.size(); k++) {
                                JSONObject jsob = (JSONObject) JSONValue.parse(js.get(k).toString());
                                if (jsob.containsKey("id") && NinjaUtils.isNumericInt(jsob.get("id").toString())) {
                                    int value = Integer.parseInt(jsob.get("id").toString());
                                    int quantity = Integer.parseInt(jsob.get("quantity").toString());
                                    Item itm = ItemFactory.getInstance().newItem(value);
                                    // hạn
                                    if (itm.template.isUpToUp) {
                                        itm.setQuantity(quantity);
                                        String expireStr = "";
                                        if (jsob.containsKey("expire")
                                                && NinjaUtils.isNumericInt(jsob.get("expire").toString())) {
                                            int day = Integer.parseInt(jsob.get("expire").toString());
                                            long expire = System.currentTimeMillis() + (60_000L * 60 * 24 * day);
                                            if (day != -1) {
                                                itm.expire = expire;
                                                expireStr = " [" + day + " ngày]";
                                            } else {
                                                itm.expire = -1;
                                            }
                                        } else {
                                            itm.expire = -1;
                                        }
                                        if (jsob.containsKey("isLock")
                                                && NinjaUtils.isNumericInt(jsob.get("isLock").toString())) {
                                            int lock = Integer.parseInt(jsob.get("isLock").toString());
                                            if (lock == 1) {
                                                itm.isLock = true;
                                            } else {
                                                itm.isLock = false;
                                            }
                                        } else {
                                            itm.isLock = false;
                                        }
                                        if (jsob.containsKey("options")) {
                                            ParseData parse = new ParseData(jsob);
                                            JSONArray ability = parse.getJSONArray("options");
                                            int size2 = ability.size();
                                            if (size2 > 0) {
                                                itm.options.clear();
                                                for (int c = 0; c < size2; c++) {
                                                    JSONArray jAbility = (JSONArray) ability.get(c);
                                                    int templateId = Integer.parseInt(jAbility.get(0).toString());
                                                    int param = Integer.parseInt(jAbility.get(1).toString());
                                                    if (templateId == 46 && param == 800) {
                                                        param = 55;
                                                    }
                                                    itm.options.add(new ItemOption(templateId, param));
                                                }
                                            }
                                        }
                                        this.addItemToBag(itm);
                                        notice2 += ("\n+ " + itm.template.name + expireStr);
                                    } else {
                                        for (int i = 0; i < quantity; i++) {
                                            Item item = ItemFactory.getInstance().newItem(value);
                                            item.setQuantity(1);
                                            String expireStr = "";
                                            if (jsob.containsKey("expire")
                                                    && NinjaUtils.isNumericInt(jsob.get("expire").toString())) {
                                                int day = Integer.parseInt(jsob.get("expire").toString());
                                                long expire = System.currentTimeMillis() + (60_000L * 60 * 24 * day);
                                                if (day != -1) {
                                                    item.expire = expire;
                                                    expireStr = " [" + day + " ngày]";
                                                } else {
                                                    item.expire = -1;
                                                }
                                            } else {
                                                item.expire = -1;
                                            }
                                            if (jsob.containsKey("isLock")
                                                    && NinjaUtils.isNumericInt(jsob.get("isLock").toString())) {
                                                int lock = Integer.parseInt(jsob.get("isLock").toString());
                                                if (lock == 1) {
                                                    item.isLock = true;
                                                } else {
                                                    item.isLock = false;
                                                }
                                            } else {
                                                item.isLock = false;
                                            }
                                            if (jsob.containsKey("options")) {
                                                ParseData parse = new ParseData(jsob);
                                                JSONArray ability = parse.getJSONArray("options");
                                                int size2 = ability.size();
                                                if (size2 > 0) {
                                                    item.options.clear();
                                                    for (int c = 0; c < size2; c++) {
                                                        JSONArray jAbility = (JSONArray) ability.get(c);
                                                        int templateId = Integer.parseInt(jAbility.get(0).toString());
                                                        int param = Integer.parseInt(jAbility.get(1).toString());
                                                        if (templateId == 46 && param == 800) {
                                                            param = 55;
                                                        }
                                                        item.options.add(new ItemOption(templateId, param));
                                                    }
                                                }
                                            }
                                            this.addItemToBag(item);
                                            notice2 += ("\n+ " + item.template.name + expireStr);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.logException("Lỗi npcAdmin(4)  : ", Char.class, e);
                            serverDialog("Có lỗi xảy ra, hãy thử lại sau (lỗi không tìm được item)");
                            return;
                        }
                    }
                    // yen
                    if (dataGift.containsKey("yen")
                            && NinjaUtils.isNumericLong(dataGift.get("yen").toString())) {
                        long value = Long.parseLong(dataGift.get("yen").toString());
                        if (value > 0) {
                            addYen(value);
                            isOk = true;
                            notice += "\n+ " + NinjaUtils.getCurrency(value) + " Yên";
                        }
                    }
                    // luong
                    if (dataGift.containsKey("luong")
                            && NinjaUtils.isNumericLong(dataGift.get("luong").toString())) {
                        long value = Long.parseLong(dataGift.get("luong").toString());
                        if (value > 0) {
                            addGold((int) value);
                            isOk = true;
                            notice += "\n+ " + NinjaUtils.getCurrency(value) + " Lượng";
                        }
                    }
                    if (isOk) {
//                        Connection conn = DbManager.getInstance().getConnection(DbManager.SAVE_DATA);
                        Connection conn = DbManager.getConnection();
                        PreparedStatement stmt2 = conn.prepareStatement("UPDATE `users` SET `gift_new_bie` = ? WHERE `id` = ? LIMIT 1;");

                        try {
                            stmt2.setInt(1, 2);
                            stmt2.setInt(2, this.user.id);
                            stmt2.executeUpdate();
                        } finally {
                            stmt2.close();
                        }
                        PreparedStatement stmt3 = conn.prepareStatement("UPDATE `players` SET `gift` = ? WHERE `id` = ? LIMIT 1;");
                        try {
                            stmt3.setString(1, "");
                            stmt3.setInt(2, this.id);
                            stmt3.executeUpdate();
                        } finally {
                            stmt3.close();
                        }
                        this.user.service.showAlert("Phần quà", notice + notice2);
                    } else {
                        serverDialog("Có lỗi xảy ra, hãy thử lại sau (lỗi không tìm thấy)");
                    }
                } else {
                    serverDialog("Con chưa mua gói tân thủ");
                }
            } else {
                serverDialog("Con chưa mua gói tân thủ");
            }
        } catch (Exception ee) {
            Log.logException("Lỗi npcAdmin(5)  : ", Char.class, ee);
        }
    }

    // fix đổi tên nv

    public void changeCharname(Item item) {
        InputDialog input = new InputDialog(CMDInputDialog.EXECUTE, "Tên nv mới", () -> {
            try {
                String newUsername = this.input.getText();
                if (newUsername.equals("") || newUsername.isEmpty()) {
                    serverDialog("Tên nv không được để trống.");
                    return;
                }
                Pattern p = Pattern.compile("^[a-z0-9]+$");
                Matcher m1 = p.matcher(newUsername);
                if (!m1.find()) {
                    serverDialog("Tên nhân vật không được chứa ký tự đặc biệt!");
                    return;
                }
                if (newUsername.length() < 5 || newUsername.length() > 15) {
                    service.serverDialog("Tên tài khoản chỉ cho phép từ 5 đến 15 ký tự!");
                    return;
                }
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.CONFIRM, String.format("Bạn có chắc chắn muốn đổi tên nv thành %s không?", newUsername), () -> {
                    try {


                        ResultSet data = DbManager.executeQueryType(SQLStatement.CHECK_PLAYER_NAME, newUsername);
                        if (data.first()) {
                            serverDialog("Tên nv đã tồn tại, vui lòng chọn một tên khác.");
                            return;
                        }
                        data.close();
                        if (this.clan != null) {
                            int typeClan = clan.getMemberByName(this.name).getType();
                            Member mem = clan.getMemberByName(this.name);
                            if (typeClan == Clan.TYPE_TOCTRUONG) {
                                DbManager.executeUpdate(SQLStatement.UPDATE_CLAN_MAIN_PLAYER_NAME, newUsername, this.clan.id);
                                this.clan.main_name = newUsername;
                            }
                            if (typeClan == Clan.TYPE_TOCPHO) {
                                DbManager.executeUpdate(SQLStatement.UPDATE_CLAN_ASS_PLAYER_NAME, newUsername, this.clan.id);

                                this.clan.assist_name = newUsername;
                            }
                            mem.setName(newUsername);
                        }
                        DbManager.executeUpdate(SQLStatement.UPDATE_PLAYER_NAME, newUsername, this.id);


                        this.name = newUsername;
                        service.loadAll();


                        removeItem(item.index, 1, true);
                        serverDialog(String.format("Thay đổi thành công, tên nv mới của bạn là: %s.", newUsername));
                    } catch (Exception e) {
                        Log.logException("Lỗi đổi tên nv   : ", Char.class, e);

                        serverDialog(String.format("Thay đổi thất bại.", newUsername));
                    }
                }));
                getService().openUIConfirmID();
            } catch (Exception e) {
                getService().serverDialog(e.getMessage());
            }
        });
        setInput(input);
        getService().showInputDialog();
    }

    public int checkNewBieGift() {
        try (Connection conn = DbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT `gift_new_bie` FROM `users` WHERE `id` = ? LIMIT 1;");
            stmt.setInt(1, this.user.id);
            ResultSet result = stmt.executeQuery();
            int total = -1;
            if (result.next()) {
                total = result.getInt("gift_new_bie");
            }
            result.close();
            stmt.close();
            return total;
        } catch (SQLException e) {
            Log.logException("Lỗi checkNewBieGift: ", Char.class, e);

        }
        return -1;
    }

    public JSONObject getDataNewBieGift() {
        try (Connection conn = DbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT `gift` FROM `players` WHERE `id` = ? LIMIT 1;");
            stmt.setInt(1, this.id);
            ResultSet result = stmt.executeQuery();
            JSONObject res = null;
            String data = null;
            if (result.next()) {
                data = result.getString("gift");
                try {
                    res = (JSONObject) JSONValue.parse(data);
                } catch (Exception e) {
                }
            }
            result.close();
            stmt.close();
            if (res != null) {
                return res;
            }
        } catch (SQLException e) {
            Log.logException("Lỗi getDataNewBieGift: ", Char.class, e);
        }
        return null;
    }

    public void giftRankSilver(Char p) {
        try {

            if (!user.rankSilver) {
                service.serverDialog("Bạn chưa đủ điều kiện đạt mốc");
            } else if (user.SilverRank == 0) {
                if (this.getSlotNull() < 57) {
                    this.getService().serverDialog("Bạn cần để hành trang trống tối thiểu 57 ô");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Bạn nhận được :\n");
                addYen(200000000);
                sb.append("200tr yên\n");
                Item item = ItemFactory.getInstance().newItem(ItemName.BAT_BAO);
                checkUPTOUP(item, 5, false, -1);
                sb.append(String.format("x%d %s\n", 5, item.template.name));

                Item item1 = ItemFactory.getInstance().newItem(ItemName.LINH_CHI_NGAN_NAM);
                checkUPTOUP(item1, 50, false, -1);
                sb.append(String.format("x%d %s\n", 50, item1.template.name));

                Item item2 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                checkUPTOUP(item2, 1, false, -1);
                sb.append(String.format("x%d %s\n", 1, item2.template.name));

                this.service.showAlert("server", sb.toString());
                user.SilverRank = 1;
                DbManager.executeUpdate("UPDATE `users` SET `silver_rank`=? WHERE `id`=?", user.SilverRank, user.id);
                this.saveData();
            } else {
                service.serverDialog("Bạn đã nhận quà rank bạc rồi");
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận quà rank bạc: ", Char.class, e);
        }
    }

    public void giftRankGold(Char p) {
        try {

            if (!user.rankGold) {
                service.serverDialog("Bạn chưa đủ điều kiện nhận hạng vàng");
            } else if (user.GoldRank == 0) {
                if (this.getSlotNull() < 27) {
                    this.getService().serverDialog("Bạn cần để hành trang trống tối thiểu 27 ô");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Bạn nhận được :\n");
                addYen(500000000);
                sb.append("500tr yên\n");

                Item item = ItemFactory.getInstance().newItem(ItemName.HAKAIRO_YOROI);
                checkUPTOUP(item, 1, false, 90);
                sb.append(String.format("x%d %s\n", 1, item.template.name));

                Item item1 = ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN);
                checkUPTOUP(item1, 5, false, -1);
                sb.append(String.format("x%d %s\n", 5, item1.template.name));

                Item item2 = ItemFactory.getInstance().newItem(ItemName.LINH_CHI_VAN_NAM);
                checkUPTOUP(item2, 20, false, -1);
                sb.append(String.format("x%d %s\n", 20, item2.template.name));

                Item item3 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                checkUPTOUP(item3, 1, false, -1);
                sb.append(String.format("x%d %s\n", 1, item3.template.name));

                this.service.showAlert("server", sb.toString());
                user.GoldRank = 1;
                DbManager.executeUpdate("UPDATE `users` SET `gold_rank`=? WHERE `id`=?", user.GoldRank, user.id);
                this.saveData();
            } else {
                service.serverDialog("Bạn đã nhận quà rank vàng rồi");
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận quà rank vàng: ", Char.class, e);

        }
    }

    public void giftRankDiamond(Char p) {
        try {


            if (!user.rankDiamond) {
                service.serverDialog("Bạn chưa đ điều kiện nhận hạng kim cương");
            } else if (user.DiamondRank == 0) {
                if (this.getSlotNull() < 56) {
                    this.getService().serverDialog("Bạn cần để hành trang trống tối thiểu 56 ô");
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Bạn nhận được :\n");

                addYen(1000000000);
                sb.append("1 tỷ yên\n");

                Item item = ItemFactory.getInstance().newItem(ItemName.TON_HANH_GIA);
                checkUPTOUP(item, 1, false, -1);
                sb.append(String.format("x%d %s\n", 1, item.template.name));

                Item item1 = ItemFactory.getInstance().newItem(ItemName.HAKAIRO_YOROI);
                checkUPTOUP(item1, 1, false, 180);
                sb.append(String.format("x%d %s\n", 1, item1.template.name));

                Item item2 = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                checkUPTOUP(item2, 1, false, -1);
                sb.append(String.format("x%d %s\n", 1, item2.template.name));

                Item item3 = ItemFactory.getInstance().newItem(ItemName.LENH_BAI_HOAN_THANH);
                checkUPTOUP(item3, 3, false, -1);
                sb.append(String.format("x%d %s\n", 3, item3.template.name));

                Item item4 = ItemFactory.getInstance().newItem(ItemName.LINH_CHI_VAN_NAM);
                checkUPTOUP(item4, 50, false, -1);
                sb.append(String.format("x%d %s\n", 50, item4.template.name));
                this.service.showAlert("server", sb.toString());
                user.DiamondRank = 1;
                DbManager.executeUpdate("UPDATE `users` SET `diamond_rank`=? WHERE `id`=?", user.DiamondRank, user.id);
                this.saveData();
            } else {
                service.serverDialog("Bạn đã nhận quà rank kim cương rồi");
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận quà rank kim cương: ", Char.class, e);

        }
    }

    // fix gộp item phát quà
    public void checkUPTOUP(Item item, int slItem, boolean islock, int expire) {
        int n = slItem; // số lượng item
        int n2 = slItem; // số lượng item
        if (item.template.isUpToUp) {
            n = 1;
        }
        for (int i = 0; i < n; i++) {
            Item newItem = newItem = ItemFactory.getInstance().newItem(item.id);
            if (item.template.isUpToUp) {
                newItem.setQuantity(n2);
            } else {
                newItem.setQuantity(1);
            }
            newItem.isLock = islock;
            newItem.expire = ConstTime.Expire(expire);
            addItemToBag(newItem);

        }
    }



    public void removeAllItemTask() {
        for (int i = 0; i < this.numberCellBag; i++) {
            if (this.bag[i] != null && (this.bag[i].template.type == 24 || bag[i].template.type == 25)) {
                removeItem(i, this.bag[i].getQuantity(), true);
            }
        }
    }

    public void removeItembag() {
        for (int i = 0; i < this.numberCellBag; i++) {
            if (this.bag[i] != null) {
                removeItem(i, this.bag[i].getQuantity(), true);
            }
        }
    }

    public void menu(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            typeVXMM = -1;

            byte typeClose = 0;

            if (isVersionAbove(200)) {
                typeClose = ms.reader().readByte();
            }

            byte npcTemplateId = ms.reader().readByte();
            byte menuId = ms.reader().readByte();
            byte optionId = ms.reader().readByte();

            try {
                if (!isHuman) {
                    if (npcTemplateId != 7 && npcTemplateId != 8 && npcTemplateId != 9
                            && npcTemplateId != 10 && npcTemplateId != 11 && npcTemplateId != 12
                            && npcTemplateId != 6 /// cho pt luyện ngọc
                            && npcTemplateId != 13 && npcTemplateId != 5 && npcTemplateId != 39) {
                        getService().endWait("Bạn đang trong chế độ thứ thân không thể dùng được chức năng này.");
                        return;
                    }
                }
                if (npcTemplateId != 0) {
                    Npc npc = zone.getNpc(npcTemplateId);
                    if (npc == null) {
                        serverDialog("Không tìm thấy NPC này");
                        return;
                    }
                } else {
                    if (menus.isEmpty()) {
                        return;
                    }
                }
                if (menus.isEmpty()) {
                    if (npcTemplateId == NpcName.SHINWA) {
                        if (menuId == 0 && optionId >= 0 && optionId <= 11) {
                            Stall stall = StallManager.getInstance().findByID(optionId);
                            if (stall != null) {
                                stall.show((Char) this);
                                return;
                            }
                        }
                    } else if (npcTemplateId == NpcName.KAMAKURA) {
                        if (menuId == 2 && optionId == 0) {
                            teleportVDMQ();
                            return;
                        }

                    } else if (npcTemplateId == NpcName.DIA_NGUC) { // lang dia nguc
                        if (menuId == 2 && optionId == 0) {
                            teleportLangDiaNguc();
                            return;
                        }


                    } else if (npcTemplateId == NpcName.RIKUDOU) {
                        if (menuId == 1) {
                            if (optionId == 0) {
                                orderTaskDay();
                                return;
                            }
                            if (optionId == 1) {
                                cancelTaskDay();
                                return;
                            }
                            if (optionId == 2) {
                                finishTaskDay();
                                return;
                            }
                            if (optionId == 3) {
                                workTaskDay();
                                return;
                            }
                        } else if (menuId == 2) {
                            if (optionId == 0) {
                                orderTaskBoss();
                                return;
                            }
                            if (optionId == 1) {
                                cancelTaskBoss();
                                return;
                            }
                            if (optionId == 2) {
                                finishTaskBoss();
                                return;
                            }
                        }
                    } else if (npcTemplateId == NpcName.TAJIMA) {
                        if (menuId == 3 && optionId == 0) {
                            if (isHuman && clone != null && clone.isNhanBan && !clone.isDead && timeCountDown > 0) {
                                clone.switchToMe();
                            }
                        }
                    }
                }

                Menu menuNew = null;
                if (menus.isEmpty()) {
                    initMenu(npcTemplateId);
                }
                if (!menus.isEmpty() && menuId < menus.size()) {
                    menuNew = menus.get(menuId);
                    menus.clear();
                }
                if (menuNew != null) {
                    menu(menuNew, npcTemplateId);
                }
            } catch (Exception e) {
                Log.logException("Open menu err (2): ", Char.class, e);
            }
        } catch (Exception ex) {
            //  Log.logException("err: " + ex.getMessage(), ex);
            Log.logException("Lỗi open menu :", Char.class, ex);
        }
    }

    public void showUpgradeSoulStone(int itemID) {
        if (getSlotNull() == 0) {
            warningBagFull();
            return;
        }
        stoneItemId = itemID;
        if (stoneItemId < 880 || stoneItemId > 889) {
            return;
        }
        ItemTemplate itemTemplate = ItemManager.getInstance().getItemTemplate(stoneItemId);
        setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.UPGRADE_SOUL_STONE, "Bạn có muốn ghép 10 " + itemTemplate.name + " + 1 trượng linh hồn + 5 bụi linh hồn với tỉ lệ thành công là 50% không?"));
        getService().openUIConfirmID();
    }

    public void takingTask() {
        if (taskMain == null) {
            TaskTemplate template = Task.getTaskTemplate(taskId);
            if (template == null) {
                return;
            }
            short count = 0;
            short[] counts = template.getCounts();
            if (counts[0] < 0) {
                count = counts[0];
            }
            taskMain = TaskFactory.getInstance().createTask(taskId, (byte) 0, count);

            if (taskMain.taskId == TaskName.NV_TRUYEN_TAI_TIN_TUC) {
                Item thu = ItemFactory.getInstance().newItem(ItemName.THU_LIEN_LAC);
                thu.isLock = true;
                thu.setQuantity(3);
                addItemToBag(thu);
            } else if (taskMain.taskId == TaskName.NV_LAY_NUOC_HANG_SAU) {
                Item binhnuoc = ItemFactory.getInstance().newItem(ItemName.BINH_RONG);
                binhnuoc.isLock = true;
                binhnuoc.setQuantity(50);
                addItemToBag(binhnuoc);
            } else if (taskMain.taskId == TaskName.NV_TRUY_TIM_DIA_DO) {
                Item chiaKhoa = ItemFactory.getInstance().newItem(ItemName.CHIA_KHOA_CO_QUAN2);
                chiaKhoa.isLock = true;
                chiaKhoa.setQuantity(1);
                addItemToBag(chiaKhoa);
            } else if (taskMain.taskId == TaskName.NV_TRUY_TIM_BAO_VAT) {
                Item diaDo = ItemFactory.getInstance().newItem((Integer) NinjaUtils.randomObject(ItemName.DIA_DO2, ItemName.DIA_DO3, ItemName.DIA_DO4));
                diaDo.isLock = true;
                diaDo.setQuantity(1);
                addItemToBag(diaDo);
            } else if (taskMain.taskId == TaskName.NV_THU_THAP_XAC_DOI_LUA) {
                Item tinhTheBang = ItemFactory.getInstance().newItem(ItemName.TINH_THE_BANG2);
                tinhTheBang.isLock = true;
                tinhTheBang.setQuantity(100);
                addItemToBag(tinhTheBang);
            } else if (taskMain.taskId == TaskName.NV_REN_LUYEN_Y_CHI) {
                Item canCau = ItemFactory.getInstance().newItem(ItemName.CAN_CAU);
                canCau.isLock = true;
                canCau.setQuantity(1);
                addItemToBag(canCau);
            } else if (taskMain.taskId == TaskName.NV_GIUP_DO_DAN_LANG) {
                Item binhnuoc = ItemFactory.getInstance().newItem(ItemName.BINH_RONG);
                binhnuoc.isLock = true;
                binhnuoc.setQuantity(150);
                addItemToBag(binhnuoc);
            }
            getService().sendTaskInfo();
            if (taskId == TaskName.NV_LAN_DAU_DUNG_KIEM && equipment != null && equipment[1] != null) {
                taskNext();
            }
            updateTaskLevelUp();
        }
    }

    private void menu(Menu menu, int npc) {
        switch (menu.getId()) {

            case CMDMenu.EXECUTE:
                menu.confirm();
                break;

            case CMDMenu.VXMM_1:
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Thông tin", () -> {
                    typeVXMM = LuckyDrawManager.VIP;
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.VIP);
                    lucky.show((Char) this);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.VIP);
                    InputDialog input = new InputDialog(CMDInputDialog.VXMMVIP, lucky.getName());
                    setInput(input);
                    getService().showInputDialog();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Luật chơi", () -> {
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.VIP);
                    getService().showAlert(lucky.getName(), String.format(language.getString("LAW"), NinjaUtils.getCurrency(lucky.getXuWin()), NinjaUtils.getCurrency(lucky.getXuMax())));
                }));
                getService().openUIMenu();
                break;

            case CMDMenu.VXMM_2:
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Thông tin", () -> {
                    typeVXMM = LuckyDrawManager.NORMAL;
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.NORMAL);
                    lucky.show((Char) this);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.NORMAL);
                    InputDialog input = new InputDialog(CMDInputDialog.VXMMNORMAL, lucky.getName());
                    setInput(input);
                    getService().showInputDialog();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Luật chơi", () -> {
                    LuckyDraw lucky = LuckyDrawManager.getInstance().find(LuckyDrawManager.NORMAL);
                    getService().showAlert(lucky.getName(), String.format(language.getString("LAW"), NinjaUtils.getCurrency(lucky.getXuWin()), NinjaUtils.getCurrency(lucky.getXuMax())));
                }));
                getService().openUIMenu();
                break;

            case CMDMenu.HOAN_THANH_NHIEM_VU:
                finishTask(false);
                break;

            case CMDMenu.NHAN_NHIEM_VU:
                menus.clear();
                menus.add(new Menu(CMDMenu.XAC_NHAN_NHAN_NHIEM_VU, "Nhận"));
                menus.add(new Menu(CMDMenu.CANCEL, "Hủy"));
                getService().openUIConfirm(npc, "Bạn có muốn nhận nhiệm vụ này.");
                break;

            case CMDMenu.XAC_NHAN_NHAN_NHIEM_VU:
                takingTask();
                break;

            case CMDMenu.LAM_NHIEM_VU:
                if (taskMain != null) {
                    if (npc == 14 || npc == 15 || npc == 16) {
                        taskNext();
                        int index = getIndexItemByIdInBag(214);
                        if (index != -1) {
                            this.removeItem(index, 1, true);
                        }
                    } else if (taskId == TaskName.NV_KIEN_THUC || taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                        questionAndAnswer();
                    }
                    break;
                }

            case CMDMenu.A:
                if (taskId == TaskName.NV_KIEN_THUC) {
                    if (nAnswer == 0 || nAnswer == 5) {
                        nAnswer++;
                    }
                }
                if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                    if (taskMain.index == 1) {
                        if (nAnswer == 1) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 6) {
                        if (nAnswer == 1) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 11) {
                        if (nAnswer == 0) {
                            nAnswer++;
                        }
                    }
                }
                break;

            case CMDMenu.B:
                if (taskId == TaskName.NV_KIEN_THUC) {
                    if (nAnswer == 2) {
                        nAnswer++;
                    }
                }
                if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                    if (taskMain.index == 1) {
                        if (nAnswer == 0 || nAnswer == 2 || nAnswer == 3) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 6) {
                        if (nAnswer == 4) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 11) {
                        if (nAnswer == 1 || nAnswer == 4) {
                            nAnswer++;
                        }
                    }
                }
                break;

            case CMDMenu.C:
                if (taskId == TaskName.NV_KIEN_THUC) {
                    if (nAnswer == 1 || nAnswer == 6) {
                        nAnswer++;
                    }
                }
                if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                    if (taskMain.index == 1) {
                        if (nAnswer == 4) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 6) {
                        if (nAnswer == 0 || nAnswer == 2 || nAnswer == 3) {
                            nAnswer++;
                        }
                    } else if (taskMain.index == 11) {
                        if (nAnswer == 2 || nAnswer == 3) {
                            nAnswer++;
                        }
                    }
                }
                break;

            case CMDMenu.D:
                if (taskId == TaskName.NV_KIEN_THUC) {
                    if (nAnswer == 3 || nAnswer == 4) {
                        nAnswer++;
                    }
                }
                if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                    if (taskMain.index == 1) {

                    } else if (taskMain.index == 6) {

                    } else if (taskMain.index == 11) {

                    }
                }
                break;

            case CMDMenu.TRIEU_HOI_THAN_THU:
                if (clan != null) {
                    int type = menu.getIntExtra();
                    ThanThu thanThu = clan.getThanThu(type);
                    if (thanThu != null) {
                        if (thanThu.getEggHatchingTime() == -1) {
                            int indexItem = getIndexItemByIdInBag(ItemName.TRIEU_HOI_THU_THAN);
                            if (indexItem != -1) {
                                Item item = ItemFactory.getInstance().newItem(thanThu.getItemID());
                                item.setQuantity(1);
                                item.isLock = true;
                                for (ItemOption o : thanThu.getOptions()) {
                                    item.options.add(new ItemOption(o.optionTemplate.id, o.param));
                                }
                                item.isLock = true;
                                item.expire = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000);
                                removeItem(indexItem, 1, true);
                                addItemToBag(item);
                            } else {
                                getService().serverDialog("Kho đã hết Triệu Hồi Thú Thần");
                            }
                        } else {
                            getService().serverDialog("Trứng thần thú đang trong quá trình ấp");
                        }
                    }
                }

                break;

            case CMDMenu.UPGRADE_SOUL_STONE:
                showUpgradeSoulStone(menu.getIntExtra());
                break;

        }
        if (taskMain != null) {
            if (taskId == TaskName.NV_KIEN_THUC && taskMain.index == 0) {
                if (nAnswer < 5) {
                    questionAndAnswer();
                } else {
                    taskNext();
                    taskNext();
                    taskNext();
                    taskNext();
                    taskNext();
                    nAnswer = 0;
                    String talk = "Haha, tốt hãy quay về tìm trưởng làng, ta đã gửi ông ấy 1 món quà đặc biệt cho con.";
                    getService().npcChat(NpcName.KAMAKURA, talk);
                }
            }
            if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG && (taskMain.index == 1 || taskMain.index == 6 || taskMain.index == 11)) {
                if (nAnswer < 5) {
                    questionAndAnswer();
                } else {
                    taskNext();
                    taskNext();
                    taskNext();
                    taskNext();
                    taskNext();
                    nAnswer = 0;
                }
            }
        }
    }

    public void finishTask(boolean quick) {
        if (taskMain != null && taskMain.index == taskMain.template.getSubNames().length - 1 || quick) {
            int num = this.getSlotNull();
            switch (taskId) {
                case TaskName.NHIEM_VU_CHAO_LANG:
                    addExp(200);
                    addYen(10000);
                    break;

                case TaskName.NV_KIEN_THUC:
                    if (num < 1) {
                        warningBagFull();
                        return;
                    }
                    addExp(400);
                    addYen(100);
                    receiveWoodenSword();
                    Skill skillNew = GameData.getInstance().getSkill(SkillName.CHIEU_CO_BAN, 0, 0);
                    vSkill.add(skillNew);
                    if (skillNew.template.type == Skill.SKILL_AUTO_USE) {
                        vSupportSkill.add(skillNew);
                    } else if ((skillNew.template.type == Skill.SKILL_CLICK_USE_ATTACK || skillNew.template.type == Skill.SKILL_CLICK_LIVE || skillNew.template.type == Skill.SKILL_CLICK_USE_BUFF || skillNew.template.type == Skill.SKILL_CLICK_NPC) && (skillNew.template.maxPoint == 0 || (skillNew.template.maxPoint > 0 && skillNew.point > 0))) {

                        vSkillFight.add(skillNew);
                    }
                    selectSkill((short) 0);
                    getService().loadClass();
                    getService().loadSkill();
                    break;

                case TaskName.NV_LAN_DAU_DUNG_KIEM:
                    addExp(800);
                    addYen(200);
                    break;

                case TaskName.NV_DIET_SEN_TRU_COC:
                    addExp(1600);
                    addYen(300);
                    break;

                case TaskName.NV_VAT_LIEU_TAO_GIAP:
                    if (num < 1) {
                        warningBagFull();
                        return;
                    }
                    int itemId = this.gender == 0 ? 198 : 197;
                    Item quan = ItemFactory.getInstance().newItem(itemId);
                    quan.sys = (byte) (NinjaUtils.nextInt(3) + 1);
                    quan.isLock = true;
                    quan.options.add(new ItemOption(47, 1));
                    quan.options.add(new ItemOption(6, 5));
                    addItemToBag(quan);
                    addExp(3200);
                    addYen(400);
                    break;

                case TaskName.NV_HAI_THUOC_CUU_NGUOI:
                    if (num < 2) {
                        warningBagFull();
                        return;
                    }
                    Item hp = ItemFactory.getInstance().newItem(13);
                    hp.isLock = true;
                    hp.setQuantity(10);
                    addItemToBag(hp);
                    Item mp = ItemFactory.getInstance().newItem(18);
                    mp.isLock = true;
                    mp.setQuantity(10);
                    addItemToBag(mp);
                    addExp(4000);
                    addYen(500);
                    break;

                case TaskName.NV_KHAM_PHA_XA_LANG:
                    addExp(6000);
                    addYen(600);
                    break;

                case TaskName.NV_BAI_HOC_VAO_TRUONG:
                    addExp(8000);
                    addYen(800);
                    break;

                case TaskName.NV_TIM_HIEU_3_TRUONG:
                    if (num < 2) {
                        warningBagFull();
                        return;
                    }
//                    for (int i = ItemName.NGOC_1_SAO; i <= ItemName.NGOC_7_SAO; i++) {
//                        Item ngoc = ItemFactory.getInstance().newItem(i);
//                        ngoc.isLock = true;
//                        addItemToBag(ngoc);
//                    }

                    Item vhkdl = ItemFactory.getInstance().newItem(37);
                    vhkdl.isLock = true;
                    vhkdl.expire = System.currentTimeMillis() + 604800000L;
                    addItemToBag(vhkdl);
                    addExp(12000);
                    addYen(1000);
                    break;

                case TaskName.NV_GIA_TANG_SUC_MANH:
                    addExp(16000);
                    addYen(5000);

                    break;

                case TaskName.NV_BAI_HOC_DAU_TIEN:
                    addExp(22000);
                    addYen(10000);
                    break;

                case TaskName.NV_BAN_HUU_TAM_GIAO:
                    addExp(30000);
                    addYen(20000);
                    break;

                case TaskName.NV_NANG_CAP_TRANG_BI:
                    addExp(40000);
                    addYen(30000);

                    break;

                case TaskName.NV_THACH_DAU:
                    addExp(60000);
                    addYen(30000);
                    break;

                case TaskName.NV_THU_THAP_NGUYEN_LIEU:
                    addExp(80000);
                    addYen(40000);
                    break;

                case TaskName.NV_TRUYEN_TAI_TIN_TUC:
                    addExp(100000);
                    addYen(40000);
                    break;

                case TaskName.NV_REN_LUYEN_THE_LUC:
                    addExp(140000);
                    addYen(50000);
                    break;
                case TaskName.NV_DUA_JAIAN_TRO_VE:
                    addExp(200000);
                    addYen(60000);
                    break;
                case TaskName.NV_TIM_NGUYEN_LIEU_LAM_THUOC:
                    addExp(250000);
                    addYen(70000);
                    break;
                case TaskName.NV_LAY_NUOC_HANG_SAU:
                    addExp(300000);
                    addYen(80000);

                    break;
                case TaskName.NV_TIM_LAI_CAY_RIU:
                    addExp(360000);
                    addYen(90000);
                    break;
                case TaskName.NV_VUOT_QUA_THU_THACH:
                    addExp(400000);
                    addYen(100000);
                    break;
                case TaskName.NV_THU_THAP_CHIA_KHOA:
                    addExp(500000);
                    addYen(110000);
                    break;
                case TaskName.NV_TRUY_TIM_DIA_DO:
                    addExp(600000);
                    addYen(120000);
                    break;
                case TaskName.NV_TRUY_TIM_BAO_VAT:
                    addExp(700000);
                    addYen(130000);
                    break;
                case TaskName.NV_REN_LUYEN:
                    addExp(800000);
                    addYen(150000);
                    break;
                case TaskName.NV_THU_THAP_TINH_THE_BANG:
                    addExp(900000);
                    addYen(160000);
                    break;
                case TaskName.NV_THU_THAP_XAC_DOI_LUA:
                    addExp(1000000);
                    addYen(170000);
                    break;
                case TaskName.NV_KIEN_TRI_DIET_AC:
                    addExp(1100000);
                    addYen(180000);
                    break;
                case TaskName.NV_GIET_TINH_ANH:
                    addExp(1200000);
                    addYen(190000);
                    break;
                case TaskName.NV_TUAN_HOAN:
                    addExp(1300000);
                    addYen(200000);
                    break;
                case TaskName.NV_DU_TRU_LUONG_THUC:
                    addExp(1400000);
                    addYen(210000);
                    break;
                case TaskName.NV_REN_LUYEN_Y_CHI:
                    addExp(1500000);
                    addYen(220000);

                    break;
                case TaskName.NV_DIET_MA:
                    addExp(1600000);
                    addYen(230000);
                    break;
                case TaskName.NV_HAI_NAM:
                    addExp(1700000);
                    addYen(240000);

                    break;
                case TaskName.NV_GIUP_DO_DAN_LANG:
                    addExp(1800000);
                    addYen(250000);
                    break;
                case TaskName.NV_THU_THAP_OAN_HON:
                    addExp(1900000);
                    addYen(260000);
                    Item ngoc1 = ItemFactory.getInstance().newItem(ItemName.NGOC_1_SAO);
                    ngoc1.isLock = true;
                    addItemToBag(ngoc1);
                    break;
                case TaskName.NV_THU_THACH_CUA_GURIIN:
                    addExp(2000000);
                    addYen(270000);
                    Item ngoc2 = ItemFactory.getInstance().newItem(ItemName.NGOC_2_SAO);
                    ngoc2.isLock = true;
                    addItemToBag(ngoc2);
                    break;
                case TaskName.NV_THAP_SANG_BAN_LANG:
                    addExp(2100000);
                    addYen(280000);
                    Item ngoc3 = ItemFactory.getInstance().newItem(ItemName.NGOC_3_SAO);
                    ngoc3.isLock = true;
                    addItemToBag(ngoc3);
                    break;
                case TaskName.NV_HOAT_DONG_HANG_NGAY:
                    addExp(2200000);
                    addYen(290000);
                    Item ngoc4 = ItemFactory.getInstance().newItem(ItemName.NGOC_4_SAO);
                    ngoc4.isLock = true;
                    addItemToBag(ngoc4);
                    break;
                case TaskName.NV_THU_TAI_MAY_MAN:
                    addExp(2300000);
                    addYen(300000);
                    Item ngoc5 = ItemFactory.getInstance().newItem(ItemName.NGOC_5_SAO);
                    ngoc5.isLock = true;
                    addItemToBag(ngoc5);
                    break;
                case TaskName.NV_CHIEN_TRUONG:
                    addExp(2400000);
                    addYen(400000);
                    Item ngoc6 = ItemFactory.getInstance().newItem(ItemName.NGOC_6_SAO);
                    ngoc6.isLock = true;
                    addItemToBag(ngoc6);
                    break;

                case TaskName.NV_BAT_KHA_THI:
                    if (num < 1) {
                        warningBagFull();
                        return;
                    }
                    Item ngoc7 = ItemFactory.getInstance().newItem(ItemName.NGOC_7_SAO);
                    ngoc7.isLock = true;
                    addItemToBag(ngoc7);
                    addExp(10000000);
                    addYen(1000000);
                    break;

            }
            for (int i = 0; i < this.bag.length; i++) {
                if (this.bag[i] != null && ((int) this.bag[i].template.type == 25 || (int) this.bag[i].template.type == 23 || (int) this.bag[i].template.type == 24)) {
                    this.bag[i] = null;
                }
            }
            updateTask();

        }
    }

    public void receiveWoodenSword() {
        Item item = ItemFactory.getInstance().newItem(ItemName.KIEM_GO);
        item.isLock = true;
        item.options.add(new ItemOption(0, 10));
        item.options.add(new ItemOption(8, 1));
        item.yen = 0;
        addItemToBag(item);
    }

    public void upgradeSoulStone() {
        int index = getIndexItemByIdInBag(stoneItemId);
        int soulDustIndex = getIndexItemByIdInBag(893);
        int soulStickId = 0;
        if (stoneItemId <= 882) {
            soulStickId = 890;
        } else if (stoneItemId <= 885) {
            soulStickId = 891;
        } else {
            soulStickId = 892;
        }
        int soulStickIndex = getIndexItemByIdInBag(soulStickId);
        ItemTemplate itemTemplate = ItemManager.getInstance().getItemTemplate(stoneItemId);
        if (index == -1 || bag[index] == null || !bag[index].has(10)) {
            serverMessage("Bạn cần tối thiểu 10 " + itemTemplate.name + " để ghép lên cấp kế tiếp ");
            return;
        }
        if (soulDustIndex == -1 || bag[soulDustIndex] == null || !bag[soulDustIndex].has(10)) {
            serverMessage("Bạn cần tối thiểu 10 bụi linh hồn để có thể làm phép");
            return;
        }

        if (soulStickIndex == -1 || bag[soulStickIndex] == null || !bag[soulStickIndex].has()) {
            serverMessage("Bạn cần tối thiểu 1 gậy linh hồn để có thể làm phép");
            return;
        }
        removeItem(soulStickIndex, 1, true);
        removeItem(soulDustIndex, 5, true);
        if (NinjaUtils.nextInt(100) < 30) {
            serverMessage("Ghép phép thất bại, bạn bị tổn thất 1 gậy linh hồn và 10 bụi linh hồn");
            return;
        }
        removeItem(index, 10, true);
        Item item = ItemFactory.getInstance().newItem(stoneItemId + 1);
        addItemToBag(item);
        serverMessage("Ghép phép thành công, bạn đã nhận được " + item.template.name);
    }

    // xử lí nâng cấp ấn tộc
    public void upgradeAnToc() {
        if (fashion[13] == null) {
            serverMessage("Hãy sử dụng Ấn tộc để sử dụng chức năng này.");
            return;
        }
        if (fashion[13].upgrade >= 10) {
            serverMessage("Ấn tộc đã đạt cấp tối đa.");
            return;
        }
        int[] fee = {1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000};
        int[] costs2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] percents = {50, 35, 25, 20, 15, 9, 7, 5, 3};
        int[] items = {880, 881, 882, 883, 884, 885, 886, 887, 888};

        int index = fashion[13].upgrade - 1;
        int indexItem = getIndexItemByIdInBag(items[index], true);
        int indexGiayPhep = getIndexItemByIdInBag(ItemName.GIAY_PHEP_NANG_CAP);
        if (indexItem == -1) {
            indexItem = getIndexItemByIdInBag(items[index], false);
        }
        if (indexItem == -1) {
            ItemTemplate template = ItemManager.getInstance().getItemTemplate(items[index]);
            serverMessage("Yêu cầu phải có 10 viên " + template.name);
            return;
        }
        if (getSlotNull() == 0) {
            warningBagFull();
            return;
        }
        if (indexGiayPhep == -1 || this.bag[indexGiayPhep] == null || !this.bag[indexGiayPhep].has(costs2[index])) {
            serverMessage("Yêu cầu phải có " + costs2[index] + " giấy phép nâng cấp để có thể thăng cấp ấn tộc");
            return;
        }
        Item item = this.bag[indexItem];
        if (!item.has(10)) {
            serverMessage("Yêu cầu phải có 10 viên " + item.template.name);
            return;
        }
        if (this.user.gold < fee[index]) {
            serverMessage("Không đủ lượng để thăng cấp ấn tộc");
            return;
        }
        int percent = percents[index];
        int rand = NinjaUtils.nextInt(100);
        if (rand < percent) {
            getService().deleteItemBody(fashion[13].template.type);
            Item itm = ItemFactory.getInstance().newItem(fashion[13].id + 1);
            itm.isLock = true;
            addItemToBag(itm);
            fashion[13] = null;
        } else {
            serverMessage("Thăng cấp thất bại.");
        }
        this.user.addGold(-fee[index]);
        removeItem(indexItem, 10, true);
        removeItem(indexGiayPhep, costs2[index], true);
    }

    public void getTask(Message ms) {
        try {
            byte npcId = ms.reader().readByte();
            Npc npc = zone.getNpc(npcId);
            if (npc == null) {
                serverDialog("Không tìm thấy NPC này");
                return;
            }
            if (!isHuman) {
                if (npcId != 7 && npcId != 8 && npcId != 9 && npcId != 10 && npcId != 11 && npcId != 12 && npcId != 13 && npcId != 5 && npcId != 39) { 
                    getService().endWait("Phân thân không thể giao tiếp với NPC này.");
                    return;
                }
            }
            byte menuId = ms.reader().readByte();
            Menu menuNew = null;
            if (!menus.isEmpty() && menuId >= 0 && menuId < menus.size()) {
                menuNew = menus.get(menuId);
                menus.clear();
            }
            if (menuNew != null) {
                menu(menuNew, npcId);
            }
        } catch (Exception ex) {
            Log.logException("get Task err: ", Char.class, ex);
        }
    }

    public void questionAndAnswer() {
        try {
            String question = "";
            int npc = 4;
            menus.clear();
            if (taskId == TaskName.NV_KIEN_THUC) {

                switch (nAnswer) {
                    case 0:
                        question = "Trình độ tối đa có thể đạt được là bao nhiêu?";
//                        menus.add(new Menu(CMDMenu.A, "Cấp 150"));
                        menus.add(new Menu(CMDMenu.B, "Cấp 130")); // đúng
                        menus.add(new Menu(CMDMenu.A, "Cấp 150"));
                        menus.add(new Menu(CMDMenu.C, "Cấp 99"));
                        break;

                    case 1:
                        question = "Trình độ bao nhiêu mới có thể khám phá Vùng đất ma quỷ??";
                        menus.add(new Menu(CMDMenu.A, "Cấp 50"));
                        menus.add(new Menu(CMDMenu.C, "Cấp 60")); // đúng
                        menus.add(new Menu(CMDMenu.B, "Cấp 100"));
//                        menus.add(new Menu(CMDMenu.C, "Cấp 60"));
                        break;

                    case 2:
                        question = "Thức ăn dùng để làm gì?";
                        menus.add(new Menu(CMDMenu.A, "Ăn cho ngon"));
                        menus.add(new Menu(CMDMenu.B, "Hồi phục thể lực")); // đúng
                        menus.add(new Menu(CMDMenu.C, "Để phí tiền"));
                        break;

                    case 3:
                        question = "Khám phá làng cổ cần những điều kiện gì?";
                        menus.add(new Menu(CMDMenu.A, "Cấp 70"));
                        menus.add(new Menu(CMDMenu.D, "Tất cả")); // đúng
                        menus.add(new Menu(CMDMenu.B, "Có cổ lệnh"));
                        menus.add(new Menu(CMDMenu.C, "Không có điểm hiếu chiến"));
//                        menus.add(new Menu(CMDMenu.D, "Tất cả"));
                        break;

                    case 4:
                        question = "Muốn tinh luyện trang bị cần phải chuẩn bị gì?";
                        menus.add(new Menu(CMDMenu.A, "Trang bị cấp 12"));
                        menus.add(new Menu(CMDMenu.D, "Tất cả")); // đúng
                        menus.add(new Menu(CMDMenu.B, "Tinh thạch"));
                        menus.add(new Menu(CMDMenu.C, "Xu hoặc yên"));
//                        menus.add(new Menu(CMDMenu.D, "Tất cả"));
                        break;

                    case 5:
                        question = "Hashimoto hiện giờ đang ở đâu?";
                        menus.add(new Menu(CMDMenu.B, "Nghĩa địa Izuko"));
                        menus.add(new Menu(CMDMenu.A, "Bờ biển Oura"));  // đúng
                        menus.add(new Menu(CMDMenu.C, "Hang Kugyou"));
                        break;

                    case 6:
                        question = "Nao đang cô độc ở đâu trong thế giới Ninja?";
                        menus.add(new Menu(CMDMenu.A, "Hang Ha"));
                        menus.add(new Menu(CMDMenu.C, "Mũi Nuranura")); // đúng
                        menus.add(new Menu(CMDMenu.B, "Rừng Toge"));
                        break;
                }
            } else if (taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                if (taskMain.index == 1) {
                    npc = 4;
                    switch (nAnswer) {
                        case 0:
                            question = "Con có thể trao đổi buôn bán những loại vật phẩm nào?";
                            menus.add(new Menu(CMDMenu.A, "Tiền yên và vật phẩm khoá"));
                            menus.add(new Menu(CMDMenu.B, "Tiền xu và vật phẩm không khoá"));
                            menus.add(new Menu(CMDMenu.C, "Tiền yên, xu và vật phẩm không khoá"));
                            break;

                        case 1:
                            question = "Có bao nhiều trường học quanh đây?";
                            menus.add(new Menu(CMDMenu.B, "2 trường"));
                            menus.add(new Menu(CMDMenu.A, "3 trường"));
                            menus.add(new Menu(CMDMenu.C, "4 trường"));
                            break;

                        case 2:
                            question = "Tổng cộng có bao nhiêu lớp học?";
                            menus.add(new Menu(CMDMenu.A, "3 lớp"));
                            menus.add(new Menu(CMDMenu.B, "6 lớp"));
                            menus.add(new Menu(CMDMenu.C, "12 lớp"));
                            break;

                        case 3:
                            question = "Nội công bao gồm những lớp nào?";
                            menus.add(new Menu(CMDMenu.A, "Kiếm, phi tiêu, quạt"));
                            menus.add(new Menu(CMDMenu.B, "Phi tiêu, quạt, cung"));
                            menus.add(new Menu(CMDMenu.C, "Kunai, cung, đao"));
                            break;

                        case 4:
                            question = "Ngoại công bao gồm những lớp nào?";
                            menus.add(new Menu(CMDMenu.A, "Phi tiêu, đao, cung"));
                            menus.add(new Menu(CMDMenu.C, "Kiếm, kunai, đao"));
                            menus.add(new Menu(CMDMenu.B, "Quạt, kiếm, kunai"));
                            break;
                    }
                } else if (taskMain.index == 6) {
                    npc = 5;
                    switch (nAnswer) {
                        case 0:
                            question = "Hiện tại con đang có bao nhiêu ô trong hành trang?";
                            menus.add(new Menu(CMDMenu.A, "10 ô"));
                            menus.add(new Menu(CMDMenu.B, "20 ô"));
                            menus.add(new Menu(CMDMenu.C, "30 ô"));
                            break;

                        case 1:
                            question = "Lưu toạ độ mặc định nhằm mục đích gì?";
//                            menus.add(new Menu(CMDMenu.A, "Là nơi về khi bị thương"));
                            menus.add(new Menu(CMDMenu.B, "Nơi xuất hiện khi đăng nhập"));
                            menus.add(new Menu(CMDMenu.C, "Nơi đứng của người giữ rương"));
                            menus.add(new Menu(CMDMenu.A, "Là nơi về khi bị thương"));

                            break;

                        case 2:
                            question = "Có mấy loại tiền tệ ở thế giới Ninja này?";
                            menus.add(new Menu(CMDMenu.A, "1 loại"));
                            menus.add(new Menu(CMDMenu.B, "2 loại"));
                            menus.add(new Menu(CMDMenu.C, "3 loại"));
                            break;

                        case 3:
                            question = "Tiền yên kiếm được bằng cách nào?";
                            menus.add(new Menu(CMDMenu.A, "Làm nhiệm vụ, tham gia hoạt động"));
                            menus.add(new Menu(CMDMenu.B, "Nhặt được khi đánh quái"));
                            menus.add(new Menu(CMDMenu.C, "Cả 2 trường hợp"));
                            break;

                        case 4:
                            question = "Loại tiền nào có thể chuyển đổi qua lại giữa các người chơi?";
                            menus.add(new Menu(CMDMenu.A, "Tiền yên"));
//                            menus.add(new Menu(CMDMenu.B, "Tiền xu"));
                            menus.add(new Menu(CMDMenu.C, "Tiền lượng"));
                            menus.add(new Menu(CMDMenu.B, "Tiền xu"));
                            break;
                    }
                } else if (taskMain.index == 11) {
                    npc = 6;
                    switch (nAnswer) {
                        case 0:
                            question = "Con có biết khi nâng cấp trang bị cần những gì không?";
                            menus.add(new Menu(CMDMenu.A, "Đá + yên hoặc xu"));
                            menus.add(new Menu(CMDMenu.B, "Đá"));
                            menus.add(new Menu(CMDMenu.C, "Yên hoặc xu"));
                            break;

                        case 1:
                            question = "Tách trang bị sau khi nâng cấp sẽ nhận được gì?";
                            menus.add(new Menu(CMDMenu.B, "50% Đá đã ép vào"));
                            menus.add(new Menu(CMDMenu.A, "Xu + 100% Đá khi ép vào"));
//                            menus.add(new Menu(CMDMenu.B, "50% Đá đã ép vào"));
                            menus.add(new Menu(CMDMenu.C, "Không được gì cả"));
                            break;

                        case 2:
                            question = "Trang bị được nâng cấp tối đa lên cấp mấy?";
                            menus.add(new Menu(CMDMenu.C, "Cấp 16"));
                            menus.add(new Menu(CMDMenu.A, "Cấp 12"));
                            menus.add(new Menu(CMDMenu.B, "Cấp 14"));
//                            menus.add(new Menu(CMDMenu.C, "Cấp 16"));
                            break;

                        case 3:
                            question = "Trang bị căn bản có mấy loại?";
                            menus.add(new Menu(CMDMenu.C, "3 loại"));
                            menus.add(new Menu(CMDMenu.A, "1 loại"));
                            menus.add(new Menu(CMDMenu.B, "2 loại"));
//                            menus.add(new Menu(CMDMenu.C, "3 loại"));
                            break;

                        case 4:
                            question = "Sau khi nâng cấp trang bị sẽ như thế nào";
                            menus.add(new Menu(CMDMenu.B, "Lên cấp và chỉ số cao hơn"));
                            menus.add(new Menu(CMDMenu.A, "Không thay đổi"));
//                            menus.add(new Menu(CMDMenu.B, "Lên cấp và chỉ số cao hơn"));
                            menus.add(new Menu(CMDMenu.C, "Trông đẹp hơn"));
                            break;
                    }
                }
            }
            getService().openUIConfirm(npc, question);
        } catch (Exception ex) {
            Log.logException("open question err: ", Char.class, ex);
        }
    }

    public int getIdItemTask(int mobId) {
        if (taskMain != null) {
            if (mobId == MobName.NHIM_DA) {
                if (taskId == TaskName.NV_VAT_LIEU_TAO_GIAP && taskMain.index == 1) {
                    return ItemName.LONG_NHIM;
                }
            } else if (mobId == MobName.THO_XAM) {
                if (taskId == TaskName.NV_VAT_LIEU_TAO_GIAP && taskMain.index == 2) {
                    return ItemName.DA_THO;
                }
            } else if (mobId == MobName.KHI) {
                if (taskId == TaskName.NV_TIM_NGUYEN_LIEU_LAM_THUOC && taskMain.index == 1) {
                    return ItemName.NHI_HAU_TUU;
                }
            } else if (mobId == MobName.CHAU_CHAU) {
                if (taskId == TaskName.NV_TIM_NGUYEN_LIEU_LAM_THUOC && taskMain.index == 2) {
                    return ItemName.XAC_CHAU_CHAU;
                }
            } else if (mobId == MobName.OC_DA) {
                if (taskId == TaskName.NV_THU_THAP_NGUYEN_LIEU && taskMain.index == 2) {
                    return ItemName.VO_OC_DA;
                }
            } else if (mobId == MobName.THAO_DUOC) {
                if (taskId == TaskName.NV_HAI_THUOC_CUU_NGUOI && taskMain.index == 1) {
                    return ItemName.BONG_THAO_DUOC;
                }
            } else if (mobId == MobName.XUONG_KHO) {
                if (taskId == TaskName.NV_THU_THAP_CHIA_KHOA && taskMain.index == 1) {
                    return ItemName.CHIA_KHOA_CO_QUAN;
                }
            } else if (mobId == MobName.MA_DEM) {
                if (taskId == TaskName.NV_THAP_SANG_BAN_LANG && taskMain.index == 1) {
                    return ItemName.LONG_DEN_MA;
                }
            } else if (mobId == MobName.BUOM_PHAN) {
                if (taskId == TaskName.NV_THAP_SANG_BAN_LANG && taskMain.index == 2) {
                    return ItemName.CANH_BUOM;
                }
            } else if (mobId == MobName.HEO_RUNG) {
                if (taskId == TaskName.NV_TIM_LAI_CAY_RIU && taskMain.index == 1) {
                    return ItemName.RIU_BAC;
                }
            } else if (mobId == MobName.DOI_LUA) {
                if (taskId == TaskName.NV_THU_THAP_XAC_DOI_LUA && taskMain.index == 1) {
                    return ItemName.XAC_DOI_LUA;
                }
            } else if (mobId == MobName.CA_HO) {
                if (taskId == TaskName.NV_DU_TRU_LUONG_THUC && taskMain.index == 1) {
                    return ItemName.GIO_CA_HO;
                }
            } else if (mobId == MobName.RAN_TIA) {
                if (taskId == TaskName.NV_DU_TRU_LUONG_THUC && taskMain.index == 2) {
                    return ItemName.GIO_RAN_TIA;
                }
            } else if (mobId == MobName.OAN_HON) {
                if (taskId == TaskName.NV_THU_THAP_OAN_HON && taskMain.index == 1) {
                    return ItemName.OAN_HON;
                }
            }
        }
        return -1;
    }

    public void updateTaskLevelUp() {
        if (taskMain != null && taskMain.index == 0 && taskMain.template != null && taskMain.template.getLeveRequire() > 0 && this.level >= taskMain.template.getLeveRequire()) {
            taskNext();
        }
    }

    public void updateTaskPickItem(Item item) {
        if (taskMain != null && taskMain.template != null) {
            short[] items = taskMain.template.getItems();
            if (items.length > 0) {
                if (items[taskMain.index] == item.id) {
                    if (taskId == TaskName.NV_TRUY_TIM_DIA_DO) {
                        ChikatoyaTunnels chikatoyaTunnels = (ChikatoyaTunnels) zone;
                        chikatoyaTunnels.setTimeMap(3);
                    }
                    updateTaskCount(!item.has() ? 1 : item.getQuantity());
//                    if (this.group != null) {
//
//                        List<Char> list = this.group.getCharsInZone(mapId, zone.id);
//                        for (Char _char : list) {
//                            if (_char != this && _char.taskMain != null && this.taskMain != null && _char.taskMain.taskId == this.taskMain.taskId && _char.taskMain.index == this.taskMain.index) {
//                                _char.updateTaskCount(!item.has() ? 1 : item.getQuantity());
//                                updateTaskCount(!item.has() ? 1 : item.getQuantity());
//                            }
//                        }
//
//                    } else {

//                    }

                }
            }
        }
    }

    public void updateTaskKillMonster(Mob mob) {
        if (taskMain != null && taskMain.template != null) {
            short[][] mobs = taskMain.template.getMobs();
            if (mobs.length > 0) {
                if ((mobs[taskMain.index][0] == mob.template.id) && (mobs[taskMain.index][1] == 0 || mobs[taskMain.index][1] == mob.levelBoss)) {
                    updateTaskCount(1);
                }
            }

        }
    }

    public void openUIBox() {
        setCommandBox(RUONG_DO);
        getService().openUIBox();
    }

    public void npcLangDiaNguc() { // lang dia nguc
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Địa Ngục", () -> {
            teleportLangDiaNguc();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Chỉ dẫn", () -> {
            getService().showAlert("Chỉ Dẫn", "- Con cần đạt tối thiểu là lv 29 đến lv 79 mới có thể vào Làng Địa ngục"
                    + "\n- Đây là nơi có nhiều ám khí nguy hiểm quỷ dữ luôn rình rập để ăn bạn"
                    + "\n- Quỷ Ngục sau làng là nơi chứa nhiều vật phẩm quý hiếm nhưng cũng đầy rẫy những nguy hiểm"
                    + "\n- Hãy tự đến nơi khám phá trực tiếp. Còn không đừng lắm lời:"
                    + "\n- CHÚC MAY MẮN . đi bình an đừng trở về bằng bộ xương khô:");
        }));
    }

    public void npcChoDen() {
        menus = new ArrayList<>();
        menus.add(new Menu(CMDMenu.EXECUTE, "Tu Tiên", () -> {
            maplss();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Huyền Thoại", () -> {
            maphuyenthoai();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Máp Vip", () -> {
            mapvip();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Shiba", () -> {
            maplv();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Vượt Ải", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Vượt Ải", () -> {
                openSoloTrialDungeon();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng Dẫn", () -> {
                StringBuilder guide = new StringBuilder();
                guide.append("Hướng Dẫn Thử Thách Vượt Ải:\n")
                        .append("- Phó bản gồm 4 ải, mỗi ải có số lượng boss và thời gian giới hạn khác nhau:\n")
                        .append("  + Ải 1: 10 boss, thời gian 5 phút.\n")
                        .append("  + Ải 2: 10 boss, thời gian 7 phút.\n")
                        .append("  + Ải 3: 5 boss, thời gian 7 phút.\n")
                        .append("  + Ải 4: 3 boss (bao gồm Đại Vương Vượt Ải), thời gian 10 phút.\n")
                        .append("- Phần thưởng:\n")
                        .append("  + Ải 1: 2 Đá Thiên Tự, 3,000,000 Xu, 500 Coin.\n")
                        .append("  + Ải 2: 5 Đá Thiên Tự, 7,000,000 Xu, 1,000 Coin.\n")
                        .append("  + Ải 3: 8 Đá Thiên Tự, 12,000,000 Xu, 2,500 Coin.\n")
                        .append("  + Ải 4: 15 Đá Thiên Tự, 20,000,000 Xu, 2,000 Coin, 1 Sách Võ Công 125 ngẫu nhiên (Kiếm, Tiêu, Kunai, Cung, Đao, Quạt) và 25% cơ hội nhận thêm 1 Sách Võ Công 125.\n")
                        .append("- Tiêu diệt hết boss trong thời gian quy định để chuyển ải. Nếu hết thời gian hoặc chết, bạn sẽ bị đẩy ra ngoài.\n")
                        .append("- Chỉ 1 người chơi được phép tham gia phó bản.\n")
                        .append("- Phó bản sẽ tự động đóng sau khi hoàn thành ải 4 hoặc khi hết thời gian.\n");
                service.showAlert("Hướng Dẫn Thử Thách Vượt Ải", guide.toString());
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Hướng Dẫn", () -> {
            getService().showAlert("Hướng dẫn",
                    "Làng Huyền Thoại Dành Cho các ninja từ 80-130 \n"
                            + "Map Shinba có trình độ từ 49-79\n"
                            + "Khi sở hữu VIP, VVIP, SVIP Nhận Xu lượng Nhiều HƠN Khi TRAIN Quái\n"
                            + "\n"
                            + "Nhiều Vật Phẩm Giá Trị Đang Chờ Đợi Ngươi");
        }));
        getService().openUIMenu();
    }

    private void maplss() {  // map tu tiên
        Calendar cal = Calendar.getInstance();
        int gio = cal.get(Calendar.HOUR_OF_DAY);
        int phut = cal.get(Calendar.MINUTE);
        if (this.level <= 39) {
            serverDialog("Trình độ cấp 40 mới có thể sử dụng chức năng này.");
            return;
        }
        if (!isHuman) {
            warningClone();
            return;
        }
        if (gio < 0 || (gio >= 23 && phut >= 59)) {
            serverDialog("Chức năng chỉ hoạt động từ 12:00 đến 16:00 mỗi ngày!");
            return;
        }
        mapBeforeEnterPB = mapId;
        setXY((short) 84, (short) 96);
        changeMap(178);
        setTypePk(PK_DOSAT);
        this.isInNoPkPenaltyMap = true;
        serverMessage("Bạn đã chuyển sang chế độ PK tự do (Đồ Sát).");
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.SECOND, 0);
        long remainingSeconds = (endTime.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }
        this.service.sendTimeInMap((int) remainingSeconds);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            try {
                Map map = MapManager.getInstance().find(170);
                if (map != null) {
                    for (Zone zone : map.getZones()) {
                        if (zone != null && !zone.isClosed()) {
                            for (Char player : zone.getChars()) {
                                if (player != null) {
                                    player.outZone();
                                    short[] xy = NinjaUtils.getXY(player.mapBeforeEnterPB);
                                    player.setXY(xy[0], xy[1]);
                                    player.setXY((short) 219, (short) 192);
                                    player.setTypePk(PK_NORMAL);
                                    this.isInNoPkPenaltyMap = false;
                                    player.changeMap(22);
                                    player.serverMessage("Thời gian hoạt động đã kết thúc, bạn đã được chuyển ra ngoài!");

                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Logger.getLogger(Npc.class
                        .getName()).log(Level.SEVERE, null, e);
            } finally {
                scheduler.shutdown();
            }
        }, remainingSeconds, TimeUnit.SECONDS);
    }

    private void maphuyenthoai() {
        if (this.level < 80) {
            serverDialog("Trình độ cấp 80 mới có thể sử dụng chức năng này.");
            return;
        }
        if (this.user.gold < 300) {
            serverDialog("Ngươi Phải Có Trên 3K Lượng Mới Có Thể Vào.");
            return;
        }
        if (this.user.gold >= 300) {
            addGold(-300);
            serverDialog("Vào Lestgooo.");
        } else {
            serverDialog("Ngươi Phải Có Trên 300 Lượng Mới Có Thể Vào.");
        }
        if (!isHuman) {
            warningClone();
            return;
        }
        if (this.level >= 131) {
            serverDialog("Trình độ dưới 130 mới có thể sử dụng chức năng này.");
            return;
        }
        setXY((short) 181, (short) 120);
        changeMap(172);
    }

    private void mapvip() {
        if (this.level < 39) {
            serverDialog("Trình độ cấp 39 mới có thể sử dụng chức năng này.");
            return;
        }
        if (!isHuman) {
            warningClone();
            return;
        }
        if (user.rankSilver) {
            serverDialog("Bạn Phải Là VVIP Trở Lên Mới Vào Được.");
            return;
        }
        setXY((short) 395, (short) 192);
        changeMap(173);
    }

    private void maplv() {
        if (this.level <= 39) {
            serverDialog("Trình độ cấp 40 mới có thể sử dụng chức năng này.");
            return;
        }
        if (this.user.gold < 300) {
            serverDialog("Ngươi Phải Có Trên 300 Lượng Mới Có Thể Vào.");
            return;
        }
        if (this.user.gold >= 300) {
            addGold(-300);
            serverDialog("Vào Lestgooo.");
        } else {
            serverDialog("Ngươi Phải Có Trên 300 Lượng Mới Có Thể Vào.");
        }
        if (!isHuman) {
            warningClone();
            return;
        }
        if (this.level >= 80) {
            serverDialog("Trình độ 49- 79 mới có thể sử dụng chức năng này.");
            return;
        }
        setXY((short) 554, (short) 408);
        changeMap(162);
    }

    private void openSoloTrialDungeon() {
        try {
            if (this.isDead) {
                serverDialog("Bạn đã chết, không thể tham gia Thử Thách Vượt Ải!");
                return;
            }
            if (this.damage < 300000) {
                serverDialog("Hãy Cố Cày Lên 300k Dame trở lên rồi vào nhé bé");
                return;
            }
            if (this.zone != null && this.zone.tilemap != null && this.zone.tilemap.isDungeo()) {
                serverDialog("Bạn đang ở trong một phó bản khác, hãy rời khỏi trước!");
                return;
            }
            if (this.group != null) {
                serverDialog("Hãy rời nhóm trước khi tham gia Thử Thách Vượt Ải!");
                return;
            }
            if (this.level < 40) {
                serverDialog("Cần đạt cấp 40 để tham gia Thử Thách Vượt Ải!");
                return;
            }

            // Kiểm tra lượt chơi hàng ngày
            LocalDateTime now = LocalDateTime.now();
            if (lastSoloTrialDungeonTime != null) {
                LocalDate lastDate = lastSoloTrialDungeonTime.toLocalDate();
                LocalDate currentDate = now.toLocalDate();
                if (lastDate.equals(currentDate)) {
                    // Đã tham gia trong ngày hôm nay
                    serverDialog("Bạn đã tham gia Thử Thách Vượt Ải hôm nay, hãy thử lại vào ngày mai!");
                    return;
                } else {
                    // Reset trạng thái khi sang ngày mới
                    hasCompletedSoloTrialToday = false;
                }
            }

            // Lưu vị trí hiện tại
            this.mapBeforeEnterPB = (this.zone != null && this.zone.tilemap != null) ? this.zone.tilemap.id : 1;
            setXY((short) 200, (short) 200); // Tọa độ mặc định khi vào map 175

            // Tạo và tham gia phó bản
            SoloTrialDungeon dungeon = new SoloTrialDungeon((Char) this);
            addWorld(dungeon);
            SoloTrialDungeon.addDungeon(dungeon);
            outZone();
            dungeon.joinZone((Char) this, SoloTrialDungeon.MAP_IDS[0]); // Gọi trực tiếp joinZone của SoloTrialDungeon
            serverMessage("Bạn đã bắt đầu Thử Thách Vượt Ải! Tiêu diệt các bot để nhận điểm PK.");

            // Cập nhật trạng thái tham gia ngay khi vào
            this.lastSoloTrialDungeonTime = now;
            this.hasCompletedSoloTrialToday = false; // Đặt lại trạng thái hoàn thành
            updateSoloTrialStateToDB(); // Cập nhật trạng thái vào DB
            Log.debug("Char " + this.name + " started SoloTrialDungeon at " + now);

            // Cộng vật phẩm sự kiện nếu có
            if (Event.isEvent()) {
                int itemID = Event.getEvent().randomItemID();
                if (itemID != -1) {
                    Item itemE = ItemFactory.getInstance().newItem(itemID);
                    itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                    addItemToBag(itemE);
                    serverMessage("Bạn nhận được vật phẩm sự kiện!");
                }
            }
        } catch (Exception e) {
            serverDialog("Không thể bắt đầu Thử Thách Vượt Ải. Vui lòng thử lại.");
            Log.error("Error starting SoloTrialDungeon for char " + this.name, e);
        }
    }

    public void updateSoloTrialStateToDB() {
        try (
                Connection conn = DbManager.getConnection();) {

            String query = "UPDATE players SET data = JSON_SET(data, '$.lastSoloTrialDungeonTime', ?, '$.hasCompletedSoloTrialToday', ?) WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            try {
                stmt.setString(1, this.lastSoloTrialDungeonTime != null ? this.lastSoloTrialDungeonTime.toString() : null);
                stmt.setBoolean(2, this.hasCompletedSoloTrialToday);
                stmt.setInt(3, this.id);
                stmt.executeUpdate();
            } finally {
                stmt.close();
            }
            Log.debug("Updated SoloTrial state for char " + this.name + ": lastSoloTrialDungeonTime=" + this.lastSoloTrialDungeonTime + ", hasCompletedSoloTrialToday=" + this.hasCompletedSoloTrialToday);
        } catch (Exception e) {
            Log.error("Error updating SoloTrial state for char " + this.name, e);
        }
    }












       
    public void npcKamakura() {

        menus.add(new Menu(CMDMenu.EXECUTE, "Mở rương", () -> {
            menus.clear();

            menus.add(new Menu(CMDMenu.EXECUTE, "Mở rương", () -> {
                openUIBox();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Mở giới hạn xu", () -> {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Xu hành trang", () -> {
                    int levelUpgrade = ((int) this.coinMax - 1400000000) / 100000000;
                    int fee = (levelUpgrade) * 200;
                    menus.clear();
                    menus.add(new Menu(CMDMenu.EXECUTE, String.format("Mở 100tr (%s lượng)", NinjaUtils.getCurrency(fee)),
                            () -> {
                                if (coinMax >= 3000000000L) {
                                    getService().npcChat(NpcName.KAMAKURA, "Đã đạt giới hạn cao nhất");
                                    return;
                                }
                                if (this.user.gold < fee) {
                                    getService().npcChat(NpcName.KAMAKURA, "Không đủ lượng");
                                    return;
                                }

                                addGold(-fee);
                                this.coinMax += 100000000;
                                getService().npcChat(NpcName.KAMAKURA, "Giới hạn xu của bạn đã được tăng lên "
                                        + NinjaUtils.getCurrency(this.coinMax) + " xu");
                            }));
                    getService().openUIMenu();
                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Mở bộ sưu tập", () -> {
                setCommandBox(BO_SUU_TAP);
                getService().openUICollectionBox();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cải trang", () -> {
                setCommandBox(CAI_TRANG);
                getService().openUIMaskBox();
            }));

            menus.add(new Menu(CMDMenu.EXECUTE, "Tháo cải trang", () -> {
                setMaskId(-1);
                this.mask = null;
                setAbility();
                setFashion();
                serverMessage("Tháo cải trang thành công.");
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Lưu tọa độ", () -> {
            if (!isHuman) {
                warningClone();
                return;
            }
            if ((isVillage() || isSchool()) && mapId != 138 && mapId != 162) {
                this.saveCoordinate = mapId;
                getService().npcChat(NpcName.KAMAKURA, "Lưu toạ độ thành công.");
            } else {
                if (this.mapId == 162) {
                    getService().npcChat(NpcName.KAMAKURA, "Ngôi làng này rất nguy hiểm ta không thể để con về đây khi bị thương.");
                }
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Vùng Đất Ma Quỷ", () -> {
            teleportVDMQ();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Gia hạn", () -> {
            setCommandBox(GIA_HAN);
            getService().openUIExtendBox();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 4) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Hãy an tâm giao đồ cho ta nào!", "Trên người của bạn toàn là những đồ có giá trị, Sao không cất bớt ở đây?", "Ta giữ đồ chưa hề để thất lạc bao giờ.");
            if (this.getCoin() > 2000000000) {
                talk = "Bạn đang có " + NinjaUtils.getCurrency(this.getCoin()) + " xu trong hành trang";
            }
            getService().npcChat(NpcName.KAMAKURA, talk);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Akatsuki", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Akatsuki\nHệ băng", () -> {
                createAkatsuki(2);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Akatsuki\nHệ hỏa", () -> {
                createAkatsuki(1);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Akatsuki\nHệ phong", () -> {
                createAkatsuki(3);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Khai Hóa", () -> {

//            if (!isHuman) { // cho phân thân khai hóa
//                warningClone();
//                return;
//            }


            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Thăng Ngưỡng", () -> {
                setCommandBox(THANG_NGUONG);
                getService().openUIThangNguong();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Thăng Cấp", () -> {
                setCommandBox(THANG_CAP);
                getService().openUIThangCap();
            }));

            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                getService().showAlert("Khai Hóa Trang Bị", "Để khai hoá trang bị bạn cần làm theo thứ tự :\n\n" +
                        "Đầu tiên hãy thăng ngưỡng trang bị \n" +
                        "Sau đó tiến tới thăng cấp và chọn 1 option mà bạn muốn build riêng cho nhân vật của mình\n\n" +
                        "Thăng cấp cần các loại đá :\n" +
                        "Đá Chinh Phục cho trang bị những chỉ số cường hóa sức mạnh\n" +
                        "Đá Vô Thương cho trang bị những chỉ số tăng khả năng phòng ngự\n" +
                        "Đá Phục Sinh cho trang bị những chỉ số sinh lực, hồi phục\n\n" +
                        "Chỉ số khai hoá chỉ hiện ở bản gốc ae chú ý vào đúng pb");
            }));
            getService().openUIMenu();
        }));

    }

    public void createAkatsuki(int sys) {
        int idItem = -1;
        if (this.gender == 0) {
            idItem = ItemName.AKATSUKI_NU;
        } else {
            idItem = ItemName.AKATSUKI_NAM;
        }
        int idNeed = 0;
        if (sys == 1) {
            idNeed = ItemName.NHAM_THACH_;
        } else if (sys == 2) {
            idNeed = ItemName.HOA_TUYET;

        } else if (sys == 3) {
            idNeed = ItemName.PHA_LE;
        }
        int quantityNeed = -1;
        Item itemNeed = getItemByIdInBag(idNeed);
        if (itemNeed == null) {
            serverDialog("Bạn cần có đủ mảnh để thực hiện:\nHệ băng : hoa tuyết, Hệ hỏa: Nham thạch, Hệ Phong: pha lê");
            return;
        }
        Item itm = getItemInMaskBoxWithSys(idItem, (byte) sys);
        if (itm != null) {
            if (itm.upgrade >= 10) {
                getService().serverMessage(String.format("%s đã đạt cấp tối đa, không thể nâng cấp", itm.template.name));
                return;
            }
            quantityNeed = (itm.upgrade + 1) * 1000;
        } else {
            quantityNeed = 1000;
        }
        if (!itemNeed.has(quantityNeed)) {
            if (itm != null) {
                getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể nâng cấp", quantityNeed));
            } else {
                getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể chế tạo", quantityNeed));
            }
            return;
        }
        removeItem(itemNeed.index, quantityNeed, true);
        if (itm != null) {
            itm.upgrade++;
            itm.initOption();
            getService().serverMessage(String.format("Chúc mừng bạn đã nâng cấp thành công %s lên cấp %d", itm.template.name, itm.upgrade));
        } else {
            itm = ItemFactory.getInstance().newItem(idItem);
            itm.sys = (byte) sys;
            itm.upgrade = 1;
            itm.yen = 0;
            itm.initOption();
            maskBox.add(itm);
            getService().serverMessage(String.format("Chúc mừng bạn đã tạo thành công %s", itm.template.name));
        }
    }

    public void admission(byte sys, short npc) {
        if (this.classId == 0) {
            if (taskId >= TaskName.NV_GIA_TANG_SUC_MANH && taskMain == null && this.level >= 10) {
                if (this.equipment[1] != null) {
                    getService().npcChat(npc, "Con hãy gỡ bỏ tạp niệm bằng cách cất vũ khí đang sử dụng vào hành trang trước rồi hãy vào lớp học của ta.");
                    return;
                }
                this.classId = sys;
                this.skillPoint = (short) (this.level - 9);
                this.potentialPoint = (short) (this.level * 10);
                if (this.level >= 70) {
                    this.potentialPoint += (this.level - 70) * 10;
                }
                if (this.level >= 80) {
                    this.potentialPoint += (this.level - 80) * 10;
                }
                if (this.level >= 90) {
                    this.potentialPoint += (this.level - 90) * 10;
                }
                if (this.level >= 100) {
                    this.potentialPoint += (this.level - 100) * 10;
                }
                if (this.classId == 1 || this.classId == 3 || this.classId == 5) {
                    this.potential[0] = 15;
                    this.potential[1] = 5;
                    this.potential[2] = 5;
                    this.potential[3] = 5;
                } else {
                    this.potential[0] = 5;
                    this.potential[1] = 5;
                    this.potential[2] = 10;
                    this.potential[3] = 10;
                }
                vSkill.clear();
                this.onCSkill = new byte[]{};
                this.onKSkill = new byte[]{-1, -1, -1};
                this.onOSkill = new byte[]{-1, -1, -1, -1, -1};
                byte type = 0;
                if (!isHuman) {
                    type = 1;
                }
                getService().sendSkillShortcut("OSkill", this.onOSkill, type);
                getService().sendSkillShortcut("KSkill", this.onKSkill, type);
                getService().sendSkillShortcut("CSkill", this.onCSkill, type);
                switch (npc) {
                    case 11:
                        getService().npcChat(npc, "Chào mừng con đến với trường Haruna. Con hãy sử dụng vũ khí và đọc sách võ công mà ta tặng (mở Menu/Bản thân/Hành trang) để bước đầu chuẩn bị cho việc học tập");
                        break;

                    case 9:
                        getService().npcChat(npc, "Chào mừng con đến với trường Hirosaki. Con hãy sử dụng vũ khí và đọc sách võ công mà ta tặng (mở Menu/Bản thân/Hành trang) để bước đầu chuẩn bị cho việc học tập");
                        break;

                    case 10:
                        getService().npcChat(npc, "Chào mừng con đến với trường Ookaza. Con hãy sử dụng vũ khí và đọc sách võ công mà ta tặng (mở Menu/Bản thân/Hành trang) để bước đầu chuẩn bị cho việc học tập");
                        break;
                }
                Item item = null;
                Item item2 = null;
                switch (sys) {
                    case 1:
                        item = ItemFactory.getInstance().newItem(40);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(94);
                        item2.isLock = true;
                        item2.sys = 1;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;

                    case 3:
                        item = ItemFactory.getInstance().newItem(58);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(99);
                        item2.isLock = true;
                        item2.sys = 2;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;

                    case 5:
                        item = ItemFactory.getInstance().newItem(76);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(104);
                        item2.isLock = true;
                        item2.sys = 3;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;

                    case 2:
                        item = ItemFactory.getInstance().newItem(49);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(114);
                        item2.isLock = true;
                        item2.sys = 1;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;

                    case 4:
                        item = ItemFactory.getInstance().newItem(67);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(109);
                        item2.isLock = true;
                        item2.sys = 2;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;

                    case 6:
                        item = ItemFactory.getInstance().newItem(85);
                        item.isLock = true;
                        item2 = ItemFactory.getInstance().newItem(119);
                        item2.isLock = true;
                        item2.sys = 3;
                        item2.options.add(new ItemOption(0, 300));
                        item2.options.add(new ItemOption(8, 10));
                        item2.options.add(new ItemOption(10, 5));
                        item2.options.add(new ItemOption(7, 10));
                        item2.options.add(new ItemOption(30, 5));
                        break;
                }
                getService().loadClass();
                addItemToBag(item);
                addItemToBag(item2);
                selectedSkill = null;
                if (isHuman) {
                    taskMain = TaskFactory.getInstance().createTask(taskId, (byte) 0, (short) -1);
                    getService().sendTaskInfo();
                }
            } else {
                getService().npcChat(npc, "Con vẫn chưa đủ điều kiện để vào lớp (trình độ từ cấp 10 và làm xong nhiệm vụ tìm hiểu trường");
            }
        } else {
            getService().npcChat(npc, "Con đã vào lớp từ trước rồi mà!");
        }
    }

    public void npcKazeto() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Bảng xếp hạng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Top đại gia", () -> {
                showRankedList(0);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top cao thủ", () -> {
                showRankedList(1);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top gia tộc", () -> {
                showRankedList(2);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top hang động", () -> {
                showRankedList(3);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhập học", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Đao", () -> {
                admission((byte) 5, (short) 11);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Quạt", () -> {
                admission((byte) 6, (short) 11);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tẩy điểm", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tiềm năng", () -> {
                tayTiemNang((short) 11);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Kỹ năng", () -> {
                tayKyNang((short) 11);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Bạn may mắn lắm mới gặp được ta đó, ta vốn là Thần Gió mà!", "So với các trường khác, trường Gió là tốt nhất", "Một số học sinh trường gió của chúng ta có thể chấp hai học sinh các trường kia.");
            getService().npcChat(NpcName.THAY_KAZETO, talk);
            if (taskId == TaskName.NV_TIM_HIEU_3_TRUONG && taskMain != null && taskMain.index == 3) {
                taskNext();
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Giao chiến", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Thành tích", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
            }));
            getService().openUIMenu();
        }));
    }

    public void npcOokamesama() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Bảng xếp hạng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Top đại gia", () -> {
                showRankedList(0);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top cao thủ", () -> {
                showRankedList(1);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top gia tộc", () -> {
                showRankedList(2);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top hang động", () -> {
                showRankedList(3);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhập học", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Kunai", () -> {
                admission((byte) 3, (short) 10);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Cung", () -> {
                admission((byte) 4, (short) 10);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tẩy điểm", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tiềm năng", () -> {
                tayTiemNang((short) 10);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Kỹ năng", () -> {
                tayKyNang((short) 10);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Con có cảm thấy lạnh không?", "Học, để thành tài, để thành người tốt, chứ không phải để ganh đua với đời.", "Tập trung học tốt nhé con.");
            getService().npcChat(NpcName.THAY_OOKAMESAMA, talk);
            if (taskId == TaskName.NV_TIM_HIEU_3_TRUONG && taskMain != null && taskMain.index == 2) {
                taskNext();
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Giao chiến", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Thành tích", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
            }));
            getService().openUIMenu();
        }));
    }

    public void npcToyotomi() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Bảng xếp hạng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Top đại gia", () -> {
                showRankedList(0);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top cao thủ", () -> {
                showRankedList(1);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top gia tộc", () -> {
                showRankedList(2);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Top hang động", () -> {
                showRankedList(3);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhập học", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Kiếm", () -> {
                admission((byte) 1, (short) 9);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Ninja Phi Tiêu", () -> {
                admission((byte) 2, (short) 9);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tẩy điểm", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tiềm năng", () -> {
                tayTiemNang((short) 9);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Kỹ năng", () -> {
                tayKyNang((short) 9);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Trường ta dạy kiếm và phi tiêu, chẳng dạy các vũ khí vô danh khác", "Bạn muốn trở thành hỏa Ninja thì học, không thì cút.", "Theo học ở đây là vinh hạnh của bạn, biết chứ.");
            getService().npcChat(NpcName.CO_TOYOTOMI, talk);
            if (taskId == TaskName.NV_TIM_HIEU_3_TRUONG && taskMain != null && taskMain.index == 1) {
                taskNext();
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Giao chiến", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Thành tích", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
            }));
            getService().openUIMenu();
        }));
    }

    private void initMenu(int npcTemplateId) {
        menus.clear();
        short taskNpcId = getTaskNpcId();
        if (taskNpcId == npcTemplateId) {
            if (taskMain == null) {
                menus.add(new Menu(CMDMenu.NHAN_NHIEM_VU, Task.getTaskTemplate(taskId).getName()));
            } else if (taskMain.index == taskMain.template.getSubNames().length - 1) {
                menus.add(new Menu(CMDMenu.HOAN_THANH_NHIEM_VU, "Hoàn thành nhiệm vụ"));
            } else {
                if ((npcTemplateId == 14 || npcTemplateId == 15 || npcTemplateId == 16)) {
                    if (npcTemplateId == NpcName.FUJIWARA && taskId == TaskName.NV_TIM_LAI_CAY_RIU) {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Vào trong hang", () -> {
                            Map map = MapManager.getInstance().find(MapName.HANG_INOSHISHI);
                            InoshishiCave z = new InoshishiCave(0, map.tilemap, map);
                            mapBeforeEnterPB = mapId;
                            outZone();
                            setXY((short) 35, (short) 0);
                            z.join((Char) this);
                        }));
                    } else {
                        menus.add(new Menu(CMDMenu.LAM_NHIEM_VU, "Giao thư"));
                    }
                } else if (npcTemplateId == NpcName.THAY_OOKAMESAMA || npcTemplateId == NpcName.THAY_KAZETO || npcTemplateId == NpcName.CO_TOYOTOMI) {
                    if (isSchool()) {
                        if (taskId == TaskName.NV_THACH_DAU) {
                            menus.add(new Menu(CMDMenu.EXECUTE, "Vào phòng thi đấu", () -> {
                                Map map = null;
                                Gymnasium z = null;
                                if (npcTemplateId == NpcName.THAY_KAZETO) {
                                    int mapID = MapName.NHA_THI_DAU_HARUNA;
                                    map = MapManager.getInstance().find(mapID);
                                    z = new HarunaGymnasium(0, map.tilemap, map);
                                } else if (npcTemplateId == NpcName.CO_TOYOTOMI) {
                                    int mapID = MapName.NHA_THI_DAU_HIROSAKI;
                                    map = MapManager.getInstance().find(mapID);
                                    z = new HirosakiGymnasium(0, map.tilemap, map);
                                } else {
                                    int mapID = MapName.NHA_THI_DAU_OOKAZA;
                                    map = MapManager.getInstance().find(mapID);
                                    z = new OokazaGymnasium(0, map.tilemap, map);
                                }
                                if (z != null) {
                                    outZone();
                                    z.setPlayer((Char) this);
                                    setXY((short) 55, (short) 264);
                                    mapBeforeEnterPB = mapId;
                                    z.join((Char) this);
                                }
                            }));
                        }
                    }
                } else if (npcTemplateId == NpcName.JAIAN) {
                    if (!isLeading()) {
                        getService().npcChat(npcTemplateId, "Xin chào, ta là người dân ở làng Chài, trên dường đi đã bị bọn quái thú tấn công, xin hãy giúp ta tìm đường quay trở về.");
                        menus.add(new Menu(CMDMenu.EXECUTE, "Đi nào", () -> {
                            Task task = taskMain;
                            if (task != null && task.taskId == TaskName.NV_DUA_JAIAN_TRO_VE && task.index == 1) {
                                Npc npc = zone.getNpc(NpcName.JAIAN);
                                Bot bot = Bot.builder().id(-this.id).name(npc.template.name).level(30).typePk(PK_NORMAL).build();
                                bot.setDefault();
                                FashionCustom fashionCustom = FashionCustom.builder().head((short) npc.template.headId).body((short) npc.template.bodyId).leg((short) npc.template.legId).weapon((short) -1).build();
                                bot.setFashionStrategy(fashionCustom);
                                AbilityCustom abilityCustom = AbilityCustom.builder().hp(2000).build();
                                bot.setAbilityStrategy(abilityCustom);
                                bot.setMove(new JaianMove((Char) this, (short) npc.cy));
                                bot.setAbility();
                                bot.setFashion();
                                bot.recovery();
                                bot.setXY((short) npc.cx, (short) npc.cy);
                                setEscorted(bot);
                                getService().npcUpdate(npc.id, 15);
                                zone.join(bot);
                                setLeading(true);
                            }
                        }));
                    }
                } else {
                    if (taskId == TaskName.NV_KIEN_THUC || taskId == TaskName.NV_BAI_HOC_VAO_TRUONG) {
                        menus.add(new Menu(CMDMenu.LAM_NHIEM_VU, taskMain.template.getName()));
                    }
                }
            }
        }
//        else if (Events.event == Events.SUMMER && (npcTemplateId == 14 || npcTemplateId == 15 || npcTemplateId == 16)) {
//            menus.add(new Menu(CMDMenu.TRAO_LINH_VAT, "Trao linh vật"));
//        }
        if (zone.tilemap.isNhaThiDau()) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Rời khỏi nơi này", () -> {
                short[] xy = NinjaUtils.getXY(mapBeforeEnterPB);
                setXY(xy[0], xy[1]);
                changeMap(mapBeforeEnterPB);
            }));
            return;
        }
        switch (npcTemplateId) {
            case NpcName.KANATA:
                npcKanata();
                break;
            case NpcName.FUROYA:
                npcFuroya();
                break;
            case NpcName.AMEJI:
                npcAmeji();
                break;
            case NpcName.KIRIKO:
                npcKiriko();
                break;
            case NpcName.TABEMONO:
                npcTabemono();
                break;
            case NpcName.KAMAKURA:
                npcKamakura();
                break;
            case NpcName.KENSHINTO:
                npcKenshinto();
                break;
            case NpcName.UMAYAKI:
                npcUmayaki_1();
                break;
            case NpcName.UMAYAKI_2:
                npcUmayaki_2();
                break;
            case NpcName.CO_TOYOTOMI:
                npcToyotomi();
                break;
            case NpcName.THAY_OOKAMESAMA:
                npcOokamesama();
                break;
            case NpcName.THAY_KAZETO:
                npcKazeto();
                break;
            case NpcName.TAJIMA:
                npcTajima();
                break;
            case NpcName.HASHIMOTO:
                if (Events.event == Events.SUMMER) {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Dế Ngọc", () -> {
                        if (getSlotNull() == 0) {
                            getService().npcChat((short) npcTemplateId, "Hành trang của bạn không có đủ chỗ trống");
                            return;
                        }
                        int indexUI = getIndexItemByIdInBag(ItemName.DE_NGOC);
                        if (indexUI == -1) {
                            serverMessage("Hãy tìm linh vật rồi đến gặp ta");
                            return;
                        }
                        Event event = Event.getEvent();
                        Item item = this.bag[indexUI];
                        event.useEventItem((Char) this, item.id, event.getVipItems());
                    }));
                }
                break;
            case NpcName.FUJIWARA:
                if (Events.event == Events.SUMMER) {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Bọ Vàng", () -> {
                        if (getSlotNull() == 0) {
                            getService().npcChat((short) npcTemplateId, "Hành trang của bạn không có đủ chỗ trống");
                            return;
                        }
                        int indexUI = getIndexItemByIdInBag(ItemName.BO_VANG);
                        if (indexUI == -1) {
                            serverMessage("Hãy tìm linh vật rồi đến gặp ta");
                            return;
                        }
                        Event event = Event.getEvent();
                        Item item = this.bag[indexUI];
                        event.useEventItem((Char) this, item.id, event.getVipItems());
                    }));
                }
                break;
            case NpcName.NAO:
                if (Events.event == Events.SUMMER) {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Bướm vàng", () -> {
                        if (getSlotNull() == 0) {
                            getService().npcChat((short) npcTemplateId, "Hành trang của bạn không có đủ chỗ trống");
                            return;
                        }
                        int indexUI = getIndexItemByIdInBag(ItemName.BUOM_VANG);
                        if (indexUI == -1) {
                            serverMessage("Hãy tìm linh vật rồi đến gặp ta");
                            return;
                        }
                        Event event = Event.getEvent();
                        Item item = this.bag[indexUI];
                        event.useEventItem((Char) this, item.id, event.getVipItems());
                    }));
                }
                break;
            case NpcName.BA_REI:
                npcBaRei();
                break;
            case NpcName.OKANECHAN:
                npcOkanechan();
                break;
            case NpcName.MATSURUGI:
                npcMatsurugi();
                break;
            case NpcName.SOBA:
                npcShoba();
                break;
            case NpcName.RIKUDOU:
                npcRikudou();
                break;
            case NpcName.GOOSHO:
                npcGoosho();
                break;
            case NpcName.TRU_CO_QUAN:
                npcTruCoQuan();
                break;
            case NpcName.SHINWA:
                npcShinwa();
                break;
            case NpcName.RAKKII:
                npcRakkii();
                break;
            case NpcName.KAGAI:
                npcKagai();
                break;
            case NpcName.TIEN_NU:
                npcTienNu();
                break;
            case NpcName.VUA_HUNG:
                npcVuaHung();
                break;
            case NpcName.ADMIN:
                npcAdmin();
                break;
            case NpcName.CAY_THONG:
                npcCayThong();
                break;
            case NpcName.DEIDARA:
                npcDeidara();
                break;
            case NpcName.LONG_DEN_2:
                npcLongDen();
                break;
            case NpcName.TASHINO:
                npcTashino();
                break;
            case NpcName.SUNOO:
                npcSunoo();
                break;
            case NpcName.HOA_MAI:
                npcHoaMai();
                break;
            case NpcName.EM_BE:
                npcEmBe();
                break;
//            case NpcName.NANGPET:
//                npcNangPet();
//                break;
            case NpcName.CHAN_LE: // chanle
                npcChanLe();
                break;
            case NpcName.BUY_16: // shop 16
                npcBuy16();
                break;

            case NpcName.DIA_NGUC: // lang dia nguc
                npcLangDiaNguc();
                break;

            case NpcName.ChoDen:
                npcChoDen();
                break;

        }

    }

    public void escortFailed() {
        Bot escorted = getEscorted();
        if (escorted != null) {
            escorted.outZone();
            Npc npc = zone.getNpc(NpcName.JAIAN);
            if (npc != null) {
                getService().npcUpdate(npc.id, 1);
            }
            setLeading(false);
            setEscorted(null);
        }
    }

    public void npcKenshinto() {

        menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp", () -> {
            if (NinjaUtils.sum(this.coin, this.yen) > 2000000000) {  /// fix xu yên không quá 2 tỷ
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.CAT_XU_MAX,
                        "Tổng số xu và yên của bạn vượt quá 2 tỷ, cất bớt xu vào rương rồi thực hiện lại "));
                getService().openUIConfirmID();
                return;
            }
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Thường", () -> {
                getService().openUI((byte) 10);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cẩn thận", () -> {
                getService().openUI((byte) 31);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                getService().npcChat(NpcName.KENSHINTO, "Bỏ Trang bị và Đá vào trong khung để nâng cấp, Khi nâng cấp cẩn thận thì phải có lượng.");
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Luyện đá", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Yên", () -> {
                getService().openUI((byte) 12);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Xu", () -> {
                getService().openUI((byte) 11);

            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tách", () -> {
            getService().openUI((byte) 13);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Chuyển hóa", () -> {
            getService().openUI((byte) 33);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Luyện ngọc", () -> {
            getService().openUI((byte) 46);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Khảm", () -> {
            if (NinjaUtils.sum(this.coin, this.yen) > 2000000000) {  /// fix xu yên không quá 2 tỷ
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.CAT_XU_MAX,
                        "Tổng số xu và yên của bạn vượt quá 2 tỷ, cất bớt xu vào rương rồi thực hiện lại "));
                getService().openUIConfirmID();
                return;
            }
            getService().openUI((byte) 47);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Gọt ngọc", () -> {
            getService().openUI((byte) 49);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tháo ngọc", () -> {
            getService().openUI((byte) 50);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 2) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Bạn muốn cải tiến trang bị?", "Nâng cấp trang bị: Uy tín, giá cả phải chăng");
            getService().npcChat(NpcName.KENSHINTO, talk);
        }));
    }

    public void npcFuroya() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Y phục", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Nón", () -> {
                if (this.gender == 1) {
                    getService().openUI(StoreManager.TYPE_MEN_HAT);
                } else {
                    getService().openUI(StoreManager.TYPE_WOMEN_HAT);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Áo", () -> {
                if (this.gender == 1) {
                    getService().openUI(StoreManager.TYPE_MEN_SHIRT);
                } else {
                    getService().openUI(StoreManager.TYPE_WOMEN_SHIRT);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Găng tay", () -> {
                if (this.gender == 1) {
                    getService().openUI(StoreManager.TYPE_MEN_GLOVES);
                } else {
                    getService().openUI(StoreManager.TYPE_WOMEN_GLOVES);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Quần", () -> {
                if (this.gender == 1) {
                    getService().openUI(StoreManager.TYPE_MEN_PANT);
                } else {
                    getService().openUI(StoreManager.TYPE_WOMEN_PANT);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Giày", () -> {
                if (this.gender == 1) {
                    getService().openUI(StoreManager.TYPE_MEN_SHOES);
                } else {
                    getService().openUI(StoreManager.TYPE_WOMEN_SHOES);
                }
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Ngưới cần giày, giáp sắt, quần áo?", "Giáp, giày giá rẻ đây!", "Không mặc giáp mua từ ta, ra khỏi trường bạn sẽ gặp nguy hiểm.");
            getService().npcChat(NpcName.FUROYA, talk);
        }));
    }

    public void confirmID(Message ms) {
        try {
            if (trade != null) {
                warningTrade();
                return;
            }
            if (confirmPopup == null) {
                return;
            }
            try {
                byte id = ms.reader().readByte();
                if (confirmPopup.getId() != id) {
                    return;
                }

                if (!isHuman && (id != CMDConfirmPopup.NANG_BI_KIP &&
                        id != CMDConfirmPopup.NANG_PET &&
                        id != CMDConfirmPopup.BUY_STORE_UPGRADE && // shop 16
                        id != CMDConfirmPopup.THANG_NGUONG &&
                        id != CMDConfirmPopup.THANG_CAP &&
                        id != CMDConfirmPopup.THANG_NGUONG_C1)) {
                    warningClone();
                    return;
                }

                switch (id) {

                    case CMDConfirmPopup.HOA_HINH: // hóa hình
                        hoaHinh();
                        break;
                    case CMDConfirmPopup.PHAN_TACH:
                        phanTach();
                        break;

                    case CMDConfirmPopup.CONFIRM:
                        confirmPopup.confirm();
                        break;
                    case CMDConfirmPopup.NANG_MAT:
                    case CMDConfirmPopup.NANG_MAT_VIP:
                        if (equipment[14] == null) {
                            serverMessage("Hãy sử dụng Nguyệt Nhãn để sử dụng chức năng này.");
                            return;
                        }
                        if (equipment[14].upgrade >= 10) {
                            serverMessage("Nguyệt Nhãn đã đạt cáp tối đa.");
                            return;
                        }
                        boolean isGold = id == CMDConfirmPopup.NANG_MAT_VIP;
                        int[] costs = {500000, 750000, 1250000, 2000000, 3000000, 4500000, 6000000, 7750000, 10000000};
                        int[] costs2 = {50, 60, 85, 100, 150, 200, 300, 450, 600};
                        int[] percents = {50, 35, 25, 20, 15, 12, 10, 8, 5};
                        int[] items = {695, 696, 697, 698, 699, 700, 701, 702, 703};
                        int point = (equipment[14].upgrade + 1) * 100;
                        if (pointAo < point || pointGangTay < point || pointGiay < point || pointNgocBoi < point || pointLien < point || pointNhan < point || pointNon < point || pointPhu < point || pointQuan < point || pointVuKhi < point) {
                            serverMessage("Không đủ " + point + " điểm danh vọng mỗi loại.");
                            return;
                        }
                        int index = equipment[14].upgrade - 1;
                        int indexItem = getIndexItemByIdInBag(items[index], true);
                        if (indexItem == -1) {
                            indexItem = getIndexItemByIdInBag(items[index], false);
                        }
                        if (indexItem == -1) {
                            ItemTemplate template = ItemManager.getInstance().getItemTemplate(items[index]);
                            serverMessage("Yêu cầu phải có 10 viên " + template.name);
                            return;
                        }
                        if (getSlotNull() == 0) {
                            warningBagFull();
                            return;
                        }
                        Item item = this.bag[indexItem];
                        if (!item.has(10)) {
                            serverMessage("Yêu cầu phải có 10 viên " + item.template.name);
                            return;
                        }
                        int cost = costs[index];
                        if (NinjaUtils.sum(this.yen, this.coin) < cost) {
                            serverMessage("Không đủ xu hoặc yên để nâng cấp Nguyệt Nhãn");
                            return;
                        }
                        int percent = percents[index];
                        if (isGold) {
                            if (user.gold < costs2[index]) {
                                serverMessage("Không đủ lượng nâng cấp Nguyệt Nhãn");
                                return;
                            }
                            percent *= 2;
                            addGold(-costs2[index]);
                        }
                        int rand = NinjaUtils.nextInt(100);
                        if (rand < percent) {
                            getService().deleteItemBody(equipment[14].template.type);
                            Item itm = ItemFactory.getInstance().newItem(equipment[14].id + 1);
                            itm.isLock = true;
                            addItemToBag(itm);
                            equipment[14] = null;
                        } else {
                            serverMessage("Nâng cấp thất bại.");
                        }
                        if (this.yen < cost) {
                            addCoin(-(cost - this.yen));
                            addYen(-this.yen);
                        } else {
                            addYen(-cost);
                        }
                        removeItem(indexItem, 10, true);
                        break;

                    case CMDConfirmPopup.NANG_BI_KIP: {

                        int itemID = -1;
                        switch (getSys()) {
                            case 1:
                                itemID = 834;
                                break;

                            case 2:
                                itemID = 836;
                                break;

                            case 3:
                                itemID = 835;
                                break;
                        }
                        if (itemID == -1) {
                            return;
                        }
                        int cap = 0;
                        Item item2 = this.equipment[ItemTemplate.TYPE_BIKIP];
                        if (item2 != null) {
                            if (item2.options.isEmpty()) {
                                getService().npcChat(NpcName.TASHINO,
                                        "Hãy luyện bí kíp trước để ta có thể thấy sức mạnh của nó.");
                                return;
                            }
                            for (ItemOption option : item2.options) {
                                if (option.optionTemplate.id == 85) {
                                    cap = option.param;
                                    break;
                                }
                            }
                            if (cap >= 9) {
                                getService().npcChat(NpcName.TASHINO,
                                        "Bí kíp của ngươi đã quá mạnh, ta không thể giúp được ngươi.");
                                return;
                            }
                            String name = ItemManager.getInstance().getItemName(itemID);
                            int indexItem2 = this.getIndexItemByIdInBag(itemID);
                            int quantity = (cap + 1) * 5;
                            if (indexItem2 == -1 || this.bag[indexItem2] == null
                                    || quantity > this.bag[indexItem2].getQuantity()) {
                                getService().npcChat(NpcName.TASHINO,
                                        String.format("Hãy mang %d viên %s đưa cho ta, ta sẽ giúp.", quantity, name));
                                return;
                            }
                            int fee = (cap + 5) * (200 + cap);
                            if (fee > user.gold) {
                                getService().npcChat(NpcName.TASHINO,
                                        String.format("Muốn ta giúp ngươi phải đưa ta %d lượng.", fee));
                                return;
                            }
                            if (getSlotNull() == 0) {
                                getService().npcChat(NpcName.TASHINO,
                                        "Hãy chừa ra 1 chỗ trống trong hành trang của ngươi.");
                                return;
                            }
                            addGold(-fee);
                            removeItem(indexItem2, quantity, true);
                            int percent2 = 100 - (cap * 10) - ((cap * 2) / 2);
                            int rand2 = NinjaUtils.nextInt(100);
                            if (rand2 < percent2) {
                                Item newItem = ItemFactory.getInstance().newItem(item2.id);
                                newItem.isLock = true;
                                for (ItemOption option : item2.options) {
                                    int add = option.param / 20;
                                    if (option.optionTemplate.id == 92 || option.optionTemplate.id == 86 || option.optionTemplate.id == 84 || option.optionTemplate.id == 91) {
                                        add = option.param / 15;
                                    }

                                    option.param += (add > 0 ? add : 1);

                                    newItem.options.add(option);
                                }
                                this.equipment[ItemTemplate.TYPE_BIKIP] = null;
                                getService().deleteItemBody(ItemTemplate.TYPE_BIKIP);
                                addItemToBag(newItem);
                                setFashion();
                                setAbility();
                                String talk = (String) NinjaUtils.randomObject(
                                        "Chúc mừng ngươi, hãy tận hưởng những sức mạnh đó",
                                        "Thành công rồi, ngươi thấy sao",
                                        "Hãy xem thử, bí kíp của ngươi có vẻ đã mạnh hơn");
                                getService().npcChat(NpcName.TASHINO, talk);
                            } else {
                                String talk = (String) NinjaUtils.randomObject("Ta đã cố gắng hết sức",
                                        "Ta xin lỗi, lần sau ta sẽ cố gắng hơn nữa",
                                        "Sức mạnh quá lớn, ta không thể kiểm soát được nó");
                                getService().npcChat(NpcName.TASHINO, talk);
                            }
                        }
                    }
                    break;


//                    case CMDConfirmPopup.NANG_PET:
//
//                        int itemID = -1;
//                        switch (getSys()) {
//                            case 1:
//                                itemID = 834;
//                                break;
//
//                            case 2:
//                                itemID = 836;
//                                break;
//
//                            case 3:
//                                itemID = 835;
//                                break;
//                        }
//                        if (itemID == -1) {
//                            return;
//                        }
//                        int cap = 0;
//                        Item item2 = this.equipment[ItemTemplate.TYPE_THUNUOI];
//                        if (item2 != null) {
//                            if (item2.options.isEmpty()) {
//                                getService().npcChat(NpcName.NANGPET,
//                                        "Hãy luyện pet trước để ta có thể thấy sức mạnh của nó.");
//                                return;
//                            }
//                            for (ItemOption option : item2.options) {
//                                if (option.optionTemplate.id == 85) {
//                                    cap = option.param;
//                                    break;
//                                }
//                            }
//                            if (cap >= 9) {
//                                getService().npcChat(NpcName.NANGPET,
//                                        "Pet của ngươi đã quá mạnh, ta không thể giúp được ngươi.");
//                                return;
//                            }
//                            String name = ItemManager.getInstance().getItemName(itemID);
//                            int indexItem2 = this.getIndexItemByIdInBag(itemID);
//                            int quantity = (cap + 1) * 5;
////                            if (indexItem2 == -1 || this.bag[indexItem2] == null
////                                    || quantity > this.bag[indexItem2].getQuantity()) {
////                                getService().npcChat(NpcName.NANGPET,
////                                        String.format("Hãy mang %d viên %s đưa cho ta, ta sẽ giúp.", quantity, name));
////                                return;
////                            }
//                            int fee = (cap + 5) * (200 + cap);
//                            if (fee > user.gold) {
//                                getService().npcChat(NpcName.NANGPET,
//                                        String.format("Muốn ta giúp ngươi phải đưa ta %d lượng.", fee));
//                                return;
//                            }
//                            if (getSlotNull() == 0) {
//                                getService().npcChat(NpcName.NANGPET,
//                                        "Hãy chừa ra 1 chỗ trống trong hành trang của ngươi.");
//                                return;
//                            }
//                            addGold(-fee);
////                            removeItem(indexItem2, quantity, true);
//                            int percent2 = 100 - (cap * 10) - ((cap * 2) / 2);
//                            int rand2 = NinjaUtils.nextInt(100);
//                            if (rand2 < percent2) {
//                                Item newItem = ItemFactory.getInstance().newItem(item2.id);
//                                newItem.isLock = true;
//                                for (ItemOption option : item2.options) {
//                                    int add = option.param / 20;
//                                    if (option.optionTemplate.id == 92 || option.optionTemplate.id == 86 || option.optionTemplate.id == 84 || option.optionTemplate.id == 91) {
//                                        add = option.param / 15;
//                                    }
//
//                                    option.param += (add > 0 ? add : 1);
//
//                                    newItem.options.add(option);
//                                }
//                                this.equipment[ItemTemplate.TYPE_THUNUOI] = null;
//                                getService().deleteItemBody(ItemTemplate.TYPE_THUNUOI);
//                                addItemToBag(newItem);
//                                setFashion();
//                                setAbility();
//                                String talk = (String) NinjaUtils.randomObject(
//                                        "Chúc mừng ngươi, hãy tận hưởng những sức mạnh đó",
//                                        "Thành công rồi, ngươi thấy sao",
//                                        "Hãy xem thử, pet của ngươi có vẻ đã mạnh hơn");
//                                getService().npcChat(NpcName.NANGPET, talk);
//                            } else {
//                                String talk = (String) NinjaUtils.randomObject("Ta đã cố gắng hết sức",
//                                        "Ta xin lỗi, lần sau ta sẽ cố gắng hơn nữa",
//                                        "Sức mạnh quá lớn, ta không thể kiểm soát được nó");
//                                getService().npcChat(NpcName.NANGPET, talk);
//                            }
//                        }
//                        break;

                    case CMDConfirmPopup.NHIEM_VU_DANH_VONG:
                        if (this.gloryTask != null) {
                            String title = this.gloryTask.getTaskTitle();
                            getService().showAlert("Thông báo", "Đã hủy nhiệm vụ " + title);
                            this.gloryTask = null;
                        } else {
                            serverMessage("Hiện tại con chưa nhận nhiệm vụ nào");
                        }
                        break;
                    case CMDConfirmPopup.HUY_AN:
                        if (fashion[13] == null) {
                            serverMessage("Hãy sử dụng Ấn tộc để sử dụng chức năng này.");
                            return;
                        } else {
                            fashion[13] = null;
                            serverMessage("Đã hủy ấn thành công");
                            setAbility();
                            setFashion();
                        }
                        break;
                    case CMDConfirmPopup.HUY_ALL_NHIEM_VU:
                        if (getSlotNull() < 8) {
                            serverMessage("Hành trang không đủ chỗ trống.");
                            return;
                        }
                        if (user.gold < 2000) {
                            serverDialog("Bạn không đủ 2000 lượng để bỏ qua nhiệm vụ");
                            return;
                        }
                        if (taskId < 8) {
                            serverDialog("Làm xong hết nhiệm vụ ở làng Tone mới có thể sử dụng tính năng này!");
                            return;
                        }
                        if (taskId >= 43) {
                            serverDialog("Bạn đã hoàn thành hoặc bỏ qua toàn bộ nhiệm vụ rồi!");
                            return;
                        }
                        taskMain = null;
                        taskId = 43;
                        getService().taskFinish();
                        getService().loadAll();
                        for (int i = ItemName.NGOC_1_SAO; i <= ItemName.NGOC_7_SAO; i++) {
                            Item ngoc = ItemFactory.getInstance().newItem(i);
                            ngoc.isLock = true;
                            addItemToBag(ngoc);
                        }
                        addGold(-2000);
                        initListCanEnterMap();
                        this.saveData();
                        break;
                    case CMDConfirmPopup.UPGRADE_SOUL_STONE:
                        this.upgradeSoulStone();
                        break;
                    case CMDConfirmPopup.NANG_AN_TOC:
                        this.upgradeAnToc();
                        break;
                    case CMDConfirmPopup.CAT_XU_MAX:
                        openUIBox();
                        break;
                    case CMDConfirmPopup.MUA_DIEM_DANH_VONG:
                        int rand1 = 0;
                        int point1 = 5;
                        for (int i = 0; i < 200; i++) {
                            if (this.avgPointDV(this.getPointDV(rand1))) {
                                for (int j = 0; j < 10; j++) {
                                    rand1 = j;
                                    if (!this.avgPointDV(this.getPointDV(rand1))) {
                                        break;
                                    }
                                }
                            }
                            switch (rand1) {
                                case 0:
                                    pointNon += point1;
                                    break;

                                case 1:
                                    pointVuKhi += point1;
                                    break;

                                case 2:
                                    pointAo += point1;
                                    break;

                                case 3:
                                    pointLien += point1;
                                    break;

                                case 4:
                                    pointGangTay += point1;
                                    break;

                                case 5:
                                    pointNhan += point1;
                                    break;

                                case 7:
                                    pointNgocBoi += point1;
                                    break;

                                case 6:
                                    pointQuan += point1;
                                    break;

                                case 9:
                                    pointPhu += point1;
                                    break;
                                case 8:
                                    pointGiay += point1;
                                    break;
                            }
                        }
                        getService().showAlert("Nhận được ", " 100 điểm danh vọng ");
                        addGold(-80000);
                        break;


                    case CMDConfirmPopup.THANG_NGUONG:
                        if (this.coin < NinjaUtils.xuThangNguong[0]) {
                            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangNguong[0]) + " xu.");
                            return;
                        }
                        if (this.user.gold < NinjaUtils.luongThangNguong[0]) {
                            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangNguong[0]) + " lượng.");
                            return;
                        }
                        if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangNguong[0]) {
                            Item itemThangNguong = equipment[checkIndexUI];
                            itemThangNguong.options.add(new ItemOption(156, 1));
                            itemThangNguong.options.add(new ItemOption(157, 0));
                            serverMessage("Thăng ngưỡng thành công");
                            setAbility();
                            setFashion();
                            service.updateInfoMe();
                            getService().itemInfo(itemThangNguong, (byte) 4, IndexUI);
                        } else {
                            serverMessage("Thăng ngưỡng thất bại");
                        }
                        addGold(-NinjaUtils.luongThangNguong[0]);
                        addCoin(-NinjaUtils.xuThangNguong[0]);
                        setAbility();
                        setFashion();

                        break;
                    case CMDConfirmPopup.THANG_NGUONG_C1:
                        Item itemThangNguongC1 = equipment[checkIndexUI];
                        for (ItemOption option : itemThangNguongC1.options) {
                            if (option.optionTemplate.id == 156) {
                                if (this.coin < NinjaUtils.xuThangNguong[option.param]) {
                                    serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangNguong[option.param]) + " xu.");
                                    return;
                                }
                                if (this.user.gold < NinjaUtils.luongThangNguong[option.param]) {
                                    serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangNguong[option.param]) + " lượng.");
                                    return;
                                }
                                if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangNguong[option.param]) {
                                    serverMessage("Thăng ngưỡng thành công!");
                                    addGold(-NinjaUtils.luongThangNguong[option.param]);
                                    addCoin(-NinjaUtils.xuThangNguong[option.param]);
                                    option.param += 1;
                                    setAbility();
                                    setFashion();
                                    service.updateInfoMe();
                                    getService().itemInfo(itemThangNguongC1, (byte) 4, IndexUI);
                                    if (checkThangNguong(itemThangNguongC1) >= 9) {
                                        setCommandBox(THANG_NGUONG);
                                        getService().openUIThangNguong();
                                    }
                                    break;
                                } else {
                                    serverMessage("Thăng ngưỡng thất bại");
                                    addGold(-NinjaUtils.luongThangNguong[option.param]);
                                    addCoin(-NinjaUtils.xuThangNguong[option.param]);
                                    break;
                                }

                            }
                        }
                        break;


                    case CMDConfirmPopup.THANG_CAP:
                        List<Item> itemList = new ArrayList<>();
                        int nguong = 0;
                        int cap1 = 0;
                        for (int i = 0; i < equipment.length; i++) {
                            Item item22 = equipment[i];
                            if (item22 != null && (item22.template.isTypeClothe() || item22.template.isTypeWeapon() || item22.template.isTypeAdorn())) {
                                for (int j = 0; j < item22.options.size(); j++) {
                                    if (item22.options.get(j).optionTemplate.id == 156 && item22.options.get(j + 1).optionTemplate.id == 157) {
                                        nguong = item22.options.get(j).param;
                                        cap1 = item22.options.get(j + 1).param;
                                        if (nguong > cap1) {
                                            itemList.add(item22);
                                        }
                                    }
                                }
                            }

                        }
                        for (int i = 0; i < this.numberCellBag; i++) {
                            Item item3 = this.bag[i];
                            if (item3 != null) {
                                if (item3.template.id == ItemName.DA_CHINH_PHUC || item3.template.id == ItemName.DA_VO_THUONG || item3.template.id == ItemName.DA_PHUC_SINH) {
                                    itemList.add(item3);
                                }
                            }
                        }
                        Item itemThangCap = itemList.get(checkIndexUI);
                        int checkCap = checkThangCap(itemThangCap);
                        if (this.coin < NinjaUtils.xuThangCap[checkCap]) {
                            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.xuThangCap[checkCap]) + " xu.");
                            return;
                        }
                        if (this.user.gold < NinjaUtils.luongThangCap[checkCap]) {
                            serverDialog("Bạn không đủ " + String.format("%,d", NinjaUtils.luongThangCap[checkCap]) + " lượng.");
                            return;
                        }
                        int checkType = checkTypeItem(itemThangCap);
                        if (checkType == 1) {
                            if (this.getQuantityItemById(ItemName.DA_CHINH_PHUC) < NinjaUtils.quantity[checkCap]) {
                                serverDialog("Bạn không đủ " + NinjaUtils.quantity[checkCap] + " viên đá chinh phục.");
                                return;
                            }
                        } else if (checkType == 2) {
                            if (this.getQuantityItemById(ItemName.DA_VO_THUONG) < NinjaUtils.quantity[checkCap]) {
                                serverDialog("Bạn không đủ " + NinjaUtils.quantity[checkCap] + " viên đá vô thương.");
                                return;
                            }
                        } else {
                            if (this.getQuantityItemById(ItemName.DA_PHUC_SINH) < NinjaUtils.quantity[checkCap]) {
                                serverDialog("Bạn không đủ " + NinjaUtils.quantity[checkCap] + " viên đá phục sinh.");
                                return;
                            }
                        }
                        addGold(-NinjaUtils.luongThangCap[checkCap]);
                        addCoin(-NinjaUtils.xuThangCap[checkCap]);
                        int idDa;
                        if (checkType == 1) {
                            idDa = ItemName.DA_CHINH_PHUC;
                        } else if (checkType == 2) {
                            idDa = ItemName.DA_VO_THUONG;
                        } else {
                            idDa = ItemName.DA_PHUC_SINH;
                        }
                        removeItemBags(idDa, NinjaUtils.quantity[checkCap]);
                        if (NinjaUtils.nextInt(0, 100) < NinjaUtils.percentThangCap[checkCap]) {
                            updateItemKhaiHoa(itemThangCap);
                            serverMessage("Thăng cấp thành công.");
                            setAbility();
                            setFashion();
                            if (checkThangNguong(itemThangCap) <= checkThangCap(itemThangCap)) {

                                setCommandBox(THANG_CAP);
                                getService().openUIThangCap();
                            }
                        } else {
                            serverMessage("Thăng cấp thất bại.");
                        }

                        setAbility();
                        setFashion();
                        getService().itemInfo(itemThangCap, (byte) 4, IndexUI);
                        break;

                    case CMDConfirmPopup.BUY_STORE_UPGRADE: // shop 16
                        StoreUpgradeManager.getInstance().buy((Char) this, IndexUI, 1);
                        break;


                }
            } finally {
                confirmPopup = null;
            }
        } catch (Exception e) {
            Log.logException("conFirmID err: ", Char.class, e);
        }
    }

    public void npcTienNu() {

        if (Event.getEvent() != null) {
            Event.getEvent().menu((Char) this);
        } else {
            menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
                getService().npcChat(NpcName.TIEN_NU, "Hãy đón chờ các sự kiện tiếp theo nhé.");
            }));
        }
    }

    public void npcBaRei() {
//        menus.add(new Menu(CMDMenu.EXECUTE, "Bán cá", () -> {
//            menus.clear();
//            menus.add(new Menu(CMDMenu.EXECUTE, "Xương cá", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(895);
//                InputDialog input = new InputDialog(CMDInputDialog.XUONG_CA, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Giao lục", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(896);
//                InputDialog input = new InputDialog(CMDInputDialog.GIAO_LUC, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Cá Koi", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(897);
//                InputDialog input = new InputDialog(CMDInputDialog.CA_KOI, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Cá đĩa lam", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(898);
//                InputDialog input = new InputDialog(CMDInputDialog.CA_DIA_LAM, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Buồm tím", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(899);
//                InputDialog input = new InputDialog(CMDInputDialog.BUOM_TIM, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Sao biển xanh", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(900);
//                InputDialog input = new InputDialog(CMDInputDialog.SAO_BIEN_XANH, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            menus.add(new Menu(CMDMenu.EXECUTE, "Cua hoàng đế", () -> {
//                ItemTemplate template = ItemManager.getInstance().getItemTemplate(901);
//                InputDialog input = new InputDialog(CMDInputDialog.CUA_HOANG_DE, template.name);
//                setInput(input);
//                getService().showInputDialog();
//            }));
//            getService().openUIMenu();
//        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            getService().npcChat(NpcName.BA_REI, "Làng chài là một trong những làng quan trọng nhất thế giới Ninja.");
        }));

    }

    public void npcMatsurugi() {
        if (taskMain != null && taskMain.taskId == TaskName.NV_TRUY_TIM_DIA_DO && taskMain.index == 1) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhận chìa khóa", () -> {
                int index = getIndexItemByIdInBag(ItemName.CHIA_KHOA_CO_QUAN2);
                if (index != -1) {
                    removeItem(index, 1, true);
                }
                if (getSlotNull() == 0) {
                    warningBagFull();
                    return;
                }
                Item chiaKhoa = ItemFactory.getInstance().newItem(ItemName.CHIA_KHOA_CO_QUAN2);
                chiaKhoa.isLock = true;
                chiaKhoa.setQuantity(1);
                addItemToBag(chiaKhoa);
            }));
        }
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {

        }));
    }

    public void npcShoba() {
        if (taskMain != null && taskMain.taskId == TaskName.NV_REN_LUYEN_Y_CHI && taskMain.index == 1) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhận cần câu", () -> {
                int index = getIndexItemByIdInBag(ItemName.CAN_CAU);
                if (index != -1) {
                    removeItem(index, 1, true);
                }
                if (getSlotNull() == 0) {
                    warningBagFull();
                    return;
                }
                Item canCau = ItemFactory.getInstance().newItem(ItemName.CAN_CAU);
                canCau.isLock = true;
                canCau.setQuantity(1);
                addItemToBag(canCau);
            }));
        }

        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {

        }));
    }

    public void npcAmeji() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Trang sức", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Liên", () -> {
                getService().openUI(StoreManager.TYPE_NECKLACE);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhẫn", () -> {
                getService().openUI(StoreManager.TYPE_RING);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Ngọc bội", () -> {
                getService().openUI(StoreManager.TYPE_PEARL);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Phù", () -> {
                getService().openUI(StoreManager.TYPE_SPELL);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhiệm vụ danh vọng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhận", () -> {
                if (this.level < 50) {
                    getService().npcChat(NpcName.AMEJI, "Trình độ từ cấp 50 trở lên mới được tham gia.");
                    return;
                }
                if (this.gloryTask == null) {
                    if (countGlory == 0) {
                        serverMessage("Con đã hoàn thành đủ số nhiệm vụ cho ngày hôm nay rồi");
                        return;
                    }
                    this.gloryTask = new GloryTask((Char) this);
                    this.countGlory--;
                    getService().showAlert("Nhiệm vụ", this.gloryTask.getTask());
                } else {
                    serverMessage("Con hãy hoàn thành nhiệm vụ được giao trước.");
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Trả", () -> {
                if (this.gloryTask != null) {
                    if (this.gloryTask.isComplete()) {
                        int equipType = this.gloryTask.getEquipType();
                        this.gloryTask = null;
                        int rand = equipType;// NinjaUtil.nextInt(10);
                        int point = 5;
                        String[] arrPointName = {"Nón", "Vũ khí", "Áo", "Liên", "Găng tay", "Nhẫn", "Quần", "Ngọc bội", "Giày", "Phù"};
                        switch (rand) {
                            case 0:
                                pointNon += point;
                                break;

                            case 1:
                                pointVuKhi += point;
                                break;

                            case 2:
                                pointAo += point;
                                break;

                            case 3:
                                pointLien += point;
                                break;

                            case 4:
                                pointGangTay += point;
                                break;

                            case 5:
                                pointNhan += point;
                                break;

                            case 7:
                                pointNgocBoi += point;
                                break;

                            case 6:
                                pointQuan += point;
                                break;

                            case 9:
                                pointPhu += point;
                                break;

                            case 8:
                                pointGiay += point;
                                break;
                        }
                        if (Event.isKoroKing()) {
                            KoroKing.addTrophy((Char) this);
                        }
                        getService().showAlert("Nhận được ", "- " + point + " điểm danh vọng " + arrPointName[rand]);
                    } else {
                        serverMessage("Con hãy hoàn thành nhiệm vụ được giao trước.");
                    }
                } else {
                    serverMessage("Hiện tại con chưa nhận nhiệm vụ nào");
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hủy", () -> {
                if (this.gloryTask != null) {
                    String title = this.gloryTask.getTaskTitle();
                    setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NHIEM_VU_DANH_VONG, "Bạn có muốn hủy nhiệm vụ " + title + " hay không?"));
                    getService().openUIConfirmID();
                } else {
                    serverMessage("Hiện tại con chưa nhận nhiệm vụ nào");
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhận Geningan", () -> {
                if (pointAo < 100 || pointGangTay < 100 || pointGiay < 100 || pointNgocBoi < 100 || pointLien < 100 || pointNhan < 100 || pointNon < 100 || pointPhu < 100 || pointQuan < 100 || pointVuKhi < 100) {
                    serverMessage("Không đủ 100 điểm danh vọng mỗi loại.");
                } else {
                    Item item = ItemFactory.getInstance().newItem(685);
                    item.isLock = true;
                    addItemToBag(item);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp", () -> {
                if (equipment[14] == null) {
                    serverMessage("Hãy sử dụng Nguyệt Nhãn để sử dụng chức năng này.");
                    return;
                }
                if (equipment[14].upgrade >= 10) {
                    serverMessage("Nguyệt Nhãn đã đạt cấp tối đa.");
                    return;
                }
                int[] costs = {500000, 750000, 1250000, 2000000, 3000000, 4500000, 6000000, 7750000, 10000000};
                int[] percents = {50, 35, 25, 20, 15, 12, 10, 8, 5};
                int index = equipment[14].upgrade - 1;
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NANG_MAT, "Bạn có muốn nâng cấp " + equipment[14].template.name + " với " + costs[index] + " xu hoặc yên với tỉ lệ thành công là " + percents[index] + "% không?"));
                getService().openUIConfirmID();

            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp VIP", () -> {
                if (equipment[14] == null) {
                    serverMessage("Hãy sử dụng Nguyệt Nhãn để sử dụng chức năng này.");
                    return;
                }
                if (equipment[14].upgrade >= 10) {
                    serverMessage("Nguyệt Nhãn đã đạt cấp tối đa.");
                    return;
                }
                int[] costs = {500000, 750000, 1250000, 2000000, 3000000, 4500000, 6000000, 7750000, 10000000};
                int[] costs2 = {50, 60, 85, 100, 150, 200, 300, 450, 600};
                int[] percents = {50, 35, 25, 20, 15, 12, 10, 8, 5};
                int index = equipment[14].upgrade - 1;
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NANG_MAT_VIP, "Bạn có muốn nâng cấp " + equipment[14].template.name + " với " + costs[index] + " xu hoặc yên và lượng " + costs2[index] + " với tỉ lệ thành công là " + (percents[index] * 2) + "% không?"));
                getService().openUIConfirmID();
            }));

            menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                String str = "";
                if (this.gloryTask != null) {
                    str = this.gloryTask.getTask();
                }
                String str2 = "- Có thể nhận thêm " + this.countGlory + "  nhiệm vụ trong ngày.\n";
                String str3 = "- Sử dụng danh vọng phù để tăng số lần làm nhiệm vụ trong ngày.\n";
                String str4 = "- Có thể sử dụng thêm " + this.countUseItemGlory + "  Danh vọng phù trong ngày.";
                String show = str + str2 + str3 + str4;
                getService().showAlert("Nhiệm vụ", show);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Mua điểm danh vọng", () -> {
            menus.clear();
            getService().npcChat(NpcName.ADMIN, "Con có muốn mua 100 điểm danh vọng mỗi loại với giá 5.000 lượng không?");
            menus.add(new Menu(CMDMenu.EXECUTE, "Đồng ý", () -> {
                if (!isHuman) {
                    serverDialog("Bạn đang trong chế độ thứ thân không thể dùng được chức năng này");
                    return;
                }
                if (this.user.gold < 5000) {
                    serverDialog("Bạn không có đủ 5.000 lượng");
                    return;
                }
                addGold(-5000);
                pointNon += 100;
                pointVuKhi += 100;
                pointAo += 100;
                pointLien += 100;
                pointGangTay += 100;
                pointNhan += 100;
                pointNgocBoi += 100;
                pointQuan += 100;
                pointPhu += 100;
                pointGiay += 100;
                serverDialog("Mua điểm danh vọng thành công");
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hủy bỏ", () -> {
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Con cần mua ngọc bội, nhẫn, dây chuyền, bùa hộ thân à?", "Con chọn loại trang sức nào?", "Trang sức không chỉ để ngắm, nó còn tăng sức mạnh cho con");
            getService().npcChat(NpcName.AMEJI, talk);
        }));
    }

    public int getPointDV(int type) {
        switch (type) {
            case 0:
                return pointNon;
            case 1:
                return pointVuKhi;
            case 2:
                return pointAo;
            case 3:
                return pointLien;
            case 4:
                return pointGangTay;
            case 5:
                return pointNhan;
            case 6:
                return pointQuan;
            case 7:
                return pointNgocBoi;
            case 8:
                return pointGiay;
            case 9:
                return pointPhu;
            default:
                return 0;
        }
    }

    public boolean avgPointDV(int point) {
        int avg = (pointNon + pointVuKhi + pointAo + pointLien + pointGangTay + pointNhan + pointQuan + pointNgocBoi + pointGiay + pointPhu) / 10;
        return point > avg;
    }

    public void npcTabemono() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Thức ăn (Yên)", () -> {
            getService().openUI(StoreManager.TYPE_FOOD_LOCK);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Thức ăn (Xu)", () -> {
            getService().openUI(StoreManager.TYPE_FOOD);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 1) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Thức ăn của ta là ngon nhất rồi!", "Ăn xong đảm bảo bạn sẽ quay lại lần sau.");
            getService().npcChat(NpcName.TABEMONO, talk);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Thiên Địa bảng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Đăng ký", () -> {
                ThienDiaManager thienDiaManager = ThienDiaManager.getInstance();
                ThienDiaData thienDiaData = thienDiaManager.getThienDiaData(this.level, this.classId);
                if (thienDiaData != null) {
                    try (Connection conn = DbManager.getConnection()) {
                        List<Ranking> rankingList = thienDiaData.getRankings();
                        Ranking r = thienDiaData.getRankingByPlayerID(this.id);
                        if (r != null) {
                            getService().npcChat(NpcName.TABEMONO, "Bạn đã đăng ký thành công rồi");
                            return;
                        }
                        thienDiaManager.removeRankingByID(this.id);
                        int rankAt = rankingList.size() + 1;
                        if (rankAt > 1000) {
                            rankAt = -1;
                        }
                        ResultSet rs = null;
                        PreparedStatement ps = null;
                        int rankingID = 0;
                        ps = conn.prepareStatement("INSERT INTO `ranking_list`(`player_id`, `match_id`, `rank_at`, `server_id`) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        try {
                            ps.setInt(1, this.id);
                            ps.setInt(2, thienDiaData.getId());
                            ps.setInt(3, rankAt);
                            ps.setInt(4, Config.getInstance().getServerID());
                            ps.executeUpdate();
                            rs = ps.getGeneratedKeys();
                            if (rs.next()) {
                                rankingID = rs.getInt(1);
                            }
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            ps.close();
                        }
                        Ranking ranking = Ranking.builder().id(rankingID).playerId(this.id).name(this.name).ranked(rankAt).build();
                        thienDiaData.addRanking(ranking);
                        getService().npcChat(NpcName.TABEMONO, "Bạn đã đăng ký thành công rồi");
                    } catch (SQLException ex) {
                        Log.logException("Đăng ký TDB err: ", Char.class, ex);
                    }
                } else {
                    getService().npcChat(NpcName.TABEMONO, "Không có bảng nào phù hợp với bạn");
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Chinh phục", () -> {
                ThienDiaData thienDiaData = ThienDiaManager.getInstance().getThienDiaData(this.level, this.classId);
                List<Ranking> list = new ArrayList<>();
                if (thienDiaData != null) {
                    Ranking r = thienDiaData.getRankingByPlayerID(this.id);
                    if (r == null) {
                        getService().npcChat(NpcName.TABEMONO, "Bạn phải đăng ký mới có thể sử dụng chức năng này");
                        return;
                    }

                    if (isHuman && clone != null && clone.isNhanBan && !clone.isDead) { // fix thiên địa bảng đánh trước time
                        return;
                    }

                    List<Ranking> rankingList = thienDiaData.getRankings();
                    int rankAt = (r.getRanked() == -1) ? rankingList.size() : r.getRanked();
                    int start = rankAt - 10;
                    if (rankAt > rankingList.size()) {
                        rankAt = rankingList.size();
                    }
                    if (start < 0) {
                        start = 0;
                        rankAt = rankingList.size() + 1;
                    }
                    for (int i = start; i < rankAt - 1; i++) {
                        Ranking bot = rankingList.get(i);
                        bot.setStt(bot.isFighting() ? "đang đấu" : "có thể thách đấu");
                        list.add(bot);
                    }
                    getService().doShowRankedListUI(list);
                } else {
                    getService().npcChat(NpcName.TABEMONO, "Không có bảng nào phù hợp với bạn");
                }
            }));
            ThienDiaManager thienDiaManager = ThienDiaManager.getInstance();
            List<ThienDiaData> list = thienDiaManager.getList();
            for (ThienDiaData thienDiaData : list) {
                menus.add(new Menu(CMDMenu.EXECUTE, thienDiaData.getName(), () -> {
                    StringBuilder sb = new StringBuilder();
                    List<Ranking> rankingList = thienDiaData.getRankings();
                    int length = 10;
                    if (rankingList.size() < length) {
                        length = rankingList.size();
                    }
                    for (int i = 0; i < length; i++) {
                        Ranking ranking = rankingList.get(i);
                        sb.append(String.format("%d. ", ranking.getRanked())).append(ranking.getName()).append("\n");
                    }
                    getService().showAlert(thienDiaData.getName(), sb.toString());
                }));
            }
            getService().openUIMenu();
        }));
    }

    public void npcKiriko() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Dược phẩm (Yên)", () -> {
            getService().openUI(StoreManager.TYPE_POTION_LOCK);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Dược phẩm (Xu)", () -> {
            getService().openUI(StoreManager.TYPE_POTION);
        }));
//        if (Event.isKoroKing() || Event.isTrungThu() || true) {
//            menus.add(new Menu(CMDMenu.EXECUTE, "Hoa phục sinh", () -> {
//                menus.clear();
//                menus.add(new Menu(CMDMenu.EXECUTE, "Hoa thiên diệu", () -> {
//                    Event.getEvent().action(this, TrungThu.HOA_PHUC_SINH, ItemName.HOA_THIEN_DIEU);
//                }));
//                menus.add(new Menu(CMDMenu.EXECUTE, "Hoa dạ yến", () -> {
//                    Event.getEvent().action(this, TrungThu.HOA_PHUC_SINH, ItemName.HOA_DA_YEN);
//                }));
//                getService().openUIMenu();
//            }));
//        }
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 0) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Đi đường cần mang ít dược phẩm", "Không mang theo HP, Hp bên mình, con sẽ gặp nguy hiểm.");
            getService().npcChat((short) 3, talk);
        }));
    }

    public void loadDisplay() {
        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("SELECT `players`.`name`, `players`.`gender`, `players`.`class`, `players`.`last_logout_time`, `players`.`head`, `players`.`head2`, `players`.`body`, `players`.`weapon`, `players`.`leg`, `players`.`online`, CAST(JSON_EXTRACT(data, \"$.exp\") AS INT) AS `exp` FROM `players` WHERE `players`.`id` = ?");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
            try {
                if (rs.next()) {
                    loadDisplay(rs);
                }
            } finally {
                rs.close();
                ps.close();
            }
        } catch (Exception ex) {
            Log.logException("load display err: ", Char.class, ex);
        }
    }

    public void loadDisplay(ResultSet rs) {
        try {
//            String name = "";
//            if(this.user.SVIP){
//                name = "[SVIP] "+rs.getString("name");
//                this.name = name;
//            }else{
//                this.name = rs.getString("name");
//            }
            this.name = rs.getString("name");
            this.gender = rs.getByte("gender");
            this.classId = rs.getByte("class");
            this.online = rs.getBoolean("online");
            this.lastLogoutTime = rs.getLong("last_logout_time");
            Clazz clazz = GameData.getInstance().findClass(classId);
            this.school = clazz.getName();
            this.original_head = this.head = rs.getShort("head");
            this.weapon = -1;
            this.body = -1;
            this.leg = -1;
            this.exp = rs.getLong("exp");
            this.head = rs.getShort("head2");
            if (this.head == -1) {
                this.head = this.original_head;
            }
            this.weapon = rs.getShort("weapon");
            this.body = rs.getShort("body");
            this.leg = rs.getShort("leg");
            if (this.exp > Server.EXP_MAX) {
                this.exp = Server.EXP_MAX;
            }
            this.level = NinjaUtils.getLevel(this.exp);
        } catch (SQLException ex) {
            Log.logException("load display err(1): ", Char.class, ex);
        }
    }

    // xóa item khi vào game
    boolean isInList(int value) {
        int[] listDell = new int[]{};
        for (int i : listDell) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean load() {
        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(SQLStatement.LOAD_PLAYER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();
            try {
                if (!rs.first()) {
                    return false;
                }
                this.topThanNong = rs.getInt("thannong");
                this.invite_player = rs.getInt("invite_player");
                this.topvxmm = rs.getInt("topvxmm");
                this.topBoss = rs.getInt("topBoss");
                this.topvxmmLuong = rs.getInt("toplukygold");
                this.coin = rs.getLong("xu");
                if (this.user != null) {
                    this.user.gold = rs.getInt("luong");
                }

                this.coinInBox = rs.getLong("xuInBox");
                this.yen = rs.getLong("yen");
                this.rewardPB = rs.getInt("rewardPB"); // fix hd6x
                this.passProtech = rs.getInt("passProtect"); // fix hd6x
                this.typeProtect = rs.getInt("typeProtect"); // fix hd6x
                JSONArray ja = (JSONArray) JSONValue.parse(rs.getString("map"));
                this.mapId = Short.parseShort(ja.get(0).toString());
                this.x = Short.parseShort(ja.get(1).toString());
                this.y = Short.parseShort(ja.get(2).toString());
                this.saveCoordinate = rs.getShort("saveCoordinate");
                if (this.saveCoordinate == 162) {
                    this.saveCoordinate = 22;
                }
                this.potentialPoint = rs.getShort("point");
                this.skillPoint = rs.getShort("spoint");

                JSONArray jarzz = (JSONArray) JSONValue.parse(rs.getString("giftOnline")); /// điểm danh tân thủ
                for (int i = 0; i < 21; i++) {
                    if (jarzz.get(i) != null) {
                        this.giftOnline[i] = Integer.parseInt(jarzz.get(i).toString());
                    }
                }

                JSONArray jarzzz = (JSONArray) JSONValue.parse(rs.getString("giftTongnap")); /// điểm danh tân thủ
                for (int i = 0; i < 10; i++) {
                    if (jarzzz.get(i) != null) {
                        this.giftTongnap[i] = Integer.parseInt(jarzzz.get(i).toString());
                    }
                }

                JSONArray jArr = (JSONArray) JSONValue.parse(rs.getString("potential"));
                int len = jArr.size();
                this.potential = new int[4];
                for (int i = 0; i < 4; i++) {
                    if (jArr.get(i) != null) {
                        this.potential[i] = Integer.parseInt(jArr.get(i).toString());
                    } else {
                        this.potential[i] = 5;
                    }
                }
                jArr = (JSONArray) JSONValue.parse(rs.getString("skill"));
                len = jArr.size();
                this.vSkill = new Vector<>();
                this.vSupportSkill = new Vector<>();
                this.vSkillFight = new Vector<>();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = (JSONObject) jArr.get(i);
                    int skillId = Integer.parseInt(obj.get("id").toString());
                    int point = Integer.parseInt(obj.get("point").toString());
                    Skill skill = GameData.getInstance().getSkill(this.classId, skillId, point);
                    // System.out.println("skillId: " + skillId + " point: " + point);
                    if (skill.template.type == Skill.SKILL_AUTO_USE) {
                        this.vSupportSkill.add(skill);
                    } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK || skill.template.type == Skill.SKILL_CLICK_LIVE || skill.template.type == Skill.SKILL_CLICK_USE_BUFF || skill.template.type == Skill.SKILL_CLICK_NPC) && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 && skill.point > 0))) {

                        this.vSkillFight.add(skill);
                    }
                    this.vSkill.add(skill);
                }
                this.mount = new Mount[5];
                JSONArray jso = (JSONArray) JSONValue.parse(rs.getString("mount"));
                if (jso != null) {
                    int size = jso.size();
                    for (int i = 0; i < size; i++) {
                        Mount mount = new Mount((JSONObject) jso.get(i));
                        this.mount[mount.template.type - 29] = mount;
                    }
                    if (this.mount[4] != null) {
                        this.mount[4].fixMount();
                    }
                }
                this.bijuu = new Item[5];
                JSONArray jBijuu = (JSONArray) JSONValue.parse(rs.getString("bijuu"));
                if (jBijuu != null) {
                    int size = jBijuu.size();
                    for (int i = 0; i < size; i++) {
                        Item bi = new Item((JSONObject) jBijuu.get(i));
                        int index = 4;
                        if (bi.template.isTypeEquipmentBijuu()) {
                            index = bi.template.type - 35;
                        }
                        this.bijuu[index] = bi;
                    }
                }
                JSONArray effects = (JSONArray) JSONValue.parse(rs.getString("effect"));
                int size2 = effects.size();
                for (int i = 0; i < size2; i++) {
                    try {
                        JSONObject obj = (JSONObject) effects.get(i);
                        int id = Integer.parseInt(obj.get("id").toString());
                        int param = Integer.parseInt(obj.get("param").toString());
                        long startAt = 0;
                        long endAt = 0;
                        long timeLength = 0;
                        if (obj.containsKey("timeStart")) {
                            int timeStart = Integer.parseInt(obj.get("timeStart").toString());
                            int timeLength2 = Integer.parseInt(obj.get("timeLength").toString());
                            startAt = System.currentTimeMillis();
                            endAt = startAt + ((timeLength2 - timeStart) * 1000L);

                        } else {
                            timeLength = Long.parseLong(obj.get("time_length").toString());

                            startAt = Long.parseLong(obj.get("start_at").toString());
                            endAt = Long.parseLong(obj.get("end_at").toString());

                            long timeRemaining = timeLength;
                            endAt = System.currentTimeMillis() + timeRemaining;
                            startAt = System.currentTimeMillis();
                        }
                        if (System.currentTimeMillis() < endAt) {
                            Effect eff = new Effect(id, startAt, endAt, param);
                            em.effect(eff, true);
                            em.add(eff);
                        }
                    } catch (Exception e) {
                        Log.logException("Load player err(1): ", Char.class, e);
                    }
                }
                this.equipment = new Equip[16];
                jso = (JSONArray) JSONValue.parse(rs.getString("equiped"));
                if (jso != null) {
                    int size = jso.size();
                    for (int i = 0; i < size; i++) {
                        Equip equip = new Equip((JSONObject) jso.get(i));
                        if (equip.isExpired() || equip.isRemoveItem() || isInList(equip.template.id)) { /// fix xóa item
                            continue;
                        }
                        this.equipment[equip.template.type] = equip;
                    }
                }
                this.fashion = new Equip[16];
                jso = (JSONArray) JSONValue.parse(rs.getString("fashion"));
                if (jso != null) {
                    int size = jso.size();
                    for (int i = 0; i < size; i++) {
                        Equip equip = new Equip((JSONObject) jso.get(i));
                        if (equip.isExpired() || equip.isRemoveItem()) {
                            continue;
                        }
                        this.fashion[equip.template.type] = equip;
                    }
                }
                int clan = rs.getInt("clan");
                if (clan != 0) {
                    Optional<Clan> g = Clan.getClanDAO().get(clan);
                    if (g != null && g.isPresent()) {
                        this.clan = g.get();
                        Member mem = this.clan.getMemberByName(this.name);
                        if (mem == null) {
                            this.clan = null;
                        }
                    }
                }
                this.numberCellBag = rs.getByte("numberCellBag");
                this.numberCellBox = rs.getByte("numberCellBox");
                JSONObject json = (JSONObject) JSONValue.parse(rs.getString("data"));
                ParseData parse = new ParseData(json);
                this.expDown = parse.getLong("expDown");
                this.hieuChien = parse.getByte("hieuChien");
                this.countFinishDay = parse.getByte("countFinishDay");
                if (json.containsKey("countDoneTaskDay")) {
                    this.countDoneTaskDay = parse.getByte("countDoneTaskDay");
                } else {
                    this.countDoneTaskDay = 0;
                }

                if (parse.containsKey("rank")) {
                    this.huyHieu = parse.getInt("rank");
                } else {
                    this.huyHieu = -1;
                }
                if (parse.containsKey("timeCountDown")) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - this.lastLogoutTime;
                    int timeCountDown1 = parse.getInt("timeCountDown");
                    timeCountDown1 -= (int) (elapsedTime / 1000);
                    if (timeCountDown1 < 0) {
                        timeCountDown1 = 0; // Đảm bảo timeCountDown không nhỏ hơn 0
                    }
                    this.timeCountDown = timeCountDown1;
                } else {
                    this.timeCountDown = 0;
                }
                if (parse.containsKey("countLoopBoss")) {
                    this.countLoopBoss = parse.getInt("countLoopBoss");
                } else {
                    this.countLoopBoss = 2;
                }
                if (parse.containsKey("countUseItemGlory")) {
                    this.countUseItemGlory = parse.getInt("countUseItemGlory");
                } else {
                    this.countUseItemGlory = 6;
                }
                if (parse.containsKey("count_arena")) {
                    this.countArenaT = parse.getInt("count_arena");
                } else {
                    this.countArenaT = 1;
                }
                if (parse.containsKey("war")) {
                    JSONObject obj = (JSONObject) JSONValue.parse(parse.getString("war"));
                    this.warPoint = Integer.parseInt(obj.get("point").toString());
                    this.time = Long.parseLong(obj.get("time").toString());
                    this.faction = Byte.parseByte(obj.get("faction").toString());
                    if (obj.get("rewarded") != null) {
                        this.isRewarded = Boolean.parseBoolean(obj.get("rewarded").toString());
                    } else {
                        this.isRewarded = false;
                    }
                    if (obj.get("kill") != null) {
                        this.nKill = Integer.parseInt(obj.get("kill").toString());
                    } else {
                        this.nKill = 0;
                    }
                    if (obj.get("dead") != null) {
                        this.nDead = Integer.parseInt(obj.get("dead").toString());
                    } else {
                        this.nDead = 0;
                    }

                } else {
                    this.warPoint = 0;
                    this.time = -1;
                    this.faction = -1;
                }
                if (parse.containsKey("countUseItemDungeo")) {
                    this.countUseItemDungeo = parse.getInt("countUseItemDungeo");
                } else {
                    this.countUseItemDungeo = 1;
                }
                if (parse.containsKey("countUseItemBeast")) {
                    this.countUseItemBeast = parse.getInt("countUseItemBeast");
                } else {
                    this.countUseItemBeast = 1;
                }
                if (parse.containsKey("countGlory")) {
                    this.countGlory = parse.getInt("countGlory");
                } else {
                    this.countGlory = 20;
                }
                if (parse.containsKey("sevenbeasts_id")) {
                    sevenBeastsID = parse.getInt("sevenbeasts_id");
                } else {
                    sevenBeastsID = -1;
                }
                if (parse.containsKey("not_received_exp")) {
                    this.notReceivedExp = parse.getBoolean("not_received_exp");
                } else {
                    this.notReceivedExp = false;
                }
                if (parse.containsKey("type_sevenbeasts")) {
                    typeSevenBeasts = parse.getInt("type_sevenbeasts");
                } else {
                    typeSevenBeasts = -1;
                }

                if (parse.containsKey("countEnterFujukaSanctuary")) {
                    countEnterFujukaSanctuary = parse.getInt("countEnterFujukaSanctuary");
                } else {
                    countEnterFujukaSanctuary = 1;
                }


                long lastLoginTime = rs.getLong("last_login_time");
                this.lastLoginTime1 = rs.getLong("last_login_time");
                Date now = new Date();
                Date date = NinjaUtils.getDate(lastLoginTime);
                boolean isNewDay = !DateUtils.isSameDay(date, now);
                if (isNewDay) {
                    this.countGlory = 20;
                    this.countUseItemGlory = 6;
                    this.countUseItemDungeo = 1;
                    this.countFinishDay = 20;
                    this.countDoneTaskDay = 0;
                    this.countLoopBoss = 2;
                    this.countUseItemBeast = 2;
                    this.warPoint = 0;
                    this.time = -1;
                    this.faction = -1;
                    this.updateHPMount(-200);
                    this.countArenaT = 1;
                    this.sevenBeastsID = -1;
                    this.typeSevenBeasts = -1;
                    this.countEnterFujukaSanctuary = 1;

                }


                if (this.user != null) {
                    Date date2 = NinjaUtils.getDate(this.user.lastLoginTime);
                    boolean isNewDayUser = !DateUtils.isSameDay(date2, now);
                    if (isNewDayUser) {
                        this.user.dayLogin++; /// điểm danh tân thủ
                    }
                }
                this.countPB = parse.getByte("countPB");
                if (this.countPB == 0) {
                    if (isNewDay) {
                        this.countPB = 1;
                        this.pointPB = 0;
                        this.receivedRewardPB = false;
                    } else {
                        int dungeonId = -1;
                        if (parse.containsKey("dungeonId")) {
                            dungeonId = parse.getInt("dungeonId");
                        }
                        Dungeon dungeon = Dungeon.findDungeonById(dungeonId);
                        if (dungeon != null) {
                            this.addWorld(dungeon);
                            this.pointPB = parse.getInt("pointPB");
                            this.receivedRewardPB = parse.getBoolean("receivedRewardPB");
                        } else {
//                            this.countPB = 1;  // fix bug hd liên tục
                            this.pointPB = 0;
                            this.receivedRewardPB = false;
                        }
                    }
                }
                this.limitKyNangSo = parse.getByte("limitKyNangSo");
                this.limitTiemNangSo = parse.getByte("limitTiemNangSo");
                this.reward = new boolean[5];
                if (parse.containsKey("reward")) {
                    JSONArray jA = parse.getJSONArray("reward");
                    for (int i = 0; i < 5; i++) {
                        if (jA.get(i) != null) {
                            this.reward[i] = Boolean.parseBoolean(jA.get(i).toString());
                        }
                    }
                }
                if (parse.containsKey("levelUpTime")) {
                    this.levelUpTime = parse.getLong("levelUpTime");
                } else {
                    this.levelUpTime = (new Date()).getTime();
                }
                if (parse.containsKey("tayTiemNang")) {
                    this.tayTiemNang = parse.getInt("tayTiemNang");
                } else {
                    this.tayTiemNang = 0;
                }
                if (parse.containsKey("tayKyNang")) {
                    this.tayKyNang = parse.getInt("tayKyNang");
                } else {
                    this.tayKyNang = 0;
                }
                if (parse.containsKey("limitBangHoa")) {
                    this.limitBangHoa = parse.getInt("limitBangHoa");
                } else {
                    this.limitBangHoa = 0;
                }
                if (parse.containsKey("limitPhongLoi")) {
                    this.limitPhongLoi = parse.getInt("limitPhongLoi");
                } else {
                    this.limitPhongLoi = 0;
                }
                if (parse.containsKey("numberUseExpanedBag")) {
                    this.numberUseExpanedBag = parse.getByte("numberUseExpanedBag");
                } else {
                    this.numberUseExpanedBag = 0;
                }

                if (parse.containsKey("gloryTask")) {
                    ParseData p = parse.getParseData("gloryTask");
                    int type = p.getInt("type");
                    int quantity = p.getInt("quantity");
                    int progress = p.getInt("progress");
                    int requireUseEquip = p.getInt("requireUseEquip");
                    this.gloryTask = new GloryTask((Char) this, type, quantity, progress, requireUseEquip);
                }
                this.taskOrders = new ArrayList<>();
                if (parse.containsKey("taskOrder")) {
                    ParseData[] taskOrders = parse.getArrayParseData("taskOrder");
                    for (ParseData task : taskOrders) {
                        this.taskOrders.add(new TaskOrder((Char) this, task.getByte("taskId"), task.getInt("count"), task.getInt("maxCount"), task.getInt("killId"), task.getInt("mapId")));
                    }
                }
                if (parse.containsKey("lastTimeOutClan")) {
                    this.lastTimeOutClan = parse.getLong("lastTimeOutClan");
                } else {
                    this.lastTimeOutClan = 0;
                }
                if (parse.containsKey("coinMax")) {
                    this.coinMax = parse.getLong("coinMax");
                    if (this.coinMax == 1500000000) {
                        this.coinMax = 2000000000;
                    }
                } else {
                    this.coinMax = 2000000000L;
                }
                if (parse.containsKey("maskId")) {
                    int maskId = parse.getInt("maskId");
                    this.setMaskId(maskId);
                } else {
                    this.setMaskId(-1);
                }
                if (parse.containsKey("pointAo")) {
                    this.pointAo = parse.getInt("pointAo");
                } else {
                    this.pointAo = 0;
                }
                if (parse.containsKey("pointGangTay")) {
                    this.pointGangTay = parse.getInt("pointGangTay");
                } else {
                    this.pointGangTay = 0;
                }
                if (parse.containsKey("pointGiay")) {
                    this.pointGiay = parse.getInt("pointGiay");
                } else {
                    this.pointGiay = 0;
                }
                if (parse.containsKey("pointLien")) {
                    this.pointLien = parse.getInt("pointLien");
                } else {
                    this.pointLien = 0;
                }
                if (parse.containsKey("pointNgocBoi")) {
                    this.pointNgocBoi = parse.getInt("pointNgocBoi");
                } else {
                    this.pointNgocBoi = 0;
                }
                if (parse.containsKey("pointNhan")) {
                    this.pointNhan = parse.getInt("pointNhan");
                } else {
                    this.pointNhan = 0;
                }
                if (parse.containsKey("pointNon")) {
                    this.pointNon = parse.getInt("pointNon");
                } else {
                    this.pointNon = 0;
                }
                if (parse.containsKey("pointPhu")) {
                    this.pointPhu = parse.getInt("pointPhu");
                } else {
                    this.pointPhu = 0;
                }
                if (parse.containsKey("pointQuan")) {
                    this.pointQuan = parse.getInt("pointQuan");
                } else {
                    this.pointQuan = 0;
                }
                if (parse.containsKey("pointTinhTu")) {
                    this.pointTinhTu = parse.getInt("pointTinhTu");
                } else {
                    this.pointTinhTu = 0;
                }
                if (parse.containsKey("pointUyDanh")) {
                    this.pointUyDanh = parse.getInt("pointUyDanh");
                } else {
                    this.pointUyDanh = 0;
                }
                if (parse.containsKey("pointVuKhi")) {
                    this.pointVuKhi = parse.getInt("pointVuKhi");
                } else {
                    this.pointVuKhi = 0;
                }
                this.bag = new Item[this.numberCellBag];
                jso = (JSONArray) JSONValue.parse(rs.getString("bag"));
                if (jso != null) {
                    int size = jso.size();
                    for (int i = 0; i < size; i++) {
                        Item item = new Item((JSONObject) jso.get(i));
                        if (item == null || !item.has() || item.isExpired() || item.isRemoveItem(true) || isInList(item.template.id)) { /// fix xóa item
                            continue;
                        }
                        if (item.index < 0 || item.index >= this.bag.length) {
                            // Nếu item.index không hợp lệ, bỏ qua phần tử này
                            continue;
                        }
                        this.bag[item.index] = item;
                    }
                }
                this.box = new Item[this.numberCellBox];
                jso = (JSONArray) JSONValue.parse(rs.getString("box"));
                if (jso != null) {
                    int size = jso.size();
                    for (int i = 0; i < size; i++) {
                        Item item = new Item((JSONObject) jso.get(i));
                        if (item == null || !item.has() || item.isExpired() || item.isRemoveItem(true) || isInList(item.template.id)) { /// fix xóa item
                            continue;
                        }
                        if (item.index < 0 || item.index >= this.box.length) {
                            // Nếu item.index không hợp lệ, bỏ qua phần tử này
                            continue;
                        }
                        this.box[item.index] = item;
                    }
                }
                ArrayList<Item> maskBox = new ArrayList<>();
                Object obj2 = rs.getObject("mask_box");
                if (obj2 != null) {
                    JSONArray jA = (JSONArray) JSONValue.parse((String) obj2);
                    for (int i = 0; i < jA.size(); i++) {
                        JSONObject jO = (JSONObject) jA.get(i);
                        Item item = new Item(jO);
                        maskBox.add(item);
                        if (item.id == this.getMaskId()) {
                            this.setMask(item);
                        }
                    }
                }
                this.setMaskBox(maskBox);
                ArrayList<Item> collectionBox = new ArrayList<>();
                obj2 = rs.getObject("collection_box");
                if (obj2 != null) {
                    JSONArray jA = (JSONArray) JSONValue.parse((String) obj2);
                    for (int i = 0; i < jA.size(); i++) {
                        JSONObject jO = (JSONObject) jA.get(i);
                        Item item = new Item(jO);
                        collectionBox.add(item);
                    }
                }
                this.setCollectionBox(collectionBox);
                JSONArray j = (JSONArray) JSONValue.parse(rs.getString("onOSkill"));
                if (j != null) {
                    this.onOSkill = new byte[j.size()];
                    for (int t = 0; t < this.onOSkill.length; t++) {
                        if (t < j.size()) {
                            this.onOSkill[t] = Byte.parseByte(j.get(t).toString());
                        }
                    }
                } else {
                    this.onOSkill = new byte[0];
                }
                j = (JSONArray) JSONValue.parse(rs.getString("onCSkill"));
                this.onCSkill = new byte[]{-1, -1, -1, -1, -1};
                if (j != null) {
                    for (int t = 0; t < this.onCSkill.length; t++) {
                        if (t < j.size()) {
                            this.onCSkill[t] = Byte.parseByte(j.get(t).toString());
                        }
                    }
                }
                j = (JSONArray) JSONValue.parse(rs.getString("onKSkill"));
                this.onKSkill = new byte[]{-1, -1, -1};
                if (j != null) {
                    for (int t = 0; t < this.onKSkill.length; t++) {
                        if (t < j.size()) {
                            this.onKSkill[t] = Byte.parseByte(j.get(t).toString());
                        }
                    }
                }
                j = (JSONArray) JSONValue.parse(rs.getString("quatichluy"));
                this.quaTichLuy = new int[]{-1, -1, -1, -1, -1, -1};
                if (j != null) {
                    for (int v = 0; v < this.quaTichLuy.length; v++) {
                        if (v < j.size()) {
                            this.quaTichLuy[v] = Integer.parseInt(j.get(v).toString());
                        }
                    }
                }
                JSONArray friends = (JSONArray) JSONValue.parse(rs.getString("friends"));
                int size = friends.size();
                this.friends = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    Friend fr = new Friend((JSONObject) friends.get(i));
                    this.friends.put(fr.name, fr);
                }

                JSONArray enemies = (JSONArray) JSONValue.parse(rs.getString("enemies"));
                size = enemies.size();
                this.enemies = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    Friend fr = new Friend((JSONObject) enemies.get(i));
                    this.enemies.put(fr.name, fr);
                }
                this.timeOnline = rs.getInt("timeOnline"); /// điểm danh tân thủ
                this.taskId = rs.getShort("taskId");
                String tt = rs.getString("task");
                if (tt != null && !tt.equals("")) {
                    ParseData pd = new ParseData((JSONObject) JSONValue.parse(tt));
                    short taskID = pd.getShort("id");
                    byte taskIndex = pd.getByte("index");
                    short taskCount = pd.getShort("count");
                    TaskTemplate task = Task.getTaskTemplate(taskID);
                    if (task != null) {
                        this.taskMain = TaskFactory.getInstance().createTask(taskID, taskIndex, taskCount);
                    }
                }
                this.setLoadFinish(true);
                Object obj = rs.getObject("message");
                if (obj != null) {
                    this.message = obj.toString();
                }

                initListCanEnterMap();
                if (Event.getEvent() != null) {
                    loadEventPoint();
                }
                return true;
            } finally {
                rs.close();
                stmt.close();
            }
        } catch (Exception ex) {
            Log.logException("load data player lỗi: ", Char.class, ex);

        }
        return false;
    }


    public void loadEventPoint() {
        try (Connection conn = DbManager.getConnection()) {
            Event event = Event.getEvent();
            EventPoint eventPoint = event.findEventPointByPlayerID(this.id);
            if (eventPoint == null) {
                eventPoint = event.createEventPoint();
                eventPoint.setPlayerID(this.id);
                eventPoint.setPlayerName(this.name);

                Gson g = new Gson();
                PreparedStatement ps = conn.prepareStatement(SQLStatement.INSERT_EVENT_POINT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, event.getId());
                ps.setInt(2, this.id);
                ps.setString(3, g.toJson(eventPoint.getPoints()));
                ps.setInt(4, Config.getInstance().getServerID());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    eventPoint.setId(generatedKeys.getInt(1));
                }
                ps.close();
                event.addEventPoint(eventPoint);
            }
            setEventPoint(eventPoint);
        } catch (SQLException ex) {
            Log.logException("loadEventPoint lỗi: ", Char.class, ex);

        }
    }

    public void updateEventPoint() {
        try (Connection conn = DbManager.getConnection()) {

            EventPoint eventPoint = getEventPoint();
            if (eventPoint != null) {
                Gson g = new Gson();

                PreparedStatement stmt = conn.prepareStatement(SQLStatement.UPDATE_EVENT_POINT);
                try {
                    stmt.setString(1, g.toJson(eventPoint.getPoints()));
                    stmt.setInt(2, eventPoint.getId());
                    stmt.executeUpdate();
                } finally {
                    stmt.close();
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Char.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void npcKagai() {
        if (this.mapId == 117 || this.mapId == 98 || this.mapId == 104) {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Rời khỏi nơi này", () -> {
                short[] xy = NinjaUtils.getXY(mapBeforeEnterPB);
                setXY(xy[0], xy[1]);
                changeMap(mapBeforeEnterPB);

            }));
            if (this.mapId == 117) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Đặt cược", () -> {
                    InputDialog input = new InputDialog(CMDInputDialog.DAT_CUOC_GTC, "Nhập số tiền cược");
                    setInput(input);
                    getService().showInputDialog();
                }));
            } else {
                menus.add(new Menu(CMDMenu.EXECUTE, "Bảng xếp hạng", () -> {
                    if (this.zone.map.clanWar != null) {
                        this.zone.map.clanWar.viewTop((Char) this);
                    }
                }));
            }
            getService().openUIMenu();
        } else {
            menus.add(new Menu(CMDMenu.EXECUTE, "Chiến trường kẹo", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
                    if (!user.isTien()) {
                        serverDialog("Tính năng đang được phát triển!");
                        return;
                    }
                    CandyBattlefield candyBattlefield = MapManager.getInstance().getCandyBattlefield();
                    if (candyBattlefield == null) {
                        setInput(new InputDialog(CMDInputDialog.EXECUTE, "Số người chơi từ 10 đến 40 (Số chẵn)", () -> {
                            try {
                                int n = input.intValue();
                                if (n < 10 || n > 40) {
                                    serverDialog("Số lượng phải từ 10 đến 20 người chơi!");
                                    return;
                                }
                                if (n % 2 != 0) {
                                    serverDialog("Số lượng là số chẵn!");
                                    return;
                                }
                                CandyBattlefield candy = new CandyBattlefield(1800);
                                addWorld(candy);
                                candy.join((Char) this);
                                MapManager.getInstance().setCandyBattlefield(candy);
                                GlobalService.getInstance().chat("Chiến trường kẹo", "Chiến trường kẹo đã mở cửa, hãy gặp Kagai ở các trường để tham gia!");
                            } catch (Exception e) {
                                serverDialog(e.getLocalizedMessage());
                            }
                        }));
                        getService().showInputDialog();
                    } else {
                        if (candyBattlefield.isOpened()) {
                            serverDialog("Chiến trường đã bắt đầu! Vui lòng quay lại sau.");
                            return;
                        }
                        if (candyBattlefield.getNumberPlayer() > +candyBattlefield.getMaxPlayer()) {
                            serverDialog("Chiến trường đã đạt tối đa! Vui lòng quay lại sau.");
                            return;
                        }
                        addWorld(candyBattlefield);
                        candyBattlefield.join((Char) this);
                    }
                }));
                menus.add(new Menu(CMDMenu.CANCEL, "Hướng dẫn"));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Gia tộc chiến", () -> {

                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Thách đấu", () -> {
                    Clan clanManager = this.clan;
                    WarClan gtc = null;
                    if (this.gtcId != -1) {
                        if (WarClan.gtcs.containsKey(this.gtcId)) {
                            gtc = WarClan.gtcs.get(this.gtcId);
                        }
                    }
                    if (gtc == null) {
                        if (!this.isHuman) {
                            this.service.serverDialog("Chức năng này không dành cho phân thân!");
                            return;
                        }
                        if (this.clan == null) {
                            this.service.npcChat(NpcName.KAGAI, "Con chưa có Gia tộc.");
                            return;
                        }
                        if (this.clan.level < 25) {
                            this.service.npcChat(NpcName.KAGAI, "Gia tộc phải đạt cấp 25 trở lên mới có thể tham gia thách đấu.");
                            return;
                        }
                        if (this.clan.openGtc == 0) {
                            this.service.npcChat(NpcName.KAGAI, "Gia tộc của con đã hết lượt tham gia gia tộc chiến.");
                            return;
                        }
                        Member member = this.clan.getMemberByName(this.name);
                        if (member != null) {
                            int typeClan = member.getType();
                            if (typeClan != Clan.TYPE_TOCTRUONG) {
                                if (typeClan != Clan.TYPE_TOCTRUONG) {
                                    serverDialog("Bạn không phải tộc trưởng");
                                    return;
                                }
                            }
                        }
                        InputDialog input = new InputDialog(CMDInputDialog.INVITE_WAR_CLAN, "Nhập tên gia tộc đối thủ");
                        setInput(input);
                        getService().showInputDialog();
                    } else {
                        if (!isHuman) {
                            return;
                        }
                        if (this.pheGtc == -1) {
                            return;
                        }
                        this.mapBeforeEnterPB = this.mapId;
                        this.gtcId = clanManager.gtcID;
                        this.setTypePk((byte) this.pheGtc);
                        if (this.pheGtc == Char.PK_PHE1) {
                            short[] xy = NinjaUtils.getXY(98);
                            this.setXY(xy);
                            this.changeMap(98);
                        } else {
                            short[] xy = NinjaUtils.getXY(104);
                            this.setXY(xy);
                            this.changeMap(104);
                        }
                    }


                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Liên Server", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.CANCEL, "1 ngày"));
                menus.add(new Menu(CMDMenu.CANCEL, "3 ngày"));
                menus.add(new Menu(CMDMenu.CANCEL, "Hướng dẫn"));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Tinh luyện", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Luyện tinh thạch", () -> {
                    getService().openUI((byte) 43);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Tinh luyện vật phẩm", () -> {
                    getService().openUI((byte) 44);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Dịch chuyển trang bị", () -> {
                    getService().openUI((byte) 45);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                    String guide = "- TINH LUYỆN TRANG BỊ" + "\n";
                    guide += "  + Để tinh luyện trang bị cần phải có Tử tinh thạch." + "\n";
                    guide += "  + Độ tinh luyện cấp 1, 2, 3 cần phải có Tử tinh thạch sơ cấp." + "\n";
                    guide += "  + Độ tinh luyện cấp 4, 5, 6 cần phải có Tử tinh thạch trung cấp." + "\n";
                    guide += "  + Độ tinh luyện cấp 7, 8, 9 cần phải có Tử tinh thạch cao cấp." + "\n";
                    guide += "- LUYỆN TỬ TINH THẠCH" + "\n";
                    guide += "  + Ghép 9 Tử tinh thạch sơ cấp sẽ nhận được 1 Tử tinh thạch trung cấp." + "\n";
                    guide += "  + Ghép 9 Tử tinh thạch trung cấp sẽ nhận được 1 Tử tinh thạch cao cấp." + "\n";
                    guide += "  + Ghép 3 Tử tinh thạch sơ cấp với Đá 11 sẽ nhận được 3 Tử tinh thạch trung cấp." + "\n";
                    guide += "  + Ghép 3 Tử tinh thạch trung cấp với Đá 12 sẽ nhận được 3 Tử tinh thạch cao cấp." + "\n";
                    guide += "- DỊCH CHUYỂN TRANG BỊ" + "\n";
                    guide += "  + Trang bị dịch chuyển phải là trang bị +12 trở lên." + "\n";
                    guide += "  + Trang bị sau khi dịch chuyển sẽ có thêm hai dòng thuộc tính để tinh luyện." + "\n";
                    guide += "  + Để dịch chuyển cần phải có đủ 20 chuyển tinh thạch." + "\n";
                    guide += "- THĂNG SAO THÚ CƯỠI" + "\n";
                    guide += "  + Thú cưỡi đạt level 100 người chơi có thể lựa chọn thăng cấp sao." + "\n";
                    guide += "  + Thăng sao thành công thú cưỡi sẽ trở về level 1 với tiềm năng tốt hơn." + "\n";
                    guide += "  + Cần sử dụng chuyển tinh thạch để tăng sao thú cưỡi." + "\n";
                    getService().showAlert("Hướng dẫn", guide);
                }));
                getService().openUIMenu();
            }));

        }

    }


    public void npcOkanechan() { ///

        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 3) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Ta là hiện thân của thần tài sẽ mang đến tài lộc cho mọi người.", "Online mỗi ngày tham gia các hoạt động để tích lũy điểm hoạt động con nhé.", "Con hãy chăm đánh quái, làm nhiệm vụ để có thêm nhiều yên hơn.");
            getService().npcChat(NpcName.OKANECHAN, talk);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Mã quà tặng", () -> {
            InputDialog input = new InputDialog(CMDInputDialog.MA_QUA_TANG, "Mã quà tặng");
            setInput(input);
            getService().showInputDialog();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Đổi lượng", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Đổi xu", () -> {
                setInput(new InputDialog(CMDInputDialog.EXECUTE, "Số lượng đổi(1 Lượng = 3000 xu)", () -> {

                    String luong = input.getText();
                    if (!NinjaUtils.CheckString(luong, "^[0-9]+$")) {
                        getService().npcChat(NpcName.OKANECHAN, "Số lượng nhập vào không hợp lệ");
                        return;
                    }
                    int soluong = Integer.parseInt(luong);
                    if (soluong > 200000) {
                        getService().npcChat(NpcName.OKANECHAN, "Chỉ có thể đổi tối đa 200k lượng 1 lần");
                        return;
                    }
                    if (soluong < 1000) {
                        getService().npcChat(NpcName.OKANECHAN, "Chỉ phải đổi ít nhất 1k lượng 1 lần");
                        return;
                    }
                    if (user.gold < soluong) {
                        getService().npcChat(NpcName.OKANECHAN, "Không đủ lượng để đổi");
                    } else {
                        long coinRv = 3000 * soluong;
                        long sum = this.coin + coinRv;
                        if (sum > this.coinMax) {
                            getService().npcChat(NpcName.OKANECHAN, "Số lượng sau khi đổi vượt quá sức chứa hành trang không thể đổi");
                            return;
                        }
                        addGold(-(int) (soluong));
                        addCoin(coinRv);
                        getService().npcChat(NpcName.OKANECHAN, String.format("Bạn đã quy đổi thành công %,d xu", 3000 * soluong));
                    }
                }));
                getService().showInputDialog();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Đổi yên", () -> {
                setInput(new InputDialog(CMDInputDialog.EXECUTE, "Số lượng đổi(1 Lượng = 10000 yên)", () -> {

                    String luong = input.getText();
                    if (!NinjaUtils.CheckString(luong, "^[0-9]+$")) {
                        getService().npcChat(NpcName.OKANECHAN, "Số lượng nhập vào không hợp lệ");
                        return;
                    }
                    int soluong = Integer.parseInt(luong);
                    if (this.yen + (10000L * soluong) > 2000000000) {
                        getService().npcChat(NpcName.OKANECHAN, "Chỉ có thể đổi không được quá 2 tỷ yên");
                        return;
                    }
                    if (soluong < 1000) {
                        getService().npcChat(NpcName.OKANECHAN, "Chỉ phải đổi ít nhất 1k lượng 1 lần");
                        return;
                    }
                    if (user.gold < soluong) {
                        getService().npcChat(NpcName.OKANECHAN, "Không đủ lượng để đổi");
                    } else {
                        addGold(-(int) ((long) soluong));
                        addYen(10000L * soluong);
                        getService().npcChat(NpcName.OKANECHAN, String.format("Bạn đã quy đổi thành công %,d yên", 10000L * soluong));
                    }
                }));
                getService().showInputDialog();
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Top VXMM", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Bảng Xếp Hạng", () -> {
                showRankedList(5);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Danh Sách Quà", () -> {
                if (isHuman) {
                    this.service.showAlert("server", "Bạn có " + NinjaUtils.getCurrency(getPointTop("topvxmm")) + " điểm\n" +
                            "Tối thiểu 1000đ mới có thể nhận quà\n" +
                            "Đặt 1 triệu xu = 1 điểm khi win" +
                            "\n\n" +
                            "  Top 1 : 100.000 lượng\n\n" +
                            "  Top 2 : 50.000 lượng\n\n" +
                            "  Top 3 : 30.000 lượng");
                    return;
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Nhận Quà Top", () -> {
                if (isHuman) {
                    int top = getTop("top_week_vxmm");
                    if (top == 1) {
                        if (getSlotNull() < 2) {
                            serverDialog("Cần có ít nhất 2  trống hành trang");
                            return;
                        }
                        addGold(100000);
                        saveTop("top_week_vxmm");
                    } else if (top == 2) {
                        if (getSlotNull() < 2) {
                            serverDialog("Cần có ít nhất 2  trống hành trang");
                            return;
                        }
                        addGold(50000);
                        saveTop("top_week_vxmm");
                    } else if (top == 3) {
                        if (getSlotNull() < 2) {
                            serverDialog("Cần có ít nhất 2  trống hành trang");
                            return;
                        }
                        addGold(30000);
                        saveTop("top_week_vxmm");
                    } else {
                        this.service.showAlert("server", "Cách chơi :\n" +
                                "1. Top VXMM hằng tuần, cứ 12h chủ nhật sẽ reset bxh tuần đó\n" +
                                "2. Sang thứ 2 sẽ tính bxh mới và cho phép 3 bạn top 1,2,3 nhận quà\n" +
                                "3. Nếu bạn trong top khi ấn nhận quà sẽ được lượng, không trong top sẽ hiện bảng thông báo này");
                    }

                } else {
                    warningClone();
                }
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận thưởng thăng cấp", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 10", () -> {
                rewardLevel(0);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 20", () -> {
                rewardLevel(1);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 30", () -> {
                rewardLevel(2);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 40", () -> {
                rewardLevel(3);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 50", () -> {
                rewardLevel(4);
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 3) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Ta là hiện thân của thần tài sẽ mang đến tài lộc cho mọi người.", "Online mỗi ngày tham gia các hoạt động để tích lũy điểm hoạt động con nhé.", "Con hãy chăm đánh quái, làm nhiệm vụ để có thêm nhiều yên hơn.");
            getService().npcChat(NpcName.OKANECHAN, talk);
        }));


    }

    public int getPointTop(String top) {
        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement("SELECT `" + top + "` FROM `players` WHERE `id` = ? LIMIT 1;");
            stmt.setInt(1, this.id);
            ResultSet result = stmt.executeQuery();
            int total = 0;
            if (result.next()) {
                total = result.getInt(top);
            } else {
                serverDialog("Không tìm thấy thông tin người dùng.");
            }

            result.close();
            stmt.close();
            return total;
        } catch (SQLException e) {
            Log.logException("Lỗi get point Top", Char.class, e);
        }
        return 0;
    }


    public int getTop(String top) {
        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT `" + top + "` FROM `players` WHERE `" + top + "` AND `id` = ? LIMIT 1;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, this.id);
            ResultSet res = stmt.executeQuery();
            int topv = 0;
            if (res.first()) {
                topv = res.getInt(top);
            }
            res.close();
            stmt.close();
            return topv;
        } catch (Exception e) {
            Log.logException("Lỗi get Top", Char.class, e);
        }
        return 0;
    }

    public void saveTop(String top) {
        try {
            DbManager.executeUpdate("UPDATE `players` SET `" + top + "` = ? WHERE `id`=?", 0, this.id);
        } catch (Exception e) {
            Log.logException("Lỗi save  Top", Char.class, e);

        }

    }

    public void addItemtopUp(Item item, int n) {
        for (int i = 0; i < n; i++) {
            Item newItem = ItemFactory.getInstance().newItem(item.id);
            if (item.template.isUpToUp) {
                newItem.setQuantity(n);
            } else {
                newItem.setQuantity(1);
            }
            newItem.expire = -1;
            newItem.setLock(false);
            addItemToBag(newItem);
        }
    }

    //    hướng dẫn phát quà
    public void phatqua(Char p) {

        // item bình thường
        Item item1 = ItemFactory.getInstance().newItem(ItemName.DA_DANH_VONG_CAP_4);
        checkUPTOUP(item1, 100, false, -1);

        // theo giới tính
        int tickId = p.gender == 1 ? ItemName.GAY_MAT_TRANG : ItemName.GAY_TRAI_TIM; // nam / nữ
        Item item2 = ItemFactory.getInstance().newItem(tickId);
        checkUPTOUP(item2, 1, false, 30);

        // chỉ số riêng mn pet
        Item item3 = ItemFactory.getInstance().newItem(ItemName.MAT_NA_KUMA);
        item3.options.clear();
        item3.options.add(new ItemOption(0, NinjaUtils.nextInt(1000, 5000))); // tấn công
        item3.options.add(new ItemOption(8, NinjaUtils.nextInt(50, 200))); // vật công ngoại
        item3.options.add(new ItemOption(6, NinjaUtils.nextInt(1000, 2000))); // hp tối đa
        item3.options.add(new ItemOption(57, NinjaUtils.nextInt(80, 120))); // cộng tiềm năng cho tất cả
        item3.options.add(new ItemOption(58, NinjaUtils.nextInt(20, 30))); // cộng % tiềm năng
        item3.expire = -1;
        item3.isLock = false;
        item3.setQuantity(1);
        addItemToBag(item3);

        Item item21 = ItemFactory.getInstance().newItem(ItemName.LONG_DEN_HOA_SEN_THOI_TRANG);
        item21.options.clear();
        item21.options.add(new ItemOption(0, 5000)); // tấn công
        item21.options.add(new ItemOption(8, 200)); // vật công ngoại
        item21.options.add(new ItemOption(57, 120)); // cộng tiềm năng cho tất cả
        item21.options.add(new ItemOption(58, 30)); // cộng % tiềm năng
        item21.options.add(new ItemOption(99, 500)); // nửa giây phục hồi hp mp
        item21.expire = 90 * System.currentTimeMillis();
        ;
        item21.isLock = false;
        item21.setQuantity(1);
        addItemToBag(item21);

        // vũ khí mcs theo phái
        int[] itemIds = {1111, 1112, 1113, 1114, 1115, 1116};
        int idItem = itemIds[this.classId - 1];
        Item item4 = ItemFactory.getInstance().newItem10X(idItem, true);
        item4.expire = -1;
        item4.setQuantity(1);
        item4.isLock = false;
        addItemToBag(item4);

        // ngọc 10
        int[] idngoc = {ItemName.LUC_NGOC, ItemName.LAM_TINH_NGOC, ItemName.HUYET_NGOC, ItemName.HUYEN_TINH_NGOC};
        int itemID = idngoc[NinjaUtils.nextInt(idngoc.length)];
        addNgoc(itemID, 10, true, false);

        // random mn trang bị 2 vv
        int[] idMn = {ItemName.MAT_NA_SHIN_AH, ItemName.MAT_NA_VO_DIEN, ItemName.MAT_NA_ONI, ItemName.MAT_NA_KUMA, ItemName.MAT_NA_INU};
        int itemIDMn = idMn[NinjaUtils.nextInt(idMn.length)];
        Item Mn = ItemFactory.getInstance().newItem(itemIDMn);
        Mn.expire = -1;
        Mn.setLock(false);
        addItemToBag(Mn);

    }

    public void addNgoc2(int itemID, int upgrade, boolean max, boolean lock, int quantity) {
        Item itemNgoc = ItemFactory.getInstance().newGem(itemID, max);
        itemNgoc.setLock(lock);
        int n = quantity;
        if (itemNgoc.template.isUpToUp) {
            n = 1;
        }
        for (int j = 0; j < n; j++) {
            Item newItem = ItemFactory.getInstance().newGem(itemNgoc.id, max);
            if (itemNgoc.template.isUpToUp) {
                newItem.setQuantity(n);
            } else {
                newItem.setQuantity(1);
            }
            newItem.isLock = lock;
            for (int i = newItem.upgrade; i < upgrade; i++) {
                newItem.upgrade++;
                for (ItemOption option : newItem.options) {
                    switch (option.optionTemplate.id) {
                        case 73:
                            // tấn công
                            if (option.param > 0) {
                                int[] paramUp = {0, 50, 100, 150, 200, 250, 300, 350, 400, 450};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 100;
                            }
                            break;
                        case 115:
                            // né đòn
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 10;
                            }
                            break;
                        case 116:
                            // chính xác
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 10;
                            }
                            break;
                        case 124:
                            // giảm trừ sát thương
                            if (option.param > 0) {
                                int[] paramUp = {0, 10, 15, 20, 25, 30, 35, 40, 45, 50};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 5;
                            }
                            break;
                        case 114:
                            // chí mạng
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 5;
                            }
                            break;
                        case 126:
                            // phản đòn
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 5;
                            }
                            break;
                        case 118:
                            // kháng tất cả
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 5;
                            }
                            break;
                        case 102:
                            // sát thương lên quái
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 400, 600, 800, 1000, 1200, 1400, 1600};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 500;
                            }
                            break;
                        case 105:
                            // sát thương chí mạng
                            // lên 6 2600
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 900, 1200};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 500;
                            }
                            break;
                        case 103:
                            // sát thương lên người
                            // lên 4 900 , lên 5 1500 , lên 6 2300
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 900, 1200};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 200;
                            }
                            break;
                        case 121:
                            // kháng sát thương chí mạng
                            if (option.param > 0) {
                                int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 5;
                            }
                            break;
                        case 117:
                        case 125:
                            // hp, mp tối đa
                            if (option.param > 0) {
                                int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 800, 900};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 50;
                            }
                            break;
                        case 119:
                        case 229:
                            // hồi phục hp,
                            // mp
                            if (option.param > 0) {
                                int[] paramUp = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18};
                                option.param += paramUp[i];
                            } else {
                                option.param -= 10;
                            }
                            break;
                        case 123:
                            int[] giaKham = {800000, 1600000, 2400000, 3200000, 4800000, 7200000, 10800000, 15600000,
                                    20100000, 28100000};
                            option.param = giaKham[i];
                            break;
                        default:
                            break;
                    }
                }
            }
            addItemToBag(newItem);
        }

    }

    public void addNgoc(int itemID, int upgrade, boolean max, boolean lock) {
        Item itemNgoc = ItemFactory.getInstance().newGem(itemID, max);
        itemNgoc.setLock(lock);
        for (int i = itemNgoc.upgrade; i < upgrade; i++) {
            itemNgoc.upgrade++;
            for (ItemOption option : itemNgoc.options) {
                switch (option.optionTemplate.id) {
                    case 73:
                        // tấn công
                        if (option.param > 0) {
                            int[] paramUp = {0, 50, 100, 150, 200, 250, 300, 350, 400, 450};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 100;
                        }
                        break;
                    case 115:
                        // né đòn
                        if (option.param > 0) {
                            int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 10;
                        }
                        break;
                    case 116:
                        // chính xác
                        if (option.param > 0) {
                            int[] paramUp = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 10;
                        }
                        break;
                    case 124:
                        // giảm trừ sát thương
                        if (option.param > 0) {
                            int[] paramUp = {0, 10, 15, 20, 25, 30, 35, 40, 45, 50};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 5;
                        }
                        break;
                    case 114:
                        // chí mạng
                        if (option.param > 0) {
                            int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 5;
                        }
                        break;
                    case 126:
                        // phản đòn
                        if (option.param > 0) {
                            int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 5;
                        }
                        break;
                    case 118:
                        // kháng tất cả
                        if (option.param > 0) {
                            int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 5;
                        }
                        break;
                    case 102:
                        // sát thương lên quái
                        if (option.param > 0) {
                            int[] paramUp = {0, 100, 200, 400, 600, 800, 1000, 1200, 1400, 1600};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 500;
                        }
                        break;
                    case 105:
                        // sát thương chí mạng
                        // lên 6 2600
                        if (option.param > 0) {
                            int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 900, 1200};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 500;
                        }
                        break;
                    case 103:
                        // sát thương lên người
                        // lên 4 900 , lên 5 1500 , lên 6 2300
                        if (option.param > 0) {
                            int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 900, 1200};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 200;
                        }
                        break;
                    case 121:
                        // kháng sát thương chí mạng
                        if (option.param > 0) {
                            int[] paramUp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 5;
                        }
                        break;
                    case 117:
                    case 125:
                        // hp, mp tối đa
                        if (option.param > 0) {
                            int[] paramUp = {0, 100, 200, 300, 400, 500, 600, 700, 800, 900};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 50;
                        }
                        break;
                    case 119:
                    case 229:
                        // hồi phục hp,
                        // mp
                        if (option.param > 0) {
                            int[] paramUp = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18};
                            option.param += paramUp[i];
                        } else {
                            option.param -= 10;
                        }
                        break;
                    case 123:
                        int[] giaKham = {800000, 1600000, 2400000, 3200000, 4800000, 7200000, 10800000, 15600000,
                                20100000, 28100000};
                        option.param = giaKham[i];
                        break;
                    default:
                        break;
                }
            }
        }
        addItemToBag(itemNgoc);
    }


    private String formatNumberWithCommas(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }


    private void rewardLevel(int optionId) {
        int rewardLevel = (optionId + 1) * 10;
        if (this.level >= rewardLevel) {
            if (reward[optionId]) {
                getService().npcChat(NpcName.OKANECHAN, "Mỗi mốc chỉ được nhận một lần.");
                return;
            }
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            reward[optionId] = true;
            Item ngoc = null;
            int gold = 0;
            switch (optionId) {
                case 0:
//                    ngoc = ItemFactory.getInstance().newItem(223);
                    gold = 1000;
                    break;

                case 1:
//                    ngoc = ItemFactory.getInstance().newItem(224);
                    gold = 1000;
                    break;

                case 2:
//                    ngoc = ItemFactory.getInstance().newItem(225);
                    gold = 1000;
                    break;

                case 3:
//                    ngoc = ItemFactory.getInstance().newItem(226);
                    gold = 1000;
                    break;

                case 4:
//                    ngoc = ItemFactory.getInstance().newItem(227);
                    gold = 1000;
                    break;

            }
            boolean rewarded = true;
            // if (Config.getInstance().SERVER == 2) {
            if (user.levelRewards[optionId] == 1) {
                rewarded = false;
            } else {
                user.levelRewards[optionId] = 1;
            }
            // }

            if (rewarded) {
                addGold(gold);
            }
            if (ngoc != null) {
                ngoc.isLock = true;
                addItemToBag(ngoc);
            }
        } else {
            getService().npcChat(NpcName.OKANECHAN, "Yêu cầu cấp " + rewardLevel + " để nhận thưởng");
        }
    }

    public void orderTaskDay() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_DAY) {
                getService().npcChat(NpcName.RIKUDOU, "Nhiệm vụ lần trước ta giao, con vẫn chưa hoàn thành.");
                return;
            }
        }
        if (this.level < 30) {
            getService().npcChat(NpcName.RIKUDOU, "Con hãy có gắng luyện tập đạt cấp 30 rồi quay lại đây gặp ta.");
            return;
        }
        if (countFinishDay <= 0) {
            getService().npcChat(NpcName.RIKUDOU, "Hôm nay con đã làm hết nhiệm vụ ta giao. Hãy quay lại vào ngày hôm sau.");
            return;
        }
        TaskOrder task = TaskFactory.getInstance().createTaskOrder(TaskOrder.TASK_DAY, (Char) this);
        if (task != null) {
//            addTopThanNong(1);
            this.countFinishDay--;
            getService().sendTaskOrder(task);
            taskOrders.add(task);
            getService().npcChat(NpcName.RIKUDOU, "Đây là lần nhận nhiệm vụ thứ " + (20 - countFinishDay) + " trong ngày hôm nay. Mỗi ngày được nhận tối đa 20 lần con nhé.");
        }
    }

    public void cancelTaskDay() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_DAY) {
                getService().clearTaskOrder(task);
                taskOrders.remove(task);
                getService().npcChat(NpcName.RIKUDOU, "Ta đã hủy nhiệm vụ của con. Lần sau cố gắng hoàn thành tốt nhiệm vụ con nhé.");
                return;
            }
        }
        getService().npcChat(NpcName.RIKUDOU, "Hiện tại con không có nhiệm vụ để hủy.");
    }

    public void finishTaskDay() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_DAY) {
                if (task.isComplete()) {
                    getService().npcChat(NpcName.RIKUDOU, "Tốt lắm! Ta có phần thưởng dành cho con.");
                    this.pointUyDanh++;
                    Item item = ItemFactory.getInstance().newItem(251);
                    item.setQuantity(2);
                    addItemToBag(item);
                    addYen(20000);
                    countDoneTaskDay += 1;
                    if (this.clan != null) {
                        this.addClanPoint(10);
                    }
                    getService().clearTaskOrder(task);
                    taskOrders.remove(task);
                    if (taskId == TaskName.NV_TUAN_HOAN) {
                        if (taskMain != null && taskMain.index == 1) {
                            updateTaskCount(1);
                        }
                    }
                    if (taskId == TaskName.NV_HOAT_DONG_HANG_NGAY) {
                        if (taskMain != null && taskMain.index == 3) {
                            updateTaskCount(1);
                        }
                    }
                    if (Event.isKoroKing()) {
                        KoroKing.addTrophy((Char) this);
                    }
                    if (Event.isEvent()) {
                        int itemID = Event.getEvent().randomItemID();
                        if (itemID != -1) {
                            Item itemE = ItemFactory.getInstance().newItem(itemID);
                            itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                            addItemToBag(itemE);
                        }
                    }
                } else {
                    getService().npcChat(NpcName.RIKUDOU, "Con hãy hoàn thành nhiệm vụ rồi quay lại đây.");
                }
                return;
            }
        }
        getService().npcChat(NpcName.RIKUDOU, "Hãy làm việc cho ta bằng cách làm nhiệm vụ, sau khi hoàn thành con sẽ nhận được phần thưởng từ ta.");
    }

    public void addTopThanNong(int point) {
        this.topThanNong += point;
    }

    public void workTaskDay() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_DAY) {
                short map = (short) task.mapId;
                short[] xy = NinjaUtils.getFirstPosition(map);
                setXY(xy[0], xy[1]);
                changeMap(map);
                return;
            }
        }
        getService().npcChat(NpcName.RIKUDOU, "Hãy nhận nhiệm vụ mỗi ngày từ ta rồi mới sử dụng tính năng này.");
    }

    public void orderTaskBoss() {
        Group group = this.getGroup();
        if (group != null) {
            if (!group.isCheckLeader((Char) this)) {
                getService().npcChat(NpcName.RIKUDOU, "Bạn không phải đội trưởng.");
                return;
            }
            List<Char> chars = group.getCharsInZone(this.mapId, zone.id);
            for (Char _char : chars) {
                if (_char != null && _char != this && !_char.isDead) {
                    if (_char.taskOrders != null) {
                        for (TaskOrder task : _char.taskOrders) {
                            if (task.taskId == TaskOrder.TASK_BOSS) {
                                getService().npcChat(NpcName.RIKUDOU,
                                        "Trong nhóm có thành viên đã nhận nhiệm vụ khác");
                                return;
                            }
                        }

                        if (_char.level < 30) {
                            getService().npcChat(NpcName.RIKUDOU, "Trong nhóm có thành viên dưới cấp độ 30");
                            return;
                        }
                        if (_char.countLoopBoss <= 0) {
                            getService().npcChat(NpcName.RIKUDOU,
                                    "Trong nhóm có thành viên hết lượt nhận nhiệm vụ");
                            return;
                        }
                    }
                }
            }
        }
        //
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_BOSS) {
                getService().npcChat(NpcName.RIKUDOU, "Nhiệm vụ lần trước ta giao, con vẫn chưa hoàn thành.");
                return;
            }
        }
        if (this.level < 30) {
            getService().npcChat(NpcName.RIKUDOU, "Con hãy có gắng luyện tập đạt cấp 30 rồi quay lại đây gặp ta.");
            return;
        }
        if (countLoopBoss <= 0) {
            getService().npcChat(NpcName.RIKUDOU, "Nhiệm vụ truy bắt tà thú của ngày hôm nay đã hết con hãy quay lại vào ngày mai.");
            return;
        }
        TaskOrder task = TaskFactory.getInstance().createTaskOrder(TaskOrder.TASK_BOSS, (Char) this);
        if (task != null) {
            this.countLoopBoss--;
            getService().sendTaskOrder(task);
            taskOrders.add(task);
            getService().npcChat(NpcName.RIKUDOU,
                    "Con hay sớm hoàn thành nhiệm vụ để nhận thưởng từ ta.");
            //
            if (group != null) {
                List<Char> chars = group.getCharsInZone(this.mapId, zone.id);
                for (Char _char : chars) {
                    TaskOrder task2 = TaskFactory.getInstance().createTaskOrder(TaskOrder.TASK_BOSS, _char);
                    if (task2 != null) {
                        if (_char != null && _char != this && !_char.isDead) {
                            _char.countLoopBoss--;
                            _char.getService().sendTaskOrder(task2);
                            _char.taskOrders.add(task2);
                            _char.getService().npcChat(NpcName.RIKUDOU,
                                    "Con hay sớm hoàn thành nhiệm vụ để nhận thưởng từ ta.");
                        }
                    }
                }
            }
        }
    }

    public void cancelTaskBoss() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_BOSS) {
                getService().clearTaskOrder(task);
                taskOrders.remove(task);
                getService().npcChat(NpcName.RIKUDOU, "Ta đã hủy nhiệm vụ của con. Lần sau cố gắng hoàn thành tốt nhiệm vụ con nhé.");
                return;
            }
        }
        getService().npcChat(NpcName.RIKUDOU, "Hiện tại con không có nhiệm vụ để hủy.");
    }

    public void finishTaskBoss() {
        for (TaskOrder task : this.taskOrders) {
            if (task.taskId == TaskOrder.TASK_BOSS) {
                if (task.isComplete()) {
                    getService().npcChat(NpcName.RIKUDOU, "Tốt lắm! Ta có phần thưởng dành cho con.");
                    Item item = ItemFactory.getInstance().newItem(251);
                    item.setQuantity(5);
                    addItemToBag(item);
                    if (this.clan != null) {
                        this.addClanPoint(10);
                    }
                    getService().clearTaskOrder(task);
                    taskOrders.remove(task);
                    if (taskId == TaskName.NV_TUAN_HOAN) {
                        if (taskMain != null && taskMain.index == 2) {
                            updateTaskCount(1);
                        }
                    }
                    if (taskId == TaskName.NV_HOAT_DONG_HANG_NGAY) {
                        if (taskMain != null && taskMain.index == 1) {
                            updateTaskCount(1);
                        }
                    }
                    if (Event.isKoroKing()) {
                        KoroKing.addTrophy((Char) this);
                    }

                } else {
                    getService().npcChat(NpcName.RIKUDOU, "Con hãy hoàn thành nhiệm vụ rồi quay lại đây.");
                }
                return;
            }
        }
        getService().npcChat(NpcName.RIKUDOU, "Hãy làm việc cho ta bằng cách làm nhiệm vụ, sau khi hoàn thành con sẽ nhận được phần thưởng từ ta.");
    }

    public void npcRikudou() {
        if (mapId == 118 || mapId == 119) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Rời khỏi nơi này", () -> {
                short[] xy = NinjaUtils.getXY((short) this.mapBeforeEnterPB);
                setXY(xy);
                changeMap(this.mapBeforeEnterPB);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Tổng kết", () -> {
                viewBattleSummary();
            }));
        } else {
            menus.add(new Menu(CMDMenu.EXECUTE, "Trang chủ", () -> {
                getService().openWeb("Trang chủ", "Hủy", "/", "Bạn muốn truy cập trang chủ?");
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "NV mỗi ngày", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Nhận", () -> {
                    orderTaskDay();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hủy", () -> {
                    cancelTaskDay();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hoàn thành", () -> {
                    finishTaskDay();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Đi làm NV", () -> {
                    workTaskDay();
                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "NV Truy bắt Tà Thú", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Nhận", () -> {
                    orderTaskBoss();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hủy", () -> {
                    cancelTaskBoss();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hoàn Thành", () -> {
                    finishTaskBoss();
                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Chiến trường", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Bạch giả", () -> {
                    joinBattle(0);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hắc giả", () -> {
                    joinBattle(1);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Tổng kết", () -> {
                    viewBattleSummary();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                    getService().npcChat(NpcName.RIKUDOU, "Chiến trường là nơi hội tụ của 2 phe Nhẫn giả");
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Top", () -> {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Từ 50 đến 70", () -> {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tuần", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_50_TO_70, War.TOP_WEEK);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tháng", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_50_TO_70, War.TOP_MONTH);
                        }));
                        getService().openUIMenu();
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Từ 71 đến 89", () -> {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tuần", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_71_TO_89, War.TOP_WEEK);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tháng", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_71_TO_89, War.TOP_MONTH);
                        }));
                        getService().openUIMenu();
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Từ 90 đến 110", () -> {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tuần", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_90_TO_110, War.TOP_WEEK);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tháng", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_90_TO_110, War.TOP_MONTH);
                        }));
                        getService().openUIMenu();
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Từ 111 đến 130", () -> {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tuần", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_111_TO_130, War.TOP_WEEK);
                        }));
                        menus.add(new Menu(CMDMenu.EXECUTE, "Top Tháng", () -> {
                            War.viewTop((Char) this, War.TYPE_LEVEL_111_TO_130, War.TOP_MONTH);
                        }));
                        getService().openUIMenu();
                    }));
                    getService().openUIMenu();

                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Thất thú ải", () -> {
                Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_WEEK);
                int h = calendar.get(Calendar.HOUR_OF_DAY);
                int m = calendar.get(Calendar.MINUTE);
                if (!(((h == 9 || h == 21) || (m < 30 && (h == 10 || h == 22))) && (d == 3 || d == 5 || d == 7))) {
                    getService().npcChat(NpcName.RIKUDOU, "Thất thú ải chỉ mở 2 lần: từ 9-10h30' hoặc từ 21-22h30' vào thứ 3, 5, 7 hàng tuần.");
                    return;
                }
                if (this.level < 50) {
                    getService().npcChat(NpcName.RIKUDOU, "Yêu cầu trình độ cấp 50 trở lên mới có thể tham gia");
                    return;
                }
                SevenBeasts sevenBeasts = null;
                if (sevenBeastsID == -1) {
                    if (group == null || group.memberGroups.size() < 6) {
                        getService().npcChat(NpcName.RIKUDOU, "Phải lập nhóm và đủ 6 người thì ta mới cho vào.");
                        return;
                    }
                    if (group.memberGroups.get(0).charId != this.id) {
                        getService().npcChat(NpcName.RIKUDOU, "Chỉ nhóm trưởng mới được mở");
                        return;
                    }

                    int type = 0;
                    if (h == 9 && m < 30) {
                        type = 1;
                    }
                    if (h == 21 && m < 30) {
                        type = 2;
                    }
                    int rm = 30 - m;
                    sevenBeasts = new SevenBeasts(3600 + (rm * 60));
                    sevenBeasts.setMType((byte) type);
                    MapManager.getInstance().addSevenBeasts(sevenBeasts);
                    List<Char> chars = group.getChars();
                    for (Char p : chars) {
                        if (p.sevenBeastsID == -1) {
                            p.sevenBeastsID = sevenBeasts.getId();
                            p.typeSevenBeasts = sevenBeasts.getMType();
                            sevenBeasts.addCharId(p.id);
                        }
                    }
                    group.getGroupService().serverMessage("Nhóm trưởng đã mở thất thú ải");
                } else {
                    sevenBeasts = MapManager.getInstance().findSevenBeasts(sevenBeastsID);
                }
                if (sevenBeasts != null) {
                    if (sevenBeasts.isClosed()) {
                        getService().npcChat(NpcName.RIKUDOU, "Thất thú  ải đã đóng");
                        return;
                    }
                    if (sevenBeasts.getMType() != typeSevenBeasts) {
                        getService().npcChat(NpcName.RIKUDOU, "Mỗi ngày chỉ được tham gia một lần");
                        return;
                    }
                    addWorld(sevenBeasts);
                    this.mapBeforeEnterPB = mapId;
                    outZone();
                    setXY((short) 35, (short) 360);
                    sevenBeasts.join((Char) this);
                }
            }));
        }
    }

    private void joinBattle(int optionId) {
        War warDaily = MapManager.getInstance().normalWar;
        if (warDaily != null) {
            if ((warDaily.type == War.TYPE_LEVEL_50_TO_70 && (this.level < 50 || this.level > 70))
                    || (warDaily.type == War.TYPE_LEVEL_71_TO_89 && (this.level < 71 || this.level > 89))
                    || (warDaily.type == War.TYPE_LEVEL_90_TO_110 && (this.level < 90 || this.level > 110))
                    || (warDaily.type == War.TYPE_LEVEL_111_TO_130 && (this.level < 111 || this.level > 130))
            ) {
                getService().npcChat(NpcName.RIKUDOU, "Cấp độ không phù hợp");
                return;
            }
        }
        if (warDaily == null || warDaily.status == 2) {
            getService().npcChat(NpcName.RIKUDOU, "Chiến trường mở đăng kí vào lúc 17h30,19h00,20h30,22h00,.");
            return;
        }

        if (warDaily.status == 0 || warDaily.status == 1) {
            if (this.faction != -1 && warDaily.time == this.time && this.faction != optionId) {
                getService().npcChat(NpcName.RIKUDOU, "Không thể chọn phe khác");
                return;
            }

            if (warDaily.status == 0 && this.faction == -1) {
                optionId = warDaily.numberJoinedWhite > warDaily.numberJoinedBlack ? 1 : 0;
                if (optionId == 0 && warDaily.numberJoinedWhite - warDaily.numberJoinedBlack > 10) {
                    getService().npcChat(NpcName.RIKUDOU, "Hãy đăng ký phe Hắc giả");
                    return;
                }
                if (optionId == 1 && warDaily.numberJoinedBlack - warDaily.numberJoinedWhite > 10) {
                    getService().npcChat(NpcName.RIKUDOU, "Hãy đăng ký phe Bạch giả");
                    return;
                }
            }

            if (warDaily.status == 1) {
                if (this.faction == -1 || this.time != warDaily.time) {
                    getService().npcChat(NpcName.RIKUDOU, "Con chưa đăng ký tham gia");
                    return;
                }
            }
            if (optionId == 0 && warDaily.whiteMembers.size() - warDaily.blackMembers.size() > 10) {
                getService().npcChat(NpcName.RIKUDOU, "Chênh lệch hai phe quá lớn hãy chờ người tham gia phe Hắc giả");
                return;
            }
            if (optionId == 1 && warDaily.blackMembers.size() - warDaily.whiteMembers.size() > 10) {
                getService().npcChat(NpcName.RIKUDOU, "Chênh lệch hai phe quá lớn hãy chờ người tham gia phe Bạch giả");
                return;
            }
            if (warDaily.status == 0 && (this.faction == -1 || this.time != warDaily.time)) {
                this.faction = (byte) optionId;
                this.warPoint = 0;
                if (optionId == 0) {
                    warDaily.numberJoinedWhite++;
                }
                if (optionId == 1) {
                    warDaily.numberJoinedBlack++;
                }
                this.time = warDaily.time;
                this.member = null;
                this.isRewarded = false;
            }

            short x = -1;
            short y = -1;
            int mapID = -1;
            if (faction == 0) {
                mapID = 118;
                x = 104;
                y = 336;
            }
            if (faction == 1) {
                mapID = 119;
                x = 104;
                y = 240;
            }
            if (mapID != -1) {
                mapBeforeEnterPB = this.mapId;
                member = getMemberNormalWar();
                if (member == null) {
                    member = new WarMember();
                    member.id = this.id;
                    member.name = this.name;
                    member.point = 0;
                    member.faction = this.faction;
                    member.type = (byte) warDaily.type;
                    warDaily.addMember(member);
                }
                setXY(x, y);
                changeMap(mapID);
                warDaily.addMember((Char) this);
                war = warDaily;
            }
        }
    }


    private void viewBattleSummary() {

        War warDaily = MapManager.getInstance().normalWar;
        if (mapId == 98 || mapId == 104) {
            warDaily = this.war;
        }
        if (warDaily != null) {
            warDaily.viewTop((Char) this);
        } else {
            getService().npcChat(NpcName.RIKUDOU, "Chưa có dữ liệu.");
        }
    }


    public void npcUmayaki_1() {
        if (!isVillage()) {
            return;
        }
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 5) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Nhà bạn muốn đi đâu?", "Ngựa của ta rất khỏe có thể chạy ngàn dặm.", "Đi xe kéo của ta an toàn số một.");
            getService().npcChat(NpcName.UMAYAKI, talk);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Kojin", () -> {
            teleport(10);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Sanzu", () -> {
            teleport(17);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Tone", () -> {
            teleport(22);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng chài", () -> {
            teleport(32);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Chakumi", () -> {
            teleport(38);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Echigo", () -> {
            teleport(43);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Làng Oshin", () -> {
            teleport(48);
        }));
    }

    private void teleport(int map) {
        if (isCanEnterMap(map)) {
            short[] xy = NinjaUtils.getXY(map);
            setXY(xy[0], xy[1]);
            changeMap(map);
        } else {
            serverDialog("Bạn chưa thể đến khu vực này. Hãy hoàn thành nhiệm vụ trước.");
        }
    }

    private void teleportVDMQ() {
        if (this.level < 60) {
            serverDialog("Trình độ cấp 60 mới có thể sử dụng chức năng này.");
            return;
        }
        if (!isHuman) {
            Effect eff = em.findByID((byte) 34);
            if (eff == null) {
                getService().npcChat(NpcName.KAMAKURA, "Con đã hết thời gian phưu lưu tại Vùng đất ma quỷ. Hãy sử dụng Thí luyện thiếp để tiếp tục phưu lưu.");
                return;
            }
        }
        setXY((short) 40, (short) 384);
        changeMap(139);
    }

    private void teleportLangDiaNguc() { // lang dia nguc
        if (this.level < 29 || this.level > 79) {
            serverDialog("ít nhất phải đạt trình độ từ lv29 đến lv79 mới được vào đây con nhé . Không thì Lướt.");
            return;
        }
        if (!isHuman) {
            serverDialog("Phân thân Lướt.");
            return;
        }
        setXY((short) 104, (short) 288);
        changeMap(177);
    }

    public void npcSunoo() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận Ruby", () -> {
            if (getSlotNull() == 0) {
                getService().npcChat(NpcName.SUNOO, "Hành trang không đủ chỗ trống!");
                return;
            }
            if (this.user.gold < 500) {
                getService().npcChat(NpcName.SUNOO, "Bạn không đủ 500 lượng để nhận được Ruby!");
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (this.level < 10) {
                getService().npcChat(NpcName.SUNOO, "Yêu cầu level 10 trở lên mới dùng được Ruby!");
                return;
            }
            if (this.classId == 0) {
                getService().npcChat(NpcName.SUNOO, "Hãy vào trường trước, rồi ra đây nhận Ruby!");
                return;
            }
            Item itm = ItemFactory.getInstance().newItem(1117);
            itm.isLock = true;
            this.user.addGold(-500);
            this.addItemToBag(itm);
        }));
        menus.add(new Menu(1, "Luyện Ruby", () -> {
            if (fashion[7] == null) {
                getService().npcChat(NpcName.SUNOO, "Hãy đeo Ruby trước!");
                return;
            }
            if (fashion[7].upgrade >= 1) {
                getService().npcChat(NpcName.SUNOO, "Không thể luyện Ruby đã nâng cấp!");
                return;
            }
            Item itm = ItemFactory.getInstance().newItem(1117);
            if (!isHuman) {
                warningClone();
                return;
            }
            if (getSlotNull() == 0) {
                getService().npcChat(NpcName.SUNOO, "Hành trang bạn không đủ chỗ trống!");
                return;
            }

            if (this.user.gold < 200) {
                getService().npcChat(NpcName.SUNOO, "Bạn không đủ 200 lượng!");
                return;
            }

            int a = 0;
            for (int i = 0; i < optionmathactinh.length; i++) {
                if (NinjaUtils.nextInt(1, 10) < 4) {
                    itm.options.add(new ItemOption(optionmathactinh[i], NinjaUtils.nextInt(parammathactinh[i] * 70 / 100, parammathactinh[i])));
                    a++;
                }
            }
            itm.isLock = true;
            this.addItemToBag(itm);
            fashion[7] = null;
            this.user.addGold(-200);
            String message = "";
            if (a % 2 == 0) {
                message = "Ta cảm thấy ruby của con khá mạnh đấy!";
            } else {
                message = "Con chỉ cần cố gắng một chút nữa thôi!";
            }
            getService().npcChat(NpcName.SUNOO, message);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp Ruby", () -> {
            if (fashion[7] == null) {
                getService().npcChat(NpcName.SUNOO, "Hãy đeo Ruby trước!");
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (getSlotNull() == 0) {
                getService().npcChat(NpcName.SUNOO, "Hành trang bạn không đủ chỗ trống!");
                return;
            }
            Item itm = fashion[7];

            if (this.user.gold < 200) {
                getService().npcChat(NpcName.SUNOO, "Bạn không đủ " + 200 + " lượng!");
                return;
            }

            if (itm.upgrade >= getMaxLevelUpdate()) {
                getService().npcChat(NpcName.SUNOO, "Ruby đã đạt cấp tối đa!, để Ruby cấp cao hơn, bạn cần level cao hơn!");
                return;
            }
            if (percentmathactinh[itm.upgrade] >= NinjaUtils.nextInt(140)) {
                for (byte k = 0; k < itm.options.size(); ++k) {
                    final ItemOption option = itm.options.get(k);
                    option.param += option.param * 10 / 100;
                }
                itm.upgrade = (byte) (itm.upgrade + 1);
//                itm.isLock = true;
//                this.addItemToBag(itm);
                getService().npcChat(NpcName.SUNOO, "Nâng cấp thành công!");
                setAbility();
                setFashion();
//                fashion[7] = null;
            } else {
                getService().npcChat(NpcName.SUNOO, "Nâng cấp thất bại!");
            }
            this.user.addGold(-200);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Ta có thể giúp bạn làm tăng sức mạnh cho ruby, bạn chỉ cần trả cho ta ít ngân lượng.", "Nhanh lên kẻo bị phát hiện.", "Chắc chắn bạn sẽ cần ta giúp.");
            getService().npcChat(NpcName.SUNOO, talk);
        }));
    }

    public int getMaxLevelUpdate() {
        if (this.level < 20) {
            return 4;
        } else if (this.level < 40) {
            return 8;
        } else if (this.level < 50) {
            return 12;
        } else if (this.level < 60) {
            return 14;
        } else {
            return 16;
        }
    }

    public void npcTajima() {

        String t2 = (invite_player == 0 ? "Tắt" : "Bật");
        menus.add(new Menu(CMDMenu.EXECUTE, String.format("[%s]\n nhận lời mời", t2), () -> {
            try {
                if (invite_player == 0) {
                    invite_player = 1;
                } else {
                    invite_player = 0;
                }
                DbManager.executeUpdate("UPDATE `players` SET `invite_player`= ? WHERE `id` = ? LIMIT 1;", invite_player, this.id);
            } catch (Exception ee) {
                Log.logException("Lỗi npc Admin(3)  : ", Char.class, ee);

            }
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Hủy vật phẩm và nhiệm vụ", () -> {
            if (isHuman) {
                clearTask();
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskMain != null) {
                getService().npcChat(NpcName.TAJIMA, "Con đang làm nhiệm vụ " + taskMain.template.getName() + ", hãy chọn Menu/Nhiệm vụ để biết mình đang làm tới đâu.");
            } else {
                String talk = (String) NinjaUtils.randomObject("Đi thưa, về trình, nhé các con", "Làng Tone là ngôi làng cổ xưa, đã có từ rất lâu.", "Ta là Tajima, mọi việc ở đây đều do ta quản lý");
                getService().npcChat(NpcName.TAJIMA, talk);
            }
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Thứ thân", () -> {
            if (isHuman && clone != null && clone.isNhanBan && !clone.isDead && timeCountDown > 0) {
                clone.switchToMe();
            }
        }));

        menus.add(new Menu(CMDMenu.EXECUTE, "Chủ thân", () -> {
            if (!isHuman && !isNhanBan) {
                ((CloneChar) this).human.switchToMe();
            }
        }));


        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận kiếm gỗ", () -> {
            Item kiemgo = new Item(194);
            kiemgo.options.clear();
            kiemgo.yen = 0;
            kiemgo.options.add(new ItemOption(0, 10));
            kiemgo.options.add(new ItemOption(8, 10));
            addItemToBag(kiemgo);
        }));


    }


    public void switchToMe() {
        Controller contrl = (Controller) user.session.getMessageHandler();
        contrl.setChar((Char) this);
        Service sv = user.session.getService();
        sv.setChar((Char) this);
        Zone z = clone.zone;
        clone.outZone();
        clone.outParty();
        clone.isNhanBan = true;
        clone.getEm().clearScrAllEffect(sv, z.getService(), clone);
        setAbility();
        this.hp = this.maxHP;
        this.mp = this.maxMP;
        setXY(clone.x, clone.y);
        setFashion();
        z.join((Char) this);
        getService().sendSkillShortcut("OSkill", onOSkill, (byte) 0);
        getService().sendSkillShortcut("KSkill", onKSkill, (byte) 0);
        getService().sendSkillShortcut("CSkill", onCSkill, (byte) 0);
        getService().updateInfoMe();
        getService().onBijuuInfo(id, bijuu);
        em.displayAllEffect(sv, z.getService(), (Char) this);
        getService().loadMount((Char) this);
    }

    public void npcUmayaki_2() {
        if (!isSchool()) {
            return;
        }
        menus.add(new Menu(CMDMenu.EXECUTE, "Trường Hirosaki", () -> {
            teleport(1);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Trường Haruna", () -> {
            teleport(27);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Trường Ookaza", () -> {
            teleport(72);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            if (taskId == TaskName.NHIEM_VU_CHAO_LANG && taskMain != null && taskMain.index == 5) {
                taskNext();
            }
            String talk = (String) NinjaUtils.randomObject("Nhà bạn muốn đi đâu?", "Ngựa của ta rất khỏe có thể chạy ngàn dặm.", "Đi xe kéo của ta an toàn số một.");
            getService().npcChat(NpcName.UMAYAKI_2, talk);
        }));
    }

    public void npcTruCoQuan() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Cắm chìa khóa", () -> {
            Territory territory = (Territory) findWorld(World.TERRITORY);
            if (territory != null) {
                int indexUI = getIndexItemByIdInBag(ItemName.CHIA_KHOA_LANH_DIA_GIA_TOC);
                if (indexUI == -1) {
                    serverMessage("Không có chìa khóa.");
                    return;
                }
                territory.openMap((Char) this);
                removeItem(indexUI, 1, true);
            }
        }));
    }

    public void npcBuy16() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Cửa hàng", () -> { // shop 16
            setCommandBox(STORE_UPGRADE);
            getService().openUICuaHang();
        }));
    }

    public void npcChanLe() { // chanle
        menus.clear();
        menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
            typeVXMM = 2;
            ChanLe lucky = ChanLeManager.getInstance().find(ChanLeManager.NORMAL);
            lucky.show((Char) this);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Luật chơi", () -> {
            ChanLe lucky = ChanLeManager.getInstance().find(ChanLeManager.NORMAL);
            getService().showAlert("CLTX", "- Giá trị nhập xu thấp nhất của mỗi người là " + NinjaUtils.getCurrency(lucky.getXuMin())
                    + "\n- Giá trị nhập xu cao nhất của mỗi người là " + NinjaUtils.getCurrency(lucky.getXuMax())
                    + "\n- Kết quả sẽ được random khi ván đó nhảy xuống 5 giây"
                    + "\n- Cách chơi:"
                    + "\n- CHAN: 0 2 4 6 8"
                    + "\n- LE: 1 3 5 7 9"
                    + "\n- Cách tính kết quả"
                    + "\n- Số random 1 + số random 2, ví dụ 128171 + 12861 = 1+2+8+1+7+1+1+2+8+6+1 = 38 > Lấy số cuối 8 kết quả CHAN"
                    + "\n- Tỉ lệ ăn 1.9"
                    + "\n- Còn 10 giây cuối sẽ khoá không cho đặt xu");
        }));
    }

//    public void npcNangPet() { // fix pet ứng long
//        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận pet", () -> {
//            if (getSlotNull() == 0) {
//                getService().npcChat(NpcName.NANGPET, "Hành trang không đủ chỗ trống!");
//                return;
//            }
//            if (this.user.gold < 5000) {
//                getService().npcChat(NpcName.NANGPET, "Bạn không đủ 5000 lượng để nhận được pet!");
//                return;
//            }
//            Item itm = ItemFactory.getInstance().newItem(ItemName.PET_UNG_LONG);
//            itm.options.clear();
//            itm.isLock = true;
//            this.user.addGold(-5000);
//            this.addItemToBag(itm);
//        }));
//        menus.add(new Menu(1, "Luyện pet", () -> {
//
//            if (this.equipment[ItemTemplate.TYPE_THUNUOI] != null) {
//
//                if (this.user.gold >= 1000) {
//                    if (getSlotNull() == 0) {
//                        getService().npcChat(NpcName.NANGPET, "Hãy chừa 1 ô trống trong hành trang để nhận pet.");
//                        return;
//                    }
//                    Item itemBK = this.equipment[ItemTemplate.TYPE_THUNUOI];
//                    if (itemBK.template.id != ItemName.PET_UNG_LONG) {
//                        getService().npcChat(NpcName.NANGPET,
//                                "Chỉ dùng pet ứng long");
//                        return;
//                    }
//                    if (!itemBK.options.isEmpty()) {
//                        for (int i = 0; i < itemBK.options.size(); i++) {
//                            if (itemBK.options.get(i).optionTemplate.id == 85) {
//                                if (itemBK.options.get(i).param > 0) {
//                                    getService().npcChat(NpcName.TASHINO, "Không thể luyện pet đã nâng cấp!");
//                                    return;
//                                }
//                            }
//                        }
//                    }
//
//                    addGold(-500);
//                    int itemId = itemBK.id;
//                    getService().deleteItemBody(ItemTemplate.TYPE_THUNUOI);
//                    this.equipment[ItemTemplate.TYPE_THUNUOI] = null;
//                    Item item = ItemFactory.getInstance().newItem(itemId);
//                    item.options.clear();
//                    item.isLock = true;
//                    int random = NinjaUtils.nextInt(1, 5);
//                    ArrayList<ItemOption> options = new ArrayList<>();
//                    options.add(new ItemOption(100, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(6, NinjaUtils.nextInt(500, 1500)));
//                    options.add(new ItemOption(7, NinjaUtils.nextInt(500, 1500)));
//                    options.add(new ItemOption(5, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(10, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(0, NinjaUtils.nextInt(100, 800)));
//                    options.add(new ItemOption(88, NinjaUtils.nextInt(100, 1000)));
//                    options.add(new ItemOption(89, NinjaUtils.nextInt(100, 1000)));
//                    options.add(new ItemOption(90, NinjaUtils.nextInt(100, 1000)));
//                    options.add(new ItemOption(15, NinjaUtils.nextInt(10, 20)));
//                    if (NinjaUtils.nextBoolean()) {
//                        options.add(new ItemOption(14, NinjaUtils.nextInt(10, 20)));
//                    }
//                    options.add(new ItemOption(3, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(2, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(4, NinjaUtils.nextInt(10, 20)));
//                    options.add(new ItemOption(20, NinjaUtils.nextInt(5, 10)));
//                    if (NinjaUtils.nextBoolean()) {
//                        options.add(new ItemOption(99, NinjaUtils.nextInt(20, 80)));
//                    }
//                    item.options.add(new ItemOption(85, 0));
//
//                    for (int i = 0; i < random; i++) {
//                        int index = NinjaUtils.nextInt(options.size() - 1);
//                        item.options.add(options.get(index));
//                        options.remove(index);
//                    }
//                    addItemToBag(item);
//                    setFashion();
//                    setAbility();
//                    String text = "";
//                    if (random == 1 || random == 2) {
//                        text = "Ta chỉ giúp được cho ngươi đến thế thôi, ta xin lỗi.";
//                    } else if (random == 3 || random == 4) {
//                        text = "Không tệ ngươi xem có ổn không.";
//                    } else {
//                        text = "Khá mạnh đó, ngươi thấy ta làm tốt không?";
//                    }
//                    getService().npcChat(NpcName.NANGPET, text);
//                } else {
//                    getService().npcChat(NpcName.NANGPET, "Hãy đưa ta 1.000 lượng ta mới giúp ngươi.");
//                }
//            } else {
//                getService().npcChat(NpcName.NANGPET,
//                        "Ngươi hãy sử dụng pet ta sẽ giúp ngươi làm thay đổi sức mạnh.");
//            }
//        }));
//        menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp pet", () -> {
//            int itemID = -1;
//            switch (getSys()) {
//                case 1:
//                    itemID = 834;
//                    break;
//
//                case 2:
//                    itemID = 836;
//                    break;
//
//                case 3:
//                    itemID = 835;
//                    break;
//            }
//            if (itemID == -1) {
//                return;
//            }
//            Item item = this.equipment[ItemTemplate.TYPE_THUNUOI];
//
//
//            if (item != null) {
//                if (item.template.id != ItemName.PET_UNG_LONG) {
//                    getService().npcChat(NpcName.NANGPET,
//                            "Chỉ nâng cấp pet ứng long .");
//                    return;
//                }
//                int cap = 0;
//                for (ItemOption option : item.options) {
//                    if (option.optionTemplate.id == 85) {
//                        cap = option.param;
//                        break;
//                    }
//                }
//                if (cap >= 9) {
//                    getService().npcChat(NpcName.NANGPET,
//                            "Pet của ngươi đã quá mạnh, ta không thể giúp được ngươi.");
//                    return;
//                }
//                int quantity = (cap + 1) * 5;
//                int fee = (cap + 5) * (200 + cap);
//                int percent = 100 - (cap * 10) - ((cap * 2) / 2);
//                String name = ItemManager.getInstance().getItemName(itemID);
//                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NANG_PET, String.format(
//                        "Ngươi có muốn nâng cấp pet đang sử dụng lên cấp %d không? Cần phí %d lượng - Tỉ lệ thành công: %s.",
//                        (cap + 1), fee, percent + "%")));
//                getService().openUIConfirmID();
//            } else {
//                getService().npcChat(NpcName.NANGPET,
//                        "Ngươi hãy sử dụng pet ta sẽ giúp ngươi làm thay đổi sức mạnh.");
//            }
//        }));
//        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
//            String talk = (String) NinjaUtils.randomObject("Ta có thể giúp bạn làm tăng sức mạnh cho pet, bạn chỉ cần trả cho ta ít ngân lượng.", "Nhanh lên kẻo bị phát hiện.", "Chắc chắn bạn sẽ cần ta giúp.");
//            getService().npcChat(NpcName.NANGPET, talk);
//        }));
//    }

    public void npcTashino() {

        menus.add(new Menu(1, "Luyện bí kíp", () -> {

            if (this.equipment[ItemTemplate.TYPE_BIKIP] != null) {
                if (this.user.gold >= 1000) {
                    if (getSlotNull() == 0) {
                        getService().npcChat(NpcName.TASHINO, "Hãy chừa 1 ô trống trong hành trang để nhận bí kíp.");
                        return;
                    }
                    Item itemBK = this.equipment[ItemTemplate.TYPE_BIKIP];

                    if (!itemBK.options.isEmpty()) {
                        for (int i = 0; i < itemBK.options.size(); i++) {
                            if (itemBK.options.get(i).optionTemplate.id == 85) {
                                if (itemBK.options.get(i).param > 0) {
                                    getService().npcChat(NpcName.TASHINO, "Không thể luyện bí kíp đã nâng cấp!");
                                    return;
                                }
                            }
                        }
                    }
                    addGold(-500);
                    int itemId = itemBK.id;
                    getService().deleteItemBody(ItemTemplate.TYPE_BIKIP);
                    this.equipment[ItemTemplate.TYPE_BIKIP] = null;
                    Item item = ItemFactory.getInstance().newItem(itemId);
                    item.isLock = true;
                    int random = NinjaUtils.nextInt(1, 5);
                    ArrayList<ItemOption> options = new ArrayList<>();
                    options.add(new ItemOption(100, NinjaUtils.nextInt(10, 20))); /// fix bí kíp kháng tất cả
                    options.add(new ItemOption(82, NinjaUtils.nextInt(500, 1500)));
                    options.add(new ItemOption(83, NinjaUtils.nextInt(500, 1500)));
                    options.add(new ItemOption(84, NinjaUtils.nextInt(10, 20)));
                    options.add(new ItemOption(86, NinjaUtils.nextInt(10, 20)));
                    options.add(new ItemOption(0, NinjaUtils.nextInt(100, 800)));
                    options.add(new ItemOption(88, NinjaUtils.nextInt(100, 1000)));
                    options.add(new ItemOption(89, NinjaUtils.nextInt(100, 1000)));
                    options.add(new ItemOption(90, NinjaUtils.nextInt(100, 1000)));
                    options.add(new ItemOption(91, NinjaUtils.nextInt(10, 20)));
                    if (NinjaUtils.nextBoolean()) {
                        options.add(new ItemOption(92, NinjaUtils.nextInt(10, 20)));
                    }
                    options.add(new ItemOption(95, NinjaUtils.nextInt(10, 20)));
                    options.add(new ItemOption(96, NinjaUtils.nextInt(10, 20)));
                    options.add(new ItemOption(97, NinjaUtils.nextInt(10, 20)));
                    options.add(new ItemOption(98, NinjaUtils.nextInt(5, 10)));
                    if (NinjaUtils.nextBoolean()) {
                        options.add(new ItemOption(99, NinjaUtils.nextInt(20, 80)));
                    }
                    item.options.add(new ItemOption(85, 0));

                    for (int i = 0; i < random; i++) {
                        int index = NinjaUtils.nextInt(options.size() - 1);
                        item.options.add(options.get(index));
                        options.remove(index);
                    }
                    addItemToBag(item);
                    setFashion();
                    setAbility();
                    String text = "";
                    if (random == 1 || random == 2) {
                        text = "Ta chỉ giúp được cho ngươi đến thế thôi, ta xin lỗi.";
                    } else if (random == 3 || random == 4) {
                        text = "Không tệ ngươi xem có ổn không.";
                    } else {
                        text = "Khá mạnh đó, ngươi thấy ta làm tốt không?";
                    }
                    getService().npcChat(NpcName.TASHINO, text);
                } else {
                    getService().npcChat(NpcName.TASHINO, "Hãy đưa ta 1.000 lượng ta mới giúp ngươi.");
                }
            } else {
                getService().npcChat(NpcName.TASHINO,
                        "Ngươi hãy sử dụng bí kíp ta sẽ giúp ngươi làm thay đổi sức mạnh.");
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nâng cấp bí kíp", () -> {
            int itemID = -1;
            switch (getSys()) {
                case 1:
                    itemID = 834;
                    break;

                case 2:
                    itemID = 836;
                    break;

                case 3:
                    itemID = 835;
                    break;
            }
            if (itemID == -1) {
                return;
            }
            Item item = this.equipment[ItemTemplate.TYPE_BIKIP];
            if (item != null) {
                int cap = 0;
                for (ItemOption option : item.options) {
                    if (option.optionTemplate.id == 85) {
                        cap = option.param;
                        break;
                    }
                }
                if (cap >= 9) {
                    getService().npcChat(NpcName.TASHINO,
                            "Bí kíp của ngươi đã quá mạnh, ta không thể giúp được ngươi.");
                    return;
                }
                int quantity = (cap + 1) * 5;
                int fee = (cap + 5) * (200 + cap);
                int percent = 100 - (cap * 10) - ((cap * 2) / 2);
                String name = ItemManager.getInstance().getItemName(itemID);
                setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NANG_BI_KIP, String.format(
                        "Ngươi có muốn nâng cấp bí kíp đang sử dụng lên cấp %d không? Cần %d viên %s với phí %d lượng - Tỉ lệ thành công: %s.",
                        (cap + 1), quantity, name, fee, percent + "%")));
                getService().openUIConfirmID();
            } else {
                getService().npcChat(NpcName.TASHINO,
                        "Ngươi hãy sử dụng bí kíp ta sẽ giúp ngươi làm thay đổi sức mạnh.");
            }
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            String talk = (String) NinjaUtils.randomObject("Ta có thể giúp bạn làm tăng sức mạnh cho bí kíp, bạn chỉ cần trả cho ta ít ngân lượng.", "Nhanh lên kẻo bị phát hiện.", "Chắc chắn bạn sẽ cần ta giúp.");
            getService().npcChat(NpcName.TASHINO, talk);
        }));
    }


    public void npcCayThong() {
//        if (!Event.isNoel()) {
//            serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện Noel.");
//            return;
//        }
//        menus.add(new Menu(CMDMenu.EXECUTE, "Trang trí", () -> {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            int indexUI = getIndexItemByIdInBag(ItemName.QUA_TRANG_TRI);
//            if (indexUI == -1) {
//                serverMessage("Không có quà trang trí.");
//                return;
//            }
//            Event event = Event.getEvent();
//            Item item = this.bag[indexUI];
//            getEventPoint().addPoint(Noel.TOP_DECORATION_GIFT_BOX, 1);
//            getEventPoint().addPoint(EventPoint.DIEM_TIEU_XAI, 1);
//            event.useEventItem((Char) this, item.id, event.getItemsRecFromGold2Item());
//        }));
//        menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
//            getService().showAlert("Hướng dẫn", "Để nhận thưởng tại cây thông may mắn, các bạn cần phải có Quà Trang Trí, với mỗi Quà Trang trí đặt tại cây thông bạn sẽ nhận thêm được một phần thưởng kinh nghiệm cùng với một phần quà ngẫu nhiên.");
//        }));
    }

    public void npcHoaMai() {
        if (!Event.isLunarNewYear()) {
            serverMessage("Chỉ sử dụng được trong sự kiện tết.");
            return;
        }
        menus.add(new Menu(CMDMenu.EXECUTE, "May mắn đầu xuân", () -> {
            if (level < 20) {
                serverMessage("Không đủ sức khỏe.");
            }
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            int indexUI = getIndexItemByIdInBag(ItemName.BUA_MAY_MAN);
            if (indexUI == -1) {
                serverMessage("Không có bùa may mắn.");
                return;
            }
            Event event = Event.getEvent();
            Item item = this.bag[indexUI];
            getEventPoint().addPoint(LunarNewYear.TOP_LUCKY_CHARM, 1);
            getEventPoint().addPoint(EventPoint.DIEM_TIEU_XAI, 1);
            event.useEventItem((Char) this, item.id, event.getItemsRecFromGold2Item());
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
            getService().showAlert("Hướng dẫn", "- Để nhận thưởng tại cây mai, các bạn cần phải có Bùa May Mắn\n- Với mỗi Bùa May Mắn treo tại cây mai bạn sẽ nhận thêm được một phần thưởng kinh nghiệm cùng với một phần quà ngẫu nhiên.");
        }));
    }

    public void npcEmBe() {
//        if (Events.event != Events.SUMMER) {
//            return;
//        }
//        menus.add(new Menu(CMDMenu.EXECUTE, "Cho ăn kem", () -> {
//            int indexUI = getIndexItemByIdInBag(ItemName.HU_KEM_DAM);
//
//            if (indexUI == -1) {
//                getService().npcChat(NpcName.EM_BE, "Bạn không có hũ kem cho em bé");
//                return;
//            }
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            if (this.level < 20) {
//                getService().npcChat(NpcName.EM_BE, "Trình độ bạn phải đạt cấp 20");
//                return;
//            }
//
//            Event event = Event.getEvent();
//            Item item = this.bag[indexUI];
//            event.useEventItem((Char) this, item.id, event.getItemsRecFromCoinItem());
//
//        }));
    }

    public void npcVuaHung() {
        if (user.isTien() && (this.user.username.equals("nsokeny"))) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Quản lý", () -> {
                AdminService.getInstance().openUIAdmin((Char) this);
            }));
        }

        menus.add(new Menu(CMDMenu.EXECUTE, "Cải tạo vật phẩm", () -> {  // hóa hình
            setCommandBox(CAI_TAO);
            getService().openUICaiTao();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Hóa hình vật phẩm", () -> {
            setCommandBox(HOA_HINH);
            getService().openUIHoaHinh();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Tách vật phẩm", () -> {
            setCommandBox(PHAN_TACH);
            getService().openUITach();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Luyện thú cưỡi", () -> {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Luyện thú", () -> {
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hóa hình", () -> {
                setCommandBox(Char.HOA_HINH_THU);
                List<Item> list = getListItem5sao(ItemName.XICH_NHAN_NGAN_LANG,ItemName.XE_MAY, ItemName.HARLEY_DAVIDSON);
                getService().openUIShopTrungThu(list, "Hóa hình thú", "Hóa hình");
            }));
            getService().openUIMenu();
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
            getService().npcChat(NpcName.VUA_HUNG, "Dù ai đi ngược về xuôi nhớ ngày dỗ tổ mùng 10 tháng 3");
        }));

    }

    public void npcKanata() {
        if (mapId == 110) {
            menus.add(new Menu(CMDMenu.EXECUTE, "Rời khỏi nơi này", () -> {
                short[] xy = NinjaUtils.getXY(mapBeforeEnterPB);
                setXY(xy[0], xy[1]);
                changeMap(mapBeforeEnterPB);
            }));

            menus.add(new Menu(CMDMenu.EXECUTE, "Đặt cược", () -> {
                if (this.group != null && !this.group.isCheckLeader((Char) this)) {
                    getService().npcChat(NpcName.KANATA, "Chỉ nhóm trưởng mới có thể đặt cược.");
                    return;
                }
                InputDialog input = new InputDialog(CMDInputDialog.DAT_CUOC, "Số tiền đặt cược");
                setInput(input);
                getService().showInputDialog();
            }));

            menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
                getService().npcChat(NpcName.KANATA, "Ta không có chuyện gì để nói với bạn.");
            }));
        } else if (mapId == MapName.DAU_TRUONG) {
            MapManager.getInstance().talentShow.showMenu((Char) this);
        } else {
            menus.add(new Menu(CMDMenu.EXECUTE, "Binh khí", () -> {
                getService().openUI(StoreManager.TYPE_WEAPON);
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Gia tộc", () -> {
                menus.clear();
                if (clan == null) {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Thành lập", () -> {
                        if (clan == null) {
                            if (user.gold < 10000) {
                                getService().npcChat(NpcName.KANATA, "Phí thành lập 10.000 lượng.");
                                return;
                            }
                            InputDialog input = new InputDialog(CMDInputDialog.TAO_GIA_TOC, "Tên gia tộc");
                            setInput(input);
                            getService().showInputDialog();
                        }
                    }));

                } else {
                    menus.add(new Menu(CMDMenu.EXECUTE, "Hủy gia tộc", () -> {
                        cancelClan();
                        if (this.clan == null) {
                            getService().npcChat(NpcName.KANATA, "Đừng sai lầm nữa nhé");
                        }
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Lãnh địa gia tộc", () -> {
                        if (this.clan != null) {
                            Territory ter = Territory.getTerritory(this.clan.id);
                            Member member = this.clan.getMemberByName(this.name);
                            if (member == null) {
                                return;
                            }
                            int typeClan = member.getType();
                            boolean isClanLeader = typeClan == Clan.TYPE_TOCTRUONG || typeClan == Clan.TYPE_TOCPHO;

                            if (ter == null) {
                                if (clan.getOpenDun() <= 0) {
                                    getService().npcChat(NpcName.KANATA, "Gia tộc của bạn đã hết lượt tham gia lãnh địa.");
                                    return;
                                }
                                if (getSlotNull() == 0) {
                                    getService().npcChat(NpcName.KANATA, "Hãy để lại 1 slot trống trong rương để nhận chìa khoá.");
                                    return;
                                }
                                if (!isClanLeader) {
                                    getService().npcChat(NpcName.KANATA, "Để mở lãnh địa gia tộc cần những thành viên cấp cao như Tộc Trưởng và Tộc Phó.");
                                    return;
                                }

                                ter = new Territory(this.clan.id);
                                Territory.addTerritory(this.clan.id, ter);

                                Item newItem = ItemFactory.getInstance().newItem(ItemName.CHIA_KHOA_LANH_DIA_GIA_TOC);
                                newItem.isLock = true;
                                newItem.expire = System.currentTimeMillis() + 600000;
                                addItemToBag(newItem);
                                this.clan.openDun -= 1;
                                this.clan.getClanService().serverMessage(this.name + " đã cửa lãnh địa, hãy gặp Kanata để điểm danh");
                            }

                            if (!isClanLeader && !ter.isInGuestList(this.id)) {
                                getService().npcChat(NpcName.KANATA, "Bạn chưa được tộc trưởng mời vào lãnh địa.");
                                return;
                            }

                            if (ter.started && !ter.isInTerritory(this.id)) {
                                getService().npcChat(NpcName.KANATA, "Lãnh địa gia tộc đã bắt đầu, bạn hãy tham gia vào lần sau.");
                                return;
                            }

                            addWorld(ter);
                            this.mapBeforeEnterPB = mapId;
                            setXY((short) 191, (short) 144);
                            outZone();
                            joinZone(80, -1, -1);
                        } else {
                            getService().npcChat(NpcName.KANATA, "Bạn đã có gia tộc đâu.");
                        }
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Nhận ấn tộc", () -> { ///
                        if (this.clan != null) {
                            Member member = this.clan.getMemberByName(this.name);
                            if (member == null) {
                                return;
                            }
                            if (member.getPointClanWeek() < 1000) {
                                getService().npcChat(NpcName.KANATA, "Bạn cần tối thiểu 1.000 điểm cống hiến tuần để đổi lấy ấn tộc cấp 1");
                                return;
                            }
//                            if (this.user.gold < 10000) {
//                                getService().npcChat(NpcName.KANATA, "Bạn không đủ 10000 lượng!");
//                                return;
//                            }
                            if (getSlotNull() == 0) {
                                getService().npcChat(NpcName.KANATA, "Hãy chừa ra một chỗ trống trong hành trang của bạn.");
                                return;
                            }
//                            this.user.addGold(-10000);
                            member.addPointClanWeek(-1000);
                            Item newItem = ItemFactory.getInstance().newItem(870);

                            newItem.isLock = true;
                            addItemToBag(newItem);
                        } else {
                            getService().npcChat(NpcName.KANATA, "Bạn đã có gia tộc đâu.");
                        }
                    }));

                    menus.add(new Menu(CMDMenu.EXECUTE, "Giấy phép nâng cấp", () -> {
                        setInput(new InputDialog(CMDInputDialog.EXECUTE, "Nhập số lần đổi", () -> {
                            if (this.clan != null) {
                                Member member = this.clan.getMemberByName(this.name);
                                if (member == null) {
                                    return;
                                }

                                String luong = input.getText();
                                if (!NinjaUtils.CheckString(luong, "^[0-9]+$")) {
                                    getService().npcChat(NpcName.KANATA, "Số lượng nhập vào không hợp lệ");
                                    return;
                                }

                                int soluong = Integer.parseInt(luong);

                                if (soluong > 50) {
                                    getService().npcChat(NpcName.KANATA, "Bạn có thể đổi tối đa 50 lần");
                                    return;
                                }
                                if (soluong < 1) {
                                    getService().npcChat(NpcName.KANATA, "Bạn phải đổi ít nhất 1 lần");
                                    return;
                                }
                                if (user.gold < 1000 * soluong) {
                                    getService().npcChat(NpcName.KANATA, "Bạn không đủ " + 1000 * soluong + " lượng!");
                                    return;
                                }

                                if (getSlotNull() < 6) {
                                    getService().npcChat(NpcName.KANATA, "Hãy chừa ra 6 chỗ trống trong hành trang");
                                    return;
                                }

                                if (user.gold < 1000 * soluong) {
                                    getService().npcChat(NpcName.KANATA, "Không đủ lượng để đổi");
                                } else {
                                    for (int i = 0; i < soluong; i++) {
                                        Item newItem = ItemFactory.getInstance().newItem(ItemName.GIAY_PHEP_NANG_CAP);
                                        newItem.isLock = true;
                                        addItemToBag(newItem);
                                    }
                                    addGold(-1000 * soluong);
                                    getService().npcChat(NpcName.KANATA, String.format("Bạn đã mua thành công %d giấy phép", soluong));
                                }
                            } else {
                                getService().npcChat(NpcName.KANATA, "Bạn đã có gia tộc đâu.");
                            }
                        }));
                        getService().showInputDialog();
                    }));


                }
                menus.add(new Menu(CMDMenu.EXECUTE, "Thăng ấn", () -> {
                    if (fashion[13] == null) {
                        serverMessage("Hãy sử dụng Ấn tộc để sử dụng chức năng này.");
                        return;
                    }
                    if (fashion[13].upgrade >= 10) {
                        serverMessage("Ấn tộc đã đạt cấp tối đa.");
                        return;
                    }
//                    int[] costs = {1000000, 1750000, 2250000, 3500000, 5200000, 7500000, 8200000, 10500000, 15000000};
                    int[] fee = {1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000};
                    int[] costs2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    int[] percents = {50, 35, 25, 20, 15, 9, 7, 5, 3};
                    int[] items = {880, 881, 882, 883, 884, 885, 886, 887, 888};
                    int index = fashion[13].upgrade - 1;
                    ItemTemplate template = ItemManager.getInstance().getItemTemplate(items[index]);
                    setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.NANG_AN_TOC, "Bạn có muốn thăng cấp " + fashion[13].template.name + " với 10 viên " + template.name + " + " + costs2[index] + " giấy phép nâng cấp + " + fee[index] + " lượng không ?"));
                    getService().openUIConfirmID();
                }));

                menus.add(new Menu(CMDMenu.EXECUTE, "Thứ 7 đen tối", () -> {//t7
                    showRankedList(7);
                }));
                if (this.clan != null && this.clan.topWarWeek != 0) {  /// t7 đen tối
                    if (this.clan.getMemberByName(this.name).getType() == Clan.TYPE_TOCTRUONG) {
                        menus.add(new Menu(CMDMenu.EXECUTE, String.format("Nhận Thưởng TOP %d", this.clan.topWarWeek), () -> {
                            RewardClan();
                        }));
                    }
                }


                menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
                    getService().showAlert("Hướng dẫn ấn tộc", "Ấn tộc chỉ xuất hiện sau khi bạn vào trong gia tộc \n\n" +
                            "Để đổi ấn tộc cần 1000 điểm cống hiến tuần bằng cách đi làm các hoạt động trong game \n\n" +
                            "Để thăng ấn tộc bạn cần : giấy phép nâng cấp + đá linh hồn\n\n" +
                            "Đá linh hồn tìm ở sự kiện \n\n" +
                            "Có thể phù phép tạo đá linh hồn từ : bụi linh hồn + trượng linh hồn");
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Hủy ấn", () -> {
                    setConfirmPopup(new ConfirmPopup(CMDConfirmPopup.HUY_AN, "Bạn có muốn Hủy ấn không?"));
                    getService().openUIConfirmID();
                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Hang động sau trường", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Nhận thưởng", () -> {
                    getService().reviewDungeon();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 35", () -> {
                    openDungeon(1);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 45", () -> {
                    openDungeon(2);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 55", () -> {
                    openDungeon(3);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 65", () -> {
                    openDungeon(4);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 75", () -> {
                    openDungeon(5);
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Cấp 95", () -> {
                    openDungeon(6);
                }));
                getService().openUIMenu();
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Lôi đài", () -> {
                if (this.user.activated == 0) {
                    service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                    return;
                }
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Thách đấu", () -> {
                    if (Arena.arenas.size() == 127) {
                        getService().npcChat(NpcName.KANATA, "Lôi dài quá tải. Vui lòng quay lại sau.");
                    } else {
                        InputDialog input = new InputDialog(CMDInputDialog.THACH_DAU, "Nhập tên nhân vật");
                        setInput(input);
                        getService().showInputDialog();
                    }
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Xem thi đấu", () -> {
                    getService().testDunageonList();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Kết quả", () -> {
                    String content = "";
                    int size = Arena.results.size();
                    size = size > 20 ? 20 : size;
                    for (int i = 0; i < size; i++) {
                        content += (i + 1) + ". " + Arena.results.get(i) + "\n";
                    }
                    getService().showAlert("Kết quả", content);
                }));
                getService().openUIMenu();
            }));

            TalentShow talentShow = MapManager.getInstance().talentShow;
            if (talentShow.opened) {
                menus.add(new Menu(CMDMenu.EXECUTE, "Ninja tài năng", () -> {
                    menus.clear();
                    int tlsFaction = talentShow.getFactionByName(name);
                    if (tlsFaction != -1) {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Tham gia", () -> {
                            TalentShow tls = MapManager.getInstance().talentShow;
                            if (!tls.opened) {
                                getService().npcChat(NpcName.KANATA, "Ninja tài năng chưa mở cửa hoặc đã kết thúc.");
                                return;
                            }
                            this.mapBeforeEnterPB = mapId;
                            int faction = tls.getFactionByName(name);

                            if (faction != -1) {
                                tls.addPlayer(name, faction);
                            }
                        }));
                    }
                    if (tlsFaction == -1) {
                        menus.add(new Menu(CMDMenu.EXECUTE, "Xem thi đấu", () -> {
                            TalentShow tls = MapManager.getInstance().talentShow;
                            if (!tls.opened) {
                                getService().npcChat(NpcName.KANATA, "Ninja tài năng chưa mở cửa hoặc đã kết thúc.");
                                return;
                            }
                            this.mapBeforeEnterPB = mapId;
                            int randPointX = NinjaUtils.nextInt(70, 650);
                            setXY((short) randPointX, (short) 384);
                            changeMap(MapName.DAU_TRUONG);
                        }));
                    }
                    getService().openUIMenu();
                }));
            }

            if (this.user.isTien()) {
                menus.add(new Menu(CMDMenu.EXECUTE, talentShow.opened ? "Tắt TLS" : "Bật TLS", () -> {
                    TalentShow tls = MapManager.getInstance().talentShow;
                    tls.opened = !tls.opened;
                }));
            }

            menus.add(new Menu(CMDMenu.EXECUTE, "Nói chuyện", () -> {
                String talk = (String) NinjaUtils.randomObject("Haha, nhà bạn cần vũ khí gì?", "Ở chỗ ta có rất nhiều binh khí có giá trị", "Hãy chọn cho mình 1 món binh khí đi");
                getService().npcChat(NpcName.KANATA, talk);
            }));
        }
    }

    private void openDungeon(int optionId) {
        int[] pb = Dungeon.INFO[optionId - 1];
        Dungeon dungeon = (Dungeon) findWorld(World.DUNGEON);
        if (dungeon != null && !dungeon.isClosed()) { /// fix hd 13/8
            if (dungeon.level != optionId - 1) {
                serverDialog("Hình trình khám phá hang động chưa kết thúc.");
                return;
            }
            if (!dungeon.isClosed()) {
                this.mapBeforeEnterPB = mapId;
                setXY((short) pb[1], (short) pb[2]);
                outZone();
                joinZone(pb[0], -1, -1);
                getService().updatePointPB();
            } else {
                serverDialog("Hình trình khám phá hang động đã kết thúc.");
            }
        } else if (countPB == 1) { /// fix hd 13/8

            if ((this.level / 10 == pb[4] / 10) || (optionId == 5 && this.level >= 70 && this.level <= 89) || (optionId == 6 && this.level >= 90)) {
                if (this.group == null) {
                    this.mapBeforeEnterPB = mapId;
                    this.countPB = 0;
                    dungeon = new Dungeon(pb[3], 3600);
                    addWorld(dungeon);
                    Dungeon.addDungeon(dungeon);
                    setXY((short) pb[1], (short) pb[2]);
                    outZone();
                    joinZone(pb[0], -1, -1);
                    if (Event.isEvent()) {
                        int itemID = Event.getEvent().randomItemID();
                        if (itemID != -1) {
                            Item itemE = ItemFactory.getInstance().newItem(itemID);
                            itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                            addItemToBag(itemE);
                        }
                    }
                } else {
                    if (group.isOpenPB) {

                        int indexParty = this.group.getIndexById(this.id);
                        Dungeon dun = (Dungeon) this.group.memberGroups.get(indexParty).find(World.DUNGEON);
                        if (dun == null) {
                            serverDialog("Không thể vào hang động.");
                            return;
                        }
                        if (dun.isClosed()) {
                            serverDialog("Hình trình khám phá hang động đã kết thúc.");
                            return;
                        }
                        this.mapBeforeEnterPB = mapId;
                        setXY((short) pb[1], (short) pb[2]);
                        addWorld(dun);
                        this.countPB = 0;
                        outZone();
                        joinZone(pb[0], -1, -1);
                        getService().updatePointPB();
                        if (Event.isEvent()) {
                            int itemID = Event.getEvent().randomItemID();
                            if (itemID != -1) {
                                Item itemE = ItemFactory.getInstance().newItem(itemID);
                                itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                                addItemToBag(itemE);
                            }
                        }
                    } else {

                        if (this.group.getIndexById(this.id) != 0) {
                            serverDialog("Phải là trưởng nhóm mới có thể mở phó bản");
                        } else {
                            if (optionId == 4 && this.group.getNumberMember() > 1) {
                                serverDialog("Bạn chỉ có thể đi một mình.");
                                return;
                            }
                            for (int i = 0; i < this.group.memberGroups.size(); i++) {
                                MemberGroup mem = this.group.memberGroups.get(i);
                                if ((mem.getChar().level / 10 == pb[4] / 10) /// dev fix hd 13/8
                                        || (optionId == 5 && mem.getChar().level >= 70 && mem.getChar().level <= 89)
                                        || (optionId == 6 && mem.getChar().level >= 90)) {
                                    continue;
                                }
                                serverDialog("Nhóm có thành viên không đủ cấp độ");
                                return;
                            }
                            this.mapBeforeEnterPB = mapId;
                            dungeon = new Dungeon(pb[3], 3600);
                            Dungeon.addDungeon(dungeon);
                            this.group.addWorld(dungeon);
                            this.group.isOpenPB = true;
                            this.countPB = 0;
                            addWorld(dungeon);
                            setXY((short) pb[1], (short) pb[2]);
                            outZone();
                            joinZone(pb[0], -1, -1);
                            if (this.group != null) { /// dev fix 13/8
                                this.group.getGroupService()
                                        .serverMessage(this.name + " đã mở cửa hang hang động sau trường cấp " + pb[4]
                                                + ", vào báo danh tại Kanata.");
                            }
                            if (Event.isEvent()) {
                                int itemID = Event.getEvent().randomItemID();
                                if (itemID != -1) {
                                    Item itemE = ItemFactory.getInstance().newItem(itemID);
                                    itemE.setQuantity(NinjaUtils.nextInt(1, 5));
                                    addItemToBag(itemE);
                                }
                            }
                        }

                    }
                }
            } else {
                serverDialog("Cấp độ không phù hợp");
            }
        } else {  /// fix hd
            serverDialog("Hôm nay đã hết lượt vào hang động");
        }
    }

    public void npcGoosho() {
        menus.add(new Menu(CMDMenu.EXECUTE, "Cửa hàng", () -> {
            getService().openUI(StoreManager.TYPE_MISCELLANEOUS);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Quầy sách", () -> {
            getService().openUI(StoreManager.TYPE_BOOK);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Thời trang", () -> {
            getService().openUI(StoreManager.TYPE_FASHION);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Gia tộc", () -> {
            getService().openUI(StoreManager.TYPE_CLAN);
        }));
    }

    public void npcShinwa() {
        if (!Config.getInstance().isShinwa()) {
            serverMessage("Tính năng đang bảo trì.");
            return;
        }
//        if (this.user.activated == 0) {
//            service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
//            return;
//        }
        menus.add(new Menu(CMDMenu.EXECUTE, "Gian hàng", () -> {
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            StallManager.getInstance().openUIMenu((Char) this);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Bán vật phẩm", () -> {
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            getService().openUI((byte) 36);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Nhận lại vật phâm", () -> {
            StallManager.getInstance().receiveItem((Char) this);
        }));
    }

    public void requestViewDetails(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            int id = ms.reader().readInt();
            Stall stall = StallManager.getInstance().findByID(viewAuctionTab);
            Item item = stall.find(id);
            if (item != null) {
                getService().requestViewDetails(id, item);
            }
        } catch (Exception e) {
            Log.logException("View detail err: ", Char.class, e);
        }
    }

    public void npcRakkii() {

        menus.add(new Menu(CMDMenu.EXECUTE, "Lật hình", () -> {

            if (this.level < 20) {
                this.serverDialog("Bạn phải trên level 20 mới có thể tham gia lật hình");
                return;
            }
            SelectCard.getInstance().open((Char) this);
        }));
        menus.add(new Menu(CMDMenu.EXECUTE, "Mã quà tặng", () -> {
            InputDialog input = new InputDialog(CMDInputDialog.MA_QUA_TANG, "Mã quà tặng");
            setInput(input);
            getService().showInputDialog();
        }));
        menus.add(new Menu(CMDMenu.VXMM_1, "Vòng quay vip"));
        menus.add(new Menu(CMDMenu.VXMM_2, "Vòng quay thường"));
    }

}