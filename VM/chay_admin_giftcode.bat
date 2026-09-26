@echo off
setlocal
title NSO Server - Quan Ly Giftcode ^& OTP
color 0B

echo ============================================================
echo   KHOI CHAY HE THONG QUAN LY GIFTCODE ^& OTP TRUC TIEP
echo ============================================================
echo.

cd /d "%~dp0"
set "NODE_PATH=%~dp0..\web\node_modules"

echo Dang khoi dong may chu backend quan tri tai http://localhost:4000 ...
start "" http://localhost:4000

node admin_server.js

pause
