$path = "c:\android-Zyro-browser-main\apk_unpacked\smali_classes4\com\zyro\browser\MainActivity.smali"
$c = [System.IO.File]::ReadAllText($path, [System.Text.Encoding]::UTF8)

$prefix = ".method private static final setupTopOmnibox`$lambda`$11"
$start = $c.IndexOf($prefix)
if ($start -lt 0) { throw "Could not find lambda11 start!" }

$end = $c.IndexOf(".end method", $start) + 11
$target = $c.Substring($start, $end - $start)
Write-Host "Found target ($($target.Length) chars):"
Write-Host $target

$rep = @"
.method private static final setupTopOmnibox`$lambda`$11(Lcom/zyro/browser/MainActivity;Landroid/widget/TextView;ILandroid/view/KeyEvent;)Z
    .locals 1
    .param p0, "this`$0"    # Lcom/zyro/browser/MainActivity;
    .param p2, "actionId"    # I
    .param p3, "event"    # Landroid/view/KeyEvent;

    const-string p1, "this`$0"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/zyro/browser/MainActivity;->loadEnteredUrl()V

    const/4 p1, 0x1

    return p1
.end method
"@

$newC = $c.Substring(0, $start) + $rep + $c.Substring($end)
[System.IO.File]::WriteAllBytes($path, [System.Text.UTF8Encoding]::new($false).GetBytes($newC))
Write-Host "Successfully patched setupTopOmnibox`$lambda`$11!"
