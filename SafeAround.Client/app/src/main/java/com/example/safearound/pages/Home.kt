package com.example.safearound.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.safearound.components.Map
import com.example.safearound.components.incidentview.IncidentSheet
import com.example.safearound.services.SafeAroundClient
import com.example.safearound.viewmodels.MapViewModel
import com.example.safearound.viewmodels.UserLocationViewModel

@Composable
fun Home(mapViewModel: MapViewModel, locationViewModel: UserLocationViewModel, onError: (String) -> Unit) {
    val clickedIncident by mapViewModel.clickedIncident

    Box(Modifier.fillMaxSize()) {
        IncidentSheet(
            safeAroundClient = SafeAroundClient(),
            incidentId = clickedIncident?.id,
            onDismiss = {
                mapViewModel.onMarkerClicked(null)
            }
        )
        Map(SafeAroundClient(), mapViewModel, locationViewModel, onError)
    }
}