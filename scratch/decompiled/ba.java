/*
 * Decompiled with CFR 0.152.
 */
public class ba
extends ax {
    private static final int[] a;

    private static boolean a(int n2) {
        return n2 <= 0;
    }

    public String toString() {
        return "Auto Nhi\u1ec7m V\u1ee5 10";
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static boolean f(int n2) {
        return n2 == 0;
    }

    private static boolean e(int n2, int n3) {
        return n2 < n3;
    }

    public final void a() {
        ec ec2 = ec.n();
        if (ba.e(this.a(ec2) ? 1 : 0)) {
            ap.a("Xong!");
            ey.q();
            "".length();
            if ((0x63 ^ 0x66) <= 0) {
                return;
            }
        } else {
            byte by2 = ap.ak();
            byte by3 = ap.w();
            if (ba.a(ec.n().cl)) {
                ax.a(a[2]);
                "".length();
                if (-(0xF3 ^ 0xC1 ^ (0xB7 ^ 0x80)) >= 0) {
                    return;
                }
            } else if (ba.b(ec2.ds)) {
                if (ba.c(de.i, by2)) {
                    super.a(by2, a[16], a[7], a[7]);
                    "".length();
                    if ("   ".length() == -" ".length()) {
                        return;
                    }
                } else {
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    this.x = a[7];
                    "".length();
                    if ((0x5E ^ 0x5B) <= 0) {
                        return;
                    }
                }
            } else if (ba.f(ec2.ds.e, ec2.ds.a.length - a[1])) {
                if (ba.c(de.i, by2)) {
                    super.a(by2, a[16], a[7], a[7]);
                    "".length();
                    if ("  ".length() == " ".length()) {
                        return;
                    }
                } else {
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    this.x = a[7];
                    "".length();
                    if ((0x9B ^ 0x9F) <= " ".length()) {
                        return;
                    }
                }
            } else {
                this.a(ec2, by2, by3);
            }
        }
    }

    static {
        ba.e();
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public void a(ec ec2, byte by2, byte by3) {
        switch (ec2.bu) {
            case 0: {
                ft ft2 = ds.b[by3];
                int n2 = a[2];
                while (ba.e(n2, ft2.f.length)) {
                    if (ba.e(ft2.f[n2][a[2]].equals("N\u00f3i chuy\u1ec7n") ? 1 : 0)) {
                        ap.b(by3, n2, a[2]);
                        bo.r();
                        return;
                    }
                    ++n2;
                    "".length();
                    if (null == null) continue;
                    return;
                }
                return;
            }
            case 1: {
                if (ba.f(ec2.ds.e)) {
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[1])) {
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[3])) {
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[4])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (!ba.d(ec2.ds.e, a[5])) break;
                dl.h().b((int)by3, a[2]);
                bo.r();
                return;
            }
            case 2: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.a(ec2.en[a[2]])) {
                        dl.h().u(a[2]);
                    }
                    bo.r();
                    return;
                }
                if (!ba.d(ec2.ds.e, a[1])) break;
                if (ba.d(de.i, a[6])) {
                    this.d(a[7]);
                    this.b(a[2], a[1]);
                    return;
                }
                this.a(a[6], a[7], a[7], a[7]);
                return;
            }
            case 3: {
                if (ba.f(ec2.ds.e)) {
                    ax.a(2000L);
                    ap.b(a[5], a[2], a[2]);
                    dl.h().c(a[0], a[2], a[4]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[1])) {
                    if (ba.a(ec2.en[a[2]])) {
                        dl.h().u(a[2]);
                    }
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[3])) {
                    if (ba.d(de.i, a[8])) {
                        this.d(a[7]);
                        this.b(a[1], a[1]);
                        return;
                    }
                    this.a(a[8], a[7], a[7], a[7]);
                    return;
                }
                if (!ba.d(ec2.ds.e, a[4])) break;
                if (ba.d(de.i, a[8])) {
                    this.d(a[7]);
                    this.b(a[3], a[1]);
                    return;
                }
                this.a(a[8], a[7], a[7], a[7]);
                return;
            }
            case 4: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.d(de.i, a[9])) {
                        this.d(a[7]);
                        this.b(a[7], a[1]);
                        return;
                    }
                    this.a(a[9], a[7], a[7], a[7]);
                    return;
                }
                if (ba.d(ec2.ds.e, a[1])) {
                    if (ba.d(de.i, a[9])) {
                        this.d(a[10]);
                        this.b(a[4], a[1]);
                        return;
                    }
                    this.a(a[9], a[7], a[7], a[7]);
                    return;
                }
                if (!ba.d(ec2.ds.e, a[3])) break;
                if (ba.d(de.i, a[8])) {
                    this.d(a[11]);
                    this.b(a[5], a[1]);
                    return;
                }
                this.a(a[8], a[7], a[7], a[7]);
                return;
            }
            case 5: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.d(de.i, a[12])) {
                        this.d(a[7]);
                        this.b(a[4], a[1]);
                        return;
                    }
                    this.a(a[12], a[7], a[7], a[7]);
                    return;
                }
                if (!ba.d(ec2.ds.e, a[1])) break;
                if (ba.d(de.i, a[12])) {
                    this.d(a[13]);
                    this.b(a[14], a[1]);
                    return;
                }
                this.a(a[12], a[7], a[7], a[7]);
                return;
            }
            case 6: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.d(de.i, a[15])) {
                        this.d(a[7]);
                        this.b(a[7], a[1]);
                        return;
                    }
                    this.a(a[15], a[7], a[7], a[7]);
                    return;
                }
                if (ba.d(ec2.ds.e, a[1])) {
                    super.a(a[3], a[16], a[7], a[7]);
                    ax.a(500L);
                    return;
                }
                if (ba.d(ec2.ds.e, a[3])) {
                    super.a(a[17], a[16], a[7], a[7]);
                    ax.a(500L);
                    return;
                }
                if (!ba.d(ec2.ds.e, a[4])) break;
                super.a(a[15], a[16], a[7], a[7]);
                ax.a(500L);
                return;
            }
            case 7: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.d(de.i, a[17])) {
                        this.d(a[7]);
                        this.b(a[7], a[1]);
                        return;
                    }
                    this.a(a[17], a[7], a[7], a[7]);
                    return;
                }
                if (ba.d(ec2.ds.e, a[1])) {
                    super.a(by2, a[16], a[7], a[7]);
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[3])) {
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[4])) {
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[5])) {
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[18])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[19])) {
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[20])) {
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[21])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[0])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[22])) {
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[23])) {
                    ap.b(by3, a[2], a[2]);
                    dl.h().b((int)by3, a[2]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[24])) {
                    dl.h().b((int)by3, a[1]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[25])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (ba.d(ec2.ds.e, a[26])) {
                    dl.h().b((int)by3, a[3]);
                    bo.r();
                    return;
                }
                if (!ba.d(ec2.ds.e, a[27])) break;
                dl.h().b((int)by3, a[1]);
                bo.r();
                return;
            }
            case 8: {
                if (ba.f(ec2.ds.e)) {
                    if (ba.d(de.i, a[15])) {
                        this.d(a[7]);
                        this.b(a[7], a[1]);
                        return;
                    }
                    this.a(a[15], a[7], a[7], a[7]);
                    return;
                }
                super.a(by2, a[16], a[7], a[7]);
                ap.g(by3);
                ft ft3 = ds.b[by3];
                int n3 = a[2];
                while (ba.e(n3, ft3.f.length)) {
                    if (ba.e(ft3.f[n3][a[2]].equals("N\u00f3i chuy\u1ec7n") ? 1 : 0)) {
                        ap.b(by3, n3, a[2]);
                        bo.r();
                        ax.a(1000L);
                        return;
                    }
                    ++n3;
                    "".length();
                    if (null == null) continue;
                    return;
                }
                break;
            }
        }
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public boolean a(ec ec2) {
        boolean bl2;
        if (ba.f(ec2.bu, a[0])) {
            bl2 = a[1];
            "".length();
            if (((0x43 ^ 0x5C) & ~(0x98 ^ 0x87)) != 0) {
                return ((0xFD ^ 0xAE) & ~(0xFC ^ 0xAF)) != 0;
            }
        } else {
            bl2 = a[2];
        }
        return bl2;
    }

    private static boolean f(int n2, int n3) {
        return n2 >= n3;
    }

    private static void e() {
        a = new int[28];
        ba.a[0] = 0xAF ^ 0xA6;
        ba.a[1] = " ".length();
        ba.a[2] = (110 + 168 - 169 + 109 ^ 58 + 50 - 102 + 136) & (0x8A ^ 0xC2 ^ (0x99 ^ 0x85) ^ -" ".length());
        ba.a[3] = "  ".length();
        ba.a[4] = "   ".length();
        ba.a[5] = 0x48 ^ 0x4C;
        ba.a[6] = 0x2E ^ 0x38;
        ba.a[7] = -" ".length();
        ba.a[8] = 0xC1 ^ 0xC7 ^ (0x3A ^ 0x2B);
        ba.a[9] = 0x93 ^ 0x8D ^ (0x1C ^ 0x17);
        ba.a[10] = 92 + 54 - -58 + 5;
        ba.a[11] = 7 + 5 - -15 + 170 + (0x23 ^ 0x52) - (0x70 ^ 6) + (0x9B ^ 0x89);
        ba.a[12] = 0x58 ^ 0x68 ^ (0x7F ^ 0x5B);
        ba.a[13] = 71 + 46 - -4 + 6 + (0x6E ^ 2) - (0x27 ^ 0x6A) + (0x68 ^ 0x5D);
        ba.a[14] = 0x33 ^ 0x64 ^ (0x1C ^ 0x7D);
        ba.a[15] = 12 + 73 - 78 + 136 ^ 136 + 18 - 130 + 125;
        ba.a[16] = -"  ".length();
        ba.a[17] = 95 + 70 - 38 + 127 ^ 174 + 104 - 135 + 42;
        ba.a[18] = 0x8C ^ 0xB1 ^ (0x91 ^ 0xA9);
        ba.a[19] = 0xA7 ^ 0xA1;
        ba.a[20] = 0xA5 ^ 0xA2;
        ba.a[21] = 0x2A ^ 0x22;
        ba.a[22] = 0x97 ^ 0x9D;
        ba.a[23] = 0x78 ^ 0x73;
        ba.a[24] = 0x1F ^ 0x67 ^ (0xF2 ^ 0x86);
        ba.a[25] = 0x6F ^ 0x62;
        ba.a[26] = 157 + 30 - 102 + 99 ^ 40 + 23 - -97 + 22;
        ba.a[27] = 0xC8 ^ 0xC7;
    }
}

