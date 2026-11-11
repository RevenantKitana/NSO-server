@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion
title NSO Server - Alpine Linux VM Management Hub (OpenRC + jemalloc)
color 0A

set "ROOT_DIR=%~dp0"
if "%ROOT_DIR:~-1%"=="\" set "ROOT_DIR=%ROOT_DIR:~0,-1%"

:LOAD_CONFIG
set "VM_IP="
set "VM_USER="
set "VM_PASS="
set "VM_PORT="
set "SSH_KEY_REL="
set "WEB_PORT="
set "SERVER_PROFILE="

set "CONFIG_FILE=%ROOT_DIR%\config\alpine_config.ini"
if not exist "%CONFIG_FILE%" (
    cls
    echo ===============================================================================
    echo  [LOI NGHIEM TRONG] Khong tim thay file cau hinh Alpine tai:
    echo  %CONFIG_FILE%
    echo ===============================================================================
    echo  Vui long tao file config\alpine_config.ini truoc khi mo chuong trinh!
    echo ===============================================================================
    echo.
    pause
    exit /b 1
)

for /f "usebackq tokens=1,2 delims==" %%A in ("%CONFIG_FILE%") do (
    set "CFG_KEY=%%A"
    set "CFG_VAL=%%B"
    if not "!CFG_KEY:~0,1!"=="#" (
        if /i "!CFG_KEY!"=="VM_IP" set "VM_IP=!CFG_VAL!"
        if /i "!CFG_KEY!"=="VM_USER" set "VM_USER=!CFG_VAL!"
        if /i "!CFG_KEY!"=="VM_PASS" set "VM_PASS=!CFG_VAL!"
        if /i "!CFG_KEY!"=="VM_PORT" set "VM_PORT=!CFG_VAL!"
        if /i "!CFG_KEY!"=="SSH_KEY" set "SSH_KEY_REL=!CFG_VAL!"
        if /i "!CFG_KEY!"=="WEB_PORT" set "WEB_PORT=!CFG_VAL!"
        if /i "!CFG_KEY!"=="SERVER_PROFILE" set "SERVER_PROFILE=!CFG_VAL!"
    )
)

if "!VM_USER!"=="" set "VM_USER=root"
if "!VM_PORT!"=="" set "VM_PORT=14444"
if "!SSH_KEY_REL!"=="" set "SSH_KEY_REL=config/alpine_vm.key"
if "!SERVER_PROFILE!"=="" set "SERVER_PROFILE=LOW_RESOURCE"

set "PROFILE_LABEL=[LOW_RESOURCE - Alpine 512M-2GB RAM (jemalloc + SerialGC, Heap 320M)]"
if /i "!SERVER_PROFILE!"=="STANDARD" set "PROFILE_LABEL=[STANDARD - Alpine 4GB+ RAM (jemalloc + G1GC, Heap 2048M)]"
if /i "!SERVER_PROFILE!"=="AUTO" set "PROFILE_LABEL=[AUTO - Tu dong theo RAM thuc te tren Alpine VM]"

set "KEY_PATH=%ROOT_DIR%\!SSH_KEY_REL!"

if exist "!KEY_PATH!" (
    icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1
)

:MAIN_MENU
cls

set "STATUS_JDK=[CHUA CO - Hay chon 9 de tai]"
if exist "%ROOT_DIR%\tools\jdk\bin\javac.exe" (
    set "STATUS_JDK=[OK - Da cai dat Portable]"
) else (
    where javac >nul 2>&1
    if !errorlevel! equ 0 set "STATUS_JDK=[OK - San co tren Windows]"
)

set "STATUS_MVN=[CHUA CO - Hay chon 9 de tai]"
if exist "%ROOT_DIR%\tools\maven\bin\mvn.cmd" (
    set "STATUS_MVN=[OK - Da cai dat Portable]"
) else (
    where mvn >nul 2>&1
    if !errorlevel! equ 0 set "STATUS_MVN=[OK - San co tren Windows]"
)

set "STATUS_JAR=[CHUA BUILD - Hay chon 1 hoac 10]"
if exist "%ROOT_DIR%\target\Nso-jar-with-dependencies.jar" (
    set "STATUS_JAR=[OK - Da build san sang deploy]"
)

set "STATUS_KEY=[TU DONG TAO KHI KET NOI]"
if exist "!KEY_PATH!" (
    set "STATUS_KEY=[OK - Da co key + Auto-Sync]"
)

set "STATUS_DATA=[THIEU - Thu muc Data trong]"
if exist "%ROOT_DIR%\Data" (
    set "STATUS_DATA=[OK - Da san sang]"
)

set "STATUS_DB=[THIEU - Chua co file SQL]"
if exist "%ROOT_DIR%\database\init_nso_clean.sql" (
    set "STATUS_DB=[OK - Da san sang]"
)

set "STATUS_NODE=[CHUA CAI - Can cho Web Admin]"
where node >nul 2>&1
if !errorlevel! equ 0 (
    set "STATUS_NODE=[OK - Da cai dat tren Windows]"
)

echo ===============================================================================
echo        NSO SERVER - BO CONG CU QUAN TRI ALPINE LINUX DOC LAP (OPENRC)
echo ===============================================================================
echo   Alpine VM      : !VM_USER!@!VM_IP! ^| Game Port: !VM_PORT!
echo   SSH Auth       : !SSH_KEY_REL! !STATUS_KEY!
echo   Active Profile : !PROFILE_LABEL!
echo   Thu muc VM     : /opt/nso-server (OpenRC: /etc/init.d/nso-server)
echo.
echo   [ TRANG THAI TAI NGUYEN VA MOI TRUONG - ALPINE TOOLKIT ]
echo   - JDK 17 Compiler     : !STATUS_JDK!
echo   - Apache Maven        : !STATUS_MVN!
echo   - File JAR Game Server: !STATUS_JAR!
echo   - Du lieu Game (Data) : !STATUS_DATA!
echo   - Database sach (SQL) : !STATUS_DB!
echo   - Node.js (Web Admin) : !STATUS_NODE!
echo ===============================================================================
echo.
echo   --- [ VAN HANH VA TRIEN KHAI ALPINE LINUX VM ] ---
echo   [1]  1-Click Build va Deploy len Alpine VM (OpenRC + jemalloc + Profile)
echo   [2]  Bang dieu khien Alpine VM (Status / Live Logs / Restart OpenRC Service)
echo   [3]  Mo SSH Terminal ket noi truc tiep vao Alpine VM
echo   [4]  Theo doi tai nguyen Alpine VM thoi gian thuc (CPU / RAM / Disk / Ports)
echo   [5]  Tai ban sao luu Database tu Alpine VM ve may ca nhan (backups/)
echo   [6]  Khoi chay Web Quan tri Admin cho Alpine VM (Giftcode ^& Ma OTP)
echo   [15] Tao tai khoan Game truc tiep tren Alpine VM (BCrypt + Tuy chon Admin)
echo.
echo   --- [ THIET LAP MAY CHU ALPINE MOI (FIRST SETUP) ] ---
echo   [7]  Cai dat Alpine VM tu A-Z (Disk/Swap, OpenJDK 17, jemalloc, MariaDB, Data)
echo   [12] Cau hinh / Kiem tra Embedded HTTP API Bridge (Port 80 ^& Dong Port 3306)
echo   [13] Toi uu hoa Alpine OS ^& Bao mat Firewall (Kernel Sysctl, MariaDB, iptables)
echo.
echo   --- [ CONG CU CLIENT VA MOI TRUONG BUILD ] ---
echo   [8]  Cau hinh IP / Port cho Game Client JAR (.client/)
echo   [9]  Tai va Thiet lap JDK 17 + Maven Portable (100%% Tu dong)
echo   [10] Bien dich thu nghiem tren may (Test Maven Build)
echo.
echo   --- [ HE THONG ^& PROFILE ALPINE ] ---
echo   [11] Chinh sua cau hinh Alpine VM (IP, User, Password, Port, Profile...)
echo   [14] Chuyen doi nhanh Profile Server (Low-Resource / Standard / Auto)
echo   [0]  Thoat
echo ===============================================================================
set "OPT="
set /p "OPT=>> Nhap lua chon cua ban [0-15]: "

if "!OPT!"=="1" goto :DEPLOY_VM
if "!OPT!"=="2" goto :VM_CONTROL
if "!OPT!"=="3" goto :SSH_SHELL
if "!OPT!"=="4" goto :VM_MONITOR
if "!OPT!"=="5" goto :BACKUP_DB
if "!OPT!"=="6" goto :ADMIN_PORTAL
if "!OPT!"=="7" goto :BOOTSTRAP_VM
if "!OPT!"=="8" goto :PATCH_CLIENT
if "!OPT!"=="9" goto :SETUP_TOOLS
if "!OPT!"=="10" goto :LOCAL_BUILD
if "!OPT!"=="11" goto :EDIT_CONFIG
if "!OPT!"=="12" goto :SETUP_WEB_DB
if "!OPT!"=="13" goto :OPTIMIZE_VM
if "!OPT!"=="14" goto :QUICK_SWITCH_PROFILE
if "!OPT!"=="15" goto :CREATE_ACCOUNT
if "!OPT!"=="0" exit /b 0

echo [!] Lua chon khong hop le. Vui long nhap tu 0 den 15.
timeout /t 2 >nul
goto :MAIN_MENU

:: ===============================================================================
:: HELPER: CHECK JAVA / MAVEN & BUILD WRAPPER
:: ===============================================================================
:CHECK_LOCAL_ENV
set "MVN_BIN=mvn"
if exist "%ROOT_DIR%\tools\jdk\bin\javac.exe" (
    if exist "%ROOT_DIR%\tools\maven\bin\mvn.cmd" (
        set "JAVA_HOME=%ROOT_DIR%\tools\jdk"
        set "PATH=%ROOT_DIR%\tools\maven\bin;%ROOT_DIR%\tools\jdk\bin;%PATH%"
        set "MVN_BIN=%ROOT_DIR%\tools\maven\bin\mvn.cmd"
        goto :EOF
    )
)
where mvn >nul 2>&1
if %errorlevel% equ 0 goto :EOF

echo.
echo [!] Chua tim thay JDK 17 va Maven. Dang tu dong tai bo Portable...
call :SETUP_TOOLS_SILENT
if exist "%ROOT_DIR%\tools\jdk\bin\javac.exe" (
    if exist "%ROOT_DIR%\tools\maven\bin\mvn.cmd" (
        set "JAVA_HOME=%ROOT_DIR%\tools\jdk"
        set "PATH=%ROOT_DIR%\tools\maven\bin;%ROOT_DIR%\tools\jdk\bin;%PATH%"
        set "MVN_BIN=%ROOT_DIR%\tools\maven\bin\mvn.cmd"
    )
)
goto :EOF

:RUN_MAVEN_BUILD
call :CHECK_LOCAL_ENV
cd /d "%ROOT_DIR%"
cmd.exe /c ""!MVN_BIN!" clean package -DskipTests"
exit /b %errorlevel%

:SETUP_TOOLS_SILENT
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\setup_portable_tools.ps1"
goto :EOF

:SAVE_ALPINE_CONFIG
(
    echo # ============================================================
    echo # NSO GAME SERVER - CAU HINH ALPINE LINUX VM (DOC LAP)
    echo # ============================================================
    echo.
    echo VM_IP=!VM_IP!
    echo VM_USER=!VM_USER!
    echo VM_PASS=!VM_PASS!
    echo VM_PORT=!VM_PORT!
    echo SSH_KEY=!SSH_KEY_REL!
    echo WEB_PORT=!WEB_PORT!
    echo SERVER_PROFILE=!SERVER_PROFILE!
) > "%CONFIG_FILE%"
goto :EOF

:: ===============================================================================
:: 1. 1-CLICK DEPLOY TO ALPINE VM
:: ===============================================================================
:DEPLOY_VM
cls
echo ===============================================================================
echo          QUY TRINH 1-CLICK BUILD VA DEPLOY LEN ALPINE LINUX VM
echo ===============================================================================
echo   -- May chu Alpine : !VM_USER!@!VM_IP!
echo   -- Key xac thuc   : !KEY_PATH!
echo   -- Profile hien   : !PROFILE_LABEL!
echo ===============================================================================
echo.
echo   [CHON PROFILE TOI UU CHO LAN DEPLOY NAY]
echo   [Enter] Giu nguyen Profile hien tai: !SERVER_PROFILE!
echo   [1]     Chuyen sang LOW_RESOURCE (512M-2GB RAM - jemalloc + SerialGC, 320M)
echo   [2]     Chuyen sang STANDARD     (4GB+ RAM - jemalloc + G1GC, Heap 2048M)
echo   [3]     Chuyen sang AUTO         (Tu dong nhan dien dung luong RAM tren VM)
echo ===============================================================================
set "DEP_P_OPT="
set /p "DEP_P_OPT=>> Bam Enter de tiep tuc hoac nhap [1/2/3] de doi Profile: "

if "!DEP_P_OPT!"=="1" set "SERVER_PROFILE=LOW_RESOURCE"
if "!DEP_P_OPT!"=="2" set "SERVER_PROFILE=STANDARD"
if "!DEP_P_OPT!"=="3" set "SERVER_PROFILE=AUTO"

call :SAVE_ALPINE_CONFIG

echo.
echo [*] Dang deploy len Alpine VM voi Profile: !SERVER_PROFILE!
echo.
echo [BUOC 1/4] Bien dich du an tren may ca nhan...
call :RUN_MAVEN_BUILD
if errorlevel 1 (
    echo.
    echo [LOI] Build that bai voi ma loi !errorlevel!!
    pause
    goto :MAIN_MENU
)

powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\alpine-local\deploy_alpine_vm.ps1"
if errorlevel 1 (
    echo.
    echo [LOI] Qua trinh deploy len Alpine VM gap su co!
    pause
    goto :MAIN_MENU
)

echo.
echo ===============================================================================
echo   HOAN TAT BUILD VA DEPLOY LEN ALPINE VM [!SERVER_PROFILE!] THANH CONG!
echo ===============================================================================
echo.
pause
goto :LOAD_CONFIG

:: ===============================================================================
:: 2. ALPINE VM CONTROL MENU (OPENRC)
:: ===============================================================================
:VM_CONTROL
cls
powershell -NoProfile -ExecutionPolicy Bypass -Command ". '%ROOT_DIR%\scripts\alpine-local\ssh_alpine_helper.ps1'; Ensure-AlpineSshKey | Out-Null"
echo ===============================================================================
echo          DANG KIEM TRA TRANG THAI ALPINE VM: !VM_USER!@!VM_IP!
echo ===============================================================================
echo.
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR -o ConnectTimeout=8 !VM_USER!@!VM_IP! "if [ -f /root/check_status.sh ]; then sh /root/check_status.sh; elif [ -f /opt/nso-server/check_status.sh ]; then sh /opt/nso-server/check_status.sh; else rc-service nso-server status; ss -tuln; free -h; df -h / /opt; fi"

:VM_SUBMENU
echo.
echo ===============================================================================
echo                     THAO TAC NHANH VOI ALPINE VM (OPENRC)
echo ===============================================================================
echo   [1] Lam moi / Kiem tra lai trang thai
echo   [2] Xem Live Log may chu [tail -f /opt/nso-server/logs/service.log]
echo   [3] Khoi dong lai Server Game [rc-service nso-server restart]
echo   [4] Bat Server Game [rc-service nso-server start]
echo   [5] Dung Server Game [rc-service nso-server stop]
echo   [0] Quay lai Menu chinh
echo ===============================================================================
set "VM_OPT="
set /p "VM_OPT=>> Nhap lua chon [0-5]: "

if "!VM_OPT!"=="1" goto :VM_CONTROL
if "!VM_OPT!"=="2" goto :VM_VIEW_LOGS
if "!VM_OPT!"=="3" goto :VM_RESTART_SVC
if "!VM_OPT!"=="4" goto :VM_START_SVC
if "!VM_OPT!"=="5" goto :VM_STOP_SVC
if "!VM_OPT!"=="0" goto :MAIN_MENU
goto :VM_SUBMENU

:VM_VIEW_LOGS
cls
echo [*] Dang theo doi Live Log tren Alpine VM [Nhan Ctrl+C de dung va quay lai]...
echo.
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "tail -n 50 -f /opt/nso-server/logs/service.log /opt/nso-server/logs/service_error.log"
goto :VM_CONTROL

:VM_RESTART_SVC
echo.
echo [*] Dang restart OpenRC service nso-server...
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "rc-service nso-server restart && sleep 2"
goto :VM_CONTROL

:VM_START_SVC
echo.
echo [*] Dang start OpenRC service nso-server...
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "rc-service nso-server start && sleep 2"
goto :VM_CONTROL

:VM_STOP_SVC
echo.
echo [*] Dang stop OpenRC service nso-server...
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "rc-service nso-server stop"
echo.
pause
goto :VM_CONTROL

:: ===============================================================================
:: 3. SSH SHELL
:: ===============================================================================
:SSH_SHELL
cls
powershell -NoProfile -ExecutionPolicy Bypass -Command ". '%ROOT_DIR%\scripts\alpine-local\ssh_alpine_helper.ps1'; Ensure-AlpineSshKey | Out-Null"
echo ===============================================================================
echo   DANG MO SSH TERMINAL TOI ALPINE VM !VM_USER!@!VM_IP!... (Go 'exit' de thoat)
echo ===============================================================================
echo.
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP!
goto :MAIN_MENU

:: ===============================================================================
:: 4. ALPINE VM RESOURCE MONITOR
:: ===============================================================================
:VM_MONITOR
cls
powershell -NoProfile -ExecutionPolicy Bypass -Command ". '%ROOT_DIR%\scripts\alpine-local\ssh_alpine_helper.ps1'; Ensure-AlpineSshKey | Out-Null"
echo ===============================================================================
echo   THEO DOI TAI NGUYEN ALPINE VM THOI GIAN THUC (Nhan Ctrl+C de thoat)
echo ===============================================================================
echo.
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "while true; do clear; echo '=================================================='; echo ' ALPINE VM: '$(hostname)' - TIME: '$(date '+%%Y-%%m-%%d %%H:%%M:%%S'); echo '=================================================='; echo '--- 1. RAM & SWAP ---'; free -h; echo ''; echo '--- 2. DISK (/ & /opt) ---'; df -h / /opt | uniq; echo ''; echo '--- 3. UPTIME & LOAD ---'; uptime; echo ''; echo '--- 4. PORTS ---'; ss -tuln; echo ''; echo '[Lam moi moi 3s - Nhan Ctrl+C de dung]'; sleep 3; done"
goto :MAIN_MENU

:: ===============================================================================
:: 5. BACKUP DB
:: ===============================================================================
:BACKUP_DB
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\alpine-local\backup_alpine_db.ps1"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 6. ADMIN PORTAL (ALPINE)
:: ===============================================================================
:ADMIN_PORTAL
cls
echo ===============================================================================
echo   KHOI CHAY TRANG QUAN TRI ADMIN CHO ALPINE VM (GIFTCODE VA MA OTP)
echo ===============================================================================
echo.
where node >nul 2>&1
if %errorlevel% neq 0 (
    echo [LOI] May tinh chua cai dat Node.js. Vui long cai Node.js de chay web admin!
    pause
    goto :MAIN_MENU
)

powershell -NoProfile -ExecutionPolicy Bypass -Command ". '%ROOT_DIR%\scripts\alpine-local\ssh_alpine_helper.ps1'; Ensure-AlpineSshKey | Out-Null"

set "ADMIN_PORT=4000"
if not "!WEB_PORT!"=="" set "ADMIN_PORT=!WEB_PORT!"

echo [*] Dang mo trinh duyet tai: http://localhost:!ADMIN_PORT! ...
start "" "http://localhost:!ADMIN_PORT!"

echo [*] Khoi chay backend admin server ket noi Alpine VM (Nhan Ctrl+C de tat)...
echo.
node "%ROOT_DIR%\tools\admin-portal\admin_server_alpine.js"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 7. BOOTSTRAP FRESH ALPINE VM (A-Z)
:: ===============================================================================
:BOOTSTRAP_VM
cls
echo ===============================================================================
echo    CAI DAT TOAN DIEN MAY CHU ALPINE LINUX VM MOI TINH (BOOTSTRAP TU A-Z)
echo ===============================================================================
echo   -- May chu Alpine : !VM_USER!@!VM_IP!
echo   -- Server Profile : !PROFILE_LABEL!
echo   -- Thao tac       : Format/Mount Disk (neu Live), Cai OpenJDK 17 + jemalloc,
echo                       MariaDB, Swap, Firewall, Upload Data ^& Nap Database sach.
echo ===============================================================================
echo.
set /p "CONFIRM=>> Ban co chac chan muon tien hanh cai dat Alpine VM [Y/N]? "
if /i not "!CONFIRM!"=="Y" (
    echo [*] Da huy thao tac cai dat Alpine VM.
    timeout /t 2 >nul
    goto :MAIN_MENU
)

powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\alpine-local\bootstrap_alpine_vm.ps1"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 8. PATCH CLIENT
:: ===============================================================================
:PATCH_CLIENT
call "%ROOT_DIR%\scripts\local\set_client_ip.cmd"
goto :MAIN_MENU

:: ===============================================================================
:: 9. SETUP TOOLS
:: ===============================================================================
:SETUP_TOOLS
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\setup_portable_tools.ps1"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 10. TEST BUILD
:: ===============================================================================
:LOCAL_BUILD
cls
echo ===============================================================================
echo   DANG BIEN DICH VA DONG GOI NSO GAME SERVER (TEST BUILD)...
echo ===============================================================================
echo.
call :RUN_MAVEN_BUILD
if %errorlevel% equ 0 (
    echo.
    echo [OK] Build thanh cong: target\Nso-jar-with-dependencies.jar
) else (
    echo.
    echo [LOI] Qua trinh build gap su co! [Ma loi: %errorlevel%]
)
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 11. EDIT ALPINE CONFIG
:: ===============================================================================
:EDIT_CONFIG
cls
echo ===============================================================================
echo                  CAU HINH HE THONG ALPINE LINUX HIEN TAI
echo ===============================================================================
echo   [1] Alpine VM IP   : !VM_IP!
echo   [2] SSH User       : !VM_USER!
echo   [3] SSH Password   : !VM_PASS!
echo   [4] Game Port      : !VM_PORT!
echo   [5] SSH Key Path   : !SSH_KEY_REL!
echo   [6] Web Admin Port : !WEB_PORT!
echo   [7] Server Profile : !SERVER_PROFILE!
echo ===============================================================================
echo.
set /p "NEW_IP=>> Nhap Alpine VM IP moi (Enter de giu '!VM_IP!'): "
if not "!NEW_IP!"=="" set "VM_IP=!NEW_IP!"

set /p "NEW_USER=>> Nhap SSH User moi (Enter de giu '!VM_USER!'): "
if not "!NEW_USER!"=="" set "VM_USER=!NEW_USER!"

set /p "NEW_PASS=>> Nhap SSH Password moi (Enter de giu '!VM_PASS!'): "
if not "!NEW_PASS!"=="" set "VM_PASS=!NEW_PASS!"

set /p "NEW_PORT=>> Nhap Game Port moi (Enter de giu '!VM_PORT!'): "
if not "!NEW_PORT!"=="" set "VM_PORT=!NEW_PORT!"

set /p "NEW_WEB_PORT=>> Nhap Web Port (Enter de giu '!WEB_PORT!'): "
if not "!NEW_WEB_PORT!"=="" set "WEB_PORT=!NEW_WEB_PORT!"

echo.
echo   Tuy chon Profile toi uu:
echo   [1] LOW_RESOURCE (512M - 2GB RAM)
echo   [2] STANDARD     (4GB+ RAM)
echo   [3] AUTO         (Tu dong theo RAM tren Alpine VM)
set /p "NEW_PROF_OPT=>> Chon Profile [Enter de giu '!SERVER_PROFILE!']: "
if "!NEW_PROF_OPT!"=="1" set "SERVER_PROFILE=LOW_RESOURCE"
if "!NEW_PROF_OPT!"=="2" set "SERVER_PROFILE=STANDARD"
if "!NEW_PROF_OPT!"=="3" set "SERVER_PROFILE=AUTO"

call :SAVE_ALPINE_CONFIG

echo.
echo [OK] Da luu cau hinh moi vao %CONFIG_FILE%!
timeout /t 2 >nul
goto :LOAD_CONFIG

:: ===============================================================================
:: 12. SETUP HTTP API BRIDGE (PORT 80 & ISOLATE PORT 3306)
:: ===============================================================================
:SETUP_WEB_DB
cls
echo ===============================================================================
echo   CAU HINH HTTP API BRIDGE TREN ALPINE VM (PORT 80 ^& DONG KIN PORT 3306)
echo ===============================================================================
powershell -NoProfile -ExecutionPolicy Bypass -Command ". '%ROOT_DIR%\scripts\alpine-local\ssh_alpine_helper.ps1'; Ensure-AlpineSshKey | Out-Null"
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o LogLevel=ERROR !VM_USER!@!VM_IP! "sed -i 's/bind-address.*/bind-address = 127.0.0.1/g' /etc/my.cnf.d/*.cnf 2>/dev/null || true && rc-service mariadb restart && iptables -D INPUT -p tcp --dport 3306 -j ACCEPT 2>/dev/null || true && iptables -I INPUT 1 -p tcp --dport 80 -j ACCEPT 2>/dev/null || true && /etc/init.d/iptables save >/dev/null 2>&1 || true && echo '' && echo '=== TRANG THAI CAC PORT TREN ALPINE VM ===' && ss -tuln"
echo.
powershell -Command "Test-NetConnection !VM_IP! -Port 80"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 13. OPTIMIZE ALPINE OS & SECURITY HARDENING
:: ===============================================================================
:OPTIMIZE_VM
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\alpine-local\optimize_alpine_vm.ps1"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 14. QUICK SWITCH PROFILE
:: ===============================================================================
:QUICK_SWITCH_PROFILE
cls
echo ===============================================================================
echo         CHUYEN DOI NHANH PROFILE TOI UU CHO ALPINE LINUX GAME SERVER
echo ===============================================================================
echo   Profile hien tai : !PROFILE_LABEL!
echo ===============================================================================
echo.
echo   [1] LOW_RESOURCE Profile (Toi uu cho Alpine VM 512MB - 2GB RAM)
echo       - Heap: -Xms128M -Xmx320M -Xss256k -XX:+UseSerialGC + jemalloc
echo       - ThreadPool: 4, ScheduledPool: 2, Database Pool: 2-8, Buffer: 32M
echo.
echo   [2] STANDARD Profile (Toi uu cho Alpine VM 4GB+ RAM)
echo       - Heap: -Xms1024M -Xmx2048M -Xss512k -XX:+UseG1GC + jemalloc
echo       - ThreadPool: 10, ScheduledPool: 5, Database Pool: 5-20, Buffer: 512M
echo.
echo   [3] AUTO Profile (Tu dong nhan dien thong so RAM tren Alpine VM)
echo.
echo   [0] Quay lai Menu chinh
echo ===============================================================================
set "SW_OPT="
set /p "SW_OPT=>> Nhap lua chon [0-3]: "

if "!SW_OPT!"=="1" set "SERVER_PROFILE=LOW_RESOURCE"
if "!SW_OPT!"=="2" set "SERVER_PROFILE=STANDARD"
if "!SW_OPT!"=="3" set "SERVER_PROFILE=AUTO"
if "!SW_OPT!"=="0" goto :MAIN_MENU

call :SAVE_ALPINE_CONFIG

echo.
echo [OK] Da chuyen sang Profile: !SERVER_PROFILE!
timeout /t 2 >nul
goto :LOAD_CONFIG

:: ===============================================================================
:: 15. CREATE GAME ACCOUNT ON ALPINE VM
:: ===============================================================================
:CREATE_ACCOUNT
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\alpine-local\create_alpine_account.ps1"
echo.
pause
goto :MAIN_MENU


