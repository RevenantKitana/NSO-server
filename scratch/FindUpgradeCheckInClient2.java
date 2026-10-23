import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindUpgradeCheckInClient2 {
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
                
                // Check if class has lookupswitch 0xAB with pairs 3, 4, 5, 6, 7
                for (int i = 0; i < bytes.length - 30; i++) {
                    if ((bytes[i] & 0xFF) == 0xAB) { // lookupswitch
                        int pad = 3 - (i % 4);
                        int npairsOffset = i + 1 + pad + 4;
                        if (npairsOffset + 4 <= bytes.length) {
                            int npairs = ((bytes[npairsOffset]&0xFF)<<24) | ((bytes[npairsOffset+1]&0xFF)<<16) | ((bytes[npairsOffset+2]&0xFF)<<8) | (bytes[npairsOffset+3]&0xFF);
                            if (npairs == 5) {
                                System.out.println("lookupswitch with 5 pairs in: " + entry.getName());
                            }
                        }
                    }
                }
            }
        }
    }
}
