package com.baxtiyorov.security

data class RiskResult(
    val score: Int,
    val level: String,
    val flaggedPermissions: List<Pair<String, Int>>
)

object RiskAnalyzer {

    private val weights: Map<String, Int> = mapOf(
        "android.permission.SEND_SMS" to 20,
        "android.permission.RECEIVE_SMS" to 15,
        "android.permission.READ_SMS" to 15,
        "android.permission.CALL_PHONE" to 10,
        "android.permission.PROCESS_OUTGOING_CALLS" to 15,
        "android.permission.READ_CALL_LOG" to 12,
        "android.permission.WRITE_CALL_LOG" to 12,
        "android.permission.RECORD_AUDIO" to 15,
        "android.permission.CAMERA" to 10,
        "android.permission.ACCESS_FINE_LOCATION" to 10,
        "android.permission.ACCESS_BACKGROUND_LOCATION" to 15,
        "android.permission.READ_CONTACTS" to 10,
        "android.permission.WRITE_CONTACTS" to 8,
        "android.permission.SYSTEM_ALERT_WINDOW" to 18,
        "android.permission.BIND_ACCESSIBILITY_SERVICE" to 25,
        "android.permission.BIND_DEVICE_ADMIN" to 25,
        "android.permission.REQUEST_INSTALL_PACKAGES" to 20,
        "android.permission.PACKAGE_USAGE_STATS" to 15,
        "android.permission.WRITE_SECURE_SETTINGS" to 22,
        "android.permission.READ_PHONE_STATE" to 8,
        "android.permission.READ_EXTERNAL_STORAGE" to 6,
        "android.permission.WRITE_EXTERNAL_STORAGE" to 8,
        "android.permission.MANAGE_EXTERNAL_STORAGE" to 18,
        "android.permission.INSTALL_PACKAGES" to 20,
        "android.permission.DELETE_PACKAGES" to 18,
        "android.permission.GET_ACCOUNTS" to 6,
        "android.permission.AUTHENTICATE_ACCOUNTS" to 10,
        "android.permission.BLUETOOTH_ADMIN" to 5,
        "android.permission.CHANGE_WIFI_STATE" to 5,
        "android.permission.DISABLE_KEYGUARD" to 10
    )

    fun dangerousPermissionSet(): Set<String> = weights.keys

    fun analyze(permissions: List<String>, isFromUnknownSource: Boolean, isSystemApp: Boolean): RiskResult {
        val flagged = permissions
            .filter { weights.containsKey(it) }
            .map { it to (weights[it] ?: 0) }
            .sortedByDescending { it.second }

        var score = flagged.sumOf { it.second }

        val names = permissions.toSet()
        if (names.contains("android.permission.BIND_ACCESSIBILITY_SERVICE") &&
            names.contains("android.permission.SYSTEM_ALERT_WINDOW")
        ) score += 15

        if (names.contains("android.permission.SEND_SMS") &&
            names.contains("android.permission.RECEIVE_SMS")
        ) score += 10

        if (isFromUnknownSource) score += 10
        if (isSystemApp) score = (score * 0.3).toInt()

        score = score.coerceIn(0, 100)

        val level = when {
            score >= 55 -> "high"
            score >= 25 -> "medium"
            else -> "low"
        }

        return RiskResult(score, level, flagged)
    }
}
