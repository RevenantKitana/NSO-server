package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.world.WorldManager;
import com.nsoz.v2.domain.world.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 4. CombatPacketController: High-Cohesion controller for Mob/Player Attacks, Skills,
 * and Status Effects.
 */
public class CombatPacketController {
    private static final Logger logger = LoggerFactory.getLogger(CombatPacketController.class);
    private static final CombatPacketController INSTANCE = new CombatPacketController();

    public static final byte CMD_PLAYER_ATTACK_NPC = 60;
    public static final byte CMD_PLAYER_ATTACK_PLAYER = 61;

    private CombatPacketController() {}

    public static CombatPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame() || session.getActiveCharacter() == null) {
            return;
        }

        byte cmd = msg.getCommand();
        if (cmd == CMD_PLAYER_ATTACK_NPC) {
            handleAttackNpc(session, msg.reader());
        }
    }

    private void handleAttackNpc(V2Session session, DataInputStream reader) {
        if (session.getActiveCharacter() == null) {
            return;
        }

        int mapId = session.getActiveCharacter().getSpatial().getMapId();
        int zoneId = session.getActiveCharacter().getSpatial().getZoneId();
        Zone zone = WorldManager.getInstance().findZone(mapId, zoneId);
        if (zone == null) {
            return;
        }

        try {
            while (reader.available() > 0) {
                int mobId = reader.readUnsignedByte();
                zone.handlePlayerAttackMob(session, mobId, (byte) 0);
            }
        } catch (IOException e) {
            logger.error("Error reading attack NPC packet from session [{}]", session.getSessionId(), e);
        }
    }
}
