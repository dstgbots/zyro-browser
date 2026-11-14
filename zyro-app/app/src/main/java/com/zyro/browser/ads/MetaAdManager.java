package com.zyro.browser.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.zyro.browser.model.MetaAdsConfig;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* compiled from: MetaAdManager.kt */
/* loaded from: classes10.dex */
public final class MetaAdManager {
    private static final String TAG = "MetaAdManager";
    private boolean isInitialized;
    private MetaAdsConfig metaConfig;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<MetaAdManager> instance$delegate = LazyKt.lazy(new Function0<MetaAdManager>() { // from class: com.zyro.browser.ads.MetaAdManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final MetaAdManager invoke() {
            return new MetaAdManager(null);
        }
    });

    public /* synthetic */ MetaAdManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private MetaAdManager() {
        this.metaConfig = new MetaAdsConfig(false, false, null, null, null, null, null, null, null, null, 1023, null);
    }

    /* compiled from: MetaAdManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MetaAdManager getInstance() {
            return (MetaAdManager) MetaAdManager.instance$delegate.getValue();
        }
    }

    public final void updateConfig(MetaAdsConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.metaConfig = config;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.isInitialized) {
            return;
        }
        if (this.metaConfig.getTestMode()) {
            AdSettings.setTestMode(true);
            try {
                AdSettings.turnOnSDKDebugger(context);
            } catch (Exception e) {
            }
        }
        AudienceNetworkAds.buildInitSettings(context).withInitListener(new AudienceNetworkAds.InitListener() { // from class: com.zyro.browser.ads.MetaAdManager$$ExternalSyntheticLambda0
            @Override // com.facebook.ads.AudienceNetworkAds.InitListener
            public final void onInitialized(AudienceNetworkAds.InitResult initResult) {
                MetaAdManager.init$lambda$0(MetaAdManager.this, initResult);
            }
        }).initialize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(MetaAdManager this$0, AudienceNetworkAds.InitResult result) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isInitialized = result.isSuccess();
        Log.d(TAG, "Meta Audience Network initialized: " + result.isSuccess() + ", message: " + result.getMessage());
    }

    private final String getEffectivePlacementId(String rawId, String testPrefix) {
        String trimmed = StringsKt.trim((CharSequence) rawId).toString();
        if (trimmed.length() == 0) {
            return testPrefix + "#YOUR_PLACEMENT_ID";
        }
        if (this.metaConfig.getTestMode() && !StringsKt.contains$default((CharSequence) trimmed, (CharSequence) "#", false, 2, (Object) null)) {
            return testPrefix + "#" + trimmed;
        }
        return trimmed;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showInterstitial$default(MetaAdManager metaAdManager, Activity activity, Function0 function0, Function0 function02, Function0 function03, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function03 = null;
        }
        metaAdManager.showInterstitial(activity, function0, function02, function03);
    }

    public final void showInterstitial(Activity activity, final Function0<Unit> onAdLoaded, final Function0<Unit> onDismissed, final Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        String placementId = getEffectivePlacementId(this.metaConfig.getInterstitialPlacementId(), "VID_HD_16_9_46S_LINK");
        Log.d(TAG, "Loading Meta Interstitial with placement: " + placementId);
        final InterstitialAd interstitialAd = new InterstitialAd(activity, placementId);
        InterstitialAd.InterstitialLoadAdConfig loadConfig = interstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() { // from class: com.zyro.browser.ads.MetaAdManager$showInterstitial$listener$1
            @Override // com.facebook.ads.InterstitialAdListener
            public void onInterstitialDisplayed(Ad ad) {
                Log.d("MetaAdManager", "Meta Interstitial displayed");
            }

            @Override // com.facebook.ads.InterstitialAdListener
            public void onInterstitialDismissed(Ad ad) {
                Log.d("MetaAdManager", "Meta Interstitial dismissed");
                onDismissed.invoke();
            }

            @Override // com.facebook.ads.AdListener
            public void onError(Ad ad, AdError error) {
                Log.e("MetaAdManager", "Meta Interstitial error: " + (error != null ? error.getErrorMessage() : null) + " (" + (error != null ? Integer.valueOf(error.getErrorCode()) : null) + ")");
                Function0<Unit> function0 = onError;
                if (function0 != null) {
                    function0.invoke();
                } else {
                    onDismissed.invoke();
                }
            }

            @Override // com.facebook.ads.AdListener
            public void onAdLoaded(Ad ad) {
                Log.d("MetaAdManager", "Meta Interstitial loaded -> showing");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                if (interstitialAd.isAdLoaded() && !interstitialAd.isAdInvalidated()) {
                    interstitialAd.show();
                } else {
                    onDismissed.invoke();
                }
            }

            @Override // com.facebook.ads.AdListener
            public void onAdClicked(Ad ad) {
                Log.d("MetaAdManager", "Meta Interstitial clicked");
            }

            @Override // com.facebook.ads.AdListener
            public void onLoggingImpression(Ad ad) {
                Log.d("MetaAdManager", "Meta Interstitial impression logged");
            }
        }).build();
        interstitialAd.loadAd(loadConfig);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showRewardedVideo$default(MetaAdManager metaAdManager, Activity activity, Function0 function0, Function1 function1, Function0 function02, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function02 = null;
        }
        metaAdManager.showRewardedVideo(activity, function0, function1, function02);
    }

    public final void showRewardedVideo(Activity activity, final Function0<Unit> onAdLoaded, final Function1<? super Boolean, Unit> onRewardCompleted, final Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onRewardCompleted, "onRewardCompleted");
        String placementId = getEffectivePlacementId(this.metaConfig.getRewardedPlacementId(), "VID_HD_16_9_46S_APP_INSTALL");
        Log.d(TAG, "Loading Meta Rewarded Video with placement: " + placementId);
        final RewardedVideoAd rewardedAd = new RewardedVideoAd(activity, placementId);
        final Ref.BooleanRef userEarnedReward = new Ref.BooleanRef();
        RewardedVideoAd.RewardedVideoLoadAdConfig loadConfig = rewardedAd.buildLoadAdConfig().withAdListener(new RewardedVideoAdListener() { // from class: com.zyro.browser.ads.MetaAdManager$showRewardedVideo$listener$1
            @Override // com.facebook.ads.RewardedVideoAdListener
            public void onRewardedVideoCompleted() {
                Log.d("MetaAdManager", "Meta Rewarded Video completed! Reward earned.");
                Ref.BooleanRef.this.element = true;
            }

            @Override // com.facebook.ads.RewardedVideoAdListener
            public void onRewardedVideoClosed() {
                Log.d("MetaAdManager", "Meta Rewarded Video closed");
                onRewardCompleted.invoke(Boolean.valueOf(Ref.BooleanRef.this.element));
            }

            @Override // com.facebook.ads.AdListener
            public void onError(Ad ad, AdError error) {
                Log.e("MetaAdManager", "Meta Rewarded Video error: " + (error != null ? error.getErrorMessage() : null) + " (" + (error != null ? Integer.valueOf(error.getErrorCode()) : null) + ")");
                Function0<Unit> function0 = onError;
                if (function0 != null) {
                    function0.invoke();
                } else {
                    onRewardCompleted.invoke(false);
                }
            }

            @Override // com.facebook.ads.AdListener
            public void onAdLoaded(Ad ad) {
                Log.d("MetaAdManager", "Meta Rewarded Video loaded -> showing");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                if (rewardedAd.isAdLoaded() && !rewardedAd.isAdInvalidated()) {
                    rewardedAd.show();
                } else {
                    onRewardCompleted.invoke(false);
                }
            }

            @Override // com.facebook.ads.AdListener
            public void onAdClicked(Ad ad) {
                Log.d("MetaAdManager", "Meta Rewarded Video clicked");
            }

            @Override // com.facebook.ads.AdListener
            public void onLoggingImpression(Ad ad) {
                Log.d("MetaAdManager", "Meta Rewarded Video impression logged");
            }
        }).build();
        rewardedAd.loadAd(loadConfig);
    }

    public static /* synthetic */ AdView createBannerView$default(MetaAdManager metaAdManager, Context context, boolean z, AdListener adListener, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            adListener = null;
        }
        return metaAdManager.createBannerView(context, z, adListener);
    }

    public final AdView createBannerView(Context context, boolean isMrec, AdListener listener) {
        Intrinsics.checkNotNullParameter(context, "context");
        MetaAdsConfig metaAdsConfig = this.metaConfig;
        String rawId = isMrec ? metaAdsConfig.getBannerMrecPlacementId() : metaAdsConfig.getBannerPlacementId();
        String prefix = isMrec ? "IMG_16_9_APP_INSTALL" : "IMG_16_9_LINK";
        String placementId = getEffectivePlacementId(rawId, prefix);
        AdSize size = isMrec ? AdSize.RECTANGLE_HEIGHT_250 : AdSize.BANNER_HEIGHT_50;
        AdView adView = new AdView(context, placementId, size);
        AdView.AdViewLoadConfigBuilder builder = adView.buildLoadAdConfig();
        if (listener != null) {
            builder.withAdListener(listener);
        }
        adView.loadAd(builder.build());
        return adView;
    }
}
