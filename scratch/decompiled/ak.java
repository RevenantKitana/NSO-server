/*
 * Decompiled with CFR 0.152.
 */
import java.util.Calendar;

public final class ak
extends ax {
    private static final int[] ah;
    private boolean ai;
    public static boolean a;
    private boolean aj;
    private boolean ak;
    public int b;
    private int al;
    private int am;
    public static boolean c;
    private int an;
    private long ao;
    private long ap;
    private int aq;
    public int d;
    public static boolean e;
    private boolean ar;

    private static boolean a(int n2) {
        return n2 < 0;
    }

    public final String toString() {
        String string;
        if (ak.f(this.ai ? 1 : 0) && ak.f(this.ak ? 1 : 0)) {
            return "Stanima Buff HS";
        }
        if (ak.f(this.ai ? 1 : 0)) {
            return "Stanima Buff";
        }
        if (ak.f(this.ak ? 1 : 0)) {
            return "Stanima HS";
        }
        if (ak.h(this.b) && ak.c(this.b, by.h.length)) {
            string = "Stanima " + by.h[this.b].j;
            "".length();
            if ((0x1C ^ 0x19) == 0) {
                return null;
            }
        } else {
            string = "Stanima";
        }
        return string;
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean e(int n2) {
        return n2 > 0;
    }

    public final void d() {
        if (ak.g(this.d)) {
            this.aq = fo.a().get(ah[5]);
            if (ak.g(de.e(this.u) ? 1 : 0)) {
                this.d = ah[4];
                this.ao = System.currentTimeMillis();
                this.an = this.u;
                this.am = this.x;
                this.ar = this.j;
                this.aj = ah[0];
                e = ah[0];
                a = ah[0];
                return;
            }
        } else {
            if (ak.e(this.d, ah[1])) {
                this.d = ah[37];
                this.ao = System.currentTimeMillis();
                this.aj = ah[0];
                return;
            }
            if (ak.e(this.d, ah[4])) {
                this.d = ah[1];
                this.ao = System.currentTimeMillis();
                c = ah[0];
                return;
            }
            if (ak.e(this.d, ah[37])) {
                this.d = ah[0];
                this.aj = ah[0];
                ey.g();
                return;
            }
            if (ak.e(this.d, ah[38])) {
                this.d = ah[0];
                this.aj = ah[0];
            }
        }
    }

    private static boolean f(int n2) {
        return n2 != 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    public final void b() {
        this.ap = 0L;
        super.b();
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean h(int n2) {
        return n2 >= 0;
    }

    public final void a(String string) {
        if (ak.a(ey.v) && ak.g(de.e(this.u) ? 1 : 0) && ak.f(string.startsWith("Th\u1ea7n th\u00fa \u0111\u00e3 xu\u1ea5t hi\u1ec7n t\u1ea1i") ? 1 : 0)) {
            if (ak.f(ec.n().cx.equals(ey.v) ? 1 : 0)) {
                int n2 = ec.n().aa;
                ad ad2 = new ad();
                ad ad3 = new ad();
                if (ak.f(n2, ah[6]) && ak.g(n2, ah[7])) {
                    if (ak.e(string.indexOf("\u0110\u1ea3o Hebi"))) {
                        ad2.addElement(new Integer(ah[8]));
                        ad3.addElement(new Integer(ah[9]));
                    }
                    if (ak.e(string.indexOf("Hang Meiro"))) {
                        ad2.addElement(new Integer(ah[10]));
                        ad3.addElement(new Integer(ah[9]));
                    }
                    if (ak.e(string.indexOf("R\u1eebng Kappa"))) {
                        ad2.addElement(new Integer(ah[11]));
                        ad3.addElement(new Integer(ah[12]));
                    }
                    if (ak.e(string.indexOf("R\u1eebng Aokigahara"))) {
                        ad2.addElement(new Integer(ah[13]));
                        ad3.addElement(new Integer(ah[14]));
                    }
                    if (ak.e(string.indexOf("R\u1eebng Aokigahara"))) {
                        ad2.addElement(new Integer(ah[13]));
                        ad3.addElement(new Integer(ah[14]));
                    }
                    if (ak.e(string.indexOf("V\u00e1ch n\u00fai Ito"))) {
                        ad2.addElement(new Integer(ah[15]));
                        ad3.addElement(new Integer(ah[14]));
                    }
                    if (ak.e(string.indexOf("N\u00fai Anzen"))) {
                        ad2.addElement(new Integer(ah[16]));
                        ad3.addElement(new Integer(ah[17]));
                    }
                    if (ak.e(string.indexOf("Thung l\u0169ng Taira"))) {
                        ad2.addElement(new Integer(ah[18]));
                        ad3.addElement(new Integer(ah[14]));
                    }
                }
                if (ak.f(n2, ah[19]) && ak.g(n2, ah[20])) {
                    if (ak.e(string.indexOf("N\u00fai Ontake"))) {
                        ad2.addElement(new Integer(ah[21]));
                        ad3.addElement(new Integer(ah[17]));
                    }
                    if (ak.e(string.indexOf("\u0110\u1ec9nh Okama"))) {
                        ad2.addElement(new Integer(ah[22]));
                        ad3.addElement(new Integer(ah[23]));
                    }
                }
                if (ak.f(n2, ah[7]) && ak.g(n2, ah[24])) {
                    if (ak.e(string.indexOf("Khu \u0111\u00e1 \u0111\u1ecf Akai"))) {
                        ad2.addElement(new Integer(ah[25]));
                        ad3.addElement(new Integer(ah[23]));
                    }
                    if (ak.e(string.indexOf("M\u0169i Hone"))) {
                        ad2.addElement(new Integer(ah[26]));
                        ad3.addElement(new Integer(ah[17]));
                    }
                    if (ak.e(string.indexOf("\u0110\u1ec9nh Ichidai"))) {
                        ad2.addElement(new Integer(ah[27]));
                        ad3.addElement(new Integer(ah[17]));
                    }
                    if (ak.e(string.indexOf("Hang n\u00fai Kurai"))) {
                        ad2.addElement(new Integer(ah[28]));
                        ad3.addElement(new Integer(ah[23]));
                    }
                }
                if (ak.f(n2, ah[20]) && ak.g(n2, ah[29])) {
                    if (ak.e(string.indexOf("Ng\u00f4i \u0111\u1ec1n Orochi"))) {
                        ad2.addElement(new Integer(ah[30]));
                        ad3.addElement(new Integer(ah[31]));
                    }
                    if (ak.e(string.indexOf("\u0110\u1ed3ng Kisei"))) {
                        ad2.addElement(new Integer(ah[32]));
                        ad3.addElement(new Integer(ah[17]));
                    }
                    if (ak.e(string.indexOf("\u0110\u1ec1n Harumoto"))) {
                        ad2.addElement(new Integer(ah[33]));
                        ad3.addElement(new Integer(ah[23]));
                    }
                }
                if (ak.f(n2, ah[34])) {
                    if (ak.e(string.indexOf("Sinh T\u1eed Ki\u1ec1u"))) {
                        ad2.addElement(new Integer(ah[35]));
                        ad3.addElement(new Integer(this.al));
                    }
                    if (ak.e(string.indexOf("\u0110o\u1ea1n S\u01a1n"))) {
                        ad2.addElement(new Integer(ah[36]));
                        ad3.addElement(new Integer(this.al));
                    }
                }
                if (ak.e(ad2.size())) {
                    ey.a(new cw(ad2, ad3, this.al));
                    return;
                }
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            } else {
                ey.a(new cw(this.al));
            }
        }
    }

    private static boolean f(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final boolean e() {
        int n2;
        if (ak.f(ey.u instanceof ak) && ak.e(this.d, ah[4]) && ak.f(ec.n().aa, ah[7]) && ak.c(ec.n().aa, ah[20])) {
            n2 = ah[1];
            "".length();
            if (((0x23 ^ 0x40 ^ (0x7F ^ 0x56)) & (0x16 ^ 0x35 ^ (0x70 ^ 0x19) ^ -" ".length())) != 0) {
                return ((95 + 22 - 33 + 43 ^ (0x95 ^ 0xBA)) & (19 + 166 - 151 + 188 ^ 65 + 115 - 175 + 137 ^ -" ".length())) != 0;
            }
        } else {
            n2 = ah[0];
        }
        return n2 != 0;
    }

    private static boolean g(int n2, int n3) {
        return n2 <= n3;
    }

    public final void a() {
        if (ak.f(ax.g() ? 1 : 0)) {
            ax.a(ah[1]);
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        } else {
            Calendar calendar = fo.a();
            int n2 = calendar.get(ah[5]);
            int n3 = calendar.get(ah[3]);
            int n4 = calendar.get(ah[39]);
            switch (this.d) {
                case 0: {
                    Object object;
                    if (ak.g(this.aj ? 1 : 0) && ak.e(this.al)) {
                        if (ak.h(de.i, this.al)) {
                            this.a(this.al, ah[40], ah[2], ah[2]);
                            return;
                        }
                        ap.b(ah[5], ah[1], ah[0]);
                        if (ak.f(bo.f() ? 1 : 0)) {
                            this.aj = ah[1];
                            this.u = this.an;
                            this.x = this.am;
                            this.j = this.ar;
                            this.t = ah[0];
                            return;
                        }
                        return;
                    }
                    if (ak.a(this.al) && (!ak.g(de.b(de.i) ? 1 : 0) || ak.f(de.j(de.i) ? 1 : 0))) {
                        this.al = de.i;
                    }
                    if (ak.h(n2, this.aq) && ak.f(n3, ah[4]) && !ak.c(n4, ah[41])) break;
                    if (!ak.e(this.u, de.i) || ak.g(this.t ? 1 : 0) && ak.h(this.x, de.ab)) {
                        this.a(this.u, this.x, this.p, this.s);
                        return;
                    }
                    if (ak.f(ec.ew ? 1 : 0) && ak.f(ey.h() ? 1 : 0) && ak.c(ec.u(), ah[5]) && ak.g(de.e(de.i) ? 1 : 0)) {
                        ax.h();
                        return;
                    }
                    if (ak.g(this.ak ? 1 : 0) && ak.g(this.ai ? 1 : 0)) {
                        this.b(this.b, this.a(ec.dn, ec.ea, ec.eg));
                        this.d(ah[2]);
                        "".length();
                        if (((0x69 ^ 0x64) & ~(0x76 ^ 0x7B)) != 0) {
                            return;
                        }
                    } else {
                        cv cv2;
                        ec ec2;
                        ec ec3;
                        object = ec.n();
                        if (ak.e(ap.an.size())) {
                            ec3 = ((dg)ap.an.firstElement()).b;
                            "".length();
                            if ("  ".length() == -" ".length()) {
                                return;
                            }
                        } else {
                            ec3 = ec2 = null;
                        }
                        if (ak.f(this.ak ? 1 : 0) && ak.e(ap.an.size()) && ak.e(((ec)object).dk.c, ah[42])) {
                            n3 = ah[0];
                            while (ak.c(n3, ((ec)object).ao.size())) {
                                cv2 = (cv)((ec)object).ao.elementAt(n3);
                                if (ak.a(cv2) && ak.e(cv2.k.b, ah[38])) {
                                    if (!ak.g(cv2.a() ? 1 : 0)) break;
                                    n3 = ah[0];
                                    while (ak.c(n3, ap.an.size())) {
                                        dg dg2 = (dg)ap.an.elementAt(n3);
                                        if (ak.h(dg2.d, ((ec)object).w) && ak.a(dg2.b) && ak.i(dg2.b.cl)) {
                                            n3 = ((ec)object).s;
                                            int n5 = ((ec)object).fc;
                                            ec ec4 = dg2.b;
                                            ec.d(ec4.s, ec4.fc);
                                            "".length();
                                            ax.a(500L);
                                            dl.h().am(dg2.d);
                                            cv2.c = System.currentTimeMillis();
                                            cv2.f = ah[1];
                                            ((ec)object).b(ap.ch[cv2.k.d], ah[0]);
                                            ax.a(1000L);
                                            ec.d(n3, n5);
                                            "".length();
                                            return;
                                        }
                                        ++n3;
                                        "".length();
                                        if ("  ".length() >= -" ".length()) continue;
                                        return;
                                    }
                                    break;
                                }
                                ++n3;
                                "".length();
                                if (" ".length() < "   ".length()) continue;
                                return;
                            }
                        }
                        if (ak.f(this.ai ? 1 : 0) && ak.f(this.i() ? 1 : 0) && ak.a(ec2) && ak.e(((ec)object).dk.c, ah[42])) {
                            n3 = ah[0];
                            while (ak.c(n3, ((ec)object).ao.size())) {
                                cv2 = (cv)((ec)object).ao.elementAt(n3);
                                if (ak.a(cv2) && ak.g(cv2.a() ? 1 : 0) && ak.e(cv2.k.b, ah[4]) && (!ak.f(cv2.k.d, ah[21]) || ak.d(cv2.k.d, ah[43])) && (!ak.e(cv2.k.d, ah[44]) || ak.c(ec2.cl, ec2.fd * ec.ec / ah[29]))) {
                                    int n6 = ah[0];
                                    while (ak.c(n6, ec2.dy.size())) {
                                        ec2.dy.elementAt(n6);
                                        "".length();
                                        ++n6;
                                        "".length();
                                        if (-"  ".length() <= 0) continue;
                                        return;
                                    }
                                    n6 = ((ec)object).s;
                                    n3 = ((ec)object).fc;
                                    ec.d(ec2.s, ec2.fc);
                                    "".length();
                                    dl.h().e(cv2.k.d);
                                    dl.h().c();
                                    cv2.c = System.currentTimeMillis();
                                    cv2.f = ah[1];
                                    ((ec)object).b(ap.ch[cv2.k.d], ah[0]);
                                    ax.a(1000L);
                                    ec.d(n6, n3);
                                    "".length();
                                    "".length();
                                    if (" ".length() >= 0) break;
                                    return;
                                }
                                ++n3;
                                "".length();
                                if (-" ".length() <= 0) continue;
                                return;
                            }
                        }
                    }
                    if (ak.e(ak.a(System.currentTimeMillis() - this.ap, 602000L)) && ak.d(ec.u(), ah[1]) && ak.a(object = ec.b(ah[17]))) {
                        dl.h().u(((el)object).n);
                        this.ap = System.currentTimeMillis();
                        return;
                    }
                    return;
                }
                case 1: {
                    if (!ak.a(ey.v) || ak.h(ak.a(System.currentTimeMillis() - this.ao, 3600000L))) {
                        this.d();
                        return;
                    }
                    if (!ak.e(de.i, ah[1]) || ak.h(de.ab, ah[45])) {
                        this.a(ah[1], ah[45], ah[2], ah[2]);
                        return;
                    }
                    y y2 = ec.n(ah[1]);
                    int n7 = ec.n().cx.equals(ey.v);
                    if (ak.f(c ? 1 : 0) && ak.f(n7)) {
                        this.d();
                        dl.h().l("sts");
                        return;
                    }
                    if (ak.b(y2)) {
                        ds ds2 = ap.b(ah[46]);
                        ec.d(ds2.s, ds2.fc);
                        "".length();
                        bo.a(300000L);
                        if (ak.f(n7)) {
                            ap.b(ah[46], ap.dv + ah[1], ah[0]);
                            bo.ag();
                            ax.a(2000L);
                            return;
                        }
                        "".length();
                        if (-" ".length() != -" ".length()) {
                            return;
                        }
                    } else {
                        if (ak.f(y2.a, y2.f)) {
                            ap.b(ah[46], ap.dv + ah[1], ah[4]);
                            bo.e();
                            ax.a(2000L);
                            return;
                        }
                        if (ak.f(n7)) {
                            df df2 = ey.ao;
                            df2.c();
                            df2.j = ah[1];
                            ey.a(df2);
                            dl.h().l("att " + df2.u + " " + df2.x + " " + df2.c);
                            return;
                        }
                    }
                    return;
                }
                case 2: {
                    if (!ak.f(ec.n().aa, ah[41]) || ak.h(ak.a(System.currentTimeMillis() - this.ao, 10800000L))) {
                        this.d();
                        return;
                    }
                    if (ak.g(this.aj ? 1 : 0)) {
                        int n5;
                        if (!ak.e(de.i, ah[1]) || ak.h(de.ab, ah[45])) {
                            this.a(ah[1], ah[45], ah[2], ah[2]);
                            return;
                        }
                        if (ak.g(ec.l, ah[19])) {
                            int n9;
                            int n6;
                            int n7;
                            if (ak.e(ec.l, ah[19])) {
                                n7 = ah[47];
                                "".length();
                                if ((0x9B ^ 0x9E) <= 0) {
                                    return;
                                }
                            } else {
                                n7 = ah[48] + ec.l / ah[14];
                            }
                            if (ak.f(n2 = ec.l(n7), ah[4])) {
                                n6 = ah[0];
                                "".length();
                                if (-" ".length() >= ("   ".length() & ~"   ".length())) {
                                    return;
                                }
                            } else {
                                n6 = ah[4] - n2;
                            }
                            n2 = n9 = n6;
                            if (ak.e(n9)) {
                                ap.b(ah[38], ah[0], ah[0]);
                                if (ak.e(ec.l, ah[19])) {
                                    dl.h().c(ah[49], ah[50], n2);
                                    "".length();
                                    if ("  ".length() <= 0) {
                                        return;
                                    }
                                } else {
                                    dl.h().c(ah[49], ec.l / ah[14], n2);
                                }
                                bo.aa();
                            }
                        }
                        if (ak.e(calendar.get(ah[50]), ah[4])) {
                            ap.b(ah[27], ah[1], ah[0]);
                            ax.a(2000L);
                        }
                        this.aj = ah[1];
                        n2 = ec.n().aa;
                        if (ak.f(n2, ah[34])) {
                            bo.c();
                            ey.a(new ez());
                            e = ah[1];
                            return;
                        }
                        if (ak.c(n2, ah[6])) {
                            n5 = ah[51];
                            "".length();
                            if (-"  ".length() >= 0) {
                                return;
                            }
                        } else if (ak.c(n2, ah[19])) {
                            n5 = ah[52];
                            "".length();
                            if (((44 + 44 - 9 + 73 ^ 9 + 31 - -82 + 54) & (99 + 37 - 17 + 33 ^ 50 + 49 - 73 + 150 ^ -" ".length())) >= " ".length()) {
                                return;
                            }
                        } else if (ak.c(n2, ah[7])) {
                            n5 = ah[53];
                            "".length();
                            if (null != null) {
                                return;
                            }
                        } else if (ak.c(n2, ah[20])) {
                            n5 = ah[54];
                            "".length();
                            if ((148 + 34 - 106 + 80 ^ 38 + 74 - 92 + 132) == 0) {
                                return;
                            }
                        } else {
                            n5 = ah[55];
                        }
                        this.u = n5;
                        this.t = ah[1];
                        this.j = ah[0];
                        if (ak.f(n2, ah[7]) && ak.c(n2, ah[20])) {
                            if (ak.d(ap.an.size(), ah[1])) {
                                dl.h().i();
                                return;
                            }
                            return;
                        }
                        if (!ak.a(ey.v)) break;
                        bo.c();
                        return;
                    }
                    if (ak.g(e ? 1 : 0)) {
                        if (ak.e(de.i, this.u)) {
                            this.b(ah[2], ah[2]);
                            this.d(ah[2]);
                            return;
                        }
                        this.a(this.u, ah[40], ah[2], ah[2]);
                        return;
                    }
                    if (ak.g(de.j(de.i) ? 1 : 0)) {
                        return;
                    }
                    ap.b(ah[0], ah[4], ah[0]);
                    dl.h().f();
                    this.aj = ah[0];
                    e = ah[0];
                    if (!ak.g(a ? 1 : 0)) break;
                    return;
                }
                case 3: {
                    if (!ak.g(this.aj ? 1 : 0)) break;
                    if (!ak.e(de.i, ah[1]) || ak.h(de.ab, ah[45])) {
                        this.a(ah[1], ah[45], ah[2], ah[2]);
                        return;
                    }
                    ap.b(ah[5], ah[1], ah[0]);
                    if (ak.f(bo.f() ? 1 : 0)) {
                        if (ak.e(calendar.get(ah[50]), ah[4])) {
                            ap.b(ah[27], ah[1], ah[0]);
                            ax.a(2000L);
                        }
                        this.aj = ah[1];
                        return;
                    }
                    return;
                }
                default: {
                    return;
                }
            }
            this.d();
        }
    }

    private static boolean i(int n2) {
        return n2 <= 0;
    }

    private static boolean h(int n2, int n3) {
        return n2 != n3;
    }

    static {
        ak.f();
    }

    private static void f() {
        ah = new int[56];
        ak.ah[0] = (0x13 ^ 0x48 ^ (0xE ^ 0x7E)) & (0x4A ^ 0x22 ^ (0x47 ^ 4) ^ -" ".length());
        ak.ah[1] = " ".length();
        ak.ah[2] = -" ".length();
        ak.ah[3] = 16 + 52 - 30 + 107 ^ 107 + 61 - 54 + 40;
        ak.ah[4] = "  ".length();
        ak.ah[5] = 5 ^ 0x5B ^ (0x59 ^ 2);
        ak.ah[6] = 0x7D ^ 0x55;
        ak.ah[7] = 6 ^ 0x3A;
        ak.ah[8] = 0x81 ^ 0xB1 ^ (3 ^ 0x11);
        ak.ah[9] = 2 ^ 0x4F ^ (0x28 ^ 0x45);
        ak.ah[10] = 0x7A ^ 0x1C ^ (0x45 ^ 0);
        ak.ah[11] = 0x4D ^ 0x79;
        ak.ah[12] = 0xAE ^ 0x98 ^ (0x29 ^ 0x2F);
        ak.ah[13] = 0xAE ^ 0xA3 ^ "   ".length();
        ak.ah[14] = 47 + 63 - -25 + 2 ^ 43 + 105 - 102 + 85;
        ak.ah[15] = 0x91 ^ 0xC7 ^ (0x5D ^ 4);
        ak.ah[16] = 0xB ^ 0x4F;
        ak.ah[17] = " ".length() ^ (0xB7 ^ 0x90);
        ak.ah[18] = 0xD7 ^ 0xC7;
        ak.ah[19] = 0x70 ^ 0x42;
        ak.ah[20] = 0x77 ^ 0x31;
        ak.ah[21] = 52 + 70 - 2 + 83 ^ 37 + 48 - 58 + 109;
        ak.ah[22] = 0x13 ^ 0x3F;
        ak.ah[23] = 72 + 192 - 106 + 79 ^ 27 + 96 - -72 + 3;
        ak.ah[24] = 0 ^ 0x50;
        ak.ah[25] = 0xEB ^ 0xBB ^ (0xC0 ^ 0xB9);
        ak.ah[26] = 0x86 ^ 0xBD;
        ak.ah[27] = 0xDA ^ 0xC2;
        ak.ah[28] = 0x9E ^ 0xB3;
        ak.ah[29] = 0xFE ^ 0x9A;
        ak.ah[30] = 0xB6 ^ 0xA5;
        ak.ah[31] = 0x3C ^ 0x2D;
        ak.ah[32] = 0xBE ^ 0x9A;
        ak.ah[33] = 0x45 ^ 0x73;
        ak.ah[34] = 124 + 71 - -5 + 42 ^ 60 + 69 - 30 + 69;
        ak.ah[35] = (0xFF ^ 0x91) + (0xDC ^ 0x8F) - (0x2B ^ 0x61) + (0x5D ^ 0x45);
        ak.ah[36] = 101 + 80 - 94 + 54;
        ak.ah[37] = "   ".length();
        ak.ah[38] = 0x57 ^ 0x2E ^ (3 ^ 0x7E);
        ak.ah[39] = 22 + 98 - 77 + 145 ^ 99 + 34 - 16 + 59;
        ak.ah[40] = -"  ".length();
        ak.ah[41] = 0xD8 ^ 0x92 ^ (0x44 ^ 0x10);
        ak.ah[42] = 106 + 90 - 47 + 49 ^ 176 + 161 - 220 + 75;
        ak.ah[43] = 0x44 ^ 0xC;
        ak.ah[44] = 126 + 85 - 75 + 46 ^ 67 + 103 - 74 + 57;
        ak.ah[45] = 0x4B ^ 0x6F ^ (0xB7 ^ 0x86);
        ak.ah[46] = 0x4E ^ 0xF ^ (0x2D ^ 0x75);
        ak.ah[47] = 0x8F ^ 0x92;
        ak.ah[48] = 154 + 81 - 161 + 94 ^ 32 + 68 - -60 + 31;
        ak.ah[49] = 0x57 ^ 0x5E;
        ak.ah[50] = 0x95 ^ 0x92;
        ak.ah[51] = 0x50 ^ 0x12 ^ (0xD8 ^ 0xC1);
        ak.ah[52] = 7 + 89 - -23 + 35 ^ 100 + 18 - 42 + 120;
        ak.ah[53] = 4 ^ 0x31 ^ (0x34 ^ 0x68);
        ak.ah[54] = 0xF0 ^ 0x82;
        ak.ah[55] = 0xF1 ^ 0x8C;
    }

    public final void a(int n2, int n3, int n4, boolean bl2, boolean bl3) {
        int n5;
        super.c();
        this.u = n3;
        this.x = n4;
        this.b = n2;
        this.ai = bl2;
        this.ak = bl3;
        this.d = ah[0];
        this.ap = 0L;
        this.aj = ah[1];
        this.al = ah[2];
        Calendar calendar = fo.a();
        if (ak.c(calendar.get(ah[3]), ah[4])) {
            n5 = calendar.get(ah[5]) - ah[1];
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        } else {
            n5 = calendar.get(ah[5]);
        }
        this.aq = n5;
    }
}

