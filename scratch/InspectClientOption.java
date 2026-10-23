import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectClientOption {
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
                String content = new String(bytes, "ISO-8859-1");
                
                // Check if class has item option formatting (replacing '#' with value)
                if (content.contains("#") && (content.contains("replace") || content.contains("indexOf"))) {
                    System.out.println("Candidate item option formatter class: " + entry.getName());
                }
            }
        }
    }
}
