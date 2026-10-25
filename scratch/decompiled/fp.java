/*
 * Decompiled with CFR 0.152.
 */
public final class fp {
    private static final int[] d;
    public String a;
    public int b;
    doa c;

    private static void a() {
        d = new int[1];
        fp.d[0] = 0x31 ^ 0x25;
    }

    public fp(String string, doa doa2, int n2) {
        this.c = doa2;
        this.a = string;
        this.b = n2;
    }

    public fp() {
    }

    static {
        fp.a();
    }

    public fp(String string) {
        this.c = doa.m;
        this.a = string;
        this.b = d[0];
    }
}

