@echo off
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\entidades\*.java src\datos\*.java src\controladores\*.java src\interfaz\*.java
if %errorlevel% neq 0 (
    echo Error al compilar. Verifica que tengas el JDK instalado.
    pause
    exit /b %errorlevel%
)
java -cp bin interfaz.Principal
