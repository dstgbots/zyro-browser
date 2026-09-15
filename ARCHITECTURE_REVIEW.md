# ARCHITECTURE_REVIEW.md — Comprehensive Senior Architect Review

> [!NOTE]
> **Project Context**: This document presents the definitive architectural audit of **Titanium Browser for Android** in preparation for its strategic transformation into **True Links Browser**.
> **Rule Compliance**: No source code modifications, refactorings, or production changes have been executed during this analysis phase.

---

## 1. What This Project Actually Is

Titanium Browser is **not** a monolithic Android app or a standard Gradle project. It is a **lightweight, upstream-tracking Chromium orchestration and patching meta-repository**.

Rather than checking in the ~50 GB Google Chromium source code, this repository contains:
1. Shell build scripts ([`build.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/build.sh), [`common.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/common.sh)).
2. A surgical in-tree patch script ([`patch.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh)).
3. GN compiler configuration ([`args.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/args.gn)).
4. Extension bundling utilities ([`extensions/`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/)).
5. Branding assets ([`res/`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/)).
6. A Git submodule link to GrapheneOS's **Vanadium** hardening patches.

At compile time, it pulls a clean tag of Google Chromium (M153+), applies GrapheneOS's security and ad-blocking patches, injects Titanium's desktop-to-mobile extension adaptations and scheme guards, and compiles official signed Android APKs and AAB bundles.

---

## 2. How It Builds

The build pipeline is automated via [`build.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/build.sh) and executed in CI via [`.github/workflows/build.yml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.github/workflows/build.yml):

```
SOURCE CHECKOUT (Chromium src.git @ tag $VERSION)
   ↓
DEPENDENCY HOOKS (.gclient: EasyList/EasyPrivacy download & bundled CRX ingestion)
   ↓
VANADIUM PATCH APPLICATION (git am on vanadium/patches/*.patch with token rebranding)
   ↓
TITANIUM SURGICAL PATCHES (patch.sh in-place sed edits & asset copies)
   ↓
GN CONFIGURATION (args.gn -> out/Default/args.gn, gn gen out/Default)
   ↓
COMPILE (autoninja -C out/Default chrome_public_apk for arm & arm64)
   ↓
PACKAGE & SIGN (apksigner for APKs, jarsigner for AAB bundles)
   ↓
DELIVERABLES (out/release/*.apk & *.aab with SLSA supply-chain attestation)
```

**Key Build Directives**:
- Compilation toolchain: Google hermetic Clang/LLVM, GN, Ninja/Siso.
- Targets: `chrome_public_apk` and `chrome_public_bundle`.
- Key flags: `is_desktop_android = true`, `is_official_build = true`, `chrome_public_manifest_package = "io.github.jqssun.helium"`.

---

## 3. How the Android App Starts

When the user taps the app icon:
1. **OS Entry**: Android framework starts the process and calls `ChromeApplication.attachBaseContext()` and `ChromeApplicationImpl.onCreate()`.
2. **Intent Interception**: `ChromeLauncherActivity` receives the launch intent and routes to `LaunchIntentDispatcher`.
3. **Titanium Scheme Guard Check**: `LaunchIntentDispatcherHooks.java` inspects the intent URI. If it is not a valid `http://` or `https://` network URL, non-network schemes (`javascript:`, `file:`, `content:`) are rejected.
4. **Native Engine Boot**: `LibraryLoader.ensureInitialized()` loads `libchrome.so`, triggering native `ContentMain()` -> `ChromeMain()` -> `BrowserMainRunner`.
5. **Profile & Extension Staging**: `ProfileManager` loads the default profile. Chromium's `ExternalPrefLoader` executes Titanium's injected `StageBundledExtensions()` ([`stage_bundled_extensions.inc`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/stage_bundled_extensions.inc)), which extracts `titanium.crx` from the APK assets and activates it.
6. **UI & First Paint**: `ChromeTabbedActivity` initializes `TabModelSelector`, restores open tabs, or opens the New Tab Page. The active `WebContents` is bound to `CompositorViewHolder` and rendered to an Android `SurfaceView`.

---

## 4. How Chromium is Integrated

- **Language Boundary**: Managed via Chromium's automated JNI generator (`@JNINamespace`, `@NativeMethods`, `@CalledByNative`), producing static native bridge stubs.
- **Process Hierarchy**:
  - **Browser Process** (Unsandboxed, runs Android UI, Network service, Tab management).
  - **Renderer Processes** (Sandboxed via Android `isolatedProcess="true"`, runs Blink rendering and V8 JavaScript).
- **Core Abstractions**:
  - `BrowserContext` / `Profile`: User data boundary (cookies, cache, extensions).
  - `WebContents`: The core webpage frame-tree controller.
  - `TabImpl`: Java wrapper binding `WebContents` to the Android tab switcher.
  - `CompositorViewHolder`: Top-level Android view displaying frames composited by native Viz.

---

## 5. How Titanium Modifications Are Applied

Because Chromium is checked out fresh during compilation, Titanium modifies the source tree dynamically using **[`patch.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh)**:
- **XML Layout Injections**: Injects `extensions_toolbar_container_stub` into `toolbar_phone.xml`.
- **Canvas Rendering Hooks**: Edits `ToolbarPhone.java` to draw the extension action container.
- **Null Safety & Sizing**: Modifies `ExtensionActionListMediator.java` to prevent phone crashes on missing button anchors, and resizes popups to full screen in `ExtensionActionPopup.java` and `extension_action_popup_contents.cc`.
- **Manifest V2 Restoration**: Un-deprecates MV2 in `webstore_private_api.cc` and `manifest_v2_handler.cc`.
- **Crash Prevention**: Injects `HasLiveWebContentsForBrowserContext` into `profile_destroyer.cc` and `web_contents_impl.cc` to eliminate Incognito teardown crashes.

---

## 6. How Extensions Work

1. **Activation**: Enabled globally on Android via `is_desktop_android = true` in `args.gn`.
2. **Compatibility**: Both **Manifest V2** and **Manifest V3** extensions are fully functional.
3. **Ingestion Sources**:
   - Chrome Web Store (requires desktop User-Agent).
   - Off-Store CRX installs (allowlisted for Edge and Opera Add-ons via `download_crx_util.cc`).
   - Unpacked developer folders (loaded via Android Storage Access Framework and `VirtualDocumentPath.java`).
   - Zero-click pre-bundled APK extensions (staged on startup via `stage_bundled_extensions.inc`).
4. **Incognito Execution**: Permitted by overriding `extensions/browser/process_manager.cc` and `IncognitoUtils.java`.
5. **Process Stability**: Extension background hosts are assigned `ChildProcessImportance::IMPORTANT` to shield them from Android Low Memory Killer (LMK) terminations.

---

## 7. How Privacy & Ad Blocking Works

1. **Engine Foundation**: Uses Chromium's native C++ **`subresource_filter`**, NOT Brave's adblock-rs.
2. **Universal Coverage**: Vanadium patch `0199-enable-subresource-filter-on-all-sites.patch` forces the subresource filter to run unconditionally on every website, bypassing Google's "Better Ads Standards" constraint.
3. **Filter Pipeline**: [`.gclient`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient) downloads AntiAdBlock, EasyList, and EasyPrivacy text lists at build time into `filter_lists_easylist.txt`, which Chromium indexes into an in-memory Trie ruleset.
4. **Network vs. Cosmetic**: Blocks ad/tracker network requests at the engine level before data transfer. Advanced cosmetic hiding is delegated to WebExtensions.
5. **Privacy Hardening**:
   - WebRTC IP leak shielding (`DisableNonProxiedUdp`) with user toggle in Settings.
   - Third-party cookies blocked by default (`Vanadium:0077`).
   - Client variations header (`X-Client-Data`) stripped (`Vanadium:0106`).
   - Strict Site Isolation enabled on Android (`Vanadium:0123`).
   - Local password database restored (`use_login_database_as_backend = true`).
   - Default search provider configured to DuckDuckGo (`Vanadium:0114`).

---

## 8. How Navigation Works

- Navigations originate from user-typed URLs, hyperlink clicks, redirects, or external intents.
- Intent entry points are filtered by Titanium's scheme guard in `LaunchIntentDispatcherHooks.java`.
- In-page clicks pass through `InterceptNavigationDelegateImpl.java`.
- Requests enter C++ `NavigationControllerImpl` and traverse the `NavigationThrottle` chain (SubresourceFilter, HSTS upgrade).
- Network requests are dispatched via Mojo `URLLoaderFactory`. On HTTP 200, bytes are committed to Blink and rendered to `WebContents`.

---

## 9. Where Custom Functionality Can Safely Be Added

1. **Branding & Visuals**: [`res/icon.svg`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.svg), [`res/icon.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.sh), [`res/drawable/themed_app_icon.xml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/drawable/themed_app_icon.xml), and string resources (`strings.grd`).
2. **Package ID**: [`args.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/args.gn) (`chrome_public_manifest_package`).
3. **Link Guard (External Intents)**: `LaunchIntentDispatcherHooks.java` (Java layer).
4. **Link Guard (In-Page Navigation)**: `InterceptNavigationDelegateImpl.java` (Java layer).
5. **New Tab Page / Dashboard**: WebExtension NTP override (`"chrome_url_overrides": { "newtab": ... }`) or `NewTabPageLayout.java`.
6. **Custom Blocklists & Feeds**: Build-time URLs in [`.gclient`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient) or dynamic JSON configs in a new Java config manager.
7. **Custom Companion Extension**: Repackaged via `extensions/bundle.py`.

---

## 10. Dangerous Areas to Avoid Modifying

1. **Blink & V8 Internal Memory Allocators**: Modifying PartitionAlloc or V8 GC logic risks fatal native memory corruption.
2. **Chromium JNI Signature Headers**: Manually modifying auto-generated `*_jni.h` files without updating the Java `@NativeMethods` interface will cause immediate crash on `JNI_OnLoad`.
3. **Relaxing the Scheme Guard**: Removing `URLUtil.isNetworkUrl` from `LaunchIntentDispatcherHooks.java` re-opens intent injection and local file exfiltration vulnerabilities.
4. **Mismatched Upstream Chromium Upgrades**: Changing `$VERSION` in `args.gn` without porting all Vanadium patches and sed patterns will break the build.
5. **Forcing Conventional Gradle Build Setup**: Attempting to compile Chromium components via `build.gradle` will corrupt dependency resolution.

---

## 11. Rebuilding Chromium vs. Modifying Android Layer

| Modification Area | Requires Full Chromium C++ Rebuild? | Can be Modified at Android Java Layer? |
| :--- | :---: | :---: |
| **App Name, Logos, Splash Screen** | ❌ (Assets only) | ✅ Yes |
| **Settings UI & Custom Preferences** | ❌ | ✅ Yes |
| **True Links Link Guard Dialogs** | ❌ | ✅ Yes (`InterceptNavigationDelegateImpl`) |
| **Remote Config & Feature Flags Service** | ❌ | ✅ Yes (Java HTTP client) |
| **Bundled Companion Extension Updates** | ❌ | ✅ Yes (`bundle.py` asset rebuild) |
| **Core Blink HTML/CSS Parser Changes** | ✅ **Yes** | ❌ No |
| **Network Service (Mojo) Interceptors** | ✅ **Yes** | ❌ No |
| **Chromium Subresource Filter Engine C++** | ✅ **Yes** | ❌ No |
| **Clang Compiler / Hardening Flags** | ✅ **Yes** | ❌ No |

---

## 12. Remote Controllability Analysis

| Feature | Remotely Controllable? | Implementation Mechanism |
| :--- | :---: | :--- |
| **Link Guard Threat Domain Feeds** | ✅ **Yes** | Sync threat lists via lightweight HTTPS JSON endpoint to local SQLite. |
| **Feature Flags / A/B Testing** | ✅ **Yes** | Fetch signed JSON configuration on app startup into `SharedPreferences`. |
| **Sponsored Link Suggestions** | ✅ **Yes** | Dynamic JSON feed rendered on New Tab Page. |
| **Companion Extension Capabilities** | ✅ **Yes** | WebExtension can fetch dynamic scripts or rules via DeclarativeNetRequest. |
| **Native Adblock Ruleset (`subresource_filter`)** | ❌ No | Hardcoded at build time in `filter_lists_easylist.txt` (requires APK update). |
| **WebRTC Default Policy** | ❌ No | Hardcoded in C++ preference defaults (user can toggle locally). |
| **Compiler Hardening / Sandboxing** | ❌ No | Fixed at compile time. |

---

## 13. Top 20 Critical Risks Summary

1. **Sed Silent Matching Failure**: `patch.sh` does not fail if sed patterns miss due to upstream code changes.
2. **Milestone Fragility**: Upstream Chromium tag shifts break patch sets.
3. **CI OOM Killer**: Clang ThinLTO requires 24–32 GB RAM.
4. **Desktop Android Layout Assumptions**: Desktop code expecting tablet anchors causes phone crashes.
5. **Upstream MV2 Code Eviction**: Google actively removing legacy MV2 source files.
6. **LMK Termination**: Aggressive Android memory management killing background extension processes.
7. **Incognito Profile Teardown UAF**: Race conditions during OTR profile deletion.
8. **Intent Injection Exploit**: Unsanitized intents accessing internal files or XSS.
9. **Build System Cognitive Mismatch**: Treating Chromium as a standard Gradle project.
10. **Play Store Keystore Loss**: Signature mismatch permanently blocking app updates.
11. **Filter List Network Drop**: Remote blocklist host outages breaking build hooks.
12. **Missing Google Play Stubs**: Runtime crashes if Google-dependent features are toggled without APIs.
13. **Unresponsive Mobile WebUI**: Desktop WebUI pages rendering broken on small screens.
14. **SAF Scoped Storage Shifts**: Android OS updates breaking unpacked extension folder loading.
15. **Predictive Back Navigation Conflicts**: Gesture back triggering tab close before page history pop.
16. **Subresource Ruleset Bloat**: Excessively large blocklists causing browser memory exhaustion.
17. **Trichrome Shared Library Conflicts**: Multi-package target build complexities.
18. **Native Crash Debuggability**: Symbol-stripped official binaries obscuring stack traces.
19. **In-Tree Clang Drift**: Host toolchain incompatibilities.
20. **Remote Config Tampering**: Man-in-the-middle manipulation of unauthenticated feature flag feeds.

---

## 14. Recommended Development Sequence for True Links

We recommend executing the transformation into **True Links Browser** across **Five Controlled Phases**:

```
PHASE 1: BRANDING & IDENTITY (Zero Risk)
   ├── Replace res/icon.svg, res/drawable/themed_app_icon.xml, and res/icon.sh
   ├── Update application strings in strings.grd ("Titanium" -> "True Links")
   └── Set package ID in args.gn (chrome_public_manifest_package = "com.truelinks.browser")
   ↓
PHASE 2: BUILD STABILIZATION & CANARY CI (Low Risk)
   ├── Set up dedicated self-hosted Linux CI runner (32GB+ RAM, 150GB SSD)
   ├── Add patch verification script (verify_patches.py) to validate patch.sh matching
   └── Compile clean baseline True Links APK and verify attestation
   ↓
PHASE 3: TRUE LINKS LINK GUARD (Medium Risk — Android Java Layer)
   ├── Intercept incoming external links in LaunchIntentDispatcherHooks.java
   ├── Intercept in-page hyperlink clicks in InterceptNavigationDelegateImpl.java
   ├── Build native Android Material Bottom Sheet UI (TrueLinksGuardBottomSheet.java)
   └── Add local threat domain cache in SQLite/SharedPreferences
   ↓
PHASE 4: REMOTE CONFIG & FEATURE CONTROLS (Medium Risk)
   ├── Implement lightweight TrueLinksConfigManager.java (REST/HTTPS client)
   ├── Add True Links settings screen in MainSettings.java
   └── Support dynamic remote toggles for Link Guard sensitivity and feature rollouts
   ↓
PHASE 5: COMPANION EXTENSION & DASHBOARD (Medium Risk)
   ├── Rebrand android-titanium-extension -> True Links Companion Extension
   ├── Package custom True Links New Tab Page dashboard with verified link bookmarks
   └── Update extensions/bundle.py to stage the new True Links CRX
```
