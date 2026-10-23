import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectClient {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        if (!jarFile.exists()) {
            jarFile = new File("E:/TT/SETUP_LOCAL/SRC_GAME/.client/NSO.jar");
        }
        System.out.println("Inspecting jar: " + jarFile.getAbsolutePath());
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
                
                if (content.contains("(+4)") || content.contains("(+8)") || content.contains("(+12)") || content.contains("(+14)") || content.contains("(+16)")) {
                    System.out.println("Found (+X) text literal in class: " + entry.getName());
                }
            }
        }
        System.out.println("Search completed.");
    }
}
