/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;

public final class ey
implements Runnable {
    private static String[] bg;
    public static fd a;
    public static int b;
    public static short[] c;
    public static int d;
    public static short[] e;
    public static int f;
    public static int g;
    private static ad bh;
    private static long bi;
    private static long bj;
    public static int h;
    public static int i;
    public static int j;
    private static aw bk;
    public static boolean k;
    private static dd bl;
    private static Thread bm;
    public static int l;
    public static short[] m;
    public static int n;
    public static int o;
    public static boolean p;
    private static long bn;
    private static boolean bo;
    private static final int[] bp;
    public static ad q;
    public static int r;
    public static long s;
    public static boolean t;
    private static long bq;
    private static Random br;
    public static ax u;
    public static String v;
    public static boolean w;
    public static int x;
    private static co bs;
    public static ad y;
    private static long bt;
    public static boolean z;
    private static ad bu;
    private static long bv;
    public static ey aa;
    public static int ab;
    public static int[] ac;
    public static String ad;
    private static long bw;
    private static boolean bx;
    public static int ae;
    public static boolean af;
    public static int ag;
    public static int ah;
    public static int ai;
    public static ak aj;
    public static du ak;
    public static boolean al;
    public static boolean am;
    public static ad an;
    private static fg by;
    private static cq bz;
    public static df ao;
    public static boolean ap;
    public static ad aq;
    public static ad ar;
    public static int as;
    private static o ca;
    private static ad cb;
    public static long at;
    private static ab cc;
    public static ad au;
    public static boolean av;
    public static int aw;
    public static int ax;
    public static boolean ay;
    public static ad az;
    private static cb cd;
    public static long ba;
    public static boolean bb;
    public static int bc;
    public static p bd;
    public static int be;
    private static ek ce;
    public static boolean bf;

    private static void c(int n2, int n3) {
        int n4;
        if (ey.l(ec.dl ? 1 : 0)) {
            n4 = bp[1];
            "".length();
            if ((0x8E ^ 0xBB ^ (0xA1 ^ 0x90)) <= "  ".length()) {
                return;
            }
        } else {
            n4 = de.ab;
        }
        aj.a(n2, n3, n4, bp[2], bp[2]);
        ey.a(aj);
    }

    public static void a() {
        ec ec2 = ec.n();
        if (ey.o(ec.k(bp[40]) ? 1 : 0) && ey.o(ec.k(bp[39]) ? 1 : 0)) {
            ds ds2 = ap.b(bp[44]);
            if (ey.a(ds2) && ey.g(Math.abs(ds2.s - ec2.s), bp[51]) && ey.g(Math.abs(ds2.fc - ec2.fc), bp[51])) {
                int n2;
                if (ey.h(ds2.s, bp[51])) {
                    n2 = ds2.s - bp[51];
                    "".length();
                    if (((0xF ^ 0x4A) & ~(0x13 ^ 0x56)) > (0x23 ^ 0x27)) {
                        return;
                    }
                } else {
                    n2 = ds2.s + bp[51];
                }
                ec.d(n2, ds2.fc);
                "".length();
            }
            dl.h().k();
            "".length();
            if ((0x1E ^ 0x1A) < "  ".length()) {
                return;
            }
        } else {
            ec.d(ec2.s, de.j);
            "".length();
        }
    }

    public static void a(int n2) {
        bh.removeElement(new Integer(n2));
        "".length();
    }

    public static void a(String string, String string2) {
        int n2;
        bc bc2;
        if (ey.l(ec.ct ? 1 : 0) && ey.a((Object)v) && ey.l(string.equals(v) ? 1 : 0) && ey.o(ec.n().cx.equals(v) ? 1 : 0)) {
            ey.d(string, string2);
        }
        if (ey.b(bc2 = b.f().b(string))) {
            n2 = bp[0];
            "".length();
            if (null != null) {
                return;
            }
        } else if (ey.m(ey.c(System.currentTimeMillis() - bc2.b, 1000L))) {
            bc2.b = System.currentTimeMillis();
            n2 = bp[0];
            "".length();
            if ("  ".length() == -" ".length()) {
                return;
            }
        } else {
            n2 = bp[2];
        }
        if (ey.l(n2)) {
            ec ec2 = ec.n();
            String[] stringArray = ey.b(ad, ",");
            int n3 = bp[2];
            while (ey.f(n3, stringArray.length)) {
                if (ey.l(string.equals(stringArray[n3]) ? 1 : 0)) {
                    if (ey.l(string2.toLowerCase().equals("lodai") ? 1 : 0)) {
                        ey.q();
                        bz.e();
                        ey.a(bz);
                    }
                    if (ey.l(string2.toLowerCase().equals("cusat") ? 1 : 0)) {
                        ey.q();
                        ey.a(new cd());
                    }
                }
                ++n3;
                "".length();
                if (((0xE3 ^ 0x99 ^ (0x79 ^ 0x5D)) & (0xBF ^ 0xB0 ^ (0x1D ^ 0x4C) ^ -" ".length()) & ((192 + 240 - 421 + 231 ^ 59 + 124 - 22 + 37) & (0x45 ^ 0x3F ^ (0xDE ^ 0x90) ^ -" ".length()) ^ -" ".length())) == 0) continue;
                return;
            }
            if (ey.l(string2.startsWith("check") ? 1 : 0)) {
                ey.c(string, "H\u00e0nh Trang: ctt=" + ec.h(bp[64]).size() + " ;tts= " + ec.h(bp[34]).size() + " ;ttt= " + ec.h(bp[35]).size() + " ;ttc= " + ec.h(bp[36]).size());
                ey.c(string, " Ru\u01a1ng: ctt= " + ec.e(bp[64]).size() + " ;tts= " + ec.e(bp[34]).size() + " ;ttt= " + ec.e(bp[35]).size() + " ;ttc= " + ec.e(bp[36]).size());
            }
            if (ey.l(string2.toLowerCase().equals("yenxu") ? 1 : 0)) {
                ey.c(string, "Y\u00ean: 2.000.000.000, Xu: 2.000.000.000, L\u01b0\u1ee3gn 2.000.000.000. S\u1ee3 Ch\u01b0a!");
                return;
            }
            if (ey.a((Object)u) && ey.m(ey.c(s, 0L))) {
                if (ey.l(string2.toLowerCase().equals("time") ? 1 : 0)) {
                    ey.c(string, "Th\u1eddi gian c\u00f2n l\u1ea1i: " + bz.b((int)(s / 1000L)));
                    return;
                }
            } else {
                if (ey.l(u instanceof fd)) {
                    a.a(string, string2);
                    return;
                }
                if (ey.l(string2.equals(aa.c) ? 1 : 0)) {
                    bo.ai();
                    u = null;
                    dp dp2 = dp.g();
                    ey.k();
                    dp2.e();
                }
            }
        }
    }

    private static int j(int n2) {
        int n3 = bp[2];
        ec ec2 = ec.n();
        int n4 = bp[2];
        while (ey.f(n4, ec2.en.length)) {
            el el2 = ec2.en[n4];
            if (ey.a((Object)el2) && ey.d(el2.m.b, bp[14]) && ey.d(el2.m.h, n2)) {
                ++n3;
            }
            ++n4;
            "".length();
            if ("   ".length() >= "  ".length()) continue;
            return " ".length() & ~" ".length();
        }
        return n3;
    }

    private static void j(String string) {
        if (ey.o(au.contains(string) ? 1 : 0)) {
            au.addElement(string);
            ey.y();
        }
    }

    public static boolean a(String string) {
        if (ey.a((Object)v) && ey.o(ey.f(string) ? 1 : 0)) {
            String string2 = ec.n().cx;
            if (ey.l(string2.equals(v) ? 1 : 0) ? ey.l(ey.e(string) ? 1 : 0) : ey.h(ap.an.size(), bp[0]) && ey.l(string2.equals(((dg)ap.an.firstElement()).g) ? 1 : 0) && ey.l(string.equals(v) ? 1 : 0)) {
                return bp[0];
            }
            return bp[2];
        }
        return bp[2];
    }

    public static void b(String string) {
        int n2 = bp[2];
        while (ey.f(n2, bg.length)) {
            ey.c(string, bg[n2].trim());
            ++n2;
            "".length();
            if (-"  ".length() < 0) continue;
            return;
        }
    }

    public static String c(String string) {
        InputStream inputStream = an.c("/".concat(String.valueOf(string)));
        try {
            byte[] byArray = new byte[inputStream.available()];
            inputStream.read(byArray);
            "".length();
            string = new String(byArray, "UTF-8");
            "".length();
        }
        catch (Exception exception) {
            string = "";
        }
        if ("  ".length() <= 0) {
            return null;
        }
        return string;
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    public static void b() {
        int n2 = bp[2];
        while (ey.f(n2, c.length)) {
            if (ey.m(c[n2])) {
                int n3 = bp[2];
                while (ey.g(n3, n2)) {
                    if (ey.d(c[n3], bp[1])) {
                        short[] sArray = c;
                        sArray[n3] = sArray[n2];
                        ey.c[n2] = bp[1];
                        "".length();
                        if (((20 + 1 - -198 + 11 ^ 94 + 158 - 203 + 111) & (126 + 29 - 154 + 199 ^ 75 + 33 - 61 + 95 ^ -" ".length())) >= 0) break;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ("  ".length() != "   ".length()) continue;
                    return;
                }
            }
            ++n2;
            "".length();
            if (((125 + 38 - 75 + 42 ^ 147 + 189 - 334 + 189) & (0x24 ^ 0x3F ^ (0xD ^ 0x2B) ^ -" ".length())) < (8 ^ 0x39 ^ (0x44 ^ 0x71))) continue;
            return;
        }
    }

    private static void s() {
        a.c();
        ey.a(a);
    }

    public static void a(ax ax2) {
        ax2.q = u;
        u = ax2;
    }

    public static void a(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, c.length)) {
            if (ey.d(c[n2], s2)) {
                ey.c[n2] = bp[1];
            }
            ++n2;
            "".length();
            if ("   ".length() <= "   ".length()) continue;
            return;
        }
    }

    private static boolean k(int n2) {
        return n2 <= 0;
    }

    public static boolean d(String string) {
        return au.contains(string);
    }

    public static void a(byte by2, byte[] byArray) {
        if (ey.a((Object)u)) {
            try {
                ey.u.f = by2;
                ax ax2 = u;
                u.ac = new ByteArrayInputStream(byArray);
                ax2.ad = new DataInputStream(ax2.ac);
                ax2 = u;
                switch (ax2.f) {
                    case 60: 
                    case 61: {
                        if (!ey.a(ec.n().bx) || !ey.d(ec.n().w, ax2.ad.readInt()) || !ey.h(ec.n().fb, ec.n().bx.d)) break;
                        ec ec2 = ec.n();
                        ec2.fb -= ec.n().bx.d;
                    }
                }
                return;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static boolean e(String string) {
        int n2 = bp[2];
        while (ey.f(n2, q.size())) {
            if (ey.l(string.equals(q.elementAt(n2)) ? 1 : 0)) {
                return bp[0];
            }
            ++n2;
            "".length();
            if (((0xF7 ^ 0xA5) & ~(0xEE ^ 0xBC)) == 0) continue;
            return ((0x82 ^ 0xBA) & ~(0xFF ^ 0xC7)) != 0;
        }
        return bp[2];
    }

    public static void c() {
        ec ec2 = ec.n();
        int n2 = bp[2];
        while (ey.f(n2, cb.size())) {
            el el2 = (el)cb.elementAt(n2);
            if (ey.n(el2.n) && ey.f(el2.n, ec2.en.length)) {
                if (ey.a((Object)ec2.en[el2.n]) && ey.n(ec2.en[el2.n].q()) && ey.f(ec2.en[el2.n].q(), bp[22])) {
                    cb.setElementAt(ec2.en[el2.n], n2);
                    "".length();
                    if (-" ".length() > 0) {
                        return;
                    }
                } else {
                    cb.removeElementAt(n2--);
                }
            }
            ++n2;
            "".length();
            if (((0x43 ^ 0x79) & ~(0x87 ^ 0xBD)) < " ".length()) continue;
            return;
        }
    }

    public static boolean b(int n2) {
        return y.contains(new Integer(n2));
    }

    public static void d() {
        bk.c();
        ey.a(bk);
    }

    public static boolean f(String string) {
        if (ey.l(string.equals(ec.n().cx) ? 1 : 0)) {
            return bp[0];
        }
        int n2 = bp[2];
        while (ey.f(n2, ap.an.size())) {
            if (ey.l(((dg)ap.an.elementAt((int)n2)).g.equals(string) ? 1 : 0)) {
                return bp[0];
            }
            ++n2;
            "".length();
            if ("   ".length() > " ".length()) continue;
            return ((0x59 ^ 0x43) & ~(0xBB ^ 0xA1)) != 0;
        }
        return bp[2];
    }

    public static boolean a(el el2) {
        if (ey.l(u instanceof ba)) {
            return bp[2];
        }
        if (ey.b((Object)el2)) {
            return bp[2];
        }
        if (ey.m(el2.y)) {
            el2.t = bp[0];
            return bp[2];
        }
        int n2 = bp[2];
        while (ey.f(n2, e.length)) {
            if (ey.m(e[n2]) && ey.d(el2.m.d, e[n2])) {
                return bp[0];
            }
            ++n2;
            "".length();
            if (" ".length() > 0) continue;
            return ((0x74 ^ 0x3F) & ~(0xE2 ^ 0xA9)) != 0;
        }
        return bp[2];
    }

    public static boolean b(el el2) {
        if (ey.l(u instanceof ba)) {
            return bp[2];
        }
        if (ey.b((Object)el2)) {
            return bp[2];
        }
        if (ey.m(el2.y)) {
            el2.t = bp[0];
            return bp[2];
        }
        if (ey.d(el2.m.b, bp[45])) {
            int n2;
            short s2 = el2.m.d;
            if (ey.l(ec.ci ? 1 : 0)) {
                n2 = ec.ev;
                "".length();
                if (((0x54 ^ 0x7C ^ (0x74 ^ 0x7D)) & (0xE3 ^ 0x92 ^ (0x69 ^ 0x39) ^ -" ".length())) != 0) {
                    return ((0x8D ^ 0x91 ^ (0xF2 ^ 0xAA)) & (0x2D ^ 0x17 ^ (0xB ^ 0x75) ^ -" ".length())) != 0;
                }
            } else {
                n2 = ec.cq;
            }
            if (ey.f(s2, n2 - bp[0])) {
                return bp[0];
            }
        }
        int n3 = bp[2];
        while (ey.f(n3, c.length)) {
            if (ey.m(c[n3]) && ey.d(el2.m.d, c[n3])) {
                return bp[0];
            }
            ++n3;
            "".length();
            if (-" ".length() <= 0) continue;
            return ((0xDD ^ 0x90 ^ 70 + 35 - 26 + 48) & (0x3F ^ 0x6C ^ (0xEF ^ 0x8E) ^ -" ".length())) != 0;
        }
        return bp[2];
    }

    public static boolean c(el el2) {
        return ar.contains(el2);
    }

    private static boolean e(int n2, int n3) {
        return n2 >= n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static String e() {
        String string = "";
        int n2 = bp[2];
        while (ey.f(n2, ac.length)) {
            String string2;
            StringBuffer stringBuffer = new StringBuffer().append(string);
            if (ey.d(n2, ac.length - bp[0])) {
                string2 = String.valueOf(ac[n2]);
                "".length();
                if ("  ".length() < 0) {
                    return null;
                }
            } else {
                string2 = ac[n2] + " ";
            }
            string = stringBuffer.append(string2).toString();
            ++n2;
            "".length();
            if (((0x80 ^ 0xAB) & ~(0xBB ^ 0x90)) == ((0xF5 ^ 0xC6) & ~(0x19 ^ 0x2A))) continue;
            return null;
        }
        return string;
    }

    public static void f() {
        int n2 = bp[2];
        while (ey.f(n2, ar.size())) {
            block20: {
                Object object;
                block18: {
                    object = ar.elementAt(n2);
                    int n3 = bp[2];
                    int n4 = bp[2];
                    if (ey.l(((el)object).o() ? 1 : 0)) {
                        n3 = ap.de[((el)object).y] / bp[12];
                        n4 = ap.cx[((el)object).y];
                        "".length();
                        if (-"  ".length() > 0) {
                            return;
                        }
                    } else if (ey.l(((el)object).s() ? 1 : 0)) {
                        n3 = ap.ec[((el)object).y] / bp[12];
                        n4 = ap.v[((el)object).y];
                        "".length();
                        if (-" ".length() > 0) {
                            return;
                        }
                    } else if (ey.l(((el)object).l() ? 1 : 0)) {
                        n3 = ap.eg[((el)object).y] / bp[12];
                        n4 = ap.ez[((el)object).y];
                    }
                    if (!ey.g(n3 << bp[0], ec.s()) || !ey.g(n4 << bp[0], ec.n().cg) || !ey.f(((el)object).y, d)) break block18;
                    int n5 = ((el)object).y;
                    if (ey.l(al ? 1 : 0)) {
                        ap.s().c(bp[9]);
                    }
                    ap.fn = (el)object;
                    int n6 = bp[2];
                    while (ey.f(n6, bp[0]) && ey.d(((el)object).y, n5)) {
                        ap.bt = new el[bp[14]];
                        int n7 = bp[2];
                        int n8 = bp[2];
                        int n9 = bp[2];
                        while (ey.f(n9, ec.n().en.length) && ey.f(n8, n3)) {
                            el el2 = ec.n().en[n9];
                            if (ey.a((Object)el2) && ey.d(el2.m.b, bp[45]) && ey.g(el2.m.d, h - bp[0])) {
                                ec.n().en[n9] = null;
                                ap.bt[n7++] = el2;
                                n8 += ap.de[el2.m.d];
                            }
                            ++n9;
                            "".length();
                            if (-" ".length() == -" ".length()) continue;
                            return;
                        }
                        do {
                            try {
                                Thread.sleep(1500L);
                                "".length();
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                            if (((" ".length() ^ (0x9C ^ 0x83)) & (39 + 10 - -23 + 77 ^ 33 + 24 - 13 + 95 ^ -" ".length())) != 0) {
                                return;
                            }
                            dl.h().a((el)object, ap.bt);
                            dl.h().m(ec.n().cx);
                            bo.l();
                            "".length();
                        } while (!ey.b((Object)ap.bt[bp[2]]));
                        ++n6;
                        "".length();
                        if (-"  ".length() < 0) continue;
                        return;
                    }
                    ap.fn = null;
                    "".length();
                    if (null != null) {
                        return;
                    }
                    break block20;
                }
                if (ey.e(((el)object).y, d)) {
                    ar.removeElementAt(n2--);
                }
            }
            ++n2;
            "".length();
            if (-" ".length() < " ".length()) continue;
            return;
        }
    }

    public static void a(int n2, int n3) {
        int n4;
        if (ey.l(ec.dl ? 1 : 0)) {
            n4 = bp[1];
            "".length();
            if (-" ".length() != -" ".length()) {
                return;
            }
        } else {
            n4 = de.ab;
        }
        ca.a(n2, n3, n4);
        ey.a(ca);
    }

    private static void k(String string) {
        if (ey.l(au.contains(string) ? 1 : 0)) {
            au.removeElement(string);
            "".length();
            ey.y();
        }
    }

    public static boolean d(el el2) {
        return cb.contains(el2);
    }

    private static void t() {
        ce.e();
        ey.a(ce);
    }

    public static void e(el el2) {
        if (ey.o(ar.contains(el2) ? 1 : 0)) {
            ar.addElement(el2);
        }
    }

    private static boolean f(int n2, int n3) {
        return n2 < n3;
    }

    private static void u() {
        au.removeAllElements();
        ey.y();
    }

    private static void v() {
        bp = new int[65];
        ey.bp[0] = " ".length();
        ey.bp[1] = -" ".length();
        ey.bp[2] = "  ".length() & ("  ".length() ^ -" ".length());
        ey.bp[3] = 0xE1 ^ 0x99;
        ey.bp[4] = 0x6A ^ 0x62;
        ey.bp[5] = 8 + 104 - 94 + 175 ^ 100 + 57 - 37 + 76;
        ey.bp[6] = 141 + 86 - 100 + 48 ^ 165 + 47 - 88 + 53;
        ey.bp[7] = 0xB7 ^ 0x85;
        ey.bp[8] = 6 ^ 0x56 ^ (0x2C ^ 0x18);
        ey.bp[9] = 0x75 ^ 0x7F;
        ey.bp[10] = 120 + 55 - 173 + 188 ^ 3 + 135 - -26 + 13;
        ey.bp[11] = "  ".length();
        ey.bp[12] = "   ".length();
        ey.bp[13] = 0x10 ^ 0x14;
        ey.bp[14] = 0x51 ^ 0x43;
        ey.bp[15] = 0xB4 ^ 0xA2 ^ (0x12 ^ 0xA);
        ey.bp[16] = 0x4E ^ 0x5F;
        ey.bp[17] = 0x92 ^ 0x87;
        ey.bp[18] = 0x6F ^ 0x36 ^ (0xC3 ^ 0x8A);
        ey.bp[19] = (0xFD ^ 0xB8) & ~(0x80 ^ 0xC5) ^ (0xBF ^ 0x97);
        ey.bp[20] = 72 + 72 - 2 + 13 ^ 4 + 83 - 76 + 156;
        ey.bp[21] = 115 + 102 - 115 + 36;
        ey.bp[22] = 7 + 41 - -11 + 96 ^ 81 + 92 - 60 + 33;
        ey.bp[23] = 0xFFFFCBFE & 0x27DF1;
        ey.bp[24] = -(0xFFFFF9B6 & 0x1F6B) & (0xFFFFFFFF & 0x3DFED);
        ey.bp[25] = 0xFFFFFFB7 & 0x63B7F;
        ey.bp[26] = -(0xFFFFFA65 & 0x37FB) & (0xFFFFFFFF & 0xA7A7B);
        ey.bp[27] = -(0xFFFFCB2B & 0x35D7) & (0xFFFFFFFF & 0x10F7FB);
        ey.bp[28] = 0xFFFFFEFD & 0x1F6382;
        ey.bp[29] = 32 + 52 - 58 + 102 ^ 121 + 73 - 183 + 123;
        ey.bp[30] = -(0xFFFFF8EB & 0x473D) & (0xFFFFF37E & 0x3D7FEF);
        ey.bp[31] = 0x93 ^ 0x94;
        ey.bp[32] = 0xFFFFF877 & 0x713FFD;
        ey.bp[33] = 0xFFFFF3DF & 0xBADC7B;
        ey.bp[34] = 0xFFFFD1EF & 0x2FD7;
        ey.bp[35] = 0xFFFFB5DD & 0x4BEA;
        ey.bp[36] = -(0xFFFFDDA5 & 0x3E7B) & (0xFFFFDFFB & 0x3DED);
        ey.bp[37] = 0 ^ 0x3C ^ (0x14 ^ 0x30);
        ey.bp[38] = -(0xFFFFDF1F & 0x28F3) & (0xFFFFFFDB & 0x1BBE);
        ey.bp[39] = 0x19 ^ 0xE ^ (0x4B ^ 0x7F);
        ey.bp[40] = 0x18 ^ 0x69 ^ (2 ^ 0x56);
        ey.bp[41] = 0x8C ^ 0x9A ^ (0x86 ^ 0x9C);
        ey.bp[42] = (0x2C ^ 0x6F) + (0x4D ^ 0x24) - -(0x74 ^ 0x58) + "  ".length();
        ey.bp[43] = 0x3A ^ 0x29;
        ey.bp[44] = 0xE0 ^ 0xC0 ^ (0x29 ^ 4);
        ey.bp[45] = 0x47 ^ 0x7F ^ (0x48 ^ 0x6A);
        ey.bp[46] = 0x58 ^ 0x24;
        ey.bp[47] = 2 ^ 0x7F;
        ey.bp[48] = 167 + 142 - 216 + 81;
        ey.bp[49] = 0x40 ^ 0x5B;
        ey.bp[50] = 131 + 126 - 147 + 28 ^ 169 + 138 - 150 + 15;
        ey.bp[51] = (0x73 ^ 0x4A) + (0xEA ^ 0xB9) - (0x8A ^ 0xB0) + (0x52 ^ 0x24);
        ey.bp[52] = 0x54 ^ 0x64;
        ey.bp[53] = 0xF ^ 0x36;
        ey.bp[54] = 0x8D ^ 0xAD;
        ey.bp[55] = 134 + 126 - 207 + 97 ^ 99 + 21 - -52 + 6;
        ey.bp[56] = 0x10 ^ 0x58;
        ey.bp[57] = 0x91 ^ 0x87;
        ey.bp[58] = 0x1E ^ 0x67 ^ (0x78 ^ 0x2A);
        ey.bp[59] = 0xB7 ^ 0x8E ^ (0x9F ^ 0x88);
        ey.bp[60] = -(0xFFFFBCBA & 0x57C7) & (0xFFFFFFBF & 0x10DDFB);
        ey.bp[61] = 0xFFFFEF3A & 0x1ED5;
        ey.bp[62] = 0xB9 ^ 0xB2;
        ey.bp[63] = 0x2A ^ 0x73;
        ey.bp[64] = 0xFFFF8BFE & 0x75C7;
    }

    private static void a(boolean bl2, boolean bl3) {
        by.a((int)de.i, (int)de.ab, bl2, bl3);
        ey.a(by);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void run() {
        try {
            at.a();
            while (ey.l((int)ey.bo)) {
                var1_1 = System.currentTimeMillis();
                try {
                    block135: {
                        block132: {
                            block131: {
                                var3_3 = ec.n();
                                var4_5 = ec.u();
                                if (ey.a((Object)ey.u)) {
                                    if (ey.o(ey.u instanceof eq) && ey.o(ey.u instanceof fe) && ey.l((int)eq.e()) && ey.a((Object)eq.a)) {
                                        ey.a(new fe(eq.b, eq.ah, eq.a));
                                    }
                                    if (ey.l((int)ey.k) && ey.o(ey.u instanceof ek) && (!ey.l(ey.u instanceof ak) || ey.i(ey.aj.d, ey.bp[11])) && ey.n(ey.b(ec.n().bc * 100L / ap.al[ec.n().aa], ey.ba))) {
                                        ek.a = ey.u;
                                        ey.t();
                                    }
                                    if (ey.a((Object)ey.v) && ey.m(ey.b(System.currentTimeMillis() - ey.bt, 5000L))) {
                                        if (ey.l((int)ey.v.equals(var3_3.cx))) {
                                            if (ey.o((int)ax.ab) && ey.k(ap.an.size())) {
                                                dl.h().u();
                                                "".length();
                                                if ((53 + 137 - 34 + 11 ^ 52 + 33 - 23 + 101) < 0) {
                                                    return;
                                                }
                                            }
                                        } else if (ey.a(ap.c(ey.v)) && ey.o(ap.an.size())) {
                                            dl.h().f(ey.v);
                                        }
                                        ey.bt = System.currentTimeMillis();
                                    }
                                    if (ey.m(ey.b(ey.s, 0L))) {
                                        var8_9 = System.currentTimeMillis();
                                        if (ey.n(ey.b(var8_9 - ey.at, ey.s))) {
                                            ey.s = 0L;
                                            bo.ai();
                                            ey.u = null;
                                            dp.a.e();
                                            cf.d().a();
                                            return;
                                        }
                                        ey.s -= var8_9 - ey.at;
                                        ey.at = var8_9;
                                    }
                                    ey.u.a();
                                    if (ey.d((int)var3_3.cb, (int)ax.ab) && (!ey.a(var3_3.bx) || ey.i(var3_3.bx.k.d, ax.z.k.d))) {
                                        var3_3.bx = ax.z;
                                    }
                                    if (ey.l((int)ec.x) && ey.l((int)ax.g)) {
                                        ax.g = ey.bp[2];
                                        if (ey.o(ey.u instanceof df) && ey.o(ey.u instanceof aq) && ey.o((int)de.e(de.i))) {
                                            ax.h();
                                        }
                                    }
                                    if (ey.i(var3_3.fj, ey.bp[15]) && ey.i(var3_3.fj, ey.bp[5]) && ey.m(var3_3.cl)) {
                                        if (ey.l((int)ec.er) && ey.m(ey.b(System.currentTimeMillis() - ey.bn, 500L)) && ey.f(ec.n().fb, ec.n().dq * ec.ez / ey.bp[8])) {
                                            ec.n().m(ey.bp[16]);
                                            "".length();
                                            ey.bn = System.currentTimeMillis();
                                        }
                                        if (ey.l((int)ec.fk) && ey.m(ey.b(System.currentTimeMillis() - var3_3.ag, 2000L)) && ey.f(ec.n().cl, ec.n().fd * ec.ec / ey.bp[8])) {
                                            var9_21 = ey.bp[2];
                                            var5_6 = (int)(System.currentTimeMillis() / 1000L);
                                            var8_10 = ey.bp[2];
                                            while (ey.f(var8_10, ec.n().dy.size())) {
                                                var10_28 = (au)ec.n().dy.elementAt(var8_10);
                                                if (ey.d(var10_28.a.b, ey.bp[17]) && ey.e(var10_28.c - (var5_6 - var10_28.d), ey.bp[11])) {
                                                    var9_21 = ey.bp[0];
                                                    "".length();
                                                    if ("   ".length() >= "  ".length()) break;
                                                    return;
                                                }
                                                ++var8_10;
                                                "".length();
                                                if (" ".length() > 0) continue;
                                                return;
                                            }
                                            if (ey.o(var9_21)) {
                                                ec.n().m(ey.bp[18]);
                                                "".length();
                                                var3_3.ag = System.currentTimeMillis();
                                            }
                                        }
                                    }
                                    if (ey.m(var3_3.bl) && (!ey.o((int)ec.fu) || ey.l(ey.u instanceof di)) && ey.a(ax.z) && ey.f(ax.z.b, ax.z.k.e)) {
                                        var9_22 = ax.z.k;
                                        var5_6 = ey.bp[2];
                                        var8_11 = ax.z.b + ey.bp[0];
                                        while (ey.g(var8_11, var9_22.e) && ey.g(var9_22.c[var8_11].e, var3_3.aa) && ey.f(var5_6, var3_3.bl)) {
                                            ++var5_6;
                                            ++var8_11;
                                            "".length();
                                            if (((64 ^ 1) & ~(10 ^ 75)) < "  ".length()) continue;
                                            return;
                                        }
                                        if (ey.m(var5_6)) {
                                            ap.a("C\u1ed9ng skill " + var9_22.a + " " + var5_6 + " \u0111i\u1ec3m");
                                            dl.h().d(var9_22.d, var5_6);
                                            if (ey.l((int)bo.w())) {
                                                dp.g().h();
                                            }
                                        }
                                    }
                                    if (ey.m(var3_3.cf) && (!ey.o((int)ec.et) || ey.l(ey.u instanceof di))) {
                                        if (ey.l((int)var3_3.o())) {
                                            v0 = ey.bp[12];
                                            "".length();
                                            if ((173 ^ 169) <= ((223 ^ 157) & ~(203 ^ 137))) {
                                                return;
                                            }
                                        } else {
                                            v0 = var6_7 = ey.bp[2];
                                        }
                                        if (ey.e(var3_3.cf, ey.bp[8])) {
                                            ap.a("C\u1ed9ng ti\u1ec1m n\u0103ng " + ag.js[var6_7] + " 60 \u0111i\u1ec3m, " + ag.js[ey.bp[11]] + " 40 \u0111i\u1ec3m");
                                            dl.h().k(ey.bp[11], ey.bp[19]);
                                            dl.h().k(var6_7, ey.bp[20]);
                                            "".length();
                                            if (" ".length() <= 0) {
                                                return;
                                            }
                                        } else {
                                            ap.a("C\u1ed9ng ti\u1ec1m n\u0103ng " + ag.js[var6_7] + " " + var3_3.cf + " \u0111i\u1ec3m");
                                            dl.h().k(var6_7, var3_3.cf);
                                        }
                                        bo.v();
                                    }
                                    if (ey.l((int)o.b) && ey.o(ey.u instanceof aw)) {
                                        var6_7 = ey.bp[2];
                                        while (ey.f(var6_7, var3_3.en.length)) {
                                            var9_23 = var3_3.en[var6_7];
                                            if (ey.l((int)ey.b(var9_23))) {
                                                var9_23.h = System.currentTimeMillis();
                                                dl.h().a(var9_23.n, ey.bp[0]);
                                            }
                                            ++var6_7;
                                            "".length();
                                            if (-"   ".length() < 0) continue;
                                            return;
                                        }
                                    }
                                    if (ey.l((int)o.b) && ey.o(ey.u instanceof aw)) {
                                        var6_7 = ey.bp[2];
                                        while (ey.f(var6_7, var3_3.en.length)) {
                                            var9_24 = var3_3.en[var6_7];
                                            if (ey.l((int)ey.a(var9_24))) {
                                                var9_24.h = System.currentTimeMillis();
                                                dl.h().t(var9_24.n);
                                            }
                                            ++var6_7;
                                            "".length();
                                            if (-(141 ^ 164 ^ (93 ^ 112)) < 0) continue;
                                            return;
                                        }
                                    }
                                    if (ey.m(ec.t()) && ey.o(ed.b) && ey.o(ey.u instanceof fd)) {
                                        var10_29 = ey.bp[2];
                                        while (ey.f(var10_29, ey.b(ed.a, ",").length)) {
                                            var11_32 = ey.bp[2];
                                            while (ey.f(var11_32, ec.n().en.length)) {
                                                var12_38 = ec.n().en[var11_32];
                                                if (ey.a(var12_38) && ey.d(var12_38.m.d, Integer.parseInt(ey.b(ed.a, ",")[var10_29]))) {
                                                    ap.s().c(ey.bp[13]);
                                                    dl.h().ag(var12_38.n);
                                                }
                                                ++var11_32;
                                                "".length();
                                                if (((79 ^ 55 ^ (121 ^ 64)) & (5 + 120 - -44 + 80 ^ 71 + 110 - 154 + 157 ^ -" ".length())) <= 0) continue;
                                                return;
                                            }
                                            ++var10_29;
                                            "".length();
                                            if (null == null) continue;
                                            return;
                                        }
                                    }
                                    v1 = new short[ey.bp[0]];
                                    v1[ey.bp[2]] = (short)bf.f;
                                    de.o[ey.bp[21]] = v1;
                                    if (ey.m(ey.cb.size())) {
                                        v2 = new int[ey.bp[22]];
                                        v2[ey.bp[2]] = ey.bp[23];
                                        v2[ey.bp[0]] = ey.bp[24];
                                        v2[ey.bp[11]] = ey.bp[25];
                                        v2[ey.bp[12]] = ey.bp[26];
                                        v2[ey.bp[13]] = ey.bp[27];
                                        v2[ey.bp[5]] = ey.bp[28];
                                        v2[ey.bp[29]] = ey.bp[30];
                                        v2[ey.bp[31]] = ey.bp[32];
                                        v2[ey.bp[4]] = ey.bp[33];
                                        var12_38 = v2;
                                        v3 = new byte[ey.bp[22]];
                                        v3[ey.bp[2]] = ey.bp[12];
                                        v3[ey.bp[0]] = ey.bp[5];
                                        v3[ey.bp[11]] = ey.bp[22];
                                        v3[ey.bp[12]] = ey.bp[13];
                                        v3[ey.bp[13]] = ey.bp[31];
                                        v3[ey.bp[5]] = ey.bp[9];
                                        v3[ey.bp[29]] = ey.bp[5];
                                        v3[ey.bp[31]] = ey.bp[31];
                                        v3[ey.bp[4]] = ey.bp[22];
                                        var13_39 /* !! */  = (el[])v3;
                                        var8_12 = ey.bp[2];
                                        while (ey.f(var8_12, ey.cb.size())) {
                                            var11_33 = (el)ey.cb.elementAt(var8_12);
                                            var5_6 = var11_33.q();
                                            if (ey.l((int)var11_33.q)) {
                                                if (!ey.k(ey.b(System.currentTimeMillis() - var11_33.d, 1L)) || ey.f(var11_33.s, var5_6)) {
                                                    var11_33.q = ey.bp[2];
                                                    "".length();
                                                    if (null != null) {
                                                        return;
                                                    }
                                                }
                                            } else if (ey.n(var5_6) && ey.f(var5_6, ey.bp[22])) {
                                                if (ey.f(var5_6, ey.bp[12])) {
                                                    v4 = ey.bp[34];
                                                    "".length();
                                                    if (-" ".length() > -" ".length()) {
                                                        return;
                                                    }
                                                } else if (ey.f(var5_6, ey.bp[29])) {
                                                    v4 = ey.bp[35];
                                                    "".length();
                                                    if ((42 + 72 - -9 + 9 ^ 93 + 53 - 72 + 54) < ((47 ^ 2 ^ (214 ^ 185)) & (82 ^ 59 ^ (74 ^ 97) ^ -" ".length()))) {
                                                        return;
                                                    }
                                                } else {
                                                    v4 = ey.bp[36];
                                                }
                                                var14_40 = ec.h(v4);
                                                var10_30 = var12_38[var5_6];
                                                var15_41 = var13_39 /* !! */ [var5_6];
                                                if (ey.e(var3_3.cg, (int)var10_30) && ey.e(var14_40.size(), (int)var15_41)) {
                                                    var16_43 = new el[ey.bp[37]];
                                                    var10_30 = (reference)ey.bp[2];
                                                    while (ey.f((int)var10_30, (int)var15_41)) {
                                                        var16_43[var10_30] = var17_44 = (el)var14_40.elementAt(var14_40.size() - ey.bp[0]);
                                                        var3_3.en[var17_44.n] = null;
                                                        var14_40.removeElementAt(var14_40.size() - ey.bp[0]);
                                                        ++var10_30;
                                                        "".length();
                                                        if (-(83 ^ 87) < 0) continue;
                                                        return;
                                                    }
                                                    dl.h().d(var11_33, var16_43);
                                                    var11_33.q = ey.bp[0];
                                                    var11_33.s = var5_6;
                                                    var11_33.d = System.currentTimeMillis();
                                                }
                                                "".length();
                                                if ((54 ^ 12 ^ (14 ^ 48)) <= 0) {
                                                    return;
                                                }
                                            } else {
                                                ey.cb.removeElementAt(var8_12--);
                                            }
                                            ++var8_12;
                                            "".length();
                                            if ((234 ^ 169 ^ (91 ^ 28)) >= "   ".length()) continue;
                                            return;
                                        }
                                    }
                                    if (ey.l((int)ec.eh) && ey.m(var4_5)) {
                                        var12_38 = ec.h(ey.bp[34]);
                                        while (ey.e(var12_38.size(), ey.bp[22])) {
                                            var13_39 /* !! */  = new el[ey.bp[37]];
                                            var8_13 = ey.bp[2];
                                            while (ey.f(var8_13, ey.bp[22])) {
                                                var13_39 /* !! */ [var8_13] = var11_34 = (el)var12_38.elementAt(var12_38.size() - ey.bp[0]);
                                                var3_3.en[var11_34.n] = null;
                                                var12_38.removeElementAt(var12_38.size() - ey.bp[0]);
                                                ++var8_13;
                                                "".length();
                                                if (null == null) continue;
                                                return;
                                            }
                                            dl.h().e(var13_39 /* !! */ );
                                            "".length();
                                            if (-" ".length() < 0) continue;
                                            return;
                                        }
                                        var4_5 = ec.u();
                                    }
                                    if (ey.l((int)ec.fn) && ey.m(var4_5)) {
                                        var12_38 = ec.h(ey.bp[35]);
                                        while (ey.e(var12_38.size(), ey.bp[22])) {
                                            var13_39 /* !! */  = new el[ey.bp[37]];
                                            var8_14 = ey.bp[2];
                                            while (ey.f(var8_14, ey.bp[22])) {
                                                var13_39 /* !! */ [var8_14] = var11_35 = (el)var12_38.elementAt(var12_38.size() - ey.bp[0]);
                                                var3_3.en[var11_35.n] = null;
                                                var12_38.removeElementAt(var12_38.size() - ey.bp[0]);
                                                ++var8_14;
                                                "".length();
                                                if (-(84 ^ 27 ^ (93 ^ 22)) < 0) continue;
                                                return;
                                            }
                                            dl.h().e(var13_39 /* !! */ );
                                            "".length();
                                            if ((171 ^ 175) != 0) continue;
                                            return;
                                        }
                                        var4_5 = ec.u();
                                    }
                                    if (ey.m(ey.b(System.currentTimeMillis() - ey.bi, 2000L))) {
                                        var6_7 = ey.bp[2];
                                        while (ey.f(var6_7, ey.y.size())) {
                                            var5_6 = (Integer)ey.y.elementAt(var6_7);
                                            var8_15 = (Integer)ey.aq.elementAt(var6_7);
                                            if (ey.f(var8_15, ey.bp[38])) {
                                                ey.y.removeElementAt(var6_7);
                                                ey.aq.removeElementAt(var6_7);
                                                --var6_7;
                                                "".length();
                                                if (null != null) {
                                                    return;
                                                }
                                            } else {
                                                var11_36 = ec.a(var5_6);
                                                if (ey.a((Object)var11_36)) {
                                                    dl.h().a(var11_36, var8_15);
                                                }
                                            }
                                            ++var6_7;
                                            "".length();
                                            if (-"   ".length() <= 0) continue;
                                            return;
                                        }
                                        var4_5 = ec.u();
                                        ey.bi = System.currentTimeMillis();
                                    }
                                    if (ey.i(de.i, ey.bp[21]) && ey.l((int)de.e(de.i)) && (ey.o((int)ec.k(ey.bp[39])) && !ey.l((int)ec.k(ey.bp[40])) || ey.l((int)ec.n) && ey.l((int)ec.cz) && ey.g(ec.l, ey.bp[7]) && ey.h(var4_5, ey.bp[0]) && ey.o(ey.j(ec.l)))) {
                                        de.g(ey.bp[2]);
                                        de.g();
                                    }
                                    if (ey.f(var4_5, ey.bp[13]) && ey.o(ey.u instanceof ba) && ey.l((int)ec.ew) && ey.h(var3_3.bu, ey.bp[22]) && ey.m(var4_5) && ey.l((int)ey.h())) {
                                        var14_40 = new Vector();
                                        var8_16 = ey.bp[2];
                                        block18: while (ey.f(var8_16, ec.cq - ey.bp[0])) {
                                            var14_40.removeAllElements();
                                            var7_8 = ey.bp[2];
                                            while (ey.f(var7_8, var3_3.en.length)) {
                                                var15_42 = var3_3.en[var7_8];
                                                if (ey.a((Object)var15_42) && ey.d(var15_42.m.d, var8_16)) {
                                                    var14_40.addElement(var15_42);
                                                }
                                                ++var7_8;
                                                "".length();
                                                if (((122 + 120 - 172 + 91 ^ 120 + 101 - 200 + 146) & (186 ^ 149 ^ (122 ^ 83) ^ -" ".length())) >= 0) continue;
                                                return;
                                            }
                                            while (ey.e(var14_40.size(), ey.bp[13])) {
                                                var7_8 = ey.bp[0];
                                                var5_6 = var8_16;
                                                while (ey.f(var5_6, ec.cq - ey.bp[0]) && ey.g(ap.cu[var5_6], var3_3.cg) && ey.g(var7_8 << ey.bp[11], var14_40.size()) && ey.f(var7_8, ey.bp[18])) {
                                                    var7_8 <<= ey.bp[11];
                                                    ++var5_6;
                                                    "".length();
                                                    if (" ".length() != 0) continue;
                                                    return;
                                                }
                                                if (ey.d(var7_8, ey.bp[0])) {
                                                    "".length();
                                                    if (" ".length() == " ".length()) break block18;
                                                    return;
                                                }
                                                ap.s().c(ey.bp[41]);
                                                ap.af = new el[ey.bp[37]];
                                                var10_31 = ey.bp[2];
                                                while (ey.f(var10_31, var7_8)) {
                                                    ap.af[var10_31] = var15_42 = (el)var14_40.elementAt(ey.bp[2]);
                                                    var3_3.en[var15_42.n] = null;
                                                    var14_40.removeElementAt(ey.bp[2]);
                                                    ++var10_31;
                                                    "".length();
                                                    if (null == null) continue;
                                                    return;
                                                }
                                                dl.h().a(ap.af);
                                                bo.u();
                                                if (!ey.a((Object)ap.af[ey.bp[2]])) continue;
                                                var3_3.en[ap.af[ey.bp[2]].n] = ap.af[ey.bp[2]];
                                                "".length();
                                                if ((105 ^ 91 ^ (102 ^ 80)) >= ("  ".length() & ("  ".length() ^ -" ".length()))) continue;
                                                return;
                                            }
                                            ++var8_16;
                                            "".length();
                                            if (-" ".length() != "   ".length()) continue;
                                            return;
                                        }
                                        ap.s().c(ey.bp[13]);
                                        var5_6 = ey.bp[2];
                                        var8_16 = ec.t();
                                        while (ey.f(var5_6, var3_3.en.length)) {
                                            var11_37 = var3_3.en[var5_6];
                                            if (ey.a((Object)var11_37) && ey.d(var11_37.m.d, ec.cq - ey.bp[0]) && ey.m(var8_16)) {
                                                dl.h().ag(var11_37.n);
                                                --var8_16;
                                            }
                                            ++var5_6;
                                            "".length();
                                            if (-" ".length() < 0) continue;
                                            return;
                                        }
                                        dl.h().aa();
                                        bo.ak();
                                        "".length();
                                        Thread.sleep(1000L);
                                        cf.d().a();
                                    }
                                    if (!ey.o((int)de.b(de.i)) || ey.l((int)de.j(de.i))) {
                                        if ((!ey.o((int)ec.cz) || ey.l(ey.u instanceof ba)) && ey.h(var4_5, ey.bp[0]) && ey.h(var3_3.bu, ey.bp[12])) {
                                            if (ey.l(ey.u instanceof ba)) {
                                                if (ey.e(var3_3.bu, ey.bp[22])) {
                                                    v5 = ey.bp[9];
                                                    "".length();
                                                    if ("  ".length() <= " ".length()) {
                                                        return;
                                                    }
                                                } else {
                                                    v5 = ey.bp[0];
                                                    "".length();
                                                    if ((211 ^ 166 ^ (79 ^ 62)) == 0) {
                                                        return;
                                                    }
                                                }
                                            } else {
                                                v5 = var8_17 = ec.l;
                                            }
                                            if (ey.g(v5, ey.bp[7]) && ey.o(ey.j(var8_17))) {
                                                var5_6 = ey.bp[11];
                                                var6_7 = ey.bp[2];
                                                while (ey.f(var6_7, var3_3.dy.size())) {
                                                    if (ey.o(((au)var3_3.dy.elementAt((int)var6_7)).a.c)) {
                                                        --var5_6;
                                                        "".length();
                                                        if (((51 ^ 26) & ~(27 ^ 50)) >= 0) break;
                                                        return;
                                                    }
                                                    ++var6_7;
                                                    "".length();
                                                    if ((10 ^ 6 ^ (73 ^ 65)) >= (192 ^ 133 ^ (30 ^ 95))) continue;
                                                    return;
                                                }
                                                ap.b(ey.bp[13], ey.bp[2], ey.bp[2]);
                                                if (ey.d(var8_17, ey.bp[7])) {
                                                    dl.h().b(ey.bp[22], ey.bp[31], var5_6);
                                                    "".length();
                                                    if ((157 ^ 153) < 0) {
                                                        return;
                                                    }
                                                } else {
                                                    dl.h().b(ey.bp[22], var8_17 / ey.bp[9], var5_6);
                                                }
                                                bo.aa();
                                            }
                                        }
                                        if (ey.d(de.i, ey.bp[21]) && ey.h(var4_5, ey.bp[0]) && ey.o((int)ec.k(ey.bp[39])) && ey.o((int)ec.k(ey.bp[40]))) {
                                            ap.b(ey.bp[13], ey.bp[2], ey.bp[2]);
                                            dl.h().b(ey.bp[22], ey.bp[29], ey.bp[0]);
                                            bo.aa();
                                            ++var4_5;
                                        }
                                    }
                                }
                                if (ey.l((int)ey.bb)) {
                                    var6_7 = ey.bp[8];
                                    var8_18 = null;
                                    var7_8 = ey.bp[2];
                                    do {
                                        if (ey.e(var7_8, ap.ff.size())) {
                                            if (ey.a(var8_18)) {
                                                dl.h().r(var8_18.l);
                                                ax.a(50L);
                                                "".length();
                                                if (-" ".length() != -" ".length()) {
                                                    return;
                                                }
                                            }
                                            break block131;
                                        }
                                        var9_25 = (fm)ap.ff.elementAt(var7_8);
                                        var5_6 = fo.a(var3_3.s, var3_3.fc, var9_25.h, var9_25.a);
                                        if ((!ey.i(var6_7, ey.bp[1]) || ey.f(var5_6, var6_7)) && (!ey.o((int)ey.a(var9_25.k)) || ey.d(var3_3.dk.c, ey.bp[0]) && ey.d(var9_25.k.d, ey.bp[42])) && (!ey.g(var4_5, ey.bp[11]) || !ey.i(var9_25.k.b, ey.bp[43]) || ey.l((int)var9_25.k.g) && ey.l((int)ec.k(var9_25.k.d)))) {
                                            var6_7 = var5_6;
                                            var8_18 = var9_25;
                                        }
                                        ++var7_8;
                                        "".length();
                                    } while (-" ".length() == -" ".length());
                                    return;
                                }
                            }
                            if (ey.l((int)ey.k) && ey.n(ey.b(var3_3.bc * 100L / ap.al[var3_3.aa], 95L))) {
                                bo.ai();
                                ey.u = null;
                                dp.g().c();
                                em.f.g();
                            }
                            if (ey.m(ey.ar.size())) {
                                ey.f();
                            }
                            if (!ey.m(ey.b(System.currentTimeMillis() - ey.bw, 2L))) break block132;
                            var6_7 = ey.bp[2];
                            do {
                                block133: {
                                    if (ey.e(var6_7, ey.bh.size())) {
                                        ey.bw = System.currentTimeMillis();
                                        "".length();
                                        if (-(162 ^ 172 ^ (60 ^ 55)) >= 0) {
                                            return;
                                        }
                                        break block132;
                                    }
                                    var5_6 = (Integer)ey.bh.elementAt(var6_7);
                                    var8_19 = t.b((short)var5_6);
                                    if (!ey.l((int)ec.k(var5_6))) ** GOTO lbl-1000
                                    var7_8 = ey.bp[2];
                                    while (ey.f(var7_8, var3_3.dy.size())) {
                                        var9_26 = (au)var3_3.dy.elementAt(var7_8);
                                        if (ey.a(var9_26) && ey.d(var9_26.a.a, var8_19.j)) {
                                            "".length();
                                            if (null != null) {
                                                return;
                                            }
                                            break block133;
                                        }
                                        ++var7_8;
                                        "".length();
                                        if ((212 ^ 136 ^ (225 ^ 185)) != "   ".length()) continue;
                                        return;
                                    }
                                    var7_8 = ec.c(var5_6);
                                    if (ey.n(var7_8)) {
                                        dl.h().t(var7_8);
                                        "".length();
                                        if (null != null) {
                                            return;
                                        }
                                    } else lbl-1000:
                                    // 2 sources

                                    {
                                        ey.bh.removeElementAt(var6_7);
                                        --var6_7;
                                    }
                                }
                                ++var6_7;
                                "".length();
                            } while (null == null);
                            return;
                        }
                        if (!ey.m(ey.b(System.currentTimeMillis() - ey.bq, 2000L))) break block135;
                        var6_7 = ey.bp[2];
                        while (ey.f(var6_7, ey.bu.size())) {
                            block134: {
                                var5_6 = (Integer)ey.bu.elementAt(var6_7);
                                var8_20 = t.b((short)var5_6);
                                if (!ey.l((int)ec.k(var5_6))) ** GOTO lbl-1000
                                var7_8 = ey.bp[2];
                                while (ey.f(var7_8, var3_3.dy.size())) {
                                    var9_27 = (au)var3_3.dy.elementAt(var7_8);
                                    if (ey.a(var9_27) && ey.d(var9_27.a.a, var8_20.j)) {
                                        "".length();
                                        if ((208 ^ 196 ^ (25 ^ 9)) <= 0) {
                                            return;
                                        }
                                        break block134;
                                    }
                                    ++var7_8;
                                    "".length();
                                    if (-" ".length() < "  ".length()) continue;
                                    return;
                                }
                                var7_8 = ec.c(var5_6);
                                if (ey.n(var7_8)) {
                                    dl.h().u(var7_8);
                                    "".length();
                                    if ("   ".length() <= 0) {
                                        return;
                                    }
                                } else lbl-1000:
                                // 2 sources

                                {
                                    ey.bu.removeElementAt(var6_7);
                                    --var6_7;
                                }
                            }
                            ++var6_7;
                            "".length();
                            if (" ".length() != 0) continue;
                            return;
                        }
                        ey.bq = System.currentTimeMillis();
                    }
                    "".length();
                }
                catch (Exception var3_4) {
                    // empty catch block
                }
                if (-(228 ^ 138 ^ (234 ^ 128)) >= 0) {
                    return;
                }
                if (ey.l((int)ec.n().cn)) {
                    bo.af();
                }
                if (ey.p(ey.b(var1_1 = System.currentTimeMillis() - var1_1, 100L))) {
                    v6 = 100L - var1_1;
                    "".length();
                    if (-(185 ^ 189) >= 0) {
                        return;
                    }
                } else {
                    v6 = 0L;
                }
                ax.a(v6);
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
        }
        catch (Exception var1_2) {
            // empty catch block
        }
        if ("   ".length() < -" ".length()) {
            return;
        }
    }

    private static boolean g(int n2, int n3) {
        return n2 <= n3;
    }

    public static void g() {
        bd.c();
        ey.a(bd);
    }

    public static String[] b(String string, String string2) {
        int n2 = bp[2];
        int n3 = string2.length();
        int n4 = string.indexOf(string2, bp[2]);
        while (ey.i(n4, bp[1])) {
            n4 += n3;
            n4 = string.indexOf(string2, n4);
            ++n2;
            "".length();
            if ("   ".length() >= ((136 + 58 - 62 + 33 ^ 105 + 37 - 16 + 16) & (7 ^ 0x72 ^ (0xEE ^ 0xB0) ^ -" ".length()))) continue;
            return null;
        }
        String[] stringArray = new String[n2 + bp[0]];
        n4 = string.indexOf(string2);
        int n5 = bp[2];
        int n6 = bp[2];
        while (ey.i(n4, bp[1])) {
            stringArray[n6] = string.substring(n5, n4);
            n5 = n4 + n3;
            n4 = string.indexOf(string2, n5);
            ++n6;
            "".length();
            if (-" ".length() != ((0x39 ^ 4) & ~(0x42 ^ 0x7F))) continue;
            return null;
        }
        stringArray[n6] = string.substring(n5, string.length());
        return stringArray;
    }

    public static boolean h() {
        int n2 = bp[2];
        while (ey.f(n2, ec.cq - bp[0])) {
            if (ey.e(ec.l(n2), bp[13])) {
                return bp[0];
            }
            ++n2;
            "".length();
            if (-" ".length() != " ".length()) continue;
            return ((0x8D ^ 0xAA) & ~(0x11 ^ 0x36)) != 0;
        }
        return bp[2];
    }

    public static void c(int n2) {
        bu.removeElement(new Integer(n2));
        "".length();
    }

    private static boolean l(int n2) {
        return n2 != 0;
    }

    public static void b(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, e.length)) {
            if (ey.d(e[n2], s2)) {
                ey.e[n2] = bp[1];
            }
            ++n2;
            "".length();
            if ("  ".length() <= "  ".length()) continue;
            return;
        }
    }

    public static void c(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, m.length)) {
            if (ey.d(m[n2], s2)) {
                return;
            }
            ++n2;
            "".length();
            if (null == null) continue;
            return;
        }
        n2 = bp[1];
        int n3 = bp[2];
        while (ey.f(n3, m.length)) {
            if (ey.p(m[n3])) {
                n2 = n3;
                "".length();
                if (((0xE0 ^ 0xC2 ^ (0x36 ^ 0x48)) & (187 + 133 - 185 + 101 ^ 98 + 30 - 9 + 57 ^ -" ".length())) != -" ".length()) break;
                return;
            }
            ++n3;
            "".length();
            if (((119 + 35 - 124 + 188 ^ 43 + 98 - -5 + 3) & (0x45 ^ 0x3E ^ (0x48 ^ 0x7C) ^ -" ".length())) < "  ".length()) continue;
            return;
        }
        if (ey.d(n2, bp[1])) {
            n2 = m.length;
            short[] sArray = new short[m.length + bp[9]];
            System.arraycopy(sArray, bp[2], m, bp[2], m.length);
            int n4 = m.length;
            while (ey.f(n4, sArray.length)) {
                sArray[n4] = bp[1];
                ++n4;
                "".length();
                if ("  ".length() >= -" ".length()) continue;
                return;
            }
            m = sArray;
        }
        ey.m[n2] = s2;
    }

    private static boolean m(int n2) {
        return n2 > 0;
    }

    public static boolean d(int n2) {
        return bu.contains(new Integer(n2));
    }

    public static boolean a(bf bf2) {
        int n2;
        if (ey.l(u instanceof di)) {
            int n3;
            int n4;
            int n5;
            if (ey.d(bf2.b, bp[43])) {
                return bp[0];
            }
            if ((!ey.i(bf2.b, bp[18]) || ey.d(bf2.b, bp[16])) && ey.d(bf2.h, bp[9])) {
                return bp[0];
            }
            ec ec2 = ec.n();
            if (ey.g(ec.u(), bp[29])) {
                return bp[2];
            }
            if ((!ey.e(ec2.bu, bp[44]) || ey.d(ec2.bu, bp[44]) && ey.a((Object)ec2.ff[bp[0]]) && ey.f(ec2.ff[ey.bp[0]].y, bp[11])) && ey.d(bf2.b, bp[45]) && ey.m(bf2.d)) {
                return bp[0];
            }
            if (ey.d(ec2.ch, bp[0])) {
                n5 = bp[46];
                "".length();
                if (null != null) {
                    return ((0xF3 ^ 0xB8 ^ (0xBC ^ 0xA4)) & (0x76 ^ 0x55 ^ (0x64 ^ 0x14) ^ -" ".length())) != 0;
                }
            } else {
                n5 = n4 = bp[47];
            }
            if (ey.g(ec2.bu, bp[41]) && (ey.d(bf2.d, bp[48]) && !ey.l(ec.k(bp[48]) ? 1 : 0) || ey.d(bf2.d, n4) && ey.o(ec.k(n4) ? 1 : 0))) {
                n3 = bp[0];
                "".length();
                if ("  ".length() == ((0xD7 ^ 0xC0) & ~(0x35 ^ 0x22))) {
                    return ((0xA6 ^ 0xAE) & ~(0x6D ^ 0x65)) != 0;
                }
            } else {
                n3 = bp[2];
            }
            return n3 != 0;
        }
        if (ey.l(u instanceof ba)) {
            boolean bl2;
            if (ey.d(bf2.b, bp[43])) {
                bl2 = bp[0];
                "".length();
                if (" ".length() >= (0xE7 ^ 0x96 ^ (0xCA ^ 0xBF))) {
                    return ((0x29 ^ 0x33 ^ "  ".length()) & (107 + 30 - 10 + 5 ^ 9 + 74 - 24 + 97 ^ -" ".length())) != 0;
                }
            } else {
                bl2 = bp[2];
            }
            return bl2;
        }
        if (ey.d(bf2.b, bp[43])) {
            return ec.ab;
        }
        if (ey.i(bf2.b, bp[18]) && ey.i(bf2.b, bp[16])) {
            if (ey.d(bf2.b, bp[45])) {
                int n6;
                if (ey.l(ec.ci ? 1 : 0) && ey.e(bf2.d, ec.ev - bp[0])) {
                    n6 = bp[0];
                    "".length();
                    if (null != null) {
                        return ((0xC1 ^ 0xBC ^ (0x31 ^ 0x79)) & (90 + 21 - -2 + 42 ^ 6 + 15 - -118 + 35 ^ -" ".length())) != 0;
                    }
                } else {
                    n6 = bp[2];
                }
                return n6 != 0;
            }
            if (ey.l(bf2.d() ? 1 : 0)) {
                int n7;
                if ((!ey.o(ec.g ? 1 : 0) || ey.l(u instanceof ak)) && ey.e(bf2.h, ec.z)) {
                    n7 = bp[0];
                    "".length();
                    if ("  ".length() <= 0) {
                        return ((0x74 ^ 0x17) & ~(0x3B ^ 0x58)) != 0;
                    }
                } else {
                    n7 = bp[2];
                }
                return n7 != 0;
            }
            if (ey.l(bf2.b() ? 1 : 0)) {
                return ec.bg;
            }
            if (ey.d(bf2.b, bp[49])) {
                if (!ey.o(bf2.c.startsWith("V\u1eadt ph\u1ea9m s\u1ef1 ki\u1ec7n") ? 1 : 0) || ey.l(bf2.c.startsWith("V\u1eadt ph\u1ea9m S\u1ef1 ki\u1ec7n") ? 1 : 0)) {
                    return ec.dt;
                }
                if (ey.l(bf2.e.startsWith("S\u00e1ch v\u00f5 c\u00f4ng") ? 1 : 0)) {
                    return ec.bd;
                }
                if (ey.l(de.e(de.i) ? 1 : 0) && ey.d(bf2.d, bp[50])) {
                    return bp[2];
                }
            }
            int n8 = bp[2];
            while (ey.f(n8, m.length)) {
                if (ey.m(m[n8]) && ey.d(bf2.d, m[n8])) {
                    return bp[0];
                }
                ++n8;
                "".length();
                if (null == null) continue;
                return ((13 + 58 - -61 + 73 ^ 102 + 33 - 88 + 85) & (0xEB ^ 0xA7 ^ (0x6D ^ 0x68) ^ -" ".length())) != 0;
            }
            return ec.ee;
        }
        if (ey.l(ec.v ? 1 : 0) && ey.e(bf2.h, ec.ah)) {
            n2 = bp[0];
            "".length();
            if ("  ".length() < 0) {
                return ((0x87 ^ 0xAE) & ~(0x42 ^ 0x6B)) != 0;
            }
        } else {
            n2 = bp[2];
        }
        return n2 != 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static void i() {
        bo.ai();
        u = ey.u.q;
    }

    public static void d(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, m.length)) {
            if (ey.d(m[n2], s2)) {
                ey.m[n2] = bp[1];
            }
            ++n2;
            "".length();
            if (" ".length() != 0) continue;
            return;
        }
    }

    private static boolean n(int n2) {
        return n2 >= 0;
    }

    public static void e(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, c.length)) {
            if (ey.d(c[n2], s2)) {
                return;
            }
            ++n2;
            "".length();
            if ((0x3D ^ 0x14 ^ (0x9E ^ 0xB2)) > 0) continue;
            return;
        }
        n2 = bp[1];
        int n3 = bp[2];
        while (ey.f(n3, c.length)) {
            if (ey.p(c[n3])) {
                n2 = n3;
                "".length();
                if (" ".length() != "  ".length()) break;
                return;
            }
            ++n3;
            "".length();
            if (-" ".length() == -" ".length()) continue;
            return;
        }
        if (ey.d(n2, bp[1])) {
            n2 = c.length;
            short[] sArray = new short[c.length + bp[9]];
            System.arraycopy(sArray, bp[2], c, bp[2], c.length);
            int n4 = c.length;
            while (ey.f(n4, sArray.length)) {
                sArray[n4] = bp[1];
                ++n4;
                "".length();
                if ("  ".length() > " ".length()) continue;
                return;
            }
            c = sArray;
        }
        ey.c[n2] = s2;
    }

    public static void f(el el2) {
        ar.removeElement(el2);
        "".length();
    }

    public static void e(int n2) {
        Integer n3 = new Integer(n2);
        if (ey.o(bu.contains(n3) ? 1 : 0)) {
            bu.addElement(n3);
        }
    }

    public static ad j() {
        ad ad2 = new ad();
        int n2 = bp[2];
        while (ey.f(n2, au.size())) {
            ad2.addElement(n2 + ". " + (String)au.elementAt(n2));
            ++n2;
            "".length();
            if (null == null) continue;
            return null;
        }
        return ad2;
    }

    private static void w() {
        String[] stringArray;
        String string;
        int n2;
        int n3;
        int n4;
        int n5;
        cc = new ab();
        fa.e = bp[0];
        fa.h = bp[1];
        fa.c = new dj();
        if (ey.d(an.d("hsl"), bp[0])) {
            n5 = bp[0];
            "".length();
            if ("   ".length() < 0) {
                return;
            }
        } else {
            fa.b = bp[2];
            n5 = fa.b ? 1 : 0;
        }
        if (ey.d(an.d("glv"), bp[0])) {
            n4 = bp[0];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            k = bp[2];
            n4 = k ? 1 : 0;
        }
        if (ey.d(an.d("adv"), bp[0])) {
            n3 = bp[0];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            fa.g = bp[2];
            n3 = fa.g ? 1 : 0;
        }
        if (ey.d(an.d("locdo"), bp[0])) {
            n2 = bp[0];
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        } else {
            o.b = bp[2];
            n2 = o.b ? 1 : 0;
        }
        if (ey.a((Object)(string = an.a("nextmap")))) {
            try {
                fa.e = Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            "".length();
            if ("  ".length() == 0) {
                return;
            }
        }
        if (ey.a((Object)(string = an.a("background")))) {
            try {
                fa.h = Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            "".length();
            if (null != null) {
                return;
            }
        }
        aa = new ey();
        bo = bp[2];
        ca = new o();
        aj = new ak();
        bd = new p();
        ao = new df();
        cd = new cb();
        by = new fg();
        a = new fd();
        bk = new aw();
        ak = new du();
        bz = new cq();
        ce = new ek();
        bs = new co();
        bl = new dd();
        bt = 0L;
        bn = 0L;
        m = new short[bp[3]];
        c = new short[bp[3]];
        e = new short[bp[3]];
        ae = bp[1];
        be = bp[1];
        o = bp[1];
        d = bp[4];
        h = bp[5];
        bc = bp[1];
        bb = bp[2];
        av = bp[2];
        az = new ad();
        ar = new ad();
        an = new ad();
        w = bp[2];
        ac = new int[bp[2]];
        am = bp[2];
        n = bp[6];
        bq = 0L;
        bu = new ad();
        bw = 0L;
        bh = new ad();
        cb = new ad();
        bi = 0L;
        y = new ad();
        aq = new ad();
        int n6 = bp[2];
        while (ey.f(n6, m.length)) {
            ey.m[n6] = bp[1];
            ++n6;
            "".length();
            if (((0x8D ^ 0x95) & ~(0x1D ^ 5)) <= ((0x13 ^ 0x44) & ~(0x6C ^ 0x3B))) continue;
            return;
        }
        n6 = bp[2];
        while (ey.f(n6, c.length)) {
            ey.c[n6] = bp[1];
            ++n6;
            "".length();
            if (null == null) continue;
            return;
        }
        n6 = bp[2];
        while (ey.f(n6, e.length)) {
            ey.e[n6] = bp[1];
            ++n6;
            "".length();
            if ("  ".length() != " ".length()) continue;
            return;
        }
        v = null;
        q = new ad();
        au = new ad();
        try {
            stringArray = new ByteArrayInputStream(an.b("V6Group"));
            DataInputStream dataInputStream = new DataInputStream((InputStream)stringArray);
            v = dataInputStream.readUTF();
            if (ey.l(v.equals("") ? 1 : 0)) {
                v = null;
            }
            byte by2 = dataInputStream.readByte();
            int n7 = bp[2];
            while (ey.f(n7, by2)) {
                q.addElement(dataInputStream.readUTF());
                ++n7;
                "".length();
                if ((0xBF ^ 0xC2 ^ (0x3D ^ 0x44)) >= (0xBC ^ 0xC0 ^ (0x7A ^ 2))) continue;
                return;
            }
            int n8 = dataInputStream.readInt();
            n7 = bp[2];
            while (ey.f(n7, n8)) {
                au.addElement(dataInputStream.readUTF());
                ++n7;
                "".length();
                if (" ".length() > ((0x74 ^ 0x61) & ~(6 ^ 0x13))) continue;
                return;
            }
            dataInputStream.close();
            stringArray.close();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((0x50 ^ 0x54) < "   ".length()) {
            return;
        }
        try {
            bg = ey.b(ey.c("text.txt"), "\n");
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            bg = new String[bp[2]];
        }
        if ("   ".length() == 0) {
            return;
        }
        at = 0L;
        s = 0L;
        t = bp[2];
        bf = bp[2];
        ay = bp[2];
        x = bp[5];
        p = bp[2];
        ah = bp[7];
        z = bp[2];
        ab = bp[7];
        ap = bp[2];
        aw = bp[8];
        b = bp[9];
        g = bp[10];
        l = bp[1];
        ai = bp[1];
        r = bp[1];
        ba = 50L;
        string = an.a("chipAutopk");
        if (ey.a((Object)string)) {
            stringArray = ey.b(string, ";");
            try {
                b = Integer.parseInt(stringArray[bp[2]]);
                g = Integer.parseInt(stringArray[bp[0]]);
                l = Integer.parseInt(stringArray[bp[11]]);
                ai = Integer.parseInt(stringArray[bp[12]]);
                r = Integer.parseInt(stringArray[bp[13]]);
                ba = Long.parseLong(stringArray[bp[5]]);
                return;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
    }

    private static void x() {
        cd.c();
        ey.a(cd);
    }

    public static void f(short s2) {
        int n2 = bp[2];
        while (ey.f(n2, e.length)) {
            if (ey.d(e[n2], s2)) {
                return;
            }
            ++n2;
            "".length();
            if (null == null) continue;
            return;
        }
        n2 = bp[1];
        int n3 = bp[2];
        while (ey.f(n3, e.length)) {
            if (ey.p(e[n3])) {
                n2 = n3;
                "".length();
                if (-"  ".length() <= 0) break;
                return;
            }
            ++n3;
            "".length();
            if ((55 + 68 - 90 + 130 ^ 88 + 112 - 34 + 1) >= 0) continue;
            return;
        }
        if (ey.d(n2, bp[1])) {
            n2 = e.length;
            short[] sArray = new short[e.length + bp[9]];
            System.arraycopy(sArray, bp[2], e, bp[2], e.length);
            int n4 = e.length;
            while (ey.f(n4, sArray.length)) {
                sArray[n4] = bp[1];
                ++n4;
                "".length();
                if (" ".length() != "   ".length()) continue;
                return;
            }
            e = sArray;
        }
        ey.e[n2] = s2;
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static boolean g(String string) {
        block374: {
            block375: {
                block376: {
                    block377: {
                        block378: {
                            int n2;
                            block379: {
                                block381: {
                                    block386: {
                                        int n3;
                                        int n4;
                                        block384: {
                                            block382: {
                                                String string2;
                                                block380: {
                                                    n2 = bp[2];
                                                    StringBuffer stringBuffer = new StringBuffer();
                                                    StringBuffer stringBuffer2 = new StringBuffer();
                                                    int n5 = bp[2];
                                                    block14: while (ey.f(n5, string.length())) {
                                                        char c2 = string.charAt(n5);
                                                        if (ey.e(c2, bp[52]) && !ey.h(c2, bp[53]) || ey.d(c2, bp[54])) {
                                                            while (ey.f(n5, string.length()) && ey.e(c2 = string.charAt(n5), bp[52])) {
                                                                if (ey.h(c2, bp[53])) {
                                                                    "".length();
                                                                    if ("  ".length() > 0) break block14;
                                                                    return ((0x6C ^ 0x1A ^ (0x8A ^ 0xB7)) & (0xA8 ^ 0x82 ^ (0x67 ^ 6) ^ -" ".length())) != 0;
                                                                }
                                                                stringBuffer2.append(c2);
                                                                "".length();
                                                                ++n5;
                                                                "".length();
                                                                if (-"  ".length() <= 0) continue;
                                                                return ((0xED ^ 0xAD) & ~(0x20 ^ 0x60)) != 0;
                                                            }
                                                            break;
                                                        }
                                                        stringBuffer.append(c2);
                                                        "".length();
                                                        ++n5;
                                                        "".length();
                                                        if (" ".length() >= 0) continue;
                                                        return ((0x7E ^ 0x4C ^ (0x2A ^ 0x41)) & (188 + 201 - 353 + 180 ^ 104 + 28 - 31 + 28 ^ -" ".length())) != 0;
                                                    }
                                                    string2 = stringBuffer.toString().toLowerCase();
                                                    if (ey.m(stringBuffer2.length())) {
                                                        try {
                                                            n2 = Integer.parseInt(stringBuffer2.toString());
                                                            "".length();
                                                        }
                                                        catch (Exception exception) {
                                                            // empty catch block
                                                        }
                                                        if ("   ".length() == 0) {
                                                            return ((0xB ^ 0x3A ^ (0x71 ^ 0x7B)) & (0x3E ^ 0x48 ^ (0x2D ^ 0x60) ^ -" ".length())) != 0;
                                                        }
                                                    }
                                                    if (ey.l(string2.equals("uppk") ? 1 : 0)) {
                                                        new bi().a();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("adpk") ? 1 : 0)) {
                                                        if (ey.l(u instanceof dd)) {
                                                            ap.a("T\u1eaft auto \u0111\u00e1nh ai b\u1eadt pk");
                                                            ey.q();
                                                            "".length();
                                                            if (null != null) {
                                                                return ((0xCE ^ 0x97) & ~(0x9F ^ 0xC6)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt auto \u0111\u00e1nh ai b\u1eadt pk");
                                                            bl.e();
                                                            ey.a(bl);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ktg") ? 1 : 0)) {
                                                        String string3;
                                                        int n6;
                                                        if (ey.o(ei.a ? 1 : 0)) {
                                                            n6 = bp[0];
                                                            "".length();
                                                            if (null != null) {
                                                                return ((0x5E ^ 0x3F) & ~(0x6C ^ 0xD)) != 0;
                                                            }
                                                        } else {
                                                            n6 = bp[2];
                                                        }
                                                        ei.a = n6;
                                                        new Thread(new ei()).start();
                                                        if (ey.l(ei.a ? 1 : 0)) {
                                                            string3 = "Auto Ch\u00e1t KTG : B\u1eadt";
                                                            "".length();
                                                            if ("   ".length() != "   ".length()) {
                                                                return ((0x5B ^ 0x78) & ~(0x64 ^ 0x47)) != 0;
                                                            }
                                                        } else {
                                                            string3 = "Auto Ch\u00e1t KTG : T\u1eaft";
                                                        }
                                                        ap.a(string3);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("acpk") ? 1 : 0)) {
                                                        if (ey.l(u instanceof co)) {
                                                            ap.a("T\u1eaft auto ch\u1edd pk");
                                                            ey.q();
                                                            "".length();
                                                            if (-" ".length() > "  ".length()) {
                                                                return ((211 + 31 - 226 + 224 ^ 158 + 111 - 243 + 137) & (0x97 ^ 0xB3 ^ (0x26 ^ 0x51) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt auto ch\u1edd pk");
                                                            bs.e();
                                                            ey.a(bs);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("atpk") ? 1 : 0)) {
                                                        if (ey.l(u instanceof ek)) {
                                                            ap.a("T\u1eaft auto pk \u00e2m kinh nghi\u1ec7m");
                                                            ey.q();
                                                            "".length();
                                                            if (" ".length() <= 0) {
                                                                return ((0xD6 ^ 0x96) & ~(0x4C ^ 0xC)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt auto pk \u00e2m kinh nghi\u1ec7m");
                                                            if (ey.a((Object)u)) {
                                                                ek.a = u;
                                                            }
                                                            k = bp[0];
                                                            ey.t();
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("s") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            ap.a("Ch\u1ea1y \u0111i \u0111ou v\u1edbi t\u1ed1c \u0111\u1ed9 0?");
                                                            "".length();
                                                            if (((0xA9 ^ 0x82 ^ " ".length()) & (0x3B ^ 0x77 ^ (0xD5 ^ 0xB3) ^ -" ".length())) == -" ".length()) {
                                                                return ((176 + 147 - 202 + 97 ^ 82 + 37 - 75 + 86) & (0x45 ^ 0x66 ^ (0xFC ^ 0x87) ^ -" ".length())) != 0;
                                                            }
                                                        } else if (ey.h(n2, bp[8])) {
                                                            ap.a("T\u1ed1c gi\u00e0y n\u00ean \u0111\u1ec3 <= 100 \u0111\u1ec3 ko b\u1ecb gi\u1eadt!");
                                                            "".length();
                                                            if (null != null) {
                                                                return ((1 ^ 0x37) & ~(0x4C ^ 0x7A)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("Fake t\u1ed1c ch\u1ea1y ".concat(String.valueOf(n2)));
                                                            x = n2;
                                                            ay = bp[0];
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("rs") ? 1 : 0)) {
                                                        ap.a("Reset t\u1ed1c ch\u1ea1y");
                                                        ay = bp[2];
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("n") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[8];
                                                        }
                                                        ap.a("Fake t\u1ea7m ngang ".concat(String.valueOf(n2)));
                                                        p = bp[0];
                                                        ah = n2;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("c") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[8];
                                                        }
                                                        ap.a("Fake t\u1ea7m cao ".concat(String.valueOf(n2)));
                                                        z = bp[0];
                                                        ab = n2;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("m") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[0];
                                                        }
                                                        ap.a("Fake lan ".concat(String.valueOf(n2)));
                                                        ap = bp[0];
                                                        aw = n2;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("rsk") ? 1 : 0)) {
                                                        ap.a("Reset fake t\u1ea7m lan skill");
                                                        ap = bp[2];
                                                        p = bp[2];
                                                        z = bp[2];
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("nhan") ? 1 : 0)) {
                                                        if (ey.i(de.i, eq.b)) {
                                                            ap.a("H\u00e3y V\u1ec1 " + de.d[eq.b]);
                                                            "".length();
                                                            if (-(0x27 ^ 0x3D ^ (0x30 ^ 0x2E)) >= 0) {
                                                                return ((151 + 4 - 55 + 66 ^ 13 + 106 - 112 + 181) & (0x52 ^ 0x39 ^ (0xF5 ^ 0x84) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            if (ey.i(de.ab, eq.ah)) {
                                                                ap.s();
                                                                "".length();
                                                                ap.d((int)eq.ah);
                                                                bl.a("Ch\u00e1t l\u1ea1i: nhan \u0111\u1ec3 b\u1eadt auto nh\u1eadn \u0111\u1ed3!", bp[7], doa.j);
                                                            }
                                                            if (ey.d(de.ab, eq.ah)) {
                                                                ey.a(new eq());
                                                            }
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("zone") ? 1 : 0)) {
                                                        String string4;
                                                        int n7;
                                                        if (ey.o(aw.c ? 1 : 0)) {
                                                            n7 = bp[0];
                                                            "".length();
                                                            if (" ".length() <= 0) {
                                                                return ((0x16 ^ 0x40 ^ (0x18 ^ 0x49)) & (154 + 166 - 297 + 153 ^ 52 + 73 - 96 + 154 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            n7 = bp[2];
                                                        }
                                                        if (ey.l((aw.c = n7) ? 1 : 0)) {
                                                            string4 = "B\u1eadt TSMT";
                                                            "".length();
                                                            if ("  ".length() <= " ".length()) {
                                                                return ((0x41 ^ 0x22) & ~(0x51 ^ 0x32)) != 0;
                                                            }
                                                        } else {
                                                            string4 = "B\u1eadt TSMKT";
                                                        }
                                                        ap.a(string4);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("show") ? 1 : 0)) {
                                                        String string5;
                                                        int n8;
                                                        if (ey.o(al ? 1 : 0)) {
                                                            n8 = bp[0];
                                                            "".length();
                                                            if ("  ".length() < 0) {
                                                                return ((3 ^ 0x1F ^ (0x19 ^ 0x2F)) & ("   ".length() ^ (0x44 ^ 0x6D) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            n8 = bp[2];
                                                        }
                                                        if (ey.l((al = n8) ? 1 : 0)) {
                                                            string5 = "B\u1eadt Show";
                                                            "".length();
                                                            if (((0xFE ^ 0xAB ^ (9 ^ 0x61)) & (0x16 ^ 0x5C ^ (0x3A ^ 0x4D) ^ -" ".length())) != 0) {
                                                                return ((22 + 82 - 80 + 108 ^ 8 + 87 - 2 + 92) & (0xD2 ^ 0xB3 ^ (0x22 ^ 0x7E) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            string5 = "B\u1eadt Show";
                                                        }
                                                        ap.a(string5);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("set") ? 1 : 0)) {
                                                        new dt().c();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("check") ? 1 : 0)) {
                                                        dl.h().m(ec.n().cx);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dd") ? 1 : 0)) {
                                                        int n9;
                                                        String string6;
                                                        StringBuffer stringBuffer3 = new StringBuffer();
                                                        if (ey.l(af ? 1 : 0)) {
                                                            string6 = "T\u1eaft";
                                                            "".length();
                                                            if (null != null) {
                                                                return ((5 ^ 0x32) & ~(0x6D ^ 0x5A)) != 0;
                                                            }
                                                        } else {
                                                            string6 = "B\u1eadt";
                                                        }
                                                        ap.a(stringBuffer3.append(string6).append(" Auto \u0110\u1eadp 8 ").toString());
                                                        if (ey.o(af ? 1 : 0)) {
                                                            n9 = bp[0];
                                                            "".length();
                                                            if ((0xDF ^ 0x9E ^ (0x83 ^ 0xC6)) < (88 + 62 - 66 + 97 ^ 78 + 118 - 110 + 91)) {
                                                                return ((0xBF ^ 0xC6 ^ (0x76 ^ 1)) & (0x84 ^ 0x9D ^ (0x35 ^ 0x22) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            n9 = bp[2];
                                                        }
                                                        af = n9;
                                                        new Thread(new cx(bp[2], bp[0])).start();
                                                        return bp[0];
                                                    }
                                                    if (!ey.o(string2.equals("bang") ? 1 : 0) || !ey.o(string2.equals("fz") ? 1 : 0)) break block374;
                                                    if (!ey.o(string2.equals("bangb") ? 1 : 0) || !ey.o(string2.equals("fb") ? 1 : 0)) break block375;
                                                    if (!ey.o(string2.equals("bangs") ? 1 : 0) || !ey.o(string2.equals("fs") ? 1 : 0)) break block376;
                                                    if (!ey.o(string2.equals("pbang") ? 1 : 0) || !ey.o(string2.equals("wz") ? 1 : 0)) break block377;
                                                    if (ey.l(string2.equals("u") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[7];
                                                        }
                                                        ap.a("Khinh k\u00f4ng ".concat(String.valueOf(n2)));
                                                        ec.d(ec.n().s, ec.n().fc - n2);
                                                        "".length();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("d") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[7];
                                                        }
                                                        ap.a("\u0110\u1ed9n th\u1ed5 ".concat(String.valueOf(n2)));
                                                        ec.d(ec.n().s, ec.n().fc + n2);
                                                        "".length();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("l") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[7];
                                                        }
                                                        ap.a("D\u1ecbch tr\u00e1i ".concat(String.valueOf(n2)));
                                                        ec.d(ec.n().s - n2, ec.n().fc);
                                                        "".length();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("r") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[7];
                                                        }
                                                        ap.a("D\u1ecbch ph\u1ea3i ".concat(String.valueOf(n2)));
                                                        ec.d(ec.n().s + n2, ec.n().fc);
                                                        "".length();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("g") ? 1 : 0)) {
                                                        ec ec2 = ec.n();
                                                        if (ey.a(ec2.fm)) {
                                                            ap.a("MoveTo " + ec2.fm.cx);
                                                            ec.d(ec2.fm.s, ec2.fm.fc);
                                                            "".length();
                                                            "".length();
                                                            if (((138 + 10 - 97 + 126 ^ 142 + 36 - 75 + 66) & (0x48 ^ 0x20 ^ (0x72 ^ 2) ^ -" ".length())) != 0) {
                                                                return ((0x25 ^ 0x40 ^ (0x53 ^ 0x66)) & (0x51 ^ 0x64 ^ (0x3B ^ 0x5E) ^ -" ".length())) != 0;
                                                            }
                                                        } else if (ey.a(ec2.ap)) {
                                                            ap.a("MoveTo " + ec2.ap.cx);
                                                            ec.d(ec2.ap.s, ec2.ap.fc);
                                                            "".length();
                                                            "".length();
                                                            if ((137 + 41 - 152 + 130 ^ 74 + 96 - 97 + 79) == 0) {
                                                                return ((15 + 16 - -81 + 91 ^ 114 + 127 - 131 + 48) & (0x41 ^ 0x6B ^ 78 + 18 - -29 + 2 ^ -" ".length())) != 0;
                                                            }
                                                        } else if (ey.a(ec2.di)) {
                                                            ap.a("MoveTo " + ec2.di.f().j);
                                                            ec.d(ec2.di.y, ec2.di.x);
                                                            "".length();
                                                            "".length();
                                                            if ((8 ^ 0xC) <= ((0x3E ^ 0x2C) & ~(0x87 ^ 0x95))) {
                                                                return ((0x8D ^ 0xA9) & ~(0xA9 ^ 0x8D) & ~((0xA6 ^ 0xBF) & ~(0xD9 ^ 0xC0))) != 0;
                                                            }
                                                        } else if (ey.a(ec2.fz)) {
                                                            ap.a("MoveTo " + ec2.fz.k.e);
                                                            ec.d(ec2.fz.g, ec2.fz.d);
                                                            "".length();
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ta") ? 1 : 0)) {
                                                        ap.s().c(bp[22]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sw") ? 1 : 0)) {
                                                        ap.s().c(bp[55]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("up") ? 1 : 0)) {
                                                        dn.a().b();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("aq") ? 1 : 0)) {
                                                        ec ec3 = ec.n();
                                                        if (ey.a(ec3.di)) {
                                                            ap.au.removeElement(ec3.di);
                                                            "".length();
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("z") ? 1 : 0)) {
                                                        int n10;
                                                        String string7;
                                                        StringBuffer stringBuffer4 = new StringBuffer();
                                                        if (ey.l(ec.af ? 1 : 0)) {
                                                            string7 = "T\u1eaft";
                                                            "".length();
                                                            if (null != null) {
                                                                return ((0x79 ^ 0x70) & ~(0x3D ^ 0x34)) != 0;
                                                            }
                                                        } else {
                                                            string7 = "B\u1eadt";
                                                        }
                                                        ap.a(stringBuffer4.append(string7).append(" auto chuy\u1ec3n map").toString());
                                                        if (ey.o(ec.af ? 1 : 0)) {
                                                            n10 = bp[0];
                                                            "".length();
                                                            if (((0x7E ^ 0x24) & ~(0xC5 ^ 0x9F)) != 0) {
                                                                return ((0x52 ^ 0x44) & ~(0xA ^ 0x1C)) != 0;
                                                            }
                                                        } else {
                                                            n10 = bp[2];
                                                        }
                                                        ec.af = n10;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("rm") ? 1 : 0)) {
                                                        int n11;
                                                        String string8;
                                                        StringBuffer stringBuffer5 = new StringBuffer();
                                                        if (ey.l(ec.cs ? 1 : 0)) {
                                                            string8 = "T\u1eaft";
                                                            "".length();
                                                            if (((0xAB ^ 0xC6 ^ (0x61 ^ 0x41)) & (31 + 2 - -5 + 203 ^ 116 + 122 - 203 + 153 ^ -" ".length())) < 0) {
                                                                return ((21 + 131 - 129 + 119 ^ 100 + 63 - 131 + 107) & (0x40 ^ 0x63 ^ (0x83 ^ 0xA5) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            string8 = "B\u1eadt";
                                                        }
                                                        ap.a(stringBuffer5.append(string8).append(" auto next map").toString());
                                                        if (ey.o(ec.cs ? 1 : 0)) {
                                                            n11 = bp[0];
                                                            "".length();
                                                            if ("  ".length() < 0) {
                                                                return ((0xDE ^ 0xC0) & ~(0x40 ^ 0x5E)) != 0;
                                                            }
                                                        } else {
                                                            n11 = bp[2];
                                                        }
                                                        ec.cs = n11;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("x") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[1];
                                                        }
                                                        ap.a("KC Nh\u1eb7t ".concat(String.valueOf(n2)));
                                                        ae = n2;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("kts") ? 1 : 0)) {
                                                        if (ey.o(n2)) {
                                                            n2 = bp[1];
                                                        }
                                                        ap.a("KC T\u00e0n s\u00e1t ".concat(String.valueOf(n2)));
                                                        o = ec.n().s;
                                                        bc = ec.n().fc;
                                                        be = n2;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ts") ? 1 : 0)) {
                                                        by by2 = by.b(n2);
                                                        if (ey.b(by2)) {
                                                            ap.a("T\u00e0n s\u00e1t all");
                                                            ey.a(bp[1], de.i);
                                                            "".length();
                                                            if (((0x5C ^ 0x1B ^ (0x2F ^ 0x3C)) & (0x90 ^ 0xB5 ^ (0xF2 ^ 0x83) ^ -" ".length())) != ((0x5E ^ 0x77 ^ (0x36 ^ 0x3F)) & (0x5B ^ 0x32 ^ (0xC2 ^ 0x8B) ^ -" ".length()))) {
                                                                return ((159 + 35 - 36 + 7 ^ 111 + 99 - 126 + 47) & (0xC0 ^ 0xA9 ^ (0x34 ^ 0x7B) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("T\u00e0n s\u00e1t " + by2.f().j + " lv " + n2);
                                                            ey.a(by2.g, de.i);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tsx") ? 1 : 0)) {
                                                        bu bu2;
                                                        bu bu3;
                                                        if (ey.n(n2) && ey.f(n2, by.h.length)) {
                                                            bu3 = by.h[n2];
                                                            "".length();
                                                            if ((0x79 ^ 0x7C) <= 0) {
                                                                return ("  ".length() & ~"  ".length()) != 0;
                                                            }
                                                        } else {
                                                            bu3 = null;
                                                        }
                                                        bu bu4 = bu2 = bu3;
                                                        if (ey.b(bu2)) {
                                                            ap.a("T\u00e0n s\u00e1t all");
                                                            ey.a(bp[1], de.i);
                                                            "".length();
                                                            if ((131 + 43 - 16 + 29 ^ 153 + 91 - 241 + 188) < 0) {
                                                                return ((49 + 51 - 47 + 121 ^ 98 + 9 - 100 + 127) & (0x29 ^ 0x27 ^ (0x42 ^ 0x64) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("T\u00e0n s\u00e1t " + bu4.j + " id " + n2);
                                                            ey.a(bu4.h, de.i);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tsa") ? 1 : 0)) {
                                                        ap.a("T\u00e0n s\u00e1t all");
                                                        ey.a(bp[1], de.i);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("anv") ? 1 : 0)) {
                                                        if (ey.i(de.i, bp[0]) && ey.i(de.i, bp[49]) && ey.i(de.i, bp[56])) {
                                                            ap.a("B\u1ea1n ph\u1ea3i \u0111\u1ee9ng \u1edf tr\u01b0\u1eddng \u0111\u1ec3 Auto");
                                                            return bp[0];
                                                        }
                                                        ap.a("Auto Nhi\u1ec7m V\u1ee5 H\u1eb1ng Ng\u00e0y");
                                                        ey.g();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("att") ? 1 : 0)) {
                                                        ap.a("Auto T\u00e0 Th\u00fa");
                                                        ey.l();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ak") ? 1 : 0)) {
                                                        if (ey.a(u, cd)) {
                                                            ap.a("T\u1eaft t\u1ef1 \u0111\u00e1nh");
                                                            ey.q();
                                                            "".length();
                                                            if (-"   ".length() > 0) {
                                                                return ((0x84 ^ 0xC7) & ~(0x74 ^ 0x37)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt t\u1ef1 \u0111\u00e1nh");
                                                            ey.x();
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("adv") ? 1 : 0)) {
                                                        ap.a("Auto Danh V\u1ecdng");
                                                        ey.d();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ld") ? 1 : 0)) {
                                                        cc.b();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("nw") ? 1 : 0)) {
                                                        if (ey.a(u, ak)) {
                                                            ap.a("T\u1eaft auto l\u00f4i \u0111\u00e0i win");
                                                            bo.ai();
                                                            u = null;
                                                            "".length();
                                                            if ((0x2B ^ 0x11 ^ (0x7A ^ 0x44)) != (0xD8 ^ 0x8F ^ (0x10 ^ 0x43))) {
                                                                return ((0x29 ^ 0x27 ^ (0x35 ^ 0x13)) & (129 + 53 - 123 + 112 ^ 96 + 71 - 133 + 97 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt auto l\u00f4i \u0111\u00e0i win");
                                                            ak.e();
                                                            ey.a(ak);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("nl") ? 1 : 0)) {
                                                        if (ey.a(u, bz)) {
                                                            ap.a("T\u1eaft auto l\u00f4i \u0111\u00e0i lose");
                                                            bo.ai();
                                                            u = null;
                                                            "".length();
                                                            if ((149 + 183 - 165 + 23 ^ 87 + 52 - 34 + 81) != (0x82 ^ 0xA2 ^ (0x64 ^ 0x40))) {
                                                                return ((169 + 179 - 145 + 16 ^ 148 + 143 - 232 + 139) & (0xFB ^ 0xBD ^ (0xEE ^ 0xB5) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt auto l\u00f4i \u0111\u00e0i lose");
                                                            bz.e();
                                                            ey.a(bz);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (!ey.o(string2.equals("e") ? 1 : 0) || !ey.o(string2.equals("pe") ? 1 : 0)) break block378;
                                                    if (ey.l(string2.equals("k") ? 1 : 0)) {
                                                        ap.a("Chuy\u1ec3n Khu: ".concat(String.valueOf(n2)));
                                                        ap.s();
                                                        "".length();
                                                        ap.d(n2);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ltd") ? 1 : 0)) {
                                                        if (ey.o(de.j(de.i) ? 1 : 0) && ey.o(de.b(de.i) ? 1 : 0)) {
                                                            ap.a("H\u00e3y \u0111\u1ee9ng \u1edf l\u00e0ng ho\u1eb7c tr\u01b0\u1eddng \u0111\u1ec3 l\u01b0u t\u1ecda \u0111\u1ed9");
                                                            "".length();
                                                            if ("  ".length() >= (0x28 ^ 0x2C)) {
                                                                return ((0xB5 ^ 0x8A) & ~(0xB2 ^ 0x8D)) != 0;
                                                            }
                                                        } else {
                                                            ap.g(bp[5]);
                                                            dl.h().x(bp[5]);
                                                            dl.h().d(bp[5], bp[0], bp[2]);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("nm") ? 1 : 0)) {
                                                        ap.a("Next map: ".concat(String.valueOf(n2)));
                                                        de.d(n2);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("gm") ? 1 : 0)) {
                                                        if (ey.f(n2, de.d.length) && ey.n(n2)) {
                                                            ap.a("Go to: " + de.d[n2]);
                                                            de.m(n2);
                                                            return bp[0];
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("npc") ? 1 : 0)) {
                                                        if (ey.f(n2, ds.b.length)) {
                                                            ap.a("Act NPC: " + ds.b[n2].d);
                                                            ap.g(n2);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("hs") ? 1 : 0)) {
                                                        ap.a("Next to hirosaki");
                                                        new Thread(new cx(bp[2], bp[0])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("hr") ? 1 : 0)) {
                                                        ap.a("Next to haruna");
                                                        new Thread(new cx(bp[0], bp[49])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("oz") ? 1 : 0)) {
                                                        ap.a("Next to Ozawa(Oozaka)");
                                                        new Thread(new cx(bp[11], bp[56])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("kj") ? 1 : 0)) {
                                                        ap.a("Next to Kojin");
                                                        new Thread(new cx(bp[12], bp[9])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sz") ? 1 : 0)) {
                                                        ap.a("Next to Sanzu");
                                                        new Thread(new cx(bp[13], bp[16])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tn") ? 1 : 0)) {
                                                        ap.a("Next to Tone");
                                                        new Thread(new cx(bp[5], bp[57])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("lc") ? 1 : 0)) {
                                                        ap.a("Next to Ch\u00e0i");
                                                        new Thread(new cx(bp[29], bp[54])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ck") ? 1 : 0)) {
                                                        ap.a("Next to Chakumi");
                                                        new Thread(new cx(bp[31], bp[50])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("eg") ? 1 : 0)) {
                                                        ap.a("Next to Echigo");
                                                        new Thread(new cx(bp[4], bp[58])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("os") ? 1 : 0)) {
                                                        ap.a("Next to Oshin");
                                                        new Thread(new cx(bp[22], bp[52])).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("mnv") ? 1 : 0)) {
                                                        ap.a("Next to Map Nhi\u1ec7m V\u1ee5");
                                                        de.m(ap.ak());
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("mnvp") ? 1 : 0)) {
                                                        ap.a("Next to Map Nhi\u1ec7m V\u1ee5 Ph\u1ee5");
                                                        y y2 = ec.n(bp[2]);
                                                        if (ey.a(y2)) {
                                                            de.m(y2.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("add") ? 1 : 0)) {
                                                        ap.a("Th\u00eam v\u1eadt ph\u1ea9m v\u00e0o ds nh\u1eb7t");
                                                        fm fm2 = ec.n().fz;
                                                        if (ey.a(fm2)) {
                                                            ey.c(fm2.k.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("del") ? 1 : 0)) {
                                                        ap.a("X\u00f3a v\u1eadt ph\u1ea9m kh\u1ecfi ds nh\u1eb7t");
                                                        fm fm3 = ec.n().fz;
                                                        if (ey.a(fm3)) {
                                                            ey.d(fm3.k.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ait") ? 1 : 0)) {
                                                        bf bf2 = t.b((short)n2);
                                                        if (ey.a((Object)bf2)) {
                                                            ap.a("Th\u00eam " + bf2.e + " v\u00e0o ds nh\u1eb7t");
                                                            ey.c(bf2.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dit") ? 1 : 0)) {
                                                        bf bf3 = t.b((short)n2);
                                                        if (ey.a((Object)bf3)) {
                                                            ap.a("X\u00f3a " + bf3.e + " kh\u1ecfi ds nh\u1eb7t");
                                                            ey.d(bf3.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ajt") ? 1 : 0)) {
                                                        bf bf4 = t.b((short)n2);
                                                        if (ey.a((Object)bf4)) {
                                                            ap.a("Th\u00eam " + bf4.e + " v\u00e0o ds nh\u1eb7t");
                                                            ey.e(bf4.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("djt") ? 1 : 0)) {
                                                        bf bf5 = t.b((short)n2);
                                                        if (ey.a((Object)bf5)) {
                                                            ap.a("X\u00f3a " + bf5.e + " kh\u1ecfi ds nh\u1eb7t");
                                                            ey.a(bf5.d);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("cnhat") ? 1 : 0)) {
                                                        int n12;
                                                        if (ey.l(bb ? 1 : 0)) {
                                                            ap.a("B\u1eadt nh\u1eb7t xa");
                                                            "".length();
                                                            if (-" ".length() > (0x6B ^ 0x33 ^ (0xEA ^ 0xB6))) {
                                                                return (("  ".length() ^ (0x70 ^ 0x3A)) & (136 + 58 - 32 + 66 ^ 74 + 97 - 101 + 102 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt h\u00fat VP");
                                                        }
                                                        if (ey.o(bb ? 1 : 0)) {
                                                            n12 = bp[0];
                                                            "".length();
                                                            if (" ".length() <= 0) {
                                                                return ((0x76 ^ 0x1F ^ (0xC3 ^ 0x83)) & (116 + 144 - 160 + 132 ^ 47 + 86 - 114 + 174 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            n12 = bp[2];
                                                        }
                                                        bb = n12;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("ruong") ? 1 : 0)) {
                                                        ap.s().j();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("vpnhat") ? 1 : 0)) {
                                                        ap.s().c(bp[59]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("die") ? 1 : 0)) {
                                                        ey.a();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dcvt") ? 1 : 0)) {
                                                        int n13;
                                                        if (ey.l(av ? 1 : 0)) {
                                                            ap.a("T\u1eaft \u0111\u00e1nh chuy\u1ec3n v\u1ecb tr\u00ed");
                                                            "".length();
                                                            if (-"  ".length() >= 0) {
                                                                return ((0xDE ^ 0xAA ^ (0x62 ^ 0x37)) & (0x8C ^ 0x97 ^ (0x5F ^ 0x65) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt \u0111\u00e1nh chuy\u1ec3n v\u1ecb tr\u00ed");
                                                        }
                                                        if (ey.o(av ? 1 : 0)) {
                                                            n13 = bp[0];
                                                            "".length();
                                                            if ("  ".length() <= ((0x97 ^ 0x9B ^ (0xFD ^ 0xB6)) & (0x99 ^ 0xA3 ^ (0x25 ^ 0x58) ^ -" ".length()))) {
                                                                return ((142 + 131 - 258 + 157 ^ 23 + 84 - -40 + 3) & (0xCE ^ 0xC5 ^ (0x79 ^ 0x48) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            av = bp[2];
                                                            n13 = av ? 1 : 0;
                                                        }
                                                        if (ey.l(ec.ct ? 1 : 0)) {
                                                            int n14;
                                                            dl dl2 = dl.h();
                                                            StringBuffer stringBuffer6 = new StringBuffer().append("dcvt ");
                                                            if (ey.l(av ? 1 : 0)) {
                                                                n14 = bp[0];
                                                                "".length();
                                                                if (-(0x10 ^ 0x14) >= 0) {
                                                                    return ("   ".length() & ~"   ".length()) != 0;
                                                                }
                                                            } else {
                                                                n14 = bp[2];
                                                            }
                                                            dl2.l(stringBuffer6.append(n14).toString());
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("avt") ? 1 : 0)) {
                                                        ap.a("Th\u00eam v\u1ecb tr\u00ed " + az.size());
                                                        az.addElement(new Integer(ec.n().s));
                                                        an.addElement(new Integer(ec.n().fc));
                                                        if (ey.l(ec.ct ? 1 : 0)) {
                                                            dl.h().l("avt " + ec.n().s + " " + ec.n().fc);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dvt") ? 1 : 0)) {
                                                        ap.a("X\u00f3a h\u1ebft v\u1ecb tr\u00ed");
                                                        az.removeAllElements();
                                                        an.removeAllElements();
                                                        if (ey.l(ec.ct ? 1 : 0)) {
                                                            dl.h().l("dvt");
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dvtx") ? 1 : 0)) {
                                                        ap.a("X\u00f3a v\u1ecb tr\u00ed ".concat(String.valueOf(n2)));
                                                        az.removeElementAt(n2);
                                                        an.removeElementAt(n2);
                                                        if (ey.l(ec.ct ? 1 : 0)) {
                                                            dl.h().l("dtvx ".concat(String.valueOf(n2)));
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dck") ? 1 : 0)) {
                                                        int n15;
                                                        if (ey.o(w ? 1 : 0)) {
                                                            n15 = bp[0];
                                                            "".length();
                                                            if (" ".length() == (0x41 ^ 0x2C ^ (0x11 ^ 0x78))) {
                                                                return ((0x58 ^ 0x17 ^ (0x3D ^ 0x2F)) & (47 + 124 - 105 + 130 ^ 24 + 79 - 32 + 82 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            w = bp[2];
                                                            n15 = w ? 1 : 0;
                                                        }
                                                        if (ey.l(n15)) {
                                                            ap.a("T\u1eaft \u0111\u00e1nh chuy\u1ec3n khu");
                                                            "".length();
                                                            if (null != null) {
                                                                return ((0x58 ^ 0x73) & ~(0x49 ^ 0x62)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt \u0111\u00e1nh chuy\u1ec3n khu");
                                                            em.ak.a("Khu", new fr("\u0110\u1eb7t", bp[60]), bp[0]);
                                                            em.ak.a.a(ey.e());
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("keeplevel") ? 1 : 0)) {
                                                        int n16;
                                                        if (ey.l(k ? 1 : 0)) {
                                                            ap.a("T\u1eaft gi\u1eef lv");
                                                            "".length();
                                                            if (((151 + 151 - 270 + 150 ^ 89 + 116 - 130 + 62) & (0x42 ^ 6 ^ (0x6E ^ 0x15) ^ -" ".length())) != 0) {
                                                                return ((0x16 ^ 0x1E ^ (0x77 ^ 0x2D)) & (0xCF ^ 0xA9 ^ (0x94 ^ 0xA0) ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("B\u1eadt gi\u1eef lv");
                                                        }
                                                        if (ey.o(k ? 1 : 0)) {
                                                            n16 = bp[0];
                                                            "".length();
                                                            if (-" ".length() > 0) {
                                                                return ((42 + 38 - 31 + 141 ^ 21 + 84 - 66 + 96) & (28 + 73 - 86 + 167 ^ 101 + 5 - 3 + 40 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            n16 = bp[2];
                                                        }
                                                        k = n16;
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("addn") ? 1 : 0)) {
                                                        ap.a("Th\u00eam nh\u00f3m");
                                                        ec ec4 = ec.n().fm;
                                                        if (ey.a(ec4)) {
                                                            if (ey.o(ey.e(ec4.cx) ? 1 : 0)) {
                                                                q.addElement(ec4.cx);
                                                            }
                                                            dl.h().b(ec4.cx);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("cn") ? 1 : 0)) {
                                                        ap.a("X\u00f3a nh\u00f3m");
                                                        v = null;
                                                        q.removeAllElements();
                                                        ey.y();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("pt") ? 1 : 0)) {
                                                        if (ey.o(ec.n().cx.equals(v) ? 1 : 0)) {
                                                            ap.a("B\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                            return bp[0];
                                                        }
                                                        ap.a("PT nh\u00f3m");
                                                        n5 = bp[2];
                                                        while (ey.f(n5, q.size())) {
                                                            string = (String)q.elementAt(n5);
                                                            if (ey.o(ey.f(string) ? 1 : 0)) {
                                                                dl.h().b(string);
                                                            }
                                                            if (ey.l(u instanceof aq)) {
                                                                dl.h().a(string, "pkm " + ey.u.u);
                                                                dl.h().a(string, "pkk " + ey.u.x);
                                                                "".length();
                                                                if (((0x4A ^ 0x7B ^ (0x8B ^ 0x85)) & (29 + 129 - 155 + 135 ^ 51 + 10 - 50 + 170 ^ -" ".length())) < -" ".length()) {
                                                                    return ((48 + 144 - 45 + 50 ^ 127 + 56 - 182 + 139) & (217 + 172 - 170 + 9 ^ 153 + 47 - 132 + 105 ^ -" ".length())) != 0;
                                                                }
                                                            } else if (ey.a((Object)u)) {
                                                                dl.h().a(string, "map " + ey.u.u);
                                                                dl.h().a(string, "khu " + ey.u.x);
                                                            }
                                                            ++n5;
                                                            "".length();
                                                            if ((0xAD ^ 0xC1 ^ (0x56 ^ 0x3E)) == (0x3B ^ 1 ^ (0x3F ^ 1))) continue;
                                                            return ((0xCF ^ 0xAC ^ (0x4E ^ 0x18)) & (0x24 ^ 0xE ^ (0x3B ^ 0x24) ^ -" ".length())) != 0;
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sn") ? 1 : 0)) {
                                                        ap.a("L\u01b0u nh\u00f3m");
                                                        ey.y();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tsn") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            by by3 = by.b(n2);
                                                            if (ey.b(by3)) {
                                                                ap.a("T\u00e0n s\u00e1t nh\u00f3m all");
                                                                ey.a(bp[1], de.i);
                                                                "".length();
                                                                if (-"   ".length() > 0) {
                                                                    return ((0xA6 ^ 0x8C ^ "   ".length()) & (0x1A ^ 0x43 ^ (0x1E ^ 0x6E) ^ -" ".length())) != 0;
                                                                }
                                                            } else {
                                                                ap.a("T\u00e0n s\u00e1t nh\u00f3m " + by3.f().j + " lv " + n2);
                                                                ey.a(by3.g, de.i);
                                                            }
                                                            ey.ca.j = bp[0];
                                                            dl.h().l("ts " + ey.ca.u + " " + ey.ca.x + " " + ey.ca.c);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tsnx") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            bu bu5;
                                                            bu bu6;
                                                            if (ey.n(n2) && ey.f(n2, by.h.length)) {
                                                                bu6 = by.h[n2];
                                                                "".length();
                                                                if ((0x16 ^ 0x12) == " ".length()) {
                                                                    return ((0xC9 ^ 0x94) & ~(0x44 ^ 0x19)) != 0;
                                                                }
                                                            } else {
                                                                bu6 = null;
                                                            }
                                                            bu bu7 = bu5 = bu6;
                                                            if (ey.b(bu5)) {
                                                                ap.a("T\u00e0n s\u00e1t nh\u00f3m all");
                                                                ey.a(bp[1], de.i);
                                                                "".length();
                                                                if (-" ".length() >= 0) {
                                                                    return ((0x7B ^ 0x3B) & ~(0xF6 ^ 0xB6)) != 0;
                                                                }
                                                            } else {
                                                                ap.a("T\u00e0n s\u00e1t nh\u00f3m " + bu7.j + " id " + n2);
                                                                ey.a(bu7.h, de.i);
                                                            }
                                                            ey.ca.j = bp[0];
                                                            dl.h().l("ts " + ey.ca.u + " " + ey.ca.x + " " + ey.ca.c);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tsan") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            ap.a("T\u00e0n s\u00e1t nh\u00f3m all");
                                                            ey.a(bp[1], de.i);
                                                            ey.ca.j = bp[0];
                                                            dl.h().l("tsa " + ey.ca.u + " " + ey.ca.x);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("attn") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            ap.a("Auto T\u00e0 Th\u00fa Nh\u00f3m");
                                                            ey.l();
                                                            ey.ao.j = bp[0];
                                                            dl.h().l("att " + ey.ao.u + " " + ey.ao.x + " " + ey.ao.c);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("buff") ? 1 : 0)) {
                                                        ap.a("B\u1eadt Buff HS Xa");
                                                        ey.a(bp[0], bp[0]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("bux") ? 1 : 0)) {
                                                        ap.a("B\u1eadt Buff Xa");
                                                        ey.a(bp[0], bp[2]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("hsx") ? 1 : 0)) {
                                                        ap.a("B\u1eadt HS Xa");
                                                        ey.a(bp[2], bp[0]);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("cy") ? 1 : 0)) {
                                                        if (ey.b(u)) {
                                                            ap.a("B\u1ea1n ch\u01b0a up y\u00ean");
                                                            "".length();
                                                            if ("   ".length() > (0x36 ^ 0x32)) {
                                                                return ((0x1D ^ 0x45) & ~(0xFD ^ 0xA5)) != 0;
                                                            }
                                                        } else {
                                                            int n17 = ec.n().cg - ey.u.r;
                                                            n2 = (int)((System.currentTimeMillis() - ey.u.m) / 1000L);
                                                            ap.a("Up " + n17 + " trong " + bz.b(n2) + " perh=" + n17 / n2 * bp[61]);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("clv") ? 1 : 0)) {
                                                        if (ey.b(u)) {
                                                            ap.a("B\u1ea1n ch\u01b0a up level");
                                                            "".length();
                                                            if (-" ".length() == "   ".length()) {
                                                                return ((0x5E ^ 0x14) & ~(0x19 ^ 0x53)) != 0;
                                                            }
                                                        } else {
                                                            long l2 = ec.n().cj - ey.u.o;
                                                            float f2 = (float)(l2 * 10000L / ap.al[ec.n().aa]) / 100.0f;
                                                            int n18 = (int)((System.currentTimeMillis() - ey.u.m) / 1000L);
                                                            long l3 = l2 * 3600L / (long)n18;
                                                            float f3 = (float)(l3 * 10000L / ap.al[ec.n().aa]) / 100.0f;
                                                            ap.a("Up " + l2 + " - " + f2 + "% trong " + bz.b(n18) + " perh=" + l3 + " - " + f3 + "%");
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("st") ? 1 : 0)) {
                                                        by by4 = by.b(n2);
                                                        if (ey.b(by4)) {
                                                            ap.a("Stanima all");
                                                            ey.c(bp[1], de.i);
                                                            "".length();
                                                            if (null != null) {
                                                                return (("  ".length() ^ (0x48 ^ 0x59)) & (135 + 130 - 136 + 18 ^ 88 + 15 - 14 + 39 ^ -" ".length())) != 0;
                                                            }
                                                        } else {
                                                            ap.a("Stanima " + by4.f().j + " lv " + n2);
                                                            ey.c(by4.g, de.i);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sta") ? 1 : 0)) {
                                                        ap.a("Stanima all");
                                                        ey.c(bp[1], de.i);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("stn") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            by by5 = by.b(n2);
                                                            if (ey.b(by5)) {
                                                                ap.a("Stanima nh\u00f3m all");
                                                                ey.c(bp[1], de.i);
                                                                "".length();
                                                                if (" ".length() < -" ".length()) {
                                                                    return ((0xDE ^ 0xBF ^ (0x47 ^ 0x77)) & (0x16 ^ 0x36 ^ (0x18 ^ 0x69) ^ -" ".length())) != 0;
                                                                }
                                                            } else {
                                                                ap.a("Stanima nh\u00f3m " + by5.f().j + " lv " + n2);
                                                                ey.c(by5.g, de.i);
                                                            }
                                                            ey.aj.j = bp[0];
                                                            dl.h().l("st " + ey.aj.u + " " + ey.aj.x + " " + ey.aj.b);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("stan") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            ap.a("Stanima nh\u00f3m all");
                                                            ey.c(bp[1], de.i);
                                                            ey.aj.j = bp[0];
                                                            dl.h().l("sta " + ey.aj.u + " " + ey.aj.x);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("stx") ? 1 : 0)) {
                                                        bu bu8;
                                                        bu bu9;
                                                        if (ey.n(n2) && ey.f(n2, by.h.length)) {
                                                            bu9 = by.h[n2];
                                                            "".length();
                                                            if ("   ".length() < 0) {
                                                                return ((0xF1 ^ 0xC4) & ~(0xC ^ 0x39)) != 0;
                                                            }
                                                        } else {
                                                            bu9 = null;
                                                        }
                                                        bu bu10 = bu8 = bu9;
                                                        if (ey.b(bu8)) {
                                                            ap.a("T\u00e0n s\u00e1t all");
                                                            ey.a(bp[1], de.i);
                                                            "".length();
                                                            if (-" ".length() == (0x75 ^ 0x71)) {
                                                                return ((0x9D ^ 0xB0) & ~(0x78 ^ 0x55)) != 0;
                                                            }
                                                        } else {
                                                            ap.a("T\u00e0n s\u00e1t " + bu10.j + " id " + n2);
                                                            ey.c(bu10.h, de.i);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (!ey.o(string2.equals("stnx") ? 1 : 0)) break block379;
                                                    if (ey.l(string2.equals("sts") ? 1 : 0)) {
                                                        ap.a("Step Stanima");
                                                        aj.d();
                                                        if (ey.l(ec.n().cx.equals(v) ? 1 : 0) && ey.m(ap.an.size())) {
                                                            dl.h().l("sts");
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("stb") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.i(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            if (ey.i(ec.n().dk.c, bp[29])) {
                                                                ap.a("B\u1ea1n kh\u00f4ng ph\u1ea3i l\u00e0 qu\u1ea1t");
                                                                return bp[0];
                                                            }
                                                            ap.a("Stanima Buff HS");
                                                            ey.b(bp[0], bp[0]);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("stbx") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.i(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            if (ey.i(ec.n().dk.c, bp[29])) {
                                                                ap.a("B\u1ea1n kh\u00f4ng ph\u1ea3i l\u00e0 qu\u1ea1t");
                                                                return bp[0];
                                                            }
                                                            ap.a("Stanima Buff");
                                                            ey.b(bp[0], bp[2]);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sths") ? 1 : 0)) {
                                                        if (ey.m(ap.an.size()) && ey.i(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            if (ey.i(ec.n().dk.c, bp[29])) {
                                                                ap.a("B\u1ea1n kh\u00f4ng ph\u1ea3i l\u00e0 qu\u1ea1t");
                                                                return bp[0];
                                                            }
                                                            ap.a("Stanima HS");
                                                            ey.b(bp[2], bp[0]);
                                                            return bp[0];
                                                        }
                                                        ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("pkb") ? 1 : 0)) {
                                                        ap.a("PK Th\u1ea7n Th\u00fa");
                                                        ey.a(new aq(de.i));
                                                        if (ey.a((Object)v) && ey.l(ec.n().cx.equals(v) ? 1 : 0) && ey.h(ap.an.size(), bp[0])) {
                                                            dl.h().l("pkm " + de.i);
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("pkk") ? 1 : 0)) {
                                                        ap.a("PK Th\u1ea7n Th\u00fa");
                                                        aq aq2 = new aq(de.i);
                                                        new aq(de.i).x = n2;
                                                        ey.a(aq2);
                                                        if (ey.a((Object)v) && ey.l(ec.n().cx.equals(v) ? 1 : 0) && ey.h(ap.an.size(), bp[0])) {
                                                            dl.h().l("pkm " + de.i);
                                                            dl.h().l("pkk ".concat(String.valueOf(n2)));
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("lb") ? 1 : 0)) {
                                                        string = "";
                                                        n2 = bp[2];
                                                        while (ey.f(n2, ap.au.size())) {
                                                            by by6 = (by)ap.au.elementAt(n2);
                                                            if (ey.l(by6.j ? 1 : 0)) {
                                                                string = string + by6.f().j + " lv: " + by6.i + ", ";
                                                            }
                                                            ++n2;
                                                            "".length();
                                                            if (null == null) continue;
                                                            return ((0x85 ^ 0x8E) & ~(0x54 ^ 0x5F)) != 0;
                                                        }
                                                        ap.a("Mob: ".concat(String.valueOf(string)));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("tb") ? 1 : 0)) {
                                                        new Thread(new es()).start();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("sell") ? 1 : 0)) {
                                                        ap.a("Auto Sell");
                                                        ey.s();
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("h") ? 1 : 0)) {
                                                        Calendar calendar = fo.a();
                                                        ap.a("Time " + calendar.get(bp[62]) + ":" + calendar.get(bp[41]) + ":" + calendar.get(bp[44]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dt") ? 1 : 0)) {
                                                        ey.a(new cj());
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("dh") ? 1 : 0)) {
                                                        ey.a(new cm());
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("nv") ? 1 : 0)) {
                                                        ey.a(new s());
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string2.equals("f") ? 1 : 0)) {
                                                        ap.s().c(n2);
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("hd9x") ? 1 : 0)) {
                                                        ap.a("Hang \u0111\u1ed9ng 9x");
                                                        ey.a(new ez());
                                                        if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                                            dl.h().l("hd9x");
                                                        }
                                                        return bp[0];
                                                    }
                                                    if (!ey.d(string.length(), bp[13])) break block380;
                                                    if (ey.l(string.equals("as10") ? 1 : 0)) {
                                                        ey.a(new ba());
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("as20") ? 1 : 0)) {
                                                        ey.a(new di(bp[2]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20k") ? 1 : 0)) {
                                                        ey.a(new di(bp[0]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20t") ? 1 : 0)) {
                                                        ey.a(new di(bp[11]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20u") ? 1 : 0)) {
                                                        ey.a(new di(bp[12]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20c") ? 1 : 0)) {
                                                        ey.a(new di(bp[13]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20d") ? 1 : 0)) {
                                                        ey.a(new di(bp[5]));
                                                        return bp[0];
                                                    }
                                                    if (ey.l(string.equals("a20q") ? 1 : 0)) {
                                                        ey.a(new di(bp[29]));
                                                        return bp[0];
                                                    }
                                                    break block381;
                                                }
                                                if (ey.l(string2.equals("boss") ? 1 : 0)) {
                                                    ap.a("Auto Boss ".concat(String.valueOf(n2)));
                                                    ey.a(new u(n2));
                                                    return bp[0];
                                                }
                                                if (ey.l(string2.equals("kpk") ? 1 : 0)) {
                                                    ap.a("Khu PK ".concat(String.valueOf(n2)));
                                                    ax.ae = n2;
                                                    return bp[0];
                                                }
                                                if (ey.l(string2.equals("cpk") ? 1 : 0)) {
                                                    ap.a("X\u00f3a ds PK");
                                                    fs.a();
                                                    return bp[0];
                                                }
                                                if (ey.l(string.startsWith("apk") ? 1 : 0)) {
                                                    String[] stringArray = ey.b(string, " ");
                                                    if (ey.h(stringArray.length, bp[0])) {
                                                        ap.a("Th\u00eam " + stringArray[bp[0]] + " v\u00e0o ds PK");
                                                        fs.c(stringArray[bp[0]]);
                                                        "".length();
                                                        if (-"   ".length() >= 0) {
                                                            return ((0xCF ^ 0xC7 ^ (0x1B ^ 0x31)) & (0x5C ^ 0x53 ^ (0x6F ^ 0x42) ^ -" ".length())) != 0;
                                                        }
                                                    } else if (ey.a(ec.n().fm)) {
                                                        ap.a("Th\u00eam " + ec.n().fm.cx + " v\u00e0o ds PK");
                                                        fs.c(ec.n().fm.cx);
                                                    }
                                                    return bp[0];
                                                }
                                                if (ey.l(string.startsWith("dpk") ? 1 : 0)) {
                                                    String[] stringArray = ey.b(string, " ");
                                                    if (ey.h(stringArray.length, bp[0])) {
                                                        ap.a("X\u00f3a " + stringArray[bp[0]] + " kh\u1ecfi ds PK");
                                                        fs.b(stringArray[bp[0]]);
                                                        "".length();
                                                        if (" ".length() != " ".length()) {
                                                            return ((0x87 ^ 0xC4) & ~(0x10 ^ 0x53)) != 0;
                                                        }
                                                    } else if (ey.a(ec.n().fm)) {
                                                        ap.a("X\u00f3a " + ec.n().fm.cx + " kh\u1ecfi ds PK");
                                                        fs.b(ec.n().fm.cx);
                                                    }
                                                    return bp[0];
                                                }
                                                if (ey.l(string2.equals("chs") ? 1 : 0)) {
                                                    ap.a("X\u00f3a ds HS");
                                                    ey.u();
                                                    return bp[0];
                                                }
                                                if (ey.l(string.startsWith("ahs") ? 1 : 0)) {
                                                    String[] stringArray = ey.b(string, " ");
                                                    if (ey.h(stringArray.length, bp[0])) {
                                                        ap.a("Th\u00eam " + stringArray[bp[0]] + " v\u00e0o ds HS");
                                                        ey.j(stringArray[bp[0]]);
                                                        "".length();
                                                        if (-" ".length() >= 0) {
                                                            return ((0x3B ^ 0x27 ^ (0x34 ^ 0xE)) & (130 + 27 - 104 + 135 ^ 100 + 23 - 105 + 136 ^ -" ".length())) != 0;
                                                        }
                                                    } else if (ey.a(ec.n().fm)) {
                                                        ap.a("Th\u00eam " + ec.n().fm.cx + " v\u00e0o ds HS");
                                                        ey.j(ec.n().fm.cx);
                                                    }
                                                    return bp[0];
                                                }
                                                if (ey.l(string2.equals("dhs") ? 1 : 0)) {
                                                    String[] stringArray = ey.b(string, " ");
                                                    if (ey.h(stringArray.length, bp[0])) {
                                                        ap.a("X\u00f3a " + stringArray[bp[0]] + " kh\u1ecfi ds HS");
                                                        ey.k(stringArray[bp[0]]);
                                                        "".length();
                                                        if (null != null) {
                                                            return ((0x19 ^ 0x5D) & ~(0x2C ^ 0x68)) != 0;
                                                        }
                                                    } else if (ey.a(ec.n().fm)) {
                                                        ap.a("X\u00f3a " + ec.n().fm.cx + " kh\u1ecfi ds PK");
                                                        ey.k(ec.n().fm.cx);
                                                    }
                                                    return bp[0];
                                                }
                                                if (ey.l(string.startsWith("a20s") ? 1 : 0)) {
                                                    String[] stringArray = ey.b(string, " ");
                                                    if (ey.h(stringArray.length, bp[0])) {
                                                        ey.a(new fc(stringArray[bp[0]]));
                                                    }
                                                    return bp[0];
                                                }
                                                if (ey.l(string.startsWith("dg") ? 1 : 0)) {
                                                    n2 = (string = string.substring(bp[12])).indexOf(bp[54]);
                                                    if (ey.m(n2)) {
                                                        try {
                                                            bf bf6 = t.b(Short.parseShort(string.substring(bp[2], n2)));
                                                            bp.a(bf6, string.substring(n2 + bp[0], string.length()));
                                                            ap.a("\u0110\u1eb7t gi\u00e1: " + bf6.e);
                                                            "".length();
                                                        }
                                                        catch (Exception exception) {
                                                            exception.printStackTrace();
                                                        }
                                                        if (null != null) {
                                                            return ((32 + 147 - 65 + 80 ^ 158 + 109 - 265 + 157) & (19 + 126 - 8 + 21 ^ 143 + 77 - 42 + 17 ^ -" ".length())) != 0;
                                                        }
                                                    }
                                                    return bp[0];
                                                }
                                                if (!ey.l(string.startsWith("asw") ? 1 : 0)) break block382;
                                                String[] stringArray = ey.b(string, " ");
                                                try {
                                                    n2 = Integer.parseInt(stringArray[bp[0]]);
                                                    int n19 = Integer.parseInt(stringArray[bp[11]]);
                                                    bf bf7 = t.b((short)n2);
                                                    ap.a("Th\u00eam: " + bf7.e + " gi\u00e1: " + n19 + " v\u00e0o ds b\u00e1n Shinwa");
                                                    ey.b(n2, n19);
                                                    "".length();
                                                }
                                                catch (Exception exception) {
                                                    exception.printStackTrace();
                                                }
                                                if (-"  ".length() > 0) {
                                                    return ((0x43 ^ 0x1D) & ~(0xA ^ 0x54)) != 0;
                                                }
                                                return bp[0];
                                            }
                                            if (!ey.l(string.startsWith("rsw") ? 1 : 0)) break block384;
                                            String[] stringArray = ey.b(string, " ");
                                            try {
                                                n2 = Integer.parseInt(stringArray[bp[0]]);
                                                bf bf8 = t.b((short)n2);
                                                if (ey.l(ey.b(n2) ? 1 : 0)) {
                                                    int n20 = ey.h(n2);
                                                    ap.a("X\u00f3a: " + bf8.e + " gi\u00e1: " + n20 + " kh\u1ecfi ds b\u00e1n Shinwa");
                                                    ey.i(n2);
                                                    "".length();
                                                    if ("   ".length() < 0) {
                                                        return ((0xD7 ^ 0x8B ^ (0x5B ^ 0x42)) & (0xA9 ^ 0xC3 ^ (0x86 ^ 0xA9) ^ -" ".length())) != 0;
                                                    }
                                                } else {
                                                    ap.a("Item " + bf8.e + " ch\u01b0a c\u00f3 trong ds b\u00e1n Shinwa");
                                                }
                                                "".length();
                                            }
                                            catch (Exception exception) {
                                                exception.printStackTrace();
                                            }
                                            if ("  ".length() == (99 + 76 - 130 + 128 ^ 138 + 153 - 246 + 124)) {
                                                return ((102 + 192 - 264 + 203 ^ 122 + 44 - 161 + 155) & (191 + 108 - 192 + 123 ^ 110 + 25 - -38 + 2 ^ -" ".length())) != 0;
                                            }
                                            return bp[0];
                                        }
                                        if (!ey.l(string.startsWith("tnx") ? 1 : 0)) break block381;
                                        if (!ey.l(de.j(de.i) ? 1 : 0)) break block386;
                                        String[] stringArray = ey.b(string, " ");
                                        try {
                                            n2 = Integer.parseInt(stringArray[bp[0]]);
                                            "".length();
                                        }
                                        catch (Exception exception) {
                                            return bp[2];
                                        }
                                        if (-" ".length() >= ((0xBB ^ 0xA9) & ~(0x76 ^ 0x64))) {
                                            return ((0xAC ^ 0x8C) & ~(0x4B ^ 0x6B) & ~((0x6B ^ 0x33) & ~(0x1E ^ 0x46))) != 0;
                                        }
                                        try {
                                            n4 = Integer.parseInt(stringArray[bp[11]]);
                                            "".length();
                                        }
                                        catch (Exception exception) {
                                            n3 = bp[2];
                                        }
                                        if (((0x64 ^ 0x40) & ~(0x95 ^ 0xB1)) != 0) {
                                            return ((0xCD ^ 0x83) & ~(0x65 ^ 0x2B)) != 0;
                                        }
                                        try {
                                            n4 = Integer.parseInt(stringArray[bp[12]]);
                                            "".length();
                                        }
                                        catch (Exception exception) {
                                            n3 = bp[2];
                                        }
                                        if (((2 ^ 0x1F ^ (0x31 ^ 0x69)) & (0x42 ^ 0x66 ^ (7 ^ 0x66) ^ -" ".length())) < 0) {
                                            return ((37 + 42 - -88 + 22 ^ 98 + 75 - 5 + 19) & (1 ^ 0x1E ^ (0xAB ^ 0xB2) ^ -" ".length())) != 0;
                                        }
                                        ap.a("Auto l\u00e0m " + n2 + " ti\u00ean n\u1eef");
                                        return bp[0];
                                    }
                                    ap.a("H\u00e3y \u0111\u1ee9ng Tr\u01b0\u1eddng \u0111\u1ec3 auto l\u00e0m ti\u00ean n\u1eef");
                                }
                                return bp[2];
                            }
                            if (ey.m(ap.an.size()) && ey.d(((dg)ap.an.firstElement()).d, ec.n().w)) {
                                bu bu11;
                                bu bu12;
                                if (ey.n(n2) && ey.f(n2, by.h.length)) {
                                    bu12 = by.h[n2];
                                    "".length();
                                    if (null != null) {
                                        return ((0x6E ^ 0x3F) & ~(0xD5 ^ 0x84)) != 0;
                                    }
                                } else {
                                    bu12 = null;
                                }
                                bu bu13 = bu11 = bu12;
                                if (ey.b(bu11)) {
                                    ap.a("Stanima nh\u00f3m all");
                                    ey.c(bp[1], de.i);
                                    "".length();
                                    if (null != null) {
                                        return ((0x1E ^ 0x44) & ~(0x56 ^ 0xC)) != 0;
                                    }
                                } else {
                                    ap.a("Stanima nh\u00f3m " + bu13.j + " id " + n2);
                                    ey.c(bu13.h, de.i);
                                }
                                ey.aj.j = bp[0];
                                dl.h().l("st " + ey.aj.u + " " + ey.aj.x + " " + ey.aj.b);
                                return bp[0];
                            }
                            ap.a("Ch\u01b0a c\u00f3 nh\u00f3m ho\u1eb7c b\u1ea1n kh\u00f4ng l\u00e0 nh\u00f3m tr\u01b0\u1edfng");
                            return bp[0];
                        }
                        ap.a("End Auto");
                        ey.q();
                        if (ey.l(ec.ct ? 1 : 0)) {
                            dl.h().l("pe");
                        }
                        return bp[0];
                    }
                    ap.a("Ph\u00e1 b\u0103ng");
                    t = bp[2];
                    bf = bp[2];
                    return bp[0];
                }
                ap.a("B\u0103ng skill");
                bf = bp[0];
                return bp[0];
            }
            ap.a("B\u0103ng boss");
            t = bp[0];
            return bp[0];
        }
        ap.a("\u0110\u00f3ng b\u0103ng");
        t = bp[0];
        bf = bp[0];
        return bp[0];
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static void h(String string) {
        String[] stringArray = ey.b(string, " ");
        int[] nArray = new int[stringArray.length];
        int n2 = bp[2];
        while (ey.f(n2, stringArray.length)) {
            try {
                nArray[n2] = Integer.parseInt(stringArray[n2]);
                "".length();
            }
            catch (Exception exception) {
                nArray[n2] = bp[1];
            }
            if ((39 + 51 - -1 + 71 ^ 18 + 16 - -24 + 108) < 0) {
                return;
            }
            ++n2;
            "".length();
            if (" ".length() <= " ".length()) continue;
            return;
        }
        ac = nArray;
    }

    public static void c(String string, String string2) {
        b.f().a(string, ec.n().cx, string2);
        dl.h().a(string, string2);
        ax.a(20L);
    }

    private static boolean o(int n2) {
        return n2 == 0;
    }

    public static void k() {
        bo = bp[2];
        if (ey.a(bm)) {
            bo.ai();
            bm.interrupt();
        }
    }

    private static boolean p(int n2) {
        return n2 < 0;
    }

    public static void l() {
        ao.c();
        ey.a(ao);
    }

    private static void y() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            String string;
            if (ey.b((Object)v)) {
                string = "";
                "".length();
                if ("   ".length() < "   ".length()) {
                    return;
                }
            } else {
                string = v;
            }
            dataOutputStream.writeUTF(string);
            dataOutputStream.writeByte(q.size());
            int n2 = bp[2];
            while (ey.f(n2, q.size())) {
                dataOutputStream.writeUTF((String)q.elementAt(n2));
                ++n2;
                "".length();
                if ("  ".length() != 0) continue;
                return;
            }
            dataOutputStream.writeInt(au.size());
            n2 = bp[2];
            while (ey.f(n2, au.size())) {
                dataOutputStream.writeUTF((String)au.elementAt(n2));
                ++n2;
                "".length();
                if (-(0x1C ^ 0x18) < 0) continue;
                return;
            }
            dataOutputStream.flush();
            byteArrayOutputStream.flush();
            an.a("V6Group", byteArrayOutputStream.toByteArray());
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ("   ".length() <= ((0x21 ^ 0x2F) & ~(0x28 ^ 0x26))) {
            return;
        }
    }

    private static boolean h(int n2, int n3) {
        return n2 > n3;
    }

    public static void d(String string, String string2) {
        if (ey.l(ec.ct ? 1 : 0) && ey.a((Object)v) && ey.l(string.equals(v) ? 1 : 0) && ey.o(ec.n().cx.equals(v) ? 1 : 0)) {
            String[] stringArray = ey.b(string2, " ");
            try {
                if (ey.l(stringArray[bp[2]].equals("dcvt") ? 1 : 0)) {
                    int n2;
                    if (ey.d(Integer.parseInt(stringArray[bp[0]]), bp[0])) {
                        n2 = bp[0];
                        "".length();
                        if (((0x8C ^ 0x94) & ~(0x66 ^ 0x7E)) >= " ".length()) {
                            return;
                        }
                    } else {
                        n2 = bp[2];
                    }
                    av = n2;
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("avt") ? 1 : 0)) {
                    ap.a("Th\u00eam v\u1ecb tr\u00ed " + az.size());
                    az.addElement(Integer.valueOf(stringArray[bp[0]]));
                    an.addElement(Integer.valueOf(stringArray[bp[11]]));
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("dvt") ? 1 : 0)) {
                    ap.a("X\u00f3a h\u1ebft v\u1ecb tr\u00ed");
                    az.removeAllElements();
                    an.removeAllElements();
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("pe") ? 1 : 0)) {
                    ap.a("End Auto");
                    bo.ai();
                    u = null;
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("tsa") ? 1 : 0)) {
                    if (ey.a(u, by)) {
                        ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                        ey.by.x = Integer.parseInt(stringArray[bp[11]]);
                        return;
                    }
                    ca.a(bp[1], Integer.parseInt(stringArray[bp[0]]), Integer.parseInt(stringArray[bp[11]]));
                    ey.ca.j = bp[0];
                    ey.a(ca);
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("ts") ? 1 : 0)) {
                    if (ey.a(u, by)) {
                        ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                        ey.by.x = Integer.parseInt(stringArray[bp[11]]);
                        return;
                    }
                    ca.a(Integer.parseInt(stringArray[bp[12]]), Integer.parseInt(stringArray[bp[0]]), Integer.parseInt(stringArray[bp[11]]));
                    ey.ca.j = bp[0];
                    ey.a(ca);
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("att") ? 1 : 0)) {
                    if (ey.a(u, by)) {
                        ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                        ey.by.x = Integer.parseInt(stringArray[bp[11]]);
                        return;
                    }
                    int n3 = Integer.parseInt(stringArray[bp[0]]);
                    int n4 = Integer.parseInt(stringArray[bp[12]]);
                    y y2 = ec.n(bp[0]);
                    if (ey.a(y2) && ey.d(y2.d, n3)) {
                        ao.c();
                        "".length();
                        if ((0x75 ^ 0x73 ^ "  ".length()) <= "   ".length()) {
                            return;
                        }
                    } else {
                        ao.c(n3, n4);
                    }
                    ey.ao.x = Integer.parseInt(stringArray[bp[11]]);
                    ey.ao.j = bp[0];
                    ey.a(ao);
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("sta") ? 1 : 0)) {
                    if (ey.a(u, by)) {
                        ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                        ey.by.x = Integer.parseInt(stringArray[bp[11]]);
                        return;
                    }
                    aj.a(bp[1], Integer.parseInt(stringArray[bp[0]]), Integer.parseInt(stringArray[bp[11]]), bp[2], bp[2]);
                    ey.aj.j = bp[0];
                    ey.a(aj);
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("st") ? 1 : 0)) {
                    if (ey.a(u, by)) {
                        ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                        ey.by.x = Integer.parseInt(stringArray[bp[11]]);
                        return;
                    }
                    aj.a(Integer.parseInt(stringArray[bp[12]]), Integer.parseInt(stringArray[bp[0]]), Integer.parseInt(stringArray[bp[11]]), bp[2], bp[2]);
                    ey.aj.j = bp[0];
                    ey.a(aj);
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("hd9x") ? 1 : 0)) {
                    ey.a(new ez());
                    return;
                }
                if (ey.l(stringArray[bp[2]].equals("pkms") ? 1 : 0)) {
                    if (ey.l(u instanceof cw)) {
                        cw cw2 = (cw)u;
                        ((cw)u).u = Integer.parseInt(stringArray[bp[0]]);
                        cw2.a = Integer.parseInt(stringArray[bp[11]]);
                        cw2.b = bp[12];
                        return;
                    }
                } else if (ey.l(stringArray[bp[2]].equals("pkes") ? 1 : 0)) {
                    if (ey.l(u instanceof cw)) {
                        ((cw)ey.u).b = bp[13];
                        return;
                    }
                } else {
                    if (ey.l(stringArray[bp[2]].equals("pkm") ? 1 : 0)) {
                        ax ax2;
                        if (ey.a(u, by)) {
                            ey.by.u = Integer.parseInt(stringArray[bp[0]]);
                            return;
                        }
                        if (ey.l(u instanceof aq)) {
                            ax2 = ey.u.q;
                            "".length();
                            if ("  ".length() <= " ".length()) {
                                return;
                            }
                        } else {
                            ax2 = u;
                        }
                        ax ax3 = ax2;
                        ey.a(new aq(Integer.parseInt(stringArray[bp[0]])));
                        ey.u.q = ax3;
                        return;
                    }
                    if (ey.l(stringArray[bp[2]].equals("pkk") ? 1 : 0)) {
                        if (!ey.o(u instanceof aq) || ey.a(u, by)) {
                            ey.u.x = Integer.parseInt(stringArray[bp[0]]);
                            return;
                        }
                    } else if (ey.l(stringArray[bp[2]].equals("pke") ? 1 : 0)) {
                        if (ey.l(u instanceof aq)) {
                            ey.i();
                            return;
                        }
                    } else if (ey.a((Object)u)) {
                        if (ey.l(stringArray[bp[2]].equals("map") ? 1 : 0)) {
                            ey.u.u = Integer.parseInt(stringArray[bp[0]]);
                            return;
                        }
                        if (ey.l(stringArray[bp[2]].equals("khu") ? 1 : 0)) {
                            ey.u.x = Integer.parseInt(stringArray[bp[0]]);
                            return;
                        }
                        if (ey.l(u instanceof df)) {
                            if (ey.l(stringArray[bp[2]].equals("waitGr") ? 1 : 0)) {
                                df.a = System.currentTimeMillis();
                                df.b = bp[0];
                                return;
                            }
                            if (ey.l(stringArray[bp[2]].equals("notifyGr") ? 1 : 0)) {
                                df.b = bp[2];
                                return;
                            }
                        } else if (ey.l(u instanceof ak) && ey.l(stringArray[bp[2]].equals("sts") ? 1 : 0)) {
                            aj.d();
                            return;
                        }
                    }
                }
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public final void m() {
        if (ey.o(bo ? 1 : 0)) {
            if (ey.a((Object)u)) {
                u.b();
            }
            bq = System.currentTimeMillis();
            bo = bp[0];
            bm = new Thread(this);
            bm.start();
            ci.a("", bp[8], doa.r);
        }
    }

    public static void b(int n2, int n3) {
        Integer n4 = new Integer(n2);
        if (ey.o(y.contains(n4) ? 1 : 0)) {
            y.addElement(n4);
            aq.addElement(new Integer(n3));
        }
    }

    static {
        ey.v();
        bv = 0L;
        br = new Random();
        bj = 0L;
        ey.w();
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public static boolean f(int n2) {
        return bh.contains(new Integer(n2));
    }

    public static ad n() {
        ad ad2 = new ad();
        int n2 = bp[2];
        while (ey.f(n2, y.size())) {
            int n3 = (Integer)y.elementAt(n2);
            int n4 = (Integer)aq.elementAt(n2);
            bf bf2 = t.b((short)n3);
            ad2.addElement(n2 + ". " + bf2.e + " id " + n3 + " gi\u00e1 " + n4);
            ++n2;
            "".length();
            if (((0xC0 ^ 0x9A) & ~(0x1F ^ 0x45)) == ((0x66 ^ 0x73) & ~(0x2C ^ 0x39))) continue;
            return null;
        }
        return ad2;
    }

    private static void b(boolean bl2, boolean bl3) {
        aj.a(bp[1], (int)de.i, (int)de.ab, bl2, bl3);
        ey.aj.j = bp[0];
        ey.a(aj);
    }

    public static void o() {
        int n2 = bp[2];
        while (ey.f(n2, m.length)) {
            if (ey.m(m[n2])) {
                int n3 = bp[2];
                while (ey.g(n3, n2)) {
                    if (ey.d(m[n3], bp[1])) {
                        short[] sArray = m;
                        sArray[n3] = sArray[n2];
                        ey.m[n2] = bp[1];
                        "".length();
                        if (null == null) break;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ("  ".length() >= 0) continue;
                    return;
                }
            }
            ++n2;
            "".length();
            if ("  ".length() != 0) continue;
            return;
        }
    }

    public static void g(el el2) {
        if (ey.o(cb.contains(el2) ? 1 : 0)) {
            cb.addElement(el2);
        }
    }

    public static void p() {
        int n2 = bp[2];
        while (ey.f(n2, e.length)) {
            if (ey.m(e[n2])) {
                int n3 = bp[2];
                while (ey.g(n3, n2)) {
                    if (ey.d(e[n3], bp[1])) {
                        short[] sArray = e;
                        sArray[n3] = sArray[n2];
                        ey.e[n2] = bp[1];
                        "".length();
                        if (null == null) break;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ("   ".length() != "  ".length()) continue;
                    return;
                }
            }
            ++n2;
            "".length();
            if ((0x2F ^ 0x2A) != 0) continue;
            return;
        }
    }

    private static int d(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public static void i(String string) {
        if (ey.n(ey.d(System.currentTimeMillis() - bv, 5000L))) {
            bv = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            string = "@" + (bp[9] + br.nextInt(bp[63])) + " " + string + " " + calendar.get(bp[62]) + ":" + calendar.get(bp[41]) + ":" + calendar.get(bp[44]);
            dl.h().e(string);
        }
    }

    public static void q() {
        bo.ai();
        u = null;
    }

    public static void g(int n2) {
        Integer n3 = new Integer(n2);
        if (ey.o(bh.contains(n3) ? 1 : 0)) {
            bh.addElement(n3);
        }
    }

    public static void r() {
        if (ey.m(ey.a(System.currentTimeMillis() - bj, 60000L))) {
            bj = System.currentTimeMillis();
            ad ad2 = new ad();
            ad2.addElement(ec.n());
            dl.h().a(new ad(), ad2, bp[11]);
        }
    }

    public static int h(int n2) {
        int n3;
        if (ey.n(n2 = y.indexOf(new Integer(n2)))) {
            n3 = (Integer)aq.elementAt(n2);
            "".length();
            if (((0x3D ^ 0x13 ^ (0x49 ^ 0x35)) & (0xD1 ^ 0x9D ^ (0x1B ^ 5) ^ -" ".length())) != ((0x14 ^ 0x2D ^ (0x1E ^ 0x44)) & (0x69 ^ 0x58 ^ (0x66 ^ 0x34) ^ -" ".length()))) {
                return (134 + 105 - 206 + 169 ^ 113 + 35 - 93 + 73) & (77 + 141 - 74 + 86 ^ 1 + 142 - -8 + 21 ^ -" ".length());
            }
        } else {
            n3 = bp[2];
        }
        return n3;
    }

    private static boolean i(int n2, int n3) {
        return n2 != n3;
    }

    public static void h(el el2) {
        cb.removeElement(el2);
        "".length();
    }

    public static void i(int n2) {
        if (ey.n(n2 = y.indexOf(new Integer(n2)))) {
            y.removeElementAt(n2);
            aq.removeElementAt(n2);
        }
    }
}

