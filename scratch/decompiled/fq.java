/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public final class fq
implements Runnable {
    private static final int[] a;
    private dp b;

    private static boolean a(int n2) {
        return n2 > 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    static {
        fq.a();
    }

    fq(dp dp2) {
        this.b = dp2;
    }

    private static void a() {
        a = new int[12];
        fq.a[0] = -(33 + 137 - 47 + 28 ^ 46 + 25 - 15 + 127);
        fq.a[1] = 155 + 119 - 177 + 64 + (0x55 ^ 0x1A) - (0x3A ^ 0x7F) + (0x49 ^ 0x1D);
        fq.a[2] = 0x56 ^ 0x3F ^ (0xF2 ^ 0x83);
        fq.a[3] = 0x38 ^ 0x74 ^ (0xEB ^ 0xB7);
        fq.a[4] = 0xBE ^ 0x96 ^ (0x1A ^ 0x3A);
        fq.a[5] = (0x4B ^ 6) & ~(0xF4 ^ 0xB9);
        fq.a[6] = -" ".length();
        fq.a[7] = 9 ^ 0xC;
        fq.a[8] = 0xFFFF9F6A & 0x6495;
        fq.a[9] = 0xDA ^ 0xB0 ^ (0xC ^ 0);
        fq.a[10] = -(0xDD ^ 0xC6);
        fq.a[11] = " ".length();
    }

    public final void run() {
        block20: {
            try {
                while (fq.b(this.b.n ? 1 : 0)) {
                    int n2;
                    Object object;
                    int n3;
                    int n4;
                    byte by2;
                    byte by3;
                    byte by4 = this.b.g.readByte();
                    if (fq.b(dp.h(this.b) ? 1 : 0)) {
                        by4 = dp.a(this.b, by4);
                    }
                    if (fq.c(by4, a[0])) {
                        by4 = this.b.g.readByte();
                        if (fq.b(dp.h(this.b) ? 1 : 0)) {
                            by4 = dp.a(this.b, by4);
                        }
                        by3 = dp.a(this.b, this.b.g.readByte());
                        by2 = dp.a(this.b, this.b.g.readByte());
                        byte by5 = dp.a(this.b, this.b.g.readByte());
                        n4 = dp.a(this.b, this.b.g.readByte());
                        n3 = (by3 & a[1]) << a[2] | (by2 & a[1]) << a[3] | (by5 & a[1]) << a[4] | n4 & a[1];
                        "".length();
                        if ("  ".length() == "   ".length()) {
                            return;
                        }
                    } else if (fq.b(dp.h(this.b) ? 1 : 0)) {
                        by3 = this.b.g.readByte();
                        by2 = this.b.g.readByte();
                        n3 = (dp.a(this.b, by3) & a[1]) << a[4] | dp.a(this.b, by2) & a[1];
                        "".length();
                        if (-" ".length() != -" ".length()) {
                            return;
                        }
                    } else {
                        n3 = this.b.g.readUnsignedShort();
                    }
                    byte[] byArray = new byte[n3];
                    n4 = a[5];
                    int n5 = a[5];
                    while (fq.a(n5, a[6]) && fq.b(n4, n3)) {
                        n5 = this.b.g.read(byArray, n4, n3 - n4);
                        if (!fq.a(n5)) continue;
                        object = this.b;
                        ((dp)object).e += (n4 += n5) + a[7];
                        n2 = this.b.e + this.b.o;
                        this.b.j = n2 / a[8] + "." + n2 % a[8] / a[9] + "Kb";
                        "".length();
                        if (null == null) continue;
                        return;
                    }
                    if (fq.b(dp.h(this.b) ? 1 : 0)) {
                        n2 = a[5];
                        while (fq.b(n2, byArray.length)) {
                            byArray[n2] = dp.a(this.b, byArray[n2]);
                            ++n2;
                            "".length();
                            if (-"   ".length() < 0) continue;
                            return;
                        }
                    }
                    this.b.l = System.currentTimeMillis();
                    object = new eh(by4, byArray);
                    if (fq.c(((eh)object).c, a[10])) {
                        this.a((eh)object);
                        "".length();
                        if (null != null) {
                            return;
                        }
                    } else {
                        this.b.c.a((eh)object);
                    }
                    "".length();
                    if ("  ".length() >= 0) continue;
                    return;
                }
                "".length();
            }
            catch (Exception exception) {
                if (!fq.b(this.b.n ? 1 : 0)) break block20;
                if (fq.a(this.b.p)) {
                    this.b.e();
                }
                if (!fq.a(this.b.c)) break block20;
                if (fq.a(fq.a(System.currentTimeMillis() - dp.b(this.b), 500L))) {
                    this.b.c.a();
                    return;
                }
                this.b.c.c();
            }
            if ((0x23 ^ 0x27) == "   ".length()) {
                return;
            }
        }
    }

    private void a(eh eh2) {
        try {
            byte by2 = eh2.a.readByte();
            this.b.b = new byte[by2];
            int n2 = a[5];
            while (fq.b(n2, by2)) {
                this.b.b[n2] = eh2.a.readByte();
                ++n2;
                "".length();
                if ((6 ^ 3) > 0) continue;
                return;
            }
            n2 = a[5];
            while (fq.b(n2, this.b.b.length - a[11])) {
                byte[] byArray = this.b.b;
                int n3 = n2 + a[11];
                byArray[n3] = (byte)(byArray[n3] ^ this.b.b[n2]);
                ++n2;
                "".length();
                if ((0x39 ^ 0x33 ^ (0x64 ^ 0x6A)) > "  ".length()) continue;
                return;
            }
            dp.i(this.b);
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if ((0xA1 ^ 0xA5) != (0xBA ^ 0xBE)) {
            return;
        }
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }
}

