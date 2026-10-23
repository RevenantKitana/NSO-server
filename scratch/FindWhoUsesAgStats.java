import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindWhoUsesAgStats {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class") && !entry.getName().equals("ag.class")) {
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) > 0) baos.write(buf, 0, n);
                byte[] bytes = baos.toByteArray();
                String s = new String(bytes, "ISO-8859-1");
                
                // If it references ag.class
                if (s.contains("ag") && (s.contains("sysUp") || s.contains("VIEW_INFO") || s.contains("dameDown") || s.contains("exactly"))) {
                    System.out.println("Class drawing info: " + entry.getName());
                }
            }
        }
    }
}
