@echo off
REM ============================================================================
REM SMART PARKING MANAGEMENT SYSTEM - LAUNCHER (WINDOWS)
REM ============================================================================
REM This is the ONLY file you need to run to start the entire application
REM Just double-click this file or run: run.bat
REM ============================================================================

setlocal enabledelayedexpansion

set "PROJECT_DIR=%~dp0"
set "JAR_FILE=%PROJECT_DIR%SmartParking.jar"
set "JDBC_JAR=%PROJECT_DIR%mysql-connector-java-8.0.33.jar"

cls
echo.
echo ╔════════════════════════════════════════════════════════════════════════╗
echo ║                                                                        ║
echo ║          SMART PARKING MANAGEMENT SYSTEM - LAUNCHER                  ║
echo ║                                                                        ║
echo ╚════════════════════════════════════════════════════════════════════════╝
echo.

REM Check if JAR file exists
if not exist "%JAR_FILE%" (
    echo ❌ Error: SmartParking.jar not found!
    echo Location: %JAR_FILE%
    pause
    exit /b 1
)

REM Check if JDBC driver exists
if not exist "%JDBC_JAR%" (
    echo ❌ Error: MySQL JDBC driver not found!
    echo Location: %JDBC_JAR%
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Java is not installed!
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| findstr /R "version"') do set JAVA_VERSION=%%i
echo ✅ Java found: %JAVA_VERSION%
echo.

echo 🚀 Starting Smart Parking System...
echo.

java -cp "%JDBC_JAR%;%JAR_FILE%" main.MainApp

pause

