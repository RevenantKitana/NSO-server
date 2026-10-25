/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Enumeration;

public final class z
extends cl {
    private boolean e;
    private ex f;
    private int g;
    private static final int[] h;
    private short i;
    private int j;
    private byte k = (byte)h[0];
    private int l;
    private static e m;
    private int n;
    private long o = 0L;
    private static e p;
    private static bs[] q;

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final void a(bh bh2) {
        if (z.b(this.f) && z.b(this.f.a) && z.b(this.h().c)) {
            h h2 = this.h().b[this.h().c[this.j]];
            int n2 = h[0];
            while (z.b(n2, h2.c.length)) {
                bs bs2 = this.h();
                byte by2 = h2.b[n2];
                ai ai2 = bs2.a[by2];
                if (z.b(this.l)) {
                    bh2.a(this.f.a, ai2.b, ai2.a, ai2.d, ai2.c, h[0], this.g + h2.c[n2], this.n + h2.a[n2] - h[2], h[1]);
                    "".length();
                    if (" ".length() < 0) {
                        return;
                    }
                } else {
                    bh2.a(this.f.a, ai2.b, ai2.a, ai2.d, ai2.c, h[4], this.g - h2.c[n2], this.n + h2.a[n2] - h[2], h[5]);
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
        }
    }

    private bs h() {
        return q[this.i];
    }

    public static void a(short s2, byte[] byArray) {
        if (z.a(((bs)z.p.get((Object)String.valueOf((int)s2))).c)) {
            new bs();
            "".length();
            bs bs2 = z.a(byArray);
            p.put(String.valueOf(s2), bs2);
            "".length();
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(int n2) {
        return n2 <= 0;
    }

    public static void a(byte by2, byte[] byArray) {
        ex ex2 = (ex)m.get(String.valueOf(by2));
        if (z.a(ex2)) {
            ex2 = new ex();
            m.put(String.valueOf(by2), ex2);
            "".length();
        }
        ex2.a = cf.a(byArray);
        if (z.a((int)fl.l, h[2])) {
            an.a("effauto ".concat(String.valueOf(by2)), byArray);
        }
    }

    public static void a() {
        q = new bs[h[1]];
        m = new e();
        p = new e();
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static void a(short s2, int n2, int n3, byte by2, short s3, int n4) {
        z z2 = new z();
        new z().i = s2;
        z2.g = n2;
        z2.n = n3;
        z2.k = by2;
        if (z.d(z2.k)) {
            z2.e = h[2];
            "".length();
            if (" ".length() == 0) {
                return;
            }
        } else {
            z2.e = h[0];
        }
        if (z.b(s3)) {
            z2.o = System.currentTimeMillis() + (long)(s3 * h[3]);
            "".length();
            if (" ".length() == ("  ".length() & ("  ".length() ^ -" ".length()))) {
                return;
            }
        } else {
            z2.o = -1L;
        }
        z2.l = n4;
        cl.c.addElement(z2);
    }

    private static boolean b(int n2) {
        return n2 > 0;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    private static void i() {
        h = new int[7];
        z.h[0] = (0x41 ^ 0x77) & ~(0x76 ^ 0x40);
        z.h[1] = 88 + 172 - 132 + 48 ^ 19 + 40 - 28 + 133;
        z.h[2] = " ".length();
        z.h[3] = -(0xFFFFD897 & 0x677C) & (0xFFFFCFFB & 0x73FF);
        z.h[4] = "  ".length();
        z.h[5] = 150 + 157 - 144 + 22 ^ 48 + 103 - 126 + 136;
        z.h[6] = 0xBD ^ 0x9A;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean d(int n2) {
        return n2 < 0;
    }

    public static void b() {
        try {
            Enumeration enumeration = m.keys();
            while (z.c(enumeration.hasMoreElements() ? 1 : 0)) {
                String string = (String)enumeration.nextElement();
                ex ex2 = (ex)m.get(string);
                if (z.b(z.c(System.currentTimeMillis() - ex2.b, 60000L))) {
                    m.remove(string);
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
        if (null != null) {
            return;
        }
    }

    static {
        z.i();
        ce.a(h[6]);
        z.a();
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean e(int n2) {
        return n2 == 0;
    }

    private static bs a(byte[] byArray) {
        try {
            int n2;
            short s2;
            bs bs2 = new bs();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            bs2.a = new ai[dataInputStream.readByte()];
            int n3 = h[0];
            while (z.b(n3, bs2.a.length)) {
                bs2.a[n3] = new ai();
                dataInputStream.readByte();
                "".length();
                bs2.a[n3].b = dataInputStream.readUnsignedByte();
                bs2.a[n3].a = dataInputStream.readUnsignedByte();
                bs2.a[n3].d = dataInputStream.readUnsignedByte();
                bs2.a[n3].c = dataInputStream.readUnsignedByte();
                ++n3;
                "".length();
                if (" ".length() != -" ".length()) continue;
                return null;
            }
            bs2.b = new h[dataInputStream.readShort()];
            n3 = h[0];
            while (z.b(n3, bs2.b.length)) {
                bs2.b[n3] = new h();
                s2 = dataInputStream.readByte();
                bs2.b[n3].c = new short[s2];
                bs2.b[n3].a = new short[s2];
                bs2.b[n3].b = new byte[s2];
                n2 = h[0];
                while (z.b(n2, s2)) {
                    bs2.b[n3].c[n2] = dataInputStream.readShort();
                    bs2.b[n3].a[n2] = dataInputStream.readShort();
                    bs2.b[n3].b[n2] = dataInputStream.readByte();
                    ++n2;
                    "".length();
                    if (((0x44 ^ 0x19 ^ (0x4E ^ 0xD)) & (134 + 132 - 98 + 11 ^ 100 + 158 - 148 + 63 ^ -" ".length())) == ((0x98 ^ 0xA8 ^ 100 + 101 - 112 + 38) & (0xFB ^ 0x82 ^ (0x9E ^ 0xA8) ^ -" ".length()))) continue;
                    return null;
                }
                ++n3;
                "".length();
                if (((0xF7 ^ 0xB6 ^ (1 ^ 0x17)) & (0x2B ^ 0x72 ^ (0x49 ^ 0x47) ^ -" ".length())) == 0) continue;
                return null;
            }
            s2 = dataInputStream.readShort();
            bs2.c = new short[s2];
            n2 = h[0];
            while (z.b(n2, s2)) {
                bs2.c[n2] = dataInputStream.readShort();
                ++n2;
                "".length();
                if (-"   ".length() < 0) continue;
                return null;
            }
            return bs2;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static void d() {
        try {
            Enumeration enumeration = p.keys();
            while (z.c(enumeration.hasMoreElements() ? 1 : 0)) {
                String string = (String)enumeration.nextElement();
                bs bs2 = (bs)p.get(string);
                if (z.b(z.a(System.currentTimeMillis() - bs2.e, 600000L))) {
                    p.remove(string);
                    "".length();
                }
                "".length();
                if (((0x58 ^ 0xA) & ~(0x11 ^ 0x43)) == 0) continue;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ("   ".length() == "  ".length()) {
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    public static void e() {
        q = null;
        m = null;
        p = null;
    }

    /*
     * Unable to fully structure code
     */
    public final void c() {
        try {
            block20: {
                block21: {
                    block22: {
                        z.q[this.i] = (bs)z.p.get(String.valueOf(this.i));
                        if (z.a(z.q[this.i])) {
                            z.q[this.i] = new bs();
                            z.p.put(String.valueOf(this.i), z.q[this.i]);
                            "".length();
                            this.h().d = System.currentTimeMillis();
                            dl.h().a(z.h[2], this.i);
                            "".length();
                            if (((36 ^ 34 ^ (141 ^ 184)) & (232 + 169 - 202 + 43 ^ 92 + 189 - 186 + 98 ^ -" ".length())) != 0) {
                                return;
                            }
                        } else if (z.a(this.h().c) && z.b(z.b(System.currentTimeMillis() - this.h().d, 3000L))) {
                            this.h().d = System.currentTimeMillis();
                            dl.h().a(z.h[2], this.i);
                        }
                        if (z.b(this.h().c)) {
                            this.f = (ex)z.m.get(String.valueOf(this.i));
                            if (z.a(this.f)) {
                                this.f = new ex();
                                z.m.put(String.valueOf(this.i), this.f);
                                "".length();
                                this.f.a = cf.a(an.b("effauto " + this.i));
                                if (z.a(this.f.a)) {
                                    this.f.c = System.currentTimeMillis();
                                    dl.h().a(z.h[0], this.i);
                                    "".length();
                                    if (((97 ^ 46) & ~(6 ^ 73)) != 0) {
                                        return;
                                    }
                                }
                            } else if (z.a(this.f.a) && z.b(z.b(System.currentTimeMillis() - this.f.c, 6000L))) {
                                this.f.c = System.currentTimeMillis();
                                dl.h().a(z.h[0], this.i);
                            }
                        }
                        if (!z.b(this.f) || !z.b(this.f.a) || !z.b(this.h().c)) break block20;
                        this.j += z.h[2];
                        if (!z.c(this.j, this.h().c.length)) break block21;
                        if (!z.c(z.b(this.o, -1L))) break block22;
                        if (z.a(z.b(this.o - System.currentTimeMillis(), 0L))) {
                            cl.c.removeElement(this);
                            "".length();
                            "".length();
                            if (null != null) {
                                return;
                            }
                        } else {
                            this.j = z.h[0];
                            "".length();
                            if ((("  ".length() ^ (245 ^ 180)) & (213 ^ 167 ^ (68 ^ 117) ^ -" ".length())) != 0) {
                                return;
                            }
                        }
                        break block21;
                    }
                    if (!z.e((int)this.e)) ** GOTO lbl-1000
                    this.k = (byte)(this.k - z.h[2]);
                    if (z.a(this.k)) {
                        cl.c.removeElement(this);
                        "".length();
                        "".length();
                        if ("   ".length() <= 0) {
                            return;
                        }
                    } else lbl-1000:
                    // 2 sources

                    {
                        this.j = z.h[0];
                    }
                }
                this.f.b = System.currentTimeMillis();
                return;
            }
            "".length();
        }
        catch (Exception var1_1) {
            System.out.println("Err update effauto:" + var1_1.toString());
        }
        if (" ".length() < 0) {
            return;
        }
    }
}

