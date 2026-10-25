/*
 * Decompiled with CFR 0.152.
 */
public final class dx
extends cl {
    private int e;
    private static final int[] f;
    private boolean g;
    private int h;
    private long i;
    private boolean j;
    private ad[] k;
    private int[] l;
    private ag m;
    private ad n;
    private int o;
    private int p;

    private static boolean a(int n2) {
        return n2 == 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void c() {
        if (dx.a(this.m)) {
            cl.d.addElement(this);
            "".length();
            if (-" ".length() <= 0) return;
            return;
        }
        try {
            if (dx.b(em.aa % f[0], f[3])) {
                this.m.pg = (byte)f[8];
                this.m.ca = (short)f[8];
                int n2 = f[1];
                while (dx.c(n2, this.n.size())) {
                    ag ag2 = (ag)this.n.elementAt(n2);
                    ((ag)this.n.elementAt(n2)).ca = (short)f[8];
                    ag2.pg = (byte)f[8];
                    ++n2;
                    "".length();
                    if ("  ".length() > 0) continue;
                    return;
                }
                if (dx.b(this.j ? 1 : 0) && dx.b(this.g ? 1 : 0) && dx.e(this.n.size(), f[3]) && dx.d(dx.a(System.currentTimeMillis() / 10L - this.i, 30L))) {
                    this.i = System.currentTimeMillis() / 10L;
                    this.m = (ag)this.n.elementAt(f[1]);
                    this.n.removeElementAt(f[1]);
                }
                this.a(this.n, this.m, this.j);
                if (dx.e(this.o, f[14])) {
                    this.h = f[5];
                    cl.c.removeElement(this);
                    "".length();
                }
                this.o += f[3];
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((0x75 ^ 0x1F ^ (0x21 ^ 0x4F)) >= (0xF2 ^ 0xAD ^ (0x9B ^ 0xC0))) return;
        return;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public final void a(bh bh2) {
        this.e = f[1];
        if (dx.d(this.p += f[3], f[15])) {
            this.p = f[1];
        }
        if (dx.b(this.k)) {
            int n2 = f[1];
            while (dx.c(n2, this.k.length)) {
                int n3 = f[1];
                while (dx.c(n3, this.k[n2].size())) {
                    ag ag2 = (ag)this.k[n2].elementAt(n3);
                    if (dx.c(ag2.pg) && dx.c(ag2.pg, this.k[n2].size())) {
                        ag ag3 = (ag)this.k[n2].elementAt(ag2.pg);
                        if (dx.b(em.e(ag2.os, ag2.he) ? 1 : 0) && dx.b(em.e(ag3.os, ag3.he) ? 1 : 0)) {
                            bh2.a(this.l[f[1]]);
                            bh2.b(ag2.os, ag2.he, ag3.os, ag3.he);
                            if (dx.b(ag2.ca, f[8])) {
                                bh2.a(this.l[f[3]]);
                                bh2.b(ag2.os - f[3], ag2.he, ag3.os - f[3], ag3.he);
                                if (dx.b(this.j ? 1 : 0) && dx.b(this.g ? 1 : 0)) {
                                    bh2.b(ag2.os + f[3], ag2.he, ag3.os + f[3], ag3.he);
                                }
                            }
                        }
                        if (dx.b(this.j ? 1 : 0) && dx.b(this.g ? 1 : 0)) {
                            this.e += f[3];
                            if (dx.d(this.e, this.h)) {
                                this.h += f[5];
                                return;
                            }
                        }
                    }
                    ++n3;
                    "".length();
                    if (-(0x8E ^ 0x8A) < 0) continue;
                    return;
                }
                ++n2;
                "".length();
                if ("  ".length() >= "  ".length()) continue;
                return;
            }
        }
    }

    private void a(ad ad2, ag ag2, boolean bl2) {
        if (dx.b(ad2.size())) {
            ag ag3;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            ag ag4;
            int n8;
            ag ag5;
            int n9;
            ag ag6;
            int n10;
            int n11;
            ad ad3;
            this.j = bl2;
            if (dx.a(bl2 ? 1 : 0)) {
                ad3 = ad2;
                n11 = ad2.size();
                n10 = f[1];
                while (dx.c(n10, n11 - f[3])) {
                    ag6 = (ag)ad3.elementAt(n10);
                    n9 = n10 + f[3];
                    while (dx.c(n9, n11)) {
                        ag5 = (ag)ad3.elementAt(n9);
                        if (dx.e(ag6.os, ag5.os)) {
                            ad3.setElementAt(ag6, n9);
                            ad3.setElementAt(ag5, n10);
                            ag6 = ag5;
                        }
                        ++n9;
                        "".length();
                        if (-"   ".length() <= 0) continue;
                        return;
                    }
                    ++n10;
                    "".length();
                    if (null == null) continue;
                    return;
                }
            }
            this.n = ad2;
            this.m = ag2;
            this.k = new ad[ad2.size()];
            int n12 = f[1];
            while (dx.c(n12, this.k.length)) {
                this.k[n12] = new ad();
                ++n12;
                "".length();
                if ((92 + 48 - 43 + 51 ^ 3 + 125 - 89 + 105) > " ".length()) continue;
                return;
            }
            ag2.pg = (byte)f[8];
            this.k[f[1]].addElement(ag2);
            n12 = f[8];
            n11 = f[1];
            while (dx.c(n11, ad2.size())) {
                block26: {
                    n8 = ag2.os;
                    int n13 = ag2.he;
                    if (dx.b(bl2 ? 1 : 0) && dx.a(n12, f[8])) {
                        ag4 = (ag)ad2.elementAt(n12);
                        n8 = ag4.os;
                        n13 = ag4.he;
                    }
                    if (dx.b(bl2 ? 1 : 0)) {
                        ++n12;
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        int n14;
                        block25: {
                            ad3 = ad2;
                            n10 = f[1];
                            n7 = f[1];
                            while (dx.c(n7, ad3.size())) {
                                if (dx.b(((ag)ad3.elementAt((int)n7)).ca, f[8])) {
                                    ++n10;
                                }
                                ++n7;
                                "".length();
                                if (-" ".length() <= -" ".length()) continue;
                                return;
                            }
                            if (dx.b(n10)) {
                                n10 = fo.c(n10);
                                n7 = f[1];
                                n9 = f[1];
                                while (dx.c(n9, ad3.size())) {
                                    ag5 = (ag)ad3.elementAt(n9);
                                    if (dx.b(ag5.ca, f[8])) {
                                        if (dx.b(n10, n7)) {
                                            ag5.ca = (short)f[1];
                                            n14 = n9;
                                            "".length();
                                            if ("  ".length() == (0x28 ^ 0x1A ^ (0x1F ^ 0x29))) {
                                                return;
                                            }
                                            break block25;
                                        }
                                        ++n7;
                                    }
                                    ++n9;
                                    "".length();
                                    if (-" ".length() <= 0) continue;
                                    return;
                                }
                            }
                            n14 = f[8];
                        }
                        n12 = n14;
                    }
                    n10 = this.k[n12].size() - f[3];
                    ag6 = (ag)ad2.elementAt(n12);
                    n9 = fo.b(ag6.os - n8, -(ag6.he - n13));
                    n6 = fo.c(f[9]) + f[10];
                    n5 = f[1];
                    do {
                        n4 = f[1];
                        if (dx.b(n5)) {
                            n4 = n9 - f[11] + fo.c(f[10]);
                        }
                        n4 = fo.e(n4);
                        n3 = n6 * n5 * fo.f(n4) >> f[10];
                        n2 = -(n6 * n5 * fo.b(n4)) >> f[10];
                        ag3 = new ag(n8 + n3, n13 + n2, n10++);
                        this.k[n12].addElement(ag3);
                        if (dx.d(fo.a(n8, n13, n8 + n3, n13 + n2), fo.a(n8, n13, ag6.os, ag6.he) - f[12])) {
                            "".length();
                            if (" ".length() < 0) {
                                return;
                            }
                            break block26;
                        }
                        ++n5;
                        "".length();
                    } while (null == null);
                    return;
                }
                ++n11;
                "".length();
                if (null == null) continue;
                return;
            }
            n11 = f[1];
            while (dx.c(n11, this.k.length)) {
                n8 = this.k[n11].size();
                ag ag7 = (ag)ad2.elementAt(n11);
                ((ag)ad2.elementAt(n11)).pg = (byte)(this.k[n11].size() - f[3]);
                ag7.ca = (short)f[8];
                ag4 = new ag(ag7.os, ag7.he, ag7.pg);
                new ag(ag7.os, ag7.he, ag7.pg).ca = (short)f[8];
                this.k[n11].addElement(ag4);
                n7 = f[3];
                while (dx.c(n7, n8)) {
                    ag3 = (ag)this.k[n11].elementAt(n7);
                    n6 = fo.c(f[0]);
                    n5 = f[1];
                    while (dx.c(n5, n6)) {
                        n4 = f[13] + fo.c(f[13]);
                        n3 = f[11] + fo.c(f[10]);
                        n2 = n3 * fo.f(fo.e(n4)) >> f[10];
                        n4 = -(n3 * fo.b(fo.e(n4))) >> f[10];
                        ag2 = new ag(ag3.os + n2, ag3.he + n4, n7);
                        new ag(ag3.os + n2, ag3.he + n4, n7).ca = (short)f[1];
                        this.k[n11].addElement(ag2);
                        ++n5;
                        "".length();
                        if (-" ".length() <= 0) continue;
                        return;
                    }
                    ++n7;
                    "".length();
                    if (null == null) continue;
                    return;
                }
                ++n11;
                "".length();
                if (-" ".length() < "   ".length()) continue;
                return;
            }
        }
    }

    private static boolean a(Object object) {
        return object == null;
    }

    static {
        dx.a();
    }

    private static void a() {
        f = new int[16];
        dx.f[0] = "  ".length();
        dx.f[1] = (0x9F ^ 0xC5) & ~(2 ^ 0x58);
        dx.f[2] = 0xFFFFFFFD & 0xFCFCFF;
        dx.f[3] = " ".length();
        dx.f[4] = -(0xFFFFC769 & 0x7897) & (0xFFFFFFFC & 0xAAF7FF);
        dx.f[5] = 3 ^ 0x63 ^ (0xC9 ^ 0xAE);
        dx.f[6] = 0x60 ^ 1 ^ (0x8D ^ 0x88);
        dx.f[7] = 0x70 ^ 0x42;
        dx.f[8] = -" ".length();
        dx.f[9] = 0x9A ^ 0x95;
        dx.f[10] = 102 + 22 - -25 + 35 ^ 116 + 154 - 217 + 125;
        dx.f[11] = 0x9C ^ 0xAC ^ (0xA7 ^ 0x92);
        dx.f[12] = 0xBB ^ 0xAF;
        dx.f[13] = 15 + 97 - 82 + 144 + (88 + 119 - 195 + 137) - (135 + 93 - 156 + 156) + (0x2C ^ 0x79);
        dx.f[14] = "   ".length();
        dx.f[15] = 0x59 ^ 0x55;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    private static boolean c(int n2) {
        return n2 >= 0;
    }

    public dx() {
        int[] nArray = new int[f[0]];
        nArray[dx.f[1]] = f[2];
        nArray[dx.f[3]] = f[4];
        this.l = nArray;
        this.n = new ad();
        this.i = 0L;
        this.j = f[1];
        this.g = f[3];
        this.o = f[1];
        this.p = f[1];
        this.e = f[1];
        this.h = f[5];
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    public static void a(ad ad2, ag ag2, int n2) {
        dx dx2 = new dx();
        dx2.l[dx.f[3]] = n2;
        n2 = f[1];
        while (dx.c(n2, ad2.size())) {
            ag ag3 = (ag)ad2.elementAt(n2);
            if (dx.b(ag3) && (!dx.c(fo.d(ag3.os - ag2.os), f[6]) || dx.d(fo.d(ag3.he - ag2.he), f[7]))) {
                ad2.removeElementAt(n2);
            }
            ++n2;
            "".length();
            if ((0xF2 ^ 0xA5 ^ (0x4D ^ 0x1E)) >= -" ".length()) continue;
            return;
        }
        dx2.a(ad2, ag2, f[3]);
        cl.c.addElement(dx2);
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean e(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }
}

