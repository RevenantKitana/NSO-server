/*
 * Decompiled with CFR 0.152.
 */
public final class ea {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    private static final int[] q;
    public int i;
    public String j = "";
    public String k = "";
    public String l = "";
    public el[] m;
    public String n = "";
    public int o;
    public String p = "";

    public ea() {
        new ad();
        "".length();
    }

    private static boolean a(int n2, int n3) {
        return n2 == n3;
    }

    private static void a() {
        q = new int[7];
        ea.q[0] = (0x97 ^ 0x8D) & ~(0x15 ^ 0xF);
        ea.q[1] = " ".length();
        ea.q[2] = "  ".length();
        ea.q[3] = "   ".length();
        ea.q[4] = 0x75 ^ 0x5C ^ (0x17 ^ 0x3A);
        ea.q[5] = 0xC4 ^ 0xB8 ^ (0x25 ^ 0x5C);
        ea.q[6] = 0xC6 ^ 0xAD ^ (0xDF ^ 0xB2);
    }

    public final void a(String string) {
        String[] stringArray = bz.a(string, "\n");
        this.k = "";
        try {
            int n2 = q[0];
            while (ea.b(n2, stringArray.length)) {
                String string2 = stringArray[n2].trim();
                if (ea.a(string2.equals("") ? 1 : 0)) {
                    try {
                        String[] stringArray2 = bz.a(string2, ",");
                        String string3 = stringArray2[q[0]];
                        int n3 = Integer.parseInt(stringArray2[q[1]]);
                        if (ea.a(n3)) {
                            string3 = "c0".concat(String.valueOf(string3));
                            string3 = string3 + ag.j[q[1]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                            "".length();
                            if (null != null) {
                                return;
                            }
                        } else if (ea.a(n3, q[1])) {
                            string3 = "c1".concat(String.valueOf(string3));
                            string3 = string3 + " " + ag.j[q[2]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                            "".length();
                            if (-(60 + 32 - 28 + 74 ^ 68 + 107 - 63 + 31) >= 0) {
                                return;
                            }
                        } else if (ea.a(n3, q[2])) {
                            string3 = "c2".concat(String.valueOf(string3));
                            string3 = string3 + " " + ag.j[q[3]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                            "".length();
                            if ((0xB0 ^ 0xBA ^ (0xB8 ^ 0xB6)) <= 0) {
                                return;
                            }
                        } else if (ea.a(n3, q[3])) {
                            string3 = "c1".concat(String.valueOf(string3));
                            string3 = string3 + " " + ag.j[q[4]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                            "".length();
                            if ("   ".length() <= "  ".length()) {
                                return;
                            }
                        } else if (ea.a(n3, q[4])) {
                            string3 = "c1".concat(String.valueOf(string3));
                            string3 = string3 + ag.j[q[5]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                            "".length();
                            if ((0xC3 ^ 0x95 ^ (0x1A ^ 0x49)) == 0) {
                                return;
                            }
                        } else if (ea.a(n3, q[5])) {
                            string3 = "c2".concat(String.valueOf(string3));
                            string3 = string3 + " " + ag.j[q[6]] + " " + bz.b(stringArray2[q[2]]) + " " + ag.j[q[0]] + " " + stringArray2[q[3]];
                        }
                        this.k = this.k + string3 + "\n";
                        "".length();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if ("   ".length() == -" ".length()) {
                        return;
                    }
                }
                ++n2;
                "".length();
                if ("  ".length() > 0) continue;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (null != null) {
            return;
        }
    }

    private static boolean a(int n2) {
        return n2 == 0;
    }

    static {
        ea.a();
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }
}

