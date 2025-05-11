package com.example.scoutweatherinterview.feature.weather.data.dto

data class ForecastResponseDTO(
    val current: CurrentDTO,
    val forecast: ForecastDTO,
    val location: LocationDTO
)
