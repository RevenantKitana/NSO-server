/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public abstract class ax {
    private static ad a;
    byte f;
    public static boolean g;
    public static cv h;
    protected long i = 0L;
    public boolean j;
    protected long k = 0L;
    public static cv l;
    public long m;
    public static ad n;
    public long o;
    public int p;
    public ax q;
    public int r;
    private static long b;
    public int s;
    public boolean t;
    public int u;
    private int c;
    protected boolean v = d[0];
    private static final int[] d;
    private static ad e;
    public static ad w;
    public int x;
    protected long y = 0L;
    public static cv z;
    public static cv aa;
    public static boolean ab;
    private static boolean ah;
    private int ai;
    ByteArrayInputStream ac;
    DataInputStream ad;
    public static int ae;
    private static long aj;
    public static ad af;
    public static cv ag;

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean c(int n2, int n3) {
        int n4;
        if (!ax.h(n3) || ax.a(n2) && !ax.e(n3 & d[3]) || ax.j(n2, d[3]) && !ax.e(n3 & d[6]) || ax.j(n2, d[6]) && !ax.e(n3 & d[9]) || ax.j(n2, d[1]) && ax.f(n3 & d[28])) {
            n4 = d[3];
            "".length();
            if (null != null) {
                return ((0xB6 ^ 0x90) & ~(0x77 ^ 0x51)) != 0;
            }
        } else {
            n4 = d[0];
        }
        return n4 != 0;
    }

    public static void a(bx bx2) {
        if (!ax.e(n.size()) || ax.f(w.size())) {
            bm[] bmArray = new bm[n.size() + w.size()];
            int n2 = d[0];
            while (ax.i(n2, n.size())) {
                bmArray[n2] = new bm();
                bmArray[n2].e = ap.dx[bx2.b - d[3]];
                bmArray[n2].c = (by)n.elementAt(n2);
                ++n2;
                "".length();
                if ("   ".length() == "   ".length()) continue;
                return;
            }
            n2 = d[0];
            while (ax.i(n2, w.size())) {
                bmArray[n2 + ax.n.size()] = new bm();
                bmArray[n2 + ax.n.size()].e = ap.dx[bx2.b - d[3]];
                bmArray[n2 + ax.n.size()].b = (ec)w.elementAt(n2);
                ++n2;
                "".length();
                if (((2 ^ 0x14) & ~(0xB1 ^ 0xA7)) == 0) continue;
                return;
            }
            if (ax.d(bmArray.length, d[3])) {
                ag ag2 = new ag();
                if (ax.a(bmArray[ax.d[0]].c)) {
                    ag2 = new ag(bmArray[ax.d[0]].c.z, bmArray[ax.d[0]].c.n);
                    "".length();
                    if ("  ".length() == " ".length()) {
                        return;
                    }
                } else if (ax.a(bmArray[ax.d[0]].b)) {
                    ag2 = new ag(bmArray[ax.d[0]].b.s, bmArray[ax.d[0]].b.fc);
                }
                ad ad2 = new ad();
                int n3 = d[3];
                while (ax.i(n3, bmArray.length)) {
                    if (ax.a(bmArray[n3].c)) {
                        ad2.addElement(new ag(bmArray[n3].c.z, bmArray[n3].c.n));
                        "".length();
                        if ((0x47 ^ 0x43) < (0x23 ^ 0x27)) {
                            return;
                        }
                    } else if (ax.a(bmArray[n3].b)) {
                        ad2.addElement(new ag(bmArray[n3].b.s, bmArray[n3].b.fc));
                    }
                    if (ax.d(n3, d[11])) {
                        "".length();
                        if (-(0x78 ^ 0x20 ^ (0x9C ^ 0xC0)) < 0) break;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ("   ".length() >= "  ".length()) continue;
                    return;
                }
                dx.a(ad2, ag2, ec.n().f());
            }
            ec.n().el = bmArray;
        }
    }

    protected static boolean b(ec ec2) {
        int n2;
        if (!ax.f(ec2.cl) || !ax.h(ec2.fj, d[10]) || ax.j(ec2.fj, d[11])) {
            n2 = d[3];
            "".length();
            if ("  ".length() == 0) {
                return ((152 + 28 - 146 + 180 ^ 66 + 82 - 52 + 32) & (0xBB ^ 0x9D ^ (0xCE ^ 0xBE) ^ -" ".length())) != 0;
            }
        } else {
            n2 = d[0];
        }
        return n2 != 0;
    }

    protected final ec a(ec ec2, int n2) {
        int n3 = d[0];
        while (ax.i(n3, ap.dn.size())) {
            ec ec3 = (ec)ap.dn.elementAt(n3);
            if (ax.a(ec3) && ax.a(ax.b(ec3) ? 1 : 0) && ax.a(this.a(n2, ec3.s, ec3.fc) ? 1 : 0) && ax.a(this.f(ec3.s, ec3.fc) ? 1 : 0) && ax.a(ey.f(ec3.cx) ? 1 : 0) && ax.i(fs.a(ec3.cx) ? 1 : 0) && (!ax.h(ec3.ej, d[3]) || !ax.h(ec3.gk, ec2.w) || ax.i(ec2.dp, d[19]))) {
                return ec3;
            }
            ++n3;
            "".length();
            if ((0x1E ^ 0x23 ^ (0xBA ^ 0x83)) > -" ".length()) continue;
            return null;
        }
        return null;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    private void a(byte by2) {
        this.f = by2;
    }

    protected final void a(by by2) {
        if (ax.a(by2)) {
            int n2 = by2.y;
            int n3 = by2.x;
            ec ec2 = ec.n();
            if (ax.j(de.i, d[15])) {
                if (ax.j(by2.y, d[29]) && ax.j(by2.x, d[30])) {
                    n2 = d[31];
                    n3 = d[32];
                    "".length();
                    if ("  ".length() <= -" ".length()) {
                        return;
                    }
                } else if (ax.j(by2.y, d[33]) && ax.j(by2.x, d[30])) {
                    n2 = d[34];
                    n3 = d[32];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else if (ax.j(by2.y, d[35]) && ax.j(by2.x, d[36])) {
                    n2 = d[37];
                    n3 = d[38];
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
            } else if (ax.j(de.i, d[14])) {
                if ((!ax.h(by2.y, d[39]) || ax.j(by2.y, d[40])) && ax.j(by2.x, d[41])) {
                    n2 = d[40];
                    n3 = d[42];
                    "".length();
                    if (((0x11 ^ 0x50) & ~(0x2F ^ 0x6E)) != 0) {
                        return;
                    }
                } else if ((!ax.h(by2.y, d[43]) || ax.j(by2.y, d[44])) && ax.j(by2.x, d[45])) {
                    n2 = d[44];
                    n3 = d[46];
                    "".length();
                    if (((152 + 92 - 159 + 101 ^ 116 + 68 - 169 + 134) & (140 + 20 - 26 + 20 ^ 17 + 116 - 97 + 145 ^ -" ".length())) != 0) {
                        return;
                    }
                } else if ((!ax.h(by2.y, d[47]) || ax.j(by2.y, d[39])) && ax.j(by2.x, d[48])) {
                    n2 = d[47];
                    n3 = d[36];
                    "".length();
                    if (-"   ".length() > 0) {
                        return;
                    }
                } else if (ax.j(by2.y, d[49]) && !ax.h(by2.x, d[50]) || ax.j(by2.y, d[51]) && ax.j(by2.x, d[52])) {
                    n2 = d[51];
                    n3 = d[50];
                }
            }
            if (ax.i(ec.a(n2, n3) ? 1 : 0)) {
                this.c = this.p;
                this.ai = this.s;
                this.p = ec2.s;
                this.s = ec2.fc;
                ec2.di = by2;
                try {
                    Thread.sleep(1L);
                    return;
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
            }
            ec2.di = null;
        }
    }

    protected final void b(int n2) {
        block24: {
            int n3;
            ap ap2;
            block23: {
                block25: {
                    if (ax.i(this.j ? 1 : 0) && ax.a(ey.v) && !ax.i(ec.n().cx.equals(ey.v) ? 1 : 0)) break block24;
                    ap2 = ap.s();
                    ds ds2 = ap.b(d[16]);
                    n3 = d[8];
                    if (!ax.a(ds2) || !ax.h(ds2.fj, d[19])) break block25;
                    if (ax.e(Math.abs(ds2.s - ec.n().s), d[20]) && !ax.d(Math.abs(ds2.fc - ec.n().fc), d[20])) break block23;
                    ec.d(ds2.s, ds2.fc);
                    "".length();
                    try {
                        Thread.sleep(500L);
                        "".length();
                    }
                    catch (InterruptedException interruptedException) {
                        "".length();
                        if ((0xB4 ^ 0x81 ^ (0x47 ^ 0x76)) <= 0) {
                            return;
                        }
                        break block23;
                    }
                    if (((0x1B ^ 0x6E ^ (0x57 ^ 0x42)) & (82 + 138 - 219 + 203 ^ 48 + 85 - 3 + 42 ^ -" ".length())) == " ".length()) {
                        return;
                    }
                    break block23;
                }
                if (ax.h(de.i, d[21]) && ax.h(de.i, d[22]) && ax.h(de.i, d[23]) && ax.h(de.i, d[24]) && ax.h(de.i, d[25]) && ax.h(de.i, d[26])) {
                    this.x = de.ab;
                    b = System.currentTimeMillis();
                    return;
                }
                n3 = ec.c(d[14]);
                if (ax.g(n3) && ax.g(n3 = ec.c(d[15]))) {
                    this.x = de.ab;
                    b = System.currentTimeMillis();
                    return;
                }
            }
            if (ax.g(ax.a(System.currentTimeMillis() - b, 1L))) {
                return;
            }
            dl.h().k();
            bo.q();
            int n4 = d[8];
            if (ax.g(n2)) {
                n2 = ap2.ed.length - d[3];
                "".length();
                if (null != null) {
                    return;
                }
            } else if (ax.g(n2, ap2.ed.length)) {
                n2 = d[0];
            }
            if (ax.i(this instanceof df)) {
                n4 = (n2 / d[11] + d[3]) * d[11] % ap2.ed.length;
                "".length();
                if ((0x9B ^ 0x9F) != (0xC1 ^ 0xC5)) {
                    return;
                }
            } else if (ax.a(ec.dn ? 1 : 0)) {
                n4 = (n2 + d[3]) % ap2.ed.length;
                "".length();
                if (-"   ".length() >= 0) {
                    return;
                }
            } else {
                int n5 = d[8];
                int n6 = (n2 + d[3]) % ap2.ed.length;
                while (ax.h(n6, n2)) {
                    if (ax.i(aw.c ? 1 : 0) && (!ax.h(n5, d[8]) || ax.d(ap2.ed[n6], n5))) {
                        n4 = n6;
                        n5 = ap2.ed[n6];
                    }
                    if (ax.a(aw.c ? 1 : 0) && (!ax.h(n5, d[8]) || ax.i(ap2.ed[n6], n5))) {
                        n4 = n6;
                        n5 = ap2.ed[n6];
                    }
                    n6 = (n6 + d[3]) % ap2.ed.length;
                    "".length();
                    if ((78 + 125 - 113 + 41 ^ 2 + 87 - -6 + 40) != "   ".length()) continue;
                    return;
                }
            }
            dl.h().h(n4, n3);
            this.x = n4;
            de.g();
            if (ax.i(this.f() ? 1 : 0)) {
                dl.h().l("khu ".concat(String.valueOf(n4)));
            }
            b = System.currentTimeMillis();
            try {
                Thread.sleep(1L);
                return;
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    protected static boolean g() {
        return ax.b(ec.n());
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean e(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean e(int n2) {
        return n2 <= 0;
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private void e() {
        if (ax.i(ey.w ? 1 : 0)) {
            ey.ax = (ey.ax + d[3]) % ey.ac.length;
            this.x = ey.ac[ey.ax];
            ax.c(this.x);
            if (ax.i(this.f() ? 1 : 0)) {
                dl.h().l("khu " + this.x);
                return;
            }
        } else {
            this.b(de.ab);
        }
    }

    private boolean f(int n2, int n3) {
        if (ax.i(ec.da ? 1 : 0) && ax.a(this instanceof df)) {
            int n4 = d[0];
            while (ax.i(n4, a.size())) {
                ec ec2 = (ec)a.elementAt(n4);
                if (ax.a(ax.b(ec2) ? 1 : 0) && ax.e(fo.d(n2 - ec2.s), d[7]) && ax.e(fo.d(n3 - ec2.fc), d[7])) {
                    return d[3];
                }
                ++n4;
                "".length();
                if (-" ".length() <= 0) continue;
                return ((0x80 ^ 0xA2) & ~(0x46 ^ 0x64)) != 0;
            }
            return d[0];
        }
        return d[0];
    }

    protected static void c(ec ec2) {
        if (ax.a(ec2)) {
            ec ec3 = ec.n();
            ec.d(ec2.s, de.a(ec2.s, ec2.fc));
            "".length();
            ec3.fm = ec2;
            try {
                Thread.sleep(1L);
                return;
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public String toString() {
        return "";
    }

    protected static ec d(ec ec2) {
        int n2;
        int n3;
        ec ec3 = ec2;
        int n4 = ec2.fc;
        int n5 = ec2.s;
        ec2 = null;
        ec ec4 = ec.n();
        int n6 = n5 - ec4.j() - d[2];
        int n7 = n5 + ec4.j() + d[2];
        int n8 = n4 - ec4.ad();
        if (ax.i(ec4.dk.c) && ax.h(ec4.dk.c, d[3]) && ax.h(ec4.dk.c, d[1]) && ax.h(ec4.dk.c, d[11])) {
            n3 = d[0];
            "".length();
            if ("  ".length() >= (0x4C ^ 0x48)) {
                return null;
            }
        } else {
            n3 = d[54];
        }
        int n9 = n8 - n3;
        int n10 = n4 + ec4.ad();
        if (ax.i(ec4.dk.c) && ax.h(ec4.dk.c, d[3]) && ax.h(ec4.dk.c, d[1]) && ax.h(ec4.dk.c, d[11])) {
            n2 = d[0];
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            n2 = d[54];
        }
        int n11 = n10 + n2;
        int n12 = d[8];
        int n13 = d[0];
        while (ax.i(n13, ap.dn.size())) {
            int n14;
            int n15;
            ec ec5 = (ec)ap.dn.elementAt(n13);
            int n16 = Math.abs(n5 - ec5.s);
            if (ax.d(n16, n15 = Math.abs(n4 - ec5.fc))) {
                n14 = n16;
                "".length();
                if (-(60 + 141 - 57 + 18 ^ 24 + 115 - 9 + 36) >= 0) {
                    return null;
                }
            } else {
                n14 = n16 = n15;
            }
            if (ax.e(n6, ec5.s) && ax.e(ec5.s, n7) && ax.e(n9, ec5.fc) && ax.e(ec5.fc, n11) && ax.a(ax.b(ec5) ? 1 : 0) && ax.i(ax.a(ec4, ec5) ? 1 : 0) && ax.a(ey.f(ec5.cx) ? 1 : 0) && (!ax.h(n12, d[8]) || ax.i(n16, n12))) {
                ec2 = ec5;
                n12 = n16;
            }
            ++n13;
            "".length();
            if (null == null) continue;
            return null;
        }
        ec3.fm = ec2;
        return ec3.fm;
    }

    private static boolean g(int n2, int n3) {
        return n2 >= n3;
    }

    public static void e(ec ec2) {
        if (ax.a((Object)ec2, (Object)ec.n())) {
            if (ax.i(a.contains(ec2) ? 1 : 0)) {
                if (ax.h(ec2.ej, d[1]) && ax.h(ec2.gk, ec.n().w)) {
                    a.removeElement(ec2);
                    "".length();
                    return;
                }
            } else if (!ax.h(ec2.ej, d[1]) || ax.j(ec2.gk, ec.n().w)) {
                a.addElement(ec2);
                if (ax.i(bo.b ? 1 : 0) && ax.e(fo.d(ec.n().s - ec2.s), d[7]) && ax.e(fo.d(ec.n().fc - ec2.fc), d[7])) {
                    bo.n();
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private boolean a(int var1_1, int var2_2, int var3_3) {
        if (ax.g(var1_1, ax.d[9])) {
            return ax.d[0];
        }
        var4_4 = ax.d[0];
        while (ax.i(var4_4, ax.af.size())) {
            block11: {
                block8: {
                    block10: {
                        block9: {
                            var5_5 = (by)ax.af.elementAt(var4_4);
                            if (!ax.i(var5_5.aa) || !ax.f(var5_5.v) || !ax.i(var5_5.p)) break block8;
                            if (!ax.j(var5_5.aa, ax.d[1])) break block9;
                            if (ax.a(this instanceof df) && !ax.i(this instanceof cb)) ** GOTO lbl-1000
                            var6_6 = ax.d[0];
                            "".length();
                            if (" ".length() <= ((48 + 106 - 43 + 22 ^ 33 + 15 - -90 + 36) & (0 ^ 49 ^ (129 ^ 155) ^ -" ".length()))) {
                                return (boolean)((23 + 64 - 40 + 104 ^ 151 + 170 - 259 + 122) & (82 ^ 62 ^ (71 ^ 4) ^ -" ".length()));
                            }
                            break block10;
                        }
                        if (!(ax.i((int)var5_5.j) && !ax.j(var1_1 & ax.d[18], ax.d[18]) || ax.j(var5_5.aa, ax.d[3]) && !ax.i(var1_1 & ax.d[6]) || ax.j(var5_5.aa, ax.d[6]) && !ax.i(var1_1 & ax.d[9]))) {
                            var6_6 = ax.d[0];
                            "".length();
                            if (null != null) {
                                return (boolean)((49 + 70 - 0 + 82 ^ 121 + 43 - 151 + 120) & (86 + 106 - 97 + 102 ^ 114 + 77 - 75 + 21 ^ -" ".length()));
                            }
                        } else lbl-1000:
                        // 2 sources

                        {
                            var6_6 = ax.d[3];
                        }
                    }
                    if (ax.i(var6_6) && ax.e(fo.d(var2_2 - var5_5.y), ax.d[53]) && ax.e(fo.d(var3_3 - var5_5.x), ax.d[4])) {
                        return ax.d[3];
                    }
                    "".length();
                    if ("  ".length() < 0) {
                        return (boolean)((19 ^ 24) & ~(15 ^ 4));
                    }
                    break block11;
                }
                ax.af.removeElement(var5_5);
                "".length();
                --var4_4;
            }
            ++var4_4;
            "".length();
            if (-" ".length() <= ((149 ^ 180 ^ (0 ^ 112)) & (92 ^ 90 ^ (193 ^ 150) ^ -" ".length()))) continue;
            return (boolean)((81 + 53 - 45 + 136 ^ 131 + 149 - 261 + 147) & (106 + 12 - -55 + 43 ^ 130 + 83 - 128 + 74 ^ -" ".length()));
        }
        return ax.d[0];
    }

    private static boolean h(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean f(int n2) {
        return n2 > 0;
    }

    private boolean f() {
        int n2;
        if (ax.i(this.j ? 1 : 0) && ax.f(ap.an.size()) && ax.j(((dg)ap.an.firstElement()).d, ec.n().w)) {
            n2 = d[3];
            "".length();
            if ("  ".length() != "  ".length()) {
                return ((0x69 ^ 0x34) & ~(0xF8 ^ 0xA5)) != 0;
            }
        } else {
            n2 = d[0];
        }
        return n2 != 0;
    }

    protected static void c(int n2) {
        block10: {
            int n3;
            block9: {
                block11: {
                    if (!ax.h(de.ab, n2)) break block10;
                    ds ds2 = ap.b(d[16]);
                    n3 = d[8];
                    if (!ax.a(ds2) || !ax.h(ds2.fj, d[19])) break block11;
                    if (ax.e(Math.abs(ds2.s - ec.n().s), d[20]) && !ax.d(Math.abs(ds2.fc - ec.n().fc), d[20])) break block9;
                    ec.d(ds2.s, ds2.fc);
                    "".length();
                    try {
                        Thread.sleep(1L);
                        "".length();
                    }
                    catch (InterruptedException interruptedException) {
                        "".length();
                        if (-"  ".length() > 0) {
                            return;
                        }
                        break block9;
                    }
                    if ((0x83 ^ 0x87) == ((0x28 ^ 0xE) & ~(0x1A ^ 0x3C))) {
                        return;
                    }
                    break block9;
                }
                if (ax.h(de.i, d[21]) && ax.h(de.i, d[22]) && ax.h(de.i, d[23]) && ax.h(de.i, d[24]) && ax.h(de.i, d[25]) && ax.h(de.i, d[26])) {
                    return;
                }
                n3 = ec.c(d[14]);
                if (ax.g(n3) && ax.g(n3 = ec.c(d[15]))) {
                    return;
                }
            }
            if (ax.g(ax.f(System.currentTimeMillis() - b, 2000L))) {
                return;
            }
            dl.h().h(n2, n3);
            de.g();
            b = System.currentTimeMillis();
            try {
                Thread.sleep(1L);
                return;
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    private static boolean g(int n2) {
        return n2 < 0;
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static void b(by by2) {
        if (!ax.a(by2.j ? 1 : 0) || ax.i(by2.p) && ax.h(by2.aa, d[1]) && ax.h(by2.a, by2.f().g)) {
            if (ax.a(by2.j ? 1 : 0) && ax.a(by2.aa)) {
                if (ax.j(by2.a, d[2] * by2.f().g)) {
                    by2.aa = (short)d[3];
                    "".length();
                    if (-" ".length() >= "  ".length()) {
                        return;
                    }
                } else {
                    if (ax.h(by2.a, d[4] * by2.f().g) && ax.h(by2.g, d[5])) {
                        return;
                    }
                    by2.aa = (short)d[6];
                }
            }
            if (ax.a(af.contains(by2) ? 1 : 0)) {
                af.addElement(by2);
            }
        }
    }

    private static boolean a(by by2, int n2) {
        boolean bl2;
        if (ax.j(by2.g, d[27]) && ax.j(by2.p, d[28])) {
            return d[0];
        }
        if (!ax.h(n2) || ax.j(by2.g, n2)) {
            bl2 = d[3];
            "".length();
            if ("   ".length() < 0) {
                return ((0x4D ^ 0x6D) & ~(0x1E ^ 0x3E)) != 0;
            }
        } else {
            bl2 = d[0];
        }
        return bl2;
    }

    private void n() {
        switch (this.f) {
            case 60: 
            case 61: {
                try {
                    if (ax.a(ec.n().bx) && ax.j(ec.n().w, this.ad.readInt()) && ax.d(ec.n().fb, ec.n().bx.d)) {
                        ec ec2 = ec.n();
                        ec2.fb -= ec.n().bx.d;
                    }
                    "".length();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                if ("   ".length() == "   ".length()) break;
                return;
            }
        }
    }

    protected static by a(int n2, int n3) {
        int n4;
        by by2 = null;
        ec ec2 = ec.n();
        int n5 = n2 - ec2.j() - d[2];
        int n6 = n2 + ec2.j() + d[2];
        int n7 = n3 - ec2.ad();
        if (ax.i(ec2.dk.c) && ax.h(ec2.dk.c, d[3]) && ax.h(ec2.dk.c, d[1]) && ax.h(ec2.dk.c, d[11])) {
            n4 = d[0];
            "".length();
            if (-" ".length() > "   ".length()) {
                return null;
            }
        } else {
            n4 = d[54];
        }
        int n8 = n7 - n4;
        int n9 = n3 + ec2.ad();
        if (ax.d(n9, n3 + d[55])) {
            n9 = n3 + d[55];
        }
        int n10 = d[8];
        int n11 = d[0];
        while (ax.i(n11, ap.au.size())) {
            int n12;
            int n13;
            by by3 = (by)ap.au.elementAt(n11);
            int n14 = Math.abs(n2 - by3.z);
            if (ax.d(n14, n13 = Math.abs(n3 - by3.n))) {
                n12 = n14;
                "".length();
                if (null != null) {
                    return null;
                }
            } else {
                n12 = n14 = n13;
            }
            if (ax.e(n5, by3.z) && ax.e(by3.z, n6) && ax.e(n8, by3.n) && ax.e(by3.n, n9) && ax.i(by3.p) && ax.h(by3.p, d[3]) && (!ax.h(n10, d[8]) || ax.i(n14, n10))) {
                by2 = by3;
                n10 = n14;
            }
            ++n11;
            "".length();
            if ("   ".length() > -" ".length()) continue;
            return null;
        }
        return by2;
    }

    private static boolean h(int n2) {
        return n2 >= 0;
    }

    public static void c(by by2) {
        af.removeElement(by2);
        "".length();
    }

    protected static void h() {
        ec ec2 = ec.n();
        if (ax.a(ec.k(d[14]) ? 1 : 0) && ax.a(ec.k(d[15]) ? 1 : 0)) {
            ds ds2 = ap.b(d[16]);
            if (ax.a(ds2) && ax.e(Math.abs(ds2.s - ec2.s), d[17]) && ax.e(Math.abs(ds2.fc - ec2.fc), d[17])) {
                int n2;
                if (ax.d(ds2.s, d[17])) {
                    n2 = ds2.s - d[17];
                    "".length();
                    if (-" ".length() >= " ".length()) {
                        return;
                    }
                } else {
                    n2 = ds2.s + d[17];
                }
                ec.d(n2, ds2.fc);
                "".length();
            }
            dl.h().k();
            "".length();
            if (null != null) {
                return;
            }
        } else {
            ec.d(ec2.s, de.j);
            "".length();
        }
        long l2 = System.currentTimeMillis();
        while (ax.f(ec2.cl) && ax.g(ax.b(System.currentTimeMillis() - l2, 5L))) {
            try {
                Thread.sleep(1L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                "".length();
                if (-" ".length() < (0x15 ^ 0x3C ^ (0xA2 ^ 0x8F))) continue;
                return;
            }
            if (-" ".length() <= "   ".length()) continue;
            return;
        }
    }

    protected final boolean i() {
        int n2;
        if (ax.i(this.j ? 1 : 0) && ax.f(ap.an.size()) && ax.h(((dg)ap.an.firstElement()).d, ec.n().w)) {
            n2 = d[3];
            "".length();
            if (" ".length() <= 0) {
                return ((0xF4 ^ 0x9B ^ (0x2D ^ 0x73)) & (157 + 117 - 215 + 123 ^ 78 + 9 - 3 + 51 ^ -" ".length())) != 0;
            }
        } else {
            n2 = d[0];
        }
        return n2 != 0;
    }

    public void c() {
        cv cv2;
        cv cv3;
        cv cv4;
        cv cv5;
        this.u = d[8];
        this.x = d[8];
        this.t = d[0];
        this.q = null;
        this.r = ec.n().cg;
        this.o = ec.n().cj;
        this.m = System.currentTimeMillis();
        this.j = d[0];
        ey.as = d[8];
        ey.ax = d[0];
        ab = ec.n().cb;
        z = ec.n().bx;
        if (ax.a(ap.ef[d[3]]) && ax.i(ee.d ? 1 : 0)) {
            cv5 = ap.ef[d[3]];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            cv5 = l = null;
        }
        if (ax.a(ap.ef[d[6]]) && ax.i(ee.d ? 1 : 0)) {
            cv4 = ap.ef[d[6]];
            "".length();
            if ("   ".length() <= ((0x90 ^ 0xB3 ^ (0x24 ^ 0x2E)) & (0xA2 ^ 0x81 ^ (0x9A ^ 0x90) ^ -" ".length()))) {
                return;
            }
        } else {
            cv4 = ag = null;
        }
        if (ax.a(ap.ef[d[1]]) && ax.i(ee.d ? 1 : 0)) {
            cv3 = ap.ef[d[1]];
            "".length();
            if (" ".length() <= ((0x36 ^ 0x3F) & ~(0x4D ^ 0x44))) {
                return;
            }
        } else {
            cv3 = h = null;
        }
        if (ax.a(ap.ef[d[9]]) && ax.i(ee.d ? 1 : 0)) {
            cv2 = ap.ef[d[9]];
            "".length();
            if (" ".length() == 0) {
                return;
            }
        } else {
            cv2 = null;
        }
        aa = cv2;
        this.b();
    }

    protected final void j() {
        if (ax.h(de.i, d[20])) {
            this.a(d[20], d[73], d[8], d[8]);
            "".length();
            if (" ".length() != " ".length()) {
                return;
            }
        } else {
            ec ec2 = ec.n();
            if (ax.a(ec2.aq)) {
                int n2 = d[0];
                while (ax.i(n2, ec2.ao.size())) {
                    cv cv2 = (cv)ec2.ao.elementAt(n2);
                    if (ax.a(cv2) && ax.a(cv2.a() ? 1 : 0) && ax.g(cv2.k.d, d[62]) && ax.e(cv2.k.d, d[63])) {
                        dl.h().e(cv2.k.d);
                        dl.h().c();
                        bo.p();
                        "".length();
                        if (((0x1A ^ 0x27 ^ (0x30 ^ 0x19)) & (0x38 ^ 0x44 ^ (0x7B ^ 0x13) ^ -" ".length())) >= 0) break;
                        return;
                    }
                    ++n2;
                    "".length();
                    if (" ".length() > 0) continue;
                    return;
                }
                ap.g(d[74]);
                dl.h().b(d[74], d[1]);
                bo.p();
            }
        }
    }

    public static void k() {
        a.removeAllElements();
    }

    protected final void a(int n2, boolean bl2) {
        if (!ax.h(ey.as) || ax.g(ey.as, ey.az.size())) {
            ey.as = d[0];
        }
        do {
            int n3 = (Integer)ey.az.elementAt(ey.as);
            int n4 = (Integer)ey.an.elementAt(ey.as);
            by by2 = ax.a(n3, n4);
            if (ax.a(this.a(n2, n3, n4) ? 1 : 0) && ax.a(this.f(n3, n4) ? 1 : 0) && ax.a(by2) && ax.a(this.a(n2, by2.z, by2.n) ? 1 : 0)) {
                this.c = ec.n().s;
                this.ai = ec.n().fc;
                ec.d(n3, n4);
                "".length();
                ec.n().di = by2;
                dl.h().a(by2.b);
                try {
                    Thread.sleep(1L);
                    return;
                }
                catch (InterruptedException interruptedException) {
                    return;
                }
            }
            if (ax.j(ey.as += d[3], ey.az.size())) {
                ey.as = d[0];
                if (ax.i(ec.af ? 1 : 0) && ax.i(bl2 ? 1 : 0)) {
                    this.e();
                }
            }
            "".length();
        } while (-(0x16 ^ 0x35 ^ (0x89 ^ 0xAF)) < 0);
    }

    protected final void d(int n2) {
        block12: {
            if (!ax.a(ey.bb ? 1 : 0)) break block12;
            ec ec2 = ec.n();
            if (!ax.a(ax.l() ? 1 : 0)) break block12;
            e.removeAllElements();
            int n3 = this.a(ec.dn, ec.ea, ec.eg);
            int n4 = d[0];
            while (ax.i(n4, ap.ff.size())) {
                fm fm2 = (fm)ap.ff.elementAt(n4);
                if (ax.a(fm2.i ? 1 : 0) && (ax.j(ec2.dk.c, d[3]) && !ax.h(fm2.k.d, d[71]) || (!ax.a(ey.a(fm2.k) ? 1 : 0) || ax.j(fm2.k.d, n2)) && (!ax.e(ec.u(), d[6]) || !ax.h(fm2.k.b, d[72]) || ax.i(fm2.k.g ? 1 : 0) && ax.i(ec.k(fm2.k.d) ? 1 : 0))) && ax.a(this.a(n3, fm2.h, fm2.a) ? 1 : 0) && ax.a(this.f(fm2.h, fm2.a) ? 1 : 0) && (!ax.h(ey.ae) || ax.i(fo.a(ec2.s, ec2.fc, fm2.h, fm2.a), ey.ae))) {
                    e.addElement(fm2);
                }
                ++n4;
                "".length();
                if ((0x7B ^ 0x7F) != -" ".length()) continue;
                return;
            }
            if (!ax.f(e.size())) break block12;
            n4 = ec2.s;
            int n5 = ec2.fc;
            by by2 = ec2.di;
            n3 = d[0];
            block5: while (ax.i(n3, e.size())) {
                try {
                    Thread.sleep(1L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (((0xF ^ 0x2A) & ~(0x3D ^ 0x18)) > 0) {
                    return;
                }
                fm fm3 = (fm)e.elementAt(n3);
                ec.d(fm3.h, de.a(fm3.h, fm3.a));
                "".length();
                ec2.fz = fm3;
                int n6 = d[0];
                while (ax.i(n6, d[9]) && ax.h(fm3.f, d[6]) && ax.a(fm3.i ? 1 : 0)) {
                    dl.h().r(fm3.l);
                    if (ax.i(bo.ab() ? 1 : 0)) {
                        "".length();
                        if (" ".length() > 0) break;
                        return;
                    }
                    if (!ax.a(this.f(ec2.s, ec2.fc) ? 1 : 0)) break block5;
                    if (ax.e(ec2.cl)) {
                        "".length();
                        if ((59 + 57 - 63 + 100 ^ 103 + 106 - 181 + 129) >= 0) break block5;
                        return;
                    }
                    ++n6;
                    "".length();
                    if (null == null) continue;
                    return;
                }
                fm3.i = d[3];
                fm3.e = System.currentTimeMillis();
                ++n3;
                "".length();
                if (null == null) continue;
                return;
            }
            try {
                Thread.sleep(1L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if ((0x8A ^ 0x8E) <= "   ".length()) {
                return;
            }
            ec.d(n4, n5);
            "".length();
            ec2.di = by2;
        }
    }

    private static int d(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    protected static boolean l() {
        ec ec2 = ec.n();
        if (ax.i(ec2.cb ? 1 : 0) && ax.i(ec2.cl, ec2.fd)) {
            int n2 = d[0];
            while (ax.i(n2, ec2.dy.size())) {
                au au2 = (au)ec2.dy.elementAt(n2);
                if (ax.a(au2) && ax.j(au2.a.c, d[74])) {
                    return d[3];
                }
                ++n2;
                "".length();
                if ("   ".length() > "  ".length()) continue;
                return ((0xB ^ 0x14) & ~(8 ^ 0x17)) != 0;
            }
        }
        return d[0];
    }

    private static boolean i(int n2, int n3) {
        return n2 < n3;
    }

    protected final void a(int n2, int n3, int n4, int n5) {
        block23: {
            block21: {
                block24: {
                    block22: {
                        if (ax.g(n2, d[12]) && !ax.d(n2, d[13]) || !ax.g(de.i, d[12]) || !ax.e(de.i, d[13])) break block22;
                        ax.h();
                        "".length();
                        if ((43 + 186 - 39 + 1 ^ 120 + 126 - 162 + 102) <= 0) {
                            return;
                        }
                        break block23;
                    }
                    if (!ax.h(de.i, n2)) break block21;
                    if (ax.a(de.f(n2) ? 1 : 0)) {
                        if (!ax.a(de.e(n2) ? 1 : 0) || ax.i(de.o(n2) ? 1 : 0)) {
                            try {
                                Thread.sleep(1L);
                                return;
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                        }
                        return;
                    }
                    if (!ax.g(n3, d[8]) || !ax.h(de.ab, n3)) break block24;
                    try {
                        Thread.sleep(1L);
                        "".length();
                    }
                    catch (InterruptedException interruptedException) {
                        "".length();
                        if ((2 + 162 - 4 + 11 ^ 133 + 113 - 91 + 20) < 0) {
                            return;
                        }
                        break block21;
                    }
                    if (((0x5A ^ 0x4C) & ~(0x15 ^ 3)) > "  ".length()) {
                        return;
                    }
                    break block21;
                }
                try {
                    Thread.sleep(1L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (((0x78 ^ 0x44) & ~(0x10 ^ 0x2C)) != 0) {
                    return;
                }
            }
            if (ax.j(n3, d[8])) {
                if (ax.i(ey.w ? 1 : 0)) {
                    int[] nArray = ey.ac;
                    ey.ax = d[0];
                    this.x = nArray[d[0]];
                    ax.c(this.x);
                    "".length();
                    if (((0x26 ^ 0xE) & ~(0xB6 ^ 0x9E)) > 0) {
                        return;
                    }
                } else {
                    this.b(n3);
                    "".length();
                    if ("  ".length() <= 0) {
                        return;
                    }
                }
            } else if (ax.h(n3)) {
                ax.c(n3);
            }
            if (ax.j(de.ab, this.x) && ax.f(n4) && ax.f(n5) && ax.h(ec.n().s, n4) && ax.h(ec.n().fc, n5)) {
                if (!ax.a(this instanceof cb) || ax.i(this instanceof aq)) {
                    ec.d(n4, n5);
                    "".length();
                    return;
                }
                this.a(ax.a(n4, n5));
            }
        }
    }

    protected by a(ec ec2, int n2, int n3, ec ec3, boolean bl2) {
        block29: {
            block30: {
                by by2;
                block28: {
                    if (ax.i(ey.av ? 1 : 0) && ax.f(ey.az.size())) {
                        this.a(n3, bl2);
                        return ax.a(ec2.s, ec2.fc);
                    }
                    int n4 = n3;
                    int n5 = n2;
                    n3 = ec2.fc;
                    n2 = ec2.s;
                    ax ax2 = this;
                    int n6 = d[8];
                    int n7 = d[8];
                    int n8 = d[8];
                    by by3 = null;
                    ad ad2 = ap.au;
                    int n9 = d[0];
                    do {
                        if (ax.g(n9, ad2.size())) {
                            by2 = by3;
                            "".length();
                            if ("   ".length() >= (0x4A ^ 0x33 ^ (0x56 ^ 0x2B))) {
                                return null;
                            }
                            break block28;
                        }
                        by by4 = (by)ad2.elementAt(n9);
                        if (ax.a(by4) && ax.f(by4.v) && ax.i(by4.p) && ax.h(by4.p, d[3]) && ax.i(ax.a(by4, n5) ? 1 : 0) && ax.i(ax.c(by4.aa, n4) ? 1 : 0) && (!ax.a(ec3) || !ax.h(ec3.w, ec.n().w) || ax.e(fo.a(by4.y, by4.x, ec3.s, ec3.fc), d[56])) && ax.a(ax2.a(n4, by4.z, by4.n) ? 1 : 0) && ax.a(ax2.f(by4.z, by4.n) ? 1 : 0)) {
                            if (ax.i(ax2.t ? 1 : 0)) {
                                if (ax.h(ax2.u, d[57]) && ax.h(ax2.u, d[58]) && ax.h(ax2.u, d[59])) {
                                    if (!ax.h(n6, d[8]) || !ax.g(by4.aa, n8) || !ax.g(by4.x, n6) || ax.j(by4.x, n6) && ax.i(by4.y, n7)) {
                                        n8 = by4.aa;
                                        n6 = by4.x;
                                        n7 = by4.y;
                                        by3 = by4;
                                        "".length();
                                        if ("  ".length() == -" ".length()) {
                                            return null;
                                        }
                                    }
                                } else if (ax.i(by4.j ? 1 : 0)) {
                                    by2 = by4;
                                    "".length();
                                    if ("   ".length() <= -" ".length()) {
                                        return null;
                                    }
                                    break block28;
                                }
                            } else if (!ax.h(ey.be, d[8]) || ax.e(fo.a(ey.o, ey.bc, by4.y, by4.x), ey.be)) {
                                int n10;
                                ad ad3 = ad2;
                                by by5 = by4;
                                int n11 = d[0];
                                int n12 = d[0];
                                while (ax.i(n12, ad3.size())) {
                                    by by6 = (by)ad3.elementAt(n12);
                                    if (ax.a(by6) && ax.f(by6.v) && ax.i(by6.p) && ax.h(by6.p, d[3]) && ax.i(ax.a(by5, n5) ? 1 : 0) && ax.i(ax.c(by5.aa, n4) ? 1 : 0) && ax.e(fo.d(by6.z - by5.z), d[4]) && ax.e(fo.d(by6.n - by5.n), d[60])) {
                                        ++n11;
                                    }
                                    ++n12;
                                    "".length();
                                    if ((97 + 29 - 61 + 127 ^ 5 + 110 - 92 + 174) > 0) continue;
                                    return null;
                                }
                                if (ax.d(n11, ax.z.j)) {
                                    n11 = ax.z.j;
                                }
                                n11 = by5.aa << d[9] | n11 & d[19];
                                if (ax.a(ec3) && ax.h(ec3.w, ec.n().w)) {
                                    n10 = fo.a(ec3.s, ec3.fc, by4.y, by4.x);
                                    "".length();
                                    if ("  ".length() == 0) {
                                        return null;
                                    }
                                } else {
                                    n10 = n12 = fo.a(n2, n3, by4.y, by4.x);
                                }
                                if (!ax.e(n11, n8) || ax.j(n11, n8) && ax.i(n12, n6)) {
                                    n8 = n11;
                                    n6 = n12;
                                    by3 = by4;
                                }
                            }
                        }
                        ++n9;
                        "".length();
                    } while (-(0xB4 ^ 0x8D ^ (0x39 ^ 4)) < 0);
                    return null;
                }
                if (ax.a(by2)) {
                    this.a(by2);
                    return by2;
                }
                if (!ax.f(ax.d(System.currentTimeMillis() - this.y, 1L)) || !ax.a(this.a_() ? 1 : 0)) break block29;
                if (!ax.i(this.t ? 1 : 0)) break block30;
                int n13 = de.l(de.i);
                if (ax.h(n13)) {
                    this.u = n13;
                }
                this.p = this.s = d[8];
                try {
                    Thread.sleep(5L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if ("  ".length() == ((37 + 205 - 43 + 32 ^ 186 + 2 - 76 + 76) & (212 + 70 - 271 + 212 ^ 4 + 55 - -1 + 72 ^ -" ".length()))) {
                    return null;
                }
                "".length();
                if (null != null) {
                    return null;
                }
                break block29;
            }
            if (ax.i(bl2 ? 1 : 0) && ax.i(ec.af ? 1 : 0)) {
                this.e();
            }
        }
        return null;
    }

    protected abstract void a();

    protected boolean a_() {
        if (ax.a(this instanceof df) && ax.a(ey.bb ? 1 : 0)) {
            int n2;
            ec ec2 = ec.n();
            if (ax.g(ey.ae)) {
                n2 = d[8];
                "".length();
                if (null != null) {
                    return ("   ".length() & ("   ".length() ^ -" ".length())) != 0;
                }
            } else {
                n2 = ey.ae;
            }
            int n3 = n2;
            int n4 = d[0];
            while (ax.i(n4, ap.ff.size())) {
                fm fm2 = (fm)ap.ff.elementAt(n4);
                if (ax.a(fm2.i ? 1 : 0) && (ax.j(ec2.dk.c, d[3]) && !ax.h(fm2.k.d, d[71]) || !ax.h(fm2.k.b, d[72]) || ax.i(ey.a(fm2.k) ? 1 : 0) && (!ax.e(ec.u(), d[6]) || ax.i(fm2.k.g ? 1 : 0) && ax.i(ec.k(fm2.k.d) ? 1 : 0))) && (!ax.h(n3) || ax.i(fo.a(ec2.s, ec2.fc, fm2.h, fm2.a), n3)) && ax.a(this.f(fm2.g, fm2.d) ? 1 : 0)) {
                    return d[3];
                }
                ++n4;
                "".length();
                if ("  ".length() >= 0) continue;
                return ((0x26 ^ 0x3E) & ~(0x89 ^ 0x91)) != 0;
            }
            return d[0];
        }
        return d[0];
    }

    private static boolean i(int n2) {
        return n2 != 0;
    }

    /*
     * Unable to fully structure code
     */
    protected final void b(int var1_1, int var2_2) {
        block94: {
            block91: {
                block92: {
                    block97: {
                        block95: {
                            at.a();
                            var3_3 = ec.n();
                            if (!ax.a((int)ax.l())) break block94;
                            if (ax.i((int)this.j) && ax.f(ap.an.size())) {
                                v0 = ((dg)ap.an.firstElement()).b;
                                "".length();
                                if ("  ".length() == 0) {
                                    return;
                                }
                            } else {
                                v0 = var4_4 = null;
                            }
                            if (!ax.i((int)this.j) || !ax.a(ey.v) || ax.i((int)var3_3.cx.equals(ey.v)) && ax.i((int)bo.g())) {
                                v1 = ax.d[3];
                                "".length();
                                if (null != null) {
                                    return;
                                }
                            } else {
                                v1 = ax.d[0];
                            }
                            var5_5 = v1;
                            var6_6 = var3_3.di;
                            var7_7 = var3_3.fm;
                            if (ax.i((int)ey.am) && (!ax.a(var7_7) || !ax.a((int)ey.f(var7_7.cx)) || ax.a((int)fs.a(var7_7.cx)) && ax.a((int)ax.a(var3_3, var7_7))) && ax.b((Object)(var7_7 = this.a(var3_3, var2_2)))) {
                                var7_7 = ax.d(var3_3);
                            }
                            if (ax.a(var7_7) && ax.i((int)fs.a(var7_7.cx))) {
                                v2 = ax.d[3];
                                "".length();
                                if (-" ".length() > "   ".length()) {
                                    return;
                                }
                            } else {
                                v2 = var8_8 = ax.d[0];
                            }
                            if (ax.b((Object)var7_7) && ax.i((int)this.v)) {
                                dl.h().al(ax.d[0]);
                                this.v = ax.d[0];
                            }
                            if (ax.i((int)ey.am) && ax.g(var3_3.dp, ax.d[11]) && ax.f(ax.e(System.currentTimeMillis() - this.k, 5000L))) {
                                var9_9 = ec.b(ax.d[61]);
                                if (ax.a(var9_9) && ax.j(var9_9.m.d, ax.d[61])) {
                                    dl.h().u(var9_9.n);
                                }
                                this.k = System.currentTimeMillis();
                            }
                            if (ax.i((int)ey.av) && ax.f(ey.az.size()) && ax.g(ey.as)) {
                                this.a(var2_2, (boolean)var5_5);
                                return;
                            }
                            var9_10 = ax.d[0];
                            if (ax.a((int)this.a(var2_2, var3_3.s, var3_3.fc)) && ax.a((int)this.f(var3_3.s, var3_3.fc)) && (!ax.a(var6_6) || !ax.i((int)this.a(var2_2, var6_6.z, var6_6.n)))) break block95;
                            try {
                                Thread.sleep(5L);
                                "".length();
                            }
                            catch (InterruptedException var10_11) {
                                // empty catch block
                            }
                            if (-"   ".length() >= 0) {
                                return;
                            }
                            ap.a("N\u00e9");
                            if (ax.i((int)ec.af) && ax.i(var5_5)) {
                                this.e();
                                var9_10 = ax.d[3];
                                "".length();
                                if ("   ".length() < 0) {
                                    return;
                                }
                            } else {
                                var9_10 = ax.d[0];
                            }
                            if (ax.i(var9_10)) {
                                return;
                            }
                            var9_10 = ax.d[3];
                            var6_6 = null;
                        }
                        if (!(ax.a(var6_6) && ax.i(var6_6.p) && ax.i((int)ax.a((by)var6_6, var1_1)) && ax.i((int)ax.c(var6_6.aa, var2_2)) && !ax.f(ax.e(System.currentTimeMillis() - this.y, 5000L)))) {
                            var6_6 = this.a(var3_3, var1_1, var2_2, var4_4, (boolean)var5_5);
                        }
                        if (ax.b(var6_6) && ax.i(var9_10) && ax.f(this.c) && ax.f(this.ai)) {
                            ec.d(this.c, this.ai);
                            "".length();
                        }
                        if (!ax.i((int)ec.ct) || !ax.f(ap.an.size()) || !ax.j(var3_3.dk.c, ax.d[18]) || !ax.f(var3_3.cl)) break block97;
                        var12_17 = ax.d[0];
                        while (ax.i(var12_17, var3_3.ao.size())) {
                            block98: {
                                var13_18 = (cv)var3_3.ao.elementAt(var12_17);
                                if (!ax.a(var13_18) || !ax.j(var13_18.k.b, ax.d[9])) break block98;
                                if (!ax.a((int)var13_18.a())) break;
                                var10_12 = ax.d[0];
                                while (ax.i(var10_12, ap.an.size())) {
                                    block99: {
                                        var14_19 = (dg)ap.an.elementAt(var10_12);
                                        if (!ax.h(var14_19.d, var3_3.w) || !ax.a(var14_19.b) || !ax.e(var14_19.b.cl)) break block99;
                                        var11_23 = var14_19.b;
                                        if (!ax.e(Math.abs(var3_3.s - var11_23.s), ax.d[60]) || ax.d(Math.abs(var3_3.fc - var11_23.fc), ax.d[60])) {
                                            ec.d(var11_23.s, var11_23.fc);
                                            "".length();
                                        }
                                        try {
                                            Thread.sleep(500L);
                                            "".length();
                                        }
                                        catch (InterruptedException var15_26) {
                                            // empty catch block
                                        }
                                        if (null != null) {
                                            return;
                                        }
                                        dl.h().am(var14_19.d);
                                        var13_18.c = System.currentTimeMillis();
                                        var13_18.f = ax.d[3];
                                        var3_3.b(ap.ch[var13_18.k.d], ax.d[0]);
                                        try {
                                            Thread.sleep(1L);
                                            return;
                                        }
                                        catch (InterruptedException var15_27) {
                                            return;
                                        }
                                    }
                                    ++var10_12;
                                    "".length();
                                    if (null == null) continue;
                                    return;
                                }
                                break;
                            }
                            ++var12_17;
                            "".length();
                            if ((107 ^ 110) != 0) continue;
                            return;
                        }
                    }
                    if (ax.i((int)ec.f) && ax.a((int)this.t) && (!ax.a(var6_6) || ax.a(var6_6.aa) && ax.i(var2_2 & ax.d[18]))) {
                        if (ax.i(var2_2 & ax.d[6])) {
                            v3 = ax.d[3];
                            "".length();
                            if (-"  ".length() > 0) {
                                return;
                            }
                        } else {
                            v3 = var12_17 = ax.d[0];
                        }
                        if (ax.i(var2_2 & ax.d[9])) {
                            v4 = ax.d[3];
                            "".length();
                            if (null != null) {
                                return;
                            }
                        } else {
                            v4 = ax.d[0];
                        }
                        var5_5 = v4;
                        var10_13 = ax.d[0];
                        while (ax.i(var10_13, ax.af.size())) {
                            var13_18 = (by)ax.af.elementAt(var10_13);
                            if (ax.f(var13_18.v) && ax.i(var13_18.p) && ax.h(var13_18.p, ax.d[3]) && ax.a((int)this.a(var2_2, var13_18.z, var13_18.n)) && ax.a((int)this.f(var13_18.z, var13_18.n)) && ax.i((int)ax.a((by)var13_18, var1_1)) && (ax.i(var12_17) && !ax.h(var13_18.aa, ax.d[3]) || ax.i(var5_5) && ax.j(var13_18.aa, ax.d[6]))) {
                                var6_6 = var13_18;
                                this.a((by)var13_18);
                                "".length();
                                if ((14 + 54 - 2 + 75 ^ 80 + 124 - 87 + 20) != 0) break;
                                return;
                            }
                            ++var10_13;
                            "".length();
                            if ("  ".length() > 0) continue;
                            return;
                        }
                    }
                    if (!ax.a(ax.z)) break block94;
                    var13_18 = ax.z;
                    if (ax.a(ax.l) && ax.a((int)ax.l.a()) && ax.i((int)ee.d)) {
                        var13_18 = ax.l;
                    }
                    if (ax.a(ax.ag) && ax.a((int)ax.ag.a()) && ax.i((int)ee.d)) {
                        var13_18 = ax.ag;
                    }
                    if (ax.a(ax.h) && ax.a((int)ax.h.a()) && ax.i((int)ee.d)) {
                        var13_18 = ax.h;
                    }
                    if (ax.a(ax.aa) && ax.a((int)ax.aa.a()) && ax.i((int)ee.d)) {
                        var13_18 = ax.aa;
                    }
                    if (!ax.i((int)var13_18.a()) || ax.a((int)ec.es) && !ax.i(this instanceof di)) break block91;
                    var14_20 = ax.d[0];
                    do {
                        block93: {
                            block102: {
                                block101: {
                                    if (ax.g(var14_20, var3_3.ao.size())) {
                                        "".length();
                                        if (null != null) {
                                            return;
                                        }
                                        break block91;
                                    }
                                    var15_28 = (cv)var3_3.ao.elementAt(var14_20);
                                    if (!ax.a(var15_28) || !ax.h(ax.e(System.currentTimeMillis() - var15_28.c, (long)var15_28.g - 300L))) break block93;
                                    if (!ax.j(var15_28.k.b, ax.d[6])) break block101;
                                    if (!((!ax.b((Object)var3_3.aq) || ax.a((int)ec.av)) && ax.g(var15_28.k.d, ax.d[62]) && !ax.d(var15_28.k.d, ax.d[63]) || ax.a((int)ec.bh) && !ax.h(var15_28.k.d, ax.d[64]) || ax.j(var15_28.k.d, ax.d[19]) && ax.i((int)ec.fw) && (!ax.i(var3_3.cl, var3_3.fd * ec.ec / ax.d[4]) || !ax.i((int)var3_3.cb)) || ax.j(var15_28.k.d, ax.d[18]) && !ax.i((int)var3_3.cb))) {
                                        var12_17 = (int)(System.currentTimeMillis() / 1000L);
                                        var10_14 = ax.d[0];
                                        do {
                                            if (ax.g(var10_14, var3_3.dy.size())) {
                                                "".length();
                                                if ("  ".length() <= 0) {
                                                    return;
                                                }
                                                break block92;
                                            }
                                            var16_29 = (au)var3_3.dy.elementAt(var10_14);
                                            if (ax.a(var16_29) && (!ax.h(var16_29.a.a, var15_28.k.f) || ax.j(var15_28.k.d, ax.d[65]) && ax.j(var16_29.a.c, ax.d[66])) && ax.g(var16_29.c - (var12_17 - var16_29.d), ax.d[6])) {
                                                "".length();
                                                if (((144 ^ 142 ^ (3 ^ 5)) & (25 ^ 38 ^ (166 ^ 129) ^ -" ".length())) != 0) {
                                                    return;
                                                }
                                                break block93;
                                            }
                                            ++var10_14;
                                            "".length();
                                        } while (((54 ^ 39) & ~(172 ^ 189)) <= (43 ^ 47));
                                        return;
                                    }
                                    break block93;
                                }
                                if (!ax.j(var15_28.k.b, ax.d[1]) || !ax.a(var6_6) || !ax.a(var6_6.aa) || !ax.d(var6_6.v, var6_6.a / ax.d[6])) break block102;
                                if (!ax.j(var15_28.k.d, ax.d[9])) break block92;
                                if (ax.i((int)ec.fw) && ax.i(var3_3.cl, var3_3.fd * ec.ec / ax.d[4])) {
                                    "".length();
                                    if (" ".length() != " ".length()) {
                                        return;
                                    } else {
                                        ** GOTO lbl214
                                    }
                                }
                                break block93;
lbl214:
                                // 2 sources

                                break block92;
                            }
                            if (ax.h(var15_28.k.d, ax.d[66]) && ax.h(var15_28.k.d, ax.d[67]) && ax.h(var15_28.k.d, ax.d[68]) && ax.h(var15_28.k.d, ax.d[69]) && !ax.j(var15_28.k.d, ax.d[70]) || !ax.a(var6_6) || ax.a(var6_6.aa) && !ax.g(var6_6.v, var6_6.a / ax.d[6]) || (!ax.h(var15_28.k.d, ax.d[66]) || ax.j(var15_28.k.d, ax.d[67])) && !ax.a((int)var6_6.l) || (!ax.h(var15_28.k.d, ax.d[68]) || ax.j(var15_28.k.d, ax.d[69])) && !ax.i((int)var6_6.r)) break block93;
                            if (!ax.j(var15_28.k.d, ax.d[70])) break block92;
                            if (ax.i((int)var6_6.u)) {
                                "".length();
                                if ((100 ^ 87 ^ (36 ^ 19)) < 0) {
                                    return;
                                } else {
                                    ** GOTO lbl224
                                }
                            }
                            break block93;
lbl224:
                            // 2 sources

                            break block92;
                        }
                        ++var14_20;
                        "".length();
                    } while (" ".length() == " ".length());
                    return;
                }
                var13_18 = var15_28;
                try {
                    Thread.sleep(500L);
                    "".length();
                }
                catch (InterruptedException var16_30) {
                    // empty catch block
                }
                if (null != null) {
                    return;
                }
            }
            if (ax.j(var13_18.k.b, ax.d[6])) {
                if (ax.h(ax.e(System.currentTimeMillis() - var13_18.c, (long)(var13_18.g - ax.d[4])))) {
                    var13_18.c = System.currentTimeMillis();
                    dl.h().e(var13_18.k.d);
                    dl.h().c();
                    if (ax.a((int)ey.bf)) {
                        var3_3.b(ap.ch[var13_18.k.d], ax.d[0]);
                        "".length();
                        if (null != null) {
                            return;
                        }
                    }
                } else {
                    var13_18.f = ax.d[3];
                    "".length();
                    if ("   ".length() < 0) {
                        return;
                    }
                }
            } else if (ax.i((int)ey.am) && ax.a(var7_7) && ax.a((int)ax.b(var7_7)) && (!ax.a(var8_8) || ax.i((int)ax.a(var3_3, var7_7)))) {
                if (ax.i(var8_8)) {
                    if (!(ax.h(var13_18.k.b, ax.d[3]) && !ax.j(var13_18.k.b, ax.d[1]) || ax.e(fo.d(var3_3.s - var7_7.s), var13_18.i + ax.d[55]) && !ax.d(fo.d(var3_3.fc - var7_7.fc), var13_18.a + ax.d[55]) || !ax.f(ax.e(System.currentTimeMillis() - this.i, 1500L)))) {
                        ax.c(var7_7);
                        this.i = System.currentTimeMillis();
                    }
                    if (ax.h(var7_7.gk, var3_3.w) && ax.h(var7_7.ej, ax.d[1])) {
                        this.v = ax.d[3];
                        dl.h().al(ax.d[1]);
                    }
                }
                var14_21 = var13_18.i;
                var10_15 = var13_18.a;
                ax.n.removeAllElements();
                ax.w.removeAllElements();
                ax.w.addElement(var7_7);
                var12_17 = ax.d[0];
                while (ax.i(var12_17, ap.dn.size()) && ax.i(ax.n.size() + ax.w.size(), var13_18.j)) {
                    var11_24 = (ec)ap.dn.elementAt(var12_17);
                    if (ax.f(var11_24.cl) && ax.h(var11_24.fj, ax.d[10]) && ax.h(var11_24.fj, ax.d[11]) && ax.h(var11_24.fj, ax.d[19]) && ax.a((int)var11_24.equals(var7_7)) && (ax.h(var11_24.ej, ax.d[1]) == false || ax.h(var3_3.ej, ax.d[1]) == false || ax.j(var11_24.ej, ax.d[3]) != false && ax.h(var3_3.ej, ax.d[3]) == false || ax.h(var3_3.gk) != false && ax.h(var3_3.gk, var11_24.w) == false || ax.h(var3_3.ge) != false && ax.h(var3_3.ge, var11_24.w) == false || ax.j(var11_24.gk, var3_3.w)) && ax.a((int)ey.f(var11_24.cx)) && ax.e(var7_7.s - var14_21, var11_24.s) && ax.e(var11_24.s, var7_7.s + var14_21) && ax.e(var7_7.fc - var10_15, var11_24.fc) && ax.e(var11_24.fc, var7_7.fc + var10_15)) {
                        ax.w.addElement(var11_24);
                    }
                    ++var12_17;
                    "".length();
                    if ("  ".length() != 0) continue;
                    return;
                }
                var12_17 = ax.d[0];
                while (ax.i(var12_17, ap.au.size()) && ax.i(ax.n.size() + ax.w.size(), var13_18.j)) {
                    var15_28 = (by)ap.au.elementAt(var12_17);
                    if (ax.i(var15_28.p) && ax.h(var15_28.p, ax.d[3]) && ax.e(var7_7.s - var14_21, var15_28.z) && ax.e(var15_28.z, var7_7.s + var14_21) && ax.e(var7_7.fc - var10_15, var15_28.n) && ax.e(var15_28.n, var7_7.fc + var10_15) && ax.i((int)ax.c(var15_28.aa, var2_2)) && (!ax.h(var1_1, ax.d[8]) || ax.j(var15_28.g, var1_1))) {
                        ax.n.addElement(var15_28);
                    }
                    ++var12_17;
                    "".length();
                    if (((104 ^ 66) & ~(55 ^ 29)) < "   ".length()) continue;
                    return;
                }
                if (ax.h(ax.e(System.currentTimeMillis() - var13_18.c, (long)(var13_18.g - ax.d[4])))) {
                    var13_18.c = System.currentTimeMillis();
                    dl.h().e(var13_18.k.d);
                    dl.h().a(ax.n, ax.w, ax.d[6]);
                    if (ax.a((int)ey.bf)) {
                        var3_3.b(ap.ch[var13_18.k.d], ax.d[0]);
                        "".length();
                        if ((53 ^ 109 ^ (237 ^ 176)) <= 0) {
                            return;
                        }
                    }
                } else {
                    var13_18.f = ax.d[3];
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
            } else {
                if (!ax.a(var6_6) || ax.h(var1_1, ax.d[8]) && !ax.j(var6_6.g, var1_1) || ax.a((int)ax.c(var6_6.aa, var2_2))) {
                    return;
                }
                if (!(ax.h(var13_18.k.b, ax.d[3]) && !ax.j(var13_18.k.b, ax.d[1]) || ax.e(fo.d(var3_3.s - var6_6.y), var13_18.i + ax.d[55]) && !ax.d(fo.d(var3_3.fc - var6_6.x), var13_18.a + ax.d[55]))) {
                    var3_3.di = null;
                    return;
                }
                var14_22 = var13_18.i;
                var10_16 = var13_18.a;
                ax.n.removeAllElements();
                ax.w.removeAllElements();
                ax.n.addElement(var6_6);
                var12_17 = ax.d[0];
                while (ax.i(var12_17, ap.au.size()) && ax.i(ax.n.size() + ax.w.size(), var13_18.j)) {
                    var15_28 = (by)ap.au.elementAt(var12_17);
                    if (ax.i(var15_28.p) && ax.h(var15_28.p, ax.d[3]) && ax.a((int)var15_28.equals(var6_6)) && ax.e(var6_6.y - ax.d[4], var15_28.y) && ax.e(var15_28.y, var6_6.y + ax.d[4]) && ax.e(var6_6.x - ax.d[60], var15_28.x) && ax.e(var15_28.x, var6_6.x + ax.d[60]) && ax.i((int)ax.c(var15_28.aa, var2_2)) && (!ax.h(var1_1, ax.d[8]) || ax.j(var15_28.g, var1_1))) {
                        ax.n.addElement(var15_28);
                    }
                    ++var12_17;
                    "".length();
                    if ((121 + 57 - 76 + 42 ^ 60 + 78 - 38 + 49) != 0) continue;
                    return;
                }
                var12_17 = ax.d[0];
                while (ax.i(var12_17, ap.dn.size()) && ax.i(ax.n.size() + ax.w.size(), var13_18.j)) {
                    var11_25 = (ec)ap.dn.elementAt(var12_17);
                    if (ax.f(var11_25.cl) && ax.h(var11_25.fj, ax.d[10]) && ax.h(var11_25.fj, ax.d[11]) && ax.h(var11_25.fj, ax.d[19]) && (ax.h(var11_25.ej, ax.d[1]) == false || ax.h(var3_3.ej, ax.d[1]) == false || ax.j(var11_25.ej, ax.d[3]) != false && ax.h(var3_3.ej, ax.d[3]) == false || ax.h(var3_3.gk) != false && ax.h(var3_3.gk, var11_25.w) == false || ax.h(var3_3.ge) != false && ax.h(var3_3.ge, var11_25.w) == false || ax.j(var11_25.gk, var3_3.w)) && ax.a((int)ey.f(var11_25.cx)) && ax.e(var6_6.z - var14_22, var11_25.s) && ax.e(var11_25.s, var6_6.z + var14_22) && ax.e(var6_6.n - var10_16, var11_25.fc) && ax.e(var11_25.fc, var6_6.n + var10_16)) {
                        ax.w.addElement(var11_25);
                    }
                    ++var12_17;
                    "".length();
                    if ("   ".length() != 0) continue;
                    return;
                }
                if (ax.h(ax.e(System.currentTimeMillis() - var13_18.c, (long)(var13_18.g - ax.d[4])))) {
                    var13_18.c = System.currentTimeMillis();
                    dl.h().e(var13_18.k.d);
                    dl.h().a(ax.n, ax.w, ax.d[3]);
                    if (ax.a((int)ey.bf)) {
                        var3_3.b(ap.ch[var13_18.k.d], ax.d[0]);
                        "".length();
                        if (-" ".length() != -" ".length()) {
                            return;
                        }
                    }
                } else {
                    var13_18.f = ax.d[3];
                }
            }
            this.y = System.currentTimeMillis();
        }
    }

    private static int e(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean j(int n2, int n3) {
        return n2 == n3;
    }

    protected static boolean a(ec ec2, ec ec3) {
        int n2;
        if (ax.h(ec3.fj, d[10]) && ax.h(ec3.fj, d[11]) && ax.h(ec3.fj, d[19]) && (!ax.h(ec3.ej, d[1]) || !ax.h(ec2.ej, d[1]) || ax.j(ec3.ej, d[3]) && !ax.h(ec2.ej, d[3]) || ax.j(ec3.ej, d[11]) && !ax.h(ec2.ej, d[9]) || ax.j(ec3.ej, d[9]) && !ax.h(ec2.ej, d[11]) || ax.h(ec2.gk) && !ax.h(ec2.gk, ec3.w) || ax.h(ec2.ge) && !ax.h(ec2.ge, ec3.w) || ax.h(ec3.gk) && ax.j(ec3.gk, ec2.w))) {
            n2 = d[3];
            "".length();
            if ((0x6B ^ 0x6F) < ((0x18 ^ 0x2B) & ~(0x1C ^ 0x2F))) {
                return ((0xD1 ^ 0x81) & ~(0xD7 ^ 0x87)) != 0;
            }
        } else {
            n2 = d[0];
        }
        return n2 != 0;
    }

    public static void a(long l2) {
        try {
            Thread.sleep(l2);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (((0x6F ^ 0xA ^ (0x6D ^ 0x1E)) & (0 + 3 - -114 + 41 ^ 1 + 0 - -118 + 17 ^ -" ".length())) < 0) {
            return;
        }
    }

    private static int f(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final int a(boolean bl2, boolean bl3, boolean bl4) {
        if (ax.i(this.t ? 1 : 0)) {
            return d[8];
        }
        int n2 = d[0];
        if (ax.i(bl2 ? 1 : 0)) {
            n2 = d[3];
        }
        if (ax.i(bl3 ? 1 : 0)) {
            n2 |= d[6];
        }
        if (ax.i(bl4 ? 1 : 0)) {
            n2 |= d[9];
        }
        return n2;
    }

    protected static void a(boolean bl2) {
        ec ec2 = ec.n();
        if (ax.i(bl2 ? 1 : 0)) {
            if (ax.i(ah ? 1 : 0)) {
                if (ax.g(ax.c(System.currentTimeMillis() - aj, 2L))) {
                    return;
                }
                ah = d[0];
                "".length();
                if (((138 + 136 - 83 + 36 ^ 70 + 178 - 138 + 72) & (108 + 55 - -32 + 17 ^ 10 + 11 - -35 + 73 ^ -" ".length())) == -" ".length()) {
                    return;
                }
            } else if (ax.i(ec.ct ? 1 : 0) && ax.f(ap.an.size())) {
                int n2 = d[0];
                while (ax.i(n2, ap.an.size())) {
                    dg dg2 = (dg)ap.an.elementAt(n2);
                    if (ax.h(dg2.d, ec2.w) && ax.a(dg2.b) && ax.f(dg2.b.cl) && ax.j(dg2.b.dk.c, d[18])) {
                        ap.a("Ch\u1edd h\u1ed3i sinh!");
                        aj = System.currentTimeMillis();
                        ah = d[3];
                        return;
                    }
                    ++n2;
                    "".length();
                    if (" ".length() >= 0) continue;
                    return;
                }
            }
        }
        try {
            Thread.sleep(5L);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if ((0x26 ^ 0x23) == 0) {
            return;
        }
        af.removeAllElements();
        g = d[0];
        bo.a = d[3];
        if (ax.i(fa.b ? 1 : 0) && ax.f(ec.n().e)) {
            dl.h().r();
            "".length();
            if (((0x46 ^ 0x35 ^ (0x13 ^ 0x47)) & (3 ^ 0xF ^ (0x34 ^ 0x1F) ^ -" ".length())) != 0) {
                return;
            }
        } else {
            dl.h().g();
            de.g();
        }
        bo.a = d[0];
        try {
            Thread.sleep(5L);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if ("   ".length() <= -" ".length()) {
            return;
        }
    }

    private static boolean a(Object object, Object object2) {
        return object != object2;
    }

    static {
        ax.o();
        b = 0L;
        af = new ad();
        a = new ad();
        ae = d[0];
        ah = d[0];
        aj = -1L;
        n = new ad();
        w = new ad();
        e = new ad();
    }

    public static void m() {
        af.removeAllElements();
    }

    protected void b() {
        g = d[0];
        ey.at = System.currentTimeMillis();
    }

    private void a(byte[] byArray) {
        this.ac = new ByteArrayInputStream(byArray);
        this.ad = new DataInputStream(this.ac);
    }

    private static void o() {
        d = new int[75];
        ax.d[0] = (0x25 ^ 0x30 ^ (0x19 ^ 0x44)) & (0x7F ^ 0x71 ^ (0xDA ^ 0x9C) ^ -" ".length());
        ax.d[1] = "   ".length();
        ax.d[2] = 0x7A ^ 0x70;
        ax.d[3] = " ".length();
        ax.d[4] = 0xFB ^ 0x9F;
        ax.d[5] = 0x7F ^ 0x26;
        ax.d[6] = "  ".length();
        ax.d[7] = -(0xFFFFAC7B & 0x5797) & (0xFFFFBF7F & 0x45BE);
        ax.d[8] = -" ".length();
        ax.d[9] = 23 + 5 - -54 + 67 ^ 31 + 22 - 1 + 93;
        ax.d[10] = 62 + 10 - 15 + 79 ^ 96 + 77 - 95 + 56;
        ax.d[11] = 2 + 38 - -108 + 8 ^ 66 + 98 - 159 + 148;
        ax.d[12] = 55 + 65 - 42 + 61;
        ax.d[13] = (0xEA ^ 0xBF) + "   ".length() - (0x54 ^ 4) + (102 + 131 - 193 + 100);
        ax.d[14] = 0x1F ^ 0x54 ^ (0xE4 ^ 0x8A);
        ax.d[15] = 0x39 ^ 0x1A;
        ax.d[16] = 59 + 23 - 78 + 181 ^ 105 + 33 - 10 + 52;
        ax.d[17] = -(0xFFFFEDEB & 0x563F) & (0xFFFFC7BB & 0x7DFE);
        ax.d[18] = 0x44 ^ 0x42;
        ax.d[19] = 99 + 134 - 102 + 29 ^ 17 + 43 - 51 + 166;
        ax.d[20] = 0xA5 ^ 0xB3;
        ax.d[21] = 0xA3 ^ 0x9C ^ (0xB ^ 0x57);
        ax.d[22] = 0xEE ^ 0x89;
        ax.d[23] = 128 + 53 - 63 + 16;
        ax.d[24] = 46 + 122 - 91 + 58;
        ax.d[25] = (0x75 ^ 7) + (0x22 ^ 8) - (2 ^ 0x7A) + (0x24 ^ 0x40);
        ax.d[26] = (0x75 ^ 0x4B) + " ".length() - (" ".length() & ~" ".length()) + (0x2A ^ 0x60);
        ax.d[27] = 0 + 40 - -157 + 5;
        ax.d[28] = 0x3D ^ 0x35;
        ax.d[29] = 0xFFFFAFD6 & 0x55BD;
        ax.d[30] = 0xFFFFEA56 & 0x17B9;
        ax.d[31] = -(0xFFFFFB54 & 0x4CEF) & (0xFFFFEFFF & 0x5DEF);
        ax.d[32] = -(0xFFFFD53F & 0x7FC8) & (0xFFFFFF3F & 0x57EF);
        ax.d[33] = 0xFFFF972F & 0x6DD4;
        ax.d[34] = 0xFFFF8D9C & 0x777F;
        ax.d[35] = -(0xFFFFFFEF & 0x6052) & (0xFFFFF76F & 0x6FFD);
        ax.d[36] = -(0xFFFFFE5F & 0x6DF7) & (0xFFFFEFDE & 0x7EFF);
        ax.d[37] = -(0xFFFFD1F3 & 0x7E8D) & (0xFFFFDF9F & 0x77F4);
        ax.d[38] = 0xFFFFB7A7 & 0x4AF8;
        ax.d[39] = 0xFFFFDBEE & 0x277D;
        ax.d[40] = 0xFFFF83EF & 0x7F94;
        ax.d[41] = 0xFFFF8BD8 & 0x75BF;
        ax.d[42] = 0xFFFFB5B3 & 0x4BFC;
        ax.d[43] = -(0xFFFFFD57 & 0x7EEB) & (0xFFFFFFFE & 0x7F7F);
        ax.d[44] = 0xFFFFF3DD & 0xF76;
        ax.d[45] = 0xFFFFB1EF & 0x4F78;
        ax.d[46] = -(0xFFFFF6FC & 0x3947) & (0xFFFFB9D7 & 0x77EB);
        ax.d[47] = 0xFFFFF3BC & 0xFDF;
        ax.d[48] = 0xFFFFC3F2 & 0x3E7D;
        ax.d[49] = 0xFFFFBFFE & 0x42DD;
        ax.d[50] = -(0xFFFFB227 & 0x6DDC) & (0xFFFFA67F & 0x7BDB);
        ax.d[51] = 0xFFFFEFF6 & 0x12FD;
        ax.d[52] = 0xFFFF9254 & 0x6FEB;
        ax.d[53] = 98 + 122 - 197 + 177;
        ax.d[54] = 0x3F ^ 0x17;
        ax.d[55] = 0x75 ^ 0x62 ^ (0x35 ^ 0x3C);
        ax.d[56] = -(0xFFFFFC36 & 0x3FDD) & (0xFFFFFFFF & 0x3FFB);
        ax.d[57] = 84 + 87 - 101 + 87;
        ax.d[58] = 145 + 121 - 125 + 17;
        ax.d[59] = (0x39 ^ 0x65) + (0xA0 ^ 0x99) - (0xBF ^ 0x8B) + (0xA0 ^ 0x9E);
        ax.d[60] = 0x43 ^ 0x4C ^ (0xA6 ^ 0x9B);
        ax.d[61] = -(0xFFFFF6BD & 0x7D63) & (0xFFFFF529 & 0x7FF7);
        ax.d[62] = 121 + 160 - 230 + 178 ^ 3 + 163 - 122 + 122;
        ax.d[63] = 0xF1 ^ 0xB9;
        ax.d[64] = 0x2B ^ 0x34;
        ax.d[65] = 0xBF ^ 0xAC ^ (0xA3 ^ 0x8A);
        ax.d[66] = 0x81 ^ 0x86;
        ax.d[67] = 0x25 ^ 0x35;
        ax.d[68] = 0xF ^ 0x16;
        ax.d[69] = 0x58 ^ 0x56 ^ (0x33 ^ 0x1F);
        ax.d[70] = 0xBF ^ 0x94;
        ax.d[71] = 18 + 50 - -46 + 73 + (0x7D ^ 0x3C) - (115 + 193 - 276 + 170) + (30 + 25 - 53 + 166);
        ax.d[72] = 0xA5 ^ 0xB6;
        ax.d[73] = -"  ".length();
        ax.d[74] = 0x66 ^ 3 ^ (0xE7 ^ 0x8E);
    }
}

