package com.guicarneirodev.wayairlines.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.guicarneirodev.wayairlines.data.model.Flight
import com.guicarneirodev.wayairlines.data.model.FlightsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FlightRepository(private val context: Context) {
    private val TAG = "FlightRepository"

    private val flights: List<Flight> by lazy {
        loadFlightsFromJson()
    }

    private fun loadFlightsFromJson(): List<Flight> {
        return try {
            context.assets.open("massa-de-teste.json").use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val flightsList = Gson().fromJson(jsonString, FlightsData::class.java).flights

                if (flightsList.isNotEmpty()) {
                    Log.d(TAG, "Example flight: ${flightsList[0]}")
                }

                flightsList
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading flights from JSON", e)
            emptyList()
        }
    }

    fun getFlights(): Flow<List<Flight>> = flow {
        withContext(Dispatchers.IO) {
            kotlinx.coroutines.delay(1000)
        }
        Log.d(TAG, "Emitting ${flights.size} flights")
        emit(flights)
    }

    fun getCompletedFlights(): Flow<List<Flight>> = flow {
        val completed = flights.filter { it.status == "CONCLUIDO" }
        Log.d(TAG, "Emitting ${completed.size} completed flights")
        emit(completed)
    }

    fun getCancelledFlights(): Flow<List<Flight>> = flow {
        val cancelled = flights.filter { it.status == "CANCELADO" }
        Log.d(TAG, "Emitting ${cancelled.size} cancelled flights")
        emit(cancelled)
    }

    fun getFutureFlights(): Flow<List<Flight>> = flow {
        val future = flights.filter { it.status != "CONCLUIDO" && it.status != "CANCELADO" }
        Log.d(TAG, "Emitting ${future.size} future flights")
        emit(future)
    }
}