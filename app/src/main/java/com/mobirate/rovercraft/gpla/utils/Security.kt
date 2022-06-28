package com.mobirate.rovercraft.gpla.utils

import android.content.Context
import android.provider.Settings
import java.io.File

class Security(private val ctx: Context) {
    val notSecure = RootSecurity.enabled || AdbSecurity().enabled
    val secure = !RootSecurity.enabled && !AdbSecurity().enabled

    private object RootSecurity {
        val enabled: Boolean
            get() {
                val dirsArray: List<String> = listOf(
                    "/sbin/",
                    "/system/bin/",
                    "/system/xbin/",
                    "/data/local/xbin/",
                    "/data/local/bin/",
                    "/system/sd/xbin/",
                    "/system/bin/failsafe/",
                    "/data/local/"
                )
                try {
                    for (dir in dirsArray) {
                        if (File(dir + "su").exists()) return true
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
                return false
            }
    }

    private inner class AdbSecurity {
        val enabled = Settings.Global.getString(
            ctx.contentResolver,
            Settings.Global.ADB_ENABLED
        ) == "1"
    }
}