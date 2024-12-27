package com.example.safearound.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RadiusDropdown(onRadiusChanged: (radius: Int) -> Unit) {
    Column {
        DropdownInput(
            options = listOf(1, 2, 5, 10, 25, 50, 100).map { DropdownItem(
                value = it,
                name = "$it km"
            ) },
            label = "Promień",
            onItemSelected = { onRadiusChanged(it.value) }
        )
        Text("Promień okręgu wokół twojej lokalizacji, w którym będą wyświetlane zgłoszenia")
    }
}