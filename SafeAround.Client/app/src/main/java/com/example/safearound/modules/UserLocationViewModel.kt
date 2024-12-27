package com.example.safearound.modules

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class UserLocationViewModel : ViewModel() {
    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    fun fetchUserLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val isPermissionGranted = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        Log.d("User debugging", "Permission for location: $isPermissionGranted")
        if (isPermissionGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    _userLocation.value = userLatLng
                    Log.d("User debugging", "User location (viewModel): ${_userLocation.value}")

                    // INVALID SIMULATED LOCATION WORKAROUND
                    //_userLocation.value = LatLng(51.09, 17.0400)
                }
            }
        }
    }
}


