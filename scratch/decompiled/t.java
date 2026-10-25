/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;

public final class t {
    public static e a;
    private static final int[] b;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    public static void a(bf bf2) {
        a.put(new Short(bf2.d), bf2);
        "".length();
    }

    public static short a(short s2) {
        return t.b((short)s2).j;
    }

    public static bf a(String string, byte by2) {
        bf bf2;
        Enumeration enumeration = a.elements();
        do {
            if (t.b(enumeration.hasMoreElements() ? 1 : 0)) {
                return null;
            }
            bf2 = (bf)enumeration.nextElement();
        } while (!t.a(bf2.e.equals(string) ? 1 : 0) || t.a(bf2.i, b[0]) && !t.b(bf2.i, by2));
        return bf2;
    }

    static {
        t.a();
        a = new e();
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean b(int n2, int n3) {
        return n2 == n3;
    }

    private static void a() {
        b = new int[1];
        t.b[0] = "  ".length();
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public static bf b(short s2) {
        return (bf)a.get(new Short(s2));
    }
}

