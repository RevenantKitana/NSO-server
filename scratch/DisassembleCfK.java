import java.io.*;
import java.util.*;

public class DisassembleCfK {
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

        boolean inMethod = false;
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains("void k(eh)")) {
                inMethod = true;
                System.out.println("Found void k(eh) at line " + i);
            } else if (inMethod && (l.startsWith("  public ") || l.startsWith("  private ") || l.startsWith("  static "))) {
                inMethod = false;
            }
            if (inMethod) {
                System.out.println(l);
            }
        }
    }
}
