/*
 * Decompiled with CFR 0.152.
 */
public final class c
extends ef
implements bv {
    private int a;
    private int b;
    private static final int[] c;
    private int d;
    private int e;
    private int f = c[0];

    private static boolean a(Object object) {
        return object != null;
    }

    private static void g() {
        c = new int[35];
        c.c[0] = -" ".length();
        c.c[1] = "  ".length();
        c.c[2] = (0xC2 ^ 0xA9 ^ (0x6D ^ 0x66)) & (0x6B ^ 0x2F ^ (0x92 ^ 0xB6) ^ -" ".length());
        c.c[3] = 0xAC ^ 0xA9;
        c.c[4] = 0x4B ^ 0x4D;
        c.c[5] = 0x34 ^ 0x30;
        c.c[6] = " ".length();
        c.c[7] = 171 + 51 - 123 + 127 ^ 105 + 130 - 138 + 37;
        c.c[8] = 1 + 55 - -67 + 47;
        c.c[9] = 19 + 57 - 0 + 99;
        c.c[10] = (0xAE ^ 0xC1) + "   ".length() - (0xAA ^ 0xB6) + (0xB6 ^ 0x9C);
        c.c[11] = 28 + 101 - 9 + 17 + (0x7A ^ 0x1D) - (0x24 ^ 0x7C) + (0x63 ^ 0x5B);
        c.c[12] = 0x59 ^ 0x27;
        c.c[13] = 28 + 2 - -120 + 10;
        c.c[14] = 62 + 56 - 100 + 169 + (57 + 168 - 157 + 135) - (7 + 126 - -3 + 13) + (0xA5 ^ 0xAC);
        c.c[15] = 0 ^ 0xA;
        c.c[16] = -(0xFFFFFDBF & 0xA57) & (0xFFFFAFFE & 0x5BFF);
        c.c[17] = -(0xFFFFED1B & 0x1BEF) & (0xFFFFAFFF & 0x7BBF);
        c.c[18] = -(0xFFFFFB7D & 0x6CBA) & (0xFFFFEB77 & 0x7DFF);
        c.c[19] = 0x24 ^ 0x7F ^ (0x41 ^ 0x39);
        c.c[20] = 2 ^ 0x69 ^ (6 ^ 0x35);
        c.c[21] = 0xEA ^ 0x80 ^ (0xC6 ^ 0xB6);
        c.c[22] = 0xB2 ^ 0xBE;
        c.c[23] = 0x4B ^ 0x4C;
        c.c[24] = 0x83 ^ 0x85 ^ (0x83 ^ 0x93);
        c.c[25] = 0xB2 ^ 0xAA;
        c.c[26] = 106 + 76 - 49 + 14 ^ 44 + 72 - 42 + 87;
        c.c[27] = 0x20 ^ 0x73 ^ (0x60 ^ 0x27);
        c.c[28] = 0x53 ^ 0x23 ^ (0xF7 ^ 0x9B);
        c.c[29] = -(0xFFFFD36F & 0x3E91) & (0xFFFFDFEB & 0x577B5D);
        c.c[30] = -(0xFFFFF766 & 0x4EDB) & (0xFFFFEFEF & 0xA8FEF9);
        c.c[31] = 0x86 ^ 0x8E;
        c.c[32] = "   ".length();
        c.c[33] = 0x77 ^ 0x6A ^ (0x13 ^ 0x23);
        c.c[34] = 0x80 ^ 0xC6;
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    public void b_() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    static {
        c.g();
    }

    public final void a(bh bh2) {
        bh2.a(c[2]);
        bh2.e(c[2], c[2], em.aj, em.aq);
        em.b(bh2);
        br.b(this.b, this.a, this.d, this.e, bh2);
        bh2.a(br.e);
        bh2.b(em.r - doa.b.a(ag.pd) / c[1] - c[22], this.a + c[23], doa.b.a(ag.pd) + c[24], c[25], c[4], c[4]);
        bh2.a(br.f);
        bh2.a(em.r - doa.b.a(ag.pd) / c[1] - c[22], this.a + c[23], doa.b.a(ag.pd) + c[24], c[25], c[4], c[4]);
        doa.b.a(bh2, ag.pd, em.r, this.a + c[22], c[1]);
        String[] stringArray = ag.dc;
        int n2 = this.a + c[26];
        int n3 = c[2];
        while (c.d(n3, stringArray.length)) {
            bh2.a(br.e);
            bh2.e(this.b + c[15], n2 + n3 * c[19], this.d - c[27], c[28]);
            bh2.a(c[29]);
            bh2.d(this.b + c[15], n2 + n3 * c[19], this.d - c[27], c[28]);
            if (c.e(n3, this.f)) {
                bh2.a(br.f);
                bh2.e(this.b + c[15], n2 + n3 * c[19], this.d - c[27], c[28]);
                bh2.a(c[30]);
                bh2.d(this.b + c[15], n2 + n3 * c[19], this.d - c[27], c[28]);
            }
            doa.v.a(bh2, stringArray[n3], this.b + this.d / c[1], n2 + n3 * c[19] + c[31], c[1]);
            ++n3;
            "".length();
            if ((0x3E ^ 0x19 ^ (0xE4 ^ 0xC6)) > 0) continue;
            return;
        }
        super.a(bh2);
        br.a(bh2, this.ft, this.fx, this.fw);
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 1000: {
                int n3;
                em.d = null;
                if (c.b(this.f)) {
                    n3 = c[2];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    n3 = c[6];
                }
                ag.br = n3;
                n2 = n3;
                an.a("indLanguage", n2);
                ag.a();
                an.d();
                em.i();
                em.au.c();
            }
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public void b() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final void c() {
        String string;
        ap.bv = em.aq;
        if (c.e(em.b, c[1])) {
            em.c(c[2]);
            "".length();
            if (null != null) {
                return;
            }
        } else {
            em.c(de.ae);
        }
        super.c();
        if (c.a(ap.dc)) {
            ap.dc = null;
        }
        if (!c.b(de.ae = (byte)(System.currentTimeMillis() % 9L), c[3]) || c.e(de.ae, c[4])) {
            de.ae = (byte)c[5];
        }
        ap.a(c[6]);
        ap.fb = c[7];
        this.d = c[8];
        this.e = c[9];
        if (!c.b(em.aj, c[10]) || c.f(em.aq, c[11])) {
            this.d = c[12];
            this.e = c[13];
        }
        this.b = em.aj / c[1] - this.d / c[1];
        this.a = em.aq / c[1] - this.e / c[1];
        if (c.f(em.aq, c[14])) {
            this.a -= c[15];
        }
        if (c.a(em.w ? 1 : 0)) {
            string = "";
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        } else {
            string = ag.v;
        }
        this.fx = new fr(string, this, c[16], null);
        this.fw = new fr(ag.mx, em.f, c[17], null);
        this.f = c[0];
        if (c.b(em.w ? 1 : 0)) {
            this.f = c[2];
        }
        if (c.a(em.w ? 1 : 0) && c.a(em.aj, c[18])) {
            this.fx.h = em.aj / c[1] - c[19];
            this.fw.h = em.aj / c[1] + c[20];
            this.fx.a = this.fw.a = em.aq - c[21];
        }
    }

    public void a(eh eh2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }

    public void a(String string, String string2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void d() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static boolean f(int n2, int n3) {
        return n2 <= n3;
    }

    public final void e() {
        if (c.c(ap.fb += c[6], em.aj * c[32] + c[7])) {
            ap.fb = c[7];
        }
        super.e();
    }

    public final void f() {
        if (!c.b(em.j[c[1]]) || !c.b(em.j[c[5]]) || !c.b(em.j[c[4]]) || c.a(em.j[c[31]])) {
            int n2;
            if (c.b(this.f)) {
                n2 = c[6];
                "".length();
                if (((0xCC ^ 0x9C) & ~(0x4C ^ 0x1C)) != 0) {
                    return;
                }
            } else {
                n2 = this.f = c[2];
            }
        }
        if (c.a(em.q ? 1 : 0) && c.a(em.c(this.b + c[15], this.a + c[33], this.d - c[15], c[34]) ? 1 : 0)) {
            if (c.a(em.am ? 1 : 0)) {
                this.f = (em.an - (this.a + c[33])) / c[19];
            }
            this.a(c[16], null);
        }
        super.f();
        em.h();
    }
}

