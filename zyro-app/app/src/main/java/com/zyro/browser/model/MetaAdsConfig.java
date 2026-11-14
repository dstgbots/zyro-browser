package com.zyro.browser.model;

import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MetaAdsConfig.kt */
/* loaded from: classes9.dex */
public final class MetaAdsConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String appId;
    private String bannerMrecPlacementId;
    private String bannerPlacementId;
    private boolean enabled;
    private String interstitialPlacementId;
    private String nativeBannerPlacementId;
    private String nativePlacementId;
    private String playablePlacementId;
    private String rewardedPlacementId;
    private boolean testMode;

    public MetaAdsConfig() {
        this(false, false, null, null, null, null, null, null, null, null, 1023, null);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getEnabled() {
        return this.enabled;
    }

    /* renamed from: component10, reason: from getter */
    public final String getPlayablePlacementId() {
        return this.playablePlacementId;
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
    public final String getBannerPlacementId() {
        return this.bannerPlacementId;
    }

    /* renamed from: component5, reason: from getter */
    public final String getBannerMrecPlacementId() {
        return this.bannerMrecPlacementId;
    }

    /* renamed from: component6, reason: from getter */
    public final String getInterstitialPlacementId() {
        return this.interstitialPlacementId;
    }

    /* renamed from: component7, reason: from getter */
    public final String getRewardedPlacementId() {
        return this.rewardedPlacementId;
    }

    /* renamed from: component8, reason: from getter */
    public final String getNativePlacementId() {
        return this.nativePlacementId;
    }

    /* renamed from: component9, reason: from getter */
    public final String getNativeBannerPlacementId() {
        return this.nativeBannerPlacementId;
    }

    public final MetaAdsConfig copy(boolean enabled, boolean testMode, String appId, String bannerPlacementId, String bannerMrecPlacementId, String interstitialPlacementId, String rewardedPlacementId, String nativePlacementId, String nativeBannerPlacementId, String playablePlacementId) {
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(bannerPlacementId, "bannerPlacementId");
        Intrinsics.checkNotNullParameter(bannerMrecPlacementId, "bannerMrecPlacementId");
        Intrinsics.checkNotNullParameter(interstitialPlacementId, "interstitialPlacementId");
        Intrinsics.checkNotNullParameter(rewardedPlacementId, "rewardedPlacementId");
        Intrinsics.checkNotNullParameter(nativePlacementId, "nativePlacementId");
        Intrinsics.checkNotNullParameter(nativeBannerPlacementId, "nativeBannerPlacementId");
        Intrinsics.checkNotNullParameter(playablePlacementId, "playablePlacementId");
        return new MetaAdsConfig(enabled, testMode, appId, bannerPlacementId, bannerMrecPlacementId, interstitialPlacementId, rewardedPlacementId, nativePlacementId, nativeBannerPlacementId, playablePlacementId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MetaAdsConfig)) {
            return false;
        }
        MetaAdsConfig metaAdsConfig = (MetaAdsConfig) other;
        return this.enabled == metaAdsConfig.enabled && this.testMode == metaAdsConfig.testMode && Intrinsics.areEqual(this.appId, metaAdsConfig.appId) && Intrinsics.areEqual(this.bannerPlacementId, metaAdsConfig.bannerPlacementId) && Intrinsics.areEqual(this.bannerMrecPlacementId, metaAdsConfig.bannerMrecPlacementId) && Intrinsics.areEqual(this.interstitialPlacementId, metaAdsConfig.interstitialPlacementId) && Intrinsics.areEqual(this.rewardedPlacementId, metaAdsConfig.rewardedPlacementId) && Intrinsics.areEqual(this.nativePlacementId, metaAdsConfig.nativePlacementId) && Intrinsics.areEqual(this.nativeBannerPlacementId, metaAdsConfig.nativeBannerPlacementId) && Intrinsics.areEqual(this.playablePlacementId, metaAdsConfig.playablePlacementId);
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
        return ((((((((((((((((i + (z2 ? 1 : z2 ? 1 : 0)) * 31) + this.appId.hashCode()) * 31) + this.bannerPlacementId.hashCode()) * 31) + this.bannerMrecPlacementId.hashCode()) * 31) + this.interstitialPlacementId.hashCode()) * 31) + this.rewardedPlacementId.hashCode()) * 31) + this.nativePlacementId.hashCode()) * 31) + this.nativeBannerPlacementId.hashCode()) * 31) + this.playablePlacementId.hashCode();
    }

    public String toString() {
        return "MetaAdsConfig(enabled=" + this.enabled + ", testMode=" + this.testMode + ", appId=" + this.appId + ", bannerPlacementId=" + this.bannerPlacementId + ", bannerMrecPlacementId=" + this.bannerMrecPlacementId + ", interstitialPlacementId=" + this.interstitialPlacementId + ", rewardedPlacementId=" + this.rewardedPlacementId + ", nativePlacementId=" + this.nativePlacementId + ", nativeBannerPlacementId=" + this.nativeBannerPlacementId + ", playablePlacementId=" + this.playablePlacementId + ")";
    }

    public MetaAdsConfig(boolean enabled, boolean testMode, String appId, String bannerPlacementId, String bannerMrecPlacementId, String interstitialPlacementId, String rewardedPlacementId, String nativePlacementId, String nativeBannerPlacementId, String playablePlacementId) {
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(bannerPlacementId, "bannerPlacementId");
        Intrinsics.checkNotNullParameter(bannerMrecPlacementId, "bannerMrecPlacementId");
        Intrinsics.checkNotNullParameter(interstitialPlacementId, "interstitialPlacementId");
        Intrinsics.checkNotNullParameter(rewardedPlacementId, "rewardedPlacementId");
        Intrinsics.checkNotNullParameter(nativePlacementId, "nativePlacementId");
        Intrinsics.checkNotNullParameter(nativeBannerPlacementId, "nativeBannerPlacementId");
        Intrinsics.checkNotNullParameter(playablePlacementId, "playablePlacementId");
        this.enabled = enabled;
        this.testMode = testMode;
        this.appId = appId;
        this.bannerPlacementId = bannerPlacementId;
        this.bannerMrecPlacementId = bannerMrecPlacementId;
        this.interstitialPlacementId = interstitialPlacementId;
        this.rewardedPlacementId = rewardedPlacementId;
        this.nativePlacementId = nativePlacementId;
        this.nativeBannerPlacementId = nativeBannerPlacementId;
        this.playablePlacementId = playablePlacementId;
    }

    public /* synthetic */ MetaAdsConfig(boolean z, boolean z2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) == 0 ? z2 : true, (i & 4) != 0 ? "123456789012345" : str, (i & 8) != 0 ? "IMG_16_9_LINK#YOUR_PLACEMENT_ID" : str2, (i & 16) != 0 ? "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID" : str3, (i & 32) != 0 ? "VID_HD_16_9_46S_LINK#YOUR_PLACEMENT_ID" : str4, (i & 64) != 0 ? "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID" : str5, (i & 128) == 0 ? str6 : "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", (i & 256) == 0 ? str7 : "IMG_16_9_LINK#YOUR_PLACEMENT_ID", (i & 512) != 0 ? "PLAYABLE#YOUR_PLACEMENT_ID" : str8);
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

    public final String getAppId() {
        return this.appId;
    }

    public final void setAppId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.appId = str;
    }

    public final String getBannerPlacementId() {
        return this.bannerPlacementId;
    }

    public final void setBannerPlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerPlacementId = str;
    }

    public final String getBannerMrecPlacementId() {
        return this.bannerMrecPlacementId;
    }

    public final void setBannerMrecPlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bannerMrecPlacementId = str;
    }

    public final String getInterstitialPlacementId() {
        return this.interstitialPlacementId;
    }

    public final void setInterstitialPlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.interstitialPlacementId = str;
    }

    public final String getRewardedPlacementId() {
        return this.rewardedPlacementId;
    }

    public final void setRewardedPlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.rewardedPlacementId = str;
    }

    public final String getNativePlacementId() {
        return this.nativePlacementId;
    }

    public final void setNativePlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nativePlacementId = str;
    }

    public final String getNativeBannerPlacementId() {
        return this.nativeBannerPlacementId;
    }

    public final void setNativeBannerPlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.nativeBannerPlacementId = str;
    }

    public final String getPlayablePlacementId() {
        return this.playablePlacementId;
    }

    public final void setPlayablePlacementId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.playablePlacementId = str;
    }

    /* compiled from: MetaAdsConfig.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MetaAdsConfig fromMap(Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("enabled");
            Boolean bool = obj instanceof Boolean ? (Boolean) obj : null;
            boolean booleanValue = bool != null ? bool.booleanValue() : true;
            Object obj2 = map.get("testMode");
            Boolean bool2 = obj2 instanceof Boolean ? (Boolean) obj2 : null;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : true;
            Object obj3 = map.get("appId");
            String str = obj3 instanceof String ? (String) obj3 : null;
            if (str == null) {
                str = "123456789012345";
            }
            String str2 = str;
            Object obj4 = map.get("bannerPlacementId");
            String str3 = obj4 instanceof String ? (String) obj4 : null;
            String str4 = str3 == null ? "IMG_16_9_LINK#YOUR_PLACEMENT_ID" : str3;
            Object obj5 = map.get("bannerMrecPlacementId");
            String str5 = obj5 instanceof String ? (String) obj5 : null;
            String str6 = str5 == null ? "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID" : str5;
            Object obj6 = map.get("interstitialPlacementId");
            String str7 = obj6 instanceof String ? (String) obj6 : null;
            if (str7 == null) {
                str7 = "VID_HD_16_9_46S_LINK#YOUR_PLACEMENT_ID";
            }
            String str8 = str7;
            Object obj7 = map.get("rewardedPlacementId");
            String str9 = obj7 instanceof String ? (String) obj7 : null;
            if (str9 == null) {
                str9 = "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID";
            }
            String str10 = str9;
            Object obj8 = map.get("nativePlacementId");
            String str11 = obj8 instanceof String ? (String) obj8 : null;
            String str12 = str11 == null ? "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID" : str11;
            Object obj9 = map.get("nativeBannerPlacementId");
            String str13 = obj9 instanceof String ? (String) obj9 : null;
            String str14 = str13 == null ? "IMG_16_9_LINK#YOUR_PLACEMENT_ID" : str13;
            Object obj10 = map.get("playablePlacementId");
            String str15 = obj10 instanceof String ? (String) obj10 : null;
            return new MetaAdsConfig(booleanValue, booleanValue2, str2, str4, str6, str8, str10, str12, str14, str15 == null ? "PLAYABLE#YOUR_PLACEMENT_ID" : str15);
        }
    }
}
