package com.example.scoutweatherinterview.feature.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    ForecastDetailContent(
                        forecastDetails = uiState.result,
                        shouldShowInFahrenheit = isFahrenheitState
                    )
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
    Column(modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = forecast.day, fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Text(text = "${forecastDetails.location.name} ${forecastDetails.location.region}", fontSize = 18.sp, fontWeight = FontWeight.Normal)

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
        ForecastDetailsGridSection(
            informationSection = listOf(
                Pair("Lows", "${temperatures.temperatureLow}$unicodeTemp"),
                Pair("Highs", "${temperatures.temperatureHigh}$unicodeTemp"),
                Pair("Rain", "${forecast.chanceOfRain}%"),
                Pair("Snow", "${forecast.chanceOfSnow}%"),
                Pair("Humidity", "${forecast.averageHumidity}%")
            )
        )
    }
}

@Composable
private fun ForecastDetailsGridSection(modifier: Modifier = Modifier, informationSection: List<Pair<String, String>>) {
    Card(modifier = modifier.padding(top = 32.dp), colors = CardColors(containerColor = Color.White, disabledContainerColor = Color.White, contentColor = Color.Black, disabledContentColor = Color.Black)) {
        val groupedByTwoSection = informationSection.chunked(2)
        groupedByTwoSection.forEachIndexed { index, chunk ->
            val leftSection = chunk.first()
            val rightSection = chunk.getOrNull(1)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                leftSection.let { (title, subTitle) ->
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        Text(text = title, fontWeight = FontWeight.Bold)
                        Text(subTitle)
                    }
                }
                rightSection?.let { (title, subTitle) ->
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text(text = title, fontWeight = FontWeight.Bold)
                        Text(subTitle)
                    }
                }
            }
            if (index != groupedByTwoSection.size - 1) {
                HorizontalDivider(Modifier.padding(horizontal = 8.dp))
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
