package com.zyro.browser.browser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.webkit.internal.AssetHelper;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.messaging.Constants;
import java.io.ByteArrayInputStream;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ZyroWebViewClient.kt */
/* loaded from: classes5.dex */
public final class ZyroWebViewClient extends WebViewClient {
    private final List<String> adFilterPatterns;
    private final byte[] emptyBytes;
    private final Function1<String, Unit> onPageFinishedCallback;
    private final Function2<String, Bitmap, Unit> onPageStartedCallback;
    private final Function1<Boolean, Unit> onSslStateChanged;
    private final Function0<Unit> onTrackerBlockedCallback;

    /* JADX WARN: Multi-variable type inference failed */
    public ZyroWebViewClient(Function2<? super String, ? super Bitmap, Unit> onPageStartedCallback, Function1<? super String, Unit> onPageFinishedCallback, Function1<? super Boolean, Unit> onSslStateChanged, Function0<Unit> onTrackerBlockedCallback) {
        Intrinsics.checkNotNullParameter(onPageStartedCallback, "onPageStartedCallback");
        Intrinsics.checkNotNullParameter(onPageFinishedCallback, "onPageFinishedCallback");
        Intrinsics.checkNotNullParameter(onSslStateChanged, "onSslStateChanged");
        Intrinsics.checkNotNullParameter(onTrackerBlockedCallback, "onTrackerBlockedCallback");
        this.onPageStartedCallback = onPageStartedCallback;
        this.onPageFinishedCallback = onPageFinishedCallback;
        this.onSslStateChanged = onSslStateChanged;
        this.onTrackerBlockedCallback = onTrackerBlockedCallback;
        this.emptyBytes = new byte[0];
        this.adFilterPatterns = CollectionsKt.listOf((Object[]) new String[]{"googlesyndication.com", "doubleclick.net", "google-analytics.com", "adservice.google.", "admob.com", "criteo.com", "taboola.com", "outbrain.com", "facebook.com/tr/", "scorecardresearch.com"});
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Uri url;
        String url2;
        if (request == null || (url = request.getUrl()) == null || (url2 = url.toString()) == null) {
            return null;
        }
        for (String pattern : this.adFilterPatterns) {
            if (StringsKt.contains((CharSequence) url2, (CharSequence) pattern, true)) {
                this.onTrackerBlockedCallback.invoke();
                return new WebResourceResponse(AssetHelper.DEFAULT_MIME_TYPE, "UTF-8", new ByteArrayInputStream(this.emptyBytes));
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        boolean isSecure = false;
        if (url != null && StringsKt.startsWith(url, "https://", true)) {
            isSecure = true;
        }
        this.onSslStateChanged.invoke(Boolean.valueOf(isSecure));
        this.onPageStartedCallback.invoke(url, favicon);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        boolean isSecure = false;
        if (url != null && StringsKt.startsWith(url, "https://", true)) {
            isSecure = true;
        }
        this.onSslStateChanged.invoke(Boolean.valueOf(isSecure));
        this.onPageFinishedCallback.invoke(url);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri url;
        String url2;
        Intent intent;
        Context context;
        if (request == null || (url = request.getUrl()) == null || (url2 = url.toString()) == null || StringsKt.startsWith(url2, "http://", true) || StringsKt.startsWith(url2, "https://", true)) {
            return false;
        }
        try {
            if (StringsKt.startsWith(url2, "intent://", true)) {
                intent = Intent.parseUri(url2, 1);
            } else {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(url2));
            }
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setFlags(268435456);
            if (view != null && (context = view.getContext()) != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        this.onSslStateChanged.invoke(false);
        if (handler != null) {
            handler.cancel();
        }
    }
}
