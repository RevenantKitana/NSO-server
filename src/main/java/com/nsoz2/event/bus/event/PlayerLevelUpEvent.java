package com.nsoz2.event.bus.event;

import com.nsoz2.model.Char;
import lombok.Getter;

/**
 * Sự kiện sinh ra khi người chơi thăng cấp
 */
@Getter
public class PlayerLevelUpEvent {

    private final Char player;
    private final int newLevel;

    public PlayerLevelUpEvent(Char player, int newLevel) {
        this.player = player;
        this.newLevel = newLevel;
    }
}

