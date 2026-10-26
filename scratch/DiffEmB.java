import java.io.*;

public class DiffEmB {
    public static void main(String[] args) throws Exception {
        Process p1 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", "scratch/orig", "em"});
        String s1 = getMethod(read(p1.getInputStream()), "public static void b(bh);");
        Process p2 = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", "scratch/curr", "em"});
        String s2 = getMethod(read(p2.getInputStream()), "public static void b(bh);");

        String[] lines1 = s1.split("\n");
        String[] lines2 = s2.split("\n");
        int max = Math.max(lines1.length, lines2.length);
        for (int i = 0; i < max; i++) {
            String l1 = i < lines1.length ? lines1[i] : "<end>";
            String l2 = i < lines2.length ? lines2[i] : "<end>";
            if (!l1.equals(l2)) {
                System.out.println("Line " + i + ":");
                System.out.println("  orig: " + l1);
                System.out.println("  curr: " + l2);
            }
        }
    }

    static String getMethod(String text, String m) {
        int idx = text.indexOf(m);
        if (idx == -1) return "";
        int end = text.indexOf("public", idx + m.length());
        if (end == -1) end = text.indexOf("private", idx + m.length());
        if (end == -1) end = text.length();
        return text.substring(idx, end);
    }

    static String read(InputStream in) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String l;
        while ((l = r.readLine()) != null) sb.append(l).append("\n");
        return sb.toString();
    }
}
