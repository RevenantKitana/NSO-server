package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3. InventoryPacketController: High-Cohesion controller for Bags, Equips, Boxes,
 * and Item Enhancements.
 */
public class InventoryPacketController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPacketController.class);
    private static final InventoryPacketController INSTANCE = new InventoryPacketController();

    private InventoryPacketController() {}

    public static InventoryPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame()) {
            return;
        }
        logger.debug("Received inventory packet [{}] for character [{}]", msg.getCommand(), session.getActiveCharacter().getName());
    }
}

