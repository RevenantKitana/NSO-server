import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FindMaxFight {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("scratch/decompiled");
        Files.list(dir).filter(p -> p.toString().endsWith(".java")).forEach(p -> {
            try {
                List<String> lines = Files.readAllLines(p);
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.matches(".*\\b(cv|var[0-9]+_[0-9]+)\\.j\\b.*") || line.contains("maxFight")) {
                        System.out.println(p.getFileName() + ":" + (i+1) + " -> " + line.trim());
                    }
                }
            } catch (Exception e) {}
        });
    }
}
