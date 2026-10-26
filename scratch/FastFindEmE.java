import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FastFindEmE {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            if (e.getName().endsWith(".class")) {
                byte[] b = readStream(z.getInputStream(e));
                if (hasEmE(b, e.getName())) {
                    System.out.println("Matches: " + e.getName());
                }
            }
        }
        z.close();
    }

    static boolean hasEmE(byte[] b, String name) {
        // Simple search for string "em" and "e" and "Z"
        // In class file, constant pool has Utf8 entries.
        // Let's check if the class contains UTF8 "lowGraphic" or fieldref to em.e
        String s = new String(b);
        if (name.equals("em.class")) return true;
        // Search if "em" is followed or referenced
        return s.contains("lowGraphic") || (s.contains("em") && s.contains("()Z"));
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
