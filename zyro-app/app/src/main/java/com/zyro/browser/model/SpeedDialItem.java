package com.zyro.browser.model;

import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpeedDialItem.kt */
/* loaded from: classes9.dex */
public final class SpeedDialItem {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String badgeText;
    private String bgColor;
    private boolean enabled;
    private String iconUrl;
    private String id;
    private int order;
    private String title;
    private String url;

    public SpeedDialItem() {
        this(null, null, null, null, null, null, 0, false, 255, null);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: component4, reason: from getter */
    public final String getIconUrl() {
        return this.iconUrl;
    }

    /* renamed from: component5, reason: from getter */
    public final String getBadgeText() {
        return this.badgeText;
    }

    /* renamed from: component6, reason: from getter */
    public final String getBgColor() {
        return this.bgColor;
    }

    /* renamed from: component7, reason: from getter */
    public final int getOrder() {
        return this.order;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getEnabled() {
        return this.enabled;
    }

    public final SpeedDialItem copy(String id, String title, String url, String iconUrl, String badgeText, String bgColor, int order, boolean enabled) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(iconUrl, "iconUrl");
        Intrinsics.checkNotNullParameter(badgeText, "badgeText");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        return new SpeedDialItem(id, title, url, iconUrl, badgeText, bgColor, order, enabled);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpeedDialItem)) {
            return false;
        }
        SpeedDialItem speedDialItem = (SpeedDialItem) other;
        return Intrinsics.areEqual(this.id, speedDialItem.id) && Intrinsics.areEqual(this.title, speedDialItem.title) && Intrinsics.areEqual(this.url, speedDialItem.url) && Intrinsics.areEqual(this.iconUrl, speedDialItem.iconUrl) && Intrinsics.areEqual(this.badgeText, speedDialItem.badgeText) && Intrinsics.areEqual(this.bgColor, speedDialItem.bgColor) && this.order == speedDialItem.order && this.enabled == speedDialItem.enabled;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int hashCode = ((((((((((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.url.hashCode()) * 31) + this.iconUrl.hashCode()) * 31) + this.badgeText.hashCode()) * 31) + this.bgColor.hashCode()) * 31) + Integer.hashCode(this.order)) * 31;
        boolean z = this.enabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public String toString() {
        return "SpeedDialItem(id=" + this.id + ", title=" + this.title + ", url=" + this.url + ", iconUrl=" + this.iconUrl + ", badgeText=" + this.badgeText + ", bgColor=" + this.bgColor + ", order=" + this.order + ", enabled=" + this.enabled + ")";
    }

    public SpeedDialItem(String id, String title, String url, String iconUrl, String badgeText, String bgColor, int order, boolean enabled) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(iconUrl, "iconUrl");
        Intrinsics.checkNotNullParameter(badgeText, "badgeText");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        this.id = id;
        this.title = title;
        this.url = url;
        this.iconUrl = iconUrl;
        this.badgeText = badgeText;
        this.bgColor = bgColor;
        this.order = order;
        this.enabled = enabled;
    }

    public /* synthetic */ SpeedDialItem(String str, String str2, String str3, String str4, String str5, String str6, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3, (i2 & 8) != 0 ? "" : str4, (i2 & 16) == 0 ? str5 : "", (i2 & 32) != 0 ? "#FF6D00" : str6, (i2 & 64) != 0 ? 1 : i, (i2 & 128) == 0 ? z : true);
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.url = str;
    }

    public final String getIconUrl() {
        return this.iconUrl;
    }

    public final void setIconUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.iconUrl = str;
    }

    public final String getBadgeText() {
        return this.badgeText;
    }

    public final void setBadgeText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.badgeText = str;
    }

    public final String getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bgColor = str;
    }

    public final int getOrder() {
        return this.order;
    }

    public final void setOrder(int i) {
        this.order = i;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean z) {
        this.enabled = z;
    }

    public final Map<String, Object> toMap() {
        return MapsKt.mapOf(TuplesKt.to("id", this.id), TuplesKt.to("title", this.title), TuplesKt.to(ImagesContract.URL, this.url), TuplesKt.to("iconUrl", this.iconUrl), TuplesKt.to("badgeText", this.badgeText), TuplesKt.to("bgColor", this.bgColor), TuplesKt.to("order", Integer.valueOf(this.order)), TuplesKt.to("enabled", Boolean.valueOf(this.enabled)));
    }

    /* compiled from: SpeedDialItem.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SpeedDialItem fromMap(String id, Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("title");
            String str = obj instanceof String ? (String) obj : null;
            String str2 = str == null ? "" : str;
            Object obj2 = map.get(ImagesContract.URL);
            String str3 = obj2 instanceof String ? (String) obj2 : null;
            String str4 = str3 == null ? "" : str3;
            Object obj3 = map.get("iconUrl");
            String str5 = obj3 instanceof String ? (String) obj3 : null;
            String str6 = str5 == null ? "" : str5;
            Object obj4 = map.get("badgeText");
            String str7 = obj4 instanceof String ? (String) obj4 : null;
            String str8 = str7 == null ? "" : str7;
            Object obj5 = map.get("bgColor");
            String str9 = obj5 instanceof String ? (String) obj5 : null;
            if (str9 == null) {
                str9 = "#FF6D00";
            }
            String str10 = str9;
            Object obj6 = map.get("order");
            Number number = obj6 instanceof Number ? (Number) obj6 : null;
            int intValue = number != null ? number.intValue() : 1;
            Object obj7 = map.get("enabled");
            Boolean bool = obj7 instanceof Boolean ? (Boolean) obj7 : null;
            return new SpeedDialItem(id, str2, str4, str6, str8, str10, intValue, bool != null ? bool.booleanValue() : true);
        }

        public final List<SpeedDialItem> getDefaultItems() {
            String str = null;
            boolean z = true;
            int i = 8;
            DefaultConstructorMarker defaultConstructorMarker = null;
            return CollectionsKt.listOf((Object[]) new SpeedDialItem[]{new SpeedDialItem("google", "Google", "https://www.google.com", null, RequestConfiguration.MAX_AD_CONTENT_RATING_G, "#4285F4", 1, true, 8, null), new SpeedDialItem("youtube", "YouTube", "https://www.youtube.com", null, "YT", "#FF0000", 2, true, 8, null), new SpeedDialItem("reddit", "Reddit", "https://www.reddit.com", str, "RD", "#FF4500", 3, z, i, defaultConstructorMarker), new SpeedDialItem("wikipedia", "Wikipedia", "https://www.wikipedia.org", str, "W", "#4F46E5", 4, z, i, defaultConstructorMarker), new SpeedDialItem("github", "GitHub", "https://www.github.com", str, "GH", "#181717", 5, z, i, defaultConstructorMarker), new SpeedDialItem("x_twitter", "X", "https://twitter.com", str, "𝕏", "#000000", 6, z, i, defaultConstructorMarker), new SpeedDialItem("amazon", "Amazon", "https://www.amazon.com", str, "AZ", "#FF9900", 7, z, i, defaultConstructorMarker), new SpeedDialItem("chatgpt", "ChatGPT", "https://chatgpt.com", str, "AI", "#10A37F", 8, z, i, defaultConstructorMarker)});
        }
    }
}
