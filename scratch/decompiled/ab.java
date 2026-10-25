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

public final class ab
implements CommandListener {
    private TextField f;
    public static int a;
    private static DataInputStream g;
    private static ByteArrayOutputStream h;
    private static ByteArrayInputStream i;
    private TextField j;
    private static final int[] k;
    private static DataOutputStream l;
    private TextField m;
    public static int b;
    private TextField n;
    private TextField o;
    private static RecordStore p;
    public static int c;
    private Command q;
    private TextField r;
    private TextField s;
    private TextField t;
    private TextField u;
    private TextField v;
    private Command w;
    public static int d;
    public static int e;
    private TextField x;
    private Form y = new Form("Menu Danh V\u1ecdng");
    private TextField z;

    public static void a() {
        fa.d = k[3];
        fa.a = k[3];
        b = k[8];
        ey.ad = "";
        ey.ag = k[5];
        ey.f = k[9];
        ey.i = k[10];
        ey.j = k[10];
        d = k[0];
        a = k[3];
        c = k[10];
        e = k[10];
        try {
            p = RecordStore.openRecordStore((String)"DanhVong", (boolean)k[5]);
            if (ab.a(p.getNumRecords())) {
                i = new ByteArrayInputStream(p.getRecord(k[5]));
                g = new DataInputStream(i);
                fa.d = g.readInt();
                fa.a = g.readInt();
                b = g.readInt();
                ey.ad = g.readUTF();
                ey.ag = g.readInt();
                ey.f = g.readInt();
                ey.i = g.readInt();
                ey.j = g.readInt();
                d = g.readInt();
                a = g.readInt();
                c = g.readInt();
                e = g.readInt();
            }
            p.closeRecordStore();
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (-"  ".length() >= 0) {
            return;
        }
    }

    public final void b() {
        Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)this.y);
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }

    private static void d() {
        k = new int[12];
        ab.k[0] = "  ".length();
        ab.k[1] = 0x11 ^ 0x1B;
        ab.k[2] = -(0xFFFFFAF1 & 0x5F9F) & (0xFFFFFFBA & 0x5ED5);
        ab.k[3] = (0x1D ^ 0x6A ^ (0xD ^ 0x48)) & (0x75 ^ 0x5A ^ (0x94 ^ 0x89) ^ -" ".length());
        ab.k[4] = "   ".length();
        ab.k[5] = " ".length();
        ab.k[6] = 0x5A ^ 4 ^ (0xC4 ^ 0x9E);
        ab.k[7] = 0xA4 ^ 0xA3;
        ab.k[8] = -(0xFFFFC876 & 0x3FCD) & (0xFFFFEF7B & 0x3FD7);
        ab.k[9] = 0x79 ^ 0x4D ^ (0xB4 ^ 0x96);
        ab.k[10] = -" ".length();
        ab.k[11] = 0xB ^ 0x14;
    }

    public final void commandAction(Command command, Displayable displayable) {
        block7: {
            if (!ab.a(command, this.w)) break block7;
            fa.d = Integer.parseInt(this.t.getString());
            fa.a = Integer.parseInt(this.f.getString());
            b = Integer.parseInt(this.x.getString());
            ey.ad = this.m.getString().trim();
            ey.ag = Integer.parseInt(this.o.getString());
            ey.f = Integer.parseInt(this.v.getString());
            ey.i = Integer.parseInt(this.s.getString());
            ey.j = Integer.parseInt(this.j.getString());
            d = Integer.parseInt(this.r.getString());
            a = Integer.parseInt(this.n.getString());
            c = Integer.parseInt(this.u.getString());
            e = Integer.parseInt(this.z.getString());
            try {
                h = new ByteArrayOutputStream();
                l = new DataOutputStream(h);
                l.writeInt(fa.d);
                l.writeInt(fa.a);
                l.writeInt(b);
                l.writeUTF(ey.ad);
                l.writeInt(ey.ag);
                l.writeInt(ey.f);
                l.writeInt(ey.i);
                l.writeInt(ey.j);
                l.writeInt(d);
                l.writeInt(a);
                l.writeInt(c);
                l.writeInt(e);
                l.flush();
                l.close();
                h.flush();
                p = RecordStore.openRecordStore((String)"DanhVong", (boolean)k[5]);
                byte[] byArray = h.toByteArray();
                h.close();
                if (ab.b(p.getNumRecords())) {
                    p.addRecord(byArray, k[3], byArray.length);
                    "".length();
                    "".length();
                    if ("  ".length() != "  ".length()) {
                        return;
                    }
                } else {
                    p.setRecord(k[5], byArray, k[3], byArray.length);
                }
                p.closeRecordStore();
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ((0xA1 ^ 0xC3 ^ (0x19 ^ 0x7F)) != (0x5E ^ 0x45 ^ (0x53 ^ 0x4C))) {
                return;
            }
            Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.e());
            em.c("L\u01b0u c\u00e0i \u0111\u1eb7t th\u00e0nh c\u00f4ng");
        }
        if (ab.a(command, this.q)) {
            Display.getDisplay((MIDlet)fl.a).setCurrent((Displayable)fk.e());
        }
    }

    static {
        ab.d();
        ce.a(k[11]);
        ab.a();
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    public ab() {
        this.t = new TextField("Gi\u1edd l\u00e0m danh v\u1ecdng", "" + fa.d, k[0], k[0]);
        this.y.append((Item)this.t);
        "".length();
        this.f = new TextField("Ph\u00fat l\u00e0m danh v\u1ecdng", "" + fa.a, k[0], k[0]);
        this.y.append((Item)this.f);
        "".length();
        this.x = new TextField("Xu c\u01b0\u1ee3c", "" + b, k[1], k[0]);
        this.y.append((Item)this.x);
        "".length();
        this.m = new TextField("T\u00ean \u0110\u1ed1i Th\u1ee7 + ',': <C\u00e1ch nhau b\u1eb1ng d\u1ea5u ph\u1ea3y ','> ;", ey.ad, k[2], k[3]);
        this.y.append((Item)this.m);
        "".length();
        this.o = new TextField("Map l\u00f4i \u0111\u00e0i", "" + ey.ag, k[4], k[0]);
        this.y.append((Item)this.o);
        "".length();
        this.v = new TextField("Khu l\u00f4i \u0111\u00e0i", "" + ey.f, k[4], k[0]);
        this.y.append((Item)this.v);
        "".length();
        this.s = new TextField("Map danh v\u1ecdng", "" + ey.i, k[4], k[5]);
        this.y.append((Item)this.s);
        "".length();
        this.j = new TextField("Khu danh v\u1ecdng", "" + ey.j, k[4], k[5]);
        this.y.append((Item)this.j);
        "".length();
        this.r = new TextField("Map C\u1eebu S\u00e1t", "" + d, k[4], k[4]);
        this.y.append((Item)this.r);
        "".length();
        this.n = new TextField("Khu C\u1eebu S\u00e1t", "" + a, k[4], k[4]);
        this.y.append((Item)this.n);
        "".length();
        this.u = new TextField("Map Ki\u1ebfm Y\u00ean", "" + c, k[4], k[4]);
        this.y.append((Item)this.u);
        "".length();
        this.z = new TextField("Khu Ki\u1ebfm Y\u00ean", "" + e, k[4], k[4]);
        this.y.append((Item)this.z);
        "".length();
        this.w = new Command("L\u01b0u", k[6], k[5]);
        this.y.addCommand(this.w);
        this.q = new Command("H\u1ee7y", k[7], k[5]);
        this.y.addCommand(this.q);
        this.y.setCommandListener((CommandListener)this);
    }

    public static void c() {
        i = null;
        g = null;
        h = null;
        l = null;
        p = null;
        b = k[3];
        d = k[3];
        a = k[3];
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }
}

