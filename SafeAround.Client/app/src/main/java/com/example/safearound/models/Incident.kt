package com.example.safearound.models

import kotlinx.serialization.Serializable

@Serializable
data class Incident (
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    //val date: LocalDate,
    //val createdOn: DateTime,
    //val userId: UUID,
){
}