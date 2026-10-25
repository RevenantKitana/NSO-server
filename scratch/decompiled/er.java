/*
 * Decompiled with CFR 0.152.
 */
public final class er
extends ef {
    private static final int[] b;
    public static int a;

    static {
        er.a();
    }

    private static boolean a(int n2) {
        return n2 <= 0;
    }

    private static void a() {
        b = new int[2];
        er.b[0] = " ".length();
        er.b[1] = 0x37 ^ 0x1E ^ (0xA6 ^ 0x8A);
    }

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    public final void e() {
        int n2 = a;
        a = n2 + b[0];
        if (er.a(n2, b[1])) {
            if (er.a(an.d("indLanguage"))) {
                em.i();
                em.au.c();
                return;
            }
            em.i();
            em.ab.c();
        }
    }
}

