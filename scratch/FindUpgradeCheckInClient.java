import java.io.*;
import java.util.*;
import java.util.jar.*;

public class FindUpgradeCheckInClient {
    public static void main(String[] args) throws Exception {
        File jarFile = new File(".client/NSO.jar");
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().endsWith(".class")) {
                InputStream is = jar.getInputStream(entry);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = is.read(buf)) > 0) baos.write(buf, 0, n);
                byte[] bytes = baos.toByteArray();
                
                // Let's search for a method that has:
                // if (type == 3 && upgrade < 4) or similar
                // Or switch(type) -> case 3: return 4; case 4: return 8; case 5: return 12; case 6: return 14; case 7: return 16;
                // Bytecode for:
                // tableswitch 3 to 7:
                // 3: 4
                // 4: 8
                // 5: 12
                // 6: 14
                // 7: 16
                for (int i = 0; i < bytes.length - 20; i++) {
                    // tableswitch opcode is 0xAA
                    if ((bytes[i] & 0xFF) == 0xAA) {
                        // Let's check if nearby bytes have 4, 8, 12, 14, 16
                        // Let's print candidate class
                        int pad = 3 - (i % 4);
                        int defaultOffset = i + 1 + pad;
                        if (defaultOffset + 12 < bytes.length) {
                            int low = ((bytes[defaultOffset+4]&0xFF)<<24) | ((bytes[defaultOffset+5]&0xFF)<<16) | ((bytes[defaultOffset+6]&0xFF)<<8) | (bytes[defaultOffset+7]&0xFF);
                            int high = ((bytes[defaultOffset+8]&0xFF)<<24) | ((bytes[defaultOffset+9]&0xFF)<<16) | ((bytes[defaultOffset+10]&0xFF)<<8) | (bytes[defaultOffset+11]&0xFF);
                            if (low == 3 && high == 7) {
                                System.out.println("FOUND EXACT SWITCH 3..7 (Mốc +4, +8, +12, +14, +16) IN: " + entry.getName());
                            }
                        }
                    }
                }
            }
        }
    }
}
