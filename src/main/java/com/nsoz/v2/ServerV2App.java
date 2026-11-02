package com.nsoz.v2;

import com.nsoz.v2.adapter.network.V2NettyServer;
import com.nsoz.v2.infrastructure.config.ServerConfig;
import com.nsoz.v2.infrastructure.database.HikariDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Bootstrap Entry Point for Server V2 (Modern Portfolio-Grade Architecture).
 * Dual-stack Sandbox: Runs on Port 14445 alongside Legacy Server (Port 14444).
 */
public class ServerV2App {
    private static final Logger logger = LoggerFactory.getLogger(ServerV2App.class);

    public static void main(String[] args) {
        printBanner();

        // 1. Load Server Configuration
        ServerConfig config = ServerConfig.getInstance();
        logger.info("Initializing Server V2 [{}] on port {}...", config.getServerName(), config.getServerPort());

        // 2. Initialize Database Connection Pool (HikariCP -> nso_v2)
        try {
            HikariDataSourceProvider.getInstance().initialize();
            if (!HikariDataSourceProvider.getInstance().testConnection()) {
                logger.error("Database connection test to [{}] FAILED! Check credentials and network.", config.getDbName());
            } else {
                logger.info("Database connection to [{}] verified successfully via HikariCP.", config.getDbName());
            }
        } catch (Exception e) {
            logger.error("Failed to initialize database connection pool", e);
        }

        // 3. Initialize Game World & Zone Hibernation Engine
        try {
            com.nsoz.v2.domain.world.WorldManager.getInstance().initialize();
        } catch (Exception e) {
            logger.error("Failed to initialize WorldManager", e);
        }

        // 4. Start Netty 4.1 Socket Server
        V2NettyServer nettyServer = new V2NettyServer(config.getServerPort());
        try {
            nettyServer.start();
        } catch (Exception e) {
            logger.error("Fatal error starting Netty Server on port {}", config.getServerPort(), e);
            System.exit(1);
        }

        // 5. Register Graceful Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("JVM shutdown signal received. Gracefully closing Server V2 resources...");
            nettyServer.stop();
            HikariDataSourceProvider.getInstance().shutdown();
            logger.info("Server V2 terminated cleanly.");
        }, "ServerV2-ShutdownHook"));

        logger.info("==================================================================");
        logger.info(" [SERVER V2 READY] Listening for connections on port {}", config.getServerPort());
        logger.info("==================================================================");
    }

    private static void printBanner() {
        System.out.println("  _   _ ____   ____    ____  _______  ______      _______ ");
        System.out.println(" | \\ | / ___| / __ \\  |  _ \\| ____\\ \\/ / ___|    / /___  |");
        System.out.println(" |  \\| \\___ \\| |  | | | |_) |  _|  \\  / |       / /   / / ");
        System.out.println(" | |\\  |___) | |__| | |  _ <| |___ /  \\ |___   / /   / /  ");
        System.out.println(" |_| \\_|____/ \\____/  |_| \\_\\_____/_/\\_\\____| /_/   /_/   ");
        System.out.println(" =========================================================");
        System.out.println("  Ninja School Online V2 Modern Architecture Sandbox      ");
        System.out.println("  Port: 14445 | Database: nso_v2 | Engine: Netty 4.1 NIO  ");
        System.out.println(" =========================================================");
    }
}

