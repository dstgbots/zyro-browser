package com.zyro.browser.browser;

import android.graphics.Bitmap;
import android.webkit.WebView;
import com.google.android.gms.common.internal.ImagesContract;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TabManager.kt */
/* loaded from: classes5.dex */
public final class BrowserTab {
    private final long createdAt;
    private Bitmap favicon;
    private final String id;
    private boolean isDesktopMode;
    private Bitmap thumbnail;
    private String title;
    private String url;
    private WebView webView;

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: component4, reason: from getter */
    public final Bitmap getFavicon() {
        return this.favicon;
    }

    /* renamed from: component5, reason: from getter */
    public final Bitmap getThumbnail() {
        return this.thumbnail;
    }

    /* renamed from: component6, reason: from getter */
    public final WebView getWebView() {
        return this.webView;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getIsDesktopMode() {
        return this.isDesktopMode;
    }

    /* renamed from: component8, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final BrowserTab copy(String id, String title, String url, Bitmap favicon, Bitmap thumbnail, WebView webView, boolean isDesktopMode, long createdAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(url, "url");
        return new BrowserTab(id, title, url, favicon, thumbnail, webView, isDesktopMode, createdAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrowserTab)) {
            return false;
        }
        BrowserTab browserTab = (BrowserTab) other;
        return Intrinsics.areEqual(this.id, browserTab.id) && Intrinsics.areEqual(this.title, browserTab.title) && Intrinsics.areEqual(this.url, browserTab.url) && Intrinsics.areEqual(this.favicon, browserTab.favicon) && Intrinsics.areEqual(this.thumbnail, browserTab.thumbnail) && Intrinsics.areEqual(this.webView, browserTab.webView) && this.isDesktopMode == browserTab.isDesktopMode && this.createdAt == browserTab.createdAt;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int hashCode = ((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.url.hashCode()) * 31;
        Bitmap bitmap = this.favicon;
        int hashCode2 = (hashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Bitmap bitmap2 = this.thumbnail;
        int hashCode3 = (hashCode2 + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        WebView webView = this.webView;
        int hashCode4 = (hashCode3 + (webView != null ? webView.hashCode() : 0)) * 31;
        boolean z = this.isDesktopMode;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return ((hashCode4 + i) * 31) + Long.hashCode(this.createdAt);
    }

    public String toString() {
        return "BrowserTab(id=" + this.id + ", title=" + this.title + ", url=" + this.url + ", favicon=" + this.favicon + ", thumbnail=" + this.thumbnail + ", webView=" + this.webView + ", isDesktopMode=" + this.isDesktopMode + ", createdAt=" + this.createdAt + ")";
    }

    public BrowserTab(String id, String title, String url, Bitmap favicon, Bitmap thumbnail, WebView webView, boolean isDesktopMode, long createdAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(url, "url");
        this.id = id;
        this.title = title;
        this.url = url;
        this.favicon = favicon;
        this.thumbnail = thumbnail;
        this.webView = webView;
        this.isDesktopMode = isDesktopMode;
        this.createdAt = createdAt;
    }

    public /* synthetic */ BrowserTab(String str, String str2, String str3, Bitmap bitmap, Bitmap bitmap2, WebView webView, boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? "New Tab" : str2, (i & 4) != 0 ? "zyro://home" : str3, (i & 8) != 0 ? null : bitmap, (i & 16) != 0 ? null : bitmap2, (i & 32) == 0 ? webView : null, (i & 64) != 0 ? false : z, (i & 128) != 0 ? System.currentTimeMillis() : j);
    }

    public final String getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public final Bitmap getFavicon() {
        return this.favicon;
    }

    public final void setFavicon(Bitmap bitmap) {
        this.favicon = bitmap;
    }

    public final Bitmap getThumbnail() {
        return this.thumbnail;
    }

    public final void setThumbnail(Bitmap bitmap) {
        this.thumbnail = bitmap;
    }

    public final WebView getWebView() {
        return this.webView;
    }

    public final void setWebView(WebView webView) {
        this.webView = webView;
    }

    public final boolean isDesktopMode() {
        return this.isDesktopMode;
    }

    public final void setDesktopMode(boolean z) {
        this.isDesktopMode = z;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }
}
