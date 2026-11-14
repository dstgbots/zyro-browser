$url = 'https://bitbucket.org/JesusFreke/smali/downloads/baksmali-2.5.2.jar'
$out = 'C:\android-Zyro-browser-main\baksmali.jar'
Write-Host "Downloading $url ..."
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest -Uri $url -OutFile $out -UseBasicParsing
Get-Item $out | Select-Object Name, Length
