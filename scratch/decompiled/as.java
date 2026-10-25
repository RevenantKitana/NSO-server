/*
 * Decompiled with CFR 0.152.
 */
public abstract class as {
    private static final int[] a;
    public fr c;
    public fr d;
    public fr e;

    public void a(int n2) {
        switch (n2) {
            case -39: 
            case -2: {
                em.af[as.a[1]] = a[2];
                em.j[as.a[1]] = a[2];
                return;
            }
            case -38: 
            case -1: {
                em.af[as.a[3]] = a[2];
                em.j[as.a[3]] = a[2];
                return;
            }
            case -22: 
            case -7: {
                em.af[as.a[4]] = a[2];
                em.j[as.a[4]] = a[2];
                return;
            }
            case -21: 
            case -6: {
                em.af[as.a[5]] = a[2];
                em.j[as.a[5]] = a[2];
                return;
            }
            case -5: 
            case 10: {
                em.af[as.a[6]] = a[2];
                em.j[as.a[6]] = a[2];
                return;
            }
        }
    }

    private static void b() {
        a = new int[8];
        as.a[0] = (0x7A ^ 0x67) & ~(0x8F ^ 0x92);
        as.a[1] = 0xAE ^ 0xA6;
        as.a[2] = " ".length();
        as.a[3] = "  ".length();
        as.a[4] = 9 ^ 0x25 ^ (0x39 ^ 0x18);
        as.a[5] = 0xAF ^ 0xA3;
        as.a[6] = 0xAF ^ 0xAA;
        as.a[7] = -" ".length();
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    public void a() {
        if (as.a(this.e) && (!as.b(em.j[a[6]]) || as.c(ef.a(this.e) ? 1 : 0))) {
            em.j[as.a[6]] = a[0];
            em.am = a[0];
            ef.fr = a[7];
            em.q = a[0];
            if (as.a(this.e)) {
                this.e.c();
            }
            ef.fr = a[7];
        }
        if (as.a(this.c) && (!as.b(em.j[a[5]]) || as.c(ef.a(this.c) ? 1 : 0))) {
            em.j[as.a[5]] = a[0];
            em.am = a[0];
            ef.fr = a[7];
            em.q = a[0];
            if (as.a(this.c)) {
                this.c.c();
            }
            ef.fr = a[7];
        }
        if (as.a(this.d) && (!as.b(em.j[a[4]]) || as.c(ef.a(this.d) ? 1 : 0))) {
            em.j[as.a[4]] = a[0];
            em.am = a[0];
            em.q = a[0];
            ef.fr = a[7];
            if (as.a(this.d)) {
                this.d.c();
            }
            ef.fr = a[7];
        }
        em.h();
        em.m();
    }

    static {
        as.b();
    }

    public void a(bh bh2) {
        bh2.a(-bh2.a(), -bh2.b());
        bh2.a(a[0], a[0], em.aj, em.aq);
        br.a(bh2);
        br.a(bh2, this.c, this.e, this.d);
    }
}

