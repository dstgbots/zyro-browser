$adb = "C:\Users\koust\AppData\Local\Android\Sdk\platform-tools\adb.exe"
& $adb -s ZA22362KZM logcat -c
& $adb -s ZA22362KZM shell "am start -S -n com.zyro.browser/com.zyro.browser.MainActivity -a android.intent.action.VIEW -d 'https://zyrobrowser.com/link-scanner?url=https://ezyshort.xyz/jH1C&uuid=2d570bc4-84c2-0f13-edbd-95fae010d2be'"
Start-Sleep -Seconds 4
& $adb -s ZA22362KZM logcat -d | Select-String -Pattern "LinkScanner" | Select-Object -Last 10
