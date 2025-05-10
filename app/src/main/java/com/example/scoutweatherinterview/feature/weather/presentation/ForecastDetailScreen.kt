package com.example.scoutweatherinterview.feature.weather.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.scoutweatherinterview.ui.theme.ScoutWeatherInterviewTheme

@Composable
fun ForecastDetailScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Forecast Detail Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastDetailScreenPreview() {
    ScoutWeatherInterviewTheme {
        ForecastDetailScreen()
    }
}
