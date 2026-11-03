package com.nsoz.v2.domain.world;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NPC spatial position and template metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NpcPosition {
    private short x;
    private short y;
    private int templateId;
    private int status;
}

