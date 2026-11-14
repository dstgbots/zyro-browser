$adb = "C:\Users\koust\AppData\Local\Android\Sdk\platform-tools\adb.exe"
& $adb -s ZA22362KZM shell screencap -p /sdcard/screen.png
& $adb -s ZA22362KZM pull /sdcard/screen.png "C:\Users\koust\.gemini\antigravity\brain\8027ef0c-4d11-4543-9d20-b070dd9670b6\banner_screen.png"
