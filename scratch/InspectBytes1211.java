import java.io.*;

public class InspectBytes1211 {
    public static void main(String[] args) throws Exception {
        byte[] b = readStream(new FileInputStream("scratch/curr/em.class"));
        // Earlier diff showed offset 0x6820 (26656)
        int target = 26656;
        System.out.println("Bytes around 26656 (0x6820):");
        for (int i = -10; i < 30; i++) {
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
