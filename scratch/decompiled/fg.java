/*
 * Decompiled with CFR 0.152.
 */
public final class fg
extends ax {
    private static final int[] a;
    private long b = 0L;
    private boolean c;
    private boolean d;

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    static {
        fg.e();
    }

    public final String toString() {
        String string;
        if (fg.e(this.d ? 1 : 0) && fg.e(this.c ? 1 : 0)) {
            return "Buff HS Xa";
        }
        if (fg.e(this.c ? 1 : 0)) {
            string = "HSinh Xa";
            "".length();
            if ("   ".length() <= "  ".length()) {
                return null;
            }
        } else {
            string = "Buff Xa";
        }
        return string;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static void e() {
        a = new int[16];
        fg.a[0] = " ".length();
        fg.a[1] = 0x10 ^ 0x16;
        fg.a[2] = (0xB ^ 0x63 ^ (0x23 ^ 0x4C)) & (0x1C ^ 0x31 ^ (0x16 ^ 0x3C) ^ -" ".length());
        fg.a[3] = 0x9C ^ 0x98;
        fg.a[4] = "  ".length();
        fg.a[5] = 0x32 ^ 0x60 ^ (0x7D ^ 0x6C);
        fg.a[6] = 0x47 ^ 0x32 ^ (0x91 ^ 0xAC);
        fg.a[7] = 0x20 ^ 0x2B ^ (0x9D ^ 0xB9);
        fg.a[8] = 0xF6 ^ 0x92;
        fg.a[9] = -" ".length();
        fg.a[10] = 0xC7 ^ 0x8E ^ (0xFD ^ 0xB1);
        fg.a[11] = 0xFFFF85DF & 0x7B21;
        fg.a[12] = "   ".length();
        fg.a[13] = 0x7E ^ 0x60;
        fg.a[14] = 0xA4 ^ 0xA2 ^ (0xE ^ 6);
        fg.a[15] = 0xCA ^ 0xC5;
    }

    public final void a(int n2, int n3, boolean bl2, boolean bl3) {
        super.c();
        this.u = n2;
        this.x = n3;
        this.t = de.n(n2);
        this.d = bl2;
        this.c = bl3;
        this.j = a[0];
    }

    private static boolean e(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static boolean f(int n2) {
        return n2 <= 0;
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    private static boolean f(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean g(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean h(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean h(int n2) {
        return n2 > 0;
    }

    public final void a() {
        if (fg.e(ax.g() ? 1 : 0)) {
            ax.a(a[0]);
            "".length();
            if (-" ".length() == (0xB0 ^ 0xB4)) {
                return;
            }
        } else if (fg.f(this.u, de.i) && (!fg.g(de.n(this.u) ? 1 : 0) || fg.f(this.x, de.ab))) {
            ec ec2;
            ec ec3;
            Object object;
            int n2;
            cv cv2;
            int n3;
            ec ec4 = ec.n();
            if (fg.e(this.c ? 1 : 0) && (!fg.e(ap.an.size(), a[0]) || fg.h(ey.au.size())) && fg.f(ec4.dk.c, a[1])) {
                n3 = a[2];
                while (fg.h(n3, ec4.ao.size())) {
                    cv2 = (cv)ec4.ao.elementAt(n3);
                    if (fg.a(cv2) && fg.f(cv2.k.b, a[3])) {
                        if (!fg.h(fg.a(System.currentTimeMillis() - this.b, 3000L))) break;
                        n2 = a[2];
                        while (fg.h(n2, ap.an.size())) {
                            object = (dg)ap.an.elementAt(n2);
                            if (fg.g(((dg)object).d, ec4.w) && fg.a(((dg)object).b) && fg.f(((dg)object).b.cl)) {
                                int n4 = ec4.s;
                                int n5 = ec4.fc;
                                ec ec5 = ((dg)object).b;
                                ec.d(ec5.s, ec5.fc);
                                "".length();
                                ax.a(500L);
                                if (fg.e(ax.b(ec5) ? 1 : 0)) {
                                    this.b = System.currentTimeMillis();
                                    dl.h().am(((dg)object).d);
                                    cv2.c = System.currentTimeMillis();
                                    cv2.f = a[0];
                                    ec4.b(ap.ch[cv2.k.d], a[2]);
                                    ax.a(1000L);
                                }
                                ec.d(n4, n5);
                                "".length();
                                return;
                            }
                            ++n2;
                            "".length();
                            if (null == null) continue;
                            return;
                        }
                        n2 = a[2];
                        while (fg.h(n2, ap.dn.size())) {
                            ec3 = (ec)ap.dn.elementAt(n2);
                            if (fg.a(ec3) && fg.e(ax.b(ec3) ? 1 : 0) && fg.e(ey.d(ec3.cx) ? 1 : 0)) {
                                int n6 = ec4.s;
                                int n7 = ec4.fc;
                                ec.d(ec3.s, ec3.fc);
                                "".length();
                                ax.a(500L);
                                if (fg.e(ax.b(ec3) ? 1 : 0)) {
                                    this.b = System.currentTimeMillis();
                                    dl.h().am(ec3.w);
                                    cv2.c = System.currentTimeMillis();
                                    cv2.f = a[0];
                                    ec4.b(ap.ch[cv2.k.d], a[2]);
                                    ax.a(1000L);
                                }
                                ec.d(n6, n7);
                                "".length();
                                return;
                            }
                            ++n2;
                            "".length();
                            if ((0x61 ^ 0x5F ^ (0x3D ^ 7)) == (0xEC ^ 0xC3 ^ (0xA4 ^ 0x8F))) continue;
                            return;
                        }
                        break;
                    }
                    ++n3;
                    "".length();
                    if (-" ".length() < "  ".length()) continue;
                    return;
                }
            }
            if (fg.h(ap.an.size())) {
                ec2 = ((dg)ap.an.firstElement()).b;
                "".length();
                if (((0xE ^ 0x12) & ~(0xF ^ 0x13)) != 0) {
                    return;
                }
            } else {
                ec2 = ec3 = null;
            }
            if (fg.e(this.d ? 1 : 0) && fg.e(this.i() ? 1 : 0) && fg.a(ec3) && fg.f(ec4.dk.c, a[1])) {
                n3 = a[2];
                while (fg.h(n3, ec4.ao.size())) {
                    cv2 = (cv)ec4.ao.elementAt(n3);
                    if (fg.a(cv2) && fg.g(cv2.a() ? 1 : 0) && fg.f(cv2.k.b, a[4]) && (!fg.d(cv2.k.d, a[5]) || fg.c(cv2.k.d, a[6])) && (!fg.f(cv2.k.d, a[7]) || fg.h(ec3.cl, ec3.fd * ec.ec / a[8]))) {
                        int n8 = a[2];
                        while (fg.h(n8, ec3.dy.size())) {
                            ec3.dy.elementAt(n8);
                            "".length();
                            ++n8;
                            "".length();
                            if (((0x31 ^ 0xB) & ~(0x59 ^ 0x63)) == 0) continue;
                            return;
                        }
                        n8 = ec4.s;
                        int n9 = ec4.fc;
                        ec.d(ec3.s, ec3.fc);
                        "".length();
                        dl.h().e(cv2.k.d);
                        dl.h().c();
                        cv2.c = System.currentTimeMillis();
                        cv2.f = a[0];
                        ec4.b(ap.ch[cv2.k.d], a[2]);
                        ax.a(1000L);
                        ec.d(n8, n9);
                        "".length();
                        return;
                    }
                    ++n3;
                    "".length();
                    if (((0x4D ^ 0x6F ^ (0x43 ^ 0x7E)) & (39 + 128 - 160 + 142 ^ 89 + 28 - 103 + 124 ^ -" ".length())) == 0) continue;
                    return;
                }
            }
            if (fg.e(ey.am ? 1 : 0)) {
                int n10;
                int n11;
                ec ec6 = ec4.fm;
                if ((!fg.a(ec6) || fg.g(fs.a(ec6.cx) ? 1 : 0) && fg.g(ax.a(ec4, ec6) ? 1 : 0)) && fg.b((Object)(ec6 = this.a(ec4, a[9])))) {
                    ec6 = ax.d(ec4);
                }
                if (fg.a(ec6) && fg.e(fs.a(ec6.cx) ? 1 : 0)) {
                    n11 = a[0];
                    "".length();
                    if ((0x46 ^ 0x17 ^ (0x44 ^ 0x11)) == 0) {
                        return;
                    }
                } else {
                    n11 = n10 = a[2];
                }
                if (fg.b((Object)ec6) && fg.e(this.v ? 1 : 0)) {
                    dl.h().al(a[2]);
                    this.v = a[2];
                }
                if (fg.d(ec4.dp, a[10]) && fg.h(fg.a(System.currentTimeMillis() - this.k, 5000L))) {
                    object = ec.b(a[11]);
                    if (fg.a(object) && fg.f(((el)object).m.d, a[11])) {
                        dl.h().u(((el)object).n);
                    }
                    this.k = System.currentTimeMillis();
                }
                if (fg.a(ec6) && fg.a(ec6) && fg.g(ax.b(ec6) ? 1 : 0) && (!fg.g(n10) || fg.e(ax.a(ec4, ec6) ? 1 : 0))) {
                    object = ax.z;
                    if (fg.e(n10)) {
                        if (!(fg.g(((cv)object).k.b, a[0]) && !fg.f(((cv)object).k.b, a[12]) || fg.e(fo.d(ec4.s - ec6.s), ((cv)object).i + a[13]) && !fg.c(fo.d(ec4.fc - ec6.fc), ((cv)object).a + a[13]) || !fg.h(fg.a(System.currentTimeMillis() - this.i, 1500L)))) {
                            ax.c(ec6);
                            this.i = System.currentTimeMillis();
                        }
                        if (fg.g(ec6.gk, ec4.w) && fg.g(ec6.ej, a[12])) {
                            this.v = a[0];
                            dl.h().al(a[12]);
                        }
                    }
                    int n12 = ((cv)object).i;
                    int n13 = ((cv)object).a;
                    ax.n.removeAllElements();
                    ax.w.removeAllElements();
                    ax.w.addElement(ec6);
                    n2 = a[2];
                    while (fg.h(n2, ap.dn.size()) && fg.h(ax.n.size() + ax.w.size(), ((cv)object).j)) {
                        ec ec7 = (ec)ap.dn.elementAt(n2);
                        if (fg.h(ec7.cl) && fg.g(ec7.fj, a[14]) && fg.g(ec7.fj, a[10]) && fg.g(ec7.fj, a[15]) && fg.g(ec7.equals(ec6) ? 1 : 0) && (!fg.g(ec7.ej, a[12]) || !fg.g(ec4.ej, a[12]) || fg.f(ec7.ej, a[0]) && !fg.g(ec4.ej, a[0]) || fg.a(ec4.gk) && !fg.g(ec4.gk, ec7.w) || fg.a(ec4.ge) && fg.f(ec4.ge, ec7.w)) && fg.g(ey.f(ec7.cx) ? 1 : 0) && fg.e(ec6.s - n12, ec7.s) && fg.e(ec7.s, ec6.s + n12) && fg.e(ec6.fc - n13, ec7.fc) && fg.e(ec7.fc, ec6.fc + n13)) {
                            ax.w.addElement(ec7);
                        }
                        ++n2;
                        "".length();
                        if (" ".length() > 0) continue;
                        return;
                    }
                    dl.h().a(ax.n, ax.w, a[4]);
                    if (fg.a(fg.a(System.currentTimeMillis() - ((cv)object).c, (long)((cv)object).g))) {
                        ((cv)object).c = System.currentTimeMillis();
                        ((cv)object).f = a[0];
                        if (fg.g(ey.bf ? 1 : 0)) {
                            ec4.b(ap.ch[((cv)object).k.d], a[2]);
                        }
                    }
                    this.y = System.currentTimeMillis();
                    return;
                }
            }
            "".length();
            if (-" ".length() > " ".length()) {
                return;
            }
        } else {
            this.a(this.u, this.x, a[9], a[9]);
        }
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean b(Object object) {
        return object == null;
    }
}

