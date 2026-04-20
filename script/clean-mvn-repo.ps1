param(
    [string]$repo
)

Write-Host "开始清理 Maven 仓库..."

$envMavenRepo = $env:MAVEN_REPOSITORY

if ($repo)
{
    $envMavenRepo = $repo
    Write-Host "使用传入的 --repo 路径: $envMavenRepo"
}
elseif (-not $envMavenRepo)
{
    $envMavenRepo = $env:USERPROFILE\.m2
    Write-Host "未找到 MAVEN_REPOSITORY 环境变量,使用默认路径: $envMavenRepo"
}
else
{
    Write-Host "使用 MAVEN_REPOSITORY 环境变量路径: $envMavenRepo"
}

Get-ChildItem -Path $envMavenRepo -Recurse -Directory | Where-Object { Get-ChildItem $_.FullName -Filter "*.lastUpdated" } | Remove-Item -Recurse -Force
Write-Host "清理 Maven 仓库完成"

Get-ChildItem -Path $env:USERPROFILE\.m2 -Recurse -Directory | Where-Object { Get-ChildItem $_.FullName -Filter "*.lastUpdated" } | Remove-Item -Recurse -Force
Write-Host "清理用户 Maven 仓库完成"
