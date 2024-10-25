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
fun FlightDates(flight: Flight) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Data de saída:",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )
            Text(
                text = flight.start_date,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Text(
                text = "Data de chegada:",
                style = MaterialTheme.typography.bodySmall,
                color = Gray
            )
            Text(
                text = flight.end_date,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}