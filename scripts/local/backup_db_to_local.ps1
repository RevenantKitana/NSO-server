# ==========================================================
# MANUAL DB BACKUP TO LOCAL PC VIA SSH
# Independent of auto-backup retention
# ==========================================================

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

if (!(Test-Path $KeyPath)) {
    Write-Host "[LOI] Khong tim thay file SSH Key tai: $KeyPath" -ForegroundColor Red
    exit 1
}

$LocalBackupDir = Join-Path $ProjectRoot "backups"
if (!(Test-Path $LocalBackupDir)) {
    New-Item -ItemType Directory -Path $LocalBackupDir -Force | Out-Null
}

$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$LocalFileName = "manual_db_backup_$Timestamp.sql.gz"
$LocalFilePath = Join-Path $LocalBackupDir $LocalFileName
$RemoteTempPath = "/tmp/manual_db_backup_$Timestamp.sql.gz"

Write-Host "[1/3] Dang tao ban dump co so du lieu tren VM ($VM_IP) qua SSH..." -ForegroundColor Cyan

$dumpCmd = "sudo mariadb-dump --single-transaction --routines --triggers nso_test | gzip > $RemoteTempPath && sudo chmod 644 $RemoteTempPath"
$sshProcess = Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"$dumpCmd`"" -Wait -NoNewWindow -PassThru

if ($sshProcess.ExitCode -ne 0) {
    Write-Host "LOI: Khong the tao ban dump tren VM!" -ForegroundColor Red
    exit 1
}

Write-Host "[2/3] Dang tai file backup ve may tinh ca nhan..." -ForegroundColor Cyan
$scpProcess = Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP`:$RemoteTempPath `"$LocalFilePath`"" -Wait -NoNewWindow -PassThru

if ($scpProcess.ExitCode -ne 0) {
    Write-Host "LOI: Khong the tai file backup ve local!" -ForegroundColor Red
    exit 1
}

Write-Host "[3/3] Dang don dep file tam tren VM..." -ForegroundColor Cyan
$cleanCmd = "sudo rm -f $RemoteTempPath"
Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"$cleanCmd`"" -Wait -NoNewWindow | Out-Null

$FileInfo = Get-Item $LocalFilePath
$SizeKB = [math]::Round($FileInfo.Length / 1KB, 2)

Write-Host ""
Write-Host "============================================================" -ForegroundColor Green
Write-Host "  SAO LUU DATABASE VE MAY TINH THANH CONG!" -ForegroundColor Green
Write-Host "============================================================" -ForegroundColor Green
Write-Host "  File luu tai : $LocalFilePath" -ForegroundColor Yellow
Write-Host "  Dung luong   : $SizeKB KB" -ForegroundColor Yellow
Write-Host "  Thoi gian    : $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')" -ForegroundColor Yellow
Write-Host "============================================================" -ForegroundColor Green
