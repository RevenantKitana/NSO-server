/*
 * Decompiled with CFR 0.152.
 */
public final class am
implements bv {
    public boolean a = j[0];
    private static am h;
    private long i = 0L;
    public eg b;
    public String c;
    public fr d = new fr(ag.kx, this, j[1], j[2], em.aq - ef.fs + j[2]);
    public fr e = new fr(ag.qo, this, j[3], em.aj - j[4], em.aq - ef.fs + j[2]);
    private static final int[] j;
    public bv f;
    public fr g = null;

    public final void a(int n2, bv bv2, String string) {
        this.e.d = ag.pf;
        this.c = string;
        this.b.b(n2);
        "".length();
        if (am.b(this.b.h.equals("") ? 1 : 0) && am.b(em.d)) {
            this.f = bv2;
            this.a = j[2];
        }
        this.b.v = string;
    }

    private static void e() {
        j = new int[9];
        am.j[0] = (0x87 ^ 0xC7) & ~(0xF7 ^ 0xB7);
        am.j[1] = 0xFFFFFFC4 & 0x1F7B;
        am.j[2] = " ".length();
        am.j[3] = -(0xFFFFF0FD & 0x2FBF) & (0xFFFFFFFD & 0x3FFF);
        am.j[4] = 0x9A ^ 0xAF;
        am.j[5] = 0x16 ^ 6;
        am.j[6] = (0x77 ^ 0x49) & ~(0x29 ^ 0x17) ^ (2 ^ 0x22);
        am.j[7] = "  ".length();
        am.j[8] = 0xE4 ^ 0xB9 ^ (0x20 ^ 0x55);
    }

    public void a(String string, String string2) {
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void a(String string) {
        this.e.d = ag.pf;
        this.c = string;
        if (am.b(em.d)) {
            this.a = j[2];
            if (am.c(em.w ? 1 : 0)) {
                this.b.g();
            }
        }
        this.b.v = string;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    public void b_() {
    }

    public void d() {
    }

    public void a(eh eh2) {
    }

    public static am c() {
        am am2;
        if (am.b(h)) {
            am2 = h = new am();
            "".length();
            if (" ".length() <= 0) {
                return null;
            }
        } else {
            am2 = h;
        }
        return am2;
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 8000: {
                if (!am.a(this.f)) break;
                long l2 = System.currentTimeMillis();
                if (am.a(am.a(l2 - this.i, 1000L))) {
                    return;
                }
                this.i = l2;
                this.f.a(this.b.h, this.c);
                this.b.a("");
                this.e.d = ag.pf;
                return;
            }
            case 8001: {
                this.b.e();
                if (!am.c(this.b.h.equals("") ? 1 : 0)) break;
                this.a = j[0];
                this.f.b_();
            }
        }
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    static {
        am.e();
    }

    public void b() {
    }

    protected am() {
        this.b = new eg();
        this.b.l = "chat";
        this.b.j = j[5];
        this.b.d = fk.a.d() - j[6];
        this.b.q = ef.fu + j[7];
        this.b.a = j[2];
        this.b.i = j[8];
    }

    public final void a(bh bh2) {
        if (am.c(this.a ? 1 : 0)) {
            this.b.a(bh2);
        }
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }
}

