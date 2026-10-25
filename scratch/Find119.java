import java.io.*;
import java.nio.file.*;

public class Find119 {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("scratch/client");
        Files.list(dir).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
            try {
                byte[] b = Files.readAllBytes(p);
                for (int i = 0; i < b.length - 1; i++) {
                    if (b[i] == 0x10 && b[i+1] == 119) {
                        System.out.println(p.getFileName() + " has bipush 119 at " + i);
                    }
                }
            } catch (Exception e) {}
        });
    }
}
