package com.example.safearound.components.incidentview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.safearound.helpers.formatForDisplay
import com.example.safearound.helpers.toShortString
import com.example.safearound.models.Incident

@Composable
fun IncidentDetails(incident: Incident) {
    Column(Modifier.padding(24.dp, 0.dp, 24.dp, 24.dp)) {
        Text(
            text = incident.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = incident.categoryName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Zgłoszono: " + incident.occurrenceDate.toShortString() + " (" + incident.occurrenceDate.formatForDisplay() + ")",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = incident.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}