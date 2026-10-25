/*
 * Decompiled with CFR 0.152.
 */
public final class br {
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static int e;
    private static final int[] g;
    public static int f;

    public static void a(bh bh2, fr fr2, fr fr3, fr fr4) {
        int n2;
        int n3;
        doa doa2;
        doa doa3;
        if (br.c(em.w ? 1 : 0)) {
            doa3 = doa.r;
            "".length();
            if (null != null) {
                return;
            }
        } else {
            doa3 = doa2 = doa.b;
        }
        if (br.c(em.w ? 1 : 0)) {
            n3 = g[6];
            "".length();
            if ("   ".length() < -" ".length()) {
                return;
            }
        } else {
            n3 = n2 = g[7];
        }
        if (br.a(em.w ? 1 : 0)) {
            if (br.a(fr2)) {
                doa2.a(bh2, fr2.d, g[8], em.aq - ef.fs + g[9] + n2, g[10]);
            }
            if (br.a(fr3)) {
                doa2.a(bh2, fr3.d, em.r, em.aq - ef.fs + g[9] + n2, g[11]);
            }
            if (br.a(fr4)) {
                if (br.a(fr4.f)) {
                    bh2.a(fr4.f, em.aj - g[8], em.aq - g[12], g[13]);
                    return;
                }
                doa2.a(bh2, fr4.d, em.aj - g[8], em.aq - ef.fs + g[9] + n2, g[7]);
                return;
            }
        } else {
            if (br.a(fr2) && br.b(doa2.a(fr2.d))) {
                if (br.b(fr2.h) && br.b(fr2.a)) {
                    fr2.a(bh2);
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    if (br.a(ef.fr)) {
                        bh2.a(ap.ak, g[7], em.aq - ef.fs + g[7], g[10]);
                        "".length();
                        if ("  ".length() <= 0) {
                            return;
                        }
                    } else {
                        bh2.a(ap.et, g[7], em.aq - ef.fs + g[7], g[10]);
                    }
                    doa2.a(bh2, fr2.d, g[14], em.aq - ef.fs + g[9] + n2, g[11]);
                }
            }
            if (br.a(fr3) && br.b(doa2.a(fr3.d))) {
                if (br.b(fr3.h) && br.b(fr3.a)) {
                    fr3.a(bh2);
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    if (br.b(ef.fr, g[7])) {
                        bh2.a(ap.ak, em.r - g[14], em.aq - ef.fs + g[7], g[10]);
                        "".length();
                        if ("  ".length() == (0x3E ^ 0x3A)) {
                            return;
                        }
                    } else {
                        bh2.a(ap.et, em.r - g[14], em.aq - ef.fs + g[7], g[10]);
                    }
                    doa2.a(bh2, fr3.d, em.r, em.aq - ef.fs + g[9] + n2, g[11]);
                }
            }
            if (br.a(fr4) && br.b(doa2.a(fr4.d))) {
                if (br.b(fr4.h) && br.b(fr4.a)) {
                    fr4.a(bh2);
                    return;
                }
                if (br.b(ef.fr, g[11])) {
                    bh2.a(ap.ak, em.aj - g[15], em.aq - ef.fs + g[7], g[10]);
                    "".length();
                    if ("   ".length() != "   ".length()) {
                        return;
                    }
                } else {
                    bh2.a(ap.et, em.aj - g[15], em.aq - ef.fs + g[7], g[10]);
                }
                doa2.a(bh2, fr4.d, em.aj - g[14], em.aq - ef.fs + g[9] + n2, g[11]);
            }
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static void a(bh bh2) {
        if (br.a(em.w ? 1 : 0)) {
            bh2.a(g[10]);
            bh2.e(g[10], em.aq - a, em.aj, a + g[7]);
            bh2.a(g[16]);
            bh2.e(g[10], em.aq - (a - g[7]), em.aj, g[7]);
        }
    }

    public static void a(bh bh2, int n2, int n3, int n4, int n5, String[] stringArray) {
        br.b(n2, n3, n4, n5, bh2);
        n3 = n3 + g[24] - doa.b.l;
        n5 = g[10];
        while (br.a(n5, stringArray.length)) {
            doa.b.a(bh2, stringArray[n5], n2 + n4 / g[11], n3, g[11]);
            ++n5;
            n3 += doa.b.l;
            "".length();
            if (-(0xB9 ^ 0xBD) <= 0) continue;
            return;
        }
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    public static void a(int n2, int n3, int n4, int n5, bh bh2) {
        bh2.a(f);
        bh2.e(n2, n3, n4, n5);
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    public static void a(bh bh2, boolean bl2, int n2, int n3, int n4, int n5, int n6, int n7, String string) {
        bh2.a(g[10]);
        if (br.c(bl2 ? 1 : 0)) {
            bh2.a(ap.fo, g[10], g[17], g[18], g[19], g[10], n2, n3, g[10]);
            bh2.a(ap.fo, g[10], g[20], g[18], g[19], g[10], n2 + n4 - g[18], n3, g[10]);
            bh2.a(ap.fo, g[10], g[21], g[18], g[19], g[10], n2 + n4 - g[22], n3, g[10]);
            int n8 = g[10];
            while (br.a(n8, (n4 - g[22]) / g[18])) {
                bh2.a(ap.fo, g[10], g[21], g[18], g[19], g[10], n2 + g[18] + n8 * g[18], n3, g[10]);
                ++n8;
                "".length();
                if ("   ".length() > "  ".length()) continue;
                return;
            }
        } else {
            bh2.a(ap.fo, g[10], g[10], g[18], g[19], g[10], n2, n3, g[10]);
            bh2.a(ap.fo, g[10], g[23], g[18], g[19], g[10], n2 + n4 - g[18], n3, g[10]);
            bh2.a(ap.fo, g[10], g[19], g[18], g[19], g[10], n2 + n4 - g[22], n3, g[10]);
            int n9 = g[10];
            while (br.a(n9, (n4 - g[22]) / g[18])) {
                bh2.a(ap.fo, g[10], g[19], g[18], g[19], g[10], n2 + g[18] + n9 * g[18], n3, g[10]);
                ++n9;
                "".length();
                if (((0x7B ^ 0x19) & ~(0xEC ^ 0x8E)) > -" ".length()) continue;
                return;
            }
        }
        bh2.a(n2 + g[6], n3 + g[7], n4 - g[9], n5 - g[9]);
        doa.b.a(bh2, string, n6, n7, g[10]);
    }

    public static void a(bh bh2, boolean bl2, int n2, int n3, int n4, int n5, int n6, String string) {
        bh2.a(g[10]);
        if (br.c(bl2 ? 1 : 0)) {
            bh2.a(ap.fo, g[10], g[17], g[18], g[19], g[10], n2, n3, g[10]);
            bh2.a(ap.fo, g[10], g[20], g[18], g[19], g[10], n2 + n4 - g[18], n3, g[10]);
            bh2.a(ap.fo, g[10], g[21], g[18], g[19], g[10], n2 + n4 - g[22], n3, g[10]);
            int n7 = g[10];
            while (br.a(n7, (n4 - g[22]) / g[18])) {
                bh2.a(ap.fo, g[10], g[21], g[18], g[19], g[10], n2 + g[18] + n7 * g[18], n3, g[10]);
                ++n7;
                "".length();
                if (" ".length() >= 0) continue;
                return;
            }
        } else {
            bh2.a(ap.fo, g[10], g[10], g[18], g[19], g[10], n2, n3, g[10]);
            bh2.a(ap.fo, g[10], g[23], g[18], g[19], g[10], n2 + n4 - g[18], n3, g[10]);
            bh2.a(ap.fo, g[10], g[19], g[18], g[19], g[10], n2 + n4 - g[22], n3, g[10]);
            int n8 = g[10];
            while (br.a(n8, (n4 - g[22]) / g[18])) {
                bh2.a(ap.fo, g[10], g[19], g[18], g[19], g[10], n2 + g[18] + n8 * g[18], n3, g[10]);
                ++n8;
                "".length();
                if (((0x1C ^ 0x49 ^ (0x4F ^ 0x3F)) & (0x80 ^ 0xC2 ^ (0x38 ^ 0x5F) ^ -" ".length())) >= 0) continue;
                return;
            }
        }
        doa.b.a(bh2, string, n5, n6, g[10]);
    }

    public static void b(int n2, int n3, int n4, int n5, bh bh2) {
        bh2.a(c);
        bh2.e(n2, n3, n4, n5);
        bh2.a(g[10]);
        bh2.d(n2 - g[11], n3 - g[11], n4 + g[6], n5 + g[6]);
        bh2.a(g[25]);
        bh2.d(n2 - g[7], n3 - g[7], n4 + g[7], n5 + g[7]);
        bh2.a(g[26]);
        bh2.d(n2, n3, n4 - g[7], n5 - g[7]);
        if (br.c(em.w ? 1 : 0)) {
            bh2.a(em.x[g[10]], n2 - g[9], n3 - g[6], g[24]);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[11], n2 + n4 + g[9], n3 - g[6], d.a);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[7], n2 - g[9], n3 + n5 + g[6], d.c);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[6], n2 + n4 + g[9], n3 + n5 + g[6], d.g);
            bh2.a(em.x[g[7]], n2 + n4 / g[11], n3 - g[9], d.j);
        }
    }

    static {
        br.c();
        ce.a(g[23]);
        br.b();
    }

    public static void c(int n2, int n3, int n4, int n5, bh bh2) {
        bh2.a(g[10]);
        bh2.d(n2 - g[11], n3 - g[11], n4 + g[6], n5 + g[6]);
        bh2.a(g[25]);
        bh2.d(n2 - g[7], n3 - g[7], n4 + g[7], n5 + g[7]);
        bh2.a(g[26]);
        bh2.d(n2, n3, n4 - g[7], n5 - g[7]);
        if (br.c(em.w ? 1 : 0)) {
            bh2.a(em.x[g[10]], n2 - g[9], n3 - g[6], g[24]);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[11], n2 + n4 + g[9], n3 - g[6], d.a);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[7], n2 - g[9], n3 + n5 + g[6], d.c);
            bh2.a(em.x[g[10]], g[10], g[10], em.ap, em.k, g[6], n2 + n4 + g[9], n3 + n5 + g[6], d.g);
            bh2.a(em.x[g[7]], n2 + n4 / g[11], n3 - g[9], d.j);
        }
    }

    private static void c() {
        g = new int[27];
        br.g[0] = -(0xFFFFC9FB & 0x7F6C) & (0xFFFFFBE7 & 0x646F7F);
        br.g[1] = -(0xFFFFCF17 & 0x70F9) & (0xFFFFFBF2 & 0x92761D);
        br.g[2] = 0xFFFFFDF5 & 0x3C160A;
        br.g[3] = 0xFFFFEF1A & 0xE85FE5;
        br.g[4] = 0xFFFFFFFF & 0xFFFFFF;
        br.g[5] = 0x33 ^ 0x2B;
        br.g[6] = "   ".length();
        br.g[7] = " ".length();
        br.g[8] = 0x60 ^ 0x65;
        br.g[9] = 0x60 ^ 0x64;
        br.g[10] = (59 + 8 - 32 + 97 ^ 22 + 74 - 21 + 86) & (0x62 ^ 0x3D ^ (0x17 ^ 0x6D) ^ -" ".length());
        br.g[11] = "  ".length();
        br.g[12] = 0x7B ^ 0x39 ^ (0x54 ^ 0x1D);
        br.g[13] = 0x11 ^ 0x1B;
        br.g[14] = 0x64 ^ 0x59 ^ (0xAC ^ 0xB2);
        br.g[15] = 0x96 ^ 0x8E ^ (0x63 ^ 0x3C);
        br.g[16] = 0xFFFFBA9A & 0x88CDED;
        br.g[17] = 0xC2 ^ 0x93;
        br.g[18] = 88 + 76 - 116 + 91 ^ 133 + 64 - 125 + 78;
        br.g[19] = 6 ^ 0x1D;
        br.g[20] = 131 + 63 - 101 + 42;
        br.g[21] = 0x91 ^ 0xB0 ^ (0xDE ^ 0x93);
        br.g[22] = 0x21 ^ 0x1B;
        br.g[23] = 0x76 ^ 0x40;
        br.g[24] = 0x73 ^ 0x23 ^ (4 ^ 0x40);
        br.g[25] = -(0xFFFFB9A4 & 0x6F7F) & (0xFFFFFDF7 & 0xD4FFFF);
        br.g[26] = 0xFFFFCBDB & 0x577D6D;
    }

    public static void a() {
        c = g[10];
        f = g[10];
        e = g[10];
        d = g[10];
        b = g[10];
        a = g[10];
    }

    public static void d(int n2, int n3, int n4, int n5, bh bh2) {
        bh2.a(c);
        bh2.e(n2, n3, n4, n5);
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    public static void b() {
        c = g[0];
        f = g[1];
        e = g[2];
        d = g[3];
        b = g[4];
        a = g[5];
    }
}

