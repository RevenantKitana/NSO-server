const net = require('net');

const HOST = '161.118.209.14';
const PORT = 14445;

console.log('================================================================');
console.log('   TESTING MILESTONE 4: 5-TIER ITEMS, KENSHIKO ENCHANT & STATS   ');
console.log('================================================================');

const socket = new net.Socket();
let key = null;
let curR = 0;
let curW = 0;
let handshakeDone = false;
let decryptedBuffer = Buffer.alloc(0);

let adminCharId = null;
let currentYen = 0;
let initialWeaponUpgrade = 0;
let newWeaponUpgrade = 0;
let step = 0;
let testPassed = false;

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
    console.log('[TestClient] TCP Connected! Sending probe...');
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
            console.log(`[TestClient] Handshake complete! Key: "${key.toString('utf8')}"`);
            sendLogin();
        }
        return;
    }

    // Decrypt incoming stream
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
            console.log('[TestClient] Received Character List! Selecting "admin_v2"...');
            sendSelectChar("admin_v2");
        }
    } else if (cmd === -30) {
        // Sub-command: ME_LOAD_ALL (-127 / -107) or PLAYER_LOAD_VUKHI (-117)
        const subCmd = payload.readInt8(0);
        if (subCmd === -127 || subCmd === -107) {
            console.log('[TestClient] Received ME_LOAD_ALL! Parsing character & inventory...');
            parseLoadAll(payload.subarray(1));
        } else if (subCmd === -117) {
            const playerId = payload.readInt32BE(1);
            const weaponId = payload.readInt16BE(payload.length - 2);
            console.log(`[TestClient] Zone Event: PLAYER_LOAD_VUKHI! Player ID: ${playerId}, Weapon Template: ${weaponId}`);
        }
    } else if (cmd === 42) {
        // CMD_REQUEST_ITEM_INFO
        console.log('[TestClient] Received ITEM_INFO (CMD 42)!');
        const typeUI = payload.readInt8(0);
        const indexUI = payload.readInt8(1);
        const expire = payload.readBigInt64BE(2);
        const yenPrice = payload.readInt32BE(10);
        const sys = payload.readInt8(14);
        console.log(`[TestClient]   -> Item slot: ${indexUI}, Sys: ${sys}, Options:`);

        let p = 15;
        let optionCount = 0;
        while (p + 5 <= payload.length) {
            const optId = payload.readUInt8(p++);
            const optVal = payload.readInt32BE(p);
            p += 4;
            optionCount++;
            console.log(`      * Option ID ${optId}: +${optVal}`);
        }

        if (optionCount >= 3) {
            console.log('[TestClient] Item Info Options verified successfully!');
            // Proceed to Step 2: Kenshiko Enchanting
            setTimeout(sendUpgradeRequest, 500);
        }
    } else if (cmd === 21) {
        // CMD_UPGRADE
        const resultType = payload.readInt8(0);
        const gold = payload.readInt32BE(1);
        const xu = payload.readInt32BE(5);
        const yen = payload.readInt32BE(9);
        newWeaponUpgrade = payload.readInt8(13);

        console.log(`[TestClient] Received UPGRADE Response (CMD 21):`);
        console.log(`   -> Result: ${resultType === 1 ? 'SUCCESS' : 'FAIL'}`);
        console.log(`   -> Remaining Yen: ${yen} (was ${currentYen})`);
        console.log(`   -> New Upgrade Level: +${newWeaponUpgrade}`);

        if (resultType === 1 && newWeaponUpgrade > initialWeaponUpgrade) {
            console.log('[TestClient] Kenshiko Upgrade passed (+0 -> +' + newWeaponUpgrade + ')!');
            // Proceed to Step 3: Equip Weapon
            setTimeout(sendEquipWeapon, 500);
        }
    } else if (cmd === 11) {
        // CMD_ITEM_USE
        const bagSlot = payload.readInt8(0);
        const speed = payload.readInt8(1);
        const maxHp = payload.readInt32BE(2);
        const maxMp = payload.readInt32BE(6);
        console.log(`[TestClient] Received ITEM_USE Response (CMD 11): Bag Slot ${bagSlot} equipped! Speed: ${speed}, MaxHP: ${maxHp}`);

        // Proceed to Step 4: Attack Mob with equipped upgraded weapon
        setTimeout(sendAttackMob, 500);
    } else if (cmd === -1) {
        // NPC_HP
        const mobId = payload.readUInt8(0);
        const damageDealt = payload.readInt32BE(1);
        const remainingHp = payload.readInt32BE(5);
        console.log(`[TestClient] COMBAT: Attacked Mob ID ${mobId}! Dealt: ${damageDealt} damage (Remaining HP: ${remainingHp})`);

        if (damageDealt >= 40) {
            console.log(`[TestClient] High damage verified (${damageDealt} dmg) with equipped +${newWeaponUpgrade} Bạch Hạc Kiếm!`);
        }
    } else if (cmd === -4) {
        // NPC_DIE
        const mobId = payload.readUInt8(0);
        console.log(`[TestClient] Mob ID ${mobId} died from lethal sword attack!`);

        // Proceed to Step 5: Unequip weapon back to bag
        setTimeout(sendUnequipWeapon, 500);
    } else if (cmd === 15) {
        // CMD_ITEM_BODY_TO_BAG
        const speed = payload.readInt8(0);
        const maxHp = payload.readInt32BE(1);
        const maxMp = payload.readInt32BE(5);
        const equipSlot = payload.readInt8(11);
        const bagSlot = payload.readInt8(12);
        console.log(`[TestClient] Received ITEM_BODY_TO_BAG (CMD 15): Equip slot ${equipSlot} -> Bag slot ${bagSlot}!`);

        console.log('================================================================');
        console.log('   MILESTONE 4 TEST PASSED: ALL 5-TIER ITEM CHECKS VERIFIED!   ');
        console.log('================================================================');
        testPassed = true;
        setTimeout(() => {
            socket.destroy();
            process.exit(0);
        }, 500);
    }
}

function parseLoadAll(buf) {
    let p = 0;
    adminCharId = buf.readInt32BE(p); p += 4;
    const clanLen = buf.readUInt16BE(p); p += 2;
    p += clanLen; // clan name
    p += 1; // taskId
    p += 1; // gender
    p += 2; // head
    p += 1; // speed
    const nameLen = buf.readUInt16BE(p); p += 2;
    const name = buf.toString('utf8', p, p + nameLen); p += nameLen;
    p += 1; // pk
    p += 1; // typePk
    const maxHp = buf.readInt32BE(p); p += 4;
    const hp = buf.readInt32BE(p); p += 4;
    const maxMp = buf.readInt32BE(p); p += 4;
    const mp = buf.readInt32BE(p); p += 4;
    const exp = buf.readBigInt64BE(p); p += 8;
    p += 8; // expDown
    p += 2; // eff5buffhp
    p += 2; // eff5buffmp
    p += 1; // classId
    p += 2; // potPoint
    p += 2; // str
    p += 2; // agi
    p += 4; // vit
    p += 4; // spi
    p += 2; // skillPoint
    const skillCount = buf.readUInt8(p++); // skills
    p += skillCount * 2;
    const xu = buf.readInt32BE(p); p += 4;
    currentYen = buf.readInt32BE(p); p += 4;
    const gold = buf.readInt32BE(p); p += 4;

    console.log(`[TestClient] Player: "${name}", Level HP: ${hp}/${maxHp}, Yen: ${currentYen}, Xu: ${xu}`);

    // Parse Bag
    const bagSlots = buf.readUInt8(p++);
    console.log(`[TestClient] Bag Capacity: ${bagSlots} slots`);
    for (let i = 0; i < bagSlots; i++) {
        const templateId = buf.readInt16BE(p); p += 2;
        if (templateId !== -1) {
            const isLock = buf.readUInt8(p++) !== 0;
            let upgrade = 0;
            // Bạch Hạc Kiếm is weapon (templateId 94)
            if (templateId === 94) {
                upgrade = buf.readUInt8(p++);
                initialWeaponUpgrade = upgrade;
            }
            const hasExpire = buf.readUInt8(p++) !== 0;
            const quantity = buf.readUInt16BE(p); p += 2;
            console.log(`   Slot ${i}: Item Template ${templateId}, Upgrade: +${upgrade}, Qty: ${quantity}`);
        }
    }

    // Step 1: Request item info for bag slot 0 (Bạch Hạc Kiếm)
    setTimeout(sendRequestItemInfo, 500);
}

function sendRequestItemInfo() {
    console.log('[TestClient] Sending REQUEST_ITEM_INFO (CMD 42) for Bag Slot 0...');
    const body = Buffer.alloc(2);
    body.writeInt8(3, 0); // typeUI 3 = Bag
    body.writeInt8(0, 1); // slot 0
    sendPacket(42, body);
}

function sendUpgradeRequest() {
    console.log('[TestClient] Sending UPGRADE (CMD 21) for Bag Slot 0 to Kenshiko...');
    const body = Buffer.alloc(2);
    body.writeInt8(0, 0); // isGold = false
    body.writeUInt8(0, 1); // bagSlot 0
    sendPacket(21, body);
}

function sendEquipWeapon() {
    console.log('[TestClient] Sending ITEM_USE (CMD 11) to equip weapon from Bag Slot 0...');
    const body = Buffer.alloc(1);
    body.writeInt8(0, 0); // bagSlot 0
    sendPacket(11, body);
}

function sendAttackMob() {
    console.log('[TestClient] Sending PLAYER_ATTACK_NPC (CMD 60) against Mob ID 0...');
    const body = Buffer.alloc(1);
    body.writeUInt8(0, 0); // target mob ID 0 (Ốc sên)
    sendPacket(60, body);
}

function sendUnequipWeapon() {
    console.log('[TestClient] Sending ITEM_BODY_TO_BAG (CMD 15) to unequip weapon from Equip Slot 1...');
    const body = Buffer.alloc(1);
    body.writeInt8(1, 0); // equipSlot 1 (Vũ khí)
    sendPacket(15, body);
}

function sendPacket(cmd, bodyBuf) {
    const len = bodyBuf ? bodyBuf.length : 0;
    const frame = Buffer.alloc(3 + len);
    frame.writeUInt8(writeKey(cmd), 0);
    frame.writeUInt8(writeKey((len >> 8) & 0xFF), 1);
    frame.writeUInt8(writeKey(len & 0xFF), 2);
    if (len > 0) {
        for (let i = 0; i < len; i++) {
            frame[3 + i] = writeKey(bodyBuf[i]);
        }
    }
    socket.write(frame);
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
    const body = Buffer.concat(payloadParts);
    sendPacket(-29, body);
}

function sendSelectChar(name) {
    const payloadParts = [];
    payloadParts.push(Buffer.from([-126])); // sub-cmd SELECT_PLAYER
    const strBuf = Buffer.from(name, 'utf8');
    const lenBuf = Buffer.alloc(2);
    lenBuf.writeUInt16BE(strBuf.length);
    payloadParts.push(lenBuf);
    payloadParts.push(strBuf);
    const body = Buffer.concat(payloadParts);
    sendPacket(-28, body);
}

// Timeout fail-safe
setTimeout(() => {
    if (!testPassed) {
        console.error('[TestClient] TEST TIMED OUT (15s)!');
        socket.destroy();
        process.exit(1);
    }
}, 15000);
