# 01 — Repository Overview & High-Level Inventory

## 1. Executive Summary

The **Titanium Browser for Android** repository (formerly known as Helium Browser, package `io.github.jqssun.helium`) is an open-source, Chromium-based Android web browser developed by `jqssun`. It builds upon **GrapheneOS Vanadium** hardening and extends Chromium with full **Manifest V2 & V3 browser extension support on mobile form factors**, off-store CRX installation, privacy hardening, and native ad/tracker blocking.

Crucially, **this repository is NOT a monolithic checkout of the multi-gigabyte Chromium source tree**. Instead, it is an **architectural orchestration, patching, and build framework** that:
1. Clones Google's `depot_tools` and a specific tagged release of Chromium `src` (M153+).
2. Clones the GrapheneOS **Vanadium** submodule.
3. Filters, rebrands, and applies Vanadium's hardening patches via `git am`.
4. Executes build hooks to fetch updated ad-blocking filter lists (EasyList, EasyPrivacy) and the prebuilt Titanium companion extension.
5. Injects Titanium-specific native C++ and Java patches via precision sed scripting (`patch.sh`).
6. Configures Chromium build flags via GN (`args.gn`).
7. Compiles native libraries and the Android application package using Google's **GN**, **Ninja/Siso**, and Android Clang toolchains into signed APKs and AAB bundles.

---

## 2. Directory & File Inventory

```
android-zyro-browser-main/
├── .gclient                         # Gclient solution configuration & build hooks
├── .github/
│   ├── ISSUE_TEMPLATE/              # Issue and bug report templates
│   └── workflows/
│       └── build.yml                # Master GitHub Actions CI/CD pipeline
├── .gitignore                       # Repository git ignore rules
├── .gitmodules                      # Submodule definition pointing to GrapheneOS Vanadium
├── args.gn                          # Target GN build configuration flags for Chromium
├── build.sh                         # Master build orchestration script (Linux/Ubuntu)
├── common.sh                        # Shared helper functions (sed, keystore, apksigner, jarsigner)
├── extensions/
│   ├── BUILD.gn                     # GN rules to package bundled extensions into APK assets
│   ├── bundle.py                    # Python script to download, verify, and index CRX bundles
│   └── stage_bundled_extensions.inc # C++ code injected into Chromium ExternalPrefLoader
├── fastlane/
│   └── metadata/android/en-US/      # Play Store metadata, graphics, screenshots, descriptions
├── LICENSE                          # GPL-2.0 License
├── patch.sh                         # Master Titanium source patching script
├── README.md                        # Project documentation and architecture overview
├── res/
│   ├── drawable/
│   │   └── themed_app_icon.xml      # Vector adaptive themed launcher icon
│   ├── icon.sh                      # Shell script for procedural raster icon generation
│   └── icon.svg                     # Source SVG vector icon for Titanium branding
└── vanadium/                        # Git submodule directory (GrapheneOS Vanadium)
```

---

## 3. Comprehensive Component Analysis

| Path | Purpose | Technology | Origin / Ownership | True Links Impact |
| :--- | :--- | :--- | :--- | :--- |
| [`.gclient`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient) | Defines Chromium upstream repo, target OS (`android`), and pre-sync hooks (`fetch_filter_lists`, `apply_subprojects_patches`, `fetch_titanium_extension`). | Python-eval gclient DSL | Chromium / Titanium custom | **Modify** (custom filter lists, new bundled extensions or assets). |
| [`args.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/args.gn) | Master GN compilation arguments: disables VR, telemetry, sets official release, enables desktop Android flags, embeds package ID `io.github.jqssun.helium`. | GN (Generate Ninja) | Chromium / Vanadium / Titanium | **Modify** (package name, build optimization, feature flags). |
| [`build.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/build.sh) | End-to-end build pipeline script: installs APT tools, clones `depot_tools`, pulls Chromium tag, prunes/applies Vanadium patches, runs hooks, applies `patch.sh`, invokes Ninja, and signs APKs/AABs. | Bash Shell | Titanium custom | **Modify** (custom build targets, toolchain flags, True Links pipeline). |
| [`common.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/common.sh) | Key extraction, base64 keystore decoding, APK signing with `apksigner`, and AAB signing with `jarsigner`. | Bash Shell | Titanium custom | **Modify** (True Links signing keys and keystore aliases). |
| [`patch.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh) | In-place stream-editor (`sed`) modification script that alters checked-out Chromium and Vanadium code (toolbar UI, extension popups, MV2 un-deprecation, intent guards). | Bash / sed | Titanium custom | **Major Modification** (Primary locus for custom True Links changes before compilation). |
| [`extensions/bundle.py`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/bundle.py) | Python script that downloads `.crx` files, decodes CRX3 protobuf headers, extracts extension public key hash / ID, and generates `bundled.json`. | Python 3 | Titanium custom | **Keep / Extend** (Can be reused to bundle True Links companion extensions). |
| [`extensions/stage_bundled_extensions.inc`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/stage_bundled_extensions.inc) | C++ source included into Chromium's `external_pref_loader.cc`; unpacks bundled CRX from APK assets directly to device disk on startup. | C++ (Chromium base API) | Titanium custom | **Keep / Extend** (Core mechanism for shipping zero-click extensions). |
| [`extensions/BUILD.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/extensions/BUILD.gn) | Declares `extension_assets` target for packaging extension CRX and metadata into Android APK assets. | GN | Titanium custom | **Keep / Extend**. |
| [`res/icon.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.sh) | Script using ImageMagick and `librsvg` to generate adaptive icons across phone display buckets (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi). | Shell / ImageMagick | Titanium custom | **Replace** (Replace with True Links icon generator and brand assets). |
| [`res/icon.svg`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/icon.svg) | Master SVG brand artwork for Titanium Browser. | SVG XML | Titanium custom | **Replace** (True Links brand logo). |
| [`res/drawable/themed_app_icon.xml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/res/drawable/themed_app_icon.xml) | Monochrome adaptive icon for Android 13+ launcher dynamic theming. | Android Vector Drawable | Titanium custom | **Replace** (True Links monochrome logo). |
| [`vanadium/`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/vanadium) | Git submodule pointing to `GrapheneOS/Vanadium.git`. Supplies security hardening patches, site settings hooks, subresource filter integration, and DDG defaults. | Git Submodule (C++, Java, Python) | GrapheneOS Project | **Keep / Selectively Patch** (Base foundation of browser engine). |
| [`.github/workflows/build.yml`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.github/workflows/build.yml) | GitHub Actions CI definition: checks out submodule, pulls latest Vanadium tag, executes `build.sh`, tags release, signs release, publishes APKs/AAB, and generates attestation. | GitHub Actions YAML | Titanium custom | **Modify** (True Links repository CI/CD and publishing targets). |
| [`fastlane/metadata/`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/fastlane/metadata) | Play Store metadata, promotional text, descriptions, and feature graphics. | Text / PNG | Titanium custom | **Replace** (True Links marketing and store listings). |

---

## 4. Key Architectural Observations

1. **No Local Gradle Project**: Unlike conventional Android applications built with Android Studio and Gradle, Titanium Browser is compiled using **Chromium's native meta-build system (GN + Ninja/Siso)**. The APK is built as the `chrome_public_apk` GN target.
2. **Dynamic In-Tree Patching**: Titanium does not maintain a massive fork repository with millions of Chromium commits. It adopts a lightweight **upstream-tracking patch architecture**. When building, it downloads clean Google Chromium, applies GrapheneOS Vanadium patches, and then applies Titanium's surgical modifications.
3. **Dual Component Topology**: Advanced browser features are divided between the **Browser Core** (Chromium/C++/Android Java) and the **Titanium Companion Extension** (`android-titanium-extension`), loaded via `extensions/bundle.py` and `stage_bundled_extensions.inc`.
