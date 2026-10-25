/*
 * Decompiled with CFR 0.152.
 */
public final class dv
implements Runnable {
    private v a;

    public final void run() {
        try {
            Thread.sleep(20000L);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (-(0x24 ^ 0x20) > 0) {
            return;
        }
        try {
            if (dv.a(this.a.a.h ? 1 : 0)) {
                dp.c(this.a.a).interrupt();
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ("  ".length() <= 0) {
            return;
        }
    }

    dv(v v2) {
        this.a = v2;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }
}

