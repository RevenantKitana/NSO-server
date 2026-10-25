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
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordStore
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public final class ee
implements CommandListener {
    public static boolean a;
    public static boolean b;
    private final Command e;
    private static DataOutputStream f;
    private final Command g;
    private final Form h = new Form("menu pro");
    private final ChoiceGroup i;
    public static boolean c;
    private static DataInputStream j;
    private static RecordStore k;
    private static ByteArrayOutputStream l;
    private static ByteArrayInputStream m;
    public static boolean d;
    private static final int[] n;

    private static void a(Displayable displayable) {
        Display.getDisplay((MIDlet)fl.a).setCurrent(displayable);
    }

    public ee() {
        this.e = new Command("L\u01b0u", n[0], n[1]);
        this.g = new Command("H\u1ee7y", n[2], n[1]);
        String[] stringArray = new String[n[0]];
        stringArray[ee.n[4]] = "Auto 4skill";
        stringArray[ee.n[1]] = "Hi\u1ec7n ID Map";
        stringArray[ee.n[3]] = "Hi\u1ec7n Exp/Y\u00ean Khi Up";
        stringArray[ee.n[2]] = "Hi\u1ec7u \u1ee8ng Tele Qu\u00e1i";
        this.i = new ChoiceGroup("C\u00e0i \u0110\u1eb7t Chung!!", n[3], stringArray, (Image[])null);
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public final void a() {
        this.h.append((Item)this.i);
        "".length();
        this.h.addCommand(this.e);
        this.h.addCommand(this.g);
        this.h.setCommandListener((CommandListener)this);
        this.i.setSelectedIndex(n[4], d);
        this.i.setSelectedIndex(n[1], b);
        this.i.setSelectedIndex(n[3], a);
        this.i.setSelectedIndex(n[2], c);
        ee.a((Displayable)this.h);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    static {
        ee.b();
        d = n[4];
        b = n[1];
        a = n[4];
        c = n[4];
        try {
            k = RecordStore.openRecordStore((String)"smenu", (boolean)n[1]);
            if (ee.b(k.getNumRecords())) {
                m = new ByteArrayInputStream(k.getRecord(n[1]));
                j = new DataInputStream(m);
                d = j.readBoolean();
                b = j.readBoolean();
                a = j.readBoolean();
                c = j.readBoolean();
            }
            k.closeRecordStore();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (((0x77 ^ 0x33 ^ (0x1C ^ 0x6A)) & (132 + 83 - 156 + 77 ^ 159 + 156 - 311 + 182 ^ -" ".length())) > 0) {
            // empty if block
        }
    }

    public final void commandAction(Command command, Displayable displayable) {
        block9: {
            if (!ee.a(command, this.e)) break block9;
            try {
                d = this.i.isSelected(n[4]);
                b = this.i.isSelected(n[1]);
                a = this.i.isSelected(n[3]);
                c = this.i.isSelected(n[2]);
                l = new ByteArrayOutputStream();
                f = new DataOutputStream(l);
                try {
                    f.writeBoolean(d);
                    f.writeBoolean(b);
                    f.writeBoolean(a);
                    f.writeBoolean(c);
                    f.flush();
                    f.close();
                    l.flush();
                    k = RecordStore.openRecordStore((String)"smenu", (boolean)n[1]);
                    byte[] byArray = l.toByteArray();
                    l.close();
                    if (ee.a(k.getNumRecords())) {
                        k.addRecord(byArray, n[4], byArray.length);
                        "".length();
                        "".length();
                        if (((0x9F ^ 0xB5) & ~(0xAF ^ 0x85)) != 0) {
                            return;
                        }
                    } else {
                        k.setRecord(n[1], byArray, n[4], byArray.length);
                    }
                    k.closeRecordStore();
                    "".length();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if ((122 + 136 - 196 + 130 ^ 125 + 114 - 195 + 152) < 0) {
                    return;
                }
                em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            "".length();
            if ((0xA ^ 0xE) == 0) {
                return;
            }
        }
        ee.a((Displayable)fk.e());
    }

    private static void b() {
        n = new int[5];
        ee.n[0] = 0xAB ^ 0xAF;
        ee.n[1] = " ".length();
        ee.n[2] = "   ".length();
        ee.n[3] = "  ".length();
        ee.n[4] = (0xC5 ^ 0x8E) & ~(0xDA ^ 0x91);
    }
}

