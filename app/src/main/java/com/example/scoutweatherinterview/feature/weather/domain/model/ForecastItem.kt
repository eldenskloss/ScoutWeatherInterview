package com.example.scoutweatherinterview.feature.weather.domain.model

data class ForecastItem(
    val day: String,
    val temperaturesInFahrenheit: Temperatures,
    val temperaturesInCelsius: Temperatures,
    val condition: Condition,
    val chanceOfRain: String,
    val chanceOfSnow: String,
    val averageHumidity: String
)

data class Temperatures(
    val averageTemperature: String,
    val temperatureLow: String,
    val temperatureHigh: String
)
