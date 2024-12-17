package com.example.safearound.services

import com.example.safearound.models.AddIncidentRequest
import com.example.safearound.models.Category
import com.example.safearound.models.Incident
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
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

    suspend fun addIncident(request: AddIncidentRequest) {
        val response = client.post("$BASE_URL/incidents") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(
                AddIncidentRequest(
                    request.title,
                    request.description,
                    request.categoryId,
                    request.latitude,
                    request.longitude
                )
            ))
        }

        val content = response.bodyAsText()
        return json.decodeFromString(content)
    }
}