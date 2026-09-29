package com.nsoz.bot;

import com.nsoz.item.Equip;
import com.nsoz.item.Item;
import com.nsoz.item.Mount;
import com.nsoz.model.Char;
import com.nsoz.network.NoService;
import com.nsoz.network.Service;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author PC
 */
public class Bot extends Char {

    @Setter
    private IAttack attack;

    @Setter
    @Getter
    private IMove move;

    public Bot(int id) {
        super(id);
    }

    @Builder
    public Bot(int id, String name, int level, byte typePk, byte classId) {
        super(id);
        this.name = name;
        this.level = level;
        this.typePk = typePk;
        this.classId = classId;
    }

    public void setDefault() {
        this.bag = new Item[0];
        this.box = new Item[0];
        this.equipment = new Equip[16];
        this.fashion = new Equip[16];
        this.mount = new Mount[5];
        this.bijuu = new Item[5];
    }

    public void recovery() {
        this.hp = this.maxHP;
        this.mp = this.maxMP;
        this.isDead = false;
    }

    public void setUp() {
        loadDisplay();
        load();
        setAbility();
        this.hp = this.maxHP;
        this.mp = this.maxMP;
        setFashion();
    }

    public Service getService() {
        return NoService.getInstance();
    }

    @Override
    public void addMp(int add) {

    }

    @Override
    public void updateEveryHalfSecond() {
        try {
            super.updateEveryHalfSecond(); //To change body of generated methods, choose Tools | Templates.
        } finally {
            if (attack != null) {
                attack.attack(this);
            }
            if (move != null) {
                move.move(this);
            }
        }
    }

    public static void spawnFriendBots() {
        try {
            // Gió / Sét: Trường Haruna (MapName.TRUONG_HARUNA = 27)
            spawnBot(-1001, "Thor", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_HARUNA, (short) 356, (short) 264, true); // Nam
            spawnBot(-1002, "Iris", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_HARUNA, (short) 400, (short) 264, false); // Nữ

            // Lửa / Sáng: Trường Ookaza (MapName.TRUONG_OOKAZA = 72)
            spawnBot(-1003, "Apollo", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_OOKAZA, (short) 356, (short) 264, true); // Nam
            spawnBot(-1004, "Brigid", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_OOKAZA, (short) 400, (short) 264, false); // Nữ

            // Băng / Nước: Trường Hirosaki (MapName.TRUONG_HIROSAKI = 1)
            spawnBot(-1005, "Njord", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_HIROSAKI, (short) 356, (short) 264, true); // Nam
            spawnBot(-1006, "Skadi", 10, (byte) 0, (byte) 1, com.nsoz.constants.MapName.TRUONG_HIROSAKI, (short) 400, (short) 264, false); // Nữ
        } catch (Exception e) {
            com.nsoz.util.Log.logException("Spawn bot error", Bot.class, e);
        }
    }

    private static void spawnBot(int id, String name, int level, byte typePk, byte classId, int mapId, short x, short y, boolean isMale) {
        Bot bot = Bot.builder()
                .id(id)
                .name(name)
                .level(level)
                .typePk(typePk)
                .classId(classId)
                .build();
        bot.isHuman = false;
        bot.gender = (byte) (isMale ? 1 : 0);
        bot.setDefault();
        bot.setUp();
        bot.setXY(x, y);
        com.nsoz.map.Map map = com.nsoz.map.MapManager.getInstance().find(mapId);
        if (map != null && map.getZones() != null && !map.getZones().isEmpty()) {
            com.nsoz.map.zones.Zone zone = map.getZones().get(0); // Thêm vào khu 0
            if (zone != null) {
                zone.join(bot);
            }
        }
    }

}
