import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.*;
import java.util.regex.*;
import java.util.zip.*;

public class PatchClient {

    private static final Set<String> KNOWN_HOSTS = new HashSet<>(Arrays.asList(
            "127.0.0.1",
            "localhost",
            "161.118.202.174",
            "222.255.214.211",
            "sv.nsoblue.com",
            "nsoblue.com"
    ));

    private static final Pattern IPV4_PATTERN = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
    private static final Pattern SOCKET_PATTERN = Pattern.compile("^socket://([^:/]+)(?::(\\d+))?(.*)$");

    public static void main(String[] args) {
        try {
            String targetHost = "161.118.202.174";
            String targetPort = "14444";
            File baseDir = new File(PatchClient.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
            if (baseDir == null || !baseDir.exists()) {
                baseDir = new File(".");
            }

            if (args.length >= 1 && !args[0].trim().isEmpty()) {
                targetHost = args[0].trim();
            }
            if (args.length >= 2 && !args[1].trim().isEmpty()) {
                targetPort = args[1].trim();
            }

            System.out.println("============================================================");
            System.out.println("   NSO CLIENT JAR PATCHER - NSO AUTO IP & PORT CONFIG");
            System.out.println("============================================================");
            System.out.println(" Target IP/Host: " + targetHost);
            System.out.println(" Target Port   : " + targetPort);
            System.out.println(" Working Dir   : " + baseDir.getCanonicalPath());
            System.out.println("------------------------------------------------------------");

            // Look for jars to patch (Standardized to NSO.jar)
            List<File> targetJars = new ArrayList<>();
            if (args.length >= 3) {
                targetJars.add(new File(args[2]));
            } else {
                File nsoJar = new File(baseDir, "NSO.jar");
                if (nsoJar.exists()) {
                    targetJars.add(nsoJar);
                } else {
                    File jarLocal = new File(baseDir, "JAR_local.jar");
                    if (jarLocal.exists()) targetJars.add(jarLocal);
                }
                
                // If baseDir didn't have them, check .client/ subfolder or parent
                if (targetJars.isEmpty()) {
                    File clientSubdir = new File(baseDir, ".client");
                    if (clientSubdir.exists()) {
                        File j2 = new File(clientSubdir, "NSO.jar");
                        if (j2.exists()) {
                            targetJars.add(j2);
                        } else {
                            File j1 = new File(clientSubdir, "JAR_local.jar");
                            if (j1.exists()) targetJars.add(j1);
                        }
                    }
                }
            }

            if (targetJars.isEmpty()) {
                System.err.println("ERROR: No client JAR file found to patch (NSO.jar).");
                System.exit(1);
            }

            for (File jarFile : targetJars) {
                System.out.println("\n[*] Processing: " + jarFile.getName() + " (" + jarFile.length() + " bytes)");

                // 1. Ensure backup exists
                File backupFile = new File(jarFile.getParentFile(), jarFile.getName() + ".bak");
                if (!backupFile.exists()) {
                    copyFile(jarFile, backupFile);
                    System.out.println("    [+] Created backup: " + backupFile.getName());
                } else {
                    System.out.println("    [*] Backup already exists: " + backupFile.getName());
                }

                // 2. Perform patching into temp file
                File tempPatched = new File(jarFile.getParentFile(), jarFile.getName() + ".tmp");
                int replaceCount = patchJar(jarFile, tempPatched, targetHost, targetPort);

                // 3. Replace original file
                if (replaceCount > 0) {
                    if (jarFile.delete()) {
                        if (tempPatched.renameTo(jarFile)) {
                            System.out.println("    [OK] Successfully updated: " + jarFile.getName() + " (" + replaceCount + " replacements)");
                        } else {
                            copyFile(tempPatched, jarFile);
                            tempPatched.delete();
                            System.out.println("    [OK] Successfully copied to: " + jarFile.getName());
                        }
                    } else {
                        copyFile(tempPatched, jarFile);
                        tempPatched.delete();
                        System.out.println("    [OK] Successfully overwritten: " + jarFile.getName());
                    }
                } else {
                    tempPatched.delete();
                    System.out.println("    [!] No replacement needed or no matching host strings found.");
                }
            }

            System.out.println("\n============================================================");
            System.out.println(" [SUCCESS] Client JAR configuration completed!");
            System.out.println(" IP: " + targetHost + " | Port: " + targetPort);
            System.out.println("============================================================\n");

        } catch (Exception e) {
            System.err.println("ERROR: Failed to patch client JAR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
        }
    }

    public static int patchJar(File inJar, File outJar, String targetHost, String targetPort) throws Exception {
        int totalReplaced = 0;
        try (ZipFile zipIn = new ZipFile(inJar);
             JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(outJar))) {

            jarOut.setLevel(Deflater.DEFAULT_COMPRESSION);
            Enumeration<? extends ZipEntry> entries = zipIn.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                byte[] data;
                try (InputStream is = zipIn.getInputStream(entry)) {
                    data = readAllBytes(is);
                }

                if (name.endsWith(".class")) {
                    PatchResult res = patchClassBytecode(data, name, targetHost, targetPort);
                    if (res.modified) {
                        data = res.bytecode;
                        totalReplaced += res.replacementCount;
                    }
                }

                // Clean ZipEntry
                ZipEntry newEntry = new ZipEntry(name);
                newEntry.setTime(entry.getTime());
                jarOut.putNextEntry(newEntry);
                jarOut.write(data);
                jarOut.closeEntry();
            }
        }
        return totalReplaced;
    }

    private static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = is.read(buf)) != -1) {
            baos.write(buf, 0, n);
        }
        return baos.toByteArray();
    }

    private static class PatchResult {
        boolean modified;
        int replacementCount;
        byte[] bytecode;
    }

    public static PatchResult patchClassBytecode(byte[] classBytes, String className, String targetHost, String targetPort) throws Exception {
        PatchResult result = new PatchResult();
        result.bytecode = classBytes;
        result.modified = false;
        result.replacementCount = 0;

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(classBytes));
        int magic = dis.readInt();
        if (magic != 0xCAFEBABE) {
            return result; // not a valid class file
        }

        int minor = dis.readUnsignedShort();
        int major = dis.readUnsignedShort();
        int cpCount = dis.readUnsignedShort();

        ByteArrayOutputStream cpBaos = new ByteArrayOutputStream();
        DataOutputStream cpDos = new DataOutputStream(cpBaos);

        for (int i = 1; i < cpCount; i++) {
            int tag = dis.readUnsignedByte();
            cpDos.writeByte(tag);

            switch (tag) {
                case 1: { // CONSTANT_Utf8
                    int len = dis.readUnsignedShort();
                    byte[] strBytes = new byte[len];
                    dis.readFully(strBytes);
                    String str = new String(strBytes, StandardCharsets.UTF_8);

                    String replaced = computeReplacement(str, targetHost, targetPort);

                    if (replaced != null && !replaced.equals(str)) {
                        byte[] newBytes = replaced.getBytes(StandardCharsets.UTF_8);
                        cpDos.writeShort(newBytes.length);
                        cpDos.write(newBytes);
                        result.modified = true;
                        result.replacementCount++;
                        System.out.println("      [" + className + "] '" + str + "' -> '" + replaced + "'");
                    } else {
                        cpDos.writeShort(len);
                        cpDos.write(strBytes);
                    }
                    break;
                }
                case 3: // CONSTANT_Integer
                case 4: { // CONSTANT_Float
                    cpDos.writeInt(dis.readInt());
                    break;
                }
                case 5: // CONSTANT_Long
                case 6: { // CONSTANT_Double
                    cpDos.writeLong(dis.readLong());
                    i++; // Takes 2 entries in Constant Pool
                    break;
                }
                case 7: // CONSTANT_Class
                case 8: { // CONSTANT_String
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 9:  // CONSTANT_Fieldref
                case 10: // CONSTANT_Methodref
                case 11: // CONSTANT_InterfaceMethodref
                case 12: { // CONSTANT_NameAndType
                    cpDos.writeShort(dis.readUnsignedShort());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 15: { // CONSTANT_MethodHandle
                    cpDos.writeByte(dis.readUnsignedByte());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 16: { // CONSTANT_MethodType
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 17: // CONSTANT_Dynamic
                case 18: { // CONSTANT_InvokeDynamic
                    cpDos.writeShort(dis.readUnsignedShort());
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                case 19: // CONSTANT_Module
                case 20: { // CONSTANT_Package
                    cpDos.writeShort(dis.readUnsignedShort());
                    break;
                }
                default:
                    throw new IllegalStateException("Unknown CP tag: " + tag + " in class " + className);
            }
        }

        if (!result.modified) {
            return result;
        }

        // Reconstruct class bytecode
        ByteArrayOutputStream finalBaos = new ByteArrayOutputStream();
        DataOutputStream finalDos = new DataOutputStream(finalBaos);
        finalDos.writeInt(magic);
        finalDos.writeShort(minor);
        finalDos.writeShort(major);
        finalDos.writeShort(cpCount);
        finalDos.write(cpBaos.toByteArray());

        // Copy remaining class file bytes (interfaces, fields, methods, attributes)
        byte[] remainder = readAllBytes(dis);
        finalDos.write(remainder);

        result.bytecode = finalBaos.toByteArray();
        return result;
    }

    private static String computeReplacement(String str, String targetHost, String targetPort) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        // 1. Socket URL matching: socket://<host>:<port> or socket://<host>
        Matcher socketMatcher = SOCKET_PATTERN.matcher(str);
        if (socketMatcher.matches()) {
            String suffix = socketMatcher.group(3);
            if (suffix == null) suffix = "";
            return "socket://" + targetHost + ":" + targetPort + suffix;
        }

        // 2. Exact match in known hosts
        if (KNOWN_HOSTS.contains(str)) {
            return targetHost;
        }

        // 3. IPv4 address format (avoid matching version numbers like 1.0.0.0 by ensuring standard IP patterns)
        if (IPV4_PATTERN.matcher(str).matches()) {
            // Check if it's not a common version string
            if (!str.equals("0.0.0.0") && !str.equals("255.255.255.255")) {
                return targetHost;
            }
        }

        return null;
    }
}
