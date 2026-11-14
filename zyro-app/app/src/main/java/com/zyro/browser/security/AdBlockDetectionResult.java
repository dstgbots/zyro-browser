package com.zyro.browser.security;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdBlockDetector.kt */
/* loaded from: classes9.dex */
public final class AdBlockDetectionResult {
    private final boolean isBlocked;
    private final String provider;
    private final String reason;

    public static /* synthetic */ AdBlockDetectionResult copy$default(AdBlockDetectionResult adBlockDetectionResult, boolean z, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = adBlockDetectionResult.isBlocked;
        }
        if ((i & 2) != 0) {
            str = adBlockDetectionResult.provider;
        }
        if ((i & 4) != 0) {
            str2 = adBlockDetectionResult.reason;
        }
        return adBlockDetectionResult.copy(z, str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsBlocked() {
        return this.isBlocked;
    }

    /* renamed from: component2, reason: from getter */
    public final String getProvider() {
        return this.provider;
    }

    /* renamed from: component3, reason: from getter */
    public final String getReason() {
        return this.reason;
    }

    public final AdBlockDetectionResult copy(boolean isBlocked, String provider, String reason) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(reason, "reason");
        return new AdBlockDetectionResult(isBlocked, provider, reason);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AdBlockDetectionResult)) {
            return false;
        }
        AdBlockDetectionResult adBlockDetectionResult = (AdBlockDetectionResult) other;
        return this.isBlocked == adBlockDetectionResult.isBlocked && Intrinsics.areEqual(this.provider, adBlockDetectionResult.provider) && Intrinsics.areEqual(this.reason, adBlockDetectionResult.reason);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isBlocked;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return (((r0 * 31) + this.provider.hashCode()) * 31) + this.reason.hashCode();
    }

    public String toString() {
        return "AdBlockDetectionResult(isBlocked=" + this.isBlocked + ", provider=" + this.provider + ", reason=" + this.reason + ")";
    }

    public AdBlockDetectionResult(boolean isBlocked, String provider, String reason) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.isBlocked = isBlocked;
        this.provider = provider;
        this.reason = reason;
    }

    public /* synthetic */ AdBlockDetectionResult(boolean z, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? "" : str, (i & 4) != 0 ? "" : str2);
    }

    public final boolean isBlocked() {
        return this.isBlocked;
    }

    public final String getProvider() {
        return this.provider;
    }

    public final String getReason() {
        return this.reason;
    }
}
