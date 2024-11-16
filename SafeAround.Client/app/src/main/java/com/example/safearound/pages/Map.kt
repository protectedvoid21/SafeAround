package com.example.safearound.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun Map(navController: NavController) {
    val uiSettings by remember { mutableStateOf(MapUiSettings(
        mapToolbarEnabled = true,
        myLocationButtonEnabled = true,
    )) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val scope = rememberCoroutineScope()
    val client = remember { SafeAroundClient() }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.1, 17.0), 10f)
    }

    Box(Modifier.fillMaxSize()) {
        var incidents by remember {
            mutableStateOf(emptyList<Incident>())
        }
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            LaunchedEffect(true) {
                scope.launch {
                    incidents = client.getIncidents()
                }
            }

            incidents.forEach { incident ->
                Marker(
                    state = rememberMarkerState(position = LatLng(incident.latitude, incident.longitude)),
                    title = incident.title,
                    snippet = incident.description
                )
            }
        }
    }
}