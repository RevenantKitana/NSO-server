import java.io.*;
import java.util.*;

public class FindTableSwitchCase {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "e:\\TT\\SETUP_LOCAL\\SRC_GAME\\tools\\jdk\\bin\\javap.exe",
            "-cp", ".client\\NSO.jar",
            "-c", "-p", "cf"
        );
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        p.waitFor();

        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains(" 648: ") || l.contains(" 657: ")) {
                for (int j = Math.max(0, i - 2); j < Math.min(lines.size(), i + 25); j++) {
                    System.out.println(lines.get(j));
                }
            }
        }
    }
}
