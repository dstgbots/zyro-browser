package com.zyro.browser.model;

import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: SafeLinkAd.kt */
/* loaded from: classes9.dex */
public final class SafeLinkAd {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String adType;
    private String badge;
    private String ctaText;
    private String ctaUrl;
    private String description;
    private int durationSeconds;
    private int order;
    private String stepId;
    private String stepName;
    private String stepSubtitle;
    private String title;

    public SafeLinkAd() {
        this(null, null, null, 0, null, null, null, null, null, null, 0, 2047, null);
    }

    /* renamed from: component1, reason: from getter */
    public final String getStepId() {
        return this.stepId;
    }

    /* renamed from: component10, reason: from getter */
    public final String getCtaUrl() {
        return this.ctaUrl;
    }

    /* renamed from: component11, reason: from getter */
    public final int getDurationSeconds() {
        return this.durationSeconds;
    }

    /* renamed from: component2, reason: from getter */
    public final String getStepName() {
        return this.stepName;
    }

    /* renamed from: component3, reason: from getter */
    public final String getStepSubtitle() {
        return this.stepSubtitle;
    }

    /* renamed from: component4, reason: from getter */
    public final int getOrder() {
        return this.order;
    }

    /* renamed from: component5, reason: from getter */
    public final String getAdType() {
        return this.adType;
    }

    /* renamed from: component6, reason: from getter */
    public final String getBadge() {
        return this.badge;
    }

    /* renamed from: component7, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component8, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component9, reason: from getter */
    public final String getCtaText() {
        return this.ctaText;
    }

    public final SafeLinkAd copy(String stepId, String stepName, String stepSubtitle, int order, String adType, String badge, String title, String description, String ctaText, String ctaUrl, int durationSeconds) {
        Intrinsics.checkNotNullParameter(stepId, "stepId");
        Intrinsics.checkNotNullParameter(stepName, "stepName");
        Intrinsics.checkNotNullParameter(stepSubtitle, "stepSubtitle");
        Intrinsics.checkNotNullParameter(adType, "adType");
        Intrinsics.checkNotNullParameter(badge, "badge");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(ctaText, "ctaText");
        Intrinsics.checkNotNullParameter(ctaUrl, "ctaUrl");
        return new SafeLinkAd(stepId, stepName, stepSubtitle, order, adType, badge, title, description, ctaText, ctaUrl, durationSeconds);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SafeLinkAd)) {
            return false;
        }
        SafeLinkAd safeLinkAd = (SafeLinkAd) other;
        return Intrinsics.areEqual(this.stepId, safeLinkAd.stepId) && Intrinsics.areEqual(this.stepName, safeLinkAd.stepName) && Intrinsics.areEqual(this.stepSubtitle, safeLinkAd.stepSubtitle) && this.order == safeLinkAd.order && Intrinsics.areEqual(this.adType, safeLinkAd.adType) && Intrinsics.areEqual(this.badge, safeLinkAd.badge) && Intrinsics.areEqual(this.title, safeLinkAd.title) && Intrinsics.areEqual(this.description, safeLinkAd.description) && Intrinsics.areEqual(this.ctaText, safeLinkAd.ctaText) && Intrinsics.areEqual(this.ctaUrl, safeLinkAd.ctaUrl) && this.durationSeconds == safeLinkAd.durationSeconds;
    }

    public int hashCode() {
        return (((((((((((((((((((this.stepId.hashCode() * 31) + this.stepName.hashCode()) * 31) + this.stepSubtitle.hashCode()) * 31) + Integer.hashCode(this.order)) * 31) + this.adType.hashCode()) * 31) + this.badge.hashCode()) * 31) + this.title.hashCode()) * 31) + this.description.hashCode()) * 31) + this.ctaText.hashCode()) * 31) + this.ctaUrl.hashCode()) * 31) + Integer.hashCode(this.durationSeconds);
    }

    public String toString() {
        return "SafeLinkAd(stepId=" + this.stepId + ", stepName=" + this.stepName + ", stepSubtitle=" + this.stepSubtitle + ", order=" + this.order + ", adType=" + this.adType + ", badge=" + this.badge + ", title=" + this.title + ", description=" + this.description + ", ctaText=" + this.ctaText + ", ctaUrl=" + this.ctaUrl + ", durationSeconds=" + this.durationSeconds + ")";
    }

    public SafeLinkAd(String stepId, String stepName, String stepSubtitle, int order, String adType, String badge, String title, String description, String ctaText, String ctaUrl, int durationSeconds) {
        Intrinsics.checkNotNullParameter(stepId, "stepId");
        Intrinsics.checkNotNullParameter(stepName, "stepName");
        Intrinsics.checkNotNullParameter(stepSubtitle, "stepSubtitle");
        Intrinsics.checkNotNullParameter(adType, "adType");
        Intrinsics.checkNotNullParameter(badge, "badge");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(ctaText, "ctaText");
        Intrinsics.checkNotNullParameter(ctaUrl, "ctaUrl");
        this.stepId = stepId;
        this.stepName = stepName;
        this.stepSubtitle = stepSubtitle;
        this.order = order;
        this.adType = adType;
        this.badge = badge;
        this.title = title;
        this.description = description;
        this.ctaText = ctaText;
        this.ctaUrl = ctaUrl;
        this.durationSeconds = durationSeconds;
    }

    public /* synthetic */ SafeLinkAd(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, String str8, String str9, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? "step_1" : str, (i3 & 2) != 0 ? "Security Verification" : str2, (i3 & 4) != 0 ? "Analyzing threat vectors & integrity" : str3, (i3 & 8) != 0 ? 1 : i, (i3 & 16) != 0 ? "BANNER" : str4, (i3 & 32) != 0 ? "SPONSORED AD" : str5, (i3 & 64) != 0 ? "" : str6, (i3 & 128) != 0 ? "" : str7, (i3 & 256) != 0 ? "" : str8, (i3 & 512) == 0 ? str9 : "", (i3 & 1024) != 0 ? 3 : i2);
    }

    public final String getStepId() {
        return this.stepId;
    }

    public final void setStepId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.stepId = str;
    }

    public final String getStepName() {
        return this.stepName;
    }

    public final void setStepName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.stepName = str;
    }

    public final String getStepSubtitle() {
        return this.stepSubtitle;
    }

    public final void setStepSubtitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.stepSubtitle = str;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int i) {
        this.order = i;
    }

    public final String getAdType() {
        return this.adType;
    }

    public final void setAdType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.adType = str;
    }

    public final String getBadge() {
        return this.badge;
    }

    public final void setBadge(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.badge = str;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    public final String getCtaText() {
        return this.ctaText;
    }

    public final void setCtaText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctaText = str;
    }

    public final String getCtaUrl() {
        return this.ctaUrl;
    }

    public final void setCtaUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctaUrl = str;
    }

    public final int getDurationSeconds() {
        return this.durationSeconds;
    }

    public final void setDurationSeconds(int i) {
        this.durationSeconds = i;
    }

    /* compiled from: SafeLinkAd.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SafeLinkAd fromMap(String id, Map<String, ? extends Object> map) {
            String str;
            String str2;
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(map, "map");
            Integer intOrNull = StringsKt.toIntOrNull(StringsKt.replace$default(id, "step_", "", false, 4, (Object) null));
            int num = intOrNull != null ? intOrNull.intValue() : 1;
            switch (num) {
                case 1:
                    str = "Domain Verification";
                    break;
                case 2:
                    str = "SSL Certificate Check";
                    break;
                case 3:
                    str = "Safe Browsing Check";
                    break;
                case 4:
                    str = "Redirection Check";
                    break;
                default:
                    str = "Security Verification " + num;
                    break;
            }
            String defaultName = str;
            switch (num) {
                case 1:
                    str2 = "Checking WHOIS, DNS records & reputation authority";
                    break;
                case 2:
                    str2 = "Validating TLS 1.3 cipher suite & integrity";
                    break;
                case 3:
                    str2 = "Scanning phishing, malware & zero-day databases";
                    break;
                case 4:
                    str2 = "Detecting tracking bounces & URL hijacks";
                    break;
                default:
                    str2 = "Deep protocol & threat vector analysis";
                    break;
            }
            String defaultSubtitle = str2;
            String str3 = "BANNER";
            switch (num) {
                case 2:
                    str3 = "NATIVE";
                    break;
                case 3:
                    str3 = "INTERSTITIAL";
                    break;
                case 4:
                    str3 = "REWARDED";
                    break;
            }
            String defaultAdType = str3;
            Object obj = map.get("stepName");
            String str4 = obj instanceof String ? (String) obj : null;
            String str5 = str4 == null ? defaultName : str4;
            Object obj2 = map.get("stepSubtitle");
            String str6 = obj2 instanceof String ? (String) obj2 : null;
            String str7 = str6 == null ? defaultSubtitle : str6;
            Object obj3 = map.get("order");
            Long l = obj3 instanceof Long ? (Long) obj3 : null;
            int longValue = l != null ? (int) l.longValue() : num;
            Object obj4 = map.get("adType");
            String str8 = obj4 instanceof String ? (String) obj4 : null;
            String str9 = str8 == null ? defaultAdType : str8;
            Object obj5 = map.get("badge");
            String str10 = obj5 instanceof String ? (String) obj5 : null;
            if (str10 == null) {
                str10 = "SPONSORED AD";
            }
            String str11 = str10;
            Object obj6 = map.get("title");
            String str12 = obj6 instanceof String ? (String) obj6 : null;
            String str13 = str12 == null ? "" : str12;
            Object obj7 = map.get("description");
            String str14 = obj7 instanceof String ? (String) obj7 : null;
            String str15 = str14 == null ? "" : str14;
            Object obj8 = map.get("ctaText");
            String str16 = obj8 instanceof String ? (String) obj8 : null;
            String str17 = str16 == null ? "" : str16;
            Object obj9 = map.get("ctaUrl");
            String str18 = obj9 instanceof String ? (String) obj9 : null;
            String str19 = str18 == null ? "" : str18;
            Object obj10 = map.get("durationSeconds");
            Long l2 = obj10 instanceof Long ? (Long) obj10 : null;
            return new SafeLinkAd(id, str5, str7, longValue, str9, str11, str13, str15, str17, str19, l2 != null ? (int) l2.longValue() : 3);
        }
    }
}
