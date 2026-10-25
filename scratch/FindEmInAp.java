import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FindEmInAp {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("scratch/decompiled/ap.java"));
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains(".em.") || l.contains(".em =") || l.contains("ec.n().em")) {
                System.out.println((i+1) + ": " + l.trim());
            }
        }
    }
}
