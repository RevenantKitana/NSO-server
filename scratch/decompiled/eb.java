/*
 * Decompiled with CFR 0.152.
 */
public class eb {
    private static final int[] a;
    public ad ad = new ad();

    public final void a(bh bh2, int n2, int n3) {
        int n4 = a[0];
        while (eb.b(n4, this.ad.size())) {
            dz dz2 = (dz)this.ad.elementAt(n4);
            if (eb.a(dz2)) {
                dz2.b(bh2, n2, n3);
            }
            ++n4;
            "".length();
            if ("   ".length() >= 0) continue;
            return;
        }
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    public final void a(byte by2, int n2) {
        int n3 = a[0];
        while (eb.b(n3, this.ad.size())) {
            dz dz2 = (dz)this.ad.elementAt(n3);
            if (eb.a(dz2)) {
                dz2.a();
                if (eb.b(dz2.d ? 1 : 0)) {
                    this.ad.removeElement(dz2);
                    "".length();
                }
            }
            ++n3;
            "".length();
            if (("  ".length() & ~"  ".length()) <= "  ".length()) continue;
            return;
        }
        if (eb.a(by2) && eb.a(n2, a[1])) {
            this.ad.removeAllElements();
        }
        if (eb.a(by2, a[2]) && eb.a(n2)) {
            this.ad.removeAllElements();
        }
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static void a() {
        a = new int[3];
        eb.a[0] = (0xA3 ^ 0x88 ^ (0x2A ^ 0x13)) & (97 + 118 - 129 + 42 ^ 75 + 46 - 9 + 34 ^ -" ".length());
        eb.a[1] = 0x50 ^ 0x5E;
        eb.a[2] = " ".length();
    }

    private static boolean a(Object object) {
        return object != null;
    }

    static {
        eb.a();
    }

    public final void b(bh bh2, int n2, int n3) {
        int n4 = a[0];
        while (eb.b(n4, this.ad.size())) {
            dz dz2 = (dz)this.ad.elementAt(n4);
            if (eb.a(dz2)) {
                dz2.a(bh2, n2, n3);
            }
            ++n4;
            "".length();
            if (-" ".length() <= -" ".length()) continue;
            return;
        }
    }
}

