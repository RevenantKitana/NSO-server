/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.SocketConnection
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.io.SocketConnection;

public final class dp
implements bv {
    public static dp a;
    public byte[] b = null;
    public ac c;
    private Thread q;
    private Thread r;
    public static boolean d;
    private Thread s;
    public int e;
    public DataOutputStream f;
    public DataInputStream g;
    public boolean h;
    private byte t;
    public static String i;
    public String j = "";
    public static byte k;
    private boolean u;
    public long l;
    private Thread v;
    private static final int[] w;
    public static int m;
    private long x;
    public boolean n;
    private static Object y;
    private fq z;
    private byte aa;
    public int o;
    private ep ab = new ep(this);
    public SocketConnection p;

    static Thread a(dp dp2) {
        return dp2.s;
    }

    public final void c() {
        ey.k();
        this.e();
    }

    static long b(dp dp2) {
        return dp2.x;
    }

    private byte a(byte by2) {
        byte[] byArray = this.b;
        byte by3 = this.aa;
        this.aa = (byte)(by3 + w[4]);
        by2 = (byte)(byArray[by3] & w[5] ^ by2 & w[5]);
        if (dp.c(this.aa, this.b.length)) {
            this.aa = (byte)(this.aa % this.b.length);
        }
        return by2;
    }

    public void d() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static boolean a(Object object) {
        return object != null;
    }

    static Thread c(dp dp2) {
        return dp2.v;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void a(long l2) {
        long l3 = l2;
        Object object = y;
        synchronized (object) {
            try {
                y.wait(l3);
                "".length();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        "".length();
        if (-" ".length() >= "   ".length()) {
            return;
        }
    }

    public final void e() {
        this.u = w[0];
        this.n = w[0];
        this.h = w[0];
        this.b = null;
        this.t = (byte)w[0];
        this.aa = (byte)w[0];
        this.ab.a.removeAllElements();
        try {
            if (dp.a(this.p)) {
                this.p.close();
                this.p = null;
            }
            if (dp.a(this.f)) {
                this.f.close();
                this.f = null;
            }
            if (dp.a(this.g)) {
                this.g.close();
                this.g = null;
            }
            if (dp.a(this.r)) {
                if (dp.a(this.r.isAlive() ? 1 : 0)) {
                    this.r.interrupt();
                }
                this.r = null;
            }
            if (dp.a(this.v)) {
                if (dp.a(this.v.isAlive() ? 1 : 0)) {
                    this.v.interrupt();
                }
                this.v = null;
            }
            if (dp.a(this.q)) {
                if (dp.a(this.q.isAlive() ? 1 : 0)) {
                    this.q.interrupt();
                }
                this.q = null;
            }
            if (dp.a(this.s)) {
                if (dp.a(this.s.isAlive() ? 1 : 0)) {
                    this.s.interrupt();
                }
                this.s = null;
            }
            System.gc();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("  ".length() == 0) {
            return;
        }
    }

    static void a(dp dp2, Thread thread) {
        dp2.r = thread;
    }

    public void a(String string, String string2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public final void a(eh eh2) {
        this.ab.a.addElement(eh2);
    }

    static Thread d(dp dp2) {
        return dp2.r;
    }

    static void a(dp dp2, eh eh2) {
        dp2.b(eh2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void f() {
        Object object = y;
        synchronized (object) {
            y.notifyAll();
        }
        "".length();
    }

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean a(int n2, int n3) {
        return n2 != n3;
    }

    static void b(dp dp2, Thread thread) {
        dp2.s = thread;
    }

    static void a(dp dp2, long l2) {
        dp2.x = l2;
    }

    public static dp g() {
        return a;
    }

    static void c(dp dp2, Thread thread) {
        dp2.q = thread;
    }

    private static boolean b(int n2, int n3) {
        return n2 < n3;
    }

    private static boolean a(Object object, Object object2) {
        return object != object2;
    }

    public void a(int n2, Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void b() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void b_() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static boolean c(int n2, int n3) {
        return n2 >= n3;
    }

    static fq e(dp dp2) {
        return dp2.z;
    }

    private static boolean b(int n2) {
        return n2 == 0;
    }

    public final void a(String string, int n2) {
        String string2 = "socket://" + string + ":" + n2;
        if (dp.a(em.ai ? 1 : 0)) {
            if (dp.b(em.ac ? 1 : 0)) {
                string2 = string2 + ";interface=wifi";
                "".length();
                if ("   ".length() < 0) {
                    return;
                }
            } else {
                string2 = string2 + ";deviceside=true";
            }
        }
        System.out.println("connect to " + string2 + " : " + fl.h);
        if (dp.b(this.n ? 1 : 0) && dp.b(this.h ? 1 : 0)) {
            this.u = w[0];
            this.p = null;
            this.v = new Thread(new v(this, string2));
            this.v.start();
            i = string;
            m = n2;
            k = fl.h;
        }
    }

    private static void i() {
        w = new int[6];
        dp.w[0] = (0x27 ^ 0x3D ^ (0x23 ^ 0x71)) & (0xB4 ^ 0x9B ^ (0x6D ^ 0xA) ^ -" ".length());
        dp.w[1] = -(0x13 ^ 0x3B ^ (0xA7 ^ 0x90));
        dp.w[2] = 0xAF ^ 0xA7;
        dp.w[3] = 0x33 ^ 0x36;
        dp.w[4] = " ".length();
        dp.w[5] = (0xE5 ^ 0xAA) + (" ".length() & ~" ".length()) - -(0x99 ^ 0x96) + (145 + 75 - 155 + 96);
    }

    static ep f(dp dp2) {
        return dp2.ab;
    }

    static Thread g(dp dp2) {
        return dp2.q;
    }

    private synchronized void b(eh eh2) {
        byte[] byArray = eh2.b.toByteArray();
        try {
            if (dp.a(this.u ? 1 : 0)) {
                this.f.writeByte(this.a(eh2.c));
                "".length();
                if (((0x58 ^ 0x69) & ~(0x93 ^ 0xA2)) != 0) {
                    return;
                }
            } else {
                this.f.writeByte(eh2.c);
            }
            if (dp.a(byArray)) {
                int n2;
                int n3 = byArray.length;
                if (dp.a(eh2.c, w[1]) && dp.a(this.u ? 1 : 0)) {
                    n2 = this.a((byte)(n3 >> w[2]));
                    this.f.writeByte(n2);
                    n2 = this.a((byte)n3);
                    this.f.writeByte(n2);
                    "".length();
                    if ("   ".length() <= -" ".length()) {
                        return;
                    }
                } else {
                    this.f.writeShort(n3);
                }
                if (dp.a(this.u ? 1 : 0)) {
                    n2 = w[0];
                    while (dp.b(n2, byArray.length)) {
                        byArray[n2] = this.a(byArray[n2]);
                        ++n2;
                        "".length();
                        if (-" ".length() < 0) continue;
                        return;
                    }
                }
                this.f.write(byArray);
                this.o += w[3] + byArray.length;
                "".length();
                if (" ".length() <= 0) {
                    return;
                }
            } else {
                this.f.writeShort(w[0]);
                this.o += w[3];
            }
            this.f.flush();
            "".length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if ("   ".length() < "   ".length()) {
            return;
        }
    }

    public final void h() {
        if (dp.a(em.at, em.au)) {
            em.f.n();
        }
        if (dp.a(d ? 1 : 0)) {
            dp.f();
            "".length();
            if (((181 + 124 - 177 + 82 ^ 53 + 110 - 97 + 65) & (19 + 101 - -10 + 14 ^ 9 + 43 - -41 + 100 ^ -" ".length())) > 0) {
                return;
            }
        } else {
            d = w[4];
            new Thread(new dh(this)).start();
        }
    }

    static boolean h(dp dp2) {
        return dp2.u;
    }

    static void i(dp dp2) {
        dp2.u = w[4];
    }

    static {
        dp.i();
        a = new dp();
        y = new Object();
    }

    public dp() {
        this.z = new fq(this);
    }

    static byte a(dp dp2, byte by2) {
        byte[] byArray = dp2.b;
        byte by3 = dp2.t;
        dp2.t = (byte)(by3 + w[4]);
        by2 = (byte)(byArray[by3] & w[5] ^ by2 & w[5]);
        if (dp.c(dp2.t, dp2.b.length)) {
            dp2.t = (byte)(dp2.t % dp2.b.length);
        }
        return by2;
    }
}

