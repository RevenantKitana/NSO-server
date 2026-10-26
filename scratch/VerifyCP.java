import java.io.*;

public class VerifyCP {
    public static void main(String[] args) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"javap", "-v", "-p", "-cp", "scratch/curr", "em"});
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l;
        while ((l = r.readLine()) != null) {
            if (l.contains("#262") || l.contains("#334") || l.contains("#342") || l.contains("#137") || l.contains("#128")) {
                System.out.println(l);
            }
        }
    }
}
