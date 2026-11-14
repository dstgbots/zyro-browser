Add-Type -AssemblyName System.Drawing

$srcPath = "c:\android-Zyro-browser-main\Zyro_white_logo.png"
$src = [System.Drawing.Bitmap]::FromFile($srcPath)
$w = $src.Width
$h = $src.Height

# Create 32-bit ARGB bitmap
$dst = New-Object System.Drawing.Bitmap($w, $h, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)

# Background color in Zyro_white_logo is ~254, 254, 254
for ($y = 0; $y -lt $h; $y++) {
    for ($x = 0; $x -lt $w; $x++) {
        $c = $src.GetPixel($x, $y)
        
        # Calculate brightness / distance from pure white
        # If it's pure or near white: transparent
        $diff = (255 - $c.R) + (255 - $c.G) + (255 - $c.B)
        
        if ($diff -le 15) {
            # Pure white background -> completely transparent
            $dst.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(0, 0, 0, 0))
        } elseif ($diff -lt 60) {
            # Feather edge
            $alpha = [int](($diff - 15) / 45.0 * 255.0)
            if ($alpha -gt 255) { $alpha = 255 }
            if ($alpha -lt 0) { $alpha = 0 }
            $dst.SetPixel($x, $y, [System.Drawing.Color]::FromArgb($alpha, $c.R, $c.G, $c.B))
        } else {
            $dst.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(255, $c.R, $c.G, $c.B))
        }
    }
}

$src.Dispose()

$outPath = "c:\android-Zyro-browser-main\scratch\logo_transparent.png"
$dst.Save($outPath, [System.Drawing.Imaging.ImageFormat]::Png)
$dst.Dispose()

Write-Host "Created transparent logo at $outPath"
