package com.example.scoutweatherinterview.feature.weather.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scoutweatherinterview.core.CommonUIState
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.presentation.viewmodels.SevenDayForecastViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SevenDayForecastScreen(
    viewModel: SevenDayForecastViewModel = hiltViewModel<SevenDayForecastViewModel>(),
    onNavigate: (forecastID: String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value ?: run { return }
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
    onNavigate: (forecastID: String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Seven Day Forecast screen for ${forecast.location.name}")
        forecast.forecasts.forEach { forecast ->

            Spacer(modifier = Modifier.height(8.dp)) // Use space between and a list instead

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        GlideImage(
                            modifier = Modifier.height(32.dp),
                            imageModel = { forecast.condition.icon },
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        Text(forecast.condition.text)
                    }
                    Row {
                        Text("Highs of ${forecast.temperatureHigh} and lows of ${forecast.temperatureLow}")
                    }
                    Text("Date: ${forecast.day} Humidity: ${forecast.humidity} wind: ${forecast.windInMph}")
                }
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun ForecastPreview() {
//    ScoutWeatherInterviewTheme {
//        Forecast(viewModel = viewModel, onNavigate = {})
//    }
// }
