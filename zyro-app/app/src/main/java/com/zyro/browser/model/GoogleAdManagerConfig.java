package com.zyro.browser.model;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GoogleAdManagerConfig.kt */
/* loaded from: classes9.dex */
public final class GoogleAdManagerConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DEMO_ADAPTIVE_BANNER = "/21775744923/example/adaptive-banner";
    public static final String DEMO_APP_OPEN = "/21775744923/example/app-open";
    public static final String DEMO_FIXED_BANNER = "/21775744923/example/fixed-size-banner";
    public static final String DEMO_INTERSTITIAL = "/21775744923/example/interstitial";
    public static final String DEMO_INTERSTITIAL_ALT = "/6499/example/interstitial";
    public static final String DEMO_NATIVE = "/21775744923/example/native";
    public static final String DEMO_REWARDED = "/21775744923/example/rewarded";
    public static final String DEMO_REWARDED_FALLBACK = "ca-app-pub-3940256099942544/5224354917";
    public static final String DEMO_REWARDED_INTERSTITIAL = "/21775744923/example/rewarded-interstitial";
    private boolean adsEnabled;
    private String appOpenAdUnitId;
    private String bannerAdUnitId;
    private String bannerPosition;
    private Map<String, String> customTargeting;
    private boolean enableAdInspector;
    private String fixedBannerAdUnitId;
    private String interstitialAdUnitId;
    private int interstitialInterval;
    private String nativeAdUnitId;
    private String networkCode;
    private String rewardedAdUnitId;
    private String rewardedInterstitialAdUnitId;
    private boolean safelinkGamAdsEnabled;
    private boolean testMode;
    private long updatedAt;

    public GoogleAdManagerConfig() {
        this(false, false, null, null, null, null, null, null, null, null, null, 0, null, false, false, 0L, 65535, null);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getAdsEnabled() {
        return this.adsEnabled;
    }

    /* renamed from: component10, reason: from getter */
    public final String getAppOpenAdUnitId() {
        return this.appOpenAdUnitId;
    }

    public final Map<String, String> component11() {
        return this.customTargeting;
    }

    /* renamed from: component12, reason: from getter */
    public final int getInterstitialInterval() {
        return this.interstitialInterval;
    }

    /* renamed from: component13, reason: from getter */
    public final String getBannerPosition() {
        return this.bannerPosition;
    }

    /* renamed from: component14, reason: from getter */
    public final boolean getSafelinkGamAdsEnabled() {
        return this.safelinkGamAdsEnabled;
    }

    /* renamed from: component15, reason: from getter */
    public final boolean getEnableAdInspector() {
        return this.enableAdInspector;
    }

    /* renamed from: component16, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getTestMode() {
        return this.testMode;
    }

    /* renamed from: component3, reason: from getter */
    public final String getNetworkCode() {
        return this.networkCode;
    }

    /* renamed from: component4, reason: from getter */
    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getFixedBannerAdUnitId() {
        return this.fixedBannerAdUnitId;
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
    public final String getRewardedInterstitialAdUnitId() {
        return this.rewardedInterstitialAdUnitId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getNativeAdUnitId() {
        return this.nativeAdUnitId;
    }

    public final GoogleAdManagerConfig copy(boolean adsEnabled, boolean testMode, String networkCode, String bannerAdUnitId, String fixedBannerAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String rewardedInterstitialAdUnitId, String nativeAdUnitId, String appOpenAdUnitId, Map<String, String> customTargeting, int interstitialInterval, String bannerPosition, boolean safelinkGamAdsEnabled, boolean enableAdInspector, long updatedAt) {
        Intrinsics.checkNotNullParameter(networkCode, "networkCode");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(fixedBannerAdUnitId, "fixedBannerAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedInterstitialAdUnitId, "rewardedInterstitialAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        Intrinsics.checkNotNullParameter(customTargeting, "customTargeting");
        Intrinsics.checkNotNullParameter(bannerPosition, "bannerPosition");
        return new GoogleAdManagerConfig(adsEnabled, testMode, networkCode, bannerAdUnitId, fixedBannerAdUnitId, interstitialAdUnitId, rewardedAdUnitId, rewardedInterstitialAdUnitId, nativeAdUnitId, appOpenAdUnitId, customTargeting, interstitialInterval, bannerPosition, safelinkGamAdsEnabled, enableAdInspector, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GoogleAdManagerConfig)) {
            return false;
        }
        GoogleAdManagerConfig googleAdManagerConfig = (GoogleAdManagerConfig) other;
        return this.adsEnabled == googleAdManagerConfig.adsEnabled && this.testMode == googleAdManagerConfig.testMode && Intrinsics.areEqual(this.networkCode, googleAdManagerConfig.networkCode) && Intrinsics.areEqual(this.bannerAdUnitId, googleAdManagerConfig.bannerAdUnitId) && Intrinsics.areEqual(this.fixedBannerAdUnitId, googleAdManagerConfig.fixedBannerAdUnitId) && Intrinsics.areEqual(this.interstitialAdUnitId, googleAdManagerConfig.interstitialAdUnitId) && Intrinsics.areEqual(this.rewardedAdUnitId, googleAdManagerConfig.rewardedAdUnitId) && Intrinsics.areEqual(this.rewardedInterstitialAdUnitId, googleAdManagerConfig.rewardedInterstitialAdUnitId) && Intrinsics.areEqual(this.nativeAdUnitId, googleAdManagerConfig.nativeAdUnitId) && Intrinsics.areEqual(this.appOpenAdUnitId, googleAdManagerConfig.appOpenAdUnitId) && Intrinsics.areEqual(this.customTargeting, googleAdManagerConfig.customTargeting) && this.interstitialInterval == googleAdManagerConfig.interstitialInterval && Intrinsics.areEqual(this.bannerPosition, googleAdManagerConfig.bannerPosition) && this.safelinkGamAdsEnabled == googleAdManagerConfig.safelinkGamAdsEnabled && this.enableAdInspector == googleAdManagerConfig.enableAdInspector && this.updatedAt == googleAdManagerConfig.updatedAt;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v24, types: [boolean] */
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
        int hashCode = (((((((((((((((((((((((i + i2) * 31) + this.networkCode.hashCode()) * 31) + this.bannerAdUnitId.hashCode()) * 31) + this.fixedBannerAdUnitId.hashCode()) * 31) + this.interstitialAdUnitId.hashCode()) * 31) + this.rewardedAdUnitId.hashCode()) * 31) + this.rewardedInterstitialAdUnitId.hashCode()) * 31) + this.nativeAdUnitId.hashCode()) * 31) + this.appOpenAdUnitId.hashCode()) * 31) + this.customTargeting.hashCode()) * 31) + Integer.hashCode(this.interstitialInterval)) * 31) + this.bannerPosition.hashCode()) * 31;
        ?? r32 = this.safelinkGamAdsEnabled;
        int i3 = r32;
        if (r32 != 0) {
            i3 = 1;
        }
        int i4 = (hashCode + i3) * 31;
        boolean z2 = this.enableAdInspector;
        return ((i4 + (z2 ? 1 : z2 ? 1 : 0)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "GoogleAdManagerConfig(adsEnabled=" + this.adsEnabled + ", testMode=" + this.testMode + ", networkCode=" + this.networkCode + ", bannerAdUnitId=" + this.bannerAdUnitId + ", fixedBannerAdUnitId=" + this.fixedBannerAdUnitId + ", interstitialAdUnitId=" + this.interstitialAdUnitId + ", rewardedAdUnitId=" + this.rewardedAdUnitId + ", rewardedInterstitialAdUnitId=" + this.rewardedInterstitialAdUnitId + ", nativeAdUnitId=" + this.nativeAdUnitId + ", appOpenAdUnitId=" + this.appOpenAdUnitId + ", customTargeting=" + this.customTargeting + ", interstitialInterval=" + this.interstitialInterval + ", bannerPosition=" + this.bannerPosition + ", safelinkGamAdsEnabled=" + this.safelinkGamAdsEnabled + ", enableAdInspector=" + this.enableAdInspector + ", updatedAt=" + this.updatedAt + ")";
    }

    public GoogleAdManagerConfig(boolean adsEnabled, boolean testMode, String networkCode, String bannerAdUnitId, String fixedBannerAdUnitId, String interstitialAdUnitId, String rewardedAdUnitId, String rewardedInterstitialAdUnitId, String nativeAdUnitId, String appOpenAdUnitId, Map<String, String> customTargeting, int interstitialInterval, String bannerPosition, boolean safelinkGamAdsEnabled, boolean enableAdInspector, long updatedAt) {
        Intrinsics.checkNotNullParameter(networkCode, "networkCode");
        Intrinsics.checkNotNullParameter(bannerAdUnitId, "bannerAdUnitId");
        Intrinsics.checkNotNullParameter(fixedBannerAdUnitId, "fixedBannerAdUnitId");
        Intrinsics.checkNotNullParameter(interstitialAdUnitId, "interstitialAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedAdUnitId, "rewardedAdUnitId");
        Intrinsics.checkNotNullParameter(rewardedInterstitialAdUnitId, "rewardedInterstitialAdUnitId");
        Intrinsics.checkNotNullParameter(nativeAdUnitId, "nativeAdUnitId");
        Intrinsics.checkNotNullParameter(appOpenAdUnitId, "appOpenAdUnitId");
        Intrinsics.checkNotNullParameter(customTargeting, "customTargeting");
        Intrinsics.checkNotNullParameter(bannerPosition, "bannerPosition");
        this.adsEnabled = adsEnabled;
        this.testMode = testMode;
        this.networkCode = networkCode;
        this.bannerAdUnitId = bannerAdUnitId;
        this.fixedBannerAdUnitId = fixedBannerAdUnitId;
        this.interstitialAdUnitId = interstitialAdUnitId;
        this.rewardedAdUnitId = rewardedAdUnitId;
        this.rewardedInterstitialAdUnitId = rewardedInterstitialAdUnitId;
        this.nativeAdUnitId = nativeAdUnitId;
        this.appOpenAdUnitId = appOpenAdUnitId;
        this.customTargeting = customTargeting;
        this.interstitialInterval = interstitialInterval;
        this.bannerPosition = bannerPosition;
        this.safelinkGamAdsEnabled = safelinkGamAdsEnabled;
        this.enableAdInspector = enableAdInspector;
        this.updatedAt = updatedAt;
    }

    public /* synthetic */ GoogleAdManagerConfig(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, Map map, int i, String str9, boolean z3, boolean z4, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? true : z, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? "21775744923" : str, (i2 & 8) != 0 ? DEMO_ADAPTIVE_BANNER : str2, (i2 & 16) != 0 ? DEMO_FIXED_BANNER : str3, (i2 & 32) != 0 ? DEMO_INTERSTITIAL : str4, (i2 & 64) != 0 ? DEMO_REWARDED : str5, (i2 & 128) != 0 ? DEMO_REWARDED_INTERSTITIAL : str6, (i2 & 256) != 0 ? DEMO_NATIVE : str7, (i2 & 512) != 0 ? DEMO_APP_OPEN : str8, (i2 & 1024) != 0 ? MapsKt.mapOf(TuplesKt.to("category", "browser"), TuplesKt.to("tier", "free")) : map, (i2 & 2048) != 0 ? 3 : i, (i2 & 4096) != 0 ? "BOTTOM" : str9, (i2 & 8192) != 0 ? true : z3, (i2 & 16384) != 0 ? true : z4, (i2 & 32768) != 0 ? System.currentTimeMillis() : j);
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

    public final String getNetworkCode() {
        return this.networkCode;
    }

    public final void setNetworkCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.networkCode = str;
    }

    public final String getBannerAdUnitId() {
        return this.bannerAdUnitId;
    }

    public final void setBannerAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerAdUnitId = str;
    }

    public final String getFixedBannerAdUnitId() {
        return this.fixedBannerAdUnitId;
    }

    public final void setFixedBannerAdUnitId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.fixedBannerAdUnitId = str;
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

    public final Map<String, String> getCustomTargeting() {
        return this.customTargeting;
    }

    public final void setCustomTargeting(Map<String, String> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.customTargeting = map;
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

    public final boolean getSafelinkGamAdsEnabled() {
        return this.safelinkGamAdsEnabled;
    }

    public final void setSafelinkGamAdsEnabled(boolean z) {
        this.safelinkGamAdsEnabled = z;
    }

    public final boolean getEnableAdInspector() {
        return this.enableAdInspector;
    }

    public final void setEnableAdInspector(boolean z) {
        this.enableAdInspector = z;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final void setUpdatedAt(long j) {
        this.updatedAt = j;
    }

    public final Map<String, Object> toMap() {
        return MapsKt.mapOf(TuplesKt.to("adsEnabled", Boolean.valueOf(this.adsEnabled)), TuplesKt.to("testMode", Boolean.valueOf(this.testMode)), TuplesKt.to("networkCode", this.networkCode), TuplesKt.to("bannerAdUnitId", this.bannerAdUnitId), TuplesKt.to("fixedBannerAdUnitId", this.fixedBannerAdUnitId), TuplesKt.to("interstitialAdUnitId", this.interstitialAdUnitId), TuplesKt.to("rewardedAdUnitId", this.rewardedAdUnitId), TuplesKt.to("rewardedInterstitialAdUnitId", this.rewardedInterstitialAdUnitId), TuplesKt.to("nativeAdUnitId", this.nativeAdUnitId), TuplesKt.to("appOpenAdUnitId", this.appOpenAdUnitId), TuplesKt.to("customTargeting", this.customTargeting), TuplesKt.to("interstitialInterval", Integer.valueOf(this.interstitialInterval)), TuplesKt.to("bannerPosition", this.bannerPosition), TuplesKt.to("safelinkGamAdsEnabled", Boolean.valueOf(this.safelinkGamAdsEnabled)), TuplesKt.to("enableAdInspector", Boolean.valueOf(this.enableAdInspector)), TuplesKt.to("updatedAt", Long.valueOf(System.currentTimeMillis())));
    }

    /* compiled from: GoogleAdManagerConfig.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GoogleAdManagerConfig fromMap(Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("customTargeting");
            Map targetingRaw = obj instanceof Map ? (Map) obj : null;
            Map parsedTargeting = new LinkedHashMap();
            if (targetingRaw != null) {
                Map $this$forEach$iv = targetingRaw;
                for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                    String k = (String) element$iv.getKey();
                    Object v = element$iv.getValue();
                    if (v != null) {
                        parsedTargeting.put(k, v.toString());
                    }
                }
            }
            if (parsedTargeting.isEmpty()) {
                parsedTargeting.put("category", "browser");
                parsedTargeting.put("tier", "free");
            }
            Object obj2 = map.get("adsEnabled");
            Boolean bool = obj2 instanceof Boolean ? (Boolean) obj2 : null;
            boolean booleanValue = bool != null ? bool.booleanValue() : true;
            Object obj3 = map.get("testMode");
            Boolean bool2 = obj3 instanceof Boolean ? (Boolean) obj3 : null;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : true;
            Object obj4 = map.get("networkCode");
            String str = obj4 instanceof String ? (String) obj4 : null;
            if (str == null) {
                str = "21775744923";
            }
            String str2 = str;
            Object obj5 = map.get("bannerAdUnitId");
            String str3 = obj5 instanceof String ? (String) obj5 : null;
            if (str3 == null) {
                str3 = GoogleAdManagerConfig.DEMO_ADAPTIVE_BANNER;
            }
            String str4 = str3;
            Object obj6 = map.get("fixedBannerAdUnitId");
            String str5 = obj6 instanceof String ? (String) obj6 : null;
            if (str5 == null) {
                str5 = GoogleAdManagerConfig.DEMO_FIXED_BANNER;
            }
            String str6 = str5;
            Object obj7 = map.get("interstitialAdUnitId");
            String str7 = obj7 instanceof String ? (String) obj7 : null;
            if (str7 == null) {
                str7 = GoogleAdManagerConfig.DEMO_INTERSTITIAL;
            }
            String str8 = str7;
            Object obj8 = map.get("rewardedAdUnitId");
            String str9 = obj8 instanceof String ? (String) obj8 : null;
            if (str9 == null) {
                str9 = GoogleAdManagerConfig.DEMO_REWARDED;
            }
            String str10 = str9;
            Object obj9 = map.get("rewardedInterstitialAdUnitId");
            String str11 = obj9 instanceof String ? (String) obj9 : null;
            if (str11 == null) {
                str11 = GoogleAdManagerConfig.DEMO_REWARDED_INTERSTITIAL;
            }
            String str12 = str11;
            Object obj10 = map.get("nativeAdUnitId");
            String str13 = obj10 instanceof String ? (String) obj10 : null;
            if (str13 == null) {
                str13 = GoogleAdManagerConfig.DEMO_NATIVE;
            }
            String str14 = str13;
            Object obj11 = map.get("appOpenAdUnitId");
            String str15 = obj11 instanceof String ? (String) obj11 : null;
            if (str15 == null) {
                str15 = GoogleAdManagerConfig.DEMO_APP_OPEN;
            }
            String str16 = str15;
            Object obj12 = map.get("interstitialInterval");
            Number number = obj12 instanceof Number ? (Number) obj12 : null;
            int intValue = number != null ? number.intValue() : 3;
            Object obj13 = map.get("bannerPosition");
            String str17 = obj13 instanceof String ? (String) obj13 : null;
            if (str17 == null) {
                str17 = "BOTTOM";
            }
            String str18 = str17;
            Object obj14 = map.get("safelinkGamAdsEnabled");
            Boolean bool3 = obj14 instanceof Boolean ? (Boolean) obj14 : null;
            boolean booleanValue3 = bool3 != null ? bool3.booleanValue() : true;
            Object obj15 = map.get("enableAdInspector");
            Boolean bool4 = obj15 instanceof Boolean ? (Boolean) obj15 : null;
            boolean booleanValue4 = bool4 != null ? bool4.booleanValue() : true;
            Object obj16 = map.get("updatedAt");
            Number number2 = obj16 instanceof Number ? (Number) obj16 : null;
            return new GoogleAdManagerConfig(booleanValue, booleanValue2, str2, str4, str6, str8, str10, str12, str14, str16, parsedTargeting, intValue, str18, booleanValue3, booleanValue4, number2 != null ? number2.longValue() : System.currentTimeMillis());
        }
    }
}
