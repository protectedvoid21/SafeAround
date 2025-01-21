package com.example.safearound.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.safearound.models.Incident
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IncidentsListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyIncidentsListShouldHaveExplicitTextAboutNoIncidents() {
        // Test no incidents message displayed
        composeTestRule.setContent {
            IncidentsList(incidents = emptyList(), onRadiusChanged = {})
        }
        composeTestRule.onNodeWithText("Brak zarejestrowanych zgłoszeń w okolicy").assertIsDisplayed()
    }

    @Test
    fun loadedListOfIncidentsShouldBeDisplayed() {
        val incidentTitle = "Zderzenie na Kościuszki"
        val incidentDescription = "Ruch został zablokowany na ulicy Kościuszki w obu kierunkach. Kierowcy proszeni są o zachowanie ostrożności."

        val exampleIncident = Incident(
            id = 1,
            title = incidentTitle,
            description = incidentDescription,
            latitude = 51.09,
            longitude = 17.0400,
            categoryId = 1,
            categoryName = "Wypadek samochodowy",
            categoryCode = "CAR_ACCIDENT",
            occurrenceDate = LocalDateTime(2024, 11, 21, 12, 43, 50),
            userId = "asdkpoaskdopk1opk2o",
            distanceInKm = 2.532
        )
        val incidents = listOf(exampleIncident)
        composeTestRule.setContent {
            IncidentsList(incidents = incidents, onRadiusChanged = {})
        }
        composeTestRule.onNodeWithText(incidentTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(incidentDescription).assertIsDisplayed()
        composeTestRule.onNodeWithText("Brak zarejestrowanych zgłoszeń w okolicy").assertDoesNotExist()
    }
}