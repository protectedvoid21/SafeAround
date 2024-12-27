package com.example.safearound.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.safearound.components.IncidentSheet
import com.example.safearound.components.Map
import com.example.safearound.modules.UserLocationViewModel
import com.example.safearound.viewmodels.MapViewModel

@Composable
fun Home(mapViewModel: MapViewModel, locationViewModel: UserLocationViewModel, onError: (String) -> Unit) {
    val clickedIncident by mapViewModel.clickedIncident

    Box(Modifier.fillMaxSize()) {
        IncidentSheet(
            clickedMarker = clickedIncident,
            onDismiss = {
                mapViewModel.onMarkerClicked(null)
            }
        )
        Map(mapViewModel, locationViewModel, onError)
    }
}