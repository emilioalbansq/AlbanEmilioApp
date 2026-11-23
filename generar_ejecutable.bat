@echo off
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\entidades\*.java src\datos\*.java src\controladores\*.java src\interfaz\*.java
if %errorlevel% neq 0 (
    echo Error al compilar. Verifica que tengas el JDK instalado.
    pause
    exit /b %errorlevel%
)
xcopy /E /I /Y src\recursos bin\recursos > nul
echo Main-Class: interfaz.Principal> manifiesto.txt
jar cfm AulEmi.jar manifiesto.txt -C bin .
del manifiesto.txt
echo.
echo ========================================================
echo  Ejecutable generado con exito: AulEmi.jar
echo  Puedes abrirlo con doble clic o con: java -jar AulEmi.jar
echo ========================================================
pause
