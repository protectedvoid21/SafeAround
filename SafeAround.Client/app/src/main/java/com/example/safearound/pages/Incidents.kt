package com.example.safearound.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safearound.helpers.getIconForCategory
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient
import kotlinx.datetime.LocalDateTime

@Composable
fun Incidents() {
    var incidents by remember { mutableStateOf(listOf<Incident>()) }

    LaunchedEffect(Unit) {
        incidents = SafeAroundClient().getIncidents()
    }

    IncidentsList(incidents)
}

@Composable
fun IncidentsList(incidents: List<Incident>) {
    @Composable
    fun IncidentItem(incident: Incident) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(
                    color = Color.hsl(0.0f, 0.0f, 0.9f)
                )
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(getIconForCategory(incident.categoryCode)),
                contentDescription = "Incident icon",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically),
            )
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
                    fontSize = 14.sp)
                Text(incident.description,
                    fontSize = 12.sp)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState(),
            )
    ) {
        Text(
            text = "Zgłoszenia",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        incidents.forEach { incident ->
            IncidentItem(incident)
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
    )

    val exampleIncidents = List(10) { exampleIncident }
    IncidentsList(exampleIncidents)
}