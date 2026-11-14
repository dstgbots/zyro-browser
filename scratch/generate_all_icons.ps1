Add-Type -AssemblyName System.Drawing

$srcLogo = [System.Drawing.Bitmap]::FromFile("c:\android-Zyro-browser-main\scratch\logo_transparent.png")

# Densities and sizes:
# mdpi: launcher=48x48, foreground=108x108
# hdpi: launcher=72x72, foreground=162x162
# xhdpi: launcher=96x96, foreground=216x216
# xxhdpi: launcher=144x144, foreground=324x324
# xxxhdpi: launcher=192x192, foreground=432x432

$specs = @(
    @{ folder = "mipmap-mdpi"; launcherSize = 48; fgSize = 108 },
    @{ folder = "mipmap-hdpi"; launcherSize = 72; fgSize = 162 },
    @{ folder = "mipmap-xhdpi"; launcherSize = 96; fgSize = 216 },
    @{ folder = "mipmap-xxhdpi"; launcherSize = 144; fgSize = 324 },
    @{ folder = "mipmap-xxxhdpi"; launcherSize = 192; fgSize = 432 }
)

$targetRoots = @(
    "c:\android-Zyro-browser-main\apk_unpacked\res",
    "c:\android-Zyro-browser-main\zyro-app\app\src\main\res",
    "c:\android-Zyro-browser-main\android-zyro-browser-main\zyro-app\app\src\main\res"
)

function CreateScaledImage($src, $w, $h, $paddingPercent) {
    $bmp = New-Object System.Drawing.Bitmap($w, $h, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    $g = [System.Drawing.Graphics]::FromImage($bmp)
    $g.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
    $g.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::HighQuality
    $g.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::HighQuality
    $g.CompositingQuality = [System.Drawing.Drawing2D.CompositingQuality]::HighQuality
    $g.Clear([System.Drawing.Color]::Transparent)

    $targetW = [int]($w * (1.0 - $paddingPercent))
    $targetH = [int]($h * (1.0 - $paddingPercent))
    $offsetX = [int](($w - $targetW) / 2.0)
    $offsetY = [int](($h - $targetH) / 2.0)

    $g.DrawImage($src, $offsetX, $offsetY, $targetW, $targetH)
    $g.Dispose()
    return $bmp
}

foreach ($spec in $specs) {
    $folderName = $spec.folder
    $lSize = $spec.launcherSize
    $fSize = $spec.fgSize

    Write-Host "Generating for $folderName (launcher=$lSize, fg=$fSize)..."
    
    # 1. ic_launcher.png (standard icon, 4% padding for clean margins)
    $launcherBmp = CreateScaledImage $srcLogo $lSize $lSize 0.05
    
    # 2. ic_launcher_round.png (same)
    $roundBmp = CreateScaledImage $srcLogo $lSize $lSize 0.05

    # 3. ic_launcher_foreground.png (adaptive icon, standard 33% padding so inner content is 67% safe zone)
    $fgBmp = CreateScaledImage $srcLogo $fSize $fSize 0.30

    foreach ($root in $targetRoots) {
        $dir = Join-Path $root $folderName
        if (Test-Path $dir) {
            $p1 = Join-Path $dir "ic_launcher.png"
            $p2 = Join-Path $dir "ic_launcher_round.png"
            $p3 = Join-Path $dir "ic_launcher_foreground.png"

            $launcherBmp.Save($p1, [System.Drawing.Imaging.ImageFormat]::Png)
            $roundBmp.Save($p2, [System.Drawing.Imaging.ImageFormat]::Png)
            $fgBmp.Save($p3, [System.Drawing.Imaging.ImageFormat]::Png)
            Write-Host "  Saved to $dir"
        }
    }

    $launcherBmp.Dispose()
    $roundBmp.Dispose()
    $fgBmp.Dispose()
}

$srcLogo.Dispose()
Write-Host "ALL ICONS GENERATED SUCCESSFULLY!"
