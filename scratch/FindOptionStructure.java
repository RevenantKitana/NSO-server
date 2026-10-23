import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindOptionStructure {
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
                dis.readUnsignedShort(); dis.readUnsignedShort();
                int cpCount = dis.readUnsignedShort();
                
                String[] utf8 = new String[cpCount];
                for (int i = 1; i < cpCount; i++) {
                    int tag = dis.readUnsignedByte();
                    if (tag == 1) {
                        utf8[i] = dis.readUTF();
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
                
                int accessFlags = dis.readUnsignedShort();
                int thisClass = dis.readUnsignedShort();
                int superClass = dis.readUnsignedShort();
                int intfCount = dis.readUnsignedShort();
                for (int i = 0; i < intfCount; i++) dis.readUnsignedShort();
                
                int fieldCount = dis.readUnsignedShort();
                List<String> fieldDescs = new ArrayList<>();
                for (int i = 0; i < fieldCount; i++) {
                    int fFlags = dis.readUnsignedShort();
                    int nameIdx = dis.readUnsignedShort();
                    int descIdx = dis.readUnsignedShort();
                    fieldDescs.add(utf8[descIdx]);
                    int attrCount = dis.readUnsignedShort();
                    for (int a = 0; a < attrCount; a++) {
                        dis.readUnsignedShort();
                        int len = dis.readInt();
                        dis.skipBytes(len);
                    }
                }
                
                // An ItemOptionTemplate usually has 3 fields: int id (or not), String name ("Ljava/lang/String;"), int/byte type ("B" or "I")
                if (fieldDescs.size() == 3 && fieldDescs.contains("Ljava/lang/String;") && (fieldDescs.contains("B") || fieldDescs.contains("I"))) {
                    System.out.println("Candidate OptionTemplate: " + entry.getName() + " fields: " + fieldDescs);
                }
            }
        }
    }
}
