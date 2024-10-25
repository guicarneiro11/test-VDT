package com.guicarneirodev.wayairlines.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.guicarneirodev.wayairlines.domain.filters.FlightFilter
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.domain.model.FlightsData
import com.guicarneirodev.wayairlines.domain.model.ResponseData
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

    fun getFlights(): Flow<ResponseData<List<Flight>>> = flow {
        emit(ResponseData.Loading)

        try {
            withContext(Dispatchers.IO) {
                kotlinx.coroutines.delay(1000)
            }

            val flightsList = flights
            if (flightsList.isEmpty()) {
                emit(ResponseData.Error(message = "Não foi possível carregar os voos"))
            } else {
                Log.d(TAG, "Emitting ${flightsList.size} flights")
                emit(ResponseData.Success(flightsList))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading flights", e)
            emit(ResponseData.Error(message = "Erro ao carregar voos: ${e.message}"))
        }
    }

    private fun getFilteredFlights(filter: FlightFilter): Flow<ResponseData<List<Flight>>> = flow {
        emit(ResponseData.Loading)

        try {
            val filteredFlights = flights.filter(filter.predicate)
            Log.d(TAG, "Emitting ${filter.logMessage}: ${filteredFlights.size} flights")
            emit(ResponseData.Success(filteredFlights))
        } catch (e: Exception) {
            Log.e(TAG, "Error filtering flights", e)
            emit(ResponseData.Error(message = "Erro ao filtrar ${filter.errorMessage}: ${e.message}"))
        }
    }

    fun getCompletedFlights(): Flow<ResponseData<List<Flight>>> =
        getFilteredFlights(FlightFilter.Completed)

    fun getCancelledFlights(): Flow<ResponseData<List<Flight>>> =
        getFilteredFlights(FlightFilter.Cancelled)

    fun getFutureFlights(): Flow<ResponseData<List<Flight>>> =
        getFilteredFlights(FlightFilter.Future)
}