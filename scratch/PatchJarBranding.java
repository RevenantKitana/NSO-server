import java.io.*;
import java.util.*;
import java.util.zip.*;

public class PatchJarBranding {
    public static void main(String[] args) throws Exception {
        File inJar = new File(".client/NSO.jar");
        File outJar = new File(".client/MioNso.jar");

        ZipFile inZip = new ZipFile(inJar);
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(outJar));

        Enumeration<? extends ZipEntry> entries = inZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            byte[] data = readStream(inZip.getInputStream(entry));

            if ("ag.class".equals(entry.getName())) {
                System.out.println("Patching ag.class...");
                data = patchAgClass(data, "NSOTIEN.COM", "MioNso");
            } else if ("text.txt".equals(entry.getName())) {
                System.out.println("Patching text.txt in jar...");
                String newText = "MioNso\n- Phiên bản Ninja School Online\n- Chúc các bạn chơi game vui vẻ!\n";
                data = newText.getBytes("UTF-8");
            } else if ("META-INF/MANIFEST.MF".equals(entry.getName())) {
                System.out.println("Patching MANIFEST.MF in jar...");
                String manifest = new String(data, "UTF-8");
                manifest = manifest.replace("MIDlet-1: nso148,/icon.png,fl", "MIDlet-1: MioNso,/icon.png,fl");
                manifest = manifest.replace("MIDlet-Vendor: NSOTIEN TV", "MIDlet-Vendor: MioNso");
                manifest = manifest.replace("MIDlet-Name: nso148", "MIDlet-Name: MioNso");
                manifest = manifest.replace("MIDlet-Version: 1.4.8", "MIDlet-Version: 1.0.0");
                data = manifest.getBytes("UTF-8");
            }

            ZipEntry newEntry = new ZipEntry(entry.getName());
            outZip.putNextEntry(newEntry);
            outZip.write(data);
            outZip.closeEntry();
        }

        inZip.close();
        outZip.close();
        System.out.println("Successfully generated: " + outJar.getAbsolutePath());
    }

    static byte[] patchAgClass(byte[] orig, String fromStr, String toStr) throws Exception {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(orig));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        int magic = dis.readInt();
        int minor = dis.readUnsignedShort();
        int major = dis.readUnsignedShort();
        dos.writeInt(magic);
        dos.writeShort(minor);
        dos.writeShort(major);

        int cpCount = dis.readUnsignedShort();
        dos.writeShort(cpCount);

        int patchedCount = 0;
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            dos.writeByte(tag);
            switch (tag) {
                case 1: // Utf8
                    String s = dis.readUTF();
                    if (s.equals(fromStr)) {
                        System.out.println("  Replaced CP #" + i + " '" + s + "' with '" + toStr + "'");
                        s = toStr;
                        patchedCount++;
                    }
                    dos.writeUTF(s);
                    break;
                case 3: // Integer
                case 4: // Float
                    dos.writeInt(dis.readInt());
                    break;
                case 5: // Long
                case 6: // Double
                    dos.writeLong(dis.readLong());
                    i++;
                    break;
                case 7: // Class
                case 8: // String
                    dos.writeShort(dis.readUnsignedShort());
                    break;
                case 9: // Fieldref
                case 10: // Methodref
                case 11: // InterfaceMethodref
                case 12: // NameAndType
                    dos.writeInt(dis.readInt());
                    break;
                default:
                    throw new RuntimeException("Unknown CP tag: " + tag);
            }
        }

        System.out.println("  Patched " + patchedCount + " entries in constant pool.");

        // Copy the remainder of the class file exactly as is
        byte[] rem = readStream(dis);
        dos.write(rem);
        dos.flush();

        return baos.toByteArray();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
