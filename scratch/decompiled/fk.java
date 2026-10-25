/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public final class fk
extends Canvas
implements Runnable {
    public static fk a;
    private int d = e[0];
    private static final int[] e;
    public static boolean b;
    public ao c;

    public static void a() {
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    protected final void keyPressed(int n2) {
        this.c.b(n2);
    }

    public static void b() {
        a = null;
        b = e[1];
    }

    protected final void pointerPressed(int n2, int n3) {
        this.c.a(n2 /= this.d, n3 /= this.d);
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public final void run() {
        try {
            Thread.sleep(10L);
            "".length();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (((0x69 ^ 0x1C ^ (6 ^ 0x56)) & (0xD2 ^ 0xB2 ^ (0x83 ^ 0xC6) ^ -" ".length())) != 0) {
            return;
        }
        b = e[0];
        while (fk.b(b ? 1 : 0)) {
            try {
                long l2 = System.currentTimeMillis();
                this.c.c();
                long l3 = System.currentTimeMillis() - l2;
                try {
                    long l4;
                    if (fk.a(fk.a(l3, (long)ey.n))) {
                        l4 = (long)ey.n - l3;
                        "".length();
                        if (((105 + 215 - 269 + 179 ^ 128 + 109 - 198 + 144) & (82 + 145 - 119 + 93 ^ 111 + 23 - 112 + 130 ^ -" ".length())) != 0) {
                            return;
                        }
                    } else {
                        l4 = 1L;
                    }
                    Thread.sleep(l4);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (((0xF ^ 0x55) & ~(0x6E ^ 0x34)) != 0) {
                    return;
                }
                "".length();
            }
            catch (Exception exception) {
                try {
                    Thread.sleep(1000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                if (" ".length() == 0) {
                    return;
                }
                exception.printStackTrace();
                "".length();
                if ((0x47 ^ 0x43) >= 0) continue;
                return;
            }
            if (((0xB ^ 1) & ~(0x48 ^ 0x42)) >= 0) continue;
            return;
        }
    }

    protected final void pointerReleased(int n2, int n3) {
        this.c.b(n2 /= this.d, n3 /= this.d);
    }

    protected final void pointerDragged(int n2, int n3) {
        this.c.c(n2 /= this.d, n3 /= this.d);
    }

    protected final void paint(Graphics graphics) {
        this.c.a(graphics);
    }

    protected final void keyReleased(int n2) {
        this.c.a(n2);
    }

    public fk() {
        this.setFullScreenMode(e[0]);
        bh.b = this.d = e[0];
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    public final int c() {
        int n2;
        if (fk.a(this.d, e[0])) {
            n2 = this.getHeight();
            "".length();
            if ("   ".length() <= ((0x63 ^ 0x5A ^ (0xFC ^ 0xC1)) & (0xF ^ 4 ^ (0xB1 ^ 0xBE) ^ -" ".length()))) {
                return (0xEF ^ 0xBB ^ (0x4D ^ 0x4B)) & (0x38 ^ 0x4E ^ (0x6B ^ 0x4F) ^ -" ".length());
            }
        } else {
            n2 = e[1];
        }
        return n2;
    }

    private static void f() {
        e = new int[3];
        fk.e[0] = " ".length();
        fk.e[1] = (85 + 3 - -40 + 2 ^ 10 + 1 - -111 + 53) & (167 + 174 - 247 + 91 ^ 8 + 93 - 37 + 84 ^ -" ".length());
        fk.e[2] = 0x36 ^ 0x1D ^ (0x1E ^ 0);
    }

    public final int d() {
        int n2;
        if (fk.a(this.d, e[0])) {
            n2 = this.getWidth();
            "".length();
            if (-" ".length() > -" ".length()) {
                return (0xCD ^ 0x83 ^ (0xD1 ^ 0x96)) & (54 + 107 - 8 + 27 ^ 41 + 137 - 122 + 133 ^ -" ".length());
            }
        } else {
            n2 = e[1];
        }
        return n2;
    }

    public static fk e() {
        if (fk.a(a)) {
            a = new fk();
        }
        return a;
    }

    static {
        fk.f();
        ce.a(e[2]);
        fk.a();
    }
}

