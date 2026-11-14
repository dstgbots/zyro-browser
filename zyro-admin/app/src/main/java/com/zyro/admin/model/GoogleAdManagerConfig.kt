package com.zyro.admin.model

data class GoogleAdManagerConfig(
    var adsEnabled: Boolean = true,
    var testMode: Boolean = true,
    var networkCode: String = "21775744923",
    var bannerAdUnitId: String = DEMO_ADAPTIVE_BANNER,
    var fixedBannerAdUnitId: String = DEMO_FIXED_BANNER,
    var interstitialAdUnitId: String = DEMO_INTERSTITIAL,
    var rewardedAdUnitId: String = DEMO_REWARDED,
    var rewardedInterstitialAdUnitId: String = DEMO_REWARDED_INTERSTITIAL,
    var nativeAdUnitId: String = DEMO_NATIVE,
    var appOpenAdUnitId: String = DEMO_APP_OPEN,
    var customTargeting: Map<String, String> = mapOf("category" to "browser", "tier" to "free"),
    var interstitialInterval: Int = 3,
    var bannerPosition: String = "BOTTOM",
    var safelinkGamAdsEnabled: Boolean = true,
    var enableAdInspector: Boolean = true,
    var updatedAt: Long = System.currentTimeMillis()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "adsEnabled" to adsEnabled,
            "testMode" to testMode,
            "networkCode" to networkCode,
            "bannerAdUnitId" to bannerAdUnitId,
            "fixedBannerAdUnitId" to fixedBannerAdUnitId,
            "interstitialAdUnitId" to interstitialAdUnitId,
            "rewardedAdUnitId" to rewardedAdUnitId,
            "rewardedInterstitialAdUnitId" to rewardedInterstitialAdUnitId,
            "nativeAdUnitId" to nativeAdUnitId,
            "appOpenAdUnitId" to appOpenAdUnitId,
            "customTargeting" to customTargeting,
            "interstitialInterval" to interstitialInterval,
            "bannerPosition" to bannerPosition,
            "safelinkGamAdsEnabled" to safelinkGamAdsEnabled,
            "enableAdInspector" to enableAdInspector,
            "updatedAt" to System.currentTimeMillis()
        )
    }

    companion object {
        const val DEMO_ADAPTIVE_BANNER = "/21775744923/example/adaptive-banner"
        const val DEMO_FIXED_BANNER = "/21775744923/example/fixed-size-banner"
        const val DEMO_INTERSTITIAL = "/21775744923/example/interstitial"
        const val DEMO_INTERSTITIAL_ALT = "/6499/example/interstitial"
        const val DEMO_REWARDED = "/21775744923/example/rewarded"
        const val DEMO_REWARDED_FALLBACK = "ca-app-pub-3940256099942544/5224354917"
        const val DEMO_REWARDED_INTERSTITIAL = "/21775744923/example/rewarded-interstitial"
        const val DEMO_NATIVE = "/21775744923/example/native"
        const val DEMO_APP_OPEN = "/21775744923/example/app-open"

        fun fromMap(map: Map<String, Any?>): GoogleAdManagerConfig {
            val targetingRaw = map["customTargeting"] as? Map<*, *>
            val targeting = mutableMapOf<String, String>()
            targetingRaw?.forEach { (k, v) ->
                if (k is String && v != null) {
                    targeting[k] = v.toString()
                }
            }

            return GoogleAdManagerConfig(
                adsEnabled = (map["adsEnabled"] as? Boolean) ?: true,
                testMode = (map["testMode"] as? Boolean) ?: true,
                networkCode = (map["networkCode"] as? String) ?: "21775744923",
                bannerAdUnitId = (map["bannerAdUnitId"] as? String) ?: DEMO_ADAPTIVE_BANNER,
                fixedBannerAdUnitId = (map["fixedBannerAdUnitId"] as? String) ?: DEMO_FIXED_BANNER,
                interstitialAdUnitId = (map["interstitialAdUnitId"] as? String) ?: DEMO_INTERSTITIAL,
                rewardedAdUnitId = (map["rewardedAdUnitId"] as? String) ?: DEMO_REWARDED,
                rewardedInterstitialAdUnitId = (map["rewardedInterstitialAdUnitId"] as? String) ?: DEMO_REWARDED_INTERSTITIAL,
                nativeAdUnitId = (map["nativeAdUnitId"] as? String) ?: DEMO_NATIVE,
                appOpenAdUnitId = (map["appOpenAdUnitId"] as? String) ?: DEMO_APP_OPEN,
                customTargeting = if (targeting.isNotEmpty()) targeting else mapOf("category" to "browser", "tier" to "free"),
                interstitialInterval = ((map["interstitialInterval"] as? Number)?.toInt()) ?: 3,
                bannerPosition = (map["bannerPosition"] as? String) ?: "BOTTOM",
                safelinkGamAdsEnabled = (map["safelinkGamAdsEnabled"] as? Boolean) ?: true,
                enableAdInspector = (map["enableAdInspector"] as? Boolean) ?: true,
                updatedAt = (map["updatedAt"] as? Long) ?: System.currentTimeMillis()
            )
        }
    }
}
