import java.io.*;

public class DiffEtJavap {
    public static void main(String[] args) throws Exception {
        Process p1 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "scratch/et_orig.class"});
        String s1 = read(p1.getInputStream());
        Process p2 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "scratch/et_curr.class"});
        String s2 = read(p2.getInputStream());

        String[] lines1 = s1.split("\n");
        String[] lines2 = s2.split("\n");
        for (int i = 0; i < Math.min(lines1.length, lines2.length); i++) {
            if (!lines1[i].equals(lines2[i])) {
                System.out.println("Line " + i + ":");
                System.out.println("  orig: " + lines1[i]);
                System.out.println("  curr: " + lines2[i]);
            }
        }
    }

    static String read(InputStream in) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String l;
        while ((l = r.readLine()) != null) sb.append(l).append("\n");
        return sb.toString();
    }
}
