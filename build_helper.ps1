$sdkDir = "C:\Users\koust\AppData\Local\Android\Sdk"
$androidJar = "$sdkDir\platforms\android-34\android.jar"
$d8 = "$sdkDir\build-tools\35.0.1\d8.bat"
$baksmali = "C:\android-Zyro-browser-main\baksmali.jar"

Write-Host "Compiling Java..."
javac -cp $androidJar -d "C:\android-Zyro-browser-main\scratch\classes" "C:\android-Zyro-browser-main\scratch\ExternalBrowserHelper.java"
if ($LASTEXITCODE -ne 0) { throw "javac failed" }

Write-Host "Dexing..."
if (!(Test-Path "C:\android-Zyro-browser-main\scratch\dex_out")) {
    New-Item -ItemType Directory -Path "C:\android-Zyro-browser-main\scratch\dex_out" -Force
}
& $d8 "C:\android-Zyro-browser-main\scratch\classes\com\zyro\browser\ExternalBrowserHelper.class" --output "C:\android-Zyro-browser-main\scratch\dex_out" --lib $androidJar
if ($LASTEXITCODE -ne 0) { throw "d8 failed" }

Write-Host "Baksmaling..."
java -jar $baksmali d "C:\android-Zyro-browser-main\scratch\dex_out\classes.dex" -o "C:\android-Zyro-browser-main\scratch\smali_out"
if ($LASTEXITCODE -ne 0) { throw "baksmali failed" }

Write-Host "Generated Smali:"
Get-ChildItem "C:\android-Zyro-browser-main\scratch\smali_out" -Recurse | Select-Object FullName
