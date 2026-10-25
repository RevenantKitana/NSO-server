/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Image;

public final class et
extends ef
implements bv {
    private int d;
    private String e = "";
    private int f;
    private int g;
    private int h;
    public static Image a;
    private boolean i;
    private int j;
    public static boolean b;
    private eg k;
    private fr l;
    private fr m;
    private String[] n;
    private static final int[] o;
    private eg p;
    private boolean q = o[1];
    private fr r;
    private int s;
    private fr t;
    private int u;
    private eg v;
    public static int c;
    private int w;
    private int x;
    private static et y;
    private eg z;
    private int aa;

    public void b() {
    }

    public static et g() {
        return y;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    private static boolean c(int n2) {
        return n2 <= 0;
    }

    public final void h() {
        this.i = o[3];
        this.aa = o[0];
        ap.bv = em.aq;
        if (et.c(em.b, o[2])) {
            em.c(o[1]);
            "".length();
            if ("   ".length() < " ".length()) {
                return;
            }
        } else {
            em.c(de.ae);
        }
        super.c();
        if (et.b(ap.dc)) {
            ap.dc = null;
        }
        if (et.b(em.a)) {
            em.a = new cc();
        }
        em.ac = o[1];
        this.ft = this.l = new fr("H\u1ee7y", this, o[5], null);
    }

    private void a(String string) {
        fl.c = be.e[o[1]];
        em.b(ag.ko);
        em.q();
        em.b(ag.qy);
        dl.h().o();
        dl dl2 = dl.h();
        String string2 = this.p.h;
        String string3 = this.z.h;
        String string4 = string;
        dl dl3 = dl2;
        try {
            eh eh2 = new eh(o[27]);
            eh2.d.writeUTF(string4);
            eh2.d.writeUTF(string3);
            eh2.d.writeUTF(string2);
            dl3.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (" ".length() != " ".length()) {
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static void a(boolean bl2) {
        int n2;
        em.ac = bl2;
        if (et.f(bl2 ? 1 : 0)) {
            n2 = o[3];
            "".length();
            if ((97 + 30 - 42 + 55 ^ 129 + 44 - 172 + 135) == "   ".length()) {
                return;
            }
        } else {
            n2 = o[2];
        }
        an.a("isGPRS", n2);
    }

    private static boolean d(int n2) {
        return n2 == 0;
    }

    public final void e() {
        if (et.b(ap.fb += o[3], em.aj * o[21] + o[11])) {
            ap.fb = o[11];
        }
        this.k.f();
        this.z.f();
        if (et.f(this.i ? 1 : 0)) {
            this.v.f();
            this.p.f();
        }
        if (et.a(this.x, this.aa)) {
            this.aa += this.x - this.aa >> o[3];
        }
        if (et.f(em.w ? 1 : 0)) {
            this.fx = null;
            if (et.f(this.i ? 1 : 0)) {
                this.fw = this.t;
                "".length();
                if ("  ".length() < 0) {
                    return;
                }
            } else {
                this.fw = this.m;
                "".length();
                if (((0xE ^ 0x69 ^ (0x7D ^ 0x15)) & (93 + 95 - 67 + 13 ^ 109 + 120 - 115 + 23 ^ -" ".length())) > " ".length()) {
                    return;
                }
            }
        } else if (et.f(this.i ? 1 : 0)) {
            this.fx = this.t;
            "".length();
            if ((1 ^ 4) == 0) {
                return;
            }
        } else if (et.c(this.u, o[2])) {
            this.fx = this.r;
            if (et.f(this.q ? 1 : 0)) {
                this.fx.d = ag.qz;
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            } else {
                this.fx.d = ag.qx;
                "".length();
                if ("   ".length() == 0) {
                    return;
                }
            }
        } else {
            this.fx = this.m;
        }
        if (et.e(this.s)) {
            this.d += this.g * this.s;
            this.s += this.g * this.h;
            if (et.c(this.s)) {
                this.g = -this.g;
            }
            if (et.b(this.d)) {
                this.g = -this.g;
                this.s -= o[2] * this.h;
            }
        }
        if (et.e(this.j) && et.d(em.aa % o[11])) {
            this.j += o[3];
            if (et.d(this.j, ag.cr.length)) {
                this.j = o[1];
            }
            this.n = doa.m.a(ag.cr[this.j], em.aj - o[28]);
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    public final void f() {
        if (et.f(em.j[o[2]])) {
            this.u -= o[3];
            if (et.a(this.u)) {
                this.u = o[21];
                "".length();
                if ("  ".length() >= (59 + 82 - 57 + 70 ^ 93 + 46 - 87 + 106)) {
                    return;
                }
            }
        } else if (et.f(em.j[o[40]])) {
            this.u += o[3];
            if (et.b(this.u, o[21])) {
                this.u = o[1];
            }
        }
        if (!et.d(em.j[o[2]]) || et.f(em.j[o[40]])) {
            em.h();
            if (et.c(this.u, o[3])) {
                this.k.a = o[1];
                this.z.a = o[3];
                this.v.a = o[1];
                this.p.a = o[1];
                this.fw = this.z.n;
                "".length();
                if (-" ".length() >= "   ".length()) {
                    return;
                }
            } else if (et.d(this.u)) {
                this.k.a = o[3];
                this.z.a = o[1];
                this.v.a = o[1];
                this.p.a = o[1];
                this.fw = this.k.n;
                "".length();
                if (("  ".length() & ("  ".length() ^ -" ".length())) < 0) {
                    return;
                }
            } else {
                this.k.a = o[1];
                this.z.a = o[1];
                if (et.f(this.i ? 1 : 0)) {
                    if (et.c(this.u, o[2])) {
                        this.v.a = o[3];
                        this.p.a = o[1];
                        this.fw = this.v.n;
                        "".length();
                        if (((0x64 ^ 0x2B ^ (0x8A ^ 0x94)) & (184 + 153 - 244 + 143 ^ 124 + 136 - 155 + 84 ^ -" ".length())) != ((0x18 ^ 0x28 ^ (0xE3 ^ 0x93)) & (162 + 65 - 129 + 126 ^ 5 + 75 - 21 + 101 ^ -" ".length()))) {
                            return;
                        }
                    } else if (et.c(this.u, o[21])) {
                        this.p.a = o[3];
                        this.v.a = o[1];
                        this.fw = this.p.n;
                    }
                }
            }
        }
        if (et.f(em.q ? 1 : 0)) {
            if (et.f(em.c(this.k.j, this.k.s, this.k.d, this.k.q) ? 1 : 0)) {
                this.u = o[1];
                "".length();
                if (((0x35 ^ 0x5C ^ (0x7E ^ 0x3B)) & (0x50 ^ 0x34 ^ (0x59 ^ 0x11) ^ -" ".length())) < ((0x16 ^ 0x67 ^ (0x3C ^ 0x41)) & (0xA4 ^ 0x9F ^ (0x9A ^ 0xAD) ^ -" ".length()))) {
                    return;
                }
            } else if (et.f(em.c(this.z.j, this.z.s, this.z.d, this.z.q) ? 1 : 0)) {
                this.u = o[3];
                "".length();
                if ((0x52 ^ 0x56) <= "  ".length()) {
                    return;
                }
            } else {
                if (et.f(this.i ? 1 : 0)) {
                    if (et.f(em.c(this.v.j, this.v.s, this.v.d, this.v.q) ? 1 : 0)) {
                        this.u = o[2];
                        "".length();
                        if (-(0x74 ^ 0x70) > 0) {
                            return;
                        }
                    } else if (et.f(em.c(this.p.j, this.p.s, this.p.d, this.p.q) ? 1 : 0)) {
                        this.u = o[21];
                        "".length();
                        if (-(0x7E ^ 0x30 ^ (0xFF ^ 0xB5)) > 0) {
                            return;
                        }
                    }
                } else if (et.f(em.c(this.k.j - o[19], em.m + o[28], o[13], o[19]) ? 1 : 0)) {
                    int n2;
                    if (et.d(this.q ? 1 : 0)) {
                        n2 = o[3];
                        "".length();
                        if ("  ".length() != "  ".length()) {
                            return;
                        }
                    } else {
                        n2 = o[1];
                    }
                    this.q = n2;
                }
                this.u = o[2];
            }
        }
        super.f();
        em.h();
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 1002: {
                this.i = o[3];
                this.v.a = o[1];
                this.p.a = o[1];
                this.z.a = o[1];
                this.k.a = o[3];
                this.fw = this.k.n;
                this.ft = new fr(ag.ip, this, o[41], null);
                return;
            }
            case 1003: {
                try {
                    fl.a.platformRequest("http://ninjaschool.vn");
                    "".length();
                    return;
                }
                catch (ConnectionNotFoundException connectionNotFoundException) {
                    return;
                }
            }
            case 1004: {
                ad ad2 = new ad();
                int n3 = an.d("lowGraphic");
                if (et.d(em.w ? 1 : 0)) {
                    if (et.c(n3, o[3])) {
                        ad2.addElement(new fr(ag.sn, this, o[42], null));
                        "".length();
                        if (-(0x5A ^ 0x5E) >= 0) {
                            return;
                        }
                    } else {
                        ad2.addElement(new fr(ag.rn, this, o[43], null));
                    }
                }
                ad2.addElement(new fr(ag.pd, this, o[44], null));
                if (et.a(em.at, this)) {
                    ad2.addElement(new fr(ag.ny, this, o[45], null));
                }
                em.a.a(ad2);
                return;
            }
            case 1005: {
                em.a(ag.ba, new fr("3G/Wifi", this, o[46], null), new fr("GPRS", this, o[47], null));
                return;
            }
            case 1006: {
                em.a(ag.ob, new fr(ag.dk, this, o[48], null), new fr(ag.bd, em.f(), o[49], null));
                return;
            }
            case 1007: {
                int n4;
                if (et.d(ca.a ? 1 : 0)) {
                    n4 = o[3];
                    "".length();
                    if ((46 + 53 - 47 + 142 ^ 192 + 52 - 208 + 162) < 0) {
                        return;
                    }
                } else {
                    ca.a = o[1];
                    n4 = ca.a ? 1 : 0;
                }
                if (et.f(n4)) {
                    an.a("isSound", o[3]);
                    return;
                }
                an.a("isSound", o[2]);
                System.out.println("tat am thanh");
                return;
            }
            case 1009: {
                an.d();
                return;
            }
            case 2000: {
                if (et.d(this.k.h.equals("") ? 1 : 0) && et.d(this.z.h.equals("") ? 1 : 0)) {
                    aa.b = this.k.h;
                    aa.d = this.z.h;
                }
                em.au.c();
                return;
            }
            case 2001: {
                if (et.f(this.q ? 1 : 0)) {
                    this.q = o[1];
                    return;
                }
                this.q = o[3];
                return;
            }
            case 2002: {
                if (et.f(this.k.h.equals("") ? 1 : 0)) {
                    em.c(ag.lr);
                    return;
                }
                char[] cArray = this.k.h.toCharArray();
                int n5 = o[1];
                while (et.f(n5, cArray.length)) {
                    if (et.d(eg.a(cArray[n5]) ? 1 : 0)) {
                        em.c(ag.nm);
                        return;
                    }
                    ++n5;
                    "".length();
                    if ("   ".length() > 0) continue;
                    return;
                }
                if (et.f(this.z.h.equals("") ? 1 : 0)) {
                    em.c(ag.bs);
                    return;
                }
                if (et.f(this.v.h.equals("") ? 1 : 0)) {
                    em.c(ag.jp);
                    return;
                }
                if (et.f(this.k.h.length(), o[8])) {
                    em.c(ag.bi);
                    return;
                }
                if (et.d(this.z.h.equals(this.v.h) ? 1 : 0)) {
                    em.c(ag.jy);
                    return;
                }
                if (et.d(this.p.h.equals("") ? 1 : 0)) {
                    em.g.a(ag.ms[o[1]] + " " + this.k.h + ", " + ag.ms[o[3]], new fr(ag.dk, this, o[50], null), null, new fr(ag.bd, em.f, o[49], null));
                    em.d = em.g;
                    return;
                }
                em.a("B\u1ea1n ch\u01b0a nh\u1eadp Email/s\u1ed1 di \u0111\u1ed9ng, Email/s\u1ed1 di \u0111\u1ed9ng gi\u00fap b\u1ea1n l\u1ea5y l\u1ea1i m\u1eadt kh\u1ea9u khi m\u1ea5t m\u1eadt kh\u1ea9u", new fr("Ti\u1ebfp T\u1ee5c", this, o[51], null), new fr(ag.bd, em.f, o[49], null));
                return;
            }
            case 2003: {
                fl.a("http://dd.ninjaschool.vn/app/index.php?for=event&do=resetpass");
                return;
            }
            case 2004: {
                em.ak.a(ag.db, new fr(ag.v, this, o[52], null), o[1]);
                return;
            }
            case 2005: {
                em.a("B\u1ea1n c\u00f3 mu\u1ed1n reset m\u1eadt kh\u1ea9u kh\u00f4ng?", new fr(ag.v, this, o[53], null), new fr(ag.bd, em.f, o[49], null));
                return;
            }
            case 3000: {
                et.a(o[1]);
                em.k();
                return;
            }
            case 3001: {
                et.a(o[3]);
                em.k();
                return;
            }
            case 4000: {
                this.a(this.k.h);
                return;
            }
            case 4001: {
                this.a(this.k.h);
                return;
            }
            case 10021: {
                this.i = o[1];
                this.v.a = o[1];
                this.z.a = o[1];
                this.k.a = o[3];
                this.fw = this.k.n;
                this.ft = this.l;
                return;
            }
            case 10041: {
                an.a("lowGraphic", o[1]);
                em.a(ag.g, o[54]);
                return;
            }
            case 10042: {
                an.a("lowGraphic", o[3]);
                em.a(ag.g, o[54]);
                return;
            }
            case 10051: {
                an.a("isSoftKey", o[3]);
                ap.ci = o[3];
                return;
            }
            case 10052: {
                an.a("isSoftKey", o[2]);
                ap.ci = o[1];
                return;
            }
            case 10061: {
                em.k();
                an.a("indLanguage", o[6]);
                fl.a.notifyDestroyed();
                return;
            }
            case 20041: {
                this.e = em.ak.a.h.toString();
                em.k();
                if (et.f(this.e.equals("") ? 1 : 0)) {
                    em.c(ag.lr);
                    return;
                }
                em.a(ag.mu, new fr(ag.k, this, o[55], null), new fr(ag.bd, this, o[56], null));
                return;
            }
            case 20051: {
                ap.s().c();
                return;
            }
            case 20052: {
                fl.a("http://dd.ninjaschool.vn/app/index.php?for=event&do=resetpass");
                return;
            }
            case 200041: {
                em.a.m = o[1];
                fl.c = be.e[o[1]];
                fl.e = o[57];
                an.a("indServer", fl.i[o[9]]);
                this.i();
                return;
            }
            case 200042: {
                em.a.m = o[1];
                fl.c = be.e[o[1]];
                fl.e = o[57];
                an.a("indServer", fl.d[o[9]]);
                this.i();
                return;
            }
            case 200421: {
                em.k();
                String string = this.e;
                fl.c = be.e[o[1]];
                em.b(ag.ko);
                em.q();
                em.b(ag.ab);
                dl.h().a(string);
                return;
            }
            case 200422: {
                String string = this.e;
                em.c(bz.a(ag.sb, "#", string));
            }
        }
        if (et.d(n2, o[58]) && et.f(n2, o[58] + be.c.length)) {
            int n6 = be.c[n2 - o[58]];
            em.a.m = o[1];
            fl.c = be.e[n6];
            fl.e = be.a[n6];
            fl.h = be.d[n6];
            an.a("indServer", be.c[n6]);
            this.i();
            return;
        }
    }

    private void i() {
        this.j = em.aa % ag.cr.length;
        this.n = doa.m.a(ag.cr[this.j], em.aj - o[28]);
        String string = this.k.h.toLowerCase().trim();
        String string2 = this.z.h.toLowerCase().trim();
        if (et.f(string.equals("a") ? 1 : 0) && et.f(string2.equals("a") ? 1 : 0)) {
            c = o[3];
            "".length();
            if (((0x5D ^ 0x3F) & ~(0xF ^ 0x6D)) != 0) {
                return;
            }
        } else if (et.f(string.equals("b") ? 1 : 0) && et.f(string2.equals("b") ? 1 : 0)) {
            c = o[2];
        }
        if (et.b(string) && et.b(string2) && et.d(string.equals("") ? 1 : 0)) {
            if (et.f(string2.equals("") ? 1 : 0)) {
                this.u = o[3];
                this.k.a = o[1];
                this.z.a = o[3];
                this.fw = this.z.n;
                return;
            }
            em.b(ag.ko);
            em.q();
            em.b(ag.mk);
            dl.h().a(string, string2, "1.8.0");
            b = o[3];
            if (et.f(this.q ? 1 : 0)) {
                an.a("check", o[3]);
                an.a("acc", string);
                an.a("pass", string2);
                "".length();
                if ("  ".length() != "  ".length()) {
                    return;
                }
            } else {
                an.a("check", o[2]);
                an.a("acc", "");
                an.a("pass", "");
            }
            this.u = o[1];
        }
    }

    public final void c() {
        if (et.a(an.a("random"))) {
            an.a("random", aa.h());
        }
        this.aa = o[0];
        this.i = o[1];
        ap.bv = em.aq;
        if (et.c(em.b, o[2])) {
            em.c(o[1]);
            "".length();
            if ("  ".length() <= -" ".length()) {
                return;
            }
        } else {
            em.c(de.ae);
        }
        super.c();
        if (et.b(ap.dc)) {
            ap.dc = null;
        }
        if (et.b(em.a)) {
            em.a = new cc();
        }
        em.ac = o[1];
        int n2 = an.d("isSoftKey");
        if (et.c(n2)) {
            an.a("isSoftKey", o[3]);
            ap.ci = o[3];
            "".length();
            if (" ".length() == 0) {
                return;
            }
        } else if (et.c(n2, o[3])) {
            ap.ci = o[3];
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        } else if (et.c(n2, o[2])) {
            ap.ci = o[1];
        }
        if (et.a(n2 = an.d("isSound"))) {
            an.a("isSound", o[3]);
            ca.a = o[3];
            "".length();
            if (null != null) {
                return;
            }
        } else if (et.c(n2, o[3])) {
            ca.a = o[3];
            "".length();
            if ("  ".length() <= -" ".length()) {
                return;
            }
        } else if (et.c(n2, o[2])) {
            ca.a = o[1];
        }
        this.ft = this.l = new fr("Q.M\u1eadt Kh\u1ea9u", this, o[4], null);
    }

    public final void a(bh bh2) {
        bh2.a(o[1]);
        bh2.e(o[1], o[1], em.aj, em.aq);
        em.b(bh2);
        int n2 = this.k.s - o[29];
        if (et.e(em.aq, o[30])) {
            n2 += 5;
        }
        if (et.a(em.d)) {
            if (et.f(this.i ? 1 : 0)) {
                br.b(em.r - o[31], this.k.s - o[32], o[33], o[34], bh2);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                br.b(em.r - o[31], this.k.s - o[32], o[33], o[35], bh2);
            }
            if (et.b(em.aq, o[18]) && et.b(a)) {
                bh2.a(a, em.r, n2 - o[2], o[21]);
            }
            this.k.a(bh2);
            this.z.a(bh2);
            if (et.f(this.i ? 1 : 0)) {
                this.v.a(bh2);
                this.p.a(bh2);
            }
            bh2.a(o[1], o[1], em.aj, em.aq);
            if (et.b(em.aj, o[12])) {
                if (et.f(this.k.h.equals("") ? 1 : 0)) {
                    if (et.d(this.k.a ? 1 : 0)) {
                        doa.v.a(bh2, ag.lz, this.k.j + o[8], this.k.s + o[36], o[1]);
                        "".length();
                        if (((0x6C ^ 0x41) & ~(0x1B ^ 0x36)) >= (0x14 ^ 0x10)) {
                            return;
                        }
                    } else {
                        doa.d.a(bh2, ag.lz, this.k.j + o[8], this.k.s + o[36], o[1]);
                    }
                }
                if (et.f(this.z.h.equals("") ? 1 : 0)) {
                    if (et.d(this.z.a ? 1 : 0)) {
                        doa.v.a(bh2, ag.di, this.z.j + o[8], this.z.s + o[36], o[1]);
                        "".length();
                        if (((0x98 ^ 0x90 ^ (0x25 ^ 0x60)) & (0x94 ^ 0x9A ^ (0x16 ^ 0x55) ^ -" ".length())) != 0) {
                            return;
                        }
                    } else {
                        doa.d.a(bh2, ag.di, this.z.j + o[8], this.z.s + o[36], o[1]);
                    }
                }
                if (et.f(this.i ? 1 : 0)) {
                    if (et.f(this.v.h.equals("") ? 1 : 0)) {
                        if (et.d(this.v.a ? 1 : 0)) {
                            doa.v.a(bh2, ag.ru, this.v.j + o[8], this.v.s + o[36], o[1]);
                            doa.v.a(bh2, ag.di, this.v.j + o[37], this.v.s + o[36], o[1]);
                            "".length();
                            if (((0x5D ^ 4) & ~(0x9C ^ 0xC5)) != ((0xE2 ^ 0xBE) & ~(0xC7 ^ 0x9B))) {
                                return;
                            }
                        } else {
                            doa.d.a(bh2, ag.ru, this.v.j + o[8], this.v.s + o[36], o[1]);
                            doa.d.a(bh2, ag.di, this.v.j + o[37], this.v.s + o[36], o[1]);
                        }
                    }
                    if (et.f(this.p.h.equals("") ? 1 : 0)) {
                        if (et.d(this.p.a ? 1 : 0)) {
                            doa.v.a(bh2, "Email/s\u1ed1 di \u0111\u1ed9ng", this.p.j + o[8], this.p.s + o[8], o[1]);
                            "".length();
                            if (((19 + 16 - -150 + 16 ^ 129 + 69 - 107 + 39) & (2 ^ 0x33 ^ (0xD4 ^ 0xAE) ^ -" ".length())) < 0) {
                                return;
                            }
                        } else {
                            doa.d.a(bh2, "Email/s\u1ed1 di \u0111\u1ed9ng", this.p.j + o[8], this.p.s + o[8], o[1]);
                            "".length();
                            if (null != null) {
                                return;
                            }
                        }
                    }
                }
            } else {
                if (et.f(this.k.h.equals("") ? 1 : 0)) {
                    doa.v.a(bh2, ag.cq, this.k.j - o[22], this.k.s + o[36], o[1]);
                }
                if (et.f(this.z.h.equals("") ? 1 : 0)) {
                    doa.v.a(bh2, ag.od, this.z.j - o[22], this.z.s + o[36], o[1]);
                }
                if (et.f(this.i ? 1 : 0)) {
                    doa.v.a(bh2, ag.oa, this.v.j - o[22], this.v.s - o[3], o[1]);
                    doa.v.a(bh2, ag.od, this.v.j - o[22], this.v.s + o[38], o[1]);
                    doa.v.a(bh2, "Email/s\u1ed1 di \u0111\u1ed9ng", this.p.j - o[22], this.p.s + o[8], o[1]);
                    "".length();
                    if ("  ".length() <= 0) {
                        return;
                    }
                }
            }
        } else if (et.b(this.n)) {
            n2 = o[1];
            while (et.f(n2, this.n.length)) {
                doa.m.a(bh2, this.n[n2], em.aj / o[2], this.k.s - o[32] + n2 * o[39], o[2], doa.d);
                ++n2;
                "".length();
                if ((0x61 ^ 0x65) > "   ".length()) continue;
                return;
            }
        }
        String string = "1.8.0";
        if (et.f(b ? 1 : 0)) {
            string = dp.g().j;
        }
        doa.d.a(bh2, string, em.aj - o[8], o[8], o[3]);
        super.a(bh2);
    }

    public et() {
        this.i = o[1];
        this.j = o[6];
        this.h = o[2];
        this.s = o[1];
        this.d = o[7];
        this.g = o[3];
        y = this;
        this.i = o[1];
        de.ae = (byte)(System.currentTimeMillis() % 9L);
        if (!et.a(de.ae, o[8]) || et.c(de.ae, o[9])) {
            de.ae = (byte)o[10];
        }
        ap.a(o[3]);
        ap.fb = o[11];
        if (et.b(em.aq, o[12])) {
            this.x = em.m - o[13];
            "".length();
            if (" ".length() >= "  ".length()) {
                throw null;
            }
        } else {
            this.x = em.m - o[14];
        }
        this.aa = o[0];
        this.f = em.aj - o[15];
        if (et.f(this.f, o[16])) {
            this.f = o[16];
        }
        if (et.b(this.f, o[17])) {
            this.f = o[17];
        }
        this.w = em.m - ef.fu - o[8];
        if (et.e(em.aq, o[18])) {
            this.w = o[19];
        }
        this.k = new eg();
        this.k.l = ag.lz;
        this.k.j = em.r - o[19] - o[20];
        this.k.s = this.w;
        this.k.d = this.f;
        this.k.q = ef.fu + o[2];
        this.k.a = o[3];
        this.k.o = o[21];
        this.z = new eg();
        this.z.l = ag.di;
        this.z.j = em.r - o[19] - o[20];
        this.z.s = this.w += o[22];
        this.z.d = this.f;
        this.z.q = ef.fu + o[2];
        this.z.a = o[1];
        this.z.o = o[2];
        this.v = new eg();
        this.v.l = ag.jl;
        this.v.j = em.r - o[19] - o[20];
        this.v.s = this.w += o[22];
        this.v.d = this.f;
        this.v.q = ef.fu + o[2];
        this.v.a = o[1];
        this.v.o = o[2];
        this.p = new eg();
        this.p.l = "Email/S\u1ed1 di \u0111\u1ed9ng";
        this.p.j = em.r - o[19] - o[20];
        this.p.s = this.w += o[22];
        this.p.d = this.f;
        this.p.q = ef.fu + o[2];
        this.p.a = o[1];
        this.p.o = o[21];
        this.q = o[3];
        if (et.b(aa.a)) {
            if (et.f(aa.a.startsWith("tmpusr") ? 1 : 0)) {
                this.k.a("");
                this.z.a("");
                "".length();
                if (((0x6A ^ 0x5A) & ~(0x70 ^ 0x40)) < ((0x98 ^ 0xAA) & ~(0xBE ^ 0x8C))) {
                    throw null;
                }
            } else {
                this.k.a(aa.a);
                this.z.a(aa.c);
            }
        }
        this.u = o[1];
        this.m = new fr(ag.v, this, o[23], null);
        this.r = new fr(ag.qx, this, o[24], null);
        this.t = new fr(ag.v, this, o[25], null);
        if (et.d(this.i ? 1 : 0)) {
            this.ft = this.l = new fr("Q.M\u1eadt Kh\u1ea9u", this, o[4], null);
            "".length();
            if (-"  ".length() >= 0) {
                throw null;
            }
        } else {
            this.ft = this.l = new fr("H\u1ee7y", this, o[5], null);
        }
        if (et.f(em.w ? 1 : 0) && et.d(em.aj, o[26])) {
            this.fx = null;
            this.fw = this.m;
            "".length();
            if (null != null) {
                throw null;
            }
        } else {
            this.fx = this.m;
            this.fw = this.k.n;
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    private static boolean e(int n2) {
        return n2 >= 0;
    }

    static {
        et.j();
        c = o[1];
        a = em.a("/tt.png");
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static boolean e(int n2, int n3) {
        return n2 <= n3;
    }

    public void a(String string, String string2) {
    }

    public void a(eh eh2) {
    }

    public void b_() {
    }

    public final void q() {
        super.q();
    }

    private static boolean f(int n2) {
        return n2 != 0;
    }

    public void d() {
    }

    private static void j() {
        o = new int[59];
        et.o[0] = -(0xD1 ^ 0xC2 ^ (0xA5 ^ 0x84));
        et.o[1] = (0x93 ^ 0xB0) & ~(0xE2 ^ 0xC1);
        et.o[2] = "  ".length();
        et.o[3] = " ".length();
        et.o[4] = 0xFFFF9FDF & 0x67F5;
        et.o[5] = -(0xFFFFBC6F & 0x53BD) & (0xFFFFFFFF & 0x5E7F);
        et.o[6] = -" ".length();
        et.o[7] = -(0x9F ^ 0xB7);
        et.o[8] = 0x4A ^ 0x4F;
        et.o[9] = 0x71 ^ 0x63 ^ (0x6D ^ 0x79);
        et.o[10] = 0x7D ^ 0x57 ^ (0x32 ^ 0x1C);
        et.o[11] = 68 + 10 - -118 + 20 ^ 55 + 162 - 34 + 5;
        et.o[12] = 37 + 27 - -21 + 115;
        et.o[13] = "   ".length() ^ (5 ^ 0x56);
        et.o[14] = 0x3B ^ 0x57 ^ (0x42 ^ 0x6F);
        et.o[15] = 0xF2 ^ 0xC0 ^ (0xB7 ^ 0x9B);
        et.o[16] = (0xEC ^ 0x85) + (0x96 ^ 0x82) - (9 ^ 0x1A) + (0xAB ^ 0xB6);
        et.o[17] = 77 + 47 - 62 + 93;
        et.o[18] = 109 + 78 - 140 + 113;
        et.o[19] = 0x19 ^ 0xD;
        et.o[20] = 0x84 ^ 0xBD;
        et.o[21] = "   ".length();
        et.o[22] = 0x2A ^ 0x38 ^ (0x63 ^ 0x52);
        et.o[23] = -(0x7B ^ 0x52) & (0xFFFFE7FB & 0x1FFC);
        et.o[24] = -(0xFFFFCBAF & 0x7C77) & (0xFFFFFFF7 & 0x4FFF);
        et.o[25] = 0xFFFFA7D7 & 0x5FFA;
        et.o[26] = 0xFFFFD37C & 0x2DC3;
        et.o[27] = 0x73 ^ 0x5E ^ (0x6B ^ 0x30);
        et.o[28] = 0x1C ^ 0x34;
        et.o[29] = 134 + 144 - 190 + 65 ^ 155 + 117 - 189 + 97;
        et.o[30] = 109 + 193 - 126 + 44;
        et.o[31] = 0x2D ^ 0x4D ^ (0xF0 ^ 0xC5);
        et.o[32] = 0xA8 ^ 0xA7;
        et.o[33] = (0xBB ^ 0xB0) + (0x11 ^ 4) - (0x14 ^ 0x1C) + (84 + 137 - 137 + 62);
        et.o[34] = 16 + 82 - 54 + 106;
        et.o[35] = 0x26 ^ 0x7C;
        et.o[36] = 116 + 47 - 33 + 65 ^ 1 + 118 - 115 + 192;
        et.o[37] = 0x4B ^ 0x79;
        et.o[38] = 0x15 ^ 0x18;
        et.o[39] = 7 ^ 0 ^ (0x6F ^ 0x62);
        et.o[40] = 0x1F ^ 0x2B ^ (0x40 ^ 0x7C);
        et.o[41] = 0xFFFFAF77 & 0x77AD;
        et.o[42] = -(0xFFFFFB7F & 0x1C87) & (0xFFFFFFFF & 0x3F3F);
        et.o[43] = -(0xFFFFDDDD & 0x7AE7) & (0xFFFFFFFF & 0x7FFE);
        et.o[44] = 0xFFFFBBFE & 0x47EF;
        et.o[45] = 0xFFFFEBF3 & 0x17FD;
        et.o[46] = 0xFFFFDBFB & 0x2FBC;
        et.o[47] = -"   ".length() & (0xFFFF9FBB & 0x6BFF);
        et.o[48] = 0xFFFFA7CF & 0x7F7D;
        et.o[49] = 0xFFFFFBB3 & 0x26FE;
        et.o[50] = -(0xFFFFE54D & 0x7AB7) & (0xFFFFEFBF & 0x7FE4);
        et.o[51] = 0xFFFF9FA7 & 0x6FF9;
        et.o[52] = -(0xFFFFBB93 & 0x45EF) & (0xFFFFCFCB & Short.MAX_VALUE);
        et.o[53] = -(0xFFFFF9AC & 0x377F) & (0xFFFFFF7F & Short.MAX_VALUE);
        et.o[54] = -(0xFFFFF5D1 & 0x1F6F) & (0xFFFFBFFD & 0x77F7);
        et.o[55] = 0xFFFFCEF5 & 0x33FEF;
        et.o[56] = 0xFFFF8EE6 & 0x37FFF;
        et.o[57] = -(124 + 128 - 248 + 126) & (0xFFFFFEED & 0x39FF);
        et.o[58] = 0xFFFFCE21 & 0x7FFE;
    }

    public final void h(int n2) {
        if (et.f(this.k.a ? 1 : 0)) {
            this.k.b(n2);
            "".length();
            "".length();
            if ("   ".length() == ((0x2B ^ 0x60) & ~(0xDB ^ 0x90))) {
                return;
            }
        } else if (et.f(this.z.a ? 1 : 0)) {
            this.z.b(n2);
            "".length();
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        } else if (et.f(this.i ? 1 : 0) && et.f(this.v.a ? 1 : 0)) {
            this.v.b(n2);
            "".length();
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        } else if (et.f(this.i ? 1 : 0) && et.f(this.p.a ? 1 : 0)) {
            this.p.b(n2);
            "".length();
        }
        super.h(n2);
    }

    private static boolean f(int n2, int n3) {
        return n2 < n3;
    }
}

