/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;

public final class dy
implements Runnable {
    public static boolean a;
    public static long b;
    private static final int[] i;
    public static int c;
    public static Random d;
    public final int e;
    public static boolean f;
    public static int g;
    public static int h;

    static {
        dy.a();
        b = 10L;
        d = new Random();
    }

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    public final void run() {
        int n2 = i[0];
        if (dy.b(de.i, i[1])) {
            ap.a("\u0110\u1ee9ng \u1edf Okaza \u0111\u1ec3 auto");
            ap.a(String.valueOf(h));
            "".length();
            if (-(0xF6 ^ 0xAE ^ (0x1A ^ 0x46)) > 0) {
                return;
            }
        } else {
            n2 = i[0];
            ap.b(i[2], i[0], i[0]);
            do {
                ++n2;
                dl.h().ae();
                em.r();
                try {
                    Thread.sleep(b);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                if (-" ".length() < "  ".length()) continue;
                return;
            } while (!dy.a(n2, this.e));
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    dy(int n2) {
        this.e = n2;
    }

    private static void a() {
        i = new int[3];
        dy.i[0] = (0xF4 ^ 0x81 ^ (0xCA ^ 0x9F)) & (0x85 ^ 0xB3 ^ (0x2C ^ 0x3A) ^ -" ".length());
        dy.i[1] = 0x16 ^ 0x5E;
        dy.i[2] = 0x96 ^ 0x88;
    }
}

