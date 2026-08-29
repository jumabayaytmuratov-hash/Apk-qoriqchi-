package com.baxtiyorov.security

enum class AppLang { KAA, UZ, RU }

object Lang {
    var current: AppLang = AppLang.KAA

    private val strings = mapOf(
        "app_name" to mapOf(
            AppLang.KAA to "APK Qo'riqchi",
            AppLang.UZ to "APK Qo'riqchi",
            AppLang.RU to "APK Qo'riqchi"
        ),
        "installed_apps" to mapOf(
            AppLang.KAA to "Ornatılg'an dasturlar",
            AppLang.UZ to "O'rnatilgan dasturlar",
            AppLang.RU to "Установленные приложения"
        ),
        "scan_apk_file" to mapOf(
            AppLang.KAA to "APK fayldı tekseriw",
            AppLang.UZ to "APK faylni tekshirish",
            AppLang.RU to "Проверить APK-файл"
        ),
        "scanning" to mapOf(
            AppLang.KAA to "Tekserilmekte...",
            AppLang.UZ to "Tekshirilmoqda...",
            AppLang.RU to "Проверка..."
        ),
        "risk_low" to mapOf(
            AppLang.KAA to "Qawips passak",
            AppLang.UZ to "Xavf past",
            AppLang.RU to "Низкий риск"
        ),
        "risk_medium" to mapOf(
            AppLang.KAA to "Qawips orta",
            AppLang.UZ to "Xavf o'rtacha",
            AppLang.RU to "Средний риск"
        ),
        "risk_high" to mapOf(
            AppLang.KAA to "Qawips joqarı",
            AppLang.UZ to "Xavf yuqori",
            AppLang.RU to "Высокий риск"
        ),
        "permissions" to mapOf(
            AppLang.KAA to "Ruxsatlar",
            AppLang.UZ to "Ruxsatlar",
            AppLang.RU to "Разрешения"
        ),
        "no_dangerous_permissions" to mapOf(
            AppLang.KAA to "Qawipli ruxsatlar tabılmadı",
            AppLang.UZ to "Xavfli ruxsatlar topilmadi",
            AppLang.RU to "Опасных разрешений не найдено"
        ),
        "sha256_hash" to mapOf(
            AppLang.KAA to "SHA-256 xesh",
            AppLang.UZ to "SHA-256 xesh",
            AppLang.RU to "SHA-256 хеш"
        ),
        "package_name" to mapOf(
            AppLang.KAA to "Paket atı",
            AppLang.UZ to "Paket nomi",
            AppLang.RU to "Имя пакета"
        ),
        "version" to mapOf(
            AppLang.KAA to "Versiya",
            AppLang.UZ to "Versiya",
            AppLang.RU to "Версия"
        ),
        "risk_score" to mapOf(
            AppLang.KAA to "Qawip ballı",
            AppLang.UZ to "Xavf balli",
            AppLang.RU to "Оценка риска"
        ),
        "system_app" to mapOf(
            AppLang.KAA to "Sistema dasturı",
            AppLang.UZ to "Tizim dasturi",
            AppLang.RU to "Системное приложение"
        ),
        "select_apk" to mapOf(
            AppLang.KAA to "APK faylın tańlań",
            AppLang.UZ to "APK faylni tanlang",
            AppLang.RU to "Выберите APK-файл"
        ),
        "analysis_failed" to mapOf(
            AppLang.KAA to "Tallaw sátsiz boldı",
            AppLang.UZ to "Tahlil muvaffaqiyatsiz",
            AppLang.RU to "Ошибка анализа"
        ),
        "installer" to mapOf(
            AppLang.KAA to "Ornatıwshı",
            AppLang.UZ to "O'rnatuvchi",
            AppLang.RU to "Установщик"
        ),
        "unknown_source" to mapOf(
            AppLang.KAA to "Belgisiz derek",
            AppLang.UZ to "Noma'lum manba",
            AppLang.RU to "Неизвестный источник"
        )
    )

    fun t(key: String): String = strings[key]?.get(current) ?: key
}
