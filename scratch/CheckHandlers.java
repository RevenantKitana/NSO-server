import java.io.*;

public class CheckHandlers {
    public static void main(String[] args) throws Exception {
        String[] classes = {"ab", "dm", "dj", "cf", "ey"};
        for (String c : classes) {
            Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", c});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String currentMethod = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("(") && line.contains(")") && line.endsWith(";")) {
                    currentMethod = line.trim();
                }
                if (line.contains("readUTF")) {
                    System.out.println("Class " + c + " -> method " + currentMethod + " calls readUTF");
                }
            }
        }
    }
}
