import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FindEmEPrecise {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> entries = z.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            if (e.getName().endsWith(".class")) {
                byte[] b = readStream(z.getInputStream(e));
                checkClass(e.getName(), b);
            }
        }
        z.close();
    }

    static void checkClass(String name, byte[] b) throws Exception {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        int magic = dis.readInt();
        if (magic != 0xCAFEBABE) return;
        dis.readUnsignedShort(); // minor
        dis.readUnsignedShort(); // major
        int cpCount = dis.readUnsignedShort();
        Object[] cp = new Object[cpCount];
        int[] cpTag = new int[cpCount];
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            cpTag[i] = tag;
            switch (tag) {
                case 1: // Utf8
                    cp[i] = dis.readUTF();
                    break;
                case 3: // Integer
                case 4: // Float
                    dis.readInt();
                    break;
                case 5: // Long
                case 6: // Double
                    dis.readLong();
                    i++;
                    break;
                case 7: // Class
                case 8: // String
                    cp[i] = dis.readUnsignedShort();
                    break;
                case 9: // Fieldref
                case 10: // Methodref
                case 11: // InterfaceMethodref
                case 12: // NameAndType
                    cp[i] = (dis.readUnsignedShort() << 16) | dis.readUnsignedShort();
                    break;
                default:
                    return;
            }
        }

        // Now find if any Fieldref references Class "em" and NameAndType "e":"Z"
        for (int i = 1; i < cpCount; i++) {
            if (cpTag[i] == 9) { // Fieldref
                int val = (Integer) cp[i];
                int classIdx = (val >> 16) & 0xFFFF;
                int natIdx = val & 0xFFFF;
                int classNameIdx = (Integer) cp[classIdx];
                String className = (String) cp[classNameIdx];
                if ("em".equals(className)) {
                    int natVal = (Integer) cp[natIdx];
                    int nameIdx = (natVal >> 16) & 0xFFFF;
                    int descIdx = natVal & 0xFFFF;
                    String fieldName = (String) cp[nameIdx];
                    String fieldDesc = (String) cp[descIdx];
                    if ("e".equals(fieldName) && "Z".equals(fieldDesc)) {
                        System.out.println("Class " + name + " references em.e:Z at CP #" + i);
                    }
                }
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
