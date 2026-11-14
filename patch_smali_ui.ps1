$f1 = 'C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali'
$c1 = [System.IO.File]::ReadAllText($f1)

$c1 = $c1.Replace('"LINK PROTECTION VERIFIED - 100% SAFE"', '"Safety Verified"')
$c1 = $c1.Replace('"OPEN VERIFIED LINK \u2192"', '"Proceed to Destination \u2192"')
$c1 = $c1.Replace('"LINK PROTECTION SCAN IN PROGRESS..."', '"Verifying Link Security..."')

[System.IO.File]::WriteAllText($f1, $c1)
Write-Host "Updated MainActivity.smali strings"

$f2 = 'C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity$finishSafeLinkSuccess$1.smali'
$c2 = [System.IO.File]::ReadAllText($f2)

$c2 = $c2.Replace('"OPEN VERIFIED LINK \u2192"', '"Proceed to Destination \u2192"')

[System.IO.File]::WriteAllText($f2, $c2)
Write-Host "Updated MainActivity`$finishSafeLinkSuccess`$1.smali strings"
