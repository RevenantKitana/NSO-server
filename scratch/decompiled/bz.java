/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.util.Random;

public final class bz {
    private static Random a;
    private static final int[] b;

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    public static int a() {
        return b[0] + a.nextInt(b[1]);
    }

    public static void b() {
        a = new Random();
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    public static byte[] a(eh eh2) {
        try {
            byte[] byArray = new byte[eh2.a.readInt()];
            eh2.a.read(byArray);
            "".length();
            return byArray;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static boolean d(int n2) {
        return n2 >= 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    public static void a(String string) {
        try {
            fl.a.platformRequest(string);
            "".length();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() == 0) {
                return;
            }
        }
        finally {
            fl.a.notifyDestroyed();
        }
    }

    public static void c() {
        a = null;
    }

    private static void d() {
        b = new int[13];
        bz.b[0] = -(0x54 ^ 0x53);
        bz.b[1] = 0x95 ^ 0x9B;
        bz.b[2] = -" ".length();
        bz.b[3] = (111 + 61 - 99 + 75 ^ 50 + 40 - -11 + 60) & (0xDB ^ 0xC2 ^ (0x7D ^ 0x51) ^ -" ".length());
        bz.b[4] = 0x8D ^ 0xA0;
        bz.b[5] = " ".length();
        bz.b[6] = "   ".length();
        bz.b[7] = 0xFFFFFBFF & 0x15EFB;
        bz.b[8] = -(0xFFFFB36B & 0x6D96) & (0xFFFFFFFD & 0x17BFF);
        bz.b[9] = 162 + 160 - 315 + 184 ^ 22 + 83 - 3 + 29;
        bz.b[10] = 0x97 ^ 0x8F;
        bz.b[11] = 0xCC ^ 0xC5;
        bz.b[12] = 0xA1 ^ 0xC2 ^ (0x53 ^ 0x1E);
    }

    public static int a(int n2) {
        return a.nextInt(n2);
    }

    public static String b(String string) {
        String string2 = "";
        String string3 = "";
        if (bz.c(string.equals("") ? 1 : 0)) {
            return string2;
        }
        if (bz.a(string.charAt(b[3]), b[4])) {
            string3 = "-";
            string = string.substring(b[5]);
        }
        int n2 = string.length() - b[5];
        while (bz.d(n2)) {
            if (bz.e((string.length() - b[5] - n2) % b[6]) && bz.f(string.length() - b[5] - n2)) {
                string2 = string.charAt(n2) + "." + string2;
                "".length();
                if (" ".length() != " ".length()) {
                    return null;
                }
            } else {
                string2 = string.charAt(n2) + string2;
            }
            --n2;
            "".length();
            if (" ".length() < "   ".length()) continue;
            return null;
        }
        return string3 + string2;
    }

    public static void a(String string, short s2) {
        bj.a(string, "sms://".concat(String.valueOf(s2)), new fr("", em.f(), b[7], null), new fr("", em.f(), b[8], null));
    }

    private static boolean e(int n2) {
        return n2 == 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean f(int n2) {
        return n2 > 0;
    }

    public static String[] a(String string, String string2) {
        ad ad2 = new ad();
        int n2 = string.indexOf(string2);
        while (bz.d(n2)) {
            ad2.addElement(string.substring(b[3], n2));
            string = string.substring(n2 + string2.length());
            n2 = string.indexOf(string2);
            "".length();
            if (-(0x3D ^ 0x38) < 0) continue;
            return null;
        }
        ad2.addElement(string);
        String[] stringArray = new String[ad2.size()];
        if (bz.f(ad2.size())) {
            int n3 = b[3];
            while (bz.d(n3, ad2.size())) {
                stringArray[n3] = (String)ad2.elementAt(n3);
                ++n3;
                "".length();
                if (null == null) continue;
                return null;
            }
        }
        return stringArray;
    }

    static {
        bz.d();
        ce.a(b[12]);
        bz.b();
    }

    public static byte[] b(eh eh2) {
        try {
            byte[] byArray = new byte[eh2.a.readInt()];
            eh2.a.read(byArray);
            "".length();
            return byArray;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] a(DataInputStream dataInputStream) {
        try {
            byte[] byArray = new byte[dataInputStream.readInt()];
            dataInputStream.read(byArray);
            "".length();
            return byArray;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String b(int n2) {
        int n3 = b[3];
        if (bz.c(n2, b[9])) {
            n3 = n2 / b[9];
            n2 %= b[9];
        }
        int n4 = b[3];
        if (bz.c(n3, b[9])) {
            n4 = n3 / b[9];
            n3 %= b[9];
        }
        int n5 = b[3];
        if (bz.c(n4, b[10])) {
            n5 = n4 / b[10];
            n4 %= b[10];
        }
        String string = "";
        if (bz.f(n5)) {
            string = string + n5;
            string = string + "d";
            string = string + n4 + "h";
            "".length();
            if (" ".length() == 0) {
                return null;
            }
        } else if (bz.f(n4)) {
            string = string + n4;
            string = string + "h";
            string = string + n3 + "'";
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            if (bz.c(n3, b[11])) {
                string = string + n3;
                "".length();
                if (((0xEF ^ 0xBD) & ~(0x92 ^ 0xC0)) != 0) {
                    return null;
                }
            } else {
                string = string + "0" + n3;
            }
            string = string + ":";
            if (bz.c(n2, b[11])) {
                string = string + n2;
                "".length();
                if ((0x84 ^ 0xC2 ^ (0x76 ^ 0x34)) < (63 + 15 - 28 + 146 ^ 101 + 138 - 200 + 153)) {
                    return null;
                }
            } else {
                string = string + "0" + n2;
            }
        }
        return string;
    }

    public static String a(String string, String string2, String string3) {
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        while (bz.b(n2 = string.indexOf(string2), b[2])) {
            stringBuffer.append(string.substring(b[3], n2) + string3);
            "".length();
            string = string.substring(n2 + string2.length());
            "".length();
            if ("  ".length() == "  ".length()) continue;
            return null;
        }
        stringBuffer.append(string);
        "".length();
        return stringBuffer.toString();
    }
}

