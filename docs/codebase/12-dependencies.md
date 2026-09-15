# 12 — Dependency Inventory & Third-Party Coupling Analysis

## 1. Overview of Dependency Architecture

Because Chromium is a self-contained meta-project, dependencies fall into three distinct tiers:
1. **Host Build-Time Dependencies**: Linux utilities and image manipulation tools required on the compile host.
2. **Meta-Build Orchestration Dependencies**: Google `depot_tools`, GrapheneOS Vanadium, and upstream Chromium source.
3. **In-Tree Hermetic Dependencies**: Compilers, SDKs, and third-party C++ libraries vendored directly by Google inside the Chromium checkout.

---

## 2. Dependency Inventory

| Dependency Name | Version / Commit | Purpose | Origin / Source | License | Coupling / Replacement Risk |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Chromium Engine (`src.git`)** | `153.0.8010.36` (Defined in `vanadium/args.gn`) | Complete core browser engine (Blink, V8, Network, UI). | Google (`chromium.googlesource.com`) | BSD-style (3-Clause) | **Core Engine**. Cannot be replaced; tightly coupled. |
| **GrapheneOS Vanadium** | Git Submodule (`main` branch) | Security hardening patches, subresource ad filtering, privacy defaults. | GrapheneOS (`github.com/GrapheneOS/Vanadium`) | GPL-2.0 | **High Coupling**. Patches are tied directly to specific Chromium milestones. |
| **`depot_tools`** | Latest Git shallow clone | Build orchestration (gclient, GN, Ninja, Siso, Python wrappers). | Google (`chromium.googlesource.com`) | BSD-style | **Build-Time Only**. Required to compile Chromium. |
| **Android SDK / NDK** | In-Tree (`third_party/android_sdk`) | Android compilation, DEX compilation, `apksigner`, platform JARs. | Google / Chromium third_party | Android Software Development Kit License | **Build-Time Only**. Hermetically downloaded via `gclient runhooks`. |
| **Google Clang / LLVM** | In-Tree (`third_party/llvm-build`) | C/C++ cross-compiler with CFI and Shadow Call Stack support. | Google / LLVM Project | Apache-2.0 with LLVM Exception | **Build-Time Only**. Pinned to exact Chromium commit. |
| **Titanium Companion Extension** | Latest GitHub Release (`titanium.crx`) | Companion WebExtension providing alternative store support and download routing. | `github.com/jqssun/android-titanium-extension` | GPL-2.0 | **Loose Coupling**. Can be replaced or updated independently via `bundle.py`. |
| **EasyList / EasyPrivacy** | Live Download (`easylist.to`) | Ad and tracker blocking rulesets for Chromium subresource filter. | EasyList Community | GPL-3.0 / CC-BY-SA | **Low Coupling**. Can be substituted with custom True Links filter lists in `.gclient`. |
| **AntiAdBlock Filters** | Live Download (`adblockplus.org`) | Anti-adblock evasion countermeasure rules. | Adblock Plus Project | GPL-3.0 | **Low Coupling**. Can be modified or augmented. |
| **ImageMagick** | Host APT package | Dynamic icon rendering and color sampling in `res/icon.sh`. | Ubuntu APT repository | ImageMagick License | **Build-Time Only**. Replaceable with SVG/PNG assets. |
| **librsvg (`rsvg-convert`)** | Host APT package | Vector-to-raster SVG conversion for Android app icons. | GNOME Project | LGPL-2.0+ | **Build-Time Only**. Replaceable. |
| **Python 3 & Pillow** | Host APT package (`python3-pillow`) | Executes build hooks, `bundle.py`, and filter list downloaders. | Python Software Foundation | PSF License | **Build-Time Only**. Standard runtime. |

---

## 3. Coupling & Replacement Safety Analysis

### 1. High Risk / Tightly Coupled
- **Chromium Milestone Upgrades**: Upgrading Chromium from M153 to M154+ is a major engineering effort. Vanadium patches and Titanium sed scripts will fail to apply if upstream Google engineers refactor line numbers, function signatures, or file paths.
- **`patch.sh` Precision**: The sed commands in `patch.sh` rely on exact string matches. Even a single whitespace change in an upstream Chromium `.cc` or `.java` file will silently fail to match, causing compilation errors or unpatched behavior.

### 2. Safe to Replace / Customize for True Links
- **Branding Assets (`res/`)**: Fully isolated. `icon.svg`, `icon.sh`, and `themed_app_icon.xml` can be replaced with True Links assets with zero risk of breaking engine compilation.
- **Companion Extension (`extensions/`)**: Fully modular. The URL and package in `.gclient` can point to a new `true-links-extension.crx`.
- **Filter Lists (`.gclient:L20-27`)**: Safe to modify. Custom URLs or blocklists can be supplied to `filter_list_download.py` without modifying C++ source.
- **GN Package Name (`args.gn`)**: Safe to alter `chrome_public_manifest_package` to `com.truelinks.browser`.
