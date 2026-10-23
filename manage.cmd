@echo off
chcp 65001 >nul
call "%~dp0manage.bat" %*
if errorlevel 1 (
    echo.
    echo [THONG BAO] Tien trinh da dung lai voi ma loi: %errorlevel%
    pause
)
