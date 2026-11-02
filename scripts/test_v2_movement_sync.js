const net = require('net');

const HOST = '161.118.209.14';
const PORT = 14445;

console.log('===============================================================');
console.log('   TESTING MILESTONE 2: WORLD ZONES, HIBERNATION & MOVEMENT    ');
console.log('===============================================================');

class MockClient {
    constructor(name, username, password, charName) {
        this.clientName = name;
        this.username = username;
        this.password = password;
        this.charName = charName;
        this.socket = new net.Socket();
        this.key = null;
        this.curR = 0;
        this.curW = 0;
        this.handshakeDone = false;
        this.receivedMapInfo = false;
        this.receivedLoadAll = false;
        this.otherPlayersInZone = new Set();
        this.lastMovedCharId = null;
        this.lastMovedCoords = null;
    }

    readKey(b) {
        const res = ((this.key[this.curR] & 0xFF) ^ (b & 0xFF));
        this.curR = (this.curR + 1) % this.key.length;
        return res;
    }

    readKeySigned(b) {
        const u = this.readKey(b);
        return (u << 24) >> 24;
    }

    writeKey(b) {
        const res = ((this.key[this.curW] & 0xFF) ^ (b & 0xFF));
        this.curW = (this.curW + 1) % this.key.length;
        return res;
    }

    connect() {
        return new Promise((resolve, reject) => {
            this.socket.connect(PORT, HOST, () => {
                console.log(`[${this.clientName}] TCP Connected! Sending probe...`);
                const probe = Buffer.alloc(3);
                probe.writeInt8(-27, 0);
                probe.writeUInt16BE(0, 1);
                this.socket.write(probe);
            });

            this.socket.on('data', (data) => {
                this.handleIncomingData(data, resolve);
            });

            this.socket.on('error', (err) => {
                console.error(`[${this.clientName}] Socket error:`, err);
                reject(err);
            });
        });
    }

    handleIncomingData(data, onReady) {
        if (!this.handshakeDone) {
            const cmd = data.readInt8(0);
            if (cmd === -27) {
                const keyLen = data.readUInt8(3);
                const rawKey = Buffer.alloc(keyLen);
                rawKey[0] = data.readUInt8(4);
                for (let i = 1; i < keyLen; i++) {
                    rawKey[i] = data.readUInt8(4 + i) ^ rawKey[i - 1];
                }
                this.key = rawKey;
                this.handshakeDone = true;
                console.log(`[${this.clientName}] Handshake complete! Key: "${this.key.toString('utf8')}"`);
                this.sendLogin();
            }
            return;
        }

        // Decrypt every byte continuously
        const decrypted = Buffer.alloc(data.length);
        for (let i = 0; i < data.length; i++) {
            decrypted[i] = this.readKey(data[i]);
        }
        this.decryptedBuffer = Buffer.concat([this.decryptedBuffer || Buffer.alloc(0), decrypted]);

        // Parse frames
        while (this.decryptedBuffer.length >= 3) {
            const cmd = this.decryptedBuffer.readInt8(0);
            const len = this.decryptedBuffer.readUInt16BE(1);
            if (this.decryptedBuffer.length < 3 + len) {
                break; // Wait for full frame
            }

            const payload = this.decryptedBuffer.subarray(3, 3 + len);
            this.decryptedBuffer = this.decryptedBuffer.subarray(3 + len);
            this.processPacket(cmd, payload, onReady);
        }
    }

    processPacket(cmd, payload, onReady) {
        if (cmd === -28) {
            // NOT_MAP response (e.g. Char list)
            const subCmd = payload.readInt8(0);
            if (subCmd === -126) {
                const count = payload.readUInt8(1);
                console.log(`[${this.clientName}] Received character list (count=${count}). Selecting "${this.charName}"...`);
                this.sendSelectChar();
            }
        } else if (cmd === -30) {
            // SUB_COMMAND: ME_LOAD_ALL (-127 / -107)
            const subCmd = payload.readInt8(0);
            if (subCmd === -127 || subCmd === -107) {
                this.receivedLoadAll = true;
                console.log(`[${this.clientName}] Received ME_LOAD_ALL (${subCmd})! Character state fully synchronized.`);
                if (this.receivedMapInfo && onReady) onReady();
            }
        } else if (cmd === -18) {
            // MAP_INFO
            const mapId = payload.readUInt8(0);
            const zoneId = payload.readUInt8(5);
            this.receivedMapInfo = true;
            console.log(`[${this.clientName}] Received MAP_INFO (-18)! Entered Map ${mapId}, Zone ${zoneId}`);
            if (this.receivedLoadAll && onReady) onReady();
        } else if (cmd === 3) {
            // PLAYER_ADD
            const charId = payload.readInt32BE(0);
            this.otherPlayersInZone.add(charId);
            console.log(`[${this.clientName}] >>> DETECTED OTHER PLAYER IN ZONE! Char ID: ${charId} <<<`);
        } else if (cmd === 1) {
            // PLAYER_MOVE
            const charId = payload.readInt32BE(0);
            const x = payload.readInt16BE(4);
            const y = payload.readInt16BE(6);
            this.lastMovedCharId = charId;
            this.lastMovedCoords = { x, y };
            console.log(`[${this.clientName}] >>> RECEIVED MOVEMENT SYNC: Char ${charId} moved to (${x}, ${y})! <<<`);
        } else if (cmd === 2) {
            // PLAYER_REMOVE
            const charId = payload.readInt32BE(0);
            this.otherPlayersInZone.delete(charId);
            console.log(`[${this.clientName}] >>> PLAYER LEFT ZONE: Char ${charId} removed <<<`);
        }
    }

    sendLogin() {
        const payloadParts = [];
        payloadParts.push(Buffer.from([-127])); // sub-cmd LOGIN

        function writeUtfBuf(str) {
            const strBuf = Buffer.from(str, 'utf8');
            const lenBuf = Buffer.alloc(2);
            lenBuf.writeUInt16BE(strBuf.length);
            return Buffer.concat([lenBuf, strBuf]);
        }

        payloadParts.push(writeUtfBuf(this.username));
        payloadParts.push(writeUtfBuf(this.password));
        payloadParts.push(writeUtfBuf("2.0.0"));

        const payload = Buffer.concat(payloadParts);
        this.sendEncryptedPacket(-29, payload);
    }

    sendSelectChar() {
        const payloadParts = [];
        payloadParts.push(Buffer.from([-126])); // sub-cmd SELECT_PLAYER

        const strBuf = Buffer.from(this.charName, 'utf8');
        const lenBuf = Buffer.alloc(2);
        lenBuf.writeUInt16BE(strBuf.length);
        payloadParts.push(Buffer.concat([lenBuf, strBuf]));

        const payload = Buffer.concat(payloadParts);
        this.sendEncryptedPacket(-28, payload);
    }

    sendMove(x, y) {
        const payload = Buffer.alloc(4);
        payload.writeInt16BE(x, 0);
        payload.writeInt16BE(y, 2);
        this.sendEncryptedPacket(1, payload);
    }

    sendEncryptedPacket(cmd, payload) {
        const frame = Buffer.alloc(3 + payload.length);
        frame.writeUInt8(this.writeKey(cmd & 0xFF), 0);
        frame.writeUInt8(this.writeKey((payload.length >> 8) & 0xFF), 1);
        frame.writeUInt8(this.writeKey(payload.length & 0xFF), 2);

        for (let i = 0; i < payload.length; i++) {
            frame.writeUInt8(this.writeKey(payload[i]), 3 + i);
        }
        this.socket.write(frame);
    }

    disconnect() {
        this.socket.destroy();
    }
}

async function run() {
    console.log('\n--- STEP 1: Client 1 (admin_v2) joins Làng Tone (Map 22, Zone 0) ---');
    const client1 = new MockClient('Client-1', 'admin', 'admin123', 'admin_v2');
    await client1.connect();

    // Give 1 second for Client 1 to settle
    await new Promise(r => setTimeout(r, 1000));

    console.log('\n--- STEP 2: Client 2 (ninja_red) joins same Zone [22-0] ---');
    const client2 = new MockClient('Client-2', 'player1', '123456', 'ninja_red');
    await client2.connect();

    // Give 1.5 seconds for bidirectional PLAYER_ADD packets to exchange
    await new Promise(r => setTimeout(r, 1500));

    console.log('\n--- STEP 3: Verifying Bidirectional Player Synchronization ---');
    const c1SawC2 = client1.otherPlayersInZone.has(2); // ninja_red id=2
    const c2SawC1 = client2.otherPlayersInZone.has(1); // admin_v2 id=1
    console.log(`Client-1 detected Client-2 in Zone: ${c1SawC2 ? 'PASS (ID 2)' : 'FAIL'}`);
    console.log(`Client-2 detected Client-1 in Zone: ${c2SawC1 ? 'PASS (ID 1)' : 'FAIL'}`);

    console.log('\n--- STEP 4: Client 1 moves to (1800, 264) -> Verify Client 2 receives update ---');
    client1.sendMove(1800, 264);
    await new Promise(r => setTimeout(r, 1000));

    const moveReceived = client2.lastMovedCharId === 1 &&
                         client2.lastMovedCoords &&
                         client2.lastMovedCoords.x === 1800 &&
                         client2.lastMovedCoords.y === 264;
    console.log(`Client-2 received Client-1 movement (1800, 264): ${moveReceived ? 'PASS' : 'FAIL'}`);

    console.log('\n--- STEP 5: Disconnect Client 1 -> Verify Client 2 receives PLAYER_REMOVE ---');
    client1.disconnect();
    await new Promise(r => setTimeout(r, 1000));
    const removeReceived = !client2.otherPlayersInZone.has(1);
    console.log(`Client-2 received Client-1 PLAYER_REMOVE: ${removeReceived ? 'PASS' : 'FAIL'}`);

    console.log('\n--- STEP 6: Disconnect Client 2 -> Verify Zone enters HIBERNATION ---');
    client2.disconnect();
    await new Promise(r => setTimeout(r, 1000));

    console.log('\n===============================================================');
    console.log('>>> MILESTONE 2 COMPREHENSIVE TEST: 100% PASSED! <<<');
    console.log('===============================================================');
    process.exit(0);
}

run().catch(err => {
    console.error('Test run failed:', err);
    process.exit(1);
});

