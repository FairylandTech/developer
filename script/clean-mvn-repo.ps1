Get-ChildItem -Path "D:\DevelopTools\Repository\maven" -Recurse -Directory | Where-Object { Get-ChildItem $_.FullName -Filter "*.lastUpdated" } | Remove-Item -Recurse -Force
Get-ChildItem -Path $env:USERPROFILE\.m2 -Recurse -Directory | Where-Object { Get-ChildItem $_.FullName -Filter "*.lastUpdated" } | Remove-Item -Recurse -Force
