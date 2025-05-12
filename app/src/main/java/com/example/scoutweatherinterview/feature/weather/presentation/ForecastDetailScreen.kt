package com.example.scoutweatherinterview.feature.weather.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.feature.weather.domain.model.Condition
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastDetails
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastItem
import com.example.scoutweatherinterview.feature.weather.domain.model.Location
import com.example.scoutweatherinterview.feature.weather.domain.model.Temperatures
import com.example.scoutweatherinterview.feature.weather.presentation.viewmodels.ForecastDetailViewModel
import com.example.scoutweatherinterview.ui.components.ErrorScreen
import com.example.scoutweatherinterview.ui.components.LoadingScreen
import com.example.scoutweatherinterview.ui.theme.ScoutWeatherInterviewTheme

@Composable
fun ForecastDetailScreen(forecastDate: String, viewModel: ForecastDetailViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeatherFromLocation(forecastDate)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is CommonUIState.Error -> {
                    ErrorScreen(errorText = uiState.message)
                }

                CommonUIState.Loading -> {
                    LoadingScreen()
                }

                is CommonUIState.Success -> {
                }
            }
        }
    }
}

@Composable
fun ForecastDetailContent(modifier: Modifier = Modifier, forecastDetails: ForecastDetails) {
    Column {
        Text("You got the weather for ${forecastDetails.location.name} for ${forecastDetails.forecast.day}")
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastDetailScreenPreview() {
    ScoutWeatherInterviewTheme {
        ForecastDetailContent(
            forecastDetails = ForecastDetails(
                location = Location(name = "San Antonio", "TX", "United States"),
                forecast = ForecastItem(
                    day = "Today",
                    temperaturesInFahrenheit = Temperatures(
                        averageTemperature = "90",
                        temperatureLow = "60",
                        temperatureHigh = "99"
                    ),
                    temperaturesInCelsius = Temperatures(
                        averageTemperature = "32",
                        temperatureLow = "15",
                        temperatureHigh = "37"
                    ),
                    condition = Condition(
                        "Sunny",
                        "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                        100
                    ),
                    chanceOfRain = "0",
                    chanceOfSnow = "0",
                    averageHumidity = "20",
                    date = ""
                )
            )
        )
    }
}
