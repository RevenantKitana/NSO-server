/*
 * Decompiled with CFR 0.152.
 */
public final class cz {
    private static final int[] b;
    public bb[] a;

    private static void a() {
        b = new int[6];
        cz.b[0] = 0x6A ^ 0x62;
        cz.b[1] = " ".length();
        cz.b[2] = 0x67 ^ 0x75;
        cz.b[3] = "  ".length();
        cz.b[4] = 0xB8 ^ 0xB2;
        cz.b[5] = "   ".length();
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    public cz(int n2) {
        if (cz.a(n2)) {
            this.a = new bb[b[0]];
        }
        if (cz.a(n2, b[1])) {
            this.a = new bb[b[2]];
        }
        if (cz.a(n2, b[3])) {
            this.a = new bb[b[4]];
        }
        if (cz.a(n2, b[5])) {
            this.a = new bb[b[3]];
        }
    }

    static {
        cz.a();
    }
}

