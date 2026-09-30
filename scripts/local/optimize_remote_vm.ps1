# ===============================================================================
# SCRIPT TOI UU HOA BASE OS & BAO MAT CLOUD VM CHUYEN DUNG CHO NSO GAME SERVER
# Chay tu dong: Tao Swap, Kernel Sysctl, De-bloat Services, Rate-limit Firewall, Fail2ban
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
Write-Host "     TOI UU HOA BASE OS VA TANG CUONG BAO MAT CHO CLOUD VM (NSO SERVER)       " -ForegroundColor Yellow
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "  >> May chu VM : $VM_USER@$VM_IP" -ForegroundColor White
Write-Host "  >> SSH Key    : $KeyPath" -ForegroundColor White
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

if (!(Test-Path $KeyPath)) {
    Write-Host "[LOI] Khong tim thay SSH Key tai: $KeyPath" -ForegroundColor Red
    exit 1
}

cmd.exe /c "icacls `"$KeyPath`" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1"

Write-Host "[1/3] Kiem tra ket noi SSH toi VM..." -ForegroundColor Cyan
$testSsh = Start-Process -FilePath "ssh" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no -o ConnectTimeout=8 $VM_USER@$VM_IP `"echo SSH_OK`"" -Wait -NoNewWindow -PassThru
if ($testSsh.ExitCode -ne 0) {
    Write-Host "[LOI] Khong the ket noi SSH toi $VM_USER@$VM_IP. Vui long kiem tra IP va SSH Key!" -ForegroundColor Red
    exit 1
}

Write-Host "[2/3] Upload script toi uu hoa OS & Bao mat len VM..." -ForegroundColor Cyan
$OptimizeScriptLocal = Join-Path $ProjectRoot "scripts\remote-vm\optimize_os.sh"

# Upload and execute optimize script
Start-Process -FilePath "scp" -ArgumentList "-i `"$KeyPath`" -o StrictHostKeyChecking=no `"$OptimizeScriptLocal`" $VM_USER@$VM_IP`:/tmp/optimize_os.sh" -Wait -NoNewWindow | Out-Null

Write-Host "[3/3] Dang thuc thi quy trinh toi uu hoa OS & Kernel tren VM..." -ForegroundColor Cyan
$resOpt = Start-Process -FilePath "ssh" -ArgumentList "-t -i `"$KeyPath`" -o StrictHostKeyChecking=no $VM_USER@$VM_IP `"sudo sed -i 's/\r$//' /tmp/optimize_os.sh && sudo bash /tmp/optimize_os.sh && rm -f /tmp/optimize_os.sh`"" -Wait -NoNewWindow -PassThru

if ($resOpt.ExitCode -eq 0) {
    Write-Host ""
    Write-Host "===============================================================================" -ForegroundColor Green
    Write-Host "   TOI UU HOA TOAN DIEN BASE OS & KERNEL CHO VM THANH CONG 100%!               " -ForegroundColor Green
    Write-Host "===============================================================================" -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "[CANH BAO] Qua trinh toi uu ket thuc voi ma loi: $($resOpt.ExitCode)" -ForegroundColor Yellow
}
