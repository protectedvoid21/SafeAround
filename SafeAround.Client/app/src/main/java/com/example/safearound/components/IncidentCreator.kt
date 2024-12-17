@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.safearound.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safearound.viewmodels.IncidentViewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun IncidentCreator(
    createLatLng: LatLng?,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(createLatLng) {
        if (createLatLng != null) {
            sheetState.show()
        }
        else {
            sheetState.hide()
        }
    }

    createLatLng?.let {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                IncidentForm(createLatLng, onSuccess, onError)
            }
        }
    }
}

@Composable
fun IncidentForm(
    latLng: LatLng,
    onSuccess: () -> Unit,
    onError: (message: String) -> Unit,
    incidentViewModel: IncidentViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            value = incidentViewModel.title,
            onValueChange = { incidentViewModel.onTitleChange(it) },
            label = { Text("Tytuł") },
        )
        TextField(
            value = incidentViewModel.description,
            minLines = 4,
            onValueChange = { incidentViewModel.onDescriptionChange(it) },
            label = { Text("Opis") }
        )
        DropdownInput(
            incidentViewModel.categoriesDropdown.value,
            "Rodzaj zdarzenia",
            onItemSelected = { incidentViewModel.onCategoryChange(it.id) })
        Button(onClick = {
            incidentViewModel.send(latLng, onSuccess, onError)
        }) {
            Text("Wyślij")
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun IncidentCreatorPreview() {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {},
    ) {
        IncidentForm(LatLng(0.0, 0.0), {}, {})
    }
}

