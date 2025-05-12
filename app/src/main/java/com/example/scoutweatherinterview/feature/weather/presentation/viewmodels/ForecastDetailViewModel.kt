package com.example.scoutweatherinterview.feature.weather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.core.LocationResult
import com.example.scoutweatherinterview.feature.weather.domain.FetchForecastUseCase
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastDetails
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastDetailViewModel @Inject constructor(
    private val fetchForecast: FetchForecastUseCase,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CommonUIState<ForecastDetails>>(CommonUIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchWeatherFromLocation(forecastDate: String) {
        viewModelScope.launch {
            when (val locationResult = locationRepository.getUsersLocation()) {
                LocationResult.Error -> {
                    _uiState.value = CommonUIState.Error("Unable to retrieve location")
                }

                LocationResult.MissingPermission -> {
                    // Ask for permission
                }

                is LocationResult.Success -> {
                    fetchForecast.fetchForecastForDate(
                        lat = locationResult.location.lat,
                        long = locationResult.location.long,
                        specificDate = forecastDate
                    ).collect { state ->
                        _uiState.value = state
                    }
                }
            }
        }
    }
}
