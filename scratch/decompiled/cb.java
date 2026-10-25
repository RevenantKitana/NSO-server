/*
 * Decompiled with CFR 0.152.
 */
public final class cb
extends ax {
    private static final int[] a;

    private static boolean a(int n2) {
        return n2 != 0;
    }

    private static boolean e(int n2) {
        return n2 > 0;
    }

    public final String toString() {
        return "T\u1ef1 \u0111\u00e1nh";
    }

    public final void a() {
        if (!cb.d(this.u, a[0]) || !cb.d(this.u, a[1]) || !cb.d(this.u, a[2]) || cb.c(this.u, a[3])) {
            bf.f = this.u;
        }
        if (cb.a(ax.g() ? 1 : 0)) {
            if (cb.a(ec.cs ? 1 : 0)) {
                ax.a(a[4]);
                "".length();
                if ("   ".length() <= " ".length()) {
                    return;
                }
            }
        } else {
            if (cb.f(ax.ab ? 1 : 0) && cb.a(ec.n().cb ? 1 : 0)) {
                this.j();
                return;
            }
            if (cb.c(this.u, de.i) && (!cb.f(this.t ? 1 : 0) || cb.c(this.x, de.ab))) {
                this.d(a[5]);
                this.b(a[5], a[5]);
                return;
            }
            if (cb.a(ec.cs ? 1 : 0)) {
                this.a(this.u, this.x, this.p, this.s);
            }
        }
    }

    public final void c() {
        super.c();
        this.u = de.i;
        this.x = de.ab;
        this.p = ec.n().s;
        this.s = ec.n().fc;
    }

    private static boolean c(int n2, int n3) {
        return n2 == n3;
    }

    static {
        cb.e();
    }

    protected final by a(ec ec2, int n2, int n3, ec ec3, boolean bl2) {
        if (cb.a(ey.av ? 1 : 0) && cb.e(ey.az.size())) {
            this.a(n3, bl2);
            return ax.a(ec2.s, ec2.fc);
        }
        return ax.a(ec2.s, ec2.fc);
    }

    private static void e() {
        a = new int[6];
        cb.a[0] = (0x93 ^ 0xA3) + (0x7A ^ 0xD) - (49 + 45 - -21 + 16) + (0x57 ^ 0x35);
        cb.a[1] = (0xE0 ^ 0x8E) + (0x29 ^ 0x41) - (55 + 88 - 66 + 96) + (0x2C ^ 0x72);
        cb.a[2] = 12 + 43 - -64 + 17;
        cb.a[3] = (0x66 ^ 0x5E) + (0x4A ^ 0x59) - (0x40 ^ 0x66) + (0xC7 ^ 0xA3);
        cb.a[4] = " ".length();
        cb.a[5] = -" ".length();
    }

    private static boolean d(int n2, int n3) {
        return n2 != n3;
    }

    private static boolean f(int n2) {
        return n2 == 0;
    }
}

