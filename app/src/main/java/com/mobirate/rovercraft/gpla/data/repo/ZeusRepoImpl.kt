package com.mobirate.rovercraft.gpla.data.repo

import androidx.datastore.core.DataStore
import com.mobirate.rovercraft.gpla.UrlSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ZeusRepoImpl(
    private val store: DataStore<UrlSettings>
) : ZeusRepo {
    override fun getUrl(): Flow<String> {
        return store.data.map { urlSettings -> urlSettings.url }
    }

    override fun getAppsID(): Flow<String> {
        return store.data.map { urlSettings -> urlSettings.appsId }
    }

    override fun getOneSignalId(): Flow<String> {
        return store.data.map { urlSettings -> urlSettings.oneSignalId }
    }

    override fun getIsSaved(): Flow<Boolean> {
        return store.data.map { urlSettings -> urlSettings.isSaved }
    }

    override suspend fun setUrl(url: String) {
        if (!getIsSaved().first()) {
            store.updateData { urlSettings ->
                with(urlSettings.toBuilder()) {
                    this.url = url
                    this.isSaved = true
                    build()
                }
            }
        }
    }
}