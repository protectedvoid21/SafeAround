package com.example.safearound.components.incidentview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationRailItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.safearound.R
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient

data class NavigationRailItem(
    val content: @Composable () -> Unit,
    val label: String,
    val icon: @Composable () -> Unit
)

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

    val sections = listOf(
        NavigationRailItem(
            label = "Szczegóły",
            content = { IncidentDetails(incident!!) },
            icon = { Icon(painter = painterResource(id = R.drawable.info), contentDescription = null) }
        ),
        NavigationRailItem(
            label = "Zdjęcia",
            content = { Text("TODOasdasd") },
            icon = { Icon(painter = painterResource(id = R.drawable.image), contentDescription = null) }
        ),
        NavigationRailItem(
            label = "Komentarze",
            content = { IncidentComments(incident?.comments) },
            icon = { Icon(painter = painterResource(id = R.drawable.comments), contentDescription = null) }
        )
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
                sections[selectedIndex].content()
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    sections.forEachIndexed { index, item ->
                        NavigationRailItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                            },
                            label = { Text(item.label) },
                            icon = item.icon
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

