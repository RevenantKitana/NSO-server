import java.io.*;
import java.util.zip.*;

public class CheckEtDiff {
    public static void main(String[] args) throws Exception {
        ZipFile z1 = new ZipFile(".client/NSO.jar.sp_bak");
        ZipFile z2 = new ZipFile(".client/NSO.jar");
        dump(z1, "scratch/et_orig.class");
        dump(z2, "scratch/et_curr.class");
        z1.close();
        z2.close();
        System.out.println("Dumped et classes");
    }

    static void dump(ZipFile z, String out) throws Exception {
        InputStream in = z.getInputStream(z.getEntry("et.class"));
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) fos.write(buf, 0, n);
        fos.close();
        in.close();
    }
}
