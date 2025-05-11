package com.example.scoutweatherinterview.feature.weather.data.repository

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.scoutweatherinterview.core.LocationResult
import com.example.scoutweatherinterview.feature.weather.domain.model.CurrentLocation
import com.example.scoutweatherinterview.feature.weather.domain.repository.LocationRepository
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl(private val application: Application) : LocationRepository {
    override suspend fun getUsersLocation(): LocationResult {
        if (ActivityCompat.checkSelfPermission(
                application,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    application,
                    ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return LocationResult.MissingPermission
        }
        return suspendCoroutine { cont ->
            try {
                val fusedLocationService =
                    LocationServices.getFusedLocationProviderClient(application)
                fusedLocationService.lastLocation.addOnCompleteListener { task ->
                    if (task.isComplete) {
                        if (task.isSuccessful) {
                            val lat = task.result.latitude
                            val long = task.result.longitude
                            cont.resume(
                                LocationResult.Success(
                                    CurrentLocation(
                                        lat = lat.toString(),
                                        long = long.toString()
                                    )
                                )
                            )
                        } else {
                            cont.resume(LocationResult.Error)
                        }
                    }
                }
            } catch (e: SecurityException) {
                cont.resume(LocationResult.Error)
            }
        }
    }
}
