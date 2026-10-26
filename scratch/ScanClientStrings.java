import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ScanClientStrings {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");

        // 1. MANIFEST.MF
        ZipEntry mf = z.getEntry("META-INF/MANIFEST.MF");
        if (mf != null) {
            System.out.println("=== META-INF/MANIFEST.MF ===");
            System.out.println(new String(readStream(z.getInputStream(mf)), "UTF-8"));
            System.out.println("=============================");
        }

        // 2. Scan all entries
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            byte[] data = readStream(z.getInputStream(e));
            if (e.getName().endsWith(".class")) {
                scanClass(e.getName(), data);
            } else if (!e.isDirectory()) {
                String s = new String(data, "UTF-8");
                checkText(e.getName(), s);
            }
        }
        z.close();
    }

    static void scanClass(String className, byte[] b) throws Exception {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        int magic = dis.readInt();
        if (magic != 0xCAFEBABE) return;
        dis.readUnsignedShort();
        dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            switch (tag) {
                case 1: // Utf8
                    String str = dis.readUTF();
                    checkString(className, i, str);
                    break;
                case 3: case 4: dis.readInt(); break;
                case 5: case 6: dis.readLong(); i++; break;
                case 7: case 8: dis.readUnsignedShort(); break;
                case 9: case 10: case 11: case 12: dis.readInt(); break;
                default: return;
            }
        }
    }

    static void checkText(String name, String s) {
        String lower = s.toLowerCase();
        if (lower.contains("nsotien") || lower.contains("tien") || lower.contains("http") || lower.contains(".com")) {
            System.out.println("Text file match in " + name + ":\n" + s);
        }
    }

    static void checkString(String className, int cpIdx, String str) {
        String lower = str.toLowerCase();
        if (lower.contains("nsotien") ||
            lower.contains("tien") && (lower.contains("nso") || lower.contains("mod") || lower.contains(".com") || lower.contains("by")) ||
            lower.contains("http://") || lower.contains("https://") ||
            lower.contains(".com") || lower.contains(".net") || lower.contains(".vn") ||
            str.contains("AngelChip") || str.contains("GameHub") || str.contains("WapVip")) {
            System.out.println(className + " [#" + cpIdx + "]: " + str);
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
