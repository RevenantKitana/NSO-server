package com.nsoz.v2.domain.world;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.infrastructure.database.HikariDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global World Manager: orchestrates all GameMaps, Zones, and spatial player placement.
 */
public class WorldManager {
    private static final Logger logger = LoggerFactory.getLogger(WorldManager.class);
    private static final WorldManager INSTANCE = new WorldManager();

    private final Map<Integer, GameMap> maps = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    private WorldManager() {}

    public static WorldManager getInstance() {
        return INSTANCE;
    }

    public synchronized void initialize() {
        maps.clear();
        String sql = "SELECT id, name, tileId, bgId, type, zone_number, waypoint, npc FROM map ORDER BY id ASC";

        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int count = 0;
            Type waypointListType = new TypeToken<ArrayList<Waypoint>>() {}.getType();
            Type npcListType = new TypeToken<ArrayList<NpcPosition>>() {}.getType();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte tileId = rs.getByte("tileId");
                byte bgId = rs.getByte("bgId");
                byte type = rs.getByte("type");
                int zoneNumber = rs.getInt("zone_number");
                String waypointJson = rs.getString("waypoint");
                String npcJson = rs.getString("npc");

                List<Waypoint> waypoints = new ArrayList<>();
                if (waypointJson != null && !waypointJson.trim().isEmpty()) {
                    try {
                        waypoints = gson.fromJson(waypointJson, waypointListType);
                    } catch (Exception e) {
                        logger.warn("Could not parse waypoints JSON for map [{}]", id);
                    }
                }

                List<NpcPosition> npcs = new ArrayList<>();
                if (npcJson != null && !npcJson.trim().isEmpty()) {
                    try {
                        npcs = gson.fromJson(npcJson, npcListType);
                    } catch (Exception e) {
                        logger.warn("Could not parse npc JSON for map [{}]", id);
                    }
                }

                MapTemplate template = MapTemplate.builder()
                        .id(id)
                        .name(name)
                        .tileId(tileId)
                        .bgId(bgId)
                        .type(type)
                        .zoneNumber(zoneNumber)
                        .waypoints(waypoints != null ? waypoints : new ArrayList<>())
                        .npcs(npcs != null ? npcs : new ArrayList<>())
                        .build();

                GameMap gameMap = new GameMap(template);
                maps.put(id, gameMap);
                count++;
            }
            logger.info("Successfully loaded {} Maps into WorldManager (Total initial zones: hibernated)", count);
        } catch (SQLException e) {
            logger.error("Failed to load maps from database", e);
        }
    }

    public GameMap findMap(int mapId) {
        return maps.get(mapId);
    }

    public Zone findZone(int mapId, int zoneId) {
        GameMap map = findMap(mapId);
        if (map != null) {
            return map.getZone(zoneId);
        }
        return null;
    }

    public void joinZone(V2Session session, int mapId, int zoneId) {
        GameMap map = findMap(mapId);
        if (map == null) {
            logger.warn("Cannot find map [{}] for session [{}]", mapId, session.getSessionId());
            return;
        }

        Zone targetZone = map.getZone(zoneId);
        targetZone.join(session);
    }

    public void leaveCurrentZone(V2Session session) {
        PlayerCharacter c = session.getActiveCharacter();
        if (c != null) {
            Zone zone = findZone(c.getSpatial().getMapId(), c.getSpatial().getZoneId());
            if (zone != null) {
                zone.leave(session);
            }
        }
    }

    public void move(V2Session session, short x, short y) {
        PlayerCharacter c = session.getActiveCharacter();
        if (c != null) {
            Zone zone = findZone(c.getSpatial().getMapId(), c.getSpatial().getZoneId());
            if (zone != null) {
                zone.move(session, x, y);
            }
        }
    }
}

