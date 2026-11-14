package com.zyro.admin.model

data class SpeedDialItem(
    var id: String = "",
    var title: String = "",
    var url: String = "",
    var iconUrl: String = "",
    var badgeText: String = "",
    var bgColor: String = "#FF6D00",
    var order: Int = 1,
    var enabled: Boolean = true
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "title" to title,
            "url" to url,
            "iconUrl" to iconUrl,
            "badgeText" to badgeText,
            "bgColor" to bgColor,
            "order" to order,
            "enabled" to enabled
        )
    }

    companion object {
        fun fromMap(id: String, map: Map<String, Any?>): SpeedDialItem {
            return SpeedDialItem(
                id = id,
                title = (map["title"] as? String) ?: "",
                url = (map["url"] as? String) ?: "",
                iconUrl = (map["iconUrl"] as? String) ?: "",
                badgeText = (map["badgeText"] as? String) ?: "",
                bgColor = (map["bgColor"] as? String) ?: "#FF6D00",
                order = ((map["order"] as? Number)?.toInt()) ?: 1,
                enabled = (map["enabled"] as? Boolean) ?: true
            )
        }

        fun getDefaultItems(): List<SpeedDialItem> {
            return listOf(
                SpeedDialItem("google", "Google", "https://www.google.com", "", "G", "#4285F4", 1, true),
                SpeedDialItem("youtube", "YouTube", "https://www.youtube.com", "", "YT", "#FF0000", 2, true),
                SpeedDialItem("reddit", "Reddit", "https://www.reddit.com", "", "RD", "#FF4500", 3, true),
                SpeedDialItem("wikipedia", "Wikipedia", "https://www.wikipedia.org", "", "W", "#4F46E5", 4, true),
                SpeedDialItem("github", "GitHub", "https://www.github.com", "", "GH", "#181717", 5, true),
                SpeedDialItem("x_twitter", "X", "https://twitter.com", "", "𝕏", "#000000", 6, true),
                SpeedDialItem("amazon", "Amazon", "https://www.amazon.com", "", "AZ", "#FF9900", 7, true),
                SpeedDialItem("chatgpt", "ChatGPT", "https://chatgpt.com", "", "AI", "#10A37F", 8, true)
            )
        }
    }
}
