package com.nsoz.v2.infrastructure.config;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Thread-safe Configuration Manager for Server V2.
 * Reads config/server_v2.properties with fallback defaults.
 */
@Getter
public class ServerConfig {
    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);
    private static final ServerConfig INSTANCE = new ServerConfig();

    private int serverId = 1;
    private String serverName = "NSO V2 Server";
    private String serverHost = "0.0.0.0";
    private int serverPort = 14445;
    private int bossThreads = 1;
    private int workerThreads = 4;
    private int maxPacketSize = 65536;

    private String dbDriver = "com.mysql.cj.jdbc.Driver";
    private String dbHost = "127.0.0.1";
    private int dbPort = 3306;
    private String dbName = "nso_v2";
    private String dbUser = "nso_user";
    private String dbPassword = "NsoGame2026!@#";
    private String dbPoolName = "NSO_V2_Pool";
    private int dbPoolMin = 5;
    private int dbPoolMax = 20;
    private long dbLifetime = 1200000L;
    private long dbIdleTimeout = 600000L;
    private long dbConnectionTimeout = 30000L;
    private boolean dbLog = false;

    private int versionData = 2;
    private int versionItem = 15;
    private int versionMap = 4;
    private int versionSkill = 15;
    private int versionImage = 1;

    private ServerConfig() {
        load();
    }

    public static ServerConfig getInstance() {
        return INSTANCE;
    }

    public void load() {
        File configFile = new File("config/server_v2.properties");
        if (!configFile.exists()) {
            configFile = new File("server_v2.properties");
        }

        if (configFile.exists()) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(configFile);
                 InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
                props.load(reader);

                serverId = Integer.parseInt(props.getProperty("server.id", String.valueOf(serverId)));
                serverName = props.getProperty("server.name", serverName);
                serverHost = props.getProperty("server.host", serverHost);
                serverPort = Integer.parseInt(props.getProperty("server.port", String.valueOf(serverPort)));
                bossThreads = Integer.parseInt(props.getProperty("server.boss.threads", String.valueOf(bossThreads)));
                workerThreads = Integer.parseInt(props.getProperty("server.worker.threads", String.valueOf(workerThreads)));
                maxPacketSize = Integer.parseInt(props.getProperty("server.packet.size.max", String.valueOf(maxPacketSize)));

                dbDriver = props.getProperty("db.driver", dbDriver);
                dbHost = props.getProperty("db.host", dbHost);
                dbPort = Integer.parseInt(props.getProperty("db.port", String.valueOf(dbPort)));
                dbName = props.getProperty("db.name", dbName);
                dbUser = props.getProperty("db.user", dbUser);
                dbPassword = props.getProperty("db.pass", dbPassword);
                dbPoolName = props.getProperty("db.pool.name", dbPoolName);
                dbPoolMin = Integer.parseInt(props.getProperty("db.pool.min", String.valueOf(dbPoolMin)));
                dbPoolMax = Integer.parseInt(props.getProperty("db.pool.max", String.valueOf(dbPoolMax)));
                dbLifetime = Long.parseLong(props.getProperty("db.pool.lifetime", String.valueOf(dbLifetime)));
                dbIdleTimeout = Long.parseLong(props.getProperty("db.pool.idleTimeout", String.valueOf(dbIdleTimeout)));
                dbConnectionTimeout = Long.parseLong(props.getProperty("db.pool.connectionTimeout", String.valueOf(dbConnectionTimeout)));
                dbLog = Boolean.parseBoolean(props.getProperty("db.log", String.valueOf(dbLog)));

                versionData = Integer.parseInt(props.getProperty("game.version.data", String.valueOf(versionData)));
                versionItem = Integer.parseInt(props.getProperty("game.version.item", String.valueOf(versionItem)));
                versionMap = Integer.parseInt(props.getProperty("game.version.map", String.valueOf(versionMap)));
                versionSkill = Integer.parseInt(props.getProperty("game.version.skill", String.valueOf(versionSkill)));
                versionImage = Integer.parseInt(props.getProperty("game.version.image", String.valueOf(versionImage)));

                logger.info("Successfully loaded Server V2 configuration from: {}", configFile.getAbsolutePath());
            } catch (Exception e) {
                logger.error("Failed to load server_v2.properties, using built-in defaults", e);
            }
        } else {
            logger.warn("Configuration file config/server_v2.properties not found! Using defaults.");
        }
    }

    public String getJdbcUrl() {
        return String.format("jdbc:mysql://%s:%d/%s?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh&useSSL=false&allowPublicKeyRetrieval=true",
                dbHost, dbPort, dbName);
    }
}

