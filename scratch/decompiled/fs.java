/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class fs {
    private static final int[] a;
    private static ad b;

    public static boolean a(String string) {
        return b.contains(string);
    }

    public static void a() {
        b.removeAllElements();
        fs.f();
    }

    public static void b() {
        b = new ad();
        b.removeAllElements();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(an.b("V6PKS"));
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            int n2 = dataInputStream.readInt();
            int n3 = a[0];
            while (fs.a(n3, n2)) {
                b.addElement(dataInputStream.readUTF());
                ++n3;
                "".length();
                if ((0x28 ^ 0x2C) != 0) continue;
                return;
            }
            dataInputStream.close();
            byteArrayInputStream.close();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-" ".length() >= " ".length()) {
            return;
        }
    }

    public static ad c() {
        ad ad2 = new ad();
        int n2 = a[0];
        while (fs.a(n2, b.size())) {
            ad2.addElement(n2 + ". " + (String)b.elementAt(n2));
            ++n2;
            "".length();
            if (((0x3B ^ 0x6D ^ (0x8E ^ 0xB8)) & (22 + 96 - 19 + 96 ^ 144 + 152 - 165 + 32 ^ -" ".length())) >= -" ".length()) continue;
            return null;
        }
        return ad2;
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static void e() {
        a = new int[2];
        fs.a[0] = (0x36 ^ 0x66) & ~(0x91 ^ 0xC1);
        fs.a[1] = 0xB1 ^ 0x85;
    }

    private static void f() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeInt(b.size());
            int n2 = a[0];
            while (fs.a(n2, b.size())) {
                dataOutputStream.writeUTF((String)b.elementAt(n2));
                ++n2;
                "".length();
                if (((0x5C ^ 0x14) & ~(0x2C ^ 0x64)) >= 0) continue;
                return;
            }
            dataOutputStream.flush();
            byteArrayOutputStream.flush();
            an.a("V6PKS", byteArrayOutputStream.toByteArray());
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (((0x4C ^ 0x6E ^ (0x96 ^ 0xBB)) & (9 ^ 0x3A ^ (0x9C ^ 0xA0) ^ -" ".length())) == -" ".length()) {
            return;
        }
    }

    public static void d() {
        b = null;
    }

    static {
        fs.e();
        ce.a(a[1]);
        fs.b();
    }

    public static void b(String string) {
        if (fs.b(b.contains(string) ? 1 : 0)) {
            b.removeElement(string);
            "".length();
            fs.f();
        }
    }

    public static void c(String string) {
        if (fs.a(b.contains(string) ? 1 : 0)) {
            b.addElement(string);
            fs.f();
        }
    }
}

