package com.guicarneirodev.wayairlines.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guicarneirodev.wayairlines.ui.flights.FlightsScreen
import com.guicarneirodev.wayairlines.ui.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("flights") { FlightsScreen(viewModel = koinViewModel()) }
    }
}