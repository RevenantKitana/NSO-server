import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class ExportSkills {

    static class SkillTpl {
        int id;
        int classId;
        String name;
        int maxPoint;
        int type;
        int icon;
        String desc;
        Map<Integer, SkillLevel> skills = new TreeMap<>();
    }

    static class SkillLevel {
        int id;
        int templateId;
        int maxFight;
        int level;
        int manaUse;
        int cooldown;
        int point;
        int dx;
        int dy;
        String rawOptions;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Reading database/init_nso_clean.sql line by line...");

        Map<Integer, String> clazzMap = new LinkedHashMap<>();
        Map<Integer, String> optionMap = new HashMap<>();
        Map<Integer, SkillTpl> templateMap = new LinkedHashMap<>();
        List<SkillLevel> rawSkillLevels = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of("database/init_nso_clean.sql"), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("INSERT INTO `clazz` VALUES ")) {
                    parseClazz(line, clazzMap);
                } else if (line.startsWith("INSERT INTO `skill_option` VALUES ")) {
                    parseSkillOption(line, optionMap);
                } else if (line.startsWith("INSERT INTO `skill_template` VALUES ")) {
                    parseSkillTemplate(line, templateMap);
                } else if (line.startsWith("INSERT INTO `skill` VALUES ")) {
                    parseSkill(line, rawSkillLevels);
                }
            }
        }

        int skillCount = 0;
        for (SkillLevel sl : rawSkillLevels) {
            SkillTpl tpl = templateMap.get(sl.templateId);
            if (tpl != null) {
                tpl.skills.put(sl.point, sl);
                skillCount++;
            }
        }

        System.out.println("Loaded clazzes: " + clazzMap.size());
        System.out.println("Loaded skill options: " + optionMap.size());
        System.out.println("Loaded skill templates: " + templateMap.size());
        System.out.println("Loaded skill levels: " + skillCount);

        // Generate Markdown
        StringBuilder sb = new StringBuilder();
        sb.append("# CẨM NANG TOÀN DIỆN CHI TIẾT KỸ NĂNG VÀ TỪNG CẤP ĐỘ CỦA 6 PHÁI NSO\n\n");
        sb.append("> **Phiên bản máy chủ**: NSO Ninja School Private 2026  \n");
        sb.append("> **Mã nguồn tính toán**: `com.nsoz.skill`, `AbilityFromEquip`, `Char`  \n");
        sb.append("> **Cơ sở dữ liệu trích xuất**: MariaDB `nso_test` (`skill_template`, `skill`, `skill_option`, `clazz`)  \n");
        sb.append("> **Quy mô dữ liệu**: 6 Phái + Vô phái, 91 Skill Templates, 967 Cấp độ kỹ năng đầy đủ thông số.  \n\n");
        sb.append("---\n\n");

        sb.append("## MỤC LỤC\n");
        sb.append("1. [Cơ chế Hoạt động & Hệ thống Thuộc tính Kỹ năng](#1-cơ-chế-hoạt-động--hệ-thống-thuộc-tính-kỹ-năng)\n");
        sb.append("2. [Phái 1: Ninja Kiếm (Katana - Hệ Hỏa)](#2-phái-1-ninja-kiếm-katana---hệ-hỏa)\n");
        sb.append("3. [Phái 2: Ninja Phi Tiêu (Shuriken - Hệ Hỏa)](#3-phái-2-ninja-phi-tiêu-shuriken---hệ-hỏa)\n");
        sb.append("4. [Phái 3: Ninja Kunai (Đoản Đao - Hệ Băng)](#4-phái-3-ninja-kunai-đoản-đao---hệ-băng)\n");
        sb.append("5. [Phái 4: Ninja Cung (Cung Tên - Hệ Băng)](#5-phái-4-ninja-cung-cung-tên---hệ-băng)\n");
        sb.append("6. [Phái 5: Ninja Đao (Bản Đao - Hệ Phong)](#6-phái-5-ninja-đao-bản-đao---hệ-phong)\n");
        sb.append("7. [Phái 6: Ninja Quạt (Phiến - Hệ Phong)](#7-phái-6-ninja-quạt-phiến---hệ-phong)\n");
        sb.append("8. [Phái 0: Chưa Vào Trường (Kỹ Năng Cơ Bản)](#8-phái-0-chưa-vào-trường-kỹ-năng-cơ-bản)\n\n");
        sb.append("---\n\n");

        sb.append("## 1. CƠ CHẾ HOẠT ĐỘNG & HỆ THỐNG THUỘC TÍNH KỸ NĂNG\n\n");
        sb.append("Trong Game Engine NSO:\n");
        sb.append("- **Point (Cấp điểm kỹ năng)**: Từ cấp 1 đến `maxPoint` (6, 10, 12, hoặc 15 điểm). Cấp 0 là trạng thái khởi tạo chưa cộng điểm.\n");
        sb.append("- **Level (Yêu cầu cấp nhân vật)**: Cấp độ nhân vật tối thiểu để người chơi có thể cộng điểm vào cấp kỹ năng đó.\n");
        sb.append("- **Mana Use (Tiêu hao MP)**: Lượng Chakra (MP) tiêu tốn mỗi lần xuất chiêu.\n");
        sb.append("- **Cooldown (Hồi chiêu)**: Tính bằng mili-giây (ms). Các chiêu tâm pháp/bị động có cooldown = 0ms.\n");
        sb.append("- **Max Fight (Số mục tiêu)**: 1 (đơn mục tiêu), 3 hoặc 4 (đánh lan diện rộng).\n");
        sb.append("- **Phạm vi tác động (dx, dy)**: Khoảng cách tầm đánh ngang (dx) và dọc (dy) tính bằng pixel.\n");
        sb.append("- **Options (Chỉ số hiệu ứng)**: Sát thương phần trăm, điểm sát thương hệ, hỗ trợ chiêu cấp trước/sau, hiệu ứng khống chế (bỏng, đóng băng, choáng, tàng hình, hồi phục, hồi sinh...).\n\n");
        sb.append("---\n\n");

        Map<Integer, String> classTitles = Map.of(
            1, "## 2. PHÁI 1: NINJA KIẾM (KATANA - HỆ HỎA)\n\n* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Cận chiến / Ngoại công (Sức Khỏe)\n* **Đặc trưng**: Sát thương bạo kích cực lớn, tỉ lệ nổ chí mạng cao, đòn đánh dồn dập thiêu đốt rút máu.\n",
            2, "## 3. PHÁI 2: NINJA PHI TIÊU (SHURIKEN - HỆ HỎA)\n\n* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Viễn chiến / Nội công (Chakra)\n* **Đặc trưng**: Tốc độ ném phi tiêu nhanh nhất game, tầm xa linh hoạt, phân thân thả diều đối thủ.\n",
            3, "## 4. PHÁI 3: NINJA KUNAI (ĐOẢN ĐAO - HỆ BĂNG)\n\n* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Cận chiến / Ám sát / Ngoại công (Sức Khỏe)\n* **Đặc trưng**: Đột kích chớp nhoáng, khống chế làm chậm và đóng băng kẻ địch, khả năng né tránh vô địch.\n",
            4, "## 5. PHÁI 4: NINJA CUNG (CUNG TÊN - HỆ BĂNG)\n\n* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Viễn chiến / Tầm xa / Nội công (Chakra)\n* **Đặc trưng**: Tầm bắn xa nhất trò chơi, chính xác tuyệt đối, khóa chân mục tiêu từ ngoài tầm nhìn.\n",
            5, "## 6. PHÁI 5: NINJA ĐAO (BẢN ĐAO - HỆ PHONG)\n\n* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Tanker / Đấu sĩ / Ngoại công (Sức Khỏe + Thể Lực)\n* **Đặc trưng**: Máu dày, giáp trâu nhất game, sát thương sấm sét gây Choáng (Stun) diện rộng.\n",
            6, "## 7. PHÁI 6: NINJA QUẠT (PHIẾN - HỆ PHONG)\n\n* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Pháp sư / Hỗ trợ / Nội công (Chakra + Thể Lực)\n* **Đặc trưng**: Khống chế bão lốc xoáy, duy nhất có khả năng Hồi máu (Heal), Buff Công Thủ và Hồi sinh đồng đội.\n"
        );

        int[] classOrder = {1, 2, 3, 4, 5, 6, 0};
        for (int cId : classOrder) {
            String title = classTitles.get(cId);
            if (title == null) {
                title = "## 8. PHÁI 0: CHƯA VÀO TRƯỜNG (KỸ NĂNG CƠ BẢN)\n\n* Kỹ năng nhập môn ban đầu dành cho tân thủ khi chưa gia nhập học viện ninja.\n";
            }
            sb.append(title).append("\n");

            List<SkillTpl> classTemplates = new ArrayList<>();
            for (SkillTpl t : templateMap.values()) {
                if (t.classId == cId) {
                    classTemplates.add(t);
                }
            }
            classTemplates.sort(Comparator.comparingInt(a -> a.id));

            for (SkillTpl t : classTemplates) {
                sb.append("### Skill ID ").append(t.id).append(": ").append(t.name).append("\n");
                sb.append("* **Loại chiêu**: ").append(getTypeName(t.type)).append("\n");
                sb.append("* **Cấp kỹ năng tối đa**: ").append(t.maxPoint).append(" điểm\n");
                sb.append("* **Mô tả cốt truyện**: ").append(t.desc).append("\n\n");

                sb.append("| Cấp Skill | Y/C Cấp Độ | MP Tiêu Hao | Thời Gian Hồi | Mục Tiêu | Tầm Đánh (dx x dy) | Chi Tiết Hiệu Ứng & Thuộc Tính |\n");
                sb.append("| :---: | :---: | :---: | :---: | :---: | :---: | :--- |\n");

                for (Map.Entry<Integer, SkillLevel> entry : t.skills.entrySet()) {
                    SkillLevel s = entry.getValue();
                    String pointStr = (s.point == 0) ? "Cấp 0 *(Gốc)*" : "**Cấp " + s.point + "**";
                    String cdStr = formatCooldown(s.cooldown);
                    String rangeStr = (s.dx == 0 && s.dy == 0) ? "Bản thân / Ẩn" : s.dx + " x " + s.dy;
                    String optStr = formatOptions(s.rawOptions, optionMap);

                    sb.append("| ").append(pointStr)
                      .append(" | Lv.").append(s.level)
                      .append(" | ").append(s.manaUse)
                      .append(" | ").append(cdStr)
                      .append(" | ").append(s.maxFight)
                      .append(" | ").append(rangeStr)
                      .append(" | ").append(optStr)
                      .append(" |\n");
                }
                sb.append("\n");
            }
            sb.append("---\n\n");
        }

        Files.writeString(Path.of("CHI_TIET_KY_NANG_6_PHAI_VA_TUNG_CAP.md"), sb.toString(), StandardCharsets.UTF_8);
        System.out.println("Done! Successfully written CHI_TIET_KY_NANG_6_PHAI_VA_TUNG_CAP.md (" + sb.length() + " chars)");
    }

    private static void parseClazz(String line, Map<Integer, String> map) {
        String data = line.substring("INSERT INTO `clazz` VALUES ".length());
        List<List<String>> rows = parseSqlTuples(data);
        for (List<String> r : rows) {
            if (r.size() >= 2) {
                map.put(Integer.parseInt(r.get(0)), r.get(1));
            }
        }
    }

    private static void parseSkillOption(String line, Map<Integer, String> map) {
        String data = line.substring("INSERT INTO `skill_option` VALUES ".length());
        List<List<String>> rows = parseSqlTuples(data);
        for (List<String> r : rows) {
            if (r.size() >= 2) {
                map.put(Integer.parseInt(r.get(0)), r.get(1));
            }
        }
    }

    private static void parseSkillTemplate(String line, Map<Integer, SkillTpl> map) {
        String data = line.substring("INSERT INTO `skill_template` VALUES ".length());
        List<List<String>> rows = parseSqlTuples(data);
        for (List<String> r : rows) {
            if (r.size() >= 7) {
                SkillTpl t = new SkillTpl();
                t.id = Integer.parseInt(r.get(0));
                t.classId = Integer.parseInt(r.get(1));
                t.name = r.get(2);
                t.maxPoint = Integer.parseInt(r.get(3));
                t.type = Integer.parseInt(r.get(4));
                t.icon = Integer.parseInt(r.get(5));
                t.desc = r.get(6);
                map.put(t.id, t);
            }
        }
    }

    private static void parseSkill(String line, List<SkillLevel> list) {
        String data = line.substring("INSERT INTO `skill` VALUES ".length());
        List<List<String>> rows = parseSqlTuples(data);
        for (List<String> r : rows) {
            if (r.size() >= 10) {
                SkillLevel s = new SkillLevel();
                s.id = Integer.parseInt(r.get(0));
                s.templateId = Integer.parseInt(r.get(1));
                s.maxFight = Integer.parseInt(r.get(2));
                s.level = Integer.parseInt(r.get(3));
                s.manaUse = Integer.parseInt(r.get(4));
                s.cooldown = Integer.parseInt(r.get(5));
                s.point = Integer.parseInt(r.get(6));
                s.dx = Integer.parseInt(r.get(7));
                s.dy = Integer.parseInt(r.get(8));
                s.rawOptions = r.get(9);
                list.add(s);
            }
        }
    }

    /**
     * Robust SQL VALUES parser: handles commas inside quotes and escaped quotes.
     */
    private static List<List<String>> parseSqlTuples(String text) {
        List<List<String>> result = new ArrayList<>();
        int len = text.length();
        int i = 0;

        while (i < len) {
            // Find opening '('
            while (i < len && text.charAt(i) != '(') {
                if (text.charAt(i) == ';') return result;
                i++;
            }
            if (i >= len) break;
            i++; // skip '('

            List<String> row = new ArrayList<>();
            StringBuilder curVal = new StringBuilder();
            boolean inQuote = false;

            while (i < len) {
                char c = text.charAt(i);

                if (inQuote) {
                    if (c == '\\' && i + 1 < len) {
                        curVal.append(text.charAt(i + 1));
                        i += 2;
                        continue;
                    } else if (c == '\'') {
                        // Check if escaped quote ''
                        if (i + 1 < len && text.charAt(i + 1) == '\'') {
                            curVal.append('\'');
                            i += 2;
                            continue;
                        } else {
                            inQuote = false;
                            i++;
                            continue;
                        }
                    } else {
                        curVal.append(c);
                        i++;
                        continue;
                    }
                } else {
                    if (c == '\'') {
                        inQuote = true;
                        i++;
                        continue;
                    } else if (c == ',') {
                        row.add(curVal.toString().trim());
                        curVal.setLength(0);
                        i++;
                        continue;
                    } else if (c == ')') {
                        row.add(curVal.toString().trim());
                        curVal.setLength(0);
                        i++;
                        break;
                    } else {
                        curVal.append(c);
                        i++;
                    }
                }
            }
            result.add(row);
        }

        return result;
    }

    private static String getTypeName(int type) {
        return switch (type) {
            case 0 -> "Bị động (Tâm pháp / Nội tại ẩn)";
            case 1 -> "Chủ động tấn công";
            case 2 -> "Hỗ trợ / Buff bản thân & đồng đội";
            case 3 -> "Khống chế / Bắt quái";
            case 4 -> "Hồi sinh đồng đội";
            default -> "Loại " + type;
        };
    }

    private static String formatCooldown(int cd) {
        if (cd == 0) return "0s (Bị động)";
        if (cd >= 1000) {
            double sec = cd / 1000.0;
            if (sec == (long) sec) {
                return (long) sec + "s";
            }
            return String.format(Locale.US, "%.2fs", sec);
        }
        return cd + "ms";
    }

    private static String formatOptions(String rawJson, Map<Integer, String> optionMap) {
        String clean = rawJson.replace("\\\"", "\"");
        Matcher m = Pattern.compile("\\{\"param\":(-?\\d+),\"id\":(\\d+)\\}").matcher(clean);
        List<String> list = new ArrayList<>();
        while (m.find()) {
            int param = Integer.parseInt(m.group(1));
            int id = Integer.parseInt(m.group(2));
            if (param != 0 || id == 0) {
                String tpl = optionMap.get(id);
                if (tpl != null) {
                    list.add(tpl.replace("#", String.valueOf(param)));
                } else {
                    list.add("Option " + id + ": " + param);
                }
            }
        }
        if (list.isEmpty()) {
            return "*(Chưa có hiệu ứng ở cấp 0)*";
        }
        return String.join(", ", list);
    }
}
