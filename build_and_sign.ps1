$java = "C:\android-Zyro-browser-main\corretto-17\jdk17.0.21_0\bin\java.exe"
$apktool = "C:\android-Zyro-browser-main\apktool.jar"
$sdkBuildTools = "C:\Users\koust\AppData\Local\Android\Sdk\build-tools\35.0.1"
$zipalign = "$sdkBuildTools\zipalign.exe"
$apksigner = "$sdkBuildTools\apksigner.bat"
$keystore = "C:\Users\koust\.android\debug.keystore"

$unpackedDir = "C:\android-Zyro-browser-main\apk_unpacked"
$unsignedApk = "C:\android-Zyro-browser-main\ZyroBrowser-unsigned.apk"
$alignedApk = "C:\android-Zyro-browser-main\ZyroBrowser-aligned.apk"
$finalApk = "C:\android-Zyro-browser-main\ZyroBrowser-updated.apk"
$framePath = "C:\android-Zyro-browser-main\apktool_framework"

Write-Host "Building APK with Apktool via Corretto 17 with frame-path..."
& $java -jar $apktool b -p $framePath $unpackedDir -o $unsignedApk
if ($LASTEXITCODE -ne 0) { throw "Apktool build failed with exit code $LASTEXITCODE" }

Write-Host "Running zipalign..."
if (Test-Path $alignedApk) { Remove-Item $alignedApk -Force }
& $zipalign -v -p 4 $unsignedApk $alignedApk
if ($LASTEXITCODE -ne 0) { throw "Zipalign failed with exit code $LASTEXITCODE" }

Write-Host "Signing APK with apksigner..."
if (Test-Path $finalApk) { Remove-Item $finalApk -Force }
& $apksigner sign --ks $keystore --ks-pass pass:android --key-pass pass:android --ks-key-alias androiddebugkey --out $finalApk $alignedApk
if ($LASTEXITCODE -ne 0) { throw "Signing failed with exit code $LASTEXITCODE" }

Write-Host "Copying to ZyroBrowser-v1.0.apk..."
Copy-Item $finalApk "C:\android-Zyro-browser-main\ZyroBrowser-v1.0.apk" -Force

Write-Host "SUCCESS! APK built and signed:"
Get-Item $finalApk | Select-Object Name, Length, LastWriteTime
