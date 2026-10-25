/*
 * Decompiled with CFR 0.152.
 */
public abstract class cl {
    public static ad a;
    private static final int[] e;
    public static ad b;
    public static ad c;
    public static ad d;

    private static void a() {
        e = new int[1];
        cl.e[0] = 50 + 52 - 9 + 64 ^ 44 + 22 - 40 + 106;
    }

    public static void f() {
        c = new ad();
        d = new ad();
        a = new ad();
        b = new ad();
    }

    public abstract void a(bh var1);

    static {
        cl.a();
        ce.a(e[0]);
        cl.f();
    }

    public abstract void c();

    public static void g() {
        c = null;
        d = null;
        a = null;
        b = null;
    }
}

