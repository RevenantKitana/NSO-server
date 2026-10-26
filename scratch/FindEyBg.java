import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FindEyBg {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            if (e.getName().endsWith(".class")) {
                byte[] b = readStream(z.getInputStream(e));
                String s = new String(b);
                if (s.contains("ey") && s.contains("bg")) {
                    // Check with javap
                    Process p = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", ".client/NSO.jar", e.getName().replace(".class", "")});
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String l;
                    while ((l = r.readLine()) != null) {
                        if (l.contains("ey.bg") || (e.getName().equals("ey.class") && l.contains("Field bg:[Ljava/lang/String;"))) {
                            System.out.println(e.getName() + " -> " + l.trim());
                        }
                    }
                }
            }
        }
        z.close();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
