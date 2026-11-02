package com.nsoz.v2.domain.world;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.model.PlayerCharacter;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Isolated runtime map zone instance featuring Zone Hibernation.
 * Automatically suspends compute cycles when player count drops to zero.
 */
public class Zone {
    private static final Logger logger = LoggerFactory.getLogger(Zone.class);

    public static final byte CMD_PLAYER_MOVE = 1;
    public static final byte CMD_PLAYER_REMOVE = 2;
    public static final byte CMD_PLAYER_ADD = 3;
    public static final byte CMD_MAP_INFO = -18;

    @Getter
    private final int id;
    @Getter
    private final GameMap map;
    private final Map<Long, V2Session> players = new ConcurrentHashMap<>();

    @Getter
    private volatile boolean hibernated = true;

    public Zone(int id, GameMap map) {
        this.id = id;
        this.map = map;
        this.hibernated = true; // Starts in sleep mode
    }

    public synchronized void join(V2Session session) {
        PlayerCharacter character = session.getActiveCharacter();
        if (character == null) {
            return;
        }

        // Zone Hibernation Wake-up
        if (hibernated) {
            hibernated = false;
            logger.info("Zone [{}-{}] WOKE UP from hibernation (Player [{}] entered)", map.getId(), id, character.getName());
        }

        character.getSpatial().setMapId(map.getId());
        character.getSpatial().setZoneId(id);

        // 1. Send Map & Tile info to the entering player
        sendMapInfo(session);

        // 2. Broadcast this player's presence to existing players in this zone
        V2Message addMsg = buildPlayerAddMessage(character);
        broadcast(addMsg, session.getSessionId());

        // 3. Send all existing players in this zone to the entering player
        for (V2Session existing : players.values()) {
            if (existing.getActiveCharacter() != null) {
                session.send(buildPlayerAddMessage(existing.getActiveCharacter()));
            }
        }

        // 4. Register session in this zone
        players.put(session.getSessionId(), session);
        logger.info("Player [{}] successfully joined Zone [{}-{}] (Total active players: {})",
                character.getName(), map.getId(), id, players.size());
    }

    public synchronized void leave(V2Session session) {
        players.remove(session.getSessionId());
        PlayerCharacter character = session.getActiveCharacter();
        if (character != null) {
            // Broadcast removal
            V2Message removeMsg = buildPlayerRemoveMessage(character.getId());
            broadcast(removeMsg, session.getSessionId());
            logger.info("Player [{}] left Zone [{}-{}] (Remaining players: {})",
                    character.getName(), map.getId(), id, players.size());
        }

        // Automatic Zone Hibernation when empty
        if (players.isEmpty()) {
            hibernated = true;
            logger.info("Zone [{}-{}] ENTERED HIBERNATION (Zero players remaining)", map.getId(), id);
        }
    }

    public void move(V2Session session, short newX, short newY) {
        PlayerCharacter character = session.getActiveCharacter();
        if (character == null) {
            return;
        }

        character.getSpatial().moveTo(newX, newY);

        // Broadcast movement update to other players in this zone
        V2Message moveMsg = buildPlayerMoveMessage(character.getId(), newX, newY);
        broadcast(moveMsg, session.getSessionId());
    }

    public void broadcast(V2Message msg, long excludeSessionId) {
        for (V2Session session : players.values()) {
            if (session.getSessionId() != excludeSessionId && session.getChannel() != null && session.getChannel().isActive()) {
                session.send(msg);
            }
        }
    }

    public Collection<V2Session> getPlayers() {
        return players.values();
    }

    public int getPlayerCount() {
        return players.size();
    }

    private void sendMapInfo(V2Session session) {
        PlayerCharacter c = session.getActiveCharacter();
        MapTemplate tpl = map.getTemplate();

        try {
            V2Message ms = new V2Message(CMD_MAP_INFO);
            DataOutputStream ds = ms.writer();
            ds.writeByte(tpl.getId());
            ds.writeByte(tpl.getTileId());
            ds.writeByte(tpl.getBgId());
            ds.writeByte(tpl.getType());
            ds.writeUTF(tpl.getName());
            ds.writeByte(id); // zoneId
            ds.writeShort(c.getSpatial().getX());
            ds.writeShort(c.getSpatial().getY());

            // Waypoints
            ds.writeByte(tpl.getWaypoints().size());
            for (Waypoint w : tpl.getWaypoints()) {
                ds.writeShort(w.getMinX());
                ds.writeShort(w.getMinY());
                ds.writeShort(w.getMaxX());
                ds.writeShort(w.getMaxY());
            }

            // Monsters count (0 for initial pure slice)
            ds.writeByte(0);

            // Bu Nhin count
            ds.writeByte(0);

            // NPCs count
            ds.writeByte(0);

            // Items on ground count
            ds.writeByte(0);

            // TileMap Name & location stands
            ds.writeUTF(tpl.getName());
            ds.writeByte(0); // location stands

            ds.flush();
            session.send(ms);
        } catch (IOException e) {
            logger.error("Error sending MAP_INFO to session [{}]", session.getSessionId(), e);
        }
    }

    public static V2Message buildPlayerAddMessage(PlayerCharacter c) {
        V2Message ms = new V2Message(CMD_PLAYER_ADD);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(c.getId());
            ds.writeUTF(""); // Clan name
            ds.writeBoolean(false); // isInvisible
            ds.writeByte(c.getCombat().getPk());
            ds.writeByte(c.getCombat().getClazz());
            ds.writeByte(c.getGender());
            ds.writeShort(c.getHead());
            ds.writeUTF(c.getName());
            ds.writeInt(c.getState().getHp());
            ds.writeInt(c.getState().getMaxHp());
            ds.writeByte(c.getState().getLevel());
            ds.writeShort(-1); // weapon
            ds.writeShort(-1); // body
            ds.writeShort(-1); // leg
            ds.writeByte(-1);  // mobMe
            ds.writeShort(c.getSpatial().getX());
            ds.writeShort(c.getSpatial().getY());
            ds.writeShort(0);  // eff5buffhp
            ds.writeShort(0);  // eff5buffmp
            ds.writeByte(0);   // effect count
            ds.writeBoolean(true);  // isHuman
            ds.writeBoolean(false); // isNhanBan
            ds.writeShort(c.getHead());
            ds.writeShort(-1); // weapon
            ds.writeShort(-1); // body
            ds.writeShort(-1); // leg

            // 10 fashion slots
            for (int i = 0; i < 10; i++) {
                ds.writeShort(-1);
            }
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_ADD for char [{}]", c.getName(), e);
        }
        return ms;
    }

    public static V2Message buildPlayerMoveMessage(int charId, short x, short y) {
        V2Message ms = new V2Message(CMD_PLAYER_MOVE);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(charId);
            ds.writeShort(x);
            ds.writeShort(y);
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_MOVE for charId [{}]", charId, e);
        }
        return ms;
    }

    public static V2Message buildPlayerRemoveMessage(int charId) {
        V2Message ms = new V2Message(CMD_PLAYER_REMOVE);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(charId);
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_REMOVE for charId [{}]", charId, e);
        }
        return ms;
    }
}

