import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectItemOptionClient {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) > 0) baos.write(buf, 0, n);
                byte[] bytes = baos.toByteArray();
                
                // Let's check javap or byte patterns for -119 (0x89)
                for (int i = 0; i < bytes.length; i++) {
                    if (bytes[i] == (byte)-119) {
                        System.out.println("Class referencing CMD -119: " + entry.getName());
                        break;
                    }
                }
            }
        }
    }
}
