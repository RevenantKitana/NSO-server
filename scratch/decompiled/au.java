/*
 * Decompiled with CFR 0.152.
 */
public final class au {
    public dr a;
    public static dr[] b;
    public int c;
    public int d;
    public short e;
    private static final int[] f;

    private static void c() {
        f = new int[2];
        au.f[0] = -(0xFFFFAF4D & 0x58B3) & (0xFFFFCFED & 0x3BFA);
        au.f[1] = 16 + 12 - -46 + 57 ^ 37 + 90 - 45 + 58;
    }

    public static void a() {
        b = null;
    }

    static {
        au.c();
        ce.a(f[1]);
        au.b();
    }

    public au(byte by2, int n2, int n3, short s2) {
        this.a = b[by2];
        this.d = n2;
        this.c = n3 / f[0];
        this.e = s2;
    }

    public static void b() {
    }
}

