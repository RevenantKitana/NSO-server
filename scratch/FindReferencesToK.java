import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindReferencesToK {
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
                String s = new String(bytes, "ISO-8859-1");
                
                if (s.contains("k;") || s.contains("Lk;")) {
                    System.out.println("Class referencing k: " + entry.getName());
                }
            }
        }
    }
}
