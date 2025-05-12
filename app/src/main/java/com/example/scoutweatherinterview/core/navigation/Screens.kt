package com.example.scoutweatherinterview.core.navigation

import kotlinx.serialization.Serializable

interface Screens {
    @Serializable
    data object SevenDayForecast : Screens

    @Serializable
    data class ForecastDetailScreen(val forecastDate: String) : Screens
}
