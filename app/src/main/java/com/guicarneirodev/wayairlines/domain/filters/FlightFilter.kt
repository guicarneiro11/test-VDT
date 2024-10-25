package com.guicarneirodev.wayairlines.domain.filters

import com.guicarneirodev.wayairlines.domain.model.Flight

sealed class FlightFilter(
    val predicate: (Flight) -> Boolean,
    val logMessage: String,
    val errorMessage: String
) {
    data object Completed: FlightFilter(
        predicate = { it.status == "CONCLUIDO" },
        logMessage = "completed",
        errorMessage = "voos concluídos"
    )

    data object Cancelled: FlightFilter(
        predicate = { it.status == "CANCELADO" },
        logMessage = "cancelled",
        errorMessage = "voos cancelados"
    )

    data object Future: FlightFilter(
        predicate = { it.status != "CONCLUIDO" && it.status != "CANCELADO" },
        logMessage = "future",
        errorMessage = "voos futuros"
    )
}