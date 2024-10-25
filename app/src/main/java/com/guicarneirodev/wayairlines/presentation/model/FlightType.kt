package com.guicarneirodev.wayairlines.presentation.model

import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.domain.model.ResponseData
import kotlinx.coroutines.flow.Flow

sealed class FlightType(
    val getFlights: FlightRepository.() -> Flow<ResponseData<List<Flight>>>
) {
    data object Completed: FlightType(
        getFlights = { getCompletedFlights() }
    )

    data object Cancelled: FlightType(
        getFlights = { getCancelledFlights() }
    )

    data object Future: FlightType(
        getFlights = { getFutureFlights() }
    )
}