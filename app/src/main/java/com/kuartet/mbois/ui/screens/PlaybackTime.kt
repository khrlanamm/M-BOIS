package com.kuartet.mbois.ui.screens

import java.util.Locale

fun formatPlaybackTime(millis: Float, locale: Locale = Locale.getDefault()): String {
    val totalSeconds = (millis / 1000).toLong()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(locale, "%02d:%02d", minutes, seconds)
}
