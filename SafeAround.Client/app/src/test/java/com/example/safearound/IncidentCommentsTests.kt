package com.example.safearound

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.safearound.components.incidentview.IncidentComments
import com.example.safearound.models.Comment
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IncidentCommentsTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyCommentListShouldStillDisplayNoCommentsText() {
        composeTestRule.setContent {
            IncidentComments(comments = emptyList())
        }
        composeTestRule.onNodeWithText("Brak komentarzy", substring = true).assertExists()
    }

    @Test
    fun loadedCommentsShouldBeDisplayed() {
        val commentText = "This is a comment"
        val comments = listOf(Comment(1, "user", "Jezus Ipanienko", commentText,  LocalDateTime(2004, 4, 2, 21, 37, 0)))
        composeTestRule.setContent {
            IncidentComments(comments = comments)
        }
        composeTestRule.onNodeWithText(commentText).assertExists()
        composeTestRule.onNodeWithText("Brak komentarzy", substring = true).assertDoesNotExist()
    }
}