package com.psspl.myweatherdashboard.domain.usecase

import com.psspl.myweatherdashboard.data.location.Location
import com.psspl.myweatherdashboard.data.model.WeatherResponseDto
import com.psspl.myweatherdashboard.domain.repository.WeatherRepository
import javax.inject.Inject


/***
 * Name : GetCurrentWeatherUseCase.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Use case to fetch current weather data.
 * */
class GetCurrentWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {

    /***
     * Fetches current weather data for a location.
     * @param location Optional location, defaults to device location if null.
     * @return Result<WeatherResponseDto> with success or failure.
     */
    suspend operator fun invoke(location: Location? = null): Result<WeatherResponseDto> {
        return try {
            // Get location (either provided or from device)
            val finalLocation = location ?: repository.getCurrentLocation()
            ?: return Result.failure(Exception("Unable to get location"))

            // Fetch weather data
            val response = repository.getCurrentWeather(finalLocation)

            // Map DTO to domain model
            //val weather = response.toDomainModel()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
