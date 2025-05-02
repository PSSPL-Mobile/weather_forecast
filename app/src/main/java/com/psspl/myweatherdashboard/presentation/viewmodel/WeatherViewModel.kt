package com.psspl.myweatherdashboard.presentation.viewmodel

import HourlyWeatherDto
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psspl.myweatherdashboard.data.location.Location
import com.psspl.myweatherdashboard.domain.model.City
import com.psspl.myweatherdashboard.data.model.GeocodingResponseDto
import com.psspl.myweatherdashboard.data.model.PollutionResponseDto
import com.psspl.myweatherdashboard.data.model.WeatherResponseDto
import com.psspl.myweatherdashboard.domain.model.CurrentWeather
import com.psspl.myweatherdashboard.domain.model.HourlyWeather
import com.psspl.myweatherdashboard.domain.model.Pollution
import com.psspl.myweatherdashboard.domain.model.Weather
import com.psspl.myweatherdashboard.domain.model.WeatherCondition
import com.psspl.myweatherdashboard.domain.usecase.GetCurrentWeatherUseCase
import com.psspl.myweatherdashboard.domain.usecase.GetHourlyWeatherUseCase
import com.psspl.myweatherdashboard.domain.usecase.GetLocationNameUseCase
import com.psspl.myweatherdashboard.domain.usecase.GetPollutionDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Name : WeatherUiState.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Weather screen is used for show UI related information.
 **/
data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String? = null,
    val selectedTab: Int = 0 // 0 for Hourly
)

/***
 * Name : WeatherViewModel.kt
 * Author : Prakash Software Pvt Ltd
 * Date : 28 Apr 2025
 * Desc : Stores weather business logic information.
 * @param getCurrentWeatherUseCase : get current weather info from lat-long.
 * @param getHourlyWeatherUseCase : get hourly weather info from lat-long.
 * @param getPollutionDataUseCase : get pollution details from lat-long.
 * @param getLocationNameUseCase : get location details from lat long.
 **/
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase,
    private val getPollutionDataUseCase: GetPollutionDataUseCase,
    private val getLocationNameUseCase: GetLocationNameUseCase,
) : ViewModel() {

    //Used for UI changes.
    var uiState = mutableStateOf(WeatherUiState())
        private set

    //Used to get location permission.
    var locationPermissionGranted = mutableStateOf(false)
        private set

    //Used to stores selected city
    private val _selectedCity = mutableStateOf<City?>(null)

    //Used to ui related changes
    val selectedCity: MutableState<City?> = _selectedCity

    // List of Indian cities
    val indianCities = listOf(
        City("Mumbai", "Maharashtra", "IN", 19.0760, 72.8777),
        City("Delhi", "Delhi", "IN", 28.7041, 77.1025),
        City("Bangalore", "Karnataka", "IN", 12.9716, 77.5946),
        City("Ahmedabad", "Gujarat", "IN", 23.0225, 72.5714),
        City("Chennai", "Tamil Nadu", "IN", 13.0827, 80.2707),
        City("Kolkata", "West Bengal", "IN", 22.5726, 88.3639),
        City("Hyderabad", "Telangana", "IN", 17.3850, 78.4867)
    )

    /***
     * Handles location permission result.
     * @param granted Permission status.
     */
    fun onPermissionResult(granted: Boolean) {
        locationPermissionGranted.value = granted
        if (granted) {
            viewModelScope.launch {
                uiState.value = uiState.value.copy(isLoading = true)
                try {
                    val defaultCity = indianCities.first { it.city == "Ahmedabad" }
                    _selectedCity.value = defaultCity
                    fetchWeather(_selectedCity)
                } catch (e: Exception) {
                    uiState.value = uiState.value.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    /***
     * Updates selected tab index.
     * @param index New tab index.
     */
    fun selectTab(index: Int) {
        uiState.value = uiState.value.copy(selectedTab = index)
    }

    /***
     * Updates selected city and fetches weather.
     * @param city New selected city.
     */
    fun selectCity(city: City) {
        _selectedCity.value = city
        viewModelScope.launch {
            uiState.value = uiState.value.copy(isLoading = true)
            try {
                fetchWeather(_selectedCity)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    /***
     * Fetches weather data for the selected city.
     * @param _selectedCity Mutable state of selected city.
     */
    private fun fetchWeather(_selectedCity: MutableState<City?>) {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(isLoading = true, error = null)
            async {
                var location = Location(_selectedCity.value?.latitude!!, _selectedCity.value?.longitude!!)
                val resultCurrentWeather = getCurrentWeatherUseCase(location)
                val resultHourlyWeather = getHourlyWeatherUseCase(location)
                val resultWeeklyWeather = getPollutionDataUseCase(location)
                val resultLocationName = getLocationNameUseCase(location)

                when {
                    resultCurrentWeather.isSuccess -> {
                        var weatherResponseDto: WeatherResponseDto? =
                            resultCurrentWeather.getOrNull()
                        var resultCurrentWeather = weatherResponseDto?.toDomainModel()
                        checkHourlyWeatherResponse(
                            resultCurrentWeather,
                            resultHourlyWeather,
                            resultWeeklyWeather,
                            resultLocationName,
                        )
                    }

                    else -> {
                        uiState.value.copy(
                            isLoading = false,
                            error = resultCurrentWeather.exceptionOrNull()?.message
                                ?: "Failed to fetch weather"
                        )
                    }
                }
            }.await()
        }
    }

    /***
     * Checks and processes hourly weather response.
     * @param resultCurrentWeather Current weather data.
     * @param resultHourlyWeather Hourly weather result.
     * @param resultPollution Pollution result.
     * @param resultLocationName Location name result.
     */
    private fun checkHourlyWeatherResponse(
        resultCurrentWeather: Weather?,
        resultHourlyWeather: Result<HourlyWeatherDto>,
        resultPollution: Result<PollutionResponseDto>,
        resultLocationName: Result<List<GeocodingResponseDto>>,
    ) {
        when {
            resultHourlyWeather.isSuccess -> {
                var hourlyWeatherList = arrayListOf<HourlyWeather>()
                var hourlyResponseDto: HourlyWeatherDto? = resultHourlyWeather.getOrNull()
                hourlyResponseDto?.list?.forEach {
                    var weatherCondition = WeatherCondition(
                        id = it.weather[0].id,
                        description = it.weather[0].description,
                        icon = it.weather[0].icon,
                        main = it.weather[0].main
                    )

                    var hourlyWeather = HourlyWeather(
                        time = it.timestamp,
                        temperature = it.main.temperature,
                        condition = weatherCondition,
                    )
                    hourlyWeatherList.add(hourlyWeather)
                }
                resultCurrentWeather?.hourly = hourlyWeatherList
                checkPollutionResponse(resultCurrentWeather, resultPollution, resultLocationName)
            }

            else -> {
                uiState.value.copy(
                    isLoading = false,
                    error = resultHourlyWeather.exceptionOrNull()?.message
                        ?: "Failed to fetch weather"
                )
            }
        }
    }

    /***
     * Checks and processes pollution response.
     * @param resultCurrentWeather Current weather data.
     * @param resultPollution Pollution result.
     * @param resultLocationName Location name result.
     */
    private fun checkPollutionResponse(
        resultCurrentWeather: Weather?,
        resultPollution: Result<PollutionResponseDto>,
        resultLocationName: Result<List<GeocodingResponseDto>>
    ) {
        when {
            resultPollution.isSuccess -> {
                var hourlyResponseDto: PollutionResponseDto? = resultPollution.getOrNull()
                hourlyResponseDto?.list
                resultCurrentWeather?.pollution = Pollution(
                    aqi = hourlyResponseDto?.list[0]?.main?.aqi!!,
                    pm25 = hourlyResponseDto?.list[0]?.components?.pm25!!,
                    pm10 = hourlyResponseDto.list[0].components.pm10
                )
                checkLocationName(resultCurrentWeather, resultLocationName)
            }

            else -> {
                uiState.value.copy(
                    isLoading = false,
                    error = resultPollution.exceptionOrNull()?.message
                        ?: "Failed to fetch pollution"
                )
            }
        }
    }

    /***
     *  Checks and processes location name response.
     * @param resultCurrentWeather Current weather data.
     * @param resultLocationName Location name result.
     */
    private fun checkLocationName(
        resultCurrentWeather: Weather?,
        resultLocationName: Result<List<GeocodingResponseDto>>
    ) {
        uiState.value = when {
            resultLocationName.isSuccess -> {
                var result: List<GeocodingResponseDto>? = resultLocationName.getOrNull()

                // Map location name if available
                val locationName = result?.firstOrNull()?.let { geo ->
                    buildString {
                        append(geo.name)
                        geo.state?.let { append(", $it") }
                        append(", ${geo.country}")
                    }
                }

                resultCurrentWeather?.locationName = locationName
                uiState.value.copy(isLoading = false, weather = resultCurrentWeather)
            }

            else -> {
                uiState.value.copy(
                    isLoading = false,
                    error = resultLocationName.exceptionOrNull()?.message
                        ?: "Failed to fetch pollution"
                )
            }
        }
    }

    /***
     * Maps WeatherResponseDto to Weather domain model.
     * @return Weather domain model instance.
     */
    private fun WeatherResponseDto.toDomainModel(): Weather {
        var weatherCondition = WeatherCondition(
            id = weather[0].id,
            main = weather[0].main,
            description = weather[0].description,
            icon = weather[0].icon,
        )

        return Weather(
            current = CurrentWeather(
                temperature = main.temperature,
                condition = weatherCondition
            ),
            hourly = /*hourly.map { it.toDomainHourly() }*/arrayListOf(),
            daily = /*daily.map { it.toDomainDaily() }*/arrayListOf(),
            latitude = coord.latitude,
            longitude = coord.longitude
        )
    }
}