import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public class PatchClient {

    public static void main(String[] args) throws Exception {
        File originalJar = new File("e:/TT/SETUP_LOCAL/SRC_GAME/.client/JAR_local.jar");
        File patchedJar = new File("e:/TT/SETUP_LOCAL/SRC_GAME/.client/NSO_161.118.202.174.jar");
        File backupJar = new File("e:/TT/SETUP_LOCAL/SRC_GAME/.client/JAR_local.jar.bak");

        if (!backupJar.exists()) {
            copyFile(originalJar, backupJar);
            System.out.println("Created backup: " + backupJar.getAbsolutePath());
        }

        Map<String, String> replacements = new LinkedHashMap<>();
        replacements.put("socket://127.0.0.1:14444", "socket://161.118.202.174:14444");
        replacements.put("127.0.0.1", "161.118.202.174");
        replacements.put("sv.nsoblue.com", "161.118.202.174");
        replacements.put("222.255.214.211", "161.118.202.174");

        patchJar(originalJar, patchedJar, replacements);
        System.out.println("SUCCESS: Patched client saved to: " + patchedJar.getAbsolutePath());

        // Also update JAR_local.jar in place
        copyFile(patchedJar, originalJar);
        System.out.println("SUCCESS: Updated JAR_local.jar in place.");
    }

    private static void copyFile(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
        }
    }

    public static void patchJar(File inJar, File outJar, Map<String, String> replacements) throws Exception {
        try (ZipFile zipIn = new ZipFile(inJar);
             JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(outJar))) {

            jarOut.setLevel(Deflater.DEFAULT_COMPRESSION);
            Enumeration<? extends ZipEntry> entries = zipIn.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                byte[] data;
                try (InputStream is = zipIn.getInputStream(entry)) {
                    data = readAllBytes(is);
                }

                if (name.endsWith(".class")) {
                    byte[] patched = patchClassBytecode(data, name, replacements);
                    if (patched != data) {
                        System.out.println("Patched class: " + name);
                    }
                    data = patched;
                }

                // Create clean ZipEntry without Zip64 or extra corrupt fields
                ZipEntry newEntry = new ZipEntry(name);
                newEntry.setTime(entry.getTime());
                jarOut.putNextEntry(newEntry);
                jarOut.write(data);
                jarOut.closeEntry();
            }
        }
    }

    private static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = is.read(buf)) != -1) {
            baos.write(buf, 0, n);
        }
        return baos.toByteArray();
    }

    public static byte[] patchClassBytecode(byte[] classBytes, String className, Map<String, String> replacements) throws Exception {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(classBytes));
        int magic = dis.readInt();
        if (magic != 0xCAFEBABE) {
            return classBytes; // not a class file
        }

        int minor = dis.readUnsignedShort();
        int major = dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();

        ByteArrayOutputStream cpBaos = new ByteArrayOutputStream();
        DataOutputStream cpDos = new DataOutputStream(cpBaos);

        boolean modified = false;

        // Write magic and version
        ByteArrayOutputStream finalBaos = new ByteArrayOutputStream();
        DataOutputStream finalDos = new DataOutputStream(finalBaos);
        finalDos.writeInt(magic);
        finalDos.writeShort(minor);
        finalDos.writeShort(major);
        finalDos.writeShort(cpCount);

        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            cpDos.writeByte(tag);

            switch (tag) {
                case 1: { // CONSTANT_Utf8
                    int len = dis.readUnsignedShort();
                    byte[] strBytes = new byte[len];
                    dis.readFully(strBytes);
                    String str = new String(strBytes, StandardCharsets.UTF_8);

                    if (replacements.containsKey(str)) {
                        String newStr = replacements.get(str);
                        byte[] newBytes = newStr.getBytes(StandardCharsets.UTF_8);
                        cpDos.writeShort(newBytes.length);
                        cpDos.write(newBytes);
                        modified = true;
                        System.out.println("  [" + className + "] Replaced: '" + str + "' -> '" + newStr + "'");
                    } else {
                        cpDos.writeShort(len);
                        cpDos.write(strBytes);
                    }
                    break;
                }
                case 3: // CONSTANT_Integer
                case 4: { // CONSTANT_Float
                    cpDos.writeInt(dis.readInt());
                    break;
                }
                case 5: // CONSTANT_Long
                case 6: { // CONSTANT_Double
                    cpDos.writeLong(dis.readLong());
                    i++; // Takes 2 entries in Constant Pool
                    break;
                }
                case 7: // CONSTANT_Class
                case 8: { // CONSTANT_String
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 9:  // CONSTANT_Fieldref
                case 10: // CONSTANT_Methodref
                case 11: // CONSTANT_InterfaceMethodref
                case 12: { // CONSTANT_NameAndType
                    cpDos.writeShort(dis.readUnsignedShort());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 15: { // CONSTANT_MethodHandle
                    cpDos.writeByte(dis.readUnsignedByte());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 16: { // CONSTANT_MethodType
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 17: // CONSTANT_Dynamic
                case 18: { // CONSTANT_InvokeDynamic
                    cpDos.writeShort(dis.readUnsignedShort());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 19: // CONSTANT_Module
                case 20: { // CONSTANT_Package
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                default:
                    throw new IllegalStateException("Unknown CP tag: " + tag + " in class " + className);
            }
        }

        if (!modified) {
            return classBytes;
        }

        // Write new constant pool
        finalDos.write(cpBaos.toByteArray());

        // Copy remaining class file attributes (interfaces, fields, methods, attributes)
        byte[] remainder = readAllBytes(dis);
        finalDos.write(remainder);

        return finalBaos.toByteArray();
    }
}
