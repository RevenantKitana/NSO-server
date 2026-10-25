/*
 * Decompiled with CFR 0.152.
 */
import java.util.Calendar;

public final class o
extends ax {
    public static boolean a;
    public static boolean b;
    public int c;
    private static final int[] d;

    static {
        o.e();
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void e() {
        d = new int[8];
        o.d[0] = 45 + 5 - 28 + 112;
        o.d[1] = (0x77 ^ 0x61) + (33 + 49 - 5 + 51) - (0xC4 ^ 0x95) + (8 ^ 0x4A);
        o.d[2] = (0x65 ^ 0x34) + (0x92 ^ 0x9D) - (0x2B ^ 0x6A) + (0x64 ^ 0xD);
        o.d[3] = (0x69 ^ 0x3C) + (0x24 ^ 0x61) - (0x27 ^ 0xD) + (0xDB ^ 0xC2);
        o.d[4] = " ".length();
        o.d[5] = 0x60 ^ 0x6B;
        o.d[6] = 82 + 14 - -27 + 44 ^ 144 + 38 - 107 + 96;
        o.d[7] = -" ".length();
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    public final String toString() {
        String string;
        if (o.f(this.c) && o.e(this.c, by.h.length)) {
            string = "T\u00e0n s\u00e1t " + by.h[this.c].j;
            "".length();
            if (" ".length() <= 0) {
                return null;
            }
        } else {
            string = "T\u00e0n s\u00e1t";
        }
        return string;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void a() {
        if (!o.c(this.u, d[0]) || !o.c(this.u, d[1]) || !o.c(this.u, d[2]) || o.d(this.u, d[3])) {
            bf.f = this.u;
        }
        if (o.a(ax.g() ? 1 : 0)) {
            if (o.a(ec.cs ? 1 : 0)) {
                ax.a(d[4]);
                "".length();
                if ((0x44 ^ 0x35 ^ (0xED ^ 0x99)) <= 0) {
                    return;
                }
            }
        } else {
            Calendar calendar = fo.a();
            int n2 = calendar.get(d[5]);
            int n3 = calendar.get(d[6]);
            if (o.a(fa.g ? 1 : 0) && o.d(n2, fa.d) && o.d(n3, fa.a) && o.f(o.a(System.currentTimeMillis() - fa.f, 60000L))) {
                ey.d();
                fa.f = System.currentTimeMillis();
                return;
            }
            if (o.e(ax.ab ? 1 : 0) && o.a(ec.n().cb ? 1 : 0)) {
                this.j();
                return;
            }
            if (!o.d(this.u, de.i) || o.e(this.t ? 1 : 0) && o.c(this.x, de.ab)) {
                this.a(this.u, this.x, this.p, this.s);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                this.b(this.c, this.a(ec.dn, ec.ea, ec.eg));
                this.d(d[7]);
            }
        }
    }

    private static boolean e(int n2) {
        return n2 == 0;
    }

    private static boolean e(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean f(int n2) {
        return n2 >= 0;
    }

    public final void a(int n2, int n3, int n4) {
        super.c();
        this.u = n3;
        this.x = n4;
        this.t = de.n(n3);
        this.c = n2;
    }
}

