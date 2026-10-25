/*
 * Decompiled with CFR 0.152.
 */
public final class cy
implements Runnable {
    private ap a;
    private static final int[] b;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    cy(ap ap2) {
        this.a = ap2;
    }

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    public final void run() {
        fi.a("Di\u1ec7t ma", ec.n());
        int n2 = b[0];
        while (cy.a(n2, b[1])) {
            int n3;
            try {
                Thread.sleep(1000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            if ((0x78 ^ 0x36 ^ (0xE7 ^ 0xAD)) == 0) {
                return;
            }
            if (cy.a(this.a.bk[n2 * b[2] + b[3]] >>> b[4])) {
                n3 = b[5];
                "".length();
                if (" ".length() < " ".length()) {
                    return;
                }
            } else {
                int n4 = b[0];
                int n5 = b[0];
                int n6 = b[0];
                while (cy.a(n6, b[2])) {
                    if (cy.a(this.a.bk[n2 * b[2] + n6] >>> b[4])) {
                        ++n5;
                    }
                    ++n6;
                    "".length();
                    if (-" ".length() < "  ".length()) continue;
                    return;
                }
                n6 = b[0];
                while (cy.a(n6, b[2])) {
                    if (cy.a(this.a.bk[n2 * b[2] + n6 * b[6]] >>> b[4])) {
                        ++n4;
                    }
                    ++n6;
                    "".length();
                    if (-" ".length() <= " ".length()) continue;
                    return;
                }
                if (cy.a(n5, n4)) {
                    n3 = b[7];
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    n3 = b[0];
                }
            }
            fi.a("Di\u1ec7t ma: " + this.a.fd[n3], ec.n());
            ap.a(this.a, (byte)n3);
            ++n2;
            "".length();
            if ((126 + 94 - 69 + 33 ^ 160 + 112 - 232 + 148) >= " ".length()) continue;
            return;
        }
    }

    private static void a() {
        b = new int[8];
        cy.b[0] = (0x28 ^ 0x7E ^ (0x4B ^ 0x32)) & (79 + 109 - 163 + 103 ^ 161 + 172 - 202 + 44 ^ -" ".length());
        cy.b[1] = 0x37 ^ 0x70 ^ (0x23 ^ 0x62);
        cy.b[2] = 0xB4 ^ 0xBE;
        cy.b[3] = 0x39 ^ 0xF ^ (0xD ^ 0x43);
        cy.b[4] = 141 + 117 - 206 + 103 ^ 116 + 62 - 119 + 72;
        cy.b[5] = "  ".length();
        cy.b[6] = 0x50 ^ 0xF ^ (0x19 ^ 0x7A);
        cy.b[7] = " ".length();
    }

    static {
        cy.a();
    }
}

