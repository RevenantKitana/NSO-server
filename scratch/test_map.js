const fs = require('fs');
const cp = require('child_process');

// Use jar.exe to extract map/22
try {
    cp.execSync('tools\\\\jdk\\\\bin\\\\jar.exe xf E:/TT/SETUP_LOCAL/PB/NSO_rework_v2.jar map/22', { cwd: 'scratch' });
    const buf = fs.readFileSync('scratch/map/22');
    console.log('Extracted map/22 length:', buf.length);
    console.log('Bytes 0..10:', [...buf.slice(0, 10)]);
    const w = buf[0];
    const h = buf[1];
    console.log('Width:', w, 'Height:', h, 'Expected map cells:', w * h, 'Remaining bytes:', buf.length - 2);
} catch (e) {
    console.error(e);
}
