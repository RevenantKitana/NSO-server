$cmd = $args -join ' '
if (-not $cmd) {
    Write-Error "No command provided"
    exit 1
}
$bytes = [System.Text.Encoding]::UTF8.GetBytes($cmd)
$b64 = [System.Convert]::ToBase64String($bytes)
$remoteCmd = "echo $b64 | base64 -d | bash"
& ssh -n -i config/161.118.209.14.key ubuntu@161.118.209.14 $remoteCmd
