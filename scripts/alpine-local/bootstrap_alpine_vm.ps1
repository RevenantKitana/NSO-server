# ===============================================================================
# SCRIPT 1-CLICK THIET LAP MAY CHU ALPINE LINUX VM MOI TINH (BOOTSTRAP A-Z)
# Tu dong: Disk/Swap, OpenJDK 17 + jemalloc, MariaDB, Firewall, Data, DB, OpenRC
# ===============================================================================

. "$PSScriptRoot\ssh_alpine_helper.ps1"

Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "    QUY TRINH THIET LAP MAY CHU ALPINE LINUX VM MOI TINH (1-CLICK BOOTSTRAP)   " -ForegroundColor Yellow
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "  >> May chu Alpine : $VM_USER@$VM_IP" -ForegroundColor White
Write-Host "  >> SSH Key        : $KeyPath" -ForegroundColor White
Write-Host "  >> Server Profile : $ServerProfile" -ForegroundColor White
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

# ------------------------------------------------------------
# 1. KIEM TRA KET NOI SSH & TU DONG DONG BO SSH KEY
# ------------------------------------------------------------
Write-Host "[BUOC 1/6] Kiem tra ket noi SSH & xac thuc voi Alpine VM..." -ForegroundColor Cyan
if (!(Ensure-AlpineSshKey)) {
    Write-Host "[LOI] Khong the ket noi SSH toi $VM_USER@$VM_IP. Vui long kiem tra IP va Mat khau/Key!" -ForegroundColor Red
    exit 1
}
Write-Host "  >> Ket noi SSH toi Alpine VM thanh cong!" -ForegroundColor Green

# ------------------------------------------------------------
# 2. UPLOAD VA CHAY SETUP_ALPINE_VM.SH
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 2/6] Toi uu Alpine OS, Disk/Swap, Cai OpenJDK 17 + jemalloc, MariaDB & Firewall..." -ForegroundColor Cyan
$SetupScriptLocal = Join-Path $ProjectRoot "scripts\alpine-vm\setup_alpine_vm.sh"

Invoke-AlpineScp -Source "`"$SetupScriptLocal`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/setup_alpine_vm.sh" | Out-Null
$resSetup = Invoke-AlpineSsh -Command "sed -i 's/\r$//' /tmp/setup_alpine_vm.sh && sh /tmp/setup_alpine_vm.sh && rm -f /tmp/setup_alpine_vm.sh" -ConnectTimeout 30 -Interactive
if ($resSetup -ne 0) {
    Write-Host "[LOI] Cai dat setup_alpine_vm.sh tren Alpine VM that bai!" -ForegroundColor Red
    exit 1
}
Write-Host "  >> Toi uu Alpine OS, OpenJDK 17 + jemalloc & MariaDB thanh cong!" -ForegroundColor Green

# ------------------------------------------------------------
# 3. DONG GOI VA UPLOAD THU MUC DATA/
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 3/6] Dang dong goi va upload thu muc Data/ (Hinh anh, Map, Item, Mob)..." -ForegroundColor Cyan
$DataTar = Join-Path $ProjectRoot "Data_alpine_upload.tar"
if (Test-Path $DataTar) { Remove-Item -Force $DataTar }

Write-Host "  >> [1/3] Dang dong goi thu muc Data/ bang tar..." -ForegroundColor Gray
cmd.exe /c "tar -cf `"$DataTar`" -C `"$ProjectRoot`" Data"

Invoke-AlpineSsh -Command "mkdir -p /opt/nso-server" | Out-Null

$tarSizeMB = [math]::Round((Get-Item $DataTar).Length / 1MB, 2)
Write-Host "  >> [2/3] Dang upload Data ($tarSizeMB MB) sang Alpine VM (/opt/nso-server)..." -ForegroundColor Gray
$resScpData = Invoke-AlpineScp -Source "`"$DataTar`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/Data.tar" -ConnectTimeout 60

Remove-Item -Force $DataTar -ErrorAction SilentlyContinue

if ($resScpData -ne 0) {
    Write-Host "[LOI] Khong the upload Data.tar sang /opt/nso-server/!" -ForegroundColor Red
    exit 1
}

Write-Host "  >> [3/3] Dang giai nen du lieu Data tren Alpine VM..." -ForegroundColor Gray
Invoke-AlpineSsh -Command "cd /opt/nso-server && tar -xf Data.tar && rm -f Data.tar" -ConnectTimeout 30 | Out-Null
Write-Host "  >> Upload va giai nen Data/ thanh cong tren Alpine VM!" -ForegroundColor Green

# ------------------------------------------------------------
# 4. NAP DATABASE SACH (INIT_NSO_CLEAN.SQL)
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 4/6] Dang nap co so du lieu game sach (database/init_nso_clean.sql)..." -ForegroundColor Cyan
$SqlCleanLocal = Join-Path $ProjectRoot "database\init_nso_clean.sql"

Invoke-AlpineScp -Source "`"$SqlCleanLocal`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/init_nso_clean.sql" | Out-Null
Invoke-AlpineSsh -Command "mariadb -u root nso_test < /opt/nso-server/init_nso_clean.sql && rm -f /opt/nso-server/init_nso_clean.sql" -ConnectTimeout 30 | Out-Null
Write-Host "  >> Nap Database game sach 100% thanh cong tren MariaDB (Alpine)!" -ForegroundColor Green

# ------------------------------------------------------------
# 5. UPLOAD FILE CAU HINH THEO PROFILE (LOW_RESOURCE / STANDARD / AUTO)
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 5/6] Dang dong bo file cau hinh theo Profile [$ServerProfile]..." -ForegroundColor Cyan

$CfgProp = Join-Path $ProjectRoot "config\config.properties.low"
$MyProp = Join-Path $ProjectRoot "config\mysql.properties.low"
$OpenRcFile = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc.low"

if ($ServerProfile -eq "STANDARD") {
    $CfgProp = Join-Path $ProjectRoot "config\config.properties.standard"
    $MyProp = Join-Path $ProjectRoot "config\mysql.properties.standard"
    $OpenRcFile = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc.standard"
} elseif ($ServerProfile -eq "AUTO") {
    $CfgProp = Join-Path $ProjectRoot "config\config.properties.prod"
    $MyProp = Join-Path $ProjectRoot "config\mysql.properties.prod"
    $OpenRcFile = Join-Path $ProjectRoot "scripts\alpine-vm\nso-server.openrc"
}

Invoke-AlpineScp -Source "`"$CfgProp`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/config.properties" | Out-Null
Invoke-AlpineScp -Source "`"$MyProp`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/mysql.properties" | Out-Null

$ClientJarLocal = Join-Path $ProjectRoot ".client\NSO.jar"
if (Test-Path $ClientJarLocal) {
    Invoke-AlpineScp -Source "`"$ClientJarLocal`"" -Destination "$($VM_USER)@$($VM_IP):/opt/nso-server/NSO.jar" | Out-Null
    Write-Host "  >> Upload Client Game (NSO.jar) len Alpine VM thanh cong!" -ForegroundColor Green
}
Write-Host "  >> Cau hinh Profile [$ServerProfile] da duoc thiet lap tren Alpine VM!" -ForegroundColor Green

# ------------------------------------------------------------
# 6. DANG KY OPENRC SERVICE (/etc/init.d/nso-server) VA CRONJOB
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 6/6] Dang dang ky OpenRC Service (/etc/init.d/nso-server) va Cronjob sao luu..." -ForegroundColor Cyan
$AutoBackup = Join-Path $ProjectRoot "scripts\alpine-vm\auto_backup_alpine_db.sh"
$CheckStatus = Join-Path $ProjectRoot "scripts\alpine-vm\check_alpine_status.sh"

Invoke-AlpineScp -Source "`"$OpenRcFile`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/nso-server.openrc" | Out-Null
Invoke-AlpineScp -Source "`"$AutoBackup`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/auto_backup_alpine_db.sh" | Out-Null
Invoke-AlpineScp -Source "`"$CheckStatus`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/check_alpine_status.sh" | Out-Null

$setupOpenRcCmd = "sed -i 's/\r$//' /tmp/nso-server.openrc /tmp/auto_backup_alpine_db.sh /tmp/check_alpine_status.sh && " +
                  "mv -f /tmp/nso-server.openrc /etc/init.d/nso-server && " +
                  "mv -f /tmp/auto_backup_alpine_db.sh /opt/nso-server/auto_backup_alpine_db.sh && " +
                  "cp -f /tmp/check_alpine_status.sh /opt/nso-server/check_status.sh && " +
                  "mv -f /tmp/check_alpine_status.sh /root/check_status.sh && " +
                  "chmod +x /etc/init.d/nso-server /opt/nso-server/auto_backup_alpine_db.sh /opt/nso-server/check_status.sh /root/check_status.sh && " +
                  "rc-update add nso-server default >/dev/null 2>&1 || true && " +
                  "mkdir -p /opt/nso-server/logs /etc/crontabs && " +
                  "(crontab -l 2>/dev/null | grep -v 'auto_backup_alpine_db.sh'; echo '0 */12 * * * /opt/nso-server/auto_backup_alpine_db.sh >/dev/null 2>&1') | crontab - && " +
                  "rc-service crond restart >/dev/null 2>&1 || true"

Invoke-AlpineSsh -Command $setupOpenRcCmd | Out-Null

Write-Host ""
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "   HOAN TAT THIET LAP MAY CHU ALPINE LINUX VM THANH CONG 100%!                 " -ForegroundColor Green
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "  - Moi truong OpenJDK 17 + jemalloc + MariaDB + Swap + Firewall da san sang." -ForegroundColor Yellow
Write-Host "  - Du lieu Game (Data/) va Database sach (nso_test) da duoc khoi tao." -ForegroundColor Yellow
Write-Host "  - Dich vu OpenRC (/etc/init.d/nso-server) da duoc dang ky." -ForegroundColor Yellow
Write-Host ""
Write-Host ">> Bay gio ban chi can chon: [1] 1-Click Build & Deploy len Alpine VM!" -ForegroundColor Cyan
Write-Host "===============================================================================" -ForegroundColor Green

