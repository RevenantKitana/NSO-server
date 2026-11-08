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
 * CharItemHandler: Phân hệ xử lý trang bị, thú cưỡi và toàn bộ nghiệp vụ sử dụng vật phẩm (Item Usage).
 * Tách biệt thành công 2,648 dòng xử lý vật phẩm khỏi Char.java (Phase 2 Refactoring).
 */
public abstract class CharItemHandler extends CharNpcHandler {

    public CharItemHandler(int id) {
        super(id);
    }

    public void useItemChangeMap(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (isDead) {
                serverDialog("Không thể sử dụng vật phẩm ở trạng thái này.");
                return;
            }
            /// fix sài khả di lệnh đi hang động
            if (mapId == 74 || this.zone.tilemap.isWorld() || zone.tilemap.isNotChangeMap() || zone.tilemap.id == MapName.KHU_BAO_DANH || zone.tilemap.id == MapName.KHU_BAO_DANH_2 || zone.tilemap.id == MapName.DAU_TRUONG) {
                serverMessage("Không thể sử dụng tính năng này tại đây.");
                return;
            }
            byte indexUI = ms.reader().readByte();
            byte indexMenu = ms.reader().readByte();
            if (bag[indexUI] != null && (bag[indexUI].id == 35 || bag[indexUI].id == 37)) {
                short map = (new short[]{1, 27, 72, 10, 17, 22, 32, 38, 43, 48})[indexMenu];
                if (isCanEnterMap(map)) {
                    short[] xy = NinjaUtils.getXY(map);
                    setXY(xy[0], xy[1]);
                    changeMap(map);
                    if (bag[indexUI].id == 35 || (bag[indexUI].id == 37 && bag[indexUI].hasExpire() && bag[indexUI].expire < (new Date()).getTime())) {
                        removeItem(indexUI, 1, true);
                    }
                } else {
                    getService().resetPoint();
                    serverDialog("Bạn chưa thể đến khu vực này. Hãy hoàn thành nhiệm vụ trước.");
                }
            }
        } catch (Exception ex) {
            Log.logException("use item change map err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public void useMount(Item item) {
        byte indexUI = (byte) item.index;
        byte index = (byte) (item.template.type - 29);

        if (index != 4) {
            if (this.mount[4] == null) {
                serverDialog("Chưa sử dụng thú cưỡi.");
                return;
            }


            int mountId = this.mount[4].id;
            int itemId = item.id;

            if (mountId == ItemName.XE_MAY) {
                if (item.id != ItemName.BO_DIEU_KHIEN && item.id != ItemName.DONG_CO_V_POWER && item.id != ItemName.DINH_VI && item.id != ItemName.BINH_NITRO) {
                    serverDialog("Trang bị xe không phù hợp.");
                    return;
                }
            }
            if (mountId == ItemName.XICH_NHAN_NGAN_LANG) {
                if (item.id != ItemName.THU_TRANG && item.id != ItemName.GIAP_THU && item.id != ItemName.YEN2 && item.id != ItemName.DAY_CUONG) {
                    serverDialog("Trang bị thú không phù hợp.");
                    return;
                }
            }
        } else {
            if (this.mount[0] != null || this.mount[1] != null || this.mount[2] != null || this.mount[3] != null) {
                serverDialog("Bạn cần phải tháo trang bị thú cưỡi đang sử dụng.");
                return;
            }
        }
        if (mount[index] != null) {
            Item it = Converter.getInstance().toItem(this.mount[index]);
            it.index = indexUI;
            it.isLock = true;
            this.bag[indexUI] = it;
        } else {
            this.bag[indexUI] = null;
        }
        Mount mount = Converter.getInstance().toMount(item);
        mount.isLock = true;
        if (index == 4 && mount.options.size() == 2) {
            mount.randomOptionMount();
        }
        this.mount[index] = mount;
        if (index == 4) {

            this.mount[4].fixMount();
        }
        getService().useItem(indexUI);
        zone.getService().loadMount((Char) this);
        setFashion();
        setAbility();
    }

    public void useEquipment(Item item) {
        byte indexUI = (byte) item.index;
        byte index = item.template.type;
        // fix vk 10x
        if (item.template.fashion > -1 && item.id != 797 && !item.template.isVk100x() && item.id != 1116 && item.id != ItemName.PET_UNG_LONG) {
            if (fashion[index] != null) {
                Item it = Converter.getInstance().toItem(fashion[index]);
                it.index = indexUI;
                it.isLock = true;
                bag[indexUI] = it;
            } else {
                bag[indexUI] = null;
            }
            Equip equip = Converter.getInstance().toEquip(item);
            equip.isLock = true;
            fashion[index] = equip;

            if (equip != null && ((equip.id >= 814 && equip.id <= 818) || equip.id == ItemName.KHAU_TRANG
                    || equip.id == ItemName.NHAT_TU_LAM_PHONG || equip.id == ItemName.THIEN_NGUYET_CHI_NU
                    || equip.id == ItemName.SHIRAIJI || equip.id == ItemName.HAJIRO
                    || equip.id == ItemName.AO_NGU_THAN || equip.id == ItemName.AO_TAN_THOI
                    || equip.id == ItemName.AO_KAGE_NAM || equip.id == ItemName.AO_KAGE_NU // ct kage
                    || equip.id == ItemName.THOI_TRANG_OBITO || equip.id == ItemName.THOI_TRANG_SAKURA)
                    && equip.options.isEmpty()) { // random chỉ số
                equip.randomOption();
            }
        } else {
            if (item.template.isTypeWeapon()) {
                if (((this.classId == 0 || this.classId == 1) && !item.template.isKiem()) || ((this.classId == 2) && !item.template.isTieu()) || ((this.classId == 3) && !item.template.isKunai()) || ((this.classId == 4) && !item.template.isCung()) || ((this.classId == 5) && !item.template.isDao()) || ((this.classId == 6) && !item.template.isQuat())) {
                    serverDialog("Vũ khí không thích hợp.");
                    return;
                }
            }
            if (classId > 0 && item.id == 194) {
                serverDialog("Vũ khí không thích hợp.");
                return;
            }
            if (index == ItemTemplate.TYPE_AOCHOANG) {
                int sys = getSys();
                if (item.id == 420 && sys != 1) {
                    return;
                }
                if (item.id == 421 && sys != 2) {
                    return;
                }
                if (item.id == 422 && sys != 3) {
                    return;
                }
            }
            if (index == ItemTemplate.TYPE_BIKIP) {
                if ((this.classId == 1 && item.id != ItemName.BI_KIP_KIEM_THUAT) || (this.classId == 2 && item.id != ItemName.BI_KIP_TIEU_THUAT) || (this.classId == 3 && item.id != ItemName.BI_KIP_KUNAI) || (this.classId == 4 && item.id != ItemName.BI_KIP_CUNG) || (this.classId == 5 && item.id != ItemName.BI_KIP_DAO) || (this.classId == 6 && item.id != ItemName.BI_KIP_QUAT) || this.classId == 0) {
                    return;
                }
            }
            if (equipment[index] != null) {
                if (index == ItemTemplate.TYPE_THUNUOI) {
                    Item itemType = equipment[index];
                    if (itemType != null && (itemType.id == 568 || itemType.id == 569 || itemType.id == 570 || itemType.id == 571)) {
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


                Item it = Converter.getInstance().toItem(equipment[index]);
                it.index = indexUI;
                it.isLock = true;
                bag[indexUI] = it;

            } else {
                bag[indexUI] = null;
            }

            Equip equip = Converter.getInstance().toEquip(item);
            equip.isLock = true;
            equipment[index] = equip;
            if (equip != null && equip.id == ItemName.PET_UNG_LONG && equip.options.size() == 2) { // random chỉ số
                equip.randomOption();
            }
            if (taskMain != null) {
                if (taskId == TaskName.NV_LAN_DAU_DUNG_KIEM && taskMain.index == 0 && item.template.isTypeWeapon()) {
                    taskNext();
                } else if (taskId == TaskName.NV_NANG_CAP_TRANG_BI) {
                    if (item.upgrade > 0) {
                        if (taskMain.index == 1 && item.template.isTypeWeapon()) {
                            taskNext();
                        } else if (taskMain.index == 2 && item.template.isTypeAdorn()) {
                            taskNext();
                        } else if (taskMain.index == 3 && item.template.isTypeClothe()) {
                            taskNext();
                        }
                    }
                }
            }
        }
        getService().useItem(indexUI);
        if (index == ItemTemplate.TYPE_THUNUOI && item.template.fashion == -1) {
            em.setEffectPet();
        }
        setAbility();
        setFashion();
    }

    public void useItemTaskWait(Item item) {
        try {
            if (item.id == ItemName.TUAN_THU_LENH) {
                if (!Event.isNoel()) {
                    getService().endWait("Vật phẩm chỉ được sử dụng ở sự kiện Noel.");
                    return;
                }
                if (zone.tilemap.isNotSummon()) {
                    getService().endWait("Không thể triệu hồi ở đây.");
                    return;
                }
                if (this.mob == null) {
                    MobTemplate template = MobManager.getInstance().find(230);
                    Mob monster = new Mob(127, (short) template.id, template.hp, template.level, this.x, this.y, false, template.isBoss(), zone);
                    monster.addCharId(this.id);
                    this.mob = monster;
                    getService().addMonster(monster);
                    removeItem(item.index, 1, true);
                    getService().endWait();
                } else {
                    getService().endWait("Vui lòng tiêu diệt Bosss vua tuần lộc kia trước.");
                }
            } else {
                if (taskMain != null) {
                    if (this.isCatchItem) {
                        return;
                    }
                    if (item.id == ItemName.CAN_CAU) {
                        if (taskMain.taskId == TaskName.NV_REN_LUYEN_Y_CHI && taskMain.index == 1) {
                            if (mapId == MapName.LANG_CHAKUMI && this.x >= 72 && this.x <= 288 && this.y == 360) {
                                if (getSlotNull() == 0) {
                                    warningBagFull();
                                    return;
                                }
                                isCatchItem = true;
                                threadPool.submit(new Thread(new Runnable() {

                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            if (!isCleaned) {
                                                int rd = NinjaUtils.nextInt(10);
                                                if (rd == 0) {
                                                    taskNext();
                                                    Item item = ItemFactory.getInstance().newItem(ItemName.GUOC_GO);
                                                    item.isLock = true;
                                                    item.setQuantity(1);
                                                    addItemToBag(item);
                                                } else {
                                                    getService().endWait("Vẫn chưa câu được vật phẩm.");
                                                }
                                                removeItem(item.index, 1, true);
                                                isCatchItem = false;
                                            }
                                        } catch (Exception ex) {
                                            serverDialog(ex.getMessage());
                                        }
                                    }
                                }));
                            } else {
                                getService().endWait("Vị trí không thích hợp.");
                            }
                        }
                    } else if (item.id == ItemName.DIA_DO2 || item.id == ItemName.DIA_DO3 || item.id == ItemName.DIA_DO4) {
                        if (taskMain.taskId == TaskName.NV_TRUY_TIM_BAO_VAT && taskMain.index == 1) {
                            ImageMap imageMap = Server.IMAGE_MAP_ARR[item.id - ItemName.DIA_DO2][0];
                            if (imageMap.getMapID() != mapId) {
                                Map map = MapManager.getInstance().find(imageMap.getMapID());
                                getService().showAlert(item.template.name, String.format("Bạn đang ở rất xa bảo vật. Hãy đi đến khu vực ̀%s và xem tiếp địa đồ", map.tilemap.name));
                                return;
                            }
                            if (this.x >= imageMap.getX() && this.x <= imageMap.getX() + imageMap.getW() && this.y >= imageMap.getY() && this.y <= imageMap.getY() + imageMap.getH()) {
                                if (getSlotNull() == 0) {
                                    warningBagFull();
                                    return;
                                }
                                isCatchItem = true;
                                threadPool.submit(new Thread(new Runnable() {

                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            if (!isCleaned) {
                                                int rd = NinjaUtils.nextInt(3);
                                                if (rd == 0) {
                                                    removeItem(item.index, 1, true);
                                                    taskNext();
                                                    Item item = ItemFactory.getInstance().newItem(ItemName.DA_CAP_9);
                                                    item.isLock = true;
                                                    item.setQuantity(1);
                                                    addItemToBag(item);
                                                    getService().endWait("Bạn nhận ược bảo vật.");
                                                } else {
                                                    getService().endWait("Bảo vật đang ở đây, hãy đào sâu hơn nữa.");
                                                }
                                                isCatchItem = false;
                                            }
                                        } catch (Exception ex) {
                                            Log.logException("dao dia do err: ", Char.class, ex);
                                            serverDialog(ex.getMessage());
                                        }
                                    }
                                }));

                            } else {
                                getService().showAlert(item.template.name, "Bảo vật đang ở quanh đây, hãy tìm đúng vị trí trong hình của địa đồ và đào lấy bảo vật.");
                            }
                        }
                    } else if (item.id == ItemName.BINH_RONG) {
                        if (taskMain.index == 1) {
                            if ((mapId == MapName.HANG_HA && taskId == TaskName.NV_LAY_NUOC_HANG_SAU) || (mapId == MapName.DINH_ICHIDAI && taskId == TaskName.NV_GIUP_DO_DAN_LANG)) {

                                if (zone.tilemap.tileTypeAt(this.x, this.y - 12, TileMap.T_WATERFLOW)) {
                                    if (getSlotNull() > 0) {
                                        isCatchItem = true;
                                        threadPool.submit(new Thread(new Runnable() {

                                            public void run() {
                                                try {
                                                    Thread.sleep(3000);
                                                    if (!isCleaned) {
                                                        if (!isFailure) {
                                                            removeItem(item.index, 1, true);
                                                            Item binhNuoc = ItemFactory.getInstance().newItem(ItemName.BINH_NUOC);
                                                            binhNuoc.isLock = true;
                                                            addItemToBag(binhNuoc);
                                                            updateTaskCount(1);
                                                            getService().endWait("Bạn nhận được bình nước");
                                                        }
                                                        isFailure = false;
                                                        isCatchItem = false;

                                                    }
                                                } catch (Exception ex) {
                                                    Log.logException("muc nuoc err: ", Char.class, ex);
                                                    serverDialog(ex.getMessage());
                                                }
                                            }
                                        }));
                                    } else {
                                        warningBagFull();
                                    }
                                } else {
                                    serverDialog("Hãy đứng chỗ có nước để lấy");
                                }
                            } else {
                                if (taskId == TaskName.NV_LAY_NUOC_HANG_SAU) {
                                    serverDialog("Hãy đến Hang Ha để lấy nước");
                                } else {
                                    serverDialog("Hãy đến Đỉnh Ichidai để lấy nước");
                                }
                            }
                        } else {
                            serverDialog("Bạn không làm nhiệm vụ này");
                        }
                    }
                }
            }
        } finally {
            if (!isCatchItem) {
                getService().useItem(item.index);
            }
        }
    }

    private void useItemTypeOrder(Item item) {


        if (Event.getLunarNewYear() != null) {
            switch (item.id) {
                case ItemName.LAM_SON_DA:
                case ItemName.TRUC_BACH_THIEN_LU:
                    if (getSlotNull() == 0) {
                        warningBagFull();
                        return;
                    }
                    Event.useVipEventItem((Char) this, item.id == ItemName.LAM_SON_DA ? 1 : 2, Event.getLunarNewYear().vipItems);
                    removeItem(item.index, 1, true);
                    return;
            }
        }
        if (item.id == ItemName.VI_THU_LENH) {
            if (MapManager.getInstance().vithu != null) {
//                if (NinjaUtils.isToday(DayOfWeek.SUNDAY)) { /// vĩ thú mở vào chủ nhật
//                    serverDialog("Vĩ thú chỉ mở cửa vào chủ nhật hằng tuần");
//                    return;
//                }
                if (!MapManager.getInstance().vithu.dangKyVithu) {
                    this.getService().showAlert("Hướng dẫn",
                            "-Các nhẫn giả có thể dùng vĩ thú lệnh tham gia phòng chờ từ 20h55-21h \n\n"
                                    + "-Khi đến 21h các nhẫn giả có thể sang map và từ sau 21h các bạn không thể dùng vĩ thú lệnh để tham gia");
                    return;
                }
                if (this.level < 50) {
                    this.getService().serverDialog("Trên level 50 mới có thể tham gia chức năng này!");
                    return;
                }
                if (this.typePk != Char.PK_NORMAL) {
                    this.getService().serverDialog("Vui lòng về trạng thái pk thường để tham gia");
                    return;
                }
                if (!isHuman) {
                    warningClone();
                    return;
                }
                this.mapBeforeEnterPB = this.mapId;
                short[] xy = NinjaUtils.getXY(171);
                this.setXY(xy);
                this.changeMap(171);
                removeItem(item.index, 1, true);
            } else {
                this.getService().showAlert("Hướng dẫn",
                        "Thời gian sử dụng vĩ thú lệnh : 20h55 tới 21h\n" +
                                "Quá 21h không thể sử dụng \n" +
                                "\n" +
                                "Thời gian trong hang : 60 phút");
            }
        } else if (item.id == 1074) {
            Effect eff2 = new Effect(47, 1800000, 0);
            em.setEffect(eff2);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TRUNG_VI_THU) {
            if (getSlotNull() == 0) {
                getService().serverDialog("Hành trang không đủ chỗ trống");
                return;
            }
            if (this.yen < 1000000) {
                getService().serverDialog("Hành trang không đủ 1tr yên để giải phong ấn");
                return;
            }
            final short[] arId = {ItemName.TAMAKIRO, ItemName.TAMAMURA, ItemName.TAMADAI};
            final short idI = arId[NinjaUtils.nextInt(arId.length)];
            Item itemup = ItemFactory.getInstance().newItem(idI);
            addItemToBag(itemup);
            addYen(-1000000);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TAMAKIRO) {
            if (getSlotNull() == 0) {
                getService().serverDialog("Hành trang không đủ chỗ trống");
                return;
            }
            final short[] arId = {994, 995, 996, 997, 998, 999, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011};
            final short idI = arId[NinjaUtils.nextInt(arId.length)];
            Item itemup = ItemFactory.getInstance().newItem(idI);
            addItemToBag(itemup);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TAMAMURA) {
            if (getSlotNull() == 0) {
                getService().serverDialog("Hành trang không đủ chỗ trống");
                return;
            }

            final short[] arId = {1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029};
            final short idI = arId[NinjaUtils.nextInt(arId.length)];
            Item itemup = ItemFactory.getInstance().newItem(idI);
            addItemToBag(itemup);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TAMADAI) {
            if (getSlotNull() == 0) {
                getService().serverDialog("Hành trang không đủ chỗ trống");
                return;
            }

            final short[] arId = {1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047};
            final short idI = arId[NinjaUtils.nextInt(arId.length)];
            Item itemup = ItemFactory.getInstance().newItem(idI);
            addItemToBag(itemup);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.LENH_BAI_HOAN_THANH) {
            if (!isHuman) {
                warningClone();
                return;
            }
            finishTask(true);
            serverDialog("Đã bỏ qua nhiệm vụ hiện tại của bạn");
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.THIEN_BIEN_LENH) {
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, (!isAutoPlay ? "Bật" : "Tắt"), () -> {
                this.isAutoPlay = !this.isAutoPlay;
                if (this.isAutoPlay) {
                    getService().turnOnAuto();
                } else {
                    getService().turnOffAuto();
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Cài đặt", () -> {
                menus.clear();
                menus.add(new Menu(CMDMenu.EXECUTE, "Phạm vi: " + (this.range <= 0 || this.range > 360 ? "Toàn map" : this.range), () -> {
                    menus.clear();
                    menus.add(new Menu(CMDMenu.EXECUTE, "Toàn map", () -> {
                        setRange(-1);
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Phạm vi 240", () -> {
                        setRange(240);
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Phạm vi 360", () -> {
                        setRange(360);
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "Tùy chỉnh", () -> {
                        InputDialog input = new InputDialog(CMDInputDialog.EXECUTE, "Phạm vi", () -> {
                            try {
                                int range = this.input.intValue();
                                if (range > 360) {
                                    range = -1;
                                }
                                setRange(range);
                            } catch (Exception e) {
                                getService().serverDialog(e.getMessage());
                            }
                        });
                        setInput(input);
                        getService().showInputDialog();
                    }));
                    getService().openUIMenu();
                }));
                menus.add(new Menu(CMDMenu.EXECUTE, "Tự động nhặt: " + AUTO_PICK_ITEM[getTypeAutoPickItem()], () -> {
                    menus.clear();
                    int i = 0;
                    for (String caption : AUTO_PICK_ITEM) {
                        final int type = i;
                        menus.add(new Menu(CMDMenu.EXECUTE, caption, () -> {
                            setTypeAutoPickItem(type);
                        }));
                        i++;
                    }
                    getService().openUIMenu();
                }));
                getService().openUIMenu();
            }));
            getService().openUIMenu();
        } else if (item.id == ItemName.NUOC_DIET_KHUAN) {// sự kiện tháng 7
            if (isInfected()) {
                cure();
                addExp(5000000);
                if (this.getSlotNull() > 0) {
                    Item itm = ItemFactory.getInstance().newItem(RandomItem.TRE_XANH_TRAM_DOT);
                    itm.initExpire();
                    addItemToBag(itm);
                    removeItem(item.index, 1, true);
                }
            } else {
                serverMessage("Bạn không bị nhiễm bệnh");
            }
        } else if (item.isPieceCollection()) {
            pieceTogether(item);
        } else if (item.id == ItemName.TIEN_HOA_DAN) {
            if (clan != null) {
                ArrayList<ThanThu> thanThus = new ArrayList<>();
                for (ThanThu thanThu : clan.thanThus) {
                    if (thanThu.getEggHatchingTime() == -1 && thanThu.getStars() < ThanThu.MAX_STAR) {
                        thanThus.add(thanThu);
                    }
                }
                if (thanThus.size() == 1) {
                    ThanThu thanThu = thanThus.get(0);
                    if (item.has()) {
                        if (thanThu.getLevel() < 100) {
                            serverMessage("Thần thú phải đạt cấp 100 mới có thể tiến hóa");
                            return;
                        }
                        if (thanThu.getStars() == 3) {
                            serverMessage("Thần thú đã tiến hóa  cấp tối đa");
                            return;
                        }
                        int rd = NinjaUtils.nextInt(100);
                        if (rd < 5) {
                            thanThu.evolution();
                            serverMessage(String.format("Tiến hóa thành công! %s đã đạt %d sao", thanThu.getName(), thanThu.getStars()));
                        } else {
                            serverMessage("Tiến hóa thất bại!");
                        }
                        removeItem(item.index, 1, true);
                    }
                } else {
                    menus.clear();
                    for (ThanThu thanThu : thanThus) {
                        if (thanThu.getEggHatchingTime() == -1 && thanThu.getLevel() == ThanThu.MAX_LEVEL && thanThu.getStars() < ThanThu.MAX_STAR) {
                            menus.add(new Menu(CMDMenu.EXECUTE, thanThu.getName(), () -> {
                                if (thanThu.getLevel() < 100) {
                                    serverMessage("Thần thú phải đạt cấp 100 mới có thể tiến hóa");
                                    return;
                                }
                                if (thanThu.getStars() == 3) {
                                    serverMessage("Thần thú đã tiến hóa  cấp tối đa");
                                    return;
                                }
                                if (item.has()) {
                                    int rd = NinjaUtils.nextInt(100);
                                    if (rd < 5) {
                                        thanThu.evolution();
                                        serverMessage(String.format("Tiến hóa thành công! %s đã đạt %d sao", thanThu.getName(), thanThu.getStars()));
                                    } else {
                                        serverMessage("Tiến hóa thất bại!");
                                    }
                                    removeItem(item.index, 1, true);
                                }
                            }));
                        }
                    }
                    if (menus.size() > 0) {
                        getService().openUIMenu();
                    }
                }

            } else {
                getService().serverDialog("Hãy tham gia vào gia tộc để thực hiện");
            }
        } else if (item.id == ItemName.HUYET_LONG_NGU || item.id == ItemName.TUYET_SA_NGU || item.id == ItemName.LINH_TAM_NGU) {
            if (clan != null) {
                ArrayList<ThanThu> thanThus = new ArrayList<>();
                for (ThanThu thanThu : clan.thanThus) {
                    if (thanThu.getEggHatchingTime() == -1 && thanThu.getLevel() < ThanThu.MAX_LEVEL) {
                        thanThus.add(thanThu);
                    }
                }
                if (thanThus.isEmpty()) {
                    serverMessage("Thần thú đã đạt max level, hãy nâng sao");
                    return;
                }
                if (thanThus.size() > 1) {
                    menus.clear();
                    for (ThanThu thanThu : thanThus) {
                        menus.add(new Menu(CMDMenu.EXECUTE, thanThu.getName(), () -> {
                            if (item.has()) {
                                int exp = 50;
                                if (item.id == ItemName.TUYET_SA_NGU) {
                                    exp = 150;
                                } else if (item.id == ItemName.LINH_TAM_NGU) {
                                    exp = 1000;
                                }
                                thanThu.addExp(exp);
                                removeItem(item.index, 1, true);
                            }
                        }));
                    }
                    getService().openUIMenu();
                    return;
                }
                if (thanThus.size() == 1) {
                    ThanThu thanThu = thanThus.get(0);
                    int exp = 100;
                    if (item.id == ItemName.TUYET_SA_NGU) {
                        exp = 200;
                    } else if (item.id == ItemName.LINH_TAM_NGU) {
                        exp = 400;
                    }
                    thanThu.addExp(exp);
                    removeItem(item.index, 1, true);
                }
            } else {
                getService().serverDialog("Hãy tham gia vào gia tộc để thực hiện");
            }
        } else if (item.id == ItemName.TRIEU_HOI_THU_THAN) {
            if (clan != null) {
                menus.clear();
                for (ThanThu thanThu : clan.thanThus) {
                    if (thanThu.getEggHatchingTime() == -1) {
                        menus.add(new Menu(CMDMenu.TRIEU_HOI_THAN_THU, thanThu.getName(), (int) thanThu.getType()));
                    }
                }
                if (menus.size() > 0) {
                    getService().openUIMenu();
                }
            } else {
                getService().serverDialog("Hãy tham gia vào gia tộc để thực hiện");
            }
        } else if (item.id == ItemName.HOA_THIEN_DIEU || item.id == ItemName.HOA_DA_YEN) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            RandomCollection<Integer> rc = RandomItem.LINH_VAT;
            Event.useVipEventItem((Char) this, item.id == ItemName.HOA_THIEN_DIEU ? 1 : 2, rc);
            removeItem(item.index, 1, true);
        } else if (item.id >= 895 && item.id <= 901) {
            if (Events.event != Events.SUMMER) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện hè.");
                return;
            }
            Npc npc = zone.getNpc(18);

            if (npc == null) {
                serverMessage("Vui lòng tới làng chài, gặp bà Rei để thương thảo về mặt hàng này.");
                return;
            }

            int distance = NinjaUtils.getDistance(npc.cx, npc.cy, this.x, this.y);
            if (distance > 100) {
                serverMessage("Hãy lại gần gặp bà Rei để thương thảo về mặt hàng này.");
                return;
            }
            Events.sellFish((Char) this, item.id, 1);
        } else if (item.id == ItemName.DIEU_GIAY || item.id == ItemName.DIEU_VAI) {
            if (Events.event != Events.SUMMER) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện hè.");
                return;
            }
            int priceGold = 0;
            int[][] itemRequires = new int[][]{{item.id, 1}};
            RandomCollection<Integer> rc = item.id == ItemName.DIEU_GIAY ? RandomItem.TRE_XANH_TRAM_DOT : RandomItem.TRE_VANG_TRAM_DOT;
            Events.useEventItem((Char) this, 1, itemRequires, priceGold, rc);
        } else if (item.id == ItemName.VAN_NGU_CAU) {
            if (this.isCatchItem) {
                return;
            }
            if (zone.tilemap.tileTypeAt(this.x, this.y - 12, TileMap.T_WATERFLOW)) {
                if (mapId == MapName.LANG_CHAI) {
                    List<Char> chars = zone.getChars();
                    int num = 0;
                    Char closet = null;
                    int closetD = -1;
                    for (Char c : chars) {
                        if (c == this) {
                            continue;
                        }
                        int d = NinjaUtils.getDistance(c.x, c.y, this.x, this.y);
                        if (d < 200) {
                            num++;
                        }
                        if (closetD == -1 || d < closetD) {
                            closet = c;
                        }

                    }
                    int indexDeCom = getIndexItemByIdInBag(ItemName.DE_COM);
                    int indexGiunDat = getIndexItemByIdInBag(ItemName.GIUN_DAT);
                    if (indexDeCom == -1 && indexGiunDat == -1) {
                        List<String> list = new ArrayList<>();
                        list.add("Hết mồi rồi kìa bạn ơi");
                        list.add("Hết mồi rồi! ra mua của ông Goshoo để câu tiếp thôi");
                        if (num > 3) {
                            list.add("Ai cho xin ít mồi câu với");
                        }
                        if (closet != null) {
                            list.add(String.format("Bạn @%s cho mình xin ít mồi được không ?", closet.name));
                        }
                        int rdIndex = NinjaUtils.nextInt(list.size());
                        getService().endDlg(true);
                        zone.getService().chat(this.id, list.get(rdIndex));
                        return;
                    }
                    int num2 = num;
                    if (getSlotNull() > 0) {
                        removeItem(item.index, 1, true);
                        if (indexGiunDat != -1) {
                            removeItem(indexGiunDat, 1, true);
                        } else {
                            removeItem(indexDeCom, 1, true);
                        }
                        isCatchItem = true;
                        Char _char = (Char) this;
                        threadPool.submit(new Thread(new Runnable() {
                            // tỉ lệ câu exp thần thú
                            public void run() {
                                try {
                                    getService().endDlg(true);
                                    serverMessage("Đang thả câu...");
                                    Thread.sleep(1000L);
                                    if (!isCleaned) {
                                        int percentNotHit = 50;
                                        if (indexGiunDat == -1) {
                                            percentNotHit = 75;
                                        }
                                        percentNotHit -= (zone.getNumberChar() - 1);
                                        percentNotHit += num2 * 5;
                                        if (numberMissed > 20) {
                                            percentNotHit /= 2;
                                        }
                                        RandomCollection<Integer> rd = new RandomCollection<>();
                                        rd.add(percentNotHit, -1);
                                        rd.add(10 + numberMissed / 3, ItemName.HUYET_LONG_NGU);
                                        rd.add(5 + numberMissed / 4, ItemName.TUYET_SA_NGU);
                                        rd.add(1 + numberMissed / 5, ItemName.LINH_TAM_NGU);
                                        int id = rd.next();
                                        if (id == -1) {
                                            List<String> list = new ArrayList<>();
                                            list.add("Hụt mất rồi :(");
                                            if (numberMissed >= 3) {
                                                list.add("Lại hụt mất rồi");
                                            }
                                            if (numberMissed >= 6) {
                                                list.add(String.format("Hụt %d lần rồi", numberMissed));
                                            }
                                            if (numberMissed >= 10) {
                                                list.add("Chắc khó câu lắm đây ToT");
                                            }
                                            if (numberMissed >= 14) {
                                                list.add("Nản quá! cá ơi cắn đi mồi đi mà");
                                            }
                                            int rdIndex = NinjaUtils.nextInt(list.size());
                                            zone.getService().chat(_char.id, list.get(rdIndex));
                                            numberMissed++;
                                        } else {
                                            List<String> list = new ArrayList<>();
                                            Item item2 = ItemFactory.getInstance().newItem(id);
                                            addItemToBag(item2);
                                            list.add(String.format("Dính rồi! %s", item2.template.name));
                                            if (numberMissed == 0) {
                                                list.add(String.format("Một phát ăn ngay %s", item2.template.name));
                                            }
                                            if (numberMissed > 5) {
                                                list.add("Cuối cùng cũng câu được một con");
                                            }
                                            if (id == ItemName.LINH_TAM_NGU) {
                                                if (numberMissed > 10) {
                                                    list.add("Haha! không uổng công");
                                                } else {
                                                    list.add(String.format("Hên qua! thả vài lần mà đã câu được %s", item2.template.name));
                                                }
                                            }
                                            int rdIndex = NinjaUtils.nextInt(list.size());
                                            zone.getService().chat(_char.id, list.get(rdIndex));
                                            numberMissed = 0;
                                        }
                                    }
                                    isCatchItem = false;
                                } catch (Exception ex) {
                                    Log.logException("use item 'Van Ngu Cau' err: ", Char.class, ex);
                                    serverDialog(ex.getMessage());
                                }
                            }
                        }));
                    } else {
                        warningBagFull();
                    }
                } else {
                    getService().endDlg(true);
                    zone.getService().chat(this.id, "Ở đây quá tầm thường để câu cá hiếm");
                }
            } else {
                getService().endDlg(true);
                zone.getService().chat(this.id, "Sao mình có thể thông minh vậy được");
            }

        } else if (item.id == ItemName.CAN_CAU_CA) {
            if (Events.event != Events.SUMMER) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện hè.");
                return;
            }
            if (this.isCatchItem) {
                return;
            }
            if (zone.tilemap.tileTypeAt(this.x, this.y - 12, TileMap.T_WATERFLOW)) {
                if (getSlotNull() > 0) {
                    removeItem(item.index, 1, true);
                    isCatchItem = true;
                    Char _char = (Char) this;
                    threadPool.submit(new Thread(new Runnable() {
                        public void run() {
                            try {
                                getService().endDlg(true);
                                serverMessage("Đang thả câu...");
                                Thread.sleep(20L);
                                if (!isCleaned) {
                                    int[] idItem = {895, 896, 899, 898, 897, 900, 901};
                                    int[] percent = {20, 20, 20, 20, 10, 5, 5};
                                    int index = NinjaUtils.randomWithRate(percent, 100);
                                    int id = idItem[index];

                                    Item item2 = ItemFactory.getInstance().newItem(id);
                                    addItemToBag(item2);
                                    addExp(5000000);
                                    if (percent[index] <= 10) {
                                        zone.getService().chat(_char.id, "Haha! Được một con " + item2.template.name + " rồi nè!");
                                    }
//                                     addEventPoint(1, Events.TOP_FISHING);
                                }
                                isCatchItem = false;
                            } catch (Exception ex) {
                                Log.logException("use item 'Can Cau Ca' err: ", Char.class, ex);
                                serverDialog(ex.getMessage());
                            }
                        }
                    }));
                    this.getEventPoint().addPoint(He.TOP_CAU_CA, 1);
                    this.getEventPoint().addPoint(EventPoint.DIEM_TIEU_XAI, 1);
                } else {
                    warningBagFull();
                }
            } else {
                serverDialog("Hãy đến chỗ có cá để câu");
            }
        } else if (item.id == 890 || item.id == 891 || item.id == 892) {
            menus.clear();
            if (item.id == 890) {
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 2", 880));
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 3", 881));
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 4", 882));
            } else if (item.id == 891) {
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 5", 883));
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 6", 884));
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 7", 885));
            } else if (item.id == 892) {
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 8", 886));
                menus.add(new Menu(CMDMenu.UPGRADE_SOUL_STONE, "Ghép cấp 9", 887));
            }
            getService().openUIMenu();
        } else if (item.id == ItemName.LAM_THAO_DUOC) {
            Effect eff = new Effect(23, 5 * 60 * 1000, 0);
            em.setEffect(eff);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.SUNG_LUC || item.id == ItemName.CUNG || item.id == ItemName.KIEM_BA_CANH || item.id == ItemName.BONG) {
            if (Events.event != Events.SEA_GAME) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện SeaGame.");
                return;
            }
            int priceGold = 0;
            int[][] itemRequires;
            RandomCollection<Integer> rc = RandomItem.TRE_VANG_TRAM_DOT;
            if (item.id == ItemName.BONG) {
                itemRequires = new int[][]{{ItemName.BONG, 1}};
                priceGold = 20;
            } else if (item.id == ItemName.KIEM_BA_CANH) {
                itemRequires = new int[][]{{ItemName.KIEM_BA_CANH, 1}};
                priceGold = 20;
            } else if (item.id == ItemName.SUNG_LUC) {
                itemRequires = new int[][]{{ItemName.SUNG_LUC, 1}, {ItemName.VIEN_DAN, 7}};
                rc = RandomItem.TRE_XANH_TRAM_DOT;
            } else {
                itemRequires = new int[][]{{ItemName.CUNG, 1}, {ItemName.MUI_TEN, 10}};
                rc = RandomItem.TRE_XANH_TRAM_DOT;
            }

            Events.useEventItem((Char) this, 1, itemRequires, priceGold, rc);
        } else if (item.id == ItemName.CUP_BAC || item.id == ItemName.CUP_VANG) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            if (Events.event != Events.SEA_GAME) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện SeaGame.");
                return;
            }

            RandomCollection<Integer> rc = RandomItem.CUP_VANG;
            Event.useVipEventItem((Char) this, item.id == ItemName.CUP_VANG ? 2 : 1, rc);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.DE_NGOC || item.id == ItemName.BUOM_VANG || item.id == ItemName.BO_VANG) {
            serverDialog("Hãy giao cho Hashimoto ở Bờ Biển Oura để nhận đuợc vật phẩm quý giá");
        } else if (item.id == ItemName.KHAI_THU_LENH) {
            if (openMoreOptionForMount()) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.TRE_VANG_TRAM_DOT || item.id == ItemName.THAU_THIT_CA) {
            if (Events.event != Events.HUNG_KING) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện giỗ tổ hùng vương.");
                return;
            }
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            addExp(5000000);
            if (NinjaUtils.nextBoolean()) {
                RandomCollection<Integer> rc = RandomItem.TRE_VANG_TRAM_DOT;
                int itemId = rc.next();
                Item itm = ItemFactory.getInstance().newItem(itemId);
                itm.initExpire();
                if (itm.id == ItemName.THONG_LINH_THAO) {
                    itm.setQuantity(NinjaUtils.nextInt(5, 10));
                }

                if (itemId == ItemName.BAT_BAO || itemId == ItemName.RUONG_BACH_NGAN || itemId == ItemName.RUONG_HUYEN_BI) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng " + item.template.name + " nhận được " + itm.template.name);
                }

                addItemToBag(itm);
            }
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TRE_XANH_TRAM_DOT || item.id == ItemName.THAU_RAU_CU) {
            if (Events.event != Events.HUNG_KING) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện giỗ tổ hùng vương.");
                return;
            }
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            addExp(3000000);
            if (NinjaUtils.nextBoolean()) {
                RandomCollection<Integer> rc = RandomItem.TRE_XANH_TRAM_DOT;
                int itemId = rc.next();
                Item itm = ItemFactory.getInstance().newItem(itemId);
                itm.initExpire();
                if (itm.id == ItemName.THONG_LINH_THAO) {
                    itm.setQuantity(NinjaUtils.nextInt(5, 10));
                }

                if (itemId == ItemName.BAT_BAO || itemId == ItemName.RUONG_BACH_NGAN || itemId == ItemName.RUONG_HUYEN_BI) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng " + item.template.name + " nhận được " + itm.template.name);
                }

                addItemToBag(itm);
            }
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.GIO_HOA_8_3) {
            if (Events.event != Events.WOMAN_DAY) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện 8/3.");
                return;
            }
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            if (!this.notReceivedExp) {
                addExp(5000000);
            }
            if (NinjaUtils.nextBoolean()) {
                RandomCollection<Integer> rc = RandomItem.BANH_KHUC_CAY_CHOCOLATE;
                int itemId = rc.next();
                Item itm = ItemFactory.getInstance().newItem(itemId);
                itm.initExpire();

                if (itemId == ItemName.BAT_BAO || itemId == ItemName.RUONG_BACH_NGAN || itemId == ItemName.RUONG_HUYEN_BI) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng " + item.template.name + " nhận được " + itm.template.name);
                }
                addItemToBag(itm);
            }
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.GIAY_RACH || item.id == ItemName.GIAY_BAC || item.id == ItemName.GIAY_VANG) {
            int yen = 0;
            if (item.id == ItemName.GIAY_RACH) {
                yen = NinjaUtils.nextInt(20000, 100000);
            } else if (item.id == ItemName.GIAY_BAC) {
                yen = NinjaUtils.nextInt(100000, 500000);
            } else if (item.id == ItemName.GIAY_VANG) {
                yen = NinjaUtils.nextInt(500000, 2000000);
            }
            addYen(yen);
            serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(yen) + " yên");
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.RUONG_CHIEN_TRUONG) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            int indexItem2 = this.getIndexItemByIdInBag(ItemName.KHOA_RUONG);
            RandomCollection<Integer> rand = RandomItem.RUONG_CHIEN_TRUONG;
            int itemId = rand.next();
            if (indexItem2 != -1 && itemId == -1) {
                while (itemId == -1) {
                    itemId = rand.next();
                }
            }  // sử dụng rương ra
            if (itemId != -1) {
                Item itm = ItemFactory.getInstance().newItem(itemId);
                if (itemId == ItemName.SON_TINH || itemId == ItemName.THUY_TINH) {
                    itm.expire = System.currentTimeMillis() + (long) (86400000);
                    itm.options.add(new ItemOption(0, NinjaUtils.nextInt(3000, 5000)));
                    itm.options.add(new ItemOption(6, NinjaUtils.nextInt(3000, 5000)));
                }

                addItemToBag(itm);
                if (itemId == ItemName.BAT_BAO || itemId == ItemName.RUONG_BACH_NGAN) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng "
                            + item.template.name + " nhận được " + itm.template.name);
                }
            } else {
                int[] yens = {500000, 1000000, 2000000, 5000000, 100000000};
                int yen = yens[NinjaUtils.randomWithRate(new int[]{80, 30, 15, 5, 1})];
                addYen(yen);
                serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(yen) + " yên");
                if (yen == 100000000) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng " + item.template.name + " nhận được 100 triệu yên");
                }
            }

            removeItem(item.index, 1, true);
            if (indexItem2 != -1) {
                removeItem(indexItem2, 1, true);
            }

            //            (logic là 100 mảnh sách cũ đổi được svc 120, 100 mảnh sách mới đổi được svc 125)

        } else if (item.id == ItemName.MANH_SACH_MOI) { // skill 125
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            if (!item.has(100)) {
                serverMessage("Bạn cần thu thập đủ 100 mảnh sách cổ.");
                return;
            }
            RandomCollection<Integer> rand = RandomItem.SACH_VO_CONG_150;
            int id = rand.next();
            Item itm = ItemFactory.getInstance().newItem(id);
            addItemToBag(itm);
            removeItem(item.index, 100, true);

        } else if (item.id == ItemName.MANH_SACH_CO) { // skill 120
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            if (!item.has(100)) {
                serverMessage("Bạn cần thu thập đủ 100 mảnh sách cổ.");
                return;
            }
            RandomCollection<Integer> rand = RandomItem.SACH_VO_CONG_120;
            int id = rand.next();
            Item itm = ItemFactory.getInstance().newItem(id);
            addItemToBag(itm);
            removeItem(item.index, 100, true);

            // đổi trang bị 100
        } else if (item.id >= 1177 && item.id <= 1190) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            int[] arrItem = null;
            if (item.id == ItemName.MANH_NON_100_NAM) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.NON_100_NAM};
            } else if (item.id == ItemName.MANH_NON_100_NU) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.NON_100_NU};
            } else if (item.id == ItemName.MANH_AO_100_NAM) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.AO_100_NAM};
            } else if (item.id == ItemName.MANH_AO_100_NU) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.AO_100_NU};
            } else if (item.id == ItemName.MANH_QUAN_100_NAM) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.QUAN_100_NAM};
            } else if (item.id == ItemName.MANH_QUAN_100_NU) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.QUAN_100_NU};
            } else if (item.id == ItemName.MANH_GANG_100_NAM) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.GANG_100_NAM};
            } else if (item.id == ItemName.MANH_GANG_100_NU) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.GANG_100_NU};
            } else if (item.id == ItemName.MANH_GIAY_100_NAM) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.GIAY_100_NAM};
            } else if (item.id == ItemName.MANH_GIAY_100_NU) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.GIAY_100_NU};
            } else if (item.id == ItemName.MANH_PHU_100) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.PHU_100};
            } else if (item.id == ItemName.MANH_BOI_100) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.BOI_100};
            } else if (item.id == ItemName.MANH_NHAN_100) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.NHAN_100};
            } else if (item.id == ItemName.MANH_LIEN_100) {
                if (!item.has(1000)) {
                    getService().serverDialog("Bạn không đủ 1000 mảnh");
                    return;
                }
                arrItem = new int[]{ItemName.LIEN_100};
            }
            int itemId = NinjaUtils.nextInt(arrItem);
            Item itm = ItemFactory.getInstance().newItem10X(itemId, NinjaUtils.nextInt(3) == 1);
            addItemToBag(itm);
            removeItem(item.index, 1000, true);


        } else if (item.id == ItemName.KHI_BAO) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            RandomCollection<Integer> rand = RandomItem.KHI_BAO;
            int id = rand.next();
            Item itm = ItemFactory.getInstance().newItem(id);
            addItemToBag(itm);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.LANG_BAO) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            RandomCollection<Integer> rand = RandomItem.LANG_BAO;
            int id = rand.next();
            Item itm = ItemFactory.getInstance().newItem(id);
            addItemToBag(itm);
            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.RUONG_MAY_MAN_2) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            RandomCollection<Integer> rand = new RandomCollection<>();
//            if (DONG_XU[0] > 0) {
//                rand.add(2, ItemName.DONG_XU_VANG);
//            }
//            if (DONG_XU[1] > 0) {
//                rand.add(0.5, ItemName.DONG_XU_XANH);
//            }
//            if (DONG_XU[2] > 0) {
//                rand.add(0.03, ItemName.DONG_XU_XANH_LA);
//            }
//            if (DONG_XU[3] > 0) {
//                rand.add(0.05, ItemName.DONG_XU_TRANG);
//            }
//            if (DONG_XU[4] > 0) {
//                rand.add(0.05, ItemName.DONG_XU_DO);
//            }
//            rand.add(10.8, ItemName.LANG_BAO);
//            rand.add(10, ItemName.KHI_BAO);
//            rand.add(10, ItemName.MAT_NA_SUPER_BROLY);
//            rand.add(10, ItemName.MAT_NA_VEGETA);
//            rand.add(10, ItemName.MAT_NA_ONNA_BUGEISHA);
//            rand.add(10, ItemName.MAT_NA_KUNOICHI);
//            rand.add(30, ItemName.LUC_THANH_HOA);
//            rand.add(1, ItemName.PET_BONG_MA);
//            rand.add(1, ItemName.PET_YEU_TINH);
//            rand.add(5, ItemName.HUYET_NGOC);
//            rand.add(5, ItemName.HUYEN_TINH_NGOC);
//            rand.add(5, ItemName.LUC_NGOC);
//            rand.add(5, ItemName.LAM_TINH_NGOC);
//            int id = rand.next();
//            Item itm = ItemFactory.getInstance().newItem(id);
//            if (id == ItemName.LUC_THANH_HOA) {
//                itm.setQuantity(3);
//            } else if (id == ItemName.PET_BONG_MA || id == ItemName.PET_YEU_TINH || id == ItemName.MAT_NA_SUPER_BROLY || id == ItemName.MAT_NA_VEGETA || id == ItemName.MAT_NA_ONNA_BUGEISHA || id == ItemName.MAT_NA_KUNOICHI) {
//                itm.expire = System.currentTimeMillis();
//                long expire = 2;
//                if (id == ItemName.PET_BONG_MA || id == ItemName.PET_YEU_TINH) {
//                    expire = 1;
//                }
//                expire *= 30;
//                expire *= 24;
//                expire *= 60;
//                expire *= 60;
//                expire *= 1000;
//                itm.expire += expire;
//            }
//            if (id == ItemName.DONG_XU_VANG) {
//                DONG_XU[0]--;
//            }
//            if (id == ItemName.DONG_XU_XANH) {
//                DONG_XU[1]--;
//            }
//            if (id == ItemName.DONG_XU_XANH_LA) {
//                DONG_XU[2]--;
//            }
//            if (id == ItemName.DONG_XU_TRANG) {
//                DONG_XU[3]--;
//            }
//            if (id == ItemName.DONG_XU_DO) {
//                DONG_XU[4]--;
//            }
//            addItemToBag(itm);
//            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.DONG_XU_DO) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            addItemToBag(ItemFactory.getInstance().newItem(ItemName.PET_YEU_TINH));
//            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.DONG_XU_TRANG) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            addItemToBag(ItemFactory.getInstance().newItem(ItemName.PET_BONG_MA));
//            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.DONG_XU_XANH_LA) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            addItemToBag(ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI));
//            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.DONG_XU_XANH) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            addItemToBag(ItemFactory.getInstance().newItem(ItemName.RUONG_BACH_NGAN));
//            removeItem(item.index, 1, true);
//        } else if (item.id == ItemName.DONG_XU_VANG) {
//            if (getSlotNull() == 0) {
//                warningBagFull();
//                return;
//            }
//            addItemToBag(ItemFactory.getInstance().newItem(ItemName.BAT_BAO));
//            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.DONG_XU_DO || item.id == ItemName.DONG_XU_VANG || item.id == ItemName.DONG_XU_XANH_LA) {
            int gold = 0;
            if (item.id == ItemName.DONG_XU_DO) {
                gold = NinjaUtils.nextInt(100, 1000);
            } else if (item.id == ItemName.DONG_XU_VANG) {
                gold = NinjaUtils.nextInt(1000, 5000);
            } else if (item.id == ItemName.DONG_XU_XANH_LA) {
                gold = NinjaUtils.nextInt(2000, 7000);
            }
            addGold(gold);
            serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(gold) + " lượng");
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.LONG_KHI) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            if (!item.has(10000)) {
                serverMessage("Bạn không đủ 10.000 Lông khỉ.");
                return;
            }
            addItemToBag(ItemFactory.getInstance().newItem(ItemName.TON_HANH_GIA));
            removeItem(item.index, 10000, true);
        } else if (item.id == ItemName.HOA_TUYET) {
            int quantityNeed = -1;
            Item itm = getItemInMaskBox(ItemName.SANTA_CLAUS);
            if (itm != null) {
                if (itm.upgrade >= 10) {
                    getService().serverMessage(String.format("%s đã đạt cấp tối đa, không thể nâng cấp", itm.template.name));
                    return;
                }
                quantityNeed = (itm.upgrade + 1) * 1000;
            } else {
                quantityNeed = 1000;
            }
            if (!item.has(quantityNeed)) {
                if (itm == null) {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể chế tạo", quantityNeed));
                } else {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể nâng cấp", quantityNeed));
                }
                return;
            }
            removeItem(item.index, quantityNeed, true);
            if (itm != null) {
                itm.upgrade++;
                itm.initOption();
                getService().serverMessage(String.format("Chúc mừng bạn đã nâng cấp thành công %s lên cấp %d", itm.template.name, itm.upgrade));
            } else {
                itm = ItemFactory.getInstance().newItem(ItemName.SANTA_CLAUS);
                itm.upgrade = 1;
                itm.yen = 0;
                itm.initOption();
                maskBox.add(itm);
                getService().serverMessage(String.format("Chúc mừng bạn đã tạo thành công %s", itm.template.name));
            }

        } else if (item.id == ItemName.PHA_LE) {
            int quantityNeed = -1;
            Item itm = getItemInMaskBox(ItemName.YUKIMURA_);
            if (itm != null) {
                if (itm.upgrade >= 10) {
                    getService().serverMessage(String.format("%s đã đạt cấp tối đa, không thể nâng cấp", itm.template.name));
                    return;
                }
                quantityNeed = (itm.upgrade + 1) * 1000;
            } else {
                quantityNeed = 1000;
            }
            if (!item.has(quantityNeed)) {
                if (itm == null) {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể chế tạo", quantityNeed));
                } else {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể nâng cấp", quantityNeed));
                }
                return;
            }
            removeItem(item.index, quantityNeed, true);
            if (itm != null) {
                itm.upgrade++;
                itm.initOption();
                getService().serverMessage(String.format("Chúc mừng bạn đã nâng cấp thành công %s lên cấp %d", itm.template.name, itm.upgrade));
            } else {
                itm = ItemFactory.getInstance().newItem(ItemName.YUKIMURA_);
                itm.upgrade = 1;
                itm.yen = 0;
                itm.initOption();
                maskBox.add(itm);
                getService().serverMessage(String.format("Chúc mừng bạn đã tạo thành công %s", itm.template.name));
            }

        } else if (item.id == ItemName.NHAM_THACH_) {
            int quantityNeed = -1;
            Item itm = getItemInMaskBox(ItemName.SUMIMURA_);
            if (itm != null) {
                if (itm.upgrade >= 10) {
                    getService().serverMessage(String.format("%s đã đạt cấp tối đa, không thể nâng cấp", itm.template.name));
                    return;
                }
                quantityNeed = (itm.upgrade + 1) * 1000;
            } else {
                quantityNeed = 1000;
            }
            if (!item.has(quantityNeed)) {
                if (itm == null) {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể chế tạo", quantityNeed));
                } else {
                    getService().serverMessage(String.format("Phải có đủ %d mảnh mới có thể nâng cấp", quantityNeed));
                }
                return;
            }
            removeItem(item.index, quantityNeed, true);
            if (itm != null) {
                itm.upgrade++;
                itm.initOption();
                getService().serverMessage(String.format("Chúc mừng bạn đã nâng cấp thành công %s lên cấp %d", itm.template.name, itm.upgrade));
            } else {
                itm = ItemFactory.getInstance().newItem(ItemName.SUMIMURA_);
                itm.upgrade = 1;
                itm.yen = 0;
                itm.initOption();
                maskBox.add(itm);
                getService().serverMessage(String.format("Chúc mừng bạn đã tạo thành công %s", itm.template.name));
            }

        } else if (item.id == ItemName.THI_LUYEN_THIEP) {
            int time = 7200000;
            Effect eff = em.findByID((byte) 34);
            if (eff != null) {
                if (eff.getTimeRemaining() + time > 36000000) {
                    serverMessage("Chỉ có thể sử dụng tối đa 10 giờ.");
                    return;
                }
                eff.addTime(time);
                em.setEffect(eff);
            } else {
                Effect effect = new Effect(34, time, 0);
                em.setEffect(effect);
            }
            removeItem(item.index, 1, true);

        } else if (item.id == ItemName.MAY_DO_OAN_HON) {  // up vxmq ko ra oan hồn
            int time = 18000000;
            Effect eff = em.findByID((byte) 46);
            if (eff != null) {
                if (eff.getTimeLength() + time > 360000000) {
                    serverMessage("Chỉ có thể sử dụng tối đa 10 giờ.");
                    return;
                }
                eff.addTime(time);
                em.setEffect(eff);
            } else {
                Effect effect = new Effect(46, time, 0);
                em.setEffect(effect);
            }
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.THIEN_NHAN_PHU || item.id == ItemName.KHAI_NHAN_PHU) {
            if (isTNP || isKNP) {
                serverMessage("Vui lòng chờ hết hiệu lực nhãn phù.");
                return;
            }
            byte template = 40;
            if (item.id == ItemName.KHAI_NHAN_PHU) {
                template = 41;
            }
            Effect eff = new Effect(template, 5 * 60 * 60 * 1000, 0);
            em.setEffect(eff);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.MANH_GIAY_VUN) {
            if (!item.has(300)) {
                serverMessage("Không đủ giấy vụn.");
                return;
            }
            menus.clear();
            menus.add(new Menu(CMDMenu.EXECUTE, "Sách tiềm năng sơ", () -> {
                if (getSlotNull() == 0) {
                    warningBagFull();
                    return;
                }
                int index = getIndexItemByIdInBag(ItemName.MANH_GIAY_VUN);
                if (index != -1 && bag[index] != null && bag[index].has(300)) {
                    Item itm = ItemFactory.getInstance().newItem(ItemName.SACH_TIEM_NANG_SO_CAP);
                    addItemToBag(itm);
                    removeItem(index, 300, true);
                }
            }));
            menus.add(new Menu(CMDMenu.EXECUTE, "Sách kỹ năng sơ", () -> {
                if (getSlotNull() == 0) {
                    warningBagFull();
                    return;
                }
                int index = getIndexItemByIdInBag(ItemName.MANH_GIAY_VUN);
                if (index != -1 && bag[index] != null && bag[index].has(300)) {
                    Item itm = ItemFactory.getInstance().newItem(ItemName.SACH_KY_NANG_SO_CAP);
                    addItemToBag(itm);
                    removeItem(index, 300, true);
                }
            }));
            getService().openUIMenu();
        } else if (item.id == ItemName.LENH_BAI_HANG_DONG) {
            if (this.countPB == 0) {
                if (countUseItemDungeo == 0) {
                    serverMessage("Đã hết lượt sử dụng.");
                    return;
                }
                Dungeon dungeon = (Dungeon) findWorld(World.DUNGEON);
                if (dungeon != null && !dungeon.isClosed()) {
                    serverMessage("Hang động hiện tại vẫn chưa kết thúc.");
                    return;
                }
                removeWorld(World.DUNGEON);
                this.countPB = 1;
                this.pointPB = 0;
                this.receivedRewardPB = false;
                this.countUseItemDungeo = 0;
                serverMessage("Bạn nhận được thêm 1 lần khám phá hang động.");
                removeItem(item.index, 1, true);
            } else {
                serverMessage("Bạn vẫn còn lượt vào hang động.");
            }
        } else if (item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_SO || item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_TRUNG || item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_CAO) {
            if (clan == null) {
                serverMessage("Cần có gia tộc để sử dụng.");
                return;
            }
            if (this.zone.map.tilemap.isLangCo() || this.zone.map.tilemap.isLangTruyenThuyet() || this.zone.map.tilemap.isLangDiaNguc()) { // lang dia nguc
                serverMessage("Không thể sử dụng tại đây.");
                return;
            }
            int exp = 0;
            int level = 0;

            if (item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_SO) {
                // sử dụng thẻ bài gia tộc từ lv5
                level = 1;
                exp = NinjaUtils.nextInt(100, 200);
            } else if (item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_TRUNG) {
                level = 10;
                exp = NinjaUtils.nextInt(300, 800);
            } else if (item.id == ItemName.THE_BAI_KINH_NGHIEM_GIA_TOC_CAO) {
                level = 15;
                exp = NinjaUtils.nextInt(1000, 200);
            }
            if (level > clan.level) {
                serverMessage("Yêu cầu gia tộc phải đạt cấp " + level);
                return;
            }
            Member mem = clan.getMemberByName(this.name);
            if (mem != null) {
                mem.addPointClan(exp);
                // mem.addPointClanWeek(exp);
                clan.addExp(exp);
                removeItem(item.index, 1, true);
            } else {
                serverDialog("Có lỗi xảy ra.");
            }
        } else if (item.id == ItemName.XANG_A95) {
            Mount mount = this.mount[4];
            if (mount != null) {
                if (mount.id == ItemName.HARLEY_DAVIDSON || item.id == ItemName.XE_MAY) {
                    updateHPMount(200);
                    zone.getService().loadMount((Char) this);
                    removeItem(item.index, 1, true);
                } else {
                    serverDialog("Không thể dùng cho thú cưỡi.");
                }
            } else {
                serverDialog("Bạn chưa sử dụng xe máy.");
            }
        } else if (item.id == ItemName.LINH_LANG_THAO) {
            Mount mount = this.mount[4];
            if (mount != null) {
                if (mount.id != ItemName.HARLEY_DAVIDSON && item.id != ItemName.XE_MAY) {
                    updateHPMount(200);
                    zone.getService().loadMount((Char) this);
                    removeItem(item.index, 1, true);
                } else {
                    serverDialog("Không thể dùng cho xe máy.");
                }
            } else {
                serverDialog("Bạn chưa sử dụng thú cưỡi.");
            }
        } else if (item.id == ItemName.DANH_VONG_PHU) {
            if (this.countUseItemGlory > 0) {
                this.countGlory += 5;
                this.countUseItemGlory--;
                removeItem(item.index, 1, true);
//                if (this.countUseItemGlory == 0) {
//                    addTopThanNong(5);
//                }
                serverMessage("Bạn nhận được thêm 5 lần làm nhiệm vụ danh vọng.");
            } else {
                serverMessage("Đã hết số lần dùng trong ngày hôm nay rồi.");
            }
        } else if (item.id == ItemName.TA_THU_LENH) {
            if (this.countUseItemBeast > 0) {
                this.countLoopBoss++;
                this.countUseItemBeast--;
                removeItem(item.index, 1, true);
                serverMessage("Bạn nhận được thêm 1 lần làm nhiệm vụ tà thú.");
            } else {
                serverMessage("Đã hết số lần dùng trong ngày hôm nay rồi.");
            }
        } else if (item.id == ItemName.BACH_BIEN_LENH || item.id == ItemName.VO_HAN_BACH_BIEN_LENH) {
            if (zone.tilemap.isNotReturnTown()) {
                serverMessage("Không thể sử dụng tính năng này tại đây.");
                return;
            }
            short[] xy = NinjaUtils.getXY(this.saveCoordinate);
            setXY(xy[0], xy[1]);
            if (item.id == ItemName.BACH_BIEN_LENH) {
                removeItem(item.index, item.getQuantity(), true);
            }
            changeMap(this.saveCoordinate);
        } else if (item.id >= 695 && item.id <= 703) {
            int quantity = item.getQuantity() / 10;
            if (quantity <= 0) {
                serverDialog("Yêu cầu tối thiểu 10 viên " + item.template.name);
                return;
            }
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            removeItem(item.index, quantity * 10, true);
            Item itm = ItemFactory.getInstance().newItem(item.id + 1);
            itm.setQuantity(quantity);
            itm.isLock = true;
            addItemToBag(itm);
        } else if (item.id == ItemName.THAT_THU_THU_BAO) {
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }

            removeItem(item.index, 1, true);
            if (taskId == TaskName.NV_THU_TAI_MAY_MAN) {
                if (taskMain != null && taskMain.index == 2) {
                    updateTaskCount(1);
                }
            }
            int itemId = RandomItem.THAT_THU_BAO.next();
            Item itm = ItemFactory.getInstance().newItem(itemId);
            itm.setQuantity(1);
            addItemToBag(itm);
            // rương hang động
        } else if (item.id == ItemName.RUONG_MAY_MAN || item.id == ItemName.RUONG_TINH_XAO || item.id == ItemName.RUONG_MA_QUAI) {
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            int[] arrItem = null;
            int[] arrPercent = null;
            int idItem = 12;
            boolean[] arrLock = null;
            if (item.id == 272) {
                idItem = RandomItem.RUONG_MAY_MAN.next();
            } else if (item.id == 282) {
                idItem = RandomItem.RUONG_TINH_XAO.next();
            } else if (item.id == 647) {
                idItem = RandomItem.RUONG_MA_QUAI.next();
            }
            if (taskId == TaskName.NV_THU_TAI_MAY_MAN) {
                if (taskMain != null && taskMain.index == 1) {
                    updateTaskCount(1);
                }
            }

            if (idItem != 12) {
                Item itm = ItemFactory.getInstance().newItem9X(idItem, false);
                itm.isLock = false;
                addItemToBag(itm);
            } else {
                addYen(NinjaUtils.nextInt(10, 100) * 1000L);
            }
            removeItem(item.index, 1, true);
        } else if (item.id == 275 || item.id == 276 || item.id == 277 || item.id == 278) {
            byte templateId = 0;
            short param = 0;
            switch (item.id) {
                case 275:
                    templateId = 24;
                    param = 500;
                    break;

                case 276:
                    templateId = 25;
                    param = 500;
                    break;

                case 277:
                    templateId = 26;
                    param = 100;
                    break;

                case 278:
                    templateId = 27;
                    param = 1000;
                    break;
            }
            Effect effect = new Effect(templateId, 600000, param);
            em.setEffect(effect);
            setAbility();
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.RUONG_HAC_AM) {
            if (getSlotNull() == 0) {
                warningBagFull();
                return;
            }
            int keyIndex = this.getIndexItemByIdInBag(ItemName.KHOA_HAC_AM);
            if (keyIndex != -1) {
                int[] arrItem = {564, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637};
                int itemId = NinjaUtils.nextInt(arrItem);
                Item itm = ItemFactory.getInstance().newItem9X(itemId, NinjaUtils.nextInt(20) == 1);
                addItemToBag(itm);
                removeItem(keyIndex, 1, true);
            } else {
                Item itm = ItemFactory.getInstance().newItem(RandomItem.RUONG_HAC_AM);
                addItemToBag(itm);
                // ktg khi mở ruơng hắc ám
                if (item.id == ItemName.BAT_BAO || item.id == ItemName.RUONG_BACH_NGAN || item.id == ItemName.THI_LUYEN_THIEP) {
                    GlobalService.getInstance().chat("server", this.name + " sử dụng " + item.template.name + " nhận được " + itm.template.name);
                }
            }
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.RUONG_THAN_BI) {
            int[] arrItem = null;
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            arrItem = new int[]{1163, 1164, 1165, 1166, 1167, 1168, 1169, 1170, 1171, 1172, 1173, 1174, 1175, 1176};
            int index = NinjaUtils.nextInt(arrItem.length);
            int id = arrItem[index];
            Item itm = ItemFactory.getInstance().newItem10X(id);
            itm.isLock = false;
            addItemToBag(itm);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.RUONG_BAU_VAT) {
            int[] arrItem = null;
            int[] arrPercent = null;
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            arrItem = new int[]{1111, 1112, 1113, 1114, 1115, 1116};
            arrPercent = new int[]{30, 30, 50, 50, 50, 50};

            int index = NinjaUtils.randomWithRate(arrPercent, 300);
            int id = arrItem[index];
            Item itm = ItemFactory.getInstance().newItem10X(id);
            itm.isLock = false;
            addItemToBag(itm);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.TUI_QUA_NOEL) {
            int exp = 15000000;
            addExp(exp);
        } else if (item.id == ItemName.BAT_BAO || item.id == ItemName.RUONG_BACH_NGAN || item.id == ItemName.RUONG_HUYEN_BI) {
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            if (classId == 0) {
                serverDialog("Bạn không đủ sức khỏe để mở. Hãy vào trường để rèn luyện thêm.");
                return;
            }
            int upgrade = 12;
            if (item.id == ItemName.RUONG_BACH_NGAN) {
                upgrade = 14;
            } else if (item.id == ItemName.RUONG_HUYEN_BI) {
                upgrade = 16;
            }
            RandomCollection<Byte> storeTypes = new RandomCollection<>();
            storeTypes.add(5, StoreManager.TYPE_WEAPON);
            storeTypes.add(7, StoreManager.TYPE_NECKLACE);
            storeTypes.add(7, StoreManager.TYPE_RING);
            storeTypes.add(7, StoreManager.TYPE_PEARL);
            storeTypes.add(7, StoreManager.TYPE_SPELL);
            if (gender == 1) {
                storeTypes.add(10, StoreManager.TYPE_MEN_HAT);
                storeTypes.add(10, StoreManager.TYPE_MEN_SHIRT);
                storeTypes.add(10, StoreManager.TYPE_MEN_GLOVES);
                storeTypes.add(10, StoreManager.TYPE_MEN_PANT);
                storeTypes.add(10, StoreManager.TYPE_MEN_SHOES);
            } else {
                storeTypes.add(10, StoreManager.TYPE_WOMEN_HAT);
                storeTypes.add(10, StoreManager.TYPE_WOMEN_SHIRT);
                storeTypes.add(10, StoreManager.TYPE_WOMEN_GLOVES);
                storeTypes.add(10, StoreManager.TYPE_WOMEN_PANT);
                storeTypes.add(10, StoreManager.TYPE_WOMEN_SHOES);
            }
            byte rd = storeTypes.next();
            Store store = StoreManager.getInstance().find(rd);
            int level = 50;
            if (this.level >= 60 || item.id == 385) {
                level = 70;
            }
            final int sys = NinjaUtils.nextInt(1, 3);
            final int level2 = level;
            ItemStore itemStore = store.stream().filter((t) -> {
                ItemTemplate template = t.getTemplate();
                if (store.getType() == StoreManager.TYPE_WEAPON) {
                    if ((classId == 1 && !template.isKiem()) || (classId == 2 && !template.isTieu()) || (classId == 3 && !template.isKunai()) || (classId == 4 && !template.isCung()) || (classId == 5 && !template.isDao()) || (classId == 6 && !template.isQuat())) {
                        return false;
                    }
                } else {
                    if (t.getSys() != sys) {
                        return false;
                    }
                }
                return ((int) template.level / 10) == (level2 / 10);

            }).findFirst().orElse(null);
            if (itemStore != null) {
                Item add = ItemFactory.getInstance().newItem(itemStore.getItemID());
                add.sys = itemStore.getSys();
                List<ItemOption> options = itemStore.getMaxOptions();
                for (ItemOption o : options) {
                    int templateId = o.optionTemplate.id;
                    int param = o.param;
                    add.options.add(new ItemOption(templateId, param));
                }
                add.next(upgrade);
                add.isLock = item.isLock;
                addItemToBag(add);
                removeItem(item.index, 1, true);
            } else {
                serverDialog("Có lỗi xảy ra! Vui lòng báo với quản trị viên.");
            }
        } else if (item.id == 254 || item.id == 255 || item.id == 256) {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (item.id == 254 && this.level >= 30) {
                serverDialog("Vật phẩm chỉ có thể xóa kinh nghiệm âm dưới cấp 30");
                return;
            } else if (item.id == 255 && this.level >= 60) {
                serverDialog("Vật phẩm chỉ có thể xóa kinh nghiệm âm dưới cấp 60");
                return;
            }
            addExp(this.expDown);
            removeItem(item.index, 1, true);
            serverMessage("Đã xóa kinh nghiệm âm");
        } else if (item.id == ItemName.CAN_CAU_VANG) { // câu cá
            if (this.isCatchItem) {
                return;
            }
            if (zone.tilemap.tileTypeAt(this.x, this.y - 12, TileMap.T_WATERFLOW)) {
                if (getSlotNull() > 0) {
                    removeItem(item.index, 1, true);
                    isCatchItem = true;
                    Char _char = (Char) this;
                    threadPool.submit(new Thread(new Runnable() {
                        public void run() {
                            try {
                                getService().endDlg(true);
                                serverMessage("Đang thả câu....");
                                Thread.sleep(3000L);
                                if (!isCleaned) {
                                    int[] idItem = {6, 7, 8, 9, 10, -2};
                                    int[] percent = {50, 50, 40, 30, 20, 20};
                                    int index = NinjaUtils.randomWithRate(percent, 100);
                                    int id = idItem[index];
                                    if (id == -1) {
                                        int exp = NinjaUtils.nextInt(1, 10) * 100000;
                                        addExp(exp);
                                        serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(exp) + " kinh nghiệm");
                                    } else if (id == -2) {
                                        zone.getService().chat(_char.id, "Ôi? Dây câu đứt mất rồi!");
                                    } else {
                                        Item item2 = ItemFactory.getInstance().newItem(id);
                                        addItemToBag(item2);
                                        if (percent[index] < 10) {
                                            zone.getService().chat(_char.id, "tôi câu được " + item2.template.name + " " + NinjaUtils.nextInt(300, 500) + " nè");
                                        }
                                    }
                                }
                                isCatchItem = false;
                            } catch (Exception ex) {
                                Log.logException("use item 'Can Cau Vang' err: ", Char.class, ex);
                                serverDialog(ex.getMessage());
                            }
                        }
                    }));

                } else {
                    warningBagFull();
                }
            } else {
                serverDialog("Hãy đứng chỗ có nước để câu");
            }
            // fix dùng tiến hóa thao như trâu
        } else if (item.id == ItemName.TIEN_HOA_THAO) {
            if (mount[4] != null) {
                if (mount[4].id == ItemName.HAC_NGUU
                        || mount[4].id == ItemName.KIM_NGUU
                        || mount[4].id == ItemName.BACH_HO
                        || mount[4].id == ItemName.SIEU_XE_HALLOWEEN
                        || mount[4].id == ItemName.BACH_NGAN_LANG  // thêm pet mới
                        || mount[4].id == ItemName.BACH_SU_VUONG   // thêm pet mới
                        || mount[4].id == ItemName.LAN_SU_VU
                        || mount[4].id == ItemName.XICH_TU_MA
                        || mount[4].id == ItemName.TA_LINH_MA
                        || mount[4].id == ItemName.PHONG_THUONG_MA
                        || mount[4].id == ItemName.PHUONG_HOANG_BANG
                        || mount[4].id == ItemName.HOA_KY_LAN) {
                    if (upgradeMount()) {
                        removeItem(item.index, 1, true);
                    }
                }
            }

        } else if (item.id == ItemName.CHUYEN_TINH_THACH) { // nếu dùng tến hoóa thảo cho ấy con kia thì cỗ cct phải bỏ mấy con ấy ra
            if (mount[4] != null) {
                if (mount[4].id != ItemName.HAC_NGUU
                        && mount[4].id != ItemName.KIM_NGUU
                        && mount[4].id != ItemName.PHUONG_HOANG_BANG
                        && mount[4].id != ItemName.HOA_KY_LAN
                        && mount[4].id != ItemName.XICH_TU_MA
                        && mount[4].id != ItemName.TA_LINH_MA
                        && mount[4].id != ItemName.PHONG_THUONG_MA
                        && mount[4].id != ItemName.LAN_SU_VU
                        && mount[4].id != ItemName.KIM_NGUU) {
                    if (upgradeMount()) {
                        removeItem(item.index, 1, true);
                    }
                }
            }

        } else if (item.id == ItemName.LANG_HON_THAO) {
            if (addExpForMount(5, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.LANG_HON_MOC) {
            if (addExpForMount(7, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.DIA_LANG_THAO) {
            if (addExpForMount(14, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.TAM_LUC_DIEP) {
            if (addExpForMount(20, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.XICH_LAN_HOA_) {
            if (addExpForMount(25, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.LUC_THANH_HOA) {
            if (addExpForMount(200, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.TU_LINH_LIEN_HOA) {
            if (addExpForMount(400, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.LINH_LANG_HO_DIEP) {
            if (addExpForMount(600, (byte) 0)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.BANH_RANG) {
            if (addExpForMount(100, (byte) 1)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.IK) {
            if (addExpForMount(250, (byte) 1)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.THUOC_CAI_TIEN) {
            if (addExpForMount(500, (byte) 1)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.THONG_LINH_THAO) {
            if (addExpForMount(NinjaUtils.nextInt(10, 100), (byte) 2)) {
                removeItem(item.index, 1, true);
            }
        } else if (item.id == ItemName.HOAN_LUONG_CHI_THAO) {
            if (!isHuman) {
                warningClone();
                return;
            }

            if (this.hieuChien == 0) {
                serverMessage("Điểm hiếu chiến của bạn đã bằng 0.");
                return;
            }

            if (this.zone.tilemap.isNotAllowPkPoint()) {
                serverMessage("Không thể sử dụng vật phẩm này tại đây.");
                return;
            }

            addPointPk(-5);
            serverMessage(String.format("Điểm hiếu chiến của bạn còn %d điểm", this.hieuChien));
            removeItem(item.index, 1, true);
        } else if (item.id == 279) {
            if (!isHuman) {
                warningClone();
                return;
            }
            InputDialog input = new InputDialog(CMDInputDialog.TELEPORT, "Nhập tên người cần tới");
            setInput(input);
            getService().showInputDialog();
        } else if (item.id == ItemName.NAM_LINH_CHI) {
            int time = 5 * 60 * 60 * 1000;
            short param = 2;
            byte template = 22;
            Effect eff = em.findByID(template);
            if (eff != null) {
                eff.addTime(time);
                em.setEffect(eff);
            } else {
                Effect effect = new Effect(template, time, param);
                effect.param2 = item.id;
                em.setEffect(effect);
            }
            removeItem(item.index, item.getQuantity(), true);
        } else if (item.id == ItemName.LINH_CHI_NGAN_NAM) {
            int time = 3600000;
            short param = 3;
            byte template = 32;
            Effect eff = em.findByID(template);
            if (eff != null) {
                eff.addTime(time);
                em.setEffect(eff);
            } else {
                Effect effect = new Effect(template, time, param);
                em.setEffect(effect);
            }
            removeItem(item.index, item.getQuantity(), true);
        } else if (item.id == ItemName.LINH_CHI_VAN_NAM) {
            int time = 3600000;
            short param = 4;
            byte template = 33;
            Effect eff = em.findByID(template);
            if (eff != null) {
                eff.addTime(time);
                em.setEffect(eff);
            } else {
                Effect effect = new Effect(template, time, param);
                em.setEffect(effect);
            }
            removeItem(item.index, item.getQuantity(), true);

        } else if (item.id == ItemName.NHAN_SAM_NGAN_NAM) { // nhân sâm
            if (level >= 130) {
                serverMessage("Cấp độ của bạn đã đạt giới hạn, không thể sử dụng Nhân Sâm Ngàn Năm.");
                return;
            }
            removeItem(item.index, 1, true);
            long expNext = Server.exps[level];
            long expToAdd = (long) (expNext * 0.1);
            addExp(expToAdd);
            double newExpPercent = (double) exp / expNext;
            if (newExpPercent > 1.0) {
                newExpPercent = 1.0;
            }
            serverMessage("Bạn nhận được 10% kinh nghiệm.");

        } else if (item.id == ItemName.NHAN_SAM_VAN_NAM) { // nhân sâm
            if (level >= 130) {
                serverMessage("Cấp độ của bạn đã đạt giới hạn, không thể sử dụng Nhân Sâm Vạn Năm.");
                return;
            }
            removeItem(item.index, 1, true);
            long duration = 2 * 60 * 60 * 1000L;
            Effect nlcEffect = em.findByID((byte)22);
            if (nlcEffect != null && !nlcEffect.isExpired()) {
                Effect expBuff = new Effect((byte)23, duration, (short)4);
                em.setEffect(expBuff);
            } else {
                Effect expBuff = new Effect((byte)23, duration, (short)2); // param = 2 nghĩa là x2
                em.setEffect(expBuff);
                serverMessage("Bạn nhận được hiệu ứng x2 kinh nghiệm trong 2 tiếng!");
            }

        } else if (item.id == 38) {
            int yen = NinjaUtils.nextInt(1000, 20000);
            addYen(yen);
            serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(yen) + " yên");
            removeItem(item.index, 1, true);
        } else if (item.id == 240) {
            this.tayTiemNang++;
            removeItem(item.index, item.getQuantity(), true);
            serverMessage(String.format("Số lần tẩy điểm tiềm năng của bạn là %d", tayTiemNang));
        } else if (item.id == 241) {
            this.tayKyNang++;
            removeItem(item.index, item.getQuantity(), true);
            serverMessage(String.format("Số lần tẩy điểm kỹ năng của bạn là %d", tayTiemNang));
        } else if (item.id == 252) {
            if (this.limitKyNangSo < 3) {
                this.limitKyNangSo++;
                this.skillPoint++;
                getService().loadSkill();
                removeItem(item.index, item.getQuantity(), true);
                serverMessage("Bạn nhận được 1 điểm kỹ năng.");
            } else {
                serverDialog("Đã hết số lần sử dụng.");
            }
        } else if (item.id == 253) {
            if (this.limitTiemNangSo < 8) {
                this.limitTiemNangSo++;
                this.potentialPoint += 10;
                getService().updatePotential();
                removeItem(item.index, item.getQuantity(), true);
                serverMessage("Bạn nhận được 10 điểm tiềm năng.");
            } else {
                serverDialog("Đã hết số lần sử dụng.");
            }
        } else if (item.id == 308) {
            if (this.limitPhongLoi < 10) {
                this.limitPhongLoi++;
                this.skillPoint++;
                getService().loadSkill();
                removeItem(item.index, item.getQuantity(), true);
                serverMessage("Bạn nhận được 1 điểm kỹ năng.");
            } else {
                serverDialog("Đã hết số lần sử dụng.");
            }
        } else if (item.id == 309) {
            if (this.limitBangHoa < 10) {
                this.limitBangHoa++;
                this.potentialPoint += 10;
                getService().updatePotential();
                removeItem(item.index, item.getQuantity(), true);
                serverMessage("Bạn nhận được 10 điểm tiềm năng.");
            } else {
                serverDialog("Đã hết số lần sử dụng.");
            }
        } else if (item.id == 215 || item.id == 229 || item.id == 283 || item.id == 801 || item.id == 1094 || item.id == 1158) {
            expandBag(item);
        } else if (item.id == ItemName.CO_LENH) {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (mapId == 138 || mapId == 137 || mapId == 136 || mapId == 135 || mapId == 134) {
                serverMessage("Bạn đang trong làng cổ.");
                return;
            }
            setXY((short) 109, (short) 408);
            changeMap(138);
            removeItem(item.index, item.getQuantity(), true);
        } else if (item.id == ItemName.TRUYEN_THUYET_LENH) {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (mapId == 162 || mapId == 163 || mapId == 164 || mapId == 165) {
                serverMessage("Bạn đang trong làng truyền thuyết.");
                return;
            }
            setXY((short) 171, (short) 408);
            changeMap(162);
            removeItem(item.index, item.getQuantity(), true);
        } else if (item.id == ItemName.MAM_CUNG_CO_HON || item.id == ItemName.MAM_CUNG_CO_HON_BAC || item.id == ItemName.MAM_CUNG_CO_HON_VANG || item.id == ItemName.HO_LO || item.id == ItemName.HOP_QUA_MAY_MAN) {
            Event.getEvent().useItem((Char) this, item);
        } else if (item.id == ItemName.HOP_VU_KHI) { // ran dom vk
            int[] arrItem = null;
            int[] arrPercent = null;
            int slotNull = getSlotNull();
            if (slotNull == 0) {
                warningBagFull();
                return;
            }
            arrItem = new int[]{1111, 1112, 1113, 1114, 1115, 1116};


            int id = arrItem[NinjaUtils.nextInt(0, arrItem.length - 1)];
            Item itm = ItemFactory.getInstance().newItem10X(id);
            itm.isLock = false;
            int upgrade = (byte) NinjaUtils.nextInt(12, 16); // random +12-> +16
            itm.next(upgrade);
            itm.expire = System.currentTimeMillis() + (long) (86400000L * NinjaUtils.nextInt(1, 3)); // hạn random từ 3-7 ngayf


            int tinhluyen = NinjaUtils.nextInt(1, 9);
            int Paramstinhluyen = NinjaUtils.nextInt(350, 600);

            itm.options.add(new ItemOption(85, tinhluyen));
            int[] optionId = {88, 89, 90};
            itm.options.add(new ItemOption(optionId[itm.sys - 1], Paramstinhluyen));
            for (int i = 0; i < itm.options.size(); i++) {
                ItemOption option = itm.options.get(i);
                int optionId1 = option.optionTemplate.id;
                int optionParam = option.param;
                if (optionId1 == 85) {
                    for (ItemOption option1 : itm.options) {
                        if (option1.optionTemplate.id == 88 || option1.optionTemplate.id == 89 || option1.optionTemplate.id == 90) {
                            int[] percentIncreases = new int[]{50, 70, 100, 140, 190, 250, 320, 400, 500};
                            option1.param += percentIncreases[optionParam];

                        }
                    }
                }
            }
            int khaihoa = NinjaUtils.nextInt(1, 9);
            itm.options.add(new ItemOption(156, khaihoa));
            itm.options.add(new ItemOption(157, khaihoa));
            itm.options.add(new ItemOption(0, 500));
            itm.options.add(new ItemOption(67, 5));
            itm.options.add(new ItemOption(14, 10));
            itm.options.add(new ItemOption(10, 15));
            for (int i1 = 0; i1 <= khaihoa; i1++) {

                for (int i = 0; i < itm.options.size() - 3; i++) {
                    int opt0 = itm.options.get(i).optionTemplate.id;
                    int opt1 = itm.options.get(i + 1).optionTemplate.id;
                    int opt2 = itm.options.get(i + 2).optionTemplate.id;
                    if ((opt0 == 0 || opt0 == 73) && opt1 == 67 && (opt2 == 14 || opt2 == 69)) {
                        itm.options.get(i).param += 170;
                        itm.options.get(i + 1).param += 3;
                        itm.options.get(i + 2).param += 3;
                        itm.options.get(i + 3).param += 10;
                    }
                }
            }
            addItemToBag(itm);
            removeItem(item.index, 1, true);
        } else if (item.id == ItemName.THE_DOI_TEN) {
            changeCharname(item);
        } else if (item.id == ItemName.RUONG_DANH_VONG) {


            // Lấy ra ngẫu nhiên một số từ 0 đến 9
            int randomIndex = NinjaUtils.nextInt(10);
            int randPoint = NinjaUtils.nextInt(1, 5);
            switch (randomIndex) {
                case 0:
                    pointNon += randPoint;
                    break;
                case 1:
                    pointVuKhi += randPoint;
                    break;
                case 2:
                    pointAo += randPoint;
                    break;
                case 3:
                    pointLien += randPoint;
                    break;
                case 4:
                    pointGangTay += randPoint;
                    break;
                case 5:
                    pointNhan += randPoint;
                    break;
                case 6:
                    pointQuan += randPoint;
                    break;
                case 7:
                    pointNgocBoi += randPoint;
                    break;
                case 8:
                    pointGiay += randPoint;
                    break;
                case 9:
                    pointPhu += randPoint;
                    break;
            }
            String[] arrPointName = {"Nón", "Vũ khí", "Áo", "Liên", "Găng tay", "Nhẫn", "Quần", "Ngọc bội", "Giày", "Phù"};

            serverMessage("Bạn nhận được " + randPoint + " điểm danh vọng " + arrPointName[randomIndex]);
            removeItem(item.index, 1, true);
        } else {
            learnSkill(item);
        }
        if (Event.isEvent()) {
            Event.getEvent().useItem((Char) this, item);
        }
    }

    public void useItem(Message ms) {
        lockItem.lock();
        try {
            if (isDead) {
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
            byte indexUI = ms.reader().readByte();
            if (indexUI >= 0 && indexUI <= numberCellBag) {
                Item item = bag[indexUI];
                if (item != null && item.has()) {
                    if (this.level < item.template.level) {
                        return;
                    }
                    if (item.isExpired()) {
                        removeItem(item.index, 1, true);
                        return;
                    }
                    if ((item.template.gender == 0 || item.template.gender == 1) && item.template.gender != this.gender) {
                        return;
                    }
                    /// fix sài khả di lệnh đi hang động
                    if (item.template.id == 34 || item.template.id == 35 || item.template.id == 36 || item.template.id == 37) {
                        if (this.zone.tilemap.id == 74 || this.zone.tilemap.id == 110 || this.zone.tilemap.id == 161 || this.zone.tilemap.isWorld() || this.zone.tilemap.isLoiDai() || this.zone.tilemap.isNhaThiDau()) {
                            serverMessage("Bạn không thể sử dụng vật phẩm này tại đây.");
                            return;
                        }
                    }
                    if (item.template.isTypeBody()) {
                        if (this.zone.tilemap.isFujukaSanctuary()) {
                            serverMessage("Không thể trang bị trong khu vực này");
                            return;
                        }
                        if (item.template.id == ItemName.SANTA_CLAUS) {
                            menus.clear();
                            menus.add(new Menu(CMDMenu.EXECUTE, "Trang bị", () -> {
                                useEquipment(item);
                            }));
                            menus.add(new Menu(CMDMenu.EXECUTE, "Tách", () -> {
                                int indexItem = this.getIndexItemByIdInBag(ItemName.SANTA_CLAUS);
                                if (indexItem != -1) {
                                    removeItem(indexItem, 1, true);
                                    Item newItem = ItemFactory.getInstance().newItem(ItemName.HOA_TUYET);
                                    newItem.setQuantity(10000);
                                    newItem.isLock = true;
                                    addItemToBag(newItem);
                                }
                            }));
                            getService().openUIMenu();
                            return;
                        } else if (item.template.isTypeBijuu()) {
                            doUseBijuu(item);
                        } else {
                            useEquipment(item);
                        }
                        return;
                    } else if (item.template.isTypeMount()) {
                        useMount(item);
                        return;
                    } else if (item.template.isTypeEquipmentBijuu()) {
                        doUseEquipmentBijuu(item);
                        return;
                    } else if (item.template.type == ItemTemplate.TYPE_TASK) {
                        if (item.id == ItemName.CHIA_KHOA_CO_QUAN2) {
                            if (mapId == MapName.HANG_MEIRO && this.y == 432 && this.x >= 1700 && this.x <= 1896) {
                                if (taskMain != null && taskMain.taskId == TaskName.NV_TRUY_TIM_DIA_DO && taskMain.index == 1) {
                                    removeItem(item.index, 1, true);
                                    Map map = MapManager.getInstance().find(MapName.DIA_DAO_CHIKATOYA);
                                    ChikatoyaTunnels chikatoyaTunnels = new ChikatoyaTunnels(0, map.tilemap, map);
                                    outZone();
                                    setXY((short) 35, (short) 96);
                                    chikatoyaTunnels.join((Char) this);
                                }
                            }
                        }
                    } else if (item.template.type == ItemTemplate.TYPE_ORDER) {
                        useItemTypeOrder(item);
                        return;
                    } else if (item.template.type == ItemTemplate.TYPE_TASK_WAIT) {
                        useItemTaskWait(item);
                        return;
                    } else if (item.template.type == ItemTemplate.TYPE_DRAGONBALL || (item.id >= ItemName.NGOC_1_SAO && item.id <= ItemName.NGOC_7_SAO)) {
                        if (isCatchItem) {
                            return;
                        }
                        if (this.classId == 0 || getSys() == 0) {
                            serverDialog("Bạn cần gia nhập một trường phái ninja trước khi sử dụng 7 viên ngọc rồng!");
                            return;
                        }
                        int[] index = new int[7];
                        for (int i = 222; i <= 228; i++) {
                            int in = this.getIndexItemByIdInBag(i);
                            if (in == -1) {
                                serverDialog("Cần phải hội tụ đủ 7 viên ngọc rồng (1 sao đến 7 sao) mới có thể sử dụng!");
                                return;
                            }
                            index[i - 222] = in;
                        }
                        try {
                            isCatchItem = true;
                            getService().callEffectBall();
                            for (int in : index) {
                                removeItem(in, 1, true);
                            }
                            if (!isCleaned) {
                                int sys = getSys();
                                int id = (new int[]{ItemName.FAIYAA_YOROI, ItemName.MIZU_YOROI, ItemName.WINDO_YOROI})[sys - 1];
                                Item itm = ItemFactory.getInstance().newItem(id);
                                itm.isLock = true;
                                itm.sys = (byte) sys;
                                addItemToBag(itm);
                                serverDialog("Bạn đã hội tụ thành công 7 viên ngọc rồng và nhận được " + itm.template.name + "!");
                            }
                        } finally {
                            isCatchItem = false;
                        }
                    } else if (item.template.type == ItemTemplate.TYPE_HP) {
                        if (this.hieuChien >= 10) {
                            serverMessage("Điểm hiếu chiến quá cao.");
                            return;
                        }
                        if (this.hp >= this.maxHP) {
                            serverMessage("HP đã đầy.");
                            return;
                        }
                        long now = System.currentTimeMillis();
                        if (now - this.lastTimeUseHp < 5000L) {
                            long remain = (5000L - (now - this.lastTimeUseHp) + 999) / 1000;
                            serverMessage("Chờ " + remain + "s để tiếp tục dùng bình HP.");
                            return;
                        }
                        this.lastTimeUseHp = now;

                        int time = 3000;
                        short param = 0;
                        if (item.id == 13) {
                            param = 12;
                        } else if (item.id == 14) {
                            param = 45;
                        } else if (item.id == 15) {
                            param = 115;
                        } else if (item.id == 16) {
                            param = 200;
                        } else if (item.id == 17) {
                            param = 325;
                        } else if (item.id == 565) {
                            param = 750;
                        }
                        byte template = 21;
                        Effect effect = new Effect(template, time, param);
                        em.setEffect(effect);
                        removeItem(item.index, 1, true);
                        if (this.zone.tilemap.isFujukaSanctuary()) {
                            ((FujukaSanctuary) this.zone).copyRecoveryEffect((Char) this, effect);
                        }
                        return;
                    } else if (item.template.type == ItemTemplate.TYPE_MP) {
                        if (this.hieuChien >= 10) {
                            serverMessage("Điểm hiếu chiến quá cao.");
                            return;
                        }
                        if (this.mp >= this.maxMP) {
                            serverMessage("MP đã đầy.");
                            return;
                        }
                        long now = System.currentTimeMillis();
                        if (now - this.lastTimeUseMp < 5000L) {
                            long remain = (5000L - (now - this.lastTimeUseMp) + 999) / 1000;
                            serverMessage("Chờ " + remain + "s để tiếp tục dùng bình MP.");
                            return;
                        }
                        this.lastTimeUseMp = now;

                        int mp = 0;
                        switch (item.id) {

                            case 18:
                                mp = 75;
                                break;

                            case 19:
                                mp = 250;
                                break;

                            case 20:
                                mp = 500;
                                break;

                            case 21:
                                mp = 1000;
                                break;

                            case 22:
                                mp = 1750;
                                break;

                            case 566:
                                mp = 2500;
                                break;
                        }
                        this.mp += mp;
                        if (this.mp > this.maxMP) {
                            this.mp = this.maxMP;
                        }
                        getService().updateMp();
                        removeItem(item.index, 1, true);
                        return;
                    } else if (item.template.type == ItemTemplate.TYPE_EAT) {
                        if (this.hieuChien >= 10) {
                            serverMessage("Điểm hiếu chiến quá cao.");
                            return;
                        }
                        int time = 0;
                        short param = 0;
                        byte template = 0;
                        switch (item.id) {
                            case 23:
                                time = 1800000;
                                param = 3;
                                template = 0;
                                break;

                            case 24:
                                time = 1800000;
                                param = 20;
                                template = 1;
                                break;

                            case 25:
                                time = 1800000;
                                param = 30;
                                template = 2;
                                break;

                            case 26:
                                time = 1800000;
                                param = 40;
                                template = 3;
                                break;

                            case 27:
                                time = 1800000;
                                param = 50;
                                template = 4;
                                break;

                            case 29:
                                time = 1800000;
                                param = 60;
                                template = 28;
                                break;

                            case 30:
                                time = 3 * 24 * 60 * 60 * 1000;
                                param = 60;
                                template = 28;
                                break;

                            case 249:
                                time = 3 * 24 * 60 * 60 * 1000;
                                param = 40;
                                template = 3;
                                break;

                            case 250:
                                time = 3 * 24 * 60 * 60 * 1000;
                                param = 50;
                                template = 4;
                                break;

                            case 409:
                                time = 24 * 60 * 60 * 1000;
                                param = 150;
                                template = 30;
                                break;

                            case 410:
                                time = 24 * 60 * 60 * 1000;
                                param = 200;
                                template = 31;
                                break;

                            case 567:
                                time = 24 * 60 * 60 * 1000;
                                param = 300;
                                template = 35;
                                break;
                        }

                        Effect effect = new Effect(template, time, param);
                        em.setEffect(effect);
                        removeItem(item.index, item.template.isUpToUp ? 1 : item.getQuantity(), true);
                        if (taskId == TaskName.NV_DIET_SEN_TRU_COC && taskMain != null && taskMain.index == 1) {
                            taskNext();
                        }
                    }
                    if (item.template.isTypeStack()) {
                        getService().useItem(indexUI);
                    }
                }
            }
        } catch (Exception ex) {
            Log.logException("use item err: ", Char.class, ex);
        } finally {
            lockItem.unlock();
        }
    }

}