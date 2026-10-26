import java.io.*;

public class FindOffsetInClass {
    public static void main(String[] args) throws Exception {
        byte[] b = readStream(new FileInputStream("scratch/curr/ey.class"));
        int off1 = 20313;
        int off2 = 29798;
        printContext(b, off1, "ey offset 20313");
        printContext(b, off2, "ey offset 29798");

        byte[] bem = readStream(new FileInputStream("scratch/curr/em.class"));
        printContext(bem, 25054, "em offset 25054");
        printContext(bem, 26656, "em offset 26656");
    }

    static void printContext(byte[] b, int off, String desc) {
        System.out.println("=== " + desc + " ===");
        int start = Math.max(0, off - 15);
        int end = Math.min(b.length, off + 20);
        for (int i = start; i < end; i++) {
            if (i == off) System.out.print("[" + String.format("%02X", b[i]) + "] ");
            else System.out.print(String.format("%02X", b[i]) + " ");
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
