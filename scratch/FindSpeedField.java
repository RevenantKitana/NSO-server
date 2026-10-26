import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindSpeedField {
    public static void main(String[] args) throws Exception {
        // Let's search in all classes for CMD.POTENTIAL_UP (-109, 0x93) or CMD.ITEM_USE (-13, 0xF3)
        // or ME_LOAD_CLASS (-126, 0x82)
        JarFile jar = new JarFile(".client/NSO.jar");
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                byte[] bytes = is.readAllBytes();
                for (int i = 0; i < bytes.length - 2; i++) {
                    if ((bytes[i] & 0xFF) == 0x10 && (bytes[i + 1] & 0xFF) == 0x93) { // bipush -109 (POTENTIAL_UP)
                        System.out.println("POTENTIAL_UP (-109) in " + entry.getName() + " offset " + i);
                    }
                }
            }
        }
        jar.close();
    }
}
