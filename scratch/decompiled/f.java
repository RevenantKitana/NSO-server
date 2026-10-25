/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class f {
    public static e a;
    public static e b;
    private static final int[] c;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ct a(short s2) {
        try {
            ct ct2;
            block12: {
                block11: {
                    ct2 = (ct)a.get(String.valueOf(s2));
                    if (f.a(ct2) && (!f.a(ct2) || !f.b(ct2.b))) break block12;
                    if (f.b(ct2)) {
                        ct2 = new ct();
                        a.put(String.valueOf(s2), ct2);
                        "".length();
                    }
                    ct2.b = cf.a(an.b("effect ".concat(String.valueOf(s2))));
                    if (!f.b(ct2.b) || !f.a(f.a(System.currentTimeMillis() / 1000L - ct2.a, 10L))) break block12;
                    ct2.a = (int)(System.currentTimeMillis() / 1000L);
                    if (f.c(s2)) {
                        ct2.b = em.a("/eff_auto/" + s2 + ".png");
                    }
                    if (!f.b(ct2.b) || !f.b(dp.g().n ? 1 : 0)) break block12;
                    dl dl2 = dl.h();
                    short s3 = s2;
                    dl dl3 = dl2;
                    eh eh2 = null;
                    try {
                        eh2 = new eh(c[0]);
                        eh2.d.writeByte(c[2]);
                        eh2.d.writeShort(s3);
                        dl3.a.a(eh2);
                        "".length();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        "".length();
                        if (-"  ".length() > 0) {
                            return null;
                        }
                        break block11;
                    }
                    finally {
                        eh2.c();
                    }
                    if (" ".length() == (0x79 ^ 0x7D)) {
                        return null;
                    }
                }
                ct2.a = (int)(System.currentTimeMillis() / 1000L);
            }
            return ct2;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static void c() {
        c = new int[5];
        f.c[0] = 0xD2 ^ 0x9F ^ (0x14 ^ 0x24);
        f.c[1] = "  ".length();
        f.c[2] = " ".length();
        f.c[3] = (208 + 197 - 289 + 133 ^ 144 + 148 - 266 + 135) & (0x93 ^ 0xBE ^ (0xB1 ^ 0xC4) ^ -" ".length());
        f.c[4] = 18 + 156 - 57 + 61 ^ 59 + 138 - 114 + 101;
    }

    public static void a() {
        a = null;
        b = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static m b(short s2) {
        m m2;
        block8: {
            m2 = (m)b.get(String.valueOf(s2));
            if (f.b(m2)) {
                m2 = new m();
                b.put(String.valueOf(s2), m2);
                "".length();
                dl dl2 = dl.h();
                short s3 = s2;
                dl dl3 = dl2;
                eh eh2 = null;
                try {
                    eh2 = new eh(c[0]);
                    eh2.d.writeByte(c[1]);
                    eh2.d.writeShort(s3);
                    dl3.a.a(eh2);
                    "".length();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    "".length();
                    if (-(0x11 ^ 0x15) > 0) {
                        return null;
                    }
                    break block8;
                }
                finally {
                    eh2.c();
                }
                if (null != null) {
                    return null;
                }
            }
        }
        return m2;
    }

    public static void b() {
        a = new e();
        b = new e();
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean c(int n2) {
        return n2 >= 0;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    static {
        f.c();
        ce.a(c[4]);
        f.b();
    }

    public static void a(short s2, byte[] byArray) {
        try {
            ct ct2 = (ct)a.get(String.valueOf(s2));
            if (f.b(ct2)) {
                ct2 = new ct();
            }
            a.put(String.valueOf(s2), ct2);
            "".length();
            if (f.a(byArray.length)) {
                ct2.b = Image.createImage((byte[])byArray, (int)c[3], (int)byArray.length);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                ct2.a = (int)(System.currentTimeMillis() / 1000L);
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((0x5D ^ 0x58) <= 0) {
            return;
        }
    }
}

