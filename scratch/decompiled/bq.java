/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class bq
extends cl {
    private static Image e;
    private ad f;
    private int g;
    private static Image h;
    private static al i;
    private static al j;
    private byte k = (byte)l[0];
    private static final int[] l;

    private void a(ch ch2) {
        ch2.c = (ap.fb - l[4] + fo.c((em.aj + l[5]) / l[3]) * l[3]) * l[1];
        ch2.b = (ap.s - em.m + fo.c(em.aq / l[3]) * l[3]) * l[1];
        if (bq.e(this.k, l[6])) {
            ch2.d = fo.c(l[6]);
            "".length();
            if ("  ".length() == 0) {
                return;
            }
        } else {
            ch2.d = fo.c(l[7]);
        }
    }

    public bq(byte by2, int n2) {
        this.g = l[0];
        this.f = new ad();
        this.k = by2;
        this.g = n2;
        switch (by2) {
            case 1: {
                n2 = l[1];
                if (bq.a(i)) {
                    i = new al(h, l[2], l[1]);
                }
            }
            default: {
                "".length();
                if ("   ".length() >= 0) break;
                throw null;
            }
            case 3: {
                if (!bq.a(j)) break;
                j = new al(e, l[3], l[3]);
            }
        }
        int n3 = l[0];
        while (bq.a(n3, n2)) {
            ch ch2 = new ch((ap.fb - l[4] + fo.c((em.aj + l[5]) / l[3]) * l[3]) * l[1], (ap.s - l[4] + fo.c(em.aq / l[3]) * l[3]) * l[1]);
            if (bq.e(by2, l[6])) {
                ch2.d = fo.c(l[6]);
                "".length();
                if (((0x1F ^ 0x7A ^ (0xC1 ^ 0x87)) & (0x5B ^ 0x1A ^ (0x3B ^ 0x59) ^ -" ".length())) < ((0xC8 ^ 0xBE ^ (0x16 ^ 0x46)) & (79 + 179 - 161 + 90 ^ 116 + 94 - 143 + 90 ^ -" ".length()))) {
                    throw null;
                }
            } else {
                ch2.d = fo.c(l[7]);
            }
            ch2.a = l[2] + (fo.c(l[6]) << l[8]);
            ch2.e = fo.a(l[9], l[10]);
            ch2.f = fo.c(ch2.a);
            this.f.addElement(ch2);
            ++n3;
            "".length();
            if ("   ".length() > 0) continue;
            throw null;
        }
    }

    static {
        bq.a();
        e = em.a("/u/tuyet.png");
        h = em.a("/u/cobay.png");
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    private static void a() {
        l = new int[15];
        bq.l[0] = "  ".length() & ("  ".length() ^ -" ".length());
        bq.l[1] = 0x67 ^ 0x6D;
        bq.l[2] = 0x70 ^ 0x60;
        bq.l[3] = 0xC7 ^ 0x8E ^ (0x1D ^ 0x51);
        bq.l[4] = 0x3D ^ 0x35 ^ (0x4B ^ 0x57);
        bq.l[5] = 0xA8 ^ 0x80;
        bq.l[6] = "   ".length();
        bq.l[7] = 0xEA ^ 0xB4 ^ (0x5D ^ 7);
        bq.l[8] = "  ".length();
        bq.l[9] = -" ".length();
        bq.l[10] = " ".length();
        bq.l[11] = -((0x98 ^ 0x81) + (0x29 ^ 0x3D) - (0x75 ^ 0x63) + (167 + 43 - 35 + 2));
        bq.l[12] = 66 + 108 - 173 + 199;
        bq.l[13] = 0x1A ^ 0x62;
        bq.l[14] = 0x74 ^ 0x6A;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public final void c() {
        switch (this.k) {
            case 1: {
                bq bq2 = this;
                int n2 = l[0];
                while (bq.a(n2, bq2.g)) {
                    ch ch2;
                    ch ch3 = ch2 = (ch)bq2.f.elementAt(n2);
                    ch3.b += l[1];
                    ch2.c += ch2.e * l[1];
                    if (bq.b(ch2.f += l[10], ch2.a)) {
                        ch2.f = l[0];
                    }
                    if (!bq.c(ch2.b / l[1], ap.s + em.aq - (l[6] - ch2.d) * l[5]) || !bq.b(ch2.c / l[1], ap.fb - em.r) || bq.d(ch2.c / l[1], ap.fb + em.aj + em.r)) {
                        bq2.a(ch2);
                    }
                    ++n2;
                    "".length();
                    if (-"   ".length() <= 0) continue;
                    return;
                }
                return;
            }
            default: {
                return;
            }
            case 3: 
        }
        bq bq3 = this;
        int n3 = l[0];
        while (bq.a(n3, bq3.g)) {
            int n4;
            ch ch4;
            ch ch5 = ch4 = (ch)bq3.f.elementAt(n3);
            ch5.b += (ch4.d + l[10]) * l[3];
            ch4.c += ch4.d + l[10] << l[10];
            if (bq.d(ch4.b / l[1], ap.s + em.aq - (l[6] - ch4.d) * l[14])) {
                bq3.a(ch4);
            }
            if (bq.a(ch4.c / l[1] + (n4 = ap.fb * (l[8] - ch4.d) * l[4] / l[13]), ap.fb - l[1])) {
                ch4.c += (em.aj + l[4]) * l[1];
            }
            if (bq.d(ch4.c / l[1] + n4, ap.fb + em.aj + l[1])) {
                ch4.c -= (em.aj + l[4]) * l[1];
            }
            ++n3;
            "".length();
            if ("   ".length() >= " ".length()) continue;
            return;
        }
    }

    public final void a(bh bh2) {
        bh2.a(-bh2.a(), -bh2.b());
        bh2.a(l[0], l[11], em.aj, l[12] + em.aq);
        switch (this.k) {
            case 1: {
                int n2 = l[0];
                while (bq.a(n2, this.g)) {
                    ch ch2 = (ch)this.f.elementAt(n2);
                    i.a(ch2.f / (ch2.a / l[7]), ch2.c / l[1] - ap.fb, ch2.b / l[1] - ap.s, l[0], l[6], bh2);
                    ++n2;
                    "".length();
                    if (" ".length() >= 0) continue;
                    return;
                }
                return;
            }
            default: {
                return;
            }
            case 3: 
        }
        int n3 = l[0];
        while (bq.a(n3, this.g)) {
            ch ch3 = (ch)this.f.elementAt(n3);
            if (bq.a(ch3.d)) {
                if (bq.a(fj.h)) {
                    fj.h = new fj();
                }
                int n4 = fj.h.g * (l[8] - ch3.d) * l[4] / l[13] - ap.fb;
                j.a(ch3.d, n4 + ch3.c / l[1], ch3.b / l[1] - ap.s, l[8], l[0], bh2);
            }
            ++n3;
            "".length();
            if (null == null) continue;
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 >= n3;
    }

    private static boolean c(int n2, int n3) {
        return n2 <= n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    private static boolean e(int n2, int n3) {
        return n2 == n3;
    }
}

