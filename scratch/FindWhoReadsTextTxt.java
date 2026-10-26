import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FindWhoReadsTextTxt {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            if (e.getName().endsWith(".class")) {
                byte[] b = readStream(z.getInputStream(e));
                String s = new String(b);
                if (s.contains("text.txt")) {
                    System.out.println("Class referencing text.txt: " + e.getName());
                }
                if (s.contains("hd.txt")) {
                    System.out.println("Class referencing hd.txt: " + e.getName());
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
