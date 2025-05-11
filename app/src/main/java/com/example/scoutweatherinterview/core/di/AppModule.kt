package com.example.scoutweatherinterview.core.di

import com.example.scoutweatherinterview.core.Constants.WEATHER_API_BASE_URL
import com.example.scoutweatherinterview.feature.weather.data.repository.FetchForecastApi
import com.example.scoutweatherinterview.feature.weather.data.repository.FetchForecastRepositoryImpl
import com.example.scoutweatherinterview.feature.weather.data.repository.LocationRepositoryImpl
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFetchForecastApi(): FetchForecastApi {
        return Retrofit
            .Builder()
            .baseUrl(WEATHER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchForecastApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFetchForecastRepository(api: FetchForecastApi): FetchForecastRepository {
        return FetchForecastRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesLocationRepository(): LocationRepository {
        return LocationRepositoryImpl()
    }
}
