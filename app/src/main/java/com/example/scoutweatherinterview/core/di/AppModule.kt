package com.example.scoutweatherinterview.core.di

import android.app.Application
import com.example.scoutweatherinterview.core.Constants.WEATHER_API_BASE_URL
import com.example.scoutweatherinterview.core.logging.Logger
import com.example.scoutweatherinterview.core.logging.LoggerImpl
import com.example.scoutweatherinterview.core.network.CacheInterceptor
import com.example.scoutweatherinterview.feature.weather.data.repository.DataStoreManagerImpl
import com.example.scoutweatherinterview.feature.weather.data.repository.FetchForecastApi
import com.example.scoutweatherinterview.feature.weather.data.repository.FetchForecastRepositoryImpl
import com.example.scoutweatherinterview.feature.weather.data.repository.LocationRepositoryImpl
import com.example.scoutweatherinterview.feature.weather.domain.repository.DataStoreManager
import com.example.scoutweatherinterview.feature.weather.domain.repository.FetchForecastRepository
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val tenMegabytes = 10L * 1024L * 1024L
        return OkHttpClient().newBuilder()
            .cache(Cache(File(application.cacheDir, "http-cache"), tenMegabytes))
            .addNetworkInterceptor(CacheInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesFetchForecastApi(httpClient: OkHttpClient): FetchForecastApi {
        return Retrofit
            .Builder()
            .client(httpClient)
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
    fun providesLocationRepository(application: Application): LocationRepository {
        return LocationRepositoryImpl(application)
    }

    @Provides
    @Singleton
    fun providesDataStoreManager(application: Application): DataStoreManager {
        return DataStoreManagerImpl(application)
    }

    @Provides
    fun providesLogger(): Logger {
        return LoggerImpl()
    }
}
