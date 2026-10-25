/*
 * Decompiled with CFR 0.152.
 */
public final class bl {
    private static int a;
    private static int b;
    private static ad c;
    private static int d;
    private static fp e;
    private static int f;
    private static final int[] g;
    private static int h;
    private static int i;

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    public static void a() {
        c = new ad();
        h = g[0];
        a = g[1];
        d = g[2];
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    static {
        bl.e();
        ce.a(g[21]);
        bl.a();
    }

    public static void a(String string, int n2, doa doa2) {
        if (bl.a(bl.b(string) ? 1 : 0)) {
            if (bl.b(em.aj, g[17])) {
                a = g[15];
            }
            if (bl.d(c.size(), g[9])) {
                c.removeElementAt(g[11]);
            }
            c.addElement(new fp(string, doa2, n2));
        }
    }

    public static boolean b() {
        int n2;
        if (bl.b(h, g[0]) && bl.a(c.size())) {
            n2 = g[15];
            "".length();
            if ("   ".length() > "   ".length()) {
                return ((0xE6 ^ 0xB0) & ~(0xF0 ^ 0xA6)) != 0;
            }
        } else {
            n2 = g[11];
        }
        return n2 != 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public static void c() {
        if (bl.a(h)) {
            if (bl.c((i += (a - i) / g[14]) - a, g[14])) {
                i = a + g[1];
                h = g[1];
                b = g[11];
                return;
            }
        } else if (bl.b(h, g[1])) {
            if (bl.d(b += g[15], bl.e.b)) {
                h = g[14];
                b = g[11];
                return;
            }
        } else if (bl.b(h, g[14])) {
            if (bl.c(i + f, a + em.aj - g[2])) {
                i -= g[16];
                "".length();
                if (" ".length() <= ((0x43 ^ 0x75) & ~(0x2D ^ 0x1B))) {
                    return;
                }
            } else {
                i -= g[1];
            }
            if (bl.c(i + f, a)) {
                h = g[12];
                b = g[11];
                return;
            }
        } else if (bl.b(h, g[12])) {
            if (bl.d(b += g[15], g[9])) {
                h = g[0];
                b = g[11];
                return;
            }
        } else if (bl.b(h, g[0])) {
            if (bl.c(c.size())) {
                fp fp2 = (fp)c.firstElement();
                c.removeElementAt(g[11]);
                if (bl.b(e) && bl.b(fp2.a.equals(bl.e.a) ? 1 : 0)) {
                    return;
                }
                e = fp2;
                f = fp2.c.a(bl.e.a);
                b = g[11];
                h = g[11];
                i = em.aj;
                return;
            }
            e = null;
        }
    }

    private static boolean c(int n2) {
        return n2 > 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    public static void a(bh bh2) {
        int n2 = a;
        int n3 = em.aq - g[3];
        int n4 = em.aj;
        if (bl.b(em.w ? 1 : 0)) {
            if (bl.a(em.aj, g[4])) {
                n2 = g[5];
                n4 = em.aj - g[6];
                "".length();
                if (((0x8A ^ 0x8F) & ~(0x86 ^ 0x83)) != 0) {
                    return;
                }
            } else {
                n2 = g[7];
                n4 = em.aj - g[8] - g[9];
            }
            n3 = em.aq - g[10];
            a = n2 + g[1];
        }
        if (bl.b(e) && (!bl.b(em.d) || bl.a(em.d.e))) {
            bh2.a(g[11], g[11], em.aj, em.aq);
            if (bl.b(em.w ? 1 : 0)) {
                br.b(n2, n3 - g[12], n4 + g[9], d + g[13], bh2);
                "".length();
                if (((0xBC ^ 0x86) & ~(0x47 ^ 0x7D)) >= "  ".length()) {
                    return;
                }
            } else {
                bh2.a(g[11]);
                bh2.e(n2 - g[1], n3, n4 + g[1], d);
            }
            bh2.a(n2, n3, n4, d);
            bl.e.c.a(bh2, bl.e.a, i, n3 + g[14], g[11]);
        }
    }

    public static void a(String string) {
        if (bl.a(bl.b(string) ? 1 : 0)) {
            if (bl.b(em.aj, g[17])) {
                a = g[15];
            }
            if (bl.d(c.size(), g[9])) {
                c.removeElementAt(g[11]);
            }
            c.addElement(new fp(string));
        }
    }

    private static void e() {
        g = new int[22];
        bl.g[0] = 0x69 ^ 0x6C;
        bl.g[1] = "  ".length();
        bl.g[2] = 0x54 ^ 0x40;
        bl.g[3] = 0xEB ^ 0x8E ^ (0x38 ^ 0x4A);
        bl.g[4] = -(0xFFFFDF3D & 0x7ADF) & (0xFFFFFBFE & 0x5FDF);
        bl.g[5] = (0x30 ^ 0x48) + (0x57 ^ 0x25) - (0x7A ^ 6) + (0x1D ^ 9);
        bl.g[6] = -(0xFFFFFCFC & Short.MAX_VALUE) & (0xFFFFFFFF & 0x7DFF);
        bl.g[7] = 0x20 ^ 0x70;
        bl.g[8] = (0x1C ^ 0x21) + (0x91 ^ 0x8C) - -(0x65 ^ 0x7D) + (0xAF ^ 0x81);
        bl.g[9] = 0x4D ^ 0x2F ^ (0x5E ^ 0x36);
        bl.g[10] = 0x8B ^ 0xB7;
        bl.g[11] = (0xB3 ^ 0x97) & ~(0x6E ^ 0x4A);
        bl.g[12] = 0x7D ^ 7 ^ (0x65 ^ 0x1B);
        bl.g[13] = 108 + 19 - 38 + 45 ^ 55 + 61 - 25 + 51;
        bl.g[14] = "   ".length();
        bl.g[15] = " ".length();
        bl.g[16] = 0x2F ^ 0x29;
        bl.g[17] = 101 + 58 - 136 + 105;
        bl.g[18] = 35 + 12 - -96 + 15 ^ 103 + 15 - 77 + 112;
        bl.g[19] = 0x1E ^ 0x26 ^ (0x84 ^ 0x8C);
        bl.g[20] = 0xAE ^ 0xB6 ^ (0x90 ^ 0xB1);
        bl.g[21] = 0x31 ^ 0x21 ^ (0x4E ^ 0x57);
    }

    public static void d() {
        c = null;
        e = null;
        h = g[11];
        b = g[11];
        i = g[11];
        f = g[11];
        a = g[11];
        d = g[11];
    }

    private static boolean b(String string) {
        String string2;
        String string3;
        String string4;
        if (bl.b(e) && bl.b((Object)bl.e.a) && bl.b(string.equals(bl.e.a) ? 1 : 0)) {
            return g[15];
        }
        if (bl.c(c.size()) && bl.b(string.equals(((fp)bl.c.lastElement()).a) ? 1 : 0)) {
            return g[15];
        }
        if (bl.c(string.length(), g[13])) {
            return g[11];
        }
        if (bl.b(e) && bl.b((Object)bl.e.a) && bl.c(h, g[14]) && bl.a(bl.e.a.length(), g[13]) && bl.b((string4 = string.substring(g[11], g[13])).equals(string3 = bl.e.a.substring(g[11], g[13])) ? 1 : 0)) {
            int n2 = g[18];
            while (bl.c(n2, string.length()) && bl.c(n2, bl.e.a.length()) && (!bl.a(string.charAt(n2), g[19]) || bl.d(string.charAt(n2), g[20])) && bl.b(string.charAt(n2), bl.e.a.charAt(n2))) {
                ++n2;
                "".length();
                if (-(0x10 ^ 0x15) < 0) continue;
                return ((0xBB ^ 0x8C) & ~(0x4D ^ 0x7A)) != 0;
            }
            string3 = string.substring(n2, string.length());
            bl.e.a = bl.e.a + ", " + string3;
            h = g[1];
            b = g[11];
            return g[15];
        }
        if (bl.c(c.size()) && bl.a((string4 = ((fp)bl.c.lastElement()).a).length(), g[13]) && bl.b((string3 = string.substring(g[11], g[13])).equals(string2 = string4.substring(g[11], g[13])) ? 1 : 0)) {
            int n3 = g[18];
            while (bl.c(n3, string.length()) && bl.c(n3, string4.length()) && (!bl.a(string.charAt(n3), g[19]) || bl.d(string.charAt(n3), g[20])) && bl.b(string.charAt(n3), string4.charAt(n3))) {
                ++n3;
                "".length();
                if ("   ".length() > 0) continue;
                return ((0x5D ^ 0x55 ^ (0xA9 ^ 0x9F)) & (0x1A ^ 0x74 ^ (0x64 ^ 0x34) ^ -" ".length())) != 0;
            }
            string = string.substring(n3, string.length());
            new StringBuffer(String.valueOf(string4)).append(", ").append(string);
            "".length();
            return g[15];
        }
        return g[11];
    }
}

