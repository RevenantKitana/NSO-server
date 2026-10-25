/*
 * Decompiled with CFR 0.152.
 */
public final class g
implements Runnable {
    private static final int[] a;
    private final el b;

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    static {
        g.a();
    }

    public final void run() {
        try {
            int n2 = a[0];
            while (g.a(n2, this.b.g) && g.b(ec.u())) {
                if (g.a(em.at instanceof ap)) {
                    return;
                }
                dl.h().u(this.b.n);
                Thread.sleep(1L);
                ++n2;
                "".length();
                if (((0xA8 ^ 0x85 ^ (9 ^ 0x78)) & (0x76 ^ 0x60 ^ (0x8E ^ 0xC4) ^ -" ".length())) >= 0) continue;
                return;
            }
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (null != null) {
            return;
        }
    }

    g(el el2) {
        this.b = el2;
    }

    private static void a() {
        a = new int[1];
        g.a[0] = (0x4F ^ 0x47) & ~(0x75 ^ 0x7D);
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }
}

