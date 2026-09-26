@echo off
setlocal
title Sao Luu Database NSO Server Ve May Tinh
color 0A

echo ============================================================
echo   SAO LUU CO SO DU LIEU NSO SERVER VE MAY TINH CA NHAN
echo ============================================================
echo.

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0backup_db_to_local.ps1"

echo.
pause
