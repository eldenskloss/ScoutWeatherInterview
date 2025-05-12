package com.example.scoutweatherinterview.feature.weather.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.feature.weather.domain.model.Condition
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastItem
import com.example.scoutweatherinterview.feature.weather.domain.model.Location
import com.example.scoutweatherinterview.feature.weather.domain.model.Temperatures
import com.example.scoutweatherinterview.feature.weather.presentation.viewmodels.SevenDayForecastViewModel
import com.example.scoutweatherinterview.ui.theme.ScoutWeatherInterviewTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SevenDayForecastScreen(
    viewModel: SevenDayForecastViewModel = hiltViewModel<SevenDayForecastViewModel>(),
    onNavigate: (forecastDate: String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
        )
    )
    LaunchedEffect(locationPermissionState) {
        snapshotFlow { locationPermissionState.allPermissionsGranted }.collect {
            if (it) {
                viewModel.fetchWeatherFromLocation()
            } else {
                // TODO: Show user Location is required, try again screen
                locationPermissionState.launchMultiplePermissionRequest()
            }
        }
    }

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
                    ForecastContent(forecast = uiState.result, onNavigate = onNavigate)
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, errorText: String) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(errorText)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun ForecastContent(
    modifier: Modifier = Modifier,
    forecast: Forecast,
    shouldShowInFahrenheit: Boolean = true,
    onNavigate: (forecastDate: String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Seven Day Forecast for ${forecast.location.name}")
        forecast.forecasts.forEach { forecast ->
            val temperatures = if (shouldShowInFahrenheit) {
                forecast.temperaturesInFahrenheit
            } else {
                forecast.temperaturesInCelsius
            }
            val unicodeTemp = if (shouldShowInFahrenheit) "\u2109" else "\u2103"
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onNavigate(forecast.date) }
                ) {
                    Text(text = forecast.day, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GlideImage(
                            modifier = Modifier.height(40.dp),
                            imageModel = { forecast.condition.icon },
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        Text(text = forecast.condition.text, fontSize = 18.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(text = "Low:", fontWeight = FontWeight.Bold)
                            Text("${temperatures.temperatureLow}$unicodeTemp")
                        }
                        Row {
                            Text(text = "Avg:", fontWeight = FontWeight.Bold)
                            Text("${temperatures.averageTemperature}$unicodeTemp")
                        }
                        Row {
                            Text(text = "High:", fontWeight = FontWeight.Bold)
                            Text("${temperatures.temperatureHigh}$unicodeTemp")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastPreview() {
    val mockForecast = Forecast(
        location = Location("San Antonio", "Tx", "United States"),
        forecasts = getMockForecasts()
    )
    ScoutWeatherInterviewTheme {
        ForecastContent(
            forecast = mockForecast,
            onNavigate = {}
        )
    }
}

private fun getMockForecasts(): List<ForecastItem> {
    return listOf(
        ForecastItem(
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
        ),
        ForecastItem(
            day = "Tuesday",
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = "85",
                temperatureLow = "58",
                temperatureHigh = "92"
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = "29",
                temperatureLow = "14",
                temperatureHigh = "33"
            ),
            condition = Condition(
                "Partly cloudy",
                "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                100
            ),
            chanceOfRain = "5",
            chanceOfSnow = "0",
            averageHumidity = "25",
            date = ""
        ),
        ForecastItem(
            day = "Wednesday",
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = "88",
                temperatureLow = "62",
                temperatureHigh = "95"
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = "31",
                temperatureLow = "17",
                temperatureHigh = "35"
            ),
            condition = Condition(
                "Clear",
                "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                100
            ),
            chanceOfRain = "0",
            chanceOfSnow = "0",
            averageHumidity = "18",
            date = ""
        ),
        ForecastItem( // Thu
            day = "Thursday",
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = "78",
                temperatureLow = "55",
                temperatureHigh = "85"
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = "26",
                temperatureLow = "13",
                temperatureHigh = "29"
            ),
            condition = Condition(
                "Light rain shower",
                "https://cdn.weatherapi.com/weather/64x64/day/353.png",
                100
            ),
            chanceOfRain = "40",
            chanceOfSnow = "0",
            averageHumidity = "60",
            date = ""
        ),
        ForecastItem( // Fri
            day = "Friday",
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = "80",
                temperatureLow = "56",
                temperatureHigh = "88"
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = "27",
                temperatureLow = "13",
                temperatureHigh = "31"
            ),
            condition = Condition(
                "Cloudy",
                "https://cdn.weatherapi.com/weather/64x64/day/119.png",
                100
            ),
            chanceOfRain = "10",
            chanceOfSnow = "0",
            averageHumidity = "55",
            date = ""
        ),
        ForecastItem(
            day = "Saturday",
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = "82",
                temperatureLow = "57",
                temperatureHigh = "90"
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = "28",
                temperatureLow = "14",
                temperatureHigh = "32"
            ),
            condition = Condition(
                "Sunny",
                "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                100
            ),
            chanceOfRain = "",
            chanceOfSnow = "",
            averageHumidity = "",
            date = ""
        )
    )
}
