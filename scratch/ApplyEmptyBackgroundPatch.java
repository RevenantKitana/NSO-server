import java.io.*;
import java.util.*;
import java.util.zip.*;

public class ApplyEmptyBackgroundPatch {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        File tempJar = new File(".client/NSO.jar.tmp");

        // The 28 replacement bytes at offset 31860 in em.class:
        // 2A 03 B2 01 06 9A 00 07 57 B2 00 EF B6 01 4E 2A 03 03 B2 00 89 B2 00 80 B6 01 56 B1
        byte[] patch = new byte[]{
            (byte) 0x2A,                         // 2561: aload_0
            (byte) 0x03,                         // 2562: iconst_0
            (byte) 0xB2, (byte) 0x01, (byte) 0x06, // 2563: getstatic Field em.e:Z
            (byte) 0x9A, (byte) 0x00, (byte) 0x07, // 2566: ifne +7 -> 2573
            (byte) 0x57,                         // 2569: pop
            (byte) 0xB2, (byte) 0x00, (byte) 0xEF, // 2570: getstatic Field bn:I
            (byte) 0xB6, (byte) 0x01, (byte) 0x4E, // 2573: invokevirtual bh.a:(I)V
            (byte) 0x2A,                         // 2576: aload_0
            (byte) 0x03,                         // 2577: iconst_0
            (byte) 0x03,                         // 2578: iconst_0
            (byte) 0xB2, (byte) 0x00, (byte) 0x89, // 2579: getstatic Field ap.fq:I
            (byte) 0xB2, (byte) 0x00, (byte) 0x80, // 2582: getstatic Field ap.bv:I
            (byte) 0xB6, (byte) 0x01, (byte) 0x56, // 2585: invokevirtual bh.e:(IIII)V
            (byte) 0xB1                          // 2588: return
        };

        if (patch.length != 28) {
            throw new RuntimeException("Patch length must be 28, got " + patch.length);
        }

        byte[] expectedOrig = new byte[]{
            (byte) 0x2A, (byte) 0xB2, (byte) 0x00, (byte) 0xEF, (byte) 0xB6, (byte) 0x01, (byte) 0x4E,
            (byte) 0x2A, (byte) 0xB2, (byte) 0x00, (byte) 0xF4, (byte) 0x05, (byte) 0x2E,
            (byte) 0xB2, (byte) 0x00, (byte) 0xF4, (byte) 0x05, (byte) 0x2E,
            (byte) 0xB2, (byte) 0x00, (byte) 0x89, (byte) 0xB2, (byte) 0x00, (byte) 0x80,
            (byte) 0xB6, (byte) 0x01, (byte) 0x56, (byte) 0xB1
        };

        ZipFile inZip = new ZipFile(jarFile);
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(tempJar));

        Enumeration<? extends ZipEntry> entries = inZip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            byte[] data = readStream(inZip.getInputStream(entry));

            if ("em.class".equals(entry.getName())) {
                System.out.println("Patching em.class (length " + data.length + ")...");
                int offset = 31860; // 0x7C74
                // Verify expected bytes
                boolean match = true;
                for (int i = 0; i < 28; i++) {
                    if (data[offset + i] != expectedOrig[i]) {
                        match = false;
                        System.out.printf("Mismatch at %d: expected 0x%02X, got 0x%02X\n", i, expectedOrig[i], data[offset + i]);
                    }
                }
                if (!match) {
                    throw new RuntimeException("Original bytes do not match expected!");
                }

                // Apply patch
                System.arraycopy(patch, 0, data, offset, 28);
                System.out.println("Applied 28-byte patch at offset " + offset);
            }

            ZipEntry newEntry = new ZipEntry(entry.getName());
            outZip.putNextEntry(newEntry);
            outZip.write(data);
            outZip.closeEntry();
        }

        inZip.close();
        outZip.close();
        System.out.println("Created temp jar: " + tempJar.getAbsolutePath());
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
