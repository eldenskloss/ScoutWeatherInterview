package com.example.scoutweatherinterview.feature.weather.data.repository

import com.example.scoutweatherinterview.BuildConfig
import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchForecastApi {
    companion object {
        private const val WEATHER_FORECAST_ROUTE = "v1/forecast.json"
    }

    @GET("/$WEATHER_FORECAST_ROUTE")
    suspend fun fetchForecast(@Query("key") apiKey: String = BuildConfig.WEAHTER_API_KEY, @Query("q") latLong: String, @Query("days") days: Int): ForecastResponseDTO
}
