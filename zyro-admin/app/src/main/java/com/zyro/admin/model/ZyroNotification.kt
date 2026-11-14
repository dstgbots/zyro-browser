package com.zyro.admin.model

data class ZyroNotification(
    var id: String = "",
    var title: String = "",
    var body: String = "",
    var imageUrl: String = "",
    var targetUrl: String = "",
    var topic: String = "all_users",
    var priority: String = "HIGH",
    var timestamp: Long = System.currentTimeMillis(),
    var status: String = "SENT"
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "body" to body,
            "imageUrl" to imageUrl,
            "targetUrl" to targetUrl,
            "topic" to topic,
            "priority" to priority,
            "timestamp" to timestamp,
            "status" to status
        )
    }

    companion object {
        fun fromMap(id: String, map: Map<String, Any?>): ZyroNotification {
            return ZyroNotification(
                id = id,
                title = (map["title"] as? String) ?: "",
                body = (map["body"] as? String) ?: "",
                imageUrl = (map["imageUrl"] as? String) ?: "",
                targetUrl = (map["targetUrl"] as? String) ?: "",
                topic = (map["topic"] as? String) ?: "all_users",
                priority = (map["priority"] as? String) ?: "HIGH",
                timestamp = (map["timestamp"] as? Long) ?: System.currentTimeMillis(),
                status = (map["status"] as? String) ?: "SENT"
            )
        }
    }
}
