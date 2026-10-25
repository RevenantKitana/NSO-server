/*
 * Decompiled with CFR 0.152.
 */
public final class ar
extends as
implements bv {
    private static final int[] f;
    private String[] g;
    private int h;
    public eg a;
    private int i;
    public eg b;
    private int j;
    private int k;

    public final void a(String string, fr fr2, fr fr3, int n2, int n3) {
        this.a.a("");
        this.a.o = n2;
        this.b.a("");
        this.b.o = n3;
        this.g = doa.b.a(string, em.aj - this.h * f[10]);
        ad ad2 = doa.b.b(string, em.aj - this.h * f[10]);
        this.i = ad2.size();
        this.c = fr2;
        this.e = fr3;
        em.d = this;
    }

    public final void a(int n2, Object object) {
    }

    public ar() {
        new fr("", f[0], null);
        "".length();
        this.a = new eg();
        this.b = new eg();
    }

    public final void a(bh bh2) {
        br.a(bh2, this.h, em.aq - f[11] - ef.fs - this.i * f[12], em.aj - (this.h << f[8]), f[13] + this.i * f[12], this.g);
        doa.b.a(bh2, this.a.f + ": ", this.a.j - this.j - f[14], this.a.s + f[14], f[2]);
        this.a.a(bh2);
        bh2.a(f[2], f[2], em.aj, em.aq);
        doa.b.a(bh2, this.b.f + ": ", this.b.j - this.j - f[14], this.b.s + f[14], f[2]);
        this.b.a(bh2);
        super.a(bh2);
    }

    static {
        ar.c();
    }

    public void b() {
    }

    private static void c() {
        f = new int[20];
        ar.f[0] = -(0xFFFF9E7A & 0x7197) & (0xFFFFB7FD & 0x5BFB);
        ar.f[1] = 0x99 ^ 0xB1;
        ar.f[2] = (0x8C ^ 0x94) & ~(0xAE ^ 0xB6);
        ar.f[3] = 1 + 92 - 84 + 167;
        ar.f[4] = 0x18 ^ 0x12;
        ar.f[5] = "  ".length();
        ar.f[6] = (0x19 ^ 9) & ~(0x1A ^ 0xA) ^ (0x92 ^ 0xA0);
        ar.f[7] = 146 + 46 - 149 + 114 ^ 56 + 45 - 38 + 74;
        ar.f[8] = " ".length();
        ar.f[9] = 171 + 137 - 182 + 54 ^ 16 + 89 - 61 + 115;
        ar.f[10] = "   ".length();
        ar.f[11] = 9 ^ 0x5C;
        ar.f[12] = 0xB5 ^ 0xB8;
        ar.f[13] = 0xD7 ^ 0x87;
        ar.f[14] = 0x9B ^ 0xBE ^ (0xB0 ^ 0x90);
        ar.f[15] = 0xC4 ^ 0xAE ^ (0xED ^ 0x8F);
        ar.f[16] = 152 + 62 - 72 + 18;
        ar.f[17] = 0x35 ^ 0x2F;
        ar.f[18] = 195 + 20 - 178 + 193 ^ 7 + 171 - 118 + 137;
        ar.f[19] = 170 + 118 - 263 + 200 ^ 63 + 72 - 40 + 90;
    }

    private static boolean a(int n2, int n3) {
        return n2 <= n3;
    }

    public final void a(int n2) {
        if (ar.b(this.k)) {
            this.a.b(n2);
            "".length();
            "".length();
            if (null != null) {
                return;
            }
        } else {
            this.b.b(n2);
            "".length();
        }
        super.a(n2);
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    public void a(eh eh2) {
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public void d() {
    }

    public final void a(String string, String string2) {
        int n2;
        if (ar.b(doa.b.a(string), doa.b.a(string2))) {
            n2 = doa.b.a(string);
            "".length();
            if (((0xE5 ^ 0xA8) & ~(0x14 ^ 0x59)) > 0) {
                return;
            }
        } else {
            n2 = doa.b.a(string2);
        }
        this.j = n2;
        this.h = f[1];
        this.k = f[2];
        if (ar.a(em.aj, f[3])) {
            this.h = f[4];
        }
        this.a.j = this.h + this.j + f[4];
        this.a.s = em.aq - f[5] * ef.fu - f[6];
        this.a.d = em.aj - f[5] * this.h - this.j - f[7];
        this.a.q = ef.fu + f[5];
        this.a.a = f[8];
        this.a.f = string;
        this.b.j = this.h + this.j + f[4];
        this.b.s = em.aq - ef.fu - f[9];
        this.b.d = em.aj - f[5] * this.h - this.j - f[7];
        this.b.q = ef.fu + f[5];
        this.b.f = string2;
        this.d = this.a.n;
    }

    public final void a() {
        if (ar.c(em.j[f[5]])) {
            this.k = f[2];
        }
        if (ar.c(em.j[f[15]])) {
            this.k = f[8];
        }
        if (ar.b(this.k)) {
            this.a.a = f[8];
            this.b.a = f[2];
            this.d = this.a.n;
            this.a.f();
            "".length();
            if (-" ".length() > "   ".length()) {
                return;
            }
        } else {
            this.a.a = f[2];
            this.b.a = f[8];
            this.d = this.b.n;
            this.b.f();
        }
        if (ar.c(em.w ? 1 : 0) && ar.c(em.q ? 1 : 0) && ar.c(em.am ? 1 : 0)) {
            if (ar.c(em.c(this.a.j, this.a.s, this.a.d, this.a.q) ? 1 : 0)) {
                this.k = f[2];
            }
            if (ar.c(em.c(this.b.j, this.b.s, this.b.d, this.b.q) ? 1 : 0)) {
                this.k = f[8];
            }
        }
        if (ar.a(this.c)) {
            this.c.h = em.aj / f[5] - f[16];
            this.c.a = em.aq - f[17];
        }
        if (ar.a(this.e)) {
            this.e.h = em.aj / f[5] - f[18];
            this.e.a = em.aq - f[17];
        }
        if (ar.a(this.d)) {
            this.d.h = em.aj / f[5] + f[19];
            this.d.a = em.aq - f[17];
        }
        super.a();
    }

    public void b_() {
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }
}

