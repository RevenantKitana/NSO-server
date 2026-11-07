import java.io.*;
import java.net.*;

public class VerifyServerV2Handshake {
    public static void main(String[] args) {
        System.out.println("=== VERIFYING SERVER ISOLATION AND HANDSHAKE ===");
        testServer("161.118.209.14", 14444, "Legacy Server V1");
        testServer("161.118.209.14", 14445, "Modernized Server V2");
    }

    private static void testServer(String host, int port, String label) {
        System.out.println("\n[*] Testing " + label + " (" + host + ":" + port + ")...");
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);
            System.out.println("  [+] TCP Connection established.");

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // Send initial ping packet: cmd = 0, size = 0
            dos.writeByte(0);
            dos.writeShort(0);
            dos.flush();
            System.out.println("  [+] Sent initial handshake ping.");

            byte cmd = dis.readByte();
            int size = dis.readUnsignedShort();
            System.out.println("  [+] Received response: CMD=" + cmd + " (expected -27 for GET_SESSION_ID), payload size=" + size + " bytes");

            if (cmd == -27) {
                System.out.println("  [SUCCESS] " + label + " handshake verified!");
            } else {
                System.out.println("  [WARNING] Unexpected CMD: " + cmd);
            }
        } catch (Exception e) {
            System.err.println("  [FAILED] Could not complete handshake: " + e.getMessage());
        }
    }
}

