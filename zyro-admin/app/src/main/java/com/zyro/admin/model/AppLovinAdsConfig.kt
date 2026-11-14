package com.zyro.admin.model

data class AppLovinAdsConfig(
    var enabled: Boolean = true,
    var testMode: Boolean = true,
    var sdkKey: String = "YOUR_APPLOVIN_SDK_KEY",
    var bannerAdUnitId: String = "YOUR_BANNER_AD_UNIT_ID",
    var mrecAdUnitId: String = "YOUR_MREC_AD_UNIT_ID",
    var interstitialAdUnitId: String = "YOUR_INTERSTITIAL_AD_UNIT_ID",
    var rewardedAdUnitId: String = "YOUR_REWARDED_AD_UNIT_ID",
    var nativeAdUnitId: String = "YOUR_NATIVE_AD_UNIT_ID",
    var appOpenAdUnitId: String = "YOUR_APP_OPEN_AD_UNIT_ID"
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "enabled" to enabled,
            "testMode" to testMode,
            "sdkKey" to sdkKey,
            "bannerAdUnitId" to bannerAdUnitId,
            "mrecAdUnitId" to mrecAdUnitId,
            "interstitialAdUnitId" to interstitialAdUnitId,
            "rewardedAdUnitId" to rewardedAdUnitId,
            "nativeAdUnitId" to nativeAdUnitId,
            "appOpenAdUnitId" to appOpenAdUnitId,
            "updatedAt" to System.currentTimeMillis()
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): AppLovinAdsConfig {
            return AppLovinAdsConfig(
                enabled = (map["enabled"] as? Boolean) ?: true,
                testMode = (map["testMode"] as? Boolean) ?: true,
                sdkKey = (map["sdkKey"] as? String) ?: "YOUR_APPLOVIN_SDK_KEY",
                bannerAdUnitId = (map["bannerAdUnitId"] as? String) ?: "YOUR_BANNER_AD_UNIT_ID",
                mrecAdUnitId = (map["mrecAdUnitId"] as? String) ?: "YOUR_MREC_AD_UNIT_ID",
                interstitialAdUnitId = (map["interstitialAdUnitId"] as? String) ?: "YOUR_INTERSTITIAL_AD_UNIT_ID",
                rewardedAdUnitId = (map["rewardedAdUnitId"] as? String) ?: "YOUR_REWARDED_AD_UNIT_ID",
                nativeAdUnitId = (map["nativeAdUnitId"] as? String) ?: "YOUR_NATIVE_AD_UNIT_ID",
                appOpenAdUnitId = (map["appOpenAdUnitId"] as? String) ?: "YOUR_APP_OPEN_AD_UNIT_ID"
            )
        }
    }
}
