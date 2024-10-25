package com.guicarneirodev.wayairlines.ui.flights

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.ui.flights.components.FlightDates
import com.guicarneirodev.wayairlines.ui.flights.components.FlightHeader
import com.guicarneirodev.wayairlines.ui.flights.components.FlightRoute
import com.guicarneirodev.wayairlines.ui.flights.components.FlightTimes

@Composable
fun FlightItems(flight: Flight) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            FlightHeader(flight)
            Spacer(modifier = Modifier.height(8.dp))
            FlightRoute(flight)
            Spacer(modifier = Modifier.height(8.dp))
            FlightTimes(flight)
            Spacer(modifier = Modifier.height(4.dp))
            FlightDates(flight)
        }
    }
}