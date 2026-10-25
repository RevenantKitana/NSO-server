/*
 * Decompiled with CFR 0.152.
 */
public final class fi
extends cl
implements bv {
    public static fi e;
    private int g;
    private int h = j[0];
    public fr f;
    private String[] i;
    private static final int[] j;
    private int k;
    private int l;
    private int m;
    private int n;
    private ec o;
    private String[] p;
    private int q;

    public final void c() {
        if (fi.a(this.o)) {
            this.n = this.o.s;
            this.k = this.o.fc;
            this.q = this.o.t + j[5];
        }
        if (fi.a(this.m)) {
            this.m -= j[2];
        }
        if (fi.b(this.g, j[2])) {
            this.g -= j[2];
        }
        if (fi.a(this.o) && fi.a(this.o.fi) && !fi.a(this.o.fi, this) || fi.a(this.o) && !fi.a(this.o.fi) || fi.b(this.m)) {
            cl.a.removeElement(this);
            "".length();
            cl.c.removeElement(this);
            "".length();
        }
    }

    public static void a(String string, ec ec2) {
        String[] stringArray = fo.a(string, "\n", j[1]);
        if (fi.a(stringArray.length, j[2])) {
            fi.a(stringArray[j[1]], j[3], ec2);
            "".length();
            "".length();
            if ("   ".length() == 0) {
                return;
            }
        } else {
            e = fi.a(stringArray[j[1]], j[3], ec2);
            fi.a(stringArray[j[1]], j[3], ec2).l = j[1];
            fi.e.p = stringArray;
            fi.e.f = new fr(ag.bn, e, j[4], null);
        }
    }

    public void b() {
    }

    public void a(String string, String string2) {
    }

    public void b_() {
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public final void a(bh bh2) {
        int n2 = this.n;
        int n3 = this.k;
        bh2.a(j[9]);
        bh2.b(n2 - this.h / j[10] - j[2], n3 - this.q - j[11] + this.g - this.i.length * j[12] - j[5], this.h + j[10], (this.i.length + j[2]) * j[12] + j[2], j[12], j[12]);
        bh2.a(j[1]);
        bh2.a(n2 - this.h / j[10] - j[2], n3 - this.q - j[11] + this.g - this.i.length * j[12] - j[5], this.h + j[2], (this.i.length + j[2]) * j[12], j[12], j[12]);
        bd.a(bh2, j[13], n2 - j[14], n3 - this.q - j[11] + this.g + j[10], j[1], j[15]);
        int n4 = j[1];
        while (fi.c(n4, this.i.length)) {
            doa.i.a(bh2, this.i[n4], n2, n3 - this.q - j[11] + this.g + n4 * j[12] - this.i.length * j[12] - j[16], j[10]);
            ++n4;
            "".length();
            if (((0x51 ^ 0xC) & ~(0x22 ^ 0x7F)) == ((0x1E ^ 0x17) & ~(0 ^ 9))) continue;
            return;
        }
    }

    public static fi a(String string, int n2, ec ec2) {
        fi fi2 = new fi();
        if (fi.c(string.length(), j[5])) {
            fi2.h = j[6];
        }
        if (fi.a(em.aj, j[7])) {
            fi2.h = j[7];
        }
        fi2.i = doa.a.a(string, fi2.h - j[5]);
        fi2.m = n2;
        fi2.o = ec2;
        fi2.n = ec2.s;
        fi2.k = ec2.fc;
        ec2.fi = fi2;
        fi2.g = j[8];
        cl.c.addElement(fi2);
        return fi2;
    }

    private static void e() {
        j = new int[17];
        fi.j[0] = 0x4C ^ 0x28;
        fi.j[1] = (0x96 ^ 0x89 ^ (0x36 ^ 9)) & (0x89 ^ 0xC0 ^ (0x24 ^ 0x4D) ^ -" ".length());
        fi.j[2] = " ".length();
        fi.j[3] = -(0xFFFFA7FF & 0x7C15) & (0xFFFFFFFC & 0x27FF);
        fi.j[4] = -(0xFFFFE7BD & 0x58FB) & (0xFFFFFFFF & 0x5FF8);
        fi.j[5] = 0xAC ^ 0xA6;
        fi.j[6] = 0xF8 ^ 0x9C ^ (0x3A ^ 0x1E);
        fi.j[7] = (0x4B ^ 0x5F) + (0x71 ^ 0x3B) - (0x53 ^ 0x6E) + (0xD ^ 0x52);
        fi.j[8] = 57 + 1 - -66 + 4 ^ 42 + 4 - -83 + 6;
        fi.j[9] = 0xFFFFFFFF & 0xFFFFFF;
        fi.j[10] = "  ".length();
        fi.j[11] = 0x9E ^ 0x91;
        fi.j[12] = 0x71 ^ 0x58 ^ (0x21 ^ 4);
        fi.j[13] = -(0xFFFFBA6B & 0x7595) & (0xFFFFFFBD & 0x33EF);
        fi.j[14] = "   ".length();
        fi.j[15] = 0x7B ^ 0x6A;
        fi.j[16] = 0xC5 ^ 0xC1;
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    static {
        fi.e();
    }

    public void a(eh eh2) {
    }

    public void d() {
    }

    public final void a(int n2, Object object) {
        if (fi.a(n2, j[4])) {
            n2 = fi.e.l;
            if (fi.d(++n2, fi.e.p.length)) {
                fi.e.o.fi = null;
                e = null;
                return;
            }
            fi fi2 = fi.a(fi.e.p[n2], fi.e.m, fi.e.o);
            fi.a(fi.e.p[n2], fi.e.m, fi.e.o).l = n2;
            fi2.p = fi.e.p;
            fi2.f = fi.e.f;
            e = fi2;
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }
}

