package com.nsoz.v2.repository;

import com.nsoz.v2.domain.item.ItemInstance;
import com.nsoz.v2.infrastructure.database.HikariDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clean Repository for ItemInstance entities in nso_v2.character_items.
 */
public class ItemRepository {
    private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);
    private static final ItemRepository INSTANCE = new ItemRepository();

    private ItemRepository() {}

    public static ItemRepository getInstance() {
        return INSTANCE;
    }

    public List<ItemInstance> findByCharacterId(int characterId) {
        List<ItemInstance> items = new ArrayList<>();
        String sql = "SELECT id, character_id, template_id, upgrade_level, tier, location, slot_index, " +
                "quantity, sys, flags, yen_price, expires_at, extra_data " +
                "FROM character_items WHERE character_id = ? ORDER BY slot_index ASC";

        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, characterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToItem(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding items for characterId: {}", characterId, e);
        }
        return items;
    }

    public boolean updateLocation(long itemId, String location, short slotIndex) {
        String sql = "UPDATE character_items SET location = ?, slot_index = ? WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location);
            ps.setShort(2, slotIndex);
            ps.setLong(3, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating location for itemId: {}", itemId, e);
            return false;
        }
    }

    public boolean updateUpgrade(long itemId, byte upgradeLevel) {
        String sql = "UPDATE character_items SET upgrade_level = ? WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setByte(1, upgradeLevel);
            ps.setLong(2, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating upgrade level for itemId: {}", itemId, e);
            return false;
        }
    }

    public boolean updateQuantity(long itemId, int quantity) {
        String sql = "UPDATE character_items SET quantity = ? WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setLong(2, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating quantity for itemId: {}", itemId, e);
            return false;
        }
    }

    public boolean delete(long itemId) {
        String sql = "DELETE FROM character_items WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, itemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting itemId: {}", itemId, e);
            return false;
        }
    }

    public long insert(ItemInstance item) {
        String sql = "INSERT INTO character_items (character_id, template_id, upgrade_level, tier, " +
                "location, slot_index, quantity, sys, flags, yen_price, expires_at, extra_data) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, item.getCharacterId());
            ps.setInt(2, item.getTemplateId());
            ps.setByte(3, item.getUpgradeLevel());
            ps.setByte(4, item.getTier());
            ps.setString(5, item.getLocation());
            ps.setShort(6, item.getSlotIndex());
            ps.setInt(7, item.getQuantity());
            ps.setByte(8, item.getSys());
            ps.setInt(9, item.getFlags());
            ps.setInt(10, item.getYenPrice());
            ps.setLong(11, item.getExpiresAt());
            ps.setString(12, item.getExtraData());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long generatedId = rs.getLong(1);
                        item.setId(generatedId);
                        return generatedId;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting item for characterId: {}", item.getCharacterId(), e);
        }
        return -1;
    }

    private ItemInstance mapResultSetToItem(ResultSet rs) throws SQLException {
        return ItemInstance.builder()
                .id(rs.getLong("id"))
                .characterId(rs.getInt("character_id"))
                .templateId(rs.getInt("template_id"))
                .upgradeLevel(rs.getByte("upgrade_level"))
                .tier(rs.getByte("tier"))
                .location(rs.getString("location"))
                .slotIndex(rs.getShort("slot_index"))
                .quantity(rs.getInt("quantity"))
                .sys(rs.getByte("sys"))
                .flags(rs.getInt("flags"))
                .yenPrice(rs.getInt("yen_price"))
                .expiresAt(rs.getLong("expires_at"))
                .extraData(rs.getString("extra_data"))
                .build();
    }
}
