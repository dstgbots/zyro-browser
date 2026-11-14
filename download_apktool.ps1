$url = 'https://github.com/iBotPeaches/Apktool/releases/download/v2.10.0/apktool_2.10.0.jar'
$output = 'C:\android-Zyro-browser-main\apktool.jar'
Write-Host "Downloading $url to $output ..."
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest -Uri $url -OutFile $output -UseBasicParsing
Get-Item $output | Select-Object Name, Length
