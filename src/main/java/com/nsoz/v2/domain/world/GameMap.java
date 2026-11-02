package com.nsoz.v2.domain.world;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Map Entity holding multiple runtime Zones.
 */
public class GameMap {
    @Getter
    private final MapTemplate template;
    private final List<Zone> zones;

    public GameMap(MapTemplate template) {
        this.template = template;
        int zoneCount = Math.max(1, template.getZoneNumber());
        this.zones = new ArrayList<>(zoneCount);

        for (int i = 0; i < zoneCount; i++) {
            this.zones.add(new Zone(i, this));
        }
    }

    public int getId() {
        return template.getId();
    }

    public String getName() {
        return template.getName();
    }

    public Zone getZone(int zoneId) {
        if (zoneId < 0 || zoneId >= zones.size()) {
            return zones.get(0);
        }
        return zones.get(zoneId);
    }

    public List<Zone> getZones() {
        return Collections.unmodifiableList(zones);
    }
}

