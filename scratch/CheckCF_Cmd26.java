import java.io.*;

public class CheckCF_Cmd26 {
    public static void main(String[] args) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "cf"});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        int lineNum = 0;
        while ((line = br.readLine()) != null) {
            lineNum++;
            if (line.contains("bipush        -26")) {
                System.out.println("Line: " + lineNum + ": " + line);
            }
        }
    }
}
