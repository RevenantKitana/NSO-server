package com.nsoz.v2.domain.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spatial Waypoint boundary linking one map region to another.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {
    private short minX;
    private short minY;
    private short maxX;
    private short maxY;
    private int next;
    private short x;
    private short y;
}

