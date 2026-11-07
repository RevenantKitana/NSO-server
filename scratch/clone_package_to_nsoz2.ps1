$srcDir = "src/main/java/com/nsoz"
$destDir = "src/main/java/com/nsoz2"

Write-Host "[*] Cloning $srcDir -> $destDir..."
if (Test-Path $destDir) {
    Remove-Item -Recurse -Force $destDir
}

Copy-Item -Recurse -Force $srcDir $destDir

$files = Get-ChildItem -Path $destDir -Recurse -Filter *.java
Write-Host "[*] Found $($files.Count) Java files to transform in $destDir..."

$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::UTF8)
    
    # Strip any existing BOM
    $content = $content.TrimStart([char]0xFEFF)
    
    # Replace package declaration: package com.nsoz -> package com.nsoz2
    $content = $content -replace 'package\s+com\.nsoz\b', 'package com.nsoz2'
    
    # Replace imports: import com.nsoz. -> import com.nsoz2.
    $content = $content -replace 'import\s+com\.nsoz\.', 'import com.nsoz2.'
    $content = $content -replace 'import\s+static\s+com\.nsoz\.', 'import static com.nsoz2.'
    
    # Replace fully-qualified names: com.nsoz. -> com.nsoz2.
    $content = $content -replace '\bcom\.nsoz\.', 'com.nsoz2.'
    
    [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
}

Write-Host "[*] Checking for any remaining com.nsoz references in $destDir..."
$remains = Get-ChildItem -Path $destDir -Recurse -Filter *.java | Select-String "com\.nsoz\." | Where-Object { $_.Line -notmatch "com\.nsoz2\." }
if ($remains) {
    Write-Warning "Found $($remains.Count) remaining references:"
    $remains | ForEach-Object { Write-Warning "$($_.Filename):$($_.LineNumber): $($_.Line.Trim())" }
} else {
    Write-Host "[SUCCESS] 0 lingering com.nsoz references found in com.nsoz2!"
}
