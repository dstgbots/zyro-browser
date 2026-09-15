# 18 — Architectural Glossary & Terminology Reference

This glossary defines key domain-specific terminology across Google Chromium, GrapheneOS Vanadium, Titanium Browser, and the future True Links project.

---

### Meta-Build & Toolchain Terminology

- **`depot_tools`**: Google's suite of Git extensions, Python wrappers, and build tools (including `gclient`, `gn`, `ninja`, and `autoninja`) required to check out and compile Chromium.
- **`gclient`**: Chromium's multi-repository checkout management utility configured via [`.gclient`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/.gclient).
- **GN (Generate Ninja)**: Google’s high-performance meta-build system. It consumes `BUILD.gn` and `.gni` files to generate low-level Ninja compilation graphs.
- **Ninja**: Fast, low-level build executor optimized for parallel C++ compilation.
- **Siso**: Google's next-generation build tool written in Go that optimizes distributed and local remote-execution builds (enabled in [`args.gn`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/args.gn) via `use_siso = true`).
- **`chrome_public_apk`**: The primary GN build target for Chromium on Android, compiling a complete, standalone browser APK.
- **`chrome_public_bundle`**: The GN build target producing an Android App Bundle (`.aab`) suitable for Google Play Store upload.

---

### Chromium Core Engine Concepts

- **`WebContents`**: The core Content API abstraction representing a webpage lifecycle, navigation history, and DOM document tree (`content/public/browser/web_contents.h`).
- **`RenderFrameHost`**: Native C++ representation of a single frame (top-level or iframe) in a renderer process.
- **`RenderProcessHost`**: The browser-process representation of an isolated sandboxed OS process executing web rendering and JavaScript.
- **`BrowserContext` / `Profile`**: The boundary representing a user identity and state (cookies, history, bookmarks, extensions, disk cache). Regular and Incognito profiles are distinct `Profile` instances.
- **`NavigationController`**: The session history manager responsible for forward/backward navigation lists and pending URL commits.
- **`NavigationThrottle`**: An interceptor pipeline (`content::NavigationThrottle`) that can defer, cancel, or redirect navigation events before network processing.
- **`SubresourceFilter`**: Chromium's native C++ content blocking module (`components/subresource_filter/`), repurposed by Vanadium into a universal ad blocker.

---

### Android Browser Layer Terminology

- **`ChromeTabbedActivity`**: The primary Android `Activity` hosting regular multi-tab browsing on phones and tablets.
- **`TabModel` / `TabModelSelector`**: High-level Java controllers managing the collection of active, frozen, and restored tabs.
- **`HubLayout`**: The modern Android tab-switcher and grid-card UI introduced in recent Chromium versions.
- **`ToolbarPhone`**: The native Java view managing the top address bar, reload button, security icon, and extension actions on phone form factors.
- **`CompositorViewHolder`**: The top-level Android `ViewGroup` containing the hardware-accelerated `SurfaceView` rendered by the native compositor.
- **`LaunchIntentDispatcher`**: The intent routing gateway that validates incoming Android intents before launching browser activities.
- **`InterceptNavigationDelegate`**: The Java bridge that inspects link clicks on active web pages before navigation starts.

---

### Extension & Desktop Android Terminology

- **`is_desktop_android`**: A GN compilation flag introduced by Google to enable desktop Chromium features (including extensions and DevTools) on Android tablets and external monitors.
- **CRX (CRX3)**: The binary distribution format for Chromium extensions, containing ZIP-compressed assets, an asymmetric public key, and cryptographic signature headers.
- **Manifest V2 (MV2)**: The legacy Chromium extension specification supporting blocking webRequest APIs. Restored by Titanium.
- **Manifest V3 (MV3)**: The current Chromium extension specification utilizing service workers and DeclarativeNetRequest rulesets.
- **Unpacked Extension**: An extension loaded directly from an uncompressed directory on disk rather than an installed CRX package.
- **Storage Access Framework (SAF)**: Android's file picker framework (`DocumentsContract`) used to grant scoped directory access for loading unpacked extensions.
- **`VirtualDocumentPath`**: Chromium’s Android file abstraction that maps SAF content tree URIs into POSIX-like paths for native C++ file operations.

---

### Privacy & Security Terminology

- **GrapheneOS Vanadium**: A security and privacy hardened Chromium fork developed by the GrapheneOS project, used as the base patch foundation for Titanium Browser.
- **Strict Site Isolation**: A security mechanism that forces pages from different origins into separate OS processes, preventing cross-site memory disclosure.
- **WebRTC IP Leak**: A vulnerability where WebRTC peer connections reveal real public or local IP addresses even when using VPNs or proxies. Mitigated via `DisableNonProxiedUdp`.
- **HSTS (HTTP Strict Transport Security)**: An HTTP security header forcing all connections to a domain to use HTTPS.
- **OSCrypt**: Chromium’s platform cryptographic utility that encrypts cookies and passwords at rest using keys backed by the Android Keystore.
- **SLSA Provenance / Attestation**: Cryptographic proof generated during GitHub Actions builds verifying that released binaries were built from the exact repository source code without tampering.
