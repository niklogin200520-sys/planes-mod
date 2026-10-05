@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo.
echo ╔════════════════════════════════════════════╗
echo ║     PLANES MOD COMPILER                    ║
echo ║     Minecraft 1.21.1 Fabric                ║
echo ╚════════════════════════════════════════════╝
echo.

REM Проверка Java
java -version >nul 2>&1
if errorlevel 1 (
    color 4F
    echo.
    echo ❌ ERROR: Java НЕ установлена!
    echo.
    echo Скачай Java 21:
    echo https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo ✓ Java найдена
echo.
echo Начинаю компиляцию... (это займет 2-3 минуты)
echo.

call gradlew.bat build

if errorlevel 1 (
    color 4F
    echo.
    echo ❌ Ошибка компиляции!
    echo.
    pause
    exit /b 1
)

color 2F
echo.
echo ╔════════════════════════════════════════════╗
echo ║  ✓ УСПЕШНО! МОД ГОТОВ!                    ║
echo ╚════════════════════════════════════════════╝
echo.
echo 📁 Файл находится:
echo    build\libs\planes-mod-1.0.0.jar
echo.
echo 🎮 Скопируй его в папку mods:
echo    C:\Users\%USERNAME%\.minecraft\mods\
echo.
echo 📖 Затем запусти Minecraft с Fabric 1.21.1
echo.
pause
