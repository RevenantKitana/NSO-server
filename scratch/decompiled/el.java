/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.microedition.lcdui.Image;

public final class el {
    public boolean a;
    private static final int[] z;
    public ad b;
    public boolean c;
    public long d = 0L;
    private long aa;
    public int e;
    public int f;
    public int g;
    public long h = 0L;
    public int i;
    public long j = 0L;
    public int k;
    public boolean l = z[0];
    public bf m;
    public int n;
    public Image o;
    public int p;
    public boolean q;
    public int r;
    public int s;
    public boolean t = z[0];
    public long u;
    public int v;
    public int w;
    public a x;
    public int y;

    public final boolean a() {
        boolean bl2;
        if (el.c(this.r, z[34])) {
            bl2 = z[1];
            "".length();
            if (null != null) {
                return ((0x4C ^ 0x69) & ~(0x4A ^ 0x6F)) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    private static boolean a(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    public final int b() {
        int n2;
        if (el.d(this.m.h) && el.d(this.m.h, z[15])) {
            return z[30];
        }
        if (el.e(this.m.h, z[15]) && el.d(this.m.h, z[35])) {
            return z[4];
        }
        if (el.e(this.m.h, z[35]) && el.d(this.m.h, z[36])) {
            return z[9];
        }
        if (el.e(this.m.h, z[36]) && el.d(this.m.h, z[37])) {
            n2 = z[11];
            "".length();
            if (-" ".length() == "  ".length()) {
                return (0xDD ^ 0x81) & ~(0x73 ^ 0x2F);
            }
        } else {
            n2 = z[22];
        }
        return n2;
    }

    private static boolean e(int n2) {
        return n2 == 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public final boolean c() {
        int n2;
        if (!(el.e(this.f() ? 1 : 0) && el.e(this.p() ? 1 : 0) && el.e(this.j() ? 1 : 0) && el.e(this.a() ? 1 : 0) && !el.g(this.g() ? 1 : 0))) {
            n2 = z[1];
            "".length();
            if ("  ".length() == 0) {
                return ((0x55 ^ 0x10) & ~(0x18 ^ 0x5D)) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    private static boolean f(int n2) {
        return n2 <= 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    public final el d() {
        el el2 = new el();
        new el().m = this.m;
        if (el.a(this.b)) {
            el2.b = new ad();
            int n2 = z[0];
            while (el.d(n2, this.b.size())) {
                cs cs2 = new cs();
                new cs().a = ((cs)this.b.elementAt((int)n2)).a;
                cs2.c = ((cs)this.b.elementAt((int)n2)).c;
                el2.b.addElement(cs2);
                ++n2;
                "".length();
                if (null == null) continue;
                return null;
            }
        }
        el2.w = this.w;
        el2.n = this.n;
        el2.g = this.g;
        el2.u = this.u;
        el2.c = this.c;
        el2.v = this.v;
        el2.y = this.y;
        el2.f = this.f;
        el2.p = this.p;
        el2.i = this.i;
        el2.e = this.e;
        el2.r = this.r;
        el2.a = this.a;
        return el2;
    }

    public final boolean e() {
        boolean bl2;
        if (el.c(this.b(), this.y)) {
            bl2 = z[1];
            "".length();
            if (((0x44 ^ 3) & ~(0x7A ^ 0x3D)) != ((0x6C ^ 0x3F) & ~(0x16 ^ 0x45))) {
                return ((0x12 ^ 0x29) & ~(0x40 ^ 0x7B)) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    public final boolean f() {
        int n2;
        if (!(el.b(this.r, z[15]) && el.b(this.r, z[16]) && el.b(this.r, z[17]) && el.b(this.r, z[18]) && el.b(this.r, z[19]) && el.b(this.r, z[20]) && el.b(this.r, z[21]) && el.b(this.r, z[32]) && el.b(this.r, z[33]) && el.b(this.r, z[26]) && el.b(this.r, z[22]) && el.b(this.r, z[13]) && el.b(this.r, z[14]) && el.b(this.r, z[6]) && el.b(this.r, z[29]) && el.b(this.r, z[2]) && el.b(this.r, z[4]) && !el.c(this.r, z[28]))) {
            n2 = z[1];
            "".length();
            if (null != null) {
                return ((0xE6 ^ 0xB0) & ~(0xD2 ^ 0x84)) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    private static void v() {
        z = new int[40];
        el.z[0] = (0xC4 ^ 0x87) & ~(0x11 ^ 0x52);
        el.z[1] = " ".length();
        el.z[2] = 0xED ^ 0xB8 ^ (0x95 ^ 0xC6);
        el.z[3] = 0x81 ^ 0xB4 ^ (0x26 ^ 0x14);
        el.z[4] = 0x2C ^ 0x24;
        el.z[5] = 0x12 ^ 0x3E ^ (0x21 ^ 4);
        el.z[6] = 0x8A ^ 0x99;
        el.z[7] = 0x69 ^ 0x63;
        el.z[8] = 0xCC ^ 0xC7;
        el.z[9] = 0x4F ^ 0x43;
        el.z[10] = 116 + 100 - 171 + 125 ^ 6 + 129 - 19 + 51;
        el.z[11] = 0x6E ^ 0xB ^ (0xF ^ 0x64);
        el.z[12] = 0x87 ^ 0x8E ^ (0x20 ^ 0x26);
        el.z[13] = 0x2F ^ 0x46 ^ (0x4A ^ 0x32);
        el.z[14] = 149 + 29 - 113 + 91 ^ 55 + 135 - 165 + 117;
        el.z[15] = 0x11 ^ 5;
        el.z[16] = 0x95 ^ 0x80;
        el.z[17] = 0x16 ^ 0;
        el.z[18] = 0x30 ^ 0x27;
        el.z[19] = 0x7D ^ 0x65;
        el.z[20] = 0x95 ^ 0x8C;
        el.z[21] = 0xEC ^ 0xA0 ^ (0x39 ^ 0x6F);
        el.z[22] = 0xA4 ^ 0xB4;
        el.z[23] = "   ".length();
        el.z[24] = (0xA9 ^ 0xB4) + (0x4B ^ 0x5C) - -(0x25 ^ 0x66) + (0x72 ^ 0x6D);
        el.z[25] = 3 + 10 - -62 + 108 ^ 1 + 137 - -23 + 17;
        el.z[26] = 0x48 ^ 0x55;
        el.z[27] = 0x54 ^ 0x75;
        el.z[28] = 0x82 ^ 0x87 ^ (0x31 ^ 0x16);
        el.z[29] = "  ".length();
        el.z[30] = 0xC4 ^ 0xC0;
        el.z[31] = 0x4B ^ 0x3D ^ (0xE5 ^ 0xB4);
        el.z[32] = 0x8E ^ 0x95;
        el.z[33] = 0x21 ^ 0x3D;
        el.z[34] = 0xBD ^ 0x9D;
        el.z[35] = 0x71 ^ 0x59;
        el.z[36] = 0xAC ^ 0x9E;
        el.z[37] = 0x47 ^ 0x7B;
        el.z[38] = 126 + 17 - -30 + 38 ^ 116 + 126 - 112 + 4;
        el.z[39] = -" ".length();
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    public final boolean g() {
        boolean bl2;
        if (el.c(this.r, z[28])) {
            bl2 = z[1];
            "".length();
            if (((114 + 72 - 170 + 125 ^ 130 + 41 - 151 + 139) & (0x36 ^ 0x3E ^ (0x69 ^ 0x73) ^ -" ".length())) >= (60 + 152 - 176 + 153 ^ 167 + 69 - 145 + 94)) {
                return ((0x7D ^ 0x30 ^ (0x3F ^ 0x21)) & (0xA ^ 0x45 ^ (0xDF ^ 0xC3) ^ -" ".length())) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    public final boolean h() {
        int n2;
        if (el.a(z[26], this.m.b) && el.a(this.m.b, z[27])) {
            n2 = z[1];
            "".length();
            if (((0x6E ^ 0x47) & ~(0x1C ^ 0x35)) != ((0x94 ^ 0xAD) & ~(0x2B ^ 0x12))) {
                return ((0x2B ^ 0x1A) & ~(0xBA ^ 0x8B)) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    public final boolean i() {
        boolean bl2;
        if (el.c(this.m.b, z[28])) {
            bl2 = z[1];
            "".length();
            if (" ".length() >= "  ".length()) {
                return ((0x80 ^ 0xB3) & ~(0xA6 ^ 0x95)) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    private static boolean g(int n2) {
        return n2 != 0;
    }

    public final boolean j() {
        boolean bl2;
        if (el.c(this.r, z[12])) {
            bl2 = z[1];
            "".length();
            if ("  ".length() < 0) {
                return ((0xAC ^ 0xB3) & ~(0x6A ^ 0x75)) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    private static boolean d(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean h(int n2) {
        return n2 >= 0;
    }

    public final String k() {
        if (el.f(el.a(this.u, 0L))) {
            return ag.kb;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        calendar.setTime(new Date(this.u));
        int n2 = calendar.get(z[8]);
        int n3 = calendar.get(z[9]);
        int n4 = calendar.get(z[25]);
        int n5 = calendar.get(z[29]) + z[1];
        int n6 = calendar.get(z[1]);
        return n4 + "/" + n5 + "/" + n6 + " " + n2 + "h" + n3 + "'";
    }

    public final boolean l() {
        boolean bl2;
        if (el.c(this.m.b, z[1])) {
            bl2 = z[1];
            "".length();
            if (((0x4F ^ 0x33 ^ (0xDE ^ 0x95)) & (0xC8 ^ 0x8B ^ (0x38 ^ 0x4C) ^ -" ".length())) != 0) {
                return ((0xB1 ^ 0xC1 ^ (0xB ^ 0x32)) & (157 + 32 - 149 + 158 ^ 14 + 9 - -1 + 119 ^ -" ".length())) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    public final boolean a(int n2) {
        if (el.a(this.b)) {
            int n3 = z[0];
            while (el.d(n3, this.b.size())) {
                cs cs2 = (cs)this.b.elementAt(n3);
                if (el.a(cs2) && el.c(cs2.a.b, n2)) {
                    return z[1];
                }
                ++n3;
                "".length();
                if (null == null) continue;
                return ((0x9E ^ 0xA8) & ~(0x9D ^ 0xAB)) != 0;
            }
        }
        return z[0];
    }

    public final boolean m() {
        int n2;
        if (el.h(this.m.b) && el.a(this.m.b, z[12])) {
            n2 = z[1];
            "".length();
            if (((0x3A ^ 0x17) & ~(0x9F ^ 0xB2)) != 0) {
                return ((0x71 ^ 0x50) & ~(0x94 ^ 0xB5)) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    public final void n() {
        if (el.e(this.h() ? 1 : 0)) {
            this.u = 0L;
        }
    }

    public final boolean o() {
        int n2;
        if (!(el.g(this.m.b) && el.b(this.m.b, z[29]) && el.b(this.m.b, z[30]) && el.b(this.m.b, z[2]) && !el.c(this.m.b, z[4]))) {
            n2 = z[1];
            "".length();
            if (null != null) {
                return ((0x5D ^ 0x4D ^ (0xAA ^ 0x99)) & (0x1F ^ 0x57 ^ (0x78 ^ 0x13) ^ -" ".length())) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    private static boolean e(int n2, int n3) {
        return n2 >= n3;
    }

    public final boolean p() {
        boolean bl2;
        if (el.c(this.r, z[11])) {
            bl2 = z[1];
            "".length();
            if (null != null) {
                return ((0x68 ^ 0x41) & ~(0x6C ^ 0x45)) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }

    public final el b(int n2) {
        el el2 = this.d();
        this.d().c = z[1];
        int n3 = n2 - el2.y;
        if (el.e(n3)) {
            return el2;
        }
        el2.y = n2;
        if (el.a(el2.b)) {
            n2 = z[0];
            while (el.d(n2, el2.b.size())) {
                cs cs2 = (cs)el2.b.elementAt(n2);
                if (el.b(cs2.a.b, z[2]) && el.b(cs2.a.b, z[3])) {
                    if (el.b(cs2.a.b, z[4]) && el.b(cs2.a.b, z[5]) && el.b(cs2.a.b, z[6])) {
                        if (el.b(cs2.a.b, z[7]) && el.b(cs2.a.b, z[8]) && el.b(cs2.a.b, z[9]) && el.b(cs2.a.b, z[10]) && el.b(cs2.a.b, z[11]) && el.b(cs2.a.b, z[12]) && el.b(cs2.a.b, z[13]) && el.b(cs2.a.b, z[14]) && el.b(cs2.a.b, z[15])) {
                            if (el.b(cs2.a.b, z[16]) && el.b(cs2.a.b, z[17]) && el.b(cs2.a.b, z[18]) && el.b(cs2.a.b, z[19]) && el.b(cs2.a.b, z[20]) && el.b(cs2.a.b, z[21])) {
                                if (el.c(cs2.a.b, z[22])) {
                                    cs2.c += n3 * z[23];
                                    "".length();
                                    if ("  ".length() < "  ".length()) {
                                        return null;
                                    }
                                }
                            } else {
                                cs2.c += n3 * z[24];
                                "".length();
                                if (null != null) {
                                    return null;
                                }
                            }
                        } else {
                            cs2.c += n3 * z[25];
                            "".length();
                            if (null != null) {
                                return null;
                            }
                        }
                    } else {
                        cs2.c += n3 * z[7];
                        "".length();
                        if (((0x78 ^ 0xF ^ (0x22 ^ 0x17)) & (0xC9 ^ 0x99 ^ (0x68 ^ 0x7A) ^ -" ".length()) & ((0xD5 ^ 0x9B ^ (0x20 ^ 0x38)) & (153 + 149 - 122 + 25 ^ 122 + 40 - 111 + 104 ^ -" ".length()) ^ -" ".length())) <= -" ".length()) {
                            return null;
                        }
                    }
                } else {
                    cs2.c += n3 * z[12];
                }
                ++n2;
                "".length();
                if (((0x15 ^ 0x21 ^ (0xF8 ^ 0x86)) & (0x5F ^ 0x57 ^ (0xFE ^ 0xBC) ^ -" ".length())) == 0) continue;
                return null;
            }
        }
        return el2;
    }

    public final int q() {
        if (el.a(this.b)) {
            int n2 = z[0];
            while (el.d(n2, this.b.size())) {
                cs cs2 = (cs)this.b.elementAt(n2);
                if (el.a(cs2) && el.c(cs2.a.b, z[38])) {
                    return cs2.c;
                }
                ++n2;
                "".length();
                if (((0xA9 ^ 0xC2 ^ (0xF5 ^ 0x90)) & (43 + 12 - 22 + 102 ^ 4 + 86 - -7 + 40 ^ -" ".length())) == 0) continue;
                return (114 + 201 - 208 + 98 ^ 125 + 3 - 120 + 141) & (0x43 ^ 7 ^ (0x29 ^ 0x35) ^ -" ".length());
            }
        }
        return z[39];
    }

    public final boolean r() {
        int n2;
        if (!el.b(this.r, z[25]) || !el.b(this.r, z[23]) || !el.b(this.r, z[30]) || el.c(this.r, z[31])) {
            n2 = z[1];
            "".length();
            if (" ".length() >= "   ".length()) {
                return ((0xC2 ^ 0x97 ^ (0xA2 ^ 0xBE)) & ((0xB5 ^ 0xA7) & ~(0x92 ^ 0x80) ^ (0x59 ^ 0x10) ^ -" ".length())) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final int c(int n2) {
        int n3 = z[0];
        if (el.a(this.b)) {
            int n4 = z[0];
            while (el.d(n4, this.b.size())) {
                cs cs2 = (cs)this.b.elementAt(n4);
                if (el.a(cs2) && el.c(cs2.a.a, n2)) {
                    ++n3;
                }
                ++n4;
                "".length();
                if (null == null) continue;
                return (0x3F ^ 0x35) & ~(0x32 ^ 0x38);
            }
        }
        return n3;
    }

    public final boolean s() {
        int n2;
        if (!el.b(this.m.b, z[23]) || !el.b(this.m.b, z[25]) || !el.b(this.m.b, z[3]) || el.c(this.m.b, z[5])) {
            n2 = z[1];
            "".length();
            if ("  ".length() == 0) {
                return ((0x59 ^ 0x79) & ~(0x5B ^ 0x7B)) != 0;
            }
        } else {
            n2 = z[0];
        }
        return n2 != 0;
    }

    static {
        el.v();
    }

    public el() {
        this.q = z[0];
        this.s = z[0];
    }

    public final String t() {
        String string;
        if (el.f(el.b(this.u, 0L))) {
            return ag.kb;
        }
        if (el.h(el.b(this.u / 1000L, 2592000L))) {
            return this.u / 1000L / 2592000L + " " + ag.er;
        }
        if (el.h(el.b(this.u / 1000L, 604800L))) {
            return this.u / 1000L / 604800L + " " + ag.sm;
        }
        if (el.h(el.b(this.u / 1000L, 86400L))) {
            return this.u / 1000L / 86400L + " " + ag.ri;
        }
        if (el.h(el.b(this.u / 1000L, 3600L))) {
            return this.u / 1000L / 3600L + " " + ag.rd;
        }
        if (el.h(el.b(this.u / 1000L, 60L))) {
            string = this.u / 1000L / 60L + " " + ag.ah;
            "".length();
            if ((0x3E ^ 0x3A) < ((0xB9 ^ 0xBC) & ~(0xB0 ^ 0xB5))) {
                return null;
            }
        } else {
            string = "";
        }
        return string;
    }

    public final boolean u() {
        boolean bl2;
        if (!el.b(this.r, z[3]) || el.c(this.r, z[5])) {
            bl2 = z[1];
            "".length();
            if ((0x55 ^ 0x6B ^ (2 ^ 0x38)) != (0x4F ^ 0x4A ^ " ".length())) {
                return ((40 + 207 - 21 + 6 ^ 70 + 78 - 139 + 166) & (0xD9 ^ 0x99 ^ (0x17 ^ 0x10) ^ -" ".length())) != 0;
            }
        } else {
            bl2 = z[0];
        }
        return bl2;
    }
}

