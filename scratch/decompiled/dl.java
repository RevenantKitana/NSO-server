/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
import java.io.IOException;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public final class dl {
    private static final int[] b;
    private static dl c;
    public bv a = dp.g();
    private int d;
    private int e;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[18]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x66 ^ 0x59 ^ (0x9C ^ 0x98)) & (0x63 ^ 0x3A ^ (0xE9 ^ 0x8B) ^ -" ".length())) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void a() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[101]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-(3 + 28 - -82 + 54 ^ 161 + 151 - 212 + 63) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void a(String string) {
        try {
            eh eh2 = dl.b(b[7]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (null != null) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[22]);
            eh2.d.writeByte(n2);
            if (dl.o(n3, b[10])) {
                eh2.d.writeInt(n3);
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((51 + 37 - -16 + 37 ^ 127 + 94 - 185 + 101) < (106 + 86 - 57 + 9 ^ 37 + 109 - 3 + 5)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(byte by2, el el2, el el3, el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[135]);
            eh2.d.writeByte(by2);
            if (dl.an(by2)) {
                eh2.d.writeByte(el2.n);
                eh2.d.writeByte(el3.n);
                int n2 = b[4];
                while (dl.p(n2, elArray.length)) {
                    if (dl.a(elArray[n2])) {
                        eh2.d.writeByte(elArray[n2].n);
                    }
                    ++n2;
                    "".length();
                    if ((0xC0 ^ 0xB2 ^ (6 ^ 0x70)) >= (78 + 3 - 77 + 125 ^ 75 + 47 - 81 + 92)) continue;
                    return;
                }
            } else if (dl.n(by2, b[10])) {
                eh2.d.writeByte(el3.n);
                int n3 = b[4];
                while (dl.p(n3, elArray.length)) {
                    if (dl.a(elArray[n3])) {
                        eh2.d.writeByte(elArray[n3].n);
                    }
                    ++n3;
                    "".length();
                    if (" ".length() != 0) continue;
                    return;
                }
            } else if (!dl.m(by2, b[51]) || dl.n(by2, b[136])) {
                eh2.d.writeByte(el3.n);
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() < 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[32]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xEE ^ 0xB2) & ~(0xD1 ^ 0x8D)) > "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(boolean bl2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[80]);
            eh2.d.writeBoolean(bl2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() != "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean an(int n2) {
        return n2 == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[35]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((86 + 59 - 108 + 109 ^ 82 + 143 - 94 + 48) & (0xA ^ 0x64 ^ (0xE7 ^ 0xA8) ^ -" ".length())) < 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void f(el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[121]);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[122]) || dl.n(elArray[n2].m.d, b[20]))) {
                    eh2.d.writeByte(elArray[n2].n);
                    "".length();
                    if ("  ".length() < "   ".length()) break;
                    return;
                }
                ++n2;
                "".length();
                if ("  ".length() >= 0) continue;
                return;
            }
            n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[123]) || dl.n(elArray[n2].m.d, b[124]))) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (((0x32 ^ 0x5B ^ (0x2C ^ 0x69)) & (87 + 15 - 98 + 157 ^ 28 + 136 - 116 + 93 ^ -" ".length())) != (30 + 131 - 52 + 51 ^ 97 + 107 - 77 + 37)) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean m(int n2, int n3) {
        return n2 != n3;
    }

    private void ah() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[85]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(int n2, int n3, int n4) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[79]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            eh2.d.writeByte(n4);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x75 ^ 0x71) != (0x14 ^ 0x10)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(short s2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[130]);
            eh2.d.writeByte(s2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(el[] elArray) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[39]);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() == (0x18 ^ 0x1C)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(String string, int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[107]);
            eh2.d.writeUTF(string);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() <= " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[74]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xDE ^ 0xC7 ^ (0xA4 ^ 0xB1)) & (0x55 ^ 0x21 ^ (0x76 ^ 0xE) ^ -" ".length())) > "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void b() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[64]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x86 ^ 0x88 ^ (0x7D ^ 0x77)) < 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[90]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() == " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[60]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() <= -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void c() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[78]);
            eh2.d.writeByte(ec.n().cy);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[112]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(short s2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[138]);
            eh2.d.writeByte(b[10]);
            eh2.d.writeShort(s2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x71 ^ 0x53 ^ (0xBC ^ 0x8A)) & (0x11 ^ 0x2D ^ (0x8B ^ 0xA3) ^ -" ".length())) > " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ao(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[138]);
            eh2.d.writeByte(b[51]);
            eh2.d.writeShort(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() == 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[97]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean ap(int n2) {
        return n2 != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[13]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(byte by2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[133]);
            eh2.d.writeByte(b[50]);
            eh2.d.writeByte(by2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(el el2, el[] elArray) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[38]);
            eh2.d.writeBoolean(b[4]);
            eh2.d.writeByte(el2.n);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if ("   ".length() != 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-(0xC1 ^ 0xC5) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void d() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[98]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void e(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[24]);
            eh2.d.writeShort(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aq(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[12]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() <= -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(String string, String string2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[93]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x9D ^ 0x83 ^ (0x9A ^ 0xAB)) & (0x7E ^ 0x33 ^ (0x24 ^ 0x46) ^ -" ".length())) >= "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[12]);
            eh2.d.writeShort(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(el el2, el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[125]);
            eh2.d.writeByte(el2.n);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[123]) || !dl.m(elArray[n2].m.d, b[124]) || dl.n(elArray[n2].m.d, b[126]))) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (((0x5F ^ 0x3F) & ~(0xD2 ^ 0xB2)) == 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void e(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[82]);
            eh2.d.writeByte(n2);
            eh2.d.writeInt(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void f(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[43]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((153 + 129 - 270 + 143 ^ 42 + 31 - 13 + 99) < "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void g(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[44]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((95 + 48 - -15 + 70 ^ 6 + 114 - 74 + 135) & (0xF ^ 0x64 ^ (0xB5 ^ 0x8F) ^ -" ".length())) >= "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void e() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[111]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xA4 ^ 0xAC) & ~(7 ^ 0xF)) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(String string, int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[96]);
            eh2.d.writeInt(n2);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(short s2, String string) {
        block8: {
            block7: {
                eh eh2 = null;
                try {
                    eh2 = new eh(b[31]);
                    eh2.d.writeShort(s2);
                    eh2.d.writeUTF(string);
                    this.a.a(eh2);
                    "".length();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    "".length();
                    if (null != null) {
                        return;
                    }
                    break block7;
                }
                finally {
                    eh2.c();
                }
                if ("  ".length() <= -" ".length()) {
                    return;
                }
                break block8;
            }
            return;
        }
        System.out.println("" + s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void h(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[104]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xD8 ^ 0xBF ^ (0x58 ^ 0x7C)) & (0xB2 ^ 0xB4 ^ (0x2A ^ 0x6F) ^ -" ".length())) > " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(short s2, String string, byte by2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[31]);
            eh2.d.writeShort(s2);
            eh2.d.writeUTF(string);
            eh2.d.writeByte(by2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0xB0 ^ 0xB4) <= ((0xBF ^ 0xB9) & ~(0x5B ^ 0x5D))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean n(int n2, int n3) {
        return n2 == n3;
    }

    public final void f() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[116]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x50 ^ 0x55) == 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void g() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[58]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() != " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public static dl h() {
        if (dl.b(c)) {
            c = new dl();
        }
        return c;
    }

    public final void i() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[81]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    static eh b(byte by2) {
        eh eh2 = new eh(b[0]);
        try {
            eh2.d.writeByte(by2);
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (-(0x69 ^ 0x6D) >= 0) {
            return null;
        }
        return eh2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void i(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[77]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() == "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void s(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[92]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0xA5 ^ 0xA1) <= " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void j() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[65]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() <= ((207 + 147 - 206 + 84 ^ 13 + 83 - 85 + 156) & (2 ^ 0x2B ^ (0xB ^ 0x6D) ^ -" ".length()))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void k() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[26]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0xC3 ^ 0xC7) <= -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void l() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[25]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() > "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(short s2, String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[6]);
            eh2.d.writeUTF(string);
            eh2.d.writeShort(s2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[39]);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (((0x80 ^ 0xAF) & ~(0xB8 ^ 0x97)) == 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x84 ^ 0x81) == 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ar(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[45]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void e(String string) {
        int n2;
        Object object;
        fa.c();
        "".length();
        if (dl.ap(string.equals("cd") ? 1 : 0)) {
            object = fa.c;
            Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)((dj)object).a);
            n2 = b[10];
            "".length();
            if (null != null) {
                return;
            }
        } else if (dl.ap(string.equals("atc") ? 1 : 0)) {
            String string2;
            int n3;
            if (dl.an(cp.b ? 1 : 0)) {
                n3 = b[10];
                "".length();
                if (((0x21 ^ 0x44 ^ (0x7E ^ 0x12)) & (0x50 ^ 0x61 ^ (0x7C ^ 0x44) ^ -" ".length())) < 0) {
                    return;
                }
            } else {
                n3 = b[4];
            }
            cp.b = n3;
            new Thread(new cp()).start();
            StringBuffer stringBuffer = new StringBuffer().append("Auto chat: ");
            if (dl.ap(cp.b ? 1 : 0)) {
                string2 = "B\u1eadt";
                "".length();
                if (((0x4B ^ 0x79) & ~(0xBB ^ 0x89)) != 0) {
                    return;
                }
            } else {
                string2 = "T\u1eaft";
            }
            ap.a(stringBuffer.append(string2).toString());
            n2 = b[10];
            "".length();
            if (null != null) {
                return;
            }
        } else if (dl.ap(string.equals("hsl") ? 1 : 0)) {
            int n4;
            if (dl.an(fa.b ? 1 : 0)) {
                n4 = b[10];
                "".length();
                if ("   ".length() < "   ".length()) {
                    return;
                }
            } else {
                fa.b = b[4];
                n4 = fa.b ? 1 : 0;
            }
            if (dl.ap(n4)) {
                an.a("hsl", b[10]);
                ap.a("H\u1ed3i sinh l\u01b0\u1ee3ng: B\u1eadt");
                "".length();
                if ("   ".length() == 0) {
                    return;
                }
            } else {
                an.a("hsl", b[53]);
                ap.a("H\u1ed3i sinh l\u01b0\u1ee3ng: T\u1eaft");
            }
            n2 = b[10];
            "".length();
            if (-" ".length() >= (61 + 70 - 93 + 92 ^ 51 + 0 - -3 + 80)) {
                return;
            }
        } else if (dl.ap(string.equals("locdo") ? 1 : 0)) {
            int n5;
            if (dl.an(o.b ? 1 : 0)) {
                n5 = b[10];
                "".length();
                if (" ".length() >= (0x4A ^ 0x3A ^ (0xB5 ^ 0xC1))) {
                    return;
                }
            } else {
                o.b = b[4];
                n5 = o.b ? 1 : 0;
            }
            if (dl.ap(n5)) {
                an.a("locdo", b[10]);
                ap.a("L\u1ecdc \u0111\u1ed3: B\u1eadt");
                "".length();
                if (" ".length() < 0) {
                    return;
                }
            } else {
                an.a("locdo", b[53]);
                ap.a("L\u1ecdc \u0111\u1ed3: T\u1eaft");
            }
            n2 = b[10];
            "".length();
            if (-(0xB9 ^ 0x97 ^ (0x5E ^ 0x75)) >= 0) {
                return;
            }
        } else if (dl.ap(string.equals("dv") ? 1 : 0)) {
            int n6;
            if (dl.an(fa.g ? 1 : 0)) {
                n6 = b[10];
                "".length();
                if (((0xC2 ^ 0xC7) & ~(0xA2 ^ 0xA7)) != 0) {
                    return;
                }
            } else {
                fa.g = b[4];
                n6 = fa.g ? 1 : 0;
            }
            if (dl.ap(n6)) {
                an.a("adv", b[10]);
                ap.a("T\u1ef1 \u0111i l\u00e0m danh v\u1ecdng: B\u1eadt");
                "".length();
                if (((0x7C ^ 0x69) & ~(0x1F ^ 0xA)) != ((0xAF ^ 0x88) & ~(0x6B ^ 0x4C))) {
                    return;
                }
            } else {
                an.a("adv", b[53]);
                ap.a("T\u1ef1 \u0111i l\u00e0m danh v\u1ecdng: T\u1eaft");
            }
            n2 = b[10];
            "".length();
            if (-" ".length() == "   ".length()) {
                return;
            }
        } else if (dl.ap(string.equals("glv") ? 1 : 0)) {
            int n7;
            if (dl.an(ey.k ? 1 : 0)) {
                n7 = b[10];
                "".length();
                if ("  ".length() < "  ".length()) {
                    return;
                }
            } else {
                ey.k = b[4];
                n7 = ey.k ? 1 : 0;
            }
            if (dl.ap(n7)) {
                an.a("glv", b[10]);
                ap.a("T\u1ef1 \u0111i pk \u00e2m: B\u1eadt");
                "".length();
                if (" ".length() == 0) {
                    return;
                }
            } else {
                an.a("glv", b[53]);
                ap.a("T\u1ef1 \u0111i pk \u00e2m: T\u1eaft");
            }
            n2 = b[10];
            "".length();
            if (-" ".length() > "   ".length()) {
                return;
            }
        } else if (dl.ap(string.equals("pk") ? 1 : 0)) {
            String string3;
            int n8;
            if (dl.an(ey.am ? 1 : 0)) {
                n8 = b[10];
                "".length();
                if (-"   ".length() > 0) {
                    return;
                }
            } else {
                n8 = b[4];
            }
            ey.am = n8;
            StringBuffer stringBuffer = new StringBuffer().append("Auto pk: ");
            if (dl.ap(ey.am ? 1 : 0)) {
                string3 = "B\u1eadt";
                "".length();
                if (((58 + 71 - 100 + 107 ^ 125 + 13 - -39 + 12) & (45 + 26 - 20 + 92 ^ 112 + 165 - 221 + 130 ^ -" ".length())) > 0) {
                    return;
                }
            } else {
                string3 = "T\u1eaft";
            }
            ap.a(stringBuffer.append(string3).toString());
            n2 = b[10];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            n2 = b[4];
        }
        if (dl.an(n2)) {
            object = null;
            try {
                object = new eh(b[61]);
                ((eh)object).d.writeUTF(string);
                this.a.a((eh)object);
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                "".length();
                if ("  ".length() < " ".length()) {
                    return;
                }
            }
            finally {
                ((eh)object).c();
            }
        }
    }

    public final void m() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[100]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() == "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void n() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[105]);
            eh2.d.writeByte(ap.dz);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() < ((0x60 ^ 0x27) & ~(0xD4 ^ 0x93))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean as(int n2) {
        return n2 > 0;
    }

    public final void f(int n2, int n3) {
        try {
            if (!dl.an(n2 - this.d) || dl.ap(n3 - this.e)) {
                eh eh2 = new eh(b[10]);
                eh2.d.writeShort(n2);
                eh2.d.writeShort(n3);
                this.d = n2;
                this.e = n3;
                this.a.a(eh2);
                eh2.c();
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("  ".length() != "  ".length()) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void j(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[102]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() >= "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void k(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[67]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void o() {
        try {
            eh eh2 = dl.b(b[3]);
            eh2.d.writeByte(fl.l);
            eh2.d.writeByte(bh.b);
            eh2.d.writeBoolean(em.ac);
            eh2.d.writeInt(em.aj);
            eh2.d.writeInt(em.aq);
            eh2.d.writeBoolean(eg.r);
            eh2.d.writeBoolean(em.w);
            eh2.d.writeUTF(System.getProperty("microedition.platform"));
            eh2.d.writeByte(b[4]);
            eh2.d.writeInt(b[4]);
            eh2.d.writeByte(ag.br);
            eh2.d.writeInt(fl.g);
            eh2.d.writeUTF(fl.f);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (null != null) {
            return;
        }
    }

    private static boolean a(Object object) {
        return object != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void l(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[17]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() <= ((0xB1 ^ 0x93) & ~(0x7B ^ 0x59))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void at(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[70]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() != "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void p() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[47]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((129 + 25 - 143 + 120 ^ 109 + 77 - 71 + 20) < 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void q() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[114]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() == "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean o(int n2, int n3) {
        return n2 > n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void m(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[110]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() != "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void r() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[59]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x5C ^ 0x18) & ~(0x47 ^ 3)) < ((0xF ^ 0x3D) & ~(0x1E ^ 0x2C))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void n(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[42]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void g(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[27]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() >= (112 + 161 - 131 + 31 ^ 50 + 144 - 128 + 103)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void o(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[36]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void s() {
        eh eh2 = new eh(b[8]);
        this.a.a(eh2);
        eh2.c();
    }

    public final void t() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[63]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x72 ^ 0x3A ^ (0x54 ^ 0x18)) <= "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void f(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[87]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x18 ^ 0x1C) <= -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void u() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[79]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public final void v() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[48]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private void b(String string, String string2, String string3) {
        try {
            eh eh2 = new eh(b[6]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            eh2.d.writeUTF(string3);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-"   ".length() > 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(String string, byte[] byArray, byte by2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[94]);
            eh2.d.writeUTF(string);
            eh2.d.writeInt(byArray.length);
            eh2.d.write(byArray);
            eh2.d.writeByte(by2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(int n2, int n3, int n4) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[23]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            if (dl.o(n4, b[10])) {
                eh2.d.writeShort(n4);
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() >= ((6 + 81 - -79 + 16 ^ 63 + 112 - 158 + 117) & (0x4D ^ 0x5F ^ (0xC ^ 0x2E) ^ -" ".length()))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void w() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[73]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private void a(String string, int n2, int n3) {
        eh eh2 = new eh(b[1]);
        try {
            eh2.d.writeByte(b[3]);
            eh2.d.writeUTF(string);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("  ".length() <= 0) {
            return;
        }
        this.a.a(eh2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(String string, String string2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[91]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x93 ^ 0x97) <= "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(el el2, el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[127]);
            eh2.d.writeByte(el2.n);
            int n2 = b[4];
            int n3 = b[4];
            while (dl.p(n3, elArray.length)) {
                if (dl.a(elArray[n3]) && dl.n(elArray[n3].m.d, b[128])) {
                    eh2.d.writeByte(elArray[n3].n);
                    ++n2;
                }
                ++n3;
                "".length();
                if (((0xA4 ^ 0x9D) & ~(0x6B ^ 0x52)) == 0) continue;
                return;
            }
            if (dl.p(n2, b[39])) {
                em.c(ag.nz);
                return;
            }
            this.a.a(eh2);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        finally {
            eh2.c();
        }
        if ("  ".length() < 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void p(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[115]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() == 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(el el2, int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[117]);
            eh2.d.writeByte(el2.n);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xC3 ^ 0x9C) & ~(0xC1 ^ 0x9E)) >= (0x5E ^ 0x5A)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(el el2, el[] elArray, boolean bl2) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[38]);
            eh2.d.writeBoolean(bl2);
            eh2.d.writeByte(el2.n);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() == -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void q(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[15]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x5E ^ 0x67) & ~(0xBD ^ 0x84)) != ((0x27 ^ 0x71) & ~(0x14 ^ 0x42))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void r(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[56]);
            eh2.d.writeShort(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() != "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void g(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[75]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() > (0xA4 ^ 0xC1 ^ (0x45 ^ 0x24))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void x() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[7]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void s(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[35]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() <= ((0x76 ^ 0x36) & ~(0x68 ^ 0x28))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(el el2, el[] elArray) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[125]);
            eh2.d.writeByte(el2.n);
            int n2 = b[4];
            while (dl.p(n2, b[21])) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[123]) || !dl.m(elArray[n2].m.d, b[124]) || dl.n(elArray[n2].m.d, b[126]))) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (((161 + 79 - 75 + 15 ^ 18 + 92 - 52 + 81) & (0x2E ^ 0x33 ^ (0x28 ^ 0xA) ^ -" ".length())) == 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() != "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(byte by2, String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[132]);
            eh2.d.writeByte(by2);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x88 ^ 0xA3 ^ (0xB9 ^ 0x96)) <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void a(ad ad2, ad ad3, int n2) {
        eh eh2 = null;
        if (dl.ap(n2)) {
            try {
                if (dl.as(ad2.size()) && dl.as(ad3.size())) {
                    if (dl.n(n2, b[10])) {
                        eh2 = new eh(b[50]);
                        "".length();
                        if (("   ".length() & ~"   ".length()) == "   ".length()) {
                            return;
                        }
                    } else if (dl.n(n2, b[51])) {
                        eh2 = new eh(b[52]);
                    }
                    eh2.d.writeByte(ad2.size());
                    n2 = b[4];
                    while (dl.p(n2, ad2.size())) {
                        by by2 = (by)ad2.elementAt(n2);
                        eh2.d.writeByte(by2.b);
                        ++n2;
                        "".length();
                        if ((30 + 85 - 24 + 73 ^ 39 + 7 - 7 + 121) > "   ".length()) continue;
                        return;
                    }
                    n2 = b[4];
                    while (dl.p(n2, ad3.size())) {
                        ec ec2 = (ec)ad3.elementAt(n2);
                        if (dl.a(ec2)) {
                            eh2.d.writeInt(ec2.w);
                            "".length();
                            if ((0xBA ^ 0xBE) < 0) {
                                return;
                            }
                        } else {
                            eh2.d.writeInt(b[53]);
                        }
                        ++n2;
                        "".length();
                        if ((0xB1 ^ 0xC0 ^ (0xFA ^ 0x8F)) != 0) continue;
                        return;
                    }
                } else if (dl.as(ad2.size())) {
                    eh2 = new eh(b[54]);
                    n2 = b[4];
                    while (dl.p(n2, ad2.size())) {
                        by by3 = (by)ad2.elementAt(n2);
                        eh2.d.writeByte(by3.b);
                        ++n2;
                        "".length();
                        if (-(0x24 ^ 0x73 ^ (0xD6 ^ 0x84)) < 0) continue;
                        return;
                    }
                } else if (dl.as(ad3.size())) {
                    eh2 = new eh(b[55]);
                    n2 = b[4];
                    while (dl.p(n2, ad3.size())) {
                        ec ec3 = (ec)ad3.elementAt(n2);
                        eh2.d.writeInt(ec3.w);
                        ++n2;
                        "".length();
                        if (((0x67 ^ 0x29) & ~(0x44 ^ 0xA)) == 0) continue;
                        return;
                    }
                }
                if (dl.a(eh2)) {
                    this.a.a(eh2);
                    return;
                }
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (null != null) {
                return;
            }
        }
    }

    public final void y() {
        eh eh2 = null;
        try {
            eh2 = new eh(b[46]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void t(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[57]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x45 ^ 0x1F ^ (0xC0 ^ 0xB9)) & (0x6F ^ 1 ^ (0x13 ^ 0x5E) ^ -" ".length())) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[37]);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if ((0x5B ^ 0x23 ^ (0x22 ^ 0x5E)) != 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void h(int n2, int n3) {
        eh eh2 = new eh(b[9]);
        try {
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((0x6F ^ 0x6B) != (0x3E ^ 0x3A)) {
            return;
        }
    }

    public final void z() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[66]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void u(int n2) {
        block8: {
            eh eh2 = null;
            try {
                eh2 = new eh(b[20]);
                eh2.d.writeByte(n2);
                this.a.a(eh2);
                "".length();
            }
            catch (Exception exception) {
                exception.printStackTrace();
                "".length();
                if ("   ".length() == 0) {
                    return;
                }
                break block8;
            }
            finally {
                eh2.c();
            }
            if (" ".length() > (0xA1 ^ 0xA5)) {
                return;
            }
        }
        if (dl.a(ec.n().en[n2]) && dl.n(ec.n().en[n2].m.b, b[21])) {
            ap.s().ap();
            fb.a("");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void v(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[71]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() != "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void h(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[86]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() >= ((0x5D ^ 0x43 ^ (0x6F ^ 0x64)) & (0xEB ^ 0xC7 ^ (0x1B ^ 0x22) ^ -" ".length()))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c(int n2, int n3, int n4) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[23]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            if (dl.o(n4, b[10])) {
                eh2.d.writeShort(n4);
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x53 ^ 0x70) & ~(0xF ^ 0x2C)) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void i(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[103]);
            eh2.d.writeByte(ap.dz);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() != -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public static eh c(byte by2) {
        eh eh2 = new eh(b[1]);
        try {
            eh2.d.writeByte(by2);
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (" ".length() <= 0) {
            return null;
        }
        return eh2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void w(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[119]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() < 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private void a(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
        try {
            eh eh2 = new eh(b[134]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            eh2.d.writeUTF(string3);
            eh2.d.writeUTF(string4);
            eh2.d.writeUTF(string5);
            eh2.d.writeUTF(string6);
            eh2.d.writeUTF(string7);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("   ".length() < -" ".length()) {
            return;
        }
    }

    public final void aa() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[33]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x30 ^ 0x69) & ~(0x6A ^ 0x33)) != ((0x5D ^ 0x43) & ~(0x37 ^ 0x29))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void i(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[36]);
            eh2.d.writeInt(n2);
            eh2.d.writeInt(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x95 ^ 0x91) != (0xB3 ^ 0xB7)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void x(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[28]);
            eh2.d.writeShort(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() <= ((0xE6 ^ 0xA2) & ~(0xC2 ^ 0x86))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void y(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[41]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void z(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[72]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((45 + 117 - 58 + 45 ^ 38 + 57 - 38 + 106) & (0x9F ^ 0xC2 ^ (0x1D ^ 0x76) ^ -" ".length())) == (19 + 77 - -53 + 8 ^ 3 + 31 - 15 + 134)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void aa(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[129]);
            eh2.d.writeShort(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() >= (144 + 140 - 227 + 130 ^ 21 + 94 - -56 + 20)) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private static boolean p(int n2, int n3) {
        return n2 < n3;
    }

    private static void ai() {
        b = new int[139];
        dl.b[0] = -(40 + 2 - -88 + 28 ^ 127 + 56 - 103 + 51);
        dl.b[1] = -(0x17 ^ 0xB);
        dl.b[2] = -(0xB5 ^ 0xAB);
        dl.b[3] = -(0x74 ^ 9);
        dl.b[4] = (0xC4 ^ 0x9F) & ~(0xFE ^ 0xA5);
        dl.b[5] = -(58 + 112 - 62 + 19);
        dl.b[6] = 0x5C ^ 9 ^ (0x8A ^ 0xA9);
        dl.b[7] = -(0xDA ^ 0xA0);
        dl.b[8] = -(0x8D ^ 0x9C);
        dl.b[9] = 123 + 69 - 74 + 101 ^ 176 + 172 - 310 + 161;
        dl.b[10] = " ".length();
        dl.b[11] = -(0 ^ 0x7E);
        dl.b[12] = -(66 + 101 - 25 + 74 ^ 105 + 137 - 224 + 162);
        dl.b[13] = 103 + 172 - 214 + 113 ^ 21 + 127 - 91 + 75;
        dl.b[14] = 0x48 ^ 0x16;
        dl.b[15] = -(0x57 ^ 0x3A);
        dl.b[16] = 0x73 ^ 0x7C;
        dl.b[17] = 132 + 79 - 14 + 13 ^ 187 + 28 - 111 + 86;
        dl.b[18] = 0x24 ^ 0x34;
        dl.b[19] = 0x72 ^ 0x63;
        dl.b[20] = 0xA2 ^ 0xA9;
        dl.b[21] = 0x84 ^ 0x9C;
        dl.b[22] = 63 + 68 - -5 + 69 ^ 161 + 15 - 171 + 190;
        dl.b[23] = 0x10 ^ 0x1D;
        dl.b[24] = 0x77 ^ 0x5E;
        dl.b[25] = -(4 ^ 0x49);
        dl.b[26] = 0x73 ^ 0x32 ^ (0xE4 ^ 0x81);
        dl.b[27] = 0xA8 ^ 0x82 ^ (0xD ^ 0x2B);
        dl.b[28] = 0x7A ^ 0x52;
        dl.b[29] = 0x7E ^ 0x63;
        dl.b[30] = 0x66 ^ 0x48 ^ (0x97 ^ 0x9B);
        dl.b[31] = 0x27 ^ 0x7B;
        dl.b[32] = -(0xA7 ^ 0xC0);
        dl.b[33] = -(0x22 ^ 0x3D ^ (0xF4 ^ 0x80));
        dl.b[34] = -(0x31 ^ 0xF ^ (0x93 ^ 0xC7));
        dl.b[35] = -(0xCA ^ 0xB9 ^ (2 ^ 0x18));
        dl.b[36] = -(66 + 96 - -39 + 18 ^ 49 + 11 - 29 + 148);
        dl.b[37] = 0xBA ^ 0xA9;
        dl.b[38] = 0x4F ^ 0x7A ^ (0x5D ^ 0x7D);
        dl.b[39] = 0x4D ^ 0x19 ^ (0x19 ^ 0x59);
        dl.b[40] = 9 ^ 0x3C ^ (0x25 ^ 6);
        dl.b[41] = 0xA5 ^ 0x89;
        dl.b[42] = 0x78 ^ 0xB ^ (0x10 ^ 0);
        dl.b[43] = 0xAA ^ 0xC0;
        dl.b[44] = 0x68 ^ 3;
        dl.b[45] = 9 ^ 0x4B;
        dl.b[46] = 0x1B ^ 0x3B ^ (0x5B ^ 0x43);
        dl.b[47] = 0x39 ^ 0;
        dl.b[48] = 0x75 ^ 0x5B;
        dl.b[49] = 139 + 84 - 57 + 12 ^ 21 + 43 - -4 + 91;
        dl.b[50] = 0xFE ^ 0x8B ^ (0x7A ^ 0xB);
        dl.b[51] = "  ".length();
        dl.b[52] = 155 + 2 - -34 + 22 ^ 143 + 90 - 212 + 135;
        dl.b[53] = -" ".length();
        dl.b[54] = 0x14 ^ 0x28;
        dl.b[55] = 36 + 92 - 87 + 87 ^ 49 + 60 - -51 + 29;
        dl.b[56] = -(0x22 ^ 0x2C);
        dl.b[57] = -(0xBF ^ 0xB3);
        dl.b[58] = -(0x99 ^ 0x90);
        dl.b[59] = -(0xBF ^ 0xB5);
        dl.b[60] = 0x47 ^ 0x76 ^ (0x77 ^ 0x69);
        dl.b[61] = -(116 + 105 - 168 + 115 ^ 135 + 161 - 280 + 175);
        dl.b[62] = -(183 + 114 - 82 + 34 ^ 133 + 105 - 132 + 77);
        dl.b[63] = -(63 + 174 - 230 + 180 ^ 30 + 13 - -105 + 46);
        dl.b[64] = -(0x1D ^ 0x51 ^ (0xAE ^ 0x9A));
        dl.b[65] = -(0x6A ^ 0x1D);
        dl.b[66] = -(0xB ^ 0x75 ^ (0xD8 ^ 0xC3));
        dl.b[67] = 0xB5 ^ 0x9E;
        dl.b[68] = 0x38 ^ 3;
        dl.b[69] = 0x6E ^ 0x3E;
        dl.b[70] = 0x78 ^ 0x5E ^ (0x64 ^ 0x13);
        dl.b[71] = 65 + 33 - -48 + 69 ^ 11 + 127 - 97 + 109;
        dl.b[72] = 0x4B ^ 0xF;
        dl.b[73] = -(203 + 209 - 330 + 168 ^ 177 + 28 - 176 + 152);
        dl.b[74] = 0xBE ^ 0xC4 ^ (0x1F ^ 0x2A);
        dl.b[75] = -(0x71 ^ 0x2C);
        dl.b[76] = -(115 + 45 - 49 + 100 ^ 107 + 63 - 46 + 8);
        dl.b[77] = -(0xAA ^ 0x9D ^ (0x15 ^ 0x74));
        dl.b[78] = 0x78 ^ 3 ^ (2 ^ 0x33);
        dl.b[79] = -(37 + 97 - 112 + 199 ^ 89 + 38 - 47 + 53);
        dl.b[80] = -(0xEB ^ 0x82 ^ (0x26 ^ 3));
        dl.b[81] = 0x3B ^ 0x68;
        dl.b[82] = -(0x36 ^ 0x6C ^ (0x4E ^ 0x41));
        dl.b[83] = 0xDB ^ 0xBF;
        dl.b[84] = 0xB1 ^ 0xA8;
        dl.b[85] = -(152 + 65 - 114 + 121 ^ 62 + 57 - 51 + 112);
        dl.b[86] = -(0x5D ^ 3 ^ (0xC ^ 1));
        dl.b[87] = 0xAC ^ 0xBB;
        dl.b[88] = 199 + 147 - 304 + 167 ^ 45 + 13 - 51 + 133;
        dl.b[89] = -(0x5D ^ 0x49);
        dl.b[90] = -(7 ^ 0x12);
        dl.b[91] = -(34 + 44 - 29 + 106 ^ 64 + 9 - -40 + 28);
        dl.b[92] = -(0x29 ^ 0x3A);
        dl.b[93] = -(1 ^ 0x62);
        dl.b[94] = -(0x66 ^ 0x25);
        dl.b[95] = -(0x33 ^ 0x18 ^ (0x2C ^ 0x46));
        dl.b[96] = -(4 ^ 0x65);
        dl.b[97] = -(0xE3 ^ 0x90);
        dl.b[98] = -(0x78 ^ 0x50 ^ (0xD1 ^ 0x88));
        dl.b[99] = -(0x5B ^ 0x2B);
        dl.b[100] = -(0xB2 ^ 0x87 ^ (0x48 ^ 0x12));
        dl.b[101] = -(0xA7 ^ 0xC0 ^ (0x8E ^ 0x9B));
        dl.b[102] = -(0x6D ^ 0x52);
        dl.b[103] = -(0x1C ^ 0x1A ^ (0x2C ^ 0x17));
        dl.b[104] = -(0x65 ^ 0x5B);
        dl.b[105] = -(0x67 ^ 0x5B);
        dl.b[106] = -(14 + 192 - 80 + 68 ^ 64 + 137 - 62 + 18);
        dl.b[107] = -(0xE5 ^ 0xBB);
        dl.b[108] = -(0xD4 ^ 0x89 ^ " ".length());
        dl.b[109] = -(0x3B ^ 0x61);
        dl.b[110] = -(214 + 143 - 254 + 125 ^ 25 + 64 - -9 + 91);
        dl.b[111] = -(0x8F ^ 0xBF ^ (0xDF ^ 0xB4));
        dl.b[112] = -(0x2B ^ 0x17 ^ (0xC7 ^ 0xBD));
        dl.b[113] = -(0xC ^ 0x48);
        dl.b[114] = -(0x78 ^ 0x3D);
        dl.b[115] = -(113 + 41 - 103 + 179 ^ 22 + 0 - -23 + 83);
        dl.b[116] = -(0x1C ^ 0x2C ^ (0x46 ^ 0x24));
        dl.b[117] = 0x7B ^ 0x1D;
        dl.b[118] = 176 + 68 - 48 + 45 ^ 127 + 36 - 159 + 149;
        dl.b[119] = 0xD8 ^ 0xB1;
        dl.b[120] = -(0x6A ^ 0x78 ^ (0x5A ^ 0));
        dl.b[121] = 0x4A ^ 0x24;
        dl.b[122] = 152 + 75 - 140 + 72 ^ 51 + 77 - 117 + 138;
        dl.b[123] = 0xFFFFC9FF & 0x37C7;
        dl.b[124] = -(0xFFFFF55D & 0x7EB3) & (0xFFFFF5DB & 0x7FFC);
        dl.b[125] = 0x7C ^ 0x13;
        dl.b[126] = 0xFFFFFBCD & 0x5FB;
        dl.b[127] = 95 + 36 - 84 + 206 ^ 127 + 31 - 73 + 56;
        dl.b[128] = -(0xFFFFF43F & 0x7BF2) & (0xFFFFF3FF & 0x7DF7);
        dl.b[129] = 0x21 ^ 0x50;
        dl.b[130] = 0xE5 ^ 0x92;
        dl.b[131] = -((0x4C ^ 0x52) & ~(0xB4 ^ 0xAA) ^ (0x4D ^ 0x36));
        dl.b[132] = 0xE2 ^ 0xA6 ^ (0xA7 ^ 0x9A);
        dl.b[133] = 0x10 ^ 0x6A;
        dl.b[134] = 159 + 153 - 144 + 17 ^ 186 + 176 - 261 + 93;
        dl.b[135] = 64 + 182 - 209 + 153 ^ 3 + 184 - 111 + 118;
        dl.b[136] = "   ".length();
        dl.b[137] = 0x34 ^ 0x26 ^ (0xED ^ 0x81);
        dl.b[138] = 0x22 ^ 0x5F;
    }

    static {
        dl.ai();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void j(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[106]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void k(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[21]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x67 ^ 0x63) <= " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ab(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[105]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xA7 ^ 0xB3) & ~(0x57 ^ 0x43)) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void j(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[14]);
            eh2.d.writeInt(n2);
            eh2.d.writeByte(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x31 ^ 0x34) <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void l(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[89]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void k(int n2, int n3) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[15]);
            eh2.d.writeByte(n2);
            eh2.d.writeShort(n3);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() != -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void ab() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[34]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void m(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[88]);
            eh2.d.writeUTF(string);
            eh2.d.writeByte(b[4]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x61 ^ 0x45) & ~(0x45 ^ 0x61)) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ac(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[16]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x55 ^ 0x79) & ~(0x83 ^ 0xAF)) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void a(String string, String string2, String string3) {
        this.o();
        try {
            eh eh2 = dl.b(b[5]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            eh2.d.writeUTF(string3);
            eh2.d.writeUTF("");
            eh2.d.writeUTF("");
            eh2.d.writeUTF(an.a("random"));
            eh2.d.writeByte(fl.h);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (" ".length() >= "  ".length()) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(el el2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[40]);
            eh2.d.writeByte(el2.n);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xC4 ^ 0x9E ^ (0xA ^ 0x60)) & (191 + 34 - 17 + 34 ^ 139 + 14 - 89 + 130 ^ -" ".length())) >= "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ad(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[69]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-(58 + 151 - 129 + 103 ^ 68 + 24 - 41 + 127) >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void ac() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[108]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() != "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void l(int n2, int n3) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[22]);
            eh2.d.writeByte(n2);
            if (dl.o(n3, b[10])) {
                eh2.d.writeInt(n3);
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() < "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void n(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[76]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x16 ^ 0x45 ^ (0xBB ^ 0xAD)) & (0x1E ^ 0x5C ^ (0xB3 ^ 0xB4) ^ -" ".length())) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(short s2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[30]);
            eh2.d.writeShort(s2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void ad() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[99]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-(149 + 28 - 146 + 145 ^ 106 + 26 - -20 + 28) >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ae(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[83]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0x68 ^ 0x46 ^ (0x65 ^ 8)) & (0xFE ^ 0x91 ^ (0xA2 ^ 0x8E) ^ -" ".length())) < ((0xF2 ^ 0xC2 ^ "  ".length()) & (0x36 ^ 0x6A ^ (0xFA ^ 0x94) ^ -" ".length()))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void o(String string) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[68]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() < " ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void af(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[109]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((1 ^ 0x3D) & ~(0x6C ^ 0x50)) >= "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(el[] elArray) {
        em.g.b();
        eh eh2 = null;
        try {
            eh2 = new eh(b[137]);
            int n2 = b[4];
            while (dl.p(n2, elArray.length)) {
                if (dl.a(elArray[n2])) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if (-"   ".length() <= 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ag(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[19]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void p(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[95]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x18 ^ 0x5E ^ (0xFD ^ 0xBF)) == "  ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private void c(String string, String string2, String string3) {
        try {
            eh eh2 = dl.b(b[131]);
            eh2.d.writeUTF(string);
            eh2.d.writeUTF(string2);
            eh2.d.writeUTF(string3);
            this.a.a(eh2);
            eh2.c();
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (-"  ".length() > 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ah(int n2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[118]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xC7 ^ 0x83) & ~(6 ^ 0x42)) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ai(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[76]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() != -" ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public final void a(String string, String string2, byte by2) {
        eh eh2 = null;
        string = string + " " + string2;
        try {
            eh2 = new eh(b[90]);
            eh2.d.writeUTF(string);
            eh2.d.writeByte(by2);
            this.a.a(eh2);
            eh2.c();
            return;
        }
        catch (Exception exception) {
            eh2.c();
            "".length();
            if (((0x6A ^ 0x1C ^ (0x30 ^ 0x7D)) & (54 + 119 - 58 + 47 ^ 89 + 84 - 91 + 71 ^ -" ".length())) != 0) {
                return;
            }
            catch (Throwable throwable) {
                eh2.c();
                throw throwable;
            }
        }
    }

    public final void ae() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[120]);
            eh2.d.writeByte(ap.dz);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-(36 + 111 - 33 + 29 ^ 79 + 27 - 28 + 61) > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void q(String string) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[62]);
            eh2.d.writeUTF(string);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void aj(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[103]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    private void aj() {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[82]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void ak(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[32]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("  ".length() > "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void af() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[113]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (null != null) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void al(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[75]);
            eh2.d.writeByte(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ("   ".length() == ((0xC ^ 4) & ~(0x19 ^ 0x11))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(byte by2, short s2) {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[133]);
            eh2.d.writeByte(by2);
            eh2.d.writeByte(s2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() >= "   ".length()) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void am(int n2) {
        eh eh2 = null;
        try {
            eh2 = dl.d(b[73]);
            eh2.d.writeInt(n2);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((0x66 ^ 0x17 ^ (0x6E ^ 0x1B)) != (0x74 ^ 0xC ^ (0xF9 ^ 0x85))) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(int n2, el[] elArray) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[49]);
            eh2.d.writeInt(n2);
            n2 = b[4];
            int n3 = b[4];
            while (dl.p(n3, elArray.length)) {
                if (dl.a(elArray[n3])) {
                    ++n2;
                }
                ++n3;
                "".length();
                if (((0xA7 ^ 0xA0) & ~(0x5C ^ 0x5B)) == ((0x90 ^ 0xB5) & ~(0x2B ^ 0xE))) continue;
                return;
            }
            eh2.d.writeByte(n2);
            n3 = b[4];
            while (dl.p(n3, elArray.length)) {
                if (dl.a(elArray[n3])) {
                    eh2.d.writeByte(elArray[n3].n);
                }
                ++n3;
                "".length();
                if (-" ".length() <= 0) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void e(el[] elArray) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[121]);
            int n2 = b[4];
            while (dl.p(n2, b[21])) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[122]) || dl.n(elArray[n2].m.d, b[20]))) {
                    eh2.d.writeByte(elArray[n2].n);
                    "".length();
                    if (" ".length() > -" ".length()) break;
                    return;
                }
                ++n2;
                "".length();
                if (((7 ^ 0x27) & ~(0x3E ^ 0x1E)) == 0) continue;
                return;
            }
            n2 = b[4];
            while (dl.p(n2, b[21])) {
                if (dl.a(elArray[n2]) && (!dl.m(elArray[n2].m.d, b[123]) || dl.n(elArray[n2].m.d, b[124]))) {
                    eh2.d.writeByte(elArray[n2].n);
                }
                ++n2;
                "".length();
                if ("   ".length() == "   ".length()) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void d(int n2, int n3, int n4) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[29]);
            eh2.d.writeByte(n2);
            eh2.d.writeByte(n3);
            eh2.d.writeByte(n4);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    public final void r(String string) {
        eh eh2 = new eh(b[1]);
        try {
            eh2.d.writeByte(b[11]);
            eh2.d.writeUTF(string);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-" ".length() > 0) {
            return;
        }
        this.a.a(eh2);
    }

    public final void ag() {
        eh eh2 = null;
        try {
            eh2 = dl.c(b[104]);
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if ((127 + 69 - 133 + 122 ^ 59 + 6 - -22 + 101) <= 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(ad ad2) {
        eh eh2 = null;
        try {
            eh2 = new eh(b[84]);
            eh2.d.writeByte(ad2.size());
            int n2 = b[4];
            while (dl.p(n2, ad2.size())) {
                ec ec2 = (ec)ad2.elementAt(n2);
                eh2.d.writeInt(ec2.w);
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
            this.a.a(eh2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            "".length();
            if (((0xBC ^ 0x95) & ~(0x25 ^ 0xC)) != 0) {
                return;
            }
        }
        finally {
            eh2.c();
        }
    }

    static eh d(byte by2) {
        eh eh2 = new eh(b[2]);
        try {
            eh2.d.writeByte(by2);
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (null != null) {
            return null;
        }
        return eh2;
    }
}

