$adb = "C:\Users\koust\AppData\Local\Android\Sdk\platform-tools\adb.exe"
& $adb -s ZA22362KZM logcat -c
& $adb -s ZA22362KZM shell "am start -S -n com.zyro.browser/com.zyro.browser.MainActivity -a android.intent.action.VIEW -d 'zyro://safelink?alias=jH1C&uuid=2d570bc4-84c2-0f13-edbd-95fae010d2be&api=https%3A%2F%2Fezyshort.xyz%2Fapi.php&dest=https%3A%2F%2Fezyshort.xyz%2FjH1C'"
Start-Sleep -Seconds 4
& $adb -s ZA22362KZM logcat -d | Select-String -Pattern "SafeLink|zyrobrowser|ezyshort" | Select-Object -Last 30
