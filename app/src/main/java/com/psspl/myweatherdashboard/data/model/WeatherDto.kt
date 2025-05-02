package com.psspl.myweatherdashboard.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/***
 * Name : WeatherResponseDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for OpenWeatherMap API weather response.
 * */
@JsonClass(generateAdapter = true)
data class WeatherResponseDto(
    @Json(name = "coord") val coord: CoordDto,
    @Json(name = "weather") val weather: List<WeatherConditionDto>,
    @Json(name = "main") val main: MainDto,
    @Json(name = "wind") val wind: WindDto,
    @Json(name = "clouds") val clouds: CloudsDto,
    @Json(name = "dt") val timestamp: Long,
    @Json(name = "sys") val sys: SysDto,
    @Json(name = "timezone") val timezone: Int,
    @Json(name = "name") val cityName: String
)

/***
 * Name : CoordDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for geographical coordinates.
 * */
@JsonClass(generateAdapter = true)
data class CoordDto(
    @Json(name = "lon") val longitude: Double,
    @Json(name = "lat") val latitude: Double
)

/***
 * Name : WeatherConditionDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for weather condition details.
 * */
@JsonClass(generateAdapter = true)
data class WeatherConditionDto(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

/***
 * Name : MainDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for main weather data.
 * */
@JsonClass(generateAdapter = true)
data class MainDto(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "sea_level") val seaLevel: Int,
    @Json(name = "grnd_level") val groundLevel: Int,
    @Json(name = "humidity") val humidity: Int
)

/***
 * Name : WindDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for wind data.
 * */
@JsonClass(generateAdapter = true)
data class WindDto(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val direction: Int
)

/***
 * Name : CloudsDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for cloud coverage data.
 * */
@JsonClass(generateAdapter = true)
data class CloudsDto(
    @Json(name = "all") val all: Int
)

/***
 * Name : CloudsDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : DTO for system data (sunrise, sunset).
 * */
@JsonClass(generateAdapter = true)
data class SysDto(
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long
)

