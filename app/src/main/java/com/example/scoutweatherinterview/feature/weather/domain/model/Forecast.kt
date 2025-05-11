package com.example.scoutweatherinterview.feature.weather.domain.model

data class Forecast(val location: Location, val forecasts: List<ForecastItem>)
