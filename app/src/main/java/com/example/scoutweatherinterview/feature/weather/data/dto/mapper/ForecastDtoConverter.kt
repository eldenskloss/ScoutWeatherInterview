package com.example.scoutweatherinterview.feature.weather.data.dto.mapper

import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastResponseDTO
import com.example.scoutweatherinterview.feature.weather.domain.model.Condition
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.model.ForecastItem
import com.example.scoutweatherinterview.feature.weather.domain.model.Location
import com.example.scoutweatherinterview.feature.weather.domain.model.Temperatures
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun ForecastResponseDTO.toForecast(): Forecast {
    val forecasts = this.forecast.forecastday.map {
        val dayDTO = it.day
        val conditionDTO = dayDTO.condition

        // TODO: Move this out of this extension
        fun getDayOfWeek(dateString: String): String {
            try {
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = formatter.parse(dateString) ?: return ""
                val calendar = Calendar.getInstance()
                calendar.time = date

                val today = Calendar.getInstance().time
                val todayString = formatter.format(today)
                if (dateString == todayString) {
                    return "Today"
                }

                return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                    Calendar.SUNDAY -> "Sunday"
                    Calendar.MONDAY -> "Monday"
                    Calendar.TUESDAY -> "Tuesday"
                    Calendar.WEDNESDAY -> "Wednesday"
                    Calendar.THURSDAY -> "Thursday"
                    Calendar.FRIDAY -> "Friday"
                    Calendar.SATURDAY -> "Saturday"
                    else -> ""
                }
            } catch (e: Exception) {
                return ""
            }
        }

        ForecastItem(
            day = getDayOfWeek(it.date),
            temperaturesInFahrenheit = Temperatures(
                averageTemperature = dayDTO.avgtemp_f.toString(),
                temperatureLow = dayDTO.mintemp_f.toString(),
                temperatureHigh = dayDTO.maxtemp_f.toString()
            ),
            temperaturesInCelsius = Temperatures(
                averageTemperature = dayDTO.avgtemp_c.toString(),
                temperatureLow = dayDTO.mintemp_c.toString(),
                temperatureHigh = dayDTO.maxtemp_c.toString()
            ),
            averageHumidity = it.day.avghumidity.toString(),
            condition = Condition(
                text = conditionDTO.text,
                icon = "https:${conditionDTO.icon}",
                code = conditionDTO.code
            ),
            chanceOfRain = it.day.daily_chance_of_rain.toString(),
            chanceOfSnow = it.day.daily_chance_of_snow.toString()
        )
    }
    return Forecast(
        location = Location(
            this.location.name,
            this.location.region,
            this.location.country
        ),
        forecasts = forecasts
    )
}
