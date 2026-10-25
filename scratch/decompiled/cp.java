/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;
import java.util.Vector;

public final class cp
extends Thread {
    public static long a;
    private int d = e[0];
    private static final int[] e;
    private static Random f;
    public static boolean b;
    public static String c;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void a() {
        e = new int[4];
        cp.e[0] = (0x65 ^ 0x76 ^ (0xCE ^ 0xC0)) & (0xFA ^ 0x81 ^ (0xF3 ^ 0x95) ^ -" ".length());
        cp.e[1] = -" ".length();
        cp.e[2] = " ".length();
        cp.e[3] = 3 ^ 0x51 ^ (0x3D ^ 0xC);
    }

    static {
        cp.a();
        f = new Random();
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    public final void run() {
        String string = ";";
        String string2 = c;
        Vector<String> vector = new Vector<String>();
        if (cp.b(string2.endsWith(string) ? 1 : 0)) {
            string2 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(string2)))) + string;
        }
        int n2 = e[0];
        int n3 = string2.indexOf(string);
        while (cp.a(n3, e[1])) {
            vector.addElement(string2.substring(n2, n3));
            n2 = n3 + string.length();
            n3 = string2.indexOf(string, n2);
            "".length();
            if (-(0x5D ^ 0x58) < 0) continue;
            return;
        }
        Object[] objectArray = new String[vector.size()];
        vector.copyInto(objectArray);
        Object[] objectArray2 = objectArray;
        while (cp.a(b ? 1 : 0)) {
            this.d += e[2];
            this.d %= objectArray2.length;
            dl.h().e("@" + f.nextInt(e[3]) + ": " + (String)objectArray2[this.d]);
            try {
                Thread.sleep(a);
                "".length();
            }
            catch (Exception exception) {
                "".length();
                if ("   ".length() >= "   ".length()) continue;
                return;
            }
            if ("  ".length() < "   ".length()) continue;
            return;
        }
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }
}

