package com.example.safearound.helpers

import com.example.safearound.R

fun getIconForCategory(category: String): Int {
    return when (category) {
        "CAR_ACCIDENT" -> R.drawable.car_accident
        "THEFT" -> R.drawable.theft
        "FIRE" -> R.drawable.fire
        "FLOOD" -> R.drawable.flood
        "EARTHQUAKE" -> R.drawable.earthquake
        "POWER_OUTAGES" -> R.drawable.power_outages
        "TERRORIST_ATTACK" -> R.drawable.terrorist_attack
        else -> {
            R.drawable.danger
        }

    }
}