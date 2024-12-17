package com.example.safearound.services

import com.example.safearound.models.AddIncidentRequest
import com.example.safearound.models.Category
import com.example.safearound.models.Incident
import com.example.safearound.viewmodels.IncidentViewModel
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SafeAroundClient {
    private val client = HttpClient(CIO)
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private const val BASE_URL = "http://192.168.0.24:5178"
    }

    suspend fun getIncidents(): List<Incident> {
        val response: HttpResponse = client.get("$BASE_URL/incidents")
        val content = response.bodyAsText()

        return json.decodeFromString<List<Incident>>(content)
    }

    suspend fun getCategories(): List<Category> {
        val response: HttpResponse = client.get("$BASE_URL/categories")
        val content = response.bodyAsText()

        return json.decodeFromString<List<Category>>(content)
    }

    suspend fun addIncident(incidentViewModel: IncidentViewModel, latLng: LatLng) {
        val response = client.post("$BASE_URL/incidents") {
            setBody(json.encodeToString(
                AddIncidentRequest(
                    incidentViewModel.title,
                    incidentViewModel.description,
                    incidentViewModel.categoryId,
                    latLng.latitude,
                    latLng.longitude
                )
            ))
        }
    }
}