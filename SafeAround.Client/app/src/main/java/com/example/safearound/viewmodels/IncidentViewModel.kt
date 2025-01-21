package com.example.safearound.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safearound.components.DropdownItem
import com.example.safearound.models.AddIncidentRequest
import com.example.safearound.services.ISafeAroundClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class IncidentViewModel(private val safeAroundClient: ISafeAroundClient) : ViewModel() {
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var categoryId by mutableIntStateOf(0)
        private set

    private val _categoriesDropdown = mutableStateOf(emptyList<DropdownItem>())
    val categoriesDropdown: State<List<DropdownItem>> = _categoriesDropdown

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            val categories = safeAroundClient.getCategories()
            _categoriesDropdown.value = categories.map { DropdownItem(it.id, it.name, it.iconCode) }
        }
    }

    public fun send(latLng: LatLng, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val response = safeAroundClient.addIncident(
                AddIncidentRequest(
                    title,
                    description,
                    categoryId,
                    latLng.latitude,
                    latLng.longitude
                )
            )

            if (response.isSuccess) {
                onSuccess()
            } else {
                onError(response.message)
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        title = newTitle
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
    }

    fun onCategoryChange(newCategoryId: Int) {
        categoryId = newCategoryId
    }
}