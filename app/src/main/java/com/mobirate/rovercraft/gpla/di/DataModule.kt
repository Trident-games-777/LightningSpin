package com.mobirate.rovercraft.gpla.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mobirate.rovercraft.gpla.UrlSettings
import com.mobirate.rovercraft.gpla.data.SettingsSerializer
import com.mobirate.rovercraft.gpla.data.repo.ZeusRepo
import com.mobirate.rovercraft.gpla.data.repo.ZeusRepoImpl
import com.mobirate.rovercraft.gpla.ui.loading.LoadingViewModel
import com.mobirate.rovercraft.gpla.ui.web.WebViewModel
import com.mobirate.rovercraft.gpla.utils.Security
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.urlSettings: DataStore<UrlSettings> by dataStore(
    fileName = "url_settings.proto",
    serializer = SettingsSerializer
)

val dataModule = module {
    single<ZeusRepo> {
        ZeusRepoImpl(androidContext().urlSettings)
    }

    factory { Security(get()) }

    viewModel {
        LoadingViewModel(
            app = get(),
            repo = get(),
            oneSignalScope = CoroutineScope(
                SupervisorJob() + Dispatchers.Default
            )
        )
    }

    viewModel { WebViewModel(get()) }
}