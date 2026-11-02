package com.nsoz.v2.adapter.network;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
 * Clean binary packet representation for Server V2.
 * Decoupled from legacy Message to ensure zero unintended side effects.
 */
public class V2Message {

    @Getter
    @Setter
    private byte command;

    private ByteArrayOutputStream os;
    private DataOutputStream dos;

    private ByteArrayInputStream is;
    private DataInputStream dis;

    private byte[] data;

    public V2Message(int command) {
        this((byte) command);
    }

    public V2Message(byte command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public V2Message(byte command, byte[] data) {
        this.command = command;
        this.data = data;
        if (data != null) {
            this.is = new ByteArrayInputStream(data);
            this.dis = new DataInputStream(this.is);
        }
    }

    public DataOutputStream writer() {
        if (dos == null) {
            os = new ByteArrayOutputStream();
            dos = new DataOutputStream(os);
        }
        return dos;
    }

    public DataInputStream reader() {
        if (dis == null && data != null) {
            is = new ByteArrayInputStream(data);
            dis = new DataInputStream(is);
        }
        return dis;
    }

    public byte[] getData() {
        if (os != null) {
            return os.toByteArray();
        }
        return data != null ? data : new byte[0];
    }

    public void cleanup() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (is != null) is.close();
            if (os != null) os.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public String toString() {
        return String.format("V2Message[cmd=%d, len=%d]", command, getData().length);
    }
}

