/*
 * Decompiled with CFR 0.152.
 */
public final class eu
implements Runnable {
    private final int a;
    private static final int[] b;

    eu(int n2) {
        this.a = n2;
    }

    public final void run() {
        ds ds2 = ap.b(b[0]);
        int n2 = b[1];
        if (eu.a(ds2) && eu.a(ds2.fj, b[2])) {
            if (!eu.b(Math.abs(ds2.s - ec.n().s), b[3]) || eu.c(Math.abs(ds2.fc - ec.n().fc), b[3])) {
                ec.d(ds2.s, ds2.fc);
                "".length();
                "".length();
                if (null != null) {
                    return;
                }
            }
        } else {
            if (eu.a(de.i, b[4]) && eu.a(de.i, b[5]) && eu.a(de.i, b[6]) && eu.a(de.i, b[7]) && eu.a(de.i, b[8]) && eu.a(de.i, b[9])) {
                return;
            }
            n2 = ec.c(b[10]);
            if (eu.a(n2) && eu.a(n2 = ec.c(b[11]))) {
                return;
            }
        }
        dl.h().h(this.a, n2);
        de.g();
        try {
            Thread.sleep(100L);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        if (null != null) {
            return;
        }
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    static {
        eu.a();
    }

    private static boolean b(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static void a() {
        b = new int[12];
        eu.b[0] = 0x23 ^ 0x2E;
        eu.b[1] = -" ".length();
        eu.b[2] = 102 + 43 - 54 + 77 ^ 22 + 55 - -24 + 66;
        eu.b[3] = 0x93 ^ 0x85;
        eu.b[4] = 121 + 18 - 44 + 160 ^ 136 + 74 - 71 + 17;
        eu.b[5] = 0x5A ^ 0x3D;
        eu.b[6] = 120 + 30 - 57 + 41;
        eu.b[7] = (0x2B ^ 0x57) + (0x74 ^ 0x49) - (0x29 ^ 0x42) + (0 ^ 0x39);
        eu.b[8] = 113 + 89 - 128 + 62;
        eu.b[9] = (3 ^ 0x67) + (3 ^ 0x63) - (11 + 149 - 147 + 161) + (0x30 ^ 0x43);
        eu.b[10] = 0x5E ^ 0x7B;
        eu.b[11] = 0xAB ^ 0x88;
    }
}

