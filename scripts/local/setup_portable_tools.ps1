# ===============================================================================
# SCRIPT TU DONG TAI VA THIET LAP JDK 17 VA MAVEN PORTABLE (CUC BO)
# 100% Cuc bo - Khong cai vao Windows - Khong sua System PATH
# ===============================================================================

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$ProjectRoot = (Get-Item "$PSScriptRoot\..\..").FullName
$ToolsDir = "$ProjectRoot\tools"
$JdkDir = "$ToolsDir\jdk"
$MavenDir = "$ToolsDir\maven"

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   THIET LAP MOI TRUONG BUILD JAVA 17 VA MAVEN PORTABLE     " -ForegroundColor Yellow
Write-Host "   (100% Portable cuc bo - Khong dung vao Windows PATH)     " -ForegroundColor Gray
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host ">> Thu muc cong cu: $ToolsDir" -ForegroundColor White

if (-not (Test-Path $ToolsDir)) {
    New-Item -ItemType Directory -Path $ToolsDir -Force | Out-Null
}

# ------------------------------------------------------------
# 1. TAI VA GIAI NEN OPENJDK 17 PORTABLE
# ------------------------------------------------------------
if (-not (Test-Path "$JdkDir\bin\javac.exe")) {
    Write-Host ""
    Write-Host "[1/2] Dang tai OpenJDK 17 Portable (Eclipse Temurin x64)..." -ForegroundColor Cyan
    $JdkZip = "$ToolsDir\openjdk17.zip"
    $JdkUrl = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.12%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.12_7.zip"
    
    try {
        Write-Host "   >> URL: $JdkUrl" -ForegroundColor Gray
        Invoke-WebRequest -Uri $JdkUrl -OutFile $JdkZip -UseBasicParsing
        Write-Host "   >> Tai thanh cong! Dang giai nen JDK 17..." -ForegroundColor Green
        
        $TempJdkExtract = "$ToolsDir\temp_jdk"
        if (Test-Path $TempJdkExtract) { Remove-Item -Recurse -Force $TempJdkExtract }
        Expand-Archive -Path $JdkZip -DestinationPath $TempJdkExtract -Force
        
        $InnerJdkFolder = Get-ChildItem -Path $TempJdkExtract -Directory | Select-Object -First 1
        if (Test-Path $JdkDir) { Remove-Item -Recurse -Force $JdkDir }
        Move-Item -Path $InnerJdkFolder.FullName -Destination $JdkDir
        
        Remove-Item -Recurse -Force $TempJdkExtract -ErrorAction SilentlyContinue
        Remove-Item -Force $JdkZip -ErrorAction SilentlyContinue
        Write-Host "   >> Cai dat JDK 17 Portable thanh cong!" -ForegroundColor Green
    }
    catch {
        Write-Host "   [LOI] Khong the tai JDK 17 tu Adoptium: $_" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "[1/2] JDK 17 Portable da san sang tai: $JdkDir" -ForegroundColor Green
}

# ------------------------------------------------------------
# 2. TAI VA GIAI NEN APACHE MAVEN PORTABLE
# ------------------------------------------------------------
if (-not (Test-Path "$MavenDir\bin\mvn.cmd")) {
    Write-Host ""
    Write-Host "[2/2] Dang tai Apache Maven Portable..." -ForegroundColor Cyan
    $MvnZip = "$ToolsDir\maven.zip"
    $MvnUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip"
    
    try {
        Write-Host "   >> URL: $MvnUrl" -ForegroundColor Gray
        Invoke-WebRequest -Uri $MvnUrl -OutFile $MvnZip -UseBasicParsing
        Write-Host "   >> Tai thanh cong! Dang giai nen Apache Maven..." -ForegroundColor Green
        
        $TempMvnExtract = "$ToolsDir\temp_mvn"
        if (Test-Path $TempMvnExtract) { Remove-Item -Recurse -Force $TempMvnExtract }
        Expand-Archive -Path $MvnZip -DestinationPath $TempMvnExtract -Force
        
        $InnerMvnFolder = Get-ChildItem -Path $TempMvnExtract -Directory | Select-Object -First 1
        if (Test-Path $MavenDir) { Remove-Item -Recurse -Force $MavenDir }
        Move-Item -Path $InnerMvnFolder.FullName -Destination $MavenDir
        
        Remove-Item -Recurse -Force $TempMvnExtract -ErrorAction SilentlyContinue
        Remove-Item -Force $MvnZip -ErrorAction SilentlyContinue
        Write-Host "   >> Cai dat Apache Maven Portable thanh cong!" -ForegroundColor Green
    }
    catch {
        Write-Host "   [LOI] Khong the tai Maven tu Apache Archive: $_" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "[2/2] Apache Maven Portable da san sang tai: $MavenDir" -ForegroundColor Green
}

Write-Host ""
Write-Host "============================================================" -ForegroundColor Green
Write-Host "   HOAN TAT THIET LAP MOI TRUONG BUILD CUC BO (PORTABLE)!   " -ForegroundColor Green
Write-Host "============================================================" -ForegroundColor Green
