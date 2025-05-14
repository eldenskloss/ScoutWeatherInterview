package com.example.scoutweatherinterview

import androidx.compose.runtime.collectAsState
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.core.LocationResult
import com.example.scoutweatherinterview.core.logging.Logger
import com.example.scoutweatherinterview.core.network.CommonResponse
import com.example.scoutweatherinterview.feature.weather.domain.FetchForecastUseCase
import com.example.scoutweatherinterview.feature.weather.domain.model.CurrentLocation
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.model.Location
import com.example.scoutweatherinterview.feature.weather.domain.repository.DataStoreManager
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import com.example.scoutweatherinterview.feature.weather.presentation.viewmodels.SevenDayForecastViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SevenDayForecastViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var fetchForecastRepository: FetchForecastRepository

    @Mock
    private lateinit var locationRepository: LocationRepository

    @Mock
    private lateinit var dataStoreManager: DataStoreManager

    @Mock
    private lateinit var logger: Logger

    private lateinit var viewModel: SevenDayForecastViewModel

    @Before
    fun setup() {
        val fetchForecastUseCase = FetchForecastUseCase(fetchForecastRepository)
        viewModel = SevenDayForecastViewModel(
            fetchForecastUseCase,
            locationRepository,
            dataStoreManager,
            logger
        )
    }

    @Test
    fun logScreenTest() {
        verify(logger).onScreenViewed("SevenDayForecastScreen")
    }

    @Test
    fun failedToFetchWeather() = runTest {
        `when`(locationRepository.getUsersLocation()).thenReturn(LocationResult.Error)
        viewModel.fetchWeatherFromLocation()
        val result = viewModel.uiState.take(1).first()
        assertEquals(
            CommonUIState.Error("Unable to retrieve location"),
            result
        )
    }


    @Test
    fun successWeather() = runTest {
        `when`(locationRepository.getUsersLocation()).thenReturn(LocationResult.Success(
            CurrentLocation("1", "2")
        ))
        `when`(fetchForecastRepository.fetchForecast("1", "2")).thenReturn(
            CommonResponse.Success(
                Forecast(Location("San Antonio", "", ""), emptyList())
            )
        )
        viewModel.fetchWeatherFromLocation()

        val result = viewModel.uiState.drop(1).first()
        assertEquals(
            CommonUIState.Success(Forecast(Location("San Antonio", "", ""), emptyList())),
            result
        )
    }
}