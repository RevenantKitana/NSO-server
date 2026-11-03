const { execSync } = require('child_process');
const list = execSync('.\\tools\\jdk\\bin\\jar.exe tf .client/NSO_rework_v2.jar').toString().split(/\r?\n/);
for (const f of list) {
    if (f.endsWith('.class')) {
        try {
            execSync(`.\\tools\\jdk\\bin\\jar.exe xf .client/NSO_rework_v2.jar ${f}`);
            const out = execSync(`.\\tools\\jdk\\bin\\javap.exe -c -p ${f}`).toString();
            if (out.includes('vjmap') || out.includes('b:(Ljava/io/DataInputStream;)V')) {
                console.log('Match:', f);
            }
        } catch (e) {}
    }
}
