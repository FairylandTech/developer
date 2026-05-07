@echo off
REM ============================================================================
REM Spring DDD 项目名称修改脚本 (Windows)
REM 功能: 将项目名称从 quickstart 修改为指定的新名称
REM 用法: rename.bat <new-project-name>
REM 示例: rename.bat web-move
REM ============================================================================

setlocal enabledelayedexpansion
chcp 65001 >nul 2>&1

REM 检查参数
if "%1"=="" (
    echo 错误: 请提供新的项目名称
    echo 用法: rename.bat ^<new-project-name^>
    echo 示例: rename.bat web-move
    exit /b 1
)

set OLD_NAME=quickstart
set NEW_NAME=%1
set SCRIPT_DIR=%~dp0

echo ============================================================================
echo Spring DDD 项目名称修改脚本
echo ============================================================================
echo 旧项目名称: %OLD_NAME%
echo 新项目名称: %NEW_NAME%
echo 项目路径: %SCRIPT_DIR%
echo ============================================================================
echo.

REM 检查目录是否存在
if not exist "%SCRIPT_DIR%\%OLD_NAME%-bootstrap" (
    echo 错误: 项目目录不存在或项目名称不是 %OLD_NAME%
    exit /b 1
)

echo [1/3] 重命名目录...
if exist "%SCRIPT_DIR%\%OLD_NAME%-bootstrap" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-bootstrap" "%NEW_NAME%-bootstrap"
    echo   ✓ %OLD_NAME%-bootstrap → %NEW_NAME%-bootstrap
)
if exist "%SCRIPT_DIR%\%OLD_NAME%-interface" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-interface" "%NEW_NAME%-interface"
    echo   ✓ %OLD_NAME%-interface → %NEW_NAME%-interface
)
if exist "%SCRIPT_DIR%\%OLD_NAME%-facade" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-facade" "%NEW_NAME%-facade"
    echo   ✓ %OLD_NAME%-facade → %NEW_NAME%-facade
)
if exist "%SCRIPT_DIR%\%OLD_NAME%-application" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-application" "%NEW_NAME%-application"
    echo   ✓ %OLD_NAME%-application → %NEW_NAME%-application
)
if exist "%SCRIPT_DIR%\%OLD_NAME%-domain" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-domain" "%NEW_NAME%-domain"
    echo   ✓ %OLD_NAME%-domain → %NEW_NAME%-domain
)
if exist "%SCRIPT_DIR%\%OLD_NAME%-infrastructure" (
    ren "%SCRIPT_DIR%\%OLD_NAME%-infrastructure" "%NEW_NAME%-infrastructure"
    echo   ✓ %OLD_NAME%-infrastructure → %NEW_NAME%-infrastructure
)
echo.

echo [2/3] 修改根目录 settings.gradle.kts...
set SETTINGS_FILE=%SCRIPT_DIR%settings.gradle.kts
if exist "!SETTINGS_FILE!" (
    powershell -NoProfile -Command ^
        "$content = [System.IO.File]::ReadAllText('%SETTINGS_FILE%', [System.Text.Encoding]::UTF8); " ^
        "$content = $content.Replace('rootProject.name = \"%OLD_NAME%\"', 'rootProject.name = \"%NEW_NAME%\"'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-bootstrap\")', 'include(\"%NEW_NAME%-bootstrap\")'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-interface\")', 'include(\"%NEW_NAME%-interface\")'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-facade\")', 'include(\"%NEW_NAME%-facade\")'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-application\")', 'include(\"%NEW_NAME%-application\")'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-domain\")', 'include(\"%NEW_NAME%-domain\")'); " ^
        "$content = $content.Replace('include(\"%OLD_NAME%-infrastructure\")', 'include(\"%NEW_NAME%-infrastructure\")'); " ^
        "[System.IO.File]::WriteAllText('%SETTINGS_FILE%', $content, [System.Text.Encoding]::UTF8)"
    echo   ✓ settings.gradle.kts 已更新
) else (
    echo   ✗ settings.gradle.kts 不存在
)
echo.

echo [3/3] 修改所有 build.gradle.kts 文件...
REM 修改根目录 build.gradle.kts
set BUILD_FILE=%SCRIPT_DIR%build.gradle.kts
if exist "!BUILD_FILE!" (
    powershell -NoProfile -Command ^
        "$content = [System.IO.File]::ReadAllText('%BUILD_FILE%', [System.Text.Encoding]::UTF8); " ^
        "$content = $content.Replace('description = \"%OLD_NAME%-parent\"', 'description = \"%NEW_NAME%-parent\"'); " ^
        "[System.IO.File]::WriteAllText('%BUILD_FILE%', $content, [System.Text.Encoding]::UTF8)"
    echo   ✓ build.gradle.kts (根目录) 已更新
)

REM 修改各子模块 build.gradle.kts
for %%D in (
    "%NEW_NAME%-bootstrap"
    "%NEW_NAME%-interface"
    "%NEW_NAME%-facade"
    "%NEW_NAME%-application"
    "%NEW_NAME%-domain"
    "%NEW_NAME%-infrastructure"
) do (
    set MODULE_BUILD=!SCRIPT_DIR!%%~D\build.gradle.kts
    if exist "!MODULE_BUILD!" (
        powershell -NoProfile -Command ^
            "$content = [System.IO.File]::ReadAllText('!MODULE_BUILD!', [System.Text.Encoding]::UTF8); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-interface\")', 'project(\":%NEW_NAME%-interface\")'); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-application\")', 'project(\":%NEW_NAME%-application\")'); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-infrastructure\")', 'project(\":%NEW_NAME%-infrastructure\")'); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-domain\")', 'project(\":%NEW_NAME%-domain\")'); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-bootstrap\")', 'project(\":%NEW_NAME%-bootstrap\")'); " ^
            "$content = $content.Replace('project(\":%OLD_NAME%-facade\")', 'project(\":%NEW_NAME%-facade\")'); " ^
            "[System.IO.File]::WriteAllText('!MODULE_BUILD!', $content, [System.Text.Encoding]::UTF8)"
        echo   ✓ build.gradle.kts (%%~D) 已更新
    )
)
echo.

echo ============================================================================
echo ✓ 项目名称修改完成！
echo ============================================================================
echo 下一步建议:
echo   1. 在新项目目录下运行: gradlew clean build
echo   2. 更新 IDEA/IDE 以重新加载项目配置
echo ============================================================================
endlocal
exit /b 0
