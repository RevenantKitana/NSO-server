/*
 * Decompiled with CFR 0.152.
 */
public final class aa
extends ef
implements bv {
    private static fr[][] e;
    public static String a;
    private static String[] f;
    public static String b;
    private int g;
    private int h;
    private int i;
    private int j = q[0];
    public static String c;
    private static fr k;
    private static fr l;
    private static fr m;
    private static fr n;
    public static String d;
    private int o;
    private static fr p;
    private static int[] q;

    static {
        aa.i();
        b = "";
        d = "";
        m = null;
        k = null;
        n = null;
        p = null;
        e = null;
        a = an.a("acc");
        c = an.a("pass");
        if (aa.b(a)) {
            a = "";
        }
        if (aa.b(c)) {
            c = "";
        }
    }

    @Override
    public void d() {
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    @Override
    public final void a(bh bh2) {
        bh2.a(q[1]);
        bh2.e(q[1], q[1], em.aj, em.aq);
        em.b(bh2);
        bh2.a(et.a, em.r - et.a.getWidth() / q[2], this.h - q[25] - et.a.getHeight() / q[2], q[1]);
        if (aa.f(em.a.c, q[0])) {
            em.a.c = q[1];
        }
        int n2 = this.h + q[26];
        int n3 = q[1];
        while (aa.d(n3, f.length)) {
            bh2.a(br.e);
            bh2.e(this.o + q[15], n2 + n3 * q[26], this.i - q[27], q[28]);
            br.c(this.o + q[15], n2 + n3 * q[26], this.i - q[27], q[28], bh2);
            if (aa.f(n3, this.j)) {
                bh2.a(br.f);
                bh2.e(this.o + q[15], n2 + n3 * q[26], this.i - q[27], q[28]);
                br.c(this.o + q[15], n2 + n3 * q[26], this.i - q[27], q[28], bh2);
            }
            if (aa.d(n3, f.length)) {
                if (aa.c(a.equals("") ? 1 : 0) && aa.c(b.equals("") ? 1 : 0)) {
                    if (aa.f(n3, q[2])) {
                        doa.v.a(bh2, String.valueOf(f[n3]) + be.b[em.a.c], this.o + this.i / q[2], n2 + n3 * q[26] + q[29], q[2]);
                        "".length();
                        if ("  ".length() <= 0) {
                            return;
                        }
                    } else {
                        doa.v.a(bh2, f[n3], this.o + this.i / q[2], n2 + n3 * q[26] + q[29], q[2]);
                        "".length();
                        if ("  ".length() == " ".length()) {
                            return;
                        }
                    }
                } else if (aa.b(n3)) {
                    String string;
                    doa doa2 = doa.v;
                    StringBuffer stringBuffer = new StringBuffer().append(f[n3]);
                    if (aa.b(b.equals("") ? 1 : 0)) {
                        string = ": " + b;
                        "".length();
                    } else if (aa.c(a.startsWith("tmpusr") ? 1 : 0)) {
                        string = "";
                        "".length();
                        if (((0x61 ^ "   ".length()) & (0x62 ^ -" ".length())) > 0) {
                            return;
                        }
                    } else {
                        string = ": " + a;
                    }
                    doa2.a(bh2, stringBuffer.append(string).toString(), this.o + this.i / q[2], n2 + n3 * q[26] + q[29], q[2]);
                    "".length();
                } else if (aa.f(n3, q[24])) {
                    doa.v.a(bh2, String.valueOf(f[n3]) + be.b[em.a.c], this.o + this.i / q[2], n2 + n3 * q[26] + q[29], q[2]);
                    "".length();
                    if (-"  ".length() > 0) {
                        return;
                    }
                } else {
                    doa.v.a(bh2, f[n3], this.o + this.i / q[2], n2 + n3 * q[26] + q[29], q[2]);
                }
            }
            ++n3;
            "".length();
            if ("  ".length() >= -" ".length()) continue;
            return;
        }
        if (aa.b(em.d)) {
            br.a(bh2, this.ft, this.fx, this.fw);
        }
        super.a(bh2);
    }

    private static boolean a(int n2, int n3) {
        return n2 <= n3;
    }

    @Override
    public final void e() {
        if (aa.c(a.equals("") ? 1 : 0) && aa.c(b.equals("") ? 1 : 0)) {
            if (aa.a(this.j) && aa.d(this.j, e[q[1]].length)) {
                this.fx = e[q[1]][this.j];
                "".length();
                if (-"  ".length() >= 0) {
                    return;
                }
            }
        } else if (aa.a(this.j) && aa.d(this.j, e[q[6]].length)) {
            this.fx = e[q[6]][this.j];
        }
        if (aa.b(ap.fb += q[6], em.aj * q[24] + q[7])) {
            ap.fb = q[7];
        }
        super.e();
    }

    @Override
    public void b_() {
    }

    public static boolean g() {
        int n2;
        if (aa.a(a) && (!aa.b(a.startsWith("tmpusr") ? 1 : 0) || aa.c(a.equals("") ? 1 : 0))) {
            n2 = q[6];
            "".length();
            if ((0x61 & (0x61 ^ -" ".length())) != 0) {
                return (0x55 & (0x55 ^ -" ".length())) != 0;
            }
        } else {
            n2 = q[1];
        }
        return n2 != 0;
    }

    private static void i() {
        int[] nArray = new int[36];
        q = nArray;
        nArray[0] = -" ".length();
        aa.q[1] = 0;
        aa.q[2] = "  ".length();
        aa.q[3] = 5;
        aa.q[4] = 6;
        aa.q[5] = 4;
        aa.q[6] = " ".length();
        aa.q[7] = 100;
        aa.q[8] = 170;
        aa.q[9] = 175;
        aa.q[10] = 128;
        aa.q[11] = 208;
        aa.q[12] = 126;
        aa.q[13] = 160;
        aa.q[14] = 250;
        aa.q[15] = 10;
        aa.q[16] = 8885;
        aa.q[17] = 320;
        aa.q[18] = 88;
        aa.q[19] = 1000;
        aa.q[20] = 1001;
        aa.q[21] = 1002;
        aa.q[22] = 1003;
        aa.q[23] = 1004;
        aa.q[24] = "   ".length();
        aa.q[25] = 15;
        aa.q[26] = 35;
        aa.q[27] = 20;
        aa.q[28] = 28;
        aa.q[29] = 8;
        aa.q[30] = 12;
        aa.q[31] = 9;
        aa.q[32] = 10001;
        aa.q[33] = 8882;
        aa.q[34] = 10004;
        aa.q[35] = 20000;
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    @Override
    public final void a(int n2, Object object) {
        switch (n2) {
            case 1000: {
                if (aa.c(aa.g() ? 1 : 0) && aa.b(a.equals("") ? 1 : 0)) {
                    em.a(ag.oh, new fr(ag.hx, this, q[32], null), new fr(ag.bd, em.f, q[33], null));
                    return;
                }
                aa.j();
                dl.h().a("-1", "12345", "1.8.0");
                return;
            }
            case 1001: {
                if (aa.c(aa.g() ? 1 : 0) && aa.b(a.equals("") ? 1 : 0) && aa.c(b.equals("") ? 1 : 0)) {
                    em.a(ag.oh, new fr(ag.iu, this, q[34], null), new fr(ag.bd, em.f, q[33], null));
                    return;
                }
                em.l.c();
                return;
            }
            case 1002: {
                ad ad2 = new ad();
                int n3 = q[1];
                while (aa.d(n3, be.c.length)) {
                    ad2.addElement(new fr(be.b[be.c[n3]], this, n3 + q[35], null));
                    ++n3;
                    "".length();
                    if ((0x60 & (0x60 ^ -" ".length())) <= 4) continue;
                    return;
                }
                em.a.a(ad2);
                if (aa.e(an.d("indServer"), q[0]) && aa.b(em.w ? 1 : 0)) {
                    em.a.c = an.d("indServer");
                }
                return;
            }
            case 1003: {
                aa.j();
                if (aa.b(b.equals("") ? 1 : 0)) {
                    a = b;
                    c = d;
                    b = "";
                    d = "";
                    an.a("acc", a);
                    an.a("pass", c);
                }
                dl.h().a(a, c, "1.8.0");
                return;
            }
            case 1004: {
                String string;
                int n4 = be.a() ? 1 : 0;
                dp.g().c();
                em.i();
                em.k();
                if (aa.c(n4)) {
                    string = ag.ff;
                    "".length();
                    if ("  ".length() != "  ".length()) {
                        return;
                    }
                } else {
                    string = ag.mr;
                }
                em.a(string, q[33]);
                return;
            }
            case 10001: {
                aa.j();
                dl.h().a("-1", "12345", "1.8.0");
                if (!aa.b(b.equals("") ? 1 : 0)) break;
                a = b;
                c = d;
                b = "";
                d = "";
                an.a("acc", a);
                an.a("pass", c);
                return;
            }
        }
        if (aa.c(n2, q[35]) && aa.d(n2, q[35] + be.c.length)) {
            int n5 = be.c[n2 - q[35]];
            em.a.m = q[1] != 0;
            fl.c = be.e[n5];
            fl.e = be.a[n5];
            fl.h = be.d[n5];
            an.a("indServer", be.c[n5]);
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    @Override
    public final void c() {
        String[] stringArray;
        ap.bv = em.aq;
        if (aa.f(em.b, q[2])) {
            em.c(q[1]);
        } else {
            em.c(de.ae);
        }
        super.c();
        if (aa.a(ap.dc)) {
            ap.dc = null;
        }
        if (!aa.e(de.ae = (byte)(System.currentTimeMillis() % 9L), q[3]) || aa.f(de.ae, q[4])) {
            de.ae = (byte)q[5];
        }
        ap.a(q[6] != 0);
        ap.fb = q[7];
        this.i = q[8];
        this.g = q[9];
        if (!aa.e(em.aj, q[10]) || aa.a(em.aq, q[11])) {
            this.i = q[12];
            this.g = q[13];
        }
        this.o = em.aj / q[2] - this.i / q[2];
        this.h = em.aq / q[2] - this.g / q[2];
        if (aa.a(em.aq, q[14])) {
            this.h -= q[15];
        }
        this.fw = new fr(ag.mx, em.f, q[16], null);
        this.j = q[0];
        if (aa.b(em.w ? 1 : 0)) {
            this.j = q[1];
        }
        if (aa.c(em.w ? 1 : 0) && aa.c(em.aj, q[17])) {
            this.fw.h = em.aj / q[2] + q[18];
        }
        if (aa.b(m)) {
            String string;
            String string2;
            String string3;
            String string4;
            if (aa.c(em.w ? 1 : 0)) {
                stringArray = "";
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            } else {
                stringArray = ag.v;
            }
            m = new fr((String)stringArray, this, q[19], null);
            if (aa.c(em.w ? 1 : 0)) {
                string4 = "";
                "".length();
                if (" ".length() <= -" ".length()) {
                    return;
                }
            } else {
                string4 = ag.v;
            }
            k = new fr(string4, this, q[20], null);
            if (aa.c(em.w ? 1 : 0)) {
                string3 = "";
                "".length();
            } else {
                string3 = ag.v;
            }
            p = new fr(string3, this, q[21], null);
            if (aa.c(em.w ? 1 : 0)) {
                string2 = "";
                "".length();
                if (-"  ".length() > 0) {
                    return;
                }
            } else {
                string2 = ag.v;
            }
            n = new fr(string2, this, q[22], null);
            if (aa.c(em.w ? 1 : 0)) {
                string = "";
                "".length();
            } else {
                string = ag.v;
            }
            l = new fr(string, this, q[23], null);
            fr[][] frArrayArray = new fr[q[2]][];
            int n2 = q[1];
            fr[] frArray = new fr[]{m, k};
            frArrayArray[n2] = frArray;
            int n3 = q[6];
            fr[] frArray2 = new fr[]{n, m, k};
            frArrayArray[n3] = frArray2;
            e = frArrayArray;
        }
        if ((!aa.a(a) || aa.c(a.equals("") ? 1 : 0)) && aa.c(b.equals("") ? 1 : 0)) {
            stringArray = new String[]{ag.ey, ag.hg};
            f = stringArray;
            "".length();
            if (" ".length() <= -" ".length()) {
                return;
            }
        } else {
            stringArray = new String[]{ag.hx, ag.ey, ag.hg};
            stringArray[aa.q[1]] = ag.hx;
            stringArray[aa.q[6]] = ag.ey;
            stringArray[aa.q[2]] = ag.hg;
            f = stringArray;
        }
        em.a.c = be.c[q[1]];
        fl.c = be.e[em.a.c];
        int n4 = q[1];
        while (aa.d(n4, be.c.length)) {
            if (!aa.e(an.d("indServer"), be.c[n4])) {
                em.a.c = be.c[n4];
                fl.c = be.e[n4];
                return;
            }
            "".length();
            ++n4;
            "".length();
        }
        if (aa.b(an.a("random"))) {
            an.a("random", aa.h());
        }
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    public static String h() {
        String string = "";
        int n2 = q[1];
        while (aa.d(n2, q[30])) {
            string = String.valueOf(string) + Integer.toString(fo.a(q[1], q[31]));
            ++n2;
            "".length();
            if (("   ".length() & ~"   ".length()) < "  ".length()) continue;
            return null;
        }
        return string;
    }

    public aa() {
        em.a.c = be.c[q[1]];
        fl.c = be.e[em.a.c];
        int n2 = q[1];
        while (aa.d(n2, be.c.length)) {
            if (!aa.e(an.d("indServer"), be.c[n2])) {
                em.a.c = be.c[n2];
                fl.c = be.e[n2];
                return;
            }
            "".length();
            ++n2;
            "".length();
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    @Override
    public final void f() {
        if (aa.b(em.j[q[2]] ? 1 : 0) && aa.b(em.j[q[5]] ? 1 : 0)) {
            if (!aa.b(em.j[q[29]] ? 1 : 0) || aa.c(em.j[q[4]] ? 1 : 0)) {
                this.j += q[6];
                if (aa.b(this.j, f.length - q[6])) {
                    this.j = q[1];
                    "".length();
                }
            }
        } else {
            this.j -= q[6];
            if (aa.d(this.j)) {
                this.j = f.length - q[6];
            }
        }
        if (aa.c(em.q ? 1 : 0) && aa.c(em.c(this.o + q[15], this.h + q[26], this.i - q[15], q[9]) ? 1 : 0)) {
            if (aa.c(em.am ? 1 : 0)) {
                this.j = (em.an - (this.h + q[26])) / q[26];
            }
            if (aa.c(a.equals("") ? 1 : 0) && aa.c(b.equals("") ? 1 : 0)) {
                if (aa.a(this.j) && aa.d(this.j, e[q[1]].length)) {
                    e[q[1]][this.j].c();
                    "".length();
                    if ((0x15 & (0x15 ^ -" ".length())) >= "   ".length()) {
                        return;
                    }
                }
            } else if (aa.a(this.j) && aa.d(this.j, e[q[6]].length)) {
                e[q[6]][this.j].c();
            }
        }
        super.f();
        em.h();
    }

    private static boolean d(int n2) {
        return n2 < 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    @Override
    public void b() {
    }

    private static void j() {
        if (aa.b(dp.g().n ? 1 : 0)) {
            em.q();
        }
        em.r();
    }

    @Override
    public void a(String string, String string2) {
    }

    private static boolean b(Object object) {
        return object == null;
    }

    @Override
    public void a(eh eh2) {
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean f(int n2, int n3) {
        return n2 == n3;
    }
}

