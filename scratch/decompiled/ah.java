/*
 * Decompiled with CFR 0.152.
 */
public final class ah {
    public byte a;
    public int b = g[0];
    public int c;
    public static by d;
    private static final int[] g;
    public int e;
    public int f;

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    public ah(int n2, int n3) {
        this.a = (byte)g[0];
        this.f = n2;
        this.c = n3;
    }

    public final boolean a() {
        boolean bl2;
        if (ah.c(this.f, ap.fb)) {
            return g[0];
        }
        if (ah.a(this.f, ap.fb + ap.fq)) {
            return g[0];
        }
        if (ah.c(this.c, ap.s)) {
            return g[0];
        }
        if (ah.a(this.c, ap.s + ap.bv + g[1])) {
            return g[0];
        }
        if (!ah.a(d) || ah.b(ah.d.p, g[2])) {
            bl2 = g[3];
            "".length();
            if ("   ".length() == 0) {
                return ((0xD ^ 0x37) & ~(0xA9 ^ 0x93)) != 0;
            }
        } else {
            bl2 = g[0];
        }
        return bl2;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    static {
        ah.d();
        ce.a(g[4]);
        ah.b();
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    public static void b() {
    }

    private static void d() {
        g = new int[5];
        ah.g[0] = (0xF1 ^ 0xAD) & ~(0x9F ^ 0xC3);
        ah.g[1] = 0x76 ^ 0x68;
        ah.g[2] = 0x11 ^ 0x19;
        ah.g[3] = " ".length();
        ah.g[4] = 0xBE ^ 0x96;
    }

    public static void c() {
        d = null;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }
}

