import java.io.*;
import java.nio.file.*;

public class PrintAxD {
    public static void main(String[] args) throws Exception {
        byte[] b = Files.readAllBytes(Paths.get("scratch/client/dl.class"));
        // Find dl.b static array initialization
        // Let's look at dl.java decompiled by CFR!
        System.out.println("Reading decompiled dl.java");
    }
}
