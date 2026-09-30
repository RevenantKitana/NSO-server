@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion
title NSO Server - Cloud VM Management Hub
color 0B

set "ROOT_DIR=%~dp0"
if "%ROOT_DIR:~-1%"=="\" set "ROOT_DIR=%ROOT_DIR:~0,-1%"

:LOAD_CONFIG
set "VM_IP="
set "VM_USER="
set "VM_PORT="
set "SSH_KEY_REL="
set "WEB_PORT="

set "CONFIG_FILE=%ROOT_DIR%\config\server_config.ini"
if not exist "%CONFIG_FILE%" (
    cls
    echo ===============================================================================
    echo  [LOI NGHIEM TRONG] Khong tim thay file cau hinh bat buoc tai:
    echo  %CONFIG_FILE%
    echo ===============================================================================
    echo  Vui long tao file config\server_config.ini truoc khi mo chuong trinh!
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
        if /i "!CFG_KEY!"=="VM_PORT" set "VM_PORT=!CFG_VAL!"
        if /i "!CFG_KEY!"=="SSH_KEY" set "SSH_KEY_REL=!CFG_VAL!"
        if /i "!CFG_KEY!"=="WEB_PORT" set "WEB_PORT=!CFG_VAL!"
    )
)

REM Kiem tra bat buoc cac truong cau hinh
set "HAS_CONFIG_ERROR=0"
if "!VM_IP!"=="" set "HAS_CONFIG_ERROR=1"
if "!VM_USER!"=="" set "HAS_CONFIG_ERROR=1"
if "!VM_PORT!"=="" set "HAS_CONFIG_ERROR=1"
if "!SSH_KEY_REL!"=="" set "HAS_CONFIG_ERROR=1"

if "!HAS_CONFIG_ERROR!"=="1" (
    cls
    echo ===============================================================================
    echo  [LOI CAU HINH] File config\server_config.ini thieu cac thong so bat buoc:
    echo ===============================================================================
    if "!VM_IP!"=="" echo   [-] Thieu thong tin: VM_IP - Dia chi IP cua VPS
    if "!VM_USER!"=="" echo   [-] Thieu thong tin: VM_USER - User SSH [ubuntu]
    if "!VM_PORT!"=="" echo   [-] Thieu thong tin: VM_PORT - Port Game [14444]
    if "!SSH_KEY_REL!"=="" echo   [-] Thieu thong tin: SSH_KEY - Duong dan file SSH Key
    echo ===============================================================================
    echo  Vui long bo sung day du vao file: %CONFIG_FILE%
    echo ===============================================================================
    echo.
    pause
    exit /b 1
)

set "KEY_PATH=%ROOT_DIR%\!SSH_KEY_REL!"

REM Tu dong thiet lap quyen doc bao mat cho SSH Key tren Windows
if exist "!KEY_PATH!" (
    icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1
)

:MAIN_MENU
cls

REM ------------------------------------------------------------
REM KIEM TRA TRANG THAI CAC THANH PHAN HE THONG (FIRST SETUP CHECK)
REM ------------------------------------------------------------
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

set "STATUS_KEY=[LOI - Khong tim thay file key]"
if exist "!KEY_PATH!" (
    set "STATUS_KEY=[OK - Da co key]"
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
echo                NSO SERVER - BANG DIEU KHIEN CLOUD VM CHUYEN NGHIEP
echo ===============================================================================
echo   Cloud VM : !VM_USER!@!VM_IP! ^| Game Port: !VM_PORT!
echo   SSH Key  : !SSH_KEY_REL! !STATUS_KEY!
echo.
echo   [ TRANG THAI TAI NGUYEN VA MOI TRUONG - FIRST SETUP CHECK ]
echo   - JDK 17 Compiler     : !STATUS_JDK!
echo   - Apache Maven        : !STATUS_MVN!
echo   - File JAR Game Server: !STATUS_JAR!
echo   - Du lieu Game (Data) : !STATUS_DATA!
echo   - Database sach (SQL) : !STATUS_DB!
echo   - Node.js (Web Admin) : !STATUS_NODE!
echo ===============================================================================
echo.
echo   --- [ VAN HANH VA TRIEN KHAI CLOUD VM ] ---
echo   [1]  1-Click Build va Deploy len VM (Build Local - Upload - Restart Service)
echo   [2]  Bang dieu khien VM (Kiem tra Status / Live Logs / Restart Service)
echo   [3]  Mo SSH Terminal ket noi truc tiep vao VM
echo   [4]  Theo doi tai nguyen VM thoi gian thuc (CPU / RAM / Disk / Uptime)
echo   [5]  Tai ban sao luu Database tu VM ve may ca nhan (Thu muc backups/)
echo   [6]  Khoi chay Web Quan tri Admin (Tao Giftcode va Ma OTP qua Web)
echo.
echo   --- [ THIET LAP MAY CHU MOI (FIRST SETUP) ] ---
echo   [7]  Cai dat VM moi tinh tu A-Z (Toi uu OS, Swap, Java 17, MariaDB, Database, Data)
echo   [12] Cau hinh / Mo ket noi Web Database (Port 3306 ^& User nso_web)
echo   [13] Toi uu hoa Base OS ^& Bao mat Firewall (Chay toi uu doc lap cho VM)
echo.
echo   --- [ CONG CU CLIENT VA MOI TRUONG BUILD ] ---
echo   [8]  Cau hinh IP / Port cho Game Client JAR (.client/)
echo   [9]  Tai va Thiet lap JDK 17 + Maven Portable (100%% Tu dong)
echo   [10] Bien dich thu nghiem tren may (Test Maven Build)
echo.
echo   --- [ HE THONG ] ---
echo   [11] Chinh sua cau hinh nhanh (Doi IP VM, Port, SSH Key...)
echo   [0]  Thoat
echo ===============================================================================
set "OPT="
set /p "OPT=>> Nhap lua chon cua ban [0-13]: "

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
if "!OPT!"=="0" exit /b 0

echo [!] Lua chon khong hop le. Vui long nhap tu 0 den 13.
timeout /t 2 >nul
goto :MAIN_MENU

:: ===============================================================================
:: HELPER: CHECK JAVA / MAVEN
:: ===============================================================================
:CHECK_LOCAL_ENV
if exist "%ROOT_DIR%\tools\jdk\bin\javac.exe" (
    if exist "%ROOT_DIR%\tools\maven\bin\mvn.cmd" (
        set "JAVA_HOME=%ROOT_DIR%\tools\jdk"
        set "PATH=%ROOT_DIR%\tools\maven\bin;%ROOT_DIR%\tools\jdk\bin;%PATH%"
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
    )
)
goto :EOF

:SETUP_TOOLS_SILENT
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\setup_portable_tools.ps1"
goto :EOF

:: ===============================================================================
:: 1. 1-CLICK DEPLOY TO VM
:: ===============================================================================
:DEPLOY_VM
cls
echo ===============================================================================
echo            QUY TRINH 1-CLICK BUILD VA DEPLOY LEN VM CLOUD
echo ===============================================================================
echo   -- May chu dich : !VM_USER!@!VM_IP!
echo   -- Key xac thuc : !KEY_PATH!
echo ===============================================================================
echo.

if not exist "!KEY_PATH!" (
    echo [LOI] Khong tim thay file SSH Key tai duong dan:
    echo       !KEY_PATH!
    echo Vui long kiem tra lai thong so SSH_KEY trong config\server_config.ini!
    pause
    goto :MAIN_MENU
)

icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1

echo [BUOC 1/4] Bien dich du an tren may ca nhan...
call :CHECK_LOCAL_ENV
cd /d "%ROOT_DIR%"
call mvn clean package -DskipTests
if not exist "%ROOT_DIR%\target\Nso-jar-with-dependencies.jar" (
    echo.
    echo [LOI] Build that bai! Khong tim thay file target\Nso-jar-with-dependencies.jar.
    pause
    goto :MAIN_MENU
)

echo.
echo [BUOC 2/4] Chuan bi moi truong va sao luu ban JAR cu tren VM...
ssh -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "mkdir -p /home/ubuntu/nso-server/logs /home/ubuntu/nso-server/backups; chown -R ubuntu:ubuntu /home/ubuntu/nso-server; if [ -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar ]; then cp -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar /home/ubuntu/nso-server/backups/Nso_backup_`date +%%Y%%m%%d_%%H%%M%%S`.jar; echo '>> Da sao luu ban JAR cu thanh cong.'; ls -1t /home/ubuntu/nso-server/backups/Nso_backup_*.jar 2>/dev/null | tail -n +4 | xargs -r rm -f; else echo '>> Chua co file JAR cu tren VM (Cai dat moi).'; fi"

echo.
echo [BUOC 3/4] Tai file JAR moi va dong bo cau hinh len VM (!VM_IP!)...
scp -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o ConnectTimeout=10 "%ROOT_DIR%\target\Nso-jar-with-dependencies.jar" !VM_USER!@!VM_IP!:/home/ubuntu/nso-server/Nso-jar-with-dependencies.jar
scp -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o ConnectTimeout=10 "%ROOT_DIR%\config\config.properties.prod" !VM_USER!@!VM_IP!:/home/ubuntu/nso-server/config.properties >nul 2>&1
scp -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o ConnectTimeout=10 "%ROOT_DIR%\config\mysql.properties.prod" !VM_USER!@!VM_IP!:/home/ubuntu/nso-server/mysql.properties >nul 2>&1
if errorlevel 1 (
    echo [LOI] Khong the upload file len VM! Vui long kiem tra ket noi mang.
    pause
    goto :MAIN_MENU
)

echo.
echo [BUOC 4/4] Khoi dong lai dich vu nso-server tren VM...
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "mkdir -p /home/ubuntu/nso-server/logs && sudo systemctl restart nso-server.service && sleep 3 && sudo systemctl status nso-server.service --no-pager && echo '' && echo '=== CAC PORT DANG MO (PORTS) ===' && sudo ss -tuln"

echo.
echo ===============================================================================
echo   HOAN TAT BUILD VA DEPLOY LEN VM THANH CONG!
echo ===============================================================================
echo.
pause
goto :MAIN_MENU
    
    :: ===============================================================================
    :: 2. VM CONTROL MENU
    :: ===============================================================================
    :VM_CONTROL
    cls
    if not exist "!KEY_PATH!" (
        echo [LOI] Khong tim thay file SSH Key tai: !KEY_PATH!
        pause
        goto :MAIN_MENU
    )
    
    icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1
    
    echo ===============================================================================
    echo            DANG KIEM TRA TRANG THAI MAY CHU: !VM_USER!@!VM_IP!
    echo ===============================================================================
    echo.
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no -o ConnectTimeout=8 !VM_USER!@!VM_IP! "if [ -f /home/ubuntu/check_status.sh ]; then /home/ubuntu/check_status.sh; elif [ -f /home/ubuntu/nso-server/check_status.sh ]; then /home/ubuntu/nso-server/check_status.sh; else echo '=== 1. SYSTEMD SERVICE ===' && sudo systemctl status nso-server.service --no-pager && echo '' && echo '=== 2. OPEN PORTS ===' && sudo ss -tuln && echo '' && echo '=== 3. RAM & DISK ===' && free -h && echo '' && df -h /; fi"
    
    :VM_SUBMENU
    echo.
    echo ===============================================================================
    echo                           THAO TAC NHANH VOI VM
    echo ===============================================================================
    echo   [1] Lam moi / Kiem tra lai trang thai
    echo   [2] Xem Live Log may chu [tail -f logs/service.log]
    echo   [3] Khoi dong lai Server Game [Restart nso-server.service]
    echo   [4] Bat Server Game [Start nso-server.service]
    echo   [5] Dung Server Game [Stop nso-server.service]
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
    echo [*] Dang theo doi Live Log [Nhan Ctrl+C de dung va quay lai]...
    echo.
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "if [ -f /home/ubuntu/nso-server/logs/service.log ]; then tail -f /home/ubuntu/nso-server/logs/service.log; else sudo journalctl -u nso-server.service -f; fi"
    goto :VM_CONTROL
    
    :VM_RESTART_SVC
    echo.
    echo [*] Dang restart nso-server.service...
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "sudo systemctl restart nso-server.service && sleep 2"
    goto :VM_CONTROL
    
    :VM_START_SVC
    echo.
    echo [*] Dang start nso-server.service...
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "sudo systemctl start nso-server.service && sleep 2"
    goto :VM_CONTROL
    
    :VM_STOP_SVC
    echo.
    echo [*] Dang stop nso-server.service...
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "sudo systemctl stop nso-server.service"
    echo.
    pause
    goto :VM_CONTROL
    
    :: ===============================================================================
    :: 3. SSH SHELL
    :: ===============================================================================
    :SSH_SHELL
    cls
    if not exist "!KEY_PATH!" (
        echo [LOI] Khong tim thay file SSH Key tai: !KEY_PATH!
        pause
        goto :MAIN_MENU
    )
    icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1
    echo ===============================================================================
    echo   DANG MO SSH TERMINAL TOI !VM_USER!@!VM_IP!... (Go 'exit' de thoat)
    echo ===============================================================================
    echo.
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP!
    goto :MAIN_MENU
    
    :: ===============================================================================
    :: 4. VM RESOURCE MONITOR
    :: ===============================================================================
    :VM_MONITOR
    cls
    if not exist "!KEY_PATH!" (
        echo [LOI] Khong tim thay file SSH Key tai: !KEY_PATH!
        pause
        goto :MAIN_MENU
    )
    icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1
    echo ===============================================================================
    echo   THEO DOI TAI NGUYEN VM THOI GIAN THUC (Nhan Ctrl+C de thoat)
    echo ===============================================================================
    echo.
    ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "while true; do clear; echo '=================================================='; echo ' VM: '$(hostname)' - TIME: '$(date '+%%Y-%%m-%%d %%H:%%M:%%S'); echo '=================================================='; echo '--- 1. RAM & SWAP ---'; free -h; echo ''; echo '--- 2. DISK ROOT ---'; df -h /; echo ''; echo '--- 3. UPTIME & LOAD ---'; uptime; echo ''; echo '--- 4. PORTS ---'; sudo ss -tuln; echo ''; echo '[Lam moi moi 3s - Nhan Ctrl+C de dung]'; sleep 3; done"
    goto :MAIN_MENU

:: ===============================================================================
:: 5. BACKUP DB
:: ===============================================================================
:BACKUP_DB
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\backup_db_to_local.ps1"
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 6. ADMIN PORTAL (GIFTCODE & OTP)
:: ===============================================================================
:ADMIN_PORTAL
cls
echo ===============================================================================
echo   KHOI CHAY TRANG QUAN TRI ADMIN (GIFTCODE VA MA OTP)
echo ===============================================================================
echo.
where node >nul 2>&1
if %errorlevel% neq 0 (
    echo [LOI] May tinh chua cai dat Node.js. Vui long cai Node.js de chay web admin!
    pause
    goto :MAIN_MENU
)

set "ADMIN_PORT=4000"
if not "!WEB_PORT!"=="" set "ADMIN_PORT=!WEB_PORT!"

echo [*] Dang mo trinh duyet tai: http://localhost:!ADMIN_PORT! ...
start "" "http://localhost:!ADMIN_PORT!"

echo [*] Khoi chay backend admin server (Nhan Ctrl+C de tat server khi xong)...
echo.
node "%ROOT_DIR%\tools\admin-portal\admin_server.js"
goto :MAIN_MENU

:: ===============================================================================
:: 7. BOOTSTRAP FRESH VM (A-Z)
:: ===============================================================================
:BOOTSTRAP_VM
cls
echo ===============================================================================
echo    CAI DAT TOAN DIEN MAY CHU CLOUD VM MOI TINH (BOOTSTRAP TU A-Z)
echo ===============================================================================
echo   -- May chu dich : !VM_USER!@!VM_IP!
echo   -- Canh bao     : Thao tac nay se cai Java 17, MariaDB, Swap, upload Data
echo                     va nap Database sach lan dau len VM.
echo ===============================================================================
echo.
set /p "CONFIRM=>> Ban co chac chan muon tien hanh cai dat [Y/N]? "
if /i not "!CONFIRM!"=="Y" (
    echo [*] Da huy thao tac cai dat VM.
    timeout /t 2 >nul
    goto :MAIN_MENU
)

powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\bootstrap_fresh_vm.ps1"
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
call :CHECK_LOCAL_ENV
cd /d "%ROOT_DIR%"
call mvn clean package -DskipTests
if %errorlevel% equ 0 (
    echo.
    echo [OK] Build thanh cong: target\Nso-jar-with-dependencies.jar
) else (
    echo.
    echo [LOI] Qua trinh build gap su co!
)
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 11. EDIT CONFIG
:: ===============================================================================
:EDIT_CONFIG
cls
echo ===============================================================================
echo                      CAU HINH HE THONG HIEN TAI
echo ===============================================================================
echo   [1] VM IP   : !VM_IP!
echo   [2] VM User : !VM_USER!
echo   [3] VM Port : !VM_PORT!
echo   [4] SSH Key : !SSH_KEY_REL!
echo   [5] Web Port: !WEB_PORT!
echo ===============================================================================
echo.
set /p "NEW_IP=>> Nhap VM IP moi (Bam Enter de giu nguyen '!VM_IP!'): "
if not "!NEW_IP!"=="" set "VM_IP=!NEW_IP!"

set /p "NEW_PORT=>> Nhap Game Port moi (Bam Enter de giu nguyen '!VM_PORT!'): "
if not "!NEW_PORT!"=="" set "VM_PORT=!NEW_PORT!"

set /p "NEW_KEY=>> Nhap duong dan SSH Key (Bam Enter de giu nguyen '!SSH_KEY_REL!'): "
if not "!NEW_KEY!"=="" set "SSH_KEY_REL=!NEW_KEY!"

(
    echo # ============================================================
    echo # NSO GAME SERVER - CAU HINH HE THONG VA CLOUD VM
    echo # ============================================================
    echo.
    echo VM_IP=!VM_IP!
    echo VM_USER=!VM_USER!
    echo VM_PORT=!VM_PORT!
    echo SSH_KEY=!SSH_KEY_REL!
    echo WEB_PORT=!WEB_PORT!
) > "%CONFIG_FILE%"

echo.
echo [OK] Da luu cau hinh moi vao %CONFIG_FILE%!
timeout /t 2 >nul
goto :LOAD_CONFIG

:: ===============================================================================
:: 12. SETUP WEB DATABASE (PORT 3306 & REMOTE USER)
:: ===============================================================================
:SETUP_WEB_DB
cls
echo ===============================================================================
echo   CAU HINH VA MO PORT DATABASE MARIADB CHO WEB DANG KY (PORT 3306)
echo ===============================================================================
echo   -- May chu dich : !VM_USER!@!VM_IP!
echo   -- Thao tac     : Mo MariaDB bind 0.0.0.0, tao user nso_web, mo Firewall 3306
echo ===============================================================================
echo.
if not exist "!KEY_PATH!" (
    echo [LOI] Khong tim thay file SSH Key tai: !KEY_PATH!
    pause
    goto :MAIN_MENU
)

icacls "!KEY_PATH!" /inheritance:r /grant:r %USERNAME%:R >nul 2>&1

echo [*] Dang cau hinh MariaDB va Firewall tren VM...
ssh -t -i "!KEY_PATH!" -o StrictHostKeyChecking=no !VM_USER!@!VM_IP! "sudo sed -i 's/bind-address.*/bind-address = 0.0.0.0/g' /etc/mysql/mariadb.conf.d/*.cnf 2>/dev/null || true && sudo systemctl restart mariadb && sudo mariadb -e \"CREATE USER IF NOT EXISTS 'nso_web'@'%%' IDENTIFIED BY 'NsoWebDb2026!@#'; ALTER USER 'nso_web'@'%%' IDENTIFIED BY 'NsoWebDb2026!@#'; GRANT SELECT, INSERT, UPDATE, DELETE ON nso_test.* TO 'nso_web'@'%%'; FLUSH PRIVILEGES;\" && sudo iptables -I INPUT 1 -p tcp --dport 3306 -j ACCEPT 2>/dev/null || true && sudo iptables-save > /etc/iptables/rules.v4 2>/dev/null || true && echo '' && echo '=== TRANG THAI PORT 3306 ===' && sudo ss -tulnp | grep 3306"

echo.
echo [*] Dang kiem tra ket noi TCP Port 3306 tu may tinh cua ban...
powershell -Command "Test-NetConnection !VM_IP! -Port 3306"
echo.
echo ===============================================================================
echo   HOAN TAT THIET LAP WEB DATABASE CHO !VM_IP!:3306!
echo ===============================================================================
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 13. OPTIMIZE BASE OS & SECURITY HARDENING
:: ===============================================================================
:OPTIMIZE_VM
cls
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT_DIR%\scripts\local\optimize_remote_vm.ps1"
echo.
pause
goto :MAIN_MENU

