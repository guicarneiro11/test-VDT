package com.guicarneirodev.wayairlines

import android.app.Application
import com.guicarneirodev.wayairlines.di.repositoryModule
import com.guicarneirodev.wayairlines.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WayAirlinesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WayAirlinesApplication)
            modules(repositoryModule, viewModelModule)
        }
    }
}