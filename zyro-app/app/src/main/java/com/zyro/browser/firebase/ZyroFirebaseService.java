package com.zyro.browser.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zyro.browser.MainActivity;
import com.zyro.browser.R;
import com.zyro.browser.model.ZyroNotification;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ZyroFirebaseService.kt */
/* loaded from: classes3.dex */
public final class ZyroFirebaseService extends FirebaseMessagingService {
    public static final String CHANNEL_ID = "zyro_notifications_channel";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ZyroFirebase";

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        super.onNewToken(token);
        Log.d(TAG, "Zyro FCM Token refreshed: " + token);
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str;
        String str2;
        String str3;
        Uri imageUrl;
        Intrinsics.checkNotNullParameter(remoteMessage, "remoteMessage");
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "FCM Message received from: " + remoteMessage.getFrom());
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title = ((notification == null || (str = notification.getTitle()) == null) && (str = remoteMessage.getData().get("title")) == null) ? "Zyro Browser" : str;
        RemoteMessage.Notification notification2 = remoteMessage.getNotification();
        String body = ((notification2 == null || (str2 = notification2.getBody()) == null) && (str2 = remoteMessage.getData().get("body")) == null) ? "You have a new update from Zyro Browser." : str2;
        RemoteMessage.Notification notification3 = remoteMessage.getNotification();
        String imageUrl2 = ((notification3 == null || (imageUrl = notification3.getImageUrl()) == null || (str3 = imageUrl.toString()) == null) && (str3 = remoteMessage.getData().get("imageUrl")) == null) ? "" : str3;
        String str4 = remoteMessage.getData().get("targetUrl");
        String targetUrl = str4 == null ? "" : str4;
        String str5 = remoteMessage.getData().get("priority");
        if (str5 == null) {
            str5 = "HIGH";
        }
        String priority = str5;
        String messageId = remoteMessage.getMessageId();
        if (messageId == null) {
            messageId = "fcm_" + System.currentTimeMillis();
        }
        String str6 = messageId;
        Intrinsics.checkNotNullExpressionValue(str6, "remoteMessage.messageId …tem.currentTimeMillis()}\"");
        ZyroNotification notif = new ZyroNotification(str6, title, body, imageUrl2, targetUrl, null, priority, 0L, null, TypedValues.CycleType.TYPE_PATH_ROTATE, null);
        INSTANCE.displaySystemNotification(this, notif);
    }

    /* compiled from: ZyroFirebaseService.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void displaySystemNotification(final Context context, final ZyroNotification notif) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(notif, "notif");
            ThreadsKt.thread((r12 & 1) != 0, (r12 & 2) != 0 ? false : false, (r12 & 4) != 0 ? null : null, (r12 & 8) != 0 ? null : null, (r12 & 16) != 0 ? -1 : 0, new Function0<Unit>() { // from class: com.zyro.browser.firebase.ZyroFirebaseService$Companion$displaySystemNotification$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    int importance;
                    SharedPreferences prefs = context.getSharedPreferences("zyro_prefs", 0);
                    Set seenIds = prefs.getStringSet("seen_notif_ids", SetsKt.emptySet());
                    if (seenIds == null) {
                        seenIds = SetsKt.emptySet();
                    }
                    if ((!StringsKt.isBlank(notif.getId())) && seenIds.contains(notif.getId())) {
                        Log.d("ZyroFirebase", "Notification " + notif.getId() + " already shown. Skipping duplicate.");
                        return;
                    }
                    if (!StringsKt.isBlank(notif.getId())) {
                        HashSet $this$invoke_u24lambda_u240 = new HashSet(seenIds);
                        $this$invoke_u24lambda_u240.add(notif.getId());
                        prefs.edit().putStringSet("seen_notif_ids", $this$invoke_u24lambda_u240).apply();
                    }
                    Bitmap bitmap = null;
                    if (!StringsKt.isBlank(notif.getImageUrl())) {
                        try {
                            URL url = new URL(notif.getImageUrl());
                            URLConnection openConnection = url.openConnection();
                            Intrinsics.checkNotNull(openConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                            HttpURLConnection conn = (HttpURLConnection) openConnection;
                            conn.setDoInput(true);
                            conn.setConnectTimeout(5000);
                            conn.setReadTimeout(5000);
                            conn.connect();
                            InputStream input = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(input);
                        } catch (Exception e) {
                            Log.w("ZyroFirebase", "Failed to download notification image: " + e.getMessage());
                        }
                    }
                    Object systemService = context.getSystemService("notification");
                    Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
                    NotificationManager notificationManager = (NotificationManager) systemService;
                    if (Build.VERSION.SDK_INT >= 26) {
                        if (StringsKt.equals(notif.getPriority(), "HIGH", true)) {
                            importance = 4;
                        } else {
                            importance = 3;
                        }
                        NotificationChannel channel = new NotificationChannel(ZyroFirebaseService.CHANNEL_ID, "Zyro Browser Announcements", importance);
                        channel.setDescription("Announcements, security updates, and promos from Zyro Browser");
                        channel.enableLights(true);
                        channel.enableVibration(true);
                        notificationManager.createNotificationChannel(channel);
                    }
                    Intent intent = new Intent(context, (Class<?>) MainActivity.class);
                    ZyroNotification zyroNotification = notif;
                    intent.setFlags(603979776);
                    if (!StringsKt.isBlank(zyroNotification.getTargetUrl())) {
                        intent.putExtra("target_url", zyroNotification.getTargetUrl());
                    }
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, notif.getId().hashCode(), intent, 201326592);
                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(2);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ZyroFirebaseService.CHANNEL_ID).setSmallIcon(R.drawable.ic_notification_bell).setContentTitle(notif.getTitle()).setContentText(notif.getBody()).setAutoCancel(true).setSound(defaultSoundUri).setColor(-37632).setContentIntent(pendingIntent).setPriority(StringsKt.equals(notif.getPriority(), "HIGH", true) ? 1 : 0);
                    Intrinsics.checkNotNullExpressionValue(builder, "Builder(context, CHANNEL…  }\n                    )");
                    if (bitmap != null) {
                        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText(notif.getBody()));
                        builder.setLargeIcon(bitmap);
                    } else {
                        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(notif.getBody()));
                    }
                    notificationManager.notify(notif.getId().hashCode(), builder.build());
                }
            });
        }
    }
}
