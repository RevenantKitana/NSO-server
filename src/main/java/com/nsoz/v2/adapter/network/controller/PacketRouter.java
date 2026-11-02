package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Top-level Packet Router: dispatches incoming opcodes to the 5 Cohesive Packet Controllers.
 * Prevents opcode sprawl and God-Class controller anti-patterns.
 */
public class PacketRouter {
    private static final Logger logger = LoggerFactory.getLogger(PacketRouter.class);
    private static final PacketRouter INSTANCE = new PacketRouter();

    private final AuthPacketController authController = AuthPacketController.getInstance();
    private final MovementPacketController movementController = MovementPacketController.getInstance();
    private final InventoryPacketController inventoryController = InventoryPacketController.getInstance();
    private final CombatPacketController combatController = CombatPacketController.getInstance();
    private final SocialPacketController socialController = SocialPacketController.getInstance();

    private PacketRouter() {}

    public static PacketRouter getInstance() {
        return INSTANCE;
    }

    public void route(V2Session session, V2Message msg) {
        byte cmd = msg.getCommand();

        switch (cmd) {
            case AuthPacketController.CMD_GET_SESSION_ID:
                // Handshake initial trigger
                authController.sendHandshake(session);
                break;

            case AuthPacketController.CMD_NOT_LOGIN:
                authController.handleNotLogin(session, msg);
                break;

            case AuthPacketController.CMD_NOT_MAP:
                authController.handleNotMap(session, msg);
                break;

            case MovementPacketController.CMD_PLAYER_MOVE:
                movementController.handlePacket(session, msg);
                break;

            default:
                logger.debug("Received unhandled opcode [{}] from session [{}]", cmd, session.getSessionId());
                break;
        }
    }
}

