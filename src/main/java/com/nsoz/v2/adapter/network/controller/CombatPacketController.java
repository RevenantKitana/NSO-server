package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 4. CombatPacketController: High-Cohesion controller for Mob/Player Attacks, Skills,
 * and Status Effects.
 */
public class CombatPacketController {
    private static final Logger logger = LoggerFactory.getLogger(CombatPacketController.class);
    private static final CombatPacketController INSTANCE = new CombatPacketController();

    private CombatPacketController() {}

    public static CombatPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame()) {
            return;
        }
        logger.debug("Received combat packet [{}] for character [{}]", msg.getCommand(), session.getActiveCharacter().getName());
    }
}

