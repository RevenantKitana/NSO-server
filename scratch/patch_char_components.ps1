$path = "src/main/java/com/nsoz2/model/Char.java"
$content = [System.IO.File]::ReadAllText($path, [System.Text.Encoding]::UTF8)

$pattern = '(?m)^\s*public\s+Char\(int\s+id\)\s*\{\r?\n\s*this\.id\s*=\s*id;'
if ($content -match $pattern) {
    Write-Host "[OK] Regex matched!"
    $replacement = @"
    // 5 Domain Components (Milestone 3 Architecture)
    public SpatialComponent spatial;
    public CharacterStateComponent state;
    public InventoryComponent inventory;
    public CombatComponent combat;
    public SocialComponent social;

    public Char(int id) {
        this.id = id;
        this.spatial = new SpatialComponent(this);
        this.state = new CharacterStateComponent(this);
        this.inventory = new InventoryComponent(this);
        this.combat = new CombatComponent(this);
        this.social = new SocialComponent(this);
"@
    $content = [System.Text.RegularExpressions.Regex]::Replace($content, $pattern, $replacement)
    $utf8NoBom = New-Object System.Text.UTF8Encoding($false)
    [System.IO.File]::WriteAllText($path, $content, $utf8NoBom)
    Write-Host "[SUCCESS] Updated Char.java with components!"
} else {
    Write-Warning "Regex did not match!"
}
