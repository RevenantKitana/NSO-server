import java.io.*;
import java.util.jar.*;

public class FindCanvas {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) != -1) baos.write(buf, 0, n);
                String s = new String(baos.toByteArray(), "ISO-8859-1");
                if (s.contains("pointerPressed") || s.contains("pointerDragged") || s.contains("Canvas")) {
                    System.out.println("Match: " + entry.getName());
                }
            }
        }
        jar.close();
    }
}
