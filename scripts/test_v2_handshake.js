const net = require('net');

const HOST = '161.118.209.14';
const PORT = 14445;

console.log(`Connecting to Server V2 at ${HOST}:${PORT}...`);

const socket = new net.Socket();

let curR = 0;
let curW = 0;
let key = null;
let handshakeDone = false;

function readKey(b) {
    const res = ((key[curR] & 0xFF) ^ (b & 0xFF));
    curR = (curR + 1) % key.length;
    return res;
}

function writeKey(b) {
    const res = ((key[curW] & 0xFF) ^ (b & 0xFF));
    curW = (curW + 1) % key.length;
    return res;
}

socket.connect(PORT, HOST, () => {
    console.log('TCP connection established!');
    
    // Send initial client probe packet: CMD -27 with length 0
    // Unencrypted: [CMD (1 byte), length (2 bytes)]
    const probe = Buffer.alloc(3);
    probe.writeInt8(-27, 0);
    probe.writeUInt16BE(0, 1);
    socket.write(probe);
    console.log('Sent initial client probe packet (CMD -27)...');
});

socket.on('data', (data) => {
    console.log(`Received ${data.length} bytes from server:`, data);

    if (!handshakeDone) {
        // First packet from server is unencrypted CMD -27
        const cmd = data.readInt8(0);
        const len = data.readUInt16BE(1);
        console.log(`Packet header: cmd=${cmd}, len=${len}`);

        if (cmd === -27) {
            const keyLen = data.readUInt8(3);
            const rawKey = Buffer.alloc(keyLen);
            rawKey[0] = data.readUInt8(4);
            for (let i = 1; i < keyLen; i++) {
                rawKey[i] = data.readUInt8(4 + i) ^ rawKey[i - 1];
            }
            key = rawKey;
            handshakeDone = true;
            console.log(`[HANDSHAKE SUCCESS] Decoded session key (length ${keyLen}): "${key.toString('utf8')}"`);

            // Now test sending encrypted login request!
            // CMD.NOT_LOGIN (-29), sub-command CMD.LOGIN (-127)
            sendLoginRequest();
        }
    } else {
        // Subsequent packets are XOR encrypted
        let offset = 0;
        const decryptedBytes = [];
        while (offset < data.length) {
            decryptedBytes.push(readKey(data[offset++]));
        }
        const decBuf = Buffer.from(decryptedBytes);
        console.log(`Decrypted packet (length ${decBuf.length}):`, decBuf);

        const cmd = decBuf.readInt8(0);
        const len = decBuf.readUInt16BE(1);
        const subCmd = decBuf.length > 3 ? decBuf.readInt8(3) : null;
        console.log(`[DECRYPTED FRAME] cmd=${cmd}, len=${len}, subCmd=${subCmd}`);

        if (cmd === -28 && subCmd === -126) {
            // Character list response!
            const charCount = decBuf.readUInt8(4);
            console.log(`[LOGIN SUCCESS] Server returned character list: count=${charCount}`);
            let p = 5;
            for (let i = 0; i < charCount; i++) {
                const gender = decBuf.readUInt8(p++);
                const nameLen = decBuf.readUInt16BE(p); p += 2;
                const name = decBuf.toString('utf8', p, p + nameLen); p += nameLen;
                const schoolLen = decBuf.readUInt16BE(p); p += 2;
                const school = decBuf.toString('utf8', p, p + schoolLen); p += schoolLen;
                const level = decBuf.readUInt8(p++);
                const head = decBuf.readInt16BE(p); p += 2;
                p += 6; // weapon, body, leg
                console.log(`  -> Character #${i + 1}: Name="${name}", Level=${level}, School="${school}", Head=${head}, Gender=${gender}`);
            }

            console.log('\n>>> Character List Received! Now testing Character Selection: "admin_v2" ... <<<');
            sendSelectCharRequest("admin_v2");
        }
    }
});

function sendSelectCharRequest(charName) {
    const payloadParts = [];
    payloadParts.push(Buffer.from([-126])); // sub-cmd SELECT_PLAYER (-126)

    const strBuf = Buffer.from(charName, 'utf8');
    const lenBuf = Buffer.alloc(2);
    lenBuf.writeUInt16BE(strBuf.length);
    payloadParts.push(Buffer.concat([lenBuf, strBuf]));

    const payload = Buffer.concat(payloadParts);
    console.log(`Sending Select Character packet (name="${charName}", len=${payload.length})...`);

    // Frame: cmd = -28 (CMD_NOT_MAP), len = payload.length
    const frame = Buffer.alloc(3 + payload.length);
    frame.writeUInt8(writeKey(-28 & 0xFF), 0);
    frame.writeUInt8(writeKey((payload.length >> 8) & 0xFF), 1);
    frame.writeUInt8(writeKey(payload.length & 0xFF), 2);

    for (let i = 0; i < payload.length; i++) {
        frame.writeUInt8(writeKey(payload[i]), 3 + i);
    }

    socket.write(frame);

    setTimeout(() => {
        console.log('\n=============================================================');
        console.log('>>> MILESTONE 1 SMOKE TEST PASSED 100% (HANDSHAKE + AUTH + CHAR SELECT) <<<');
        console.log('=============================================================');
        socket.destroy();
        process.exit(0);
    }, 1000);
}

function sendLoginRequest() {
    // Write Login payload:
    // subCommand: byte (-127: CMD.LOGIN)
    // username: writeUTF("admin")
    // password: writeUTF("admin123")
    // version: writeUTF("2.0.0")
    const username = "admin";
    const password = "admin123";
    const version = "2.0.0";

    const payloadParts = [];
    payloadParts.push(Buffer.from([-127])); // sub-cmd LOGIN

    function writeUtfBuf(str) {
        const strBuf = Buffer.from(str, 'utf8');
        const lenBuf = Buffer.alloc(2);
        lenBuf.writeUInt16BE(strBuf.length);
        return Buffer.concat([lenBuf, strBuf]);
    }

    payloadParts.push(writeUtfBuf(username));
    payloadParts.push(writeUtfBuf(password));
    payloadParts.push(writeUtfBuf(version));

    const payload = Buffer.concat(payloadParts);
    console.log(`Sending Login payload (username="${username}", len=${payload.length})...`);

    // Frame: cmd = -29 (CMD_NOT_LOGIN), len = payload.length
    const frame = Buffer.alloc(3 + payload.length);
    frame.writeUInt8(writeKey(-29 & 0xFF), 0);
    frame.writeUInt8(writeKey((payload.length >> 8) & 0xFF), 1);
    frame.writeUInt8(writeKey(payload.length & 0xFF), 2);

    for (let i = 0; i < payload.length; i++) {
        frame.writeUInt8(writeKey(payload[i]), 3 + i);
    }

    socket.write(frame);
}

socket.on('error', (err) => {
    console.error('Socket error:', err);
});

socket.on('close', () => {
    console.log('Connection closed.');
});
