package com.example.safearound.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.safearound.components.IncidentSheet
import com.example.safearound.viewmodels.MapViewModel
import com.example.safearound.components.Map

@Composable
fun Home(mapViewModel: MapViewModel) {
    val clickedMarker by mapViewModel.clickedMarker

    Box(Modifier.fillMaxSize()) {
        IncidentSheet(
            clickedMarker = clickedMarker,
            onDismiss = {
                mapViewModel.onMarkerClicked(null)
            }
        )
        Map(mapViewModel)
    }
}