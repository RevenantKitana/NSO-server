/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class em
extends ao
implements bv {
    public static cc a;
    public static int b;
    private int[] aw;
    private static Image ax;
    public static boolean c;
    public static as d;
    private static Image[][] ay;
    public static boolean e;
    public static em f;
    public static bt g;
    private static Image[] az;
    private static int ba;
    public static int h;
    private static int bb;
    private static long bc;
    public static int i;
    private boolean bd;
    public static boolean[] j;
    public static int k;
    public static et l;
    private static Image[] be;
    public static int m;
    public static boolean n;
    private static int[] bf;
    public static boolean o;
    private static Image bg;
    public static boolean p;
    private static int bh;
    public static boolean q;
    public static int r;
    public static boolean s;
    private static Image[][] bi;
    private static boolean bj;
    public static long t;
    public static boolean u;
    private static int bk;
    public static boolean v;
    private static Image bl;
    private static int bm;
    public static boolean w;
    public static Image[] x;
    private static int bn;
    private int[] bo;
    public static int y;
    private static Image[] bp;
    public static long z;
    public static int aa;
    private static int[] bq;
    private static int br;
    public static c ab;
    public static boolean ac;
    public static boolean ad;
    private static final int[] bs;
    public static int ae;
    public static boolean[] af;
    public static ch[] ag;
    public static boolean ah;
    public static boolean ai;
    public static int aj;
    private static int[] bt;
    public static ae ak;
    private static int bu;
    public static boolean al;
    private static int bv;
    private static int[] bw;
    private static long bx;
    public static boolean am;
    public static int an;
    private static int[] by;
    public static int ao;
    public static int ap;
    private static int[] bz;
    private static int[] ca;
    private bh cb = new bh();
    private static int cc;
    public static int aq;
    public static ar ar;
    public static int as;
    private static long cd;
    private static boolean[] ce;
    public static ef at;
    private int[] cf;
    private static Image[] cg;
    public static aa au;
    private static int ch;
    public static int av;

    public final void e() {
        if (em.i(e ? 1 : 0)) {
            int n2 = bs[2];
            while (em.j(n2, bs[1])) {
                if (em.h(this.bo[n2], bs[11])) {
                    int n3 = n2;
                    int n4 = this.bo[n3];
                    this.bo[n3] = n4 + bs[0];
                    int n5 = n4;
                    if (em.f(this.bo[n2], bs[10])) {
                        this.bo[n2] = bs[11];
                    }
                    if (em.i(n2)) {
                        int n6 = n2;
                        int n7 = this.cf[n6];
                        this.cf[n6] = n7 - bs[0];
                        n5 = n7;
                        "".length();
                        if ((0 ^ 4) <= "  ".length()) {
                            return;
                        }
                    } else {
                        int n8 = n2;
                        int n9 = this.cf[n8];
                        this.cf[n8] = n9 + bs[0];
                        n5 = n9;
                    }
                    int n10 = n2;
                    int n11 = this.aw[n10];
                    this.aw[n10] = n11 - bs[0];
                    n5 = n11;
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
        }
    }

    private static boolean d(int n2) {
        return n2 >= 0;
    }

    public static em f() {
        if (em.b(f)) {
            f = new em();
        }
        return f;
    }

    public final void g() {
        ad = bs[2];
        this.bd = bs[0];
    }

    public static Image a(String string) {
        string = "/x" + bh.b + string;
        Image image = null;
        try {
            image = Image.createImage((String)string);
            "".length();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (((39 + 127 - 102 + 109 ^ 105 + 22 - -57 + 0) & (82 + 105 - 121 + 65 ^ 82 + 143 - 183 + 108 ^ -" ".length())) != 0) {
            return null;
        }
        return image;
    }

    public static boolean a(int n2, int n3, int n4, int n5) {
        int n6;
        int n7 = ae + ap.fb;
        int n8 = ap.s + an;
        if ((!em.i(ah ? 1 : 0) || em.e(q ? 1 : 0)) && em.f(n7, n2) && em.g(n7, n2 + n4) && em.f(n8, n3) && em.g(n8, n3 + n5)) {
            n6 = bs[0];
            "".length();
            if ("   ".length() <= 0) {
                return ((43 + 50 - 84 + 128 ^ 125 + 80 - 150 + 107) & (0xDF ^ 0xC4 ^ (0x4A ^ 0x7A) ^ -" ".length())) != 0;
            }
        } else {
            n6 = bs[2];
        }
        return n6 != 0;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    public static boolean d(int n2, int n3) {
        int n4;
        int n5;
        if (em.e(e ? 1 : 0)) {
            return bs[2];
        }
        if (em.k(bf[bs[2]], bs[11])) {
            n5 = bs[2];
            "".length();
            if ("  ".length() > "  ".length()) {
                return ((0x80 ^ 0xBF ^ " ".length()) & (144 + 122 - 170 + 86 ^ 56 + 68 - 26 + 38 ^ -" ".length())) != 0;
            }
        } else {
            n5 = bs[0];
        }
        if (em.h(bf[n4 = n5], bs[11])) {
            return bs[2];
        }
        em.bf[n4] = bs[2];
        em.bz[n4] = n2;
        em.bt[n4] = n3;
        return bs[0];
    }

    private static boolean f(int n2, int n3) {
        return n2 >= n3;
    }

    public static void a(String string, int n2, Object object, int n3, Object object2) {
        g.a(string, new fr(ag.k, f, n2, object), new fr("", f, n2, object), new fr(ag.bd, f, n3, object2));
        d = g;
    }

    private static boolean f(int n2) {
        return n2 > 0;
    }

    public void d() {
    }

    public final void c() {
        long l2;
        if (em.f(as)) {
            t = System.currentTimeMillis();
            if (em.d(em.a(t - z, 1000L))) {
                if (em.i(as -= bs[0])) {
                    dp.g().h();
                }
                z = t;
                "".length();
                if (" ".length() <= 0) {
                    return;
                }
            }
        } else if (em.e(dp.g().n ? 1 : 0) && em.f(em.a(System.currentTimeMillis() - dp.g().l, 300000L))) {
            dp.g().h();
        }
        if (em.d(em.a((l2 = System.currentTimeMillis()) - bx, 780L)) && em.i(p ? 1 : 0)) {
            bx = l2;
            p = bs[0];
            "".length();
            if (((0xA4 ^ 0x92) & ~(0x10 ^ 0x26)) != 0) {
                return;
            }
        } else {
            p = bs[2];
        }
        if (em.d(em.a(l2 - bc, 7800L)) && em.i(al ? 1 : 0)) {
            bc = l2;
            al = bs[0];
            "".length();
            if ("  ".length() >= (0xA7 ^ 0xBB ^ (0x4D ^ 0x55))) {
                return;
            }
        } else {
            al = bs[2];
        }
        if (em.f(i)) {
            i -= bs[0];
        }
        if (em.i(aa += bs[0], bs[12])) {
            aa = bs[2];
        }
        if (em.a(at)) {
            if (em.a(d)) {
                d.a();
                "".length();
                if (-(134 + 139 - 179 + 79 ^ 10 + 134 - 60 + 85) > 0) {
                    return;
                }
            } else if (em.e(em.a.m ? 1 : 0)) {
                cc cc2 = a;
                if (em.e(cc2.q) && em.i(cc2.f ? 1 : 0)) {
                    if (em.h(cc.t += cc2.q / bs[13])) {
                        cc.t = bs[2];
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else if (em.i(cc.t, cc.r)) {
                        cc.t = cc.r;
                        "".length();
                        if (((0xC4 ^ 0x87) & ~(0x83 ^ 0xC0)) > 0) {
                            return;
                        }
                    } else {
                        cc.e = cc.t;
                    }
                    cc2.q = cc2.q * bs[14] / bs[15];
                    if (em.j(cc2.q, bs[13]) && em.i(cc2.q, bs[16])) {
                        cc2.q = bs[2];
                    }
                }
                if (em.h(cc.e, cc.t) && em.i(cc2.f ? 1 : 0)) {
                    cc2.aa = cc.t - cc.e << bs[1];
                    cc2.y += cc2.aa;
                    cc.e += cc2.y >> bs[9];
                    cc2.y &= bs[6];
                }
                if (em.i(cc2.ab, cc2.a)) {
                    int n2 = cc2.ab - cc2.a >> bs[0];
                    if (em.g(n2)) {
                        n2 = bs[0];
                    }
                    cc2.ab -= n2;
                }
                if (em.e(cc.d) && em.h(cc.d >>= bs[0])) {
                    cc.d = bs[2];
                }
                if (em.f(cc2.l)) {
                    cc2.l -= bs[0];
                    ap.cy = bs[2];
                    if (em.i(cc2.l)) {
                        fr fr2;
                        cc2.m = bs[2];
                        if (em.d(cc2.c) && em.a(fr2 = (fr)cc2.i.elementAt(cc2.c))) {
                            fr2.c();
                        }
                    }
                }
                cc2 = a;
                if (em.e(cc2.m ? 1 : 0)) {
                    int n3 = bs[2];
                    if (em.i(j[bs[1]]) && em.i(j[bs[9]])) {
                        if (em.i(j[bs[17]]) && em.i(j[bs[18]])) {
                            if (em.e(j[bs[10]])) {
                                if (em.a(cc2.z)) {
                                    if (em.f(cc2.z.g)) {
                                        if (em.a(cc2.z.i, ap.s())) {
                                            ap.s().b(cc2.z.g, cc2.z.c);
                                            "".length();
                                            if ("  ".length() <= ((0xF ^ 0x33) & ~(0x2A ^ 0x16))) {
                                                return;
                                            }
                                        } else {
                                            cc2.a(cc2.z.g, cc2.z.c);
                                            "".length();
                                            if (" ".length() == 0) {
                                                return;
                                            }
                                        }
                                    }
                                } else {
                                    cc2.l = bs[1];
                                    "".length();
                                    if ((0x7D ^ 0x78) == 0) {
                                        return;
                                    }
                                }
                            } else if (em.e(j[bs[19]])) {
                                if (em.f(cc2.u.g)) {
                                    cc2.a(cc2.u.g, cc2.u.c);
                                    "".length();
                                    if ((12 + 34 - -48 + 36 ^ 9 + 13 - -17 + 95) == " ".length()) {
                                        return;
                                    }
                                } else {
                                    cc2.l = bs[1];
                                    "".length();
                                    if ("  ".length() == 0) {
                                        return;
                                    }
                                }
                            } else if (em.i(cc2.o ? 1 : 0) && (!em.i(j[bs[20]]) || em.e(ef.a(cc2.g) ? 1 : 0))) {
                                cc2.m = bs[2];
                                fb.a();
                                "".length();
                                if (" ".length() == 0) {
                                    return;
                                }
                            }
                        } else {
                            n3 = bs[0];
                            cc2.c += bs[0];
                            if (em.i(cc2.c, cc2.i.size() - bs[0])) {
                                cc2.c = bs[2];
                                "".length();
                                if (" ".length() != " ".length()) {
                                    return;
                                }
                            }
                        }
                    } else {
                        n3 = bs[0];
                        cc2.c -= bs[0];
                        if (em.h(cc2.c)) {
                            cc2.c = cc2.i.size() - bs[0];
                        }
                    }
                    cc2.z = null;
                    if (em.e(ap.cy ? 1 : 0) && em.i(w ? 1 : 0) && em.h(cc2.c, bs[11])) {
                        fr fr3 = (fr)cc2.i.elementAt(cc2.c);
                        bc bc2 = b.f().b(fr3.d);
                        if (em.a(bc2) && em.k(bc2.c, bs[1])) {
                            cc2.z = new fr(ag.hw, cc2, bs[21], bc2);
                        }
                    }
                    if (em.e(n3)) {
                        cc.t = cc2.c * cc2.n + cc2.n - aj / bs[1];
                        if (em.i(cc.t, cc.r)) {
                            cc.t = cc.r;
                        }
                        if (em.h(cc.t)) {
                            cc.t = bs[2];
                        }
                        if (!em.h(cc2.c, cc2.i.size() - bs[0]) || em.i(cc2.c)) {
                            cc.e = cc.t;
                        }
                    }
                    if (em.i(cc2.o ? 1 : 0) && em.e(q ? 1 : 0) && em.i(em.b(cc2.h, cc2.a, cc2.b, cc2.j) ? 1 : 0) && em.i(cc2.f ? 1 : 0)) {
                        cc2.x = cc2.v = bs[2];
                        cc2.f = bs[2];
                        cc2.m = bs[2];
                        q = bs[2];
                        "".length();
                        if ((0x6B ^ 0x6F) <= -" ".length()) {
                            return;
                        }
                    } else {
                        if (em.e(ah ? 1 : 0)) {
                            if (em.i(cc2.f ? 1 : 0) && em.e(em.b(cc2.h, cc2.a, cc2.b, cc2.j) ? 1 : 0)) {
                                int n4;
                                int n5 = bs[2];
                                while (em.j(n5, cc2.k.length)) {
                                    cc2.k[em.bs[2]] = ae;
                                    ++n5;
                                    "".length();
                                    if (((0x4A ^ 7 ^ (0xE8 ^ 0x88)) & (0xF8 ^ 0x9E ^ (0x54 ^ 0x1F) ^ -" ".length())) >= 0) continue;
                                    return;
                                }
                                cc2.v = ae;
                                cc2.f = bs[0];
                                if (em.e(cc2.q)) {
                                    n4 = bs[0];
                                    "".length();
                                    if (null != null) {
                                        return;
                                    }
                                } else {
                                    n4 = bs[2];
                                }
                                cc2.s = n4;
                                cc2.q = bs[2];
                                "".length();
                                if (" ".length() > " ".length()) {
                                    return;
                                }
                            } else if (em.e(cc2.f ? 1 : 0)) {
                                int n6;
                                cc2.x += bs[0];
                                if (em.i(cc2.x, bs[10]) && em.k(cc2.v, ae) && em.i(cc2.s ? 1 : 0)) {
                                    cc2.v = bs[22];
                                    cc2.c = (cc.t + ae - cc2.h) / cc2.n;
                                }
                                if (em.e(n6 = ae - cc2.k[bs[2]]) && em.h(cc2.c, bs[11])) {
                                    cc2.c = bs[11];
                                }
                                int n7 = cc2.k.length - bs[0];
                                while (em.f(n7)) {
                                    int[] nArray = cc2.k;
                                    nArray[n7] = nArray[n7 - bs[0]];
                                    --n7;
                                    "".length();
                                    if (" ".length() >= 0) continue;
                                    return;
                                }
                                cc2.k[em.bs[2]] = ae;
                                if (em.h(cc.t -= n6)) {
                                    cc.t = bs[2];
                                }
                                if (em.i(cc.t, cc.r)) {
                                    cc.t = cc.r;
                                }
                                if (!em.d(cc.e) || em.i(cc.e, cc.r)) {
                                    n6 /= bs[1];
                                }
                                cc.e -= n6;
                            }
                        }
                        if (em.e(q ? 1 : 0) && em.e(cc2.f ? 1 : 0)) {
                            int n8 = ae - cc2.k[bs[2]];
                            q = bs[2];
                            if (em.j(fo.d(n8), bs[23]) && em.j(fo.d(ae - cc2.v), bs[23]) && em.i(cc2.s ? 1 : 0)) {
                                cc2.q = bs[2];
                                cc.t = cc.e;
                                cc2.v = bs[22];
                                cc2.c = (cc.t + ae - cc2.h) / cc2.n;
                                cc2.x = bs[2];
                                cc2.l = bs[15];
                                "".length();
                                if (null != null) {
                                    return;
                                }
                            } else if (em.h(cc2.c, bs[11]) && em.i(cc2.x, bs[10])) {
                                cc2.x = bs[2];
                                cc2.l = bs[0];
                                "".length();
                                if (-" ".length() >= "   ".length()) {
                                    return;
                                }
                            } else if (em.k(cc2.c, bs[11]) && em.i(cc2.s ? 1 : 0)) {
                                if (em.h(cc.e)) {
                                    cc.t = bs[2];
                                    "".length();
                                    if (-" ".length() >= ((145 + 170 - 310 + 245 ^ 37 + 110 - 106 + 125) & (102 + 200 - 277 + 178 ^ 94 + 19 - -18 + 20 ^ -" ".length()))) {
                                        return;
                                    }
                                } else if (em.i(cc.e, cc.r)) {
                                    cc.t = cc.r;
                                    "".length();
                                    if (-"  ".length() >= 0) {
                                        return;
                                    }
                                } else {
                                    int n9;
                                    int n10 = ae - cc2.k[bs[2]] + (cc2.k[bs[2]] - cc2.k[bs[0]]) + (cc2.k[bs[0]] - cc2.k[bs[1]]);
                                    if (em.i(n10, bs[15])) {
                                        n9 = bs[15];
                                        "".length();
                                        if ((0xEB ^ 0xA8 ^ (0x56 ^ 0x11)) < " ".length()) {
                                            return;
                                        }
                                    } else if (em.j(n10, bs[24])) {
                                        n9 = bs[24];
                                        "".length();
                                        if (null != null) {
                                            return;
                                        }
                                    } else {
                                        n9 = bs[2];
                                    }
                                    cc2.q = -n9 * bs[13];
                                }
                            }
                            cc2.f = bs[2];
                            cc2.x = bs[2];
                            q = bs[2];
                        }
                        em.h();
                        em.m();
                    }
                }
            }
            if (em.i(ad ? 1 : 0)) {
                at.e();
            }
            at.f();
        }
        long l3 = System.currentTimeMillis();
        if (em.e(an.j ? 1 : 0) && em.f(em.a(l3, an.e))) {
            an.j = bs[2];
            try {
                if (em.f(an.g)) {
                    ap.s();
                    "".length();
                    f.g();
                }
                "".length();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            if ((0x73 ^ 0x79 ^ (0xAF ^ 0xA1)) < -" ".length()) {
                return;
            }
        }
        if (em.f(fb.e) && em.i(fb.e -= bs[0])) {
            fb.a();
        }
        if (em.e(this.bd ? 1 : 0)) {
            this.n();
        }
        fk.a.repaint();
        fk.a.serviceRepaints();
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void a(bh bh2) {
        if (em.i(e ? 1 : 0)) {
            int n2 = bs[2];
            while (em.j(n2, bs[1])) {
                if (em.h(this.bo[n2], bs[11]) && em.e(em.e(this.cf[n2], this.aw[n2]) ? 1 : 0)) {
                    bh2.a(bi[n2][this.bo[n2]], this.cf[n2], this.aw[n2], bs[8]);
                }
                ++n2;
                "".length();
                if ("  ".length() > ((60 + 191 - 188 + 129 ^ 126 + 158 - 146 + 59) & (0xD0 ^ 0xC6 ^ (0x59 ^ 0x4A) ^ -" ".length()))) continue;
                return;
            }
        }
    }

    public static void c(int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        block79: {
            n6 = bs[2];
            n5 = bs[2];
            n4 = bs[2];
            b = n2;
            switch (n2) {
                case 2: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if (-" ".length() > ((0xA7 ^ 0x8E) & ~(0x44 ^ 0x6D))) {
                        return;
                    }
                    break block79;
                }
                case 3: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if ((0x79 ^ 0x29 ^ (0x2F ^ 0x7B)) != (46 + 137 - 155 + 122 ^ 74 + 60 - 106 + 118)) {
                        return;
                    }
                    break block79;
                }
                case 4: {
                    n6 = bs[14];
                    n5 = bs[18];
                    "".length();
                    if ((0xA8 ^ 0xAC) == (2 ^ 6)) break;
                    return;
                }
                case 5: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[0];
                    nArray[em.bs[1]] = bs[0];
                    nArray[em.bs[8]] = bs[0];
                    ca = nArray;
                    "".length();
                    if ("  ".length() == 0) {
                        return;
                    }
                    break block79;
                }
                case 6: {
                    n6 = bs[19];
                    "".length();
                    if (-"  ".length() <= 0) break;
                    return;
                }
                case 7: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if (((0x26 ^ 0x14) & ~(0x6D ^ 0x5F)) != 0) {
                        return;
                    }
                    break block79;
                }
                case 8: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if ((109 + 39 - 124 + 173 ^ 109 + 172 - 95 + 6) == 0) {
                        return;
                    }
                    break block79;
                }
                case 9: {
                    n6 = bs[30];
                    n5 = bs[15];
                    n4 = bs[18];
                    "".length();
                    if (-" ".length() < ((0x9F ^ 0xA1) & ~(0x6B ^ 0x55))) break;
                    return;
                }
                case 10: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[0];
                    nArray[em.bs[1]] = bs[0];
                    nArray[em.bs[8]] = bs[0];
                    ca = nArray;
                    "".length();
                    if ("   ".length() < " ".length()) {
                        return;
                    }
                    break block79;
                }
                case 11: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if (((0x81 ^ 0xAD ^ (0x99 ^ 0xA5)) & (72 + 29 - -16 + 15 ^ 60 + 77 - 18 + 29 ^ -" ".length())) != ((0x54 ^ 0x5D ^ (0x5F ^ 0x73)) & ("  ".length() ^ (0x9D ^ 0xBA) ^ -" ".length()))) {
                        return;
                    }
                    break block79;
                }
                case 12: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if (-" ".length() > 0) {
                        return;
                    }
                    break block79;
                }
                case 13: {
                    n6 = bs[38];
                    "".length();
                    if (" ".length() != 0) break;
                    return;
                }
                case 14: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if ("   ".length() <= 0) {
                        return;
                    }
                    break block79;
                }
                case 15: {
                    int[] nArray = new int[bs[9]];
                    nArray[em.bs[2]] = bs[0];
                    nArray[em.bs[0]] = bs[1];
                    nArray[em.bs[1]] = bs[8];
                    nArray[em.bs[8]] = bs[9];
                    ca = nArray;
                    "".length();
                    if ("   ".length() <= "  ".length()) {
                        return;
                    }
                    break block79;
                }
                case 16: {
                    "".length();
                    if (((173 + 172 - 250 + 139 ^ 4 + 40 - -103 + 26) & (117 + 45 - 66 + 125 ^ 6 + 148 - 46 + 46 ^ -" ".length())) != "   ".length()) break;
                    return;
                }
                default: {
                    "".length();
                    if ((0x15 ^ 0x11) < 0) {
                        return;
                    }
                    break block79;
                }
            }
            int[] nArray = new int[bs[9]];
            nArray[em.bs[2]] = bs[0];
            nArray[em.bs[0]] = bs[1];
            nArray[em.bs[1]] = bs[8];
            nArray[em.bs[8]] = bs[9];
            ca = nArray;
        }
        bn = d.d[b];
        try {
            block80: {
                if (!em.i(e ? 1 : 0)) break block80;
                be = new Image[bs[9]];
                bw = new int[bs[9]];
                n3 = bs[2];
                while (em.j(n3, bs[9])) {
                    try {
                        if (em.h(d.e[b][n3], bs[11])) {
                            em.be[n3] = ay[n3][d.e[b][n3]];
                        }
                        "".length();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if (-(0x41 ^ 0x45) >= 0) {
                        return;
                    }
                    if (em.a(be[n3])) {
                        em.bw[n3] = bh.a(be[n3]);
                    }
                    ++n3;
                    "".length();
                    if ("   ".length() != 0) continue;
                    return;
                }
                if (em.k(b, bs[15])) {
                    em.be[em.bs[0]] = ay[bs[2]][bs[14]];
                    em.be[em.bs[1]] = ay[bs[2]][bs[14]];
                    em.bw[em.bs[0]] = bh.a(be[bs[0]]);
                    em.bw[em.bs[1]] = bh.a(be[bs[1]]);
                }
                if (em.k(b, bs[19])) {
                    em.be[em.bs[8]] = ay[bs[8]][bs[14]];
                    em.bw[em.bs[8]] = bh.a(be[bs[8]]);
                }
                if (em.k(b, bs[33])) {
                    if (em.e(w ? 1 : 0)) {
                        bv = aq - bh.b(be[bs[2]]);
                        bb = bv - bh.b(be[bs[0]]);
                        "".length();
                        if ((0x59 ^ 0x5D) < 0) {
                            return;
                        }
                    } else {
                        bv = aq - bh.b(be[bs[2]]) - bs[32];
                        bb = bv - bh.b(be[bs[0]]);
                    }
                }
                if (em.k(b, bs[6]) && em.e(w ? 1 : 0)) {
                    bv = aq - bh.b(be[bs[2]]);
                    bb = bv - bh.b(be[bs[0]]) + bs[13];
                }
                if (em.k(b, bs[30])) {
                    if (em.e(w ? 1 : 0)) {
                        bv = aq - bh.b(be[bs[2]]);
                        bb = bv - bh.b(be[bs[0]]) + bs[13];
                        "".length();
                        if ("  ".length() >= "   ".length()) {
                            return;
                        }
                    } else {
                        bv = aq - bh.b(be[bs[2]]) - bs[39];
                        bb = bv - bh.b(be[bs[0]]) + bs[13];
                    }
                }
            }
            if (em.d(b) && em.g(b, bs[0])) {
                bg = bp[bs[2]];
                ax = az[bs[2]];
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                bg = null;
                ax = null;
            }
            if (em.k(b, bs[1])) {
                bg = bp[bs[0]];
                ax = az[bs[0]];
            }
            if (!em.h(b, bs[25]) || !em.h(b, bs[31]) || em.k(b, bs[19])) {
                if (em.k(de.i, bs[23])) {
                    bg = null;
                    "".length();
                    if (((0x2A ^ 0x1C) & ~(0x75 ^ 0x43)) != 0) {
                        return;
                    }
                } else {
                    bg = bp[bs[2]];
                }
            }
            if (!em.h(n2, bs[20]) || em.k(n2, bs[6])) {
                bg = bp[bs[1]];
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (" ".length() <= ((0xFF ^ 0xAA) & ~(0x7B ^ 0x2E))) {
            return;
        }
        bj = bs[2];
        if (em.i(e ? 1 : 0)) {
            bj = bs[0];
            if (em.a(be[bs[2]]) && em.a(be[bs[0]]) && em.a(be[bs[1]])) {
                cc = ap.bv - (bh.b(be[bs[2]]) + bh.b(be[bs[0]]) + bh.b(be[bs[1]])) + bs[31];
            }
            if (em.a(be[bs[2]])) {
                bu = ap.bv - bh.b(be[bs[2]]);
            }
            if (em.a(be[bs[0]])) {
                ch = bu - bh.b(be[bs[0]]);
            }
            if (em.a(be[bs[1]])) {
                bh = ch - bh.b(be[bs[1]]);
            }
            if (em.a(be[bs[8]])) {
                bm = ch - bh.b(be[bs[8]]) - bs[15];
            }
            if (em.f(b, bs[1]) && em.g(b, bs[20])) {
                bv = n3 = ap.bv - bh.b(be[bs[2]]);
                if (em.a(be[bs[0]])) {
                    n3 = n3 - bh.b(be[bs[0]]) + n6;
                }
                bb = n3;
                if (em.a(be[bs[8]])) {
                    n3 = n3 - bh.b(be[bs[8]]) + n4;
                }
                bm = n3;
                cc = n3;
                if (em.a(be[bs[1]])) {
                    bh = bb - bh.b(be[bs[1]]) + n5;
                }
                if (em.k(b, bs[1])) {
                    cc = aq;
                }
            }
        }
        if (em.f(b, bs[1]) && em.g(b, bs[19])) {
            n3 = bs[1] * ap.bv / bs[8] - bb;
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        } else {
            n3 = bs[1] * ap.bv / bs[8] - ch;
        }
        if (em.h(n3)) {
            n3 = bs[2];
        }
        if (em.k(de.i, bs[40]) && em.k(de.i, bs[41])) {
            bv += n3;
        }
        if (em.f(b, bs[1]) && em.g(b, bs[18])) {
            bb += n3;
        }
        cc += n3;
        bu += n3;
        ch += n3;
        bh += n3;
        bm += n3;
        ba = bs[8] * ap.fq / bs[9];
        br = cc / bs[8];
        by = new int[bs[1]];
        bq = new int[bs[1]];
        em.by[em.bs[2]] = ap.fq / bs[8];
        em.bq[em.bs[2]] = cc / bs[1] - bs[17];
        em.by[em.bs[0]] = bs[1] * ap.fq / bs[8];
        em.bq[em.bs[0]] = cc / bs[1] + bs[17];
        if (em.k(b, bs[1])) {
            br = cc / bs[10];
            by = new int[bs[10]];
            bq = new int[bs[10]];
            em.by[em.bs[2]] = ap.fq / bs[8];
            em.bq[em.bs[2]] = cc / bs[8] - bs[42];
            em.by[em.bs[0]] = bs[8] * ap.fq / bs[9];
            em.bq[em.bs[0]] = cc / bs[8] + bs[19];
            em.by[em.bs[1]] = ap.fq / bs[8] - bs[6];
            em.bq[em.bs[1]] = cc / bs[8] + bs[19];
            em.by[em.bs[8]] = ap.fq / bs[6];
            em.bq[em.bs[8]] = cc / bs[1] + bs[19];
            em.by[em.bs[9]] = bs[1] * ap.fq / bs[8] + bs[43];
            em.bq[em.bs[9]] = cc / bs[8] + bs[19];
        }
        if (em.i(e ? 1 : 0)) {
            if (em.k(b, bs[17])) {
                bv = bb = ap.dl - bs[44];
            }
            if (em.k(b, bs[15]) && em.a(be[bs[8]])) {
                bm = bh - bh.b(be[bs[8]]);
            }
            if (!em.h(b, bs[31]) || em.k(b, bs[19])) {
                bm = bs[2];
            }
        }
    }

    public void b_() {
    }

    private static boolean g(int n2, int n3) {
        return n2 <= n3;
    }

    public static void h() {
        int n2 = bs[2];
        while (em.j(n2, bs[33])) {
            em.j[n2] = bs[2];
            ++n2;
            "".length();
            if (null == null) continue;
            return;
        }
        q = bs[2];
    }

    public static void i() {
        aj = fk.a.d();
        aq = fk.a.c();
        r = aj / bs[1];
        m = aq / bs[1];
        l = new et();
        au = new aa();
        new az();
        "".length();
        ak = new ae();
        ar = new ar();
    }

    private static boolean g(int n2) {
        return n2 <= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public final void a(int var1_1, Object var2_2) {
        switch (var1_1) {
            case 1608: {
                dl.h().a(em.ar.a.c(), em.ar.b.c(), em.bs[2]);
                em.k();
                return;
            }
            case 1700: {
                dl.h().a(em.bs[13], em.ak.a.c(), ap.cg);
                em.k();
                return;
            }
            case 8881: {
                bz.a((String)var2_2);
                em.d = null;
                return;
            }
            case 8882: {
                em.d = null;
                return;
            }
            case 8883: {
                var6_3 = (Short)((ad)var2_2).elementAt(em.bs[2]);
                bz.a((String)((ad)var2_2).elementAt(em.bs[2]), var6_3);
                em.d = null;
                return;
            }
            case 8884: {
                em.k();
                em.au.c();
                return;
            }
            case 8885: {
                fl.a.notifyDestroyed();
                return;
            }
            case 8887: {
                em.k();
                var1_1 = (Integer)var2_2;
                dl.h().ad(var1_1);
                return;
            }
            case 8888: {
                var1_1 = (Integer)var2_2;
                var5_7 = dl.h();
                var8_11 = var1_1;
                var11_16 = var5_7;
                var10_20 = null;
                try {
                    var10_20 = new eh(em.bs[62]);
                    var10_20.b().writeInt(var8_11);
                    var11_16.a.a(var10_20);
                    "".length();
                }
                catch (Exception var13_23) {
                    var13_23.printStackTrace();
                    "".length();
                    if ("   ".length() == 0) {
                        return;
                    }
                    ** GOTO lbl59
                }
                finally {
                    var10_20.c();
                }
                if (-" ".length() >= 0) {
                    return;
                }
lbl59:
                // 2 sources

                em.k();
                return;
            }
            case 8889: {
                var4_28 = (String)var2_2;
                em.k();
                dl.h().k(var4_28);
                return;
            }
            case 8890: {
                em.k();
                dl.h().g((Integer)var2_2);
                return;
            }
            case 16081: {
                dl.h().a(em.ar.a.c(), em.ar.b.c(), em.bs[0]);
                em.k();
                return;
            }
            case 88810: {
                var1_1 = (Integer)var2_2;
                em.k();
                dl.h().y(var1_1);
                return;
            }
            case 88811: {
                em.k();
                dl.h().y();
                return;
            }
            case 88812: {
                var7_37 = (ec)var2_2;
                em.k();
                var5_8 = dl.h();
                var8_12 = var7_37.w;
                var11_17 = var5_8;
                var10_21 = null;
                try {
                    var10_21 = new eh(em.bs[63]);
                    var10_21.b().writeInt(var8_12);
                    var11_17.a.a(var10_21);
                    return;
                }
                catch (Exception var13_24) {
                    var13_24.printStackTrace();
                    "".length();
                    if (-(137 ^ 140) >= 0) {
                        return;
                    }
                }
                finally {
                    var10_21.c();
                }
                return;
            }
            case 88813: {
                em.k();
                var12_42 = (el[])var2_2;
                dl.h().c(var12_42);
                return;
            }
            case 88814: {
                var12_43 = (el[])var2_2;
                em.k();
                dl.h().b(var12_43);
                return;
            }
            case 88815: {
                ap.s();
                "".length();
                ap.ar();
                return;
            }
            case 88816: {
                var5_9 = dl.h();
                var13_25 = em.ar.a.c();
                var14_27 = em.ar.b.c();
                var9_44 = var13_25;
                var11_18 = var5_9;
                var15_41 = null;
                try {
                    var15_41 = dl.c(em.bs[64]);
                    var15_41.b().writeUTF(var9_44);
                    var15_41.b().writeUTF(var14_27);
                    var11_18.a.a(var15_41);
                    "".length();
                }
                catch (Exception var16_47) {
                    var16_47.printStackTrace();
                    "".length();
                    if ("  ".length() < ((117 + 46 - 42 + 84 ^ 136 + 130 - 181 + 65) & (65 ^ 4 ^ (103 ^ 121) ^ -" ".length()))) {
                        return;
                    }
                    ** GOTO lbl150
                }
                finally {
                    var15_41.c();
                }
                if ("   ".length() == 0) {
                    return;
                }
lbl150:
                // 2 sources

                em.k();
                return;
            }
            case 88817: {
                if (em.a(ec.n().ap)) {
                    dl.h().d(ec.n().ap.a.e, em.a.c, em.bs[2]);
                    return;
                }
                dl.h().d(em.bs[2], em.a.c, em.bs[2]);
                return;
            }
            case 88818: {
                var6_4 = (Short)var2_2;
                dl.h().a(var6_4, em.ak.a.c());
                em.k();
                return;
            }
            case 88819: {
                var6_5 = (Short)var2_2;
                var5_10 = dl.h();
                var16_48 = var6_5;
                var11_19 = var5_10;
                var10_22 = null;
                try {
                    var10_22 = new eh(em.bs[65]);
                    var10_22.b().writeShort(var16_48);
                    var11_19.a.a(var10_22);
                    "".length();
                }
                catch (Exception var17_50) {
                    var17_50.printStackTrace();
                    "".length();
                    if (null != null) {
                        return;
                    }
                    ** GOTO lbl188
                }
                finally {
                    var10_22.c();
                }
                if (-" ".length() >= 0) {
                    return;
                }
lbl188:
                // 2 sources

                ap.s().an();
                return;
            }
            case 88820: {
                var17_51 = (String[])var2_2;
                if (em.b(ec.n().ap)) {
                    return;
                }
                var18_53 = new Integer(em.a.c);
                if (em.g(var17_51.length, em.bs[0])) {
                    fi.a("", em.bs[0], ec.n().ap);
                    "".length();
                    dl.h().d(ec.n().ap.a.e, var18_53, em.bs[2]);
                    return;
                }
                var19_54 = new ad();
                var3_55 = em.bs[2];
                while (em.j(var3_55, var17_51.length - em.bs[0])) {
                    var19_54.addElement(new fr(var17_51[var3_55 + em.bs[0]], em.f, em.bs[66], var18_53));
                    ++var3_55;
                    "".length();
                    if ((26 + 133 - 82 + 84 ^ 149 + 6 - 88 + 97) != 0) continue;
                    return;
                }
                em.a.a(var19_54);
                return;
            }
            case 88821: {
                var3_56 = (Integer)var2_2;
                fi.a("", em.bs[0], ec.n().ap);
                "".length();
                dl.h().d(ec.n().ap.a.e, var3_56, em.a.c);
                return;
            }
            case 88822: {
                fi.a("", em.bs[0], ec.n().ap);
                "".length();
                dl.h().d(ec.n().ap.a.e, em.a.c, em.bs[2]);
                return;
            }
            case 88823: {
                em.c(ag.cb);
                return;
            }
            case 88824: {
                em.c(ag.kr);
                return;
            }
            case 88825: {
                em.c(ag.ac);
                return;
            }
            case 88826: {
                em.c(ag.r);
                return;
            }
            case 88827: {
                em.c(ag.dj);
                return;
            }
            case 88828: {
                em.c(ag.bp);
                return;
            }
            case 88829: {
                var4_29 = em.ak.a.c();
                if (em.e((int)var4_29.equals(""))) {
                    return;
                }
                dl.h().b(var4_29, (int)((Integer)var2_2));
                em.b(ag.ab);
                return;
            }
            case 88830: {
                var1_1 = (Integer)var2_2;
                em.k();
                dl.h().h(var1_1);
                return;
            }
            case 88831: {
                var1_1 = (Integer)var2_2;
                em.k();
                dl.h().ab(var1_1);
                return;
            }
            case 88832: {
                var4_30 = em.ak.a.c();
                em.k();
                if (!em.i((int)var4_30.equals(""))) break;
                dl.h().j(var4_30);
                return;
            }
            case 88833: {
                var4_31 = em.ak.a.c();
                em.k();
                if (!em.i((int)var4_31.equals(""))) break;
                try {
                    var1_1 = Integer.parseInt(var4_31);
                    if (em.f(ec.n().bt, var1_1) && em.d(var1_1)) {
                        dl.h().af(var1_1);
                        return;
                    }
                    bl.a(ag.bm, em.bs[23], doa.j);
                    return;
                }
                catch (Exception var20_57) {
                    return;
                }
            }
            case 88834: {
                var4_32 = em.ak.a.c();
                em.k();
                if (!em.i((int)var4_32.equals(""))) break;
                try {
                    var1_1 = Integer.parseInt(var4_32);
                    if (em.g(var1_1)) {
                        return;
                    }
                    dl.h().m(var1_1);
                    return;
                }
                catch (Exception var20_58) {
                    return;
                }
            }
            case 88835: {
                var1_1 = Integer.parseInt((String)var2_2);
                var8_13 = Integer.parseInt(em.ak.a.c());
                em.d = null;
                if (em.f(var8_13) && em.j(var8_13, ec.n().en[var1_1].g)) {
                    dl.h().e(var1_1, var8_13);
                    return;
                }
                em.c(ag.rm);
                return;
            }
            case 88836: {
                em.ak.a.a(em.bs[18]);
                em.ak.a(ag.rf, new fr(ag.dk, em.f, em.bs[67], null), em.bs[0]);
                return;
            }
            case 88837: {
                var4_33 = em.ak.a.c();
                em.k();
                try {
                    dl.h().ak(Integer.parseInt(var4_33.trim()));
                    return;
                }
                catch (Exception var20_59) {
                    return;
                }
            }
            case 88838: {
                var4_34 = em.ar.a.c().trim();
                var9_45 = em.ar.b.c().trim();
                em.k();
                if (em.f(var4_34.length(), em.bs[18]) && em.f(var9_45.length(), em.bs[18])) {
                    try {
                        var1_1 = Integer.parseInt(var4_34);
                        var8_14 = Integer.parseInt(var9_45);
                        if (em.f(var1_1, em.bs[68]) && em.f(var8_14, em.bs[68])) {
                            dl.h().i(var1_1, var8_14);
                            return;
                        }
                        em.c(ag.pe);
                        return;
                    }
                    catch (Exception var20_60) {
                        em.c(ag.rg);
                        return;
                    }
                }
                em.c(ag.a);
                return;
            }
            case 88839: {
                var4_35 = em.ak.a.c();
                em.k();
                try {
                    Integer.parseInt(var4_35);
                    "".length();
                    em.a(ag.dq, em.bs[69], var4_35, em.bs[59], null);
                    return;
                }
                catch (Exception var20_61) {
                    bl.a(ag.pi, em.bs[23], doa.j);
                    return;
                }
            }
            case 88840: {
                var7_38 = (ec)var2_2;
                em.k();
                dl.h().n(var7_38.w);
                return;
            }
            case 88841: {
                var7_39 = (ec)var2_2;
                em.k();
                dl.h().f(var7_39.w);
                return;
            }
            case 88842: {
                dl.h().af();
                return;
            }
            case 88843: {
                var9_46 = em.ak.a.c();
                em.k();
                if (em.e((int)var9_46.equals(""))) {
                    em.c(ag.oj);
                    return;
                }
                dl.h().i(var9_46);
                return;
            }
            case 888181: {
                var6_6 = (Short)var2_2;
                dl.h().b(var6_6, em.ak.a.c());
                em.k();
                return;
            }
            case 888361: {
                var4_36 = em.ak.a.c();
                em.k();
                if (em.f(var4_36.length(), em.bs[18]) && em.i((int)var4_36.equals(""))) {
                    try {
                        dl.h().s(Integer.parseInt(var4_36));
                        return;
                    }
                    catch (Exception var20_62) {
                        em.c(ag.rg);
                        return;
                    }
                }
                em.c(ag.a);
                return;
            }
            case 888391: {
                try {
                    em.k();
                    var8_15 = Integer.parseInt((String)var2_2);
                    dl.h().p(var8_15);
                    return;
                }
                catch (Exception var20_63) {
                    return;
                }
            }
        }
    }

    public static boolean e(int n2, int n3) {
        int n4;
        if (em.f(n2, ap.fb) && em.g(n2, ap.fb + ap.fq) && em.f(n3, ap.s) && em.g(n3, ap.s + ap.bv + bs[51])) {
            n4 = bs[0];
            "".length();
            if ((0x64 ^ 0x60) < (0x6B ^ 0x6F)) {
                return ((0xBB ^ 0x95) & ~(0x34 ^ 0x1A)) != 0;
            }
        } else {
            n4 = bs[2];
        }
        return n4 != 0;
    }

    public static void j() {
        if (em.i(e ? 1 : 0) && em.a(bg)) {
            int n2 = bs[2];
            while (em.j(n2, by.length)) {
                if (em.i(aa % (n2 + bs[1] << bs[8]))) {
                    int n3 = n2;
                    by[n3] = by[n3] + bs[0];
                    if (em.i(by[n2], ap.fq + (bh.a(bg) >> bs[0]))) {
                        em.by[n2] = -(bh.a(bg) >> bs[0]);
                    }
                }
                ++n2;
                "".length();
                if ("   ".length() == "   ".length()) continue;
                return;
            }
        }
    }

    public static void k() {
        em.ak.a.i = bs[58];
        em.ar.a.i = bs[58];
        em.ar.b.i = bs[58];
        d = null;
    }

    private static boolean h(int n2) {
        return n2 < 0;
    }

    public final void a(int n2, int n3) {
        ah = bs[0];
        am = bs[0];
        cd = System.currentTimeMillis();
        ao = n2;
        av = n3;
        ae = n2;
        an = n3;
    }

    public static boolean l() {
        boolean bl2;
        if (em.d(em.b(System.currentTimeMillis() - cd, 800L))) {
            bl2 = bs[0];
            "".length();
            if (null != null) {
                return ((0x7F ^ 0x55 ^ (0x6F ^ 0x1D)) & (14 + 113 - 50 + 50 ^ (0x69 ^ 0x4E) ^ -" ".length())) != 0;
            }
        } else {
            bl2 = bs[2];
        }
        return bl2;
    }

    public void b() {
    }

    public final void a(String string, String string2, short s2, String string3, String string4) {
        ad ad2 = new ad();
        ad2.addElement(new Short(s2));
        ad2.addElement(string3);
        g.a(string4, new fr(string, this, bs[61], ad2), null, new fr(string2, this, bs[59], null));
        d = g;
    }

    public static void m() {
        int n2 = bs[2];
        while (em.j(n2, bs[33])) {
            em.af[n2] = bs[2];
            ++n2;
            "".length();
            if (-" ".length() < "   ".length()) continue;
            return;
        }
    }

    public final void n() {
        this.bd = bs[2];
        au.c();
        try {
            ec.p();
            ap.ae();
            ap.ah();
            em.k();
            fb.a();
            ap.a(bs[0]);
            ap.fb = bs[13];
            em.c(de.ae);
            ap.an.removeAllElements();
            ap.c.removeAllElements();
            ap.h.removeAllElements();
            ap.at.removeAllElements();
            ec.eu = null;
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((0xD8 ^ 0xAE ^ (0xE6 ^ 0x94)) <= 0) {
            return;
        }
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public final void a(int n2) {
        h = bs[2];
        switch (n2) {
            case -39: 
            case -2: {
                em.af[em.bs[17]] = bs[2];
                return;
            }
            case -38: 
            case -1: {
                em.af[em.bs[1]] = bs[2];
                return;
            }
            case -22: 
            case -7: {
                em.af[em.bs[20]] = bs[2];
                em.ce[em.bs[20]] = bs[0];
                return;
            }
            case -21: 
            case -6: {
                em.af[em.bs[19]] = bs[2];
                em.ce[em.bs[19]] = bs[0];
                return;
            }
            case -5: 
            case 10: {
                em.af[em.bs[10]] = bs[2];
                em.ce[em.bs[10]] = bs[0];
                return;
            }
            case -4: {
                em.af[em.bs[18]] = bs[2];
                return;
            }
            case -3: {
                em.af[em.bs[9]] = bs[2];
                return;
            }
            case 35: {
                em.af[em.bs[31]] = bs[2];
                em.ce[em.bs[31]] = bs[0];
                return;
            }
            case 42: {
                em.af[em.bs[15]] = bs[2];
                em.ce[em.bs[15]] = bs[0];
                return;
            }
            case 48: {
                em.af[em.bs[2]] = bs[2];
                em.ce[em.bs[2]] = bs[0];
                return;
            }
            case 49: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[0]] = bs[2];
                    em.ce[em.bs[0]] = bs[0];
                }
                return;
            }
            case 50: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[1]] = bs[2];
                    em.ce[em.bs[1]] = bs[0];
                }
                return;
            }
            case 51: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[8]] = bs[2];
                    em.ce[em.bs[8]] = bs[0];
                }
                return;
            }
            case 52: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[9]] = bs[2];
                    em.ce[em.bs[9]] = bs[0];
                }
                return;
            }
            case 53: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[10]] = bs[2];
                    em.ce[em.bs[10]] = bs[0];
                }
                return;
            }
            case 54: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[18]] = bs[2];
                    em.ce[em.bs[18]] = bs[0];
                }
                return;
            }
            case 55: {
                em.af[em.bs[25]] = bs[2];
                em.ce[em.bs[25]] = bs[0];
                return;
            }
            case 56: {
                if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                    em.af[em.bs[17]] = bs[2];
                    em.ce[em.bs[17]] = bs[0];
                }
                return;
            }
            case 57: {
                em.af[em.bs[14]] = bs[2];
                em.ce[em.bs[14]] = bs[0];
                return;
            }
        }
    }

    private static boolean i(int n2) {
        return n2 == 0;
    }

    public static void o() {
        if (em.e(n ? 1 : 0) && em.h(y -= bs[0])) {
            n = bs[2];
        }
    }

    private static void t() {
        bs = new int[70];
        em.bs[0] = " ".length();
        em.bs[1] = "  ".length();
        em.bs[2] = (0x85 ^ 0x8F) & ~(0xAC ^ 0xA6);
        em.bs[3] = (0x1A ^ 3) + (0xEE ^ 0xB9) - -(0xE ^ 5) + (0xFA ^ 0x8F);
        em.bs[4] = -(0xFFFFBEA7 & 0x6FFA) & (0xFFFFFFEF & 0x2FF1);
        em.bs[5] = 81 + 66 - 125 + 121 + (0x10 ^ 0x44) - (0xC4 ^ 0x97) + (0x2D ^ 0x3D);
        em.bs[6] = 0xA ^ 5;
        em.bs[7] = 0xA3 ^ 0xB2;
        em.bs[8] = "   ".length();
        em.bs[9] = 0x88 ^ 0x8C;
        em.bs[10] = 150 + 74 - 178 + 107 ^ 70 + 52 - -15 + 19;
        em.bs[11] = -" ".length();
        em.bs[12] = -(0xFFFFE96E & 0x56F5) & (0xFFFFF777 & 0x6FFB);
        em.bs[13] = 0x6D ^ 9;
        em.bs[14] = 0xA0 ^ 0xB9 ^ (0x78 ^ 0x68);
        em.bs[15] = 0xF ^ 3 ^ (0x55 ^ 0x53);
        em.bs[16] = -(4 + 16 - -195 + 37 ^ 31 + 65 - 6 + 62);
        em.bs[17] = 0xB5 ^ 0xBD;
        em.bs[18] = 0xB9 ^ 0xBF;
        em.bs[19] = 0xD6 ^ 0x98 ^ (0x60 ^ 0x22);
        em.bs[20] = 47 + 128 - 65 + 28 ^ 79 + 35 - 41 + 62;
        em.bs[21] = -(0xFFFFFD4F & 0x26B5) & (0xFFFFF7EF & 0x2FFC);
        em.bs[22] = -(0xFFFFFBFA & 0x7ED);
        em.bs[23] = 0x37 ^ 0x23;
        em.bs[24] = -(0x65 ^ 0x5A ^ (0x64 ^ 0x51));
        em.bs[25] = 60 + 26 - 8 + 105 ^ 15 + 21 - -115 + 25;
        em.bs[26] = 0xFFFFFFFF & 0xFFFFFF;
        em.bs[27] = 0x45 ^ 0x5D;
        em.bs[28] = 0x2F ^ 0x6F;
        em.bs[29] = (0x5F ^ 0x5B) + (0xAE ^ 0xBD) - -(0x43 ^ 0x75) + (0xD6 ^ 0xA5);
        em.bs[30] = 0x54 ^ 0x44;
        em.bs[31] = 0xAA ^ 0xA1;
        em.bs[32] = 4 ^ 0x29;
        em.bs[33] = 0xA8 ^ 0x97 ^ (0xBC ^ 0x8D);
        em.bs[34] = 0x34 ^ 0;
        em.bs[35] = 0x34 ^ 3 ^ (0x91 ^ 0x80) & ~(0x47 ^ 0x56);
        em.bs[36] = 0xFFFFF331 & 0x110CCE;
        em.bs[37] = 98 + 13 - 49 + 68;
        em.bs[38] = 0x6A ^ 0x56;
        em.bs[39] = 0x4D ^ 0x65;
        em.bs[40] = 84 + 6 - -8 + 56 ^ 69 + 21 - -35 + 45;
        em.bs[41] = 92 + 31 - 107 + 130 ^ 29 + 82 - 102 + 152;
        em.bs[42] = 0x48 ^ 0x6B;
        em.bs[43] = 0xF ^ 0x16;
        em.bs[44] = 60 + 115 - 138 + 123 ^ 38 + 54 - 39 + 93;
        em.bs[45] = 0x1B ^ 0x22 ^ (0x7C ^ 0x5C) & ~(0x2B ^ 0xB);
        em.bs[46] = 0x78 ^ 0x39;
        em.bs[47] = 0xCF ^ 0xB5;
        em.bs[48] = 0x65 ^ 0x6A ^ (8 ^ 0x27);
        em.bs[49] = 0xFFFFFB7F & 0x17FE;
        em.bs[50] = 29 + 57 - 52 + 94;
        em.bs[51] = 0x9A ^ 0x84;
        em.bs[52] = 0xA ^ 0x49 ^ (0xF5 ^ 0xA1);
        em.bs[53] = 0x38 ^ 0x18 ^ (0x86 ^ 0xB4);
        em.bs[54] = 0xF8 ^ 0x98 ^ (0x89 ^ 0x9F);
        em.bs[55] = 0x64 ^ 0x34;
        em.bs[56] = 0x3C ^ 0x58 ^ (0x42 ^ 0x44);
        em.bs[57] = 0x46 ^ 0x1F ^ (0x77 ^ 0x60);
        em.bs[58] = -(0xFFFFE835 & 0x7FCE) & (0xFFFFEBFF & 0x7DF7);
        em.bs[59] = 0xFFFFE3BF & 0x3EF2;
        em.bs[60] = 0xFFFFE3B9 & 0x3EF7;
        em.bs[61] = -(0xFFFFFEFD & 0x4D4F) & (0xFFFFFEFF & 0x6FFF);
        em.bs[62] = 0x71 ^ 0x20;
        em.bs[63] = 0x37 ^ 0x75;
        em.bs[64] = -(0x35 ^ 0x56);
        em.bs[65] = 0x71 ^ 0x38 ^ (0x1D ^ 0x76);
        em.bs[66] = -(0xFFFFF58F & 0xF79) & (0xFFFFFFFF & 0x15FFD);
        em.bs[67] = 0xFFFFCFEF & 0xDBE39;
        em.bs[68] = 0xFFFFCF9F & 0x1B6FF;
        em.bs[69] = -(0xFFFFB731 & 0x69FF) & (0xFFFFAFFF & 0xDFF77);
    }

    public static boolean a(int n2, int n3, int n4, int n5, fj fj2) {
        int n6;
        int n7 = ae + fj2.g;
        int n8 = fj2.b + an;
        if ((!em.i(ah ? 1 : 0) || em.e(q ? 1 : 0)) && em.f(n7, n2) && em.g(n7, n2 + n4) && em.f(n8, n3) && em.g(n8, n3 + n5)) {
            n6 = bs[0];
            "".length();
            if ((0x55 ^ 0x51) < 0) {
                return ((0xF1 ^ 0xC6) & ~(0x52 ^ 0x65)) != 0;
            }
        } else {
            n6 = bs[2];
        }
        return n6 != 0;
    }

    public static void a(String string, fr fr2, fr fr3) {
        g.a(string, fr2, null, fr3);
        d = g;
    }

    public static boolean b(int n2, int n3, int n4, int n5) {
        int n6;
        if ((!em.i(ah ? 1 : 0) || em.e(q ? 1 : 0)) && em.f(ae, n2) && em.g(ae, n2 + n4) && em.f(an, n3) && em.g(an, n3 + n5)) {
            n6 = bs[0];
            "".length();
            if (((78 + 121 - 83 + 24 ^ 123 + 120 - 145 + 32) & (0xC ^ 0x4E ^ (0x34 ^ 0x78) ^ -" ".length())) != 0) {
                return ((0xCC ^ 0xAD ^ (0x57 ^ 0x70)) & (168 + 43 - 133 + 153 ^ 72 + 107 - 108 + 90 ^ -" ".length())) != 0;
            }
        } else {
            n6 = bs[2];
        }
        return n6 != 0;
    }

    public final void a(String string, String string2, String string3, String string4) {
        g.a(string4, new fr(string, this, bs[60], string3), null, new fr(string2, this, bs[59], null));
        d = g;
    }

    public static void p() {
        be = null;
        bg = null;
        ax = null;
    }

    public final void a(Graphics graphics) {
        this.cb.a = graphics;
        try {
            block51: {
                block50: {
                    if (em.a(at) && em.i(ad ? 1 : 0)) {
                        at.a(this.cb);
                        this.cb.a(bs[2], bs[2], aj, aq);
                    }
                    this.cb.a(-this.cb.a(), -this.cb.b());
                    this.cb.a(bs[2], bs[2], aj, aq);
                    bh bh2 = this.cb;
                    String string = fb.h;
                    if (em.a((Object)de.f)) {
                        string = de.f;
                    }
                    if (em.e(fb.d ? 1 : 0) && (!em.e(fb.g ? 1 : 0) || em.g(fb.e, bs[49])) && em.i(ap.eo ? 1 : 0)) {
                        br.b(r - bs[28], bs[15], bs[50], bs[39], bh2);
                        if (em.e(fb.g ? 1 : 0)) {
                            em.a(r - doa.b.a(string) / bs[1] - bs[15], bs[51], bh2);
                            doa.b.a(bh2, string, r + bs[10], bs[52], bs[1]);
                            "".length();
                            if (-"  ".length() > 0) {
                                return;
                            }
                        } else if (em.a((Object)fb.f)) {
                            doa.b.a(bh2, string, r, bs[53], bs[1]);
                            doa.m.a(bh2, fb.f, r, bs[48], bs[1]);
                            "".length();
                            if (-"   ".length() > 0) {
                                return;
                            }
                        } else {
                            doa.b.a(bh2, string, r, bs[52], bs[1]);
                        }
                    }
                    if (!em.a(d)) break block50;
                    d.a(this.cb);
                    "".length();
                    if ((8 ^ 0xC) < " ".length()) {
                        return;
                    }
                    break block51;
                }
                if (em.e(em.a.m ? 1 : 0)) {
                    bh bh3 = this.cb;
                    cc cc2 = a;
                    try {
                        bh3.a(-bh3.a(), -bh3.b());
                        bh3.a(-cc.e, bs[2]);
                        if (em.e(w ? 1 : 0)) {
                            int n2 = bs[2];
                            while (em.j(n2, cc2.i.size())) {
                                if (em.k(n2, cc2.c)) {
                                    bh3.a(cc.w, cc2.h + n2 * cc2.n + bs[0], cc2.ab + bs[0], bs[2]);
                                    "".length();
                                    if (("  ".length() & ~"  ".length()) == "  ".length()) {
                                        return;
                                    }
                                } else {
                                    bh3.a(cc.p, cc2.h + n2 * cc2.n + bs[0], cc2.ab + bs[0], bs[2]);
                                }
                                String[] stringArray = ((fr)cc2.i.elementAt((int)n2)).b;
                                if (em.b(((fr)cc2.i.elementAt((int)n2)).b)) {
                                    String[] stringArray2 = new String[bs[0]];
                                    stringArray2[em.bs[2]] = ((fr)cc2.i.elementAt((int)n2)).d;
                                    stringArray = stringArray2;
                                }
                                int n3 = cc2.ab + (cc2.j - stringArray.length * bs[33]) / bs[1] + bs[0];
                                int n4 = bs[2];
                                while (em.j(n4, stringArray.length)) {
                                    if (em.e(ap.cy ? 1 : 0)) {
                                        if (em.e(b.f().d(stringArray[n4]) ? 1 : 0)) {
                                            if (em.i(aa % bs[15], bs[10])) {
                                                doa.a.a(bh3, stringArray[n4], cc2.h + n2 * cc2.n + cc2.n / bs[1] - bs[1], n3 + n4 * bs[33], bs[1]);
                                                "".length();
                                                if ("  ".length() == " ".length()) {
                                                    return;
                                                }
                                            } else {
                                                doa.j.a(bh3, stringArray[n4], cc2.h + n2 * cc2.n + cc2.n / bs[1] - bs[1], n3 + n4 * bs[33], bs[1]);
                                                "".length();
                                                if ("  ".length() == 0) {
                                                    return;
                                                }
                                            }
                                        } else {
                                            doa.j.a(bh3, stringArray[n4], cc2.h + n2 * cc2.n + cc2.n / bs[1] - bs[1], n3 + n4 * bs[33], bs[1]);
                                            "".length();
                                            if (" ".length() == 0) {
                                                return;
                                            }
                                        }
                                    } else {
                                        doa.j.a(bh3, stringArray[n4], cc2.h + n2 * cc2.n + cc2.n / bs[1] - bs[1], n3 + n4 * bs[33], bs[1]);
                                    }
                                    ++n4;
                                    "".length();
                                    if (((66 + 74 - 101 + 172 ^ 85 + 176 - 119 + 52) & (0x82 ^ 0xAF ^ (0xB3 ^ 0x8F) ^ -" ".length())) == 0) continue;
                                    return;
                                }
                                ++n2;
                                "".length();
                                if ("   ".length() > -" ".length()) continue;
                                return;
                            }
                        } else {
                            int n5 = bs[2];
                            while (em.j(n5, cc2.i.size())) {
                                if (em.k(n5, cc2.c)) {
                                    bh3.a(cc.w, cc2.h + n5 * cc2.n + bs[0], cc2.ab + bs[0] - bs[52], bs[2]);
                                    "".length();
                                    if (null != null) {
                                        return;
                                    }
                                } else {
                                    bh3.a(cc.p, cc2.h + n5 * cc2.n + bs[0], cc2.ab + bs[0] - bs[52], bs[2]);
                                }
                                String[] stringArray = ((fr)cc2.i.elementAt((int)n5)).b;
                                if (em.b(((fr)cc2.i.elementAt((int)n5)).b)) {
                                    String[] stringArray3 = new String[bs[0]];
                                    stringArray3[em.bs[2]] = ((fr)cc2.i.elementAt((int)n5)).d;
                                    stringArray = stringArray3;
                                }
                                int n6 = cc2.ab + (cc2.j - stringArray.length * bs[33]) / bs[1] + bs[0] - bs[52];
                                int n7 = bs[2];
                                while (em.j(n7, stringArray.length)) {
                                    if (em.e(ap.cy ? 1 : 0)) {
                                        if (em.e(b.f().d(stringArray[n7]) ? 1 : 0)) {
                                            if (em.i(aa % bs[15], bs[10])) {
                                                doa.a.a(bh3, stringArray[n7], cc2.h + n5 * cc2.n + cc2.n / bs[1] - bs[1], n6 + n7 * bs[33], bs[1]);
                                                "".length();
                                                if (((0x3E ^ 0x2A ^ (0x89 ^ 0xAD)) & (0x13 ^ 0x4E ^ (0xCB ^ 0xA6) ^ -" ".length())) != 0) {
                                                    return;
                                                }
                                            } else {
                                                doa.j.a(bh3, stringArray[n7], cc2.h + n5 * cc2.n + cc2.n / bs[1] - bs[1], n6 + n7 * bs[33], bs[1]);
                                                "".length();
                                                if ("  ".length() > (0x5F ^ 0x68 ^ (0x2A ^ 0x19))) {
                                                    return;
                                                }
                                            }
                                        } else {
                                            doa.j.a(bh3, stringArray[n7], cc2.h + n5 * cc2.n + cc2.n / bs[1] - bs[1], n6 + n7 * bs[33], bs[1]);
                                            "".length();
                                            if (-"   ".length() >= 0) {
                                                return;
                                            }
                                        }
                                    } else {
                                        doa.j.a(bh3, stringArray[n7], cc2.h + n5 * cc2.n + cc2.n / bs[1] - bs[1], n6 + n7 * bs[33], bs[1]);
                                    }
                                    ++n7;
                                    "".length();
                                    if (null == null) continue;
                                    return;
                                }
                                ++n5;
                                "".length();
                                if ((185 + 43 - 147 + 110 ^ 82 + 45 - 119 + 179) >= -" ".length()) continue;
                                return;
                            }
                        }
                        bh3.a(-bh3.a(), -bh3.b());
                        "".length();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if (null != null) {
                        return;
                    }
                }
            }
            ap.b(this.cb);
            if (em.f(as)) {
                br.b(bs[51], aq - bs[54], aj - bs[38], bs[55], this.cb);
                em.a(r, aq - bs[56], this.cb);
                doa.b.a(this.cb, "Xin ch\u1edd " + as + "s...", r, aq - bs[57], bs[1]);
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("   ".length() < " ".length()) {
            return;
        }
    }

    static {
        em.t();
        e = bs[2];
        u = bs[0];
        ac = bs[0];
        n = bs[2];
        w = bs[2];
        j = new boolean[bs[33]];
        ce = new boolean[bs[33]];
        af = new boolean[bs[33]];
        ag = new ch[bs[9]];
        a = new cc();
        bk = bs[2];
        x = new Image[bs[1]];
        b = bs[11];
        cd = 0L;
        ay = new Image[bs[9]][];
        bp = new Image[bs[9]];
        az = new Image[bs[1]];
    }

    private static boolean h(int n2, int n3) {
        return n2 != n3;
    }

    public static void q() {
        dp.g().a(fl.c, fl.e);
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static void a(String string, int n2) {
        g.a(string, null, new fr(ag.v, f, n2, aj / bs[1] - bs[42], aq - bs[44]), null);
        d = g;
    }

    public em() {
        int n2;
        int n3;
        fk fk2 = fk.e();
        fk2.setFullScreenMode(bs[0]);
        fk2.c = this;
        aj = fk2.d();
        aq = fk2.c();
        r = aj / bs[1];
        m = aq / bs[1];
        if (em.i(System.getProperty("microedition.platform").indexOf("RIM"))) {
            n3 = bs[0];
            "".length();
            if (null != null) {
                throw null;
            }
        } else {
            ai = bs[2];
            n3 = ai ? 1 : 0;
        }
        if (em.e(fk.a.hasPointerEvents() ? 1 : 0)) {
            w = bs[0];
            if (em.f(aj, bs[3])) {
                c = bs[0];
            }
            if (em.j(aj, bs[4])) {
                s = bs[0];
            }
            if (em.f(aj, bs[4])) {
                v = bs[0];
            }
        }
        if (em.h(n2 = an.d("indLanguage"))) {
            ag.br = bs[2];
            "".length();
            if ("   ".length() == (0x57 ^ 9 ^ (0x2B ^ 0x71))) {
                throw null;
            }
        } else {
            ag.br = n2;
        }
        ag.a();
        g = new bt();
        if (em.g(aq, bs[5])) {
            br.a = bs[6];
            ef.fs = bs[7];
        }
        f = this;
        System.gc();
        new br();
        "".length();
        em.ay[em.bs[2]] = new Image[bs[7]];
        n2 = bs[2];
        while (em.j(n2, bs[7])) {
            em.ay[em.bs[2]][n2] = em.a("/bg/bg0" + n2 + ".png");
            ++n2;
            "".length();
            if (-"   ".length() < 0) continue;
            throw null;
        }
        em.ay[em.bs[0]] = new Image[bs[7]];
        n2 = bs[2];
        while (em.j(n2, bs[7])) {
            em.ay[em.bs[0]][n2] = em.a("/bg/bg1" + n2 + ".png");
            ++n2;
            "".length();
            if (null == null) continue;
            throw null;
        }
        em.ay[em.bs[1]] = new Image[bs[7]];
        n2 = bs[2];
        while (em.j(n2, bs[7])) {
            em.ay[em.bs[1]][n2] = em.a("/bg/bg2" + n2 + ".png");
            ++n2;
            "".length();
            if (" ".length() >= " ".length()) continue;
            throw null;
        }
        em.ay[em.bs[8]] = new Image[bs[6]];
        n2 = bs[2];
        while (em.j(n2, bs[6])) {
            em.ay[em.bs[8]][n2] = em.a("/bg/bg3" + n2 + ".png");
            ++n2;
            "".length();
            if (-"  ".length() < 0) continue;
            throw null;
        }
        n2 = bs[2];
        while (em.j(n2, bs[9])) {
            em.bp[n2] = em.a("/bg/cl" + n2 + ".png");
            ++n2;
            "".length();
            if (-" ".length() < (0xCA ^ 0xA4 ^ (0xCF ^ 0xA5))) continue;
            throw null;
        }
        em.az[em.bs[2]] = em.a("/bg/sun0.png");
        em.az[em.bs[0]] = em.a("/bg/sun1.png");
        de.i();
        if (em.i(e ? 1 : 0)) {
            if (em.b(bi)) {
                bi = new Image[bs[1]][bs[10]];
                n2 = bs[2];
                while (em.j(n2, bs[1])) {
                    int n4 = bs[2];
                    while (em.j(n4, bs[10])) {
                        em.bi[n2][n4] = em.a("/e/d" + n2 + n4 + ".png");
                        ++n4;
                        "".length();
                        if (-"  ".length() <= 0) continue;
                        throw null;
                    }
                    ++n2;
                    "".length();
                    if ((0x96 ^ 0x92) != 0) continue;
                    throw null;
                }
            }
            this.cf = new int[bs[1]];
            this.aw = new int[bs[1]];
            this.bo = new int[bs[1]];
            int n5 = bs[11];
            this.bo[em.bs[0]] = n5;
            this.bo[em.bs[2]] = n5;
        }
        em.u();
        bl = em.a("/u/f.png");
        if (em.e(w ? 1 : 0)) {
            n2 = bs[2];
            while (em.j(n2, bs[1])) {
                em.x[n2] = em.a("/hd/bd" + n2 + ".png");
                ++n2;
                "".length();
                if ((0x75 ^ 0x71) != "  ".length()) continue;
                throw null;
            }
            ap = bh.a(x[bs[2]]);
            k = bh.b(x[bs[2]]);
            bh.a(x[bs[0]]);
            "".length();
            bh.b(x[bs[0]]);
            "".length();
            "".length();
            if (((0xA ^ 0x10) & ~(0xBC ^ 0xA6)) != 0) {
                throw null;
            }
        } else if (em.k(an.d("lowGraphic"), bs[0])) {
            e = bs[0];
        }
        bd.a();
        ef.au();
        ab = new c();
    }

    public static boolean c(int n2, int n3, int n4, int n5) {
        int n6;
        if ((!em.i(ah ? 1 : 0) || em.e(q ? 1 : 0)) && em.f(ae, n2) && em.g(ae, n2 + n4) && em.f(an, n3) && em.g(an, n3 + n5)) {
            n6 = bs[0];
            "".length();
            if (-" ".length() >= 0) {
                return ((0x76 ^ 0x31) & ~(0xD8 ^ 0x9F)) != 0;
            }
        } else {
            n6 = bs[2];
        }
        return n6 != 0;
    }

    public static void r() {
        em.b(ag.ab);
    }

    public final boolean a(int n2, int n3, int n4) {
        int n5;
        if (em.e(e ? 1 : 0)) {
            return bs[2];
        }
        if (em.k(n2, bs[0])) {
            n5 = bs[2];
            "".length();
            if ("   ".length() < "  ".length()) {
                return ((177 + 124 - 133 + 44 ^ 99 + 2 - 23 + 72) & (0xFE ^ 0xAD ^ (0x2B ^ 0x3A) ^ -" ".length())) != 0;
            }
        } else {
            n5 = bs[0];
        }
        if (em.h(this.bo[n2 = n5], bs[11])) {
            return bs[2];
        }
        this.bo[n2] = bs[2];
        this.cf[n2] = n3;
        this.aw[n2] = n4;
        return bs[0];
    }

    public final void b(int n2, int n3) {
        ah = bs[2];
        q = bs[0];
        ef.fr = bs[11];
        ae = n2;
        an = n3;
    }

    public final void b(int n2) {
        cd = System.currentTimeMillis();
        if (em.f(n2, bs[40]) && !em.i(n2, bs[45]) || em.f(n2, bs[46]) && !em.i(n2, bs[47]) || !em.h(n2, bs[15]) || !em.h(n2, bs[17]) || !em.h(n2, bs[20]) || em.k(n2, bs[48])) {
            h = n2;
        }
        if (em.a(d)) {
            d.a(n2);
            h = bs[2];
            "".length();
            if (" ".length() > (81 + 48 - 88 + 86 ^ (0x32 ^ 0x49))) {
                return;
            }
        } else {
            at.h(n2);
            switch (n2) {
                case -39: 
                case -2: {
                    em.af[em.bs[17]] = bs[0];
                    em.j[em.bs[17]] = bs[0];
                    return;
                }
                case -38: 
                case -1: {
                    em.af[em.bs[1]] = bs[0];
                    em.j[em.bs[1]] = bs[0];
                    return;
                }
                case -22: 
                case -7: {
                    em.af[em.bs[20]] = bs[0];
                    em.j[em.bs[20]] = bs[0];
                    return;
                }
                case -21: 
                case -6: {
                    em.af[em.bs[19]] = bs[0];
                    em.j[em.bs[19]] = bs[0];
                    return;
                }
                case -5: 
                case 10: {
                    em.af[em.bs[10]] = bs[0];
                    em.j[em.bs[10]] = bs[0];
                    return;
                }
                case -4: {
                    em.af[em.bs[18]] = bs[0];
                    em.j[em.bs[18]] = bs[0];
                    return;
                }
                case -3: {
                    em.af[em.bs[9]] = bs[0];
                    em.j[em.bs[9]] = bs[0];
                    return;
                }
                case 35: {
                    em.af[em.bs[31]] = bs[0];
                    em.j[em.bs[31]] = bs[0];
                    return;
                }
                case 42: {
                    em.af[em.bs[15]] = bs[0];
                    em.j[em.bs[15]] = bs[0];
                    return;
                }
                case 48: {
                    em.af[em.bs[2]] = bs[0];
                    em.j[em.bs[2]] = bs[0];
                    return;
                }
                case 49: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                        em.af[em.bs[0]] = bs[0];
                        em.j[em.bs[0]] = bs[0];
                    }
                    return;
                }
                case 50: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0) && em.i(ap.cd ? 1 : 0)) {
                        em.af[em.bs[1]] = bs[0];
                        em.j[em.bs[1]] = bs[0];
                    }
                    return;
                }
                case 51: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0)) {
                        em.af[em.bs[8]] = bs[0];
                        em.j[em.bs[8]] = bs[0];
                    }
                    return;
                }
                case 52: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0) && em.i(ap.cd ? 1 : 0)) {
                        em.af[em.bs[9]] = bs[0];
                        em.j[em.bs[9]] = bs[0];
                    }
                    return;
                }
                case 53: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0) && em.i(ap.cd ? 1 : 0)) {
                        em.af[em.bs[10]] = bs[0];
                        em.j[em.bs[10]] = bs[0];
                    }
                    return;
                }
                case 54: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0) && em.i(ap.cd ? 1 : 0)) {
                        em.af[em.bs[18]] = bs[0];
                        em.j[em.bs[18]] = bs[0];
                    }
                    return;
                }
                case 55: {
                    em.af[em.bs[25]] = bs[0];
                    em.j[em.bs[25]] = bs[0];
                    return;
                }
                case 56: {
                    if (em.a(at, ap.dc) && em.e(u ? 1 : 0) && em.i(am.c().a ? 1 : 0) && em.i(ap.cd ? 1 : 0)) {
                        em.af[em.bs[17]] = bs[0];
                        em.j[em.bs[17]] = bs[0];
                    }
                    return;
                }
                case 57: {
                    em.af[em.bs[14]] = bs[0];
                    em.j[em.bs[14]] = bs[0];
                    return;
                }
            }
        }
    }

    public static void s() {
        em.g.b = bs[58];
        g.a(ag.ab, null, null, null);
        d = g;
        em.g.a = bs[0];
    }

    public final void c(int n2, int n3) {
        if (!em.j(fo.d(n2 - ao), bs[15]) || em.f(fo.d(n3 - av), bs[15])) {
            am = bs[2];
        }
        ae = n2;
        an = n3;
        if (em.i(bk += bs[0], bs[8])) {
            bk = bs[2];
        }
        em.ag[em.bk] = new ch(n2, n3);
    }

    private static boolean i(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public void a(String string, String string2) {
    }

    private static boolean j(int n2, int n3) {
        return n2 < n3;
    }

    public static void a(int n2, int n3, bh bh2) {
        int n4 = aa % bs[8];
        bh2.a(bl, bs[2], n4 << bs[9], bs[30], bs[30], bs[2], n2, n3, bs[8]);
    }

    public static void b(String string) {
        g.a(string, null, new fr(ag.ip, f, bs[59], null), null);
        d = g;
        em.g.a = bs[0];
    }

    private static void u() {
        if (em.i(e ? 1 : 0)) {
            cg = new Image[bs[8]];
            int n2 = bs[2];
            while (em.j(n2, bs[8])) {
                em.cg[n2] = em.a("/e/w" + n2 + ".png");
                ++n2;
                "".length();
                if (((0xAB ^ 0xBC ^ (0x52 ^ 0xF)) & (0x69 ^ 0x58 ^ (0x6F ^ 0x14) ^ -" ".length())) < " ".length()) continue;
                return;
            }
            bz = new int[bs[1]];
            bt = new int[bs[1]];
            int[] nArray = new int[bs[1]];
            bf = nArray;
            int n3 = bs[11];
            em.bf[em.bs[0]] = n3;
            nArray[em.bs[2]] = n3;
        }
    }

    public static void b(bh bh2) {
        if (em.e(n ? 1 : 0)) {
            if (em.i(aa % bs[15], bs[25])) {
                bh2.a(bs[26]);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                bh2.a(bs[2]);
            }
            bh2.e(bs[2], bs[2], ap.fq, ap.bv);
            "".length();
            if (-(128 + 140 - 261 + 185 ^ 145 + 29 - 93 + 116) >= 0) {
                return;
            }
        } else {
            int n2;
            int n3 = fa.h;
            if (em.h(n3, bs[11])) {
                bh2.a(n3);
                bh2.e(bs[2], bs[2], aj, aq);
                n2 = bs[0];
                "".length();
                if (((0x85 ^ 0xAE) & ~(0x65 ^ 0x4E) & ~((0x78 ^ 0x3D) & ~(0x53 ^ 0x16))) != 0) {
                    return;
                }
            } else {
                n2 = bs[2];
            }
            if (em.i(n2)) {
                if (em.e(bj ? 1 : 0) && em.i(e ? 1 : 0) && em.a(be)) {
                    bh2.a(bn);
                    bh2.e(bs[2], bs[2], ap.fq, cc);
                    if (em.d(b) && em.g(b, bs[0])) {
                        int n4;
                        if (em.a(be[bs[2]])) {
                            n4 = -((ap.fb >> bs[0]) % bs[27]);
                            while (em.j(n4, ap.fq)) {
                                bh2.a(be[bs[2]], n4, bu, bs[2]);
                                n4 += 24;
                                "".length();
                                if (null == null) continue;
                                return;
                            }
                        }
                        if (em.a(be[bs[0]])) {
                            n4 = -((ap.fb >> bs[1]) % bs[27]);
                            while (em.j(n4, ap.fq)) {
                                bh2.a(be[bs[0]], n4, ch, bs[2]);
                                n4 += 24;
                                "".length();
                                if (null == null) continue;
                                return;
                            }
                        }
                        if (em.a(be[bs[8]])) {
                            n4 = -((ap.fb >> bs[9]) % bs[28]);
                            while (em.j(n4, ap.fq)) {
                                bh2.a(be[bs[8]], n4, bm, bs[2]);
                                n4 += 64;
                                "".length();
                                if (-" ".length() <= "  ".length()) continue;
                                return;
                            }
                        }
                        if (em.a(ax)) {
                            bh2.a(ax, ba, br, bs[8]);
                        }
                        if (em.a(bg)) {
                            n4 = bs[2];
                            while (em.j(n4, bs[1])) {
                                bh2.a(bg, by[n4], bq[n4], bs[8]);
                                ++n4;
                                "".length();
                                if (null == null) continue;
                                return;
                            }
                        }
                        if (em.a(be[bs[1]])) {
                            n4 = -((ap.fb >> bs[8]) % bs[29]);
                            while (em.j(n4, ap.fq)) {
                                bh2.a(be[bs[1]], n4, bh, bs[2]);
                                n4 += 192;
                                "".length();
                                if (-(5 ^ 1) <= 0) continue;
                                return;
                            }
                            return;
                        }
                    } else if (em.f(b, bs[1]) && em.g(b, bs[18])) {
                        int n5;
                        if (em.a(ax)) {
                            bh2.a(ax, ba, br, bs[8]);
                        }
                        if (em.a(bg)) {
                            n5 = bs[2];
                            while (em.j(n5, by.length)) {
                                bh2.a(bg, by[n5], bq[n5], bs[8]);
                                ++n5;
                                "".length();
                                if (((93 + 91 - 122 + 86 ^ 99 + 9 - 27 + 72) & (0x4A ^ 0x5A ^ (0x2C ^ 0x31) ^ -" ".length())) == ((200 + 205 - 180 + 20 ^ 164 + 114 - 114 + 4) & (171 + 111 - 186 + 96 ^ 141 + 51 - 50 + 15 ^ -" ".length()) & ((62 + 111 - 122 + 86 ^ 18 + 16 - -45 + 105) & (159 + 80 - 153 + 82 ^ 121 + 23 - 28 + 37 ^ -" ".length()) ^ -" ".length()))) continue;
                                return;
                            }
                        }
                        if (em.h(b, bs[1])) {
                            if (em.a(be[bs[8]])) {
                                n5 = -((ap.fb >> ca[bs[8]]) % bw[bs[8]]);
                                while (em.j(n5, ap.fq)) {
                                    bh2.a(be[bs[8]], n5, bm, bs[2]);
                                    n5 += bw[bs[8]];
                                    "".length();
                                    if (null == null) continue;
                                    return;
                                }
                            }
                            if (em.a(be[bs[1]])) {
                                n5 = -((ap.fb >> ca[bs[1]]) % bw[bs[1]]);
                                while (em.j(n5, ap.fq)) {
                                    bh2.a(be[bs[1]], n5, bh, bs[2]);
                                    n5 += bw[bs[1]];
                                    "".length();
                                    if (null == null) continue;
                                    return;
                                }
                            }
                            if (em.a(be[bs[0]])) {
                                n5 = -((ap.fb >> ca[bs[0]]) % bw[bs[0]]);
                                while (em.j(n5, ap.fq)) {
                                    bh2.a(be[bs[0]], n5, bb, bs[2]);
                                    n5 += bw[bs[0]];
                                    "".length();
                                    if (-(42 + 59 - -35 + 21 ^ 129 + 107 - 181 + 98) <= 0) continue;
                                    return;
                                }
                            }
                            if (em.a(be[bs[2]])) {
                                n5 = -((ap.fb >> ca[bs[2]]) % bw[bs[2]]);
                                while (em.j(n5, ap.fq)) {
                                    bh2.a(be[bs[2]], n5, bv, bs[2]);
                                    n5 += bw[bs[2]];
                                    "".length();
                                    if ("   ".length() >= 0) continue;
                                    return;
                                }
                                return;
                            }
                        }
                    } else if (em.f(b, bs[25]) && em.g(b, bs[30])) {
                        int n6;
                        bh2.a(bn);
                        bh2.e(bs[2], bs[2], ap.fq, ap.bv);
                        if (em.h(b, bs[17]) && em.a(be[bs[8]])) {
                            n6 = -((ap.fb >> ca[bs[8]]) % bw[bs[8]]);
                            while (em.j(n6, ap.fq)) {
                                if (em.h(b, bs[31]) && em.h(b, bs[19])) {
                                    bh2.a(be[bs[8]], n6, bm, bs[2]);
                                    "".length();
                                    if (-" ".length() >= "   ".length()) {
                                        return;
                                    }
                                } else {
                                    bh2.a(be[bs[8]], n6, ap.bv - bh.b(be[bs[8]]), bs[2]);
                                }
                                n6 += bw[bs[8]];
                                "".length();
                                if (((0x49 ^ 0xF) & ~(0x7E ^ 0x38)) == 0) continue;
                                return;
                            }
                        }
                        if (em.h(b, bs[17]) && em.h(b, bs[31]) && em.h(b, bs[19]) && em.a(be[bs[1]])) {
                            if (em.k(de.i, bs[32])) {
                                bh2.a(be[bs[1]], ap.fq, bh, bs[2]);
                                "".length();
                                if (null != null) {
                                    return;
                                }
                            } else {
                                n6 = -((ap.fb >> ca[bs[1]]) % bw[bs[1]]);
                                while (em.j(n6, ap.fq)) {
                                    if (em.k(b, bs[33])) {
                                        bh2.a(be[bs[1]], n6, bh + bs[19], bs[2]);
                                        "".length();
                                        if (-" ".length() != -" ".length()) {
                                            return;
                                        }
                                    } else {
                                        bh2.a(be[bs[1]], n6, bh, bs[2]);
                                    }
                                    n6 += bw[bs[1]];
                                    "".length();
                                    if (((18 + 80 - 67 + 133 ^ 2 + 139 - 88 + 94) & (0xB6 ^ 0xC7 ^ (0x19 ^ 0x5F) ^ -" ".length())) <= "  ".length()) continue;
                                    return;
                                }
                            }
                        }
                        if (em.h(b, bs[31]) && em.h(b, bs[19]) && em.a(be[bs[0]]) && em.h(de.i, bs[34])) {
                            n6 = -((ap.fb >> ca[bs[0]]) % bw[bs[0]]);
                            while (em.j(n6, ap.fq)) {
                                bh2.a(be[bs[0]], n6, bb, bs[2]);
                                n6 += bw[bs[0]];
                                "".length();
                                if (((0x94 ^ 0xC3) & ~(0x56 ^ 1)) == 0) continue;
                                return;
                            }
                        }
                        if (!em.h(de.i, bs[32]) || em.k(de.i, bs[35])) {
                            bh2.a(bs[36]);
                            bh2.e(bs[2], bv + bs[23], ap.fq, ap.bv);
                        }
                        if (em.a(be[bs[2]])) {
                            n6 = -((ap.fb >> ca[bs[2]]) % bw[bs[2]]);
                            while (em.j(n6, ap.fq)) {
                                bh2.a(be[bs[2]], n6, bv, bs[2]);
                                n6 += bw[bs[2]];
                                "".length();
                                if ((0xBA ^ 0xBF) > 0) continue;
                                return;
                            }
                        }
                        if (em.a(bg)) {
                            if (em.h(b, bs[20]) && em.h(b, bs[6])) {
                                n6 = bs[2];
                                while (em.j(n6, bs[1])) {
                                    bh2.a(bg, by[n6], bq[n6], bs[8]);
                                    ++n6;
                                    "".length();
                                    if ("   ".length() > " ".length()) continue;
                                    return;
                                }
                                return;
                            }
                            n6 = bs[2];
                            while (em.j(n6, bs[1])) {
                                bh2.a(bg, by[n6], bq[n6] - bs[37], bs[8]);
                                ++n6;
                                "".length();
                                if (null == null) continue;
                                return;
                            }
                            return;
                        }
                    }
                    "".length();
                    if (((0xD8 ^ 0x8B ^ (0xD7 ^ 0x92)) & (86 + 6 - 63 + 110 ^ 108 + 123 - 121 + 47 ^ -" ".length())) != 0) {
                        return;
                    }
                } else {
                    bh2.a(bn);
                    bh2.e(bs[2], bs[2], ap.fq, ap.bv);
                }
            }
        }
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public void a(eh eh2) {
    }

    private static boolean k(int n2, int n3) {
        return n2 == n3;
    }

    public static void c(String string) {
        g.a(string, null, new fr(ag.v, f, bs[59], null), null);
        d = g;
    }
}

