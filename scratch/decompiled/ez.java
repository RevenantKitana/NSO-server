/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class ez
extends ax {
    private boolean[] a;
    private static final int[] b;
    private int c;
    private int d;

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean e(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static boolean f(int n2) {
        return n2 <= 0;
    }

    public ez() {
        this.u = b[0];
        this.t = b[1];
        this.x = b[2];
        this.c = b[3];
        this.d = b[4];
        this.a = new boolean[b[5]];
    }

    private static boolean g(int n2) {
        return n2 >= 0;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean h(int n2) {
        return n2 == 0;
    }

    private static boolean f(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean g(int n2, int n3) {
        return n2 > n3;
    }

    public final String toString() {
        return "Hang 9x Lvl " + this.d;
    }

    static {
        ez.e();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a() {
        block46: {
            by by2;
            int n2;
            Object object;
            by by3;
            ec ec2;
            block47: {
                Object object2;
                block51: {
                    int n3;
                    block52: {
                        block53: {
                            int n4;
                            block45: {
                                block50: {
                                    block49: {
                                        by by4;
                                        if (ez.e(ax.g() ? 1 : 0)) {
                                            ax.a(b[1]);
                                            "".length();
                                            return;
                                        }
                                        if (ez.f(this.u, de.i)) {
                                            this.a(this.u, this.x, this.p, this.s);
                                            "".length();
                                            if ("   ".length() > 0) return;
                                            return;
                                        }
                                        ec2 = ec.n();
                                        if (!ez.h(ax.l() ? 1 : 0)) break block46;
                                        by3 = ec2.di;
                                        if (ez.b((Object)by3) && ez.e(by3.p) && ez.e(by3.j ? 1 : 0) && !ez.a(ez.a(System.currentTimeMillis() - this.y, 5000L))) break block49;
                                        object = this;
                                        object2 = ap.au;
                                        n2 = b[4];
                                        while (true) {
                                            if (ez.h(n2, ((Vector)object2).size())) {
                                                by4 = null;
                                                "".length();
                                                if (" ".length() <= " ".length()) break;
                                                return;
                                            }
                                            by2 = (by)((ad)object2).elementAt(n2);
                                            if (ez.b((Object)by2) && ez.a(by2.v) && ez.e(by2.p) && ez.f(by2.p, b[1]) && ez.c(by2.g, ((ez)object).c) && ez.c(by2.aa, ((ez)object).d)) {
                                                by4 = by2;
                                                "".length();
                                                break;
                                            }
                                            ++n2;
                                            "".length();
                                        }
                                        by3 = by4;
                                        if (ez.a(by4) && ez.a(ez.a(System.currentTimeMillis() - this.y, 1000L)) && ez.h(this.a_() ? 1 : 0)) break block50;
                                    }
                                    if (!ez.e(ec.ct ? 1 : 0) || !ez.a(ap.an.size()) || !ez.c(ec2.dk.c, b[12]) || !ez.a(ec2.cl)) break block51;
                                    n3 = b[4];
                                    break block52;
                                }
                                object2 = ap.au;
                                n2 = b[4];
                                do {
                                    if (ez.h(n2, ((Vector)object2).size())) {
                                        n4 = b[1];
                                        "".length();
                                        break block45;
                                    }
                                    by2 = (by)((ad)object2).elementAt(n2);
                                    if (ez.b((Object)by2) && ez.a(by2.v) && ez.e(by2.p) && ez.f(by2.p, b[1])) {
                                        n4 = b[4];
                                        "".length();
                                        if (-" ".length() >= 0) {
                                            return;
                                        }
                                        break block45;
                                    }
                                    ++n2;
                                    "".length();
                                } while (((0x41 ^ 0x63 ^ (0xF6 ^ 0x9B)) & (160 + 223 - 308 + 162 ^ 66 + 104 - 20 + 12 ^ -" ".length())) == 0);
                                return;
                            }
                            if (!ez.e(n4)) break block53;
                            this.a[this.c - ez.b[6]] = b[1];
                            if (!ez.e(this.a[b[4]]) || !ez.e(this.a[b[1]]) || !ez.e(this.a[b[7]])) break block53;
                            ey.i();
                            "".length();
                            if ("  ".length() <= ((38 + 25 - -50 + 92 ^ 45 + 63 - 65 + 88) & (0x9B ^ 0xA5 ^ (0x68 ^ 0x18) ^ -" ".length()))) {
                                return;
                            }
                            break block46;
                        }
                        switch (this.c) {
                            case 198: {
                                this.c = b[3];
                                this.u = b[0];
                                if (ez.h(this.d)) {
                                    this.d = b[8];
                                    "".length();
                                    if (-" ".length() >= 0) {
                                        return;
                                    }
                                }
                                break block46;
                            }
                            case 199: {
                                this.c = b[9];
                                this.u = b[10];
                                "".length();
                                if ("   ".length() == 0) {
                                    return;
                                }
                                break block46;
                            }
                            case 200: {
                                this.c = b[6];
                                this.u = b[11];
                            }
                            default: {
                                "".length();
                                if (((0xB1 ^ 0xB6) & ~(0x6E ^ 0x69)) <= -" ".length()) {
                                    return;
                                }
                                break block46;
                            }
                        }
                    }
                    while (ez.e(n3, ec2.ao.size())) {
                        int n5;
                        object2 = (cv)ec2.ao.elementAt(n3);
                        if (ez.b(object2) && ez.c(((cv)object2).k.b, b[8])) {
                            if (!ez.h(((cv)object2).a() ? 1 : 0)) break;
                            n5 = b[4];
                        } else {
                            ++n3;
                            "".length();
                            if ("  ".length() < (0x80 ^ 0x84)) continue;
                            return;
                        }
                        while (ez.e(n5, ap.an.size())) {
                            dg dg2 = (dg)ap.an.elementAt(n5);
                            if (ez.f(dg2.d, ec2.w) && ez.b((Object)dg2.b) && ez.f(dg2.b.cl)) {
                                ec ec3 = dg2.b;
                                if (!ez.d(Math.abs(ec2.s - ec3.s), b[13]) || ez.g(Math.abs(ec2.fc - ec3.fc), b[13])) {
                                    ec.d(ec3.s, ec3.fc);
                                    "".length();
                                }
                                ax.a(500L);
                                dl.h().am(dg2.d);
                                ((cv)object2).c = System.currentTimeMillis();
                                ((cv)object2).f = b[1];
                                ec2.b(ap.ch[((cv)object2).k.d], b[4]);
                                ax.a(1000L);
                                "".length();
                                break block46;
                            }
                            ++n5;
                            "".length();
                        }
                        "".length();
                        break;
                    }
                }
                if (!ez.b(ax.z) || !ez.b((Object)by3) || !ez.e(by3.j ? 1 : 0) || !ez.c(by3.g, this.c) || !ez.c(by3.aa, this.d)) break block46;
                object = ax.z;
                if (!ez.e(((cv)object).a() ? 1 : 0) || !ez.e(ec.es ? 1 : 0)) break block47;
                n2 = b[4];
                block9: while (true) {
                    block48: {
                        block55: {
                            block54: {
                                if (ez.h(n2, ec2.ao.size())) {
                                    "".length();
                                    break block47;
                                }
                                object2 = (cv)ec2.ao.elementAt(n2);
                                if (!ez.b(object2) || !ez.g(ez.a(System.currentTimeMillis() - ((cv)object2).c, (long)((cv)object2).g - 300L))) break block48;
                                if (!ez.c(((cv)object2).k.b, b[7])) break block54;
                                if ((!ez.a(ec2.aq) || ez.h(ec.av ? 1 : 0)) && ez.h(((cv)object2).k.d, b[14]) && !ez.g(((cv)object2).k.d, b[15]) || ez.h(ec.bh ? 1 : 0) && !ez.f(((cv)object2).k.d, b[16]) || ez.c(((cv)object2).k.d, b[17]) && !ez.e(ec2.cl, ec2.fd * ec.ec / b[18])) break block48;
                                break block55;
                            }
                            if (ez.c(((cv)object2).k.b, b[5]) && ez.h(by3.aa) && ez.g(by3.v, by3.a / b[7])) {
                                if (!ez.c(((cv)object2).k.d, b[8])) break;
                                if (ez.e(ec.fw ? 1 : 0) && ez.e(ec2.cl, ec2.fd * ec.ec / b[18])) {
                                    "".length();
                                    if (-"   ".length() < 0) break;
                                    return;
                                }
                                break block48;
                            } else if (!(ez.f(((cv)object2).k.d, b[20]) && ez.f(((cv)object2).k.d, b[21]) && ez.f(((cv)object2).k.d, b[22]) && ez.f(((cv)object2).k.d, b[23]) && !ez.c(((cv)object2).k.d, b[24]) || ez.h(by3.aa) && !ez.h(by3.v, by3.a / b[7]) || (!ez.f(((cv)object2).k.d, b[20]) || ez.c(((cv)object2).k.d, b[21])) && !ez.h(by3.l ? 1 : 0) || (!ez.f(((cv)object2).k.d, b[22]) || ez.c(((cv)object2).k.d, b[23])) && !ez.e(by3.r ? 1 : 0))) {
                                if (!ez.c(((cv)object2).k.d, b[24])) break;
                                if (ez.e(by3.u ? 1 : 0)) {
                                    "".length();
                                    if (((0xE2 ^ 0x9E ^ (0x1C ^ 0x3E)) & (189 + 209 - 280 + 104 ^ 58 + 4 - 2 + 68 ^ -" ".length())) == ((0x39 ^ 0x72 ^ "   ".length()) & (0x8A ^ 0xC1 ^ "   ".length() ^ -" ".length()))) break;
                                    return;
                                }
                            }
                            break block48;
                        }
                        int n6 = (int)(System.currentTimeMillis() / 1000L);
                        int n7 = b[4];
                        do {
                            if (ez.h(n7, ec2.dy.size())) {
                                "".length();
                                break block9;
                            }
                            au au2 = (au)ec2.dy.elementAt(n7);
                            if (ez.b(au2) && (!ez.f(au2.a.a, ((cv)object2).k.f) || ez.c(((cv)object2).k.d, b[19]) && ez.c(au2.a.c, b[20])) && ez.h(au2.c - (n6 - au2.d), b[7])) {
                                "".length();
                                "".length();
                                if (-" ".length() <= "  ".length()) break block48;
                                return;
                            }
                            ++n7;
                            "".length();
                        } while ("   ".length() < (0x1A ^ 0x1E));
                        return;
                    }
                    ++n2;
                    "".length();
                }
                object = object2;
                ax.a(500L);
            }
            if (!(ez.f(((cv)object).k.b, b[1]) && !ez.c(((cv)object).k.b, b[5]) || ez.d(fo.d(ec2.s - by3.y), ((cv)object).i) && !ez.g(fo.d(ec2.fc - by3.x), ((cv)object).a))) {
                this.a(by3);
            }
            dl.h().e(((cv)object).k.d);
            if (ez.c(((cv)object).k.b, b[7])) {
                dl.h().c();
                "".length();
                if (-" ".length() == ((0x61 ^ 0x2D ^ (0x60 ^ 0x77)) & (3 + 59 - -120 + 44 ^ 75 + 64 - -35 + 11 ^ -" ".length()))) {
                    return;
                }
            } else {
                ax.n.removeAllElements();
                ax.w.removeAllElements();
                if (ez.b((Object)by3)) {
                    ax.n.addElement(by3);
                    n2 = b[4];
                    while (ez.e(n2, ap.au.size()) && ez.e(ax.n.size() + ax.w.size(), ((cv)object).j)) {
                        by2 = (by)ap.au.elementAt(n2);
                        if (ez.e(by2.p) && ez.f(by2.p, b[1]) && ez.h(by2.equals(by3) ? 1 : 0) && ez.d(by3.y - b[18], by2.y) && ez.d(by2.y, by3.y + b[18]) && ez.d(by3.x - b[13], by2.x) && ez.d(by2.x, by3.x + b[13])) {
                            ax.n.addElement(by2);
                        }
                        ++n2;
                        "".length();
                    }
                }
                dl.h().a(ax.n, ax.w, b[1]);
            }
            if (ez.g(ez.a(System.currentTimeMillis() - ((cv)object).c, (long)((cv)object).g))) {
                ((cv)object).c = System.currentTimeMillis();
                ((cv)object).f = b[1];
                if (ez.h(ey.bf ? 1 : 0)) {
                    ec2.b(ap.ch[((cv)object).k.d], b[4]);
                }
            }
            this.y = System.currentTimeMillis();
            if (ez.c(((cv)object).k.d, b[17])) {
                ax.a(2000L);
            }
        }
        this.d(b[25]);
    }

    private static void e() {
        b = new int[26];
        ez.b[0] = (0x83 ^ 0xBB) + (0x6E ^ 0x23) - (0x5D ^ 0x6A) + (0x4A ^ 5);
        ez.b[1] = " ".length();
        ez.b[2] = -"  ".length();
        ez.b[3] = (0x79 ^ 0x64) + (75 + 90 - 110 + 90) - (85 + 150 - 233 + 160) + (10 + 145 - 129 + 161);
        ez.b[4] = (0x89 ^ 0x91 ^ (0x39 ^ 0x6D)) & (0xE1 ^ 0xAD ^ (0x6F ^ 0xD) & ~(0x2A ^ 0x48) ^ -" ".length());
        ez.b[5] = "   ".length();
        ez.b[6] = (0xA ^ 0x53) + (0x51 ^ 0x11) - (0xBC ^ 0xAB) + (0xCA ^ 0x8E);
        ez.b[7] = "  ".length();
        ez.b[8] = 0x1B ^ 0x1F;
        ez.b[9] = 45 + 81 - -32 + 42;
        ez.b[10] = 72 + 71 - 129 + 134 + (0x6A ^ 0x32) - (127 + 15 - 23 + 93) + (19 + 5 - -42 + 68);
        ez.b[11] = 52 + 78 - 111 + 140;
        ez.b[12] = 59 + 146 - 92 + 37 ^ 128 + 68 - 66 + 14;
        ez.b[13] = 18 + 24 - -123 + 1 ^ 46 + 43 - 17 + 76;
        ez.b[14] = 126 + 120 - 236 + 214 ^ 126 + 84 - 80 + 33;
        ez.b[15] = 0xAD ^ 0xBE ^ (0x10 ^ 0x4B);
        ez.b[16] = 43 + 87 - 96 + 187 ^ 92 + 178 - 101 + 25;
        ez.b[17] = 0xCD ^ 0xC2;
        ez.b[18] = 0x5A ^ 0x3E;
        ez.b[19] = 0x59 ^ 0x63;
        ez.b[20] = 0x51 ^ 0x56;
        ez.b[21] = 123 + 31 - 102 + 77 ^ 14 + 70 - -35 + 26;
        ez.b[22] = 0xD ^ 0x14;
        ez.b[23] = 0xAA ^ 0x8B ^ "   ".length();
        ez.b[24] = 0xFA ^ 0xB2 ^ (0x74 ^ 0x17);
        ez.b[25] = -" ".length();
    }

    private static boolean h(int n2, int n3) {
        return n2 >= n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean b(Object object) {
        return object != null;
    }
}

