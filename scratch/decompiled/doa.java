/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.DataInputStream;
import javax.microedition.lcdui.Image;

public final class doa {
    public static doa a;
    public static doa b;
    public static doa c;
    public static doa d;
    public static doa e;
    public static doa f;
    public static doa g;
    public static doa h;
    private String w;
    public static doa i;
    public static doa j;
    Image k;
    int l;
    public static doa m;
    public static doa n;
    public static doa o;
    public static doa p;
    private static String x;
    private int[][] y;
    private static final int[] z;
    public static doa q;
    public static doa r;
    public static doa s;
    public static doa t;
    private String aa;
    private int ab;
    public static doa u;
    public static doa v;

    private void b() {
        this.k = null;
    }

    private int c() {
        return this.l;
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    static {
        doa.d();
        x = " 0123456789+-*='_?.,<>/[]{}!@#$%^&*():a\u00e1\u00e0\u1ea3\u00e3\u1ea1\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7bcd\u0111e\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7fghi\u00ed\u00ec\u1ec9\u0129\u1ecbjklmno\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3pqrstu\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1vxy\u00fd\u1ef3\u1ef7\u1ef9\u1ef5zwA\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eb0\u1eae\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eacBCD\u0110E\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6FGHI\u00cd\u00cc\u1ec8\u0128\u1ecaJKLMNO\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2PQRSTU\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0VXY\u00dd\u1ef2\u1ef6\u1ef8\u1ef4ZW";
        q = new doa(x, "/font/tahoma_7b_red.png", "/font/tahoma_7b", z[0]);
        p = new doa(x, "/font/tahoma_7b_blue.png", "/font/tahoma_7b", z[0]);
        u = new doa(x, "/font/tahoma_7b_purple.png", "/font/tahoma_7b", z[0]);
        r = new doa(x, "/font/tahoma_7b_yellow.png", "/font/tahoma_7b", z[0]);
        v = new doa(x, "/font/tahoma_7b_white.png", "/font/tahoma_7b", z[0]);
        e = new doa(x, "/font/tahoma_7b_green.png", "/font/tahoma_7b", z[0]);
        i = new doa(x, "/font/tahoma_7.png", "/font/tahoma_7", z[0]);
        c = new doa(x, "/font/tahoma_7_blue1.png", "/font/tahoma_7", z[0]);
        m = new doa(x, "/font/tahoma_7_white.png", "/font/tahoma_7", z[0]);
        j = new doa(x, "/font/tahoma_7_yellow.png", "/font/tahoma_7", z[0]);
        d = new doa(x, "/font/tahoma_7_grey.png", "/font/tahoma_7", z[0]);
        a = new doa(x, "/font/tahoma_7_red.png", "/font/tahoma_7", z[0]);
        o = new doa(x, "/font/tahoma_7_blue.png", "/font/tahoma_7", z[0]);
        g = new doa(x, "/font/tahoma_7_green.png", "/font/tahoma_7", z[0]);
        b = new doa(x, "/font/tahoma_8b.png", "/font/tahoma_8b", z[5]);
        h = new doa(" 0123456789+-", "/font/number_yellow.png", "/font/number", z[0]);
        n = new doa(" 0123456789+-", "/font/number_red.png", "/font/number", z[0]);
        s = new doa(" 0123456789+-", "/font/number_green.png", "/font/number", z[0]);
        f = new doa(" 0123456789+-", "/font/number_white.png", "/font/number", z[0]);
        t = new doa(" 0123456789+-", "/font/number_orange.png", "/font/number", z[0]);
    }

    public final String[] a(String string, int n2) {
        ad ad2 = this.b(string, n2);
        String[] stringArray = new String[ad2.size()];
        int n3 = z[0];
        while (doa.d(n3, ad2.size())) {
            stringArray[n3] = ad2.elementAt(n3).toString();
            ++n3;
            "".length();
            if (null == null) continue;
            return null;
        }
        return stringArray;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    public final ad b(String string, int n2) {
        ad ad2 = new ad();
        String string2 = "";
        int n3 = z[0];
        while (doa.d(n3, string.length())) {
            if (doa.a(string.charAt(n3), z[7])) {
                ad2.addElement(string2);
                string2 = "";
                "".length();
                if ("  ".length() > "  ".length()) {
                    return null;
                }
            } else {
                if (doa.c(this.a(string2 = string2 + string.charAt(n3)), n2)) {
                    int n4 = string2.length() - z[2];
                    while (doa.a(n4) && doa.b(string2.charAt(n4), z[8])) {
                        --n4;
                        "".length();
                        if (null == null) continue;
                        return null;
                    }
                    if (doa.c(n4)) {
                        n4 = string2.length() - z[2];
                    }
                    ad2.addElement(string2.substring(z[0], n4));
                    n3 = n3 - (string2.length() - n4) + z[2];
                    string2 = "";
                }
                if (doa.a(n3, string.length() - z[2]) && doa.b(string2.trim().equals("") ? 1 : 0)) {
                    ad2.addElement(string2);
                }
            }
            ++n3;
            "".length();
            if (" ".length() >= " ".length()) continue;
            return null;
        }
        return ad2;
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public final void a(bh bh2, String string, int n2, int n3, int n4) {
        int n5 = string.length();
        if (doa.b(n4)) {
            n4 = n2;
            "".length();
            if ((0x38 ^ 0x3D) <= 0) {
                return;
            }
        } else if (doa.a(n4, z[2])) {
            n4 = n2 - this.a(string);
            "".length();
            if (((91 + 132 - 80 + 69 ^ 35 + 142 - 127 + 95) & (0xB6 ^ 0x92 ^ (0xF ^ 0x6E) ^ -" ".length())) != 0) {
                return;
            }
        } else {
            n4 = n2 - (this.a(string) >> z[2]);
        }
        int n6 = z[0];
        while (doa.d(n6, n5)) {
            n2 = this.w.indexOf(string.charAt(n6));
            if (doa.a(n2, z[5])) {
                n2 = z[0];
            }
            if (doa.a(n2)) {
                bh2.a(this.k, this.y[n2][z[0]], this.y[n2][z[2]], this.y[n2][z[3]], this.y[n2][z[4]], z[0], n4, n3, z[6]);
            }
            n4 += this.y[n2][z[3]] + this.ab;
            ++n6;
            "".length();
            if (null == null) continue;
            return;
        }
    }

    private doa(String string, String string2, String string3, int n2) {
        try {
            this.w = string;
            this.ab = n2;
            this.aa = string2;
            string = null;
            this.a();
            try {
                DataInputStream dataInputStream = new DataInputStream(this.getClass().getResourceAsStream(string3));
                this.y = new int[dataInputStream.readShort()][];
                int n3 = z[0];
                while (doa.d(n3, this.y.length)) {
                    int n4;
                    this.y[n3] = new int[z[1]];
                    this.y[n3][doa.z[0]] = dataInputStream.readShort();
                    this.y[n3][doa.z[2]] = dataInputStream.readShort();
                    this.y[n3][doa.z[3]] = dataInputStream.readShort();
                    this.y[n3][doa.z[4]] = dataInputStream.readShort();
                    this.l = n4 = this.y[n3][z[4]];
                    ++n3;
                    "".length();
                    if ((0x10 ^ 0x14) >= 0) continue;
                    throw null;
                }
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ((0xBE ^ 0xBA) < (0x20 ^ 0x24)) {
                throw null;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-"   ".length() >= 0) {
            throw null;
        }
    }

    public final void a() {
        this.k = em.a(this.aa);
    }

    private static void d() {
        z = new int[9];
        doa.z[0] = (0x15 ^ 0x43) & ~(0x1D ^ 0x4B);
        doa.z[1] = 0x4C ^ 0x63 ^ (0x5C ^ 0x77);
        doa.z[2] = " ".length();
        doa.z[3] = "  ".length();
        doa.z[4] = "   ".length();
        doa.z[5] = -" ".length();
        doa.z[6] = 0x2F ^ 0x3B;
        doa.z[7] = 0x63 ^ 0x69;
        doa.z[8] = 0x20 ^ 0;
    }

    public final int a(String string) {
        int n2 = z[0];
        int n3 = z[0];
        while (doa.d(n3, string.length())) {
            int n4 = this.w.indexOf(string.charAt(n3));
            if (doa.a(n4, z[5])) {
                n4 = z[0];
            }
            n2 += this.y[n4][z[3]] + this.ab;
            ++n3;
            "".length();
            if (null == null) continue;
            return (13 + 30 - -43 + 73 ^ 126 + 53 - 173 + 140) & (75 + 93 - 41 + 18 ^ 34 + 43 - 36 + 115 ^ -" ".length());
        }
        return n2;
    }

    public final void a(bh bh2, String string, int n2, int n3, int n4, doa doa2) {
        int n5 = string.length();
        if (doa.b(n4)) {
            n4 = n2;
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
        } else if (doa.a(n4, z[2])) {
            n4 = n2 - this.a(string);
            "".length();
            if (((0x32 ^ 0x7E) & ~(0xE3 ^ 0xAF)) > 0) {
                return;
            }
        } else {
            n4 = n2 - (this.a(string) >> z[2]);
        }
        int n6 = z[0];
        while (doa.d(n6, n5)) {
            n2 = this.w.indexOf(string.charAt(n6));
            if (doa.a(n2, z[5])) {
                n2 = z[0];
            }
            if (doa.a(n2)) {
                bh2.a(doa2.k, this.y[n2][z[0]], this.y[n2][z[2]], this.y[n2][z[3]], this.y[n2][z[4]], z[0], n4 + z[2], n3, z[6]);
                bh2.a(doa2.k, this.y[n2][z[0]], this.y[n2][z[2]], this.y[n2][z[3]], this.y[n2][z[4]], z[0], n4, n3 + z[2], z[6]);
                bh2.a(this.k, this.y[n2][z[0]], this.y[n2][z[2]], this.y[n2][z[3]], this.y[n2][z[4]], z[0], n4, n3, z[6]);
            }
            n4 += this.y[n2][z[3]] + this.ab;
            ++n6;
            "".length();
            if ((0x36 ^ 0x33) > 0) continue;
            return;
        }
    }

    private static boolean c(int n2) {
        return n2 < 0;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }
}

