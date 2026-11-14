package com.zyro.browser.security;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: AdBlockDetector.kt */
/* loaded from: classes9.dex */
public final class AdBlockDetector {
    private static final String CANARY_AD_DOMAIN = "pagead2.googlesyndication.com";
    private static final String CONTROL_BENIGN_DOMAIN = "google.com";
    public static final AdBlockDetector INSTANCE = new AdBlockDetector();
    private static final List<String> KNOWN_ADBLOCK_HOST_PATTERNS = CollectionsKt.listOf((Object[]) new String[]{"adguard", "nextdns", "controld", "mullvad", "cleanbrowsing", "ahadns", "adhole", "alternate-dns", "decloudus", "rethinkdns", "dnsforge", "pi-hole", "pihole", "block"});
    private static final String TAG = "AdBlockDetector";

    private AdBlockDetector() {
    }

    public final Object detect(Context context, Continuation<? super AdBlockDetectionResult> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AdBlockDetector$detect$2(context, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0045 A[Catch: Exception -> 0x0172, TryCatch #0 {Exception -> 0x0172, blocks: (B:3:0x0002, B:5:0x000e, B:7:0x0014, B:9:0x001c, B:10:0x0022, B:14:0x002e, B:15:0x0034, B:17:0x0039, B:22:0x0045, B:24:0x0057, B:28:0x0092, B:31:0x00b8, B:33:0x00d5, B:35:0x00da, B:40:0x00e6, B:42:0x00fd, B:46:0x0137, B:50:0x0109, B:51:0x010d, B:53:0x0113, B:62:0x0064, B:63:0x0068, B:65:0x006e), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e6 A[Catch: Exception -> 0x0172, TryCatch #0 {Exception -> 0x0172, blocks: (B:3:0x0002, B:5:0x000e, B:7:0x0014, B:9:0x001c, B:10:0x0022, B:14:0x002e, B:15:0x0034, B:17:0x0039, B:22:0x0045, B:24:0x0057, B:28:0x0092, B:31:0x00b8, B:33:0x00d5, B:35:0x00da, B:40:0x00e6, B:42:0x00fd, B:46:0x0137, B:50:0x0109, B:51:0x010d, B:53:0x0113, B:62:0x0064, B:63:0x0068, B:65:0x006e), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.zyro.browser.security.AdBlockDetectionResult checkPrivateDns(android.content.Context r20) {
        /*
            Method dump skipped, instructions count: 409
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zyro.browser.security.AdBlockDetector.checkPrivateDns(android.content.Context):com.zyro.browser.security.AdBlockDetectionResult");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AdBlockDetectionResult checkDnsProbe() {
        boolean isInternetAlive = false;
        try {
            InetAddress[] controlAddrs = InetAddress.getAllByName(CONTROL_BENIGN_DOMAIN);
            Intrinsics.checkNotNullExpressionValue(controlAddrs, "controlAddrs");
            if (!(controlAddrs.length == 0)) {
                isInternetAlive = true;
            }
        } catch (Exception e) {
        }
        try {
        } catch (Exception e2) {
            Log.w(TAG, "DNS probe exception: " + e2.getMessage());
        }
        if (!isInternetAlive) {
            return new AdBlockDetectionResult(false, null, null, 6, null);
        }
        try {
            InetAddress[] addresses = InetAddress.getAllByName(CANARY_AD_DOMAIN);
            Intrinsics.checkNotNullExpressionValue(addresses, "addresses");
            for (InetAddress addr : addresses) {
                String ip = addr.getHostAddress();
                if (ip == null) {
                    ip = "";
                }
                if (!Intrinsics.areEqual(ip, "127.0.0.1") && !Intrinsics.areEqual(ip, "0.0.0.0") && !Intrinsics.areEqual(ip, "::1") && !Intrinsics.areEqual(ip, "::") && !addr.isLoopbackAddress() && !addr.isAnyLocalAddress()) {
                }
                Log.d(TAG, "Canary ad domain sinkholed to " + ip);
                return new AdBlockDetectionResult(true, "DNS Ad Blocker (" + ip + ")", "Ad domain resolved to local loopback sinkhole (" + ip + ").");
            }
            return new AdBlockDetectionResult(false, null, null, 6, null);
        } catch (UnknownHostException e3) {
            Log.d(TAG, "Canary ad domain blocked via NXDOMAIN");
            return new AdBlockDetectionResult(true, "DNS Filter / Sinkhole", "Ad domain was intercepted and blocked by external DNS resolver.");
        }
    }

    public final void openPrivateDnsSettings(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "private_dns_settings");
        Intent $this$openPrivateDnsSettings_u24lambda_u243 = new Intent("android.settings.WIRELESS_SETTINGS");
        $this$openPrivateDnsSettings_u24lambda_u243.putExtra(":settings:fragment_args_key", "private_dns_settings");
        $this$openPrivateDnsSettings_u24lambda_u243.putExtra(":settings:show_fragment_args", bundle);
        Unit unit = Unit.INSTANCE;
        Intent className = new Intent().setClassName("com.android.settings", "com.android.settings.Settings$NetworkDashboardActivity");
        Intrinsics.checkNotNullExpressionValue(className, "Intent().setClassName(\"c…etworkDashboardActivity\")");
        List<Intent> intents = CollectionsKt.listOf((Object[]) new Intent[]{new Intent("com.samsung.android.settings.MORE_CONNECTION_SETTINGS"), $this$openPrivateDnsSettings_u24lambda_u243, className, new Intent("android.settings.WIRELESS_SETTINGS"), new Intent("android.settings.SETTINGS")});
        for (Intent intent : intents) {
            try {
                activity.startActivity(intent);
                return;
            } catch (Exception e) {
            }
        }
    }
}
