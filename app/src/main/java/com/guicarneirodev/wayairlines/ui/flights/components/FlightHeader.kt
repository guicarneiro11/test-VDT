package com.guicarneirodev.wayairlines.ui.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.ui.theme.PrimaryBlue

@Composable
fun FlightHeader(flight: Flight) {
    var showCompletionStatus by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Voo ${flight.flight_id} - ${flight.airplane_name}",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold
        )
        StatusChip(
            status = if (showCompletionStatus) flight.completion_status else flight.status,
            onClick = { showCompletionStatus = !showCompletionStatus }
        )
    }
}