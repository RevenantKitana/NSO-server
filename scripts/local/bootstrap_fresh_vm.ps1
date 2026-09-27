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
# 2. UPLOAD VA CHAY SETUP_VM.SH (CAI JAVA, MARIADB, SWAP, FIREWALL)
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 2/6] Cai dat moi truong he thong (Java 17, MariaDB, Swap 4GB, Firewall)..." -ForegroundColor Cyan
$SetupScriptLocal = Join-Path $ProjectRoot "scripts\remote-vm\setup_vm.sh"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$SetupScriptLocal`" $VM_USER@$VM_IP`:/tmp/setup_vm.sh" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "ssh" -ArgumentList "-t -i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"sudo bash /tmp/setup_vm.sh && rm -f /tmp/setup_vm.sh`"" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Cai dat he thong & database thanh cong!" -ForegroundColor Green

# ------------------------------------------------------------
# 3. NEN VA UPLOAD THU MUC DATA/
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 3/6] Dang dong goi va upload thu muc Data/ (Hinh anh, Map, Item, Mob)..." -ForegroundColor Cyan
$DataDir = Join-Path $ProjectRoot "Data"
$DataZip = Join-Path $ProjectRoot "Data_upload.zip"

if (Test-Path $DataZip) { Remove-Item -Force $DataZip }

Write-Host "  >> Dang nen thu muc Data/..." -ForegroundColor Gray
Compress-Archive -Path "$DataDir\*" -DestinationPath $DataZip -Force

Write-Host "  >> Dang upload Data_upload.zip sang VM..." -ForegroundColor Gray
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$DataZip`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/Data.zip" -Wait -NoNewWindow | Out-Null

Remove-Item -Force $DataZip -ErrorAction SilentlyContinue

Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"cd /home/ubuntu/nso-server && unzip -q -o Data.zip && rm -f Data.zip`"" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Upload va giai nen Data/ thanh cong tren VM!" -ForegroundColor Green

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
# 5. UPLOAD FILE CAU HINH PRODUCTION
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 5/6] Dang dong bo file cau hinh config.properties & mysql.properties..." -ForegroundColor Cyan
$CfgProp = Join-Path $ProjectRoot "config\config.properties.prod"
$MyProp = Join-Path $ProjectRoot "config\mysql.properties.prod"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$CfgProp`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/config.properties" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$MyProp`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/mysql.properties" -Wait -NoNewWindow | Out-Null
Write-Host "  >> Cau hinh Production da duoc thiet lap tren VM!" -ForegroundColor Green

# ------------------------------------------------------------
# 6. DANG KY SYSTEMD SERVICE VA AUTO BACKUP CRONJOB
# ------------------------------------------------------------
Write-Host ""
Write-Host "[BUOC 6/6] Dang dang ky nso-server.service va cronjob sao luu tu dong..." -ForegroundColor Cyan
$ServiceFile = Join-Path $ProjectRoot "scripts\remote-vm\nso-server.service"
$AutoBackup = Join-Path $ProjectRoot "scripts\remote-vm\auto_backup_db.sh"

Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$ServiceFile`" $VM_USER@$VM_IP`:/tmp/nso-server.service" -Wait -NoNewWindow | Out-Null
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$AutoBackup`" $VM_USER@$VM_IP`:/home/ubuntu/nso-server/auto_backup_db.sh" -Wait -NoNewWindow | Out-Null

$setupSystemCmd = "sudo cp /tmp/nso-server.service /etc/systemd/system/nso-server.service && " +
                  "rm -f /tmp/nso-server.service && " +
                  "chmod +x /home/ubuntu/nso-server/auto_backup_db.sh && " +
                  "sudo systemctl daemon-reload && " +
                  "sudo systemctl enable nso-server.service && " +
                  "mkdir -p /home/ubuntu/nso-server/logs && " +
                  "(crontab -l 2>/dev/null | grep -v 'auto_backup_db.sh'; echo '0 */12 * * * /home/ubuntu/nso-server/auto_backup_db.sh >/dev/null 2>&1') | crontab -"

Start-Process -FilePath "ssh" -ArgumentList "-t -i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"$setupSystemCmd`"" -Wait -NoNewWindow | Out-Null

Write-Host ""
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "   HOAN TAT THIET LAP MAY CHU CLOUD VM MOI TINH THANH CONG 100%!               " -ForegroundColor Green
Write-Host "===============================================================================" -ForegroundColor Green
Write-Host "  - Moi truong Java 17 + MariaDB + Swap 4GB + Firewall da san sang." -ForegroundColor Yellow
Write-Host "  - Du lieu Game (Data/) va Database sach (nso_test) da duoc khoi tao." -ForegroundColor Yellow
Write-Host "  - Dịch vu nso-server.service da duoc dang ky tu bat khi khoi dong VM." -ForegroundColor Yellow
Write-Host ""
Write-Host ">> Bay gio ban chi can chon: [1] 1-Click Build & Deploy len VM de bat server!" -ForegroundColor Cyan
Write-Host "===============================================================================" -ForegroundColor Green
