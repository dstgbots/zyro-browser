Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [System.IO.Compression.ZipFile]::OpenRead('C:\android-Zyro-browser-main\ZyroBrowser-v1.0.apk')
foreach ($entry in $zip.Entries) {
    if ($entry.Name -like '*.dex') {
        $stream = $entry.Open()
        $reader = New-Object System.IO.BinaryReader($stream)
        $bytes = $reader.ReadBytes($entry.Length)
        $text = [System.Text.Encoding]::ASCII.GetString($bytes)
        if ($text.Contains('Lcom/zyro/browser/MainActivity;')) {
            Write-Host ('MainActivity found in: ' + $entry.Name)
        }
        $reader.Close()
        $stream.Close()
    }
}
$zip.Dispose()
