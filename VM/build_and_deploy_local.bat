@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

title NSO Server - Build Local va Deploy Len VM
color 0B

:: ============================================================
:: CONFIG
:: ============================================================
set "PROJECT_ROOT=%~dp0.."
pushd "%PROJECT_ROOT%"
set "PROJECT_ROOT=%CD%"
popd

set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"

set "LOCAL_JDK=%PROJECT_ROOT%\tools\jdk"
set "LOCAL_MAVEN=%PROJECT_ROOT%\tools\maven"

echo ===============================================================================
echo        QUY TRINH BUILD LOCAL VA TRIEN KHAI NHANH LEN VM CLOUD
echo ===============================================================================
echo   >> Thu muc du an: %PROJECT_ROOT%
echo   >> May chu dich  : %VM_USER%@%VM_IP%
echo ===============================================================================
echo.

:: ============================================================
:: 1. KIEM TRA MÔI TRUONG JAVA & MAVEN PORTABLE
:: ============================================================
if exist "%LOCAL_JDK%\bin\javac.exe" (
    if exist "%LOCAL_MAVEN%\bin\mvn.cmd" (
        echo [OK] Su dung JDK 17 & Maven Portable cuc bo trong thu muc tools/
        set "JAVA_HOME=%LOCAL_JDK%"
        set "PATH=%LOCAL_MAVEN%\bin;%LOCAL_JDK%\bin;%PATH%"
        goto START_BUILD
    )
)

:: Neu chua co ban portable, kiem tra xem may da cai maven/java chua
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Su dung Maven va Java co san tren he thong Windows.
    goto START_BUILD
)

echo [!] Chua tim thay JDK & Maven Portable trong thu muc tools/
echo >> Dang tu dong tai va thiet lap JDK 17 + Maven Portable (chi can tai 1 lan)...
call "%~dp0setup_portable_tools.bat"

if exist "%LOCAL_JDK%\bin\javac.exe" (
    if exist "%LOCAL_MAVEN%\bin\mvn.cmd" (
        set "JAVA_HOME=%LOCAL_JDK%"
        set "PATH=%LOCAL_MAVEN%\bin;%LOCAL_JDK%\bin;%PATH%"
        goto START_BUILD
    )
)

echo.
echo [LOI] Khong the thiet lap JDK/Maven Portable. Vui long kiem tra ket noi mang!
pause
exit /b 1

:START_BUILD
:: ============================================================
:: 2. BIEN DICH VA DONG GOI TREN MAY TINH CA NHAN (LOCAL)
:: ============================================================
echo.
echo ===============================================================================
echo  [BUOC 1/3] DANG BIEN DICH VA BUILD JAR TREN MAY TINH (LOCAL)...
echo ===============================================================================
cd /d "%PROJECT_ROOT%"

call mvn clean package -DskipTests

if not exist "%PROJECT_ROOT%\target\Nso-jar-with-dependencies.jar" (
    echo.
    echo ===============================================================================
    echo  [LOI] Build that bai! File target\Nso-jar-with-dependencies.jar khong ton tai.
    echo ===============================================================================
    pause
    exit /b 1
)

echo.
echo [OK] Build thanh cong file: target\Nso-jar-with-dependencies.jar

:: ============================================================
:: 3. SAO LUU BAN RUNTIME JAR CU TREN VM (SIEU TOC 0.1S)
:: ============================================================
echo.
echo ===============================================================================
echo  [BUOC 2/4] SAO LUU BAN RUNTIME JAR CU TREN VM PHONG RUI RO...
echo ===============================================================================

if not exist "%KEY_PATH%" (
    echo [LOI] Khong tim thay SSH Key tai: %KEY_PATH%
    pause
    exit /b 1
)

ssh -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "mkdir -p /home/ubuntu/nso-server/backups; if [ -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar ]; then cp -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar /home/ubuntu/nso-server/backups/Nso_backup_\$(date +%%Y%%m%%d_%%H%%M%%S).jar; echo '>> Da sao luu ban JAR cu thanh cong!'; ls -1t /home/ubuntu/nso-server/backups/Nso_backup_*.jar 2>/dev/null | tail -n +4 | xargs -r rm -f; else echo '>> Chua co file JAR cu tren VM (cai dat moi).'; fi"

:: ============================================================
:: 4. UPLOAD FILE JAR MOI LEN VM CLOUD (QUA SCP)
:: ============================================================
echo.
echo ===============================================================================
echo  [BUOC 3/4] DANG UPLOAD FILE JAR MOI LEN VM (%VM_IP%)...
echo ===============================================================================

scp -i "%KEY_PATH%" -o StrictHostKeyChecking=no -o ConnectTimeout=10 "%PROJECT_ROOT%\target\Nso-jar-with-dependencies.jar" %VM_USER%@%VM_IP%:/home/ubuntu/nso-server/Nso-jar-with-dependencies.jar

if errorlevel 1 (
    echo.
    echo [LOI] Upload file jar len VM that bai! Kiem tra ket noi mang hoac trang thai VM.
    pause
    exit /b 1
)

echo [OK] Upload file jar moi thanh cong len VM!

:: ============================================================
:: 5. KHOI DONG LAI GAME SERVER TREN VM (QUA SSH)
:: ============================================================
echo.
echo ===============================================================================
echo  [BUOC 4/4] DANG KHOI DONG LAI GAME SERVER TREN VM...
echo ===============================================================================

ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl restart nso-server.service && sleep 3 && sudo systemctl status nso-server.service --no-pager && echo '' && echo '=== CAC PORT DANG LANG NGHE ===' && sudo ss -tulnp | grep -E '14444|8020|3306'"

echo.
echo ===============================================================================
echo   HOAN TAT QUY TRINH BUILD & DEPLOY LEN VM THANH CONG!
echo ===============================================================================
echo   - Ban co the dung check_server_status.cmd de xem log va trang thai server.
echo ===============================================================================
echo.
pause
