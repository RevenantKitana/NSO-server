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
 *  javax.microedition.rms.RecordStore
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public final class dm
implements CommandListener {
    public static String a;
    private TextField d;
    private Command e;
    private static ByteArrayInputStream f;
    private static final int[] g;
    private static DataOutputStream h;
    public static long b;
    public Form c = new Form("Auto Ch\u00e1t KTG !");
    private TextField i = new TextField("Qu\u00e3ng ngh\u1ec9 (milisecond)", "" + b, g[0], g[1]);
    private static ByteArrayOutputStream j;
    private Command k;
    private static RecordStore l;
    private static DataInputStream m;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static void b() {
        g = new int[7];
        dm.g[0] = 0xFFFFEDF5 & 0x13FE;
        dm.g[1] = "  ".length();
        dm.g[2] = 0xFFFFB7DF & 0x6F30;
        dm.g[3] = " ".length();
        dm.g[4] = 0x54 ^ 0x50;
        dm.g[5] = 0x6B ^ 0x5D ^ (0xA2 ^ 0x93);
        dm.g[6] = (0x1C ^ 0x7C) & ~(0xD8 ^ 0xB8);
    }

    private static void a(Displayable displayable) {
        Display.getDisplay((MIDlet)fl.a).setCurrent(displayable);
    }

    public final void a() {
        this.c.append((Item)this.i);
        "".length();
        this.c.append((Item)this.d);
        "".length();
        this.c.addCommand(this.e);
        this.c.addCommand(this.k);
        this.c.setCommandListener((CommandListener)this);
        dm.a((Displayable)this.c);
    }

    static {
        dm.b();
        b = 62000L;
        try {
            l = RecordStore.openRecordStore((String)"ktg", (boolean)g[3]);
            if (dm.a(l.getNumRecords())) {
                f = new ByteArrayInputStream(l.getRecord(g[3]));
                m = new DataInputStream(f);
                b = m.readLong();
                a = m.readUTF();
            }
            l.closeRecordStore();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (-"  ".length() > 0) {
            // empty if block
        }
    }

    public final void commandAction(Command command, Displayable displayable) {
        block6: {
            if (!dm.a(command, this.e)) break block6;
            b = Long.parseLong(this.i.getString());
            a = this.d.getString();
            try {
                j = new ByteArrayOutputStream();
                h = new DataOutputStream(j);
                h.writeLong(b);
                h.writeUTF(a);
                h.flush();
                h.close();
                j.flush();
                l = RecordStore.openRecordStore((String)"ktg", (boolean)g[3]);
                j.close();
                if (dm.b(l.getNumRecords())) {
                    l.addRecord(j.toByteArray(), g[6], j.toByteArray().length);
                    "".length();
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    l.setRecord(g[3], j.toByteArray(), g[6], j.toByteArray().length);
                }
                l.closeRecordStore();
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (" ".length() <= 0) {
                return;
            }
            em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
        }
        dm.a((Displayable)fk.e());
    }

    public dm() {
        this.d = new TextField("N\u1ed9i Dung Ch\u00e1t KTG", a, g[2], g[3]);
        this.e = new Command("L\u01b0u", g[4], g[3]);
        this.k = new Command("H\u1ee7y", g[5], g[3]);
    }
}

