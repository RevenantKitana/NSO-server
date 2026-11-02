package com.nsoz.v2.domain.model;

import com.nsoz.v2.domain.model.component.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * PlayerCharacter: Aggregate Root connecting the 5 Cohesive Sets.
 * Clean, lightweight, high cohesion, zero spaghetti dependencies.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCharacter {
    private int id;
    private int userId;
    private String name;
    private byte gender;
    private short head;

    // 5 Cohesive Role Components
    private SpatialComponent spatial;
    private CharacterStateComponent state;
    private InventoryComponent inventory;
    private CombatComponent combat;
    private SocialComponent social;

    private Timestamp createdAt;
    private Timestamp lastLoginAt;

    public static PlayerCharacter createDefault(int id, int userId, String name, byte gender, short head) {
        return PlayerCharacter.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .gender(gender)
                .head(head)
                .spatial(SpatialComponent.builder()
                        .mapId(22) // Làng Tone
                        .zoneId(0)
                        .x((short) 1741)
                        .y((short) 264)
                        .build())
                .state(CharacterStateComponent.builder()
                        .level(1)
                        .exp(0)
                        .hp(100)
                        .maxHp(100)
                        .mp(100)
                        .maxMp(100)
                        .potentialPoint(0)
                        .skillPoint(0)
                        .build())
                .inventory(InventoryComponent.builder()
                        .yen(10000)
                        .xu(1000)
                        .bagCapacity(30)
                        .boxCapacity(30)
                        .build())
                .combat(CombatComponent.builder()
                        .clazz((byte) 0)
                        .pk((byte) 0)
                        .speed(10)
                        .build())
                .social(SocialComponent.builder()
                        .clanId(-1)
                        .partyId(-1)
                        .build())
                .build();
    }
}

