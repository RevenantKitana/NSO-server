# ===============================================================================
# SCRIPT TOI UU HOA BASE OS & BAO MAT CHO ALPINE LINUX VM (NSO SERVER)
# ===============================================================================

. "$PSScriptRoot\ssh_alpine_helper.ps1"

Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "    TOI UU HOA BASE OS VA TANG CUONG BAO MAT CHO ALPINE LINUX VM               " -ForegroundColor Yellow
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host "  >> May chu Alpine : $VM_USER@$VM_IP" -ForegroundColor White
Write-Host "  >> SSH Key        : $KeyPath" -ForegroundColor White
Write-Host "===============================================================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "[1/3] Kiem tra ket noi SSH toi Alpine VM..." -ForegroundColor Cyan
if (!(Ensure-AlpineSshKey)) {
    Write-Host "[LOI] Khong the ket noi SSH toi $VM_USER@$VM_IP!" -ForegroundColor Red
    exit 1
}

Write-Host "[2/3] Upload script toi uu hoa Alpine OS len VM..." -ForegroundColor Cyan
$OptimizeScriptLocal = Join-Path $ProjectRoot "scripts\alpine-vm\optimize_alpine_os.sh"
Invoke-AlpineScp -Source "`"$OptimizeScriptLocal`"" -Destination "$($VM_USER)@$($VM_IP):/tmp/optimize_alpine_os.sh" | Out-Null

Write-Host "[3/3] Dang thuc thi quy trinh toi uu hoa Alpine OS & Kernel..." -ForegroundColor Cyan
$resOpt = Invoke-AlpineSsh -Command "sed -i 's/\r$//' /tmp/optimize_alpine_os.sh && sh /tmp/optimize_alpine_os.sh && rm -f /tmp/optimize_alpine_os.sh" -Interactive

if ($resOpt -eq 0) {
    Write-Host ""
    Write-Host "===============================================================================" -ForegroundColor Green
    Write-Host "   TOI UU HOA TOAN DIEN ALPINE LINUX OS & KERNEL THANH CONG 100%!              " -ForegroundColor Green
    Write-Host "===============================================================================" -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "[CANH BAO] Qua trinh toi uu ket thuc voi ma loi: $resOpt" -ForegroundColor Yellow
}

