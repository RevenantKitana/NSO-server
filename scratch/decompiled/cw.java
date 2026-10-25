/*
 * Decompiled with CFR 0.152.
 */
public final class cw
extends ax {
    private ad c;
    private int d;
    public int a;
    private ad e;
    public int b;
    private static final int[] ah;
    private long ai;

    private static boolean a(int n2) {
        return n2 <= 0;
    }

    protected final void a() {
        if (cw.a(ec.n().cl)) {
            ax.a(ah[2]);
            "".length();
            if (null != null) {
                return;
            }
        } else {
            switch (this.b) {
                case 0: {
                    if (cw.d(de.i, ah[3]) && cw.d(de.ab, ah[4])) {
                        bo.y();
                        this.b = ah[5];
                        return;
                    }
                    this.a(ah[3], ah[4], ah[1], ah[1]);
                    return;
                }
                case 1: {
                    if (cw.g(cw.a(System.currentTimeMillis() - this.ai, 180000L))) {
                        ey.i();
                        return;
                    }
                    if (cw.d(de.i, ah[3]) && !cw.c(de.ab, ah[4])) break;
                    this.a(ah[3], ah[4], ah[1], ah[1]);
                    return;
                }
                case 2: {
                    if (cw.g(this.e.size())) {
                        int n2 = this.e.size() - ah[2];
                        this.u = (Integer)this.e.elementAt(n2);
                        this.a = (Integer)this.c.elementAt(n2);
                        this.e.removeElementAt(n2);
                        this.c.removeElementAt(n2);
                        this.b = ah[6];
                        dl.h().l("pkms " + this.u + " " + this.a);
                        return;
                    }
                    this.b = ah[7];
                    dl.h().l("pkes");
                    return;
                }
                case 3: {
                    if (!cw.g(this.a)) break;
                    if (cw.c(de.i, this.a)) {
                        this.a(this.a, ah[8], ah[1], ah[1]);
                        return;
                    }
                    ap.b(ah[9], ah[2], ah[0]);
                    if (!cw.e(bo.f() ? 1 : 0)) break;
                    this.b = ah[9];
                    ey.a(new aq(this.u));
                    return;
                }
                case 4: {
                    if (cw.g(this.d)) {
                        if (cw.c(de.i, this.d)) {
                            this.a(this.d, ah[8], ah[1], ah[1]);
                            return;
                        }
                        ap.b(ah[9], ah[2], ah[0]);
                        if (!cw.e(bo.f() ? 1 : 0)) break;
                        ey.i();
                        return;
                    }
                    this.d = ah[10];
                    "".length();
                    if (-"  ".length() < 0) break;
                    return;
                }
                case 5: {
                    ax.a(10000L);
                    if (cw.a(ey.v) && cw.f(ec.n().cx.equals(ey.v) ? 1 : 0)) {
                        this.b = ah[2];
                        this.ai = System.currentTimeMillis();
                        return;
                    }
                    this.b = ah[5];
                    return;
                }
            }
        }
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean f(int n2) {
        return n2 == 0;
    }

    public cw(ad ad2, ad ad3, int n2) {
        this.b = ah[0];
        this.a = ah[1];
        this.d = n2;
        this.e = ad2;
        this.c = ad3;
    }

    private static boolean g(int n2) {
        return n2 > 0;
    }

    public cw(int n2) {
        this.b = ah[2];
        this.a = ah[1];
        this.d = n2;
        this.ai = System.currentTimeMillis();
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    static {
        cw.e();
    }

    private static void e() {
        ah = new int[11];
        cw.ah[0] = (0xE8 ^ 0x9E ^ (0x6A ^ 0x4A)) & (0x12 ^ 0x56 ^ (1 ^ 0x13) ^ -" ".length());
        cw.ah[1] = -" ".length();
        cw.ah[2] = " ".length();
        cw.ah[3] = 178 + 79 - 96 + 24 ^ 147 + 71 - 214 + 155;
        cw.ah[4] = 0xB ^ 0x1E;
        cw.ah[5] = "  ".length();
        cw.ah[6] = "   ".length();
        cw.ah[7] = 1 + 128 - 14 + 81 ^ 41 + 163 - 137 + 125;
        cw.ah[8] = -"  ".length();
        cw.ah[9] = 0xC2 ^ 0xC7;
        cw.ah[10] = 0x61 ^ 0x34 ^ (0x1B ^ 0x58);
    }

    public final String toString() {
        return "PKBoss S";
    }
}

