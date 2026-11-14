$adb = "C:\Users\koust\AppData\Local\Android\Sdk\platform-tools\adb.exe"
& $adb -s ZA22362KZM logcat -d | Select-String "SafeLink" | Select-Object -Last 30
