/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class j {
    private int b;
    private int c;
    private static final int[] d;
    public int a;

    private static void a() {
        d = new int[4];
        j.d[0] = 0xB1 ^ 0xA9;
        j.d[1] = 90 + 43 - -9 + 28 ^ 55 + 128 - 39 + 22;
        j.d[2] = "   ".length();
        j.d[3] = 0x57 ^ 0x1B ^ (0x2C ^ 0x41);
    }

    public j(int n2, int n3) {
        this.c = n2 * d[0] + d[1];
        this.b = n3 * d[0] + d[0] + d[2];
    }

    private Object b() {
        Object object;
        Object object2 = de.p.get(String.valueOf(this.a));
        if (j.a(object2)) {
            object = object2;
            "".length();
            if ("  ".length() < ((67 + 112 - 27 + 92 ^ 56 + 80 - -28 + 12) & (0xFA ^ 0x82 ^ (0xA9 ^ 0x95) ^ -" ".length()))) {
                return null;
            }
        } else {
            object = null;
        }
        return object;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    static {
        j.a();
    }

    public final void a(bh bh2) {
        if (j.a(em.e ? 1 : 0) && j.a(this.b())) {
            bh2.a((Image)this.b(), this.c, this.b, d[3]);
        }
    }
}

