/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 *  javax.microedition.lcdui.Item
 *  javax.microedition.lcdui.TextField
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public final class bi
extends Form
implements CommandListener {
    private final TextField a = new TextField("Khu ch\u1edd PK:", String.valueOf(ey.b), d[0], d[0]);
    private final TextField b;
    private final TextField c;
    private static final int[] d;
    private final TextField e;
    private final TextField f;
    private final Command g;
    private final TextField h;

    static {
        bi.b();
    }

    public final void a() {
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)this);
    }

    public bi() {
        super("C\u00e0i khu ch\u1edd pk v\u00e0 \u0111\u00e1nh pk");
        String string;
        String string2;
        String string3;
        this.append((Item)this.a);
        "".length();
        this.h = new TextField("Khu \u0111\u00e1nh PK:", String.valueOf(ey.g), d[0], d[0]);
        this.append((Item)this.h);
        "".length();
        String string4 = "ID map PK - (map hi\u1ec7n t\u1ea1i: " + de.i + ")";
        if (bi.a(ey.l, d[1])) {
            string3 = String.valueOf(ey.l);
            "".length();
            if (-" ".length() == "   ".length()) {
                throw null;
            }
        } else {
            string3 = "70";
        }
        this.b = new TextField(string4, string3, d[2], d[0]);
        this.append((Item)this.b);
        "".length();
        String string5 = "V\u1ecb tr\u00ed X - (vt hi\u1ec7n t\u1ea1i: " + ec.n().s + ")";
        if (bi.a(ey.ai, d[1])) {
            string2 = String.valueOf(ey.ai);
            "".length();
            if (-(0x94 ^ 0x90) > 0) {
                throw null;
            }
        } else {
            string2 = "1500";
        }
        this.e = new TextField(string5, string2, d[3], d[0]);
        this.append((Item)this.e);
        "".length();
        String string6 = "V\u1ecb tr\u00ed Y - (vt hi\u1ec7n t\u1ea1i: " + ec.n().fc + ")";
        if (bi.a(ey.r, d[1])) {
            string = String.valueOf(ey.r);
            "".length();
            if (null != null) {
                throw null;
            }
        } else {
            string = "528";
        }
        this.f = new TextField(string6, string, d[3], d[0]);
        this.append((Item)this.f);
        "".length();
        this.append("L\u01b0u \u00fd: nh\u1eefng c\u00e0i \u0111\u1eb7t n\u00e0y c\u1ea3 3 acc ph\u1ea3i c\u00e0i gi\u1ed1ng h\u1ec7t nhau!");
        "".length();
        this.c = new TextField("Bao nhi\u00eau % th\u00ec auto UpPK:", String.valueOf(ey.ba), d[0], d[0]);
        this.append((Item)this.c);
        "".length();
        this.append("P/s: C\u00e0i \u0111\u1eb7t n\u00e0y d\u00e0nh cho acc ch\u00ednh!");
        "".length();
        this.g = new Command("Save", d[4], d[5]);
        this.addCommand(this.g);
        this.addCommand(new Command("Cancel", d[6], d[5]));
        this.setCommandListener(this);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (bi.a(command, this.g)) {
            try {
                ey.b = Integer.parseInt(this.a.getString());
                ey.g = Integer.parseInt(this.h.getString());
                ey.l = Integer.parseInt(this.b.getString());
                ey.ai = Integer.parseInt(this.e.getString());
                ey.r = Integer.parseInt(this.f.getString());
                ey.ba = Long.parseLong(this.c.getString());
                an.a("chipAutopk", ey.b + ";" + ey.g + ";" + ey.l + ";" + ey.ai + ";" + ey.r + ";" + (int)ey.ba);
                em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            "".length();
            if (-"  ".length() >= 0) {
                return;
            }
        }
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.e());
    }

    private static void b() {
        d = new int[7];
        bi.d[0] = "  ".length();
        bi.d[1] = -" ".length();
        bi.d[2] = "   ".length();
        bi.d[3] = 0x37 ^ 0x3E;
        bi.d[4] = 0x8E ^ 0x8A;
        bi.d[5] = (153 + 91 - 91 + 12 ^ 25 + 15 - -33 + 125) & (28 + 69 - 91 + 226 ^ 33 + 103 - 10 + 13 ^ -" ".length());
        bi.d[6] = 0x16 ^ 0x11;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }
}

