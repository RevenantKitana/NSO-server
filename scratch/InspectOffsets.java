import java.io.*;
import java.util.*;
import java.util.zip.*;

public class InspectOffsets {
    public static void main(String[] args) throws Exception {
        ZipFile z_orig = new ZipFile(".client/NSO.jar.sp_bak");
        ZipFile z_curr = new ZipFile(".client/NSO.jar");

        checkClass(z_orig, z_curr, "ey.class");
        checkClass(z_orig, z_curr, "em.class");
    }

    static void checkClass(ZipFile z1, ZipFile z2, String name) throws Exception {
        byte[] b1 = readStream(z1.getInputStream(z1.getEntry(name)));
        byte[] b2 = readStream(z2.getInputStream(z2.getEntry(name)));

        File f1 = new File("scratch/" + name + ".orig");
        File f2 = new File("scratch/" + name + ".curr");
        writeFile(f1, b1);
        writeFile(f2, b2);

        System.out.println("Written scratch/" + name + ".orig and .curr");
    }

    static void writeFile(File f, byte[] b) throws Exception {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(b);
        fos.close();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
