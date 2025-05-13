package com.example.scoutweatherinterview.feature.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastDetails
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastItem
import com.example.scoutweatherinterview.feature.weather.domain.model.Location
import com.example.scoutweatherinterview.feature.weather.domain.model.Temperatures
import com.example.scoutweatherinterview.feature.weather.presentation.viewmodels.ForecastDetailViewModel
import com.example.scoutweatherinterview.ui.components.ErrorScreen
import com.example.scoutweatherinterview.ui.components.LoadingScreen
import com.example.scoutweatherinterview.ui.theme.ScoutWeatherInterviewTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ForecastDetailScreen(
    forecastDate: String,
    viewModel: ForecastDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeatherFromLocation(forecastDate)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val isFahrenheitState = viewModel.isFahrenheitState.collectAsStateWithLifecycle(true).value

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
                    ForecastDetailContent(forecastDetails = uiState.result, shouldShowInFahrenheit = isFahrenheitState)
                }
            }
        }
    }
}

@Composable
fun ForecastDetailContent(
    modifier: Modifier = Modifier,
    forecastDetails: ForecastDetails,
    shouldShowInFahrenheit: Boolean = true
) {
    val temperatures = forecastDetails.forecast.getTemperatureFor(shouldShowInFahrenheit)
    val unicodeTemp = forecastDetails.forecast.getTemperatureSign(shouldShowInFahrenheit)
    val forecast = forecastDetails.forecast
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(forecast.day)
            Text("${temperatures.temperatureLow}/ ${temperatures.temperatureHigh}$unicodeTemp")
        }
        Text("${forecastDetails.location.name} ${forecastDetails.location.region}")

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier.height(120.dp),
                imageModel = { forecast.condition.icon },
                imageOptions = ImageOptions(contentScale = ContentScale.Fit)
            )
            Row(verticalAlignment = Alignment.Top) {
                Text(text = temperatures.averageTemperature, fontSize = 40.sp)
                Text(unicodeTemp)
            }
            Text(text = forecast.condition.text, fontSize = 24.sp)
        }

        Card(modifier = Modifier.padding(top = 32.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(text = "Humidity", fontWeight = FontWeight.Bold)
                    Text("${forecast.averageHumidity}%")
                }
                VerticalDivider(Modifier.height(32.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Rain", fontWeight = FontWeight.Bold)
                    Text("${forecast.chanceOfRain}%")
                }
                VerticalDivider(Modifier.height(32.dp))
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(text = "Snow", fontWeight = FontWeight.Bold)
                    Text("${forecast.chanceOfSnow}%")
                }
            }
        }
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
