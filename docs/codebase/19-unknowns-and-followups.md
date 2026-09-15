# 19 — Unknown Areas, Verification Gaps & Follow-Up Inquiries

> [!IMPORTANT]
> **Strict Adherence to Rule 9 & Rule 10**:
> This document explicitly catalogs components that **cannot be fully inspected from local repository files alone** and require live compilation, runtime testing, or external repository access.

---

## 1. External & Generated Dependencies Not Present Locally

| Missing / External Component | Current State in Local Repository | Source Origin | Why It Cannot Be Fully Inspected Locally |
| :--- | :--- | :--- | :--- |
| **Full Chromium Source Tree (`src/`)** | **Not Present Locally** (~50 GB) | `chromium.googlesource.com/chromium/src.git` | Chromium is fetched shallowly during CI build via Git at tag `153.0.8010.36`. Local repo contains only orchestration scripts (`build.sh`, `patch.sh`, `args.gn`). |
| **Vanadium Submodule Files** | **Empty Directory Locally** | `github.com/GrapheneOS/Vanadium` | The downloaded zip archive did not initialize Git submodules. Vanadium patches and tools were analyzed via GitHub APIs and remote inspection. |
| **Filter Lists File** | **Generated at Build Time** | `easylist.to`, `adblockplus.org` | Merged text file `filter_lists_easylist.txt` is downloaded dynamically by `filter_list_download.py` during `gclient runhooks`. |
| **Bundled Companion Extension CRX** | **Downloaded at Build Time** | `github.com/jqssun/android-titanium-extension` | The `titanium.crx` binary is fetched by `extensions/bundle.py` during build and is not checked into git. |
| **In-Tree Clang & Android SDK** | **Downloaded at Build Time** | `third_party/android_sdk`, `llvm-build` | Hermetically managed by Google depot_tools during `gclient sync`. |

---

## 2. Areas Requiring Runtime & Compilation Validation

### 1. Multi-Aspect Ratio Phone Popup Sizing
- **Code Reference**: [`patch.sh:L97-101`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh#L97-L101) sets popup dimensions using `decor.getWidth()` and `decor.getHeight()`.
- **Unknown**: How this fullscreen popup behaves on modern Android devices with display cutouts, foldables in half-open tabletop mode, or multi-window split screen mode.
- **Action Required**: Conduct on-device testing across phone form factors and split-screen configurations.

### 2. Google Play Store Policy on Off-Store Extension Ingestion
- **Code Reference**: [`patch.sh:L75`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh#L75) allows direct CRX downloading and installation from Opera Add-ons and Microsoft Edge Add-ons.
- **Unknown**: Whether distributing an app on Google Play that permits off-store browser extensions violates Google Play's "Device and Network Abuse" or "Malware / Unverified Code Execution" policies.
- **Action Required**: Review Google Play Store Developer Program Policies regarding sideloaded browser extensions prior to True Links Play Store publishing.

### 3. Companion Extension Maintenance Ownership
- **Code Reference**: [`.gclient:L45`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient#L45) points directly to `github.com/jqssun/android-titanium-extension`.
- **Unknown**: Whether `jqssun` will continue maintaining this extension, and whether True Links will create a fork repository (`github.com/truelinks/android-true-links-extension`).
- **Action Required**: Fork and rebrand `android-titanium-extension` into a True Links managed repository to prevent upstream supply-chain tampering.

### 4. Build Environment Validation on Local Machine
- **Code Reference**: [`build.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/build.sh) is written strictly for Ubuntu/Debian Linux (`apt-get`, Linux paths).
- **Current Development OS**: Windows (Host OS).
- **Unknown**: Whether the developer plans to compile True Links via GitHub Actions, WSL2 (Windows Subsystem for Linux), or a dedicated Linux workstation.
- **Action Required**: Confirm local compilation environment (WSL2 with Ubuntu 22.04/24.04 and 64GB RAM vs. CI-only builds).
