const fs = require('fs');
const buf = fs.readFileSync('C:/Users/nqk68/.microemulator/suite-MioNso/vjmap.rs');

// The record data starts at offset 28
const data = buf.slice(28);
console.log('Record data length:', data.length);

let offset = 0;
// Read according to cf.b(DataInputStream):
// ap.da = dis.readByte();
const da = data.readInt8(offset++);
console.log('ap.da:', da);

// de.d: array of String
const numD = data.readUInt8(offset++);
console.log('de.d count:', numD);
for (let i = 0; i < numD; i++) {
    const sLen = data.readUInt16BE(offset);
    offset += 2;
    const str = data.toString('utf8', offset, offset + sLen);
    offset += sLen;
}

// ds.b: array of ft
const numFt = data.readInt8(offset++);
console.log('numFt (ds.b count):', numFt);
for (let i = 0; i < numFt; i++) {
    const nameLen = data.readUInt16BE(offset); offset += 2;
    offset += nameLen; // name
    const nNpc = data.readInt8(offset++);
    for (let j = 0; j < nNpc; j++) {
        const len1 = data.readInt8(offset++);
        for (let k = 0; k < len1; k++) {
            const utflen = data.readUInt16BE(offset); offset += 2;
            offset += utflen;
        }
    }
}

// by.h: array of bu!
const numBu = data.readUInt8(offset++);
console.log('numBu (by.h count):', numBu);
for (let i = 0; i < numBu; i++) {
    const type = data.readInt8(offset++);
    const nameLen = data.readUInt16BE(offset); offset += 2;
    const name = data.toString('utf8', offset, offset + nameLen); offset += nameLen;
    const hp = data.readInt32BE(offset); offset += 4;
    const range = data.readInt8(offset++);
    const speed = data.readInt8(offset++);
    if (i < 10) {
        console.log(`bu[${i}]: name="${name}", type=${type}, hp=${hp}, range=${range}, speed=${speed}`);
    }
}
