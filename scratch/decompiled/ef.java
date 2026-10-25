/*
 * Decompiled with CFR 0.152.
 */
public class ef {
    public static int fr;
    public static int fs;
    public fr ft;
    private static final int[] a;
    public static int fu;
    public static int fv;
    public fr fw;
    public fr fx;

    private static boolean a(Object object, Object object2) {
        return object != object2;
    }

    public static boolean a(fr fr2) {
        if (ef.b(fr2)) {
            return a[8];
        }
        if (ef.a(fr2.h) && ef.a(fr2.a)) {
            return fr2.b();
        }
        if (ef.a(em.d)) {
            if (ef.a((Object)em.d.e) && ef.a(em.c(em.aj - fv >> a[4], em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[4];
                if (ef.b(fr2, em.d.e) && ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
            if (ef.a((Object)em.d.c) && ef.a(em.c(a[8], em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[8];
                if (ef.b(fr2, em.d.c) && ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
            if (ef.a((Object)em.d.d) && ef.a(em.c(em.aj - fv, em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[12];
                if ((!ef.a(fr2, em.d.d) || ef.b(fr2, am.c().e)) && ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
        } else {
            if (ef.b(fr2, em.at.ft) && ef.a(em.c(a[8], em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[8];
                if (ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
            if (ef.b(fr2, em.at.fw) && ef.a(em.c(em.aj - fv, em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[12];
                if (ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
            if ((!ef.a(fr2, em.at.fx) || ef.a(fi.e)) && ef.a(em.c(em.aj - fv >> a[4], em.aq - fs - a[7], fv, fs + a[11]) ? 1 : 0)) {
                fr = a[4];
                if (ef.a(em.am ? 1 : 0) && ef.a(em.q ? 1 : 0)) {
                    return a[4];
                }
            }
        }
        return a[8];
    }

    private static void a() {
        a = new int[13];
        ef.a[0] = 54 + 169 - 166 + 125 ^ 70 + 137 - 184 + 167;
        ef.a[1] = 0x7A ^ 0x68 ^ (0x55 ^ 1);
        ef.a[2] = 0x95 ^ 0x83;
        ef.a[3] = -" ".length();
        ef.a[4] = " ".length();
        ef.a[5] = 0x98 ^ 0xB1 ^ (0x80 ^ 0xB3);
        ef.a[6] = 0x44 ^ 0x26 ^ (0x2E ^ 0x54);
        ef.a[7] = 0xC5 ^ 0xC0;
        ef.a[8] = (28 + 79 - -15 + 7 ^ 40 + 99 - 112 + 109) & (0x8F ^ 0x84 ^ "  ".length() ^ -" ".length());
        ef.a[9] = 0x1E ^ 0x12;
        ef.a[10] = 0x7E ^ 0x2B ^ (0xE6 ^ 0xBE);
        ef.a[11] = 5 + 8 - -41 + 102 ^ 110 + 93 - 110 + 57;
        ef.a[12] = "  ".length();
    }

    public void h(int n2) {
    }

    public static void au() {
        if (ef.a(em.w ? 1 : 0)) {
            fs = a[5];
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        } else {
            fs = a[6];
        }
    }

    public void a(bh bh2) {
        bh2.a(-bh2.a(), -bh2.b());
        bh2.a(a[8], a[8], em.aj, em.aq + a[4]);
        br.a(bh2);
        if (ef.a(fi.e)) {
            br.a(bh2, null, fi.e.f, null);
            "".length();
            if ("  ".length() < " ".length()) {
                return;
            }
        } else if (ef.a(am.c().a ? 1 : 0)) {
            br.a(bh2, am.c().d, am.c().g, am.c().e);
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        } else if (ef.b(em.d) && ef.b(em.a.m ? 1 : 0)) {
            br.a(bh2, this.ft, this.fx, this.fw);
        }
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean b(Object object, Object object2) {
        return object == object2;
    }

    public void c() {
        em.h();
        em.m();
        if (ef.a(em.at)) {
            em.at.q();
        }
        em.at = this;
        fk.a.setFullScreenMode(a[4]);
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public void q() {
    }

    public void f() {
        if (!ef.b(em.j[a[7]]) || ef.a(ef.a(em.at.fx) ? 1 : 0)) {
            em.j[ef.a[7]] = a[8];
            fr = a[3];
            em.q = a[8];
            if (ef.a((Object)this.fx)) {
                this.fx.c();
            }
        }
        if (!ef.b(em.j[a[9]]) || ef.a(ef.a(em.at.ft) ? 1 : 0)) {
            em.j[ef.a[9]] = a[8];
            fr = a[3];
            em.q = a[8];
            if (ef.a(am.c().a ? 1 : 0)) {
                if (ef.a((Object)am.c().d)) {
                    am.c().d.c();
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
            } else if (ef.a((Object)this.ft)) {
                this.ft.c();
            }
        }
        if (!ef.b(em.j[a[10]]) || ef.a(ef.a(em.at.fw) ? 1 : 0)) {
            em.j[ef.a[10]] = a[8];
            fr = a[3];
            em.q = a[8];
            if (ef.a(am.c().a ? 1 : 0)) {
                if (ef.a((Object)am.c().e)) {
                    am.c().e.c();
                    return;
                }
            } else if (ef.a((Object)this.fw)) {
                this.fw.c();
            }
        }
    }

    public void e() {
    }

    private static boolean b(Object object) {
        return object == null;
    }

    static {
        ef.a();
        ef.b();
    }

    private static void b() {
        fu = doa.b.l + a[0];
        fv = a[1] * bh.b;
        fs = a[2];
        fr = a[3];
    }
}

