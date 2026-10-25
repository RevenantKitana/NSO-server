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

public final class dj
implements CommandListener {
    private TextField b;
    private static DataOutputStream c;
    private static DataInputStream d;
    private static final int[] e;
    public Form a = new Form("Menu Auto Chat");
    private TextField f;
    private Command g;
    private static ByteArrayOutputStream h;
    private static RecordStore i;
    private Command j;
    private static ByteArrayInputStream k;

    static {
        dj.c();
        ce.a(e[0]);
        dj.b();
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    public final void commandAction(Command command, Displayable displayable) {
        block7: {
            if (!dj.a(command, this.j)) break block7;
            cp.c = this.b.getString();
            cp.a = Long.parseLong(this.f.getString());
            try {
                h = new ByteArrayOutputStream();
                c = new DataOutputStream(h);
                c.writeUTF(cp.c);
                c.writeLong(cp.a);
                c.flush();
                c.close();
                h.flush();
                i = RecordStore.openRecordStore((String)"AutoChat", (boolean)e[3]);
                byte[] byArray = h.toByteArray();
                h.close();
                if (dj.b(i.getNumRecords())) {
                    i.addRecord(byArray, e[1], byArray.length);
                    "".length();
                    "".length();
                    if (-" ".length() == ((0x6D ^ 0x78 ^ (0 ^ 0x75)) & (0xD4 ^ 0x83 ^ (0x50 ^ 0x67) ^ -" ".length()))) {
                        return;
                    }
                } else {
                    i.setRecord(e[3], byArray, e[1], byArray.length);
                }
                i.closeRecordStore();
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ((7 ^ 0x4A ^ (0x6B ^ 0x22)) == 0) {
                return;
            }
            Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.e());
            em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
        }
        if (dj.a(command, this.g)) {
            Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.e());
        }
    }

    public static void a() {
        k = null;
        d = null;
        h = null;
        c = null;
        i = null;
    }

    private static void c() {
        e = new int[31];
        dj.e[0] = 0x9B ^ 0x8B;
        dj.e[1] = "   ".length() & ("   ".length() ^ -" ".length());
        dj.e[2] = 192 + 103 - 270 + 201 ^ 5 + 142 - 105 + 101;
        dj.e[3] = " ".length();
        dj.e[4] = "   ".length() ^ (0x13 ^ 0x7F);
        dj.e[5] = "  ".length();
        dj.e[6] = 0x3E ^ 0x7B ^ (0xBE ^ 0x9F);
        dj.e[7] = "   ".length();
        dj.e[8] = 0xB6 ^ 0x96;
        dj.e[9] = 0x92 ^ 0x96;
        dj.e[10] = 0x5F ^ 0xC ^ (0x6E ^ 0x5F);
        dj.e[11] = 0x7C ^ 0x79;
        dj.e[12] = 0xCB ^ 0xB2;
        dj.e[13] = 0xBE ^ 0xB8;
        dj.e[14] = 53 + 93 - 104 + 155 ^ 167 + 97 - 222 + 152;
        dj.e[15] = 0x3F ^ 0x71 ^ (0xE8 ^ 0xC5);
        dj.e[16] = 0xC8 ^ 0xC0;
        dj.e[17] = (0xF0 ^ 0xB4) + (0xD3 ^ 0x94) - -(0xA5 ^ 0xB9) + (0xCF ^ 0x82);
        dj.e[18] = 0x64 ^ 0x6D;
        dj.e[19] = 0x4D ^ 3 ^ (0x7B ^ 0x5B);
        dj.e[20] = 10 + 157 - -15 + 2 ^ 94 + 62 - 34 + 56;
        dj.e[21] = 0x2B ^ 0x1E ^ (0x1A ^ 0x48);
        dj.e[22] = 0xFF ^ 0x8F ^ (0x64 ^ 0x1F);
        dj.e[23] = 0x79 ^ 0x75;
        dj.e[24] = 0x7C ^ 0x14;
        dj.e[25] = 135 + 140 - 178 + 80 ^ 35 + 7 - -78 + 68;
        dj.e[26] = (0x10 ^ 0x5C) + (0x29 ^ 0x1A) - (0x38 ^ 0x10) + (73 + 87 - 94 + 96);
        dj.e[27] = 0xCF ^ 0xC1;
        dj.e[28] = 120 + 78 - 134 + 66 ^ 94 + 39 - 34 + 42;
        dj.e[29] = 0xFFFF978C & 0x7BFB;
        dj.e[30] = 0xFFFFA5F4 & 0x5BFF;
    }

    public dj() {
        this.b = new TextField("N\u1ed9i dung", cp.c, e[29], e[1]);
        this.a.append((Item)this.b);
        "".length();
        this.a.append("* L\u01b0u \u00fd: m\u1ed7i c\u00e2u c\u00e1ch nhau b\u1eb1ng d\u1ea5u ch\u1ea5m ph\u1ea9y (;)");
        "".length();
        this.f = new TextField("Qu\u00e3ng ngh\u1ec9 (milisecond)", "" + cp.a, e[30], e[5]);
        this.a.append((Item)this.f);
        "".length();
        this.j = new Command("L\u01b0u", e[9], e[3]);
        this.a.addCommand(this.j);
        this.g = new Command("H\u1ee7y", e[14], e[3]);
        this.a.addCommand(this.g);
        this.a.setCommandListener((CommandListener)this);
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    public static void b() {
        char[] cArray = new char[e[0]];
        cArray[dj.e[1]] = e[2];
        cArray[dj.e[3]] = e[4];
        cArray[dj.e[5]] = e[6];
        cArray[dj.e[7]] = e[8];
        cArray[dj.e[9]] = e[10];
        cArray[dj.e[11]] = e[12];
        cArray[dj.e[13]] = e[8];
        cArray[dj.e[14]] = e[15];
        cArray[dj.e[16]] = e[17];
        cArray[dj.e[18]] = e[19];
        cArray[dj.e[20]] = e[21];
        cArray[dj.e[22]] = e[8];
        cArray[dj.e[23]] = e[24];
        cArray[dj.e[25]] = e[26];
        cArray[dj.e[27]] = e[19];
        cArray[dj.e[28]] = e[21];
        cp.c = new String(cArray);
        cp.a = 20000L;
        try {
            i = RecordStore.openRecordStore((String)"AutoChat", (boolean)e[3]);
            if (dj.a(i.getNumRecords())) {
                k = new ByteArrayInputStream(i.getRecord(e[3]));
                d = new DataInputStream(k);
                cp.c = d.readUTF();
                cp.a = d.readLong();
            }
            i.closeRecordStore();
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

