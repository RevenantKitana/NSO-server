/*
 * Decompiled with CFR 0.152.
 */
public final class bg {
    private int[] a;
    private int b;
    private int[] c;
    private int d;
    private ec e;
    private int f;
    private int g;
    private int h;
    private static final int[] i;
    private int j;
    private int k;
    private int[] l;

    static {
        bg.b();
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static void b() {
        i = new int[16];
        bg.i[0] = 0x24 ^ 0x3C ^ (0x20 ^ 0x10);
        bg.i[1] = 0x65 ^ 0x38 ^ (0x69 ^ 0x20);
        bg.i[2] = " ".length();
        bg.i[3] = "  ".length();
        bg.i[4] = 0xC1 ^ 0xC4;
        bg.i[5] = -(0xFFFFCA9B & 0x7775) & (0xFFFFDFFD & 0x633E);
        bg.i[6] = (0xE5 ^ 0xB5) & ~(0xE8 ^ 0xB8);
        bg.i[7] = 0x22 ^ 0x28;
        bg.i[8] = -" ".length();
        bg.i[9] = "   ".length();
        bg.i[10] = 156 + 98 - 147 + 55 ^ 93 + 50 - 37 + 92;
        bg.i[11] = 0x3B ^ 0x2A ^ (0x7A ^ 0x59);
        bg.i[12] = 70 + 101 - 95 + 102 ^ 97 + 171 - 98 + 12;
        bg.i[13] = -(2 ^ 7);
        bg.i[14] = -(0xFFFFA16F & 0x7ED5) & (0xFFFFEDFF & 0x37DD);
        bg.i[15] = 0xFFFFFD9A & 0x7FF;
    }

    private static boolean b(int n2, int n3) {
        return n2 >= n3;
    }

    public final void a() {
        if (bg.a(this.b, i[2])) {
            if (bg.a(this.e)) {
                ap.k.removeElement(this);
                "".length();
                return;
            }
            if (bg.a(this.k, i[2])) {
                if (bg.c(this.g, this.j)) {
                    this.g -= i[3];
                    this.d += i[2] - em.aa % i[9];
                    "".length();
                    if (" ".length() == 0) {
                        return;
                    }
                } else {
                    this.k = i[3];
                }
                this.h = i[10];
                return;
            }
            this.h -= i[2];
            if (bg.b(fo.d(this.e.s - this.d), i[11]) && bg.b(fo.d(this.e.fc - this.g), i[11])) {
                this.d += (this.e.s - this.d) / i[7];
                this.g += (this.e.fc - this.g) / i[7];
                "".length();
                if ("   ".length() < 0) {
                    return;
                }
            } else {
                this.d += (this.e.s - this.d) / i[12];
                this.g += (this.e.fc - this.g) / i[12];
            }
            if (bg.a(this.h)) {
                this.d = this.e.s;
                this.g = this.e.fc - this.e.t / i[3];
                if (bg.d(this.h, i[13])) {
                    ap.k.removeElement(this);
                    "".length();
                    return;
                }
            } else if (bg.d(fo.d(this.e.s - this.d), i[7]) && bg.d(fo.d(this.e.fc - this.g), i[7])) {
                ap.k.removeElement(this);
                "".length();
                return;
            }
        } else if (bg.a(this.b, i[3])) {
            int n2 = i[6];
            while (bg.d(n2, this.c.length)) {
                int[] nArray = this.l;
                int n3 = n2;
                nArray[n3] = nArray[n3] - em.aa % i[4];
                if (bg.a(this.a[n2], i[8])) {
                    int n4 = n2;
                    this.c[n4] = this.c[n4] - i[2];
                    "".length();
                    if (" ".length() == "   ".length()) {
                        return;
                    }
                } else {
                    int n5 = n2;
                    this.c[n5] = this.c[n5] + i[2];
                }
                if (!bg.c(this.c[n2], this.f - i[1]) || bg.b(this.c[n2], this.f + i[1])) {
                    int[] nArray2 = this.a;
                    nArray2[n2] = -nArray2[n2];
                }
                if (bg.a(this.l[n2])) {
                    ap.k.removeElement(this);
                    "".length();
                }
                ++n2;
                "".length();
                if (((0xED ^ 0xBB) & ~(0xDB ^ 0x8D)) == 0) continue;
                return;
            }
        }
    }

    public bg(int n2, int n3) {
        this.k = i[2];
        this.b = i[3];
        this.f = n2;
        this.j = n3;
        this.c = new int[i[4]];
        this.l = new int[i[4]];
        this.a = new int[i[4]];
        this.h = i[5];
        int n4 = i[6];
        while (bg.d(n4, this.c.length)) {
            int n5;
            this.c[n4] = fo.a(n2 - i[1], n2 + i[1]);
            this.l[n4] = n3 - i[7];
            if (bg.b(n4 % i[3])) {
                n5 = i[2];
                "".length();
                if (-(125 + 62 - 157 + 166 ^ 64 + 48 - 63 + 143) > 0) {
                    throw null;
                }
            } else {
                n5 = i[8];
            }
            this.a[n4] = n5;
            ++n4;
            "".length();
            if (null == null) continue;
            throw null;
        }
    }

    public bg(int n2, int n3, ec ec2) {
        this.d = n2;
        this.g = n3;
        this.f = n2;
        this.j = n3 - (fo.c(i[0]) + i[1]);
        this.k = i[2];
        this.b = i[2];
        this.e = ec2;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    public final void a(bh bh2) {
        if (bg.a(this.b, i[2])) {
            if (bg.c(em.aa % i[4], i[3])) {
                bd.a(bh2, i[14], this.d, this.g, i[6], i[9]);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                bd.a(bh2, i[15], this.d, this.g, i[6], i[9]);
                "".length();
                if ("   ".length() == " ".length()) {
                    return;
                }
            }
        } else {
            int n2 = i[6];
            while (bg.d(n2, this.c.length)) {
                if (bg.c(em.aa % i[4], i[3])) {
                    bd.a(bh2, i[14], this.c[n2], this.l[n2], i[6], i[9]);
                    "".length();
                    if (-"  ".length() > 0) {
                        return;
                    }
                } else {
                    bd.a(bh2, i[15], this.c[n2], this.l[n2], i[6], i[9]);
                }
                ++n2;
                "".length();
                if ((0x8A ^ 0x8F) > 0) continue;
                return;
            }
        }
    }
}

