import java.io.*;
import java.util.*;

public class InspectModSpeed {
    public static void main(String[] args) throws Exception {
        // Disassemble ap.class looking for SPGame or speed
        ProcessBuilder pb = new ProcessBuilder(
            "e:\\TT\\SETUP_LOCAL\\SRC_GAME\\tools\\jdk\\bin\\javap.exe",
            "-cp", ".client\\NSO.jar",
            "-c", "-p", "ap"
        );
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        p.waitFor();

        System.out.println("Total lines in ap: " + lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains("SPGame") || l.contains("speed") || l.contains("SPNextMap")) {
                int start = Math.max(0, i - 15);
                int end = Math.min(lines.size(), i + 25);
                System.out.println("=== Match at " + i + ": " + l + " ===");
                for (int j = start; j < end; j++) {
                    System.out.println(lines.get(j));
                }
            }
        }
    }
}
