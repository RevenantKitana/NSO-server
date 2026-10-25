/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class fr {
    private static final int[] k;
    public int a;
    public String[] b;
    public Object c;
    public String d;
    public int e;
    public Image f;
    public int g;
    public int h;
    public bv i;
    private boolean l;
    public int j;

    public fr(String string, int n2) {
        this.h = k[0];
        this.a = k[0];
        this.e = ef.fv;
        this.j = ef.fs;
        this.l = k[0];
        this.d = string;
        this.g = n2;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    public fr(String string, int n2, Object object) {
        this.h = k[0];
        this.a = k[0];
        this.e = ef.fv;
        this.j = ef.fs;
        this.l = k[0];
        this.d = string;
        this.g = n2;
        this.c = object;
    }

    private static void d() {
        k = new int[6];
        fr.k[0] = (0x11 ^ 0x59 ^ (0x8D ^ 0x9C)) & (135 + 94 - 163 + 174 ^ 34 + 4 - 15 + 146 ^ -" ".length());
        fr.k[1] = "  ".length();
        fr.k[2] = "   ".length();
        fr.k[3] = 6 ^ 0x22;
        fr.k[4] = 0x82 ^ 0xB3 ^ (0xC ^ 0x3B);
        fr.k[5] = " ".length();
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public fr() {
    }

    public final void a() {
        if (fr.b(this.h) && fr.b(this.a) && fr.c(this.b() ? 1 : 0)) {
            this.c();
            em.ah = k[0];
            this.l = k[0];
        }
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private static boolean a(Object object, Object object2) {
        return object != object2;
    }

    public fr(String string, int n2, int n3) {
        this.h = k[0];
        this.a = k[0];
        this.e = ef.fv;
        this.j = ef.fs;
        this.l = k[0];
        this.d = string;
        this.g = k[0];
        this.h = n2;
        this.a = n3;
    }

    static {
        fr.d();
    }

    public final boolean b() {
        this.l = k[0];
        if (fr.c(em.c(this.h - k[2], this.a - k[2], this.e + k[4], this.j + k[4]) ? 1 : 0)) {
            if (fr.c(em.ah ? 1 : 0)) {
                this.l = k[5];
            }
            if (fr.c(em.q ? 1 : 0) && fr.c(em.am ? 1 : 0)) {
                return k[5];
            }
        }
        return k[0];
    }

    public fr(String string, bv bv2, int n2, int n3, int n4) {
        this.h = k[0];
        this.a = k[0];
        this.e = ef.fv;
        this.j = ef.fs;
        this.l = k[0];
        this.d = string;
        this.g = n2;
        this.i = bv2;
        this.c = null;
        this.h = n3;
        this.a = n4;
        this.e = ef.fv;
        this.j = ef.fs;
    }

    public fr(String string, bv bv2, int n2, Object object) {
        this.h = k[0];
        this.a = k[0];
        this.e = ef.fv;
        this.j = ef.fs;
        this.l = k[0];
        this.d = string;
        this.g = n2;
        this.i = bv2;
        this.c = object;
    }

    public final void c() {
        if (fr.b(this.g)) {
            if (fr.a(this.i)) {
                this.i.a(this.g, this.c);
                return;
            }
            ap.s().b(this.g, this.c);
        }
    }

    public final void a(bh bh2) {
        if (fr.a(this.f)) {
            bh2.a(this.f, this.h + bh.a(this.f) / k[1], this.a + bh.b(this.f) / k[1], k[2]);
            "".length();
            if ("   ".length() <= "  ".length()) {
                return;
            }
        } else {
            if (fr.a(this.d, "")) {
                if (fr.a(this.l ? 1 : 0)) {
                    bh2.a(ap.et, this.h, this.a, k[0]);
                    "".length();
                    if ((0x14 ^ 0x11) <= 0) {
                        return;
                    }
                } else {
                    bh2.a(ap.ak, this.h, this.a, k[0]);
                }
            }
            doa.r.a(bh2, this.d, this.h + k[3], this.a + k[4], k[1]);
        }
    }
}

