package com.example.scoutweatherinterview.feature.weather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.feature.weather.domain.FetchForecastUseCase
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SevenDayForecastViewModel @Inject constructor(private val fetchForecast: FetchForecastUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<CommonUIState<Forecast>?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchForecast.fetchForecast().collect { state ->
                _uiState.value = state
            }
        }
    }
}
