/*
 * Decompiled with CFR 0.152.
 */
public final class cn {
    private int d;
    private int e;
    private int f;
    private static byte[] g;
    private fn h = null;
    private static int[] i;
    private int j;
    private ec k;
    public int a = n[0];
    public int b;
    public int c;
    private int l;
    private int m;
    private static final int[] n;
    private static int[] o;

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static boolean c(int n2) {
        return n2 < 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 <= n3;
    }

    public final void a(bh bh2) {
        int n2;
        block11: {
            n2 = this.f - this.c;
            int n3 = this.l - this.b;
            n2 = fo.b(n2, -n3);
            n3 = n[0];
            do {
                if (cn.e(n3, i.length - n[9])) {
                    n2 = n[0];
                    "".length();
                    if (null != null) {
                        return;
                    }
                    break block11;
                }
                if (cn.e(n2, i[n3]) && cn.b(n2, i[n3 + n[9]])) {
                    int n4;
                    if (cn.e(n3, n[10])) {
                        n4 = n[0];
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        n4 = n3;
                    }
                    n2 = n4;
                    "".length();
                    if ("   ".length() > (0xBF ^ 0xA0 ^ (0xB0 ^ 0xAB))) {
                        return;
                    }
                    break block11;
                }
                ++n3;
                "".length();
            } while ((0x2D ^ 0x29) != ((0xBF ^ 0x9A) & ~(0x62 ^ 0x47)));
            return;
        }
        try {
            bd.a(bh2, this.h.a[g[n2]], this.c, this.b, o[n2], n[3]);
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ("  ".length() >= (0x98 ^ 0x9C)) {
            return;
        }
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 != n3;
    }

    public final void a() {
        if (!cn.a(this.k.di) || cn.b(this.k.fm)) {
            if (cn.b(this.k.di)) {
                this.f = this.k.di.z;
                this.l = this.k.di.n - this.k.di.q / n[1];
                "".length();
                if (((0x69 ^ 8 ^ (0x5F ^ 0x2B)) & (0x9F ^ 0xB9 ^ (0xBD ^ 0x8E) ^ -" ".length())) > (0xAE ^ 0x9B ^ (0x3E ^ 0xF))) {
                    return;
                }
            } else if (cn.b(this.k.fm)) {
                this.f = this.k.fm.s;
                this.l = this.k.fm.fc - this.k.fm.t / n[1];
            }
            int n2 = this.f - this.c;
            int n3 = this.l - this.b;
            int n4 = n[1];
            if (cn.c(n2 + n3, n[2])) {
                n4 = n[3];
                "".length();
                if (null != null) {
                    return;
                }
            } else if (cn.c(n2 + n3, n[4])) {
                n4 = n[5];
            }
            if (cn.d(this.c, this.f)) {
                if (cn.a(n2) && cn.c(n2, n[6])) {
                    this.c = this.f;
                    "".length();
                    if ("   ".length() < "   ".length()) {
                        return;
                    }
                } else if (cn.c(n2) && cn.a(n2, n[7])) {
                    this.c = this.f;
                    "".length();
                    if (-" ".length() < -" ".length()) {
                        return;
                    }
                } else {
                    this.m = this.f - this.c << n[5];
                    this.j += this.m;
                    this.c += this.j >> n4;
                    this.j &= n[8];
                }
            }
            if (cn.d(this.b, this.l)) {
                if (cn.a(n3) && cn.c(n3, n[6])) {
                    this.b = this.l;
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else if (cn.c(n3) && cn.a(n3, n[7])) {
                    this.b = this.l;
                    "".length();
                    if (-"   ".length() > 0) {
                        return;
                    }
                } else {
                    this.e = this.l - this.b << n[5];
                    this.d += this.e;
                    this.b += this.d >> n4;
                    this.d &= n[8];
                }
            }
            n2 = n[0];
            n3 = n[0];
            int n5 = n[0];
            int n6 = n[0];
            if (cn.b(this.k.di)) {
                n2 = this.f - this.k.di.k / n[1];
                n5 = this.f + this.k.di.k / n[1];
                n3 = this.l - this.k.di.q / n[1];
                n6 = this.l + this.k.di.q / n[1];
                "".length();
                if (null != null) {
                    return;
                }
            } else if (cn.b(this.k.fm)) {
                n2 = this.f - this.k.fm.dj / n[1];
                n5 = this.f + this.k.fm.dj / n[1];
                n3 = this.l - this.k.fm.t / n[1];
                n6 = this.l + this.k.fm.t / n[1];
            }
            if (cn.a(this.a)) {
                this.a -= n[9];
            }
            if (!(!cn.b(this.a) || cn.e(this.c, n2) && cn.b(this.c, n5) && cn.e(this.b, n3) && !cn.a(this.b, n6))) {
                return;
            }
        }
        this.k.ax = null;
        this.j = this.d = n[0];
        this.e = this.d;
        this.m = this.d;
        this.l = this.d;
        this.f = this.d;
        this.b = this.d;
        this.c = this.d;
        this.k.l();
        if (cn.b(this.k.ep ? 1 : 0)) {
            this.k.af();
        }
    }

    static {
        cn.b();
        byte[] byArray = new byte[n[11]];
        byArray[cn.n[0]] = n[0];
        byArray[cn.n[9]] = n[9];
        byArray[cn.n[5]] = n[5];
        byArray[cn.n[3]] = n[9];
        byArray[cn.n[1]] = n[0];
        byArray[cn.n[6]] = n[9];
        byArray[cn.n[12]] = n[5];
        byArray[cn.n[13]] = n[9];
        byArray[cn.n[14]] = n[0];
        byArray[cn.n[15]] = n[9];
        byArray[cn.n[16]] = n[5];
        byArray[cn.n[17]] = n[9];
        byArray[cn.n[18]] = n[0];
        byArray[cn.n[19]] = n[9];
        byArray[cn.n[20]] = n[5];
        byArray[cn.n[8]] = n[9];
        byArray[cn.n[10]] = n[0];
        byArray[cn.n[21]] = n[9];
        byArray[cn.n[22]] = n[5];
        byArray[cn.n[23]] = n[9];
        byArray[cn.n[24]] = n[0];
        byArray[cn.n[25]] = n[9];
        byArray[cn.n[26]] = n[5];
        byArray[cn.n[27]] = n[9];
        byArray[cn.n[28]] = n[0];
        g = byArray;
        int[] nArray = new int[n[22]];
        nArray[cn.n[0]] = n[0];
        nArray[cn.n[9]] = n[8];
        nArray[cn.n[5]] = n[29];
        nArray[cn.n[3]] = n[30];
        nArray[cn.n[1]] = n[31];
        nArray[cn.n[6]] = n[32];
        nArray[cn.n[12]] = n[33];
        nArray[cn.n[13]] = n[34];
        nArray[cn.n[14]] = n[35];
        nArray[cn.n[15]] = n[36];
        nArray[cn.n[16]] = n[37];
        nArray[cn.n[17]] = n[38];
        nArray[cn.n[18]] = n[39];
        nArray[cn.n[19]] = n[40];
        nArray[cn.n[20]] = n[41];
        nArray[cn.n[8]] = n[42];
        nArray[cn.n[10]] = n[43];
        nArray[cn.n[21]] = n[44];
        i = nArray;
        int[] nArray2 = new int[n[10]];
        nArray2[cn.n[0]] = n[0];
        nArray2[cn.n[9]] = n[0];
        nArray2[cn.n[5]] = n[0];
        nArray2[cn.n[3]] = n[13];
        nArray2[cn.n[1]] = n[12];
        nArray2[cn.n[6]] = n[12];
        nArray2[cn.n[12]] = n[12];
        nArray2[cn.n[13]] = n[5];
        nArray2[cn.n[14]] = n[5];
        nArray2[cn.n[15]] = n[3];
        nArray2[cn.n[16]] = n[3];
        nArray2[cn.n[17]] = n[1];
        nArray2[cn.n[18]] = n[6];
        nArray2[cn.n[19]] = n[6];
        nArray2[cn.n[20]] = n[6];
        nArray2[cn.n[8]] = n[9];
        o = nArray2;
    }

    public cn(ec ec2, fn fn2) {
        this.k = ec2;
        this.h = fn2;
    }

    private static boolean e(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    private static void b() {
        n = new int[45];
        cn.n[0] = (0xDB ^ 0x9D) & ~(0xDD ^ 0x9B);
        cn.n[1] = 19 + 102 - 104 + 111 ^ 96 + 70 - 124 + 90;
        cn.n[2] = 0x5B ^ 0x67;
        cn.n[3] = "   ".length();
        cn.n[4] = 0x54 ^ 0x4A;
        cn.n[5] = "  ".length();
        cn.n[6] = 0x37 ^ 0x1E ^ (7 ^ 0x2B);
        cn.n[7] = -(0x8B ^ 0x9C ^ (0x1F ^ 0xD));
        cn.n[8] = 0xC ^ 3;
        cn.n[9] = " ".length();
        cn.n[10] = 0x3A ^ 0x2A;
        cn.n[11] = 0xD5 ^ 0x87 ^ (0x6D ^ 0x26);
        cn.n[12] = 2 ^ 4;
        cn.n[13] = 4 ^ 0x5D ^ (0xF1 ^ 0xAF);
        cn.n[14] = 35 + 70 - 66 + 103 ^ 121 + 83 - 195 + 125;
        cn.n[15] = 0xA ^ 3;
        cn.n[16] = 0x93 ^ 0x99;
        cn.n[17] = 0x8E ^ 0x85;
        cn.n[18] = 0xB3 ^ 0xBF;
        cn.n[19] = 0x9A ^ 0x86 ^ (0xA9 ^ 0xB8);
        cn.n[20] = 0x60 ^ 3 ^ (0x30 ^ 0x5D);
        cn.n[21] = 0x14 ^ 0x20 ^ (0x84 ^ 0xA1);
        cn.n[22] = 0x5B ^ 0x49;
        cn.n[23] = 163 + 7 - 106 + 126 ^ 19 + 168 - 84 + 70;
        cn.n[24] = 0x87 ^ 0xC6 ^ (0xC7 ^ 0x92);
        cn.n[25] = 0x68 ^ 0x7D;
        cn.n[26] = 0x25 ^ 0x33;
        cn.n[27] = 0x45 ^ 0x52;
        cn.n[28] = 105 + 27 - 34 + 50 ^ 113 + 137 - 117 + 7;
        cn.n[29] = 0x89 ^ 0xAC;
        cn.n[30] = 0xB4 ^ 0x80;
        cn.n[31] = 0xF7 ^ 0xBC;
        cn.n[32] = 109 + 94 - 146 + 180 ^ 40 + 125 - 97 + 64;
        cn.n[33] = 84 + 92 - 144 + 95;
        cn.n[34] = 4 + 31 - -20 + 73 + (0xED ^ 0xC5) - (0x41 ^ 0x6E) + (0x5F ^ 0x4A);
        cn.n[35] = (0x2E ^ 0x48) + (0x17 ^ 0x31) - (0x70 ^ 0x40) + (0xED ^ 0xA4);
        cn.n[36] = (0x37 ^ 0x52) + (0x77 ^ 0x11) - (137 + 96 - 163 + 105) + (1 + 61 - 43 + 148);
        cn.n[37] = 80 + 26 - 80 + 179 + (0xA9 ^ 0x97) - (0x58 ^ 0xE) + (4 ^ 0x20);
        cn.n[38] = (0x55 ^ 0x19) + (58 + 113 - 66 + 76) - (0x57 ^ 8) + (0x39 ^ 0x7F);
        cn.n[39] = 119 + 36 - -15 + 85;
        cn.n[40] = 0xFFFF95DF & 0x6B3D;
        cn.n[41] = 0xFFFFA93B & 0x57F7;
        cn.n[42] = 0xFFFFE9FF & 0x1742;
        cn.n[43] = 0xFFFFA359 & 0x5DFF;
        cn.n[44] = 0xFFFF8BFB & 0x7576;
    }
}

