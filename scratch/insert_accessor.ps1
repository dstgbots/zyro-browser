$file = "C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali"
$content = [System.IO.File]::ReadAllText($file)

$target = @'
.method public static final synthetic access$finishSafeLinkSuccess(Lcom/zyro/browser/MainActivity;Ljava/lang/String;)V
    .locals 0
    .param p0, "$this"    # Lcom/zyro/browser/MainActivity;
    .param p1, "targetUrl"    # Ljava/lang/String;

    .line 111
    invoke-direct {p0, p1}, Lcom/zyro/browser/MainActivity;->finishSafeLinkSuccess(Ljava/lang/String;)V

    return-void
.end method
'@

$replacement = @'
.method public static final synthetic access$finishSafeLinkSuccess(Lcom/zyro/browser/MainActivity;Ljava/lang/String;)V
    .locals 0
    .param p0, "$this"    # Lcom/zyro/browser/MainActivity;
    .param p1, "targetUrl"    # Ljava/lang/String;

    .line 111
    invoke-direct {p0, p1}, Lcom/zyro/browser/MainActivity;->finishSafeLinkSuccess(Ljava/lang/String;)V

    return-void
.end method

.method public static final synthetic access$runStepWithProgress(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V
    .locals 0
    .param p0, "$this"    # Lcom/zyro/browser/MainActivity;
    .param p1, "stepIndex"    # I
    .param p2, "targetUrl"    # Ljava/lang/String;

    .line 111
    invoke-direct {p0, p1, p2}, Lcom/zyro/browser/MainActivity;->runStepWithProgress(ILjava/lang/String;)V

    return-void
.end method
'@

$contentNorm = $content -replace "\r\n", "`n"
$targetNorm = $target -replace "\r\n", "`n"
$replacementNorm = $replacement -replace "\r\n", "`n"

if ($contentNorm.Contains($targetNorm)) {
    $contentNorm = $contentNorm.Replace($targetNorm, $replacementNorm)
    $finalContent = $contentNorm -replace "`n", "`r`n"
    [System.IO.File]::WriteAllText($file, $finalContent)
    Write-Host "access$runStepWithProgress inserted successfully!"
} else {
    Write-Error "Target not found!"
}
