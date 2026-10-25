/*
 * Decompiled with CFR 0.152.
 */
public final class fb {
    int a;
    int b;
    int c;
    private static final int[] j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    public static boolean d;
    public static int e;
    public static String f;
    public static boolean g;
    private int o;
    public static String h;
    int i;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u = j[0];

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static void f() {
        em.af[fb.j[4]] = j[1];
        em.af[fb.j[3]] = j[1];
        em.af[fb.j[2]] = j[1];
        em.af[fb.j[5]] = j[1];
        em.af[fb.j[6]] = j[1];
    }

    public fb() {
        this.k = j[1];
    }

    public static void a(String string) {
        fb.a(string, null, j[15]);
        g = j[4];
    }

    public static void a() {
        h = "";
        f = null;
        g = j[1];
        e = j[1];
        d = j[1];
    }

    static {
        fb.h();
        ce.a(j[26]);
        fb.d();
    }

    private static boolean b(int n2, int n3) {
        return n2 <= n3;
    }

    public static void a(String string, String string2, int n2) {
        if (fb.a((Object)string)) {
            d = j[4];
            h = string;
            f = string2;
            e = n2;
        }
    }

    private static void a(bh bh2) {
        String string = h;
        if (fb.a((Object)de.f)) {
            string = de.f;
        }
        if (fb.a(d ? 1 : 0) && (!fb.a(g ? 1 : 0) || fb.b(e, j[16])) && fb.c(ap.eo ? 1 : 0)) {
            br.b(em.r - j[17], j[18], j[19], j[20], bh2);
            if (fb.a(g ? 1 : 0)) {
                em.a(em.r - doa.b.a(string) / j[3] - j[18], j[21], bh2);
                doa.b.a(bh2, string, em.r + j[22], j[23], j[3]);
                return;
            }
            if (fb.a((Object)f)) {
                doa.b.a(bh2, string, em.r, j[24], j[3]);
                doa.m.a(bh2, f, em.r, j[25], j[3]);
                return;
            }
            doa.b.a(bh2, string, em.r, j[23], j[3]);
        }
    }

    private static void g() {
        if (fb.b(e) && fb.c(e -= j[4])) {
            fb.a();
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    public static void b() {
        d = j[1];
        h = null;
        f = null;
        e = j[1];
        g = j[1];
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static void c() {
        fb.a(ag.ab, null, j[15]);
        g = j[4];
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    private static void h() {
        j = new int[27];
        fb.j[0] = 64 + 24 - -21 + 25 ^ 40 + 76 - 63 + 101;
        fb.j[1] = (0xE5 ^ 0x8B ^ (0xFD ^ 0xC3)) & (132 + 46 - 2 + 59 ^ 13 + 141 - 73 + 106 ^ -" ".length());
        fb.j[2] = "   ".length();
        fb.j[3] = "  ".length();
        fb.j[4] = " ".length();
        fb.j[5] = 0xC4 ^ 0x8C ^ (0x62 ^ 0x2E);
        fb.j[6] = 0xAC ^ 0xAA;
        fb.j[7] = 0xBF ^ 0x94 ^ (0x11 ^ 0x5E);
        fb.j[8] = 0xFFFFD3FC & 0x2D6B;
        fb.j[9] = 0xFFFF8754 & 0x79FF;
        fb.j[10] = 0x48 ^ 0x6E ^ (0x77 ^ 0xB);
        fb.j[11] = 0xFFFFEDA3 & 0x137E;
        fb.j[12] = 230 + 187 - 382 + 215;
        fb.j[13] = (0x3E ^ 0x77) + (0x4F ^ 0x50) - (0xBF ^ 0xB5) + (0x33 ^ 0x59);
        fb.j[14] = 28 + 146 - 167 + 181 ^ 21 + 124 - 25 + 22;
        fb.j[15] = 0xFFFFD78D & 0x3BFA;
        fb.j[16] = 0xFFFF9F7F & 0x73FE;
        fb.j[17] = 0xDE ^ 0x9E;
        fb.j[18] = 0xCF ^ 0xC5;
        fb.j[19] = 35 + 14 - -56 + 23;
        fb.j[20] = 208 + 82 - 125 + 70 ^ 21 + 3 - -28 + 143;
        fb.j[21] = 0x4E ^ 0x63 ^ (0xAB ^ 0x98);
        fb.j[22] = 0x29 ^ 0x2C;
        fb.j[23] = 0x95 ^ 0x82;
        fb.j[24] = 0x4B ^ 0x59;
        fb.j[25] = 0x7E ^ 0x58 ^ (4 ^ 2);
        fb.j[26] = 0x10 ^ 0x1D;
    }

    private static boolean c(int n2) {
        return n2 == 0;
    }

    private static boolean i() {
        block8: {
            if (!fb.c(ec.n().fj, j[2])) break block8;
            int n2 = j[3];
            while (fb.b(n2)) {
                block9: {
                    block7: {
                        try {
                            if (!fb.b(fo.d(em.ag[n2].c - em.ag[n2 - fb.j[4]].c), j[3])) break block7;
                            "".length();
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            return j[4];
                        }
                        if ("  ".length() <= ((2 + 175 - 155 + 190 ^ 44 + 6 - -66 + 13) & (0xFB ^ 0x98 ^ (0x2A ^ 0x1C) ^ -" ".length()))) {
                            return ((0x2D ^ 0x58 ^ (0x75 ^ 0x40)) & (0xC ^ 0x5D ^ (0xA4 ^ 0xB5) ^ -" ".length())) != 0;
                        }
                        break block9;
                    }
                    int n3 = fo.d(em.ag[n2].b - em.ag[n2 - fb.j[4]].b);
                    "".length();
                    if (null != null) {
                        return ((0xC ^ 0x67 ^ (0x17 ^ 0x36)) & (0x8D ^ 0xB9 ^ (0x7A ^ 4) ^ -" ".length())) != 0;
                    }
                    if (fb.a(n3, j[3])) {
                        return j[1];
                    }
                }
                --n2;
                "".length();
                if (-(0x5C ^ 0x59) < 0) continue;
                return ((0xF7 ^ 0xBF) & ~(0x4E ^ 6)) != 0;
            }
        }
        return j[4];
    }

    public static void d() {
    }

    public final void e() {
        if (fb.a(em.ah ? 1 : 0) && fb.c(em.q ? 1 : 0)) {
            this.q = em.ao;
            this.p = em.av;
            if (fb.b(this.q, (em.aj >> j[4]) - j[7]) && fb.d(this.p, em.aq >> j[4])) {
                if (fb.c(this.k ? 1 : 0)) {
                    this.i = this.b = this.q;
                    this.c = this.a = this.p;
                }
                this.k = j[4];
                this.r = em.ae - this.i;
                this.n = em.an - this.c;
                this.t = this.r * this.r + this.n * this.n;
                this.l = fo.a(this.t);
                if (!fb.b(Math.abs(this.r), j[5]) || fb.a(Math.abs(this.n), j[5])) {
                    this.s = fo.b(this.r, this.n);
                    if (fb.c(em.c(this.i - this.u, this.c - this.u, this.u << j[4], this.u << j[4]) ? 1 : 0)) {
                        if (fb.a(this.l)) {
                            this.a = this.n * this.u / this.l;
                            this.b = this.r * this.u / this.l;
                            this.b += this.i;
                            this.a += this.c;
                            if (fb.c(fo.a(this.i - this.u, this.c - this.u, this.u << j[4], this.u << j[4], this.b, this.a) ? 1 : 0)) {
                                this.b = this.o;
                                this.a = this.m;
                                "".length();
                                if (" ".length() > " ".length()) {
                                    return;
                                }
                            } else {
                                this.o = this.b;
                                this.m = this.a;
                                "".length();
                                if ("  ".length() == ((0x40 ^ 0x7E) & ~(0x21 ^ 0x1F))) {
                                    return;
                                }
                            }
                        } else {
                            this.b = this.o;
                            this.a = this.m;
                            "".length();
                            if ((0xEB ^ 0x98 ^ (0x35 ^ 0x42)) <= 0) {
                                return;
                            }
                        }
                    } else {
                        this.b = em.ae;
                        this.a = em.an;
                    }
                    fb.f();
                    if (fb.c(fb.i() ? 1 : 0)) {
                        fb.f();
                        return;
                    }
                    if (fb.b(this.s, j[8]) && !fb.b(this.s, j[9]) || fb.b(this.s) && fb.b(this.s, j[10])) {
                        ap.fi = j[1];
                        em.af[fb.j[6]] = j[4];
                        em.j[fb.j[6]] = j[4];
                        return;
                    }
                    if (fb.a(this.s, j[11]) && fb.b(this.s, j[9])) {
                        ap.fi = j[1];
                        em.af[fb.j[2]] = j[4];
                        em.j[fb.j[2]] = j[4];
                        return;
                    }
                    if (fb.a(this.s, j[12]) && fb.b(this.s, j[11])) {
                        ap.fi = j[1];
                        em.af[fb.j[3]] = j[4];
                        em.j[fb.j[3]] = j[4];
                        return;
                    }
                    if (fb.a(this.s, j[13]) && fb.b(this.s, j[12])) {
                        ap.fi = j[1];
                        em.af[fb.j[4]] = j[4];
                        em.j[fb.j[4]] = j[4];
                        return;
                    }
                    if (fb.a(this.s, j[10]) && fb.b(this.s, j[13])) {
                        ap.fi = j[1];
                        em.af[fb.j[5]] = j[4];
                        em.j[fb.j[5]] = j[4];
                        return;
                    }
                }
            }
        } else {
            this.i = this.b = j[14];
            this.c = this.a = em.aq - j[14];
            this.k = j[1];
            fb.f();
        }
    }
}

