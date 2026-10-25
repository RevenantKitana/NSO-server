/*
 * Decompiled with CFR 0.152.
 */
public final class bw {
    public String[] a;
    public String[] b;
    public String[] c;
    private static final int[] g;
    public short d;
    public int e;
    public short[] f;

    private static void a() {
        g = new int[1];
        bw.g[0] = (0xC ^ 0x6B) + (13 + 44 - -43 + 32) - (81 + 135 - 214 + 184) + (0x74 ^ 0x1E);
    }

    public bw(byte by2, String string, String string2, String[] stringArray, short[] sArray, short s2) {
        this.e = by2;
        this.c = doa.v.a(string, g[0]);
        this.b = doa.i.a(string2, g[0]);
        this.a = stringArray;
        this.f = sArray;
        this.d = s2;
    }

    static {
        bw.a();
    }
}

