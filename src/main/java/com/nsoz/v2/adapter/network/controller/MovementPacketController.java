package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.world.WorldManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 2. MovementPacketController: High-Cohesion controller for Map Navigation, Zone Switching,
 * and Coordinate Movement.
 */
public class MovementPacketController {
    private static final Logger logger = LoggerFactory.getLogger(MovementPacketController.class);
    private static final MovementPacketController INSTANCE = new MovementPacketController();

    public static final byte CMD_PLAYER_MOVE = 1;
    public static final byte CMD_MOVE = 1;

    private MovementPacketController() {}

    public static MovementPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame() || session.getActiveCharacter() == null) {
            return;
        }

        byte cmd = msg.getCommand();
        if (cmd == CMD_PLAYER_MOVE) {
            handleMove(session, msg.reader());
        }
    }

    private void handleMove(V2Session session, DataInputStream reader) {
        try {
            short x = reader.readShort();
            short y = reader.readShort();

            WorldManager.getInstance().move(session, x, y);
            logger.debug("Player [{}] moved to ({}, {}) in Zone [{}-{}]",
                    session.getActiveCharacter().getName(), x, y,
                    session.getActiveCharacter().getSpatial().getMapId(),
                    session.getActiveCharacter().getSpatial().getZoneId());
        } catch (IOException e) {
            logger.error("Error parsing move coordinates for session [{}]", session.getSessionId(), e);
        }
    }
}
