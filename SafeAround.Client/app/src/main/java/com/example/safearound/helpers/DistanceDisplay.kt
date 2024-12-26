package com.example.safearound.helpers

fun distanceToString(distance: Double): String {
    return when {
        distance < 1 -> "${(distance * 100).toInt()}0 m"
        else -> "${(distance * 10.0).toInt() / 10.0} km"
    }
}