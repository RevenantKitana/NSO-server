import java.io.*;

public class CheckEmDiff {
    public static void main(String[] args) throws Exception {
        // Run javap on orig and curr and grep for the differences
        Process p1 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", "scratch/orig", "em"});
        String orig = read(p1.getInputStream());
        Process p2 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", "scratch/curr", "em"});
        String curr = read(p2.getInputStream());

        // Find static {} in orig and curr
        printMethod(orig, "static {};");
        printMethod(curr, "static {};");
    }

    static void printMethod(String text, String m) {
        System.out.println("=== " + m + " ===");
        int idx = text.indexOf(m);
        if (idx != -1) {
            int end = text.indexOf("public", idx + m.length());
            if (end == -1) end = text.indexOf("private", idx + m.length());
            if (end == -1) end = text.length();
            System.out.println(text.substring(idx, Math.min(idx + 1200, end)));
        }
    }

    static String read(InputStream in) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String l;
        while ((l = r.readLine()) != null) {
            sb.append(l).append("\n");
        }
        return sb.toString();
    }
}
