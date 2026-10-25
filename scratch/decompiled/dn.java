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

public final class dn
extends Form
implements CommandListener {
    private Command a;
    private static dn b;
    private TextField c;
    private TextField d;
    private TextField e = new TextField("Ng\u00e0y up", "", f[0], f[1]);
    private static final int[] f;

    public static dn a() {
        if (dn.a((Object)b)) {
            b = new dn();
        }
        return b;
    }

    public final void b() {
        long l2 = ey.s / 60000L;
        this.c.setString(String.valueOf(l2 % 60L));
        this.d.setString(String.valueOf((l2 /= 60L) % 60L));
        this.e.setString(String.valueOf(l2 /= 60L));
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)this);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static boolean a(Object object) {
        return object == null;
    }

    public final void commandAction(Command command, Displayable displayable) {
        block3: {
            if (!dn.a(command, this.a)) break block3;
            int n2 = f[3];
            int n3 = f[3];
            int n4 = f[3];
            try {
                n2 = Integer.parseInt(this.e.getString());
                n3 = Integer.parseInt(this.d.getString());
                n4 = Integer.parseInt(this.c.getString());
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (-" ".length() > 0) {
                return;
            }
            ey.s = ((long)(n2 * f[5] + n3) * 60L + (long)n4) * 60L * 1000L;
        }
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.a);
        fk.a.setFullScreenMode(f[6]);
    }

    private static void e() {
        f = new int[8];
        dn.f[0] = "   ".length();
        dn.f[1] = "  ".length();
        dn.f[2] = 21 + 10 - -60 + 58 ^ 84 + 53 - 123 + 131;
        dn.f[3] = (0x82 ^ 0x8B) & ~(0x79 ^ 0x70);
        dn.f[4] = 0x74 ^ 0x73;
        dn.f[5] = 0x9E ^ 0x86;
        dn.f[6] = " ".length();
        dn.f[7] = 0x7E ^ 0x3D ^ (0xA ^ 0x58);
    }

    public static void c() {
        b = null;
    }

    public dn() {
        super("C\u00e0i up time - C\u00e0i all 0 \u0111\u1ec3 up full time");
        this.append((Item)this.e);
        "".length();
        this.d = new TextField("Gi\u1edd up", "", f[0], f[1]);
        this.append((Item)this.d);
        "".length();
        this.c = new TextField("Ph\u00fat up", "", f[0], f[1]);
        this.append((Item)this.c);
        "".length();
        this.a = new Command("L\u01b0u", f[2], f[3]);
        this.addCommand(this.a);
        this.addCommand(new Command("Tho\u00e1t", f[4], f[3]));
        this.setCommandListener(this);
    }

    public static void d() {
    }

    static {
        dn.e();
        ce.a(f[7]);
        dn.d();
    }
}

