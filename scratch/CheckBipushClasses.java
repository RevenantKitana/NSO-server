import java.io.*;

public class CheckBipushClasses {
    public static void main(String[] args) throws Exception {
        String[] classes = {"di", "fo", "ag", "be", "ap", "r", "cq", "cf", "ey", "w"};
        for (String c : classes) {
            Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", c});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String currentMethod = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("(") && line.contains(")") && line.endsWith(";")) {
                    currentMethod = line.trim();
                }
                if (line.contains("bipush        -26")) {
                    System.out.println("Class " + c + " -> method: " + currentMethod + " -> " + line.trim());
                }
            }
            p.waitFor();
        }
    }
}
