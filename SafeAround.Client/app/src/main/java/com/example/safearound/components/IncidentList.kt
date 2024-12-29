package com.example.safearound.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safearound.helpers.distanceToString
import com.example.safearound.helpers.formatForDisplay
import com.example.safearound.helpers.getIconForCategory
import com.example.safearound.models.Incident
import kotlinx.datetime.LocalDateTime

@Composable
fun IncidentsList(incidents: List<Incident>, onRadiusChanged: (radius: Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(
                rememberScrollState(),
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Zgłoszenia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            RadiusDropdown(onRadiusChanged = onRadiusChanged)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (incidents.any()) {
                incidents.forEach { incident ->
                    IncidentItem(incident)
                }
            }
            else {
                Text(
                    "Brak zarejestrowanych zgłoszeń w okolicy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )
            }
        }
    }
}

@Composable
fun IncidentItem(incident: Incident) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                color = Color.hsl(0.0f, 0.0f, 0.9f)
            )
            .padding(16.dp, 4.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.25f)
        ) {
            Icon(
                painter = painterResource(getIconForCategory(incident.categoryCode)),
                contentDescription = "Incident icon",
                modifier = Modifier
                    .size(48.dp)
            )
            Text(
                distanceToString(incident.distanceInKm!!),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Clip
            )
            Text(
                incident.occurrenceDate.formatForDisplay(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                incident.categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(incident.title,
                fontSize = 16.sp)
            Text(incident.description,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncidentListPreview() {
    val exampleIncident = Incident(
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
        distanceInKm = 2.532
    )

    val exampleIncidents = List(10) { exampleIncident }
    IncidentsList(exampleIncidents, onRadiusChanged = {})
}