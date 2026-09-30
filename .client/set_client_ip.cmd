@echo off
setlocal EnableDelayedExpansion

REM Chuyen tiep toi script chuan hoa duy nhat trong scripts\local\set_client_ip.cmd
set "SCRIPT_DIR=%~dp0"
if "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR:~0,-1%"

if exist "%SCRIPT_DIR%\..\scripts\local\set_client_ip.cmd" (
    call "%SCRIPT_DIR%\..\scripts\local\set_client_ip.cmd" %*
) else (
    echo [LOI] Khong tim thay scripts\local\set_client_ip.cmd!
    if "%~1"=="" pause
)
