const fs = require('fs');
const buf = fs.readFileSync('C:/Users/nqk68/.microemulator/suite-MioNso/vjmap.rs');

// Look for record in RecordStore
// In RecordStoreImpl: name(UTF), b(int), c(long), d(int)
let offset = 0;
const nameLen = buf.readUInt16BE(offset); offset += 2;
const name = buf.toString('utf8', offset, offset + nameLen); offset += nameLen;
const b = buf.readInt32BE(offset); offset += 4;
const c = buf.readBigInt64BE(offset); offset += 8;
const d = buf.readInt32BE(offset); offset += 4;
console.log({ name, b, c, d, offset });

const recordId = buf.readInt32BE(offset); offset += 4;
const recordLen = buf.readInt32BE(offset); offset += 4;
console.log({ recordId, recordLen, offset });

const data = buf.slice(offset, offset + recordLen);
let p = 0;
const da = data.readInt8(p++);
const numD = data.readUInt8(p++);
console.log({ da, numD });
for (let i = 0; i < numD; i++) {
    const sLen = data.readUInt16BE(p); p += 2;
    p += sLen;
}
const numFt = data.readInt8(p++);
console.log({ numFt });
for (let i = 0; i < numFt; i++) {
    const sLen = data.readUInt16BE(p); p += 2;
    p += sLen;
    const nNpc = data.readInt8(p++);
    for (let j = 0; j < nNpc; j++) {
        const len1 = data.readInt8(p++);
        for (let k = 0; k < len1; k++) {
            const utflen = data.readUInt16BE(p); p += 2;
            p += utflen;
        }
    }
}

const numBu = data.readUInt8(p++);
console.log({ numBu });
for (let i = 0; i < Math.min(10, numBu); i++) {
    const type = data.readInt8(p++);
    const nLen = data.readUInt16BE(p); p += 2;
    const bName = data.toString('utf8', p, p + nLen); p += nLen;
    const hp = data.readInt32BE(p); p += 4;
    const range = data.readInt8(p++);
    const speed = data.readInt8(p++);
    console.log(`bu[${i}]: type=${type}, name=${bName}, hp=${hp}, range=${range}, speed=${speed}`);
}

