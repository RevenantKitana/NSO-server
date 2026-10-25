/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class cc
implements bv {
    public int a;
    public int b;
    public int c;
    public static int d;
    public static int e;
    public boolean f;
    public fr g;
    public int h;
    public ad i;
    private static final int[] ac;
    public int j;
    public int[] k;
    public int l;
    public boolean m;
    public int n;
    public boolean o;
    public static Image p;
    public int q;
    public static int r;
    public boolean s;
    public static int t;
    public fr u = new fr(ag.pa, ac[0]);
    public int v;
    public static Image w;
    public int x;
    public int y;
    public fr z;
    public int aa;
    public int ab;

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean b(int n2) {
        return n2 < 0;
    }

    public final void a(ad ad2) {
        this.o = ac[0];
        fi.e = null;
        fb.a();
        if (cc.e(ad2.size())) {
            this.i = ad2;
            this.n = ac[4];
            this.j = ac[4];
            int n2 = ac[0];
            while (cc.d(n2, ad2.size())) {
                fr fr2 = (fr)ad2.elementAt(n2);
                if (cc.c(doa.j.a(fr2.d), this.n - ac[5])) {
                    fr2.b = doa.j.a(fr2.d, this.n - ac[5]);
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
            this.h = (em.aj - ad2.size() * this.n) / ac[6];
            if (cc.f(this.h)) {
                this.h = ac[2];
            }
            this.a = em.aq - this.j - (br.a + ac[2]);
            if (cc.e(em.w ? 1 : 0)) {
                this.a -= ac[3];
            }
            this.ab = this.a;
            this.m = ac[2];
            this.c = ac[0];
            r = this.i.size() * this.n - em.aj;
            if (cc.b(r)) {
                r = ac[0];
            }
            t = ac[0];
            e = ac[0];
            d = ac[7];
            this.b = ad2.size() * this.n - ac[2];
            if (cc.c(this.b, em.aj - ac[6])) {
                this.b = em.aj - ac[6];
            }
            if (cc.e(em.w ? 1 : 0)) {
                this.c = ac[8];
            }
        }
    }

    public final void a(int n2, Object object) {
        if (cc.a(n2, ac[14])) {
            Object object2;
            bc bc2 = (bc)object;
            this.i.removeAllElements();
            b.f().e(bc2.d);
            b.f().c.removeElement(bc2);
            "".length();
            n2 = ac[0];
            while (cc.d(n2, b.f().c.size())) {
                object2 = (bc)b.f().c.elementAt(n2);
                this.i.addElement(new fr(((bc)object2).d, null, ac[25], new Integer(n2)));
                ++n2;
                "".length();
                if (-"   ".length() < 0) continue;
                return;
            }
            this.i.addElement(new fr(ag.km, null, ac[26], null));
            this.i.addElement(new fr(ag.ly, null, ac[27], null));
            n2 = ac[0];
            while (cc.d(n2, this.i.size())) {
                object2 = (fr)this.i.elementAt(n2);
                if (cc.c(doa.j.a(((fr)object2).d), this.n - ac[5])) {
                    ((fr)object2).b = doa.j.a(((fr)object2).d, this.n - ac[5]);
                }
                ++n2;
                "".length();
                if ("   ".length() >= -" ".length()) continue;
                return;
            }
            t = this.c * this.n + this.n - em.aj / ac[6];
            r = this.i.size() * this.n - em.aj;
            if (cc.c(t, r)) {
                t = r;
            }
            if (cc.b(t)) {
                t = ac[0];
            }
            if (!cc.b(this.c, this.i.size() - ac[2]) || cc.d(this.c)) {
                e = t;
            }
        }
    }

    static {
        cc.f();
        p = em.a("/hd/btnlBig0.png");
        w = em.a("/hd/btnlBig1.png");
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean c(int n2) {
        return n2 >= 0;
    }

    private static boolean d(int n2) {
        return n2 == 0;
    }

    public void a(eh eh2) {
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public void b_() {
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private void a(bh bh2) {
        try {
            bh2.a(-bh2.a(), -bh2.b());
            bh2.a(-e, ac[0]);
            if (cc.e(em.w ? 1 : 0)) {
                int n2 = ac[0];
                while (cc.d(n2, this.i.size())) {
                    if (cc.a(n2, this.c)) {
                        bh2.a(w, this.h + n2 * this.n + ac[2], this.ab + ac[2], ac[0]);
                        "".length();
                        if (((0x2D ^ 0x63) & ~(0x16 ^ 0x58)) != 0) {
                            return;
                        }
                    } else {
                        bh2.a(p, this.h + n2 * this.n + ac[2], this.ab + ac[2], ac[0]);
                    }
                    String[] stringArray = ((fr)this.i.elementAt((int)n2)).b;
                    if (cc.b(((fr)this.i.elementAt((int)n2)).b)) {
                        String[] stringArray2 = new String[ac[2]];
                        stringArray2[cc.ac[0]] = ((fr)this.i.elementAt((int)n2)).d;
                        stringArray = stringArray2;
                    }
                    int n3 = this.ab + (this.j - stringArray.length * ac[20]) / ac[6] + ac[2];
                    int n4 = ac[0];
                    while (cc.d(n4, stringArray.length)) {
                        if (cc.e(ap.cy ? 1 : 0)) {
                            if (cc.e(b.f().d(stringArray[n4]) ? 1 : 0)) {
                                if (cc.c(em.aa % ac[17], ac[11])) {
                                    doa.a.a(bh2, stringArray[n4], this.h + n2 * this.n + this.n / ac[6] - ac[6], n3 + n4 * ac[20], ac[6]);
                                    "".length();
                                    if (-" ".length() > " ".length()) {
                                        return;
                                    }
                                } else {
                                    doa.j.a(bh2, stringArray[n4], this.h + n2 * this.n + this.n / ac[6] - ac[6], n3 + n4 * ac[20], ac[6]);
                                    "".length();
                                    if (-"  ".length() > 0) {
                                        return;
                                    }
                                }
                            } else {
                                doa.j.a(bh2, stringArray[n4], this.h + n2 * this.n + this.n / ac[6] - ac[6], n3 + n4 * ac[20], ac[6]);
                                "".length();
                                if ((0x18 ^ 0x1C) == 0) {
                                    return;
                                }
                            }
                        } else {
                            doa.j.a(bh2, stringArray[n4], this.h + n2 * this.n + this.n / ac[6] - ac[6], n3 + n4 * ac[20], ac[6]);
                        }
                        ++n4;
                        "".length();
                        if ("  ".length() >= " ".length()) continue;
                        return;
                    }
                    ++n2;
                    "".length();
                    if (-"   ".length() <= 0) continue;
                    return;
                }
            } else {
                int n5 = ac[0];
                while (cc.d(n5, this.i.size())) {
                    if (cc.a(n5, this.c)) {
                        bh2.a(w, this.h + n5 * this.n + ac[2], this.ab + ac[2] - ac[21], ac[0]);
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        bh2.a(p, this.h + n5 * this.n + ac[2], this.ab + ac[2] - ac[21], ac[0]);
                    }
                    String[] stringArray = ((fr)this.i.elementAt((int)n5)).b;
                    if (cc.b(((fr)this.i.elementAt((int)n5)).b)) {
                        String[] stringArray3 = new String[ac[2]];
                        stringArray3[cc.ac[0]] = ((fr)this.i.elementAt((int)n5)).d;
                        stringArray = stringArray3;
                    }
                    int n6 = this.ab + (this.j - stringArray.length * ac[20]) / ac[6] + ac[2] - ac[21];
                    int n7 = ac[0];
                    while (cc.d(n7, stringArray.length)) {
                        if (cc.e(ap.cy ? 1 : 0)) {
                            if (cc.e(b.f().d(stringArray[n7]) ? 1 : 0)) {
                                if (cc.c(em.aa % ac[17], ac[11])) {
                                    doa.a.a(bh2, stringArray[n7], this.h + n5 * this.n + this.n / ac[6] - ac[6], n6 + n7 * ac[20], ac[6]);
                                    "".length();
                                    if (" ".length() < ((3 + 73 - -30 + 22 ^ 175 + 157 - 255 + 115) & (89 + 220 - 199 + 114 ^ 98 + 49 - 109 + 122 ^ -" ".length()))) {
                                        return;
                                    }
                                } else {
                                    doa.j.a(bh2, stringArray[n7], this.h + n5 * this.n + this.n / ac[6] - ac[6], n6 + n7 * ac[20], ac[6]);
                                    "".length();
                                    if (((0x22 ^ 0x40) & ~(0x61 ^ 3)) >= "   ".length()) {
                                        return;
                                    }
                                }
                            } else {
                                doa.j.a(bh2, stringArray[n7], this.h + n5 * this.n + this.n / ac[6] - ac[6], n6 + n7 * ac[20], ac[6]);
                                "".length();
                                if (-" ".length() != -" ".length()) {
                                    return;
                                }
                            }
                        } else {
                            doa.j.a(bh2, stringArray[n7], this.h + n5 * this.n + this.n / ac[6] - ac[6], n6 + n7 * ac[20], ac[6]);
                        }
                        ++n7;
                        "".length();
                        if ((0x6A ^ 0x6F) > 0) continue;
                        return;
                    }
                    ++n5;
                    "".length();
                    if (((151 + 173 - 143 + 1 ^ 71 + 116 - 88 + 51) & (0x19 ^ 0x22 ^ (0xA7 ^ 0xBC) ^ -" ".length())) == 0) continue;
                    return;
                }
            }
            bh2.a(-bh2.a(), -bh2.b());
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((0xC5 ^ 0x97 ^ (0x69 ^ 0x3F)) <= 0) {
            return;
        }
    }

    private void c() {
        if (cc.e(this.q) && cc.d(this.f ? 1 : 0)) {
            if (cc.b(t += this.q / ac[19])) {
                t = ac[0];
                "".length();
                if (" ".length() != " ".length()) {
                    return;
                }
            } else if (cc.c(t, r)) {
                t = r;
                "".length();
                if (-"   ".length() > 0) {
                    return;
                }
            } else {
                e = t;
            }
            this.q = this.q * ac[22] / ac[17];
            if (cc.d(this.q, ac[19]) && cc.c(this.q, ac[23])) {
                this.q = ac[0];
            }
        }
        if (cc.b(e, t) && cc.d(this.f ? 1 : 0)) {
            this.aa = t - e << ac[6];
            this.y += this.aa;
            e += this.y >> ac[9];
            this.y &= ac[24];
        }
        if (cc.c(this.ab, this.a)) {
            int n2 = this.ab - this.a >> ac[2];
            if (cc.f(n2)) {
                n2 = ac[2];
            }
            this.ab -= n2;
        }
        if (cc.e(d) && cc.b(d >>= ac[2])) {
            d = ac[0];
        }
        if (cc.a(this.l)) {
            this.l -= ac[2];
            ap.cy = ac[0];
            if (cc.d(this.l)) {
                fr fr2;
                this.m = ac[0];
                if (cc.c(this.c) && cc.a(fr2 = (fr)this.i.elementAt(this.c))) {
                    fr2.c();
                }
            }
        }
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private void e() {
        if (cc.e(this.m ? 1 : 0)) {
            int n2 = ac[0];
            if (cc.d(em.j[ac[6]]) && cc.d(em.j[ac[9]])) {
                if (cc.d(em.j[ac[5]]) && cc.d(em.j[ac[10]])) {
                    if (cc.e(em.j[ac[11]])) {
                        if (cc.a(this.z)) {
                            if (cc.a(this.z.g)) {
                                if (cc.a(this.z.i, ap.s())) {
                                    ap.s().b(this.z.g, this.z.c);
                                    "".length();
                                    if ((0x6C ^ 0x68) <= 0) {
                                        return;
                                    }
                                } else {
                                    this.a(this.z.g, this.z.c);
                                    "".length();
                                    if (null != null) {
                                        return;
                                    }
                                }
                            }
                        } else {
                            this.l = ac[6];
                            "".length();
                            if (((0xAF ^ 0x99) & ~(0xB7 ^ 0x81)) > 0) {
                                return;
                            }
                        }
                    } else if (cc.e(em.j[ac[12]])) {
                        if (cc.a(this.u.g)) {
                            this.a(this.u.g, this.u.c);
                            "".length();
                            if (((0xB7 ^ 0xBB ^ (0x13 ^ 0x3A)) & (0x6B ^ 6 ^ (0x27 ^ 0x6F) ^ -" ".length())) > "   ".length()) {
                                return;
                            }
                        } else {
                            this.l = ac[6];
                            "".length();
                            if ("   ".length() < 0) {
                                return;
                            }
                        }
                    } else if (cc.d(this.o ? 1 : 0) && (!cc.d(em.j[ac[13]]) || cc.e(ef.a(this.g) ? 1 : 0))) {
                        this.m = ac[0];
                        fb.a();
                        "".length();
                        if (" ".length() == 0) {
                            return;
                        }
                    }
                } else {
                    n2 = ac[2];
                    this.c += ac[2];
                    if (cc.c(this.c, this.i.size() - ac[2])) {
                        this.c = ac[0];
                        "".length();
                        if ("  ".length() == 0) {
                            return;
                        }
                    }
                }
            } else {
                n2 = ac[2];
                this.c -= ac[2];
                if (cc.b(this.c)) {
                    this.c = this.i.size() - ac[2];
                }
            }
            this.z = null;
            if (cc.e(ap.cy ? 1 : 0) && cc.d(em.w ? 1 : 0) && cc.b(this.c, ac[8])) {
                fr fr2 = (fr)this.i.elementAt(this.c);
                bc bc2 = b.f().b(fr2.d);
                if (cc.a(bc2) && cc.a(bc2.c, ac[6])) {
                    this.z = new fr(ag.hw, this, ac[14], bc2);
                }
            }
            if (cc.e(n2)) {
                t = this.c * this.n + this.n - em.aj / ac[6];
                if (cc.c(t, r)) {
                    t = r;
                }
                if (cc.b(t)) {
                    t = ac[0];
                }
                if (!cc.b(this.c, this.i.size() - ac[2]) || cc.d(this.c)) {
                    e = t;
                }
            }
            if (cc.d(this.o ? 1 : 0) && cc.e(em.q ? 1 : 0) && cc.d(em.b(this.h, this.a, this.b, this.j) ? 1 : 0) && cc.d(this.f ? 1 : 0)) {
                this.x = this.v = ac[0];
                this.f = ac[0];
                this.m = ac[0];
                em.q = ac[0];
                return;
            }
            if (cc.e(em.ah ? 1 : 0)) {
                if (cc.d(this.f ? 1 : 0) && cc.e(em.b(this.h, this.a, this.b, this.j) ? 1 : 0)) {
                    int n3;
                    int n4 = ac[0];
                    while (cc.d(n4, this.k.length)) {
                        this.k[cc.ac[0]] = em.ae;
                        ++n4;
                        "".length();
                        if (null == null) continue;
                        return;
                    }
                    this.v = em.ae;
                    this.f = ac[2];
                    if (cc.e(this.q)) {
                        n3 = ac[2];
                        "".length();
                        if (-"   ".length() > 0) {
                            return;
                        }
                    } else {
                        n3 = ac[0];
                    }
                    this.s = n3;
                    this.q = ac[0];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else if (cc.e(this.f ? 1 : 0)) {
                    int n5;
                    this.x += ac[2];
                    if (cc.c(this.x, ac[11]) && cc.a(this.v, em.ae) && cc.d(this.s ? 1 : 0)) {
                        this.v = ac[15];
                        this.c = (t + em.ae - this.h) / this.n;
                    }
                    if (cc.e(n5 = em.ae - this.k[ac[0]]) && cc.b(this.c, ac[8])) {
                        this.c = ac[8];
                    }
                    int n6 = this.k.length - ac[2];
                    while (cc.a(n6)) {
                        int[] nArray = this.k;
                        nArray[n6] = nArray[n6 - ac[2]];
                        --n6;
                        "".length();
                        if ((0xC4 ^ 0xC0) != " ".length()) continue;
                        return;
                    }
                    this.k[cc.ac[0]] = em.ae;
                    if (cc.b(t -= n5)) {
                        t = ac[0];
                    }
                    if (cc.c(t, r)) {
                        t = r;
                    }
                    if (!cc.c(e) || cc.c(e, r)) {
                        n5 /= ac[6];
                    }
                    e -= n5;
                }
            }
            if (cc.e(em.q ? 1 : 0) && cc.e(this.f ? 1 : 0)) {
                int n7 = em.ae - this.k[ac[0]];
                em.q = ac[0];
                if (cc.d(fo.d(n7), ac[16]) && cc.d(fo.d(em.ae - this.v), ac[16]) && cc.d(this.s ? 1 : 0)) {
                    this.q = ac[0];
                    t = e;
                    this.v = ac[15];
                    this.c = (t + em.ae - this.h) / this.n;
                    this.x = ac[0];
                    this.l = ac[17];
                    "".length();
                    if (-"   ".length() > 0) {
                        return;
                    }
                } else if (cc.b(this.c, ac[8]) && cc.c(this.x, ac[11])) {
                    this.x = ac[0];
                    this.l = ac[2];
                    "".length();
                    if (((0x43 ^ 3) & ~(0xFD ^ 0xBD)) != 0) {
                        return;
                    }
                } else if (cc.a(this.c, ac[8]) && cc.d(this.s ? 1 : 0)) {
                    if (cc.b(e)) {
                        t = ac[0];
                        "".length();
                        if ("   ".length() < ((0x50 ^ 0x68) & ~(0x26 ^ 0x1E))) {
                            return;
                        }
                    } else if (cc.c(e, r)) {
                        t = r;
                        "".length();
                        if (-(0x88 ^ 0x8D) >= 0) {
                            return;
                        }
                    } else {
                        int n8;
                        int n9 = em.ae - this.k[ac[0]] + (this.k[ac[0]] - this.k[ac[2]]) + (this.k[ac[2]] - this.k[ac[6]]);
                        if (cc.c(n9, ac[17])) {
                            n8 = ac[17];
                            "".length();
                            if (((0x27 ^ 0x69) & ~(0x8E ^ 0xC0)) != 0) {
                                return;
                            }
                        } else if (cc.d(n9, ac[18])) {
                            n8 = ac[18];
                            "".length();
                            if (" ".length() < ((53 + 2 - 19 + 104 ^ 5 + 142 - 2 + 33) & (25 + 106 - 70 + 115 ^ 73 + 36 - 31 + 64 ^ -" ".length()))) {
                                return;
                            }
                        } else {
                            n8 = ac[0];
                        }
                        this.q = -n8 * ac[19];
                    }
                }
                this.f = ac[0];
                this.x = ac[0];
                em.q = ac[0];
            }
            em.h();
            em.m();
        }
    }

    public void b() {
    }

    private static boolean f(int n2) {
        return n2 <= 0;
    }

    public void a(String string, String string2) {
    }

    public cc() {
        fr fr2;
        if (cc.e(em.w ? 1 : 0)) {
            fr2 = null;
            "".length();
            if ("  ".length() <= 0) {
                throw null;
            }
        } else {
            fr2 = new fr(ag.pf, em.aj - ac[1], em.aq - ef.fs + ac[2]);
        }
        this.g = fr2;
        this.z = null;
        this.k = new int[ac[3]];
    }

    public void d() {
    }

    private static void f() {
        ac = new int[28];
        cc.ac[0] = (136 + 85 - 81 + 85 ^ 131 + 16 - 60 + 108) & (91 + 94 - 91 + 35 ^ 45 + 69 - 2 + 51 ^ -" ".length());
        cc.ac[1] = 0x7F ^ 0x10 ^ (7 ^ 0x2F);
        cc.ac[2] = " ".length();
        cc.ac[3] = "   ".length();
        cc.ac[4] = 0x90 ^ 0xAC;
        cc.ac[5] = 0x56 ^ 0x5E;
        cc.ac[6] = "  ".length();
        cc.ac[7] = 0x91 ^ 0x9D ^ (0x3B ^ 5);
        cc.ac[8] = -" ".length();
        cc.ac[9] = 0x3C ^ 0x48 ^ (0xFF ^ 0x8F);
        cc.ac[10] = 0x81 ^ 0x87;
        cc.ac[11] = 0x52 ^ 0x57;
        cc.ac[12] = 0xA7 ^ 0xAB;
        cc.ac[13] = 0x8F ^ 0x82;
        cc.ac[14] = -(0xFFFFFAC5 & 0x553E) & (0xFFFFD3FB & 0x7FEF);
        cc.ac[15] = -(-(0xFFFFBE77 & 0x799F) & (0xFFFFFFFE & 0x3BFF));
        cc.ac[16] = 1 ^ 0x15;
        cc.ac[17] = 0xAB ^ 0xA1;
        cc.ac[18] = -(0xBE ^ 0xB4);
        cc.ac[19] = 0x4F ^ 0x28 ^ "   ".length();
        cc.ac[20] = 155 + 140 - 150 + 26 ^ 84 + 157 - 147 + 71;
        cc.ac[21] = 0xAF ^ 0x86 ^ (0x3A ^ 4);
        cc.ac[22] = 52 + 88 - 50 + 47 ^ 85 + 9 - -29 + 5;
        cc.ac[23] = -(0x2D ^ 0x63 ^ (0xB8 ^ 0x92));
        cc.ac[24] = 65 + 101 - -9 + 16 ^ 51 + 89 - 114 + 150;
        cc.ac[25] = -(0xFFFFB3F7 & 0x5D0F) & (0xFFFFFFF7 & 0x3FEF);
        cc.ac[26] = -(0xFFFFD709 & 0x69FF) & (0xFFFFFFEE & 0x6FFF);
        cc.ac[27] = -(0xFFFFFDEB & 0x131C) & (0xFFFFBFFF & 0x7FEF);
    }
}

