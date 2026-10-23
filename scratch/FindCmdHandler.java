import java.io.*;
import java.util.jar.*;

public class FindCmdHandler {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) != -1) baos.write(buf, 0, n);
                byte[] bytes = baos.toByteArray();
                
                // Let's check if the class contains bipush -26 (0x10 0xE6) or tableswitch / lookupswitch with -26 (0xFFFFFFE6)
                // 0xFF 0xFF 0xFF 0xE6
                for (int i = 0; i < bytes.length - 3; i++) {
                    if ((bytes[i] & 0xFF) == 0xFF && (bytes[i+1] & 0xFF) == 0xFF && (bytes[i+2] & 0xFF) == 0xFF && (bytes[i+3] & 0xFF) == 0xE6) {
                        System.out.println("Found switch case -26 in: " + entry.getName() + " offset " + i);
                    }
                }
            }
        }
        jar.close();
    }
}
