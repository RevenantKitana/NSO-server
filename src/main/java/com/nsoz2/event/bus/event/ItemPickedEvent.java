package com.nsoz2.event.bus.event;

import com.nsoz2.model.Char;
import com.nsoz2.item.Item;
import lombok.Getter;

/**
 * Sự kiện sinh ra khi người chơi nhặt một vật phẩm trên mặt đất
 */
@Getter
public class ItemPickedEvent {

    private final Char player;
    private final Item item;

    public ItemPickedEvent(Char player, Item item) {
        this.player = player;
        this.item = item;
    }
}

