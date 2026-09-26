@echo off
setlocal
title Tao Ma OTP Dang Ky NSO Server
color 0E

set "VM_IP=161.118.202.174"
set "VM_USER=ubuntu"
set "KEY_PATH=%~dp0ssh-key-2026-09-26.key"

echo.
echo  Dang ket noi toi backend VM de tao ma OTP...
echo.

ssh -t -i "%KEY_PATH%" -o StrictHostKeyChecking=no %VM_USER%@%VM_IP% "sudo /home/ubuntu/nso-server/scripts/gen_otp.sh"

echo.
pause
