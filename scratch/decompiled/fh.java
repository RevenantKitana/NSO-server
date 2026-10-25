/*
 * Decompiled with CFR 0.152.
 */
public final class fh {
    private int b;
    public int a = f[0];
    private int c;
    private int d;
    private int e;
    private static final int[] f;

    public final void a(bh bh2) {
        if (fh.a(this.c)) {
            bh2.a(ap.d, f[0], this.a * f[3], f[3], f[3], f[0], this.e, this.d, f[0]);
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        } else if (fh.a(this.c, f[1])) {
            bh2.a(ap.ai, f[0], this.a * f[6], f[6], f[6], f[0], this.e, this.d, f[7]);
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        } else if (fh.a(this.c, f[2])) {
            bh2.a(ap.cp, f[0], this.a * f[8], f[9], f[8], f[0], this.e + f[6], this.d + f[4], f[6]);
            "".length();
            if (((0x17 ^ 9) & ~(0xDC ^ 0xC2)) != ((0 ^ 0x55) & ~(0xF7 ^ 0xA2))) {
                return;
            }
        } else if (fh.a(this.c, f[3])) {
            bh2.a(ap.dy, f[0], this.a << f[3], f[10], f[10], f[0], this.e + f[6], this.d + f[4], f[6]);
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        } else if (fh.a(this.c, f[4])) {
            bh2.a(ap.eq, f[0], this.a << f[3], f[10], f[10], f[0], this.e + f[6], this.d + f[4], f[6]);
            "".length();
            if (-" ".length() > -" ".length()) {
                return;
            }
        } else if (fh.a(this.c, f[5])) {
            bh2.a(ap.dd, f[0], this.a * f[9], f[9], f[9], f[0], this.e + f[6], this.d + f[4], f[6]);
        }
    }

    public final void a() {
        if (fh.a(this.c, f[1])) {
            this.b += f[1];
            if (fh.a(this.b % f[2])) {
                this.a += f[1];
                return;
            }
        } else {
            if (fh.a(this.c)) {
                this.a += f[1];
                return;
            }
            if (!fh.b(this.c, f[2]) || !fh.b(this.c, f[3]) || !fh.b(this.c, f[4]) || fh.a(this.c, f[5])) {
                this.b += f[1];
                if (fh.a(this.b % f[2])) {
                    this.a += f[1];
                }
            }
        }
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    public fh(int n2, int n3, int n4) {
        this.b = f[0];
        this.e = n2;
        this.d = n3;
        this.c = n4;
    }

    static {
        fh.b();
    }

    private static void b() {
        f = new int[11];
        fh.f[0] = (0xB1 ^ 0xA6 ^ (0x66 ^ 0x33)) & (224 + 196 - 312 + 145 ^ 154 + 110 - 134 + 61 ^ -" ".length());
        fh.f[1] = " ".length();
        fh.f[2] = "  ".length();
        fh.f[3] = "   ".length();
        fh.f[4] = 0x4E ^ 0x4A ^ (0x6A ^ 0x35) & ~(0x49 ^ 0x16);
        fh.f[5] = 79 + 65 - 81 + 84 ^ 64 + 40 - 1 + 47;
        fh.f[6] = 0x4A ^ 0x5E;
        fh.f[7] = 0xAA ^ 0xB8 ^ (0xB8 ^ 0x8B);
        fh.f[8] = 0x27 ^ 0x28;
        fh.f[9] = 0xC8 ^ 0x96 ^ (0xCF ^ 0x9F);
        fh.f[10] = 0xA4 ^ 0xAC;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }
}

