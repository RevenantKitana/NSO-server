import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ListNonClassEntries {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> e = z.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            if (!entry.getName().endsWith(".class")) {
                System.out.printf("%s (%d bytes)\n", entry.getName(), entry.getSize());
            }
        }
        z.close();
    }
}
