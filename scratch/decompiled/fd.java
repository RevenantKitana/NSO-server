/*
 * Decompiled with CFR 0.152.
 */
public final class fd
extends ax {
    private ad b;
    private ad c;
    private boolean d;
    private long e;
    private int ah;
    private boolean ai;
    private static final int[] aj;
    private String ak;
    private int al;
    public static boolean a;
    private boolean am;

    private void a(ec ec2) {
        int n2;
        int n3;
        el[] elArray = new el[aj[2]];
        int n4 = aj[1];
        int n5 = aj[1];
        int n6 = aj[1];
        if (fd.a(this.b)) {
            n3 = aj[1];
            while (fd.f(n3, this.b.size())) {
                Object object;
                an an2 = (an)this.b.elementAt(n3);
                n2 = (Integer)this.c.elementAt(n3);
                if (fd.e(an2.d.g ? 1 : 0)) {
                    object = ec.b(an2.d.d);
                    if (fd.b(object)) {
                        ey.c(this.ak, "<Xin l\u1ed7i, c\u00f3 l\u1ed7i ch\u1ecdn item>");
                        return;
                    }
                    dl.h().e(((el)object).n, n2);
                    bo.ak();
                    "".length();
                    object = ec.b(an2.d.d, n2);
                    if (fd.b(object)) {
                        ey.c(this.ak, "<Xin l\u1ed7i, c\u00f3 l\u1ed7i ch\u1ecdn item>");
                        return;
                    }
                    elArray[n4++] = object;
                    n6 = aj[0];
                    "".length();
                    if ((0x59 ^ 0x4B ^ (0x64 ^ 0x72)) == 0) {
                        return;
                    }
                } else {
                    object = ec.n();
                    int n7 = aj[1];
                    int n8 = aj[1];
                    while (fd.f(n7, n2)) {
                        block43: {
                            while (fd.f(n8, ((ec)object).en.length)) {
                                el el2 = ((ec)object).en[n8];
                                if (fd.a(el2) && fd.c(el2.m.d, an2.d.d)) {
                                    elArray[n4++] = el2;
                                    ++n8;
                                    "".length();
                                    if (((0x55 ^ 0x5C) & ~(0xB4 ^ 0xBD)) >= " ".length()) {
                                        return;
                                    }
                                    break block43;
                                }
                                ++n8;
                                "".length();
                                if (-" ".length() < ((0x49 ^ 0x4F) & ~(0x41 ^ 0x47))) continue;
                                return;
                            }
                            ey.c(this.ak, "<Xin l\u1ed7i, c\u00f3 l\u1ed7i ch\u1ecdn item>");
                            return;
                        }
                        ++n7;
                        "".length();
                        if (-"   ".length() < 0) continue;
                        return;
                    }
                }
                n5 += n2 * an2.c * aj[4] / an2.h;
                ++n3;
                "".length();
                if (((105 + 151 - 103 + 15 ^ 30 + 137 - 135 + 155) & (194 + 41 - 230 + 205 ^ 176 + 36 - 115 + 96 ^ -" ".length())) <= (8 ^ 0x4D ^ (0x3A ^ 0x7B))) continue;
                return;
            }
            if (fd.f(n5)) {
                n5 = aj[0];
            }
        }
        a = aj[1];
        if (fd.f(ec.a(ec2.s, ec2.fc) ? 1 : 0)) {
            ey.c(this.ak, "<H\u00e3y \u0111\u1ee9ng \u1edf n\u01a1i d\u1ec5 GD>");
            if (fd.e(n6)) {
                dl.h().aa();
                bo.ak();
                "".length();
                "".length();
                if (((24 + 61 - 70 + 113 ^ 97 + 14 - 20 + 82) & (0xF2 ^ 0xAE ^ (0x19 ^ 0x68) ^ -" ".length())) != 0) {
                    return;
                }
            }
        } else {
            dl.h().k(ec2.w);
            if (fd.f(bo.h() ? 1 : 0)) {
                if (fd.e(ap.bq ? 1 : 0)) {
                    dl.h().p();
                    ax.a(1000L);
                }
                if (fd.e(n6)) {
                    dl.h().aa();
                    bo.ak();
                    "".length();
                }
                ey.c(this.ak, "<H\u00e3y \u0111\u1ed3ng \u00fd giao d\u1ecbch>");
                "".length();
                if ("   ".length() != "   ".length()) {
                    return;
                }
            } else {
                ap.s().er = aj[1];
                ap.ag = new el[aj[2]];
                n3 = aj[1];
                while (fd.f(n3, aj[2])) {
                    ap.ag[n3] = elArray[n3];
                    if (fd.a(elArray[n3]) && fd.g(elArray[n3].n) && fd.f(elArray[n3].n, ec.n().en.length)) {
                        int n9 = elArray[n3].n;
                        ec.n().en[n9] = null;
                    }
                    ++n3;
                    "".length();
                    if ((0x3C ^ 0x38) == (0x56 ^ 0x52)) continue;
                    return;
                }
                dl.h().a(aj[1], elArray);
                ap.s().ao = aj[0];
                long l2 = System.currentTimeMillis();
                while (fd.e(ap.s().bc, aj[0])) {
                    if (!fd.f(a ? 1 : 0) || fd.g(fd.b(System.currentTimeMillis() - l2, 20000L))) {
                        if (fd.e(ap.bq ? 1 : 0)) {
                            dl.h().p();
                            ax.a(1000L);
                        }
                        if (fd.e(n6)) {
                            dl.h().aa();
                            bo.ak();
                            "".length();
                        }
                        ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                        return;
                    }
                    ax.a(200L);
                    "".length();
                    if (-"   ".length() <= 0) continue;
                    return;
                }
                l2 = System.currentTimeMillis();
                if (fd.f(ap.s().bz, n5)) {
                    if (fd.e(ap.bq ? 1 : 0)) {
                        dl.h().p();
                        ax.a(1000L);
                    }
                    if (fd.e(n6)) {
                        dl.h().aa();
                        bo.ak();
                        "".length();
                    }
                    ey.c(this.ak, "<Kh\u00f4ng \u0111\u1ee7 xu>");
                    "".length();
                    if ((0x76 ^ 0x73) <= 0) {
                        return;
                    }
                } else {
                    n2 = aj[1];
                    while (fd.f(n2, ap.es.length)) {
                        if (fd.a(ap.es[n2]) && fd.b(bp.a(ap.es[n2].m.d))) {
                            if (fd.e(ap.bq ? 1 : 0)) {
                                dl.h().p();
                                ax.a(1000L);
                            }
                            if (fd.e(n6)) {
                                dl.h().aa();
                                bo.ak();
                                "".length();
                            }
                            ey.c(this.ak, "<Ch\u1ec9 GD xu>");
                            return;
                        }
                        ++n2;
                        "".length();
                        if (" ".length() != 0) continue;
                        return;
                    }
                    while (fd.i(fd.b(System.currentTimeMillis() - l2, 6000L))) {
                        if (fd.e(a ? 1 : 0)) {
                            if (fd.e(n6)) {
                                dl.h().aa();
                                bo.ak();
                                "".length();
                            }
                            ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                            return;
                        }
                        ax.a(200L);
                        "".length();
                        if (null == null) continue;
                        return;
                    }
                    dl.h().v();
                    ap.s().ao = aj[5];
                    while (fd.f(ap.s().bc, aj[5])) {
                        if (!fd.f(a ? 1 : 0) || fd.g(fd.b(System.currentTimeMillis() - l2, 20000L))) {
                            if (fd.e(ap.bq ? 1 : 0)) {
                                dl.h().p();
                                ax.a(1000L);
                            }
                            if (fd.e(n6)) {
                                dl.h().aa();
                                bo.ak();
                                "".length();
                            }
                            ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                            return;
                        }
                        ax.a(200L);
                        "".length();
                        if (-"   ".length() < 0) continue;
                        return;
                    }
                }
            }
        }
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static void e() {
        aj = new int[6];
        fd.aj[0] = " ".length();
        fd.aj[1] = (0xD5 ^ 0x88) & ~(0x3D ^ 0x60);
        fd.aj[2] = 0x34 ^ 0x73 ^ (0x34 ^ 0x7F);
        fd.aj[3] = -" ".length();
        fd.aj[4] = -(0xFFFFDF4E & 0x2CB3) & (0xFFFFFFF9 & 0xFEF);
        fd.aj[5] = "  ".length();
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private void f(ec ec2) {
        int n2;
        int n3;
        an an2;
        long l2;
        el[] elArray = new el[aj[2]];
        a = aj[1];
        if (fd.f(this.am ? 1 : 0)) {
            if (fd.f(ec.a(ec2.s, ec2.fc) ? 1 : 0)) {
                ey.c(this.ak, "<H\u00e3y \u0111\u1ee9ng \u1edf n\u01a1i d\u1ec5 GD>");
                return;
            }
            dl.h().k(ec2.w);
            if (fd.f(bo.h() ? 1 : 0)) {
                if (fd.e(ap.bq ? 1 : 0)) {
                    dl.h().p();
                    ax.a(1000L);
                }
                ey.c(this.ak, "<H\u00e3y \u0111\u1ed3ng \u00fd giao d\u1ecbch>");
                return;
            }
        } else {
            l2 = System.currentTimeMillis();
            while (!fd.e(ap.bq ? 1 : 0) || fd.g(fd.a(System.currentTimeMillis() - l2, 10000L))) {
                ax.a(1000L);
                "".length();
                if (((1 ^ 0x6C ^ (0x38 ^ 0x49)) & (33 + 104 - 75 + 155 ^ 104 + 180 - 133 + 46 ^ -" ".length())) < "  ".length()) continue;
                return;
            }
        }
        l2 = System.currentTimeMillis();
        while (fd.e(ap.s().bc, aj[0])) {
            if (!fd.f(a ? 1 : 0) || fd.g(fd.a(System.currentTimeMillis() - l2, 20000L))) {
                if (fd.e(ap.bq ? 1 : 0)) {
                    dl.h().p();
                    ax.a(1000L);
                }
                ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                return;
            }
            ax.a(200L);
            "".length();
            if (null == null) continue;
            return;
        }
        ad ad2 = new ad();
        ad ad3 = new ad();
        int n4 = aj[1];
        while (fd.f(n4, ap.es.length)) {
            if (fd.a(ap.es[n4])) {
                int n5;
                int n6;
                an2 = bp.a(ap.es[n4].m.d);
                if (!fd.a(an2) || fd.f(an2.c() ? 1 : 0)) {
                    if (fd.e(ap.bq ? 1 : 0)) {
                        dl.h().p();
                        ax.a(1000L);
                    }
                    ey.c(this.ak, "<Item " + n4 + ": b\u1ea1n \u0111\u00e3 GD vp mk ko mua>");
                    return;
                }
                if (fd.e(ap.es[n4].m.g ? 1 : 0)) {
                    n6 = ap.es[n4].n;
                    "".length();
                    if (-"   ".length() > 0) {
                        return;
                    }
                } else {
                    n6 = n5 = aj[0];
                }
                if (fd.e(ad2.contains(an2) ? 1 : 0)) {
                    n3 = ad2.indexOf(an2);
                    n2 = (Integer)ad3.elementAt(n3) + n5;
                    ad3.setElementAt(new Integer(n2), n3);
                    "".length();
                    if (" ".length() < -" ".length()) {
                        return;
                    }
                } else {
                    ad2.addElement(an2);
                    ad3.addElement(new Integer(n5));
                }
            }
            ++n4;
            "".length();
            if ("   ".length() >= ((0xD3 ^ 0x91) & ~(0x62 ^ 0x20))) continue;
            return;
        }
        if (fd.f(ad2.size())) {
            if (fd.e(ap.bq ? 1 : 0)) {
                dl.h().p();
                ax.a(1000L);
            }
            ey.c(this.ak, "<Giao d\u1ecbch tr\u1ed1ng>");
            "".length();
            if (null != null) {
                return;
            }
        } else {
            n4 = aj[1];
            n2 = aj[1];
            while (fd.f(n2, ad2.size())) {
                an2 = (an)ad2.elementAt(n2);
                n3 = (Integer)ad3.elementAt(n2);
                if (fd.d(ec.l(an2.d.d) + n3, an2.f)) {
                    if (fd.e(ap.bq ? 1 : 0)) {
                        dl.h().p();
                        ax.a(1000L);
                    }
                    ey.c(this.ak, "<" + an2.d.e + " b\u1ea1n \u0111\u00e1 b\u00e1n qu\u00e1 s\u1ed1 mk c\u1ea7n mua>");
                    return;
                }
                n4 += n3 * an2.a * aj[4] / an2.b;
                ++n2;
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            if (fd.d(n4, ec.n().bt)) {
                if (fd.e(ap.bq ? 1 : 0)) {
                    dl.h().p();
                }
                ey.c(this.ak, "<Hi\u1ec7n mk ko \u0111\u1ee7 xu, xin b\u1ea1n quay l\u1ea1i sau>");
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                ap.s().er = n4;
                ap.ag = elArray;
                dl.h().a(n4, elArray);
                ap.s().ao = aj[0];
                l2 = System.currentTimeMillis();
                while (fd.i(fd.a(System.currentTimeMillis() - l2, 5000L))) {
                    if (fd.e(a ? 1 : 0)) {
                        ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                        return;
                    }
                    ax.a(200L);
                    "".length();
                    if (" ".length() >= 0) continue;
                    return;
                }
                dl.h().v();
                ap.s().ao = aj[5];
                while (fd.f(ap.s().er, aj[5])) {
                    if (!fd.f(a ? 1 : 0) || fd.g(fd.a(System.currentTimeMillis() - l2, 20000L))) {
                        if (fd.e(ap.bq ? 1 : 0)) {
                            dl.h().p();
                            ax.a(1000L);
                        }
                        ey.c(this.ak, "<H\u00e3y giao d\u1ecbch l\u1ea1i>");
                        return;
                    }
                    ax.a(200L);
                    "".length();
                    if (((0x15 ^ 0x6F ^ (0xE ^ 0x60)) & (70 + 40 - 54 + 79 ^ 99 + 139 - 137 + 46 ^ -" ".length())) < (0x57 ^ 0x60 ^ (0x95 ^ 0xA6))) continue;
                    return;
                }
            }
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean f(int n2) {
        return n2 == 0;
    }

    private static boolean g(int n2) {
        return n2 >= 0;
    }

    static {
        fd.e();
    }

    public final void a() {
        if (fd.e(ax.g() ? 1 : 0)) {
            ax.a(aj[0]);
            "".length();
            if ((23 + 115 - 15 + 70 ^ 118 + 177 - 173 + 75) == -" ".length()) {
                return;
            }
        } else if (fd.c(this.u, de.i) && fd.c(this.x, de.ab)) {
            if (fd.e(this.d ? 1 : 0)) {
                ec ec2;
                ec ec3;
                block23: {
                    String string = this.ak;
                    int n2 = aj[1];
                    do {
                        if (fd.g(n2, ap.dn.size())) {
                            ec3 = null;
                            "".length();
                            if (" ".length() <= 0) {
                                return;
                            }
                            break block23;
                        }
                        ec2 = (ec)ap.dn.elementAt(n2);
                        if (fd.e(ec2.cx.equals(string) ? 1 : 0)) {
                            ec3 = ec2;
                            "".length();
                            if ("   ".length() < "  ".length()) {
                                return;
                            }
                            break block23;
                        }
                        ++n2;
                        "".length();
                    } while ("   ".length() != 0);
                    return;
                }
                ec2 = ec3;
                if (fd.b(ec3)) {
                    if (fd.a(this.ak)) {
                        ey.c(this.ak, "<H\u00e3y \u0111\u1ebfn " + de.d[de.i] + " khu " + de.ab + " \u0111\u1ec3 giao d\u1ecbch>");
                    }
                    this.d = aj[1];
                    "".length();
                    if (-" ".length() >= ((0xB8 ^ 0x91 ^ (0x61 ^ 0x68)) & (105 + 40 - 72 + 100 ^ 90 + 46 - 95 + 100 ^ -" ".length()))) {
                        return;
                    }
                } else {
                    if (fd.e(this.ai ? 1 : 0)) {
                        this.f(ec2);
                        "".length();
                        if ("   ".length() != "   ".length()) {
                            return;
                        }
                    } else {
                        this.a(ec2);
                    }
                    ax.a(500L);
                    this.d = aj[1];
                }
                "".length();
                if ("  ".length() <= 0) {
                    return;
                }
            } else {
                String string;
                if (!fd.c(ec.n().s, this.al) || fd.e(ec.n().fc, this.ah)) {
                    ec.d(this.al, this.ah);
                    "".length();
                    ax.a(500L);
                }
                if (fd.g(fd.c(System.currentTimeMillis() - this.e, 5000L)) && fd.f((string = bp.d()).equals("") ? 1 : 0)) {
                    dl.h().e(string);
                    this.e = System.currentTimeMillis();
                }
                "".length();
                if (((101 + 50 - 43 + 96 ^ 73 + 41 - -22 + 12) & (58 + 160 - 129 + 125 ^ 57 + 138 - 126 + 73 ^ -" ".length())) > " ".length()) {
                    return;
                }
            }
        } else {
            this.a(this.u, this.x, aj[3], aj[3]);
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void a(String string, int n2) {
        if (fd.e(bp.e() ? 1 : 0)) {
            dl.h().y(n2);
            this.ak = string;
            this.ai = aj[0];
            this.am = aj[0];
            this.b = null;
            this.c = null;
            this.d = aj[0];
            "".length();
            if ("   ".length() < -" ".length()) {
                return;
            }
        } else {
            dl.h().y();
            ey.c(string, "<Hi\u1ec7n mk ko mua vp n\u00e0o>");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(String string, String string2) {
        String[] stringArray;
        block41: {
            stringArray = ey.b(string2, " ");
            if (fd.e(this.d ? 1 : 0)) {
                if (!fd.f(ap.bq ? 1 : 0)) return;
                ey.c(string, "<\u0110ang giao d\u1ecbch v\u1edbi ng\u01b0\u1eddi kh\u00e1c. Mong b\u1ea1n ch\u1edd gi\u00e2y l\u00e1t>");
                "".length();
                if ("  ".length() <= "   ".length()) return;
                return;
            }
            if (!fd.e(stringArray[aj[1]].toLowerCase().equals("buy") ? 1 : 0)) break block41;
            ad ad2 = new ad();
            ad ad3 = new ad();
            int n2 = aj[1];
            int n3 = aj[0];
            int n4 = stringArray.length - aj[0];
            while (fd.h(n3, n4)) {
                block38: {
                    String string3;
                    block42: {
                        string3 = stringArray[n3];
                        String string4 = stringArray[n3 + aj[0]];
                        an an2 = bp.a(string3);
                        if (!fd.a(an2) || !fd.a(an2.h)) break block42;
                        if (fd.a(an2.h) && fd.h(ec.l(an2.d.d), an2.i)) {
                            ey.c(string, "<" + string3 + " h\u1ebft h\u00e0ng>");
                            "".length();
                            if (-" ".length() > "  ".length()) {
                                return;
                            }
                            break block38;
                        } else {
                            int n5;
                            block37: {
                                try {
                                    n5 = Integer.parseInt(string4);
                                    if (!fd.h(n5)) break block37;
                                    ey.c(string, "<S\u1ed1 l\u01b0\u1ee3ng ph\u1ea3i l\u00e0 s\u1ed1 d\u01b0\u01a1ng v\u00e0 l\u1edbn h\u01a1n 0>");
                                    "".length();
                                    break block38;
                                }
                                catch (Exception exception) {
                                    ey.c(string, "<" + string3 + " s\u1ed1 l\u01b0\u1ee3ng l\u1ed7i>");
                                    "".length();
                                    if ("   ".length() < "   ".length()) {
                                        return;
                                    }
                                    break block38;
                                }
                            }
                            "".length();
                            if (fd.f(an2.b(), n5)) {
                                ey.c(string, "<" + string3 + " s\u1ed1 l\u01b0\u1ee3ng Item n\u00e0y hi\u1ec7n kh\u00f4ng \u0111\u1ee7>");
                                "".length();
                                if (((74 + 89 - 45 + 13 ^ 33 + 184 - 203 + 173) & (0x5B ^ 0x56 ^ (0x86 ^ 0xB3) ^ -" ".length())) != 0) {
                                    return;
                                }
                                break block38;
                            } else {
                                int n6;
                                int n7;
                                if (fd.e(an2.d.g ? 1 : 0)) {
                                    n7 = aj[0];
                                    "".length();
                                    if (" ".length() != " ".length()) {
                                        return;
                                    }
                                } else {
                                    n7 = n5;
                                }
                                if (fd.d(n2 + (n6 = n7), aj[2])) {
                                    ey.c(string, "<" + string3 + " s\u1ed1 l\u01b0\u1ee3ng \u0111\u1ea7y. VP sau s\u1ebd k dc t\u00ednh>");
                                    "".length();
                                    if (" ".length() != "  ".length()) break;
                                    return;
                                }
                                n2 += n6;
                                ad2.addElement(an2);
                                ad3.addElement(new Integer(n5));
                                "".length();
                                if (((0x66 ^ 0x2D ^ (0x35 ^ 0x50)) & (0x57 ^ 0x37 ^ (0x4B ^ 5) ^ -" ".length())) == (117 + 88 - 98 + 59 ^ 149 + 56 - 86 + 43)) {
                                    return;
                                }
                            }
                        }
                        break block38;
                    }
                    ey.c(string, "<" + string3 + " ko t\u1ed3n t\u1ea1i ho\u1eb7c mk ko b\u00e1n vp n\u00e0y>");
                }
                n3 += 2;
                "".length();
                if (((0xEA ^ 0xB6 ^ (0x58 ^ 0x51)) & (0x25 ^ 1 ^ (0xE1 ^ 0x90) ^ -" ".length())) > -" ".length()) continue;
                return;
            }
            if (fd.f(ad2.size())) {
                ey.c(string, "<Giao d\u1ecbch r\u1ed7ng>");
                "".length();
                if (-"  ".length() < 0) return;
                return;
            }
            this.ak = string;
            this.ai = aj[1];
            this.b = ad2;
            this.c = ad3;
            this.d = aj[0];
            "".length();
            if (("  ".length() & ("  ".length() ^ -" ".length())) < "  ".length()) return;
            return;
        }
        if (fd.f(stringArray[aj[1]].toLowerCase().equals("mua") ? 1 : 0)) {
            if (fd.e(stringArray[aj[1]].toLowerCase().equals("donate") ? 1 : 0)) {
                this.ak = string;
                this.ai = aj[1];
                this.b = null;
                this.c = null;
                this.d = aj[0];
                "".length();
                if ("   ".length() >= -" ".length()) return;
                return;
            }
            if (!fd.e(stringArray[aj[1]].toLowerCase().equals("sell") ? 1 : 0)) {
                ey.c(string, "<B\u00e1n h\u00e0ng t\u1ef1 \u0111\u1ed9ng v4 By Leo>");
                ey.c(string, "<Danh s\u00e1ch h\u00e0ng>");
                bp.b(string);
                ey.c(string, "<-------------------------->");
                bp.c(string);
                ey.c(string, "<-------------------------->");
                "".length();
                return;
            }
            if (fd.e(bp.e() ? 1 : 0)) {
                this.ak = string;
                this.ai = aj[0];
                this.am = aj[1];
                this.b = null;
                this.c = null;
                this.d = aj[0];
                "".length();
                if (-" ".length() < 0) return;
                return;
            }
            ey.c(string, "<Hi\u1ec7n mk ko mua vp n\u00e0o>");
            "".length();
            return;
        }
        ad ad4 = new ad();
        ad ad5 = new ad();
        int n8 = aj[1];
        int n9 = aj[0];
        int n10 = stringArray.length - aj[0];
        while (fd.h(n9, n10)) {
            block40: {
                String string5;
                block43: {
                    int n11;
                    an an3;
                    block39: {
                        String string6 = stringArray[n9];
                        string5 = stringArray[n9 + aj[0]];
                        an3 = bp.a(string5);
                        if (!fd.a(an3) || !fd.a(an3.h)) break block43;
                        if (fd.a(an3.h) && fd.h(ec.l(an3.d.d), an3.i)) {
                            ey.c(string, "<" + string5 + " h\u1ebft h\u00e0ng>");
                            "".length();
                            if (((46 + 116 - 75 + 81 ^ 140 + 147 - 170 + 40) & (41 + 158 - 141 + 189 ^ 176 + 176 - 291 + 133 ^ -" ".length())) < 0) {
                                return;
                            }
                            break block40;
                        } else {
                            try {
                                n11 = Integer.parseInt(string6);
                                if (!fd.h(n11)) break block39;
                                ey.c(string, "<S\u1ed1 l\u01b0\u1ee3ng ph\u1ea3i l\u00e0 s\u1ed1 d\u01b0\u01a1ng v\u00e0 l\u1edbn h\u01a1n 0>");
                                "".length();
                            }
                            catch (Exception exception) {
                                ey.c(string, "<" + string5 + " s\u1ed1 l\u01b0\u1ee3ng l\u1ed7i>");
                                "".length();
                                if ((("   ".length() ^ (0xF ^ 0x6E)) & (0xE7 ^ 0x8F ^ (0x7B ^ 0x71) ^ -" ".length())) < 0) {
                                    return;
                                }
                                break block40;
                            }
                            if ("   ".length() < 0) {
                                return;
                            }
                        }
                        break block40;
                    }
                    "".length();
                    if (((0x2F ^ 0x5E ^ (0x38 ^ 0x63)) & (13 + 102 - 73 + 144 ^ 63 + 100 - 84 + 65 ^ -" ".length())) != 0) {
                        return;
                    }
                    if (fd.f(an3.b(), n11)) {
                        ey.c(string, "<" + string5 + " s\u1ed1 l\u01b0\u1ee3ng Item n\u00e0y hi\u1ec7n kh\u00f4ng \u0111\u1ee7>");
                        "".length();
                        break block40;
                    } else {
                        int n12;
                        int n13;
                        if (fd.e(an3.d.g ? 1 : 0)) {
                            n13 = aj[0];
                            "".length();
                        } else {
                            n13 = n12 = n11;
                        }
                        if (fd.d(n8 + n12, aj[2])) {
                            ey.c(string, "<" + string5 + " s\u1ed1 l\u01b0\u1ee3ng \u0111\u1ea7y. VP sau s\u1ebd k dc t\u00ednh>");
                            "".length();
                            break;
                        }
                        n8 += n12;
                        ad4.addElement(an3);
                        ad5.addElement(new Integer(n11));
                        "".length();
                        if (((0x90 ^ 0x97) & ~(0x48 ^ 0x4F)) <= -" ".length()) {
                            return;
                        }
                    }
                    break block40;
                }
                ey.c(string, "<" + string5 + " ko t\u1ed3n t\u1ea1i ho\u1eb7c mk ko b\u00e1n vp n\u00e0y>");
            }
            n9 += 2;
            "".length();
            if (-"   ".length() < 0) continue;
            return;
        }
        if (fd.f(ad4.size())) {
            ey.c(string, "<Giao d\u1ecbch r\u1ed7ng>");
            "".length();
            return;
        }
        this.ak = string;
        this.ai = aj[1];
        this.b = ad4;
        this.c = ad5;
        this.d = aj[0];
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean h(int n2) {
        return n2 <= 0;
    }

    private static boolean i(int n2) {
        return n2 < 0;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public final String toString() {
        return "Auto Sell";
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void c() {
        super.c();
        this.u = de.i;
        this.x = de.ab;
        this.al = ec.n().s;
        this.ah = ec.n().fc;
        this.e = 0L;
        this.d = aj[1];
        bp.a = aj[3];
    }

    private static boolean f(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean g(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean h(int n2, int n3) {
        return n2 <= n3;
    }
}

