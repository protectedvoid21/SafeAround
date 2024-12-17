@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.safearound.components

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.safearound.helpers.getIconForCategory

data class DropdownItem(val id: Int, val name: String, val icon: String? = null)

@Composable
fun DropdownInput(options: List<DropdownItem>, label: String, onItemSelected: (DropdownItem) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState("")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            value = textFieldState.text.toString(),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            label = { Text(label) },
            onValueChange = {
                textFieldState.setTextAndPlaceCursorAtEnd(it)
            },
            readOnly = true,
            singleLine = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name, style = MaterialTheme.typography.bodyLarge) },
                    trailingIcon = {
                        if (option.icon != null) {
                            Icon(
                                painter = painterResource(getIconForCategory(option.icon)),
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                            )
                        }
                    },
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd(option.name)
                        expanded = false
                        onItemSelected(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}