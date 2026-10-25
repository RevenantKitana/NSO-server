/*
 * Decompiled with CFR 0.152.
 */
public final class cx
implements Runnable {
    private final int a;
    private final int b;
    private static final int[] c;

    private static boolean a(Object object) {
        return object == null;
    }

    static {
        cx.a();
    }

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    public final void run() {
        if (cx.a(ec.n().cg, c[0]) && cx.a(ec.n().bu, c[1])) {
            el el2 = ec.b(c[2]);
            if (cx.a(el2) && cx.a(el2 = ec.b(c[3]))) {
                ap.b(c[4], c[5], c[5]);
                dl.h().b(c[6], c[7], c[8]);
                bo.aa();
                ax.a(100L);
                el2 = ec.b(c[3]);
            }
            if (cx.b(el2)) {
                dl.h().g(el2.n, this.a);
                de.g();
            }
            "".length();
            if ("   ".length() <= "  ".length()) {
                return;
            }
        } else {
            de.m(this.b);
        }
    }

    private static void a() {
        c = new int[9];
        cx.c[0] = 0xFFFFA7ED & 0x5BFA;
        cx.c[1] = 0x4A ^ 0x4F ^ (0x46 ^ 0x5D);
        cx.c[2] = 0xE4 ^ 0xC1;
        cx.c[3] = 0x3C ^ 0x1F;
        cx.c[4] = 0x7C ^ 0x78;
        cx.c[5] = (103 + 10 - -1 + 69 ^ 141 + 73 - 157 + 101) & (0xC5 ^ 0xC3 ^ (0x6E ^ 0x41) ^ -" ".length());
        cx.c[6] = 0x30 ^ 0x39;
        cx.c[7] = 28 + 151 - 140 + 145 ^ 28 + 134 - 66 + 94;
        cx.c[8] = " ".length();
    }

    cx(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }
}

