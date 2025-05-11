package com.example.scoutweatherinterview.feature.weather.data.repository

import com.example.scoutweatherinterview.core.network.CommonResponse
import com.example.scoutweatherinterview.feature.weather.data.dto.mapper.toForecast
import com.example.scoutweatherinterview.feature.weather.domain.model.Forecast
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class FetchForecastRepositoryImpl @Inject constructor(private val api: FetchForecastApi) :
    FetchForecastRepository {
    override suspend fun fetchForecast(lat: String, long: String): CommonResponse<Forecast> {
        return try {
            val forecast = api.fetchForecast("$lat,$long", 7).toForecast()
            CommonResponse.Success(forecast)
        } catch (e: HttpException) {
            CommonResponse.Error(e.message())
        } catch (e: UnknownHostException) {
            CommonResponse.Error(e.message ?: "Unknown Host")
        } catch (e: SocketTimeoutException) {
            CommonResponse.Error(e.message ?: "Socket Timeout")
        }
    }
}
