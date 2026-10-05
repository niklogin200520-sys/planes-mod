@echo off
echo ============================================
echo Planes Mod Compiler
echo ============================================
echo.

REM Проверка Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java не установлена!
    echo Скачай Java 21 отсюда: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo [OK] Java найдена

REM Компиляция
echo.
echo Начинаю компиляцию...
call gradlew.bat build

if errorlevel 1 (
    echo.
    echo ERROR: Компиляция не удалась!
    pause
    exit /b 1
)

echo.
echo ============================================
echo SUCCESS! Мод готов!
echo ============================================
echo.
echo JAR файл: build\libs\planes-mod-1.0.0.jar
echo.
echo Скопируй этот файл в папку:
echo C:\Users\%USERNAME%\.minecraft\mods\
echo.
pause
