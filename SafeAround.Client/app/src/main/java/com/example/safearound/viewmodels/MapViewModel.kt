package com.example.safearound.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.safearound.models.Incident

class MapViewModel : ViewModel() {
    private val _clickedMarker = mutableStateOf<Incident?>(null)
    val clickedIncident: State<Incident?> = _clickedMarker

    fun onMarkerClicked(markerData: Incident?) {
        _clickedMarker.value = markerData
    }
}