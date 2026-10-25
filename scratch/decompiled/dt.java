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
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public final class dt
implements CommandListener {
    private final Command j;
    private final Form k = new Form("C\u00e0i \u0111\u1eb7t NVDV ");
    private final Command l;
    private final ChoiceGroup m;
    private static ByteArrayInputStream n;
    private final ChoiceGroup o;
    private final ChoiceGroup p;
    public static byte a;
    private static DataInputStream q;
    private final ChoiceGroup r;
    private TextField s;
    private final ChoiceGroup t;
    public static byte b;
    public static byte c;
    private static DataOutputStream u;
    public static byte d;
    private static final int[] v;
    private TextField w;
    public static byte e;
    private final ChoiceGroup x;
    private final ChoiceGroup y;
    public static byte f;
    public static byte g;
    private final ChoiceGroup z;
    private static RecordStore aa;
    public static byte h;
    private static ByteArrayOutputStream ab;
    private final ChoiceGroup ac;
    public static byte i;

    public static void a() {
        e = (byte)v[1];
        try {
            aa = RecordStore.openRecordStore((String)"formdv", (boolean)v[1]);
            if (dt.a(aa.getNumRecords())) {
                n = new ByteArrayInputStream(aa.getRecord(v[1]));
                q = new DataInputStream(n);
                f = q.readByte();
                h = q.readByte();
                i = q.readByte();
                c = q.readByte();
                d = q.readByte();
                g = q.readByte();
                a = q.readByte();
                e = q.readByte();
                b = q.readByte();
                ey.d = q.readInt();
                ey.h = q.readInt();
            }
            aa.closeRecordStore();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (null != null) {
            return;
        }
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void a(Displayable displayable) {
        Display.getDisplay((MIDlet)fl.a).setCurrent(displayable);
    }

    static {
        dt.d();
        ce.a(v[5]);
        dt.a();
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public dt() {
        this.j = new Command("L\u01b0u", v[0], v[1]);
        this.l = new Command("H\u1ee7y", v[2], v[1]);
        String[] stringArray = new String[v[3]];
        stringArray[dt.v[4]] = "L\u00e0m NV";
        stringArray[dt.v[1]] = "H\u1ee7y NV";
        this.y = new ChoiceGroup("- Ti\u00eau di\u1ec7t qu\u00e1i th\u01b0\u1eddng", v[1], stringArray, new Image[v[3]]);
        String[] stringArray2 = new String[v[3]];
        stringArray2[dt.v[4]] = "L\u00e0m NV";
        stringArray2[dt.v[1]] = "H\u1ee7y NV";
        this.t = new ChoiceGroup("- N\u00e2ng c\u1ea5p v\u1eadt ph\u1ea9m!", v[1], stringArray2, new Image[v[3]]);
        String[] stringArray3 = new String[v[3]];
        stringArray3[dt.v[4]] = "L\u00e0m NV";
        stringArray3[dt.v[1]] = "H\u1ee7y NV";
        this.o = new ChoiceGroup("-- Chi\u1ebfn th\u1eafng l\u00f4i \u0111\u00e0i!", v[1], stringArray3, new Image[v[3]]);
        String[] stringArray4 = new String[v[2]];
        stringArray4[dt.v[4]] = "L\u00e0m NV";
        stringArray4[dt.v[1]] = "H\u1ee7y NV";
        stringArray4[dt.v[3]] = "D\u1eebng Auto";
        this.z = new ChoiceGroup("- C\u1eebu s\u00e1t ng\u01b0\u1eddi kh\u00e1c!", v[1], stringArray4, new Image[v[2]]);
        String[] stringArray5 = new String[v[3]];
        stringArray5[dt.v[4]] = "L\u00e0m NV";
        stringArray5[dt.v[1]] = "H\u1ee7y NV";
        this.ac = new ChoiceGroup("- N\u00f4ng d\u00e2n ch\u0103m ch\u1ec9!", v[1], stringArray5, new Image[v[3]]);
        String[] stringArray6 = new String[v[3]];
        stringArray6[dt.v[4]] = "L\u00e0m NV";
        stringArray6[dt.v[1]] = "H\u1ee7y NV";
        this.x = new ChoiceGroup("- Ti\u00eau di\u1ec7t Tinh Anh", v[1], stringArray6, new Image[v[3]]);
        String[] stringArray7 = new String[v[3]];
        stringArray7[dt.v[4]] = "L\u00e0m NV";
        stringArray7[dt.v[1]] = "H\u1ee7y NV";
        this.m = new ChoiceGroup("- Ti\u00eau di\u1ec7t Th\u1ee7 L\u0129nh", v[1], stringArray7, new Image[v[3]]);
        String[] stringArray8 = new String[v[3]];
        stringArray8[dt.v[4]] = "D\u00f9ng B\u1ea3o Hi\u1ec3m";
        stringArray8[dt.v[1]] = "Kh\u00f4ng B\u1ea3o Hi\u1ec3m";
        this.p = new ChoiceGroup("- S\u1eed d\u1ee5ng b\u1ea3o hi\u1ec3m \u0111\u1ec3 n\u00e2ng l\u00ean 7", v[1], stringArray8, new Image[v[3]]);
        String[] stringArray9 = new String[v[3]];
        stringArray9[dt.v[4]] = "D\u00f9ng B\u1ea3o Hi\u1ec3m";
        stringArray9[dt.v[1]] = "Kh\u00f4ng B\u1ea3o Hi\u1ec3m";
        this.r = new ChoiceGroup("- S\u1eed d\u1ee5ng b\u1ea3o hi\u1ec3m \u0111\u1ec3 n\u00e2ng l\u00ean 8", v[1], stringArray9, new Image[v[3]]);
        this.s = new TextField("N\u00e2ng l\u00ean c\u1ea5p !", "" + ey.d, v[3], v[3]);
        this.w = new TextField("\u0110\u00e1 n\u00e2ng c\u1ea5p !", "" + ey.h, v[3], v[3]);
    }

    public static void b() {
        n = null;
        q = null;
        ab = null;
        u = null;
        aa = null;
        f = (byte)v[4];
        h = (byte)v[4];
        i = (byte)v[4];
        c = (byte)v[4];
        d = (byte)v[4];
        e = (byte)v[4];
        g = (byte)v[4];
        a = (byte)v[4];
        b = (byte)v[4];
    }

    public final void commandAction(Command command, Displayable displayable) {
        block9: {
            if (!dt.a(command, this.j)) break block9;
            try {
                f = (byte)this.y.getSelectedIndex();
                h = (byte)this.x.getSelectedIndex();
                i = (byte)this.m.getSelectedIndex();
                c = (byte)this.t.getSelectedIndex();
                d = (byte)this.o.getSelectedIndex();
                e = (byte)this.z.getSelectedIndex();
                b = (byte)this.ac.getSelectedIndex();
                a = (byte)this.r.getSelectedIndex();
                g = (byte)this.p.getSelectedIndex();
                ey.d = Integer.parseInt(this.s.getString());
                ey.h = Integer.parseInt(this.w.getString());
                ab = new ByteArrayOutputStream();
                u = new DataOutputStream(ab);
                try {
                    u.writeByte(f);
                    u.writeByte(h);
                    u.writeByte(i);
                    u.writeByte(c);
                    u.writeByte(d);
                    u.writeByte(e);
                    u.writeByte(g);
                    u.writeByte(a);
                    u.writeByte(b);
                    u.writeInt(ey.d);
                    u.writeInt(ey.h);
                    u.flush();
                    u.close();
                    ab.flush();
                    aa = RecordStore.openRecordStore((String)"formdv", (boolean)v[1]);
                    byte[] byArray = ab.toByteArray();
                    ab.close();
                    if (dt.b(aa.getNumRecords())) {
                        aa.addRecord(byArray, v[4], byArray.length);
                        "".length();
                        "".length();
                        if (" ".length() == -" ".length()) {
                            return;
                        }
                    } else {
                        aa.setRecord(v[1], byArray, v[4], byArray.length);
                    }
                    aa.closeRecordStore();
                    "".length();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (-"  ".length() >= 0) {
                    return;
                }
                em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            "".length();
            if (-" ".length() >= ((0xA ^ 0x3E) & ~(0x58 ^ 0x6C))) {
                return;
            }
        }
        dt.a((Displayable)fk.e());
    }

    private static void d() {
        v = new int[6];
        dt.v[0] = 0x35 ^ 0x31;
        dt.v[1] = " ".length();
        dt.v[2] = "   ".length();
        dt.v[3] = "  ".length();
        dt.v[4] = "   ".length() & ("   ".length() ^ -" ".length());
        dt.v[5] = 0x6B ^ 0x28;
    }

    public final void c() {
        this.k.append((Item)this.p);
        "".length();
        this.k.append((Item)this.r);
        "".length();
        this.k.append((Item)this.s);
        "".length();
        this.k.append((Item)this.w);
        "".length();
        this.k.append((Item)this.y);
        "".length();
        this.k.append((Item)this.x);
        "".length();
        this.k.append((Item)this.m);
        "".length();
        this.k.append((Item)this.t);
        "".length();
        this.k.append((Item)this.o);
        "".length();
        this.k.append((Item)this.z);
        "".length();
        this.k.append((Item)this.ac);
        "".length();
        this.k.addCommand(this.j);
        this.k.addCommand(this.l);
        this.k.setCommandListener((CommandListener)this);
        this.y.setSelectedIndex((int)f, v[1]);
        this.t.setSelectedIndex((int)c, v[1]);
        this.o.setSelectedIndex((int)d, v[1]);
        this.z.setSelectedIndex((int)e, v[1]);
        this.ac.setSelectedIndex((int)b, v[1]);
        this.x.setSelectedIndex((int)h, v[1]);
        this.m.setSelectedIndex((int)i, v[1]);
        this.p.setSelectedIndex((int)g, v[1]);
        this.r.setSelectedIndex((int)a, v[1]);
        dt.a((Displayable)this.k);
    }
}

