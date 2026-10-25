/*
 * Decompiled with CFR 0.152.
 */
public final class bc {
    public ad a = new ad();
    public long b = 0L;
    public int c;
    private static final int[] e;
    public String d;

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    public final void a(String string) {
        int n2 = e[0];
        if (bc.a(ap.ek ? 1 : 0) && bc.a(b.f().d(), this) && bc.c(ap.en, this.a.size() - e[1])) {
            n2 = e[1];
        }
        ad ad2 = doa.m.b(string, e[2]);
        int n3 = e[0];
        while (bc.b(n3, ad2.size())) {
            this.a.addElement(ad2.elementAt(n3));
            ++n3;
            "".length();
            if (-(0 ^ 0x4B ^ (0xF6 ^ 0xB9)) < 0) continue;
            return;
        }
        if (bc.a(n2)) {
            ap.s().r();
        }
        this.b();
    }

    private static void a() {
        e = new int[4];
        bc.e[0] = (0xCE ^ 0x88) & ~(0xF3 ^ 0xB5);
        bc.e[1] = " ".length();
        bc.e[2] = (0x3E ^ 0x66) + (0xB8 ^ 0xC6) - (18 + 59 - -104 + 25) + (10 + 20 - -84 + 38);
        bc.e[3] = 0xE ^ 0x3C;
    }

    static {
        bc.a();
    }

    public final void a(String string, String string2) {
        int n2 = e[0];
        if (bc.a(ap.ek ? 1 : 0) && bc.a(b.f().d(), this) && bc.c(ap.en, this.a.size() - e[1])) {
            n2 = e[1];
        }
        this.a.addElement("c3@".concat(String.valueOf(string)));
        ad ad2 = doa.m.b(string2, e[2]);
        int n3 = e[0];
        while (bc.b(n3, ad2.size())) {
            this.a.addElement("c0" + ad2.elementAt(n3));
            ++n3;
            "".length();
            if ("   ".length() < (0x8F ^ 0xB4 ^ (0xB1 ^ 0x8E))) continue;
            return;
        }
        if (bc.a(n2)) {
            ap.s().r();
        }
        this.b();
    }

    public bc(String string, int n2) {
        this.d = string;
        this.c = n2;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private void b() {
        while (bc.a(this.a.size(), e[3])) {
            this.a.removeElementAt(e[1]);
            "".length();
            if (((117 + 77 - -34 + 16 ^ 140 + 103 - 136 + 71) & (204 + 206 - 330 + 142 ^ 133 + 82 - 188 + 125 ^ -" ".length())) == 0) continue;
            return;
        }
    }
}

