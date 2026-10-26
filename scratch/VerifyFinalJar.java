import java.io.*;
import java.util.*;
import java.util.zip.*;

public class VerifyFinalJar {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");

        // 1. MANIFEST.MF
        ZipEntry mf = z.getEntry("META-INF/MANIFEST.MF");
        System.out.println("=== MANIFEST.MF ===");
        System.out.println(new String(readStream(z.getInputStream(mf)), "UTF-8"));

        // 2. text.txt
        ZipEntry tt = z.getEntry("text.txt");
        System.out.println("=== text.txt ===");
        System.out.println(new String(readStream(z.getInputStream(tt)), "UTF-8"));

        // 3. ag.class CP #3400
        byte[] agData = readStream(z.getInputStream(z.getEntry("ag.class")));
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(agData));
        dis.readInt(); dis.readUnsignedShort(); dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            if (tag == 1) {
                String s = dis.readUTF();
                if (i == 3400 || s.contains("MioNso") || s.contains("NSOTIEN")) {
                    System.out.println("ag.class CP #" + i + ": " + s);
                }
            } else if (tag == 3 || tag == 4) dis.readInt();
            else if (tag == 5 || tag == 6) { dis.readLong(); i++; }
            else if (tag == 7 || tag == 8) dis.readUnsignedShort();
            else if (tag >= 9 && tag <= 12) dis.readInt();
        }
        z.close();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
