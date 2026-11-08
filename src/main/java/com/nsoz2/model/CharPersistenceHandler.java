package com.nsoz2.model;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nsoz2.constants.*;
import com.nsoz2.convert.Converter;
import com.nsoz2.db.jdbc.DbManager;
import com.nsoz2.db.mongodb.MongoDbConnection;
import com.nsoz2.effect.Effect;
import com.nsoz2.effect.EffectManager;
import com.nsoz2.event.*;
import com.nsoz2.item.*;
import com.nsoz2.map.Map;
import com.nsoz2.map.*;
import com.nsoz2.map.world.*;
import com.nsoz2.map.zones.*;
import com.nsoz2.model.Friend;
import com.nsoz2.model.GiftCode;
import com.nsoz2.model.CloneChar;
import com.nsoz2.model.History;
import com.nsoz2.clan.Member;
import com.nsoz2.network.Message;
import com.nsoz2.network.Service;
import com.nsoz2.option.ItemOption;
import com.nsoz2.server.*;
import com.nsoz2.skill.Skill;
import com.nsoz2.task.*;
import com.nsoz2.util.Log;
import com.nsoz2.util.NinjaUtils;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CharPersistenceHandler: Quản lý toàn bộ nghiệp vụ lưu trữ cơ sở dữ liệu (saveData, saveRms),
 * đồng bộ tiền tệ/unpaid (gold, coin, yen) và nạp giftcode chưa xử lý.
 */
public abstract class CharPersistenceHandler extends CharInventoryHandler {

    public CharPersistenceHandler(int id) {
        super(id);
    }

    public void useGiftCode(String code) {
        GiftCode.getInstance().use((Char) this, code);
    }
    public void goldUnpaid() {
        if (isUnpaid) {
            return;
        }
        isUnpaid = true;

        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT `amount_unpaid` FROM `players` WHERE `id` = ? LIMIT 1;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, this.id);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    int amount = res.getInt("amount_unpaid");
                    if (amount == 0) {
                        isUnpaid = false;
                        return;
                    } else {
                        addGold(amount);
                        DbManager.updateGold(this.id, this.user.gold);
                    }
                }
            } finally {
                res.close();
                stmt.close();
            }
            if (isUnpaid) {
                DbManager.updateAmountUnpaid(this.id, 0);
                isUnpaid = false;
            }
        } catch (Exception e) {
//             Log.logException("err: " + e.getMessage(), e);
        }
    }

    public void yenUnpaid() {
        if (isYenUnpaid) {
            return;
        }
        isYenUnpaid = true;

        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT `yen_unpaid` FROM `players` WHERE `id` = ? LIMIT 1;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, this.id);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    int amount = res.getInt("yen_unpaid");
                    if (amount == 0) {
                        isYenUnpaid = false;
                        return;
                    } else {
                        addYen(amount);
                        DbManager.updateYen(this.id, (int) this.yen);
                    }
                }
            } finally {
                res.close();
                stmt.close();
            }
            if (isYenUnpaid) {
                DbManager.updateYenUnpaid(this.id, 0);
                isYenUnpaid = false;
            }
        } catch (Exception e) {
//             Log.logException("err: " + e.getMessage(), e);
        }
    }

    public void coinUnpaid() {
        if (isCoinUnpaid) {
            return;
        }
        isCoinUnpaid = true;

        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT `coin_unpaid` FROM `players` WHERE `id` = ? LIMIT 1;", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, this.id);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    int amount = res.getInt("coin_unpaid");
                    if (amount == 0) {
                        isCoinUnpaid = false;
                        return;
                    } else {
                        addCoin(amount);
                        DbManager.updateCoin(this.id, (int) this.coin);
                    }
                }
            } finally {
                res.close();
                stmt.close();
            }
            if (isCoinUnpaid) {
                DbManager.updateCoinUnpaid(this.id, 0);
                isCoinUnpaid = false;
            }
        } catch (Exception e) {
//             Log.logException("err: " + e.getMessage(), e);
        }
    }

    public void giftcodeUnpaid() {
        if (isGiftCodeUnpaid) {
            return;
        }
        isGiftCodeUnpaid = true;
        try (Connection conn = DbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT `giftcode_unpaid` FROM `players` WHERE `id` = ? LIMIT 1;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.setInt(1, this.id);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    String giftCodeString = res.getString("giftcode_unpaid");
                    if (giftCodeString != null && !giftCodeString.equals("")) {
                        useGiftCode(giftCodeString);
                    }
                }
            } finally {
                res.close();
                stmt.close();
            }
            if (isGiftCodeUnpaid) {
                updateCodeUnbaid();
                isGiftCodeUnpaid = false;
            }
        } catch (Exception e) {
            Log.logException("err: ", Char.class, e);
        }
    }

    private void updateCodeUnbaid() {

        try {
            DbManager.executeQuery("UPDATE `players` SET `giftcode_unpaid` = '' WHERE `id` = ? LIMIT 1;", this.id);
        } catch (Exception e) {

        }
    }
    public int getCoinInt() {
        if (this.coin >= 2000000000) {
            return 2000000000;
        }
        return (int) this.coin;
    }

    public int getCoinInBoxInt() {
        if (this.coinInBox >= 2000000000) {
            return 2000000000;
        }
        return (int) this.coinInBox;
    }

    public int getYenInt() {
        if (this.yen >= 2000000000) {
            return 2000000000;
        }
        return (int) this.yen;
    }

    public void saveRms(Message mss) {
        try {
            String key = mss.reader().readUTF();
            int len = mss.reader().readInt();
            byte[] ab = new byte[len];
            mss.reader().read(ab);
            byte type = mss.reader().readByte();
            switch (key) {
                case "KSkill":
                    this.onKSkill = new byte[]{-1, -1, -1};
                    for (int i = 0; i < ab.length; i++) {
                        if (ab[i] != -1) {
                            for (Skill skill : this.vSkill) {
                                if (skill.template.id == ab[i]) {
                                    this.onKSkill[i] = ab[i];
                                    break;
                                }
                            }
                        }
                    }
                    break;

                case "OSkill":
                    this.onOSkill = new byte[]{-1, -1, -1, -1, -1};
                    for (int i = 0; i < ab.length; i++) {
                        if (ab[i] != -1) {
                            for (Skill skill : this.vSkill) {
                                if (skill.template.id == ab[i]) {
                                    this.onOSkill[i] = ab[i];
                                    break;
                                }
                            }
                        }
                    }
                    break;

                case "CSkill":
                    if (ab != null) {
                        this.onCSkill = ab;
                    }
                    break;
            }
        } catch (Exception ex) {
            Log.logException("Save Rms err: ", Char.class, ex);
        }
    }


    public JSONArray getDefaultMap() {
        JSONArray jArr = new JSONArray();
        int mapId = this.saveCoordinate;
        if (zone != null) {
            if (zone.tilemap.isNotSave()) {
                mapId = this.mapBeforeEnterPB;

            }
            if (zone.tilemap.isChienTruong()) {
                if (war != null) {
                    war.removeMember((Char) this);
                }
            }
        }
        short[] xy = NinjaUtils.getXY(mapId);
        jArr.add(mapId);
        jArr.add(xy[0]);
        jArr.add(xy[1]);
        return jArr;
    }

    public synchronized void saveData() {
        if (isLoadFinish() && !saving) {
            try (Connection conn = DbManager.getConnection()) {
                saving = true;
                Log.debug("save data " + this.name);


                JSONArray bags = new JSONArray();
                for (int i = 0; i < this.numberCellBag; i++) {
                    try {
                        if (this.bag[i] != null) {
                            bags.add(this.bag[i].toJSONObject());

                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray boxs = new JSONArray();
                for (int i = 0; i < this.numberCellBox; i++) {
                    try {
                        if (this.box[i] != null) {
                            boxs.add(this.box[i].toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray equipment = new JSONArray();
                for (int i = 0; i < 16; i++) {
                    try {
                        if (this.equipment[i] != null) {
                            equipment.add(this.equipment[i].toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray fashion = new JSONArray();
                for (int i = 0; i < 16; i++) {
                    try {
                        if (this.fashion[i] != null) {
                            fashion.add(this.fashion[i].toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray mounts = new JSONArray();
                for (int i = 0; i < 5; i++) {
                    try {
                        if (this.mount[i] != null) {
                            mounts.add(this.mount[i].toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray maskBox = new JSONArray();
                for (Item item : this.maskBox) {
                    try {
                        if (item != null) {
                            maskBox.add(item.toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray collectionBox = new JSONArray();
                for (Item item : this.collectionBox) {
                    try {
                        if (item != null) {
                            collectionBox.add(item.toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray bijuus = new JSONArray();
                for (int i = 0; i < 5; i++) {
                    try {
                        if (this.bijuu[i] != null) {
                            bijuus.add(this.bijuu[i].toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray skill = new JSONArray();
                if (this.vSkill != null) {
                    try {
                        for (Skill s : this.vSkill) {
                            skill.add(s.toJSONObject());
                        }
                    } catch (Exception e) {
                    }
                }
                JSONArray rewards = new JSONArray();
                if (this.reward != null) {
                    for (int i = 0; i < 5; i++) {
                        rewards.add(this.reward[i]);
                    }
                }

                JSONObject data = new JSONObject();
                data.put("exp", this.exp);
                data.put("expDown", this.expDown);
                data.put("level", this.level);
                data.put("countPB", this.countPB);
                if (this.countPB == 0) {
                    Dungeon dungeon = (Dungeon) findWorld(World.DUNGEON);
                    if (dungeon != null) {
                        data.put("dungeonId", dungeon.getId());
                        data.put("receivedRewardPB", this.receivedRewardPB);
                    }

                }
                data.put("pointPB", this.pointPB);
                data.put("hieuChien", this.hieuChien);
                data.put("countFinishDay", this.countFinishDay);
                data.put("countDoneTaskDay", this.countDoneTaskDay);
                data.put("countLoopBoss", this.countLoopBoss);
                data.put("limitKyNangSo", this.limitKyNangSo);
                data.put("limitTiemNangSo", this.limitTiemNangSo);
                data.put("limitBangHoa", this.limitBangHoa);
                data.put("limitPhongLoi", this.limitPhongLoi);
                data.put("lastTimeOutClan", this.lastTimeOutClan);
                data.put("maskId", this.getMaskId());
                data.put("coinMax", this.coinMax);
                data.put("tayTiemNang", this.tayTiemNang);
                data.put("tayKyNang", this.tayKyNang);
                data.put("numberUseExpanedBag", this.numberUseExpanedBag);
                data.put("levelUpTime", this.levelUpTime);
                data.put("not_received_exp", this.notReceivedExp);
                data.put("count_arena", this.countArenaT);
                data.put("sevenbeasts_id", this.sevenBeastsID);
                data.put("type_sevenbeasts", this.typeSevenBeasts);

                if (gloryTask != null) {
                    JSONObject glory = new JSONObject();
                    glory.put("type", gloryTask.type);
                    glory.put("quantity", gloryTask.quantity);
                    glory.put("progress", gloryTask.progress);
                    glory.put("requireUseEquip", gloryTask.requireUseEquip);
                    data.put("gloryTask", glory);
                }

                JSONArray taskOrders = new JSONArray();
                for (TaskOrder task : this.taskOrders) {
                    JSONObject obj = new JSONObject();
                    obj.put("taskId", task.taskId);
                    obj.put("count", task.count);
                    obj.put("maxCount", task.maxCount);
                    obj.put("killId", task.killId);
                    obj.put("mapId", task.mapId);
                    taskOrders.add(obj);
                }
                if (this.huyHieu > -1) {
                    data.put("rank", this.huyHieu);
                }

                JSONObject warObj = new JSONObject();
                warObj.put("point", warPoint);
                warObj.put("time", time);
                warObj.put("faction", faction);
                warObj.put("kill", nKill);
                warObj.put("dead", nDead);
                warObj.put("rewarded", isRewarded);
                data.put("war", warObj);

                data.put("taskOrder", taskOrders.toJSONString());
                data.put("countUseItemGlory", this.countUseItemGlory);
                data.put("timeCountDown", this.timeCountDown);
                data.put("countUseItemDungeo", this.countUseItemDungeo);
                data.put("countUseItemBeast", this.countUseItemBeast);
                data.put("countGlory", this.countGlory);
                data.put("pointAo", this.pointAo);
                data.put("pointGangTay", this.pointGangTay);
                data.put("pointGiay", this.pointGiay);
                data.put("pointLien", this.pointLien);
                data.put("pointNgocBoi", this.pointNgocBoi);
                data.put("pointNhan", this.pointNhan);
                data.put("pointNon", this.pointNon);
                data.put("pointPhu", this.pointPhu);
                data.put("pointQuan", this.pointQuan);
                data.put("pointTinhTu", this.pointTinhTu);
                data.put("pointUyDanh", this.pointUyDanh);
                data.put("pointVuKhi", this.pointVuKhi);

                data.put("countEnterFujukaSanctuary", this.countEnterFujukaSanctuary);
                data.put("reward", rewards.toJSONString());
                JSONObject obj = new JSONObject();
                obj.put("range", this.range);
                obj.put("type_pick_item", this.typeAutoPickItem);
                data.put("auto", obj);
                JSONArray potentials = new JSONArray();
                if (this.potential != null) {
                    for (int i = 0; i < 4; i++) {
                        potentials.add(this.potential[i]);
                    }
                }

/// điểm danh tân thủ
                JSONArray giftOnline = new JSONArray();
                if (this.giftOnline != null) {
                    for (int i = 0; i < 21; i++) {
                        giftOnline.add(this.giftOnline[i]);
                    }
                }

                JSONArray giftTongnap = new JSONArray();
                if (this.giftTongnap != null) {
                    for (int i = 0; i < 10; i++) {
                        giftTongnap.add(this.giftTongnap[i]);
                    }
                }


                JSONArray map = new JSONArray();
                try {
                    if (this.hp > 0 && !zone.tilemap.isNotSave()) {
                        if (clone != null && !clone.isNhanBan) {
                            map.add(clone.mapId);
                            map.add(clone.x);
                            map.add(clone.y);
                        } else {
                            map.add(this.mapId);
                            map.add(this.x);
                            map.add(this.y);
                        }
                    } else {
                        map = getDefaultMap();
                    }
                } catch (Exception e) {
                    map = getDefaultMap();
                }

                String task = "";
                if (this.taskMain != null) {
                    JSONObject t = new JSONObject();
                    t.put("id", this.taskMain.taskId);
                    t.put("index", this.taskMain.index);
                    t.put("count", this.taskMain.count);
                    task = t.toJSONString();
                }
                JSONArray effects = em.toJSONArray();
                JSONArray friends = new JSONArray();
                if (this.friends != null) {
                    Friend[] fr = getFriends();
                    for (Friend friend : fr) {
                        friends.add(friend.toJSONObject());
                    }
                }

                JSONArray enemies = new JSONArray();
                if (this.enemies != null) {
                    Friend[] es = getEnemies();
                    for (Friend enemy : es) {
                        enemies.add(enemy.toJSONObject());
                    }
                }
                if (this.onOSkill == null) {
                    this.onOSkill = new byte[]{-1, -1, -1, -1, -1};
                }
                if (this.onKSkill == null) {
                    this.onKSkill = new byte[]{-1, -1, -1};
                }
                if (this.onCSkill == null) {
                    this.onCSkill = new byte[]{};
                }
                String onOSkill = Arrays.toString(this.onOSkill).replace(" ", "");
                String onCSkill = Arrays.toString(this.onCSkill).replace(" ", "");
                String onKSkill = Arrays.toString(this.onKSkill).replace(" ", "");
                String jquaTichLuy = Arrays.toString(this.quaTichLuy).replace(" ", "");
                String jData = data.toJSONString();
                String jSkill = skill.toJSONString();
                String jPotentials = potentials.toJSONString();
                String jMap = map.toJSONString();
                String jEquipment = equipment.toJSONString();
                String jBag = bags.toJSONString();
                String jBoxes = boxs.toJSONString();
                String jMounts = mounts.toJSONString();
                String jFashion = fashion.toJSONString();
                String jEffects = effects.toJSONString();
                String jFriends = friends.toJSONString();
                String jEnemies = enemies.toJSONString();
                String jMaskBox = maskBox.toString();
                String jCollection = collectionBox.toString();
                String jBijuus = bijuus.toString();
                /// điểm danh tân thủ
                String JgiftOnline = giftOnline.toJSONString();

                String JgiftTongnap = giftTongnap.toJSONString();

                try {
                    boolean flag = false;
                    Long last = GameData.HASH_MAP.get(this.id);
                    long now = System.currentTimeMillis();
                    if (last != null) {
                        if (now - last < 300000) {
                            flag = true;
                        }
                    }

                    if (!flag) {
                        // tắt mongodb
                        if (Config.getInstance().isMongodbBat()) {
                            MongoCollection collection = MongoDbConnection.getCollection("player");
                            Document document = new Document();
                            document.put("player_id", this.id);
                            document.put("coin", coin);
                            document.put("gold", user.gold);
                            document.put("yen", yen);
                            document.put("bag", jBag);
                            document.put("box", jBoxes);
                            document.put("equipment", jEquipment);
                            document.put("mount", jMounts);
                            document.put("fashion", jFashion);
                            document.put("bijuu", jBijuus);
                            document.put("update_at", now);
                            collection.insertOne(document);
                        }
                        GameData.HASH_MAP.put(this.id, now);
                    }

                } catch (Exception e) {
                    Log.logException("Lỗi save data player (1)", Char.class, e);
                }
                if (this.clan != null) {
                    Member mem = this.clan.getMemberByName(this.name);
                    if (mem != null) {
                        mem.setLevel(this.level);
                    }
                }
                PreparedStatement stmt = conn.prepareStatement(SQLStatement.SAVE_DATA_PLAYER);
                try {
                    stmt.setLong(1, this.coin);
                    stmt.setLong(2, this.coinInBox);
                    stmt.setLong(3, this.yen);
                    stmt.setInt(4, this.potentialPoint);
                    stmt.setInt(5, this.skillPoint);
                    stmt.setInt(6, this.saveCoordinate);
                    stmt.setInt(7, this.numberCellBag);
                    stmt.setInt(8, this.numberCellBox);
                    stmt.setLong(9, this.lastLogoutTime);
                    int clan = this.clan != null ? this.clan.getId() : 0;
                    stmt.setInt(10, clan);
                    stmt.setInt(11, this.taskId);
                    stmt.setInt(12, this.rewardPB);
                    stmt.setString(13, this.message);
                    stmt.setString(14, jData);
                    stmt.setString(15, jSkill);
                    stmt.setString(16, jPotentials);
                    stmt.setString(17, jMap);
                    stmt.setString(18, jEquipment);
                    stmt.setString(19, jBag);
                    stmt.setString(20, jBoxes);
                    stmt.setString(21, jMounts);
                    stmt.setString(22, task);
                    stmt.setString(23, jFashion);
                    stmt.setByte(24, this.classId);
                    stmt.setString(25, jEffects);
                    stmt.setString(26, jFriends);
                    stmt.setInt(27, this.head);
                    stmt.setInt(28, this.weapon);
                    stmt.setInt(29, this.body);
                    stmt.setInt(30, this.leg);
                    stmt.setString(31, jEnemies);
                    stmt.setString(32, onCSkill);
                    stmt.setString(33, onKSkill);
                    stmt.setString(34, onOSkill);
                    stmt.setString(35, jMaskBox);
                    stmt.setString(36, jCollection);
                    stmt.setString(37, jBijuus);
                    stmt.setString(38, jquaTichLuy);
                    stmt.setInt(39, topvxmm);
                    stmt.setInt(40, topBoss);
                    stmt.setInt(41, topThanNong);
                    stmt.setInt(42, topvxmmLuong);
                    stmt.setInt(43, passProtech);
                    stmt.setInt(44, this.user.gold);
                    /// điểm danh tân thủ
                    stmt.setString(45, JgiftOnline);

                    stmt.setInt(46, this.timeOnline);

                    stmt.setString(47, JgiftTongnap);
                    stmt.setInt(48, this.id);
                    stmt.executeUpdate();
                } finally {
                    stmt.close();
                }

            } catch (Exception e) {
                Log.logException("Lỗi save data player (2)", Char.class, e);

            } finally {
                saving = false;
                if (clone != null) {
                    clone.saveData();
                }
                if (user != null) {
                    this.user.saveData();
                }
            }
        }
    }

}
