package com.nsoz.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nsoz.db.jdbc.DbManager;
import com.nsoz.util.Log;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Built-in Lightweight HTTP API Bridge
 * Replaces external Node.js process (nso_bridge.js) to save ~48MB RAM.
 */
public class HttpBridge {

    private static final Gson GSON = new Gson();
    private static final Set<String> VALID_TOKENS = new HashSet<>(Arrays.asList(
            "NsoBridgeSecret2026Pass",
            "NsoBridgeSecret2026!@#",
            "NsoBridgeSecret2026!@",
            "NsoBridgeSecret2026"
    ));

    private static HttpServer server;

    public static void start(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
            server.createContext("/api/status", new StatusHandler());
            server.createContext("/api/giftcodes", new GiftcodesHandler());
            server.createContext("/api/register", new RegisterHandler());
            server.createContext("/download/NSO.jar", new DownloadClientHandler());
            server.createContext("/api/download/client", new DownloadClientHandler());
            server.createContext("/NSO.jar", new DownloadClientHandler());
            // Lightweight 2-thread pool for HTTP API
            server.setExecutor(Executors.newFixedThreadPool(2));
            server.start();
            Log.info("[EMBEDDED HTTP BRIDGE] API Bridge listening on 0.0.0.0:" + port + " (Zero Node.js dependency)");
        } catch (Exception e) {
            Log.error("[EMBEDDED HTTP BRIDGE] Failed to start HTTP Bridge on port " + port + ": " + e.getMessage());
        }
    }

    public static void stop() {
        if (server != null) {
            server.stop(1);
            server = null;
        }
    }

    private static boolean authenticate(HttpExchange exchange) {
        String token = exchange.getRequestHeaders().getFirst("x-bridge-token");
        if (token != null) {
            token = token.trim().replaceAll("^[\"']|[\"']$", "");
        }
        return token != null && VALID_TOKENS.contains(token);
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, JsonObject json) throws IOException {
        byte[] bytes = GSON.toJson(json).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, x-bridge-token");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static boolean handleCorsPreflight(HttpExchange exchange) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, x-bridge-token");
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return true;
        }
        return false;
    }

    private static String readRequestBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            return bos.toString(StandardCharsets.UTF_8);
        }
    }

    // 1. GET /api/status
    private static class StatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (handleCorsPreflight(exchange)) return;
            if (!authenticate(exchange)) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Unauthorized: Invalid Bridge Secret Token");
                sendJsonResponse(exchange, 401, err);
                return;
            }

            int totalUsers = 0;
            int onlineUsers = 0;
            try (Connection conn = DbManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) as total, SUM(CASE WHEN online = 1 THEN 1 ELSE 0 END) as online FROM users;");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalUsers = rs.getInt("total");
                    onlineUsers = rs.getInt("online");
                }
            } catch (Exception e) {
                Log.error("Error in /api/status: " + e.getMessage());
            }

            JsonObject resp = new JsonObject();
            resp.addProperty("success", true);

            JsonObject serverInfo = new JsonObject();
            serverInfo.addProperty("serverName", "NSO Custom (Mod by Khánh)");
            serverInfo.addProperty("status", Server.start ? "ONLINE" : "OFFLINE");
            serverInfo.addProperty("isOnline", Server.start);
            serverInfo.addProperty("totalUsers", totalUsers);
            serverInfo.addProperty("onlineUsers", onlineUsers);
            serverInfo.addProperty("lastChecked", Instant.now().toString());

            resp.add("server", serverInfo);
            sendJsonResponse(exchange, 200, resp);
        }
    }

    // 2. GET /api/giftcodes
    private static class GiftcodesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (handleCorsPreflight(exchange)) return;
            if (!authenticate(exchange)) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Unauthorized: Invalid Bridge Secret Token");
                sendJsonResponse(exchange, 401, err);
                return;
            }

            JsonArray list = new JsonArray();
            String sql = "SELECT id, code, type, gold, coin, yen, items, expires_at, created_at FROM gift_codes WHERE status = 0 AND (expires_at IS NULL OR expires_at > NOW()) ORDER BY id DESC LIMIT 50;";
            try (Connection conn = DbManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JsonObject item = new JsonObject();
                    item.addProperty("id", rs.getInt("id"));
                    item.addProperty("code", rs.getString("code"));
                    item.addProperty("type", rs.getInt("type") == 1 ? "Mỗi nhân vật 1 lần" : "Dùng chung");
                    item.addProperty("gold", rs.getInt("gold"));
                    item.addProperty("coin", rs.getInt("coin"));
                    item.addProperty("yen", rs.getInt("yen"));

                    String itemsStr = rs.getString("items");
                    JsonArray parsedItems = new JsonArray();
                    if (itemsStr != null && !itemsStr.trim().isEmpty()) {
                        try {
                            JsonElement el = JsonParser.parseString(itemsStr);
                            if (el.isJsonArray()) {
                                parsedItems = el.getAsJsonArray();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    item.addProperty("itemsCount", parsedItems.size());
                    item.add("items", parsedItems);

                    Timestamp exp = rs.getTimestamp("expires_at");
                    item.addProperty("expiresAt", exp != null ? exp.toInstant().toString() : null);
                    list.add(item);
                }
            } catch (Exception e) {
                Log.error("Error in /api/giftcodes: " + e.getMessage());
            }

            JsonObject resp = new JsonObject();
            resp.addProperty("success", true);
            resp.add("giftcodes", list);
            sendJsonResponse(exchange, 200, resp);
        }
    }

    // 3. POST /api/register
    private static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (handleCorsPreflight(exchange)) return;
            if (!authenticate(exchange)) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Unauthorized: Invalid Bridge Secret Token");
                sendJsonResponse(exchange, 401, err);
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Method Not Allowed");
                sendJsonResponse(exchange, 405, err);
                return;
            }

            String body = readRequestBody(exchange);
            JsonObject reqJson;
            try {
                reqJson = JsonParser.parseString(body).getAsJsonObject();
            } catch (Exception e) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Malformed JSON body");
                sendJsonResponse(exchange, 400, err);
                return;
            }

            String username = reqJson.has("username") ? reqJson.get("username").getAsString().trim().toLowerCase() : "";
            String passwordHash = reqJson.has("passwordHash") ? reqJson.get("passwordHash").getAsString().trim() : "";
            String otp = reqJson.has("otp") ? reqJson.get("otp").getAsString().trim() : "";

            if (username.isEmpty() || passwordHash.isEmpty() || otp.isEmpty()) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Thiếu thông tin đăng ký bắt buộc!");
                sendJsonResponse(exchange, 400, err);
                return;
            }

            try (Connection conn = DbManager.getConnection()) {
                // Validate OTP
                try (PreparedStatement psOtp = conn.prepareStatement("SELECT id, code, expires_at, used FROM registration_otps WHERE code = ? LIMIT 1;")) {
                    psOtp.setString(1, otp);
                    try (ResultSet rsOtp = psOtp.executeQuery()) {
                        if (!rsOtp.next()) {
                            JsonObject err = new JsonObject();
                            err.addProperty("success", false);
                            err.addProperty("error", "Mã OTP không hợp lệ! Vui lòng liên hệ [Khánh] để nhận mã cấp phép.");
                            sendJsonResponse(exchange, 400, err);
                            return;
                        }
                        if (rsOtp.getInt("used") == 1) {
                            JsonObject err = new JsonObject();
                            err.addProperty("success", false);
                            err.addProperty("error", "Mã OTP này đã được sử dụng trước đó! Mỗi mã chỉ dùng được 1 lần.");
                            sendJsonResponse(exchange, 400, err);
                            return;
                        }
                        Timestamp expiresAt = rsOtp.getTimestamp("expires_at");
                        if (expiresAt != null && expiresAt.toInstant().isBefore(Instant.now())) {
                            JsonObject err = new JsonObject();
                            err.addProperty("success", false);
                            err.addProperty("error", "Mã OTP này đã hết hạn! Vui lòng liên hệ [Khánh] để nhận mã mới.");
                            sendJsonResponse(exchange, 400, err);
                            return;
                        }
                    }
                }

                // Check username exists
                try (PreparedStatement psUser = conn.prepareStatement("SELECT id FROM users WHERE username = ? LIMIT 1;")) {
                    psUser.setString(1, username);
                    try (ResultSet rsUser = psUser.executeQuery()) {
                        if (rsUser.next()) {
                            JsonObject err = new JsonObject();
                            err.addProperty("success", false);
                            err.addProperty("error", "Tên tài khoản này đã được sử dụng! Vui lòng chọn tên khác.");
                            sendJsonResponse(exchange, 400, err);
                            return;
                        }
                    }
                }

                // Insert user
                String insertSql = "INSERT INTO users (username, password, activated, balance, luong, tongnap, point_vip, role, status, online, nap, tanthu, level, created_at, updated_at) " +
                        "VALUES (?, ?, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 'member', NOW(), NOW());";
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setString(1, username);
                    psInsert.setString(2, passwordHash);
                    psInsert.executeUpdate();
                }

                // Delete used OTP
                try (PreparedStatement psDel = conn.prepareStatement("DELETE FROM registration_otps WHERE code = ?;")) {
                    psDel.setString(1, otp);
                    psDel.executeUpdate();
                }

                // Cleanup expired OTPs asynchronously
                try (PreparedStatement psClean = conn.prepareStatement("DELETE FROM registration_otps WHERE expires_at < NOW();")) {
                    psClean.executeUpdate();
                } catch (Exception ignored) {
                }

                JsonObject ok = new JsonObject();
                ok.addProperty("success", true);
                ok.addProperty("message", "Đăng ký tài khoản thành công! Bạn có thể mở Client game và đăng nhập ngay bây giờ.");
                ok.addProperty("username", username);
                sendJsonResponse(exchange, 200, ok);
            } catch (Exception e) {
                Log.error("Error in /api/register: " + e.getMessage());
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", e.getMessage() != null ? e.getMessage() : "Internal Bridge Error");
                sendJsonResponse(exchange, 500, err);
            }
        }
    }

    // 4. GET /download/NSO.jar or /api/download/client or /NSO.jar
    private static class DownloadClientHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (handleCorsPreflight(exchange)) return;

            String method = exchange.getRequestMethod();
            if (!"GET".equalsIgnoreCase(method) && !"HEAD".equalsIgnoreCase(method)) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "Method Not Allowed");
                sendJsonResponse(exchange, 405, err);
                return;
            }

            File file = new File("NSO.jar");
            if (!file.exists() || !file.isFile()) {
                file = new File(".client/NSO.jar");
            }
            if (!file.exists() || !file.isFile()) {
                file = new File("client/NSO.jar");
            }

            if (!file.exists() || !file.isFile()) {
                JsonObject err = new JsonObject();
                err.addProperty("success", false);
                err.addProperty("error", "File NSO.jar not found on server");
                sendJsonResponse(exchange, 404, err);
                return;
            }

            long fileLength = file.length();
            exchange.getResponseHeaders().set("Content-Type", "application/java-archive");
            exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=\"NSO.jar\"");
            exchange.getResponseHeaders().set("Content-Length", String.valueOf(fileLength));
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS");
            exchange.getResponseHeaders().set("Cache-Control", "public, max-age=60");

            if ("HEAD".equalsIgnoreCase(method)) {
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
                return;
            }

            exchange.sendResponseHeaders(200, fileLength);
            try (InputStream is = new FileInputStream(file);
                 OutputStream os = exchange.getResponseBody()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            }
        }
    }
}
