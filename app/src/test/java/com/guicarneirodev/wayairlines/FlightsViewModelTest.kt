package com.guicarneirodev.wayairlines

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.guicarneirodev.wayairlines.data.model.Flight
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.viewmodel.FlightsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlightsViewModelTest {

    private lateinit var viewModel: FlightsViewModel
    private lateinit var repository: FlightRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()

        coEvery { repository.getFlights() } returns flowOf(emptyList())
        coEvery { repository.getCompletedFlights() } returns flowOf(emptyList())
        coEvery { repository.getCancelledFlights() } returns flowOf(emptyList())
        coEvery { repository.getFutureFlights() } returns flowOf(emptyList())

        viewModel = FlightsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFlights should update flights and isLoading states`() = runTest {
        val testFlights = listOf(
            Flight("AB123", "CONCLUIDO", "ATRASOU", "2024-08-01", "2024-08-01", "10:00", "14:00", "JFK", "LAX", "Boeing 737"),
            Flight("CD456", "CONCLUIDO", "NO_HORARIO", "2024-08-03", "2024-08-03", "15:30", "19:45", "ORD", "MIA", "Airbus A320")
        )

        coEvery { repository.getFlights() } returns flowOf(testFlights)

        viewModel = FlightsViewModel(repository)

        viewModel.loadFlights().join()

        assertThat(viewModel.isLoading.value).isFalse()

        assertThat(viewModel.flights.value).isEqualTo(testFlights)
    }

    @Test
    fun `completedFlights should return only completed flights`() = runTest {
        val completedFlights = listOf(
            Flight("AB123", "CONCLUIDO", "ATRASOU", "2024-08-01", "2024-08-01", "10:00", "14:00", "JFK", "LAX", "Boeing 737"),
            Flight("CD456", "CONCLUIDO", "NO_HORARIO", "2024-08-03", "2024-08-03", "15:30", "19:45", "ORD", "MIA", "Airbus A320")
        )

        coEvery { repository.getCompletedFlights() } returns flowOf(completedFlights)

        viewModel = FlightsViewModel(repository)

        viewModel.completedFlights.test {
            assertThat(awaitItem()).isEmpty()
            assertThat(awaitItem()).isEqualTo(completedFlights)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `cancelledFlights should return only cancelled flights`() = runTest {
        val cancelledFlights = listOf(
            Flight("EF789", "CANCELADO", "CANCELADO", "2024-08-05", "2024-08-05", "08:00", "11:30", "SFO", "SEA", "Boeing 747")
        )

        coEvery { repository.getCancelledFlights() } returns flowOf(cancelledFlights)

        viewModel = FlightsViewModel(repository)

        viewModel.cancelledFlights.test {
            assertThat(awaitItem()).isEmpty()
            assertThat(awaitItem()).isEqualTo(cancelledFlights)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `futureFlights should return an empty list`() = runTest {
        coEvery { repository.getFutureFlights() } returns flowOf(emptyList())

        viewModel.futureFlights.test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}