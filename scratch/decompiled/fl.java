/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 */
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class fl
extends MIDlet {
    public static fl a;
    public static String[] b;
    public static String c;
    public static String[] d;
    public static int e;
    public static String f;
    private static final int[] m;
    public static byte g;
    public static byte h;
    public static int[] i;
    public static int[] j;
    public static byte[] k;
    public static byte l;

    protected void pauseApp() {
    }

    protected void startApp() {
        Display.getDisplay((MIDlet)this).setCurrent((Displayable)fk.e());
    }

    private static String b(String string) {
        InputStream inputStream = an.c("/".concat(String.valueOf(string)));
        try {
            byte[] byArray = new byte[inputStream.available()];
            inputStream.read(byArray);
            "".length();
            string = new String(byArray, "UTF-8");
            "".length();
        }
        catch (IOException iOException) {
            string = "";
        }
        if (null != null) {
            return null;
        }
        return string;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    public static void a(String string) {
        if (fl.a(string.equals("") ? 1 : 0)) {
            try {
                a.platformRequest(string);
                "".length();
                a.notifyDestroyed();
                return;
            }
            catch (ConnectionNotFoundException connectionNotFoundException) {
                // empty catch block
            }
        }
    }

    protected void destroyApp(boolean bl2) {
    }

    public fl() {
        be.c();
        dp.g().c = cf.d();
        a = this;
        f = fl.b("agent.txt");
        g = Byte.parseByte(fl.b("provider.txt"));
        System.out.println("AGENT: " + f + ", PROVIDER: " + g);
        er.a = m[0];
        em.at = new er();
        fk.e().c = em.f();
        if (fl.a(fk.b ? 1 : 0)) {
            new Thread(fk.e()).start();
        }
    }

    static {
        fl.a();
        String[] stringArray = new String[m[1]];
        stringArray[fl.m[0]] = "161.118.209.14";
        stringArray[fl.m[2]] = "161.118.209.14";
        stringArray[fl.m[3]] = "161.118.209.14";
        stringArray[fl.m[4]] = ff.f;
        stringArray[fl.m[5]] = ff.f;
        stringArray[fl.m[6]] = ff.f;
        stringArray[fl.m[7]] = ff.f;
        stringArray[fl.m[8]] = ff.f;
        d = stringArray;
        int[] nArray = new int[m[1]];
        nArray[fl.m[0]] = ff.b;
        nArray[fl.m[2]] = ff.b;
        nArray[fl.m[3]] = ff.b;
        nArray[fl.m[4]] = ff.b;
        nArray[fl.m[5]] = ff.b;
        nArray[fl.m[6]] = ff.b;
        nArray[fl.m[7]] = ff.b;
        nArray[fl.m[8]] = ff.b;
        j = nArray;
        byte[] byArray = new byte[m[1]];
        byArray[fl.m[0]] = m[0];
        byArray[fl.m[2]] = m[0];
        byArray[fl.m[3]] = m[2];
        byArray[fl.m[4]] = m[0];
        byArray[fl.m[5]] = m[0];
        byArray[fl.m[6]] = m[0];
        byArray[fl.m[7]] = m[0];
        byArray[fl.m[8]] = m[0];
        k = byArray;
        int[] nArray2 = new int[m[1]];
        nArray2[fl.m[0]] = m[0];
        nArray2[fl.m[2]] = m[2];
        nArray2[fl.m[3]] = m[3];
        nArray2[fl.m[4]] = m[4];
        nArray2[fl.m[5]] = m[5];
        nArray2[fl.m[6]] = m[6];
        nArray2[fl.m[7]] = m[7];
        nArray2[fl.m[8]] = m[8];
        i = nArray2;
        l = (byte)m[2];
        e = ff.c;
        c = "";
        g = (byte)m[0];
        h = (byte)m[0];
        String[] stringArray2 = new String[m[1]];
        stringArray2[fl.m[0]] = "161.118.209.14";
        stringArray2[fl.m[2]] = "161.118.209.14";
        stringArray2[fl.m[3]] = "161.118.209.14";
        stringArray2[fl.m[4]] = ff.a;
        stringArray2[fl.m[5]] = ff.a;
        stringArray2[fl.m[6]] = ff.a;
        stringArray2[fl.m[7]] = ff.a;
        stringArray2[fl.m[8]] = ff.a;
        b = stringArray2;
    }

    private static void a() {
        m = new int[9];
        fl.m[0] = (7 ^ 9) & ~(0xA4 ^ 0xAA);
        fl.m[1] = 103 + 109 - 120 + 50 ^ 18 + 9 - -45 + 62;
        fl.m[2] = " ".length();
        fl.m[3] = "  ".length();
        fl.m[4] = "   ".length();
        fl.m[5] = 8 ^ 0xC;
        fl.m[6] = 7 ^ 0x46 ^ (0xD2 ^ 0x96);
        fl.m[7] = 3 + 184 - 147 + 145 ^ 163 + 87 - 215 + 156;
        fl.m[8] = 0xA ^ 0x31 ^ (0x82 ^ 0xBE);
    }
}

