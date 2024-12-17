package com.example.safearound.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


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
)

@Serializable
data class Category (
    val id: Int,
    val name: String,
    val iconCode: String,
)

data class AddIncidentRequest (
    val title: String,
    val description: String,
    val categoryId: Int,
    val latitude: Double,
    val longitude: Double,
)