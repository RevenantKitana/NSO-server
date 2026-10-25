/*
 * Decompiled with CFR 0.152.
 */
public final class ch {
    private static final int[] g;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f = g[0];

    public ch() {
        this.c = g[0];
        this.b = g[0];
    }

    public ch(int n2, int n3) {
        this.c = n2;
        this.b = n3;
    }

    static {
        ch.a();
    }

    private static void a() {
        g = new int[1];
        ch.g[0] = (0xE9 ^ 0xA6) & ~(0xF4 ^ 0xBB);
    }
}

