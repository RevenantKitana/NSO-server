/*
 * Decompiled with CFR 0.152.
 */
public final class dq
implements Runnable {
    private static final int[] a;
    private final int b;

    private static void a() {
        a = new int[2];
        dq.a[0] = (0x68 ^ 0x43) & ~(0x36 ^ 0x1D);
        dq.a[1] = " ".length();
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    dq(int n2) {
        this.b = n2;
    }

    static {
        dq.a();
    }

    public final void run() {
        try {
            int n2 = this.b;
            if (dq.a(n2)) {
                n2 = a[0];
            }
            if (dq.a(n2, de.r.size())) {
                n2 = de.r.size() - a[1];
            }
            de.g(n2);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (null != null) {
            return;
        }
        System.gc();
        if (dq.b(dp.g().n ? 1 : 0)) {
            ap.s().c();
        }
        em.k();
        em.ad = a[0];
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }
}

