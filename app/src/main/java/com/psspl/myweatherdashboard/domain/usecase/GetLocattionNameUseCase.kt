package com.psspl.myweatherdashboard.domain.usecase

import com.psspl.myweatherdashboard.data.location.Location
import com.psspl.myweatherdashboard.data.model.GeocodingResponseDto
import com.psspl.myweatherdashboard.domain.repository.WeatherRepository
import javax.inject.Inject

/***
 * Name : GetLocationNameUseCase.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Use case to get location data from api.
 **/
class GetLocationNameUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(location: Location? = null): Result<List<GeocodingResponseDto>> {
        return try {
            // Get location (either provided or from device)
            val finalLocation = location ?: repository.getCurrentLocation()
            ?: return Result.failure(Exception("Unable to get location"))
            // Fetch hourly weather data
            val response = repository.getLocationName(finalLocation)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}