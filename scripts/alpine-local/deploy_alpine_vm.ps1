# ===============================================================================
# SCRIPT UPLOAD & DEPLOY FILE JAR LEN ALPINE LINUX VM (OPENRC + JEMALLOC)
# ===============================================================================

. "$PSScriptRoot\ssh_alpine_helper.ps1"

$JarLocal = Join-Path $ProjectRoot "target\Nso-jar-with-dependencies.jar"
if (!(Test-Path $JarLocal)) {
    Write-Host "[LOI] Khong tim thay file JAR da build tai: $JarLocal" -ForegroundColor Red
    exit 1
}

if (!(Ensure-AlpineSshKey)) {
    Write-Host "[LOI] Khong the ket noi SSH toi Alpine VM ($VM_USER@$VM_IP)!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[BUOC 2/4] Chuan bi moi truong va sao luu ban JAR cu tren Alpine VM..." -ForegroundColor Cyan
$backupJarCmd = "mkdir -p /opt/nso-server/logs /opt/nso-server/backups && " +
                "if [ -f /opt/nso-server/Nso-jar-with-dependencies.jar ]; then " +
                "BFILE=/opt/nso-server/backups/Nso_backup_`$(date +%Y%m%d_%H%M%S).jar; " +
                "cp -f /opt/nso-server/Nso-jar-with-dependencies.jar `"`$BFILE`" && " +
                "echo '  >> Da sao luu ban JAR cu thanh cong.' && " +
                "ls -1t /opt/nso-server/backups/Nso_backup_*.jar 2>/dev/null | tail -n +4 | xargs -r rm -f; " +
                "else echo '  >> Chua co file JAR cu tren Alpine VM (Trien khai lan dau).'; fi"

Invoke-AlpineSsh -Command $backupJarCmd | Out-Null

Write-Host ""
Write-Host "[BUOC 3/4] Tai file JAR moi va dong bo cau hinh Profile ($ServerProfile) len Alpine VM..." -ForegroundColor Cyan

$LocalCfgSrc = Join-Path $ProjectRoot "config\config.properties.low"
$LocalSqlSrc = Join-Path $ProjectRoot "config\mysql.properties.low"
$LocalSvcSrc = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc.low"

if ($ServerProfile -eq "STANDARD") {
    $LocalCfgSrc = Join-Path $ProjectRoot "config\config.properties.standard"
    $LocalSqlSrc = Join-Path $ProjectRoot "config\mysql.properties.standard"
    $LocalSvcSrc = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc.standard"
} elseif ($ServerProfile -eq "AUTO") {
    $LocalCfgSrc = Join-Path $ProjectRoot "config\config.properties.prod"
    $LocalSqlSrc = Join-Path $ProjectRoot "config\mysql.properties.prod"
    $LocalSvcSrc = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc"
}

$resJar = Invoke-AlpineScp -Source "`"$JarLocal`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/Nso-jar-with-dependencies.jar" -ConnectTimeout 45
if ($resJar -ne 0) {
    Write-Host "[LOI] Khong the upload file Nso-jar-with-dependencies.jar len Alpine VM!" -ForegroundColor Red
    exit 1
}

$ClientJarLocal = Join-Path $ProjectRoot ".client\NSO.jar"
if (Test-Path $ClientJarLocal) {
    Write-Host "  [*] Dang upload Client Game [NSO.jar] len Alpine VM..." -ForegroundColor Gray
    Invoke-AlpineScp -Source "`"$ClientJarLocal`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/NSO.jar" | Out-Null
}

Invoke-AlpineScp -Source "`"$LocalSvcSrc`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/nso-server.openrc" | Out-Null
Invoke-AlpineScp -Source "`"$LocalCfgSrc`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/config.properties" | Out-Null
Invoke-AlpineScp -Source "`"$LocalSqlSrc`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/mysql.properties" | Out-Null

$CheckStatus = Join-Path $ProjectRoot "scripts\alpine-vm\check_alpine_status.sh"
if (Test-Path $CheckStatus) {
    Invoke-AlpineScp -Source "`"$CheckStatus`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/check_alpine_status.sh" | Out-Null
}

Write-Host ""
Write-Host "[BUOC 4/4] Khoi dong OpenRC Service (/etc/init.d/nso-server - Profile: $ServerProfile)..." -ForegroundColor Cyan
$startCmd = "mkdir -p /opt/nso-server/logs && " +
            "sed -i 's/\r$//' /tmp/nso-server.openrc /opt/nso-server/config.properties /opt/nso-server/mysql.properties 2>/dev/null || true && " +
            "mv -f /tmp/nso-server.openrc /etc/init.d/nso-server && " +
            "chmod +x /etc/init.d/nso-server && " +
            "if [ -f /tmp/check_alpine_status.sh ]; then sed -i 's/\r$//' /tmp/check_alpine_status.sh && cp -f /tmp/check_alpine_status.sh /opt/nso-server/check_status.sh && mv -f /tmp/check_alpine_status.sh /root/check_status.sh && chmod +x /opt/nso-server/check_status.sh /root/check_status.sh; fi && " +
            "rc-update add nso-server default >/dev/null 2>&1 || true && " +
            "rc-service mariadb start >/dev/null 2>&1 || true && " +
            "rc-service nso-server restart && " +
            "sleep 5 && " +
            "/root/check_status.sh"

Invoke-AlpineSsh -Command $startCmd -Interactive | Out-Null

