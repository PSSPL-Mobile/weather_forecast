package com.psspl.myweatherdashboard.data.model

import com.squareup.moshi.Json

/***
 * Name : GeocodingResponseDto.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Data class for OpenWeatherMap /geo/1.0/reverse API response.
 * Maps location details from JSON to Kotlin objects.
 */
data class GeocodingResponseDto(
    @Json(name = "name") val name: String,
    @Json(name = "country") val country: String,
    @Json(name = "local_names") val localNames: Map<String, String>? = null,
    @Json(name = "state") val state: String? = null, // State might not always be present
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)