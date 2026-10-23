import java.io.*;
import java.util.jar.*;

public class FindMessage26 {
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
                while ((n = is.read(buf)) != -1) {
                    baos.write(buf, 0, n);
                }
                byte[] bytes = baos.toByteArray();
                // look for -26 (0xE6) preceded by bipush (0x10) -> 0x10 0xE6
                for (int i = 0; i < bytes.length - 1; i++) {
                    if ((bytes[i] & 0xFF) == 0x10 && (bytes[i + 1] & 0xFF) == 0xE6) {
                        System.out.println("Found bipush -26 in class: " + entry.getName());
                    }
                }
            }
        }
        jar.close();
    }
}
