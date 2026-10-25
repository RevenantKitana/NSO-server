/*
 * Decompiled with CFR 0.152.
 */
public final class cu
extends ef
implements bv {
    private static int a;
    private static int b;
    private int c = j[0];
    private static eg d;
    private static cu e;
    private static int[][] f;
    private fr g;
    private static int[] h;
    private fr i;
    private static final int[] j;
    private static int k;
    private static int[] l;

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public final void f() {
        if (cu.d(em.j[j[3]]) && cu.c(a -= j[7])) {
            a = ag.m.length - j[7];
        }
        if (cu.d(em.j[j[25]]) && cu.a(a += j[7], ag.m.length)) {
            a = j[0];
        }
        if (cu.b(a)) {
            this.fw = cu.d.n;
            d.f();
        }
        if (cu.b(a, j[7])) {
            if (cu.d(em.j[j[19]]) && cu.c(b -= j[7])) {
                b = ag.aj.length - j[7];
            }
            if (cu.d(em.j[j[26]]) && cu.d(b += j[7], ag.aj.length - j[7])) {
                b = j[0];
            }
            this.fw = null;
        }
        if (cu.b(a, j[3])) {
            if (cu.d(em.j[j[19]]) && cu.c(k -= j[7])) {
                k = ag.rk[j[0]].length - j[7];
            }
            if (cu.d(em.j[j[26]]) && cu.d(k += j[7], ag.rk[j[0]].length - j[7])) {
                k = j[0];
            }
        }
        if (cu.d(em.q ? 1 : 0) && cu.d(em.c(ap.bh + j[27], ap.g + j[28], ap.fl - j[27], ef.fu) ? 1 : 0)) {
            a = j[0];
        }
        super.f();
        em.m();
        em.h();
    }

    public void a(eh eh2) {
    }

    static {
        cu.h();
        int[][] nArrayArray = new int[j[3]][];
        int[] nArray = new int[j[19]];
        nArray[cu.j[0]] = j[42];
        nArray[cu.j[7]] = j[12];
        nArray[cu.j[3]] = j[43];
        nArray[cu.j[20]] = j[44];
        nArrayArray[cu.j[0]] = nArray;
        int[] nArray2 = new int[j[19]];
        nArray2[cu.j[0]] = j[3];
        nArray2[cu.j[7]] = j[45];
        nArray2[cu.j[3]] = j[46];
        nArray2[cu.j[20]] = j[47];
        nArrayArray[cu.j[7]] = nArray2;
        f = nArrayArray;
        int[] nArray3 = new int[j[3]];
        nArray3[cu.j[0]] = j[48];
        nArray3[cu.j[7]] = j[0];
        l = nArray3;
        int[] nArray4 = new int[j[3]];
        nArray4[cu.j[0]] = j[34];
        nArray4[cu.j[7]] = j[7];
        h = nArray4;
    }

    public void b_() {
    }

    private static void h() {
        j = new int[49];
        cu.j[0] = (0x33 ^ 0x63 ^ (0xF9 ^ 0x80)) & (127 + 71 - 117 + 56 ^ 119 + 24 - 1 + 18 ^ -" ".length());
        cu.j[1] = 40 + 12 - 37 + 113;
        cu.j[2] = 0x73 ^ 0x5E ^ (0xE5 ^ 0xB0);
        cu.j[3] = "  ".length();
        cu.j[4] = 57 + 82 - 111 + 102 + (18 + 118 - 44 + 57) - (170 + 22 - 55 + 98) + (0xF8 ^ 0x86);
        cu.j[5] = (0x3F ^ 0x25) + (0x8C ^ 0xA8) - (0xB4 ^ 0x8E) + (184 + 110 - 194 + 86);
        cu.j[6] = 213 + 159 - 244 + 92;
        cu.j[7] = " ".length();
        cu.j[8] = 0x34 ^ 0x50;
        cu.j[9] = 0x86 ^ 0xAB;
        cu.j[10] = 0xFD ^ 0xC3;
        cu.j[11] = 0x34 ^ 8;
        cu.j[12] = 0xAB ^ 0xB1;
        cu.j[13] = -(0xFFFFE8EF & 0x77BF) & (0xFFFFFFEE & Short.MAX_VALUE);
        cu.j[14] = 0xFFFFFFF1 & 0x1F4F;
        cu.j[15] = 0xFFFFFF6F & 0x1FD2;
        cu.j[16] = 0x79 ^ 0x3F;
        cu.j[17] = 0x96 ^ 0x88;
        cu.j[18] = 0xFFFFDFFF & 0x3F43;
        cu.j[19] = 0xF ^ 0xB;
        cu.j[20] = "   ".length();
        cu.j[21] = -(0xFFFFB6A8 & 0x5F5F) & (0xFFFF9FFF & 0x7747);
        cu.j[22] = 29 + 38 - 18 + 87 + (0x79 ^ 0x30) - (0x25 ^ 0x12) + (7 ^ 1);
        cu.j[23] = 0x21 ^ 2;
        cu.j[24] = 130 + 104 - 170 + 129 ^ 150 + 88 - 192 + 107;
        cu.j[25] = 0x3F ^ 0x37;
        cu.j[26] = 0xA5 ^ 0xA3;
        cu.j[27] = 0x56 ^ 0x65 ^ (0x1B ^ 0x2D);
        cu.j[28] = 0xF3 ^ 0xB2;
        cu.j[29] = 0x16 ^ 0x3E;
        cu.j[30] = 0x8B ^ 0x9F;
        cu.j[31] = 23 + 31 - -57 + 20 ^ 169 + 79 - 231 + 160;
        cu.j[32] = -(0xBF ^ 0xBA);
        cu.j[33] = 0xFFFF9BDD & 0x67FF;
        cu.j[34] = 0xAE ^ 0xB5 ^ (0x33 ^ 0x22);
        cu.j[35] = 0x75 ^ 0x72;
        cu.j[36] = 0x16 ^ 0x46 ^ (0xD ^ 0x11);
        cu.j[37] = 1 ^ 0xE;
        cu.j[38] = 0x7D ^ 0x28;
        cu.j[39] = 0xD3 ^ 0x91;
        cu.j[40] = -(114 + 62 - 140 + 116 ^ 31 + 78 - 29 + 52);
        cu.j[41] = -(0xE4 ^ 0x99);
        cu.j[42] = 0x1E ^ 0x15;
        cu.j[43] = 0x25 ^ 0x3E;
        cu.j[44] = 0x20 ^ 0x3C;
        cu.j[45] = 0x61 ^ 0x23 ^ (0x63 ^ 0x36);
        cu.j[46] = 69 + 141 - 113 + 82 ^ 29 + 13 - 11 + 140;
        cu.j[47] = 0x73 ^ 0x6A;
        cu.j[48] = 78 + 111 - 14 + 16 ^ 167 + 137 - 125 + 3;
    }

    public final void c() {
        b = em.aa % j[3];
        k = em.aa % j[19];
        super.c();
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void a(bh bh2) {
        try {
            em.b(bh2);
            br.b(ap.bh, ap.g, ap.fl, ap.dg, bh2);
            int n2 = j[29];
            if (cu.b(em.aj, j[1])) {
                n2 = j[30];
            }
            int n3 = f[b][k];
            int n4 = l[b];
            int n5 = h[b];
            cz cz2 = ap.ep[n3];
            cz cz3 = ap.ep[n4];
            cz cz4 = ap.ep[n5];
            int n6 = em.aj / j[3];
            int n7 = ap.g + j[31];
            bd.a(bh2, cz2.a[ec.az[cu.j[0]][cu.j[0]][cu.j[0]]].a, n6 + ec.az[j[0]][j[0]][j[7]] + cz2.a[ec.az[cu.j[0]][cu.j[0]][cu.j[0]]].b, n7 - ec.az[j[0]][j[0]][j[3]] + cz2.a[ec.az[cu.j[0]][cu.j[0]][cu.j[0]]].c, j[0], j[0]);
            bd.a(bh2, cz3.a[ec.az[cu.j[0]][cu.j[7]][cu.j[0]]].a, n6 + ec.az[j[0]][j[7]][j[7]] + cz3.a[ec.az[cu.j[0]][cu.j[7]][cu.j[0]]].b, n7 - ec.az[j[0]][j[7]][j[3]] + cz3.a[ec.az[cu.j[0]][cu.j[7]][cu.j[0]]].c, j[0], j[0]);
            bd.a(bh2, cz4.a[ec.az[cu.j[0]][cu.j[3]][cu.j[0]]].a, n6 + ec.az[j[0]][j[3]][j[7]] + cz4.a[ec.az[cu.j[0]][cu.j[3]][cu.j[0]]].b, n7 - ec.az[j[0]][j[3]][j[3]] + cz4.a[ec.az[cu.j[0]][cu.j[3]][cu.j[0]]].c, j[0], j[0]);
            if (cu.b(em.w ? 1 : 0)) {
                n3 = j[0];
                while (cu.c(n3, ag.m.length)) {
                    if (cu.b(a, n3)) {
                        int n8;
                        int n9;
                        int n10;
                        if (cu.a(n3)) {
                            n10 = j[32];
                            "".length();
                            if ((0x38 ^ 0x3D) <= 0) {
                                return;
                            }
                        } else {
                            n10 = j[0];
                        }
                        n4 = n10;
                        int n11 = j[33];
                        int n12 = ap.bh + j[34];
                        if (cu.d(em.aa % j[35], j[20])) {
                            n9 = j[7];
                            "".length();
                            if (((0xFA ^ 0x9A) & ~(5 ^ 0x65)) == "   ".length()) {
                                return;
                            }
                        } else {
                            n9 = j[0];
                        }
                        bd.a(bh2, n11, n12 + n9, ap.g + j[36] + n3 * n2 + n4, j[3], d.h);
                        int n13 = j[33];
                        int n14 = ap.bh + ap.fl - j[37];
                        if (cu.d(em.aa % j[35], j[20])) {
                            n8 = j[7];
                            "".length();
                            if ("  ".length() <= -" ".length()) {
                                return;
                            }
                        } else {
                            n8 = j[0];
                        }
                        bd.a(bh2, n13, n14 - n8, ap.g + j[36] + n3 * n2 + n4, j[0], d.h);
                    }
                    ++n3;
                    "".length();
                    if ((0xB8 ^ 0xBC) == (0xC0 ^ 0xC4)) continue;
                    return;
                }
            }
            if (cu.d(em.w ? 1 : 0)) {
                this.i.d = ag.aj[b];
                this.i.a(bh2);
                this.g.d = ag.rk[b][k];
                this.g.a(bh2);
                "".length();
                if ((0x61 ^ 1 ^ (0 ^ 0x65)) <= 0) {
                    return;
                }
            } else {
                br.c(this.i.h, this.i.a, this.i.e, this.i.j, bh2);
                if (cu.b(a, j[7])) {
                    bh2.a(br.f);
                    bh2.e(this.i.h, this.i.a, this.i.e, this.i.j);
                    br.c(this.i.h, this.i.a, this.i.e, this.i.j, bh2);
                }
                br.c(this.g.h, this.g.a, this.g.e, this.g.j, bh2);
                if (cu.b(a, j[3])) {
                    bh2.a(br.f);
                    bh2.e(this.g.h, this.g.a, this.g.e, this.g.j);
                    br.c(this.g.h, this.g.a, this.g.e, this.g.j, bh2);
                }
                doa.v.a(bh2, ag.aj[b], ap.bh + j[38], ap.g + j[39] + n2, j[3]);
                doa.v.a(bh2, ag.rk[b][k], ap.bh + j[38], ap.g + j[39] + (n2 << j[7]), j[3]);
            }
            cu.d.j = ap.bh + ap.fl / j[3] - cu.d.d / j[3];
            cu.d.s = ap.g + j[10];
            cu.d.v = ag.la;
            d.a(bh2);
            super.a(bh2);
            bh2.a(j[0]);
            "".length();
        }
        catch (Exception exception) {
            System.out.println("CreateCharScr.paint(): 0");
            exception.printStackTrace();
        }
        if (" ".length() != " ".length()) {
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public void a(String string, String string2) {
    }

    public final void e() {
        if (cu.d(ap.fb += j[7], em.aj * j[20] + j[8])) {
            ap.fb = j[8];
        }
        if (cu.d(em.w ? 1 : 0) && cu.a(em.aj, j[21])) {
            if (cu.a((Object)this.ft)) {
                this.ft.h = em.aj / j[3] - j[22];
                this.ft.a = em.aq - j[12];
            }
            if (cu.a((Object)this.fx)) {
                this.fx.h = em.aj / j[3] - j[23];
                this.fx.a = em.aq - j[12];
            }
            if (cu.a((Object)this.fw)) {
                this.fw.h = em.aj / j[3] + j[24];
                this.fw.a = em.aq - j[12];
            }
        }
        if (cu.a((Object)this.g)) {
            this.g.a();
        }
        if (cu.a((Object)this.i)) {
            this.i.a();
        }
    }

    public final void h(int n2) {
        d.b(n2);
        "".length();
    }

    private static boolean c(int n2) {
        return n2 < 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    public void d() {
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    public static cu g() {
        if (cu.b(e)) {
            e = new cu();
        }
        return e;
    }

    public void b() {
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 8000: {
                dl dl2 = dl.h();
                int n3 = f[b][k];
                int n4 = b;
                String string = cu.d.h;
                dl dl3 = dl2;
                eh eh2 = new eh(j[40]);
                try {
                    eh2.d.writeByte(j[41]);
                    eh2.d.writeUTF(string);
                    eh2.d.writeByte(n4);
                    eh2.d.writeByte(n3);
                    "".length();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                if ("  ".length() >= (0xA4 ^ 0xA0)) {
                    return;
                }
                dl3.a.a(eh2);
                return;
            }
            case 8001: {
                if (cu.d(w.g().f ? 1 : 0)) {
                    em.au.c();
                    return;
                }
                w.g().c();
                return;
            }
            case 8002: {
                this.c = j[7];
                a = j[7];
                if (!cu.b(this.c, a) || !cu.c(b -= j[7])) break;
                b = ag.aj.length - j[7];
                return;
            }
            case 8003: {
                this.c = j[3];
                a = j[3];
                if (!cu.b(this.c, a) || !cu.d(k += j[7], ag.rk[j[0]].length - j[7])) break;
                k = j[0];
            }
        }
    }

    private static boolean b(Object object) {
        return object == null;
    }

    private static boolean d(int n2) {
        return n2 != 0;
    }

    public cu() {
        if (cu.b(em.aj, j[1])) {
            ap.b(j[1], j[2]);
            ap.bh = (em.aj - j[1]) / j[3];
            ap.g = j[0];
            "".length();
            if ("  ".length() <= 0) {
                throw null;
            }
        } else {
            ap.b(j[4], j[5]);
            ap.bh = (em.aj - j[4]) / j[3];
            ap.g = (em.aq - j[6]) / j[3];
        }
        b = j[7];
        d = new eg();
        new eg().l = ag.la;
        cu.d.d = j[8];
        cu.d.j = ap.bh + j[9];
        cu.d.j = ap.bh + ap.fl / j[3] - cu.d.d / j[3];
        cu.d.s = ap.g + j[10];
        if (cu.b(em.aj, j[1])) {
            cu.d.d = j[11];
        }
        cu.d.q = j[12];
        cu.d.a = j[7];
        cu.d.o = j[0];
        b = j[7];
        k = j[0];
        this.fx = new fr(ag.re, this, j[13], null);
        this.ft = new fr(ag.dn, this, j[14], null);
        this.i = new fr("", this, j[15], ap.bh + ap.fl / j[3] - ef.fv / j[3], ap.g + j[16] + j[17]);
        this.g = new fr("", this, j[18], ap.bh + ap.fl / j[3] - ef.fv / j[3], ap.g + j[16] + j[16]);
        this.fw = cu.d.n;
    }
}

