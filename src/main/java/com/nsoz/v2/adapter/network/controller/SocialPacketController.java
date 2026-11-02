package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 5. SocialPacketController: High-Cohesion controller for Chat, Friends, Party,
 * and Clan interactions.
 */
public class SocialPacketController {
    private static final Logger logger = LoggerFactory.getLogger(SocialPacketController.class);
    private static final SocialPacketController INSTANCE = new SocialPacketController();

    private SocialPacketController() {}

    public static SocialPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame()) {
            return;
        }
        logger.debug("Received social packet [{}] for character [{}]", msg.getCommand(), session.getActiveCharacter().getName());
    }
}

