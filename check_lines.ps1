$f1 = 'C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity$finishSafeLinkSuccess$1.smali'
$lines = [System.IO.File]::ReadAllLines($f1)
for ($i = 108; $i -le 116; $i++) {
    Write-Host "$i : $($lines[$i])"
}
