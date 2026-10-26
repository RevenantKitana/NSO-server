import java.io.*;
import java.util.*;

public class DisassembleCfA {
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

        System.out.println("Total lines in cf: " + lines.size());
        // Find method "a(eh)"
        boolean inMethod = false;
        int count = 0;
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains("public final void a(eh)")) {
                inMethod = true;
                System.out.println("Found method a(eh) at line " + i);
            } else if (inMethod && l.startsWith("  public ")) {
                inMethod = false;
            }
            if (inMethod) {
                // look for bipush -30 or lookupswitch
                if (l.contains("bipush") || l.contains("lookupswitch") || l.contains("tableswitch")) {
                    System.out.println(l);
                    count++;
                    if (count > 50) break;
                }
            }
        }
    }
}
