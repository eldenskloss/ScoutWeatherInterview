package com.example.scoutweatherinterview.feature.weather.domain.repository

import com.example.scoutweatherinterview.core.network.CommonResponse
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast

interface FetchForecastRepository {
    suspend fun fetchForecast(lat: String, long: String): CommonResponse<Forecast>
}
