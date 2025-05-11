package com.example.scoutweatherinterview.feature.weather.data.repository

import com.example.scoutweatherinterview.feature.weather.domain.model.CurrentLocation
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository

class LocationRepositoryImpl : LocationRepository {
    override fun getUsersLocation(): CurrentLocation {
        // TODO: Hook this up
        return CurrentLocation("29.424349", "-98.491142")
    }
}
