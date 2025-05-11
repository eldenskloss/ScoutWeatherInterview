package com.example.scoutweatherinterview.feature.weather.domain.repository

import com.example.scoutweatherinterview.feature.weather.domain.model.CurrentLocation

interface LocationRepository {
    fun getUsersLocation(): CurrentLocation
}
