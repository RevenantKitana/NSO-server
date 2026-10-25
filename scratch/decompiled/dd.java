/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class dd
extends ax {
    private static long a;
    private long b;
    private static final int[] c;

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    protected final void a() {
        block17: {
            block18: {
                if (!dd.a(dd.g() ? 1 : 0)) break block18;
                dd.a(c[4]);
                try {
                    Thread.sleep(1500L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    "".length();
                    if ((0x92 ^ 0x96) <= "  ".length()) {
                        return;
                    }
                    break block17;
                }
                if ("  ".length() < "  ".length()) {
                    return;
                }
                break block17;
            }
            if (dd.f(this.u, de.i) && dd.f(this.x, de.ab)) {
                Object object;
                if (!dd.f(ec.n().s, ey.ai) || dd.e(ec.n().fc, ey.r)) {
                    if (dd.e(ey.ai, c[0]) && dd.e(ey.r, c[0])) {
                        ec.d(ey.ai, ey.r);
                        "".length();
                        "".length();
                        if ((8 ^ 0xC) <= ((0x1B ^ 0x37) & ~(0xBF ^ 0x93))) {
                            return;
                        }
                    } else {
                        ey.ai = ec.n().s;
                        ey.r = ec.n().fc;
                    }
                    try {
                        Thread.sleep(500L);
                        return;
                    }
                    catch (InterruptedException interruptedException) {
                        return;
                    }
                }
                if (dd.f(dd.a(System.currentTimeMillis() - a, 300L))) {
                    object = new ad();
                    int n2 = c[5];
                    while (dd.c(n2, ap.dn.size())) {
                        ec ec2 = (ec)ap.dn.elementAt(n2);
                        if (dd.e(ec2.cl) && dd.f(ec2.ej, c[6]) && dd.d(Math.abs(ec.n().s - ec2.s), c[7]) && dd.d(Math.abs(ec.n().fc - ec2.fc), c[7]) && dd.d(((Vector)object).size(), dd.z.j)) {
                            ((ad)object).addElement(ec2);
                            if (dd.e(ec2.cl) && dd.f(ec2.ej, c[6]) && dd.d(Math.abs(ec.n().s - ec2.s), c[7])) {
                                dl.h().a(new ad(), (ad)object, c[8]);
                                a = System.currentTimeMillis();
                            }
                        }
                        ++n2;
                        "".length();
                        if ((0xAC ^ 0xA8) > " ".length()) continue;
                        return;
                    }
                }
                ey.r();
                if (dd.f(dd.a(System.currentTimeMillis() - this.b, 30000L))) {
                    object = ey.aa;
                    ey.i("chao e. a dung day tu chieu");
                    this.b = System.currentTimeMillis();
                    return;
                }
            } else {
                this.a(this.u, this.x, this.p, this.s);
            }
        }
    }

    public final String toString() {
        return "Auto \u0111\u00e1nh pk";
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void f() {
        c = new int[9];
        dd.c[0] = -" ".length();
        dd.c[1] = 0x47 ^ 0x50 ^ (0x15 ^ 0x44);
        dd.c[2] = -(0xFFFFF837 & 0x77EA) & (0xFFFFF5FF & 0x7FFD);
        dd.c[3] = -(0xFFFF9B89 & 0x75FF) & (0xFFFFFFDC & 0x13BB);
        dd.c[4] = " ".length();
        dd.c[5] = (0xF6 ^ 0x8D ^ (0xF8 ^ 0xA7)) & (34 + 37 - 57 + 157 ^ 38 + 133 - 102 + 74 ^ -" ".length());
        dd.c[6] = "   ".length();
        dd.c[7] = 0x7B ^ 8 ^ (0x6A ^ 0x2B);
        dd.c[8] = "  ".length();
    }

    private static boolean d(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean e(int n2) {
        return n2 > 0;
    }

    static {
        dd.f();
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean f(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean f(int n2) {
        return n2 >= 0;
    }

    public final void e() {
        super.c();
        if (!dd.e(ey.l, c[0]) || !dd.e(ey.ai, c[0]) || dd.f(ey.r, c[0])) {
            ey.l = c[1];
            ey.ai = c[2];
            ey.r = c[3];
        }
        this.u = ey.l;
        this.x = ey.g;
        this.t = de.n(ey.l);
    }
}

