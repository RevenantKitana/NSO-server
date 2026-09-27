@echo off
chcp 65001 >nul
setlocal
title Cai Dat JDK 17 va Maven Portable (Cuc Bo)
color 0B

echo ============================================================
echo   CAI DAT JDK 17 VA MAVEN PORTABLE CHO MAY TINH CA NHAN
echo   (100%% Cuc bo, Khong cai vao Windows, Khong dung den PATH)
echo ============================================================
echo.

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0setup_portable_tools.ps1"

if errorlevel 1 (
    echo.
    echo [LOI] Qua trinh cai dat portable tools gap su co!
    echo.
)

echo.
pause
