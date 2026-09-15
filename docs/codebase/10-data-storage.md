# 10 — Local Data Storage Architecture & Persistence

## 1. Storage Inventory & Directory Layout

On Android devices, Titanium Browser stores all application data inside the private application sandbox at:
`/data/data/io.github.jqssun.helium/`

```
/data/data/io.github.jqssun.helium/
├── app_chrome/                              # Chromium Native Profile Root
│   ├── Default/                             # Active Regular Profile
│   │   ├── Cookies                          # SQLite: HTTP Cookies
│   │   ├── History                          # SQLite: Browsing history & visits
│   │   ├── Bookmarks                        # JSON: Bookmark node tree
│   │   ├── Favicons                         # SQLite: Cached webpage icons
│   │   ├── Login Data                       # SQLite: Saved passwords (local backend)
│   │   ├── Web Data                         # SQLite: Autofill profiles & credit cards
│   │   ├── Preferences                      # JSON: Chromium core preference values
│   │   ├── Secure Preferences               # JSON: Extension settings & HMAC hashes
│   │   ├── Extensions/                      # Unpacked installed extension files
│   │   │   └── <extension_id>/<version>/    # Manifest, JS, HTML, icons
│   │   ├── Extension Rules/                 # LevelDB: DeclarativeNetRequest rulesets
│   │   ├── Extension State/                 # LevelDB: Extension storage API state
│   │   ├── Local Storage/leveldb/           # LevelDB: Web HTML5 localStorage
│   │   ├── IndexedDB/                       # LevelDB: Web IndexedDB databases
│   │   └── Subresource Filter/              # Binary: Indexed adblock rulesets
│   └── extensions/                          # Staged bundled CRX files (from APK assets)
├── cache/
│   ├── Cache_Data/                          # Chromium HTTP disk cache
│   └── Code Cache/                          # V8 compiled JavaScript bytecode cache
└── shared_prefs/
    ├── org.chromium.chrome.browser_preferences.xml # Android SharedPreferences
    └── io.github.jqssun.helium_preferences.xml     # Package SharedPreferences
```

---

## 2. Storage Subsystem Classification

| Storage System | Technology | File Location | Data Managed | Encryption / Security |
| :--- | :--- | :--- | :--- | :--- |
| **Android SharedPreferences** | XML (Android Framework) | `shared_prefs/*.xml` | UI settings, first-run completion, WebRTC IP policy, tab close settings, theme preferences. | Android OS sandboxed (Private UID). Plaintext XML. |
| **Chromium Core Preferences** | JSON format | `app_chrome/Default/Preferences` | Search engine selection, download prompt toggle, content settings, permission states. | Plaintext JSON, protected by Android UID isolation. |
| **History & Visits** | SQLite 3 | `app_chrome/Default/History` | Browsing URLs, timestamps, titles, visit counts, search terms. | SQLite file; not encrypted on disk. Protected by sandbox. |
| **Bookmarks** | JSON format | `app_chrome/Default/Bookmarks` | Hierarchical folder tree of user bookmarks and mobile shortcuts. | Plaintext JSON. |
| **Saved Passwords** | SQLite 3 (`Login Data`) | `app_chrome/Default/Login Data` | Web usernames, encrypted passwords, form signatures. | Encrypted using Chromium OSCrypt (AES-256-GCM / Android Keystore backed). |
| **Web Cookies** | SQLite 3 | `app_chrome/Default/Cookies` | Session cookies, persistent authentication tokens. | Encrypted via OSCrypt on disk. |
| **Autofill / Addresses** | SQLite 3 (`Web Data`) | `app_chrome/Default/Web Data` | Form autofill entries, postal addresses, phone numbers. | Sensitive fields encrypted via OSCrypt. |
| **Extensions Store** | Directory / Files | `app_chrome/Default/Extensions/` | Full unpacked extension assets (manifest, background scripts, content scripts). | Standard filesystem hierarchy within sandbox. |
| **Extension Storage API** | LevelDB | `app_chrome/Default/Extension State/` | Data stored by extensions via `chrome.storage.local` / `chrome.storage.sync`. | LevelDB key-value store. |
| **Web Storage (HTML5)** | LevelDB / SQLite | `app_chrome/Default/Local Storage/`<br>`IndexedDB/` | HTML5 client-side web application data. | LevelDB files per origin. |
| **HTTP Disk Cache** | Block-file Cache | `cache/Cache_Data/` | Cached HTTP responses, images, stylesheets, media. | High-performance binary format with expiration LRU. |

---

## 3. Data Persistence & Lifecycle Matrix

| Event | SharedPreferences | Chromium Profile (History/Cookies) | Saved Passwords | Extensions | Incognito (OTR) Data | Downloaded Files |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Tab Closed** | Persists | Persists | Persists | Persists | Deleted | Persists |
| **Browser Restart (Killed from Recents)** | Persists | Persists | Persists | Persists | Deleted | Persists |
| **Device Reboot** | Persists | Persists | Persists | Persists | Deleted | Persists |
| **App Update (APK / Play Store)** | Persists | Persists | Persists | Persists | Deleted | Persists |
| **Clear App Data (Android Settings)** | **Destroyed** | **Destroyed** | **Destroyed** | **Destroyed** | N/A | **Retained** in `/sdcard/Download` |
| **Uninstall Application** | **Destroyed** | **Destroyed** | **Destroyed** | **Destroyed** | N/A | **Retained** in `/sdcard/Download` |

---

## 4. True Links Storage Considerations

When designing True Links features:
1. **Link Guard Cache & Threat Lists**:
   - Small lookups (whitelist, user toggle): Store in Android `SharedPreferences`.
   - Threat domain lists / hash prefixes: Store in a local SQLite database or LevelDB rather than loading large JSONs into memory.
2. **User Profiles & Authentication**:
   - Store secure session tokens in Android `EncryptedSharedPreferences` (backed by Android Keystore hardware security module).
3. **Admin / Feature Flags**:
   - Store remote config values in a dedicated SharedPreferences file (`true_links_config.xml`) with fallback defaults if offline.
