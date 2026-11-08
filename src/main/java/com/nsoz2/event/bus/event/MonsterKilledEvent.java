package com.nsoz2.event.bus.event;

import com.nsoz2.model.Char;
import com.nsoz2.mob.Mob;
import lombok.Getter;

/**
 * Sự kiện sinh ra khi người chơi tiêu diệt quái vật hoặc boss
 */
@Getter
public class MonsterKilledEvent {

    private final Char killer;
    private final Mob mob;

    public MonsterKilledEvent(Char killer, Mob mob) {
        this.killer = killer;
        this.mob = mob;
    }
}

