package com.nsoz.v2.infrastructure.database;

import com.nsoz.v2.infrastructure.config.ServerConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * HikariCP Connection Pool Provider for Server V2 (nso_v2 database).
 * Isolates database connections from legacy nso_test.
 */
public class HikariDataSourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(HikariDataSourceProvider.class);
    private static final HikariDataSourceProvider INSTANCE = new HikariDataSourceProvider();

    private HikariDataSource dataSource;

    private HikariDataSourceProvider() {}

    public static HikariDataSourceProvider getInstance() {
        return INSTANCE;
    }

    public synchronized void initialize() {
        if (dataSource != null && !dataSource.isClosed()) {
            logger.warn("HikariCP DataSource is already initialized.");
            return;
        }

        ServerConfig config = ServerConfig.getInstance();
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(config.getDbDriver());
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getDbUser());
        hikariConfig.setPassword(config.getDbPassword());

        hikariConfig.setPoolName(config.getDbPoolName());
        hikariConfig.setMinimumIdle(config.getDbPoolMin());
        hikariConfig.setMaximumPoolSize(config.getDbPoolMax());
        hikariConfig.setMaxLifetime(config.getDbLifetime());
        hikariConfig.setIdleTimeout(config.getDbIdleTimeout());
        hikariConfig.setConnectionTimeout(config.getDbConnectionTimeout());

        // Performance & UTF-8 options
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        hikariConfig.setConnectionInitSql("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;");

        this.dataSource = new HikariDataSource(hikariConfig);
        logger.info("Initialized HikariCP DataSource: [{}] connecting to [{}]", config.getDbPoolName(), config.getJdbcUrl());
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("HikariCP DataSource is not initialized or closed.");
        }
        return dataSource.getConnection();
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(5);
        } catch (Exception e) {
            logger.error("Database health check failed for nso_v2: {}", e.getMessage());
            return false;
        }
    }

    public synchronized void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            logger.info("Shutting down HikariCP DataSource [{}]...", dataSource.getPoolName());
            dataSource.close();
            dataSource = null;
        }
    }
}

