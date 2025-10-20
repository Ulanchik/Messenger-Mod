@echo off
echo Building MessengerMod...
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

echo Java version:
java -version

echo.
echo Generating Protobuf files...
gradlew.bat generateProto

echo.
echo Compiling project...
gradlew.bat compileJava compileClientJava

echo.
echo Building JAR...
gradlew.bat build

echo.
echo Build completed! Check build/libs/ for the JAR file.
pause
