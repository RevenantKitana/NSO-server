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
 * CharBase: Lớp thực thể cơ sở chứa toàn bộ thuộc tính, hằng số và các method nền tảng của nhân vật.
 * Phân rã từ Char.java (Phase 1 Refactoring).
 */
public abstract class CharBase {

    public static final byte PK_NORMAL = 0;
    public static final byte PK_NHOM = 1;
    public static final byte PK_PHE = 1;
    public static final byte PK_BANG = 2;
    public static final byte PK_DOSAT = 3;
    public static final byte PK_PHE1 = 4;
    public static final byte PK_PHE2 = 5;
    public static final byte PK_PHE3 = 6;
    public static final byte RUONG_DO = 0;
    public static final byte BO_SUU_TAP = 1;
    public static final byte CAI_TRANG = 2;
    public static final byte DOI_LONG_DEN_XU = 3;
    public static final byte DOI_LONG_DEN_LUONG = 4;
    public static final byte THANG_NGUONG = 5;
    public static final byte THANG_CAP = 6;
    public static final byte GIA_HAN = 7;
    public static final byte STORE_UPGRADE = 8; // shop 16

    public static final byte HOA_HINH_THU = 16;

    public static final byte CAI_TAO = 13; // hóa hình
    public static final byte HOA_HINH = 14;
    public static final byte PHAN_TACH = 15;

    protected static final long[] EXPIRE_DATE_OF_TIME = new long[]{259200000, 604800000};
    protected static final String[] AUTO_PICK_ITEM = new String[]{"Không nhặt gì cả", "Nhặt tất cả", "Nhặt v.phẩm hữu ích", "Nhặt theo danh sách"};
    public static HashMap<String, Lock> lockGiftCodes = new HashMap<>();
    public static int[] DONG_XU = {0, 0, 0, 0, 0};
    public static ExecutorService threadPool = Executors.newFixedThreadPool(500);
    protected static final int[] optionBikip;
    protected static final int[] paramBikip;
    protected static final int[] percentBikip;
    protected static final int[] fee;
    protected static final int[] danangcap;
    protected static final int[] optionmathactinh;
    protected static final int[] parammathactinh;
    protected static final int[] percentmathactinh;

    protected LocalDateTime lastSoloTrialDungeonTime;
    public boolean hasCompletedSoloTrialToday;

    static {


        optionBikip = new int[]{84, 86, 87, 88, 89, 90, 91, 92, 94, 95, 96, 97, 82, 83};
        paramBikip = new int[]{35, 35, 1000, 600, 600, 600, 30, 10, 20, 50, 50, 50, 1000, 1000};
        percentBikip = new int[]{80, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20, 7, 5, 4, 3, 1};
        fee = new int[]{1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600, 2800, 3000, 3100, 3200, 3300, 3400, 3500, 3600};
        danangcap = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85};

        optionmathactinh = new int[]{84, 86, 87, 88, 89, 90, 91, 92, 95, 96, 97, 82, 83};// id mat param
        parammathactinh = new int[]{50, 50, 1000, 500, 500, 500, 50, 10, 100, 100, 100, 600, 600};// chỉ số
        percentmathactinh = new int[]{70, 65, 60, 55, 50, 45, 40, 35, 25, 15, 15, 10, 5, 5, 5, 3};// tỉ lệ
    }

    public int id;
    public User user;
    public long timeCanGiveaways;
    public String name;
    public byte gender;
    public String school;
    public byte classId;
    public int level;
    public short head, original_head;
    public short weapon;
    public short body;
    public short leg;
    public short coat;
    public short glove;
    @Getter
    public long coin, coinInBox;
    public long coinMax;
    public int invite_player;
    public int gtcId = -1;
    public int pointGTC = 0;
    public int passProtech = 0;
    public int typeProtect = 0;
    public int pheGtc = 0;
    @Getter
    public long yen;
    public volatile int hp, maxHP;
    public volatile int mp, maxMP;
    public int damage, damage2, basicAttack;
    public int dameDown;
    public int exactly, miss, fatalDame, percentFatalDame;
    public int resFire, resIce, resWind, fatal, reactDame, sysUp, sysDown;
    public long exp, expDown;
    public long lastLoginTime1;
    public byte hieuChien, typePk;
    public Clan clan;
    public int[] potential;
    public Vector<Skill> vSkill, vSkillFight, vSupportSkill;
    public short potentialPoint, skillPoint;
    public int countDoneTaskDay, pointUyDanh, pointNon, pointVuKhi, pointAo, pointLien, pointGangTay, pointNhan, pointQuan, pointNgocBoi, pointGiay, pointPhu, pointTinhTu, countFinishDay, countLoopBoss, limitTiemNangSo, limitKyNangSo, limitPhongLoi, limitBangHoa, countPB, pointPB, countUseItemGlory, countUseItemDungeo, countUseItemBeast, countGlory, countArenaT;
    public byte speed;
    public byte numberCellBag, numberCellBox;
    public Zone zone;
    public short mapId, x, y, preX, preY;
    public short eff5buffhp, eff5buffmp;
    public byte captcha = 0;
    public byte[] onKSkill;
    public byte[] onOSkill;
    public byte[] onCSkill;
    public Item[] bag, box, bijuu, ItemExtend;
    public Equip[] equipment, fashion;
    public Trade trade;
    public Trader myTrade;
    public Trader partnerTrade;
    public int tayTiemNang, tayKyNang;
    public int[] options, optionsSupportSkill;
    public boolean[] haveOptions;
    public short saveCoordinate;
    public Mount[] mount;
    public Skill selectedSkill;
    public boolean isDead;
    public boolean isChangeMap;
    public HashMap<String, Friend> friends, enemies;
    public byte numberUseExpanedBag;
    @Setter
    public Service service;
    public long lastLogoutTime;
    public long lastLoginTime;
    public long levelUpTime;
    public short taskId = 1;
    public Task taskMain;
    public byte nAnswer = 0;
    public long lastTimeChatGlobal;
    public long lastTimeTeleport;
    public long lastTimeUseHp;
    public long lastTimeUseMp;
    public Mob mobMe, mobBijuu;
    public Mob mobFocus;
    public Char enemy;
    public boolean isCatchItem = false;
    public boolean isFailure = false;
    public Language language;
    public int testCharId = 0;
    public boolean isTest = false;
    public int mobAtk = -1;
    public int killCharId = 0;
    public boolean isCuuSat = false;
    public boolean[] reward;
    public boolean receivedRewardPB;
    public boolean isInNoPkPenaltyMap = false;
    public int mapBeforeEnterPB;
    public int rewardPB;
    public String message = "";
    public long lastEffect = 0L;
    public int haoQuang = -1;
    public int honor; // vinh danh
    public GloryTask gloryTask;
    public ArrayList<TaskOrder> taskOrders;
    public War war;
    public short ID_BODY = -1;
    public short ID_PP = -1;
    public short ID_HAIR = -1;
    public short ID_LEG = -1;
    public short ID_HORSE = -1;
    public short ID_NAME = -1;
    public short ID_RANK = -1;
    public short ID_MAT_NA = -1;
    public short ID_BIEN_HINH = -1;
    public short ID_WEA_PONE = -1;
    public boolean isHuman;
    public boolean isNhanBan;
    public Invite invite;
    public CloneChar clone;
    public int timeCountDown;
    public int huyHieu;
    public Lock lock = new ReentrantLock();
    public Lock lockItem = new ReentrantLock();
    public boolean isCleaned;
    public boolean isInvisible, isHide, isTNP, isKNP, isBiMa, isEffExp, isEffDameDown, isEffSkipResistance, isIce, isWind, isMayDo, isFire, isDontMove, isMiss, isShieldMana, isEffBong;
    public int incrExp, incrHP, incrDame, incrDame2, incrDame3, incrMiss, incrRes1, incrRes2, incrExactly;
    public Mob mob;
    public boolean online;
    // public long lastMessageSentAt = System.currentTimeMillis();
    public boolean isModeCreate;
    public boolean isModeAdd;
    public boolean isModeRemove;
    public boolean notReceivedExp;
    // chiến trường
    public int warPoint;
    public byte faction;
    public long time;
    public WarMember member;
    public boolean isRewarded;
    public boolean isRewardedGtc;
    public boolean isViewListFriend;
    public int nKill;
    public int nDead;
    public int nKillGtc;
    public int nDeadGTC;
    public long lastTimeReductionDame = 0L;
    public boolean isReductionDame = false;
    public int stoneItemId = -1;
    public long lastTimeOutClan = 0;
    @Getter
    public ArrayList<Menu> menus;
    @Getter
    public boolean isLockFire;
    public long nonCombatTime = 0;
    public int[] quaTichLuy;
    @Getter
    @Setter
    protected EffectManager em;
    protected boolean saving;
    byte checkIndexUI;
    byte IndexUI;
    @Getter
    @Setter
    protected int topvxmm;
    public long delayvithu;
    @Getter
    @Setter
    protected int topvxmmLuong;
    @Getter
    @Setter
    protected int topBoss;
    @Getter
    @Setter
    protected int topThanNong;
    @Getter
    @Setter
    protected Group group;
    protected boolean isUnpaid;
    protected boolean isYenUnpaid;
    protected boolean isCoinUnpaid;
    public long timeChangeMap; // fix map lc

    protected boolean isGiftCodeUnpaid;
    protected volatile CharacterStatsSnapshot statsSnapshot;

    public CharacterStatsSnapshot getStatsSnapshot() {
        if (this.statsSnapshot == null) {
            this.statsSnapshot = CharacterStatsSnapshot.from((Char) this);
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
    public Item[] ItemPhanTach;
    public int[] IndexInBag;
    public byte indexMenuBox;
    public int typekhaihoa;

    @Setter
    protected byte viewAuctionTab;
    @Getter
    @Setter
    protected ConfirmPopup confirmPopup;
    @Getter
    @Setter
    protected ArrayList<Item> maskBox;
    @Getter
    @Setter
    protected ArrayList<Item> collectionBox;
    @Setter
    @Getter
    protected byte commandBox;
    @Setter
    @Getter
    protected Item mask;
    @Setter
    @Getter
    protected int maskId;
    @Getter
    @Setter
    protected boolean isLoadFinish;
    @Setter
    @Getter
    protected boolean isInfected;
    @Getter
    @Setter
    protected InputDialog input;
    @Setter
    protected ArenaT arenaT;
    protected int numberMissed;
    @Setter
    @Getter
    protected boolean leading;
    @Setter
    @Getter
    protected Bot escorted, escortedEvent;
    protected final List<Integer> listCanEnterMap;
    protected int sevenBeastsID;
    protected long typeSevenBeasts;
    @Getter
    @Setter
    protected Room room;
    protected List<World> worlds;// danh sách world đang tham gia
    protected int countEnterFujukaSanctuary;
    @Getter
    @Setter
    protected boolean isAutoPlay;
    @Getter
    @Setter
    protected int range = -1;
    @Getter
    @Setter
    protected int typeAutoPickItem = 0;
    @Getter
    @Setter
    protected int groupIndex;
    @Setter
    protected EventPoint eventPoint;
    @Setter
    protected FashionStrategy fashionStrategy;
    @Setter
    protected AbilityStrategy abilityStrategy;
    @Getter
    @Setter
    protected AbsSelectCard selectCard;
    protected IChat chatGlobal;
    public int timeOnline;
    public int[] giftOnline = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    /// điểm danh tân thủ
    public int[] giftTongnap = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    // 5 Domain Components (Milestone 3 Architecture)
    public SpatialComponent spatial;
    public CharacterStateComponent state;
    public InventoryComponent inventory;
    public CombatComponent combat;
    public SocialComponent social;


    public Char asChar() { return (Char) this; }

    public CharBase(int id) {
        this.id = id;
        this.options = new int[ItemManager.getInstance().getOptionSize()];
        this.isHuman = true;
        this.isNhanBan = false;
        this.menus = new ArrayList<>();
        this.listCanEnterMap = new ArrayList<>();
        this.worlds = new ArrayList<>();
    }

    public EventPoint getEventPoint() {
//        if(eventPoint == null){
//            initEventPoint();
//        }
        return eventPoint;
    }

    public void addWorld(World world) {
        synchronized (worlds) {
            if (worlds.stream().noneMatch(w -> w.getType() == world.getType())) {
                worlds.add(world);
                Log.debug("add worldType: " + world.getType());
            }
        }
    }

    public void removeWorld(byte type) {
        synchronized (worlds) {
            worlds.removeIf((t) -> t.getType() == type);
            Log.debug("remove worldType: " + type);
        }
    }

    public World findWorld(byte type) {
        if (worlds != null) {
            synchronized (worlds) {
                for (World world : worlds) {
                    if (world.getType() == type) {
                        return world;
                    }
                }
                return null;
            }
        } else {
            return null;
        }
    }

    // di chuyển map khi làm nvct
    public void initListCanEnterMap() {
        listCanEnterMap.clear();
        listCanEnterMap.add(MapName.LANG_TONE);
        if (taskId >= TaskName.NV_DIET_SEN_TRU_COC) {
            listCanEnterMap.add(MapName.DOI_FUMIMEN);
            listCanEnterMap.add(MapName.VACH_ICHIDAI);
        }
        if (taskId >= TaskName.NV_HAI_THUOC_CUU_NGUOI) {
            listCanEnterMap.add(MapName.THAC_KITAJIMA);
            listCanEnterMap.add(MapName.CHAN_THAC_KITAJIMA);
        }
        if (taskId >= TaskName.NV_KHAM_PHA_XA_LANG) {
            listCanEnterMap.add(MapName.DOI_KOKORO);
            listCanEnterMap.add(MapName.VACH_AINODAKE);
            listCanEnterMap.add(MapName.THUNG_LUNG_CHET);
            listCanEnterMap.add(MapName.KHU_LUYEN_TAP);
            listCanEnterMap.add(MapName.RUNG_GIA);
            listCanEnterMap.add(MapName.CANH_DONG_FUKI);
        }
        if (taskId >= TaskName.NV_TIM_HIEU_3_TRUONG) {
            listCanEnterMap.add(MapName.TRUONG_HARUNA);
            listCanEnterMap.add(MapName.TRUONG_HIROSAKI);
            listCanEnterMap.add(MapName.TRUONG_OOKAZA);
        }
        if (taskId >= TaskName.NV_BAI_HOC_DAU_TIEN) {
            listCanEnterMap.add(MapName.DONG_HACHI);
            listCanEnterMap.add(MapName.RUNG_DAO_SAKURA);
            listCanEnterMap.add(MapName.KY_TUC_XA_HARUNA);
            listCanEnterMap.add(MapName.CUA_HANG_AKA);
            listCanEnterMap.add(MapName.SONG_BANG_YAMATO);
            listCanEnterMap.add(MapName.HO_STUKI);
        }
        if (taskId >= TaskName.NV_THU_THAP_NGUYEN_LIEU) {
            listCanEnterMap.add(MapName.HANG_AKA);
            listCanEnterMap.add(MapName.CANH_DONG_HIYA);
            listCanEnterMap.add(MapName.RUNG_TRUC_UTRA);
        }
        if (taskId >= TaskName.NV_TRUYEN_TAI_TIN_TUC) {
            listCanEnterMap.add(MapName.RUNG_MISHIMA);
            listCanEnterMap.add(MapName.SONG_WATAMARO);
            listCanEnterMap.add(MapName.MUI_NURANURA);
            listCanEnterMap.add(MapName.SUOI_AKAGI);
            listCanEnterMap.add(MapName.BO_BIEN_OURA);
        }

        // full map tu nv lv22

//        if (taskId >= TaskName.NV_REN_LUYEN_THE_LUC) {
//            listCanEnterMap.clear();
//            for (int i = 0; i <= 176; i++) {
//                listCanEnterMap.add(i);
//            }
//        }

        if (taskId >= TaskName.NV_REN_LUYEN_THE_LUC) {
            listCanEnterMap.add(MapName.HANG_HA);
            listCanEnterMap.add(MapName.HEM_NUI_TAKANA);
            listCanEnterMap.add(MapName.NGHIA_DIA_IZUKO);
        }
        if (taskId >= TaskName.NV_DUA_JAIAN_TRO_VE) {
            listCanEnterMap.add(MapName.LANG_CHAI);
            listCanEnterMap.add(MapName.CUA_BIEN_KAWAGUCHI);
            listCanEnterMap.add(MapName.RUNG_MOSHIO);
        }
        if (taskId >= TaskName.NV_TIM_NGUYEN_LIEU_LAM_THUOC) {
            listCanEnterMap.add(MapName.LANG_OSHIN);
            listCanEnterMap.add(MapName.RUNG_KANASHII);
            listCanEnterMap.add(MapName.LANG_KOJIN);
            listCanEnterMap.add(MapName.MIEU_KAMO);
        }
        if (taskId >= TaskName.NV_LAY_NUOC_HANG_SAU) {
            listCanEnterMap.add(MapName.DEN_AMATERASU);
        }
        if (taskId >= TaskName.NV_VUOT_QUA_THU_THACH) {
            listCanEnterMap.add(MapName.MIEU_OBOKO);
            listCanEnterMap.add(MapName.DAO_HEBI);
            listCanEnterMap.add(MapName.RUNG_TOGE);
            listCanEnterMap.add(MapName.SAN_SAU_MIEU_OBOKO);
        }
        if (taskId >= TaskName.NV_THU_THAP_CHIA_KHOA) {
            listCanEnterMap.add(MapName.HANG_MEIRO);
        }
        if (taskId >= TaskName.NV_TRUY_TIM_BAO_VAT) {
            listCanEnterMap.add(MapName.RUNG_KAPPA);
            listCanEnterMap.add(MapName.KHE_NUI_CHOROCHORO);
        }
        if (taskId >= TaskName.NV_REN_LUYEN) {
            listCanEnterMap.add(MapName.RUNG_GO_KOUJI);
        }
        if (taskId >= TaskName.NV_THU_THAP_TINH_THE_BANG) {
            listCanEnterMap.add(MapName.HANG_KUGYOU);
        }
        if (taskId >= TaskName.NV_THU_THAP_XAC_DOI_LUA) {
            listCanEnterMap.add(MapName.RUNG_AOKIGAHARA);
        }
        if (taskId >= TaskName.NV_KIEN_TRI_DIET_AC) {
            listCanEnterMap.add(MapName.LANG_CHAKUMI);
            listCanEnterMap.add(MapName.VACH_NUI_ITO);
            listCanEnterMap.add(MapName.NUI_ANZEN);
            listCanEnterMap.add(MapName.NUI_ONTAKE);
            listCanEnterMap.add(MapName.THUNG_LUNG_TAIRA);
        }
        if (taskId >= TaskName.NV_DU_TRU_LUONG_THUC) {
            listCanEnterMap.add(MapName.NUI_ANZEN);
            listCanEnterMap.add(MapName.NUI_ONTAKE);
            listCanEnterMap.add(MapName.THUNG_LUNG_TAIRA);
        }
        if (taskId >= TaskName.NV_TUAN_HOAN) { // NV_DIET_MA
            listCanEnterMap.add(MapName.LANG_SANZU);
            listCanEnterMap.add(MapName.KHU_DA_DO_AKAI);
            listCanEnterMap.add(MapName.LANG_ECHIGO);
            listCanEnterMap.add(MapName.KHU_DA_DO_AIKO);
            listCanEnterMap.add(MapName.MUI_HONE);
            listCanEnterMap.add(MapName.DINH_ICHIDAI);
            listCanEnterMap.add(MapName.DONG_KISEI);
            listCanEnterMap.add(MapName.NUI_HASHIGOTO);
            listCanEnterMap.add(MapName.HANG_CHI);
            listCanEnterMap.add(MapName.DINH_OKAMA);
            listCanEnterMap.add(MapName.HANG_NUI_KURAI);
            listCanEnterMap.add(MapName.DONG_TAMATAMO);
            listCanEnterMap.add(MapName.DEN_HARUMOTO);
            listCanEnterMap.add(MapName.PHONG_AN_OUNIO);
            listCanEnterMap.add(MapName.SAN_DEN_OROCHI);
            listCanEnterMap.add(MapName.NGOI_DEN_OROCHI);
            listCanEnterMap.add(MapName.SAN_SAU_DEN_OROCHI);
        }
    }

    public boolean isCanEnterMap(int map) {
        if (map == MapName.NHA_THI_DAU_HARUNA || map >= 72 || map == -1) {
            return true;
        }
        for (int m : listCanEnterMap) {
            if (m == map) {
                return true;
            }
        }
        return false;
    }

    public boolean isInvite() {
        return invite_player == 1;
    }

    public String getRank() {
        String result;
        if (this.pointGTC >= 4000) {
            result = "Nhẫn Giả";
        } else if (this.pointGTC >= 1500) {
            result = "Thượng Nhẫn";
        } else if (this.pointGTC >= 600) {
            result = "Trung Nhẫn";
        } else if (this.pointGTC >= 200) {
            result = "Hạ Nhẫn";
        } else {
            result = "Học Giả";
        }
        return result;
    }

    public void setLanguage(Language lang) {
        this.language = lang;
    }

    public void escortEventFailed() {
        zone.out(escortedEvent);
        serverMessage(String.format("Hộ tống %s thất bại!", escortedEvent.name));
        setEscortedEvent(null);
        setLeading(false);
    }

    public void escortEventFinish() {
        zone.out(escortedEvent);
        serverMessage(String.format("Hộ tống %s thành công!", escortedEvent.name));
        setEscortedEvent(null);
        setLeading(false);
        // Item
        if (Event.isTrungThu()) {
            TrungThuNew trungThu = (TrungThuNew) Event.getEvent();
            trungThu.escortFinish((Char) this);
        }
    }


    // --- Abstract methods implemented in Char ---
    public abstract void serverDialog(String text);
    public abstract void serverMessage(String text);
    public abstract Service getService();
    public abstract int getSlotNull();
    public abstract void warningTrade();
    public abstract void warningBagFull();
    public abstract void warningClone();
    public abstract int getIndexItemByIdInBag(int id);
    public abstract int getIndexItemByIdInBag(int id, boolean isLock);
    public abstract Item getItemByIdInBag(int id);
    public abstract int getQuantityItemById(int id);
    public abstract boolean isVillage();
    public abstract boolean isSchool();
    public abstract boolean isVersionAbove(int version);
    public abstract void setTypePk(byte typePk);
    public abstract void setXY(short... xy);
    public abstract void outZone();
    public abstract void joinZone(int map, int zoneId, int team);
    public abstract void changeMap(int id);
    public abstract void setAbility();
    public abstract void setFashion();
    public abstract void addExp(long exp);
    public abstract void addExp(Mob mob, int dame);
    public abstract boolean addItemToBag(Item item);
    public abstract void removeItem(int index, int quantity, boolean isUpdate);
    public abstract void removeItemBags(final int id, final int quantity);
    public abstract Skill getSkill(int templateId);
    public abstract void selectSkill(short templateId);
    public abstract void selectSkill(Message ms);
    public abstract Item getItemInMaskBoxWithSys(int id, byte sys);
    public abstract void tayTiemNang(short npcId);
    public abstract void tayKyNang(short npcId);
    public abstract int checkThangNguong(Item item);
    public abstract int checkThangCap(Item item);
        public abstract Item getItemInCollectionBox(int id);
        public abstract void addWarClanPoint(short point);
    public abstract void addWarPoint(short point);
    public abstract Char getOriginChar();
    public abstract boolean isBot();
    public abstract boolean isCool();
    public abstract boolean isInvisible();
    public abstract boolean isNonCombatState();

    public abstract int checkTypeItem(Item item);
    public abstract void updateItemKhaiHoa(Item item);
    public abstract void updateHPMount(int hp);
    public abstract List<Item> getListItem5sao(int... ids);
    public abstract void clearTask();
    public abstract void taskNext();
    public abstract void updateTask();
    public abstract void updateTaskCount(int count);
    public abstract byte getTaskNpcId();
    public abstract void showRankedList(int type);
    public abstract void close();
    public abstract void hoaHinh();
    public abstract void phanTach();
    public abstract byte getSys();
    public abstract void addClanPoint(int point);
    public abstract WarMember getMemberNormalWar();
    public abstract void outParty();
    public abstract void cancelClan();
    public abstract void RewardClan();
    public abstract void updatePointPB(int point);
    public abstract void addGold(int gold);
    public abstract void addCoin(long xu);
    public abstract void addYen(long yen);
    public abstract void saveData();
    public abstract boolean addExpForMount(int exp, byte type);
    public abstract void addPointPk(int p);
    public abstract void cure();
    public abstract void doUseBijuu(Item item);
    public abstract void doUseEquipmentBijuu(Item item);
    public abstract void expandBag(Item item);
    public abstract Item getItemInMaskBox(int id);
    public abstract void learnSkill(Item item);
    public abstract boolean openMoreOptionForMount();
    public abstract boolean upgradeMount();
    public abstract void pieceTogether(Item item);
}