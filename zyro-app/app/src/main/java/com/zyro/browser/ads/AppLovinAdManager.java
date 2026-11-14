package com.zyro.browser.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.core.app.FrameMetricsAggregator;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkInitializationConfiguration;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.zyro.browser.model.AppLovinAdsConfig;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* compiled from: AppLovinAdManager.kt */
/* loaded from: classes10.dex */
public final class AppLovinAdManager {
    private static final String TAG = "AppLovinAdManager";
    private AppLovinAdsConfig applovinConfig;
    private boolean isInitialized;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<AppLovinAdManager> instance$delegate = LazyKt.lazy(new Function0<AppLovinAdManager>() { // from class: com.zyro.browser.ads.AppLovinAdManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final AppLovinAdManager invoke() {
            return new AppLovinAdManager(null);
        }
    });

    public /* synthetic */ AppLovinAdManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private AppLovinAdManager() {
        this.applovinConfig = new AppLovinAdsConfig(false, false, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    /* compiled from: AppLovinAdManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AppLovinAdManager getInstance() {
            return (AppLovinAdManager) AppLovinAdManager.instance$delegate.getValue();
        }
    }

    public final void updateConfig(AppLovinAdsConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.applovinConfig = config;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.isInitialized) {
            return;
        }
        String obj = StringsKt.trim((CharSequence) this.applovinConfig.getSdkKey()).toString();
        if (obj.length() == 0) {
            obj = "YOUR_APPLOVIN_SDK_KEY";
        }
        String effectiveKey = obj;
        Log.d(TAG, "Initializing AppLovin MAX SDK...");
        try {
            AppLovinSdkInitializationConfiguration initConfig = AppLovinSdkInitializationConfiguration.builder(effectiveKey, context.getApplicationContext()).setMediationProvider(AppLovinMediationProvider.MAX).build();
            AppLovinSdk.getInstance(context.getApplicationContext()).initialize(initConfig, new AppLovinSdk.SdkInitializationListener() { // from class: com.zyro.browser.ads.AppLovinAdManager$$ExternalSyntheticLambda0
                @Override // com.applovin.sdk.AppLovinSdk.SdkInitializationListener
                public final void onSdkInitialized(AppLovinSdkConfiguration appLovinSdkConfiguration) {
                    AppLovinAdManager.init$lambda$1(AppLovinAdManager.this, appLovinSdkConfiguration);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize AppLovin SDK: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$1(AppLovinAdManager this$0, AppLovinSdkConfiguration it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isInitialized = true;
        Log.d(TAG, "AppLovin MAX SDK initialized successfully! Test Mode: " + this$0.applovinConfig.getTestMode());
    }

    public final void showMediationDebugger(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        try {
            AppLovinSdk.getInstance(activity).showMediationDebugger();
        } catch (Exception e) {
            Log.e(TAG, "Failed to open Mediation Debugger: " + e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showInterstitial$default(AppLovinAdManager appLovinAdManager, Activity activity, Function0 function0, Function0 function02, Function0 function03, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function03 = null;
        }
        appLovinAdManager.showInterstitial(activity, function0, function02, function03);
    }

    public final void showInterstitial(final Activity activity, final Function0<Unit> onAdLoaded, final Function0<Unit> onDismissed, final Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        String obj = StringsKt.trim((CharSequence) this.applovinConfig.getInterstitialAdUnitId()).toString();
        if (obj.length() == 0) {
            obj = "YOUR_INTERSTITIAL_AD_UNIT_ID";
        }
        String adUnitId = obj;
        Log.d(TAG, "Loading AppLovin Interstitial with adUnitId: " + adUnitId);
        final MaxInterstitialAd interstitialAd = new MaxInterstitialAd(adUnitId, activity);
        interstitialAd.setListener(new MaxAdListener() { // from class: com.zyro.browser.ads.AppLovinAdManager$showInterstitial$1
            @Override // com.applovin.mediation.MaxAdListener
            public void onAdLoaded(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Interstitial loaded -> showing");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd(activity);
                } else {
                    onDismissed.invoke();
                }
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdDisplayed(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Interstitial displayed");
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdHidden(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Interstitial dismissed / closed");
                onDismissed.invoke();
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdClicked(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Interstitial clicked");
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdLoadFailed(String adUnitId2, MaxError error) {
                Intrinsics.checkNotNullParameter(adUnitId2, "adUnitId");
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("AppLovinAdManager", "AppLovin Interstitial load failed: " + error.getMessage() + " (" + error.getCode() + ")");
                Function0<Unit> function0 = onError;
                if (function0 == null) {
                    function0 = onDismissed;
                }
                function0.invoke();
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("AppLovinAdManager", "AppLovin Interstitial display failed: " + error.getMessage() + " (" + error.getCode() + ")");
                onDismissed.invoke();
            }
        });
        interstitialAd.loadAd();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showRewardedVideo$default(AppLovinAdManager appLovinAdManager, Activity activity, Function0 function0, Function1 function1, Function0 function02, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function02 = null;
        }
        appLovinAdManager.showRewardedVideo(activity, function0, function1, function02);
    }

    public final void showRewardedVideo(final Activity activity, final Function0<Unit> onAdLoaded, final Function1<? super Boolean, Unit> onRewardCompleted, final Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onRewardCompleted, "onRewardCompleted");
        String obj = StringsKt.trim((CharSequence) this.applovinConfig.getRewardedAdUnitId()).toString();
        if (obj.length() == 0) {
            obj = "YOUR_REWARDED_AD_UNIT_ID";
        }
        String adUnitId = obj;
        Log.d(TAG, "Loading AppLovin Rewarded Video with adUnitId: " + adUnitId);
        final MaxRewardedAd rewardedAd = MaxRewardedAd.getInstance(adUnitId, activity);
        final Ref.BooleanRef userEarnedReward = new Ref.BooleanRef();
        rewardedAd.setListener(new MaxRewardedAdListener() { // from class: com.zyro.browser.ads.AppLovinAdManager$showRewardedVideo$1
            @Override // com.applovin.mediation.MaxAdListener
            public void onAdLoaded(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Rewarded Video loaded -> showing");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                if (rewardedAd.isReady()) {
                    rewardedAd.showAd(activity);
                } else {
                    onRewardCompleted.invoke(false);
                }
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdDisplayed(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Rewarded Video displayed");
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdHidden(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Rewarded Video closed");
                onRewardCompleted.invoke(Boolean.valueOf(userEarnedReward.element));
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdClicked(MaxAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Log.d("AppLovinAdManager", "AppLovin Rewarded Video clicked");
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdLoadFailed(String adUnitId2, MaxError error) {
                Intrinsics.checkNotNullParameter(adUnitId2, "adUnitId");
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("AppLovinAdManager", "AppLovin Rewarded Video load failed: " + error.getMessage() + " (" + error.getCode() + ")");
                Function0<Unit> function0 = onError;
                if (function0 != null) {
                    function0.invoke();
                } else {
                    onRewardCompleted.invoke(false);
                }
            }

            @Override // com.applovin.mediation.MaxAdListener
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("AppLovinAdManager", "AppLovin Rewarded Video display failed: " + error.getMessage() + " (" + error.getCode() + ")");
                onRewardCompleted.invoke(false);
            }

            @Override // com.applovin.mediation.MaxRewardedAdListener
            public void onUserRewarded(MaxAd ad, MaxReward reward) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Intrinsics.checkNotNullParameter(reward, "reward");
                Log.d("AppLovinAdManager", "AppLovin Rewarded Video completed! Reward: " + reward.getAmount() + " " + reward.getLabel());
                userEarnedReward.element = true;
            }
        });
        rewardedAd.loadAd();
    }

    public static /* synthetic */ MaxAdView createBannerView$default(AppLovinAdManager appLovinAdManager, Context context, boolean z, MaxAdViewAdListener maxAdViewAdListener, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            maxAdViewAdListener = null;
        }
        return appLovinAdManager.createBannerView(context, z, maxAdViewAdListener);
    }

    public final MaxAdView createBannerView(Context context, boolean isMrec, MaxAdViewAdListener listener) {
        String str;
        MaxAdView adView;
        Intrinsics.checkNotNullParameter(context, "context");
        if (isMrec) {
            String obj = StringsKt.trim((CharSequence) this.applovinConfig.getMrecAdUnitId()).toString();
            if (obj.length() == 0) {
                obj = "YOUR_MREC_AD_UNIT_ID";
            }
            str = obj;
        } else {
            String obj2 = StringsKt.trim((CharSequence) this.applovinConfig.getBannerAdUnitId()).toString();
            if (obj2.length() == 0) {
                obj2 = "YOUR_BANNER_AD_UNIT_ID";
            }
            str = obj2;
        }
        String adUnitId = str;
        if (isMrec) {
            adView = new MaxAdView(adUnitId, MaxAdFormat.MREC, context);
        } else {
            adView = new MaxAdView(adUnitId, context);
        }
        if (listener != null) {
            adView.setListener(listener);
        }
        adView.loadAd();
        return adView;
    }
}
