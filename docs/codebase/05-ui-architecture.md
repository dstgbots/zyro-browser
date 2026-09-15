# 05 — UI Architecture & Visual Component Mapping

## 1. UI Layer Taxonomy

Titanium's user interface is a hybrid architecture composed of:
1. **Android Native Views (Java/XML)**: Application chrome, top toolbar, phone extension action container, bottom navigation bars, and native dialogs.
2. **Chromium Composited Views (C++/Java Surface)**: Tab strip (tablet), compositor animations, surface layer rendering.
3. **WebUI (HTML5/TypeScript/Polymer)**: Internal pages (`chrome://extensions`, `chrome://flags`, `chrome://history`, `chrome://chrome-urls`).
4. **Rendered Web Content**: Standard web pages inside `WebContents`.

```mermaid
graph TD
    subgraph AndroidViewHierarchy ["Android View Hierarchy (Native Java)"]
        CTA[ChromeTabbedActivity]
        Root[DecorView / RootFrameLayout]
        CVH[CompositorViewHolder]
        Toolbar[ToolbarManager / ToolbarPhone]
        ExtContainer[extensions_toolbar_container (Titanium Injected)]
        Omnibox[LocationBarCoordinator / UrlBar]
        BottomBar[BottomControlsCoordinator (Optional)]
    end

    subgraph NativeSurfaces ["Hardware Composited Layers (C++)"]
        Surface[SurfaceView / HardwareBuffer]
        Viz[Viz Display Compositor]
        TabCompositor[TabSwitcher / HubLayout]
    end

    subgraph WebInterfaces ["Web-Based Pages (HTML/JS/WebUI)"]
        ExtWebUI["chrome://extensions (Responsive Mobile CSS via patch.sh)"]
        FlagsWebUI["chrome://flags (Experimentation Engine)"]
        UrlsWebUI["chrome://chrome-urls"]
    end

    CTA --> Root
    Root --> CVH
    Root --> Toolbar
    Toolbar --> Omnibox
    Toolbar --> ExtContainer
    Root --> BottomBar
    CVH --> Surface
    Viz --> Surface
    Surface --> TabCompositor
    Surface --> ExtWebUI
    Surface --> FlagsWebUI
    Surface --> UrlsWebUI
```

---

## 2. Component-by-Component Mapping

| UI Component | Implementation Layer | Primary Source File(s) | Technology | Titanium-Specific Overrides |
| :--- | :--- | :--- | :--- | :--- |
| **Phone Toolbar** | Android Native (View) | `chrome/browser/ui/android/toolbar/.../ToolbarPhone.java`<br>`.../res/layout/toolbar_phone.xml` | Java / Android XML | Injects `extensions_toolbar_container_stub` into layout; draws extensions container in `ToolbarPhone.draw()` (`patch.sh:L79-88`). |
| **Tablet Toolbar** | Android Native (View) | `chrome/browser/ui/android/toolbar/.../ToolbarTablet.java` | Java / Android XML | Upstream desktop Android extensions were tablet-only; Titanium decouples this in `ToolbarManager.java` (`patch.sh:L87`). |
| **Address Bar (Omnibox)** | Android Native (View) | `chrome/browser/ui/android/omnibox/.../LocationBarCoordinator.java`<br>`.../UrlBar.java` | Java | Restores mobile zero-suggest provider in `zero_suggest_verbatim_match_provider.cc` & `autocomplete_result.cc` (`patch.sh:L134-137`). |
| **Extension Toolbar Icon List** | Android Native (View) | `chrome/browser/ui/android/toolbar/.../ExtensionActionListCoordinator.java`<br>`.../ExtensionActionListMediator.java` | Java (RecyclerView) | Exposes `getContainerView()`; anchors popups to toolbar on phones without crash (`patch.sh:L91-95`). |
| **Extension Action Popup** | Android Native + C++ | `chrome/browser/ui/android/toolbar/.../ExtensionActionPopup.java`<br>`chrome/browser/ui/android/extensions/.../extension_action_popup_contents.cc` | Java / C++ / JNI | Auto-resizes extension popup to full window width/height on phones (`patch.sh:L97-101`). |
| **App Menu (3-dots)** | Android Native | `chrome/android/java/.../tabbed_mode/TabbedAppMenuPropertiesDelegate.java` | Java | Restores single-window Incognito options when `is_desktop_android` is enabled (`patch.sh:L140-141`). |
| **Tab Switcher (Hub)** | Android Native / Compositor | `chrome/android/java/.../hub/HubLayout.java`<br>`chrome/android/java/.../tabmodel/TabModelSelector.java` | Java / OpenGL | Upstream Chromium Hub architecture; tab grid cards and incognito switcher. |
| **New Tab Page (NTP)** | Android Native / Web | `chrome/android/java/.../ntp/NewTabPage.java`<br>`chrome/android/java/.../ChromeTabbedActivity.java` | Java / HTML | Bypasses native NTP if extension overrides NTP (`UrlOverrideUtils.isNtpOverrideEnabled()`, `patch.sh:L127-132`). |
| **Settings (Main)** | Android Native (Jetpack) | `chrome/android/java/.../settings/MainSettings.java` | Java / Preference | Core Chromium preference hierarchy. |
| **Privacy Settings** | Android Native (Jetpack) | `chrome/android/java/.../privacy/settings/PrivacySettings.java`<br>`titanium/.../PrivacySettingsExt.java` | Java / Preference | Vanadium hooks `PrivacySettingsExt` to add WebRTC toggle, close tabs on exit, and hide telemetry settings (`patch.sh:L21`). |
| **Extension Manager** | WebUI | `chrome/browser/resources/extensions/extensions.html`<br>`.../item_list.css`, `.../toggle_row.css` | WebUI (Polymer/TS) | Injects `<meta name="viewport" content="width=device-width">`, adjusts card width to 96% and enables vertical scrolling (`patch.sh:L63-68`). |
| **Bookmarks** | Android Native | `chrome/android/java/.../bookmarks/BookmarkManagerCoordinator.java` | Java | Native bookmark tree and search. |
| **History** | WebUI / Native Hybrid | `chrome/browser/resources/history/` | WebUI / Java | Native Android coordinator loading WebUI history manager. |
| **Downloads** | Android Native | `chrome/android/java/.../download/DownloadManagerCoordinator.java` | Java | Native list of downloaded files with SAF integration. |

---

## 3. Extension Phone UI Adaptation Architecture

Upstream Google Chromium developed the Android Extension UI (`chrome/browser/ui/android/extensions/`) specifically for large-screen tablets running `is_desktop_android = true`. On phones, this causes severe crashes due to null view anchors and desktop-sized popups.

Titanium solves this through five synchronized patches:
1. **Layout Injection**: `toolbar_phone.xml` receives `<ViewStub android:id="@+id/extensions_toolbar_container_stub" ... />` directly adjacent to the app menu button.
2. **Canvas Painting**: In `ToolbarPhone.java`, when drawing the top toolbar canvas, the extension container view is translated and explicitly rendered.
3. **Null Anchor Guarding**: In `ExtensionActionListMediator.java`, if `buttonView == null` (phone collapsed layout), it falls back to `mRecyclerViewDelegate.getContainerView()`.
4. **Fullscreen Mobile Popups**: In `extension_action_popup_contents.cc`, if `ui::GetDeviceFormFactor() == ui::DEVICE_FORM_FACTOR_PHONE`, desktop `EnableAutoResize` is bypassed and `Java_ExtensionActionPopupContents_resizeDueToAutoResize` is triggered with max bounds (100000x100000). In `ExtensionActionPopup.java`, popup size is set to the full window decor view dimensions (`decor.getWidth()`, `decor.getHeight()`).
5. **Mobile-Responsive WebUI**: In `chrome/browser/resources/extensions/extensions.html`, a standard mobile viewport is injected, setting extension card widths to `96%` instead of hardcoded `400px`.
