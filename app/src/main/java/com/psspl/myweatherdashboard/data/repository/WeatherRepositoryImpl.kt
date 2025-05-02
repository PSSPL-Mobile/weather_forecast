package com.psspl.myweatherdashboard.data.repository

import HourlyWeatherDto
import com.psspl.myweatherdashboard.BuildConfig
import com.psspl.myweatherdashboard.data.location.Location
import com.psspl.myweatherdashboard.data.location.LocationTracker
import com.psspl.myweatherdashboard.data.model.GeocodingResponseDto
import com.psspl.myweatherdashboard.data.model.PollutionResponseDto
import com.psspl.myweatherdashboard.data.model.WeatherResponseDto
import com.psspl.myweatherdashboard.data.network.WeatherApi
import com.psspl.myweatherdashboard.domain.repository.WeatherRepository
import javax.inject.Inject

/***
 * Name : WeatherRepositoryImpl.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Implementation of WeatherRepository for fetching weather and location data.
 * */
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val locationTracker: LocationTracker
) : WeatherRepository {

    /***
     * Fetches current weather data for a location.
     * @param location The location to fetch weather for.
     * @return WeatherResponseDto with current weather data.
     */
    override suspend fun getCurrentWeather(location: Location): WeatherResponseDto {
        // Fetch weather data using OpenWeatherMap API
        return weatherApi.getCurrentWeather(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
        )
    }

    /***
     * Fetches hourly weather forecast for a location.
     * @param location The location to fetch weather for.
     * @return HourlyWeatherDto with hourly forecast data.
     */
    override suspend fun getHourlyCurrentWeather(location: Location): HourlyWeatherDto {
        // Fetch weather data using OpenWeatherMap API
        return weatherApi.getHourlyWeather(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
        )
    }

    /***
     * Fetches air pollution data for a location.
     * @param location The location to fetch pollution data for.
     * @return PollutionResponseDto with pollution data.
     */
    override suspend fun getPollutionData(location: Location): PollutionResponseDto {
        // Fetch weekly weather data using OpenWeatherMap API
        return weatherApi.getWeeklyWeather(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
        )
    }

    /***
     * Fetches location name by coordinates (reverse geocoding).
     * @param location The location to fetch the name for.
     * @return List of GeocodingResponseDto with location details.
     */
    override suspend fun getLocationName(location: Location): List<GeocodingResponseDto> {
        // Fetch location data using OpenWeatherMap API
        return weatherApi.getLocationName(
            latitude = location.latitude,
            longitude = location.longitude,
            apiKey = BuildConfig.OPENWEATHERMAP_API_KEY
        )
    }

    /***
     * Fetches the current device location.
     * @return Location with current coordinates, or null if unavailable.
     */
    override suspend fun getCurrentLocation(): Location? {
        // Fetch current device location
        return locationTracker.getCurrentLocation()
    }
}