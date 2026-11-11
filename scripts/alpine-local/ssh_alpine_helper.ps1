# ===============================================================================
# SHARED SSH & CONFIG HELPER FOR ALPINE LINUX VM TOOLKIT
# Supports automatic SSH Key generation & password-to-key provisioning (VM_PASS)
# ===============================================================================

$Global:ProjectRoot = (Get-Item "$PSScriptRoot\..\..").FullName
$Global:AlpineConfigFile = Join-Path $Global:ProjectRoot "config\alpine_config.ini"

if (!(Test-Path $Global:AlpineConfigFile)) {
    Write-Host "[LOI] Khong tim thay file cau hinh Alpine: $Global:AlpineConfigFile" -ForegroundColor Red
    exit 1
}

$Global:AlpineConfig = @{}
Get-Content $Global:AlpineConfigFile | ForEach-Object {
    $line = $_.Trim()
    if ($line -and -not $line.StartsWith("#")) {
        $parts = $line.Split("=", 2)
        if ($parts.Length -eq 2) {
            $Global:AlpineConfig[$parts[0].Trim()] = $parts[1].Trim()
        }
    }
}

$Global:VM_IP   = $Global:AlpineConfig["VM_IP"]
$Global:VM_USER = if ($Global:AlpineConfig["VM_USER"]) { $Global:AlpineConfig["VM_USER"] } else { "root" }
$Global:VM_PASS = $Global:AlpineConfig["VM_PASS"]
$Global:VM_PORT = if ($Global:AlpineConfig["VM_PORT"]) { $Global:AlpineConfig["VM_PORT"] } else { "14444" }
$KeyRel         = if ($Global:AlpineConfig["SSH_KEY"]) { $Global:AlpineConfig["SSH_KEY"] } else { "config/alpine_vm.key" }
$Global:KeyPath = [System.IO.Path]::GetFullPath((Join-Path $Global:ProjectRoot $KeyRel))
$Global:ServerProfile = if ($Global:AlpineConfig["SERVER_PROFILE"]) { $Global:AlpineConfig["SERVER_PROFILE"].ToUpper() } else { "LOW_RESOURCE" }

function Ensure-AlpineSshKey {
    if (!(Test-Path $Global:KeyPath)) {
        Write-Host "  [*] Chua co SSH Key cho Alpine VM. Dang tu dong khoi tao $KeyRel..." -ForegroundColor Yellow
        ssh-keygen -t ed25519 -f "$Global:KeyPath" -N '""' -C "nso-alpine-vm" | Out-Null
    }
    cmd.exe /c "icacls `"$Global:KeyPath`" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1"

    # Test key authentication first
    $testKey = Start-Process -FilePath "ssh" -ArgumentList "-i `"$Global:KeyPath`" -o BatchMode=yes -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o ConnectTimeout=6 $($Global:VM_USER)@$($Global:VM_IP) `"echo KEY_OK`"" -Wait -NoNewWindow -PassThru -RedirectStandardOutput "$env:TEMP\alpine_ssh_out.txt" -RedirectStandardError "$env:TEMP\alpine_ssh_err.txt"
    if ($testKey.ExitCode -eq 0) {
        return $true
    }

    # If key auth failed and VM_PASS is available, automatically push public key via SSH_ASKPASS
    if ($Global:VM_PASS) {
        Write-Host "  [*] Dang tu dong nap SSH Key len Alpine VM ($($Global:VM_USER)@$($Global:VM_IP)) bang mat khau..." -ForegroundColor Yellow
        $pubKeyPath = "$($Global:KeyPath).pub"
        if (!(Test-Path $pubKeyPath)) {
            ssh-keygen -y -f "$Global:KeyPath" | Out-File -FilePath $pubKeyPath -Encoding ascii
        }
        $pubKey = (Get-Content $pubKeyPath -Raw).Trim()
        $askpassBat = Join-Path $env:TEMP "alpine_askpass_$PID.bat"
        "@echo $($Global:VM_PASS)" | Out-File -FilePath $askpassBat -Encoding ascii

        $prevAsk = $env:SSH_ASKPASS
        $prevReq = $env:SSH_ASKPASS_REQUIRE
        $prevDisp = $env:DISPLAY
        $env:SSH_ASKPASS = $askpassBat
        $env:SSH_ASKPASS_REQUIRE = "force"
        $env:DISPLAY = "1"

        $authCmd = "mkdir -p ~/.ssh && chmod 700 ~/.ssh && (grep -qF '$pubKey' ~/.ssh/authorized_keys 2>/dev/null || echo '$pubKey' >> ~/.ssh/authorized_keys) && chmod 600 ~/.ssh/authorized_keys"
        $pushProc = Start-Process -FilePath "ssh" -ArgumentList "-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o ConnectTimeout=8 $($Global:VM_USER)@$($Global:VM_IP) `"$authCmd`"" -Wait -NoNewWindow -PassThru

        $env:SSH_ASKPASS = $prevAsk
        $env:SSH_ASKPASS_REQUIRE = $prevReq
        $env:DISPLAY = $prevDisp
        Remove-Item $askpassBat -Force -ErrorAction SilentlyContinue

        if ($pushProc.ExitCode -eq 0) {
            Write-Host "  >> Da dong bo SSH Key len Alpine VM thanh cong!" -ForegroundColor Green
            return $true
        }
    }
    return $false
}

function Invoke-AlpineSsh {
    param(
        [Parameter(Mandatory=$true)][string]$Command,
        [int]$ConnectTimeout = 15,
        [switch]$Interactive
    )
    $ttyFlag = if ($Interactive) { "-t" } else { "" }
    $argStr = "$ttyFlag -i `"$Global:KeyPath`" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR -o ConnectTimeout=$ConnectTimeout $($Global:VM_USER)@$($Global:VM_IP) `"$Command`""
    $proc = Start-Process -FilePath "ssh" -ArgumentList $argStr -Wait -NoNewWindow -PassThru
    return $proc.ExitCode
}

function Invoke-AlpineScp {
    param(
        [Parameter(Mandatory=$true)][string]$Source,
        [Parameter(Mandatory=$true)][string]$Destination,
        [int]$ConnectTimeout = 30
    )
    $argStr = "-i `"$Global:KeyPath`" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR -o ConnectTimeout=$ConnectTimeout $Source $Destination"
    $proc = Start-Process -FilePath "scp" -ArgumentList $argStr -Wait -NoNewWindow -PassThru
    return $proc.ExitCode
}

