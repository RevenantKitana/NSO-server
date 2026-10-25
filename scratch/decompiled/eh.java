/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class eh {
    private ByteArrayInputStream e = null;
    DataInputStream a = null;
    ByteArrayOutputStream b = null;
    public byte c;
    DataOutputStream d = null;

    private byte[] d() {
        return this.b.toByteArray();
    }

    public eh(byte by2) {
        this.c = by2;
        this.b = new ByteArrayOutputStream();
        this.d = new DataOutputStream(this.b);
    }

    public eh(byte by2, byte[] byArray) {
        this.c = by2;
        this.e = new ByteArrayInputStream(byArray);
        this.a = new DataInputStream(this.e);
        ey.a(by2, byArray);
    }

    public final DataInputStream a() {
        return this.a;
    }

    public eh() {
    }

    public final DataOutputStream b() {
        return this.d;
    }

    private static boolean a(Object object) {
        return object != null;
    }

    public final void c() {
        try {
            if (eh.a(this.a)) {
                this.a.close();
            }
            if (eh.a(this.d)) {
                this.d.close();
            }
            "".length();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if ("   ".length() > "   ".length()) {
            return;
        }
    }
}

