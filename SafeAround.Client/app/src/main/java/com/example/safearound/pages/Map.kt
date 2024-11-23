package com.example.safearound.pages

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.safearound.R
import com.example.safearound.helpers.getIconForCategory
import com.example.safearound.helpers.vectorToBitmap
import com.example.safearound.models.Incident
import com.example.safearound.services.SafeAroundClient
import com.example.safearound.viewmodels.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
    val uiSettings by remember { mutableStateOf(MapUiSettings(
        mapToolbarEnabled = true,
        myLocationButtonEnabled = true,
    )) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val scope = rememberCoroutineScope()
    val client = remember { SafeAroundClient() }

    val cameraPositionState = rememberCameraPositionState()
    val context: Context = LocalContext.current
    val userLocation by mapViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

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
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    mapViewModel.fetchUserLocation(context, fusedLocationClient)
                }
            }

            LaunchedEffect(Unit) {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
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
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
            }

            LaunchedEffect(true) {
                scope.launch {
                    incidents = client.getIncidents()
                }
            }

            incidents.forEach { incident ->
                Marker(
                    icon = vectorToBitmap(context, getIconForCategory(incident.categoryCode)),
                    state = rememberMarkerState(position = LatLng(incident.latitude, incident.longitude)),
                    title = incident.title,
                    snippet = incident.description
                )
            }
        }
    }
}