@echo off
setlocal
title Update NSO Server on VM via Git
color 0B

set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"

echo.
echo ============================================================
echo   UPDATING NSO SERVER ON VM VIA GITHUB
echo ============================================================
echo.

ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "/home/ubuntu/update.sh"

echo.
echo ============================================================
echo   DONE!
echo ============================================================
pause
