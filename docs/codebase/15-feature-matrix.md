# 15 — Current Feature Matrix & Baseline Capabilities

## Master Feature Matrix

| Feature | Exists | Implementation | Location in Tree | Keep | Modify | Replace | Unknown | Notes |
| :--- | :---: | :--- | :--- | :---: | :---: | :---: | :---: | :--- |
| **Browser Engine** | Yes | Chromium Content / Blink / V8 (C++) | `src/content`, `src/v8`, `src/third_party/blink` | ✅ | | | | Upstream Google Chromium core engine @ milestone M153. |
| **Tabs Architecture** | Yes | Java TabModel / C++ WebContents | `chrome/android/java/.../tab/`, `src/content` | ✅ | | | | Standard multi-tab management, tab groups, and tab switcher grid. |
| **Address Bar (Omnibox)** | Yes | Android Java View + Omnibox Engine | `chrome/browser/ui/android/omnibox/` | | ✅ | | | Modified in `patch.sh` to retain phone autocomplete behavior under `is_desktop_android`. |
| **Search Engine** | Yes | Default set to DuckDuckGo; engine list | `components/search_engines/`, `Vanadium:0114` | | ✅ | | | Customized by Vanadium; True Links can add custom search providers. |
| **Bookmarks** | Yes | SQLite / JSON Chromium model | `chrome/android/java/.../bookmarks/` | ✅ | | | | Native bookmark tree and management UI. |
| **History** | Yes | Chromium History WebUI / SQLite | `chrome/browser/resources/history/` | ✅ | | | | Standard browsing history and search. |
| **Downloads** | Yes | Android DownloadManager + SAF | `chrome/android/java/.../download/` | ✅ | | | | Hardened download prompt toggle (`Vanadium:0102-0103`). |
| **Incognito (OTR)** | Yes | Chromium Off-the-record Profile | `chrome/browser/incognito/`, `patch.sh:L149-159` | ✅ | | | | Titanium patches prevent UAF crashes during OTR profile teardown. |
| **Extensions (MV2/MV3)** | Yes | Desktop Chromium Extensions on Android | `chrome/browser/extensions/`, `patch.sh:L69-125` | ✅ | | | | **Core Titanium capability**: Restores MV2, off-store CRX, and phone UI popups. |
| **Privacy Protections** | Yes | Hardened defaults & telemetry strip | `Vanadium patches`, `args.gn:L18-20` | ✅ | | | | Strict site isolation, 3rd-party cookies blocked, no variations header. |
| **Ad Blocking** | Yes | Native Subresource Filter + EasyList | `components/subresource_filter/`, `.gclient` | | ✅ | | | Active on all sites (`Vanadium:0199`). Can be extended with True Links filter lists. |
| **WebRTC Controls** | Yes | IP policy toggle in Privacy Settings | `Vanadium:0100`, `Vanadium:0164` | ✅ | | | | Defaults to `DisableNonProxiedUdp` with Settings UI toggle. |
| **Settings** | Yes | Android Jetpack Preference UI | `chrome/android/java/.../settings/` | | ✅ | | | Can be extended to host True Links preferences and Link Guard controls. |
| **Themes / Dark Mode** | Yes | Android DayNight & Web Contents Darkening | `chrome/android/java/res/values/styles.xml` | ✅ | | | | Native dark theme support and web page auto-darkening flag. |
| **New Tab Page (NTP)** | Yes | Android Native NTP + Extension Override | `chrome/android/java/.../ntp/`, `patch.sh:L127-132` | | ✅ | | | Can be replaced with a custom True Links home experience or dashboard. |
| **Navigation & Links** | Yes | NavigationController & Throttle Chain | `src/content/browser/renderer_host/` | | ✅ | | | Foundation for future True Links Link Guard insertion. |
| **Redirect Handling** | Yes | NavigationURLLoader HTTP 30x logic | `src/content/browser/loader/` | | ✅ | | | Safe browsing and redirect validation. |
| **External Intents** | Yes | LaunchIntentDispatcher & Scheme Guard | `titanium/.../LaunchIntentDispatcherHooks.java` | | ✅ | | | Patched by Titanium to reject non-HTTP/HTTPS URLs (`patch.sh:L22-24`). |
| **Notifications** | Yes | Android NotificationManager / Chrome service | `chrome/android/java/.../notifications/` | ✅ | | | | Standard web push notification infrastructure. |
| **Analytics (Google)** | No | Stripped / Disabled | `args.gn:L18-20`, `Vanadium:0106` | | | ✅ | | Google Analytics and variations telemetry are stripped. True Links can add opt-in telemetry. |
| **Crash Reporting** | No | Crashpad disabled / stubbed | `build.sh:L26`, `args.gn` | | | ✅ | | Crashpad / Google crash server uploads are disabled. True Links can add Crashlytics if desired. |
| **Built-in Ads Engine** | No | None | N/A | | | ✅ | | No ad monetization engine currently exists. Target for True Links custom ad engine. |
| **User Account / Login** | No | Google Auth disabled | `args.gn:L18-20` | | | ✅ | | Google Play Services sign-in disabled. Target for True Links custom user accounts. |
| **Cloud Sync** | No | Google Chrome Sync disabled | `args.gn:L18-20` | | | ✅ | | Google Sync disabled. Target for True Links encrypted cloud bookmarks/tab sync. |
