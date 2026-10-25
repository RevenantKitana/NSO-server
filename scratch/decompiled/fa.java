/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

public final class fa
implements bv {
    private static String i;
    public static int a;
    private static String j;
    private static String k;
    public static boolean b;
    public static dj c;
    private static final int[] l;
    private static fa m;
    public static int d;
    public static int e;
    public static long f;
    public static boolean g;
    public static int h;

    public void b_() {
    }

    private static void e() {
        l = new int[7];
        fa.l[0] = " ".length();
        fa.l[1] = "  ".length();
        fa.l[2] = "   ".length();
        fa.l[3] = 0x43 ^ 0x2C ^ (0x1D ^ 0x76);
        fa.l[4] = -" ".length();
        fa.l[5] = 0xFFFFC56A & 0x1003A95;
        fa.l[6] = 0x5C ^ 0x59;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    public void a(String string, String string2) {
    }

    private static String a(String string) {
        InputStream inputStream = an.c("/".concat(String.valueOf(string)));
        try {
            byte[] byArray = new byte[inputStream.available()];
            inputStream.read(byArray);
            "".length();
            string = new String(byArray, "UTF-8");
            "".length();
        }
        catch (Exception exception) {
            string = "";
        }
        if (null != null) {
            return null;
        }
        return string;
    }

    public void d() {
    }

    private static boolean a(Object object) {
        return object == null;
    }

    public static fa c() {
        if (fa.a(m)) {
            m = new fa();
        }
        System.gc();
        return m;
    }

    public final void a(int n2, Object object) {
        block15: {
            block19: {
                block18: {
                    block16: {
                        block14: {
                            if (!fa.a(n2, l[0])) break block14;
                            em.ak.a("T\u1ed1c \u0111\u1ed9 NextMap", new fr("\u0110\u1eb7t", fa.c(), l[1], null), l[0]);
                            em.ak.a.a(String.valueOf(e));
                            "".length();
                            if ("  ".length() < "  ".length()) {
                                return;
                            }
                            break block15;
                        }
                        if (!fa.a(n2, l[1])) break block16;
                        n2 = e;
                        try {
                            n2 = Integer.parseInt(em.ak.a.c());
                            "".length();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (" ".length() == ((0x54 ^ 3) & ~(0x30 ^ 0x67))) {
                            return;
                        }
                        e = n2;
                        an.a("nextmap", String.valueOf(e));
                        em.k();
                        "".length();
                        if (((51 + 129 - 46 + 20 ^ 33 + 79 - 41 + 62) & (33 + 109 - 133 + 140 ^ 21 + 37 - -42 + 38 ^ -" ".length())) <= -" ".length()) {
                            return;
                        }
                        break block15;
                    }
                    if (!fa.a(n2, l[2])) break block18;
                    em.ak.a("M\u00e0u n\u1ec1n Background", new fr("\u0110\u1eb7t", fa.c(), l[3], null), l[0]);
                    em.ak.a.a(String.valueOf(h));
                    "".length();
                    if (((0x47 ^ 0xB) & ~(0xE2 ^ 0xAE)) >= (0xB1 ^ 0xB5)) {
                        return;
                    }
                    break block15;
                }
                if (!fa.a(n2, l[3])) break block19;
                n2 = h;
                try {
                    n2 = Integer.parseInt(em.ak.a.c());
                    "".length();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if ((20 + 150 - 37 + 24 ^ 123 + 23 - 29 + 36) < " ".length()) {
                    return;
                }
                if (fa.b(n2, l[4]) && fa.c(n2, l[5])) {
                    h = n2;
                    an.a("background", String.valueOf(h));
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    ap.a("M\u00e0u n\u1ec1n t\u1eeb -1 \u0111\u1ebfn 16777216");
                }
                em.k();
                "".length();
                if (null != null) {
                    return;
                }
                break block15;
            }
            if (fa.a(n2, l[6])) {
                String string = fa.a("hd.txt");
                ap.s().a("H\u01b0\u1edbng d\u1eabn", string, l[0]);
            }
        }
    }

    public void a(eh eh2) {
    }

    private static boolean b(int n2, int n3) {
        return n2 >= n3;
    }

    public void b() {
    }

    static {
        fa.e();
    }

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }
}

