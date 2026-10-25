import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FindEmInEc {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("scratch/decompiled/ec.java"));
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l.contains("this.em") || l.contains(".em")) {
                System.out.println((i+1) + ": " + l.trim());
            }
        }
    }
}
