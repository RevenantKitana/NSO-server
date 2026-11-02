package com.nsoz.v2.adapter.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.CorruptedFrameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Netty Codec for binary J2ME NSO wire format with stream XOR cipher.
 * Handles framing, variable-length payloads, and bidirectional stream decryption/encryption.
 */
public class V2PacketCodec extends ByteToMessageCodec<V2Message> {
    private static final Logger logger = LoggerFactory.getLogger(V2PacketCodec.class);
    public static final byte CMD_FULL_SIZE = -32;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        V2Session session = ctx.channel().attr(V2Session.SESSION_KEY).get();
        if (session == null) {
            return;
        }

        // Before handshake: packets are unencrypted
        if (!session.isSendKeyComplete()) {
            if (in.readableBytes() < 3) {
                return; // Need cmd (1 byte) + size (2 bytes)
            }
            in.markReaderIndex();
            byte cmd = in.readByte();
            int size = in.readUnsignedShort();

            if (in.readableBytes() < size) {
                in.resetReaderIndex();
                return;
            }

            byte[] data = new byte[size];
            in.readBytes(data);
            out.add(new V2Message(cmd, data));
            return;
        }

        // After handshake: packets are encrypted with cyclic XOR stream cipher
        while (in.readableBytes() >= 3) {
            int initialReaderIndex = in.readerIndex();
            byte savedCurR = session.getCurR();

            // Decrypt command opcode
            byte cmd = session.readKey(in.readByte());
            int size;

            if (cmd == CMD_FULL_SIZE) {
                if (in.readableBytes() < 5) {
                    // Need real command (1 byte) + int length (4 bytes)
                    in.readerIndex(initialReaderIndex);
                    session.setCurR(savedCurR);
                    return;
                }
                cmd = session.readKey(in.readByte());
                byte b1 = session.readKey(in.readByte());
                byte b2 = session.readKey(in.readByte());
                byte b3 = session.readKey(in.readByte());
                byte b4 = session.readKey(in.readByte());
                size = ((b1 & 0xFF) << 24) | ((b2 & 0xFF) << 16) | ((b3 & 0xFF) << 8) | (b4 & 0xFF);
            } else {
                byte b1 = session.readKey(in.readByte());
                byte b2 = session.readKey(in.readByte());
                size = ((b1 & 0xFF) << 8) | (b2 & 0xFF);
            }

            if (size < 0 || size > 65536) {
                throw new CorruptedFrameException("Invalid encrypted frame size: " + size);
            }

            if (in.readableBytes() < size) {
                // Incomplete payload: rollback reader index AND stream cipher cursor
                in.readerIndex(initialReaderIndex);
                session.setCurR(savedCurR);
                return;
            }

            // Read and decrypt full payload
            byte[] data = new byte[size];
            for (int i = 0; i < size; i++) {
                data[i] = session.readKey(in.readByte());
            }

            out.add(new V2Message(cmd, data));
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, V2Message msg, ByteBuf out) {
        V2Session session = ctx.channel().attr(V2Session.SESSION_KEY).get();
        if (session == null) {
            return;
        }

        byte[] data = msg.getData();
        int len = data.length;

        if (!session.isSendKeyComplete()) {
            // Handshake packet (CMD -27) is transmitted unencrypted
            out.writeByte(msg.getCommand());
            out.writeShort(len);
            out.writeBytes(data);
        } else {
            // Encrypted transmission
            if (len > Short.MAX_VALUE) {
                out.writeByte(session.writeKey(CMD_FULL_SIZE));
                out.writeByte(session.writeKey(msg.getCommand()));
                out.writeByte(session.writeKey((byte) (len >> 24)));
                out.writeByte(session.writeKey((byte) (len >> 16)));
                out.writeByte(session.writeKey((byte) (len >> 8)));
                out.writeByte(session.writeKey((byte) (len & 0xFF)));
            } else {
                out.writeByte(session.writeKey(msg.getCommand()));
                out.writeByte(session.writeKey((byte) (len >> 8)));
                out.writeByte(session.writeKey((byte) (len & 0xFF)));
            }

            for (byte b : data) {
                out.writeByte(session.writeKey(b));
            }
        }
    }
}

