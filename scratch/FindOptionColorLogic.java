import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindOptionColorLogic {
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
                
                // Let's check classes that use '#' or format option
                String s = new String(bytes, "ISO-8859-1");
                if (s.contains("#") && s.contains("java/lang/String")) {
                    // Check if bytecode has tableswitch with 3, 4, 5, 6, 7 or if_icmpeq
                    // Let's see if it has 3, 4, 5, 6, 7
                    boolean hasAll = true;
                    for (int t = 3; t <= 7; t++) {
                        boolean found = false;
                        for (int i = 0; i < bytes.length; i++) {
                            if (bytes[i] == (byte)(t)) { // could be iconst_3...
                                found = true; break;
                            }
                        }
                        if (!found) hasAll = false;
                    }
                    if (hasAll) {
                        System.out.println("Candidate item draw info: " + entry.getName());
                    }
                }
            }
        }
    }
}
