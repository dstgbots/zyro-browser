package com.zyro.browser.model;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GoogleAdsConfig.kt */
/* loaded from: classes9.dex */
public final class GoogleAdsConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean adsEnabled;
    private String appId;
    private String appOpenAdUnitId;
    private String bannerAdUnitId;
    private String bannerPosition;
    private String interstitialAdUnitId;
    private int interstitialInterval;
    private String nativeAdUnitId;
    private String rewardedAdUnitId;
    private String rewardedInterstitialAdUnitId;
    private boolean safelinkGoogleAdsEnabled;
    private boolean testMode;

    public GoogleAdsConfig() {
        this(false, false, null, null, null, null, null, null, null, 0, null, false, 4095, null);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getAdsEnabled() {
        return this.adsEnabled;
    }

    /* renamed from: component10, reason: from getter */
    public final int getInterstitialInterval() {
        return this.interstitialInterval;
    }

    /* renamed from: component11, reason: from getter */
    public final String getBannerPosition() {
        return this.bannerPosition;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean getSafelinkGoogleAdsEnabled() {
        return this.safelinkGoogleAdsEnabled;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getTestMode() {
        return this.testMode;
    }

    /* renamed from: component3, reason: from getter */
    public final String getAppId() {
        return this.appId;
    }

    /* renamed from: component4, reason: from getter */
    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getInterstitialAdUnitId() {
        return this.interstitialAdUnitId;
    }

    /* renamed from: component6, reason: from getter */
    public final String getRewardedAdUnitId() {
        return this.rewardedAdUnitId;
    }

    /* renamed from: component7, reason: from getter */
    public final String getRewardedInterstitialAdUnitId() {
        return this.rewardedInterstitialAdUnitId;
    }

    /* renamed from: component8, reason: from getter */
    public final String getNativeAdUnitId() {
        return this.nativeAdUnitId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getAppOpenAdUnitId() {
        return this.appOpenAdUnitId;
    }

    public final GoogleAdsConfig copy(boolean adsEnabled, boolean testMode, String appId, String bannerAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String rewardedInterstitialAdUnitId, String nativeAdUnitId, String appOpenAdUnitId, int interstitialInterval, String bannerPosition, boolean safelinkGoogleAdsEnabled) {
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedInterstitialAdUnitId, "rewardedInterstitialAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        Intrinsics.checkNotNullParameter(bannerPosition, "bannerPosition");
        return new GoogleAdsConfig(adsEnabled, testMode, appId, bannerAdUnitId, interstitialAdUnitId, rewardedAdUnitId, rewardedInterstitialAdUnitId, nativeAdUnitId, appOpenAdUnitId, interstitialInterval, bannerPosition, safelinkGoogleAdsEnabled);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GoogleAdsConfig)) {
            return false;
        }
        GoogleAdsConfig googleAdsConfig = (GoogleAdsConfig) other;
        return this.adsEnabled == googleAdsConfig.adsEnabled && this.testMode == googleAdsConfig.testMode && Intrinsics.areEqual(this.appId, googleAdsConfig.appId) && Intrinsics.areEqual(this.bannerAdUnitId, googleAdsConfig.bannerAdUnitId) && Intrinsics.areEqual(this.interstitialAdUnitId, googleAdsConfig.interstitialAdUnitId) && Intrinsics.areEqual(this.rewardedAdUnitId, googleAdsConfig.rewardedAdUnitId) && Intrinsics.areEqual(this.rewardedInterstitialAdUnitId, googleAdsConfig.rewardedInterstitialAdUnitId) && Intrinsics.areEqual(this.nativeAdUnitId, googleAdsConfig.nativeAdUnitId) && Intrinsics.areEqual(this.appOpenAdUnitId, googleAdsConfig.appOpenAdUnitId) && this.interstitialInterval == googleAdsConfig.interstitialInterval && Intrinsics.areEqual(this.bannerPosition, googleAdsConfig.bannerPosition) && this.safelinkGoogleAdsEnabled == googleAdsConfig.safelinkGoogleAdsEnabled;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    public int hashCode() {
        boolean z = this.adsEnabled;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r3 = this.testMode;
        int i2 = r3;
        if (r3 != 0) {
            i2 = 1;
        }
        int hashCode = (((((((((((((((((((i + i2) * 31) + this.appId.hashCode()) * 31) + this.bannerAdUnitId.hashCode()) * 31) + this.interstitialAdUnitId.hashCode()) * 31) + this.rewardedAdUnitId.hashCode()) * 31) + this.rewardedInterstitialAdUnitId.hashCode()) * 31) + this.nativeAdUnitId.hashCode()) * 31) + this.appOpenAdUnitId.hashCode()) * 31) + Integer.hashCode(this.interstitialInterval)) * 31) + this.bannerPosition.hashCode()) * 31;
        boolean z2 = this.safelinkGoogleAdsEnabled;
        return hashCode + (z2 ? 1 : z2 ? 1 : 0);
    }

    public String toString() {
        return "GoogleAdsConfig(adsEnabled=" + this.adsEnabled + ", testMode=" + this.testMode + ", appId=" + this.appId + ", bannerAdUnitId=" + this.bannerAdUnitId + ", interstitialAdUnitId=" + this.interstitialAdUnitId + ", rewardedAdUnitId=" + this.rewardedAdUnitId + ", rewardedInterstitialAdUnitId=" + this.rewardedInterstitialAdUnitId + ", nativeAdUnitId=" + this.nativeAdUnitId + ", appOpenAdUnitId=" + this.appOpenAdUnitId + ", interstitialInterval=" + this.interstitialInterval + ", bannerPosition=" + this.bannerPosition + ", safelinkGoogleAdsEnabled=" + this.safelinkGoogleAdsEnabled + ")";
    }

    public GoogleAdsConfig(boolean adsEnabled, boolean testMode, String appId, String bannerAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String rewardedInterstitialAdUnitId, String nativeAdUnitId, String appOpenAdUnitId, int interstitialInterval, String bannerPosition, boolean safelinkGoogleAdsEnabled) {
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedInterstitialAdUnitId, "rewardedInterstitialAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        Intrinsics.checkNotNullParameter(bannerPosition, "bannerPosition");
        this.adsEnabled = adsEnabled;
        this.testMode = testMode;
        this.appId = appId;
        this.bannerAdUnitId = bannerAdUnitId;
        this.interstitialAdUnitId = interstitialAdUnitId;
        this.rewardedAdUnitId = rewardedAdUnitId;
        this.rewardedInterstitialAdUnitId = rewardedInterstitialAdUnitId;
        this.nativeAdUnitId = nativeAdUnitId;
        this.appOpenAdUnitId = appOpenAdUnitId;
        this.interstitialInterval = interstitialInterval;
        this.bannerPosition = bannerPosition;
        this.safelinkGoogleAdsEnabled = safelinkGoogleAdsEnabled;
    }

    public /* synthetic */ GoogleAdsConfig(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, String str8, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? true : z, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? "ca-app-pub-3940256099942544~3347511713" : str, (i2 & 8) != 0 ? "ca-app-pub-3940256099942544/9214589741" : str2, (i2 & 16) != 0 ? "ca-app-pub-3940256099942544/1033173712" : str3, (i2 & 32) != 0 ? GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK : str4, (i2 & 64) != 0 ? "ca-app-pub-3940256099942544/5354046379" : str5, (i2 & 128) != 0 ? "ca-app-pub-3940256099942544/2247696110" : str6, (i2 & 256) != 0 ? "ca-app-pub-3940256099942544/9257395921" : str7, (i2 & 512) != 0 ? 3 : i, (i2 & 1024) != 0 ? "BOTTOM" : str8, (i2 & 2048) == 0 ? z3 : true);
    }

    public final boolean getAdsEnabled() {
        return this.adsEnabled;
    }

    public final void setAdsEnabled(boolean z) {
        this.adsEnabled = z;
    }

    public final boolean getTestMode() {
        return this.testMode;
    }

    public final void setTestMode(boolean z) {
        this.testMode = z;
    }

    public final String getAppId() {
        return this.appId;
    }

    public final void setAppId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appId = str;
    }

    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    public final void setBannerAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerAdUnitId = str;
    }

    public final String getInterstitialAdUnitId() {
        return this.interstitialAdUnitId;
    }

    public final void setInterstitialAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.interstitialAdUnitId = str;
    }

    public final String getRewardedAdUnitId() {
        return this.rewardedAdUnitId;
    }

    public final void setRewardedAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rewardedAdUnitId = str;
    }

    public final String getRewardedInterstitialAdUnitId() {
        return this.rewardedInterstitialAdUnitId;
    }

    public final void setRewardedInterstitialAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rewardedInterstitialAdUnitId = str;
    }

    public final String getNativeAdUnitId() {
        return this.nativeAdUnitId;
    }

    public final void setNativeAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nativeAdUnitId = str;
    }

    public final String getAppOpenAdUnitId() {
        return this.appOpenAdUnitId;
    }

    public final void setAppOpenAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appOpenAdUnitId = str;
    }

    public final int getInterstitialInterval() {
        return this.interstitialInterval;
    }

    public final void setInterstitialInterval(int i) {
        this.interstitialInterval = i;
    }

    public final String getBannerPosition() {
        return this.bannerPosition;
    }

    public final void setBannerPosition(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerPosition = str;
    }

    public final boolean getSafelinkGoogleAdsEnabled() {
        return this.safelinkGoogleAdsEnabled;
    }

    public final void setSafelinkGoogleAdsEnabled(boolean z) {
        this.safelinkGoogleAdsEnabled = z;
    }

    public final Map<String, Object> toMap() {
        return MapsKt.mapOf(TuplesKt.to("adsEnabled", Boolean.valueOf(this.adsEnabled)), TuplesKt.to("testMode", Boolean.valueOf(this.testMode)), TuplesKt.to("appId", this.appId), TuplesKt.to("bannerAdUnitId", this.bannerAdUnitId), TuplesKt.to("interstitialAdUnitId", this.interstitialAdUnitId), TuplesKt.to("rewardedAdUnitId", this.rewardedAdUnitId), TuplesKt.to("rewardedInterstitialAdUnitId", this.rewardedInterstitialAdUnitId), TuplesKt.to("nativeAdUnitId", this.nativeAdUnitId), TuplesKt.to("appOpenAdUnitId", this.appOpenAdUnitId), TuplesKt.to("interstitialInterval", Integer.valueOf(this.interstitialInterval)), TuplesKt.to("bannerPosition", this.bannerPosition), TuplesKt.to("safelinkGoogleAdsEnabled", Boolean.valueOf(this.safelinkGoogleAdsEnabled)), TuplesKt.to("updatedAt", Long.valueOf(System.currentTimeMillis())));
    }

    /* compiled from: GoogleAdsConfig.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GoogleAdsConfig fromMap(Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("adsEnabled");
            Boolean bool = obj instanceof Boolean ? (Boolean) obj : null;
            boolean booleanValue = bool != null ? bool.booleanValue() : true;
            Object obj2 = map.get("testMode");
            Boolean bool2 = obj2 instanceof Boolean ? (Boolean) obj2 : null;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : true;
            Object obj3 = map.get("appId");
            String str = obj3 instanceof String ? (String) obj3 : null;
            if (str == null) {
                str = "ca-app-pub-3940256099942544~3347511713";
            }
            String str2 = str;
            Object obj4 = map.get("bannerAdUnitId");
            String str3 = obj4 instanceof String ? (String) obj4 : null;
            if (str3 == null) {
                str3 = "ca-app-pub-3940256099942544/9214589741";
            }
            String str4 = str3;
            Object obj5 = map.get("interstitialAdUnitId");
            String str5 = obj5 instanceof String ? (String) obj5 : null;
            if (str5 == null) {
                str5 = "ca-app-pub-3940256099942544/1033173712";
            }
            String str6 = str5;
            Object obj6 = map.get("rewardedAdUnitId");
            String str7 = obj6 instanceof String ? (String) obj6 : null;
            if (str7 == null) {
                str7 = GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK;
            }
            String str8 = str7;
            Object obj7 = map.get("rewardedInterstitialAdUnitId");
            String str9 = obj7 instanceof String ? (String) obj7 : null;
            if (str9 == null) {
                str9 = "ca-app-pub-3940256099942544/5354046379";
            }
            String str10 = str9;
            Object obj8 = map.get("nativeAdUnitId");
            String str11 = obj8 instanceof String ? (String) obj8 : null;
            if (str11 == null) {
                str11 = "ca-app-pub-3940256099942544/2247696110";
            }
            String str12 = str11;
            Object obj9 = map.get("appOpenAdUnitId");
            String str13 = obj9 instanceof String ? (String) obj9 : null;
            if (str13 == null) {
                str13 = "ca-app-pub-3940256099942544/9257395921";
            }
            String str14 = str13;
            Object obj10 = map.get("interstitialInterval");
            Number number = obj10 instanceof Number ? (Number) obj10 : null;
            int intValue = number != null ? number.intValue() : 3;
            Object obj11 = map.get("bannerPosition");
            String str15 = obj11 instanceof String ? (String) obj11 : null;
            if (str15 == null) {
                str15 = "BOTTOM";
            }
            String str16 = str15;
            Object obj12 = map.get("safelinkGoogleAdsEnabled");
            Boolean bool3 = obj12 instanceof Boolean ? (Boolean) obj12 : null;
            return new GoogleAdsConfig(booleanValue, booleanValue2, str2, str4, str6, str8, str10, str12, str14, intValue, str16, bool3 != null ? bool3.booleanValue() : true);
        }
    }
}
