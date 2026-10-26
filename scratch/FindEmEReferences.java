import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FindEmEReferences {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> e = z.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            if (entry.getName().endsWith(".class")) {
                byte[] b = readStream(z.getInputStream(entry));
                // Look for UTF8 constant "em" or field ref "e" "Z"
                // Or disassemble using javap
                checkClass(entry.getName());
            }
        }
        z.close();
    }

    static void checkClass(String className) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", ".client/NSO.jar", className.replace(".class", "")});
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String curMethod = "";
        while ((line = r.readLine()) != null) {
            if (line.endsWith(";") && !line.startsWith(" ")) {
                curMethod = line;
            } else if (line.contains("em.e:Z") || line.contains("Field e:Z")) {
                System.out.println(className + " -> " + curMethod.trim() + " :: " + line.trim());
            }
        }
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
