package com.guicarneirodev.wayairlines

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.guicarneirodev.wayairlines.data.repository.FlightRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
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

    @Before
    fun setup() {
        stopKoin()
        context = ApplicationProvider.getApplicationContext()
        startKoin {
            androidContext(context)
            modules(appModule)
        }
        repository = FlightRepository(context)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getFlights returns non-empty list`() = runBlocking {
        val flights = repository.getFlights().first()
        assertThat(flights).isNotEmpty()
    }

    @Test
    fun `getCompletedFlights returns only completed flights`() = runBlocking {
        val completedFlights = repository.getCompletedFlights().first()
        assertThat(completedFlights).isNotEmpty()
        assertThat(completedFlights.all { it.status == "CONCLUIDO" }).isTrue()
    }

    @Test
    fun `getCancelledFlights returns only cancelled flights`() = runBlocking {
        val cancelledFlights = repository.getCancelledFlights().first()
        assertThat(cancelledFlights).isNotEmpty()
        assertThat(cancelledFlights.all { it.status == "CANCELADO" }).isTrue()
    }

    @Test
    fun `getFutureFlights returns an empty list if there are no future flights`() = runBlocking {
        val futureFlights = repository.getFutureFlights().first()
        assertThat(futureFlights).isEmpty()
    }
}