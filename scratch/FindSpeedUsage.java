import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindSpeedUsage {
    public static void main(String[] args) throws Exception {
        JarFile jar = new JarFile(".client/NSO.jar");
        Enumeration<JarEntry> entries = jar.entries();
        // First find fields named "speed" or methods related to speed
        List<String> speedClasses = new ArrayList<>();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                byte[] bytes = is.readAllBytes();
                String utf8 = new String(bytes, "UTF-8");
                if (utf8.contains("speed")) {
                    speedClasses.add(entry.getName());
                }
            }
        }
        System.out.println("Classes containing 'speed': " + speedClasses);
        jar.close();
    }
}
