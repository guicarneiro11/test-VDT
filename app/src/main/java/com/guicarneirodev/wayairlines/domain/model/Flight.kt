package com.guicarneirodev.wayairlines.domain.model

data class Flight(
    val flight_id: String,
    val status: String,
    val completion_status: String,
    val start_date: String,
    val end_date: String,
    val departure_time: String,
    val arrival_time: String,
    val departure_airport: String,
    val arrival_airport: String,
    val airplane_name: String
)

data class FlightsData(
    val flights: List<Flight>
)