package com.example.safearound.models

import kotlinx.datetime.Instant
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
){
}