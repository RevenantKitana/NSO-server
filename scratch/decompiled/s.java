/*
 * Decompiled with CFR 0.152.
 */
public final class s
extends ba {
    private int a;
    private static final int[] b;

    public s() {
        this.a = ec.n().bu;
    }

    static {
        s.e();
    }

    private static boolean c(int n2, int n3) {
        return n2 > n3;
    }

    public final String toString() {
        return "Tr\u1ea3 NV";
    }

    public final void a(ec ec2, byte by2, byte by3) {
    }

    private static void e() {
        b = new int[2];
        s.b[0] = " ".length();
        s.b[1] = (0x58 ^ 0x31 ^ (0x1C ^ 0x48)) & (0x3B ^ 0x65 ^ (0xD6 ^ 0xB5) ^ -" ".length());
    }

    public final boolean a(ec ec2) {
        boolean bl2;
        if (s.c(ec2.bu, this.a)) {
            bl2 = b[0];
            "".length();
            if (-" ".length() == "   ".length()) {
                return ((0x58 ^ 0x47 ^ " ".length()) & (0x3B ^ 6 ^ (0x70 ^ 0x53) ^ -" ".length())) != 0;
            }
        } else {
            bl2 = b[1];
        }
        return bl2;
    }
}

