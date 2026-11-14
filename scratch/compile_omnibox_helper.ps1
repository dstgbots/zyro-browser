$sdkDir = "C:\Users\koust\AppData\Local\Android\Sdk"
$androidJar = "$sdkDir\platforms\android-34\android.jar"
$javac = "C:\android-Zyro-browser-main\corretto-17\jdk17.0.21_0\bin\javac.exe"
$d8 = "$sdkDir\build-tools\35.0.1\d8.bat"
$java = "C:\android-Zyro-browser-main\corretto-17\jdk17.0.21_0\bin\java.exe"
$baksmali = "C:\android-Zyro-browser-main\baksmali.jar"

$classesDir = "C:\android-Zyro-browser-main\scratch\classes"
$dexDir = "C:\android-Zyro-browser-main\scratch\dex_out"
$smaliDir = "C:\android-Zyro-browser-main\scratch\smali_out"

if (Test-Path $classesDir) { Remove-Item $classesDir -Recurse -Force }
New-Item -ItemType Directory -Path $classesDir -Force

if (Test-Path $dexDir) { Remove-Item $dexDir -Recurse -Force }
New-Item -ItemType Directory -Path $dexDir -Force

if (Test-Path $smaliDir) { Remove-Item $smaliDir -Recurse -Force }
New-Item -ItemType Directory -Path $smaliDir -Force

Write-Host "Compiling Java files..."
& $javac -cp $androidJar -d $classesDir "C:\android-Zyro-browser-main\scratch\MainActivity.java" "C:\android-Zyro-browser-main\scratch\BrowserOmniboxHelper.java"
if ($LASTEXITCODE -ne 0) { throw "javac failed with code $LASTEXITCODE" }

Write-Host "Dexing BrowserOmniboxHelper..."
$omniboxClasses = Get-ChildItem -Path "$classesDir\com\zyro\browser" -Filter "BrowserOmniboxHelper*.class" | ForEach-Object { $_.FullName }
& $d8 $omniboxClasses --output $dexDir --lib $androidJar
if ($LASTEXITCODE -ne 0) { throw "d8 failed with code $LASTEXITCODE" }

Write-Host "Baksmaling..."
& $java -jar $baksmali d "$dexDir\classes.dex" -o $smaliDir
if ($LASTEXITCODE -ne 0) { throw "baksmali failed with code $LASTEXITCODE" }

Write-Host "Generated Smali files:"
Get-ChildItem -Path $smaliDir -Recurse | Select-Object Name
