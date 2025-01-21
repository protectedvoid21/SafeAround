package com.example.safearound

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.safearound.components.Map
import com.example.safearound.models.Incident
import com.example.safearound.services.ISafeAroundClient
import com.example.safearound.viewmodels.MapViewModel
import com.example.safearound.viewmodels.UserLocationViewModel
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.LocalDateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MapTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var safeAroundClient: ISafeAroundClient

    private val mapViewModel = MapViewModel()
    private var locationViewModel = UserLocationViewModel()

    @Before
    fun setUp() {
        safeAroundClient = mockk<ISafeAroundClient>()

        coEvery { safeAroundClient.getIncidents(any(), any(), any()) } coAnswers {
            listOf(
                Incident(
                    id = 1,
                    title = "Incident Title",
                    description = "Incident Description",
                    latitude = 51.09,
                    longitude = 17.0400,
                    categoryId = 1,
                    categoryName = "Incident Category",
                    categoryCode = "INCIDENT",
                    occurrenceDate = LocalDateTime(2024, 11, 21, 12, 43, 50),
                    userId = "asdkpoaskdopk1opk2o",
                    distanceInKm = 2.532
                )
            )
        }

        locationViewModel = mockk<UserLocationViewModel>()
        coEvery { locationViewModel.userLocation } coAnswers {
            mutableStateOf(LatLng(51.09, 17.0400))
        }

        composeTestRule.setContent {
            Map(safeAroundClient, mapViewModel, locationViewModel, onError = {})
        }
    }

    @Test
    fun testMapDisplaysIncidents() {
        // Assuming incidents have a title or description that can be checked
        composeTestRule.onNodeWithTag("GMap").assertExists()
        coVerify { safeAroundClient.getIncidents(any(), any(), any()) }
        composeTestRule.onNodeWithText("Incident Title", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun testMapLongClickCreatesMarker() {
        // Simulate a long click on the map
        composeTestRule.onNodeWithTag("GMap").performClick()

        // Check if the marker is created
        composeTestRule.onNodeWithText("Nowe zgłoszenie").assertIsDisplayed()
    }

    @Test
    fun testIncidentCreatorDismiss() {
        // Simulate a long click on the map
        composeTestRule.onNodeWithTag("GMap").performClick()

        // Check if the marker is created
        composeTestRule.onNodeWithText("Nowe zgłoszenie").assertIsDisplayed()

        // Simulate dismissing the incident creator
        composeTestRule.onNodeWithText("Dismiss").performClick()

        // Check if the marker is removed
        composeTestRule.onNodeWithText("Nowe zgłoszenie").assertDoesNotExist()
    }
}