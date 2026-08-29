package com.baxtiyorov.security

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import java.io.InputStream
import java.security.MessageDigest

data class AppEntry(
    val label: String,
    val packageName: String,
    val versionName: String?,
    val permissions: List<String>,
    val isSystemApp: Boolean,
    val installerPackage: String?
)

object ApkUtils {

    fun listInstalledApps(context: Context): List<AppEntry> {
        val pm = context.packageManager
        val flags = PackageManager.GET_PERMISSIONS
        val packages: List<PackageInfo> = pm.getInstalledPackages(flags)

        return packages.map { pkg ->
            val appInfo = pkg.applicationInfo
            val isSystem = appInfo != null &&
                (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
            val installer = try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    pm.getInstallSourceInfo(pkg.packageName).installingPackageName
                } else {
                    @Suppress("DEPRECATION")
                    pm.getInstallerPackageName(pkg.packageName)
                }
            } catch (e: Exception) {
                null
            }
            AppEntry(
                label = appInfo?.let { pm.getApplicationLabel(it).toString() } ?: pkg.packageName,
                packageName = pkg.packageName,
                versionName = pkg.versionName,
                permissions = pkg.requestedPermissions?.toList() ?: emptyList(),
                isSystemApp = isSystem,
                installerPackage = installer
            )
        }.sortedBy { it.label.lowercase() }
    }

    fun analyzeApkFile(context: Context, uri: Uri): AppEntry? {
        val pm = context.packageManager
        val tempFile = java.io.File(context.cacheDir, "temp_scan.apk")
        context.contentResolver.openInputStream(uri)?.use { input ->
            tempFile.outputStream().use { output -> input.copyTo(output) }
        } ?: return null

        val pkg = pm.getPackageArchiveInfo(
            tempFile.absolutePath,
            PackageManager.GET_PERMISSIONS
        ) ?: return null

        pkg.applicationInfo?.sourceDir = tempFile.absolutePath
        pkg.applicationInfo?.publicSourceDir = tempFile.absolutePath

        val label = pkg.applicationInfo?.let { pm.getApplicationLabel(it).toString() } ?: pkg.packageName

        return AppEntry(
            label = label,
            packageName = pkg.packageName,
            versionName = pkg.versionName,
            permissions = pkg.requestedPermissions?.toList() ?: emptyList(),
            isSystemApp = false,
            installerPackage = "unknown_source_file"
        )
    }

    fun sha256OfUri(context: Context, uri: Uri): String {
        val digest = MessageDigest.getInstance("SHA-256")
        context.contentResolver.openInputStream(uri)?.use { input: InputStream ->
            val buffer = ByteArray(8192)
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
