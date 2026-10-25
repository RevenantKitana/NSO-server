/*
 * Decompiled with CFR 0.152.
 */
public final class w
extends ef
implements bv {
    public String[] a;
    public int[] b;
    public int[] c;
    private int l;
    public byte[] d;
    private int m;
    private int n;
    public int e;
    public boolean f;
    public String[] g;
    private fr o;
    public int[] h;
    private static w p;
    public static String i;
    public int[] j;
    private int q = r[0];
    private static final int[] r;
    public int[] k;
    private int s;
    private int t;

    public static w g() {
        if (w.b(p)) {
            p = new w();
        }
        return p;
    }

    private void h() {
        if (w.a(this.a[this.e])) {
            i = this.a[this.e];
            dl.h().r(this.a[this.e]);
            em.b(ag.ab);
            em.ad = r[2];
            "".length();
            if (-" ".length() >= "  ".length()) {
                return;
            }
        } else {
            cu.g().c();
        }
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    public final void c() {
        de.e();
        System.gc();
        super.c();
        int n2 = r[21];
        while (w.b(n2, this.a.length)) {
            if (w.a(this.a[n2])) {
                this.f = r[21];
                "".length();
                if ("  ".length() > -" ".length()) break;
                return;
            }
            ++n2;
            "".length();
            if ("   ".length() > 0) continue;
            return;
        }
        if (w.e(this.f ? 1 : 0)) {
            cu.g().c();
        }
    }

    public void b_() {
    }

    static {
        w.i();
        i = "";
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    public final void e() {
        if (w.c(ap.fb += r[2], em.aj * r[6] + r[24])) {
            ap.fb = r[24];
        }
        if (w.b(this.m)) {
            this.m -= r[2];
            if (w.c(this.m) && w.d(this.e)) {
                this.h();
            }
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    public void a(eh eh2) {
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 1000: {
                this.h();
                return;
            }
            case 1001: {
                dp dp2 = dp.g();
                ey.k();
                dp2.e();
                em.f.g();
            }
        }
    }

    private static boolean c(int n2) {
        return n2 == 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    public final void a(bh bh2) {
        em.b(bh2);
        int n2 = r[21];
        while (w.b(n2, r[6])) {
            if (w.d(this.e, n2)) {
                br.a(this.s + n2 * (this.q + this.t), this.l, this.q, this.n, bh2);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                br.d(this.s + n2 * (this.q + this.t), this.l, this.q, this.n, bh2);
            }
            br.c(this.s + n2 * (this.q + this.t), this.l, this.q, this.n, bh2);
            ++n2;
            "".length();
            if ((154 + 89 - 55 + 0 ^ 91 + 117 - 150 + 126) >= ((4 ^ 0x38 ^ (0xE6 ^ 0xC4)) & (14 + 77 - -26 + 32 ^ 54 + 49 - 16 + 52 ^ -" ".length()))) continue;
            return;
        }
        n2 = r[21];
        while (w.b(n2, r[6])) {
            if (w.a(this.a[n2])) {
                int n3;
                int n4;
                cz cz2 = ap.ep[this.k[n2]];
                cz cz3 = ap.ep[this.b[n2]];
                cz cz4 = ap.ep[this.c[n2]];
                cz cz5 = ap.ep[this.h[n2]];
                if (w.a(cz2.a) && w.a(cz2.a.length, r[25])) {
                    n4 = r[21];
                    while (w.b(n4, cz2.a.length)) {
                        if (!w.a(cz2.a[n4]) || w.c(bd.a(cz2.a[n4].a) ? 1 : 0)) {
                            ec.n();
                            "".length();
                            cz2 = ec.j(this.d[n2]);
                            "".length();
                            if (-" ".length() >= ("   ".length() & ("   ".length() ^ -" ".length()))) {
                                return;
                            }
                            break;
                        }
                        ++n4;
                        "".length();
                        if (-" ".length() < 0) continue;
                        return;
                    }
                } else {
                    ec.n();
                    "".length();
                    cz2 = ec.j(this.d[n2]);
                }
                n4 = this.s + n2 * (this.q + this.t) + this.q / r[18];
                if (w.c(em.w ? 1 : 0)) {
                    n3 = this.l + this.n / r[18] + r[26];
                    bd.a(bh2, cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].a, n4 + ec.az[r[21]][r[6]][r[2]] + cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].b, n3 - ec.az[r[21]][r[6]][r[18]] + cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].a, n4 + ec.az[r[21]][r[21]][r[2]] + cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].b, n3 - ec.az[r[21]][r[21]][r[18]] + cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].a, n4 + ec.az[r[21]][r[2]][r[2]] + cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].b, n3 - ec.az[r[21]][r[2]][r[18]] + cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].a, n4 + ec.az[r[21]][r[18]][r[2]] + cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].b, n3 - ec.az[r[21]][r[18]][r[18]] + cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].c, r[21], r[21]);
                    if (w.d(this.e, n2)) {
                        doa.b.a(bh2, ag.ge[r[21]] + ": " + this.a[n2], em.r, this.l - r[27], r[18]);
                        doa.v.a(bh2, ag.ge[r[2]] + ": " + this.j[n2], em.r, this.l - r[28], r[18], doa.p);
                        doa.v.a(bh2, this.g[n2], em.r, this.l - r[26], r[18], doa.p);
                        "".length();
                        if (null != null) {
                            return;
                        }
                    }
                } else {
                    n3 = this.l + this.n / r[18] - r[7];
                    bd.a(bh2, cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].a, n4 + ec.az[r[21]][r[6]][r[2]] + cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].b, n3 - ec.az[r[21]][r[6]][r[18]] + cz5.a[ec.az[w.r[21]][w.r[6]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].a, n4 + ec.az[r[21]][r[21]][r[2]] + cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].b, n3 - ec.az[r[21]][r[21]][r[18]] + cz2.a[ec.az[w.r[21]][w.r[21]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].a, n4 + ec.az[r[21]][r[2]][r[2]] + cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].b, n3 - ec.az[r[21]][r[2]][r[18]] + cz3.a[ec.az[w.r[21]][w.r[2]][w.r[21]]].c, r[21], r[21]);
                    bd.a(bh2, cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].a, n4 + ec.az[r[21]][r[18]][r[2]] + cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].b, n3 - ec.az[r[21]][r[18]][r[18]] + cz4.a[ec.az[w.r[21]][w.r[18]][w.r[21]]].c, r[21], r[21]);
                    doa.b.a(bh2, this.a[n2], n4, this.l + this.n / r[18] + r[7], r[18]);
                    doa.v.a(bh2, ag.ge[r[2]] + ": " + this.j[n2], n4, this.l + this.n / r[18] + r[29], r[18]);
                    if (w.c(em.aj, r[9])) {
                        doa.v.a(bh2, this.g[n2], n4, this.l + this.n / r[18] + r[30], r[18]);
                    }
                }
            }
            ++n2;
            "".length();
            if (null == null) continue;
            return;
        }
        super.a(bh2);
    }

    public void d() {
    }

    public void a(String string, String string2) {
    }

    public void b() {
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void f() {
        super.f();
        if (w.e(em.j[r[15]])) {
            this.e += r[2];
            if (w.a(this.e, r[6])) {
                this.e = r[21];
            }
        }
        if (w.e(em.j[r[22]])) {
            this.e -= r[2];
            if (w.a(this.e)) {
                this.e = r[18];
            }
        }
        if (w.e(em.ah ? 1 : 0) && w.e(em.c(this.s, this.l, r[6] * (this.q + this.t), this.n) ? 1 : 0)) {
            int n2 = (em.ae - this.s) / (this.q + this.t);
            if (w.c(n2, r[18])) {
                n2 = r[18];
            }
            if (w.a(n2)) {
                n2 = r[21];
            }
            this.e = n2;
        }
        if (w.e(em.q ? 1 : 0)) {
            if (w.e(em.c(this.s, this.l, r[6] * (this.q + this.t), this.n) ? 1 : 0)) {
                this.m = r[7];
                "".length();
                if ("   ".length() == 0) {
                    return;
                }
            } else {
                this.e = r[23];
            }
        }
        em.m();
        em.h();
    }

    private static boolean d(int n2) {
        return n2 >= 0;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    private static void i() {
        r = new int[31];
        w.r[0] = 0x79 ^ 0x49;
        w.r[1] = 0xEA ^ 0xBF;
        w.r[2] = " ".length();
        w.r[3] = (0x15 ^ 0x7B) + (0x65 ^ 0x2D) - (0x4C ^ 0x17) + (0x10 ^ 0x55);
        w.r[4] = (7 ^ 0x5C) & ~(0x6C ^ 0x37) ^ (0x7E ^ 0x5E);
        w.r[5] = 0x11 ^ 0x16;
        w.r[6] = "   ".length();
        w.r[7] = 9 + 92 - 71 + 114 ^ 72 + 99 - 92 + 70;
        w.r[8] = 0x45 ^ 0x7E ^ (0x79 ^ 0x48);
        w.r[9] = (0x5E ^ 3) + (116 + 121 - 119 + 55) - (0x7B ^ 0) + (0xA1 ^ 0x98);
        w.r[10] = 2 ^ 0x6A ^ (0x83 ^ 0xA1);
        w.r[11] = 7 ^ 0x1E;
        w.r[12] = 0x66 ^ 0x15 ^ (0xDE ^ 0xC3);
        w.r[13] = 0x37 ^ 0x40 ^ (0x52 ^ 0x31);
        w.r[14] = -(0xFFFFBEDA & 0x69B5) & (0xFFFFFDCF & 0x2BFF);
        w.r[15] = 0x30 ^ 0x36;
        w.r[16] = 0xFFFFDBEF & 0x27F8;
        w.r[17] = -(0xFFFFC81F & 0x7FF5) & (0xFFFFFFFF & 0x4BFD);
        w.r[18] = "  ".length();
        w.r[19] = 0x14 ^ 0x4C;
        w.r[20] = 0x72 ^ 0x6A ^ "  ".length();
        w.r[21] = (0x4D ^ 0x19 ^ (0x14 ^ 0x5C)) & (143 + 126 - 189 + 141 ^ 179 + 119 - 281 + 176 ^ -" ".length());
        w.r[22] = 0x55 ^ 0x51;
        w.r[23] = -" ".length();
        w.r[24] = 0x5E ^ 0x3A;
        w.r[25] = 74 + 132 - 108 + 89 ^ 62 + 35 - 72 + 154;
        w.r[26] = 13 + 39 - -79 + 40 ^ 121 + 16 - 86 + 136;
        w.r[27] = 0x2B ^ 6;
        w.r[28] = 0x5E ^ 0x42;
        w.r[29] = 0x3E ^ 0x28;
        w.r[30] = 0xE7 ^ 0xC5;
    }

    public w() {
        this.n = r[1];
        this.f = r[2];
        if (w.b(em.aj, r[3])) {
            this.q = r[4];
        }
        this.t = r[5];
        this.s = (em.aj - r[6] * this.q >> r[2]) - r[7];
        this.l = em.m - (this.n >> r[2]) + r[8];
        if (w.e(em.w ? 1 : 0) && w.c(em.aj, r[9])) {
            this.q = r[10];
            this.t = r[11];
            this.n = r[12];
            this.s = (em.aj - r[6] * this.q >> r[2]) - r[13];
            this.l = em.m - (this.n >> r[2]);
            if (w.b(em.aj, r[14])) {
                this.t = r[15];
                this.s = (em.aj - r[6] * this.q >> r[2]) - r[15];
            }
        }
        this.ft = null;
        this.o = new fr(ag.pa, this, r[16], null);
        this.fx = new fr("", this, r[16], null);
        this.fw = new fr(ag.mx, this, r[17], null);
        this.ft = this.o;
        if (w.e(em.w ? 1 : 0)) {
            this.fx = null;
            this.ft = null;
        }
        if (w.e(em.w ? 1 : 0) && w.a(em.aj, r[14])) {
            this.fw.h = em.aj / r[18] + r[19];
            this.fw.a = em.aq - r[20];
        }
    }
}

