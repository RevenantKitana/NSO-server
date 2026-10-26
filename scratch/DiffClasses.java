import java.io.*;
import java.util.*;
import java.util.zip.*;

public class DiffClasses {
    public static void main(String[] args) throws Exception {
        String[] classes = {"et.class", "em.class", "ey.class"};
        ZipFile z1 = new ZipFile(".client/NSO.jar.sp_bak");
        ZipFile z2 = new ZipFile(".client/NSO.jar");
        for (String c : classes) {
            byte[] b1 = readStream(z1.getInputStream(z1.getEntry(c)));
            byte[] b2 = readStream(z2.getInputStream(z2.getEntry(c)));
            System.out.println("=== " + c + " ===");
            for (int i = 0; i < b1.length; i++) {
                if (b1[i] != b2[i]) {
                    System.out.printf("Diff at offset 0x%X (%d): 0x%02X -> 0x%02X\n", i, i, b1[i], b2[i]);
                }
            }
        }
        z1.close();
        z2.close();
    }

    private static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) {
            baos.write(buf, 0, n);
        }
        return baos.toByteArray();
    }
}
