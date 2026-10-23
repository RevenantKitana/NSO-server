import java.io.*;

public class CheckPointerAndScroll {
    public static void main(String[] args) throws Exception {
        String[] classes = {"bt", "as", "em"};
        for (String c : classes) {
            Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "-p", c});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String curMethod = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("(") && line.contains(")") && (line.contains("public") || line.contains("protected"))) {
                    curMethod = line.trim();
                }
                // look for pointer, drag, scroll, wheel, cmy, cmto
                if (line.contains("pointer") || line.contains("drag") || line.contains("scroll") || line.contains("cmy") || line.contains("cmto") || line.contains("cmx")) {
                    System.out.println(c + " -> " + curMethod + " : " + line.trim());
                }
            }
        }
    }
}
