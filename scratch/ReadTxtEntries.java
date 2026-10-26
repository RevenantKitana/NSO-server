import java.io.*;
import java.util.zip.*;

public class ReadTxtEntries {
    public static void main(String[] args) throws Exception {
        ZipFile z = new ZipFile(".client/NSO.jar");
        String[] txts = {"text.txt", "hd.txt", "provider.txt", "agent.txt"};
        for (String t : txts) {
            ZipEntry e = z.getEntry(t);
            if (e != null) {
                System.out.println("=== " + t + " ===");
                byte[] b = readStream(z.getInputStream(e));
                System.out.println(new String(b, "UTF-8"));
            }
        }
        z.close();
    }

    static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) baos.write(buf, 0, n);
        return baos.toByteArray();
    }
}
