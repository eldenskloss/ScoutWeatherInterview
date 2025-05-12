package com.example.scoutweatherinterview.feature.weather.domain

import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.core.network.CommonResponse
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastDetails
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchForecastUseCase @Inject constructor(
    private val forecastRepository: FetchForecastRepository
) {
    fun fetchForecast(lat: String, long: String) = flow {
        emit(CommonUIState.Loading)
        when (val response = forecastRepository.fetchForecast(lat, long)) {
            is CommonResponse.Success -> {
                emit(CommonUIState.Success(response.result))
            }

            is CommonResponse.Error -> {
                emit(CommonUIState.Error(response.errorMessage))
            }
        }
    }

    fun fetchForecastForDate(lat: String, long: String, specificDate: String) = flow {
        emit(CommonUIState.Loading)
        when (val response = forecastRepository.fetchForecast(lat, long)) {
            is CommonResponse.Success -> {
                response.result.forecasts.firstOrNull { it.date == specificDate }
                    ?.let { forecastForDate ->
                        emit(
                            CommonUIState.Success(
                                ForecastDetails(
                                    response.result.location,
                                    forecastForDate
                                )
                            )
                        )
                    } ?: run {
                    emit(CommonUIState.Error("Forecast for that date not found."))
                }
            }

            is CommonResponse.Error -> {
                emit(CommonUIState.Error(response.errorMessage))
            }
        }
    }
}
