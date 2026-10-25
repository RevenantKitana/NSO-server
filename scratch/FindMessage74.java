import java.io.*;
import java.nio.file.*;

public class FindMessage74 {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("scratch/client");
        // Look for new Message((byte)74) -> new (class with "Message" or whatever) or method call with 74
        // In client bytecode:
        // bipush 74, invokestatic or invokespecial (Message constructor or service method)
        Files.list(dir).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
            try {
                byte[] b = Files.readAllBytes(p);
                for (int i = 0; i < b.length - 4; i++) {
                    if (b[i] == 0x10 && b[i+1] == 74) { // bipush 74
                        // check what follows within 5 bytes: invokespecial (0xb7) or invokestatic (0xb8) or invokevirtual (0xb6)
                        for (int j = i + 2; j <= i + 6 && j < b.length; j++) {
                            int op = b[j] & 0xFF;
                            if (op == 0xb7 || op == 0xb8 || op == 0xb6) {
                                System.out.println(p.getFileName() + " invokes method at " + j + " after bipush 74 at " + i);
                            }
                        }
                    }
                }
            } catch (Exception e) {}
        });
    }
}
