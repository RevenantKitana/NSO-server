/*
 * Decompiled with CFR 0.152.
 */
public final class ci {
    private static int b;
    private static int c;
    private static int d;
    private static ad e;
    private static final int[] f;
    private static fp g;
    public static int a;
    private static int h;
    private static int i;

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    static {
        ci.d();
        ce.a(f[16]);
        ci.a();
    }

    /*
     * Unable to fully structure code
     */
    public static void a(String var0, int var1_1, doa var2_2) {
        block14: {
            block15: {
                block13: {
                    var3_3 = var0;
                    if (!ci.a(ci.g) || !ci.a(ci.g.a) || !ci.c((int)var0.equals(ci.g.a))) break block13;
                    var4_4 = ci.f[10];
                    "".length();
                    if (-" ".length() > "  ".length()) {
                        return;
                    }
                    break block14;
                }
                if (!ci.b(ci.e.size()) || !ci.c((int)var0.equals(((fp)ci.e.lastElement()).a))) break block15;
                var4_4 = ci.f[10];
                "".length();
                if (" ".length() <= 0) {
                    return;
                }
                break block14;
            }
            if (!ci.c(var0.length(), ci.f[7])) ** GOTO lbl-1000
            if (ci.a(ci.g) && ci.a(ci.g.a) && ci.b(ci.i, ci.f[9]) && ci.c(ci.g.a.length(), ci.f[7]) && ci.c((int)(var5_5 = var0.substring(ci.f[3], ci.f[7])).equals(var6_6 = ci.g.a.substring(ci.f[3], ci.f[7])))) {
                var7_7 = ci.f[14];
                while (ci.b(var7_7, var3_3.length()) && ci.b(var7_7, ci.g.a.length()) && ci.d(var3_3.charAt(var7_7), ci.g.a.charAt(var7_7))) {
                    ++var7_7;
                    "".length();
                    if ((13 ^ 9) >= ((57 ^ 25) & ~(11 ^ 43))) continue;
                    return;
                }
                var6_6 = var3_3.substring(var7_7, var3_3.length());
                ci.g.a = ci.g.a + ", " + var6_6;
                ci.i = ci.f[1];
                ci.c = ci.f[3];
                var4_4 = ci.f[10];
                "".length();
                if (-"  ".length() >= 0) {
                    return;
                }
            } else if (ci.b(ci.e.size()) && ci.c((var5_5 = ((fp)ci.e.lastElement()).a).length(), ci.f[7]) && ci.c((int)(var6_6 = var0.substring(ci.f[3], ci.f[7])).equals(var7_8 = var5_5.substring(ci.f[3], ci.f[7])))) {
                var8_9 = ci.f[14];
                while (ci.b(var8_9, var3_3.length()) && ci.b(var8_9, var5_5.length()) && ci.d(var3_3.charAt(var8_9), var5_5.charAt(var8_9))) {
                    ++var8_9;
                    "".length();
                    if (" ".length() >= 0) continue;
                    return;
                }
                var3_3 = var3_3.substring(var8_9, var3_3.length());
                new StringBuffer(String.valueOf(var5_5)).append(", ").append(var3_3);
                "".length();
                var4_4 = ci.f[10];
                "".length();
                if ("   ".length() <= -" ".length()) {
                    return;
                }
            } else lbl-1000:
            // 2 sources

            {
                var4_4 = ci.f[3];
            }
        }
        if (ci.a(var4_4)) {
            if (ci.d(em.aj, ci.f[15])) {
                ci.b = ci.f[10];
            }
            if (ci.a(ci.e.size(), ci.f[6])) {
                ci.e.removeElementAt(ci.f[3]);
            }
            ci.e.addElement(new fp(var0, var2_2, var1_1));
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private static void d() {
        f = new int[17];
        ci.f[0] = 0x51 ^ 0x54;
        ci.f[1] = "  ".length();
        ci.f[2] = 0xAA ^ 0xA5;
        ci.f[3] = (0x89 ^ 0xAF) & ~(0xB ^ 0x2D);
        ci.f[4] = -(0xD9 ^ 0x86 ^ (0xF ^ 0x56));
        ci.f[5] = -(0x5A ^ 0x5E);
        ci.f[6] = 8 + 69 - 73 + 144 ^ 3 + 131 - 70 + 94;
        ci.f[7] = 0xE ^ 0x5A ^ (0xFA ^ 0xA6);
        ci.f[8] = 131 + 50 - 146 + 144 ^ 139 + 31 - 146 + 143;
        ci.f[9] = "   ".length();
        ci.f[10] = " ".length();
        ci.f[11] = 36 + 135 - 36 + 18 + (0x85 ^ 0x90) - (0x34 ^ 0x7A) + (0x50 ^ 0x10);
        ci.f[12] = 99 + 96 - 192 + 146 ^ 79 + 45 - 98 + 121;
        ci.f[13] = 139 + 45 - 83 + 53 ^ 76 + 125 - 149 + 106;
        ci.f[14] = 0xE2 ^ 0x85 ^ (0x53 ^ 0x33);
        ci.f[15] = (0x37 ^ 0x32) + (0xD ^ 0x42) - -(0x2F ^ 8) + (0xC5 ^ 0xC0);
        ci.f[16] = 0x43 ^ 0x61;
    }

    public static void a() {
        e = new ad();
        i = f[0];
        b = f[1];
        a = f[2];
    }

    public static void a(bh bh2) {
        int n2 = em.aj;
        if (ci.a(g)) {
            bh2.a(f[3], f[3], em.aj, em.aq);
            if (ci.a(em.w ? 1 : 0)) {
                br.b(f[4], f[5], n2 + f[6], a + f[7], bh2);
                "".length();
                if (((0x3E ^ 0x18) & ~(0x6C ^ 0x4A)) != 0) {
                    return;
                }
            } else {
                bh2.a(f[3]);
                bh2.e(f[3], f[3], n2, a);
            }
            bh2.a(f[3], f[3], n2, a + f[0]);
            ci.g.c.a(bh2, ci.g.a, h, f[0], f[3]);
        }
    }

    public static void b() {
        if (ci.c(em.w ? 1 : 0)) {
            a = f[8];
        }
        if (ci.a(i)) {
            if (ci.b((h += (b - h) / f[9]) - b, f[9])) {
                h = b + f[1];
                i = f[1];
                c = f[3];
                return;
            }
        } else if (ci.d(i, f[1])) {
            if (ci.a(c += f[10], ci.g.b)) {
                i = f[9];
                c = f[3];
                return;
            }
        } else if (ci.d(i, f[9])) {
            if (ci.b(h + d, b + em.aj - f[11])) {
                h -= f[12];
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            } else {
                h -= f[1];
            }
            if (ci.b(h + d, b)) {
                i = f[13];
                c = f[3];
                return;
            }
        } else if (ci.d(i, f[13])) {
            if (ci.a(c += f[10], f[6])) {
                i = f[0];
                c = f[3];
                return;
            }
        } else if (ci.d(i, f[0])) {
            if (ci.b(e.size())) {
                fp fp2 = (fp)e.firstElement();
                e.removeElementAt(f[3]);
                if (ci.a(g) && ci.c(fp2.a.equals(ci.g.a) ? 1 : 0)) {
                    return;
                }
                g = fp2;
                d = fp2.c.a(ci.g.a);
                c = f[3];
                i = f[3];
                h = em.aj;
                return;
            }
            g = null;
            if (ci.c(em.w ? 1 : 0)) {
                a = f[3];
            }
        }
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    public static void c() {
        e = null;
        g = null;
        i = f[3];
        c = f[3];
        h = f[3];
        d = f[3];
        b = f[3];
        a = f[3];
    }
}

