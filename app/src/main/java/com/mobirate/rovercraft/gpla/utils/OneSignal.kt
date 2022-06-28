package com.mobirate.rovercraft.gpla.utils

import android.content.Context
import com.onesignal.OneSignal

@Suppress("FunctionName")
fun OneSignal(
    ctx: Context, oneSignalId: String, adId: String,
    appsData: MutableMap<String, Any>?, deepLink: String
) {
    OneSignal.initWithContext(ctx)
    OneSignal.setAppId(oneSignalId)
    OneSignal.setExternalUserId(adId)
    val campaign = appsData?.get("campaign").toString()
    val key = "key2"

    when {
        campaign == "null" && deepLink == "null" -> {
            OneSignal.sendTag(key, "organic")
        }
        deepLink != "null" -> {
            OneSignal.sendTag(
                key,
                deepLink.replace("myapp://", "").substringBefore("/")
            )
        }
        campaign != "null" -> {
            OneSignal.sendTag(key, campaign.substringBefore("_"))
        }
    }
}