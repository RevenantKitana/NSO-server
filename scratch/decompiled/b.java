/*
 * Decompiled with CFR 0.152.
 */
public final class b {
    public static boolean a;
    public static boolean b;
    public ad c = new ad();
    public ad d;
    public int e = i[1];
    public static boolean f;
    static b g;
    public static boolean h;
    private static final int[] i;

    public final void a(String string) {
        int n2 = i[1];
        while (b.a(n2, this.d.size())) {
            if (b.d(((String)this.d.elementAt(n2)).equals(string) ? 1 : 0)) {
                return;
            }
            ++n2;
            "".length();
            if ("   ".length() != " ".length()) continue;
            return;
        }
        this.d.addElement(string);
    }

    public final void a() {
        this.e += i[0];
        if (b.b(this.e, this.c.size() - i[0])) {
            this.e = i[1];
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    public final void b() {
        this.e = this.c.size() - i[0];
    }

    public static void c() {
    }

    public final bc d() {
        return (bc)this.c.elementAt(this.e);
    }

    public static void e() {
        g = null;
        h = i[1];
        f = i[1];
        a = i[1];
        b = i[1];
    }

    private static boolean b(int n2) {
        return n2 < 0;
    }

    public final void a(String string, String string2, String string3) {
        bc bc2 = this.b(string);
        if (b.a(bc2)) {
            bc2 = this.c(string);
        }
        bc2.a(string2, string3);
    }

    public final bc b(String string) {
        int n2 = i[1];
        while (b.a(n2, this.c.size())) {
            bc bc2 = (bc)this.c.elementAt(n2);
            if (b.d(bc2.d.equals(string) ? 1 : 0)) {
                return bc2;
            }
            ++n2;
            "".length();
            if (-" ".length() < "  ".length()) continue;
            return null;
        }
        return null;
    }

    public final void a(int n2) {
        this.e = n2;
    }

    public final bc c(String string) {
        bc bc2 = new bc(string, i[4]);
        if (b.c(em.w ? 1 : 0)) {
            bc2.a("c2" + ag.hd);
        }
        this.c.addElement(bc2);
        return bc2;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static void h() {
        g = null;
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    public final boolean d(String string) {
        int n2 = i[1];
        while (b.a(n2, this.d.size())) {
            if (b.d(((String)this.d.elementAt(n2)).equals(string) ? 1 : 0)) {
                return i[0];
            }
            ++n2;
            "".length();
            if (((0xA ^ 0x77 ^ (0xC8 ^ 0x8A)) & (0x4D ^ 0x3E ^ (9 ^ 0x45) ^ -" ".length())) <= "  ".length()) continue;
            return ((0x9A ^ 0xB0 ^ (0x28 ^ 0x4F)) & (0x84 ^ 0xA0 ^ (0x7C ^ 0x15) ^ -" ".length())) != 0;
        }
        return i[1];
    }

    private static boolean c(int n2) {
        return n2 == 0;
    }

    public b() {
        this.d = new ad();
        this.c.addElement(new bc(ag.kd[i[1]], i[1]));
        this.c.addElement(new bc(ag.lp[i[1]], i[0]));
        this.c.addElement(new bc(ag.lk[i[1]], i[2]));
        this.c.addElement(new bc(ag.mn[i[1]], i[3]));
        bc bc2 = this.b(ag.lk[i[1]]);
        bc2.a("c8" + ag.lk[i[0]]);
        bc2.a("c8" + ag.lk[i[4]]);
        bc2.a("c8" + ag.lk[i[2]]);
        this.b(ag.lp[i[1]]).a("c8" + ag.lp[i[0]]);
        this.b(ag.mn[i[1]]).a("c8" + ag.mn[i[0]]);
        this.b(ag.kd[i[1]]).a("c8" + ag.kd[i[0]]);
    }

    private static boolean d(int n2) {
        return n2 != 0;
    }

    public final void e(String string) {
        int n2 = i[1];
        while (b.a(n2, this.d.size())) {
            if (b.d(((String)this.d.elementAt(n2)).equals(string) ? 1 : 0)) {
                this.d.removeElementAt(n2);
                return;
            }
            ++n2;
            "".length();
            if (" ".length() > 0) continue;
            return;
        }
    }

    public final void a(bc bc2) {
        this.e = this.c.indexOf(bc2);
    }

    private static void i() {
        i = new int[6];
        b.i[0] = " ".length();
        b.i[1] = (0x16 ^ 0x6C ^ (0xA0 ^ 0x99)) & (0x7B ^ 0x1E ^ (0x60 ^ 0x46) ^ -" ".length());
        b.i[2] = "   ".length();
        b.i[3] = 0x9A ^ 0x90 ^ (0x6D ^ 0x63);
        b.i[4] = "  ".length();
        b.i[5] = 120 + 1 - 101 + 163 ^ 92 + 89 - 119 + 109;
    }

    public static b f() {
        b b2;
        if (b.a(g)) {
            b2 = g = new b();
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            b2 = g;
        }
        return b2;
    }

    static {
        b.i();
        ce.a(i[5]);
        b.c();
    }

    public final void g() {
        this.e -= i[0];
        if (b.b(this.e)) {
            this.e = this.c.size() - i[0];
        }
    }
}

