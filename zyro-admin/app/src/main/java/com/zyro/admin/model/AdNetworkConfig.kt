package com.zyro.admin.model

data class AdNetworkConfig(
    var activePlatform: String = PLATFORM_GOOGLE,
    var globalAdsEnabled: Boolean = true,
    var updatedAt: Long = System.currentTimeMillis()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "activePlatform" to activePlatform,
            "globalAdsEnabled" to globalAdsEnabled,
            "updatedAt" to System.currentTimeMillis()
        )
    }

    companion object {
        const val PLATFORM_GOOGLE = "GOOGLE_ADMOB"
        const val PLATFORM_META = "META_AUDIENCE_NETWORK"
        const val PLATFORM_APPLOVIN = "APPLOVIN_MAX"
        const val PLATFORM_GAM = "GOOGLE_AD_MANAGER"

        fun fromMap(map: Map<String, Any?>): AdNetworkConfig {
            val activePlatform = (map["activePlatform"] as? String) ?: PLATFORM_GOOGLE
            val globalAdsEnabled = (map["globalAdsEnabled"] as? Boolean) ?: true
            val updatedAt = (map["updatedAt"] as? Long) ?: System.currentTimeMillis()
            return AdNetworkConfig(activePlatform, globalAdsEnabled, updatedAt)
        }
    }
}
