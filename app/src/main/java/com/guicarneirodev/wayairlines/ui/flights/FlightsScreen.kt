package com.guicarneirodev.wayairlines.ui.flights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guicarneirodev.wayairlines.viewmodel.FlightsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlightsScreen(viewModel: FlightsViewModel = koinViewModel()) {
    FlightsScreenContent(viewModel)
}

@Composable
fun FlightsScreenContent(viewModel: FlightsViewModel) {
    val flights by viewModel.flights.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val completedFlights by viewModel.completedFlights.collectAsState()
    val cancelledFlights by viewModel.cancelledFlights.collectAsState()
    val futureFlights by viewModel.futureFlights.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D599C))
    ) {
        Text(
            text = "Histórico de Voos",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = Color(0xFF0D599C),
            contentColor = Color.White,
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        height = 3.dp,
                        color = Color.White
                    )
                }
            }
        ) {
            listOf("Todos", "Concluídos", "Cancelados", "Futuros").forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, maxLines = 1) }
                )
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val displayedFlights = when (selectedTab) {
                    1 -> completedFlights
                    2 -> cancelledFlights
                    3 -> futureFlights
                    else -> flights
                }
                items(displayedFlights) { flight ->
                    FlightItems(flight)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}