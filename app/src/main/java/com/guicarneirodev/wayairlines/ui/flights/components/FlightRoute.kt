package com.guicarneirodev.wayairlines.ui.flights.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.guicarneirodev.wayairlines.R
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.ui.theme.PrimaryBlue

@Composable
fun FlightRoute(flight: Flight) {
    var isExpandedDeparture by remember { mutableStateOf(false) }
    var isExpandedArrival by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .animateContentSize()
                .clickable { isExpandedDeparture = !isExpandedDeparture }
        ) {
            Text(
                text = flight.departure_airport,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = if (isExpandedDeparture) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.airplane),
            contentDescription = "airplane icon",
            tint = PrimaryBlue,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .animateContentSize()
                .clickable { isExpandedArrival = !isExpandedArrival },
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = flight.arrival_airport,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = if (isExpandedArrival) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}