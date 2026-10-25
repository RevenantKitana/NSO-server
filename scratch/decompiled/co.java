/*
 * Decompiled with CFR 0.152.
 */
public final class co
extends ax {
    private long a;
    private static final int[] b;

    static {
        co.f();
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    private static void f() {
        b = new int[5];
        co.b[0] = -" ".length();
        co.b[1] = 162 + 145 - 285 + 176 ^ 13 + 57 - 41 + 99;
        co.b[2] = -(0xFFFFF866 & 0x3F9B) & (0xFFFFFFFD & 0x3DDF);
        co.b[3] = -(0xFFFFFD5F & 0x13AB) & (0xFFFFFBBA & 0x175F);
        co.b[4] = " ".length();
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    public final void e() {
        super.c();
        if (!co.c(ey.l, b[0]) || !co.c(ey.ai, b[0]) || co.d(ey.r, b[0])) {
            ey.l = b[1];
            ey.ai = b[2];
            ey.r = b[3];
        }
        this.u = ey.l;
        this.x = ey.b;
        this.t = de.n(ey.l);
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final String toString() {
        return "Auto \u0111\u1ee9ng ch\u1edd pk";
    }

    protected final void a() {
        block13: {
            block14: {
                if (!co.e(co.g() ? 1 : 0)) break block14;
                co.a(b[4]);
                try {
                    Thread.sleep(1500L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    "".length();
                    if (-"   ".length() >= 0) {
                        return;
                    }
                    break block13;
                }
                if ("  ".length() < 0) {
                    return;
                }
                break block13;
            }
            if (co.d(this.u, de.i) && co.d(this.x, de.ab)) {
                if (!co.d(ec.n().s, ey.ai) || co.c(ec.n().fc, ey.r)) {
                    if (co.c(ey.ai, b[0]) && co.c(ey.r, b[0])) {
                        ec.d(ey.ai, ey.r);
                        "".length();
                        "".length();
                        if ("  ".length() <= 0) {
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
                ey.r();
                if (co.a(co.a(System.currentTimeMillis() - this.a, 30000L))) {
                    ey ey2 = ey.aa;
                    ey.i("chao a. e dung day tu chieu");
                    this.a = System.currentTimeMillis();
                    return;
                }
            } else {
                this.a(this.u, this.x, this.p, this.s);
            }
        }
    }
}

