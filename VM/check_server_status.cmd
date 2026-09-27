@echo off
chcp 65001 >nul
setlocal

title NSO Game Server - Kiem Tra Trang Thai
color 0B

:: ============================================================
:: CAU HINH KET NOI VM
:: ============================================================
set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"

:: ============================================================
:: KIEM TRA SSH KEY
:: ============================================================
if not exist "%KEY_PATH%" (
    echo.
    echo ============================================================
    echo  [LOI] Khong tim thay file SSH Key tai:
    echo  %KEY_PATH%
    echo ============================================================
    echo.
    pause
    exit /b 1
)

:CHECK_STATUS
cls
echo ===============================================================================
echo            DANG KET NOI VA KIEM TRA TRANG THAI NSO GAME SERVER...
echo            May chu: %VM_USER%@%VM_IP%
echo ===============================================================================
echo.

ssh -t ^
    -i "%KEY_PATH%" ^
    -o StrictHostKeyChecking=no ^
    -o ConnectTimeout=8 ^
    -o ServerAliveInterval=15 ^
    %VM_USER%@%VM_IP% ^
    "if [ -f /home/ubuntu/check_status.sh ]; then /home/ubuntu/check_status.sh; else bash -c 'echo \"=== 1. SYSTEMD SERVICE ===\"; sudo systemctl status nso-server.service --no-pager; echo \"=== 2. PORTS ===\"; sudo ss -tulnp | grep -E \"14444|8020|3306\"; echo \"=== 3. RAM & DISK ===\"; free -h; df -h /; echo \"=== 4. LOGS ===\"; tail -n 10 /home/ubuntu/nso-server/logs/service.log 2>/dev/null || true'; fi"

if errorlevel 1 (
    echo.
    echo ===============================================================================
    echo  [CANH BAO] Khong the ket noi toi VM (%VM_USER%@%VM_IP%).
    echo  Nguyen nhan co the do:
    echo    1. VM dang tat hoac dang khoi dong lai tren Cloud.
    echo    2. Mang Internet bi gian doan hoac timeout ket noi.
    echo    3. IP cua VM da thay doi.
    echo ===============================================================================
    echo.
)

:MENU
echo.
echo ===============================================================================
echo                           MENU THAO TAC NHANH
echo ===============================================================================
echo   [1] Lam moi / Kiem tra lai trang thai (Refresh)
echo   [2] Xem log server truc tiep thoi gian thuc (Live Tail Log - tail -f)
echo   [3] Khoi dong lai Server Game (Restart nso-server.service)
echo   [4] Bat Server Game (Start nso-server.service)
echo   [5] Dung Server Game (Stop nso-server.service)
echo   [6] Theo doi tai nguyen VM truc tiep (Live Resource Monitor)
echo   [7] Mo SSH Terminal truc tiep vao VM (Connect Shell)
echo   [0] Thoat (Exit)
echo ===============================================================================
set /p "CHOICE=>> Nhap lua chon cua ban [0-7] (Mac dinh: 1): "

if "%CHOICE%"=="" set "CHOICE=1"
if "%CHOICE%"=="1" goto CHECK_STATUS
if "%CHOICE%"=="2" goto LIVE_LOGS
if "%CHOICE%"=="3" goto RESTART_SERVER
if "%CHOICE%"=="4" goto START_SERVER
if "%CHOICE%"=="5" goto STOP_SERVER
if "%CHOICE%"=="6" goto LIVE_MONITOR
if "%CHOICE%"=="7" goto CONNECT_SSH
if "%CHOICE%"=="0" goto EXIT_SCRIPT

echo.
echo [!] Lua chon khong hop le, vui long nhap tu 0 den 7.
timeout /t 2 >nul
goto MENU

:: ============================================================
:: CAC CHUC NANG
:: ============================================================

:LIVE_LOGS
cls
echo ===============================================================================
echo   DANG THEO DOI LOG REAL-TIME (Nhan Ctrl+C de dung xem va quay lai menu)
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "if [ -f /home/ubuntu/nso-server/logs/service.log ]; then tail -f /home/ubuntu/nso-server/logs/service.log; else sudo journalctl -u nso-server.service -f; fi"
goto CHECK_STATUS

:RESTART_SERVER
echo.
echo >> Dang khoi dong lai dich vu nso-server.service tren VM...
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl restart nso-server.service && echo '>> Da khoi dong lai thanh cong! Dang doi 3 giay de kiem tra lai...' && sleep 3"
goto CHECK_STATUS

:START_SERVER
echo.
echo >> Dang bat dich vu nso-server.service tren VM...
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl start nso-server.service && echo '>> Da bat server thanh cong! Dang doi 3 giay de kiem tra lai...' && sleep 3"
goto CHECK_STATUS

:STOP_SERVER
echo.
echo >> Dang dung dich vu nso-server.service tren VM...
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl stop nso-server.service && echo '>> Da dung server thanh cong!'"
echo.
pause
goto CHECK_STATUS

:LIVE_MONITOR
cls
echo ===============================================================================
echo   THEO DOI TAI NGUYEN VM (CPU / RAM / DISK / UPTIME) - Nhan Ctrl+C de thoat
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "while true; do clear; echo '=================================================='; echo \" VM: \$(hostname) | DATE: \$(date '+%Y-%m-%d %H:%M:%S')\"; echo '=================================================='; echo '--- 1. RAM & SWAP ---'; free -h; echo; echo '--- 2. DISK ROOT ---'; df -h /; echo; echo '--- 3. UPTIME & LOAD ---'; uptime; echo; echo '--- 4. NSO PROCESS & PORTS ---'; sudo ss -tulnp | grep -E '14444|8020|3306'; echo; echo '[Cap nhat moi 3s - Nhan Ctrl+C de thoat]'; sleep 3; done"
goto CHECK_STATUS

:CONNECT_SSH
cls
echo ===============================================================================
echo   DANG KET NOI SSH TRUC TIEP VAO VM... (Go 'exit' de quay lai)
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP%
goto CHECK_STATUS

:EXIT_SCRIPT
echo.
echo Cam on ban da su dung script!
exit /b 0
