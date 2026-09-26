@echo off
setlocal

title NSO Server VM - Resource Monitor
color 0A

:: ==============================
:: CONFIG
:: ==============================

set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"
set "REFRESH_SEC=5"

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
:: SSH - LIVE MONITOR
:: ==============================

echo.
echo  Connecting to %VM_USER%@%VM_IP% (monitor mode, refresh %REFRESH_SEC%s)...
echo  Press Ctrl+C to stop.
echo.

ssh -t ^
    -i "%KEY_PATH%" ^
    -o StrictHostKeyChecking=no ^
    -o ServerAliveInterval=30 ^
    -o ServerAliveCountMax=3 ^
    %VM_USER%@%VM_IP% ^
    "while true; do clear; echo VM: $(hostname); echo TIME: $(date); echo; echo --- RAM --- ; free -h; echo; echo --- DISK --- ; df -h /; echo; echo --- UPTIME --- ; uptime -p; echo; echo --- CPU LOAD --- ; uptime; echo; echo [Refreshing every %REFRESH_SEC%s - Ctrl+C to stop]; sleep %REFRESH_SEC%; done"

echo.
echo  Monitor stopped.
pause
