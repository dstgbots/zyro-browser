package com.zyro.browser.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdInspectorError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnAdInspectorClosedListener;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.zyro.browser.model.GoogleAdManagerConfig;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* compiled from: GoogleAdManager.kt */
/* loaded from: classes10.dex */
public final class GoogleAdManager {
    private static final String TAG = "GoogleAdManager";
    private GoogleAdManagerConfig gamConfig;
    private boolean isInitialized;
    private boolean isInterstitialLoading;
    private boolean isRewardedLoading;
    private AdManagerInterstitialAd preloadedInterstitial;
    private RewardedAd preloadedRewarded;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<GoogleAdManager> instance$delegate = LazyKt.lazy(new Function0<GoogleAdManager>() { // from class: com.zyro.browser.ads.GoogleAdManager$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final GoogleAdManager invoke() {
            return new GoogleAdManager(null);
        }
    });

    public /* synthetic */ GoogleAdManager(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private GoogleAdManager() {
        this.gamConfig = new GoogleAdManagerConfig(false, false, null, null, null, null, null, null, null, null, null, 0, null, false, false, 0L, 65535, null);
    }

    /* compiled from: GoogleAdManager.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GoogleAdManager getInstance() {
            return (GoogleAdManager) GoogleAdManager.instance$delegate.getValue();
        }
    }

    public final void updateConfig(GoogleAdManagerConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.gamConfig = config;
        Log.d(TAG, "Google Ad Manager config updated: testMode=" + config.getTestMode() + ", networkCode=" + config.getNetworkCode());
    }

    /* renamed from: getConfig, reason: from getter */
    public final GoogleAdManagerConfig getGamConfig() {
        return this.gamConfig;
    }

    public final void init(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.isInitialized) {
            return;
        }
        try {
            List testDeviceIds = CollectionsKt.mutableListOf("C461CDBBAD2423FDF56B59308C7AA4AF", "B3EEABB8EE11C2BE770B684D95219ECB");
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            Intrinsics.checkNotNullExpressionValue(configuration, "Builder()\n              …\n                .build()");
            MobileAds.setRequestConfiguration(configuration);
            MobileAds.initialize(context, new OnInitializationCompleteListener() { // from class: com.zyro.browser.ads.GoogleAdManager$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.ads.initialization.OnInitializationCompleteListener
                public final void onInitializationComplete(InitializationStatus initializationStatus) {
                    GoogleAdManager.init$lambda$0(GoogleAdManager.this, context, initializationStatus);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize Google Ad Manager: " + e.getMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(GoogleAdManager this$0, Context context, InitializationStatus status) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(status, "status");
        this$0.isInitialized = true;
        Log.d(TAG, "Google Ad Manager initialized. Adapter statuses: " + status.getAdapterStatusMap().keySet());
        this$0.preloadInterstitial(context);
        this$0.preloadRewarded(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AdManagerAdRequest buildAdRequest$default(GoogleAdManager googleAdManager, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            map = null;
        }
        return googleAdManager.buildAdRequest(map);
    }

    public final AdManagerAdRequest buildAdRequest(Map<String, String> extraTargeting) {
        AdManagerAdRequest.Builder builder = new AdManagerAdRequest.Builder();
        Map $this$forEach$iv = this.gamConfig.getCustomTargeting();
        for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            String key = element$iv.getKey();
            String value = element$iv.getValue();
            if ((!StringsKt.isBlank(key)) && (!StringsKt.isBlank(value))) {
                builder.addCustomTargeting(StringsKt.trim((CharSequence) key).toString(), StringsKt.trim((CharSequence) value).toString());
            }
        }
        if (extraTargeting != null) {
            for (Map.Entry element$iv2 : extraTargeting.entrySet()) {
                String key2 = element$iv2.getKey();
                String value2 = element$iv2.getValue();
                if ((!StringsKt.isBlank(key2)) && (!StringsKt.isBlank(value2))) {
                    builder.addCustomTargeting(StringsKt.trim((CharSequence) key2).toString(), StringsKt.trim((CharSequence) value2).toString());
                }
            }
        }
        builder.addCustomTargeting("app", "zyro_browser");
        builder.addCustomTargeting("mode", this.gamConfig.getTestMode() ? "test" : "live");
        AdManagerAdRequest build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "builder.build()");
        return build;
    }

    private final String getEffectiveAdUnitId(String rawId, String demoFallback) {
        if (this.gamConfig.getTestMode()) {
            return demoFallback;
        }
        String obj = StringsKt.trim((CharSequence) rawId).toString();
        if (obj.length() == 0) {
            obj = demoFallback;
        }
        return obj;
    }

    public static /* synthetic */ AdManagerAdView createBannerView$default(GoogleAdManager googleAdManager, Activity activity, boolean z, AdListener adListener, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            adListener = null;
        }
        return googleAdManager.createBannerView(activity, z, adListener);
    }

    public final AdManagerAdView createBannerView(Activity activity, boolean isAdaptive, AdListener listener) {
        AdSize primaryAdSize;
        Intrinsics.checkNotNullParameter(activity, "activity");
        AdManagerAdView adView = new AdManagerAdView(activity);
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getBannerAdUnitId(), GoogleAdManagerConfig.DEMO_ADAPTIVE_BANNER);
        adView.setAdUnitId(adUnitId);
        if (isAdaptive) {
            primaryAdSize = getAdaptiveBannerSize(activity);
        } else {
            primaryAdSize = AdSize.BANNER;
            Intrinsics.checkNotNullExpressionValue(primaryAdSize, "{\n            AdSize.BANNER\n        }");
        }
        adView.setAdSizes(primaryAdSize, AdSize.BANNER, AdSize.MEDIUM_RECTANGLE);
        if (listener != null) {
            adView.setAdListener(listener);
        }
        Log.d(TAG, "Loading GAM Banner with unit: " + adUnitId + ", primary size: " + primaryAdSize);
        adView.loadAd(buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "banner"))));
        return adView;
    }

    private final AdSize getAdaptiveBannerSize(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        Intrinsics.checkNotNullExpressionValue(displayMetrics, "activity.resources.displayMetrics");
        float widthPixels = displayMetrics.widthPixels;
        float density = displayMetrics.density;
        int adWidth = (int) (widthPixels / density);
        AdSize currentOrientationAnchoredAdaptiveBannerAdSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
        Intrinsics.checkNotNullExpressionValue(currentOrientationAnchoredAdaptiveBannerAdSize, "getCurrentOrientationAnc…AdSize(activity, adWidth)");
        return currentOrientationAnchoredAdaptiveBannerAdSize;
    }

    public final void preloadInterstitial(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.preloadedInterstitial != null || this.isInterstitialLoading || !this.gamConfig.getAdsEnabled()) {
            return;
        }
        this.isInterstitialLoading = true;
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getInterstitialAdUnitId(), GoogleAdManagerConfig.DEMO_INTERSTITIAL);
        Log.d(TAG, "Pre-loading GAM Interstitial: " + adUnitId);
        loadInterstitialInternal(context, adUnitId, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadInterstitialInternal(final Context context, final String adUnitId, final boolean isFallback) {
        AdManagerInterstitialAd.load(context, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "interstitial"))), new AdManagerInterstitialAdLoadCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$loadInterstitialInternal$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(AdManagerInterstitialAd interstitialAd) {
                Intrinsics.checkNotNullParameter(interstitialAd, "interstitialAd");
                GoogleAdManager.this.isInterstitialLoading = false;
                GoogleAdManager.this.preloadedInterstitial = interstitialAd;
                Log.d("GoogleAdManager", "GAM Interstitial successfully pre-loaded (" + adUnitId + ")");
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                GoogleAdManagerConfig googleAdManagerConfig;
                Intrinsics.checkNotNullParameter(loadAdError, "loadAdError");
                Log.e("GoogleAdManager", "GAM Interstitial pre-load failed (" + adUnitId + "): " + loadAdError.getMessage() + " (" + loadAdError.getCode() + ")");
                if (!isFallback) {
                    googleAdManagerConfig = GoogleAdManager.this.gamConfig;
                    if (googleAdManagerConfig.getTestMode()) {
                        String str = adUnitId;
                        String str2 = GoogleAdManagerConfig.DEMO_INTERSTITIAL;
                        if (Intrinsics.areEqual(str, GoogleAdManagerConfig.DEMO_INTERSTITIAL)) {
                            str2 = GoogleAdManagerConfig.DEMO_INTERSTITIAL_ALT;
                        }
                        String fallbackUnit = str2;
                        Log.d("GoogleAdManager", "Attempting GAM Interstitial preload fallback with unit: " + fallbackUnit);
                        GoogleAdManager.this.loadInterstitialInternal(context, fallbackUnit, true);
                        return;
                    }
                }
                GoogleAdManager.this.isInterstitialLoading = false;
                GoogleAdManager.this.preloadedInterstitial = null;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showInterstitial$default(GoogleAdManager googleAdManager, Activity activity, Function0 function0, Function0 function02, Function0 function03, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function03 = null;
        }
        googleAdManager.showInterstitial(activity, function0, function02, function03);
    }

    public final void showInterstitial(Activity activity, Function0<Unit> onAdLoaded, Function0<Unit> onDismissed, Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        AdManagerInterstitialAd cachedAd = this.preloadedInterstitial;
        if (cachedAd != null) {
            this.preloadedInterstitial = null;
            if (onAdLoaded != null) {
                onAdLoaded.invoke();
            }
            presentInterstitial(activity, cachedAd, onDismissed);
            preloadInterstitial(activity);
            return;
        }
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getInterstitialAdUnitId(), GoogleAdManagerConfig.DEMO_INTERSTITIAL);
        Log.d(TAG, "Loading on-demand GAM Interstitial: " + adUnitId);
        loadAndShowInterstitial(activity, adUnitId, false, onAdLoaded, onDismissed, onError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadAndShowInterstitial(final Activity activity, final String adUnitId, final boolean isFallback, final Function0<Unit> onAdLoaded, final Function0<Unit> onDismissed, final Function0<Unit> onError) {
        AdManagerInterstitialAd.load(activity, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "interstitial"))), new AdManagerInterstitialAdLoadCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$loadAndShowInterstitial$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(AdManagerInterstitialAd interstitialAd) {
                Intrinsics.checkNotNullParameter(interstitialAd, "interstitialAd");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                this.presentInterstitial(activity, interstitialAd, onDismissed);
                this.preloadInterstitial(activity);
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                GoogleAdManagerConfig googleAdManagerConfig;
                Intrinsics.checkNotNullParameter(loadAdError, "loadAdError");
                Log.e("GoogleAdManager", "GAM Interstitial on-demand load failed (" + adUnitId + "): " + loadAdError.getMessage());
                if (!isFallback) {
                    googleAdManagerConfig = this.gamConfig;
                    if (googleAdManagerConfig.getTestMode()) {
                        String str = adUnitId;
                        String str2 = GoogleAdManagerConfig.DEMO_INTERSTITIAL;
                        if (Intrinsics.areEqual(str, GoogleAdManagerConfig.DEMO_INTERSTITIAL)) {
                            str2 = GoogleAdManagerConfig.DEMO_INTERSTITIAL_ALT;
                        }
                        String fallbackUnit = str2;
                        Log.d("GoogleAdManager", "Retrying on-demand GAM Interstitial with fallback unit: " + fallbackUnit);
                        this.loadAndShowInterstitial(activity, fallbackUnit, true, onAdLoaded, onDismissed, onError);
                        return;
                    }
                }
                Function0<Unit> function0 = onError;
                if (function0 == null) {
                    function0 = onDismissed;
                }
                function0.invoke();
                this.preloadInterstitial(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void presentInterstitial(Activity activity, AdManagerInterstitialAd ad, final Function0<Unit> onDismissed) {
        ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$presentInterstitial$1
            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdDismissedFullScreenContent() {
                Log.d("GoogleAdManager", "GAM Interstitial dismissed");
                onDismissed.invoke();
            }

            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdFailedToShowFullScreenContent(AdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Interstitial failed to show: " + error.getMessage());
                onDismissed.invoke();
            }

            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdShowedFullScreenContent() {
                Log.d("GoogleAdManager", "GAM Interstitial displayed");
            }
        });
        ad.show(activity);
    }

    public final void preloadRewarded(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.preloadedRewarded != null || this.isRewardedLoading || !this.gamConfig.getAdsEnabled()) {
            return;
        }
        this.isRewardedLoading = true;
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getRewardedAdUnitId(), GoogleAdManagerConfig.DEMO_REWARDED);
        Log.d(TAG, "Pre-loading GAM Rewarded Ad: " + adUnitId);
        loadRewardedInternal(context, adUnitId, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadRewardedInternal(final Context context, final String adUnitId, final boolean isFallback) {
        RewardedAd.load(context, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "rewarded"))), new RewardedAdLoadCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$loadRewardedInternal$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(RewardedAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                GoogleAdManager.this.isRewardedLoading = false;
                GoogleAdManager.this.preloadedRewarded = ad;
                Log.d("GoogleAdManager", "GAM Rewarded Ad successfully pre-loaded (" + adUnitId + ")");
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError error) {
                GoogleAdManagerConfig googleAdManagerConfig;
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Rewarded Ad pre-load failed (" + adUnitId + "): " + error.getMessage());
                if (!isFallback) {
                    googleAdManagerConfig = GoogleAdManager.this.gamConfig;
                    if (googleAdManagerConfig.getTestMode()) {
                        Log.d("GoogleAdManager", "Attempting GAM Rewarded preload fallback with unit: ca-app-pub-3940256099942544/5224354917");
                        GoogleAdManager.this.loadRewardedInternal(context, GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK, true);
                        return;
                    }
                }
                GoogleAdManager.this.isRewardedLoading = false;
                GoogleAdManager.this.preloadedRewarded = null;
            }
        });
    }

    public final void showRewarded(Activity activity, Function0<Unit> onAdLoaded, Function0<Unit> onUserEarnedReward, Function1<? super Boolean, Unit> onDismissed, Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        RewardedAd cachedAd = this.preloadedRewarded;
        if (cachedAd != null) {
            this.preloadedRewarded = null;
            if (onAdLoaded != null) {
                onAdLoaded.invoke();
            }
            presentRewarded(activity, cachedAd, onUserEarnedReward, onDismissed);
            preloadRewarded(activity);
            return;
        }
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getRewardedAdUnitId(), GoogleAdManagerConfig.DEMO_REWARDED);
        Log.d(TAG, "Loading on-demand GAM Rewarded: " + adUnitId);
        loadAndShowRewarded(activity, adUnitId, false, onAdLoaded, onUserEarnedReward, onDismissed, onError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadAndShowRewarded(final Activity activity, final String adUnitId, final boolean isFallback, final Function0<Unit> onAdLoaded, final Function0<Unit> onUserEarnedReward, final Function1<? super Boolean, Unit> onDismissed, final Function0<Unit> onError) {
        RewardedAd.load((Context) activity, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "rewarded"))), new RewardedAdLoadCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$loadAndShowRewarded$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(RewardedAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                this.presentRewarded(activity, ad, onUserEarnedReward, onDismissed);
                this.preloadRewarded(activity);
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError error) {
                GoogleAdManagerConfig googleAdManagerConfig;
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Rewarded load failed (" + adUnitId + "): " + error.getMessage());
                if (!isFallback) {
                    googleAdManagerConfig = this.gamConfig;
                    if (googleAdManagerConfig.getTestMode()) {
                        Log.d("GoogleAdManager", "Retrying on-demand GAM Rewarded with fallback unit: ca-app-pub-3940256099942544/5224354917");
                        this.loadAndShowRewarded(activity, GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK, true, onAdLoaded, onUserEarnedReward, onDismissed, onError);
                        return;
                    }
                }
                Function0<Unit> function0 = onError;
                if (function0 != null) {
                    function0.invoke();
                } else {
                    onDismissed.invoke(false);
                }
                this.preloadRewarded(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void presentRewarded(Activity activity, RewardedAd ad, final Function0<Unit> onUserEarnedReward, final Function1<? super Boolean, Unit> onDismissed) {
        final Ref.BooleanRef userEarnedReward = new Ref.BooleanRef();
        ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$presentRewarded$1
            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdDismissedFullScreenContent() {
                Log.d("GoogleAdManager", "GAM Rewarded dismissed. rewardEarned=" + Ref.BooleanRef.this.element);
                onDismissed.invoke(Boolean.valueOf(Ref.BooleanRef.this.element));
            }

            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdFailedToShowFullScreenContent(AdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Rewarded failed to show: " + error.getMessage());
                onDismissed.invoke(false);
            }
        });
        ad.show(activity, new OnUserEarnedRewardListener() { // from class: com.zyro.browser.ads.GoogleAdManager$$ExternalSyntheticLambda3
            @Override // com.google.android.gms.ads.OnUserEarnedRewardListener
            public final void onUserEarnedReward(RewardItem rewardItem) {
                GoogleAdManager.presentRewarded$lambda$4(Ref.BooleanRef.this, onUserEarnedReward, rewardItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void presentRewarded$lambda$4(Ref.BooleanRef userEarnedReward, Function0 $onUserEarnedReward, RewardItem rewardItem) {
        Intrinsics.checkNotNullParameter(userEarnedReward, "$userEarnedReward");
        Intrinsics.checkNotNullParameter(rewardItem, "rewardItem");
        Log.d(TAG, "User earned GAM reward: " + rewardItem.getAmount() + " " + rewardItem.getType());
        userEarnedReward.element = true;
        if ($onUserEarnedReward != null) {
            $onUserEarnedReward.invoke();
        }
    }

    public final void showRewardedInterstitial(Activity activity, Function0<Unit> onAdLoaded, Function0<Unit> onUserEarnedReward, Function1<? super Boolean, Unit> onDismissed, Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getRewardedInterstitialAdUnitId(), GoogleAdManagerConfig.DEMO_REWARDED_INTERSTITIAL);
        Log.d(TAG, "Loading GAM Rewarded Interstitial: " + adUnitId);
        RewardedInterstitialAd.load((Context) activity, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "rewarded_interstitial"))), (RewardedInterstitialAdLoadCallback) new GoogleAdManager$showRewardedInterstitial$1(onAdLoaded, activity, onDismissed, onUserEarnedReward, onError));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void showAppOpenAd$default(GoogleAdManager googleAdManager, Activity activity, Function0 function0, Function0 function02, Function0 function03, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = null;
        }
        if ((i & 8) != 0) {
            function03 = null;
        }
        googleAdManager.showAppOpenAd(activity, function0, function02, function03);
    }

    public final void showAppOpenAd(final Activity activity, final Function0<Unit> onAdLoaded, final Function0<Unit> onDismissed, final Function0<Unit> onError) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(onDismissed, "onDismissed");
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getAppOpenAdUnitId(), GoogleAdManagerConfig.DEMO_APP_OPEN);
        Log.d(TAG, "Loading GAM App Open Ad: " + adUnitId);
        AppOpenAd.load(activity, adUnitId, buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", FirebaseAnalytics.Event.APP_OPEN))), new AppOpenAd.AppOpenAdLoadCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$showAppOpenAd$1
            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdLoaded(AppOpenAd ad) {
                Intrinsics.checkNotNullParameter(ad, "ad");
                Function0<Unit> function0 = onAdLoaded;
                if (function0 != null) {
                    function0.invoke();
                }
                final Function0<Unit> function02 = onDismissed;
                ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$showAppOpenAd$1$onAdLoaded$1
                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdDismissedFullScreenContent() {
                        function02.invoke();
                    }

                    @Override // com.google.android.gms.ads.FullScreenContentCallback
                    public void onAdFailedToShowFullScreenContent(AdError error) {
                        Intrinsics.checkNotNullParameter(error, "error");
                        Log.e("GoogleAdManager", "GAM App Open failed to show: " + error.getMessage());
                        function02.invoke();
                    }
                });
                ad.show(activity);
            }

            @Override // com.google.android.gms.ads.AdLoadCallback
            public void onAdFailedToLoad(LoadAdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM App Open load failed: " + error.getMessage());
                Function0<Unit> function0 = onError;
                if (function0 == null) {
                    function0 = onDismissed;
                }
                function0.invoke();
            }
        });
    }

    public final void loadNativeAd(Context context, final Function1<? super NativeAd, Unit> onLoaded, final Function1<? super LoadAdError, Unit> onError) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onLoaded, "onLoaded");
        Intrinsics.checkNotNullParameter(onError, "onError");
        String adUnitId = getEffectiveAdUnitId(this.gamConfig.getNativeAdUnitId(), GoogleAdManagerConfig.DEMO_NATIVE);
        Log.d(TAG, "Loading GAM Native Ad: " + adUnitId);
        AdLoader adLoader = new AdLoader.Builder(context, adUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() { // from class: com.zyro.browser.ads.GoogleAdManager$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
            public final void onNativeAdLoaded(NativeAd nativeAd) {
                GoogleAdManager.loadNativeAd$lambda$5(Function1.this, nativeAd);
            }
        }).withAdListener(new AdListener() { // from class: com.zyro.browser.ads.GoogleAdManager$loadNativeAd$adLoader$2
            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Native Ad failed to load: " + error.getMessage() + " (" + error.getCode() + ")");
                onError.invoke(error);
            }
        }).build();
        Intrinsics.checkNotNullExpressionValue(adLoader, "onError: (LoadAdError) -…  })\n            .build()");
        adLoader.loadAd(buildAdRequest(MapsKt.mapOf(TuplesKt.to("format", "native"))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadNativeAd$lambda$5(Function1 onLoaded, NativeAd nativeAd) {
        Intrinsics.checkNotNullParameter(onLoaded, "$onLoaded");
        Intrinsics.checkNotNullParameter(nativeAd, "nativeAd");
        Log.d(TAG, "GAM Native Ad loaded successfully");
        onLoaded.invoke(nativeAd);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void openAdInspector$default(GoogleAdManager googleAdManager, Activity activity, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        googleAdManager.openAdInspector(activity, function1);
    }

    public final void openAdInspector(Activity activity, final Function1<? super AdInspectorError, Unit> onComplete) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        try {
            MobileAds.openAdInspector(activity, new OnAdInspectorClosedListener() { // from class: com.zyro.browser.ads.GoogleAdManager$$ExternalSyntheticLambda2
                @Override // com.google.android.gms.ads.OnAdInspectorClosedListener
                public final void onAdInspectorClosed(AdInspectorError adInspectorError) {
                    GoogleAdManager.openAdInspector$lambda$6(Function1.this, adInspectorError);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Could not open Ad Inspector: " + e.getMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void openAdInspector$lambda$6(Function1 $onComplete, AdInspectorError error) {
        if (error == null) {
            Log.d(TAG, "Ad Inspector closed successfully.");
        } else {
            Log.e(TAG, "Ad Inspector opened with error: " + error.getMessage() + " (" + error.getCode() + ")");
        }
        if ($onComplete != null) {
            $onComplete.invoke(error);
        }
    }
}
