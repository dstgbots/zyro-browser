Add-Type -AssemblyName System.Drawing

$trans = [System.Drawing.Bitmap]::FromFile("c:\android-Zyro-browser-main\scratch\logo_transparent.png")
$w = 400
$h = 400

# 1. On White Background
$bmpWhite = New-Object System.Drawing.Bitmap($w, $h)
$gWhite = [System.Drawing.Graphics]::FromImage($bmpWhite)
$gWhite.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
$gWhite.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::HighQuality
$gWhite.Clear([System.Drawing.Color]::White)
$gWhite.DrawImage($trans, 0, 0, $w, $h)
$gWhite.Dispose()
$bmpWhite.Save("c:\android-Zyro-browser-main\scratch\preview_white.png", [System.Drawing.Imaging.ImageFormat]::Png)
$bmpWhite.Dispose()

# 2. On Dark Background (#121316)
$bmpDark = New-Object System.Drawing.Bitmap($w, $h)
$gDark = [System.Drawing.Graphics]::FromImage($bmpDark)
$gDark.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
$gDark.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::HighQuality
$gDark.Clear([System.Drawing.Color]::FromArgb(255, 18, 19, 22))
$gDark.DrawImage($trans, 0, 0, $w, $h)
$gDark.Dispose()
$bmpDark.Save("c:\android-Zyro-browser-main\scratch\preview_dark.png", [System.Drawing.Imaging.ImageFormat]::Png)
$bmpDark.Dispose()

$trans.Dispose()
Write-Host "Saved preview_white.png and preview_dark.png"
