package com.example.scoutweatherinterview.feature.weather.data.dto

data class ForecastdayDTO(
    val date: String,
    val date_epoch: Int,
    val day: DayDTO
)
