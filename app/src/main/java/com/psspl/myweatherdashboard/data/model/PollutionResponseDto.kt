package com.psspl.myweatherdashboard.data.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/***
 * Name : PollutionResponseDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Data class for OpenWeatherMap air pollution API response.
 * Desc : Maps JSON response to Kotlin objects.
 */
@JsonClass(generateAdapter = true)
data class PollutionResponseDto(
    @Json(name = "coord") val coord: CoordDto,
    @Json(name = "list") val list: List<PollutionEntryDto>
)

/***
 * Name : PollutionEntryDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Data class for a pollution data entry in the OpenWeatherMap API response.
 * Desc : Represents a single pollution data point.
 */
@JsonClass(generateAdapter = true)
data class PollutionEntryDto(
    @Json(name = "main") val main: PollutionMainDto,
    @Json(name = "components") val components: PollutionComponentsDto,
    @Json(name = "dt") val timestamp: Long
)

/***
 * Name : PollutionMainDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Data class for main pollution data in the OpenWeatherMap API response.
 * Desc : Contains the Air Quality Index (AQI).
 */
@JsonClass(generateAdapter = true)
data class PollutionMainDto(
    @Json(name = "aqi") val aqi: Int
)

/***
 * Name : PollutionComponentsDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Data class for pollution components in the OpenWeatherMap API response.
 * Desc : Represents concentrations of various pollutants.
 */
@JsonClass(generateAdapter = true)
data class PollutionComponentsDto(
    @Json(name = "co") val co: Double,
    @Json(name = "no") val no: Double,
    @Json(name = "no2") val no2: Double,
    @Json(name = "o3") val o3: Double,
    @Json(name = "so2") val so2: Double,
    @Json(name = "pm2_5") val pm25: Double,
    @Json(name = "pm10") val pm10: Double,
    @Json(name = "nh3") val nh3: Double
)