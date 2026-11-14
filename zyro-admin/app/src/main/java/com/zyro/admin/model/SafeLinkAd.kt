package com.zyro.admin.model

data class SafeLinkAd(
    var stepId: String = "step_1",
    var stepName: String = "Security Verification",
    var stepSubtitle: String = "Analyzing threat vectors & integrity",
    var order: Int = 1,
    var adType: String = "BANNER",
    var badge: String = "SPONSORED AD",
    var title: String = "",
    var description: String = "",
    var ctaText: String = "",
    var ctaUrl: String = "",
    var durationSeconds: Int = 3
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "stepName" to stepName,
            "stepSubtitle" to stepSubtitle,
            "order" to order,
            "adType" to adType,
            "badge" to badge,
            "title" to title,
            "description" to description,
            "ctaText" to ctaText,
            "ctaUrl" to ctaUrl,
            "durationSeconds" to durationSeconds
        )
    }

    companion object {
        fun fromMap(id: String, map: Map<String, Any?>): SafeLinkAd {
            val num = when (id) {
                "step_1" -> 1
                "step_2" -> 2
                "step_3" -> 3
                "step_4" -> 4
                else -> 1
            }
            val defaultName = when (num) {
                1 -> "Domain Verification"
                2 -> "SSL Certificate Check"
                3 -> "Safe Browsing Check"
                else -> "Redirection Check"
            }
            val defaultSubtitle = when (num) {
                1 -> "Checking URL reputation & safety..."
                2 -> "Validating SSL certificate & cipher suites..."
                3 -> "Querying Google Safe Browsing & threat feeds..."
                else -> "Resolving redirect chain & final destination..."
            }
            val defaultAdType = when (num) {
                1, 2 -> "BANNER"
                3 -> "INTERSTITIAL"
                else -> "REWARDED"
            }

            return SafeLinkAd(
                stepId = id,
                stepName = (map["stepName"] as? String) ?: defaultName,
                stepSubtitle = (map["stepSubtitle"] as? String) ?: defaultSubtitle,
                order = ((map["order"] as? Long)?.toInt()) ?: num,
                adType = (map["adType"] as? String) ?: defaultAdType,
                badge = (map["badge"] as? String) ?: "SPONSORED AD",
                title = (map["title"] as? String) ?: "",
                description = (map["description"] as? String) ?: "",
                ctaText = (map["ctaText"] as? String) ?: "",
                ctaUrl = (map["ctaUrl"] as? String) ?: "",
                durationSeconds = ((map["durationSeconds"] as? Long)?.toInt()) ?: 3
            )
        }
    }
}
