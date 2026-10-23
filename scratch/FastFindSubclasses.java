import java.io.*;
import java.util.jar.*;

public class FastFindSubclasses {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        var entries = jar.entries();
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
                Object[] cp = new Object[cpCount];
                int[] classRef = new int[cpCount];
                for (int i = 1; i < cpCount; i++) {
                    int tag = dis.readUnsignedByte();
                    switch (tag) {
                        case 7: // Class
                            classRef[i] = dis.readUnsignedShort();
                            break;
                        case 9: case 10: case 11: // Ref
                            dis.skipBytes(4);
                            break;
                        case 8: // String
                            dis.skipBytes(2);
                            break;
                        case 3: case 4: // Integer, Float
                            dis.skipBytes(4);
                            break;
                        case 5: case 6: // Long, Double
                            dis.skipBytes(8);
                            i++;
                            break;
                        case 12: // NameAndType
                            dis.skipBytes(4);
                            break;
                        case 1: // Utf8
                            cp[i] = dis.readUTF();
                            break;
                        case 15: // MethodHandle
                            dis.skipBytes(3);
                            break;
                        case 16: // MethodType
                            dis.skipBytes(2);
                            break;
                        case 18: // InvokeDynamic
                            dis.skipBytes(4);
                            break;
                        default:
                            break;
                    }
                }
                int access = dis.readUnsignedShort();
                int thisClass = dis.readUnsignedShort();
                int superClass = dis.readUnsignedShort();
                String superName = (superClass > 0 && classRef[superClass] > 0) ? (String)cp[classRef[superClass]] : "";
                String thisName = (thisClass > 0 && classRef[thisClass] > 0) ? (String)cp[classRef[thisClass]] : "";
                if ("as".equals(superName)) {
                    System.out.println("Subclass of as: " + thisName);
                }
                dis.close();
            }
        }
        jar.close();
    }
}
