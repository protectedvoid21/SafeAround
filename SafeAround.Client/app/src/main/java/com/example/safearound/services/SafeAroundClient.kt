package com.example.safearound.services

import com.example.safearound.models.Incident
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class SafeAroundClient {
    private val client = HttpClient(CIO)
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private const val BASE_URL = "http://10.0.2.2:5178"
    }

    suspend fun getIncidents(): List<Incident> {
        val response: HttpResponse = client.get("$BASE_URL/incidents")
        val content = response.bodyAsText()

        return json.decodeFromString<List<Incident>>(content)
    }
}