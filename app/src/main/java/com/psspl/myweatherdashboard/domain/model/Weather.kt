package com.psspl.myweatherdashboard.domain.model

/***
 * Name : Weather.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for consolidated weather data.
 **/
data class Weather(
    val current: CurrentWeather,
    var hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val latitude: Double,
    val longitude: Double,
    var pollution: Pollution? = null,
    var locationName: String? = null,
)

/***
 * Name : Weather.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for current weather details.
 **/
data class CurrentWeather(
    val temperature: Double,
    val condition: WeatherCondition
)

/***
 * Name : Weather.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for hourly weather forecast.
 **/
data class HourlyWeather(
    val time: Long,
    val temperature: Double,
    val condition: WeatherCondition
)

/***
 * Name : DailyWeather.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for daily weather forecast.
 **/
data class DailyWeather(
    val date: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val condition: WeatherCondition
)

/***
 * Name : WeatherCondition.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for weather condition details.
 **/
data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

/***
 * Name : WeatherCondition.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Domain model for pollution data.
 **/
data class Pollution(
    val aqi: Int,
    val pm25: Double,
    val pm10: Double
) {
    /***
     * Returns the air quality name based on AQI.
     * @return String representing air quality (e.g., "Good").
     */
    fun getAirQualityName(): String {
        return when (aqi) {
            1 -> "Good"
            2 -> "Fair"
            3 -> "Moderate"
            4 -> "Poor"
            5 -> "Very Poor"
            else -> "Unknown"
        }
    }
}