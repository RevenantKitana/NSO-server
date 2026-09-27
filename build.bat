@echo off
chcp 65001 >nul
setlocal

if exist "%~dp0tools\jdk\bin\javac.exe" (
    if exist "%~dp0tools\maven\bin\mvn.cmd" (
        set "JAVA_HOME=%~dp0tools\jdk"
        set "PATH=%~dp0tools\maven\bin;%~dp0tools\jdk\bin;%PATH%"
    )
)

call mvn clean package -DskipTests
pause