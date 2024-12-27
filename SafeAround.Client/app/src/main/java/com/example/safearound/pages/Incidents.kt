package com.example.safearound.pages

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safearound.components.IncidentsList
import com.example.safearound.models.Incident
import com.example.safearound.modules.UserLocationViewModel
import com.example.safearound.services.SafeAroundClient

@Composable
fun Incidents(
    locationViewModel: UserLocationViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var incidents by remember { mutableStateOf(listOf<Incident>()) }
    var radius by remember { mutableIntStateOf(5) }
    val context: Context = LocalContext.current

    LaunchedEffect(locationViewModel.userLocation.value, radius) {
        locationViewModel.fetchUserLocation(context)
        locationViewModel.userLocation.value?.let {
            incidents = SafeAroundClient().getIncidents(
                latitude = it.latitude,
                longitude = it.longitude,
                radius = radius
            )
        }
    }

    Box(modifier = modifier) {
        IncidentsList(incidents, onRadiusChanged = { radius = it; })
    }
}