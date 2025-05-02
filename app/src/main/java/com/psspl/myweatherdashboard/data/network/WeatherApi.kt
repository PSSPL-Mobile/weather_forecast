package com.psspl.myweatherdashboard.data.network

import HourlyWeatherDto
import com.psspl.myweatherdashboard.data.model.GeocodingResponseDto
import com.psspl.myweatherdashboard.data.model.PollutionResponseDto
import com.psspl.myweatherdashboard.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/***
 * Name : WeatherApi.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc: Retrofit API interface for OpenWeatherMap weather services.
 **/
interface WeatherApi {
    // Fetch weather data using latitude and longitude
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely,alerts"
    ): WeatherResponseDto

    // Fetch weather data using latitude and longitude
    @GET("data/2.5/forecast")
    suspend fun getHourlyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
    ): HourlyWeatherDto

    // Fetch weather data using latitude and longitude
    @GET("data/2.5/air_pollution")
    suspend fun getWeeklyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): PollutionResponseDto

    // Fetch weather data using latitude and longitude
    @GET("geo/1.0/reverse")
    suspend fun getLocationName(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeocodingResponseDto>
}