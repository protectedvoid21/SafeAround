package com.example.safearound.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safearound.models.Incident
import kotlinx.datetime.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentSheet(clickedMarker: Incident?, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(clickedMarker) {
        clickedMarker?.let {
            sheetState.show()
        }
    }

    if (clickedMarker != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
            },
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = clickedMarker.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = clickedMarker.categoryName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Zgłoszono: " + clickedMarker.occurrenceDate.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = clickedMarker.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun IncidentSheetPreview() {
    IncidentSheet(
        clickedMarker = Incident(
            id = 1,
            title = "Zderzenie na Kościuszki",
            description = "Ruch został zablokowany na ulicy Kościuszki w obu kierunkach. Kierowcy proszeni są o zachowanie ostrożności.",
            latitude = 51.09,
            longitude = 17.0400,
            categoryId = 1,
            categoryName = "Wypadek samochodowy",
            categoryCode = "CAR_ACCIDENT",
            occurrenceDate = LocalDateTime(2024, 11, 21, 12, 43, 50),
            userId = "asdkpoaskdopk1opk2o",
            distanceInKm = 0.532
        ),
        onDismiss = {}
    )
}
