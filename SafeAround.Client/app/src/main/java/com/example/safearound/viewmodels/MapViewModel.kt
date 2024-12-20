package com.example.safearound.viewmodels

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.safearound.models.Incident
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    private val _clickedMarker = mutableStateOf<Incident?>(null)
    val clickedIncident: State<Incident?> = _clickedMarker

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    fun onMarkerClicked(markerData: Incident?) {
        _clickedMarker.value = markerData
    }

    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)

                    _userLocation.value = userLatLng
                    // INVALID SIMULATED LOCATION WORKAROUND
                    _userLocation.value = LatLng(51.09, 17.0400)
                }
            }
        }
    }
}