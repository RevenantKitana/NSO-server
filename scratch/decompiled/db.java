/*
 * Decompiled with CFR 0.152.
 */
public final class db
extends cl {
    private int e;
    private static final int[] f;
    private short g;
    private long h = 0L;
    private int i;
    private a j;
    private int k;
    private ec l;
    private int m = f[0];

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean a(Object object, Object object2) {
        return object != object2;
    }

    public static void a(int n2, ec ec2, int n3) {
        db db2 = new db();
        new db().j = ap.dx[n2];
        db2.l = ec2;
        db2.h = System.currentTimeMillis() + (long)(n3 * f[2]);
        cl.c.addElement(db2);
    }

    public db() {
        this.g = (short)f[1];
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public static void a(int n2, int n3, int n4, int n5) {
        db db2 = new db();
        new db().j = ap.dx[n2];
        db2.k = n3;
        db2.e = n4;
        db2.g = (short)n5;
        cl.c.addElement(db2);
    }

    static {
        db.a();
    }

    public final void c() {
        if (db.b(db.a(this.h, 0L))) {
            this.i += f[0];
            if (db.b(this.i, this.j.b.length)) {
                this.i = f[1];
            }
            if (db.a(db.a(System.currentTimeMillis() - this.h, 0L))) {
                if (db.a(this.j.a, f[5])) {
                    em.n = f[1];
                }
                cl.c.removeElement(this);
                "".length();
                "".length();
                if ((33 + 131 - 149 + 123 ^ 114 + 101 - 87 + 14) > (0x3D ^ 0x2A ^ (0x71 ^ 0x62))) {
                    return;
                }
            }
        } else {
            this.i += f[0];
            if (db.b(this.i, this.j.b.length)) {
                this.g = (short)(this.g - f[0]);
                if (db.c(this.g)) {
                    if (db.a(this.j.a, f[5])) {
                        em.n = f[1];
                    }
                    cl.c.removeElement(this);
                    "".length();
                    "".length();
                    if (("  ".length() & ~"  ".length()) != ((0xF5 ^ 0xAE) & ~(0x58 ^ 3))) {
                        return;
                    }
                } else {
                    this.i = f[1];
                }
            }
        }
        if (db.d(em.aa % f[6]) && db.a(this.l) && db.a(this.l, ec.n()) && db.d(ap.dn.contains(this.l) ? 1 : 0)) {
            cl.c.removeElement(this);
            "".length();
        }
    }

    private static void a() {
        f = new int[7];
        db.f[0] = " ".length();
        db.f[1] = (0x3A ^ 0x49 ^ (0x71 ^ 0x15)) & (7 ^ 0x2D ^ (0x62 ^ 0x5F) ^ -" ".length());
        db.f[2] = 0xFFFFCFF8 & 0x33EF;
        db.f[3] = "  ".length();
        db.f[4] = "   ".length();
        db.f[5] = 9 ^ 0x71;
        db.f[6] = 0xBB ^ 0xB0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean c(int n2) {
        return n2 <= 0;
    }

    public static void b(int n2, ec ec2, int n3) {
        db db2 = new db();
        new db().j = ap.dx[n2];
        db2.l = ec2;
        db2.g = (short)n3;
        cl.c.addElement(db2);
    }

    public final void a(bh bh2) {
        int n2;
        int n3;
        if (db.a(this.l)) {
            this.k = this.l.s;
            this.e = this.l.fc;
        }
        if (db.b(em.e(n3 = this.k + this.j.b[this.i].b * this.m, n2 = this.e + this.j.b[this.i].c) ? 1 : 0)) {
            int n4;
            int n5 = this.j.b[this.i].a;
            if (db.a(this.m, f[0])) {
                n4 = f[1];
                "".length();
                if ((59 + 45 - -1 + 38 ^ 126 + 57 - 84 + 40) < "   ".length()) {
                    return;
                }
            } else {
                n4 = f[3];
            }
            bd.a(bh2, n5, n3, n2, n4, f[4]);
        }
    }

    private static boolean d(int n2) {
        return n2 == 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 >= n3;
    }

    public static void a(int n2, int n3, int n4, int n5, byte by2) {
        db db2 = new db();
        new db().j = ap.dx[n2];
        db2.k = n3;
        db2.e = n4;
        db2.g = (short)n5;
        db2.m = by2;
        cl.c.addElement(db2);
    }
}

