package com.psspl.myweatherdashboard.domain.model

/***
 * Name : City.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Data class representing a city with its geographical and administrative details.
 *
 * This class is used to store information about a city, including its name, state, country,
 * and geographical coordinates (latitude and longitude). It is typically used to populate
 * a list of cities for user selection in the UI, such as in a dropdown menu for weather data.
 *
 * @property city The name of the city (e.g., "Ahmedabad").
 * @property state The state or administrative region where the city is located (e.g., "Gujarat").
 * @property country The country code of the city, typically in ISO 3166-1 alpha-2 format (e.g., "IN" for India).
 * @property latitude The latitude coordinate of the city in degrees, used for fetching weather data.
 * @property longitude The longitude coordinate of the city in degrees, used for fetching weather data.
 */
data class City(
    val city: String,
    val state: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)