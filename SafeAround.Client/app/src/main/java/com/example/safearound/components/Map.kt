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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
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

    var incidents by remember { mutableStateOf(emptyList<Incident>()) }
    var createNewIncidentMarker by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapLongClick = { latLng ->
            createNewIncidentMarker = latLng
            scope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(latLng, 18f)
                )
            }
        }
    ) {
        InitMap(mapViewModel, context, cameraPositionState)

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

        if(createNewIncidentMarker != null) {
            Marker(
                state = rememberMarkerState(
                    position = createNewIncidentMarker!!
                ),
                title = "Nowe zgłoszenie",
                snippet = "Kliknij aby dodać zgłoszenie"
            )
            IncidentCreator(
                createNewIncidentMarker,
                onDismiss = { createNewIncidentMarker = null }
            )
        }
    }
}

@Composable
fun InitMap(mapViewModel: MapViewModel, context: Context, cameraPositionState: CameraPositionState) {
    val userLocation by mapViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

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
}
