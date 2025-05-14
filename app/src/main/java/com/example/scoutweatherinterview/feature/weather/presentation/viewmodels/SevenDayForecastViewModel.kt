package com.example.scoutweatherinterview.feature.weather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.core.LocationResult
import com.example.scoutweatherinterview.core.logging.Logger
import com.example.scoutweatherinterview.feature.weather.domain.FetchForecastUseCase
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.repository.DataStoreManager
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SevenDayForecastViewModel @Inject constructor(
    private val fetchForecast: FetchForecastUseCase,
    private val locationRepository: LocationRepository,
    private val dataStoreManager: DataStoreManager,
    private val logger: Logger
) : ViewModel() {
    private val _uiState = MutableStateFlow<CommonUIState<Forecast>>(CommonUIState.Loading)
    val uiState = _uiState.asStateFlow()
    val isFahrenheitState = dataStoreManager.getIsFahrenheit()

    init {
        logger.onScreenViewed("SevenDayForecastScreen")
    }

    fun setIsFahrenheit(isFahrenheit: Boolean) {
        logger.onFahrenheitToggled(isFahrenheit)
        viewModelScope.launch {
            dataStoreManager.setIsFahrenheit(isFahrenheit)
        }
    }

    fun fetchWeatherFromLocation() {
        viewModelScope.launch {
            when (val locationResult = locationRepository.getUsersLocation()) {
                LocationResult.Error -> {
                    _uiState.value = CommonUIState.Error("Unable to retrieve location")
                }
                LocationResult.MissingPermission -> {
                    // Ask for permission
                }
                is LocationResult.Success -> {
                    collectStateForFetchingForecast(locationResult.location.lat, locationResult.location.long)
                }
            }
        }
    }

    private suspend fun collectStateForFetchingForecast(lat: String, long: String) {
        fetchForecast.fetchForecast(lat, long).collect { state ->
            _uiState.value = state
        }
    }
}
