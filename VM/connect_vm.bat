@echo off
setlocal

title NSO Server VM - SSH
color 0A

:: ==============================
:: CONFIG
:: ==============================

set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"

:: ==============================
:: CHECK KEY
:: ==============================

if not exist "%KEY_PATH%" (
    echo [ERROR] SSH key not found:
    echo %KEY_PATH%
    pause
    exit /b 1
)

:: ==============================
:: SSH
:: ==============================

echo.
echo  Connecting to %VM_USER%@%VM_IP% ...
echo.

ssh -t ^
    -i "%KEY_PATH%" ^
    -o StrictHostKeyChecking=no ^
    -o ServerAliveInterval=30 ^
    -o ServerAliveCountMax=3 ^
    %VM_USER%@%VM_IP%

echo.
echo  Session closed.
pause
