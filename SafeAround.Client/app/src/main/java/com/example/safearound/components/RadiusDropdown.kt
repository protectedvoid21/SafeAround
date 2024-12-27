package com.example.safearound.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadiusDropdown(onRadiusChanged: (radius: Int) -> Unit) {
    Column {
        DropdownInput(
            options = listOf(1, 2, 5, 10, 25, 50, 100).map { DropdownItem(
                value = it,
                name = "$it km"
            ) },
            label = "Promień",
            onItemSelected = { onRadiusChanged(it.value) },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Promień okręgu wokół twojej lokalizacji, w którym będą wyświetlane zgłoszenia",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}