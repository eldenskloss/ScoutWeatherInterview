package com.target.targetcasestudy.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
                    onNavigate = { forecastDate ->
                        navController.navigate(Screens.ForecastDetailScreen(forecastDate))
                    }
                )
            }
            composable<Screens.ForecastDetailScreen> { entry ->
                val forecastDate = entry.toRoute<Screens.ForecastDetailScreen>().forecastDate
                ForecastDetailScreen(forecastDate)
            }
        }
    )
}
