package com.zyro.browser.ads;

import android.app.Activity;
import android.util.Log;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.firebase.messaging.Constants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: GoogleAdManager.kt */
/* loaded from: classes10.dex */
public final class GoogleAdManager$showRewardedInterstitial$1 extends RewardedInterstitialAdLoadCallback {
    final /* synthetic */ Activity $activity;
    final /* synthetic */ Function0<Unit> $onAdLoaded;
    final /* synthetic */ Function1<Boolean, Unit> $onDismissed;
    final /* synthetic */ Function0<Unit> $onError;
    final /* synthetic */ Function0<Unit> $onUserEarnedReward;

    /* JADX WARN: Multi-variable type inference failed */
    GoogleAdManager$showRewardedInterstitial$1(Function0<Unit> function0, Activity $activity, Function1<? super Boolean, Unit> function1, Function0<Unit> function02, Function0<Unit> function03) {
        this.$onAdLoaded = function0;
        this.$activity = $activity;
        this.$onDismissed = function1;
        this.$onUserEarnedReward = function02;
        this.$onError = function03;
    }

    @Override // com.google.android.gms.ads.AdLoadCallback
    public void onAdLoaded(RewardedInterstitialAd ad) {
        Intrinsics.checkNotNullParameter(ad, "ad");
        Function0<Unit> function0 = this.$onAdLoaded;
        if (function0 != null) {
            function0.invoke();
        }
        final Ref.BooleanRef userEarnedReward = new Ref.BooleanRef();
        final Function1<Boolean, Unit> function1 = this.$onDismissed;
        ad.setFullScreenContentCallback(new FullScreenContentCallback() { // from class: com.zyro.browser.ads.GoogleAdManager$showRewardedInterstitial$1$onAdLoaded$1
            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdDismissedFullScreenContent() {
                Log.d("GoogleAdManager", "GAM Rewarded Interstitial dismissed. rewardEarned=" + Ref.BooleanRef.this.element);
                function1.invoke(Boolean.valueOf(Ref.BooleanRef.this.element));
            }

            @Override // com.google.android.gms.ads.FullScreenContentCallback
            public void onAdFailedToShowFullScreenContent(AdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.e("GoogleAdManager", "GAM Rewarded Interstitial failed: " + error.getMessage());
                function1.invoke(false);
            }
        });
        Activity activity = this.$activity;
        final Function0<Unit> function02 = this.$onUserEarnedReward;
        ad.show(activity, new OnUserEarnedRewardListener() { // from class: com.zyro.browser.ads.GoogleAdManager$showRewardedInterstitial$1$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.ads.OnUserEarnedRewardListener
            public final void onUserEarnedReward(RewardItem rewardItem) {
                GoogleAdManager$showRewardedInterstitial$1.onAdLoaded$lambda$0(Ref.BooleanRef.this, function02, rewardItem);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAdLoaded$lambda$0(Ref.BooleanRef userEarnedReward, Function0 $onUserEarnedReward, RewardItem rewardItem) {
        Intrinsics.checkNotNullParameter(userEarnedReward, "$userEarnedReward");
        Intrinsics.checkNotNullParameter(rewardItem, "rewardItem");
        Log.d("GoogleAdManager", "User earned GAM Rewarded Interstitial: " + rewardItem.getAmount() + " " + rewardItem.getType());
        userEarnedReward.element = true;
        if ($onUserEarnedReward != null) {
            $onUserEarnedReward.invoke();
        }
    }

    @Override // com.google.android.gms.ads.AdLoadCallback
    public void onAdFailedToLoad(LoadAdError error) {
        Intrinsics.checkNotNullParameter(error, "error");
        Log.e("GoogleAdManager", "GAM Rewarded Interstitial failed to load: " + error.getMessage());
        Function0<Unit> function0 = this.$onError;
        if (function0 != null) {
            function0.invoke();
        } else {
            this.$onDismissed.invoke(false);
        }
    }
}
