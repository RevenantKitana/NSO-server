import java.io.*;

public class InspectBytes2561 {
    public static void main(String[] args) throws Exception {
        byte[] b = readStream(new FileInputStream("scratch/curr/em.class"));
        // We know code offset in em.class is 29299 (0x7273)
        int codeStart = 29299;
        int target = codeStart + 2561;
        System.out.println("Bytes from 2561 (file offset " + target + " / 0x" + Integer.toHexString(target) + "):");
        for (int i = 0; i < 28; i++) {
            System.out.printf("%02X ", b[target + i]);
        }
        System.out.println();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
