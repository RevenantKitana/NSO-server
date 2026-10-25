/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public final class fo {
    private static short[] a;
    private static Random b;
    private static int[] c;
    private static final int[] d;
    private static short[] e;

    private static boolean g(int n2) {
        return n2 < 0;
    }

    private static boolean h(int n2) {
        return n2 >= 0;
    }

    public static String a(long l2, int n2) {
        String string;
        String string2 = "";
        long l3 = (l2 + (long)n2 - System.currentTimeMillis()) / 1000L;
        if (fo.i(fo.a(l3, 0L))) {
            return "";
        }
        long l4 = 0L;
        long l5 = 0L;
        long l6 = l3 / 60L;
        long l7 = l3;
        if (fo.h(fo.a(l3, 86400L))) {
            l4 = l3 / 86400L;
            l5 = l3 % 86400L / 3600L;
            "".length();
            if ("  ".length() <= " ".length()) {
                return null;
            }
        } else if (fo.h(fo.a(l3, 3600L))) {
            l5 = l3 / 3600L;
            l6 = l3 % 3600L / 60L;
            "".length();
            if (" ".length() == "  ".length()) {
                return null;
            }
        } else if (fo.h(fo.a(l3, 60L))) {
            l7 = l3 % 60L;
            "".length();
            if ((0x1C ^ 0x19) <= 0) {
                return null;
            }
        } else {
            l7 = l3;
        }
        if (fo.k(fo.a(l4, 0L))) {
            if (fo.h(fo.a(l4, 10L))) {
                String string3;
                if (fo.g(fo.a(l5, 1L))) {
                    string3 = l4 + "d";
                    "".length();
                    if ("   ".length() < 0) {
                        return null;
                    }
                } else if (fo.g(fo.a(l5, 10L))) {
                    string3 = l4 + "d0" + l5 + "h";
                    "".length();
                    if (((0xE2 ^ 0x98 ^ (2 ^ 0x41)) & (0x35 ^ 0x54 ^ (0x31 ^ 0x69) ^ -" ".length())) != 0) {
                        return null;
                    }
                } else {
                    string3 = l4 + "d" + l5 + "h";
                }
                string = string3;
                "".length();
                if ("  ".length() == 0) {
                    return null;
                }
            } else {
                String string4;
                if (fo.h(fo.a(l4, 10L))) {
                    return string2;
                }
                if (fo.g(fo.a(l5, 1L))) {
                    string4 = l4 + "d";
                    "".length();
                    if (" ".length() == 0) {
                        return null;
                    }
                } else if (fo.g(fo.a(l5, 10L))) {
                    string4 = l4 + "d0" + l5 + "h";
                    "".length();
                    if (" ".length() >= (0x9E ^ 0x9A)) {
                        return null;
                    }
                } else {
                    string4 = l4 + "d" + l5 + "h";
                }
                string = string4;
                "".length();
                if ((0x51 ^ 0x3B ^ (0xEC ^ 0x82)) == 0) {
                    return null;
                }
            }
        } else if (fo.k(fo.a(l5, 0L))) {
            if (fo.h(fo.a(l5, 10L))) {
                String string5;
                if (fo.g(fo.a(l6, 1L))) {
                    string5 = l5 + "h";
                    "".length();
                    if (null != null) {
                        return null;
                    }
                } else if (fo.g(fo.a(l6, 10L))) {
                    string5 = l5 + "h0" + l6 + "m";
                    "".length();
                    if (-" ".length() < -" ".length()) {
                        return null;
                    }
                } else {
                    string5 = l5 + "h" + l6 + "m";
                }
                string = string5;
                "".length();
                if ("  ".length() == 0) {
                    return null;
                }
            } else {
                String string6;
                if (fo.h(fo.a(l5, 10L))) {
                    return string2;
                }
                if (fo.g(fo.a(l6, 1L))) {
                    string6 = l5 + "h";
                    "".length();
                    if (-" ".length() >= "  ".length()) {
                        return null;
                    }
                } else if (fo.g(fo.a(l6, 10L))) {
                    string6 = l5 + "h0" + l6 + "m";
                    "".length();
                    if (((53 + 12 - -26 + 81 ^ 103 + 54 - 89 + 82) & (0x7D ^ 0x60 ^ (0x5A ^ 0x7D) ^ -" ".length())) < -" ".length()) {
                        return null;
                    }
                } else {
                    string6 = l5 + "h" + l6 + "m";
                }
                string = string6;
                "".length();
                if ((30 + 79 - 11 + 57 ^ 125 + 98 - 123 + 59) <= 0) {
                    return null;
                }
            }
        } else {
            String string7;
            if (fo.k(fo.a(l6, 0L))) {
                if (fo.h(fo.a(l6, 10L))) {
                    if (fo.h(fo.a(l7, 10L))) {
                        string7 = l6 + "m" + l7 + "s";
                        "".length();
                        if (((55 + 33 - -58 + 5 ^ 151 + 86 - 119 + 35) & (90 + 176 - 194 + 134 ^ 25 + 103 - 100 + 164 ^ -" ".length())) > "  ".length()) {
                            return null;
                        }
                    } else {
                        if (fo.h(fo.a(l7, 10L))) {
                            return string2;
                        }
                        string7 = l6 + "m0" + l7 + "s";
                        "".length();
                        if ("  ".length() != "  ".length()) {
                            return null;
                        }
                    }
                } else {
                    if (fo.h(fo.a(l6, 10L))) {
                        return string2;
                    }
                    if (fo.h(fo.a(l7, 10L))) {
                        string7 = l6 + "m" + l7 + "s";
                        "".length();
                        if (((0x58 ^ 0x73) & ~(0x58 ^ 0x73)) == " ".length()) {
                            return null;
                        }
                    } else {
                        if (fo.h(fo.a(l7, 10L))) {
                            return string2;
                        }
                        string7 = l6 + "m0" + l7 + "s";
                        "".length();
                        if ("   ".length() == 0) {
                            return null;
                        }
                    }
                }
            } else {
                String string8;
                if (fo.g(fo.a(l7, 10L))) {
                    string8 = "0" + l7 + "s";
                    "".length();
                    if ((0x29 ^ 0x2D) <= -" ".length()) {
                        return null;
                    }
                } else {
                    string8 = l7 + "s";
                }
                string7 = string8;
            }
            string = string7;
        }
        return string;
    }

    static {
        fo.e();
        ce.a(d[3]);
        fo.b();
    }

    public static int a(int n2, int n3) {
        return n2 + b.nextInt(n3 - n2);
    }

    public static int a(int n2, int n3, int n4, int n5) {
        if (fo.i(n2 = (n2 - n4) * (n2 - n4) + (n3 - n5) * (n3 - n5))) {
            return d[1];
        }
        n3 = (n2 + d[2]) / d[4];
        while (!fo.c(Math.abs((n4 = n3) - (n3 = n3 / d[4] + n2 / (n3 << d[2]))), d[2])) {
        }
        return n3;
    }

    public static void a(bh bh2) {
        bh2.a(-bh2.a(), -bh2.b());
        bh2.a(d[1], d[1], em.aj, em.aq);
    }

    public static DataInputStream a(String string) {
        DataInputStream dataInputStream = null;
        InputStream inputStream = "".getClass().getResourceAsStream(string);
        if (fo.a(inputStream)) {
            dataInputStream = new DataInputStream(inputStream);
        }
        return dataInputStream;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public static Calendar a() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
    }

    public static String[] a(String string, String string2, int n2) {
        String[] stringArray;
        int n3 = string.indexOf(string2);
        if (fo.h(n3)) {
            stringArray = fo.a(string.substring(n3 + string2.length()), string2, n2 + d[2]);
            "".length();
            if (-" ".length() > " ".length()) {
                return null;
            }
        } else {
            stringArray = new String[n2 + d[2]];
            n3 = string.length();
        }
        stringArray[n2] = string.substring(d[1], n3);
        return stringArray;
    }

    public static int a(int n2) {
        if (fo.i(n2)) {
            return d[1];
        }
        int n3 = (n2 + d[2]) / d[4];
        do {
            int n4;
            if (fo.c(Math.abs(n3 - (n4 = n3 / d[4] + n2 / (n3 << d[2]))), d[2])) {
                return n4;
            }
            n3 = n4;
            "".length();
        } while ((0x63 ^ 0x66) != 0);
        return (0x3F ^ 0x33) & ~(0x3F ^ 0x33);
    }

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    public static void b() {
        short[] sArray = new short[d[0]];
        sArray[fo.d[1]] = d[1];
        sArray[fo.d[2]] = d[3];
        sArray[fo.d[4]] = d[5];
        sArray[fo.d[6]] = d[7];
        sArray[fo.d[8]] = d[9];
        sArray[fo.d[10]] = d[11];
        sArray[fo.d[12]] = d[13];
        sArray[fo.d[14]] = d[15];
        sArray[fo.d[16]] = d[17];
        sArray[fo.d[18]] = d[19];
        sArray[fo.d[20]] = d[21];
        sArray[fo.d[22]] = d[23];
        sArray[fo.d[24]] = d[25];
        sArray[fo.d[26]] = d[27];
        sArray[fo.d[28]] = d[29];
        sArray[fo.d[30]] = d[31];
        sArray[fo.d[32]] = d[33];
        sArray[fo.d[34]] = d[35];
        sArray[fo.d[3]] = d[36];
        sArray[fo.d[37]] = d[38];
        sArray[fo.d[39]] = d[40];
        sArray[fo.d[41]] = d[42];
        sArray[fo.d[43]] = d[44];
        sArray[fo.d[45]] = d[46];
        sArray[fo.d[47]] = d[48];
        sArray[fo.d[49]] = d[50];
        sArray[fo.d[51]] = d[52];
        sArray[fo.d[53]] = d[54];
        sArray[fo.d[55]] = d[56];
        sArray[fo.d[57]] = d[58];
        sArray[fo.d[59]] = d[60];
        sArray[fo.d[61]] = d[62];
        sArray[fo.d[63]] = d[64];
        sArray[fo.d[65]] = d[66];
        sArray[fo.d[67]] = d[68];
        sArray[fo.d[69]] = d[70];
        sArray[fo.d[5]] = d[71];
        sArray[fo.d[72]] = d[73];
        sArray[fo.d[74]] = d[75];
        sArray[fo.d[76]] = d[77];
        sArray[fo.d[78]] = d[79];
        sArray[fo.d[80]] = d[81];
        sArray[fo.d[82]] = d[83];
        sArray[fo.d[84]] = d[85];
        sArray[fo.d[86]] = d[87];
        sArray[fo.d[88]] = d[89];
        sArray[fo.d[90]] = d[91];
        sArray[fo.d[92]] = d[93];
        sArray[fo.d[94]] = d[95];
        sArray[fo.d[96]] = d[97];
        sArray[fo.d[98]] = d[99];
        sArray[fo.d[100]] = d[101];
        sArray[fo.d[102]] = d[103];
        sArray[fo.d[104]] = d[105];
        sArray[fo.d[7]] = d[106];
        sArray[fo.d[107]] = d[108];
        sArray[fo.d[109]] = d[110];
        sArray[fo.d[111]] = d[112];
        sArray[fo.d[113]] = d[114];
        sArray[fo.d[115]] = d[116];
        sArray[fo.d[117]] = d[118];
        sArray[fo.d[119]] = d[120];
        sArray[fo.d[121]] = d[122];
        sArray[fo.d[123]] = d[124];
        sArray[fo.d[125]] = d[126];
        sArray[fo.d[127]] = d[128];
        sArray[fo.d[129]] = d[130];
        sArray[fo.d[131]] = d[132];
        sArray[fo.d[133]] = d[134];
        sArray[fo.d[135]] = d[136];
        sArray[fo.d[137]] = d[138];
        sArray[fo.d[9]] = d[139];
        sArray[fo.d[140]] = d[141];
        sArray[fo.d[142]] = d[143];
        sArray[fo.d[144]] = d[145];
        sArray[fo.d[146]] = d[147];
        sArray[fo.d[148]] = d[149];
        sArray[fo.d[150]] = d[151];
        sArray[fo.d[152]] = d[153];
        sArray[fo.d[154]] = d[155];
        sArray[fo.d[156]] = d[157];
        sArray[fo.d[158]] = d[159];
        sArray[fo.d[160]] = d[161];
        sArray[fo.d[162]] = d[163];
        sArray[fo.d[164]] = d[165];
        sArray[fo.d[166]] = d[167];
        sArray[fo.d[168]] = d[169];
        sArray[fo.d[170]] = d[171];
        sArray[fo.d[172]] = d[171];
        sArray[fo.d[11]] = d[173];
        sArray[fo.d[174]] = d[173];
        a = sArray;
        b = new Random();
    }

    public static void c() {
        a = null;
        e = null;
        c = null;
        b = null;
    }

    private static boolean e(int n2, int n3) {
        return n2 >= n3;
    }

    public static final int b(int n2, int n3) {
        int n4;
        int n5;
        block14: {
            if (fo.j(n2)) {
                int n6;
                if (fo.k(n3)) {
                    n6 = d[174];
                    "".length();
                    if (((0x16 ^ 0x2A ^ (0x82 ^ 0xB1)) & (0xE1 ^ 0x9F ^ (0x69 ^ 0x18) ^ -" ".length())) >= " ".length()) {
                        return (0x2A ^ 0x23 ^ (0x44 ^ 0x15)) & (70 + 80 - -16 + 72 ^ 3 + 141 - 12 + 50 ^ -" ".length());
                    }
                } else {
                    n6 = d[177];
                }
                return n6;
            }
            n5 = Math.abs((n3 << d[20]) / n2);
            int n7 = d[1];
            do {
                if (fo.f(n7, d[174])) {
                    n5 = d[1];
                    "".length();
                    if (-"  ".length() > 0) {
                        return (141 + 9 - 125 + 230 ^ 139 + 8 - 123 + 171) & (64 + 20 - -58 + 39 ^ 13 + 97 - 15 + 42 ^ -" ".length());
                    }
                    break block14;
                }
                if (fo.e(c[n7], n5)) {
                    n5 = n7;
                    "".length();
                    if ("  ".length() <= " ".length()) {
                        return (0xE9 ^ 0xA8) & ~(0x5A ^ 0x1B);
                    }
                    break block14;
                }
                ++n7;
                "".length();
            } while ((0xDE ^ 0xA0 ^ (0x36 ^ 0x4C)) >= -" ".length());
            return (182 + 17 - 176 + 176 ^ 25 + 156 - 150 + 134) & (0x57 ^ 0x26 ^ (0xBE ^ 0xAD) ^ -" ".length());
        }
        if (fo.h(n3) && fo.g(n2)) {
            n5 = d[176] - n5;
        }
        if (fo.g(n3) && fo.g(n2)) {
            n5 += 180;
        }
        if (fo.g(n3) && fo.h(n2)) {
            n4 = d[178] - n5;
            "".length();
            if (((0x5F ^ 0x65) & ~(0x39 ^ 3)) >= " ".length()) {
                return (2 ^ 0x11) & ~(0x41 ^ 0x52);
            }
        } else {
            n4 = n5;
        }
        return n4;
    }

    public static final int b(int n2) {
        short s2;
        if (fo.h(n2 = fo.e(n2)) && fo.d(n2, d[174])) {
            return a[n2];
        }
        if (fo.e(n2, d[174]) && fo.d(n2, d[176])) {
            return a[d[176] - n2];
        }
        if (fo.e(n2, d[176]) && fo.d(n2, d[177])) {
            s2 = -a[n2 - d[176]];
            "".length();
            if (((0x63 ^ 0x77 ^ (0xB1 ^ 0xC6)) & (121 + 15 - 19 + 90 ^ 38 + 103 - 130 + 161 ^ -" ".length())) == " ".length()) {
                return "  ".length() & ("  ".length() ^ -" ".length());
            }
        } else {
            s2 = -a[d[178] - n2];
        }
        return s2;
    }

    public static void d() {
        e = new short[d[0]];
        c = new int[d[0]];
        int n2 = d[1];
        while (fo.c(n2, d[174])) {
            fo.e[n2] = a[d[174] - n2];
            if (fo.j(e[n2])) {
                fo.c[n2] = d[175];
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                fo.c[n2] = (a[n2] << d[20]) / e[n2];
            }
            ++n2;
            "".length();
            if (" ".length() == " ".length()) continue;
            return;
        }
    }

    public static int c(int n2) {
        return b.nextInt(n2);
    }

    private static boolean f(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean i(int n2) {
        return n2 <= 0;
    }

    public static boolean a(int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        if (fo.e(n2, n4) && fo.d(n2, n4 + n6) && fo.e(n3, n5) && fo.d(n3, n5 + n7)) {
            n8 = d[2];
            "".length();
            if (((0xF0 ^ 0xBA) & ~(0xA ^ 0x40)) > "  ".length()) {
                return ((0x76 ^ 0x68) & ~(0xA4 ^ 0xBA)) != 0;
            }
        } else {
            n8 = d[1];
        }
        return n8 != 0;
    }

    public static int d(int n2) {
        int n3;
        if (fo.k(n2)) {
            n3 = n2;
            "".length();
            if ((0x3D ^ 0x59 ^ (0x75 ^ 0x15)) < "   ".length()) {
                return (0x59 ^ 0x1A ^ (0x8E ^ 0x96)) & (0xF7 ^ 0x9A ^ (0x70 ^ 0x46) ^ -" ".length());
            }
        } else {
            n3 = -n2;
        }
        return n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static void e() {
        d = new int[179];
        fo.d[0] = 15 + 6 - -152 + 66 ^ 113 + 169 - 160 + 58;
        fo.d[1] = (0x53 ^ 0x2E ^ (0x70 ^ 0x26)) & (0x5D ^ 0x28 ^ (0x68 ^ 0x36) ^ -" ".length());
        fo.d[2] = " ".length();
        fo.d[3] = 0xF ^ 0x1D;
        fo.d[4] = "  ".length();
        fo.d[5] = 0x58 ^ 0x7C;
        fo.d[6] = "   ".length();
        fo.d[7] = 0x25 ^ 0x44 ^ (0x7F ^ 0x28);
        fo.d[8] = 0x4F ^ 0x54 ^ (0xB1 ^ 0xAE);
        fo.d[9] = 0x6A ^ 0x64 ^ (0x68 ^ 0x21);
        fo.d[10] = 0x7E ^ 0x3D ^ (0xFB ^ 0xBD);
        fo.d[11] = 0x76 ^ 0x2C ^ "   ".length();
        fo.d[12] = 0x2A ^ 0x5F ^ (0x6D ^ 0x1E);
        fo.d[13] = 0x7F ^ 0x29 ^ (0xF ^ 0x32);
        fo.d[14] = 0xE9 ^ 0xA6 ^ (0x30 ^ 0x78);
        fo.d[15] = 0xBA ^ 0xC7;
        fo.d[16] = 0x72 ^ 0x16 ^ (0x65 ^ 9);
        fo.d[17] = 137 + 95 - 137 + 48;
        fo.d[18] = 0x10 ^ 0x19;
        fo.d[19] = (0x78 ^ 0x6F) + (0x9F ^ 0x84) - -(1 ^ 0x39) + (0x1A ^ 0x2C);
        fo.d[20] = 0x68 ^ 0x44 ^ (0xB0 ^ 0x96);
        fo.d[21] = 130 + 137 - 235 + 146;
        fo.d[22] = 0xBA ^ 0xB1;
        fo.d[23] = 86 + 92 - 33 + 50;
        fo.d[24] = 0x24 ^ 0x28;
        fo.d[25] = (0x4A ^ 0x7A) + (32 + 20 - -98 + 3) - (0x28 ^ 0x7B) + (0xD2 ^ 0x8D);
        fo.d[26] = 0xB ^ 6;
        fo.d[27] = 22 + 77 - 89 + 220;
        fo.d[28] = 0x57 ^ 0x59;
        fo.d[29] = (0x76 ^ 0x4A) + (93 + 69 - 107 + 81) - (0x9F ^ 0xB5) + (0x73 ^ 0x2D);
        fo.d[30] = 83 + 163 - 123 + 52 ^ 76 + 92 - 117 + 109;
        fo.d[31] = -(0xFFFFBEF7 & 0x49FB) & (0xFFFFE9FB & 0x1FFF);
        fo.d[32] = 53 + 66 - 24 + 44 ^ 55 + 150 - 161 + 111;
        fo.d[33] = -(0xFFFFFEFD & 0x5FA7) & (0xFFFFFFBE & 0x5FFF);
        fo.d[34] = 0x79 ^ 0xF ^ (0x20 ^ 0x47);
        fo.d[35] = 0xFFFFBBFB & 0x452F;
        fo.d[36] = -(0xFFFFFFDF & 0x3AA1) & (0xFFFFFFBC & 0x3BFF);
        fo.d[37] = 142 + 30 - 171 + 180 ^ 46 + 160 - 161 + 121;
        fo.d[38] = 0xFFFFD9ED & 0x275F;
        fo.d[39] = 16 + 150 - -3 + 39 ^ 57 + 63 - 14 + 90;
        fo.d[40] = -(0xFFFFF72A & 0x7AF7) & (0xFFFFF77F & 0x7BFF);
        fo.d[41] = 2 ^ 0x5C ^ (0x3F ^ 0x74);
        fo.d[42] = -(0xFFFFC53F & 0x7EC1) & (0xFFFFFFEF & 0x457F);
        fo.d[43] = 0xF8 ^ 0x8C ^ (0xDA ^ 0xB8);
        fo.d[44] = -(0xFFFFF342 & 0x3CBF) & (0xFFFFB98F & 0x77F1);
        fo.d[45] = 0x49 ^ 0x75 ^ (0x3F ^ 0x14);
        fo.d[46] = -(0xFFFFFDCF & 0x567E) & (0xFFFFFFDF & 0x55FD);
        fo.d[47] = 0x81 ^ 0x99;
        fo.d[48] = 0xFFFF85B5 & 0x7BEA;
        fo.d[49] = 0x74 ^ 0x33 ^ (0x9F ^ 0xC1);
        fo.d[50] = -(0xFFFFD9DF & 0x7E6F) & (0xFFFFDBFF & 0x7DFF);
        fo.d[51] = 0x87 ^ 0xBC ^ (0x4A ^ 0x6B);
        fo.d[52] = -(0xFFFFFF9B & 0x347F) & (0xFFFFF5DF & 0x3FFB);
        fo.d[53] = 0x44 ^ 0x32 ^ (0x44 ^ 0x29);
        fo.d[54] = 0xFFFF8DF1 & 0x73DF;
        fo.d[55] = 17 + 50 - 60 + 147 ^ 14 + 1 - -7 + 112;
        fo.d[56] = -(0xFFFFEEB5 & 0x7F5F) & (0xFFFFEFFF & 0x7FF5);
        fo.d[57] = 0x10 ^ 0x58 ^ (0x69 ^ 0x3C);
        fo.d[58] = -(0xFFFFBFCF & 0x7A32) & (0xFFFFBFF1 & 0x7BFF);
        fo.d[59] = 0xFA ^ 0xB0 ^ (0x29 ^ 0x7D);
        fo.d[60] = -(0xFFFFBAFF & 0x7DFD) & (0xFFFFBBFE & 0x7EFD);
        fo.d[61] = 130 + 150 - 151 + 47 ^ 77 + 130 - 93 + 61;
        fo.d[62] = -(0xFFFFBFFD & 0x5D73) & (0xFFFF9FFF & 0x7F7F);
        fo.d[63] = 0xAB ^ 0x92 ^ (0x8C ^ 0x95);
        fo.d[64] = 0xFFFFBB1F & 0x46FF;
        fo.d[65] = 0x81 ^ 0xA0;
        fo.d[66] = 0xFFFFABAE & 0x567F;
        fo.d[67] = 57 + 53 - -2 + 21 ^ 100 + 162 - 249 + 154;
        fo.d[68] = 0xFFFFF37F & 0xEBD;
        fo.d[69] = 0xCA ^ 0x9A ^ (0xCB ^ 0xB8);
        fo.d[70] = 0xFFFFA66F & 0x5BDB;
        fo.d[71] = 0xFFFFD25B & 0x2FFE;
        fo.d[72] = 0x48 ^ 8 ^ (0xEE ^ 0x8B);
        fo.d[73] = 0xFFFF8B6B & 0x76FC;
        fo.d[74] = 14 + 133 - 71 + 97 ^ 88 + 54 - 64 + 61;
        fo.d[75] = -(0xFFFFF51E & 0x1FEB) & (0xFFFF9FFF & 0x777F);
        fo.d[76] = 0x68 ^ 0x4F;
        fo.d[77] = -(0xFFFFACFF & 0x7F7A) & (0xFFFFFEFF & 0x2FFD);
        fo.d[78] = 0xCF ^ 0xC2 ^ (0xBC ^ 0x99);
        fo.d[79] = 0xFFFFCAF6 & 0x379B;
        fo.d[80] = 0xE ^ 0x27;
        fo.d[81] = -(0xFFFFD5D6 & 0x2B3B) & (0xFFFFFBBF & 0x7F1);
        fo.d[82] = 0x26 ^ 0xC;
        fo.d[83] = 0xFFFFA7BF & 0x5AED;
        fo.d[84] = 0x90 ^ 0xBB;
        fo.d[85] = -(0xFFFFF0D7 & 0x1F2D) & (0xFFFFDFFF & 0x32BE);
        fo.d[86] = 0x35 ^ 0x19;
        fo.d[87] = 0xFFFFCBFF & 0x36C7;
        fo.d[88] = 0x10 ^ 0x37 ^ (0xE ^ 4);
        fo.d[89] = -(0xFFFFFD1E & 0x4EEB) & (0xFFFFEEFD & 0x5FDF);
        fo.d[90] = 0x83 ^ 0xAD;
        fo.d[91] = 0xFFFFB2F3 & 0x4FED;
        fo.d[92] = 174 + 104 - 96 + 8 ^ 2 + 14 - -115 + 14;
        fo.d[93] = 0xFFFFAAED & 0x57FF;
        fo.d[94] = 0xF2 ^ 0xC2;
        fo.d[95] = 0xFFFFB7FB & 0x4AFD;
        fo.d[96] = 48 + 130 - 31 + 31 ^ 94 + 116 - 188 + 109;
        fo.d[97] = 0xFFFFF73D & 0xBC7;
        fo.d[98] = 0x42 ^ 0x3F ^ (5 ^ 0x4A);
        fo.d[99] = 0xFFFFD39D & 0x2F72;
        fo.d[100] = 112 + 16 - 47 + 61 ^ 50 + 64 - 5 + 80;
        fo.d[101] = -(0xFFFFF93F & 0x56E1) & (0xFFFFFF7D & 0x53BE);
        fo.d[102] = 0x48 ^ 0x7D ^ " ".length();
        fo.d[103] = 0xFFFFCFA7 & 0x337F;
        fo.d[104] = 0x26 ^ 0x6E ^ (0x30 ^ 0x4D);
        fo.d[105] = 0xFFFFF77B & 0xBB6;
        fo.d[106] = 0xFFFF87BD & 0x7B7E;
        fo.d[107] = 6 + 8 - 10 + 154 ^ 75 + 149 - 59 + 4;
        fo.d[108] = 0xFFFF9B77 & 0x67CF;
        fo.d[109] = 0x32 ^ 0xA;
        fo.d[110] = 0xFFFFC373 & 0x3FDD;
        fo.d[111] = 0xB7 ^ 0x8E;
        fo.d[112] = 0xFFFF8FDB & 0x737F;
        fo.d[113] = 0x5F ^ 0x65;
        fo.d[114] = -(0xFFFF999F & 0x7EEA) & (0xFFFFDBED & 0x3FFF);
        fo.d[115] = 130 + 89 - 189 + 113 ^ 60 + 63 - -44 + 13;
        fo.d[116] = -(0xFFFFFDBF & 0x5652) & (0xFFFFFF7F & 0x57FF);
        fo.d[117] = 0xB5 ^ 0x89;
        fo.d[118] = -(0xFFFFCF4F & 0x7CB9) & (0xFFFFCFFF & 0x7F7F);
        fo.d[119] = 0xAC ^ 0xA9 ^ (0x76 ^ 0x4E);
        fo.d[120] = 0xFFFFF3CE & 0xFB1;
        fo.d[121] = 0x15 ^ 0x40 ^ (0x31 ^ 0x5A);
        fo.d[122] = 0xFFFFEFDE & 0x13A9;
        fo.d[123] = 0x99 ^ 0xA6;
        fo.d[124] = 0xFFFFABBE & 0x57D1;
        fo.d[125] = 0xFA ^ 0xBA;
        fo.d[126] = 0xFFFF93BF & 0x6FD8;
        fo.d[127] = 90 + 9 - 72 + 106 ^ 166 + 132 - 296 + 194;
        fo.d[128] = -(0xFFFF9CE0 & 0x6F7F) & (0xFFFFBFFF & 0x4FFF);
        fo.d[129] = 0x1F ^ 0x5D;
        fo.d[130] = 0xFFFFC3BF & 0x3FE7;
        fo.d[131] = 98 + 82 - 126 + 80 ^ 141 + 20 - 92 + 128;
        fo.d[132] = 0xFFFFA3BF & 0x5FEF;
        fo.d[133] = 26 + 90 - -92 + 1 ^ 33 + 144 - 55 + 27;
        fo.d[134] = 0xFFFFDFF7 & 0x23BD;
        fo.d[135] = 19 + 109 - 119 + 121 ^ 134 + 104 - 154 + 115;
        fo.d[136] = 0xFFFFE3BF & 0x1FFC;
        fo.d[137] = 118 + 221 - 301 + 213 ^ 6 + 2 - -119 + 62;
        fo.d[138] = -(0xFFFFFEBD & 0x7167) & (0xFFFFFBF6 & 0x77EF);
        fo.d[139] = 0xFFFFDFFB & 0x23CC;
        fo.d[140] = 67 + 210 - 104 + 66 ^ 125 + 118 - 114 + 38;
        fo.d[141] = 0xFFFFBFCE & 0x43FF;
        fo.d[142] = 0x23 ^ 0x6A;
        fo.d[143] = 0xFFFFFBFF & 0x7D3;
        fo.d[144] = 0xCE ^ 0x84;
        fo.d[145] = 0xFFFFC3DD & 0x3FFA;
        fo.d[146] = 0x6E ^ 0x25;
        fo.d[147] = -" ".length() & (0xFFFF87FD & 0x7BDF);
        fo.d[148] = 89 + 81 - 74 + 158 ^ 30 + 76 - -9 + 63;
        fo.d[149] = 0xFFFFC3FB & 0x3FE6;
        fo.d[150] = 0x7C ^ 0x31;
        fo.d[151] = 0xFFFF83EE & 0x7FF7;
        fo.d[152] = 0x79 ^ 0x37;
        fo.d[153] = 0xFFFFB3EB & 0x4FFE;
        fo.d[154] = 137 + 132 - 263 + 134 ^ 194 + 42 - 104 + 63;
        fo.d[155] = 0xFFFFB3FD & 0x4FEF;
        fo.d[156] = 0x7F ^ 0x2F;
        fo.d[157] = 0xFFFFFFF2 & 0x3FD;
        fo.d[158] = 240 + 104 - 218 + 126 ^ 145 + 34 - 36 + 30;
        fo.d[159] = 0xFFFFD3F7 & 0x2FFB;
        fo.d[160] = 0x42 ^ 0x2C ^ (0x29 ^ 0x15);
        fo.d[161] = -(0xFFFFF642 & 0x6DBF) & (0xFFFFEFF7 & 0x77FF);
        fo.d[162] = 0x5B ^ 8;
        fo.d[163] = 0xFFFFDBFE & 0x27F9;
        fo.d[164] = 47 + 32 - -104 + 19 ^ 140 + 68 - 201 + 151;
        fo.d[165] = -(0xFFFFDC47 & 0x73BD) & (0xFFFFD7FE & 0x7BFF);
        fo.d[166] = 0x27 ^ 0x23 ^ (0x11 ^ 0x40);
        fo.d[167] = -(0xFFFFB0D9 & 0x7F27) & (0xFFFFFBFD & 0x37FE);
        fo.d[168] = 0xFE ^ 0xA8;
        fo.d[169] = 0xFFFFCBFF & 0x37FE;
        fo.d[170] = 0x3B ^ 0x6C;
        fo.d[171] = -(0xFFFFFC09 & 0x3FF7) & (0xFFFFBFFF & Short.MAX_VALUE);
        fo.d[172] = 20 + 0 - -24 + 167 ^ 33 + 55 - 59 + 110;
        fo.d[173] = -(0xFFFFBABE & 0x777F) & (0xFFFFFF7F & 0x36BD);
        fo.d[174] = 206 + 52 - 153 + 117 ^ 59 + 79 - 53 + 47;
        fo.d[175] = -" ".length() & (0xFFFFFFFF & Integer.MAX_VALUE);
        fo.d[176] = (0x68 ^ 0x13) + (0x12 ^ 0x35) - "   ".length() + (0x34 ^ 0x21);
        fo.d[177] = -(0xFFFFFFF3 & 0x2E0D) & (0xFFFFAF3F & 0x7FCE);
        fo.d[178] = 0xFFFF9FEF & 0x6178;
    }

    private static boolean j(int n2) {
        return n2 == 0;
    }

    private static boolean k(int n2) {
        return n2 > 0;
    }

    public static final int e(int n2) {
        if (fo.e(n2, d[178])) {
            n2 -= 360;
        }
        if (fo.g(n2)) {
            n2 += 360;
        }
        return n2;
    }

    public static final int f(int n2) {
        short s2;
        if (fo.h(n2 = fo.e(n2)) && fo.d(n2, d[174])) {
            return e[n2];
        }
        if (fo.e(n2, d[174]) && fo.d(n2, d[176])) {
            return -e[d[176] - n2];
        }
        if (fo.e(n2, d[176]) && fo.d(n2, d[177])) {
            s2 = -e[n2 - d[176]];
            "".length();
            if ((0x11 ^ 0x15) == 0) {
                return (0xD0 ^ 0x9E) & ~(0x46 ^ 8);
            }
        } else {
            s2 = e[d[178] - n2];
        }
        return s2;
    }
}

