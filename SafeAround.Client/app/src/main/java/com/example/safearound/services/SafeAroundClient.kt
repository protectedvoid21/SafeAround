package com.example.safearound.services

import com.example.safearound.models.AddIncidentRequest
import com.example.safearound.models.ApiResponse
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
        private const val BASE_URL = "http://192.168.31.156:5178"
    }

    suspend fun getIncidents(latitude: Double, longitude: Double, radius: Int): List<Incident> {
        val response: HttpResponse = client.get("$BASE_URL/incidents?latitude=$latitude&longitude=$longitude&radius=$radius")
        val content = response.bodyAsText()

        return json.decodeFromString<List<Incident>>(content)
    }

    suspend fun getIncident(id: Int): Incident? {
        val response: HttpResponse = client.get("$BASE_URL/incidents/$id")
        val content = response.bodyAsText()

        return json.decodeFromString<Incident?>(content)
    }

    suspend fun getCategories(): List<Category> {
        val response: HttpResponse = client.get("$BASE_URL/categories")
        val content = response.bodyAsText()

        return json.decodeFromString<List<Category>>(content)
    }

    suspend fun addIncident(request: AddIncidentRequest): ApiResponse {
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
        return json.decodeFromString<ApiResponse>(content)
    }

    suspend fun addComment(incidentId: Int, comment: String): ApiResponse {
        val response = client.post("$BASE_URL/incidents/$incidentId/comments") {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(mapOf("comment" to comment)))
        }

        val content = response.bodyAsText()
        return json.decodeFromString<ApiResponse>(content)
    }
}