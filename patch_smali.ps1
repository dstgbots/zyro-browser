$f1 = 'C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity$finishSafeLinkSuccess$1.smali'
$c1 = [System.IO.File]::ReadAllText($f1)

# 1. In invoke$lambda$0 and invoke$lambda$1, replace navigateToUrl with ExternalBrowserHelper.openInExternalBrowser
$oldNav0 = 'invoke-static {p0, p1}, Lcom/zyro/browser/MainActivity;->access$navigateToUrl(Lcom/zyro/browser/MainActivity;Ljava/lang/String;)V'
$newNav0 = 'invoke-static {p0, p1}, Lcom/zyro/browser/ExternalBrowserHelper;->openInExternalBrowser(Landroid/app/Activity;Ljava/lang/String;)V'

if (-not $c1.Contains($oldNav0)) {
    throw 'Target oldNav0 not found in f1'
}

$c1 = $c1.Replace($oldNav0, $newNav0)

# 2. Remove the postDelayed runnable block in invoke(Ljava/lang/String;)V
$patternTimer = '(?s)new-instance v0, Landroid/os/Handler;.*?invoke-virtual \{v0, v2, v3, v4\}, Landroid/os/Handler;->postDelayed\(Ljava/lang/Runnable;J\)Z\r?\n\r?\n\s+goto :goto_0'
if (-not [regex]::IsMatch($c1, $patternTimer)) {
    throw 'Timer pattern not matched in f1'
}
$c1 = [regex]::Replace($c1, $patternTimer, 'goto :goto_0')

[System.IO.File]::WriteAllText($f1, $c1)
Write-Host 'Patched f1 successfully!'

# 3. In MainActivity.smali, update finishSafeLinkSuccess$lambda$121
$f2 = 'C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali'
$c2 = [System.IO.File]::ReadAllText($f2)

$targetBlock = '(?s)finishSafeLinkSuccess\$lambda\$121.*?invoke-direct \{p0\}, Lcom/zyro/browser/MainActivity;->hideSafeLinkScreen\(\)V\r?\n\r?\n\s+\.line \d+\r?\n\s+invoke-direct \{p0, p1\}, Lcom/zyro/browser/MainActivity;->navigateToUrl\(Ljava/lang/String;\)V'

if (-not [regex]::IsMatch($c2, $targetBlock)) {
    throw 'Target block not matched in f2'
}

$replacePattern = '(?s)(finishSafeLinkSuccess\$lambda\$121.*?invoke-direct \{p0\}, Lcom/zyro/browser/MainActivity;->hideSafeLinkScreen\(\)V\r?\n\r?\n\s+\.line \d+\r?\n\s+)invoke-direct \{p0, p1\}, Lcom/zyro/browser/MainActivity;->navigateToUrl\(Ljava/lang/String;\)V'
$replacement = '$1invoke-static {p0, p1}, Lcom/zyro/browser/ExternalBrowserHelper;->openInExternalBrowser(Landroid/app/Activity;Ljava/lang/String;)V'

$c2 = [regex]::Replace($c2, $replacePattern, $replacement)

[System.IO.File]::WriteAllText($f2, $c2)
Write-Host 'Patched f2 successfully!'
