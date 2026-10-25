/*
 * Decompiled with CFR 0.152.
 */
public final class ep
implements Runnable {
    private dp b;
    private static final int[] c;
    ad a;

    static {
        ep.b();
    }

    public ep(dp dp2) {
        this.b = dp2;
        this.a = new ad();
    }

    public final void run() {
        try {
            while (ep.a(this.b.n ? 1 : 0)) {
                if (ep.a(dp.h(this.b) ? 1 : 0)) {
                    while (ep.b(this.a.size())) {
                        eh eh2 = (eh)this.a.elementAt(c[0]);
                        this.a.removeElementAt(c[0]);
                        dp.a(this.b, eh2);
                        "".length();
                        if (null == null) continue;
                        return;
                    }
                }
                Thread.sleep(1L);
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (-" ".length() >= 0) {
            return;
        }
    }

    private void a() {
        this.a.removeAllElements();
    }

    private static void b() {
        c = new int[1];
        ep.c[0] = (7 ^ 0x54) & ~(0xE0 ^ 0xB3);
    }

    private void a(eh eh2) {
        this.a.addElement(eh2);
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }
}

