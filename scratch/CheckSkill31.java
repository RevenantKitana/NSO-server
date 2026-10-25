import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CheckSkill31 {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get("scratch/decompiled");
        Files.list(p).filter(f -> f.toString().endsWith(".java")).forEach(f -> {
            try {
                List<String> lines = Files.readAllLines(f);
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    // Check for ax.d[64] or references to 31 in context of skill or cv.k.d
                    if (line.contains("d[64]") || line.contains("b[16]") || line.contains("c[18]") || (line.contains(".d") && line.contains("31"))) {
                        System.out.println(f.getFileName() + ":" + (i+1) + " -> " + line.trim());
                    }
                }
            } catch (Exception e) {}
        });
    }
}
