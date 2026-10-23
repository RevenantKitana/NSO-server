import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindMessageController {
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
                
                // Search for byte pattern for -119 (0x89) in tableswitch / lookupswitch
                // In Java bytecode, -119 as byte is (byte)0x89
                // Let's see if this class has a switch with many cases like -119, -120, -121...
                int count = 0;
                for (byte b : bytes) {
                    if (b == (byte)-119 || b == (byte)-120 || b == (byte)-121 || b == (byte)-122) {
                        count++;
                    }
                }
                if (count > 20) {
                    System.out.println("Likely Message Handler Class: " + entry.getName() + " (matches: " + count + ")");
                }
            }
        }
    }
}
