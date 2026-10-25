/*
 * Decompiled with CFR 0.152.
 */
public final class bo {
    public static boolean a;
    private static boolean c;
    public static boolean b;
    private static boolean d;
    private static boolean e;
    private static boolean f;
    private static boolean g;
    private static boolean h;
    private static boolean i;
    private static boolean j;
    private static final int[] k;
    private static boolean l;
    private static int m;
    private static boolean n;
    private static boolean o;
    private static Object p;
    private static boolean q;
    private static boolean r;
    private static boolean s;
    private static boolean t;

    private static boolean a(int n2, int n3) {
        return n2 >= n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a() {
        if (bo.b(d ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if ("   ".length() > (0x36 ^ 0x32)) {
                return;
            }
            d = k[1];
        }
    }

    private static int a(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void b() {
        if (bo.b(o ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            o = k[1];
        }
    }

    private static int b(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * Exception decompiling
     */
    public static void c() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[DOLOOP]], but top level block is 2[WHILELOOP]
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

    static {
        bo.al();
        i = k[1];
        a = k[1];
        b = k[1];
        l = k[1];
        c = k[1];
        n = k[1];
        f = k[1];
        h = k[1];
        q = k[1];
        j = k[1];
        t = k[1];
        s = k[1];
        r = k[1];
        o = k[1];
        e = k[1];
        d = k[1];
        g = k[1];
        p = new Object();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void d() {
        if (bo.b(n ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (((0xDD ^ 0xBE) & ~(0xE6 ^ 0x85) & ~("  ".length() & ~"  ".length())) == -" ".length()) {
                return;
            }
            n = k[1];
        }
    }

    private static boolean a(Object object) {
        return object == null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void e() {
        block6: {
            f = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block6;
                }
                if (-" ".length() >= (0xE8 ^ 0x88 ^ (0x7D ^ 0x19))) {
                    return;
                }
            }
        }
        "".length();
        if (-" ".length() != -" ".length()) {
            return;
        }
    }

    private static int c(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean f() {
        boolean bl2;
        o = k[0];
        long l2 = System.currentTimeMillis();
        Object object = p;
        synchronized (object) {
            try {
                p.wait(5000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
        if (bo.d(bo.g(System.currentTimeMillis() - l2, 5000L))) {
            bl2 = k[0];
            "".length();
            return bl2;
        }
        bl2 = k[1];
        return bl2;
    }

    private static int d(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    public static boolean g() {
        int n2 = k[1];
        while (bo.c(n2, ey.q.size())) {
            block4: {
                String string = (String)ey.q.elementAt(n2);
                int n3 = k[1];
                while (bo.c(n3, ap.an.size())) {
                    if (bo.b(((dg)ap.an.elementAt((int)n3)).g.equals(string) ? 1 : 0)) {
                        "".length();
                        if ("   ".length() != "   ".length()) {
                            return ((0x95 ^ 0xBF) & ~(0x4B ^ 0x61)) != 0;
                        }
                        break block4;
                    }
                    ++n3;
                    "".length();
                    if ((0x1E ^ 0x1A) >= ("   ".length() & ~"   ".length())) continue;
                    return ((0x19 ^ 0x56) & ~(0x5D ^ 0x12)) != 0;
                }
                return k[1];
            }
            ++n2;
            "".length();
            if (((0xB8 ^ 0x82) & ~(0x38 ^ 2)) < "  ".length()) continue;
            return ((0x75 ^ 0x20) & ~(8 ^ 0x5D)) != 0;
        }
        return k[0];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean h() {
        boolean bl2;
        long l2;
        block9: {
            e = k[0];
            l2 = System.currentTimeMillis();
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(20000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block9;
                }
                if (" ".length() <= 0) {
                    return ((0xA0 ^ 0x94) & ~(0x79 ^ 0x4D)) != 0;
                }
            }
        }
        "".length();
        if (bo.b(fd.a ? 1 : 0)) {
            fd.a = k[1];
            return k[1];
        }
        if (bo.d(bo.h(System.currentTimeMillis() - l2, 20000L))) {
            bl2 = k[0];
            "".length();
            if (-"   ".length() < 0) return bl2;
            return ((0x9B ^ 0xA2 ^ (0x6A ^ 0x4B)) & (0x47 ^ 0x2A ^ (0x79 ^ 0xC) ^ -" ".length())) != 0;
        } else {
            bl2 = k[1];
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void i() {
        if (bo.b(t ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
            t = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void j() {
        System.out.println("NotifyDV");
        if (bo.b(g ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            g = k[1];
        }
    }

    private static int e(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static void al() {
        k = new int[3];
        bo.k[0] = " ".length();
        bo.k[1] = (0x9D ^ 0x90 ^ (0x95 ^ 0x82)) & (0xB8 ^ 0xC6 ^ (9 ^ 0x6D) ^ -" ".length());
        bo.k[2] = 0x9F ^ 0x80 ^ (0xDE ^ 0xA5);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void k() {
        if (bo.b(h ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            h = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean l() {
        boolean bl2;
        long l2;
        block9: {
            q = k[0];
            l2 = System.currentTimeMillis();
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block9;
                }
                if ("  ".length() < 0) {
                    return ((0x88 ^ 0x82 ^ (0x75 ^ 0x62)) & (63 + 49 - 84 + 137 ^ 88 + 12 - -59 + 25 ^ -" ".length())) != 0;
                }
            }
        }
        "".length();
        if ("   ".length() <= 0) {
            return ((0xD ^ 0x5B ^ (0x9B ^ 0x85)) & (0xE3 ^ 0xAB ^ (0xC0 ^ 0x9A) & ~(0xEF ^ 0xB5) ^ -" ".length())) != 0;
        }
        if (bo.d(bo.c(System.currentTimeMillis() - l2, 2000L))) {
            bl2 = k[0];
            "".length();
            if ("  ".length() > 0) return bl2;
            return ((28 + 148 - 118 + 178 ^ 177 + 115 - 226 + 117) & (110 + 158 - 150 + 102 ^ 78 + 28 - -17 + 12 ^ -" ".length())) != 0;
        } else {
            bl2 = k[1];
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void m() {
        if (bo.b(l ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if ("   ".length() == 0) {
                return;
            }
            l = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void n() {
        if (bo.b(b ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (" ".length() <= -" ".length()) {
                return;
            }
            b = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean o() {
        boolean bl2;
        System.out.println("WaitDV");
        g = k[0];
        long l2 = System.currentTimeMillis();
        Object object = p;
        synchronized (object) {
            try {
                p.wait(10000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
        if ("   ".length() > "   ".length()) {
            return ((0xEB ^ 0x84 ^ (0xA5 ^ 0x8F)) & (0x45 ^ 0x4C ^ (0x35 ^ 0x79) ^ -" ".length())) != 0;
        }
        if (bo.d(bo.d(System.currentTimeMillis() - l2, 10000L))) {
            bl2 = k[0];
            "".length();
            if (((0xC8 ^ 0xA1 ^ (0x94 ^ 0xA0)) & (98 + 114 - 77 + 86 ^ 33 + 29 - -21 + 45 ^ -" ".length())) == 0) return bl2;
            return ((136 + 25 - 112 + 91 ^ 163 + 58 - 106 + 57) & (0x51 ^ 0x7D ^ (0x15 ^ 0x19) ^ -" ".length())) != 0;
        } else {
            bl2 = k[1];
        }
        return bl2;
    }

    private static boolean b(int n2, int n3) {
        return n2 > n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void p() {
        d = k[0];
        Object object = p;
        synchronized (object) {
            try {
                p.wait(500L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void q() {
        block5: {
            l = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block5;
                }
                if (-" ".length() >= " ".length()) {
                    return;
                }
            }
        }
        "".length();
    }

    private static boolean a(int n2) {
        return n2 >= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void r() {
        h = k[0];
        Object object = p;
        synchronized (object) {
            try {
                p.wait(2000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
        if ("   ".length() < 0) {
            return;
        }
    }

    public static void a(long l2) {
        if (bo.b(ey.v)) {
            long l3 = System.currentTimeMillis();
            while (bo.c(bo.x() ? 1 : 0) && bo.d(bo.j(System.currentTimeMillis() - l3, l2))) {
                ax.a(2000L);
                "".length();
                if ("   ".length() == "   ".length()) continue;
                return;
            }
        }
    }

    private static boolean c(int n2, int n3) {
        return n2 < n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void s() {
        if (bo.b(s ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (((0x20 ^ 0x67 ^ (0xA8 ^ 0xB3)) & (0xFD ^ 0x81 ^ (0x32 ^ 0x12) ^ -" ".length())) >= "   ".length()) {
                return;
            }
            s = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void t() {
        if (bo.b(q ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if ((0xE ^ 0x2C ^ (0x5D ^ 0x7B)) < -" ".length()) {
                return;
            }
            q = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void u() {
        block6: {
            i = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block6;
                }
                if (" ".length() < 0) {
                    return;
                }
            }
        }
        "".length();
        if (-"  ".length() > 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean b(long l2) {
        boolean bl2;
        long l3;
        block16: {
            Object object;
            e = k[0];
            if (bo.d(bo.b(l2, 0L))) {
                block15: {
                    Object object2;
                    Object object3 = object2 = p;
                    synchronized (object3) {
                        try {
                            p.wait();
                            "".length();
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                            break block15;
                        }
                        if ("  ".length() >= "   ".length()) {
                            return ((0xDD ^ 0xB1 ^ (0x6B ^ 0x5F)) & (142 + 181 - 244 + 130 ^ 25 + 29 - -55 + 28 ^ -" ".length())) != 0;
                        }
                    }
                }
                "".length();
                if ("  ".length() <= " ".length()) {
                    return ((0x98 ^ 0xC0) & ~(0xDE ^ 0x86)) != 0;
                }
                if (!bo.b(fd.a ? 1 : 0)) return k[0];
                fd.a = k[1];
                return k[1];
            }
            l3 = System.currentTimeMillis();
            Object throwable = object = p;
            synchronized (throwable) {
                try {
                    p.wait(l2);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block16;
                }
                if (((0x37 ^ 0x2A ^ (0xFF ^ 0xC6)) & (172 + 34 - 133 + 104 ^ 94 + 67 - 129 + 117 ^ -" ".length())) != 0) {
                    return ((0x48 ^ 0x55 ^ (0x6E ^ 0x5E)) & (0x2B ^ 0x1E ^ (0x90 ^ 0x88) ^ -" ".length())) != 0;
                }
            }
        }
        "".length();
        if ((35 + 55 - 20 + 75 ^ 97 + 59 - 87 + 80) <= ((67 + 159 - 213 + 222 ^ 104 + 56 - 70 + 49) & (21 + 187 - 84 + 95 ^ 91 + 5 - 86 + 177 ^ -" ".length()))) {
            return ((16 + 23 - -93 + 22 ^ 118 + 148 - 135 + 31) & (43 + 23 - -19 + 57 ^ 56 + 108 - 47 + 65 ^ -" ".length())) != 0;
        }
        if (bo.b(fd.a ? 1 : 0)) {
            fd.a = k[1];
            return k[1];
        }
        if (bo.d(bo.b(System.currentTimeMillis() - l3, l2))) {
            bl2 = k[0];
            "".length();
            return bl2;
        }
        bl2 = k[1];
        return bl2;
    }

    private static int f(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void v() {
        r = k[0];
        Object object = p;
        synchronized (object) {
            try {
                p.wait(3000L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
    }

    private static boolean b(Object object) {
        return object != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean w() {
        boolean bl2;
        block11: {
            int n2;
            if (bo.b(ax.z)) {
                n2 = ax.z.b;
                "".length();
            } else {
                n2 = k[1];
            }
            m = n2;
            s = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(3000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block11;
                }
                if (((134 + 169 - 122 + 59 ^ 24 + 34 - 17 + 157) & (0xC0 ^ 0x80 ^ (0xB4 ^ 0xC2) ^ -" ".length())) < 0) {
                    return ((0x2E ^ 0xB ^ (0xAC ^ 0xB5)) & (19 + 197 - 138 + 175 ^ 29 + 10 - -88 + 66 ^ -" ".length())) != 0;
                }
            }
        }
        "".length();
        if ("   ".length() >= (0x8C ^ 0xBB ^ (0x21 ^ 0x12))) {
            return ((195 + 9 - 201 + 224 ^ 73 + 0 - 24 + 114) & (" ".length() ^ (0xF8 ^ 0xB9) ^ -" ".length())) != 0;
        }
        if (!bo.b(ax.z) || bo.b(ax.z.b, m)) {
            bl2 = k[0];
            "".length();
            if (-" ".length() < 0) return bl2;
            return ((0x26 ^ 0x2A) & ~(0x80 ^ 0x8C)) != 0;
        } else {
            bl2 = k[1];
        }
        return bl2;
    }

    public static boolean x() {
        int n2 = k[1];
        block0: while (bo.c(n2, ey.q.size())) {
            String string = (String)ey.q.elementAt(n2);
            int n3 = k[1];
            while (bo.c(n3, ap.an.size())) {
                dg dg2 = (dg)ap.an.elementAt(n3);
                if (bo.b(dg2.g.equals(string) ? 1 : 0)) {
                    if (!bo.b(dg2.b) || bo.b(fo.a(ec.n().s, ec.n().fc, dg2.b.s, dg2.b.fc), k[2])) {
                        return k[1];
                    }
                    ++n2;
                    "".length();
                    if ((0x4A ^ 0x4E) > 0) continue block0;
                    return ((0x2E ^ 0xA) & ~(0x32 ^ 0x16)) != 0;
                }
                ++n3;
                "".length();
                if (((0x9D ^ 0xBC) & ~(0x92 ^ 0xB3)) == 0) continue;
                return ((0xD1 ^ 0xB0) & ~(0xC1 ^ 0xA0)) != 0;
            }
            return k[1];
        }
        return k[0];
    }

    private static boolean b(int n2) {
        return n2 != 0;
    }

    public static void y() {
        if (bo.b(ey.v)) {
            long l2 = System.currentTimeMillis();
            while (bo.c(bo.g() ? 1 : 0) && bo.d(bo.e(System.currentTimeMillis() - l2, 60000L))) {
                ax.a(2000L);
                "".length();
                if (-"   ".length() < 0) continue;
                return;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void z() {
        if (bo.b(f ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            f = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void aa() {
        block5: {
            c = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block5;
                }
                if ("  ".length() != "  ".length()) {
                    return;
                }
            }
        }
        "".length();
    }

    private static boolean c(int n2) {
        return n2 == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean ab() {
        boolean bl2;
        b = k[0];
        long l2 = System.currentTimeMillis();
        Object object = p;
        synchronized (object) {
            try {
                p.wait(32L);
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
        if (-" ".length() >= "  ".length()) {
            return ((194 + 121 - 94 + 3 ^ 124 + 77 - 153 + 122) & (0x21 ^ 0x41 ^ (0x14 ^ 0x3E) ^ -" ".length())) != 0;
        }
        if (bo.d(bo.i(System.currentTimeMillis() - l2, 32L))) {
            bl2 = k[0];
            "".length();
            return bl2;
        }
        bl2 = k[1];
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ac() {
        if (bo.b(r ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            r = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ad() {
        if (bo.b(e ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (-" ".length() > ((0x95 ^ 0xC7) & ~(0xFE ^ 0xAC))) {
                return;
            }
            e = k[1];
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ae() {
        if (bo.b(c ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
            c = k[1];
        }
    }

    private static int g(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static int h(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void af() {
        j = k[0];
        Object object = p;
        synchronized (object) {
            try {
                p.wait();
                "".length();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        "".length();
        if (" ".length() < 0) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ag() {
        block6: {
            n = k[0];
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(2000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block6;
                }
                if (" ".length() == -" ".length()) {
                    return;
                }
            }
        }
        "".length();
        if ("  ".length() >= (0x94 ^ 0x90)) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ah() {
        if (bo.b(i ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            i = k[1];
        }
    }

    private static int i(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void ai() {
        Object object = p;
        synchronized (object) {
            p.notifyAll();
        }
        "".length();
        if (-" ".length() >= 0) {
            return;
        }
        b = k[1];
        l = k[1];
        c = k[1];
        n = k[1];
        f = k[1];
        h = k[1];
        j = k[1];
        i = k[1];
        q = k[1];
        a = k[1];
        t = k[1];
        s = k[1];
        r = k[1];
        o = k[1];
        e = k[1];
        d = k[1];
        de.a = k[1];
        de.a();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void aj() {
        if (bo.b(j ? 1 : 0)) {
            Object object = p;
            synchronized (object) {
                p.notifyAll();
            }
            "".length();
            if (((0xF7 ^ 0xC0 ^ (0x1C ^ 0x7D)) & (0xC6 ^ 0xB4 ^ (0xB9 ^ 0x9D) ^ -" ".length())) > "   ".length()) {
                return;
            }
            j = k[1];
        }
    }

    private static int j(long l2, long l3) {
        return l2 == l3 ? 0 : (l2 < l3 ? -1 : 1);
    }

    private static boolean d(int n2) {
        return n2 < 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean ak() {
        boolean bl2;
        long l2;
        block9: {
            t = k[0];
            l2 = System.currentTimeMillis();
            Object object = p;
            synchronized (object) {
                try {
                    p.wait(7000L);
                    "".length();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                    break block9;
                }
                if (((0x95 ^ 0x89) & ~(0x39 ^ 0x25)) >= "  ".length()) {
                    return ((0x5F ^ 0x49) & ~(0x82 ^ 0x94)) != 0;
                }
            }
        }
        "".length();
        if ("  ".length() < " ".length()) {
            return ((2 ^ 0x25) & ~(0x42 ^ 0x65)) != 0;
        }
        if (bo.d(bo.f(System.currentTimeMillis() - l2, 7000L))) {
            bl2 = k[0];
            "".length();
            if (" ".length() < "   ".length()) return bl2;
            return ((0x27 ^ 0x2E ^ " ".length()) & (0x6C ^ 0x33 ^ (0xC2 ^ 0x95) ^ -" ".length())) != 0;
        } else {
            bl2 = k[1];
        }
        return bl2;
    }
}

