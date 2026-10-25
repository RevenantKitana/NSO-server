/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.HttpConnection
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class be {
    public static int[] a;
    public static String[] b;
    private static final int[] g;
    public static int[] c;
    public static byte[] d;
    public static String[] e;
    public static String f;

    public static boolean a() {
        char[] cArray = new char[g[2]];
        cArray[be.g[3]] = g[4];
        cArray[be.g[5]] = g[6];
        cArray[be.g[7]] = g[6];
        cArray[be.g[8]] = g[9];
        cArray[be.g[10]] = g[11];
        cArray[be.g[12]] = g[13];
        cArray[be.g[14]] = g[13];
        cArray[be.g[15]] = g[16];
        cArray[be.g[17]] = g[9];
        cArray[be.g[18]] = g[19];
        cArray[be.g[20]] = g[21];
        cArray[be.g[22]] = g[23];
        cArray[be.g[24]] = g[25];
        cArray[be.g[26]] = g[27];
        cArray[be.g[28]] = g[9];
        cArray[be.g[29]] = g[30];
        cArray[be.g[31]] = g[16];
        cArray[be.g[32]] = g[6];
        cArray[be.g[33]] = g[21];
        cArray[be.g[34]] = g[23];
        cArray[be.g[35]] = g[36];
        cArray[be.g[37]] = g[21];
        cArray[be.g[38]] = g[13];
        cArray[be.g[39]] = g[16];
        cArray[be.g[40]] = g[9];
        cArray[be.g[41]] = g[6];
        cArray[be.g[42]] = g[4];
        cArray[be.g[43]] = g[19];
        cArray[be.g[44]] = g[45];
        String string = be.a(new String(cArray));
        if (be.b(string.equals("") ? 1 : 0)) {
            return g[3];
        }
        String[] stringArray = be.a(string.trim(), ",", g[3]);
        b = new String[stringArray.length];
        e = new String[stringArray.length];
        a = new int[stringArray.length];
        d = new byte[stringArray.length];
        c = new int[stringArray.length];
        int n2 = g[3];
        while (be.a(n2, stringArray.length)) {
            String[] stringArray2 = be.a(stringArray[n2].trim(), ":", g[3]);
            be.b[n2] = stringArray2[g[3]];
            be.e[n2] = stringArray2[g[5]];
            be.a[n2] = Integer.parseInt(stringArray2[g[7]]);
            be.d[n2] = Byte.parseByte(stringArray2[g[8]]);
            be.c[n2] = n2;
            ++n2;
            "".length();
            if ("   ".length() >= 0) continue;
            return ((91 + 142 - 224 + 207 ^ 127 + 116 - 144 + 53) & (0xD1 ^ 0x8A ^ (0x7A ^ 0x61) ^ -" ".length())) != 0;
        }
        be.b();
        return g[5];
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    private static void d() {
        g = new int[77];
        be.g[0] = (0xB0 ^ 0x99) + (0x93 ^ 0xBE) - -(0x74 ^ 0x5E) + (0x6E ^ 0x26);
        be.g[1] = -" ".length();
        be.g[2] = 0x18 ^ 5;
        be.g[3] = (0xF4 ^ 0xA9) & ~(0x47 ^ 0x1A);
        be.g[4] = 0x7C ^ 0x14;
        be.g[5] = " ".length();
        be.g[6] = 0xAA ^ 0xC0 ^ (0x4D ^ 0x53);
        be.g[7] = "  ".length();
        be.g[8] = "   ".length();
        be.g[9] = 0xB ^ 0x7B;
        be.g[10] = 119 + 112 - 59 + 26 ^ 18 + 69 - -26 + 81;
        be.g[11] = "   ".length() ^ (0x1A ^ 0x23);
        be.g[12] = 38 + 51 - 65 + 133 ^ 54 + 94 - 146 + 150;
        be.g[13] = 0x22 ^ 0xD;
        be.g[14] = 0x3F ^ 0x39;
        be.g[15] = 0xB0 ^ 0xB7;
        be.g[16] = 0x25 ^ 0x4C;
        be.g[17] = 32 + 23 - -85 + 29 ^ 45 + 73 - 87 + 130;
        be.g[18] = 0xC8 ^ 0xC1;
        be.g[19] = 0xF4 ^ 0x9A;
        be.g[20] = 0x87 ^ 0x8D;
        be.g[21] = 0x7C ^ 0x19;
        be.g[22] = 0x6A ^ 0x61;
        be.g[23] = 0xD2 ^ 0x8C ^ (0xDF ^ 0xAF);
        be.g[24] = 0xCA ^ 0x89 ^ (0xFC ^ 0xB3);
        be.g[25] = 0x69 ^ 0x1E;
        be.g[26] = 152 + 114 - 256 + 196 ^ 142 + 117 - 239 + 175;
        be.g[27] = 0xE0 ^ 0x81;
        be.g[28] = 0x6F ^ 0x61;
        be.g[29] = 0x3B ^ 0x34;
        be.g[30] = 143 + 109 - 146 + 89 ^ 172 + 128 - 181 + 57;
        be.g[31] = 0x40 ^ 0x50;
        be.g[32] = 0x4E ^ 0x5F;
        be.g[33] = 0x86 ^ 0x94;
        be.g[34] = 0xD1 ^ 0xC2;
        be.g[35] = 0xAE ^ 0x80 ^ (0x49 ^ 0x73);
        be.g[36] = 0x46 ^ 0x2B;
        be.g[37] = 0x4D ^ 0x58;
        be.g[38] = 0x53 ^ 0x66 ^ (0x34 ^ 0x17);
        be.g[39] = 129 + 26 - 68 + 69 ^ 71 + 131 - 81 + 18;
        be.g[40] = 0xAA ^ 0xA3 ^ (0xA4 ^ 0xB5);
        be.g[41] = 0x4F ^ 0x56;
        be.g[42] = 0xA6 ^ 0xBC;
        be.g[43] = 0xDE ^ 0xC5;
        be.g[44] = 0x1A ^ 9 ^ (0x28 ^ 0x27);
        be.g[45] = 0xCB ^ 0xA1;
        be.g[46] = 172 + 63 - 126 + 65 ^ 51 + 102 - 116 + 122;
        be.g[47] = 0xF ^ 0x20 ^ (0x3C ^ 0x47);
        be.g[48] = 0x19 ^ 0x6B;
        be.g[49] = -(0xFFFFEBAF & 0x7C5C) & (0xFFFFEDBF & 0x7BFB);
        be.g[50] = -(0xFFFFE941 & 0x77BF) & (0xFFFFFFDD & Short.MAX_VALUE);
        be.g[51] = 0x7B ^ 5 ^ (0x37 ^ 0x2E);
        be.g[52] = 117 + 164 - 148 + 48 ^ 70 + 115 - 132 + 96;
        be.g[53] = 0xE1 ^ 0xA9;
        be.g[54] = -(0xFFFFE563 & 0x7BBF) & (0xFFFFFFEF & Short.MAX_VALUE);
        be.g[55] = 160 + 21 - 120 + 137 ^ 68 + 43 - -20 + 34;
        be.g[56] = 0xC2 ^ 0x8C;
        be.g[57] = 0x50 ^ 0xC ^ (0x2B ^ 1);
        be.g[58] = 187 + 156 - 322 + 193 ^ 81 + 125 - 156 + 113;
        be.g[59] = 0x76 ^ 0x65 ^ (0xE2 ^ 0x9E);
        be.g[60] = 0x8F ^ 0x91;
        be.g[61] = 0x69 ^ 0x76;
        be.g[62] = 0x58 ^ 0x21 ^ (0x65 ^ 0x3D);
        be.g[63] = 0xB4 ^ 0x96;
        be.g[64] = 0x2E ^ 2 ^ (0x65 ^ 0x6A);
        be.g[65] = 0x67 ^ 0x61 ^ (0x98 ^ 0xBA);
        be.g[66] = 51 + 150 - 143 + 118 ^ 25 + 21 - 10 + 113;
        be.g[67] = 0x20 ^ 0x72 ^ (0x27 ^ 0x53);
        be.g[68] = 0xAE ^ 0x89;
        be.g[69] = 0x9F ^ 0xB7;
        be.g[70] = 0x3E ^ 0x70 ^ (0xF0 ^ 0x97);
        be.g[71] = 0x9E ^ 0x88 ^ (0x11 ^ 0x33);
        be.g[72] = 0x96 ^ 0x8A ^ (1 ^ 0x37);
        be.g[73] = 111 + 52 - 96 + 111 ^ 5 + 143 - 89 + 94;
        be.g[74] = 0x8B ^ 0xA7;
        be.g[75] = 0x8B ^ 0xA6;
        be.g[76] = 0x36 ^ 6;
    }

    public static void b() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int n2 = e.length;
            dataOutputStream.writeInt(n2);
            int n3 = g[3];
            while (be.a(n3, n2)) {
                dataOutputStream.writeUTF(b[n3]);
                dataOutputStream.writeUTF(e[n3]);
                dataOutputStream.writeInt(a[n3]);
                dataOutputStream.writeByte(d[n3]);
                ++n3;
                "".length();
                if (null == null) continue;
                return;
            }
            dataOutputStream.flush();
            byteArrayOutputStream.flush();
            an.a("abcdip", byteArrayOutputStream.toByteArray());
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (" ".length() > "  ".length()) {
            return;
        }
    }

    static {
        be.d();
        char[] cArray = new char[g[46]];
        cArray[be.g[3]] = g[47];
        cArray[be.g[5]] = g[48];
        cArray[be.g[7]] = g[49];
        cArray[be.g[8]] = g[50];
        cArray[be.g[10]] = g[19];
        cArray[be.g[12]] = g[51];
        cArray[be.g[14]] = g[52];
        cArray[be.g[15]] = g[53];
        cArray[be.g[17]] = g[54];
        cArray[be.g[18]] = g[55];
        cArray[be.g[20]] = g[52];
        cArray[be.g[22]] = g[56];
        cArray[be.g[24]] = g[16];
        cArray[be.g[26]] = g[19];
        cArray[be.g[28]] = g[45];
        cArray[be.g[29]] = g[27];
        cArray[be.g[31]] = g[11];
        cArray[be.g[32]] = g[30];
        cArray[be.g[33]] = g[57];
        cArray[be.g[34]] = g[46];
        cArray[be.g[35]] = g[23];
        cArray[be.g[37]] = g[6];
        cArray[be.g[38]] = g[48];
        cArray[be.g[39]] = g[58];
        cArray[be.g[40]] = g[59];
        cArray[be.g[41]] = g[19];
        cArray[be.g[42]] = g[51];
        cArray[be.g[43]] = g[4];
        cArray[be.g[44]] = g[59];
        cArray[be.g[2]] = g[55];
        cArray[be.g[60]] = g[19];
        cArray[be.g[61]] = g[16];
        cArray[be.g[52]] = g[19];
        cArray[be.g[62]] = g[45];
        cArray[be.g[63]] = g[27];
        cArray[be.g[64]] = g[23];
        cArray[be.g[65]] = g[55];
        cArray[be.g[66]] = g[59];
        cArray[be.g[67]] = g[36];
        cArray[be.g[68]] = g[11];
        cArray[be.g[69]] = g[46];
        cArray[be.g[70]] = g[71];
        cArray[be.g[72]] = g[71];
        cArray[be.g[73]] = g[71];
        cArray[be.g[74]] = g[71];
        cArray[be.g[75]] = g[11];
        cArray[be.g[23]] = g[76];
        cArray[be.g[13]] = g[11];
        cArray[be.g[76]] = g[76];
        f = new String(cArray);
    }

    public static String[] a(String string, String string2, int n2) {
        String[] stringArray;
        int n3 = string.indexOf(string2);
        if (be.c(n3)) {
            stringArray = be.a(string.substring(n3 + string2.length()), string2, n2 + g[5]);
            "".length();
            if (" ".length() < 0) {
                return null;
            }
        } else {
            stringArray = new String[n2 + g[5]];
            n3 = string.length();
        }
        stringArray[n2] = string.substring(g[3], n3);
        return stringArray;
    }

    private static boolean b(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean a(int n2) {
        return n2 > 0;
    }

    public static void c() {
        String[] stringArray;
        try {
            stringArray = new ByteArrayInputStream(an.b("abcdip"));
            DataInputStream dataInputStream = new DataInputStream((InputStream)stringArray);
            if (be.a(stringArray.available())) {
                int n2 = dataInputStream.readInt();
                b = new String[n2];
                e = new String[n2];
                a = new int[n2];
                d = new byte[n2];
                c = new int[n2];
                int n3 = g[3];
                while (be.a(n3, n2)) {
                    be.b[n3] = dataInputStream.readUTF();
                    be.e[n3] = dataInputStream.readUTF();
                    be.a[n3] = dataInputStream.readInt();
                    be.d[n3] = dataInputStream.readByte();
                    be.c[n3] = n3;
                    ++n3;
                    "".length();
                    if (((0x65 ^ 0x3A ^ (0x3E ^ 0x7A)) & (0xB2 ^ 0xBA ^ (0xBE ^ 0xAD) ^ -" ".length())) <= 0) continue;
                    return;
                }
                stringArray.close();
                dataInputStream.close();
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((7 ^ 3) < " ".length()) {
            return;
        }
        stringArray = be.a(f.trim(), ",", g[3]);
        b = new String[stringArray.length];
        e = new String[stringArray.length];
        a = new int[stringArray.length];
        d = new byte[stringArray.length];
        c = new int[stringArray.length];
        int n4 = g[3];
        while (be.a(n4, stringArray.length)) {
            String[] stringArray2 = be.a(stringArray[n4].trim(), ":", g[3]);
            be.b[n4] = stringArray2[g[3]];
            be.e[n4] = stringArray2[g[5]];
            be.a[n4] = Integer.parseInt(stringArray2[g[7]]);
            be.d[n4] = Byte.parseByte(stringArray2[g[8]]);
            be.c[n4] = n4;
            ++n4;
            "".length();
            if (-" ".length() <= ((0xA7 ^ 0xC4 ^ (0x8E ^ 0xA6)) & (89 + 93 - 133 + 179 ^ 171 + 19 - 132 + 117 ^ -" ".length()))) continue;
            return;
        }
        be.b();
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    private static boolean c(int n2) {
        return n2 >= 0;
    }

    private static String a(String string) {
        String string2 = "";
        try {
            int n2;
            HttpConnection httpConnection = (HttpConnection)Connector.open((String)string);
            if (be.b(httpConnection.getResponseCode(), g[0])) {
                throw new IOException(httpConnection.getResponseMessage());
            }
            InputStream inputStream = httpConnection.openInputStream();
            new InputStreamReader(inputStream, "utf-8");
            "".length();
            while (be.b(n2 = inputStream.read(), g[1])) {
                string2 = String.valueOf(string2) + (char)n2;
                "".length();
                if ((0x7B ^ 0x7E) > 0) continue;
                return null;
            }
            "".length();
        }
        catch (Exception exception) {
            string2 = "";
        }
        if (-(0x30 ^ 0x35) >= 0) {
            return null;
        }
        return string2;
    }
}

