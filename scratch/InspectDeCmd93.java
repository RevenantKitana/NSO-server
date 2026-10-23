import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectDeCmd93 {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        JarEntry entry = jar.getJarEntry("de.class");
        InputStream is = jar.getInputStream(entry);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = is.read(buf)) > 0) baos.write(buf, 0, n);
        byte[] bytes = baos.toByteArray();
        
        // Find 93 in de.class
        for (int i = 0; i < bytes.length - 10; i++) {
            if (bytes[i] == (byte)93) {
                // print nearby bytes
                System.out.println("Found 93 at offset: " + i);
            }
        }
    }
}
