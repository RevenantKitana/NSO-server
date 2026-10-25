/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class al {
    private int a;
    private int b;
    private int c;
    private int d;
    private static final int[] e;
    private Image f;
    private int[] g;

    public al(Image image, int n2, int n3) {
        this.f = image;
        this.d = n2;
        this.c = n3;
        this.b = image.getHeight();
        this.a = this.b / n3;
        this.g = new int[this.a];
        int n4 = e[0];
        while (al.a(n4, this.a)) {
            this.g[n4] = n4 * n3;
            ++n4;
            "".length();
            if ((121 + 95 - 171 + 85 ^ 1 + 32 - -30 + 72) > 0) continue;
            throw null;
        }
    }

    private static void a() {
        e = new int[1];
        al.e[0] = (0xFF ^ 0xAA) & ~(0x4B ^ 0x1E);
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, bh bh2) {
        if (al.a(n2) && al.a(n2, this.a)) {
            bh2.a(this.f, e[0], this.g[n2], this.d, this.c, n5, n3, n4, n6);
        }
    }

    static {
        al.a();
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }
}

