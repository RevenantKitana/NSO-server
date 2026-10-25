/*
 * Decompiled with CFR 0.152.
 */
public final class dc {
    public boolean a = e[0];
    public short b;
    public String c;
    private static final int[] e;
    public short d;

    static {
        dc.a();
    }

    private static void a() {
        e = new int[1];
        dc.e[0] = (0xB5 ^ 0x85) & ~(0x1A ^ 0x2A);
    }

    public dc(String string, short s2, short s3) {
        this.d = s2;
        this.b = s3;
        this.c = string;
    }
}

