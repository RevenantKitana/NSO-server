package com.nsoz.v2.domain.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Static metadata template representing a map configuration from nso_v2.map.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapTemplate {
    private int id;
    private String name;
    private byte tileId;
    private byte bgId;
    private byte type;
    private int zoneNumber;

    @Builder.Default
    private List<Waypoint> waypoints = new ArrayList<>();
}

