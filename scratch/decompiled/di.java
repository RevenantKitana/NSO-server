/*
 * Decompiled with CFR 0.152.
 */
public class di
extends ba {
    private int a;
    private static int[] b;
    private static int[] c;
    private static int[] d;
    private static int[] e;
    private static int[] ah;
    private static int[] ai;
    private static int[] aj;
    private static final int[] ak;

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    static {
        di.e();
        int[] nArray = new int[ak[18]];
        nArray[di.ak[2]] = ak[2];
        nArray[di.ak[1]] = ak[1];
        nArray[di.ak[16]] = ak[1];
        nArray[di.ak[21]] = ak[47];
        nArray[di.ak[10]] = ak[47];
        nArray[di.ak[7]] = ak[9];
        nArray[di.ak[15]] = ak[9];
        aj = nArray;
        int[] nArray2 = new int[ak[18]];
        nArray2[di.ak[2]] = ak[2];
        nArray2[di.ak[1]] = ak[3];
        nArray2[di.ak[16]] = ak[3];
        nArray2[di.ak[21]] = ak[11];
        nArray2[di.ak[10]] = ak[11];
        nArray2[di.ak[7]] = ak[48];
        nArray2[di.ak[15]] = ak[48];
        e = nArray2;
        int[] nArray3 = new int[ak[18]];
        nArray3[di.ak[2]] = ak[2];
        nArray3[di.ak[1]] = ak[2];
        nArray3[di.ak[16]] = ak[1];
        nArray3[di.ak[21]] = ak[2];
        nArray3[di.ak[10]] = ak[1];
        nArray3[di.ak[7]] = ak[2];
        nArray3[di.ak[15]] = ak[1];
        ah = nArray3;
        int[] nArray4 = new int[ak[18]];
        nArray4[di.ak[2]] = ak[2];
        nArray4[di.ak[1]] = ak[23];
        nArray4[di.ak[16]] = ak[24];
        nArray4[di.ak[21]] = ak[25];
        nArray4[di.ak[10]] = ak[26];
        nArray4[di.ak[7]] = ak[27];
        nArray4[di.ak[15]] = ak[28];
        d = nArray4;
        int[] nArray5 = new int[ak[18]];
        nArray5[di.ak[2]] = ak[5];
        nArray5[di.ak[1]] = ak[42];
        nArray5[di.ak[16]] = ak[49];
        nArray5[di.ak[21]] = ak[50];
        nArray5[di.ak[10]] = ak[51];
        nArray5[di.ak[7]] = ak[52];
        nArray5[di.ak[15]] = ak[53];
        b = nArray5;
        int[] nArray6 = new int[ak[18]];
        nArray6[di.ak[2]] = ak[5];
        nArray6[di.ak[1]] = ak[54];
        nArray6[di.ak[16]] = ak[38];
        nArray6[di.ak[21]] = ak[55];
        nArray6[di.ak[10]] = ak[56];
        nArray6[di.ak[7]] = ak[57];
        nArray6[di.ak[15]] = ak[58];
        c = nArray6;
        int[] nArray7 = new int[ak[18]];
        nArray7[di.ak[2]] = ak[5];
        nArray7[di.ak[1]] = ak[59];
        nArray7[di.ak[16]] = ak[60];
        nArray7[di.ak[21]] = ak[61];
        nArray7[di.ak[10]] = ak[62];
        nArray7[di.ak[7]] = ak[63];
        nArray7[di.ak[15]] = ak[64];
        ai = nArray7;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public di(int n2) {
        this.a = n2;
    }

    private static boolean d(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    public void a(ec ec2, byte by2, byte by3) {
        if (di.h(ec2.bu, ak[3])) {
            super.a(ec2, by2, by3);
            "".length();
            if ((0x39 ^ 0x3D) != (0x10 ^ 0x14)) {
                return;
            }
        } else {
            switch (ec2.bu) {
                case 9: {
                    if (di.h(ec2.dk.c)) {
                        if (di.e(de.i, ak[4])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[4], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.g(this.a)) {
                        ap.a("H\u00e3y v\u00e0o l\u1edbp!");
                        ey.q();
                        return;
                    }
                    int n2 = aj[this.a];
                    if (di.f(de.i, n2)) {
                        this.a(n2, ak[6], ak[5], ak[5]);
                        return;
                    }
                    ap.b(ak[7], ak[1], ak[2]);
                    int n3 = ak[2];
                    while (di.h(n3, ec2.en.length)) {
                        el el2 = ec2.en[n3];
                        if (di.b(el2) && (!di.f(el2.m.b, ak[8]) || di.e(el2.m.b, ak[9]))) {
                            dl.h().u(el2.n);
                        }
                        ++n3;
                        "".length();
                        if (((131 + 94 - 193 + 103 ^ 139 + 26 - 160 + 150) & (85 + 32 - 32 + 94 ^ 52 + 109 - 76 + 90 ^ -" ".length())) == 0) continue;
                        return;
                    }
                    ax.a(1000L);
                    el el3 = ec2.ff[ak[1]];
                    if (di.b(el3)) {
                        dl.h().a((short)el3.m.b);
                        bo.l();
                        "".length();
                    }
                    ap.b(e[this.a], ak[1], ah[this.a]);
                    do {
                        ax.a(1000L);
                    } while (!di.b(ec.b(d[this.a])));
                    el3 = ec.b(b[this.a]);
                    if (di.b(el3)) {
                        dl.h().u(el3.n);
                    }
                    if (di.b(el3 = ec.b(d[this.a]))) {
                        dl.h().u(el3.n);
                    }
                    ax.a(1000L);
                    ap.b(ak[10], ak[2], ak[2]);
                    int n4 = ak[2];
                    while (di.h(n4, ec2.en.length)) {
                        el3 = ec2.en[n4];
                        if (!(!di.b(el3) || di.c(el3.m.b, ak[11]) && di.f(el3.m.b, ak[12]) && di.f(el3.m.b, ak[13]) && !di.e(el3.m.d, ak[14]))) {
                            dl.h().a(el3.n, el3.n);
                        }
                        ++n4;
                        "".length();
                        if (((0xB2 ^ 0xAD ^ (0x42 ^ 0x1A)) & (91 + 51 - 60 + 110 ^ 115 + 100 - 152 + 72 ^ -" ".length())) == 0) continue;
                        return;
                    }
                    dl.h().aa();
                    bo.ak();
                    "".length();
                    return;
                }
                case 10: {
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[4])) {
                            this.d(ak[5]);
                            this.b(ak[7], ak[1]);
                            return;
                        }
                        this.a(ak[4], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.e(ec2.ds.e, ak[1])) {
                        if (di.e(de.i, ak[10])) {
                            this.d(ak[5]);
                            this.b(ak[15], ak[1]);
                            return;
                        }
                        this.a(ak[10], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (!di.e(ec2.ds.e, ak[16])) break;
                    if (di.e(de.i, ak[17])) {
                        this.d(ak[5]);
                        this.b(ak[18], ak[1]);
                        return;
                    }
                    this.a(ak[17], ak[5], ak[5], ak[5]);
                    return;
                }
                case 11: {
                    Object object;
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[4])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[4], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (!di.e(ec2.ds.e, ak[1])) break;
                    int n5 = ak[2];
                    while (di.h(n5, ap.dn.size())) {
                        object = (ec)ap.dn.elementAt(n5);
                        if (di.b(object)) {
                            dl.h().a(((ec)object).cx);
                        }
                        ++n5;
                        "".length();
                        if (" ".length() > -" ".length()) continue;
                        return;
                    }
                    int n6 = this.x;
                    object = ap.s();
                    ds ds2 = ap.b(ak[19]);
                    if (di.b((Object)ds2) && di.f(ds2.fj, ak[20])) {
                        if (!di.d(Math.abs(ds2.s - ec.n().s), ak[8]) || di.g(Math.abs(ds2.fc - ec.n().fc), ak[8])) {
                            ec.d(ds2.s, ds2.fc);
                            "".length();
                        }
                        dl.h().k();
                        bo.q();
                        n5 = ak[5];
                        if (di.f(n6)) {
                            n6 = ((ap)object).ed.length - ak[1];
                            "".length();
                            if (" ".length() < " ".length()) {
                                return;
                            }
                        } else if (di.c(n6, ((ap)object).ed.length)) {
                            n6 = ak[2];
                        }
                        int n7 = ak[2];
                        int n8 = (n6 + ak[1]) % ((ap)object).ed.length;
                        while (di.f(n8, n6)) {
                            if (di.h(((ap)object).ed[n8], ak[0]) && di.g(((ap)object).ed[n8], n7)) {
                                n5 = n8;
                                n7 = ((ap)object).ed[n8];
                            }
                            n8 = (n8 + ak[1]) % ((ap)object).ed.length;
                            "".length();
                            if ((0xBB ^ 0xBF) != -" ".length()) continue;
                            return;
                        }
                        this.x = n5;
                        dl.h().h(n5, ak[5]);
                        de.g();
                        ax.a(100L);
                        return;
                    }
                    this.x = de.ab;
                    return;
                }
                case 12: {
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[21])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[21], ak[5], ak[5], ak[5]);
                        return;
                    }
                    int n9 = ak[2];
                    int n10 = ak[5];
                    el el4 = null;
                    if (di.e(ec2.ds.e, ak[1])) {
                        n9 = ak[1];
                        int[] nArray = new int[ak[18]];
                        nArray[di.ak[2]] = ak[22];
                        nArray[di.ak[1]] = ak[23];
                        nArray[di.ak[16]] = ak[24];
                        nArray[di.ak[21]] = ak[25];
                        nArray[di.ak[10]] = ak[26];
                        nArray[di.ak[7]] = ak[27];
                        nArray[di.ak[15]] = ak[28];
                        n10 = nArray[ec2.dk.c];
                        el4 = ec2.ff[ak[1]];
                        if (di.a(el4)) {
                            n9 = ak[2];
                            el4 = ec.b(n10);
                            "".length();
                            if (" ".length() <= 0) {
                                return;
                            }
                        }
                    } else if (di.e(ec2.ds.e, ak[16])) {
                        n9 = ak[1];
                        n10 = ak[29];
                        el4 = ec2.ff[ak[3]];
                        if (di.a(el4)) {
                            n9 = ak[2];
                            el4 = ec.b(ak[29]);
                            "".length();
                            if (" ".length() == "  ".length()) {
                                return;
                            }
                        }
                    } else if (di.e(ec2.ds.e, ak[21])) {
                        int n11;
                        n9 = ak[1];
                        if (di.e(ec2.ch, ak[1])) {
                            n11 = ak[30];
                            "".length();
                            if ("   ".length() == 0) {
                                return;
                            }
                        } else {
                            n11 = n10 = ak[31];
                        }
                        if (di.a(el4 = ec2.ff[ak[32]])) {
                            n9 = ak[2];
                            el4 = ec.b(n10);
                        }
                    }
                    if (di.a(el4)) {
                        if (di.e(de.i, ak[10])) {
                            this.d(n10);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[10], ak[5], ak[5], ak[5]);
                        return;
                    }
                    int n12 = ak[2];
                    int n13 = ak[2];
                    if (di.h(el4.o() ? 1 : 0)) {
                        n12 = ap.de[el4.y] / ak[16];
                        n13 = ap.cx[el4.y];
                        "".length();
                        if ("   ".length() <= ((0x4C ^ 0x26 ^ (0x16 ^ 0x77)) & (0x77 ^ 0xB ^ (0xDA ^ 0xAD) ^ -" ".length()))) {
                            return;
                        }
                    } else if (di.h(el4.s() ? 1 : 0)) {
                        n12 = ap.ec[el4.y] / ak[16];
                        n13 = ap.v[el4.y];
                        "".length();
                        if (-"  ".length() > 0) {
                            return;
                        }
                    } else if (di.h(el4.l() ? 1 : 0)) {
                        n12 = ap.eg[el4.y] / ak[16];
                        n13 = ap.ez[el4.y];
                    }
                    if (!di.d(n12 << ak[1], ec.s()) || di.g(n13 << ak[1], ec2.cg)) {
                        if (di.e(de.i, ak[17])) {
                            this.d(ak[1]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[17], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.f(de.i, ak[8])) {
                        this.a(ak[8], ak[6], ak[5], ak[5]);
                        return;
                    }
                    if (di.h(n9)) {
                        dl.h().ac(el4.m.b);
                        bo.l();
                        "".length();
                    }
                    int n14 = el4.y;
                    ap.b(ak[15], ak[2], ak[2]);
                    bo.l();
                    "".length();
                    ap.fn = el4;
                    int n15 = ak[2];
                    while (di.h(n15, ak[16]) && di.e(el4.y, n14)) {
                        ap.bt = new el[ak[33]];
                        int n16 = ak[2];
                        int n17 = ak[2];
                        int n18 = ak[2];
                        while (di.h(n18, ec2.en.length) && di.h(n17, n12)) {
                            el el5 = ec2.en[n18];
                            if (di.b(el5) && di.e(el5.m.b, ak[34]) && di.d(el5.m.d, ak[21])) {
                                ec2.en[n18] = null;
                                ap.bt[n16++] = el5;
                                n17 += ap.de[el5.m.d];
                            }
                            ++n18;
                            "".length();
                            if (-(0xFC ^ 0xA1 ^ (6 ^ 0x5F)) <= 0) continue;
                            return;
                        }
                        do {
                            ax.a(3000L);
                            dl.h().a(el4, ap.bt);
                            bo.l();
                            "".length();
                        } while (!di.a(ap.bt[ak[2]]));
                        ++n15;
                        "".length();
                        if ("   ".length() != -" ".length()) continue;
                        return;
                    }
                    ap.fn = null;
                    dl.h().u(el4.n);
                    if (!di.g(el4.y, n14)) break;
                    bo.r();
                    return;
                }
                case 13: {
                    ec ec3;
                    int n19;
                    int n20;
                    el el6 = ec2.ff[ak[1]];
                    if (di.b(el6) && di.h(el6.y, ak[16])) {
                        int n21 = ap.eg[el6.y] / ak[16];
                        int n22 = ap.ez[el6.y];
                        if (di.d(n21 << ak[1], ec.s()) && di.d(n22 << ak[1], ec2.cg)) {
                            if (di.f(de.i, ak[8])) {
                                this.a(ak[8], ak[6], ak[5], ak[5]);
                                return;
                            }
                            dl.h().ac(el6.m.b);
                            bo.l();
                            "".length();
                            int n23 = el6.y;
                            ap.b(ak[15], ak[2], ak[2]);
                            bo.l();
                            "".length();
                            ap.fn = el6;
                            int n24 = ak[2];
                            while (di.h(n24, ak[16]) && di.e(el6.y, n23)) {
                                ap.bt = new el[ak[33]];
                                int n25 = ak[2];
                                int n26 = ak[2];
                                int n27 = ak[2];
                                while (di.h(n27, ec2.en.length) && di.h(n26, n21)) {
                                    el el7 = ec2.en[n27];
                                    if (di.b(el7) && di.e(el7.m.b, ak[34]) && di.d(el7.m.d, ak[21])) {
                                        ec2.en[n27] = null;
                                        ap.bt[n25++] = el7;
                                        n26 += ap.de[el7.m.d];
                                    }
                                    ++n27;
                                    "".length();
                                    if ((0x94 ^ 0x90) != -" ".length()) continue;
                                    return;
                                }
                                do {
                                    ax.a(3000L);
                                    dl.h().a(el6, ap.bt, ak[2]);
                                    bo.l();
                                    "".length();
                                } while (!di.a(ap.bt[ak[2]]));
                                ++n24;
                                "".length();
                                if (-" ".length() < 0) continue;
                                return;
                            }
                            ap.fn = null;
                            dl.h().u(el6.n);
                            return;
                        }
                        if (di.e(de.i, ak[10])) {
                            this.d(ak[1]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[10], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[10])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[10], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.e(ec2.ds.e, ak[1])) {
                        n20 = ak[35];
                        "".length();
                        if (((0x41 ^ 0x15) & ~(2 ^ 0x56)) > (0xA3 ^ 0xA7)) {
                            return;
                        }
                    } else if (di.e(ec2.ds.e, ak[16])) {
                        n20 = ak[2];
                        "".length();
                        if (" ".length() > "  ".length()) {
                            return;
                        }
                    } else {
                        n20 = ak[36];
                    }
                    if (di.f(de.i, n19 = n20)) {
                        if (di.f(de.i, by2)) {
                            super.a(by2, ak[6], ak[5], ak[5]);
                            return;
                        }
                        if (di.h(ap.ap, ak[11]) && di.c(ec2.cg, ak[37] * (ak[11] - ap.ap))) {
                            ap.b(ak[21], ak[2], ak[2]);
                            dl.h().c(ak[18], ak[1], ak[11] - ap.ap);
                            bo.aa();
                            return;
                        }
                        ap.b(by3, ak[2], ak[2]);
                        dl.h().b((int)by3, ak[2]);
                        de.g();
                        return;
                    }
                    if (di.h(ec2.cl, ec2.fd / ak[16]) && di.a(ec2.cl)) {
                        ec2.m(ak[12]);
                        "".length();
                    }
                    if (di.h(ec2.fb, ec2.dq / ak[16]) && di.a(ec2.cl)) {
                        ec2.m(ak[13]);
                        "".length();
                    }
                    if (!di.a(ap.dn.size()) || !di.b((Object)(ec3 = (ec)ap.dn.elementAt(ak[2])))) break;
                    cv cv2 = ax.z;
                    if (!di.d(fo.d(ec2.s - ec3.s), cv2.i) || di.g(fo.d(ec2.fc - ec3.fc), cv2.a)) {
                        int n28;
                        if (di.h(ec3.s, de.c)) {
                            n28 = ec3.s;
                            "".length();
                            if (((0x6D ^ 0x72) & ~(0xA1 ^ 0xBE)) != ((0x12 ^ 0x29) & ~(0xE ^ 0x35))) {
                                return;
                            }
                        } else {
                            n28 = de.c - ak[38];
                        }
                        ec.d(n28, ec3.fc);
                        "".length();
                    }
                    ax.n.removeAllElements();
                    ax.w.removeAllElements();
                    ax.w.addElement(ec3);
                    dl.h().a(ax.n, ax.w, ak[1]);
                    if (di.e(di.a(System.currentTimeMillis() - cv2.c, (long)cv2.g))) {
                        cv2.c = System.currentTimeMillis();
                        cv2.f = ak[1];
                        ec2.b(ap.ch[cv2.k.d], ak[2]);
                        return;
                    }
                    "".length();
                    if ("  ".length() != -" ".length()) break;
                    return;
                }
                case 14: {
                    el el8;
                    if (di.c(ec2.aa, ak[20]) && di.b(el8 = ec.b(c[ec2.dk.c]))) {
                        ap.a("H\u1ecdc s\u00e1ch k\u0129 n\u0103ng");
                        dl.h().u(el8.n);
                        ax.a(1000L);
                    }
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[39])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[39], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.e(ec2.ds.e, ak[1])) {
                        if (di.e(de.i, ak[39]) && di.e(this.x, de.ab)) {
                            int n29;
                            if (di.f(ey.ae)) {
                                n29 = ak[5];
                                "".length();
                                if (((0xD5 ^ 0xB4) & ~(0xE9 ^ 0x88)) != 0) {
                                    return;
                                }
                            } else {
                                n29 = ey.ae;
                                n29 *= n29;
                            }
                            int n30 = n29;
                            fm fm2 = null;
                            int n31 = ak[2];
                            while (di.h(n31, ap.ff.size())) {
                                fm fm3 = (fm)ap.ff.elementAt(n31);
                                int n32 = Math.abs(fm3.g - ec2.s);
                                int n33 = Math.abs(fm3.d - ec2.fc);
                                int n34 = n32 * n32 + n33 * n33;
                                if (di.g(fm3.i ? 1 : 0) && di.e(fm3.k.d, ak[40]) && (!di.d(ec.u(), ak[16]) || di.h(ec.k(ak[40]) ? 1 : 0)) && (!di.e(n30) || di.h(n34, n30))) {
                                    n30 = n34;
                                    fm2 = fm3;
                                }
                                ++n31;
                                "".length();
                                if (null == null) continue;
                                return;
                            }
                            if (di.a(fm2)) {
                                this.x = (this.x + ak[1]) % ak[41];
                                return;
                            }
                            ec.d(fm2.h, fm2.a);
                            "".length();
                            dl.h().r(fm2.l);
                            n31 = ak[2];
                            while (di.h(n31, ak[7]) && di.g(bo.ab() ? 1 : 0)) {
                                ++n31;
                                "".length();
                                if ((0x97 ^ 0x85 ^ (1 ^ 0x17)) != 0) continue;
                                return;
                            }
                            fm2.i = ak[1];
                            return;
                        }
                        this.a(ak[39], this.x, ak[5], ak[5]);
                        return;
                    }
                    if (!di.e(ec2.ds.e, ak[16])) break;
                    if (di.e(de.i, ak[42])) {
                        this.b(ak[20], ak[1]);
                        this.d(ak[43]);
                        return;
                    }
                    this.a(ak[42], ak[5], ak[5], ak[5]);
                    return;
                }
                case 15: {
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[32])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[32], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.f(de.i, by2)) {
                        super.a(by2, ak[6], ak[5], ak[5]);
                        return;
                    }
                    ap.b(by3, ak[2], ak[2]);
                    bo.r();
                    ax.h();
                    return;
                }
                case 16: {
                    el el9;
                    if (di.c(ec2.aa, ak[0]) && di.b(el9 = ec.b(ai[ec2.dk.c]))) {
                        ap.a("H\u1ecdc s\u00e1ch k\u0129 n\u0103ng");
                        dl.h().u(el9.n);
                        ax.a(1000L);
                    }
                    if (di.g(ec2.ds.e)) {
                        if (di.e(de.i, ak[32])) {
                            this.d(ak[5]);
                            this.b(ak[5], ak[1]);
                            return;
                        }
                        this.a(ak[32], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (di.e(ec2.ds.e, ak[1])) {
                        if (di.e(de.i, ak[44])) {
                            this.d(ak[5]);
                            this.b(ak[14], ak[1]);
                            return;
                        }
                        this.a(ak[44], ak[5], ak[5], ak[5]);
                        return;
                    }
                    if (!di.e(ec2.ds.e, ak[16])) break;
                    if (di.e(de.i, ak[45])) {
                        this.d(ak[5]);
                        this.b(ak[46], ak[1]);
                        return;
                    }
                    this.a(ak[45], ak[5], ak[5], ak[5]);
                }
            }
        }
    }

    public String toString() {
        return "Auto Nhi\u1ec7m V\u1ee5 20";
    }

    private static boolean e(int n2) {
        return n2 >= 0;
    }

    private static boolean f(int n2) {
        return n2 < 0;
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }

    public boolean a(ec ec2) {
        boolean bl2;
        if (di.c(ec2.aa, ak[0])) {
            bl2 = ak[1];
            "".length();
            if ((0x2C ^ 0x28) != (0x50 ^ 0x54)) {
                return ((0xB7 ^ 0xBA) & ~(0x47 ^ 0x4A)) != 0;
            }
        } else {
            bl2 = ak[2];
        }
        return bl2;
    }

    private static boolean f(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean g(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    private static void e() {
        ak = new int[65];
        di.ak[0] = 144 + 82 - 194 + 140 ^ 160 + 62 - 77 + 39;
        di.ak[1] = " ".length();
        di.ak[2] = (7 ^ 0x19 ^ (0x38 ^ 9)) & (10 + 73 - -26 + 32 ^ 40 + 39 - -70 + 13 ^ -" ".length());
        di.ak[3] = 0xCF ^ 0x99 ^ (0x25 ^ 0x7A);
        di.ak[4] = 0x47 ^ 0x5B;
        di.ak[5] = -" ".length();
        di.ak[6] = -"  ".length();
        di.ak[7] = 0x6B ^ 0x6E;
        di.ak[8] = 0x44 ^ 0x7D ^ (0x60 ^ 0x4F);
        di.ak[9] = 0x68 ^ 0x73;
        di.ak[10] = 122 + 84 - 125 + 52 ^ 68 + 125 - 152 + 88;
        di.ak[11] = 0x6E ^ 0x49 ^ (0xB ^ 0x26);
        di.ak[12] = 0x79 ^ 0x69;
        di.ak[13] = 0x6F ^ 0x7E;
        di.ak[14] = 16 + 80 - 50 + 92 ^ 81 + 111 - 115 + 80;
        di.ak[15] = 0x2D ^ 0x2B;
        di.ak[16] = "  ".length();
        di.ak[17] = 0x34 ^ 0x1A;
        di.ak[18] = 6 ^ 1;
        di.ak[19] = 0x8B ^ 0x86;
        di.ak[20] = 0x59 ^ 0x56;
        di.ak[21] = "   ".length();
        di.ak[22] = 190 + 111 - 269 + 162;
        di.ak[23] = 0x3E ^ 0x19 ^ (0x5E ^ 0x27);
        di.ak[24] = 0x44 ^ 0x36;
        di.ak[25] = 0x2A ^ 0x49;
        di.ak[26] = 0x57 ^ 0x3A;
        di.ak[27] = 0xA ^ 0x1E ^ (0x70 ^ 0xD);
        di.ak[28] = 0x4C ^ 0x3B;
        di.ak[29] = 78 + 170 - 194 + 120;
        di.ak[30] = 171 + 66 - 32 + 28 ^ 24 + 67 - 41 + 99;
        di.ak[31] = 0xE9 ^ 0x94;
        di.ak[32] = 102 + 78 - 164 + 162 ^ 10 + 122 - 12 + 66;
        di.ak[33] = 0x3D ^ 0x2F;
        di.ak[34] = 0x70 ^ 0x13 ^ (0x61 ^ 0x18);
        di.ak[35] = 0xC6 ^ 0xC2 ^ (0x60 ^ 0x5C);
        di.ak[36] = 100 + 151 - 62 + 29 ^ 105 + 109 - 114 + 47;
        di.ak[37] = -(0xFFFFBFE5 & 0x769B) & (0xFFFFB7FE & 0x7FAD);
        di.ak[38] = 0xAE ^ 0x9C;
        di.ak[39] = 0x46 ^ 0x5B;
        di.ak[40] = 42 + 49 - -51 + 11 + " ".length() - -(0xBE ^ 0x9E) + (0x2A ^ 0x30);
        di.ak[41] = 0x39 ^ 0x27;
        di.ak[42] = 0x3F ^ 0x17;
        di.ak[43] = (0x64 ^ 0x6E) + (96 + 126 - 205 + 110) - -(0x4E ^ 0x52) + (0x9C ^ 0xAC);
        di.ak[44] = 0x20 ^ 0x1F;
        di.ak[45] = 0x2C ^ 0x51 ^ (0xC4 ^ 0x96);
        di.ak[46] = 62 + 175 - 101 + 52 ^ 0 + 39 - 15 + 140;
        di.ak[47] = 156 + 151 - 146 + 45 ^ 74 + 91 - 163 + 132;
        di.ak[48] = 0x6E ^ 0x65;
        di.ak[49] = 137 + 102 - 171 + 77 ^ 85 + 152 - 155 + 78;
        di.ak[50] = 0xA5 ^ 0x9F;
        di.ak[51] = 0x22 ^ 0x49 ^ (0x56 ^ 0x7E);
        di.ak[52] = 0x1E ^ 0x65 ^ (0x61 ^ 0x56);
        di.ak[53] = 0x76 ^ 0x32 ^ (0x2A ^ 0x3B);
        di.ak[54] = 0x99 ^ 0xB0;
        di.ak[55] = 0x60 ^ 0x52 ^ (0x95 ^ 0x9C);
        di.ak[56] = 0x1C ^ 0x58;
        di.ak[57] = 0x59 ^ 0x14;
        di.ak[58] = 0x92 ^ 0xC4;
        di.ak[59] = 0xBB ^ 0xA5 ^ (0x66 ^ 0x52);
        di.ak[60] = 0x32 ^ 1;
        di.ak[61] = 0x3D ^ 0x4E ^ (0x88 ^ 0xC7);
        di.ak[62] = 0xCE ^ 0x8B;
        di.ak[63] = 0x3B ^ 0x1E ^ (0x49 ^ 0x22);
        di.ak[64] = 0x25 ^ 0xE ^ (0xBF ^ 0xC3);
    }

    private static boolean h(int n2) {
        return n2 != 0;
    }

    private static boolean h(int n2, int n3) {
        return n2 < n3;
    }
}

