package com.guicarneirodev.wayairlines.ui.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.ui.theme.Gray

@Composable
fun FlightTimes(flight: Flight) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Horário de saída:",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )
            Text(
                text = flight.departure_time,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Text(
                text = "Horário de chegada:",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )
            Text(
                text = flight.arrival_time,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}