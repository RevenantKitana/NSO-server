/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public final class bj
implements Runnable {
    private final fr a;
    private final String b;
    private final String c;
    private final fr d;
    private final String e;

    private bj(String string, String string2, String string3, fr fr2, fr fr3) {
        this.b = string;
        this.c = string2;
        this.e = string3;
        this.a = fr2;
        this.d = fr3;
    }

    public static void a(String string, String string2, fr fr2, fr fr3) {
        new Thread(new bj(string2, string, string2, fr2, fr3)).start();
    }

    public final void run() {
        try {
            MessageConnection messageConnection = (MessageConnection)Connector.open((String)this.b);
            TextMessage textMessage = (TextMessage)messageConnection.newMessage("text");
            textMessage.setAddress(this.b);
            textMessage.setPayloadText(this.c);
            messageConnection.send((Message)textMessage);
            System.out.println("SMS data: " + this.c + ", to: " + this.e);
            this.a.c();
            "".length();
        }
        catch (Exception exception) {
            this.d.c();
        }
        if ((0x77 ^ 0x73) == 0) {
            return;
        }
    }
}

