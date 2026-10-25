/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import javax.microedition.lcdui.Image;

public final class dz {
    public ad a = new ad();
    private byte l;
    public int b;
    public bd[] c;
    private boolean m;
    public boolean d;
    private byte n;
    private static final int[] o;
    private long p;
    private boolean q;
    public byte[] e;
    public short f;
    public int g;
    public int h;
    public long i;
    public byte[][] j;
    private byte r;
    public byte[] k;

    public final void a(bh bh2, int n2, int n3) {
        try {
            if (dz.b(this.d() ? 1 : 0)) {
                return;
            }
            if (dz.c(this.q ? 1 : 0)) {
                return;
            }
            if (dz.a(this.l, this.a.size())) {
                ad ad2 = ((bn)this.a.elementAt((int)this.l)).a;
                int n4 = o[1];
                while (dz.a(n4, ad2.size())) {
                    af af2 = (af)ad2.elementAt(n4);
                    bd bd2 = this.c[af2.b];
                    ct ct2 = f.a(this.f);
                    if (dz.a(ct2) && dz.a(ct2.b)) {
                        int n5;
                        short s2 = af2.c;
                        int n6 = bd2.e;
                        int n7 = bd2.a;
                        int n8 = bd2.b;
                        int n9 = bd2.c;
                        if (dz.b(n8, ct2.b.getWidth())) {
                            n8 = o[1];
                        }
                        if (dz.b(n9, ct2.b.getHeight())) {
                            n9 = o[1];
                        }
                        if (dz.b(n8 + n6, ct2.b.getWidth())) {
                            n6 = ct2.b.getWidth() - n8;
                        }
                        if (dz.b(n9 + n7, ct2.b.getHeight())) {
                            n7 = ct2.b.getHeight() - n9;
                        }
                        Image image = ct2.b;
                        if (dz.c(af2.e, o[2])) {
                            n5 = o[5];
                            "".length();
                            if ("   ".length() <= -" ".length()) {
                                return;
                            }
                        } else {
                            n5 = o[1];
                        }
                        bh2.a(image, n8, n9, n6, n7, n5, n2 + s2, n3 + af2.a, o[1]);
                    }
                    ++n4;
                    "".length();
                    if ((0x26 ^ 0x23 ^ " ".length()) >= ((0x4D ^ 0x66 ^ (0xC0 ^ 0xA9)) & (126 + 46 - 155 + 117 ^ 191 + 153 - 318 + 170 ^ -" ".length()))) continue;
                    return;
                }
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Err DataSkillEff  paintBottomEff:" + this.f);
        }
        if (-(0xA1 ^ 0xA4) >= 0) {
            return;
        }
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void a() {
        if (dz.d(this.a.size())) {
            try {
                if (dz.b(this.q ? 1 : 0)) {
                    switch (this.n) {
                        case 0: {
                            this.r = (byte)(this.r + o[2]);
                            if (dz.b(this.r, this.e.length)) {
                                this.d = o[2];
                                this.r = (byte)o[1];
                            }
                            this.l = this.e[this.r];
                            return;
                        }
                        case 1: {
                            this.r = (byte)(this.r + o[2]);
                            if (dz.b(this.r, this.e.length)) {
                                this.r = (byte)o[1];
                                this.d = o[2];
                            }
                            this.l = this.e[this.r];
                            return;
                        }
                        case 2: {
                            this.r = (byte)(this.r + o[2]);
                            if (dz.c(this.r, (byte)(this.e.length - o[2])) && dz.a(dz.a(this.i - System.currentTimeMillis(), 0L))) {
                                this.d = o[2];
                            }
                            if (dz.b(this.r, this.e.length)) {
                                this.r = (byte)o[1];
                                this.a(o[2]);
                            }
                            this.l = this.e[this.r];
                            return;
                        }
                        case 3: {
                            this.r = (byte)(this.r + o[2]);
                            if (dz.b(this.r, this.e.length)) {
                                this.r = (byte)o[1];
                                this.a(o[2]);
                            }
                            this.l = this.e[this.r];
                            return;
                        }
                    }
                    return;
                }
                if (dz.a(dz.a(this.p - System.currentTimeMillis(), 0L))) {
                    this.a(o[1]);
                    return;
                }
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ("   ".length() > (105 + 139 - 69 + 7 ^ 109 + 62 - 84 + 91)) {
                return;
            }
        }
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(int n2) {
        return n2 < 0;
    }

    static {
        dz.c();
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public dz() {
        new ad();
        "".length();
        this.j = new byte[o[0]][];
        new ad();
        "".length();
        this.f = (short)o[1];
        this.n = (byte)o[1];
        this.k = new byte[o[0]];
        new ad();
        "".length();
    }

    private void b() {
        try {
            m m2 = (m)f.b.get("" + this.f);
            if (dz.b(m2)) {
                DataInputStream dataInputStream = fo.a("/effdata/" + this.f);
                if (dz.a(dataInputStream)) {
                    byte[] byArray = new byte[(short)dataInputStream.available()];
                    dataInputStream.read(byArray, o[1], byArray.length);
                    "".length();
                    this.a(byArray);
                    return;
                }
                f.b(this.f);
                "".length();
                return;
            }
            if (dz.a(m2) && dz.c(m2.g ? 1 : 0)) {
                this.a = m2.e;
                this.c = m2.a;
                this.b = m2.h;
                this.h = m2.f;
                this.e = m2.c;
                this.k = m2.d;
                this.j = m2.b;
                this.m = m2.g;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            f.b(this.f);
            "".length();
        }
        if (-"  ".length() >= 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(byte[] byArray) {
        if (!dz.a(byArray)) return;
        FilterInputStream filterInputStream = null;
        try {
            block22: {
                int n2;
                this.a.removeAllElements();
                this.c = null;
                filterInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                byte by2 = ((DataInputStream)filterInputStream).readByte();
                this.c = new bd[by2];
                int n3 = o[1];
                while (dz.a(n3, by2)) {
                    bd[] bdArray = this.c;
                    ((DataInputStream)filterInputStream).readUnsignedByte();
                    "".length();
                    bdArray[n3] = new bd(((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte(), ((DataInputStream)filterInputStream).readUnsignedByte());
                    ++n3;
                    "".length();
                    if ("   ".length() < (0x8E ^ 0x8A)) continue;
                    return;
                }
                n3 = o[1];
                short s2 = ((DataInputStream)filterInputStream).readShort();
                int n4 = o[1];
                while (dz.a(n4, s2)) {
                    n2 = ((DataInputStream)filterInputStream).readByte();
                    ad ad2 = new ad();
                    ad ad3 = new ad();
                    int n5 = o[1];
                    while (dz.a(n5, n2)) {
                        af af2 = new af(((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readByte());
                        new af(((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readShort(), ((DataInputStream)filterInputStream).readByte()).e = ((DataInputStream)filterInputStream).readByte();
                        af2.d = ((DataInputStream)filterInputStream).readByte();
                        if (dz.b(af2.d)) {
                            ad2.addElement(af2);
                            "".length();
                        } else {
                            ad3.addElement(af2);
                        }
                        if (dz.a(n3, fo.d(af2.a))) {
                            n3 = fo.d(af2.a);
                        }
                        ++n5;
                        "".length();
                        if (-" ".length() <= 0) continue;
                        return;
                    }
                    this.a.addElement(new bn(ad2, ad3));
                    ++n4;
                    "".length();
                    if (((0x5A ^ 0x38 ^ (0xD0 ^ 0x93)) & (117 + 14 - 129 + 152 ^ 169 + 109 - 157 + 66 ^ -" ".length())) < (0x26 ^ 0x4A ^ (1 ^ 0x69))) continue;
                    return;
                }
                this.b = this.c[dz.o[1]].e;
                this.h = (short)n3;
                n4 = (short)((DataInputStream)filterInputStream).readUnsignedByte();
                this.e = new byte[n4];
                n2 = o[1];
                while (dz.a(n2, n4)) {
                    this.e[n2] = (byte)((DataInputStream)filterInputStream).readShort();
                    ++n2;
                    "".length();
                    if (-" ".length() < 0) continue;
                    return;
                }
                ((DataInputStream)filterInputStream).readByte();
                "".length();
                byte by3 = ((DataInputStream)filterInputStream).readByte();
                this.j[dz.o[1]] = new byte[by3];
                n2 = o[1];
                while (dz.a(n2, by3)) {
                    this.j[dz.o[1]][n2] = ((DataInputStream)filterInputStream).readByte();
                    ++n2;
                    "".length();
                }
                by3 = ((DataInputStream)filterInputStream).readByte();
                this.j[dz.o[2]] = new byte[by3];
                n2 = o[1];
                while (dz.a(n2, by3)) {
                    this.j[dz.o[2]][n2] = ((DataInputStream)filterInputStream).readByte();
                    ++n2;
                    "".length();
                    if (((0x5B ^ 0x5E ^ (0x40 ^ 0x5F)) & (0xFA ^ 0xB2 ^ (0x15 ^ 0x47) ^ -" ".length())) <= 0) continue;
                    return;
                }
                by3 = ((DataInputStream)filterInputStream).readByte();
                this.j[dz.o[3]] = new byte[by3];
                n2 = o[1];
                while (dz.a(n2, by3)) {
                    this.j[dz.o[3]][n2] = ((DataInputStream)filterInputStream).readByte();
                    ++n2;
                    "".length();
                    if ("  ".length() >= 0) continue;
                    return;
                }
                this.m = o[2];
                try {
                    this.k[dz.o[1]] = (byte)(this.j[o[1]].length - o[4]);
                    this.k[dz.o[2]] = (byte)(this.j[o[2]].length - o[4]);
                    this.k[dz.o[5]] = (byte)(this.j[o[3]].length - o[4]);
                    this.k[dz.o[3]] = (byte)(this.j[o[3]].length - o[4]);
                    "".length();
                }
                catch (Exception exception) {
                    // empty catch block
                    break block22;
                }
                if ("   ".length() < ((0x8F ^ 0x9F ^ (0x56 ^ 1)) & (0xC0 ^ 0x8D ^ (0xB5 ^ 0xBF) ^ -" ".length()))) {
                    return;
                }
            }
            this.k[dz.o[1]] = ((DataInputStream)filterInputStream).readByte();
            this.k[dz.o[2]] = ((DataInputStream)filterInputStream).readByte();
            this.k[dz.o[5]] = ((DataInputStream)filterInputStream).readByte();
            this.k[dz.o[3]] = this.k[o[5]];
        }
        catch (Exception exception) {
            try {
                filterInputStream.close();
                "".length();
                return;
            }
            catch (Exception exception2) {
                "".length();
                if (" ".length() != (0x4F ^ 0x35 ^ (0x45 ^ 0x3B))) return;
                return;
            }
        }
        catch (Throwable throwable) {
            try {
                filterInputStream.close();
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
                throw throwable;
            }
            if (((0x33 ^ 0x61) & ~(0x2F ^ 0x7D)) <= "  ".length()) throw throwable;
            return;
        }
        try {
            filterInputStream.close();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
            return;
        }
        if (-" ".length() <= ((0x91 ^ 0x9D) & ~(0x8B ^ 0x87))) return;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    private static boolean c(int n2) {
        return n2 != 0;
    }

    public final void b(bh bh2, int n2, int n3) {
        try {
            if (dz.b(this.d() ? 1 : 0)) {
                return;
            }
            if (dz.c(this.q ? 1 : 0)) {
                return;
            }
            if (dz.a(this.l, this.a.size())) {
                ad ad2 = ((bn)this.a.elementAt((int)this.l)).b;
                int n4 = o[1];
                while (dz.a(n4, ad2.size())) {
                    af af2 = (af)ad2.elementAt(n4);
                    bd bd2 = this.c[af2.b];
                    ct ct2 = f.a(this.f);
                    if (dz.a(ct2) && dz.a(ct2.b)) {
                        int n5;
                        short s2 = af2.c;
                        int n6 = bd2.e;
                        int n7 = bd2.a;
                        int n8 = bd2.b;
                        int n9 = bd2.c;
                        if (dz.b(n8, ct2.b.getWidth())) {
                            n8 = o[1];
                        }
                        if (dz.b(n9, ct2.b.getHeight())) {
                            n9 = o[1];
                        }
                        if (dz.b(n8 + n6, ct2.b.getWidth())) {
                            n6 = ct2.b.getWidth() - n8;
                        }
                        if (dz.b(n9 + n7, ct2.b.getHeight())) {
                            n7 = ct2.b.getHeight() - n9;
                        }
                        Image image = ct2.b;
                        if (dz.c(af2.e, o[2])) {
                            n5 = o[5];
                            "".length();
                            if (-" ".length() != -" ".length()) {
                                return;
                            }
                        } else {
                            n5 = o[1];
                        }
                        bh2.a(image, n8, n9, n6, n7, n5, n2 + s2, n3 + af2.a, o[1]);
                    }
                    ++n4;
                    "".length();
                    if (((81 + 42 - 13 + 34 ^ 119 + 64 - 182 + 188) & (74 + 139 - 76 + 11 ^ 44 + 176 - 128 + 93 ^ -" ".length())) <= "  ".length()) continue;
                    return;
                }
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Err DataSkillEff  paintBottomEff:" + this.f);
        }
        if (" ".length() < 0) {
            return;
        }
    }

    private static void c() {
        o = new int[6];
        dz.o[0] = 0x14 ^ 0x10;
        dz.o[1] = (0x26 ^ 0x34 ^ (0x55 ^ 6)) & (7 ^ 0x4A ^ (0x3F ^ 0x33) ^ -" ".length());
        dz.o[2] = " ".length();
        dz.o[3] = "   ".length();
        dz.o[4] = 0xA0 ^ 0xA7;
        dz.o[5] = "  ".length();
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    private boolean d() {
        if (dz.c(this.m ? 1 : 0)) {
            return o[2];
        }
        this.b();
        return o[1];
    }

    public dz(short s2, long l2, int n2) {
        new ad();
        "".length();
        this.j = new byte[o[0]][];
        new ad();
        "".length();
        this.f = (short)o[1];
        this.n = (byte)o[1];
        this.k = new byte[o[0]];
        new ad();
        "".length();
        this.f = s2;
        this.i = l2;
        this.g = n2;
        this.b();
        if (dz.b(dz.b(l2, -1L))) {
            this.n = (byte)o[3];
            "".length();
            if (" ".length() < 0) {
                throw null;
            }
        } else if (dz.b(dz.b(l2, 0L))) {
            this.n = (byte)o[2];
            "".length();
            if (-" ".length() == (0x41 ^ 0x70 ^ (0x5F ^ 0x6A))) {
                throw null;
            }
        } else {
            this.n = (byte)o[5];
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private void a(boolean bl2) {
        if (dz.d(this.g)) {
            this.q = bl2;
            if (dz.c(this.q ? 1 : 0)) {
                this.p = System.currentTimeMillis() + (long)this.g;
                return;
            }
        } else {
            this.q = o[1];
        }
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }
}

