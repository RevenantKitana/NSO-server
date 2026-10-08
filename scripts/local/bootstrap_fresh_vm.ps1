# ===============================================================================
# SCRIPT 1-CLICK THIET LAP MAY CHU CLOUD VM MOI TINH (BOOTSTRAP FULL ENVIRONMENT)
# Tu dong: Cai Java 17, MariaDB, Swap 4GB, Firewall, Upload Data, Nap Database sach, Tao Service
# ===============================================================================

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$ProjectRoot = (Get-Item "$PSScriptRoot\..\..").FullName
$ConfigFile = Join-Path $ProjectRoot "config\server_config.ini"

if (!(Test-Path $ConfigFile)) {
    Write-Host "[LOI] Khong tim thay file cau hinh: $ConfigFile" -ForegroundColor Red
    exit 1
}

$Config = @{}
Get-Content $ConfigFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -and -not $line.StartsWith("#")) {
        $parts = $line.Split("=", 2)
        if ($parts.Length -eq 2) {
            $Config[$parts[0].Trim()] = $parts[1].Trim()
        }
    }
}

if (!$Config["VM_IP"] -or !$Config["VM_USER"] -or !$Config["SSH_KEY"]) {
    Write-Host "[LOI] File config/server_config.ini thieu cac thong so bat buoc (VM_IP, VM_USER, SSH_KEY)!" -ForegroundColor Red
    exit 1
}

$VM_IP = $Config["VM_IP"]
$VM_USER = $Config["VM_USER"]
$KeyRel = $Config["SSH_KEY"]
$KeyPath = [System.IO.Path]::GetFullPath((Join-Path $ProjectRoot $KeyRel))

Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "       QUY TRINH THIET LAP MAY CHU CLOUD VM MOI TINH (1-CLICK BOOTSTRAP)       " -ForegroundColor Yellow
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "  >> May chu VM : $VM_USER@$VM_IP" -ForegroundColor White
Write-Host "  >> SSH Key    : $KeyPath" -ForegroundColor White
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

if (!(Test-Path $KeyPath)) {
    Write-Host "[LOI] Khong tim thay SSH Key tai: $KeyPath" -ForegroundColor Red
    exit 1
}

# Tu dong fix quyen file SSH Key tren Windows (Tranh loi Bad permissions)
cmd.exe /c "icacls `"$KeyPath`" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1"

# ------------------------------------------------------------
# 1. TEST KET NOI SSH
# ------------------------------------------------------------
Write-Host "[BUOC 1/6] Kiem tra ket noi SSH toi VM..." -ForegroundColor Cyan
$testSsh = Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=8 $VM_USER@$VM_IP `"echo SSH_OK`"" -Wait -NoNewWindow -PassThru
if ($testSsh.ExitCode -ne 0) {
    Write-Host "[LOI] Khong the ket noi SSH toi $VM_USER@$VM_IP. Vui long kiem tra IP va SSH Key!" -ForegroundColor Red
    exit 1
}
Write-Host "  >> Ket noi SSH thanh cong!" -ForegroundColor Green

# ------------------------------------------------------------
# 2. UPLOAD VA CHAY SETUP_VM.SH (TOI UU OS, SWAP 4GB, JAVA 17, MARIADB, BAO MAT)
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 2/6] Toi uu hoa toan dien Base OS, Swap 4GB, Cai Java 17, MariaDB & Tuong lua..." -ForegroundColor Cyan
$SetupScriptLocal = Join-Path $ProjectRoot "scripts\remote-vm\setup_vm.sh"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$SetupScriptLocal`" $VM_USER@$VM_IP`:/tmp/setup_vm.sh" -Wait -NoNewWindow | Out-Null
$resSetup = Start-Process -FilePath "ssh" -ArgumentList "-t -i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"sudo sed -i 's/\r$//' /tmp/setup_vm.sh && sudo bash /tmp/setup_vm.sh && rm -f /tmp/setup_vm.sh`"" -Wait -NoNewWindow -PassThru
if ($resSetup.ExitCode -ne 0) {
    Write-Host "[LOI] Cai dat setup_vm.sh tren VM that bai!" -ForegroundColor Red
    exit 1
}
Write-Host "  >> Toi uu Base OS, he thong & database thanh cong!" -ForegroundColor Green

# ------------------------------------------------------------
# 3. DONG GOI (KHONG NEN) VA UPLOAD THU MUC DATA/
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 3/6] Dang dong goi va upload thu muc Data/ (Hinh anh, Map, Item, Mob)..." -ForegroundColor Cyan
$DataTar = Join-Path $ProjectRoot "Data_upload.tar"
$OldDataTarGz = Join-Path $ProjectRoot "Data_upload.tar.gz"

if (Test-Path $DataTar) { Remove-Item -Force $DataTar }
if (Test-Path $OldDataTarGz) { Remove-Item -Force $OldDataTarGz }

Write-Host "  >> [1/3] Dang dong goi thu muc Data/ bang tar (Khong nen - toc do tuc thi)..." -ForegroundColor Gray
cmd.exe /c "tar -cf `"$DataTar`" -C `"$ProjectRoot`" Data"

# Dam bao thu muc /home/ubuntu/nso-server ton tai tren VM truoc khi upload
Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 $VM_USER@$VM_IP `"mkdir -p /home/ubuntu/nso-server`"" -Wait -NoNewWindow | Out-Null

$tarSizeMB = [math]::Round((Get-Item $DataTar).Length / 1MB, 2)
Write-Host "  >> [2/3] Dang upload Data ($tarSizeMB MB) sang VM qua SSH..." -ForegroundColor Gray
$resScpData = Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=60 `"$DataTar`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/Data.tar" -Wait -NoNewWindow -PassThru

Remove-Item -Force $DataTar -ErrorAction SilentlyContinue

if ($resScpData.ExitCode -ne 0) {
    Write-Host "[LOI] Khong the upload Data.tar sang /home/ubuntu/nso-server/!" -ForegroundColor Red
    exit 1
}

Write-Host "  >> [3/3] Dang giai dong goi du lieu Data tren VM..." -ForegroundColor Gray
Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=20 $VM_USER@$VM_IP `"cd /home/ubuntu/nso-server && tar -xf Data.tar && rm -f Data.tar Data.tar.gz`"" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Upload va giai dong goi Data/ thanh cong tren VM!" -ForegroundColor Green

# ------------------------------------------------------------
# 4. NAP DATABASE SACH (INIT_NSO_CLEAN.SQL)
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 4/6] Dang nap co so du lieu game sach (database/init_nso_clean.sql)..." -ForegroundColor Cyan
$SqlCleanLocal = Join-Path $ProjectRoot "database\init_nso_clean.sql"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$SqlCleanLocal`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/init_nso_clean.sql" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"sudo mariadb nso_test < /home/ubuntu/nso-server/init_nso_clean.sql && rm -f /home/ubuntu/nso-server/init_nso_clean.sql`"" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Nap Database game sach 100% thanh cong tren MariaDB!" -ForegroundColor Green

# ------------------------------------------------------------
# 5. UPLOAD FILE CAU HINH THEO PROFILE (LOW_RESOURCE / STANDARD / AUTO)
# ------------------------------------------------------------
$ServerProfile = if ($Config["SERVER_PROFILE"]) { $Config["SERVER_PROFILE"].ToUpper() } else { "LOW_RESOURCE" }
Write-Host ""
Write-Host "[BUOC 5/6] Dang dong bo file cau hinh theo Profile [$ServerProfile]..." -ForegroundColor Cyan

$CfgProp = Join-Path $ProjectRoot "config\config.properties.low"
$MyProp = Join-Path $ProjectRoot "config\mysql.properties.low"
$ServiceFile = Join-Path $ProjectRoot "scripts\remote-vm\nso-server.service.low"

if ($ServerProfile -eq "STANDARD") {
    $CfgProp = Join-Path $ProjectRoot "config\config.properties.standard"
    $MyProp = Join-Path $ProjectRoot "config\mysql.properties.standard"
    $ServiceFile = Join-Path $ProjectRoot "scripts\remote-vm\nso-server.service.standard"
} elseif ($ServerProfile -eq "AUTO") {
    $CfgProp = Join-Path $ProjectRoot "config\config.properties.prod"
    $MyProp = Join-Path $ProjectRoot "config\mysql.properties.prod"
    $ServiceFile = Join-Path $ProjectRoot "scripts\remote-vm\nso-server.service"
}

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 `"$CfgProp`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/config.properties" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 `"$MyProp`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/mysql.properties" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Cau hinh Profile [$ServerProfile] da duoc thiet lap tren VM!" -ForegroundColor Green

# ------------------------------------------------------------
# 6. DANG KY SYSTEMD SERVICE (GAME + API BRIDGE) VA AUTO BACKUP CRONJOB
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 6/6] Dang dang ky nso-server.service (Profile: $ServerProfile), nso-bridge.service va cronjob sao luu..." -ForegroundColor Cyan
$BridgeService = Join-Path $ProjectRoot "scripts\remote-vm\nso-bridge.service"
$BridgeScript = Join-Path $ProjectRoot "scripts\remote-vm\nso_bridge.js"
$AutoBackup = Join-Path $ProjectRoot "scripts\remote-vm\auto_backup_db.sh"
$CheckStatus = Join-Path $ProjectRoot "scripts\remote-vm\check_status.sh"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 `"$ServiceFile`" $VM_USER@$VM_IP`:/tmp/nso-server.service" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 `"$BridgeService`" `"$BridgeScript`" `"$AutoBackup`" `"$CheckStatus`" $VM_USER@$VM_IP`:/tmp/" -Wait -NoNewWindow | Out-Null

$setupSystemCmd = "sudo sed -i 's/\r$//' /tmp/nso-server.service /tmp/nso-bridge.service /tmp/nso_bridge.js /tmp/auto_backup_db.sh /tmp/check_status.sh 2>/dev/null || true && " +
                  "sudo mv -f /tmp/nso-server.service /etc/systemd/system/nso-server.service && " +
                  "sudo mv -f /tmp/nso-bridge.service /etc/systemd/system/nso-bridge.service && " +
                  "mv -f /tmp/nso_bridge.js /home/ubuntu/nso-server/nso_bridge.js && " +
                  "mv -f /tmp/auto_backup_db.sh /home/ubuntu/nso-server/auto_backup_db.sh && " +
                  "mv -f /tmp/check_status.sh /home/ubuntu/check_status.sh && " +
                  "chmod +x /home/ubuntu/nso-server/nso_bridge.js /home/ubuntu/nso-server/auto_backup_db.sh /home/ubuntu/check_status.sh && " +
                  "sudo systemctl daemon-reload && " +
                  "sudo systemctl enable nso-server.service nso-bridge.service && " +
                  "sudo systemctl restart nso-bridge.service && " +
                  "mkdir -p /home/ubuntu/nso-server/logs && " +
                  "(crontab -l 2>/dev/null | grep -v 'auto_backup_db.sh'; echo '0 */12 * * * /home/ubuntu/nso-server/auto_backup_db.sh >/dev/null 2>&1') | crontab -"

Start-Process -FilePath "ssh" -ArgumentList "-t -i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=10 $VM_USER@$VM_IP `"$setupSystemCmd`"" -Wait -NoNewWindow | Out-Null

Write-Host ""
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "   HOAN TAT THIET LAP MAY CHU CLOUD VM MOI TINH THANH CONG 100%!               " -ForegroundColor Green
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "  - Moi truong Java 17 + MariaDB + Swap 4GB + Firewall da san sang." -ForegroundColor Yellow
Write-Host "  - Du lieu Game (Data/) va Database sach (nso_test) da duoc khoi tao." -ForegroundColor Yellow
Write-Host "  - Dịch vu HTTP API Bridge (Port 8020) da bat -> Port 3306 duoc dong kin." -ForegroundColor Yellow
Write-Host "  - Dich vu nso-server.service da duoc dang ky tu bat khi khoi dong VM." -ForegroundColor Yellow
Write-Host ""
Write-Host ">> Bay gio ban chi can chon: [1] 1-Click Build & Deploy len VM de bat server!" -ForegroundColor Cyan
Write-Host "===============================================================================" -ForegroundColor Green
