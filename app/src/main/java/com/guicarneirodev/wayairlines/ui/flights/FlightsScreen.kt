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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.ui.theme.PrimaryBlue
import com.guicarneirodev.wayairlines.ui.theme.White
import com.guicarneirodev.wayairlines.presentation.viewmodel.FlightsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlightsScreen(viewModel: FlightsViewModel = koinViewModel()) {
    FlightsScreenContent(viewModel)
}

@Composable
fun FlightsScreenContent(viewModel: FlightsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val completedFlights by viewModel.completedFlights.collectAsState()
    val cancelledFlights by viewModel.cancelledFlights.collectAsState()
    val futureFlights by viewModel.futureFlights.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
    ) {
        Text(
            text = "Histórico de Voos",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = PrimaryBlue,
            contentColor = White,
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        height = 3.dp,
                        color = White
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

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = White)
                }
            }
            uiState.error != null -> {
                ErrorMessage(uiState.error!!) {
                    viewModel.loadFlights()
                }
            }
            else -> {
                FlightsList(
                    allFlights = uiState.allFlights,
                    completedFlights = completedFlights,
                    cancelledFlights = cancelledFlights,
                    futureFlights = futureFlights,
                    selectedTab = selectedTab
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = White)
        ) {
            Text("Tentar Novamente", color = PrimaryBlue)
        }
    }
}

@Composable
fun FlightsList(
    allFlights: List<Flight>,
    completedFlights: List<Flight>,
    cancelledFlights: List<Flight>,
    futureFlights: List<Flight>,
    selectedTab: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val displayedFlights = when (selectedTab) {
            1 -> completedFlights
            2 -> cancelledFlights
            3 -> futureFlights
            else -> allFlights
        }
        items(displayedFlights) { flight ->
            FlightItems(flight)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}