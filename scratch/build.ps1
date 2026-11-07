$jdkPath = (Resolve-Path "tools\jdk").Path
$env:JAVA_HOME = $jdkPath
$env:PATH = "$jdkPath\bin;" + $env:PATH
& "tools\maven\bin\mvn.cmd" $args

