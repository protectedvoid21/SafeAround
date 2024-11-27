package com.example.safearound.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.safearound.viewmodels.MarkerData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentSheet(clickedMarker: MarkerData?, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(clickedMarker) {
        clickedMarker?.let {
            sheetState.show()
        }
    }

    if (clickedMarker != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
            },
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = clickedMarker.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = clickedMarker.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
