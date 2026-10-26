import java.io.*;
import java.util.*;
import java.util.zip.*;

public class DeepScanClient {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            byte[] data = readStream(z.getInputStream(e));
            if (e.getName().endsWith(".class")) {
                scanClass(e.getName(), data);
            }
        }
        z.close();
    }

    static void scanClass(String className, byte[] b) throws Exception {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        int magic = dis.readInt();
        if (magic != 0xCAFEBABE) return;
        dis.readUnsignedShort(); dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            switch (tag) {
                case 1:
                    String str = dis.readUTF();
                    check(className, i, str);
                    break;
                case 3: case 4: dis.readInt(); break;
                case 5: case 6: dis.readLong(); i++; break;
                case 7: case 8: dis.readUnsignedShort(); break;
                case 9: case 10: case 11: case 12: dis.readInt(); break;
                default: return;
            }
        }
    }

    static void check(String className, int idx, String s) {
        String l = s.toLowerCase();
        if (l.contains("tien") || l.contains("zalo") || l.contains("fb.com") || l.contains("facebook") ||
            l.contains("youtube") || l.contains("admin") || l.contains("tac gia") || l.contains("tác giả") ||
            l.contains("phien ban") || l.contains("phiên bản") || l.contains("nso") && (l.contains("mod") || l.contains(".com"))) {
            System.out.println(className + " [#" + idx + "]: " + s);
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
