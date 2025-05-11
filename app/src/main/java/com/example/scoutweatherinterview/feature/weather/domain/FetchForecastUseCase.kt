package com.example.scoutweatherinterview.feature.weather.domain

import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.core.network.CommonResponse
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchForecastUseCase @Inject constructor(
    private val forecastRepository: FetchForecastRepository,
    private val locationRepository: LocationRepository
) {
    fun fetchForecast() = flow {
        emit(CommonUIState.Loading)
        val location = locationRepository.getUsersLocation()
        when (val response = forecastRepository.fetchForecast(location.lat, location.long)) {
            is CommonResponse.Success -> {
                emit(CommonUIState.Success(response.result))
            }
            is CommonResponse.Error -> {
                emit(CommonUIState.Error(response.errorMessage))
            }
        }
    }
}
