package com.guicarneirodev.wayairlines

import app.cash.turbine.test
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.domain.model.ResponseData
import com.guicarneirodev.wayairlines.presentation.model.FlightType
import com.guicarneirodev.wayairlines.presentation.viewmodel.FlightsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlightsViewModelTest {
    private lateinit var viewModel: FlightsViewModel
    private lateinit var repository: FlightRepository
    private val testDispatcher = StandardTestDispatcher()

    private val testFlight = Flight(
        flight_id = "TEST123",
        status = "CONCLUIDO",
        completion_status = "NO_HORARIO",
        start_date = "2024-08-01",
        end_date = "2024-08-01",
        departure_time = "10:00",
        arrival_time = "11:00",
        departure_airport = "TST",
        arrival_airport = "TST2",
        airplane_name = "Test Plane"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `completedFlights should emit correct data`() = runTest {
        // Given
        val completedFlight = testFlight.copy(status = "CONCLUIDO")

        coEvery { repository.getCompletedFlights() } returns flowOf(
            ResponseData.Success(listOf(completedFlight))
        )

        // When
        viewModel = FlightsViewModel(repository)

        // Then
        viewModel.completedFlights.test {
            assertEquals(emptyList<Flight>(), awaitItem()) // Estado inicial vazio
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(completedFlight), awaitItem()) // Estado após carregamento
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { repository.getCompletedFlights() }
    }

    @Test
    fun `cancelledFlights should emit correct data`() = runTest {
        // Given
        val cancelledFlight = testFlight.copy(status = "CANCELADO")

        coEvery { repository.getCancelledFlights() } returns flowOf(
            ResponseData.Success(listOf(cancelledFlight))
        )

        // When
        viewModel = FlightsViewModel(repository)

        // Then
        viewModel.cancelledFlights.test {
            assertEquals(emptyList<Flight>(), awaitItem()) // Estado inicial vazio
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(cancelledFlight), awaitItem()) // Estado após carregamento
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { repository.getCancelledFlights() }
    }

    @Test
    fun `futureFlights should emit correct data`() = runTest {
        // Given
        val futureFlight = testFlight.copy(status = "AGENDADO")

        coEvery { repository.getFutureFlights() } returns flowOf(
            ResponseData.Success(listOf(futureFlight))
        )

        // When
        viewModel = FlightsViewModel(repository)

        // Then
        viewModel.futureFlights.test {
            assertEquals(emptyList<Flight>(), awaitItem()) // Estado inicial vazio
            testScheduler.advanceUntilIdle()
            assertEquals(listOf(futureFlight), awaitItem()) // Estado após carregamento
            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { repository.getFutureFlights() }
    }
}