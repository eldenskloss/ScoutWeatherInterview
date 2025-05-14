package com.example.scoutweatherinterview

import com.example.scoutweatherinterview.feature.weather.data.dto.ConditionDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.CurrentDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.DayDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastResponseDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.ForecastdayDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.LocationDTO
import com.example.scoutweatherinterview.feature.weather.data.dto.mapper.toForecast
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test_dto_converter() {
        val dto = ForecastResponseDTO(
            current = CurrentDTO(10, ConditionDTO(10, "Cloudy", "")),
            forecast = ForecastDTO(
                listOf(
                    // 2023-10-26 was a Thursday
                    ForecastdayDTO(
                        "2023-10-26",
                        100,
                        DayDTO(
                            1,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            ConditionDTO(10, "Cloudy", ""),
                            1,
                            1,
                            1,
                            1,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0,
                            1.0
                        )
                    )
                )
            ),
            location = LocationDTO("London", 10.0, "", 1000, 10.0, "", "", "")
        )
        val convertedDTO = dto.toForecast()
        assertEquals(dto.forecast.forecastday.first().date, convertedDTO.forecasts.first().date)
        assertEquals("Thursday", convertedDTO.forecasts.first().day)
    }
}
