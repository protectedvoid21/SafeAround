package com.example.safearound.helpers

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.formatForDisplay(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val dateDiff = now.date.minus(this.date)
    if (dateDiff.years > 0) {
        if (dateDiff.years == 1) {
            return "rok temu"
        }
        return "${dateDiff.years} lat temu"
    }
    if (dateDiff.months > 0) {
        return "${dateDiff.months} miesięcy temu"
    }
    if (dateDiff.days > 0) {
        return "${dateDiff.days} dni temu"
    }
    if (dateDiff.hours > 0) {
        return "${dateDiff.hours} godzin temu"
    }
    if (dateDiff.minutes > 0) {
        return "${dateDiff.minutes} minut temu"
    }
    return "przed chwilą"
}

fun getCommentsText(commentCount: Int): String {
    return when (commentCount) {
        0 -> "Brak komentarzy!"
        1 -> "1 komentarz"
        else -> when (commentCount % 10) {
            in 2..4 -> "$commentCount komentarze"
            else -> "$commentCount komentarzy"
        }
    }
}