package com.zyro.browser.browser;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ZyroChromeClient.kt */
/* loaded from: classes5.dex */
public final class ZyroChromeClient extends WebChromeClient {
    private final Function0<Unit> onHideCustomViewCallback;
    private final Function1<Integer, Unit> onProgressChangedCallback;
    private final Function1<Bitmap, Unit> onReceivedIconCallback;
    private final Function1<String, Unit> onReceivedTitleCallback;
    private final Function2<View, WebChromeClient.CustomViewCallback, Unit> onShowCustomViewCallback;
    private final Function2<ValueCallback<Uri[]>, WebChromeClient.FileChooserParams, Boolean> onShowFileChooserCallback;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ ZyroChromeClient(kotlin.jvm.functions.Function1 r8, kotlin.jvm.functions.Function1 r9, kotlin.jvm.functions.Function2 r10, kotlin.jvm.functions.Function0 r11, kotlin.jvm.functions.Function2 r12, kotlin.jvm.functions.Function1 r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r14 = r14 & 32
            if (r14 == 0) goto L7
            r13 = 0
            r6 = r13
            goto L8
        L7:
            r6 = r13
        L8:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zyro.browser.browser.ZyroChromeClient.<init>(kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ZyroChromeClient(Function1<? super Integer, Unit> onProgressChangedCallback, Function1<? super String, Unit> onReceivedTitleCallback, Function2<? super View, ? super WebChromeClient.CustomViewCallback, Unit> onShowCustomViewCallback, Function0<Unit> onHideCustomViewCallback, Function2<? super ValueCallback<Uri[]>, ? super WebChromeClient.FileChooserParams, Boolean> onShowFileChooserCallback, Function1<? super Bitmap, Unit> function1) {
        Intrinsics.checkNotNullParameter(onProgressChangedCallback, "onProgressChangedCallback");
        Intrinsics.checkNotNullParameter(onReceivedTitleCallback, "onReceivedTitleCallback");
        Intrinsics.checkNotNullParameter(onShowCustomViewCallback, "onShowCustomViewCallback");
        Intrinsics.checkNotNullParameter(onHideCustomViewCallback, "onHideCustomViewCallback");
        Intrinsics.checkNotNullParameter(onShowFileChooserCallback, "onShowFileChooserCallback");
        this.onProgressChangedCallback = onProgressChangedCallback;
        this.onReceivedTitleCallback = onReceivedTitleCallback;
        this.onShowCustomViewCallback = onShowCustomViewCallback;
        this.onHideCustomViewCallback = onHideCustomViewCallback;
        this.onShowFileChooserCallback = onShowFileChooserCallback;
        this.onReceivedIconCallback = function1;
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        this.onProgressChangedCallback.invoke(Integer.valueOf(newProgress));
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        this.onReceivedTitleCallback.invoke(title);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        Function1<Bitmap, Unit> function1 = this.onReceivedIconCallback;
        if (function1 != null) {
            function1.invoke(icon);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        this.onShowCustomViewCallback.invoke(view, callback);
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        super.onHideCustomView();
        this.onHideCustomViewCallback.invoke();
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        if (callback != null) {
            callback.invoke(origin, true, false);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return this.onShowFileChooserCallback.invoke(filePathCallback, fileChooserParams).booleanValue();
    }
}
