/*
 * Decompiled with CFR 0.152.
 */
public final class dg {
    public boolean a;
    public ec b;
    public int c;
    public int d;
    public short e;
    private static final int[] h;
    public int f;
    public String g;

    public dg(byte by2, int n2, String string, int n3) {
        switch (by2) {
            case 0: {
                this.e = (short)h[0];
                "".length();
                if ((0x16 ^ 0x13) != 0) break;
                throw null;
            }
            case 1: {
                this.e = (short)h[1];
                "".length();
                if (-"  ".length() < 0) break;
                throw null;
            }
            case 2: {
                this.e = (short)h[2];
                "".length();
                if ("  ".length() == "  ".length()) break;
                throw null;
            }
            case 3: {
                this.e = (short)h[3];
                "".length();
                if (("   ".length() & ~"   ".length()) < "   ".length()) break;
                throw null;
            }
            case 4: {
                this.e = (short)h[4];
                "".length();
                if (-(0x83 ^ 0x87) < 0) break;
                throw null;
            }
            case 5: {
                this.e = (short)h[5];
                "".length();
                if (" ".length() == " ".length()) break;
                throw null;
            }
            case 6: {
                this.e = (short)h[6];
            }
        }
        this.g = string;
        this.f = n2;
        this.c = n3;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    static {
        dg.b();
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    public dg(int n2, byte by2, String string, boolean bl2) {
        this.d = n2;
        this.a = bl2;
        switch (by2) {
            case 0: {
                this.e = (short)h[0];
                "".length();
                if (((0x9A ^ 0x80) & ~(0xA4 ^ 0xBE)) == 0) break;
                throw null;
            }
            case 1: {
                this.e = (short)h[1];
                "".length();
                if (" ".length() > 0) break;
                throw null;
            }
            case 2: {
                this.e = (short)h[2];
                "".length();
                if (null == null) break;
                throw null;
            }
            case 3: {
                this.e = (short)h[3];
                "".length();
                if ("   ".length() != 0) break;
                throw null;
            }
            case 4: {
                this.e = (short)h[4];
                "".length();
                if (null == null) break;
                throw null;
            }
            case 5: {
                this.e = (short)h[5];
                "".length();
                if (((0x86 ^ 0xAD) & ~(0x89 ^ 0xA2)) == 0) break;
                throw null;
            }
            case 6: {
                this.e = (short)h[6];
            }
        }
        this.g = string;
        if (dg.c(n2, ec.n().w)) {
            this.b = ec.n();
            "".length();
            if (-(9 + 59 - 44 + 105 ^ 126 + 84 - 165 + 87) >= 0) {
                throw null;
            }
        } else {
            this.b = ap.e(n2);
        }
    }

    public static void a() {
        int n2 = h[7];
        while (dg.b(n2, ap.an.size())) {
            dg dg2 = (dg)ap.an.elementAt(n2);
            if (dg.a(dg2.d, ec.n().w)) {
                dg2.b = ap.e(dg2.d);
            }
            ++n2;
            "".length();
            if ("  ".length() < "   ".length()) continue;
            return;
        }
    }

    public static void a(int n2) {
        int n3 = h[7];
        while (dg.b(n3, ap.an.size())) {
            dg dg2 = (dg)ap.an.elementAt(n3);
            if (dg.c(dg2.d, n2)) {
                dg2.b = null;
                return;
            }
            ++n3;
            "".length();
            if (" ".length() != -" ".length()) continue;
            return;
        }
    }

    private static void b() {
        h = new int[8];
        dg.h[0] = -(0xFFFFE5FD & 0x7E23) & (0xFFFFFEFF & 0x67A7);
        dg.h[1] = -(0xFFFFF5F9 & 0x4B67) & (0xFFFFDDFF & 0x67FE);
        dg.h[2] = -(0xFFFF8A73 & 0x77EF) & (0xFFFFFEFF & 0x7FF);
        dg.h[3] = -(0xFFFFFF7F & 0x61BD) & (0xFFFFE3BF & Short.MAX_VALUE);
        dg.h[4] = -(0xFFFFDDB9 & 0x276F) & (0xFFFFE7FF & 0x1FAD);
        dg.h[5] = 0xFFFFA6A7 & 0x5BFC;
        dg.h[6] = -(0xFFFFBB75 & 0x648B) & (0xFFFFE7DF & 0x3C7F);
        dg.h[7] = (0x41 ^ 0x54 ^ (0x9E ^ 0xAD)) & (0x73 ^ 0x56 ^ "   ".length() ^ -" ".length());
    }
}

