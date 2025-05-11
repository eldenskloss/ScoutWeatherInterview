package com.example.scoutweatherinterview.feature.weather.data.dto.mapper

import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastResponseDTO
import com.example.scoutweatherinterview.feature.weather.domain.model.Condition
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastItem
import com.example.scoutweatherinterview.feature.weather.domain.model.Location

fun ForecastResponseDTO.toForecast(): Forecast {
    val forecasts = this.forecast.forecastday.map {
        val conditionDTO = it.day.condition
        ForecastItem(
            day = it.date,
            temperatureHigh = it.day.maxtemp_f.toString(),
            temperatureLow = it.day.mintemp_f.toString(),
            humidity = it.day.avghumidity.toString(),
            windInMph = 1.0,
            condition = Condition(text = conditionDTO.text, icon = "https:${conditionDTO.icon}", code = conditionDTO.code)
        )
    }
    return Forecast(
        location = Location(
            this.location.name,
            this.location.region,
            this.location.country
        ),
        forecasts = forecasts
    )
}
