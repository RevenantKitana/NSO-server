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
public class Char extends CharPersistenceHandler {

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
                            int param = ItemStatCalculator.calculate(min[a][1], max[a][1], item.tier);
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



}
