package com.zyro.browser.model;

import androidx.core.app.FrameMetricsAggregator;
import androidx.core.app.NotificationCompat;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ZyroNotification.kt */
/* loaded from: classes9.dex */
public final class ZyroNotification {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private String body;
    private String id;
    private String imageUrl;
    private String priority;
    private String status;
    private String targetUrl;
    private long timestamp;
    private String title;
    private String topic;

    public ZyroNotification() {
        this(null, null, null, null, null, null, null, 0L, null, FrameMetricsAggregator.EVERY_DURATION, null);
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
    public final String getBody() {
        return this.body;
    }

    /* renamed from: component4, reason: from getter */
    public final String getImageUrl() {
        return this.imageUrl;
    }

    /* renamed from: component5, reason: from getter */
    public final String getTargetUrl() {
        return this.targetUrl;
    }

    /* renamed from: component6, reason: from getter */
    public final String getTopic() {
        return this.topic;
    }

    /* renamed from: component7, reason: from getter */
    public final String getPriority() {
        return this.priority;
    }

    /* renamed from: component8, reason: from getter */
    public final long getTimestamp() {
        return this.timestamp;
    }

    /* renamed from: component9, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    public final ZyroNotification copy(String id, String title, String body, String imageUrl, String targetUrl, String topic, String priority, long timestamp, String status) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
        Intrinsics.checkNotNullParameter(targetUrl, "targetUrl");
        Intrinsics.checkNotNullParameter(topic, "topic");
        Intrinsics.checkNotNullParameter(priority, "priority");
        Intrinsics.checkNotNullParameter(status, "status");
        return new ZyroNotification(id, title, body, imageUrl, targetUrl, topic, priority, timestamp, status);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ZyroNotification)) {
            return false;
        }
        ZyroNotification zyroNotification = (ZyroNotification) other;
        return Intrinsics.areEqual(this.id, zyroNotification.id) && Intrinsics.areEqual(this.title, zyroNotification.title) && Intrinsics.areEqual(this.body, zyroNotification.body) && Intrinsics.areEqual(this.imageUrl, zyroNotification.imageUrl) && Intrinsics.areEqual(this.targetUrl, zyroNotification.targetUrl) && Intrinsics.areEqual(this.topic, zyroNotification.topic) && Intrinsics.areEqual(this.priority, zyroNotification.priority) && this.timestamp == zyroNotification.timestamp && Intrinsics.areEqual(this.status, zyroNotification.status);
    }

    public int hashCode() {
        return (((((((((((((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.body.hashCode()) * 31) + this.imageUrl.hashCode()) * 31) + this.targetUrl.hashCode()) * 31) + this.topic.hashCode()) * 31) + this.priority.hashCode()) * 31) + Long.hashCode(this.timestamp)) * 31) + this.status.hashCode();
    }

    public String toString() {
        return "ZyroNotification(id=" + this.id + ", title=" + this.title + ", body=" + this.body + ", imageUrl=" + this.imageUrl + ", targetUrl=" + this.targetUrl + ", topic=" + this.topic + ", priority=" + this.priority + ", timestamp=" + this.timestamp + ", status=" + this.status + ")";
    }

    public ZyroNotification(String id, String title, String body, String imageUrl, String targetUrl, String topic, String priority, long timestamp, String status) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(imageUrl, "imageUrl");
        Intrinsics.checkNotNullParameter(targetUrl, "targetUrl");
        Intrinsics.checkNotNullParameter(topic, "topic");
        Intrinsics.checkNotNullParameter(priority, "priority");
        Intrinsics.checkNotNullParameter(status, "status");
        this.id = id;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.targetUrl = targetUrl;
        this.topic = topic;
        this.priority = priority;
        this.timestamp = timestamp;
        this.status = status;
    }

    public /* synthetic */ ZyroNotification(String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, String str8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) == 0 ? str5 : "", (i & 32) != 0 ? "all_users" : str6, (i & 64) != 0 ? "HIGH" : str7, (i & 128) != 0 ? System.currentTimeMillis() : j, (i & 256) != 0 ? "SENT" : str8);
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

    public final String getBody() {
        return this.body;
    }

    public final void setBody(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.body = str;
    }

    public final String getImageUrl() {
        return this.imageUrl;
    }

    public final void setImageUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.imageUrl = str;
    }

    public final String getTargetUrl() {
        return this.targetUrl;
    }

    public final void setTargetUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.targetUrl = str;
    }

    public final String getTopic() {
        return this.topic;
    }

    public final void setTopic(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.topic = str;
    }

    public final String getPriority() {
        return this.priority;
    }

    public final void setPriority(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.priority = str;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final void setTimestamp(long j) {
        this.timestamp = j;
    }

    public final String getStatus() {
        return this.status;
    }

    public final void setStatus(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.status = str;
    }

    /* compiled from: ZyroNotification.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ZyroNotification fromMap(String id, Map<String, ? extends Object> map) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("title");
            String str = obj instanceof String ? (String) obj : null;
            String str2 = str == null ? "" : str;
            Object obj2 = map.get("body");
            String str3 = obj2 instanceof String ? (String) obj2 : null;
            String str4 = str3 == null ? "" : str3;
            Object obj3 = map.get("imageUrl");
            String str5 = obj3 instanceof String ? (String) obj3 : null;
            String str6 = str5 == null ? "" : str5;
            Object obj4 = map.get("targetUrl");
            String str7 = obj4 instanceof String ? (String) obj4 : null;
            String str8 = str7 == null ? "" : str7;
            Object obj5 = map.get("topic");
            String str9 = obj5 instanceof String ? (String) obj5 : null;
            if (str9 == null) {
                str9 = "all_users";
            }
            String str10 = str9;
            Object obj6 = map.get("priority");
            String str11 = obj6 instanceof String ? (String) obj6 : null;
            if (str11 == null) {
                str11 = "HIGH";
            }
            String str12 = str11;
            Object obj7 = map.get("timestamp");
            Long l = obj7 instanceof Long ? (Long) obj7 : null;
            long longValue = l != null ? l.longValue() : System.currentTimeMillis();
            Object obj8 = map.get(NotificationCompat.CATEGORY_STATUS);
            String str13 = obj8 instanceof String ? (String) obj8 : null;
            return new ZyroNotification(id, str2, str4, str6, str8, str10, str12, longValue, str13 == null ? "SENT" : str13);
        }
    }
}
