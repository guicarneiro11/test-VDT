package com.guicarneirodev.wayairlines.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.domain.model.ResponseData
import com.guicarneirodev.wayairlines.presentation.model.FlightType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightsViewModel(private val repository: FlightRepository) : ViewModel() {
    data class FlightsUiState(
        val allFlights: List<Flight> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(FlightsUiState())
    val uiState: StateFlow<FlightsUiState> = _uiState.asStateFlow()

    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
            repository.getFlights().collect { response ->
                when (response) {
                    is ResponseData.Success -> {
                        _uiState.update {
                            it.copy(
                                allFlights = response.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is ResponseData.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = response.message
                            )
                        }
                    }
                    ResponseData.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true, error = null)
                        }
                    }
                }
            }
        }
    }

    private fun createFilteredStateFlow(type: FlightType): StateFlow<List<Flight>> =
        type.getFlights(repository)
            .map { response ->
                when (response) {
                    is ResponseData.Success -> response.data
                    is ResponseData.Error -> {
                        _uiState.update { it.copy(error = response.message) }
                        emptyList()
                    }
                    ResponseData.Loading -> emptyList()
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList()
            )

    val completedFlights = createFilteredStateFlow(FlightType.Completed)
    val cancelledFlights = createFilteredStateFlow(FlightType.Cancelled)
    val futureFlights = createFilteredStateFlow(FlightType.Future)
}