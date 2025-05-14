package com.example.scoutweatherinterview.feature.weather.data.repository

import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchForecastApi {
    companion object {
        private const val KEY = "" // Your Key Here
        private const val SUB_ROUTE = "v1/forecast.json"
        private const val KEY_QUERY = "?key=$KEY"
    }

    @GET("/$SUB_ROUTE$KEY_QUERY")
    suspend fun fetchForecast(@Query("q") latLong: String, @Query("days") days: Int): ForecastResponseDTO
}
