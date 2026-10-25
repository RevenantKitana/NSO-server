/*
 * Decompiled with CFR 0.152.
 */
public final class af {
    public short a;
    public short b;
    public short c;
    private static final int[] f;
    public byte d = (byte)f[0];
    public byte e;

    static {
        af.a();
    }

    public af(int n2, int n3, int n4) {
        this.b = (short)n4;
        this.c = (short)n2;
        this.a = (short)n3;
    }

    private static void a() {
        f = new int[1];
        af.f[0] = (0x97 ^ 0xC3) & ~(3 ^ 0x57);
    }
}

