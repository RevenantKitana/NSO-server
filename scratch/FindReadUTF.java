import java.io.*;
import java.util.jar.*;

public class FindReadUTF {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "-v", entry.getName().replace(".class", "")});
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                boolean hasReadUTF = false;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    if (line.contains("readUTF")) {
                        hasReadUTF = true;
                    }
                    sb.append(line).append("\n");
                }
                if (hasReadUTF) {
                    String full = sb.toString();
                    if (full.contains("-26") || full.contains("230")) {
                        System.out.println("Class " + entry.getName() + " has readUTF and -26/230");
                    }
                }
            }
        }
        jar.close();
    }
}
