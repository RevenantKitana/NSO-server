/*
 * Decompiled with CFR 0.152.
 */
public final class ae
extends as {
    private static final int[] b;
    private int f = b[0];
    public eg a;
    private String[] g;

    private static boolean a(int n2, int n3) {
        return n2 <= n3;
    }

    public final void a(int n2) {
        this.a.b(n2);
        "".length();
        super.a(n2);
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public ae() {
        if (ae.a(em.aj, b[1])) {
            this.f = b[2];
        }
        this.a = new eg();
        this.a.j = this.f + b[2];
        this.a.s = em.aq - ef.fu - b[3];
        this.a.d = em.aj - b[4] * (this.f + b[2]);
        this.a.q = ef.fu + b[4];
        this.a.a = b[5];
        this.d = this.a.n;
    }

    private static void b() {
        b = new int[13];
        ae.b[0] = 0x19 ^ 0x5A ^ (0x56 ^ 0x3D);
        ae.b[1] = (0xF4 ^ 0xA8) + (92 + 131 - 177 + 94) - (115 + 23 - -63 + 28) + (155 + 68 - 71 + 21);
        ae.b[2] = 43 + 105 - 114 + 112 ^ 86 + 61 - 126 + 131;
        ae.b[3] = 0xBF ^ 0x8F ^ (0xDB ^ 0xC0);
        ae.b[4] = "  ".length();
        ae.b[5] = " ".length();
        ae.b[6] = -(0xFFFFD5CB & 0x7A36) & (0xFFFFF3FB & 0x7EB7);
        ae.b[7] = 104 + 52 - 32 + 36;
        ae.b[8] = 0xBB ^ 0xA1;
        ae.b[9] = 141 + 30 - 44 + 15 ^ 89 + 115 - 187 + 156;
        ae.b[10] = 0x7A ^ 0x22;
        ae.b[11] = 0x21 ^ 0x6C;
        ae.b[12] = 0x82 ^ 0xC7;
    }

    public final void a(String string, fr fr2, int n2) {
        try {
            this.a.a("");
            this.a.o = n2;
            this.g = doa.b.a(string, em.aj - (this.f << b[5]));
            this.c = new fr(ag.pf, em.f(), b[6], null);
            this.e = fr2;
            if (ae.a(this.c)) {
                this.c.h = em.aj / b[4] - b[7];
                this.c.a = em.aq - b[8];
            }
            if (ae.a(this.e)) {
                this.e.h = em.aj / b[4] - b[9];
                this.e.a = em.aq - b[8];
            }
            if (ae.a(this.d)) {
                this.d.h = em.aj / b[4] + b[10];
                this.d.a = em.aq - b[8];
            }
            em.d = this;
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (" ".length() <= 0) {
            return;
        }
    }

    public final void a() {
        this.a.f();
        super.a();
    }

    static {
        ae.b();
    }

    public final void a(bh bh2) {
        br.a(bh2, this.f, em.aq - b[11] - ef.fs, em.aj - (this.f << b[5]), b[12], this.g);
        this.a.a(bh2);
        super.a(bh2);
    }
}

