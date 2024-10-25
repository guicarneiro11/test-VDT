package com.guicarneirodev.wayairlines.di

import com.guicarneirodev.wayairlines.presentation.viewmodel.FlightsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FlightsViewModel(get()) }
}