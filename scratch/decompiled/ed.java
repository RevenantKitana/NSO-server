/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.ChoiceGroup
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 *  javax.microedition.lcdui.Image
 *  javax.microedition.lcdui.Item
 *  javax.microedition.lcdui.TextField
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public final class ed
implements CommandListener {
    private TextField d;
    private Command e;
    public static String a;
    private static final int[] f;
    private final Form g = new Form("Setting Gom \u0110\u1ed3!");
    private final ChoiceGroup h;
    private TextField i;
    public static byte b;
    private TextField j;
    public static String c;
    private TextField k;
    private TextField l;
    private TextField m;
    private TextField n;

    public final void commandAction(Command command, Displayable displayable) {
        block3: {
            if (!ed.a(command, this.e)) break block3;
            eq.a = this.k.getString();
            c = this.l.getString();
            a = this.d.getString();
            try {
                b = (byte)this.h.getSelectedIndex();
                eq.e = Byte.parseByte(this.i.getString());
                eq.d = Byte.parseByte(this.m.getString());
                eq.b = Integer.parseInt(this.j.getString());
                eq.ah = Byte.parseByte(this.n.getString());
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (((0x4D ^ 1) & ~(0x5E ^ 0x12)) > ((0x40 ^ 0x6F) & ~(0x97 ^ 0xB8))) {
                return;
            }
            em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
            eq.f();
        }
        ed.a((Displayable)fk.e());
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void a(Displayable displayable) {
        Display.getDisplay((MIDlet)fl.a).setCurrent(displayable);
    }

    private static void b() {
        f = new int[8];
        ed.f[0] = " ".length();
        ed.f[1] = (0x23 ^ 0x38 ^ (0x2B ^ 0x1E)) & (0x2E ^ 0x5B ^ (0x35 ^ 0x6E) ^ -" ".length());
        ed.f[2] = -(0xFFFFC8FF & 0x3FE4) & (0xFFFFEFF3 & 0x3FFF);
        ed.f[3] = "  ".length();
        ed.f[4] = 0xFFFFEFEF & 0x13F8;
        ed.f[5] = 0x48 ^ 0x41 ^ (0x43 ^ 0x4E);
        ed.f[6] = "   ".length();
        ed.f[7] = 0x23 ^ 0x24;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    public ed() {
        String string;
        String string2;
        String string3;
        String string4;
        String string5;
        int n2;
        int n3;
        if (ed.a(eq.a)) {
            n3 = f[0];
            "".length();
            if ("   ".length() < -" ".length()) {
                throw null;
            }
        } else {
            n3 = f[1];
        }
        if (ed.a(n2 = n3)) {
            string5 = "";
            "".length();
            if (((0x5E ^ 0x13 ^ (0xF1 ^ 0x80)) & (92 + 122 - 85 + 33 ^ 148 + 49 - 193 + 154 ^ -" ".length())) <= -" ".length()) {
                throw null;
            }
        } else {
            string5 = eq.a;
        }
        this.k = new TextField("Name nh\u1eadn \u0111\u1ed3", string5, f[2], f[1]);
        if (ed.a(n2)) {
            string4 = "0";
            "".length();
            if ("   ".length() <= -" ".length()) {
                throw null;
            }
        } else {
            string4 = String.valueOf(eq.e);
        }
        this.i = new TextField("Gi\u1edd nh\u1eadn \u0111\u1ed3", string4, f[3], f[3]);
        this.l = new TextField("Item GD <M\u1ed7i ID c\u00e1ch nhau b\u1eb1ng d\u1ea5u ph\u1ea3y > Nh\u1eadp id item", c, f[4], f[1]);
        this.d = new TextField("Item C\u1ea5t <M\u1ed7i ID c\u00e1ch nhau b\u1eb1ng d\u1ea5u ph\u1ea3y > Nh\u1eadp id item", a, f[4], f[1]);
        String[] stringArray = new String[f[3]];
        stringArray[ed.f[1]] = "B\u1eadt";
        stringArray[ed.f[0]] = "T\u1eaft";
        this.h = new ChoiceGroup("Auto C\u1ea5t \u0111\u1ed3 v\u00e0o r\u01b0\u01a1ng khi up !", f[0], stringArray, new Image[f[3]]);
        if (ed.a(n2)) {
            string3 = "0";
            "".length();
            if (-"  ".length() > 0) {
                throw null;
            }
        } else {
            string3 = String.valueOf(eq.d);
        }
        this.m = new TextField("Ph\u00fat nh\u1eadn \u0111\u1ed3", string3, f[3], f[3]);
        if (ed.a(n2)) {
            string2 = "22";
            "".length();
            if ((0x81 ^ 0x94 ^ (0x5F ^ 0x4E)) <= 0) {
                throw null;
            }
        } else {
            string2 = String.valueOf(eq.b);
        }
        this.j = new TextField("Map nh\u1eadn \u0111\u1ed3", string2, f[5], f[3]);
        if (ed.a(n2)) {
            string = "20";
            "".length();
            if ((0x70 ^ 0x17 ^ (0x1B ^ 0x79)) == 0) {
                throw null;
            }
        } else {
            string = String.valueOf(eq.ah);
        }
        this.n = new TextField("Khun V\u1ef1c nh\u1eadn", string, f[6], f[3]);
        this.e = new Command("L\u01b0u", f[5], f[1]);
    }

    public final void a() {
        this.g.append((Item)this.k);
        "".length();
        this.g.append((Item)this.l);
        "".length();
        this.g.append((Item)this.i);
        "".length();
        this.g.append((Item)this.m);
        "".length();
        this.g.append((Item)this.j);
        "".length();
        this.g.append((Item)this.n);
        "".length();
        this.g.append((Item)this.h);
        "".length();
        this.g.append((Item)this.d);
        "".length();
        this.g.addCommand(this.e);
        this.g.addCommand(new Command("Tho\u00e1t", f[7], f[1]));
        this.h.setSelectedIndex((int)b, f[0]);
        this.g.setCommandListener((CommandListener)this);
        ed.a((Displayable)this.g);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    static {
        ed.b();
        c = "";
        a = "454,455,456,457";
        b = (byte)f[1];
    }
}

