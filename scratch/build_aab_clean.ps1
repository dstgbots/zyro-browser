$protoApk = "c:\android-Zyro-browser-main\scratch\proto_converted.apk"
$staging = "c:\android-Zyro-browser-main\scratch\bundle_clean"

if (Test-Path $staging) { Remove-Item -Recurse -Force $staging }
New-Item -ItemType Directory -Path "$staging\base\manifest" | Out-Null
New-Item -ItemType Directory -Path "$staging\base\dex" | Out-Null
New-Item -ItemType Directory -Path "$staging\base\root" | Out-Null

Write-Host "Extracting proto APK..."
$tmp = "$staging\tmp"
New-Item -ItemType Directory -Path $tmp | Out-Null
tar -xf $protoApk -C $tmp

Write-Host "Organizing module layout..."
# Move standard directories if they exist
if (Test-Path "$tmp\res") { Move-Item "$tmp\res" "$staging\base\res" }
if (Test-Path "$tmp\lib") { Move-Item "$tmp\lib" "$staging\base\lib" }
if (Test-Path "$tmp\assets") { Move-Item "$tmp\assets" "$staging\base\assets" }

# Move manifest
Move-Item "$tmp\AndroidManifest.xml" "$staging\base\manifest\AndroidManifest.xml"

# Move protobuf resource table to module root
if (Test-Path "$tmp\resources.pb") { Move-Item "$tmp\resources.pb" "$staging\base\resources.pb" }
if (Test-Path "$tmp\assets.pb") { Move-Item "$tmp\assets.pb" "$staging\base\assets.pb" }
if (Test-Path "$tmp\native.pb") { Move-Item "$tmp\native.pb" "$staging\base\native.pb" }

# Move dex files to dex/
Get-ChildItem -Path $tmp -Filter "classes*.dex" | ForEach-Object {
    Move-Item $_.FullName "$staging\base\dex\"
}

# Any other remaining root files go to root/
Get-ChildItem -Path $tmp -File | ForEach-Object {
    Move-Item $_.FullName "$staging\base\root\"
}

Write-Host "Creating base.zip..."
$baseZip = "$staging\base.zip"
Set-Location "$staging\base"
& "C:\Windows\System32\tar.exe" -a -c -f $baseZip *

Write-Host "Running bundletool build-bundle..."
$env:JAVA_HOME = "C:\android-Zyro-browser-main\corretto-17\jdk17.0.21_0"
$outputAab = "c:\android-Zyro-browser-main\ZyroBrowser-v1.0.2.aab"
$outputAab1 = "c:\android-Zyro-browser-main\ZyroBrowser-v1.0.1.aab"
$rootAab = "c:\android-Zyro-browser-main\ZyroBrowser-v1.0.aab"

& "$env:JAVA_HOME\bin\java.exe" -jar "c:\android-Zyro-browser-main\tools\bundletool.jar" build-bundle `
    --modules=$baseZip `
    --output=$outputAab `
    --overwrite

Write-Host "Validating bundle..."
& "$env:JAVA_HOME\bin\java.exe" -jar "c:\android-Zyro-browser-main\tools\bundletool.jar" validate --bundle=$outputAab

Write-Host "Signing AAB with release keystore..."
$jarsigner = "$env:JAVA_HOME\bin\jarsigner.exe"
$ksPath = "c:\android-Zyro-browser-main\zyro-release.keystore"
$alias = "zyro"
$pass = "zyro123456"

& $jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 `
    -keystore $ksPath `
    -storepass $pass `
    -keypass $pass `
    $outputAab $alias

& $jarsigner -verify $outputAab

Copy-Item $outputAab $outputAab1 -Force
Copy-Item $outputAab $rootAab -Force

Write-Host "SUCCESS! Output: $outputAab and $rootAab"
Get-Item $outputAab | Select-Object Name, Length, LastWriteTime
