package com.example.safearound.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.safearound.helpers.getIconForCategory
import com.example.safearound.helpers.vectorToBitmap
import com.example.safearound.models.Incident
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

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