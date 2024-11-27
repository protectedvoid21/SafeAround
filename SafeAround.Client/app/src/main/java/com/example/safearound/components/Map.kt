package com.example.safearound.components

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.safearound.helpers.getIconForCategory
import com.example.safearound.helpers.vectorToBitmap
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient
import com.example.safearound.viewmodels.MapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Map(mapViewModel: MapViewModel) {
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = true,
                myLocationButtonEnabled = true,
            )
        )
    }

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val scope = rememberCoroutineScope()
    val client = remember { SafeAroundClient() }

    val cameraPositionState = rememberCameraPositionState()
    val context: Context = LocalContext.current
    val userLocation by mapViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var incidents by remember {
        mutableStateOf(emptyList<Incident>())
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
    ) {
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                mapViewModel.fetchUserLocation(context, fusedLocationClient)
            }
        }

        LaunchedEffect(Unit) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    mapViewModel.fetchUserLocation(context, fusedLocationClient)
                }

                else -> {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
        userLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Twoja lokalizacja",
                snippet = "Tutaj jest twoje położenie"
            )
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 12.5f)
        }

        LaunchedEffect(true) {
            scope.launch {
                incidents = client.getIncidents()
            }
        }

        incidents.forEach { incident ->
            IncidentMarker(
                incident,
                cameraPositionState,
                mapViewModel::onMarkerClicked
            )
        }
    }
}

@Composable
fun IncidentMarker(incident: Incident, cameraPositionState: CameraPositionState, onMarkerClicked: (Incident) -> Unit) {
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    MarkerInfoWindow(
        state = rememberMarkerState(
            position = LatLng(
                incident.latitude,
                incident.longitude
            )
        ),
        onClick = { _ ->
            coroutineScope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(incident.latitude, incident.longitude),
                        15f
                    )
                )
            }
            onMarkerClicked(incident)
            true
        },
        title = incident.title,
        snippet = incident.description,
        icon = vectorToBitmap(context, getIconForCategory(incident.categoryCode))
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = incident.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = incident.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}