/*
 * Decompiled with CFR 0.152.
 */
public final class bt
extends as {
    public boolean a;
    private int f = h[0];
    private int g;
    public int b;
    private static final int[] h;
    private String[] i;

    static {
        bt.c();
    }

    private static void c() {
        h = new int[16];
        bt.h[0] = 0x2A ^ 0x17 ^ (0xBB ^ 0x98);
        bt.h[1] = 70 + 16 - 43 + 98 + (0x62 ^ 0x5C) - (0xD ^ 0x6B) + (0x1E ^ 0x55);
        bt.h[2] = 152 + 146 - 254 + 132 ^ 9 + 40 - -129 + 8;
        bt.h[3] = " ".length();
        bt.h[4] = 0xF ^ 0x27;
        bt.h[5] = "  ".length();
        bt.h[6] = 0xF ^ 0x2C;
        bt.h[7] = 0x31 ^ 0x20 ^ (0xCE ^ 0xC5);
        bt.h[8] = 0xC3 ^ 0xB0;
        bt.h[9] = 19 + 13 - 16 + 141 ^ 5 + 88 - -63 + 20;
        bt.h[10] = 93 + 189 - 254 + 216 ^ 119 + 84 - 107 + 68;
        bt.h[11] = (0x23 ^ 0x6A ^ (0xEC ^ 0x89)) & (0x14 ^ 0x37 ^ (0x54 ^ 0x5B) ^ -" ".length());
        bt.h[12] = 0xAA ^ 0xAF;
        bt.h[13] = 0x45 ^ 0x51;
        bt.h[14] = 12 + 117 - 106 + 132 ^ 171 + 111 - 171 + 78;
        bt.h[15] = 0x27 ^ 0x2B;
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean b(int n2, int n3) {
        return n2 <= n3;
    }

    public final void a(bh bh2) {
        int n2 = em.aq - this.g - h[14];
        br.b(this.f, n2, em.aj - (this.f << h[3]), this.g, bh2);
        n2 = n2 + (this.g - this.i.length * doa.b.l) / h[5] - h[5];
        if (bt.b(this.a ? 1 : 0)) {
            em.a(em.r, (n2 += 8) - h[15], bh2);
        }
        int n3 = h[11];
        while (bt.c(n3, this.i.length)) {
            doa.b.a(bh2, this.i[n3], em.r, n2, h[5]);
            ++n3;
            n2 += doa.b.l;
            "".length();
            if (-(19 + 69 - 32 + 73 ^ 95 + 18 - 83 + 102) < 0) continue;
            return;
        }
        super.a(bh2);
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2) {
        return n2 > 0;
    }

    public final void a(String string, fr fr2, fr fr3, fr fr4) {
        this.i = doa.b.a(string, em.aj - ((this.f << h[3]) + h[4]));
        this.c = fr2;
        this.e = fr3;
        this.d = fr4;
        if (bt.a(fr3)) {
            this.e.h = em.aj / h[5] - h[6];
            this.e.a = em.aq - h[7];
            if (bt.a(fr2)) {
                this.c.h = em.aj / h[5] - h[8];
                this.c.a = em.aq - h[7];
            }
            if (bt.a(fr4)) {
                this.d.h = em.aj / h[5] + h[9];
                this.d.a = em.aq - h[7];
                "".length();
                if (-" ".length() >= "   ".length()) {
                    return;
                }
            }
        } else {
            if (bt.a(fr2)) {
                this.c.h = em.aj / h[5] - h[10];
                this.c.a = em.aq - h[7];
            }
            if (bt.a(fr4)) {
                this.d.h = em.aj / h[5] + h[2];
                this.d.a = em.aq - h[7];
            }
        }
        this.a = h[11];
        this.g = h[10];
        if (bt.a(this.i.length, h[12])) {
            this.g = this.i.length * doa.b.l + h[13];
        }
    }

    public final void a() {
        if (bt.c(this.b)) {
            this.b -= h[3];
            if (bt.d(this.b, h[3])) {
                em.k();
                this.b = h[11];
            }
        }
        super.a();
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    public final void b() {
        this.a(ag.ab, null, null, null);
        em.d = this;
    }

    public bt() {
        if (bt.b(em.aj, h[1])) {
            this.f = h[2];
        }
    }
}

