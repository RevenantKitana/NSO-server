/*
 * Decompiled with CFR 0.152.
 */
public final class p
extends ax {
    private y b;
    private static String[] c;
    private static final int[] d;
    public static boolean a;
    private static int e;

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }

    static {
        p.e();
        a = d[0];
        String[] stringArray = new String[d[6]];
        stringArray[p.d[0]] = "H\u00f4m nay con \u0111\u00e3 l\u00e0m h\u1ebft nhi\u1ec7m v\u1ee5 ta giao. H\u00e3y quay l\u1ea1i v\u00e0o ng\u00e0y h\u00f4m sau.";
        stringArray[p.d[2]] = "\u0110\u00e2y l\u00e0 l\u1ea7n nh\u1eadn nhi\u1ec7m v\u1ee5 th\u1ee9 ";
        stringArray[p.d[3]] = " trong ng\u00e0y h\u00f4m nay. M\u1ed7i ng\u00e0y \u0111\u01b0\u1ee3c nh\u1eadn t\u1ed1i \u0111a 20 l\u1ea7n con nh\u00e9.";
        c = stringArray;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    public final void b() {
        this.b = ec.n(d[0]);
    }

    private static boolean e(int n2) {
        return n2 < 0;
    }

    public final String toString() {
        return "Auto Nhi\u1ec7m v\u1ee5 h\u1eb1ng ng\u00e0y: " + e + "/20";
    }

    public static void a(String string) {
        if (p.a(string.equals(c[d[0]]) ? 1 : 0)) {
            e = d[1];
            bo.d();
            "".length();
            if ("   ".length() < "  ".length()) {
                return;
            }
        } else {
            int n2 = string.indexOf(c[d[2]]);
            if (p.f(n2)) {
                string = string.substring(n2 + c[d[2]].length(), string.indexOf(c[d[3]])).trim();
                try {
                    e = Integer.parseInt(string);
                    return;
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        }
    }

    private static boolean f(int n2) {
        return n2 >= 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void a() {
        if (p.c(e, d[4]) && (!p.a(this.q instanceof ak) || p.e(p.a(System.currentTimeMillis() - this.m, 3600000L)))) {
            if (p.g(ec.n().cl)) {
                ax.a(d[0]);
                return;
            }
            if (p.a(de.j(de.i) ? 1 : 0)) {
                if (p.b(this.b)) {
                    ap.a("Nh\u1eadn NV " + (e + d[2]) + "/20");
                    ap.b(d[5], ap.dv, d[0]);
                    bo.ag();
                    this.b = ec.n(d[0]);
                    return;
                }
                if (p.d(this.b.a, this.b.f)) {
                    if (p.g(ec.u())) {
                        ap.a("H\u00e0nh trang \u0111\u1ea7y");
                        return;
                    }
                    ap.a("Ho\u00e0n th\u00e0nh NV " + e + "/20");
                    ap.b(d[5], ap.dv, d[3]);
                    this.b = null;
                    return;
                }
                ap.a("\u0110i l\u00e0m NV " + e + "/20");
                ap.b(d[5], ap.dv, d[6]);
                de.g();
                this.b(this.x);
                return;
            }
            if (p.a(this.b) && p.e(de.i, this.b.d)) {
                if (p.d(this.b.a, this.b.f)) {
                    ax.h();
                    return;
                }
                this.b(this.b.b, d[2]);
                this.d(d[7]);
                if (p.a(a ? 1 : 0)) {
                    ap.a("Nhi\u1ec7m v\u1ee5 " + e + "/20: " + this.b.a + "/" + this.b.f + " " + by.h[this.b.b].j);
                    a = d[0];
                    return;
                }
            }
        } else {
            ap.a("Ho\u00e0n th\u00e0nh!");
            ey.i();
        }
    }

    public final void c() {
        e = d[0];
        this.b = ec.n(d[0]);
        super.c();
    }

    private static void e() {
        d = new int[8];
        p.d[0] = (0xA7 ^ 0x9C) & ~(0xAD ^ 0x96);
        p.d[1] = 162 + 138 - 142 + 50 ^ 115 + 31 - 37 + 88;
        p.d[2] = " ".length();
        p.d[3] = "  ".length();
        p.d[4] = 0x10 ^ 4;
        p.d[5] = 0xA ^ 0x30 ^ (0x40 ^ 0x63);
        p.d[6] = "   ".length();
        p.d[7] = -" ".length();
    }

    private static boolean g(int n2) {
        return n2 <= 0;
    }

    private static boolean b(Object object) {
        return object == null;
    }
}

