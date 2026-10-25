/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import java.io.InputStream;
import javax.microedition.rms.RecordStore;

public final class an {
    public int a;
    public int b;
    public int c;
    public bf d;
    public static long e;
    private static final int[] l;
    public int f;
    public static int g;
    public int h;
    public int i;
    public static boolean j;
    public String k;

    public final boolean a() {
        int n2;
        if (an.a(this.h) && an.a(ec.l(this.d.d), this.i)) {
            n2 = l[0];
            "".length();
            if (((67 + 21 - 22 + 71 ^ 59 + 82 - -36 + 9) & (0x39 ^ 0x5F ^ (0x5F ^ 0xA) ^ -" ".length())) >= "   ".length()) {
                return ((0x24 ^ 0x52 ^ (0x82 ^ 0xAA)) & (140 + 175 - 160 + 76 ^ 117 + 77 - 23 + 14 ^ -" ".length())) != 0;
            }
        } else {
            n2 = l[1];
        }
        return n2 != 0;
    }

    public static String a(String string) {
        byte[] byArray = an.b(string);
        if (an.a(byArray)) {
            return null;
        }
        try {
            return new String(byArray, "UTF-8");
        }
        catch (Exception exception) {
            return new String(byArray);
        }
    }

    public final int b() {
        int n2;
        if (an.a(this.h) && an.a(ec.l(this.d.d), this.i)) {
            n2 = ec.l(this.d.d) - this.i;
            "".length();
            if (null != null) {
                return (0x80 ^ 0x96) & ~(0x8A ^ 0x9C);
            }
        } else {
            n2 = l[1];
        }
        return n2;
    }

    public final boolean c() {
        int n2;
        if (an.a(this.b) && an.b(ec.l(this.d.d), this.f)) {
            n2 = l[0];
            "".length();
            if ("   ".length() < "  ".length()) {
                return ((0xDB ^ 0xC7 ^ (1 ^ 0x3E)) & (0x95 ^ 0x8C ^ (0x5B ^ 0x61) ^ -" ".length())) != 0;
            }
        } else {
            n2 = l[1];
        }
        return n2 != 0;
    }

    public static byte[] b(String string) {
        try {
            RecordStore recordStore = RecordStore.openRecordStore((String)"vj".concat(String.valueOf(string)), (boolean)l[1]);
            byte[] byArray = recordStore.getRecord(l[0]);
            recordStore.closeRecordStore();
            return byArray;
        }
        catch (Exception exception) {
            return null;
        }
    }

    static {
        an.h();
        ce.a(l[8]);
        an.f();
    }

    private static void e(String string) {
        try {
            RecordStore.deleteRecordStore((String)"vj".concat(String.valueOf(string)));
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (" ".length() >= "  ".length()) {
            return;
        }
    }

    public static void d() {
        an.e("nj_arrow");
        an.e("nj_effect");
        an.e("nj_image");
        an.e("nj_part");
        an.e("nj_skill");
        an.e("data");
        an.e("dataVersion");
        an.e("map");
        an.e("mapVersion");
        an.e("skill");
        an.e("killVersion");
        an.e("item");
        an.e("itemVersion");
    }

    private static boolean a(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public static an a(bf bf2, String string) {
        an an2;
        an an3 = new an();
        new an().d = bf2;
        String[] stringArray = ey.b(string, " ");
        an3.k = stringArray[l[1]];
        try {
            an3.c = Integer.parseInt(stringArray[l[0]]);
            an3.h = Integer.parseInt(stringArray[l[3]]);
            an3.i = Integer.parseInt(stringArray[l[4]]);
            "".length();
        }
        catch (Exception exception) {
            an3.a = l[1];
            an3.b = l[1];
        }
        if (" ".length() == (0x5F ^ 0x1C ^ (0x2F ^ 0x68))) {
            return null;
        }
        try {
            an3.a = Integer.parseInt(stringArray[l[5]]);
            an3.b = Integer.parseInt(stringArray[l[6]]);
            an3.f = Integer.parseInt(stringArray[l[7]]);
            "".length();
        }
        catch (Exception exception) {
            an3.a = l[1];
            an3.b = l[1];
        }
        if (" ".length() != " ".length()) {
            return null;
        }
        if (an.b(an3.h) && an.b(an3.b)) {
            an2 = null;
            "".length();
            if (-"   ".length() > 0) {
                return null;
            }
        } else {
            an2 = an3;
        }
        return an2;
    }

    public static void a(String string, int n2) {
        try {
            byte[] byArray = new byte[l[0]];
            byArray[an.l[1]] = (byte)n2;
            an.a(string, byArray);
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (("  ".length() & ("  ".length() ^ -" ".length())) != 0) {
            return;
        }
    }

    public static InputStream c(String string) {
        return "".getClass().getResourceAsStream(string);
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static void h() {
        l = new int[9];
        an.l[0] = " ".length();
        an.l[1] = (0x79 ^ 0x13 ^ (0x64 ^ 0x57)) & (0x94 ^ 0xA8 ^ (0x25 ^ 0x40) ^ -" ".length());
        an.l[2] = -" ".length();
        an.l[3] = "  ".length();
        an.l[4] = "   ".length();
        an.l[5] = 107 + 39 - 77 + 69 ^ 114 + 141 - 133 + 20;
        an.l[6] = 195 + 143 - 285 + 144 ^ 159 + 180 - 233 + 86;
        an.l[7] = 0x84 ^ 0x82;
        an.l[8] = 0x14 ^ 0x78 ^ (0x50 ^ 0xF);
    }

    public static void a(String string, String string2) {
        try {
            an.a(string, string2.getBytes("UTF-8"));
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (((0x72 ^ 0x66 ^ (0x50 ^ 0x73)) & (9 + 135 - 21 + 51 ^ 127 + 133 - 124 + 17 ^ -" ".length())) > "   ".length()) {
            return;
        }
    }

    public static void e() {
        g = l[1];
        e = 0L;
        j = l[1];
    }

    public static int d(String string) {
        int n2;
        byte[] byArray = an.b(string);
        if (an.a(byArray)) {
            n2 = l[2];
            "".length();
            if (null != null) {
                return (51 + 166 - 29 + 31 ^ 80 + 99 - 44 + 2) & (128 + 76 - 91 + 35 ^ 52 + 16 - -68 + 62 ^ -" ".length());
            }
        } else {
            n2 = byArray[l[1]];
        }
        return n2;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public static void a(String string, byte[] byArray) {
        try {
            RecordStore recordStore = RecordStore.openRecordStore((String)"vj".concat(String.valueOf(string)), (boolean)l[0]);
            if (an.a(recordStore.getNumRecords())) {
                recordStore.setRecord(l[0], byArray, l[1], byArray.length);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                recordStore.addRecord(byArray, l[1], byArray.length);
                "".length();
            }
            recordStore.closeRecordStore();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (-" ".length() != -" ".length()) {
            return;
        }
    }

    public static void f() {
    }

    public final String g() {
        return this.k + " " + this.c + " " + this.h + " " + this.i + " " + this.a + " " + this.b + " " + this.f;
    }
}

