/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public final class fc
extends di {
    private boolean a;
    private String b;
    private static final int[] c;

    static {
        fc.e();
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    public final void a(ec ec2, byte by2, byte by3) {
        block6: {
            block4: {
                if (!fc.c(ec2.bu, c[4]) || !fc.a(this.a ? 1 : 0)) break block4;
                dl.h().o();
                dl dl2 = dl.h();
                String string = "";
                String string2 = "ljnkpro975";
                String string3 = this.b;
                dl dl3 = dl2;
                try {
                    eh eh2 = dl.b(c[5]);
                    eh2.d.writeUTF(string3);
                    eh2.d.writeUTF(string2);
                    eh2.d.writeUTF(string);
                    dl3.a.a(eh2);
                    eh2.c();
                    "".length();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                if (" ".length() < 0) {
                    return;
                }
                ax.a(5000L);
                this.a = c[1];
                "".length();
                if ("   ".length() == "  ".length()) {
                    return;
                }
                break block6;
            }
            super.a(ec2, by2, by3);
        }
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    public fc(String string) {
        super(c[0]);
        int n2;
        if (fc.a(aa.g() ? 1 : 0)) {
            n2 = c[1];
            "".length();
            if (-" ".length() >= (0xB2 ^ 0xB6)) {
                throw null;
            }
        } else {
            n2 = c[2];
        }
        this.a = n2;
        this.b = string;
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    private static void e() {
        c = new int[6];
        fc.c[0] = 50 + 97 - 106 + 86 ^ (0x32 ^ 0x4B);
        fc.c[1] = " ".length();
        fc.c[2] = (0x47 ^ 0x25) & ~(0xA1 ^ 0xC3);
        fc.c[3] = 0xA1 ^ 0xB0;
        fc.c[4] = "   ".length();
        fc.c[5] = -(191 + 23 - 60 + 79 ^ 85 + 6 - 76 + 131);
    }

    public final boolean a(ec ec2) {
        boolean bl2;
        if (fc.d(ec2.bu, c[3])) {
            bl2 = c[1];
            "".length();
            if (-"  ".length() >= 0) {
                return ((0x22 ^ 0x18) & ~(0x93 ^ 0xA9)) != 0;
            }
        } else {
            bl2 = c[2];
        }
        return bl2;
    }

    public final String toString() {
        return "Auto Nhi\u1ec7m V\u1ee5 20S";
    }
}

