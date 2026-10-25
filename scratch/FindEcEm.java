import java.io.*;
import java.nio.file.*;

public class FindEcEm {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("scratch/client");
        Files.list(dir).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
            try {
                byte[] b = Files.readAllBytes(p);
                String s = new String(b, "ISO-8859-1");
                if (s.contains("em") && s.contains("Lad;")) {
                    System.out.println(p.getFileName() + " accesses em:Lad;");
                }
            } catch (Exception e) {}
        });
    }
}
