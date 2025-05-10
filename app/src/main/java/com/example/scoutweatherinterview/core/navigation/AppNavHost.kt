package com.target.targetcasestudy.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scoutweatherinterview.core.navigation.Screens
import com.example.scoutweatherinterview.feature.weather.presentation.ForecastDetailScreen
import com.example.scoutweatherinterview.feature.weather.presentation.SevenDayForecastScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.SevenDayForecast,
        builder = {
            composable<Screens.SevenDayForecast> {
                SevenDayForecastScreen(
                    onNavigate = { forecastID ->
                        navController.navigate(Screens.ForecastDetailScreen(forecastID))
                    }
                )
            }
            composable<Screens.ForecastDetailScreen> {
                ForecastDetailScreen()
            }
        }
    )
}
