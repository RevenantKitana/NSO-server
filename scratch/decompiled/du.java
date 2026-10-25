/*
 * Decompiled with CFR 0.152.
 */
public final class du
extends ax {
    private static boolean a;
    private long b;
    private long c;
    private long d = 0L;
    private static final int[] e;
    private long ah;
    private static boolean ai;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean e(int n2) {
        return n2 >= 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    public final void e() {
        super.c();
        ai = e[0];
        a = e[0];
    }

    public final String toString() {
        return "L\u00f4i win";
    }

    private static boolean f(int n2) {
        return n2 > 0;
    }

    public final void a() {
        block41: {
            block47: {
                block48: {
                    block44: {
                        block45: {
                            block42: {
                                block43: {
                                    if (!du.a(du.g() ? 1 : 0)) break block42;
                                    if (!du.g(de.i, e[1])) break block43;
                                    try {
                                        Thread.sleep(500L);
                                        "".length();
                                    }
                                    catch (InterruptedException interruptedException) {
                                        "".length();
                                        if (null != null) {
                                            return;
                                        }
                                        break block41;
                                    }
                                    if (((0x48 ^ 0x1B) & ~(0xDF ^ 0x8C)) != 0) {
                                        return;
                                    }
                                    break block41;
                                }
                                ax.a(e[2]);
                                "".length();
                                if ((0xB ^ 0x53 ^ (0x43 ^ 0x1E)) == 0) {
                                    return;
                                }
                                break block41;
                            }
                            if (!du.g(ai ? 1 : 0) || !du.g(a ? 1 : 0)) break block44;
                            if (du.g(de.i, e[3])) {
                                ai = e[2];
                                a = e[0];
                                return;
                            }
                            if (du.g(de.i, ey.ag) && du.g(de.ab, ey.f)) {
                                ds ds2 = ap.b(e[0]);
                                if (du.g(ec.n().s, ds2.s) && du.g(ec.n().fc, ds2.fc)) {
                                    int n2 = e[0];
                                    while (du.d(n2, ap.dn.size())) {
                                        String[] stringArray = ey.b(ey.ad, ",");
                                        int n3 = e[0];
                                        while (du.d(n3, stringArray.length)) {
                                            if (du.a(stringArray[n3].equals(((ec)ap.dn.elementAt((int)n2)).cx) ? 1 : 0) && du.e(du.a(System.currentTimeMillis() - this.c, 5000L))) {
                                                ap.b(e[0], e[4], e[0]);
                                                dl.h().a(e[5], stringArray[n3]);
                                                em.k();
                                                this.c = System.currentTimeMillis();
                                            }
                                            ++n3;
                                            "".length();
                                            if (" ".length() < (0x23 ^ 0x27)) continue;
                                            return;
                                        }
                                        ++n2;
                                        "".length();
                                        if (null == null) continue;
                                        return;
                                    }
                                    return;
                                }
                                ec.d(ds2.s, ds2.fc);
                                "".length();
                                try {
                                    Thread.sleep(1000L);
                                    return;
                                }
                                catch (InterruptedException interruptedException) {
                                    return;
                                }
                            }
                            if (!du.e(de.i, ey.ag)) break block45;
                            if (du.e(de.i, e[1]) && du.e(de.i, e[6]) && du.e(de.i, e[7])) {
                                this.a(ey.ag, ey.f, e[8], e[8]);
                                return;
                            }
                            try {
                                Thread.sleep(1000L);
                                "".length();
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                            if ("  ".length() == 0) {
                                return;
                            }
                            ax.h();
                            "".length();
                            if (((0x9B ^ 0xC5 ^ (0x13 ^ 6)) & (0x8A ^ 0xB7 ^ (0x49 ^ 0x3F) ^ -" ".length())) != 0) {
                                return;
                            }
                            break block41;
                        }
                        ax.c(ey.f);
                        "".length();
                        if ((0x9D ^ 0x98) == 0) {
                            return;
                        }
                        break block41;
                    }
                    if (!du.a(ai ? 1 : 0) || !du.g(a ? 1 : 0)) break block47;
                    if (du.g(de.i, e[1])) {
                        this.ah = System.currentTimeMillis();
                        ai = e[2];
                        a = e[2];
                        return;
                    }
                    if (!du.g(de.i, e[3])) break block41;
                    ds ds3 = ap.b(e[0]);
                    if (du.g(ec.n().s, ds3.s) && !du.e(ec.n().fc, ds3.fc)) break block48;
                    ec.d(ds3.s, ds3.fc);
                    "".length();
                    try {
                        Thread.sleep(1000L);
                        "".length();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    if ("  ".length() == "   ".length()) {
                        return;
                    }
                    this.d = System.currentTimeMillis();
                    return;
                }
                if (!du.e(du.a(System.currentTimeMillis() - this.d, 3000L))) break block41;
                ap.g(e[0]);
                dl.h().d(e[0], e[2], e[0]);
                dl.h().a(e[9], String.valueOf(ab.b));
                em.k();
                try {
                    Thread.sleep(3000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (" ".length() >= "  ".length()) {
                    return;
                }
                this.d = System.currentTimeMillis();
                "".length();
                if ("   ".length() < -" ".length()) {
                    return;
                }
                break block41;
            }
            if (du.a(ai ? 1 : 0) && du.a(a ? 1 : 0)) {
                if (du.e(de.i, e[1])) {
                    ai = e[0];
                    a = e[0];
                    this.ah = System.currentTimeMillis();
                    return;
                }
                if (du.e(du.a(System.currentTimeMillis() - this.ah, 59000L))) {
                    Object object;
                    Object object2 = null;
                    int n4 = e[0];
                    while (du.d(n4, ap.dn.size())) {
                        String[] stringArray = ey.b(ey.ad, ",");
                        int n5 = e[0];
                        while (du.d(n5, stringArray.length)) {
                            object = (ec)ap.dn.elementAt(n4);
                            if (du.a(((ec)object).cx.equals(stringArray[n5]) ? 1 : 0)) {
                                object2 = object;
                                "".length();
                                if (((0x7D ^ 0x5D ^ (0xB2 ^ 0x94)) & (28 + 97 - 114 + 120 ^ 68 + 75 - 16 + 6 ^ -" ".length())) == 0) break;
                                return;
                            }
                            ++n5;
                            "".length();
                            if (((22 + 32 - -7 + 75 ^ 97 + 102 - 26 + 18) & (0xEC ^ 0xA2 ^ (0xD3 ^ 0xAA) ^ -" ".length())) == ((0x25 ^ 0x51 ^ (0x3B ^ 0x72)) & (0x7A ^ 0x76 ^ (0x20 ^ 0x11) ^ -" ".length()))) continue;
                            return;
                        }
                        ++n4;
                        "".length();
                        if ("  ".length() == "  ".length()) continue;
                        return;
                    }
                    if (du.b(object2)) {
                        ai = e[0];
                        a = e[0];
                        this.ah = System.currentTimeMillis();
                        return;
                    }
                    if (du.a(ax.z) && du.f(((ec)object2).cl)) {
                        object = ax.z;
                        ec ec2 = ec.n();
                        if (du.g(((cv)object).k.b, e[10])) {
                            dl.h().e(((cv)object).k.d);
                            dl.h().c();
                            "".length();
                            if ("  ".length() < -" ".length()) {
                                return;
                            }
                        } else {
                            if (!(du.e(((cv)object).k.b, e[2]) && !du.g(((cv)object).k.b, e[4]) || du.c(fo.d(ec2.s - ((ec)object2).s), ((cv)object).i + e[11]) && !du.f(fo.d(ec2.fc - ((ec)object2).fc), ((cv)object).a + e[11]) || !du.f(du.a(System.currentTimeMillis() - this.b, 1500L)))) {
                                ax.c((ec)object2);
                                this.b = System.currentTimeMillis();
                            }
                            ax.n.removeAllElements();
                            ax.w.removeAllElements();
                            ax.w.addElement(object2);
                            dl.h().e(((cv)object).k.d);
                            dl.h().a(ax.n, ax.w, e[10]);
                        }
                        if (du.e(du.a(System.currentTimeMillis() - ((cv)object).c, (long)((cv)object).g + 50L))) {
                            ((cv)object).c = System.currentTimeMillis();
                            ((cv)object).f = e[2];
                            if (du.g(ey.bf ? 1 : 0)) {
                                ec2.b(ap.ch[((cv)object).k.d], e[0]);
                            }
                        }
                        this.y = System.currentTimeMillis();
                    }
                }
            }
        }
    }

    private static boolean f(int n2, int n3) {
        return n2 > n3;
    }

    public static void f() {
        ai = e[0];
        a = e[0];
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    public static void n() {
        ai = e[0];
        a = e[0];
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean g(int n2, int n3) {
        return n2 == n3;
    }

    private static void o() {
        e = new int[13];
        du.e[0] = (0xA8 ^ 0x92 ^ (0x71 ^ 0x56)) & (67 + 109 - 54 + 20 ^ 146 + 16 - 108 + 93 ^ -" ".length()) & ((0xA7 ^ 0xB0 ^ (0xE7 ^ 0xC2)) & (0xC2 ^ 0x8F ^ 21 + 67 - 74 + 113 ^ -" ".length()) ^ -" ".length());
        du.e[1] = 96 + 129 - 75 + 10;
        du.e[2] = " ".length();
        du.e[3] = 0x69 ^ 0x1B ^ (0x5D ^ 0x41);
        du.e[4] = "   ".length();
        du.e[5] = -(0xFFFFDEB5 & 0x617F) & (0xFFFFEFFF & 0x7BFF);
        du.e[6] = (0x5B ^ 0x6C) + (0xE5 ^ 0x84) - (0x43 ^ 0x58) + (0xB5 ^ 0xB1);
        du.e[7] = 102 + 63 - 103 + 87;
        du.e[8] = -" ".length();
        du.e[9] = -(0xFFFFE57B & 0x5AA7) & (0xFFFFEBEE & Short.MAX_VALUE);
        du.e[10] = "  ".length();
        du.e[11] = 0x2B ^ 0x35;
        du.e[12] = 0x34 ^ 0x7F;
    }

    static {
        du.o();
        ce.a(e[12]);
        du.n();
    }

    private static boolean b(Object object) {
        return object == null;
    }
}

