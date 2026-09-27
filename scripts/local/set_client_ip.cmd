@echo off
setlocal EnableDelayedExpansion

REM -------------------------------------------------------------
REM Tool set nhanh IP & Port cho Game Client JAR (NSO)
REM Ho tro: Cloud VM, Localhost, Custom IP/Port, Backup/Restore
REM -------------------------------------------------------------

set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

set "ROOT_DIR=%SCRIPT_DIR%\..\.."
pushd "%ROOT_DIR%"
set "ROOT_DIR=%CD%"
popd

set "CLIENT_DIR=%ROOT_DIR%\.client"

REM Doc cau hinh mac dinh tu config\server_config.ini
set "VM_IP=161.118.202.174"
set "VM_PORT=14444"
if exist "%ROOT_DIR%\config\server_config.ini" (
    for /f "usebackq tokens=1,* delims==" %%A in ("%ROOT_DIR%\config\server_config.ini") do (
        set "KEY=%%A"
        set "VAL=%%B"
        if not "!KEY:~0,1!"=="#" (
            if /i "!KEY!"=="VM_IP" set "VM_IP=!VAL!"
            if /i "!KEY!"=="VM_PORT" set "VM_PORT=!VAL!"
        )
    )
)

REM Tim kiem Java runtime
set "JAVA_CMD="
set "JAVAC_CMD="

if exist "%ROOT_DIR%\tools\jdk\bin\java.exe" (
    set "JAVA_CMD=%ROOT_DIR%\tools\jdk\bin\java.exe"
    set "JAVAC_CMD=%ROOT_DIR%\tools\jdk\bin\javac.exe"
) else (
    where java >nul 2>&1
    if !errorlevel! equ 0 (
        set "JAVA_CMD=java"
        where javac >nul 2>&1
        if !errorlevel! equ 0 (
            set "JAVAC_CMD=javac"
        )
    )
)

if "%JAVA_CMD%"=="" (
    echo ============================================================
    echo [LOI] Khong tim thay Java!
    echo Vui long mo manage.bat va chon thiet lap JDK 17 Portable.
    echo ============================================================
    if "%~1"=="" pause
    exit /b 1
)

REM Bien dich PatchClient neu chua co class
if not exist "%CLIENT_DIR%\PatchClient.class" (
    if exist "%CLIENT_DIR%\PatchClient.java" (
        echo [*] Dang bien dich PatchClient.java...
        if not "%JAVAC_CMD%"=="" (
            "%JAVAC_CMD%" "%CLIENT_DIR%\PatchClient.java"
        ) else (
            "%JAVA_CMD%" "%CLIENT_DIR%\PatchClient.java"
        )
    )
)

REM Xu ly tham so CLI neu co
set "IS_INTERACTIVE=0"
if "%~1"=="" (
    set "IS_INTERACTIVE=1"
    goto :MENU
)

set "ARG1=%~1"
if /i "%ARG1%"=="restore" goto :DO_RESTORE
if /i "%ARG1%"=="bak" goto :DO_RESTORE

if /i "%ARG1%"=="vm" (
    set "TARGET_IP=%VM_IP%"
    set "TARGET_PORT=%VM_PORT%"
    goto :DO_PATCH
)

if /i "%ARG1%"=="local" (
    set "TARGET_IP=127.0.0.1"
    set "TARGET_PORT=%VM_PORT%"
    goto :DO_PATCH
)

set "TARGET_IP=%~1"
set "TARGET_PORT=%~2"
if "%TARGET_PORT%"=="" set "TARGET_PORT=%VM_PORT%"
goto :DO_PATCH

:MENU
cls
echo ============================================================
echo         CAU HINH IP / PORT CHO CLIENT JAR (NSO)
echo ============================================================
echo.
echo   [1] Ket noi Cloud VM     (%VM_IP% : %VM_PORT%)
echo   [2] Ket noi Localhost    (127.0.0.1       : %VM_PORT%)
echo   [3] Nhap IP va Port tuy chinh (Custom IP/Port)
echo   [4] Khoi phuc Client goc tu file Backup (.bak)
echo   [0] Thoat
echo.
echo ============================================================
set /p "CHOICE=>> Nhap lua chon cua ban [1-4, 0]: "

if "%CHOICE%"=="1" (
    set "TARGET_IP=%VM_IP%"
    set "TARGET_PORT=%VM_PORT%"
    goto :DO_PATCH
)
if "%CHOICE%"=="2" (
    set "TARGET_IP=127.0.0.1"
    set "TARGET_PORT=%VM_PORT%"
    goto :DO_PATCH
)
if "%CHOICE%"=="3" (
    echo.
    set /p "TARGET_IP=>> Nhap IP hoac Domain Server: "
    if "!TARGET_IP!"=="" (
        echo [!] IP khong duoc de trong!
        timeout /t 2 >nul
        goto :MENU
    )
    set /p "TARGET_PORT=>> Nhap Port Server (Mac dinh %VM_PORT%): "
    if "!TARGET_PORT!"=="" set "TARGET_PORT=%VM_PORT%"
    goto :DO_PATCH
)
if "%CHOICE%"=="4" (
    goto :DO_RESTORE
)
if "%CHOICE%"=="0" (
    exit /b 0
)

echo [!] Lua chon khong hop le!
timeout /t 1 >nul
goto :MENU

:DO_PATCH
echo.
echo [*] Dang ap dung cau hinh: IP = %TARGET_IP% ^| Port = %TARGET_PORT%...
echo.

"%JAVA_CMD%" -cp "%CLIENT_DIR%" PatchClient "%TARGET_IP%" "%TARGET_PORT%"
set "PATCH_EXIT=%errorlevel%"

if %PATCH_EXIT% equ 0 (
    echo.
    echo ============================================================
    echo [THANH CONG] Da cap nhat Client Jar thanh cong!
    echo Client da duoc dong bo de ket noi: %TARGET_IP%:%TARGET_PORT%
    echo Thu muc chua file: %CLIENT_DIR%
    echo ============================================================
) else (
    echo.
    echo [THAT BAI] Co loi xay ra trong qua trinh patch client jar.
)

if "%IS_INTERACTIVE%"=="1" (
    echo.
    pause
)
exit /b %PATCH_EXIT%

:DO_RESTORE
echo.
echo [*] Dang khoi phuc cac file Client goc tu backup (.bak)...
set "RESTORED=0"

if exist "%CLIENT_DIR%\JAR_local.jar.bak" (
    copy /y "%CLIENT_DIR%\JAR_local.jar.bak" "%CLIENT_DIR%\JAR_local.jar" >nul
    echo    [+] Da khoi phuc: JAR_local.jar
    set /a "RESTORED+=1"
)

if exist "%CLIENT_DIR%\NSO.jar.bak" (
    copy /y "%CLIENT_DIR%\NSO.jar.bak" "%CLIENT_DIR%\NSO.jar" >nul
    echo    [+] Da khoi phuc: NSO.jar
    set /a "RESTORED+=1"
)

if !RESTORED! gtr 0 (
    echo.
    echo [THANH CONG] Da khoi phuc !RESTORED! file client ve trang thai ban dau.
) else (
    echo.
    echo [!] Khong tim thay file .bak nao trong %CLIENT_DIR%.
)

if "%IS_INTERACTIVE%"=="1" (
    echo.
    pause
)
exit /b 0
