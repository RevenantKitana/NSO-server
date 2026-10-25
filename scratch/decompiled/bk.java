/*
 * Decompiled with CFR 0.152.
 */
public final class bk {
    public boolean a;
    public int b;
    public int c;
    public int d = f[0];
    public int e;
    private static final int[] f;

    static {
        bk.a();
    }

    public bk(int n2, int n3) {
        this.c = n2;
        this.b = this.e = n3;
        this.a = f[1];
    }

    private static void a() {
        f = new int[2];
        bk.f[0] = "   ".length();
        bk.f[1] = (0x47 ^ 0x7C) & ~(0x9A ^ 0xA1);
    }
}

