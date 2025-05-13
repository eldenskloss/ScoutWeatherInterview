package com.example.scoutweatherinterview.feature.weather.domain.model

import com.example.scoutweatherinterview.core.Constants

data class ForecastItem(
    val date: String,
    val day: String,
    val temperaturesInFahrenheit: Temperatures,
    val temperaturesInCelsius: Temperatures,
    val condition: Condition,
    val chanceOfRain: String,
    val chanceOfSnow: String,
    val averageHumidity: String
) {
    fun getTemperatureFor(isFahrenheit: Boolean): Temperatures {
        return if (isFahrenheit) {
            temperaturesInFahrenheit
        } else {
            temperaturesInCelsius
        }
    }

    fun getTemperatureSign(isFahrenheit: Boolean): String {
        return if (isFahrenheit) Constants.FAHRENHEIT_UNICODE else Constants.CELSIUS_UNICODE
    }
}

data class Temperatures(
    val averageTemperature: String,
    val temperatureLow: String,
    val temperatureHigh: String
)
