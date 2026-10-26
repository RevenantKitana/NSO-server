import java.io.*;
import java.util.*;
import java.util.zip.*;

public class InspectAgClass {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        byte[] b = readStream(z.getInputStream(z.getEntry("ag.class")));
        z.close();

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        int magic = dis.readInt();
        int minor = dis.readUnsignedShort();
        int major = dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        System.out.println("ag.class: magic=" + Integer.toHexString(magic) + ", cpCount=" + cpCount);

        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            if (tag == 1) { // Utf8
                String s = dis.readUTF();
                if (s.equals("NSOTIEN.COM") || i == 3400) {
                    System.out.println("CP #" + i + " [tag 1]: " + s);
                }
            } else if (tag == 3 || tag == 4) {
                dis.readInt();
            } else if (tag == 5 || tag == 6) {
                dis.readLong();
                i++;
            } else if (tag == 7 || tag == 8) {
                dis.readUnsignedShort();
            } else if (tag == 9 || tag == 10 || tag == 11 || tag == 12) {
                dis.readInt();
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
