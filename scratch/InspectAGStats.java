import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectAGStats {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        JarEntry entry = jar.getJarEntry("ag.class");
        InputStream is = jar.getInputStream(entry);
        DataInputStream dis = new DataInputStream(is);
        dis.readInt(); dis.readUnsignedShort(); dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            if (tag == 1) {
                String val = dis.readUTF();
                // print if related to stats
                if (val.contains("công") || val.contains("mạng") || val.contains("khắc") || val.contains("chống") || val.contains("né") || val.contains("sát") || val.contains("giảm")) {
                    System.out.println("Stat label: " + val);
                }
            } else if (tag == 3 || tag == 4) {
                dis.readInt();
            } else if (tag == 5 || tag == 6) {
                dis.readLong(); i++;
            } else if (tag == 7 || tag == 8) {
                dis.readUnsignedShort();
            } else if (tag == 9 || tag == 10 || tag == 11 || tag == 12) {
                dis.readInt();
            } else if (tag == 15) {
                dis.readByte(); dis.readShort();
            } else if (tag == 16) {
                dis.readShort();
            } else if (tag == 18) {
                dis.readInt();
            }
        }
    }
}
