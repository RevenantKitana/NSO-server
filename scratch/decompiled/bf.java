/*
 * Decompiled with CFR 0.152.
 */
public final class bf {
    private static final int[] k;
    public short a;
    public byte b;
    public String c;
    public short d;
    public String e;
    public static int f;
    public boolean g;
    public byte h;
    public byte i;
    public short j;

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    public static void a() {
        f = k[2];
    }

    private static boolean b(int n2, int n3) {
        return n2 <= n3;
    }

    public final boolean b() {
        int n2;
        if (bf.a(this.b, k[3]) && bf.b(this.b, k[4])) {
            n2 = k[1];
            "".length();
            if (-(0x90 ^ 0x94) > 0) {
                return ((0x19 ^ 0x3E) & ~(0x35 ^ 0x12)) != 0;
            }
        } else {
            n2 = k[2];
        }
        return n2 != 0;
    }

    public static void c() {
        f = k[5];
    }

    public bf(short s2, byte by2, byte by3, String string, String string2, byte by4, short s3, short s4, boolean bl2) {
        this.d = s2;
        this.b = by2;
        this.i = by3;
        this.e = string;
        this.c = string2;
        this.h = by4;
        this.j = s3;
        this.a = s4;
        this.g = bl2;
    }

    static {
        bf.e();
        ce.a(k[6]);
        bf.c();
    }

    private static void e() {
        k = new int[7];
        bf.k[0] = 0x70 ^ 0x7F;
        bf.k[1] = " ".length();
        bf.k[2] = (0x6F ^ 0x3F ^ (0xC0 ^ 0xB9)) & (0x8C ^ 0x94 ^ (0xBA ^ 0x8B) ^ -" ".length());
        bf.k[3] = 0x80 ^ 0x97;
        bf.k[4] = 0x3E ^ 0x27;
        bf.k[5] = (0x1F ^ 0x74) + (0x95 ^ 0xAB) - (0x4C ^ 0x26) + (0x2A ^ 0x6D);
        bf.k[6] = 0xCE ^ 0x8B;
    }

    public final boolean d() {
        int n2;
        if (bf.a(this.b) && bf.b(this.b, k[0])) {
            n2 = k[1];
            "".length();
            if (((0xA4 ^ 0xA3) & ~(0x68 ^ 0x6F)) != 0) {
                return ((0xAC ^ 0xB8) & ~(0x27 ^ 0x33)) != 0;
            }
        } else {
            n2 = k[2];
        }
        return n2 != 0;
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }
}

