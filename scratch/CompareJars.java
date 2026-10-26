import java.io.*;
import java.util.*;
import java.util.zip.*;

public class CompareJars {
    public static void main(String[] args) throws Exception {
        ZipFile z1 = new ZipFile(".client/NSO.jar.sp_bak");
        ZipFile z2 = new ZipFile(".client/NSO.jar");
        Enumeration<? extends ZipEntry> e = z2.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            ZipEntry e1 = z1.getEntry(entry.getName());
            if (e1 == null) {
                System.out.println("Added: " + entry.getName());
            } else if (entry.getCrc() != e1.getCrc()) {
                System.out.println("Modified: " + entry.getName() + " (" + e1.getSize() + " -> " + entry.getSize() + ")");
            }
        }
        z1.close();
        z2.close();
    }
}
