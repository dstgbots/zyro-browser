$mainSmaliPath = "C:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali"
$content = [System.IO.File]::ReadAllText($mainSmaliPath, [System.Text.Encoding]::UTF8)

# 1. Add access$loadEnteredUrl right after access$runStepWithProgress
$target1 = ".method public static final synthetic access`$runStepWithProgress(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V"
$target1Full = @"
.method public static final synthetic access`$runStepWithProgress(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V
    .locals 0
    .param p0, "`$this"    # Lcom/zyro/browser/MainActivity;
    .param p1, "stepIndex"    # I
    .param p2, "targetUrl"    # Ljava/lang/String;

    .line 111
    invoke-direct {p0, p1, p2}, Lcom/zyro/browser/MainActivity;->runStepWithProgress(ILjava/lang/String;)V

    return-void
.end method
"@

$replacement1 = @"
.method public static final synthetic access`$runStepWithProgress(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V
    .locals 0
    .param p0, "`$this"    # Lcom/zyro/browser/MainActivity;
    .param p1, "stepIndex"    # I
    .param p2, "targetUrl"    # Ljava/lang/String;

    .line 111
    invoke-direct {p0, p1, p2}, Lcom/zyro/browser/MainActivity;->runStepWithProgress(ILjava/lang/String;)V

    return-void
.end method

.method public static final synthetic access`$loadEnteredUrl(Lcom/zyro/browser/MainActivity;)V
    .locals 0
    .param p0, "`$this"    # Lcom/zyro/browser/MainActivity;

    invoke-direct {p0}, Lcom/zyro/browser/MainActivity;->loadEnteredUrl()V

    return-void
.end method
"@

# 2. Add BrowserOmniboxHelper.setupOmnibox call before return-void in setupTopOmnibox()V
$target2 = @"
    invoke-virtual {v0, v1}, Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout;->setOnChildScrollUpCallback(Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout`$OnChildScrollUpCallback;)V

    .line 596
    return-void
.end method
"@

$replacement2 = @"
    invoke-virtual {v0, v1}, Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout;->setOnChildScrollUpCallback(Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout`$OnChildScrollUpCallback;)V

    invoke-static {p0}, Lcom/zyro/browser/BrowserOmniboxHelper;->setupOmnibox(Landroid/app/Activity;)V

    .line 596
    return-void
.end method
"@

# 3. Enhance setupTopOmnibox$lambda$11 to accept all action IDs and Enter keys
$target3 = @"
.method private static final setupTopOmnibox`$lambda`$11(Lcom/zyro/browser/MainActivity;Landroid/widget/TextView;ILandroid/view/KeyEvent;)Z
    .locals 1
    .param p0, "this`$0"    # Lcom/zyro/browser/MainActivity;
    .param p2, "actionId"    # I
    .param p3, "event"    # Landroid/view/KeyEvent;

    const-string p1, "this`$0"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 540
    const/4 p1, 0x2

    if-eq p2, p1, :cond_1

    .line 541
    if-eqz p3, :cond_0

    invoke-virtual {p3}, Landroid/view/KeyEvent;->getKeyCode()I

    move-result p1

    const/16 v0, 0x42

    if-ne p1, v0, :cond_0

    invoke-virtual {p3}, Landroid/view/KeyEvent;->getAction()I

    move-result p1

    if-nez p1, :cond_0

    goto :goto_0

    .line 546
    :cond_0
    const/4 p1, 0x0

    goto :goto_1

    .line 543
    :cond_1
    :goto_0
    invoke-direct {p0}, Lcom/zyro/browser/MainActivity;->loadEnteredUrl()V

    .line 544
    const/4 p1, 0x1

    .line 540
    :goto_1
    return p1
.end method
"@

$replacement3 = @"
.method private static final setupTopOmnibox`$lambda`$11(Lcom/zyro/browser/MainActivity;Landroid/widget/TextView;ILandroid/view/KeyEvent;)Z
    .locals 1
    .param p0, "this`$0"    # Lcom/zyro/browser/MainActivity;
    .param p2, "actionId"    # I
    .param p3, "event"    # Landroid/view/KeyEvent;

    const-string p1, "this`$0"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    # Accept GO(2), SEARCH(3), SEND(4), NEXT(5), or DONE(6)
    const/4 p1, 0x2
    if-lt p2, p1, :check_event
    const/4 p1, 0x6
    if-le p2, p1, :check_event
    goto :goto_0

    :check_event
    if-eqz p3, :cond_0

    invoke-virtual {p3}, Landroid/view/KeyEvent;->getKeyCode()I
    move-result p1
    const/16 v0, 0x42
    if-eq p1, v0, :check_numpad
    goto :goto_0

    :check_numpad
    const/16 v0, 0xa0
    if-ne p1, v0, :goto_0

    :cond_0
    const/4 p1, 0x0
    return p1

    :goto_0
    invoke-direct {p0}, Lcom/zyro/browser/MainActivity;->loadEnteredUrl()V
    const/4 p1, 0x1
    return p1
.end method
"@

# Normalize CRLF
$normContent = $content.Replace("`r`n", "`n")
$normTarget1 = $target1Full.Replace("`r`n", "`n")
$normRep1 = $replacement1.Replace("`r`n", "`n")
$normTarget2 = $target2.Replace("`r`n", "`n")
$normRep2 = $replacement2.Replace("`r`n", "`n")
$normTarget3 = $target3.Replace("`r`n", "`n")
$normRep3 = $replacement3.Replace("`r`n", "`n")

Write-Host "Checking target 1..."
if (-not $normContent.Contains($normTarget1)) { throw "Target 1 not found in MainActivity.smali!" }
Write-Host "Target 1 found!"

Write-Host "Checking target 2..."
if (-not $normContent.Contains($normTarget2)) { throw "Target 2 not found in MainActivity.smali!" }
Write-Host "Target 2 found!"

Write-Host "Checking target 3..."
if (-not $normContent.Contains($normTarget3)) { throw "Target 3 not found in MainActivity.smali!" }
Write-Host "Target 3 found!"

$normContent = $normContent.Replace($normTarget1, $normRep1)
$normContent = $normContent.Replace($normTarget2, $normRep2)
$normContent = $normContent.Replace($normTarget3, $normRep3)

[System.IO.File]::WriteAllText($mainSmaliPath, $normContent.Replace("`n", "`r`n"), [System.Text.Encoding]::UTF8)
Write-Host "SUCCESS: MainActivity.smali patched successfully!"
