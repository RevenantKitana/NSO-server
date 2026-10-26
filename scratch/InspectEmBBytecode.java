import java.io.*;
import java.util.*;

public class InspectEmBBytecode {
    public static void main(String[] args) throws Exception {
        byte[] b = readStream(new FileInputStream("scratch/curr/em.class"));
        // Find method b(Lbh;)V
        // In Class file:
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        int magic = dis.readInt();
        int minor = dis.readUnsignedShort();
        int major = dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();
        Object[] cp = new Object[cpCount];
        int[] cpTag = new int[cpCount];
        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            cpTag[i] = tag;
            switch (tag) {
                case 1: cp[i] = dis.readUTF(); break;
                case 3: case 4: dis.readInt(); break;
                case 5: case 6: dis.readLong(); i++; break;
                case 7: case 8: cp[i] = dis.readUnsignedShort(); break;
                case 9: case 10: case 11: case 12: cp[i] = (dis.readUnsignedShort() << 16) | dis.readUnsignedShort(); break;
            }
        }
        int accessFlags = dis.readUnsignedShort();
        int thisClass = dis.readUnsignedShort();
        int superClass = dis.readUnsignedShort();
        int interfacesCount = dis.readUnsignedShort();
        for (int i = 0; i < interfacesCount; i++) dis.readUnsignedShort();
        int fieldsCount = dis.readUnsignedShort();
        for (int i = 0; i < fieldsCount; i++) {
            dis.readUnsignedShort(); // access
            dis.readUnsignedShort(); // name
            dis.readUnsignedShort(); // desc
            int ac = dis.readUnsignedShort();
            for (int j = 0; j < ac; j++) {
                dis.readUnsignedShort(); // attr name
                int len = dis.readInt();
                dis.skipBytes(len);
            }
        }
        int methodsCount = dis.readUnsignedShort();
        for (int i = 0; i < methodsCount; i++) {
            int mAccess = dis.readUnsignedShort();
            int mNameIdx = dis.readUnsignedShort();
            int mDescIdx = dis.readUnsignedShort();
            String mName = (String) cp[mNameIdx];
            String mDesc = (String) cp[mDescIdx];
            int ac = dis.readUnsignedShort();
            for (int j = 0; j < ac; j++) {
                int attrNameIdx = dis.readUnsignedShort();
                int attrLen = dis.readInt();
                String attrName = (String) cp[attrNameIdx];
                if ("Code".equals(attrName)) {
                    int maxStack = dis.readUnsignedShort();
                    int maxLocals = dis.readUnsignedShort();
                    int codeLen = dis.readInt();
                    byte[] code = new byte[codeLen];
                    dis.readFully(code);
                    if ("b".equals(mName) && "(Lbh;)V".equals(mDesc)) {
                        System.out.println("Found em.b(bh) Code: length = " + codeLen + ", maxStack = " + maxStack + ", maxLocals = " + maxLocals);
                        // Find this code in original byte array b
                        int codeOffset = findSubarray(b, code);
                        System.out.println("Code offset in em.class: 0x" + Integer.toHexString(codeOffset) + " (" + codeOffset + ")");
                        // Print first 40 bytes
                        for (int k = 0; k < 40; k++) {
                            System.out.printf("%02X ", code[k]);
                        }
                        System.out.println();
                    }
                    int exTableLen = dis.readUnsignedShort();
                    dis.skipBytes(exTableLen * 8);
                    int codeAttrCount = dis.readUnsignedShort();
                    for (int k = 0; k < codeAttrCount; k++) {
                        dis.readUnsignedShort();
                        int cal = dis.readInt();
                        dis.skipBytes(cal);
                    }
                } else {
                    dis.skipBytes(attrLen);
                }
            }
        }
    }

    static int findSubarray(byte[] outer, byte[] inner) {
        outerLoop:
        for (int i = 0; i <= outer.length - inner.length; i++) {
            for (int j = 0; j < inner.length; j++) {
                if (outer[i + j] != inner[j]) continue outerLoop;
            }
            return i;
        }
        return -1;
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
