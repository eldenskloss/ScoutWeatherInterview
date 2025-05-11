package com.example.scoutweatherinterview.core

import com.example.scoutweatherinterview.feature.weather.domain.model.CurrentLocation

sealed class LocationResult {
    data class Success(val location: CurrentLocation) : LocationResult()
    data object MissingPermission : LocationResult()
    data object Error : LocationResult()
}
