$adb = "C:\Users\koust\AppData\Local\Android\Sdk\platform-tools\adb.exe"
& $adb -s ZA22362KZM logcat -c
& $adb -s ZA22362KZM shell am start -n com.zyro.browser/com.zyro.browser.MainActivity -a android.intent.action.VIEW -d "zyro://safelink?alias=https://ezyshort.xyz/jH1C`&uuid=8caa17c2-9f3d-5580-e2f0-b5596fcf65f8"
Start-Sleep -Seconds 3
& $adb -s ZA22362KZM logcat -d -s SafeLinkInputHelper:* Ads:* GoogleAdManager:* MetaAdManager:* | Select-Object -Last 40
