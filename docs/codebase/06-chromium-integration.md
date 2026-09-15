# 06 — Chromium Engine Integration & Core Abstractions

## 1. Engine Architecture & Boundary Mapping

Titanium Browser embeds the Chromium `content` module and `chrome` layer. The diagram below illustrates how core abstractions interact between native C++ and Android Java:

```mermaid
graph TD
    subgraph JavaAndroid ["Android Application Layer (Java)"]
        CTA[ChromeTabbedActivity]
        TMS[TabModelSelector]
        TabJ[Tab / TabImpl]
        ProfJ[Profile / ProfileManager]
        WCJ[WebContents (Java Wrapper)]
        CVH[CompositorViewHolder]
    end

    subgraph NativeChrome ["Chromium Native Layer (C++)"]
        ProfC[Profile / ProfileImpl]
        ExtSys[ExtensionSystem]
        SubFilter[SubresourceFilter]
        PrefSvc[PrefService]
        WCC[WebContents / WebContentsImpl]
        NavCtrl[NavigationControllerImpl]
        RWHV[RenderWidgetHostViewAndroid]
    end

    subgraph ContentEngine ["Content Engine (C++)"]
        BC[BrowserContext]
        RPH[RenderProcessHost (Sandboxed)]
        RFH[RenderFrameHost]
        NetSvc[NetworkService / NetworkContext]
        Blink[Blink Rendering Engine]
        V8[V8 JavaScript Engine]
    end

    CTA --> TMS
    TMS --> TabJ
    TabJ --> WCJ
    CTA --> ProfJ
    ProfJ <== JNI ==> ProfC
    WCJ <== JNI ==> WCC
    ProfC --- BC
    ProfC --> ExtSys
    ProfC --> SubFilter
    ProfC --> PrefSvc
    WCC --> NavCtrl
    WCC --> RWHV
    WCC --> RFH
    RWHV <== JNI ==> CVH
    RFH --> RPH
    RPH --> Blink
    Blink --> V8
    NavCtrl --> NetSvc
```

---

## 2. Core Engine Abstractions

### 1. `BrowserContext` and `Profile`
- **Native**: `content::BrowserContext` is the foundational data isolation boundary in the Content module. In the `chrome` layer, it is subclassed as `Profile` (`chrome/browser/profiles/profile.h`).
- **Java**: `org.chromium.chrome.browser.profiles.Profile`.
- **Function**: Owns cookies, HTTP cache, local storage, extensions, bookmarks, history, and preferences.
- **Keyed Services**: Services (e.g. `ExtensionSystem`, `SubresourceFilterProfileContext`, `HistoryService`) are attached via `BrowserContextKeyedServiceFactory`.

### 2. `WebContents`
- **Native**: `content::WebContents` (`content/public/browser/web_contents.h`).
- **Java**: `org.chromium.content_public.browser.WebContents`.
- **Function**: Represents a single web document tree. It hosts the frame tree (`RenderFrameHost`), handles navigations, input event dispatching, and page lifecycle.

### 3. `Tab` and `TabModel`
- **Java**: `org.chromium.chrome.browser.tab.TabImpl` holds a reference to a `WebContents`.
- **Java**: `TabModel` maintains the ordered list of open tabs and manages active tab switching.
- **Java**: `TabModelSelector` coordinates regular and Off-The-Record (Incognito) tab models.

### 4. `NavigationController` and `NavigationThrottle`
- **Native**: `content::NavigationController` tracks session history (back/forward list).
- **Native**: `content::NavigationThrottle` provides an interceptor pipeline allowing browser components to pause, defer, cancel, or redirect navigation requests before or after network requests.

### 5. `RenderProcessHost` (Sandboxed Execution)
- Renderers execute in isolated Android unprivileged app processes (`isolated_process = true`).
- Android OS enforces SELinux and UID sandboxing on renderer processes.
- IPC between Browser and Renderer occurs via Chromium **Mojo IPC**.

---

## 3. Titanium Engine Patches & Crash Fixes

Titanium modifies core Chromium engine C++ files in [`patch.sh`](file:///v:/android-Zyro-browser-main/android-zyro-browser-main/patch.sh) to resolve race conditions and crashes introduced by running desktop extensions on Android:

### 1. Incognito Use-After-Free (UAF) Prevention
- **Upstream Chromium Issue (`crbug.com/40274462`)**: When closing Incognito tabs, the OTR profile was destroyed immediately even if background extension frames or web contents still referenced it, causing a native `SIGSEGV`.
- **Titanium Engine Fix (`patch.sh:L153-159`)**:
  1. Injects `WebContents::HasLiveWebContentsForBrowserContext(BrowserContext* browser_context)` into `content/public/browser/web_contents.h` and `web_contents_impl.cc`.
  2. Modifies `ProfileDestroyer::DestroyOTRProfileWhenAppropriateWithTimeout` in `chrome/browser/profiles/profile_destroyer.cc`:
     ```cpp
     if (content::WebContents::HasLiveWebContentsForBrowserContext(profile)) {
         return; // Aborts premature profile destruction
     }
     ```

### 2. Extension Tabs API Null Check
- **Issue (`crbug.com/431004500`)**: When extensions invoked `chrome.tabs.query` or `chrome.tabs.get` while an incognito window was closing, a null pointer dereference occurred in `tabs_api.cc`.
- **Titanium Fix (`patch.sh:L150`)**:
  ```cpp
  if (!tab_list) { continue; }
  ```

### 3. Extension Process Priority Escalation
- On mobile Android, background processes are aggressively killed by the LMK (Low Memory Killer).
- **Titanium Fix (`patch.sh:L110-112`)**:
  In `extensions/browser/extension_host.cc`:
  ```cpp
  host_contents_->SetPrimaryPageImportance(
      content::ChildProcessImportance::IMPORTANT,
      content::ChildProcessImportance::NORMAL);
  ```
  Prevents Android LMK from killing active extension background pages when navigating between tabs.
