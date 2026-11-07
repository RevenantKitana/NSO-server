/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz2.map.zones;

import com.nsoz2.constants.MapName;
import com.nsoz2.constants.MobName;
import com.nsoz2.map.Map;
import com.nsoz2.map.TileMap;
import com.nsoz2.mob.Mob;
import com.nsoz2.mob.MobManager;
import com.nsoz2.mob.MobPosition;
import com.nsoz2.mob.MobTemplate;
import com.nsoz2.model.Char;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Admin
 */
public class BeastArea extends Z7Beasts {

    @Getter
    private int previousPlayerDied;

    public BeastArea(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
        previousPlayerDied = -1;
    }

    @Override
    public void join(@NotNull Char p) {
        super.join(p);
        previousPlayerDied = p.id;
    }

    @Override
    public void refresh() {
        this.level++;
        this.previousPlayerDied = -1;
        if (this.level < 6) {
            createMonster();
        } else {
            Mob mob = new Mob(monsters.size(), (short) MobName.MUC_ONG_DO, 20000000, (short) 68, (short) 565, (short) 384, false, false, this);
            addMob(mob);
        }
    }

    @Override
    public void update() {
        super.update();
        if (level < 6) {
            if (getLivingMonsters().isEmpty()) {
                List<Char> chars = getChars();
                Zone z = world.find(MapName.KHU_VUC_CHO);
                for (Char c : chars) {
                    if (c.isNhanBan) {
                        continue;
                    }
                    c.outZone();
                    c.setXY((short) 35, (short) 360);
                    z.join(c);
                }
                refresh();
            }
        }
    }

    @Override
    public void createMonster() {
        monsters.clear();
        int id = 0;
        for (MobPosition mob : tilemap.monsterCoordinates) {
            short templateID = (short) (mob.getId() + level);
            MobTemplate template = MobManager.getInstance().find(templateID);
            short x = mob.getX();
            short y = mob.getY();
            if (template.type == 4) {
                y -= 24;
            }
            Mob monster = new Mob(id++, templateID, 500000, (short) 68, x, y, false, false, this);
            addMob(monster);
        }
    }

}
