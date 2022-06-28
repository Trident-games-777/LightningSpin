package com.mobirate.rovercraft.gpla.app

import android.app.Application
import com.mobirate.rovercraft.gpla.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ZeusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ZeusApp)
            modules(dataModule)
        }
    }
}