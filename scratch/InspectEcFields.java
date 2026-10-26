import java.io.*;
import java.util.*;

public class InspectEcFields {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
            "e:\\TT\\SETUP_LOCAL\\SRC_GAME\\tools\\jdk\\bin\\javap.exe",
            "-cp", ".client\\NSO.jar",
            "-p", "ec"
        );
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.contains("(") && (line.contains("int ") || line.contains("byte ") || line.contains("short "))) {
                System.out.println(line.trim());
            }
        }
        p.waitFor();
    }
}
