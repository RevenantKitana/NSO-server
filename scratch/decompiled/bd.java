/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Enumeration;
import javax.microedition.lcdui.Image;

public final class bd {
    public short a;
    private static final int[] f;
    public short b;
    public short c;
    private static Image[] g;
    public static int[][] d;
    private static e h;
    public short e;

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static void a() {
        g = null;
        System.gc();
        Image[] imageArray = new Image[f[0]];
        imageArray[bd.f[1]] = em.a("/img/Big0.png");
        imageArray[bd.f[2]] = em.a("/img/Big1.png");
        imageArray[bd.f[3]] = em.a("/img/Big2.png");
        imageArray[bd.f[4]] = em.a("/img/Big3.png");
        imageArray[bd.f[5]] = em.a("/img/Big4.png");
        g = imageArray;
        int[] nArray = new int[f[2]];
        nArray[bd.f[1]] = f[6];
        Image.createRGBImage((int[])nArray, (int)f[2], (int)f[2], (boolean)f[2]);
        "".length();
    }

    public static void a(int n2, byte[] byArray) {
        ex ex2 = (ex)h.get(String.valueOf(n2));
        if (bd.b(ex2)) {
            ex2 = new ex();
            h.put(String.valueOf(n2), ex2);
            "".length();
        }
        ex2.a = cf.a(byArray);
        if (bd.a((int)fl.l, f[2])) {
            an.a(String.valueOf(n2), byArray);
        }
    }

    public static void b() {
        new bd();
        "".length();
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    public static void c() {
        h = new e();
    }

    private static void g() {
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(an.b("nj_image")));
            short s2 = dataInputStream.readShort();
            d = new int[s2][f[0]];
            int n2 = f[1];
            while (bd.b(n2, s2)) {
                bd.d[n2][bd.f[1]] = dataInputStream.readUnsignedByte();
                bd.d[n2][bd.f[2]] = dataInputStream.readShort();
                bd.d[n2][bd.f[3]] = dataInputStream.readShort();
                bd.d[n2][bd.f[4]] = dataInputStream.readShort();
                bd.d[n2][bd.f[5]] = dataInputStream.readShort();
                ++n2;
                "".length();
                if (-"  ".length() < 0) continue;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (" ".length() < 0) {
            return;
        }
    }

    public static void d() {
        d = null;
        g = null;
        h = null;
    }

    public static void e() {
        try {
            Enumeration enumeration = h.keys();
            while (bd.c(enumeration.hasMoreElements() ? 1 : 0)) {
                String string = (String)enumeration.nextElement();
                ex ex2 = (ex)h.get(string);
                if (bd.d(bd.a(System.currentTimeMillis() - ex2.b, 180000L))) {
                    h.remove(string);
                    "".length();
                }
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (((0x7F ^ 0x1C) & ~(0x6C ^ 0xF)) > "  ".length()) {
            return;
        }
    }

    public static boolean a(int n2) {
        if (!(bd.b(n2, d.length) && bd.b(d[n2][f[2]], g[d[n2][f[1]]].getWidth()) && bd.b(d[n2][f[4]], g[d[n2][f[1]]].getWidth()) && bd.b(d[n2][f[3]], g[d[n2][f[1]]].getHeight()) && !bd.c(d[n2][f[5]], g[d[n2][f[1]]].getHeight()))) {
            ex ex2 = (ex)h.get(String.valueOf(n2));
            if (bd.b(ex2)) {
                ex2 = new ex();
                h.put(String.valueOf(n2), ex2);
                "".length();
                ex2.a = cf.a(an.b(String.valueOf(n2)));
                if (bd.b(ex2.a)) {
                    ex2.c = System.currentTimeMillis();
                    dl.h().d(n2);
                }
                return f[1];
            }
            if (bd.a(ex2.a)) {
                return f[2];
            }
            if (bd.b(ex2.a) && bd.d(bd.c(System.currentTimeMillis() - ex2.c, 60000L))) {
                ex2.c = System.currentTimeMillis();
                dl.h().d(n2);
                return f[1];
            }
        }
        return f[2];
    }

    public static int b(int n2) {
        return d[n2][f[5]];
    }

    public static void f() {
        g = null;
        System.gc();
    }

    public bd() {
        bd.g();
    }

    public static void a(bh bh2, int n2, int n3, int n4, int n5, int n6) {
        if (bd.b(n2, d.length) && bd.b(d[n2][f[2]], g[d[n2][f[1]]].getWidth()) && bd.b(d[n2][f[4]], g[d[n2][f[1]]].getWidth()) && bd.b(d[n2][f[3]], g[d[n2][f[1]]].getHeight()) && bd.b(d[n2][f[5]], g[d[n2][f[1]]].getHeight())) {
            bh2.a(g[d[n2][f[1]]], d[n2][f[2]], d[n2][f[3]], d[n2][f[4]], d[n2][f[5]], n5, n3, n4, n6);
            "".length();
            if (null != null) {
                return;
            }
        } else {
            ex ex2 = (ex)h.get(String.valueOf(n2));
            if (bd.b(ex2)) {
                ex2 = new ex();
                h.put(String.valueOf(n2), ex2);
                "".length();
                ex2.a = cf.a(an.b(String.valueOf(n2)));
                if (bd.b(ex2.a)) {
                    ex2.c = System.currentTimeMillis();
                    dl.h().d(n2);
                    "".length();
                    if ((177 + 4 - -3 + 4 ^ 83 + 30 - 44 + 115) < 0) {
                        return;
                    }
                }
            } else if (bd.b(ex2.a) && bd.d(bd.b(System.currentTimeMillis() - ex2.c, 60000L))) {
                ex2.c = System.currentTimeMillis();
                dl.h().d(n2);
            }
            if (bd.a(ex2)) {
                if (bd.a(ex2.a)) {
                    bh2.a(ex2.a, f[1], f[1], ex2.a.getWidth(), ex2.a.getHeight(), n5, n3, n4, n6);
                }
                ex2.b = System.currentTimeMillis();
            }
        }
    }

    private static void h() {
        f = new int[8];
        bd.f[0] = 0x6C ^ 0x69;
        bd.f[1] = (0x14 ^ 0x56) & ~(0x13 ^ 0x51);
        bd.f[2] = " ".length();
        bd.f[3] = "  ".length();
        bd.f[4] = "   ".length();
        bd.f[5] = 141 + 28 - 148 + 149 ^ 88 + 96 - 70 + 60;
        bd.f[6] = -(-(0xFFFFFA5F & 0x7FEC) & (0xFFFFFBCB & 0x78007E7F));
        bd.f[7] = 0x23 ^ 0x1A;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    static {
        bd.h();
        ce.a(f[7]);
        bd.c();
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public bd(int n2, int n3, int n4, int n5) {
        this.b = (short)n2;
        this.c = (short)n3;
        this.e = (short)n4;
        this.a = (short)n5;
    }

    private static boolean b(Object object) {
        return object == null;
    }
}

