/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class ff {
    private static final int[] g;
    public static String a;
    public static int b;
    public static int c;
    public static String d;
    public static int[] e;
    public static String f;

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static void c() {
        g = new int[9];
        ff.g[0] = (0x2C ^ 0x6B) & ~(0xB ^ 0x4C);
        ff.g[1] = 0x1F ^ 0x1B;
        ff.g[2] = " ".length();
        ff.g[3] = 0xB4 ^ 0xBD;
        ff.g[4] = "  ".length();
        ff.g[5] = 0x65 ^ 0x75;
        ff.g[6] = 0x15 ^ 0x60 ^ (0x6D ^ 0x14);
        ff.g[7] = 0x43 ^ 0x5B;
        ff.g[8] = -(0xFFFFEE2E & 0x55D5) & (0xFFFFFCEF & 0x7F7F);
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static boolean a() {
        int n2 = g[0];
        while (ff.b(n2, g[1])) {
            if (ff.c(ec.l(n2), g[1])) {
                return g[2];
            }
            ++n2;
            "".length();
            if (-(0x62 ^ 0x4D ^ (0xE ^ 0x24)) < 0) continue;
            return ((108 + 153 - 145 + 53 ^ 5 + 91 - 36 + 93) & (49 + 83 - 111 + 116 ^ 130 + 149 - 137 + 43 ^ -" ".length())) != 0;
        }
        return g[0];
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean e(int n2, int n3) {
        return n2 <= n3;
    }

    public static void b() {
        ec ec2 = ec.n();
        if (ff.a(ey.u instanceof ba) && ff.a(ec2.bu, g[3]) && ff.b(ff.a() ? 1 : 0)) {
            Vector<el> vector = new Vector<el>();
            int n2 = g[0];
            while (ff.b(n2, g[1])) {
                vector.removeAllElements();
                int n3 = g[0];
                while (ff.b(n3, ec2.en.length)) {
                    el el2 = ec2.en[n3];
                    if (ff.a(el2) && ff.d(el2.m.d, n2)) {
                        vector.addElement(el2);
                    }
                    ++n3;
                    "".length();
                    if (" ".length() > -" ".length()) continue;
                    return;
                }
                while (ff.c(vector.size(), g[1])) {
                    n3 = g[2];
                    int n4 = n2;
                    while (ff.b(n4, g[1]) && ff.e(ap.cu[n4], ec2.cg) && ff.e(n3 << g[4], vector.size()) && ff.b(n3, g[5])) {
                        n3 <<= g[4];
                        ++n4;
                        "".length();
                        if (((0x2C ^ 0x40 ^ (0x3F ^ 0x1F)) & (0xF7 ^ 0x89 ^ (0x7A ^ 0x48) ^ -" ".length())) > -" ".length()) continue;
                        return;
                    }
                    if (ff.d(n3, g[2])) {
                        return;
                    }
                    ap.s().c(g[6]);
                    ap.af = new el[g[7]];
                    n4 = g[0];
                    while (ff.b(n4, n3)) {
                        el el3;
                        ap.af[n4] = el3 = (el)vector.elementAt(g[0]);
                        ec2.en[el3.n] = null;
                        vector.removeElementAt(g[0]);
                        ++n4;
                        "".length();
                        if (-" ".length() < 0) continue;
                        return;
                    }
                    dl.h().a(ap.af);
                    bo.u();
                    if (ff.a(ap.af[g[0]])) {
                        ec2.en[ap.af[ff.g[0]].n] = ap.af[g[0]];
                    }
                    "".length();
                    if (null == null) continue;
                    return;
                }
                n2 = (short)(n2 + g[2]);
                "".length();
                if (-" ".length() < "   ".length()) continue;
                return;
            }
        }
    }

    static {
        ff.c();
        a = "161.118.209.14";
        d = "161.118.209.14";
        f = "161.118.209.14";
        b = g[8];
        c = g[8];
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }
}

