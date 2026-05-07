#Requires -Version 5.0
<#
.SYNOPSIS
    Spring Cloud 项目名称修改脚本 (PowerShell)
    
.DESCRIPTION
    将项目名称从 quickstart 修改为指定的新名称
    
.PARAMETER NewProjectName
    新的项目名称
    
.EXAMPLE
    .\rename.ps1 -NewProjectName "spring-cloud-quickstart"
    或
    .\rename.ps1 spring-cloud-quickstart
#>

param(
    [Parameter(Mandatory=$true, Position=0)]
    [string]$NewProjectName
)

$ErrorActionPreference = "Stop"
$VerbosePreference = "Continue"

# 配置
$OLD_NAME = "quickstart"
$NEW_NAME = $NewProjectName
$SCRIPT_DIR = Split-Path -Parent $PSCommandPath

Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "Spring Cloud 项目名称修改脚本" -ForegroundColor Cyan
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "旧项目名称: $OLD_NAME"
Write-Host "新项目名称: $NEW_NAME"
Write-Host "项目路径: $SCRIPT_DIR"
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host ""

# 检查目录是否存在
$bootstrapDir = Join-Path $SCRIPT_DIR "$OLD_NAME-bootstrap"
if (-not (Test-Path $bootstrapDir)) {
    Write-Error "错误: 项目目录不存在，请确保项目名称是 $OLD_NAME"
    exit 1
}

# Step 1: 重命名目录
Write-Host "[1/3] 重命名目录..." -ForegroundColor Yellow
$dirs = @(
    "$OLD_NAME-bootstrap"
    "$OLD_NAME-interface"
    "$OLD_NAME-facade"
    "$OLD_NAME-application"
    "$OLD_NAME-domain"
    "$OLD_NAME-infrastructure"
    "$OLD_NAME-common"
)

foreach ($dir in $dirs) {
    $oldPath = Join-Path $SCRIPT_DIR $dir
    $newPath = Join-Path $SCRIPT_DIR $dir.Replace($OLD_NAME, $NEW_NAME)
    
    if (Test-Path $oldPath) {
        try {
            Rename-Item -Path $oldPath -NewName (Split-Path $newPath -Leaf) -ErrorAction Stop
            Write-Host "  ✓ $dir → $(Split-Path $newPath -Leaf)" -ForegroundColor Green
        }
        catch {
            Write-Host "  ✗ 无法重命名 $dir : $_" -ForegroundColor Red
        }
    }
}
Write-Host ""

# Helper 函数: 替换文件内容
function Update-FileContent {
    param(
        [string]$FilePath,
        [string]$OldText,
        [string]$NewText
    )
    
    if (Test-Path $FilePath) {
        try {
            $content = [System.IO.File]::ReadAllText($FilePath, [System.Text.Encoding]::UTF8)
            $newContent = $content.Replace($OldText, $NewText)
            [System.IO.File]::WriteAllText($FilePath, $newContent, [System.Text.Encoding]::UTF8)
            return $true
        }
        catch {
            Write-Host "  警告: 无法更新 $([System.IO.Path]::GetFileName($FilePath)): $_" -ForegroundColor Yellow
            return $false
        }
    }
    return $false
}

# Step 2: 修改配置文件
Write-Host "[2/3] 修改配置文件..." -ForegroundColor Yellow

$configFiles = @(
    "settings.gradle.kts"
    "build.gradle.kts"
    "gradle.properties"
)

foreach ($file in $configFiles) {
    $filePath = Join-Path $SCRIPT_DIR $file
    if (Update-FileContent $filePath $OLD_NAME $NEW_NAME) {
        Write-Host "  ✓ $file 已更新" -ForegroundColor Green
    }
}
Write-Host ""

# Step 3: 修改各子模块文件
Write-Host "[3/3] 修改子模块配置文件..." -ForegroundColor Yellow

$newDirs = @(
    "$NEW_NAME-bootstrap"
    "$NEW_NAME-interface"
    "$NEW_NAME-facade"
    "$NEW_NAME-application"
    "$NEW_NAME-domain"
    "$NEW_NAME-infrastructure"
    "$NEW_NAME-common"
)

foreach ($dir in $newDirs) {
    $modulePath = Join-Path $SCRIPT_DIR $dir
    if (Test-Path $modulePath) {
        $buildFile = Join-Path $modulePath "build.gradle.kts"
        if (Update-FileContent $buildFile $OLD_NAME $NEW_NAME) {
            Write-Host "  ✓ $dir/build.gradle.kts 已更新" -ForegroundColor Green
        }
    }
}

Write-Host ""
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "✓ 项目名称修改完成！" -ForegroundColor Green
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "下一步建议:" -ForegroundColor Yellow
Write-Host "  1. 运行: .\gradlew clean build"
Write-Host "  2. 在 IDE 中重新加载项目"
Write-Host "============================================================================" -ForegroundColor Cyan
