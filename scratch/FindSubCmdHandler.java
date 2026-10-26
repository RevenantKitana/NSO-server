import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindSubCmdHandler {
    public static void main(String[] args) throws Exception {
        JarFile jar = new JarFile(".client/NSO.jar");
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                byte[] bytes = is.readAllBytes();
                // Check if bytecode has -127 (ME_LOAD_ALL) or -126 (ME_LOAD_CLASS)
                // -127 in signed byte is 129 in unsigned
                // In bytecode: bipush -127 -> 0x10 0x81 (or iconst/sipush)
                for (int i = 0; i < bytes.length - 2; i++) {
                    if ((bytes[i] & 0xFF) == 0x10 && (bytes[i + 1] & 0xFF) == 0x81) { // bipush -127
                        System.out.println("ME_LOAD_ALL (-127) found in: " + entry.getName());
                    }
                    if ((bytes[i] & 0xFF) == 0x10 && (bytes[i + 1] & 0xFF) == 0x82) { // bipush -126
                        System.out.println("ME_LOAD_CLASS (-126) found in: " + entry.getName());
                    }
                    if ((bytes[i] & 0xFF) == 0x10 && (bytes[i + 1] & 0xFF) == 0x85) { // bipush -123
                        System.out.println("ME_LOAD_INFO (-123) found in: " + entry.getName());
                    }
                }
            }
        }
        jar.close();
    }
}
