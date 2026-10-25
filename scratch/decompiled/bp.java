/*
 * Decompiled with CFR 0.152.
 */
public final class bp {
    private static final int[] b;
    public static int a;
    private static ad c;

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public static void a() {
        c = null;
        a = b[1];
    }

    public static an a(String string) {
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            an an2 = (an)c.elementAt(n2);
            if (bp.b(an2.k.equals(string) ? 1 : 0)) {
                return an2;
            }
            ++n2;
            "".length();
            if ((0x41 ^ 0x45) > -" ".length()) continue;
            return null;
        }
        return null;
    }

    private static void f() {
        b = new int[4];
        bp.b[0] = -" ".length();
        bp.b[1] = (0x1C ^ 0x25 ^ (0x43 ^ 0x77)) & (0x7E ^ 0x73 ^ (0x35 ^ 0x1D) & ~(0x1A ^ 0x32) ^ -" ".length());
        bp.b[2] = " ".length();
        bp.b[3] = 0x3A ^ 0x3D;
    }

    public static void b() {
        c = new ad();
        a = b[0];
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public static an a(short s2) {
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            an an2 = (an)c.elementAt(n2);
            if (bp.b(an2.d.d, s2)) {
                return an2;
            }
            ++n2;
            "".length();
            if (-(0x5B ^ 0x32 ^ (0xE9 ^ 0x84)) <= 0) continue;
            return null;
        }
        return null;
    }

    private static boolean c(int n2) {
        return n2 < 0;
    }

    static {
        bp.f();
        ce.a(b[3]);
        bp.b();
    }

    public static ad c() {
        ad ad2 = new ad();
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            an an2 = (an)c.elementAt(n2);
            ad2.addElement(n2 + ". " + an2.d.e + " id " + an2.k);
            if (bp.a(an2.b)) {
                ad2.addElement("Mua " + an2.a + "k/" + an2.b + " max " + an2.f);
            }
            if (bp.a(an2.h)) {
                ad2.addElement("B\u00e1n " + an2.c + "k/" + an2.h + " min " + an2.i);
            }
            ++n2;
            "".length();
            if ("  ".length() != -" ".length()) continue;
            return null;
        }
        return ad2;
    }

    public static void a(bf bf2) {
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            if (bp.b(((an)bp.c.elementAt((int)n2)).d.d, bf2.d)) {
                c.removeElementAt(n2);
                return;
            }
            ++n2;
            "".length();
            if ((0x1D ^ 0x19) == (0x77 ^ 0x73)) continue;
            return;
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    public static void b(String string) {
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            an an2 = (an)c.elementAt(n2);
            if (bp.a(ec.b(an2.d.d))) {
                int n3 = an2.a();
                int n4 = an2.c();
                if (bp.b(n3) && bp.b(n4)) {
                    ey.c(string, "id: " + an2.k + " " + an2.d.e + " b\u00e1n: " + an2.c + "k/" + an2.h + "c\u00e1i, mua: " + an2.a + "k/" + an2.b + " c\u00e1i. C\u00f2n " + an2.b() + " c\u00e1i.");
                    "".length();
                    if (-(0x4D ^ 0x1A ^ (0x2D ^ 0x7E)) >= 0) {
                        return;
                    }
                } else if (bp.b(n3)) {
                    ey.c(string, "id: " + an2.k + " " + an2.d.e + " b\u00e1n: " + an2.c + "k/" + an2.h + "c\u00e1i. C\u00f2n " + an2.b() + " c\u00e1i.");
                    "".length();
                    if (-"   ".length() >= 0) {
                        return;
                    }
                } else if (bp.b(n4)) {
                    int n5;
                    StringBuffer stringBuffer = new StringBuffer().append("id: ").append(an2.k).append(" ").append(an2.d.e).append(" mua: ").append(an2.a).append("k/").append(an2.b).append(" c\u00e1i. C\u00f2n mua: ");
                    if (bp.a(an2.b) && bp.a(ec.l(an2.d.d), an2.f)) {
                        n5 = an2.f - ec.l(an2.d.d);
                        "".length();
                        if ((0x43 ^ 0x19 ^ (0x56 ^ 8)) == "   ".length()) {
                            return;
                        }
                    } else {
                        n5 = b[1];
                    }
                    ey.c(string, stringBuffer.append(n5).toString());
                }
            }
            ++n2;
            "".length();
            if (" ".length() < "   ".length()) continue;
            return;
        }
    }

    public static String d() {
        if (bp.d(c.size())) {
            return "";
        }
        if (!bp.a(a, c.size()) || bp.c(a)) {
            a = b[1];
        }
        int n2 = a;
        do {
            a = (a + b[2]) % c.size();
            an an2 = (an)c.elementAt(a);
            int n3 = an2.a();
            int n4 = an2.c();
            if (bp.b(n3) && bp.b(n4)) {
                return "@" + ec.n().cx + " b\u00e1n " + an2.k + " " + an2.c + "k " + an2.h + " c\u00e1i, mua " + an2.a + "k " + an2.b + " cai, mua b\u00e1n pm";
            }
            if (bp.b(n3)) {
                return "@" + ec.n().cx + " b\u00e1n " + an2.k + " " + an2.c + "k " + an2.h + " c\u00e1i, mua pm";
            }
            if (!bp.b(n4)) continue;
            return "@" + ec.n().cx + " mua " + an2.k + " " + an2.a + "k " + an2.b + " c\u00e1i, b\u00e1n pm ho\u1eb7c gd";
        } while (!bp.b(n2, a));
        return "";
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean d(int n2) {
        return n2 == 0;
    }

    public static boolean e() {
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            if (bp.b(((an)c.elementAt(n2)).c() ? 1 : 0)) {
                return b[2];
            }
            ++n2;
            "".length();
            if (((0x6C ^ 0x5A) & ~(0x98 ^ 0xAE)) == 0) continue;
            return ((0xAE ^ 0x85) & ~(0x76 ^ 0x5D)) != 0;
        }
        return b[1];
    }

    public static void c(String string) {
        an an2;
        ad ad2 = new ad();
        int n2 = b[1];
        while (bp.a(n2, c.size())) {
            an2 = (an)c.elementAt(n2);
            if (bp.b(an2.a() ? 1 : 0)) {
                ad2.addElement(an2);
            }
            ++n2;
            "".length();
            if (-" ".length() < 0) continue;
            return;
        }
        if (bp.a(ad2.size())) {
            if (bp.b(ad2.size(), b[2])) {
                an an3 = (an)ad2.firstElement();
                ey.c(string, "- \u0110\u1ec3 mua h\u00e0ng chat buy idvp solg ho\u1eb7c mua solg idvp");
                ey.c(string, "     Vd mua 12 " + an3.d.e);
                ey.c(string, "     chat mua 12 " + an3.k + " ho\u1eb7c buy " + an3.k + " 12");
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            } else {
                an an4 = (an)ad2.elementAt(b[1]);
                an2 = (an)ad2.elementAt(b[2]);
                ey.c(string, "- \u0110\u1ec3 mua h\u00e0ng chat buy idvp1 solg1 idvp2 solg2.... ho\u1eb7c mua solg1 idvp1 solg2 idvp2...");
                ey.c(string, "     Vd mua 5 " + an4.d.e + " 5 " + an2.d.e);
                ey.c(string, "     chat mua 5 " + an4.k + " 5 " + an2.k + " ho\u1eb7c buy " + an4.k + " 5 " + an2.k + " 5");
            }
        }
        if (bp.b(bp.e() ? 1 : 0)) {
            ey.c(string, "- \u0110\u1ec3 b\u00e1n h\u00e0ng chat sell ho\u1eb7c giao d\u1ecbch");
        }
        ey.c(string, "- \u0110\u1ec3 Donate chat donate. Thank");
    }

    public static void a(bf bf2, String string) {
        an an2 = an.a(bf2, string);
        if (bp.a(an2)) {
            int n2 = b[1];
            while (bp.a(n2, c.size())) {
                if (bp.b(((an)bp.c.elementAt((int)n2)).d.d, bf2.d)) {
                    c.setElementAt(an2, n2);
                    return;
                }
                ++n2;
                "".length();
                if ("   ".length() != ((124 + 143 - 260 + 246 ^ 5 + 126 - 62 + 108) & (0xB9 ^ 0xB6 ^ (0xF2 ^ 0xB1) ^ -" ".length()))) continue;
                return;
            }
            c.addElement(an2);
        }
    }

    private static boolean a(Object object) {
        return object != null;
    }
}

