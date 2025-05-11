package com.example.scoutweatherinterview.feature.weather.domain.repository

import com.example.scoutweatherinterview.core.LocationResult

interface LocationRepository {
    suspend fun getUsersLocation(): LocationResult
}
