import java.io.*;
import java.util.jar.*;

public class DumpApStrings {
    public static void main(String[] args) throws Exception {
        JarFile jar = new JarFile(".client/NSO.jar");
        String[] classes = {"ey.class", "ag.class", "fa.class"};
        for (String cname : classes) {
            System.out.println("=== " + cname + " ===");
            JarEntry entry = jar.getJarEntry(cname);
            DataInputStream dis = new DataInputStream(jar.getInputStream(entry));
            int magic = dis.readInt();
            int minor = dis.readUnsignedShort();
            int major = dis.readUnsignedShort();
            int cpCount = dis.readUnsignedShort();
            for (int i = 1; i < cpCount; i++) {
                int tag = dis.readUnsignedByte();
                if (tag == 1) { // Utf8
                    String str = dis.readUTF();
                    if (str.length() > 0 && !str.startsWith("L") && !str.contains("/") && !str.contains("(")) {
                        if (str.length() > 3 || str.matches(".*[\\p{L}].*")) {
                            System.out.println("  " + str);
                        }
                    }
                } else if (tag == 7 || tag == 8 || tag == 16 || tag == 19 || tag == 20) {
                    dis.readUnsignedShort();
                } else if (tag == 3 || tag == 4 || tag == 9 || tag == 10 || tag == 11 || tag == 12 || tag == 18) {
                    dis.readInt();
                } else if (tag == 5 || tag == 6) {
                    dis.readLong();
                    i++;
                } else if (tag == 15) {
                    dis.readByte();
                    dis.readUnsignedShort();
                }
            }
        }
        jar.close();
    }
}
