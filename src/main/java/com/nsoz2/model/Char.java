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
 * Char (Player Character Aggregate Root).
 * Kế thừa toàn bộ hệ thống NPC từ CharNpcHandler và thuộc tính từ CharBase.
 * Đã clean hóa bóc tách thành công 8,947 dòng NPC ra CharNpcHandler.
 */
public class Char extends CharNpcHandler {

    public Char(int id) {
        super(id);
        this.spatial = new SpatialComponent(this);
        this.state = new CharacterStateComponent(this);
        this.inventory = new InventoryComponent(this);
        this.combat = new CombatComponent(this);
        this.social = new SocialComponent(this);
        this.chatGlobal = new ChatGlobal(this);
        this.em = new EffectManager(this);
    }

    public static Char findCharByName(String name) {
        Char _char = ServerManager.findCharByName(name);
        if (_char != null) {
            if (_char.clone != null && !_char.clone.isNhanBan) {
                return _char.clone;
            } else {
                return _char;
            }
        }
        return null;
    }


    public CharacterStatsSnapshot getStatsSnapshot() {
        if (this.statsSnapshot == null) {
            this.statsSnapshot = CharacterStatsSnapshot.from(this);
        }
        return this.statsSnapshot;
    }

    public void invalidateStatsSnapshot() {
        this.statsSnapshot = null;
    }

    public byte typeVXMM; // chanle
    public int xuWin; // chanle

    public Item[] ItemKhaiHoa; // hóa hình
    public Item[] ItemCaiTao_HoaHinh;

    public void updateEverySecond() {

        if (mobMe != null) {  // fix quái không hồi sinh
            mobMe.attack(this);
        }

        if (Event.isEvent()) {
            if (escortedEvent != null) {
                if (Event.isTrungThu()) {
                    if (escortedEvent.isDead) {
                        escortEventFailed();
                    } else {
                        int x = 0;
                        int y = 0;
                        if (mapId == MapName.TRUONG_HIROSAKI) {
                            x = 828;
                            y = 312;
                        } else if (mapId == MapName.TRUONG_HARUNA) {
                            x = 1241;
                            y = 360;
                        } else if (mapId == MapName.TRUONG_OOKAZA) {
                            x = 848;
                            y = 648;
                        }
                        int d = NinjaUtils.getDistance(x, y, escortedEvent.x, escortedEvent.y);
                        if (d < 100) {
                            escortEventFinish();
                        }
                    }
                }
            }
        }
        final Calendar rightNow = Calendar.getInstance(); /// điểm danh tân thủ
        int hour = rightNow.get(11);
        int min = rightNow.get(12);
        int sec = rightNow.get(13);
        if (sec % 60 == 0) {
            this.timeOnline++;
        }
        if (!isNhanBan) {
            if (invite != null) {
                invite.update();
            }

        }
        if (this.timeCountDown > 0) {
            this.timeCountDown--;
//                System.out.println(" time "+ timeCountDown);
            if (this.timeCountDown == 0) {
                if (isHuman) {
                    if (clone != null) {
                        if (clone.isNhanBan && !clone.isDead) {
                            clone.outZone();
                            clone.isDead = true;
                        }
                    }
                } else {
                    this.isDead = true;
                    ((CloneChar) this).human.switchToMe();
                }
            }
        }
        WarClan gtc = null;
        if (this.clan != null && this.clan.gtcID != -1) {
            if (WarClan.gtcs.containsKey(this.clan.gtcID)) {
                gtc = WarClan.gtcs.get(this.clan.gtcID);
            }
        }
        if (gtc != null) {
            if (System.currentTimeMillis() > gtc.time && this.zone.map.id == 117) {
                gtc.rest();
            }
            if (System.currentTimeMillis() > gtc.time && !gtc.isDatCuoc && !gtc.start && !gtc.rest && !gtc.viewkq) {
                gtc.start();
            }
            if (System.currentTimeMillis() > gtc.time && !gtc.rest && !gtc.viewkq && gtc.start) {
                gtc.viewKetqua();
            }
            if (System.currentTimeMillis() > gtc.time && !gtc.rest && gtc.viewkq) {
                if ((gtc.isDatCuoc && !gtc.start) || (!gtc.isDatCuoc && gtc.start)) {
                    gtc.rest();
                }
            }
        }
        if (this.zone != null && this.zone.map != null && this.zone.map.vithu != null) {
            if ((this.zone.map.id == 169) && System.currentTimeMillis() > this.zone.map.vithu.timeVithu) {
                this.setTypePk(Char.PK_NORMAL);
                short[] xy = NinjaUtils.getXY(this.mapBeforeEnterPB);
                this.setXY(xy);
                this.changeMap(this.mapBeforeEnterPB);
            }
            if (this.zone.map.id == 171 && System.currentTimeMillis() > this.zone.map.vithu.timeVithu) {
                this.setTypePk(Char.PK_NORMAL);
                short[] xy = NinjaUtils.getXY(this.mapBeforeEnterPB);
                this.setXY(xy);
                this.changeMap(this.mapBeforeEnterPB);
            }

        } else {
            if (this.zone != null && this.zone.map != null) {
                if ((this.zone.map.id == 169)) {
                    this.setTypePk(Char.PK_NORMAL);
                    short[] xy = NinjaUtils.getXY(this.saveCoordinate);
                    this.setXY(xy);
                    this.changeMap(this.saveCoordinate);
                }
                if (this.zone.map.id == 171) {
                    this.setTypePk(Char.PK_NORMAL);
                    short[] xy = NinjaUtils.getXY(this.saveCoordinate);
                    this.setXY(xy);
                    this.changeMap(this.saveCoordinate);
                }
            }

        }


    }

    public void updateEveryThirtSecond() {
        if (this.user != null) {
//            goldUnpaid();
//            this.user.checkLock();
        }

    }

    // nạp lượng
    public void updateEveryHalfSecond() {
        // System.out.println("run here!");
        if (isInfected() && (isVillage() || isSchool())) {
            cure();
        }
        int hp = 0;
        int mp = 0;
        boolean hasOption99 = false;
        boolean hasDamageEffect = false;

        if (em != null) {
            if (!this.isDead) {
                List<Effect> effects = em.filter(e -> {
                    int id = e.template.id;
                    int type = e.template.type;
                    return id == 36 || id == 42 || type == 0 || type == 12 || type == 13 || type == 4 || type == 17;
                });
                if (!effects.isEmpty()) {
                    for (Effect eff : effects) {
                        // hiệu ứng item sài ldcc
                        if (eff.template.id == 36 || eff.template.id == 42) {
                            hp += eff.param;
                            mp += eff.param;
                        } else {
                            switch (eff.template.type) {
                                case 0:
                                case 12:
                                    int i = eff.param;
                                    hp += i;
                                    mp += i;
                                    break;
                                case 13:
                                    hp -= maxHP * 3 / 100;
                                    hasDamageEffect = true;
                                    break;
                                case 4:
                                case 17:
                                    hp += eff.param;
                                    eff.tickCount++;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }

            if (this.options[136] > 0) {
                if (System.currentTimeMillis() - lastTimeReductionDame > 45000L) {
                    if (NinjaUtils.nextInt(0, 100) < 10) {
                        lastTimeReductionDame = System.currentTimeMillis();
                        this.isReductionDame = true;
                    }
                } else if (isReductionDame && System.currentTimeMillis() - lastTimeReductionDame > 5000L) {
                    this.isReductionDame = false;
                }
            }

            // Option 99: "Mỗi nửa giây hồi phục # HP và MP" từ trang bị / ngọc (chạy mỗi 500ms)
            if (!this.isDead && this.options[99] > 0) {
                hp += this.options[99];
                mp += this.options[99];
                hasOption99 = true;
            }
            if (isCool()) {
                hp /= 2;
                mp /= 2;
            }
            if (hp != 0 || mp != 0) {
                synchronized (this) {
                    int preHP = this.hp;
                    int preMP = this.mp;
                    this.hp += hp;
                    this.mp += mp;
                    if (this.hp > this.maxHP) {
                        this.hp = this.maxHP;
                    }
                    if (this.mp > this.maxMP) {
                        this.mp = this.maxMP;
                    }
                    if (this.hp != preHP || this.mp != preMP) {
                        if (this.hp <= 0) {
                            zone.getService().loadHP(this);
                            getService().updateHp();
                            startDie();
                        } else {
                            if (this.hp != preHP) {
                                zone.getService().loadHP(this);
                                getService().updateHp();
                            }
                            if (this.mp != preMP) {
                                getService().updateMp();
                            }
                        }
                    }
                }
            }

            // Dọn dẹp effect hết hạn sau khi đã tick hồi phục ở nhịp hiện tại
            em.update();

//             hiệu ứng khai hóa

//            if (addEffectItemKhaiHoa(6)) {
//                if (getSys() == 1) {
//                    zone.getService().addEffect(this, 119, 2000, 3000, 0);
//                } else if (getSys() == 2) {
//                    zone.getService().addEffect(this, 199, 2000, 3000, 0);
//                } else if (getSys() == 3) {
//                    zone.getService().addEffect(this, 200, 3000, 3000, 0);
//                }
//            }

//            if (addEffectItemKhaiHoa(9)) {
//
//            }

            // hiệu ứng danh hiệu
            if (fashion[15] != null) {

                if (fashion[15].id == ItemName.TRUM_PHAI) {
                    if (classId == 1) {
                        zone.getService().addEffect(this, 16, 100, 0, 0);
                    } else if (classId == 2) {
                        zone.getService().addEffect(this, 17, 100, 0, 0);
                    } else if (classId == 3) {
                        zone.getService().addEffect(this, 15, 100, 0, 0);
                    } else if (classId == 4) {
                        zone.getService().addEffect(this, 13, 100, 0, 0);
                    } else if (classId == 5) {
                        zone.getService().addEffect(this, 12, 100, 0, 0);
                    } else if (classId == 6) {
                        zone.getService().addEffect(this, 14, 100, 0, 0);
                    }
                }


                if (fashion[15].id == ItemName.TRUM_TRUONG) {
                    if (getSys() == 1) {
                        zone.getService().addEffect(this, 19, 100, 0, 0);
                    } else if (getSys() == 2) {
                        zone.getService().addEffect(this, 20, 100, 0, 0);
                    } else if (getSys() == 3) {
                        zone.getService().addEffect(this, 18, 100, 0, 0);
                    }
                }


                if (fashion[15].id == ItemName.FAN_CUNG) {

                    zone.getService().addEffect(this, 231, 10000, 0, 0);
                }




                if (fashion[15].id == ItemName.V_VIP) {
                    if (getSys() == 1) {
                        zone.getService().addEffect(this, 301, 10000, 0, 0);
                    } else if (getSys() == 2) {
                        zone.getService().addEffect(this, 302, 10000, 0, 0);
                    } else if (getSys() == 3) {
                        zone.getService().addEffect(this, 303, 10000, 0, 0);
                    }
                }


                if (fashion[15].id == ItemName.DE_NHAT_NINJA) {

                    zone.getService().addEffect(this, 201, 10000, 0, 0);
                }
                if (fashion[15].id == ItemName.DE_NHI) {

                    zone.getService().addEffect(this, 202, 10000, 0, 0);
                }
                if (fashion[15].id == ItemName.DE_TAM) {

                    zone.getService().addEffect(this, 203, 10000, 0, 0);
                }
                if (fashion[15].id == ItemName.DE_TU) {

                    zone.getService().addEffect(this, 204, 10000, 0, 0);
                }




                if (fashion[10] != null) {
                    if (fashion[10].id == ItemName.LONG_DEN_RONG && isVersionAbove(180)) {
                        zone.getService().addEffect(this, 241, 5000, 0, 0);
                    }
                }

            }

            if (this.getMask() != null) {
                updateEffNinja(this);
            }

            if (this.zone.tilemap.isNotAllowPkPoint() && this.hieuChien > 0) {
                short[] xy = NinjaUtils.getXY(this.saveCoordinate);
                setXY(xy[0], xy[1]);
                changeMap(this.saveCoordinate);
            }

            if (BlackSaturday.getInstance().status == 1 && !zone.tilemap.isNotBlack()) { /// t7 đen tối
                if (!isVillage() && !isSchool()) {
                    if (this.clan != null && this.typePk != PK_PHE && this.level >= 70 && this.isHuman) {
                        this.setTypePk(PK_PHE);
                    }
                    if (this.clan != null && this.typePk == PK_PHE && this.level < 70 && this.isHuman
                            || this.clan == null && this.typePk == PK_PHE && this.isHuman) {
                        this.setTypePk(PK_NORMAL);
                    }
                }
            }


        }

    }

    private boolean addEffectItemKhaiHoa(int cap) {
        int countItem = 0;
        if (equipment != null) {
            for (Item item : equipment) {
                if (item != null && item.options != null) {
                    for (ItemOption itemOption : item.options) {
                        if (itemOption != null && itemOption.optionTemplate != null && itemOption.optionTemplate.id == 157) {
                            if (itemOption.param >= cap) {
                                countItem++;
                            }
                        }
                    }
                }

            }
        }
        return countItem >= 10;
    }

    private void updateEffNinja(Char charNinja) {
        Item itemMask = charNinja.getMask();
        if (itemMask.id == 774) {
            if (itemMask.upgrade >= 6 && itemMask.upgrade < 10) {
                zone.getService().addEffect(this, 22, 5000, 3, 0);
            }
            if (itemMask.upgrade >= 10) {
                zone.getService().addEffect(this, 23, 5000, 3, 0);
            }
        }
        if (itemMask.id == 787) {
            if (itemMask.upgrade >= 6 && itemMask.upgrade < 10) {
                zone.getService().addEffect(this, 26, 5000, 3, 0);
            }
            if (itemMask.upgrade >= 10) {
                zone.getService().addEffect(this, 27, 5000, 3, 0);
            }
        }
        if (itemMask.id == 786) {
            if (itemMask.upgrade >= 6 && itemMask.upgrade < 10) {
                zone.getService().addEffect(this, 24, 5000, 3, 0);
            }
            if (itemMask.upgrade >= 10) {
                zone.getService().addEffect(this, 25, 5000, 3, 0);
            }
        }
    }

    // bật hiệu ứng khói trắng

    /// dev fix lag 13/8
    public void updateEveryFiveSecond() {
        if (isAutoPlay()) {
            int index = getIndexItemByIdInBag(ItemName.THIEN_BIEN_LENH);
            if (index == -1 || this.bag[index].isExpired()) {
                setAutoPlay(false);
                getService().turnOffAuto();
            }
        }
        if (!(isBot())) {
            if (this.coin < 0 || this.coinInBox < 0 || this.yen < 0 || user != null && user.gold < 0) {
                user.lock();
                return;
            }
        }
        if (!this.isDead) {
            int hp = this.options[30] + this.options[120];
            int mp = this.options[27] + this.options[119];

            // Chiêu 25 Kiếm (Kakyuu - Hỏa Vị Tiêu Hóa): Tăng hồi phục thức ăn mỗi nhịp 5 giây (chỉ áp dụng cho phái Kiếm)
            Effect foodEff = this.em.findByType((byte) 0);
            if (this.classId == 1 && (foodEff != null || this.options[30] > 0)) {
                Skill k25 = getSkill(4);
                if (k25 != null && k25.point > 0) {
                    int pt = k25.point;
                    int flatBonus = (pt == 1) ? 50 : (pt == 2 ? 75 : (pt == 3 ? 100 : (pt == 4 ? 125 : (pt == 5 ? 150 : 200))));
                    int percentBonus = (pt == 1 || pt == 2) ? 1 : ((pt == 3) ? 2 : ((pt == 4) ? 3 : ((pt == 5) ? 4 : 5)));
                    if (k25.options != null && k25.options.length >= 2) {
                        flatBonus = k25.options[0].param;
                        percentBonus = k25.options[1].param;
                    }
                    hp += flatBonus + (int) ((long) this.maxHP * percentBonus / 100);
                }
            }

            if (isCool()) {
                hp /= 2;
                mp /= 2;
            }

            if (this.zone.tilemap.id == 167) {
                this.hp -= this.hp * 20 / 100;
                addHp(-this.hp * 20 / 100);
                zone.getService().chat(this.id, "Ngột ngạt quá, chết tiệt");
            }

            if (hp != 0 || mp != 0) {
                synchronized (this) {
                    int preHP = this.hp;
                    int preMP = this.mp;
                    this.hp += hp;
                    this.mp += mp;
                    if (this.hp > this.maxHP) {
                        this.hp = this.maxHP;
                    }
                    if (this.mp > this.maxMP) {
                        this.mp = this.maxMP;
                    }
                    if (this.hp != preHP || this.mp != preMP) {
                        if (this.hp != preHP) {
                            zone.getService().loadHP(this);
                            getService().updateHp();
                        }
                        if (this.mp != preMP) {
                            getService().updateMp();
                        }
                        if (this.hp <= 0) {
                            startDie();
                            return;
                        }
                    }
                }
            } else if (zone != null) {
                getService().updateHp();
                getService().updateMp();
            }
            if (haoQuang > -1) {
                int sys = getSys();
                int effId = 0;
                switch (sys) {
                    case 0:
                    case 1:
                        if (haoQuang == 0) {
                            effId = 9;
                        }
                        if (haoQuang == 1) {
                            effId = 10;
                        }
                        if (haoQuang == 2) {
                            effId = 11;
                        }
                        break;

                    case 2:
                        if (haoQuang == 0) {
                            effId = 3;
                        }
                        if (haoQuang == 1) {
                            effId = 4;
                        }
                        if (haoQuang == 2) {
                            effId = 5;
                        }
                        break;

                    case 3:
                        if (haoQuang == 0) {
                            effId = 6;
                        }
                        if (haoQuang == 1) {
                            effId = 7;
                        }
                        if (haoQuang == 2) {
                            effId = 8;
                        }
                        break;
                }
                // fix hào quang
                zone.getService().addEffect(this, effId, 5000, 0, 0);
            }
            if (this.options[158] > 0 || this.options[159] > 0) {
                int rand = NinjaUtils.nextInt(100);
                int percentage = this.options[158] > 0 ? this.options[158] : this.options[159];
                if (rand < percentage) {
                    List<Mob> mobs = getMonsterByDistance(200);
                    for (Mob mob : mobs) {
                        int dameHit = mob.hp * 50 / 100;
                        mob.addHp(-dameHit);
                        if (mob.hp <= 0) {
                            mob.die();
                        }
                        zone.getService().attackMonster(dameHit, false, mob);
                        zone.getService().addEffect(mob, 104, 500, 5, 0);
                    }
                }
            }
            // get monsters within 2m
            if (this.options[134] > 0 || this.options[155] > 0) {
                int rand = NinjaUtils.nextInt(100);
                int percentage = this.options[134] > 0 ? this.options[134] : this.options[155];
                if (rand < percentage) {
                    List<Mob> mobs = getMonsterByDistance(200);
                    for (Mob mob : mobs) {
                        int dameHit = mob.hp * 30 / 100;
                        mob.addHp(-dameHit);
                        if (mob.hp <= 0) {
                            mob.die();
                        }
                        zone.getService().attackMonster(dameHit, false, mob);
                        zone.getService().addEffect(mob, 65, 5, 5, 0);
                    }
                }
                if (this.options[155] > 0) {
                    if (rand < this.options[155]) {
                        List<Char> chars = getCharByDistance(200);
                        for (Char c : chars) {
                            int dameHit = c.hp * 30 / 100;
                            c.addHp(-dameHit);
                            if (c.hp <= 0) {
                                c.startDie();
                            }
                            zone.getService().attackCharacter(dameHit, 0, c);
                            zone.getService().addEffect(c, 65, 5, 5, 0);
                        }
                    }
                }
            }
        }
        /// tắt update lượng fix lag
//       goldUnpaid();
//       yenUnpaid();
//       coinUnpaid();
    }

    public List<Mob> getMonsterByDistance(int distance) {
        ArrayList<Mob> monsters = new ArrayList<>();
        List<Mob> mobs = zone.getMonsters();
        for (Mob mob : mobs) {
            int mobDistance = NinjaUtils.getDistance(mob.x, mob.y, this.x, this.y);
            if (mobDistance <= distance) {
                monsters.add(mob);
            }
        }
        return monsters;
    }

    public List<Char> getCharByDistance(int distance) {
        ArrayList<Char> characters = new ArrayList<>();
        if (isLang() || isSchool()) {
            return characters;
        }
        List<Char> chars = zone.getChars();
        for (Char c : chars) {
            int charDistance = NinjaUtils.getDistance(c.x, c.y, this.x, this.y);
            if (charDistance <= distance && isMeCanAttackOtherPlayer(c)) {
                characters.add(c);
            }
        }
        return characters;
    }

    public short[] getFashion() {
        short[] thoiTrang = new short[10];
        thoiTrang[0] = this.ID_HAIR;
        thoiTrang[1] = this.ID_BODY;
        thoiTrang[2] = this.ID_LEG;
        thoiTrang[3] = this.ID_WEA_PONE;
        thoiTrang[4] = this.ID_PP;
        thoiTrang[5] = this.ID_NAME;
        thoiTrang[6] = this.ID_HORSE;
        thoiTrang[7] = this.ID_RANK;
        thoiTrang[8] = this.ID_MAT_NA;
        thoiTrang[9] = this.ID_BIEN_HINH;
        return thoiTrang;
    }

    public void updateHPMount(int hp) {
        Mount mount = this.mount[4];
        if (mount != null) {
            for (ItemOption option : mount.options) {
                if (option.optionTemplate.id == 66) {
                    option.param += hp;
                    if (option.param > 1000) {
                        option.param = 1000;
                    }
                    if (option.param < 0) {
                        option.param = 0;
                    }
                    break;
                }
            }
        }
    }

    public Skill getSkill(int templateId) {
        if (this.vSkill != null) {
            for (Skill s : this.vSkill) {
                if (s != null && s.template != null && s.template.id == templateId) {
                    return s;
                }
            }
        }
        return null;
    }

    /// fix skill phân thân
    public void upSkill(Message ms) {
        try {
            if (this.isDead) {
                return;
            }
            int templateId = ms.reader().readUnsignedShort();
            int point = ms.reader().readUnsignedByte();
            if (point > this.skillPoint || point < 0) {
                return;
            }
            int size = vSkill.size();
            for (int i = 0; i < size; i++) {
                if (vSkill.get(i).template.id == templateId) {
                    Skill check = vSkill.get(i);
                    int maxPoint = check.template.maxPoint;
                    if (check.point == maxPoint) {
                        serverDialog("Kỹ năng đã đạt cấp tối đa.");
                        return;
                    }
                    if (check.point + point > maxPoint) {
                        serverDialog("Kỹ năng chỉ có thể lên cấp tối đa là " + maxPoint);
                        return;
                    }
                    if (templateId >= 67 && templateId <= 72) {
                        serverDialog("Không thể nâng cấp kĩ năng này");
                        return;
                    }
                    Skill skill = GameData.getInstance().getSkill(this.classId, templateId,
                            vSkill.get(i).point + point);
                    if (skill.level > this.level) {
                        serverDialog("Trình độ không đủ yêu cầu.");
                        return;
                    }
                    vSkill.set(i, skill);
                    if (skill.template.type == Skill.SKILL_AUTO_USE) {
                        for (int j = 0; j < vSupportSkill.size(); j++) {
                            if (vSupportSkill.get(j).template.id == templateId) {
                                vSupportSkill.set(j, skill);
                            }
                        }
                    } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK
                            || skill.template.type == Skill.SKILL_CLICK_LIVE
                            || skill.template.type == Skill.SKILL_CLICK_USE_BUFF
                            || skill.template.type == Skill.SKILL_CLICK_NPC)
                            && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 && skill.point > 0))) {

                        for (int j = 0; j < vSkillFight.size(); j++) {
                            if (vSkillFight.get(j).template.id == templateId) {
                                vSkillFight.set(j, skill);
                            }
                        }
                    }
                    for (int j = 0; j < this.onOSkill.length; j++) {
                        if (onOSkill[j] == templateId) {
                            onOSkill[j] = (byte) skill.template.id;
                        }
                    }
                    for (int j = 0; j < this.onKSkill.length; j++) {
                        if (onKSkill[j] == templateId) {
                            onKSkill[j] = (byte) skill.template.id;
                        }
                    }
                    if (onCSkill != null) {
                        for (int j = 0; j < this.onCSkill.length; j++) {
                            if (onCSkill[j] == templateId) {
                                onCSkill[j] = (byte) skill.template.id;
                            }
                        }
                    }
                    this.skillPoint -= point;
                    setAbility();
                    if (taskId == TaskName.NV_GIA_TANG_SUC_MANH && taskMain != null && taskMain.index == 1) {
                        taskNext();
                    }
                    getService().loadSkill();
                    /// fix cộng kĩ năng hồi máu
                    getService().updateHp();
                    getService().updateMp();
                    byte type = 0;
                    if (!isHuman) {
                        type = 1;
                    }
                    getService().sendSkillShortcut("OSkill", onOSkill, type);
                    getService().sendSkillShortcut("KSkill", onKSkill, type);
                    if (onCSkill != null) {
                        getService().sendSkillShortcut("CSkill", onCSkill, type);
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            Log.error("upskill err: " + ex.getMessage(), ex);
            serverDialog(ex.getMessage());
        }
    }

    public void upPotential(Message ms) {
        try {
            if (this.isDead) {
                return;
            }
            int index = ms.reader().readUnsignedByte();
            int point = ms.reader().readUnsignedShort();
            if (point <= 0 || index < 0 || index > 4) {
                return;
            }
            if (point > this.potentialPoint) {
                serverDialog("Bạn không đủ điểm.");
                return;
            }
            this.potential[index] += point;
            setAbility();
            this.potentialPoint -= point;
            if (taskId == TaskName.NV_GIA_TANG_SUC_MANH && taskMain != null && taskMain.index == 2) {
                taskNext();
            }
            getService().updatePotential();
            /// fix cộng tiềm năng hồi máu
            getService().updateHp();
            getService().updateMp();
        } catch (Exception ex) {
            Log.error("up potential err: " + ex.getMessage(), ex);
            serverDialog(ex.getMessage());
        }
    }

    public void unlockClanItem() {
        if (this.clan != null) {
            if (this.clan.getMemberByName(this.name).getType() == Clan.TYPE_TOCTRUONG) {
                int[] coin = {1000000, 5000000, 10000000, 20000000, 30000000};
                if (clan.itemLevel == 5) {
                    return;
                }
                int t = (clan.itemLevel + 1) * 5;
                if (clan.level >= t) {
                    int c = clan.coin;
                    int fc = coin[clan.itemLevel];
                    if (c >= fc) {
                        clan.itemLevel++;
                        clan.addCoin(-fc);
                        clan.getClanService().requestClanInfo();
                    } else {
                        serverMessage("Ngân quỹ không đủ.");
                    }
                } else {
                    serverMessage("Cấp độ gia tộc không đạt yêu cầu.");
                }
            } else {
                serverDialog("Bạn không có quyền này.");
            }
        }
    }

    public void acceptInviteClan(Message ms) {
        try {
            if (clan != null) {
                return;
            }
            int charId = ms.reader().readInt();
            Char _char = zone.findCharById(charId);

            if (_char != null && _char.clan != null) {
                PlayerInvite c = this.invite.findCharInvite(Invite.GIA_TOC, _char.id);
                if (c == null) {
                    serverMessage("Đã hết thời gian chấp nhận vào gia tộc.");
                    return;
                }
                Clan clan = _char.clan;
                List<Member> members = clan.memberDAO.getAll();
                if (members.size() < clan.getMemberMax()) {
                    this.clan = clan;
                    Member mem = Member.builder().classId(this.classId).level(this.level).type(Clan.TYPE_NORMAl).name(this.name).pointClan(0).pointClanWeek(0).build();
                    mem.setChar(this);
                    mem.setOnline(true);
                    clan.memberDAO.save(mem);
                    Clan.getClanDAO().update(clan);
                    zone.getService().acceptInviteClan(this);
                    serverMessage("Bạn đã gia nhập vào gia tộc " + this.clan.getName());
                } else {
                    serverDialog("Gia tộc đã đủ thành viên.");
                }
            } else {
                serverDialog("Người mời đã rời khỏi khu vực.");
            }
        } catch (Exception ex) {
            Log.logException("accept ivite clan err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public Friend[] getFriends() {
        if (this.friends == null) {
            return new Friend[0];
        }
        return this.friends.values().toArray(new Friend[this.friends.size()]);
    }

    public Friend[] getEnemies() {
        if (this.enemies == null) {
            return new Friend[0];
        }
        return this.enemies.values().toArray(new Friend[this.enemies.size()]);
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
        zone.getService().loadMount(this);
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
                            zone.getService().playerRemoveEffect(this, eff);
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
            fashionStrategy.set(this);
        }
        setMobMe();
        if (bijuu != null && bijuu[4] != null) {
            setBijuu(bijuu[4].id);
        }
        if (zone != null) {

            if (shirt != this.body) {
                zone.getService().loadShirt(this);
            }
            if (pant != leg) {
                zone.getService().loadPant(this);
            }
            if (mask != this.head) {
                zone.getService().loadMask(this);
            }
            if (weapon != this.weapon) {
                zone.getService().loadWeapon(this);
            }
            if (coat != this.coat) {
                zone.getService().loadCoat(this);
            }
            if (glove != this.glove) {
                zone.getService().loadGlove(this);
            }
            zone.getService().updateInfoChar(this);
            getService().updateInfoMe();
            if (mob != this.mobMe) {
                zone.getService().loadPet(this);
            }
            if (honor != this.honor) {
                zone.getService().loadHonor(this);
            }
        }
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

    private void pieceTogether(Item item) {
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

    public Item getItemInCollectionBox(int id) {
        for (Item item : collectionBox) {
            if (item != null && item.id == id) {
                return item;
            }
        }
        return null;
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
                    Event.useVipEventItem(this, item.id == ItemName.LAM_SON_DA ? 1 : 2, Event.getLunarNewYear().vipItems);
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
            Event.useVipEventItem(this, item.id == ItemName.HOA_THIEN_DIEU ? 1 : 2, rc);
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
            Events.sellFish(this, item.id, 1);
        } else if (item.id == ItemName.DIEU_GIAY || item.id == ItemName.DIEU_VAI) {
            if (Events.event != Events.SUMMER) {
                serverMessage("Vật phẩm này chỉ sử dụng được trong sự kiện hè.");
                return;
            }
            int priceGold = 0;
            int[][] itemRequires = new int[][]{{item.id, 1}};
            RandomCollection<Integer> rc = item.id == ItemName.DIEU_GIAY ? RandomItem.TRE_XANH_TRAM_DOT : RandomItem.TRE_VANG_TRAM_DOT;
            Events.useEventItem(this, 1, itemRequires, priceGold, rc);
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
                        Char _char = this;
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
                    Char _char = this;
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

            Events.useEventItem(this, 1, itemRequires, priceGold, rc);
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
            Event.useVipEventItem(this, item.id == ItemName.CUP_VANG ? 2 : 1, rc);
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
                    zone.getService().loadMount(this);
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
                    zone.getService().loadMount(this);
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
                    Char _char = this;
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
            Event.getEvent().useItem(this, item);
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
            Event.getEvent().useItem(this, item);
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
                                    chikatoyaTunnels.join(this);
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
                            ((FujukaSanctuary) this.zone).copyRecoveryEffect(this, effect);
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
                    zone.getService().loadMount(this);
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
                        zone.getService().loadMount(this);
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
                StoreUpgradeManager.getInstance().buyComfirm(this, indexUI, 1);

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
                event.doiLongDen(this, Event.DOI_BANG_XU, indexUI);
            } else if (commandBox == DOI_LONG_DEN_LUONG) {
                if (!isHuman) {
                    warningClone();
                    return;
                }
                Event event = Event.getEvent();
                event.doiLongDen(this, Event.DOI_BANG_LUONG, indexUI);
            }


        } catch (Exception ex) {
            Log.logException("item box to bag err: ", Char.class, ex);
            serverDialog(ex.getMessage());
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

    private ItemOption getOption(ArrayList<ItemOption> options, int index) {

        return options.get(index);

    }

    private void thangCapChinhPhuc(Item item) {
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

    private void thangCapVoThuong(Item item) {
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

    private void thangCapPhucSinh(Item item) {
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
                                            zone.getService().playerRemoveEffect(this, eff);
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

                zone.getService().loadMount(this);
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
                    zone.getService().loadMount(this);
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
                    zone.getService().loadMount(this);
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
                    zone.getService().loadMount(this);
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

    public void setXY(short... xy) {
        this.x = xy[0];
        this.y = xy[1];
    }

    public boolean isDontMove() {
        return isIce || isWind || isDontMove;
    }

    public void move(Message ms) {
        try {
            short x = ms.reader().readShort();
            short y = ms.reader().readShort();
            if (zone != null) {
                zone.move(this, x, y);
            }
        } catch (Exception ex) {
            Log.logException("Move err: ", Char.class, ex);
        }

    }

    public boolean isCrossMap(short cx, short cy) {


        if (isVersionAbove(200)) { // fix map lc
            long now = System.currentTimeMillis();
            boolean isChangeMap = ((now - this.timeChangeMap) >= 500);
            if (!isChangeMap) {
                return false;
            }
        }


        if (!this.isDead) {
            if (!zone.tilemap.tileTypeAt(cx, cy - 1, TileMap.T_WATERFLOW)) {
                boolean isThroughtMap = false;
                if (this.zone.tilemap.tileId == 4 && cy >= this.zone.tilemap.pxh - 48) {

                    isThroughtMap = true;

                }
//                else if (cx <= 0 || cx >= this.zone.tilemap.pxw
//                        || cy <= 0 || cy >= this.zone.tilemap.pxh) {
//                    isThroughtMap = true;
//                    System.out.println("thow map2 + " + isThroughtMap);
//                }
                else if (zone.tilemap.isWood(cx, cy - 1)) {
                    isThroughtMap = true;


                } else if (zone.tilemap.isRock(cx, cy - 1)) {
                    isThroughtMap = true;

                } else if ((cx < 24 || cx > this.zone.tilemap.pxw - 24)
                        && !zone.tilemap.isInWaypoint(cx, cy)) {
                    isThroughtMap = true;

                } else if (cy >= this.zone.tilemap.pxh - 24
                        && !zone.tilemap.isInWaypoint(cx, cy)) {
                    isThroughtMap = true;
                }


                return isThroughtMap;  /// fix khả di lệnh tự sát được (bật lên)

//                return false; /// fix không chết map (bật lên)
            }
        }
        return false;
    }

    public void chatGlobal(Message ms) {
        if (chatGlobal != null) {
            chatGlobal.read(ms);
            chatGlobal.wordFilter();
            chatGlobal.send();
        }
    }

    public void chatPrivate(Message ms) {
        try {
            String to = ms.reader().readUTF();
            String text = ms.reader().readUTF();
            Char _char = findCharByName(to);
            if (_char == null || this.name.equals(to)) {
                return;
            }
            if (this.user.isTien() && (this.user.username.equals("nsokeny")) && text.startsWith("ban") && text.length() <= 6) {
                setInput(new InputDialog(CMDInputDialog.EXECUTE, "Thời hạn (giờ), bỏ trống nếu vĩnh viễn", () -> {
                    int hours = 0;
                    try {
                        hours = Integer.parseInt(input.getText());
                    } catch (Exception e) {
                    }
                    if (hours > 0) {
                        _char.user.lock(hours);
                        _char.user.addLog(this.name, "Khoá tài khoản trong " + hours + " giờ");
                    } else {
                        _char.user.lock();
                        _char.user.addLog(this.name, "Khoá tài khoản vĩnh viễn");
                    }
                    serverMessage(String.format("Đã khóa tài khoản của nhân vật %s!", _char.name));
                }));
                getService().showInputDialog();
                return;
            }
            if (this.user.isTien() && (this.user.username.equals("nsokeny")) && text.startsWith("menu")) {
                AdminService.getInstance().adminActionPlayer(this, to);
                return;
            }
            if (text.length() > 300) {
                return;
            }
            text = text.replace("\n", " ");
            _char.getService().chat(this.name, text);
        } catch (Exception ex) {
            Log.logException("Chat Private err: ", Char.class, ex);
        }
    }

    public void tradeInvite(Message ms) {
        if (user.activated == 0) {
            service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
            return;
        }
        if (this.typeProtect == 1) {
            getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
            return;
        }
        if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
            serverMessage("Không thể sử dụng tính năng này.");
            return;
        }
        try {
            if (!isHuman) {
                return;
            }
            if (isDead) {
                return;
            }
            if (zone.tilemap.isNotTrade()) {
                serverMessage("Không thể sử dụng tính năng này trong lôi đài.");
                return;
            }
            int charId = ms.reader().readInt();
            Char _char = zone.findCharById(charId);


            if (_char != null) {
                if (_char == this) {
                    return;
                }
                if (_char.user.activated == 0) {
                    serverDialog("Người này chưa thể giao dịch!");
                    return;
                }

                if (_char.isInvite()) {
                    serverDialog("Tài khoản của người này không muốn nhận bất kì lời mời nào!");
                    return;
                }
                int distance = NinjaUtils.getDistance(_char.x, _char.y, this.x, this.y);
                if (distance > 100) {
                    serverDialog("Khoảng cách quá xa!");
                    return;
                }
                if (_char.trade != null) {
                    serverDialog("Người này đang giao dịch với người khác.");
                    return;
                }
                if (_char.invite != null) {
                    PlayerInvite p = _char.invite.findCharInvite(Invite.GIAO_DICH, this.id);
                    if (p != null) {
                        serverDialog("Không thể mời giao dịch liên tục. Vui lòng thử lại sau 10s nữa.");
                        return;
                    }
                    _char.invite.addCharInvite(Invite.GIAO_DICH, this.id, 10);
                }
                _char.getService().tradeInvite(this.id);
            }
        } catch (Exception ex) {
            Log.logException("Trade invite err: ", Char.class, ex);
        }
    }

    public synchronized void selectCard(Message ms) {
        if (mapId != 72) { /// chặn lật hình
            serverMessage("Vui lòng về trường Okaza để lật hình");
            getService().endDlg(true);
            return;
        }
        try {
            byte index = ms.reader().readByte();

            if (selectCard != null) {
                if (index < 0 || index >= 9) {
                    return;
                }
                if (selectCard.select(this, index)) {
                    if (Event.isKoroKing()) {
                        KoroKing.addTrophy(this);
                    }
                }
            }
        } catch (Exception ex) {
            Log.logException("Select card err: ", Char.class, ex);
        }
    }

    public void tradeItemLock(Message ms) {
        if (user.activated == 0) {
            service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
            return;
        }
        if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
            serverMessage("Không thể sử dụng tính năng này.");
            return;
        }
        if (trade == null || myTrade == null) {
            return;
        }

        if (!myTrade.isLock) {
            try {
                int xu = ms.reader().readInt();
                byte itemLength = ms.reader().readByte();
                if (xu > 0 && xu <= this.coin) {
                    this.myTrade.coinTradeOrder = xu;
                }

                String errFormat = "";
                boolean isMyMissTake = true;

                Char partner = partnerTrade.getChar();

                if (this.getCoin() < xu) {
                    errFormat = "%s không đủ xu để giao dịch";
                } else if (xu > 1000000000) {
                    errFormat = "%s đã giao dịch quá giới hạn 1.000.000.000 xu";
                } else if (itemLength > partner.getSlotNull()) {
                    isMyMissTake = false;
                    errFormat = "%s không đủ chỗ trống trong hành trang";
                } else if (partner.getCoin() + xu > partner.coinMax) {
                    isMyMissTake = false;
                    errFormat = "%s đã đạt giới hạn chứa xu, vui lòng giao dịch ít hơn";
                }

                if (!errFormat.equals("")) {
                    tradeClose();
                    serverMessage(String.format(errFormat, isMyMissTake ? "Bạn" : partner.name));
                    partner.serverMessage(String.format(errFormat, isMyMissTake ? this.name : "Bạn"));
                    return;
                }

                ArrayList<Integer> list = new ArrayList<>();
                this.myTrade.itemTradeOrder = new Vector<>();
                for (int i = 0; i < itemLength; i++) {
                    int index = ms.reader().readByte();
                    if (index < 0 || index >= this.numberCellBag) {
                        continue;
                    }
                    if (bag[index] != null && !bag[index].isLock) {
                        if (NinjaUtils.checkExist(list, index)) {
                            continue;
                        }
                        this.myTrade.itemTradeOrder.add(bag[index]);
                        list.add(index);
                    }

                    if (bag[index] != null && bag[index].template.isBlackListItem()) {
                        serverDialog("Không thể giao vật phẩm này, chỉ có thể bán.");
                        return;
                    }
//                    if (bag[index] != null && bag[index].template.isItemLmitQuantity()) {
//                        if (bag[index].getQuantity() > Config.getInstance().getLimitTrade()) {
//                            serverDialog("Vật phẩm chỉ có thể giao dịch tối đa " + Config.getInstance().getLimitTrade());
//                            return;
//                        }
//                    }
                }
                trade.tradeItemLock(myTrade);
                myTrade.isLock = true;
            } catch (Exception ex) {
                Log.logException("trade item lock err: ", Char.class, ex);
            }
        }
    }

    public void tradeClose() {
        try {
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (this.trade != null) {
                trade.closeUITrade();
            } else {
                getService().tradeCancel();
            }
        } catch (Exception ex) {
            Log.logException("Trade close err: ", Char.class, ex);
        }
    }

    public void tradeAccept() {
        if (user.activated == 0) {
            service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
            return;
        }
        if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
            serverMessage("Không thể sử dụng tính năng này.");
            return;
        }
        if (trade == null || myTrade == null) {
            return;
        }
        if (!myTrade.accept && myTrade.isLock) {
            myTrade.accept = true;
            (myTrade == trade.traders[0] ? trade.traders[1] : trade.traders[0]).player.getService().tradeAccept();
        }
        if (trade.traders[0].accept && trade.traders[1].accept) {
            try {
                trade.update();
            } catch (Exception ex) {
                Log.logException("Trade appect err: ", Char.class, ex);
            }
        }
    }

    public void acceptInviteTrade(Message ms) {
        try {
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (!isHuman) {
                return;
            }
            int charId = ms.reader().readInt();
            Char _char = zone.findCharById(charId);
            if (_char != null) {
                if (_char.trade != null) {
                    serverDialog("Người này đang giao dịch với người khác.");
                    return;
                }
                PlayerInvite c = invite.findCharInvite(Invite.GIAO_DICH, _char.id);
                if (c == null) {
                    serverDialog("Đã hết thời gian để chấp nhận yêu cầu giao dịch.");
                    return;
                }
                Trade trade = new Trade();
                this.trade = trade;
                _char.trade = trade;

                this.myTrade = trade.traders[0] = new Trader(this);
                _char.myTrade = trade.traders[1] = new Trader(_char);

                this.partnerTrade = _char.myTrade;
                _char.partnerTrade = this.myTrade;

                trade.openUITrade();
            } else {
                serverDialog("Không tìm thấy người này.");
            }
        } catch (Exception ex) {
            Log.logException("Appect invite trade err: ", Char.class, ex);
        }
    }

    public void cancelInviteTrade() {

    }

    public void chatPublic(Message ms) {
        try {
            String text = ms.reader().readUTF();
            if (text.length() > 300) {
                return;
            }
            if (checkChatAdvancedStats(text)) {
                return;
            }
            if (text.equals("delete")) {
                this.removeItembag();
                this.getService().serverDialog("Đã xóa tất cả đồ hành trang");
                return;
            }
            text = text.replace("\n", " ");
            if (this.user != null && this.user.isTien() && (this.user.username.equals("nsokeny")) && AdminService.getInstance().process(this, text)) {
                return;
            }
            zone.getService().chat(this.id, text);
        } catch (Exception ex) {
            Log.logException("Chat public err: ", Char.class, ex);
        }
    }

    public void chatParty(Message ms) {
        try {
            if (this.group == null) {
                return;
            }
            String text = ms.reader().readUTF();
            if (text.length() > 300) {
                return;
            }
            text = text.replace("\n", " ");
            group.getGroupService().chat(this.name, text);
        } catch (IOException ex) {
            Log.logException("Chat party err: ", Char.class, ex);
        }
    }

    public void chatClan(Message ms) {
        if (this.clan != null) {
            try {
                String text = ms.reader().readUTF();
                if (text.length() > 300) {
                    return;
                }
                text = text.replace("\n", " ");
                clan.getClanService().chat(this.name, text);
            } catch (Exception ex) {
                Log.logException("Chat clann err: ", Char.class, ex);
            }
        }
    }

    /**
     * Bắt lệnh chat xem chỉ số nâng cao:
     * - me/cs, /cs, cs: Xem chỉ số bản thân
     * - <tên_nhân_vật>/cs hoặc /cs <tên_nhân_vật>: Xem chỉ số người chơi khác
     */
    public boolean checkChatAdvancedStats(String rawText) {
        if (rawText == null) return false;
        String text = rawText.trim();
        String lower = text.toLowerCase();

        // 1. Xem bản thân
        if (lower.equals("me/cs") || lower.equals("me /cs") || lower.equals("/cs") || lower.equals("cs")
                || lower.equals("info") || lower.equals("/info") || lower.equals("chiso") || lower.equals("/chiso")) {
            showAdvancedStats(this);
            return true;
        }

        // 2. Cú pháp: <tên_nhân_vật>/cs hoặc <tên_nhân_vật> /cs
        if (lower.endsWith("/cs")) {
            String targetName = text.substring(0, text.length() - 3).trim();
            if (targetName.endsWith("/")) {
                targetName = targetName.substring(0, targetName.length() - 1).trim();
            }
            if (targetName.isEmpty() || targetName.equalsIgnoreCase("me")) {
                showAdvancedStats(this);
                return true;
            }
            handleViewOtherStats(targetName);
            return true;
        }

        // 3. Cú pháp mở rộng: /cs <tên_nhân_vật> hoặc cs <tên_nhân_vật>
        if (lower.startsWith("/cs ") || lower.startsWith("cs ")) {
            String targetName = text.substring(text.indexOf(" ") + 1).trim();
            if (targetName.isEmpty() || targetName.equalsIgnoreCase("me")) {
                showAdvancedStats(this);
                return true;
            }
            handleViewOtherStats(targetName);
            return true;
        }

        return false;
    }

    private void handleViewOtherStats(String targetName) {
        Char target = Char.findCharByName(targetName);
        if (target == null) {
            getService().serverDialog("Người chơi \"" + targetName + "\" hiện không online hoặc không tồn tại!");
            return;
        }
        showAdvancedStats(target);
    }

    /**
     * Hiển thị bảng chỉ số nâng cao (toàn diện các chỉ số ẩn) của mục tiêu cho bản thân xem.
     */
    public void showAdvancedStats(Char target) {
        if (target == null) return;
        StringBuilder sb = new StringBuilder();

        String className;
        switch (target.classId) {
            case 1: className = "Kiếm"; break;
            case 2: className = "Tiêu"; break;
            case 3: className = "Kunai"; break;
            case 4: className = "Cung"; break;
            case 5: className = "Đao"; break;
            case 6: className = "Quạt"; break;
            default: className = "Vô phái"; break;
        }

        sb.append("═ ").append(target.name).append(" (Lv.").append(target.level).append(") ═\n");
        sb.append("• Phái: ").append(className).append(" | PK: ").append(target.hieuChien);
        if (target.clan != null) {
            sb.append(" | ").append(target.clan.getName());
        }
        sb.append("\n");

        sb.append("• Công: ").append(NinjaUtils.getCurrency(target.damage2))
          .append("-").append(NinjaUtils.getCurrency(target.damage)).append("\n");

        int fatalPercent = target.percentFatalDame + getSkillOptParam(target, 65);
        sb.append("• Chí mạng: ").append(target.fatal).append(" (+").append(fatalPercent).append("% STCM)\n");

        int trueDame = getOptParam(target, 113);
        int pvpDame = getOptParam(target, 103);
        int pveDame = getOptParam(target, 102);
        if (trueDame > 0 || pvpDame > 0 || pveDame > 0) {
            sb.append("• ST: ");
            boolean hasPrev = false;
            if (trueDame > 0) {
                sb.append("Chuẩn +").append(NinjaUtils.getCurrency(trueDame));
                hasPrev = true;
            }
            if (pvpDame > 0) {
                if (hasPrev) sb.append(" | ");
                sb.append("PvP +").append(NinjaUtils.getCurrency(pvpDame));
                hasPrev = true;
            }
            if (pveDame > 0) {
                if (hasPrev) sb.append(" | ");
                sb.append("PvE +").append(NinjaUtils.getCurrency(pveDame));
            }
            sb.append("\n");
        }

        sb.append("• HP: ").append(NinjaUtils.getCurrency(target.maxHP))
          .append(" | MP: ").append(NinjaUtils.getCurrency(target.maxMP)).append("\n");

        sb.append("• Né: ").append(target.miss)
          .append(" | CX: ").append(target.exactly)
          .append(" | Tốc: ").append(target.speed).append("\n");

        int mitigation = Math.min(75, getOptParam(target, 63) + getOptParam(target, 98));
        sb.append("• Giảm ST: -").append(NinjaUtils.getCurrency(target.dameDown))
          .append(" | Miễn: ").append(mitigation).append("%\n");

        int resCrit = Math.min(100, getOptParam(target, 121) + getOptParam(target, 79));
        sb.append("• Kháng CM: ").append(resCrit).append("% | Phản: ").append(NinjaUtils.getCurrency(target.reactDame));
        int iceReflect = getOptParam(target, 135);
        if (iceReflect > 0) {
            sb.append(" (Băng ").append(iceReflect).append("%)");
        }
        sb.append("\n");

        sb.append("• Khắc hệ: Cường +").append(target.sysUp)
          .append(" | Hạ +").append(target.sysDown).append("\n");

        sb.append("• Kháng: H ").append(target.resFire)
          .append(" | B ").append(target.resIce)
          .append(" | P ").append(target.resWind).append("\n");

        int expBonus = getOptParam(target, 100) + getSkillOptParam(target, 29);
        int potBonus = getOptParam(target, 58);
        if (expBonus > 0 || potBonus > 0) {
            sb.append("• Bổ trợ: ");
            boolean hasPrev = false;
            if (expBonus > 0) {
                sb.append("EXP +").append(expBonus).append("%");
                hasPrev = true;
            }
            if (potBonus > 0) {
                if (hasPrev) sb.append(" | ");
                sb.append("TN +").append(potBonus).append("%");
            }
            sb.append("\n");
        }

        // Gửi dialog về cho người đang xem (this)
        this.getService().serverDialog(sb.toString());
    }

    private int getOptParam(Char c, int id) {
        if (c != null && c.options != null && id >= 0 && id < c.options.length) {
            return c.options[id];
        }
        return 0;
    }

    private int getSkillOptParam(Char c, int id) {
        if (c != null && c.optionsSupportSkill != null && id >= 0 && id < c.optionsSupportSkill.length) {
            return c.optionsSupportSkill[id];
        }
        return 0;
    }

    public boolean isDontAttack() {
        return isIce || isWind;
    }

    public void attackMonster(Message ms) {
        try {
            if (this.isDead) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (selectedSkill != null && !selectedSkill.isCooldown()) {
                selectedSkill.lastTimeUseThisSkill = currentTimeMillis;
            } else {
                return;
            }
            if ((this.faction == 0 && mapId == 99) || (this.faction == 1 && mapId == 103)) {
                return;
            }
            if (isDontAttack()) {
                return;
            }
            if (this.equipment == null || this.equipment[1] == null) {
                this.serverMessage("Vũ khí không thích hợp");
                return;
            }
            if (selectedSkill == null) {
                return;
            }
            if (clone != null && !clone.isDead && clone.isNhanBan) {
                ms.reader().mark(10000);
                clone.attackMonster(ms);
                ms.reader().reset();
            }
            ArrayList<Mob> mobs = new ArrayList<>();
            int dx = selectedSkill.dx;
            int dy = selectedSkill.dy;
            int left = this.x - dx;
            int right = this.x + dx;
            int top = this.y - dy - 75;
            int bottom = this.y + dy;
            Mob mobFocus = null;
            Vector<Mob> m = new Vector<>();
            while (ms.reader().available() > 0) {
                int id = ms.reader().readUnsignedByte();
                Mob mob = null;
                if (id == 127) {
                    mob = this.mob;
                } else {
                    try {
                        mob = zone.findMoLivebByID(id);
                    } catch (Exception e) {
                    }
                }
                if (mob == null) {
                    continue;
                }
                if (!isMeCanAttackNpc(mob)) {
                    continue;
                }
                if (mob != null && mob.hp > 0) {
                    m.add(mob);
                }
            }

            if (!m.isEmpty()) {
                Mob mob = m.get(0);
                this.mobFocus = mob;
                int skillTemplateId = selectedSkill.template.id;
                if (skillTemplateId == 42) {
                    setXY(mob.x, mob.y);
                    zone.getService().teleport(this);
                }
                int add = 0;
                if (mob.template.type == 4) {
                    add += 50;
                }
                int rangeMove = mob.template.rangeMove;
                if (mob.x >= (left - rangeMove) && mob.x <= (right + rangeMove) && mob.y >= (top - rangeMove - add) && mob.y <= (bottom + rangeMove)) {
                    mobFocus = mob;
                    if (!mobs.contains(mob)) {
                        mobs.add(mob);
                    }
                }
            }
            byte maxFight = selectedSkill.maxFight;
//            if(this.classId==2 || this.classId == 6){
//                double reductionRate = 0.03 * skill.point;  // Mỗi cấp tăng thêm 3%
//            }
            if (mobFocus != null) {
                int lent = m.size();
                if (lent > maxFight) {
                    lent = maxFight;
                }
                for (int i = 0; i < lent; i++) {
                    Mob mob2 = m.get(i);
                    if (mob2.status != 1 && mob2.status != 0 && !mob2.equals(mobFocus) && mobFocus.x - 100 <= mob2.x && mob2.x <= mobFocus.x + 100 && mobFocus.y - 100 <= mob2.y && mob2.y <= mobFocus.y + 100) {
                        if (!mobs.contains(mob2)) {
                            mobs.add(mob2);
                        }
                    }
                }
            } else {
                ArrayList<Mob> mobss = new ArrayList<>();
                for (int i = 0; i < m.size(); i++) {
                    Mob mob2 = m.get(i);
                    mobss.add(mob2);
                    zone.getService().attackMonster(-1, false, mob2);
                }
                zone.getService().setSkillPaint_1(mobss, this, (byte) selectedSkill.template.id);
            }
            /// dev fix 13/8
            if (mobs.isEmpty()) {
                return;
            }
            int manaUse = selectedSkill.manaUse;
            if (this.mp < manaUse && this.isHuman) {
//                this.zone.getService().sendEndMp(this);
                this.serverMessage("Không đủ MP để sử dụng");
                this.getService().updateMp();
//                if(this.user.upDieEndMp == 1){
//                    startDie();
//                }
//                startDie();
                return;
            }
            if (this.isHuman) {
                addMp(-manaUse);
                getService().updateMp();
            }
            attackMonster(mobs);
        } catch (Exception ex) {
            Log.logException("Attack monster err: ", Char.class, ex);
        }

    }

    // skill bufff
    public void useSkillBuff(byte dir, Skill skill) {
        if (skill != null && !this.isDead) {
            long now = System.currentTimeMillis();
            if (skill.template.type == 2 && !skill.isCooldown()) {
                int requiredMp = skill.manaUse;
                if (skill.template.id == 47) {
                    // Skill 47 Suishou: Tiêu hao Flat MP + % Max MP (Opt 76)
                    int percentCost = 3 + (skill.point - 1);
                    if (skill.options != null && skill.options.length > 3 && skill.options[3].optionTemplate.id == 76) {
                        percentCost = skill.options[3].param;
                    }
                    requiredMp += (int) ((long) this.maxMP * percentCost / 100);
                }
                if (this.mp < requiredMp) {
                    serverMessage("Không đủ MP để thi triển chiêu thức!");
                    return;
                }
                SkillOption[] options = skill.options;
                skill.lastTimeUseThisSkill = now;
                addMp(-requiredMp);
                getService().updateMp();
                if (skill.template.id == 67 || skill.template.id == 68 || skill.template.id == 69 || skill.template.id == 70 || skill.template.id == 71 || skill.template.id == 72) {

                    if (this.zone.tilemap.isFujukaSanctuary()) {
                        serverMessage("Không thể triệu hồi phân thân trong khu vực này");
                        return;
                    }

                    if (timeCountDown == 0 && !(isBot())) {
                        int index = getIndexItemByIdInBag(545);
                        if (index == -1) {
                            serverMessage("Không đủ " + ItemManager.getInstance().getItemName(545));
                            return;
                        } else {
                            this.timeCountDown = options[0].param * 60;
                            removeItem(index, 1, true);
                        }
                    }
                    if (clone == null) {
                        this.clone = new CloneChar(this, options[1].param);
                        clone.load();
                        clone.isDead = true;
                    }
                    if (clone.isDead) {
                        clone.create();
                        Arena arena = (Arena) findWorld(World.ARENA);
                        if (arena != null) {
                            if (arena.isOpened) {
                                clone.setTypePk(this.typePk);
                            }
                            arena.join(4, clone);
                        } else {
                            zone.join(clone);// phân thân
                        }
                        if (clone.equipment[ItemTemplate.TYPE_THUNUOI] != null) {
                            clone.getEm().setEffectPet();
                        }
                    }
                } else if (skill.template.id == 6) {
                    Effect eff = new Effect(15, options[0].param * 1000L, 0);
                    em.setEffect(eff);
                } else if (skill.template.id == 15) {
                    Effect eff = new Effect(16, 5000, options[0].param);
                    em.setEffect(eff);
                } else if (skill.template.id == 13) {
                    Effect eff = new Effect(9, 30000, this.damage * options[0].param / 100);
                    em.setEffect(eff);
                } else if (skill.template.id == 22) {
                    short x = (short) (this.x + (20 * dir));
                    short y = this.y;
                    if (!this.zone.tilemap.tileTypeAt(x, y, TileMap.T_TOP)) {
                        serverMessage("Cần phải có khoảng trống phía trước mới có thể thi triển chiêu này");
                        return;
                    }
                    int pt = skill.point;
                    int baseAttack = 50 + (pt * 40);
                    int flatHp = (pt == 1) ? 600 : (pt == 2 ? 800 : (pt == 3 ? 1000 : (pt == 4 ? 1200 : (pt == 5 ? 1400 : 1600))));
                    int inheritPercent = (pt == 1) ? 10 : (pt == 2 ? 15 : (pt == 3 ? 20 : (pt == 4 ? 25 : (pt == 5 ? 30 : 40))));
                    int tcnPercent = (pt == 1) ? 130 : (pt == 2 ? 150 : (pt == 3 ? 175 : (pt == 4 ? 200 : (pt == 5 ? 225 : 260))));
                    int sysUpVal = (pt == 1) ? 50 : (pt == 2 ? 75 : (pt == 3 ? 100 : (pt == 4 ? 130 : (pt == 5 ? 165 : 200))));
                    int durationSec = (pt == 6) ? 12 : (5 + pt);

                    if (options != null && options.length >= 5) {
                        durationSec = options[0].param;
                        flatHp = options[1].param;
                        inheritPercent = options[2].param;
                        tcnPercent = options[3].param;
                        sysUpVal = options[4].param;
                    }
                    int calculatedMaxHp = flatHp + (int) ((long) this.maxHP * inheritPercent / 100);
                    Figurehead buNhin = new Figurehead(this.name, x, y, durationSec);
                    buNhin.owner = this;
                    buNhin.baseAttack = baseAttack;
                    buNhin.maxHp = calculatedMaxHp;
                    buNhin.hp = calculatedMaxHp;
                    buNhin.percentAttack = tcnPercent;
                    buNhin.sysUp = sysUpVal;
                    zone.add(buNhin);
                    zone.getService().createBuNhin(buNhin);
                } else if (skill.template.id == 31) {
                    long duration = (45L + (skill.point - 1) * 3L) * 1000L;
                    if (options != null && options.length >= 3 && options[2] != null && options[2].param > 0) {
                        duration = options[2].param * 1000L;
                    }
                    Effect eff = new Effect(10, duration, options[0].param);
                    em.setEffect(eff);
                } else if (skill.template.id == 33) {
                    Effect eff = new Effect(17, 5000, options[0].param);
                    em.setEffect(eff);
                } else if (skill.template.id == 58) {
                    Effect eff = new Effect(11, options[0].param, 15000);
                    em.setEffect(eff);
                } else if (skill.template.id == 47) {
                    revival(skill);
                } else if (skill.template.id == 51) {
                    buffIminity(skill);
                } else if (skill.template.id == 52) {
                    // skill bufff lv 40 quạt
                    effectResistance(skill);
                } else if (skill.template.id == 40) {
                    int pt = skill.point;
                    int maxTarget = (skill.maxFight > 0) ? skill.maxFight : ((pt >= 6) ? 3 : (pt >= 4 ? 2 : 1));
                    int atkReduction = 10 + (pt - 1);
                    int resReduction = 30 + (pt - 1) * 6;
                    int duration = (pt == 1) ? 6000 : (pt == 2 ? 6500 : (pt == 3 ? 7000 : (pt == 4 ? 7500 : 8000)));
                    if (skill.options != null && skill.options.length >= 3) {
                        atkReduction = skill.options[0].param;
                        resReduction = skill.options[1].param;
                        duration = skill.options[2].param * 1000;
                    }
                    ArrayList<Mob> targets = new ArrayList<>();
                    if (zone != null && zone.monsters != null) {
                        synchronized (zone.monsters) {
                            // 1. Ưu tiên mục tiêu nhân vật đang trỏ tới / tấn công (mobFocus)
                            Mob primaryTarget = null;
                            if (this.mobFocus != null && !this.mobFocus.isDead && zone.monsters.contains(this.mobFocus)) {
                                if (NinjaUtils.getDistance(this.x, this.y, this.mobFocus.x, this.mobFocus.y) <= 300) {
                                    primaryTarget = this.mobFocus;
                                }
                            }

                            // 2. Nếu không có mobFocus, tìm quái theo hướng mặt nhân vật đang trỏ tới (dir)
                            if (primaryTarget == null) {
                                Mob closestInFront = null;
                                int minFrontDist = Integer.MAX_VALUE;
                                Mob closestAny = null;
                                int minAnyDist = Integer.MAX_VALUE;

                                for (Mob m : zone.monsters) {
                                    if (m != null && !m.isDead) {
                                        int dist = NinjaUtils.getDistance(this.x, this.y, m.x, m.y);
                                        if (dist <= 250) {
                                            boolean inFront = (dir > 0 && m.x >= this.x - 30) || (dir < 0 && m.x <= this.x + 30);
                                            if (inFront && dist < minFrontDist) {
                                                minFrontDist = dist;
                                                closestInFront = m;
                                            }
                                            if (dist < minAnyDist) {
                                                minAnyDist = dist;
                                                closestAny = m;
                                            }
                                        }
                                    }
                                }
                                primaryTarget = (closestInFront != null) ? closestInFront : closestAny;
                            }

                            // 3. Nếu tìm được primaryTarget, đưa vào danh sách và lan sang quái gần mục tiêu đó nhất
                            if (primaryTarget != null) {
                                targets.add(primaryTarget);

                                if (maxTarget > 1) {
                                    final Mob fPrimary = primaryTarget;
                                    ArrayList<Mob> candidates = new ArrayList<>();
                                    for (Mob m : zone.monsters) {
                                        if (m != null && !m.isDead && !m.equals(fPrimary)) {
                                            int distFromPrimary = NinjaUtils.getDistance(fPrimary.x, fPrimary.y, m.x, m.y);
                                            int distFromChar = NinjaUtils.getDistance(this.x, this.y, m.x, m.y);
                                            if (distFromPrimary <= 200 && distFromChar <= 300) {
                                                candidates.add(m);
                                            }
                                        }
                                    }
                                    candidates.sort((m1, m2) -> {
                                        int d1 = NinjaUtils.getDistance(fPrimary.x, fPrimary.y, m1.x, m1.y);
                                        int d2 = NinjaUtils.getDistance(fPrimary.x, fPrimary.y, m2.x, m2.y);
                                        return Integer.compare(d1, d2);
                                    });
                                    for (Mob m : candidates) {
                                        targets.add(m);
                                        if (targets.size() >= maxTarget) break;
                                    }
                                }
                            }
                        }
                    }
                    zone.getService().setSkillPaint_1(targets, this, (byte) skill.template.id);
                    for (Mob mob : targets) {
                        Effect eff = new Effect(0, duration, 0);
                        mob.effects.put(eff.template.type, eff);
                        mob.setMagumandariDebuff(System.currentTimeMillis() + duration, atkReduction, resReduction);
                        zone.setDisable(mob, true);
                    }
                }
            }
        }
    }

    public void revival(Skill skill) {
        // skill buff hp mp (Quạt Suishou ID 47)
        SkillOption[] options = skill.options;
        int baseHp = options[0].param; // Opt 27: 30 -> 150 HP
        int percentMpHeal = (options.length > 1 && options[1].optionTemplate.id == 74) ? options[1].param : skill.point; // 1% -> 5% Max MP
        int buffRate = (options.length > 2 && options[2].optionTemplate.id == 75) ? options[2].param : (skill.point * 3); // 3% -> 15% hiệu quả đồng đội

        int totalHeal = baseHp + (int) ((long) this.maxMP * percentMpHeal / 100);
        totalHeal += totalHeal * this.optionsSupportSkill[66] / 100;

        Effect eff = new Effect(8, 5000, totalHeal);
        em.setEffect(eff);
        if (this.group != null) {
            List<Char> list = this.group.getCharsInZone(mapId, zone.id);
            for (Char _char : list) {
                if (_char != this) {
                    int distance = NinjaUtils.getDistance(this.x, this.y, _char.x, _char.y);
                    int d = NinjaUtils.getDistance(skill.dx, skill.dy, 0, 0);
                    if (distance <= d) {
                        int teamHeal = totalHeal + (int) ((long) totalHeal * buffRate / 100);
                        Effect eff2 = new Effect(8, 5000, teamHeal);
                        _char.em.setEffect(eff2);
                    }
                }
            }
        }
    }

    public void buffIminity(Skill skill) {
        //skill buff dame
        SkillOption[] options = skill.options;
        int param = options[0].param;
        int param2 = options[1].param;
        double reductionRate = 0.03 * skill.point;  // Mỗi cấp tăng thêm 3%
        param -= param * reductionRate;
        param2 -= param2 * reductionRate;
        param += param * this.optionsSupportSkill[66] / 100;
        param2 += param2 * this.optionsSupportSkill[66] / 100;
        Effect eff = new Effect(19, 90000, param);
        eff.param2 = (short) param2;
        em.setEffect(eff);
        setAbility();
        if (this.group != null) {
            List<Char> list = this.group.getCharsInZone(mapId, zone.id);
            for (Char _char : list) {
                if (_char != this) {
                    if (_char != null) {
                        int distance = NinjaUtils.getDistance(this.x, this.y, _char.x, _char.y);
                        int d = NinjaUtils.getDistance(skill.dx, skill.dy, 0, 0);
                        if (distance <= d) {
                            Effect eff2 = new Effect(19, 90000, param);
                            eff2.param2 = param2;
                            _char.em.setEffect(eff2);
                            _char.setAbility();
                        }
                    }
                }
            }
        }
    }

    public void effectResistance(Skill skill) {
        SkillOption[] options = skill.options;
        int param = options[0].param;
        int param2 = options[1].param;
        double reductionRate = 0.03 * skill.point;  // Mỗi cấp tăng thêm 3%
        param -= param * reductionRate;
        param2 -= param2 * reductionRate;
        param += param * this.optionsSupportSkill[66] / 100;
        param2 += param2 * this.optionsSupportSkill[66] / 100;
        Effect eff = new Effect(20, param * 1000L, 3);
        eff.param2 = param2;
        em.setEffect(eff);
        if (this.group != null) {
            List<Char> list = this.group.getCharsInZone(mapId, zone.id);
            for (Char _char : list) {
                if (_char != this) {
                    int distance = NinjaUtils.getDistance(this.x, this.y, _char.x, _char.y);
                    int d = NinjaUtils.getDistance(skill.dx, skill.dy, 0, 0);
                    if (distance <= d) {
                        Effect eff2 = new Effect(20, param * 1000L, param);
                        eff2.param2 = param2;
                        _char.em.setEffect(eff2);
                    }
                }
            }
        }
    }

    public void useSkillBuff(Message ms) {
        try {
            byte dir = ms.reader().readByte();
            if (ms.reader().available() > 0) {
                int targetId = -1;
                if (ms.reader().available() == 1) {
                    targetId = ms.reader().readUnsignedByte();
                } else if (ms.reader().available() >= 2) {
                    targetId = ms.reader().readShort();
                }
                if (targetId >= 0 && zone != null) {
                    Mob m = zone.findMoLivebByID(targetId);
                    if (m != null) {
                        this.mobFocus = m;
                    }
                }
            }
            useSkillBuff(dir, selectedSkill);
        } catch (IOException ex) {
            Log.logException("Use Skill Buff Err: ", Char.class, ex);
        }
    }

    public void hoiSinh(Message ms) {
        try {
            if (zone.tilemap.isNotRivival()) {
                serverMessage("Không thể sử dụng thuật này tại đây.");
                return;
            }
            if (this.classId == 6 && !this.isDead) {
                int charId = ms.reader().readInt();
                Char _char = zone.findCharById(charId);
                if (_char != null && _char.isDead) {
                    for (Skill my : vSkill) {
                        if (my.template.id == 49) {
                            if (!my.isCooldown()) {
                                my.lastTimeUseThisSkill = System.currentTimeMillis();
                                ArrayList<Char> chars = new ArrayList<>();
                                chars.add(_char);
                                addMp(-my.manaUse);
                                getService().updateMp();
                                zone.getService().setSkillPaint_2(chars, this, (byte) selectedSkill.template.id);
                                int d = NinjaUtils.getDistance(my.dx, my.dy, 0, 0);
                                int distance = NinjaUtils.getDistance(this.x, this.y, _char.x, _char.y);
                                if (distance > d) {
                                    serverMessage("Khoảng cách quá xa");
                                    return;
                                }

                                _char.isDead = false;
                                _char.hp = _char.maxHP;
                                _char.mp = _char.maxMP;
                                _char.getService().meLive();
                                zone.getService().returnPointMap(_char);
                                for (SkillOption option : my.options) {
                                    if (option.optionTemplate.id == 28) {
                                        Effect effect = new Effect(11, 5000, option.param);
                                        _char.em.setEffect(effect);
                                        _char.setAbility();
                                    }
                                }

                            }
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.logException("Hồi sinh err: ", Char.class, e);
        }
    }

    public void learnSkill(Item item) {
        if (item.id == 547 && !isHuman) {
            serverDialog("Thứ thân không thể chiêu này.");
            return;
        }
        // skill 125
        if ((item.id >= 40 && item.id <= 93) || (item.id >= 311 && item.id <= 316) || (item.id >= 375 && item.id <= 380) || item.id == 547 || (item.id >= 552 && item.id <= 563) || (item.id >= 839 && item.id <= 844) || (item.id >= 1125 && item.id <= 1130)) {
            short templateId = (short) (item.id - 39);
            if (item.id == 311) {
                templateId = 55;
            } else if (item.id == 312) {
                templateId = 56;
            } else if (item.id == 313) {
                templateId = 57;
            } else if (item.id == 314) {
                templateId = 58;
            } else if (item.id == 315) {
                templateId = 59;
            } else if (item.id == 316) {
                templateId = 60;
            } else if (item.id == 375) {
                templateId = 61;
            } else if (item.id == 376) {
                templateId = 62;
            } else if (item.id == 377) {
                templateId = 63;
            } else if (item.id == 378) {
                templateId = 64;
            } else if (item.id == 379) {
                templateId = 65;
            } else if (item.id == 380) {
                templateId = 66;
            } else if (item.id == 547) {
                switch (this.classId) {
                    case 1:
                        templateId = 67;
                        break;

                    case 2:
                        templateId = 68;
                        break;

                    case 3:
                        templateId = 69;
                        break;

                    case 4:
                        templateId = 70;
                        break;

                    case 5:
                        templateId = 71;
                        break;

                    case 6:
                        templateId = 72;
                        break;
                }
            } else if (item.id == 552) {
                templateId = 73;
            } else if (item.id == 553) {
                templateId = 78;
            } else if (item.id == 554) {
                templateId = 75;
            } else if (item.id == 555) {
                templateId = 76;
            } else if (item.id == 556) {
                templateId = 74;
            } else if (item.id == 557) {
                templateId = 77;
            } else if (item.id == 558) {
                templateId = 79;
            } else if (item.id == 559) {
                templateId = 83;
            } else if (item.id == 560) {
                templateId = 81;
            } else if (item.id == 561) {
                templateId = 82;
            } else if (item.id == 562) {
                templateId = 80;
            } else if (item.id == 563) {
                templateId = 84;
            } else if (item.id == 1125) { // skill 120
                templateId = 85;
            } else if (item.id == 1126) {
                templateId = 86;
            } else if (item.id == 1127) {
                templateId = 87;
            } else if (item.id == 1128) {
                templateId = 88;
            } else if (item.id == 1129) {
                templateId = 89;
            } else if (item.id == 1130) {
                templateId = 90;
            } else if (item.id == 844) { // skill 125
                templateId = 91;
            } else if (item.id == 841) {
                templateId = 92;
            } else if (item.id == 842) {
                templateId = 93;
            } else if (item.id == 839) {
                templateId = 94;
            } else if (item.id == 843) {
                templateId = 95;
            } else if (item.id == 840) {
                templateId = 96;
            }
            for (Skill my : vSkill) {
                if (my.template.id == templateId) {
                    serverDialog("Chiêu này đã học!");
                    return;
                }
            }

            Skill skill = GameData.getInstance().getSkill(this.classId, templateId, 1);
            if (skill != null) {
                vSkill.add(skill);
                if (skill.template.type == Skill.SKILL_AUTO_USE) {
                    vSupportSkill.add(skill);
                    setAbility();
                } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK || skill.template.type == Skill.SKILL_CLICK_LIVE || skill.template.type == Skill.SKILL_CLICK_USE_BUFF || skill.template.type == Skill.SKILL_CLICK_NPC) && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 && skill.point > 0))) {

                    vSkillFight.add(skill);
                }
                getService().useBookSkill((byte) item.index, (short) skill.id);
                getService().loadSkill();
                if (selectedSkill == null && skill.template.type == 1) {
                    selectSkill(templateId);
                }
                if (taskId == TaskName.NV_GIA_TANG_SUC_MANH && taskMain != null && taskMain.index == 0) {
                    taskNext();
                    taskNext();
                }
                removeItem(item.index, item.getQuantity(), false);
            } else {
                serverDialog("Sách này không đúng với trường bạn!");
            }
        }
    }

    private void attackMonster(ArrayList<Mob> mobs) {
        if (this.isDead) {
            return;
        }
        if (mobs.isEmpty()) {
            return;
        }
        try {
            selectedSkill.lastTimeUseThisSkill = System.currentTimeMillis();
            int skillTemplateId = selectedSkill.template.id;

            if (skillTemplateId == 4) {
                return;
            } else if (skillTemplateId == 25 || skillTemplateId == 34) {
                SkillOption[] option = selectedSkill.options;
                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {
                    Effect eff = new Effect(6, 3000, 0);
                    Mob mob = mobs.get(0);
                    mob.effects.put(eff.template.type, eff);
                    zone.setIce(mob, true);
                }
            } else if (skillTemplateId == 24) {
                SkillOption[] option = selectedSkill.options;
                Effect eff = new Effect(18, option[0].param * 1000L, 0);
                Mob mob = mobs.get(0);
                mob.effects.put(eff.template.type, eff);
                zone.setMove(mob, true);
                return;
            } else if (skillTemplateId == 40) {
                int pt = selectedSkill.point;
                int maxTarget = (selectedSkill.maxFight > 0) ? selectedSkill.maxFight : ((pt >= 6) ? 3 : (pt >= 4 ? 2 : 1));
                int atkReduction = 10 + (pt - 1);
                int resReduction = 30 + (pt - 1) * 6;
                int duration = (pt == 1) ? 6000 : (pt == 2 ? 6500 : (pt == 3 ? 7000 : (pt == 4 ? 7500 : 8000)));
                if (selectedSkill.options != null && selectedSkill.options.length >= 3) {
                    atkReduction = selectedSkill.options[0].param;
                    resReduction = selectedSkill.options[1].param;
                    duration = selectedSkill.options[2].param * 1000;
                }
                ArrayList<Mob> targets = new ArrayList<>();
                for (Mob m : mobs) {
                    if (m != null && !m.isDead && !targets.contains(m)) {
                        targets.add(m);
                        if (targets.size() >= maxTarget) break;
                    }
                }
                if (targets.size() < maxTarget && !targets.isEmpty()) {
                    Mob primary = targets.get(0);
                    for (Mob m : zone.monsters) {
                        if (m != null && !m.isDead && !targets.contains(m)) {
                            if (NinjaUtils.getDistance(primary.x, primary.y, m.x, m.y) <= 200) {
                                targets.add(m);
                                if (targets.size() >= maxTarget) break;
                            }
                        }
                    }
                }
                zone.getService().setSkillPaint_1(targets, this, (byte) selectedSkill.template.id);
                for (Mob mob : targets) {
                    Effect eff = new Effect(0, duration, 0);
                    mob.effects.put(eff.template.type, eff);
                    mob.setMagumandariDebuff(System.currentTimeMillis() + duration, atkReduction, resReduction);
                    zone.setDisable(mob, true);
                }
                return;
            } else if (skillTemplateId == 43) {
                SkillOption[] option = selectedSkill.options;
                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {
                    Effect eff = new Effect(7, 2000, 0);
                    Mob mob = mobs.get(0);
                    mob.effects.put(eff.template.type, eff);
                    zone.setWind(mob, true);
                }
            } else if (skillTemplateId == 7 || skillTemplateId == 16) {
                SkillOption[] option = selectedSkill.options;
                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {
                    Effect eff = new Effect(5, 4000, 0);
                    Mob mob = mobs.get(0);
                    mob.effects.put(eff.template.type, eff);

                    zone.setFire(mob, true);
                }
            } else if (skillTemplateId == 9
                    || skillTemplateId == 61
                    || skillTemplateId == 73
                    || skillTemplateId == 79
                    || skillTemplateId == 18
                    || skillTemplateId == 62
                    || skillTemplateId == 78
                    || skillTemplateId == 83 || skillTemplateId == 85 || skillTemplateId == 86) {
                SkillOption[] option = selectedSkill.options;
                for (Mob mob : mobs) {
                    int rand = NinjaUtils.nextInt(100);
                    if (rand <= option[0].param) {
                        Effect eff = new Effect(5, 2000, 0);
                        byte type = eff.template.type;
                        Effect eff2 = mob.effects.get(type);
                        if (eff2 != null) {
                            if (eff2.getTimeRemaining() > eff.getTimeLength()) {
                                continue;
                            }
                        }
                        mob.effects.put(type, eff);
                        zone.setFire(mob, true);
                    }
                }
            } else if (skillTemplateId == 27
                    || skillTemplateId == 63
                    || skillTemplateId == 75
                    || skillTemplateId == 81
                    || skillTemplateId == 36
                    || skillTemplateId == 64
                    || skillTemplateId == 76
                    || skillTemplateId == 82 || skillTemplateId == 87 || skillTemplateId == 88) {
                SkillOption[] option = selectedSkill.options;
                for (Mob mob : mobs) {
                    int rand = NinjaUtils.nextInt(100);
                    if (rand <= option[0].param) {
                        Effect eff = new Effect(6, 2000, 0);
                        byte type = eff.template.type;
                        Effect eff2 = mob.effects.get(type);
                        if (eff2 != null) {
                            if (eff2.getTimeRemaining() > eff.getTimeLength()) {
                                continue;
                            }
                        }
                        mob.effects.put(type, eff);
                        zone.setIce(mob, true);
                    }
                }
            } else if (skillTemplateId == 45
                    || skillTemplateId == 65
                    || skillTemplateId == 74
                    || skillTemplateId == 80
                    || skillTemplateId == 54
                    || skillTemplateId == 66
                    || skillTemplateId == 77
                    || skillTemplateId == 84 || skillTemplateId == 89 || skillTemplateId == 90) {
                SkillOption[] option = selectedSkill.options;
                for (Mob mob : mobs) {
                    int rand = NinjaUtils.nextInt(100);
                    if (rand <= option[0].param) {
                        Effect eff = new Effect(7, 1000, 0);
                        byte type = eff.template.type;
                        Effect eff2 = mob.effects.get(type);
                        if (eff2 != null) {
                            if (eff2.getTimeRemaining() > eff.getTimeLength()) {
                                continue;
                            }
                        }
                        mob.effects.put(type, eff);
                        zone.setWind(mob, true);
                    }
                }
            } else if (optionsSupportSkill[69] > 0) {
                for (Mob mob : mobs) {
                    int rand = NinjaUtils.nextInt(100);
                    if (rand <= optionsSupportSkill[69]) {
                        Effect eff = new Effect(6, 2000, 0);
                        byte type = eff.template.type;
                        Effect eff2 = mob.effects.get(type);
                        if (eff2 != null) {
                            if (eff2.getTimeRemaining() > eff.getTimeLength()) {
                                continue;
                            }
                        }
                        mob.effects.put(type, eff);
                        zone.setIce(mob, true);
                    }
                }
            }
            boolean isFatal = this.fatal > NinjaUtils.nextInt(1000); // 1000 chí mạng là max 100% (fix dame ảo)
            int add = 0;
            if (isInvisible) {
                Effect eff = em.findByType((byte) 11);
                if (eff != null) {
                    isFatal = true;
                    add = optionsSupportSkill[61];
                    getService().removeEffect(eff);
                    zone.getService().playerRemoveEffect(this, eff);
                    em.removeEffect(eff);
                }
            }
            // zone.lockChar.lock();
            // try {
            zone.getService().setSkillPaint_1(mobs, this, (byte) selectedSkill.template.id);
            // getService().updateMp();
            for (Mob mob : mobs) {

                mob.lock.lock();

                try {
                    if (!mob.isDead) {
                        if (mob.template.id == MobName.BOSS_TUAN_LOC || mob.template.id == MobName.QUAI_VAT) {
                            if (mob.chars.get(0) != this.id) {
                                continue;
                            }
                        }
                        // if ((getEventPoint().getPoint(LunarNewYear.MYSTERY_BOX_LEFT) <= 0
                        // && mob.template.id == MobName.HOP_BI_AN && this.mob == null)) {
                        // continue;
                        // }
                        if (clan == null && mob.template.id == MobName.NGUOI_TUYET) {
                            continue;
                        }

                        if (mob.template.id == MobName.BAO_QUAN && em.findByID((byte) 23) == null) {
                            continue;
                        }

                        if (mob.template.id == MobName.KORO_KING && getIndexItemByIdInBag(ItemName.VIEN_THUOC_THAN_KY) == -1) {
                            continue;
                        }

                        if (mob.template.id == MobName.CHUOT_CANH_TY) {
                            if (fashion[2] == null || (fashion[2] != null && fashion[2].template.id != ItemName.AO_NGU_THAN && fashion[2].template.id != ItemName.AO_TAN_THOI)) {
                                continue;
                            }
                        }

                        SingleRollCombatResolver.CombatOutcome outcome = SingleRollCombatResolver.resolve(
                            this.exactly,
                            (int) mob.level * 2,
                            this.fatal,
                            0,
                            0.0
                        );
                        boolean isMiss = outcome.isMiss();
                        boolean mobIsFatal = isFatal || outcome.isCrit();

                        // boolean isMiss = NinjaUtil.nextInt(this.exactly + 10) < 4;
                        if (skillTemplateId == 7 || skillTemplateId == 25 || skillTemplateId == 34 || skillTemplateId == 43) {
                            isMiss = false;
                        }
                        if (mob.isBoss && !this.zone.tilemap.isDungeo9x() && mob.template.id != MobName.CO_HON) { /// level ăn boss
                            if (this.zone.tilemap.isVDMQ() || this.zone.tilemap.isLangCo() || this.zone.tilemap.isLangTruyenThuyet() || this.zone.tilemap.isLangDiaNguc() ) { // lang dia nguc

                                if (this.isHuman && Math.abs(this.level - (int) mob.level) > 20) {
                                    isMiss = true;
                                }
                                if (clone != null && !clone.isDead && clone.isNhanBan) {
                                    if (Math.abs(clone.level - (int) mob.level) > 20) {
                                        isMiss = true;
                                    }
                                }
                            } else { // trường hợp còn lại khi tất cả các điều kiện đều không đúng
                                if (!this.zone.tilemap.isDungeoClan()) {
                                    if (Math.abs(this.level - (int) mob.level) > 20) {
                                        isMiss = true;
                                    }
                                    if (clone != null && !clone.isDead && clone.isNhanBan) {
                                        if (Math.abs(clone.level - (int) mob.level) > 20) {
                                            isMiss = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (isMiss) {
                            zone.getService().attackMonster(-1, false, mob);
                        } else {
                            if (isEffBong) {
                                Effect eff = em.findByType((byte) 5);
                                if (eff != null) {
                                    Effect eff2 = new Effect(9, eff.getTimeRemaining(), eff.param);
                                    eff2.param2 = this.id;
                                    mob.effects.put(eff2.template.type, eff2);
                                }
                            }
                            int dameHit = CombatService.calculatePvEDamage(this, mob, mobIsFatal, add);
                            if (dameHit > 0) {
                                mob.Fight(this, dameHit); // fix dame boss
                                int preHP = mob.hp;
                                if (this.isModeRemove) {
                                    mob.hp = 0;
                                } else {
                                    if (mob.template.id == MobName.BU_NHIN) {
                                        mob.addHp(-(mob.maxHP / 5));
                                    } else {
                                        mob.addHp(-dameHit);
                                    }
                                }
                                if (mob.hp < 0) {
                                    mob.hp = 0;
                                }
                                int nextHP = mob.hp;
                                int hp = Math.abs(nextHP - preHP);
                                int dLevel = Math.abs(mob.level - this.level);
                                if (mob.template.id != MobName.BOSS_TUAN_LOC && mob.template.id != MobName.QUAI_VAT && mob.template.id != MobName.JUUBI_SHINJU) {
                                    addExp(mob, hp);
                                }
                                if (mob.hp <= 0) {
                                    mob.die();
                                }
                                if (mob.isDead) {
                                    Char master = mob.sortNinjaFight(); // fix dame boss
                                    Char killer = getOriginChar();
                                    mob.dead(killer, master); // fix dame boss
                                } else {
                                    if (mob.id != 0 && !isNhanBan) {
                                        mob.addCharId(this.id);
                                    }
                                }

                                if (mob.template.id == MobName.HOP_BI_AN && mob.isDead) {
                                    zone.getService().addEffectAuto((byte) 8, mob.x, mob.y, (byte) 0, (short) 1);
                                }

                                zone.getService().attackMonster(dameHit, mobIsFatal, mob);
                                this.mobAtk = mob.id;
                                if (zone.tilemap.isDungeoClan()) {
                                    Territory.checkEveryAttack(this);
                                }
                            }
                        }
                    }
                } finally {
                    mob.lock.unlock();
                }
            }
        } catch (Exception e) {
            Log.logException("Attack monster(2) err: ", Char.class, e);
        }
        // } finally {
        // zone.lockChar.unlock();
        // }
    }

    // exp quái , tinh anh thủ lĩnh , boss
    public void addExp(Mob mob, int dame) {
        int dLevel = Math.abs(mob.level - this.level);
        if (mob.template.id != MobName.BU_NHIN && (dLevel <= 10)) {
            long exp = 1;
            int a = this.level / 20;
            a = a == 0 ? 1 : a;
            int b = dame * a; // thêm /2 là giảm 1 nửa
            int c = (mob.level - this.level) * a;
            exp = (b / 2) + ((long) b * c / 100);
            if (mob.isBoss) {
                exp *= 2;
            } else if (mob.levelBoss == 1) {
                exp *= 4;
            } else if (mob.levelBoss == 2) {
                exp *= 7;
            }
            int add = 0;
            if (isEffExp) {
                Effect effAddExp = em.findByID((byte) 38);
                if (effAddExp != null) {
                    add = effAddExp.param;
                }
            }
            exp += exp * (options[100] + optionsSupportSkill[29] + add) / 100;
            if (incrExp > 0) {
                exp *= incrExp;
            }
            if (zone.tilemap.isDungeo()) {
                exp *= 3;
            }
//            if (this.level >= 60) { /// giảm exp từ level 80
//                exp /= 2;
//            }
            // exp nhóm
            if (exp > 0) {
                exp *= 1;
                addExp(exp);
                if (zone.tilemap.isDungeo()) {
                    long expGroup = exp / 99; // mặc định là chia 10
                    Dungeon dun = (Dungeon) findWorld(World.DUNGEON);
                    if (dun != null) {
                        dun.addExp(this, expGroup); // exp hang động
                    }

                } else {
                    if (this.group != null) {
                        long expGroup = exp / 99; // mặc định là chia 10
                        List<Char> chars = this.group.getCharsInZone(this.mapId, zone.id);
                        for (Char _char : chars) {
                            if (_char != null && _char != this && !_char.isDead) {
                                int dLevel2 = Math.abs(this.level - _char.level);
                                if (dLevel2 <= 10) {
                                    _char.addExp(expGroup); // exp nhóm
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void LogCheck(String name) {
        if (this.name.equals("admin")) {
            System.out.println(name);
        }
    }

    /// fix bất tử
//    public void attackCharacter(ArrayList<Char> chars) {
//        if (this.isDead) {
//            return;
//        }
//        Char originChar = getOriginChar();
//        if (originChar.mapId != 138) {
//            if (!originChar.isTest && (isVillage() || isSchool())) {
//                return;
//            }
//        }
//
//        if (isNonCombatState()) {
//            return;
//        }
//        selectedSkill.lastTimeUseThisSkill = System.currentTimeMillis();
//        int skillTemplateId = selectedSkill.template.id;
//        if (skillTemplateId == 4) {
//            serverDialog("Không thể thi triển thuật này lên người");
//            return;
//        } else if (skillTemplateId == 25 || skillTemplateId == 34) {
//            SkillOption[] option = selectedSkill.options;
//            int rand = NinjaUtils.nextInt(100);
//            if (rand <= option[0].param) {
//                Effect eff = new Effect(6, 3000, 0);
//                Char _char = chars.get(0);
//                eff.addTime(-_char.options[41] * 1000);
//                eff.addTime(options[44]);
//                Effect eff2 = _char.em.findByType((byte) 16);
//                if (eff2 != null) {
//                    eff.addTime(-eff2.param2 * 1000);
//                }
//                _char.em.setEffect(eff);
//            }
//        } else if (skillTemplateId == 24) {
//            SkillOption[] option = selectedSkill.options;
//            Effect eff = new Effect(18, option[0].param * 1000, 0);
//            Char _char = chars.get(0);
//            _char.em.setEffect(eff);
//            return;
//        } else if (skillTemplateId == 43) {
//            SkillOption[] option = selectedSkill.options;
//            int rand = NinjaUtils.nextInt(100);
//            if (rand <= option[0].param) {
//                Effect eff = new Effect(7, 3000, 0);
//                Char _char = chars.get(0);
//                eff.addTime(-_char.options[42] * 1000);
//                eff.addTime(options[45] * 1000);
//                _char.em.setEffect(eff);
//            }
//        } else if (skillTemplateId == 7) {
//            SkillOption[] option = selectedSkill.options;
//            int rand = NinjaUtils.nextInt(100);
//            if (rand <= option[0].param) {
//                Effect eff = new Effect(5, 3000, 0);
//                Char _char = chars.get(0);
//                eff.addTime(-_char.options[40] * 1000);
//                eff.addTime(options[43] * 1000);
//                Effect eff2 = _char.em.findByType((byte) 16);
//                if (eff2 != null) {
//                    eff.addTime(-eff2.param * 1000);
//                }
//                _char.em.setEffect(eff);
//            }
//        } else if (skillTemplateId == 9 || skillTemplateId == 61 || skillTemplateId == 73 || skillTemplateId == 79
//                || skillTemplateId == 18 || skillTemplateId == 62 || skillTemplateId == 78 || skillTemplateId == 83) {
//            SkillOption[] option = selectedSkill.options;
//            for (Char _char : chars) {
//                int rand = NinjaUtils.nextInt(100);
//                if (rand <= option[0].param) {
//                    Effect eff = new Effect(5, 2000, 0);
//                    eff.addTime(-_char.options[40] * 1000);
//                    eff.addTime(options[43] * 1000);
//                    Effect eff2 = _char.em.findByType((byte) 16);
//                    if (eff2 != null) {
//                        eff.addTime(-eff2.param * 1000);
//                    }
//                    byte type = eff.template.type;
//                    Effect eff3 = _char.em.findByType(type);
//                    if (eff3 != null) {
//                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
//                            continue;
//                        }
//                    }
//                    _char.em.setEffect(eff);
//                }
//
//            }
//
//        } else if (skillTemplateId == 27 || skillTemplateId == 63 || skillTemplateId == 75 || skillTemplateId == 81
//                || skillTemplateId == 36 || skillTemplateId == 64 || skillTemplateId == 76 || skillTemplateId == 82) {
//            SkillOption[] option = selectedSkill.options;
//            for (Char _char : chars) {
//                int rand = NinjaUtils.nextInt(100);
//                if (rand <= option[0].param) {
//                    Effect eff = new Effect(6, 2000, 0);
//                    eff.addTime(-_char.options[41] * 1000);
//                    eff.addTime(options[44] * 1000);
//                    Effect eff2 = _char.em.findByType((byte) 16);
//                    if (eff2 != null) {
//                        eff.addTime(-eff2.param2 * 1000);
//                    }
//                    byte type = eff.template.type;
//                    Effect eff3 = _char.em.findByType(type);
//                    if (eff3 != null) {
//                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
//                            continue;
//                        }
//                    }
//                    _char.em.setEffect(eff);
//                }
//
//            }
//        } else if (skillTemplateId == 45 || skillTemplateId == 65 || skillTemplateId == 74 || skillTemplateId == 80
//                || skillTemplateId == 54 || skillTemplateId == 66 || skillTemplateId == 77 || skillTemplateId == 84) {
//            SkillOption[] option = selectedSkill.options;
//            for (Char _char : chars) {
//                int rand = NinjaUtils.nextInt(100);
//                if (rand <= option[0].param) {
//                    Effect eff = new Effect(7, 1000, 0);
//                    eff.addTime(-_char.options[42] * 1000);
//                    eff.addTime(options[45] * 1000);
//                    byte type = eff.template.type;
//                    Effect eff3 = _char.em.findByType(type);
//                    if (eff3 != null) {
//                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
//                            continue;
//                        }
//                    }
//                    _char.em.setEffect(eff);
//                }
//            }
//        }
//        boolean isFatal = this.fatal > NinjaUtils.nextInt(1000);
//        if (isInvisible) {
//            Effect eff = em.findByType((byte) 11);
//            if (eff != null) {
//                isFatal = true;
//                getService().removeEffect(eff);
//                zone.getService().playerRemoveEffect(this, eff);
//                em.removeEffect(eff);
//            }
//        }
//        zone.getService().setSkillPaint_2(chars, this, (byte) selectedSkill.template.id);
//        for (Char pl : chars) {
//            if (mapId != 138 && !pl.isTest && (isVillage() || isSchool())) {
//                continue;
//            }
//            if (BlackSaturday.getInstance().status == 1) {
//                if (pl.clan != null && this.clan != null && this.clan == pl.clan) {
//                    if (this.typePk == PK_PHE && pl.typePk == PK_PHE) {
//                        continue;
//                    }
//                }
//            }
//            if (pl.equipment != null) {
//                if (isHuman && pl.equipment[10] != null) {
//                    if (pl.equipment[10].id == ItemName.TUAN_LOC_PK || (pl.equipment[10].id == ItemName.PET_BORU && !pl.equipment[10].hasExpire())) {
//                        continue;
//                    }
//                }
//            }
//            if (selectedSkill.template.type == 1) {
//                if (mobMe != null && mobMe.damageOnPlayer > 0) {
//                    mobMe.attack(pl, this);
//                }
//            }
//            pl.lock.lock();
//            try {
//                if (!pl.isDead && !pl.isCleaned) {
//                    int dameHit = NinjaUtils.nextInt(this.damage2, this.damage);
//                    int dameBasic = dameHit;
//                    int dameMp = 0;
//                    dameHit += options[103];
//                    if (isEffDameDown) {
//                        Effect eff2 = pl.em.findByID((byte) 37);
//                        if (eff2 != null) {
//                            dameHit -= dameHit * eff2.param / 100;
//                        }
//                    }
//                    int dameFatal = 0;
//                    if (isFatal) {
//                        dameFatal += dameBasic;
//                        dameFatal += (dameBasic * (this.percentFatalDame + optionsSupportSkill[65])) / 100;
//                        dameFatal += this.fatalDame;
//                        dameFatal -= dameHit * (pl.options[46] + pl.options[79]) / 100;
//                    }
//                    if (pl.isFire) {
//                        dameHit += dameBasic;
//                    }
//                    boolean isSkipResistance = false;
//                    if (isEffSkipResistance) {
//                        Effect eff3 = pl.em.findByID((byte) 39);
//                        if (eff3 != null) {
//                            isSkipResistance = NinjaUtils.nextInt(100) < eff3.param;
//                        }
//                    }
//
//                    if (pl.optionsSupportSkill[69] > 0) { // dong bang skill kunai 6x
//                        if (!this.isNhanBan) {
//                            int rand = NinjaUtils.nextInt(100);
//                            if (rand <= pl.optionsSupportSkill[69]) {
//                                Effect eff = new Effect(6, 1000, 0);
//                                eff.addTime(-options[41] * 1000);
//                                eff.addTime(pl.options[44] * 1000);
//                                Effect eff2 = this.em.findByType((byte) 16);
//                                if (eff2 != null) {
//                                    eff.addTime(-eff2.param2 * 1000);
//                                }
//                                byte type = eff.template.type;
//                                Effect eff3 = pl.em.findByType(type);
//                                if (eff3 != null) {
//                                    if (eff3.getTimeRemaining() > eff.getTimeLength()) {
//                                        continue;
//                                    }
//                                }
//                                em.setEffect(eff);
//                            }
//                        }
//                    }
//
//                    switch (getSys()) {
//                        case 1:
//                            if (!isSkipResistance) {
//                                dameHit -= pl.resFire;
//                            }
//                            dameHit -= dameHit * pl.options[127] / 100;
//                            dameHit -= pl.options[48];
//                            dameHit += options[51];
//                            dameHit += dameBasic * options[54] / 100;
//                            break;
//
//                        case 2:
//                            if (!isSkipResistance) {
//                                dameHit -= pl.resIce;
//                            }
//
//                            dameHit -= dameHit * pl.options[130] / 100;
//                            dameHit -= pl.options[49];
//                            dameHit += options[52];
//                            dameHit += dameBasic * options[54] / 100;
//                            if (isFatal) {
//                                dameFatal += dameHit * optionsSupportSkill[65] / 100;
//                            }
//                            break;
//
//                        case 3:
//                            if (!isSkipResistance) {
//                                dameHit -= pl.resWind;
//                            }
//                            dameHit -= dameHit * pl.options[131] / 100;
//                            dameHit -= pl.options[50];
//                            dameHit += options[53];
//                            dameHit += dameBasic * options[54] / 100;
//                            break;
//                    }
//                    int kstcm = pl.options[121] > 100 ? 100 : pl.options[121];
//                    dameFatal -= dameFatal * kstcm / 100;
//                    dameHit -= dameHit * (pl.options[63] + pl.options[98]) / 100;
//                    if (pl.isReductionDame) {
//                        dameHit -= dameHit * pl.options[136] / 100;
//                    }
//                    dameHit = (dameHit + (dameFatal / 3)) * (6 - ((pl.level - this.level) / 40)) / 100;
//                    if (!isNhanBan) {
//                        int reactDame = pl.reactDame;
//
//                        if (pl.options[135] > 0) {
//                            if (NinjaUtils.nextInt(100) < pl.options[135]) {
//                                reactDame = hp * 20 / 100;
//                                zone.getService().addEffect(mob, 64, 5, 5, 0);
//                            }
//                        }
//
//                        if (reactDame > 0) {
//                            if (this.hp - reactDame > 1) {
//                                addHp(-reactDame);
//                            } else {
//                                this.hp = 1;
//                                reactDame = -1;
//                            }
//                            zone.getService().attackCharacter(reactDame, 0, this);
//                        }
//                    }
//                    if (dameHit <= 0) {
//                        dameHit = 1;
//                    }
//                    int randMiss = NinjaUtils.nextInt(pl.miss + 100);
//                    int randExactly = NinjaUtils.nextInt(this.exactly + 100);
//                    boolean isMiss = randMiss > randExactly;
//                    if (pl.isMiss) {
//                        isMiss = true;
//                    }
//
//                    // }
//                    dameHit += options[113];
//                    if (isInfected()) {
//                        dameHit -= dameHit * 80 / 100;
//                    }
//                    if (isCool()) {
//                        dameHit -= dameHit * 50 / 100;
//                    }
//                    if (isMiss) {
//                        dameHit = 0;
//                        dameMp = 0;
//                    }
//                    int num = dameHit + dameMp;
//                    // num -= pl.dameDown;
//                    if (num > 0) {
//                        if (isFatal) {
//                            dameHit *= -1;
//                            dameMp *= -1;
//                        }
//                        zone.getService().attackCharacter(dameHit, dameMp, pl);
//                        if (pl.isTest && pl.hp - num <= 0) {
//                            Char pl2 = zone.findCharById(pl.testCharId);
//                            int num2 = pl.hp;
//                            pl.testEnd(num2, pl2);
//                        } else {
//                            pl.addHp(-num);
//                        }
//                        if (pl.hp <= 0) {
//                            if (BlackSaturday.getInstance().status == 1) {
//                                if (pl.clan != null && this.clan != null && this.clan != pl.clan) {
//                                    if (!isVillage() && !isSchool()) {
//                                        if (this.typePk == PK_PHE && pl.typePk == PK_PHE) {
//                                            if (this.level - pl.level <= 10) {
//                                                this.clan.addPoint(1);
//                                            }
//                                            this.serverMessage("Bạn đã đánh trọng thương " + pl.name + " trong đại chiến gia tộc.");
//                                            pl.serverMessage("Bạn bị " + this.name + " đánh trọng thương trong đại chiến gia tộc.");
//                                        }
//                                    }
//                                }
//                            }
//                            if (zone.tilemap.isChienTruong()) {
//                                short pointAdd = 5;
//                                int range = pl.nDead - pl.nKill;
//                                if (range > 50) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 100) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 150) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 200) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 250) {
//                                    pointAdd -= 1;
//                                }
//                                nKill += 1;
//                                pl.nDead += 1;
//                                addWarPoint(pointAdd);
//                                getService().serverMessage("Bạn vừa đánh trọng thương " + pl.name);
//                                pl.getService().serverMessage("Bạn bị " + this.name + " đánh trọng thương");
//                                int[] var10000;

    /// /                                if (nvBiAn[1] != -1 && nvBiAn[2] == 6 && nvBiAn[3] != -1) {
    /// /                                    var10000 = nvBiAn;
    /// /                                    var10000[0] += pointAdd;
    /// /                                    if (nvBiAn[0] >= nvBiAn[1]) {
    /// /                                        this.completeTaskBiAn();
    /// /                                    }
    /// /                                }
//                            }
//                            if (zone.tilemap.isGTC()) {
//                                short pointAdd = 5;
//                                int range = pl.nDeadGTC - pl.nKillGtc;
//                                if (range > 50) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 100) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 150) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 200) {
//                                    pointAdd -= 1;
//                                }
//                                if (range > 250) {
//                                    pointAdd -= 1;
//                                }
//                                nKillGtc += 1;
//                                pl.nDeadGTC += 1;
//                                addWarClanPoint(pointAdd);
//                                String pl_name_new = this.name; // SVIP
//                                String name_new = this.name; // SVIP
//                                getService().serverMessage("Bạn vừa đánh trọng thương " + pl_name_new);
//                                pl.getService().serverMessage("Bạn bị " + name_new + " đánh trọng thương");
//                            }
//                            if ((originChar.typePk == 2 || originChar.typePk == 3) && originChar.gloryTask != null) {
//                                if (originChar.gloryTask.type == GloryTask.CUU_SAT_NGUOI_KHAC
//                                        //&& Math.abs(this.level - pl.level) <= 10
//                                        && !originChar.gloryTask.isExistCharacterId(pl.id)) {
//                                    originChar.gloryTask.updateProgress(1);
//                                    originChar.gloryTask.addCharacterId(pl.id);
//                                }
//                            }
//                            if (pl.enemies != null) {
//                                if (originChar.typePk == 3) {
//                                    originChar.addPointPk(1);
//                                } else if (originChar.typePk == 2 || originChar.killCharId == pl.id) {
//                                    originChar.addPointPk(2);
//                                }
//                                if (!(isBot())) {
//                                    pl.enemies.put(this.name, new Friend(this.name, (byte) 0));
//                                }
//                            }
//                            if (pl.hieuChien > 0) {
//                                long expLevel = NinjaUtils.getExp(pl.level);
//                                long expNext = Server.exps[pl.level];
//                                long expD = (expNext * ((pl.hieuChien > 5) ? 5 : pl.hieuChien) / 50);
//                                if (pl.exp - expD >= expLevel) {
//                                    pl.exp -= expD;
//                                    pl.expDown = 0;
//                                } else {
//                                    expD -= (pl.exp - expLevel);
//                                    pl.exp = expLevel;
//                                    pl.expDown += expD;
//                                    long fiftyPercent = (expNext / 2);
//                                    if (pl.expDown > fiftyPercent) {
//                                        pl.expDown = fiftyPercent;
//                                    }
//                                }
//                                pl.addPointPk(-1);
//                            }
//                            pl.startDie();
//                        }
//                    }
//                }
//            } finally {
//                pl.lock.unlock();
//            }
//        }
//    }
    public void attackCharacter(ArrayList<Char> chars) {
        if (this.isDead) {
            return;
        }
        Char originChar = getOriginChar();

        if (originChar.mapId != 138) {
            if (!originChar.isTest && (isVillage() || isSchool())) {
                return;
            }
        }

        if (isNonCombatState()) {
            return;
        }
        if (selectedSkill == null) {
            return;
        }
        if (originChar.hieuChien >= 15) {
            serverMessage("Điểm hiếu chiến quá cao, không thể tiếp tục thi triển thuật này lên người.");
            return;
        }
        selectedSkill.lastTimeUseThisSkill = System.currentTimeMillis();
        int skillTemplateId = selectedSkill.template.id;
        if (skillTemplateId == 4) {
            serverMessage("Kỹ năng bị động không thể kích hoạt!");
            return;
        } else if (skillTemplateId == 40) {
            serverMessage("Chiêu thức này chỉ có tác dụng lên quái vật!");
            return;
        } else if (skillTemplateId == 25 || skillTemplateId == 34) {
            SkillOption[] option = selectedSkill.options;
            int rand = NinjaUtils.nextInt(100);
            if (rand <= option[0].param) {
                if (!chars.isEmpty()) {
                    Effect eff = new Effect(6, 3000, 0);
                    Char _char = chars.get(0);
                    eff.addTime(-_char.options[41] * 1000L);
                    eff.addTime(options[44]);
                    Effect eff2 = _char.em.findByType((byte) 16);
                    if (eff2 != null) {
                        eff.addTime(-eff2.param2 * 1000L);
                    }
                    _char.em.setEffect(eff);
                }

            }
        } else if (skillTemplateId == 24) {
            if (!chars.isEmpty()) {
                SkillOption[] option = selectedSkill.options;
                Effect eff = new Effect(18, option[0].param * 1000L, 0);
                Char _char = chars.get(0);
                _char.em.setEffect(eff);
            }

            return;
        } else if (skillTemplateId == 43) {
            SkillOption[] option = selectedSkill.options;
            int rand = NinjaUtils.nextInt(100);
            if (rand <= option[0].param) {
                if (!chars.isEmpty()) {
                    Effect eff = new Effect(7, 3000, 0);
                    Char _char = chars.get(0);
                    eff.addTime(-_char.options[42] * 1000L);
                    eff.addTime(options[45] * 1000L);
                    _char.em.setEffect(eff);
                }

            }
        } else if (skillTemplateId == 7) {
            SkillOption[] option = selectedSkill.options;
            int rand = NinjaUtils.nextInt(100);
            if (rand <= option[0].param) {
                if (!chars.isEmpty()) {
                    Effect eff = new Effect(5, 3000, 0);
                    Char _char = chars.get(0);
                    eff.addTime(-_char.options[40] * 1000L);
                    eff.addTime(options[43] * 1000L);
                    Effect eff2 = _char.em.findByType((byte) 16);
                    if (eff2 != null) {
                        eff.addTime(-eff2.param * 1000L);
                    }
                    _char.em.setEffect(eff);
                }

            }
        } else if (skillTemplateId == 9 || skillTemplateId == 61 || skillTemplateId == 73 || skillTemplateId == 79 || skillTemplateId == 18 || skillTemplateId == 62 || skillTemplateId == 78 || skillTemplateId == 83) {
            SkillOption[] option = selectedSkill.options;
            for (Char _char : chars) {

                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {

                    Effect eff = new Effect(5, 2000, 0);
                    eff.addTime(-_char.options[40] * 1000L);
                    eff.addTime(options[43] * 1000L);
                    Effect eff2 = _char.em.findByType((byte) 16);
                    if (eff2 != null) {
                        eff.addTime(-eff2.param * 1000L);
                    }
                    byte type = eff.template.type;
                    Effect eff3 = _char.em.findByType(type);
                    if (eff3 != null) {
                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
                            continue;
                        }
                    }
                    _char.em.setEffect(eff);
                }

            }

        } else if (skillTemplateId == 27 || skillTemplateId == 63 || skillTemplateId == 75 || skillTemplateId == 81 || skillTemplateId == 36 || skillTemplateId == 64 || skillTemplateId == 76 || skillTemplateId == 82) {
            SkillOption[] option = selectedSkill.options;
            for (Char _char : chars) {
                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {
                    Effect eff = new Effect(6, 2000, 0);
                    eff.addTime(-_char.options[41] * 1000L);
                    eff.addTime(options[44] * 1000L);
                    Effect eff2 = _char.em.findByType((byte) 16);
                    if (eff2 != null) {
                        eff.addTime(-eff2.param2 * 1000L);
                    }
                    byte type = eff.template.type;
                    Effect eff3 = _char.em.findByType(type);
                    if (eff3 != null) {
                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
                            continue;
                        }
                    }
                    _char.em.setEffect(eff);
                }

            }
        } else if (skillTemplateId == 45 || skillTemplateId == 65 || skillTemplateId == 74 || skillTemplateId == 80 || skillTemplateId == 54 || skillTemplateId == 66 || skillTemplateId == 77 || skillTemplateId == 84) {
            SkillOption[] option = selectedSkill.options;
            for (Char _char : chars) {
                int rand = NinjaUtils.nextInt(100);
                if (rand <= option[0].param) {
                    Effect eff = new Effect(7, 1000, 0);
                    eff.addTime(-_char.options[42] * 1000L);
                    eff.addTime(options[45] * 1000L);
                    byte type = eff.template.type;
                    Effect eff3 = _char.em.findByType(type);
                    if (eff3 != null) {
                        if (eff3.getTimeRemaining() > eff.getTimeLength()) {
                            continue;
                        }
                    }
                    _char.em.setEffect(eff);
                }
            }
        }
        boolean isFatal = this.fatal > NinjaUtils.nextInt(1000); // 1000 chí mạng là max 100% (fix dame ảo)
        if (isInvisible) {
            Effect eff = em.findByType((byte) 11);
            if (eff != null) {
                isFatal = true;
                getService().removeEffect(eff);
                zone.getService().playerRemoveEffect(this, eff);
                em.removeEffect(eff);
            }
        }
        zone.getService().setSkillPaint_2(chars, this, (byte) selectedSkill.template.id);
        for (Char pl : chars) {
            if (mapId != 138 && !pl.isTest && (isVillage() || isSchool())) {
                continue;
            }
            if (selectedSkill.template.type == 1) {
                if (mobMe != null && mobMe.damageOnPlayer > 0) {
                    long now = System.currentTimeMillis();
                    if (now - mobMe.lastTimeAttack > (mobMe.attackDelay + 2000)) {
                        mobMe.lastTimeAttack = now;
                        mobMe.attack(pl, this);
                    }

                }
            }
            pl.lock.lock();
            try {
                if (!pl.isDead && !pl.isCleaned) {
                    if (pl.optionsSupportSkill[69] > 0) { // dong bang skill kunai 6x
                        if (!this.isNhanBan) {
                            int rand = NinjaUtils.nextInt(150);
                            if (rand <= pl.optionsSupportSkill[69]) {
                                Effect eff = new Effect(6, 1000, 0);
                                eff.addTime(-options[41] * 1000L);
                                eff.addTime(pl.options[44] * 1000L);
                                Effect eff2 = this.em.findByType((byte) 16);
                                if (eff2 != null) {
                                    eff.addTime(-eff2.param2 * 1000L);
                                }
                                byte type = eff.template.type;
                                Effect eff3 = pl.em.findByType(type);
                                if (eff3 != null) {
                                    if (eff3.getTimeRemaining() > eff.getTimeLength()) {
                                        continue;
                                    }
                                }
                                em.setEffect(eff);
                            }
                        }
                    }

                    SingleRollCombatResolver.CombatOutcome outcome = SingleRollCombatResolver.resolve(
                        this.exactly,
                        pl.miss,
                        this.fatal,
                        (pl.options != null && pl.options.length > 31) ? pl.options[31] : 0,
                        0.0
                    );
                    boolean playerIsFatal = isFatal || outcome.isCrit();
                    CombatResult result = CombatService.calculatePvPDamage(this, pl, playerIsFatal);

                    if (!isNhanBan && result.getReactDamage() > 0) {
                        int reactDame = result.getReactDamage();
                        if (result.isIceReflect()) {
                            zone.getService().addEffect(this, 64, 5, 5, 0);
                        }
                        if (this.hp - reactDame > 1) {
                            addHp(-reactDame);
                        } else {
                            reactDame = Math.max(1, this.hp - 1);
                            addHp(-reactDame);
                        }
                        zone.getService().attackCharacter(reactDame, 0, this);
                        if (this.hp <= 0) {
                            startDie();
                        }
                    }

                    int dameHit = result.getDamage();
                    int dameMp = 0;
                    boolean isMiss = result.isMiss();
                    if (isMiss) {
                        dameHit = 0;
                        dameMp = 0;
                    } else if (pl.isShieldMana && (pl.mp * 100 / Math.max(1, pl.maxMP)) >= 10) {
                        int absorption = 10;
                        Effect eff = pl.em.findByType((byte) 6);
                        if (eff != null && eff.param > 0) {
                            absorption = eff.param;
                        }
                        Skill c25 = pl.getSkill(31);
                        int pt = (c25 != null) ? c25.point : 1;
                        int absorbDame = (dameHit * absorption) / 100;
                        dameHit -= absorbDame;
                        int ratio10 = Math.max(10, 20 - (pt - 1) * 2);
                        dameMp = absorbDame * ratio10 / 10;
                        pl.addMp(-dameMp);
                    }
                    int num = dameHit;

                    if (num >= 0) {
                        if (isFatal) {
                            dameHit *= -1;
                            dameMp *= -1;
                        }
                        if (pl.isTest && pl.hp - num <= 0) {
                            Char pl2 = zone.findCharById(pl.testCharId);
                            int num2 = pl.hp;
                            pl.testEnd(num2, pl2);
                        } else {
                            pl.addHp(-num);
                        }
                        zone.getService().attackCharacter(dameHit, dameMp, pl);
                        if (pl.hp <= 0) {

                            if (BlackSaturday.getInstance().status == 1 && !zone.tilemap.isNotBlack()) { /// t7 đen tối
                                if (pl.clan != null && this.clan != null && this.clan != pl.clan) {
                                    if (!isVillage() && !isSchool()) {
                                        if (this.typePk == PK_PHE && pl.typePk == PK_PHE) {
                                            if (this.level - pl.level <= 10) {
                                                this.clan.addPoint(1);
                                            }
                                            this.serverMessage("Bạn đã đánh trọng thương " + pl.name + " trong đại chiến gia tộc.");
                                            pl.serverMessage("Bạn bị " + this.name + " đánh trọng thương trong đại chiến gia tộc.");
                                        }
                                    }
                                }
                            }


                            if (zone.tilemap.isChienTruong()) {
                                short pointAdd = 5;
                                int range = pl.nDead - pl.nKill;
                                if (range > 50) {
                                    pointAdd -= 1;
                                }
                                if (range > 100) {
                                    pointAdd -= 1;
                                }
                                if (range > 150) {
                                    pointAdd -= 1;
                                }
                                if (range > 200) {
                                    pointAdd -= 1;
                                }
                                if (range > 250) {
                                    pointAdd -= 1;
                                }
                                nKill += 1;
                                pl.nDead += 1;
                                addWarPoint(pointAdd);
                                getService().serverMessage("Bạn vừa đánh trọng thương " + pl.name);
                                pl.getService().serverMessage("Bạn bị " + this.name + " đánh trọng thương");
                            }
                            if (zone.tilemap.isGTC()) {
                                short pointAdd = 5;
                                int range = pl.nDeadGTC - pl.nKillGtc;
                                if (range > 50) {
                                    pointAdd -= 1;
                                }
                                if (range > 100) {
                                    pointAdd -= 1;
                                }
                                if (range > 150) {
                                    pointAdd -= 1;
                                }
                                if (range > 200) {
                                    pointAdd -= 1;
                                }
                                if (range > 250) {
                                    pointAdd -= 1;
                                }
                                nKillGtc += 1;
                                pl.nDeadGTC += 1;
                                addWarClanPoint(pointAdd);
                                getService().serverMessage("Bạn vừa đánh trọng thương " + pl.name);
                                pl.getService().serverMessage("Bạn bị " + this.name + " đánh trọng thương");
                            }
                            if ((originChar.typePk == 2 || originChar.typePk == 3) && originChar.gloryTask != null) {
                                if (originChar.gloryTask.type == GloryTask.CUU_SAT_NGUOI_KHAC && Math.abs(this.level - pl.level) <= 10 && !originChar.gloryTask.isExistCharacterId(pl.id)) {
                                    originChar.gloryTask.updateProgress(1);
                                    originChar.gloryTask.addCharacterId(pl.id);
                                }
                            }
                            if (pl.enemies != null) {
                                if (originChar.typePk == 3) {
                                    originChar.addPointPk(1);
                                } else if (originChar.typePk == 2 || originChar.killCharId == pl.id) {
                                    originChar.addPointPk(2);
                                }
                                if (!(isBot())) {
                                    pl.enemies.put(this.name, new Friend(this.name, (byte) 0));
                                }
                            }
                            if (pl.hieuChien > 0) {
                                long expLevel = NinjaUtils.getExp(pl.level);
                                long expNext = Server.exps[pl.level];
                                long expD = (expNext * ((pl.hieuChien > 5) ? 5 : pl.hieuChien) / 50);
                                if (pl.exp - expD >= expLevel) {
                                    pl.exp -= expD;
                                    pl.expDown = 0;
                                } else {
                                    expD -= (pl.exp - expLevel);
                                    pl.exp = expLevel;
                                    pl.expDown += expD;
                                    long fiftyPercent = (expNext / 2);
                                    if (pl.expDown > fiftyPercent) {
                                        pl.expDown = fiftyPercent;
                                    }
                                }
                                pl.addPointPk(-1);
                            }
                            pl.startDie();
                        }
                    }
                }
            } finally {
                pl.lock.unlock();
            }
        }
    }

    public void attackAllType(Message ms, int type) {
        try {
            // Char originChar = this.getOriginChar();
            if (this.isDead) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (!selectedSkill.isCooldown()) {
                selectedSkill.lastTimeUseThisSkill = currentTimeMillis;
            } else {
                return;
            }
            if (isDontAttack()) {
                return;
            }
            if (this.equipment == null || this.equipment[1] == null) {
                return;
            }
            if (selectedSkill == null) {
                return;
            }
            if (clone != null && !clone.isDead && clone.isNhanBan) {
                ms.reader().mark(10000);
                clone.attackAllType(ms, type);
                ms.reader().reset();
            }
            ArrayList<Mob> mobs = new ArrayList<>();
            ArrayList<Char> chars = new ArrayList<>();
            int left = this.x - selectedSkill.dx;
            int right = this.x + selectedSkill.dx;
            int top = this.y - selectedSkill.dy - 50;
            int bottom = this.y + selectedSkill.dy;
            // int dx = selectedSkill.dx + 20;
            int len = ms.reader().readByte();
            Vector<Mob> m = new Vector<>();
            for (int i = 0; i < len; i++) {
                int id = ms.reader().readUnsignedByte();
                if ((this.faction == 0 && mapId == 99) || (this.faction == 1 && mapId == 103)) {
                    continue;
                }
                Mob mob = null;
                if (id == 127) {
                    mob = this.mob;
                } else {
                    mob = zone.findMoLivebByID(id);
                }
                if (mob == null) {
                    continue;
                }
                if (!isMeCanAttackNpc(mob)) {
                    continue;
                }
                if (mob != null && mob.hp > 0) {
                    m.add(mob);
                }
            }
            if (!m.isEmpty()) {
                this.mobFocus = m.get(0);
            }
            Vector<Char> m2 = new Vector<>();
            while (ms.reader().available() > 0) {
                int id = ms.reader().readInt();
                if (id == -1) {
                    continue;
                }
                Char _char = zone.findCharById(id);
                if (_char != null && _char.hp > 0 && _char != this) {
                    if (!isMeCanAttackOtherPlayer(_char)) {
                        continue;
                    }
                    if (_char.isInvisible()) {
                        continue;
                    }
                    m2.add(_char);
                }
            }
            int focusX = 0;
            int focusY = 0;
            Char charFocus = null;
            Mob mobFocus = null;
            if (type == 1) {
                if (!m.isEmpty()) {
                    Mob mob = m.get(0);
                    int rangeMove = mob.template.rangeMove;
                    if (mob.x >= (left - rangeMove) && mob.x <= (right + rangeMove) && mob.y >= (top - rangeMove) && mob.y <= (bottom + rangeMove)) {
                        mobFocus = mob;
                        if (!mobs.contains(mob)) {
                            mobs.add(mob);
                        }
                        focusX = mob.x;
                        focusY = mob.y;
                    }
                }
            } else if (type == 2) {
                if (m2.size() > 0) {
                    Char _char = m2.get(0);
                    if (left <= _char.x && _char.x <= right && top <= _char.y && _char.y <= bottom) {
                        charFocus = _char;
                        chars.add(_char);
                        focusX = _char.x;
                        focusY = _char.y;
                    }
                }
            }
            byte maxFight = selectedSkill.maxFight;
            if (mobFocus == null && charFocus == null) {
                ArrayList<Mob> mobss = new ArrayList<>();
                for (Mob mob : m) {
                    mobss.add(mob);
                    zone.getService().attackMonster(-1, false, mob);
                }
                zone.getService().setSkillPaint_1(mobss, this, (byte) selectedSkill.template.id);
                ArrayList<Char> chars2 = new ArrayList<>();
                for (Char _char : m2) {
                    chars2.add(_char);
                    zone.getService().attackCharacter(0, 0, _char);
                }
                zone.getService().setSkillPaint_2(chars2, this, (byte) selectedSkill.template.id);
            } else {
                int lent = m.size();
                if (lent > maxFight) {
                    lent = maxFight;
                }
                if (lent > 0) {
                    for (int i = 0; i < lent; i++) {
                        Mob mob2 = m.get(i);
                        if (mob2.status != 1 && mob2.status != 0 && mob2 != mobFocus && focusX - 100 <= mob2.x && mob2.x <= focusX + 100 && focusY - 50 <= mob2.y && mob2.y <= focusY + 50) {
                            if (!mobs.contains(mob2)) {
                                mobs.add(mob2);
                            }
                        }
                    }
                }
                int lent2 = m2.size();
                if (lent2 > maxFight - lent) {
                    lent2 = maxFight - lent;
                }
                if (lent2 > 0) {
                    for (int i = 0; i < lent2; i++) {
                        Char _char2 = m2.get(i);
                        if (_char2 != charFocus && focusX - 100 <= _char2.x && _char2.x <= focusX + 100 && focusY - 50 <= _char2.y && _char2.y <= focusY + 50) {
                            if (!chars.contains(_char2)) {
                                chars.add(_char2);
                            }
                        }
                    }
                }
            }
            if (mobs.isEmpty() && chars.isEmpty()) {
                return;
            }
            int manaUse = selectedSkill.manaUse;
            if (this.mp < manaUse) {
                return;
            }
            addMp(-manaUse);
            attackMonster(mobs);
            attackCharacter(chars);
        } catch (Exception ex) {
            Log.logException("Attack all type err: ", Char.class, ex);
        }
    }

    public boolean isMeCanAttackNpc(Mob mob) {
        if (isLockFire) {
            return false;
        }
        if (mob == null || this.selectedSkill == null || (this.selectedSkill.template.type == 2 && this.selectedSkill.template.id != 40) || (this.selectedSkill.template.type == 3 && this.selectedSkill.template.id != 40) || (this.selectedSkill.template.type == 4 && !mob.isDead)) {
            return false;
        }
        if (mob != null) {
            int templateID = mob.template.id;
            return !((templateID == MobName.VET_THU_HO && this.typePk == Char.PK_PHE1) || (templateID == MobName.BACH_LONG_TRU && this.typePk == Char.PK_PHE1) || (templateID == MobName.ONG_TU_SI && this.typePk == Char.PK_PHE2) || (templateID == MobName.HAC_LONG_TRU && this.typePk == Char.PK_PHE2) || (templateID == MobName.LAO_NHI && this.typePk == Char.PK_PHE1) || (templateID == MobName.LAO_TAM && this.typePk == Char.PK_PHE2) || (templateID == MobName.LAO_DAI && this.typePk == Char.PK_PHE3) || (templateID == MobName.THUY_TINH && typePk == Char.PK_PHE1) || (templateID == MobName.SON_TINH && typePk == Char.PK_PHE2));
        }

        return false;
    }

    public boolean isMeCanAttackOtherPlayer(Char cAtt) {
        if (isLockFire || cAtt.isInvisible()) {
            return false;
        }

        if (!isTest && ((cAtt.typePk == 0) || (cAtt.typePk == 3))
                && ((typePk == 0) || (typePk == 3)) && !cAtt.isCuuSat && !cAtt.isNhanBan
                && !cAtt.isBot()) {
            if (!(
                    typePk == 3 || isCuuSat || cAtt.isCuuSat || cAtt.typePk == 3
            )) {

                return false;
            }
        }

        if (cAtt.isNhanBan) {
            return false;
        }
        if (!(this.testCharId >= 0 && this.testCharId == cAtt.id) && (isVillage() || isSchool()) || mapId == 133 || mapId == 98 || mapId == 162) {
            serverMessage("Không được pk ở trong trường hoặc làng.");
            return false;
        }
        if (cAtt.equipment != null) {
            Equip equip = cAtt.equipment[ItemTemplate.TYPE_THUNUOI];
            if (equip != null) {
                if (equip.id == ItemName.TUAN_LOC_PK && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                    serverMessage("Không thể pk người này.");
                    return false;
                }
                if (equip.id == ItemName.CAI_TRANG_ANIME && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                    serverMessage("Không thể pk người này.");
                    return false;
                }
                if (equip.id == ItemName.BI_RE_HANH_PK && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                    serverMessage("Không thể pk người này.");
                    return false;
                }
            }
        }
//        // chống pk


        if (this.selectedSkill == null || this.selectedSkill.template.type == 2 || this.selectedSkill.template.type == 3 || this.selectedSkill.template.type == 4 && !cAtt.isDead) {
            return false;
        }

        return ((((this.typePk == PK_PHE3 && (cAtt.typePk == PK_PHE1 || cAtt.typePk == PK_PHE2)) || (this.typePk == PK_PHE1 && (cAtt.typePk == PK_PHE2 || cAtt.typePk == PK_PHE3)) || (this.typePk == PK_PHE2 && (cAtt.typePk == PK_PHE1 || cAtt.typePk == PK_PHE3))) && !isTeam(cAtt) && !isLang()) || (cAtt.typePk == PK_DOSAT && !isTeam(cAtt) && !isLang()) || (this.typePk == PK_DOSAT && !this.isTeam(cAtt) && !isLang()) || (this.typePk == PK_NHOM && cAtt.typePk == PK_NHOM && !this.isTeam(cAtt) && !isLang()) || (this.testCharId >= 0 && this.testCharId == cAtt.id) || (this.killCharId >= 0 && this.killCharId == cAtt.id && !isLang()) || (cAtt.killCharId >= 0 && cAtt.killCharId == this.id && !isLang())) && !cAtt.isDead;
    }

    public boolean isLang() {
        return mapId == 1 || mapId == 27 || mapId == 72 || mapId == 10 || mapId == 17 || mapId == 22 || mapId == 32 || mapId == 38 || mapId == 43 || mapId == 48;
    }

    public boolean isTeam(Char c) {
        if (group != null) {
            synchronized (group.memberGroups) {
                for (MemberGroup p : group.memberGroups) {
                    if (c.id == p.charId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isMobSameParty(Mob mob) {
        if (mob != null) {
            int templateID = mob.template.id;
            return (templateID == 142 && this.typePk == Char.PK_PHE1) || (templateID == 143 && this.typePk == Char.PK_PHE2) || (templateID == 143 && this.typePk == Char.PK_PHE3);
        }
        return false;
    }

    /// fix bất tử
//    public void attackCharacter(Message ms) {
//        try {
//            if (this.isDead) {
//                return;
//            }
//            if (isNonCombatState()) {
//                return;
//            }
//            long currentTimeMillis = System.currentTimeMillis();
//            if (selectedSkill != null && !selectedSkill.isCooldown()) {
//                selectedSkill.lastTimeUseThisSkill = currentTimeMillis;
//            } else {
//                return;
//            }
//            if (isDontAttack()) {
//                return;
//            }
//            if (this.equipment[1] == null) {
//                this.serverMessage("Vũ khí không thích hợp");
//                return;
//            }
//            if (selectedSkill == null) {
//                return;
//            }
//            if (clone != null && !clone.isDead && clone.isNhanBan) {
//                ms.reader().mark(10000);
//                clone.attackCharacter(ms);
//                ms.reader().reset();
//            }
//            ArrayList<Char> chars = new ArrayList<>();
//            int num = 0;
//            if (this.classId == 0 || this.classId == 1 || this.classId == 3 || this.classId == 5) {
//                num = 40;
//            }
//            int num2 = this.x - selectedSkill.dx;
//            int num3 = this.x + selectedSkill.dx;
//            int num4 = this.y - selectedSkill.dy - num;
//            int num5 = this.y + selectedSkill.dy;
//            Char charFocus = null;
//            Vector<Char> characterCanAttack = new Vector<>();
//            // Char originChar = this.getOriginChar();
//            while (ms.reader().available() > 0) {
//                int id = ms.reader().readInt();
//                Char _char = zone.findCharById(id);
//
//                if (_char == null) {  // null thì out
//                    _char.user.session.closeMessage();
//                }
//
//                if (_char != null && _char != this && _char.hp > 0) {
//                    if (!isMeCanAttackOtherPlayer(_char)) {
//                        continue;
//                    }
//                    if (_char.isInvisible()) {
//                        continue;
//                    }
//                    characterCanAttack.add(_char);
//                }
//            }
//            if (characterCanAttack.size() > 0) {
//                Char _char = characterCanAttack.get(0);
//                int skillTemplateId = selectedSkill.template.id;
//                if (skillTemplateId == 42) {
//                    setXY(_char.x, _char.y);
//                    zone.getService().teleport(this);
//                    getService().resetPoint();
//                    if (optionsSupportSkill[62] > 0) {
//                        Effect eff = new Effect(18, optionsSupportSkill[62], 0);
//                        _char.em.setEffect(eff);
//                    }
//                }
//                if (num2 <= _char.x && _char.x <= num3 && num4 <= _char.y && _char.y <= num5) {
//                    charFocus = _char;
//                    if (!chars.contains(_char)) {
//                        chars.add(_char);
//                    }
//                }
//            }
//            byte maxFight = selectedSkill.maxFight;
//            if (charFocus != null) {
//                int lent = characterCanAttack.size();
//                if (lent > maxFight) {
//                    lent = maxFight;
//                }
//                for (int i = 0; i < lent; i++) {
//                    Char _char = characterCanAttack.get(i);
//                    if (_char != charFocus && charFocus.x - 100 <= _char.x && _char.x <= charFocus.x + 100
//                            && charFocus.y - 50 <= _char.y && _char.y <= charFocus.y + 50) {
//                        if (!chars.contains(_char)) {
//                            chars.add(_char);
//                        }
//                    }
//                }
//            } else {
//                ArrayList<Char> charAttackNoDame = new ArrayList<>();
//                for (int i = 0; i < characterCanAttack.size(); i++) {
//                    Char _char = characterCanAttack.get(i);
//                    charAttackNoDame.add(_char);
//                    zone.getService().attackCharacter(0, 0, _char);
//                }
//                zone.getService().setSkillPaint_2(charAttackNoDame, this, (byte) selectedSkill.template.id);
//            }
//            if (chars.isEmpty()) {
//                return;
//            }
//            int manaUse = selectedSkill.manaUse;
//            if (this.mp < manaUse && this.isHuman) {
//                getService().SendMp();
//                return;
//            }
//            if (this.isHuman) {
//                addMp(-manaUse);
//                getService().updateMp();
//            }
//            attackCharacter(chars);
//        } catch (Exception ex) {
//            Log.error("err: " + ex.getMessage(), ex);
//        }
//    }
    public void attackCharacter(Message ms) {
        try {
            if (this.isDead) {
                return;
            }
            if (isNonCombatState()) { /// fix phân thân đánh trừ máu sớm trong lôi đài
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (selectedSkill != null && !selectedSkill.isCooldown()) {
                selectedSkill.lastTimeUseThisSkill = currentTimeMillis;
            } else {
                return;
            }
            if (isDontAttack()) {
                return;
            }
            if (this.equipment == null || this.equipment[1] == null) {
                return;
            }
            if (selectedSkill == null) {
                return;
            }
            if (clone != null && !clone.isDead && clone.isNhanBan) {
                ms.reader().mark(10000);
                clone.attackCharacter(ms);
                ms.reader().reset();
            }
            ArrayList<Char> chars = new ArrayList<>();
            int num = 0;
            if (this.classId == 0 || this.classId == 1 || this.classId == 3 || this.classId == 5) {
                num = 40;
            }
            int num2 = this.x - selectedSkill.dx;
            int num3 = this.x + selectedSkill.dx;
            int num4 = this.y - selectedSkill.dy - num;
            int num5 = this.y + selectedSkill.dy;
            Char charFocus = null;
            Vector<Char> characterCanAttack = new Vector<>();
            // Char originChar = this.getOriginChar();
            while (ms.reader().available() > 0) {
                int id = ms.reader().readInt();
                Char _char = zone.findCharById(id);
                if (_char != null && _char != this && _char.hp > 0) {
                    if (!isMeCanAttackOtherPlayer(_char)) {
                        continue;
                    }
                    if (_char.isInvisible()) {
                        continue;
                    }
                    characterCanAttack.add(_char);
                }
            }
            if (!characterCanAttack.isEmpty()) {
                Char _char = characterCanAttack.get(0);

                int skillTemplateId = selectedSkill.template.id;
                if (skillTemplateId == 42) {
                    setXY(_char.x, _char.y);
                    zone.getService().teleport(this);
                    getService().resetPoint();
                    if (optionsSupportSkill[62] > 0) {
                        Effect eff = new Effect(18, optionsSupportSkill[62], 0);
                        _char.em.setEffect(eff);
                    }
                }
                if (num2 <= _char.x && _char.x <= num3 && num4 <= _char.y && _char.y <= num5) {
                    charFocus = _char;
                    chars.add(_char);
                }
            }
            byte maxFight = selectedSkill.maxFight;
            if (charFocus != null) {
                int lent = characterCanAttack.size();
                if (lent > maxFight) {
                    lent = maxFight;
                }
                for (int i = 0; i < lent; i++) {
                    Char _char = characterCanAttack.get(i);
                    if (_char != charFocus && charFocus.x - 100 <= _char.x && _char.x <= charFocus.x + 100 && charFocus.y - 50 <= _char.y && _char.y <= charFocus.y + 50) {
                        if (!chars.contains(_char)) {
                            chars.add(_char);
                        }
                    }
                }
            } else {
                ArrayList<Char> charAttackNoDame = new ArrayList<>();
                for (int i = 0; i < characterCanAttack.size(); i++) {
                    Char _char = characterCanAttack.get(i);
                    charAttackNoDame.add(_char);
                    zone.getService().attackCharacter(0, 0, _char);
                }
                zone.getService().setSkillPaint_2(charAttackNoDame, this, (byte) selectedSkill.template.id);
            }
            if (chars.isEmpty()) {
                return;
            }
            int manaUse = selectedSkill.manaUse;
            if (this.mp < manaUse && this.isHuman) {
                this.serverMessage("Không đủ MP để sử dụng");
                this.getService().updateMp();
                return;
            }
            if (this.isHuman) {
                addMp(-manaUse);
                getService().updateMp();
            }
            attackCharacter(chars);
        } catch (Exception ex) {
            Log.logException("Attack player err: ", Char.class, ex);
        }
    }

    public synchronized void addHp(int add) {
        if (add < 0 && isCool()) {
            add *= 2;
        }
        this.hp += add;
        if (this.hp > this.maxHP) {
            this.hp = this.maxHP;
        }
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public synchronized void addMp(int add) {
        if (add < 0 && isCool()) {
            add *= 2;
        }
        this.mp += add;
        if (this.mp > this.maxMP) {
            this.mp = this.maxMP;
        }
        if (this.mp < 0) {
            this.mp = 0;
        }
    }

    public void testInvite(Message msg) {
        try {
            if (!isHuman) {
                return;
            }
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (zone.tilemap.isNotPk()) {
                serverMessage("Không thể sử dụng tính năng này trong lôi đài.");
                return;
            }
            Char _char = this.zone.findCharById(msg.reader().readInt());
            if (_char == null) {
                serverDialog("Hiện người này không còn trong khu vực này.");
                return;
            }
            if (_char.isInvite()) {
                serverDialog("Tài khoản của người này không muốn nhận bất kì lời mời nào!");
                return;
            }
            PlayerInvite p = _char.invite.findCharInvite(Invite.TY_THI, this.id);
            if (p != null) {
                serverMessage("Không thể mời tỷ thí liên tục. Vui lòng thử lại sau 30s nữa.");
                return;
            }
            _char.invite.addCharInvite(Invite.TY_THI, this.id, 30);
            if (!_char.isTest) {
                _char.getService().testInvite(this.id);
            } else {
                serverDialog("Hiện tại người này đang tỷ thí với người khác.");
            }
        } catch (Exception e) {
            Log.logException("Test invite err: ", Char.class, e);
        }
    }

    public void testEnd(int num, Char pl) {
        if (num > 0) {
            this.hp = num;
        }
        pl.testCharId = 0;
        pl.isTest = false;
        pl.invite.removeInvite(Invite.TY_THI);
        this.invite.removeInvite(Invite.TY_THI);
        zone.getService().testEnd(this.id, pl.id, num);
        this.isTest = false;
        this.testCharId = 0;
    }

    public void testAccept(Message msg) {
        try {
            if (!isHuman) {
                return;
            }
            Char player = zone.findCharById(msg.reader().readInt());
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (player == null) {
                serverDialog("Hiện người này không còn trong khu vực này.");
                return;
            }
            if (player.isTest) {
                serverDialog("Hiện tại người này đang tỷ thí với người khác.");
                return;
            }
            PlayerInvite p = invite.findCharInvite(Invite.TY_THI, player.id);
            if (p != null) {
                player.testCharId = this.id;
                this.testCharId = player.id;
                this.isTest = true;
                player.isTest = true;
                zone.getService().testAccept(this.id, player.id);
            } else {
                serverMessage("Đã hết thời gian để chấp nhận.");
            }
        } catch (Exception e) {
        }
    }

    public void addCuuSat(Message msg) {
        try {
            if (!isHuman) {
                return;
            }
            if (isDead) {
                return;
            }
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if ((isVillage() || isSchool()) || mapId == 138 || mapId == 133 || mapId == 98 || mapId == 162) {
                serverMessage("Không được cừu sát ở trong trường hoặc làng.");
                return;
            }
            if (zone.tilemap.isNotPk()) {
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }

            if (killCharId == 0) {

                Char player = this.zone.findCharById(msg.reader().readInt());
                if (player != null && player.equipment != null && player.zone != null) {
                    // chống cừu sát
                    Equip equip = player.equipment[ItemTemplate.TYPE_THUNUOI];
                    if (equip != null) {
                        if (equip.id == ItemName.TUAN_LOC_PK && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                            serverMessage("Không thể cừu sát người này.");
                            return;
                        }
                        if (equip.id == ItemName.CAI_TRANG_ANIME && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                            serverMessage("Không thể cừu sát người này.");
                            return;
                        }
                        if (equip.id == ItemName.BI_RE_HANH_PK && (mapId < 163 || mapId > 165) && (mapId < 99 || mapId > 103) && (mapId < 134 || mapId > 137)) {
                            serverMessage("Không thể cừu sát người này.");
                            return;
                        }
                    }

                    this.killCharId = player.id;
                    this.isCuuSat = true;
                    player.getService().addCuuSat(id);
                    getService().meCuuSat(player.id);
                }
            }
        } catch (Exception ex) {
            Log.logException("Add Cừu sát err: ", Char.class, ex);
        }
    }

    public void clearCuuSat() {
        try {
            Char player = this.zone.findCharById(this.killCharId);
            if (player != null) {
                player.getService().clearCuuSat(id);
                getService().clearCuuSat(id);
            }
            this.killCharId = 0;
            this.isCuuSat = false;
        } catch (Exception e) {
        }
    }

    public void returnTownFromDead(Message ms) {
        if (!this.isDead) {
            return;
        }
        if (!isHuman) {
            return;
        }
        if (zone.tilemap.isNotReturnTown()) {
            serverMessage("Không thể sử dụng tính năng này.");
            return;
        }
        wakeUpFromDead();
        zone.returnTownFromDead(this);
        getService().loadInfo();
    }

    public void wakeUpFromDead(Message ms) {
        try {
            if (!this.isDead) {
                return;
            }
            if (!isHuman) {
                return;
            }
            if (zone.tilemap.isNotRivival()) {
                serverMessage("Không thể sử dụng tính năng này tại đây.");
                return;
            }
            if (user.gold < 1) {
                serverDialog("Cần 1 lượng để hồi sinh tại chỗ.");
                return;
            }
            addGold(-1);
            wakeUpFromDead();
        } catch (Exception ex) {
            Log.logException("Wake up form dead err: ", Char.class, ex);
        }
    }

    public void wakeUpFromDead() {
        try {
            if (!this.isDead) {
                return;
            }
            this.isDead = false;
            this.hp = this.maxHP;
            this.mp = this.maxMP;
            getService().meLive();
            getService().resetPoint();
            zone.getService().returnPointMap(this); /// fix chết trong map ninja tài năng
        } catch (Exception ex) {
            Log.logException("Wake up form dead err (2): ", Char.class, ex);
        }
    }

    public void updateWithBalanceMessage() {
        getService().loadInfo();
        if (this.getCoin() > 2000000000) {
            serverMessage("Bạn có " + NinjaUtils.getCurrency(this.getCoin()) + " xu trong hành trang");
        }
    }

    public void tayTiemNang(short npcId) {
        if ((npcId == 9 && getSys() == 1) || (npcId == 10 && getSys() == 2) || (npcId == 11 && getSys() == 3)) {
            if (this.tayTiemNang > 0) {
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
                if (this.level >= 130) {
                    this.potentialPoint += (this.level - 130) * 10;
                }
                if (this.level >= 145) {
                    this.potentialPoint += (this.level - 145) * 10;
                }
                this.potentialPoint += 10 * (this.limitTiemNangSo + this.limitBangHoa);
                this.potential[1] = 5;
                this.potential[2] = 5;
                if (this.classId == 1 || this.classId == 3 || this.classId == 5) {
                    this.potential[0] = 10;
                    this.potential[3] = 5;
                } else {
                    this.potential[0] = 5;
                    this.potential[3] = 10;
                }
                this.tayTiemNang--;
                setAbility();
                getService().updatePotential();
                if (tayTiemNang > 0) {
                    getService().npcChat(npcId, String.format("Ta đã giúp con tẩy điểm tiềm năng. Con vẫn có thể tẩy thêm được %d lần tẩy tiềm năng nữa.", tayTiemNang));
                } else {
                    getService().npcChat(npcId, "Ta đã giúp con tẩy điểm tiềm năng. Đây là lần cuối con được tẩy tiềm năng, hãy sử dụng cho thật tốt điểm tiềm năng nhé.");
                }
            } else {
                getService().npcChat(npcId, "Số lần tẩy điểm tiềm năng của con đã hết.");
            }
        } else {
            getService().npcChat(npcId, "Con không phải là học sinh của trường này, không thể tẩy điểm ở đây.");
        }
    }

    public boolean isCheckSkillPoint() {
        int skillPoint = (short) (this.level - 9);
        if (classId == 0) {
            skillPoint = 0;
        }
        skillPoint += this.limitKyNangSo + this.limitPhongLoi;
        int point = this.skillPoint;
        for (Skill skill : vSkill) {
            point += skill.point - 1;
        }

        return skillPoint >= point;
    }

    public boolean isCheckPotential() {
        int sum = (int) (NinjaUtils.sum(this.potential) + this.potentialPoint);
        int potentialPoint = 0;
        if (classId == 0) {
            potentialPoint = (this.level - 1) * 5 + 30;
            potentialPoint += 10 * (this.limitTiemNangSo + this.limitBangHoa);
            if (sum > potentialPoint) {
                potentialPoint = (this.level - 1) * 11 + 30;
                potentialPoint += 10 * (this.limitTiemNangSo + this.limitBangHoa);
            }
        } else {
            potentialPoint = (short) (this.level * 10);
            if (this.level >= 70) {
                potentialPoint += (this.level - 70) * 10;
            }
            if (this.level >= 80) {
                potentialPoint += (this.level - 80) * 10;
            }
            if (this.level >= 90) {
                potentialPoint += (this.level - 90) * 10;
            }
            if (this.level >= 100) {
                potentialPoint += (this.level - 100) * 10;
            }
            potentialPoint += 10 * (this.limitTiemNangSo + this.limitBangHoa);
            potentialPoint += 25;
        }
        return sum <= potentialPoint;
    }

    /// fix skill phân thân
    public void tayKyNang(short npcId) {
        if ((npcId == 9 && getSys() == 1) || (npcId == 10 && getSys() == 2) || (npcId == 11 && getSys() == 3)) {
            if (this.tayKyNang > 0) {
                this.skillPoint = (short) (this.level - 9);
                this.skillPoint += this.limitKyNangSo + this.limitPhongLoi;
                int size = vSkill.size();
                for (int i = 0; i < size; i++) {
                    Skill check = vSkill.get(i);
                    if (check.isCloneSkill()) {
                        continue;
                    }
                    Skill skill = GameData.getInstance().getSkill(this.classId, check.template.id,
                            1);
                    vSkill.set(i, skill);
                    if (skill.template.type == Skill.SKILL_AUTO_USE) {
                        for (int j = 0; j < vSupportSkill.size(); j++) {
                            if (vSupportSkill.get(j).template.id == check.template.id) {
                                vSupportSkill.set(j, skill);
                            }
                        }
                    } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK
                            || skill.template.type == Skill.SKILL_CLICK_LIVE
                            || skill.template.type == Skill.SKILL_CLICK_USE_BUFF
                            || skill.template.type == Skill.SKILL_CLICK_NPC)
                            && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 && skill.point > 0))) {

                        for (int j = 0; j < vSkillFight.size(); j++) {
                            if (vSkillFight.get(j).template.id == check.template.id) {
                                vSkillFight.set(j, skill);
                            }
                        }
                    }
                    for (int j = 0; j < this.onOSkill.length; j++) {
                        if (onOSkill[j] == check.template.id) {
                            onOSkill[j] = (byte) skill.template.id;
                        }
                    }
                    for (int j = 0; j < this.onKSkill.length; j++) {
                        if (onKSkill[j] == check.template.id) {
                            onKSkill[j] = (byte) skill.template.id;
                        }
                    }
                    if (onCSkill != null) {
                        for (int j = 0; j < this.onCSkill.length; j++) {
                            if (onCSkill[j] == check.template.id) {
                                onCSkill[j] = (byte) skill.template.id;
                            }
                        }
                    }
                }
                this.tayKyNang--;
                setAbility();
                getService().loadSkill();
                getService().callEffectMini((short) 21);
                byte type = 0;
                if (!isHuman) {
                    type = 1;
                }
                getService().sendSkillShortcut("OSkill", onOSkill, type);
                getService().sendSkillShortcut("KSkill", onKSkill, type);
                if (onCSkill != null) {
                    getService().sendSkillShortcut("CSkill", onCSkill, type);
                }
                if (tayKyNang > 0) {
                    getService().npcChat(npcId, String.format(
                            "Ta đã giúp con tẩy điểm kỹ năng. Con vẫn có thể tẩy thêm được %d lần tẩy kỹ năng nữa.",
                            tayKyNang));
                } else {
                    getService().npcChat(npcId,
                            "Ta đã giúp con tẩy điểm kỹ năng. Đây là lần cuối con được tẩy kỹ năng, hãy sử dụng cho thật tốt điểm kỹ năng nhé.");
                }
            } else {
                getService().npcChat(npcId, "Số lần tẩy điểm kỹ năng của con đã hết.");
            }
        } else {
            getService().npcChat(npcId, "Con không phải là học sinh của trường này, không thể tẩy điểm ở đây.");
        }
    }

    public void addSkill(Skill skill) {
        vSkill.add(skill);
        if (skill.template.type == Skill.SKILL_AUTO_USE) {
            vSupportSkill.add(skill);
        } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK || skill.template.type == Skill.SKILL_CLICK_LIVE || skill.template.type == Skill.SKILL_CLICK_USE_BUFF || skill.template.type == Skill.SKILL_CLICK_NPC) && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 && skill.point > 0))) {
            vSkillFight.add(skill);
        }
    }

    public void expandBag(Item item) {
        if (!isHuman) {
            warningClone();
            return;
        }
        short[] ids = new short[]{215, 229, 283, 801, 1094, 1158};
        byte[] numberCells = new byte[]{6, 6, 12, 24, 24, 18};
        if (numberUseExpanedBag < ids.length) {
            int i = ids[numberUseExpanedBag];
            int i2 = numberCells[numberUseExpanedBag];
            if (i == item.id) {
                this.numberCellBag += i2;
                this.numberUseExpanedBag++;
                Item[] bag = new Item[this.numberCellBag];
                System.arraycopy(this.bag, 0, bag, 0, this.bag.length);
                this.bag = bag;
                getService().expandBag(item);
                removeItem(item.index, item.getQuantity(), false);
            } else {
                String name = ItemManager.getInstance().getItemName(i);
                serverMessage("Hãy dùng sử dụng " + name);
            }
        } else {
            serverMessage("Bạn đã sử dụng tất cả các loại túi vải.");
        }
    }

    public void startDie() {
        try {
            zone.startDie(this);

            if (trade != null) {
                trade.closeUITrade();
            }
            if (escorted != null) {
                escortFailed();
            }
            if (escortedEvent != null) {
                escortEventFailed();
            }
            if (this.isTest && this.testCharId != 0) {
                Char player = this.zone.findCharById(this.testCharId);
                if (player != null) {
                    this.testEnd(1, player);
                }
            }
            if (taskId == TaskName.NV_THU_THAP_OAN_HON) {
                if (taskMain != null && taskMain.index == 1) {
                    int index = getIndexItemByIdInBag(ItemName.OAN_HON);
                    if (index != -1) {
                        removeItem(index, bag[index].getQuantity() / 2, true);
                    }
                    updateTaskCount(-taskMain.count / 2);
                }
            }
            this.isCuuSat = false;
            this.killCharId = 0;
            zone.getService().clearCuuSat(this);

            if (this.mob != null) {
                this.mob.die();
                this.zone.getService().attackMonster(this.mob.hp, false, this.mob);
                this.serverMessage("Quái vật quá mạnh, bạn không thể tiếp tục đánh do bị trọng thương");
                this.mob = null;
            }

            if (this.mapId == 138 || this.mapId == 137 || this.mapId == 136 || this.mapId == 135 || this.mapId == 134) {
                this.isDead = false;
                this.hp = this.maxHP;
                this.mp = this.maxMP;
                short[] xy = NinjaUtils.getXY(this.saveCoordinate);
                setXY(xy[0], xy[1]);
                changeMap(this.saveCoordinate);
                getService().loadInfo();
                return;
            }

            if (zone.tilemap.isChienTruong() && this.war != null) {
                this.isDead = false;
                this.hp = this.maxHP;
                this.mp = this.maxMP;
                int mapID = -1;
                short x = -1;
                short y = -1;
                if (this.faction == 0 || this.faction == 2) {
                    mapID = 118;
                    x = 104;
                    y = 336;
                }
                if (this.faction == 1) {
                    mapID = 119;
                    x = 104;
                    y = 240;
                }
                setXY(x, y);
                changeMap(mapID);
                return;
            }
            if (this.zone.map != null && this.zone.map.clanWar != null && zone.tilemap.isGTC() && zone.map.id != 117) {
                this.isDead = false;
                this.hp = this.maxHP;
                this.mp = this.maxMP;
                int mapID = -1;
                if (this.zone.map.clanWar.gt1.contains(this)) {
                    mapID = 98;
                } else {
                    mapID = 104;
                }
                short[] xy = NinjaUtils.getXY(mapID);
                this.setXY(xy);
                this.changeMap(mapID);

                return;
            }
            if (this.mapId == 162 || this.mapId == 163 || this.mapId == 164 || this.mapId == 165) {
                this.isDead = false;
                this.hp = this.maxHP;
                this.mp = this.maxMP;
                short[] xy = NinjaUtils.getXY(this.saveCoordinate);
                setXY(xy[0], xy[1]);
                changeMap(this.saveCoordinate);
                getService().loadInfo();
                return;
            }

            if (zone.tilemap.isFujukaSanctuary()) {

            }

            this.y = (short) (this.y / 24 * 24);
            if (this.y == 0) {
                while (this.zone.tilemap.tileTypeAt(this.x, this.y, TileMap.T_TOP)) {
                    this.y += 24;
                    if (this.y > zone.tilemap.pxh) {
                        break;
                    }
                }
            }
            while (!this.zone.tilemap.tileTypeAt(this.x, this.y, TileMap.T_TOP)) {
                this.y += 24;
                if (this.y > zone.tilemap.pxh) {
                    break;
                }
            }
            zone.getService().playerMove(this);
            getService().resetPoint();
            this.isDead = true;

            this.hp = 0;
            if (this.expDown > 0) {
                getService().meDieExpDown();
            } else {
                getService().meDie();
            }

            zone.getService().waitToDie(this);
            if (this.mapId == 169) {

                if (this.em.findByID(47) != null) {
                    wakeUpFromDead();
                }
            }

        } finally {
            if (arenaT != null) {
                boolean win = isBot();
                arenaT.setWin(win);
            }
            if (zone instanceof Gymnasium) {
                Gymnasium gymnasium = (Gymnasium) zone;
                gymnasium.setWin();
            }
        }
    }

    private boolean maxMap() {
        for (Zone z : zone.map.getZones()) {
            if (z.getNumberChar() < 24) {
                return false;
            }
        }
        return true;
    }

    private boolean maxZone(List<Zone> zones, int maxNinja, byte zoneId) {
        if (zones.get(zoneId).getNumberChar() >= maxNinja) {
            getService().endWait("Khu vực đã đầy!");
            return true;
        }
        return false;
    }

    public void changeZone(Message ms) {
        try {
            if (zone.tilemap.isNotChangeZone()) {
                serverDialog("Không thể chuyển khu");
                return;
            }
//            PlayerInvite p = this.invite.findCharInvite(Invite.CHANGE_ZONE, -1);
//            if (p != null) {
//                getService().endWait("Vui lòng chuyển khu lại sau " + p.time + " giây.");
//                getService().endDlg(true);
//                serverDialog("Vui lòng chuyển khu lại sau " + p.time + " giây.");
//                return;
//            }
            this.invite.addCharInvite(Invite.CHANGE_ZONE, -1, 6);
            byte zoneId = ms.reader().readByte();

            byte indexUI = ms.reader().readByte();
            Map map = zone.map;
            List<Zone> zones = map.getZones();
            if (zoneId < 0 || zoneId >= zones.size()) {
                return;
            }
            //maxnumberninja : map max number ninja = 90
            if (this.mapId == 141 || this.mapId == 142 || this.mapId == 143 || this.mapId == 3 || this.mapId == 39 || this.mapId == 28 || this.mapId == 163 || this.mapId == 164 || this.mapId == 165 || this.mapId == 134 || this.mapId == 135 || this.mapId == 136 || this.mapId == 137) {
                if (maxZone(zones, 90, zoneId)) {
                    return;
                }
            }
//            else if (this.mapId == 1){
//                if(maxZone(zones,123,zoneId)){
//                    return;
//                }
//            }
            else {
                if (maxZone(zones, 20, zoneId)) {
                    return;
                }
            }
            outZone();
            joinZone(this.mapId, zoneId, -1);
        } catch (Exception ex) {
            Log.logException("Change zone err: ", Char.class, ex);
        }
    }

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

    public void luckyDrawRefresh(Message ms) {
        if (!isHuman) {
            return;
        }
        if (typeVXMM == 2) {
            ChanLe lucky = ChanLeManager.getInstance().find(0);
            if (lucky != null) {
                lucky.show(this);
            }
            return;
        }
        byte typeLuck = typeVXMM;
        LuckyDraw lucky = LuckyDrawManager.getInstance().find(typeLuck);
        if (lucky != null) {
            lucky.show(this);
        }
    }

    public void moveTo(String name) {
        if (isDead) {
            return;
        }
        if (name.length() < 5) {
            return;
        }
        if (!isHuman) {
            warningClone();
            return;
        }
        if (zone.tilemap.isNotTeleport()) {
            serverMessage("Không thể sử dụng tính năng này.");
            return;
        }

        int index = getIndexItemByIdInBag(279);
        if (index == -1 && this.level < 10) {
            serverMessage("Để sử dụng chức năng này bạn phải có Vạn biến lệnh.");
            return;
        }
        if (name.equals(this.name)) {
            serverMessage("Không thể dịch chuyển đến chính mình.");
            return;
        }
        long now = System.currentTimeMillis();
        int second = (int) ((now - lastTimeTeleport) / 1000);
        if (second < 20) {
            serverMessage("Chỉ có thể sử dụng sau " + (20 - second) + " giây");
            return;
        }
        Char _char = Char.findCharByName(name);

        if (_char != null) {
            if (!_char.isHuman) {
                serverMessage("Người này đang trong chế độ thứ thân không thể dùng được chức năng này.");
                return;
            }
            if (_char.zone == null || _char.zone.tilemap.isNotTeleport()) {
                serverMessage("Không thể tới đây.");
                return;
            }
            try {
                if (_char.mapId == 0 || _char.mapId > 72) {
                    serverMessage("Không thể tới khu vực này.");
                    return;
                }
                //maxnumberninja
                if (_char.zone.getNumberChar() >= 24) {
                    serverMessage("Khu vực đã đầy!");
                    return;
                }
                setXY(_char.x, _char.y);
                if (_char.zone != this.zone) {
                    outZone();
                    joinZone(_char.mapId, _char.zone.id, -1);
                } else {
                    getService().endDlg(true);
                }
                lastTimeTeleport = now;
                zone.getService().teleport(this);
                getService().resetPoint();
            } catch (Exception ex) {
                Log.logException("Teleport err: ", Char.class, ex);
            }
        } else {
            serverMessage("Hiện tại người này không online hoặc không tồn tại.");
        }
    }


    public void input(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }

            short inputID = ms.reader().readShort();
            if (inputID == CMDInputDialog.BET_LUCKYDRAW) {
                betMessage(ms);
            } else if (inputID == CMDInputDialog.REFRESH_LUCKYDRAW) {
                luckyDrawRefresh(ms);
            } else if (inputID == CMDInputDialog.MENU_LUCKYDRAW) {
                if (user.activated == 0) {
                    service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                    return;
                }
                menus.clear();
                menus.add(new Menu(CMDMenu.VXMM_1, "Vòng xoay vip"));
                menus.add(new Menu(CMDMenu.VXMM_2, "Vòng xoay thường"));
                getService().openUIMenu();
            }
            {
                InputDialog input = getInput();
                try {
                    if (input == null) {
                        if (inputID == CMDInputDialog.TELEPORT) {
                            moveTo(ms.reader().readUTF());
                            ms.reader().reset();
                        }
                        return;
                    }
                    if (inputID != input.getId()) {
                        return;
                    }
                    input.setText(ms.reader().readUTF());
                    ms.reader().reset();
                    int itemID = -1;
                    switch (input.getId()) {
                        case CMDInputDialog.EXECUTE:
                            input.execute();
                            break;
                        case CMDInputDialog.TELEPORT:
                            moveTo(input.getText());
                            break;
                        case CMDInputDialog.VXMMNORMAL:
                            bet(input.getText(), 0);
                            break;
                        case CMDInputDialog.VXMMVIP:
                            bet(input.getText(), 1);
                            break;
                        case CMDInputDialog.THACH_DAU:
                            inviteArena(input.getText());
                            break;

                        case CMDInputDialog.INVITE_WAR_CLAN:
                            inviteArenaWarClan(input.getText());
                            break;
                        case CMDInputDialog.DAT_CUOC_GTC:
                            Datcuoc(input.getText());
                            break;
                        case CMDInputDialog.DAT_CUOC:
                            betArena(input.getText());
                            break;

                        case CMDInputDialog.TAO_GIA_TOC:
                            createClan(input.getText().trim());
                            break;

                        case CMDInputDialog.MA_QUA_TANG:
                            useGiftCode(input.getText().trim());
                            break;
                        default:
                            Log.debug("Input ID " + input.getId());
                            break;
                    }

                } finally {
                    setInput(null);
                }
            }
        } catch (Exception ex) {
            Log.logException("input err: ", Char.class, ex);
        }
    }

    public void betArena(String content) {
        Arena arena = (Arena) findWorld(World.ARENA);
        if (arena == null) {
            return;
        }
        if (this.user.activated == 0) {
            service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
            return;
        }
        if (this.group != null && !this.group.isCheckLeader(this)) {
            return;
        }
        int coin = 0;
        try {
            coin = Integer.parseInt(content);
        } catch (Exception e) {
            getService().npcChat(NpcName.KANATA, "Số tiền cược không hợp lệ.");
            return;
        }
        if (coin < 1000) {
            getService().npcChat(NpcName.KANATA, "Tối thiểu phải là 1.000xu.");
            return;
        }
        if (coin > 1000000) {  /// fix cược xu lôi đài
            getService().npcChat(NpcName.KANATA, "Chỉ được đặt tối đa 1.000.000 xu.");
            return;
        }
        if (coin > this.coin) {
            serverDialog(language.getString("NOT_ENOUGH_MONEY"));
            return;
        }
        int team = arena.getTeam(this);
        arena.setMoney(team, coin);
    }

    public void inviteArena(String name) {
        if (!Config.getInstance().isArena()) {
            serverDialog("Tính năng này đang được bảo trì, vui lòng quay lại sau.");
            return;
        }
        if (name.equals(this.name)) {
            getService().npcChat(NpcName.KANATA, "Không thể thách đấu bản thân.");
            return;
        }
        Char _char = zone.findCharName(name);
        if (_char != null) {
            try {
                if (_char.user.activated == 0) {
                    serverDialog("Tài khoản của người này chưa thể thực hiện chức năng này!");
                    return;
                }
                if (_char.isInvite()) {
                    serverDialog("Tài khoản của người này không muốn nhận bất kì lời mời nào!");
                    return;
                }
                if (this.group != null) {
                    if (!this.group.isCheckLeader(this)) {
                        getService().npcChat(NpcName.KANATA, "Chỉ có nhóm trưởng mới có thể mời thách đấu.");
                        return;
                    }
                    int index = this.group.getIndexById(_char.id);
                    if (index != -1) {
                        getService().npcChat(NpcName.KANATA, "Không thể mời người cùng nhóm.");
                        return;
                    }
                }
                if (_char.group != null) {
                    if (!_char.group.isCheckLeader(_char)) {
                        getService().npcChat(NpcName.KANATA, "Đối phương không phải nhóm trưởng.");
                        return;
                    }
                }
                PlayerInvite p = _char.invite.findCharInvite(Invite.PK, this.id);
                if (p != null) {
                    serverDialog("Không thể mời thách đấu liên tục. Vui lòng thử lại sau 30s nữa.");
                    return;
                }
                _char.getService().testDungeonInvite(this.id);
                _char.invite.addCharInvite(Invite.PK, this.id, 30);
                getService().npcChat(NpcName.KANATA, "Ta đã gởi lời mời thách đấu đến " + name);
            } catch (Exception e) {
                Log.logException("Lỗi thach đấu lôi đài:", Char.class, e);
            }
        } else {
            getService().npcChat(NpcName.KANATA, "Hiện tại " + name + " không có mặt ở đây.");
        }
    }

    public void inviteArenaWarClan(String name) {

        if (this.clan == null) {
            serverDialog("kẻ không bang không phái làm sao có quyền lên tiếng");
            return;
        }
        Member member = this.clan.getMemberByName(this.name);
        if (member != null) {
            int typeClan = member.getType();
            if (typeClan != Clan.TYPE_TOCTRUONG && typeClan != Clan.TYPE_TOCPHO) {
                if (typeClan != Clan.TYPE_TOCTRUONG) {
                    serverDialog("Bạn không phải tộc trưởng");
                    return;
                }
            }
        }
        if (name.equals(this.clan.name)) {
            getService().npcChat(NpcName.KAGAI, "Không thể thách đấu bản thân.");
            return;
        }
        Clan clan1 = Clan.mapClan.get(this.clan.name);
        Clan clan2 = Clan.mapClan.get(name);
        if (clan2 == null) {
            getService().npcChat(NpcName.KAGAI, "Gia tộc này không tồn tại");
            return;
        }
        if (clan2.level < 25) {
            this.service.npcChat(NpcName.KAGAI, "Gia tộc đối phương chưa đủ điều kiện để tham gia thách đấu.");
            return;
        }
        if (clan2.openGtc == 0) {
            this.service.npcChat(NpcName.KAGAI, "Gia tộc đối phương đã hết lượt tham gia gia tộc chiến.");
            return;
        }
        Char _char = zone.findCharName(clan2.main_name);
        if (_char != null) {
            try {


                if (clan2.gtcID != -1 && clan2.gtcClanName != null) {
                    this.service.npcChat(NpcName.KAGAI, "Gia tộc này đang có lời mời từ gia tộc khác");
                    return;
                }
                PlayerInvite p = _char.invite.findCharInvite(Invite.GIA_TOC_CHIEN, this.id);
                if (p != null) {
                    serverMessage("Không thể mời thách đấu liên tục. Vui lòng thử lại sau 30s nữa.");
                    return;
                }
                _char.getService().testWarClanInvite(this.id);
                _char.invite.addCharInvite(Invite.GIA_TOC_CHIEN, this.id, 30);
                WarClan giaTocChien = new WarClan();
                clan1.gtcID = giaTocChien.gtcID;
                clan1.gtcClanName = this.clan.name;
                clan2.gtcID = giaTocChien.gtcID;
                clan2.gtcClanName = name;
                getService().npcChat(NpcName.KAGAI, "Ta đã gởi lời mời thách đấu đến gia tộc" + name);
            } catch (Exception e) {
                Log.logException("Invite war clan err: ", Char.class, e);
            }
        } else {
            getService().npcChat(NpcName.KAGAI, "Hiện tại tộc trưởng gia tộc " + name + " không có mặt ở đây.");
        }
    }

    public void acceptInviteWarClan(Message ms) {
        try {
            int charId = ms.reader().readInt();
            Char _char = zone.findCharById(charId);
            Clan clan1 = Clan.mapClan.get(_char.clan.name);
            if (clan1 != null) {
                if (WarClan.gtcs.containsKey(clan1.gtcID)) {
                    WarClan giaTocChien = WarClan.gtcs.get(clan1.gtcID);
                    Clan clan2 = Clan.mapClan.get(clan1.gtcClanName);
                    String main2 = clan2.main_name;
                    Char _charTT = zone.findCharName(main2);
                    if (giaTocChien != null) {
                        if (_charTT != null) {
                            giaTocChien.clan1 = clan;
                            giaTocChien.clan2 = clan2;
                            this.mapBeforeEnterPB = this.mapId;
                            _charTT.mapBeforeEnterPB = _charTT.mapId;
                            this.setXY((short) 117, (short) 136);
                            this.changeMap(117);
                            _charTT.setXY((short) 117, (short) 136);
                            _charTT.changeMap(117);
                            giaTocChien.join();
                        } else {
                            this.service.serverMessage("Tộc trưởng đối phương không online.");
                        }
                    }
                } else {
                    this.service.serverMessage("Lời mời đã hết hạn.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Datcuoc(String str) {
        String check = str.replaceAll("\\s+", "");
        if (!NinjaUtils.isNumericLong(str) || check.equals("") || !NinjaUtils.isNumericInt(str)) {
            this.service.npcChat(NpcName.KAGAI, "Giá trị tiền cược nhập vào không đúng");
            return;
        }
        long tienCuoc = Long.parseLong(str);
        Clan clanManager = Clan.mapClan.get(this.clan.name);
        if (tienCuoc > clanManager.coin || clanManager.coin < 1000) {
            this.service.npcChat(NpcName.KAGAI, "Gia tộc của con không đủ ngân sách để đặt cược.");
            return;
        }
        if (tienCuoc < 10000000 || tienCuoc > 100000000) {
            this.service.npcChat(NpcName.KAGAI, "Tiền cược phải nằm trong khoảng từ 10tr đến 100tr xu");
            return;
        }

        WarClan gtc = null;
        if (clanManager.gtcID != -1) {
            if (WarClan.gtcs.containsKey(clanManager.gtcID)) {
                gtc = WarClan.gtcs.get(clanManager.gtcID);
            }
        }
        if (gtc != null) {
            if (gtc.clan1.id == clanManager.id) {
                if (gtc.tienCuoc2 != 0 && gtc.tienCuoc2 != tienCuoc) {
                    this.service.npcChat(NpcName.KAGAI, "Gia tộc đối thủ của con đã đặt cược " + NinjaUtils.getCurrency(gtc.tienCuoc2) + " xu con hãy đặt lại đi!");
                    return;
                }
                if (gtc.tienCuoc1 != 0) {
                    this.service.npcChat(NpcName.KAGAI, "Gia tộc của con đã đặt cược trước đó rồi.");
                    return;
                }

                gtc.tienCuoc1 = tienCuoc;
                clanManager.coin -= tienCuoc;
                this.service.npcChat(NpcName.KAGAI, "Con đã đặt cược " + NinjaUtils.getCurrency(gtc.tienCuoc1) + " xu");
                if (gtc.gt2.size() > 0) {
                    for (int i = 0; i < gtc.gt2.size(); i++) {
                        gtc.gt2.get(i).service.serverMessage("Gia tộc " + clanManager.name + " đã đặt cược " + NinjaUtils.getCurrency(gtc.tienCuoc1) + " xu.");
                    }
                }


            } else if (gtc.clan2.id == clanManager.id) {
                if (gtc.tienCuoc1 != 0 && gtc.tienCuoc1 != tienCuoc) {
                    this.service.npcChat(NpcName.KAGAI, "Gia tộc đối thủ của con đã đặt cược " + NinjaUtils.getCurrency(gtc.tienCuoc1) + " xu con hãy đặt lại đi!");
                    return;
                }
                if (gtc.tienCuoc2 != 0) {
                    this.service.npcChat(NpcName.KAGAI, "Con đã đặt cược trước đó rồi.");
                    return;
                }

                gtc.tienCuoc2 = tienCuoc;
                clanManager.coin -= tienCuoc;
                this.service.npcChat(NpcName.KAGAI, "Con đã đặt cược " + gtc.tienCuoc2 + " xu");
                if (gtc.gt1.size() > 0) {
                    for (int i = 0; i < gtc.gt1.size(); i++) {
                        gtc.gt1.get(i).service.serverMessage("Gia tộc " + clanManager.name + " đã được cược " + NinjaUtils.getCurrency(gtc.tienCuoc2) + " xu.");
                    }
                }
            }

            if (gtc.tienCuoc1 != 0 && gtc.tienCuoc2 != 0 && gtc.tienCuoc1 == gtc.tienCuoc2 && gtc.gt1.size() > 0 && gtc.gt2.size() > 0) {
                gtc.invite();
            }
        } else {
        }

    }

    public void inviteTerritory(Message ms) {
        try {
            Territory territory = (Territory) findWorld(World.TERRITORY);
            if (territory == null) {
                return;
            }
            if (!isHuman) {
                return;
            }
            String charName = ms.reader().readUTF();
            Member member = this.clan.getMemberByName(charName);
            if (member != null && member.isOnline()) {
                if (!territory.started) {
                    territory.addGuest(member.getChar().id);
                    member.getChar().serverMessage("Bạn được " + this.name + " mời bạn vào lãnh địa gia tộc, hãy gặp NPC Kanata để điểm danh");
                } else {
                    serverDialog("Lãnh địa đã bắt đầu, không thể mời thêm.");
                }
            } else {
                serverDialog("Thành viên không hoạt động.");
            }
        } catch (Exception e) {
            Log.logException("Invite LDGT err: ", Char.class, e);
        }
    }

    public void inviteMemberGTC(Message ms) {
        try {
            WarClan war = this.zone.map.clanWar;
            if (war == null) {
                return;
            }
            if (!isHuman) {
                return;
            }
            String charName = ms.reader().readUTF();
            Member member = this.clan.getMemberByName(charName);
            if (member != null && member.isOnline()) {
                if (!war.start) {
                    member.getChar().getService().inviteMemberGtc(this.name);
                } else {
                    serverDialog("Gia tộc chiến đã bắt đầu, không thể mời thêm.");
                }
            } else {
                serverDialog("Thành viên không hoạt động.");
            }
        } catch (Exception e) {
            Log.logException("InVite member GTC err: ", Char.class, e);
        }
    }

    public void memberAppectGTC() {
        try {
            Clan clanManager = this.clan;
            WarClan gtc = null;
            if (clanManager.gtcID != -1) {
                if (WarClan.gtcs.containsKey(clanManager.gtcID)) {
                    gtc = WarClan.gtcs.get(clanManager.gtcID);
                }
            }
            if (gtc == null) {
                serverDialog("Gia tộc chiến đã kết thúc, không thể vào.");
                return;
            }
            if (!isHuman) {
                return;
            }
            if (!gtc.start) {
                Member _charTT = this.clan.getMemberByName(this.clan.main_name);
                this.mapBeforeEnterPB = this.mapId;
                this.gtcId = clanManager.gtcID;
                this.pheGtc = _charTT.getChar().pheGtc;
                this.isRewardedGtc = false;
                this.pointGTC = 0;
                this.setTypePk((byte) _charTT.getChar().pheGtc);
                if (_charTT.getChar().pheGtc == -1) {
                    return;
                }
                if (_charTT.getChar().pheGtc == Char.PK_PHE1) {
                    short[] xy = NinjaUtils.getXY(98);
                    this.setXY(xy);
                    this.changeMap(98);
                } else {
                    short[] xy = NinjaUtils.getXY(104);
                    this.setXY(xy);
                    this.changeMap(104);
                }
            } else {
                serverDialog("Gia tộc chiến đã bắt đầu, không thể mời thêm.");
            }

        } catch (Exception e) {
            Log.logException("Member appect GTC Err: ", Char.class, e);
        }
    }

    public void clanUseItem(Message ms) {
        try {
            if (this.clan == null) {
                return;
            }
            if (!isHuman) {
                return;
            }
            byte indexUI = ms.reader().readByte();
            if (indexUI < 0 || indexUI >= this.clan.getItems().length) {
                return;
            }
            Item item = this.clan.getItems()[indexUI];
            if (item == null) {
                return;
            }

            if (item.template.id == ItemName.LENH_BAI_GIA_TOC) {
                if (this.clan != null) {
                    if (this.clan.getOpenDun() == 0) {
                        if (this.clan.getUseCard() == 0) {
                            serverMessage("Đã hết lượt sử dụng.");
                            return;
                        }
                        Territory territory = (Territory) findWorld(World.TERRITORY);
                        if (territory != null && !territory.isClosed()) {
                            serverMessage("Lãnh địa hiện tại vẫn chưa kết thúc.");
                            return;
                        }
                        removeWorld(World.TERRITORY);
                        this.clan.openDun++;
                        this.clan.use_card--;
                        serverMessage("Gia tộc nhận được thêm 1 lần lãnh địa gia tộc.");
                        this.clan.removeItem(item, 1);
                        clan.getClanService().requestClanItem();
                    } else {
                        serverMessage("Gia tộc vẫn còn lượt vào lãnh địa.");
                    }
                }
            }

        } catch (Exception e) {
            Log.logException("err: ", Char.class, e);
        }
    }

    public void acceptInviteTestDun(Message ms) {
        try {
            if (!isHuman) {
                return;
            }
            int charId = ms.reader().readInt();
            if (invite.findCharInvite(Invite.PK, charId) != null) {
                Char _char = zone.findCharById(charId);
                if (_char != null) {
                    int mapBeforeEnterPB = this.mapId;
                    Arena arena = new Arena();
                    if (this.group != null) {
                        List<Char> chars = this.group.getChars();
                        for (Char _char2 : chars) {
                            _char2.mapBeforeEnterPB = mapBeforeEnterPB;
                            _char2.outZone();
                            _char2.addWorld(arena);
                            _char2.joinZone(-1, -1, 1);
                        }
                    } else {
                        this.mapBeforeEnterPB = mapBeforeEnterPB;
                        outZone();
                        this.addWorld(arena);
                        joinZone(-1, -1, 1);
                    }
                    if (_char.group != null) {
                        List<Char> chars = _char.group.getChars();
                        for (Char _char2 : chars) {
                            _char2.mapBeforeEnterPB = mapBeforeEnterPB;
                            _char2.outZone();
                            _char2.addWorld(arena);
                            _char2.joinZone(-1, -1, 2);
                        }
                    } else {
                        _char.mapBeforeEnterPB = mapBeforeEnterPB;
                        _char.outZone();
                        _char.addWorld(arena);
                        _char.joinZone(-1, -1, 2);
                    }
                    arena.isTwoTeamsEtered = true;
                } else {
                    serverDialog("Hiện tại người này không có mặt ở đây.");
                }
            } else {
                serverDialog("Đã hết thời gian để chấp nhận yêu cầu.");
            }
        } catch (Exception e) {
            Log.logException("apect lôi Đài err: ", Char.class, e);
        }
    }

    public void requestMatchInfo(Message ms) {
        try {
            if (!isSchool()) {
                return;
            }
            if (!isHuman) {
                return;
            }
            byte id = ms.reader().readByte();
            Arena arena = Arena.getArenaByID(id);
            if (arena != null) {
                if (arena.isOpened) {
                    this.mapBeforeEnterPB = this.mapId;

                    if (NinjaUtils.nextInt(2) == 0) { // fix die lôi đài
                        setXY((short) 130, (short) 336);
                    } else {
                        setXY((short) 635, (short) 336);
                    }

                    outZone();
                    addWorld(arena);
                    joinZone(-1, -1, 3);
                } else {
                    serverDialog("Trận đấu chưa bắt đầu.");
                }
            } else {
                serverDialog("Không tìm thấy trận đấu này.");
            }
        } catch (Exception e) {
            Log.logException("Request TDB err: ", Char.class, e);
        }
    }

    public void useGiftCode(String code) {
        GiftCode.getInstance().use(this, code);
    }

    public void createClan(String name) {
        if (this.user.activated == 0) {
            serverMessage("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
            return;
        }
        if (this.clan == null) {
            try {
                if (user.gold < 10000) {
                    getService().npcChat(NpcName.KANATA, "Bạn không đủ 10000 lương.");
                    return;
                }
//                if (this.level < 40) {
//                    getService().npcChat(NpcName.KANATA, "Yêu cầu trình độ đạt cấp 40.");
//                    return;
//                }
                if (name.equals("")) {
                    getService().npcChat(NpcName.KANATA, "Tên không hợp lệ.");
                    return;
                }

                if (name.length() < 5 || name.length() > 10) {
                    getService().npcChat(NpcName.KANATA, "Con hãy chọn cái tên Từ 5-10 ký tự.");
                    return;
                }
                Pattern p = Pattern.compile("^[a-z0-9]+$");
                Matcher m1 = p.matcher(name);
                if (!m1.find()) {
                    getService().npcChat(NpcName.KANATA, "Tên tài khoản có kí tự lạ.");
                    return;
                }
                ClanDAO clanDAO = Clan.getClanDAO();
                if (clanDAO.checkExist(name)) {
                    getService().npcChat(NpcName.KANATA, "Tên gia tộc đã tồn tại.");
                    return;
                }
                addGold(-10000);
                Clan clan = new Clan();
                clan.setName(name);
                clan.setAssistName("");
                clan.setMainName(this.name);
                clan.setAlert("");
                clan.setLevel((byte) 1);
                clan.setCoin(0);
                clan.setItemLevel((byte) 0);
                clan.setExp(0);
                clan.setRegDate(new Date());
                clan.setOpenDun((byte) 1);
                clan.setOpenGtc((byte) 2);
                clan.setUseCard(1);
                clan.writeLog(clan.getAssistName(), Clan.CREATE_CLAN, clan.getCoin());
                this.clan = clan;

                Member mem = Member.builder().classId(this.classId).level(this.level).type(Clan.TYPE_TOCTRUONG).name(this.name).pointClan(0).pointClanWeek(0).build();
                mem.setChar(this);
                mem.setOnline(true);
                clan.memberDAO.save(mem);
                Clan.getClanDAO().save(clan);
                getService().createClan();
                zone.getService().acceptInviteClan(this);

                getService().npcChat(NpcName.KANATA, "Gia tộc của con đã được thành lập.");
            } catch (Exception ex) {
                Log.logException("create clan err: ", Char.class, ex);
                serverDialog(ex.getMessage());
            }
        } else {
            getService().npcChat(NpcName.KANATA, "Con đang ở trong 1 gia tộc nào đó.");
        }
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

    public void bet(String money, int type) {
        try {
            if (money == null || money.equals("")) {
                return;
            }
            Pattern p = Pattern.compile("^[0-9]+$");
            Matcher m1 = p.matcher(money);
            if (!m1.find() || money.length() > 8) {
                serverMessage("Số xu không hợp lệ!");
                return;
            }
            switch (type) {
                case 0:
                    typeVXMM = LuckyDrawManager.NORMAL;
                    break;

                case 1:
                    typeVXMM = LuckyDrawManager.VIP;
                    break;
            }
            LuckyDraw lucky = LuckyDrawManager.getInstance().find(typeVXMM);
            lucky.show(this);
            lucky.join(this, Integer.parseInt(money));
            lucky.show(this);
        } catch (Exception ex) {
            Log.logException("err: ", Char.class, ex);
        }
    }

    public void kickOption(Equip equip, int maxKick) {
        int num = 0;
        if (equip != null && equip.options != null) {
            for (int i = 0; i < equip.options.size(); i++) {
                ItemOption itemOption = equip.options.get(i);
                itemOption.active = 0;
                if (itemOption.optionTemplate.type == 2) {
                    if (num < maxKick) {
                        itemOption.active = 1;
                        num++;
                    }
                } else if (itemOption.optionTemplate.type == 3 && equip.upgrade >= 4) {
                    itemOption.active = 1;
                } else if (itemOption.optionTemplate.type == 4 && equip.upgrade >= 8) {
                    itemOption.active = 1;
                } else if (itemOption.optionTemplate.type == 5 && equip.upgrade >= 12) {
                    itemOption.active = 1;
                } else if (itemOption.optionTemplate.type == 6 && equip.upgrade >= 14) {
                    itemOption.active = 1;
                } else if (itemOption.optionTemplate.type == 7 && equip.upgrade >= 16) {
                    itemOption.active = 1;
                }
            }
        }
    }

    public void updateKickOption() {
        int num = 2;
        int num2 = 2;
        int num3 = 2;
        if (this.equipment[0] == null) {
            num--;
        }
        if (this.equipment[6] == null) {
            num--;
        }
        if (this.equipment[5] == null) {
            num--;
        }
        kickOption(this.equipment[0], num);
        kickOption(this.equipment[6], num);
        kickOption(this.equipment[5], num);
        if (this.equipment[2] == null) {
            num2--;
        }
        if (this.equipment[8] == null) {
            num2--;
        }
        if (this.equipment[7] == null) {
            num2--;
        }
        kickOption(this.equipment[2], num2);
        kickOption(this.equipment[8], num2);
        kickOption(this.equipment[7], num2);
        if (this.equipment[4] == null) {
            num3--;
        }
        if (this.equipment[3] == null) {
            num3--;
        }
        if (this.equipment[9] == null) {
            num3--;
        }
        if (this.equipment[1] != null) {
            if (this.equipment[1].sys == this.getSys()) {
                if (this.equipment[1].options != null) {
                    for (int i = 0; i < this.equipment[1].options.size(); i++) {
                        ItemOption itemOption = this.equipment[1].options.get(i);
                        if (itemOption.optionTemplate.type == 2) {
                            itemOption.active = 1;
                        }
                    }
                }
            } else if (this.equipment[1].options != null) {
                for (int j = 0; j < this.equipment[1].options.size(); j++) {
                    ItemOption itemOption2 = this.equipment[1].options.get(j);
                    if (itemOption2.optionTemplate.type == 2) {
                        itemOption2.active = 0;
                    }
                }
            }
        }
        kickOption(this.equipment[4], num3);
        kickOption(this.equipment[3], num3);
        kickOption(this.equipment[9], num3);
    }

    public void setAbility() {
        if (abilityStrategy != null) {
            abilityStrategy.setAbility(this);
            if (zone != null) {
                zone.getService().refreshHP(this);
            }
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

    public void betMessage(Message ms) {
        try {
            String money = ms.reader().readUTF();
            byte typeLuck = ms.reader().readByte();
            if (money.isEmpty()) {
                return;
            }
            Pattern p = Pattern.compile("^[0-9]+$");
            Matcher m1 = p.matcher(money);
            if (!m1.find() || money.length() > 8) {
                serverMessage("Số xu không hợp lệ!");
                return;
            }


            if (typeVXMM == 2) { // chanle
                ChanLe lucky = ChanLeManager.getInstance().find(0);
                if (lucky.isCheck(this.id)) {
                    serverMessage("Bạn không thể tham gia thêm, vui lòng chờ ván mới");
                    return;
                } else {
                    menus.clear();
                    menus.add(new Menu(CMDMenu.EXECUTE, "CHAN", () -> {
                        lucky.join(this, Integer.parseInt(money), 0);
                        lucky.show(this);
                    }));
                    menus.add(new Menu(CMDMenu.EXECUTE, "LE", () -> {
                        lucky.join(this, Integer.parseInt(money), 1);
                        lucky.show(this);
                    }));
                    getService().openUIMenu();
                }
                return;
            }


            LuckyDraw lucky = LuckyDrawManager.getInstance().find(typeLuck);
            lucky.join(this, Integer.parseInt(money));
            lucky.show(this);
        } catch (Exception ex) {
            Log.logException("betMessage err: ", Char.class, ex);
        }
    }

    // fix 5 skill
    public long lastTimeChangelSkill;
    private int idSkillHoTro;
    private long timeCanNhanQua;
    private long timeCanGiveaways;

    private boolean isSkillHoTro(int id) {
        int[] idSkill = new int[]{6, 13, 15, 22, 31, 33, 47, 51, 52, 58, 67, 68, 69, 70, 71, 72, 4, 40, 49, 2, 8, 11, 17, 20, 26, 29, 35, 38, 44, 48, 53, 55, 56, 57, 59, 60};
        for (int j : idSkill) {
            if (id == j) {
                return true;
            }
        }
        return false;
    }

    public void selectSkill(Message ms) {
        try {
            short skillTemplateId = ms.reader().readShort();
            long now = System.currentTimeMillis();
            boolean isSelectSkill = ((now - this.lastTimeChangelSkill) >= 1000);
            if (isSkillHoTro(idSkillHoTro) || isSkillHoTro(skillTemplateId)) {
                selectSkill(skillTemplateId);
                idSkillHoTro = skillTemplateId;
            } else {
                if (isSelectSkill) {
                    this.lastTimeChangelSkill = now;
                    selectSkill(skillTemplateId);
                    onCSkill[0] = (byte) skillTemplateId; // ecuong fix skill out
                    idSkillHoTro = skillTemplateId;
                } else {
                }
            }

        } catch (Exception ex) {
        }
    }

    public void selectSkill(short templateId) {
        for (Skill my : vSkillFight) {
            if (my.template.id == templateId) {
                selectedSkill = my;
                setAbility();
                return;
            }
        }
        for (Skill my : vSkillFight) {
            if (my.template.type == 1) {
                selectedSkill = my;
                setAbility();
                return;
            }
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
                            Char o = this;
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
//                                                updateTaskPickItem(itm);
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
                                zone.getService().loadHP(this);
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
                                        Char o = this;
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
                                        zone.getService().refreshHP(this);
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
                                zone.getService().pickItem(this, itemMap);
                                zone.removeItem(itemMap);
                                updateTaskPickItem(item);
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
                                                        _char.updateTaskPickItem(item);

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
                    zone.getService().throwItem(this, indexUI, itemMap);
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

    public void rewardCT() {
        if (war != null && !this.zone.tilemap.isGTC()) {
            war.reward(this);
        }
        if (this.zone.map.clanWar != null) {
            this.zone.map.clanWar.reward(this);
        }
    }

    public void sendClanItem(Message ms) {
        try {
            if (this.clan == null) {
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            byte index = ms.reader().readByte();
            String name = ms.reader().readUTF();
            Item[] items = this.clan.getItems();
            if (items == null || index > items.length || items[index] == null) {
                return;
            }
            Member member = this.clan.getMemberByName(this.name);
            if (member != null) {
                int typeClan = member.getType();
                if (typeClan != Clan.TYPE_TOCTRUONG && typeClan != Clan.TYPE_TOCPHO) {
                    serverDialog("Bạn không phải tộc trưởng hoặc tộc phó.");
                    return;
                }
                Member mem = this.clan.getMemberByName(name);
                if (mem == null) {
                    serverDialog("Người này không trong gia tộc.");
                    return;
                }
                Char _char = mem.getChar();
                if (_char != null) {
                    int slot = _char.getSlotNull();
                    if (slot == 0) {
                        warningBagFull();
                        return;
                    }
                    Item item = Converter.getInstance().newItem(items[index]);
                    if (item.hasExpire()) {
                        item.expire += System.currentTimeMillis();
                    }
                    item.setQuantity(1);
                    if (item.template.isTypeBody()) {
                        int[][] max = new int[item.options.size()][2];
                        for (int i = 0; i < max.length; i++) {
                            ItemOption o = item.options.get(i);
                            max[i][0] = o.optionTemplate.id;
                            max[i][1] = o.param;
                        }
                        item.options.clear();
                        int[][] min = NinjaUtils.getOptionShop(max);
                        for (int a = 0; a < max.length; a++) {
                            int templateId = max[a][0];
                            int param = NinjaUtils.nextInt(min[a][1], max[a][1]);
                            item.options.add(new ItemOption(templateId, param));
                        }
                    }
                    item.isLock = true;
                    _char.addItemToBag(item);
                    clan.removeItem(items[index], 1);
                    clan.getClanService().requestClanItem();
                    serverDialog("Phát vật phẩm cho " + name + " thành công.");
                } else {
                    serverDialog("Người này không online.");
                }
            }
        } catch (Exception ex) {
            Log.logException("Send Clan item err: ", Char.class, ex);
        }
    }

//    public void updateProtect(Message ms) {
//        try {
//            int index = ms.reader().readInt();
//            if (this.yen < 10000) {
//                serverDialog("Bạn không đủ 10,000 yên");
//                return;
//            }
//            this.passProtech = index;
//            this.typeProtect = 1;
//            this.getService().meLoadActive();
//            DbManager.executeUpdate("UPDATE `players` SET `passProtect` = ?, `typeProtect` = ? WHERE `id`=?;", this.passProtech, this.typeProtect, this.id);
//            this.addYen(-10000);
//            serverDialog("Bạn đã cài mã bảo vệ thành công");
//
//        } catch (Exception e) {
//
//        }
//
//    }

    public void updatePassProtect(Message ms) {
        try {
            int pass_old = ms.reader().readInt();
            int pass_new = ms.reader().readInt();
            if (this.passProtech != pass_old) {
                serverDialog("Mật kẩu bảo vệ không chính xác");
                return;
            }
            if (this.passProtech == pass_new) {
                serverDialog("Mật kẩu mới không được giống mật khẩu cũ");
                return;
            }

            this.passProtech = pass_new;
            this.typeProtect = 1;
            this.getService().meLoadActive();
            DbManager.executeUpdate("UPDATE `players` SET `passProtect` = ?, `typeProtect` = ? WHERE `id`=?;", this.passProtech, this.typeProtect, this.id);
            serverDialog("Bạn đã đổi mã bảo vệ thành công");
        } catch (Exception e) {

        }

    }

    public void openProtect(Message ms) {
        try {
            int pass_old = ms.reader().readInt();
            if (this.passProtech != pass_old) {
                serverDialog("Mật kẩu bảo vệ không chính xác");
                return;
            }
            this.typeProtect = 2;
            this.getService().meLoadActive();
            serverDialog("Bạn đã mở khóa mã bảo vệ thành công");
        } catch (Exception e) {

        }

    }

    public void clearProtect(Message ms) {
        try {
            int pass_old = ms.reader().readInt();
            if (this.passProtech != pass_old) {
                serverDialog("Mật kẩu bảo vệ không chính xác");
                return;
            }
            this.typeProtect = 0;
            this.passProtech = 0;
            this.getService().meLoadActive();
            DbManager.executeUpdate("UPDATE `players` SET `passProtect` = ?, `typeProtect` = ? WHERE `id`=?;", this.passProtech, this.typeProtect, this.id);
            serverDialog("Bạn đã xóa mã bảo vệ thành công");
        } catch (Exception e) {

        }

    }

    public void outputCoinClan(Message ms) {
        try {
            if (clan == null) {
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            int coin = ms.reader().readInt();
            serverMessage("Tính năng đang bảo trì.");
        } catch (Exception ex) {
            Log.logException("Out put coin clan err: ", Char.class, ex);
        }
    }

    public void inputCoinClan(Message ms) {
        try {
            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (clan == null) {
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            int coin = ms.reader().readInt();
            if (coin < 1000 || coin > 100000000) {
                serverDialog("Vui lòng nhập trong khoảng từ 1.000xu đến 100.000.000xu.");
                return;
            }
            if (coin + clan.coin > 2000000000) {
                serverDialog("Xu clan không được quá 2 tỷ.");
                return;
            }
            if (coin > this.coin) {
                serverDialog("Bạn không đủ xu.");
                return;
            }
            clan.writeLog(this.name, Clan.MOVE_INPUT_MONEY, coin);
            clan.addCoin(coin);
            this.coin -= coin;
            getService().addCoinClan(this.getCoinInt());
            clan.getClanService().requestClanInfo();
            clan.getClanService().writeLog();
            clan.getClanService().serverMessage(this.name + " đóng góp vào ngân quỹ " + NinjaUtils.getCurrency(coin) + " xu");
        } catch (Exception ex) {
            Log.logException("Input coin clan err: ", Char.class, ex);
        }
    }

    public void moveOutClan(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            String name = ms.reader().readUTF();
            if (name.equals(this.name)) {
                serverDialog("Bạn không thể tự trục xuất chính mình.");
                return;
            }
            if (this.clan != null) {
                int cType = this.clan.getMemberByName(this.name).getType();
                if (cType == Clan.TYPE_TOCTRUONG || cType == Clan.TYPE_TOCPHO) {
                    Member mem = this.clan.getMemberByName(name);
                    if (mem == null) {
                        serverMessage("Thành viên này không tồn tại.");
                        return;
                    }
                    int memType = mem.getType();
                    if (memType >= cType) {
                        serverMessage("Bạn không có quyền trục xuất người này.");
                        return;
                    }

                    int coin = 10000;
                    switch (memType) {

                        case Clan.TYPE_TOCPHO:
                            coin = 100000;
                            break;

                        case Clan.TYPE_TRUONGLAO:
                            coin = 50000;
                            break;

                        case Clan.TYPE_UUTU:
                            coin = 20000;
                            break;
                    }
                    if (this.clan.getCoin() < coin) {
                        serverMessage("Ngân quỹ không đủ.");
                        return;
                    }
                    clan.memberDAO.delete(mem);
                    clan.writeLog(name, Clan.MOVE_OUT_MEM, coin);
                    this.clan.addCoin(-coin);
                    clan.getClanService().requestClanMember();
                    Clan.getClanDAO().update(clan);
                    Char _char = Char.findCharByName(name);
                    if (_char != null) {
                        _char.clan = null;
                        _char.zone.getService().moveOutClan(_char);
                        _char.lastTimeOutClan = System.currentTimeMillis();
                    }
                    clan.getClanService().writeLog();
                    this.clan.getClanService().serverMessage(this.name + " đã trục xuất " + name + ", ngân quỹ trừ " + NinjaUtils.getCurrency(coin) + " xu");
                } else {
                }
            } else {
                serverMessage("Bạn không có gia tộc");
            }
        } catch (Exception ex) {
            Log.logException("Move Out clan err: ", Char.class, ex);
        }
    }

    public void outClan() {
        if (this.clan != null) {
            if (!isHuman) {
                warningClone();
                return;
            }
            Member mem = this.clan.getMemberByName(this.name);
            if (mem != null) {
                int cType = mem.getType();
                if (cType != Clan.TYPE_TOCTRUONG) {
                    try {
                        int coin = 10000;
                        switch (cType) {
                            case Clan.TYPE_TOCPHO:
                                coin = 100000;
                                break;

                            case Clan.TYPE_TRUONGLAO:
                                coin = 50000;
                                break;

                            case Clan.TYPE_UUTU:
                                coin = 20000;
                                break;
                        }
                        if (this.coin < coin) {
                            serverMessage("Bạn không đủ xu.");
                            return;
                        }
                        addCoin(-coin);
                        this.clan.memberDAO.delete(mem);
                        Clan.getClanDAO().update(this.clan);
                        zone.getService().moveOutClan(this);
                        clan.getClanService().requestClanMember();
                        this.clan.getClanService().serverMessage(this.name + " đã rời gia tộc");
                        this.clan = null;
                        this.lastTimeOutClan = System.currentTimeMillis();
                        serverMessage("Bạn đã rời gia tộc thành công.");
                    } catch (Exception ex) {
                        Log.logException("out clan err: ", Char.class, ex);
                    }
                } else {
                    serverMessage("Bạn là tộc trưởng nên không thể rời.");
                }
            }
        } else {
            serverMessage("Bạn không trong gia tộc.");
        }
    }

    public void clanUpLevel() {
        if (!isHuman) {
            warningClone();
            return;
        }
        if (this.clan != null) {
            int cType = this.clan.getMemberByName(this.name).getType();
            if (cType == Clan.TYPE_TOCTRUONG || cType == Clan.TYPE_TOCPHO) {
                int expNext = clan.getExpNext();
                int coin = clan.getCoin();
                int coinUp = clan.getCoinUp();
                if (clan.exp >= expNext) {
                    if (coin >= coinUp) {
                        clan.exp -= expNext;
                        clan.level++;
                        clan.writeLog(name, Clan.UP_LEVEL, -coinUp);
                        clan.addCoin(-coinUp);
                        clan.getClanService().requestClanInfo();
                        serverMessage("Gia tộc được lên cấp " + clan.level);
                    } else {
                        serverMessage("Ngân quỹ gia tộc không đủ");
                    }
                } else {
                    serverMessage("Không đủ kình nghiệm năng cấp gia tộc");
                }
            } else {
                serverMessage("Bạn không có quyền này.");
            }
        } else {
            serverMessage("Bạn không trong gia tộc.");
        }
    }

    public void changeClanType(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            String name = ms.reader().readUTF();
            byte type = ms.reader().readByte();
            if (this.clan != null) {
                int cType = clan.getMemberByName(this.name).getType();
                if (cType == Clan.TYPE_TOCTRUONG) {
                    Member mem = this.clan.getMemberByName(name);
                    if (mem != null) {
                        if (type == Clan.TYPE_TOCPHO || type == Clan.TYPE_TRUONGLAO) {
                            if (type == Clan.TYPE_TOCPHO && this.clan.getNumberSameType(type) >= 1) {
                                serverMessage("Chức phó gia tộc đã đủ chỗ.");
                                return;
                            }
                            if (type == Clan.TYPE_TRUONGLAO && this.clan.getNumberSameType(type) >= 5) {
                                serverMessage("Chức trưởng lão gia tộc đã đủ chỗ.");
                                return;
                            }

                            mem.setType(type);


                            if (type == Clan.TYPE_TOCPHO) {
                                this.clan.setAssistName(name);
                                DbManager.executeUpdate("UPDATE `clan` SET `assist_name` = ? WHERE `id` = ? LIMIT 1;", name, clan.getId());

                            }
                            Char _char = Char.findCharByName(name);
                            if (_char != null) {
                                _char.zone.getService().acceptInviteClan(_char);
                            }
                            clan.getClanService().requestClanMember();
                            clan.getClanService().serverMessage(name + " được bổ nhiệm làm " + ((type == Clan.TYPE_TOCPHO) ? "tộc phó" : "trưởng lão"));

                        } else {
                            int preType = mem.getType();
                            if (preType == Clan.TYPE_TOCPHO) {
                                this.clan.setAssistName("");
                                DbManager.executeUpdate("UPDATE `clan` SET `assist_name` = ? WHERE `id` = ? LIMIT 1;", "", clan.getId());
                            }
                            mem.setType(Clan.TYPE_NORMAl);
                            Char _char = Char.findCharByName(name);
                            if (_char != null) {
                                _char.zone.getService().acceptInviteClan(this);
                            }
                            clan.getClanService().requestClanMember();
                            clan.getClanService().serverMessage(name + " đã bị bãi nhiệm");
                        }
                    } else {
                        serverMessage("Thành viên không tồn tại.");
                    }
                } else {
                    serverMessage("Bạn không phải tộc trưởng.");
                }
            } else {
                serverMessage("Bạn không có gia tộc.");
            }
        } catch (Exception ex) {
            Log.logException("Clange Clan Type err: ", Char.class, ex);
        }
    }

    public void requestChangeMap() {
        if (this.isDead) {
            return;
        }
        zone.requestChangeMap(this);
    }

    public boolean isInvisible() {
        return isInvisible || isHide;
    }

    public void addParty(Message ms) {
        try {
            if (isDead) {
                return;
            }
            if (!isHuman) {
                warningClone();
                return;
            }
            if (zone.tilemap.isNotInvite()) {
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            String name = ms.reader().readUTF();
            if (this.name.equals(name)) {
                return;
            }
            Char _char = Char.findCharByName(name);
            if (_char != null) {
                if (_char.isInvite()) {
                    serverDialog("Tài khoản của người này không muốn nhận bất kì lời mời nào!");
                    return;
                }
                if (this.group == null) {
                    createGroup();
                } else {
                    MemberGroup party = this.group.memberGroups.get(0);
                    if (party.charId != this.id) {
                        serverMessage("Bạn không phải trưởng nhóm");
                        return;
                    }
                }
                if (_char.group != null) {
                    serverMessage("Người này đã gia nhập nhóm khác");
                    return;
                }
                PlayerInvite p = _char.invite.findCharInvite(Invite.NHOM, this.id);
                if (p != null) {
                    serverMessage("Không thể mời vào nhóm liên tục. Vui lòng thử lại sau 30s nữa.");
                    return;
                }
                _char.invite.addCharInvite(Invite.NHOM, this.id, 30);
                _char.getService().partyInvite(this.id, this.name);
            } else {
                serverMessage("Người này không tồn tại hoặc không online.");
            }
        } catch (Exception ex) {
            Log.logException("Add Party err: ", Char.class, ex);
        }
    }

    public void addPartyAccept(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (this.group == null) {
                int charId = ms.reader().readInt();
                Char _char = ServerManager.findCharById(charId);
                if (zone.tilemap.isNotInvite()) {
                    serverMessage("Không thể sử dụng tính năng này.");
                    return;
                }
                if (_char != null) {
                    if (_char.group == null) {
                        serverMessage("Nhóm này không còn tồn tại");
                        return;
                    }
                    MemberGroup p = _char.group.memberGroups.get(0);
                    if (p.charId != _char.id) {
                        serverMessage("Người này không phải nhóm trưởng");
                        return;
                    }
                    PlayerInvite c = this.invite.findCharInvite(Invite.NHOM, _char.id);
                    if (c == null) {
                        serverMessage("Đã hết thời gian chấp nhận yêu cầu vào nhóm.");
                        return;
                    }
                    MemberGroup party = new MemberGroup();
                    party.charId = this.id;
                    party.classId = this.classId;
                    party.name = this.name;
                    party.setChar(this);
                    _char.group.add(party);
                    this.group = _char.group;
                } else {
                    serverMessage("Hiện tại người này không online.");
                }
            } else {
                serverMessage("Bạn đã gia nhập nhóm khác");
            }
        } catch (Exception ex) {
            Log.logException("Appect Party err: ", Char.class, ex);
        }
    }

    public WarMember getMemberNormalWar() {
        War normalWar = MapManager.getInstance().normalWar;
        if (normalWar != null) {
            for (WarMember mem : normalWar.members) {
                if (mem.id == id) {
                    return mem;
                }
            }
        }
        return null;
    }


    public WarMember getMemberTalentWar() {
        War talentWar = MapManager.getInstance().talentWar;
        if (talentWar != null) {
            for (WarMember mem : talentWar.members) {
                if (mem.id == id) {
                    return mem;
                }
            }
        }
        return null;
    }

    public void addWarPoint(short point) {
        this.warPoint += point;
        if (this.gloryTask != null && this.gloryTask.type == GloryTask.NANG_CAP) {
            this.gloryTask.updateProgress(point);
        }

        if (taskId == TaskName.NV_CHIEN_TRUONG && taskMain != null && taskMain.index == 1) {
            updateTaskCount(point);
        }

        getService().warInfo();
        if (war != null && war.status == 1) {
            if (member != null) {
                member.point += point;
                if (this.faction == 0) {
                    war.whitePoint += point;
                }
                if (this.faction == 1) {
                    war.blackPoint += point;
                }
            }
        }
    }

    public void addWarClanPoint(short point) {
        this.pointGTC += point;
        if (this.pointGTC > 14000) {
            this.pointGTC = 14000;
        }
        getService().sendPointGTC();
        if (this.zone.map.clanWar != null) {
            if (this.zone.map.clanWar.gt1.contains(this)) {
                this.zone.map.clanWar.pointClan1 += point;
            } else {
                this.zone.map.clanWar.pointClan2 += point;
            }
        }
    }

    public void infection() {
        setInfected(true);
        setFashion();
    }

    public void cure() {
        setInfected(false);
        setFashion();
    }

    public void addPartyCancel(Message ms) {
        try {
            int charId = ms.reader().readInt();
            Char _char = ServerManager.findCharById(charId);

        } catch (IOException ex) {
            Log.logException("Party cancel err: ", Char.class, ex);
        }
    }

    public void outParty() {
        if (this.group != null) {
            synchronized (group) {
                if (this.group.memberGroups.size() > 1) {
                    int index = this.group.getIndexById(this.id);
                    if (index != -1) {
                        this.group.removeParty(index);
                    }
                }
                this.group = null;
                getService().outParty();
            }
        }

    }

    public void lockParty(Message ms) {
        try {
            boolean isLock = ms.reader().readBoolean();
            if (this.group != null && this.group.memberGroups.get(0).charId == this.id) {
                this.group.isLock = isLock;
                this.group.getGroupService().lockParty(isLock);
            }
        } catch (Exception ex) {
            Log.logException("Lock party err: ", Char.class, ex);
        }
    }

    public void openFindParty() {
        if (!isHuman) {
            warningClone();
            return;
        }
        if (this.group == null) {
            try {
                HashMap<String, Group> groups = new HashMap<>();
                List<Char> chars = zone.getChars();
                for (Char _char : chars) {
                    if (_char != null && _char.group != null) {
                        groups.put(_char.group.memberGroups.get(0).name, _char.group);
                    }
                }
                getService().openFindParty(groups);
            } catch (Exception ex) {
                Log.logException("Open find party err: ", Char.class, ex);
            }
        }
    }

    public void pleaseInputParty(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (zone.tilemap.isNotInvite()) {
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (this.group != null) {
                return;
            }
            String name = ms.reader().readUTF();
            if (this.name.equals(name)) {
                return;
            }
            Char _char = Char.findCharByName(name);
            if (_char != null) {
                if (_char.group != null) {
                    if (_char.group.isLock) {
                        serverMessage("Nhóm này đã khóa, không thể xin gia nhập");
                        return;
                    }
                    if (_char.group.memberGroups.size() == 6) {
                        serverMessage("Nhóm đã đủ thành viên");
                        return;
                    }
                    if (_char.group.memberGroups.get(0).charId != _char.id) {
                        return;
                    }
                    PlayerInvite p = _char.invite.findCharInvite(Invite.XIN_VAO_NHOM, this.id);
                    if (p != null) {
                        serverMessage("Không thể xin vào nhóm liên tục. Vui lòng thử lại sau 30s nữa.");
                        return;
                    }
                    _char.invite.addCharInvite(Invite.XIN_VAO_NHOM, this.id, 30);
                    _char.getService().pleaseInputParty(this.name);
                } else {
                    serverMessage("Nhóm này không tồn tại");
                }
            } else {
                serverMessage("Người này không online hoặc không tồn tại");
            }
        } catch (Exception ex) {
            Log.logException("please Input Party err: ", Char.class, ex);
        }
    }

    public void moveMember(Message ms) {
        try {
            if (this.group != null) {
                if (this.group.memberGroups.get(0).charId == this.id) {
                    byte index = ms.reader().readByte();
                    if (index == 0 || index >= this.group.memberGroups.size()) {
                        serverMessage("Không thể trục xuất người này");
                        return;
                    }
                    Char _char = this.group.memberGroups.get(index).getChar();
                    _char.outParty();
                    // _char.sendMessage(new Message(83));
                    // this.group.removeParty(index);
                } else {
                    serverMessage("Bạn không phải trưởng nhóm");
                }
            }

        } catch (Exception ex) {
            Log.logException("Move Member err: ", Char.class, ex);
        }
    }

    public void changeTeamLeader(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (isDead) {
                return;
            }
            if (zone.tilemap.isNotInvite()) {
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (this.group != null) {
                if (this.group.memberGroups.get(0).charId == this.id) {
                    byte index = ms.reader().readByte();
                    if (index == 0 || index >= this.group.memberGroups.size()) {
                        serverMessage("Không thể nhường cho người này");
                        return;
                    }
                    this.group.changeLeader(index);
                } else {
                    serverMessage("Bạn không phải trưởng nhóm");
                }
            }

        } catch (Exception ex) {
            Log.logException("changeTeamLeader err: ", Char.class, ex);
        }
    }


    public void acceptPleaseParty(Message ms) {
        if (this.group == null) {
            return;
        }
        if (this.group.memberGroups.size() < 6) {
            if (this.group.memberGroups.get(0).charId == this.id) {
                try {
                    String name = ms.reader().readUTF();
                    if (this.name.equals(name)) {
                        return;
                    }
                    Char _char = Char.findCharByName(name);
                    if (_char != null) {
                        if (!_char.isHuman) {
                            warningClone();
                            return;
                        }
                        if (_char.group == null) {
                            PlayerInvite c = this.invite.findCharInvite(Invite.XIN_VAO_NHOM, _char.id);
                            if (c == null) {
                                serverDialog("Đã hết thời gian chấp nhận yêu cầu tham gia nhóm.");
                                return;
                            }
                            _char.group = this.group;
                            MemberGroup p = new MemberGroup();
                            p.charId = _char.id;
                            p.classId = _char.classId;
                            p.name = _char.name;
                            p.setChar(_char);
                            _char.group.add(p);
                        } else {
                            serverDialog("Người này đã gia nhập nhóm khác");
                        }
                    } else {
                        serverDialog("Người này không online");
                    }
                } catch (IOException ex) {
                    Log.logException("Appect Please Party err: ", Char.class, ex);
                }
            }
        } else {
            serverDialog("Nhóm đã đủ thành viên");
        }
    }

    public void createGroup() {
        try {
            if (this.group != null) {
                serverDialog("Không thể tạo nhóm");
                return;
            }
            if (zone.tilemap.isNotInvite()) {
                serverMessage("Không thể sử dụng tính năng này trong lôi đài.");
                return;
            }
            int maxGroup = 6;
            if (this.mapId >= 134 && this.mapId <= 137) {
                maxGroup = 4;
            }
            int numberGroup = zone.getNumberGroup();
            if (numberGroup >= maxGroup) {
                serverMessage("Số nhóm trong khu vực đã đạt tối đa.");
                return;
            }
            Group group = new Group();
            MemberGroup party = new MemberGroup();
            party.charId = this.id;
            party.classId = this.classId;
            party.name = this.name;
            party.setChar(this);
            group.add(party);
            this.group = group;
        } catch (Exception ex) {
            Log.logException("Create Group err: ", Char.class, ex);
        }
    }

    public void changeTypePk(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (BlackSaturday.getInstance().status == 1) { /// t7 đen tối
                serverMessage("Không thể thay đổi trạng thái PK trong thời gian thứ 7 đen tối .");
                return;
            }
            if (zone.tilemap.isNotPk()) {
                serverMessage("Không thể sử dụng tính năng này tại đây.");
                return;
            }
            if (mapId >= 98 && mapId <= 104) {
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }

            byte type = ms.reader().readByte();
            if (type < 0 || type > 3) {
                return;
            }
            setTypePk(type);
        } catch (Exception ex) {
            Log.logException("Change type Pk err: ", Char.class, ex);
        }
    }

    public void setTypePk(byte typePk) {
        this.typePk = typePk;
        if (zone != null) {
            zone.getService().changePk(this);
        }
    }

    public void changeMap(int id) {
        try {
            int zoneId = NinjaUtils.randomZoneId(id);


            if (zoneId == -1) {
                serverDialog("Không tìm thấy nơi này!");
                return;
            }
            this.mapId = (short) id;
            if (isDead) {
                wakeUpFromDead();
            }
            boolean isSameGroup = false;
            if (this.group != null) {
                List<Char> chars = this.group.getCharsInMap(id);
                for (Char _char : chars) {
                    if (_char != null && _char != this) {
                        zoneId = _char.zone.id;
                        isSameGroup = true;
                        break;
                    }
                }
                if (id >= 134 && id <= 137) {
                    if (!isSameGroup) {
                        Map map = MapManager.getInstance().find(id);
                        Zone zone = map.getZoneById(zoneId);
                        int maxGruop = 4;
                        int numberGroup = zone.getNumberGroup();
                        if (numberGroup >= maxGruop) {
                            setXY(preX, preY);
                            getService().resetPoint();
                            serverDialog("Số nhóm trong khu vực này đã tối đa.");
                            return;
                        }
                    }

                    //maxnumberninja
                    if (zone.getNumberChar() >= 24) {
                        setXY(preX, preY);
                        getService().resetPoint();
                        serverDialog("Khu vực đã đầy.");
                        return;
                    }

                }

            }

//            if (maxMap()) {
//                setXY(preX, preY);
//                getService().resetPoint();
//                serverDialog("Map đã đầy ");
//                return;
//            }
            isChangeMap = true;
            if (id == MapName.HANG_KARASUMORI_94 || (id == MapName.HANG_KARASUMORI_92 && this.zone.map.vithu.isDieVithu)) {
                int zoneid = 0;
                if (this.level >= 50 && this.level <= 70) {
                    zoneid = 1;
                } else if (this.level <= 100) {
                    zoneid = 2;
                } else if (this.level <= 130) {
                    zoneid = 3;
                } else if (this.level <= 150) {
                    zoneid = 4;
                }
                outZone();
                joinZone(id, zoneid, -1);

            } else {
                outZone();
                joinZone(id, zoneId, -1);


            }


            if (taskId == TaskName.NV_KHAM_PHA_XA_LANG && taskMain != null) {
                if ((mapId == 2 && taskMain.index == 1) || (mapId == 71 && taskMain.index == 2) || (mapId == 26 && taskMain.index == 3)) {
                    taskNext();
                }
            }
        } catch (Exception ex) {
            Log.logException("Change Map err: ", Char.class, ex);
        }
    }

    public void removeMemberFromWorld(Zone pre, Zone now) {
        synchronized (worlds) {
            worlds.forEach((t) -> {
                if (t.leaveWorld(pre, now)) {
                    t.removeMember(this);
                }
            });
        }
    }

    public void addMemberForWorld(Zone pre, Zone now) {
        synchronized (worlds) {
            worlds.forEach((t) -> {
                if (t.enterWorld(pre, now)) {
                    t.addMember(this);
                }
            });
        }
    }

    public void sendToSaleItem(Message ms) {
        try {

            if (user.activated == 0) {
                service.serverDialog("NSO_KENY Chúc bạn chơi game vui vẻ: Zalo 0962.566.289");
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
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
            if (zone.tilemap.id != 1 && zone.tilemap.id != 27 && zone.tilemap.id != 72) { /// chặn shinwa ngoài map
                serverMessage("Bạn phải về trường học để thao tác.");
                getService().endDlg(true);
                return;
            }
            if (!Config.getInstance().isShinwa()) {
                serverMessage("Tính năng đang bảo trì.");
                return;
            }
            byte index = ms.reader().readByte();
            int price = ms.reader().readInt();
            if (index < 0 || index >= this.numberCellBag) {
                return;
            }
            Item item = this.bag[index];
            if (item == null) {
                serverDialog("Không tìm thấy vật phẩm.");
                return;
            }
            if (price <= 0) {
                serverDialog("Giá xu phải lớn hơn 0 xu");
                return;
            }
            if (price > 100000000) {
                serverDialog("Giá xu tối đa là 100.000.000 xu");
                return;
            }
            if (item.isLock) {
                serverDialog("Chỉ được chọn phẩm không khóa và không có hạn sửa dụng.");
                return;
            }
            if (item.hasExpire()) {
                serverDialog("Chỉ được chọn phẩm không khóa và không có hạn sửa dụng.");
                return;
            }
            if (item.template.isBlackListItem()) {
                serverDialog("Không thể giao vật phẩm này, chỉ có thể bán.");
                return;
            }
            int fee = Config.getInstance().getShinwaFee();
            int max = Config.getInstance().getShinwaMax();
            if (this.coin < fee) {
                serverDialog(String.format("Phí bán vật phẩm là %,d xu.", fee));
                return;
            }
            if (StallManager.getInstance().getTotalProductBySeller(name) > max) {
                serverDialog(String.format("Mỗi người chỉ có thể bán tối đa %,d vật phẩm.", max));
                return;
            }
            int type = item.template.type;
            max = Config.getInstance().getAuctionMax();
            Stall stall = StallManager.getInstance().findByType((byte) type);
            if (stall.getTotalProduct() > max) {
                serverDialog(String.format("Gian hàng chỉ có thể bán được %,d vật phẩm.", max));
                return;
            }

            StallManager.getInstance().addItem(this, item, price);

            // ADD HISTORY
            History history = new History(this.id, History.SHINWA_BAN);
            history.setBefore(this.coin, user.gold, this.yen);
            this.coin -= fee;
            history.setAfter(this.coin, user.gold, this.yen);
            history.addItem(item);
            history.setTime(System.currentTimeMillis());
            History.insert(history);
            // END HISTORY

            this.bag[index] = null;
            getService().sendItemToAuction(index);
        } catch (IOException e) {
            Log.logException("Send to sale Item err: ", Char.class, e);
        }
    }

    public synchronized void buyItemAuction(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            if (this.typeProtect == 1) {
                getService().endWait("Tài khoản đang được bảo vệ vui lòng mở khóa để thực hiện.");
                return;
            }
            if (this.taskId < TaskName.NV_DIET_SEN_TRU_COC) {
                getService().endWait("Hãy hoàn nhiệm vụ diệt trừ cóc để sử dụng chức năng này.");
                return;
            }
            if (trade != null) {
                warningTrade();
                return;
            }
            if (zone.tilemap.id != 1 && zone.tilemap.id != 27 && zone.tilemap.id != 72) { /// chặn shinwa ngoài map
                serverMessage("Bạn phải về trường học để thao tác.");
                getService().endDlg(true);
                return;
            }
            if (!Config.getInstance().isShinwa()) {
                serverMessage("Tính năng đang bảo trì.");
                return;
            }
            int id = ms.reader().readInt();
            Stall stall = StallManager.getInstance().findByID(viewAuctionTab);
            if (stall == null) {
                return;
            }
            stall.buy(this, id);
        } catch (Exception e) {
            Log.logException("Buy Item Shinwa err: ", Char.class, e);
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

    public void requestCharInfo(Message ms) {
        try {
            int num = ms.reader().readByte();
            ArrayList<Char> chars = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                int charId = ms.reader().readInt();
                Char _char2 = zone.findCharById(charId);
                if (_char2 != null) {
                    chars.add(_char2);
                }
            }
            getService().requestPlayers(chars);
        } catch (Exception ex) {
            Log.logException("Request Char Info err: ", Char.class, ex);
        }
    }

    public void requestItemChar(Message ms) {
        try {
            int charId = ms.reader().readInt();
            byte indexUI = ms.reader().readByte();
            Char _char = ServerManager.findCharById(charId);
            if (_char != null) {
                if (indexUI >= 0) {
                    if (indexUI < 16) {
                        Equip equip = _char.equipment[indexUI];
                        if (equip != null) {
                            getService().requestItemChar(equip, indexUI);
                        }
                    } else {
                        int index = indexUI - 16;
                        if (index >= 0 && index < 16) {
                            Equip equip = _char.fashion[index];
                            if (equip != null) {
                                getService().requestItemChar(equip, indexUI);
                            }

                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.logException("Request Item Char err: ", Char.class, ex);
        }
    }

    public void changeClanAlert(Message ms) {
        if (!isHuman) {
            warningClone();
            return;
        }
        Clan clan = this.clan;
        if (clan != null && clan.getMemberByName(this.name).getType() == Clan.TYPE_TOCTRUONG) {
            try {
                String alert = ms.reader().readUTF();
                clan.setAlert(alert);
                getService().changeClanAlert(alert);
                serverDialog("Thay đổi thông báo thành công.");
            } catch (Exception ex) {
                Log.logException("Change Clan Alert err: ", Char.class, ex);
            }
        } else {
            serverDialog("Có lỗi xảy ra.");
        }
    }

    public void requestItem(Message ms) {
        try {
            byte typeUI = ms.reader().readByte();
            Store storeData = StoreManager.getInstance().find(typeUI);
            if (storeData != null) {
                storeData.show(this);
            }
        } catch (Exception ex) {
            Log.logException("Request Item err: ", Char.class, ex);
        }
    }

    public void requestEnemies() {

    }

    public void requestItemInfo(Message ms) {
        try {
            byte typeUI = ms.reader().readByte();
            byte indexUI = ms.reader().readByte();
            if (indexUI < 0) {
                return;
            }
            if (typeUI == 2 || (typeUI >= 14 && typeUI <= 29) || typeUI == 32 || typeUI == 34 || typeUI == 8 || typeUI == 9 || typeUI == 7 || typeUI == 8) {
                Store store = StoreManager.getInstance().find(typeUI);
                if (store != null) {
                    ItemStore item = store.get(indexUI);
                    if (item != null) {
                        getService().itemStoreInfo(item, typeUI, indexUI);
                    }
                }
            } else if (typeUI == 3) {
                if (indexUI >= bag.length) {
                    return;
                }
                if (bag[indexUI] != null) {
                    getService().itemInfo(bag[indexUI], typeUI, indexUI);

                }
            } else if (typeUI == 4) {
                switch (getCommandBox()) {
                    case CAI_TAO:  // hóa hinh
                    case HOA_HINH:
                        if (indexUI >= ItemCaiTao_HoaHinh.length) {
                            return;
                        }
                        Item itcaitao = ItemCaiTao_HoaHinh[indexUI];
                        if (itcaitao != null) {
                            getService().itemInfo(itcaitao, typeUI, indexUI);
                        }
                        break;
                    case PHAN_TACH:
                        if (indexUI >= ItemPhanTach.length) {
                            return;
                        }
                        Item itTach = ItemPhanTach[indexUI];
                        if (itTach != null) {
                            getService().itemInfo(itTach, typeUI, indexUI);
                        }
                        break;



                    case RUONG_DO:
                        if (indexUI >= box.length) {
                            return;
                        }
                        if (box[indexUI] != null) {
                            getService().itemInfo(box[indexUI], typeUI, indexUI);
                        }
                        break;
                    case THANG_NGUONG:
                        if (indexUI >= equipment.length) {
                            return;
                        }
                        if (equipment[indexUI] != null) {
                            getService().itemInfo(equipment[indexUI], typeUI, indexUI);

                        }
                        break;

                    case STORE_UPGRADE: // shop 16
                        if (indexUI >= StoreUpgradeManager.getInstance().items.size()) {
                            return;
                        }
                        if (StoreUpgradeManager.getInstance().items.get(indexUI) != null) {
                            Item item = Converter.getInstance().toItem(StoreUpgradeManager.getInstance().items.get(indexUI));
                            getService().itemInfo(item, typeUI, indexUI);
                        }
                        break;
                    case HOA_HINH_THU:
                        List<Item> listx = getListItem5sao(ItemName.XICH_NHAN_NGAN_LANG,ItemName.XE_MAY, ItemName.HARLEY_DAVIDSON);
                        if (indexUI >= listx.size()) {
                            return;
                        }
                        Item itm5 = listx.get(indexUI);
                        if (itm5 != null) {
                            getService().itemInfo(itm5, typeUI, indexUI);
                        }
                        break;

                    case THANG_CAP:
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
                        if (indexUI >= itemList.size()) {
                            return;
                        }
                        if (itemList.get(indexUI) != null) {
                            getService().itemInfo(itemList.get(indexUI), typeUI, indexUI);
                        }

                        break;

                    case BO_SUU_TAP:
                        if (collectionBox == null) {
                            return;
                        }
                        if (indexUI >= collectionBox.size()) {
                            return;
                        }
                        Item itm = collectionBox.get(indexUI);
                        if (itm != null) {
                            getService().itemInfo(itm, typeUI, indexUI);
                        }
                        break;

                    case CAI_TRANG:
                        if (maskBox == null) {
                            return;
                        }
                        if (indexUI >= maskBox.size()) {
                            return;
                        }
                        Item itm2 = maskBox.get(indexUI);
                        if (itm2 != null) {
                            getService().itemInfo(itm2, typeUI, indexUI);
                        }
                        break;
                    case GIA_HAN:
                        if (indexUI >= ItemExtend.length) {
                            return;
                        }
                        Item itmex = ItemExtend[indexUI];
                        if (itmex != null) {
                            getService().itemInfo(itmex, typeUI, indexUI);
                        }
                        break;

                    case DOI_LONG_DEN_XU:
                    case DOI_LONG_DEN_LUONG:
                        List<Item> list = getListItemByID(ItemName.LONG_DEN_TRON, ItemName.LONG_DEN_CA_CHEP, ItemName.LONG_DEN_MAT_TRANG, ItemName.LONG_DEN_NGOI_SAO);
                        if (indexUI >= list.size()) {
                            return;
                        }
                        Item itm3 = list.get(indexUI);
                        if (itm3 != null) {
                            getService().itemInfo(itm3, typeUI, indexUI);
                        }
                        break;
                }

            } else if (typeUI == 5) {
                if (indexUI < 16) {
                    if (equipment[indexUI] != null) {
                        getService().equipmentInfo(equipment[indexUI], typeUI, indexUI);
                    }
                } else {
                    int index = indexUI - 16;
                    if (index >= fashion.length) {
                        return;
                    }
                    if (fashion[index] != null) {
                        getService().equipmentInfo(fashion[index], typeUI, indexUI);
                    }
                }
            } else if (typeUI == 30) {
                if (trade != null) {
                    trade.viewItemInfo(this, typeUI, indexUI);
                }
            }
            // else if (typeUI == 39) {
            // if (this.clan != null) {
            // Item[] items = this.clan.getItems();
            // if (items != null) {
            // getService().itemInfo(items[indexUI], typeUI, indexUI);
            // }
            // }
            // }
        } catch (Exception ex) {
            Log.logException("Request Item Info err (1): ", Char.class, ex);
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
                store.buy(this, indexUI, quantity);
            }
        } catch (Exception ex) {
            Log.logException("Buy Item err: ", Char.class, ex);
        }
    }

    public void viewInfo(Message mss) {
        try {
            String text = mss.reader().readUTF();
            Char _char = null;
//            String name = "";
//            if(this.user.SVIP){
//                name = "[SVIP] "+this.name;
//            }
            if (text.equals(this.name)) {
                if (clone != null && !clone.isNhanBan) {
                    _char = clone;
                } else {
                    _char = this;
                }
            } else {
                _char = findCharByName(text);
            }

            if (_char != null) {
                getService().viewInfo(_char);
            } else {
                serverMessage("Người này hiện tại không online.");
            }
        } catch (Exception ex) {
            Log.logException("view info err: ", Char.class, ex);
        }
    }

    public void rewardDungeon() {
        Dungeon dungeon = (Dungeon) findWorld(World.DUNGEON);
        if (dungeon != null && !receivedRewardPB && pointPB > 0) { /// fix hd
            int team = dungeon.listCharId.size();
            int reward = pointPB / Dungeon.POINT[dungeon.level];
            if (dungeon.timeFinish > 0) {
                reward += team;
            }
            Item item = ItemFactory.getInstance().newItem(Dungeon.REWARD[dungeon.level]);
            item.setQuantity(reward);
            item.isLock = true;
            addItemToBag(item);
            this.receivedRewardPB = true;
            /// fix hd 13/8
            if (reward > rewardPB) {
                rewardPB = reward;
            }
            pointPB = 0;
            removeWorld(World.DUNGEON);
            getService().updatePointPB();
        }
    }

    public byte getSideClass() {
        if (this.classId == 6 || this.classId == 4 || this.classId == 2) {
            return 1;
        } else {
            return 0;
        }
    }

    public byte getSys() {
        if (this.classId == 1 || this.classId == 2) {
            return 1;//hoa
        }
        if (this.classId == 3 || this.classId == 4) {
            return 2;// bang
        }
        if (this.classId == 5 || this.classId == 6) {
            return 3; // phong
        }
        return 0;
    }

    public boolean isVillage() {
        short[] map = new short[]{10, 17, 22, 32, 38, 43, 48, 138, 162};
        for (short m : map) {
            if (this.mapId == m) {
                return true;
            }
        }
        return false;
    }

    public boolean isSchool() {
        short[] map = new short[]{1, 27, 72};
        for (short m : map) {
            if (this.mapId == m) {
                return true;
            }
        }
        return false;
    }

    public void updatePointPB(int point) {
        this.pointPB += point;
        getService().updatePointPB();
    }

    public synchronized void addGold(int gold) {
        long sum = (long) user.gold + (long) gold;
        int pre = user.gold;
        if (sum > 2000000000) {
            user.gold = 2000000000;
        } else {
            user.gold += gold;
        }
        if (user.gold < 0) {
            user.gold = 0;
        }
        gold = (user.gold - pre);
        if (gold > 0) {
            getService().addGold(gold);
        } else {
            getService().loadGold();
        }
    }

    public synchronized void addCoin(long xu) {
        if (trade != null) {
            trade.closeUITrade();
        }
        long sum = this.coin + xu;
        // long pre = this.coin;
        if (sum > this.coinMax) {
            this.coin = this.coinMax;
        } else {
            this.coin += xu;
        }
        if (this.coin < 0) {
            this.coin = 0;
        }
        // getService().addXu((int) (this.coin - pre));
        updateWithBalanceMessage();
    }

    public synchronized void addYen(long yen) {
        long sum = this.yen + yen;
        long pre = this.yen;
        if (sum > 2000000000) {
            this.yen = 2000000000;
        } else {
            this.yen += yen;
        }
        if (this.yen < 0) {
            this.yen = 0;
        }
        getService().addYen((int) (this.yen - pre));
        // update();
    }

    public synchronized void addExp(long exp) {
        if (notReceivedExp || haveOptions[ItemOptionName.KHONG_NHAN_EXP_TYPE_0]) {
            return;
        }
        exp = exp * Config.getInstance().getRateEXP();
        if (this.isNhanBan) {
            CloneChar clone = (CloneChar) this;
            clone.human.addExp(exp);
            return;
        }
        if (this.expDown > 0) {
            if (this.expDown - exp >= 0) {
                this.expDown -= exp;
            } else {
                exp -= this.expDown;
                this.expDown = 0;
                addExp(exp);
            }
            getService().addExpDown(exp);
        } else {

            if (this.classId > 0 && exp > 500000L && this.isHuman && this.clone != null) { /// fix skill phân thân
                try {
                    for (short i = 0; i < this.vSkill.size(); ++i) {
                        Skill skill = this.vSkill.get(i);
                        if (skill != null && skill.isCloneSkill() && skill.template.maxPoint > skill.point && NinjaUtils.nextInt(1000) < 100 / (skill.point * 10)) {
                            Skill skillup = GameData.getInstance().getSkill(this.classId, skill.template.id,
                                    vSkill.get(i).point + 1);
                            vSkill.set(i, skillup);
                            serverMessage(skill.template.name + " đã đạt cấp " + skillup.point);
                            getService().loadSkill();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.exp += exp;
            if (this.exp > Server.EXP_MAX) {
                this.exp = Server.EXP_MAX;
                return;
            }
            getService().addExp(exp);
            int preLevel = this.level;
            this.level = NinjaUtils.getLevel(this.exp);

            int nextLevel = this.level;
            int num = nextLevel - preLevel;
            if (num > 0) {
                this.levelUpTime = (new Date()).getTime();
                if (this.classId == 0) {
                    this.potential[0] += 2 * num;
                    this.potential[1] += num;
                    this.potential[2] += num;
                    this.potential[3] += num;
                } else {
                    for (int i = preLevel; i < nextLevel; i++) {
                        if (i >= 10 && i <= 69) {
                            this.potentialPoint += 10;
                        } else if (i <= 79) {
                            this.potentialPoint += 20;
                        } else if (i <= 89) {
                            this.potentialPoint += 30;
                        } else if (i <= 99) {
                            this.potentialPoint += 40;
                        } else if (i <= 130) {
                            this.potentialPoint += 50;
                        } else if (i <= 145) {
                            this.potentialPoint += 60;
                        } else {
                            this.potentialPoint += 70;
                        }
                    }
                    this.skillPoint += num;
                }

                updateTaskLevelUp();
                getService().levelUp();
                zone.getService().loadLevel(this);
                setAbility();
            }


        }
    }

    public void updateTaskCount(int count) {
        if (taskMain != null) {
            try {
//                if(taskMain.count>=8){
//                    taskMain.count+=40;
//                }else{
//
//                }
                taskMain.count += count;
                short[] counts = taskMain.template.getCounts();
                getService().updateTaskCount(taskMain.count);
                if (taskMain.count >= counts[taskMain.index]) {
                    taskNext();
                }
            } catch (Exception ex) {
                Log.logException("update Task count err: ", Char.class, ex);
            }
        }
    }

    public void taskNext() {
        if (taskMain != null) {
            taskMain.index++;
            TaskTemplate template = taskMain.template;
            taskMain.count = 0;
            short[] counts = taskMain.template.getCounts();
            if (taskMain.index > counts.length - 1) {
                taskMain.index = counts.length - 1;
            }
            getService().taskNext();
        }
    }

    public void updateTask() {
        if (taskMain != null) {
            taskMain = null;

            if (taskId >= 70) {
                taskId += 1;
            } else {
                taskId++;
            }
            getService().taskFinish();
        }
        initListCanEnterMap();
    }

    public void removeFriend(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            String name = ms.reader().readUTF();
            if (this.isViewListFriend) {
                if (this.friends.get(name) != null) {
                    this.friends.remove(name);
                    getService().removeFriend(name);
                } else {
                    serverMessage("Người này không tồn tại.");
                }
            }
            if (!this.isViewListFriend) {
                if (this.enemies.get(name) != null) {
                    this.enemies.remove(name);
                    getService().removeEnemy(name);
                } else {
                    serverMessage("Người này không tồn tại.");
                }
            }
        } catch (Exception ex) {
            Log.logException("Remove Friend err: ", Char.class, ex);
        }

    }

    public void removeEnemy(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            String name = ms.reader().readUTF();
            if (this.enemies.get(name) != null) {
                this.enemies.remove(name);
                getService().removeEnemy(name);
            } else {
                serverMessage("Người này không tồn tại.");
            }
        } catch (Exception ex) {
            Log.logException("Remove Enemy err: ", Char.class, ex);
        }

    }

    public void addFriend(Message ms) {
        try {
            if (!isHuman) {
                warningClone();
                return;
            }
            String name = ms.reader().readUTF();
            Char _char = findCharByName(name);
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (_char == null) {
                serverMessage("Người này không online hoặc không tồn tại!");
                return;
            }
            if (_char == this) {
                return;
            }
            if (!_char.isHuman && !(_char instanceof com.nsoz2.bot.Bot)) {
                return;
            }
            Friend friend = friends.get(name);
            if (friend != null) {
                serverMessage(name + " đã có trong danh sách bạn bè.");
                return;
            }
            Friend me = _char.friends.get(this.name);
            if (me != null) {
                me.type = 1;
                friends.put(_char.name, new Friend(_char.name, (byte) 1));
                getService().addFriend(name, 1);
                _char.getService().addFriend(this.name, 1);
                return;
            } else {
                friends.put(_char.name, new Friend(_char.name, (byte) 0));
            }
            if (taskId == TaskName.NV_BAN_HUU_TAM_GIAO && taskMain != null && taskMain.index == 1) {
                updateTaskCount(1);
            }
            getService().addFriend(name, 0);
            _char.getService().inviteFriend(this.name);
        } catch (Exception ex) {
            Log.logException("add Friend Err: ", Char.class, ex);
        }
    }

    public void addPointPk(int p) {
        if (isBot()) {
            return;
        }
        this.hieuChien += p;
        if (this.hieuChien > 15) {
            this.hieuChien = 15;
        } else if (this.hieuChien < 0) {
            this.hieuChien = 0;
        }
        /// dev fix 13/8
        if (this.zone.tilemap.isNotAllowPkPoint() && this.hieuChien > 0) {
            short[] xy = NinjaUtils.getXY(this.saveCoordinate);
            setXY(xy[0], xy[1]);
            changeMap(this.saveCoordinate);
        }
    }

    public byte getTaskMapId() {
        if (taskId >= Server.npcTasks.length) {
            return -3;
        }
        byte b;
        if (taskMain == null) {
            b = Server.mapTasks[taskId][0];
        } else {
            b = Server.mapTasks[taskId][taskMain.index + 1];
        }
        if ((int) b == -1) {
            if (this.classId == 0 && taskId == 9) {
                b = -2;
            } else if (this.classId == 0 || this.classId == 1 || this.classId == 2) {
                b = 1;
            } else if (this.classId == 3 || this.classId == 4) {
                b = 72;
            } else if (this.classId == 5 || this.classId == 6) {
                b = 27;
            }
        }
        return b;
    }

    public byte getTaskNpcId() {
        byte result;
        try {
            if (taskId >= Server.npcTasks.length) {
                result = -3;
            } else {
                byte b;
                if (taskMain == null) {
                    b = Server.npcTasks[taskId][0];
                } else {
                    b = Server.npcTasks[taskId][taskMain.index + 1];
                }
                if ((int) b == -1) {
                    if (classId == 0 && taskId == TaskName.NV_GIA_TANG_SUC_MANH) {
                        b = -2;
                    } else if (classId == 0 || classId == 1 || classId == 2) {
                        b = 9;
                    } else if (classId == 3 || classId == 4) {
                        b = 10;
                    } else if (classId == 5 || classId == 6) {
                        b = 11;
                    }
                }
                result = b;
            }
        } catch (Exception ex) {
            result = -1;
        }
        return result;
    }

    public void clearTask() {
        try {
            if (this.level < 10) {
                getService().npcChat(NpcName.TAJIMA, "Trình độ của con không đạt cấp 10 nên không được sử dụng chức năng này");
                return;
            }
            if (this.taskMain.taskId == 24 && this.taskMain.index == 2) {
                removeItemBags(ItemName.DA_CAP_9, 1);
            }
            this.taskMain = null;
            for (int i = 0; i < this.bag.length; i++) {
                if (this.bag[i] != null && ((int) this.bag[i].template.type == 25 || (int) this.bag[i].template.type == 23 || (int) this.bag[i].template.type == 24)) {
                    this.bag[i] = null;
                }
            }

            getService().clearTask();
            getService().npcChat(NpcName.TAJIMA, "Tất cả vật phẩm nhiệm vụ và nhiệm vụ hiện tại trên người con đã được hủy.");
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

    public void loadSkillShortcut(Message mss) {
        try {
            byte type = 0;
            if (!isHuman) {
                type = 1;
            }
            String key = mss.reader().readUTF();
            byte[] data = {};
            switch (key) {
                case "KSkill":
                    data = this.onKSkill;
                    break;

                case "OSkill":
                    data = this.onOSkill;
                    break;

                case "CSkill":
                    data = this.onCSkill;
                    break;
            }
            getService().sendSkillShortcut(key, data, type);
        } catch (Exception ex) {
            Log.logException("Load skill ShorCut err: ", Char.class, ex);
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
                    war.removeMember(this);
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

    public void showRankedList(int type) {
        String content = "";
        Vector ranked = Ranked.RANKED[type];
        for (int i = 0; i < ranked.size(); i++) {
            content += ranked.get(i) + "\n";
        }
        getService().showAlert(Ranked.NAME[type], content);
    }

    public void clanInvite(Message ms) {
        if (this.clan != null) {
            int typeClan = this.clan.getMemberByName(this.name).getType();
            if (zone.tilemap.isNotInvite()) { /// dev fix bug lôi đài 13/8
                serverMessage("Không thể sử dụng tính năng này.");
                return;
            }
            if (typeClan == Clan.TYPE_TOCTRUONG || typeClan == Clan.TYPE_TOCPHO || typeClan == Clan.TYPE_TRUONGLAO) {
                try {
                    int charId = ms.reader().readInt();
                    Char _char = zone.findCharById(charId);
                    if (_char != null) {
                        if (_char.isInvite()) {
                            serverDialog("Tài khoản của người này không muốn nhận bất kì lời mời nào!");
                            return;
                        }
                        if (_char.user.activated == 0) {
                            serverMessage("Đối phương chưa kích hoạt tài khoản không thể tham gia gia tộc!");
                            return;
                        }
                        if (_char == this) {
                            return;
                        }
                        if (_char.clan != null) {
                            return;
                        }
                        PlayerInvite p = _char.invite.findCharInvite(Invite.GIA_TOC, this.id);
                        if (p != null) {
                            serverMessage("Không thể mời vào vào liên tục. Vui lòng thử lại sau 30s nữa.");
                            return;
                        }
                        if (System.currentTimeMillis() - _char.lastTimeOutClan < 86400000) {
                            serverMessage("Người này vừa rời gia tộc, chỉ có thể mời lại sau 24 giờ nữa .");
                            return;
                        }
                        _char.invite.addCharInvite(Invite.GIA_TOC, this.id, 30);
                        _char.getService().clanInvite(this);
                    } else {
                        serverDialog("Người này không còn trong khu vực!");
                    }
                } catch (Exception ex) {
                    Log.logException("Clan Invite err: ", Char.class, ex);
                }
            } else {
                serverDialog("Bạn không có quyền này.");
            }
        } else {
            serverDialog("Bạn không có gia tộc.");
        }
    }

    // nâng pet 5 sao
    public boolean addExpForMount(int exp, byte type) {
        Mount mount = this.mount[4];
        if (mount == null) {
            serverMessage("Bạn cần có thú cưỡi");
            return false;
//        } else if (mount.hasExpire() && mount.id != 798) {
//            return false;

        } else if (mount.hasExpire()) {  // fix thú cưỡi hạn
                serverMessage("Chỉ sử dụng với thú cưỡi vĩnh viễn");
                return false;


                // nâng exp thú
        } else if (type == 0 && mount.id != 443 && mount.id != 523 && mount.id != 524 && mount.id != 798) {
            serverMessage("Chỉ sử dụng cho thú cưỡi");
            return false;
        } else if (type == 1 && mount.id != 485 && mount.id != 524) {
            serverMessage("Chỉ sử dụng cho xe máy");
            return false;
        } else if (type == 2 && mount.id != ItemName.HAC_NGUU && mount.id != ItemName.KIM_NGUU && mount.id != ItemName.LAN_SU_VU
                && mount.id != ItemName.XICH_TU_MA && mount.id != ItemName.TA_LINH_MA && mount.id != ItemName.PHONG_THUONG_MA
                && mount.id != ItemName.BACH_NGAN_LANG && mount.id != ItemName.BACH_SU_VUONG   // thêm pet mới
                && mount.id != ItemName.SIEU_XE_HALLOWEEN
                && mount.id != ItemName.PHUONG_HOANG_BANG && mount.id != ItemName.BACH_HO && mount.id != ItemName.HOA_KY_LAN) {
            serverMessage("Chỉ sử dụng cho Trâu, Hổ và Lân");
            return false;
        } else if (mount.upgrade < 99) {
            ItemOption opExp = null;
            for (byte i = 0; i < mount.options.size(); i++) {
                ItemOption op = mount.options.get(i);
                if (op.optionTemplate.id == 65) {
                    opExp = op;
                    break;
                }
            }
            if (opExp != null) {
                opExp.param += exp;
                while (opExp.param >= 1000) {
                    int num = opExp.param - 1000;
                    opExp.param = num;
                    mount.upgrade++;
                    int level = mount.upgrade + 1;
                    if (level == 10 || level == 20 || level == 30 || level == 40 || level == 50 || level == 60
                            || level == 70 || level == 80 || level == 90 || level == 100) {
                        for (byte i = 0; i < mount.options.size(); i++) {
                            ItemOption op = mount.options.get(i);
                            if (op.optionTemplate.id != 65 && op.optionTemplate.id != 66) {

                                if (op.optionTemplate.id == 6 || op.optionTemplate.id == 7 || op.optionTemplate.id == 10 || op.optionTemplate.id == 68 || op.optionTemplate.id == 69 || op.optionTemplate.id == 73 || op.optionTemplate.id == 74) {
                                    for (byte k = 0; k < ItemManager.MOUNT_OPTION_ID.length; k++) {
                                        if (ItemManager.MOUNT_OPTION_ID[k] == op.optionTemplate.id) {
                                            op.param += ItemManager.MOUNT_OPTION_PARAM[k];
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (level == 20 || level == 40 || level == 60 || level == 80 || level == 100) {
                        for (byte i = 0; i < mount.options.size(); i++) {
                            ItemOption op = mount.options.get(i);
                            if (op.optionTemplate.id != 65 && op.optionTemplate.id != 66) {
                                if (op.optionTemplate.id == 67 || op.optionTemplate.id == 70 || op.optionTemplate.id == 71 || op.optionTemplate.id == 72 || op.optionTemplate.id == 118) {
                                    for (byte k = 0; k < ItemManager.MOUNT_OPTION_ID.length; k++) {
                                        if (ItemManager.MOUNT_OPTION_ID[k] == op.optionTemplate.id) {
                                            op.param += ItemManager.MOUNT_OPTION_PARAM[k];
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    getService().callEffectMini((short) 58);
                }
            }
            zone.getService().loadMount(this);
        } else {
            serverMessage("Thú cưới đã đạt cấp tối đa");
            return false;
        }

        return true;
    }


    public void setMobMe() {
        if (isInfected()) {
            setMobMe(233, 0);
        } else if (equipment != null && equipment[10] != null) {
            switch (equipment[10].id) {
                case 246:
                    setMobMe(70, (byte) 0);
                    break;
                case 419:
                    setMobMe(122, (byte) 0);
                    break;
                case 568:
                    setMobMe(205, (byte) 0);
                    break;
                case 569:
                    setMobMe(206, (byte) 0);
                    break;
                case 570:
                    setMobMe(207, (byte) 0);
                    break;
                case 571:
                    setMobMe(208, (byte) 0);
                    break;
                case 583:
                    setMobMe(211, (byte) 1);
                    break;
                case 584:
                    setMobMe(212, (byte) 1);
                    break;
                case 585:
                    setMobMe(213, (byte) 1);
                    break;
                case 586:
                    setMobMe(214, (byte) 1);
                    break;
                case 587:
                    setMobMe(215, (byte) 1);
                    break;
                case 588:
                    setMobMe(216, (byte) 1);
                    break;
                case 589:
                    setMobMe(217, (byte) 1);
                    break;
                case 742:
                case 744:
                    setMobMe(229, (byte) 1);
                    break;
                case ItemName.PET_THANH_LONG:
                    if (this.user.session.isVersionAbove(180)) {
                        setMobMe(258, (byte) 1);
                    } else {
                        setMobMe(0, (byte) 0);
                    }
                    break;
//                case ItemName.LONG_DEN_RONG:
//                    if (this.user.session.isVersionAbove(180)) {
//                        setMobMe(260, (byte) 1);
//                    } else {
//                        setMobMe(0, (byte) 0);
//                    }
//                    break;

                case 772:
                case 773:
                    setMobMe(234, (byte) 1);
                    break;
                case 945:
                    setMobMe(235, (byte) 1);
                    break;
                case 851:
                    setMobMe(238, (byte) 1);
                    break;
            }
        } else {
            setMobMe(0, (byte) 0);
        }
    }

    public void setMobMe(int id, int boss) {
        try {
            if (id > 0) {
                if (mobMe != null) {
                    if (mobMe.template.id == id) {
                        return;
                    }
                }
                Mob mob = new Mob((short) id, boss != 0);
                Equip e = equipment[ItemTemplate.TYPE_THUNUOI];
                if (e != null) {
                    int damageOnPlayer = 0;
                    int damageOnMob = 0;
                    for (ItemOption o : e.options) {
                        if (o.optionTemplate.id == ThanThu.ST_NGUOI_ID) {
                            damageOnPlayer = o.param;
                        }
                        if (o.optionTemplate.id == ThanThu.ST_QUAI_ID) {
                            damageOnMob = o.param;
                        }
                    }
                    mob.damageOnMob = damageOnMob;
                    mob.damageOnMob2 = damageOnMob - (damageOnMob / 10);
                    mob.damageOnPlayer = damageOnPlayer;
                    mob.damageOnPlayer2 = damageOnPlayer - (damageOnPlayer / 10);
                }
                mobMe = mob;

            } else {
                mobMe = null;
            }

            getService().loadMobMe();
        } catch (Exception e) {
            Log.logException("Set Mob me err: ", Char.class, e);
        }
    }

    public void joinZone(int map, int zoneId, int team) {

        if (team != -1) {
            Arena arena = (Arena) findWorld(World.ARENA);
            arena.join(team, this);
        } else {
            TileMap tile = MapManager.getInstance().getTileMap(map);
            if (tile.isDungeo()) {
                Dungeon dungeon = (Dungeon) findWorld(World.DUNGEON);
                dungeon.joinZone(this, map);
            } else if (tile.isDungeoClan()) {
                Territory territory = (Territory) findWorld(World.TERRITORY);
                territory.joinZone(this, map);
            } else {
                MapManager.getInstance().joinZone(this, map, zoneId);
            }
        }

    }

    public void outZone() {
        if (trade != null) {
            trade.closeUITrade();
        }
        if (this.group != null && this.group.memberGroups.size() <= 1) {
            outParty();
        }
        if (escorted != null) {
            escortFailed();
        }
        if (!isNhanBan) {
            if (isTest && testCharId != 0) {
                Char player = zone.findCharById(testCharId);
                if (player != null) {
                    testEnd(0, player);
                }
            }
            if (isCuuSat) {
                clearCuuSat();
            }
            if (zone != null && zone.getService() != null) {
                zone.getService().clearCuuSat(this);
            }
        }
        Arena arena = (Arena) findWorld(World.ARENA);
        if (arena != null) {
            arena.out(this);
        } else if (zone != null) {
            zone.out(this);
        }
        clearMap();
    }

    public void close() {
        try {
            if (isLoadFinish()) {
                if (trade != null) {
                    trade.closeUITrade();
                }
                if (arenaT != null) {
                    arenaT.setWin(false);
                }
                if (clone != null && !clone.isNhanBan) {
                    clone.timeCountDown = this.timeCountDown = 0;
                    clone.outZone();
                }

                if (worlds != null) {
                    synchronized (worlds) {
                        worlds.forEach((t) -> {
                            if (!t.isClosed()) {
                                t.removeMember(this);
                            }
                        });
                    }
                }
                if (zone != null) {
                    if (zone.tilemap.isDauTruong()) {
                        TalentShow tls = MapManager.getInstance().talentShow;
                        tls.removePlayer(this);
                    }
                }
                if (this.clan != null) {
                    Member mem = this.clan.getMemberByName(name);
                    if (mem != null) {
                        mem.setOnline(false);
                        mem.setChar(null);
                    }
                    clan.getClanService().requestClanMember();
                }
                if (this.group != null) {
                    outParty();
                }
                try {
                    History history = new History(this.id, History.OFFLINE);
                    for (Item item : this.bag) {
                        if (item != null) {
                            history.addItem(History.HANH_TRANG, item);
                        }
                    }
                    for (Item item : this.box) {
                        if (item != null) {
                            history.addItem(History.RUONG_DO, item);
                        }
                    }
                    for (Equip item : this.equipment) {
                        if (item != null) {
                            history.addItem(History.TRANG_BI, item);
                        }
                    }
                    for (Mount item : this.mount) {
                        if (item != null) {
                            history.addItem(History.THU_CUOI, item);
                        }
                    }
                    history.setBefore(this.coin, user.gold, this.yen);
                    history.setAfter(this.coin, user.gold, this.yen);
                    history.setIPAddress(user.session.IPAddress);
                    history.setTime(System.currentTimeMillis());
                    int check = History.insert(history);
                    if (check == 1) {
                        this.user.session.closeMessage();
                    }
                } catch (Exception e) {
                    Log.logException("Char close err", Char.class, e);
                }
            }
        } catch (Exception e) {
            Log.logException("Char close err (1)", Char.class, e);
        }
    }

    public void cleanTrade() {
        this.trade = null;
        this.myTrade = null;
        this.partnerTrade = null;
    }

    public void cleanUp() {
        this.isCleaned = true;
        this.bag = null;
        this.equipment = null;
        this.box = null;
        this.mount = null;
        this.mobMe = null;
        this.mob = null;
        this.mobFocus = null;
        this.enemy = null;
        this.friends = null;
        this.selectedSkill = null;
        this.taskMain = null;
        this.myTrade = null;
        this.trade = null;
        this.vSkill = null;
        this.vSupportSkill = null;
        this.vSkillFight = null;
        this.language = null;
        this.gloryTask = null;
        this.taskOrders = null;
        this.invite = null;
        if (this.worlds != null) {
            this.worlds.clear();
            this.worlds = null;
        }
        if (isHuman) {
            this.clone = null;
            Log.debug("clean " + this.name);
        } else {
            ((CloneChar) this).human = null;
            Log.debug("clean clone " + this.name);
        }
    }

    public Service getService() {
        return this.service;
    }

    public void serverMessage(String text) {
        getService().serverMessage(text);
    }

    public void serverDialog(String text) {
        getService().serverDialog(text);
    }

    public void inputInvalid() {
        getService().serverDialog("Số lượng không hợp lệ!");
    }

    public void clearMap() {
        if (getService() != null) {
            getService().clearMap();
        }
    }

    public void cancelClan() {
        if (this.clan != null) {
            if (this.clan.getNumberMember() <= 1) {
                Member member = clan.getMemberByName(this.name);
                if (member != null) {
                    clan.memberDAO.delete(member);
                }
                Clan.getClanDAO().delete(clan);
                zone.getService().moveOutClan(this);
                this.clan = null;
            } else {
                serverDialog("Chỉ có thể hủy gia tộc khi chỉ còn 1 thành viên.");
            }
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

    public void addBossVuiXuan(short bossX, short bossY) {
        getEventPoint().subPoint(LunarNewYear.MYSTERY_BOX_LEFT, 1);
        MobTemplate template = MobManager.getInstance().find(226);
        int bossHP = NinjaUtils.nextInt(this.maxHP * 100, this.maxHP * 200);
        Mob monster = new Mob(127, (short) template.id, bossHP, template.level, bossX, bossY, false, template.isBoss(), zone);
        monster.damageOnPlayer = this.maxHP;
        monster.damageOnPlayer2 = monster.damageOnPlayer - monster.damageOnPlayer / 10;
        monster.addCharId(this.id);
        this.mob = monster;
        getService().addMonster(this.mob);
    }

    public void rewardVuiXuan() {
        RandomCollection<Integer> rc = Event.getEvent().getItemsRecFromGold2Item();
        int itemId = rc.next();
        Item itm = ItemFactory.getInstance().newItem(itemId);
        itm.initExpire();

        if (itm.id == ItemName.THONG_LINH_THAO) {
            itm.setQuantity(NinjaUtils.nextInt(5, 10));
        } else if (itm.id == ItemName.MAT_NA_HO) {
            itm.randomOptionTigerMask();
        }

        addItemToBag(itm);
    }

    public Char getOriginChar() {
        Char killer = null;
        if (isNhanBan) {
            killer = ((CloneChar) this).human;
        } else {
            killer = this;
        }
        return killer;
    }

    // public void upgrade() {
    // addGold(NinjaUtils.nextInt(1000, 10000));
    // level = NinjaUtils.getLevel(this.exp);
    // if (this.level != 1) {
    // return;
    // }
    // int[] levels = {59, 70, 90, 99};
    // int levelRandom = levels[NinjaUtils.nextInt(3)];
    // long expAdd = NinjaUtils.getExpFromLevel(levelRandom);
    // addExp(expAdd);
    // this.level = NinjaUtils.getLevel(this.exp);
    // this.taskMain = null;
    // this.taskId = 29;
    // if (this.level >= 70) {
    // this.taskId = 43;
    // }
    //
    // admission((byte) NinjaUtils.nextInt(1, 6), (short) 12);
    // for (int i = 0; i <= 4; i++) {
    // Item itemMount = ItemFactory.getInstance().newItem(439 + i);
    // this.mount[i] = Converter.getInstance().toMount(itemMount);
    // if (i == 4) {
    // this.mount[i].upgrade = 99;
    // this.mount[i].sys = 4;
    // this.mount[i].randomOptionMount(true);
    // this.mount[i].fixMountOption();
    // }
    // }
    //
    // zone.getService().loadMount(this);
    //
    // this.numberUseExpanedBag = 4;
    // this.numberCellBag += 6 + 6 + 12 + 24;
    // Item[] bag = ItemFactory.getInstance().newItem[this.numberCellBag];
    // for (int numBag = 0; numBag < this.bag.length; numBag++) {
    // bag[numBag] = this.bag[numBag];
    // }
    // this.bag = bag;
    // Item itemx = ItemFactory.getInstance().newItem(1);
    // getService().expandBag(itemx);
    //
    // int itemLevel = 50;
    //
    // if (this.level >= 70) {
    // itemLevel = 60;
    //
    // for (int i = 0; i < 5; i++) {
    // reward[i] = true;
    // }
    // }
    //
    // if (this.level == 90) {
    // itemLevel = 80;
    // } else if (this.level == 99) {
    // itemLevel = 99;
    // }
    //
    // for (int i = 0; i < 10; i++) {
    // int bodyItemLevel = itemLevel + i;
    // int gender = 2;
    // if (bodyItemLevel % 2 != 0) {
    // gender = this.gender;
    // }
    // if (bodyItemLevel == 60) {
    // bodyItemLevel = 70;
    // }
    //
    // if (bodyItemLevel >= 81) {
    // break;
    // }
    // int sys = bodyItemLevel % 10 == 0 ? this.classId : this.getSys();
    //
    // ItemStore itemStore = Store.getItemBody(bodyItemLevel, sys, gender);
    // upgradeItemBody(itemStore);
    // }
    //
    // if (this.level == 90) {
    // List<Integer> exists = new ArrayList<Integer>();
    // for (ItemStore item : Store.ITEM_BODY) {
    // if (item != null && item.template.level == 85 && item.sys == getSys()
    // && (item.template.gender == this.gender || item.template.gender == 2)) { //
    // if (!exists.contains(item.template.id)) {
    // upgradeItemBody(item);
    // exists.add(item.template.id);
    // }
    // }
    // }
    // } else if (this.level == 99) {
    // int[] arrItem = {618, 620, 622, 624, 626, 628, 629, 630, 631};
    // for (int i = 0; i < arrItem.length; i++) {
    // int itemId = arrItem[i];
    // Item item = ItemFactory.getInstance().newItem9X(itemId);
    // int upgrade = NinjaUtils.nextInt(14, 16);
    // item.next(upgrade);
    // addItemToBag(item);
    // }
    // Item item = ItemFactory.getInstance().newItem9X(631 + this.classId);
    // int upgrade = NinjaUtils.nextInt(14, 16);
    // item.next(upgrade);
    // item.isLock = true;
    // addItemToBag(item);
    // }
    //
    // try {
    // ArrayList<Skill> skills = GameData.getInstance().getSkills(this);
    // for (Skill skillz : skills) {
    // if (skillz.level > this.level) {
    // continue;
    // }
    // Skill skill = Converter.getInstance().newSkill(skillz);
    // vSkill.add(skill);
    // if (skill.template.type == Skill.SKILL_AUTO_USE) {
    // vSupportSkill.add(skill);
    // setAbility();
    // } else if ((skill.template.type == Skill.SKILL_CLICK_USE_ATTACK
    // || skill.template.type == Skill.SKILL_CLICK_LIVE
    // || skill.template.type == Skill.SKILL_CLICK_USE_BUFF
    // || skill.template.type == Skill.SKILL_CLICK_NPC)
    // && (skill.template.maxPoint == 0 || (skill.template.maxPoint > 0 &&
    // skill.point > 0))) {
    //
    // vSkillFight.add(skill);
    // }
    // getService().useBookSkill((byte) itemx.index, (short) skill.id);
    // getService().loadSkill();
    // if (taskId == TaskName.NV_GIA_TANG_SUC_MANH && taskMain != null &&
    // taskMain.index == 0) {
    // taskNext();
    // taskNext();
    // }
    // }
    // } catch (Exception e) {
    //  Log.logException("err: " + e.getMessage(), e);
    // }
    //
    // if (this.level >= 70) {
    // for (int i = 222; i <= 228; i++) {
    // Item item = ItemFactory.getInstance().newItem(i);
    // addItemToBag(item);
    // }
    // }
    //
    // setFashion();
    // setAbility();
    // }
    // public void upgradeItemBody(ItemStore itemStore) {
    // if (itemStore == null) {
    // return;
    // }
    // Item add = ItemFactory.getInstance().newItem(itemStore.itemID);
    // add.sys = itemStore.sys;
    // int num = itemStore.option_max.length;
    // for (int a = 0; a < num; a++) {
    // int templateId = itemStore.option_max[a][0];
    // int param = itemStore.option_max[a][1] - (NinjaUtils.nextInt(1, 10));
    // add.options.add(new ItemOption(templateId, param));
    // }
    //
    // int upgrade = 12;
    // if (this.level == 59) {
    // upgrade = NinjaUtils.nextInt(12, 14);
    // } else {
    // upgrade = NinjaUtils.nextInt(13, 16);
    // }
    //
    // add.next(upgrade);
    // add.isLock = true;
    // addItemToBag(add);
    // }
    public void requestRanked(Message mss) {
        try {
            if (!(isVillage() || isSchool())) {
                return;
            }
            if (arenaT != null) {
                return;
            }
            byte index = mss.reader().readByte();
            String name = mss.reader().readUTF();
            if (index == 0) {
                if (this.name.equals(name)) {
                    return;
                }
                Char _c = ServerManager.findCharByName(name);
                if (_c == null) {
                    ThienDiaData thienDiaData = ThienDiaManager.getInstance().getThienDiaData(this.level, this.classId);
                    Ranking ranking = thienDiaData.getRankedByName(name);
                    if (ranking != null) {
                        _c = new Bot(ranking.getPlayerId());
                        ((Bot) _c).setUp();
                    }
                }
                if (_c != null) {
                    getService().viewInfo(_c);
                } else {
                    getService().serverMessage("Có lỗi xảy ra");
                }
            } else {
                if (this.name.equals(name)) {
                    getService().serverMessage("Không thể thách đấu chính bản thân mình");
                    return;
                }
                if (countArenaT <= 0) {
                    getService().serverMessage("Bạn đã hết lượt thi đấu. Xin tiếp tục vào ngày hôm sau.");
                    return;
                }
                Char _c = ServerManager.findCharByName(name);
                if (_c != null) {
                    _c.saveData();
                }
                ThienDiaData thienDiaData = ThienDiaManager.getInstance().getThienDiaData(this.level, this.classId);
                Ranking ranking1 = thienDiaData.getRankedByName(name);
                if (ranking1 == null) {
                    return;
                }
                if (ranking1.isFighting()) {
                    getService().serverMessage("Người này đang thi đấu");
                    return;
                }
                Ranking ranking2 = thienDiaData.getRankedByName(this.name);
                if (ranking2 == null) {
                    return;
                }
                if (ranking2.isFighting()) {
                    getService().serverMessage("Bạn đang thi đấu");
                    return;
                }
                if (Math.abs(ranking1.getRanked() - ranking2.getRanked()) <= 10) {
                    ranking1.setFighting(true);
                    ranking2.setFighting(true);
                    Bot bot = new Bot(ranking1.getPlayerId());
                    bot.setFashionStrategy(new FashionFromEquip());
                    bot.setAbilityStrategy(new AbilityFromEquip());
                    bot.setAttack(new AttackTarget(this));
                    bot.setMove(new MoveToTarget(this));
                    bot.setUp();
                    this.mapBeforeEnterPB = mapId;
                    Map map = MapManager.getInstance().find(MapName.LOI_DAI);
                    ArenaT arena = new ArenaT(0, map.tilemap, map);
                    arena.setRanking1(ranking1);
                    arena.setRanking2(ranking2);
                    arena.setThienDiaData(thienDiaData);
                    setArenaT(arena);
                    bot.setArenaT(arena);
                    arena.setBot(bot);
                    arena.setPlayer(this);
                    bot.setXY((short) 565, (short) 264);
                    bot.setTypePk(PK_DOSAT);
                    arena.join(bot);
                    bot.getEm().setEffect(new Effect(14, 10000, 0));
                    outZone();
                    setXY((short) 205, (short) 264);
                    em.setEffect(new Effect(14, 10000, 0));
                    arena.join(this);
                    if (clone != null && clone.isNhanBan && !clone.isDead) {
                        arena.join(clone);
                    }
                }
            }
        } catch (IOException ex) {
            Log.logException("request ranked err: ", Char.class, ex);
            serverDialog(ex.getMessage());
        }
    }

    public void addClanPoint(int point) {
        if (this.clan != null) {
            Member mem = this.clan.getMemberByName(this.name);
            if (mem != null) {
                mem.addPointClanWeek(point);
                mem.addPointClan(point);
                this.clan.addExp(point);
                this.serverMessage("Bạn nhận được " + point + " điểm cống hiến gia tộc.");
            }
        }
    }

    public boolean isBot() {
        return this instanceof Bot;
    }

    public boolean isHuman() {
        return this instanceof Char;
    }

    public boolean isClone() {
        return this instanceof CloneChar;
    }

    public boolean isNonCombatState() {
        return System.currentTimeMillis() < nonCombatTime;
    }

    public void setNonCombatState(int time) {
        Effect eff = new Effect(14, time * 1000L, 0);
        em.setEffect(eff);
        nonCombatTime = System.currentTimeMillis() + (time * 1000L);
    }

    private void doUseBijuu(Item item) {
        doUseEquipmentBijuu(item);
        setBijuu(item.id);
    }

    private void doUseEquipmentBijuu(Item item) {
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

    public boolean isVersionAbove(int version) {
        if (user != null && user.session != null) {
            return user.session.isVersionAbove(version);
        }
        return false;
    }

    public boolean isCool() {
        if (Event.isEvent() && Event.isNoel()) {
            if (((Noel) Event.getEvent()).isCoolTime()) {
                Effect eff = em.findByType((byte) 28);
                return eff == null;
            }
        }
        return false;
    }

    public void RewardClan() { /// t7 đen tối
        if (this.clan.topWarWeek == 0 || this.clan.topWarWeek > 10) return;
        if (this.clan.topWarWeek > 0) {
            if (this.clan.topWarWeek == 1) {//top1
                this.clan.addCoin(100000000);
            } else if (this.clan.topWarWeek == 2) {//top2
                this.clan.addCoin(70000000);
            } else if (this.clan.topWarWeek == 3) {//top3
                this.clan.addCoin(50000000);
            } else if (this.clan.topWarWeek >= 4 && this.clan.topWarWeek <= 10) {//top4-10
                this.clan.addCoin(20000000);
            }
            this.clan.topWarWeek = 0;
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