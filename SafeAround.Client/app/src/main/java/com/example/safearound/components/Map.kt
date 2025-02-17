package com.example.safearound.components

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.content.ContextCompat
import com.example.safearound.models.Incident
import com.example.safearound.services.ISafeAroundClient
import com.example.safearound.viewmodels.MapViewModel
import com.example.safearound.viewmodels.UserLocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun Map(
    safeAroundClient: ISafeAroundClient,
    mapViewModel: MapViewModel,
    locationViewModel: UserLocationViewModel,
    onError: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val client = remember { safeAroundClient }

    val cameraPositionState = rememberCameraPositionState()
    val context: Context = LocalContext.current

    var incidents by remember { mutableStateOf(emptyList<Incident>()) }
    var createNewIncidentMarker by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = false),
        onMapLongClick = { latLng ->
            createNewIncidentMarker = latLng
            scope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(latLng, 18f)
                )
            }
        },
        modifier = Modifier.testTag("GMap")
    ) {
        InitMap(locationViewModel, context, cameraPositionState)

        LaunchedEffect(locationViewModel.userLocation.value) {
            if (locationViewModel.userLocation.value == null) {
                return@LaunchedEffect
            }
            scope.launch {
                incidents = client.getIncidents(
                    latitude = locationViewModel.userLocation.value!!.latitude,
                    longitude = locationViewModel.userLocation.value!!.longitude,
                    radius = 25
                )
            }
        }

        incidents.forEach { incident ->
            IncidentMarker(
                incident,
                cameraPositionState,
                mapViewModel::onMarkerClicked
            )
        }

        createNewIncidentMarker?.let {
            Marker(
                state = rememberMarkerState(
                    position = it
                ),
                title = "Nowe zgłoszenie",
                snippet = "Kliknij aby dodać zgłoszenie"
            )
            IncidentCreator(
                createNewIncidentMarker,
                onDismiss = { createNewIncidentMarker = null },
                onSuccess = {
                    createNewIncidentMarker = null
                    scope.launch {
                        incidents = client.getIncidents(
                            latitude = locationViewModel.userLocation.value!!.latitude,
                            longitude = locationViewModel.userLocation.value!!.longitude,
                            radius = 25
                        )
                    }
                },
                onError = onError
            )
        }
    }
}

@Composable
fun InitMap(locationViewModel: UserLocationViewModel, context: Context, cameraPositionState: CameraPositionState) {
    val userLocation by locationViewModel.userLocation

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationViewModel.fetchUserLocation(context)
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                locationViewModel.fetchUserLocation(context)
            }
            else -> {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
    userLocation?.let {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 12.5f)
    }
}
