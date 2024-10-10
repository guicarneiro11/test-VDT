package com.guicarneirodev.wayairlines

import android.app.Application
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.viewmodel.FlightsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FlightRepository(androidContext()) }
    viewModel { FlightsViewModel(get()) }
}

class WayAirlinesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WayAirlinesApplication)
            modules(appModule)
        }
    }
}