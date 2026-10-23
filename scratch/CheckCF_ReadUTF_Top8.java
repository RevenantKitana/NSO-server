import java.io.*;

public class CheckCF_ReadUTF_Top8 {
    public static void main(String[] args) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "cf"});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        boolean capturing = false;
        int count = 0;
        int linesAfter = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("readUTF")) {
                capturing = true;
                count++;
                if (count <= 8) {
                    System.out.println("Match #" + count + ": " + line.trim());
                    linesAfter = 0;
                } else break;
            } else if (capturing && count <= 8) {
                System.out.println("   " + line.trim());
                linesAfter++;
                if (linesAfter > 8) capturing = false;
            }
        }
    }
}
