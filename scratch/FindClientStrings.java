import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindClientStrings {
    public static void main(String[] args) throws Exception {
        JarFile jar = new JarFile(".client/NSO.jar");
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                byte[] bytes = is.readAllBytes();
                String utf8 = new String(bytes, "UTF-8");
                if (utf8.contains("Tốc độ") || utf8.contains("Tốc") || utf8.contains("di chuyển")
                        || utf8.contains("Chính xác") || utf8.contains("Né đòn") || utf8.contains("Chí mạng")
                        || utf8.contains("Kháng")) {
                    System.out.println("Match in: " + entry.getName());
                }
            }
        }
        jar.close();
    }
}
