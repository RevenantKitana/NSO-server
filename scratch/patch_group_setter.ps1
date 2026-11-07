$path = "src/main/java/com/nsoz2/model/Char.java"
$content = [System.IO.File]::ReadAllText($path, [System.Text.Encoding]::UTF8)

$pattern = '(?m)^\s*@Getter\r?\n\s*private\s+Group\s+group;'
$replacement = @"
    @Getter
    @Setter
    private Group group;
"@

if ($content -match $pattern) {
    $content = [System.Text.RegularExpressions.Regex]::Replace($content, $pattern, $replacement)
    $utf8NoBom = New-Object System.Text.UTF8Encoding($false)
    [System.IO.File]::WriteAllText($path, $content, $utf8NoBom)
    Write-Host "[SUCCESS] Added @Setter to group in Char.java!"
} else {
    Write-Warning "Pattern not matched in $path"
}
