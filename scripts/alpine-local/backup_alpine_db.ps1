# ==========================================================
# MANUAL DB BACKUP FROM ALPINE LINUX VM TO LOCAL PC VIA SSH
# ==========================================================

. "$PSScriptRoot\ssh_alpine_helper.ps1"

if (!(Ensure-AlpineSshKey)) {
    Write-Host "[LOI] Khong the ket noi SSH toi Alpine VM ($VM_USER@$VM_IP)!" -ForegroundColor Red
    exit 1
}

$LocalBackupDir = Join-Path $ProjectRoot "backups"
if (!(Test-Path $LocalBackupDir)) {
    New-Item -ItemType Directory -Path $LocalBackupDir -Force | Out-Null
}

$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$LocalFileName = "alpine_db_backup_$Timestamp.sql.gz"
$LocalFilePath = Join-Path $LocalBackupDir $LocalFileName
$RemoteTempPath = "/tmp/alpine_db_backup_$Timestamp.sql.gz"

Write-Host "[1/3] Dang tao ban dump co so du lieu tren Alpine VM ($VM_IP)..." -ForegroundColor Cyan

$dumpCmd = "mariadb-dump -u root --single-transaction --routines --triggers nso_test | gzip > $RemoteTempPath && chmod 644 $RemoteTempPath"
$resDump = Invoke-AlpineSsh -Command $dumpCmd

if ($resDump -ne 0) {
    Write-Host "[LOI] Khong the tao ban dump tren Alpine VM!" -ForegroundColor Red
    exit 1
}

Write-Host "[2/3] Dang tai file backup ve may tinh ca nhan..." -ForegroundColor Cyan
$resScp = Invoke-AlpineScp -Source "$($VM_USER)@$($VM_IP):$RemoteTempPath" -Destination "`"$LocalFilePath`""

if ($resScp -ne 0) {
    Write-Host "[LOI] Khong the tai file backup ve local!" -ForegroundColor Red
    exit 1
}

Write-Host "[3/3] Dang don dep file tam tren Alpine VM..." -ForegroundColor Cyan
Invoke-AlpineSsh -Command "rm -f $RemoteTempPath" | Out-Null

$FileInfo = Get-Item $LocalFilePath
$SizeKB = [math]::Round($FileInfo.Length / 1KB, 2)

Write-Host ""
Write-Host "============================================================" -ForegroundColor Green
Write-Host "  SAO LUU DATABASE TU ALPINE VM VE MAY TINH THANH CONG!" -ForegroundColor Green
Write-Host "============================================================" -ForegroundColor Green
Write-Host "  File luu tai : $LocalFilePath" -ForegroundColor Yellow
Write-Host "  Dung luong   : $SizeKB KB" -ForegroundColor Yellow
Write-Host "  Thoi gian    : $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')" -ForegroundColor Yellow
Write-Host "============================================================" -ForegroundColor Green

