package com.psspl.myweatherdashboard.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/***
 * Name : Location.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class representing a geographic location with latitude and longitude coordinates.
 * @property latitude The latitude coordinate of the location in degrees.
 * @property longitude The longitude coordinate of the location in degrees.
 */
data class Location(val latitude: Double, val longitude: Double)

/***
 * Name : LocationTracker.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc :Interface defining the contract for tracking the device's current location.
 * This interface provides a single method to retrieve the current location asynchronously,
 * allowing implementations to handle location updates using different strategies.
 */
interface LocationTracker {
    /***
     * Retrieves the current location of the device.
     * @return A [Location] object containing the latitude and longitude, or null if the location
     *         cannot be determined (e.g., due to missing permissions or location services).
     */
    suspend fun getCurrentLocation(): Location?
}


/***
 * Name : DefaultLocationTracker.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Default implementation of the [LocationTracker] interface using the Google Play Services
 * Fused Location Provider API to fetch the device's current location.
 *
 * This class injects a [Context] and [FusedLocationProviderClient] to manage location requests
 * and updates. It uses Kotlin coroutines and Flow to handle asynchronous location data.
 *
 * @property context The application context used to access system services.
 * @property fusedLocationClient The FusedLocationProviderClient instance for location services.
 */
class DefaultLocationTracker @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationTracker {

    /***
     * Retrieves the current location of the device using the Fused Location Provider API.
     *
     * This method creates a [callbackFlow] to emit location updates and returns the first available
     * location. It requires the necessary location permissions to be granted. The `@SuppressLint`
     * annotation suppresses the "MissingPermission" warning, assuming permissions are handled
     * elsewhere (e.g., in the UI layer).
     *
     * @return A [Location] object with the latest latitude and longitude, or null if no location
     *         is available within the request duration.
     */
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = callbackFlow {
        // Define a location request with high accuracy and update intervals
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        // Custom LocationCallback to handle location results
        val locationCallback = object : LocationCallback() {
            /***
             * Called when new location data is available.
             *
             * Emits a [Location] object with the latest coordinates if a valid location is received.
             *
             * @param result The [LocationResult] containing the latest location data.
             */
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let {
                    trySend(Location(it.latitude, it.longitude)).isSuccess
                }
            }
        }

        // Request location updates from the FusedLocationProviderClient
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // Cleanup: Remove location updates when the flow is closed
        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }.firstOrNull() // Return the first emitted location or null if none
}