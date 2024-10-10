package com.guicarneirodev.wayairlines

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.guicarneirodev.wayairlines.data.model.Flight
import com.guicarneirodev.wayairlines.ui.navigation.NavGraph
import com.guicarneirodev.wayairlines.viewmodel.FlightsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class FlightsScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val mockViewModel = mockk<FlightsViewModel>(relaxed = true)

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                viewModel { mockViewModel }
            })
        }

        val flightsStateFlow = MutableStateFlow(listOf(
            Flight(
                flight_id = "123",
                airplane_name = "Boeing 737",
                departure_airport = "GRU",
                arrival_airport = "CGH",
                departure_time = "10:00",
                arrival_time = "11:00",
                start_date = "2024-03-15",
                end_date = "2024-03-15",
                status = "Agendado",
                completion_status = "No Horário"
            )
        ))
        every { mockViewModel.flights } returns flightsStateFlow
        every { mockViewModel.completedFlights } returns MutableStateFlow(emptyList())
        every { mockViewModel.cancelledFlights } returns MutableStateFlow(emptyList())
        every { mockViewModel.futureFlights } returns MutableStateFlow(emptyList())
        every { mockViewModel.isLoading } returns MutableStateFlow(false)

        composeTestRule.setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
            navController.navigate("flights")
        }

        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Histórico de Voos").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testFlightsScreenContent() {
        composeTestRule.onRoot().printToLog("FLIGHTS_SCREEN_CONTENT")

        composeTestRule.onNodeWithText("Histórico de Voos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Todos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Concluídos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancelados").assertIsDisplayed()
        composeTestRule.onNodeWithText("Futuros").assertIsDisplayed()

        composeTestRule.onNodeWithText("Voo 123 - Boeing 737").assertIsDisplayed()
        composeTestRule.onNodeWithText("GRU").assertIsDisplayed()
        composeTestRule.onNodeWithText("CGH").assertIsDisplayed()

        composeTestRule.onNode(hasScrollToIndexAction()).performScrollToIndex(0)

        composeTestRule.onNodeWithText("Horário de saída:").assertIsDisplayed()
        composeTestRule.onNodeWithText("10:00").assertIsDisplayed()

        composeTestRule.onNodeWithText("Horário de chegada:").assertIsDisplayed()
        composeTestRule.onNodeWithText("11:00").assertIsDisplayed()

        composeTestRule.onNodeWithText("Data de saída:").assertIsDisplayed()

        composeTestRule.onRoot().printToLog("ALL_NODES")

        composeTestRule.onNodeWithText("Data de chegada:").assertIsDisplayed()

        composeTestRule.onNodeWithText("Agendado").assertIsDisplayed()
    }

    @Test
    fun testLoadingState() {
        val loadingStateFlow = MutableStateFlow(false)
        every { mockViewModel.isLoading } returns loadingStateFlow

        loadingStateFlow.value = true

        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.waitForIdle()

        composeTestRule.onRoot().printToLog("LOADING_STATE_TREE")

        loadingStateFlow.value = false
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Histórico de Voos").assertIsDisplayed()
    }

    @Test
    fun testTabSelection() {
        composeTestRule.onNodeWithText("Concluídos").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Cancelados").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Futuros").performClick()
        composeTestRule.waitForIdle()
    }
}