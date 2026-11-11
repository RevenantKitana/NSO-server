# ===============================================================================
# SCRIPT TAO TAI KHOAN GAME TRUC TIEP TREN ALPINE LINUX VM (BCRYPT HASH)
# ===============================================================================

. "$PSScriptRoot\ssh_alpine_helper.ps1"

Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "         TAO TAI KHOAN GAME NSO TRUC TIEP TREN MAY CHU ALPINE LINUX            " -ForegroundColor Yellow
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "  >> May chu Alpine : $VM_USER@$VM_IP" -ForegroundColor White
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

if (!(Ensure-AlpineSshKey)) {
    Write-Host "[LOI] Khong the ket noi SSH toi Alpine VM ($VM_USER@$VM_IP)!" -ForegroundColor Red
    exit 1
}

$Username = (Read-Host ">> Nhap ten tai khoan (Username)").Trim().ToLower()
if (!$Username) {
    Write-Host "[!] Ten tai khoan khong duoc de trong!" -ForegroundColor Red
    exit 1
}

$Password = (Read-Host ">> Nhap mat khau (Bam Enter de dung mac dinh '1')").Trim()
if (!$Password) { $Password = "1" }

$LuongStr = (Read-Host ">> Nhap so Luong khoi dau (Bam Enter de dung mac dinh '100000')").Trim()
$Luong = if ($LuongStr -match '^\d+$') { [int]$LuongStr } else { 100000 }

$IsAdmin = (Read-Host ">> Cap quyen Admin trong game? [y/N]").Trim()
$Role = if ($IsAdmin -match '^[yY]$') { 1 } else { 0 }
$LevelStr = if ($Role -eq 1) { "admin" } else { "member" }

Write-Host ""
Write-Host "[*] Dang ma hoa mat khau BCrypt..." -ForegroundColor Cyan

$BcryptHash = $null
$BcryptJsPath = Join-Path $ProjectRoot "web\node_modules\bcryptjs"
if ((Get-Command node -ErrorAction SilentlyContinue) -and (Test-Path $BcryptJsPath)) {
    $escapedPass = $Password.Replace('\', '\\').Replace("'", "\'")
    $BcryptHash = (node -e "const bcrypt = require('./web/node_modules/bcryptjs'); console.log(bcrypt.hashSync('$escapedPass', 10));" 2>$null).Trim()
}

if (!$BcryptHash) {
    Write-Host "[LOI] Khong the ma hoa BCrypt (can Node.js va web/node_modules/bcryptjs)." -ForegroundColor Red
    exit 1
}

Write-Host "[*] Dang khoi tao tai khoan '$Username' tren MariaDB (Alpine VM)..." -ForegroundColor Cyan

$sql = @"
INSERT INTO users (username, password, activated, balance, luong, tongnap, point_vip, role, status, online, nap, tanthu, level, created_at, updated_at)
VALUES ('$Username', '$BcryptHash', 1, 0, $Luong, 0, 0, $Role, 0, 0, 0, 1, '$LevelStr', NOW(), NOW())
ON DUPLICATE KEY UPDATE password='$BcryptHash', activated=1, luong=$Luong, role=$Role, status=0, level='$LevelStr', updated_at=NOW();
SELECT id, username, activated, luong, role, status, level, created_at FROM users WHERE username='$Username';
"@

$sql | ssh -i "$KeyPath" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR "$VM_USER@$VM_IP" "mariadb -u root nso_test --table"

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "===============================================================================" -ForegroundColor Green
    Write-Host "  TAO / CAP NHAT TAI KHOAN GAME THANH CONG TREN ALPINE LINUX VM!" -ForegroundColor Green
    Write-Host "===============================================================================" -ForegroundColor Green
    Write-Host "  - Tai khoan : $Username" -ForegroundColor Yellow
    Write-Host "  - Mat khau  : $Password" -ForegroundColor Yellow
    Write-Host "  - So Luong  : $Luong" -ForegroundColor Yellow
    Write-Host "  - Quyen han : $LevelStr (role=$Role)" -ForegroundColor Yellow
    Write-Host "===============================================================================" -ForegroundColor Green
} else {
    Write-Host "[LOI] Khong the ghi tai khoan vao MariaDB tren Alpine VM!" -ForegroundColor Red
    exit 1
}

