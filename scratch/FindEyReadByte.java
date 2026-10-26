import java.io.*;
import java.util.*;

public class FindEyReadByte {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "e:\\TT\\SETUP_LOCAL\\SRC_GAME\\tools\\jdk\\bin\\javap.exe",
            "-cp", ".client\\NSO.jar",
            "-c", "-p", "ey"
        );
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        p.waitFor();

        System.out.println("Total lines in ey: " + lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains("readByte")) {
                int start = Math.max(0, i - 10);
                int end = Math.min(lines.size(), i + 15);
                System.out.println("=== Match at " + i + ": " + l + " ===");
                for (int j = start; j < end; j++) {
                    System.out.println(lines.get(j));
                }
            }
        }
    }
}
