package com.nsoz2.model;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nsoz2.ability.AbilityCustom;
import com.nsoz2.ability.AbilityFromEquip;
import com.nsoz2.ability.AbilityStrategy;
import com.nsoz2.admin.AdminService;
import com.nsoz2.bot.Bot;
import com.nsoz2.bot.attack.AttackTarget;
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
 * CharCombatHandler handles combat, attack algorithms (PvE & PvP), skills,
 * buffs, duels (loi dai), assassinations (cuu sat), death, revival, and PK states.
 */
public abstract class CharCombatHandler extends CharBlacksmithHandler {

    public CharCombatHandler(int id) {
        super(id);
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

    public int getSkillOptParam(Char c, int id) {
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
                    zone.getService().teleport((Char) this);
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
                zone.getService().setSkillPaint_1(mobss, (Char) this, (byte) selectedSkill.template.id);
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
                        this.clone = new CloneChar((Char) this, options[1].param);
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
                    buNhin.owner = (Char) this;
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
                    zone.getService().setSkillPaint_1(targets, (Char) this, (byte) skill.template.id);
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
                                zone.getService().setSkillPaint_2(chars, (Char) this, (byte) selectedSkill.template.id);
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
                zone.getService().setSkillPaint_1(targets, (Char) this, (byte) selectedSkill.template.id);
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
                    zone.getService().playerRemoveEffect((Char) this, eff);
                    em.removeEffect(eff);
                }
            }
            // zone.lockChar.lock();
            // try {
            zone.getService().setSkillPaint_1(mobs, (Char) this, (byte) selectedSkill.template.id);
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
                            int dameHit = CombatService.calculatePvEDamage((Char) this, mob, mobIsFatal, add);
                            if (dameHit > 0) {
                                mob.Fight((Char) this, dameHit); // fix dame boss
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
                                    Territory.checkEveryAttack((Char) this);
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
                        dun.addExp((Char) this, expGroup); // exp hang động
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
//                zone.getService().playerRemoveEffect((Char) this, eff);
//                em.removeEffect(eff);
//            }
//        }
//        zone.getService().setSkillPaint_2(chars, (Char) this, (byte) selectedSkill.template.id);
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
//                    mobMe.attack(pl, (Char) this);
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
//                            zone.getService().attackCharacter(reactDame, 0, (Char) this);
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
                zone.getService().playerRemoveEffect((Char) this, eff);
                em.removeEffect(eff);
            }
        }
        zone.getService().setSkillPaint_2(chars, (Char) this, (byte) selectedSkill.template.id);
        for (Char pl : chars) {
            if (mapId != 138 && !pl.isTest && (isVillage() || isSchool())) {
                continue;
            }
            if (selectedSkill.template.type == 1) {
                if (mobMe != null && mobMe.damageOnPlayer > 0) {
                    long now = System.currentTimeMillis();
                    if (now - mobMe.lastTimeAttack > (mobMe.attackDelay + 2000)) {
                        mobMe.lastTimeAttack = now;
                        mobMe.attack(pl, (Char) this);
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
                    CombatResult result = CombatService.calculatePvPDamage((Char) this, pl, playerIsFatal);

                    if (!isNhanBan && result.getReactDamage() > 0) {
                        int reactDame = result.getReactDamage();
                        if (result.isIceReflect()) {
                            zone.getService().addEffect((Char) this, 64, 5, 5, 0);
                        }
                        if (this.hp - reactDame > 1) {
                            addHp(-reactDame);
                        } else {
                            reactDame = Math.max(1, this.hp - 1);
                            addHp(-reactDame);
                        }
                        zone.getService().attackCharacter(reactDame, 0, (Char) this);
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
                zone.getService().setSkillPaint_1(mobss, (Char) this, (byte) selectedSkill.template.id);
                ArrayList<Char> chars2 = new ArrayList<>();
                for (Char _char : m2) {
                    chars2.add(_char);
                    zone.getService().attackCharacter(0, 0, _char);
                }
                zone.getService().setSkillPaint_2(chars2, (Char) this, (byte) selectedSkill.template.id);
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
        int templateID = mob.template.id;
        return !((templateID == MobName.VET_THU_HO && this.typePk == Char.PK_PHE1) || (templateID == MobName.BACH_LONG_TRU && this.typePk == Char.PK_PHE1) || (templateID == MobName.ONG_TU_SI && this.typePk == Char.PK_PHE2) || (templateID == MobName.HAC_LONG_TRU && this.typePk == Char.PK_PHE2) || (templateID == MobName.LAO_NHI && this.typePk == Char.PK_PHE1) || (templateID == MobName.LAO_TAM && this.typePk == Char.PK_PHE2) || (templateID == MobName.LAO_DAI && this.typePk == Char.PK_PHE3) || (templateID == MobName.THUY_TINH && typePk == Char.PK_PHE1) || (templateID == MobName.SON_TINH && typePk == Char.PK_PHE2));
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
//                    zone.getService().teleport((Char) this);
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
//                zone.getService().setSkillPaint_2(charAttackNoDame, (Char) this, (byte) selectedSkill.template.id);
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
                    zone.getService().teleport((Char) this);
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
                zone.getService().setSkillPaint_2(charAttackNoDame, (Char) this, (byte) selectedSkill.template.id);
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
        zone.returnTownFromDead((Char) this);
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
            zone.getService().returnPointMap((Char) this); /// fix chết trong map ninja tài năng
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
            zone.startDie((Char) this);

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
            zone.getService().clearCuuSat((Char) this);

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
                if (this.zone.map.clanWar.gt1.contains((Char) this)) {
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
            zone.getService().playerMove((Char) this);
            getService().resetPoint();
            this.isDead = true;

            this.hp = 0;
            if (this.expDown > 0) {
                getService().meDieExpDown();
            } else {
                getService().meDie();
            }

            zone.getService().waitToDie((Char) this);
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

    public void cure() {
        setInfected(false);
        setFashion();
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
            zone.getService().changePk((Char) this);
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
}
