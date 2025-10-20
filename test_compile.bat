@echo off
echo Testing Java compilation...
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

echo Java version:
java -version

echo.
echo Testing Gradle wrapper:
gradlew.bat --version

echo.
echo Testing compilation:
gradlew.bat compileJava compileClientJava --info

pause
