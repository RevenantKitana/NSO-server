package com.nsoz.v2.repository;

import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.domain.model.component.*;
import com.nsoz.v2.infrastructure.database.HikariDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clean Repository for PlayerCharacter aggregate entities in nso_v2.characters.
 */
public class CharacterRepository {
    private static final Logger logger = LoggerFactory.getLogger(CharacterRepository.class);
    private static final CharacterRepository INSTANCE = new CharacterRepository();

    private CharacterRepository() {}

    public static CharacterRepository getInstance() {
        return INSTANCE;
    }

    public List<PlayerCharacter> findByUserId(int userId) {
        List<PlayerCharacter> characters = new ArrayList<>();
        String sql = "SELECT id, user_id, name, gender, class_id, level, exp, potential_point, skill_point, " +
                "xu, yen, bag_slots, box_slots, map_id, map_x, map_y, head_id, pk_point, last_login, created_at " +
                "FROM characters WHERE user_id = ? ORDER BY id ASC LIMIT 3";

        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    characters.add(mapResultSetToCharacter(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding characters by userId: {}", userId, e);
        }
        return characters;
    }

    public Optional<PlayerCharacter> findById(int charId) {
        String sql = "SELECT id, user_id, name, gender, class_id, level, exp, potential_point, skill_point, " +
                "xu, yen, bag_slots, box_slots, map_id, map_x, map_y, head_id, pk_point, last_login, created_at " +
                "FROM characters WHERE id = ? LIMIT 1";

        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, charId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCharacter(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding character by charId: {}", charId, e);
        }
        return Optional.empty();
    }

    public boolean updateLocation(int charId, int mapId, short x, short y) {
        String sql = "UPDATE characters SET map_id = ?, map_x = ?, map_y = ? WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mapId);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setInt(4, charId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating location for charId: {}", charId, e);
            return false;
        }
    }

    private PlayerCharacter mapResultSetToCharacter(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String name = rs.getString("name");
        byte gender = rs.getByte("gender");
        short head = rs.getShort("head_id");
        byte classId = rs.getByte("class_id");
        int level = rs.getInt("level");
        long exp = rs.getLong("exp");
        int potPoint = rs.getInt("potential_point");
        int skillPoint = rs.getInt("skill_point");
        long xu = rs.getLong("xu");
        long yen = rs.getLong("yen");
        byte bagSlots = rs.getByte("bag_slots");
        byte boxSlots = rs.getByte("box_slots");
        int mapId = rs.getInt("map_id");
        short mapX = (short) rs.getInt("map_x");
        short mapY = (short) rs.getInt("map_y");
        byte pk = (byte) rs.getInt("pk_point");

        return PlayerCharacter.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .gender(gender)
                .head(head)
                .spatial(SpatialComponent.builder()
                        .mapId(mapId)
                        .zoneId(0)
                        .x(mapX)
                        .y(mapY)
                        .build())
                .state(CharacterStateComponent.builder()
                        .level(level)
                        .exp(exp)
                        .hp(100 + level * 50)
                        .maxHp(100 + level * 50)
                        .mp(100 + level * 20)
                        .maxMp(100 + level * 20)
                        .potentialPoint(potPoint)
                        .skillPoint(skillPoint)
                        .build())
                .inventory(InventoryComponent.builder()
                        .yen(yen)
                        .xu(xu)
                        .bagCapacity(bagSlots)
                        .boxCapacity(boxSlots)
                        .build())
                .combat(CombatComponent.builder()
                        .clazz(classId)
                        .pk(pk)
                        .speed(10)
                        .build())
                .social(SocialComponent.builder()
                        .clanId(-1)
                        .partyId(-1)
                        .build())
                .createdAt(rs.getTimestamp("created_at"))
                .lastLoginAt(rs.getTimestamp("last_login"))
                .build();
    }
}

