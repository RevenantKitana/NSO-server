/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.SocketConnection
 */
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public final class v
implements Runnable {
    private static final int[] b;
    final dp a;
    private final String c;

    static {
        v.a();
    }

    v(dp dp2, String string) {
        this.a = dp2;
        this.c = "socket://161.118.209.14:14444";
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void run() {
        block3: {
            try {
                dp.a(this.a, new Thread(new dv(this)));
                dp.d(this.a).start();
                this.a.l = System.currentTimeMillis();
                this.a.h = b[0];
                this.a.n = b[0];
                this.a.p = (SocketConnection)Connector.open((String)this.c);
                this.a.f = this.a.p.openDataOutputStream();
                this.a.g = this.a.p.openDataInputStream();
                dp.c(this.a, new Thread(dp.f(this.a)));
                dp.b(this.a, new Thread(dp.e(this.a)));
                dp.g(this.a).start();
                dp.a(this.a).start();
                dp.a(this.a, System.currentTimeMillis());
                dp.a(this.a, new eh(b[1]));
                this.a.h = b[2];
                this.a.c.b();
                "".length();
            }
            catch (Exception exception) {
                dp dp2 = this.a;
                ey.k();
                dp2.e();
                if (!v.a(this.a.c)) break block3;
                this.a.c.c();
            }
            if ((118 + 77 - 105 + 63 ^ 52 + 131 - 114 + 88) == "   ".length()) {
                return;
            }
        }
    }

    private static void a() {
        b = new int[3];
        v.b[0] = " ".length();
        v.b[1] = -(0x49 ^ 0x52);
        v.b[2] = (0x42 ^ 0x11) & ~(0xE6 ^ 0xB5);
    }
}

