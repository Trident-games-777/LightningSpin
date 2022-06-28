package com.mobirate.rovercraft.gpla.ui.loading

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.mobirate.rovercraft.gpla.data.repo.ZeusRepo
import com.mobirate.rovercraft.gpla.utils.Link
import com.mobirate.rovercraft.gpla.utils.OneSignal
import com.mobirate.rovercraft.gpla.utils.ZeusConversionListener
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class LoadingViewModel(
    private val app: Application,
    private val repo: ZeusRepo,
    private val oneSignalScope: CoroutineScope
) : ViewModel() {

    private val _url: MutableStateFlow<String> = MutableStateFlow("")
    val url: StateFlow<String> = _url

    fun start() = runBlocking {
        if (repo.getIsSaved().first()) {
            _url.emit(repo.getUrl().first())
        } else {
            AppsFlyerLib.getInstance().init(
                repo.getAppsID().first(),
                ZeusConversionListener { appsData ->
                    AppLinkData.fetchDeferredAppLinkData(app) { appLinkData ->
                        processAppData(
                            appsData = appsData,
                            deepLink = appLinkData?.targetUri.toString()
                        )
                    }
                },
                app
            )
            AppsFlyerLib.getInstance().start(app)
        }
    }

    private fun processAppData(appsData: MutableMap<String, Any>?, deepLink: String) =
        viewModelScope.launch(Dispatchers.Default) {
            @Suppress("BlockingMethodInNonBlockingContext") val gadid =
                AdvertisingIdClient.getAdvertisingIdInfo(app).id.toString()
            val newUrl = Link(
                res = app.resources,
                baseUrl = repo.getUrl().first(),
                gadid = gadid,
                apps = appsData,
                deep = deepLink,
                uid = AppsFlyerLib.getInstance().getAppsFlyerUID(app)
            )
            oneSignalScope.launch {
                OneSignal(
                    ctx = app,
                    oneSignalId = repo.getOneSignalId().first(),
                    adId = gadid,
                    appsData = appsData,
                    deepLink = deepLink
                )
            }
            _url.emit(newUrl)
        }

    fun isSaved() = repo.getIsSaved()
    fun getUrl() = repo.getUrl()
}