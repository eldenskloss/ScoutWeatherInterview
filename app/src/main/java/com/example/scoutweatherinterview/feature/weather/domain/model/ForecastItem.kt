package com.example.scoutweatherinterview.feature.weather.domain.model

data class ForecastItem(val day: String, val temperatureHigh: String, val temperatureLow: String, val humidity: String, val windInMph: Double, val condition: Condition)
