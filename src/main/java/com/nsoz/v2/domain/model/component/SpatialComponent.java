package com.nsoz.v2.domain.model.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1. Spatial Component: Encapsulates map, zone, and spatial coordinates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpatialComponent {
    private int mapId;
    private int zoneId;
    private short x;
    private short y;

    public void moveTo(short newX, short newY) {
        this.x = newX;
        this.y = newY;
    }

    public void changeLocation(int newMapId, int newZoneId, short newX, short newY) {
        this.mapId = newMapId;
        this.zoneId = newZoneId;
        this.x = newX;
        this.y = newY;
    }
}

