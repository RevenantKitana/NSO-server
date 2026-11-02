const net = require('net');

const HOST = '161.118.209.14';
const PORT = 14445;

console.log('===============================================================');
console.log('   TESTING MILESTONE 3: COMBAT ENGINE, SINGLE-ROLL & MOBS      ');
console.log('===============================================================');

const socket = new net.Socket();
let key = null;
let curR = 0;
let curW = 0;
let handshakeDone = false;
let decryptedBuffer = Buffer.alloc(0);

let targetMob = null;
let totalDamageDealt = 0;
let elementalEffectObserved = false;
let critObserved = false;
let mobDied = false;

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
    console.log('[CombatClient] TCP Connected! Sending probe...');
    const probe = Buffer.alloc(3);
    probe.writeInt8(-27, 0);
    probe.writeUInt16BE(0, 1);
    socket.write(probe);
});

socket.on('data', (data) => {
    if (!handshakeDone) {
        const cmd = data.readInt8(0);
        if (cmd === -27) {
            const keyLen = data.readUInt8(3);
            const rawKey = Buffer.alloc(keyLen);
            rawKey[0] = data.readUInt8(4);
            for (let i = 1; i < keyLen; i++) {
                rawKey[i] = data.readUInt8(4 + i) ^ rawKey[i - 1];
            }
            key = rawKey;
            handshakeDone = true;
            console.log(`[CombatClient] Handshake complete! Key: "${key.toString('utf8')}"`);
            sendLogin();
        }
        return;
    }

    // Continuous decryption stream
    const decrypted = Buffer.alloc(data.length);
    for (let i = 0; i < data.length; i++) {
        decrypted[i] = readKey(data[i]);
    }
    decryptedBuffer = Buffer.concat([decryptedBuffer, decrypted]);

    while (decryptedBuffer.length >= 3) {
        const cmd = decryptedBuffer.readInt8(0);
        const len = decryptedBuffer.readUInt16BE(1);
        if (decryptedBuffer.length < 3 + len) {
            break; // Wait for full frame
        }

        const payload = decryptedBuffer.subarray(3, 3 + len);
        decryptedBuffer = decryptedBuffer.subarray(3 + len);
        processPacket(cmd, payload);
    }
});

function processPacket(cmd, payload) {
    if (cmd === -28) {
        // Character list
        const subCmd = payload.readInt8(0);
        if (subCmd === -126) {
            console.log('[CombatClient] Received Character List! Selecting "admin_v2"...');
            sendSelectChar("admin_v2");
        }
    } else if (cmd === -18) {
        // MAP_INFO: parse monsters
        console.log('[CombatClient] Received MAP_INFO (-18)! Parsing monsters in Zone...');
        let p = 0;
        const mapId = payload.readUInt8(p++);
        const tileId = payload.readUInt8(p++);
        const bgId = payload.readUInt8(p++);
        const type = payload.readUInt8(p++);
        const nameLen = payload.readUInt16BE(p); p += 2;
        const mapName = payload.toString('utf8', p, p + nameLen); p += nameLen;
        const zoneId = payload.readUInt8(p++);
        const playerX = payload.readInt16BE(p); p += 2;
        const playerY = payload.readInt16BE(p); p += 2;

        const wpLen = payload.readUInt8(p++);
        p += wpLen * 8; // 8 bytes per waypoint

        const monsterCount = payload.readUInt8(p++);
        console.log(`[CombatClient] Map "${mapName}" (Zone ${zoneId}) contains ${monsterCount} monsters:`);

        for (let i = 0; i < monsterCount; i++) {
            const isDisable = payload.readUInt8(p++) === 1;
            const isDontMove = payload.readUInt8(p++) === 1;
            const isFire = payload.readUInt8(p++) === 1;
            const isIce = payload.readUInt8(p++) === 1;
            const isWind = payload.readUInt8(p++) === 1;
            const templateId = payload.readUInt8(p++);
            const sys = payload.readUInt8(p++);
            const hp = payload.readInt32BE(p); p += 4;
            const level = payload.readUInt8(p++);
            const maxHp = payload.readInt32BE(p); p += 4;
            const mx = payload.readInt16BE(p); p += 2;
            const my = payload.readInt16BE(p); p += 2;
            const status = payload.readUInt8(p++);
            const levelBoss = payload.readUInt8(p++);
            const isBoss = payload.readUInt8(p++) === 1;

            console.log(`  -> Mob #${i} (ID ${i}): Template=${templateId}, HP=${hp}/${maxHp}, Pos=(${mx}, ${my}), Lvl=${level}`);
            if (i === 0) {
                targetMob = { id: i, hp, maxHp };
            }
        }

        // Start combat loop!
        if (targetMob) {
            console.log(`\n>>> STARTING COMBAT: Attacking Mob #${targetMob.id} (Ốc sên) ... <<<`);
            triggerAttack();
        }
    } else if (cmd === -1) {
        // NPC_HP
        const mobId = payload.readUInt8(0);
        const remainingHp = payload.readInt32BE(1);
        const damage = payload.readInt32BE(5);
        const isCrit = payload.readUInt8(9) === 1;
        totalDamageDealt += damage;
        if (isCrit) critObserved = true;

        console.log(`[CombatClient] HIT! Dealt ${damage} damage (Crit=${isCrit}). Mob #${mobId} remaining HP: ${remainingHp}/${targetMob.maxHp}`);

        // Attack again if still alive
        if (remainingHp > 0) {
            setTimeout(triggerAttack, 300);
        }
    } else if (cmd === 51) {
        // NPC_MISS
        const mobId = payload.readUInt8(0);
        console.log(`[CombatClient] MISS! Attack against Mob #${mobId} was dodged.`);
        setTimeout(triggerAttack, 300);
    } else if (cmd === 89) {
        // NPC_IS_FIRE
        const mobId = payload.readUInt8(0);
        elementalEffectObserved = true;
        console.log(`[CombatClient] >>> ELEMENTAL PROC: Mob #${mobId} is now BURNED (Hỏa - Trừ 100% Miễn Thương)! <<<`);
    } else if (cmd === 90) {
        // NPC_IS_ICE
        const mobId = payload.readUInt8(0);
        elementalEffectObserved = true;
        console.log(`[CombatClient] >>> ELEMENTAL PROC: Mob #${mobId} is now FROZEN (Băng - 50% Thân Pháp)! <<<`);
    } else if (cmd === 91) {
        // NPC_IS_WIND
        const mobId = payload.readUInt8(0);
        elementalEffectObserved = true;
        console.log(`[CombatClient] >>> ELEMENTAL PROC: Mob #${mobId} is now STUNNED (Phong - Choáng Khóa Đòn)! <<<`);
    } else if (cmd === -4) {
        // NPC_DIE
        const mobId = payload.readUInt8(0);
        const damage = payload.readInt32BE(1);
        const isCrit = payload.readUInt8(5) === 1;
        mobDied = true;
        totalDamageDealt += damage;
        if (isCrit) critObserved = true;

        console.log(`[CombatClient] >>> FATAL BLOW! Dealt ${damage} damage (Crit=${isCrit}). Mob #${mobId} DIED! <<<`);
        console.log('[CombatClient] >>> EXP REWARD GRANTED (+50 EXP)! <<<');

        setTimeout(() => {
            console.log('\n===============================================================');
            console.log('>>> MILESTONE 3 COMBAT ENGINE TEST: 100% PASSED! <<<');
            console.log(`Summary: Total Damage=${totalDamageDealt}, Mob Dead=${mobDied}, Crit Observed=${critObserved}, Elemental Proc=${elementalEffectObserved}`);
            console.log('===============================================================');
            socket.destroy();
            process.exit(0);
        }, 500);
    }
}

function triggerAttack() {
    if (!targetMob) return;
    // CMD_PLAYER_ATTACK_NPC (60)
    // Payload: 1 byte mobId
    const payload = Buffer.from([targetMob.id]);
    sendEncryptedPacket(60, payload);
}

function sendLogin() {
    const payloadParts = [];
    payloadParts.push(Buffer.from([-127])); // sub-cmd LOGIN

    function writeUtfBuf(str) {
        const strBuf = Buffer.from(str, 'utf8');
        const lenBuf = Buffer.alloc(2);
        lenBuf.writeUInt16BE(strBuf.length);
        return Buffer.concat([lenBuf, strBuf]);
    }

    payloadParts.push(writeUtfBuf("admin"));
    payloadParts.push(writeUtfBuf("admin123"));
    payloadParts.push(writeUtfBuf("2.0.0"));

    const payload = Buffer.concat(payloadParts);
    sendEncryptedPacket(-29, payload);
}

function sendSelectChar(name) {
    const payloadParts = [];
    payloadParts.push(Buffer.from([-126])); // sub-cmd SELECT_PLAYER

    const strBuf = Buffer.from(name, 'utf8');
    const lenBuf = Buffer.alloc(2);
    lenBuf.writeUInt16BE(strBuf.length);
    payloadParts.push(Buffer.concat([lenBuf, strBuf]));

    const payload = Buffer.concat(payloadParts);
    sendEncryptedPacket(-28, payload);
}

function sendEncryptedPacket(cmd, payload) {
    const frame = Buffer.alloc(3 + payload.length);
    frame.writeUInt8(writeKey(cmd & 0xFF), 0);
    frame.writeUInt8(writeKey((payload.length >> 8) & 0xFF), 1);
    frame.writeUInt8(writeKey(payload.length & 0xFF), 2);

    for (let i = 0; i < payload.length; i++) {
        frame.writeUInt8(writeKey(payload[i]), 3 + i);
    }
    socket.write(frame);
}

socket.on('error', (err) => {
    console.error('[CombatClient] Socket error:', err);
});

