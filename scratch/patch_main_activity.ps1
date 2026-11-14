$file = "C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali"
$content = [System.IO.File]::ReadAllText($file)

# 1. Update menu launcher to pass empty string "" instead of "https://example.com"
$target1 = 'const-string v0, "https://example.com"'
$replacement1 = 'const-string v0, ""'

if ($content.Contains($target1)) {
    $content = $content.Replace($target1, $replacement1)
    Write-Host "Target 1 replaced successfully"
} else {
    Write-Warning "Target 1 not found!"
}

# 2. Update end of startSafeLinkVerification to invoke SafeLinkInputHelper
$target2 = @'
    invoke-static {v2, v6, v7}, Lcom/zyro/browser/SafeLinkAnimationHelper;->animateCardEntry(Landroid/view/View;J)V

    .line 1435
    const/4 v2, 0x0

    invoke-direct {v0, v2, v1}, Lcom/zyro/browser/MainActivity;->runStepWithProgress(ILjava/lang/String;)V

    .line 1436
    return-void
.end method
'@

$replacement2 = @'
    invoke-static {v2, v6, v7}, Lcom/zyro/browser/SafeLinkAnimationHelper;->animateCardEntry(Landroid/view/View;J)V

    invoke-virtual {v5}, Lcom/zyro/browser/databinding/LayoutSafelinkVerificationBinding;->getRoot()Landroid/widget/LinearLayout;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->setupSafeLinkMode(Landroid/app/Activity;Landroid/view/View;Ljava/lang/String;)V

    invoke-static {v1}, Lcom/zyro/browser/SafeLinkInputHelper;->isManualMode(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_manual_run

    return-void

    :cond_manual_run
    .line 1435
    const/4 v2, 0x0

    invoke-direct {v0, v2, v1}, Lcom/zyro/browser/MainActivity;->runStepWithProgress(ILjava/lang/String;)V

    .line 1436
    return-void
.end method
'@

# Normalize line endings for replacement
$contentNorm = $content -replace "\r\n", "`n"
$target2Norm = $target2 -replace "\r\n", "`n"
$replacement2Norm = $replacement2 -replace "\r\n", "`n"

if ($contentNorm.Contains($target2Norm)) {
    $contentNorm = $contentNorm.Replace($target2Norm, $replacement2Norm)
    Write-Host "Target 2 replaced successfully"
    # Convert back to Windows CRLF
    $finalContent = $contentNorm -replace "`n", "`r`n"
    [System.IO.File]::WriteAllText($file, $finalContent)
    Write-Host "MainActivity.smali written successfully"
} else {
    Write-Error "Target 2 not found in MainActivity.smali!"
}
