/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Image;

public final class cf
implements ac {
    private eh a;
    private static String b;
    private static cf c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;
    private static final int[] h;
    private static String i;
    private static String[] j;

    private static boolean a(int n2, int n3) {
        return n2 < n3;
    }

    public final void c() {
        if (cf.a(ec.eb ? 1 : 0) && cf.a(ey.u)) {
            dp.g().h();
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        } else {
            em.a(ag.ix, h[0]);
        }
    }

    /*
     * Exception decompiling
     */
    public final void a(eh var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static void b(eh eh2) {
        try {
            byte by2 = eh2.a.readByte();
            int n2 = h[4];
            while (cf.a(n2, by2)) {
                short s2 = (short)eh2.a.readUnsignedByte();
                boolean bl2 = eh2.a.readBoolean();
                boolean bl3 = eh2.a.readBoolean();
                boolean bl4 = eh2.a.readBoolean();
                boolean bl5 = eh2.a.readBoolean();
                boolean bl6 = eh2.a.readBoolean();
                int n3 = eh2.a.readUnsignedByte();
                byte by3 = eh2.a.readByte();
                int n4 = eh2.a.readInt();
                int n5 = eh2.a.readUnsignedByte();
                int n6 = eh2.a.readInt();
                short s3 = eh2.a.readShort();
                short s4 = eh2.a.readShort();
                byte by4 = eh2.a.readByte();
                byte by5 = eh2.a.readByte();
                boolean bl7 = eh2.a.readBoolean();
                ap.a(new by(s2, bl2, bl3, bl4, bl5, bl6, n3, by3, n4, n5, n6, s3, s4, by4, by5, bl7, h[3]), n2);
                n2 = (byte)(n2 + h[3]);
                "".length();
                if ((0x1D ^ 0x19) > ((0xDD ^ 0x97) & ~(0x1B ^ 0x51))) continue;
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err addMob");
        }
        if (-(0x8C ^ 0x89) >= 0) {
            return;
        }
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    public static Image a(byte[] byArray) {
        try {
            return Image.createImage((byte[])byArray, (int)h[4], (int)byArray.length);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void a(DataInputStream dataInputStream) {
        try {
            ap.cq = dataInputStream.readByte();
            an.a("nj_arrow", bz.a(dataInputStream));
            an.a("nj_effect", bz.a(dataInputStream));
            an.a("nj_image", bz.a(dataInputStream));
            an.a("nj_part", bz.a(dataInputStream));
            an.a("nj_skill", bz.a(dataInputStream));
            byte[][] byArrayArray = new byte[dataInputStream.readByte()][];
            ap.by = byArrayArray;
            ap.ba = new byte[byArrayArray.length][];
            int n2 = h[4];
            while (cf.a(n2, ap.by.length)) {
                ap.by[n2] = new byte[dataInputStream.readByte()];
                ap.ba[n2] = new byte[ap.by[n2].length];
                int n3 = h[4];
                while (cf.a(n3, ap.by[n2].length)) {
                    ap.by[n2][n3] = dataInputStream.readByte();
                    ap.ba[n2][n3] = dataInputStream.readByte();
                    ++n3;
                    "".length();
                    if (-" ".length() <= ((212 + 232 - 325 + 133 ^ 105 + 177 - 234 + 143) & (0xBB ^ 0x9A ^ (0xF9 ^ 0x9B) ^ -" ".length()))) continue;
                    return;
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
            ap.al = new long[dataInputStream.readUnsignedByte()];
            n2 = h[4];
            while (cf.a(n2, ap.al.length)) {
                ap.al[n2] = dataInputStream.readLong();
                ++n2;
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            ap.fa = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.fa.length)) {
                ap.fa[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if ("   ".length() >= "   ".length()) continue;
                return;
            }
            ap.de = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.de.length)) {
                ap.de[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            ap.ec = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.ec.length)) {
                ap.ec[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if ("  ".length() > 0) continue;
                return;
            }
            ap.eg = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.eg.length)) {
                ap.eg[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (-" ".length() < ((29 + 97 - -1 + 1 ^ 132 + 155 - 203 + 73) & (0xC2 ^ 0xB3 ^ (0xE5 ^ 0x89) ^ -" ".length()))) continue;
                return;
            }
            ap.cu = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.cu.length)) {
                ap.cu[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (((0xF7 ^ 0xA6) & ~(0xF3 ^ 0xA2)) == 0) continue;
                return;
            }
            ap.cx = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.cx.length)) {
                ap.cx[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (((0xAD ^ 0xB5 ^ (0x23 ^ 0x6D)) & (0xE9 ^ 0xB2 ^ (0xAC ^ 0xA1) ^ -" ".length())) > -" ".length()) continue;
                return;
            }
            ap.v = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.v.length)) {
                ap.v[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (-" ".length() != " ".length()) continue;
                return;
            }
            ap.ez = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.ez.length)) {
                ap.ez[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (((3 + 230 - 100 + 121 ^ 67 + 150 - 190 + 165) & (28 + 78 - 87 + 163 ^ 85 + 10 - 85 + 126 ^ -" ".length())) <= 0) continue;
                return;
            }
            ap.n = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.n.length)) {
                ap.n[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (" ".length() < "   ".length()) continue;
                return;
            }
            ap.dh = new int[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, ap.dh.length)) {
                ap.dh[n2] = dataInputStream.readInt();
                ++n2;
                "".length();
                if (((0x7F ^ 0x67) & ~(0xAB ^ 0xB3)) != "   ".length()) continue;
                return;
            }
            au.b = new dr[dataInputStream.readByte()];
            n2 = h[4];
            while (cf.a(n2, au.b.length)) {
                au.b[n2] = new dr();
                au.b[n2].b = dataInputStream.readByte();
                au.b[n2].c = dataInputStream.readByte();
                dataInputStream.readUTF();
                "".length();
                au.b[n2].a = dataInputStream.readShort();
                ++n2;
                "".length();
                if (((0x42 ^ 0x5E) & ~(0x13 ^ 0xF)) == 0) continue;
                return;
            }
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if ("   ".length() != "   ".length()) {
            return;
        }
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static void c(eh eh2) {
        try {
            el el2 = null;
            int n2 = eh2.a.readInt();
            int n3 = h[4];
            while (cf.a(n3, ap.fc.length)) {
                if (cf.c(ap.fc[n3].c.w, n2)) {
                    el2 = ap.fc[n3].c;
                    "".length();
                    if (-" ".length() >= -" ".length()) break;
                    return;
                }
                ++n3;
                "".length();
                if (-" ".length() < ((72 + 74 - 88 + 69 ^ (0x20 ^ 0x78)) & (0xCF ^ 0x93 ^ (0x1F ^ 0x64) ^ -" ".length()))) continue;
                return;
            }
            el2.r = h[5];
            el2.u = -1L;
            el2.e = eh2.a.readInt();
            if (!cf.e(el2.m() ? 1 : 0) || cf.a(el2.i() ? 1 : 0)) {
                el2.b = new ad();
                try {
                    el2.y = eh2.a.readByte();
                    el2.v = eh2.a.readByte();
                    do {
                        el2.b.addElement(new cs(eh2.a.readByte(), eh2.a.readInt()));
                        "".length();
                    } while (((0x24 ^ 0x28 ^ (7 ^ 0x3F)) & (0x51 ^ 0x14 ^ (0xF8 ^ 0x89) ^ -" ".length())) != "   ".length());
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-"  ".length() > 0) {
            return;
        }
    }

    private static void e() {
        h = new int[106];
        cf.h[0] = -(0xFFFFDBD9 & 0x3D2F) & (0xFFFFFBBF & 0x3FFC);
        cf.h[1] = 0x69 ^ 0x62;
        cf.h[2] = "   ".length();
        cf.h[3] = " ".length();
        cf.h[4] = (0x20 ^ 0x2B) & ~(0xBE ^ 0xB5);
        cf.h[5] = 19 + 28 - 30 + 140 ^ 86 + 25 - 48 + 121;
        cf.h[6] = -" ".length();
        cf.h[7] = (0x5D ^ 0x21) + (61 + 72 - 30 + 25) - (26 + 49 - 21 + 83) + (0xA3 ^ 0x80);
        cf.h[8] = "  ".length();
        cf.h[9] = 0x1D ^ 0x19;
        cf.h[10] = 0x59 ^ 0x6B;
        cf.h[11] = 171 + 107 - 216 + 132 ^ 26 + 112 - 133 + 161;
        cf.h[12] = 0x93 ^ 0xC3;
        cf.h[13] = 0x10 ^ 0x38;
        cf.h[14] = -(-(0xFFFF95B3 & 0x7ADD) & (0xFFFFFF9F & 0x37FF));
        cf.h[15] = 0x4D ^ 0x78 ^ (0xD ^ 0x32);
        cf.h[16] = 0x91 ^ 0x82;
        cf.h[17] = 0xA9 ^ 0xB0;
        cf.h[18] = 170 + 3 - 2 + 67;
        cf.h[19] = 0x2E ^ 0x43 ^ (0xFE ^ 0x9C);
        cf.h[20] = 0x8D ^ 0x88;
        cf.h[21] = (0x3A ^ 0x22) + (0x8D ^ 0xB1) - -(0xE0 ^ 0x8D) + (0x87 ^ 0x8E);
        cf.h[22] = (5 ^ 0x18) + (141 + 126 - 166 + 43) - (0x7D ^ 0x36) + (0x2A ^ 0x18);
        cf.h[23] = 80 + 14 - 48 + 86 ^ 29 + 112 - 40 + 83;
        cf.h[24] = 0xFFFFFFFF & Short.MAX_VALUE;
        cf.h[25] = 0x1C ^ 0x56 ^ (0xD9 ^ 0x8B);
        cf.h[26] = (0x16 ^ 0x3F) + (0x31 ^ 0x6A) - (41 + 122 - 134 + 101) + (11 + 137 - 95 + 86);
        cf.h[27] = 0xF5 ^ 0xBE ^ (0x43 ^ 0x32);
        cf.h[28] = 0xA9 ^ 0xC2 ^ 15 + 47 - -27 + 38;
        cf.h[29] = 0x95 ^ 0xBE ^ (0xBD ^ 0x86);
        cf.h[30] = 0x80 ^ 0x91;
        cf.h[31] = 0xFFFF97DF & 0x6974;
        cf.h[32] = 0x2F ^ 0x1A;
        cf.h[33] = 108 + 59 - 97 + 85 ^ 42 + 112 - 8 + 11;
        cf.h[34] = 4 ^ 0x46;
        cf.h[35] = -(0xFFFFDF5F & 0x7DE5) & (0xFFFFFFFF & 0x7FFD);
        cf.h[36] = 0xFFFFBAFB & 0x67B6;
        cf.h[37] = 0x4A ^ 0xF ^ (0xE8 ^ 0xA3);
        cf.h[38] = 0x34 ^ 0x14;
        cf.h[39] = 0x94 ^ 0xB6;
        cf.h[40] = 0xAF ^ 0x8C;
        cf.h[41] = 0xAD ^ 0xBE ^ (0xBA ^ 0xBC);
        cf.h[42] = 0x1D ^ 0xB;
        cf.h[43] = 107 + 77 - 18 + 49 ^ 113 + 65 - 90 + 104;
        cf.h[44] = 112 + 101 - 166 + 91 ^ 84 + 102 - 122 + 80;
        cf.h[45] = "  ".length() ^ (0x18 ^ 1);
        cf.h[46] = 0x3F ^ 0x75 ^ (0x45 ^ 0x13);
        cf.h[47] = 0x70 ^ 0x30 ^ (0xF5 ^ 0xA8);
        cf.h[48] = 147 + 85 - 186 + 144 ^ 92 + 105 - 145 + 120;
        cf.h[49] = 0x41 ^ 0x3F ^ (0xF ^ 0x76);
        cf.h[50] = 0xBD ^ 0xB5;
        cf.h[51] = 0x41 ^ 0x48;
        cf.h[52] = -(0xFFFFEE2B & 0x35D5) & (0xFFFFFEFF & 0x17FF3);
        cf.h[53] = 0xFFFF8BFB & 0x77EC;
        cf.h[54] = -(0xFFFFE5AE & 0x3F5B) & (0xFFFFFFFF & 0x17FFF);
        cf.h[55] = 0xFFFFFBF6 & 0x15EFD;
        cf.h[56] = 0xFFFFFAFF & 0x15FEA;
        cf.h[57] = -(0x1E ^ 0xF) & (0xFFFFFEFF & 0x15BFB);
        cf.h[58] = 199 + 47 - 96 + 50 ^ 183 + 184 - 187 + 16;
        cf.h[59] = 15 + 36 - 30 + 141 ^ 117 + 136 - 188 + 94;
        cf.h[60] = -(0xFFFFEF3D & 0x7CC6) & (0xFFFFFFF7 & 0x6DFF);
        cf.h[61] = -(0xFFFFF9A7 & 0x275F) & (0xFFFFFBFF & 0x17FF7);
        cf.h[62] = -(0xFFFFACA9 & 0x7357) & (0xFFFFFFFC & 0x17AEF);
        cf.h[63] = 0x79 ^ 0x20;
        cf.h[64] = 0x5D ^ 0x66;
        cf.h[65] = 0xFFFFFABF & 0x27F7;
        cf.h[66] = -(0xFFFFFF5D & 0x15A7) & (0xFFFFFFFD & 0x37BE);
        cf.h[67] = 0xC4 ^ 0x89;
        cf.h[68] = -(0xFFFFD4AF & 0x2F5A) & (0xFFFFDFFF & 0x17EFB);
        cf.h[69] = -(0x5C ^ 0x64) & (0xFFFFFF3F & 0x15BFF);
        cf.h[70] = -(0xFFFFBEF1 & 0x657F) & (0xFFFFFFFF & 0x17F79);
        cf.h[71] = -(0xFFFFD7AF & 0x7D51) & (0xFFFFF7FA & 0x7FBF);
        cf.h[72] = -(0xFFFFFEDD & 0x7F3A) & (0xFFFFFFFF & 0x7F7F);
        cf.h[73] = 15 + 89 - 47 + 71;
        cf.h[74] = 149 + 86 - 56 + 29;
        cf.h[75] = 0x27 ^ 0x7D ^ (0x12 ^ 0x56);
        cf.h[76] = 0xFFFFA7B7 & 0x1DEE8;
        cf.h[77] = 0x5B ^ 0x7C;
        cf.h[78] = 0xD8 ^ 0xBA;
        cf.h[79] = 0xCD ^ 0xAE;
        cf.h[80] = 113 + 202 - 116 + 20;
        cf.h[81] = -(0xFFFFCF5B & 0x35AC) & (0xFFFFFFF7 & 0x15FFF);
        cf.h[82] = -(0xFFFFE29B & 0x3D65) & (0xFFFFFFFD & 0x17AFF);
        cf.h[83] = -(0xFFFFE877 & 0x57AF) & (0xFFFFDFF7 & 0x67FE);
        cf.h[84] = -(0xFFFFFB7F & 0x5E95) & (0xFFFFFFF7 & 0x5FFE);
        cf.h[85] = -(142 + 173 - 275 + 153) & (0xFFFFDBDA & 0x17FEF);
        cf.h[86] = 0xFFFFFFFF & 0x15AF9;
        cf.h[87] = 0xFFFFFEFB & 0x15BFE;
        cf.h[88] = 69 + 105 - 160 + 122 ^ 138 + 57 - 130 + 131;
        cf.h[89] = 0xAD ^ 0x9D;
        cf.h[90] = 0x41 ^ 0x6A;
        cf.h[91] = 34 + 132 - 116 + 105 ^ 22 + 114 - 21 + 35;
        cf.h[92] = 0x58 ^ 0x47;
        cf.h[93] = 0xE5 ^ 0xA6;
        cf.h[94] = -(0xFFFFDD29 & 0x27D7) & (0xFFFFDFFF & 0x17FFE);
        cf.h[95] = 0xFFFFFEFF & 0x15BFF;
        cf.h[96] = 0xC2 ^ 0x84;
        cf.h[97] = 0x46 ^ 0x31;
        cf.h[98] = 0x1C ^ 8 ^ (0xFE ^ 0xC3);
        cf.h[99] = 0xFFFF8FFF & 0x71E5;
        cf.h[100] = -(0xFFFFFCF3 & 0x777D) & (0xFFFFFF7E & 0x76FD);
        cf.h[101] = -(0xFFFFBFE7 & 0x745D) & (0xFFFFBDFF & 0x77FF);
        cf.h[102] = -(0xFFFFED5F & 0x76B5) & (0xFFFFE61F & Short.MAX_VALUE);
        cf.h[103] = 185 + 25 - 6 + 29;
        cf.h[104] = 72 + 126 - 120 + 51 + (0x67 ^ 0x3D) - (0x2B ^ 0x6C) + (0x3D ^ 0x6B);
        cf.h[105] = 93 + 134 - 92 + 35 + (0xA1 ^ 0xAB) - -(0x1A ^ 9) + (7 ^ 0x23);
    }

    private static boolean a(Object object) {
        return object != null;
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * Exception decompiling
     */
    private void d(eh var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 45[SWITCH]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static void e(eh eh2) {
        try {
            int n2 = eh2.a.readUnsignedByte();
            el el2 = ap.dm.ff[n2];
            ap.dm.ff[n2].u = eh2.a.readLong();
            el2.e = eh2.a.readInt();
            el2.v = eh2.a.readByte();
            el2.b = new ad();
            try {
                do {
                    el2.b.addElement(new cs(eh2.a.readByte(), eh2.a.readInt()));
                    "".length();
                } while (null == null);
                return;
            }
            catch (Exception exception) {
                "".length();
                if (null != null) {
                    return;
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    private static boolean d(int n2, int n3) {
        return n2 >= n3;
    }

    private static void f(eh eh2) {
        try {
            byte by2 = eh2.a.readByte();
            int n2 = eh2.a.readUnsignedByte();
            el el2 = null;
            block2 : switch (by2) {
                case 2: {
                    el2 = ap.y[n2];
                    "".length();
                    if (null == null) break;
                    return;
                }
                case 3: {
                    int n3;
                    el2 = ec.n().en[n2];
                    if (cf.a(el2)) {
                        "".length();
                        if (" ".length() <= (0xB3 ^ 0xB7)) break;
                        return;
                    }
                    if (cf.a(ap.bo) && cf.c(ap.bo.n, n2)) {
                        el2 = ap.bo;
                    }
                    if (cf.a(ap.fn) && cf.c(ap.fn.n, n2)) {
                        el2 = ap.fn;
                    }
                    if (cf.a(ap.cj) && cf.c(ap.cj.n, n2)) {
                        el2 = ap.cj;
                    }
                    if (cf.b(el2) && cf.a(ap.bt)) {
                        n3 = h[4];
                        while (cf.a(n3, ap.bt.length)) {
                            if (cf.a(ap.bt[n3]) && cf.c(ap.bt[n3].n, n2)) {
                                el2 = ap.bt[n3];
                                "".length();
                                if (null == null) break;
                                return;
                            }
                            ++n3;
                            "".length();
                            if ("  ".length() <= "  ".length()) continue;
                            return;
                        }
                    }
                    if (cf.b(el2) && cf.a(ap.dq)) {
                        n3 = h[4];
                        while (cf.a(n3, ap.dq.length)) {
                            if (cf.a(ap.dq[n3]) && cf.c(ap.dq[n3].n, n2)) {
                                el2 = ap.dq[n3];
                                "".length();
                                if ("   ".length() > 0) break;
                                return;
                            }
                            ++n3;
                            "".length();
                            if (" ".length() >= 0) continue;
                            return;
                        }
                    }
                    if (cf.b(el2) && cf.a(ap.af)) {
                        n3 = h[4];
                        while (cf.a(n3, ap.af.length)) {
                            if (cf.a(ap.af[n3]) && cf.c(ap.af[n3].n, n2)) {
                                el2 = ap.af[n3];
                                "".length();
                                if (-" ".length() == -" ".length()) break;
                                return;
                            }
                            ++n3;
                            "".length();
                            if (null == null) continue;
                            return;
                        }
                    }
                    if (cf.b(el2) && cf.a(ap.ag)) {
                        n3 = h[4];
                        while (cf.a(n3, ap.ag.length)) {
                            if (cf.a(ap.ag[n3]) && cf.c(ap.ag[n3].n, n2)) {
                                el2 = ap.ag[n3];
                                "".length();
                                if (null == null) break;
                                return;
                            }
                            ++n3;
                            "".length();
                            if ("   ".length() != " ".length()) continue;
                            return;
                        }
                    }
                    if (!cf.b(el2)) break;
                    if (cf.b(ap.bm)) {
                        "".length();
                        if ("  ".length() == "  ".length()) break;
                        return;
                    }
                    n3 = h[4];
                    do {
                        if (cf.d(n3, ap.bm.length)) {
                            "".length();
                            if (null == null) break block2;
                            return;
                        }
                        if (cf.a(ap.bm[n3]) && cf.c(ap.bm[n3].n, n2)) {
                            el2 = ap.bm[n3];
                            "".length();
                            if (null == null) break block2;
                            return;
                        }
                        ++n3;
                        "".length();
                    } while ((0x2D ^ 0x29) >= 0);
                    return;
                }
                case 4: {
                    el2 = ec.n().ef[n2];
                    "".length();
                    if (null == null) break;
                    return;
                }
                case 5: {
                    el2 = ec.n().ff[n2];
                    "".length();
                    if ((0x90 ^ 0x94) >= 0) break;
                    return;
                }
                case 6: {
                    el2 = ap.ey[n2];
                    "".length();
                    if ((0x32 ^ 0x5D ^ (8 ^ 0x62)) != 0) break;
                    return;
                }
                case 7: {
                    el2 = ap.cz[n2];
                    "".length();
                    if (-(0x1D ^ 0x18) < 0) break;
                    return;
                }
                case 8: {
                    el2 = ap.di[n2];
                    "".length();
                    if (null == null) break;
                    return;
                }
                case 9: {
                    el2 = ap.bu[n2];
                }
                default: {
                    "".length();
                    if (-" ".length() != (1 ^ 5)) break;
                    return;
                }
                case 14: {
                    el2 = ap.l[n2];
                    "".length();
                    if ("   ".length() != 0) break;
                    return;
                }
                case 15: {
                    el2 = ap.aw[n2];
                    "".length();
                    if ((0x18 ^ 0x57 ^ (0x8F ^ 0xC4)) == (0xD1 ^ 0x9E ^ (0x78 ^ 0x33))) break;
                    return;
                }
                case 16: {
                    el2 = ap.df[n2];
                    "".length();
                    if (((0xDB ^ 0xBA) & ~(0xDB ^ 0xBA)) == 0) break;
                    return;
                }
                case 17: {
                    el2 = ap.ea[n2];
                    "".length();
                    if ("  ".length() > -" ".length()) break;
                    return;
                }
                case 18: {
                    el2 = ap.ds[n2];
                    "".length();
                    if (" ".length() >= -" ".length()) break;
                    return;
                }
                case 19: {
                    el2 = ap.w[n2];
                    "".length();
                    if ("   ".length() >= -" ".length()) break;
                    return;
                }
                case 20: {
                    el2 = ap.fe[n2];
                    "".length();
                    if (((0x84 ^ 0x93 ^ (0xAF ^ 0xB2)) & (199 + 20 - 202 + 184 ^ 76 + 175 - 98 + 42 ^ -" ".length())) <= 0) break;
                    return;
                }
                case 21: {
                    el2 = ap.am[n2];
                    "".length();
                    if ("   ".length() > 0) break;
                    return;
                }
                case 22: {
                    el2 = ap.r[n2];
                    "".length();
                    if (((0x5B ^ 5) & ~(0xC6 ^ 0x98)) > -" ".length()) break;
                    return;
                }
                case 23: {
                    el2 = ap.em[n2];
                    "".length();
                    if (-" ".length() < (0x67 ^ 0x63)) break;
                    return;
                }
                case 24: {
                    el2 = ap.br[n2];
                    "".length();
                    if ((1 ^ 0x73 ^ (6 ^ 0x70)) != "  ".length()) break;
                    return;
                }
                case 25: {
                    el2 = ap.doa[n2];
                    "".length();
                    if ((0x80 ^ 0x84) == (0x74 ^ 0x70)) break;
                    return;
                }
                case 26: {
                    el2 = ap.be[n2];
                    "".length();
                    if (-" ".length() <= 0) break;
                    return;
                }
                case 27: {
                    el2 = ap.ae[n2];
                    "".length();
                    if ("   ".length() >= (" ".length() & ~" ".length())) break;
                    return;
                }
                case 28: {
                    el2 = ap.a[n2];
                    "".length();
                    if (((0x28 ^ 0x71) & ~(0x9E ^ 0xC7)) <= 0) break;
                    return;
                }
                case 29: {
                    el2 = ap.bf[n2];
                    "".length();
                    if (((0x90 ^ 0xAB) & ~(0xB7 ^ 0x8C)) == 0) break;
                    return;
                }
                case 30: {
                    el2 = ap.es[n2];
                    "".length();
                    if ("   ".length() >= ((0x8E ^ 0x84) & ~(0x79 ^ 0x73))) break;
                    return;
                }
                case 32: {
                    el2 = ap.ac[n2];
                    "".length();
                    if (" ".length() <= "   ".length()) break;
                    return;
                }
                case 34: {
                    el2 = ap.fm[n2];
                    "".length();
                    if ("   ".length() != -" ".length()) break;
                    return;
                }
                case 35: {
                    el2 = ap.du[n2];
                    "".length();
                    if (-" ".length() < ((0x76 ^ 0x2C ^ (0x3D ^ 0x53)) & (67 + 115 - 44 + 104 ^ 150 + 144 - 270 + 174 ^ -" ".length()))) break;
                    return;
                }
                case 39: {
                    el2 = ec.eu.m[ap.dz];
                }
            }
            el2.u = eh2.a.readLong();
            if (cf.a(el2.r() ? 1 : 0)) {
                el2.e = eh2.a.readInt();
                "".length();
                if ("   ".length() < -" ".length()) {
                    return;
                }
            } else if (!(cf.e(el2.f() ? 1 : 0) && cf.e(el2.u() ? 1 : 0) && cf.e(el2.p() ? 1 : 0) && cf.e(el2.j() ? 1 : 0) && cf.e(el2.a() ? 1 : 0) && !cf.a(el2.g() ? 1 : 0))) {
                el2.f = eh2.a.readInt();
                el2.p = eh2.a.readInt();
                el2.i = eh2.a.readInt();
            }
            if (cf.e(el2.m() ? 1 : 0) && cf.e(el2.h() ? 1 : 0) && cf.e(el2.i() ? 1 : 0)) {
                if (cf.c(el2.m.d, h[103])) {
                    el2.o = cf.a(bz.a(eh2));
                    "".length();
                    if (((0x57 ^ 0x1F ^ (0xFA ^ 0x91)) & (0x36 ^ 0x28 ^ (0x39 ^ 4) ^ -" ".length())) != 0) {
                        return;
                    }
                } else if (cf.c(el2.m.d, h[104])) {
                    el2.o = cf.a(bz.a(eh2));
                    "".length();
                    if ((0x7D ^ 0x20 ^ (2 ^ 0x5B)) <= "   ".length()) {
                        return;
                    }
                } else if (cf.c(el2.m.d, h[105])) {
                    el2.o = cf.a(bz.a(eh2));
                    "".length();
                    if ((0x34 ^ 0x30) == 0) {
                        return;
                    }
                }
            } else {
                el2.v = eh2.a.readByte();
                el2.b = new ad();
                try {
                    do {
                        el2.b.addElement(new cs(eh2.a.readByte(), eh2.a.readInt()));
                        "".length();
                    } while ("  ".length() <= (0x96 ^ 0x92));
                    return;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            el2.l = h[3];
            if (cf.c(by2, h[20])) {
                ec.n().aa();
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Controller.requestItemInfo()");
        }
        if ("   ".length() > "   ".length()) {
            return;
        }
        bo.i();
    }

    private static void g(eh eh2) {
        try {
            int n2;
            ax.m();
            ax.k();
            ax.g = h[4];
            ec ec2 = ec.n();
            ec.n().i = null;
            short s2 = eh2.a.readShort();
            ec2.bm = s2;
            ec2.s = s2;
            short s3 = eh2.a.readShort();
            ec2.bb = s3;
            ec2.fc = s3;
            byte by2 = eh2.a.readByte();
            int n3 = h[4];
            while (cf.a(n3, by2)) {
                de.r.addElement(new n(eh2.a.readShort(), eh2.a.readShort(), eh2.a.readShort(), eh2.a.readShort()));
                ++n3;
                "".length();
                if ((" ".length() & (" ".length() ^ -" ".length())) < "   ".length()) continue;
                return;
            }
            ax.m();
            by2 = eh2.a.readByte();
            int n4 = h[4];
            while (cf.a(n4, by2)) {
                ap.a(new by((short)n4, eh2.a.readBoolean(), eh2.a.readBoolean(), eh2.a.readBoolean(), eh2.a.readBoolean(), eh2.a.readBoolean(), eh2.a.readUnsignedByte(), eh2.a.readByte(), eh2.a.readInt(), eh2.a.readUnsignedByte(), eh2.a.readInt(), eh2.a.readShort(), eh2.a.readShort(), eh2.a.readByte(), eh2.a.readByte(), eh2.a.readBoolean(), h[4]), n4);
                n4 = (byte)(n4 + h[3]);
                "".length();
                if ("  ".length() > ("  ".length() & ("  ".length() ^ -" ".length()))) continue;
                return;
            }
            by2 = eh2.a.readByte();
            n4 = h[4];
            while (cf.a(n4, by2)) {
                ap.aa.addElement(new dc(eh2.a.readUTF(), eh2.a.readShort(), eh2.a.readShort()));
                n4 = (byte)(n4 + h[3]);
                "".length();
                if ("  ".length() >= "  ".length()) continue;
                return;
            }
            by2 = eh2.a.readByte();
            n3 = h[4];
            while (cf.a(n3, by2)) {
                ap.bj.addElement(new ds(eh2.a.readByte(), eh2.a.readShort(), eh2.a.readShort(), eh2.a.readByte()));
                ++n3;
                "".length();
                if (-" ".length() >= -" ".length()) continue;
                return;
            }
            by2 = eh2.a.readByte();
            n3 = h[4];
            while (cf.a(n3, by2)) {
                fm fm2 = new fm(eh2.a.readShort(), eh2.a.readShort(), eh2.a.readShort(), eh2.a.readShort());
                n2 = h[4];
                int n5 = h[4];
                while (cf.a(n5, ap.ff.size())) {
                    if (cf.c(((fm)ap.ff.elementAt((int)n5)).l, fm2.l)) {
                        n2 = h[3];
                        "".length();
                        if ("  ".length() > -" ".length()) break;
                        return;
                    }
                    ++n5;
                    "".length();
                    if (((17 + 154 - 71 + 74 ^ 151 + 160 - 276 + 153) & (158 + 12 - 53 + 43 ^ 15 + 63 - 30 + 130 ^ -" ".length())) <= "   ".length()) continue;
                    return;
                }
                if (cf.e(n2)) {
                    ap.ff.addElement(fm2);
                }
                ++n3;
                "".length();
                if (null == null) continue;
                return;
            }
            ap.a(h[4]);
            try {
                de.f = null;
                de.ag = de.f = eh2.a.readUTF();
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
            if ("  ".length() < " ".length()) {
                return;
            }
            try {
                de.x.clear();
                n3 = eh2.a.readUnsignedByte();
                int n6 = h[4];
                while (cf.a(n6, n3)) {
                    n2 = eh2.a.readUnsignedByte();
                    String string = String.valueOf((short)(eh2.a.readUnsignedByte() * de.aa + n2));
                    de.x.put(string, "location");
                    "".length();
                    ++n6;
                    "".length();
                    if (-" ".length() != (0xBE ^ 0x8B ^ (0x19 ^ 0x28))) continue;
                    return;
                }
                "".length();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            if (((0xA ^ 0x2E) & ~(0xA9 ^ 0x8D)) != 0) {
                return;
            }
            de.k(de.ad);
            ec.n().fg = h[4];
            ec.n().fj = h[9];
            ap ap2 = ap.s();
            if (cf.b(ap.az)) {
                ap.az = new Image[h[2]];
                n2 = h[4];
                while (cf.a(n2, h[2])) {
                    ap.az[n2] = em.a("/e/sp" + n2 + ".png");
                    ++n2;
                    "".length();
                    if (-" ".length() == -" ".length()) continue;
                    return;
                }
            }
            ap.ad = new int[h[8]];
            ap.bn = new int[h[8]];
            ap.fj = new int[h[8]];
            ap.dk = new int[h[8]];
            ap.ei = new int[h[8]];
            int n7 = h[6];
            ap.fj[cf.h[3]] = n7;
            ap.fj[cf.h[4]] = n7;
            ap2.ag();
            fo.d();
            em.c(de.ae);
            ec.fh = h[4];
            ec.j = h[4];
            em.m();
            em.h();
            if (!cf.a(de.a ? 1 : 0) || cf.c(de.l, de.i)) {
                ap.s().c();
                fb.a();
                fb.a(de.ag, ag.dt + " " + de.ab, h[75]);
                dg.a();
                em.k();
            }
            dp.f();
            em.ad = h[4];
            "".length();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ("  ".length() <= 0) {
            return;
        }
        de.a();
    }

    private static void h(eh eh2) {
        try {
            m m2;
            short s2 = (short)eh2.a.readUnsignedByte();
            short s3 = eh2.a.readShort();
            byte[] byArray = null;
            if (cf.d(s3)) {
                byArray = new byte[s3];
                eh2.a.read(byArray);
                "".length();
            }
            if (cf.a(m2 = (m)f.b.get(String.valueOf(s2)))) {
                if (cf.e(m2.g ? 1 : 0)) {
                    dz dz2 = new dz();
                    dz2.a(byArray);
                    m2.e = dz2.a;
                    m2.a = dz2.c;
                    m2.h = dz2.b;
                    m2.f = dz2.h;
                    m2.c = dz2.e;
                    m2.d = dz2.k;
                    m2.b = dz2.j;
                    m2.g = h[3];
                }
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err getDataEffect ");
        }
        if ((7 ^ 3) < 0) {
            return;
        }
    }

    private static boolean b(int n2) {
        return n2 < 0;
    }

    private static boolean c(int n2) {
        return n2 <= 0;
    }

    private static boolean d(int n2) {
        return n2 > 0;
    }

    private static void i(eh eh2) {
        try {
            short s2 = (short)eh2.a.readUnsignedByte();
            short s3 = eh2.a.readShort();
            short s4 = eh2.a.readShort();
            byte by2 = eh2.a.readByte();
            short s5 = eh2.a.readShort();
            z.a(s2, s3, s4, by2, s5, h[3]);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err add effAuto");
        }
        if ("   ".length() < "   ".length()) {
            return;
        }
    }

    private static void j(eh eh2) {
        try {
            byte by2 = (byte)eh2.a.readUnsignedByte();
            byte[] byArray = bz.b(eh2);
            z.a(by2, byArray);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err getImgEffAuto");
        }
        if (-"  ".length() > 0) {
            return;
        }
    }

    static {
        cf.e();
        String[] stringArray = new String[h[8]];
        stringArray[cf.h[4]] = "B\u1ea1n ch\u1ec9 c\u00f3 th\u1ec3 v\u00e0o l\u1ea1i game sau ";
        stringArray[cf.h[3]] = " gi\u00e2y n\u1eefa";
        j = stringArray;
        b = "B\u1ea1n ch\u01b0a th\u1ec3 \u0111i \u0111\u1ebfn khu v\u1ef1c n\u00e0y.H\u00e3y ho\u00e0n th\u00e0nh nhi\u1ec7m v\u1ee5 tr\u01b0\u1edbc.";
        e = "C\u1eeda n\u00e0y v\u1eabn ch\u01b0a \u0111\u01b0\u1ee3c m\u1edf.";
        g = "C\u1eeda n\u00e0y ch\u1ec9 ch\u1ee9a \u0111\u01b0\u1ee3c t\u1ed1i \u0111a 2 ng\u01b0\u1eddi.";
        i = "S\u1ed1 nh\u00f3m c\u1ee7a khu v\u1ef1c n\u00e0y";
        d = "Khu v\u1ef1c n\u00e0y \u0111\u00e3 \u0111\u1ea7y.";
        f = "Thao t\u00e1c qu\u00e1 nhanh.";
    }

    private static boolean a(ec ec2, eh eh2) {
        try {
            Object object;
            int n2;
            short s2;
            ec2.fr = eh2.a.readUTF();
            if (cf.e(ec2.fr.equals("") ? 1 : 0)) {
                ec2.dr = eh2.a.readByte();
            }
            ec2.fa = eh2.a.readBoolean();
            ec2.ej = eh2.a.readByte();
            ec2.dk = ap.bw[eh2.a.readByte()];
            ec2.ch = eh2.a.readByte();
            ec2.h = eh2.a.readShort();
            ec2.cx = eh2.a.readUTF();
            ec2.cl = eh2.a.readInt();
            ec2.fd = eh2.a.readInt();
            ec2.aa = eh2.a.readUnsignedByte();
            ec2.gc = eh2.a.readShort();
            ec2.ba = eh2.a.readShort();
            ec2.dd = eh2.a.readShort();
            byte by2 = eh2.a.readByte();
            if (cf.c(ec2.gc, h[6])) {
                ec2.gc = (short)h[19];
            }
            if (cf.c(ec2.ba, h[6])) {
                ec2.e();
            }
            if (cf.c(ec2.dd, h[6])) {
                ec2.ae();
            }
            if (cf.c(by2, h[6])) {
                ec2.fe = null;
                "".length();
                if (null != null) {
                    return ((0xDB ^ 0xC1) & ~(0x38 ^ 0x22)) != 0;
                }
            } else {
                s2 = (short)ec2.s;
                n2 = ec2.fc - h[13];
                ec2.fe = new by(h[6], h[4], h[4], h[4], h[4], h[4], by2, h[3], h[4], h[4], h[4], s2, (short)n2, h[9], h[4], h[4], h[4]);
                ec2.fe.p = h[20];
            }
            short s3 = eh2.a.readShort();
            ec2.ai = s3;
            ec2.s = s3;
            short s4 = eh2.a.readShort();
            ec2.ed = s4;
            ec2.fc = s4;
            ec2.gf = eh2.a.readShort();
            ec2.gb = eh2.a.readShort();
            s2 = eh2.a.readByte();
            n2 = h[4];
            while (cf.a(n2, s2)) {
                object = new au(eh2.a.readByte(), eh2.a.readInt(), eh2.a.readInt(), eh2.a.readShort());
                ec2.dy.addElement(object);
                if (!cf.f(((au)object).a.c, h[58]) || cf.c(((au)object).a.c, h[1])) {
                    ec2.fa = h[3];
                }
                ++n2;
                "".length();
                if ("   ".length() != 0) continue;
                return ((0x23 ^ 0x7B) & ~(0x50 ^ 8)) != 0;
            }
            if (cf.e(ec2.fa ? 1 : 0)) {
                db.b(h[23], ec2, h[3]);
            }
            if (cf.e(ec2.cl)) {
                ec2.fj = h[37];
                if (cf.c(ec.n().w, ec2.w)) {
                    ap.s().ap();
                }
            }
            if (cf.c(ec2.w, -ec.n().w)) {
                n2 = h[4];
                while (cf.a(n2, ap.bj.size())) {
                    object = (ds)ap.bj.elementAt(n2);
                    if (cf.a(((ds)object).a.d.equals(ec2.cx) ? 1 : 0)) {
                        ((ds)object).fj = h[19];
                        ((ds)object).fi = null;
                        "".length();
                        if ("  ".length() != 0) break;
                        return ((0x2C ^ 0x2A) & ~(8 ^ 0xE)) != 0;
                    }
                    ++n2;
                    "".length();
                    if (-(12 + 58 - 16 + 101 ^ 153 + 78 - 195 + 123) <= 0) continue;
                    return ("   ".length() & ("   ".length() ^ -" ".length())) != 0;
                }
            }
            ec2.cb = eh2.a.readBoolean();
            ec2.as = eh2.a.readBoolean();
            if (cf.a(ec2.as ? 1 : 0)) {
                db.a(h[26], ec2.s, ec2.fc, h[4]);
            }
            short[] sArray = new short[h[9]];
            sArray[cf.h[4]] = eh2.a.readShort();
            sArray[cf.h[3]] = eh2.a.readShort();
            sArray[cf.h[8]] = eh2.a.readShort();
            sArray[cf.h[2]] = eh2.a.readShort();
            object = sArray;
            if (cf.f(sArray[h[4]])) {
                ec2.h = (short)object[h[4]];
            }
            if (cf.f((int)object[h[3]])) {
                ec2.gc = (short)object[h[3]];
            }
            if (cf.f((int)object[h[8]])) {
                ec2.ba = (short)object[h[8]];
            }
            if (cf.f((int)object[h[2]])) {
                ec2.dd = (short)object[h[2]];
            }
            int n3 = h[4];
            while (cf.a(n3, ap.an.size())) {
                dg dg2 = (dg)ap.an.elementAt(n3);
                if (cf.c(dg2.d, ec2.w)) {
                    dg2.b = ec2;
                    "".length();
                    if (-" ".length() == -" ".length()) break;
                    return ((0x45 ^ 0x6A) & ~(0x44 ^ 0x6B)) != 0;
                }
                ++n3;
                "".length();
                if ("   ".length() > " ".length()) continue;
                return ((0x9C ^ 0x8F ^ (0x66 ^ 0x33)) & (0x7D ^ 0x41 ^ (0xE8 ^ 0x92) ^ -" ".length())) != 0;
            }
            return h[3];
        }
        catch (Exception exception) {
            return h[4];
        }
    }

    private static boolean e(int n2, int n3) {
        return n2 <= n3;
    }

    private static void b(DataInputStream dataInputStream) {
        try {
            int n2;
            ap.da = dataInputStream.readByte();
            de.d = new String[dataInputStream.readUnsignedByte()];
            int n3 = h[4];
            while (cf.a(n3, de.d.length)) {
                de.d[n3] = dataInputStream.readUTF();
                ++n3;
                "".length();
                if ("   ".length() < (0x65 ^ 0x61)) continue;
                return;
            }
            ds.b = new ft[dataInputStream.readByte()];
            int n4 = h[4];
            while (cf.a(n4, ds.b.length)) {
                ds.b[n4] = new ft();
                ds.b[n4].e = n4;
                ds.b[n4].d = dataInputStream.readUTF();
                ds.b[n4].b = dataInputStream.readShort();
                ds.b[n4].a = dataInputStream.readShort();
                ds.b[n4].c = dataInputStream.readShort();
                ds.b[n4].f = new String[dataInputStream.readByte()][];
                n2 = h[4];
                while (cf.a(n2, ds.b[n4].f.length)) {
                    ds.b[n4].f[n2] = new String[dataInputStream.readByte()];
                    int n5 = h[4];
                    while (cf.a(n5, ds.b[n4].f[n2].length)) {
                        ds.b[n4].f[n2][n5] = dataInputStream.readUTF();
                        ++n5;
                        "".length();
                        if (-" ".length() <= -" ".length()) continue;
                        return;
                    }
                    ++n2;
                    "".length();
                    if ("  ".length() > " ".length()) continue;
                    return;
                }
                n4 = (byte)(n4 + h[3]);
                "".length();
                if ((0xBA ^ 0xBF) > 0) continue;
                return;
            }
            n3 = dataInputStream.readUnsignedByte();
            by.h = new bu[n3];
            n2 = h[4];
            while (cf.a(n2, n3)) {
                by.h[n2] = new bu();
                by.h[n2].h = (short)n2;
                by.h[n2].e = dataInputStream.readByte();
                by.h[n2].j = dataInputStream.readUTF();
                by.h[n2].g = dataInputStream.readInt();
                by.h[n2].b = dataInputStream.readByte();
                by.h[n2].k = dataInputStream.readByte();
                ++n2;
                "".length();
                if (" ".length() == " ".length()) continue;
                return;
            }
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (" ".length() >= "  ".length()) {
            return;
        }
    }

    private static boolean f(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean e(int n2) {
        return n2 == 0;
    }

    private static boolean b(Object object) {
        return object == null;
    }

    public static cf d() {
        if (cf.b(c)) {
            c = new cf();
        }
        return c;
    }

    private static boolean f(int n2) {
        return n2 >= 0;
    }

    /*
     * Exception decompiling
     */
    private static void k(eh var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 107[CASE]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static void l(eh eh2) {
        try {
            short s2 = (short)eh2.a.readUnsignedByte();
            byte[] byArray = bz.b(eh2);
            f.a(s2, byArray);
            ct ct2 = (ct)f.a.get(String.valueOf(s2));
            if (cf.b(ct2)) {
                ct2 = new ct();
                f.a.put(String.valueOf(s2), ct2);
                "".length();
            }
            ct2.b = cf.a(byArray);
            if (cf.f(fl.l, h[3])) {
                an.a("ImgEffect ".concat(String.valueOf(s2)), byArray);
                return;
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err getImgEffect");
        }
        if (" ".length() >= (108 + 107 - 77 + 5 ^ 45 + 26 - 63 + 131)) {
            return;
        }
    }

    private static void m(eh eh2) {
        try {
            short s2 = (short)eh2.a.readUnsignedByte();
            short s3 = eh2.a.readShort();
            byte[] byArray = null;
            if (cf.d(s3)) {
                byArray = new byte[s3];
                eh2.a.read(byArray);
                "".length();
            }
            z.a(s2, byArray);
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err add effAuto");
        }
        if ("   ".length() < 0) {
            return;
        }
    }

    private static void n(eh eh2) {
        try {
            byte by2 = eh2.a.readByte();
            System.out.println("subcmd Notlogin: ".concat(String.valueOf(by2)));
            switch (by2) {
                case -124: {
                    System.out.println("SEND SMS");
                    String string = eh2.a.readUTF();
                    bj.a(eh2.a.readUTF(), "sms://".concat(String.valueOf(string)), new fr("", em.f(), h[86], null), new fr("", em.f(), h[87], null));
                    "".length();
                    if ("   ".length() > 0) break;
                    return;
                }
                case 2: {
                    an.d();
                }
            }
        }
        catch (Exception exception) {
            if (cf.a(eh2)) {
                eh2.c();
                "".length();
                if (-" ".length() > 0) {
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            if (cf.a(eh2)) {
                eh2.c();
            }
            throw throwable;
        }
        if (cf.a(eh2)) {
            eh2.c();
            "".length();
            if (null != null) {
                return;
            }
        }
    }

    private static void o(eh eh2) {
        try {
            byte by2 = eh2.a.readByte();
            ec.n().e = eh2.a.readInt();
            ec.n().bt = eh2.a.readInt();
            ec.n().cg = eh2.a.readInt();
            if (cf.e(by2)) {
                if (cf.a(ap.bo)) {
                    ap.bo = null;
                }
                if (cf.a(ap.bm)) {
                    int n2 = h[4];
                    while (cf.a(n2, ap.bm.length)) {
                        ap.bm[n2] = null;
                        ++n2;
                        "".length();
                        if ("   ".length() == "   ".length()) continue;
                        return;
                    }
                }
            } else if (cf.c(by2, h[3])) {
                if (cf.a(ap.bo)) {
                    ap.bo.c = h[3];
                    ap.bo.y = eh2.a.readByte();
                    ap.bd = ap.dx[h[32]];
                    ap.fp = h[4];
                }
                if (cf.a(ap.bm)) {
                    int n3 = h[4];
                    while (cf.a(n3, ap.bm.length)) {
                        ap.bm[n3] = null;
                        ++n3;
                        "".length();
                        if (-" ".length() < 0) continue;
                        return;
                    }
                }
            } else if ((!cf.f(by2, h[8]) || cf.c(by2, h[2])) && cf.a(ap.bm)) {
                int n4 = h[4];
                while (cf.a(n4, ap.bm.length)) {
                    ap.bm[n4] = null;
                    ++n4;
                    "".length();
                    if (null == null) continue;
                    return;
                }
            }
            ap.s().fx = null;
            ap.s().ft = null;
            ap.s().u();
            em.k();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("err getImgEffAuto");
        }
        if (null != null) {
            return;
        }
    }

    public final void a() {
        System.out.println("Disconnected");
        ey.k();
        if (cf.a(ey.u instanceof ak) && cf.c(fo.a().get(h[1]), h[2])) {
            ak.e = h[3];
        }
        if (cf.a(ec.eb ? 1 : 0) && cf.a(ey.u)) {
            dp.g().h();
            "".length();
            if (null != null) {
                return;
            }
        } else {
            em.f.g();
        }
    }

    private static void c(DataInputStream dataInputStream) {
        try {
            ap.as = dataInputStream.readByte();
            ap.ex = new k[dataInputStream.readUnsignedByte()];
            int n2 = h[4];
            while (cf.a(n2, ap.ex.length)) {
                ap.ex[n2] = new k();
                ap.ex[n2].b = n2;
                ap.ex[n2].c = dataInputStream.readUTF();
                ap.ex[n2].a = dataInputStream.readByte();
                ++n2;
                "".length();
                if ("  ".length() == "  ".length()) continue;
                return;
            }
            n2 = dataInputStream.readShort();
            int n3 = h[4];
            while (cf.a(n3, n2)) {
                t.a(new bf((short)n3, dataInputStream.readByte(), dataInputStream.readByte(), dataInputStream.readUTF(), dataInputStream.readUTF(), dataInputStream.readByte(), dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readBoolean()));
                ++n3;
                "".length();
                if ("  ".length() != 0) continue;
                return;
            }
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if ("  ".length() == 0) {
            return;
        }
    }

    private static void p(eh eh2) {
        try {
            int n2;
            eb eb2;
            if (cf.c(eh2.a.readByte(), h[3])) {
                eb2 = by.a(eh2.a.readUnsignedByte());
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                n2 = eh2.a.readInt();
                if (cf.c(n2, ec.n().w)) {
                    eb2 = ec.n();
                    "".length();
                    if (null != null) {
                        return;
                    }
                } else {
                    eb2 = ap.e(n2);
                }
            }
            if (cf.a(eb2)) {
                dz dz2;
                short s2 = eh2.a.readShort();
                int n3 = eh2.a.readInt();
                byte by2 = eh2.a.readByte();
                long l2 = (long)n3 + System.currentTimeMillis();
                n2 = by2 * h[53];
                short s3 = s2;
                n3 = h[4];
                while (cf.a(n3, ((eb)eb2).ad.size())) {
                    dz2 = (dz)((eb)eb2).ad.elementAt(n3);
                    if (cf.a(dz2) && cf.c(dz2.f, s3)) {
                        dz2.i = l2;
                        dz2.g = n2;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ((185 + 175 - 325 + 155 ^ 140 + 123 - 141 + 64) <= (0x5F ^ 0x22 ^ (1 ^ 0x78))) continue;
                    return;
                }
                dz2 = new dz(s3, l2, n2);
                ((eb)eb2).ad.addElement(dz2);
            }
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (-"  ".length() >= 0) {
            return;
        }
    }

    private static void d(DataInputStream dataInputStream) {
        try {
            ap.u = dataInputStream.readByte();
            ap.ce = new ck[dataInputStream.readByte()];
            int n2 = h[4];
            while (cf.a(n2, ap.ce.length)) {
                ap.ce[n2] = new ck();
                ap.ce[n2].b = n2;
                ap.ce[n2].a = dataInputStream.readUTF();
                ++n2;
                "".length();
                if (((0x60 ^ 0x72) & ~(0x85 ^ 0x97)) == 0) continue;
                return;
            }
            ap.bw = new ay[dataInputStream.readUnsignedByte()];
            n2 = h[4];
            while (cf.a(n2, ap.bw.length)) {
                ap.bw[n2] = new ay();
                ap.bw[n2].c = n2;
                ap.bw[n2].a = dataInputStream.readUTF();
                ap.bw[n2].b = new l[dataInputStream.readByte()];
                int n3 = h[4];
                while (cf.a(n3, ap.bw[n2].b.length)) {
                    ap.bw[n2].b[n3] = new l();
                    ap.bw[n2].b[n3].d = dataInputStream.readByte();
                    ap.bw[n2].b[n3].a = dataInputStream.readUTF();
                    ap.bw[n2].b[n3].e = dataInputStream.readByte();
                    ap.bw[n2].b[n3].b = dataInputStream.readByte();
                    ap.bw[n2].b[n3].f = dataInputStream.readShort();
                    int n4 = h[7];
                    if (!cf.f(em.aj, h[73]) || cf.e(em.aq, h[74])) {
                        n4 = h[11];
                    }
                    ap.bw[n2].b[n3].g = doa.m.a(dataInputStream.readUTF(), n4);
                    ap.bw[n2].b[n3].c = new cv[dataInputStream.readByte()];
                    int n5 = h[4];
                    while (cf.a(n5, ap.bw[n2].b[n3].c.length)) {
                        ap.bw[n2].b[n3].c[n5] = new cv();
                        ap.bw[n2].b[n3].c[n5].l = dataInputStream.readShort();
                        ap.bw[n2].b[n3].c[n5].k = ap.bw[n2].b[n3];
                        ap.bw[n2].b[n3].c[n5].b = dataInputStream.readByte();
                        ap.bw[n2].b[n3].c[n5].e = dataInputStream.readByte();
                        ap.bw[n2].b[n3].c[n5].d = dataInputStream.readShort();
                        ap.bw[n2].b[n3].c[n5].g = dataInputStream.readInt();
                        ap.bw[n2].b[n3].c[n5].i = dataInputStream.readShort();
                        ap.bw[n2].b[n3].c[n5].a = dataInputStream.readShort();
                        ap.bw[n2].b[n3].c[n5].j = dataInputStream.readByte();
                        ap.bw[n2].b[n3].c[n5].h = new en[dataInputStream.readByte()];
                        int n6 = h[4];
                        while (cf.a(n6, ap.bw[n2].b[n3].c[n5].h.length)) {
                            ap.bw[n2].b[n3].c[n5].h[n6] = new en();
                            ap.bw[n2].b[n3].c[n5].h[n6].a = dataInputStream.readShort();
                            ap.bw[n2].b[n3].c[n5].h[n6].b = ap.ce[dataInputStream.readByte()];
                            ++n6;
                            "".length();
                            if (-"  ".length() < 0) continue;
                            return;
                        }
                        ej.a(ap.bw[n2].b[n3].c[n5]);
                        ++n5;
                        "".length();
                        if ("   ".length() > 0) continue;
                        return;
                    }
                    ++n3;
                    "".length();
                    if ("  ".length() <= (0x7B ^ 0x7F)) continue;
                    return;
                }
                ++n2;
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (-"   ".length() >= 0) {
            return;
        }
    }

    public final void b() {
        System.out.println("Connect ok");
    }

    private static boolean a(Object object, Object object2) {
        return object == object2;
    }
}

