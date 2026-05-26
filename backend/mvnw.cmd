@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup script for Windows
@REM ----------------------------------------------------------------------------
@echo off
setlocal

set "MAVEN_WRAPPER_DIR=%~dp0\.mvn\wrapper"
set "MAVEN_WRAPPER_JAR=%MAVEN_WRAPPER_DIR%\maven-wrapper.jar"
set "MAVEN_WRAPPER_PROPERTIES=%MAVEN_WRAPPER_DIR%\maven-wrapper.properties"

if not exist "%MAVEN_WRAPPER_JAR%" (
  echo Maven Wrapper jar not found: "%MAVEN_WRAPPER_JAR%"
  echo Please make sure it exists, or re-run the setup step.
  exit /b 1
)

set "JAVA_EXE=java.exe"

"%JAVA_EXE%" -classpath "%MAVEN_WRAPPER_JAR%" -Dmaven.multiModuleProjectDirectory="%~dp0" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal

