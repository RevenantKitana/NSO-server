import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectClientSpeed {
    public static void main(String[] args) throws Exception {
        JarFile jar = new JarFile(".client/NSO.jar");
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                byte[] bytes = is.readAllBytes();
                String s = new String(bytes, "ISO-8859-1");
                if (s.contains("readByte")) {
                    System.out.println("Calls readByte: " + entry.getName());
                }
            }
        }
        jar.close();
    }
}
