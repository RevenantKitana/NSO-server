@echo off
chcp 65001 >nul
call "%~dp0manage-alpine.bat" %*
if errorlevel 1 (
    echo.
    echo [THONG BAO] Tien trinh Alpine Toolkit da dung lai voi ma loi: %errorlevel%
    pause
)

