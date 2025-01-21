package com.example.safearound.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
open class ApiResponse(
    val isSuccess: Boolean = false,
    val message: String = ""
)

@Serializable
class ApiResponseWithData<T> : ApiResponse() {
    val data: T? = null
}

@Serializable
data class Incident (
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val categoryId: Int,
    val categoryName: String,
    val categoryCode: String,
    val occurrenceDate: LocalDateTime,
    val userId: String,
    val distanceInKm: Double?,
    val comments: List<Comment>? = emptyList(),
)

@Serializable
data class Category (
    val id: Int,
    val name: String,
    val iconCode: String,
)

@Serializable
data class AddIncidentRequest (
    val title: String,
    val description: String,
    val categoryId: Int,
    val latitude: Double,
    val longitude: Double,
)

@Serializable
data class  Comment (
    val id: Int,
    val userId: String,
    val userName: String,
    val content: String,
    val createdOn: LocalDateTime,
)