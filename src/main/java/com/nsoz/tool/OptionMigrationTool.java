package com.nsoz.tool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

/**
 * Migration Tool chuẩn hóa toàn bộ mã Option cũ trong Database
 * theo ITEM_OPTION_STANDARDIZATION_SPEC.md.
 *
 * An toàn tuyệt đối:
 * - Chế độ Dry-Run để kiểm tra trước.
 * - Transaction toàn vẹn (commit hoặc tự động rollback nếu gặp bất kỳ lỗi nào).
 */
public class OptionMigrationTool {

    public static final Map<Long, Long> REPLACEMENT_MAP = new HashMap<>();

    static {
        // Né đòn -> 5
        REPLACEMENT_MAP.put(17L, 5L);
        REPLACEMENT_MAP.put(68L, 5L);
        REPLACEMENT_MAP.put(78L, 5L);
        REPLACEMENT_MAP.put(84L, 5L);
        REPLACEMENT_MAP.put(115L, 5L);

        // HP tối đa -> 6
        REPLACEMENT_MAP.put(77L, 6L);
        REPLACEMENT_MAP.put(82L, 6L);
        REPLACEMENT_MAP.put(125L, 6L);

        // MP tối đa -> 7
        REPLACEMENT_MAP.put(19L, 7L);
        REPLACEMENT_MAP.put(83L, 7L);
        REPLACEMENT_MAP.put(117L, 7L);

        // Chính xác -> 10
        REPLACEMENT_MAP.put(18L, 10L);
        REPLACEMENT_MAP.put(75L, 10L);
        REPLACEMENT_MAP.put(86L, 10L);
        REPLACEMENT_MAP.put(116L, 10L);

        // Chí mạng -> 14
        REPLACEMENT_MAP.put(69L, 14L);
        REPLACEMENT_MAP.put(92L, 14L);
        REPLACEMENT_MAP.put(114L, 14L);

        // Phản đòn cận chiến -> 15
        REPLACEMENT_MAP.put(91L, 15L);
        REPLACEMENT_MAP.put(126L, 15L);

        // Kháng tất cả -> 20
        REPLACEMENT_MAP.put(81L, 20L);
        REPLACEMENT_MAP.put(118L, 20L);

        // Kháng hỏa -> 2
        REPLACEMENT_MAP.put(11L, 2L);
        REPLACEMENT_MAP.put(70L, 2L);
        REPLACEMENT_MAP.put(96L, 2L);

        // Kháng băng -> 3
        REPLACEMENT_MAP.put(12L, 3L);
        REPLACEMENT_MAP.put(71L, 3L);
        REPLACEMENT_MAP.put(95L, 3L);

        // Kháng phong -> 4
        REPLACEMENT_MAP.put(13L, 4L);
        REPLACEMENT_MAP.put(72L, 4L);
        REPLACEMENT_MAP.put(97L, 4L);

        // Giảm trừ sát thương -> 47
        REPLACEMENT_MAP.put(74L, 47L);
        REPLACEMENT_MAP.put(80L, 47L);
        REPLACEMENT_MAP.put(124L, 47L);

        // Kháng STCM -> 121
        REPLACEMENT_MAP.put(79L, 121L);
    }

    private final JSONParser parser = new JSONParser();
    private int totalReplacedOptions = 0;
    private int totalUpdatedRows = 0;

    public static class MigrationResult {
        public String modifiedJson;
        public int replacedCount;

        public MigrationResult(String modifiedJson, int replacedCount) {
            this.modifiedJson = modifiedJson;
            this.replacedCount = replacedCount;
        }
    }

    /**
     * Chuẩn hóa chuỗi JSON mảng Item (equiped, bag, box, mount, bijuu...)
     * Format bên trong mỗi item: "options": [[id, param], [id, param], ...]
     */
    public MigrationResult migrateItemArrayJson(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty() || jsonStr.trim().equals("[]")) {
            return new MigrationResult(jsonStr, 0);
        }
        try {
            Object parsed = parser.parse(jsonStr);
            if (!(parsed instanceof JSONArray)) {
                return new MigrationResult(jsonStr, 0);
            }
            JSONArray items = (JSONArray) parsed;
            int count = 0;
            for (Object obj : items) {
                if (obj instanceof JSONObject) {
                    JSONObject itemObj = (JSONObject) obj;
                    Object optsObj = itemObj.get("options");
                    if (optsObj instanceof JSONArray) {
                        JSONArray optArray = (JSONArray) optsObj;
                        for (Object optElem : optArray) {
                            if (optElem instanceof JSONArray) {
                                JSONArray singleOpt = (JSONArray) optElem;
                                if (singleOpt.size() >= 2 && singleOpt.get(0) instanceof Number) {
                                    long optId = ((Number) singleOpt.get(0)).longValue();
                                    if (REPLACEMENT_MAP.containsKey(optId)) {
                                        long newId = REPLACEMENT_MAP.get(optId);
                                        singleOpt.set(0, newId);
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return new MigrationResult(items.toJSONString(), count);
        } catch (Exception e) {
            System.err.println("Lỗi khi parse JSON Item Array: " + e.getMessage());
            return new MigrationResult(jsonStr, 0);
        }
    }

    /**
     * Chuẩn hóa chuỗi JSON của store_data hoặc weapon_store.
     * Hỗ trợ format: [{"id": id, "param": param}] hoặc [[id, param], ...]
     */
    public MigrationResult migrateStoreOptionsJson(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().isEmpty() || jsonStr.trim().equals("[]")) {
            return new MigrationResult(jsonStr, 0);
        }
        try {
            Object parsed = parser.parse(jsonStr);
            if (!(parsed instanceof JSONArray)) {
                return new MigrationResult(jsonStr, 0);
            }
            JSONArray array = (JSONArray) parsed;
            int count = 0;
            for (Object elem : array) {
                if (elem instanceof JSONObject) {
                    JSONObject obj = (JSONObject) elem;
                    if (obj.containsKey("id") && obj.get("id") instanceof Number) {
                        long optId = ((Number) obj.get("id")).longValue();
                        if (REPLACEMENT_MAP.containsKey(optId)) {
                            obj.put("id", REPLACEMENT_MAP.get(optId));
                            count++;
                        }
                    }
                } else if (elem instanceof JSONArray) {
                    JSONArray singleOpt = (JSONArray) elem;
                    if (singleOpt.size() >= 2 && singleOpt.get(0) instanceof Number) {
                        long optId = ((Number) singleOpt.get(0)).longValue();
                        if (REPLACEMENT_MAP.containsKey(optId)) {
                            singleOpt.set(0, REPLACEMENT_MAP.get(optId));
                            count++;
                        }
                    }
                }
            }
            return new MigrationResult(array.toJSONString(), count);
        } catch (Exception e) {
            System.err.println("Lỗi khi parse Store Options JSON: " + e.getMessage());
            return new MigrationResult(jsonStr, 0);
        }
    }

    public void run(Connection conn, boolean dryRun) throws SQLException {
        System.out.println("============================================================");
        System.out.println("  BẮT ĐẦU QUÁ TRÌNH DI TRÚ DATABASE CHUẨN HÓA MÃ OPTION");
        System.out.println("  Chế độ: " + (dryRun ? "[DRY-RUN (Xem trước, không ghi DB)]" : "[EXECUTE (Thực thi và ghi DB)]"));
        System.out.println("============================================================");

        totalReplacedOptions = 0;
        totalUpdatedRows = 0;

        if (!dryRun) {
            conn.setAutoCommit(false);
        }

        try {
            // 1. Migrate bảng players
            migratePlayerTable(conn, dryRun);

            // 2. Migrate bảng clone_char
            migrateCloneCharTable(conn, dryRun);

            // 3. Migrate bảng store_data
            migrateStoreDataTable(conn, dryRun);

            // 4. Migrate bảng weapon_store
            migrateWeaponStoreTable(conn, dryRun);

            if (!dryRun) {
                conn.commit();
                System.out.println(">> GIAO DỊCH COMMIT THÀNH CÔNG! Đã cập nhật cơ sở dữ liệu.");
            } else {
                System.out.println(">> CHẾ ĐỘ DRY-RUN KẾT THÚC: Không có dữ liệu nào bị thay đổi.");
            }
        } catch (Exception e) {
            if (!dryRun) {
                System.err.println("!! XẢY RA LỖI, ĐANG TỰ ĐỘNG ROLLBACK DATABASE: " + e.getMessage());
                conn.rollback();
            }
            throw new SQLException(e);
        }

        System.out.println("============================================================");
        System.out.println("  TỔNG KẾT MIGRATION:");
        System.out.println("  - Số dòng dữ liệu (records) đã xử lý : " + totalUpdatedRows);
        System.out.println("  - Số mã Option đã được chuẩn hóa     : " + totalReplacedOptions);
        System.out.println("============================================================");
    }

    private void migratePlayerTable(Connection conn, boolean dryRun) throws SQLException {
        System.out.println("\n[1/4] Đang quét và di trú bảng: players...");
        String selectSql = "SELECT id, name, equiped, bag, box, mount, bijuu FROM players";
        String updateSql = "UPDATE players SET equiped = ?, bag = ?, box = ?, mount = ?, bijuu = ? WHERE id = ?";

        int rowCount = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql);
             PreparedStatement pstmt = dryRun ? null : conn.prepareStatement(updateSql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String equiped = rs.getString("equiped");
                String bag = rs.getString("bag");
                String box = rs.getString("box");
                String mount = rs.getString("mount");
                String bijuu = rs.getString("bijuu");

                MigrationResult resEquiped = migrateItemArrayJson(equiped);
                MigrationResult resBag = migrateItemArrayJson(bag);
                MigrationResult resBox = migrateItemArrayJson(box);
                MigrationResult resMount = migrateItemArrayJson(mount);
                MigrationResult resBijuu = migrateItemArrayJson(bijuu);

                int rowChanges = resEquiped.replacedCount + resBag.replacedCount + resBox.replacedCount
                        + resMount.replacedCount + resBijuu.replacedCount;

                if (rowChanges > 0) {
                    rowCount++;
                    totalReplacedOptions += rowChanges;
                    System.out.printf("  [-] Player [%s - ID %d]: Chuẩn hóa %d option (Equiped: %d, Bag: %d, Box: %d, Mount: %d, Bijuu: %d)\n",
                            name, id, rowChanges, resEquiped.replacedCount, resBag.replacedCount, resBox.replacedCount, resMount.replacedCount, resBijuu.replacedCount);

                    if (!dryRun && pstmt != null) {
                        pstmt.setString(1, resEquiped.modifiedJson);
                        pstmt.setString(2, resBag.modifiedJson);
                        pstmt.setString(3, resBox.modifiedJson);
                        pstmt.setString(4, resMount.modifiedJson);
                        pstmt.setString(5, resBijuu.modifiedJson);
                        pstmt.setInt(6, id);
                        pstmt.executeUpdate();
                    }
                }
            }
        }
        totalUpdatedRows += rowCount;
        System.out.printf("  => Bảng players: %d nhân vật cần cập nhật.\n", rowCount);
    }

    private void migrateCloneCharTable(Connection conn, boolean dryRun) throws SQLException {
        System.out.println("\n[2/4] Đang quét và di trú bảng: clone_char...");
        String selectSql = "SELECT id, equiped, fashion, bijuu, mount FROM clone_char";
        String updateSql = "UPDATE clone_char SET equiped = ?, fashion = ?, bijuu = ?, mount = ? WHERE id = ?";

        int rowCount = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql);
             PreparedStatement pstmt = dryRun ? null : conn.prepareStatement(updateSql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String equiped = rs.getString("equiped");
                String fashion = rs.getString("fashion");
                String bijuu = rs.getString("bijuu");
                String mount = rs.getString("mount");

                MigrationResult resEquiped = migrateItemArrayJson(equiped);
                MigrationResult resFashion = migrateItemArrayJson(fashion);
                MigrationResult resBijuu = migrateItemArrayJson(bijuu);
                MigrationResult resMount = migrateItemArrayJson(mount);

                int rowChanges = resEquiped.replacedCount + resFashion.replacedCount + resBijuu.replacedCount + resMount.replacedCount;
                if (rowChanges > 0) {
                    rowCount++;
                    totalReplacedOptions += rowChanges;
                    System.out.printf("  [-] Clone Char [ID %d]: Chuẩn hóa %d option\n", id, rowChanges);

                    if (!dryRun && pstmt != null) {
                        pstmt.setString(1, resEquiped.modifiedJson);
                        pstmt.setString(2, resFashion.modifiedJson);
                        pstmt.setString(3, resBijuu.modifiedJson);
                        pstmt.setString(4, resMount.modifiedJson);
                        pstmt.setInt(5, id);
                        pstmt.executeUpdate();
                    }
                }
            }
        }
        totalUpdatedRows += rowCount;
        System.out.printf("  => Bảng clone_char: %d bản ghi cần cập nhật.\n", rowCount);
    }

    private void migrateStoreDataTable(Connection conn, boolean dryRun) throws SQLException {
        System.out.println("\n[3/4] Đang quét và di trú bảng: store_data...");
        String selectSql = "SELECT id, item_id, options FROM store_data WHERE LENGTH(options) > 2";
        String updateSql = "UPDATE store_data SET options = ? WHERE id = ?";

        int rowCount = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql);
             PreparedStatement pstmt = dryRun ? null : conn.prepareStatement(updateSql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int itemId = rs.getInt("item_id");
                String options = rs.getString("options");

                MigrationResult res = migrateStoreOptionsJson(options);
                if (res.replacedCount > 0) {
                    rowCount++;
                    totalReplacedOptions += res.replacedCount;

                    if (!dryRun && pstmt != null) {
                        pstmt.setString(1, res.modifiedJson);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                    }
                }
            }
        }
        totalUpdatedRows += rowCount;
        System.out.printf("  => Bảng store_data: %d mặt hàng cần chuẩn hóa.\n", rowCount);
    }

    private void migrateWeaponStoreTable(Connection conn, boolean dryRun) throws SQLException {
        System.out.println("\n[4/4] Đang quét và di trú bảng: weapon_store...");
        String selectSql = "SELECT id, templateId, options FROM weapon_store WHERE options IS NOT NULL AND LENGTH(options) > 2";
        String updateSql = "UPDATE weapon_store SET options = ? WHERE id = ?";

        int rowCount = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql);
             PreparedStatement pstmt = dryRun ? null : conn.prepareStatement(updateSql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int templateId = rs.getInt("templateId");
                String options = rs.getString("options");

                MigrationResult res = migrateStoreOptionsJson(options);
                if (res.replacedCount > 0) {
                    rowCount++;
                    totalReplacedOptions += res.replacedCount;

                    if (!dryRun && pstmt != null) {
                        pstmt.setString(1, res.modifiedJson);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException ignored) {
            // Trường hợp weapon_store không tồn tại hoặc không dùng
        }
        totalUpdatedRows += rowCount;
        System.out.printf("  => Bảng weapon_store: %d mặt hàng cần chuẩn hóa.\n", rowCount);
    }

    public static void main(String[] args) {
        boolean dryRun = true;
        for (String arg : args) {
            if ("--execute".equalsIgnoreCase(arg)) {
                dryRun = false;
            }
        }

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("mysql.properties")) {
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Không thể đọc file mysql.properties: " + e.getMessage());
            return;
        }

        String host = props.getProperty("nsoz.database.host", "localhost");
        String port = props.getProperty("nsoz.database.port", "3306");
        String name = props.getProperty("nsoz.database.name", "nso_test");
        String user = props.getProperty("nsoz.database.user", "root");
        String pass = props.getProperty("nsoz.database.pass", "");

        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false", host, port, name);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ignored) {}
        }

        System.out.println("Kết nối Database: " + host + ":" + port + "/" + name + " (user: " + user + ")");
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            OptionMigrationTool tool = new OptionMigrationTool();
            tool.run(conn, dryRun);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
