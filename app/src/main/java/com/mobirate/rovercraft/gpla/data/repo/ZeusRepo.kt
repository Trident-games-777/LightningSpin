package com.mobirate.rovercraft.gpla.data.repo

import kotlinx.coroutines.flow.Flow

interface ZeusRepo {
    suspend fun setUrl(url: String)

    fun getIsSaved(): Flow<Boolean>
    fun getUrl(): Flow<String>
    fun getAppsID(): Flow<String>
    fun getOneSignalId(): Flow<String>
}