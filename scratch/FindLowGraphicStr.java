import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FindLowGraphicStr {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> e = z.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream in = z.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
                String s = new String(baos.toByteArray());
                if (s.contains("lowGraphic")) {
                    System.out.println("Found in: " + entry.getName());
                }
            }
        }
        z.close();
    }
}
