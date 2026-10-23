import java.io.*;
import java.util.*;
import java.util.jar.*;

public class InspectItemOptionClient2 {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                DataInputStream dis = new DataInputStream(is);
                int magic = dis.readInt();
                if (magic != 0xCAFEBABE) continue;
                int minor = dis.readUnsignedShort();
                int major = dis.readUnsignedShort();
                int cpCount = dis.readUnsignedShort();
                
                List<String> utf8Strings = new ArrayList<>();
                for (int i = 1; i < cpCount; i++) {
                    int tag = dis.readUnsignedByte();
                    if (tag == 1) { // Utf8
                        String val = dis.readUTF();
                        utf8Strings.add(val);
                    } else if (tag == 3 || tag == 4) { // Integer, Float
                        dis.readInt();
                    } else if (tag == 5 || tag == 6) { // Long, Double
                        dis.readLong();
                        i++; // takes two entries
                    } else if (tag == 7 || tag == 8) { // Class, String
                        dis.readUnsignedShort();
                    } else if (tag == 9 || tag == 10 || tag == 11 || tag == 12) { // Fieldref, Methodref, etc.
                        dis.readInt();
                    } else if (tag == 15) { // MethodHandle
                        dis.readByte();
                        dis.readShort();
                    } else if (tag == 16) { // MethodType
                        dis.readShort();
                    } else if (tag == 18) { // InvokeDynamic
                        dis.readInt();
                    }
                }
                
                // Let's check if this class defines item option template (id, name, type)
                if (utf8Strings.contains("readUTF") && utf8Strings.contains("readByte")) {
                    for (String s : utf8Strings) {
                        if (s.contains("option") || s.contains("Option") || s.contains("itemOption")) {
                            System.out.println("Match in " + entry.getName() + " -> " + s);
                        }
                    }
                }
            }
        }
    }
}
