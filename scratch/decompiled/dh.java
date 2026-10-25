/*
 * Decompiled with CFR 0.152.
 */
public final class dh
implements Runnable {
    private dp a;

    public final void run() {
        do {
            try {
                em.s();
                dp dp2 = this.a;
                ey.k();
                dp2.e();
                Thread.sleep(5000L);
                this.a.a(dp.i, dp.m);
                fl.h = dp.k;
                dl.h().a(aa.a, aa.c, "1.8.0");
                dp.a(10000L);
                dl.h().r(w.i);
                dp.a(5000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            if (null == null) continue;
            return;
        } while (dh.a(dp.d ? 1 : 0) && !dh.a(em.at, ap.dc));
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    dh(dp dp2) {
        this.a = dp2;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }
}

