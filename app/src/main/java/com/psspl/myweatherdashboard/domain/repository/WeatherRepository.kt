package com.psspl.myweatherdashboard.domain.repository

import HourlyWeatherDto
import com.psspl.myweatherdashboard.data.location.Location
import com.psspl.myweatherdashboard.data.model.GeocodingResponseDto
import com.psspl.myweatherdashboard.data.model.PollutionResponseDto
import com.psspl.myweatherdashboard.data.model.WeatherResponseDto

interface WeatherRepository {
    suspend fun getCurrentWeather(location: Location): WeatherResponseDto
    suspend fun getHourlyCurrentWeather(location: Location): HourlyWeatherDto
    suspend fun getPollutionData(location: Location): PollutionResponseDto
    suspend fun getLocationName(location: Location): List<GeocodingResponseDto>
    suspend fun getCurrentLocation(): Location?
}