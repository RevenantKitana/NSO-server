import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SearchClient {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("scratch/client");
        Files.list(dir).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
            try {
                byte[] bytes = Files.readAllBytes(p);
                String content = new String(bytes, "UTF-8");
                String latin = new String(bytes, "ISO-8859-1");
                
                checkKeywords(p.getFileName().toString(), bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void checkKeywords(String className, byte[] bytes) {
        // Search UTF-8 strings from constant pool
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            int magic = dis.readInt();
            if (magic != 0xCAFEBABE) return;
            dis.readUnsignedShort(); // minor
            dis.readUnsignedShort(); // major
            int cpCount = dis.readUnsignedShort();
            List<String> utf8Strings = new ArrayList<>();
            for (int i = 1; i < cpCount; i++) {
                int tag = dis.readUnsignedByte();
                switch (tag) {
                    case 1: // Utf8
                        String s = dis.readUTF();
                        utf8Strings.add(s);
                        break;
                    case 3: // Integer
                    case 4: // Float
                        dis.readInt();
                        break;
                    case 5: // Long
                    case 6: // Double
                        dis.readLong();
                        i++; // takes two entries
                        break;
                    case 7: // Class
                    case 8: // String
                        dis.readUnsignedShort();
                        break;
                    case 9: // Fieldref
                    case 10: // Methodref
                    case 11: // InterfaceMethodref
                    case 12: // NameAndType
                        dis.readInt();
                        break;
                    default:
                        return;
                }
            }
            for (String s : utf8Strings) {
                String lower = s.toLowerCase();
                if (lower.contains("hỗ trợ") || lower.contains("ho tro") || lower.contains("buff") || lower.contains("tự động") || lower.contains("tu dong") || lower.contains("chiêu")) {
                    System.out.println(className + " -> " + s);
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
