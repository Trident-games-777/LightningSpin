package com.mobirate.rovercraft.gpla.utils

import android.content.res.Resources
import androidx.core.net.toUri
import com.mobirate.rovercraft.gpla.R
import java.util.*

@Suppress("FunctionName")
fun Link(
    res: Resources, baseUrl: String,
    gadid: String, apps: MutableMap<String, Any>?,
    deep: String, uid: String
): String {
    val prefix = "https://"
    val url = baseUrl.toUri().buildUpon().apply {
        appendQueryParameter(
            res.getString(R.string.secure_get_parametr),
            res.getString(R.string.secure_key)
        )
        appendQueryParameter(
            res.getString(R.string.dev_tmz_key),
            TimeZone.getDefault().id
        )
        appendQueryParameter(res.getString(R.string.gadid_key), gadid)
        appendQueryParameter(res.getString(R.string.deeplink_key), deep)
        appendQueryParameter(
            res.getString(R.string.source_key),
            apps?.get("media_source").toString()
        )
        appendQueryParameter(
            res.getString(R.string.af_id_key),
            uid
        )
        appendQueryParameter(
            res.getString(R.string.adset_id_key),
            apps?.get("adset_id").toString()
        )
        appendQueryParameter(
            res.getString(R.string.campaign_id_key),
            apps?.get("campaign_id").toString()
        )
        appendQueryParameter(
            res.getString(R.string.app_campaign_key),
            apps?.get("campaign").toString()
        )
        appendQueryParameter(
            res.getString(R.string.adset_key),
            apps?.get("adset").toString()
        )
        appendQueryParameter(
            res.getString(R.string.adgroup_key),
            apps?.get("adgroup").toString()
        )
        appendQueryParameter(
            res.getString(R.string.orig_cost_key),
            apps?.get("orig_cost").toString()
        )
        appendQueryParameter(
            res.getString(R.string.af_siteid_key),
            apps?.get("af_siteid").toString()
        )
    }.toString()
    return prefix + url
}