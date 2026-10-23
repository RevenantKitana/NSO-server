import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindOptionTemplate {
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
                
                // If a class has "readUTF" and reads options
                // Let's check classes with few fields or methods
                if (s.contains("readUTF") && s.contains("readByte")) {
                    // run javap
                    Process p = Runtime.getRuntime().exec("javap -cp .client/NSO.jar " + entry.getName().replace(".class", ""));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    boolean interesting = false;
                    List<String> lines = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                        if (line.contains("readUTF") || line.contains("ItemOption") || line.contains("String name")) {
                            interesting = true;
                        }
                    }
                    if (interesting) {
                        for (String l : lines) {
                            if (l.contains("class ") || l.contains("String") || l.contains("byte") || l.contains("int")) {
                                System.out.println("  " + l);
                            }
                        }
                    }
                }
            }
        }
    }
}
