@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion
title NSO Server - Cloud VM Management Hub
color 0B

set "ROOT_DIR=%~dp0"
if "%ROOT_DIR:~-1%"=="\" set "ROOT_DIR=%ROOT_DIR:~0,-1%"

:LOAD_CONFIG
set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "VM_PORT=14444"
set "SSH_KEY_REL=config\ssh-key-2026-09-26.key"
set "WEB_PORT=4000"

set "CONFIG_FILE=%ROOT_DIR%\config\server_config.ini"
if exist "%CONFIG_FILE%" (
    for /f "usebackq tokens=1,* delims==" %%A in ("%CONFIG_FILE%") do (
        set "KEY=%%A"
        set "VAL=%%B"
        if not "!KEY:~0,1!"=="#" (
            if /i "!KEY!"=="VM_IP" set "VM_IP=!VAL!"
            if /i "!KEY!"=="VM_USER" set "VM_USER=!VAL!"
            if /i "!KEY!"=="VM_PORT" set "VM_PORT=!VAL!"
            if /i "!KEY!"=="SSH_KEY" set "SSH_KEY_REL=!VAL!"
            if /i "!KEY!"=="WEB_PORT" set "WEB_PORT=!VAL!"
        )
    )
)

set "KEY_PATH=%ROOT_DIR%\%SSH_KEY_REL%"

:MAIN_MENU
cls
echo ===============================================================================
echo                NSO SERVER - BẢNG ĐIỀU KHIỂN CLOUD VM CHUYÊN NGHIỆP
echo ===============================================================================
echo   Cloud VM: %VM_USER%@%VM_IP% ^| Game Port: %VM_PORT%
echo   SSH Key : %SSH_KEY_REL%
echo ===============================================================================
echo.
echo   --- [ VẬN HÀNH & TRIỂN KHAI CLOUD VM ] ---
echo   [1]  1-Click Build & Deploy lên VM (Build Local -> Upload -> Restart Service)
echo   [2]  Bảng điều khiển VM (Kiểm tra Status / Live Logs / Restart Service)
echo   [3]  Mở SSH Terminal kết nối trực tiếp vào VM
echo   [4]  Theo dõi tài nguyên VM thời gian thực (CPU / RAM / Disk / Uptime)
echo   [5]  Tải bản sao lưu Database từ VM về máy cá nhân (Thư mục backups/)
echo   [6]  Khởi chạy Web Quản trị Admin (Tạo Giftcode & Mã OTP qua Web)
echo.
echo   --- [ THIẾT LẬP MÁY CHỦ MỚI ] ---
echo   [7]  ⚡ Cài đặt VM mới tinh từ A-Z (Cài Java, MariaDB, Swap, Database, Data)
echo.
echo   --- [ CÔNG CỤ CLIENT & MÔI TRƯỜNG BUILD ] ---
echo   [8]  Cấu hình IP / Port cho Game Client JAR (.client/)
echo   [9]  Tải & Thiết lập JDK 17 + Maven Portable (100%% Tự động)
echo   [10] Biên dịch thử nghiệm trên máy (Test Maven Build)
echo.
echo   --- [ HỆ THỐNG ] ---
echo   [11] Chỉnh sửa cấu hình nhanh (Đổi IP VM, Port, SSH Key...)
echo   [0]  Thoát
echo ===============================================================================
set "OPT="
set /p "OPT=>> Nhập lựa chọn của bạn [0-11]: "

if "%OPT%"=="1" goto :DEPLOY_VM
if "%OPT%"=="2" goto :VM_CONTROL
if "%OPT%"=="3" goto :SSH_SHELL
if "%OPT%"=="4" goto :VM_MONITOR
if "%OPT%"=="5" goto :BACKUP_DB
if "%OPT%"=="6" goto :ADMIN_PORTAL
if "%OPT%"=="7" goto :BOOTSTRAP_VM
if "%OPT%"=="8" goto :PATCH_CLIENT
if "%OPT%"=="9" goto :SETUP_TOOLS
if "%OPT%"=="10" goto :LOCAL_BUILD
if "%OPT%"=="11" goto :EDIT_CONFIG
if "%OPT%"=="0" exit /b 0

echo [!] Lựa chọn không hợp lệ. Vui lòng nhập từ 0 đến 11.
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
echo [!] Chưa tìm thấy JDK 17 & Maven. Đang tự động tải bộ Portable...
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
echo            QUY TRÌNH 1-CLICK BUILD & DEPLOY LÊN VM CLOUD
echo ===============================================================================
echo   >> Máy chủ đích : %VM_USER%@%VM_IP%
echo   >> Key xác thực : %KEY_PATH%
echo ===============================================================================
echo.

if not exist "%KEY_PATH%" (
    echo [LỖI] Không tìm thấy file SSH Key: %KEY_PATH%
    pause
    goto :MAIN_MENU
)

echo [BƯỚC 1/4] Biên dịch dự án trên máy cá nhân...
call :CHECK_LOCAL_ENV
cd /d "%ROOT_DIR%"
call mvn clean package -DskipTests
if not exist "%ROOT_DIR%\target\Nso-jar-with-dependencies.jar" (
    echo.
    echo [LỖI] Build thất bại! Không tìm thấy file target\Nso-jar-with-dependencies.jar.
    pause
    goto :MAIN_MENU
)

echo.
echo [BƯỚC 2/4] Sao lưu bản JAR cũ trên VM...
ssh -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "mkdir -p /home/ubuntu/nso-server/backups; if [ -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar ]; then cp -f /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar /home/ubuntu/nso-server/backups/Nso_backup_\$(date +%%Y%%m%%d_%%H%%M%%S).jar; echo '>> Đã sao lưu bản JAR cũ thành công.'; ls -1t /home/ubuntu/nso-server/backups/Nso_backup_*.jar 2>/dev/null | tail -n +4 | xargs -r rm -f; else echo '>> Chưa có file JAR cũ trên VM (Cài đặt mới).'; fi"

echo.
echo [BƯỚC 3/4] Tải file JAR mới lên VM (%VM_IP%)...
scp -i "%KEY_PATH%" -o StrictHostKeyChecking=no -o ConnectTimeout=10 "%ROOT_DIR%\target\Nso-jar-with-dependencies.jar" %VM_USER%@%VM_IP%:/home/ubuntu/nso-server/Nso-jar-with-dependencies.jar
if errorlevel 1 (
    echo [LỖI] Không thể upload file JAR lên VM! Vui lòng kiểm tra kết nối mạng.
    pause
    goto :MAIN_MENU
)

echo.
echo [BƯỚC 4/4] Khởi động lại dịch vụ nso-server trên VM...
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl restart nso-server.service && sleep 3 && sudo systemctl status nso-server.service --no-pager && echo '' && echo '=== CÁC CỔNG ĐANG MỞ (PORTS) ===' && sudo ss -tulnp | grep -E '14444|8020|3306'"

echo.
echo ===============================================================================
echo   HOÀN TẤT BUILD VÀ DEPLOY LÊN VM THÀNH CÔNG!
echo ===============================================================================
echo.
pause
goto :MAIN_MENU

:: ===============================================================================
:: 2. VM CONTROL MENU
:: ===============================================================================
:VM_CONTROL
cls
if not exist "%KEY_PATH%" (
    echo [LỖI] Không tìm thấy file SSH Key: %KEY_PATH%
    pause
    goto :MAIN_MENU
)

echo ===============================================================================
echo            ĐANG KIỂM TRA TRẠNG THÁI MÁY CHỦ: %VM_USER%@%VM_IP%
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no -o ConnectTimeout=8 %VM_USER%@%VM_IP% "if [ -f /home/ubuntu/check_status.sh ]; then /home/ubuntu/check_status.sh; else bash -c 'echo \"=== 1. SYSTEMD SERVICE ===\"; sudo systemctl status nso-server.service --no-pager; echo \"=== 2. PORTS ===\"; sudo ss -tulnp | grep -E \"14444|8020|3306\"; echo \"=== 3. RAM & DISK ===\"; free -h; df -h /; echo \"=== 4. LOGS ===\"; tail -n 10 /home/ubuntu/nso-server/logs/service.log 2>/dev/null || true'; fi"

:VM_SUBMENU
echo.
echo ===============================================================================
echo                           THAO TÁC NHANH VỚI VM
echo ===============================================================================
echo   [1] Làm mới / Kiểm tra lại trạng thái
echo   [2] Xem Live Log máy chủ (tail -f logs/service.log)
echo   [3] Khởi động lại Server Game (Restart nso-server.service)
echo   [4] Bật Server Game (Start nso-server.service)
echo   [5] Dừng Server Game (Stop nso-server.service)
echo   [0] Quay lại Menu chính
echo ===============================================================================
set "VM_OPT="
set /p "VM_OPT=>> Nhập lựa chọn [0-5]: "

if "%VM_OPT%"=="1" goto :VM_CONTROL
if "%VM_OPT%"=="2" (
    cls
    echo [*] Đang theo dõi Live Log (Nhấn Ctrl+C để dừng và quay lại)...
    ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "if [ -f /home/ubuntu/nso-server/logs/service.log ]; then tail -f /home/ubuntu/nso-server/logs/service.log; else sudo journalctl -u nso-server.service -f; fi"
    goto :VM_CONTROL
)
if "%VM_OPT%"=="3" (
    echo.
    echo >> Đang restart nso-server.service...
    ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl restart nso-server.service && sleep 2"
    goto :VM_CONTROL
)
if "%VM_OPT%"=="4" (
    echo.
    echo >> Đang start nso-server.service...
    ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl start nso-server.service && sleep 2"
    goto :VM_CONTROL
)
if "%VM_OPT%"=="5" (
    echo.
    echo >> Đang stop nso-server.service...
    ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo systemctl stop nso-server.service"
    pause
    goto :VM_CONTROL
)
if "%VM_OPT%"=="0" goto :MAIN_MENU

goto :VM_SUBMENU

:: ===============================================================================
:: 3. SSH SHELL
:: ===============================================================================
:SSH_SHELL
cls
if not exist "%KEY_PATH%" (
    echo [LỖI] Không tìm thấy file SSH Key: %KEY_PATH%
    pause
    goto :MAIN_MENU
)
echo ===============================================================================
echo   ĐANG MỞ SSH TERMINAL TỚI %VM_USER%@%VM_IP%... (Gõ 'exit' để thoát)
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP%
goto :MAIN_MENU

:: ===============================================================================
:: 4. VM RESOURCE MONITOR
:: ===============================================================================
:VM_MONITOR
cls
if not exist "%KEY_PATH%" (
    echo [LỖI] Không tìm thấy file SSH Key: %KEY_PATH%
    pause
    goto :MAIN_MENU
)
echo ===============================================================================
echo   THEO DÕI TÀI NGUYÊN VM THỜI GIAN THỰC (Nhấn Ctrl+C để thoát)
echo ===============================================================================
echo.
ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "while true; do clear; echo '=================================================='; echo \" VM: \$(hostname) | TIME: \$(date '+%Y-%m-%d %H:%M:%S')\"; echo '=================================================='; echo '--- 1. RAM & SWAP ---'; free -h; echo; echo '--- 2. DISK ROOT ---'; df -h /; echo; echo '--- 3. UPTIME & LOAD ---'; uptime; echo; echo '--- 4. NSO PROCESS & PORTS ---'; sudo ss -tulnp | grep -E '14444|8020|3306'; echo; echo '[Làm mới mỗi 3s - Nhấn Ctrl+C để dừng]'; sleep 3; done"
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
echo   KHỞI CHẠY TRANG QUẢN TRỊ ADMIN (GIFTCODE & MÃ OTP)
echo ===============================================================================
echo.
where node >nul 2>&1
if %errorlevel% neq 0 (
    echo [LỖI] Máy tính chưa cài đặt Node.js. Vui lòng cài Node.js để chạy web admin!
    pause
    goto :MAIN_MENU
)

echo [*] Đang mở trình duyệt tại: http://localhost:%WEB_PORT% ...
start "" "http://localhost:%WEB_PORT%"

echo [*] Khởi chạy backend admin server (Nhấn Ctrl+C để tắt server khi xong)...
echo.
node "%ROOT_DIR%\tools\admin-portal\admin_server.js"
goto :MAIN_MENU

:: ===============================================================================
:: 7. BOOTSTRAP FRESH VM (A-Z)
:: ===============================================================================
:BOOTSTRAP_VM
cls
echo ===============================================================================
echo    ⚡ CÀI ĐẶT TOÀN DIỆN MÁY CHỦ CLOUD VM MỚI TINH (BOOTSTRAP TỪ A-Z)
echo ===============================================================================
echo   >> Máy chủ đích : %VM_USER%@%VM_IP%
echo   >> Cảnh báo     : Thao tác này sẽ cài Java 17, MariaDB, Swap, upload Data
echo                     và nạp Database sạch lần đầu lên VM.
echo ===============================================================================
echo.
set /p "CONFIRM=>> Bạn có chắc chắn muốn tiến hành cài đặt [Y/N]? "
if /i not "%CONFIRM%"=="Y" (
    echo [*] Đã hủy thao tác cài đặt VM.
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
echo   ĐANG BIÊN DỊCH VÀ ĐÓNG GÓI NSO GAME SERVER (TEST BUILD)...
echo ===============================================================================
echo.
call :CHECK_LOCAL_ENV
cd /d "%ROOT_DIR%"
call mvn clean package -DskipTests
if %errorlevel% equ 0 (
    echo.
    echo [OK] Build thành công: target\Nso-jar-with-dependencies.jar
) else (
    echo.
    echo [LOI] Quá trình build gặp sự cố!
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
echo                      CẤU HÌNH HỆ THỐNG HIỆN TẠI
echo ===============================================================================
echo   [1] VM IP   : %VM_IP%
echo   [2] VM User : %VM_USER%
echo   [3] VM Port : %VM_PORT%
echo   [4] SSH Key : %SSH_KEY_REL%
echo   [5] Web Port: %WEB_PORT%
echo ===============================================================================
echo.
set /p "NEW_IP=>> Nhập VM IP mới (Bấm Enter để giữ nguyên '%VM_IP%'): "
if not "%NEW_IP%"=="" set "VM_IP=%NEW_IP%"

set /p "NEW_PORT=>> Nhập Game Port mới (Bấm Enter để giữ nguyên '%VM_PORT%'): "
if not "%NEW_PORT%"=="" set "VM_PORT=%NEW_PORT%"

set /p "NEW_KEY=>> Nhập đường dẫn SSH Key (Bấm Enter để giữ nguyên '%SSH_KEY_REL%'): "
if not "%NEW_KEY%"=="" set "SSH_KEY_REL=%NEW_KEY%"

(
    echo # ============================================================
    echo # NSO GAME SERVER - CAU HINH HE THONG ^& CLOUD VM
    echo # ============================================================
    echo.
    echo VM_IP=%VM_IP%
    echo VM_USER=%VM_USER%
    echo VM_PORT=%VM_PORT%
    echo SSH_KEY=%SSH_KEY_REL%
    echo WEB_PORT=%WEB_PORT%
) > "%CONFIG_FILE%"

echo.
echo [OK] Đã lưu cấu hình mới vào %CONFIG_FILE%!
timeout /t 2 >nul
goto :LOAD_CONFIG
