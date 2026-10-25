/*
 * Decompiled with CFR 0.152.
 */
public final class es
implements Runnable {
    private static final int[] a;

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 <= n3;
    }

    private static void a() {
        a = new int[5];
        es.a[0] = (0x10 ^ 0x1A) & ~(0xB4 ^ 0xBE);
        es.a[1] = 0x2F ^ 0x25 ^ (0x9A ^ 0x8E);
        es.a[2] = 0x4A ^ 0x47;
        es.a[3] = 0xF4 ^ 0x9B ^ (0x5F ^ 0x26);
        es.a[4] = -" ".length();
    }

    public final void run() {
        int n2 = a[0];
        while (es.c(n2, a[1])) {
            ds ds2;
            if (es.d(de.ab, n2) && es.a(ds2 = ap.b(a[2]))) {
                if (!es.a(Math.abs(ds2.s - ec.n().s), a[3]) || es.b(Math.abs(ds2.fc - ec.n().fc), a[3])) {
                    ec.d(ds2.s, ds2.fc);
                    "".length();
                }
                dl.h().h(n2, a[4]);
                de.g();
                try {
                    Thread.sleep(100L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (-" ".length() > 0) {
                    return;
                }
            }
            int n3 = a[0];
            while (es.c(n3, ap.au.size())) {
                by by2 = (by)ap.au.elementAt(n3);
                if (es.b(by2.j ? 1 : 0) && es.a(by2.v) && es.b(by2.p)) {
                    return;
                }
                ++n3;
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            ++n2;
            "".length();
            if (((0x23 ^ 0x6F) & ~(0xC8 ^ 0x84)) == 0) continue;
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    static {
        es.a();
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean d(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    es() {
    }
}

