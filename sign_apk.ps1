$sdkBuildTools = "C:\Users\koust\AppData\Local\Android\Sdk\build-tools\35.0.1"
$zipalign = "$sdkBuildTools\zipalign.exe"
$apksigner = "$sdkBuildTools\apksigner.bat"
$keystore = "C:\Users\koust\.android\debug.keystore"

$unsignedApk = "C:\android-Zyro-browser-main\ZyroBrowser-unsigned.apk"
$alignedApk = "C:\android-Zyro-browser-main\ZyroBrowser-aligned.apk"
$finalApk = "C:\android-Zyro-browser-main\ZyroBrowser-updated.apk"

if (Test-Path $alignedApk) { Remove-Item $alignedApk -Force }
if (Test-Path $finalApk) { Remove-Item $finalApk -Force }

Write-Host "Running zipalign..."
& $zipalign -v -p 4 $unsignedApk $alignedApk
if ($LASTEXITCODE -ne 0) { throw "zipalign failed" }

Write-Host "Signing APK with apksigner..."
& $apksigner sign --ks $keystore --ks-pass pass:android --key-pass pass:android --ks-key-alias androiddebugkey --out $finalApk $alignedApk
if ($LASTEXITCODE -ne 0) { throw "apksigner failed" }

Write-Host "Verifying signature..."
& $apksigner verify -v $finalApk
if ($LASTEXITCODE -ne 0) { throw "verification failed" }

Write-Host "SUCCESS! Final APK created at: $finalApk"
Get-Item $finalApk | Select-Object Name, Length
