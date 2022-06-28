package com.mobirate.rovercraft.gpla.utils

import com.appsflyer.AppsFlyerConversionListener

class ZeusConversionListener(
    private val callback: (MutableMap<String, Any>?) -> Unit
) : AppsFlyerConversionListener {
    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
        callback(data)
    }

    override fun onConversionDataFail(error: String?) {}
    override fun onAppOpenAttribution(attr: MutableMap<String, String>?) {}
    override fun onAttributionFailure(error: String?) {}
}