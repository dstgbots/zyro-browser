package com.zyro.browser.model;

import androidx.core.app.FrameMetricsAggregator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppLovinAdsConfig.kt */
/* loaded from: classes9.dex */
public final class AppLovinAdsConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String appOpenAdUnitId;
    private String bannerAdUnitId;
    private boolean enabled;
    private String interstitialAdUnitId;
    private String mrecAdUnitId;
    private String nativeAdUnitId;
    private String rewardedAdUnitId;
    private String sdkKey;
    private boolean testMode;

    public AppLovinAdsConfig() {
        this(false, false, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getEnabled() {
        return this.enabled;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getTestMode() {
        return this.testMode;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSdkKey() {
        return this.sdkKey;
    }

    /* renamed from: component4, reason: from getter */
    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getMrecAdUnitId() {
        return this.mrecAdUnitId;
    }

    /* renamed from: component6, reason: from getter */
    public final String getInterstitialAdUnitId() {
        return this.interstitialAdUnitId;
    }

    /* renamed from: component7, reason: from getter */
    public final String getRewardedAdUnitId() {
        return this.rewardedAdUnitId;
    }

    /* renamed from: component8, reason: from getter */
    public final String getNativeAdUnitId() {
        return this.nativeAdUnitId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getAppOpenAdUnitId() {
        return this.appOpenAdUnitId;
    }

    public final AppLovinAdsConfig copy(boolean enabled, boolean testMode, String sdkKey, String bannerAdUnitId, String mrecAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String nativeAdUnitId, String appOpenAdUnitId) {
        Intrinsics.checkNotNullParameter(sdkKey, "sdkKey");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(mrecAdUnitId, "mrecAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        return new AppLovinAdsConfig(enabled, testMode, sdkKey, bannerAdUnitId, mrecAdUnitId, interstitialAdUnitId, rewardedAdUnitId, nativeAdUnitId, appOpenAdUnitId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppLovinAdsConfig)) {
            return false;
        }
        AppLovinAdsConfig appLovinAdsConfig = (AppLovinAdsConfig) other;
        return this.enabled == appLovinAdsConfig.enabled && this.testMode == appLovinAdsConfig.testMode && Intrinsics.areEqual(this.sdkKey, appLovinAdsConfig.sdkKey) && Intrinsics.areEqual(this.bannerAdUnitId, appLovinAdsConfig.bannerAdUnitId) && Intrinsics.areEqual(this.mrecAdUnitId, appLovinAdsConfig.mrecAdUnitId) && Intrinsics.areEqual(this.interstitialAdUnitId, appLovinAdsConfig.interstitialAdUnitId) && Intrinsics.areEqual(this.rewardedAdUnitId, appLovinAdsConfig.rewardedAdUnitId) && Intrinsics.areEqual(this.nativeAdUnitId, appLovinAdsConfig.nativeAdUnitId) && Intrinsics.areEqual(this.appOpenAdUnitId, appLovinAdsConfig.appOpenAdUnitId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    public int hashCode() {
        boolean z = this.enabled;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.testMode;
        return ((((((((((((((i + (z2 ? 1 : z2 ? 1 : 0)) * 31) + this.sdkKey.hashCode()) * 31) + this.bannerAdUnitId.hashCode()) * 31) + this.mrecAdUnitId.hashCode()) * 31) + this.interstitialAdUnitId.hashCode()) * 31) + this.rewardedAdUnitId.hashCode()) * 31) + this.nativeAdUnitId.hashCode()) * 31) + this.appOpenAdUnitId.hashCode();
    }

    public String toString() {
        return "AppLovinAdsConfig(enabled=" + this.enabled + ", testMode=" + this.testMode + ", sdkKey=" + this.sdkKey + ", bannerAdUnitId=" + this.bannerAdUnitId + ", mrecAdUnitId=" + this.mrecAdUnitId + ", interstitialAdUnitId=" + this.interstitialAdUnitId + ", rewardedAdUnitId=" + this.rewardedAdUnitId + ", nativeAdUnitId=" + this.nativeAdUnitId + ", appOpenAdUnitId=" + this.appOpenAdUnitId + ")";
    }

    public AppLovinAdsConfig(boolean enabled, boolean testMode, String sdkKey, String bannerAdUnitId, String mrecAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String nativeAdUnitId, String appOpenAdUnitId) {
        Intrinsics.checkNotNullParameter(sdkKey, "sdkKey");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(mrecAdUnitId, "mrecAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        this.enabled = enabled;
        this.testMode = testMode;
        this.sdkKey = sdkKey;
        this.bannerAdUnitId = bannerAdUnitId;
        this.mrecAdUnitId = mrecAdUnitId;
        this.interstitialAdUnitId = interstitialAdUnitId;
        this.rewardedAdUnitId = rewardedAdUnitId;
        this.nativeAdUnitId = nativeAdUnitId;
        this.appOpenAdUnitId = appOpenAdUnitId;
    }

    public /* synthetic */ AppLovinAdsConfig(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) == 0 ? z2 : true, (i & 4) != 0 ? "YOUR_APPLOVIN_SDK_KEY" : str, (i & 8) != 0 ? "YOUR_BANNER_AD_UNIT_ID" : str2, (i & 16) != 0 ? "YOUR_MREC_AD_UNIT_ID" : str3, (i & 32) != 0 ? "YOUR_INTERSTITIAL_AD_UNIT_ID" : str4, (i & 64) != 0 ? "YOUR_REWARDED_AD_UNIT_ID" : str5, (i & 128) != 0 ? "YOUR_NATIVE_AD_UNIT_ID" : str6, (i & 256) != 0 ? "YOUR_APP_OPEN_AD_UNIT_ID" : str7);
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final boolean getTestMode() {
        return this.testMode;
    }

    public final void setTestMode(boolean z) {
        this.testMode = z;
    }

    public final String getSdkKey() {
        return this.sdkKey;
    }

    public final void setSdkKey(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sdkKey = str;
    }

    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    public final void setBannerAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerAdUnitId = str;
    }

    public final String getMrecAdUnitId() {
        return this.mrecAdUnitId;
    }

    public final void setMrecAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.mrecAdUnitId = str;
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

    /* compiled from: AppLovinAdsConfig.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppLovinAdsConfig fromMap(Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("enabled");
            Boolean bool = obj instanceof Boolean ? (Boolean) obj : null;
            boolean booleanValue = bool != null ? bool.booleanValue() : true;
            Object obj2 = map.get("testMode");
            Boolean bool2 = obj2 instanceof Boolean ? (Boolean) obj2 : null;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : true;
            Object obj3 = map.get("sdkKey");
            String str = obj3 instanceof String ? (String) obj3 : null;
            if (str == null) {
                str = "YOUR_APPLOVIN_SDK_KEY";
            }
            String str2 = str;
            Object obj4 = map.get("bannerAdUnitId");
            String str3 = obj4 instanceof String ? (String) obj4 : null;
            if (str3 == null) {
                str3 = "YOUR_BANNER_AD_UNIT_ID";
            }
            String str4 = str3;
            Object obj5 = map.get("mrecAdUnitId");
            String str5 = obj5 instanceof String ? (String) obj5 : null;
            if (str5 == null) {
                str5 = "YOUR_MREC_AD_UNIT_ID";
            }
            String str6 = str5;
            Object obj6 = map.get("interstitialAdUnitId");
            String str7 = obj6 instanceof String ? (String) obj6 : null;
            if (str7 == null) {
                str7 = "YOUR_INTERSTITIAL_AD_UNIT_ID";
            }
            String str8 = str7;
            Object obj7 = map.get("rewardedAdUnitId");
            String str9 = obj7 instanceof String ? (String) obj7 : null;
            if (str9 == null) {
                str9 = "YOUR_REWARDED_AD_UNIT_ID";
            }
            String str10 = str9;
            Object obj8 = map.get("nativeAdUnitId");
            String str11 = obj8 instanceof String ? (String) obj8 : null;
            if (str11 == null) {
                str11 = "YOUR_NATIVE_AD_UNIT_ID";
            }
            String str12 = str11;
            Object obj9 = map.get("appOpenAdUnitId");
            String str13 = obj9 instanceof String ? (String) obj9 : null;
            return new AppLovinAdsConfig(booleanValue, booleanValue2, str2, str4, str6, str8, str10, str12, str13 == null ? "YOUR_APP_OPEN_AD_UNIT_ID" : str13);
        }
    }
}
