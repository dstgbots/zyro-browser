# CODEBASE_MAP.md — Master Navigation Guide for AI Agents & Developers

> [!IMPORTANT]
> **READ THIS DOCUMENT FIRST BEFORE MAKING ANY CHANGES TO THIS REPOSITORY.**
> This repository is NOT a monolithic Android Studio or Gradle project. It is a **Chromium meta-build, patching, and distribution orchestration framework** that compiles Google Chromium with GrapheneOS Vanadium security hardening and Titanium mobile extension enhancements.

---

## 1. Quick Repository Orientation

| Location | Purpose | Key Files |
| :--- | :--- | :--- |
| **Root** | Build scripts, GN configuration, solution hooks | [`.gclient`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient), [`args.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/args.gn), [`build.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/build.sh), [`patch.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh), [`common.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/common.sh) |
| **`extensions/`** | Bundled companion extension pipeline & assets | [`bundle.py`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/bundle.py), [`BUILD.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/BUILD.gn), [`stage_bundled_extensions.inc`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/stage_bundled_extensions.inc) |
| **`res/`** | Adaptive launcher icons & vector templates | [`icon.svg`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.svg), [`icon.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.sh), [`themed_app_icon.xml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/drawable/themed_app_icon.xml) |
| **`vanadium/`** | Git submodule for GrapheneOS Vanadium patches | Upstream GrapheneOS hardening patches, site settings hooks, subresource filter tools |
| **`fastlane/`** | Google Play Store publishing metadata & graphics | Full/short descriptions, screenshots, feature graphics |
| **`.github/workflows/`** | GitHub Actions CI/CD automation | [`build.yml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.github/workflows/build.yml) |
| **`docs/codebase/`** | **Comprehensive Knowledge Base** (19 Architectural Guides) | Detailed technical specifications for every browser subsystem |

---

## 2. Knowledge Base Navigation (`/docs/codebase/`)

Future AI agents and developers should consult the following dedicated technical documents:

1. [**`01-repository-overview.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/01-repository-overview.md): High-level inventory, file classification, and component ownership.
2. [**`02-architecture.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/02-architecture.md): Layered assembly diagram, JNI communication pipeline, and rendering flow.
3. [**`03-build-system.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/03-build-system.md): GN flags, Ninja/Siso compiler arguments, toolchain setup, and signing.
4. [**`04-android-entrypoints.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/04-android-entrypoints.md): AndroidManifest, Application class, intent dispatching, and startup lifecycle.
5. [**`05-ui-architecture.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/05-ui-architecture.md): Mobile toolbar layout, extension action popups, and WebUI responsive adaptation.
6. [**`06-chromium-integration.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/06-chromium-integration.md): Engine abstractions (`WebContents`, `Profile`, `TabModel`), process hierarchy, and UAF crash fixes.
7. [**`07-navigation.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/07-navigation.md): Navigation lifecycle, scheme guards, and **True Links Link Guard insertion points**.
8. [**`08-extension-system.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/08-extension-system.md): Manifest V2/V3 support, off-store CRX installs, and zero-click bundled extension staging.
9. [**`09-privacy-and-blocking.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/09-privacy-and-blocking.md): Native `subresource_filter` ad-blocking, WebRTC leak shield, and privacy defaults.
10. [**`10-data-storage.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/10-data-storage.md): Local storage directory tree, SQLite databases, SharedPreferences, and data persistence.
11. [**`11-security.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/11-security.md): Process sandboxing, Clang compiler flags, OSCrypt, and secrets audit.
12. [**`12-dependencies.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/12-dependencies.md): Third-party libraries, licensing, and tight vs. loose coupling analysis.
13. [**`13-testing.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/13-testing.md): Current testing posture, Chromium test suites, and recommended True Links test strategy.
14. [**`14-ci-cd.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/14-ci-cd.md): GitHub Actions pipeline, compute requirements, artifact naming, and SLSA attestation.
15. [**`15-feature-matrix.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/15-feature-matrix.md): Master matrix comparing current baseline capabilities with target state.
16. [**`16-true-links-change-map.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/16-true-links-change-map.md): Architectural roadmap for transforming Titanium into True Links with risk tiers.
17. [**`17-known-risks.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/17-known-risks.md): Top 20 critical engineering risks and concrete mitigations.
18. [**`18-glossary.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/18-glossary.md): Definitive dictionary of Chromium, Android, and Titanium terminology.
19. [**`19-unknowns-and-followups.md`**](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/docs/codebase/19-unknowns-and-followups.md): External boundaries, uninspected files, and runtime validation items.

---

## 3. The Seven Golden Rules for Future AI Agents

1. **NEVER assume there is a `build.gradle` for compilation**: Compiling the browser is handled by `build.sh` using GN and Ninja. Modifying Gradle files will have zero effect on the production release.
2. **DO NOT upgrade the Chromium milestone casually**: The version tag (e.g. `153.0.8010.36`) in `vanadium/args.gn` is strictly coupled with Vanadium's 300+ patches and Titanium's sed scripts in `patch.sh`. Upgrading requires extensive patch porting.
3. **DO NOT break `patch.sh` string matches**: `sed` commands in `patch.sh` require exact string matches. Always verify target code matches the sed pattern before altering patch scripts.
4. **Preserve the Scheme Guard**: In `LaunchIntentDispatcherHooks.java`, the check `URLUtil.isNetworkUrl` prevents malicious third-party apps from executing cross-app data theft or arbitrary scripts.
5. **Implement True Links Features in Android Java**: Keep Link Guard, Remote Config, and UI customizations in the Android Java layer (`chrome/android/java/`) to avoid C++ re-compilation complexity.
6. **Use the Bundled Extension Pipeline**: If True Links requires content script injection, web page manipulation, or custom ad rules, use the WebExtension bundling pipeline (`extensions/bundle.py`) rather than modifying Blink or V8 C++ internals.
7. **Verify on Linux / CI**: Chromium cannot be natively compiled on Windows without a complex WSL2 or Linux remote builder environment. Always test builds via GitHub Actions or a dedicated Linux runner.
