package com.zyro.browser.model;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdNetworkConfig.kt */
/* loaded from: classes9.dex */
public final class AdNetworkConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String PLATFORM_APPLOVIN = "APPLOVIN_MAX";
    public static final String PLATFORM_GAM = "GOOGLE_AD_MANAGER";
    public static final String PLATFORM_GOOGLE = "GOOGLE_ADMOB";
    public static final String PLATFORM_META = "META_AUDIENCE_NETWORK";
    private String activePlatform;
    private boolean globalAdsEnabled;
    private long updatedAt;

    public AdNetworkConfig() {
        this(null, false, 0L, 7, null);
    }

    public static /* synthetic */ AdNetworkConfig copy$default(AdNetworkConfig adNetworkConfig, String str, boolean z, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = adNetworkConfig.activePlatform;
        }
        if ((i & 2) != 0) {
            z = adNetworkConfig.globalAdsEnabled;
        }
        if ((i & 4) != 0) {
            j = adNetworkConfig.updatedAt;
        }
        return adNetworkConfig.copy(str, z, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String getActivePlatform() {
        return this.activePlatform;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getGlobalAdsEnabled() {
        return this.globalAdsEnabled;
    }

    /* renamed from: component3, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final AdNetworkConfig copy(String activePlatform, boolean globalAdsEnabled, long updatedAt) {
        Intrinsics.checkNotNullParameter(activePlatform, "activePlatform");
        return new AdNetworkConfig(activePlatform, globalAdsEnabled, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AdNetworkConfig)) {
            return false;
        }
        AdNetworkConfig adNetworkConfig = (AdNetworkConfig) other;
        return Intrinsics.areEqual(this.activePlatform, adNetworkConfig.activePlatform) && this.globalAdsEnabled == adNetworkConfig.globalAdsEnabled && this.updatedAt == adNetworkConfig.updatedAt;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int hashCode = this.activePlatform.hashCode() * 31;
        boolean z = this.globalAdsEnabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return ((hashCode + i) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "AdNetworkConfig(activePlatform=" + this.activePlatform + ", globalAdsEnabled=" + this.globalAdsEnabled + ", updatedAt=" + this.updatedAt + ")";
    }

    public AdNetworkConfig(String activePlatform, boolean globalAdsEnabled, long updatedAt) {
        Intrinsics.checkNotNullParameter(activePlatform, "activePlatform");
        this.activePlatform = activePlatform;
        this.globalAdsEnabled = globalAdsEnabled;
        this.updatedAt = updatedAt;
    }

    public /* synthetic */ AdNetworkConfig(String str, boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? PLATFORM_GOOGLE : str, (i & 2) != 0 ? true : z, (i & 4) != 0 ? System.currentTimeMillis() : j);
    }

    public final String getActivePlatform() {
        return this.activePlatform;
    }

    public final void setActivePlatform(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.activePlatform = str;
    }

    public final boolean getGlobalAdsEnabled() {
        return this.globalAdsEnabled;
    }

    public final void setGlobalAdsEnabled(boolean z) {
        this.globalAdsEnabled = z;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final void setUpdatedAt(long j) {
        this.updatedAt = j;
    }

    public final Map<String, Object> toMap() {
        return MapsKt.mapOf(TuplesKt.to("activePlatform", this.activePlatform), TuplesKt.to("globalAdsEnabled", Boolean.valueOf(this.globalAdsEnabled)), TuplesKt.to("updatedAt", Long.valueOf(System.currentTimeMillis())));
    }

    /* compiled from: AdNetworkConfig.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AdNetworkConfig fromMap(Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("activePlatform");
            String str = obj instanceof String ? (String) obj : null;
            if (str == null) {
                str = AdNetworkConfig.PLATFORM_GOOGLE;
            }
            Object obj2 = map.get("globalAdsEnabled");
            Boolean bool = obj2 instanceof Boolean ? (Boolean) obj2 : null;
            boolean booleanValue = bool != null ? bool.booleanValue() : true;
            Object obj3 = map.get("updatedAt");
            Long l = obj3 instanceof Long ? (Long) obj3 : null;
            return new AdNetworkConfig(str, booleanValue, l != null ? l.longValue() : System.currentTimeMillis());
        }
    }
}
