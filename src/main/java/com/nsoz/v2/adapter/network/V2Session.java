package com.nsoz.v2.adapter.network;

import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.domain.model.UserAccount;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Encapsulates the network session context for a connected client.
 * Tracks connection state, cryptographic XOR stream cipher cursors, and player entity context.
 */
@Getter
@Setter
public class V2Session {
    private static final Logger logger = LoggerFactory.getLogger(V2Session.class);
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    public static final AttributeKey<V2Session> SESSION_KEY = AttributeKey.valueOf("NSO_V2_SESSION");

    private final long sessionId;
    private final Channel channel;
    private final String ipAddress;

    // Cryptographic XOR stream state
    private byte[] key;
    private byte curR = 0;
    private byte curW = 0;
    private boolean sendKeyComplete = false;

    // Client metadata
    private byte clientType = 0;
    private byte zoomLevel = 1;
    private boolean isGps = false;
    private int width = 0;
    private int height = 0;
    private boolean isQwerty = false;
    private boolean isTouch = false;
    private String platform = "";
    private byte languageId = 0;
    private int provider = 0;
    private String agent = "";
    private String version = "";

    // Authenticated context
    private UserAccount userAccount;
    private PlayerCharacter activeCharacter;
    private boolean authenticated = false;
    private boolean inGame = false;

    public V2Session(Channel channel) {
        this.sessionId = ID_GENERATOR.getAndIncrement();
        this.channel = channel;
        this.ipAddress = channel.remoteAddress() != null ? channel.remoteAddress().toString() : "unknown";
    }

    public void generateKey(int serverId) {
        int randomNonce = ThreadLocalRandom.current().nextInt(10000);
        this.key = ("nso_sony_" + (serverId * randomNonce)).getBytes();
        this.curR = 0;
        this.curW = 0;
    }

    public byte readKey(byte b) {
        byte b2 = this.curR;
        this.curR = (byte) (b2 + 1);
        byte result = (byte) ((key[b2] & 0xFF) ^ (b & 0xFF));
        if (this.curR >= key.length) {
            this.curR %= key.length;
        }
        return result;
    }

    public byte writeKey(byte b) {
        byte b2 = this.curW;
        this.curW = (byte) (b2 + 1);
        byte result = (byte) ((key[b2] & 0xFF) ^ (b & 0xFF));
        if (this.curW >= key.length) {
            this.curW %= key.length;
        }
        return result;
    }

    public void send(V2Message message) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

    public void close() {
        if (channel != null && channel.isActive()) {
            channel.close();
        }
    }

    @Override
    public String toString() {
        return String.format("V2Session[id=%d, ip=%s, auth=%s, char=%s]",
                sessionId, ipAddress, authenticated, activeCharacter != null ? activeCharacter.getName() : "none");
    }
}

