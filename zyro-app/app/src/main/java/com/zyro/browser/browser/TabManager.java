package com.zyro.browser.browser;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* compiled from: TabManager.kt */
/* loaded from: classes5.dex */
public final class TabManager {
    private final Context context;
    private int currentTabIndex;
    private final List<BrowserTab> tabs;

    public TabManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.tabs = new ArrayList();
    }

    public final int getCurrentTabIndex() {
        return this.currentTabIndex;
    }

    public final BrowserTab getCurrentTab() {
        if (!this.tabs.isEmpty()) {
            int size = this.tabs.size();
            int i = this.currentTabIndex;
            if (i >= 0 && i < size) {
                return this.tabs.get(i);
            }
        }
        return null;
    }

    public final int getTabCount() {
        return this.tabs.size();
    }

    public static /* synthetic */ BrowserTab createNewTab$default(TabManager tabManager, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "zyro://home";
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return tabManager.createNewTab(str, z);
    }

    public final BrowserTab createNewTab(String url, boolean isDesktop) {
        Intrinsics.checkNotNullParameter(url, "url");
        String tabId = "tab_" + System.currentTimeMillis() + "_" + RangesKt.random(new IntRange(100, 999), Random.INSTANCE);
        WebView $this$createNewTab_u24lambda_u240 = new WebView(this.context);
        $this$createNewTab_u24lambda_u240.setLayerType(2, null);
        $this$createNewTab_u24lambda_u240.setNestedScrollingEnabled(true);
        $this$createNewTab_u24lambda_u240.setOverScrollMode(1);
        WebSettings settings = $this$createNewTab_u24lambda_u240.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "settings");
        configureWebSettings(settings);
        BrowserTab tab = new BrowserTab(tabId, Intrinsics.areEqual(url, "zyro://home") ? "New Tab" : url, url, null, null, $this$createNewTab_u24lambda_u240, isDesktop, 0L, 152, null);
        this.tabs.add(tab);
        this.currentTabIndex = this.tabs.size() - 1;
        return tab;
    }

    public final BrowserTab closeTabAt(int index) {
        if (!(index >= 0 && index < this.tabs.size())) {
            return getCurrentTab();
        }
        BrowserTab removed = this.tabs.remove(index);
        try {
            WebView webView = removed.getWebView();
            if (webView != null) {
                webView.destroy();
            }
            Bitmap thumbnail = removed.getThumbnail();
            if (thumbnail != null) {
                thumbnail.recycle();
            }
        } catch (Exception e) {
        }
        if (this.tabs.isEmpty()) {
            return createNewTab$default(this, "zyro://home", false, 2, null);
        }
        if (this.currentTabIndex >= this.tabs.size()) {
            this.currentTabIndex = this.tabs.size() - 1;
        } else {
            int i = this.currentTabIndex;
            if (index < i) {
                this.currentTabIndex = RangesKt.coerceAtLeast(i - 1, 0);
            }
        }
        return getCurrentTab();
    }

    public final BrowserTab closeCurrentTab() {
        return closeTabAt(this.currentTabIndex);
    }

    public final BrowserTab closeAllTabs() {
        for (BrowserTab tab : this.tabs) {
            try {
                WebView webView = tab.getWebView();
                if (webView != null) {
                    webView.destroy();
                }
                Bitmap thumbnail = tab.getThumbnail();
                if (thumbnail != null) {
                    thumbnail.recycle();
                }
            } catch (Exception e) {
            }
        }
        this.tabs.clear();
        return createNewTab$default(this, "zyro://home", false, 2, null);
    }

    public final BrowserTab switchTab(int index) {
        boolean z = false;
        if (index >= 0 && index < this.tabs.size()) {
            z = true;
        }
        if (z) {
            this.currentTabIndex = index;
            return this.tabs.get(index);
        }
        return null;
    }

    public final List<BrowserTab> getTabs() {
        return this.tabs;
    }

    public final BrowserTab getTab(int index) {
        return (BrowserTab) CollectionsKt.getOrNull(this.tabs, index);
    }

    private final void configureWebSettings(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setGeolocationEnabled(true);
        settings.setMixedContentMode(0);
        settings.setCacheMode(-1);
        settings.setOffscreenPreRaster(true);
    }
}
