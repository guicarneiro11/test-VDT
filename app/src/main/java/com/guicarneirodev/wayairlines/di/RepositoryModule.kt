package com.guicarneirodev.wayairlines.di

import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { FlightRepository(androidContext()) }
}