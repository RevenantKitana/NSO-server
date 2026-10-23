import java.io.*;
import java.util.*;
import java.util.jar.*;

public class CheckUpgradeComparison {
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
                
                // Search for byte sequences having bipush 4, bipush 8, bipush 12, bipush 14, bipush 16
                // In Java bytecode:
                // bipush 4 = 16 04 (or iconst_4 = 07)
                // bipush 8 = 16 08
                // bipush 12 = 16 0c
                // bipush 14 = 16 0e
                // bipush 16 = 16 10
                boolean has4 = false, has8 = false, has12 = false, has14 = false, has16 = false;
                for (int i = 0; i < bytes.length - 1; i++) {
                    if (bytes[i] == 16 && bytes[i+1] == 8) has8 = true;
                    if (bytes[i] == 16 && bytes[i+1] == 12) has12 = true;
                    if (bytes[i] == 16 && bytes[i+1] == 14) has14 = true;
                    if (bytes[i] == 16 && bytes[i+1] == 16) has16 = true;
                }
                if (has8 && has12 && has14 && has16) {
                    System.out.println("Class checking upgrade tiers (8, 12, 14, 16): " + entry.getName());
                }
            }
        }
    }
}
