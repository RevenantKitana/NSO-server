/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;
import java.util.Random;

public final class eq
extends ax {
    public static String a;
    private static final int[] ak;
    public static int b;
    public static Random c;
    public static byte d;
    public static byte e;
    public static byte ah;
    public static boolean ai;
    public static boolean aj;

    private static boolean a(int n2) {
        return n2 < 0;
    }

    public final void a() {
        if (eq.h(ec.n().cl)) {
            ax.a(ak[2]);
            "".length();
            if (((0x2D ^ 0x1B) & ~(0x8F ^ 0xB9)) > 0) {
                return;
            }
        } else if (eq.e(this.u, de.i) && eq.e(this.x, de.ab)) {
            bo.b(-1L);
            "".length();
            fd.a = ak[3];
            long l2 = System.currentTimeMillis();
            while (eq.c(ap.s().bc, ak[2])) {
                if (!eq.g(fd.a ? 1 : 0) || eq.f(eq.b(System.currentTimeMillis() - l2, 20000L))) {
                    return;
                }
                ax.a(200L);
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            ap.s().er = ak[3];
            ap.ag = new el[ak[1]];
            dl.h().a(ak[3], ap.ag);
            ap.s().ao = ak[2];
            ax.a(5000L);
            dl.h().v();
            bo.b(20000L);
            "".length();
            "".length();
            if ("   ".length() == 0) {
                return;
            }
        } else {
            this.a(this.u, this.x, ak[4], ak[4]);
        }
    }

    private static boolean e(int n2) {
        return n2 != 0;
    }

    private static boolean f(int n2) {
        return n2 >= 0;
    }

    private static boolean g(int n2) {
        return n2 == 0;
    }

    private static boolean c(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }

    private static void o() {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(an.b("V6Rec"));
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            a = dataInputStream.readUTF();
            if (eq.e(a.equals("") ? 1 : 0)) {
                a = null;
            }
            if (eq.e((ed.c = dataInputStream.readUTF()).equals("") ? 1 : 0)) {
                ed.c = null;
            }
            if (eq.e((ed.a = dataInputStream.readUTF()).equals("") ? 1 : 0)) {
                ed.a = null;
            }
            e = dataInputStream.readByte();
            d = dataInputStream.readByte();
            b = dataInputStream.readInt();
            ah = dataInputStream.readByte();
            dataInputStream.close();
            byteArrayInputStream.close();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("  ".length() >= (0x70 ^ 0x4A ^ (0x93 ^ 0xAD))) {
            return;
        }
    }

    public final String toString() {
        return "Auto Nh\u1eadn";
    }

    private static boolean h(int n2) {
        return n2 <= 0;
    }

    private static boolean f(int n2, int n3) {
        return n2 < n3;
    }

    public eq() {
        this.u = de.i;
        this.x = de.ab;
    }

    public static void a(String string) {
        ec ec2;
        ec ec3;
        int n2;
        ec ec4;
        block22: {
            ec4 = ec.n();
            n2 = ak[3];
            do {
                if (eq.d(n2, ap.dn.size())) {
                    ec3 = null;
                    "".length();
                    if ((0xC7 ^ 0x9A ^ (0xD ^ 0x54)) == ((34 + 64 - 27 + 90 ^ 57 + 54 - -45 + 9) & (0xD5 ^ 0xB3 ^ (4 ^ 0x66) ^ -" ".length()))) {
                        return;
                    }
                    break block22;
                }
                ec2 = (ec)ap.dn.elementAt(n2);
                if (eq.e(ec2.cx.equals(string) ? 1 : 0)) {
                    ec3 = ec2;
                    "".length();
                    if (null != null) {
                        return;
                    }
                    break block22;
                }
                ++n2;
                "".length();
            } while ("   ".length() > "  ".length());
            return;
        }
        ec2 = ec3;
        if (eq.a(ec3)) {
            fe.b = ak[2];
            "".length();
            if (-(0x8B ^ 0x8F) > 0) {
                return;
            }
        } else {
            do {
                if (eq.d(fo.a(ec4.s, ec4.fc, ec2.s, ec2.fc), ak[5])) {
                    ec.d(ec2.s, ec2.fc);
                    "".length();
                    ax.a(1000L);
                    "".length();
                    if ("   ".length() >= "  ".length()) continue;
                    return;
                }
                dl.h().k(ec2.w);
            } while (eq.g(ap.bq ? 1 : 0) && !eq.e(bo.b((long)(c.nextInt(ak[6]) + ak[7])) ? 1 : 0));
            el[] elArray = new el[ak[1]];
            n2 = ak[3];
            int n3 = ak[3];
            while (eq.f(n3, ey.b(ed.c, ",").length)) {
                int n4 = ak[3];
                while (eq.f(n4, ec4.en.length)) {
                    el el2 = ec4.en[n4];
                    if (eq.f(n2, ak[1]) && eq.b(el2) && eq.e(el2.m.d, Integer.parseInt(ey.b(ed.c, ",")[n3])) && eq.g(el2.c ? 1 : 0)) {
                        elArray[n2++] = el2;
                    }
                    ++n4;
                    "".length();
                    if (((0x26 ^ 0xB ^ (0x2C ^ 0x50)) & (0x2A ^ 0x77 ^ (0xB7 ^ 0xBB) ^ -" ".length())) <= 0) continue;
                    return;
                }
                ++n3;
                "".length();
                if ("  ".length() != " ".length()) continue;
                return;
            }
            ap.s().er = ak[3];
            ap.ag = elArray;
            dl.h().a(ec.n().ga, elArray);
            ap.s().ao = ak[2];
            long l2 = System.currentTimeMillis();
            while (eq.c(ap.s().bc, ak[2])) {
                if (!eq.g(fd.a ? 1 : 0) || eq.f(eq.a(System.currentTimeMillis() - l2, 2000L))) {
                    dl.h().p();
                    return;
                }
                ax.a(200L);
                "".length();
                if (((0x57 ^ 0) & ~(0x94 ^ 0xC3)) == 0) continue;
                return;
            }
            l2 = System.currentTimeMillis();
            while (eq.a(eq.a(System.currentTimeMillis() - l2, 2000L))) {
                if (eq.e(fd.a ? 1 : 0)) {
                    return;
                }
                ax.a(200L);
                "".length();
                if (((9 ^ 0x1E) & ~(0x40 ^ 0x57)) == 0) continue;
                return;
            }
            dl.h().v();
            l2 = System.currentTimeMillis();
            while (eq.c(ap.s().bc, ak[8])) {
                if (!eq.g(fd.a ? 1 : 0) || eq.f(eq.a(System.currentTimeMillis() - l2, 5000L))) {
                    dl.h().p();
                    return;
                }
                ax.a(200L);
                "".length();
                if (((0xBD ^ 0xAC) & ~(0xA ^ 0x1B)) == ((0xA8 ^ 0x90) & ~(0x5C ^ 0x64))) continue;
                return;
            }
            if (eq.e(bo.b(2000L) ? 1 : 0)) {
                int n5 = ak[3];
                while (eq.f(n5, ak[1])) {
                    if (eq.b(elArray[n5])) {
                        int n6 = elArray[n5].n;
                        ec.n().en[n6] = null;
                    }
                    ++n5;
                    "".length();
                    if (null == null) continue;
                    return;
                }
            }
        }
    }

    public static boolean e() {
        int n2;
        Calendar calendar;
        if (eq.b(a) && eq.e((calendar = fo.a()).get(ak[0]), e) && eq.e(calendar.get(ak[1]), d)) {
            n2 = ak[2];
            "".length();
            if (null != null) {
                return ((0xB5 ^ 0xA8 ^ (0x17 ^ 0x5C)) & (104 + 186 - 261 + 200 ^ 85 + 29 - 82 + 147 ^ -" ".length())) != 0;
            }
        } else {
            n2 = ak[3];
        }
        return n2 != 0;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static void f() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            String string;
            String string2;
            String string3;
            if (eq.a((Object)a)) {
                string3 = "";
                "".length();
                if ((102 + 29 - 23 + 76 ^ 173 + 175 - 332 + 172) <= ((23 + 30 - 39 + 142 ^ 1 + 39 - -78 + 26) & (98 + 59 - 48 + 46 ^ 108 + 60 - 165 + 148 ^ -" ".length()))) {
                    return;
                }
            } else {
                string3 = a;
            }
            dataOutputStream.writeUTF(string3);
            if (eq.a((Object)ed.c)) {
                string2 = "";
                "".length();
                if (((67 + 134 - 198 + 217 ^ 80 + 82 - 103 + 74) & (0x7D ^ 0x47 ^ (5 ^ 0x66) ^ -" ".length())) != 0) {
                    return;
                }
            } else {
                string2 = ed.c;
            }
            dataOutputStream.writeUTF(string2);
            if (eq.a((Object)ed.a)) {
                string = "";
                "".length();
                if ((0xD8 ^ 0xC6 ^ (5 ^ 0x1F)) < 0) {
                    return;
                }
            } else {
                string = ed.a;
            }
            dataOutputStream.writeUTF(string);
            dataOutputStream.writeByte(e);
            dataOutputStream.writeByte(d);
            dataOutputStream.writeInt(b);
            dataOutputStream.writeByte(ah);
            dataOutputStream.flush();
            byteArrayOutputStream.flush();
            an.a("V6Rec", byteArrayOutputStream.toByteArray());
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (null != null) {
            return;
        }
    }

    public static void n() {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(an.b("V6Rec"));
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            a = dataInputStream.readUTF();
            if (eq.e(a.equals("") ? 1 : 0)) {
                a = null;
            }
            if (eq.e((ed.c = dataInputStream.readUTF()).equals("") ? 1 : 0)) {
                ed.c = null;
            }
            if (eq.e((ed.a = dataInputStream.readUTF()).equals("") ? 1 : 0)) {
                ed.a = null;
            }
            e = dataInputStream.readByte();
            d = dataInputStream.readByte();
            b = dataInputStream.readInt();
            ah = dataInputStream.readByte();
            dataInputStream.close();
            byteArrayInputStream.close();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (null != null) {
            return;
        }
    }

    static {
        eq.p();
        eq.n();
    }

    private static void p() {
        ak = new int[9];
        eq.ak[0] = 0xD0 ^ 0xB1 ^ (0xC5 ^ 0xAF);
        eq.ak[1] = 0x68 ^ 0x13 ^ (0x64 ^ 0x13);
        eq.ak[2] = " ".length();
        eq.ak[3] = (0x1F ^ 0x33 ^ (0x98 ^ 0x90)) & (0x3B ^ 0x79 ^ (0x24 ^ 0x42) ^ -" ".length());
        eq.ak[4] = -" ".length();
        eq.ak[5] = 0x33 ^ 0x3B ^ (0xB3 ^ 0x89);
        eq.ak[6] = 0xFFFF85DE & 0x7FFD;
        eq.ak[7] = -(0xFFFFC8DF & 0x3F22) & (0xFFFFEBED & 0x1FFB);
        eq.ak[8] = "  ".length();
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean b(Object object) {
        return object != null;
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }
}

