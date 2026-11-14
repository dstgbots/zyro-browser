package com.zyro.admin.model

data class GoogleAdsConfig(
    var adsEnabled: Boolean = true,
    var testMode: Boolean = true,
    var appId: String = "ca-app-pub-3940256099942544~3347511713",
    var bannerAdUnitId: String = "ca-app-pub-3940256099942544/9214589741",
    var interstitialAdUnitId: String = "ca-app-pub-3940256099942544/1033173712",
    var rewardedAdUnitId: String = GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK,
    var rewardedInterstitialAdUnitId: String = "ca-app-pub-3940256099942544/5354046379",
    var nativeAdUnitId: String = "ca-app-pub-3940256099942544/2247696110",
    var appOpenAdUnitId: String = "ca-app-pub-3940256099942544/9257395921",
    var interstitialInterval: Int = 3,
    var bannerPosition: String = "BOTTOM",
    var safelinkGoogleAdsEnabled: Boolean = true
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "adsEnabled" to adsEnabled,
            "testMode" to testMode,
            "appId" to appId,
            "bannerAdUnitId" to bannerAdUnitId,
            "interstitialAdUnitId" to interstitialAdUnitId,
            "rewardedAdUnitId" to rewardedAdUnitId,
            "rewardedInterstitialAdUnitId" to rewardedInterstitialAdUnitId,
            "nativeAdUnitId" to nativeAdUnitId,
            "appOpenAdUnitId" to appOpenAdUnitId,
            "interstitialInterval" to interstitialInterval,
            "bannerPosition" to bannerPosition,
            "safelinkGoogleAdsEnabled" to safelinkGoogleAdsEnabled,
            "updatedAt" to System.currentTimeMillis()
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): GoogleAdsConfig {
            return GoogleAdsConfig(
                adsEnabled = (map["adsEnabled"] as? Boolean) ?: true,
                testMode = (map["testMode"] as? Boolean) ?: true,
                appId = (map["appId"] as? String) ?: "ca-app-pub-3940256099942544~3347511713",
                bannerAdUnitId = (map["bannerAdUnitId"] as? String) ?: "ca-app-pub-3940256099942544/9214589741",
                interstitialAdUnitId = (map["interstitialAdUnitId"] as? String) ?: "ca-app-pub-3940256099942544/1033173712",
                rewardedAdUnitId = (map["rewardedAdUnitId"] as? String) ?: GoogleAdManagerConfig.DEMO_REWARDED_FALLBACK,
                rewardedInterstitialAdUnitId = (map["rewardedInterstitialAdUnitId"] as? String) ?: "ca-app-pub-3940256099942544/5354046379",
                nativeAdUnitId = (map["nativeAdUnitId"] as? String) ?: "ca-app-pub-3940256099942544/2247696110",
                appOpenAdUnitId = (map["appOpenAdUnitId"] as? String) ?: "ca-app-pub-3940256099942544/9257395921",
                interstitialInterval = ((map["interstitialInterval"] as? Number)?.toInt()) ?: 3,
                bannerPosition = (map["bannerPosition"] as? String) ?: "BOTTOM",
                safelinkGoogleAdsEnabled = (map["safelinkGoogleAdsEnabled"] as? Boolean) ?: true
            )
        }
    }
}
