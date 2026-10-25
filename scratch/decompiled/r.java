/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public final class r
extends cl {
    private int e;
    private int f;
    private static int[] g;
    private boolean h;
    private int i;
    private dc j;
    private int k;
    private static final int[] l;
    private int m;
    private static int[] n;
    private int o;
    private int p;
    private static Vector q;
    private short r = (short)l[42];
    private int s = l[2];
    private ec t;
    private static byte[] u;
    private static int v;
    private int w;
    private int x;
    private int y;
    private static int z;
    private int aa;
    private int ab = l[0];

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    public final void a(bh bh2) {
        if (r.c(this.h ? 1 : 0)) {
            int n2 = em.aa % l[8];
            if (r.a(this.y, l[55])) {
                if (r.b(n2, l[5])) {
                    bd.a(bh2, l[56], this.w, this.k, l[0], l[4]);
                    return;
                }
                bd.a(bh2, l[57], this.w, this.k, l[0], l[4]);
                return;
            }
            if (r.a(this.y, l[58])) {
                n2 = em.aa % l[21];
                if (r.b(n2, l[5])) {
                    bd.a(bh2, l[59], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[9])) {
                    bd.a(bh2, l[60], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[13])) {
                    bd.a(bh2, l[59], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[17])) {
                    bd.a(bh2, l[61], this.w, this.k, l[0], l[4]);
                    return;
                }
            } else if (r.a(this.y, l[62])) {
                n2 = em.aa % l[18];
                if (r.b(n2, l[5])) {
                    bd.a(bh2, l[63], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[9])) {
                    bd.a(bh2, l[64], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[13])) {
                    bd.a(bh2, l[65], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[17])) {
                    bd.a(bh2, l[66], this.w, this.k, l[0], l[4]);
                    return;
                }
            } else if (r.a(this.y, l[67])) {
                n2 = em.aa % l[21];
                if (r.b(n2, l[5])) {
                    bd.a(bh2, l[68], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[9])) {
                    bd.a(bh2, l[60], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[13])) {
                    bd.a(bh2, l[69], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[17])) {
                    bd.a(bh2, l[61], this.w, this.k, l[0], l[4]);
                    return;
                }
            } else if (!r.e(this.y, l[70]) || r.a(this.y, l[71])) {
                n2 = em.aa % l[21];
                if (r.b(n2, l[5])) {
                    bd.a(bh2, l[68], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[9])) {
                    bd.a(bh2, l[60], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[13])) {
                    bd.a(bh2, l[69], this.w, this.k, l[0], l[4]);
                    return;
                }
                if (r.b(n2, l[17])) {
                    bd.a(bh2, l[61], this.w, this.k, l[0], l[4]);
                    return;
                }
            }
        } else {
            if (r.f(v, l[47])) {
                bh2.a(ap.ej, l[0], this.ab * l[24], l[72], l[24], z, this.w, this.k, l[0]);
                "".length();
                if ("  ".length() <= 0) {
                    return;
                }
            } else if (r.f(v, l[48]) && r.c(v, l[47])) {
                bh2.a(ap.ej, l[0], l[0], l[15], l[15], z, this.w, this.k, l[0]);
                "".length();
                if (" ".length() == -" ".length()) {
                    return;
                }
            } else if (r.f(v, l[49]) && r.c(v, l[48])) {
                bh2.a(ap.ej, l[0], l[0], l[9], l[9], z, this.w, this.k, l[0]);
                "".length();
                if (null != null) {
                    return;
                }
            } else if (r.d(v) && r.c(v, l[49])) {
                bh2.a(ap.ej, l[0], l[0], l[9], l[9], z, this.w, this.k, l[0]);
            }
            int n3 = l[0];
            while (r.b(n3, q.size())) {
                fh fh2 = (fh)q.elementAt(n3);
                if (r.a(fh2)) {
                    fh2.a(bh2);
                }
                ++n3;
                "".length();
                if ("  ".length() < "   ".length()) continue;
                return;
            }
        }
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    public static void a(int n2, int n3, boolean bl2, short s2, int n4, int n5, int n6, ec ec2) {
        v = s2;
        cl.c.addElement(new r(n2, n3, bl2, n4, n5, n6, ec2));
    }

    private void b(int n2) {
        this.i = n2;
        this.m = this.r * fo.f(n2) >> l[11];
        this.f = this.r * fo.b(n2) >> l[11];
    }

    static {
        r.d();
        ce.a(l[73]);
        r.a();
    }

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }

    public static void a() {
        q = new Vector();
        z = l[0];
        byte[] byArray = new byte[l[1]];
        byArray[r.l[0]] = l[0];
        byArray[r.l[2]] = l[2];
        byArray[r.l[3]] = l[3];
        byArray[r.l[4]] = l[2];
        byArray[r.l[5]] = l[0];
        byArray[r.l[6]] = l[2];
        byArray[r.l[7]] = l[3];
        byArray[r.l[8]] = l[2];
        byArray[r.l[9]] = l[0];
        byArray[r.l[10]] = l[2];
        byArray[r.l[11]] = l[3];
        byArray[r.l[12]] = l[2];
        byArray[r.l[13]] = l[0];
        byArray[r.l[14]] = l[2];
        byArray[r.l[15]] = l[3];
        byArray[r.l[16]] = l[2];
        byArray[r.l[17]] = l[0];
        byArray[r.l[18]] = l[2];
        byArray[r.l[19]] = l[3];
        byArray[r.l[20]] = l[2];
        byArray[r.l[21]] = l[0];
        byArray[r.l[22]] = l[2];
        byArray[r.l[23]] = l[3];
        byArray[r.l[24]] = l[2];
        byArray[r.l[25]] = l[0];
        u = byArray;
        int[] nArray = new int[l[17]];
        nArray[r.l[0]] = l[0];
        nArray[r.l[2]] = l[0];
        nArray[r.l[3]] = l[0];
        nArray[r.l[4]] = l[8];
        nArray[r.l[5]] = l[7];
        nArray[r.l[6]] = l[7];
        nArray[r.l[7]] = l[7];
        nArray[r.l[8]] = l[3];
        nArray[r.l[9]] = l[3];
        nArray[r.l[10]] = l[4];
        nArray[r.l[11]] = l[4];
        nArray[r.l[12]] = l[5];
        nArray[r.l[13]] = l[6];
        nArray[r.l[14]] = l[6];
        nArray[r.l[15]] = l[6];
        nArray[r.l[16]] = l[2];
        g = nArray;
        int[] nArray2 = new int[l[19]];
        nArray2[r.l[0]] = l[0];
        nArray2[r.l[2]] = l[16];
        nArray2[r.l[3]] = l[26];
        nArray2[r.l[4]] = l[27];
        nArray2[r.l[5]] = l[28];
        nArray2[r.l[6]] = l[29];
        nArray2[r.l[7]] = l[30];
        nArray2[r.l[8]] = l[31];
        nArray2[r.l[9]] = l[32];
        nArray2[r.l[10]] = l[33];
        nArray2[r.l[11]] = l[34];
        nArray2[r.l[12]] = l[35];
        nArray2[r.l[13]] = l[36];
        nArray2[r.l[14]] = l[37];
        nArray2[r.l[15]] = l[38];
        nArray2[r.l[16]] = l[39];
        nArray2[r.l[17]] = l[40];
        nArray2[r.l[18]] = l[41];
        n = nArray2;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    public static void b() {
        v = l[0];
        q = null;
        z = l[0];
        u = null;
        g = null;
        n = null;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static void d() {
        l = new int[74];
        r.l[0] = (0x92 ^ 0x85 ^ (0x3E ^ 0x23)) & (33 + 8 - -106 + 19 ^ 71 + 133 - 138 + 106 ^ -" ".length());
        r.l[1] = 0x2B ^ 0x32;
        r.l[2] = " ".length();
        r.l[3] = "  ".length();
        r.l[4] = "   ".length();
        r.l[5] = 0x58 ^ 0x5C;
        r.l[6] = 47 + 57 - -2 + 28 ^ 63 + 106 - 133 + 95;
        r.l[7] = 0xE ^ 0x5C ^ (0x7D ^ 0x29);
        r.l[8] = 126 + 2 - 95 + 131 ^ 99 + 160 - 101 + 5;
        r.l[9] = 0x45 ^ 0x4D;
        r.l[10] = 0x87 ^ 0x8E;
        r.l[11] = 0x1B ^ 0x11;
        r.l[12] = 0xAC ^ 0xA7;
        r.l[13] = 25 + 109 - 118 + 120 ^ 95 + 25 - 18 + 30;
        r.l[14] = 0x74 ^ 0x79;
        r.l[15] = 0x12 ^ 0x3C ^ (0xA7 ^ 0x87);
        r.l[16] = 31 + 155 - 153 + 127 ^ 12 + 114 - 83 + 132;
        r.l[17] = 73 + 61 - 48 + 60 ^ 29 + 24 - 2 + 79;
        r.l[18] = 0x73 ^ 0x7B ^ (0x54 ^ 0x4D);
        r.l[19] = 0x59 ^ 0x38 ^ (0xE1 ^ 0x92);
        r.l[20] = 0x31 ^ 0x11 ^ (0x99 ^ 0xAA);
        r.l[21] = 0x1A ^ 0x67 ^ (0x57 ^ 0x3E);
        r.l[22] = 0x3F ^ 0x21 ^ (0x1F ^ 0x14);
        r.l[23] = 2 ^ 0x14;
        r.l[24] = 0x2B ^ 0x3C;
        r.l[25] = 0x81 ^ 0x99;
        r.l[26] = 0x88 ^ 0xAD;
        r.l[27] = 0xF1 ^ 0xC5;
        r.l[28] = 0x92 ^ 0x88 ^ (0x7A ^ 0x2B);
        r.l[29] = 0x74 ^ 0x7A ^ (0x2F ^ 0x48);
        r.l[30] = 0 + 39 - -24 + 64;
        r.l[31] = (6 ^ 8) + (0x77 ^ 0x6B) - -"  ".length() + (0x35 ^ 0x57);
        r.l[32] = (0xA7 ^ 0x95) + (0x90 ^ 0xA9) - -(0x57 ^ 0x6F) + "  ".length();
        r.l[33] = (0x72 ^ 0x22) + (0x72 ^ 0x44) - (0xE9 ^ 0xC7) + (4 ^ 0x6F);
        r.l[34] = 121 + 178 - 255 + 173;
        r.l[35] = 161 + 228 - 297 + 140;
        r.l[36] = 64 + 147 - 35 + 79;
        r.l[37] = -(0xFFFFFCCF & 0xBF3) & (0xFFFF8DDF & 0x7BFF);
        r.l[38] = 0xFFFFC5BF & 0x3B73;
        r.l[39] = 0xFFFFA9D3 & 0x576E;
        r.l[40] = -(0xFFFFDCEF & 0x3F91) & (0xFFFFFDFF & 0x1FD9);
        r.l[41] = -(0xFFFFACDD & 0x7BAF) & (0xFFFFFDFF & 0x2BFE);
        r.l[42] = 0xFFFFC16C & 0x3F93;
        r.l[43] = 201 + 207 - 292 + 134 ^ 21 + 68 - -26 + 45;
        r.l[44] = 34 + 45 - -23 + 138;
        r.l[45] = -(0xFFFF9FB7 & 0x64D9) & (0xFFFFCFFF & 0x35BC);
        r.l[46] = (0x9D ^ 0x87) + (0xCB ^ 0x8A) - -(0x53 ^ 0x6E) + (0x56 ^ 0x4A);
        r.l[47] = 0x48 ^ 0x1C ^ (0x8A ^ 0xBA);
        r.l[48] = 0x70 ^ 0x42;
        r.l[49] = 0x7F ^ 0x61;
        r.l[50] = 0x5F ^ 0x63;
        r.l[51] = -(0xFFFFAE5F & 0x5FE3) & (0xFFFFBFE3 & 0x5E5E);
        r.l[52] = -(170 + 173 - 209 + 46);
        r.l[53] = -(0xFFFFB7FB & 0x5BFD) & (0xFFFFB7FA & 0x7BFD);
        r.l[54] = -(0xFFFFE3FD & 0x7DE6) & (0xFFFFEFF3 & 0x75EF);
        r.l[55] = 0xDC ^ 0xAE;
        r.l[56] = 0xFFFF85DF & 0x7F33;
        r.l[57] = 0xFFFF85FB & 0x7F1F;
        r.l[58] = 0x4C ^ 0x3F;
        r.l[59] = 0xFFFFFFFB & 0x567;
        r.l[60] = 0xFFFFBFFF & 0x4564;
        r.l[61] = 0xFFFFFF7F & 0x5E6;
        r.l[62] = 0xD7 ^ 0xA3;
        r.l[63] = -(0xFFFFB9E9 & 0x569F) & (0xFFFF95FF & Short.MAX_VALUE);
        r.l[64] = -(0xFFFFF607 & 0x79F9) & (0xFFFFF779 & 0x7DFE);
        r.l[65] = -(0xFFFFDCE7 & 0x6B9F) & (0xFFFFEFFF & 0x5DFF);
        r.l[66] = 0xFFFFEDFB & 0x177E;
        r.l[67] = 71 + 37 - 95 + 126;
        r.l[68] = 0xFFFFD5BF & 0x2FF3;
        r.l[69] = 0xFFFFA5B7 & 0x5FFD;
        r.l[70] = (0x64 ^ 0x56) + (118 + 50 - 35 + 7) - (42 + 87 - 4 + 57) + (78 + 121 - 179 + 116);
        r.l[71] = 58 + 29 - 55 + 131;
        r.l[72] = 0x11 ^ 0x6D ^ (0x13 ^ 0x70);
        r.l[73] = 0x88 ^ 0xA9;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private r(int n2, int n3, boolean bl2, int n4, int n5, int n6, ec ec2) {
        this.aa = l[0];
        this.w = n2;
        this.k = n3;
        this.h = bl2;
        this.y = n4;
        this.e = n5;
        this.o = n6;
        this.t = ec2;
        if (r.c(bl2 ? 1 : 0)) {
            this.b(this.s * l[43]);
            this.s += l[2];
            if (r.f(this.s, l[4])) {
                this.s = l[2];
                return;
            }
        } else {
            if (r.f(n2, ec2.s)) {
                this.b(l[44]);
                return;
            }
            this.b(l[45]);
        }
    }

    public static void a(int n2, int n3, dc dc2) {
        cl.c.addElement(new r(n2, n3, dc2));
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    private static boolean f(int n2, int n3) {
        return n2 > n3;
    }

    private r(int n2, int n3, dc dc2) {
        this.aa = l[2];
        this.w = n2;
        this.k = n3;
        this.j = dc2;
        if (r.f(n2, dc2.d)) {
            this.b(l[44]);
            "".length();
            if ("   ".length() == "  ".length()) {
                throw null;
            }
        } else {
            this.b(l[45]);
        }
        this.r = (short)l[42];
        this.i = l[46];
        this.m = this.r * fo.f(this.i) >> l[11];
        this.f = this.r * fo.b(this.i) >> l[11];
    }

    public final void c() {
        int n2;
        int n3;
        int n4;
        fh fh2;
        if (r.f(v, l[47])) {
            fh2 = new fh(this.w, this.k, l[3]);
            q.addElement(fh2);
            "".length();
            if ("  ".length() < 0) {
                return;
            }
        } else if (r.f(v, l[48]) && r.c(v, l[47])) {
            fh2 = new fh(this.w, this.k, l[6]);
            q.addElement(fh2);
            "".length();
            if (" ".length() > "  ".length()) {
                return;
            }
        } else if (r.f(v, l[49]) && r.c(v, l[48])) {
            fh2 = new fh(this.w, this.k, l[5]);
            q.addElement(fh2);
            "".length();
            if ("  ".length() >= "   ".length()) {
                return;
            }
        } else if (r.d(v) && r.c(v, l[49])) {
            fh2 = new fh(this.w, this.k, l[4]);
            q.addElement(fh2);
        }
        if (r.a(this.t)) {
            n4 = this.t.s - this.w;
            n3 = this.t.fc - (this.t.t >> l[2]) - this.k;
            this.p += l[2];
            if ((!r.b(fo.d(n4), l[17]) || r.d(fo.d(n3), l[17])) && r.c(this.p, l[50])) {
                block43: {
                    n2 = fo.b(n4, n3);
                    if (!r.d(Math.abs(n2 - this.i), l[43]) || r.f(n4 * n4 + n3 * n3, l[51])) {
                        if (r.b(Math.abs(n2 - this.i), l[16])) {
                            this.i = n2;
                            "".length();
                            if ((0x24 ^ 0x20) > (0x49 ^ 0x4D)) {
                                return;
                            }
                        } else if ((!r.a(n2 - this.i) || r.d(n2 - this.i, l[46])) && r.d(n2 - this.i, l[52])) {
                            this.i = fo.e(this.i - l[16]);
                            "".length();
                            if (((90 + 38 - 123 + 128 ^ 38 + 91 - 120 + 130) & (0x54 ^ 0x6D ^ (0x2A ^ 0x1D) ^ -" ".length())) != 0) {
                                return;
                            }
                        } else {
                            this.i = fo.e(this.i + l[16]);
                        }
                    }
                    if (r.b(this.r, l[53])) {
                        this.r = (short)(this.r + l[54]);
                    }
                    this.m = this.r * fo.f(this.i) >> l[11];
                    this.f = this.r * fo.b(this.i) >> l[11];
                    n4 = n4 + this.m >> l[11];
                    this.w += n4;
                    n3 = n3 + this.f >> l[11];
                    this.k += n3;
                    n4 = fo.b(n4, -n3);
                    n3 = l[0];
                    do {
                        if (r.d(n3, n.length - l[2])) {
                            n4 = l[0];
                            "".length();
                            if (-(0x38 ^ 0x76 ^ (0xEC ^ 0xA6)) > 0) {
                                return;
                            }
                            break block43;
                        }
                        if (r.d(n4, n[n3]) && r.c(n4, n[n3 + l[2]])) {
                            int n5;
                            if (r.d(n3, l[17])) {
                                n5 = l[0];
                                "".length();
                                if (((0xA8 ^ 0xB3) & ~(0x66 ^ 0x7D)) >= (0x56 ^ 0x52)) {
                                    return;
                                }
                            } else {
                                n5 = n3;
                            }
                            n4 = n5;
                            "".length();
                            if (" ".length() < 0) {
                                return;
                            }
                            break block43;
                        }
                        ++n3;
                        "".length();
                    } while (-" ".length() < 0);
                    return;
                }
                this.x = n4;
                this.ab = u[this.x];
                z = g[this.x];
            }
        }
        n2 = l[0];
        n4 = l[0];
        if (r.c(this.aa)) {
            if (r.a(this.aa, l[2])) {
                n2 = this.j.d - this.w;
                n4 = this.j.b - l[11] - this.k;
                this.p += l[2];
                if (r.b(fo.d(n2), l[17]) && !r.d(fo.d(n4), l[17]) || r.f(this.p, l[50])) {
                    this.j.a = l[2];
                    cl.c.removeElement(this);
                    "".length();
                    return;
                }
            }
        } else {
            n2 = this.t.s - this.w;
            n4 = this.t.fc - l[11] - this.k;
            this.p += l[2];
            if (!r.e(this.t.fj, l[6]) || r.a(this.t.fj, l[15])) {
                this.w += (this.t.s - this.w) / l[3];
                this.k += (this.t.fc - this.k) / l[3];
            }
            if (r.b(fo.d(n2), l[17]) && !r.d(fo.d(n4), l[17]) || r.f(this.p, l[50])) {
                this.t.a(this.e, this.o, this.h, this.y);
                cl.c.removeElement(this);
                "".length();
                return;
            }
        }
        if (!r.d(Math.abs((n3 = fo.b(n2, n4)) - this.i), l[43]) || r.f(n2 * n2 + n4 * n4, l[51])) {
            if (r.b(Math.abs(n3 - this.i), l[16])) {
                this.i = n3;
                "".length();
                if ((2 ^ 9 ^ (0xCB ^ 0xC4)) < " ".length()) {
                    return;
                }
            } else if ((!r.a(n3 - this.i) || r.d(n3 - this.i, l[46])) && r.d(n3 - this.i, l[52])) {
                this.i = fo.e(this.i - l[16]);
                "".length();
                if (" ".length() == -" ".length()) {
                    return;
                }
            } else {
                this.i = fo.e(this.i + l[16]);
            }
        }
        n2 = n2 + this.m >> l[11];
        this.w += n2;
        n2 = n4 + this.f >> l[11];
        this.k += n2;
        n2 = l[0];
        while (r.b(n2, q.size())) {
            fh2 = (fh)q.elementAt(n2);
            fh2.a();
            if (r.f(fh2.a, l[4])) {
                q.removeElementAt(n2);
            }
            ++n2;
            "".length();
            if (" ".length() != "  ".length()) continue;
            return;
        }
    }
}

