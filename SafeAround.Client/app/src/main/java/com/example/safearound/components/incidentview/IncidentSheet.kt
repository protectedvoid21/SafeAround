package com.example.safearound.components.incidentview

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentSheet(incidentId: Int?, onDismiss: () -> Unit) {
    var incident by remember { mutableStateOf<Incident?>(null) }
    val sheetState = rememberModalBottomSheetState(true)

    var selectedIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(incidentId) {
        incidentId?.let {
            incident = SafeAroundClient().getIncident(incidentId)
        }
        if (incidentId == null) {
            incident = null
            selectedIndex = 0
        }
        else {
            sheetState.show()
        }
    }

    val sections = listOf<Pair<String, @Composable () -> Unit>>(
        "Szczegóły" to @Composable { IncidentDetails(incident!!) },
        "Zdjęcia" to @Composable { Text("TODO") },
        "Komentarze" to @Composable { IncidentComments(incident?.comments!!) }
    )

    incident?.let {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                sections[selectedIndex].second()
                PrimaryTabRow(
                    selectedTabIndex = selectedIndex,
                ) {
                    sections.forEachIndexed { index, section ->
                        Log.d("IncidentSheet", "Is $index selected: ${selectedIndex == index}")
                        Tab(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            text = { Text(text = section.first, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CommentsPreview() {

    IncidentCommentsPreview()
}

