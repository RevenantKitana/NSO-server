import java.io.*;

public class CheckCF_ReadUTFContext {
    public static void main(String[] args) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "cf"});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String[] ring = new String[30];
        int ringIdx = 0;
        int count = 0;
        while ((line = br.readLine()) != null) {
            ring[ringIdx % ring.length] = line;
            ringIdx++;
            if (line.contains("readUTF")) {
                count++;
                System.out.println("=== readUTF match #" + count + " ===");
                // print preceding 10 lines
                for (int i = 0; i < ring.length; i++) {
                    String prev = ring[(ringIdx - ring.length + i + ring.length) % ring.length];
                    if (prev != null) System.out.println("  " + prev);
                }
                // print following 10 lines
                for (int i = 0; i < 15; i++) {
                    String next = br.readLine();
                    if (next != null) {
                        System.out.println("  " + next);
                        ring[ringIdx % ring.length] = next;
                        ringIdx++;
                    }
                }
            }
        }
    }
}
