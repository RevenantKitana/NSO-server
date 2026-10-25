/*
 * Decompiled with CFR 0.152.
 */
public final class en {
    private String c;
    private static final int[] d;
    public int a;
    public ck b;

    private static boolean a(Object object) {
        return object == null;
    }

    public final String a() {
        if (en.a(this.c)) {
            if (en.a(this.b.b, d[0]) && en.a(this.b.b, d[1]) && en.a(this.b.b, d[2])) {
                this.c = bz.a(this.b.a, "#", String.valueOf(this.a));
                "".length();
                if ((31 + 9 - 34 + 137 ^ 7 + 59 - -49 + 23) == 0) {
                    return null;
                }
            } else {
                float f2 = (float)this.a / 1000.0f;
                this.c = bz.a(this.b.a, "#", String.valueOf(f2));
            }
        }
        return this.c;
    }

    static {
        en.b();
    }

    private static void b() {
        d = new int[3];
        en.d[0] = 56 + 112 - 69 + 33 ^ 32 + 145 - 165 + 174;
        en.d[1] = 30 + 137 - 84 + 111 ^ 16 + 86 - 60 + 88;
        en.d[2] = 0x87 ^ 0xA7 ^ (0x12 ^ 0x74);
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }
}

