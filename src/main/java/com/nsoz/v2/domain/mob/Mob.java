package com.nsoz.v2.domain.mob;

import com.nsoz.v2.domain.combat.effect.TriStateStatusManager;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain entity representing a Monster instance in a Zone.
 */
@Getter
@Setter
public class Mob {
    private final int id;
    private final int templateId;
    private final String name;
    private final int level;
    private final int maxHp;
    private volatile int hp;

    private short x;
    private short y;
    private final short spawnX;
    private final short spawnY;

    private final TriStateStatusManager statusEffects = new TriStateStatusManager();
    private volatile long respawnTime = 0;

    public Mob(int id, int templateId, String name, int level, int maxHp, short spawnX, short spawnY) {
        this.id = id;
        this.templateId = templateId;
        this.name = name;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.x = spawnX;
        this.y = spawnY;
    }

    public synchronized void takeDamage(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    public synchronized void respawn() {
        this.hp = this.maxHp;
        this.x = this.spawnX;
        this.y = this.spawnY;
        this.statusEffects.clearAll();
        this.respawnTime = 0;
    }
}

