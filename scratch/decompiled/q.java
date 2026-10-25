/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.TextBox
 *  javax.microedition.midlet.MIDlet
 */
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;

public final class q
implements CommandListener {
    private static final int[] a;
    private final TextBox b;
    private eg c;

    static {
        q.a();
    }

    public final void commandAction(Command command, Displayable displayable) {
        if (q.a(command.getLabel().equals(ag.v) ? 1 : 0)) {
            this.c.a(this.b.getString());
        }
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.a);
        fk.a.setFullScreenMode(a[0]);
        this.c.c = a[0];
    }

    private static void a() {
        a = new int[1];
        q.a[0] = " ".length();
    }

    q(eg eg2, TextBox textBox) {
        this.c = eg2;
        this.b = textBox;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }
}

