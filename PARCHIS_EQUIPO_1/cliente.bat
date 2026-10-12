@echo off
cd /d "%~dp0"
if not exist target\classes mkdir target\classes
dir /s /b src\main\java\*.java > fuentes.txt
javac -encoding UTF-8 -d target\classes @fuentes.txt || exit /b 1
xcopy /e /i /y src\main\java\imagenes target\classes\imagenes >nul
java -cp target\classes VISTA.Interfaz
