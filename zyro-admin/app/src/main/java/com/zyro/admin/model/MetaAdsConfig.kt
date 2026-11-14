package com.zyro.admin.model

data class MetaAdsConfig(
    var enabled: Boolean = true,
    var testMode: Boolean = true,
    var appId: String = "123456789012345",
    var bannerPlacementId: String = "IMG_16_9_LINK#YOUR_PLACEMENT_ID",
    var bannerMrecPlacementId: String = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
    var interstitialPlacementId: String = "VID_HD_16_9_46S_LINK#YOUR_PLACEMENT_ID",
    var rewardedPlacementId: String = "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID",
    var nativePlacementId: String = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
    var nativeBannerPlacementId: String = "IMG_16_9_LINK#YOUR_PLACEMENT_ID",
    var playablePlacementId: String = "PLAYABLE#YOUR_PLACEMENT_ID"
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "enabled" to enabled,
            "testMode" to testMode,
            "appId" to appId,
            "bannerPlacementId" to bannerPlacementId,
            "bannerMrecPlacementId" to bannerMrecPlacementId,
            "interstitialPlacementId" to interstitialPlacementId,
            "rewardedPlacementId" to rewardedPlacementId,
            "nativePlacementId" to nativePlacementId,
            "nativeBannerPlacementId" to nativeBannerPlacementId,
            "playablePlacementId" to playablePlacementId,
            "updatedAt" to System.currentTimeMillis()
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): MetaAdsConfig {
            return MetaAdsConfig(
                enabled = (map["enabled"] as? Boolean) ?: true,
                testMode = (map["testMode"] as? Boolean) ?: true,
                appId = (map["appId"] as? String) ?: "123456789012345",
                bannerPlacementId = (map["bannerPlacementId"] as? String) ?: "IMG_16_9_LINK#YOUR_PLACEMENT_ID",
                bannerMrecPlacementId = (map["bannerMrecPlacementId"] as? String) ?: "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
                interstitialPlacementId = (map["interstitialPlacementId"] as? String) ?: "VID_HD_16_9_46S_LINK#YOUR_PLACEMENT_ID",
                rewardedPlacementId = (map["rewardedPlacementId"] as? String) ?: "VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID",
                nativePlacementId = (map["nativePlacementId"] as? String) ?: "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
                nativeBannerPlacementId = (map["nativeBannerPlacementId"] as? String) ?: "IMG_16_9_LINK#YOUR_PLACEMENT_ID",
                playablePlacementId = (map["playablePlacementId"] as? String) ?: "PLAYABLE#YOUR_PLACEMENT_ID"
            )
        }
    }
}
