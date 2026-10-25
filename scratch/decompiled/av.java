/*
 * Decompiled with CFR 0.152.
 */
public final class av {
    public int a;
    public int b = k[0];
    public int c;
    public String d = "";
    private static final int[] k;
    public short e;
    public String f = "";
    public short g;
    public long h;
    public byte i;
    public ad j = new ad();

    public av(String string, byte by2, short s2, short s3, int n2, String string2, ad ad2, int n3, int n4) {
        this.c = k[0];
        this.a = k[0];
        this.d = string;
        this.g = s2;
        this.e = s3;
        this.b = n2;
        this.f = string2;
        this.j = ad2;
        this.c = n3;
        this.a = n4;
        this.h = System.currentTimeMillis();
        this.i = by2;
    }

    private static void a() {
        k = new int[1];
        av.k[0] = -" ".length();
    }

    static {
        av.a();
    }
}

