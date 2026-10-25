/*
 * Decompiled with CFR 0.152.
 */
public final class fm {
    public int a;
    public int b;
    public int c;
    public int d;
    public long e = 0L;
    private static final int[] m;
    public byte f;
    public int g;
    public int h;
    public boolean i = m[0];
    public ex j;
    public bf k;
    public int l;

    static {
        fm.a();
    }

    public final void a(int n2, int n3) {
        this.h = n2;
        this.a = n3;
        this.b = n2 - this.g >> m[1];
        this.c = n3 - this.d >> m[1];
        this.f = (byte)m[1];
    }

    public fm(short s2, short s3, int n2, int n3, int n4, int n5) {
        this.l = s2;
        this.k = t.b(s3);
        this.g = n4;
        this.d = n3;
        this.h = n4;
        this.a = n5;
        this.b = n4 - n2 >> m[1];
        this.c = m[2];
    }

    public fm(short s2, short s3, int n2, int n3) {
        this.l = s2;
        this.k = t.b(s3);
        this.g = this.h = n2;
        this.d = this.a = n3;
        this.f = (byte)m[3];
    }

    private static void a() {
        m = new int[4];
        fm.m[0] = (1 ^ 0x52) & ~(0x2D ^ 0x7E);
        fm.m[1] = "  ".length();
        fm.m[2] = 0x40 ^ 0x45;
        fm.m[3] = " ".length();
    }
}

