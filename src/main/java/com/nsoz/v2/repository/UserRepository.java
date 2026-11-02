package com.nsoz.v2.repository;

import com.nsoz.v2.domain.model.UserAccount;
import com.nsoz.v2.infrastructure.database.HikariDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Clean Repository for UserAccount entities in nso_v2.users.
 */
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static final UserRepository INSTANCE = new UserRepository();

    private UserRepository() {}

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public Optional<UserAccount> findByUsername(String username) {
        String sql = "SELECT id, username, password, role, status, luong, created_at, updated_at " +
                "FROM users WHERE username = ? LIMIT 1";

        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(UserAccount.builder()
                            .id(rs.getInt("id"))
                            .username(rs.getString("username"))
                            .password(rs.getString("password"))
                            .role(rs.getInt("role"))
                            .status(rs.getInt("status"))
                            .luong(rs.getInt("luong"))
                            .createdAt(rs.getTimestamp("created_at"))
                            .updatedAt(rs.getTimestamp("updated_at"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding user by username: {}", username, e);
        }
        return Optional.empty();
    }

    public boolean updateStatus(int userId, int status) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = HikariDataSourceProvider.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating user status for userId: {}", userId, e);
            return false;
        }
    }
}

