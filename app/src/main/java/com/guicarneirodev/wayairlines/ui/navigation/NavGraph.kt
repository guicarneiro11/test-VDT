package com.guicarneirodev.wayairlines.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guicarneirodev.wayairlines.ui.flights.FlightsScreen
import com.guicarneirodev.wayairlines.ui.home.HomeScreen
import com.guicarneirodev.wayairlines.presentation.viewmodel.FlightsViewModel
import org.koin.androidx.compose.koinViewModel

object Route {
    const val HOME = "home"
    const val FLIGHTS = "flights"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.HOME) {
        composable(Route.HOME) { HomeScreen(navController) }
        composable(Route.FLIGHTS) { FlightsScreen(viewModel = koinViewModel<FlightsViewModel>())  }
    }
}