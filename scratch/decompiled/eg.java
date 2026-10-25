/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.TextBox
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;

public final class eg
implements bv {
    public boolean a;
    static int b;
    public boolean c;
    public int d;
    private static int w;
    int e;
    private int x;
    public String f = "";
    String g = "";
    private static int y;
    String h = "";
    public int i;
    public int j;
    private static final int[] z;
    int k;
    public String l = "";
    int m = z[0];
    public fr n;
    int o;
    String p = "";
    public int q;
    private static String[] aa;
    public static boolean r;
    private int ab;
    public int s;
    int t;
    int u;
    private static String[] ac;
    private int ad;
    private static final int[] ae;
    public String v = "";

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    private void c(int n2) {
        this.o = n2;
    }

    public final String c() {
        return this.h;
    }

    public void a(String string, String string2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void h() {
        if (eg.c(this.o, z[3])) {
            this.g = this.p;
            "".length();
            if (null != null) {
                return;
            }
        } else {
            this.g = this.h;
        }
        if (eg.e(this.u) && eg.f(doa.b.a(this.g) + this.u, this.d - z[1] - z[16])) {
            this.u = this.d - z[14] - doa.b.a(this.g);
        }
        if (eg.h(this.u + doa.b.a(this.g.substring(z[0], this.m)))) {
            this.u = -doa.b.a(this.g.substring(z[0], this.m));
            this.u += z[17];
            "".length();
            if (null != null) {
                return;
            }
        } else if (eg.b(this.u + doa.b.a(this.g.substring(z[0], this.m)), this.d - z[18])) {
            this.u = this.d - z[14] - doa.b.a(this.g.substring(z[0], this.m)) - z[19];
        }
        if (eg.d(this.u)) {
            this.u = z[0];
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 <= n3;
    }

    public static boolean a(char c2) {
        boolean bl2;
        if (eg.b(c2, z[6]) && !eg.a(c2, z[7]) || eg.b(c2, z[8]) && !eg.a(c2, z[9]) || eg.b(c2, z[10]) && eg.d(c2, z[11])) {
            bl2 = z[5];
            "".length();
            if (" ".length() == -" ".length()) {
                return ((40 + 156 - 147 + 115 ^ 48 + 181 - 223 + 184) & (0xC ^ 0x68 ^ (0xB8 ^ 0xC6) ^ -" ".length())) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    private void i() {
        if (eg.c(this.o, z[3])) {
            this.p = "";
            int n2 = z[0];
            while (eg.f(n2, this.h.length())) {
                this.p = this.p + "*";
                ++n2;
                "".length();
                if (-" ".length() == -" ".length()) continue;
                return;
            }
            if (eg.d(this.t) && eg.d(this.m)) {
                this.p = this.p.substring(z[0], this.m - z[5]) + this.h.charAt(this.m - z[5]) + this.p.substring(this.m, this.p.length());
            }
        }
    }

    public final void e() {
        if (eg.d(this.m) && eg.d(this.h.length())) {
            this.h = this.h.substring(z[0], this.m - z[5]) + this.h.substring(this.m, this.h.length());
            this.m -= z[5];
            this.h();
            this.i();
        }
    }

    public final void a(int n2) {
        this.i = n2;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean e(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    public final void a(int n2, Object object) {
        switch (n2) {
            case 1000: {
                this.e();
            }
        }
    }

    private static boolean e(int n2) {
        return n2 < 0;
    }

    public void a(eh eh2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void d() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final void f() {
        this.k += z[5];
        if (eg.d(this.t)) {
            this.t -= z[5];
            if (eg.g(this.t)) {
                this.x = z[0];
                if (eg.c(this.ab, z[5]) && eg.e(this.ad, w)) {
                    this.ab = z[0];
                }
                this.ad = z[13];
                this.i();
            }
        }
        if (eg.d(this.e)) {
            this.e -= z[5];
        }
        if (eg.f(em.q ? 1 : 0)) {
            if (eg.a(em.at, az.a)) {
                int n2;
                if (eg.b(az.a)) {
                    az.a = new az();
                }
                if (eg.g(em.c(this.j, this.s - (n2 = az.a.b.b), this.d, this.q) ? 1 : 0)) {
                    this.a = z[0];
                    return;
                }
                this.g();
                return;
            }
            if (eg.f(em.c(this.j, this.s, this.d, this.q) ? 1 : 0)) {
                this.g();
                return;
            }
            this.a = z[0];
        }
    }

    private static boolean f(int n2) {
        return n2 != 0;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public void b() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void j() {
        z = new int[39];
        eg.z[0] = (112 + 8 - 92 + 100 ^ 10 + 83 - -5 + 46) & (0x3E ^ 0x59 ^ (0xB3 ^ 0xC4) ^ -" ".length());
        eg.z[1] = 0x76 ^ 0x72;
        eg.z[2] = "   ".length();
        eg.z[3] = "  ".length();
        eg.z[4] = 0xFFFFBC3B & 0x143C4;
        eg.z[5] = " ".length();
        eg.z[6] = 64 + 104 - 39 + 48 ^ 55 + 125 - 134 + 83;
        eg.z[7] = 0xAA ^ 0x93;
        eg.z[8] = 175 + 9 - 38 + 101 ^ 40 + 144 - 13 + 11;
        eg.z[9] = 0x1F ^ 0x69 ^ (0xF ^ 0x23);
        eg.z[10] = 0x1C ^ 0x5E ^ (0x6F ^ 0x4C);
        eg.z[11] = 6 ^ 0 ^ (0xDA ^ 0xA6);
        eg.z[12] = -(0xFFFFCE75 & 0x358E) & (0xFFFFCFFF & 0x35F7);
        eg.z[13] = -(-(0xFFFFD93F & 0x76D5) & (0xFFFFD7F6 & 0x7FDD));
        eg.z[14] = 0x79 ^ 0x73;
        eg.z[15] = -(0xA9 ^ 0xA1) & (0xFFFFCFEF & 0x33FF);
        eg.z[16] = 0xDF ^ 0xA8 ^ (0x70 ^ 0xA);
        eg.z[17] = 0x96 ^ 0xBE;
        eg.z[18] = 14 + 2 - -74 + 68 ^ 83 + 38 - -23 + 2;
        eg.z[19] = 0x4C ^ 0x44;
        eg.z[20] = -(117 + 98 - 149 + 109 ^ 34 + 136 - 38 + 35);
        eg.z[21] = 64 + 90 - 90 + 140;
        eg.z[22] = 0x7A ^ 0x4B ^ (0x2F ^ 0x33);
        eg.z[23] = 137 + 126 - 224 + 215 ^ 152 + 103 - 238 + 144;
        eg.z[24] = 0x94 ^ 0xB4;
        eg.z[25] = 84 + 77 - 111 + 86 ^ 98 + 137 - 91 + 18;
        eg.z[26] = 63 + 18 - -34 + 29 ^ 167 + 98 - 159 + 64;
        eg.z[27] = 0xE ^ 7 ^ (0x27 ^ 0xD);
        eg.z[28] = 0x85 ^ 0xBE;
        eg.z[29] = 0x2F ^ 0x21;
        eg.z[30] = 0x32 ^ 0x3D;
        eg.z[31] = 0x5B ^ 0x71 ^ (0x53 ^ 0x6A);
        eg.z[32] = 0xE7 ^ 0xB7 ^ (0x33 ^ 0x66);
        eg.z[33] = -(0xFFFFFE62 & 0x519F) & (0xFFFFFFEF & 0xAAFABB);
        eg.z[34] = 0x12 ^ 0x15;
        eg.z[35] = 0x5F ^ 0x40 ^ (0x95 ^ 0x98);
        eg.z[36] = 0x88 ^ 0x83;
        eg.z[37] = 0xD ^ 4;
        eg.z[38] = 0x3C ^ 8 ^ (0x99 ^ 0xAB);
    }

    public void b_() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void b(bh bh2) {
        boolean bl2 = this.a;
        if (eg.c(this.o, z[3])) {
            this.g = this.p;
            "".length();
            if ((0x57 ^ 0x53) != (0xAC ^ 0xA8)) {
                return;
            }
        } else {
            this.g = this.h;
        }
        if (eg.f(this.g.equals("") ? 1 : 0)) {
            this.g = this.v;
        }
        br.a(bh2, bl2, this.j, this.s, this.d, z[1] + this.u + this.j, this.s + (this.q - doa.b.l) / z[3], this.g);
        bh2.a(z[0]);
        if (eg.f(this.a ? 1 : 0) && eg.g(this.t) && (!eg.h(this.e) || eg.g(this.k / z[32] % z[3]))) {
            bh2.a(z[33]);
            bh2.e(z[32] + this.u + this.j + doa.b.a(this.g.substring(z[0], this.m)) - z[5], this.s + (this.q - b) / z[3] + z[5], z[5], b);
        }
    }

    public final boolean b(int n2) {
        if (eg.e(n2, z[19]) && eg.e(n2, z[20]) && eg.e(n2, z[21])) {
            if (eg.b(n2, z[8]) && eg.d(n2, z[11]) && eg.g(r ? 1 : 0)) {
                r = z[5];
                an.a("qwerty", z[5]);
            }
            if (eg.f(r ? 1 : 0)) {
                if (eg.c(n2, z[22])) {
                    if (eg.c(n2, this.ad) && eg.f(this.t, ae[y])) {
                        this.g = this.h = this.h.substring(z[0], this.m - z[5]) + z[23];
                        this.i();
                        this.h();
                        this.ad = z[13];
                        return z[0];
                    }
                    this.ad = z[22];
                }
                if (eg.b(n2, z[24])) {
                    this.i(n2);
                    return z[0];
                }
            }
            if (eg.c(n2, w)) {
                this.ab += z[5];
                if (eg.a(this.ab, z[2])) {
                    this.ab = z[0];
                }
                this.t = z[5];
                this.ad = n2;
                return z[0];
            }
            if (eg.c(n2, z[25])) {
                n2 = z[26];
            }
            if (eg.c(n2, z[27])) {
                n2 = z[28];
            }
            if (eg.b(n2, z[6]) && eg.d(n2, z[28])) {
                if (eg.f(this.o) && eg.e(this.o, z[3]) && eg.e(this.o, z[2])) {
                    if (eg.c(this.o, z[5])) {
                        this.i(n2);
                        this.t = z[5];
                        "".length();
                        if ((0xF3 ^ 0x91 ^ (0x42 ^ 0x24)) != (43 + 147 - -2 + 3 ^ 82 + 43 - 80 + 154)) {
                            return ((0x48 ^ 0x47 ^ (0x4D ^ 0x5F)) & (0xAF ^ 0xC0 ^ (0xE8 ^ 0x9A) ^ -" ".length())) != 0;
                        }
                    }
                } else {
                    String[] stringArray;
                    if (eg.e(this.o, z[3]) && eg.e(this.o, z[2])) {
                        stringArray = ac;
                        "".length();
                        if ((3 ^ 0x29 ^ (0x8D ^ 0xA3)) <= -" ".length()) {
                            return ((68 + 119 - 177 + 127 ^ 67 + 104 - 46 + 60) & (0x43 ^ 0x7D ^ (0x50 ^ 0x5E) ^ -" ".length())) != 0;
                        }
                    } else {
                        stringArray = aa;
                    }
                    if (eg.c(n2, this.ad)) {
                        this.x = (this.x + z[5]) % stringArray[n2 - z[6]].length();
                        char c2 = stringArray[n2 - z[6]].charAt(this.x);
                        if (eg.g(this.ab)) {
                            c2 = Character.toLowerCase(c2);
                            "".length();
                            if (((0xB4 ^ 0xB9 ^ (0x76 ^ 0x5D)) & (0x8B ^ 0xC2 ^ (0x2F ^ 0x40) ^ -" ".length())) != ((0xEF ^ 0xB9 ^ (0xA9 ^ 0xA5)) & (3 ^ 0x7E ^ (0x46 ^ 0x61) ^ -" ".length()))) {
                                return ((0xF6 ^ 0x96 ^ (0x55 ^ 0x7C)) & (76 + 116 - 105 + 50 ^ 91 + 33 - -67 + 1 ^ -" ".length())) != 0;
                            }
                        } else if (eg.c(this.ab, z[5])) {
                            c2 = Character.toUpperCase(c2);
                            "".length();
                            if ("   ".length() < 0) {
                                return ((0x45 ^ 0x55) & ~(0xA5 ^ 0xB5)) != 0;
                            }
                        } else if (eg.c(this.ab, z[3])) {
                            c2 = Character.toUpperCase(c2);
                            "".length();
                            if ((124 + 136 - 101 + 25 ^ 99 + 117 - 109 + 81) <= -" ".length()) {
                                return ((0xA9 ^ 0xA2 ^ (0x1B ^ 0x33)) & (0x69 ^ 0x1A ^ (0xC8 ^ 0x98) ^ -" ".length())) != 0;
                            }
                        } else {
                            c2 = stringArray[n2 - z[6]].charAt(stringArray[n2 - z[6]].length() - z[5]);
                        }
                        String string = this.h.substring(z[0], this.m - z[5]) + c2;
                        if (eg.f(this.m, this.h.length())) {
                            string = string + this.h.substring(this.m, this.h.length());
                        }
                        this.h = string;
                        this.t = ae[y];
                        this.i();
                        "".length();
                        if (-" ".length() > (0x96 ^ 0x92)) {
                            return ((0xAB ^ 0x90) & ~(0xB0 ^ 0x8B)) != 0;
                        }
                    } else if (eg.f(this.h.length(), this.i)) {
                        if (eg.c(this.ab, z[5]) && eg.e(this.ad, z[13])) {
                            this.ab = z[0];
                        }
                        this.x = z[0];
                        char c3 = stringArray[n2 - z[6]].charAt(this.x);
                        if (eg.g(this.ab)) {
                            c3 = Character.toLowerCase(c3);
                            "".length();
                            if ((0xBD ^ 0xB9) == " ".length()) {
                                return ((0x45 ^ 2) & ~(0x1A ^ 0x5D)) != 0;
                            }
                        } else if (eg.c(this.ab, z[5])) {
                            c3 = Character.toUpperCase(c3);
                            "".length();
                            if (((0x38 ^ 0x7A) & ~(0xC3 ^ 0x81)) < 0) {
                                return ((0xA ^ 0x18) & ~(0xAC ^ 0xBE)) != 0;
                            }
                        } else if (eg.c(this.ab, z[3])) {
                            c3 = Character.toUpperCase(c3);
                            "".length();
                            if (null != null) {
                                return ((142 + 68 - 144 + 80 ^ 92 + 3 - -23 + 12) & (128 + 90 - 160 + 155 ^ 21 + 150 - 59 + 85 ^ -" ".length())) != 0;
                            }
                        } else {
                            c3 = stringArray[n2 - z[6]].charAt(stringArray[n2 - z[6]].length() - z[5]);
                        }
                        String string = this.h.substring(z[0], this.m) + c3;
                        if (eg.f(this.m, this.h.length())) {
                            string = string + this.h.substring(this.m, this.h.length());
                        }
                        this.h = string;
                        this.t = ae[y];
                        this.m += z[5];
                        this.i();
                        this.h();
                    }
                    this.ad = n2;
                    "".length();
                    if (((0x33 ^ 0x15) & ~(0x64 ^ 0x42) & ~((0x7C ^ 0x39) & ~(0xDE ^ 0x9B))) < 0) {
                        return ((0x24 ^ 0x32) & ~(0x50 ^ 0x46)) != 0;
                    }
                }
            } else {
                this.x = z[0];
                this.ad = z[13];
                if (eg.c(n2, z[29])) {
                    if (eg.d(this.m)) {
                        this.m -= z[5];
                        this.h();
                        this.e = z[14];
                        return z[0];
                    }
                } else if (eg.c(n2, z[30])) {
                    if (eg.f(this.m, this.h.length())) {
                        this.m += z[5];
                        this.h();
                        this.e = z[14];
                        return z[0];
                    }
                } else {
                    if (eg.c(n2, z[31])) {
                        this.e();
                        return z[0];
                    }
                    this.ad = n2;
                }
            }
            return z[5];
        }
        this.e();
        return z[5];
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    public final void a(bh bh2) {
        bh2.a(z[0], z[0], em.aj, em.aq);
        boolean bl2 = this.a;
        if (eg.c(this.o, z[3])) {
            this.g = this.p;
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        } else {
            this.g = this.h;
        }
        if (eg.f(this.g.equals("") ? 1 : 0)) {
            this.g = this.v;
        }
        br.a(bh2, bl2, this.j, this.s, this.d, this.q, z[1] + this.u + this.j, this.s + (this.q - doa.b.l) / z[3], this.g);
        bh2.a(this.j + z[2], this.s + z[5], this.d - z[1], this.q - z[1]);
        bh2.a(z[0]);
        if (eg.f(this.a ? 1 : 0) && eg.g(this.t) && (!eg.h(this.e) || eg.g(this.k / z[32] % z[3]))) {
            bh2.a(z[33]);
            bh2.e(z[32] + this.u + this.j + doa.b.a(this.g.substring(z[0], this.m)) - z[5], this.s + (this.q - b) / z[3] + z[5], z[5], b);
        }
    }

    public final void g() {
        TextBox textBox = new TextBox(this.l, "", this.i, z[0]);
        textBox.addCommand(new Command(ag.v, z[1], z[0]));
        textBox.addCommand(new Command("Cancel", z[2], z[0]));
        textBox.setCommandListener((CommandListener)new q(this, textBox));
        try {
            if (eg.c(this.o, z[3])) {
                textBox.setConstraints(z[4]);
                "".length();
                if (null != null) {
                    return;
                }
            } else if (eg.c(this.o, z[5])) {
                textBox.setConstraints(z[3]);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                textBox.setConstraints(z[0]);
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ((80 + 64 - 45 + 28 ^ (0x7B ^ 0)) < "   ".length()) {
            return;
        }
        textBox.setString(this.h);
        textBox.setMaxSize(this.i);
        "".length();
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)textBox);
    }

    static {
        eg.j();
        y = z[3];
        int[] nArray = new int[z[34]];
        nArray[eg.z[0]] = z[35];
        nArray[eg.z[5]] = z[29];
        nArray[eg.z[3]] = z[36];
        nArray[eg.z[2]] = z[37];
        nArray[eg.z[1]] = z[38];
        nArray[eg.z[32]] = z[1];
        nArray[eg.z[38]] = z[3];
        ae = nArray;
        b = z[0];
        String[] stringArray = new String[z[18]];
        stringArray[eg.z[0]] = " 0";
        stringArray[eg.z[5]] = ".,@?!_1\"/$-():*+<=>;%&~#%^&*{}[];'/1";
        stringArray[eg.z[3]] = "abc2\u00e1\u00e0\u1ea3\u00e3\u1ea1\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb72";
        stringArray[eg.z[2]] = "def3\u0111\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec73";
        stringArray[eg.z[1]] = "ghi4\u00ed\u00ec\u1ec9\u0129\u1ecb4";
        stringArray[eg.z[32]] = "jkl5";
        stringArray[eg.z[38]] = "mno6\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee36";
        stringArray[eg.z[34]] = "pqrs7";
        stringArray[eg.z[19]] = "tuv8\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef18";
        stringArray[eg.z[37]] = "wxyz9\u00fd\u1ef3\u1ef7\u1ef9\u1ef59";
        stringArray[eg.z[14]] = "*";
        stringArray[eg.z[36]] = "#";
        ac = stringArray;
        String[] stringArray2 = new String[z[18]];
        stringArray2[eg.z[0]] = "0";
        stringArray2[eg.z[5]] = "1";
        stringArray2[eg.z[3]] = "abc2";
        stringArray2[eg.z[2]] = "def3";
        stringArray2[eg.z[1]] = "ghi4";
        stringArray2[eg.z[32]] = "jkl5";
        stringArray2[eg.z[38]] = "mno6";
        stringArray2[eg.z[34]] = "pqrs7";
        stringArray2[eg.z[19]] = "tuv8";
        stringArray2[eg.z[37]] = "wxyz9";
        stringArray2[eg.z[14]] = "0";
        stringArray2[eg.z[36]] = "0";
        aa = stringArray2;
        w = z[36];
    }

    private static boolean f(int n2, int n3) {
        return n2 < n3;
    }

    public eg() {
        this.k = z[0];
        this.i = z[12];
        this.u = z[0];
        this.ad = z[13];
        this.t = z[0];
        this.x = z[0];
        this.e = z[14];
        this.o = z[0];
        this.ab = z[0];
        b = doa.b.l + z[5];
        this.n = new fr(ag.qo, this, z[15], null);
        if (eg.c(an.d("qwerty"), z[5])) {
            r = z[5];
        }
    }

    public final void a(String string) {
        if (eg.a(string)) {
            this.ad = z[13];
            this.t = z[0];
            this.x = z[0];
            this.h = string;
            this.g = string;
            this.i();
            this.m = string.length();
            this.h();
        }
    }

    private static boolean h(int n2) {
        return n2 <= 0;
    }

    private void i(int n2) {
        if ((eg.e(this.o, z[3]) && !eg.c(this.o, z[2]) || eg.b(n2, z[6]) && !eg.a(n2, z[7]) || eg.b(n2, z[8]) && !eg.a(n2, z[9]) || eg.b(n2, z[10]) && eg.d(n2, z[11])) && eg.f(this.h.length(), this.i)) {
            String string = this.h.substring(z[0], this.m) + (char)n2;
            if (eg.f(this.m, this.h.length())) {
                string = string + this.h.substring(this.m, this.h.length());
            }
            this.h = string;
            this.m += z[5];
            this.i();
            this.h();
        }
    }

    private static boolean b(Object object) {
        return object == null;
    }
}

