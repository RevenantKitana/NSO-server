import java.io.*;
import java.util.jar.*;

public class FindSubclassesAs {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", entry.getName().replace(".class", "")});
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = br.readLine();
                while (line != null) {
                    if (line.contains("extends as")) {
                        System.out.println("Subclass of as: " + entry.getName());
                    }
                    line = br.readLine();
                }
            }
        }
        jar.close();
    }
}
