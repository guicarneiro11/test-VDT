package com.guicarneirodev.wayairlines

import android.content.Context
import android.content.res.AssetManager
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import com.guicarneirodev.wayairlines.di.repositoryModule
import com.guicarneirodev.wayairlines.di.viewModelModule
import com.guicarneirodev.wayairlines.domain.filters.FlightFilter
import com.guicarneirodev.wayairlines.domain.model.Flight
import com.guicarneirodev.wayairlines.domain.model.ResponseData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.koin.test.KoinTest

@RunWith(RobolectricTestRunner::class)
class FlightRepositoryTest : KoinTest {
    private lateinit var repository: FlightRepository
    private lateinit var context: Context

    private val testFlights = listOf(
        Flight("AB123", "CONCLUIDO", "ATRASOU", "2024-08-01", "2024-08-01", "10:00", "14:00", "JFK", "LAX", "Boeing 737"),
        Flight("CD456", "CANCELADO", "CANCELADO", "2024-08-03", "2024-08-03", "15:30", "19:45", "ORD", "MIA", "Airbus A320"),
        Flight("GH012", "AGENDADO", "", "2024-08-10", "2024-08-10", "09:00", "12:30", "LAX", "NYC", "Airbus A380")
    )

    @Before
    fun setup() {
        stopKoin()
        context = ApplicationProvider.getApplicationContext()
        startKoin {
            androidContext(context)
            modules(repositoryModule, viewModelModule)
        }
        repository = FlightRepository(context)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getFlights should emit Loading and Success states`() = runTest {
        repository.getFlights().test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(ResponseData.Loading::class.java)

            val success = awaitItem()
            assertThat(success).isInstanceOf(ResponseData.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getFilteredFlights should filter correctly for each FlightFilter type`() = runTest {
        val filterTests = listOf(
            Triple(
                FlightFilter.Completed,
                "CONCLUIDO",
                repository.getCompletedFlights()
            ),
            Triple(
                FlightFilter.Cancelled,
                "CANCELADO",
                repository.getCancelledFlights()
            ),
            Triple(
                FlightFilter.Future,
                "AGENDADO",
                repository.getFutureFlights()
            )
        )

        filterTests.forEach { (filter, _, flowToTest) ->
            flowToTest.test {
                val loading = awaitItem()
                assertThat(loading).isInstanceOf(ResponseData.Loading::class.java)

                val success = awaitItem()
                assertThat(success).isInstanceOf(ResponseData.Success::class.java)

                val flights = (success as ResponseData.Success).data
                assertTrue(flights.all(filter.predicate))

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `repository should handle json parsing error`() = runTest {
        val invalidJson = "invalid json"
        val inputStream = invalidJson.byteInputStream()

        val mockContext = mockk<Context>()
        val mockAssetManager = mockk<AssetManager>()

        every { mockContext.assets } returns mockAssetManager
        every { mockAssetManager.open(any()) } returns inputStream

        val errorRepository = FlightRepository(mockContext)

        errorRepository.getFlights().test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(ResponseData.Loading::class.java)

            val error = awaitItem()
            assertThat(error).isInstanceOf(ResponseData.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}