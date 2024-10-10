package com.guicarneirodev.wayairlines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guicarneirodev.wayairlines.data.model.Flight
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class FlightsViewModel(private val repository: FlightRepository) : ViewModel() {
    private val _flights = MutableStateFlow<List<Flight>>(emptyList())
    open val flights: StateFlow<List<Flight>> = _flights

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadFlights()
    }

    fun loadFlights(): Job {
        return viewModelScope.launch {
            _isLoading.value = true
            repository.getFlights().collect { flights ->
                _flights.value = flights
                _isLoading.value = false
            }
        }
    }

    open val completedFlights: StateFlow<List<Flight>> = repository.getCompletedFlights().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    open val cancelledFlights: StateFlow<List<Flight>> = repository.getCancelledFlights().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    open val futureFlights: StateFlow<List<Flight>> = repository.getFutureFlights().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )
}